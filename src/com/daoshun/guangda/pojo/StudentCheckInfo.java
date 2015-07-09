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
@Table(name = "t_student_chek_record")
public class StudentCheckInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2893263745168665218L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "recordid", length=10, nullable = false)
	private int recordid;
	
	@Column(name = "coachid", length=10, nullable = false)
	private int coachid;
	
	@Column(name = "studentid", length=10, nullable = false)
	private int studentid;
	
	@Column(name = "addtime", nullable = false)
	private Date addtime;

	
	public int getRecordid() {
		return recordid;
	}

	
	public void setRecordid(int recordid) {
		this.recordid = recordid;
	}

	
	public int getCoachid() {
		return coachid;
	}

	
	public void setCoachid(int coachid) {
		this.coachid = coachid;
	}

	
	public int getStudentid() {
		return studentid;
	}

	
	public void setStudentid(int studentid) {
		this.studentid = studentid;
	}

	
	public Date getAddtime() {
		return addtime;
	}

	
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	
}
