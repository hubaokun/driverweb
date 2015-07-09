package com.daoshun.guangda.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.daoshun.common.CommonUtils;
import com.daoshun.guangda.service.IDailyService;

@Service("dailyService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class DailyServiceImpl extends BaseServiceImpl implements IDailyService {

	@Override
	public int getSuserCount(String addtime) {
		long count = 0;
		StringBuffer systemhql = new StringBuffer();
		systemhql.append("from SuserInfo where 1=1");
		if(CommonUtils.isEmptyString(addtime)){
			systemhql.append(" and addtime <= '" + addtime + "'");
		}
		systemhql.insert(0, "select count(*)");
		count = (long) dataDao.getFirstObjectViaParam(systemhql.toString(), null);
		return (int) count;
	}

	@Override
	public int getCuserCount(String addtime) {
		long count = 0;
		StringBuffer systemhql = new StringBuffer();
		systemhql.append("from CuserInfo where 1=1");
		if(CommonUtils.isEmptyString(addtime)){
			systemhql.append(" and addtime <= '" + addtime + "'");
		}
		systemhql.insert(0, "select count(*)");
		count = (long) dataDao.getFirstObjectViaParam(systemhql.toString(), null);
		return (int) count;
	}

	@Override
	public List<Object> getSystemdatal(String addtime) {
		List<Object> Object = dataDao.callSyetem(addtime);
		return Object;
	}

	@Override
	public List<Object> getSchoolDaily(String addtime) {
		List<Object> Object = dataDao.callSchoolDaily(addtime);
		return Object;
	}

	@Override
	public List<Object> getCoachDaily(String addtime) {
		List<Object> Object = dataDao.callCoachDaily(addtime);
		return Object;
	}

	@Override
	public List<Object> getStudentApplyDaily(String addtime) {
		List<Object> Object = dataDao.callStudentApplyDaily(addtime);
		return Object;
	}

	@Override
	public List<Object> getCoachApplyDaily(String addtime) {
		List<Object> Object = dataDao.callCoachApplyDaily(addtime);
		return Object;
	}

	@Override
	public Object getStudentAccountDaily(String addtime) {
		Object object = dataDao.callStudentAccountDaily(addtime);
		return object;
	}

	@Override
	public Object getCoachAccountDaily(String addtime) {
		Object object = dataDao.callCoachAccountDaily(addtime);
		return object;
	}

	@Override
	public List<Object> getSchoolBillDaily(String starttime, String addtime,String schoolname) {
		List<Object> Object = dataDao.callSchoolBillDaily(starttime,addtime,schoolname);
		return Object;
	}

	@Override
	public List<Object> getXiaoBaDaily(String starttime, String addtime) {
		List<Object> Object = dataDao.callXiaoBaDaily(starttime,addtime);
		return Object;
	}

}
