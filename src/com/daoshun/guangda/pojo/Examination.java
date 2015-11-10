package com.daoshun.guangda.pojo;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 驾考题库
 * @author 卢磊
 *
 */
@Entity
@Table(name = "t_examination")
public class Examination {
	private static final long serialVersionUID = 1L;
	//题目序号
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "questionid", length = 20, nullable = false)
	private int questionid;
	
	//问题题号
	@Column(name = "questionno", length = 10, nullable = false, columnDefinition = "INT default 0")
	private int questionno;
	
	//科目 ：科目1 ，科目4
	@Column(name = "subject")
	private String subject;
	/*//题分类
	@Column(name = "category")
	private String category;*/
	
	 
	/**问题题型
	 *  1 	科目一的单选题
		2 	科目一的判断题
		3 	科目四的单选题
		4 	科目四的判断题
		5 	科目四的多选题
	 */
	@Column(name = "questiontype", length = 10, nullable = false, columnDefinition = "INT default 0")
	private int questiontype;
	
	@Column(name = "animationflag", length = 10, nullable = false, columnDefinition = "INT default 0")
	private int animationflag;
	
	//题目
	@Column(name = "question",length = 500, nullable = true)
	private String question;
	/*@Column(name = "options")
	private String options;*/
	//题目选项1
	@Column(name = "option1")
	private String option1;
	//题目选项2
	@Column(name = "option2")
	private String option2;
	//题目选项3
	@Column(name = "option3")
	private String option3;
	//题目选项4
	@Column(name = "option4")
	private String option4;
	//答案
	@Column(name = "answer")
	private String answer;
	
	//图片
	@Column(name = "animationimg")
	private String animationimg;
	
	//题目解释说明
	@Column(name = "commentate",length = 1000, nullable = true)
	private String commentate;
	
	@Transient
	private List<String> options;
	
	//是否收藏过,0  没有  ，1 有
	@Transient
	private int isfavorites=0;

	public int getQuestionid() {
		return questionid;
	}

	public void setQuestionid(int questionid) {
		this.questionid = questionid;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public int getQuestiontype() {
		return questiontype;
	}

	public void setQuestiontype(int questiontype) {
		this.questiontype = questiontype;
	}

	

	public int getAnimationflag() {
		return animationflag;
	}

	public void setAnimationflag(int animationflag) {
		this.animationflag = animationflag;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getOption1() {
		return option1;
	}

	public void setOption1(String option1) {
		this.option1 = option1;
	}

	public String getOption2() {
		return option2;
	}

	public void setOption2(String option2) {
		this.option2 = option2;
	}

	public String getOption3() {
		return option3;
	}

	public void setOption3(String option3) {
		this.option3 = option3;
	}

	public String getOption4() {
		return option4;
	}

	public void setOption4(String option4) {
		this.option4 = option4;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getAnimationimg() {
		return animationimg;
	}

	public void setAnimationimg(String animationimg) {
		this.animationimg = animationimg;
	}

	public String getCommentate() {
		return commentate;
	}

	public void setCommentate(String commentate) {
		this.commentate = commentate;
	}

	public int getQuestionno() {
		return questionno;
	}

	public void setQuestionno(int questionno) {
		this.questionno = questionno;
	}

	public List<String> getOptions() {
		return options;
	}

	public void setOptions(List<String> options) {
		this.options = options;
	}

	public int getIsfavorites() {
		return isfavorites;
	}

	public void setIsfavorites(int isfavorites) {
		this.isfavorites = isfavorites;
	}
	
}
