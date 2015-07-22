package com.daoshun.guangda.servlet;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.daoshun.common.CommonUtils;
import com.daoshun.common.Constant;
import com.daoshun.common.ErrException;
import com.daoshun.guangda.pojo.CouponRecord;
import com.daoshun.guangda.pojo.CscheduleInfo;
import com.daoshun.guangda.pojo.CuserInfo;
import com.daoshun.guangda.pojo.SuserInfo;
import com.daoshun.guangda.pojo.SystemSetInfo;
import com.daoshun.guangda.service.ICUserService;
import com.daoshun.guangda.service.ISBookService;
import com.daoshun.guangda.service.ISUserService;
import com.daoshun.guangda.service.ISystemService;

@WebServlet("/sbook")
public class SbookServlet extends BaseServlet {

	private static final long serialVersionUID = 6998440419757851253L;
	private ISBookService sbookService;
	private ISystemService systemService;
	private ICUserService cuserService;
	private ISUserService suserService;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		sbookService = (ISBookService) applicationContext.getBean("sbookService");
		systemService = (ISystemService) applicationContext.getBean("systemService");
		cuserService = (ICUserService) applicationContext.getBean("cuserService");
		suserService = (ISUserService) applicationContext.getBean("suserService");
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String action = getAction(request);

			if (Constant.BOOKCOACH.equals(action) || Constant.GETCOUPONLIST.equals(action) || Constant.GETHISCOUPONLIST.equals(action) || Constant.GETCANUSECOUPONLIST.equals(action)) {
				if (!checkSession(request, action, resultMap)) {
					setResult(response, resultMap);
					return;
				}
			}

			if (Constant.GETCOACHDETAIL.equals(action)) {
				// 获取教练详细
				getCoachDetail(request, resultMap);
			} else if (Constant.REFRESHCOACHSCHEDULE.equals(action)) {
				// 刷新教练日程安排
				refreshCoachSchedule(request, resultMap);
			} else if (Constant.GETNEARBYCOACH.equals(action)) {
				// 获取附近教练
				getNearByCoach(request, resultMap);
			} else if (Constant.GETCOACHLIST.equals(action)) {
				// 获取教练列表
				getCoachList(request, resultMap);
			} else if (Constant.BOOKCOACH.equals(action)) {
				// 预定教练
				bookCoach(request, resultMap);
			} else if (Constant.GETCOACHCOMMENTS.equals(action)) {
				getCoachComments(request, resultMap);
			} else if (Constant.GETCOUPONLIST.equals(action)) {
				getCouponList(request, resultMap);
			} else if (Constant.GETHISCOUPONLIST.equals(action)) {
				getHisCouponList(request, resultMap);
			} else if (Constant.GETCANUSECOUPONLIST.equals(action)) {
				getCanUseCouponList(request, resultMap);
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

		if (Constant.GETCOACHDETAIL.equals(action)) {
			// 获取教练详细
		} else if (Constant.REFRESHCOACHSCHEDULE.equals(action)) {
			// 刷新教练日程安排
			userid = getRequestParamter(request, "studentid");
			usertype = "2";
		} else if (Constant.GETNEARBYCOACH.equals(action)) {
			// 获取附近教练
		} else if (Constant.GETCOACHLIST.equals(action)) {
			// 获取教练列表
		} else if (Constant.BOOKCOACH.equals(action)) {
			// 预定教练
			userid = getRequestParamter(request, "studentid");
			usertype = "2";
		} else if (Constant.GETCOACHCOMMENTS.equals(action)) {

		} else if (Constant.GETCOUPONLIST.equals(action)) {
			userid = getRequestParamter(request, "studentid");
			usertype = "2";
		} else if (Constant.GETHISCOUPONLIST.equals(action)) {
			userid = getRequestParamter(request, "studentid");
			usertype = "2";
		} else if (Constant.GETCANUSECOUPONLIST.equals(action)) {
			userid = getRequestParamter(request, "studentid");
			usertype = "2";
		}

		if (!CommonUtils.isEmptyString(userid) && !CommonUtils.isEmptyString(usertype)) {
			if (CommonUtils.parseInt(usertype, 0) == 1) {
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
				SuserInfo suser = suserService.getUserById(userid);
				if (suser != null) {
					String token = getRequestParamter(request, "token");
					if (!CommonUtils.isEmptyString(token)) {
						// 时间获取
						if (token.equals(suser.getToken())) {
							int login_vcode_time = 15;// 默认十五天
							SystemSetInfo systemSet = systemService.getSystemSet();
							if (systemSet != null && systemSet.getLogin_vcode_time() != null && systemSet.getLogin_vcode_time() != 0) {
								login_vcode_time = systemSet.getLogin_vcode_time();
							}

							Calendar now = Calendar.getInstance();
							Calendar tokenTime = Calendar.getInstance();
							tokenTime.setTime(suser.getToken_time());
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

		if (Constant.GETCOACHDETAIL.equals(action)) {
			// 获取教练详细
		} else if (Constant.REFRESHCOACHSCHEDULE.equals(action)) {
			// 刷新教练日程安排
			userid = getRequestParamter(request, "studentid");
			usertype = "2";
		} else if (Constant.GETNEARBYCOACH.equals(action)) {
			// 获取附近教练
		} else if (Constant.GETCOACHLIST.equals(action)) {
			// 获取教练列表
		} else if (Constant.BOOKCOACH.equals(action)) {
			// 预定教练
			userid = getRequestParamter(request, "studentid");
			usertype = "2";
		} else if (Constant.GETCOACHCOMMENTS.equals(action)) {

		} else if (Constant.GETCOUPONLIST.equals(action)) {
			userid = getRequestParamter(request, "studentid");
			usertype = "2";
		} else if (Constant.GETHISCOUPONLIST.equals(action)) {
			userid = getRequestParamter(request, "studentid");
			usertype = "2";
		} else if (Constant.GETCANUSECOUPONLIST.equals(action)) {
			userid = getRequestParamter(request, "studentid");
			usertype = "2";
		}

		if (!CommonUtils.isEmptyString(userid) && !CommonUtils.isEmptyString(usertype)) {
			systemService.recordUserAction(userid, usertype, "sbook", action);
		}
	}

	public void getCoachDetail(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String coachid = getRequestParamter(request, "coachid");
		CommonUtils.validateEmpty(coachid);
		CuserInfo cuser = sbookService.getCoachDetail(coachid);
		resultMap.put("coachinfo", cuser);
	}

	public void refreshCoachSchedule(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String coachid = getRequestParamter(request, "coachid");
		String studentid = getRequestParamter(request, "studentid");
		String date = getRequestParamter(request, "date");
		CommonUtils.validateEmpty(coachid);
		CommonUtils.validateEmpty(date);
		List<CscheduleInfo> datelist = sbookService.refreshCoachSchedule(coachid, date, studentid);
		resultMap.put("datelist", datelist);
	}

	public void getNearByCoach(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String pointcenter = getRequestParamter(request, "pointcenter");
		String radius = getRequestParamter(request, "radius");
		CommonUtils.validateEmpty(pointcenter);
		CommonUtils.validateEmpty(radius);
		String condition1 = getRequestParamter(request, "condition1");// 关键字
		String condition2 = getRequestParamter(request, "condition2");// 星级的下限
		String condition3 = getRequestParamter(request, "condition3");// 时间的下限
		String condition4 = getRequestParamter(request, "condition4");// 时间的上限
		String condition5 = getRequestParamter(request, "condition5");// 性别 1.男 2.女 0.不限
		String condition6 = getRequestParamter(request, "condition6");// 教练教学科目ID
		String condition8 = getRequestParamter(request, "condition8");// 价格下限
		String condition9 = getRequestParamter(request, "condition9");// 价格上限
		String condition10 = getRequestParamter(request, "condition10");// 车型 0.表示不限
		String condition11 = getRequestParamter(request, "condition11");// 准教车型

		List<CuserInfo> coachlist = sbookService.getNearByCoach(pointcenter, radius, condition1, condition2, condition3, condition4, condition5, condition6, condition8, condition9, condition10,
				condition11);
		resultMap.put("coachlist", coachlist);
	}

	public void getCoachList(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String pagenum = getRequestParamter(request, "pagenum");
		String condition1 = getRequestParamter(request, "condition1");// 关键字
		String condition2 = getRequestParamter(request, "condition2");// 星级的下限
		String condition3 = getRequestParamter(request, "condition3");// 时间的下限
		String condition4 = getRequestParamter(request, "condition4");// 时间的上限
		String condition5 = getRequestParamter(request, "condition5");// 性别 1.男 2.女 0.不限
		String condition6 = getRequestParamter(request, "condition6");// 教练教学科目ID
		String condition8 = getRequestParamter(request, "condition8");// 价格下限
		String condition9 = getRequestParamter(request, "condition9");// 价格上限
		String condition10 = getRequestParamter(request, "condition10");// 车型 0.表示不限
		String condition11 = getRequestParamter(request, "condition11");// 准教车型

		HashMap<String, Object> result = sbookService.getCoachList(condition1, condition2, condition3, condition4, condition5, condition6, condition8, condition9, condition10, condition11, pagenum);
		resultMap.putAll(result);
	}

	public void bookCoach(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String coachid = getRequestParamter(request, "coachid");
		String studentid = getRequestParamter(request, "studentid");
		String date = getRequestParamter(request, "date");
		CommonUtils.validateEmpty(coachid);
		CommonUtils.validateEmpty(studentid);
		CommonUtils.validateEmpty(date);
		try {
			resultMap.putAll(sbookService.bookCoach(coachid, studentid, date));
		} catch (Exception e) {
			resultMap.put("code", 2);
			resultMap.put("message", "预约失败");
			e.printStackTrace();
		}
	}

	public void getCoachComments(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String coachid = getRequestParamter(request, "coachid");
		// String studentid = getRequestParamter(request, "studentid");
		String pagenum = getRequestParamter(request, "pagenum");
		CommonUtils.validateEmpty(coachid);
		resultMap.putAll(sbookService.getCoachComments(coachid, pagenum));
	}

	/**
	 * 获取学员小巴券列表
	 * 
	 * @param request
	 * @throws ErrException
	 */
	public void getCouponList(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String studentid = getRequestParamter(request, "studentid");// 学员ID
		CommonUtils.validateEmpty(studentid);
		List<CouponRecord> list = sbookService.getcouponList(studentid);
		resultMap.put("couponlist", list);
	}

	/**
	 * 获取学员历史小巴券列表
	 * 
	 * @param request
	 * @throws ErrException
	 */
	public void getHisCouponList(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String studentid = getRequestParamter(request, "studentid");// 学员ID
		CommonUtils.validateEmpty(studentid);
		List<CouponRecord> list = sbookService.getHisCouponList(studentid);
		resultMap.put("couponlist", list);
	}

	/**
	 * 获取可以使用的小巴券列表 条件:未过期，未使用过,且满足平台、驾校、教练规则
	 * 
	 * @param request
	 * @throws ErrException
	 */
	public void getCanUseCouponList(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String studentid = getRequestParamter(request, "studentid");// 学员ID
		String coachid = getRequestParamter(request, "coachid");// 预订的教练ID
		CommonUtils.validateEmpty(studentid);
		CommonUtils.validateEmpty(coachid);
		List<CouponRecord> list = sbookService.getCanUseCouponList(studentid, coachid);

		int canUseDiff = 0;
		int canUseMaxCount = 1;

		SystemSetInfo info = sbookService.getSystemSetInfo();
		if (info != null) {
			if (info.getCan_use_diff_coupon() != null && info.getCan_use_diff_coupon() != 0)
				canUseDiff = info.getCan_use_diff_coupon();

			if (info.getCan_use_coupon_count() != null && info.getCan_use_coupon_count() != 0)
				canUseMaxCount = info.getCan_use_coupon_count();
		}

		resultMap.put("couponlist", list);
		resultMap.put("canUseDiff", canUseDiff);
		resultMap.put("canUseMaxCount", canUseMaxCount);
	}

}
