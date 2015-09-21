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
 * 小巴券表
 * 
 * @author guok
 * 
 */
@Entity
@Table(name = "t_coupon")
public class CouponInfo implements Serializable {

	private static final long serialVersionUID = 888110791973506323L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "couponid", length = 10, nullable = false)
	private Integer couponid;
	// 到期时间
	@Column(name = "end_time", columnDefinition = "TIMESTAMP")
	private Date end_time;
	// 面值
	@Column(name = "value", length = 10, nullable = false, columnDefinition = "INT default 0")
	private Integer value;
	// 类型：1.时间券 2：抵价券
	@Column(name = "coupontype", nullable = false, columnDefinition = "TINYINT default 0")
	private int coupontype;
	// 发券人类型 0:平台发放 1:驾校发 2:教练发放
	@Column(name = "ownertype", nullable = false, columnDefinition = "TINYINT default 0")
	private int ownertype;
	// 发放者ID
	@Column(name = "ownerid", length = 10, nullable = false, columnDefinition = "INT default 0")
	private Integer ownerid;
	// 添加时间
	@Column(name = "addtime", columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
	private Date addtime;
	// 发放数量
	@Column(name = "pub_count", length = 10, nullable = false, columnDefinition = "INT default 0")
	private Integer pub_count;
	// 剩余数量——已废弃
	@Column(name = "rest_count", length = 10, nullable = false, columnDefinition = "INT default 0")
	private Integer rest_count;
    //教练可发放小巴券剩余张数
	@Transient
	private Integer coach_rest;
	
	@Transient
	private String schoolname;
	
	@Transient
	private String cusername;
	
	public Integer getCouponid() {
		return couponid;
	}

	public void setCouponid(Integer couponid) {
		this.couponid = couponid;
	}

	public Date getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public int getCoupontype() {
		return coupontype;
	}

	public void setCoupontype(int coupontype) {
		this.coupontype = coupontype;
	}

	public int getOwnertype() {
		return ownertype;
	}

	public void setOwnertype(int ownertype) {
		this.ownertype = ownertype;
	}

	public Integer getOwnerid() {
		return ownerid;
	}

	public void setOwnerid(Integer ownerid) {
		this.ownerid = ownerid;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public Integer getPub_count() {
		return pub_count;
	}

	public void setPub_count(Integer pub_count) {
		this.pub_count = pub_count;
	}

	public Integer getRest_count() {
		return rest_count;
	}

	public void setRest_count(Integer rest_count) {
		this.rest_count = rest_count;
	}

	public String getSchoolname() {
		return schoolname;
	}

	public void setSchoolname(String schoolname) {
		this.schoolname = schoolname;
	}

	public String getCusername() {
		return cusername;
	}

	public void setCusername(String cusername) {
		this.cusername = cusername;
	}

	public Integer getCoach_rest() {
		return coach_rest;
	}

	public void setCoach_rest(Integer coach_rest) {
		this.coach_rest = coach_rest;
	}

}

