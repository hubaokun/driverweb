package com.daoshun.guangda.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author wangcl 管理员表
 */
@Entity
@Table(name = "t_admin_info")
public class AdminInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "adminid", length = 20, nullable = false)
	private int adminid;

	// 用户名
	@Column(name = "login_account", nullable = false)
	private String login_account;

	// 密码
	@Column(name = "password", nullable = false)
	private String password;

	// 关联驾校 :0表示无关联
	@Column(name = "schoolid", length = 10, nullable = false, columnDefinition = "INT default 0")
	private int schoolid;

	// 管理员类型 1.平台管理员 2.驾校管理员. 0.超级管理员
	@Column(name = "type", length = 1, nullable = false, columnDefinition = "INT default 0")
	private Integer type=0;

	// 管理员联系方式
	@Column(name = "telphone")
	private String telphone;

	// 管理员的真实姓名
	@Column(name = "realname")
	private String realname;

	// 添加时间
	@Column(name = "addtime", nullable = false)
	private Date addtime;

	// 管理员权限集合,逗号分隔.权限只对平台管理员有约束.超级管理员拥有所有权限.驾校管理员只拥有驾校的管理权限
	@Column(name = "permission")
	private String permission;
	
	@Transient
	private String schoolname;
	
	@Transient
	private String permissions;

	public int getAdminid() {
		return adminid;
	}

	public void setAdminid(int adminid) {
		this.adminid = adminid;
	}

	public String getLogin_account() {
		return login_account;
	}

	public void setLogin_account(String login_account) {
		this.login_account = login_account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getSchoolid() {
		return schoolid;
	}

	public void setSchoolid(int schoolid) {
		this.schoolid = schoolid;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	
	public String getSchoolname() {
		return schoolname;
	}

	
	public void setSchoolname(String schoolname) {
		this.schoolname = schoolname;
	}

	
	public String getPermissions() {
		return permissions;
	}

	
	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}
	
	
}
