package com.daoshun.guangda.servlet;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.daoshun.common.CommonUtils;
import com.daoshun.common.Constant;
import com.daoshun.exception.NullParameterException;
import com.daoshun.guangda.pojo.AdvertisementInfo;
import com.daoshun.guangda.pojo.CuserInfo;
import com.daoshun.guangda.pojo.SuserInfo;
import com.daoshun.guangda.pojo.SystemSetInfo;
import com.daoshun.guangda.service.IAdvertisementService;
import com.daoshun.guangda.service.ICUserService;
import com.daoshun.guangda.service.IRecommendService;
import com.daoshun.guangda.service.ISUserService;
import com.daoshun.guangda.service.ISystemService;

/**
*
* @author wjr
*
*/
@WebServlet("/adver")
public class AdvertisementServlet  extends BaseServlet{

	private IAdvertisementService advertisementService;
	private ISUserService suserService;
	private ICUserService cuserService;
	private ISystemService systemService;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		advertisementService=(IAdvertisementService) applicationContext.getBean("advertisementService");
		suserService = (ISUserService) applicationContext.getBean("suserService");
		cuserService = (ICUserService) applicationContext.getBean("cuserService");
		systemService = (ISystemService) applicationContext.getBean("systemService");
	}
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		try
		{
			String action=getAction(request);
//			if(action.equals(Constant.GETADVERTISEMENT))
//			{
//				if (!checkSession(request, action, resultMap)) {
//					setResult(response, resultMap);
//					return;
//				}
//			}
			if(action.equals(Constant.GETADVERTISEMENT))
			{
				getAdvertiesementcontent(request,resultMap);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			setResultWhenException(response, ex.getMessage());
		}
		setResult(response, resultMap);
	}
	
	private boolean checkSession(HttpServletRequest request, String action, HashMap<String, Object> resultMap) throws NullParameterException {
		String userid = "";// 1.教练 2.学员
		String usertype = "";

		if (Constant.GETNOTICES.equals(action)) {
			// 获取通知列表
			userid = getRequestParamter(request, "studentid");
			usertype = "2";
		} else if (Constant.DELNOTICE.equals(action)) {
			// 删除通知
			userid = getRequestParamter(request, "studentid");
			usertype = "2";
		} else if (Constant.READNOTICE.equals(action)) {
			// 设置已读通知
			userid = getRequestParamter(request, "studentid");
			usertype = "2";
		} else if (Constant.GETMESSAGECOUNT.equals(action)) {
			// 获取未读消息条数
			userid = getRequestParamter(request, "studentid");
			usertype = "2";
		} else if (Constant.FEEDBACK.equals(action)) {
			// 意见反馈
			userid = getRequestParamter(request, "studentid");
			usertype = getRequestParamter(request, "type");
		} else if(action.equals(Constant.CGETRECOMMENDLIST)){
			userid = getRequestParamter(request, "coachid");
			usertype = "1";
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

   private void getAdvertiesementcontent(HttpServletRequest request, HashMap<String, Object> resultMap)
   {
	 /*  String id=getRequestParamter(request, "id");
	   String type=getRequestParamter(request, "type");
	   if(advertisementService.jugeFlag(type, id)==0)
	   {
		   AdvertisementInfo resultpojo=advertisementService.getAdvertisementcontent();
		   resultMap.put("advertisement",resultpojo);
		   resultMap.put("isdiplay",0);
	   }
	   else
	   {
		   AdvertisementInfo resultpojo=new  AdvertisementInfo();
		   resultMap.put("advertisement",resultpojo);
		   resultMap.put("isdiplay",1);
	   }*/
	   SystemSetInfo resultpojo=advertisementService.getAdvertisementcontent();
	   
	   resultMap.put("c_img",resultpojo.getCoach_advertisement_url());
	   resultMap.put("s_img",resultpojo.getStudent_advertisement_url());
	   resultMap.put("c_url","http://www.xiaobaxueche.com/");
	   resultMap.put("s_url","http://www.xiaobaxueche.com/");
	   resultMap.put("curldisplay",resultpojo.getCoach_advertisement_flag());
	   resultMap.put("surldisplay",resultpojo.getStudent_advertisement_flag());
   }
}
