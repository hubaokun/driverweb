package com.daoshun.guangda.service;

import java.util.List;


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

}
