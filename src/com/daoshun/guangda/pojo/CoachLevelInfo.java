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
 * 教练等级表
 * @author liukn
 *
 */
@Entity
@Table(name = "t_coach_level")
public class CoachLevelInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 144937217233701639L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="levelid",length=10,nullable=false)
	private int levelid;
	
	@Column(name="levelname",nullable=false,unique=true)
	private String levelname;
	
	@Column(name="addtime",nullable=false)
	private Date addtime;

	
	public int getLevelid() {
		return levelid;
	}

	
	public void setLevelid(int levelid) {
		this.levelid = levelid;
	}

	
	public String getLevelname() {
		return levelname;
	}

	
	public void setLevelname(String levelname) {
		this.levelname = levelname;
	}

	
	public Date getAddtime() {
		return addtime;
	}

	
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	
}
