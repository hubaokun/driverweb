package com.daoshun.guangda.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 评论表
 * @author liukn
 * 
 */
@Entity
@Table(name = "t_evaluation")
public class EvaluationInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 评价ID
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "evaluationid", length = 10, nullable = false)
	private int evaluationid;

	// 评价的订单ID
	@Column(name = "order_id", length = 10, nullable = false)
	private int order_id;

	// 评价来自用户ID
	@Column(name = "from_user", length = 10, nullable = false)
	private int from_user;

	// 被评价的用户ID
	@Column(name = "to_user", length = 10, nullable = false)
	private int to_user;

	// 评价类型(1.用户评价教练2.教练评价用户)
	@Column(name = "type", length = 1, nullable = false, columnDefinition = "INt default 0")
	private Integer type = 0;

	// 评分1
	@Column(name = "score1", length = 2, nullable = false, columnDefinition = "float default 0")
	private float score1;

	// 评分2
	@Column(name = "score2", length = 2, nullable = false, columnDefinition = "float default 0")
	private float score2;

	// 评分3
	@Column(name = "score3", length = 2, nullable = false, columnDefinition = "float default 0")
	private float score3;

	// 评价内容
	@Column(name = "content", length = 1000, nullable = true)
	private String content;

	// 添加时间
	@Column(name = "addtime", nullable = false)
	private Date addtime;

	// 综合评分
	@Transient
	private float score;
	@Transient
	private String nickname;
	@Transient
	private String avatarUrl;
	
	public int getEvaluationid() {
		return evaluationid;
	}

	public void setEvaluationid(int evaluationid) {
		this.evaluationid = evaluationid;
	}

	public int getOrder_id() {
		return order_id;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public float getScore1() {
		return score1;
	}

	public void setScore1(float score1) {
		this.score1 = score1;
	}

	public float getScore2() {
		return score2;
	}

	public void setScore2(float score2) {
		this.score2 = score2;
	}

	public float getScore3() {
		return score3;
	}

	public void setScore3(float score3) {
		this.score3 = score3;
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

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}
}
