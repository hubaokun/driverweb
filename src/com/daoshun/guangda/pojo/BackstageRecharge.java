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
/**
 * 管理员后台对学员或教练的余额增加或减少
 * @author 卢磊
 *
 */
@Entity
@Table(name = "t_backstage_recharge")
public class BackstageRecharge implements Serializable {

	private static final long serialVersionUID = 1L;

	// 充值id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rechargeid", length = 10, nullable = false)
	private Integer rechargeid;

	// 学员id
	@Column(name = "userid", length = 10, nullable = false)
	private Integer userid;

	// 1.学员 2.教练
	@Column(name = "usertype", length = 1, nullable = false, columnDefinition = "INt default 0")
	private Integer usertype;
	// 1.增加 2.减少
	@Column(name = "operatetype", length = 1 , columnDefinition = "INt default 0")
	private Integer operatetype;
	// 金额
	@Column(name = "amount", nullable = false, columnDefinition = "Decimal(20,2) default 0.00")
	private BigDecimal amount;

	// 添加时间
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "addtime", nullable = false)
	private Date addtime;
	
	//原因：为什么要添加余额或减少余额
	@Column(name = "reason", length = 100, nullable = true)
	private String reason="";
	
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

	public Integer getUsertype() {
		return usertype;
	}

	public void setUsertype(Integer usertype) {
		this.usertype = usertype;
	}

	public Integer getOperatetype() {
		return operatetype;
	}

	public void setOperatetype(Integer operatetype) {
		this.operatetype = operatetype;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
}
