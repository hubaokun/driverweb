package com.weixin.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;

import com.daoshun.common.CommonUtils;
import com.daoshun.guangda.service.ISUserService;
import com.daoshun.guangda.servlet.BaseServlet;
import com.weixin.common.GetAccessToken;
import com.weixin.common.WeiXinMessage;
import com.weixin.service.IGetYouWanna;
import com.weixin.serviceImpl.GetYouWannaImpl;


@WebServlet("/weixinWeb/weixin")
public class weixinServlet extends BaseServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IGetYouWanna wxmessageService;
	private ISUserService suserservice;
	private String baseUrl="http://wx.xiaobaxueche.com/dadmin/weixinWeb/weixin?action=";
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		wxmessageService=(IGetYouWanna)applicationContext.getBean("WXmessageService");
		suserservice=(ISUserService)applicationContext.getBean("suserService");
	}
	
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{  //此处action名字必须与方法中url中action名相同
		String action=request.getParameter("action");
		if(action.equals("login"))
			weixinlogin(request, response);
		else if(action.equals("coachlist"))
			getcoachlist(request, response);
		else if("closewindow".equals(action)){
			//closewindow(request, response);
		}
		else if(action.equals("charge"))
			gotocharge(request, response);
	}
	public void weixinlogin(HttpServletRequest request, HttpServletResponse response)
	{
		String code=request.getParameter("code");
		String state=request.getParameter("state");
		IGetYouWanna WXmessageService=new GetYouWannaImpl();
		String phone="";
		if(WXmessageService.getWebAccessToken(code)==false)
		{
			request.getSession().setAttribute("c_info", "");
			System.out.println("请求网页授权接口出错,请检查！");	
		}
		else
		{
			request.getSession().setAttribute("c_info",WXmessageService.setCustomerInfo(WeiXinMessage.getValue("openid")));
		}
		String openid=WeiXinMessage.getValue("openid");
		if(openid!=null)
		{
			phone=suserservice.checkopenid(openid);
		}
		System.out.println("phone="+phone);
		
		if(WeiXinMessage.getValue("service_access_token")==null)
		{		
			WXmessageService.getAccessToken();
			WXmessageService.getjsapi_ticket(WeiXinMessage.getValue("service_access_token"));
		}

		String url=baseUrl+"login&code="+code+"&state="+state;

		String noncestr=wxmessageService.CreatenNonce_str(16);
		long timestamp=wxmessageService.CreatenTimestamp();
		String signature=wxmessageService.getSignature(noncestr, timestamp, url);
		
      //  System.out.println("noncestr="+noncestr+"  timestamp="+timestamp+"  signature="+signature+" appid="+CommonUtils.getAppid());
		request.setAttribute("noncestr", noncestr);
		request.setAttribute("timestamp", timestamp);
		request.setAttribute("signature", signature);
		request.setAttribute("appid", CommonUtils.getAppid());
		request.setAttribute("phone", phone);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/weixinWeb/login.jsp");
		try {
			dispatcher.forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}
	public void getcoachlist(HttpServletRequest request, HttpServletResponse response)
	{
		String url=baseUrl+"coachlist";
		String noncestr=wxmessageService.CreatenNonce_str(16);
		long timestamp=wxmessageService.CreatenTimestamp();
		String signature=wxmessageService.getSignature(noncestr, timestamp, url);
		request.setAttribute("noncestr", noncestr);
		request.setAttribute("timestamp", timestamp);
		request.setAttribute("signature", signature); 
		request.setAttribute("appid", CommonUtils.getAppid());
		RequestDispatcher dispatcher = request.getRequestDispatcher("/weixinWeb/coachlist.jsp");
		try {
			dispatcher.forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}
	public void gotocharge(HttpServletRequest request, HttpServletResponse response)
	{
		String url=baseUrl+"charge";
		String noncestr=wxmessageService.CreatenNonce_str(16);
		long timestamp=wxmessageService.CreatenTimestamp();
		String signature=wxmessageService.getSignature(noncestr, timestamp, url);
		
		request.setAttribute("noncestr", noncestr);
		request.setAttribute("timestamp", timestamp);
		request.setAttribute("signature", signature); 
		request.setAttribute("appid", CommonUtils.getAppid());
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/weixinWeb/charge.jsp");
		try {
			dispatcher.forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * 关闭微信窗口：当session超时时
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @author 卢磊
	 */
	/*public void closewindow(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String url=baseUrl+"closewindow";
		String noncestr=wxmessageService.CreatenNonce_str(16);
		long timestamp=wxmessageService.CreatenTimestamp();
		String signature=wxmessageService.getSignature(noncestr, timestamp, url);
		request.setAttribute("noncestr", noncestr);
		request.setAttribute("timestamp", timestamp);
		request.setAttribute("signature", signature); 
		request.setAttribute("appid", CommonUtils.getAppid());
		
		request.getRequestDispatcher("/weixinWeb/closewindow.jsp").forward(request, response);
	}*/
}
