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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "t_recharge_record")
public class RechargeRecordInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	// 充值id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rechargeid", length = 10, nullable = false)
	private Integer rechargeid;

	// 学员id
	@Column(name = "userid", length = 10, nullable = false)
	private Integer userid;

	// 1.教练充值 2.学员充值 3.学员报名  4 管理后台添加学员余额   5 管理后台添加教练余额 
	@Column(name = "type", length = 1, nullable = false, columnDefinition = "INt default 0")
	private Integer type;
	// 0.支付宝 1.微信       ——支付来源
	@Column(name = "paytype", length = 1 , columnDefinition = "INt default 0")
	private Integer paytype;
	// 金额
	@Column(name = "amount", nullable = false, columnDefinition = "Decimal(20,2) default 0.00")
	private BigDecimal amount;

	// 类型(0:充值未完成 1：充值成功)
	@Column(name = "state", length = 10, columnDefinition = "INT default 0")
	private Integer state = 0;

	// 修改时间
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updatetime")
	private Date updatetime;

	// 添加时间
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "addtime", nullable = false)
	private Date addtime;

	@Column(name = "buyer_id", nullable = true, length = 255)
	private String buyer_id;
	
	@Column(name = "openid", nullable = true, length = 255)
	private String openid;
	
	@Column(name = "wxorderid", nullable = true, length = 255)
	private String wxorderid;


	@Column(name = "buyer_email", nullable = true, length = 255)
	private String buyer_email;
    
	
	
	@Transient
	private SuserInfo suser;
	
	public Integer getRechargeid() {
		return rechargeid;
	}

	public void setRechargeid(Integer rechargeid) {
		this.rechargeid = rechargeid;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getBuyer_id() {
		return buyer_id;
	}

	public void setBuyer_id(String buyer_id) {
		this.buyer_id = buyer_id;
	}

	public String getBuyer_email() {
		return buyer_email;
	}

	public void setBuyer_email(String buyer_email) {
		this.buyer_email = buyer_email;
	}

	public SuserInfo getSuser() {
		return suser;
	}

	public void setSuser(SuserInfo suser) {
		this.suser = suser;
	}

	public Integer getPaytype() {
		return paytype;
	}

	public void setPaytype(Integer paytype) {
		this.paytype = paytype;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getWxorderid() {
		return wxorderid;
	}

	public void setWxorderid(String wxorderid) {
		this.wxorderid = wxorderid;
	}

}
