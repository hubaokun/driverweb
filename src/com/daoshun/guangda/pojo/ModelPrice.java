package com.daoshun.guangda.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 报名时不同城市车型价格套餐
 * @author 卢磊
 *
 */
@Entity
@Table(name = "t_model_price")
public class ModelPrice {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", length = 20, nullable = false)
	private Integer id;
	
	//C1小巴价格
	@Column(name = "c1xiaobaprice", length = 10, columnDefinition = "INT default 0")
	private Integer c1xiaobaprice;
	//C1市场价格
	@Column(name = "c1marketprice", length = 10, columnDefinition = "INT default 0")
	private Integer c1marketprice;
	
	//C2小巴价格
	@Column(name = "c2xiaobaprice", length = 10, columnDefinition = "INT default 0")
	private Integer c2xiaobaprice;
	//C2市场价格
	@Column(name = "c2marketprice", length = 10, columnDefinition = "INT default 0")
	private Integer c2marketprice;
	
	//城市ID
	@Column(name = "cityid", length = 10, columnDefinition = "INT default 0")
	private Integer cityid;
	//城市名称
	@Column(name = "cityname")
	private String cityname;
	
	
	public String getCityname() {
		return cityname;
	}

	public void setCityname(String cityname) {
		this.cityname = cityname;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getC1xiaobaprice() {
		return c1xiaobaprice;
	}

	public void setC1xiaobaprice(Integer c1xiaobaprice) {
		this.c1xiaobaprice = c1xiaobaprice;
	}

	public Integer getC1marketprice() {
		return c1marketprice;
	}

	public void setC1marketprice(Integer c1marketprice) {
		this.c1marketprice = c1marketprice;
	}

	public Integer getC2xiaobaprice() {
		return c2xiaobaprice;
	}

	public void setC2xiaobaprice(Integer c2xiaobaprice) {
		this.c2xiaobaprice = c2xiaobaprice;
	}

	public Integer getC2marketprice() {
		return c2marketprice;
	}

	public void setC2marketprice(Integer c2marketprice) {
		this.c2marketprice = c2marketprice;
	}

	public Integer getCityid() {
		return cityid;
	}

	public void setCityid(Integer cityid) {
		this.cityid = cityid;
	}
	
}
