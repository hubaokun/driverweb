package com.daoshun.guangda.NetData;

import java.util.Date;

import javax.persistence.Column;

public class CouponRecordForExcel {

	private int id;
	
	//用户名
	private String username;
	
	//手机号
	private String phone;
	
	//面值
	private Integer value;
	
	//发券人
	private String owner;
	
	//发放时间
	private Date gettime;
	
	//使用状态
	private int state;
	
	//使用时间
	private Date usetime;
	
	//使用截止日期
	private Date end_time;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public Date getGettime() {
		return gettime;
	}

	public void setGettime(Date gettime) {
		this.gettime = gettime;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Date getUsetime() {
		return usetime;
	}

	public void setUsetime(Date usetime) {
		this.usetime = usetime;
	}

	public Date getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}
	
	
	
	

}
