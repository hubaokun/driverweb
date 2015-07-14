package com.daoshun.guangda.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 评论表
 * @author pigger
 * 
 */
@Entity
@Table(name = "t_user_state_log")
public class UserStateLogInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 评价ID
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "logid", length = 10, nullable = false)
	private int logid;


	// 执行操作的用户
	@Column(name = "op_userid", length = 10, nullable = false)
	private int op_user;

	// 被执行操作的用户
	@Column(name = "on_userid", length = 10, nullable = false)
	private int on_user;
	
	// 用户类型(1.教练,2.学员,3.管理员)
	@Column(name = "type", length = 1, nullable = false, columnDefinition = "INt default 0")
	private Integer type = 0;



	public int getLogid() {
		return logid;
	}

	public void setLogid(int logid) {
		this.logid = logid;
	}

	public int getOp_user() {
		return op_user;
	}

	public void setOp_user(int op_user) {
		this.op_user = op_user;
	}

	public int getOn_user() {
		return on_user;
	}

	public void setOn_user(int on_user) {
		this.on_user = on_user;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getOptime() {
		return optime;
	}

	public void setOptime(Date optime) {
		this.optime = optime;
	}

	// 备注
	@Column(name = "comment", length = 1000, nullable = true)
	private String comment;

	// 添加时间
	@Column(name = "optime", nullable = false)
	private Date optime;

}
