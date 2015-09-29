package com.daoshun.guangda.serviceImpl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.daoshun.common.CommonUtils;
import com.daoshun.guangda.pojo.AutoPositionInfo;
import com.daoshun.guangda.pojo.CBookTimeInfo;
import com.daoshun.guangda.pojo.CaddAddressInfo;
import com.daoshun.guangda.pojo.CscheduleInfo;
import com.daoshun.guangda.pojo.CsubjectInfo;
import com.daoshun.guangda.pojo.CuserInfo;
import com.daoshun.guangda.pojo.DefaultSchedule;
import com.daoshun.guangda.pojo.OrderInfo;
import com.daoshun.guangda.pojo.SuserInfo;
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
	public void updateScheduleInfoByList(List<CscheduleInfo> schedulelist) {
		for(CscheduleInfo c:schedulelist)
		{
			dataDao.updateObject(c);
		}	
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
	public void getCoachStateByFunction(String coachid, int datacount, int starthour, int endhour,
			int subjectid) {
		String querystring="from CuserInfo where coachid=:coachid";
		//String querystring1="from CscheduleInfo where coachid=:coachid and date=:date and hour!=0 and isrest=1";
		String[] params={"coachid"};
	//	String[] params1={"coachid","date"};
		Date d=new Date();
		List querylist=dataDao.getCoachState(coachid, datacount,d, starthour, endhour, subjectid);
		List querylist1=dataDao.getCoachStateAccompany(coachid, datacount,d, starthour, endhour);
		
		CuserInfo tempCuserInfo=(CuserInfo)dataDao.getFirstObjectViaParam(querystring, params, CommonUtils.parseInt(coachid, 0));
		String result=querylist.get(0).toString();
		String result1=querylist1.get(0).toString();
		if(tempCuserInfo!=null)
		{
				tempCuserInfo.setCoursestate(Integer.parseInt(result));
				tempCuserInfo.setAccompanycoursestate(Integer.parseInt(result1));
				dataDao.updateObject(tempCuserInfo);
		}
	}

	@Override
	public Map getAllCoachscheduleinfo(String coachid, String hour) {
		Map resultmap=new HashMap();
		BigDecimal info = null;
		String hql = "from DefaultSchedule where coachid = :coachid and hour = :hour";
		String[] param = { "coachid", "hour" };
		DefaultSchedule defaultSet = (DefaultSchedule) dataDao.getFirstObjectViaParam(hql, param, CommonUtils.parseInt(coachid, 0), hour);
		if (defaultSet != null) {
			info = defaultSet.getPrice();
		}
		int result = -1;
		if (defaultSet != null) {
			result=defaultSet.getIsrest();
		}
		CaddAddressInfo tempCaddAddressInfo = null;
		if (defaultSet != null) {
			tempCaddAddressInfo = dataDao.getObjectById(CaddAddressInfo.class, defaultSet.getAddressid());
		}
		CsubjectInfo tempCsubjectInfo = null;
		if (defaultSet != null) {
			tempCsubjectInfo = dataDao.getObjectById(CsubjectInfo.class, defaultSet.getSubjectid());
		}
		
		resultmap.put("info", info);
		resultmap.put("result", result);
		resultmap.put("CaddAddressInfo", tempCaddAddressInfo);
		resultmap.put("CsubjectInfo", tempCsubjectInfo);
		return resultmap;
	}
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void setDefaultNew(String coachid, String hour,String price,String addressid,String subjectid,String isrest) {
		String querystring="from DefaultSchedule where coachid=:coachid and hour=:hour";
		String[] params={"coachid","hour"};
		DefaultSchedule tempDefaultSchedule=(DefaultSchedule) dataDao.getFirstObjectViaParam(querystring, params, CommonUtils.parseInt(coachid, 0),hour);
	    if(tempDefaultSchedule!=null)
	    {
	    	BigDecimal b = new BigDecimal(CommonUtils.parseDouble(price, 0d));
	    	tempDefaultSchedule.setPrice(b);
	    	tempDefaultSchedule.setSubjectid(CommonUtils.parseInt(subjectid,0));
	    	tempDefaultSchedule.setAddressid(CommonUtils.parseInt(addressid,0));
	    	tempDefaultSchedule.setUpdatetime(new Date());
	    	tempDefaultSchedule.setIsrest(CommonUtils.parseInt(isrest, 1));
	    	dataDao.updateObject(tempDefaultSchedule);
	    }
	    else
	    {
	    	DefaultSchedule newDefaultSchedule =new DefaultSchedule();
	    	BigDecimal b = new BigDecimal(CommonUtils.parseDouble(price, 0d));
	    	newDefaultSchedule.setPrice(b);
	    	newDefaultSchedule.setSubjectid(CommonUtils.parseInt(subjectid,0));
	    	newDefaultSchedule.setAddressid(CommonUtils.parseInt(addressid,0));
	    	newDefaultSchedule.setUpdatetime(new Date());
	    	newDefaultSchedule.setHour(hour);
	    	newDefaultSchedule.setIsrest(CommonUtils.parseInt(isrest, 1));
	    	newDefaultSchedule.setCoachid(CommonUtils.parseInt(coachid, 0));
	    	dataDao.addObject(newDefaultSchedule);
	    }
	
	}

	@Override
	public List<DefaultSchedule> getDefaultNew(String coachid) {
		String querystring="from DefaultSchedule where coachid=:coachid and hour<>0";
		String[] params={"coachid"};
		List<DefaultSchedule> tempDefaultSchedule=(List<DefaultSchedule>) dataDao.getObjectsViaParam(querystring, params, CommonUtils.parseInt(coachid, 0));
		return tempDefaultSchedule;
	}

	@Override
	public String getBookederName(String coachid, String date, String hour) {
		 String querystring ="from OrderInfo where coachid=:coachid and HOUR(start_time)=:hour and DATE(start_time)=:date";
		 String[] params={"coachid","hour","date"};
		 OrderInfo tempOrderInfo=(OrderInfo) dataDao.getFirstObjectViaParam(querystring, params,CommonUtils.parseInt(coachid, 0),Integer.parseInt(hour),CommonUtils.getDateFormat(date, "yyyy-MM-dd"));
		 if(tempOrderInfo!=null)
		 {
			 int id=tempOrderInfo.getStudentid();
			 SuserInfo tempSuserInfo=dataDao.getObjectById(SuserInfo.class, id);
			 return tempSuserInfo.getRealname();
		 }
		 
		 return "";
		
	}

	@Override
	public void setCscheduleByday(String coachid, String day, String hour,int bookstate) {
		CscheduleInfo tempCscheduleInfo= getCscheduleByday(coachid, day, hour);
		tempCscheduleInfo.setBookstate(bookstate);
		updateScheduleInfo(tempCscheduleInfo);
	}

	@Override
	public DefaultSchedule getCoachDefaultScheduleByDay(String coachid, String hour) {
		String hql = "from DefaultSchedule where coachid = :coachid and hour=:hour";
		String[] param = { "coachid","hour" };
		return (DefaultSchedule) dataDao.getFirstObjectViaParam(hql, param, CommonUtils.parseInt(coachid, 0),hour);
	}

	@Override
	public int checkBooked(String coachid, String booktime, String date) {
		String hql="from CBookTimeInfo where coachid =:coachid and bookedtime =:bookedtime and date =:date";
		String params[] = { "coachid", "bookedtime", "date" };
		List<CBookTimeInfo> booktimeList = (List<CBookTimeInfo>) dataDao.getObjectsViaParam(hql, params,CommonUtils.parseInt(coachid, 0),booktime,date);
		if (booktimeList != null && booktimeList.size() > 0) 
			return 1;
		else
			return 0;
	}

	@Override
	public HashMap getPriceRange(String cityid) {
		String querystring="from AutoPositionInfo where cityid=:cityid";
		String[] params={"cityid"};
		AutoPositionInfo ap=(AutoPositionInfo) dataDao.getFirstObjectViaParam(querystring, params, CommonUtils.parseInt(cityid, 0));
		HashMap result=new HashMap();
		result.put("maxprice", ap.getMaxprice().doubleValue());
		result.put("minprice", ap.getMinprice().doubleValue());
		result.put("defaultprice", ap.getDefaultprice().doubleValue());
		return result;
	}
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void updateDefaultSchedule(DefaultSchedule ds) {
		  dataDao.updateObject(ds);
		
	}
}
