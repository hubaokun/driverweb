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
import javax.persistence.Transient;

/**
 * 订单表
 * 
 * @author liukn
 * 
 */
@Entity
@Table(name = "t_order")
public class OrderInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 订单id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "orderid", length = 10, nullable = false)
	private int orderid;

	// 学生id
	@Column(name = "studentid", length = 10, nullable = false)
	private int studentid;

	// 教练id
	@Column(name = "coachid", length = 10, nullable = false)
	private int coachid;

	// 生成时间
	@Column(name = "creat_time", nullable = false)
	private Date creat_time;

	// 日期
	@Column(name = "date", length = 20, nullable = false)
	private String date;

	// 开始时间
	@Column(name = "start_time", nullable = false)
	private Date start_time;

	// 结束时间
	@Column(name = "end_time", nullable = false)
	private Date end_time;

	// 结束时间
	@Column(name = "over_time")
	private Date over_time;

	// 预定小时数
	@Column(name = "time", length = 1, nullable = false, columnDefinition = "INT default 0")
	private Integer time = 0;

	// 订单总价格
	@Column(name = "total", nullable = false, columnDefinition = "Decimal(20,2) default 0.00")
	private BigDecimal total;

	@Column(name = "price_out1", nullable = false, columnDefinition = "Decimal(20,2) default 0.00")
	private BigDecimal price_out1;

	@Column(name = "price_out2", nullable = false, columnDefinition = "Decimal(20,2) default 0.00")
	private BigDecimal price_out2;

	@Column(name = "order_pull1", length = 10, nullable = false, columnDefinition = "INT default 0")
	private Integer order_pull1;

	@Column(name = "order_pull2", length = 10, nullable = false, columnDefinition = "INT default 0")
	private Integer order_pull2;

	@Column(name = "delmoney", nullable = false, columnDefinition = "INT default 0")
	private Integer delmoney;

	// 学生订单状态
	@Column(name = "studentstate", length = 1, nullable = false, columnDefinition = "INT default 0")
	private Integer studentstate = 0;

	// 教练订单状态
	@Column(name = "coachstate", length = 1, nullable = false, columnDefinition = "INT default 0")
	private Integer coachstate = 0;

	@Column(name = "cancancel", length = 1, nullable = false, columnDefinition = "INT default 0")
	private Integer cancancel;
	
	// 是否带车陪驾
	@Column(name = "attachcar", length = 1, nullable = false, columnDefinition = "INT default 0")
	private Integer attachcar = 0;
	// 经度
	@Column(name = "longitude", length = 50, nullable = false)
	private String longitude;

	// 纬度
	@Column(name = "latitude", length = 50, nullable = false)
	private String latitude;
	// 详细地址
	@Column(name = "detail", length = 500, nullable = false)
	private String detail;

	@Column(name = "couponrecordid", length = 50, nullable = false)
	private String couponrecordid;
	
	@Column(name = "paytype", length = 11, nullable = true, columnDefinition = "INT default 0")
	private int paytype;//1 余额  2 小巴券  3 小巴币
	// 学生的详细信息
	@Transient
	private SuserInfo studentinfo;

	// 教练详细信息
	@Transient
	private CuserInfo cuserinfo;

	// 教练任务的状态
	@Transient
	private Integer state;

	// 学员给教练的评价
	@Transient
	private EvaluationInfo myevaluation;

	// 教练给学员的评价
	@Transient
	private EvaluationInfo evaluation;

	// 综合评分
	@Transient
	private float score;

	// 评价内容
	@Transient
	private String content;

	// 学生对教练的评价
	@Transient
	private EvaluationInfo studentscore;

	// 教练对学生的评价
	@Transient
	private EvaluationInfo coachscore;

	// 订单投诉条数
	@Transient
	private long complaintnum;

	// 订单时间段价格记录
	@Transient
	private List<OrderPrice> orderprice;
	
	
	
	
	

	// 是否有投诉
	// @Transient
	// private Integer havecomplaint;
	@Transient
	private int can_complaint;
	@Transient
	private int need_uncomplaint;
	@Transient
	private int can_cancel;
	@Transient
	private int can_up;
	@Transient
	private int can_down;
	@Transient
	private int can_comment;

	// 订单距离开始小时数
	@Transient
	private int hours;
	
	
	
	

	//0 教练未同意取消  1 教练已同意取消
	@Transient
	private int agreecancel;
	//驾照
	@Transient
	private String carlicense;
	//订单准教车型
	@Transient
	private String modelid;
	//科目
	@Transient
	private String subjectname;
	
	@Column(name = "mixCoin", length = 11, nullable = true, columnDefinition = "INT default 0")
	private int mixCoin;//混合支付时的小巴币
	@Column(name = "mixMoney", length = 11, nullable = true, columnDefinition = "INT default 0")
	private int mixMoney;//混合支付时的余额
	
	//投诉内容
	@Transient
	private String complaintcontent;
	//投诉原因
	@Transient
	private String reason;
	
	@Transient
	private int disagree;//历史订单中，分原有的历史订单和教练 不同意取消的订单，原有历史订单此属性是0，教练 不同意取消的订单是1
	
	
	
	public int getDisagree() {
		return disagree;
	}

	public void setDisagree(int disagree) {
		this.disagree = disagree;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getComplaintcontent() {
		return complaintcontent;
	}

	public void setComplaintcontent(String complaintcontent) {
		this.complaintcontent = complaintcontent;
	}

	public int getMixCoin() {
		return mixCoin;
	}

	public void setMixCoin(int mixCoin) {
		this.mixCoin = mixCoin;
	}

	public int getMixMoney() {
		return mixMoney;
	}

	public void setMixMoney(int mixMoney) {
		this.mixMoney = mixMoney;
	}

	public String getSubjectname() {
		return subjectname;
	}

	public void setSubjectname(String subjectname) {
		this.subjectname = subjectname;
	}

	public String getModelid() {
		return modelid;
	}

	public void setModelid(String modelid) {
		this.modelid = modelid;
	}

	public String getCarlicense() {
		return carlicense;
	}

	public void setCarlicense(String carlicense) {
		this.carlicense = carlicense;
	}

	public int getPaytype() {
		return paytype;
	}

	public void setPaytype(int paytype) {
		this.paytype = paytype;
	}
	public int getAgreecancel() {
		return agreecancel;
	}

	public void setAgreecancel(int agreecancel) {
		this.agreecancel = agreecancel;
	}

	public int getOrderid() {
		return orderid;
	}

	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}

	public int getStudentid() {
		return studentid;
	}

	public void setStudentid(int studentid) {
		this.studentid = studentid;
	}

	public int getCoachid() {
		return coachid;
	}

	public void setCoachid(int coachid) {
		this.coachid = coachid;
	}

	public Date getCreat_time() {
		return creat_time;
	}

	public void setCreat_time(Date creat_time) {
		this.creat_time = creat_time;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Date getStart_time() {
		return start_time;
	}

	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}

	public Date getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public Integer getStudentstate() {
		return studentstate;
	}

	public void setStudentstate(Integer studentstate) {
		this.studentstate = studentstate;
	}

	public Integer getCoachstate() {
		return coachstate;
	}

	public void setCoachstate(Integer coachstate) {
		this.coachstate = coachstate;
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

	public SuserInfo getStudentinfo() {
		return studentinfo;
	}

	public void setStudentinfo(SuserInfo studentinfo) {
		this.studentinfo = studentinfo;
	}

	public CuserInfo getCuserinfo() {
		return cuserinfo;
	}

	public void setCuserinfo(CuserInfo cuserinfo) {
		this.cuserinfo = cuserinfo;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public EvaluationInfo getMyevaluation() {
		return myevaluation;
	}

	public void setMyevaluation(EvaluationInfo myevaluation) {
		this.myevaluation = myevaluation;
	}

	public EvaluationInfo getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(EvaluationInfo evaluation) {
		this.evaluation = evaluation;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public EvaluationInfo getStudentscore() {
		return studentscore;
	}

	public void setStudentscore(EvaluationInfo studentscore) {
		this.studentscore = studentscore;
	}

	public EvaluationInfo getCoachscore() {
		return coachscore;
	}

	public void setCoachscore(EvaluationInfo coachscore) {
		this.coachscore = coachscore;
	}

	public long getComplaintnum() {
		return complaintnum;
	}

	public void setComplaintnum(long complaintnum) {
		this.complaintnum = complaintnum;
	}

	public List<OrderPrice> getOrderprice() {
		return orderprice;
	}

	public void setOrderprice(List<OrderPrice> orderprice) {
		this.orderprice = orderprice;
	}

	// public Integer getHavecomplaint() {
	// return havecomplaint;
	// }
	//
	// public void setHavecomplaint(Integer havecomplaint) {
	// this.havecomplaint = havecomplaint;
	// }

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public BigDecimal getPrice_out1() {
		return price_out1;
	}

	public void setPrice_out1(BigDecimal price_out1) {
		this.price_out1 = price_out1;
	}

	public BigDecimal getPrice_out2() {
		return price_out2;
	}

	public void setPrice_out2(BigDecimal price_out2) {
		this.price_out2 = price_out2;
	}

	public int getCan_complaint() {
		return can_complaint;
	}

	public void setCan_complaint(int can_complaint) {
		this.can_complaint = can_complaint;
	}

	public int getNeed_uncomplaint() {
		return need_uncomplaint;
	}

	public void setNeed_uncomplaint(int need_uncomplaint) {
		this.need_uncomplaint = need_uncomplaint;
	}

	public int getCan_cancel() {
		return can_cancel;
	}

	public void setCan_cancel(int can_cancel) {
		this.can_cancel = can_cancel;
	}

	public int getCan_up() {
		return can_up;
	}

	public void setCan_up(int can_up) {
		this.can_up = can_up;
	}

	public int getCan_down() {
		return can_down;
	}

	public void setCan_down(int can_down) {
		this.can_down = can_down;
	}

	public int getCan_comment() {
		return can_comment;
	}

	public void setCan_comment(int can_comment) {
		this.can_comment = can_comment;
	}

	public Integer getCancancel() {
		return cancancel;
	}

	public void setCancancel(Integer cancancel) {
		this.cancancel = cancancel;
	}

	public String getCouponrecordid() {
		return couponrecordid;
	}

	public void setCouponrecordid(String couponrecordid) {
		this.couponrecordid = couponrecordid;
	}

	public int getDelmoney() {
		return delmoney;
	}

	public void setDelmoney(int delmoney) {
		this.delmoney = delmoney;
	}

	public Date getOver_time() {
		return over_time;
	}

	public void setOver_time(Date over_time) {
		this.over_time = over_time;
	}

	public Integer getOrder_pull1() {
		return order_pull1;
	}

	public void setOrder_pull1(Integer order_pull1) {
		this.order_pull1 = order_pull1;
	}

	public Integer getOrder_pull2() {
		return order_pull2;
	}

	public void setOrder_pull2(Integer order_pull2) {
		this.order_pull2 = order_pull2;
	}

	public Integer getAttachcar() {
		return attachcar;
	}

	public void setAttachcar(Integer attachcar) {
		this.attachcar = attachcar;
	}

}
