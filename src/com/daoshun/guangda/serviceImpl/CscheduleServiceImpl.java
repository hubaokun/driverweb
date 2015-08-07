package com.daoshun.guangda.serviceImpl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.daoshun.common.CommonUtils;
import com.daoshun.guangda.pojo.CBookTimeInfo;
import com.daoshun.guangda.pojo.CaddAddressInfo;
import com.daoshun.guangda.pojo.CscheduleInfo;
import com.daoshun.guangda.pojo.CsubjectInfo;
import com.daoshun.guangda.pojo.CuserInfo;
import com.daoshun.guangda.pojo.DefaultSchedule;
import com.daoshun.guangda.pojo.SystemSetInfo;
import com.daoshun.guangda.service.ICscheduleService;

@Service("cscheduleService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class CscheduleServiceImpl extends BaseServiceImpl implements ICscheduleService {

	@SuppressWarnings("unchecked")
	@Override
	public List<CscheduleInfo> getScheduleByCoachidandDate(String coachid, String starttime, String endtime) {
		StringBuffer cschehql = new StringBuffer();
		cschehql.append("from CscheduleInfo where coachid = :coachid and date >= :starttime and date <= :endtime order by date ,cast(hour as int)");
		String[] params = { "coachid", "starttime", "endtime" };
		List<CscheduleInfo> schedulelist = (List<CscheduleInfo>) dataDao.getObjectsViaParam(cschehql.toString(), params, CommonUtils.parseInt(coachid, 0), starttime, endtime);
		return schedulelist;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void updateScheduleInfo(CscheduleInfo schedule) {
		dataDao.updateObject(schedule);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void delScheduleInfo(CscheduleInfo schedule) {
		dataDao.deleteObject(schedule);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void addScheduleInfo(CscheduleInfo schedule) {
		dataDao.addObject(schedule);
	}

	@Override
	public CscheduleInfo getCscheduleByday(String coachid, String day, String hour) {
		StringBuffer cschehql = new StringBuffer();
		cschehql.append("from CscheduleInfo where coachid = :coachid and date =:day and hour = :hour");
		String[] params = { "coachid", "day", "hour" };
		CscheduleInfo cschedule = (CscheduleInfo) dataDao.getFirstObjectViaParam(cschehql.toString(), params, CommonUtils.parseInt(coachid, 0), day, hour);
		return cschedule;
	}

	@Override
	public SystemSetInfo getholidays() {
		StringBuffer cschehql = new StringBuffer();
		cschehql.append("from SystemSetInfo ");
		SystemSetInfo holidaysInfo = (SystemSetInfo) dataDao.getFirstObjectViaParam(cschehql.toString(), null);
		return holidaysInfo;
	}

	@Override
	public CscheduleInfo getCscheduleInfoByDate(String date) {
		StringBuffer cschehql = new StringBuffer();
		cschehql.append("from CscheduleInfo where date = :date ");
		String[] params = { "date" };
		CscheduleInfo cscheduleInfo = (CscheduleInfo) dataDao.getFirstObjectViaParam(cschehql.toString(), params, date);
		return cscheduleInfo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CscheduleInfo> getCscheduleInfoByDatelist(String date, String coachid) {
		StringBuffer cschehql = new StringBuffer();
		cschehql.append("from CscheduleInfo where date = :date and coachid = :coachid");
		String[] params = { "date", "coachid" };
		List<CscheduleInfo> cscheduleInfolist = (List<CscheduleInfo>) dataDao.getObjectsViaParam(cschehql.toString(), params, date, CommonUtils.parseInt(coachid, 0));
		return cscheduleInfolist;
	}

	@Override
	public CBookTimeInfo getcoachbooktime(String hour, int coachid, String date) {
		StringBuffer cschehql = new StringBuffer();
		cschehql.append("from CBookTimeInfo where bookedtime = :hour and coachid = :coachid and date = :date");
		String[] params = { "hour", "coachid", "date" };
		CBookTimeInfo cBookTimeInfo = (CBookTimeInfo) dataDao.getFirstObjectViaParam(cschehql.toString(), params, hour, coachid, date);
		return cBookTimeInfo;
	}
	/**
	 * 教练是否在这个时间能预约
	 * @param hour 小时数
	 * @param coachid 教练ID
	 * @param date 日期
	 * @return
	 */
	public boolean getIsbookedBybooktime(String hour, int coachid, String date) {
		StringBuffer cschehql = new StringBuffer();
		cschehql.append("select count(*) from CBookTimeInfo where bookedtime = :hour and coachid = :coachid and date = :date");
		String[] params = { "hour", "coachid", "date" };
		Long n = (Long) dataDao.getFirstObjectViaParam(cschehql.toString(), params, hour, coachid, date);
		if(n>0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean isSetSchedule(String coachid, String day) {
		List<CscheduleInfo> list = getCscheduleInfoByDatelist(day, coachid);
		if (list != null && list.size() > 0)
			return true;
		return false;
	}

	@Override
	public DefaultSchedule getCoachDefaultSchedule(String coachid) {
		String hql = "from DefaultSchedule where coachid = :coachid";
		String[] param = { "coachid" };
		return (DefaultSchedule) dataDao.getFirstObjectViaParam(hql, param, CommonUtils.parseInt(coachid, 0));
	}

	@Override
	public int getDefaultCancelBydate(String date) {
		SystemSetInfo holidaysInfo = getholidays();
		Calendar d = Calendar.getInstance();
		d.setTime(CommonUtils.getDateFormat(date, "yyyy-MM-dd"));
		int weekday = d.get(Calendar.DAY_OF_WEEK);
		Date nnewnow = CommonUtils.getDateFormat(date, "yyyy-MM-dd");
		String lastnow = CommonUtils.getTimeFormat(nnewnow, "yyyy年MM月dd日");
		if ((!CommonUtils.isEmptyString(holidaysInfo.getHolidays()) && holidaysInfo.getHolidays().indexOf(lastnow) != -1) || weekday == Calendar.SATURDAY || weekday == Calendar.SUNDAY) {
			return 1;// 不可以取消
		} else {
			return 0;// 可以取消
		}
	}

	@Override
	public CaddAddressInfo getDefaultAddress(String coachid, String hour) {
		CaddAddressInfo info = null;
		String hql = "from DefaultSchedule where coachid = :coachid and hour = :hour";
		String[] param = { "coachid", "hour" };
		DefaultSchedule defaultSet = (DefaultSchedule) dataDao.getFirstObjectViaParam(hql, param, CommonUtils.parseInt(coachid, 0), hour);
		if (defaultSet != null) {
			info = dataDao.getObjectById(CaddAddressInfo.class, defaultSet.getAddressid());
		}
		return info;
	}

	@Override
	public CsubjectInfo getDefaultSubject(String coachid, String hour) {
		CsubjectInfo info = null;
		String hql = "from DefaultSchedule where coachid = :coachid and hour = :hour";
		String[] param = { "coachid", "hour" };
		DefaultSchedule defaultSet = (DefaultSchedule) dataDao.getFirstObjectViaParam(hql, param, CommonUtils.parseInt(coachid, 0), hour);
		if (defaultSet != null) {
			info = dataDao.getObjectById(CsubjectInfo.class, defaultSet.getSubjectid());
		}
		return info;
	}

	@Override
	public BigDecimal getDefaultPrice(String coachid, String hour) {
		BigDecimal info = null;
		String hql = "from DefaultSchedule where coachid = :coachid and hour = :hour";
		String[] param = { "coachid", "hour" };
		DefaultSchedule defaultSet = (DefaultSchedule) dataDao.getFirstObjectViaParam(hql, param, CommonUtils.parseInt(coachid, 0), hour);
		if (defaultSet != null) {
			info = defaultSet.getPrice();
		}
		return info;
	}

	@Override
	public int getDefaultRest(String coachid, String hour) {
		int result = -1;
		String hql = "from DefaultSchedule where coachid = :coachid and hour = :hour";
		String[] param = { "coachid", "hour" };
		DefaultSchedule defaultSet = (DefaultSchedule) dataDao.getFirstObjectViaParam(hql, param, CommonUtils.parseInt(coachid, 0), hour);
		if (defaultSet != null) {
			return defaultSet.getIsrest();
		}
		return result;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void addDefaultSet(int coachid, CscheduleInfo info) {
		String hql = "from DefaultSchedule where coachid = :coachid and hour = :hour";
		String[] params = { "coachid", "hour" };
		List<DefaultSchedule> list = (List<DefaultSchedule>) dataDao.getObjectsViaParam(hql, params, coachid, info.getHour());
		if (list != null && list.size() > 0) {
			if (list.size() > 1) {
				for (int i = 1; i < list.size(); i++) {
					dataDao.deleteObject(list.get(i));
				}
			}

			DefaultSchedule toUpdate = list.get(0);
			toUpdate.setUpdatetime(new Date());
			toUpdate.setSubjectid(info.getSubjectid());
			toUpdate.setState(info.getState());
			if (info.getPrice() != null) {
				toUpdate.setPrice(info.getPrice());
			} else {
				toUpdate.setPrice(new BigDecimal(0d));
			}

			toUpdate.setIsrest(info.getIsrest());
			toUpdate.setHour(info.getHour());
			toUpdate.setCoachid(info.getCoachid());
			toUpdate.setCancelstate(info.getCancelstate());
			toUpdate.setAddressid(info.getAddressid());
			dataDao.updateObject(toUpdate);
		} else {
			DefaultSchedule toUpdate = new DefaultSchedule();
			toUpdate.setUpdatetime(new Date());
			toUpdate.setSubjectid(info.getSubjectid());
			toUpdate.setState(info.getState());
			if (info.getPrice() != null) {
				toUpdate.setPrice(info.getPrice());
			} else {
				toUpdate.setPrice(new BigDecimal(0d));
			}
			toUpdate.setIsrest(info.getIsrest());
			toUpdate.setHour(info.getHour());
			toUpdate.setCoachid(info.getCoachid());
			toUpdate.setCancelstate(info.getCancelstate());
			toUpdate.setAddressid(info.getAddressid());
			dataDao.addObject(toUpdate);
		}

	}
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void getCoachStateByFunction(String coachid, int datacount,String day, int starthour, int endhour,
			int subjectid) {
		String querystring="from CuserInfo where coachid=:coachid";
		String querystring1="from CscheduleInfo where coachid=:coachid and date=:date and hour!=0 and isrest=1";
		String[] params={"coachid"};
		String[] params1={"coachid","date"};
		Date d=new Date();
		List querylist=dataDao.getCoachState(coachid, datacount,d, starthour, endhour, subjectid);
		CuserInfo tempCuserInfo=(CuserInfo)dataDao.getFirstObjectViaParam(querystring, params, CommonUtils.parseInt(coachid, 0));
		String result=querylist.get(0).toString();
		
		if(tempCuserInfo!=null)
		{
			if(result.equals("1"))
			{
				List<CscheduleInfo> tempCscheduleInfolist=(List<CscheduleInfo>) dataDao.getObjectsViaParam(querystring1, params1,CommonUtils.parseInt(coachid, 0),day);
				if(tempCscheduleInfolist.size()==19)
				{
					tempCuserInfo.setCoursestate(0);
				}
				else
				{
					tempCuserInfo.setCoursestate(Integer.parseInt(result));
				}
				dataDao.updateObject(tempCuserInfo);
			}
			else
			{
				tempCuserInfo.setCoursestate(Integer.parseInt(result));
				dataDao.updateObject(tempCuserInfo);
			}
			
		}
	}
}
