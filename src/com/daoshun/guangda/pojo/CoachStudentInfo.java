package com.daoshun.guangda.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "t_coach_student")
public class CoachStudentInfo implements Serializable {

	private static final long serialVersionUID = 144937217233701639L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "recordid", length = 10, nullable = false)
	private Integer recordid;

	@Column(name = "coachid", length = 10, nullable = false, columnDefinition = "INT default 0")
	private Integer coachid;
	@Column(name = "studentid", length = 10, nullable = false, columnDefinition = "INT default 0")
	private Integer studentid;
	@Column(name = "money", nullable = false, columnDefinition = "Decimal(20,2) default 0.00")
	private BigDecimal money;
	@Column(name = "hour", length = 10, nullable = false, columnDefinition = "INT default 0")
	private int hour;
	
	@Transient
	private String coachPhone;
	
	@Transient
	private String studentPhone;
	
	@Transient
	private String coachname;
	
	@Transient
	private String studentname;

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

	public Integer getStudentid() {
		return studentid;
	}

	public void setStudentid(Integer studentid) {
		this.studentid = studentid;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public String getCoachPhone() {
		return coachPhone;
	}

	public void setCoachPhone(String coachPhone) {
		this.coachPhone = coachPhone;
	}

	public String getStudentPhone() {
		return studentPhone;
	}

	public void setStudentPhone(String studentPhone) {
		this.studentPhone = studentPhone;
	}

	public String getCoachname() {
		return coachname;
	}

	public void setCoachname(String coachname) {
		this.coachname = coachname;
	}

	public String getStudentname() {
		return studentname;
	}

	public void setStudentname(String studentname) {
		this.studentname = studentname;
	}
	
	
}
