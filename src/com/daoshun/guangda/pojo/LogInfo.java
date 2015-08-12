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
 * 日志表
 * 
 * @author guok
 * 
 */
@Entity
@Table(name = "t_log")
public class LogInfo {
	
	public LogInfo(){
		
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "logid", length = 10, nullable = false)
	private Integer logid;
	
	// 操作者ID
	@Column(name = "operatorid", length = 10, nullable = false, columnDefinition = "INT default 0")
	private Integer operatorid;
	//操作对应表
	@Column(name = "operateform", length = 20)
	private String operateform;
	//被操作用户id
	@Column(name = "opuserid", length = 20)
	private Integer opuserid;
	//具体操作
	@Column(name = "operatecontent")
	private String operatecontent;
	//操作时间
	@Column(name = "operatetime", columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
	private Date operatetime;
	
	@Transient
	private String operatorname;
	
	@Transient
	private String opusername;
	
	
	public Integer getLogid() {
		return logid;
	}
	public void setLogid(Integer logid) {
		this.logid = logid;
	}
	public Integer getOperatorid() {
		return operatorid;
	}
	public void setOperatorid(Integer operatorid) {
		this.operatorid = operatorid;
	}
	public String getOperateform() {
		return operateform;
	}
	public void setOperateform(String operateform) {
		this.operateform = operateform;
	}
	public Integer getOpuserid() {
		return opuserid;
	}
	public void setOpuserid(Integer opuserid) {
		this.opuserid = opuserid;
	}
	public String getOperatecontent() {
		return operatecontent;
	}
	public void setOperatecontent(String operatecontent) {
		this.operatecontent = operatecontent;
	}
	public Date getOperatetime() {
		return operatetime;
	}
	public void setOperatetime(Date operatetime) {
		this.operatetime = operatetime;
	}
	public String getOperatorname() {
		return operatorname;
	}
	public void setOperatorname(String operatorname) {
		this.operatorname = operatorname;
	}
	public String getOpusername() {
		return opusername;
	}
	public void setOpusername(String opusername) {
		this.opusername = opusername;
	}
	

	

}
