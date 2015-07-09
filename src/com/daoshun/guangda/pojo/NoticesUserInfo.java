package com.daoshun.guangda.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 通知目标信息表
 * @author liukn
 *
 */
@Entity
@Table(name = "t_notice_user")
public class NoticesUserInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3237516205533936892L;
	
	//id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", length=10,nullable=false)
	private int id;
	
	//id
	@Column(name = "noticeid", length=10, nullable = false)
	private int noticeid;
	
	//用户id
	@Column(name = "userid", length=10, nullable = false)
	private int userid;
	
	//已读、未读
	@Column(name = "readstate", length=1, columnDefinition = "INT default 0")
	private Integer readstate=0;

	
	public int getNoticeid() {
		return noticeid;
	}

	
	public void setNoticeid(int noticeid) {
		this.noticeid = noticeid;
	}

	
	public int getUserid() {
		return userid;
	}

	
	public void setUserid(int userid) {
		this.userid = userid;
	}

	
	public Integer getReadstate() {
		return readstate;
	}

	
	public void setReadstate(Integer readstate) {
		this.readstate = readstate;
	}
	
	
}
