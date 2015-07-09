package com.daoshun.guangda.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.daoshun.common.CommonUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport implements ServletRequestAware{

	private static final long serialVersionUID = 1L;

	protected HttpServletRequest request;
	
	private HttpServletResponse response;

	
	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
		this.request = arg0;
	}

	// json格式转换 并输出
	public void strToJson(Object obj) {
		HttpServletResponse response = ServletActionContext.getResponse();
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat("yyyy-MM-dd hh:mm:ss");
		response.setContentType("text/html;charset=UTF-8");
		Gson gson = gsonBuilder.create();

		String json = gson.toJson(obj);
		PrintWriter out;
		response.setContentType("text/html;charset=UTF-8");
		try {
			out = response.getWriter();
			out.print(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setResultStr(String result) {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out;
		response.setContentType("text/html;charset=UTF-8");
		try {
			out = response.getWriter();
			out.print(result);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setResponseStr(String responseString){
		HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out;
		try {
			out = response.getWriter();
	        out.print(responseString);	        
	        out.flush();
	        out.close();	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 上传图片
	 * 
	 * @return
	 * @throws Exception
	 */
	public String uploadImg(File file, String filename) throws Exception {
		String path = null;
		if (file != null) {
			// d:upload/temp/
			String uploadPath = CommonUtils.getLocationPath() + CommonUtils.getTimeFormat(new Date(), "yyyyMMdd") + File.separator;
			CommonUtils.checkPath(uploadPath);
			// 取得后缀名
			String extension = filename.substring(filename.lastIndexOf(".")).toLowerCase();
			// 时间戳加两位随机数据
			String severPicName = CommonUtils.getTimeFormat(new Date(), "yyyyMMddhhmmssSSS") + "_" + (int) (Math.random() * 100) + extension;
			// 上传文件保存路径
			File savefile = new File(uploadPath, severPicName);
			if (!savefile.getParentFile().exists())
				savefile.getParentFile().mkdirs();
			// 复制文件到指定目录
			FileUtils.copyFile(file, savefile);
			// 保存路径
			path = savefile.toString().replace(File.separator, "/");
		}
		return path;
	}
	
	public Cookie getCookieByName(HttpServletRequest request,String name){
	    Map<String,Cookie> cookieMap = ReadCookieMap(request);
	    if(cookieMap.containsKey(name)){
	        Cookie cookie = (Cookie)cookieMap.get(name);
	        return cookie;
	    }else{
	        return null;
	    }   
	}
	
	private Map<String,Cookie> ReadCookieMap(HttpServletRequest request){  
	    Map<String,Cookie> cookieMap = new HashMap<String,Cookie>();
	    Cookie[] cookies = request.getCookies();
	    if(null!=cookies){
	        for(Cookie cookie : cookies){
	            cookieMap.put(cookie.getName(), cookie);
	        }
	    }
	    return cookieMap;
	}
}
