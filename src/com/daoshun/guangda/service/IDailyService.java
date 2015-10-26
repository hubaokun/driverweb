package com.daoshun.guangda.service;

import java.util.Date;
import java.util.List;

import com.daoshun.common.QueryResult;
import com.daoshun.guangda.pojo.OrderInfo;


public interface IDailyService {

	/**
	 * 统计学员数量
	 * @param addtime
	 * @return
	 */
	public abstract int getSuserCount(String addtime);

	/**
	 * 统计教练数量
	 * @param addtime
	 * @return
	 */
	public abstract int getCuserCount(String addtime);

	public abstract List<Object> getSystemdatal(String addtime);
	
	public abstract List<Object> getSchoolDaily(String addtime);
	
	public abstract List<Object> getCoachDaily(String addtime);
	
	public abstract List<Object> getStudentApplyDaily(String addtime);
	
	public abstract List<Object> getCoachApplyDaily(String addtime);
	
	public abstract Object getStudentAccountDaily(String addtime);
	
	public abstract Object getCoachAccountDaily(String addtime);
	
	public abstract List<Object> getSchoolBillDaily(String starttime,String addtime,String schoolname);
	
	public abstract List<Object> getXiaoBaDaily(String starttime,String addtime);
	
	public abstract Object getAccountReport(String addtime);

	public abstract List<Object> getCouponReportMontly(Date startdate,Date enddate,String schoolId);
	
	public abstract List<Object> getCoinReportMontly(Date startdate,Date enddate);
	
    public abstract List<Object> getCouponReportDetail(String coachid,Date startdate,Date enddate);
	
	public abstract List<Object> getCoinReportDetail(String coachid,String ownertype,Date startdate,Date enddate);
	
	public abstract QueryResult<OrderInfo> getOrderInfos(String starttime,String endtime,int pageIndex,int pagesize);
	
	public abstract List<OrderInfo> getOrdersForExported(String date);
}
