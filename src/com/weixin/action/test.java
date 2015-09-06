package com.weixin.action;

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
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.daoshun.common.CommonUtils;

public class test {

	public static void main(String[] args) throws IOException, JSONException {
	/*	 String APPID="wx697888f825457533";
		 String APPSECRET="8152cb86739fde0d1dc354a1074dfdda";
		 String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+ APPID + "&secret=" +APPSECRET;
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

         JSONObject json = new JSONObject(message);
         String accessToken = json.getString("access_token");
         String time = json.getString("expires_in");
         System.out.println(accessToken+"                "+time);*/
		test t=new test();
		t.createmenu();
		//t.getip();
	}
	
	public void createmenu() throws UnsupportedEncodingException, IOException
	{
		//String menu = "{\"button\":[{\"type\":\"click\",\"name\":\"今日歌曲\",\"key\":\"V1001_TODAY_MUSIC\"},{\"name\":\"菜单\",\"sub_button\":[{\"type\":\"view\",\"name\":\"搜索\",\"url\":\"http://www.soso.com/\"},{\"type\":\"view\",\"name\":\"视频\",\"url\":\"http://v.qq.com/\"},{\"type\":\"click\",\"name\":\"赞一下我们\",\"key\":\"V1001_GOOD\"}]}]}";
		String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=f-vFAp-bi7AzwvAUXvcrEyxjN0D1ZkevaOoe-b9JhNvGoNlHRcxeY-yCtNb6TciUXRgm2Ni020WBoXF6j6ZOmFeVoEHlY8NlrV1M4Il2fvk";
		String menu ="{\"button\":[{\"type\":\"view\",\"name\":\"小巴学车客户端\",\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx697888f825457533&redirect_uri=http://543bb233.tunnel.mobi/xiaoba/weixinWeb/login.jsp&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect\"}]}";
		URL u=new URL(url);
        HttpURLConnection uc=(HttpURLConnection) u.openConnection();
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
        System.out.println("返回信息"+message);
      

	}
	public void deletemenu() throws IOException
	{
		 String url = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=6trLxb5awlUGKS6E9-r3fgb0F20XAzwjszjvR4BQ52rLv4okYQ-sym74FRyobjs0ny3d8DMcBjCHXnzN7vd-FqF-Zy4hxmkinD9b5ZrSv-Y";
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
         System.out.println(message);
	}
	public void getip(String ip) throws IOException, JSONException
	{
		 String url = "http://api.map.baidu.com/location/ip?ak=MGt8L6pnsrogzRS6u4TKMGcX&ip="+ip+"&coor=bd09ll";
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

         JSONObject json = new JSONObject(message);
         String accessToken = json.getString("address");
         JSONObject json1=json.getJSONObject("content");
         JSONObject json2=json1.getJSONObject("address_detail");
        // String time = json.getString("expires_in");
         System.out.println(accessToken+"      "+json2.getString("city"));
	}
	
	 public void checksuccess(HttpServletRequest request, HttpServletResponse response) throws IOException{
	    	String TOKEN = "mZHlAgNp3zqhNh";
			 // 微信加密签名
	       String signature = request.getParameter("signature");
	       // 随机字符串
	       String echostr = request.getParameter("echostr");
	       // 时间戳
	       String timestamp = request.getParameter("timestamp");
	       // 随机数
	       String nonce = request.getParameter("nonce");
	      
	     
	       String[] str = { TOKEN, timestamp, nonce };
	       Arrays.sort(str); // 字典序排序
	       String bigStr = str[0] + str[1] + str[2];
	       // SHA1加密
	       MessageDigest md;
	       String digest="";
			try {
				md = MessageDigest.getInstance("SHA-1");
				digest = CommonUtils.byteToString(md.digest(bigStr.getBytes()));
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	       

	       // 确认请求来至微信
	       if (digest.equals(signature)) {
	           response.getWriter().print(echostr);
	       }
	    }
}
