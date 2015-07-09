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
import javax.persistence.Transient;

/**
 * 反馈表
 * @author liukn
 *
 */
@Entity
@Table(name ="t_feedback")
public class FeedBackInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	//通知id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "feedbackid", length=10, nullable = false)
	private int feedbackid;
	
	//提交反馈的用户id
	@Column(name = "fromid")
	private Integer fromid;
	
	//提交反馈的用户类型(1:教练 2:学员)
	@Column(name = "from_type", length=1, nullable = false, columnDefinition = "INT default 0")
	private int from_type;
	
	//反馈内容
	@Column(name = "content")
	private String content;
	
	//添加时间
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "addtime",nullable = false)
	private Date addtime;
	
	//提交者的真是姓名
	@Transient
	private String fromrealname;
	
	
	//提交者的手机号码
	@Transient
	private String fromphone;
	
	
	
	public int getFeedbackid() {
		return feedbackid;
	}

	public void setFeedbackid(int feedbackid) {
		this.feedbackid = feedbackid;
	}

	public Integer getFromid() {
		return fromid;
	}

	public void setFromid(Integer fromid) {
		this.fromid = fromid;
	}

	public int getFrom_type() {
		return from_type;
	}

	public void setFrom_type(int from_type) {
		this.from_type = from_type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	
	public String getFromrealname() {
		return fromrealname;
	}

	
	public void setFromrealname(String fromrealname) {
		this.fromrealname = fromrealname;
	}

	
	public String getFromphone() {
		return fromphone;
	}

	
	public void setFromphone(String fromphone) {
		this.fromphone = fromphone;
	}

	
	public Date getAddtime() {
		return addtime;
	}

	
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	

}
