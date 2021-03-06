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

/**
 * 驾校信息表
 * @author liukn
 *
 */
@Entity
@Table(name = "t_drive_school_info")
public class DriveSchoolInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7400068811569305113L;

	// 驾校id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "schoolid", length = 10, nullable = false)
	private int schoolid;

	// 添加时间
	@Column(name = "addtime", nullable = false)
	private Date addtime;

	// 驾校联系方式
	@Column(name = "telphone", length = 25)
	private String telphone;

	// 驾校名称
	@Column(name = "name", nullable = false)
	private String name;

	// 驾校联系人
	@Column(name = "contact", length = 50)
	private String contact;

	// 驾校联系人支付宝账号
	@Column(name = "alipay_account")
	private String alipay_account;

	// 驾校抽成
	@Column(name = "order_pull", length = 10, nullable = false, columnDefinition = "INT default 0")
	private Integer order_pull;

	@Column(name = "money", nullable = false, columnDefinition = "Decimal(20,2) default 0.00")
	private BigDecimal money;
	
	//省
	@Column(name = "provinceid")
	private String provinceid;
	
	//市
	@Column(name = "cityid")
	private String cityid;
	
	//区
	@Column(name = "areaid")
	private String areaid;
	
	//是否签约
	@Column(name = "iscontract",length = 1, nullable = false,columnDefinition = "INT default 0")
	private Integer iscontract;

	public String getProvinceid() {
		return provinceid;
	}

	public void setProvinceid(String provinceid) {
		this.provinceid = provinceid;
	}

	public String getCityid() {
		return cityid;
	}

	public void setCityid(String cityid) {
		this.cityid = cityid;
	}

	public String getAreaid() {
		return areaid;
	}

	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}

	public int getSchoolid() {
		return schoolid;
	}

	public void setSchoolid(int schoolid) {
		this.schoolid = schoolid;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getAlipay_account() {
		return alipay_account;
	}

	public void setAlipay_account(String alipay_account) {
		this.alipay_account = alipay_account;
	}

	public Integer getOrder_pull() {
		return order_pull;
	}

	public void setOrder_pull(Integer order_pull) {
		this.order_pull = order_pull;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public Integer getIscontract() {
		return iscontract;
	}

	public void setIscontract(Integer iscontract) {
		this.iscontract = iscontract;
	}
	
}
