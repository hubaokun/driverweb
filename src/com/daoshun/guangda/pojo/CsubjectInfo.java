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
 * 科目表
 * @author liukn
 *
 */
@Entity
@Table(name = "t_teach_subject")
public class CsubjectInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6340302862427428453L;
	//科目id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "subjectid", length = 10, nullable = false)
	private int subjectid;
	
	
	//科目名称
	@Column(name = "subjectname", nullable = false)
	private String subjectname;
	
	//添加时间
	@Column(name = "addtime", nullable = false)
	private Date addtime;
	
	//是否为默认教学科目
	@Transient
	private int isdefault;
	
	public int getSubjectid() {
		return subjectid;
	}

	
	public void setSubjectid(int subjectid) {
		this.subjectid = subjectid;
	}

	
	public String getSubjectname() {
		return subjectname;
	}

	
	public void setSubjectname(String subjectname) {
		this.subjectname = subjectname;
	}

	
	public Date getAddtime() {
		return addtime;
	}

	
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}


	
	public int getIsdefault() {
		return isdefault;
	}


	
	public void setIsdefault(int isdefault) {
		this.isdefault = isdefault;
	}
	
	
}
