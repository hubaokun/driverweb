package com.daoshun.guangda.NetData;

import java.math.BigDecimal;
import java.util.List;

public class CoinReportBySchool {
	    private Integer index;
        //ID
	    private String  payerid;
	    //名字
	    private String  payername;
	    //所属驾校
	    private String  school;
	    //手机号码
	    private String  phone;
	    //发放小巴币个数
	    private BigDecimal  coinnumber;
	    //已结算小巴币个数
	    private BigDecimal  coinpay;
	    //教练账户已兑换驾校小巴币个数
	    private BigDecimal  scoinchange;
	    //教练账户已兑换教练小巴币个数
	    private BigDecimal  coinchange;
	    //教练账户未兑换驾校小巴币个数
	    private BigDecimal  unscoinchange;
	    //教练账户未兑换教练小巴币个数
	    private BigDecimal  uncoinchange;
	    //已结算学时
	    private Integer  classhour;
	     //学员信息  
	    private List<StudentCoinReportBySchool> studentlist;
		public String getPayerid() {
			return payerid;
		}
		public void setPayerid(String payerid) {
			this.payerid = payerid;
		}
		public String getPayername() {
			return payername;
		}
		public void setPayername(String payername) {
			this.payername = payername;
		}
		public String getSchool() {
			return school;
		}
		public void setSchool(String school) {
			this.school = school;
		}
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		
		public BigDecimal getCoinnumber() {
			return coinnumber;
		}
		public void setCoinnumber(BigDecimal coinnumber) {
			this.coinnumber = coinnumber;
		}
		public BigDecimal getCoinpay() {
			return coinpay;
		}
		public void setCoinpay(BigDecimal coinpay) {
			this.coinpay = coinpay;
		}
		public BigDecimal getScoinchange() {
			return scoinchange;
		}
		public void setScoinchange(BigDecimal scoinchange) {
			this.scoinchange = scoinchange;
		}
		public BigDecimal getCoinchange() {
			return coinchange;
		}
		public void setCoinchange(BigDecimal coinchange) {
			this.coinchange = coinchange;
		}
		public BigDecimal getUnscoinchange() {
			return unscoinchange;
		}
		public void setUnscoinchange(BigDecimal unscoinchange) {
			this.unscoinchange = unscoinchange;
		}
		public BigDecimal getUncoinchange() {
			return uncoinchange;
		}
		public void setUncoinchange(BigDecimal uncoinchange) {
			this.uncoinchange = uncoinchange;
		}
		public Integer getClasshour() {
			return classhour;
		}
		public void setClasshour(Integer classhour) {
			this.classhour = classhour;
		}

		public List<StudentCoinReportBySchool> getStudentlist() {
			return studentlist;
		}
		public void setStudentlist(List<StudentCoinReportBySchool> studentlist) {
			this.studentlist = studentlist;
		}
		public Integer getIndex() {
			return index;
		}
		public void setIndex(Integer index) {
			this.index = index;
		}
	    
	    
}
