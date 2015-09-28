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
import com.daoshun.guangda.pojo.CaddAddressInfo;
import com.daoshun.guangda.pojo.ComplaintInfo;
import com.daoshun.guangda.pojo.CuserInfo;
import com.daoshun.guangda.pojo.EvaluationInfo;
import com.daoshun.guangda.pojo.OrderInfo;
import com.daoshun.guangda.pojo.OrderRecordInfo;
import com.daoshun.guangda.pojo.StudentCheckInfo;
import com.daoshun.guangda.pojo.SuserInfo;
import com.daoshun.guangda.pojo.SystemSetInfo;
import com.daoshun.guangda.service.ICUserService;
import com.daoshun.guangda.service.ICtaskService;
import com.daoshun.guangda.service.ISUserService;
import com.daoshun.guangda.service.ISystemService;

/**
 * @author liukn
 * 
 */
@WebServlet("/ctask")
public class CtaskServlet extends BaseServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9034344665372163643L;

	private ICtaskService ctaskService;
	private ISUserService suserService;
	private ICUserService cuserService;
	private ISystemService systemService;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		ctaskService = (ICtaskService) applicationContext.getBean("ctaskService");
		suserService = (ISUserService) applicationContext.getBean("suserService");
		cuserService = (ICUserService) applicationContext.getBean("cuserService");
		systemService = (ISystemService) applicationContext.getBean("systemService");
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String action = getAction(request);

			if (Constant.CGETNOWTASK.equals(action) || Constant.CGETHISTASK.equals(action) || Constant.CCONFIRMON.equals(action) || Constant.CCONFIRMDOWN.equals(action)
					|| Constant.CEVALUATIONTASK.equals(action) || Constant.CSTUDENTCHECK.equals(action)) {
				if (!checkSession(request, action, resultMap)) {
					setResult(response, resultMap);
					return;
				}
			}
			//System.out.println(action+"@@@@@@@@@@@@");
			if (Constant.CGETNOWTASK.equals(action)) {
				// 获得未进行的列表
				getNowTask(request, resultMap);
			} else if (Constant.CGETHISTASK.equals(action)) {
				// 获得历史列表
				getHisTask(request, resultMap);
			} else if (Constant.CCONFIRMON.equals(action)) {
				// 确认上车
				confirmOn(request, resultMap);
			} else if (Constant.CCONFIRMDOWN.equals(action)) {
				// 确认下车
				confirmDown(request, resultMap);
			} else if (Constant.CEVALUATIONTASK.equals(action)) {
				// 教练评价任务
				evaluationTask(request, resultMap);
			} else if (Constant.CSTUDENTCHECK.equals(action)) {
				// 教练确认学生
				studentCheck(request, resultMap);
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

		if (Constant.CGETNOWTASK.equals(action)) {
			// 获得未进行的列表
			userid = request.getParameter("coachid");
			usertype = "1";
		} else if (Constant.CGETHISTASK.equals(action)) {
			// 获得历史列表
			userid = request.getParameter("coachid");
			usertype = "1";
		} else if (Constant.CCONFIRMON.equals(action)) {
			// 确认上车
			userid = request.getParameter("coachid");
			usertype = "1";
		} else if (Constant.CCONFIRMDOWN.equals(action)) {
			// 确认下车
			userid = request.getParameter("coachid");
			usertype = "1";
		} else if (Constant.CEVALUATIONTASK.equals(action)) {
			// 教练评价任务
			userid = request.getParameter("userid");
			usertype = request.getParameter("type");
		} else if (Constant.CSTUDENTCHECK.equals(action)) {
			// 教练确认学生
			userid = request.getParameter("coachid");
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
					resultMap.put(Constant.CODE, 96);
					resultMap.put(Constant.MESSAGE, "您的登录信息已经过期,请重新登录.");
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

		if (Constant.CGETNOWTASK.equals(action)) {
			// 获得未进行的列表
			userid = request.getParameter("coachid");
			usertype = "1";
		} else if (Constant.CGETHISTASK.equals(action)) {
			// 获得历史列表
			userid = request.getParameter("coachid");
			usertype = "1";
		} else if (Constant.CCONFIRMON.equals(action)) {
			// 确认上车
			userid = request.getParameter("coachid");
			usertype = "1";
		} else if (Constant.CCONFIRMDOWN.equals(action)) {
			// 确认下车
			userid = request.getParameter("coachid");
			usertype = "1";
		} else if (Constant.CEVALUATIONTASK.equals(action)) {
			// 教练评价任务
			userid = request.getParameter("userid");
			usertype = request.getParameter("type");
		} else if (Constant.CSTUDENTCHECK.equals(action)) {
			// 教练确认学生
			userid = request.getParameter("coachid");
			usertype = "1";
		}

		if (!CommonUtils.isEmptyString(userid) && !CommonUtils.isEmptyString(usertype)) {
			systemService.recordUserAction(userid, usertype, "ctask", action);
		}
	}

	/**
	 * 获得未进行的任务列表
	 * 
	 * @param request
	 * @throws ErrException
	 */
	public void getNowTask(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String coachid = request.getParameter("coachid");
		CommonUtils.validateEmpty(coachid);
		String page = request.getParameter("pagenum");
		// Date nowtime = new Date();
		// String newtime=CommonUtils.getTimeFormat(nowtime, "yyyy-MM-dd");
		// 得到未完成订单
		//List<OrderInfo> orderInfolist = ctaskService.getOrderInfoListBycoachid(coachid, CommonUtils.parseInt(page, 0), Constant.COUNT_NUM);
		List<OrderInfo> orderInfolist = ctaskService.getOrderNoExistAgreeInfoListBycoachid(coachid, CommonUtils.parseInt(page, 0), Constant.COUNT_NUM);
		// 得到coachstate为1的订单
		List<OrderInfo> orderlist = ctaskService.getOrderInfoListBycoach(1, CommonUtils.parseInt(coachid, 0));
		if (orderInfolist != null) {
			// 现在时间 c0
			Calendar c0 = Calendar.getInstance();
			// 现在时间往后一小时 c1
			Calendar c1 = Calendar.getInstance();
			c1.add(Calendar.HOUR, +1);
			for (int i = 0; i < orderInfolist.size(); i++) {
				// 如果coachstate为0 且已经过了任务结束时间，当前还没有进行中任务 那么可以上车
				if (orderInfolist.get(i).getCoachstate() == 0 && c0.getTime().after(orderInfolist.get(i).getEnd_time()) && orderlist == null) {
					orderInfolist.get(i).setState(1);
				} else {
					// 如果coachstate为0
					if (orderInfolist.get(i).getCoachstate() == 0) {
						// 距离开始时间超过一小时 接口state为0
						if ((c1.getTime().before(orderInfolist.get(i).getStart_time()))) {
							orderInfolist.get(i).setState(0);
						} else if (c0.getTime().before(orderInfolist.get(i).getStart_time()) && c1.getTime().after(orderInfolist.get(i).getStart_time()) && orderlist.size() == 0) {
							// 时间在开始时间一小时内，且教练当前不在任务中
							orderInfolist.get(i).setState(1);
						} else if (c0.getTime().before(orderInfolist.get(i).getStart_time()) && c1.getTime().after(orderInfolist.get(i).getStart_time()) && orderlist.size() != 0) {
							// 时间在开始一小时内，但是教练有任务在进行中
							orderInfolist.get(i).setState(2);
						} else if (c0.getTime().after(orderInfolist.get(i).getStart_time()) && orderlist.size() == 0) {
							// 时间在开始时间之后 无进行中任务
							orderInfolist.get(i).setState(1);
						} else if (c0.getTime().after(orderInfolist.get(i).getStart_time()) && orderlist.size() != 0) {
							// 时间在开始时间之后 但是有进行中任务
							orderInfolist.get(i).setState(2);
						}
					} else if (orderInfolist.get(i).getCoachstate() == 1) {
						orderInfolist.get(i).setState(3);
					}
				}
				//如果
				if(orderInfolist.get(i).getStudentstate()==4 && orderInfolist.get(i).getCoachstate()!=4){
					orderInfolist.get(i).setAgreecancel(0);
				}else{
					orderInfolist.get(i).setAgreecancel(1);
				}
				// 将学生信息保存到list中
				SuserInfo suser = suserService.getUserById(String.valueOf(orderInfolist.get(i).getStudentid()));
				if (suser != null) {
					suser.setAvatarurl(cuserService.backUrl(suser.getAvatar()));
					orderInfolist.get(i).setStudentinfo(suser);
				}
				// orderInfolist.get(i).getStudentinfo().setAvatarurl(cuserService.backUrl(); //cuserService.backUrl(orderInfolist.get(i).getStudentinfo().getAvatar())
				ctaskService.updateOrderInfo(orderInfolist.get(i));
			}
			resultMap.put("tasklist", orderInfolist);
		}
		List<OrderInfo> neworderInfolist = ctaskService.getOrderNoExistAgreeInfoListBycoachid(coachid, CommonUtils.parseInt(page, 0) + 1, Constant.COUNT_NUM);
		if (neworderInfolist.size() == 0) {
			resultMap.put("hasmore", 0);
		} else {
			resultMap.put("hasmore", 1);
		}
	}

	/**
	 * 分页获得历史列表
	 * 
	 * @param request
	 * @throws ErrException
	 */
	public void getHisTask(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String coachid = request.getParameter("coachid");
		CommonUtils.validateEmpty(coachid);
		String page = request.getParameter("pagenum");
		CommonUtils.validateEmpty(page);
		// 获得完成任务的列表
		List<OrderInfo> orderInfolist = ctaskService.getHistoryOrderListByCoach(CommonUtils.parseInt(coachid, 0), CommonUtils.parseInt(page, 0), Constant.COUNT_NUM);
		if (orderInfolist != null) {
			for (int i = 0; i < orderInfolist.size(); i++) {
				SuserInfo suser = suserService.getUserById(String.valueOf(orderInfolist.get(i).getStudentid()));
				if (suser != null) {
					suser.setAvatarurl(cuserService.backUrl(suser.getAvatar()));
					suserService.updateUserInfo(suser);
					orderInfolist.get(i).setStudentinfo(suser);
				}
				EvaluationInfo studentscore = ctaskService.getEvaluationInfoByorder(orderInfolist.get(i).getOrderid(), orderInfolist.get(i).getStudentid(), orderInfolist.get(i).getCoachid(), 1);
				EvaluationInfo coachscore = ctaskService.getEvaluationInfoByorder(orderInfolist.get(i).getOrderid(), orderInfolist.get(i).getCoachid(), orderInfolist.get(i).getStudentid(), 2);
				if (studentscore != null) {
					// 假设 分数一是综合分数
					studentscore.setScore(studentscore.getScore1());
					orderInfolist.get(i).setStudentscore(studentscore);
				}
				if (coachscore != null) {
					coachscore.setScore(coachscore.getScore1());
					orderInfolist.get(i).setCoachscore(coachscore);
				}
				ctaskService.updateOrderInfo(orderInfolist.get(i));
			}
			// 重新得到修改后的list 返回
			List<OrderInfo> neworderInfolist = ctaskService.getHistoryOrderListByCoach(CommonUtils.parseInt(coachid, 0), CommonUtils.parseInt(page, 0), Constant.COUNT_NUM);
			resultMap.put("tasklist", neworderInfolist);
		}
		List<OrderInfo> neworderInfolist = ctaskService.getHistoryOrderListByCoach(CommonUtils.parseInt(coachid, 0), CommonUtils.parseInt(page, 0) + 1, Constant.COUNT_NUM);
		if (neworderInfolist.size() == 0) {
			resultMap.put("hasmore", 0);
		} else {
			resultMap.put("hasmore", 1);
		}
	}

	/**
	 * 教练确认上车
	 * 
	 * @param request
	 * @throws ErrException
	 */
	public void confirmOn(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String coachid = request.getParameter("coachid");
		CommonUtils.validateEmpty(coachid);
		String orderid = String.valueOf(request.getParameter("orderid"));
		CommonUtils.validateEmpty(orderid);
		String lat = request.getParameter("lat");
		String lon = request.getParameter("lon");
		String detail = request.getParameter("detail");
		// 根据orderid 教练id 找到确认上车订单
		OrderRecordInfo orderRecordInfo = ctaskService.getOrderRecordInfo(CommonUtils.parseInt(orderid, 0), CommonUtils.parseInt(coachid, 0), 3);
		// 现在时间 和 现在时间延后一小时时间
		Calendar c0 = Calendar.getInstance();
		Calendar c1 = Calendar.getInstance();
		c1.add(Calendar.HOUR, +1);
		if (orderRecordInfo == null) {
			// 如果确认信息为空，则根据订单找到订单信息
			OrderInfo orderInfo = (OrderInfo) ctaskService.getOrderInfo(CommonUtils.parseInt(orderid, 0));
			if (orderInfo != null) {
				if (orderInfo.getCoachstate() == 2) {
					// 订单状态为2 已完成订单
					resultMap.put("code", 3);
					resultMap.put("message", "该任务已经过期了,无法确认上车");
				} else if ((orderInfo.getCoachstate() == 0 && c0.getTime().before(orderInfo.getStart_time()) && c1.getTime().after(orderInfo.getStart_time()))
						|| (orderInfo.getCoachstate() == 0 && c0.getTime().after(orderInfo.getEnd_time())) || ((orderInfo.getCoachstate() == 0) && c0.getTime().after(orderInfo.getStart_time()))) {
					// 任务未完成 开始时间前一小时 一直到以后都可以上车
					orderInfo.setCoachstate(1);
					orderInfo.setState(3);
					ctaskService.updateOrderInfo(orderInfo);
					OrderRecordInfo newOrderRecordInfo = new OrderRecordInfo();
					newOrderRecordInfo.setOrderid(CommonUtils.parseInt(orderid, 0));
					newOrderRecordInfo.setUserid(CommonUtils.parseInt(coachid, 0));
					newOrderRecordInfo.setAddtime(new Date());
					newOrderRecordInfo.setOperation(3);
					if (!CommonUtils.isEmptyString(lat)) {
						newOrderRecordInfo.setLatitude(lat);
					}
					if (!CommonUtils.isEmptyString(lon)) {
						newOrderRecordInfo.setLongitude(lon);
					}
					if (!CommonUtils.isEmptyString(detail)) {
						newOrderRecordInfo.setDetail(detail);
					} else {
						CaddAddressInfo caddAddressInfo = cuserService.getCurrentAddressBycoachid(coachid);
						if (caddAddressInfo != null) {
							newOrderRecordInfo.setLatitude(caddAddressInfo.getLatitude());
							newOrderRecordInfo.setLongitude(caddAddressInfo.getLongitude());
							newOrderRecordInfo.setDetail(caddAddressInfo.getDetail());
						}
					}
					ctaskService.addOrderRecordInfo(newOrderRecordInfo);
				} else if (orderInfo.getCoachstate() == 0 && c1.getTime().before(orderInfo.getStart_time())) {
					// 开始前超过一小时
					resultMap.put("code", 4);
					resultMap.put("message", "该任务还未到开始时间,无法确认上车");
				}
			}
		} else {
			// 如果有记录 则表示已经确认过上车
			resultMap.put("code", 2);
			resultMap.put("message", "您已经确认上车过了");
		}
	}

	/**
	 * 教练确认下车
	 * 
	 * @param request
	 * @throws ErrException
	 */
	public void confirmDown(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String coachid = request.getParameter("coachid");
		CommonUtils.validateEmpty(coachid);
		String orderid = String.valueOf(request.getParameter("orderid"));
		CommonUtils.validateEmpty(orderid);
		String lat = request.getParameter("lat");
		String lon = request.getParameter("lon");
		String detail = request.getParameter("detail");
		OrderRecordInfo orderRecordInfo = ctaskService.getOrderRecordInfo(CommonUtils.parseInt(orderid, 0), CommonUtils.parseInt(coachid, 0), 4);
		Calendar c0 = Calendar.getInstance();
		if (orderRecordInfo == null) {
			// 没有确认过下车
			OrderInfo orderInfo = (OrderInfo) ctaskService.getOrderInfo(CommonUtils.parseInt(orderid, 0));
			if (orderInfo.getCoachstate() == 1 && c0.getTime().before(orderInfo.getEnd_time())) {
				resultMap.put("code", 3);
				resultMap.put("message", "该任务还未到结束时间,无法确认下车");
			} else if (orderInfo.getCoachstate() == 1 && c0.getTime().after(orderInfo.getEnd_time())) {
				orderInfo.setCoachstate(2);
				ctaskService.updateOrderInfo(orderInfo);

				OrderRecordInfo newOrderRecordInfo = new OrderRecordInfo();
				newOrderRecordInfo.setOrderid(CommonUtils.parseInt(orderid, 0));
				newOrderRecordInfo.setUserid(CommonUtils.parseInt(coachid, 0));
				newOrderRecordInfo.setAddtime(new Date());
				newOrderRecordInfo.setOperation(4);
				if (!CommonUtils.isEmptyString(lat)) {
					newOrderRecordInfo.setLatitude(lat);
				}
				if (!CommonUtils.isEmptyString(lon)) {
					newOrderRecordInfo.setLongitude(lon);
				}
				if (!CommonUtils.isEmptyString(detail)) {
					newOrderRecordInfo.setDetail(detail);
				}
				
				ctaskService.addOrderRecordInfo(newOrderRecordInfo);
				// 判断订单是否需要结算
				if (orderInfo.getOver_time() == null) {// 订单未被结算
					// 判断订单是否有投诉,是否已经评论过,是否已经过了结算时间
					ctaskService.SettlementOrderWhenCoachDown(orderInfo.getOrderid());
				}
			}
		} else {
			resultMap.put("code", 2);
			resultMap.put("message", "您已经确认下车过了");
		}
	}

	/**
	 * 评价任务
	 * 
	 * @param request
	 * @throws ErrException
	 */
	public void evaluationTask(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String userid = request.getParameter("userid");
		CommonUtils.validateEmpty(userid);
		String type = request.getParameter("type");
		CommonUtils.validateEmpty(type);
		String orderid = request.getParameter("orderid");
		CommonUtils.validateEmpty(orderid);
		String score1 = request.getParameter("score1");
		CommonUtils.validateEmpty(score1);
		String score2 = request.getParameter("score2");
		CommonUtils.validateEmpty(score2);
		String score3 = request.getParameter("score3");
		CommonUtils.validateEmpty(score3);
		String content = request.getParameter("content");
		
		Calendar c0 = Calendar.getInstance();
		if (CommonUtils.parseInt(type, 0) == 1) {// 教练评价学员

			EvaluationInfo evaluationInfo = ctaskService.getEvaluationInfo(CommonUtils.parseInt(orderid, 0), CommonUtils.parseInt(userid, 0), 2);
			if (evaluationInfo == null) {
				// 如果评价信息为空 得到订单信息
				OrderInfo orderInfo = (OrderInfo) ctaskService.getOrderInfo(CommonUtils.parseInt(orderid, 0));
				if (orderInfo != null) {
					if (orderInfo.getCoachstate() == 0 || (orderInfo.getCoachstate() == 1 && c0.getTime().after(orderInfo.getEnd_time()))) {
						// 订单未开始 或者 订单进行中 但是早于结束时间
						resultMap.put("code", 3);
						resultMap.put("message", "任务未完成,无法评价");
					} else if (orderInfo.getCoachstate() == 2 || (orderInfo.getCoachstate() == 1 && c0.getTime().after(orderInfo.getEnd_time()))) {
						// 完成订单 但是未评价 或者 订单进行中 但是超过结束时间
						orderInfo.setCoachstate(2);
						EvaluationInfo evaluation = new EvaluationInfo();
						evaluation.setScore1(CommonUtils.parseFloat(score1, 0));
						evaluation.setScore2(CommonUtils.parseFloat(score2, 0));
						evaluation.setScore3(CommonUtils.parseFloat(score3, 0));
						evaluation.setContent(content);
						evaluation.setOrder_id(CommonUtils.parseInt(orderid, 0));
						evaluation.setType(2);
						evaluation.setFrom_user(CommonUtils.parseInt(userid, 0));
						evaluation.setTo_user(orderInfo.getStudentid());
						evaluation.setAddtime(new Date());
						ctaskService.addEvaluationInfo(evaluation, 1);
						ctaskService.updateOrderInfo(orderInfo);
						// 更新学员的评分信息
						SuserInfo suser = ctaskService.getSuserInfoBysuserid(orderInfo.getStudentid());
						if (suser != null) {
							List<EvaluationInfo> list = ctaskService.getEvaluationList(CommonUtils.parseInt(userid, 0), 2);
							int count = 0;
							if (list != null)
								count = list.size();
							float score = (CommonUtils.parseFloat(score1, 0) + CommonUtils.parseFloat(score2, 0) + CommonUtils.parseFloat(score3, 0) + suser.getScore() * count) / (count + 1);
							float num = (float) (Math.round(score * 100) / 100f);
							suser.setScore(num);
							ctaskService.updateSuserInfo(suser);
						}
					}
				}
			} else {
				resultMap.put("code", 2);
				resultMap.put("message", "您已经对该任务评价过了");
			}
		} else {
			// type=2
			EvaluationInfo evaluationInfo = ctaskService.getEvaluationInfo(CommonUtils.parseInt(orderid, 0), CommonUtils.parseInt(userid, 0), 1);
			if (evaluationInfo == null) {
				OrderInfo orderInfo = (OrderInfo) ctaskService.getOrderInfo(CommonUtils.parseInt(orderid, 0));
				CuserInfo cuser = ctaskService.getCoachInfoById(orderInfo.getCoachid());
				SuserInfo student = ctaskService.getSuserInfoBysuserid(orderInfo.getStudentid());
				if (orderInfo != null) {
					List<ComplaintInfo> coplaint = ctaskService.getComplaintInfoByorderid(orderInfo.getOrderid());
					if (coplaint != null && coplaint.size() > 0) {// 有投诉的话,无需处理

					} else {// 结算
						if (orderInfo.getOver_time() == null) {// 订单未结算过
							ctaskService.SettlementOrder(orderInfo.getOrderid());
						}
					}
					
					EvaluationInfo evaluation = new EvaluationInfo();
					evaluation.setScore1(CommonUtils.parseFloat(score1, 0));
					evaluation.setScore2(CommonUtils.parseFloat(score2, 0));
					evaluation.setScore3(CommonUtils.parseFloat(score3, 0));
					evaluation.setContent(content);
					evaluation.setOrder_id(CommonUtils.parseInt(orderid, 0));
					evaluation.setType(1);
					evaluation.setFrom_user(CommonUtils.parseInt(userid, 0));
					evaluation.setTo_user(orderInfo.getCoachid());
					evaluation.setAddtime(new Date());
					ctaskService.addEvaluationInfo(evaluation, 2);

					//CuserInfo cuser = ctaskService.getCoachInfoById(orderInfo.getCoachid());
					if (cuser != null) {
						/*List<EvaluationInfo> list = ctaskService.getEvaluationList(CommonUtils.parseInt(userid, 0), 1);
						int count = 0;
						if (list != null)
							count = list.size();
						float score = (CommonUtils.parseFloat(score1, 0) + CommonUtils.parseFloat(score2, 0) + CommonUtils.parseFloat(score3, 0) + cuser.getScore() * count) / (count + 1);
						float num = (float) (Math.round(score * 100) / 100f);*/
						//更新教练的星级 
						float score=0;
						if(cuser.getScore()<=0){
							score=(CommonUtils.parseFloat(score1, 0) + CommonUtils.parseFloat(score2, 0) + CommonUtils.parseFloat(score3, 0))/3 ;
						}else{
							score=(CommonUtils.parseFloat(score1, 0) + CommonUtils.parseFloat(score2, 0) + CommonUtils.parseFloat(score3, 0) + cuser.getScore())/4 ;
						}
						//float num=Math.round(score);
						cuser.setScore(score);
						ctaskService.updateCoachInfo(cuser);
					}
				} else {
					resultMap.put("code", 2);
					resultMap.put("message", "您已经对该任务评价过了");
				}
			}
		}
	}

	/**
	 * 教练认证学生
	 * 
	 * @param request
	 * @throws ErrException
	 */
	public void studentCheck(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String idcardf = String.valueOf(request.getAttribute("idcardf"));
		CommonUtils.validateEmpty(idcardf);
		String idcardb = String.valueOf(request.getAttribute("idcardb"));
		CommonUtils.validateEmpty(idcardb);
		String cardpic = String.valueOf(request.getAttribute("cardpic"));
		CommonUtils.validateEmpty(cardpic);
		String studentid = getRequestParamter(request, "studentid");
		CommonUtils.validateEmpty(studentid);
		String coachid = getRequestParamter(request, "coachid");
		CommonUtils.validateEmpty(coachid);
		// 设置上传信息
		SuserInfo suserInfo = (SuserInfo) ctaskService.getSuserInfoBysuserid(CommonUtils.parseInt(studentid, 0));
		if (suserInfo != null) {
			if (idcardf != null) {
				suserInfo.setCcheck_idcardpicf(CommonUtils.parseInt(idcardf, 0));
			}
			if (idcardb != null) {
				suserInfo.setCcheck_idcardpicb(CommonUtils.parseInt(idcardb, 0));
			}
			if (cardpic != null) {
				suserInfo.setCcheck_pic(CommonUtils.parseInt(cardpic, 0));
			}
			suserInfo.setCoachstate(1);
			ctaskService.updateSuserInfo(suserInfo);
		}
		// 生成认证记录数据
		StudentCheckInfo studentCheckInfo = new StudentCheckInfo();
		studentCheckInfo.setAddtime(new Date());
		studentCheckInfo.setCoachid(CommonUtils.parseInt(coachid, 0));
		studentCheckInfo.setStudentid(CommonUtils.parseInt(studentid, 0));
		ctaskService.addStudentCheckInfo(studentCheckInfo);
	}
}
