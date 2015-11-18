package com.daoshun.guangda.pojo;

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
 * 考驾题目收藏
 * @author 卢磊
 */
@Entity
@Table(name = "t_question_favorites")
public class QuestionFavorites {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", length = 20, nullable = false)
	private int id;
	
	//学员编号
	@Column(name = "imei", length = 10, nullable = false, columnDefinition = "INT default 0")
	private int imei;
	
	//题目序号
	@Column(name = "questionno", length = 10, nullable = false, columnDefinition = "INT default 0")
	private int questionno;
	
	// 添加时间
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "addtime", nullable = false)
	private Date addtime;
	
	//收藏题目数量
	@Transient
	private int num;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getImei() {
		return imei;
	}

	public void setImei(int imei) {
		this.imei = imei;
	}

	public int getQuestionno() {
		return questionno;
	}

	public void setQuestionno(int questionno) {
		this.questionno = questionno;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
	
}
