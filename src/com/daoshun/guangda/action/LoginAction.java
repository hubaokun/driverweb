package com.daoshun.guangda.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.daoshun.common.CommonUtils;
import com.daoshun.guangda.pojo.AdminInfo;
import com.daoshun.guangda.service.ICUserService;

@ParentPackage("default")
@Controller
@Scope("prototype")
public class LoginAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Resource
	private ICUserService cuserService;

	private AdminInfo admin;

	private String loginname;

	private String password;

	private String errmessage;

	private String issession;

	private String inputCode;
	
	private int jump;
	
	private List<Integer> newpermissions;
	
	@Action(value = "login", results = { 
			@Result(name = "SUCCESS1", location = "/getAdminInfolist.do?index=0", type = "redirect"),
			@Result(name = "SUCCESS2", location = "/getCoachlist.do?index=1", type = "redirect"),
			@Result(name = "SUCCESS3", location = "/getCoachApplyList.do?index=2", type = "redirect"),
			@Result(name = "SUCCESS4", location = "/getTimeCancel.do?index=3", type = "redirect"),
			@Result(name = "SUCCESS5", location = "/getNoticeList.do?index=4", type = "redirect"),
			@Result(name = "SUCCESS6", location = "/getOrderList.do?index=5", type = "redirect"),
			@Result(name = "SUCCESS7", location = "/getComplaintList.do?index=6", type = "redirect"),
			@Result(name = "SUCCESS8", location = "/getdriveSchoolById.do?index=7", type = "redirect"),
			@Result(name = "SUCCESS9", location = "/getCouponList.do?index=8", type = "redirect"),
			@Result(name = "SUCCESS10", location = "/systemdaily.do?index=10", type = "redirect"),
			@Result(name = "SUCCESS11", location = "/getFeedback.do?index=9", type = "redirect"),
			@Result(name = "SUCCESS12", location = "/CoachLog.do?index=11", type = "redirect"),
			@Result(name = ERROR, location = "/login.jsp") })
	public String login() {
		if (CommonUtils.parseInt(issession, 0) == 1) {
			errmessage = "长时间未操作，请重新登录";
			return ERROR;
		}
		if (CommonUtils.isEmptyString(loginname)) {
			errmessage = "请输入登录用户名";
			return ERROR;
		}
		if (CommonUtils.isEmptyString(password)) {
			errmessage = "请输入密码";
			return ERROR;
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpSession session = ServletActionContext.getRequest().getSession();
		session.setAttribute("session_err", "");
		/*
		 * String verifyCode = (String) session.getAttribute("validateCode"); if (inputCode != null && verifyCode != null) { if (!verifyCode.toUpperCase().equals(inputCode.toUpperCase())) { errmessage
		 * = "验证码错误"; return ERROR; } }else{ errmessage = "验证码错误"; return ERROR; }
		 */
		admin = cuserService.getAdminInfo(loginname);
		if (admin != null) {
			if (admin.getPassword().equals(password)) {
				if (admin.getType() == 0) {
					//超级管理员
					session.setAttribute("loginname", admin.getRealname());
					session.setAttribute("userid", admin.getAdminid());
					session.setAttribute("usertype", admin.getType());
					session.setAttribute("schoolid", 0);
					Cookie cookie = getCookieByName(request, "loginname");
					if (cookie == null) {
						cookie = new Cookie("loginname", loginname);
					} else {
						cookie.setValue(loginname);
					}
					response.addCookie(cookie);
					Cookie cookie2 = getCookieByName(request, "password");
					if (cookie2 == null) {
						cookie2 = new Cookie("password", password);
					} else {
						cookie2.setValue(password);
					}
					response.addCookie(cookie2);
					return "SUCCESS1";
				} else if (admin.getType() == 1) {
					//平台管理员
					session.setAttribute("loginname", admin.getRealname());
					session.setAttribute("userid", admin.getAdminid());
					session.setAttribute("usertype", admin.getType());
					session.setAttribute("schoolid",0);
					 newpermissions=new ArrayList<Integer>();
					String[] permissions = admin.getPermission().split(",");
					for (int i = 0; i < permissions.length; i++) {
					newpermissions.add(CommonUtils.parseInt(permissions[i], 0));
					}
					for (int i = 0; i < newpermissions.size(); i++) {
					}
					session.setAttribute("permission", newpermissions);
					Cookie cookie = getCookieByName(request, "loginname");
					if (cookie == null) {
						cookie = new Cookie("loginname", loginname);
					} else {
						cookie.setValue(loginname);
					}
					response.addCookie(cookie);
					Cookie cookie2 = getCookieByName(request, "password");
					if (cookie2 == null) {
						cookie2 = new Cookie("password", password);
					} else {
						cookie2.setValue(password);
					}
					response.addCookie(cookie2);
					jump=CommonUtils.parseInt(permissions[0], 0);
					return "SUCCESS"+jump;
				} else {
					//驾校管理员
					session.setAttribute("loginname", admin.getRealname());
					session.setAttribute("userid", admin.getAdminid());
					session.setAttribute("usertype", admin.getType());
					session.setAttribute("schoolid", admin.getSchoolid());
					Cookie cookie = getCookieByName(request, "loginname");
					if (cookie == null) {
						cookie = new Cookie("loginname", loginname);
					} else {
						cookie.setValue(loginname);
					}
					response.addCookie(cookie);
					Cookie cookie2 = getCookieByName(request, "password");
					if (cookie2 == null) {
						cookie2 = new Cookie("password", password);
					} else {
						cookie2.setValue(password);
					}
					response.addCookie(cookie2);
					return "SUCCESS8";
				}
			} else {
				errmessage = "用户、密码错误";
				return ERROR;
			}
		}
		errmessage = "用户、密码错误";
		return ERROR;
	}

	@Action(value = "logout")
	public void logout() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		session.invalidate();
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getErrmessage() {
		return errmessage;
	}

	public void setErrmessage(String errmessage) {
		this.errmessage = errmessage;
	}

	public String getIssession() {
		return issession;
	}

	public void setIssession(String issession) {
		this.issession = issession;
	}

	public String getInputCode() {
		return inputCode;
	}

	public void setInputCode(String inputCode) {
		this.inputCode = inputCode;
	}

	public AdminInfo getAdmin() {
		return admin;
	}

	public void setAdmin(AdminInfo admin) {
		this.admin = admin;
	}

	
	public int getJump() {
		return jump;
	}

	
	public void setJump(int jump) {
		this.jump = jump;
	}

	
	public List<Integer> getNewpermissions() {
		return newpermissions;
	}

	
	public void setNewpermissions(List<Integer> newpermissions) {
		this.newpermissions = newpermissions;
	}

	
	
}
