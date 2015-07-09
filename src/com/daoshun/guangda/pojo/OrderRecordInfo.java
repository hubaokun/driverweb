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

@Entity
@Table(name ="t_order_record")
public class OrderRecordInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	//id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "recordid", length=10, nullable = false)
	private int recordid;
	
	//订单id
	@Column(name = "orderid", length=10, nullable = false)
	private int orderid;
	
	//操作类型(1.学员确认上车2.学员确认下车3.教练确认上车4.教练确认下车5.学员取消订单6.学员评价7.学员投诉8.教练评价9.教练投诉)
	@Column(name = "operation", length=2, nullable = false)
	private int operation;
	
	//经度
	@Column(name = "longitude",length=50,nullable = true)
	private String longitude;
	
	//纬度
	@Column(name = "latitude",length=50,nullable = true)
	private String latitude;
	
	//详细地址
	@Column(name = "detail",length=500,nullable = true)
	private String detail;
	
	//添加时间
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "addtime")
	private Date addtime;
	
	//操作id
	@Column(name = "userid", length=10, nullable = false)
	private int userid;
	
	//评价id
	@Column(name = "evaluationid", length=10,columnDefinition = "INT default 0")
	private int evaluationid;
	
	//投诉id
	@Column(name = "complaintid", length=10,columnDefinition = "INT default 0")
	private int complaintid;
	
	//评价或投诉内容
	@Transient
	private String content;

	public int getRecordid() {
		return recordid;
	}

	public void setRecordid(int recordid) {
		this.recordid = recordid;
	}

	public int getOrderid() {
		return orderid;
	}

	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}

	public int getOperation() {
		return operation;
	}

	public void setOperation(int operation) {
		this.operation = operation;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public int getEvaluationid() {
		return evaluationid;
	}

	public void setEvaluationid(int evaluationid) {
		this.evaluationid = evaluationid;
	}

	public int getComplaintid() {
		return complaintid;
	}

	public void setComplaintid(int complaintid) {
		this.complaintid = complaintid;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
