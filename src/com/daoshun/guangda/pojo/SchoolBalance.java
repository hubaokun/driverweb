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

@Entity
@Table(name = "t_school_balance")
public class SchoolBalance implements Serializable {
	private static final long serialVersionUID = 7400068811569305113L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "recordid", length = 10, nullable = false)
	private Integer recordid;

	@Column(name = "schoolid", length = 10, nullable = false, columnDefinition = "INT default 0")
	private Integer schoolid;

	@Column(name = "amount", nullable = false, columnDefinition = "Decimal(20,2) default 0.00")
	private BigDecimal amount;

	//1转入 2转出
	@Column(name = "type", length = 1, nullable = false, columnDefinition = "TINYINT default 0")
	private int type;

	@Column(name = "coachid", length = 10, nullable = false, columnDefinition = "INT default 0")
	private Integer coachid;

	@Column(name = "addtime", nullable = false, columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
	private Date addtime;
	
	@Transient
	private DriveSchoolInfo driveschool;

	public Integer getRecordid() {
		return recordid;
	}

	public void setRecordid(Integer recordid) {
		this.recordid = recordid;
	}

	public Integer getSchoolid() {
		return schoolid;
	}

	public void setSchoolid(Integer schoolid) {
		this.schoolid = schoolid;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Integer getCoachid() {
		return coachid;
	}

	public void setCoachid(Integer coachid) {
		this.coachid = coachid;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public DriveSchoolInfo getDriveschool() {
		return driveschool;
	}

	public void setDriveschool(DriveSchoolInfo driveschool) {
		this.driveschool = driveschool;
	}
	
}
