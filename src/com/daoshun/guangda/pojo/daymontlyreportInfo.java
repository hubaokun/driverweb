package com.daoshun.guangda.pojo;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.transaction.Transactional;
@Entity
@Table(name="t_daymonthly")

public class DaymontlyReportInfo {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
	private Integer id;
	@Column(name="querydate")
	private Date querydate;
	@Column(name="coachregister",columnDefinition = "INT default 0")
	private Integer  coachregister;
	@Column(name="studentregister",columnDefinition = "INT default 0")
	private Integer  studentregister;
	@Column(name="coachcertification",columnDefinition = "INT default 0")
	private Integer	 coachcertification; 
	@Column(name="coachcoursetotal",columnDefinition = "INT default 0")
	private Integer	 coachcoursetotal; 
	@Column(name="coachcourseconfirm",columnDefinition = "INT default 0")
	private Integer	 coachcourseconfirm ;
	@Column(name="studentbooked",columnDefinition = "INT default 0")
	private Integer	 studentbooked ;
	@Column(name="studentconfirm",columnDefinition = "INT default 0")
	private Integer	 studentconfirm ;
	@Column(name="orderbycoupon",columnDefinition = "INT default 0")
	private Integer	 orderbycoupon ;
	@Column(name="orderbycoin",columnDefinition = "INT default 0")
	private Integer	 orderbycoin;
	@Column(name="orderbyaccount",columnDefinition = "INT default 0")
	private Integer	 orderbyaccount ;
	@Column(name="s_couponplatform",columnDefinition = "INT default 0")
	private Integer	 s_couponplatform ;
	@Column(name="s_couponschool",columnDefinition = "INT default 0")
	private Integer	 s_couponschool ;
	@Column(name="s_couponcoach",columnDefinition = "INT default 0")
	private Integer	 s_couponcoach ;
	@Column(name="c_couponplatform",columnDefinition = "INT default 0")
	private Integer	 c_couponplatform ;
	@Column(name="c_couponschool",columnDefinition = "INT default 0")
	private Integer	 c_couponschool;
	@Column(name="c_couponcoach",columnDefinition = "INT default 0")
	private Integer	 c_couponcoach ;
	@Column(name="s_coinplatform",nullable = false,columnDefinition = "Decimal(20,2) default 0.00")
	private BigDecimal  s_coinplatform;
	@Column(name="s_coinschool",nullable = false,columnDefinition = "Decimal(20,2) default 0.00")
	private BigDecimal	s_coinschool;
	@Column(name="s_coincoach",nullable = false,columnDefinition = "Decimal(20,2) default 0.00")
	private BigDecimal	s_coincoach;
	@Column(name="c_coinplatform",nullable = false,columnDefinition = "Decimal(20,2) default 0.00")
	private BigDecimal	c_coinplatform;
	@Column(name="c_coinschool",nullable = false,columnDefinition = "Decimal(20,2) default 0.00")
	private BigDecimal	c_coinschool;
	@Column(name="c_coincoach",nullable = false,columnDefinition = "Decimal(20,2) default 0.00")
	private BigDecimal	c_coincoach;
	@Column(name="coachorderprice",nullable = false,columnDefinition = "Decimal(20,2) default 0.00")
	private BigDecimal	coachorderprice;
	@Column(name="coachrecharge",nullable = false,columnDefinition = "Decimal(20,2) default 0.00")
	private BigDecimal	coachrecharge;
	@Column(name="studentrecharge",nullable = false,columnDefinition = "Decimal(20,2) default 0.00")
	private BigDecimal	studentrecharge;
	@Column(name="studentapplycash",nullable = false,columnDefinition = "Decimal(20,2) default 0.00")
	private BigDecimal	studentapplycash;
	
	@Column(name="now_month",columnDefinition = "INT default 0")
	private Integer now_month;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getQuerydate() {
		return querydate;
	}
	public void setQuerydate(Date querydate) {
		this.querydate = querydate;
	}
	public Integer getCoachregister() {
		return coachregister;
	}
	public void setCoachregister(Integer coachregister) {
		this.coachregister = coachregister;
	}
	public Integer getStudentregister() {
		return studentregister;
	}
	public void setStudentregister(Integer studentregister) {
		this.studentregister = studentregister;
	}
	public Integer getCoachcertification() {
		return coachcertification;
	}
	public void setCoachcertification(Integer coachcertification) {
		this.coachcertification = coachcertification;
	}
	public Integer getCoachcoursetotal() {
		return coachcoursetotal;
	}
	public void setCoachcoursetotal(Integer coachcoursetotal) {
		this.coachcoursetotal = coachcoursetotal;
	}
	public Integer getCoachcourseconfirm() {
		return coachcourseconfirm;
	}
	public void setCoachcourseconfirm(Integer coachcourseconfirm) {
		this.coachcourseconfirm = coachcourseconfirm;
	}
	public Integer getStudentbooked() {
		return studentbooked;
	}
	public void setStudentbooked(Integer studentbooked) {
		this.studentbooked = studentbooked;
	}
	public Integer getStudentconfirm() {
		return studentconfirm;
	}
	public void setStudentconfirm(Integer studentconfirm) {
		this.studentconfirm = studentconfirm;
	}
	public Integer getOrderbycoupon() {
		return orderbycoupon;
	}
	public void setOrderbycoupon(Integer orderbycoupon) {
		this.orderbycoupon = orderbycoupon;
	}
	public Integer getOrderbycoin() {
		return orderbycoin;
	}
	public void setOrderbycoin(Integer orderbycoin) {
		this.orderbycoin = orderbycoin;
	}
	public Integer getOrderbyaccount() {
		return orderbyaccount;
	}
	public void setOrderbyaccount(Integer orderbyaccount) {
		this.orderbyaccount = orderbyaccount;
	}
	public Integer getS_couponplatform() {
		return s_couponplatform;
	}
	public void setS_couponplatform(Integer s_couponplatform) {
		this.s_couponplatform = s_couponplatform;
	}
	public Integer getS_couponschool() {
		return s_couponschool;
	}
	public void setS_couponschool(Integer s_couponschool) {
		this.s_couponschool = s_couponschool;
	}
	public Integer getS_couponcoach() {
		return s_couponcoach;
	}
	public void setS_couponcoach(Integer s_couponcoach) {
		this.s_couponcoach = s_couponcoach;
	}
	public Integer getC_couponplatform() {
		return c_couponplatform;
	}
	public void setC_couponplatform(Integer c_couponplatform) {
		this.c_couponplatform = c_couponplatform;
	}
	public Integer getC_couponschool() {
		return c_couponschool;
	}
	public void setC_couponschool(Integer c_couponschool) {
		this.c_couponschool = c_couponschool;
	}
	public Integer getC_couponcoach() {
		return c_couponcoach;
	}
	public void setC_couponcoach(Integer c_couponcoach) {
		this.c_couponcoach = c_couponcoach;
	}
	
	public BigDecimal getS_coinplatform() {
		return s_coinplatform;
	}
	public void setS_coinplatform(BigDecimal s_coinplatform) {
		this.s_coinplatform = s_coinplatform;
	}
	public BigDecimal getS_coinschool() {
		return s_coinschool;
	}
	public void setS_coinschool(BigDecimal s_coinschool) {
		this.s_coinschool = s_coinschool;
	}
	public BigDecimal getS_coincoach() {
		return s_coincoach;
	}
	public void setS_coincoach(BigDecimal s_coincoach) {
		this.s_coincoach = s_coincoach;
	}
	public BigDecimal getC_coinplatform() {
		return c_coinplatform;
	}
	public void setC_coinplatform(BigDecimal c_coinplatform) {
		this.c_coinplatform = c_coinplatform;
	}
	public BigDecimal getC_coinschool() {
		return c_coinschool;
	}
	public void setC_coinschool(BigDecimal c_coinschool) {
		this.c_coinschool = c_coinschool;
	}
	public BigDecimal getC_coincoach() {
		return c_coincoach;
	}
	public void setC_coincoach(BigDecimal c_coincoach) {
		this.c_coincoach = c_coincoach;
	}
	public BigDecimal getCoachorderprice() {
		return coachorderprice;
	}
	public void setCoachorderprice(BigDecimal coachorderprice) {
		this.coachorderprice = coachorderprice;
	}
	public BigDecimal getCoachrecharge() {
		return coachrecharge;
	}
	public void setCoachrecharge(BigDecimal coachrecharge) {
		this.coachrecharge = coachrecharge;
	}
	public BigDecimal getStudentrecharge() {
		return studentrecharge;
	}
	public void setStudentrecharge(BigDecimal studentrecharge) {
		this.studentrecharge = studentrecharge;
	}
	public BigDecimal getStudentapplycash() {
		return studentapplycash;
	}
	public void setStudentapplycash(BigDecimal studentapplycash) {
		this.studentapplycash = studentapplycash;
	}
	public Integer getNow_month() {
		return now_month;
	}
	public void setNow_month(Integer now_month) {
		this.now_month = now_month;
	}

	
	
	
}
