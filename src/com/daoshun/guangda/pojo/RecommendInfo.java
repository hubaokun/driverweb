package com.daoshun.guangda.pojo;

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

/**
 * 
 * @author wjr 推荐信息表
 *
 */
@Entity
@Table(name="t_recommend")
public class RecommendInfo implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	//推荐列表ID，主键
   @Id
   @GeneratedValue(strategy=GenerationType.IDENTITY)
   @Column(name="rec_id",length=10,nullable = false)
   private int recid;
   //推荐教练ID
   @Column(name="coachid",length=10)
   private int coachid;
   //推荐学员ID
   @Column(name="studentid",length=10)
   private int studentid;
   //推荐码
   @Column(name="invite_id",length=50,nullable=false)
   private String inviteid;
   //被推荐教练的ID
   @Column(name="invited_coachid",length=10)
   private int invitedcoachid;
   //被推荐学员的ID
   @Column(name="invited_studentid",length=10)
   private int invitedstudentid;
   //本次推荐所获得的奖励
   @Column(name="reward",nullable=false,columnDefinition = "Decimal(20,2) default 0.00")
   private BigDecimal reward;
   //本次推荐发生的时间
   @Temporal(TemporalType.TIMESTAMP)
   @Column(name="add_time",nullable=false)
   private Date addtime;
   //本次推荐的类型
   @Column(name="type",length=1,nullable=false)
   private int type;
public int getRecid() {
	return recid;
}
public void setRecid(int recid) {
	this.recid = recid;
}
public int getCoachid() {
	return coachid;
}
public void setCoachid(int coachid) {
	this.coachid = coachid;
}
public int getStudentid() {
	return studentid;
}
public void setStudentid(int studentid) {
	this.studentid = studentid;
}
public String getInviteid() {
	return inviteid;
}
public void setInviteid(String inviteid) {
	this.inviteid = inviteid;
}
public int getInvitedcoachid() {
	return invitedcoachid;
}
public void setInvitedcoachid(int invitedcoachid) {
	this.invitedcoachid = invitedcoachid;
}
public int getInvitedstudentid() {
	return invitedstudentid;
}
public void setInvitedstudentid(int invitedstudentid) {
	this.invitedstudentid = invitedstudentid;
}
public BigDecimal getReward() {
	return reward;
}
public void setReward(BigDecimal reward) {
	this.reward = reward;
}
public Date getAddtime() {
	return addtime;
}
public void setAddtime(Date addtime) {
	this.addtime = addtime;
}
public int getType() {
	return type;
}
public void setType(int type) {
	this.type = type;
}

   
}
