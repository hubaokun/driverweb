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

@Entity
@Table(name = "t_coach_balance_record")
public class CoachBalancerecord implements Serializable {
	private static final long serialVersionUID = -279469671988283489L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "recordid", length = 10, nullable = false)
	private Integer recordid;

	@Column(name = "coachid", length = 10, nullable = false, columnDefinition = "INT default 0")
	private Integer coachid;

	@Column(name = "money", nullable = false, columnDefinition = "Decimal(20,2) default 0.00")
	private BigDecimal money;

	@Column(name = "gmoney", nullable = false, columnDefinition = "Decimal(20,2) default 0.00")
	private BigDecimal gmoney;

	@Column(name = "fmoney", nullable = false, columnDefinition = "Decimal(20,2) default 0.00")
	private BigDecimal fmoney;

	@Column(name = "addtime", nullable = false, columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
	private Date addtime;

	public Integer getRecordid() {
		return recordid;
	}

	public void setRecordid(Integer recordid) {
		this.recordid = recordid;
	}

	public Integer getCoachid() {
		return coachid;
	}

	public void setCoachid(Integer coachid) {
		this.coachid = coachid;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public BigDecimal getGmoney() {
		return gmoney;
	}

	public void setGmoney(BigDecimal gmoney) {
		this.gmoney = gmoney;
	}

	public BigDecimal getFmoney() {
		return fmoney;
	}

	public void setFmoney(BigDecimal fmoney) {
		this.fmoney = fmoney;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

}
