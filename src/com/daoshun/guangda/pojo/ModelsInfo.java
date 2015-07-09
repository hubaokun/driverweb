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

/**
 * 准教车型信息表
 * @author liukn
 *
 */
@Entity
@Table(name = "t_car_models")
public class ModelsInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4291596969136283982L;

	// 准教车型id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "modelid", length = 10, nullable = false)
	private int modelid;

	// 准教车型名车
	@Column(name = "modelname", nullable = false)
	private String modelname;

	@Column(name = "searchname", nullable = false)
	private String searchname;

	// 添加时间
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "addtime")
	private Date addtime;

	public int getModelid() {
		return modelid;
	}

	public void setModelid(int modelid) {
		this.modelid = modelid;
	}

	public String getModelname() {
		return modelname;
	}

	public void setModelname(String modelname) {
		this.modelname = modelname;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public String getSearchname() {
		return searchname;
	}

	public void setSearchname(String searchname) {
		this.searchname = searchname;
	}

}
