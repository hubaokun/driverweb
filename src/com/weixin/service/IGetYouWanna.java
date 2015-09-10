package com.weixin.service;

import java.util.List;

import org.json.JSONObject;

public interface IGetYouWanna {
	public abstract void createmenu(String service_access_token,String menu);
	
	public abstract void deletemenu(String service_access_token);
	public abstract void getip(String ip);
	public abstract void getAccessToken();
	public abstract boolean getWebAccessToken(String code);
	public abstract void getjsapi_ticket(String accesstoken);
	public abstract String CreatenNonce_str(int length);
	public abstract long CreatenTimestamp();
	public abstract String getSignature(String noncestr,long timestamp,String url);
	public abstract JSONObject  setCustomerInfo(String openid);
}
