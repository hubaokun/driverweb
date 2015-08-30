package com.daoshun.guangda.NetData;

import java.math.BigDecimal;
import java.util.List;

public class CoinReportMontly {
    
	private  Integer id;
	
	private String coachid;
	
    private String coachname;
    
    private String schoolname;
    
    private String coachphone;
    
    private BigDecimal coinnumber;
    
    private BigDecimal coinnpaycount;
    
    private List<StudentCoinDetail>  studentdetaillist;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCoachname() {
		return coachname;
	}

	public void setCoachname(String coachname) {
		this.coachname = coachname;
	}

	public String getSchoolname() {
		return schoolname;
	}

	public void setSchoolname(String schoolname) {
		this.schoolname = schoolname;
	}

	public String getCoachphone() {
		return coachphone;
	}

	public void setCoachphone(String coachphone) {
		this.coachphone = coachphone;
	}

	public BigDecimal getCoinnumber() {
		return coinnumber;
	}

	public void setCoinnumber(BigDecimal coinnumber) {
		this.coinnumber = coinnumber;
	}

	public BigDecimal getCoinnpaycount() {
		return coinnpaycount;
	}

	public void setCoinnpaycount(BigDecimal coinnpaycount) {
		this.coinnpaycount = coinnpaycount;
	}

	public List<StudentCoinDetail> getStudentdetaillist() {
		return studentdetaillist;
	}

	public void setStudentdetaillist(List<StudentCoinDetail> studentdetaillist) {
		this.studentdetaillist = studentdetaillist;
	}

	public String getCoachid() {
		return coachid;
	}

	public void setCoachid(String coachid) {
		this.coachid = coachid;
	}
    
    
}
