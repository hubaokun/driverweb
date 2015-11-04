package com.daoshun.guangda.NetData;

import java.math.BigDecimal;

public class CoinDetailMontlyStudentInfoOfCoach {

	public CoinDetailMontlyStudentInfoOfCoach() {
		super();
	}
	private int id=0;
	private String c_phone=""; 
	private String c_name="";
	private BigDecimal c_scoinnumber=new BigDecimal(0);
	private BigDecimal c_coinnumber=new BigDecimal(0);
	private BigDecimal c_usedscoinnumber=new BigDecimal(0);
	private BigDecimal c_usedcoinnumber=new BigDecimal(0);
	private BigDecimal c_coinpay=new BigDecimal(0);
	private int c_classhour=0;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getC_phone() {
		return c_phone;
	}
	public void setC_phone(String c_phone) {
		this.c_phone = c_phone;
	}
	public String getC_name() {
		return c_name;
	}
	public void setC_name(String c_name) {
		this.c_name = c_name;
	}
	public BigDecimal getC_scoinnumber() {
		return c_scoinnumber;
	}
	public void setC_scoinnumber(BigDecimal c_scoinnumber) {
		this.c_scoinnumber = c_scoinnumber;
	}
	public BigDecimal getC_coinnumber() {
		return c_coinnumber;
	}
	public void setC_coinnumber(BigDecimal c_coinnumber) {
		this.c_coinnumber = c_coinnumber;
	}
	public BigDecimal getC_usedscoinnumber() {
		return c_usedscoinnumber;
	}
	public void setC_usedscoinnumber(BigDecimal c_usedscoinnumber) {
		this.c_usedscoinnumber = c_usedscoinnumber;
	}
	public BigDecimal getC_usedcoinnumber() {
		return c_usedcoinnumber;
	}
	public void setC_usedcoinnumber(BigDecimal c_usedcoinnumber) {
		this.c_usedcoinnumber = c_usedcoinnumber;
	}
	public BigDecimal getC_coinpay() {
		return c_coinpay;
	}
	public void setC_coinpay(BigDecimal c_coinpay) {
		this.c_coinpay = c_coinpay;
	}
	public int getC_classhour() {
		return c_classhour;
	}
	public void setC_classhour(int c_classhour) {
		this.c_classhour = c_classhour;
	}
}
