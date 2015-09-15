package com.weixin.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

public interface IGetYouWanna {
	public abstract void createmenu(String service_access_token,String menu);
	
	public abstract void deletemenu(String service_access_token);
	public abstract void getAccessToken();
	public abstract boolean getWebAccessToken(String code);
	public abstract void getjsapi_ticket(String accesstoken);
	public abstract String CreatenNonce_str(int length);
	public abstract long CreatenTimestamp();
	public abstract String getSignature(String noncestr,long timestamp,String url);
	public abstract JSONObject  setCustomerInfo(String openid);
	public abstract String getSignForPrePay(String openid,String total_fee,String out_trade_no,String spbill_create_ip) throws IOException;
	public abstract String CreateRandomMath(int length);
	public abstract String getCustomerIP(HttpServletRequest request);
	public abstract String getSignForPay(String appid,long timeStamp,String nonceStr,String signType,String prepay_id);
	public abstract void getservicelist();
}
