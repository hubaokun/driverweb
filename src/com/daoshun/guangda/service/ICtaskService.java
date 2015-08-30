package com.daoshun.guangda.service;

import java.util.List;

import com.daoshun.common.QueryResult;
import com.daoshun.guangda.pojo.ComplaintInfo;
import com.daoshun.guangda.pojo.CuserInfo;
import com.daoshun.guangda.pojo.DriveSchoolInfo;
import com.daoshun.guangda.pojo.EvaluationInfo;
import com.daoshun.guangda.pojo.FeedBackInfo;
import com.daoshun.guangda.pojo.NoticesInfo;
import com.daoshun.guangda.pojo.NoticesUserInfo;
import com.daoshun.guangda.pojo.OrderInfo;
import com.daoshun.guangda.pojo.OrderRecordInfo;
import com.daoshun.guangda.pojo.StudentCheckInfo;
import com.daoshun.guangda.pojo.SuserInfo;
import com.daoshun.guangda.pojo.VersionInfo;

/**
 * @author liukn
 * 
 */
public interface ICtaskService {

	
	public abstract List<EvaluationInfo> getEvaluationList(int userid, int type);
	
	public abstract void SettlementOrderWhenCoachDown(int orderid);
	
	public abstract void SettlementOrder(int orderid);
	
	/** 根据教练id分页获取订单信息 **/
	public abstract List<OrderInfo> getOrderInfoListBycoachid(String coachid, int page, int count);
	/*
	 * 根据教练ID查询过滤掉教练已经同意取消的所有订单
	 */
	public List<OrderInfo> getOrderNoExistAgreeInfoListBycoachid(String coachid, int page, int count) ;

	/** 根据教练任务的状态查找订单 **/
	public abstract List<OrderInfo> getOrderInfoListBycoach(int coachstate, int coachid);

	/** 更新orderInfo **/
	public abstract void updateOrderInfo(OrderInfo orderinfo);

	/** 根据教练该订单的状态分页获取其历史订单 **/
	public abstract List<OrderInfo> getPageOrderInfoListBycoach(int coachstate, int coachid, int page, int count);

	/** 根据订单信息得到评价信息 **/
	public abstract EvaluationInfo getEvaluationInfoByorder(int orderid, int from_user, int to_user, int type);

	/** 根据记录id和操作类型和教练id 找出记录信息 **/
	public abstract OrderRecordInfo getOrderRecordInfo(int orderid, int coachid, int operation);

	/** 根据orderid得到订单信息 **/
	public abstract OrderInfo getOrderInfo(int orderid);

	/** 添加订单操作状态信息 **/
	public abstract void addOrderRecordInfo(OrderRecordInfo orderRecordInfo);

	/** 添加评价信息 **/
	public abstract void addEvaluationInfo(EvaluationInfo evaluationInfo, int type);

	/** 根据参数得到评价信息 **/
	public abstract EvaluationInfo getEvaluationInfo(int orderid, int userid, int type);

	/** 根据订单id找出其对应的投诉信息 **/
	public abstract List<ComplaintInfo> getComplaintInfoByorderid(int order_id);

	/** 根据学生id得到学生对象信息 **/
	public abstract SuserInfo getSuserInfoBysuserid(int studentid);

	/** 根据教练id得到教练对象信息 **/
	public abstract CuserInfo getCoachInfoById(int coachid);

	/** 更新学生教练认证信息 **/
	public abstract void updateSuserInfo(SuserInfo suserInfo);

	/** 更新教练信息 **/
	public abstract void updateCoachInfo(CuserInfo coach);

	/** 增加学生认证信息 **/
	public abstract void addStudentCheckInfo(StudentCheckInfo studentCheckInfo);

	/********************************************** ACTION *****************************************************/
	/** 获得意见反馈列表 **/
	public abstract List<FeedBackInfo> getFeedbackInfo();

	/** 根据类型找到反馈者信息 **/
	public abstract Object getFromuserBy(int fromid, int type);

	/** 根据id找到反馈对象 **/
	public abstract FeedBackInfo getfeedbackByid(int id);

	/** 根据关键字搜索意见反馈信息 **/
	public abstract List<FeedBackInfo> getFeedbackBykeyword(String searchname, String searchphone, String starttime, String endtime);

	/** 得到版本列表 **/
	public abstract List<VersionInfo> getVersion();

	/** 根据id找到版本信息 **/
	public abstract VersionInfo getVersionInfoById(int versionid);

	/** 分页获取通知列表信息 **/
	public abstract QueryResult<NoticesInfo> getNoticesInfoList(String noticestarttime, String noticeendtime,Integer pageIndex, int pagesize);

	/** 根据学校名称找到驾校 **/
	public abstract DriveSchoolInfo getDriveSchoolByname(String name);
	
	/** 根据学校ID找到驾校 **/
	public abstract DriveSchoolInfo getDriveSchoolByid(int schoolid);

	/** 根据id找到通知信息 **/
	public abstract NoticesInfo getNoticeById(int noticeid);

	/** 根据关键字搜索系统通知 **/
	public abstract QueryResult<NoticesInfo> getNoticeBykeyword(String noticestarttime, String noticeendtime, Integer pageIndex, int pagesize);

	/** 根据id得到目标用户信息 **/
	public abstract NoticesUserInfo getNoticeUserInfoByid(int noticeid);

	/** 根据关键字模糊搜索用户 **/
	public abstract List<?> getUserInfo(Integer usertype, String searchname);

	/** 根据信息去发送通知 **/
	public abstract void setMessageByInfo(Integer usertype, Integer settype, Integer singleuserid, String category, String contents);

	/** 得到教练所有的学员 **/
	public abstract List<OrderInfo> getStudentofCoach(int coachid, Integer usertype);

	/** 根据订单id得到头数数量 **/
	public abstract int getCountByorderid(int id);

	public abstract QueryResult<FeedBackInfo> getFeedbackInfoOfPage(String searchname, String searchphone, String starttime, String endtime, Integer pageIndex, int pageSize);
	public List<OrderInfo> getHistoryOrderListByCoach( int coachid, int page, int count);
}
