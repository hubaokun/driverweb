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
import com.daoshun.exception.NullParameterException;
import com.daoshun.guangda.pojo.CuserInfo;
import com.daoshun.guangda.pojo.NoticesInfo;
import com.daoshun.guangda.pojo.SuserInfo;
import com.daoshun.guangda.pojo.SystemSetInfo;
import com.daoshun.guangda.service.ICUserService;
import com.daoshun.guangda.service.ISSetService;
import com.daoshun.guangda.service.ISUserService;
import com.daoshun.guangda.service.ISystemService;

@WebServlet("/sset")
public class SsetServlet extends BaseServlet {

	private static final long serialVersionUID = 6998440419757851253L;
	private ISSetService ssetService;
	private ISystemService systemService;
	private ICUserService cuserService;
	private ISUserService suserService;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		ssetService = (ISSetService) applicationContext.getBean("ssetService");
		systemService = (ISystemService) applicationContext.getBean("systemService");
		cuserService = (ICUserService) applicationContext.getBean("cuserService");
		suserService = (ISUserService) applicationContext.getBean("suserService");
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String action = getAction(request);

			if (Constant.GETNOTICES.equals(action) || Constant.DELNOTICE.equals(action) || Constant.READNOTICE.equals(action) || Constant.GETMESSAGECOUNT.equals(action)
					|| Constant.FEEDBACK.equals(action)) {
				if (!checkSession(request, action, resultMap)) {
					setResult(response, resultMap);
					return;
				}
			}

			if (Constant.GETNOTICES.equals(action)) {
				// 获取通知列表
				getNotices(request, resultMap);
			} else if (Constant.DELNOTICE.equals(action)) {
				// 删除通知
				delNotice(request, resultMap);
			} else if (Constant.READNOTICE.equals(action)) {
				// 设置已读通知
				readNotice(request, resultMap);
			} else if (Constant.GETMESSAGECOUNT.equals(action)) {
				// 获取未读消息条数
				getMessageCount(request, resultMap);
			} else if (Constant.FEEDBACK.equals(action)) {
				// 意见反馈
				feedback(request, resultMap);
			} else {
				throw new NullParameterException();
			}

			recordUserAction(request, action);

		} catch (Exception e) {
			e.printStackTrace();
			setResultWhenException(response, e.getMessage());
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
		}

		if (!CommonUtils.isEmptyString(userid) && !CommonUtils.isEmptyString(usertype)) {
			systemService.recordUserAction(userid, usertype, "sset", action);
		}
	}

	public void getNotices(HttpServletRequest request, HashMap<String, Object> resultMap) throws NullParameterException {
		String studentid = getRequestParamter(request, "studentid");
		String pagenum = getRequestParamter(request, "pagenum");
		CommonUtils.validateEmpty(studentid);
		List<NoticesInfo> datalist = ssetService.getNoticesList(studentid, pagenum);
		int hasmore = ssetService.getNoticesMore(studentid, pagenum);
		resultMap.put("hasmore", hasmore);
		resultMap.put("datalist", datalist);
	}

	public void delNotice(HttpServletRequest request, HashMap<String, Object> resultMap) throws NullParameterException {
		String studentid = getRequestParamter(request, "studentid");
		String noticeid = getRequestParamter(request, "noticeid");
		CommonUtils.validateEmpty(studentid);
		CommonUtils.validateEmpty(noticeid);
		ssetService.delNotice(studentid, noticeid);
	}

	public void readNotice(HttpServletRequest request, HashMap<String, Object> resultMap) throws NullParameterException {
		String studentid = getRequestParamter(request, "studentid");
		String noticeid = getRequestParamter(request, "noticeid");
		CommonUtils.validateEmpty(studentid);
		CommonUtils.validateEmpty(noticeid);
		ssetService.readNotice(studentid, noticeid);
	}

	public void getMessageCount(HttpServletRequest request, HashMap<String, Object> resultMap) throws NullParameterException {
		String studentid = getRequestParamter(request, "studentid");
		CommonUtils.validateEmpty(studentid);
		int noticecount = ssetService.getMessageCount(studentid);
		resultMap.put("noticecount", noticecount);
	}

	public void feedback(HttpServletRequest request, HashMap<String, Object> resultMap) throws NullParameterException {
		String studentid = getRequestParamter(request, "studentid");
		String content = getRequestParamter(request, "content");
		String type = getRequestParamter(request, "type");
		CommonUtils.validateEmpty(studentid);
		CommonUtils.validateEmpty(content);
		CommonUtils.validateEmpty(type);
		ssetService.addFeedBack(studentid, content, type);
	}

}
