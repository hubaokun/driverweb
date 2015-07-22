package com.daoshun.guangda.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "t_user_student")
public class SuserInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "studentid", length = 10, nullable = false)
	private int studentid;

	// 修改的手机号
	@Column(name = "secondphone")
	private String secondphone;

	// 密码
	@Column(name = "password")
	private String password;

	// 手机号
	@Column(name = "phone", length = 20, unique = true)
	private String phone;

	//邀请码
	@Column(name = "invitecode", length = 20, unique = true)
	private String invitecode;
	


	// 头像id
	@Column(name = "avatar", length = 10, columnDefinition = "INT default 0")
	private Integer avatar = 0;

	// 学生证或驾驶证号
	@Column(name = "student_cardnum", unique = true)
	private String student_cardnum;

	// 学生证或驾驶证正面照id
	@Column(name = "student_cardpicf", length = 10, columnDefinition = "INT default 0")
	private Integer student_cardpicf = 0;

	// 学生证或驾驶证反面照id
	@Column(name = "student_cardpicb", length = 10, columnDefinition = "INT default 0")
	private Integer student_cardpicb = 0;

	// 学员证制证时间
	@Column(name = "student_card_creat")
	private String student_card_creat;

	// 身份证号
	@Column(name = "id_cardnum", unique = true)
	private String id_cardnum;

	// 身份证正面照id
	@Column(name = "id_cardpicf", length = 10, columnDefinition = "INT default 0")
	private Integer id_cardpicf = 0;

	// 身份证反面照id
	@Column(name = "id_cardpicb", length = 10, columnDefinition = "INT default 0")
	private Integer id_cardpicb = 0;

	// 城市
	@Column(name = "cityid")
	private String cityid;

	// 地址
	@Column(name = "address")
	private String address;

	// 性别(1:男 2:女)
	@Column(name = "gender")
	private Integer gender;

	// 出生日期
	@Column(name = "birthday")
	private String birthday;

	// 真实姓名
	@Column(name = "realname")
	private String realname;

	// 紧急联系人姓名
	@Column(name = "urgent_person")
	private String urgent_person;

	// 紧急联系人电话
	@Column(name = "urgent_phone")
	private String urgent_phone;

	// 冻结金额
	@Column(name = "fmoney", nullable = false, columnDefinition = "Decimal(20,2) default 0.00")
	private BigDecimal fmoney;

	// 账户余额
	@Column(name = "money", nullable = false, columnDefinition = "Decimal(20,2) default 0.00")
	private BigDecimal money;

	// 注册时间
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "addtime", nullable = false)
	private Date addtime;

	// 第三方qq登录id
	@Column(name = "qq_openid")
	private String qq_openid;

	// 第三方微信登录id
	@Column(name = "wx_openid")
	private String wx_openid;

	// 第三方微博登录id
	@Column(name = "wb_openid")
	private String wb_openid;

	// 审核状态(0:未审核 1：审核通过 2：审核未通过)--------已废弃，意义变更如下
	//学员状态：0-新注册用户，1-报名进行中，2-科目一进行中，3-科目三进行中，4-科目三进行中，5-科目四进行中
	@Column(name = "state", columnDefinition = "INT default 0")
	private Integer state = 0;

	// 教练确认状态(0:未认证 1：认证 2：审核未通过)
	@Column(name = "coachstate", columnDefinition = "INT default 0")
	private Integer coachstate = 0;

	// 教练上传身份证正面
	@Column(name = "ccheck_idcardpicf", columnDefinition = "INT default 0")
	private Integer ccheck_idcardpicf = 0;

	// 教练上传身份证反面
	@Column(name = "ccheck_idcardpicb", columnDefinition = "INT default 0")
	private Integer ccheck_idcardpicb = 0;

	// 教练上传驾驶证或者学员证
	@Column(name = "ccheck_pic", columnDefinition = "INT default 0")
	private Integer ccheck_pic = 0;

	// 综合评分
	@Column(name = "score", columnDefinition = "FLOAT default 0")
	private float score = 0;

	@Column(name = "alipay_account", length = 255)
	private String alipay_account;

	// 学员累计学习学时
	@Column(name = "learn_time", length = 10, columnDefinition = "INT default 0")
	private int learn_time;

	@Column(name = "token", length = 255)
	private String token;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "token_time")
	private Date token_time;

	// 头像url
	@Transient
	private String avatarurl;

	// 学生证或驾驶证正面照url
	@Transient
	private String student_cardpicf_url;

	// 学生证或驾驶证反面照url
	@Transient
	private String student_cardpicb_url;

	// 身份证正面照url
	@Transient
	private String id_cardpicf_url;

	// 身份证反面照url
	@Transient
	private String id_cardpicb_url;

	// 教练上传身份证正面照url
	@Transient
	private String ccheck_idcardpicf_url;

	// 教练上传身份证反面照url
	@Transient
	private String ccheck_idcardpicb_url;

	// 教练上传驾驶证或学员证照片url
	@Transient
	private String ccheck_pic_url;

	// 年龄
	@Transient
	private int age;

	// 认证教练信息
	@Transient
	private CuserInfo cuser;
	
	@Column(name = "provinceid", length = 100)
	private String provinceid;
	
	/*@Column(name = "city", length = 100)
	private String city;*/
	
	
	@Column(name = "areaid", length = 100)
	private String areaid;
	
	
	
	
	

	public int getStudentid() {
		return studentid;
	}

	public void setStudentid(int studentid) {
		this.studentid = studentid;
	}

	public String getSecondphone() {
		return secondphone;
	}

	public void setSecondphone(String secondphone) {
		this.secondphone = secondphone;
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

	public Integer getAvatar() {
		return avatar;
	}

	public void setAvatar(Integer avatar) {
		this.avatar = avatar;
	}

	public String getStudent_cardnum() {
		return student_cardnum;
	}

	public void setStudent_cardnum(String student_cardnum) {
		this.student_cardnum = student_cardnum;
	}

	public Integer getStudent_cardpicf() {
		return student_cardpicf;
	}

	public void setStudent_cardpicf(Integer student_cardpicf) {
		this.student_cardpicf = student_cardpicf;
	}

	public Integer getStudent_cardpicb() {
		return student_cardpicb;
	}

	public void setStudent_cardpicb(Integer student_cardpicb) {
		this.student_cardpicb = student_cardpicb;
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

	public Integer getId_cardpicf() {
		return id_cardpicf;
	}

	public void setId_cardpicf(Integer id_cardpicf) {
		this.id_cardpicf = id_cardpicf;
	}

	public Integer getId_cardpicb() {
		return id_cardpicb;
	}

	public void setId_cardpicb(Integer id_cardpicb) {
		this.id_cardpicb = id_cardpicb;
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

	public BigDecimal getFmoney() {
		return fmoney;
	}

	public void setFmoney(BigDecimal fmoney) {
		this.fmoney = fmoney;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
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

	public Integer getCcheck_idcardpicf() {
		return ccheck_idcardpicf;
	}

	public void setCcheck_idcardpicf(Integer ccheck_idcardpicf) {
		this.ccheck_idcardpicf = ccheck_idcardpicf;
	}

	public Integer getCcheck_idcardpicb() {
		return ccheck_idcardpicb;
	}

	public void setCcheck_idcardpicb(Integer ccheck_idcardpicb) {
		this.ccheck_idcardpicb = ccheck_idcardpicb;
	}

	public Integer getCcheck_pic() {
		return ccheck_pic;
	}

	public void setCcheck_pic(Integer ccheck_pic) {
		this.ccheck_pic = ccheck_pic;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	

	public String getCityid() {
		return cityid;
	}

	public void setCityid(String cityid) {
		this.cityid = cityid;
	}

	public String getProvinceid() {
		return provinceid;
	}

	public void setProvinceid(String provinceid) {
		this.provinceid = provinceid;
	}

	public String getAreaid() {
		return areaid;
	}

	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAvatarurl() {
		return avatarurl;
	}

	public void setAvatarurl(String avatarurl) {
		this.avatarurl = avatarurl;
	}

	public String getStudent_cardpicf_url() {
		return student_cardpicf_url;
	}

	public void setStudent_cardpicf_url(String student_cardpicf_url) {
		this.student_cardpicf_url = student_cardpicf_url;
	}

	public String getStudent_cardpicb_url() {
		return student_cardpicb_url;
	}

	public void setStudent_cardpicb_url(String student_cardpicb_url) {
		this.student_cardpicb_url = student_cardpicb_url;
	}

	public String getId_cardpicf_url() {
		return id_cardpicf_url;
	}

	public void setId_cardpicf_url(String id_cardpicf_url) {
		this.id_cardpicf_url = id_cardpicf_url;
	}

	public String getId_cardpicb_url() {
		return id_cardpicb_url;
	}

	public void setId_cardpicb_url(String id_cardpicb_url) {
		this.id_cardpicb_url = id_cardpicb_url;
	}

	public String getCcheck_idcardpicf_url() {
		return ccheck_idcardpicf_url;
	}

	public void setCcheck_idcardpicf_url(String ccheck_idcardpicf_url) {
		this.ccheck_idcardpicf_url = ccheck_idcardpicf_url;
	}

	public String getCcheck_idcardpicb_url() {
		return ccheck_idcardpicb_url;
	}

	public void setCcheck_idcardpicb_url(String ccheck_idcardpicb_url) {
		this.ccheck_idcardpicb_url = ccheck_idcardpicb_url;
	}

	public String getCcheck_pic_url() {
		return ccheck_pic_url;
	}

	public void setCcheck_pic_url(String ccheck_pic_url) {
		this.ccheck_pic_url = ccheck_pic_url;
	}

	public String getAlipay_account() {
		return alipay_account;
	}

	public void setAlipay_account(String alipay_account) {
		this.alipay_account = alipay_account;
	}

	public int getLearn_time() {
		return learn_time;
	}

	public void setLearn_time(int learn_time) {
		this.learn_time = learn_time;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public CuserInfo getCuser() {
		return cuser;
	}

	public void setCuser(CuserInfo cuser) {
		this.cuser = cuser;
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
	public String getInvitecode() {
		return invitecode;
	}

	public void setInvitecode(String invitecode) {
		this.invitecode = invitecode;
	}
}
