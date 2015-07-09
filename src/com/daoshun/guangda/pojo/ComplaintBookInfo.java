package com.daoshun.guangda.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "t_complaint_book")
public class ComplaintBookInfo implements Serializable{
	
	private static final long serialVersionUID = 888110791973506323L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="bookid",length=10,nullable=false)
	private int bookid;
	
	//关联投诉id
	@Column(name = "complaintid", nullable = false)
	private int complaintid;
	
	//备注时间
	@Column(name = "time", nullable = false)
	private Date time;
	
	//备注内容
	@Column(name = "content", nullable = false)
	private String content;
	
	//添加时间
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "addtime")
	private Date addtime;

	public int getBookid() {
		return bookid;
	}

	public void setBookid(int bookid) {
		this.bookid = bookid;
	}

	public int getComplaintid() {
		return complaintid;
	}

	public void setComplaintid(int complaintid) {
		this.complaintid = complaintid;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

}
