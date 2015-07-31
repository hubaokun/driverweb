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
import javax.persistence.Transient;

@Entity
@Table(name = "t_coin_record")
public class CoinRecordInfo implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "coinrecordid", length = 10, nullable = false)
	private Integer coinrecordid;

	// 小巴币的支付者id
	@Column(name = "payerid", length = 10, nullable = false, columnDefinition = "INT default 0")
	private Integer payerid;
	
	@Column(name = "payername", length = 150, nullable = true)
	private String payername;

	// 支付者类型 0:平台 1:驾校 2:教练
	@Column(name = "payertype", nullable = false, columnDefinition = "TINYINT default 0")
	private int payertype;
	
	// 小巴币的接受者id
	@Column(name = "receiverid", length = 10, nullable = false, columnDefinition = "INT default 0")
	private Integer receiverid;
	
	@Column(name = "receivername", length = 150, nullable = true)
	private String receivername;

	// 接受者类型 0:平台 1:驾校 2:教练 3 学员
	@Column(name = "receivertype", nullable = false, columnDefinition = "TINYINT default 0")
	private int receivertype;
	
	// 小巴币的数量
	@Column(name = "coinnum", nullable = false, columnDefinition = "Decimal(20,2) default 0.00")
	private Integer coinnum;

	// 支付类型,由2位数构成，便于将来状态扩展；10~19:驾校或教练发给学员；20~29学员订单支付;30~39教练兑换；40~49退款
	@Column(name = "type", length = 2, nullable = false, columnDefinition = "TINYINT default 0")
	private int type;

	// 发行者类型 0:平台发行 1:驾校发行 2:教练发行
	@Column(name = "ownertype", nullable = false, columnDefinition = "TINYINT default 0")
	private int ownertype;
	
	// 发行者ID
	@Column(name = "ownerid", length = 10, nullable = false, columnDefinition = "INT default 0")
	private Integer ownerid;
	
	@Column(name = "ownername", length = 150, nullable = true)
	private String ownername;
	
	// 添加时间
	@Column(name = "addtime", columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
	private Date addtime;
	
	
	public String getPayername() {
		return payername;
	}

	public void setPayername(String payername) {
		this.payername = payername;
	}

	public String getReceivername() {
		return receivername;
	}

	public void setReceivername(String receivername) {
		this.receivername = receivername;
	}

	public String getOwnername() {
		return ownername;
	}

	public void setOwnername(String ownername) {
		this.ownername = ownername;
	}

	public Integer getCoinrecordid() {
		return coinrecordid;
	}

	public void setCoinrecordid(Integer coinrecordid) {
		this.coinrecordid = coinrecordid;
	}

	public Integer getPayerid() {
		return payerid;
	}

	public void setPayerid(Integer payerid) {
		this.payerid = payerid;
	}

	public Integer getReceiverid() {
		return receiverid;
	}

	public void setReceiverid(Integer receiverid) {
		this.receiverid = receiverid;
	}

	public Integer getCoinnum() {
		return coinnum;
	}

	public void setCoinnum(Integer coinnum) {
		this.coinnum = coinnum;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getOwnertype() {
		return ownertype;
	}

	public void setOwnertype(int ownertype) {
		this.ownertype = ownertype;
	}

	public Integer getOwnerid() {
		return ownerid;
	}

	public void setOwnerid(Integer ownerid) {
		this.ownerid = ownerid;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public int getPayertype() {
		return payertype;
	}

	public void setPayertype(int payertype) {
		this.payertype = payertype;
	}

	public int getReceivertype() {
		return receivertype;
	}

	public void setReceivertype(int receivertype) {
		this.receivertype = receivertype;
	}
}
