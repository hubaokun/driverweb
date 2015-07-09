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
@Table(name = "t_ordernoti_set")
public class OrderNotiSetInfo implements Serializable {
	private static final long serialVersionUID = 5839289257729892586L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "setid", length = 10, nullable = false)
	private Integer setid;
	@Column(name = "beforeminute", length = 10, nullable = false, columnDefinition = "INT default 0")
	private Integer beforeminute;
	@Column(name = "type", length = 1, nullable = false, columnDefinition = "TINYINT default 0")
	private Integer type;
	@Column(name = "addtime", nullable = false, columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
	private Date addtime;

	public Integer getSetid() {
		return setid;
	}

	public void setSetid(Integer setid) {
		this.setid = setid;
	}

	public Integer getBeforeminute() {
		return beforeminute;
	}

	public void setBeforeminute(Integer beforeminute) {
		this.beforeminute = beforeminute;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

}
