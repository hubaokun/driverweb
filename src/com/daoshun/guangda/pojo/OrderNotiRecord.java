package com.daoshun.guangda.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_ordernoti_record")
public class OrderNotiRecord implements Serializable {
	private static final long serialVersionUID = 5839289257729892586L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "recordid", length = 10, nullable = false)
	private Integer recordid;
	@Column(name = "addtime", nullable = false, columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
	private Date addtime;
	@Column(name = "orderid", length = 10, nullable = false)
	private Integer orderid;
	@Column(name = "sendtime", nullable = false, columnDefinition = "TIMESTAMP")
	private Date sendtime;
	@Column(name = "type", length = 1, nullable = false, columnDefinition = "TINYINT default 0")
	private Integer type;
	@Column(name = "beforeminute", length = 10, nullable = false, columnDefinition = "INT default 0")
	private Integer beforeminute;
	@Column(name = "coachid", length = 10, nullable = false)
	private Integer coachid;
	@Column(name = "studentid", length = 10, nullable = false)
	private Integer studentid;

	public Integer getRecordid() {
		return recordid;
	}

	public void setRecordid(Integer recordid) {
		this.recordid = recordid;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public Integer getOrderid() {
		return orderid;
	}

	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}

	public Date getSendtime() {
		return sendtime;
	}

	public void setSendtime(Date sendtime) {
		this.sendtime = sendtime;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getBeforeminute() {
		return beforeminute;
	}

	public void setBeforeminute(int beforeminute) {
		this.beforeminute = beforeminute;
	}

	public Integer getCoachid() {
		return coachid;
	}

	public void setCoachid(Integer coachid) {
		this.coachid = coachid;
	}

	public Integer getStudentid() {
		return studentid;
	}

	public void setStudentid(Integer studentid) {
		this.studentid = studentid;
	}

}
