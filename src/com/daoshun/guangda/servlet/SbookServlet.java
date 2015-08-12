package com.daoshun.guangda.servlet;

import java.io.IOException;
import java.math.BigDecimal;
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
import com.daoshun.guangda.pojo.*;
import com.daoshun.guangda.service.*;

@WebServlet("/sbook")
public class SbookServlet extends BaseServlet {

	private static final long serialVersionUID = 6998440419757851253L;
	private ISBookService sbookService;
	private ISystemService systemService;
	private ICUserService cuserService;
	private ISUserService suserService;
	private IDriveSchoolService driveSchoolService;
	private ICscheduleService cscheduleService;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		sbookService = (ISBookService) applicationContext.getBean("sbookService");
		systemService = (ISystemService) applicationContext.getBean("systemService");
		cuserService = (ICUserService) applicationContext.getBean("cuserService");
		suserService = (ISUserService) applicationContext.getBean("suserService");
		driveSchoolService = (IDriveSchoolService) applicationContext.getBean("driveSchoolService");
		suserService = (ISUserService) applicationContext.getBean("suserService");
		cscheduleService = (ICscheduleService) applicationContext.getBean("cscheduleService");
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String action = getAction(request);
			String version=getRequestParamter(request, "version");
//			String token = getRequestParamter(request, "token");

			if (Constant.BOOKCOACH.equals(action) || Constant.GETCOUPONLIST.equals(action) || Constant.GETHISCOUPONLIST.equals(action)
//					||Constant.GETCANUSECOUPONLIST.equals(action)||Constant.REFRESHCOACHSCHEDULE.equals(action)
					) {
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
				//评论总列表
				getCoachComments(request, resultMap);
			}else if (Constant.GETCOMMENTSFORSTUDENT.equals(action)) {
				//某个学员针对一个教练的所有评论列表
				getCommentsForStudent(request, resultMap);
			} else if (Constant.GETCOUPONLIST.equals(action)) {
				getCouponList(request, resultMap);
			} else if (Constant.GETHISCOUPONLIST.equals(action)) {
				getHisCouponList(request, resultMap);
			} else if (Constant.GETCANUSECOUPONLIST.equals(action)) {
				getCanUseCouponList(request, resultMap);
			} else if (Constant.GETCANUSECOINSUM.equals(action)) {
				getCanUseCoinSum(request, resultMap);
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
				String version=getRequestParamter(request, "version");
				String token = getRequestParamter(request, "token");
				if(suser== null )
				{
					resultMap.put(Constant.CODE, 99);
					resultMap.put(Constant.MESSAGE, "用户参数错误!");
					return false;
				}
				/*else if ( CommonUtils.isEmptyString(version))
				{
					resultMap.put(Constant.CODE, -1);
					resultMap.put(Constant.MESSAGE, "版本太低,请升级!");
					return false;
				}*/
				else if ( CommonUtils.isEmptyString(token))
				{
					resultMap.put(Constant.CODE, -1);
					resultMap.put(Constant.MESSAGE, "您必须升级才能下订单!");
					return false;
				}


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
		if(cuser!=null){
			if(cuser.getDrive_schoolid()!=null && cuser.getDrive_school()!=null){
				if(cuser.getDrive_schoolid()>0 && cuser.getDrive_school().length()>0) {
					DriveSchoolInfo dr = driveSchoolService.getDriveSchoolInfoByid(cuser.getDrive_schoolid());
					if(dr!=null)
						cuser.setDrive_school(dr.getName());
				}
			}
		}
		
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
		String studentid = getRequestParamter(request, "studentid");// 准教车型
		String cityid = getRequestParamter(request, "cityid");// 准教车型
		List<CuserInfo> coachlist = sbookService.getNearByCoach2(cityid,pointcenter, radius, condition1, condition2, condition3, condition4, condition5, condition6, condition8, condition9, condition10,
				condition11);
		
			if(studentid==null || !"18".equals(studentid)){
				for (CuserInfo cuserInfo : coachlist) {
					if(cuserInfo.getCoachid()==13){
						coachlist.remove(cuserInfo);
						break;
					}
				}
			}
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
		String condition12 = getRequestParamter(request, "condition12");// 教练手机号码
		//接收经纬度和城市名称
		String longitude = getRequestParamter(request, "longitude");//经纬度
		String latitude = getRequestParamter(request, "latitude");
		String cityid = getRequestParamter(request, "cityid");//城市ID
		String studentid = getRequestParamter(request, "studentid");//
		/*CommonUtils.validateEmpty(longitude);
		CommonUtils.validateEmpty(latitude);
		CommonUtils.validateEmpty(cityid);*/
		
		//HashMap<String, Object> result = sbookService.getCoachList(condition1, condition2, condition3, condition4, condition5, condition6, condition8, condition9, condition10, condition11, pagenum);
		
		HashMap<String, Object> result = sbookService.getCoachList2(cityid,condition1, condition2, condition3, condition4, condition5, condition6, condition8, condition9, condition10, condition11, pagenum);
		List<CuserInfo> list=(List<CuserInfo>) result.get("coachlist");
		if(studentid==null || !"18".equals(studentid)){
			for (CuserInfo cuserInfo : list) {
				if(cuserInfo.getCoachid()==13){
					list.remove(cuserInfo);
					break;
				}
			}
		}
		resultMap.putAll(result);
	}

	public void bookCoach(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String coachid = getRequestParamter(request, "coachid");
		String studentid = getRequestParamter(request, "studentid");
		String date = getRequestParamter(request, "date");
		//String paytype = getRequestParamter(request, "paytype");
		String version=getRequestParamter(request, "version");
		
		if (version == null || version.length() == 0)
		{
			resultMap.put("code", 4);
			resultMap.put("message", "您的app版本太低,请退出app并重新进入,将自动检测更新");
			return;
		}


		try {
			CommonUtils.validateEmpty(coachid);
			CommonUtils.validateEmpty(studentid);
			CommonUtils.validateEmpty(date);
			//CommonUtils.validateEmpty(paytype);
			resultMap.putAll(sbookService.bookCoach2(coachid, studentid, date));
			//根据教练当前开课状态来设置教练表中coursestate
			cscheduleService.getCoachStateByFunction(coachid, 5, 5, 23, 0);
		} catch (Exception e) {
			resultMap.put("code", 2);
			resultMap.put("message", "预约失败");
			e.printStackTrace();
		}
	}
	//显示评论，并且过滤重复学员
	public void getCoachComments(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String coachid = getRequestParamter(request, "coachid");
		String type = getRequestParamter(request, "type");//是否过滤重复的学员 1 过滤  2 不过滤
		// String studentid = getRequestParamter(request, "studentid");
		String pagenum = getRequestParamter(request, "pagenum");
		CommonUtils.validateEmpty(coachid);
		resultMap.putAll(sbookService.getCoachComments(coachid,type, pagenum));
		
	}
	/**
	 * 查询某个学员针对一个教练的所有评论列表
	 * @param request
	 * @param resultMap
	 * @throws ErrException
	 */
	public void getCommentsForStudent(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String coachid = getRequestParamter(request, "coachid");
		String studentid = getRequestParamter(request, "studentid");
		String pagenum = getRequestParamter(request, "pagenum");
		CommonUtils.validateEmpty(coachid);
		resultMap.putAll(sbookService.getCommentsFromStudent(coachid, studentid,pagenum));
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
	 * 获取可以使用的小巴币，小巴券，余额
	 * --获取可以使用的小巴券列表 条件:未过期，未使用过,且满足平台、驾校、教练规则
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
		int num=suserService.getCanUseCoinnum(coachid,studentid);//获取可用小巴币
		int numForSchool=suserService.getCanUseCoinnumForDriveSchool(coachid,studentid);//获取驾校可用小巴币
		num+=numForSchool;
		SuserInfo suser=suserService.getUserById(studentid);//获取余额
		if(suser==null){
				resultMap.put(Constant.CODE, 2);
				resultMap.put(Constant.MESSAGE, "根据studentid查询不到此学员");
				return;
		}
		int coinnum=0;
		if(suser.getFcoinnum()==null){
			suser.setFcoinnum(new BigDecimal(0));
		}
		if(num>=suser.getFcoinnum().intValue()){//可用余额减去冻结小巴币
			coinnum=num-suser.getFcoinnum().intValue();
		}
		resultMap.put("couponlist", list);
		resultMap.put("coinnum", coinnum);//可用小巴币
		resultMap.put("money", suser.getMoney()==null?0:suser.getMoney());
		resultMap.put("canUseDiff", canUseDiff);
		resultMap.put("canUseMaxCount", canUseMaxCount);
	}



	/**
	 * 获取可以使用的小巴币个数
	 *
	 * @param request
	 * @throws ErrException
	 */
	public void getCanUseCoinSum(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String studentid = getRequestParamter(request, "studentid");// 学员ID
		String coachid = getRequestParamter(request, "coachid");// 预订的教练ID
		CommonUtils.validateEmpty(studentid);
		CommonUtils.validateEmpty(coachid);
		//对某个教练能使用的小巴币
		int coinnumForCoach = suserService.getCanUseCoinnum(coachid,studentid);
		//对某个驾校下所有教练能使用的小巴币
		int coinnumForSchool = suserService.getCanUseCoinnumForDriveSchool(coachid,studentid);
		resultMap.put("coinnum", coinnumForCoach+coinnumForSchool);
	}

}
