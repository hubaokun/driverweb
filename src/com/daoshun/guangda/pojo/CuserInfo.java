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
 * 教练信息表
 * 
 * @author liukn
 * 
 */
@Entity
@Table(name = "t_user_coach")
public class CuserInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 教练id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "coachid", length = 10, nullable = false)
	private int coachid;

	// 登录密码 加密
	@Column(name = "password", nullable = false)
	private String password;

	// 电话
	@Column(name = "phone", length = 20, unique = true)
	private String phone;

	// 教练联系电话(默认是为phone)
	@Column(name = "telphone", length = 20)
	private String telphone;

	// 身份证号码
	@Column(name = "id_cardnum", unique = true)
	private String id_cardnum;

	// 身份证正面照
	@Column(name = "id_cardpicf", length = 10, columnDefinition = "INT default 0")
	private int id_cardpicf = 0;

	// 身份证反面照
	@Column(name = "id_cardpicb", length = 10, columnDefinition = "INT default 0")
	private int id_cardpicb = 0;

	// 身份证到期时间
	@Column(name = "id_cardexptime", length = 20)
	private String id_cardexptime;

	// 教练证号
	@Column(name = "coach_cardnum", unique = true)
	private String coach_cardnum;

	// 教练证照片
	@Column(name = "coach_cardpic", length = 10, columnDefinition = "INT default 0")
	private int coach_cardpic;

	// 教练证到期时间
	@Column(name = "coach_cardexptime", length = 20)
	private String coach_cardexptime;

	// 驾驶证号
	@Column(name = "drive_cardnum", unique = true)
	private String drive_cardnum;

	// 驾驶证照片
	@Column(name = "drive_cardpic", length = 10, columnDefinition = "INT default 0")
	private int drive_cardpic;

	// 驾驶证到期时间
	@Column(name = "drive_cardexptime", length = 20)
	private String drive_cardexptime;

	// 车辆年检证号
	@Column(name = "car_cardnum", unique = true)
	private String car_cardnum;

	// 车辆行驶证正面照
	@Column(name = "car_cardpicf", length = 10, columnDefinition = "INT default 0")
	private int car_cardpicf;

	// 车辆行驶证反面照
	@Column(name = "car_cardpicb", length = 10, columnDefinition = "INT default 0")
	private int car_cardpicb;

	// 教练真实头像
	@Column(name = "realpic", length = 10, columnDefinition = "INT default 0")
	private int realpic;

	// 车辆行驶证到期日期
	@Column(name = "car_cardexptime", length = 20)
	private String car_cardexptime;

	// 教练准教车型ID集合，逗号隔开
	@Column(name = "modelid", length = 50)
	private String modelid;

	// 教学用车输入名（当carmodelid没有时生效）
	@Column(name = "carmodel")
	private String carmodel;

	// 教学用车id
	@Column(name = "carmodelid", length = 10)
	private int carmodelid;

	// 教学用车牌照
	@Column(name = "carlicense")
	private String carlicense;

	// 驾校名称
	@Column(name = "drive_school")
	private String drive_school;

	// 关联驾校id
	@Column(name = "drive_schoolid", length = 10)
	private Integer drive_schoolid;

	// 性别 1.男2.女 0未设置
	@Column(name = "gender", length = 1, columnDefinition = "INT default 0")
	private Integer gender = 0;

	// 生日
	@Column(name = "birthday")
	private String birthday;

	// 教练所属城市
	@Column(name = "city", length = 100)
	private String city;

	// 教练联系地址
	@Column(name = "address")
	private String address;

	// 真实姓名
	@Column(name = "realname")
	private String realname;

	// 紧急联系人姓名
	@Column(name = "urgent_person")
	private String urgent_person;

	// 紧急联系人电话
	@Column(name = "urgent_phone")
	private String urgent_phone;

	// 教龄
	@Column(name = "years", length = 2, columnDefinition = "INT default 0")
	private Integer years = 0;

	// 教练头像ID
	@Column(name = "avatar", length = 10, columnDefinition = "INT default 0")
	private int avatar = 0;

	// 可提现余额
	@Column(name = "money", nullable = false, columnDefinition = "Decimal(20,2) default 0.00")
	private BigDecimal money;

	// 保证金
	@Column(name = "gmoney", nullable = false, columnDefinition = "Decimal(20,2) default 0.00")
	private BigDecimal gmoney;

	// 冻结金额
	@Column(name = "fmoney", nullable = false, columnDefinition = "Decimal(20,2) default 0.00")
	private BigDecimal fmoney;

	// 教练教车单价
	@Column(name = "price", nullable = false, columnDefinition = "Decimal(20,2) default 0.00")
	private BigDecimal price;

	// 教练默认教学科目 0表示未设置
	@Column(name = "subjectdef", length = 10, nullable = false, columnDefinition = "INT default 0")
	private int subjectdef;

	// 教练状态"   0.资料不完善1.资料完善，等待审核2.审核通过3.审核未通过,需重新提交资料"
	@Column(name = "state", length = 1, nullable = false, columnDefinition = "INT default 0")
	private Integer state = 0;

	// 教练自我评价
	@Column(name = "selfeval", length = 500)
	private String selfeval;

	// 教练等级
	@Column(name = "level", length = 1)
	private int level;

	// 教练注册时间
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "addtime", nullable = false)
	private Date addtime;

	// 接收任务是否提醒(现默认提醒，该字段暂时不可修改)
	@Column(name = "newtasknoti", length = 1, columnDefinition = "INT default 0")
	private int newtasknoti = 0;

	// 教练提现方式 0:自己直接提现 1:提现先到驾校 这个字段必须是教练的drive_schoolid不为零的情况下才有效
	@Column(name = "cashtype", length = 1, nullable = false, columnDefinition = "TINYINT default 0")
	private int cashtype;

	// 教练是否可以设置订单可否取消
	@Column(name = "cancancel", nullable = false, length = 1, columnDefinition = "INT default 0")
	private Integer cancancel;

	// 教练教学时长
	@Column(name = "totaltime", nullable = false, length = 10, columnDefinition = "INT default 0")
	private int totaltime;

	// 综合评分
	@Column(name = "score", columnDefinition = "float default 0")
	private float score = 0;

	// 冻结状态
	@Column(name = "isfrozen", nullable = false, length = 1, columnDefinition = "INT default 0")
	private Integer isfrozen = 0;

	// 冻结开始时间
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "frozenstart")
	private Date frozenstart;

	// 冻结结束时间
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "frozenend")
	private Date frozenend;

	@Column(name = "token", length = 255)
	private String token;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "token_time")
	private Date token_time;

	// 教练是否离职 0未离职 1已离职
	@Column(name = "isquit", nullable = false, length = 1, columnDefinition = "INT default 0")
	private Integer isquit = 0;

	// 冻结结束时间
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "quittime")
	private Date quittime;

	@Column(name = "alipay_account", length = 255)
	private String alipay_account;

	// 准教车列表
	@Transient
	private List<ModelsInfo> modellist;

	// 身份证正面照面地址
	@Transient
	private String id_cardpicfurl;

	// 身份证反面照片地址
	@Transient
	private String id_cardpicburl;

	// 教练证照片地址
	@Transient
	private String coach_cardpicurl;

	// 驾驶证照片地址
	@Transient
	private String drive_cardpicurl;

	// 车辆年检证正面照片地址
	@Transient
	private String car_cardpicfurl;

	// 车辆年检证反面照片地址
	@Transient
	private String car_cardpicburl;

	// 真实照片地址
	@Transient
	private String realpicurl;

	// 冻结金额
	@Transient
	private String money_frozen;

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

	@Transient
	private String driveschool;

	@Transient
	private String teachcarmodel;

	@Transient
	private int age;
	// 默认科目的名称
	@Transient
	private String subjectname;
	// 默认的上车地址
	@Transient
	private String defaultAddress;

	@Transient
	private int couponhour;
	
	//教练等级
	@Transient
	private String levelname;
	
	@Column(name = "province", length = 100)
	private String province;
	
	/*@Column(name = "city", length = 100)
	private String city;*/
	
	@Column(name = "area", length = 100)
	private String area;

	
	
	
	
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public int getCoachid() {
		return coachid;
	}

	public void setCoachid(int coachid) {
		this.coachid = coachid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getId_cardnum() {
		return id_cardnum;
	}

	public void setId_cardnum(String id_cardnum) {
		this.id_cardnum = id_cardnum;
	}

	public int getId_cardpicf() {
		return id_cardpicf;
	}

	public void setId_cardpicf(int id_cardpicf) {
		this.id_cardpicf = id_cardpicf;
	}

	public int getId_cardpicb() {
		return id_cardpicb;
	}

	public void setId_cardpicb(int id_cardpicb) {
		this.id_cardpicb = id_cardpicb;
	}

	public String getId_cardexptime() {
		return id_cardexptime;
	}

	public void setId_cardexptime(String id_cardexptime) {
		this.id_cardexptime = id_cardexptime;
	}

	public String getCoach_cardnum() {
		return coach_cardnum;
	}

	public void setCoach_cardnum(String coach_cardnum) {
		this.coach_cardnum = coach_cardnum;
	}

	public int getCoach_cardpic() {
		return coach_cardpic;
	}

	public void setCoach_cardpic(int coach_cardpic) {
		this.coach_cardpic = coach_cardpic;
	}

	public String getCoach_cardexptime() {
		return coach_cardexptime;
	}

	public void setCoach_cardexptime(String coach_cardexptime) {
		this.coach_cardexptime = coach_cardexptime;
	}

	public String getDrive_cardnum() {
		return drive_cardnum;
	}

	public void setDrive_cardnum(String drive_cardnum) {
		this.drive_cardnum = drive_cardnum;
	}

	public int getDrive_cardpic() {
		return drive_cardpic;
	}

	public void setDrive_cardpic(int drive_cardpic) {
		this.drive_cardpic = drive_cardpic;
	}

	public String getDrive_cardexptime() {
		return drive_cardexptime;
	}

	public void setDrive_cardexptime(String drive_cardexptime) {
		this.drive_cardexptime = drive_cardexptime;
	}

	public String getCar_cardnum() {
		return car_cardnum;
	}

	public void setCar_cardnum(String car_cardnum) {
		this.car_cardnum = car_cardnum;
	}

	public int getCar_cardpicf() {
		return car_cardpicf;
	}

	public void setCar_cardpicf(int car_cardpicf) {
		this.car_cardpicf = car_cardpicf;
	}

	public int getCar_cardpicb() {
		return car_cardpicb;
	}

	public void setCar_cardpicb(int car_cardpicb) {
		this.car_cardpicb = car_cardpicb;
	}

	public String getCar_cardexptime() {
		return car_cardexptime;
	}

	public void setCar_cardexptime(String car_cardexptime) {
		this.car_cardexptime = car_cardexptime;
	}

	public String getModelid() {
		return modelid;
	}

	public void setModelid(String modelid) {
		this.modelid = modelid;
	}

	public String getCarmodel() {
		return carmodel;
	}

	public void setCarmodel(String carmodel) {
		this.carmodel = carmodel;
	}

	public String getCarlicense() {
		return carlicense;
	}

	public void setCarlicense(String carlicense) {
		this.carlicense = carlicense;
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

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getUrgent_person() {
		return urgent_person;
	}

	public void setUrgent_person(String urgent_person) {
		this.urgent_person = urgent_person;
	}

	public String getUrgent_phone() {
		return urgent_phone;
	}

	public void setUrgent_phone(String urgent_phone) {
		this.urgent_phone = urgent_phone;
	}

	public Integer getYears() {
		return years;
	}

	public void setYears(Integer years) {
		this.years = years;
	}

	public int getAvatar() {
		return avatar;
	}

	public void setAvatar(int avatar) {
		this.avatar = avatar;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public BigDecimal getGmoney() {
		return gmoney;
	}

	public void setGmoney(BigDecimal gmoney) {
		this.gmoney = gmoney;
	}

	public BigDecimal getFmoney() {
		return fmoney;
	}

	public void setFmoney(BigDecimal fmoney) {
		this.fmoney = fmoney;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getSubjectdef() {
		return subjectdef;
	}

	public void setSubjectdef(int subjectdef) {
		this.subjectdef = subjectdef;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getSelfeval() {
		return selfeval;
	}

	public void setSelfeval(String selfeval) {
		this.selfeval = selfeval;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public int getNewtasknoti() {
		return newtasknoti;
	}

	public void setNewtasknoti(int newtasknoti) {
		this.newtasknoti = newtasknoti;
	}

	public Integer getCancancel() {
		return cancancel;
	}

	public void setCancancel(Integer cancancel) {
		this.cancancel = cancancel;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public List<ModelsInfo> getModellist() {
		return modellist;
	}

	public void setModellist(List<ModelsInfo> modellist) {
		this.modellist = modellist;
	}

	public String getId_cardpicfurl() {
		return id_cardpicfurl;
	}

	public void setId_cardpicfurl(String id_cardpicfurl) {
		this.id_cardpicfurl = id_cardpicfurl;
	}

	public String getId_cardpicburl() {
		return id_cardpicburl;
	}

	public void setId_cardpicburl(String id_cardpicburl) {
		this.id_cardpicburl = id_cardpicburl;
	}

	public String getCoach_cardpicurl() {
		return coach_cardpicurl;
	}

	public void setCoach_cardpicurl(String coach_cardpicurl) {
		this.coach_cardpicurl = coach_cardpicurl;
	}

	public String getDrive_cardpicurl() {
		return drive_cardpicurl;
	}

	public void setDrive_cardpicurl(String drive_cardpicurl) {
		this.drive_cardpicurl = drive_cardpicurl;
	}

	public String getCar_cardpicfurl() {
		return car_cardpicfurl;
	}

	public void setCar_cardpicfurl(String car_cardpicfurl) {
		this.car_cardpicfurl = car_cardpicfurl;
	}

	public String getCar_cardpicburl() {
		return car_cardpicburl;
	}

	public void setCar_cardpicburl(String car_cardpicburl) {
		this.car_cardpicburl = car_cardpicburl;
	}

	public String getMoney_frozen() {
		return money_frozen;
	}

	public void setMoney_frozen(String money_frozen) {
		this.money_frozen = money_frozen;
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

	public int getTotaltime() {
		return totaltime;
	}

	public void setTotaltime(int totaltime) {
		this.totaltime = totaltime;
	}

	public int getCarmodelid() {
		return carmodelid;
	}

	public void setCarmodelid(int carmodelid) {
		this.carmodelid = carmodelid;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getRealpic() {
		return realpic;
	}

	public void setRealpic(int realpic) {
		this.realpic = realpic;
	}

	public String getRealpicurl() {
		return realpicurl;
	}

	public void setRealpicurl(String realpicurl) {
		this.realpicurl = realpicurl;
	}

	public Integer getIsfrozen() {
		return isfrozen;
	}

	public void setIsfrozen(Integer isfrozen) {
		this.isfrozen = isfrozen;
	}

	public Date getFrozenstart() {
		return frozenstart;
	}

	public void setFrozenstart(Date frozenstart) {
		this.frozenstart = frozenstart;
	}

	public Date getFrozenend() {
		return frozenend;
	}

	public void setFrozenend(Date frozenend) {
		this.frozenend = frozenend;
	}

	public Integer getDrive_schoolid() {
		return drive_schoolid;
	}

	public void setDrive_schoolid(Integer drive_schoolid) {
		this.drive_schoolid = drive_schoolid;
	}

	public String getDriveschool() {
		return driveschool;
	}

	public void setDriveschool(String driveschool) {
		this.driveschool = driveschool;
	}

	public String getSubjectname() {
		return subjectname;
	}

	public void setSubjectname(String subjectname) {
		this.subjectname = subjectname;
	}

	public String getDefaultAddress() {
		return defaultAddress;
	}

	public void setDefaultAddress(String defaultAddress) {
		this.defaultAddress = defaultAddress;
	}

	public Integer getIsquit() {
		return isquit;
	}

	public void setIsquit(Integer isquit) {
		this.isquit = isquit;
	}

	public Date getQuittime() {
		return quittime;
	}

	public void setQuittime(Date quittime) {
		this.quittime = quittime;
	}

	public String getAlipay_account() {
		return alipay_account;
	}

	public void setAlipay_account(String alipay_account) {
		this.alipay_account = alipay_account;
	}

	public int getCouponhour() {
		return couponhour;
	}

	public void setCouponhour(int couponhour) {
		this.couponhour = couponhour;
	}

	public String getTeachcarmodel() {
		return teachcarmodel;
	}

	public void setTeachcarmodel(String teachcarmodel) {
		this.teachcarmodel = teachcarmodel;
	}

	public int getCashtype() {
		return cashtype;
	}

	public void setCashtype(int cashtype) {
		this.cashtype = cashtype;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getToken_time() {
		return token_time;
	}

	public void setToken_time(Date token_time) {
		this.token_time = token_time;
	}

	public String getLevelname() {
		return levelname;
	}

	public void setLevelname(String levelname) {
		this.levelname = levelname;
	}

}
