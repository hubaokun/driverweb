package com.daoshun.guangda.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_default_schedule")
public class DefaultSchedule implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "scheduleid", length = 20, nullable = false)
	private Integer scheduleid;

	@Column(name = "coachid", length = 20, nullable = false)
	private Integer coachid;

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

	@Column(name = "updatetime", nullable = false)
	private Date updatetime;

	public Integer getScheduleid() {
		return scheduleid;
	}

	public void setScheduleid(Integer scheduleid) {
		this.scheduleid = scheduleid;
	}

	public Integer getCoachid() {
		return coachid;
	}

	public void setCoachid(Integer coachid) {
		this.coachid = coachid;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
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

}
