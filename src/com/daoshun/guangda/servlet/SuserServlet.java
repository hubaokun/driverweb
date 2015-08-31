package com.daoshun.guangda.servlet;

import java.io.IOException;
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
import com.daoshun.guangda.pojo.AreaInfo;
import com.daoshun.guangda.pojo.CityInfo;
import com.daoshun.guangda.pojo.CuserInfo;
import com.daoshun.guangda.pojo.ProvinceInfo;
import com.daoshun.guangda.pojo.RecommendInfo;
import com.daoshun.guangda.pojo.SuserInfo;
import com.daoshun.guangda.pojo.SystemSetInfo;
import com.daoshun.guangda.pojo.VerifyCodeInfo;
import com.daoshun.guangda.service.IBaseService;
import com.daoshun.guangda.service.ILocationService;
import com.daoshun.guangda.service.IRecommendService;
import com.daoshun.guangda.service.ISUserService;
import com.daoshun.guangda.service.ISystemService;

@WebServlet("/suser")
public class SuserServlet extends BaseServlet {

	private static final long serialVersionUID = 6998440419757851253L;
	private ISUserService suserService;
	private IBaseService baseService;
	private ISystemService systemService;
	private ILocationService locationService;
	private IRecommendService recommendService;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		suserService = (ISUserService) applicationContext.getBean("suserService");
		baseService = (IBaseService) applicationContext.getBean("baseService");
		systemService = (ISystemService) applicationContext.getBean("systemService");
		locationService = (ILocationService) applicationContext.getBean("locationService");
		recommendService=(IRecommendService) applicationContext.getBean("recommendService");
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String action = getAction(request);

			if (Constant.PERFECTACCOUNTINFO.equals(action) || Constant.CHANGEAVATAR.equals(action) || Constant.PERFECTSTUDENTINFO.equals(action) || Constant.PERFECTPERSONINFO.equals(action)
					|| Constant.GETMYBALANCEINFO.equals(action) || Constant.APPLYCASH.equals(action)  || Constant.GETSTUDENTWALLETINFO.equals(action) || Constant.GETSTUDENTCOINRECORDLIST.equals(action) || Constant.GETSTUDENTCOUPONLIST.equals(action)
					||Constant.SENROLL.equals(action)|| Constant.RECHARGE.equals(action)) {
				if (!checkSession(request, action, resultMap)) {
					setResult(response, resultMap);
					return;
				}
			}

			if (Constant.GETVERCODE.equals(action)) {
				// 获取验证码
				getVerCode(request, resultMap);
			} else if (Constant.SLOGIN.equals(action)) {
				// 登录
				login(request, resultMap);
				
			} else if (Constant.SENROLL.equals(action)) {
				//普通报名
				enroll(request, resultMap);
			} else if (Constant.ThirdLogin.equals(action)) {// 第三方登录
				thirdLogin(request, resultMap);
			} else if (Constant.BINDACCOUNT.equals(action)) {
				// 绑定已有账号
				bindAccount(request, resultMap);
			} else if (Constant.PERFECTACCOUNTINFO.equals(action)) {
				// 完善账号信息
				perfectAccountInfo(request, resultMap);
			} else if (Constant.CHANGEAVATAR.equals(action)) {
				// 修改头像
				changeAvatar(request, resultMap);
			} else if (Constant.PERFECTSTUDENTINFO.equals(action)) {
				// 完善学员学驾信息
				perfectStudentInfo(request, resultMap);
			} else if (Constant.PERFECTPERSONINFO.equals(action)) {
				// 完善个人资料
				perfectPersonInfo(request, resultMap);
			} else if (Constant.GETMYBALANCEINFO.equals(action)) {
				// 获取账户余额信息
				getMyBalanceInfo(request, resultMap);
			} else if (Constant.APPLYCASH.equals(action)) {
				// 申请提现
				applyCash(request, resultMap);
			} else if (Constant.RECHARGE.equals(action)) {
				// 账户充值
				recharge(request, resultMap);
			} else if (Constant.PROMOENROLL.equals(action)) {
				// 促销报名
				promoEnroll(request, resultMap);
			} else if (Constant.promoEnrollCallback.equals(action)) {
				promoEnrollCallback(request, response);
				return;
			}else if (Constant.GETSTUDENTWALLETINFO.equals(action)) {
				// 
				getWalletInfo(request, resultMap);
			}
			else if (Constant.GETSTUDENTCOINRECORDLIST.equals(action)) {
				// 账户充值
				getMyCoinRecord(request, resultMap);
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
		String userid = "";
		if (Constant.PERFECTACCOUNTINFO.equals(action)) {
			userid = getRequestParamter(request, "studentid");
		} else if (Constant.CHANGEAVATAR.equals(action)) {
			userid = getRequestParamter(request, "studentid");
		} else if (Constant.PERFECTSTUDENTINFO.equals(action)) {
			userid = getRequestParamter(request, "studentid");
		} else if (Constant.PERFECTPERSONINFO.equals(action)) {
			userid = getRequestParamter(request, "studentid");
		} else if (Constant.GETMYBALANCEINFO.equals(action)) {
			userid = getRequestParamter(request, "studentid");
		} else if (Constant.APPLYCASH.equals(action)) {
			userid = getRequestParamter(request, "studentid");
		} else if (Constant.RECHARGE.equals(action)) {
			userid = getRequestParamter(request, "studentid");
		}
		else if (Constant.GETSTUDENTWALLETINFO.equals(action)||Constant.GETSTUDENTCOINRECORDLIST.equals(action)) {
			userid = getRequestParamter(request, "studentid");
		}
		else if (Constant.SENROLL.equals(action)) {
			userid = getRequestParamter(request, "studentid");
			}

		if (!CommonUtils.isEmptyString(userid)) {
			SuserInfo suser = suserService.getUserById(userid);
			if (suser != null) {
				String token = getRequestParamter(request, "token");
				if (!CommonUtils.isEmptyString(token)) {
					// 时间获取3
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
//							System.out.print("SuserServlet checkSession-----1111111111");
							resultMap.put(Constant.MESSAGE, "您的登录信息已经过期,请重新登录.");
							return false;
						} else {
							return true;
						}
					} else {
						resultMap.put(Constant.CODE, 95);
//						System.out.print("SuserServlet checkSession-----222222222");
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
		String userid = "";
		String usertype = "";

		if (Constant.GETVERCODE.equals(action)) {
			usertype = getRequestParamter(request, "type");
			String phone = getRequestParamter(request, "phone");
			if (!CommonUtils.isEmptyString(phone)) {
				if ("1".equals(usertype)) {// 教练
					CuserInfo cuser = suserService.getCoachByPhone(phone);
					if (cuser != null) {
						userid = String.valueOf(cuser.getCoachid());
					}
				} else {
					SuserInfo suser = suserService.getUserByPhone(phone);
					if (suser != null) {
						userid = String.valueOf(suser.getStudentid());
					}
				}
			}

		} else if (Constant.SLOGIN.equals(action)) {
			String phone = getRequestParamter(request, "phone");
			if (!CommonUtils.isEmptyString(phone)) {
				SuserInfo suser = suserService.getUserByPhone(phone);
				if (suser != null) {
					userid = String.valueOf(suser.getStudentid());
				}
			}
			usertype = "2";
		} else if (Constant.ThirdLogin.equals(action)) {// 第三方登录
			String openid = getRequestParamter(request, "openid");// openID
			String type = getRequestParamter(request, "type");// 类型：1.QQ登录 2.微信登录 3.微博登录

			SuserInfo user = null;
			if (CommonUtils.parseInt(type, 0) == 1) {
				user = suserService.getUserByqq(openid);
			} else if (CommonUtils.parseInt(type, 0) == 2) {
				user = suserService.getUserBywx(openid);
			} else {
				user = suserService.getUserBywb(openid);
			}

			if (user != null) {
				userid = String.valueOf(user.getStudentid());
			}
			usertype = "2";
		} else if (Constant.BINDACCOUNT.equals(action)) {
			String phone = getRequestParamter(request, "phone");
			if (!CommonUtils.isEmptyString(phone)) {
				SuserInfo suser = suserService.getUserByPhone(phone);
				if (suser != null) {
					userid = String.valueOf(suser.getStudentid());
				}
			}
			usertype = "2";
		} else if (Constant.PERFECTACCOUNTINFO.equals(action)) {
			userid = getRequestParamter(request, "studentid");
			usertype = "2";
		} else if (Constant.CHANGEAVATAR.equals(action)) {
			userid = getRequestParamter(request, "studentid");
			usertype = "2";
		} else if (Constant.PERFECTSTUDENTINFO.equals(action)) {
			userid = getRequestParamter(request, "studentid");
			usertype = "2";
		} else if (Constant.PERFECTPERSONINFO.equals(action)) {
			userid = getRequestParamter(request, "studentid");
			usertype = "2";
		} else if (Constant.GETMYBALANCEINFO.equals(action)) {
			userid = getRequestParamter(request, "studentid");
			usertype = "2";
		} else if (Constant.APPLYCASH.equals(action)) {
			userid = getRequestParamter(request, "studentid");
			usertype = "2";
		} else if (Constant.RECHARGE.equals(action)) {
			userid = getRequestParamter(request, "studentid");
			usertype = "2";
		}

		if (!CommonUtils.isEmptyString(userid) && !CommonUtils.isEmptyString(usertype)) {
			systemService.recordUserAction(userid, usertype, "suser", action);
		}
	}
	/**
	 * 促销报名
	 * @param request
	 * @param resultMap
	 * @throws ErrException
	 */
	public void promoEnroll(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		try
		{
		String studentid = getRequestParamter(request, "studentid");
		String model = getRequestParamter(request, "model");//车型
		String cityid = getRequestParamter(request, "cityid");//车型
		String amount = getRequestParamter(request, "amount");// 报名金额
		CommonUtils.validateEmpty(studentid);
		CommonUtils.validateEmpty(amount);
		CommonUtils.validateEmpty(cityid);
		CommonUtils.validateEmpty(model);
		SuserInfo student = suserService.getUserById(studentid);
		student.setState(1);
		student.setEnrolltime(new Date());
		student.setModel(model);//设置车型c1,c2
		student.setModelcityid(CommonUtils.parseInt(cityid, 0));
		suserService.updateUserInfo(student);
		HashMap<String, Object> rechargeResult = suserService.promoRecharge(studentid, amount);
		resultMap.put("code", 1);
		resultMap.put("message", "报名成功");
		resultMap.putAll(rechargeResult);
		}catch(Exception ex)
		{
			resultMap.put("code", 2);
			resultMap.put("message", "报名失败");
		}
	}
	//促销报名支付回调，由支付宝服务器调用
	public void promoEnrollCallback(HttpServletRequest request, HttpServletResponse response){
		String out_trade_no = request.getParameter("out_trade_no");
		suserService.promoEnrollCallback(out_trade_no);
		String qs=request.getQueryString();
		String ru=request.getRequestURL().toString();
		System.out.println("qs:"+qs+"########ru"+ru);
		suserService.addAlipayCallBack(qs, ru);
		setAliPayResult(response);
	}
	public void register(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String phone = getRequestParamter(request, "phone");
		String realname = getRequestParamter(request, "realname");
		String password = getRequestParamter(request, "password");
		CommonUtils.validateEmpty(phone);
		CommonUtils.validateEmpty(realname);
		CommonUtils.validateEmpty(password);
		SuserInfo user = suserService.getUserByPhone(phone);
		if (user != null) {
			resultMap.put("code", 2);
			resultMap.put("message", "该手机号码已经注册");
			return;
		} else {

			String token = request.getSession().getId().toLowerCase();
			SuserInfo user1 = suserService.registerUser(phone, token);
			resultMap.put("UserInfo", user1);
		}
	}

	public void getVerCode(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String phone = getRequestParamter(request, "phone");
		String type = getRequestParamter(request, "type");
		CommonUtils.validateEmpty(phone);
		CommonUtils.validateEmpty(type);
		if(phone.equals("18888888888")){
			resultMap.put("code", 1);
			resultMap.put("message", "请使用默认验证码");
			return ;
		}
		int result = suserService.sendMessageCode(phone, type);
		if (result == 1) {
			resultMap.put("code", 1);
			resultMap.put("message", "验证码发送成功");
		} else {
			resultMap.put("code", 2);
			resultMap.put("message", "验证码发送失败");
		}
	}

	public void verificationCode(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String phone = getRequestParamter(request, "phone");
		String type = getRequestParamter(request, "type");
		String code = getRequestParamter(request, "code");
		CommonUtils.validateEmpty(phone);
		CommonUtils.validateEmpty(type);
		VerifyCodeInfo verifyCode = suserService.getVerifiCationByPhone(phone, type);
		if (verifyCode == null) {
			resultMap.put("code", 99);
			resultMap.put("message", "参数错误");
			return;
		}
		if (code != null) {
			if (!verifyCode.getCode().equals(code)) {
				resultMap.put("code", 1);
				resultMap.put("message", "验证码不正确");
				return;
			}
		}
	}

	public void login(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String phone = getRequestParamter(request, "phone");
		String password = getRequestParamter(request, "password");// 验证码
		String devicetype = getRequestParamter(request, "devicetype");//设备类型  1 IOS  2 ADNROID
		String version = getRequestParamter(request, "version");//版本
		CommonUtils.validateEmpty(phone);
		CommonUtils.validateEmpty(password);
		// 验证验证码的有效性
		int result = suserService.checkVerCode(phone, password);
		if (result == 1) {
			String token = request.getSession().getId().toLowerCase();
//			System.out.println("longin set token="+token+" "+Thread.currentThread().getId());

			SuserInfo user = suserService.getUserByPhone(phone);
			if (user == null) {
//				System.out.println("do not save token to db "+token);
				user = suserService.registerUser(phone, token);// 注册
				user.setPassword(password);
				
				resultMap.put("isregister", 1);
			} else {
				user.setToken(token);
				user.setToken_time(new Date());
				user.setInvitecode("S"+CommonUtils.getInviteCode(user.getPhone()));
				user.setPassword(password);
				resultMap.put("isregister", 0);
//				System.out.println("longin save token to db "+token+" "+Thread.currentThread().getId());
			}
			int dtype=CommonUtils.parseInt(devicetype, 0);
			if(dtype!=0){
				user.setDevicetype(dtype);//设置设备类型
			}
			if(version!=null && !"".equals(version)){
				user.setVersion(version);//设置版本号
			}
			
			suserService.updateUserInfo(user);
			//根据省市区ID查询对应的名称
			if(user.getProvinceid()!=null && user.getCityid()!=null && user.getAreaid()!=null){
				ProvinceInfo pro=locationService.getProvincesById(user.getProvinceid());
				CityInfo city=locationService.getCityById(user.getCityid());
				AreaInfo area=locationService.getAreaById(user.getAreaid());
				String locationname=pro.getProvince()+"-"+city.getCity()+"-"+area.getArea();
				user.setLocationname(locationname);
			}
			resultMap.put("UserInfo", user);
			int rflag=recommendService.checkRecommendinfo(String.valueOf(user.getStudentid()),2);
			int cflag=recommendService.isoversixhour(String.valueOf(user.getStudentid()),2);
			if(rflag==0 || cflag==0)
				//返回0代表已经存在记录了或者已经超过6个小时
				resultMap.put("isInvited", 0);
			else
				//返回1代表没有记录并且未超过6个小时
				resultMap.put("isInvited", 1);
		} else if (result == 0) {
			resultMap.put("code", 2);
			resultMap.put("message", "验证码错误,请重新输入");
		} else {
//			System.out.println("-您的登录信息已经过期,请重新获取验证码登录----:"+result);
			resultMap.put("code", 3);
			resultMap.put("message", "您的登录信息已经过期,请重新获取验证码登录");
		}
	}

	/**
	 * 第三方登录
	 * 
	 * @param request
	 * @throws ErrException
	 */
	public void thirdLogin(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String openid = getRequestParamter(request, "openid");// openID
		String type = getRequestParamter(request, "type");// 类型：1.QQ登录 2.微信登录 3.微博登录
		CommonUtils.validateEmpty(openid);
		CommonUtils.validateEmpty(type);

		SuserInfo user = null;
		if (CommonUtils.parseInt(type, 0) == 1) {
			user = suserService.getUserByqq(openid);
		} else if (CommonUtils.parseInt(type, 0) == 2) {
			user = suserService.getUserBywx(openid);
		} else {
			user = suserService.getUserBywb(openid);
		}

		if (user == null) {
			user = new SuserInfo();
			resultMap.put("isbind", 0);
		} else {
			resultMap.put("isbind", 1);
		}
		resultMap.put("UserInfo", user);
	}

	public void bindAccount(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String phone = getRequestParamter(request, "phone");
		String password = getRequestParamter(request, "password");
		String openid = getRequestParamter(request, "openid");
		String type = getRequestParamter(request, "type");
		CommonUtils.validateEmpty(phone);
		CommonUtils.validateEmpty(password);
		CommonUtils.validateEmpty(openid);
		CommonUtils.validateEmpty(type);

		// 首先验证验证码
		int result = suserService.checkVerCode(phone, password);
		if (result == 1) {
			SuserInfo user = suserService.getUserByPhone(phone);
			if (user == null) {
				String token = request.getSession().getId().toLowerCase();
				user = suserService.registerUser(phone, token);// 注册
				resultMap.put("isregister", 1);
			} else {
				resultMap.put("isregister", 0);
			}
			if (type.equals("1")) {
				user.setQq_openid(openid);
			} else if (type.equals("2")) {
				user.setWx_openid(openid);
			} else {
				user.setWb_openid(openid);
			}
			suserService.updateUserInfo(user);

			resultMap.put("UserInfo", user);
		} else if (result == 0) {
			resultMap.put("code", 2);
			resultMap.put("message", "验证码错误,请重新输入");
		} else {
			resultMap.put("code", 3);
			resultMap.put("message", "您的验证码已经过期,请重新获取");
		}
	}

	public void bindNewAccount(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String phone = getRequestParamter(request, "phone");
		String realname = getRequestParamter(request, "realname");
		String password = getRequestParamter(request, "password");
		String openid = getRequestParamter(request, "openid");
		String type = getRequestParamter(request, "type");
		CommonUtils.validateEmpty(phone);
		CommonUtils.validateEmpty(password);
		CommonUtils.validateEmpty(openid);
		CommonUtils.validateEmpty(type);
		SuserInfo user = suserService.getUserByPhone(phone);
		if (user != null) {
			resultMap.put("code", 2);
			resultMap.put("message", "该手机号码已经注册");
			return;
		} else {
			SuserInfo user1 = new SuserInfo();
			user1.setPhone(phone);
			if (realname != null) {
				user1.setRealname(realname);
			}
			if (type.equals(1)) {
				user1.setQq_openid(openid);
			} else if (type.equals(2)) {
				user1.setWx_openid(openid);
			} else {
				user1.setWb_openid(openid);
			}
			user1.setPassword(password);
			user1.setAddtime(new Date());
			suserService.addSuserInfo(user1);
			resultMap.put("UserInfo", user1);
		}
	}

	public void perfectAccountInfo(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String studentid = getRequestParamter(request, "studentid");
		String realname = getRequestParamter(request, "realname");
		String phone = getRequestParamter(request, "phone");
		String provinceid = getRequestParamter(request, "provinceid");
		String cityid = getRequestParamter(request, "cityid");
		String areaid = getRequestParamter(request, "areaid");
		CommonUtils.validateEmpty(studentid);
		SuserInfo user = suserService.getUserById(studentid);
		if (user == null) {
			resultMap.put("code", 2);
			resultMap.put("message", "该用户不存在");
			return;
		} else {
			if (phone != null) {
				user.setSecondphone(phone);
			}
			if (realname != null) {
				user.setRealname(realname);
			}
			if (provinceid != null) {
				user.setProvinceid(provinceid);
			}
			if (cityid != null) {
				user.setCityid(cityid);
			}
			if (areaid != null) {
				user.setAreaid(areaid);
			}
			suserService.updateUserInfo(user);
			List<RecommendInfo> tempList=recommendService.getRecommondInviteInfoByCoachid(studentid, realname);
			RecommendInfo temp=recommendService.getRecommondInvitedInfoByCoachid(studentid, realname);
			if(tempList.size()!=0)
			{
				for(RecommendInfo r:tempList)
				{
					r.setStudentname(realname);
					recommendService.updateRecommendInfo(r);
				}
			}
			if(temp!=null)
			{
				temp.setInvitedpeoplename(realname);
				recommendService.updateRecommendInfo(temp);
			}
		}
	}

	public void changeAvatar(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String studentid = getRequestParamter(request, "studentid");
		CommonUtils.validateEmpty(studentid);
		String fileid = (String) request.getAttribute("avatar");
		String avatarurl = suserService.changeAvatar(studentid, fileid);
		resultMap.put("avatarurl", avatarurl);
	}

	public void perfectStudentInfo(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String studentid = getRequestParamter(request, "studentid");
		String idnum = getRequestParamter(request, "idnum");
		String studentcardnum = getRequestParamter(request, "studentcardnum");
		String scardyear = getRequestParamter(request, "scardyear");
		String cardpic1 = (String) request.getAttribute("cardpic1");
		String cardpic2 = (String) request.getAttribute("cardpic2");
		String cardpic3 = (String) request.getAttribute("cardpic3");
		String cardpic4 = (String) request.getAttribute("cardpic4");
		if (studentid != null) {
			SuserInfo student = suserService.getUserById(studentid);
			if (student == null) {
				resultMap.put("code", 2);
				resultMap.put("message", "该用户不存在");
				return;
			} else if (student.getState() == 1) {
				resultMap.put("code", 3);
				resultMap.put("message", "信息已审核通过,不能进行第二次修改");
				return;
			} else {
				if (idnum != null) {
					student.setId_cardnum(idnum);
				}
				if (studentcardnum != null) {
					student.setStudent_cardnum(studentcardnum);
				}
				if (scardyear != null) {
					student.setStudent_card_creat(scardyear);
				}
				if (cardpic1 != null) {
					student.setId_cardpicf(CommonUtils.parseInt(cardpic1, 0));
					resultMap.put("cardpic1url", baseService.getFilePathById(CommonUtils.parseInt(cardpic1, 0)));
				}
				if (cardpic2 != null) {
					student.setId_cardpicb(CommonUtils.parseInt(cardpic2, 0));
					resultMap.put("cardpic2url", baseService.getFilePathById(CommonUtils.parseInt(cardpic2, 0)));
				}
				if (cardpic3 != null) {
					student.setStudent_cardpicf(CommonUtils.parseInt(cardpic3, 0));
					resultMap.put("cardpic3url", baseService.getFilePathById(CommonUtils.parseInt(cardpic3, 0)));
				}
				if (cardpic4 != null) {
					student.setStudent_cardpicb(CommonUtils.parseInt(cardpic4, 0));
					resultMap.put("cardpic4url", baseService.getFilePathById(CommonUtils.parseInt(cardpic4, 0)));
				}
				suserService.updateUserInfo(student);
			}
		}

	}
	
	public void perfectPersonInfo(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String studentid = getRequestParamter(request, "studentid");
		String gender = getRequestParamter(request, "gender");
		String birthday = getRequestParamter(request, "birthday");
		String provinceid = getRequestParamter(request, "provinceid");//添加省市区 at 20150722
		String cityid = getRequestParamter(request, "cityid");
		String areaid = getRequestParamter(request, "areaid");
		String address = getRequestParamter(request, "address");
		String urgentperson = getRequestParamter(request, "urgentperson");
		String urgentphone = getRequestParamter(request, "urgentphone");
		CommonUtils.validateEmpty(studentid);
		SuserInfo student = suserService.getUserById(studentid);
		if (student == null) {
			resultMap.put("code", 2);
			resultMap.put("message", "该用户不存在");
			return;
		} else {
			if (gender != null) {
				student.setGender(CommonUtils.parseInt(gender, 0));
			}
			if (birthday != null) {
				student.setBirthday(birthday);
			}
			if (provinceid != null) {
				student.setProvinceid(provinceid);
			}
			if (cityid != null) {
				student.setCityid(cityid);
			}
			if (areaid != null) {
				student.setAreaid(areaid);
			}
			if (address != null) {
				student.setAddress(address);
			}
			if (urgentperson != null) {
				student.setUrgent_person(urgentperson);
			}
			if (urgentphone != null) {
				student.setUrgent_phone(urgentphone);
			}
			suserService.updateUserInfo(student);
		}
	}

	public void verifyPsw(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String studentid = getRequestParamter(request, "studentid");
		String password = getRequestParamter(request, "password");
		CommonUtils.validateEmpty(studentid);
		CommonUtils.validateEmpty(password);
		SuserInfo student = suserService.getUserById(studentid);
		if (student == null) {
			resultMap.put("code", 2);
			resultMap.put("message", "该用户不存在");
			return;
		} else if (!student.getPassword().equals(password)) {
			resultMap.put("code", 3);
			resultMap.put("message", "密码不正确");
			return;
		}
	}

	public void changePsw(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String studentid = getRequestParamter(request, "studentid");
		String password = getRequestParamter(request, "password");
		CommonUtils.validateEmpty(studentid);
		CommonUtils.validateEmpty(password);
		SuserInfo student = suserService.getUserById(studentid);
		if (student == null) {
			resultMap.put("code", 2);
			resultMap.put("message", "该用户不存在");
			return;
		} else {
			student.setPassword(password);
			suserService.updateUserInfo(student);
		}
	}

	public void findPsw(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String phone = getRequestParamter(request, "phone");
		String newpassword = getRequestParamter(request, "newpassword");
		CommonUtils.validateEmpty(phone);
		CommonUtils.validateEmpty(newpassword);
		SuserInfo user = suserService.getUserByPhone(phone);
		if (user == null) {
			resultMap.put("code", 2);
			resultMap.put("message", "该用户不存在");
			return;
		} else {
			user.setPassword(newpassword);
			suserService.updateUserInfo(user);
		}
	}
	
	
	//学员报名
	public void enroll(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		try
		{
		String studentid = getRequestParamter(request, "studentid");
		String model = getRequestParamter(request, "model");//车型
		String cityid = getRequestParamter(request, "cityid");//车型
		
		SuserInfo student = suserService.getUserById(studentid);
		student.setState(1);
		student.setEnrolltime(new Date());
		student.setModel(model);//设置车型c1,c2
		student.setModelcityid(CommonUtils.parseInt(cityid, 0));
		suserService.updateUserInfo(student);
		resultMap.put("code", 1);
		resultMap.put("message", "报名成功");
		}catch(Exception ex)
		{
			resultMap.put("code", 2);
			resultMap.put("message", "报名失败");
		}
	}
	
	

	public void getMyBalanceInfo(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String studentid = getRequestParamter(request, "studentid");
		CommonUtils.validateEmpty(studentid);
		SuserInfo student = suserService.getUserById(studentid);
		if (student == null) {
			resultMap.put("code", 2);
			resultMap.put("message", "该用户不存在");
			return;
		} else {
			resultMap.put("balance", student.getMoney());
			resultMap.put("fmoney", student.getFmoney());
			resultMap.put("recordlist", suserService.getMyBalanceList(studentid));
		}
	}

	public void applyCash(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String studentid = getRequestParamter(request, "studentid");
		String count = getRequestParamter(request, "count");
		CommonUtils.validateEmpty(studentid);
		CommonUtils.validateEmpty(count);
		SuserInfo student = suserService.getUserById(studentid);
		if (student == null) {
			resultMap.put("code", 2);
			resultMap.put("message", "该用户不存在");
			return;
		}
		else if(student.getMoney().doubleValue()<0 || student.getFmoney().doubleValue()<0 || CommonUtils.parseDouble(count, 0)<0)
		{
			resultMap.put("code", 6);
			resultMap.put("message", "您已欠费");
			return;
		}
		else if ((student.getMoney().doubleValue()) < CommonUtils.parseDouble(count, 0)) {
			resultMap.put("code", 3);
			resultMap.put("message", "账户余额不足");
			return;
		}
		
		suserService.applyCash(studentid, count);
	}

	// 账户充值
	public void recharge(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String studentid = getRequestParamter(request, "studentid");// 教练ID
		String amount = getRequestParamter(request, "amount");// 充值金额
		CommonUtils.validateEmpty(studentid);
		CommonUtils.validateEmpty(amount);
		HashMap<String, Object> rechargeResult = suserService.recharge(studentid, amount);
		resultMap.putAll(rechargeResult);
	}
	
	
	// 获取账户信息充值
	public void getWalletInfo(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException 
	{		
		String studentid = getRequestParamter(request, "studentid");// 教练ID
		CommonUtils.validateEmpty(studentid);	
		int sum= suserService.getCouponSum(Integer.parseInt(studentid));
		//查询总的小巴币数量
		//int coinsum= suserService.getSumCoinnum(studentid);
		SuserInfo su=suserService.getUserById(studentid);
		int coinsum=0;
		double money=0;
		int fcoinsum=0;
		if(su!=null){
			if(su.getCoinnum()!=null){
				coinsum=su.getCoinnum();
			}
			if(su.getMoney()!=null){
				money=su.getMoney().doubleValue();
			}
			if(su.getFcoinnum()!=null){
				fcoinsum=su.getFcoinnum().intValue();
			}
		}
		resultMap.put("couponsum", sum);
		resultMap.put("money", money);
		resultMap.put("coinsum", coinsum);
		resultMap.put("fcoinsum", fcoinsum);//冻结的小巴币
		HashMap<String, Object> re=suserService.getConsumeAmount(studentid);//返回小巴币，小巴券，余额的累积消费额
		resultMap.putAll(re);
	}


	// 获取账户小巴币记录
	public void getMyCoinRecord(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String studentid = getRequestParamter(request, "studentid");// 教练ID
		HashMap<String, Object> coinRecordResult = suserService.getCoinRecordList(studentid);
		resultMap.putAll(coinRecordResult);
	}

}
