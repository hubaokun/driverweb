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
import javax.persistence.Transient;

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
   //被邀请人是否通过认证
   @Column(name="ischecked",length=1,columnDefinition="INT default 0")
   private int ischecked;
   //被邀请人是否已开单
   @Column(name="isorder",length=1,columnDefinition="INT default 0")
   private int isorder;
   //邀请人姓名
   @Column(name="coachname",length=50)
   private String coachname;
   //被邀请人姓名
   @Column(name="invited_peoplename",length=50)
   private String invitedpeoplename;
   // 推荐人联系电话
   @Column(name = "coach_telphone", length = 20)
   private String coachtelphone;
   // 被推荐人联系电话
   @Column(name = "invited_people_telphone", length = 20)
   private String invitedpeopletelphone;
   //认证奖励发放标识
   @Column(name = "cflag", length=10,columnDefinition="INT default 0")
   private int cflag;
   //开单奖励发放标识
   @Column(name = "oflag", length=10,columnDefinition="INT default 0")
   private int oflag;

   //邀请人总邀请数
   @Transient
   private int invitecount;
   //邀请人总开单数
   @Transient
   private int ordercount;
   //邀请人认证通过人数
   @Transient
   private int checkmancount;
   //奖金总额
   @Transient
   private BigDecimal totalreward;


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
   public int getIschecked() {
      return ischecked;
   }
   public void setIschecked(int ischecked) {
      this.ischecked = ischecked;
   }
   public int getIsorder() {
      return isorder;
   }
   public void setIsorder(int isorder) {
      this.isorder = isorder;
   }
   public int getInvitecount() {
      return invitecount;
   }
   public void setInvitecount(int invitecount) {
      this.invitecount = invitecount;
   }
   public int getOrdercount() {
      return ordercount;
   }
   public void setOrdercount(int ordercount) {
      this.ordercount = ordercount;
   }
   public String getCoachname() {
      return coachname;
   }
   public void setCoachname(String coachname) {
      this.coachname = coachname;
   }
   public String getInvitedpeoplename() {
      return invitedpeoplename;
   }
   public void setInvitedpeoplename(String invitedpeoplename) {
      this.invitedpeoplename = invitedpeoplename;
   }
   public String getCoachtelphone() {
      return coachtelphone;
   }
   public void setCoachtelphone(String coachtelphone) {
      this.coachtelphone = coachtelphone;
   }
   public String getInvitedpeopletelphone() {
      return invitedpeopletelphone;
   }
   public void setInvitedpeopletelphone(String invitedpeopletelphone) {
      this.invitedpeopletelphone = invitedpeopletelphone;
   }
   public BigDecimal getTotalreward() {
      return totalreward;
   }
   public void setTotalreward(BigDecimal totalreward) {
      this.totalreward = totalreward;
   }
   public int getCheckmancount() {
      return checkmancount;
   }
   public void setCheckmancount(int checkmancount) {
      this.checkmancount = checkmancount;
   }
   public int getCflag() {
      return cflag;
   }
   public void setCflag(int cflag) {
      this.cflag = cflag;
   }
   public int getOflag() {
      return oflag;
   }
   public void setOflag(int oflag) {
      this.oflag = oflag;
   }


}
