package com.weixin.common;

import java.util.Date;
import java.util.HashMap;

public class WeiXinMessage {
	/**
	 *  微信缓存变量
	 * key:service_access_token | value:服务端token
	 * key:service_access_token_expire | value："服务端token过期时间
	 * key:web_access_token | value：网页端token
	 * key:web_access_token_expire | value：网页端token过期时间
	 * key:ticket_access_token | value：ticket的token
	 * key:ticket_access_token_expire | value：ticket的token过期时间
	 * key:web_refresh_access_token | value：网页端重新刷新token
	 * key:openid | value：微信用户openid
	 */	
	private static HashMap cacheMap = new HashMap(); 
	
	private WeiXinMessage(){}
	
	public static String getValue(String key){
		return (String)cacheMap.get(key);
	}
	public static void setValue(String key,String value){
		cacheMap.put(key, value);
	}
}
