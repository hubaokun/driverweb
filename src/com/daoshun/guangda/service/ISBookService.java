package com.daoshun.guangda.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import com.daoshun.guangda.pojo.CaddAddressInfo;
import com.daoshun.guangda.pojo.CouponRecord;
import com.daoshun.guangda.pojo.CscheduleInfo;
import com.daoshun.guangda.pojo.CsubjectInfo;
import com.daoshun.guangda.pojo.CuserInfo;
import com.daoshun.guangda.pojo.SystemSetInfo;

public interface ISBookService {

	public abstract SystemSetInfo getSystemSetInfo();

	public abstract List<CouponRecord> getCanUseCouponList(String studentid, String coachid);

	public abstract List<CouponRecord> getHisCouponList(String studentid);

	public abstract List<CouponRecord> getcouponList(String studentid);

	public abstract int getDefaultRest(String coachid, String hour);

	public abstract BigDecimal getDefaultPrice(String coachid, String hour);

	public abstract CsubjectInfo getDefaultSubject(String coachid, String hour);

	public abstract CaddAddressInfo getDefaultAddress(String coachid, String hour);
	public HashMap<String, Object> getCommentsFromStudent(String coachid,String studentid, String pagenum);

	/**
	 * 获取教练详细
	 * 
	 * @param coachid
	 * @return
	 */
	public abstract CuserInfo getCoachDetail(String coachid);

	/**
	 * 刷新教练日程
	 * 
	 * @param coachid
	 * @param date
	 * @return
	 */
	public abstract List<CscheduleInfo> refreshCoachSchedule(String coachid, String date, String studentid);

	/**
	 * 获取附近教练
	 * 
	 * @param pointcenter
	 * @param radius
	 * @param condition1
	 * @param condition2
	 * @return
	 */
	public abstract List<CuserInfo> getNearByCoach(String pointcenter, String radius, String condition1, String condition2, String condition3, String condition4, String condition5, String condition6,
			String condition8, String condition9, String condition10, String condition11);
	/**
	 * 原接口上添加返回教练订单总数
	 * @return
	 */
	public abstract List<CuserInfo> getNearByCoach2(String cityid,String pointcenter, String radius, String condition1, String condition2, String condition3, String condition4, String condition5, String condition6,
			String condition8, String condition9, String condition10, String condition11);

	/**
	 * 获取教练列表
	 * 
	 * @param condition1
	 * @param condition2
	 * @return
	 */
	public abstract HashMap<String, Object> getCoachList(String condition1, String condition2, String condition3, String condition4, String condition5, String condition6, String condition8,
			String condition9, String condition10, String condition11, String pagenum);
	/**
	 * 获取教练列表新增城市ID
	 * 
	 * @param condition1
	 * @param condition2
	 * @return
	 */
	public abstract HashMap<String, Object> getCoachList2(String cityid,String condition1, String condition2, String condition3, String condition4, String condition5, String condition6, String condition8,
			String condition9, String condition10, String condition11, String pagenum);
	

	/**
	 * 预定教练
	 * 
	 * @param coachid
	 * @param studentid
	 * @param date
	 * @return
	 */
	public abstract HashMap<String, Object> bookCoach(String coachid, String studentid, String date);
	//新增不同支付处理
	public HashMap<String, Object> bookCoach2(String coachid, String studentid, String date);

	/**
	 * 获取教练的评论数量
	 * 
	 * @param coachid
	 *            教练ID
	 * @param pagenum
	 *            获取页数
	 * @return
	 */
	public abstract HashMap<String, Object> getCoachComments(String coachid,String type, String pagenum);

}
