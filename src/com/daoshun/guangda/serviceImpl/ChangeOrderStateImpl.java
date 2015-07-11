package com.daoshun.guangda.serviceImpl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.daoshun.common.CommonUtils;
import com.daoshun.guangda.pojo.BalanceCoachInfo;
import com.daoshun.guangda.pojo.BalanceStudentInfo;
import com.daoshun.guangda.pojo.CoachBalancerecord;
import com.daoshun.guangda.pojo.CoachStudentInfo;
import com.daoshun.guangda.pojo.CouponCoach;
import com.daoshun.guangda.pojo.CouponRecord;
import com.daoshun.guangda.pojo.CuserInfo;
import com.daoshun.guangda.pojo.OrderInfo;
import com.daoshun.guangda.pojo.OrderNotiRecord;
import com.daoshun.guangda.pojo.OrderRecordInfo;
import com.daoshun.guangda.pojo.StudentBalanceRecord;
import com.daoshun.guangda.pojo.SuserInfo;
import com.daoshun.guangda.pojo.SystemSetInfo;
import com.daoshun.guangda.service.IChangeOrderState;

/**
 * 定时任务每天晚上24点执行,改变订单状态
 * 
 * @author guok
 * 
 */
@Component
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class ChangeOrderStateImpl extends BaseServiceImpl implements IChangeOrderState {

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Scheduled(cron = "0 0 0 * * ?")
	@Override
	public void changeOrderState() {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.SECOND, -1);

		// 首先查询出订单相关的几个时间配置
		String hqlset = "from SystemSetInfo where 1 = 1";
		SystemSetInfo setInfo = (SystemSetInfo) dataDao.getFirstObjectViaParam(hqlset, null);

		int s_order_end = 2880;// 订单结束时间多久之后自动结算默认48小时

		if (setInfo != null) {
			if (setInfo.getS_order_end() != null && setInfo.getS_order_end() != 0)
				s_order_end = setInfo.getS_order_end();
		}

		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, -s_order_end);
		String afterTwo = CommonUtils.getTimeFormat(c.getTime(), "yyyy-MM-dd HH:mm:ss");
		// 查询出需要结算的订单：条件为(订单的结束时间距离现在已经超过结算时间且订单没有投诉且订单的状态不是已经结算 且 教练已经确认下车过了)
		String hql = "from OrderInfo o where o.end_time < '" + afterTwo + "' and (select count(*) from ComplaintInfo c where c.order_id = o.orderid " + "and c.state = 0) = 0 and o.over_time is NULL";

		List<OrderInfo> orderList = (List<OrderInfo>) dataDao.getObjectsViaParam(hql, null);
		if (orderList != null) {
			for (OrderInfo order : orderList) {
				order.setStudentstate(3);// 设置订单状态为已结算

				String hql1 = "from OrderRecordInfo where operation = 4 and orderid = :orderid";
				String[] params = { "orderid" };
				OrderRecordInfo recordinfo = (OrderRecordInfo) dataDao.getFirstObjectViaParam(hql1, params, order.getOrderid());
				if (recordinfo != null) {
					// 教练金额的修改
					order.setOver_time(now.getTime());
					CuserInfo cuser = dataDao.getObjectById(CuserInfo.class, order.getCoachid());
					if (cuser != null) {
						// 查询订单中使用的优惠券的情况
						String couponrecord = order.getCouponrecordid();
						BigDecimal couponTotal = new BigDecimal(0);
						if (!CommonUtils.isEmptyString(couponrecord)) {
							String[] cids = couponrecord.split(",");
							for (int i = 0; i < cids.length; i++) {
								CouponRecord crecord = dataDao.getObjectById(CouponRecord.class, CommonUtils.parseInt(cids[i], 0));
								if (crecord != null) {
									int couponType = crecord.getCoupontype();
									if (couponType == 1) {// 时间券
										// 把券增加到教练那边去
										CouponCoach mCouponCoach = new CouponCoach();
										mCouponCoach.setCoachid(order.getCoachid());
										mCouponCoach.setCouponid(crecord.getCouponid());
										mCouponCoach.setGettime(new Date());
										mCouponCoach.setMoney_value(crecord.getMoney_value());
										mCouponCoach.setOwnerid(crecord.getOwnerid());
										mCouponCoach.setOwnertype(crecord.getOwnertype());
										mCouponCoach.setState(1);
										mCouponCoach.setValue(crecord.getValue());
										dataDao.addObject(mCouponCoach);
									} else {// 直接把钱增加到教练的余额
										couponTotal = couponTotal.add(new BigDecimal(crecord.getValue()));
									}
								}
							}
						}
						BigDecimal addToCoach = order.getTotal();
						addToCoach = addToCoach.subtract(new BigDecimal(order.getDelmoney()));
						addToCoach = addToCoach.add(couponTotal);

						cuser.setMoney(cuser.getMoney().add(addToCoach));
						cuser.setTotaltime(cuser.getTotaltime() + order.getTime());
						dataDao.updateObject(cuser);

						// 教练的余额流水
						BalanceCoachInfo balanceCoach = new BalanceCoachInfo();
						balanceCoach.setAddtime(new Date());
						balanceCoach.setAmount(addToCoach);
						balanceCoach.setAmount_out1(order.getPrice_out1());
						balanceCoach.setAmount_out2(order.getPrice_out2());
						balanceCoach.setType(1);
						balanceCoach.setUserid(order.getCoachid());
						dataDao.addObject(balanceCoach);
					}

					// 学员金额的修改
					SuserInfo student = dataDao.getObjectById(SuserInfo.class, order.getStudentid());
					if (student != null) {
						BigDecimal subToStudent = order.getTotal();
						subToStudent = subToStudent.subtract(new BigDecimal(order.getDelmoney()));
						student.setFmoney(student.getFmoney().subtract(subToStudent));
						student.setLearn_time(student.getLearn_time() + order.getTime());
						dataDao.updateObject(student);

						BalanceStudentInfo balanceStudent = new BalanceStudentInfo();
						balanceStudent.setAddtime(new Date());
						balanceStudent.setAmount(subToStudent);
						balanceStudent.setType(3);
						balanceStudent.setUserid(student.getStudentid());
						dataDao.addObject(balanceStudent);
					}

					// 增加教练以及学员的学时数量,并且修改教练与学员的关系表
					String hqlCoachStudent = "from CoachStudentInfo where coachid = :coachid and studentid = :studentid";
					String[] params8 = { "coachid", "studentid" };
					CoachStudentInfo info = (CoachStudentInfo) dataDao.getFirstObjectViaParam(hqlCoachStudent, params8, order.getCoachid(), order.getStudentid());
					if (info != null) {
						info.setMoney(info.getMoney().add(order.getTotal()));
						info.setHour(info.getHour() + order.getTime());
						dataDao.updateObject(info);
					} else {
						info = new CoachStudentInfo();
						info.setCoachid(order.getCoachid());
						info.setHour(order.getTime());
						info.setMoney(order.getTotal());
						info.setStudentid(order.getStudentid());
						dataDao.addObject(info);
					}
				}
				dataDao.updateObject(order);
			}
		}

		// 记录所有教练和学员的账户余额信息,在订单结算之后再去记录用户的余额等情况
		List<CuserInfo> cUserList = dataDao.getAllObject(CuserInfo.class);
		for (CuserInfo cuser : cUserList) {
			CoachBalancerecord record = new CoachBalancerecord();
			record.setAddtime(now.getTime());
			record.setCoachid(cuser.getCoachid());
			record.setFmoney(cuser.getFmoney());
			record.setGmoney(cuser.getGmoney());
			record.setMoney(cuser.getMoney());
			dataDao.addObject(record);
		}

		List<SuserInfo> sUserList = dataDao.getAllObject(SuserInfo.class);
		for (SuserInfo suser : sUserList) {
			StudentBalanceRecord record = new StudentBalanceRecord();
			record.setAddtime(now.getTime());
			record.setStudentid(suser.getStudentid());
			record.setFmoney(suser.getFmoney());
			record.setMoney(suser.getMoney());
			dataDao.addObject(record);
		}

		// 每天24点清楚下订单提醒记录表中已经过期的数据
		String hqlDelete = "from OrderNotiRecord where sendtime < :sendtime";
		String[] paramsD = { "sendtime" };
		List<OrderNotiRecord> listDelete = (List<OrderNotiRecord>) dataDao.getObjectsViaParam(hqlDelete, paramsD, new Date());
		if (listDelete != null) {
			for (OrderNotiRecord orderNotiRecord : listDelete) {
				dataDao.deleteObject(orderNotiRecord);
			}
		}
	}
}
