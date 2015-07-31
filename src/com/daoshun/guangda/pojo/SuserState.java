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
@Table(name = "t_user_state")
public class SuserState implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "stateid")
	private int stateid;
	
	//��ӦѧԱid
	
	@Column(name = "studentid", length = 10, nullable = false)
	private int studentid;

	// ������id
	@Column(name = "dealpeopleid")
	private int dealpeopleid;

	// ����ʱ��
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dealtime", nullable = false)
	private Date dealtime;
	
	// ��������
	@Column(name = "content")
	private String content;
	
	// ������
	@Transient
	private String dealpeople;
	
	// ѧԱ����
	@Transient
	private String studentname;
	
	public SuserState(){
		
	}
	

	public int getStateid() {
		return stateid;
	}

	public void setStateid(int stateid) {
		this.stateid = stateid;
	}
	

	public int getStudentid() {
		return studentid;
	}

	public void setStudentid(int studentid) {
		this.studentid = studentid;
	}


	public int getDealpeopleid() {
		return dealpeopleid;
	}

	public void setDealpeopleid(int dealpeopleid) {
		this.dealpeopleid = dealpeopleid;
	}

	public Date getDealtime() {
		return dealtime;
	}

	public void setDealtime(Date dealtime) {
		this.dealtime = dealtime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}


	public String getDealpeople() {
		return dealpeople;
	}


	public void setDealpeople(String dealpeople) {
		this.dealpeople = dealpeople;
	}


	public String getStudentname() {
		return studentname;
	}


	public void setStudentname(String studentname) {
		this.studentname = studentname;
	}

	
	
	

}
