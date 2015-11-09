package com.daoshun.guangda.NetData;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
public class CoinReportMontlyOfSchoolData {
	public CoinReportMontlyOfSchoolData() {
		super();
		detailsOfStudent=new ArrayList<CoinDetailMontlyStudentInfoOfCoach>();
		// TODO Auto-generated constructor stub
	}
    private int id=0;
    private int c_payerid=0;
    private String c_name="";
    private String c_school="";
    private String c_phone="";  
    private BigDecimal c_coinnumber=new BigDecimal(0);
    private BigDecimal c_coinpay=new BigDecimal(0);
	private BigDecimal c_scoinchange=new BigDecimal(0);
    private BigDecimal  c_coinchange=new BigDecimal(0);
    private BigDecimal c_unscoinchange=new BigDecimal(0);
    private BigDecimal c_uncoinchange=new BigDecimal(0);
	private int c_classhour=0;
	private int c_type=0;
	
	private int varValue=1;//币值
	
	public int getVarValue() {
		return varValue;
	}
	public void setVarValue(int varValue) {
		this.varValue = varValue;
	}
	private List<CoinDetailMontlyStudentInfoOfCoach> detailsOfStudent;
	private int count=0;
	public int getCount() {
		return count;
	}
	
	public void countPlus()
	{
		this.count=this.count+1;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public void addDetail(CoinDetailMontlyStudentInfoOfCoach detail)
	{
		if(detail!=null)
		{
			detailsOfStudent.add(detail);
		}
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getC_payerid() {
		return c_payerid;
	}
	public void setC_payerid(int c_payerid) {
		this.c_payerid = c_payerid;
	}
	public String getC_name() {
		return c_name;
	}
	public void setC_name(String c_name) {
		this.c_name = c_name;
	}
	public String getC_school() {
		return c_school;
	}
	public void setC_school(String c_school) {
		this.c_school = c_school;
	}
	public String getC_phone() {
		return c_phone;
	}
	public void setC_phone(String c_phone) {
		this.c_phone = c_phone;
	}
	public BigDecimal getC_coinnumber() {
		return c_coinnumber;
	}
	public void setC_coinnumber(BigDecimal c_coinnumber) {
		this.c_coinnumber = c_coinnumber;
	}
	public BigDecimal getC_coinpay() {
		return c_coinpay;
	}
	public void setC_coinpay(BigDecimal c_coinpay) {
		this.c_coinpay = c_coinpay;
	}
	public BigDecimal getC_scoinchange() {
		return c_scoinchange;
	}
	public void setC_scoinchange(BigDecimal c_scoinchange) {
		this.c_scoinchange = c_scoinchange;
	}
	public BigDecimal getC_coinchange() {
		return c_coinchange;
	}
	public void setC_coinchange(BigDecimal c_coinchange) {
		this.c_coinchange = c_coinchange;
	}
	public BigDecimal getC_unscoinchange() {
		return c_unscoinchange;
	}
	public void setC_unscoinchange(BigDecimal c_unscoinchange) {
		this.c_unscoinchange = c_unscoinchange;
	}
	public BigDecimal getC_uncoinchange() {
		return c_uncoinchange;
	}
	public void setC_uncoinchange(BigDecimal c_uncoinchange) {
		this.c_uncoinchange = c_uncoinchange;
	}
	public int getC_classhour() {
		return c_classhour;
	}
	public void setC_classhour(int c_classhour) {
		this.c_classhour = c_classhour;
	}
	public int getC_type() {
		return c_type;
	}
	public void setC_type(int c_type) {
		this.c_type = c_type;
	}
	public List<CoinDetailMontlyStudentInfoOfCoach> getDetailsOfStudent() {
		return detailsOfStudent;
	}
	public void setDetailsOfStudent(List<CoinDetailMontlyStudentInfoOfCoach> detailsOfStudent) {
		this.detailsOfStudent = detailsOfStudent;
	}


}
