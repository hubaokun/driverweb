package com.daoshun.guangda.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * APP教练信息表
 * 
 * @author 卢磊
 * 
 */
@Entity
@Table(name = "t_user_coach_app")
public class AppCuserInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "coachid", length = 20, nullable = false)
	private int coachid;

	private Integer realpic;

	private String drive_school;
	private Integer gender = 0;

	private String realname;

	private float score = 0;
	private String phone;//手机号
	private String telphone;//

	// 头像图片地址
	@Transient
	private String avatarurl;

	// 教练所在经度
	@Transient
	private String longitude;

	// 教练所在纬度
	@Transient
	private String latitude;

	// 上车详细地址
	@Transient
	private String detail;

	// 教练等级
	@Transient
	private String levelname;
	private Integer drive_schoolid;
	
	// 准教车列表
	@Transient
	private List<ModelsInfo> modellist;
	
	// 教练准教车型ID集合，逗号隔开
	@Column(name = "modelid", length = 50)
	private String modelid;
	// 总订单数量
	
	private Integer sumnum;
	private String address;
	private int avatar = 0;
	private BigDecimal money;
	private BigDecimal gmoney;
	
	private int coursestate;
	
	private int accompanycoursestate;
	
	private int accompanynum;
	
	public int getAccompanynum() {
		return accompanynum;
	}

	public void setAccompanynum(int accompanynum) {
		this.accompanynum = accompanynum;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public int getCoursestate() {
		return coursestate;
	}

	public void setCoursestate(int coursestate) {
		this.coursestate = coursestate;
	}

	public BigDecimal getGmoney() {
		return gmoney;
	}

	public void setGmoney(BigDecimal gmoney) {
		this.gmoney = gmoney;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public int getAvatar() {
		return avatar;
	}

	public void setAvatar(int avatar) {
		this.avatar = avatar;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getDrive_schoolid() {
		return drive_schoolid;
	}

	public void setDrive_schoolid(Integer drive_schoolid) {
		this.drive_schoolid = drive_schoolid;
	}

	public int getCoachid() {
		return coachid;
	}

	public void setCoachid(int coachid) {
		this.coachid = coachid;
	}

	

	

	public Integer getRealpic() {
		return realpic;
	}

	public void setRealpic(Integer realpic) {
		this.realpic = realpic;
	}

	public String getDrive_school() {
		return drive_school;
	}

	public void setDrive_school(String drive_school) {
		this.drive_school = drive_school;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public String getAvatarurl() {
		return avatarurl;
	}

	public void setAvatarurl(String avatarurl) {
		this.avatarurl = avatarurl;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getLevelname() {
		return levelname;
	}

	public void setLevelname(String levelname) {
		this.levelname = levelname;
	}

	public Integer getSumnum() {
		return sumnum;
	}

	public void setSumnum(Integer sumnum) {
		this.sumnum = sumnum;
	}

	public int getAccompanycoursestate() {
		return accompanycoursestate;
	}

	public void setAccompanycoursestate(int accompanycoursestate) {
		accompanycoursestate = accompanycoursestate;
	}

	public List<ModelsInfo> getModellist() {
		return modellist;
	}

	public void setModellist(List<ModelsInfo> modellist) {
		this.modellist = modellist;
	}

	public String getModelid() {
		return modelid;
	}

	public void setModelid(String modelid) {
		this.modelid = modelid;
	}

	

}
