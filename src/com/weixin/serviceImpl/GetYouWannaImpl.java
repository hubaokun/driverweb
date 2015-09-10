package com.weixin.serviceImpl;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.persistence.Cache;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.daoshun.common.CommonUtils;
import com.daoshun.guangda.serviceImpl.BaseServiceImpl;
import com.weixin.common.WeiXinMessage;
import com.weixin.service.IGetYouWanna;

import net.sf.ehcache.CacheManager;
@Service("WXmessageService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class GetYouWannaImpl extends BaseServiceImpl implements IGetYouWanna{
	public static void main(String[] args) throws IOException, JSONException {
	
		GetYouWannaImpl g=new GetYouWannaImpl();
		g.createmenu("-CLFfR2-8CCaUlgzEm5YtgrNzSG7F3bInNP5sorib2VgQQpAYG1IreQfWwKkr5GyUS68D9hkMykfMABr5cykxIKqXfKg1f6ZFubalYFAyMo", "{\"button\":[{\"type\":\"view\",\"name\":\"\u5C0F\u5DF4\u5B66\u8F66\u5BA2\u6237\u7AEF\",\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx697888f825457533&redirect_uri=http://wx.xiaobaxueche.com/xiaoba/weixinWeb/weixinlogin?action=login&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect\"}]}");
	}
	//创建自定义菜单
	public void createmenu(String service_access_token,String menu)
	{
		String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+service_access_token; 
        try {
        	URL u=new URL(url);
            HttpURLConnection uc=(HttpURLConnection)u.openConnection();
            uc.setRequestMethod("POST");
            uc.setConnectTimeout(3000);
            uc.setDoInput(true);
            uc.setDoOutput(true);
            uc.connect();
            OutputStream os=uc.getOutputStream();
			os.write(menu.getBytes("UTF-8"));
		    os.flush();
	        os.close();
	        InputStream is =uc.getInputStream();
	        int size =is.available();
	        byte[] jsonBytes =new byte[size];
	        is.read(jsonBytes);
	        String message=new String(jsonBytes,"UTF-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      
      

	}
	//删除自定义菜单
	public void deletemenu(String service_access_token)
	{
		
		try {
			 String url = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token="+service_access_token;
	         URL u=new URL(url);
	         HttpURLConnection uc=(HttpURLConnection) u.openConnection();
	         uc.setRequestMethod("GET");
	         uc.setConnectTimeout(3000);
	         uc.setDoInput(true);
	         uc.connect();
	         InputStream is;
			 is = uc.getInputStream();
			 int size=is.available();
	         byte[] result=new byte[size];
	         is.read(result);
	         String message=new String(result,"UTF-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         
	}
	public void getip(String ip)
	{
		
		try {
			 String url = "http://api.map.baidu.com/location/ip?ak=MGt8L6pnsrogzRS6u4TKMGcX&ip="+ip+"&coor=bd09ll";
	         URL u=new URL(url);
	         HttpURLConnection uc=(HttpURLConnection) u.openConnection();
	         uc.setRequestMethod("GET");
	         uc.setConnectTimeout(3000);
	         uc.setDoInput(true);
	         uc.connect();
	         InputStream is;
			 is = uc.getInputStream();
			 int size=is.available();
	         byte[] result=new byte[size];
	         is.read(result);
	         String message=new String(result,"UTF-8");

	         JSONObject json = new JSONObject(message);
	         String accessToken = json.getString("address");
	         JSONObject json1=json.getJSONObject("content");
	         JSONObject json2=json1.getJSONObject("address_detail");
	        // String time = json.getString("expires_in");
	         System.out.println(accessToken+"      "+json2.getString("city"));
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         
	}
	//获取服务端token
	 public void getAccessToken()
	 {
		 Calendar expired=Calendar.getInstance();
		 Calendar c=Calendar.getInstance();
		 String sdate=WeiXinMessage.getValue("service_access_token_expire");
		 String appid=CommonUtils.getAppid();
		 String appsecret=CommonUtils.getAppsecret();
		 if(sdate!=null)
		 {
			 
			 expired.setTime(CommonUtils.getDateFormat(sdate,"yyyy-MM-dd HH:mm:ss"));
			 //如果已经过期，那么新申请一个token
			 if(c.after(expired))
		     {
		    	
		         try {
		        	 String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+appsecret;
			         URL u=new URL(url);
			         HttpURLConnection uc=(HttpURLConnection)u.openConnection();
			         uc.setRequestMethod("GET");
			         uc.setConnectTimeout(3000);
			         uc.setDoInput(true);
			         uc.connect();
			         InputStream is=uc.getInputStream();
			         int size=is.available();
			         byte[] result=new byte[size];
					 is.read(result);
					 String message=new String(result,"UTF-8");
			         JSONObject json = new JSONObject(message);
			         String accessToken = json.getString("access_token");
			         String expires_in = json.getString("expires_in");
			         Calendar setc=Calendar.getInstance();
			         setc.add(Calendar.SECOND, CommonUtils.parseInt(expires_in, 0));
			         Date expiredate=setc.getTime();
			         WeiXinMessage.setValue("service_access_token", accessToken);
			         WeiXinMessage.setValue("service_access_token_expire", CommonUtils.getTimeFormat(expiredate,null));
				} catch (IOException | JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		         
		     }
		 }
		 //如果为空，那么也新申请一个token
		 else
		 {
			 try {
	        	 String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+appsecret;
		         URL u=new URL(url);
		         HttpURLConnection uc=(HttpURLConnection)u.openConnection();
		         uc.setRequestMethod("GET");
		         uc.setConnectTimeout(3000);
		         uc.setDoInput(true);
		         uc.connect();
		         InputStream is=uc.getInputStream();
		         int size=is.available();
		         byte[] result=new byte[size];
				 is.read(result);
				 String message=new String(result,"UTF-8");
		         JSONObject json = new JSONObject(message);
		         String accessToken = json.getString("access_token");
		         String expires_in = json.getString("expires_in");
		         Calendar setc=Calendar.getInstance();
		         setc.add(Calendar.SECOND, CommonUtils.parseInt(expires_in, 0));
		         Date expiredate=setc.getTime();
		         WeiXinMessage.setValue("service_access_token", accessToken);
		         WeiXinMessage.setValue("service_access_token_expire", CommonUtils.getTimeFormat(expiredate,null));
			} catch (IOException | JSONException e) {
				
				e.printStackTrace();
			}
		 }
		
	 }
	//请求网页端token
     public boolean getWebAccessToken(String code)
		 {
			 Calendar expired=Calendar.getInstance();
			 Calendar c=Calendar.getInstance();
			 String appid=CommonUtils.getAppid();
			 String appsecret=CommonUtils.getAppsecret();
				 String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appid+"&secret="+appsecret+"&code="+code+"&grant_type=authorization_code";    
					try {
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
						JSONObject json = new JSONObject(message);
						if(!message.contains("errmsg"))
						{
							String accessToken= json.getString("access_token");
							String expires_in=json.getString("expires_in");
							String openid=json.getString("openid");
							Calendar setc=Calendar.getInstance();
					        setc.add(Calendar.SECOND, CommonUtils.parseInt(expires_in, 0));
					        Date expiredate=setc.getTime();
					        WeiXinMessage.setValue("web_access_token", accessToken);
					        WeiXinMessage.setValue("web_access_token_expire", CommonUtils.getTimeFormat(expiredate,null));
					        WeiXinMessage.setValue("openid", openid);
						}
						else
						{
							return false;
						}
                    
					} catch (JSONException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			  return true;
		 }
	//获取ticket的token
     @Override
	 public void getjsapi_ticket(String accesstoken) {
    	 Calendar expired=Calendar.getInstance();
		 Calendar c=Calendar.getInstance();
		 String sdate=WeiXinMessage.getValue("ticket_access_token_expire");
		 if(sdate!=null)
		 {
			 expired.setTime(CommonUtils.getDateFormat(sdate,"yyyy-MM-dd HH:mm:ss"));
			 if(c.after(expired))
		     {
		    	
				 String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+accesstoken+"&type=jsapi";   
					try {
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
						JSONObject json = new JSONObject(message);
						String accessToken= json.getString("ticket");
						String expires_in=json.getString("expires_in");
						 Calendar setc=Calendar.getInstance();
				         setc.add(Calendar.SECOND, CommonUtils.parseInt(expires_in, 0));
				         Date expiredate=setc.getTime();
				         WeiXinMessage.setValue("ticket_access_token", accessToken);
				         WeiXinMessage.setValue("ticket_access_token_expire", CommonUtils.getTimeFormat(expiredate,null));
			           
					} catch (JSONException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		         
		     }
		 }
		 else
		 {
			 String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+accesstoken+"&type=jsapi";
				try {
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
					JSONObject json = new JSONObject(message);
					String accessToken= json.getString("ticket");
					String expires_in=json.getString("expires_in");
					 Calendar setc=Calendar.getInstance();
			         setc.add(Calendar.SECOND, CommonUtils.parseInt(expires_in, 0));
			         Date expiredate=setc.getTime();
			         WeiXinMessage.setValue("ticket_access_token", accessToken);
			         WeiXinMessage.setValue("ticket_access_token_expire", CommonUtils.getTimeFormat(expiredate,null));
		           
				} catch (JSONException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		 }
	}
	//生成随机字符串
     @Override
	 public String CreatenNonce_str(int length) {
		 String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";  
	        Random random = new Random();  
	        StringBuffer sb = new StringBuffer();  
	          
	        for(int i = 0 ; i < length; ++i){  
	            int number = random.nextInt(62);//[0,62)  
	              
	            sb.append(str.charAt(number));  
	        }  
	        return sb.toString();  
		
	}
	//生成时间戳
	@Override
	public long CreatenTimestamp() {
		String timestamp=String.valueOf(System.currentTimeMillis());
		long rtimestamp=Long.parseLong(timestamp.substring(0, 10));
		 return rtimestamp;
	}
	//获取验证签名
	@Override
	public String getSignature(String noncestr, long timestamp,String url) {
		String jsapi_ticket=WeiXinMessage.getValue("ticket_access_token");
		HashMap map=new HashMap();
		StringBuffer sb=new StringBuffer();
		map.put("noncestr", noncestr);
		map.put("timestamp", timestamp);
		map.put("jsapi_ticket", jsapi_ticket);
		map.put("url", url);
		Collection<String> keyset= map.keySet();
	    List<String> list = new ArrayList<String>(keyset);  
	       
	     //对key键值按字典升序排序  
	     Collections.sort(list); 
	     for (int i = 0; i < list.size(); i++) {  
	    	 if(i==list.size()-1)
	    		 sb.append(list.get(i)+"="+map.get(list.get(i)));  
	    	 else	 
	    		 sb.append(list.get(i)+"="+map.get(list.get(i))+"&");  
	     }
	       // SHA1加密
	       MessageDigest md;
	       String digest="";
			try {
				md = MessageDigest.getInstance("SHA-1");
				digest = CommonUtils.byteToString(md.digest(sb.toString().getBytes()));
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return digest;
	}
	//设置微信用户信息
	@Override
	public JSONObject setCustomerInfo(String openid) {
		String accesstoken=WeiXinMessage.getValue("web_access_token");
		 String url = "https://api.weixin.qq.com/sns/userinfo?access_token="+accesstoken+"&openid="+openid+"&lang=zh_CN";    
		 JSONObject cinfo1=new JSONObject();
			try {
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
		        JSONObject cinfo=new JSONObject(message);
		        is.close();
		        uc.disconnect();	    
		        return cinfo;
			} catch (IOException | JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return cinfo1;
	}
}
