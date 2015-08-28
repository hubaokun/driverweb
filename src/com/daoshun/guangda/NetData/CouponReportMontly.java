package com.daoshun.guangda.NetData;

import java.util.List;

public class CouponReportMontly {
	
	private Integer id;
	
	private String coachid;
	
    private String coachname;
    
    private String coachphone;
    
    private String schoolname;
    
    private Integer couponnumber;
    
    private Integer couponpaycount;
    
    private List<StudentCouponDetail>  studentdetaillist;

	public String getCoachname() {
		return coachname;
	}

	public void setCoachname(String coachname) {
		this.coachname = coachname;
	}

	public String getCoachphone() {
		return coachphone;
	}

	public void setCoachphone(String coachphone) {
		this.coachphone = coachphone;
	}

	public String getSchoolname() {
		return schoolname;
	}

	public void setSchoolname(String schoolname) {
		this.schoolname = schoolname;
	}

	public Integer getCouponnumber() {
		return couponnumber;
	}

	public void setCouponnumber(Integer couponnumber) {
		this.couponnumber = couponnumber;
	}

	public Integer getCouponpaycount() {
		return couponpaycount;
	}

	public void setCouponpaycount(Integer couponpaycount) {
		this.couponpaycount = couponpaycount;
	}

	public List<StudentCouponDetail> getStudentdetaillist() {
		return studentdetaillist;
	}

	public void setStudentdetaillist(List<StudentCouponDetail> studentdetaillist) {
		this.studentdetaillist = studentdetaillist;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCoachid() {
		return coachid;
	}

	public void setCoachid(String coachid) {
		this.coachid = coachid;
	}
    
    
}
