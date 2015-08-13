package com.daoshun.guangda.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 系统设置表
 * @author liukn
 *
 */
@Entity
@Table(name = "t_systemset")
public class SystemSetInfo implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -6900085673250783762L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "dataid", length = 10, nullable = false)
	private int dataid;

	@Column(name = "holidays", length = 2000)
	private String holidays;

	@Column(name = "time_cancel", length = 10, nullable = false, columnDefinition = "INT default 2880")
	private Integer time_cancel;

	@Column(name = "s_can_up", length = 10, nullable = false, columnDefinition = "INT default 30")
	private Integer s_can_up;

	@Column(name = "s_can_down", length = 10, nullable = false, columnDefinition = "INT default 60")
	private Integer s_can_down;

	@Column(name = "s_order_end", length = 10, nullable = false, columnDefinition = "INT default 2880")
	private Integer s_order_end;

	//平台对订单的抽成
	@Column(name = "order_pull", length = 10, nullable = false, columnDefinition = "INT default 0")
	private Integer order_pull;

	@Column(name = "s_register_money", nullable = false, columnDefinition = "Decimal(20,2) default 0.00")
	private BigDecimal s_register_money;

	@Column(name = "c_register_money", nullable = false, columnDefinition = "Decimal(20,2) default 0.00")
	private BigDecimal c_register_money;

	@Column(name = "c_register_gmoney", nullable = false, columnDefinition = "Decimal(20,2) default 0.00")
	private BigDecimal c_register_gmoney;

	@Column(name = "s_default_coment", length = 1000)
	private String s_default_coment;

	@Column(name = "c_default_coment", length = 1000)
	private String c_default_coment;

	@Column(name = "book_day_max", length = 10, nullable = false, columnDefinition = "INT default 30")
	private Integer book_day_max;

	// 登陆验证码过期时间,默认为15天
	@Column(name = "login_vcode_time", length = 10, columnDefinition = "INT default 15")
	private Integer login_vcode_time;

	@Column(name = "coach_default_price", nullable = false, columnDefinition = "Decimal(20,2) default 100")
	private Integer coach_default_price;

	@Column(name = "coach_default_subject", length = 10, columnDefinition = "INT default 1")
	private Integer coach_default_subject;

	@Column(name = "can_use_diff_coupon", length = 1, columnDefinition = "TINYINT default 0")
	private Integer can_use_diff_coupon;

	@Column(name = "can_use_coupon_count", length = 2, columnDefinition = "TINYINT default 1")
	private Integer can_use_coupon_count;
	
	//默认教练开课显示天数
	@Column(name = "course_date", length = 2, columnDefinition = "INT default 0")
	private Integer coursedate;
	
	//教练闪屏标示位
	@Column(name = "advertisement_flag", length = 2, columnDefinition = "TINYINT default 0")
	private Integer advertisement_flag;
	//教练闪屏url
	@Column(name = "advertisement_url",  length = 1000)
	private String advertisement_url;
	
	//教练弹窗标示位
	@Column(name = "coach_advertisement_flag", length = 2, columnDefinition = "TINYINT default 0")
	private Integer coach_advertisement_flag;
	//教练弹窗url
	@Column(name = "coach_advertisement_url",  length = 1000)
	private String coach_advertisement_url;
	
	//教练弹窗img地址
	@Column(name = "coach_advertisement_img",  length = 1000)
	private String coach_advertisement_img;
	
	//学员弹框标示位
	@Column(name = "student_advertisement_flag", length = 2, columnDefinition = "TINYINT default 0")
	private Integer student_advertisement_flag;
	//学员弹框url
	@Column(name = "student_advertisement_url",  length = 1000)
	private String student_advertisement_url;
	
	//学员弹框img地址
	@Column(name = "student_advertisement_img",  length = 1000)
	private String student_advertisement_img;

	@Column(name = "creward_amount", nullable = false, columnDefinition = "Decimal(20,2) default 0.00")
	private BigDecimal crewardamount;

	@Column(name = "oreward_amount", nullable = false, columnDefinition = "Decimal(20,2) default 0.00")
	private BigDecimal orewardamount;
	
	@Column(name = "min_version", nullable = false)
	private String minversion;


	public int getDataid() {
		return dataid;
	}

	public void setDataid(int dataid) {
		this.dataid = dataid;
	}

	public String getHolidays() {
		return holidays;
	}

	public void setHolidays(String holidays) {
		this.holidays = holidays;
	}

	public Integer getTime_cancel() {
		return time_cancel;
	}

	public Integer getS_can_up() {
		return s_can_up;
	}

	public void setS_can_up(Integer s_can_up) {
		this.s_can_up = s_can_up;
	}

	public Integer getS_can_down() {
		return s_can_down;
	}

	public void setS_can_down(Integer s_can_down) {
		this.s_can_down = s_can_down;
	}

	public Integer getS_order_end() {
		return s_order_end;
	}

	public void setS_order_end(Integer s_order_end) {
		this.s_order_end = s_order_end;
	}

	public void setTime_cancel(Integer time_cancel) {
		this.time_cancel = time_cancel;
	}

	public Integer getOrder_pull() {
		return order_pull;
	}

	public void setOrder_pull(Integer order_pull) {
		this.order_pull = order_pull;
	}

	public BigDecimal getS_register_money() {
		return s_register_money;
	}

	public void setS_register_money(BigDecimal s_register_money) {
		this.s_register_money = s_register_money;
	}

	public BigDecimal getC_register_money() {
		return c_register_money;
	}

	public void setC_register_money(BigDecimal c_register_money) {
		this.c_register_money = c_register_money;
	}

	public BigDecimal getC_register_gmoney() {
		return c_register_gmoney;
	}

	public void setC_register_gmoney(BigDecimal c_register_gmoney) {
		this.c_register_gmoney = c_register_gmoney;
	}

	public String getS_default_coment() {
		return s_default_coment;
	}

	public void setS_default_coment(String s_default_coment) {
		this.s_default_coment = s_default_coment;
	}

	public String getC_default_coment() {
		return c_default_coment;
	}

	public void setC_default_coment(String c_default_coment) {
		this.c_default_coment = c_default_coment;
	}

	public Integer getLogin_vcode_time() {
		return login_vcode_time;
	}

	public void setLogin_vcode_time(Integer login_vcode_time) {
		this.login_vcode_time = login_vcode_time;
	}

	public Integer getBook_day_max() {
		return book_day_max;
	}

	public void setBook_day_max(Integer book_day_max) {
		this.book_day_max = book_day_max;
	}

	public Integer getCoach_default_price() {
		return coach_default_price;
	}

	public void setCoach_default_price(Integer coach_default_price) {
		this.coach_default_price = coach_default_price;
	}

	public Integer getCoach_default_subject() {
		return coach_default_subject;
	}

	public void setCoach_default_subject(Integer coach_default_subject) {
		this.coach_default_subject = coach_default_subject;
	}

	public Integer getCan_use_diff_coupon() {
		return can_use_diff_coupon;
	}

	public void setCan_use_diff_coupon(Integer can_use_diff_coupon) {
		this.can_use_diff_coupon = can_use_diff_coupon;
	}

	public Integer getCan_use_coupon_count() {
		return can_use_coupon_count;
	}

	public void setCan_use_coupon_count(Integer can_use_coupon_count) {
		this.can_use_coupon_count = can_use_coupon_count;
	}


	public Integer getAdvertisement_flag() {
		return advertisement_flag;
	}

	public void setAdvertisement_flag(Integer advertisement_flag) {
		this.advertisement_flag = advertisement_flag;
	}


	public String getAdvertisement_url() {
		return advertisement_url;
	}

	public void setAdvertisement_url(String advertisement_url) {
		this.advertisement_url = advertisement_url;
	}

	public BigDecimal getCrewardamount() {
		return crewardamount;
	}

	public void setCrewardamount(BigDecimal crewardamount) {
		this.crewardamount = crewardamount;
	}

	public BigDecimal getOrewardamount() {
		return orewardamount;
	}

	public void setOrewardamount(BigDecimal orewardamount) {
		this.orewardamount = orewardamount;
	}

	public String getMinversion() {
		return minversion;
	}

	public void setMinversion(String minversion) {
		this.minversion = minversion;
	}

	public Integer getStudent_advertisement_flag() {
		return student_advertisement_flag;
	}

	public void setStudent_advertisement_flag(Integer student_advertisement_flag) {
		this.student_advertisement_flag = student_advertisement_flag;
	}

	public String getStudent_advertisement_url() {
		return student_advertisement_url;
	}

	public void setStudent_advertisement_url(String student_advertisement_url) {
		this.student_advertisement_url = student_advertisement_url;
	}

	public Integer getCoach_advertisement_flag() {
		return coach_advertisement_flag;
	}

	public void setCoach_advertisement_flag(Integer coach_advertisement_flag) {
		this.coach_advertisement_flag = coach_advertisement_flag;
	}

	public String getCoach_advertisement_url() {
		return coach_advertisement_url;
	}

	public void setCoach_advertisement_url(String coach_advertisement_url) {
		this.coach_advertisement_url = coach_advertisement_url;
	}

	public Integer getCoursedate() {
		return coursedate;
	}

	public void setCoursedate(Integer coursedate) {
		this.coursedate = coursedate;
	}

	public String getCoach_advertisement_img() {
		return coach_advertisement_img;
	}

	public void setCoach_advertisement_img(String coach_advertisement_img) {
		this.coach_advertisement_img = coach_advertisement_img;
	}

	public String getStudent_advertisement_img() {
		return student_advertisement_img;
	}

	public void setStudent_advertisement_img(String student_advertisement_img) {
		this.student_advertisement_img = student_advertisement_img;
	}
	
}