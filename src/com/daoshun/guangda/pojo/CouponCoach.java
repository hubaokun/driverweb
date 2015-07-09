package com.daoshun.guangda.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_coupon_coach")
public class CouponCoach implements Serializable {

	private static final long serialVersionUID = 6176903449912228735L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "recordid", length = 10, nullable = false)
	private Integer recordid;

	@Column(name = "couponid", length = 10, nullable = false, columnDefinition = "INT default 0")
	private Integer couponid;
	@Column(name = "coachid", length = 10, nullable = false, columnDefinition = "INT default 0")
	private Integer coachid;
	@Column(name = "gettime", nullable = false, columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
	private Date gettime;
	@Column(name = "value", length = 10, nullable = false, columnDefinition = "INT default 0")
	private Integer value;
	@Column(name = "ownertype", length = 1, nullable = false, columnDefinition = "TINYINT default 0")
	private int ownertype;
	@Column(name = "money_value", length = 10, nullable = false, columnDefinition = "INT default 0")
	private Integer money_value;
	@Column(name = "state", length = 1, nullable = false, columnDefinition = "TINYINT default 0")
	private int state;
	@Column(name = "ownerid", length = 10, nullable = false, columnDefinition = "INT default 0")
	private Integer ownerid;

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

	public Integer getCoachid() {
		return coachid;
	}

	public void setCoachid(Integer coachid) {
		this.coachid = coachid;
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

	public int getOwnertype() {
		return ownertype;
	}

	public void setOwnertype(int ownertype) {
		this.ownertype = ownertype;
	}

	public Integer getMoney_value() {
		return money_value;
	}

	public void setMoney_value(Integer money_value) {
		this.money_value = money_value;
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

}
