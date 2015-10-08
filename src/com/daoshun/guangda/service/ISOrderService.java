package com.daoshun.guangda.service;

import java.util.Date;
import java.util.List;

import com.daoshun.common.QueryResult;
import com.daoshun.guangda.NetData.ComplaintNetData;
import com.daoshun.guangda.pojo.ComplaintBookInfo;
import com.daoshun.guangda.pojo.ComplaintInfo;
import com.daoshun.guangda.pojo.ComplaintSetInfo;
import com.daoshun.guangda.pojo.CuserInfo;
import com.daoshun.guangda.pojo.OrderInfo;
import com.daoshun.guangda.pojo.OrderPrice;
import com.daoshun.guangda.pojo.OrderRecordInfo;
import com.daoshun.guangda.pojo.SuserInfo;

public interface ISOrderService {

	public abstract void SettlementOrder(int orderid);
	
	/**
	 * 获取被投诉列表
	 * 
	 * @param studentid
	 * @param pagenum
	 * @return
	 */
	public abstract List<ComplaintNetData> getComplaintToMy(String studentid, String pagenum);

	/**
	 * 查询是否有更多被投诉
	 * 
	 * @param studentid
	 * @param pagenum
	 * @return
	 */
	public abstract int getComplaintToMyMore(String studentid, String pagenum);

	/**
	 * 获取投诉列表
	 * 
	 * @param studentid
	 * @param pagenum
	 * @return
	 */
	public abstract List<ComplaintNetData> getMyComplaint(String studentid, String pagenum);

	/**
	 * 查询是否有更多投诉
	 * 
	 * @param studentid
	 * @param pagenum
	 * @return
	 */
	public abstract int getMyComplaintMore(String studentid, String pagenum);

	/**
	 * 获取待评价订单列表
	 * 
	 * @param studentid
	 * @param pagenum
	 * @return
	 */
	public abstract List<OrderInfo> getWaitEvaluationOrder(String studentid, String pagenum);

	/**
	 * 查询是否有更多待评价订单
	 * 
	 * @param studentid
	 * @param pagenum
	 * @return
	 */
	public abstract int getWaitEvaluationOrderMore(String studentid, String pagenum);

	/**
	 * 取得未完成订单列表
	 * 
	 * @param studentid
	 * @param pagenum
	 * @return
	 */
	public abstract List<OrderInfo> getUnCompleteOrder(String studentid, String pagenum);

	/**
	 * 查询是否有更多未完成订单
	 * 
	 * @param studentid
	 * @param pagenum
	 * @return
	 */
	public abstract int getUnCompleteOrderMore(String studentid, String pagenum);

	/**
	 * 获取已完成订单
	 * 
	 * @param studentid
	 * @param pagenum
	 * @return
	 */
	public abstract List<OrderInfo> getCompleteOrder(String studentid, String pagenum);

	/**
	 * 查询是否有更多已完成订单
	 * 
	 * @param studentid
	 * @param pagenum
	 * @return
	 */
	public abstract int getCompleteOrderMore(String studentid, String pagenum);

	/**
	 * 获取订单详细
	 * 
	 * @param studentid
	 * @param pagenum
	 * @return
	 */
	public abstract OrderInfo getCompleteOrder(String orderid);

	/**
	 * 获取投诉原因列表
	 * 
	 * @param type
	 * @return
	 */
	public abstract List<ComplaintSetInfo> getComplaintReason(String type);

	/**
	 * 投诉
	 * 
	 * @param userid
	 * @param orderid
	 * @param type
	 * @param reason
	 * @param content
	 */
	public abstract void addComplaint(String userid, String orderid, String type, String reason, String content);

	/**
	 * 取消订单
	 * 
	 * @param studentid
	 * @param orderid
	 * @return
	 */
	public abstract int cancelOrder(String studentid, String orderid);

	/**
	 * 取消订单投诉
	 * 
	 * @param studentid
	 * @param orderid
	 * @return
	 */
	public abstract void CancelComplaint(String studentid, String orderid);

	/***************************************** Action ******************************************************/
	/**
	 * 获取订单列表
	 * 
	 * @param pageIndex
	 * @param pagesize
	 * @return
	 */
	public abstract QueryResult<OrderInfo> getOrderList(String coachphone, String studentphone, String startminsdate, String startmaxsdate, String endminsdate, String endmaxsdate, String createminsdate, String createmaxsdate, Integer state,
			Integer ordertotal, String inputordertotal, Integer ishavacomplaint,Integer paytype,Integer ordertype, Integer pageIndex, int pagesize);

	/**
	 * 获取投诉列表
	 * 
	 * @param pageIndex
	 * @param pagesize
	 * @return
	 */
	public abstract QueryResult<ComplaintInfo> getComplaintList(Integer pageIndex, int pagesize);

	/**
	 * 获取投诉详细
	 * 
	 * @param complaintid
	 * @return
	 */
	public abstract ComplaintInfo getComplaintById(int complaintid);

	/**
	 * 获取订单详细
	 * 
	 * @param orderid
	 * @return
	 */
	public abstract OrderInfo getOrderById(int orderid);

	/**
	 * 关键字搜索投诉
	 * 
	 * @param complaintid
	 * @param from_user
	 * @param to_user
	 * @param type
	 * @param minsdate
	 * @param maxsdate
	 * @param pageIndex
	 * @param pagesize
	 * @return
	 */
	public abstract QueryResult<ComplaintInfo> getComplaintByKeyword(Integer complaintid, String studentphone, String coachphone, Integer type, String minsdate, String maxsdate, Integer state,
			Integer pageIndex, int pagesize);

	/**
	 * 获取教练详细
	 * 
	 * @param id
	 * @return
	 */
	public abstract CuserInfo getCoachById(int id);

	/**
	 * 获取学员详细
	 * 
	 * @param id
	 * @return
	 */
	public abstract SuserInfo getStudentById(int id);

	/**
	 * 修改投诉状态
	 * 
	 * @param complaint
	 */
	public abstract void updateComplaintState(ComplaintInfo complaint);

	/**
	 * 根据时间获取投诉列表
	 * 
	 * @param polltime
	 * @return
	 */
	public abstract List<ComplaintInfo> getComplaintByTime(String polltime);

	/**
	 * 获取投诉处理备注列表
	 * 
	 * @param polltime
	 * @return
	 */
	public abstract List<ComplaintBookInfo> getComplaintBookList(int complaintid);

	/**
	 * 添加投诉处理备注
	 * 
	 * @param complaintbook
	 */
	public abstract void addComplaintBook(ComplaintBookInfo complaintbook);

	/**
	 * 获取订单状态列表
	 * 
	 * @param orderid
	 * @return
	 */
	public abstract List<OrderRecordInfo> getOrderRecord(int orderid);

	/**
	 * 获取订单时间段价格列表
	 * 
	 * @param orderid
	 * @return
	 */
	public abstract List<OrderPrice> getOrderPriceList(int orderid);

	/**
	 * 根据关键字获取订单
	 * 
	 * @param coachphone
	 * @param studentphone
	 * @param startminsdate
	 * @param startmaxsdate
	 * @param endminsdate
	 * @param endmaxsdate
	 * @param state
	 * @param ordertotal
	 * @param inputordertotal
	 * @param ishavacomplaint
	 * @param pageIndex
	 * @param pagesize
	 * @return
	 */
	public abstract QueryResult<OrderInfo> getOrderByKeyword(String coachphone, String studentphone, String startminsdate, String startmaxsdate, String endminsdate, String endmaxsdate, Integer state,
			Integer ordertotal, String inputordertotal, Integer ishavacomplaint, Integer pageIndex, int pagesize);

	/**
	 * 获取订单状态
	 * 
	 * @param orderid
	 * @return
	 */
	public abstract OrderRecordInfo getOrderRecordByType(int orderid, int type);

	/**
	 * 添加订单状态
	 * 
	 * @param orderRecord
	 */
	public abstract void addOrderRecord(OrderRecordInfo orderRecord);

	/**
	 * 修改订单状态
	 * 
	 * @param order
	 */
	public abstract void updateOrderInfo(OrderInfo order);

	/**
	 * 根据订单id获取投诉
	 * 
	 * @param orderid
	 * @return
	 */
	public abstract List<ComplaintInfo> getComplaintByType(int orderid);
	
	/**
	 * 获取订单
	 * @return
	 */
	public abstract List<OrderInfo> getAllOrder();
	/**
	 * 学员取消订单
	 * @param studentid
	 * @param orderid
	 * @return
	 */
	public int cancelOrderByStudent(String studentid, String orderid);
	/**
	 * 教练 同意取消订单
	 * @param orderid 订单ID
	 * @return
	 */
	public int cancelOrderByCoach(String orderid,String agree);
	/**
	 * 根据日期获取orderlist
	 * @param startdate 开始时间
	 * @param enddate 结束时间
	 */
	public List<OrderInfo> getOrderBydate(String startdate,String enddate);
	/**
	 * 被投诉的订单
	 * @param studentid
	 * @param pagenum
	 * @return
	 */
	public List<OrderInfo> getComplaintOrder(String studentid, String pagenum);
	public int getComplaintOrderMore(String studentid, String pagenum);
	public List<OrderInfo> getWaitDealwithOrder(String studentid, String pagenum);
}
