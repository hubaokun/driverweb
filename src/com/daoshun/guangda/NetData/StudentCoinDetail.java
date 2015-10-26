package com.daoshun.guangda.NetData;

import java.math.BigDecimal;

public class StudentCoinDetail {
	private String phone;
    
    private String name;
    
    private BigDecimal coinusenumber;
    
    private BigDecimal coinpaynumber;
    
    
    private String classhour;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getCoinusenumber() {
		return coinusenumber;
	}

	public void setCoinusenumber(BigDecimal coinusenumber) {
		this.coinusenumber = coinusenumber;
	}

	public BigDecimal getCoinpaynumber() {
		return coinpaynumber;
	}

	public void setCoinpaynumber(BigDecimal coinpaynumber) {
		this.coinpaynumber = coinpaynumber;
	}

	public String getClasshour() {
		return classhour;
	}

	public void setClasshour(String classhour) {
		this.classhour = classhour;
	}
    
    
}
