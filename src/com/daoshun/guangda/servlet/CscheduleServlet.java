package com.daoshun.guangda.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.daoshun.common.CommonUtils;
import com.daoshun.common.Constant;
import com.daoshun.common.CscheduleCompare;
import com.daoshun.common.ErrException;
import com.daoshun.guangda.pojo.CBookTimeInfo;
import com.daoshun.guangda.pojo.CaddAddressInfo;
import com.daoshun.guangda.pojo.CscheduleInfo;
import com.daoshun.guangda.pojo.CsubjectInfo;
import com.daoshun.guangda.pojo.CuserInfo;
import com.daoshun.guangda.pojo.SystemSetInfo;
import com.daoshun.guangda.service.ICUserService;
import com.daoshun.guangda.service.ICscheduleService;
import com.daoshun.guangda.service.ISystemService;

/**
 * @author liukn
 * 
 */
@WebServlet("/cschedule")
public class CscheduleServlet extends BaseServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7439257638135996535L;

	private ICscheduleService cscheduleService;
	private ICUserService cuserService;
	private ISystemService systemService;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		cscheduleService = (ICscheduleService) applicationContext.getBean("cscheduleService");
		cuserService = (ICUserService) applicationContext.getBean("cuserService");
		systemService = (ISystemService) applicationContext.getBean("systemService");
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String action = getAction(request);

			if (Constant.CGETSCHEDULE.equals(action) || Constant.CSETDATETIME.equals(action) || Constant.CCHANGEALLDAYSCHEDULE.equals(action) || Constant.CCHANGEORDERCANCEL.equals(action)
					|| Constant.SETDEFAULT.equals(action)) {
				if (!checkSession(request, action, resultMap)) {
					setResult(response, resultMap);
					return;
				}
			}

			if (Constant.CGETSCHEDULE.equals(action)) {
				// 获取教练的日程安排
				getSchedule2(request, resultMap);
			}else if (Constant.CGETSCHEDULEBYDATE.equals(action)) {
				// 根据日期获取教练的日程安排
				getScheduleByDate(request, resultMap);
			} else if (Constant.CSETDATETIME.equals(action)) {
				// 教练设置某天的休息时间（30天内）
				setDateTime(request, resultMap);
			} else if (Constant.CCHANGEALLDAYSCHEDULE.equals(action)) {
				// 教练改变某一天的全天时间（30天内）
				changeAllDaySchedule(request, resultMap);
			} else if (Constant.CCHANGEORDERCANCEL.equals(action)) {
				changeOrderCancel(request, resultMap);
			} else if (Constant.SETDEFAULT.equals(action)) {
				setDefault(request, resultMap);
			} else {
				throw new ErrException();
			}

			recordUserAction(request, action);
		} catch (Exception e) {
			e.printStackTrace();
			setResultWhenException(response, e.getMessage());
		}
		setResult(response, resultMap);
	}

	private boolean checkSession(HttpServletRequest request, String action, HashMap<String, Object> resultMap) throws ErrException {
		String userid = "";// 1.教练 2.学员
		String usertype = "";

		if (Constant.CGETSCHEDULE.equals(action)) {
			// 获取教练的日程安排
			userid = getRequestParamter(request, "coachid");
			usertype = "1";
		} else if (Constant.CSETDATETIME.equals(action)) {
			// 教练设置某天的休息时间（30天内）
			userid = getRequestParamter(request, "coachid");
			usertype = "1";
		} else if (Constant.CCHANGEALLDAYSCHEDULE.equals(action)) {
			// 教练改变某一天的全天时间（30天内）
			userid = getRequestParamter(request, "coachid");
			usertype = "1";
		} else if (Constant.CCHANGEORDERCANCEL.equals(action)) {
			userid = getRequestParamter(request, "coachid");
			usertype = "1";
		} else if (Constant.SETDEFAULT.equals(action)) {
			userid = getRequestParamter(request, "coachid");
			usertype = "1";
		}

		if (!CommonUtils.isEmptyString(userid) && !CommonUtils.isEmptyString(usertype)) {

			CuserInfo cuser = cuserService.getCoachByid(CommonUtils.parseInt(userid, 0));
			if (cuser != null) {
				String token = getRequestParamter(request, "token");
				if (!CommonUtils.isEmptyString(token)) {
					// 时间获取
					if (token.equals(cuser.getToken())) {
						int login_vcode_time = 15;// 默认十五天
						SystemSetInfo systemSet = systemService.getSystemSet();
						if (systemSet != null && systemSet.getLogin_vcode_time() != null && systemSet.getLogin_vcode_time() != 0) {
							login_vcode_time = systemSet.getLogin_vcode_time();
						}

						Calendar now = Calendar.getInstance();
						Calendar tokenTime = Calendar.getInstance();
						tokenTime.setTime(cuser.getToken_time());
						tokenTime.add(Calendar.DAY_OF_YEAR, login_vcode_time);
						if (now.after(tokenTime)) {
							resultMap.put(Constant.CODE, 95);
							resultMap.put(Constant.MESSAGE, "您的登录信息已经过期,请重新登录.");
							return false;
						} else {
							return true;
						}
					} else {
						resultMap.put(Constant.CODE, 95);
						resultMap.put(Constant.MESSAGE, "您的登录信息已经过期,请重新登录.");
						return false;
					}
				} else {
					return true;
				}
			} else {
				resultMap.put(Constant.CODE, 99);
				resultMap.put(Constant.MESSAGE, "用户参数错误");
				return false;
			}

		} else {
			resultMap.put(Constant.CODE, 99);
			resultMap.put(Constant.MESSAGE, "用户参数错误");
			return false;
		}
	}

	public void recordUserAction(HttpServletRequest request, String action) {
		String userid = "";// 1.教练 2.学员
		String usertype = "";

		if (Constant.CGETSCHEDULE.equals(action)) {
			// 获取教练的日程安排
			userid = getRequestParamter(request, "coachid");
			usertype = "1";
		} else if (Constant.CSETDATETIME.equals(action)) {
			// 教练设置某天的休息时间（30天内）
			userid = getRequestParamter(request, "coachid");
			usertype = "1";
		} else if (Constant.CCHANGEALLDAYSCHEDULE.equals(action)) {
			// 教练改变某一天的全天时间（30天内）
			userid = getRequestParamter(request, "coachid");
			usertype = "1";
		} else if (Constant.CCHANGEORDERCANCEL.equals(action)) {
			userid = getRequestParamter(request, "coachid");
			usertype = "1";
		} else if (Constant.SETDEFAULT.equals(action)) {
			userid = getRequestParamter(request, "coachid");
			usertype = "1";
		}

		if (!CommonUtils.isEmptyString(userid) && !CommonUtils.isEmptyString(usertype)) {
			systemService.recordUserAction(userid, usertype, "cschedule", action);
		}
	}

	/**
	 * 获取教练的日程安排
	 * 
	 * @param request
	 * @throws ErrException
	 */
	public void getSchedule(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		long start=System.currentTimeMillis();
		// 获取参数教练ID
		String coachid = getRequestParamter(request, "coachid");
		CommonUtils.validateEmpty(coachid);
		// 找到该教练
		CuserInfo cuser = cuserService.getCuserByCoachid(coachid);
		// 找到该教练的当前使用地址
		CaddAddressInfo addressInfo = cuserService.getcoachaddress(coachid);
		// 查询出系统的默认科目以及默认价格设置
		int defaultPrice = 100;
		int defaultSubjectID = 1;
		SystemSetInfo set = cuserService.getSystemSetInfo();
		if (set != null) {
			if (set.getCoach_default_price() != null && set.getCoach_default_price() != 0) {
				defaultPrice = set.getCoach_default_price();
			}

			if (set.getCoach_default_subject() != null && set.getCoach_default_subject() != 0) {
				defaultSubjectID = set.getCoach_default_subject();
			}
		}
		
		CsubjectInfo subjectInfo = cuserService.getsubjectBysubjectid(defaultSubjectID);
		if (subjectInfo == null) {
			subjectInfo = cuserService.getFirstSubject();
		}
		if (cuser != null && addressInfo != null) {
			// 查询出默认的日期天使设置
			int maxDays = 30;// 默认设置30天
			SystemSetInfo systemSet = cuserService.getSystemSetInfo();
			if (systemSet != null && systemSet.getBook_day_max() != null && systemSet.getBook_day_max() != 0) {
				maxDays = systemSet.getBook_day_max();
			}
			Calendar now = Calendar.getInstance();
			//maxDays=5;
			List<CscheduleInfo> schedulelist = new ArrayList<CscheduleInfo>();
			// 日期循环
			for (int i = 0; i < maxDays; i++) {
				// 取得日期
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, i);
				Date time = c.getTime();
				String newnow = CommonUtils.getTimeFormat(time, "yyyy-MM-dd");
				// 取得DB中当天的所有日程设置
				List<CscheduleInfo> dayist = cscheduleService.getCscheduleInfoByDatelist(newnow, coachid);
				CscheduleInfo allDaySet = null;
				// 查询是否有全天开课或者全天停课设置
				if (dayist != null) {
					for (CscheduleInfo cscheduleInfo : dayist) {
						if ("0".equals(cscheduleInfo.getHour())) {
							allDaySet = cscheduleInfo;
							break;
						}
					}
				}

				if (allDaySet == null) {// 生成默认的全天设置
					allDaySet = new CscheduleInfo();
					allDaySet.setCoachid(CommonUtils.parseInt(coachid, 0));
					allDaySet.setDate(newnow);
					allDaySet.setHour("0");
					allDaySet.setState(0);
					allDaySet.setCancelstate(cscheduleService.getDefaultCancelBydate(newnow));
				}
				schedulelist.add(allDaySet);
				//System.out.println("耗时2："+(end2-start));
				// 时间点循环
				for (int k = 5; k < 24; k++) {
					CscheduleInfo info = null;
					// 先寻找数据库中是否有该时间点的设置
					for (CscheduleInfo cscheduleInfo : dayist) {
						if (String.valueOf(k).equals(cscheduleInfo.getHour())) {
							info = cscheduleInfo;
							break;
						}
					}
					// 如果在数据库中找到了设置
					if (info != null) {
						// 查询地址信息
						CaddAddressInfo address = cuserService.getaddress(info.getAddressid());
						if (address != null) {
							info.setAddressdetail(address.getDetail());
						} else {// 找不到地址的话
							address = cscheduleService.getDefaultAddress(coachid, String.valueOf(k));// 是否有默认地址设置
							if (address != null) {
								info.setAddressdetail(address.getDetail());
							} else {// 设置为教练默认地址
								info.setAddressdetail(addressInfo.getDetail());
							}
						}

						// 科目信息
						CsubjectInfo subject = cuserService.getSubjectById(info.getSubjectid());
						if (subject != null) {
							info.setSubject(subject.getSubjectname());
						} else {
							subject = cscheduleService.getDefaultSubject(coachid, String.valueOf(k));
							if (subject != null) {
								info.setSubject(subject.getSubjectname());
							} else {
								info.setSubject(subjectInfo.getSubjectname());
							}
						}
						// 是否已经被预订
						CBookTimeInfo cbooktime = cscheduleService.getcoachbooktime(String.valueOf(k), CommonUtils.parseInt(coachid, 0), newnow);
						if (cbooktime == null) {
							info.setHasbooked(0);
						} else {
							info.setHasbooked(1);
						}

						schedulelist.add(info);
					} else {// 数据库中没有时间点的设置
						info = new CscheduleInfo();
						info.setCoachid(CommonUtils.parseInt(coachid, 0));
						info.setHour(String.valueOf(k));
						info.setDate(newnow);
						// 默认价格
						BigDecimal price = cscheduleService.getDefaultPrice(coachid, String.valueOf(k));
						if (price != null && price.doubleValue() != 0) {
							info.setPrice(price);
						} else {// 设置为系统的默认价格
							info.setPrice(new BigDecimal(defaultPrice));
						}
						// 默认的开课休息状态
						int rest = cscheduleService.getDefaultRest(coachid, String.valueOf(k));
						if (rest != -1) {
							info.setIsrest(rest);
						} else {// 5、6、12、18设置为休息
							if (k == 12 || k == 18 || k == 5 || k == 6) {
								info.setIsrest(1);
							} else {
								info.setIsrest(0);
							}
						}
						// 地址信息
						CaddAddressInfo address = cscheduleService.getDefaultAddress(coachid, String.valueOf(k));
						if (address != null) {
							info.setAddressid(address.getAddressid());
							info.setAddressdetail(address.getDetail());
						} else {
							info.setAddressid(addressInfo.getAddressid());
							info.setAddressdetail(addressInfo.getDetail());
						}
						// 科目信息
						CsubjectInfo subject = cscheduleService.getDefaultSubject(coachid, String.valueOf(k));
						if (subject != null) {
							info.setSubjectid(subject.getSubjectid());
							info.setSubject(subject.getSubjectname());
						} else {
							info.setSubjectid(subjectInfo.getSubjectid());
							info.setSubject(subjectInfo.getSubjectname());
						}
						// 是否被预定
						CBookTimeInfo cbooktime = cscheduleService.getcoachbooktime(String.valueOf(k), CommonUtils.parseInt(coachid, 0), newnow);
						if (cbooktime == null) {
							info.setHasbooked(0);
						} else {
							info.setHasbooked(1);
						}
						schedulelist.add(info);
					}
					
				}
			}

			// 获得最新的list
			Collections.sort(schedulelist, new CscheduleCompare());

			// CuserInfo newcuser = cuserService.getCuserByCoachid(coachid);
			// if (newcuser.getCancancel() == 0) {
			// resultMap.put("cancelpermission", 0);
			// } else {
			resultMap.put("cancelpermission", 1);// 设置是否具有设置订单可以取消的权限
			// }

			resultMap.put("maxdays", maxDays);
			resultMap.put("datelist", schedulelist);
			resultMap.put("today", CommonUtils.getTimeFormat(now.getTime(), "yyyy-MM-dd"));// 返回服务器的日期
			// 当前小时
			int hour = now.get(Calendar.HOUR_OF_DAY);
			resultMap.put("hour", hour);
		} else {
			resultMap.put("code", 5);
			resultMap.put("message", "请先设置默认教学地址");
		}
		long end=System.currentTimeMillis();
		System.out.println("getSchedule总耗时："+(end-start));
	}
	/**
	 * 获取教练的日程安排2
	 * 
	 * @param request
	 * @throws ErrException
	 */
	public void getSchedule2(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		long start=System.currentTimeMillis();
		// 获取参数教练ID
		String coachid = getRequestParamter(request, "coachid");
		CommonUtils.validateEmpty(coachid);
		// 找到该教练
		CuserInfo cuser = cuserService.getCuserByCoachid(coachid);
		// 找到该教练的当前使用地址
		CaddAddressInfo addressInfo = cuserService.getcoachaddress(coachid);
		// 查询出系统的默认科目以及默认价格设置
		int defaultPrice = 100;
		int defaultSubjectID = 1;
		SystemSetInfo set = cuserService.getSystemSetInfo();
		if (set != null) {
			if (set.getCoach_default_price() != null && set.getCoach_default_price() != 0) {
				defaultPrice = set.getCoach_default_price();
			}

			if (set.getCoach_default_subject() != null && set.getCoach_default_subject() != 0) {
				defaultSubjectID = set.getCoach_default_subject();
			}
		}
		
		CsubjectInfo subjectInfo = cuserService.getsubjectBysubjectid(defaultSubjectID);
		if (subjectInfo == null) {
			subjectInfo = cuserService.getFirstSubject();
		}
		if (cuser != null && addressInfo != null) {
			// 查询出默认的日期天使设置
			int maxDays = 30;// 默认设置30天
			//SystemSetInfo systemSet = cuserService.getSystemSetInfo();
			if (set != null && set.getBook_day_max() != null && set.getBook_day_max() != 0) {
				maxDays = set.getBook_day_max();
			}
			Calendar now = Calendar.getInstance();
			//maxDays=5;
			List<CscheduleInfo> schedulelist = new ArrayList<CscheduleInfo>();
			// 日期循环
			for (int i = 0; i < maxDays; i++) {
				// 取得日期
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, i);
				Date time = c.getTime();
				String newnow = CommonUtils.getTimeFormat(time, "yyyy-MM-dd");
				// 取得DB中当天的所有日程设置
				List<CscheduleInfo> dayist = cscheduleService.getCscheduleInfoByDatelist(newnow, coachid);
				CscheduleInfo allDaySet = null;
				// 查询是否有全天开课或者全天停课设置
				if (dayist != null) {
					for (CscheduleInfo cscheduleInfo : dayist) {
						if ("0".equals(cscheduleInfo.getHour())) {
							allDaySet = cscheduleInfo;
							break;
						}
					}
				}

				if (allDaySet == null) {// 生成默认的全天设置
					allDaySet = new CscheduleInfo();
					allDaySet.setCoachid(CommonUtils.parseInt(coachid, 0));
					allDaySet.setDate(newnow);
					allDaySet.setHour("0");
					allDaySet.setState(0);
					allDaySet.setCancelstate(cscheduleService.getDefaultCancelBydate(newnow));
				}
				schedulelist.add(allDaySet);
				//System.out.println("耗时2："+(end2-start));
				// 时间点循环
				for (int k = 5; k < 24; k++) {
					CscheduleInfo info = null;
					
					// 先寻找数据库中是否有该时间点的设置
					for (CscheduleInfo cscheduleInfo : dayist) {
						if (String.valueOf(k).equals(cscheduleInfo.getHour())) {
							info = cscheduleInfo;
							break;
						}
					}
					
					// 如果在数据库中找到了设置
					if (info != null) {
						// 查询地址信息
						CaddAddressInfo address = cuserService.getaddress(info.getAddressid());
						if (address != null) {
							info.setAddressdetail(address.getDetail());
						} else {// 找不到地址的话
							address = cscheduleService.getDefaultAddress(coachid, String.valueOf(k));// 是否有默认地址设置
							if (address != null) {
								info.setAddressdetail(address.getDetail());
							} else {// 设置为教练默认地址
								info.setAddressdetail(addressInfo.getDetail());
							}
						}
						// 科目信息
						CsubjectInfo subject = cuserService.getSubjectById(info.getSubjectid());
						if (subject != null) {
							info.setSubject(subject.getSubjectname());
						} else {
							subject = cscheduleService.getDefaultSubject(coachid, String.valueOf(k));
							if (subject != null) {
								info.setSubject(subject.getSubjectname());
							} else {
								info.setSubject(subjectInfo.getSubjectname());
							}
						}
						// 是否已经被预订
						/*CBookTimeInfo cbooktime = cscheduleService.getcoachbooktime(String.valueOf(k), CommonUtils.parseInt(coachid, 0), newnow);
						if (cbooktime == null) {
							info.setHasbooked(0);
						} else {
							info.setHasbooked(1);
						}*/
						boolean isbooked = cscheduleService.getIsbookedBybooktime(String.valueOf(k), CommonUtils.parseInt(coachid, 0), newnow);
						if (isbooked) {
							info.setHasbooked(0);
						} else {
							info.setHasbooked(1);
						}
						schedulelist.add(info);
					} else {// 数据库中没有时间点的设置
						info = new CscheduleInfo();
						info.setCoachid(CommonUtils.parseInt(coachid, 0));
						info.setHour(String.valueOf(k));
						info.setDate(newnow);
						// 默认价格
						BigDecimal price = cscheduleService.getDefaultPrice(coachid, String.valueOf(k));
						if (price != null && price.doubleValue() != 0) {
							info.setPrice(price);
						} else {// 设置为系统的默认价格
							info.setPrice(new BigDecimal(defaultPrice));
						}
						// 默认的开课休息状态
						int rest = cscheduleService.getDefaultRest(coachid, String.valueOf(k));
						if (rest != -1) {
							info.setIsrest(rest);
						} else {// 5、6、12、18设置为休息
							if (k == 12 || k == 18 || k == 5 || k == 6) {
								info.setIsrest(1);
							} else {
								info.setIsrest(0);
							}
						}
						
						// 地址信息
						CaddAddressInfo address = cscheduleService.getDefaultAddress(coachid, String.valueOf(k));
						if (address != null) {
							info.setAddressid(address.getAddressid());
							info.setAddressdetail(address.getDetail());
						} else {
							info.setAddressid(addressInfo.getAddressid());
							info.setAddressdetail(addressInfo.getDetail());
						}
					
						
						// 科目信息
						CsubjectInfo subject = cscheduleService.getDefaultSubject(coachid, String.valueOf(k));
						if (subject != null) {
							info.setSubjectid(subject.getSubjectid());
							info.setSubject(subject.getSubjectname());
						} else {
							info.setSubjectid(subjectInfo.getSubjectid());
							info.setSubject(subjectInfo.getSubjectname());
						}
						///long start1=System.currentTimeMillis();
						// 是否被预定
						boolean isbooked = cscheduleService.getIsbookedBybooktime(String.valueOf(k), CommonUtils.parseInt(coachid, 0), newnow);
						if (isbooked) {
							info.setHasbooked(0);
						} else {
							info.setHasbooked(1);
						}
						/*CBookTimeInfo cbooktime = cscheduleService.getcoachbooktime(String.valueOf(k), CommonUtils.parseInt(coachid, 0), newnow);
						if (cbooktime == null) {
							info.setHasbooked(0);
						} else {
							info.setHasbooked(1);
						}*/
						//long start2=System.currentTimeMillis();
						//System.out.println("@@@@@#"+(start2-start1));
						schedulelist.add(info);
					}
					
				}
			}

			// 获得最新的list
			Collections.sort(schedulelist, new CscheduleCompare());

			// CuserInfo newcuser = cuserService.getCuserByCoachid(coachid);
			// if (newcuser.getCancancel() == 0) {
			// resultMap.put("cancelpermission", 0);
			// } else {
			resultMap.put("cancelpermission", 1);// 设置是否具有设置订单可以取消的权限
			// }

			resultMap.put("maxdays", maxDays);
			resultMap.put("datelist", schedulelist);
			resultMap.put("today", CommonUtils.getTimeFormat(now.getTime(), "yyyy-MM-dd"));// 返回服务器的日期
			// 当前小时
			int hour = now.get(Calendar.HOUR_OF_DAY);
			resultMap.put("hour", hour);
		} else {
			resultMap.put("code", 5);
			resultMap.put("message", "请先设置默认教学地址");
		}
		long end=System.currentTimeMillis();
		//System.out.println("getSchedule总耗时："+(end-start));
	}
	
	/**
	 * 获取教练今天的日程安排
	 * 
	 * @param request
	 * @throws ErrException
	 */
	public void getScheduleByToday(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		//long start=System.currentTimeMillis();
		// 获取参数教练ID
		String coachid = getRequestParamter(request, "coachid");
		CommonUtils.validateEmpty(coachid);
		// 找到该教练
		CuserInfo cuser = cuserService.getCuserByCoachid(coachid);
		// 找到该教练的当前使用地址
		CaddAddressInfo addressInfo = cuserService.getcoachaddress(coachid);
		// 查询出系统的默认科目以及默认价格设置
		int defaultPrice = 100;
		int defaultSubjectID = 1;
		SystemSetInfo set = cuserService.getSystemSetInfo();
		if (set != null) {
			if (set.getCoach_default_price() != null && set.getCoach_default_price() != 0) {
				defaultPrice = set.getCoach_default_price();
			}

			if (set.getCoach_default_subject() != null && set.getCoach_default_subject() != 0) {
				defaultSubjectID = set.getCoach_default_subject();
			}
		}
		
		CsubjectInfo subjectInfo = cuserService.getsubjectBysubjectid(defaultSubjectID);
		if (subjectInfo == null) {
			subjectInfo = cuserService.getFirstSubject();
		}
		if (cuser != null && addressInfo != null) {
			// 查询出默认的日期天使设置
			int maxDays = 30;// 默认设置30天
			SystemSetInfo systemSet = cuserService.getSystemSetInfo();
			if (systemSet != null && systemSet.getBook_day_max() != null && systemSet.getBook_day_max() != 0) {
				maxDays = systemSet.getBook_day_max();
			}
			Calendar now = Calendar.getInstance();
			//今天的年月日字符串
			//String todayStr=CommonUtils.getTimeFormat(now.getTime(), "yyyy-MM-dd");
			List<CscheduleInfo> schedulelist = new ArrayList<CscheduleInfo>();
			// 日期循环
			for (int i = 0; i < maxDays; i++) {
				// 取得日期
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, i);
				Date time = c.getTime();
				String newnow = CommonUtils.getTimeFormat(time, "yyyy-MM-dd");
				// 取得DB中当天的所有日程设置
				List<CscheduleInfo> dayist = cscheduleService.getCscheduleInfoByDatelist(newnow, coachid);
				CscheduleInfo allDaySet = null;
				// 查询是否有全天开课或者全天停课设置
				if (dayist != null) {
					for (CscheduleInfo cscheduleInfo : dayist) {
						if ("0".equals(cscheduleInfo.getHour())) {
							allDaySet = cscheduleInfo;
							break;
						}
					}
				}

				if (allDaySet == null) {// 生成默认的全天设置
					allDaySet = new CscheduleInfo();
					allDaySet.setCoachid(CommonUtils.parseInt(coachid, 0));
					allDaySet.setDate(newnow);
					allDaySet.setHour("0");
					allDaySet.setState(0);
					allDaySet.setIsrest(0);
					allDaySet.setCancelstate(cscheduleService.getDefaultCancelBydate(newnow));
				}
				schedulelist.add(allDaySet);
				// 时间点循环
				if(i==0){
						for (int k = 5; k < 24; k++) {
							CscheduleInfo info = null;
							// 先寻找数据库中是否有该时间点的设置
							for (CscheduleInfo cscheduleInfo : dayist) {
								if (String.valueOf(k).equals(cscheduleInfo.getHour())) {
									info = cscheduleInfo;
									break;
								}
							}
							// 如果在数据库中找到了设置
							if (info != null) {
								// 查询地址信息
								CaddAddressInfo address = cuserService.getaddress(info.getAddressid());
								if (address != null) {
									info.setAddressdetail(address.getDetail());
								} else {// 找不到地址的话
									address = cscheduleService.getDefaultAddress(coachid, String.valueOf(k));// 是否有默认地址设置
									if (address != null) {
										info.setAddressdetail(address.getDetail());
									} else {// 设置为教练默认地址
										info.setAddressdetail(addressInfo.getDetail());
									}
								}
		
								// 科目信息
								CsubjectInfo subject = cuserService.getSubjectById(info.getSubjectid());
								if (subject != null) {
									info.setSubject(subject.getSubjectname());
								} else {
									subject = cscheduleService.getDefaultSubject(coachid, String.valueOf(k));
									if (subject != null) {
										info.setSubject(subject.getSubjectname());
									} else {
										info.setSubject(subjectInfo.getSubjectname());
									}
								}
								// 是否已经被预订
								CBookTimeInfo cbooktime = cscheduleService.getcoachbooktime(String.valueOf(k), CommonUtils.parseInt(coachid, 0), newnow);
								if (cbooktime == null) {
									info.setHasbooked(0);
								} else {
									info.setHasbooked(1);
								}
		
								schedulelist.add(info);
							} else {// 数据库中没有时间点的设置
								info = new CscheduleInfo();
								info.setCoachid(CommonUtils.parseInt(coachid, 0));
								info.setHour(String.valueOf(k));
								info.setDate(newnow);
								// 默认价格
								BigDecimal price = cscheduleService.getDefaultPrice(coachid, String.valueOf(k));
								if (price != null && price.doubleValue() != 0) {
									info.setPrice(price);
								} else {// 设置为系统的默认价格
									info.setPrice(new BigDecimal(defaultPrice));
								}
								// 默认的开课休息状态
								int rest = cscheduleService.getDefaultRest(coachid, String.valueOf(k));
								if (rest != -1) {
									info.setIsrest(rest);
								} else {// 5、6、12、18设置为休息
									if (k == 12 || k == 18 || k == 5 || k == 6) {
										info.setIsrest(1);
									} else {
										info.setIsrest(0);
									}
								}
								// 地址信息
								CaddAddressInfo address = cscheduleService.getDefaultAddress(coachid, String.valueOf(k));
								if (address != null) {
									info.setAddressid(address.getAddressid());
									info.setAddressdetail(address.getDetail());
								} else {
									info.setAddressid(addressInfo.getAddressid());
									info.setAddressdetail(addressInfo.getDetail());
								}
								// 科目信息
								CsubjectInfo subject = cscheduleService.getDefaultSubject(coachid, String.valueOf(k));
								if (subject != null) {
									info.setSubjectid(subject.getSubjectid());
									info.setSubject(subject.getSubjectname());
								} else {
									info.setSubjectid(subjectInfo.getSubjectid());
									info.setSubject(subjectInfo.getSubjectname());
								}
								// 是否被预定
								CBookTimeInfo cbooktime = cscheduleService.getcoachbooktime(String.valueOf(k), CommonUtils.parseInt(coachid, 0), newnow);
								if (cbooktime == null) {
									info.setHasbooked(0);
								} else {
									info.setHasbooked(1);
								}
								schedulelist.add(info);
							}
						}
				}
			}

			// 获得最新的list
			Collections.sort(schedulelist, new CscheduleCompare());

			// CuserInfo newcuser = cuserService.getCuserByCoachid(coachid);
			// if (newcuser.getCancancel() == 0) {
			// resultMap.put("cancelpermission", 0);
			// } else {
			resultMap.put("cancelpermission", 1);// 设置是否具有设置订单可以取消的权限
			// }
			resultMap.put("maxdays", maxDays);
			resultMap.put("datelist", schedulelist);
			resultMap.put("today", CommonUtils.getTimeFormat(now.getTime(), "yyyy-MM-dd"));// 返回服务器的日期
			// 当前小时
			int hour = now.get(Calendar.HOUR_OF_DAY);
			resultMap.put("hour", hour);
		} else {
			resultMap.put("code", 5);
			resultMap.put("message", "请先设置默认教学地址");
		}
		long end=System.currentTimeMillis();
		//System.out.println("总耗时："+(end-start));
	}
	
	/**
	 * 根据教练ID和日期值查询某一天的教练日程安排
	 * 
	 * @param request
	 * @throws ErrException
	 */
	public void getScheduleByDate(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		//long start=System.currentTimeMillis();
		// 获取参数教练ID
		String coachid = getRequestParamter(request, "coachid");
		String newnow = getRequestParamter(request, "date");
		CommonUtils.validateEmpty(coachid);
		CommonUtils.validDateFormat(newnow);
		// 找到该教练
		CuserInfo cuser = cuserService.getCuserByCoachid(coachid);
		// 找到该教练的当前使用地址
		CaddAddressInfo addressInfo = cuserService.getcoachaddress(coachid);
		// 查询出系统的默认科目以及默认价格设置
		int defaultPrice = 100;
		int defaultSubjectID = 1;
		SystemSetInfo set = cuserService.getSystemSetInfo();
		if (set != null) {
			if (set.getCoach_default_price() != null && set.getCoach_default_price() != 0) {
				defaultPrice = set.getCoach_default_price();
			}

			if (set.getCoach_default_subject() != null && set.getCoach_default_subject() != 0) {
				defaultSubjectID = set.getCoach_default_subject();
			}
		}
		
		CsubjectInfo subjectInfo = cuserService.getsubjectBysubjectid(defaultSubjectID);
		if (subjectInfo == null) {
			subjectInfo = cuserService.getFirstSubject();
		}
		if (cuser != null && addressInfo != null) {
			// 查询出默认的日期天使设置
			int maxDays = 30;// 默认设置30天
			SystemSetInfo systemSet = cuserService.getSystemSetInfo();
			if (systemSet != null && systemSet.getBook_day_max() != null && systemSet.getBook_day_max() != 0) {
				maxDays = systemSet.getBook_day_max();
			}
			List<CscheduleInfo> schedulelist = new ArrayList<CscheduleInfo>();
			
				// 取得日期
				/*Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, i);
				Date time = c.getTime();
				String newnow = CommonUtils.getTimeFormat(time, "yyyy-MM-dd");*/
				// 取得DB中当天的所有日程设置
				List<CscheduleInfo> dayist = cscheduleService.getCscheduleInfoByDatelist(newnow, coachid);
				CscheduleInfo allDaySet = null;
				// 查询是否有全天开课或者全天停课设置
				if (dayist != null) {
					for (CscheduleInfo cscheduleInfo : dayist) {
						if ("0".equals(cscheduleInfo.getHour())) {
							allDaySet = cscheduleInfo;
							break;
						}
					}
				}

				if (allDaySet == null) {// 生成默认的全天设置
					allDaySet = new CscheduleInfo();
					allDaySet.setCoachid(CommonUtils.parseInt(coachid, 0));
					allDaySet.setDate(newnow);
					allDaySet.setHour("0");
					allDaySet.setState(0);
					allDaySet.setCancelstate(cscheduleService.getDefaultCancelBydate(newnow));
				}
				schedulelist.add(allDaySet);
				// 时间点循环
				
						for (int k = 5; k < 24; k++) {
							CscheduleInfo info = null;
							// 先寻找数据库中是否有该时间点的设置
							for (CscheduleInfo cscheduleInfo : dayist) {
								if (String.valueOf(k).equals(cscheduleInfo.getHour())) {
									info = cscheduleInfo;
									break;
								}
							}
							// 如果在数据库中找到了设置
							if (info != null) {
								// 查询地址信息
								CaddAddressInfo address = cuserService.getaddress(info.getAddressid());
								if (address != null) {
									info.setAddressdetail(address.getDetail());
								} else {// 找不到地址的话
									address = cscheduleService.getDefaultAddress(coachid, String.valueOf(k));// 是否有默认地址设置
									if (address != null) {
										info.setAddressdetail(address.getDetail());
									} else {// 设置为教练默认地址
										info.setAddressdetail(addressInfo.getDetail());
									}
								}
		
								// 科目信息
								CsubjectInfo subject = cuserService.getSubjectById(info.getSubjectid());
								if (subject != null) {
									info.setSubject(subject.getSubjectname());
								} else {
									subject = cscheduleService.getDefaultSubject(coachid, String.valueOf(k));
									if (subject != null) {
										info.setSubject(subject.getSubjectname());
									} else {
										info.setSubject(subjectInfo.getSubjectname());
									}
								}
								// 是否已经被预订
								CBookTimeInfo cbooktime = cscheduleService.getcoachbooktime(String.valueOf(k), CommonUtils.parseInt(coachid, 0), newnow);
								if (cbooktime == null) {
									info.setHasbooked(0);
								} else {
									info.setHasbooked(1);
								}
		
								schedulelist.add(info);
							} else {// 数据库中没有时间点的设置
								info = new CscheduleInfo();
								info.setCoachid(CommonUtils.parseInt(coachid, 0));
								info.setHour(String.valueOf(k));
								info.setDate(newnow);
								// 默认价格
								BigDecimal price = cscheduleService.getDefaultPrice(coachid, String.valueOf(k));
								if (price != null && price.doubleValue() != 0) {
									info.setPrice(price);
								} else {// 设置为系统的默认价格
									info.setPrice(new BigDecimal(defaultPrice));
								}
								// 默认的开课休息状态
								int rest = cscheduleService.getDefaultRest(coachid, String.valueOf(k));
								if (rest != -1) {
									info.setIsrest(rest);
								} else {// 5、6、12、18设置为休息
									if (k == 12 || k == 18 || k == 5 || k == 6) {
										info.setIsrest(1);
									} else {
										info.setIsrest(0);
									}
								}
								// 地址信息
								CaddAddressInfo address = cscheduleService.getDefaultAddress(coachid, String.valueOf(k));
								if (address != null) {
									info.setAddressid(address.getAddressid());
									info.setAddressdetail(address.getDetail());
								} else {
									info.setAddressid(addressInfo.getAddressid());
									info.setAddressdetail(addressInfo.getDetail());
								}
								// 科目信息
								CsubjectInfo subject = cscheduleService.getDefaultSubject(coachid, String.valueOf(k));
								if (subject != null) {
									info.setSubjectid(subject.getSubjectid());
									info.setSubject(subject.getSubjectname());
								} else {
									info.setSubjectid(subjectInfo.getSubjectid());
									info.setSubject(subjectInfo.getSubjectname());
								}
								// 是否被预定
								CBookTimeInfo cbooktime = cscheduleService.getcoachbooktime(String.valueOf(k), CommonUtils.parseInt(coachid, 0), newnow);
								if (cbooktime == null) {
									info.setHasbooked(0);
								} else {
									info.setHasbooked(1);
								}
								schedulelist.add(info);
							}
							
							
						}
				
			

			// 获得最新的list
			Collections.sort(schedulelist, new CscheduleCompare());

			// CuserInfo newcuser = cuserService.getCuserByCoachid(coachid);
			// if (newcuser.getCancancel() == 0) {
			// resultMap.put("cancelpermission", 0);
			// } else {
			resultMap.put("cancelpermission", 1);// 设置是否具有设置订单可以取消的权限
			// }
			resultMap.put("datelist", schedulelist);
		} else {
			resultMap.put("code", 5);
			resultMap.put("message", "请先设置默认教学地址");
		}
		//long end=System.currentTimeMillis();
		//System.out.println("总耗时："+(end-start));
	}
	/**
	 * 设置教练某天的休息时间
	 * 
	 * @param request
	 * @throws ErrException
	 */
	public void setDateTime(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String coachid = getRequestParamter(request, "coachid");
		CommonUtils.validateEmpty(coachid);
		String day = getRequestParamter(request, "day");
		CommonUtils.validateEmpty(day);
		String setjson = getRequestParamter(request, "setjson");
		CommonUtils.validateEmpty(setjson);

		CaddAddressInfo addressInfo = cuserService.getcoachaddress(coachid);
		// 查询出系统的默认科目以及默认价格设置
		int defaultPrice = 100;
		int defaultSubjectID = 1;
		SystemSetInfo set = cuserService.getSystemSetInfo();
		if (set != null) {
			if (set.getCoach_default_price() != null && set.getCoach_default_price() != 0) {
				defaultPrice = set.getCoach_default_price();
			}

			if (set.getCoach_default_subject() != null && set.getCoach_default_subject() != 0) {
				defaultSubjectID = set.getCoach_default_subject();
			}
		}

		CsubjectInfo subjectInfo = cuserService.getsubjectBysubjectid(defaultSubjectID);
		if (subjectInfo == null) {
			subjectInfo = cuserService.getFirstSubject();
		}
		JSONArray json = null;
		try {
			json = new JSONArray(setjson);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}

		if (json == null) {
			resultMap.put("code", 2);
			resultMap.put("message", "设置失败,数据错误");
			return;
		} else {
			for (int i = 0; i < json.length(); i++) {
				JSONObject nextjson = null;
				String hour = null;
				String price = null;
				String isrest = null;
				String addressid = null;
				String subjectid = null;
				try {
					nextjson = json.getJSONObject(i);
					hour = nextjson.getString("hour");
					price = nextjson.getString("price");
					isrest = nextjson.getString("isrest");
					addressid = nextjson.getString("addressid");
					subjectid = nextjson.getString("subjectid");
				} catch (JSONException e) {
					e.printStackTrace();
				}

				if (hour == null || price == null || isrest == null || addressid == null || subjectid == null) {
					resultMap.put("code", 2);
					resultMap.put("message", "设置失败,数据错误");
					return;
				}

				CscheduleInfo cscheduleInfo = cscheduleService.getCscheduleByday(coachid, day, hour);// 根据时间教练 找到日程信息

				if (cscheduleInfo == null) {
					// 新添加日程
					CscheduleInfo scheduleInfo = new CscheduleInfo();
					scheduleInfo.setCoachid(CommonUtils.parseInt(coachid, 0));// 教练id
					scheduleInfo.setDate(day); // 日期
					scheduleInfo.setHour(hour); // 设置时间

					BigDecimal b = new BigDecimal(CommonUtils.parseDouble(price, 0d));
					scheduleInfo.setPrice(b); // 设置单价

					scheduleInfo.setIsrest(CommonUtils.parseInt(isrest, 0)); // 设置是否休息

					scheduleInfo.setAddressid(CommonUtils.parseInt(addressid, 0)); // 设置地址id

					scheduleInfo.setSubjectid(CommonUtils.parseInt(subjectid, 0));// 设置科目id
					cscheduleService.addScheduleInfo(scheduleInfo);
				} else {
					BigDecimal b = new BigDecimal(CommonUtils.parseDouble(price, 0d));
					cscheduleInfo.setPrice(b); // 设置单价

					cscheduleInfo.setIsrest(CommonUtils.parseInt(isrest, 0)); // 设置是否休息

					cscheduleInfo.setAddressid(CommonUtils.parseInt(addressid, 0)); // 设置地址id

					cscheduleInfo.setSubjectid(CommonUtils.parseInt(subjectid, 0));// 设置科目id
					cscheduleService.updateScheduleInfo(cscheduleInfo);
				}
			}
		}

		// 返回当天的数据给前端
		List<CscheduleInfo> schedulelist = new ArrayList<CscheduleInfo>();

		List<CscheduleInfo> dayist = cscheduleService.getCscheduleInfoByDatelist(day, coachid);
		CscheduleInfo allDaySet = null;
		if (dayist != null) {
			for (CscheduleInfo cscheduleInfo : dayist) {
				if ("0".equals(cscheduleInfo.getHour())) {
					allDaySet = cscheduleInfo;
					break;
				}
			}
		}

		if (allDaySet == null) {// 生成默认的全天设置
			allDaySet = new CscheduleInfo();
			allDaySet.setCoachid(CommonUtils.parseInt(coachid, 0));
			allDaySet.setDate(day);
			allDaySet.setHour("0");
			allDaySet.setState(0);
			allDaySet.setCancelstate(cscheduleService.getDefaultCancelBydate(day));
		}
		schedulelist.add(allDaySet);

		for (int k = 5; k < 24; k++) {
			CscheduleInfo info = null;
			for (CscheduleInfo cscheduleInfo : dayist) {
				if (String.valueOf(k).equals(cscheduleInfo.getHour())) {
					info = cscheduleInfo;
					break;
				}
			}

			if (info != null) {
				CaddAddressInfo address = cuserService.getaddress(info.getAddressid());
				if (address != null) {
					info.setAddressdetail(address.getDetail());
				} else {
					address = cscheduleService.getDefaultAddress(coachid, String.valueOf(k));
					if (address != null) {
						info.setAddressdetail(address.getDetail());
					} else {
						info.setAddressdetail(addressInfo.getDetail());
					}
				}

				CsubjectInfo subject = cuserService.getSubjectById(info.getSubjectid());
				if (subject != null) {
					info.setSubject(subject.getSubjectname());
				} else {
					subject = cscheduleService.getDefaultSubject(coachid, String.valueOf(k));
					if (subject != null) {
						info.setSubject(subject.getSubjectname());
					} else {
						info.setSubject(subjectInfo.getSubjectname());
					}
				}
				// 是否已经被预订
				CBookTimeInfo cbooktime = cscheduleService.getcoachbooktime(String.valueOf(k), CommonUtils.parseInt(coachid, 0), day);
				if (cbooktime == null) {
					info.setHasbooked(0);
				} else {
					info.setHasbooked(1);
				}

				schedulelist.add(info);
			} else {
				info = new CscheduleInfo();
				info.setCoachid(CommonUtils.parseInt(coachid, 0));
				info.setHour(String.valueOf(k));
				info.setDate(day);
				BigDecimal price = cscheduleService.getDefaultPrice(coachid, String.valueOf(k));
				if (price != null && price.doubleValue() != 0) {
					info.setPrice(price);
				} else {
					info.setPrice(new BigDecimal(defaultPrice));
				}
				int rest = cscheduleService.getDefaultRest(coachid, String.valueOf(k));
				if (rest != -1) {
					info.setIsrest(rest);
				} else {
					if (k == 12 || k == 18 || k == 5 || k == 6) {
						info.setIsrest(1);
					} else {
						info.setIsrest(0);
					}
				}

				CaddAddressInfo address = cscheduleService.getDefaultAddress(coachid, String.valueOf(k));
				if (address != null) {
					info.setAddressid(address.getAddressid());
					info.setAddressdetail(address.getDetail());
				} else {
					info.setAddressid(addressInfo.getAddressid());
					info.setAddressdetail(addressInfo.getDetail());
				}
				CsubjectInfo subject = cscheduleService.getDefaultSubject(coachid, String.valueOf(k));
				if (subject != null) {
					info.setSubjectid(subject.getSubjectid());
					info.setSubject(subject.getSubjectname());
				} else {
					info.setSubjectid(subjectInfo.getSubjectid());
					info.setSubject(subjectInfo.getSubjectname());
				}

				CBookTimeInfo cbooktime = cscheduleService.getcoachbooktime(String.valueOf(k), CommonUtils.parseInt(coachid, 0), day);
				if (cbooktime == null) {
					info.setHasbooked(0);
				} else {
					info.setHasbooked(1);
				}
				schedulelist.add(info);
			}
		}
		// 获得最新的list
		Collections.sort(schedulelist, new CscheduleCompare());
		resultMap.put("code", 1);
		resultMap.put("message", "修改成功");
		resultMap.put("datelist", schedulelist);
	}

	/**
	 * 改变某一天的全天安排
	 * 
	 * @param request
	 * @throws ErrException
	 */
	public void changeAllDaySchedule(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String coachid = getRequestParamter(request, "coachid");
		CommonUtils.validateEmpty(coachid);
		String day = getRequestParamter(request, "day");
		CommonUtils.validateEmpty(day);
		String type = getRequestParamter(request, "type");// 修改的状态1.全天开课 2.全天休息
		CommonUtils.validateEmpty(type);

		CuserInfo cuser = cuserService.getCuserByCoachid(coachid);
		CaddAddressInfo addressInfo = cuserService.getcoachaddress(coachid);

		CscheduleInfo cscheduleInfo = cscheduleService.getCscheduleByday(coachid, day, String.valueOf(0));
		if (cuser != null && addressInfo != null) {
			if (cscheduleInfo == null) {
				// 如果没有日程 此时只有设置全天开课的情况下需要新增

				cscheduleInfo = new CscheduleInfo();
				cscheduleInfo.setCoachid(CommonUtils.parseInt(coachid, 0)); // 教练id
				cscheduleInfo.setDate(day); // 设置date
				cscheduleInfo.setHour("0"); // 默认休息时间
				cscheduleInfo.setPrice(new BigDecimal(0d));

				if (CommonUtils.parseInt(type, 0) == 1) {// 全天开课
					// 传过来全天休息
					cscheduleInfo.setState(1);
					cscheduleInfo.setCancelstate(cscheduleService.getDefaultCancelBydate(day));
					// 状态为休息
					cscheduleService.addScheduleInfo(cscheduleInfo);
				} else {
					cscheduleInfo.setState(0);
				}

			} else {
				if (CommonUtils.parseInt(type, 0) == 1) {
					// 传过来全天开课
					cscheduleInfo.setState(1);// 设置状态为开课
					cuserService.updateObject(cscheduleInfo);
				} else if (CommonUtils.parseInt(type, 0) == 2) {
					cscheduleInfo.setState(0);// 状态为休息
					cuserService.updateObject(cscheduleInfo);
				} 
			}
			//根据教练当前开课状态来设置教练表中coursestate
			cscheduleService.getCoachStateByFunction(coachid, 5,day, 5, 23, 0);
			resultMap.put("code", 1);
			resultMap.put("message", "设置成功");
			resultMap.put("type", cscheduleInfo.getState());
		} else {
			resultMap.put("code", 5);
			resultMap.put("message", "请先设置默认教学地址");
		}
	}

	/**
	 * 修改订单式是否可以取消
	 * 
	 * @param request
	 * @throws ErrException
	 */
	public void changeOrderCancel(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String coachid = getRequestParamter(request, "coachid");
		CommonUtils.validateEmpty(coachid);
		String day = getRequestParamter(request, "day");
		CommonUtils.validateEmpty(day);
		String type = getRequestParamter(request, "type");// 0.可以取消 1.不可以取消
		CommonUtils.validateEmpty(type);
		CaddAddressInfo addressInfo = cuserService.getcoachaddress(coachid);
		CuserInfo cuser = cuserService.getCuserByCoachid(coachid);
		CscheduleInfo cscheduleInfo = cscheduleService.getCscheduleByday(coachid, day, String.valueOf(0));
		if (cuser != null && addressInfo != null) {
			if (cscheduleInfo == null) {
				// 如果没有日程 新增
				cscheduleInfo = new CscheduleInfo();
				cscheduleInfo.setCoachid(CommonUtils.parseInt(coachid, 0)); // 教练id
				cscheduleInfo.setDate(day); // 设置时间
				cscheduleInfo.setHour(String.valueOf(0)); // 默认时间点
				cscheduleInfo.setState(Constant.STATE_DEF); // 默认全天状态,表示当天休息,因为全天的设置没有查询到,所以这里直接全天休息就可以了
				cscheduleInfo.setPrice(new BigDecimal(0d));
				if (CommonUtils.parseInt(type, 0) == 0) {
					cscheduleInfo.setCancelstate(0);
					cscheduleService.addScheduleInfo(cscheduleInfo);
				} else if (CommonUtils.parseInt(type, 0) == 1) {
					cscheduleInfo.setCancelstate(1);
					cscheduleService.addScheduleInfo(cscheduleInfo);
				}
			} else {
				// 如果有 更新cancle状态
				if (CommonUtils.parseInt(type, 0) == 0) {// 设置为可以取消
					cscheduleInfo.setCancelstate(0);
					cscheduleService.updateScheduleInfo(cscheduleInfo);
				} else if (CommonUtils.parseInt(type, 0) == 1) {// 设置为不可以取消
					cscheduleInfo.setCancelstate(1);
					cscheduleService.updateScheduleInfo(cscheduleInfo);
				}
			}
			resultMap.put("code", 1);
			resultMap.put("message", "设置成功");
			resultMap.put("type", cscheduleInfo.getCancelstate());
		} else {
			resultMap.put("code", 5);
			resultMap.put("message", "请先设置默认教学地址");
		}
	}

	public void setDefault(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String coachid = getRequestParamter(request, "coachid");
		CommonUtils.validateEmpty(coachid);
		String day = getRequestParamter(request, "day");
		CommonUtils.validateEmpty(day);

		CaddAddressInfo addressInfo = cuserService.getcoachaddress(coachid);

		// 查询出系统的默认科目以及默认价格设置
		int defaultPrice = 100;
		int defaultSubjectID = 1;
		SystemSetInfo set = cuserService.getSystemSetInfo();
		if (set != null) {
			if (set.getCoach_default_price() != null && set.getCoach_default_price() != 0) {
				defaultPrice = set.getCoach_default_price();
			}

			if (set.getCoach_default_subject() != null && set.getCoach_default_subject() != 0) {
				defaultSubjectID = set.getCoach_default_subject();
			}
		}

		CsubjectInfo subjectInfo = cuserService.getsubjectBysubjectid(defaultSubjectID);
		if (subjectInfo == null) {
			subjectInfo = cuserService.getFirstSubject();
		}

		// 查询当天的设置出来
		List<CscheduleInfo> dayist = cscheduleService.getCscheduleInfoByDatelist(day, coachid);
		CscheduleInfo allDaySet = null;
		if (dayist != null) {
			for (CscheduleInfo cscheduleInfo : dayist) {
				if ("0".equals(cscheduleInfo.getHour())) {
					allDaySet = cscheduleInfo;
					break;
				}
			}
		}

		if (allDaySet == null) {// 生成默认的全天设置
			allDaySet = new CscheduleInfo();
			allDaySet.setCoachid(CommonUtils.parseInt(coachid, 0));
			allDaySet.setDate(day);
			allDaySet.setHour("0");
			allDaySet.setState(0);
			allDaySet.setCancelstate(cscheduleService.getDefaultCancelBydate(day));
		}

		cscheduleService.addDefaultSet(CommonUtils.parseInt(coachid, 0), allDaySet);

		for (int k = 5; k < 24; k++) {
			CscheduleInfo info = null;
			for (CscheduleInfo cscheduleInfo : dayist) {
				if (String.valueOf(k).equals(cscheduleInfo.getHour())) {
					info = cscheduleInfo;
					break;
				}
			}

			if (info != null) {
				cscheduleService.addDefaultSet(CommonUtils.parseInt(coachid, 0), info);
			} else {
				info = new CscheduleInfo();
				info.setCoachid(CommonUtils.parseInt(coachid, 0));
				info.setHour(String.valueOf(k));
				info.setDate(day);
				BigDecimal price = cscheduleService.getDefaultPrice(coachid, String.valueOf(k));
				if (price != null && price.doubleValue() != 0) {
					info.setPrice(price);
				} else {
					info.setPrice(new BigDecimal(defaultPrice));
				}
				int rest = cscheduleService.getDefaultRest(coachid, String.valueOf(k));
				if (rest != -1) {
					info.setIsrest(rest);
				} else {
					if (k == 12 || k == 18 || k == 5 || k == 6) {
						info.setIsrest(1);
					} else {
						info.setIsrest(0);
					}
				}

				CaddAddressInfo address = cscheduleService.getDefaultAddress(coachid, String.valueOf(k));
				if (address != null) {
					info.setAddressid(address.getAddressid());
				} else {
					info.setAddressid(addressInfo.getAddressid());
				}
				CsubjectInfo subject = cscheduleService.getDefaultSubject(coachid, String.valueOf(k));
				if (subject != null) {
					info.setSubjectid(subject.getSubjectid());
				} else {
					info.setSubjectid(subjectInfo.getSubjectid());
				}
				cscheduleService.addDefaultSet(CommonUtils.parseInt(coachid, 0), info);
			}
		}

		// 重新获取下所有的日期设置给前端
		int maxDays = 30;// 默认设置30天
		SystemSetInfo systemSet = cuserService.getSystemSetInfo();
		if (systemSet != null && systemSet.getBook_day_max() != null && systemSet.getBook_day_max() != 0) {
			maxDays = systemSet.getBook_day_max();
		}
		Calendar now = Calendar.getInstance();

		List<CscheduleInfo> schedulelist = new ArrayList<CscheduleInfo>();
		for (int i = 0; i < maxDays; i++) {

			Calendar c = Calendar.getInstance();
			c.add(Calendar.DATE, i);
			Date time = c.getTime();
			String newnow = CommonUtils.getTimeFormat(time, "yyyy-MM-dd");

			List<CscheduleInfo> dayist1 = cscheduleService.getCscheduleInfoByDatelist(newnow, coachid);
			CscheduleInfo allDaySet1 = null;
			if (dayist1 != null) {
				for (CscheduleInfo cscheduleInfo : dayist1) {
					if ("0".equals(cscheduleInfo.getHour())) {
						allDaySet1 = cscheduleInfo;
						break;
					}
				}
			}

			if (allDaySet1 == null) {// 生成默认的全天设置
				allDaySet1 = new CscheduleInfo();
				allDaySet1.setCoachid(CommonUtils.parseInt(coachid, 0));
				allDaySet1.setDate(newnow);
				allDaySet1.setHour("0");
				allDaySet1.setState(0);
				allDaySet1.setCancelstate(cscheduleService.getDefaultCancelBydate(newnow));
			}
			schedulelist.add(allDaySet1);

			for (int k = 5; k < 24; k++) {
				CscheduleInfo info = null;
				for (CscheduleInfo cscheduleInfo : dayist1) {
					if (String.valueOf(k).equals(cscheduleInfo.getHour())) {
						info = cscheduleInfo;
						break;
					}
				}

				if (info != null) {
					CaddAddressInfo address = cuserService.getaddress(info.getAddressid());
					if (address != null) {
						info.setAddressdetail(address.getDetail());
					} else {
						address = cscheduleService.getDefaultAddress(coachid, String.valueOf(k));
						if (address != null) {
							info.setAddressdetail(address.getDetail());
						} else {
							info.setAddressdetail(addressInfo.getDetail());
						}
					}

					CsubjectInfo subject = cuserService.getSubjectById(info.getSubjectid());
					if (subject != null) {
						info.setSubject(subject.getSubjectname());
					} else {
						subject = cscheduleService.getDefaultSubject(coachid, String.valueOf(k));
						if (subject != null) {
							info.setSubject(subject.getSubjectname());
						} else {
							info.setSubject(subjectInfo.getSubjectname());
						}
					}
					// 是否已经被预订
					CBookTimeInfo cbooktime = cscheduleService.getcoachbooktime(String.valueOf(k), CommonUtils.parseInt(coachid, 0), newnow);
					if (cbooktime == null) {
						info.setHasbooked(0);
					} else {
						info.setHasbooked(1);
					}

					schedulelist.add(info);
				} else {
					info = new CscheduleInfo();
					info.setCoachid(CommonUtils.parseInt(coachid, 0));
					info.setHour(String.valueOf(k));
					info.setDate(newnow);
					BigDecimal price = cscheduleService.getDefaultPrice(coachid, String.valueOf(k));
					if (price != null && price.doubleValue() != 0) {
						info.setPrice(price);
					} else {
						info.setPrice(new BigDecimal(defaultPrice));
					}
					int rest = cscheduleService.getDefaultRest(coachid, String.valueOf(k));
					if (rest != -1) {
						info.setIsrest(rest);
					} else {
						if (k == 12 || k == 18 || k == 5 || k == 6) {
							info.setIsrest(1);
						} else {
							info.setIsrest(0);
						}
					}

					CaddAddressInfo address = cscheduleService.getDefaultAddress(coachid, String.valueOf(k));
					if (address != null) {
						info.setAddressid(address.getAddressid());
						info.setAddressdetail(address.getDetail());
					} else {
						info.setAddressid(addressInfo.getAddressid());
						info.setAddressdetail(addressInfo.getDetail());
					}
					CsubjectInfo subject = cscheduleService.getDefaultSubject(coachid, String.valueOf(k));
					if (subject != null) {
						info.setSubjectid(subject.getSubjectid());
						info.setSubject(subject.getSubjectname());
					} else {
						info.setSubjectid(subjectInfo.getSubjectid());
						info.setSubject(subjectInfo.getSubjectname());
					}

					CBookTimeInfo cbooktime = cscheduleService.getcoachbooktime(String.valueOf(k), CommonUtils.parseInt(coachid, 0), newnow);
					if (cbooktime == null) {
						info.setHasbooked(0);
					} else {
						info.setHasbooked(1);
					}
					schedulelist.add(info);
				}
			}
		}

		// 获得最新的list
		Collections.sort(schedulelist, new CscheduleCompare());

		CuserInfo newcuser = cuserService.getCuserByCoachid(coachid);
		if (newcuser.getCancancel() == 0) {
			resultMap.put("cancelpermission", 0);
		} else {
			resultMap.put("cancelpermission", 1);
		}

		resultMap.put("maxdays", maxDays);
		resultMap.put("datelist", schedulelist);
		resultMap.put("today", CommonUtils.getTimeFormat(now.getTime(), "yyyy-MM-dd"));
		// 当前小时
		int hour = now.get(Calendar.HOUR_OF_DAY);
		resultMap.put("hour", hour);
		resultMap.put("code", 1);
		resultMap.put("message", "设置成功");
	}
}
