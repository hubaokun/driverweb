package com.daoshun.guangda.NetData;

import java.util.Date;

public class CoachInfoForExcel {
	
	//ID
	private int id;
	// 电话
	private String phone;
	
	// 身份证号码
	private String id_cardnum;
	
	// 真实姓名
	private String realname;

	// 教练联系电话(默认是为phone)
	private String telphone;

	// 身份证到期时间
	private String id_cardexptime;

	// 教练证号
	private String coach_cardnum;

	// 教练证到期时间
	private String coach_cardexptime;

	// 驾驶证号
	private String drive_cardnum;

	// 驾驶证到期时间
	private String drive_cardexptime;

	// 车辆年检证号
	private String car_cardnum;

	// 车辆行驶证到期日期
	private String car_cardexptime;

	// 教练准教车型ID集合，逗号隔开
	private String modelid;

	// 教学用车型号（当carmodelid没有时生效）
	private String carmodel;

	// 教学用车id
	private Integer carmodelid = 0;

	// 教学用车牌照
	private String carlicense;

	// 驾校名称
	private String drive_school;

	// 性别
	private Integer gender = 0;

	// 生日
	private String birthday;

	// 教练所属城市
	private String city;

	// 教练联系地址
	private String address;

	// 紧急联系人姓名
	private String urgent_person;

	// 紧急联系人电话
	private String urgent_phone;

	// 教龄
	private Integer years;

	// 可提现余额
	private double money;

	// 保证金
	private double gmoney;

	// 冻结金额
	private double fmoney = 0;

	// 教练教车单价
	private double price = 0;

	// 教练默认教学科目 0表示未设置
	private int subjectdef;

	// 教练状态"   0.资料不完善1.资料完善，等待审核2.审核通过3.审核未通过,需重新提交资料"
	private Integer state;

	// 教练自我评价
	private String selfeval;

	// 教练等级
	private int level;

	// 教练注册时间
	private Date addtime;

	// 接收任务是否提醒(现默认提醒，该字段暂时不可修改)
	private int newtasknoti;

	// 教练是否可以设置订单可否取消
	private Integer cancancel;

	// 教练教学时长
	private int totaltime;

	// 综合评分
	private float score;

	// 冻结状态
	private Integer isfrozen;

	// 冻结开始时间
	private Date frozenstart;

	// 冻结结束时间
	private Date frozenend;

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

	public Integer getCarmodelid() {
		return carmodelid;
	}

	public void setCarmodelid(Integer carmodelid) {
		this.carmodelid = carmodelid;
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

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public double getGmoney() {
		return gmoney;
	}

	public void setGmoney(double gmoney) {
		this.gmoney = gmoney;
	}

	public double getFmoney() {
		return fmoney;
	}

	public void setFmoney(double fmoney) {
		this.fmoney = fmoney;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
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

	public int getTotaltime() {
		return totaltime;
	}

	public void setTotaltime(int totaltime) {
		this.totaltime = totaltime;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
