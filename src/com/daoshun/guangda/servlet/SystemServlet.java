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
import com.daoshun.common.DeviceType;
import com.daoshun.common.ErrException;
import com.daoshun.guangda.pojo.CuserInfo;
import com.daoshun.guangda.pojo.SuserInfo;
import com.daoshun.guangda.pojo.SystemSetInfo;
import com.daoshun.guangda.pojo.UserPushInfo;
import com.daoshun.guangda.service.ICUserService;
import com.daoshun.guangda.service.ISUserService;
import com.daoshun.guangda.service.ISystemService;

@WebServlet("/system")
public class SystemServlet extends BaseServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3512442711896048924L;

	private ISystemService systemService;
	private ICUserService cuserService;
	private ISUserService suserService;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		systemService = (ISystemService) applicationContext.getBean("systemService");
		cuserService = (ICUserService) applicationContext.getBean("cuserService");
		suserService = (ISUserService) applicationContext.getBean("suserService");
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String action = getAction(request);

			if (Constant.SUPDATEPUSHINFO.equals(action) || Constant.CREFRESHUSERMONEY.equals(action)) {
				if (!checkSession(request, action, resultMap)) {
					setResult(response, resultMap);
					return;
				}
			}

			if (Constant.SUPDATEPUSHINFO.equals(action)) {
				updatePushInfo(request, resultMap);
			} else if (Constant.CREFRESHUSERMONEY.equals(action)) {
				refreshUserMoney(request, resultMap);
			} else if (Constant.UPDATEUSERLOCATION.equals(action)) {
				updateUserLocation(request, resultMap);
			} else if (Constant.CHECKVERSION.equals(action)) {
				checkVersion(request, resultMap);
			} else if (Constant.CHECKCONFIG.equals(action)) {
				checkconfig(request, resultMap);
			} 
			else {
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
		String userid = getRequestParamter(request, "userid");// 1.教练 2.学员
		String usertype = getRequestParamter(request, "usertype");

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
		String userid = getRequestParamter(request, "userid");// 1.教练 2.学员
		String usertype = getRequestParamter(request, "usertype");
		if (!CommonUtils.isEmptyString(userid) && !CommonUtils.isEmptyString(usertype)) {
			systemService.recordUserAction(userid, usertype, "system", action);
		}
	}

	/**
	 * @param request
	 * @throws ErrException
	 */
	public void updatePushInfo(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String userid = getRequestParamter(request, "userid");
		String usertype = getRequestParamter(request, "usertype");
		String devicetype = getRequestParamter(request, "devicetype");
		String jpushid = getRequestParamter(request, "jpushid");
		String devicetoken = getRequestParamter(request, "devicetoken");
		CommonUtils.validateEmpty(userid);
		CommonUtils.validateEmpty(usertype);
		CommonUtils.validateEmpty(devicetype);
		
		int dtype = CommonUtils.parseInt(devicetype, 0);// 2:安卓 1:IOS
		
		List<UserPushInfo> ups = systemService.getUserPushInfosBykeyword(CommonUtils.parseInt(userid, 0), CommonUtils.parseInt(usertype, 0));
		
		UserPushInfo userPushInfo = null;
		
		for (UserPushInfo upi : ups) {
			if(upi.getType().intValue()==dtype){
				if(dtype==DeviceType.ANDROID ){
					if(!upi.getJpushid().equals(jpushid)){
						cuserService.delObject(upi);//如果纪录与当前登录设备不符合则删除
					}else{
						userPushInfo = upi;
					}
				}else{//ios
					if(!upi.getDevicetoken().equals(devicetoken)){
						cuserService.delObject(upi);//如果纪录与当前登录设备不符合则删除
					}else{
						userPushInfo = upi;
					}
				}
			}else{
				cuserService.delObject(upi);//如果纪录与当前登录设备不符合则删除
			}
		}

		if(userPushInfo==null){
			if (dtype == DeviceType.ANDROID && !CommonUtils.isEmptyString(jpushid)) {
				UserPushInfo newuserPush = new UserPushInfo();
				newuserPush.setDevicetoken("");
				newuserPush.setJpushid(jpushid);
				newuserPush.setType(DeviceType.ANDROID);
				newuserPush.setUserid(CommonUtils.parseInt(userid, 0));
				newuserPush.setUsertype(CommonUtils.parseInt(usertype, 0));
				cuserService.addObject(newuserPush);
			} else if (dtype == DeviceType.IOS && !CommonUtils.isEmptyString(devicetoken)) {
				UserPushInfo newuserPush = new UserPushInfo();
				newuserPush.setDevicetoken(devicetoken);
				newuserPush.setJpushid("");
				newuserPush.setType(DeviceType.IOS);
				newuserPush.setUserid(CommonUtils.parseInt(userid, 0));
				newuserPush.setUsertype(CommonUtils.parseInt(usertype, 0));
				cuserService.addObject(newuserPush);
			}
		}
	}

	public void refreshUserMoney(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String userid = getRequestParamter(request, "userid");
		String usertype = getRequestParamter(request, "usertype");
		CommonUtils.validateEmpty(userid);
		CommonUtils.validateEmpty(usertype);
		//int coinnum=suserService.getSumCoinnum(userid);
		if (CommonUtils.parseInt(usertype, 0) == 1) {// 教练
			CuserInfo cuser = cuserService.getCuserByCoachid(userid);
			if(cuser==null){
				resultMap.put(Constant.CODE, 2);
				resultMap.put(Constant.MESSAGE, "教练找不到");
				return;
			}
			if(cuser.getCoinnum()==null){
				cuser.setCoinnum(0);
			}
			if(cuser.getMoney()==null){
				cuser.setMoney(new BigDecimal(0));
			}
			if(cuser.getFmoney()==null){
				cuser.setFmoney(new BigDecimal(0));
			}
			int coinnum=cuser.getCoinnum();
			if (cuser != null) {
				resultMap.put("money", cuser.getMoney());
				resultMap.put("fmoney", cuser.getFmoney());
				resultMap.put("gmoney", cuser.getGmoney());
				resultMap.put("coinnum", coinnum);
				// 这里需要把教练总共的小巴券时间返回
				int allhour = cuserService.getCoachAllCouponTime(cuser.getCoachid());
				resultMap.put("couponhour", allhour);
			}
		} else {
			SuserInfo suser = suserService.getUserById(userid);
			if(suser.getCoinnum()==null){
				suser.setCoinnum(0);
			}
			if(suser.getMoney()==null){
				suser.setMoney(new BigDecimal(0));
			}
			if(suser.getFmoney()==null){
				suser.setFmoney(new BigDecimal(0));
			}
			if (suser != null) {// 学员
				resultMap.put("money", suser.getMoney());
				resultMap.put("fmoney", suser.getFmoney());
				resultMap.put("coinnum", suser.getCoinnum());
				resultMap.put("gmoney", 0);
			}else{
					resultMap.put(Constant.CODE, 2);
					resultMap.put(Constant.MESSAGE, "学员找不到");
					return;
				
			}
		}
	}

	public void updateUserLocation(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String openid = getRequestParamter(request, "openid");
		String devicetype = getRequestParamter(request, "devicetype");
		String usertype = getRequestParamter(request, "usertype");
		String appversion = getRequestParamter(request, "appversion");
		String province = getRequestParamter(request, "province");
		String city = getRequestParamter(request, "city");
		String area = getRequestParamter(request, "area");

		CommonUtils.validateEmpty(openid);
		CommonUtils.validateEmpty(devicetype);
		CommonUtils.validateEmpty(usertype);
		CommonUtils.validateEmpty(appversion);

		systemService.updateUserLocation(openid, devicetype, usertype, appversion, province, city, area);
	}

	public void checkconfig(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String userid = getRequestParamter(request, "userid");
		String usertype = getRequestParamter(request, "usertype");
		String devicetype = getRequestParamter(request, "devicetype");
		String version = getRequestParamter(request, "version");
		SystemSetInfo sinf= systemService.getSystemSet();
		resultMap.put("config", sinf);
		
//		HashMap<String, Object> result = 
	}



	public void checkVersion(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String userid = getRequestParamter(request, "userid");
		String usertype = getRequestParamter(request, "usertype");
		String devicetype = getRequestParamter(request, "devicetype");
		String version = getRequestParamter(request, "version");

		HashMap<String, Object> result = systemService.checkVersion(userid, usertype, devicetype, version);
	}
}
