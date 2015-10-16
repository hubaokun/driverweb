package com.daoshun.guangda.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 教练日程表
 * @author liukn
 * 
 */
@Entity
@Table(name = "t_coach_schedule")
public class CscheduleInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8916584935116919658L;

	// 数据id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "scheduleid", length = 10, nullable = false)
	private int scheduleid;

	// 教练id
	@Column(name = "coachid", length = 10, nullable = false)
	private int coachid;

	// 日期
	@Column(name = "date", length = 20, nullable = false)
	private String date;

	// 时间点:1~24 0表示对全天配置
	@Column(name = "hour", length = 10, nullable = false)
	private String hour;

	// 全天状态，0、当天开课 1、当天休息（只有当hour为0的时候才有效）
	@Column(name = "state", length = 1, nullable = false, columnDefinition = "INT default 0")
	private Integer state = 0;

	// 当天订单是否可以取消 ：0可以取消 1不可以取消 默认节假日不可以取消 （只有hour为0的时候才有效）
	@Column(name = "cancelstate", length = 1, nullable = false, columnDefinition = "INT default 0")
	private Integer cancelstate = 0;

	// 单价(如果没有设置，则以教练的单价来算)
	@Column(name = "price", nullable = false, columnDefinition = "Decimal(20,2) default 0.00")
	private BigDecimal price;

	// 是否休息(0、不休息 1、休息)
	@Column(name = "isrest", nullable = false, columnDefinition = "INT default 0")
	private Integer isrest = 0;

	// 上车地址id
	@Column(name = "addressid", length = 10, nullable = false)
	private int addressid;

	// 科目id
	@Column(name = "subjectid", length = 10, nullable = false)
	private int subjectid;
	
	// 是否过期
	@Column(name = "expire", length = 10,columnDefinition = "INT default 0")
	private int expire;
	
	// 该时间段是否被预约   0=未预约   1=已预约
	@Column(name = "bookstate", length = 10,columnDefinition = "INT default 0")
	private int bookstate;

	// 该时间段是否是体验课   0=不是   1=是
	@Column(name = "isfreecourse", length = 1,columnDefinition = "INT default 0")
	private int isfreecourse;
	
	// 附加价格(用来表示陪驾教练用车价)
	@Column(name = "addtionalprice", nullable = false, columnDefinition = "Decimal(20,2) default 0.00")
	private BigDecimal addtionalprice=new BigDecimal(0);
	
	// 地址详细
	@Transient
	private String addressdetail;

	// 教学科目名称
	@Transient
	private String subject;
	
	@Transient
	private int hasbooked;//是否被预约
	
	@Transient
	private int pasttime;// 时间是不是过去时间1.过去 2.未过去

	@Transient
	private int isbooked;// 是否已经被预约   0   没有  1教练被别人预约 2教练被自己预约 3自己预约了别的教练

	
	@Transient
	private String bookedername;// 预约人的姓名

	@Transient
	private BigDecimal cuseraddtionalprice;//教练用车价格
	
	
	public String getAddressdetail() {
		return addressdetail;
	}

	public void setAddressdetail(String addressdetail) {
		this.addressdetail = addressdetail;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public int getScheduleid() {
		return scheduleid;
	}

	public void setScheduleid(int scheduleid) {
		this.scheduleid = scheduleid;
	}

	public int getCoachid() {
		return coachid;
	}

	public void setCoachid(int coachid) {
		this.coachid = coachid;
	}

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

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getCancelstate() {
		return cancelstate;
	}

	
	public void setCancelstate(Integer cancelstate) {
		this.cancelstate = cancelstate;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getIsrest() {
		return isrest;
	}

	public void setIsrest(Integer isrest) {
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

	public int getIsbooked() {
		return isbooked;
	}

	public void setIsbooked(int isbooked) {
		this.isbooked = isbooked;
	}

	public int getPasttime() {
		return pasttime;
	}

	public void setPasttime(int pasttime) {
		this.pasttime = pasttime;
	}

	
	public int getHasbooked() {
		return hasbooked;
	}
	
	public void setHasbooked(int hasbooked) {
		this.hasbooked = hasbooked;
	}

	public int getExpire() {
		return expire;
	}

	public void setExpire(int expire) {
		this.expire = expire;
	}

	public int getBookstate() {
		return bookstate;
	}

	public void setBookstate(int bookstate) {
		this.bookstate = bookstate;
	}

	public String getBookedername() {
		return bookedername;
	}

	public void setBookedername(String bookedername) {
		this.bookedername = bookedername;
	}

	public BigDecimal getAddtionalprice() {
		return addtionalprice;
	}

	public void setAddtionalprice(BigDecimal addtionalprice) {
		this.addtionalprice = addtionalprice;
	}

	public BigDecimal getCuseraddtionalprice() {
		return cuseraddtionalprice;
	}

	public void setCuseraddtionalprice(BigDecimal cuseraddtionalprice) {
		this.cuseraddtionalprice = cuseraddtionalprice;
	}

	public int getIsfreecourse() {
		return isfreecourse;
	}

	public void setIsfreecourse(int isfreecourse) {
		this.isfreecourse = isfreecourse;
	}

}
