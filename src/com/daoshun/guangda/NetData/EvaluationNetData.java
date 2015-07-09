package com.daoshun.guangda.NetData;

import java.io.Serializable;
import java.util.Date;


/**
 * 返回的临时评价信息
 * @author liukn
 *
 */
public class EvaluationNetData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6650971268029469899L;
	
	
	//投诉学员ID
		private int studentid;
		
		//投诉学员头像
		private String studentavatar;
		
		//投诉学员真是姓名
		private String name;
		
		//评分
		private float score;
		
		//学员电话号码
		private String phone;
		
		//学员证件号
		private String studentcardnum;
		
		//开始时间
		private Date starttime;
		
		//结束时间
		private Date endtime;
		
		//评价详情
		private String content;

		
		public int getStudentid() {
			return studentid;
		}

		
		public void setStudentid(int studentid) {
			this.studentid = studentid;
		}

		
		public String getStudentavatar() {
			return studentavatar;
		}

		
		public void setStudentavatar(String studentavatar) {
			this.studentavatar = studentavatar;
		}

		
		public String getName() {
			return name;
		}

		
		public void setName(String name) {
			this.name = name;
		}

		
		public float getScore() {
			return score;
		}

		
		public void setScore(float score) {
			this.score = score;
		}

		
		public String getPhone() {
			return phone;
		}

		
		public void setPhone(String phone) {
			this.phone = phone;
		}

		
		public String getStudentcardnum() {
			return studentcardnum;
		}

		
		public void setStudentcardnum(String studentcardnum) {
			this.studentcardnum = studentcardnum;
		}

	

		
		
		public Date getStarttime() {
			return starttime;
		}


		
		public void setStarttime(Date starttime) {
			this.starttime = starttime;
		}


		
		public Date getEndtime() {
			return endtime;
		}


		
		public void setEndtime(Date endtime) {
			this.endtime = endtime;
		}


		public String getContent() {
			return content;
		}

		
		public void setContent(String content) {
			this.content = content;
		}
		
		
		
}
