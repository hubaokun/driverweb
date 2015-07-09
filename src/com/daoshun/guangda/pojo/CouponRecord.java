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
 * 小巴券发放记录表
 * 
 * @author guok
 * 
 */
@Entity
@Table(name = "t_couponget_record")
public class CouponRecord implements Serializable {

	private static final long serialVersionUID = 888110791973506323L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "recordid", length = 10, nullable = false)
	private Integer recordid;

	@Column(name = "couponid", length = 10, nullable = false, columnDefinition = "INT default 0")
	private Integer couponid;

	@Column(name = "userid", length = 10, nullable = false, columnDefinition = "INT default 0")
	private Integer userid;

	@Column(name = "gettime", columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
	private Date gettime;

	@Column(name = "value", length = 10, nullable = false, columnDefinition = "INT default 0")
	private Integer value;

	@Column(name = "ownertype", nullable = false, columnDefinition = "TINYINT default 0")
	private Integer ownertype;
	// 状态 1:已经使用 0:未使用  2已经作废
	@Column(name = "state", nullable = false, columnDefinition = "TINYINT default 0")
	private int state;

	@Column(name = "ownerid", length = 10, nullable = false, columnDefinition = "INT default 0")
	private Integer ownerid;

	// 类型：1.时间券 2：抵价券
	@Column(name = "coupontype", nullable = false, columnDefinition = "TINYINT default 0")
	private int coupontype;

	// 到期时间
	@Column(name = "end_time", columnDefinition = "TIMESTAMP")
	private Date end_time;

	@Column(name = "money_value", nullable = false, columnDefinition = "INT default 0")
	private int money_value;

	@Transient
	private String usernick;

	@Transient
	private String schoolname;

	@Transient
	private String cusername;

	@Transient
	private String title;

	public Integer getRecordid() {
		return recordid;
	}

	public void setRecordid(Integer recordid) {
		this.recordid = recordid;
	}

	public Integer getCouponid() {
		return couponid;
	}

	public void setCouponid(Integer couponid) {
		this.couponid = couponid;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Date getGettime() {
		return gettime;
	}

	public void setGettime(Date gettime) {
		this.gettime = gettime;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public Integer getOwnertype() {
		return ownertype;
	}

	public void setOwnertype(Integer ownertype) {
		this.ownertype = ownertype;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Integer getOwnerid() {
		return ownerid;
	}

	public void setOwnerid(Integer ownerid) {
		this.ownerid = ownerid;
	}

	public int getCoupontype() {
		return coupontype;
	}

	public void setCoupontype(int coupontype) {
		this.coupontype = coupontype;
	}

	public String getUsernick() {
		return usernick;
	}

	public void setUsernick(String usernick) {
		this.usernick = usernick;
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

	public Date getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getMoney_value() {
		return money_value;
	}

	public void setMoney_value(int money_value) {
		this.money_value = money_value;
	}

}
