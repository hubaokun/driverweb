package com.daoshun.guangda.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;



import com.daoshun.common.CommonUtils;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class SessionInterceptorAction extends AbstractInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6993098731302572901L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		Object action = invocation.getAction();
//		System.out.println("------------bg---session---------");
		// 不做该项拦截
		if (action instanceof LoginAction) {
			return invocation.invoke();
		}
		Map<String, Object> session = invocation.getInvocationContext().getSession();
		     HttpServletRequest request = ServletActionContext.getRequest();
//		 	if(isAjaxRequest(request)){
//				setResponseStr("sessionerror");
//				return null;
//			}
		String userid = String.valueOf(session.get("userid"));  
//		String typestr = String.valueOf(session.get(CommonUtils.MD5(userid))); 
//		int usertype = CommonUtils.parseInt(typestr, 0);
//		if(usertype==1||usertype==2){
//			if(!(action instanceof VmmisuserAction)){
//				if(isAjaxRequest(request)){
//					setResponseStr("sessionerror");
//					return null;
//				}else{
//				  return "sessionnull"; 
//				}
//			}
//		}
//		if(usertype==3||usertype==4){
//			if(!(action instanceof VmcustomerAction)){
//				if(isAjaxRequest(request)){
//					setResponseStr("sessionerror");
//					return null;
//				}else{
//				  return "sessionnull"; 
//				}
//			}
//		}
//		if(usertype==5||usertype==6){
//			if(!(action instanceof VmunitAction)){
//				if(isAjaxRequest(request)){
//					setResponseStr("sessionerror");
//					return null;
//				}else{
//				  return "sessionnull"; 
//				}
//			}
//		}
//		if(usertype==7){
//			if(!(action instanceof VmcarderAction)){
//				if(isAjaxRequest(request)){
//					setResponseStr("sessionerror");
//					return null;
//				}else{
//				  return "sessionnull"; 
//				}
//			}
//		}
		if (CommonUtils.parseInt(userid, 0) > 0) {
			return invocation.invoke();  
		}
		if(isAjaxRequest(request)){
			setResponseStr("sessionerror");
			return null;
		}else{
		  return "sessionnull"; 
		}
	}
	
	 private boolean isAjaxRequest(HttpServletRequest request) {  
	        String header = request.getHeader("X-Requested-With");  
	        if (header != null && "XMLHttpRequest".equals(header))  
	            return true;  
	        else  
	            return false;  
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
}
