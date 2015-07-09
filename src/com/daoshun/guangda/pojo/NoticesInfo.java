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
 * 通知信息表
 * @author liukn
 *
 */
@Entity
@Table(name = "t_notice")
public class NoticesInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8848802895605455865L;
	
	//通知id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "noticeid", length=10, nullable = false)
	private int noticeid;
	
	//通知类型 如 审核结果、信息验证
	@Column(name = "category", length=50)
	private String category;
	
	//通知内容
	@Column(name = "content", length=1000,nullable = false)
	private String content;
	
	//添加时间
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "addtime",nullable = false)
	private Date addtime;
	
	//通知目标用户 1 教练  2学员
	@Column(name = "type", length=1,nullable = false,columnDefinition = "INT default 0")
	private Integer type=0;
	
	// 是否已读
	@Transient
	private Integer readstate;
	
	//目标用户姓名
	@Transient
	private String realname;
	
	public int getNoticeid() {
		return noticeid;
	}

	
	public void setNoticeid(int noticeid) {
		this.noticeid = noticeid;
	}

	
	public String getCategory() {
		return category;
	}

	
	public void setCategory(String category) {
		this.category = category;
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

	
	public Integer getType() {
		return type;
	}

	
	public void setType(Integer type) {
		this.type = type;
	}


	public Integer getReadstate() {
		return readstate;
	}


	public void setReadstate(Integer readstate) {
		this.readstate = readstate;
	}


	
	public String getRealname() {
		return realname;
	}


	
	public void setRealname(String realname) {
		this.realname = realname;
	}
	
	
}
