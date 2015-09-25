package com.daoshun.guangda.serviceImpl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.daoshun.common.ApplePushUtil;
import com.daoshun.common.CommonUtils;
import com.daoshun.common.Constant;
import com.daoshun.common.DeviceType;
import com.daoshun.common.PayType;
import com.daoshun.common.PushtoSingle;
import com.daoshun.guangda.pojo.AppCuserInfo;
import com.daoshun.guangda.pojo.AutoPositionInfo;
import com.daoshun.guangda.pojo.CBookTimeInfo;
import com.daoshun.guangda.pojo.CaddAddressInfo;
import com.daoshun.guangda.pojo.CityInfo;
import com.daoshun.guangda.pojo.CityRadius;
import com.daoshun.guangda.pojo.CoachStudentInfo;
import com.daoshun.guangda.pojo.CouponRecord;
import com.daoshun.guangda.pojo.CscheduleInfo;
import com.daoshun.guangda.pojo.CsubjectInfo;
import com.daoshun.guangda.pojo.CuserInfo;
import com.daoshun.guangda.pojo.DefaultSchedule;
import com.daoshun.guangda.pojo.DriveSchoolInfo;
import com.daoshun.guangda.pojo.EvaluationInfo;
import com.daoshun.guangda.pojo.ModelPrice;
import com.daoshun.guangda.pojo.ModelsInfo;
import com.daoshun.guangda.pojo.OrderInfo;
import com.daoshun.guangda.pojo.OrderNotiRecord;
import com.daoshun.guangda.pojo.OrderNotiSetInfo;
import com.daoshun.guangda.pojo.OrderPrice;
import com.daoshun.guangda.pojo.RemindCoach;
import com.daoshun.guangda.pojo.SuserInfo;
import com.daoshun.guangda.pojo.SystemSetInfo;
import com.daoshun.guangda.pojo.UserPushInfo;
import com.daoshun.guangda.service.ICscheduleService;
import com.daoshun.guangda.service.ILocationService;
import com.daoshun.guangda.service.ISBookService;
import com.daoshun.guangda.service.ISUserService;

@Service("sbookService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class SBookServiceImpl extends BaseServiceImpl implements ISBookService {
//	@Resource
//	public RedisCoachDao redisCoachDao;
	@Resource
	private ILocationService locationService;
	@Resource
	public ICscheduleService cscheduleService;
	@Resource
	private ISUserService suserService;
	@Override
	public CuserInfo getCoachDetail(String coachid) {
		CuserInfo cuser = dataDao.getObjectById(CuserInfo.class, CommonUtils.parseInt(coachid, 0));
		if (cuser != null) {
			cuser.setAvatarurl(getFilePathById(cuser.getAvatar()));// 头像
			cuser.setTelphone(cuser.getPhone());
			// 年龄
			if (!CommonUtils.isEmptyString(cuser.getBirthday())) {
				String birthday = cuser.getBirthday();
				Calendar c = Calendar.getInstance();
				c.setTime(CommonUtils.getDateFormat(birthday, "yyyy-MM-dd"));
				Calendar now = Calendar.getInstance();
				cuser.setAge(now.get(Calendar.YEAR) - c.get(Calendar.YEAR) + 1);
			}
			// 准教车型
			String hql = "from ModelsInfo where modelid in (:mids)";
			List<Integer> mids = new ArrayList<Integer>();
			if (cuser.getModelid() != null) {
				String[] midarray = cuser.getModelid().split(",");
				for (String ids : midarray) {
					mids.add(CommonUtils.parseInt(ids, 0));
				}
				String[] params = { "mids" };
				List<ModelsInfo> list = (List<ModelsInfo>) dataDao.getObjectsViaParam(hql, params, mids);
				cuser.setModellist(list);
			}


			// 教练默认的上车地址
			String hqladd = "from CaddAddressInfo where coachid = :coachid and iscurrent = 1";
			String[] paramsadd = { "coachid" };
			List<CaddAddressInfo> addressList = (List<CaddAddressInfo>) dataDao.getObjectsViaParam(hqladd, paramsadd, cuser.getCoachid());// 教练的默认上车地址
			if (addressList != null && addressList.size() > 0) {
				cuser.setDetail(addressList.get(0).getDetail());
			}
		}
		return cuser;
	}
	public int getCoachState(String coachid, int datacount, Date startdate, int starthour, int endhour,int subjectid){
		List querylist=dataDao.getCoachState(coachid, datacount,startdate, starthour, endhour, subjectid);
		if(querylist!=null && querylist.size()>0){
			String result=querylist.get(0).toString();
			return Integer.parseInt(result);
		}else{
			String result=querylist.get(0).toString();
			return Integer.parseInt(result);
		}
	}
	
	public int getRemindState(String coachid,String studentid,String date){
		String hql="from RemindCoach where coachid=:coachid and studentid=:studentid and to_days(date)=to_days(:date)";
		String params5[] = { "coachid","studentid","date" };
		RemindCoach rc=(RemindCoach) dataDao.getFirstObjectViaParam(hql, params5,CommonUtils.parseInt(coachid, 0),CommonUtils.parseInt(studentid, 0),date);
		if(rc!=null){
			return 1;
		}else{
			return 0;
		}
	}
	public void remindCoach(String coachid,String studentid,String date){
		//long starttime=System.currentTimeMillis();
		RemindCoach rc=new RemindCoach();
		rc.setCoachid(CommonUtils.parseInt(coachid, 0));
		rc.setStudentid(CommonUtils.parseInt(studentid, 0));
		rc.setDate(CommonUtils.getDateFormat(date, "yyyy-MM-dd"));
		dataDao.addObject(rc);
		String studentName="";
		SuserInfo student=dataDao.getObjectById(SuserInfo.class, CommonUtils.parseInt(studentid, 0));
		if(student!=null){
			studentName=student.getRealname();
		}
		//long e1=System.currentTimeMillis();
		//System.out.println(e1-starttime);
		String pushMsg="学员"+studentName+"提醒你开启"+date+"的课程";
		//给此订单关联的教练推送消息，提示让他同意取消订单
		String hql5 = "from UserPushInfo where userid =:userid and usertype = 1";
		String params5[] = { "userid" };
		UserPushInfo userpush = (UserPushInfo) dataDao.getFirstObjectViaParam(hql5, params5,CommonUtils.parseInt(coachid, 0));
		if (userpush != null) {
			if (userpush.getType() == DeviceType.ANDROID && !CommonUtils.isEmptyString(userpush.getJpushid())) {// 安卓
				PushtoSingle pushsingle = new PushtoSingle();
				pushsingle.pushsingle(userpush.getJpushid(), 1, "{\"message\":\"" + pushMsg + "\",\"type\":\"1\"}");
			} else if (userpush.getType() == DeviceType.IOS && !CommonUtils.isEmptyString(userpush.getDevicetoken())) {
				ApplePushUtil.sendpush(userpush.getDevicetoken(), "{\"aps\":{\"alert\":\"" + pushMsg + "\",\"sound\":\"default\"},\"userid\":" + coachid + "}", 1, 1);
			}
		}
		//long e2=System.currentTimeMillis();
		//System.out.println(e2-starttime);
	}
	@Override
	public List<CscheduleInfo> refreshCoachSchedule(String coachid, String date, String studentid) {
		// 查询教练的日期设置
		List<CscheduleInfo> datelist = new ArrayList<CscheduleInfo>();
		String hql = "from CscheduleInfo where coachid = :coachid and hour = :hour and date = :date";
		String[] params = { "coachid", "hour", "date" };

		CuserInfo user = dataDao.getObjectById(CuserInfo.class, CommonUtils.parseInt(coachid, 0));// 教练的信息

		// 获取教练的默认地址信息
		String hqladd = "from CaddAddressInfo where coachid = :coachid and iscurrent = 1";
		String[] paramsadd = { "coachid" };
		List<CaddAddressInfo> addressList = (List<CaddAddressInfo>) dataDao.getObjectsViaParam(hqladd, paramsadd, CommonUtils.parseInt(coachid, 0));// 教练的默认上车地址
		List<CscheduleInfo> listDay = (List<CscheduleInfo>) dataDao.getObjectsViaParam(hql, params, CommonUtils.parseInt(coachid, 0), "0", date);// 查询当天的全天设置

		int alldayState = 0;// 全天休息或者全天开课、默认是全天休息
		if (listDay != null && listDay.size() > 0) {
			alldayState = listDay.get(0).getState();
		}
		// 获取系统当前
		Calendar now = Calendar.getInstance();
		Calendar request = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			request.setTime(sdf.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// 系统当天的当前时间,如果此时间为-1
		// 的话表示请求的时间不是当天,如果不是-1的话,表示请求的时间是当天的时间,那么需要判断时间是否已经是过去时间
		int currentHour = -1;
		if (now.get(Calendar.YEAR) == request.get(Calendar.YEAR) && now.get(Calendar.MONTH) == request.get(Calendar.MONTH) && now.get(Calendar.DAY_OF_MONTH) == request.get(Calendar.DAY_OF_MONTH)) {
			currentHour = now.get(Calendar.HOUR_OF_DAY);
		}

		int defaultPrice = 100;
		int defaultSubjectID = 1;
		String setHql = "from SystemSetInfo where 1=1";
		SystemSetInfo set = (SystemSetInfo) dataDao.getFirstObjectViaParam(setHql, null);
		if (set != null) {
			if (set.getCoach_default_price() != null && set.getCoach_default_price() != 0) {
				defaultPrice = set.getCoach_default_price();
			}

			if (set.getCoach_default_subject() != null && set.getCoach_default_subject() != 0) {
				defaultSubjectID = set.getCoach_default_subject();
			}
		}
		CsubjectInfo sub = dataDao.getObjectById(CsubjectInfo.class, defaultSubjectID);
		if (sub == null) {
			String hql5 = "from CsubjectInfo where 1=1";
			sub = (CsubjectInfo) dataDao.getFirstObjectViaParam(hql5, null);
		}
		// 从 5 至 23
		for (int i = 5; i < 24; i++) {
			CscheduleInfo info = null;
			List<CscheduleInfo> listhour = (List<CscheduleInfo>) dataDao.getObjectsViaParam(hql, params, CommonUtils.parseInt(coachid, 0), String.valueOf(i), date);// 查询当前时间点的设置

			if (listhour != null && listhour.size() > 0) {// 有数据
				info = listhour.get(0);
				if (alldayState == 0) {// 全天休息
					info.setIsrest(1);// 设置时间点休息
				} else {
					info.setIsrest(info.getIsrest());
				}
				CsubjectInfo sub1 = dataDao.getObjectById(CsubjectInfo.class, info.getSubjectid());
				if (sub1 != null) {// 如果时间点设置了科目
					info.setSubject(sub1.getSubjectname());
				} else {
					sub1 = getDefaultSubject(coachid, String.valueOf(i));
					if (sub1 != null) {
						info.setSubject(sub1.getSubjectname());
					} else {
						info.setSubject(sub.getSubjectname());// 设置为教练的默认科目
					}
				}

				// 地址信息
				CaddAddressInfo address = dataDao.getObjectById(CaddAddressInfo.class, info.getAddressid());
				if (address != null)
					info.setAddressdetail(address.getDetail());
			} else {// 无数据默认
				info = new CscheduleInfo();// 新创建一个
				if (alldayState == 1) {// 如果是当天停课的情况下

					int rest = getDefaultRest(coachid, String.valueOf(i));
					if (rest != -1) {
						info.setIsrest(rest);
					} else {
						if (i == 12 || i == 18 || i == 5 || i == 6) {
							info.setIsrest(1);
						} else {
							info.setIsrest(0);
						}
					}

				} else {
					info.setIsrest(1);// 设置时间点为休息
				}

				// 首先查询是否有默认设置,然后设置为系统的默认设置
				BigDecimal price = getDefaultPrice(coachid, String.valueOf(i));
				if (price != null) {
					info.setPrice(price);
				} else {
					info.setPrice(new BigDecimal(defaultPrice));// 价格是教练默认价格
				}
				CsubjectInfo sub1 = getDefaultSubject(coachid, String.valueOf(i));
				if (sub1 != null)
					info.setSubject(sub1.getSubjectname());// 科目是教练默认科目
				else
					info.setSubject(sub.getSubjectname());// 科目是教练默认科目

				if (addressList != null && addressList.size() > 0) {
					info.setAddressdetail(addressList.get(0).getDetail());
				}
			}

			// 学员ID不为空的时候，需要判断学员这个时间点是否已经预约了别的教练
			// 是否已经被预约 0 没有 1教练被别人预约 2教练被自己预约 3自己预约了别的教练

			if (!CommonUtils.isEmptyString(studentid)) {
				String timeString = date + " " + (i < 10 ? "0" + i : i) + ":00:00";
				Date checkDate = CommonUtils.getDateFormat(timeString, "yyyy-MM-dd HH:mm:ss");
				String hql3 = "from OrderInfo where studentid = :studentid and start_time <= :timeString and end_time > :timeString and coachstate <>4";//教练的状态是4表示学员取消的订单已经被教练同意
				String[] params3 = { "studentid", "timeString", "timeString" };
				OrderInfo orderlist = (OrderInfo) dataDao.getFirstObjectViaParam(hql3, params3, CommonUtils.parseInt(studentid, 0), checkDate, checkDate);
				if (orderlist != null) {
					if (orderlist.getCoachid() == CommonUtils.parseInt(coachid, 0)) {
						info.setIsbooked(2);
					} else {
						info.setIsbooked(3);
					}
				} else {
					String hql2 = "from CBookTimeInfo where coachid = :cid and bookedtime = :hour and date = :date";
					String[] params2 = { "cid", "hour", "date" };
					List<CBookTimeInfo> bookedtime = (List<CBookTimeInfo>) dataDao.getObjectsViaParam(hql2, params2, user.getCoachid(), String.valueOf(i), date);
					if (bookedtime != null && bookedtime.size() > 0) {
						info.setIsbooked(1);
					} else {
						info.setIsbooked(0);
					}
				}
			} else {

				String hql2 = "from CBookTimeInfo where coachid = :cid and bookedtime = :hour and date = :date";
				String[] params2 = { "cid", "hour", "date" };
				List<CBookTimeInfo> bookedtime = (List<CBookTimeInfo>) dataDao.getObjectsViaParam(hql2, params2, user.getCoachid(), String.valueOf(i), date);
				if (bookedtime != null && bookedtime.size() > 0) {
					info.setIsbooked(1);
				} else {
					info.setIsbooked(0);
				}
			}

			if (currentHour == -1) {
				info.setPasttime(2);
			} else {
				if (i - 1 < currentHour) {
					info.setPasttime(1);
				} else {
					info.setPasttime(2);
				}
			}

			datelist.add(info);
		}
		return datelist;
	}
	@Override
	public List<CscheduleInfo> refreshCoachScheduleNew(String coachid, String date, String studentid) {
		// 查询教练的日期设置
		List<CscheduleInfo> datelist = new ArrayList<CscheduleInfo>();
		String hql = "from CscheduleInfo where coachid = :coachid and hour = :hour and date = :date";
		String[] params = { "coachid", "hour", "date" };

		CuserInfo user = dataDao.getObjectById(CuserInfo.class, CommonUtils.parseInt(coachid, 0));// 教练的信息

		// 获取教练的默认地址信息
		String hqladd = "from CaddAddressInfo where coachid = :coachid and iscurrent = 1";
		String[] paramsadd = { "coachid" };
		List<CaddAddressInfo> addressList = (List<CaddAddressInfo>) dataDao.getObjectsViaParam(hqladd, paramsadd, CommonUtils.parseInt(coachid, 0));// 教练的默认上车地址
//		List<CscheduleInfo> listDay = (List<CscheduleInfo>) dataDao.getObjectsViaParam(hql, params, CommonUtils.parseInt(coachid, 0), "0", date);// 查询当天的全天设置
//
//		int alldayState = 0;// 全天休息或者全天开课、默认是全天休息
//		if (listDay != null && listDay.size() > 0) {
//			alldayState = listDay.get(0).getState();
//		}
		// 获取系统当前
		Calendar now = Calendar.getInstance();
		Calendar request = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			request.setTime(sdf.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// 系统当天的当前时间,如果此时间为-1
		// 的话表示请求的时间不是当天,如果不是-1的话,表示请求的时间是当天的时间,那么需要判断时间是否已经是过去时间
		int currentHour = -1;
		if (now.get(Calendar.YEAR) == request.get(Calendar.YEAR) && now.get(Calendar.MONTH) == request.get(Calendar.MONTH) && now.get(Calendar.DAY_OF_MONTH) == request.get(Calendar.DAY_OF_MONTH)) {
			currentHour = now.get(Calendar.HOUR_OF_DAY);
		}

		int defaultPrice = 100;
		int defaultSubjectID = 1;
		String setHql = "from SystemSetInfo where 1=1";
		SystemSetInfo set = (SystemSetInfo) dataDao.getFirstObjectViaParam(setHql, null);
		if (set != null) {
			if (set.getCoach_default_price() != null && set.getCoach_default_price() != 0) {
				defaultPrice = set.getCoach_default_price();
			}

			if (set.getCoach_default_subject() != null && set.getCoach_default_subject() != 0) {
				defaultSubjectID = set.getCoach_default_subject();
			}
		}
		CsubjectInfo sub = dataDao.getObjectById(CsubjectInfo.class, defaultSubjectID);
		if (sub == null) {
			String hql5 = "from CsubjectInfo where 1=1";
			sub = (CsubjectInfo) dataDao.getFirstObjectViaParam(hql5, null);
		}
		// 从 5 至 23
		for (int i = 5; i < 24; i++) {
			CscheduleInfo info = null;
			List<CscheduleInfo> listhour = (List<CscheduleInfo>) dataDao.getObjectsViaParam(hql, params, CommonUtils.parseInt(coachid, 0), String.valueOf(i), date);// 查询当前时间点的设置

			if (listhour != null && listhour.size() > 0) {// 有数据
				info = listhour.get(0);
//				if (alldayState == 0) {// 全天休息
//					info.setIsrest(1);// 设置时间点休息
//				} else {
//					info.setIsrest(info.getIsrest());
//				}
				CsubjectInfo sub1 = dataDao.getObjectById(CsubjectInfo.class, info.getSubjectid());
				if (sub1 != null) {// 如果时间点设置了科目
					info.setSubject(sub1.getSubjectname());
				} 
//				else {
//					sub1 = getDefaultSubject(coachid, String.valueOf(i));
//					if (sub1 != null) {
//						info.setSubject(sub1.getSubjectname());
//					} else {
//						info.setSubject(sub.getSubjectname());// 设置为教练的默认科目
//					}
//				}

				// 地址信息
				CaddAddressInfo address = dataDao.getObjectById(CaddAddressInfo.class, info.getAddressid());
				if (address != null)
					info.setAddressdetail(address.getDetail());
			} else {// 无数据默认
				    info = new CscheduleInfo();// 新创建一个
					info.setIsrest(1);// 设置时间点为休息
					info.setPrice(new BigDecimal(100.00));
					info.setSubject("科目二");// 科目是教练默认科目

				if (addressList != null && addressList.size() > 0) {
					info.setAddressdetail(addressList.get(0).getDetail());
				}
			}

			// 学员ID不为空的时候，需要判断学员这个时间点是否已经预约了别的教练
			// 是否已经被预约 0 没有 1教练被别人预约 2教练被自己预约 3自己预约了别的教练

			if (!CommonUtils.isEmptyString(studentid)) {
				String timeString = date + " " + (i < 10 ? "0" + i : i) + ":00:00";
				Date checkDate = CommonUtils.getDateFormat(timeString, "yyyy-MM-dd HH:mm:ss");
				String hql3 = "from OrderInfo where studentid = :studentid and start_time <= :timeString and end_time > :timeString and coachstate <>4";//教练的状态是4表示学员取消的订单已经被教练同意
				String[] params3 = { "studentid", "timeString", "timeString" };
				OrderInfo orderlist = (OrderInfo) dataDao.getFirstObjectViaParam(hql3, params3, CommonUtils.parseInt(studentid, 0), checkDate, checkDate);
				if (orderlist != null) {
					if (orderlist.getCoachid() == CommonUtils.parseInt(coachid, 0)) {
						info.setIsbooked(2);
					} else {
						info.setIsbooked(3);
					}
				} else {
					String hql2 = "from CBookTimeInfo where coachid = :cid and bookedtime = :hour and date = :date";
					String[] params2 = { "cid", "hour", "date" };
					List<CBookTimeInfo> bookedtime = (List<CBookTimeInfo>) dataDao.getObjectsViaParam(hql2, params2, user.getCoachid(), String.valueOf(i), date);
					if (bookedtime != null && bookedtime.size() > 0) {
						info.setIsbooked(1);
					} else {
						info.setIsbooked(0);
					}
				}
			} else {

				String hql2 = "from CBookTimeInfo where coachid = :cid and bookedtime = :hour and date = :date";
				String[] params2 = { "cid", "hour", "date" };
				List<CBookTimeInfo> bookedtime = (List<CBookTimeInfo>) dataDao.getObjectsViaParam(hql2, params2, user.getCoachid(), String.valueOf(i), date);
				if (bookedtime != null && bookedtime.size() > 0) {
					info.setIsbooked(1);
				} else {
					info.setIsbooked(0);
				}
			}

			if (currentHour == -1) {
				info.setPasttime(2);
			} else {
				if (i - 1 < currentHour) {
					info.setPasttime(1);
				} else {
					info.setPasttime(2);
				}
			}

			datelist.add(info);
		}
		return datelist;
	}
	@Override
	public List<CuserInfo> getNearByCoach(String pointcenter, String radius, String condition1, String condition2, String condition3, String condition4, String condition5, String condition6,
			String condition8, String condition9, String condition10, String condition11) {
		List<CuserInfo> coachlist = new ArrayList<CuserInfo>();
		// 取得中心点经纬度
		String[] centers = pointcenter.split(",");
		String longitude = centers[0].trim();
		String latitude = centers[1].trim();
		//120.048943   30.329578
		/*String longitude="120.048943";
		String latitude="30.329578";*/
		
		// 获得符合条件的地址
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from CaddAddressInfo where getdistance(:longitude,:latitude, longitude ,latitude)<=:radius and iscurrent = 1");
		String[] params = { "longitude", "latitude", "radius" };
		List<CaddAddressInfo> addresslist = (List<CaddAddressInfo>) dataDao.getObjectsViaParam(cuserhql.toString(), params, CommonUtils.parseDouble(longitude, 0),
				CommonUtils.parseDouble(latitude, 0), CommonUtils.parseDouble(radius, 0));
		// 当前时间 <2015-04-25>
		String now = CommonUtils.getTimeFormat(new Date(), "yyyy-MM-dd");
		if (addresslist != null && addresslist.size() > 0) {
			// 根据地址列表查询出教练,且教练需要设置了价格、默认科目、且教练不休息、证件时间未过期
			StringBuffer hqlCoach = new StringBuffer();
			hqlCoach.append("from CuserInfo u where coachid in (:cids) and state = 2 and id_cardexptime > :now and coach_cardexptime > :now"
					+ " and drive_cardexptime > :now and car_cardexptime > :now and money >= gmoney and isquit = 0");

			// 真实姓名和教练所属驾校
			if (!CommonUtils.isEmptyString(condition1)) {
				hqlCoach.append(" and (realname like '%" + condition1 + "%' or drive_school like '%" + condition1 + "%' or drive_schoolid in (select schoolid from DriveSchoolInfo where name like  '%"
						+ condition1 + "%')) ");
			}
			// 星级
			if (!CommonUtils.isEmptyString(condition2)) {
				hqlCoach.append(" and score >= " + condition2);
			}
			// 开始时间和结束时间
			if (!CommonUtils.isEmptyString(condition3)) {

				int subjectid = CommonUtils.parseInt(condition6, 0);

				Date start = null;
				if(condition3.length() == 10){
					start = CommonUtils.getDateFormat(condition3, "yyyy-MM-dd");
				}else if(condition3.length() == 19){
					start = CommonUtils.getDateFormat(condition3, "yyyy-MM-dd HH:mm:ss");
				}

				if (start != null) {
					Calendar startCal = Calendar.getInstance();
					startCal.setTime(start);

					int starthour = startCal.get(Calendar.HOUR_OF_DAY);
					int datecount = 1;
					hqlCoach.append(" and getcoachstate(u.coachid," + datecount + ",'" + CommonUtils.getTimeFormat(start, "yyyy-MM-dd") + "'," + starthour + "," + 23 + "," + subjectid + ") = 1");

				}
			} else {
				int subjectid = CommonUtils.parseInt(condition6, 0);
				Calendar c = Calendar.getInstance();

				hqlCoach.append(" and getcoachstate(u.coachid," + 10 + ",'" + CommonUtils.getTimeFormat(c.getTime(), "yyyy-MM-dd") + "'," + 5 + "," + 23 + "," + subjectid + ") = 1");
			}

			if (!CommonUtils.isEmptyString(condition11)) {
				hqlCoach.append(" and modelid like '%" + condition11 + "%'");
			}

			// if (!CommonUtils.isEmptyString(condition3) && !CommonUtils.isEmptyString(condition4)) {
			//
			// int subjectid = CommonUtils.parseInt(condition6, 0);
			//
			// Date start = CommonUtils.getDateFormat(condition3, "yyyy-MM-dd HH:mm:ss");
			// Date end = CommonUtils.getDateFormat(condition4, "yyyy-MM-dd HH:mm:ss");
			// if (start != null && end != null) {
			// Calendar startCal = Calendar.getInstance();
			// startCal.setTime(start);
			// Calendar endCal = Calendar.getInstance();
			// endCal.setTime(end);
			//
			// if (startCal.compareTo(endCal) <= 0) {
			// int starthour = startCal.get(Calendar.HOUR_OF_DAY);
			// int endhour = endCal.get(Calendar.HOUR_OF_DAY);
			// int datecount = 1;
			// int startmonth = startCal.get(Calendar.MONTH);
			// int endmonth = endCal.get(Calendar.MONTH);
			// int startday = startCal.get(Calendar.DAY_OF_MONTH);
			// int endday = endCal.get(Calendar.DAY_OF_MONTH);
			// if (startmonth == endmonth) {
			// datecount = endday - startday + 1;
			// } else {
			// startCal.set(Calendar.DATE, 1);
			// startCal.roll(Calendar.DATE, -1);
			// int maxday = startCal.get(Calendar.DAY_OF_MONTH);
			// datecount = maxday - startday + 1;
			//
			// if (startmonth + 1 == endmonth) {
			// datecount += endday;
			// } else {
			// Calendar c = Calendar.getInstance();
			// c.set(Calendar.YEAR, startCal.get(Calendar.YEAR));
			// c.set(Calendar.MONTH, 1);
			// c.set(Calendar.DATE, 1);
			// c.roll(Calendar.DATE, -1);
			// datecount += c.get(Calendar.DAY_OF_MONTH) + endday;
			// }
			// }
			//
			// hqlCoach.append(" and getcoachstate(u.coachid," + datecount + ",'" + CommonUtils.getTimeFormat(start, "yyyy-MM-dd") + "'," + starthour + "," + endhour + "," + subjectid
			// + ") = 1");
			// }
			// }
			// }

			// if (CommonUtils.parseInt(condition5, 0) != 0) {
			// hqlCoach.append(" and gender = " + condition5);
			// }
			//
			// if (CommonUtils.parseInt(condition8, 0) != 0 && CommonUtils.parseInt(condition9, 0) != 0 && CommonUtils.parseInt(condition8, 0) <= CommonUtils.parseInt(condition9, 0)) {
			// hqlCoach.append(" and price >= " + condition8 + " and price <= " + condition9);
			// }
			//
			// if (CommonUtils.parseInt(condition10, 0) != 0 && CommonUtils.parseInt(condition10, 0) != -1) {
			// hqlCoach.append(" and carmodelid = " + condition10);
			// } else if (CommonUtils.parseInt(condition10, 0) == -1) {
			// hqlCoach.append(" and length(carmodel) > 0");
			// }

			String[] paramsCoach = { "cids", "now", "now", "now", "now", "now" };

			List<Integer> cids = new ArrayList<Integer>();
			for (CaddAddressInfo info : addresslist) {
				cids.add(info.getCoachid());
				}
			//System.out.println(hqlCoach.toString());
			List<CuserInfo> cuserlist = (List<CuserInfo>) dataDao.getObjectsViaParam(hqlCoach.toString(), paramsCoach, cids, now, now, now, now, now);
			if (cuserlist != null && cuserlist.size() > 0) {
				for (CuserInfo cuser : cuserlist) {
					if (cuser.getMoney().doubleValue() >= cuser.getGmoney().doubleValue()) {
						coachlist.add(cuser);
					}
				}
			}
			// 查询教练其它信息
			for (int i = 0; i < coachlist.size(); i++) {
				for (CaddAddressInfo address : addresslist) {// 设置地址信息 教练的默认上车地址
					if (address.getCoachid() == coachlist.get(i).getCoachid()) {
						coachlist.get(i).setLongitude(address.getLongitude());
						coachlist.get(i).setLatitude(address.getLatitude());
						coachlist.get(i).setDetail(address.getDetail());
						coachlist.get(i).setAvatarurl(getFilePathById(coachlist.get(i).getAvatar()));
					}
				}
			}
		}
		return coachlist;
	}
	
	public List<AppCuserInfo> getNearByCoach2(String cityid,String pointcenter, String radius, String condition1, String condition2, String condition3, String condition4, String condition5, String condition6,
			String condition8, String condition9, String condition10, String condition11,String studentid,String driverschoolid,String fixedposition) {
		List<AppCuserInfo> coachlist = new ArrayList<AppCuserInfo>();
		// 取得中心点经纬度
		String[] centers = pointcenter.split(",");
		String longitude =centers[0].trim();
		String latitude = centers[1].trim();
		//120.048943   30.329578
		/*String longitude="120.048943";
		String latitude="30.329578";*/
		/*String rhql="from CityRadius where cityid=:cityid";
		String p[]={"cityid"};
		CityRadius cr=(CityRadius) dataDao.getFirstObjectViaParam(rhql, p, CommonUtils.parseInt(cityid, 0));
		if(cr!=null){
			if(cr.getRadius()!=0){
				radius=String.valueOf(cr.getRadius());
			}
		}*/
		// 获得符合条件的地址
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from CaddAddressInfo where getdistance(:longitude,:latitude, longitude ,latitude)<=:radius and iscurrent = 1");
		String[] params = { "longitude", "latitude", "radius" };
		List<CaddAddressInfo> addresslist = (List<CaddAddressInfo>) dataDao.getObjectsViaParam(cuserhql.toString(), params, CommonUtils.parseDouble(longitude, 0),
				CommonUtils.parseDouble(latitude, 0), CommonUtils.parseDouble(radius, 0));
		// 当前时间 <2015-04-25>
		String now = CommonUtils.getTimeFormat(new Date(), "yyyy-MM-dd");
		if (addresslist != null && addresslist.size() > 0) {
			StringBuffer cs=new StringBuffer("(0");
			for (CaddAddressInfo info : addresslist) {
				cs.append(",").append(info.getCoachid());
			}
			cs.append(")");
			// 根据地址列表查询出教练,且教练需要设置了价格、默认科目、且教练不休息、证件时间未过期
			StringBuffer hqlCoach = new StringBuffer();
			/*hqlCoach.append("select getTeachAddress(u.coachid) as address,getCoachOrderCount(u.coachid) as drive_schoolid, u.*  from t_user_coach u where coachid in "+
							cs.toString()+" and state = 2 and id_cardexptime > curdate() and coach_cardexptime > curdate() "
					+ " and drive_cardexptime > curdate() and car_cardexptime > curdate() and money >= gmoney and isquit = 0");*/
			hqlCoach.append("select u.*  from app_coach_list u where isnew=1  and coachid in ");
			hqlCoach.append(cs.toString());
			if(!CommonUtils.isEmptyString(driverschoolid)){
				hqlCoach.append("  and (drive_schoolid  = "+driverschoolid+" or drive_schoolid in (select schoolid");
				hqlCoach.append("  from t_drive_school_info where schoolid = "+driverschoolid+"))");
			}
			/*if (!CommonUtils.isEmptyString(fixedposition)) {
				String findCityIdHql="from CityInfo where city like '%"+fixedposition+"%'";
				List<CityInfo> citylist=(List<CityInfo>) dataDao.getObjectsViaParam(findCityIdHql.toString(),null);
				if(citylist!=null && citylist.size()>0){
					CityInfo city=citylist.get(0);
					if(city!=null){
						cuserhql.append(" and cityid = " + city.getCityid());
					}
				}
			}*/
			if (!CommonUtils.isEmptyString(cityid)) {
				hqlCoach.append(" and cityid = " + cityid);
			}
			// 真实姓名和教练所属驾校
			if (!CommonUtils.isEmptyString(condition1)) {
				//如果是手机号码
				if(CommonUtils.isNumber(condition1) && condition1.trim().length()==11){
					hqlCoach.append(" and phone = '").append(condition1).append("' ");
				}else{
					hqlCoach.append(" and (realname like '%" + condition1 + "%' or drive_school like '%" + condition1 + "%' or drive_schoolid in (select schoolid from t_drive_school_info where name like  '%"
						+ condition1 + "%')) ");
				}
			}else{
				hqlCoach.append(" and  coursestate = 1 ");
			}
			//cuserhql.append(" and  coursestate = 1 ");
			// 星级
			if (!CommonUtils.isEmptyString(condition2)) {
				hqlCoach.append(" and score >= " + condition2);
			}
			
			// 开始时间和结束时间
			/*if (!CommonUtils.isEmptyString(condition3)) {

				int subjectid = CommonUtils.parseInt(condition6, 0);

				Date start = null;
				if(condition3.length() == 10){
					start = CommonUtils.getDateFormat(condition3, "yyyy-MM-dd");
				}else if(condition3.length() >10){
					start = CommonUtils.getDateFormat(condition3, "yyyy-MM-dd HH:mm:ss");
				}

				if (start != null) {
					Calendar startCal = Calendar.getInstance();
					startCal.setTime(start);

					int starthour = startCal.get(Calendar.HOUR_OF_DAY);
					int datecount = 1;
					hqlCoach.append(" and  coursestate = 1");
					//hqlCoach.append(" and getcoachstate(u.coachid," + datecount + ",'" + CommonUtils.getTimeFormat(start, "yyyy-MM-dd") + "'," + starthour + "," + 23 + "," + subjectid + ") = 1");

				}
			} else {
				int subjectid = CommonUtils.parseInt(condition6, 0);
				Calendar c = Calendar.getInstance();
				hqlCoach.append(" and  coursestate = 1");
				//hqlCoach.append(" and getcoachstate(u.coachid," + 10 + ",'" + CommonUtils.getTimeFormat(c.getTime(), "yyyy-MM-dd") + "'," + 5 + "," + 23 + "," + subjectid + ") = 1");
			}*/
			//判断是否是测试用户
			if(!CommonUtils.isEmptyString(studentid)){
				SuserInfo user=dataDao.getObjectById(SuserInfo.class, CommonUtils.parseInt(studentid, 0));
				if(user!=null){
					if(user.getUsertype()==null){
						user.setUsertype(0);
						dataDao.updateObject(user);
					}
					if(user.getUsertype()==0){//正式学员，正式学员不能看到测试教练
						hqlCoach.append(" and usertype=0 ");
					}
				}
			}else{
				hqlCoach.append(" and usertype=0 ");
			}
			
			if (!CommonUtils.isEmptyString(condition11)) {//C1手动挡 接收到17 ，C2自动挡接收到18
				hqlCoach.append(" and modelid like '%" + condition11 + "%'");
			}

			// if (!CommonUtils.isEmptyString(condition3) && !CommonUtils.isEmptyString(condition4)) {
			//
			// int subjectid = CommonUtils.parseInt(condition6, 0);
			//
			// Date start = CommonUtils.getDateFormat(condition3, "yyyy-MM-dd HH:mm:ss");
			// Date end = CommonUtils.getDateFormat(condition4, "yyyy-MM-dd HH:mm:ss");
			// if (start != null && end != null) {
			// Calendar startCal = Calendar.getInstance();
			// startCal.setTime(start);
			// Calendar endCal = Calendar.getInstance();
			// endCal.setTime(end);
			//
			// if (startCal.compareTo(endCal) <= 0) {
			// int starthour = startCal.get(Calendar.HOUR_OF_DAY);
			// int endhour = endCal.get(Calendar.HOUR_OF_DAY);
			// int datecount = 1;
			// int startmonth = startCal.get(Calendar.MONTH);
			// int endmonth = endCal.get(Calendar.MONTH);
			// int startday = startCal.get(Calendar.DAY_OF_MONTH);
			// int endday = endCal.get(Calendar.DAY_OF_MONTH);
			// if (startmonth == endmonth) {
			// datecount = endday - startday + 1;
			// } else {
			// startCal.set(Calendar.DATE, 1);
			// startCal.roll(Calendar.DATE, -1);
			// int maxday = startCal.get(Calendar.DAY_OF_MONTH);
			// datecount = maxday - startday + 1;
			//
			// if (startmonth + 1 == endmonth) {
			// datecount += endday;
			// } else {
			// Calendar c = Calendar.getInstance();
			// c.set(Calendar.YEAR, startCal.get(Calendar.YEAR));
			// c.set(Calendar.MONTH, 1);
			// c.set(Calendar.DATE, 1);
			// c.roll(Calendar.DATE, -1);
			// datecount += c.get(Calendar.DAY_OF_MONTH) + endday;
			// }
			// }
			//
			// hqlCoach.append(" and getcoachstate(u.coachid," + datecount + ",'" + CommonUtils.getTimeFormat(start, "yyyy-MM-dd") + "'," + starthour + "," + endhour + "," + subjectid
			// + ") = 1");
			// }
			// }
			// }

			// if (CommonUtils.parseInt(condition5, 0) != 0) {
			// hqlCoach.append(" and gender = " + condition5);
			// }
			//
			// if (CommonUtils.parseInt(condition8, 0) != 0 && CommonUtils.parseInt(condition9, 0) != 0 && CommonUtils.parseInt(condition8, 0) <= CommonUtils.parseInt(condition9, 0)) {
			// hqlCoach.append(" and price >= " + condition8 + " and price <= " + condition9);
			// }
			//
			// if (CommonUtils.parseInt(condition10, 0) != 0 && CommonUtils.parseInt(condition10, 0) != -1) {
			// hqlCoach.append(" and carmodelid = " + condition10);
			// } else if (CommonUtils.parseInt(condition10, 0) == -1) {
			// hqlCoach.append(" and length(carmodel) > 0");
			// }

			//String[] paramsCoach = { "cids", "now", "now", "now", "now", "now" };

			/*List<Integer> cids = new ArrayList<Integer>();
			for (CaddAddressInfo info : addresslist) {
				cids.add(info.getCoachid());
				if(info.getCoachid()==157)
					System.out.println(info.getCoachid());
			}*/
			//System.out.println("getNNNN:"+hqlCoach.toString());
			//hqlCoach.append("  order by coursestate desc,drive_schoolid desc,score desc");
			List<AppCuserInfo> cuserlist = (List<AppCuserInfo>) dataDao.SqlPageQuery(hqlCoach.toString(), null, null,AppCuserInfo.class, null);
			//List<CuserInfo> cuserlist = (List<CuserInfo>) dataDao.getObjectsViaParam(hqlCoach.toString(), paramsCoach, cids, now, now, now, now, now);
			if (cuserlist != null && cuserlist.size() > 0) {
				for (AppCuserInfo cuser : cuserlist) {
					if (cuser.getMoney().doubleValue() >= cuser.getGmoney().doubleValue()) {
						coachlist.add(cuser);
					}
				}
			}
			// 查询教练其它信息
			for (int i = 0; i < coachlist.size(); i++) {
				AppCuserInfo ac=coachlist.get(i);
				ac.setGmoney(null);
				ac.setMoney(null);
				ac.setRealpic(null);
				for (CaddAddressInfo address : addresslist) {// 设置地址信息 教练的默认上车地址
					if (address.getCoachid() == coachlist.get(i).getCoachid()) {
						coachlist.get(i).setLongitude(address.getLongitude());
						coachlist.get(i).setLatitude(address.getLatitude());
						coachlist.get(i).setDetail(address.getDetail());
						coachlist.get(i).setAvatarurl(getFilePathById(coachlist.get(i).getAvatar()));
					}
				}
			}
		}
		
		return coachlist;
	}

	@Override
	public HashMap<String, Object> getCoachList(String condition1, String condition2, String condition3, String condition4, String condition5, String condition6, String condition8, String condition9,
			String condition10, String condition11, String pagenum) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("select getTeachAddress(u.coachid) as address,getCoachOrderCount(u.coachid) as drive_schoolid, u.*  from t_user_coach u where state = 2 and id_cardexptime > curdate() and coach_cardexptime > curdate() and drive_cardexptime > curdate() and car_cardexptime > curdate() and (select count(*) from t_teach_address a where u.coachid = a.coachid and iscurrent = 1) > 0");
		// 真实姓名和教练所属驾校
		if (!CommonUtils.isEmptyString(condition1)) {
			cuserhql.append(" and (realname like '%" + condition1 + "%' or drive_school like '%" + condition1 + "%' or drive_schoolid in (select schoolid from t_drive_school_info where name like  '%"
					+ condition1 + "%')) ");
		}
		// 星级
		if (!CommonUtils.isEmptyString(condition2)) {
			cuserhql.append(" and score >= " + condition2);
		}

		if (!CommonUtils.isEmptyString(condition3)) {
			int subjectid = CommonUtils.parseInt(condition6, 0);
			Date start = null;
			if(condition3.length() == 10){
				start = CommonUtils.getDateFormat(condition3, "yyyy-MM-dd");
			}else if(condition3.length() == 19){
				start = CommonUtils.getDateFormat(condition3, "yyyy-MM-dd HH:mm:ss");
			}

			if (start != null) {
				Calendar startCal = Calendar.getInstance();
				startCal.setTime(start);

				int starthour = startCal.get(Calendar.HOUR_OF_DAY);
				int datecount = 1;
				cuserhql.append(" and  coursestate = 1");
				//cuserhql.append(" and getcoachstate(u.coachid," + datecount + ",'" + CommonUtils.getTimeFormat(start, "yyyy-MM-dd") + "'," + starthour + "," + 23 + "," + subjectid + ") = 1");
			}
		} else {
			int subjectid = CommonUtils.parseInt(condition6, 0);
			Calendar c = Calendar.getInstance();
			cuserhql.append(" and  coursestate = 1");
			//cuserhql.append(" and getcoachstate(u.coachid," + 10 + ",'" + CommonUtils.getTimeFormat(c.getTime(), "yyyy-MM-dd") + "'," + 5 + "," + 23 + "," + subjectid + ") = 1");
		}
		if (!CommonUtils.isEmptyString(condition11)) {
			cuserhql.append(" and modelid like '%" + condition11 + "%'");
		}
		// 开始时间和结束时间
		// if (!CommonUtils.isEmptyString(condition3) && !CommonUtils.isEmptyString(condition4)) {
		//
		// int subjectid = CommonUtils.parseInt(condition6, 0);
		//
		// Date start = CommonUtils.getDateFormat(condition3, "yyyy-MM-dd HH:mm:ss");
		// Date end = CommonUtils.getDateFormat(condition4, "yyyy-MM-dd HH:mm:ss");
		// if (start != null && end != null) {
		// Calendar startCal = Calendar.getInstance();
		// startCal.setTime(start);
		// Calendar endCal = Calendar.getInstance();
		// endCal.setTime(end);
		//
		// if (startCal.compareTo(endCal) <= 0) {
		// int starthour = startCal.get(Calendar.HOUR_OF_DAY);
		// int endhour = endCal.get(Calendar.HOUR_OF_DAY);
		// int datecount = 1;
		// int startmonth = startCal.get(Calendar.MONTH);
		// int endmonth = endCal.get(Calendar.MONTH);
		// int startday = startCal.get(Calendar.DAY_OF_MONTH);
		// int endday = endCal.get(Calendar.DAY_OF_MONTH);
		// if (startmonth == endmonth) {
		// datecount = endday - startday + 1;
		// } else {
		// startCal.set(Calendar.DATE, 1);
		// startCal.roll(Calendar.DATE, -1);
		// int maxday = startCal.get(Calendar.DAY_OF_MONTH);
		// datecount = maxday - startday + 1;
		//
		// if (startmonth + 1 == endmonth) {
		// datecount += endday;
		// } else {
		// Calendar c = Calendar.getInstance();
		// c.set(Calendar.YEAR, startCal.get(Calendar.YEAR));
		// c.set(Calendar.MONTH, 1);
		// c.set(Calendar.DATE, 1);
		// c.roll(Calendar.DATE, -1);
		// datecount += c.get(Calendar.DAY_OF_MONTH) + endday;
		// }
		// }
		//
		// cuserhql.append(" and getcoachstate(u.coachid," + datecount + ",'" + CommonUtils.getTimeFormat(start, "yyyy-MM-dd") + "'," + starthour + "," + endhour + "," + subjectid + ") = 1");
		// }
		// }
		// }

		// if (CommonUtils.parseInt(condition5, 0) != 0) {
		// cuserhql.append(" and gender = " + condition5);
		// }
		//
		// if (CommonUtils.parseInt(condition8, 0) != 0 && CommonUtils.parseInt(condition9, 0) != 0 && CommonUtils.parseInt(condition8, 0) <= CommonUtils.parseInt(condition9, 0)) {
		// cuserhql.append(" and price >= " + condition8 + " and price <= " + condition9);
		// }
		//
		// if (CommonUtils.parseInt(condition10, 0) != 0 && CommonUtils.parseInt(condition10, 0) != -1) {
		// cuserhql.append(" and carmodelid = " + condition10);
		// } else if (CommonUtils.parseInt(condition10, 0) == -1) {
		// cuserhql.append(" and length(carmodel) > 0");
		// }
		//cuserhql.append(" and money >= gmoney and isquit = 0 order by score desc");
		//String[] params = { "now", "now", "now", "now" };
		//String now = CommonUtils.getTimeFormat(new Date(), "yyyy-MM-dd");
		//System.out.println(cuserhql.toString());
		cuserhql.append(" and money >= gmoney and isquit = 0 and state=2 order by score desc,drive_schoolid desc ");
		//System.out.println(cuserhql.toString());
		List<CuserInfo> coachlist = (List<CuserInfo>) dataDao.SqlPageQuery(cuserhql.toString(), Constant.USERLIST_SIZE+1, CommonUtils.parseInt(pagenum, 0) + 1,CuserInfo.class, null);
		//System.out.println(cuserhql.toString());
		//String[] params = { "now", "now", "now", "now" };
		//String now = CommonUtils.getTimeFormat(new Date(), "yyyy-MM-dd");
		//System.out.println(cuserhql.toString());
		//String t="select getTeachAddress(u.coachid) as address,getCoachOrderCount(u.coachid) as drive_schoolid,u.*  from t_user_coach u";
		if (coachlist != null && coachlist.size() > 0) {
			for (CuserInfo coach : coachlist) {
				//StringBuffer cuserhql1 = new StringBuffer();
				//cuserhql1.append("from CaddAddressInfo where coachid =:coachid and iscurrent = 1");
				//String[] params1 = { "coachid" };
				//CaddAddressInfo address = (CaddAddressInfo) dataDao.getFirstObjectViaParam(cuserhql1.toString(), params1, coach.getCoachid());
				if(coach.getDrive_schoolid()!=null){
					coach.setSumnum(new Long(coach.getDrive_schoolid()));
				}
				if(coach.getAddress()!=null){
					String str[]=coach.getAddress().split("#");
					coach.setAddress("");
					if(str!=null && str.length==3){
						coach.setLongitude(str[0]);
						coach.setLatitude(str[1]);
						coach.setDetail(str[2]);
					}
					coach.setAvatarurl(getFilePathById(coach.getAvatar()));
				}
			}
		}
		result.put("coachlist", coachlist);
		List<CuserInfo> coachlistnext = (List<CuserInfo>) dataDao.SqlPageQuery(cuserhql.toString(), Constant.USERLIST_SIZE, CommonUtils.parseInt(pagenum, 0) + 2,CuserInfo.class, null);
		if (coachlistnext != null && coachlistnext.size() > 0) {
			result.put("hasmore", 1);
		} else {
			result.put("hasmore", 0);
		}
		/*int n=cuserhql.toString().indexOf("from");
		String countSql=cuserhql.toString().substring(n, cuserhql.toString().length());
		countSql="select count(*)  "+countSql;
		System.out.println(countSql);*/
		//Long o=(Long) dataDao.getFirstObjectViaParam(countSql, p, coachid);
		//Long coachlistnext = (Long) dataDao.SqlPageQuery(countSql, Constant.USERLIST_SIZE, CommonUtils.parseInt(pagenum, 0) + 2);
		/*if(coachlistnext!=null && coachlistnext.size()>0){
		int n=cuserhql.toString().indexOf("from");
		String countSql=cuserhql.toString().substring(n, cuserhql.toString().length());
		countSql="select count(*)  "+countSql;*/
		//System.out.println(countSql);
		//System.out.println("总耗时："+(endtime-starttime));
		return result;
	}
	@Override
	public HashMap<String, Object> getCoachList2(String cityid,String condition1, String condition2, String condition3, String condition4, String condition5, String condition6, String condition8, String condition9,
			String condition10, String condition11, String pagenum) {
		//long starttime=System.currentTimeMillis();
		HashMap<String, Object> result = new HashMap<String, Object>();
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("select getTeachAddress(u.coachid) as address,getCoachOrderCount(u.coachid) as drive_schoolid, u.*  from t_user_coach u where state = 2 and id_cardexptime > curdate() and coach_cardexptime > curdate() and drive_cardexptime > curdate() and car_cardexptime > curdate() and (select count(*) from t_teach_address a where u.coachid = a.coachid and iscurrent = 1) > 0");
		//cuserhql.append("select u.*  from app_coach_list u where state = 2 and id_cardexptime > curdate() and coach_cardexptime > curdate() and drive_cardexptime > curdate() and car_cardexptime > curdate() and (select count(*) from t_teach_address a where u.coachid = a.coachid and iscurrent = 1) > 0");
		if (!CommonUtils.isEmptyString(cityid)) {
			cuserhql.append(" and cityid = " + cityid);
		}
		// 真实姓名和教练所属驾校
		if (!CommonUtils.isEmptyString(condition1)) {
			//如果是手机号码
			if(CommonUtils.isNumber(condition1) && condition1.trim().length()==11){
				cuserhql.append(" and phone = '").append(condition1).append("' ");
			}else{
				cuserhql.append(" and (realname like '%" + condition1 + "%' or drive_school like '%" + condition1 + "%' or drive_schoolid in (select schoolid from t_drive_school_info where name like  '%"
						+ condition1 + "%') or phone like '%"+condition1+"%') ");
			}
			
		}else{
			cuserhql.append(" and  coursestate = 1 ");
		}
		// 星级
		if (!CommonUtils.isEmptyString(condition2)) {
			cuserhql.append(" and score >= " + condition2);
		}
		if (!CommonUtils.isEmptyString(condition3)) {
			int subjectid = CommonUtils.parseInt(condition6, 0);
			Date start = null;
			if(condition3.length() == 10){
				start = CommonUtils.getDateFormat(condition3, "yyyy-MM-dd");
			}else if(condition3.length() == 19){
				start = CommonUtils.getDateFormat(condition3, "yyyy-MM-dd HH:mm:ss");
			}
			if (start != null) {
				Calendar startCal = Calendar.getInstance();
				startCal.setTime(start);
				int starthour = startCal.get(Calendar.HOUR_OF_DAY);
				int datecount = 1;
				//cuserhql.append(" and  coursestate = 1");
				//cuserhql.append(" and getcoachstate(u.coachid," + datecount + ",'" + CommonUtils.getTimeFormat(start, "yyyy-MM-dd") + "'," + starthour + "," + 23 + "," + subjectid + ") = 1");
			}
		} else {
			int subjectid = CommonUtils.parseInt(condition6, 0);
			Calendar c = Calendar.getInstance();
			//cuserhql.append(" and  coursestate = 1");
			//cuserhql.append(" and  drive_schoolid=1");
			//cuserhql.append(" and getcoachstate(u.coachid," + 10 + ",'" + CommonUtils.getTimeFormat(c.getTime(), "yyyy-MM-dd") + "'," + 5 + "," + 23 + "," + subjectid + ") = 1");
		}

		if (!CommonUtils.isEmptyString(condition11)) {
			cuserhql.append(" and modelid like '%" + condition11 + "%'");
		}
		String studentid="";
		if(!CommonUtils.isEmptyString(studentid)){
			SuserInfo user=dataDao.getObjectById(SuserInfo.class, CommonUtils.parseInt(studentid, 0));
			if(user!=null){
				if(user.getUsertype()==0){//正式学员，正式学员不能看到测试教练
					cuserhql.append(" and usertype=0 ");
				}
			}
		}else{
			cuserhql.append(" and usertype=0 ");
		}
		cuserhql.append(" and money >= gmoney and isquit = 0  order by coursestate desc,drive_schoolid desc,score desc");
		//System.out.println(cuserhql.toString());
		List<CuserInfo> coachlist = (List<CuserInfo>) dataDao.SqlPageQuery(cuserhql.toString(), Constant.USERLIST_SIZE+1, CommonUtils.parseInt(pagenum, 0) + 1,CuserInfo.class, null);
		if (coachlist != null && coachlist.size() > 0) {
			for (CuserInfo coach : coachlist) {
				//StringBuffer cuserhql1 = new StringBuffer();
				//cuserhql1.append("from CaddAddressInfo where coachid =:coachid and iscurrent = 1");
				//String[] params1 = { "coachid" };
				//CaddAddressInfo address = (CaddAddressInfo) dataDao.getFirstObjectViaParam(cuserhql1.toString(), params1, coach.getCoachid());
				if(coach.getDrive_schoolid()!=null){//设置教练的订单总数，其中drive_schoolid字段存放的是自定义函数中获取的订单数，因为非字段别名不能自动封装值，所以先使用drive_schoolid临时存放教练的总订单数
					coach.setSumnum(new Long(coach.getDrive_schoolid()));
				}
				if(coach.getAddress()!=null){
					String str[]=coach.getAddress().split("#");
					coach.setAddress("");
					if(str!=null && str.length==3){
						coach.setLongitude(str[0]);
						coach.setLatitude(str[1]);
						coach.setDetail(str[2]);
					}
					coach.setAvatarurl(getFilePathById(coach.getAvatar()));
				}
			}
		}
		result.put("coachlist", coachlist);
		List<CuserInfo> coachlistnext = (List<CuserInfo>) dataDao.SqlPageQuery(cuserhql.toString(), Constant.USERLIST_SIZE, CommonUtils.parseInt(pagenum, 0) + 2,CuserInfo.class, null);
		if (coachlistnext != null && coachlistnext.size() > 0) {
			result.put("hasmore", 1);
		} else {
			result.put("hasmore", 0);
		}
		/*int n=cuserhql.toString().indexOf("from");
		String countSql=cuserhql.toString().substring(n, cuserhql.toString().length());
		countSql="select count(*)  "+countSql;
		System.out.println(countSql);*/
		//Long o=(Long) dataDao.getFirstObjectViaParam(countSql, p, coachid);
		//Long coachlistnext = (Long) dataDao.SqlPageQuery(countSql, Constant.USERLIST_SIZE, CommonUtils.parseInt(pagenum, 0) + 2);
		/*if(coachlistnext!=null && coachlistnext.size()>0){
		int n=cuserhql.toString().indexOf("from");
		String countSql=cuserhql.toString().substring(n, cuserhql.toString().length());
		countSql="select count(*)  "+countSql;*/
		//System.out.println(countSql);
		/*long endtime=System.currentTimeMillis();
		System.out.println("总耗时："+(endtime-starttime));*/
		return result;
	}
	
	public HashMap<String, Object> getCoachList3(String cityid,String condition1, String condition2, String condition3, String condition4, String condition5, String condition6, String condition8, String condition9,
			String condition10, String condition11, String pagenum,String studentid,String driverschoolid,String fixedposition) {
		//long starttime=System.currentTimeMillis();
		HashMap<String, Object> result = new HashMap<String, Object>();
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("select u.*  from app_coach_list u where isnew=1 ");
		//cuserhql.append("select u.*  from app_coach_list u where state = 2 and id_cardexptime > curdate() and coach_cardexptime > curdate() and drive_cardexptime > curdate() and car_cardexptime > curdate() and (select count(*) from t_teach_address a where u.coachid = a.coachid and iscurrent = 1) > 0");
		
		if (!CommonUtils.isEmptyString(fixedposition)) {
			String findCityIdHql="from CityInfo where city like '%"+fixedposition+"%'";
			List<CityInfo> citylist=(List<CityInfo>) dataDao.getObjectsViaParam(findCityIdHql.toString(),null);
			if(citylist!=null && citylist.size()>0){
				CityInfo city=citylist.get(0);
				if(city!=null){
					cuserhql.append(" and cityid = " + city.getCityid());
				}
			}
		}
		if (!CommonUtils.isEmptyString(cityid)) {
			cuserhql.append(" and cityid = " + cityid);
		}
		boolean isfindByDriverSchool=false;//是否有按驾校查询
		if(!CommonUtils.isEmptyString(driverschoolid)){
			isfindByDriverSchool=true;
			cuserhql.append("  and (drive_schoolid  = "+driverschoolid+" or drive_schoolid in (select schoolid");
			cuserhql.append("  from t_drive_school_info where schoolid = "+driverschoolid+"))");
		}
		// 真实姓名和手机号码
		if (!CommonUtils.isEmptyString(condition1)) {
			//如果是手机号码
			if(CommonUtils.isNumber(condition1) && condition1.trim().length()==11){
				cuserhql.append(" and phone = '").append(condition1).append("' ");
			}else{
				/*cuserhql.append(" and (realname like '%" + condition1 + "%' or drive_school like '%" + condition1 + "%' or drive_schoolid in (select schoolid from t_drive_school_info where name like  '%"
						+ condition1 + "%') or phone like '%"+condition1+"%') ");*/
				cuserhql.append(" and ((realname like '%" + condition1 + "%') "
						+ " or phone like '%"+condition1+"%') ");
			}
		}else{
			if(!isfindByDriverSchool){
				cuserhql.append(" and  coursestate = 1 ");
			}
		}
		// 星级
		if (!CommonUtils.isEmptyString(condition2)) {
			cuserhql.append(" and score >= " + condition2);
		}
		/*if (!CommonUtils.isEmptyString(condition3)) {
			int subjectid = CommonUtils.parseInt(condition6, 0);
			Date start = null;
			if(condition3.length() == 10){
				start = CommonUtils.getDateFormat(condition3, "yyyy-MM-dd");
			}else if(condition3.length() == 19){
				start = CommonUtils.getDateFormat(condition3, "yyyy-MM-dd HH:mm:ss");
			}
			if (start != null) {
				Calendar startCal = Calendar.getInstance();
				startCal.setTime(start);
				int starthour = startCal.get(Calendar.HOUR_OF_DAY);
				int datecount = 1;
				//coachid, 1,fdate,5,23,0
				//cuserhql.append(" and  coursestate = 1");
				//cuserhql.append(" and getcoachstate(u.coachid," + datecount + ",'" + CommonUtils.getTimeFormat(start, "yyyy-MM-dd") + "'," + starthour + "," + 23 + "," + subjectid + ") = 1");
			}
		} else {
			int subjectid = CommonUtils.parseInt(condition6, 0);
			Calendar c = Calendar.getInstance();
			//cuserhql.append(" and  coursestate = 1");
			//cuserhql.append(" and getcoachstate(u.coachid," + 10 + ",'" + CommonUtils.getTimeFormat(c.getTime(), "yyyy-MM-dd") + "'," + 5 + "," + 23 + "," + subjectid + ") = 1");
		}*/

		if (!CommonUtils.isEmptyString(condition11)) {
			cuserhql.append(" and modelid like '%" + condition11 + "%'");
		}
		if(!CommonUtils.isEmptyString(studentid)){
			SuserInfo user=dataDao.getObjectById(SuserInfo.class, CommonUtils.parseInt(studentid, 0));
			if(user!=null){
				if(user.getUsertype()==null){
					user.setUsertype(0);
					dataDao.updateObject(user);
				}
				if(user.getUsertype()==0){//正式学员，正式学员不能看到测试教练
					cuserhql.append(" and usertype=0 ");
				}
			}
		}else{
			cuserhql.append(" and usertype=0 ");
		}
		cuserhql.append(" and money >= gmoney and isquit = 0  order by coursestate desc,sumnum desc,score desc");
		//System.out.println(cuserhql.toString());
		List<AppCuserInfo> coachlist = (List<AppCuserInfo>) dataDao.SqlPageQuery(cuserhql.toString(), Constant.USERLIST_SIZE+1, CommonUtils.parseInt(pagenum, 0) + 1,AppCuserInfo.class, null);
		if (coachlist != null && coachlist.size() > 0) {
			for (AppCuserInfo coach : coachlist) {
				if(coach.getAddress()!=null){
					String str[]=coach.getAddress().split("#");
					coach.setAddress("");
					if(str!=null && str.length==3){
						coach.setLongitude(str[0]);
						coach.setLatitude(str[1]);
						coach.setDetail(str[2]);
					}
					coach.setAvatarurl(getFilePathById(coach.getAvatar()));
				}
			}
		}
		result.put("coachlist", coachlist);
		List<AppCuserInfo> coachlistnext = (List<AppCuserInfo>) dataDao.SqlPageQuery(cuserhql.toString(), Constant.USERLIST_SIZE, CommonUtils.parseInt(pagenum, 0) + 2,AppCuserInfo.class, null);
		if (coachlistnext != null && coachlistnext.size() > 0) {
			result.put("hasmore", 1);
		} else {
			result.put("hasmore", 0);
		}
		return result;
	}
	//从redis中查询开课教练信息
	public HashMap<String, Object> getCoachListByRedis(String cityid,String condition1, String condition2, String condition3, String condition4, String condition5, String condition6, String condition8, String condition9,
			String condition10, String condition11, String pagenum) {
		long starttime=System.currentTimeMillis();
		HashMap<String, Object> result = new HashMap<String, Object>();
		/*StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("select getTeachAddress(u.coachid) as address,getCoachOrderCount(u.coachid) as drive_schoolid, u.*  from t_user_coach u where state = 2 and id_cardexptime > curdate() and coach_cardexptime > curdate() and drive_cardexptime > curdate() and car_cardexptime > curdate() and (select count(*) from t_teach_address a where u.coachid = a.coachid and iscurrent = 1) > 0");
		if (!CommonUtils.isEmptyString(cityid)) {
			cuserhql.append(" and cityid = " + cityid);
		}
		// 真实姓名和教练所属驾校
		if (!CommonUtils.isEmptyString(condition1)) {
			//如果是手机号码
			if(CommonUtils.isNumber(condition1) && condition1.trim().length()==11){
				cuserhql.append(" and phone = '").append(condition1).append("' ");
			}else{
				cuserhql.append(" and (realname like '%" + condition1 + "%' or drive_school like '%" + condition1 + "%' or drive_schoolid in (select schoolid from t_drive_school_info where name like  '%"
						+ condition1 + "%') or phone like '%"+condition1+"%') ");
			}
			
		}else{
			cuserhql.append(" and  coursestate = 1 ");
		}
		// 星级
		if (!CommonUtils.isEmptyString(condition2)) {
			cuserhql.append(" and score >= " + condition2);
		}

		if (!CommonUtils.isEmptyString(condition3)) {

			int subjectid = CommonUtils.parseInt(condition6, 0);

			Date start = null;
			if(condition3.length() == 10){
				start = CommonUtils.getDateFormat(condition3, "yyyy-MM-dd");
			}else if(condition3.length() == 19){
				start = CommonUtils.getDateFormat(condition3, "yyyy-MM-dd HH:mm:ss");
			}

			if (start != null) {
				Calendar startCal = Calendar.getInstance();
				startCal.setTime(start);

				int starthour = startCal.get(Calendar.HOUR_OF_DAY);
				int datecount = 1;
				//cuserhql.append(" and  coursestate = 1");
				//cuserhql.append(" and getcoachstate(u.coachid," + datecount + ",'" + CommonUtils.getTimeFormat(start, "yyyy-MM-dd") + "'," + starthour + "," + 23 + "," + subjectid + ") = 1");

			}
		} else {
			int subjectid = CommonUtils.parseInt(condition6, 0);
			Calendar c = Calendar.getInstance();
			//cuserhql.append(" and  coursestate = 1");
			//cuserhql.append(" and  drive_schoolid=1");
			//cuserhql.append(" and getcoachstate(u.coachid," + 10 + ",'" + CommonUtils.getTimeFormat(c.getTime(), "yyyy-MM-dd") + "'," + 5 + "," + 23 + "," + subjectid + ") = 1");
		}

		if (!CommonUtils.isEmptyString(condition11)) {
			cuserhql.append(" and modelid like '%" + condition11 + "%'");
		}
		cuserhql.append(" and money >= gmoney and isquit = 0  order by coursestate desc,drive_schoolid desc,score desc");
		//System.out.println(cuserhql.toString());
		List<CuserInfo> coachlist = (List<CuserInfo>) dataDao.SqlPageQuery(cuserhql.toString(), Constant.USERLIST_SIZE+1, CommonUtils.parseInt(pagenum, 0) + 1,CuserInfo.class, null);
		
		if (coachlist != null && coachlist.size() > 0) {
			for (CuserInfo coach : coachlist) {
				//StringBuffer cuserhql1 = new StringBuffer();
				//cuserhql1.append("from CaddAddressInfo where coachid =:coachid and iscurrent = 1");
				//String[] params1 = { "coachid" };
				//CaddAddressInfo address = (CaddAddressInfo) dataDao.getFirstObjectViaParam(cuserhql1.toString(), params1, coach.getCoachid());
				if(coach.getDrive_schoolid()!=null){//设置教练的订单总数，其中drive_schoolid字段存放的是自定义函数中获取的订单数，因为非字段别名不能自动封装值，所以先使用drive_schoolid临时存放教练的总订单数
					coach.setSumnum(new Long(coach.getDrive_schoolid()));
				}
				if(coach.getAddress()!=null){
					String str[]=coach.getAddress().split("#");
					coach.setAddress("");
					if(str!=null && str.length==3){
						coach.setLongitude(str[0]);
						coach.setLatitude(str[1]);
						coach.setDetail(str[2]);
					}
					coach.setAvatarurl(getFilePathById(coach.getAvatar()));
				}
			}
		}
		result.put("coachlist", coachlist);
		List<CuserInfo> coachlistnext = (List<CuserInfo>) dataDao.SqlPageQuery(cuserhql.toString(), Constant.USERLIST_SIZE, CommonUtils.parseInt(pagenum, 0) + 2,CuserInfo.class, null);
		if (coachlistnext != null && coachlistnext.size() > 0) {
			result.put("hasmore", 1);
		} else {
			result.put("hasmore", 0);
		}*/
		/*int n=cuserhql.toString().indexOf("from");
		String countSql=cuserhql.toString().substring(n, cuserhql.toString().length());
		countSql="select count(*)  "+countSql;
		System.out.println(countSql);*/
		//Long o=(Long) dataDao.getFirstObjectViaParam(countSql, p, coachid);
		//Long coachlistnext = (Long) dataDao.SqlPageQuery(countSql, Constant.USERLIST_SIZE, CommonUtils.parseInt(pagenum, 0) + 2);
		/*if(coachlistnext!=null && coachlistnext.size()>0){
		int n=cuserhql.toString().indexOf("from");
		String countSql=cuserhql.toString().substring(n, cuserhql.toString().length());
		countSql="select count(*)  "+countSql;*/
		//System.out.println(countSql);
		/*List<CuserInfo> coachlist=redisCoachDao.getCoachList();
		result.put("coachlist", coachlist);
		long endtime=System.currentTimeMillis();
		System.out.println("总耗时："+(endtime-starttime));
		return result;*/
		return null;
	}

	class OrderModel {
		OrderInfo mOrderInfo;
		List<OrderPrice> OrderPriceList;
		List<CBookTimeInfo> CBookTimeInfoList;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.SUPPORTS)
	public HashMap<String, Object> bookCoach(String coachid, String studentid, String date) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String failtimes = "";// 不能预订的时间点集合
		int successorderid = 0;// 预订成功的第一个订单的ID
		String hql1 = "from CscheduleInfo where coachid =:coachid and date = :date and hour =:hour";
		String params1[] = { "coachid", "date", "hour" };

		String hql2 = "from CBookTimeInfo where coachid =:coachid and bookedtime =:bookedtime and date =:date";
		String params2[] = { "coachid", "bookedtime", "date" };

		String hql3 = "from CaddAddressInfo where coachid =:coachid and iscurrent = 1";
		String params3[] = { "coachid" };

		SuserInfo student = dataDao.getObjectById(SuserInfo.class, CommonUtils.parseInt(studentid, 0));
		if(student.getMoney().doubleValue()<0.0||student.getFmoney().doubleValue()<0.0||student.getCoinnum()<0)// 余额不够
		{
			//版本需要更新
			result.put("failtimes",11);
			result.put("successorderid", 11);
			result.put("coachauth", 11);
			result.put("message", "您当前处于欠费状态,无法生成订单,如信息有误,请于客服联系!");
			result.put("code", 11);//app应当提示"您当前处于欠费状态,无法生成订单
			return result;
		}

		// 首先查询出订单相关的几个时间配置
		String hqlset = "from SystemSetInfo where 1 = 1";
		SystemSetInfo setInfo = (SystemSetInfo) dataDao.getFirstObjectViaParam(hqlset, null);
		String holidays = "";// 距离订单结束之后可以确认下车的时间默认60分钟
		int systemOrderPull = 0;
		int schoolOrderPull = 0;
		int defaultPrice = 100;
		int defaultSubjectID = 1;
		if (setInfo != null) {
			if (CommonUtils.isEmptyString(setInfo.getHolidays()))
				holidays = setInfo.getHolidays();
			if (setInfo.getOrder_pull() != null && setInfo.getOrder_pull() != 0)
				systemOrderPull = setInfo.getOrder_pull();
			if (setInfo.getCoach_default_price() != null && setInfo.getCoach_default_price() != 0) {
				defaultPrice = setInfo.getCoach_default_price();
			}

			if (setInfo.getCoach_default_subject() != null && setInfo.getCoach_default_subject() != 0) {
				defaultSubjectID = setInfo.getCoach_default_subject();
			}
		}

		CuserInfo cuser = dataDao.getObjectById(CuserInfo.class, CommonUtils.parseInt(coachid, 0));
		if (cuser != null && cuser.getDrive_schoolid() != null && cuser.getDrive_schoolid() != 0) {
			DriveSchoolInfo school = dataDao.getObjectById(DriveSchoolInfo.class, cuser.getDrive_schoolid());
			if (school != null && school.getOrder_pull() != null && school.getOrder_pull() != 0) {
				schoolOrderPull = school.getOrder_pull();
			}
		}


		// 订单的提醒设置
		String hqlnoti = "from OrderNotiSetInfo where 1 = 1";
		List<OrderNotiSetInfo> orderNotiList = (List<OrderNotiSetInfo>) dataDao.getObjectsViaParam(hqlnoti, null);

		CsubjectInfo sub = dataDao.getObjectById(CsubjectInfo.class, defaultSubjectID);
		if (sub == null) {
			String hql5 = "from CsubjectInfo where 1=1";
			sub = (CsubjectInfo) dataDao.getFirstObjectViaParam(hql5, null);
		}

		try {
			List<OrderModel> orderList = new ArrayList<OrderModel>();
			JSONArray json = new JSONArray(date);
			// 教练信息
			CuserInfo cUser = dataDao.getObjectById(CuserInfo.class, CommonUtils.parseInt(coachid, 0));
			boolean hasError = false;
			for (int i = 0; i < json.length(); i++) {// 每个循环是一个订单
				boolean canOrder = true;
				JSONObject array = json.getJSONObject(i);
				JSONArray times = array.getJSONArray("time");// 订单的时间点数组
				
				String date1 = array.getString("date");// 订单的日期
				String start = "", end = "";// 订单的开始时间和结束时间
				String recordid = array.getString("recordid");
				if((recordid.lastIndexOf(',')==recordid.length()-1)|| recordid.split(",").length>times.length())
				{
					//版本需要更新
					result.put("failtimes", -1);
					result.put("successorderid", -1);
					result.put("coachauth", -1);
					result.put("code", -1);
					return result;
				}
//				array.has("delmoney");
//				array.get("delmoney")
				int delmoney = array.getInt("delmoney");
				BigDecimal total = new BigDecimal(0);// 订单的总价
				String longitude = null;
				// 纬度
				String latitude = null;
				// 详细地址
				String detail = null;
				int cancel = -1;// 订单是否可以取消 默认 为0 可以取消
				// 判断时间是否还可以预订
				// 首先查询当天的全天休息情况
				List<CscheduleInfo> scheduleinfoList = (List<CscheduleInfo>) dataDao.getObjectsViaParam(hql1, params1, CommonUtils.parseInt(coachid, 0), date1, "0");
				int state = 0;
				if (scheduleinfoList != null && scheduleinfoList.size() > 0) {
					state = scheduleinfoList.get(0).getState();
					cancel = scheduleinfoList.get(0).getCancelstate();
				}

				// 如果是用了小巴券,但是又没有传delmoney的话,订单预订失败
				if (!CommonUtils.isEmptyString(recordid) && delmoney == 0 && recordid.length()>2 ) {//
					for (int j = 0; j < times.length(); j++) {
						if (failtimes.length() == 0) {
							failtimes = date1 + times.get(j).toString() + "点";
						} else {
							failtimes += "," + date1 + times.get(j).toString() + "点";
						}
					}
					canOrder = false;
				}

				if (state == 0) {// 当天休息的情况
					for (int j = 0; j < times.length(); j++) {
						if (failtimes.length() == 0) {
							failtimes = date1 + times.get(j).toString() + "点";
						} else {
							failtimes += "," + date1 + times.get(j).toString() + "点";
						}
					}
					canOrder = false;
				} else {
					// 查看时间是否被预订或者是休息的
					for (int j = 0; j < times.length(); j++) {
						String hour = times.get(j).toString();
						if (j == 0) {
							start = hour;
						}

						if (j == times.length() - 1) {
							end = hour;
						}
						List<CBookTimeInfo> booktimeList = (List<CBookTimeInfo>) dataDao.getObjectsViaParam(hql2, params2, CommonUtils.parseInt(coachid, 0), hour, date1);
						if (booktimeList != null && booktimeList.size() > 0) {// 被预订
							if (failtimes.length() == 0) {
								failtimes = date1 + hour + "点";
							} else {
								failtimes += "," + date1 + hour + "点";
							}
							canOrder = false;
						} else {
							List<CscheduleInfo> scheduleinfoList2 = (List<CscheduleInfo>) dataDao.getObjectsViaParam(hql1, params1, CommonUtils.parseInt(coachid, 0), date1, hour);
							if (scheduleinfoList2 != null && scheduleinfoList2.size() > 0) {
								if (scheduleinfoList2.get(0).getIsrest() == 1) {
									if (failtimes.length() == 0) {// 休息
										failtimes = date1 + hour + "点";
									} else {
										failtimes += "," + date1 + hour + "点";
									}
									canOrder = false;
								} else {// 时间点不休息的话,总价增加这个时间点的价格
									total = total.add(scheduleinfoList2.get(0).getPrice());
									if (CommonUtils.isEmptyString(latitude) || CommonUtils.isEmptyString(longitude) || CommonUtils.isEmptyString(detail)) {
										CaddAddressInfo address = dataDao.getObjectById(CaddAddressInfo.class, scheduleinfoList2.get(0).getAddressid());
										if (address != null) {
											latitude = address.getLatitude();
											longitude = address.getLongitude();
											detail = address.getDetail();
										}
									}
								}
							} else {
								// 获取教练是否有默认设置
								int isrest = getDefaultRest(coachid, hour);
								if (isrest == -1) {// 默认没有设置
									if (CommonUtils.parseInt(hour, 0) == 5 || CommonUtils.parseInt(hour, 0) == 6 || CommonUtils.parseInt(hour, 0) == 12 || CommonUtils.parseInt(hour, 0) == 18) {
										if (failtimes.length() == 0) {// 休息
											failtimes = date1 + hour + "点";
										} else {
											failtimes += "," + date1 + hour + "点";
										}
										canOrder = false;
									} else {
										// 采用默认的价格设置
										BigDecimal price = getDefaultPrice(coachid, hour);
										if (price != null) {
											total = total.add(price);
										} else {
											total = total.add(new BigDecimal(defaultPrice));
										}

										if (CommonUtils.isEmptyString(latitude) || CommonUtils.isEmptyString(longitude) || CommonUtils.isEmptyString(detail)) {
											List<CaddAddressInfo> address = (List<CaddAddressInfo>) dataDao.getObjectsViaParam(hql3, params3, CommonUtils.parseInt(coachid, 0));
											if (address != null && address.size() > 0) {
												latitude = address.get(0).getLatitude();
												longitude = address.get(0).getLongitude();
												detail = address.get(0).getDetail();
											}
										}
									}
								} else if (isrest == 1) {
									if (failtimes.length() == 0) {// 休息
										failtimes = date1 + hour + "点";
									} else {
										failtimes += "," + date1 + hour + "点";
									}
									canOrder = false;
								} else {
									// 采用默认的价格设置
									BigDecimal price = getDefaultPrice(coachid, hour);
									if (price != null) {
										total = total.add(price);
									} else {
										total = total.add(new BigDecimal(defaultPrice));
									}

									if (CommonUtils.isEmptyString(latitude) || CommonUtils.isEmptyString(longitude) || CommonUtils.isEmptyString(detail)) {
										List<CaddAddressInfo> address = (List<CaddAddressInfo>) dataDao.getObjectsViaParam(hql3, params3, CommonUtils.parseInt(coachid, 0));
										if (address != null && address.size() > 0) {
											latitude = address.get(0).getLatitude();
											longitude = address.get(0).getLongitude();
											detail = address.get(0).getDetail();
										}
									}
								}

							}
						}
					}

					if (CommonUtils.isEmptyString(latitude) || CommonUtils.isEmptyString(longitude) || CommonUtils.isEmptyString(detail)) {
						List<CaddAddressInfo> address = (List<CaddAddressInfo>) dataDao.getObjectsViaParam(hql3, params3, CommonUtils.parseInt(coachid, 0));
						if (address != null && address.size() > 0) {
							latitude = address.get(0).getLatitude();
							longitude = address.get(0).getLongitude();
							detail = address.get(0).getDetail();
						}
					}

					if (canOrder) {// 这个订单是OK的,可以生成订单的
						OrderModel orderModel = new OrderModel();
						OrderInfo order = new OrderInfo();
						order.setCoachid(CommonUtils.parseInt(coachid, 0));// 教练ID
						order.setStudentid(CommonUtils.parseInt(studentid, 0));// 学员ID
						order.setCreat_time(new Date());// 添加时间
						order.setDate(date1);// 预订日期
						order.setCouponrecordid(recordid);
						order.setDelmoney(delmoney);

						Date startTime = CommonUtils.getDateFormat(date1, "yyyy-MM-dd");
						Calendar startC = Calendar.getInstance();
						startC.setTime(startTime);
						startC.set(Calendar.HOUR_OF_DAY, CommonUtils.parseInt(start, 0));
						startC.set(Calendar.MINUTE, 0);
						startC.set(Calendar.SECOND, 0);
						order.setStart_time(startC.getTime());// 开始时间

						Date endTime = CommonUtils.getDateFormat(date1, "yyyy-MM-dd");
						Calendar endC = Calendar.getInstance();
						endC.setTime(endTime);
						endC.set(Calendar.HOUR_OF_DAY, CommonUtils.parseInt(end, 0) + 1);
						endC.set(Calendar.MINUTE, 0);
						endC.set(Calendar.SECOND, 0);
						order.setEnd_time(endC.getTime());// 结束时间

						order.setTime(CommonUtils.parseInt(end, 0) - CommonUtils.parseInt(start, 0) + 1);// 订单的时长

						// 订单总价格
						order.setTotal(total);
						order.setStudentstate(0);// 学生端订单状态
						order.setCoachstate(0);// 教练段任务状态
						// 地址相关
						order.setLatitude(latitude);
						order.setLongitude(longitude);
						order.setDetail(detail);

						// 订单是否可以被取消
						// if (cancel != -1) {
						// order.setCancancel(cancel);
						// } else {
						// // 首先判断是否是节假日或者是星期天
						// Calendar calBookDate = Calendar.getInstance();
						// calBookDate.setTime(startTime);
						// int dayOfWeek = calBookDate.get(Calendar.DAY_OF_WEEK);
						// if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
						// order.setCancancel(1);
						// } else {
						// String dateCheck = CommonUtils.getTimeFormat(startTime, "yyyy年MM月dd日");
						// if (holidays.contains(dateCheck)) {
						// order.setCancancel(1);
						// } else {
						// order.setCancancel(0);
						// }
						// }
						// }
						order.setCancancel(1);// 目前所有订单不可以取消

						order.setPrice_out1(new BigDecimal(0d));
						order.setPrice_out2(new BigDecimal(0d));
						// 订单的抽成相关
						order.setOrder_pull1(systemOrderPull);
						order.setOrder_pull2(schoolOrderPull);

						orderModel.mOrderInfo = order;

						for (int j = 0; j < times.length(); j++) {// 记录时间被预订
																	// 和时间点的价格等信息
							String hour = times.get(j).toString();
							OrderPrice orderprice = new OrderPrice();
							orderprice.setOrderid(order.getOrderid());// 所属订单
							orderprice.setHour(hour);// 时间

							List<CscheduleInfo> scheduleinfoList2 = (List<CscheduleInfo>) dataDao.getObjectsViaParam(hql1, params1, CommonUtils.parseInt(coachid, 0), date1, hour);
							if (scheduleinfoList2 != null && scheduleinfoList2.size() > 0) {
								CaddAddressInfo address = dataDao.getObjectById(CaddAddressInfo.class, scheduleinfoList2.get(0).getAddressid());
								if (address != null) {
									orderprice.setDetail(address.getDetail());
									orderprice.setLatitude(address.getLatitude());
									orderprice.setLongitude(address.getLongitude());
								} else {
									orderprice.setDetail(detail);
									orderprice.setLatitude(latitude);
									orderprice.setLongitude(longitude);
								}
								orderprice.setPrice(scheduleinfoList2.get(0).getPrice());

								CsubjectInfo subject = dataDao.getObjectById(CsubjectInfo.class, scheduleinfoList2.get(0).getSubjectid());
								if (subject != null) {
									orderprice.setSubject(subject.getSubjectname());
								} else {
									orderprice.setSubject(sub.getSubjectname());
								}
							} else {
								List<CaddAddressInfo> address = (List<CaddAddressInfo>) dataDao.getObjectsViaParam(hql3, params3, CommonUtils.parseInt(coachid, 0));
								if (address != null && address.size() > 0) {
									orderprice.setDetail(address.get(0).getDetail());
									orderprice.setLatitude(address.get(0).getLatitude());
									orderprice.setLongitude(address.get(0).getLongitude());
								}

								BigDecimal price = getDefaultPrice(coachid, hour);
								if (price != null) {
									orderprice.setPrice(price);
								} else {
									orderprice.setPrice(new BigDecimal(defaultPrice));
								}

								orderprice.setSubject(sub.getSubjectname());
							}

							if (orderModel.OrderPriceList == null) {
								orderModel.OrderPriceList = new ArrayList<OrderPrice>();
							}
							orderModel.OrderPriceList.add(orderprice);

							CBookTimeInfo booktime = new CBookTimeInfo();
							booktime.setCoachid(CommonUtils.parseInt(coachid, 0));
							booktime.setDate(date1);
							booktime.setBookedtime(hour);

							List<CBookTimeInfo> booktimeList = (List<CBookTimeInfo>) dataDao.getFirstObjectViaParam(hql1, params1, CommonUtils.parseInt(coachid, 0), hour, date1);
							if (booktimeList == null || booktimeList.size() == 0) {
								if (orderModel.CBookTimeInfoList == null) {
									orderModel.CBookTimeInfoList = new ArrayList<CBookTimeInfo>();
								}
								orderModel.CBookTimeInfoList.add(booktime);
							}
						}
						orderList.add(orderModel);
					} else {
						hasError = true;
					}
				}
			}

			if (!hasError) {
				if (orderList.size() > 0) {
					BigDecimal total = new BigDecimal(0);
					for (int m = 0; m < orderList.size(); m++) {
						dataDao.addObject(orderList.get(m).mOrderInfo);
						// 查看订单的提醒设置
						if (orderNotiList != null && orderNotiList.size() > 0) {
							for (OrderNotiSetInfo setInfo1 : orderNotiList) {
								int minute = setInfo1.getBeforeminute();
								int type = setInfo1.getType();
								if (minute == 0)
									continue;
								Calendar start = Calendar.getInstance();
								start.setTime(orderList.get(m).mOrderInfo.getStart_time());
								start.add(Calendar.MINUTE, -minute);
								OrderNotiRecord record = new OrderNotiRecord();
								record.setAddtime(new Date());
								record.setBeforeminute(minute);
								record.setOrderid(orderList.get(m).mOrderInfo.getOrderid());
								record.setSendtime(start.getTime());
								record.setType(type);
								record.setCoachid(orderList.get(m).mOrderInfo.getCoachid());
								record.setStudentid(orderList.get(m).mOrderInfo.getStudentid());
								dataDao.addObject(record);
							}
						}

						if (successorderid == 0)
							successorderid = orderList.get(m).mOrderInfo.getOrderid();
						if (orderList.get(m).CBookTimeInfoList != null) {
							for (CBookTimeInfo booktime : orderList.get(m).CBookTimeInfoList) {
								dataDao.addObject(booktime);
							}
						}

						if (orderList.get(m).OrderPriceList != null) {
							for (OrderPrice mOrderPrice : orderList.get(m).OrderPriceList) {
								mOrderPrice.setOrderid(orderList.get(m).mOrderInfo.getOrderid());
								dataDao.addObject(mOrderPrice);
							}
						}
						total = total.add(orderList.get(m).mOrderInfo.getTotal());// 总价中增加订单的总价
						total = total.subtract(new BigDecimal(orderList.get(m).mOrderInfo.getDelmoney()));// 减去小巴券中抵掉的金额
						// 小巴券，判断，如果 2 
						
							if (orderList.get(m).mOrderInfo.getCouponrecordid() != null && orderList.get(m).mOrderInfo.getCouponrecordid().length() > 0) {
	
								String[] recordidArray = orderList.get(m).mOrderInfo.getCouponrecordid().split(",");
								for (int i = 0; i < recordidArray.length; i++) {
									int cid = CommonUtils.parseInt(recordidArray[i], 0);
									CouponRecord record = dataDao.getObjectById(CouponRecord.class, cid);
									if (record != null) {
										record.setState(1);// 学员的状态修改为已经使用
										dataDao.updateObject(record);
										if (record.getCoupontype() == 1) {// 时间
										} else {// 钱
											// 只需要修改为已经使用,
										}
									}
								}
							}
						
					}
					// 修改用户的余额，如果是paytype是1 余额  2 小巴卷 3 小巴币  如果1 ，2 

					if (student != null) {
						
						//  判断 1 或者 3  1 扣余额  2 扣小巴币
						student.setFmoney(student.getFmoney().add(total));
						student.setMoney(student.getMoney().subtract(total));
						//如果是小巴币，直接扣除  ，如果是余额，
						if (student.getMoney().doubleValue() < 0 || student.getFmoney().doubleValue() < 0) {
							result.put("failtimes", failtimes);
							result.put("successorderid", successorderid);
							result.put("coachauth", student.getCoachstate());
							if (failtimes.length() == 0) {
								result.put("code", 1);
							} else {
								result.put("code", 2);
							}
							return result;
						}

						dataDao.updateObject(student);
					}

					// 推送通知教练有新的订单
					String hql5 = "from UserPushInfo where userid =:userid and usertype = 1";
					String params5[] = { "userid" };
					UserPushInfo userpush = (UserPushInfo) dataDao.getFirstObjectViaParam(hql5, params5, CommonUtils.parseInt(coachid, 0));
					if (userpush != null) {
						if (userpush.getType() ==2 && !CommonUtils.isEmptyString(userpush.getJpushid())) {// 安卓
							PushtoSingle pushsingle = new PushtoSingle();
							pushsingle.pushsingle(userpush.getJpushid(), 1, "{\"message\":\"" + "您有新的订单哦" + "\",\"type\":\"1\"}");
						} else if (userpush.getType() == 1 && !CommonUtils.isEmptyString(userpush.getDevicetoken())) {
							ApplePushUtil.sendpush(userpush.getDevicetoken(), "{\"aps\":{\"alert\":\"" + "您有新的订单哦" + "\",\"sound\":\"default\"},\"userid\":" + coachid + "}", 1, 1);
						}
					}
					
					// 增加学员与教练的关系
					String coachStudentHql = "from CoachStudentInfo where coachid = :coachid and studentid = :studentid";
					String[] params8 = { "coachid", "studentid" };
					CoachStudentInfo info = (CoachStudentInfo) dataDao.getFirstObjectViaParam(coachStudentHql, params8, CommonUtils.parseInt(coachid, 0), CommonUtils.parseInt(studentid, 0));
					if (info == null) {
						info = new CoachStudentInfo();
						info.setCoachid(CommonUtils.parseInt(coachid, 0));
						info.setHour(0);
						info.setMoney(new BigDecimal(0));
						info.setStudentid(CommonUtils.parseInt(studentid, 0));
						dataDao.addObject(info);
					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
			//版本需要更新
			result.put("failtimes", -1);
			result.put("successorderid", -1);
			result.put("coachauth", -1);
			result.put("code", -1);
			return result;
			
		}
		result.put("failtimes", failtimes);
		result.put("successorderid", successorderid);
		result.put("coachauth", student.getCoachstate());
		if (failtimes.length() == 0) {
			result.put("code", 1);
		} else {
			result.put("code", 2);
		}
		return result;
	}
	/**
	 * 检查订单总额是否够支付
	 * @param json
	 * @return true 可以  false 不可以
	 * @author 卢磊
	 */
	public int checkCanPay(JSONArray json,String coachid,String studentid) throws JSONException{
		int moneySum=0;//所有订单需要消耗的余额
		int coinSum=0;//所有的订单需要消耗的小巴币
		int couponSum=0;//上传的小巴券Id的个数必须与订单个数相同
		int couponPayNum=0;//小巴券支付的订单数量
			for (int i = 0; i < json.length(); i++) {// 每个循环是一个订单
				JSONObject array = json.getJSONObject(i);
				String paytype = array.getString("paytype");// 订单的日期
				String recordid="";
				int delmoney=0;
				int mixMoney=0;//混合支付时余额支付额
				int mixCoin=0;//混合支付时的小巴币数量
				if(String.valueOf(PayType.MONEY).equals(paytype)){
					delmoney= array.getInt("delmoney");
					//需要的余额累加
					moneySum+=delmoney;
				}else if(String.valueOf(PayType.COUPON).equals(paytype)){
					couponPayNum++;
					recordid= array.getString("recordid");
					if(!recordid.equals("")){
						couponSum++;
					}
				}else if(String.valueOf(PayType.COIN).equals(paytype)){//小巴币支付
					delmoney= array.getInt("delmoney");
					coinSum+=delmoney;
				}else if(String.valueOf(PayType.COIN_MONEY).equals(paytype)){//小巴币支付
					delmoney= array.getInt("delmoney");//小巴币
					mixCoin=delmoney;//小巴币数量
					coinSum+=mixCoin;
					
					int total=array.getInt("total");//订单总额
					mixMoney=total-delmoney;//余额支付=订单总额-小巴币支付额
					moneySum+=mixMoney;
				}
			}
		
		int canUseNum[]=getCanUseCoinMoney(coachid,studentid);
		if(coinSum>canUseNum[0]){
			//小巴币不足
			return 1;
		}
		if(moneySum>canUseNum[1]){
			//余额不足
			return 2;
		}
		if(couponSum<couponPayNum){
			//小巴券不足
			return 3;
		}
		return 0;
	}
	/**
	 * 针对某个教练的可用小巴币及余额
	 * @param coachid
	 * @param studentid
	 * @return int[0] 可用小巴币  int[1] 可用余额
	 * @author 卢磊
	 */
	public int[] getCanUseCoinMoney(String coachid,String studentid){
				int canUseNum[]=new int[2];
				//小巴币的总数=教练可用小巴币+驾校可用小巴币+平台可用小巴币
				int num=suserService.getCanUseCoinnum(coachid,studentid);//获取教练可用小巴币
				int numForSchool=suserService.getCanUseCoinnumForDriveSchool(studentid);//获取驾校可用小巴币
				//获取平台发送的小巴币
				int numForPlatform=suserService.getCanUseCoinnumForPlatform("0",studentid);//获取平台可用小巴币
				num+=numForSchool;
				num+=numForPlatform;
				SuserInfo suser=suserService.getUserById(studentid);//获取余额
				int coinnum=0;
				if(suser.getFcoinnum()==null){
					suser.setFcoinnum(new BigDecimal(0));
				}
				if(num>=suser.getFcoinnum().intValue()){//可用小巴币减去冻结小巴币
					coinnum=num-suser.getFcoinnum().intValue();
				}else{
					coinnum=0;
				}
				canUseNum[0]=coinnum;//可用小巴币
				canUseNum[1]=suser.getMoney()==null?0:suser.getMoney().intValue();
				return canUseNum;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.SUPPORTS)
	public HashMap<String, Object> bookCoachNew(String coachid, String studentid, String date) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		//################处理测试账号开始#####################
		SuserInfo student = dataDao.getObjectById(SuserInfo.class, CommonUtils.parseInt(studentid, 0));
		CuserInfo cuser = dataDao.getObjectById(CuserInfo.class, CommonUtils.parseInt(coachid, 0));
		if(student!=null && cuser!=null){ 
			//学员为测试用户,教练不是测试用户
			if(student.getUsertype()!=null && student.getUsertype()==1 && cuser.getUsertype()!=null && cuser.getUsertype()!=1){
				result.put("message", "学员测试账号不能预约正式教练，请选择测试教练");
				result.put("code", 21);
				return result;
			}
			//正式学员不能预约测试教练
			if(student.getUsertype()!=null && student.getUsertype()==0 && cuser.getUsertype()!=null && cuser.getUsertype()==1){
				result.put("message", "你预约的教练是测试教练，请选择其他教练预约");
				result.put("code", 22);
				return result;
			} 
		}
		//################处理测试账号结束#####################
		
		String failtimes = "";// 不能预订的时间点集合
		int successorderid = 0;// 预订成功的第一个订单的ID
		String hql1 = "from CscheduleInfo where coachid =:coachid and date = :date and hour =:hour";
		String params1[] = { "coachid", "date", "hour" };

		String hql2 = "from CBookTimeInfo where coachid =:coachid and bookedtime =:bookedtime and date =:date";
		String params2[] = { "coachid", "bookedtime", "date" };

		String hql3 = "from CaddAddressInfo where coachid =:coachid and iscurrent = 1";
		String params3[] = { "coachid" };
		
		if(student.getMoney().doubleValue()<0.0||student.getFmoney().doubleValue()<0.0||student.getCoinnum()<0)// 余额不够
		{
			//版本需要更新
			result.put("failtimes",11);
			result.put("successorderid", 11);
			result.put("coachauth", 11);
			result.put("message", "您当前账户余额或冻结金额欠费,无法生成订单!");
			result.put("code", 11);//app应当提示"您当前处于欠费状态,无法生成订单
			return result;
		}

		// 首先查询出订单相关的几个时间配置
		String hqlset = "from SystemSetInfo where 1 = 1";
		SystemSetInfo setInfo = (SystemSetInfo) dataDao.getFirstObjectViaParam(hqlset, null);
		String holidays = "";// 距离订单结束之后可以确认下车的时间默认60分钟
		int systemOrderPull = 0;
		int schoolOrderPull = 0;
		int defaultPrice = 100;
		int defaultSubjectID = 1;
		if (setInfo != null) {
			if (CommonUtils.isEmptyString(setInfo.getHolidays()))
				holidays = setInfo.getHolidays();
			if (setInfo.getOrder_pull() != null && setInfo.getOrder_pull() != 0)
				systemOrderPull = setInfo.getOrder_pull();
			if (setInfo.getCoach_default_price() != null && setInfo.getCoach_default_price() != 0) {
				defaultPrice = setInfo.getCoach_default_price();
			}

			if (setInfo.getCoach_default_subject() != null && setInfo.getCoach_default_subject() != 0) {
				defaultSubjectID = setInfo.getCoach_default_subject();
			}
		}

		
		if (cuser != null && cuser.getDrive_schoolid() != null && cuser.getDrive_schoolid() != 0) {
			DriveSchoolInfo school = dataDao.getObjectById(DriveSchoolInfo.class, cuser.getDrive_schoolid());
			if (school != null && school.getOrder_pull() != null && school.getOrder_pull() != 0) {
				schoolOrderPull = school.getOrder_pull();
			}
		}

		// 订单的提醒设置
		String hqlnoti = "from OrderNotiSetInfo where 1 = 1";
		List<OrderNotiSetInfo> orderNotiList = (List<OrderNotiSetInfo>) dataDao.getObjectsViaParam(hqlnoti, null);

		CsubjectInfo sub = dataDao.getObjectById(CsubjectInfo.class, defaultSubjectID);
		if (sub == null) {
			String hql5 = "from CsubjectInfo where 1=1";
			sub = (CsubjectInfo) dataDao.getFirstObjectViaParam(hql5, null);
		}

		try {
			List<OrderModel> orderList = new ArrayList<OrderModel>();
			JSONArray json = new JSONArray(date);
			//支付额是否充足检测
			int checkResult=checkCanPay(json,coachid,studentid);
			if(checkResult!=0){
				if(checkResult==1){
					result.put("code", 101);
					result.put("message", "小巴币数量不正确，请重试");
				}else if(checkResult==2){
					result.put("code", 102);
					result.put("message", "账户余额不正确，请重试");
				}else if(checkResult==3){
					result.put("code", 103);
					result.put("message", "小巴券数量不正确，请重试");
				}
				return result;
			}
			// 教练信息
			//CuserInfo cUser = dataDao.getObjectById(CuserInfo.class, CommonUtils.parseInt(coachid, 0));
			boolean hasError = false;
			for (int i = 0; i < json.length(); i++) {// 每个循环是一个订单
				boolean canOrder = true;
				JSONObject array = json.getJSONObject(i);
				JSONArray times = array.getJSONArray("time");// 订单的时间点数组
				
				String date1 = array.getString("date");// 订单的日期
				String paytype = array.getString("paytype");// 订单的日期
				
				if (paytype == null || paytype.length() == 0)
				{
					result.put("code", 3);
					result.put("message", "订单没有提交支付方式");
					break;
				}
				String recordid="";
				int delmoney=0;
				//int orderPrice=0;
				int mixMoney=0;//混合支付时余额支付额
				int mixCoin=0;//混合支付时的小巴币数量
				if(String.valueOf(PayType.MONEY).equals(paytype)){
					delmoney= array.getInt("delmoney");
				}else if(String.valueOf(PayType.COUPON).equals(paytype)){
					delmoney= array.getInt("delmoney");
					//orderPrice=array.getInt("total");
					recordid= array.getString("recordid");
					boolean recordFlag=false;
					String[] recordidArray = recordid.split(",");
					if(recordidArray.length>1){
						recordFlag=true;//一次性传入多张券
					}
					
					for (int recoridn = 0; recoridn < recordidArray.length; recoridn++) {
						int cid = CommonUtils.parseInt(recordidArray[recoridn], 0);
						CouponRecord record = dataDao.getObjectById(CouponRecord.class, cid);//该券已经被使用过或者不存在
						if(record==null || record.getState()==1 ){
							recordFlag=true;
						}
					}
					//1.早起版本券的id尾巴上多了一个逗号,2.券的张数跟课时数不匹配,3.传了券id,但没传入delmoney的值,4.传了券id,但抵消金额却小于订单总价|| delmoney<orderPrice
					if((recordid.lastIndexOf(',')==recordid.length()-1)||
							recordid.split(",").length>times.length()||
							(recordid.length()>0&& delmoney<=0) || recordFlag )
					{
						//版本需要更新
						result.put("failtimes", -1);
						result.put("successorderid", -1);
						result.put("coachauth", -1);
						result.put("code", 5);
						result.put("message", "请升级App！");
						return result;
					}

				}else if(String.valueOf(PayType.COIN).equals(paytype)){//小巴币支付
					delmoney= array.getInt("delmoney");
				}else if(String.valueOf(PayType.COIN_MONEY).equals(paytype)){//小巴币支付
					delmoney= array.getInt("delmoney");//小巴币
					mixCoin=delmoney;//小巴币数量
					if(delmoney<0){
						result.put("failtimes", -1);
						result.put("successorderid", -1);
						result.put("coachauth", -1);
						result.put("code", 31);
						result.put("message", "混合支付小巴币个数有误！");
						return result;
					}
					int total=array.getInt("total");//订单总额
					if(total<0){
						result.put("failtimes", -1);
						result.put("successorderid", -1);
						result.put("coachauth", -1);
						result.put("code", 32);
						result.put("message", "混合支付订单总额有误！");
						return result;
					}
					mixMoney=total-delmoney;//余额支付=订单总额-小巴币支付额
					if(mixMoney<0){
						result.put("failtimes", -1);
						result.put("successorderid", -1);
						result.put("coachauth", -1);
						result.put("code", 32);
						result.put("message", "混合支付时余额支付有误！");
						return result;
					}
				}
				String start = "", end = "";// 订单的开始时间和结束时间
				BigDecimal total = new BigDecimal(0);// 订单的总价
				String longitude = null;
				// 纬度
				String latitude = null;
				// 详细地址
				String detail = null;
				// 判断时间是否还可以预订
				// 首先查询当天的全天休息情况
//				List<CscheduleInfo> scheduleinfoList = (List<CscheduleInfo>) dataDao.getObjectsViaParam(hql1, params1, CommonUtils.parseInt(coachid, 0), date1, "0");
//				int state = 0;
//				if (scheduleinfoList != null && scheduleinfoList.size() > 0) {
//					state = scheduleinfoList.get(0).getState();
//					cancel = scheduleinfoList.get(0).getCancelstate();
//				}
				// 如果使用了小巴券,但是又没有传delmoney的话,订单预订失败
				if(String.valueOf(PayType.COUPON).equals(paytype)){
					if (!CommonUtils.isEmptyString(recordid) && delmoney == 0 && recordid.length()>2 ) {//
						for (int j = 0; j < times.length(); j++) {
							if (failtimes.length() == 0) {
								failtimes = date1 + " "+times.get(j).toString() + "点";
							} else {
								failtimes += "," + date1 +" "+ times.get(j).toString() + "点";
							}
						}
						canOrder = false;
					}
				}
//				if (state == 0) {// 当天休息的情况
//					for (int j = 0; j < times.length(); j++) {
//						if (failtimes.length() == 0) {
//							failtimes = date1 + times.get(j).toString() + "点";
//						} else {
//							failtimes += "," + date1 + times.get(j).toString() + "点";
//						}
//					}
//					canOrder = false;
//				} else {
					// 查看时间是否被预订或者是休息的
					for (int j = 0; j < times.length(); j++) {
						String hour = times.get(j).toString();
						if (j == 0) {
							start = hour;
						}

						if (j == times.length() - 1) {
							end = hour;
						}
						List<CBookTimeInfo> booktimeList = (List<CBookTimeInfo>) dataDao.getObjectsViaParam(hql2, params2, CommonUtils.parseInt(coachid, 0), hour, date1);
						if (booktimeList != null && booktimeList.size() > 0) {// 被预订
							if (failtimes.length() == 0) {
								failtimes = date1 + " "+hour + "点";
							} else {
								failtimes += "," + date1 +" "+ hour + "点";
							}
							canOrder = false;
							result.put("message", failtimes+"已经被别人预约了");
							result.put("code", 10);
							return result;
						} else {
							List<CscheduleInfo> scheduleinfoList2 = (List<CscheduleInfo>) dataDao.getObjectsViaParam(hql1, params1, CommonUtils.parseInt(coachid, 0), date1, hour);
							if (scheduleinfoList2 != null && scheduleinfoList2.size() > 0) {
								if (scheduleinfoList2.get(0).getIsrest() == 1) {
									if (failtimes.length() == 0) {// 休息
										failtimes = date1 + " "+hour + "点";
									} else {
										failtimes += "," + date1 + " "+hour + "点";
									}
									canOrder = false;
									result.put("message", failtimes+"是休息的，请刷新后再试!");
									result.put("code", 11);
									return result;
								} else {// 时间点不休息的话,总价增加这个时间点的价格
									total = total.add(scheduleinfoList2.get(0).getPrice());
									if (CommonUtils.isEmptyString(latitude) || CommonUtils.isEmptyString(longitude) || CommonUtils.isEmptyString(detail)) {
										CaddAddressInfo address = dataDao.getObjectById(CaddAddressInfo.class, scheduleinfoList2.get(0).getAddressid());
										if (address != null) {
											latitude = address.getLatitude();
											longitude = address.getLongitude();
											detail = address.getDetail();
										}
									}
								}
							} 
//							else {
//								// 获取教练是否有默认设置
//								int isrest = getDefaultRest(coachid, hour);
//								if (isrest == -1) {// 默认没有设置
//									if (CommonUtils.parseInt(hour, 0) == 5 || CommonUtils.parseInt(hour, 0) == 6 || CommonUtils.parseInt(hour, 0) == 12 || CommonUtils.parseInt(hour, 0) == 18) {
//										if (failtimes.length() == 0) {// 休息
//											failtimes = date1 + hour + "点";
//										} else {
//											failtimes += "," + date1 + hour + "点";
//										}
//										canOrder = false;
//										result.put("message", "教练没有默认设置");
//										result.put("code", 12);
//										return result;
//									} else {
//										// 采用默认的价格设置
//										BigDecimal price = getDefaultPrice(coachid, hour);
//										if (price != null) {
//											total = total.add(price);
//										} else {
//											total = total.add(new BigDecimal(defaultPrice));
//										}
//
//										if (CommonUtils.isEmptyString(latitude) || CommonUtils.isEmptyString(longitude) || CommonUtils.isEmptyString(detail)) {
//											List<CaddAddressInfo> address = (List<CaddAddressInfo>) dataDao.getObjectsViaParam(hql3, params3, CommonUtils.parseInt(coachid, 0));
//											if (address != null && address.size() > 0) {
//												latitude = address.get(0).getLatitude();
//												longitude = address.get(0).getLongitude();
//												detail = address.get(0).getDetail();
//											}
//										}
//									}
//								} else if (isrest == 1) {
//									if (failtimes.length() == 0) {// 休息
//										failtimes = date1 + hour + "点";
//									} else {
//										failtimes += "," + date1 + hour + "点";
//									}
//									canOrder = false;
//									result.put("message", failtimes+"是休息，请刷新后再试!");
//									result.put("code", 13);
//									return result;
//								} else {
//									// 采用默认的价格设置
//									BigDecimal price = getDefaultPrice(coachid, hour);
//									if (price != null) {
//										total = total.add(price);
//									} else {
//										total = total.add(new BigDecimal(defaultPrice));
//									}
//
//									if (CommonUtils.isEmptyString(latitude) || CommonUtils.isEmptyString(longitude) || CommonUtils.isEmptyString(detail)) {
//										List<CaddAddressInfo> address = (List<CaddAddressInfo>) dataDao.getObjectsViaParam(hql3, params3, CommonUtils.parseInt(coachid, 0));
//										if (address != null && address.size() > 0) {
//											latitude = address.get(0).getLatitude();
//											longitude = address.get(0).getLongitude();
//											detail = address.get(0).getDetail();
//										}
//									}
//								}
//
//							}
						}
					}

					if (CommonUtils.isEmptyString(latitude) || CommonUtils.isEmptyString(longitude) || CommonUtils.isEmptyString(detail)) {
						List<CaddAddressInfo> address = (List<CaddAddressInfo>) dataDao.getObjectsViaParam(hql3, params3, CommonUtils.parseInt(coachid, 0));
						if (address != null && address.size() > 0) {
							latitude = address.get(0).getLatitude();
							longitude = address.get(0).getLongitude();
							detail = address.get(0).getDetail();
						}
					}

					if (canOrder) {// 这个订单是OK的,可以生成订单的
						OrderModel orderModel = new OrderModel();
						OrderInfo order = new OrderInfo();
						order.setCoachid(CommonUtils.parseInt(coachid, 0));// 教练ID
						order.setStudentid(CommonUtils.parseInt(studentid, 0));// 学员ID
						order.setCreat_time(new Date());// 添加时间
						order.setDate(date1);// 预订日期
						order.setCouponrecordid(recordid);
						order.setDelmoney(delmoney);
						order.setPaytype(CommonUtils.parseInt(paytype, 0));

						Date startTime = CommonUtils.getDateFormat(date1, "yyyy-MM-dd");
						Calendar startC = Calendar.getInstance();
						startC.setTime(startTime);
						startC.set(Calendar.HOUR_OF_DAY, CommonUtils.parseInt(start, 0));
						startC.set(Calendar.MINUTE, 0);
						startC.set(Calendar.SECOND, 0);
						order.setStart_time(startC.getTime());// 开始时间

						Date endTime = CommonUtils.getDateFormat(date1, "yyyy-MM-dd");
						Calendar endC = Calendar.getInstance();
						endC.setTime(endTime);
						endC.set(Calendar.HOUR_OF_DAY, CommonUtils.parseInt(end, 0) + 1);
						endC.set(Calendar.MINUTE, 0);
						endC.set(Calendar.SECOND, 0);
						order.setEnd_time(endC.getTime());// 结束时间

						order.setTime(CommonUtils.parseInt(end, 0) - CommonUtils.parseInt(start, 0) + 1);// 订单的时长

						// 订单总价格
						order.setTotal(total);
						order.setMixCoin(mixCoin);
						order.setMixMoney(mixMoney);
						order.setStudentstate(0);// 学生端订单状态
						order.setCoachstate(0);// 教练段任务状态
						// 地址相关
						order.setLatitude(latitude);
						order.setLongitude(longitude);
						order.setDetail(detail);

						// 订单是否可以被取消
						// if (cancel != -1) {
						// order.setCancancel(cancel);
						// } else {
						// // 首先判断是否是节假日或者是星期天
						// Calendar calBookDate = Calendar.getInstance();
						// calBookDate.setTime(startTime);
						// int dayOfWeek = calBookDate.get(Calendar.DAY_OF_WEEK);
						// if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
						// order.setCancancel(1);
						// } else {
						// String dateCheck = CommonUtils.getTimeFormat(startTime, "yyyy年MM月dd日");
						// if (holidays.contains(dateCheck)) {
						// order.setCancancel(1);
						// } else {
						// order.setCancancel(0);
						// }
						// }
						// }
						order.setCancancel(1);// 目前所有订单不可以取消

						order.setPrice_out1(new BigDecimal(0d));
						order.setPrice_out2(new BigDecimal(0d));
						// 订单的抽成相关
						order.setOrder_pull1(systemOrderPull);
						order.setOrder_pull2(schoolOrderPull);

						orderModel.mOrderInfo = order;

						for (int j = 0; j < times.length(); j++) {// 记录时间被预订
																	// 和时间点的价格等信息
							String hour = times.get(j).toString();
							OrderPrice orderprice = new OrderPrice();
							orderprice.setOrderid(order.getOrderid());// 所属订单
							orderprice.setHour(hour);// 时间

							List<CscheduleInfo> scheduleinfoList2 = (List<CscheduleInfo>) dataDao.getObjectsViaParam(hql1, params1, CommonUtils.parseInt(coachid, 0), date1, hour);
							if (scheduleinfoList2 != null && scheduleinfoList2.size() > 0) {
								CaddAddressInfo address = dataDao.getObjectById(CaddAddressInfo.class, scheduleinfoList2.get(0).getAddressid());
								if (address != null) {
									orderprice.setDetail(address.getDetail());
									orderprice.setLatitude(address.getLatitude());
									orderprice.setLongitude(address.getLongitude());
								} else {
									orderprice.setDetail(detail);
									orderprice.setLatitude(latitude);
									orderprice.setLongitude(longitude);
								}
								orderprice.setPrice(scheduleinfoList2.get(0).getPrice());

								CsubjectInfo subject = dataDao.getObjectById(CsubjectInfo.class, scheduleinfoList2.get(0).getSubjectid());
								if (subject != null) {
									orderprice.setSubject(subject.getSubjectname());
								} else {
									orderprice.setSubject(sub.getSubjectname());
								}
							} else {
								List<CaddAddressInfo> address = (List<CaddAddressInfo>) dataDao.getObjectsViaParam(hql3, params3, CommonUtils.parseInt(coachid, 0));
								if (address != null && address.size() > 0) {
									orderprice.setDetail(address.get(0).getDetail());
									orderprice.setLatitude(address.get(0).getLatitude());
									orderprice.setLongitude(address.get(0).getLongitude());
								}

								BigDecimal price = getDefaultPrice(coachid, hour);
								if (price != null) {
									orderprice.setPrice(price);
								} else {
									orderprice.setPrice(new BigDecimal(defaultPrice));
								}

								orderprice.setSubject(sub.getSubjectname());
							}

							if (orderModel.OrderPriceList == null) {
								orderModel.OrderPriceList = new ArrayList<OrderPrice>();
							}
							orderModel.OrderPriceList.add(orderprice);

							CBookTimeInfo booktime = new CBookTimeInfo();
							booktime.setCoachid(CommonUtils.parseInt(coachid, 0));
							booktime.setDate(date1);
							booktime.setBookedtime(hour);

							List<CBookTimeInfo> booktimeList = (List<CBookTimeInfo>) dataDao.getFirstObjectViaParam(hql1, params1, CommonUtils.parseInt(coachid, 0), hour, date1);
							if (booktimeList == null || booktimeList.size() == 0) {
								if (orderModel.CBookTimeInfoList == null) {
									orderModel.CBookTimeInfoList = new ArrayList<CBookTimeInfo>();
								}
								orderModel.CBookTimeInfoList.add(booktime);
							}
							cscheduleService.setCscheduleByday(coachid, date1, hour,1);
							
						}
						orderList.add(orderModel);
					} else {
						hasError = true;
					}
				//}
				//判断订单总价格，如果没有在50到500价格之间，返回预订失败
				/*if(total.doubleValue()>500 ||total.doubleValue()<50){
					result.put("code", 26);
					result.put("message", "订单额非法");
					return result;
				}*/
					
			}
			
			if (!hasError) {
				if (orderList.size() > 0) {
					
					for (int m = 0; m < orderList.size(); m++) {
						BigDecimal total = new BigDecimal(0);
						//混合支付时：
						int mixCoin=0;//混合支付时小巴币个数
						int mixMoney=0;//混合支付时余额
						dataDao.addObject(orderList.get(m).mOrderInfo);
						// 查看订单的提醒设置
						if (orderNotiList != null && orderNotiList.size() > 0) {
							for (OrderNotiSetInfo setInfo1 : orderNotiList) {
								int minute = setInfo1.getBeforeminute();
								int type = setInfo1.getType();
								if (minute == 0)
									continue;
								Calendar start = Calendar.getInstance();
								start.setTime(orderList.get(m).mOrderInfo.getStart_time());
								start.add(Calendar.MINUTE, -minute);
								OrderNotiRecord record = new OrderNotiRecord();
								record.setAddtime(new Date());
								record.setBeforeminute(minute);
								record.setOrderid(orderList.get(m).mOrderInfo.getOrderid());
								record.setSendtime(start.getTime());
								record.setType(type);
								record.setCoachid(orderList.get(m).mOrderInfo.getCoachid());
								record.setStudentid(orderList.get(m).mOrderInfo.getStudentid());
								dataDao.addObject(record);
							}
						}

						if (successorderid == 0)
							successorderid = orderList.get(m).mOrderInfo.getOrderid();
						if (orderList.get(m).CBookTimeInfoList != null) {
							for (CBookTimeInfo booktime : orderList.get(m).CBookTimeInfoList) {
								dataDao.addObject(booktime);
							}
						}

						if (orderList.get(m).OrderPriceList != null) {
							for (OrderPrice mOrderPrice : orderList.get(m).OrderPriceList) {
								mOrderPrice.setOrderid(orderList.get(m).mOrderInfo.getOrderid());
								dataDao.addObject(mOrderPrice);
							}
						}
						total = total.add(orderList.get(m).mOrderInfo.getTotal());// 总价中增加订单的总价
						
						
						// 小巴券，判断，如果 2 
						if(PayType.COUPON==orderList.get(m).mOrderInfo.getPaytype()){
							
							total = total.subtract(new BigDecimal(orderList.get(m).mOrderInfo.getDelmoney()));// 减去小巴券中抵掉的金额
								if (orderList.get(m).mOrderInfo.getCouponrecordid() != null && orderList.get(m).mOrderInfo.getCouponrecordid().length() > 0) {
									String[] recordidArray = orderList.get(m).mOrderInfo.getCouponrecordid().split(",");
									for (int i = 0; i < recordidArray.length; i++) {
										int cid = CommonUtils.parseInt(recordidArray[i], 0);
										CouponRecord record = dataDao.getObjectById(CouponRecord.class, cid);
										if (record != null && record.getState()==0) {//未被使用
											record.setState(1);// 小巴券的状态修改为已经使用
											record.setUsetime(new Date());//使用时间
											record.setOrderid(orderList.get(m).mOrderInfo.getOrderid());//订单号
											dataDao.updateObject(record);
											if (record.getCoupontype() == 1) {// 时间
											} else {// 钱
												// 只需要修改为已经使用,
											}
										}
									}
								}
						}
						
						// 修改用户的余额，如果是paytype是1 余额  2 小巴卷 3 小巴币   

						if (student != null) {
							
							//  判断 1 或者 3  1 扣余额
							if(PayType.MONEY==orderList.get(m).mOrderInfo.getPaytype()){
								if(student.getMoney().subtract(total).doubleValue()<0){
									result.put("code", 4);
									result.put("message", "账户余额不足！");
									return result;
									
								}
								student.setFmoney(student.getFmoney().add(total));
								student.setMoney(student.getMoney().subtract(total));
								/*
								 * 学员下订单时，订单价格为M，此时学员的余额减M，冻结金额加M,教练的账户金额不变
									取消订单时，学员的冻结金额减去M,学员的余额加M,教练的账户金额不变
								 */
								if (student.getMoney().doubleValue() < 0 || student.getFmoney().doubleValue() < 0) {
									result.put("failtimes", failtimes);
									result.put("successorderid", successorderid);
									result.put("coachauth", student.getCoachstate());
									if (failtimes.length() == 0) {
										result.put("code", 15);
									} else {
										result.put("code", 2);
									}
									result.put("message", "账户余额不足！");
									return result;
								}
							}else if(PayType.COIN==orderList.get(m).mOrderInfo.getPaytype()){
								BigDecimal cnum = new BigDecimal(student.getCoinnum());
								double dc=cnum.subtract(total).doubleValue();
								//小巴币大于0，并且剩余小巴币余额减去支付额大于等于0，表示余额购，否则余额不足
								if(dc>=0){
									student.setCoinnum((int)dc); //学员小巴币数量减少
									student.setFcoinnum(student.getFcoinnum().add(total));
								}else{
									//不足
									result.put("code", 6);
									result.put("message", "小巴币余额不足!");
									return result;
								}
								//教练小巴币数量增加
								//int coachid=orderList.get(m).mOrderInfo.getCoachid();
								if(cuser!=null){
									//cuser.setCoinnum(cuser.getCoinnum()+total.intValue());
									//并且冻结教练订单总额的小巴币，直到双方互评后取消冻结，已防止在为评价前教练提现
									//cuser.setFcoinnum(cuser.getFcoinnum()+total.intValue());
									//dataDao.updateObject(cuser);
									//System.out.println("教练获取小巴币成功"+total.intValue());
								}else{
									//System.out.println("教练获取小巴币失败"+total.intValue());
								}
								//suserService.addCoinForSettlement(order, cuser, student,1);
								//向小巴币记录表中插入数据 已修改，移动到结算方法中
								/////////////////////////////////////////////
								/* CoinRecordInfo coinRecordInfo = new CoinRecordInfo ();
							        coinRecordInfo.setReceiverid(cuser.getCoachid());
							        coinRecordInfo.setReceivertype(UserType.COAH);
							        coinRecordInfo.setReceivername(cuser.getRealname());
							        coinRecordInfo.setOwnerid(cuser.getCoachid());
							        coinRecordInfo.setPayerid(student.getStudentid());
							        coinRecordInfo.setPayertype(UserType.STUDENT);
							        coinRecordInfo.setPayername(student.getRealname());
							        coinRecordInfo.setType(CoinType.STUDENT_PAY);//学员支付
							        coinRecordInfo.setOwnertype(2);
							        coinRecordInfo.setCoinnum(total.intValue());
							        coinRecordInfo.setAddtime(new Date());
							        coinRecordInfo.setOrderid(orderList.get(m).mOrderInfo.getOrderid());//设置小巴币所属的订单的ID
							        dataDao.addObject(coinRecordInfo);*/
								///////////////////////////////////////////
							}else if(PayType.COIN_MONEY==orderList.get(m).mOrderInfo.getPaytype()){
								//小巴币和余额混合支付
								//#############先处理小巴币 开始###########################  
								int mixcoin=orderList.get(m).mOrderInfo.getMixCoin();
								
								//BigDecimal cnum = new BigDecimal(student.getCoinnum());
								double dc=student.getCoinnum()-mixcoin;
								//小巴币大于0，并且剩余小巴币余额减去支付额大于等于0，表示余额购，否则余额不足
								if(dc>=0){
									student.setCoinnum((int)dc); //学员小巴币数量减少
									student.setFcoinnum(student.getFcoinnum().add(new BigDecimal(mixcoin)));
								}else{
									//不足
									result.put("code", 6);
									result.put("message", "小巴币余额不足!");
									return result;
								}
								//#############小巴币处理结束###########################    
								//#############余额支付开始############################
								int mixmoney=orderList.get(m).mOrderInfo.getMixMoney();
								BigDecimal mmoney=new BigDecimal(mixmoney);
								if(student.getMoney().subtract(mmoney).doubleValue()<0){
									result.put("code", 4);
									result.put("message", "账户余额不足！");
									return result;
								}
								student.setFmoney(student.getFmoney().add(mmoney));
								student.setMoney(student.getMoney().subtract(mmoney));
								/*
								 * 学员下订单时，订单价格为M，此时学员的余额减M，冻结金额加M,教练的账户金额不变
									取消订单时，学员的冻结金额减去M,学员的余额加M,教练的账户金额不变
								 */
								if (student.getMoney().doubleValue() < 0 || student.getFmoney().doubleValue() < 0) {
									result.put("failtimes", failtimes);
									result.put("successorderid", successorderid);
									result.put("coachauth", student.getCoachstate());
									if (failtimes.length() == 0) {
										result.put("code", 15);
									} else {
										result.put("code", 2);
									}
									result.put("message", "账户余额不足！");
									return result;
								}
								//#############余额支付结束############################
							}
							dataDao.updateObject(student);
						}
					}
				

					// 推送通知教练有新的订单
					String hql5 = "from UserPushInfo where userid =:userid and usertype = 1";
					String params5[] = { "userid" };
					UserPushInfo userpush = (UserPushInfo) dataDao.getFirstObjectViaParam(hql5, params5, CommonUtils.parseInt(coachid, 0));
					String msg="您有新的订单了,学员"+student.getRealname()+"预约了你的课程";
					if (userpush != null) {
						if (userpush.getType() == 0 && !CommonUtils.isEmptyString(userpush.getJpushid())) {// 安卓
							PushtoSingle pushsingle = new PushtoSingle();
							pushsingle.pushsingle(userpush.getJpushid(), 1, "{\"message\":\"" + msg + "\",\"type\":\"1\"}");
						} else if (userpush.getType() == 1 && !CommonUtils.isEmptyString(userpush.getDevicetoken())) {
							ApplePushUtil.sendpush(userpush.getDevicetoken(), "{\"aps\":{\"alert\":\"" + msg + "\",\"sound\":\"default\"},\"userid\":" + coachid + "}", 1, 1);
						}
					}
					
					// 增加学员与教练的关系
					String coachStudentHql = "from CoachStudentInfo where coachid = :coachid and studentid = :studentid";
					String[] params8 = { "coachid", "studentid" };
					CoachStudentInfo info = (CoachStudentInfo) dataDao.getFirstObjectViaParam(coachStudentHql, params8, CommonUtils.parseInt(coachid, 0), CommonUtils.parseInt(studentid, 0));
					if (info == null) {
						info = new CoachStudentInfo();
						info.setCoachid(CommonUtils.parseInt(coachid, 0));
						info.setHour(0);
						info.setMoney(new BigDecimal(0));
						info.setStudentid(CommonUtils.parseInt(studentid, 0));
						dataDao.addObject(info);
					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
			//版本需要更新
			result.put("failtimes", -1);
			result.put("successorderid", -1);
			result.put("coachauth", -1);
			result.put("message", "预约出错");
			result.put("code", -1);
			return result;
			
		}
		result.put("failtimes", failtimes);
		result.put("successorderid", successorderid);
		result.put("coachauth", student.getCoachstate());
		if (failtimes.length() == 0) {
			result.put("code", 1);
			result.put("message", "OK");
		} else {
			result.put("code", 2);
			result.put("message", "预约出错,预约失败的时间"+failtimes);
		}
		return result;
	}
	@Transactional(readOnly = false, propagation = Propagation.SUPPORTS)
	public HashMap<String, Object> bookCoach2(String coachid, String studentid, String date) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		//################处理测试账号开始#####################
		SuserInfo student = dataDao.getObjectById(SuserInfo.class, CommonUtils.parseInt(studentid, 0));
		CuserInfo cuser = dataDao.getObjectById(CuserInfo.class, CommonUtils.parseInt(coachid, 0));
		if(student!=null && cuser!=null){ 
			//学员为测试用户,教练不是测试用户
			if(student.getUsertype()!=null && student.getUsertype()==1 && cuser.getUsertype()!=null && cuser.getUsertype()!=1){
				result.put("message", "学员测试账号不能预约正式教练，请选择测试教练");
				result.put("code", 21);
				return result;
			}
			//正式学员不能预约测试教练
			if(student.getUsertype()!=null && student.getUsertype()==0 && cuser.getUsertype()!=null && cuser.getUsertype()==1){
				result.put("message", "你预约的教练是测试教练，请选择其他教练预约");
				result.put("code", 22);
				return result;
			}
		}
		/*if(student!=null && "18888888888".equals(student.getPhone())){
			result.put("message", "测试账号不能预约");
			result.put("code", 20);
			return result;
		}*/
		//################处理测试账号结束#####################
		
		
		
		String failtimes = "";// 不能预订的时间点集合
		int successorderid = 0;// 预订成功的第一个订单的ID
		String hql1 = "from CscheduleInfo where coachid =:coachid and date = :date and hour =:hour";
		String params1[] = { "coachid", "date", "hour" };

		String hql2 = "from CBookTimeInfo where coachid =:coachid and bookedtime =:bookedtime and date =:date";
		String params2[] = { "coachid", "bookedtime", "date" };

		String hql3 = "from CaddAddressInfo where coachid =:coachid and iscurrent = 1";
		String params3[] = { "coachid" };
		
		 
		
		if(student.getMoney().doubleValue()<0.0||student.getFmoney().doubleValue()<0.0||student.getCoinnum()<0)// 余额不够
		{
			//版本需要更新
			result.put("failtimes",11);
			result.put("successorderid", 11);
			result.put("coachauth", 11);
			result.put("message", "您当前账户余额或冻结金额欠费,无法生成订单!");
			result.put("code", 11);//app应当提示"您当前处于欠费状态,无法生成订单
			return result;
		}

		// 首先查询出订单相关的几个时间配置
		String hqlset = "from SystemSetInfo where 1 = 1";
		SystemSetInfo setInfo = (SystemSetInfo) dataDao.getFirstObjectViaParam(hqlset, null);
		String holidays = "";// 距离订单结束之后可以确认下车的时间默认60分钟
		int systemOrderPull = 0;
		int schoolOrderPull = 0;
		int defaultPrice = 100;
		int defaultSubjectID = 1;
		if (setInfo != null) {
			if (CommonUtils.isEmptyString(setInfo.getHolidays()))
				holidays = setInfo.getHolidays();
			if (setInfo.getOrder_pull() != null && setInfo.getOrder_pull() != 0)
				systemOrderPull = setInfo.getOrder_pull();
			if (setInfo.getCoach_default_price() != null && setInfo.getCoach_default_price() != 0) {
				defaultPrice = setInfo.getCoach_default_price();
			}

			if (setInfo.getCoach_default_subject() != null && setInfo.getCoach_default_subject() != 0) {
				defaultSubjectID = setInfo.getCoach_default_subject();
			}
		}

		
		if (cuser != null && cuser.getDrive_schoolid() != null && cuser.getDrive_schoolid() != 0) {
			DriveSchoolInfo school = dataDao.getObjectById(DriveSchoolInfo.class, cuser.getDrive_schoolid());
			if (school != null && school.getOrder_pull() != null && school.getOrder_pull() != 0) {
				schoolOrderPull = school.getOrder_pull();
			}
		}


		// 订单的提醒设置
		String hqlnoti = "from OrderNotiSetInfo where 1 = 1";
		List<OrderNotiSetInfo> orderNotiList = (List<OrderNotiSetInfo>) dataDao.getObjectsViaParam(hqlnoti, null);

		CsubjectInfo sub = dataDao.getObjectById(CsubjectInfo.class, defaultSubjectID);
		if (sub == null) {
			String hql5 = "from CsubjectInfo where 1=1";
			sub = (CsubjectInfo) dataDao.getFirstObjectViaParam(hql5, null);
		}

		try {
			List<OrderModel> orderList = new ArrayList<OrderModel>();
			JSONArray json = new JSONArray(date);
			// 教练信息
			//CuserInfo cUser = dataDao.getObjectById(CuserInfo.class, CommonUtils.parseInt(coachid, 0));
			boolean hasError = false;
			for (int i = 0; i < json.length(); i++) {// 每个循环是一个订单
				boolean canOrder = true;
				JSONObject array = json.getJSONObject(i);
				JSONArray times = array.getJSONArray("time");// 订单的时间点数组
				
				String date1 = array.getString("date");// 订单的日期
				String paytype = array.getString("paytype");// 订单的日期
				
				if (paytype == null || paytype.length() == 0)
				{
					result.put("code", 3);
					result.put("message", "订单没有提交支付方式");
					break;
				}
				String recordid="";
				int delmoney=0;
				int orderPrice=0;
				if(String.valueOf(PayType.MONEY).equals(paytype)){
					delmoney= array.getInt("delmoney");
				}else if(String.valueOf(PayType.COUPON).equals(paytype)){
					delmoney= array.getInt("delmoney");
					//orderPrice=array.getInt("total");
					recordid= array.getString("recordid");
					boolean recordFlag=false;
					String[] recordidArray = recordid.split(",");
					if(recordidArray.length>1){
						recordFlag=true;//一次性传入多张券
					}
					
					for (int recoridn = 0; recoridn < recordidArray.length; recoridn++) {
						int cid = CommonUtils.parseInt(recordidArray[recoridn], 0);
						CouponRecord record = dataDao.getObjectById(CouponRecord.class, cid);//该券已经被使用过或者不存在
						if(record==null || record.getState()==1 ){
							recordFlag=true;
						}
					}
					//1.早起版本券的id尾巴上多了一个逗号,2.券的张数跟课时数不匹配,3.传了券id,但没传入delmoney的值,4.传了券id,但抵消金额却小于订单总价|| delmoney<orderPrice
					if((recordid.lastIndexOf(',')==recordid.length()-1)||
							recordid.split(",").length>times.length()||
							(recordid.length()>0&& delmoney<=0) || recordFlag )
					{
						//版本需要更新
						result.put("failtimes", -1);
						result.put("successorderid", -1);
						result.put("coachauth", -1);
						result.put("code", 5);
						result.put("message", "请升级App！");
						return result;
					}

				}else if(String.valueOf(PayType.COIN).equals(paytype)){//小巴币支付
					delmoney= array.getInt("delmoney");
				}
			/*	System.out.println("##############################################");
				System.out.println("paytype="+paytype);
				System.out.println("date1="+date1);
				System.out.println("delmoney="+delmoney);
				System.out.println("recordid="+recordid);
				System.out.println("##############################################");*/
				String start = "", end = "";// 订单的开始时间和结束时间

				BigDecimal total = new BigDecimal(0);// 订单的总价
				String longitude = null;
				// 纬度
				String latitude = null;
				// 详细地址
				String detail = null;
				int cancel = -1;// 订单是否可以取消 默认 为0 可以取消
				// 判断时间是否还可以预订
				// 首先查询当天的全天休息情况
				List<CscheduleInfo> scheduleinfoList = (List<CscheduleInfo>) dataDao.getObjectsViaParam(hql1, params1, CommonUtils.parseInt(coachid, 0), date1, "0");
				int state = 0;
				if (scheduleinfoList != null && scheduleinfoList.size() > 0) {
					state = scheduleinfoList.get(0).getState();
					cancel = scheduleinfoList.get(0).getCancelstate();
				}

				
				// 如果使用了小巴券,但是又没有传delmoney的话,订单预订失败
				if(String.valueOf(PayType.COUPON).equals(paytype)){
				
					if (!CommonUtils.isEmptyString(recordid) && delmoney == 0 && recordid.length()>2 ) {//
						for (int j = 0; j < times.length(); j++) {
							if (failtimes.length() == 0) {
								failtimes = date1 + times.get(j).toString() + "点";
							} else {
								failtimes += "," + date1 + times.get(j).toString() + "点";
							}
						}
						canOrder = false;
					}
				
				}
				if (state == 0) {// 当天休息的情况
					for (int j = 0; j < times.length(); j++) {
						if (failtimes.length() == 0) {
							failtimes = date1 + times.get(j).toString() + "点";
						} else {
							failtimes += "," + date1 + times.get(j).toString() + "点";
						}
					}
					canOrder = false;
				} else {
					// 查看时间是否被预订或者是休息的
					for (int j = 0; j < times.length(); j++) {
						String hour = times.get(j).toString();
						if (j == 0) {
							start = hour;
						}

						if (j == times.length() - 1) {
							end = hour;
						}
						List<CBookTimeInfo> booktimeList = (List<CBookTimeInfo>) dataDao.getObjectsViaParam(hql2, params2, CommonUtils.parseInt(coachid, 0), hour, date1);
						if (booktimeList != null && booktimeList.size() > 0) {// 被预订
							if (failtimes.length() == 0) {
								failtimes = date1 + hour + "点";
							} else {
								failtimes += "," + date1 + hour + "点";
							}
							canOrder = false;
							result.put("message", failtimes+"已经被别人预约了");
							result.put("code", 10);
							return result;
						} else {
							List<CscheduleInfo> scheduleinfoList2 = (List<CscheduleInfo>) dataDao.getObjectsViaParam(hql1, params1, CommonUtils.parseInt(coachid, 0), date1, hour);
							if (scheduleinfoList2 != null && scheduleinfoList2.size() > 0) {
								if (scheduleinfoList2.get(0).getIsrest() == 1) {
									if (failtimes.length() == 0) {// 休息
										failtimes = date1 + hour + "点";
									} else {
										failtimes += "," + date1 + hour + "点";
									}
									canOrder = false;
									result.put("message", failtimes+"是休息的，请刷新后再试!");
									result.put("code", 11);
									return result;
								} else {// 时间点不休息的话,总价增加这个时间点的价格
									total = total.add(scheduleinfoList2.get(0).getPrice());
									if (CommonUtils.isEmptyString(latitude) || CommonUtils.isEmptyString(longitude) || CommonUtils.isEmptyString(detail)) {
										CaddAddressInfo address = dataDao.getObjectById(CaddAddressInfo.class, scheduleinfoList2.get(0).getAddressid());
										if (address != null) {
											latitude = address.getLatitude();
											longitude = address.getLongitude();
											detail = address.getDetail();
										}
									}
								}
							} else {
								// 获取教练是否有默认设置
								int isrest = getDefaultRest(coachid, hour);
								if (isrest == -1) {// 默认没有设置
									if (CommonUtils.parseInt(hour, 0) == 5 || CommonUtils.parseInt(hour, 0) == 6 || CommonUtils.parseInt(hour, 0) == 12 || CommonUtils.parseInt(hour, 0) == 18) {
										if (failtimes.length() == 0) {// 休息
											failtimes = date1 + hour + "点";
										} else {
											failtimes += "," + date1 + hour + "点";
										}
										canOrder = false;
										result.put("message", "教练没有默认设置");
										result.put("code", 12);
										return result;
									} else {
										// 采用默认的价格设置
										BigDecimal price = getDefaultPrice(coachid, hour);
										if (price != null) {
											total = total.add(price);
										} else {
											total = total.add(new BigDecimal(defaultPrice));
										}

										if (CommonUtils.isEmptyString(latitude) || CommonUtils.isEmptyString(longitude) || CommonUtils.isEmptyString(detail)) {
											List<CaddAddressInfo> address = (List<CaddAddressInfo>) dataDao.getObjectsViaParam(hql3, params3, CommonUtils.parseInt(coachid, 0));
											if (address != null && address.size() > 0) {
												latitude = address.get(0).getLatitude();
												longitude = address.get(0).getLongitude();
												detail = address.get(0).getDetail();
											}
										}
									}
								} else if (isrest == 1) {
									if (failtimes.length() == 0) {// 休息
										failtimes = date1 + hour + "点";
									} else {
										failtimes += "," + date1 + hour + "点";
									}
									canOrder = false;
									result.put("message", failtimes+"是休息，请刷新后再试!");
									result.put("code", 13);
									return result;
								} else {
									// 采用默认的价格设置
									BigDecimal price = getDefaultPrice(coachid, hour);
									if (price != null) {
										total = total.add(price);
									} else {
										total = total.add(new BigDecimal(defaultPrice));
									}

									if (CommonUtils.isEmptyString(latitude) || CommonUtils.isEmptyString(longitude) || CommonUtils.isEmptyString(detail)) {
										List<CaddAddressInfo> address = (List<CaddAddressInfo>) dataDao.getObjectsViaParam(hql3, params3, CommonUtils.parseInt(coachid, 0));
										if (address != null && address.size() > 0) {
											latitude = address.get(0).getLatitude();
											longitude = address.get(0).getLongitude();
											detail = address.get(0).getDetail();
										}
									}
								}

							}
						}
					}

					if (CommonUtils.isEmptyString(latitude) || CommonUtils.isEmptyString(longitude) || CommonUtils.isEmptyString(detail)) {
						List<CaddAddressInfo> address = (List<CaddAddressInfo>) dataDao.getObjectsViaParam(hql3, params3, CommonUtils.parseInt(coachid, 0));
						if (address != null && address.size() > 0) {
							latitude = address.get(0).getLatitude();
							longitude = address.get(0).getLongitude();
							detail = address.get(0).getDetail();
						}
					}

					if (canOrder) {// 这个订单是OK的,可以生成订单的
						OrderModel orderModel = new OrderModel();
						OrderInfo order = new OrderInfo();
						order.setCoachid(CommonUtils.parseInt(coachid, 0));// 教练ID
						order.setStudentid(CommonUtils.parseInt(studentid, 0));// 学员ID
						order.setCreat_time(new Date());// 添加时间
						order.setDate(date1);// 预订日期
						order.setCouponrecordid(recordid);
						order.setDelmoney(delmoney);
						order.setPaytype(CommonUtils.parseInt(paytype, 0));

						Date startTime = CommonUtils.getDateFormat(date1, "yyyy-MM-dd");
						Calendar startC = Calendar.getInstance();
						startC.setTime(startTime);
						startC.set(Calendar.HOUR_OF_DAY, CommonUtils.parseInt(start, 0));
						startC.set(Calendar.MINUTE, 0);
						startC.set(Calendar.SECOND, 0);
						order.setStart_time(startC.getTime());// 开始时间

						Date endTime = CommonUtils.getDateFormat(date1, "yyyy-MM-dd");
						Calendar endC = Calendar.getInstance();
						endC.setTime(endTime);
						endC.set(Calendar.HOUR_OF_DAY, CommonUtils.parseInt(end, 0) + 1);
						endC.set(Calendar.MINUTE, 0);
						endC.set(Calendar.SECOND, 0);
						order.setEnd_time(endC.getTime());// 结束时间

						order.setTime(CommonUtils.parseInt(end, 0) - CommonUtils.parseInt(start, 0) + 1);// 订单的时长

						// 订单总价格
						order.setTotal(total);
						order.setStudentstate(0);// 学生端订单状态
						order.setCoachstate(0);// 教练段任务状态
						// 地址相关
						order.setLatitude(latitude);
						order.setLongitude(longitude);
						order.setDetail(detail);

						// 订单是否可以被取消
						// if (cancel != -1) {
						// order.setCancancel(cancel);
						// } else {
						// // 首先判断是否是节假日或者是星期天
						// Calendar calBookDate = Calendar.getInstance();
						// calBookDate.setTime(startTime);
						// int dayOfWeek = calBookDate.get(Calendar.DAY_OF_WEEK);
						// if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
						// order.setCancancel(1);
						// } else {
						// String dateCheck = CommonUtils.getTimeFormat(startTime, "yyyy年MM月dd日");
						// if (holidays.contains(dateCheck)) {
						// order.setCancancel(1);
						// } else {
						// order.setCancancel(0);
						// }
						// }
						// }
						order.setCancancel(1);// 目前所有订单不可以取消

						order.setPrice_out1(new BigDecimal(0d));
						order.setPrice_out2(new BigDecimal(0d));
						// 订单的抽成相关
						order.setOrder_pull1(systemOrderPull);
						order.setOrder_pull2(schoolOrderPull);

						orderModel.mOrderInfo = order;

						for (int j = 0; j < times.length(); j++) {// 记录时间被预订
																	// 和时间点的价格等信息
							String hour = times.get(j).toString();
							OrderPrice orderprice = new OrderPrice();
							orderprice.setOrderid(order.getOrderid());// 所属订单
							orderprice.setHour(hour);// 时间

							List<CscheduleInfo> scheduleinfoList2 = (List<CscheduleInfo>) dataDao.getObjectsViaParam(hql1, params1, CommonUtils.parseInt(coachid, 0), date1, hour);
							if (scheduleinfoList2 != null && scheduleinfoList2.size() > 0) {
								CaddAddressInfo address = dataDao.getObjectById(CaddAddressInfo.class, scheduleinfoList2.get(0).getAddressid());
								if (address != null) {
									orderprice.setDetail(address.getDetail());
									orderprice.setLatitude(address.getLatitude());
									orderprice.setLongitude(address.getLongitude());
								} else {
									orderprice.setDetail(detail);
									orderprice.setLatitude(latitude);
									orderprice.setLongitude(longitude);
								}
								orderprice.setPrice(scheduleinfoList2.get(0).getPrice());

								CsubjectInfo subject = dataDao.getObjectById(CsubjectInfo.class, scheduleinfoList2.get(0).getSubjectid());
								if (subject != null) {
									orderprice.setSubject(subject.getSubjectname());
								} else {
									orderprice.setSubject(sub.getSubjectname());
								}
							} else {
								List<CaddAddressInfo> address = (List<CaddAddressInfo>) dataDao.getObjectsViaParam(hql3, params3, CommonUtils.parseInt(coachid, 0));
								if (address != null && address.size() > 0) {
									orderprice.setDetail(address.get(0).getDetail());
									orderprice.setLatitude(address.get(0).getLatitude());
									orderprice.setLongitude(address.get(0).getLongitude());
								}

								BigDecimal price = getDefaultPrice(coachid, hour);
								if (price != null) {
									orderprice.setPrice(price);
								} else {
									orderprice.setPrice(new BigDecimal(defaultPrice));
								}

								orderprice.setSubject(sub.getSubjectname());
							}

							if (orderModel.OrderPriceList == null) {
								orderModel.OrderPriceList = new ArrayList<OrderPrice>();
							}
							orderModel.OrderPriceList.add(orderprice);

							CBookTimeInfo booktime = new CBookTimeInfo();
							booktime.setCoachid(CommonUtils.parseInt(coachid, 0));
							booktime.setDate(date1);
							booktime.setBookedtime(hour);

							List<CBookTimeInfo> booktimeList = (List<CBookTimeInfo>) dataDao.getFirstObjectViaParam(hql1, params1, CommonUtils.parseInt(coachid, 0), hour, date1);
							if (booktimeList == null || booktimeList.size() == 0) {
								if (orderModel.CBookTimeInfoList == null) {
									orderModel.CBookTimeInfoList = new ArrayList<CBookTimeInfo>();
								}
								orderModel.CBookTimeInfoList.add(booktime);
							}
						}
						orderList.add(orderModel);
					} else {
						hasError = true;
					}
				}
				//判断订单总价格，如果没有在50到500价格之间，返回预订失败
				if(total.doubleValue()>500 ||total.doubleValue()<50){
					result.put("code", 26);
					result.put("message", "订单额非法");
					return result;
				}
			}
			
			if (!hasError) {
				if (orderList.size() > 0) {
					
					for (int m = 0; m < orderList.size(); m++) {
						BigDecimal total = new BigDecimal(0);
						dataDao.addObject(orderList.get(m).mOrderInfo);
						// 查看订单的提醒设置
						if (orderNotiList != null && orderNotiList.size() > 0) {
							for (OrderNotiSetInfo setInfo1 : orderNotiList) {
								int minute = setInfo1.getBeforeminute();
								int type = setInfo1.getType();
								if (minute == 0)
									continue;
								Calendar start = Calendar.getInstance();
								start.setTime(orderList.get(m).mOrderInfo.getStart_time());
								start.add(Calendar.MINUTE, -minute);
								OrderNotiRecord record = new OrderNotiRecord();
								record.setAddtime(new Date());
								record.setBeforeminute(minute);
								record.setOrderid(orderList.get(m).mOrderInfo.getOrderid());
								record.setSendtime(start.getTime());
								record.setType(type);
								record.setCoachid(orderList.get(m).mOrderInfo.getCoachid());
								record.setStudentid(orderList.get(m).mOrderInfo.getStudentid());
								dataDao.addObject(record);
							}
						}

						if (successorderid == 0)
							successorderid = orderList.get(m).mOrderInfo.getOrderid();
						if (orderList.get(m).CBookTimeInfoList != null) {
							for (CBookTimeInfo booktime : orderList.get(m).CBookTimeInfoList) {
								dataDao.addObject(booktime);
							}
						}

						if (orderList.get(m).OrderPriceList != null) {
							for (OrderPrice mOrderPrice : orderList.get(m).OrderPriceList) {
								mOrderPrice.setOrderid(orderList.get(m).mOrderInfo.getOrderid());
								dataDao.addObject(mOrderPrice);
							}
						}
						total = total.add(orderList.get(m).mOrderInfo.getTotal());// 总价中增加订单的总价
						
						
						// 小巴券，判断，如果 2 
						if(PayType.COUPON==orderList.get(m).mOrderInfo.getPaytype()){
							
							total = total.subtract(new BigDecimal(orderList.get(m).mOrderInfo.getDelmoney()));// 减去小巴券中抵掉的金额
								if (orderList.get(m).mOrderInfo.getCouponrecordid() != null && orderList.get(m).mOrderInfo.getCouponrecordid().length() > 0) {
									String[] recordidArray = orderList.get(m).mOrderInfo.getCouponrecordid().split(",");
									for (int i = 0; i < recordidArray.length; i++) {
										int cid = CommonUtils.parseInt(recordidArray[i], 0);
										CouponRecord record = dataDao.getObjectById(CouponRecord.class, cid);
										if (record != null && record.getState()==0) {//未被使用
											record.setState(1);// 小巴券的状态修改为已经使用
											record.setUsetime(new Date());//使用时间
											record.setOrderid(orderList.get(m).mOrderInfo.getOrderid());//订单号
											dataDao.updateObject(record);
											if (record.getCoupontype() == 1) {// 时间
											} else {// 钱
												// 只需要修改为已经使用,
											}
										}
									}
								}
						}
						
						// 修改用户的余额，如果是paytype是1 余额  2 小巴卷 3 小巴币   

						if (student != null) {
							
							//  判断 1 或者 3  1 扣余额
							if(PayType.MONEY==orderList.get(m).mOrderInfo.getPaytype()){
								if(student.getMoney().subtract(total).doubleValue()<0){
									result.put("code", 4);
									result.put("message", "账户余额不足！");
									return result;
									
								}
								student.setFmoney(student.getFmoney().add(total));
								student.setMoney(student.getMoney().subtract(total));
								/*
								 * 学员下订单时，订单价格为M，此时学员的余额减M，冻结金额加M,教练的账户金额不变
									取消订单时，学员的冻结金额减去M,学员的余额加M,教练的账户金额不变
								 */
								if (student.getMoney().doubleValue() < 0 || student.getFmoney().doubleValue() < 0) {
									result.put("failtimes", failtimes);
									result.put("successorderid", successorderid);
									result.put("coachauth", student.getCoachstate());
									if (failtimes.length() == 0) {
										result.put("code", 15);
									} else {
										result.put("code", 2);
									}
									result.put("message", "账户余额不足！");
									return result;
								}
							}else if(PayType.COIN==orderList.get(m).mOrderInfo.getPaytype()){
								BigDecimal cnum = new BigDecimal(student.getCoinnum());
								double dc=cnum.subtract(total).doubleValue();
								//小巴币大于0，并且剩余小巴币余额减去支付额大于等于0，表示余额购，否则余额不足
								if(dc>=0){
									student.setCoinnum((int)dc); //学员小巴币数量减少
									student.setFcoinnum(student.getFcoinnum().add(total));
								}else{
									//不足
									result.put("code", 6);
									result.put("message", "小巴币余额不足!");
									return result;
								}
								//教练小巴币数量增加
								//int coachid=orderList.get(m).mOrderInfo.getCoachid();
								if(cuser!=null){
									//cuser.setCoinnum(cuser.getCoinnum()+total.intValue());
									//并且冻结教练订单总额的小巴币，直到双方互评后取消冻结，已防止在为评价前教练提现
									//cuser.setFcoinnum(cuser.getFcoinnum()+total.intValue());
									//dataDao.updateObject(cuser);
									//System.out.println("教练获取小巴币成功"+total.intValue());
								}else{
									//System.out.println("教练获取小巴币失败"+total.intValue());
								}
								//向小巴币记录表中插入数据 已修改，移动到结算方法中
								/////////////////////////////////////////////
								/* CoinRecordInfo coinRecordInfo = new CoinRecordInfo ();
							        coinRecordInfo.setReceiverid(cuser.getCoachid());
							        coinRecordInfo.setReceivertype(UserType.COAH);
							        coinRecordInfo.setReceivername(cuser.getRealname());
							        coinRecordInfo.setOwnerid(cuser.getCoachid());
							        coinRecordInfo.setPayerid(student.getStudentid());
							        coinRecordInfo.setPayertype(UserType.STUDENT);
							        coinRecordInfo.setPayername(student.getRealname());
							        coinRecordInfo.setType(CoinType.STUDENT_PAY);//学员支付
							        coinRecordInfo.setOwnertype(2);
							        coinRecordInfo.setCoinnum(total.intValue());
							        coinRecordInfo.setAddtime(new Date());
							        coinRecordInfo.setOrderid(orderList.get(m).mOrderInfo.getOrderid());//设置小巴币所属的订单的ID
							        dataDao.addObject(coinRecordInfo);*/
								///////////////////////////////////////////
							}
							dataDao.updateObject(student);
						}
					}
				

					// 推送通知教练有新的订单
					String hql5 = "from UserPushInfo where userid =:userid and usertype = 1";
					String params5[] = { "userid" };
					UserPushInfo userpush = (UserPushInfo) dataDao.getFirstObjectViaParam(hql5, params5, CommonUtils.parseInt(coachid, 0));
					if (userpush != null) {
						if (userpush.getType() == 0 && !CommonUtils.isEmptyString(userpush.getJpushid())) {// 安卓
							PushtoSingle pushsingle = new PushtoSingle();
							pushsingle.pushsingle(userpush.getJpushid(), 1, "{\"message\":\"" + "您有新的订单哦" + "\",\"type\":\"1\"}");
						} else if (userpush.getType() == 1 && !CommonUtils.isEmptyString(userpush.getDevicetoken())) {
							ApplePushUtil.sendpush(userpush.getDevicetoken(), "{\"aps\":{\"alert\":\"" + "您有新的订单哦" + "\",\"sound\":\"default\"},\"userid\":" + coachid + "}", 1, 1);
						}
					}
					
					// 增加学员与教练的关系
					String coachStudentHql = "from CoachStudentInfo where coachid = :coachid and studentid = :studentid";
					String[] params8 = { "coachid", "studentid" };
					CoachStudentInfo info = (CoachStudentInfo) dataDao.getFirstObjectViaParam(coachStudentHql, params8, CommonUtils.parseInt(coachid, 0), CommonUtils.parseInt(studentid, 0));
					if (info == null) {
						info = new CoachStudentInfo();
						info.setCoachid(CommonUtils.parseInt(coachid, 0));
						info.setHour(0);
						info.setMoney(new BigDecimal(0));
						info.setStudentid(CommonUtils.parseInt(studentid, 0));
						dataDao.addObject(info);
					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
			//版本需要更新
			result.put("failtimes", -1);
			result.put("successorderid", -1);
			result.put("coachauth", -1);
			result.put("message", "预约出错");
			result.put("code", -1);
			return result;
			
		}
		result.put("failtimes", failtimes);
		result.put("successorderid", successorderid);
		result.put("coachauth", student.getCoachstate());
		if (failtimes.length() == 0) {
			result.put("code", 1);
			result.put("message", "OK");
		} else {
			result.put("code", 2);
			result.put("message", "预约出错,预约失败的时间"+failtimes);
		}
		return result;
	}
	@Override
	public HashMap<String, Object> getCoachComments(String coachid,String type, String pagenum) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String hql="";
		if("1".equals(type)){ // type=1 过滤重复学员
			hql = "from EvaluationInfo  where to_user = :to_user and type = 1 group by from_user order by addtime desc";
		}else if("2".equals(type)){
			hql = "from EvaluationInfo  where to_user = :to_user and type = 1 order by addtime desc";
		}else{
			hql = "from EvaluationInfo  where to_user = :to_user and type = 1 group by from_user order by addtime desc";
		}
		
		String params[] = { "to_user" };

		List<EvaluationInfo> list = (List<EvaluationInfo>) dataDao.pageQueryViaParam(hql, 10, CommonUtils.parseInt(pagenum, 0) + 1, params, CommonUtils.parseInt(coachid, 0));
		List<EvaluationInfo> list2 = (List<EvaluationInfo>) dataDao.pageQueryViaParam(hql, 10, CommonUtils.parseInt(pagenum, 0) + 2, params, CommonUtils.parseInt(coachid,0));
		if(list2!=null && list2.size()>0){
			result.put("hasmore", 1);
		}else{
			result.put("hasmore", 0);
		}
		for (EvaluationInfo eva : list) {
			SuserInfo user = dataDao.getObjectById(SuserInfo.class, eva.getFrom_user());
			if (user != null) {
				if(user.getRealname()!=null && user.getRealname().length()==2){
					eva.setNickname(user.getRealname().substring(0,1)+"*");
				}else if(user.getRealname()!=null && user.getRealname().length()==3){
					eva.setNickname(user.getRealname().substring(0,1)+"**");
				}else if(user.getRealname()!=null && user.getRealname().length()>=4){
					eva.setNickname(user.getRealname().substring(0,2)+"**");
				}
				
				eva.setAvatarUrl(getFilePathById(user.getAvatar()));
			}
			eva.setScore((eva.getScore1() + eva.getScore2() + eva.getScore3()) / 3);
		}
		//评论的学员数量
		List<EvaluationInfo> listCount = (List<EvaluationInfo>) dataDao.getObjectsViaParam("from EvaluationInfo  where to_user = :to_user and type = 1 group by from_user order by addtime desc", params, CommonUtils.parseInt(coachid, 0));
		if (listCount != null)
			result.put("studentnum", listCount.size());
		else
			result.put("studentnum", 0);
		result.put("evalist", list);
		result.put("code", 1);
		result.put("message", "操作成功");
		//评论的总记录数
		List<EvaluationInfo> count = (List<EvaluationInfo>) dataDao.getObjectsViaParam("from EvaluationInfo  where to_user = :to_user and type = 1 order by addtime desc", params, CommonUtils.parseInt(coachid, 0));
		if (listCount != null)
			result.put("count", count.size());
		else
			result.put("count", 0);
		
		return result;
	}
	public HashMap<String, Object> getCommentsFromStudent(String coachid,String studentid, String pagenum) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String hql = "from EvaluationInfo where to_user = :to_user and type = 1 and from_user=:from_user order by addtime desc";
		String params[] = { "to_user","from_user" };

		List<EvaluationInfo> list = (List<EvaluationInfo>) 
				dataDao.pageQueryViaParam(hql, 20, CommonUtils.parseInt(pagenum, 0) + 1, params, CommonUtils.parseInt(coachid, 0),CommonUtils.parseInt(studentid,0));
		
		List<EvaluationInfo> list2 = (List<EvaluationInfo>) 
				dataDao.pageQueryViaParam(hql, 20, CommonUtils.parseInt(pagenum, 0) + 2, params, CommonUtils.parseInt(coachid, 0),CommonUtils.parseInt(studentid,0));
		if(list2!=null && list2.size()>0){
			result.put("hasmore", 1);
		}else{
			result.put("hasmore", 0);
		}
		for (EvaluationInfo eva : list) {
			SuserInfo user = dataDao.getObjectById(SuserInfo.class, eva.getFrom_user());
			if (user != null) {
				if(user.getRealname()!=null && user.getRealname().length()==2){
					eva.setNickname(user.getRealname().substring(0,1)+"*");
				}else if(user.getRealname()!=null && user.getRealname().length()==3){
					eva.setNickname(user.getRealname().substring(0,1)+"**");
				}else if(user.getRealname()!=null && user.getRealname().length()>=4){
					eva.setNickname(user.getRealname().substring(0,2)+"**");
				}
				eva.setAvatarUrl(getFilePathById(user.getAvatar()));
			}
			eva.setScore((eva.getScore1() + eva.getScore2() + eva.getScore3()) / 3);
		}
		List<EvaluationInfo> listCount = (List<EvaluationInfo>) dataDao.getObjectsViaParam(hql, params, CommonUtils.parseInt(coachid, 0),CommonUtils.parseInt(studentid,0));
		result.put("evalist", list);
		if (listCount != null)
			result.put("count", listCount.size());
		else
			result.put("count", 0);
		return result;
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

	@Override
	public List<CouponRecord> getHisCouponList(String studentid) {
		List<CouponRecord> result = new ArrayList<CouponRecord>();
		String hql = "from CouponRecord where userid =:userid and (end_time < :now or state = 1) order by gettime desc";
		String[] params = { "userid", "now" };
		result.addAll((List<CouponRecord>) dataDao.getObjectsViaParam(hql, params, CommonUtils.parseInt(studentid, 0), new Date()));

		for (CouponRecord record : result) {
			String title = "";
			if (record.getOwnertype() == 0) {
				title = "小巴通用券";
			} else if (record.getOwnertype() == 1) {
				DriveSchoolInfo school = dataDao.getObjectById(DriveSchoolInfo.class, record.getOwnerid());
				if (school != null && !CommonUtils.isEmptyString(school.getName())) {
					title = school.getName() + "驾校";
				}
			} else {
				CuserInfo user = dataDao.getObjectById(CuserInfo.class, record.getOwnerid());
				if (user != null && !CommonUtils.isEmptyString(user.getRealname())) {
					title = user.getRealname() + "教练";
				}
			}
			record.setTitle(title);
		}
		return result;
	}

	@Override
	public List<CouponRecord> getcouponList(String studentid) {
		List<CouponRecord> result = new ArrayList<CouponRecord>();
		String hql = "from CouponRecord where userid =:userid and end_time > :now and state = 0 order by gettime desc";
		String[] params = { "userid", "now" };
		result.addAll((List<CouponRecord>) dataDao.getObjectsViaParam(hql, params, CommonUtils.parseInt(studentid, 0), new Date()));
		for (CouponRecord record : result) {
			String title = "";
			if (record.getOwnertype() == 0) {
				title = "小巴通用券";
			} else if (record.getOwnertype() == 1) {
				DriveSchoolInfo school = dataDao.getObjectById(DriveSchoolInfo.class, record.getOwnerid());
				if (school != null && !CommonUtils.isEmptyString(school.getName())) {
					title = school.getName() + "驾校";
				}
			} else {
				CuserInfo user = dataDao.getObjectById(CuserInfo.class, record.getOwnerid());
				if (user != null && !CommonUtils.isEmptyString(user.getRealname())) {
					title = user.getRealname() + "教练";
				}
			}
			record.setTitle(title);
		}
		return result;
	}

	@Override
	public List<CouponRecord> getCanUseCouponList(String studentid, String coachid) {
		List<CouponRecord> result = new ArrayList<CouponRecord>();
		String hql = "from CouponRecord c where c.userid =:userid and c.end_time > now() and c.state = 0 and ";
		hql += "(c.ownertype = 0 or (c.ownertype = 2 and c.ownerid = :coachid) or c.ownertype = 1)";
		String[] params = { "userid", "coachid", "coachid" };
		result.addAll((List<CouponRecord>) dataDao.getObjectsViaParam(hql, params, CommonUtils.parseInt(studentid, 0), CommonUtils.parseInt(coachid, 0), CommonUtils.parseInt(coachid, 0)));

		Iterator<CouponRecord> iter = result.iterator();
		while (iter.hasNext()) {
			CouponRecord record = iter.next();
			if (record.getOwnertype() == 1) {// 驾校
				CuserInfo cuser = dataDao.getObjectById(CuserInfo.class, CommonUtils.parseInt(coachid, 0));
				if (cuser == null || cuser.getDrive_schoolid() == null || cuser.getDrive_schoolid() != record.getOwnerid()) {
					iter.remove();
				}
			}
		}
		for (CouponRecord record : result) {
			String title = "";
			if (record.getOwnertype() == 0) {
				title = "小巴通用券";
			} else if (record.getOwnertype() == 1) {
				DriveSchoolInfo school = dataDao.getObjectById(DriveSchoolInfo.class, record.getOwnerid());
				if (school != null && !CommonUtils.isEmptyString(school.getName())) {
					title = school.getName() + "驾校";
				}
			} else {
				CuserInfo user = dataDao.getObjectById(CuserInfo.class, record.getOwnerid());
				if (user != null && !CommonUtils.isEmptyString(user.getRealname())) {
					title = user.getRealname() + "教练";
				}
			}
			record.setTitle(title);
		}
		return result;
	}

	@Override
	public SystemSetInfo getSystemSetInfo() {
		String hql = "from SystemSetInfo where 1 = 1";
		return (SystemSetInfo) dataDao.getFirstObjectViaParam(hql, null);
	}
	/**
	 * 查询城市套餐价格
	 */
	@Override
	public List<ModelPrice> getModelPriceByCityId(int cityid) {
		String hql="from ModelPrice where cityid=:cityid";
		String p[]={"cityid"};
		List<ModelPrice> list=(List<ModelPrice>) dataDao.getObjectsViaParam(hql, p,cityid);
		return list;
	}
	@Override
	public List<ModelPrice> getOpenModelPrice() {
		String hql="from ModelPrice";
		List<ModelPrice> list=(List<ModelPrice>) dataDao.getObjectsViaParam(hql, null);
		/*for (ModelPrice modelPrice : list) {
			modelPrice.setC1marketprice(null);
			modelPrice.setC1xiaobaprice(null);
			modelPrice.setC2marketprice(null);
			modelPrice.setC2xiaobaprice(null);
			modelPrice.setId(null);
		}*/
		return list;
	}
	@Override
	public void addOpenModelPrice(ModelPrice mp) {
		dataDao.addObject(mp);
	}
	@Override
	public void deleteOpenModelPrice(String citysId)
	{
		dataDao.deleteBySql("delete from t_model_price where cityid in ("+citysId+")");
	}
	
	@Override
	@Transactional(readOnly = false)
	public void updateOpenModelPrice(ModelPrice mp)
	{
		dataDao.updateObject(mp);
	}
	@Override
	public ModelPrice getModelPriceById(int cityid) {
		return dataDao.getObjectById(ModelPrice.class, cityid);
	}
}
