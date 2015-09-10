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
	private String baseUrl="http://wx.xiaobaxueche.com/xiaoba/weixinWeb/weixin?action=";
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		wxmessageService=(IGetYouWanna)applicationContext.getBean("WXmessageService");
	}
	
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String action=request.getParameter("action");
		if(action.equals("login"))
			weixinlogin(request, response);
		else if(action.equals("coachlist"))
			getcoachlist(request, response);
		else if("mylearninfo".equals(action)){
			//mylearninfo(request, response);
		}
		
	}
	public void weixinlogin(HttpServletRequest request, HttpServletResponse response)
	{
		String code=request.getParameter("code");
		String state=request.getParameter("state");
		IGetYouWanna WXmessageService=new GetYouWannaImpl();
		
		if(WeiXinMessage.getValue("web_access_token_expire")==null)
		{
			if(WXmessageService.getWebAccessToken(code)==false)
			{
				request.getSession().setAttribute("CInfo", "");
				System.out.println("请求网页授权接口出错,请检查！");	
			}
			else
			{
				request.getSession().setAttribute("c_info",WXmessageService.setCustomerInfo(WeiXinMessage.getValue("openid")));
			}
				
		}
		
		if(WeiXinMessage.getValue("service_access_token")==null)
		{		
			WXmessageService.getAccessToken();
			WXmessageService.getjsapi_ticket(WeiXinMessage.getValue("service_access_token"));
		}

		String url=baseUrl+"login&code="+code+"&state="+state;

		String noncestr=wxmessageService.CreatenNonce_str(16);
		long timestamp=wxmessageService.CreatenTimestamp();
		String signature=wxmessageService.getSignature(noncestr, timestamp, url);
		

		request.setAttribute("noncestr", noncestr);
		request.setAttribute("timestamp", timestamp);
		request.setAttribute("signature", signature);
		request.setAttribute("appid", CommonUtils.getAppid());
		
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
	/*public void mylearninfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String url=baseUrl+"mylearninfo";
		String noncestr=wxmessageService.CreatenNonce_str(16);
		long timestamp=wxmessageService.CreatenTimestamp();
		String signature=wxmessageService.getSignature(noncestr, timestamp, url);
		request.setAttribute("noncestr", noncestr);
		request.setAttribute("timestamp", timestamp);
		request.setAttribute("signature", signature); 
		request.setAttribute("appid", CommonUtils.getAppid());
		
		request.getRequestDispatcher("/weixinWeb/mylearninfo.jsp").forward(request, response);
	}*/
}
