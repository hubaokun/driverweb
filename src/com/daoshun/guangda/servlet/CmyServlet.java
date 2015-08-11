package com.daoshun.guangda.servlet;

import java.io.IOException;
import java.math.BigDecimal;
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
import com.daoshun.guangda.NetData.ComplaintNetData;
import com.daoshun.guangda.NetData.EvaluationNetData;
import com.daoshun.guangda.pojo.*;
import com.daoshun.guangda.service.*;

@WebServlet("/cmy")
public class CmyServlet extends BaseServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8919627266992686515L;

	private ICmyService cmyService;
	private ICUserService cuserService;
	private ISystemService systemService;
	private ISUserService suserService;
	private ICoinRecordService coinRecordService;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		cmyService = (ICmyService) applicationContext.getBean("cmyService");
		cuserService = (ICUserService) applicationContext.getBean("cuserService");
		systemService = (ISystemService) applicationContext.getBean("systemService");
		suserService = (ISUserService) applicationContext.getBean("suserService");
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String action = getAction(request);

			if (Constant.CADDADDRESS.equals(action) || Constant.CDELADDRESS.equals(action) || Constant.CSETCURRENTADDRESS.equals(action) || Constant.CGETALLADDRESS.equals(action)
					|| Constant.CAPPLYCASH.equals(action) || Constant.CGETNOTICES.equals(action) || Constant.CDELNOTICE.equals(action) || Constant.CGETCOMPLAINTTOMY.equals(action)
					|| Constant.CGETMYCOMPLAINT.equals(action) || Constant.CGETEVALUATIONTOMY.equals(action) || Constant.CGETMYEVALUATION.equals(action) || Constant.CGETMESSAGECOUNT.equals(action)
					|| Constant.CHANGEALIACCOUNT.equals(action) || Constant.GETALLCOUPON.equals(action) || Constant.APPLYCOUPON.equals(action) || Constant.GETMYALLSTUDENT.equals(action)
					|| Constant.DELALIACCOUNT.equals(action) || Constant.CHANGEAPPLYTYPE.equals(action)||Constant.APPLYCOIN.equals(action)) {
				if (!checkSession(request, action, resultMap)) {
					setResult(response, resultMap);
					return;
				}
			}

			if (Constant.CADDADDRESS.equals(action)) {
				// 增加教练地址
				addAddress(request, resultMap);
			} else if (Constant.CDELADDRESS.equals(action)) {
				// 删除教练地址
				delAddress(request, resultMap);
			} else if (Constant.CSETCURRENTADDRESS.equals(action)) {
				// 设置地址为当前使用地址
				setCurrentAddress(request, resultMap);
			} else if (Constant.CGETALLADDRESS.equals(action)) {
				// 得到该教练所有地址
				getAllAddress(request, resultMap);
			} else if (Constant.CAPPLYCASH.equals(action)) {
				// 申请提现
				applyCash(request, resultMap);
			} else if (Constant.CGETNOTICES.equals(action)) {
				// 得到通知列表
				getNotices(request, resultMap);
			} else if (Constant.CDELNOTICE.equals(action)) {
				// 删除通知
				delNotice(request, resultMap);
			} else if (Constant.READNOTICE.equals(action)) {
				// 修改通知状态为已读
				setNoticeState(request, resultMap);
			} else if (Constant.CGETCOMPLAINTTOMY.equals(action)) {
				// 得到对我的投诉列表
				getComplaintToMy(request, resultMap);
			} else if (Constant.CGETMYCOMPLAINT.equals(action)) {
				// 得到投诉我的列表
				getMyCompliant(request, resultMap);
			} else if (Constant.CGETEVALUATIONTOMY.equals(action)) {
				// 得到对我的评价列表
				getEvaluationToMy(request, resultMap);
			} else if (Constant.CGETMYEVALUATION.equals(action)) {
				// 得到我的评价列表
				getMyEvaluation(request, resultMap);
			} else if (Constant.CGETMESSAGECOUNT.equals(action)) {
				// 获取我的消息的数量
				getMessageCount(request, resultMap);
			} else if (Constant.CGETALLSUBJECT.equals(action)) {
				// 获得所有教学信息
				getAllSubject(request, resultMap);
			} else if (Constant.CGETALLTEACHCARMODEL.equals(action)) {
				// 获得所有教学用车车型
				getAllTeachCarModel(request, resultMap);
			} else if (Constant.CGETALLSCHOOL.equals(action)) {
				// 得到所有驾校信息
				getAllSchool(request, resultMap);
			} else if (Constant.CHANGEALIACCOUNT.equals(action)) {
				changeAliAccount(request, resultMap);
			} else if (Constant.GETALLCOUPON.equals(action)) {
				getAllCoupon(request, resultMap);
			} else if (Constant.APPLYCOUPON.equals(action)) {
				applyCoupon(request, resultMap);
			} else if (Constant.APPLYCOIN.equals(action)) {
				applyCoin(request, resultMap);
			}
			else if (Constant.GETMYALLSTUDENT.equals(action)) {
				getMyAllStudent(request, resultMap);
			} else if (Constant.DELALIACCOUNT.equals(action)) {
				delAliAccount(request, resultMap);
			} else if (Constant.CHANGEAPPLYTYPE.equals(action)) {
				changeApplyType(request, resultMap);
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

		if (Constant.CADDADDRESS.equals(action)) {
			// 增加教练地址
			userid = getRequestParamter(request, "coachid");
			usertype = "1";
		} else if (Constant.CDELADDRESS.equals(action)) {
			// 删除教练地
			userid = getRequestParamter(request, "coachid");
			usertype = "1";
		} else if (Constant.CSETCURRENTADDRESS.equals(action)) {
			// 设置地址为当前使用地址
			userid = getRequestParamter(request, "coachid");
			usertype = "1";
		} else if (Constant.CGETALLADDRESS.equals(action)) {
			// 得到该教练所有地址
			userid = getRequestParamter(request, "coachid");
			usertype = "1";
		} else if (Constant.CAPPLYCASH.equals(action)) {
			// 申请提现
			userid = getRequestParamter(request, "coachid");
			usertype = "1";
		} else if (Constant.CGETNOTICES.equals(action)) {
			// 得到通知列表
			userid = getRequestParamter(request, "coachid");
			usertype = "1";
		} else if (Constant.CDELNOTICE.equals(action)) {
			// 删除通知
			userid = getRequestParamter(request, "coachid");
			usertype = "1";
		} else if (Constant.READNOTICE.equals(action)) {
			// 修改通知为已读
			userid = getRequestParamter(request, "coachid");
			usertype = "1";
		}else if (Constant.CGETCOMPLAINTTOMY.equals(action)) {
			// 得到对我的投诉列表
			userid = getRequestParamter(request, "coachid");
			usertype = "1";
		} else if (Constant.CGETMYCOMPLAINT.equals(action)) {
			// 得到投诉我的列表
			userid = getRequestParamter(request, "coachid");
			usertype = "1";
		} else if (Constant.CGETEVALUATIONTOMY.equals(action)) {
			// 得到对我的评价列表
			userid = getRequestParamter(request, "coachid");
			usertype = "1";
		} else if (Constant.CGETMYEVALUATION.equals(action)) {
			// 得到我的评价列表
			userid = getRequestParamter(request, "coachid");
			usertype = "1";
		} else if (Constant.CGETMESSAGECOUNT.equals(action)) {
			// 获取我的消息的数量
			userid = getRequestParamter(request, "coachid");
			usertype = "1";
		} else if (Constant.CGETALLSUBJECT.equals(action)) {
			// 获得所有教学信息
			userid = getRequestParamter(request, "coachid");
			usertype = "1";
		} else if (Constant.CGETALLTEACHCARMODEL.equals(action)) {
			// 获得所有教学用车车型
		} else if (Constant.CGETALLSCHOOL.equals(action)) {
			// 得到所有驾校信息
		} else if (Constant.CHANGEALIACCOUNT.equals(action)) {
			userid = getRequestParamter(request, "userid");// 用户ID
			usertype = getRequestParamter(request, "type");// 类型 1教练 2学员
		} else if (Constant.GETALLCOUPON.equals(action)) {
			userid = getRequestParamter(request, "coachid");
			usertype = "1";
		} else if (Constant.APPLYCOUPON.equals(action)) {
			userid = getRequestParamter(request, "coachid");
			usertype = "1";
		} else if (Constant.GETMYALLSTUDENT.equals(action)) {
			userid = getRequestParamter(request, "coachid");
			usertype = "1";
		} else if (Constant.DELALIACCOUNT.equals(action)) {
			userid = getRequestParamter(request, "userid");// 用户ID
			usertype = getRequestParamter(request, "type");
		} else if (Constant.CHANGEAPPLYTYPE.equals(action)) {
			userid = getRequestParamter(request, "coachid");// 用户ID
			usertype = "1";
		}
		else if (Constant.APPLYCOIN.equals(action)) {
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

	public void recordUserAction(HttpServletRequest request, String action) {
		String userid = "";// 1.教练 2.学员
		String usertype = "";

		if (Constant.CADDADDRESS.equals(action)) {
			// 增加教练地址
			userid = getRequestParamter(request, "coachid");
			usertype = "1";
		} else if (Constant.CDELADDRESS.equals(action)) {
			// 删除教练地
			userid = getRequestParamter(request, "coachid");
			usertype = "1";
		} else if (Constant.CSETCURRENTADDRESS.equals(action)) {
			// 设置地址为当前使用地址
			userid = getRequestParamter(request, "coachid");
			usertype = "1";
		} else if (Constant.CGETALLADDRESS.equals(action)) {
			// 得到该教练所有地址
			userid = getRequestParamter(request, "coachid");
			usertype = "1";
		} else if (Constant.CAPPLYCASH.equals(action)) {
			// 申请提现
			userid = getRequestParamter(request, "coachid");
			usertype = "1";
		} else if (Constant.CGETNOTICES.equals(action)) {
			// 得到通知列表
			userid = getRequestParamter(request, "coachid");
			usertype = "1";
		} else if (Constant.CDELNOTICE.equals(action)) {
			// 删除通知
			userid = getRequestParamter(request, "coachid");
			usertype = "1";
		} else if (Constant.CGETCOMPLAINTTOMY.equals(action)) {
			// 得到对我的投诉列表
			userid = getRequestParamter(request, "coachid");
			usertype = "1";
		} else if (Constant.CGETMYCOMPLAINT.equals(action)) {
			// 得到投诉我的列表
			userid = getRequestParamter(request, "coachid");
			usertype = "1";
		} else if (Constant.CGETEVALUATIONTOMY.equals(action)) {
			// 得到对我的评价列表
			userid = getRequestParamter(request, "coachid");
			usertype = "1";
		} else if (Constant.CGETMYEVALUATION.equals(action)) {
			// 得到我的评价列表
			userid = getRequestParamter(request, "coachid");
			usertype = "1";
		} else if (Constant.CGETMESSAGECOUNT.equals(action)) {
			// 获取我的消息的数量
			userid = getRequestParamter(request, "coachid");
			usertype = "1";
		} else if (Constant.CGETALLSUBJECT.equals(action)) {
			// 获得所有教学信息
			userid = getRequestParamter(request, "coachid");
			usertype = "1";
		} else if (Constant.CGETALLTEACHCARMODEL.equals(action)) {
			// 获得所有教学用车车型
		} else if (Constant.CGETALLSCHOOL.equals(action)) {
			// 得到所有驾校信息
		} else if (Constant.CHANGEALIACCOUNT.equals(action)) {
			userid = getRequestParamter(request, "userid");// 用户ID
			usertype = getRequestParamter(request, "type");// 类型 1教练 2学员
		} else if (Constant.GETALLCOUPON.equals(action)) {
			userid = getRequestParamter(request, "coachid");
			usertype = "1";
		} else if (Constant.APPLYCOUPON.equals(action)) {
			userid = getRequestParamter(request, "coachid");
			usertype = "1";
		}

		else if (Constant.APPLYCOIN.equals(action)) {
			userid = getRequestParamter(request, "coachid");
			usertype = "1";
		}

		else if (Constant.GETMYALLSTUDENT.equals(action)) {
			userid = getRequestParamter(request, "coachid");
			usertype = "1";
		} else if (Constant.DELALIACCOUNT.equals(action)) {
			userid = getRequestParamter(request, "userid");// 用户ID
			usertype = getRequestParamter(request, "type");
		} else if (Constant.CHANGEAPPLYTYPE.equals(action)) {
			userid = getRequestParamter(request, "coachid");// 用户ID
			usertype = "1";
		}

		if (!CommonUtils.isEmptyString(userid) && !CommonUtils.isEmptyString(usertype)) {
			systemService.recordUserAction(userid, usertype, "cmy", action);
		}
	}

	/**
	 * 设置教练单价
	 * 
	 * @param request
	 * @throws ErrException
	 */
	public void setPrice(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String coachid = getRequestParamter(request, "coachid");
		CommonUtils.validateEmpty(coachid);
		String price = getRequestParamter(request, "price");
		BigDecimal b = new BigDecimal(CommonUtils.parseFloat(price, 0f));
		float f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
		BigDecimal newprice = new BigDecimal(f1);
		CuserInfo cuser = cuserService.getCuserByCoachid(coachid);
		if (cuser == null) {
			resultMap.put("code", 2);
			resultMap.put("message", "用户不存在");
		} else {
			cuser.setPrice(newprice);
			cuserService.updateCuser(cuser);
		}
	}

	/**
	 * 增加地址
	 * 
	 * @param request
	 * @throws ErrException
	 */
	public void addAddress(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String coachid = getRequestParamter(request, "coachid");
		CommonUtils.validateEmpty(coachid);
		String longitude = getRequestParamter(request, "longitude");
		CommonUtils.validateEmpty(longitude);
		String latitude = getRequestParamter(request, "latitude");
		CommonUtils.validateEmpty(latitude);
		String area = getRequestParamter(request, "area");
		CommonUtils.validateEmpty(area);
		String detail = getRequestParamter(request, "detail");
		CommonUtils.validateEmpty(detail);
		// 添加地址
		CaddAddressInfo cadd = new CaddAddressInfo();
		cadd.setCoachid(CommonUtils.parseInt(coachid, 0));
		cadd.setLongitude(longitude);
		cadd.setLatitude(latitude);
		cadd.setArea(area);
		cadd.setDetail(detail);
		// 根据教练找到其地址集合
		List<CaddAddressInfo> cadds = cmyService.getAddressInfosByCoachid(coachid);
		if (cadds.size() == 0) {
			// 如果为空设置为当前使用地址
			cadd.setIscurrent(Constant.COACHCURRENTADDRESS1);
			cmyService.addAddress(cadd);
		} else {
			// 否则仅添加地址
			cmyService.addAddress(cadd);
		}
	}

	/**
	 * 删除地址
	 * 
	 * @param request
	 * @throws ErrException
	 */
	public void delAddress(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String coachid = getRequestParamter(request, "coachid");
		CommonUtils.validateEmpty(coachid);
		String addressid = getRequestParamter(request, "addressid");
		CommonUtils.validateEmpty(addressid);
		CaddAddressInfo cadd = cmyService.getCoachidByaddAddressid(addressid);
		if (cadd == null) {
			resultMap.put("code", 2);
			resultMap.put("message", "删除失败，地址不存在");
		} else if (!coachid.equals(String.valueOf(cadd.getCoachid()))) {
			resultMap.put("code", 2);
			resultMap.put("message", "不匹配");
		} else if (cadd.getIscurrent() == 1) {
			// 给教练一个提醒，此地址是当前使用地址
			cmyService.delAddress(cadd);
		} else {
			cmyService.delAddress(cadd);
		}
	}

	/**
	 * 设置地址为当前使用地址
	 * 
	 * @param request
	 * @throws ErrException
	 */
	public void setCurrentAddress(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String coachid = getRequestParamter(request, "coachid");
		CommonUtils.validateEmpty(coachid);
		String addressid = getRequestParamter(request, "addressid");
		CommonUtils.validateEmpty(addressid);
		CaddAddressInfo cadd = cmyService.getCoachidByaddAddressid(addressid);
		if (cadd == null) {
			resultMap.put("code", 2);
			resultMap.put("message", "设置失败，地址不存在");
		} else if (cadd.getCoachid() == CommonUtils.parseInt(coachid, 0)) {
			List<CaddAddressInfo> cadds = cmyService.getAddressInfosByCoachid(coachid);
			for (int i = 0; i < cadds.size(); i++) {
				if (cadds.get(i).getAddressid() == CommonUtils.parseInt(addressid, 0)) {
					// 设置为当前地址并保存更改
					cadds.get(i).setIscurrent(Constant.COACHCURRENTADDRESS1);
					cmyService.setCurrentAddress(cadds.get(i));
				} else {
					// 设置为非当前地址，并保存更改
					cadds.get(i).setIscurrent(Constant.COACHCURRENTADDRESS0);
					cmyService.setCurrentAddress(cadds.get(i));
				}
			}
		} else {
			resultMap.put("code", 2);
			resultMap.put("message", "设置失败，教练没有此地址");
		}
	}

	/**
	 * 根据coachid找到所有的地址
	 * 
	 * @param request
	 * @throws ErrException
	 */
	public void getAllAddress(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String coachid = getRequestParamter(request, "coachid");
		CommonUtils.validateEmpty(coachid);
		List<CaddAddressInfo> cadds = cmyService.getAddressInfosByCoachid(coachid);
		resultMap.put("addresslist", cadds);
	}

	/**
	 * 申请提现
	 * 
	 * @param request
	 * @throws ErrException
	 */
	public void applyCash(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String coachid = getRequestParamter(request, "coachid");
		CommonUtils.validateEmpty(coachid);
		String count = getRequestParamter(request, "count");
		CommonUtils.validateEmpty(count);
		CuserInfo cuser = cuserService.getCuserByCoachid(coachid);
		if (cuser == null) {
			// 提示用户不存在
			resultMap.put("code", 2);
			resultMap.put("message", "用户不存在");
		}
		else if(cuser.getMoney().doubleValue()<0 || cuser.getFmoney().doubleValue()<0 || CommonUtils.parseFloat(count, 0)<0)
		{
			resultMap.put("code", 6);
			resultMap.put("message", "数据异常");
		}
		else if (cuser.getMoney().doubleValue() < CommonUtils.parseFloat(count, 0)) {
			// 余额不足 无法提现
			resultMap.put("code", 3);
			resultMap.put("message", "您的余额不足");
		} else {
			// 更改教练信息 并且生成提现信息
			cuser.setFmoney(cuser.getFmoney().add(new BigDecimal(CommonUtils.parseFloat(count, 0))));
			cuser.setMoney(cuser.getMoney().subtract(new BigDecimal(CommonUtils.parseFloat(count, 0))));
			cuserService.updateCuser(cuser);
			CApplyCashInfo applycash = new CApplyCashInfo();
			applycash.setCoachid(CommonUtils.parseInt(coachid, 0));
			applycash.setAmount(new BigDecimal(CommonUtils.parseDouble(count, 0)));
			applycash.setState(Constant.COCAHAPPLY_UNCOMPLETE);
			applycash.setAddtime(new Date());
			applycash.setUpdatetime(new Date());
			applycash.setSchoolid(0);
			cmyService.addApplyCash(applycash);
			resultMap.put("code", 1);
			resultMap.put("message", "申请提交成功");
			resultMap.put("money", cuser.getMoney());
			resultMap.put("fmoney", cuser.getFmoney());
			resultMap.put("gmoney", cuser.getGmoney());
		}
	}

	/**
	 * 根据coachid分页得到对他的通知列表
	 * 
	 * @param request
	 * @throws ErrException
	 */
	public void getNotices(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String coachid = getRequestParamter(request, "coachid");
		CommonUtils.validateEmpty(coachid);
		String pagenum = getRequestParamter(request, "pagenum");
		int page = CommonUtils.parseInt(pagenum, 0);
		int count = Constant.COUNT_NUM;
		List<NoticesInfo> noticesInfoList = cmyService.getNoticesInfoByuserid(coachid, page + 1, count);
		List<NoticesInfo> noticesInfoLists = cmyService.getNoticesInfoByuserid(coachid, page + 2, count);
		if (noticesInfoLists.size() == 0) {
			resultMap.put("hasmore", 0);
		} else {
			resultMap.put("hasmore", 1);
		}
		resultMap.put("datalist", noticesInfoList);
		// 设置所有消息已读
		if (page == 0)
			cmyService.setAllMessageReaded(coachid);
	}

	/**
	 * 删除通知
	 * 
	 * @param request
	 * @throws ErrException
	 */
	public void delNotice(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String coachid = getRequestParamter(request, "coachid");
		CommonUtils.validateEmpty(coachid);
		String noticeid = getRequestParamter(request, "noticeid");
		CommonUtils.validateEmpty(noticeid);
		List<NoticesInfo> noticeInfo = cmyService.getNoticesInfoByuserid(coachid);
		if (noticeInfo.size() != 0) {
			NoticesInfo noticesInfo = cmyService.getNoticesInfoByNoiticeid(CommonUtils.parseInt(noticeid, 0));
			NoticesUserInfo noticesUserInfo = cmyService.getNoticeUserInfoByNoticeid(CommonUtils.parseInt(noticeid, 0));
			if (noticesInfo != null) {
				cmyService.delNoticesInfo(noticesInfo);
			}
			if (noticesUserInfo != null) {
				cmyService.delNoticesUserInfo(noticesUserInfo);
			}
		}
	}

	/**
	 * 取得我的投诉列表
	 * 
	 * @param request
	 * @throws ErrException
	 */
	public void getComplaintToMy(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String coachid = getRequestParamter(request, "coachid");
		CommonUtils.validateEmpty(coachid);
		String pagenum = getRequestParamter(request, "pagenum");
		List<ComplaintNetData> complaintlist = cmyService.getComplaintInfoByTo_user(CommonUtils.parseInt(coachid, 0), CommonUtils.parseInt(pagenum, 0));
		List<ComplaintNetData> newcomplaintlist = cmyService.getComplaintInfoByTo_user(CommonUtils.parseInt(coachid, 0), CommonUtils.parseInt(pagenum, 0) + 1);
		if (newcomplaintlist.size() == 0) {
			resultMap.put("hasmore", 0);
		} else {
			resultMap.put("hasmore", 1);
		}
		resultMap.put("complaintlist", complaintlist);
	}

	/**
	 * 取得我投诉的列表
	 * 
	 * @param request
	 * @throws ErrException
	 */
	public void getMyCompliant(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String coachid = getRequestParamter(request, "coachid");
		CommonUtils.validateEmpty(coachid);
		String pagenum = getRequestParamter(request, "pagenum");
		List<ComplaintNetData> complaintlist = cmyService.getComplaintInfoByFrom_user(CommonUtils.parseInt(coachid, 0), CommonUtils.parseInt(pagenum, 0));
		List<ComplaintNetData> newcomplaintlist = cmyService.getComplaintInfoByFrom_user(CommonUtils.parseInt(coachid, 0), CommonUtils.parseInt(pagenum, 0) + 1);
		if (newcomplaintlist.size() == 0) {
			resultMap.put("hasmore", 0);
		} else {
			resultMap.put("hasmore", 1);
		}
		resultMap.put("complaintlist", complaintlist);
	}

	/**
	 * 得到评价我的列表
	 * 
	 * @param request
	 * @throws ErrException
	 */
	public void getEvaluationToMy(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String coachid = getRequestParamter(request, "coachid");
		CommonUtils.validateEmpty(coachid);
		String pagenum = getRequestParamter(request, "pagenum");
		List<EvaluationNetData> evaluationlist = cmyService.getEvaluationInfoByTo_user(CommonUtils.parseInt(coachid, 0), CommonUtils.parseInt(pagenum, 0));
		List<EvaluationNetData> newevaluationlist = cmyService.getEvaluationInfoByTo_user(CommonUtils.parseInt(coachid, 0), CommonUtils.parseInt(pagenum, 0) + 1);
		resultMap.put("evaluationlist", evaluationlist);
		if (newevaluationlist.size() == 0) {
			resultMap.put("hasmore", 0);
		} else {
			resultMap.put("hasmore", 1);

		}
	}

	/**
	 * 获得我的评价列表
	 * 
	 * @param request
	 * @throws ErrException
	 */
	public void getMyEvaluation(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String coachid = getRequestParamter(request, "coachid");
		CommonUtils.validateEmpty(coachid);
		String pagenum = getRequestParamter(request, "pagenum");
		List<EvaluationNetData> evaluationlist = cmyService.getEvaluationInfoByFrom_user(CommonUtils.parseInt(coachid, 0), CommonUtils.parseInt(pagenum, 0));
		List<EvaluationNetData> newevaluationlist = cmyService.getEvaluationInfoByFrom_user(CommonUtils.parseInt(coachid, 0), CommonUtils.parseInt(pagenum, 0) + 1);
		resultMap.put("evaluationlist", evaluationlist);
		if (newevaluationlist.size() == 0) {
			resultMap.put("hasmore", 0);
		} else {
			resultMap.put("hasmore", 1);
		}
	}

	// /**
	// * 设置消息提醒设置
	// * @param request
	// * @throws ErrException
	// */
	// public void changeSet(HttpServletRequest request) throws ErrException {
	// String coachid = getRequestParamter(request, "coachid");
	// CommonUtils.validateEmpty(coachid);
	// String newtasknoti = getRequestParamter(request, "newtasknoti");
	// CuserInfo cuser = cuserService.getCuserByCoachid(coachid);
	// if (cuser != null) {
	// cuser.setNewtasknoti(CommonUtils.parseInt(newtasknoti, 0));
	// cuserService.updateCuser(cuser);
	// } else {
	// resultMap.put("code", 1);
	// resultMap.put("message", "用户不存在");
	// }
	// }

	/**
	 * 获取我的消息的数量
	 * 
	 * @param request
	 * @throws ErrException
	 */
	public void getMessageCount(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String coachid = getRequestParamter(request, "coachid");
		CommonUtils.validateEmpty(coachid);
		CuserInfo cuser = cuserService.getCuserByCoachid(coachid);
		if (cuser != null) {
			int allnoticecount = cmyService.allnoticecount(coachid);
			resultMap.put("allnoticecount", allnoticecount);
			int noticecount = cmyService.unnoticecount(coachid);
			resultMap.put("noticecount", noticecount);
			int complaint1 = cmyService.complaintmy(coachid);
			resultMap.put("complaint1", complaint1);
			int complaint2 = cmyService.mycomplaint(coachid);
			resultMap.put("complaint2", complaint2);
			int evaluation1 = cmyService.evaluationmy(coachid);
			resultMap.put("evaluation1", evaluation1);
			int evaluation2 = cmyService.myevaluation(coachid);
			resultMap.put("evaluation2", evaluation2);
			// 学员总数
			int studentcount = cmyService.getMyStudentCount(coachid);
			resultMap.put("studentcount", studentcount);
		} else {
			resultMap.put("code", 1);
			resultMap.put("message", "用户不存在");
		}
	}
	//修改通知状态为已读
	public void setNoticeState(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String coachid = getRequestParamter(request, "coachid");
		String noticeid = getRequestParamter(request, "noticeid");
		CommonUtils.validateEmptytoMsg(coachid,"coachid不能为空");
		CommonUtils.validateEmptytoMsg(noticeid,"noticeid不能为空");
		cmyService.updateNoticeState(CommonUtils.parseInt(coachid, 0),CommonUtils.parseInt(noticeid, 0));
	}

	/**
	 * 设置教练默认的教学科目
	 * 
	 * @param request
	 * @throws ErrException
	 */
	public void setSubject(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String coachid = getRequestParamter(request, "coachid");
		CommonUtils.validateEmpty(coachid);
		String subjectid = getRequestParamter(request, "subjectid");
		CommonUtils.validateEmpty(subjectid);
		CuserInfo cuser = cuserService.getCuserByCoachid(coachid);
		if (cuser != null) {
			// 根据id在教学科目表中找到这个对象
			CsubjectInfo subjectInfo = cmyService.getSubject(CommonUtils.parseInt(subjectid, 0));
			if (subjectInfo != null) {
				cuser.setSubjectdef(CommonUtils.parseInt(subjectid, 0));
				cuserService.updateCuser(cuser);
			} else {
				resultMap.put("code", 2);
				resultMap.put("message", "教学科目不存在");
			}
		} else {
			resultMap.put("code", 3);
			resultMap.put("message", "教练用户不存在");
		}

	}

	/**
	 * 获得所有的教学科目信息
	 * 
	 * @param request
	 * @throws ErrException
	 */
	public void getAllSubject(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String coachid = getRequestParamter(request, "coachid");
		CuserInfo cuserInfo = null;
		if (!CommonUtils.isEmptyString(coachid)) {
			cuserInfo = cuserService.getCuserByCoachid(coachid);
		}
		List<CsubjectInfo> subjectlist = cmyService.getAllSubject();
		if (cuserInfo != null) {
			if (subjectlist.size() != 0) {
				for (int i = 0; i < subjectlist.size(); i++) {
					if (cuserInfo.getSubjectdef() == subjectlist.get(i).getSubjectid()) {
						subjectlist.get(i).setIsdefault(1);

					} else {
						subjectlist.get(i).setIsdefault(0);
					}
				}
			}
		}
		resultMap.put("subjectlist", subjectlist);
	}

	/**
	 * 获得所有教学用车车型
	 * 
	 * @param request
	 * @throws ErrException
	 */
	public void getAllTeachCarModel(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		List<TeachcarInfo> teachcarlist = cuserService.getTeachcarInfolist();
		resultMap.put("teachcarlist", teachcarlist);
	}

	/**
	 * 获取所有驾校信息
	 * 
	 * @param request
	 * @throws ErrException
	 */
	public void getAllSchool(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		List<DriveSchoolInfo> driveschoollist = cuserService.getDriveSchoolInfo();
		resultMap.put("schoollist", driveschoollist);
	}

	public void changeAliAccount(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String userid = getRequestParamter(request, "userid");// 用户ID
		String type = getRequestParamter(request, "type");// 类型 1教练 2学员
		String aliaccount = getRequestParamter(request, "aliaccount");// 支付宝账户
		String cashtype = getRequestParamter(request, "cashtype");// 提现类型

		CommonUtils.validateEmpty(userid);
		CommonUtils.validateEmpty(type);
		CommonUtils.validateEmpty(aliaccount);

		resultMap.putAll(cmyService.updateAliAccount(userid, type, aliaccount, cashtype));
	}

	public void getAllCoupon(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String coachid = getRequestParamter(request, "coachid");// 用户ID
		CommonUtils.validateEmpty(coachid);

		List<CouponCoach> list = cmyService.getCouponCoachList(coachid);

		resultMap.put("code", 1);
		resultMap.put("message", "操作成功");
		resultMap.put("couponlist", list);
	}

	public void applyCoupon(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String coachid = getRequestParamter(request, "coachid");// 用户ID
		String recordids = getRequestParamter(request, "recordids");
		CommonUtils.validateEmpty(coachid);
		CommonUtils.validateEmpty(recordids);
		HashMap<String, Object> result = cmyService.applyCoupon(coachid, recordids);
		resultMap.putAll(result);
	}



	//申请兑现小巴币
	public void applyCoin(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String coachid = getRequestParamter(request, "coachid");// 用户ID
		String applycoinnum = getRequestParamter(request, "coinnum");
		CommonUtils.validateEmpty(coachid);
		CommonUtils.validateEmpty(applycoinnum);
		HashMap<String, Object> result =cmyService.applyCoin(coachid,Integer.parseInt(applycoinnum));
		resultMap.putAll(result);
	}




	public void getMyAllStudent(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String coachid = getRequestParamter(request, "coachid");// 用户ID
		String pageNum = getRequestParamter(request, "pageNum");
		CommonUtils.validateEmpty(coachid);

		HashMap<String, Object> result = cmyService.getMyAllStudent(coachid, pageNum);
		resultMap.putAll(result);
	}

	public void delAliAccount(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String userid = getRequestParamter(request, "userid");// 用户ID
		String type = getRequestParamter(request, "type");
		CommonUtils.validateEmpty(userid);
		CommonUtils.validateEmpty(type);

		HashMap<String, Object> result = cmyService.delAliAccount(userid, type);
		resultMap.putAll(result);

	}

	public void changeApplyType(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String coachid = getRequestParamter(request, "coachid");// 教练ID
		String setvalue = getRequestParamter(request, "setvalue");// 修改类型
		
		CommonUtils.validateEmpty(coachid);
		CommonUtils.validateEmpty(setvalue);
		
		HashMap<String, Object> result = cmyService.changeApplyType(coachid, setvalue);
		resultMap.putAll(result);
	}
}
