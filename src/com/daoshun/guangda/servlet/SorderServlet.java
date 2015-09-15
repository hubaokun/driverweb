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
import com.daoshun.guangda.NetData.ComplaintNetData;
import com.daoshun.guangda.pojo.ComplaintInfo;
import com.daoshun.guangda.pojo.ComplaintSetInfo;
import com.daoshun.guangda.pojo.CuserInfo;
import com.daoshun.guangda.pojo.OrderInfo;
import com.daoshun.guangda.pojo.OrderRecordInfo;
import com.daoshun.guangda.pojo.SuserInfo;
import com.daoshun.guangda.pojo.SystemSetInfo;
import com.daoshun.guangda.service.ICUserService;
import com.daoshun.guangda.service.ICscheduleService;
import com.daoshun.guangda.service.ISOrderService;
import com.daoshun.guangda.service.ISUserService;
import com.daoshun.guangda.service.ISystemService;

@WebServlet("/sorder")
public class SorderServlet extends BaseServlet {

	private static final long serialVersionUID = 6998440419757851253L;
	private ISOrderService sorderService;
	private ISystemService systemService;
	private ICUserService cuserService;
	private ISUserService suserService;
	private ICscheduleService cscheduleService;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		sorderService = (ISOrderService) applicationContext.getBean("sorderService");
		systemService = (ISystemService) applicationContext.getBean("systemService");
		cuserService = (ICUserService) applicationContext.getBean("cuserService");
		suserService = (ISUserService) applicationContext.getBean("suserService");
		cscheduleService = (ICscheduleService) applicationContext.getBean("cscheduleService");
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String action = getAction(request);

			if (Constant.GETCOMPLAINTTOMY.equals(action) || Constant.GETMYCOMPLAINT.equals(action) || Constant.GETWAITEVALUATIONORDER.equals(action) || Constant.GETUNCOMPLETEORDER.equals(action)
					|| Constant.GETCOMPLETEORDER.equals(action) || Constant.GETORDERDETAIL.equals(action) || Constant.COMPLAINT.equals(action) || Constant.CANCELORDER.equals(action)
					|| Constant.CANCELCOMPLAINT.equals(action) || Constant.CONFIRMON.equals(action) || Constant.CONFIRMDOWN.equals(action) || Constant.CANCELORDERAGREE.equals(action)
					||Constant.GETCOMPLAINTORDER.equals(action)) {
				if (!checkSession(request, action, resultMap)) {
					setResult(response, resultMap);
					return;
				}
			}
			//System.out.println(action+"##########");
			if (Constant.GETCOMPLAINTTOMY.equals(action)) {
				// 获取被投诉列表
				getComplaintToMy(request, resultMap);
			} else if (Constant.GETMYCOMPLAINT.equals(action)) {
				// 获取投诉列表
				getMyComplaint(request, resultMap);
			} else if (Constant.GETWAITEVALUATIONORDER.equals(action)) {
				// 获取待评价订单
				getWaitEvaluationOrder(request, resultMap);
			} else if (Constant.GETUNCOMPLETEORDER.equals(action)) {
				// 获取未完成订单
				getUnCompleteOrder(request, resultMap);
			}else if (Constant.GETCOMPLAINTORDER.equals(action)) {
				// 获取被投诉订单
				getComplaintOrder(request, resultMap);
			}else if (Constant.GETWAITDEALWITHORDER.equals(action)) {
				// #########特殊订单：获取待处理订单---》现在移到投诉列表中getComplaintOrder了#############
				getWaitDealwithOrder(request, resultMap);
			} else if (Constant.GETCOMPLETEORDER.equals(action)) {
				// 获取已完成订单
				getCompleteOrder(request, resultMap);
			} else if (Constant.GETORDERDETAIL.equals(action)) {
				// 获取订单详细
				getOrderDetail(request, resultMap);
			} else if (Constant.GETCOMPLAINTREASON.equals(action)) {
				// 获取投诉原因
				getComplaintReason(request, resultMap);
			} else if (Constant.COMPLAINT.equals(action)) {
				// 投诉
				complaint(request, resultMap);
			} else if (Constant.CANCELORDER.equals(action)) {
				// 取消订单
				cancelOrder(request, resultMap);
			} else if (Constant.CANCELORDERAGREE.equals(action)) {
				// 取消订单教练是否同意
				cancelOrderAgree(request, resultMap);
			} else if (Constant.CANCELCOMPLAINT.equals(action)) {
				// 取消订单投诉
				CancelComplaint(request, resultMap);
			} else if (Constant.CONFIRMON.equals(action)) {
				// 确认上车
				confirmOn(request, resultMap);
			} else if (Constant.CONFIRMDOWN.equals(action)) {
				confirmDown(request, resultMap);
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

		if (Constant.GETCOMPLAINTTOMY.equals(action)) {
			// 获取被投诉列表
			userid = getRequestParamter(request, "studentid");
			usertype = "2";
		} else if (Constant.GETMYCOMPLAINT.equals(action)) {
			// 获取投诉列表
			userid = getRequestParamter(request, "studentid");
			usertype = "2";
		} else if (Constant.GETWAITEVALUATIONORDER.equals(action)) {
			// 获取待评价订单
			userid = getRequestParamter(request, "studentid");
			usertype = "2";
		} else if (Constant.GETUNCOMPLETEORDER.equals(action)) {
			// 获取未完成订单
			userid = getRequestParamter(request, "studentid");
			usertype = "2";
		} else if (Constant.GETCOMPLETEORDER.equals(action)) {
			// 获取已完成订单
			userid = getRequestParamter(request, "studentid");
			usertype = "2";
		} else if (Constant.GETORDERDETAIL.equals(action)) {
			// 获取订单详细
			userid = getRequestParamter(request, "studentid");
			usertype = "2";
		} else if (Constant.GETCOMPLAINTREASON.equals(action)) {
			// 获取投诉原因
		} else if (Constant.COMPLAINT.equals(action)) {
			// 投诉
			userid = getRequestParamter(request, "userid");
			usertype = getRequestParamter(request, "type");
		} else if (Constant.CANCELORDER.equals(action)) {
			// 取消订单
			userid = getRequestParamter(request, "studentid");
			usertype = "2";
		} else if (Constant.CANCELORDERAGREE.equals(action)) {
			// 教练同意取消订单
			userid = request.getParameter("coachid");
			usertype = "1";
		} else if (Constant.CANCELCOMPLAINT.equals(action)) {
			// 取消订单投诉
			userid = getRequestParamter(request, "studentid");
			usertype = "2";
		} else if (Constant.CONFIRMON.equals(action)) {
			// 确认上车
			userid = getRequestParamter(request, "studentid");
			usertype = "2";
		} else if (Constant.CONFIRMDOWN.equals(action)) {//
			// 确认下车
			userid = getRequestParamter(request, "studentid");
			usertype = "2";
		}else if (Constant.GETCOMPLAINTORDER.equals(action)) {
			// 待处理订单列表
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
//								System.out.print("SorderServlet cuser != null checkSession-----111111111");
								resultMap.put(Constant.MESSAGE, "您的登录信息已经过期,请重新登录.");
								return false;
							} else {
								return true;
							}
						} else {
							resultMap.put(Constant.CODE, 95);
//							System.out.print("SorderServlet checkSession-----222222222");
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
//								System.out.print("SorderServlet suser != null checkSession-----111111111");
								resultMap.put(Constant.MESSAGE, "您的登录信息已经过期,请重新登录.");
								return false;
							} else {
								return true;
							}
						} else {
							resultMap.put(Constant.CODE, 95);
							System.out.println(token.toString());
							System.out.println(suser.getToken().toString());
//							System.out.print("SorderServlet 2user != null checkSession-----22222222");
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

		if (Constant.GETCOMPLAINTTOMY.equals(action)) {
			// 获取被投诉列表
			userid = getRequestParamter(request, "studentid");
			usertype = "2";
		} else if (Constant.GETMYCOMPLAINT.equals(action)) {
			// 获取投诉列表
			userid = getRequestParamter(request, "studentid");
			usertype = "2";
		} else if (Constant.GETWAITEVALUATIONORDER.equals(action)) {
			// 获取待评价订单
			userid = getRequestParamter(request, "studentid");
			usertype = "2";
		} else if (Constant.GETUNCOMPLETEORDER.equals(action)) {
			// 获取未完成订单
			userid = getRequestParamter(request, "studentid");
			usertype = "2";
		} else if (Constant.GETCOMPLETEORDER.equals(action)) {
			// 获取已完成订单
			userid = getRequestParamter(request, "studentid");
			usertype = "2";
		} else if (Constant.GETORDERDETAIL.equals(action)) {
			// 获取订单详细
			userid = getRequestParamter(request, "studentid");
			usertype = "2";
		} else if (Constant.GETCOMPLAINTREASON.equals(action)) {
			// 获取投诉原因
		} else if (Constant.COMPLAINT.equals(action)) {
			// 投诉
			userid = getRequestParamter(request, "userid");
			usertype = getRequestParamter(request, "type");
		} else if (Constant.CANCELORDER.equals(action)) {
			// 取消订单
			userid = getRequestParamter(request, "userid");
			usertype = "2";
		} else if (Constant.CANCELORDERAGREE.equals(action)) {
			// 教练同意取消订单
			userid = request.getParameter("coachid");
			usertype = "1";
		}else if (Constant.CANCELCOMPLAINT.equals(action)) {
			// 取消订单投诉
			userid = getRequestParamter(request, "studentid");
			usertype = "2";
		} else if (Constant.CONFIRMON.equals(action)) {
			// 确认上车
			userid = getRequestParamter(request, "studentid");
			usertype = "2";
		} else if (Constant.CONFIRMDOWN.equals(action)) {
			// 确认下车
			userid = getRequestParamter(request, "studentid");
			usertype = "2";
		}

		if (!CommonUtils.isEmptyString(userid) && !CommonUtils.isEmptyString(usertype)) {
			systemService.recordUserAction(userid, usertype, "sorder", action);
		}
	}

	public void getComplaintToMy(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String studentid = getRequestParamter(request, "studentid");
		String pagenum = getRequestParamter(request, "pagenum");
		CommonUtils.validateEmpty(studentid);
		List<ComplaintNetData> complaintlist = sorderService.getComplaintToMy(studentid, pagenum);
		int hasmore = sorderService.getComplaintToMyMore(studentid, pagenum);
		resultMap.put("hasmore", hasmore);
		resultMap.put("complaintlist", complaintlist);
	}

	public void getMyComplaint(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String studentid = getRequestParamter(request, "studentid");
		String pagenum = getRequestParamter(request, "pagenum");
		CommonUtils.validateEmpty(studentid);
		List<ComplaintNetData> complaintlist = sorderService.getMyComplaint(studentid, pagenum);
		int hasmore = sorderService.getMyComplaintMore(studentid, pagenum);
		resultMap.put("hasmore", hasmore);
		resultMap.put("complaintlist", complaintlist);
	}

	/**
	 * 取得待评价订单列表
	 * 
	 * @param request
	 * @throws ErrException
	 */
	public void getWaitEvaluationOrder(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String studentid = getRequestParamter(request, "studentid");
		String pagenum = getRequestParamter(request, "pagenum");
		CommonUtils.validateEmpty(studentid);
		List<OrderInfo> orderlist = sorderService.getWaitEvaluationOrder(studentid, pagenum);
		int hasmore = sorderService.getWaitEvaluationOrderMore(studentid, String.valueOf(CommonUtils.parseInt(pagenum, 0)+ 1));
		resultMap.put("hasmore", hasmore);
		resultMap.put("orderlist", orderlist);
	}
	/**
	 * ##########待处理订单列表 :暂时未使用 ，这个结果在被投诉列表中
	 * 
	 * @param request
	 * @throws ErrException
	 */
	public void getWaitDealwithOrder(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String studentid = getRequestParamter(request, "studentid");
		String pagenum = getRequestParamter(request, "pagenum");
		CommonUtils.validateEmpty(studentid);
		List<OrderInfo> orderlist = sorderService.getWaitDealwithOrder(studentid, pagenum);
		int hasmore = sorderService.getWaitEvaluationOrderMore(studentid, String.valueOf(CommonUtils.parseInt(pagenum, 0)+ 1));
		resultMap.put("hasmore", hasmore);
		resultMap.put("orderlist", orderlist);
	}

	/**
	 * 取得未完成订单列表
	 * 
	 * @param request
	 * @throws ErrException
	 */
	public void getUnCompleteOrder(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String studentid = getRequestParamter(request, "studentid");
		String pagenum = getRequestParamter(request, "pagenum");
		CommonUtils.validateEmpty(studentid);
		List<OrderInfo> orderlist = sorderService.getUnCompleteOrder(studentid, pagenum);
		int hasmore = sorderService.getUnCompleteOrderMore(studentid, String.valueOf(CommonUtils.parseInt(pagenum, 0) + 1));
		resultMap.put("hasmore", hasmore);
		resultMap.put("orderlist", orderlist);
	}
	/**
	 * 取得被投诉订单列表[待处理]
	 * 
	 * @param request
	 * @throws ErrException
	 */
	public void getComplaintOrder(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String studentid = getRequestParamter(request, "studentid");
		String pagenum = getRequestParamter(request, "pagenum");
		CommonUtils.validateEmpty(studentid);
		List<OrderInfo> orderlist = sorderService.getComplaintOrder(studentid, pagenum);
		int hasmore = sorderService.getComplaintOrderMore(studentid, String.valueOf(CommonUtils.parseInt(pagenum, 0) + 1));
		resultMap.put("hasmore", hasmore);
		resultMap.put("orderlist", orderlist);
	}
	
	/**
	 * 取得已完成订单列表
	 * 
	 * @param request
	 * @throws ErrException
	 */
	public void getCompleteOrder(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String studentid = getRequestParamter(request, "studentid");
		String pagenum = getRequestParamter(request, "pagenum");
		CommonUtils.validateEmpty(studentid);
		List<OrderInfo> orderlist = sorderService.getCompleteOrder(studentid, pagenum);
		int hasmore = sorderService.getCompleteOrderMore(studentid,String.valueOf(CommonUtils.parseInt(pagenum, 0)+ 1));
		resultMap.put("hasmore", hasmore);
		resultMap.put("orderlist", orderlist);
	}

	public void getOrderDetail(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String orderid = getRequestParamter(request, "orderid");
		CommonUtils.validateEmpty(orderid);
		OrderInfo orderinfo = sorderService.getCompleteOrder(orderid);
		if (orderinfo != null) {
			resultMap.put("orderinfo", orderinfo);
		} else {
			resultMap.put("code", 2);
			resultMap.put("message", "订单不存在");
		}
	}

	public void getComplaintReason(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String type = getRequestParamter(request, "type");
		CommonUtils.validateEmpty(type);
		List<ComplaintSetInfo> reasonlist = sorderService.getComplaintReason(type);
		resultMap.put("reasonlist", reasonlist);
	}

	public void complaint(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String userid = getRequestParamter(request, "userid");
		String orderid = getRequestParamter(request, "orderid");
		String type = getRequestParamter(request, "type");
		String reason = getRequestParamter(request, "reason");
		String content = getRequestParamter(request, "content");
		CommonUtils.validateEmpty(userid);
		CommonUtils.validateEmpty(orderid);
		CommonUtils.validateEmpty(type);
		CommonUtils.validateEmpty(reason);
		CommonUtils.validateEmpty(content);
		//学员投诉时，把studentstate设置为5
		OrderInfo order=sorderService.getOrderById(CommonUtils.parseInt(orderid, 0));
		order.setStudentstate(5);
		sorderService.updateOrderInfo(order);
		sorderService.addComplaint(userid, orderid, type, reason, content);
	}

	public void cancelOrder(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String studentid = getRequestParamter(request, "studentid");
		String orderid = getRequestParamter(request, "orderid");
		CommonUtils.validateEmpty(studentid);
		CommonUtils.validateEmpty(orderid);
		//int code = sorderService.cancelOrder(studentid, orderid);
		int code = sorderService.cancelOrderByStudent(studentid, orderid);
		if (code == -1) {
			resultMap.put("code", 3);
			resultMap.put("message", "取消订单失败,订单不存在");
			return;
		} else if (code != 0) {
			resultMap.put("code", 2);
			resultMap.put("message", "取消订单失败,您必须在订单开始" + code / 60 + "小时前取消订单");
			return;
		}else if (code == 0) {
			resultMap.put("code", 1);
			resultMap.put("message", "取消成功");
			return;
		}
	}
	/**
	 * 教练同意取消订单
	 * @param request
	 * @param resultMap
	 * @throws ErrException
	 */
	public void cancelOrderAgree(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String agree = getRequestParamter(request, "agree");
		String orderid = getRequestParamter(request, "orderid");
		String coachid = getRequestParamter(request, "coachid");
		CommonUtils.validateEmpty(agree);
		CommonUtils.validateEmpty(orderid);
		CommonUtils.validateEmpty(coachid);
		int code = sorderService.cancelOrderByCoach(orderid,agree);
		if (code == -1) {
			resultMap.put("code", 3);
			resultMap.put("message", "取消订单失败,订单不存在");
			return;
		} 
		else
		{
			//根据教练当前开课状态来设置教练表中coursestate
			cscheduleService.getCoachStateByFunction(coachid, 5, 5, 23, 0);
		}
	}

	public void CancelComplaint(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String studentid = getRequestParamter(request, "studentid");
		String orderid = getRequestParamter(request, "orderid");
		CommonUtils.validateEmpty(studentid);
		CommonUtils.validateEmpty(orderid);
		sorderService.CancelComplaint(studentid, orderid);
	}

	public void confirmOn(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String studentid = getRequestParamter(request, "studentid");
		String orderid = getRequestParamter(request, "orderid");
		String lon = getRequestParamter(request, "lon");
		String lat = getRequestParamter(request, "lat");
		String detail = getRequestParamter(request, "detail");
		CommonUtils.validateEmpty(studentid);
		CommonUtils.validateEmpty(orderid);
		OrderInfo order = sorderService.getOrderById(CommonUtils.parseInt(orderid, 0));
		OrderRecordInfo orderrecord = sorderService.getOrderRecordByType(CommonUtils.parseInt(orderid, 0), 1);
		Calendar c = Calendar.getInstance();
		c.add(Calendar.HOUR, 1);
		if (order == null) {
			resultMap.put("code", 1);
			resultMap.put("message", "订单不存在！");
			return;
		} else if (order.getStudentid() != CommonUtils.parseInt(studentid, 0)) {
			resultMap.put("code", 99);
			resultMap.put("message", "参数错误！");
			return;
		} else if (orderrecord != null) {
			resultMap.put("code", 2);
			resultMap.put("message", "您已经确认上车过了！");
			return;
		} else if (order.getEnd_time().before(new Date())) {
			resultMap.put("code", 3);
			resultMap.put("message", "该任务已经过期！");
			return;
		} else if (order.getStart_time().after(c.getTime())) {
			resultMap.put("code", 3);
			resultMap.put("message", "该任务还未到开始时间！");
			return;
		} else {
			OrderRecordInfo orderRecord1 = new OrderRecordInfo();
			orderRecord1.setOrderid(CommonUtils.parseInt(orderid, 0));
			orderRecord1.setOperation(1);
			orderRecord1.setUserid(CommonUtils.parseInt(studentid, 0));
			orderRecord1.setAddtime(new Date());
			if (!CommonUtils.isEmptyString(lon)) {
				orderRecord1.setLongitude(lon);
			}
			if (!CommonUtils.isEmptyString(lat)) {
				orderRecord1.setLatitude(lat);
			}
			if (!CommonUtils.isEmptyString(detail)) {
				orderRecord1.setDetail(detail);
			}
			sorderService.addOrderRecord(orderRecord1);
		}
	}

	public void confirmDown(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String studentid = getRequestParamter(request, "studentid");
		String orderid = getRequestParamter(request, "orderid");
		String lon = getRequestParamter(request, "lon");
		String lat = getRequestParamter(request, "lat");
		String detail = getRequestParamter(request, "detail");
		CommonUtils.validateEmpty(studentid);
		CommonUtils.validateEmpty(orderid);
		OrderInfo order = sorderService.getOrderById(CommonUtils.parseInt(orderid, 0));
		OrderRecordInfo orderrecord = sorderService.getOrderRecordByType(CommonUtils.parseInt(orderid, 0), 2);
		if (order == null) {
			resultMap.put("code", 1);
			resultMap.put("message", "订单不存在！");
			return;
		} else if (order.getStudentid() != CommonUtils.parseInt(studentid, 0)) {
			resultMap.put("code", 99);
			resultMap.put("message", "参数错误！");
			return;
		} else if (orderrecord != null) {
			resultMap.put("code", 2);
			resultMap.put("message", "您已经确认下车过了！");
			return;
		} else {
			OrderRecordInfo orderRecord1 = new OrderRecordInfo();
			orderRecord1.setOrderid(CommonUtils.parseInt(orderid, 0));
			orderRecord1.setOperation(2);
			orderRecord1.setUserid(CommonUtils.parseInt(studentid, 0));
			orderRecord1.setAddtime(new Date());
			if (!CommonUtils.isEmptyString(lon)) {
				orderRecord1.setLongitude(lon);
			}
			if (!CommonUtils.isEmptyString(lat)) {
				orderRecord1.setLatitude(lat);
			}
			if (!CommonUtils.isEmptyString(detail)) {
				orderRecord1.setDetail(detail);
			}
			sorderService.addOrderRecord(orderRecord1);
			List<ComplaintInfo> complaintlist = sorderService.getComplaintByType(CommonUtils.parseInt(orderid, 0));
			if (complaintlist.size() == 0) {
				order.setStudentstate(2);
				sorderService.updateOrderInfo(order);
			}
		}
	}

}
