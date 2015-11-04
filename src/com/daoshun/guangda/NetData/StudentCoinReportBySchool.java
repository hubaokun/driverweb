package com.daoshun.guangda.NetData;

import java.math.BigDecimal;

public class StudentCoinReportBySchool {
    //学员手机号  
	private String phone;
	//姓名
	private String name;
	//获取驾校小巴币
	private BigDecimal scoinnumber;
	//获取教练小巴币
	private BigDecimal coinnumber;
	//已使用驾校小巴币
	private BigDecimal usedscoinnumber;
	//已使用教练小巴币
	private BigDecimal usedcoinnumber;
	//已使用学时
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
	
	public BigDecimal getScoinnumber() {
		return scoinnumber;
	}
	public void setScoinnumber(BigDecimal scoinnumber) {
		this.scoinnumber = scoinnumber;
	}
	public BigDecimal getCoinnumber() {
		return coinnumber;
	}
	public void setCoinnumber(BigDecimal coinnumber) {
		this.coinnumber = coinnumber;
	}
	public BigDecimal getUsedscoinnumber() {
		return usedscoinnumber;
	}
	public void setUsedscoinnumber(BigDecimal usedscoinnumber) {
		this.usedscoinnumber = usedscoinnumber;
	}
	public BigDecimal getUsedcoinnumber() {
		return usedcoinnumber;
	}
	public void setUsedcoinnumber(BigDecimal usedcoinnumber) {
		this.usedcoinnumber = usedcoinnumber;
	}
	public String getClasshour() {
		return classhour;
	}
	public void setClasshour(String classhour) {
		this.classhour = classhour;
	}
	
	
}
