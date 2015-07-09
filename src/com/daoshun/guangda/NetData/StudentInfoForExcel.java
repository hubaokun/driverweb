package com.daoshun.guangda.NetData;

public class StudentInfoForExcel {
	
	private int id;

	// 手机号
	private String phone;
	
	// 身份证号
	private String id_cardnum;
	
	// 真实姓名
	private String realname;

	// 学生证或驾驶证号
	private String student_cardnum;

	// 学员证制证时间
	private String student_card_creat;
	
	// 城市
	private String city;

	// 地址
	private String address;

	// 性别(1:男 2:女)
	private Integer gender;

	// 出生日期
	private String birthday;

	// 紧急联系人姓名
	private String urgent_person;

	// 紧急联系人电话
	private String urgent_phone;

	// 冻结金额
	private double fmoney;

	// 账户余额
	private double money;

	// 第三方qq登录id
	private String qq_openid;

	// 第三方微信登录id
	private String wx_openid;

	// 第三方微博登录id
	private String wb_openid;

	// 审核状态(0:未审核 1：审核通过 2：审核未通过)
	private Integer state = 0;

	// 教练确认状态(0:未认证 1：认证 2：审核未通过)
	private Integer coachstate = 0;

	// 综合评分
	private float score = 0;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getStudent_cardnum() {
		return student_cardnum;
	}

	public void setStudent_cardnum(String student_cardnum) {
		this.student_cardnum = student_cardnum;
	}

	public String getStudent_card_creat() {
		return student_card_creat;
	}

	public void setStudent_card_creat(String student_card_creat) {
		this.student_card_creat = student_card_creat;
	}

	public String getId_cardnum() {
		return id_cardnum;
	}

	public void setId_cardnum(String id_cardnum) {
		this.id_cardnum = id_cardnum;
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

	public double getFmoney() {
		return fmoney;
	}

	public void setFmoney(double fmoney) {
		this.fmoney = fmoney;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public String getQq_openid() {
		return qq_openid;
	}

	public void setQq_openid(String qq_openid) {
		this.qq_openid = qq_openid;
	}

	public String getWx_openid() {
		return wx_openid;
	}

	public void setWx_openid(String wx_openid) {
		this.wx_openid = wx_openid;
	}

	public String getWb_openid() {
		return wb_openid;
	}

	public void setWb_openid(String wb_openid) {
		this.wb_openid = wb_openid;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getCoachstate() {
		return coachstate;
	}

	public void setCoachstate(Integer coachstate) {
		this.coachstate = coachstate;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	

}
