package com.daoshun.guangda.pojo;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
/**
 * 省 信息
 * @author 卢磊
 *
 */
@Entity
@Table(name = "t_provinces")
public class ProvinceInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", length = 20, nullable = false)
	private Integer id;
	
	@Column(name = "provinceid", length =20, nullable = false)
	private Integer provinceid;
	
	@Column(name = "baiduid", length =11, nullable = true)
	private Integer baiduid;
	
	@Column(name = "province", length = 100, nullable = false)
	private String province;
	
	@Column(name = "hotkey", length = 100, nullable = false)
	private String hotkey;
	
	@Transient
	List<CityInfo> cities;
	
	
	
	public Integer getBaiduid() {
		return baiduid;
	}

	public void setBaiduid(Integer baiduid) {
		this.baiduid = baiduid;
	}

	public List<CityInfo> getCities() {
		return cities;
	}

	public void setCities(List<CityInfo> cities) {
		this.cities = cities;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getProvinceid() {
		return provinceid;
	}

	public void setProvinceid(Integer provinceid) {
		this.provinceid = provinceid;
	}

	

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getHotkey() {
		return hotkey;
	}

	public void setHotkey(String hotkey) {
		this.hotkey = hotkey;
	}
	
	
}
