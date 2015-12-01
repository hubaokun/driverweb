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
 * 广告表
 * 
 * @author wjr
 *
 */
@Entity
@Table(name = "t_advertisement")
public class AdvertisementInfo implements Serializable {

	private static final long serialVersionUID = 2778524094565693169L;
	// 广告主键ID
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "adid", length = 10, nullable = false)
	private int adid;

	// 广告标题
	@Column(name = "title", length = 20, nullable = false)
	private String title;

	// 广告类型： 0=图片，1=文字
	@Column(name = "adtype", length = 1, nullable = false, columnDefinition = "INT default 0")
	private int adtype;

	// 广告内容
	@Column(name = "content", length = 20)
	private String content;

	// 广告图片url
	@Column(name = "defaultimgurl", length = 500, nullable = false)
	private String defaultimgurl;

	// 广告点击打开类型 0=无跳转，1=打开URL，2=内部action
	@Column(name = "opentype", length = 1, nullable = false, columnDefinition = "INT default 0")
	private int opentype;

	// 广告位置 0=学员端闪屏，1=学员端学车地图弹层广告，2=学员端教练详情，3=教练端闪屏，4=教练端首页弹层广告
	@Column(name = "position", length = 2, nullable = false, columnDefinition = "INT default 0")
	private int position;

	// 内部action打开位置  1=学员端学车地图首页，2= 学员端陪驾地图首页 ，3=学员端小巴商城，4=学员端题库页，5=教练端邀请朋友加入页 ，6=教练端教练开课页
	@Column(name = "openaction", length = 2)
	private int openaction;

	// 打开URL地址
	@Column(name = "openurl", length = 500)
	private String openurl;

	// 广告开始时间
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "begintime", nullable = false)
	private Date begintime;

	// 广告开始时间
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "endtime", nullable = false)
	private Date endtime;

	// 排序数值
	@Column(name = "ordervalue", length = 3, columnDefinition = "INT default 0")
	private int ordervalue;

	// 是否取消
	@Column(name = "iscanceled", length = 1, nullable = false, columnDefinition = "INT default 0")
	private int iscanceled;

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

	public int getAdid() {
		return adid;
	}

	public void setAdid(int adid) {
		this.adid = adid;
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

	public String getDefaultimgurl() {
		return defaultimgurl;
	}

	public void setDefaultimgurl(String defaultimgurl) {
		this.defaultimgurl = defaultimgurl;
	}

	public int getOpentype() {
		return opentype;
	}

	public void setOpentype(int opentype) {
		this.opentype = opentype;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
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

	public Date getBegintime() {
		return begintime;
	}

	public void setBegintime(Date begintime) {
		this.begintime = begintime;
	}

	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	public int getOrdervalue() {
		return ordervalue;
	}

	public void setOrdervalue(int ordervalue) {
		this.ordervalue = ordervalue;
	}

	public int getIscanceled() {
		return iscanceled;
	}

	public void setIscanceled(int iscanceled) {
		this.iscanceled = iscanceled;
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
