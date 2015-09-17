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
@Table(name = "t_student_apply")
public class StudentApplyInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	//申请id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "applyid", length=10, nullable = false)
	private Integer applyid;
	
	//学员id
	@Column(name = "userid",length=10, nullable = false)
	private Integer userid;
	
	//金额
	@Column(name = "amount", nullable = false, columnDefinition = "Decimal(20,2) default 0.00")
	private BigDecimal amount;
	
	//类型(0:正在申请中  1：申请通过 2：申请不通过3:作废)
	@Column(name = "state",length=10,columnDefinition = "INT default 0")
	private Integer state=0;
	
	//来源(0:支付宝 1：微信钱包)
	@Column(name = "resource",length=10,columnDefinition = "INT default 0")
	private Integer resource=0;
	
	//修改时间
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updatetime")
	private Date updatetime;
	
	//添加时间
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "addtime")
	private Date addtime;
	
	// 真实姓名
	@Transient
	private String realname;
	
	// 电话
	@Transient
	private String phone;
	
	@Transient
	private String alipay_account;
	
	@Transient
	private String weixin_account;
	
	
	public Integer getApplyid() {
		return applyid;
	}

	public void setApplyid(Integer applyid) {
		this.applyid = applyid;
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

	public String getAlipay_account() {
		return alipay_account;
	}

	public void setAlipay_account(String alipay_account) {
		this.alipay_account = alipay_account;
	}

	public Integer getResource() {
		return resource;
	}

	public void setResource(Integer resource) {
		this.resource = resource;
	}

	public String getWeixin_account() {
		return weixin_account;
	}

	public void setWeixin_account(String weixin_account) {
		this.weixin_account = weixin_account;
	}
	
	

}
