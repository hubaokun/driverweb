package com.daoshun.guangda.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 市 信息
 * @author 卢磊
 *
 */
@Entity
@Table(name = "cities")
public class Cities {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", length = 20, nullable = false)
	private Integer id;
	
	@Column(name = "cityid",  nullable = false)
	private Integer cityid;
	
	@Column(name = "city", length = 100, nullable = false)
	private String city;
	
	@Column(name = "provinceid",  nullable = false)
	private Integer provinceid;
	
	@Column(name = "hotkey", length = 100, nullable = false)
	private String hotkey;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCityid() {
		return cityid;
	}

	public void setCityid(Integer cityid) {
		this.cityid = cityid;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getProvinceid() {
		return provinceid;
	}

	public void setProvinceid(Integer provinceid) {
		this.provinceid = provinceid;
	}

	public String getHotkey() {
		return hotkey;
	}

	public void setHotkey(String hotkey) {
		this.hotkey = hotkey;
	}

	
	
	
}
