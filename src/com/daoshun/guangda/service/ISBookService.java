package com.daoshun.guangda.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.daoshun.guangda.pojo.AppCuserInfo;
import com.daoshun.guangda.pojo.CaddAddressInfo;
import com.daoshun.guangda.pojo.CouponRecord;
import com.daoshun.guangda.pojo.CscheduleInfo;
import com.daoshun.guangda.pojo.CsubjectInfo;
import com.daoshun.guangda.pojo.CuserInfo;
import com.daoshun.guangda.pojo.ModelPrice;
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
	 * 刷新教练日程 2.0新版
	 * 
	 * @param coachid
	 * @param date
	 * @return
	 */
	public abstract List<CscheduleInfo> refreshCoachScheduleNew(String coachid, String date, String studentid,String scheduletype);
	public int getCoachState(String coachid, int datacount, Date startdate, int starthour, int endhour,int subjectid);
	public void remindCoach(String coachid,String studentid,String date);
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
	public abstract List<AppCuserInfo> getNearByCoach2(String cityid,String pointcenter, String radius, String condition1, String condition2, String condition3, String condition4, String condition5, String condition6,
			String condition8, String condition9, String condition10, String condition11,String studentid,String driveschollid,String fixedposition);

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

	//新版2.0预约流程
	public HashMap<String, Object> bookCoachNew(String coachid, String studentid, String date);

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
	public HashMap<String, Object> getCoachListByRedis(String cityid,String condition1, String condition2, String condition3, String condition4, String condition5, String condition6, String condition8, String condition9,
			String condition10, String condition11, String pagenum) ;
	public int getRemindState(String coachid,String studentid,String date);
	public List<ModelPrice> getModelPriceByCityId(int cityid);
	public List<ModelPrice> getOpenModelPrice();
	public void addOpenModelPrice(ModelPrice mp);
	public HashMap<String, Object> getCoachList3(String cityid,String condition1, String condition2, String condition3, String condition4, String condition5, String condition6, String condition8, String condition9,
			String condition10, String condition11, String pagenum,String studentid,String driveschollid,String fixedposition,String pointcenter);
	public HashMap<String, Object> getCoachListAccompany(String cityid, String pagenum,String fixedposition,String pointcenter,String studentid);
	public int[] getCanUseCoinMoney(String coachid,String studentid);
	public ModelPrice getModelPriceById(int cityid);
	/**
	 *  删除开通城市
	 * @param citysId  cityId集合
	 */
	public void deleteOpenModelPrice(String citysId);
	/**
	 *  更新城市
	 * @param mp
	 */
	public void updateOpenModelPrice(ModelPrice mp);
	/*public void getCoinException();*/
}
