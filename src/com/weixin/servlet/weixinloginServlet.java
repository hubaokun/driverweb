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


@WebServlet("/weixinlogin")
public class weixinloginServlet extends BaseServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IGetYouWanna wxmessageService;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		wxmessageService=(IGetYouWanna)applicationContext.getBean("WXmessageService");
	}
	
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String code=request.getParameter("code");
		String state=request.getParameter("state");
		
		if(WeiXinMessage.getValue("service_access_token")==null)
		{
			GetAccessToken gt=new GetAccessToken();
			gt.checktoken(code);
		}
		String url="http://wx.xiaobaxueche.com/xiaoba/weixinlogin?code="+code+"&state="+state;
		String noncestr=wxmessageService.CreatenNonce_str(16);
		long timestamp=wxmessageService.CreatenTimestamp();
		String signature=wxmessageService.getSignature(noncestr, timestamp, url);
		

		request.setAttribute("noncestr", noncestr);
		request.setAttribute("timestamp", timestamp);
		request.setAttribute("signature", signature);
		request.setAttribute("appid", CommonUtils.getAppid());
		RequestDispatcher dispatcher = request.getRequestDispatcher("/weixinWeb/login.jsp");
		dispatcher.forward(request, response);
	}
}
