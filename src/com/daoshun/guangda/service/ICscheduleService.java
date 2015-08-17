package com.daoshun.guangda.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.daoshun.guangda.pojo.CBookTimeInfo;
import com.daoshun.guangda.pojo.CaddAddressInfo;
import com.daoshun.guangda.pojo.CscheduleInfo;
import com.daoshun.guangda.pojo.CsubjectInfo;
import com.daoshun.guangda.pojo.DefaultSchedule;
import com.daoshun.guangda.pojo.SystemSetInfo;

public interface ICscheduleService {

	public abstract void addDefaultSet(int coachid, CscheduleInfo info);

	public abstract int getDefaultRest(String coachid, String hour);

	public abstract BigDecimal getDefaultPrice(String coachid, String hour);

	public abstract CsubjectInfo getDefaultSubject(String coachid, String hour);

	public abstract CaddAddressInfo getDefaultAddress(String coachid, String hour);

	public abstract int getDefaultCancelBydate(String date);

	/**
	 * 获取教练的默认日程设置
	 * 
	 * @param coachid
	 * @return
	 */
	public abstract DefaultSchedule getCoachDefaultSchedule(String coachid);

	/**
	 * 查询教练当天是否设置过日程
	 * 
	 * @param coachid
	 * @param day
	 * @return
	 */
	public abstract boolean isSetSchedule(String coachid, String day);

	/**
	 * 通过教练id得到30天内的形成安排列表
	 * 
	 * @param coachid
	 *            教练id
	 * @param starttime
	 *            当前时间
	 * @param endtime
	 *            30天内
	 * @return
	 */
	public abstract List<CscheduleInfo> getScheduleByCoachidandDate(String coachid, String starttime, String endtime);

	/**
	 * 修改日程表信息
	 * 
	 * @param schedule
	 */
	public abstract void updateScheduleInfo(CscheduleInfo schedule);

	/**
	 * 删除某天的日程安排
	 * 
	 * @param schedule
	 */
	public abstract void delScheduleInfo(CscheduleInfo schedule);

	/**
	 * 添加日程
	 * 
	 * @param schedule
	 */
	public abstract void addScheduleInfo(CscheduleInfo schedule);

	/**
	 * 根据日期和教练id找到日程
	 * 
	 * @param day
	 * @param coachid
	 * @return
	 */
	public abstract CscheduleInfo getCscheduleByday(String coachid, String day, String hour);

	/**
	 * 得到所有的节假日
	 * 
	 * @return
	 */
	public abstract SystemSetInfo getholidays();

	/**
	 * 根据日期得到当天日程安排
	 * 
	 * @param date
	 * @return
	 */
	public abstract CscheduleInfo getCscheduleInfoByDate(String date);

	public List<CscheduleInfo> getCscheduleInfoByDatelist(String date, String coachid);

	public CBookTimeInfo getcoachbooktime(String hour, int coachid, String date);
	
	/**
	 * 调用函数来确定教练是否开课
	 */
	public void getCoachStateByFunction(String coachid,int datacount,int starthour,int endhour,int subjectid);
	public boolean getIsbookedBybooktime(String hour, int coachid, String date) ;
	/**
	 * 获取教练开课默认设置（包括所有项） 
	 */
	public abstract Map getAllCoachscheduleinfo(String coachid, String hour);
}
