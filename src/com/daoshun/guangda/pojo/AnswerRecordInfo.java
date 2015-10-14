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

/**
 * 答题记录表
 * @author 卢磊
 *
 */
@Entity
@Table(name = "t_answer_record")
public class AnswerRecordInfo {
	/*
	 *	模拟考每次题是随机提取，本次答题，如果没有答完而退出，下次会重新开始一套新的题，并且
		从第一道题开始做，不管是否全部做完，都记录每次的答题成绩
		
		模拟考每做完一道题，不显示正确答案，直到最后结束会显示本次的成绩及哪些题答对，哪些题答错
		
		顺序练习，多选，动画题每做完一道题，就显示本题的正确答案，当退出时，记录最后的答题题号，
		下次进入时，提示学员是否继续从上次的题号开始答题
		
		每个题目都有一个唯一的题号
		
		答题记录表：
		学员id
		答题结束时间
		上次练习到的题目类型
		题目号
		成绩（模拟题有成绩，其他没） 
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "answerid", length = 20, nullable = false)
	private int answerid;
	
	//学员编号
	@Column(name = "studentid", length = 10, nullable = false, columnDefinition = "INT default 0")
	private int studentid;
	
	// 添加时间
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "addtime", nullable = false)
	private Date addtime;
	
	@Column(name = "lastquestionno", length = 10, nullable = false, columnDefinition = "INT default 0")
	private int lastquestionno;
	
	@Column(name = "lastquestiontype", length = 10, nullable = false, columnDefinition = "INT default 0")
	private int lastquestiontype;
	
	@Column(name = "score", length = 10, nullable = false, columnDefinition = "INT default 0")
	private int score;
	//是否模拟题，模拟题有成绩，其他没。模拟题不需要记录上次的答题题号  0 不是 ，1是模拟题
	@Column(name = "analogflag", length = 10, nullable = false, columnDefinition = "INT default 0")
	private int analogflag;
	
	//模拟题库题目编号，多个题号数组
	@Column(name = "analogquestionno",length = 2000)
	private String analogquestionno;
	
	//模拟题回答内容：题号#回答选项#正确答案,题号#回答选项#正确答案
	@Column(name = "answerinfo",length = 2000)
	private String answerinfo;
	
	public int getAnswerid() {
		return answerid;
	}
	public void setAnswerid(int answerid) {
		this.answerid = answerid;
	}
	public int getStudentid() {
		return studentid;
	}
	public void setStudentid(int studentid) {
		this.studentid = studentid;
	}
	public Date getAddtime() {
		return addtime;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	public int getLastquestionno() {
		return lastquestionno;
	}
	public void setLastquestionno(int lastquestionno) {
		this.lastquestionno = lastquestionno;
	}
	public int getLastquestiontype() {
		return lastquestiontype;
	}
	public void setLastquestiontype(int lastquestiontype) {
		this.lastquestiontype = lastquestiontype;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getAnalogflag() {
		return analogflag;
	}
	public void setAnalogflag(int analogflag) {
		this.analogflag = analogflag;
	}
	public String getAnalogquestionno() {
		return analogquestionno;
	}
	public void setAnalogquestionno(String analogquestionno) {
		this.analogquestionno = analogquestionno;
	}
	
}
