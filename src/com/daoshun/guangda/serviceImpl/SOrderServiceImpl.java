package com.daoshun.guangda.serviceImpl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.daoshun.common.ApplePushUtil;
import com.daoshun.common.CommonUtils;
import com.daoshun.common.Constant;
import com.daoshun.common.DeviceType;
import com.daoshun.common.OrderState;
import com.daoshun.common.PayType;
import com.daoshun.common.PushtoSingle;
import com.daoshun.common.QueryResult;
import com.daoshun.guangda.NetData.ComplaintNetData;
import com.daoshun.guangda.pojo.BalanceCoachInfo;
import com.daoshun.guangda.pojo.BalanceStudentInfo;
import com.daoshun.guangda.pojo.CBookTimeInfo;
import com.daoshun.guangda.pojo.CoachStudentInfo;
import com.daoshun.guangda.pojo.ComplaintBookInfo;
import com.daoshun.guangda.pojo.ComplaintInfo;
import com.daoshun.guangda.pojo.ComplaintSetInfo;
import com.daoshun.guangda.pojo.CouponCoach;
import com.daoshun.guangda.pojo.CouponRecord;
import com.daoshun.guangda.pojo.CsubjectInfo;
import com.daoshun.guangda.pojo.CuserInfo;
import com.daoshun.guangda.pojo.EvaluationInfo;
import com.daoshun.guangda.pojo.OrderInfo;
import com.daoshun.guangda.pojo.OrderNotiRecord;
import com.daoshun.guangda.pojo.OrderPrice;
import com.daoshun.guangda.pojo.OrderRecordInfo;
import com.daoshun.guangda.pojo.SuserInfo;
import com.daoshun.guangda.pojo.SystemSetInfo;
import com.daoshun.guangda.pojo.UserPushInfo;
import com.daoshun.guangda.service.ICscheduleService;
import com.daoshun.guangda.service.ISOrderService;

@Service("sorderService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class SOrderServiceImpl extends BaseServiceImpl implements ISOrderService {
	@Resource
	public ICscheduleService cscheduleService;
	@Override
	public List<ComplaintNetData> getComplaintToMy(String studentid, String pagenum) {
		List<ComplaintNetData> complaintNetDatalist = new ArrayList<ComplaintNetData>();
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append(" select distinct order_id from ComplaintInfo where to_user =: studentid ");
		cuserhql.append(" order by addtime desc ");
		String[] params = { "studentid" };
		List<String> list = (List<String>) dataDao.pageQueryViaParam(cuserhql.toString(), Constant.USERLIST_SIZE, CommonUtils.parseInt(pagenum, 0) + 1, params, CommonUtils.parseInt(studentid, 0));
		if (list != null && list.size() > 0) {
			for (String orderid : list) {
				ComplaintNetData complaintNetData = new ComplaintNetData();
				OrderInfo order = dataDao.getObjectById(OrderInfo.class, CommonUtils.parseInt(orderid, 0));
				CuserInfo cuser = dataDao.getObjectById(CuserInfo.class, order.getCoachid());
				if (cuser != null) {
					complaintNetData.setCoachid(cuser.getCoachid());
					if (cuser.getRealname() != null) {
						complaintNetData.setName(cuser.getRealname());
					}
					complaintNetData.setScore(cuser.getScore());
					complaintNetData.setPhone(cuser.getPhone());
					if (cuser.getCoach_cardnum() != null) {
						complaintNetData.setStudentcardnum(cuser.getCoach_cardnum());
					}
					complaintNetData.setStarttime(order.getStart_time());
					complaintNetData.setEndtime(order.getEnd_time());
					StringBuffer cuserhql1 = new StringBuffer();
					cuserhql1.append(" from ComplaintInfo where order_id =: order_id ");
					cuserhql1.append("order by addtime desc");
					String[] params1 = { "order_id" };
					List<ComplaintInfo> complaintlist = (List<ComplaintInfo>) dataDao.getObjectsViaParam(cuserhql1.toString(), params1, CommonUtils.parseInt(orderid, 0));
					if (complaintlist != null && complaintlist.size() > 0) {
						complaintNetData.setContentlist(complaintlist);
					}
					complaintNetDatalist.add(complaintNetData);
				}
			}
		}
		return complaintNetDatalist;
	}

	@Override
	public int getComplaintToMyMore(String studentid, String pagenum) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append(" select distinct order_id from ComplaintInfo where to_user =: studentid ");
		cuserhql.append(" order by addtime desc ");
		String[] params = { "studentid" };
		List<String> list = (List<String>) dataDao.pageQueryViaParam(cuserhql.toString(), Constant.USERLIST_SIZE, CommonUtils.parseInt(pagenum, 0) + 2, params, CommonUtils.parseInt(studentid, 0));
		if (list.size() == 0) {
			return 0;
		} else {
			return 1;
		}
	}

	@Override
	public List<ComplaintNetData> getMyComplaint(String studentid, String pagenum) {
		List<ComplaintNetData> complaintNetDatalist = new ArrayList<ComplaintNetData>();
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append(" select distinct order_id from ComplaintInfo where from_user =:studentid and type = 1");
		cuserhql.append(" order by addtime desc ");
		String[] params = { "studentid" };
		List<Object> list = (List<Object>) dataDao.pageQueryViaParam(cuserhql.toString(), Constant.USERLIST_SIZE, CommonUtils.parseInt(pagenum, 0) + 1, params, CommonUtils.parseInt(studentid, 0));
		if (list != null && list.size() > 0) {
			for (Object orderid : list) {
				ComplaintNetData complaintNetData = new ComplaintNetData();
				OrderInfo order = dataDao.getObjectById(OrderInfo.class, CommonUtils.parseInt(orderid.toString(), 0));
				CuserInfo cuser = dataDao.getObjectById(CuserInfo.class, order.getCoachid());
				if (cuser != null) {
					complaintNetData.setCoachid(cuser.getCoachid());
					if (cuser.getRealname() != null) {
						complaintNetData.setName(cuser.getRealname());
					}
					complaintNetData.setScore(cuser.getScore());
					complaintNetData.setPhone(cuser.getPhone());
					complaintNetData.setCoachavatar(getFilePathById(cuser.getAvatar()));
					if (cuser.getCoach_cardnum() != null) {
						complaintNetData.setStudentcardnum(cuser.getCoach_cardnum());
					}
					complaintNetData.setStarttime(order.getStart_time());
					complaintNetData.setEndtime(order.getEnd_time());
					StringBuffer cuserhql1 = new StringBuffer();
					cuserhql1.append(" from ComplaintInfo where order_id =:order_id ");
					cuserhql1.append("order by addtime desc");
					String[] params1 = { "order_id" };
					List<ComplaintInfo> complaintlist = (List<ComplaintInfo>) dataDao.getObjectsViaParam(cuserhql1.toString(), params1, CommonUtils.parseInt(orderid.toString(), 0));
					if (complaintlist != null && complaintlist.size() > 0) {
						complaintNetData.setContentlist(complaintlist);
					}
					complaintNetDatalist.add(complaintNetData);
				}
			}
		}
		return complaintNetDatalist;
	}

	@Override
	public int getMyComplaintMore(String studentid, String pagenum) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append(" select distinct order_id from ComplaintInfo where from_user =:studentid ");
		cuserhql.append(" order by addtime desc ");
		String[] params = { "studentid" };
		List<String> list = (List<String>) dataDao.pageQueryViaParam(cuserhql.toString(), Constant.USERLIST_SIZE, CommonUtils.parseInt(pagenum, 0) + 2, params, CommonUtils.parseInt(studentid, 0));
		if (list.size() == 0) {
			return 0;
		} else {
			return 1;
		}
	}

	/**
	 * 获取待评价订单 未完成订单满足条件： studentid 为请求者ID and (studentstate = 2 or ( studentstate = 0 and 订单的结束时间已经过去 and 订单没有投诉))
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderInfo> getWaitEvaluationOrder(String studentid, String pagenum) {

		// 首先查询出订单相关的几个时间配置
		String hqlset = "from SystemSetInfo where 1 = 1";
		SystemSetInfo setInfo = (SystemSetInfo) dataDao.getFirstObjectViaParam(hqlset, null);
		int s_can_down = 60;// 距离订单结束之后可以确认下车的时间默认60分钟

		if (setInfo != null) {
			if (setInfo.getS_can_down() != null && setInfo.getS_can_down() != 0)
				s_can_down = setInfo.getS_can_down();
		}

		Calendar overC = Calendar.getInstance();
		overC.add(Calendar.MINUTE, -s_can_down);

		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from OrderInfo  a where a.studentid =:studentid and (a.studentstate = 2 or (a.studentstate = 0 and (select count(*) from ComplaintInfo c where c.order_id"
				+ " = a.orderid and c.type = 1 and c.state = 0) = 0 and a.end_time < :overtime))  order by a.start_time desc");
		String[] params = { "studentid", "overtime" };
		List<OrderInfo> orderList = new ArrayList<OrderInfo>();
		orderList.addAll((List<OrderInfo>) dataDao.pageQueryViaParam(cuserhql.toString(), Constant.ORDERLIST_SIZE, CommonUtils.parseInt(pagenum, 0) + 1, params, CommonUtils.parseInt(studentid, 0),
				overC.getTime()));

		for (OrderInfo order : orderList) {
			StringBuffer cuserhql1 = new StringBuffer();
			cuserhql1.append("from OrderPrice where orderid =:orderid ");
			String[] params1 = { "orderid" };
			List<OrderPrice> orderpricelist = (List<OrderPrice>) dataDao.getObjectsViaParam(cuserhql1.toString(), params1, order.getOrderid());
			if (orderpricelist != null && orderpricelist.size() > 0) {
				order.setOrderprice(orderpricelist);
				OrderPrice op=orderpricelist.get(0);
				if(op!=null){
					//设置科目
					order.setSubjectname(op.getSubject());
				}
			}
			CuserInfo user = dataDao.getObjectById(CuserInfo.class, order.getCoachid());
			if (user != null) {
				order.setCuserinfo(user);
				//设置牌照
				order.setCarlicense(user.getCarlicense());
				/*String subjectHql="from OrderPrice where orderid =:orderid";
				String[] params1 = { "orderid" };
				List<OrderPrice> orderpricelist = (List<OrderPrice>) dataDao.getObjectsViaParam(subjectHql.toString(), params1, order.getOrderid());
				if (orderpricelist != null && orderpricelist.size() > 0) {
					OrderPrice op=orderpricelist.get(0);
					if(op!=null){
						//设置科目
						order.setSubjectname(op.getSubject());
					}
				}*/
				/*StringBuffer subHql = new StringBuffer();
				subHql.append("from CsubjectInfo where subjectid =:subjectid ");
				String[] params3 = { "subjectid" };
				CsubjectInfo subjectInfo = (CsubjectInfo) dataDao.getFirstObjectViaParam(subHql.toString(), params3, user.getSubjectdef());
				if(subjectInfo!=null){
					//设置科目
					order.setSubjectname(subjectInfo.getSubjectname());
				}*/
			}

			order.setHours(-2);// 学车已经完成

			// 是否可以投诉
			order.setCan_complaint(1);
			order.setNeed_uncomplaint(0);// 订单肯定没有投诉
			order.setCan_cancel(OrderState.CANNOT_CANCEL);// 肯定不可以取消
			order.setCan_up(0);// 不可以再确认上车
			order.setCan_down(0);
			order.setCan_comment(1);
		}
		return orderList;
	}
	/**
	 * 获取待评价订单 未完成订单满足条件： studentid 为请求者ID and (studentstate = 2 or ( studentstate = 0 and 订单的结束时间已经过去 and 订单没有投诉))
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderInfo> getWaitDealwithOrder(String studentid, String pagenum) {

		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from OrderInfo  a where a.studentid =:studentid and a.coachstate=5 ");
		String[] params = { "studentid"};
		List<OrderInfo> orderList = new ArrayList<OrderInfo>();
		orderList.addAll((List<OrderInfo>) dataDao.pageQueryViaParam(cuserhql.toString(), Constant.ORDERLIST_SIZE, CommonUtils.parseInt(pagenum, 0) + 1, params, CommonUtils.parseInt(studentid, 0)));
		for (OrderInfo order : orderList) {
			StringBuffer cuserhql1 = new StringBuffer();
			cuserhql1.append("from OrderPrice where orderid =:orderid ");
			String[] params1 = { "orderid" };
			List<OrderPrice> orderpricelist = (List<OrderPrice>) dataDao.getObjectsViaParam(cuserhql1.toString(), params1, order.getOrderid());
			if (orderpricelist != null && orderpricelist.size() > 0) {
				order.setOrderprice(orderpricelist);
			}
			CuserInfo user = dataDao.getObjectById(CuserInfo.class, order.getCoachid());
			if (user != null) {
				order.setCuserinfo(user);
				//设置牌照
				order.setCarlicense(user.getCarlicense());
				StringBuffer subHql = new StringBuffer();
				subHql.append("from CsubjectInfo where subjectid =:subjectid ");
				String[] params3 = { "subjectid" };
				CsubjectInfo subjectInfo = (CsubjectInfo) dataDao.getFirstObjectViaParam(subHql.toString(), params3, user.getSubjectdef());
				if(subjectInfo!=null){
					//设置科目
					order.setSubjectname(subjectInfo.getSubjectname());
				}
			}
			order.setHours(-6);// 学车已经完成
			// 是否可以投诉
			order.setCan_complaint(0);
			order.setNeed_uncomplaint(0);// 订单肯定没有投诉
			order.setCan_cancel(OrderState.CANNOT_CANCEL);// 肯定不可以取消
			order.setCan_up(0);// 不可以再确认上车
			order.setCan_down(0);
			order.setCan_comment(0);
		}
		return orderList;
	}

	@Override
	public int getWaitEvaluationOrderMore(String studentid, String pagenum) {
		// 首先查询出订单相关的几个时间配置
		String hqlset = "from SystemSetInfo where 1 = 1";
		SystemSetInfo setInfo = (SystemSetInfo) dataDao.getFirstObjectViaParam(hqlset, null);
		int s_can_down = 60;// 距离订单结束之后可以确认下车的时间默认60分钟

		if (setInfo != null) {
			if (setInfo.getS_can_down() != null && setInfo.getS_can_down() != 0)
				s_can_down = setInfo.getS_can_down();
		}

		Calendar overC = Calendar.getInstance();
		overC.add(Calendar.MINUTE, -s_can_down);

		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from OrderInfo  a where a.studentid =:studentid and (a.studentstate = 2 or (a.studentstate = 0 and (select count(*) from ComplaintInfo c where c.order_id"
				+ " = a.orderid and c.type = 1 and c.state = 0) = 0 and a.end_time < :overtime))  order by a.start_time desc");
		String[] params = { "studentid", "overtime" };
		List<OrderInfo> orderList = (List<OrderInfo>) dataDao.pageQueryViaParam(cuserhql.toString(), Constant.ORDERLIST_SIZE, CommonUtils.parseInt(pagenum, 0) + 1, params,
				CommonUtils.parseInt(studentid, 0), overC.getTime());
		if (orderList == null || orderList.size() == 0) {
			return 0;
		} else {
			return 1;
		}
	}

	/**
	 * 获取未完成订单 未完成订单满足条件： studentid 为请求者ID and studentstate = 0 and ( 订单有投诉 or ( 订单无投诉 and 结束时间未过去 ) )
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderInfo> getUnCompleteOrder(String studentid, String pagenum) {
		// 首先查询出订单相关的几个时间配置
		String hqlset = "from SystemSetInfo where 1 = 1";
		SystemSetInfo setInfo = (SystemSetInfo) dataDao.getFirstObjectViaParam(hqlset, null);

		int time_cancel = 2880;// 距离订单开始前订单可以取消的时间 默认48小时
		
		int s_can_up = 30; // 距离订单开始时间前可以确认上车的时间 默认 30分钟
		int s_can_down = 60;// 距离订单结束之后可以确认下车的时间默认60分钟

		if (setInfo != null) {
			if (setInfo.getTime_cancel() != null && setInfo.getTime_cancel() != 0)
				time_cancel = setInfo.getTime_cancel();
			if (setInfo.getS_can_up() != null && setInfo.getS_can_up() != 0)
				s_can_up = setInfo.getS_can_up();
			if (setInfo.getS_can_down() != null && setInfo.getS_can_down() != 0)
				s_can_down = setInfo.getS_can_down();

		}
		time_cancel = 60;// 距离订单开始前订单可以取消的时间 默认60分钟
		Calendar now = Calendar.getInstance();
		
		Calendar nowCanDown = Calendar.getInstance();
		nowCanDown.add(Calendar.MINUTE, -s_can_down);
		StringBuffer cuserhql = new StringBuffer();
		List<OrderInfo> orderlist = new ArrayList<OrderInfo>();
		/*cuserhql.append("from OrderInfo a where a.studentid =:studentid and (a.studentstate in (0,4) and a.coachstate!=4) and ((select count(*) from ComplaintInfo c where c.order_id"
				+ " = a.orderid and c.type = 1 and c.state = 0) > 0 or ((select count(*) from ComplaintInfo c where c.order_id"
				+ " = a.orderid and c.type = 1 and c.state = 0) = 0 and  a.end_time > :now))  order by a.start_time asc");*/
		cuserhql.append("from OrderInfo a where a.studentid =:studentid and (a.studentstate in (0,4) and a.coachstate not in (4,5)) "
				+ "  and ((select count(*) from ComplaintInfo c where c.order_id "
				+ " = a.orderid and c.type = 1 and c.state = 0) = 0 and  a.end_time > :now)  order by a.start_time asc");
		String[] params = { "studentid", "now" };
		//System.out.println(cuserhql.toString());
		List<OrderInfo> list=(List<OrderInfo>) dataDao.pageQueryViaParam(cuserhql.toString(), Constant.ORDERLIST_SIZE, CommonUtils.parseInt(pagenum, 0) + 1, params, CommonUtils.parseInt(studentid, 0),
				nowCanDown.getTime());
		orderlist.addAll(list);
    
		for (OrderInfo order : orderlist) {
			CuserInfo user = dataDao.getObjectById(CuserInfo.class, order.getCoachid());
			if (user != null) {// 教练信息
				order.setCuserinfo(user);
				//设置教练的牌照
				order.setCarlicense(user.getCarlicense());
				String subjectHql="from OrderPrice where orderid =:orderid";
				String[] params1 = { "orderid" };
				List<OrderPrice> orderpricelist = (List<OrderPrice>) dataDao.getObjectsViaParam(subjectHql.toString(), params1, order.getOrderid());
				if (orderpricelist != null && orderpricelist.size() > 0) {
					OrderPrice op=orderpricelist.get(0);
					if(op!=null){
						//设置科目
						order.setSubjectname(op.getSubject());
					}
				}
				/*subHql.append("from CsubjectInfo where subjectid =:subjectid ");
				String[] params3 = { "subjectid" };
				CsubjectInfo subjectInfo = (CsubjectInfo) dataDao.getFirstObjectViaParam(subHql.toString(), params3, user.getSubjectdef());
				if(subjectInfo!=null){
					//设置科目
					order.setSubjectname(subjectInfo.getSubjectname());
				}*/
			}
			
			// 是否已经确认上车
			StringBuffer cuserhql1 = new StringBuffer();
			cuserhql1.append("from OrderRecordInfo where orderid =:orderid and userid =:userid and operation = 1");
			String[] params1 = { "orderid", "userid" };
			OrderRecordInfo orderRecord = (OrderRecordInfo) dataDao.getFirstObjectViaParam(cuserhql1.toString(), params1, order.getOrderid(), CommonUtils.parseInt(studentid, 0));

			// 是否已经确认下车
			StringBuffer cuserhql2 = new StringBuffer();
			cuserhql2.append("from OrderRecordInfo where orderid =:orderid and userid =:userid and operation = 2");
			String[] params2 = { "orderid", "userid" };
			OrderRecordInfo orderRecord2 = (OrderRecordInfo) dataDao.getFirstObjectViaParam(cuserhql2.toString(), params2, order.getOrderid(), CommonUtils.parseInt(studentid, 0));

			// 可以确认上车的时间点
			Calendar left = Calendar.getInstance();
			left.setTime(order.getStart_time());
			left.add(Calendar.MINUTE, -s_can_up);

			// 订单结束时间
			Calendar right = Calendar.getInstance();
			right.setTime(order.getEnd_time());

			// 可以确认下车的最晚时间
			Calendar right1 = Calendar.getInstance();
			right1.setTime(order.getEnd_time());
			right1.add(Calendar.MINUTE, s_can_down);

			// 订单显示即将开始
			if (orderRecord == null && now.getTime().before(order.getStart_time()) && now.after(left)) {
				order.setHours(0);
			} else if (now.before(left)) {// 订单显示距离开始时间
				long millisecond = order.getStart_time().getTime() - now.getTimeInMillis();
				order.setHours((int) (millisecond / 60000));
			} else if (orderRecord != null && orderRecord2 == null && now.before(right1)) {// 订单显示正在学车
				order.setHours(-1);
			} else if (orderRecord2 != null || now.after(right1)) {// 订单显示学车已经结束
				order.setHours(-2);
			} else if (orderRecord == null && now.getTime().after(order.getStart_time()) && now.getTime().before(order.getEnd_time())) {// 订单显示等待确认上车
				order.setHours(-3);
			} else if (orderRecord2 == null && now.getTime().after(order.getEnd_time()) && now.before(right1)) {// 订单显示等待确认下车
				order.setHours(-4);
			} else {// 订单显示学车已经结束
				order.setHours(-2);
			}

			StringBuffer cuserhql3 = new StringBuffer();
			cuserhql3.append("from OrderPrice where orderid =:orderid ");
			String[] params3 = { "orderid" };
			List<OrderPrice> orderpricelist = (List<OrderPrice>) dataDao.getObjectsViaParam(cuserhql3.toString(), params3, order.getOrderid());
			if (orderpricelist != null && orderpricelist.size() > 0) {
				order.setOrderprice(orderpricelist);
			}
			// 是否可以投诉
			if (orderRecord2 != null || now.after(right1)) {// 如果订单已经确认下车过了，或者时间已经过去了
				order.setCan_complaint(1);//
			} else {
				order.setCan_complaint(0);//
			}

			// 是否需要取消投诉
			String hql2 = "from ComplaintInfo c where c.order_id =:order_id and c.type = 1 and c.state = 0";
			String[] p2 = { "order_id" };
			List<ComplaintInfo> cList = (List<ComplaintInfo>) dataDao.getObjectsViaParam(hql2, p2, order.getOrderid());
			if (cList != null && cList.size() > 0) {
				order.setNeed_uncomplaint(1);
			} else {
				order.setNeed_uncomplaint(0);
			}
			// 是否可以取消
			/*if (order.getCancancel() == 1) {// 不可取消
				order.setCan_cancel(OrderState.CANNOT_CANCEL);
			} else {// 可以取消
				Calendar now2 = Calendar.getInstance();
				now2.add(Calendar.MINUTE, time_cancel);
				if (now2.getTime().before(order.getStart_time())) {
					order.setCan_cancel(OrderState.CAN_CANCEL);
				} else {
					order.setCan_cancel(OrderState.CANNOT_CANCEL);
				}
			}
			*/
			/*if (order.getCancancel() == 1) {// 不可取消
				order.setCan_cancel(OrderState.CAN_CANCEL);
			} else {// 可以取消
				Calendar now2 = Calendar.getInstance();
				now2.add(Calendar.MINUTE, time_cancel);
				if (now2.getTime().before(order.getStart_time())) {
					order.setCan_cancel(OrderState.CANNOT_CANCEL);
				} else {
					order.setCan_cancel(OrderState.CAN_CANCEL);
				}
			}*/
			Calendar now2 = Calendar.getInstance();
			now2.add(Calendar.MINUTE, time_cancel);
			if (now2.getTime().before(order.getStart_time())) {
				order.setCan_cancel(OrderState.CAN_CANCEL);
			} else {
				order.setCan_cancel(OrderState.CANNOT_CANCEL);//不能取消 0
			}

			if (orderRecord != null || orderRecord2 != null) {// 确认下车过或者确认上车过
				order.setCan_up(0);// 不可以再确认上车
			} else {

				if (now.after(left) && now.before(right)) {
					order.setCan_up(1);
				} else {
					order.setCan_up(0);
				}
			}

			if (orderRecord2 != null) {// 已经确认下车过了
				order.setCan_down(0);
			} else {
				if (orderRecord != null) {
					if (now.before(right1)) {
						order.setCan_down(1);
					} else {
						order.setCan_down(0);
					}
				} else {
					if (now.after(right) && now.before(right1)) {
						order.setCan_down(1);
					} else {
						order.setCan_down(0);
					}
				}
			}

			// 是否可以评论
			String hqlC = "from EvaluationInfo where order_id = :order_id and type = 1";
			String[] paramsC = { "order_id" };
			EvaluationInfo eva = (EvaluationInfo) dataDao.getFirstObjectViaParam(hqlC, paramsC, order.getOrderid());

			if (eva == null && (orderRecord2 != null || now.after(right1))) {
				order.setCan_comment(1);
			} else {
				order.setCan_comment(0);
			}
			
		}

		return orderlist;
	}
	@Override
	public int getUnCompleteOrderMore(String studentid, String pagenum) {
		// 首先查询出订单相关的几个时间配置
		String hqlset = "from SystemSetInfo where 1 = 1";
		SystemSetInfo setInfo = (SystemSetInfo) dataDao.getFirstObjectViaParam(hqlset, null);
		int s_can_down = 60;// 距离订单结束之后可以确认下车的时间默认60分钟

		if (setInfo != null) {
			if (setInfo.getS_can_down() != null && setInfo.getS_can_down() != 0)
				s_can_down = setInfo.getS_can_down();
		}
		Calendar now = Calendar.getInstance();
		now.add(Calendar.MINUTE, -s_can_down);
		StringBuffer cuserhql = new StringBuffer();

		cuserhql.append("from OrderInfo a where a.studentid =:studentid and (a.studentstate in (0,4) and a.coachstate not in (4,5)) and "
				+ "  ((select count(*) as ccount from ComplaintInfo c where c.order_id"
				+ " = a.orderid and c.type = 1 and state = 0) = 0 and  a.end_time > :now)) order by start_time desc");
		String[] params = { "studentid", "now" };
		List<OrderInfo> orderlist = (List<OrderInfo>) dataDao.pageQueryViaParam(cuserhql.toString(), Constant.ORDERLIST_SIZE, CommonUtils.parseInt(pagenum, 0) + 1, params,
				CommonUtils.parseInt(studentid, 0), now.getTime());
		if (orderlist == null || orderlist.size() == 0) {
			return 0;
		} else {
			return 1;
		}
	}
	/**
	 * 获取被投诉的订单【待处理】
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderInfo> getComplaintOrder(String studentid, String pagenum) {
		// 首先查询出订单相关的几个时间配置
		String hqlset = "from SystemSetInfo where 1 = 1";
		SystemSetInfo setInfo = (SystemSetInfo) dataDao.getFirstObjectViaParam(hqlset, null);

		int time_cancel = 2880;// 距离订单开始前订单可以取消的时间 默认48小时
		int s_can_up = 30; // 距离订单开始时间前可以确认上车的时间 默认 30分钟
		int s_can_down = 60;// 距离订单结束之后可以确认下车的时间默认60分钟

		if (setInfo != null) {
			if (setInfo.getTime_cancel() != null && setInfo.getTime_cancel() != 0)
				time_cancel = setInfo.getTime_cancel();
			if (setInfo.getS_can_up() != null && setInfo.getS_can_up() != 0)
				s_can_up = setInfo.getS_can_up();
			if (setInfo.getS_can_down() != null && setInfo.getS_can_down() != 0)
				s_can_down = setInfo.getS_can_down();

		}
		time_cancel = 60;
		Calendar now = Calendar.getInstance();

		Calendar nowCanDown = Calendar.getInstance();
		nowCanDown.add(Calendar.MINUTE, -s_can_down);
		StringBuffer cuserhql = new StringBuffer();
		List<OrderInfo> orderlist = new ArrayList<OrderInfo>();
		cuserhql.append("from OrderInfo a where a.studentid =:studentid and a.coachstate=5 or (select count(*) from ComplaintInfo c where c.order_id"
				+ " = a.orderid and c.type = 1 and c.state = 0) > 0 "
				+ "  order by a.start_time desc");
		String[] params = { "studentid" };
		//System.out.println(cuserhql.toString());
		orderlist.addAll((List<OrderInfo>) dataDao.pageQueryViaParam(cuserhql.toString(), Constant.ORDERLIST_SIZE, CommonUtils.parseInt(pagenum, 0) + 1, params, CommonUtils.parseInt(studentid, 0)));

		for (OrderInfo order : orderlist) {
			CuserInfo user = dataDao.getObjectById(CuserInfo.class, order.getCoachid());
			if (user != null) {// 教练信息
				order.setCuserinfo(user);
				//设置教练的牌照
				order.setCarlicense(user.getCarlicense());
			}
			
			
			
			// 是否已经确认上车
			StringBuffer cuserhql1 = new StringBuffer();
			cuserhql1.append("from OrderRecordInfo where orderid =:orderid and userid =:userid and operation = 1");
			String[] params1 = { "orderid", "userid" };
			OrderRecordInfo orderRecord = (OrderRecordInfo) dataDao.getFirstObjectViaParam(cuserhql1.toString(), params1, order.getOrderid(), CommonUtils.parseInt(studentid, 0));

			// 是否已经确认下车
			StringBuffer cuserhql2 = new StringBuffer();
			cuserhql2.append("from OrderRecordInfo where orderid =:orderid and userid =:userid and operation = 2");
			String[] params2 = { "orderid", "userid" };
			OrderRecordInfo orderRecord2 = (OrderRecordInfo) dataDao.getFirstObjectViaParam(cuserhql2.toString(), params2, order.getOrderid(), CommonUtils.parseInt(studentid, 0));

			// 可以确认上车的时间点
			Calendar left = Calendar.getInstance();
			left.setTime(order.getStart_time());
			left.add(Calendar.MINUTE, -s_can_up);

			// 订单结束时间
			Calendar right = Calendar.getInstance();
			right.setTime(order.getEnd_time());

			// 可以确认下车的最晚时间
			Calendar right1 = Calendar.getInstance();
			right1.setTime(order.getEnd_time());
			right1.add(Calendar.MINUTE, s_can_down);

			// 订单显示即将开始
			/*if (orderRecord == null && now.getTime().before(order.getStart_time()) && now.after(left)) {
				order.setHours(0);
			} else if (now.before(left)) {// 订单显示距离开始时间
				long millisecond = order.getStart_time().getTime() - now.getTimeInMillis();
				order.setHours((int) (millisecond / 60000));
			} else if (orderRecord != null && orderRecord2 == null && now.before(right1)) {// 订单显示正在学车
				order.setHours(-1);
			} else if (orderRecord2 != null || now.after(right1)) {// 订单显示学车已经结束
				order.setHours(-2);
			} else if (orderRecord == null && now.getTime().after(order.getStart_time()) && now.getTime().before(order.getEnd_time())) {// 订单显示等待确认上车
				order.setHours(-3);
			} else if (orderRecord2 == null && now.getTime().after(order.getEnd_time()) && now.before(right1)) {// 订单显示等待确认下车
				order.setHours(-4);
			} else {// 订单显示学车已经结束
				order.setHours(-2);
			}*/
			//学员取消教练没有操作
			if(order.getCoachstate()==5){
				order.setHours(-6);
			}else{
				order.setHours(-5);
			}
			StringBuffer cuserhql3 = new StringBuffer();
			cuserhql3.append("from OrderPrice where orderid =:orderid ");
			String[] params3 = { "orderid" };
			List<OrderPrice> orderpricelist = (List<OrderPrice>) dataDao.getObjectsViaParam(cuserhql3.toString(), params3, order.getOrderid());
			if (orderpricelist != null && orderpricelist.size() > 0) {
				order.setOrderprice(orderpricelist);
			}
			// 是否可以投诉
			order.setCan_complaint(OrderState.CANNOT_COMPLAINT);//不能投诉
			

			// 是否需要取消投诉
			String hql2 = "from ComplaintInfo c where c.order_id =:order_id and c.type = 1 and c.state = 0";
			String[] p2 = { "order_id" };
			List<ComplaintInfo> cList = (List<ComplaintInfo>) dataDao.getObjectsViaParam(hql2, p2, order.getOrderid());
			if (cList != null && cList.size() > 0) {
				order.setNeed_uncomplaint(1);
			} else {
				order.setNeed_uncomplaint(0);
			}
			// 是否可以取消
			if (order.getCancancel() == 1) {// 不可取消
				order.setCan_cancel(0);
			} else {// 可以取消
				Calendar now2 = Calendar.getInstance();
				now2.add(Calendar.MINUTE, time_cancel);
				if (now2.getTime().before(order.getStart_time())) {
					order.setCan_cancel(1);
				} else {
					order.setCan_cancel(0);
				}
			}
			order.setCan_up(0);// 不可以再确认上车
			/*if (orderRecord != null || orderRecord2 != null) {// 确认下车过或者确认上车过
				order.setCan_up(0);// 不可以再确认上车
			} else {

				if (now.after(left) && now.before(right)) {
					order.setCan_up(1);
				} else {
					order.setCan_up(0);
				}
			}*/
			order.setCan_down(0);
			/*if (orderRecord2 != null) {// 已经确认下车过了
				order.setCan_down(0);
			} else {
				if (orderRecord != null) {
					if (now.before(right1)) {
						order.setCan_down(1);
					} else {
						order.setCan_down(0);
					}
				} else {
					if (now.after(right) && now.before(right1)) {
						order.setCan_down(1);
					} else {
						order.setCan_down(0);
					}
				}
			}*/
			// 是否可以评论
			order.setCan_comment(OrderState.CANNOT_COMMENT);//不能评论
		}

		return orderlist;
	}
	
	@Override
	public int getComplaintOrderMore(String studentid, String pagenum) {
		// 首先查询出订单相关的几个时间配置
		String hqlset = "from SystemSetInfo where 1 = 1";
		SystemSetInfo setInfo = (SystemSetInfo) dataDao.getFirstObjectViaParam(hqlset, null);
		int s_can_down = 60;// 距离订单结束之后可以确认下车的时间默认60分钟

		if (setInfo != null) {
			if (setInfo.getS_can_down() != null && setInfo.getS_can_down() != 0)
				s_can_down = setInfo.getS_can_down();
		}
		Calendar now = Calendar.getInstance();
		now.add(Calendar.MINUTE, -s_can_down);
		StringBuffer cuserhql = new StringBuffer();

		cuserhql.append("from OrderInfo a where a.studentid =:studentid and (a.studentstate in (0,4) and a.coachstate!=4) and (select count(*) as ccount from ComplaintInfo c where c.order_id"
				+ " = a.orderid and c.type = 1 and state = 0) > 0 "
				+ "  order by start_time asc");
		String[] params = { "studentid" };
		List<OrderInfo> orderlist = (List<OrderInfo>) dataDao.pageQueryViaParam(cuserhql.toString(), Constant.ORDERLIST_SIZE, CommonUtils.parseInt(pagenum, 0) + 1, params,
				CommonUtils.parseInt(studentid, 0));
		if (orderlist == null || orderlist.size() == 0) {
			return 0;
		} else {
			return 1;
		}
	}
	

	/**
	 * 取得已经完成的订单列表
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderInfo> getCompleteOrder(String studentid, String pagenum) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from OrderInfo where studentid =:studentid ");
		cuserhql.append(" and studentstate =3 order by start_time desc ");
		String[] params = { "studentid" };
		List<OrderInfo> orderlist = new ArrayList<OrderInfo>();
		orderlist.addAll((List<OrderInfo>) dataDao.pageQueryViaParam(cuserhql.toString(), Constant.ORDERLIST_SIZE, CommonUtils.parseInt(pagenum, 0) + 1, params, CommonUtils.parseInt(studentid, 0)));

		for (OrderInfo order : orderlist) {
			CuserInfo user = dataDao.getObjectById(CuserInfo.class, order.getCoachid());
			if (user != null) {
				order.setCuserinfo(user);
				//设置车的牌照
				order.setCarlicense(user.getCarlicense());
				String subjectHql="from OrderPrice where orderid =:orderid";
				String[] params1 = { "orderid" };
				List<OrderPrice> orderpricelist = (List<OrderPrice>) dataDao.getObjectsViaParam(subjectHql.toString(), params1, order.getOrderid());
				if (orderpricelist != null && orderpricelist.size() > 0) {
					OrderPrice op=orderpricelist.get(0);
					if(op!=null){
						//设置科目
						order.setSubjectname(op.getSubject());
					}
				}
				/*StringBuffer subHql = new StringBuffer();
				subHql.append("from CsubjectInfo where subjectid =:subjectid ");
				String[] params3 = { "subjectid" };
				CsubjectInfo subjectInfo = (CsubjectInfo) dataDao.getFirstObjectViaParam(subHql.toString(), params3, user.getSubjectdef());
				if(subjectInfo!=null){
					//设置科目
					order.setSubjectname(subjectInfo.getSubjectname());
				}*/
			}
			StringBuffer cuserhql1 = new StringBuffer();
			cuserhql1.append("from OrderPrice where orderid =:orderid ");
			String[] params1 = { "orderid" };
			List<OrderPrice> orderpricelist = (List<OrderPrice>) dataDao.getObjectsViaParam(cuserhql1.toString(), params1, order.getOrderid());
			if (orderpricelist != null && orderpricelist.size() > 0) {
				order.setOrderprice(orderpricelist);
			}

			order.setHours(-2);// 学车已经完成

			// 是否可以投诉
			order.setCan_complaint(OrderState.CAN_COMPLAINT);
			String hql2 = "from ComplaintInfo c where c.order_id =:order_id and c.type = 1 and c.state = 0";
			String[] p2 = { "order_id" };
			List<ComplaintInfo> cList = (List<ComplaintInfo>) dataDao.getObjectsViaParam(hql2, p2, order.getOrderid());
			if (cList != null && cList.size() > 0) {
				order.setNeed_uncomplaint(1);
			} else {
				order.setNeed_uncomplaint(0);
			}
			order.setCan_cancel(0);// 肯定不可以取消
			order.setCan_up(0);// 不可以再确认上车
			order.setCan_down(0);
			// 是否可以评论
			String hqlC = "from EvaluationInfo where order_id = :order_id and type = 1";
			String[] paramsC = { "order_id" };
			EvaluationInfo eva = (EvaluationInfo) dataDao.getFirstObjectViaParam(hqlC, paramsC, order.getOrderid());

			if (eva == null) {
				order.setCan_comment(1);
			} else {
				order.setCan_comment(0);
			}
		}

		return orderlist;
	}

	@Override
	public int getCompleteOrderMore(String studentid, String pagenum) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from OrderInfo where studentid =:studentid ");
		cuserhql.append(" and studentstate =3 order by start_time desc ");
		String[] params = { "studentid" };
		List<OrderInfo> orderlist = (List<OrderInfo>) dataDao.pageQueryViaParam(cuserhql.toString(), Constant.ORDERLIST_SIZE, CommonUtils.parseInt(pagenum, 0) + 1, params,
				CommonUtils.parseInt(studentid, 0));
		if (orderlist == null || orderlist.size() == 0) {
			return 0;
		} else {
			return 1;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public OrderInfo getCompleteOrder(String orderid) {

		OrderInfo orderinfo = dataDao.getObjectById(OrderInfo.class, CommonUtils.parseInt(orderid, 0));
		if (orderinfo != null) {
			// 首先查询出订单相关的几个时间配置
			String hqlset = "from SystemSetInfo where 1 = 1";
			SystemSetInfo setInfo = (SystemSetInfo) dataDao.getFirstObjectViaParam(hqlset, null);

			int time_cancel = 2880;// 距离订单开始前订单可以取消的时间 默认48小时
			int s_can_up = 30; // 距离订单开始时间前可以确认上车的时间 默认 30分钟
			int s_can_down = 60;// 距离订单结束之后可以确认下车的时间默认60分钟

			if (setInfo != null) {
				if (setInfo.getTime_cancel() != null && setInfo.getTime_cancel() != 0)
					time_cancel = setInfo.getTime_cancel();
				if (setInfo.getS_can_up() != null && setInfo.getS_can_up() != 0)
					s_can_up = setInfo.getS_can_up();
				if (setInfo.getS_can_down() != null && setInfo.getS_can_down() != 0)
					s_can_down = setInfo.getS_can_down();

			}
			time_cancel = 10;// 距离订单开始前订单可以取消的时间 默认10分钟
			Calendar now = Calendar.getInstance();

			Calendar over = Calendar.getInstance();
			over.setTime(orderinfo.getEnd_time());
			over.add(Calendar.MINUTE, s_can_down);

			CuserInfo user = dataDao.getObjectById(CuserInfo.class, orderinfo.getCoachid());
			if (user != null) {// 教练信息
				orderinfo.setCuserinfo(user);
			}
			StringBuffer cuserhql1 = new StringBuffer();
			cuserhql1.append("from OrderPrice where orderid =:orderid");
			String[] params1 = { "orderid" };
			List<OrderPrice> orderpricelist = (List<OrderPrice>) dataDao.getObjectsViaParam(cuserhql1.toString(), params1, CommonUtils.parseInt(orderid, 0));
			if (orderpricelist != null && orderpricelist.size() > 0) {// 价格信息设置
				orderinfo.setOrderprice(orderpricelist);
			}

			String hql2 = "from ComplaintInfo c where c.order_id =:order_id and c.type = 1 and c.state = 0";
			String[] p2 = { "order_id" };
			List<ComplaintInfo> cList = (List<ComplaintInfo>) dataDao.getObjectsViaParam(hql2, p2, orderinfo.getOrderid());

			if (orderinfo.getStudentstate() == 3) {// 已经完成订单：已评价
				// 是否可以投诉
				orderinfo.setCan_complaint(1);
				// 是否可以评论
				/*String hqlC = "from EvaluationInfo where order_id = :order_id and type = 1";
				String[] paramsC = { "order_id" };
				EvaluationInfo eva = (EvaluationInfo) dataDao.getFirstObjectViaParam(hqlC, paramsC, orderinfo.getOrderid());*/

				orderinfo.setHours(-2);// 学车已经完成

				if (cList != null && cList.size() > 0) {
					orderinfo.setNeed_uncomplaint(1);
				} else {
					orderinfo.setNeed_uncomplaint(0);
				}
				orderinfo.setCan_cancel(OrderState.CANNOT_CANCEL);// 肯定不可以取消
				orderinfo.setCan_up(0);// 不可以再确认上车
				orderinfo.setCan_down(0);
				orderinfo.setCan_complaint(OrderState.CANNOT_COMPLAINT);
				orderinfo.setCan_comment(OrderState.CANNOT_COMMENT);
				
			} else if (orderinfo.getStudentstate() == 2 || (orderinfo.getStudentstate() == 0 && (cList == null || cList.size() == 0) && now.after(over))) 
			{// 待评价订单
				orderinfo.setHours(-2);// 学车已经完成
				// 是否可以投诉
				orderinfo.setCan_complaint(1);
				orderinfo.setNeed_uncomplaint(0);// 订单肯定没有投诉
				orderinfo.setCan_cancel(1);// 肯定不可以取消
				orderinfo.setCan_up(0);// 不可以再确认上车
				orderinfo.setCan_down(0);
				orderinfo.setCan_comment(1);
				// 是否可以投诉
				orderinfo.setCan_complaint(1);
				orderinfo.setCan_cancel(OrderState.CANNOT_CANCEL);
			} else if((orderinfo.getStudentstate() == 0 || orderinfo.getStudentstate()==4) 
					&& orderinfo.getCoachstate()!=4 && orderinfo.getCoachstate()!=5 && (cList == null || cList.size() == 0)){// 未完成订单

				// 是否已经确认上车
				StringBuffer cuserhql5 = new StringBuffer();
				cuserhql5.append("from OrderRecordInfo where orderid =:orderid and userid =:userid and operation = 1");
				String[] params5 = { "orderid", "userid" };
				OrderRecordInfo orderRecord = (OrderRecordInfo) dataDao.getFirstObjectViaParam(cuserhql5.toString(), params5, orderinfo.getOrderid(), orderinfo.getStudentid());

				// 是否已经确认下车
				StringBuffer cuserhql2 = new StringBuffer();
				cuserhql2.append("from OrderRecordInfo where orderid =:orderid and userid =:userid and operation = 2");
				String[] params2 = { "orderid", "userid" };
				OrderRecordInfo orderRecord2 = (OrderRecordInfo) dataDao.getFirstObjectViaParam(cuserhql2.toString(), params2, orderinfo.getOrderid(), orderinfo.getStudentid());

				// 可以确认上车的时间点
				Calendar left = Calendar.getInstance();
				left.setTime(orderinfo.getStart_time());
				left.add(Calendar.MINUTE, -s_can_up);

				// 订单结束时间
				Calendar right = Calendar.getInstance();
				right.setTime(orderinfo.getEnd_time());

				// 可以确认下车的最晚时间
				Calendar right1 = Calendar.getInstance();
				right1.setTime(orderinfo.getEnd_time());
				right1.add(Calendar.MINUTE, s_can_down);

				// 订单显示即将开始
				if (orderRecord == null && now.getTime().before(orderinfo.getStart_time()) && now.after(left)) {
					orderinfo.setHours(0);
				} else if (now.before(left)) {// 订单显示距离开始时间
					long millisecond = orderinfo.getStart_time().getTime() - now.getTimeInMillis();
					orderinfo.setHours((int) (millisecond / 60000));
				} else if (orderRecord != null && orderRecord2 == null && now.before(right1)) {// 订单显示正在学车
					orderinfo.setHours(-1);
				} else if (orderRecord2 != null || now.after(right1)) {// 订单显示学车已经结束
					orderinfo.setHours(-2);
				} else if (orderRecord == null && now.getTime().after(orderinfo.getStart_time()) && now.getTime().before(orderinfo.getEnd_time())) {// 订单显示等待确认上车
					orderinfo.setHours(-3);
				} else if (orderRecord2 == null && now.getTime().after(orderinfo.getEnd_time()) && now.before(right1)) {// 订单显示等待确认下车
					orderinfo.setHours(-4);
				} else {// 订单显示学车已经结束
					orderinfo.setHours(-2);
				}

				// 是否可以投诉
				if (orderRecord2 != null || now.after(right1)) {// 如果订单已经确认下车过了，或者时间已经过去了
					orderinfo.setCan_complaint(1);//
				} else {
					orderinfo.setCan_complaint(0);//
				}
				// 是否需要取消投诉
				if (cList != null && cList.size() > 0) {
					orderinfo.setNeed_uncomplaint(1);
				} else {
					orderinfo.setNeed_uncomplaint(0);
				}
				// 是否可以取消
				/*if (orderinfo.getCancancel() == 1) {// 不可取消
					orderinfo.setCan_cancel(OrderState.CANNOT_CANCEL);
				} else {// 可以取消
					Calendar now2 = Calendar.getInstance();
					now2.add(Calendar.MINUTE, time_cancel);
					if (now2.getTime().before(orderinfo.getStart_time())) {
						orderinfo.setCan_cancel(OrderState.CAN_CANCEL);
					} else {
						orderinfo.setCan_cancel(OrderState.CANNOT_CANCEL);
					}
				}*/
				/*if (orderinfo.getCancancel() == 1) {// 不可取消
					orderinfo.setCan_cancel(OrderState.CAN_CANCEL);
				} else {// 可以取消
					Calendar now2 = Calendar.getInstance();
					now2.add(Calendar.MINUTE, time_cancel);
					if (now2.getTime().before(orderinfo.getStart_time())) {
						orderinfo.setCan_cancel(OrderState.CANNOT_CANCEL);
					} else {
						orderinfo.setCan_cancel(OrderState.CAN_CANCEL);
					}
				}*/
				Calendar now2 = Calendar.getInstance();
				now2.add(Calendar.MINUTE, time_cancel);
				if (now2.getTime().before(orderinfo.getStart_time())) {
					orderinfo.setCan_cancel(OrderState.CAN_CANCEL);
				} else {
					orderinfo.setCan_cancel(OrderState.CANNOT_CANCEL);
				}

				if (orderRecord != null || orderRecord2 != null) {// 确认下车过或者确认上车过
					orderinfo.setCan_up(0);// 不可以再确认上车
				} else {

					if (now.after(left) && now.before(right)) {
						orderinfo.setCan_up(1);
					} else {
						orderinfo.setCan_up(0);
					}
				}

				if (orderRecord2 != null) {// 已经确认下车过了
					orderinfo.setCan_down(0);
					orderinfo.setCan_complaint(1);
				} else {
					if (orderRecord != null) {
						if (now.before(right1)) {
							orderinfo.setCan_down(1);
						} else {
							orderinfo.setCan_down(0);
						}
					} else {
						if (now.after(right) && now.before(right1)) {
							orderinfo.setCan_down(1);
						} else {
							orderinfo.setCan_down(0);
						}
					}
				}
				// 是否可以评论
				String hqlC = "from EvaluationInfo where order_id = :order_id and type = 1";
				String[] paramsC = { "order_id" };
				EvaluationInfo eva = (EvaluationInfo) dataDao.getFirstObjectViaParam(hqlC, paramsC, orderinfo.getOrderid());

				if (eva == null && (orderRecord2 != null || now.after(right1))) {
					orderinfo.setCan_comment(1);
				} else {
					orderinfo.setCan_comment(0);
				}
			}/*else if(orderinfo.getCoachstate() == 5 && orderinfo.getStudentstate()==4){//待处理订单
				orderinfo.setCan_complaint(0);
				orderinfo.setHours(-6);// 待处理
				orderinfo.setNeed_uncomplaint(0);
				orderinfo.setCan_cancel(OrderState.CANNOT_CANCEL);// 肯定不可以取消
				orderinfo.setCan_up(0);// 不可以再确认上车
				orderinfo.setCan_down(0);
				orderinfo.setCan_complaint(OrderState.CANNOT_COMPLAINT);
				orderinfo.setCan_comment(OrderState.CANNOT_COMMENT);
			}*/else{//############被投诉 //待处理订单
				// 是否已经确认上车
				StringBuffer cuserhql5 = new StringBuffer();
				cuserhql5.append("from OrderRecordInfo where orderid =:orderid and userid =:userid and operation = 1");
				String[] params5 = { "orderid", "userid" };
				OrderRecordInfo orderRecord = (OrderRecordInfo) dataDao.getFirstObjectViaParam(cuserhql5.toString(), params5, orderinfo.getOrderid(), orderinfo.getStudentid());

				// 是否已经确认下车
				StringBuffer cuserhql2 = new StringBuffer();
				cuserhql2.append("from OrderRecordInfo where orderid =:orderid and userid =:userid and operation = 2");
				String[] params2 = { "orderid", "userid" };
				OrderRecordInfo orderRecord2 = (OrderRecordInfo) dataDao.getFirstObjectViaParam(cuserhql2.toString(), params2, orderinfo.getOrderid(), orderinfo.getStudentid());

				// 可以确认上车的时间点
				Calendar left = Calendar.getInstance();
				left.setTime(orderinfo.getStart_time());
				left.add(Calendar.MINUTE, -s_can_up);

				// 订单结束时间
				Calendar right = Calendar.getInstance();
				right.setTime(orderinfo.getEnd_time());

				// 可以确认下车的最晚时间
				Calendar right1 = Calendar.getInstance();
				right1.setTime(orderinfo.getEnd_time());
				right1.add(Calendar.MINUTE, s_can_down);

				// 订单显示即将开始
				if (orderRecord == null && now.getTime().before(orderinfo.getStart_time()) && now.after(left)) {
					orderinfo.setHours(0);
				} else if (now.before(left)) {// 订单显示距离开始时间
					long millisecond = orderinfo.getStart_time().getTime() - now.getTimeInMillis();
					orderinfo.setHours((int) (millisecond / 60000));
				} else if (orderRecord != null && orderRecord2 == null && now.before(right1)) {// 订单显示正在学车
					orderinfo.setHours(-1);
				} else if (orderRecord2 != null || now.after(right1)) {// 订单显示学车已经结束
					orderinfo.setHours(-2);
				} else if (orderRecord == null && now.getTime().after(orderinfo.getStart_time()) && now.getTime().before(orderinfo.getEnd_time())) {// 订单显示等待确认上车
					orderinfo.setHours(-3);
				} else if (orderRecord2 == null && now.getTime().after(orderinfo.getEnd_time()) && now.before(right1)) {// 订单显示等待确认下车
					orderinfo.setHours(-4);
				} else {// 订单显示学车已经结束
					orderinfo.setHours(-2);
				}

				
				orderinfo.setCan_complaint(OrderState.CANNOT_COMPLAINT);//
				orderinfo.setNeed_uncomplaint(0);//不能取消投诉
				// 是否可以取消
				orderinfo.setCan_cancel(OrderState.CANNOT_COMPLAINT);
				

				if (orderRecord != null || orderRecord2 != null) {// 确认下车过或者确认上车过
					orderinfo.setCan_up(0);// 不可以再确认上车
				} else {

					if (now.after(left) && now.before(right)) {
						orderinfo.setCan_up(1);
					} else {
						orderinfo.setCan_up(0);
					}
				}
				orderinfo.setCan_down(0);
				/*if (orderRecord2 != null) {// 已经确认下车过了
					orderinfo.setCan_down(0);
					orderinfo.setCan_complaint(1);
				} else {
					if (orderRecord != null) {
						if (now.before(right1)) {
							orderinfo.setCan_down(1);
						} else {
							orderinfo.setCan_down(0);
						}
					} else {
						if (now.after(right) && now.before(right1)) {
							orderinfo.setCan_down(1);
						} else {
							orderinfo.setCan_down(0);
						}
					}
				}*/
				orderinfo.setHours(-6);//-6投诉中
				orderinfo.setCan_comment(OrderState.CANNOT_COMMENT);
				
			}

			// 设置订单的评论信息
			String commentsql = "from EvaluationInfo where order_id = :order_id";
			String[] paramscomment = { "order_id" };
			List<EvaluationInfo> evaluationList = (List<EvaluationInfo>) dataDao.getObjectsViaParam(commentsql, paramscomment, orderinfo.getOrderid());
			if (evaluationList != null) {
				for (EvaluationInfo evaluationInfo : evaluationList) {
					if (evaluationInfo.getType() == 1) {// 用户评价教练
						evaluationInfo.setScore((evaluationInfo.getScore1() + evaluationInfo.getScore2() + evaluationInfo.getScore3()) / 3);
						orderinfo.setMyevaluation(evaluationInfo);
					} else if (evaluationInfo.getType() == 2) {// 教练评价用户
						evaluationInfo.setScore((evaluationInfo.getScore1() + evaluationInfo.getScore2() + evaluationInfo.getScore3()) / 3);
						orderinfo.setEvaluation(evaluationInfo);
					}
				}
			}
		}

		return orderinfo;
	}

	@Override
	public List<ComplaintSetInfo> getComplaintReason(String type) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from ComplaintSetInfo where type =:type ");
		String[] params = { "type" };
		List<ComplaintSetInfo> reasonlist = (List<ComplaintSetInfo>) dataDao.getObjectsViaParam(cuserhql.toString(), params, CommonUtils.parseInt(type, 0));
		return reasonlist;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void addComplaint(String userid, String orderid, String type, String reason, String content) {
		ComplaintInfo complaint = new ComplaintInfo();
		OrderInfo order = dataDao.getObjectById(OrderInfo.class, CommonUtils.parseInt(orderid, 0));

		complaint.setFrom_user(CommonUtils.parseInt(userid, 0));
		if (CommonUtils.parseInt(type, 0) == 1) {// 教练投诉学员
			complaint.setType(2);
			StringBuffer cuserhql = new StringBuffer();
			cuserhql.append("from ComplaintSetInfo where setid =:setid ");
			cuserhql.append("and type = 1");
			String[] params = { "setid" };
			ComplaintSetInfo complaintset = (ComplaintSetInfo) dataDao.getFirstObjectViaParam(cuserhql.toString(), params, CommonUtils.parseInt(reason, 0));
			if (order != null) {
				complaint.setTo_user(order.getStudentid());
			}
			if (complaintset != null) {
				complaint.setReason(complaintset.getContent());
			}
		} else {// 学员投诉教练
			if (order != null && order.getStudentstate() != 3) {
				order.setStudentstate(0);
				dataDao.updateObject(order);
			}

			complaint.setType(1);
			StringBuffer cuserhql = new StringBuffer();
			cuserhql.append("from ComplaintSetInfo where setid =:setid ");
			cuserhql.append("and type = 2");
			String[] params = { "setid" };
			ComplaintSetInfo complaintset = (ComplaintSetInfo) dataDao.getFirstObjectViaParam(cuserhql.toString(), params, CommonUtils.parseInt(reason, 0));
			if (order != null) {
				complaint.setTo_user(order.getCoachid());
			}
			if (complaintset != null) {
				complaint.setReason(complaintset.getContent());
			}
		}
		complaint.setContent(content);
		complaint.setOrder_id(CommonUtils.parseInt(orderid, 0));
		complaint.setAddtime(new Date());
		dataDao.addObject(complaint);

		// 记录
		OrderRecordInfo orderRecord = new OrderRecordInfo();
		orderRecord.setAddtime(new Date());
		orderRecord.setComplaintid(complaint.getComplaintid());
		if (CommonUtils.parseInt(type, 0) == 1) {// 教练投诉学员
			orderRecord.setOperation(9);
		} else {
			orderRecord.setOperation(7);
		}
		orderRecord.setUserid(CommonUtils.parseInt(userid, 0));
		orderRecord.setOrderid(CommonUtils.parseInt(orderid, 0));
		dataDao.addObject(orderRecord);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public int cancelOrder(String studentid, String orderid) {
		OrderInfo order = dataDao.getObjectById(OrderInfo.class, CommonUtils.parseInt(orderid, 0));
		SuserInfo student = dataDao.getObjectById(SuserInfo.class, CommonUtils.parseInt(studentid, 0));
		String hql = "from SystemSetInfo where 1=1";
		SystemSetInfo system = (SystemSetInfo) dataDao.getFirstObjectViaParam(hql, null);
		Calendar c = Calendar.getInstance();
		int result = 0;
		if (system != null && system.getTime_cancel() != 0) {
			c.add(Calendar.MINUTE, system.getTime_cancel());
			result = system.getTime_cancel();
		} else {
			c.add(Calendar.HOUR, 1);
			result = 60;
		}
		if (order != null) {
			if (order.getStart_time().after(c.getTime())) {
			

				if (student != null) {
					if(order.getDelmoney()>0 && order.getCouponrecordid().length()>1)
					{
						String coupongetrecordids=order.getCouponrecordid();
						if(coupongetrecordids.lastIndexOf(',')== coupongetrecordids.length()-1)
						{
							//System.out.println("old: "+coupongetrecordids);
							coupongetrecordids = coupongetrecordids.substring(0, coupongetrecordids.length()-2);
							//System.out.println("now :"+coupongetrecordids);
						}
					dataDao.updateBySql(" update t_couponget_record set state=0 where recordid in("+coupongetrecordids+") ");
						
					}
					else
					{
						student.setMoney(student.getMoney().add(order.getTotal()));
						student.setFmoney(student.getFmoney().subtract(order.getTotal()));
						dataDao.updateObject(student);
					}
				}

				order.setStudentstate(4);//学员取消
				order.setCoachstate(4);//教练取消
				dataDao.updateObject(order);

				// 把订单中原来预订的时间返回回来
				Calendar start = Calendar.getInstance();
				start.setTime(order.getStart_time());
				int starthour = start.get(Calendar.HOUR_OF_DAY);

				Calendar end = Calendar.getInstance();
				end.setTime(order.getEnd_time());
				int endhour = end.get(Calendar.HOUR_OF_DAY);

				String hqlCoach = "from CBookTimeInfo where bookedtime in (:bookedtimes) and date = :date and coachid = :coachid";
				String[] paramsCoach = { "bookedtimes", "date", "coachid" };

				List<String> bookedtimes = new ArrayList<String>();
				for (int i = starthour; i < endhour; i++) {
					bookedtimes.add(String.valueOf(i));
				}

				List<CBookTimeInfo> list = (List<CBookTimeInfo>) dataDao.getObjectsViaParam(hqlCoach, paramsCoach, bookedtimes, CommonUtils.getTimeFormat(order.getStart_time(), "yyyy-MM-dd"),
						order.getCoachid());
				if (list != null) {
					for (CBookTimeInfo cBookTimeInfo : list) {
						dataDao.deleteObject(cBookTimeInfo);
					}
				}

				// 把订单原来的提醒记录删除掉
				String hqlDelete = "from OrderNotiRecord where orderid = :orderid";
				String[] paramsD = { "orderid" };
				List<OrderNotiRecord> listDelete = (List<OrderNotiRecord>) dataDao.getObjectsViaParam(hqlDelete, paramsD, order.getOrderid());
				if (listDelete != null) {
					for (OrderNotiRecord orderNotiRecord : listDelete) {
						dataDao.deleteObject(orderNotiRecord);
					}
				}
				// 记录取消订单
				OrderRecordInfo orderRecord = new OrderRecordInfo();
				orderRecord.setAddtime(new Date());
				orderRecord.setOperation(5);
				orderRecord.setOrderid(CommonUtils.parseInt(orderid, 0));
				orderRecord.setUserid(CommonUtils.parseInt(studentid, 0));
				dataDao.addObject(orderRecord);
				return 0;
			} else {
				return result;
			}
		}
		return -1;
	}
	
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public int cancelOrderByStudent(String studentid, String orderid) {
		OrderInfo order = dataDao.getObjectById(OrderInfo.class, CommonUtils.parseInt(orderid, 0));
		SuserInfo student = dataDao.getObjectById(SuserInfo.class, CommonUtils.parseInt(studentid, 0));
		/*String hql = "from SystemSetInfo where 1=1";
		SystemSetInfo system = (SystemSetInfo) dataDao.getFirstObjectViaParam(hql, null);*/
		Calendar c = Calendar.getInstance();
		int result = 0;
		/*if (system != null && system.getTime_cancel() != 0) {
			c.add(Calendar.MINUTE, system.getTime_cancel());
			result = system.getTime_cancel();
		} else {
			c.add(Calendar.HOUR, 1);
			result = 60;
		}*/
		c.add(Calendar.MINUTE,10);
		result = 60;
		if (order != null ) {
			int coachid=order.getCoachid();
			if (order.getStart_time().after(c.getTime())) {
				order.setStudentstate(4);//学员取消
				//order.setCoachstate(4);//教练取消
				dataDao.updateObject(order);
				
				String studentName=student.getRealname();
				//张三申请取消07/12 21:00-22:00学车课程
				String pushMsg=studentName+"申请取消"+order.getStart_time()+"的学车课程";
				//给此订单关联的教练推送消息，提示让他同意取消订单
				String hql5 = "from UserPushInfo where userid =:userid and usertype = 1";
				String params5[] = { "userid" };
				UserPushInfo userpush = (UserPushInfo) dataDao.getFirstObjectViaParam(hql5, params5,coachid);
				if (userpush != null) {
					if (userpush.getType() == DeviceType.ANDROID && !CommonUtils.isEmptyString(userpush.getJpushid())) {// 安卓
						PushtoSingle pushsingle = new PushtoSingle();
						pushsingle.pushsingle(userpush.getJpushid(), 1, "{\"message\":\"" + pushMsg + "\",\"type\":\"1\",\"orderid\":\""+orderid+"\"}");
					} else if (userpush.getType() == DeviceType.IOS && !CommonUtils.isEmptyString(userpush.getDevicetoken())) {
						ApplePushUtil.sendpush(userpush.getDevicetoken(), "{\"aps\":{\"alert\":\"" + pushMsg + "\",\"sound\":\"default\"},\"userid\":" + coachid + ",\"orderid\":\""+orderid+"\"}", 1, 1);
					}
				}
				return 0;
			} else {
				return result;
			}
		}
		return -1;
	}
	/** 
	 * 教练是否同意取消订单 。 agree  1 ：教练不同意
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public int cancelOrderByCoach(String orderid,String agree) {
		OrderInfo order = dataDao.getObjectById(OrderInfo.class, CommonUtils.parseInt(orderid, 0));
		String studentid="";
		if(order!=null){
			studentid=String.valueOf(order.getStudentid());
		}
		if(order!=null && "1".equals(agree)){//教练不同意
			
			//教练不同意时，把Coachstate设置为5
			order.setCoachstate(5);
			dataDao.updateObject(order);
			//给学员推送消息
			String pushMsg="教练不同意您取消"+order.getStart_time()+"的学车课程";
			//给此订单关联的教练推送消息，提示让他同意取消订单
			String hql5 = "from UserPushInfo where userid =:userid and usertype = 1";
			String params5[] = { "userid" };
			UserPushInfo userPushInfo = (UserPushInfo) dataDao.getFirstObjectViaParam(hql5, params5,CommonUtils.parseInt(studentid, 0));
			if (userPushInfo != null && userPushInfo.getDevicetoken() != null) {
				if (userPushInfo.getType() == DeviceType.ANDROID && !CommonUtils.isEmptyString(userPushInfo.getJpushid())) {
					PushtoSingle push = new PushtoSingle();
					push.pushsingle(userPushInfo.getJpushid(), 2, "{\"message\":\"" + pushMsg + "\",\"type\":\"5\"}");
				} else if (userPushInfo.getType() == DeviceType.IOS && !CommonUtils.isEmptyString(userPushInfo.getDevicetoken())) {
					ApplePushUtil.sendpush(userPushInfo.getDevicetoken(), "{\"aps\":{\"alert\":\"" + pushMsg + "\",\"sound\":\"default\"},\"userid\":" + studentid + "}", 1, 2);
				}
			}
			return 0;
		}
		//教练同意取消
		CuserInfo cuser;
		SuserInfo student = dataDao.getObjectById(SuserInfo.class, CommonUtils.parseInt(studentid, 0));
		String hql = "from SystemSetInfo where 1=1";
		SystemSetInfo system = (SystemSetInfo) dataDao.getFirstObjectViaParam(hql, null);
		Calendar c = Calendar.getInstance();
		int result = 0;
		if (system != null && system.getTime_cancel() != 0) {
			c.add(Calendar.MINUTE, system.getTime_cancel());
			result = system.getTime_cancel();
		} else {
			c.add(Calendar.HOUR, 1);
			result = 60;
		}
		if (order != null) {
			//if (order.getStart_time().after(c.getTime())) {
			cuser=dataDao.getObjectById(CuserInfo.class, order.getCoachid());
			Calendar c1=Calendar.getInstance();
			c1.setTime(order.getStart_time());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			cscheduleService.setCscheduleByday(String.valueOf(order.getCoachid()), sdf.format(c1.getTime()),String.valueOf(c1.get(Calendar.HOUR_OF_DAY)),0);
			if(order.getPaytype()==PayType.MONEY){
				student.setFmoney(student.getFmoney().subtract(order.getTotal()));
				student.setMoney(student.getMoney().add(order.getTotal()));
			}else if(order.getPaytype()==PayType.COUPON){
				String coupongetrecordids=order.getCouponrecordid();
				if(coupongetrecordids.lastIndexOf(',')== coupongetrecordids.length()-1)
				{
					coupongetrecordids = coupongetrecordids.substring(0, coupongetrecordids.length()-2);
				}
				dataDao.updateBySql(" update t_couponget_record set state=0 where recordid in("+coupongetrecordids+") ");
				//dataDao.deleteBySql("delete from t_coupon_coach where couponrecordid in ("+coupongetrecordids+") ");
				
			}else if(order.getPaytype()==PayType.COIN){
				//教练同意时，取消冻结金额。如果冻结金额小于订单额，直接相减后，冻结金额会出现负数，所以判断
				if(student.getFcoinnum().intValue()>=order.getTotal().intValue()){
					student.setFcoinnum(student.getFcoinnum().subtract(order.getTotal()));
				}else{
					System.out.println("cancelOrderByCoach方法中：小巴币解冻时发现数量小于订单额!停止解冻");
					return -1;
				}
				
				student.setCoinnum(student.getCoinnum()+order.getTotal().intValue());
			}else if(order.getPaytype()==PayType.COIN_MONEY){
				//***********小巴币的处理*************
				//教练同意时，取消冻结金额。如果冻结金额小于订单额，直接相减后，冻结金额会出现负数，所以判断
				if(student.getFcoinnum().intValue()>=order.getMixCoin()){
					student.setFcoinnum(student.getFcoinnum().subtract(new BigDecimal(order.getMixCoin())));
				}else{
					System.out.println("cancelOrderByCoach方法中：小巴币解冻时发现数量小于订单额!停止解冻");
					return -1;
				}
				student.setCoinnum(student.getCoinnum()+order.getMixCoin());
				order.setMixCoin(0);
				//***********余额的处理***************
				student.setFmoney(student.getFmoney().subtract(new BigDecimal(order.getMixMoney())));
				student.setMoney(student.getMoney().add(new BigDecimal(order.getMixMoney())));
				order.setMixMoney(0);
				
			}
			
				

				//order.setStudentstate(4);//学员取消
				order.setCoachstate(4);//教练同意取消
				dataDao.updateObject(order);

				// 把订单中原来预订的时间返回回来
				Calendar start = Calendar.getInstance();
				start.setTime(order.getStart_time());
				int starthour = start.get(Calendar.HOUR_OF_DAY);

				Calendar end = Calendar.getInstance();
				end.setTime(order.getEnd_time());
				int endhour = end.get(Calendar.HOUR_OF_DAY);
				if(endhour==0){
					endhour=24;
				}

				String hqlCoach = "from CBookTimeInfo where bookedtime in (:bookedtimes) and date = :date and coachid = :coachid";
				String[] paramsCoach = { "bookedtimes", "date", "coachid" };

				List<String> bookedtimes = new ArrayList<String>();
				for (int i = starthour; i < endhour; i++) {
					bookedtimes.add(String.valueOf(i));
				}

				List<CBookTimeInfo> list = (List<CBookTimeInfo>) dataDao.getObjectsViaParam(hqlCoach, paramsCoach, bookedtimes, CommonUtils.getTimeFormat(order.getStart_time(), "yyyy-MM-dd"),
						order.getCoachid());
				if (list != null) {
					for (CBookTimeInfo cBookTimeInfo : list) {
						dataDao.deleteObject(cBookTimeInfo);
					}
				}

				// 把订单原来的提醒记录删除掉
				String hqlDelete = "from OrderNotiRecord where orderid = :orderid";
				String[] paramsD = { "orderid" };
				List<OrderNotiRecord> listDelete = (List<OrderNotiRecord>) dataDao.getObjectsViaParam(hqlDelete, paramsD, order.getOrderid());
				if (listDelete != null) {
					for (OrderNotiRecord orderNotiRecord : listDelete) {
						dataDao.deleteObject(orderNotiRecord);
					}
				}
				// 记录取消订单
				OrderRecordInfo orderRecord = new OrderRecordInfo();
				orderRecord.setAddtime(new Date());
				orderRecord.setOperation(5);
				orderRecord.setOrderid(CommonUtils.parseInt(orderid, 0));
				orderRecord.setUserid(CommonUtils.parseInt(studentid, 0));
				dataDao.addObject(orderRecord);
				//给学员推送消息
				String pushMsg="教练已同意您取消"+order.getStart_time()+"的学车课程，支付的金额已退回到小巴账户余额。";
				//给此订单关联的教练推送消息，提示让他同意取消订单
				String hql5 = "from UserPushInfo where userid =:userid and usertype = 1";
				String params5[] = { "userid" };
				UserPushInfo userPushInfo = (UserPushInfo) dataDao.getFirstObjectViaParam(hql5, params5,CommonUtils.parseInt(studentid, 0));
				if (userPushInfo != null && userPushInfo.getDevicetoken() != null) {
					if (userPushInfo.getType() == DeviceType.ANDROID && !CommonUtils.isEmptyString(userPushInfo.getJpushid())) {
						PushtoSingle push = new PushtoSingle();
						push.pushsingle(userPushInfo.getJpushid(), 2, "{\"message\":\"" + pushMsg + "\",\"type\":\"4\"}");
					} else if (userPushInfo.getType() == DeviceType.IOS && !CommonUtils.isEmptyString(userPushInfo.getDevicetoken())) {
						ApplePushUtil.sendpush(userPushInfo.getDevicetoken(), "{\"aps\":{\"alert\":\"" + pushMsg + "\",\"sound\":\"default\"},\"userid\":" + studentid + "}", 1, 2);
					}
				}
				
				return 0;
			/*} else {
				return result;
			}*/
		}
		return -1;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void CancelComplaint(String studentid, String orderid) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from ComplaintInfo where from_user =:studentid ");
		cuserhql.append("and order_id =:orderid and type = 1");
		String[] params = { "studentid", "orderid" };
		List<ComplaintInfo> complaintlist = (List<ComplaintInfo>) dataDao.getObjectsViaParam(cuserhql.toString(), params, CommonUtils.parseInt(studentid, 0), CommonUtils.parseInt(orderid, 0));
		if (complaintlist.size() > 0) {
			for (ComplaintInfo complaint : complaintlist) {
				complaint.setState(2);
				dataDao.updateObject(complaint);
			}
		}

		// 查询出订单
		String orderHql = "from OrderInfo where orderid = :orderid";
		String[] paramsOrder = { "orderid" };

		OrderInfo order = (OrderInfo) dataDao.getFirstObjectViaParam(orderHql, paramsOrder, CommonUtils.parseInt(orderid, 0));
		if (order != null && order.getOver_time() == null) {// 订单状态不是已经结算
			// 首先查询出订单相关的几个时间配置
			String hqlset = "from SystemSetInfo where 1 = 1";
			SystemSetInfo setInfo = (SystemSetInfo) dataDao.getFirstObjectViaParam(hqlset, null);

			int order_over = 2880;
			int can_down = 60;

			if (setInfo != null) {
				if (setInfo.getS_order_end() != null && setInfo.getS_order_end() != 0)
					order_over = setInfo.getS_order_end();

				if (setInfo.getS_can_down() != null && setInfo.getS_can_down() != 0)
					can_down = setInfo.getS_can_down();
			}

			Calendar now = Calendar.getInstance();

			Calendar orderOver = Calendar.getInstance();
			orderOver.setTime(order.getEnd_time());
			orderOver.add(Calendar.MINUTE, order_over);

			Calendar orderCanDown = Calendar.getInstance();
			orderCanDown.setTime(order.getEnd_time());
			orderCanDown.add(Calendar.MINUTE, can_down);

			// 如果订单之前是已经结束的话,需要把订单的状态修改为2 或者直接结算
			// 是否已经确认下车
			StringBuffer cuserhql2 = new StringBuffer();
			cuserhql2.append("from OrderRecordInfo where orderid =:orderid and userid =:userid and operation = 2");
			String[] params2 = { "orderid", "userid" };
			OrderRecordInfo orderRecord2 = (OrderRecordInfo) dataDao.getFirstObjectViaParam(cuserhql2.toString(), params2, CommonUtils.parseInt(orderid, 0), CommonUtils.parseInt(studentid, 0));
			if (orderRecord2 != null) {// 已经确认下车过
				// 判断订单是否需要结算
				if (now.after(orderOver)) {// 结算订单
					order.setStudentstate(3);// 设置订单状态为已结算
					dataDao.updateObject(order);

					SettlementOrder(order.getOrderid());
				} else {
					order.setStudentstate(2);// 设置订单状态为待评价
					dataDao.updateObject(order);
				}
			} else {
				if (now.after(orderCanDown) && now.before(orderOver)) {
					order.setStudentstate(2);// 设置订单状态为已结算
					dataDao.updateObject(order);
				} else if (now.after(orderOver)) {
					order.setStudentstate(3);// 设置订单状态为已结算
					dataDao.updateObject(order);

					SettlementOrder(order.getOrderid());
				} else {
					order.setStudentstate(0);// 设置订单状态为初始
					dataDao.updateObject(order);
				}
			}
		}
	}

	@Override
	public QueryResult<OrderInfo> getOrderList(String coachphone, String studentphone, String startminsdate, String startmaxsdate, String endminsdate, String endmaxsdate,String createminsdate, String createmaxsdate, Integer state,
			Integer ordertotal, String inputordertotal, Integer ishavacomplaint,Integer paytype, Integer pageIndex, int pagesize) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from OrderInfo where 1=1");
		if (!CommonUtils.isEmptyString(coachphone)) {
			cuserhql.append(" and coachid in(select coachid from CuserInfo where phone like '%" + coachphone + "%')");
		}
		if (!CommonUtils.isEmptyString(studentphone)) {
			cuserhql.append(" and studentid in(select studentid from SuserInfo where phone like '%" + studentphone + "%')");
		}
		if (!CommonUtils.isEmptyString(startminsdate)) {
			cuserhql.append(" and start_time >='" + startminsdate + "'");
		}
		if (!CommonUtils.isEmptyString(startmaxsdate)) {
			Date startdate = CommonUtils.getDateFormat(startmaxsdate, "yyyy-MM-dd");
			startdate.setHours(23);
			startdate.setMinutes(59);
			startdate.setSeconds(59);
			String startmaxstime = CommonUtils.getTimeFormat(startdate, "yyyy-MM-dd HH:mm:ss");
			cuserhql.append(" and start_time <='" + startmaxstime + "'");
		}
		if (!CommonUtils.isEmptyString(endminsdate)) {
			cuserhql.append(" and end_time >='" + endminsdate + "'");
		}
		if (!CommonUtils.isEmptyString(endmaxsdate)) {
			Date enddate = CommonUtils.getDateFormat(endmaxsdate, "yyyy-MM-dd");
			enddate.setHours(23);
			enddate.setMinutes(59);
			enddate.setSeconds(59);
			String endmaxstime = CommonUtils.getTimeFormat(enddate, "yyyy-MM-dd HH:mm:ss");
			cuserhql.append(" and end_time <='" + endmaxstime + "'");
		}
		if (!CommonUtils.isEmptyString(createminsdate)) {
			cuserhql.append(" and creat_time >='" + createminsdate + "'");
		}
		if (!CommonUtils.isEmptyString(createmaxsdate)) {
			Date enddate = CommonUtils.getDateFormat(createmaxsdate, "yyyy-MM-dd");
			enddate.setHours(23);
			enddate.setMinutes(59);
			enddate.setSeconds(59);
			String createmaxstime = CommonUtils.getTimeFormat(enddate, "yyyy-MM-dd HH:mm:ss");
			cuserhql.append(" and creat_time <='" + createmaxstime + "'");
		}
		if (state != null) {
			if (state == 1) {
				cuserhql.append(" and (coachstate<>2 or over_time is null or studentstate in(0,2))");
			}
			if (state == 2) {
				cuserhql.append(" and (coachstate=2 and over_time is not null and studentstate=3)");
			}
			if (state == 3) {
				cuserhql.append(" and (coachstate=4 or  studentstate=4)");
			}
		}
		if (!CommonUtils.isEmptyString(inputordertotal)) {
			if (ordertotal == 0) {
				cuserhql.append(" and total >" + inputordertotal);
			}
			if (ordertotal == 1) {
				cuserhql.append(" and total =" + inputordertotal);
			}
			if (ordertotal == 2) {
				cuserhql.append(" and total <" + inputordertotal);
			}
		}
		if (ishavacomplaint != null) {
			if (ishavacomplaint == 1) {
				cuserhql.append(" and orderid in (select orderid from OrderRecordInfo where operation in(7,9))");
			}
			if (ishavacomplaint == 2) {
				cuserhql.append(" and orderid not in (select orderid from OrderRecordInfo where operation in(7,9))");
			}
		}
		if(paytype!=null)
		{
			if(paytype!=0)
				cuserhql.append(" and paytype ="+paytype);
		}
		cuserhql.append(" order by creat_time desc");
		List<OrderInfo> orderlist = (List<OrderInfo>) dataDao.pageQueryViaParam(cuserhql.toString(), pagesize, pageIndex, null);
		if (orderlist != null && orderlist.size() > 0) {
			for (OrderInfo order : orderlist) {
				SuserInfo student = dataDao.getObjectById(SuserInfo.class, order.getStudentid());
				CuserInfo coach = dataDao.getObjectById(CuserInfo.class, order.getCoachid());
				if (student != null) {
					order.setStudentinfo(student);
				}
				if (coach != null) {
					order.setCuserinfo(coach);
				}
				StringBuffer cuserhql1 = new StringBuffer();
				cuserhql1.append("from ComplaintInfo where order_id =:orderid ");
				String[] params = { "orderid" };
				String countnumhql = " select count(*) " + cuserhql1.toString();
				long complaintnum = (Long) dataDao.getFirstObjectViaParam(countnumhql, params, order.getOrderid());
				order.setComplaintnum(complaintnum);
			}
		}
		String counthql = " select count(*) " + cuserhql.toString();
		long total = (Long) dataDao.getFirstObjectViaParam(counthql, null);
		return new QueryResult<OrderInfo>(orderlist, total);
	}

	@Override
	public QueryResult<ComplaintInfo> getComplaintList(Integer pageIndex, int pagesize) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from ComplaintInfo order by addtime desc");
		List<ComplaintInfo> complaintlist = (List<ComplaintInfo>) dataDao.pageQueryViaParam(cuserhql.toString(), pagesize, pageIndex, null);
		if (complaintlist != null && complaintlist.size() > 0) {
			for (ComplaintInfo complaint : complaintlist) {
				if (complaint.getType() == 1) {
					SuserInfo student = dataDao.getObjectById(SuserInfo.class, complaint.getFrom_user());
					CuserInfo coach = dataDao.getObjectById(CuserInfo.class, complaint.getTo_user());
					if (student != null) {
						complaint.setStudent(student);
					}
					if (coach != null) {
						complaint.setCoach(coach);
						;
					}
				} else {
					SuserInfo student = dataDao.getObjectById(SuserInfo.class, complaint.getTo_user());
					CuserInfo coach = dataDao.getObjectById(CuserInfo.class, complaint.getFrom_user());
					if (student != null) {
						complaint.setStudent(student);
					}
					if (coach != null) {
						complaint.setCoach(coach);
						;
					}
				}
			}
		}
		String counthql = " select count(*) " + cuserhql.toString();
		long total = (Long) dataDao.getFirstObjectViaParam(counthql, null);
		return new QueryResult<ComplaintInfo>(complaintlist, total);
	}

	@Override
	public ComplaintInfo getComplaintById(int complaintid) {
		return dataDao.getObjectById(ComplaintInfo.class, complaintid);
	}

	@Override
	public OrderInfo getOrderById(int orderid) {
		return dataDao.getObjectById(OrderInfo.class, orderid);
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public QueryResult<ComplaintInfo> getComplaintByKeyword(Integer complaintid, String studentphone, String coachphone, Integer type, String minsdate, String maxsdate, Integer state,
			Integer pageIndex, int pagesize) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from ComplaintInfo where 1=1");
		if (complaintid != null) {
			cuserhql.append(" and complaintid =" + complaintid);
		}
		if (type == 0) {
			if (!CommonUtils.isEmptyString(studentphone)) {
				cuserhql.append(" and (from_user in(select studentid from SuserInfo where phone like '%" + studentphone + "%'" + ")or to_user in(select studentid from SuserInfo where phone like '%"
						+ studentphone + "%'" + "))");
			}
			if (!CommonUtils.isEmptyString(coachphone)) {
				cuserhql.append(" and (from_user in(select coachid from CuserInfo where phone like '%" + studentphone + "%'" + ")or to_user in(select coachid from CuserInfo where phone like '%"
						+ studentphone + "%'" + "))");
			}
		}
		if (type == 1) {
			cuserhql.append(" and type =" + type);
			if (!CommonUtils.isEmptyString(studentphone)) {
				cuserhql.append(" and from_user in(select studentid from SuserInfo where phone like '%" + studentphone + "%'" + ")");
			}
			if (!CommonUtils.isEmptyString(coachphone)) {
				cuserhql.append(" and to_user in(select coachid from CuserInfo where phone like '%" + coachphone + "%'" + ")");
			}
		}
		if (type == 2) {
			cuserhql.append(" and type =" + type);
			if (!CommonUtils.isEmptyString(studentphone)) {
				cuserhql.append(" and to_user in(select studentid from SuserInfo where phone like '%" + studentphone + "%'" + ")");
			}
			if (!CommonUtils.isEmptyString(coachphone)) {
				cuserhql.append(" and from_user in(select coachid from CuserInfo where phone like '%" + coachphone + "%'" + ")");
			}
		}
		if (!CommonUtils.isEmptyString(minsdate)) {
			cuserhql.append(" and addtime >='" + minsdate + "'");
		}
		if (!CommonUtils.isEmptyString(maxsdate)) {
			Date enddate = CommonUtils.getDateFormat(maxsdate, "yyyy-MM-dd");
			enddate.setHours(23);
			enddate.setMinutes(59);
			enddate.setSeconds(59);
			String endmaxstime = CommonUtils.getTimeFormat(enddate, "yyyy-MM-dd HH:mm:ss");
			cuserhql.append(" and addtime <='" + endmaxstime + "'");
		}
		if (state != null) {
			if (state == 1) {
				cuserhql.append(" and state = 0");
			}
			if (state == 2) {
				cuserhql.append(" and state = 1");
			}
			if (state == 3) {
				cuserhql.append(" and state = 2");
			}
		}
		cuserhql.append(" order by addtime desc");
		List<ComplaintInfo> complaintlist = (List<ComplaintInfo>) dataDao.pageQueryViaParam(cuserhql.toString(), pagesize, pageIndex, null);
		if (complaintlist != null && complaintlist.size() > 0) {
			for (ComplaintInfo complaint : complaintlist) {
				if (complaint.getType() == 1) {
					SuserInfo student = dataDao.getObjectById(SuserInfo.class, complaint.getFrom_user());
					CuserInfo coach = dataDao.getObjectById(CuserInfo.class, complaint.getTo_user());
					if (student != null) {
						complaint.setStudent(student);
					}
					if (coach != null) {
						complaint.setCoach(coach);
						;
					}
				} else {
					SuserInfo student = dataDao.getObjectById(SuserInfo.class, complaint.getTo_user());
					CuserInfo coach = dataDao.getObjectById(CuserInfo.class, complaint.getFrom_user());
					if (student != null) {
						complaint.setStudent(student);
					}
					if (coach != null) {
						complaint.setCoach(coach);
						;
					}
				}
			}
		}
		String counthql = " select count(*) " + cuserhql.toString();
		long total = (Long) dataDao.getFirstObjectViaParam(counthql, null);
		return new QueryResult<ComplaintInfo>(complaintlist, total);
	}

	@Override
	public CuserInfo getCoachById(int id) {
		return dataDao.getObjectById(CuserInfo.class, id);
	}

	@Override
	public SuserInfo getStudentById(int id) {
		return dataDao.getObjectById(SuserInfo.class, id);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void updateComplaintState(ComplaintInfo complaint) {
		dataDao.updateObject(complaint);

		// 判断订单的状态
		// 查询出订单
		String orderHql = "from OrderInfo where orderid = :orderid";
		String[] paramsOrder = { "orderid" };

		OrderInfo order = (OrderInfo) dataDao.getFirstObjectViaParam(orderHql, paramsOrder, complaint.getOrder_id());
		String hql = "from ComplaintInfo where order_id = :order_id and type = 1 and state = 0";
		String[] params = { "order_id" };
		List<ComplaintInfo> ComplaintInfoList = (List<ComplaintInfo>) dataDao.getObjectsViaParam(hql, params, complaint.getOrder_id());

		if (order != null && (ComplaintInfoList == null || ComplaintInfoList.size() == 0)) {
			// 首先查询出订单相关的几个时间配置
			String hqlset = "from SystemSetInfo where 1 = 1";
			SystemSetInfo setInfo = (SystemSetInfo) dataDao.getFirstObjectViaParam(hqlset, null);

			int order_over = 2880;
			int can_down = 60;

			if (setInfo != null) {
				if (setInfo.getS_order_end() != null && setInfo.getS_order_end() != 0)
					order_over = setInfo.getS_order_end();

				if (setInfo.getS_can_down() != null && setInfo.getS_can_down() != 0)
					can_down = setInfo.getS_can_down();
			}

			Calendar now = Calendar.getInstance();

			Calendar orderOver = Calendar.getInstance();
			orderOver.setTime(order.getEnd_time());
			orderOver.add(Calendar.MINUTE, order_over);

			Calendar orderCanDown = Calendar.getInstance();
			orderCanDown.setTime(order.getEnd_time());
			orderCanDown.add(Calendar.MINUTE, can_down);

			// 如果订单之前是已经结束的话,需要把订单的状态修改为2 或者直接结算
			// 是否已经确认下车
			StringBuffer cuserhql2 = new StringBuffer();
			cuserhql2.append("from OrderRecordInfo where orderid =:orderid and userid =:userid and operation = 2");
			String[] params2 = { "orderid", "userid" };
			OrderRecordInfo orderRecord2 = (OrderRecordInfo) dataDao.getFirstObjectViaParam(cuserhql2.toString(), params2, complaint.getOrder_id(), order.getStudentid());

			// 查询订单的评价信息
			String hqlComment = "from EvaluationInfo where order_id =:order_id and type = 1";
			String[] paramsComment = { "order_id" };

			EvaluationInfo evaInfo = (EvaluationInfo) dataDao.getFirstObjectViaParam(hqlComment, paramsComment, order.getOrderid());
			if (orderRecord2 != null) {// 已经确认下车过
				if (evaInfo != null) {// 已经评论过
					order.setStudentstate(3);// 设置订单状态为已结算
					dataDao.updateObject(order);
				} else {
					// 判断订单是否需要结算
					if (now.after(orderOver)) {// 结算订单
						SettlementOrder(order.getOrderid());
					} else {
						order.setStudentstate(2);// 设置订单状态为已结算
						dataDao.updateObject(order);
					}
				}

			} else {
				if (evaInfo != null) {
					order.setStudentstate(3);// 设置订单状态为已结算
				} else {
					if (now.after(orderCanDown) && now.before(orderOver)) {
						order.setStudentstate(2);// 设置订单状态为已结算
						dataDao.updateObject(order);
					} else {
						SettlementOrder(order.getOrderid());
					}
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ComplaintInfo> getComplaintByTime(String polltime) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from ComplaintInfo where addtime >'" + polltime + "'");
		cuserhql.append(" and state = 0");
		List<ComplaintInfo> complaintlist = (List<ComplaintInfo>) dataDao.getObjectsViaParam(cuserhql.toString(), null);
		return complaintlist;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ComplaintBookInfo> getComplaintBookList(int complaintid) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from ComplaintBookInfo where complaintid =:complaintid order by addtime desc");
		String[] params = { "complaintid" };
		List<ComplaintBookInfo> complaintbooklist = (List<ComplaintBookInfo>) dataDao.getObjectsViaParam(cuserhql.toString(), params, complaintid);
		return complaintbooklist;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void addComplaintBook(ComplaintBookInfo complaintbook) {
		dataDao.addObject(complaintbook);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderRecordInfo> getOrderRecord(int orderid) {
		List<OrderRecordInfo> orderrecordlist = new ArrayList<OrderRecordInfo>();
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from OrderRecordInfo where orderid =:orderid order by addtime desc");
		String[] params = { "orderid" };
		orderrecordlist = (List<OrderRecordInfo>) dataDao.getObjectsViaParam(cuserhql.toString(), params, orderid);
		if (orderrecordlist != null && orderrecordlist.size() > 0) {
			for (OrderRecordInfo orderrecord : orderrecordlist) {
				if (orderrecord.getEvaluationid() != 0) {
					EvaluationInfo evaluation = dataDao.getObjectById(EvaluationInfo.class, orderrecord.getEvaluationid());
					orderrecord.setContent(evaluation.getContent());
				}
				if (orderrecord.getComplaintid() != 0) {
					ComplaintInfo complaint = dataDao.getObjectById(ComplaintInfo.class, orderrecord.getComplaintid());
					orderrecord.setContent(complaint.getContent());
				}
			}
		}
		return orderrecordlist;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderPrice> getOrderPriceList(int orderid) {
		List<OrderPrice> orderpricelist = new ArrayList<OrderPrice>();
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from OrderPrice where orderid =:orderid ");
		String[] params = { "orderid" };
		orderpricelist = (List<OrderPrice>) dataDao.getObjectsViaParam(cuserhql.toString(), params, orderid);
		if (orderpricelist != null && orderpricelist.size() > 0) {
			for (OrderPrice orderprice : orderpricelist) {
				orderprice.setTotal(CommonUtils.parseInt(orderprice.getHour(), 0) * orderprice.getPrice().floatValue());
			}
		}
		return orderpricelist;
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public QueryResult<OrderInfo> getOrderByKeyword(String coachphone, String studentphone, String startminsdate, String startmaxsdate, String endminsdate, String endmaxsdate, Integer state,
			Integer ordertotal, String inputordertotal, Integer ishavacomplaint, Integer pageIndex, int pagesize) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from OrderInfo where 1=1");
		if (!CommonUtils.isEmptyString(coachphone)) {
			cuserhql.append(" and coachid in(select coachid from CuserInfo where phone like '%" + coachphone + "%')");
		}
		if (!CommonUtils.isEmptyString(studentphone)) {
			cuserhql.append(" and studentid in(select studentid from SuserInfo where phone like '%" + studentphone + "%')");
		}
		if (!CommonUtils.isEmptyString(startminsdate)) {
			cuserhql.append(" and start_time >='" + startminsdate + "'");
		}
		if (!CommonUtils.isEmptyString(startmaxsdate)) {
			Date startdate = CommonUtils.getDateFormat(startmaxsdate, "yyyy-MM-dd");
			startdate.setHours(23);
			startdate.setMinutes(59);
			startdate.setSeconds(59);
			String startmaxstime = CommonUtils.getTimeFormat(startdate, "yyyy-MM-dd HH:mm:ss");
			cuserhql.append(" and start_time <='" + startmaxstime + "'");
		}
		if (!CommonUtils.isEmptyString(endminsdate)) {
			cuserhql.append(" and end_time >='" + endminsdate + "'");
		}
		if (!CommonUtils.isEmptyString(endmaxsdate)) {
			Date enddate = CommonUtils.getDateFormat(endmaxsdate, "yyyy-MM-dd");
			enddate.setHours(23);
			enddate.setMinutes(59);
			enddate.setSeconds(59);
			String endmaxstime = CommonUtils.getTimeFormat(enddate, "yyyy-MM-dd HH:mm:ss");
			cuserhql.append(" and end_time <='" + endmaxstime + "'");
		}
		if (state == 1) {
			cuserhql.append(" and studentstate in(0,2)");
		}
		if (state == 2) {
			cuserhql.append(" and studentstate = 3");
		}
		if (state == 3) {
			cuserhql.append(" and studentstate = 4");
		}
		if (!CommonUtils.isEmptyString(inputordertotal)) {
			if (ordertotal == 0) {
				cuserhql.append(" and total >" + inputordertotal);
			}
			if (ordertotal == 1) {
				cuserhql.append(" and total =" + inputordertotal);
			}
			if (ordertotal == 2) {
				cuserhql.append(" and total <" + inputordertotal);
			}
		}
		if (ishavacomplaint == 1) {
			cuserhql.append(" and orderid in (select orderid from OrderRecordInfo where operation in(7,9))");
		}
		if (ishavacomplaint == 2) {
			cuserhql.append(" and orderid not in (select orderid from OrderRecordInfo where operation in(7,9))");
		}
		cuserhql.append(" order by creat_time desc");
		List<OrderInfo> orderlist = (List<OrderInfo>) dataDao.pageQueryViaParam(cuserhql.toString(), pagesize, pageIndex, null);
		if (orderlist != null && orderlist.size() > 0) {
			for (OrderInfo order : orderlist) {
				SuserInfo student = dataDao.getObjectById(SuserInfo.class, order.getStudentid());
				CuserInfo coach = dataDao.getObjectById(CuserInfo.class, order.getCoachid());
				if (student != null) {
					order.setStudentinfo(student);
				}
				if (coach != null) {
					order.setCuserinfo(coach);
				}
				StringBuffer cuserhql1 = new StringBuffer();
				cuserhql1.append("from ComplaintInfo where order_id =:orderid ");
				String[] params = { "orderid" };
				String countnumhql = " select count(*) " + cuserhql1.toString();
				long complaintnum = (Long) dataDao.getFirstObjectViaParam(countnumhql, params, order.getOrderid());
				order.setComplaintnum(complaintnum);
			}
		}
		String counthql = " select count(*) " + cuserhql.toString();
		long total = (Long) dataDao.getFirstObjectViaParam(counthql, null);
		return new QueryResult<OrderInfo>(orderlist, total);
	}

	@Override
	public OrderRecordInfo getOrderRecordByType(int orderid, int type) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from OrderRecordInfo where orderid =:orderid and operation =" + type);
		String[] params = { "orderid" };
		OrderRecordInfo orderrecord = (OrderRecordInfo) dataDao.getFirstObjectViaParam(cuserhql.toString(), params, orderid);
		return orderrecord;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void addOrderRecord(OrderRecordInfo orderRecord) {
		dataDao.addObject(orderRecord);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void updateOrderInfo(OrderInfo order) {
		dataDao.updateObject(order);
	}

	@Override
	public List<ComplaintInfo> getComplaintByType(int orderid) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from ComplaintInfo where order_id =:orderid and type = 1 and state = 0");
		String[] params = { "orderid" };
		List<ComplaintInfo> complaintlist = (List<ComplaintInfo>) dataDao.getObjectsViaParam(cuserhql.toString(), params, orderid);
		return complaintlist;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void SettlementOrder(int orderid) {
		String hql = "from OrderRecordInfo where operation = 4 and orderid = :orderid";
		String[] params = { "orderid" };
		OrderRecordInfo recordinfo = (OrderRecordInfo) dataDao.getFirstObjectViaParam(hql, params, orderid);

		OrderInfo order = dataDao.getObjectById(OrderInfo.class, orderid);
		if (order != null) {
			if(order.getCoachstate()==5){//特殊订单，待处理订单，不能结算，直接返回
				return;
			}
			
			order.setStudentstate(3);// 设置订单状态为已结算

			if (recordinfo != null) {// 如果教练确认下车过的话
				order.setOver_time(new Date());
				// 教练金额的修改
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

				// 更新学院与教练的关系
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

	@Override
	public List<OrderInfo> getAllOrder() {
		List<OrderInfo> orderlist = dataDao.getAllObject(OrderInfo.class);
		return orderlist;
	}

	@Override
	public List<OrderInfo> getOrderBydate(String startdate, String enddate) {
		String hql="from OrderInfo where DATE(start_time)>=:start_time and DATE(end_time)<=:end_time";
		String[] params={"start_time","end_time"};
		List<OrderInfo> orderlist = (List<OrderInfo>) dataDao.getObjectsViaParam(hql, params,CommonUtils.getDateFormat(startdate, "yyyy-MM-dd"),CommonUtils.getDateFormat(enddate, "yyyy-MM-dd"));
		return orderlist;
	}
}
