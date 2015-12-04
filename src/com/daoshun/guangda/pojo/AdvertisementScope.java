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
@Table(name = "t_advertisement_scope")
public class AdvertisementScope implements Serializable {

	private static final long serialVersionUID = 7553687481983775054L;

	// 广告目标子表ID
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "adscopeid", length = 10, nullable = false)
	private int adscopeid;

	// 广告主键ID
	@Column(name = "adid", length = 10, nullable = false)
	private int adid;

	// 目标类型 0:城市，1：驾校，2：教练
	@Column(name = "scopetype", length = 1, nullable = false, columnDefinition = "INT default 0")
	private int scopetype;

	// 城市ID
	@Column(name = "cityid", length = 6, nullable = false)
	private int cityid;

	// 城市名称
	@Column(name = "cityname", length = 20, nullable = false)
	private String cityname;

	// 驾校ID
	@Column(name = "driverschoolid", length = 10)
	private int driverschoolid;

	// 驾校名称
	@Column(name = "driverschoolname", length = 100)
	private String driverschoolname;

	// 驾校ID
	@Column(name = "coachid", length = 10)
	private int coachid;

	// 驾校名称
	@Column(name = "coachname", length = 50)
	private String coachname;

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

	public int getAdscopeid() {
		return adscopeid;
	}

	public void setAdscopeid(int adscopeid) {
		this.adscopeid = adscopeid;
	}

	public int getAdid() {
		return adid;
	}

	public void setAdid(int adid) {
		this.adid = adid;
	}

	public int getScopetype() {
		return scopetype;
	}

	public void setScopetype(int scopetype) {
		this.scopetype = scopetype;
	}

	public int getCityid() {
		return cityid;
	}

	public void setCityid(int cityid) {
		this.cityid = cityid;
	}

	public String getCityname() {
		return cityname;
	}

	public void setCityname(String cityname) {
		this.cityname = cityname;
	}

	public int getDriverschoolid() {
		return driverschoolid;
	}

	public void setDriverschoolid(int driverschoolid) {
		this.driverschoolid = driverschoolid;
	}

	public String getDriverschoolname() {
		return driverschoolname;
	}

	public void setDriverschoolname(String driverschoolname) {
		this.driverschoolname = driverschoolname;
	}

	public int getCoachid() {
		return coachid;
	}

	public void setCoachid(int coachid) {
		this.coachid = coachid;
	}

	public String getCoachname() {
		return coachname;
	}

	public void setCoachname(String coachname) {
		this.coachname = coachname;
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
