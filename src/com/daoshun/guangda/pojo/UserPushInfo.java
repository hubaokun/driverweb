package com.daoshun.guangda.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户个推信息表
 * @author liukn
 *
 */
@Entity
@Table(name = "t_userpush")
public class UserPushInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7042653152685860506L;

	// 数据id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pushid", length = 10, nullable = false)
	private int pushid;

	// 用户id
	@Column(name = "userid", length = 10, nullable = false)
	private int userid;

	// 用户类型 1、教练 2、学员
	@Column(name = "usertype", length = 1, nullable = false, columnDefinition = "INT default 0")
	private Integer usertype=0;

	// 个推id
	@Column(name = "jpushid", length = 100)
	private String jpushid;

	// 设备类型 0、安卓 1、ios
	@Column(name = "type", length = 1, nullable = false, columnDefinition = "INT default 0")
	private Integer type;

	// iphone设备标识
	@Column(name = "devicetoken")
	private String devicetoken;

	public int getPushid() {
		return pushid;
	}

	public void setPushid(int pushid) {
		this.pushid = pushid;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public Integer getUsertype() {
		return usertype;
	}

	public void setUsertype(Integer usertype) {
		this.usertype = usertype;
	}

	public String getJpushid() {
		return jpushid;
	}

	public void setJpushid(String jpushid) {
		this.jpushid = jpushid;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getDevicetoken() {
		return devicetoken;
	}

	public void setDevicetoken(String devicetoken) {
		this.devicetoken = devicetoken;
	}
}
