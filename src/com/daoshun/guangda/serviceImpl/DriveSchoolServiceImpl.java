package com.daoshun.guangda.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.daoshun.common.CommonUtils;
import com.daoshun.common.QueryResult;
import com.daoshun.guangda.pojo.CoachStudentInfo;
import com.daoshun.guangda.pojo.CuserInfo;
import com.daoshun.guangda.pojo.DriveSchoolInfo;
import com.daoshun.guangda.pojo.SchoolBalance;
import com.daoshun.guangda.service.IDriveSchoolService;

/**
 * @author wangcl
 * 
 */
@Service("driveSchoolService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class DriveSchoolServiceImpl extends BaseServiceImpl implements IDriveSchoolService{

	@SuppressWarnings("unchecked")
	@Override
	public QueryResult<SchoolBalance> getSchoolBalanceByPage(int pageIndex, int pageSize, int schoolid,Integer type, String starttime, String endtime) {
		StringBuffer schoolbalancehql = new StringBuffer();
		schoolbalancehql.append(" from SchoolBalance where type = 2");
		if(schoolid > 0){
			schoolbalancehql.append(" and schoolid = "+schoolid);
		}
		if(!CommonUtils.isEmptyString(starttime)){
			schoolbalancehql.append(" and addtime > '"+starttime+"' ");
		}
		if(!CommonUtils.isEmptyString(endtime)){
			schoolbalancehql.append(" and addtime < '"+endtime + " 23:59:59' " );
		}
		if(type!=null){
			schoolbalancehql.append(" and type = "+ type);
		}
		List<SchoolBalance> schoolbalancelist = (List<SchoolBalance>) dataDao.pageQueryViaParam(schoolbalancehql.toString() + "order by addtime desc ", pageSize, pageIndex, null);
		for(SchoolBalance schoolbalance:schoolbalancelist){
			DriveSchoolInfo driveschool = dataDao.getObjectById(DriveSchoolInfo.class, schoolbalance.getSchoolid());
			schoolbalance.setDriveschool(driveschool);
		}
		String counthql = " select count(*) " + schoolbalancehql.toString();
		long count = (Long) dataDao.getFirstObjectViaParam(counthql,null);
		QueryResult<SchoolBalance> result = new QueryResult<SchoolBalance>(schoolbalancelist, count);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DriveSchoolInfo> getDriveschoollist(String keyword) {
		StringBuffer drihql=new StringBuffer();
		drihql.append(" from DriveSchoolInfo where name like '%"+keyword+"%' ");
		List<DriveSchoolInfo> schoollist=(List<DriveSchoolInfo>) dataDao.getObjectsViaParam(drihql.toString()+" order by schoolid ", null);
		return schoollist;
	}

	@Override
	public DriveSchoolInfo getDriveSchoolInfoByid(int id) {
		DriveSchoolInfo driveSchoolInfo=dataDao.getObjectById(DriveSchoolInfo.class, id);
		return driveSchoolInfo;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CuserInfo> getCoachByschoolid(int schoolid) {
		StringBuffer cuserhql=new StringBuffer();
		cuserhql.append(" from CuserInfo where drive_schoolid = :drive_schoolid");
		String[] params={"drive_schoolid"};
		List<CuserInfo> cuserlist=	(List<CuserInfo>) dataDao.getObjectsViaParam(cuserhql.toString(), params, schoolid);
		return cuserlist;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CoachStudentInfo> getCoachStudentInfoByCoachid(int id) {
		StringBuffer coahql=new StringBuffer();
		coahql.append(" from CoachStudentInfo where coachid = :coachid ");
		String[] params={"coachid"};
		List<CoachStudentInfo> coachstudentlist=	(List<CoachStudentInfo>) dataDao.getObjectsViaParam(coahql.toString(), params, id);
		return coachstudentlist;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CuserInfo> getCuserListByKeyname(String name) {
		StringBuffer coahql=new StringBuffer();
		coahql.append(" from CuserInfo where realname like '%"+name+"%'");
		List<CuserInfo> cuserlist=	(List<CuserInfo>) dataDao.getObjectsViaParam(coahql.toString(), null);
		return cuserlist;
	}
	/**
	 * 根据定位城市名称查询驾校信息
	 * @param cityid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DriveSchoolInfo> getDriveschoolByCityName(String cityname) {
		
		
		StringBuffer drihql=new StringBuffer();
		drihql.append(" from DriveSchoolInfo where cityid in (select cityid from CityInfo where city like '%"+cityname+"%')  order by addtime desc");
		List<DriveSchoolInfo> schoollist=(List<DriveSchoolInfo>) dataDao.getObjectsViaParam(drihql.toString(),null);
		for (DriveSchoolInfo ds : schoollist) {
			ds.setAddtime(null);
			ds.setAlipay_account(null);
			ds.setAreaid(null);
			ds.setContact(null);
			ds.setIscontract(null);
			ds.setMoney(null);
			ds.setOrder_pull(null);
			ds.setProvinceid(null);
			ds.setTelphone(null);
			ds.setCityid(null);
		}
		return schoollist;
	}

}
