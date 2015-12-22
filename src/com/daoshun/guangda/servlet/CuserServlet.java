package com.daoshun.guangda.servlet;

import java.io.IOException;
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
import com.daoshun.guangda.pojo.AreaInfo;
import com.daoshun.guangda.pojo.CaddAddressInfo;
import com.daoshun.guangda.pojo.CityInfo;
import com.daoshun.guangda.pojo.CoachStudentInfo;
import com.daoshun.guangda.pojo.CouponRecord;
import com.daoshun.guangda.pojo.CsubjectInfo;
import com.daoshun.guangda.pojo.CuserInfo;
import com.daoshun.guangda.pojo.DriveSchoolInfo;
import com.daoshun.guangda.pojo.ModelsInfo;
import com.daoshun.guangda.pojo.ProvinceInfo;
import com.daoshun.guangda.pojo.RecommendInfo;
import com.daoshun.guangda.pojo.SuserInfo;
import com.daoshun.guangda.pojo.SystemSetInfo;
import com.daoshun.guangda.pojo.TeachcarInfo;
import com.daoshun.guangda.pojo.UserLoginStatus;
import com.daoshun.guangda.service.ICUserService;
import com.daoshun.guangda.service.ILocationService;
import com.daoshun.guangda.service.IRecommendService;
import com.daoshun.guangda.service.ISUserService;
import com.daoshun.guangda.service.ISystemService;

/**
 * Servlet implementation class VerifyCodeServlet
 */
/**
 * @author liukn
 *
 */
@WebServlet("/cuser")
public class CuserServlet extends BaseServlet {

	/**
	 *
	 */
	private static final long serialVersionUID = 6998440419757851253L;

	private ICUserService cuserService;
	private ISystemService systemService;
	private ISUserService suserService;
	private IRecommendService recommendService;
	private ILocationService locationService;


	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		cuserService = (ICUserService) applicationContext.getBean("cuserService");
		systemService = (ISystemService) applicationContext.getBean("systemService");
		suserService = (ISUserService) applicationContext.getBean("suserService");
		recommendService=(IRecommendService) applicationContext.getBean("recommendService");
		locationService=(ILocationService) applicationContext.getBean("locationService");
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String action = getAction(request);

			if (Constant.CPERFECTACCOUNTINFO.equals(action) || Constant.CCHANGEAVATAR.equals(action) || Constant.CPERFECTPERSONINFO.equals(action) || Constant.CPERFECTCOACHINFO.equals(action)
					|| Constant.RECHARGE.equals(action) || Constant.GETMYBALANCEINFO.equals(action) || Constant.GETMYCOINRECORD.equals(action) || Constant.GRANTCOUPON.equals(action) || Constant.GETCOACHCOUPONLIST.equals(action) || Constant.GETCOACHPRICERANGE.equals(action)) {
				if (!checkSession(request, action, resultMap)) {
					setResult(response, resultMap);
					return;
				}
			}

			if (Constant.CLOGIN.equals(action)) {
				// 登录
				login(request, resultMap);
			} else if (Constant.CPERFECTACCOUNTINFO.equals(action)) {
				// 完善账号资料
				perfectAccountInfo(request, resultMap);
			} else if (Constant.CCHANGEAVATAR.equals(action)) {
				// 修改头像
				changeAvatar(request, resultMap);
			} else if (Constant.CGETCARMODEL.equals(action)) {
				// 获得准教车型
				getCarModel(request, resultMap);
			} else if (Constant.CPERFECTPERSONINFO.equals(action)) {
				// 完善个人信息
				perfectPersonInfo(request, resultMap);
			} else if (Constant.CPERFECTCOACHINFO.equals(action)) {
				// 完善教练资格资料
				perfectCoachInfo(request, resultMap);
			}else if (Constant.PERFECTCOACHMODELID.equals(action)) {
				// 完善准教车型
				perfectCoachModelid(request, resultMap);
			} else if (Constant.RECHARGE.equals(action)) {
				// 充值
				recharge(request, resultMap);
			} else if (Constant.GETMYBALANCEINFO.equals(action)) {
				// 获取账户余额信息
				getMyBalanceInfo(request, resultMap);
			} else if (Constant.GETMYCOINRECORD.equals(action)) {
				// 获取账户余额信息
				getMyCoinRecord(request, resultMap);
			}else if (Constant.GETCOACHCOINAFFILIATION.equals(action)) {
				// 获取教练的归属小巴币
				getCoachCoinAffiliation(request, resultMap);
			}else if (Constant.GETCOACHSTUDENT.equals(action)) {
				// 获取教练有关联学员
				getCoachStudentRelationShip(request, resultMap);
			}
			else if (Constant.GETSTUDENTCOUPON.equals(action)) {
				// 获取学员可用小巴券数，剩余数
				getStudentCouon(request, resultMap);
			}
			else if (Constant.GRANTCOUPON.equals(action)) {
				// 教练发放小巴券
				CoachGrantCouon(request, resultMap);
			}
			else if (Constant.GETCOACHCOUPONLIST.equals(action)) {
				// 获取教练发放小巴券记录
				getCoachCouponlist(request, resultMap);
			}
			else if (Constant.GETCOACHCOUPONLIMIT.equals(action)) {
				// 获取教练是否有发放小巴券权限
				getCoachCouponlimit(request, resultMap);
			}else if (Constant.GETCOACHPRICERANGE.equals(action)) {
				// 获取教练是否有发放小巴券权限
				getCoachPriceRange(request, resultMap);
			}else if ("COINEXCEPTION".equals(action)) {
				// 获取教练异常小巴币归属
				getCoachCoinAffiliationExcetpion(request, resultMap);
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
		String userid = "";// 1.教练 2.学员
		String usertype = "";

		if (Constant.CPERFECTACCOUNTINFO.equals(action)) {
			// 完善账号资料
			userid = getRequestParamter(request, "coachid");
			usertype = "1";
		} else if (Constant.CCHANGEAVATAR.equals(action)) {
			userid = getRequestParamter(request, "coachid");
			usertype = "1";
		} else if (Constant.CGETCARMODEL.equals(action)) {
		} else if (Constant.CPERFECTPERSONINFO.equals(action)) {
			userid = getRequestParamter(request, "coachid");
			usertype = "1";
		} else if (Constant.CPERFECTCOACHINFO.equals(action)) {
			userid = getRequestParamter(request, "coachid");
			usertype = "1";
		} else if (Constant.RECHARGE.equals(action)) {
			userid = getRequestParamter(request, "coachid");
			usertype = "1";
		} else if (Constant.GETMYBALANCEINFO.equals(action)) {
			userid = getRequestParamter(request, "coachid");
			usertype = "1";
		}else if (Constant.GETMYCOINRECORD.equals(action)) {
			userid = getRequestParamter(request, "coachid");
			usertype = "1";
		}else if (Constant.GRANTCOUPON.equals(action)) {
			userid = getRequestParamter(request, "coachid");
			usertype = "1";
		}else if (Constant.GETCOACHCOUPONLIST.equals(action)) {
			userid = getRequestParamter(request, "coachid");
			usertype = "1";
		}else if (Constant.GETCOACHPRICERANGE.equals(action)) {
			userid = getRequestParamter(request, "coachid");
			usertype = "1";
		}

		if (!CommonUtils.isEmptyString(userid) && !CommonUtils.isEmptyString(usertype)) {
			//System.out.println("usertype:"+usertype);//教练端
			if (CommonUtils.parseInt(usertype, 0) == 1) {
				//System.out.println("userid"+userid);
				CuserInfo cuser = cuserService.getCoachByid(CommonUtils.parseInt(userid, 0));
				//System.out.println("cuser="+cuser);
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

			} else {//学员端
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

		if (Constant.CLOGIN.equals(action)) {
			// 登录
			String phone = getRequestParamter(request, "loginid");// 电话号码
			if (!CommonUtils.isEmptyString(phone)) {
				CuserInfo cuser = cuserService.getCuserByPhone(phone);
				if (cuser != null) {
					userid = String.valueOf(cuser.getCoachid());
				}
			}
			usertype = "1";
		} else if (Constant.CPERFECTACCOUNTINFO.equals(action)) {
			// 完善账号资料
			userid = getRequestParamter(request, "coachid");
			usertype = "1";
		} else if (Constant.CCHANGEAVATAR.equals(action)) {
			userid = getRequestParamter(request, "coachid");
			usertype = "1";
		} else if (Constant.CGETCARMODEL.equals(action)) {
		} else if (Constant.CPERFECTPERSONINFO.equals(action)) {
			userid = getRequestParamter(request, "coachid");
			usertype = "1";
		} else if (Constant.CPERFECTCOACHINFO.equals(action)) {
			userid = getRequestParamter(request, "coachid");
			usertype = "1";
		} else if (Constant.RECHARGE.equals(action)) {
			userid = getRequestParamter(request, "coachid");
			usertype = "1";
		} else if (Constant.GETMYBALANCEINFO.equals(action)) {
			userid = getRequestParamter(request, "coachid");
			usertype = "1";
		}

		if (!CommonUtils.isEmptyString(userid) && !CommonUtils.isEmptyString(usertype)) {
			systemService.recordUserAction(userid, usertype, "cuser", action);
		}
	}

	/**
	 * 注册
	 *
	 * @param request
	 * @throws ErrException
	 */
	public void register(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String phone = getRequestParamter(request, "phone");
		CommonUtils.validateEmpty(phone);
		String realname = getRequestParamter(request, "realname");
		CommonUtils.validateEmpty(realname);
		String password = getRequestParamter(request, "password");
		CommonUtils.validateEmpty(password);
		String idnum = getRequestParamter(request, "idnum");
		CommonUtils.validateEmpty(idnum);

		CuserInfo cuser = cuserService.getCuserByPhone(phone);
		CuserInfo idcuser = cuserService.getCoachByIdnum(idnum);
		if (cuser != null) {
			resultMap.put("code", 2);
			resultMap.put("message", "该手机号码已经注册");
		} else if (idcuser != null) {
			resultMap.put("code", 3);
			resultMap.put("message", "身份证已存在");
		} else {
			// cuser = cuserService.registerUser(phone, realname, password, idnum);
			resultMap.put("UserInfo", cuser);
		}
	}

	/**
	 * 登录
	 *
	 * @param request
	 * @throws ErrException
	 */
	public void login(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String loginid = getRequestParamter(request, "loginid");// 电话号码
		CommonUtils.validateEmpty(loginid);
		String password = getRequestParamter(request, "password");// 验证码
		String devicetype = getRequestParamter(request, "ostype");//getRequestParamter(request, "devicetype");//设备类型  1 IOS  2 ADNROID
		String version = getRequestParamter(request, "version");//版本
		CommonUtils.validateEmpty(password);
		// 验证验证码的有效性
		int result = cuserService.checkVerCode(loginid, password);

		int flag=0;
		if(1==result)
		{
			flag=UserLoginStatus.LOGIN_SUCCESS;
		}else
		{
			flag=UserLoginStatus.LOGIN_FAIL;
		}

		//判断是否超出验证码验证次数                限制次数6次  间隔10分钟
		if(systemService.overLoginLimitCount(loginid,UserLoginStatus.TYPE_COACH,flag,request, resultMap,Constant.TRY_TIMES,Constant.TRY_LOGIN_INTERVAL))
		{
			return;
		}

		//result=1;
		if (result == 1) {// 可以登录
			String token = request.getSession().getId().toLowerCase();
			CuserInfo cuser = cuserService.getCuserByPhone(loginid);
			if (cuser == null) {
				cuser = cuserService.registerUser(loginid, token);// 注册
				cuser.setPassword(password);
				int dtype=CommonUtils.parseInt(devicetype, 0);
				if(dtype!=0){
					cuser.setDevicetype(dtype);//设置设备类型
				}
				if(version!=null && !"".equals(version)){
					//强制1.×。×版本必须升级
					if (version.startsWith("1"))
					{
						resultMap.put("code", 4);
						resultMap.put("message", "您的app版本太低,请退出app并重新进入,将自动检测更新");
						System.out.println("******************check version****** "+version+" ******phone****** "+loginid+" ***** logintime ****** "+ new Date().toLocaleString());
						return;
					}
					cuser.setVersion(version);//设置版本号
				}
				resultMap.put("isregister", 1);
			} else {
				cuser.setToken(token);
				cuser.setToken_time(new Date());
				cuser.setInvitecode("C"+CommonUtils.getInviteCode(cuser.getPhone()));
				int dtype=CommonUtils.parseInt(devicetype, 0);
				if(dtype!=0){
					cuser.setDevicetype(dtype);//设置设备类型
				}
				if(version!=null && !"".equals(version)){
					cuser.setVersion(version);//设置版本号
				}
				cuserService.updateCuser(cuser);
				cuser.setCar_cardpicfurl(cuserService.backUrl(cuser.getCar_cardpicf())); // 行驶证正面照
				cuser.setCar_cardpicburl(cuserService.backUrl(cuser.getCar_cardpicb())); // 行驶证反面照
				cuser.setCoach_cardpicurl(cuserService.backUrl(cuser.getCoach_cardpic())); // 教练证照片
				cuser.setDrive_cardpicurl(cuserService.backUrl(cuser.getDrive_cardpic())); // 驾驶证照片
				cuser.setAvatarurl(cuserService.backUrl(cuser.getAvatar())); // 头像照片
				cuser.setId_cardpicfurl(cuserService.backUrl(cuser.getId_cardpicf())); // 身份证正面照
				cuser.setId_cardpicburl(cuserService.backUrl(cuser.getId_cardpicb())); // 身份证反面照
				cuser.setRealpicurl(cuserService.backUrl(cuser.getRealpic())); // 真实照片
				cuser.setPassword(password);
				cuser.setMoney_frozen(String.valueOf(cuser.getFmoney())); // 冻结金额
				if (cuser.getModelid() != null) {
					String[] ml = cuser.getModelid().split(",");
					List<ModelsInfo> modellist = new ArrayList<ModelsInfo>();
					for (int i = 0; i < ml.length; i++) {
						ModelsInfo model = cuserService.getmodellistbycoachid(CommonUtils.parseInt(ml[i], 0));
						if (model != null) {
							modellist.add(model);
						}
					}
					cuser.setModellist(modellist);
				}

				// 驾校名称
				if (cuser.getDrive_schoolid() != null && cuser.getDrive_schoolid() != 0) {
					DriveSchoolInfo school = cuserService.getDriveSchoolInfoByid(cuser.getDrive_schoolid());
					if (school != null) {
						cuser.setDriveschool(school.getName());
					}

				} else {
					if (cuser.getDrive_school() != null && cuser.getDrive_school().length() > 0) {
						cuser.setDriveschool(cuser.getDrive_school());
					}
				}

				// 教学用车型号
				if (cuser.getCarmodelid() != 0) {
					TeachcarInfo teachCarInfo = cuserService.getTeachcarInfoByID(cuser.getCarmodelid());
					if (teachCarInfo != null) {
						cuser.setTeachcarmodel(teachCarInfo.getModelname());
					}

				} else {
					if (cuser.getCarmodel() != null && cuser.getCarmodel().length() > 0) {
						cuser.setTeachcarmodel(cuser.getCarmodel());
					}
				}

				// 默认的教学内容
				if (cuser.getSubjectdef() != 0) {
					CsubjectInfo subject = cuserService.getSubjectById(cuser.getSubjectdef());
					if (subject != null) {
						cuser.setSubjectname(subject.getSubjectname());
					}
				}

				// 默认的上车地址
				CaddAddressInfo address = cuserService.getcoachaddress(String.valueOf(cuser.getCoachid()));
				if (address != null) {
					cuser.setDefaultAddress(address.getDetail());
				}

				// 教练小巴券总时间
				cuser.setCouponhour(cuserService.getCoachAllCouponTime(cuser.getCoachid()));
				resultMap.put("isregister", 0);
			}
			//根据省市区ID查询对应的名称
			if(cuser.getProvinceid()!=null && cuser.getCityid()!=null && cuser.getAreaid()!=null){
				ProvinceInfo pro=locationService.getProvincesById(cuser.getProvinceid());
				//System.out.println("----"+pro.getProvince());
				CityInfo city=locationService.getCityById(cuser.getCityid());
				//System.out.println("----"+pro.getProvince()+city.getCity());
				AreaInfo area=locationService.getAreaById(cuser.getAreaid());
				//System.out.println("----"+pro.getProvince()+city.getCity()+area.getArea());
				String locationname="";
				if(pro!=null){
					locationname=pro.getProvince()+"-"+city.getCity()+"-"+area.getArea();
				}
				cuser.setLocationname(locationname);
			}
			resultMap.put("UserInfo", cuser);
			int rflag=recommendService.checkRecommendinfo(String.valueOf(cuser.getCoachid()),1);
			int cflag=recommendService.isoversixhour(String.valueOf(cuser.getCoachid()),1);
			if(rflag==0 || cflag==0)
				//返回0代表已经存在记录了或者已经超过6个小时
				resultMap.put("isInvited", 0);
			else
				//返回1代表没有记录并且未超过6个小时
				resultMap.put("isInvited", 1);
			SystemSetInfo systemSetInfo=cuserService.getSystemSetInfo();
			resultMap.put("crewardamount", systemSetInfo.getCrewardamount());
			resultMap.put("orewardamount", systemSetInfo.getOrewardamount());

		} else if (result == 0) {
			resultMap.put("code", 2);
			resultMap.put("message", "验证码错误,请重新输入");
		} else {
			resultMap.put("code", 3);
			resultMap.put("message", "您的登录信息已经过期,请重新获取验证码登录");
		}
	}

	/**
	 * 完善账号信息
	 *
	 * @param request
	 * @throws ErrException
	 */
	public void perfectAccountInfo(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String coachid = getRequestParamter(request, "coachid");
		CommonUtils.validateEmpty(coachid);
		String realname = getRequestParamter(request, "realname");
		String phone = getRequestParamter(request, "phone");
		String driveschool = getRequestParamter(request, "driveschool"); // 驾校名称
		String driveschoolid = getRequestParamter(request, "driveschoolid"); // 驾校id
		String gender = getRequestParamter(request, "gender"); // 性别
		//CommonUtils.validateEmpty(gender);// 性别必填

		CuserInfo cuserFir = cuserService.getCuserByCoachid(coachid);
		if (cuserFir == null) {
			resultMap.put("code", 2);
			resultMap.put("message", "用户不存在");
		} else {
			if (!CommonUtils.isEmptyString(realname)) {
				cuserFir.setRealname(realname);
			}
			if (!CommonUtils.isEmptyString(phone)) {
				cuserFir.setTelphone(phone);
			}

			if (!CommonUtils.isEmptyString(driveschool)) {
				cuserFir.setDrive_school(driveschool);
				cuserFir.setDrive_schoolid(0);
			}

			if (!CommonUtils.isEmptyString(driveschoolid)) {
				cuserFir.setDrive_schoolid(CommonUtils.parseInt(driveschoolid, 0));
				//cuserFir.setDrive_school("");
			}

			if (!CommonUtils.isEmptyString(gender)) {
				cuserFir.setGender(CommonUtils.parseInt(gender, 0));
			}

			cuserService.updateCuser(cuserFir);
			List<RecommendInfo> tempList=recommendService.getRecommondInviteInfoByCoachid(coachid, realname);
			RecommendInfo temp=recommendService.getRecommondInvitedInfoByCoachid(coachid, realname);
			if(tempList.size()!=0)
			{
				for(RecommendInfo r:tempList)
				{
					r.setCoachname(realname);
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

	/**
	 * 修改头像
	 *
	 * @param request
	 * @throws ErrException
	 */
	public void changeAvatar(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String coachid = getRequestParamter(request, "coachid");
		CommonUtils.validateEmpty(coachid);
		CuserInfo cuser = cuserService.getCuserByCoachid(coachid);
		if (cuser != null) {
			String avatar = (String) request.getAttribute("avatar");
			if (!CommonUtils.isEmptyString(avatar)) {
				cuser.setAvatar(CommonUtils.parseInt(avatar, 0));
				String avatarUrl = cuserService.changeAvatar(cuser);
				resultMap.put("avatarurl", avatarUrl);
			}
		}
	}

	/**
	 * 得到所有准教车型
	 *
	 * @param request
	 * @throws ErrException
	 */
	public void getCarModel(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		//获取版本号
		String version=getRequestParamter(request, "version");
		//如果没有接收到版本号，表示老版本，老版本只返回2个项目
		List<ModelsInfo> modellist = cuserService.getAllModelInfo();
		if("".equals(version) || version==null ){
			modellist=modellist.subList(0, 2);
		}

		resultMap.put("modellist", modellist);
	}

	/**
	 * 完善教练个人资料信息
	 *
	 * @param request
	 * @throws ErrException
	 */
	public void perfectPersonInfo(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String coachid = getRequestParamter(request, "coachid");
		CommonUtils.validateEmpty(coachid);
		String birthday = getRequestParamter(request, "birthday"); // 生日
		String cityid = getRequestParamter(request, "cityid"); // 城市
		String provinceid = getRequestParamter(request, "provinceid"); // 省份
		String areaid = getRequestParamter(request, "areaid"); // 地区
		String address = getRequestParamter(request, "address"); // 住址
		String urgentperson = getRequestParamter(request, "urgentperson"); // 紧急联系人
		String urgentphone = getRequestParamter(request, "urgentphone"); // 紧急联系人电话
		String selfeval = getRequestParamter(request, "selfeval"); // 自我评价
		String years = getRequestParamter(request, "years"); // 教龄
		CuserInfo cuser = cuserService.getCuserByCoachid(coachid);
		if (cuser == null) {
			resultMap.put("code", 2);
			resultMap.put("message", "用户不存在");
		} else {

			if (!CommonUtils.isEmptyString(birthday)) {
				cuser.setBirthday(birthday);
			}
			if (!CommonUtils.isEmptyString(cityid)) {
				cuser.setCityid(cityid);
			}
			if (!CommonUtils.isEmptyString(provinceid)) {
				cuser.setProvinceid(provinceid);
			}
			if (!CommonUtils.isEmptyString(areaid)) {
				cuser.setAreaid(areaid);
			}
			if (!CommonUtils.isEmptyString(address)) {
				cuser.setAddress(address);
			}
			if (!CommonUtils.isEmptyString(urgentperson)) {
				cuser.setUrgent_person(urgentperson);
			}
			if (!CommonUtils.isEmptyString(urgentphone)) {
				cuser.setUrgent_phone(urgentphone);
			}
			if (!CommonUtils.isEmptyString(selfeval)) {
				cuser.setSelfeval(selfeval);
			}
			if (!CommonUtils.isEmptyString(years)) {
				cuser.setYears(CommonUtils.parseInt(years, 0));
			}
			cuserService.updateCuser(cuser);
		}
	}

	/**
	 * 找回原密码
	 *
	 * @param request
	 * @throws ErrException
	 */
	public void findPsw(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String phone = getRequestParamter(request, "phone");
		CommonUtils.validateEmpty(phone);
		String newpassword = getRequestParamter(request, "newpassword");
		CuserInfo cuserInfo = cuserService.getCuserByPhone(phone);
		if (cuserInfo == null) {
			resultMap.put("code", 2);
			resultMap.put("message", "电话号码不存在");
		} else if (!CommonUtils.isEmptyString(newpassword)) {
			cuserInfo.setPassword(newpassword);
			cuserService.updateCuser(cuserInfo);
		} else {
			resultMap.put("code", 3);
			resultMap.put("message", "没有输入新密码");
		}
	}

	/**
	 * 修改密码验证原密码
	 *
	 * @param request
	 * @throws ErrException
	 */
	public void varifyPsw(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String coachid = getRequestParamter(request, "coachid");
		CommonUtils.validateEmpty(coachid);
		String password = getRequestParamter(request, "password");
		CommonUtils.validateEmpty(password);
		CuserInfo cuser = cuserService.getCuserByCoachid(coachid);
		if (cuser == null) {
			resultMap.put("code", 2);
			resultMap.put("message", "用户名不存在");
		} else if (!cuser.getPassword().equals(password)) {
			resultMap.put("code", 3);
			resultMap.put("message", "密码错误");
		}
	}

	/**
	 * 修改密码
	 *
	 * @param request
	 * @throws ErrException
	 */
	public void changePsw(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String coachid = getRequestParamter(request, "coachid");
		CommonUtils.validateEmpty(coachid);
		String password = getRequestParamter(request, "password");
		CommonUtils.validateEmpty(password);
		CuserInfo cuser = cuserService.getCuserByCoachid(coachid);
		if (cuser == null) {
			resultMap.put("code", 2);
			resultMap.put("message", "用户名不存在");
		} else {
			cuser.setPassword(password);
			cuserService.updateCuser(cuser);
		}
	}
	public void perfectCoachModelid(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String coachid = getRequestParamter(request, "coachid");
		CommonUtils.validateEmptytoMsg(coachid,"教练ID不能为空");
		String modelid = getRequestParamter(request, "modelid"); // 准教车型
		CommonUtils.validateEmptytoMsg(modelid, "车型不能为空");
		CuserInfo cuser = cuserService.getCuserByCoachid(coachid);
		if (cuser == null) {
			resultMap.put("code", 2);
			resultMap.put("message", "用户不存在");
		} else {
			if (!CommonUtils.isEmptyString(modelid)) {
				cuser.setModelid(modelid);
			}
		}
		cuserService.updateCuser(cuser);
	}
	/**
	 * 完善教练资格资料
	 *
	 * @param request
	 * @throws ErrException
	 */
	public void perfectCoachInfo(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String coachid = getRequestParamter(request, "coachid");
		CommonUtils.validateEmpty(coachid);
		String idnum = getRequestParamter(request, "idnum"); // 身份证号码
		String idcardextime = getRequestParamter(request, "idcardextime"); // 身份证到期时间
		String coachcardnum = getRequestParamter(request, "coachcardnum"); // 教练证号码
		String coachcardextime = getRequestParamter(request, "coachcardextime");// 教练证到期时间
		String drivecardnum = getRequestParamter(request, "drivecardnum"); // 驾驶证号码
		String drivecardextime = getRequestParamter(request, "drivecardextime");// 驾驶证到期时间
		String carcardnum = getRequestParamter(request, "carcardnum"); // 车辆年检证号码
		String carcardextime = getRequestParamter(request, "carcardextime"); // 车辆年检证到期时间
		String driveschool = getRequestParamter(request, "driveschool"); // 驾校名称
		String driveschoolid = getRequestParamter(request, "driveschoolid"); // 驾校id
		String modelid = getRequestParamter(request, "modelid"); // 准教车型
		String carmodel = getRequestParamter(request, "carmodel"); // 教学用车车型
		String carmodelid = getRequestParamter(request, "carmodelid"); // 教学用车车型id
		String carlicense = getRequestParamter(request, "carlicense"); // 教学用车牌照号码
		String cityid = getRequestParamter(request, "cityid"); //城市id
		String provinceid = getRequestParamter(request, "provinceid"); //省id
		String areaid = getRequestParamter(request, "areaid"); //城市id
		String cradpic1 = (String) request.getAttribute("cardpic1"); // 身份证正面
		String cradpic2 = (String) request.getAttribute("cardpic2"); // 身份证反面
		String cradpic3 = (String) request.getAttribute("cardpic3"); // 教练证照片
		String cradpic4 = (String) request.getAttribute("cardpic4"); // 驾驶证照片
		String cradpic5 = (String) request.getAttribute("cardpic5"); // 车辆行驶证正面照
		String cradpic6 = (String) request.getAttribute("cardpic6"); // 车辆行驶证反面照
		String cradpic7 = (String) request.getAttribute("cardpic7"); // 教练真实照片

		CuserInfo cuser = cuserService.getCuserByCoachid(coachid);
		if (cuser == null) {
			resultMap.put("code", 2);
			resultMap.put("message", "用户不存在");
		} else {
			if (!CommonUtils.isEmptyString(idnum)) {
				CuserInfo idcuser = cuserService.getCoachByIdnum(idnum);
				if (idcuser != null && idcuser.getCoachid() != cuser.getCoachid()) {
					resultMap.put("code", 3);
					resultMap.put("message", "身份证已存在");
				} else {
					cuser.setId_cardnum(idnum);
				}
			}

			if (!CommonUtils.isEmptyString(driveschool)) {
				//cuser.setDrive_school(driveschool);
				cuser.setDrive_schoolid(cuserService.getDriverSchoolIDbyName(driveschool));
				//cuser.setDrive_schoolid(0);
			}

			if (!CommonUtils.isEmptyString(driveschoolid)) {
				cuser.setDrive_schoolid(CommonUtils.parseInt(driveschoolid, 0));
				cuser.setDrive_school("");
			}

			if (!CommonUtils.isEmptyString(idcardextime)) {
				cuser.setId_cardexptime(idcardextime);
			}
			if (!CommonUtils.isEmptyString(coachcardnum)) {
				cuser.setCoach_cardnum(coachcardnum);
			}
			if (!CommonUtils.isEmptyString(coachcardextime)) {
				cuser.setCoach_cardexptime(coachcardextime);
			}
			if (!CommonUtils.isEmptyString(drivecardnum)) {
				cuser.setDrive_cardnum(drivecardnum);
			}
			if (!CommonUtils.isEmptyString(drivecardextime)) {
				cuser.setDrive_cardexptime(drivecardextime);
			}
			if (!CommonUtils.isEmptyString(carcardnum)) {
				cuser.setCar_cardnum(carcardnum);
			}
			if (!CommonUtils.isEmptyString(carcardextime)) {
				cuser.setCar_cardexptime(carcardextime);
			}
			if (!CommonUtils.isEmptyString(modelid)) {
				cuser.setModelid(modelid);
			}
			if (!CommonUtils.isEmptyString(carmodel)) {
				cuser.setCarmodel(carmodel);
				cuser.setCarmodelid(0);
			}
			if (!CommonUtils.isEmptyString(carmodelid)) {
				cuser.setCarmodelid(CommonUtils.parseInt(carmodelid, 0));
				cuser.setCarmodel("");
			}
			if (!CommonUtils.isEmptyString(carlicense)) {
				cuser.setCarlicense(carlicense);
			}
			if (!CommonUtils.isEmptyString(cradpic1)) {
				cuser.setId_cardpicf(CommonUtils.parseInt(cradpic1, 0));
			}
			if (!CommonUtils.isEmptyString(cradpic2)) {
				cuser.setId_cardpicb(CommonUtils.parseInt(cradpic2, 0));
			}
			if (!CommonUtils.isEmptyString(cradpic3)) {
				cuser.setCoach_cardpic(CommonUtils.parseInt(cradpic3, 0));
			}
			if (!CommonUtils.isEmptyString(cradpic4)) {
				cuser.setDrive_cardpic(CommonUtils.parseInt(cradpic4, 0));
			}
			if (!CommonUtils.isEmptyString(cradpic5)) {
				cuser.setCar_cardpicf(CommonUtils.parseInt(cradpic5, 0));
			}
			if (!CommonUtils.isEmptyString(cradpic6)) {
				cuser.setCar_cardpicb(CommonUtils.parseInt(cradpic6, 0));
			}
			if (!CommonUtils.isEmptyString(cradpic7)) {
				cuser.setRealpic(CommonUtils.parseInt(cradpic7, 0));
			}
			if (!CommonUtils.isEmptyString(cityid)) {
				cuser.setCityid(cityid);
			}
			if (!CommonUtils.isEmptyString(provinceid)) {
				cuser.setProvinceid(provinceid);
			}
			if (!CommonUtils.isEmptyString(areaid)) {
				cuser.setAreaid(areaid);
			}
			cuser.setState(1);
			cuserService.updateCuser(cuser);
			String cradpic1url = cuserService.backUrl(CommonUtils.parseInt(cradpic1, 0));
			String cradpic2url = cuserService.backUrl(CommonUtils.parseInt(cradpic2, 0));
			String cradpic3url = cuserService.backUrl(CommonUtils.parseInt(cradpic3, 0));
			String cradpic4url = cuserService.backUrl(CommonUtils.parseInt(cradpic4, 0));
			String cradpic5url = cuserService.backUrl(CommonUtils.parseInt(cradpic5, 0));
			String cradpic6url = cuserService.backUrl(CommonUtils.parseInt(cradpic6, 0));
			String cradpic7url = cuserService.backUrl(CommonUtils.parseInt(cradpic7, 0));
			resultMap.put("cradpic1url", cradpic1url);
			resultMap.put("cradpic2url", cradpic2url);
			resultMap.put("cradpic3url", cradpic3url);
			resultMap.put("cradpic4url", cradpic4url);
			resultMap.put("cradpic5url", cradpic5url);
			resultMap.put("cradpic6url", cradpic6url);
			resultMap.put("cradpic7url", cradpic7url);
		}
	}

	// 账户充值
	public void recharge(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String coachid = getRequestParamter(request, "coachid");// 教练ID
		String amount = getRequestParamter(request, "amount");// 充值金额

		CommonUtils.validateEmpty(coachid);
		CommonUtils.validateEmpty(amount);
		HashMap<String, Object> rechargeResult = cuserService.recharge(coachid, amount);
		resultMap.putAll(rechargeResult);
	}

	// 获取账户余额 和充值记录
	public void getMyBalanceInfo(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String coachid = getRequestParamter(request, "coachid");// 教练ID

		HashMap<String, Object> balanceResult = cuserService.getBalanceList(coachid);
		resultMap.putAll(balanceResult);
	}


	// 获取账户小巴币记录
	public void getMyCoinRecord(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String coachid = getRequestParamter(request, "coachid");// 教练ID

		HashMap<String, Object> coinRecordResult = cuserService.getCoinRecordList(coachid);
		resultMap.putAll(coinRecordResult);
	}
	//获取教练小巴币归属那些教练或驾校使用
	public void getCoachCoinAffiliation(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String coachid = getRequestParamter(request, "coachid");// 教练ID
		CommonUtils.validateEmpty(coachid);
		HashMap<String, Object> result=cuserService.getCoinAffiliation(coachid);
		resultMap.putAll(result);
	}
	//获取教练小巴币归属那些教练的异常数据
	public void getCoachCoinAffiliationExcetpion(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {

		HashMap<String, Object> result=cuserService.getCoinAffiliationException();
		//resultMap.putAll(result);
	}
	//获取教练有关联学员信息
	public void getCoachStudentRelationShip(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException
	{
		String coachid = getRequestParamter(request, "coachid");// 教练ID
		CommonUtils.validateEmpty(coachid);
		List<SuserInfo> suser=cuserService.getCoachStudent(coachid);
		resultMap.put("studentlist", suser);
	}
	//获取学员小巴券可用，剩余张数
	public void getStudentCouon(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException
	{
		String coachid = getRequestParamter(request, "coachid");// 教练ID
		String studentid = getRequestParamter(request, "studentid");// 学员ID
		CommonUtils.validateEmpty(coachid);
		CommonUtils.validateEmpty(studentid);
		Integer total=cuserService.getstudentCoupontotal(studentid, coachid);
		Integer rest=cuserService.getstudentCouponrest(studentid, coachid);
		resultMap.put("total", total);
		resultMap.put("rest", rest);
	}
	//教练发放小巴券
	public void CoachGrantCouon(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException
	{
		String coachid = getRequestParamter(request, "coachid");// 教练ID
		String phone = getRequestParamter(request, "phone");// 手机号码
		String pubnum= getRequestParamter(request, "pubnum");// 发放张数
		CommonUtils.validateEmpty(coachid);
		CommonUtils.validateEmpty(phone);
		CommonUtils.validateEmpty(pubnum);
		String result=cuserService.coachgrantcoupon(coachid,phone,CommonUtils.parseInt(pubnum, 0));
		if(result.equals("ERROR0"))
		{
			resultMap.put("code", 9);
			resultMap.put("message", "教练没有发放小巴券权限，请在提交申请后再进行发放");
		}
		else if(result.equals("ERROR1"))
		{
			resultMap.put("code", 10);
			resultMap.put("message", "该学员不存在");
		}
		else if(result.equals("ERROR2"))
		{
			resultMap.put("code", 11);
			resultMap.put("message", "教练可用小巴券不足");
		}
	}
	//获取教练发放小巴券记录
	public void  getCoachCouponlist(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException
	{
		String coachid = getRequestParamter(request, "coachid");// 教练ID
		String pagenum = getRequestParamter(request, "pagenum");//当前页
		CommonUtils.validateEmpty(coachid);
		CommonUtils.validateEmpty(pagenum);
		HashMap<String, Object> cm=cuserService.getcoachcouponlist(coachid,pagenum);
		if(cm!=null)
		{
			resultMap.put("recordlist", cm.get("CouponRecordList"));
			resultMap.put("hasmore", cm.get("hasmore"));
		}
	}
	public void  getCoachCouponlimit(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException
	{
		String coachid = getRequestParamter(request, "coachid");// 教练ID
		CommonUtils.validateEmpty(coachid);
		Integer flag=cuserService.getcoachcouponlimit(coachid);
		resultMap.put("grantlimit",flag);
	}

	/*
	 * 教练端开课前获得该教练的课程价格区间
	 * 科目二范围 50   1000
	 * 科目三范围 50   1000
	 * 考场训练范围 50  1000
	 * 陪驾范围 50  1000
	 * 租车范围 0   500
	 * 科目二体验课范围 0  500
	 * 科目三体验课范围 0  500
	 * added by 张聚弘
	 * 2015-11-04
	 * */
	public void  getCoachPriceRange(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException
	{
		String coachid = getRequestParamter(request, "coachid");// 教练ID
		CommonUtils.validateEmpty(coachid);

		CuserInfo cuser = cuserService.getCuserByCoachid(coachid);

		resultMap.put("UserInfo",cuser);
	}
}
