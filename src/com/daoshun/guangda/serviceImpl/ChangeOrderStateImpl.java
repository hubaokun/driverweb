package com.daoshun.guangda.serviceImpl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.daoshun.common.CommonUtils;
import com.daoshun.common.PayType;
import com.daoshun.guangda.pojo.BalanceCoachInfo;
import com.daoshun.guangda.pojo.BalanceStudentInfo;
import com.daoshun.guangda.pojo.CoachBalancerecord;
import com.daoshun.guangda.pojo.CoachStudentInfo;
import com.daoshun.guangda.pojo.CouponCoach;
import com.daoshun.guangda.pojo.CouponRecord;
import com.daoshun.guangda.pojo.CuserInfo;
import com.daoshun.guangda.pojo.EvaluationInfo;
import com.daoshun.guangda.pojo.OrderInfo;
import com.daoshun.guangda.pojo.OrderNotiRecord;
import com.daoshun.guangda.pojo.OrderRecordInfo;
import com.daoshun.guangda.pojo.StudentBalanceRecord;
import com.daoshun.guangda.pojo.SuserInfo;
import com.daoshun.guangda.pojo.SystemSetInfo;
import com.daoshun.guangda.service.IChangeOrderState;
import com.daoshun.guangda.service.ISUserService;

/**
 * 定时任务每天晚上24点执行,改变订单状态
 * 
 * @author guok
 * 
 */
@Component
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class ChangeOrderStateImpl extends BaseServiceImpl implements IChangeOrderState {
	@Resource
	ISUserService suserService;
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Scheduled(cron = "0 0 1 * * ?")
	//@Scheduled(cron = "0 40 10 ? * *")
	//@Scheduled(cron="0/50 * *  * * ? ")
	@Override
	public void changeOrderState() {
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("##################定时任务执行开始："+sf.format(new Date())+"#################");
		Calendar now = Calendar.getInstance();
		now.add(Calendar.SECOND, -1);

		// 首先查询出订单相关的几个时间配置
		String hqlset = "from SystemSetInfo ";
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
		String hql = "from OrderInfo o where o.end_time < '" + afterTwo + "' and (select count(*) from ComplaintInfo c where c.order_id = o.orderid " + " and c.state = 0) = 0 and o.over_time is NULL";
		//String hql="from OrderInfo o WHERE o.paytype = 3 AND time(o.over_time) = '00:59:59'";
		//String hql="from OrderInfo o where o.over_time is null and o.studentid=30088";
		//System.out.println(hql);
		List<OrderInfo> orderList = (List<OrderInfo>) dataDao.getObjectsViaParam(hql, null);
		if (orderList != null) {
			for (OrderInfo order : orderList) {
				
				SuserInfo student = dataDao.getObjectById(SuserInfo.class, order.getStudentid());
				CuserInfo cuser = dataDao.getObjectById(CuserInfo.class, order.getCoachid());
				
				String hql1 = "from OrderRecordInfo where operation = 4 and orderid = :orderid";
				String[] params = { "orderid" };
				OrderRecordInfo recordinfo = (OrderRecordInfo) dataDao.getFirstObjectViaParam(hql1, params, order.getOrderid());
				if (recordinfo != null) {// 如果教练确认下车过的话
					addEvaluation(order,cuser,student);//生成默认好评
					order.setStudentstate(3);// 设置订单状态为已结算
					//设置订单的over_time
					order.setOver_time(new Date());
					if (cuser != null) {
						//订单中的学员小巴币及冻结小巴币
						BigDecimal studentFCoinNum =suserService.getStudentFrozenCoin(student.getStudentid());
						//订单中的教练小巴币
						int coachOrdercoinnum=suserService.getCoachCoin(cuser.getCoachid()).intValue();
						//订单中的教练余额及冻结额
						//BigDecimal cuserOrderFMoney=new BigDecimal(cmoney[1]);
						BigDecimal coachOrderMoney=suserService.getCoachMoney(cuser.getCoachid());
						
						//订单中的学员余额及冻结余额
						BigDecimal studentOrderFMoney=suserService.getStudentFrozenMoney(student.getStudentid());
						
						
						if(order.getPaytype()==PayType.MONEY){
							BigDecimal orderTotal=new BigDecimal(order.getTotal().intValue());
							//设置学员冻结金额为
							student.setFmoney(studentOrderFMoney.subtract(orderTotal));
							orderTotal=orderTotal.add(coachOrderMoney);
							cuser.setMoney(orderTotal);
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
											mCouponCoach.setCouponrecordid(crecord.getRecordid());
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
							//学员冻结小巴币取消
							BigDecimal orderTotal=new BigDecimal(order.getTotal().intValue());
							/*if(student.getFcoinnum().intValue()>=orderTotal.intValue()){
								student.setFcoinnum(studentFCoinNum.subtract(orderTotal));
							}else{ 
								System.out.println("小巴币结算：自动结算方法中：学员小巴币解冻时发现数量小于订单额!停止结算");
								System.out.println("studentid="+student.getStudentid()+",本次结算学员被冻结的小巴币："+student.getFcoinnum().intValue());
								System.out.println("studentid="+student.getStudentid()+",本次结算订单额："+orderTotal.intValue()+"orderid = "+order.getOrderid());
								continue;
								//return;
							}*/
							orderTotal=orderTotal.add(new BigDecimal(coachOrdercoinnum));
							//教练小巴币增加
							cuser.setCoinnum(orderTotal.intValue());
							suserService.addCoinForSettlement(order, cuser, student,1);
							/*CoinRecordInfo coinRecordInfo = new CoinRecordInfo ();
					        coinRecordInfo.setReceiverid(cuser.getCoachid());
					        coinRecordInfo.setReceivertype(UserType.COAH);
					        coinRecordInfo.setReceivername(cuser.getRealname());
					       
					        coinRecordInfo.setPayerid(student.getStudentid());
					        coinRecordInfo.setPayertype(UserType.STUDENT);
					        coinRecordInfo.setPayername(student.getRealname());
					        coinRecordInfo.setType(CoinType.STUDENT_PAY);//学员支付
					        //查询ownerid的来源
					        
					        coinRecordInfo.setOwnerid(cuser.getCoachid());
					        coinRecordInfo.setOwnertype(2);
					        coinRecordInfo.setOwnername(cuser.getRealname());
					        
					        coinRecordInfo.setCoinnum(order.getTotal().intValue());
					        coinRecordInfo.setAddtime(new Date());
					        coinRecordInfo.setOrderid(order.getOrderid());//设置小巴币所属的订单的ID
					        dataDao.addObject(coinRecordInfo);*/
						}else if(order.getPaytype()==PayType.COIN_MONEY){
							//混合支付
							//###############处理 小巴币 开始###################
							//学员冻结小巴币取消
							BigDecimal mixCoin=new BigDecimal(order.getMixCoin());
							/*if(student.getFcoinnum().intValue()>=mixCoin.intValue()){
								student.setFcoinnum(studentFCoinNum.subtract(mixCoin));
							}else{
								System.out.println("小巴币+余额混合支付结算小巴币结算部分：自动结算方法中：学员小巴币解冻时发现数量小于订单额!停止结算");
								System.out.println("studentid="+student.getStudentid()+",本次结算学员被冻结的小巴币："+student.getFcoinnum().intValue());
								System.out.println("studentid="+student.getStudentid()+",本次结算订单额："+mixCoin.intValue()+"orderid = "+order.getOrderid());
								continue;
								//return;
							}*/
							mixCoin=mixCoin.add(new BigDecimal(coachOrdercoinnum));
							//教练小巴币增加
							cuser.setCoinnum(mixCoin.intValue());
							suserService.addCoinForSettlement(order, cuser, student,2);
							/*CoinRecordInfo coinRecordInfo = new CoinRecordInfo ();
					        coinRecordInfo.setReceiverid(cuser.getCoachid());
					        coinRecordInfo.setReceivertype(UserType.COAH);
					        coinRecordInfo.setReceivername(cuser.getRealname());
					       
					        coinRecordInfo.setPayerid(student.getStudentid());
					        coinRecordInfo.setPayertype(UserType.STUDENT);
					        coinRecordInfo.setPayername(student.getRealname());
					        coinRecordInfo.setType(CoinType.STUDENT_PAY);//学员支付
					        
					        coinRecordInfo.setOwnerid(cuser.getCoachid());
					        coinRecordInfo.setOwnertype(2);
					        coinRecordInfo.setOwnername(cuser.getRealname());
					        
					        coinRecordInfo.setCoinnum(order.getMixCoin());
					        coinRecordInfo.setAddtime(new Date());
					        coinRecordInfo.setOrderid(order.getOrderid());//设置小巴币所属的订单的ID
					        dataDao.addObject(coinRecordInfo);*/
					        //###############处理 小巴币 结束###################
					        //###############处理余额 开始###################
					        BigDecimal mmoney=new BigDecimal(order.getMixMoney());
							//设置学员冻结金额为
							student.setFmoney(studentFCoinNum.subtract(mmoney));
							mmoney=mmoney.add(coachOrderMoney);
							cuser.setMoney(mmoney);
					        //###############处理余额 结束###################
						}
						cuser.setTotaltime(cuser.getTotaltime() + order.getTime());
						student.setLearn_time(student.getLearn_time() + order.getTime());
						
						/*if(cuser.getAccompanynum()==null){
							cuser.setAccompanynum(0);
						}
						
						//如果是陪驾，陪驾次数加一
						String hql_acc="from OrderPrice where orderid=:orderid";
						OrderPrice op=(OrderPrice) dataDao.getFirstObjectViaParam(hql_acc, new String[]{"orderid"}, order.getOrderid());
						if(op!=null){
							if("陪驾".equals(op.getSubject())){
								cuser.setAccompanynum(cuser.getAccompanynum()+1);
							}
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
						BigDecimal settleOrderTotal=suserService.getSettleOrderTotal(order.getCoachid(),order.getStudentid());
						BigInteger settleOrderTime=suserService.getSettleOrderTime(order.getCoachid(),order.getStudentid());
						info.setMoney(settleOrderTotal.add(order.getTotal()));
						info.setHour(settleOrderTime.intValue() + order.getTime());
						dataDao.updateObject(info);
					} else {
						info = new CoachStudentInfo();
						info.setCoachid(order.getCoachid());
						info.setHour(order.getTime());
						info.setMoney(order.getTotal());
						info.setStudentid(order.getStudentid());
						dataDao.addObject(info);
					}
					
					dataDao.updateObject(order);
				}
				
				/*
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
										mCouponCoach.setCouponrecordid(0);
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
			*/
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
		//重置教练开课状态
		//dataDao.callUpdatecoursestate();
		System.out.println("##################定时任务执行结束:"+sf.format(new Date())+"#################");
	}
	//如果学员没有评价教练，自动结算时，给教练好评
	public void addEvaluation(OrderInfo orderInfo,CuserInfo cuser,SuserInfo student){
		int orderid=orderInfo.getOrderid();
		int userid=student.getStudentid();
		StringBuffer ctaskhql = new StringBuffer();
		ctaskhql.append("from EvaluationInfo where order_id = :orderid and from_user = :userid and type = :type");
		String[] params = { "orderid", "userid", "type" };
		EvaluationInfo evaluationInfo = (EvaluationInfo) dataDao.getFirstObjectViaParam(ctaskhql.toString(), params, orderid, userid, 1);
		//EvaluationInfo evaluationInfo = ctaskService.getEvaluationInfo(CommonUtils.parseInt(orderid, 0), CommonUtils.parseInt(userid, 0), 1);
		if (evaluationInfo == null) {
			/*OrderInfo orderInfo = (OrderInfo) dataDao.getObjectById(OrderInfo.class, orderid);
			//OrderInfo orderInfo = (OrderInfo) ctaskService.getOrderInfo(CommonUtils.parseInt(orderid, 0));
			CuserInfo cuser = ctaskService.getCoachInfoById(orderInfo.getCoachid());
			SuserInfo student = ctaskService.getSuserInfoBysuserid(orderInfo.getStudentid());*/
			if (orderInfo != null) {
				EvaluationInfo evaluation = new EvaluationInfo();
				evaluation.setScore1(5);
				evaluation.setScore2(5);
				evaluation.setScore3(5);
				evaluation.setContent("好评!");
				evaluation.setOrder_id(orderid);
				evaluation.setType(1);
				evaluation.setFrom_user(userid);
				evaluation.setTo_user(orderInfo.getCoachid());
				evaluation.setAddtime(new Date());
				dataDao.addObject(evaluation);
				// 增加记录
				OrderRecordInfo orderRecord = new OrderRecordInfo();
				orderRecord.setAddtime(new Date());
				orderRecord.setEvaluationid(evaluation.getEvaluationid());
				orderRecord.setOperation(6);
				orderRecord.setOrderid(evaluation.getOrder_id());
				orderRecord.setUserid(evaluation.getFrom_user());
				dataDao.addObject(orderRecord);
				
				//评价后更新教练的星级
				float score=0;
				if(cuser!=null){
					if(cuser.getScore()<=0){
						score=5.0f ;//5分好评
					}else{
						score=(15.0f + cuser.getScore())/4 ;
					}
					cuser.setScore(score);
					dataDao.updateObject(cuser);
				}
			} 
		}
	}
}
