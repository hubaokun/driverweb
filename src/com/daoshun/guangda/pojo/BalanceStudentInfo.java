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
@Table(name = "t_balance_student")
public class BalanceStudentInfo implements Serializable {
	
	private static final long serialVersionUID = -1202207835234140893L;
	
	//记录Id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "recordid", length=10, nullable = false)
	private int recordid;
	
	//学员Id
	@Column(name = "userid",length=10,nullable = false)
	private Integer userid;
	
	//金额
	@Column(name = "amount", nullable = false, columnDefinition = "Decimal(20,2) default 0.00")
	private BigDecimal amount;
	
	//类型(1:支付宝充值  2：提现  3：订单支付)
	@Column(name = "type",length=10,columnDefinition = "INT default 0")
	private Integer type=0;
	
	//记录添加时间
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "addtime", nullable = false)
	private Date addtime;
	
	// 真实姓名
	@Transient
	private String realname;
	
	// 电话
	@Transient
	private String phone;
	
	// 充值账号
	@Transient
	private String buyer_email;
	
	// 支付宝
	@Transient
	private String alipay_account;

	public int getRecordid() {
		return recordid;
	}

	public void setRecordid(int recordid) {
		this.recordid = recordid;
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getBuyer_email() {
		return buyer_email;
	}

	public void setBuyer_email(String buyer_email) {
		this.buyer_email = buyer_email;
	}
	

	public String getAlipay_account() {
		return alipay_account;
	}

	public void setAlipay_account(String alipay_account) {
		this.alipay_account = alipay_account;
	}
	

}
