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

/**
 * 教练申请提现表
 * @author liukn
 * 
 */
@Entity
@Table(name = "t_coach_apply")
public class CApplyCashInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6577593111364024092L;

	// 申请id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "applyid", length = 10, nullable = false)
	private int applyid;

	// 教练id
	@Column(name = "coachid", length = 10, nullable = false)
	private int coachid;

	// 申请金额
	@Column(name = "amount", nullable = false, columnDefinition = "Decimal(20,2) default 0.00")
	private BigDecimal amount;

	// 申请类型(0:申请中；1：申请通过 2:驾校通过3：审核不通过)
	@Column(name = "state", length = 1, nullable = false, columnDefinition = "INT default 0")
	private Integer state;

	// 更新时间
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updatetime", nullable = false)
	private Date updatetime;

	// 记录添加时间
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "addtime", nullable = false)
	private Date addtime;

	@Column(name = "schoolid", length = 10, nullable = false, columnDefinition = "INT default 0")
	private Integer schoolid;

	// 真实姓名
	@Transient
	private CuserInfo coach;
	
	public int getApplyid() {
		return applyid;
	}

	public void setApplyid(int applyid) {
		this.applyid = applyid;
	}

	public int getCoachid() {
		return coachid;
	}

	public void setCoachid(int coachid) {
		this.coachid = coachid;
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

	public CuserInfo getCoach() {
		return coach;
	}

	public void setCoach(CuserInfo coach) {
		this.coach = coach;
	}

	public Integer getSchoolid() {
		return schoolid;
	}

	public void setSchoolid(Integer schoolid) {
		this.schoolid = schoolid;
	}

}
