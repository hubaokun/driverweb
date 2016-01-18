package com.daoshun.guangda.service;

import java.util.List;

import com.daoshun.common.QueryResult;
import com.daoshun.guangda.pojo.CoachStudentInfo;
import com.daoshun.guangda.pojo.CuserInfo;
import com.daoshun.guangda.pojo.DriveSchoolInfo;
import com.daoshun.guangda.pojo.SchoolBalance;

public interface IDriveSchoolService {

	public abstract QueryResult<SchoolBalance> getSchoolBalanceByPage(int pageIndex, int pageSize,
			int schoolid,Integer type,String starttime,String endtime);
	
	public abstract List<DriveSchoolInfo> getDriveschoollist(String keyword);
	
	public abstract DriveSchoolInfo getDriveSchoolInfoByid(int id);
	
	public abstract List<CuserInfo> getCoachByschoolid(int schoolid);
	
	public abstract List<CoachStudentInfo> getCoachStudentInfoByCoachid(int id);
	
	public abstract List<CuserInfo> getCuserListByKeyname(String name);
	/**
	 * 根据城市ID查询驾校信息
	 * @param cityid
	 * @return
	 */
	public List<DriveSchoolInfo> getDriveschoolByCityName(String cityid) ;
	
	
	public List<DriveSchoolInfo> getDriveschoolByCityId(String cityid) ;
}
