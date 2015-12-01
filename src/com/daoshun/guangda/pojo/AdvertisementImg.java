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
 * 广告图片子表
 * 
 * @author 张聚弘
 *
 */
@Entity
@Table(name = "t_advertisement_img")
public class AdvertisementImg implements Serializable {

	private static final long serialVersionUID = 4719671221921174743L;

	// 广告图片子表ID
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "adimgid", length = 10, nullable = false)
	private int adimgid;

	// 广告主键ID
	@Column(name = "adid", length = 10, nullable = false)
	private int adid;

	// 广告图片url
	@Column(name = "imgurl", length = 500, nullable = false)
	private String imgurl;

	// 图片高
	@Column(name = "height", length = 4, nullable = false, columnDefinition = "INT default 0")
	private int height;

	// 图片宽
	@Column(name = "width", length = 4, nullable = false, columnDefinition = "INT default 0")
	private int width;

	// 设备类型 0=IOS，1=Android
	@Column(name = "devicetype", length = 1,  columnDefinition = "INT default 0")
	private int devicetype;

	// 修改时间
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modifytime", nullable = false)
	private Date modifytime;

	// 修改人ID
	@Column(name = "modifyuserid", length = 10, nullable = false)
	private int modifyuserid;

	// 修改人姓名
	@Column(name = "modifyusername", length = 20)
	private String modifyusername;

	public int getAdimgid() {
		return adimgid;
	}

	public void setAdimgid(int adimgid) {
		this.adimgid = adimgid;
	}

	public int getAdid() {
		return adid;
	}

	public void setAdid(int adid) {
		this.adid = adid;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getDevicetype() {
		return devicetype;
	}

	public void setDevicetype(int devicetype) {
		this.devicetype = devicetype;
	}

	public Date getModifytime() {
		return modifytime;
	}

	public void setModifytime(Date modifytime) {
		this.modifytime = modifytime;
	}

	public int getModifyuserid() {
		return modifyuserid;
	}

	public void setModifyuserid(int modifyuserid) {
		this.modifyuserid = modifyuserid;
	}

	public String getModifyusername() {
		return modifyusername;
	}

	public void setModifyusername(String modifyusername) {
		this.modifyusername = modifyusername;
	}

}
