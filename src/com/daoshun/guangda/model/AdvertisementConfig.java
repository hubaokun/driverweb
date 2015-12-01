package com.daoshun.guangda.model;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;

public class AdvertisementConfig {
	
	// 广告图片子表ID
	private int adimgid;

	// 广告主键ID
	private int adid;

	// 广告位置
	private int position;

	// 广告标题
	private String title;

	// 广告类型： 0=图片，1=文字
	private int adtype;

	// 广告内容
	private String content;

	// 广告点击打开类型
	private int opentype;

	// 内部action打开位置
	private int openaction;

	// 打开URL地址
	private String openurl;

	// 广告开始时间
	private Timestamp begintime;

	// 广告开始时间
	private Timestamp endtime;

	// 广告图片url
	private String imgurl;

	// 图片高
	private int height;

	// 图片宽
	private int width;

	// 设备类型
	private int devicetype;

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

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getAdtype() {
		return adtype;
	}

	public void setAdtype(int adtype) {
		this.adtype = adtype;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getOpentype() {
		return opentype;
	}

	public void setOpentype(int opentype) {
		this.opentype = opentype;
	}

	public int getOpenaction() {
		return openaction;
	}

	public void setOpenaction(int openaction) {
		this.openaction = openaction;
	}

	public String getOpenurl() {
		return openurl;
	}

	public void setOpenurl(String openurl) {
		this.openurl = openurl;
	}

	public Timestamp getBegintime() {
		return begintime;
	}

	public void setBegintime(Timestamp begintime) {
		this.begintime = begintime;
	}

	public Timestamp getEndtime() {
		return endtime;
	}

	public void setEndtime(Timestamp endtime) {
		this.endtime = endtime;
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
}
