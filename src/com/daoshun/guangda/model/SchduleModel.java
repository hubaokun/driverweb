package com.daoshun.guangda.model;

import java.math.BigDecimal;

public class SchduleModel {
	private String date;
	private String hour;
	private int state;
	private int cancelstate;
	private BigDecimal price;
	private int isrest;
	private int addressid;
	private int subjectid;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getCancelstate() {
		return cancelstate;
	}

	public void setCancelstate(int cancelstate) {
		this.cancelstate = cancelstate;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getIsrest() {
		return isrest;
	}

	public void setIsrest(int isrest) {
		this.isrest = isrest;
	}

	public int getAddressid() {
		return addressid;
	}

	public void setAddressid(int addressid) {
		this.addressid = addressid;
	}

	public int getSubjectid() {
		return subjectid;
	}

	public void setSubjectid(int subjectid) {
		this.subjectid = subjectid;
	}

}
