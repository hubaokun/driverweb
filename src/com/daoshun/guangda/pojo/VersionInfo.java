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

/**
 * 版本信息表
 * @author liukn
 *
 */
@Entity
@Table(name = "t_version")
public class VersionInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5493186155203386828L;

	// 版本id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "versionid", length = 10, nullable = false)
	private int versionid;

	// 版本号
	@Column(name = "versioncode", length = 10, nullable = false)
	private int versioncode;

	// 版本名称
	@Column(name = "versionname", length = 50, nullable = false)
	private String versionname;

	// 类型
	@Column(name = "type", length = 1, nullable = false, columnDefinition = "INT default 0")
	private Integer type = 0;

	// 版本类型
	@Column(name = "category", length = 1, nullable = false, columnDefinition = "INT default 0")
	private Integer category = 0;

	// 版本状态 0 未发布 1发布
	@Column(name = "state", length = 1, nullable = false, columnDefinition = "INT default 0")
	private Integer state = 0;

	// 添加时间
	@Column(name = "addtime")
	private Date addtime;

	// app客户端类型 1 教练端 2学员端
	@Column(name = "apptype", length = 1, nullable = false, columnDefinition = "INT default 0")
	private Integer apptype = 0;

	// 下载地址
	@Column(name = "download", length = 500, nullable = false)
	private String download;

	public int getVersionid() {
		return versionid;
	}

	public void setVersionid(int versionid) {
		this.versionid = versionid;
	}

	public int getVersioncode() {
		return versioncode;
	}

	public void setVersioncode(int versioncode) {
		this.versioncode = versioncode;
	}

	public String getVersionname() {
		return versionname;
	}

	public void setVersionname(String versionname) {
		this.versionname = versionname;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getAddtime() {
		return addtime;
	}


	public Integer getApptype() {
		return apptype;
	}

	public void setApptype(Integer apptype) {
		this.apptype = apptype;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public String getDownload() {
		return download;
	}

	public void setDownload(String download) {
		this.download = download;
	}
}
