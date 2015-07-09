package com.daoshun.guangda.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_vcode_login")
public class VerifyCodeInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 验证码id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "codeid", length = 10, nullable = false)
	private int codeid;

	// 验证码内容
	@Column(name = "code", length = 10, nullable = false)
	private String code;

	// 发送对象： 1、教练 2、学员
	@Column(name = "totype", length = 1, nullable = false)
	private Integer totype;

	// 教练或学生id
	@Column(name = "phone", length = 11, nullable = false)
	private String phone;

	// 添加时间
	@Column(name = "addtime", nullable = false)
	private Date addtime;

	public int getCodeid() {
		return codeid;
	}

	public void setCodeid(int codeid) {
		this.codeid = codeid;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getTotype() {
		return totype;
	}

	public void setTotype(Integer totype) {
		this.totype = totype;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
}
