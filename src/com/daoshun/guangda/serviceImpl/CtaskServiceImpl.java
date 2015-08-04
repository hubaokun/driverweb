package com.daoshun.guangda.serviceImpl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.daoshun.common.CommonUtils;
import com.daoshun.common.PayType;
import com.daoshun.common.QueryResult;
import com.daoshun.common.UserType;
import com.daoshun.guangda.pojo.BalanceCoachInfo;
import com.daoshun.guangda.pojo.BalanceStudentInfo;
import com.daoshun.guangda.pojo.CoachStudentInfo;
import com.daoshun.guangda.pojo.CoinRecordInfo;
import com.daoshun.guangda.pojo.ComplaintInfo;
import com.daoshun.guangda.pojo.CouponCoach;
import com.daoshun.guangda.pojo.CouponRecord;
import com.daoshun.guangda.pojo.CuserInfo;
import com.daoshun.guangda.pojo.DriveSchoolInfo;
import com.daoshun.guangda.pojo.EvaluationInfo;
import com.daoshun.guangda.pojo.FeedBackInfo;
import com.daoshun.guangda.pojo.NoticesInfo;
import com.daoshun.guangda.pojo.NoticesUserInfo;
import com.daoshun.guangda.pojo.OrderInfo;
import com.daoshun.guangda.pojo.OrderRecordInfo;
import com.daoshun.guangda.pojo.RecommendInfo;
import com.daoshun.guangda.pojo.StudentCheckInfo;
import com.daoshun.guangda.pojo.SuserInfo;
import com.daoshun.guangda.pojo.SystemSetInfo;
import com.daoshun.guangda.pojo.VersionInfo;
import com.daoshun.guangda.service.ICtaskService;

/**
 * @author liukn
 * 
 */
@Service("ctaskService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class CtaskServiceImpl extends BaseServiceImpl implements ICtaskService {

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderInfo> getOrderInfoListBycoachid(String coachid, int page, int count) {
		StringBuffer ctaskhql = new StringBuffer();
		ctaskhql.append("from OrderInfo where coachid = :coachid and (coachstate = 1 or coachstate = 0)");
		ctaskhql.append("order by start_time asc");
		String[] params = { "coachid" };
		List<OrderInfo> orderInfolist = (List<OrderInfo>) dataDao.pageQueryViaParam(ctaskhql.toString(), count, page + 1, params, CommonUtils.parseInt(coachid, 0));
		return orderInfolist;
	}
	public List<OrderInfo> getOrderNoExistAgreeInfoListBycoachid2(String coachid, int page, int count) {
		StringBuffer ctaskhql = new StringBuffer();
		ctaskhql.append("from OrderInfo where coachid = :coachid and (coachstate = 1 or coachstate = 0) and (studentstate!=4 and coachstate!=4)");
		ctaskhql.append("order by start_time asc");
		String[] params = { "coachid" };
		List<OrderInfo> orderInfolist = (List<OrderInfo>) dataDao.pageQueryViaParam(ctaskhql.toString(), count, page + 1, params, CommonUtils.parseInt(coachid, 0));
		return orderInfolist;
	}
	public List<OrderInfo> getOrderNoExistAgreeInfoListBycoachid(String coachid, int page, int count) {
		StringBuffer ctaskhql = new StringBuffer();
		ctaskhql.append("select t.* from t_order t where coachid=");
		ctaskhql.append(coachid);
		ctaskhql.append(" and (coachstate = 1 or coachstate = 0)");
		ctaskhql.append(" and orderid not in (select orderid from t_order where studentstate=4 and coachstate=4 and coachid=");
		ctaskhql.append(coachid);
		ctaskhql.append(" )");
		ctaskhql.append(" order by start_time asc");
		System.out.println(ctaskhql.toString());
		//String[] params = { "coachid" };
		List<OrderInfo> orderInfolist = (List<OrderInfo>) dataDao.SqlPageQuery(ctaskhql.toString(), count, page + 1,OrderInfo.class, null);
		return orderInfolist;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderInfo> getOrderInfoListBycoach(int coachstate, int coachid) {
		StringBuffer ctaskhql = new StringBuffer();
		ctaskhql.append("from OrderInfo where coachstate = :coachstate and coachid = :coachid");
		String[] params = { "coachstate", "coachid" };
		List<OrderInfo> orderlist = (List<OrderInfo>) dataDao.getObjectsViaParam(ctaskhql.toString(), params, coachstate, coachid);
		return orderlist;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderInfo> getPageOrderInfoListBycoach(int coachstate, int coachid, int page, int count) {
		StringBuffer ctaskhql = new StringBuffer();
		ctaskhql.append("from OrderInfo where coachstate = :coachstate and coachid = :coachid ");
		ctaskhql.append("order by end_time desc");
		String[] params = { "coachstate", "coachid" };
		List<OrderInfo> orderlist = (List<OrderInfo>) dataDao.pageQueryViaParam(ctaskhql.toString(), count, page + 1, params, coachstate, coachid);
		return orderlist;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void updateOrderInfo(OrderInfo orderinfo) {
		dataDao.updateObject(orderinfo);
	}

	@Override
	public EvaluationInfo getEvaluationInfoByorder(int order_id, int from_user, int to_user, int type) {
		StringBuffer ctaskhql = new StringBuffer();
		ctaskhql.append("from EvaluationInfo where order_id = :order_id and from_user = :from_user and to_user = :to_user and type = :type ");
		String[] params = { "order_id", "from_user", "to_user", "type" };
		EvaluationInfo evaluationInfo = (EvaluationInfo) dataDao.getFirstObjectViaParam(ctaskhql.toString(), params, order_id, from_user, to_user, type);
		return evaluationInfo;
	}

	@Override
	public OrderRecordInfo getOrderRecordInfo(int orderid, int coachid, int operation) {
		StringBuffer ctaskhql = new StringBuffer();
		ctaskhql.append("from OrderRecordInfo where orderid = :orderid and userid = :coachid and operation = :operation");
		String[] params = { "orderid", "coachid", "operation" };
		OrderRecordInfo orderRecore = (OrderRecordInfo) dataDao.getFirstObjectViaParam(ctaskhql.toString(), params, orderid, coachid, operation);
		return orderRecore;
	}

	@Override
	public OrderInfo getOrderInfo(int orderid) {
		OrderInfo orderInfo = dataDao.getObjectById(OrderInfo.class, orderid);
		return orderInfo;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void addOrderRecordInfo(OrderRecordInfo orderRecordInfo) {
		dataDao.addObject(orderRecordInfo);
	}

	@Override
	public EvaluationInfo getEvaluationInfo(int orderid, int userid, int type) {
		StringBuffer ctaskhql = new StringBuffer();
		ctaskhql.append("from EvaluationInfo where order_id = :orderid and from_user = :userid and type = :type");
		String[] params = { "orderid", "userid", "type" };
		EvaluationInfo evaluationInfo = (EvaluationInfo) dataDao.getFirstObjectViaParam(ctaskhql.toString(), params, orderid, userid, type);
		return evaluationInfo;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void addEvaluationInfo(EvaluationInfo evaluationInfo, int type) {
		String defaultComment = "";
		// 首先查询出订单相关的几个时间配置
		String hqlset = "from SystemSetInfo where 1 = 1";
		SystemSetInfo setInfo = (SystemSetInfo) dataDao.getFirstObjectViaParam(hqlset, null);
		if (type == 1) {// 教练评价学员
			if (setInfo != null && !CommonUtils.isEmptyString(setInfo.getC_default_coment())) {
				defaultComment = setInfo.getC_default_coment();
			}
		} else {
			if (setInfo != null && !CommonUtils.isEmptyString(setInfo.getS_default_coment())) {
				defaultComment = setInfo.getS_default_coment();
			}
		}
		if (CommonUtils.isEmptyString(evaluationInfo.getContent())) {
			evaluationInfo.setContent(defaultComment);
		}
		dataDao.addObject(evaluationInfo);

		// 增加记录
		OrderRecordInfo orderRecord = new OrderRecordInfo();
		orderRecord.setAddtime(new Date());
		orderRecord.setEvaluationid(evaluationInfo.getEvaluationid());
		if (type == 1) {
			orderRecord.setOperation(8);
		} else {
			orderRecord.setOperation(6);
		}
		orderRecord.setOrderid(evaluationInfo.getOrder_id());
		orderRecord.setUserid(evaluationInfo.getFrom_user());
		dataDao.addObject(orderRecord);
	}

	@Override
	public List<ComplaintInfo> getComplaintInfoByorderid(int order_id) {
		StringBuffer ctaskhql = new StringBuffer();
		ctaskhql.append("from ComplaintInfo where order_id = :order_id and type = 1 and state = 0");
		String[] params = { "order_id" };
		return (List<ComplaintInfo>) dataDao.getObjectsViaParam(ctaskhql.toString(), params, order_id);
	}

	@Override
	public SuserInfo getSuserInfoBysuserid(int studentid) {
		SuserInfo suserInfo = dataDao.getObjectById(SuserInfo.class, studentid);
		return suserInfo;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void updateSuserInfo(SuserInfo suserInfo) {
		dataDao.updateObject(suserInfo);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void addStudentCheckInfo(StudentCheckInfo studentCheckInfo) {
		dataDao.addObject(studentCheckInfo);
	}

	/*********************************************************** ACTION ***********************************************************/
	@SuppressWarnings("unchecked")
	@Override
	public List<FeedBackInfo> getFeedbackInfo() {
		StringBuffer ctaskhql = new StringBuffer();
		ctaskhql.append("from FeedBackInfo ");
		List<FeedBackInfo> feedbacklist = (List<FeedBackInfo>) dataDao.getObjectsViaParam(ctaskhql.toString(), null);
		return feedbacklist;
	}

	@Override
	public Object getFromuserBy(int fromid, int type) {
		if (type == 1) {
			CuserInfo cuser = dataDao.getObjectById(CuserInfo.class, fromid);
			return cuser;
		} else {
			SuserInfo suser = dataDao.getObjectById(SuserInfo.class, fromid);
			return suser;
		}
	}

	@Override
	public FeedBackInfo getfeedbackByid(int feedbackid) {
		FeedBackInfo feedbackInfo = dataDao.getObjectById(FeedBackInfo.class, feedbackid);
		return feedbackInfo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FeedBackInfo> getFeedbackBykeyword(String searchname, String searchphone, String starttime, String endtime) {
		StringBuffer ctaskhql = new StringBuffer();
		ctaskhql.append("from FeedBackInfo where 1=1 ");
		if (!CommonUtils.isEmptyString(searchname) && !CommonUtils.isEmptyString(searchphone)) {
			ctaskhql.append("and ( fromid in ( select studentid from SuserInfo where realname like '%" + searchname + "%' and phone like '%" + searchphone
					+ "%' ) and from_type = 2 ) or (fromid in (select coachid from CuserInfo where realname like '%" + searchname + "%' and phone like '%" + searchphone + "%') and from_type = 1) ");
		}
		if (CommonUtils.isEmptyString(searchname) && !CommonUtils.isEmptyString(searchphone)) {
			ctaskhql.append("and (fromid in (select studentid from SuserInfo where  phone like '%" + searchphone
					+ "%') and from_type = 2) or (fromid in (select coachid from CuserInfo where  phone like '%" + searchphone + "%')and from_type = 1  )");
		}
		if (!CommonUtils.isEmptyString(searchname) && CommonUtils.isEmptyString(searchphone)) {
			ctaskhql.append("and (fromid in (select studentid from SuserInfo where  realname like '%" + searchname
					+ "%') and from_type = 2) or (fromid in ( select coachid from CuserInfo where  realname like '%" + searchname + "%') and from_type = 1)");
		}
		if (!CommonUtils.isEmptyString(starttime)) {
			Date newstart = CommonUtils.getDateFormat(starttime, "yyyy-MM-dd");
			Calendar firsttime = Calendar.getInstance();
			firsttime.setTime(newstart);
			firsttime.set(Calendar.HOUR_OF_DAY, 0);
			firsttime.set(Calendar.MINUTE, 0);
			firsttime.set(Calendar.SECOND, 0);
			Date newstarttime = firsttime.getTime();
			String newtoday = CommonUtils.getTimeFormat(newstarttime, "yyyy-MM-dd");
			ctaskhql.append("and addtime >= '" + newtoday + "'");
		}
		if (!CommonUtils.isEmptyString(endtime)) {
			Date newend = CommonUtils.getDateFormat(endtime, "yyyy-MM-dd");
			Calendar sectime = Calendar.getInstance();
			sectime.setTime(newend);
			sectime.set(Calendar.HOUR_OF_DAY, 23);
			sectime.set(Calendar.MINUTE, 59);
			sectime.set(Calendar.SECOND, 59);
			Date newendtime = sectime.getTime();
			String newtimelater = CommonUtils.getTimeFormat(newendtime, "yyyy-MM-dd");
			ctaskhql.append("and addtime <= '" + newtimelater + "'");
		}
		List<FeedBackInfo> feedBackInfolist = (List<FeedBackInfo>) dataDao.getObjectsViaParam(ctaskhql.toString(), null);
		return feedBackInfolist;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VersionInfo> getVersion() {
		StringBuffer ctaskhql = new StringBuffer();
		ctaskhql.append("from VersionInfo ");
		List<VersionInfo> versionInfo = (List<VersionInfo>) dataDao.getObjectsViaParam(ctaskhql.toString(), null);
		return versionInfo;
	}

	@Override
	public VersionInfo getVersionInfoById(int versionid) {
		VersionInfo versionInfo = dataDao.getObjectById(VersionInfo.class, versionid);
		return versionInfo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public QueryResult<NoticesInfo> getNoticesInfoList(String noticestarttime, String noticeendtime, Integer pageIndex, int pagesize) {
		StringBuffer ctaskhql = new StringBuffer();
		ctaskhql.append("from NoticesInfo where 1=1");
		if (!CommonUtils.isEmptyString(noticestarttime)) {
			noticestarttime = noticestarttime + " 00:00:00";
			ctaskhql.append(" and addtime >= '" + noticestarttime + "'");
		}
		if (!CommonUtils.isEmptyString(noticeendtime)) {
			noticeendtime = noticeendtime + " 23:59:59";
			ctaskhql.append(" and addtime <= '" + noticeendtime + "'");
		}
		List<NoticesInfo> noticesInfolist = (List<NoticesInfo>) dataDao.pageQueryViaParam(ctaskhql.toString(), pagesize, pageIndex, null);
		String counthql = " select count(*) " + ctaskhql.toString();
		long total = (Long) dataDao.getFirstObjectViaParam(counthql, null);
		return new QueryResult<NoticesInfo>(noticesInfolist, total);
	}

	@Override
	public DriveSchoolInfo getDriveSchoolByname(String name) {
		StringBuffer ctaskhql = new StringBuffer();
		ctaskhql.append("from DriveSchoolInfo where name = :name");
		String[] params = { "name" };
		DriveSchoolInfo driveSchoolInfo = (DriveSchoolInfo) dataDao.getFirstObjectViaParam(ctaskhql.toString(), params, name);
		return driveSchoolInfo;
	}

	@Override
	public NoticesInfo getNoticeById(int noticeid) {
		NoticesInfo noticesInfo = dataDao.getObjectById(NoticesInfo.class, noticeid);
		return noticesInfo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public QueryResult<NoticesInfo> getNoticeBykeyword(String noticestarttime, String noticeendtime, Integer pageIndex, int pagesize) {
		StringBuffer ctaskhql = new StringBuffer();
		ctaskhql.append("from NoticesInfo where 1=1");

		if (!CommonUtils.isEmptyString(noticeendtime)) {
			Date newend = CommonUtils.getDateFormat(noticeendtime, "yyyy-MM-dd");
			Calendar sectime = Calendar.getInstance();
			sectime.setTime(newend);
			sectime.set(Calendar.HOUR_OF_DAY, 23);
			sectime.set(Calendar.MINUTE, 59);
			sectime.set(Calendar.SECOND, 59);
			Date endtime = sectime.getTime();
			String newtimelater = CommonUtils.getTimeFormat(endtime, "yyyy-MM-dd");
			ctaskhql.append(" and addtime <= '" + newtimelater + "'");
		}
		if (!CommonUtils.isEmptyString(noticestarttime)) {
			Date newstart = CommonUtils.getDateFormat(noticestarttime, "yyyy-MM-dd");
			Calendar firsttime = Calendar.getInstance();
			firsttime.setTime(newstart);
			firsttime.set(Calendar.HOUR_OF_DAY, 0);
			firsttime.set(Calendar.MINUTE, 0);
			firsttime.set(Calendar.SECOND, 0);
			Date starttime = firsttime.getTime();
			String newtoday = CommonUtils.getTimeFormat(starttime, "yyyy-MM-dd");
			ctaskhql.append(" and addtime >= '" + newtoday + "'");
		}
		List<NoticesInfo> noticesInfolist = (List<NoticesInfo>) dataDao.pageQueryViaParam(ctaskhql.toString(), pagesize, pageIndex, null);
		String counthql = " select count(*) " + ctaskhql.toString();
		long total = (Long) dataDao.getFirstObjectViaParam(counthql, null);
		return new QueryResult<NoticesInfo>(noticesInfolist, total);
	}

	@Override
	public NoticesUserInfo getNoticeUserInfoByid(int noticeid) {
		NoticesUserInfo noticesUserInfo = dataDao.getObjectById(NoticesUserInfo.class, noticeid);
		return noticesUserInfo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<?> getUserInfo(Integer usertype, String searchname) {
		if (usertype == 1 || usertype == 3 || usertype == 4) {
			StringBuffer ctaskhql = new StringBuffer();
			ctaskhql.append(" from CuserInfo where 1=1 ");
			if (!CommonUtils.isEmptyString(searchname)) {
				ctaskhql.append(" and realname like '%" + searchname + "%'");
			}
			List<CuserInfo> cuserlist = (List<CuserInfo>) dataDao.getObjectsViaParam(ctaskhql.toString(), null);
			return cuserlist;
		} else {
			StringBuffer ctaskhql = new StringBuffer();
			ctaskhql.append(" from SuserInfo where 1=1 ");
			if (!CommonUtils.isEmptyString(searchname)) {
				ctaskhql.append(" and realname like '%" + searchname + "%'");
			}
			List<SuserInfo> suserlist = (List<SuserInfo>) dataDao.getObjectsViaParam(ctaskhql.toString(), null);
			return suserlist;
		}
	}

	@Override
	public void setMessageByInfo(Integer usertype, Integer settype, Integer singleuserid, String category, String contents) {

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<OrderInfo> getStudentofCoach(int coachid, Integer usertype) {
		StringBuffer ctaskhql = new StringBuffer();
		ctaskhql.append(" from OrderInfo where coachid = :coachid ");
		if (usertype == 4) {
			ctaskhql.append(" and (coachstate = 1 or coachstate = 0)");
		}
		String[] params = { "coachid" };
		List<OrderInfo> orderlist = (List<OrderInfo>) dataDao.getObjectsViaParam(ctaskhql.toString(), params, coachid);
		return orderlist;
	}

	@Override
	public int getCountByorderid(int id) {
		StringBuffer ctaskhql = new StringBuffer();
		ctaskhql.append("select count(*) from ComplaintInfo where orderid = :orderid ");
		String[] params = { "orderid" };
		int count = (Integer) dataDao.getFirstObjectViaParam(ctaskhql.toString(), params, id);
		return count;
	}

	@Override
	public CuserInfo getCoachInfoById(int coachid) {
		return dataDao.getObjectById(CuserInfo.class, coachid);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void updateCoachInfo(CuserInfo coach) {
		dataDao.updateObject(coach);
	}

	@Override
	public DriveSchoolInfo getDriveSchoolByid(int schoolid) {
		DriveSchoolInfo driveschool = dataDao.getObjectById(DriveSchoolInfo.class, schoolid);
		return driveschool;
	}
	/**
	 * 订单结算分为两种情况
		1、自动结算时间未到，此时学员评价和教练确认下车的后完成的动作触发订单结算
		2、自动结算时间已过，只要教练确认下车，系统就会自动结算，如果没确认下车，就会一直等到确认下车后才会结算
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void SettlementOrder(int orderid) {
		String hql = "from OrderRecordInfo where operation = 4 and orderid = :orderid";
		String[] params = { "orderid" };
		OrderRecordInfo recordinfo = (OrderRecordInfo) dataDao.getFirstObjectViaParam(hql, params, orderid);

		OrderInfo order = dataDao.getObjectById(OrderInfo.class, orderid);
		SuserInfo student = dataDao.getObjectById(SuserInfo.class, order.getStudentid());
		CuserInfo cuser = dataDao.getObjectById(CuserInfo.class, order.getCoachid());
		if (order != null) {
			
			order.setStudentstate(3);// 设置订单状态为已结算
			if (recordinfo != null) {// 如果教练确认下车过的话
				// 教练金额的修改
				order.setOver_time(new Date());
				
				if (cuser != null) {
					if(order.getPaytype()==PayType.MONEY){
						BigDecimal b1=new BigDecimal(order.getTotal().intValue());
						//设置学员冻结金额为
						student.setFmoney(student.getFmoney().subtract(b1));
						b1=b1.add(cuser.getMoney());
						cuser.setMoney(b1);
					}else if(order.getPaytype()==PayType.COUPON){
						// 查询订单中使用的优惠券的情况
						String couponrecord = order.getCouponrecordid();
						//BigDecimal couponTotal = new BigDecimal(0);
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
										mCouponCoach.setCouponrecordid(crecord.getCouponid());
										dataDao.addObject(mCouponCoach);
									} else {// 直接把钱增加到教练的余额
										//现金券，已弃用
										//couponTotal = couponTotal.add(new BigDecimal(crecord.getValue()));
									}
								}
							}
						}
						/*BigDecimal addToCoach = order.getTotal();
						addToCoach = addToCoach.subtract(new BigDecimal(order.getDelmoney()));
						addToCoach = addToCoach.add(couponTotal);
						cuser.setMoney(cuser.getMoney().add(addToCoach));
						*/
					}else if(order.getPaytype()==PayType.COIN){
						BigDecimal b1=new BigDecimal(order.getTotal().intValue());
						//设置学员冻结金额为
						student.setFcoinnum(student.getFcoinnum().subtract(b1));
						b1=b1.add(new BigDecimal(cuser.getCoinnum()));
						cuser.setCoinnum(b1.intValue());
					}
					cuser.setTotaltime(cuser.getTotaltime() + order.getTime());
					student.setLearn_time(student.getLearn_time() + order.getTime());
					
					//取消教练的冻结小巴币
					/*if(cuser.getFcoinnum()-order.getTotal().intValue()>=0){
						cuser.setFcoinnum(cuser.getFcoinnum()-order.getTotal().intValue());
					}*/
					dataDao.updateObject(cuser);
					dataDao.updateObject(student);
				}
				if(cuser!=null){
					// 教练的流水修改
					BalanceCoachInfo balanceCoach = new BalanceCoachInfo();
					balanceCoach.setAddtime(new Date());
					balanceCoach.setAmount(order.getTotal().subtract(new BigDecimal(order.getDelmoney())));
					balanceCoach.setAmount_out1(order.getPrice_out1());
					balanceCoach.setAmount_out2(order.getPrice_out2());
					balanceCoach.setType(1);//学员支付
					balanceCoach.setUserid(order.getCoachid());
					dataDao.addObject(balanceCoach);
				}
				// 学员流水的修改
				if (student != null) {
					BalanceStudentInfo balanceStudent = new BalanceStudentInfo();
					balanceStudent.setAddtime(new Date());
					balanceStudent.setAmount(order.getTotal().subtract(new BigDecimal(order.getDelmoney())));
					balanceStudent.setType(3);//3表示学员订单支付
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
	/**
	 * 订单结算分为两种情况
		1、自动结算时间未到，此时学员评价和教练确认下车的后完成的动作触发订单结算
		2、自动结算时间已过，只要教练确认下车，系统就会自动结算，如果没确认下车，就会一直等到确认下车后才会结算
	 */
	// 在教练确认下车的时候去结算订单
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void SettlementOrderWhenCoachDown(int orderid) {
		OrderInfo order = dataDao.getObjectById(OrderInfo.class, orderid);
		if (order != null) {
			// 首先查询出订单相关的几个时间配置
			String hqlset = "from SystemSetInfo where 1 = 1";
			SystemSetInfo setInfo = (SystemSetInfo) dataDao.getFirstObjectViaParam(hqlset, null);

			int s_order_end = 2880;// 订单结束时间多久之后自动结算默认48小时

			if (setInfo != null) {
				if (setInfo.getS_order_end() != null && setInfo.getS_order_end() != 0)
					s_order_end = setInfo.getS_order_end();
			}

			Calendar c = Calendar.getInstance();
			c.setTime(order.getEnd_time());
			c.add(Calendar.MINUTE, s_order_end);

			Calendar now = Calendar.getInstance();

			String hql = "from ComplaintInfo where order_id = :orderid and state = 0";
			String[] params = { "orderid" };

			List<ComplaintInfo> compList = (List<ComplaintInfo>) dataDao.getObjectsViaParam(hql, params, orderid);
			if (compList == null || compList.size() == 0) {// 无投诉的情况下
				// 时间是否已经过去订单的over时间
				if (now.after(c)) {// 系统自动结算时间已过
					// 教练金额的修改
					order.setOver_time(new Date());
					order.setStudentstate(3);
					dataDao.updateObject(order);
					SettlementOrder(orderid);
				} else {
					// 是否已经评论
					String eHql = "from EvaluationInfo where order_id = :order_id and type = 1";
					String[] eParams = { "order_id" };
					EvaluationInfo eList = (EvaluationInfo) dataDao.getFirstObjectViaParam(eHql, eParams, orderid);
					if (eList != null) {
						SettlementOrder(orderid);
					}
				}
				//修改分享表中的开单标识位
				StringBuffer cuserhql = new StringBuffer();
				cuserhql.append("from RecommendInfo where invitedcoachid = :invitedcoachid");
				String[] params1 = { "invitedcoachid" };
				RecommendInfo tempRecommendInfo = (RecommendInfo) dataDao.getFirstObjectViaParam(cuserhql.toString(), params1, order.getCoachid());
				if(tempRecommendInfo!=null)
				{
					tempRecommendInfo.setIsorder(1);
					if(tempRecommendInfo.getOflag()!=2)
					tempRecommendInfo.setOflag(1);
					dataDao.updateObject(tempRecommendInfo);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public QueryResult<FeedBackInfo> getFeedbackInfoOfPage(String searchname, String searchphone, String starttime, String endtime, Integer pageIndex, int pageSize) {
		StringBuffer ctaskhql = new StringBuffer();
		ctaskhql.append(" from FeedBackInfo where 1=1");
		if (!CommonUtils.isEmptyString(searchname) && !CommonUtils.isEmptyString(searchphone)) {
			ctaskhql.append("and ( fromid in ( select studentid from SuserInfo where realname like '%" + searchname + "%' and phone like '%" + searchphone
					+ "%' ) and from_type = 2 ) or (fromid in (select coachid from CuserInfo where realname like '%" + searchname + "%' and phone like '%" + searchphone + "%') and from_type = 1) ");
		}
		if (CommonUtils.isEmptyString(searchname) && !CommonUtils.isEmptyString(searchphone)) {
			ctaskhql.append("and (fromid in (select studentid from SuserInfo where  phone like '%" + searchphone
					+ "%') and from_type = 2) or (fromid in (select coachid from CuserInfo where  phone like '%" + searchphone + "%')and from_type = 1  )");
		}
		if (!CommonUtils.isEmptyString(searchname) && CommonUtils.isEmptyString(searchphone)) {
			ctaskhql.append("and (fromid in (select studentid from SuserInfo where  realname like '%" + searchname
					+ "%') and from_type = 2) or (fromid in ( select coachid from CuserInfo where  realname like '%" + searchname + "%') and from_type = 1)");
		}
		if (!CommonUtils.isEmptyString(starttime)) {
			Date newstart = CommonUtils.getDateFormat(starttime, "yyyy-MM-dd");
			Calendar firsttime = Calendar.getInstance();
			firsttime.setTime(newstart);
			firsttime.set(Calendar.HOUR_OF_DAY, 0);
			firsttime.set(Calendar.MINUTE, 0);
			firsttime.set(Calendar.SECOND, 0);
			Date newstarttime = firsttime.getTime();
			String newtoday = CommonUtils.getTimeFormat(newstarttime, "yyyy-MM-dd");
			ctaskhql.append("and addtime >= '" + newtoday + "'");
		}
		if (!CommonUtils.isEmptyString(endtime)) {
			Date newend = CommonUtils.getDateFormat(endtime, "yyyy-MM-dd");
			Calendar sectime = Calendar.getInstance();
			sectime.setTime(newend);
			sectime.set(Calendar.HOUR_OF_DAY, 23);
			sectime.set(Calendar.MINUTE, 59);
			sectime.set(Calendar.SECOND, 59);
			Date newendtime = sectime.getTime();
			String newtimelater = CommonUtils.getTimeFormat(newendtime, "yyyy-MM-dd");
			ctaskhql.append("and addtime <= '" + newtimelater + "'");
		}
		ctaskhql.append(" order by addtime");
		List<FeedBackInfo> list = (List<FeedBackInfo>) dataDao.pageQueryViaParam(ctaskhql.toString(), pageSize, pageIndex, null);
		String counthql = " select count(*) " + ctaskhql.toString();
		long count = (Long) dataDao.getFirstObjectViaParam(counthql, null);
		QueryResult<FeedBackInfo> result = new QueryResult<FeedBackInfo>(list, count);
		return result;
	}

	@Override
	public List<EvaluationInfo> getEvaluationList(int userid, int type) {
		String hql = "from EvaluationInfo where to_user = :to_user and type = :type";
		String[] params = { "to_user", "type" };

		return (List<EvaluationInfo>) dataDao.getObjectsViaParam(hql, params, userid, type);
	}

}
