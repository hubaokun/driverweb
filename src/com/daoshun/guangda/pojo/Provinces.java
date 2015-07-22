package com.daoshun.guangda.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 省 信息
 * @author 卢磊
 *
 */
@Entity
@Table(name = "provinces")
public class Provinces {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", length = 20, nullable = false)
	private Integer id;
	
	@Column(name = "provinceid", length =20, nullable = false)
	private Integer provinceid;
	
	@Column(name = "province", length = 100, nullable = false)
	private String province;
	
	@Column(name = "hotkey", length = 100, nullable = false)
	private String hotkey;

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
