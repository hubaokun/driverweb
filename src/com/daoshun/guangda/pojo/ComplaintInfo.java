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
import javax.persistence.Transient;

/**
 * 投诉表
 * @author liukn
 * 
 */
@Entity
@Table(name = "t_complaint")
public class ComplaintInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 888110791973506323L;

	// 投诉id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "complaintid", length = 10, nullable = false)
	private int complaintid;

	// 投诉来自用户id
	@Column(name = "from_user", length = 10, nullable = false)
	private int from_user;

	// 被投诉用户id
	@Column(name = "to_user", length = 10, nullable = false)
	private int to_user;

	// 投诉的订单id
	@Column(name = "order_id", length = 10, nullable = false)
	private int order_id;
	
	//投诉类型(1.用户投诉教练2.教练投诉用户)
	@Column(name = "type", length=1, columnDefinition = "INT default 0")
	private Integer type=0;
	
	//投诉原因id
	@Transient
	private int setid;

	// 投诉原因
	@Column(name = "reason", nullable = false)
	private String reason;

	// 投诉内容
	@Column(name = "content", length = 1000, nullable = false)
	private String content;

	// 添加时间
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "addtime", nullable = false)
	private Date addtime;

	// 投诉状态
	@Column(name = "state", length = 1, nullable = false, columnDefinition = "INT default 0")
	private Integer state=0;

	@Transient
	private String set;
	
	//学员信息
	@Transient
	private SuserInfo student;
	
	//教练信息
	@Transient
	private CuserInfo coach;
	
	//投诉关联订单
	@Transient
	private OrderInfo order;

	public int getComplaintid() {
		return complaintid;
	}

	public void setComplaintid(int complaintid) {
		this.complaintid = complaintid;
	}

	public int getFrom_user() {
		return from_user;
	}

	public void setFrom_user(int from_user) {
		this.from_user = from_user;
	}

	public int getTo_user() {
		return to_user;
	}

	public void setTo_user(int to_user) {
		this.to_user = to_user;
	}

	public int getOrder_id() {
		return order_id;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getSet() {
		return set;
	}

	public void setSet(String set) {
		this.set = set;
	}

	
	public int getSetid() {
		return setid;
	}

	
	public void setSetid(int setid) {
		this.setid = setid;
	}

	public SuserInfo getStudent() {
		return student;
	}

	public void setStudent(SuserInfo student) {
		this.student = student;
	}

	public CuserInfo getCoach() {
		return coach;
	}

	public void setCoach(CuserInfo coach) {
		this.coach = coach;
	}

	public OrderInfo getOrder() {
		return order;
	}

	public void setOrder(OrderInfo order) {
		this.order = order;
	}
	
}
