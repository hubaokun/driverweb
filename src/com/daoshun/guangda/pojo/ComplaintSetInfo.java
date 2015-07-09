package com.daoshun.guangda.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 投诉原因设置表
 * @author liukn
 *
 */
@Entity
@Table(name ="t_complaintset")
public class ComplaintSetInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6176903449912228735L;
	
	//投诉原因设置id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "setid", length=10, nullable = false)
	private int setid;
	
	//原因类型
	@Column(name = "type", length=1, nullable = false,columnDefinition = "INT default 0")
	private Integer type=0;
	
	//原因内容
	@Column(name = "content", nullable = false)
	private String content;
	
	//添加时间
	@Column(name = "addtime", length=10, nullable = false)
	private Date addtime;

	
	public int getSetid() {
		return setid;
	}

	
	public void setSetid(int setid) {
		this.setid = setid;
	}

	
	public Integer getType() {
		return type;
	}

	
	public void setType(Integer type) {
		this.type = type;
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
