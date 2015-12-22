package com.daoshun.guangda.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;



/**
 * 记录用户登录信息,包括重试登录,主要用于防止用户反复重试暴力破解
 * @author liukn
 *
 */
@Entity
@Table(name = "t_user_login_status")
public class UserLoginStatus implements Serializable {

	public final static int TYPE_STUDENT=1;
	public final static int TYPE_COACH=2;
	
	public final static int LOGIN_SUCCESS=1;
	public final static int LOGIN_FAIL=-1;
	// 版本id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", length = 10, nullable = false)
	private int id;

	@Column(name = "phone")
	private String phone;
	
	@Column(name = "userType")
	private int userType;
	
	// 添加时间
	@Column(name = "addtime")
	private Date addtime;
	
	//登录失败次数记录
	@Column(name="failedCount")
	private int failedCount=0;
	
	
	public int getId()
	{
		return id;
	}

	public void setId(int signupId)
	{
		this.id = signupId;
	}
	

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public int getUserType()
	{
		return userType;
	}

	public void setUserType(int userType)
	{
		this.userType = userType;
	}

	public Date getAddtime()
	{
		return addtime;
	}

	public void setAddtime(Date addtime)
	{
		this.addtime = addtime;
	}

	public int getFailedCount()
	{
		return failedCount;
	}

	public void setFailedCount(int failedCount)
	{
		this.failedCount = failedCount;
	}


	
	
	
	

}
