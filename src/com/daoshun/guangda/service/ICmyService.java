package com.daoshun.guangda.service;

import java.util.HashMap;
import java.util.List;

import com.daoshun.guangda.NetData.ComplaintNetData;
import com.daoshun.guangda.NetData.EvaluationNetData;
import com.daoshun.guangda.model.StudentInfo;
import com.daoshun.guangda.pojo.CApplyCashInfo;
import com.daoshun.guangda.pojo.CaddAddressInfo;
import com.daoshun.guangda.pojo.CouponCoach;
import com.daoshun.guangda.pojo.CsubjectInfo;
import com.daoshun.guangda.pojo.CuserInfo;
import com.daoshun.guangda.pojo.NoticesInfo;
import com.daoshun.guangda.pojo.NoticesUserInfo;

public interface ICmyService {

	public abstract HashMap<String, Object> changeApplyType(String coachid, String setvalue);
	
	public abstract HashMap<String, Object> delAliAccount(String userid, String type);

	public abstract int getMyStudentCount(String coachid);

	public abstract HashMap<String, Object> getMyAllStudent(String coachid, String pageNum);

	public abstract void setAllMessageReaded(String coachid);

	public abstract HashMap<String, Object> applyCoupon(String coachid, String recordids);

	/** //申请兑现小巴币 **/
	public abstract HashMap<String, Object> applyCoin(String coachid, Integer applyCoinNum);

	public abstract List<CouponCoach> getCouponCoachList(String coachid);

	public abstract HashMap<String, Object> updateAliAccount(String userid, String type, String alicount,String cashtype);

	/** 设置教练单价 **/
	public abstract void setPrice(CuserInfo cuser);

	/** 增加教练地址 **/
	public abstract void addAddress(CaddAddressInfo cadd);

	/** 根据地址id找出对应的地址信息 **/
	public abstract CaddAddressInfo getCoachidByaddAddressid(String addressid);

	/** 删除地址 **/
	public abstract void delAddress(CaddAddressInfo cadd);

	/** 根据coachid找出他的地址集合 **/
	public abstract List<CaddAddressInfo> getAddressInfosByCoachid(String coachid);

	/** 设置地址使用状况 **/
	public abstract void setCurrentAddress(CaddAddressInfo cadd);

	/** 增加申请提现信息 **/
	public abstract void addApplyCash(CApplyCashInfo applycash);



	/** 分页根据coachid找到属于他的所有的通知 **/
	public abstract List<NoticesInfo> getNoticesInfoByuserid(String userid, int page, int count);

	/** 根据userid 找到通知列表 **/
	public abstract List<NoticesInfo> getNoticesInfoByuserid(String userid);

	/** 根据通知id找到用户通知关联表信息 **/
	public abstract NoticesUserInfo getNoticeUserInfoByNoticeid(int noticeid);

	/** 修改用户通知关联信息 **/
	public abstract void updateNoticesUserInfo(NoticesUserInfo noticesUserInfo);

	/** 根据noticeid得到NoticesInfo **/
	public abstract NoticesInfo getNoticesInfoByNoiticeid(int noticeid);

	/** 删除通知信息 **/
	public abstract void delNoticesInfo(NoticesInfo noticeInfo);

	/** 删除用户通知关联信息表信息 **/
	public abstract void delNoticesUserInfo(NoticesUserInfo noticesUserInfo);

	/** 根据信息分页获取所有投诉该教练的投诉 **/
	public abstract List<ComplaintNetData> getComplaintInfoByTo_user(int to_user, int pageNum);

	/** 根据信息分页获取该教练投诉的信息 **/
	public abstract List<ComplaintNetData> getComplaintInfoByFrom_user(int from_user, int pageNum);

	/** 根据信息分页获取评价该教练的订单评价信息 **/
	public abstract List<EvaluationNetData> getEvaluationInfoByTo_user(int to_user, int pageNum);

	/** 根据信息分页得到该教练评价的信息 **/
	public abstract List<EvaluationNetData> getEvaluationInfoByFrom_user(int From_user, int pageNum);

	/** 获取教练通知总条数 **/
	public abstract int allnoticecount(String coachid);

	/** 根据教练id获得其未读的通知数 **/
	public abstract int unnoticecount(String coachid);

	/** 根据教练id获得其被投诉的消息条数 **/
	public abstract int complaintmy(String coachid);

	/** 根据教练id获得其投诉的消息条数 **/
	public abstract int mycomplaint(String coachid);

	/** 根据教练id获取其被评论的消息条数 **/
	public abstract int evaluationmy(String coachid);

	/** 根据教练id获取其评论的消息条数 **/
	public abstract int myevaluation(String coachid);

	/** 根据科目id找到教学科目信息 **/
	public abstract CsubjectInfo getSubject(int subjectid);

	/**** 得到所有的教学科目信息 *****/
	public abstract List<CsubjectInfo> getAllSubject();

	/** 更新教学科目信息 **/
	public abstract void updateSubject(CsubjectInfo subject);
	public void updateNoticeState(int userid,int noticeid);
	public HashMap<String, Object> applyCoin(String coachid, Integer applyCoinNum,int type);
	
	/** 逻辑删除教练地址 **/
	public abstract void deleteAddressbylogic(CaddAddressInfo cadd);
	public void addCashOrder(String coachid,String count) ;
}
