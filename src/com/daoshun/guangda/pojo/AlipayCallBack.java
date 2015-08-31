package com.daoshun.guangda.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_alipay_callback")
public class AlipayCallBack {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", length = 20, nullable = false)
	private Integer id;
	
	@Column(name = "qs", length = 100, nullable = false)
	private String qs;
	@Column(name = "ru", length = 100, nullable = false)
	private String ru;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getQs() {
		return qs;
	}
	public void setQs(String qs) {
		this.qs = qs;
	}
	public String getRu() {
		return ru;
	}
	public void setRu(String ru) {
		this.ru = ru;
	}
	
	
}
