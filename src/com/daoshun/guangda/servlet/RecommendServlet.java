package com.daoshun.guangda.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alipay.refund.util.httpClient.HttpRequest;
import com.alipay.refund.util.httpClient.HttpResponse;
import com.daoshun.common.CommonUtils;
import com.daoshun.common.Constant;
import com.daoshun.common.QueryResult;
import com.daoshun.exception.NullParameterException;
import com.daoshun.guangda.pojo.CuserInfo;
import com.daoshun.guangda.pojo.RecommendInfo;
import com.daoshun.guangda.pojo.SuserInfo;
import com.daoshun.guangda.pojo.SystemSetInfo;
import com.daoshun.guangda.service.ICUserService;
import com.daoshun.guangda.service.ICtaskService;
import com.daoshun.guangda.service.IRecommendService;
import com.daoshun.guangda.service.ISUserService;
import com.daoshun.guangda.service.ISystemService;
/**
 *
 * @author wjr
 *
 */
@WebServlet("/recomm")
public class RecommendServlet extends BaseServlet {

	/**
	 *
	 */
	private static final long serialVersionUID = -1262197095863272112L;


	private IRecommendService recommendService;
	private ISUserService suserService;
	private ICUserService cuserService;
	private ISystemService systemService;
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		recommendService=(IRecommendService) applicationContext.getBean("recommendService");
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
			if(action.equals(Constant.CGETRECOMMENDLIST) && action.equals(Constant.CHEAKINVITECODE))
			{
				if (!checkSession(request, action, resultMap)) {
					setResult(response, resultMap);
					return;
				}
			}
			if(action.equals(Constant.CGETRECOMMENDLIST))
			{
				getRecommendList(request,resultMap);
			}
			else if(action.equals(Constant.CHEAKINVITECODE))
			{
				addRecommendInfo(request,resultMap);
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
	public <T> void getRecommendList(HttpServletRequest request,HashMap<String, Object> resultMap) throws NullParameterException
	{
		String coachid=request.getParameter("coachid");
		String page=request.getParameter("pagenum");
		CommonUtils.validateEmpty(coachid);
		CommonUtils.validateEmpty(page);
		QueryResult<RecommendInfo> qr=recommendService.getRecommendList(coachid, CommonUtils.parseInt(page, 0));
		int hasmore=recommendService.ifhasmoreRecommendinfo(coachid, CommonUtils.parseInt(page, 0));
		int rflag=0;
		if(hasmore==0)
			resultMap.put("hasmore",0);
		else
			resultMap.put("hasmore",1);
		List<T> list=(List<T>) qr.getDataList();
		long total=qr.getTotal();
		if(list.size()!=0)
		{
			for(int i=0;i<list.size();i++)
			{
				RecommendInfo temp=(RecommendInfo) list.get(i);
			}
			BigDecimal totalreward=recommendService.getReward(coachid);
			rflag=1;
			resultMap.put("totalreward",totalreward);
		}
		else
		{
			list.add((T) new Object());
			resultMap.put("totalreward",0);
		}
		resultMap.put("rflag", rflag);
		resultMap.put("RecommendList",list);
		resultMap.put("total",total);


	}
	public void addRecommendInfo(HttpServletRequest request,HashMap<String, Object> resultMap) throws NullParameterException
	{
		String inviteid=request.getParameter("InviteCode");
		String invitedcoachid=request.getParameter("InvitedCoachid");
		CommonUtils.validateEmpty(inviteid);
		CommonUtils.validateEmpty(invitedcoachid);
		//String codetype=inviteid.substring(0, 0).toUpperCase();   邀请码类型，C=教练  S=学员
		if(inviteid.length()!=8)
		{
			resultMap.put("inviteCode","0");
			resultMap.put("isRecommended",0);
		}
		else
		{
			inviteid=inviteid.substring(1, 8).toUpperCase();
			if((recommendService.addRecommendInfo(inviteid, invitedcoachid))==1)
				resultMap.put("isRecommended",1);
			else
				resultMap.put("isRecommended",0);
			resultMap.put("inviteCode","1");
		}

	}
}
