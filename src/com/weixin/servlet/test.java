package com.weixin.servlet;


import java.util.Calendar;
import java.util.Date;

import com.weixin.serviceImpl.GetYouWannaImpl;

public class test {

	public static void main(String[] args) {
		GetYouWannaImpl g=new GetYouWannaImpl();
		   String url="http://wx.xiaobaxueche.com/xiaoba/weixinWeb/login.jsp";
			String noncestr=g.CreatenNonce_str(16);
			long timestamp=g.CreatenTimestamp();
			System.out.println(g.getSignature(noncestr, timestamp, url));
		  
	}

}
