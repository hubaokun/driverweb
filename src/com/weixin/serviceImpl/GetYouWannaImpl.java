package com.weixin.serviceImpl;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
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
import javax.xml.parsers.SAXParser;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
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
	//	g.createmenu("-CLFfR2-8CCaUlgzEm5YtgrNzSG7F3bInNP5sorib2VgQQpAYG1IreQfWwKkr5GyUS68D9hkMykfMABr5cykxIKqXfKg1f6ZFubalYFAyMo", "{\"button\":[{\"type\":\"view\",\"name\":\"\u5C0F\u5DF4\u5B66\u8F66\u5BA2\u6237\u7AEF\",\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx697888f825457533&redirect_uri=http://wx.xiaobaxueche.com/xiaoba/weixinWeb/weixinlogin?action=login&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect\"}]}");
		//g.invokePublicPayIF();
		 String aa="<xml><appid>wxabb11606edd48e8c</appid><body>小巴学员账户充值</body><mch_id>1268831501</mch_id><nonce_str>aTE5S7nGag5BE8VI2UMZsvYaU</nonce_str><notify_url>http://wx.xiaobaxueche.com/xiaoba/weixinWeb/weixinpaycb</notify_url><openid>oH0m8vhOOGZV4RbxDE-FBkSQxwSA</openid><out_trade_no>1330</out_trade_no><spbill_create_ip>122.233.42.240</spbill_create_ip><total_fee>1</total_fee><trade_type>JSAPI</trade_type><sign>2D0E93B768CAE58C4D0756086948135A</sign></xml>";
		 System.out.println(java.net.URLEncoder.encode(aa));
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
	//获取服务端token
	 public boolean getAccessToken()
	 {
		 boolean flag=true;
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
					flag=false;
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
				flag=false;
				e.printStackTrace();
			}
		 }
		return flag;
	 }
	//请求网页端token
     public boolean getWebAccessToken(String code)
		 {
//			 Calendar expired=Calendar.getInstance();
//			 Calendar c=Calendar.getInstance();
			 String appid=CommonUtils.getAppid();
			 String appsecret=CommonUtils.getAppsecret();
				 String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appid+"&secret="+appsecret+"&code="+code+"&grant_type=authorization_code";    
					try {
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
	 public boolean getjsapi_ticket(String accesstoken) {
    	 boolean flag=true;
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
						flag=false;
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
					flag=false;
					e.printStackTrace();
				}
		 }
		 return flag;
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
	//微信支付公共接口算法
	@Override
	public String getSignForPrePay(String openid,String total_fee,String out_trade_no,String spbill_create_ip,String trade_type,String paymessage) throws UnsupportedEncodingException {
		String appid=CommonUtils.getAppid();
		 String mch_id=CommonUtils.getMchid();
		 String nonce_str=CreatenNonce_str(25);
		 
		 
		 String body="hzxiaoba";
		// body=new String(body.getBytes("unicode"),"UTF-8");
		// System.out.println(body);
		 String notify_url="http://wx.xiaobaxueche.com/dadmin/weixinWeb/weixinpaycb";
		 if(trade_type!=null && trade_type.equals("APP") && paymessage!=null)
		 {
			 try {
				JSONObject object=new JSONObject(paymessage);
				spbill_create_ip=object.getString("spbill_create_ip");
				nonce_str=object.getString("nonce_str");
				appid=object.getString("appid");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		 HashMap<String,String> map=new HashMap<String,String>();
		 StringBuffer sb=new StringBuffer();
		 BigDecimal bd=new BigDecimal(total_fee);
		 bd=bd.multiply(new BigDecimal(100));
		 String Stotal_fee=bd.toString();
		 map.put("appid", appid);
		 map.put("mch_id",mch_id);
		 map.put("nonce_str", nonce_str);
		 map.put("body",body);
		 map.put("out_trade_no", out_trade_no);
		 map.put("total_fee","1");
		 map.put("spbill_create_ip", spbill_create_ip);
		 map.put("notify_url",notify_url);
		 map.put("trade_type", trade_type);
		 if(openid!=null)
		   map.put("openid",openid);
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
		   String  stringA=sb.toString();
		   String  stringSignTemp=stringA+"&key="+CommonUtils.getWXKey();
		   // MD5加密
	       MessageDigest md;
	       String digest="";
			try {
				md = MessageDigest.getInstance("MD5");
				digest = CommonUtils.byteToString(md.digest(stringSignTemp.toString().getBytes()));
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String sign=digest.toUpperCase();
			
//			sb1.append("<xml><appid><![CDATA["+appid+"]]></appid><body><![CDATA["+body+"]]></body><mch_id><![CDATA["+mch_id+"]]></mch_id><nonce_str><![CDATA["+nonce_str+"]]></nonce_str><notify_url><![CDATA["+notify_url+"]]></notify_url><openid><![CDATA["+openid+"]]></openid><out_trade_no><![CDATA["+out_trade_no+"]]></out_trade_no>"+
//			"<spbill_create_ip><![CDATA["+spbill_create_ip+"]]></spbill_create_ip><total_fee><![CDATA[1]]></total_fee><trade_type><![CDATA["+trade_type+"]]></trade_type><sign><![CDATA["+sign+"]]></sign></xml>");
//			String xml=new String(sb1.toString().getBytes(),"utf-8");
//			System.out.println(xml);
			String xml="<xml><appid><![CDATA["+appid+"]]></appid><body><![CDATA["+body+"]]></body><mch_id><![CDATA["+mch_id+"]]></mch_id><nonce_str><![CDATA["+nonce_str+"]]></nonce_str><notify_url><![CDATA["+notify_url+"]]></notify_url><openid><![CDATA["+openid+"]]></openid><out_trade_no><![CDATA["+out_trade_no+"]]></out_trade_no>"+
			"<spbill_create_ip><![CDATA["+spbill_create_ip+"]]></spbill_create_ip><total_fee>1</total_fee><trade_type><![CDATA["+trade_type+"]]></trade_type><sign><![CDATA["+sign+"]]></sign></xml>";
			//xml=new String(xml.getBytes("UTF-8"),"ISO8859-1");
//			System.out.println(xml);
			String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	        URL u;
	        String result="FAIL";
			try {
//				 u = new URL(url);
//				 HttpURLConnection uc=(HttpURLConnection)u.openConnection();
//		         uc.setRequestMethod("POST");
//		         //uc.setRequestProperty("Accept-Charset","ISO8859-1");
//		         uc.setConnectTimeout(3000);
//		         uc.setDoInput(true);
//		         uc.setDoOutput(true);
//		         
//		       //  OutputStream os=uc.getOutputStream();
//		         PrintWriter pw=new PrintWriter(uc.getOutputStream());
////		         os.write(xml.getBytes("ISO8859-1"));
////		         os.flush();
////		         os.close();
////		         uc.connect();
//		 
//		         pw.write(xml);
//		         pw.flush();
//		         pw.close();
//		         InputStream is=uc.getInputStream();
//				 SAXReader saxReader = new SAXReader();
//				 Document document;
//			     document = saxReader.read(is);
//				 Element root = document.getRootElement();
//				 List<Element> elements=root.elements();
//				 for(Element e:elements)
//				 {
//                    System.out.println(e.getName()+":"+e.getText());
////					if(e.getText().equals("FAIL"))
////						return result;
////					else if(e.getName().equals("prepay_id"))
////						return e.getText();
//				 }
//				is.close();        
				//HttpPost连接对象          
				HttpPost httpRequest = new HttpPost(url);          

//				//使用NameValuePair来保存要传递的Post参数          
//				List<NameValuePair> params = new ArrayList<NameValuePair>();          
//
//				//添加要传递的参数          
//				params.add(new BasicNameValuePair("xml",xml));          

				//设置字符集             
				StringEntity httpentity = new StringEntity(xml);              
				 httpentity.setContentEncoding("utf-8");
				 httpentity.setContentType("text/xml");
				//请求httpRequest       
				 httpRequest.setEntity(httpentity);              

				//取得默认的HttpClient              
				HttpClient httpclient = new DefaultHttpClient();             

				
				 //取得HttpResponse             
				 HttpResponse httpResponse = httpclient.execute(httpRequest);              
				//HttpStatus.SC_OK表示连接成功              
				if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK)              
				{                 
				 //取得返回的字符串                  
				//String strResult = EntityUtils.toString(httpResponse.getEntity());  
					HttpEntity resultentity=httpResponse.getEntity();
					InputStream is=resultentity.getContent();
					SAXReader saxReader = new SAXReader();
					 Document document;
				     document = saxReader.read(is);
					 Element root = document.getRootElement();
					 List<Element> elements=root.elements();
					 for(Element e:elements)
					 {
	                  //  System.out.println(e.getName()+":"+e.getText());
						if(e.getText().equals("FAIL"))
							return result;
						else if(e.getName().equals("prepay_id"))
							return e.getText();
					 }
					is.close();    
				}              
				else             
				{                  
				     
				}  
			} catch (IOException | DocumentException  e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	     return result;
	}
	//生成0-9的随机数
	@Override
	public String CreateRandomMath(int length) {
		 String str="0123456789";  
	        Random random = new Random();  
	        StringBuffer sb = new StringBuffer();     
	        for(int i = 0 ; i < length; ++i){  
	            int number = random.nextInt(10);//[0,62)  s
	              
	            sb.append(str.charAt(number));  
	        }  
	        return sb.toString();  
	}
	//获取客户端IP地址
	@Override
	public String getCustomerIP(HttpServletRequest request) {
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
	//微信支付签名算法
	@Override
	public String getSignForPay(String appid, long timeStamp, String nonceStr, String signType, String prepay_id) {
		 HashMap map=new HashMap();
		 StringBuffer sb=new StringBuffer();
		 map.put("appId", appid);
		 map.put("nonceStr", nonceStr);
		 map.put("timeStamp",timeStamp);
		 map.put("signType", signType);
		 map.put("package", "prepay_id="+prepay_id);
		 
		 
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
		   String  stringA=sb.toString();
		   String  stringSignTemp=stringA+"&key="+CommonUtils.getWXKey();
		//   System.out.println(stringSignTemp);
		   // MD5加密
	       MessageDigest md;
	       String digest="";
			try {
				md = MessageDigest.getInstance("MD5");
				digest = CommonUtils.byteToString(md.digest(stringSignTemp.toString().getBytes()));
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String sign=digest.toUpperCase();
			//System.out.println(sign);
		return sign;
	}
	//获取多客服详情
	@Override
	public void getservicelist() {
		getAccessToken();
		String accesstoken=WeiXinMessage.getValue("service_access_token");
		String url="https://api.weixin.qq.com/cgi-bin/customservice/getkflist?access_token="+accesstoken;
		try {
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
	        System.out.println(message);
	       
	        is.close();
	        uc.disconnect();
				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
	}
}
