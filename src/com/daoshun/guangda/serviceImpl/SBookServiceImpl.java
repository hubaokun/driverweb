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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.daoshun.common.ApplePushUtil;
import com.daoshun.common.CommonUtils;
import com.daoshun.common.Constant;
import com.daoshun.common.PushtoSingle;
import com.daoshun.guangda.pojo.CBookTimeInfo;
import com.daoshun.guangda.pojo.CaddAddressInfo;
import com.daoshun.guangda.pojo.CoachStudentInfo;
import com.daoshun.guangda.pojo.CouponRecord;
import com.daoshun.guangda.pojo.CscheduleInfo;
import com.daoshun.guangda.pojo.CsubjectInfo;
import com.daoshun.guangda.pojo.CuserInfo;
import com.daoshun.guangda.pojo.DefaultSchedule;
import com.daoshun.guangda.pojo.DriveSchoolInfo;
import com.daoshun.guangda.pojo.EvaluationInfo;
import com.daoshun.guangda.pojo.ModelsInfo;
import com.daoshun.guangda.pojo.OrderInfo;
import com.daoshun.guangda.pojo.OrderNotiRecord;
import com.daoshun.guangda.pojo.OrderNotiSetInfo;
import com.daoshun.guangda.pojo.OrderPrice;
import com.daoshun.guangda.pojo.RecommendInfo;
import com.daoshun.guangda.pojo.SuserInfo;
import com.daoshun.guangda.pojo.SystemSetInfo;
import com.daoshun.guangda.pojo.UserPushInfo;
import com.daoshun.guangda.service.ISBookService;

@Service("sbookService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class SBookServiceImpl extends BaseServiceImpl implements ISBookService {

	@Override
	public CuserInfo getCoachDetail(String coachid) {
		CuserInfo cuser = dataDao.getObjectById(CuserInfo.class, CommonUtils.parseInt(coachid, 0));
		if (cuser != null) {
			cuser.setAvatarurl(getFilePathById(cuser.getAvatar()));// 头像
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
				String hql3 = "from OrderInfo where studentid = :studentid and start_time <= :timeString and end_time > :timeString and studentstate <> 4";
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
					hqlCoach.append(" and getcoachstate(u.coachid," + datecount + ",'" + CommonUtils.getTimeFormat(start, "yyyy-MM-dd") + "'," + starthour + "," + 23 + "," + subjectid + ") = 1");

				}
			} else {
				int subjectid = CommonUtils.parseInt(condition6, 0);
				Calendar c = Calendar.getInstance();

				hqlCoach.append(" and getcoachstate(u.coachid," + 30 + ",'" + CommonUtils.getTimeFormat(c.getTime(), "yyyy-MM-dd") + "'," + 5 + "," + 23 + "," + subjectid + ") = 1");
			}*/
			if (!CommonUtils.isEmptyString(condition3)) {
				//int subjectid = CommonUtils.parseInt(condition6, 0);
				Date start = CommonUtils.getDateFormat(condition3, "yyyy-MM-dd");
				if (start != null) {
					cuserhql.append(" and (select count(*) from t_coach_schedule where  t_coach_schedule.coachid =  ");
					cuserhql.append(" u.coachid and t_coach_schedule.date = '");
					cuserhql.append(start).append("'");
					cuserhql.append(" and t_coach_schedule.hour = 0 and t_coach_schedule.state = 1)>0");
				}
			} else {
				/*int subjectid = CommonUtils.parseInt(condition6, 0);
				Calendar c = Calendar.getInstance();*/
				cuserhql.append(" and (select count(*) from t_coach_schedule where  t_coach_schedule.coachid =  ");
				cuserhql.append(" u.coachid and t_coach_schedule.date  <=date_sub(now(),interval -30 day) and ");
				cuserhql.append(" t_coach_schedule.hour = 0 and t_coach_schedule.state = 1)>0  ");
				//cuserhql.append(" and getcoachstate(u.coachid," + 30 + ",'" + CommonUtils.getTimeFormat(c.getTime(), "yyyy-MM-dd") + "'," + 5 + "," + 23 + "," + subjectid + ") = 1");
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
				if(info.getCoachid()==157)
					System.out.println(info.getCoachid());
			}
			List<CuserInfo> cuserlist = (List<CuserInfo>) dataDao.getObjectsViaParam(hqlCoach.toString(), paramsCoach, cids, now, now, now, now, now);
			if (cuserlist != null && cuserlist.size() > 0) {
				for (CuserInfo cuser : cuserlist) {
					if (cuser.getMoney().doubleValue() > cuser.getGmoney().doubleValue()) {
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
			//int subjectid = CommonUtils.parseInt(condition6, 0);
			Date start = CommonUtils.getDateFormat(condition3, "yyyy-MM-dd");
			if (start != null) {
				cuserhql.append(" and (select count(*) from t_coach_schedule where  t_coach_schedule.coachid =  ");
				cuserhql.append(" u.coachid and t_coach_schedule.date = '");
				cuserhql.append(start).append("'");
				cuserhql.append(" and t_coach_schedule.hour = 0 and t_coach_schedule.state = 1)>0");
			}
		} else {
			/*int subjectid = CommonUtils.parseInt(condition6, 0);
			Calendar c = Calendar.getInstance();*/
			cuserhql.append(" and (select count(*) from t_coach_schedule where  t_coach_schedule.coachid =  ");
			cuserhql.append(" u.coachid and t_coach_schedule.date  <=date_sub(now(),interval -30 day) and ");
			cuserhql.append(" t_coach_schedule.hour = 0 and t_coach_schedule.state = 1)>0");
			//cuserhql.append(" and getcoachstate(u.coachid," + 30 + ",'" + CommonUtils.getTimeFormat(c.getTime(), "yyyy-MM-dd") + "'," + 5 + "," + 23 + "," + subjectid + ") = 1");
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
		if(student.getMoney().doubleValue()<0.0)// 余额不够
		{
			//版本需要更新
			result.put("failtimes",11);
			result.put("successorderid", 11);
			result.put("coachauth", 11);
			result.put("message", "您当前处于欠费状态,无法生成订单!");
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
						// 小巴券
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
					// 修改用户的余额

					if (student != null) {
						student.setFmoney(student.getFmoney().add(total));
						student.setMoney(student.getMoney().subtract(total));

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

	@Override
	public HashMap<String, Object> getCoachComments(String coachid, String pagenum) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String hql = "from EvaluationInfo where to_user = :to_user and type = 1 order by addtime desc";
		String params[] = { "to_user" };

		List<EvaluationInfo> list = (List<EvaluationInfo>) dataDao.pageQueryViaParam(hql, 20, CommonUtils.parseInt(pagenum, 0) + 1, params, CommonUtils.parseInt(coachid, 0));

		for (EvaluationInfo eva : list) {
			SuserInfo user = dataDao.getObjectById(SuserInfo.class, eva.getFrom_user());
			if (user != null) {
				eva.setNickname(user.getRealname());
				eva.setAvatarUrl(getFilePathById(user.getAvatar()));
			}
			eva.setScore((eva.getScore1() + eva.getScore2() + eva.getScore3()) / 3);
		}
		List<EvaluationInfo> listCount = (List<EvaluationInfo>) dataDao.getObjectsViaParam(hql, params, CommonUtils.parseInt(coachid, 0));
		result.put("evalist", list);
		result.put("code", 1);
		result.put("message", "操作成功");
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
		String hql = "from CouponRecord c where c.userid =:userid and c.end_time > :now and c.state = 0 and ";
		hql += "(c.ownertype = 0 or (c.ownertype = 2 and c.ownerid = :coachid) or c.ownertype = 1)";
		String[] params = { "userid", "now", "coachid", "coachid" };
		result.addAll((List<CouponRecord>) dataDao.getObjectsViaParam(hql, params, CommonUtils.parseInt(studentid, 0), new Date(), CommonUtils.parseInt(coachid, 0), CommonUtils.parseInt(coachid, 0)));

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
}
