package com.daoshun.guangda.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_coach_booktime")
public class CBookTimeInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -279469671988283489L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id",length=10,nullable=false)
	private int id;
	
	//教练ID
	@Column(name="coachid",length=10,nullable=false)
	private int coachid;
	
	//被预定时间的集合
	@Column(name="bookedtime",length=500,nullable=false)
	private String bookedtime;
	
	//日期
	@Column(name="date",length=20,nullable=false)
	private String date;

	
	public int getId() {
		return id;
	}

	
	public void setId(int id) {
		this.id = id;
	}

	
	public int getCoachid() {
		return coachid;
	}

	
	public void setCoachid(int coachid) {
		this.coachid = coachid;
	}

	
	public String getBookedtime() {
		return bookedtime;
	}

	
	public void setBookedtime(String bookedtime) {
		this.bookedtime = bookedtime;
	}

	
	public String getDate() {
		return date;
	}

	
	public void setDate(String date) {
		this.date = date;
	}
	
	
}
