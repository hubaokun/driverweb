package com.daoshun.guangda.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import com.daoshun.guangda.pojo.AppCuserInfo;
import com.daoshun.guangda.pojo.CouponRecord;
import com.daoshun.guangda.pojo.CscheduleInfo;
import com.daoshun.guangda.pojo.CuserInfo;
import com.daoshun.guangda.pojo.DriveSchoolInfo;
import com.daoshun.guangda.pojo.ModelPrice;
import com.daoshun.guangda.pojo.ModelPriceVo;
import com.daoshun.guangda.pojo.SuserInfo;
import com.daoshun.guangda.pojo.SystemSetInfo;
import com.daoshun.guangda.service.ICUserService;
import com.daoshun.guangda.service.ICscheduleService;
import com.daoshun.guangda.service.IDriveSchoolService;
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
			}else if (Constant.BOOKCOACH.equals(action)) {
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
			} else if (Constant.REMINDCOACH.equals(action)) {
				remindCoach(request, resultMap);
			}else if (Constant.GETMODELPRICE.equals(action)) {
				//城市套餐价格
				getmodelprice(request, resultMap);
			}else if (Constant.GETOPENMODELPRICE.equals(action)) {
				//城市套餐价格
				getOpenModelPrice(request, resultMap);
			}else if (Constant.GETDRIVERSCHOOLBYCITYNAME.equals(action)) {
				//按城市ID查询驾校信息
				getDriveschoolByCityName(request, resultMap);
			}/*else if ("COIN".equals(action)) {
				coin(request, resultMap);
			}*/else {
				throw new ErrException();
			}

			recordUserAction(request, action);
		} catch (Exception e) {
			e.printStackTrace();
			setResultWhenException(response, e.getMessage());
		}
		setResult(response, resultMap);
	}
	/*public void coin(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		sbookService.getCoinException();
	}*/
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
						resultMap.put(Constant.CODE, 96);
						resultMap.put(Constant.MESSAGE, "您的登录信息已经过期,请重新登录.");
						return false;
						//return true;
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
				/*else if (CommonUtils.isEmptyString(token))
				{
					resultMap.put(Constant.CODE, -1);
					resultMap.put(Constant.MESSAGE, "您必须升级才能下订单!");
					return false;
				}*/


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
					resultMap.put(Constant.CODE, 96);
					resultMap.put(Constant.MESSAGE, "您的登录信息已经过期,请重新登录.");
					return false;
					//return true;
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
//			userid = getRequestParamter(request, "studentid");
//			usertype = "2";
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
	/**
	 * 城市套餐价格
	 * @param request
	 * @param resultMap
	 * @throws ErrException
	 */
	public void getmodelprice(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException{
		String cityid = getRequestParamter(request, "cityid");
		List<ModelPrice> list=sbookService.getModelPriceByCityId(CommonUtils.parseInt(cityid, 0));
		List<ModelPriceVo> mpvlist=null;
		if(list!=null && list.size()>0){
			mpvlist=new ArrayList<ModelPriceVo>();
			ModelPrice mp=list.get(0);
			if(mp.getC1xiaobaprice()==null){
				mp.setC1xiaobaprice(0);
			}
			if(mp.getC1xiaobaprice()!=0){
				ModelPriceVo mpv1=new ModelPriceVo();
				mpv1.setName("c1");
				mpv1.setXiaobaprice(mp.getC1xiaobaprice());
				mpv1.setMarketprice(mp.getC1marketprice());
				mpvlist.add(mpv1);
			}
			
			if(mp.getC2xiaobaprice()==null){
				mp.setC2xiaobaprice(0);
			}
			if(mp.getC2xiaobaprice()!=0){
				ModelPriceVo mpv2=new ModelPriceVo();
				mpv2.setName("c2");
				mpv2.setXiaobaprice(mp.getC2xiaobaprice());
				mpv2.setMarketprice(mp.getC2marketprice());
				mpvlist.add(mpv2);
			}
			
		}
		resultMap.put("modelprice", mpvlist);
	}
	//获取已开通套餐价格城市列表
	public void getOpenModelPrice(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException{
		List<ModelPrice> list=sbookService.getOpenModelPrice();
		resultMap.put("opencity", list);
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
	/**
	 * 提醒教练开课
	 * @param request
	 * @param resultMap
	 * @throws ErrException
	 */
	public void remindCoach(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException{
		String coachid = getRequestParamter(request, "coachid");
		String studentid = getRequestParamter(request, "studentid");
		String date = getRequestParamter(request, "date");
		sbookService.remindCoach(coachid,studentid,date);
		
	}
	public void refreshCoachSchedule(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String coachid = getRequestParamter(request, "coachid");
		String studentid = getRequestParamter(request, "studentid");
		String date = getRequestParamter(request, "date");
		String scheduletype = getRequestParamter(request, "scheduletype");
		CommonUtils.validateEmpty(coachid);
		CommonUtils.validateEmpty(date);
		CommonUtils.validateEmpty(scheduletype);
		Date fdate=CommonUtils.getDateFormat(date, "yyyy-MM-dd");
		int remindstate=sbookService.getRemindState(coachid,studentid,date);
		int coachstate=sbookService.getCoachState(coachid, 1,fdate,5,23,0);
		List<CscheduleInfo> datelist = sbookService.refreshCoachScheduleNew(coachid, date, studentid,scheduletype);
		
		resultMap.put("remindstate", remindstate);//提醒状态 1 已提醒过,  0 未提醒
		resultMap.put("coachstate", coachstate);//教练在当天的开课状态  1 开课， 0 休息
		resultMap.put("datelist", datelist);
	}

	public void getNearByCoach(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String pointcenter = getRequestParamter(request, "pointcenter");
		String radius = getRequestParamter(request, "radius");
		CommonUtils.validateEmpty(pointcenter);
		CommonUtils.validateEmpty(radius);
		String condition1 = getRequestParamter(request, "condition1");// 关键字
		if(condition1!=null){
			condition1=condition1.replaceAll("'", "");
		}
		String condition2 = getRequestParamter(request, "condition2");// 星级的下限
		String condition3 = getRequestParamter(request, "condition3");// 时间的下限
		String condition4 = getRequestParamter(request, "condition4");// 时间的上限
		String condition5 = getRequestParamter(request, "condition5");// 性别 1.男 2.女 0.不限
		String condition6 = getRequestParamter(request, "condition6");// 教练教学科目ID
		String condition8 = getRequestParamter(request, "condition8");// 价格下限
		String condition9 = getRequestParamter(request, "condition9");// 价格上限
		String condition10 = getRequestParamter(request, "condition10");// 车型 0.表示不限
		String condition11 = getRequestParamter(request, "condition11");// 准教车型
		String studentid = getRequestParamter(request, "studentid");//学员ID
		String cityid = getRequestParamter(request, "cityid");//城市ID 
		String driverschoolid = getRequestParamter(request, "driverschoolid");//驾校id
		String fixedposition =getRequestParamter(request, "fixedposition");//定位的城市名称
		List<AppCuserInfo> coachlist = sbookService.getNearByCoach2(cityid,pointcenter, radius, condition1, 
												condition2, condition3, condition4, condition5, condition6, condition8, condition9, condition10,
												condition11,studentid,driverschoolid,fixedposition);
		
			/*if(studentid==null || !"18".equals(studentid)){
				for (AppCuserInfo cuserInfo : coachlist) {
					if(cuserInfo.getCoachid()==13){
						coachlist.remove(cuserInfo);
						break;
					}
				}
			}*/
		resultMap.put("coachlist", coachlist);
	}
	
	public void getCoachList(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String pagenum = getRequestParamter(request, "pagenum");
		String condition1 = getRequestParamter(request, "condition1");// 关键字:教练名称/驾校名称/教练手机号
		if(condition1!=null){
			condition1=condition1.replaceAll("'", "");
		}
		String condition2 = getRequestParamter(request, "condition2");// 星级的下限
		String condition3 = getRequestParamter(request, "condition3");// 时间的下限
		String condition4 = getRequestParamter(request, "condition4");// 时间的上限
		String condition5 = getRequestParamter(request, "condition5");// 性别 1.男 2.女 0.不限
		String condition6 = getRequestParamter(request, "condition6");// 教练教学科目ID
		String condition8 = getRequestParamter(request, "condition8");// 价格下限
		String condition9 = getRequestParamter(request, "condition9");// 价格上限
		String condition10 = getRequestParamter(request, "condition10");// 车型 0.表示不限
		String condition11 = getRequestParamter(request, "condition11");// 准教车型
		
		//接收经纬度和城市名称
		String longitude = getRequestParamter(request, "longitude");//经纬度
		String latitude = getRequestParamter(request, "latitude");
		String cityid = getRequestParamter(request, "cityid");//城市ID
		String studentid = getRequestParamter(request, "studentid");//driverschoolid
		String driverschoolid = getRequestParamter(request, "driverschoolid");//驾校
		String fixedposition =getRequestParamter(request, "fixedposition");//定位的城市名称
		/*CommonUtils.validateEmpty(longitude);
		CommonUtils.validateEmpty(latitude);
		CommonUtils.validateEmpty(cityid);*/
		
		//HashMap<String, Object> result = sbookService.getCoachList(condition1, condition2, condition3, condition4, condition5, condition6, condition8, condition9, condition10, condition11, pagenum);
		HashMap<String, Object> result=new HashMap<String, Object>();
		if(condition11!=null && condition11.equals("19"))
		{
			result = sbookService.getCoachListAccompany(cityid,pagenum,fixedposition);
		}
		else
		{
			result = sbookService.getCoachList3(cityid,condition1, condition2, condition3, condition4, condition5, condition6,
																		condition8, condition9, condition10, condition11, pagenum,studentid,driverschoolid,fixedposition);
			List<AppCuserInfo> list=(List<AppCuserInfo>) result.get("coachlist");
			/*if(studentid==null || !"18".equals(studentid)){
				for (AppCuserInfo cuserInfo : list) {
					if(cuserInfo.getCoachid()==13){
						list.remove(cuserInfo);
						break;
					}
				}
			}*/
		}
		resultMap.putAll(result);
	}
	public void getDriveschoolByCityName(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String cityname = getRequestParamter(request, "cityname");//城市ID
		if (cityname == null || "".equals(cityname)) {
			resultMap.put("code", 2);
			resultMap.put("message","无法定位到你所在的城市!请检查手机的GPS是否开启");
			return;
		}
		//CommonUtils.validateEmptytoMsg(cityname, "无法定位到你所在的城市!请检查手机的GPS是否开启");
		List<DriveSchoolInfo> dslist=driveSchoolService.getDriveschoolByCityName(cityname);
		resultMap.put("dslist", dslist);
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
			resultMap.putAll(sbookService.bookCoachNew(coachid, studentid, date));
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
	 * 获取可以使用的小巴币，小巴券，余额,其中小巴币的总数=教练可用小巴币+驾校可用小巴币+平台可用小巴币
	 * --获取可以使用的小巴券列表 条件:未过期，未使用过,且满足平台、驾校、教练规则
	 * 
	 * @param request
	 * @throws ErrException
	 */
	public void getCanUseCouponList(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String studentid = getRequestParamter(request, "studentid");// 学员ID
		String coachid = getRequestParamter(request, "coachid");// 预订的教练ID
		String modelid = getRequestParamter(request, "modelid");// 预订的教练ID
		CommonUtils.validateEmpty(studentid);
		CommonUtils.validateEmpty(coachid);
		CommonUtils.validateEmpty(modelid);
		List<CouponRecord> list=new ArrayList<CouponRecord>();
		if(!modelid.equals("19"))
		    list = sbookService.getCanUseCouponList(studentid, coachid);

		int canUseDiff = 0;
		int canUseMaxCount = 1;

		SystemSetInfo info = sbookService.getSystemSetInfo();
		if (info != null) {
			if (info.getCan_use_diff_coupon() != null && info.getCan_use_diff_coupon() != 0)
				canUseDiff = info.getCan_use_diff_coupon();

			if (info.getCan_use_coupon_count() != null && info.getCan_use_coupon_count() != 0)
				canUseMaxCount = info.getCan_use_coupon_count();
		}
		//可用的小巴币的总数=教练可用小巴币+驾校可用小巴币+平台可用小巴币
		int num=suserService.getCanUseCoinnum(coachid,studentid);//获取教练可用小巴币
		int numForSchool=suserService.getCoinnumForDriveSchool(studentid,coachid);//获取驾校可用小巴币
		//获取平台发送的小巴币
		int numForPlatform=suserService.getCanUseCoinnumForPlatform("0",studentid);//获取平台可用小巴币
		num+=numForSchool;
		num+=numForPlatform;
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
		if(num>=suser.getFcoinnum().intValue()){//可用小巴币减去冻结小巴币
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
		int coinnumForSchool = suserService.getCanUseCoinnumForDriveSchool(studentid);
		resultMap.put("coinnum", coinnumForCoach+coinnumForSchool);
	}

}
