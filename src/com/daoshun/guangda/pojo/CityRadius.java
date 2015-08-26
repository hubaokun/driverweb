package com.daoshun.guangda.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 地图附近教练查询时的搜索半径值，每个城市大小不一，所以需要设置不同的搜索半径
 * @author 卢磊
 *
 */
@Entity
@Table(name = "t_city_radius")
public class CityRadius {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", length = 11, nullable = false)
	private Integer id;
	
	@Column(name = "radius", nullable = false, columnDefinition = "Decimal(20,2) default 0.00")
    private double radius;//半径
	
	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	@Column(name="cityid",length=11,nullable=false)
	private int cityid;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	

	public int getCityid() {
		return cityid;
	}

	public void setCityid(int cityid) {
		this.cityid = cityid;
	}
	
}
