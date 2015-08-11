package com.daoshun.guangda.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *  广告表
 * @author wjr
 *
 */
@Entity
@Table(name = "t_advertisement")
public class AdvertisementInfo implements Serializable {
 
   /**
	 * 
	 */
	private static final long serialVersionUID = 2778524094565693169L;
	//广告主键ID
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ad_id", length = 10, nullable = false)
	private int adid;
	
	//广告标题
	@Column(name = "ad_title")
	private String adtitle;
	
	//广告内容
	@Column(name = "ad_content")
	private String adcontent;
	
	//广告图片
	@Column(name = "ad_img", nullable = false)
	private String adimg;
	
	//广告url
	@Column(name = "ad_url", nullable = false)
	private String adurl;
	
	
	
	
	public int getAdid() {
		return adid;
	}
	public void setAdid(int adid) {
		this.adid = adid;
	}
	public String getAdtitle() {
		return adtitle;
	}
	public void setAdtitle(String adtitle) {
		this.adtitle = adtitle;
	}
	public String getAdcontent() {
		return adcontent;
	}
	public void setAdcontent(String adcontent) {
		this.adcontent = adcontent;
	}
	public String getAdimg() {
		return adimg;
	}
	public void setAdimg(String adimg) {
		this.adimg = adimg;
	}
	public String getAdurl() {
		return adurl;
	}
	public void setAdurl(String adurl) {
		this.adurl = adurl;
	}
	
	
}
