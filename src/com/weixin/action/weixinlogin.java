package com.weixin.action;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;

import com.daoshun.guangda.servlet.BaseServlet;


@WebServlet("/weixinlogin")
public class weixinlogin extends BaseServlet{
	
	
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
	
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String code=request.getParameter("code");
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx697888f825457533&secret=8152cb86739fde0d1dc354a1074dfdda&code="+code+"&grant_type=authorization_code";
        URL u=new URL(url);
        HttpURLConnection uc=(HttpURLConnection) u.openConnection();
        uc.setRequestMethod("GET");
        uc.setConnectTimeout(3000);
        uc.setDoInput(true);
        uc.connect();
        InputStream is=uc.getInputStream();
        int size=is.available();
        byte[] result=new byte[size];
        is.read(result);
        String message=new String(result,"UTF-8");
        is.close();
        uc.disconnect();
        
        
		try {
			JSONObject json = new JSONObject(message);
			String accessToken= json.getString("access_token");
			String openid=json.getString("openid");
			System.out.println(accessToken+"              "+openid);
			String url1="https://api.weixin.qq.com/sns/userinfo?access_token="+accessToken+"&openid="+openid+"&lang=zh_CN";
			URL u1=new URL(url1);
			HttpURLConnection uc1=(HttpURLConnection) u1.openConnection();
	        uc1.setRequestMethod("GET");
	        uc1.setConnectTimeout(3000);
	        uc1.setDoInput(true);
	        uc1.connect();
	        InputStream is1=uc1.getInputStream();
	        int size1=is1.available();
	        byte[] result1=new byte[size1];
	        is1.read(result1);
	        String message1=new String(result1,"UTF-8");
	        JSONObject json1 = new JSONObject(message1);
			String nickname= json1.getString("nickname");
			String city=json1.getString("city");
			System.out.println(nickname+"              "+city);
			is1.close();
			uc1.disconnect();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
	}
	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		ip = request.getHeader("Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		ip = request.getRemoteAddr();
		}
		return ip;
		}
}
