package com.daoshun.guangda.serviceImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.daoshun.common.CommonUtils;
import com.daoshun.common.Constant;
import com.daoshun.guangda.pojo.AnswerRecordInfo;
import com.daoshun.guangda.pojo.Examination;
import com.daoshun.guangda.pojo.QuestionFavorites;
import com.daoshun.guangda.pojo.SuserInfo;
import com.daoshun.guangda.service.IExaminationService;
/**
 * 省市区查询
 * @author 卢磊
 *
 */
@Service("examinationService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class ExaminationServiceImpl extends BaseServiceImpl implements IExaminationService{
	/**
	 * 查询非模拟题题目
	 * @param type 
	 * 1 科目一 顺序练习  ： 1  2  
	 * 2科目四顺序练习  ：3，4, 5，
	 * 3 科目四多选练习 ： 5
	 * 4 科目四动画题     ：
	 * @param pagenum 页号 ，从0开始
	 */
	@Override
	public List<Examination> getExamination(String type,String pagenum) {
		/*1 科目一 顺序练习  ： 1  2  
		 *2科目四顺序练习  ：3，4, 5，
		 *3 科目四多选练习 ： 5
		 *4 科目四动画题     ：
		 */
		//1 科目1的单选题	2 科目1的判断题      3 科目4的单选题     4 科目4的判断题      5 科目4的多选题 
		String querystring1="from Examination  where questiontype in (1,2)";
		if("1".equals(type)){//1 科目一 顺序练习 
			querystring1="from Examination  where questiontype in (1,2)";
		}else if("2".equals(type)){
			querystring1="from Examination  where questiontype in (3,4,5)";
		}else if("3".equals(type)){
			querystring1="from Examination  where questiontype =5 ";
		}else if("4".equals(type)){
			querystring1="from Examination  where animationflag=1 ";
		}
		List<Examination> list=(List<Examination>)dataDao.pageQueryViaParam(querystring1, Constant.EXAMINATION_SIZE, CommonUtils.parseInt(pagenum, 0) + 1, null);
		//List<Examination> list=(List<Examination>) dataDao.getObjectsViaParam(querystring, params,subject,category);
		return list;
	}
	public int getExaminationMore(String type,int pagenum) {
		String querystring1="select count(*) from Examination  where questiontype in (1,2)";
		if("1".equals(type)){//1 科目一 顺序练习 
			querystring1="select count(*) from Examination  where questiontype in (1,2)";
		}else if("2".equals(type)){
			querystring1="select count(*) from Examination  where questiontype in (3,4,5)";
		}else if("3".equals(type)){
			querystring1="select count(*) from Examination  where questiontype =5 ";
		}else if("4".equals(type)){
			querystring1="select count(*) from Examination  where animationflag=1 ";
		}
		
		Long l=(Long) dataDao.getFirstObjectViaParam(querystring1, null);
		if(l==null){
			l=0L;
		}
		int pageCount=0;//总页数
		if(l.intValue()%Constant.EXAMINATION_SIZE==0){
			pageCount=l.intValue()/Constant.EXAMINATION_SIZE;
		}else{
			pageCount=l.intValue()/Constant.EXAMINATION_SIZE+1;
		}
		
		if(pagenum<=pageCount){
			return 1;
		}else{
			return 0;
		}
	}
	/**
	 * 获取模拟考试题目
	 * @param studentid
	 * @param type 1 科目一 模拟题  ，  2  科目四模拟题
	 * @param pagenum
	 * @return
	 */
	public HashMap<String, Object> getAnalogExamination(int studentid,int type,int answerid,String pagenum){
		//如果answerid，表示第一次进入模拟题页面，需要针对这个学员创建一个新的模拟题，其他情况是对已有的模拟题分页的查询
		if(answerid==0){
			//创建模拟题
			createAnalogQuestion(studentid,type);
		}
		
		String hql4="from AnswerRecordInfo where studentid =:studentid order by addtime desc ";
		String params[]={"studentid"};
		AnswerRecordInfo ari=(AnswerRecordInfo) dataDao.getFirstObjectViaParam(hql4, params, studentid);
		
		String aqnos="";
		if(ari!=null ){
			if(ari.getAnalogquestionno()!=null && !"".equals(ari.getAnalogquestionno())){
				aqnos=ari.getAnalogquestionno();
			}else{
				return null;
			}
		}else{
			return null;
		}
		
		StringBuffer hql3=new StringBuffer("from Examination where questionno in (0,");
		
		hql3.append(aqnos).append(")");
		
		List<Examination> list=(List<Examination>)dataDao.pageQueryViaParam(hql3.toString(), Constant.EXAMINATION_SIZE, CommonUtils.parseInt(pagenum, 0) + 1, null);
		List<Examination> nextlist=(List<Examination>)dataDao.pageQueryViaParam(hql3.toString(), Constant.EXAMINATION_SIZE, CommonUtils.parseInt(pagenum, 0) + 2, null);
		
		HashMap<String, Object> resultMap=new HashMap<String, Object>();
		if(nextlist!=null && nextlist.size()>0){
			resultMap.put("hasmore", 1);
		}else{
			resultMap.put("hasmore", 0);
		}
		resultMap.put("answerid", ari.getAnswerid());
		resultMap.put("list", list);
		return resultMap;
	}
	/**
	 * 
	 * @param studentid 学员ID
	 * @param type 1 科目一 模拟题  ，  2 科目四模拟题
	 */
	public void createAnalogQuestion(int studentid,int type){
		StringBuffer sbf=new StringBuffer();
		if(type==1){
			//随机取判断题40个 order by rand() limit 40
			String hql1="select e.questionno from Examination e where questiontype =2 order by rand()";
			List<Integer> list1=(List<Integer>) dataDao.pageQueryViaParam(hql1, 40,1,null);
			//随机取单选题60个order by rand() limit 60
			String qhl2="select e.questionno from Examination e where questiontype =1 order by rand()";
			List<Integer> list2=(List<Integer>) dataDao.pageQueryViaParam(qhl2, 60,1,null);
			
			for (Integer integer : list1) {
				if(integer==null)
				{
					integer=0;
				}
				sbf.append(integer).append(",");
			}
			
			for (int i = 0; i < list2.size(); i++) {
				if(i<list2.size()-1){
					sbf.append(list2.get(i)).append(",");
				}else{
					sbf.append(list2.get(i));
				}
			}
		}else if(type==2){
			//随机取判断题40个 order by rand() limit 40
			String hql1="select e.questionno from Examination e where questiontype =4 order by rand()";
			List<Integer> list1=(List<Integer>) dataDao.pageQueryViaParam(hql1, 22,1,null);
			//随机取单选题60个order by rand() limit 60
			String qhl2="select e.questionno from Examination e where questiontype =3 order by rand()";
			List<Integer> list2=(List<Integer>) dataDao.pageQueryViaParam(qhl2, 22,1,null);
			String qhl3="select e.questionno from Examination e where questiontype =5 order by rand()";
			List<Integer> list3=(List<Integer>) dataDao.pageQueryViaParam(qhl3, 6,1,null);
			for (Integer integer : list1) {
				if(integer==null)
				{
					integer=0;
				}
				sbf.append(integer).append(",");
			}
			for (Integer integer : list2) {
				if(integer==null)
				{
					integer=0;
				}
				sbf.append(integer).append(",");
			}
			
			for (int i = 0; i < list3.size(); i++) {
				if(i<list3.size()-1){
					sbf.append(list3.get(i)).append(",");
				}else{
					sbf.append(list3.get(i));
				}
			}
		}
		
				AnswerRecordInfo ar=new AnswerRecordInfo();
				ar.setAddtime(new Date());
				ar.setAnalogflag(1);
				ar.setStudentid(studentid);
				ar.setAnalogquestionno(sbf.toString()); 
				dataDao.addObject(ar);
	}
	/**
	 * 添加题目收藏
	 * @param studentid 学员ID
	 * @param questionid 问题id
	 */
	public boolean addQuestionFavorites(int studentid,int questionno){
		String querystring="from QuestionFavorites where studentid=:studentid and questionno=:questionno";
		String[] params={"studentid","questionno"};
		List<QuestionFavorites> list=(List<QuestionFavorites>) dataDao.getObjectsViaParam(querystring, params,studentid,questionno);
		if(list!=null && list.size()>0){
			//以前收藏过
			return false;
		}
		QuestionFavorites qf=new QuestionFavorites();
		qf.setAddtime(new Date());
		qf.setStudentid(studentid);
		qf.setQuestionno(questionno);
		dataDao.addObject(qf);
		return true;
	}
	/**
	 * 移除收藏的题目
	 * @param studentid
	 * @param questionno
	 * @return
	 */
	public boolean removeQuestionFavorites(int studentid,int questionno){
		String querystring="from QuestionFavorites where studentid=:studentid and questionno=:questionno";
		String[] params={"studentid","questionno"};
		List<QuestionFavorites> list=(List<QuestionFavorites>) dataDao.getObjectsViaParam(querystring, params,studentid,questionno);
		if(list!=null && list.size()>0){
			/*QuestionFavorites qf=list.get(0);
			dataDao.deleteObject(qf);*/
			dataDao.deleteBySql("delete from t_question_favorites where studentid="+studentid+" and questionno="+questionno);
			return true;
		}
		return false;
	}
	
	
	@Override
	/**
	 * 获取收藏的题目
	 * @param studentid 学员ID
	 * @param pagenum 页号
	 */
	public List<Examination> getQuestionFavorites(int studentid,String pagenum) {
		String querystring="from Examination where questionno in (select questionno from QuestionFavorites where studentid=:studentid)";
		String[] params={"studentid"};
		List<Examination> list=(List<Examination>)dataDao.pageQueryViaParam(querystring, Constant.EXAMINATION_SIZE, CommonUtils.parseInt(pagenum, 0) + 1, params, studentid);
		//List<Examination> list=(List<Examination>) dataDao.getObjectsViaParam(querystring, params,studentid);
		return list;
	}
	/**
	 * 获取收藏的题目 是否有下一页
	 * @param studentid 学员ID
	 * @param pagenum 页号
	 */
	public int getQuestionFavoritesMore(int studentid,String pagenum) {
		String querystring=" from Examination where questionno in (select questionno from QuestionFavorites where studentid=:studentid)";
		String[] params={"studentid"};
		List<Long> list=(List<Long>) dataDao.pageQueryViaParam(querystring, Constant.EXAMINATION_SIZE, CommonUtils.parseInt(pagenum, 0) + 1, params, studentid);
		if(list!=null && list.size()>0){
			Long n=list.get(0);
			if(n!=null && n>0){
				return 1;
			}
		}
		return 0;
	}
	/**
	 * 获取学员的收藏题目数量
	 * @param studentid
	 * @return
	 */
	public int getQuestionFavoritesNum(int studentid) {
		String querystring="select count(*) from QuestionFavorites where studentid=:studentid";
		String[] params={"studentid"};
		Long num=(Long) dataDao.getFirstObjectViaParam(querystring, params, studentid);
		if(num==null){
			return 0;
		}
		return num.intValue();
	}
	/**
	 * 添加答题记录
	 * @param studentid 学员id
	 * @param lastquestionno 题号
	 * @param lastquestiontype 题类型
	 * @param score 成绩
	 * @param analogflag 是否模拟题  0 不是 ，1是
	 * @param answerinfo 模拟题回答的内容 ：以json字符串的形式
	 */
	@Override
	public int addAnswerRecordInfo(int studentid,int lastquestionno,int lastquestiontype,int score,int analogflag,String answerinfo) {
		SuserInfo suser=dataDao.getObjectById(SuserInfo.class, studentid);
		if(suser==null){
			return -1;
		}
		AnswerRecordInfo ar=new AnswerRecordInfo();
		//是模拟题
		if(analogflag==1){
			ar.setAddtime(new Date());
			ar.setAnalogflag(1);
			ar.setScore(score);
			ar.setStudentid(studentid);
			ar.setAnswerinfo(answerinfo);//设置用户提交的模拟题的所有选择的答案
		}else{
			ar.setStudentid(studentid);
			ar.setLastquestionno(lastquestionno);
			ar.setLastquestiontype(lastquestiontype);
			ar.setAnalogflag(0);
			ar.setAddtime(new Date());
		}
		dataDao.addObject(ar);
		return 1;
		
	}
	/**
	 * 获取学员上次的答题记录
	 * @param studentid
	 * @return
	 */
	public AnswerRecordInfo getAnswerRecordInfo(int studentid){
		String hql="from AnswerRecordInfo where studentid=:studentid order by addtime desc";
		String params[]={"studentid"};
		AnswerRecordInfo ar=(AnswerRecordInfo) dataDao.getFirstObjectViaParam(hql, params, studentid);
		return ar;
	}
	/**
	 * 把从网上抓取的题库JSON导入数据库
	 * @author 卢磊
	 */
	public  void parserJson() throws JSONException {
		// 从JSon文件读取数据
		StringBuffer stringBuffer = new StringBuffer();
		String line = null;
		try {
			File someFile = new File("e:\\JsonFile000.json");
			//输入流
			FileInputStream fis = new FileInputStream(someFile);
			InputStreamReader isr = new InputStreamReader(fis,"UTF-8");
			BufferedReader br = new BufferedReader(isr);
			
			while ((line = br.readLine()) != null) {
				stringBuffer.append(line);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//System.out.println(stringBuffer.toString());
		// 将Json文件数据形成JSONObject对象
		JSONObject jsonObject = new JSONObject(stringBuffer.toString());
		
		JSONArray provinces = jsonObject.getJSONArray("RECORDS");
		for (int i = 0; i < provinces.length(); i++) {
			String questionid = provinces.getJSONObject(i).getString("questionid");
			String subject = provinces.getJSONObject(i).getString("subject");
			String option3 = provinces.getJSONObject(i).getString("option3");
			String animationflag = provinces.getJSONObject(i).getString("animationflag");
			String answer = provinces.getJSONObject(i).getString("answer");
			String commentate = provinces.getJSONObject(i).getString("commentate");
			String questiontype = provinces.getJSONObject(i).getString("questiontype");
			String questionno = provinces.getJSONObject(i).getString("questionno");
			String animationimg = provinces.getJSONObject(i).getString("animationimg");
			String option2 = provinces.getJSONObject(i).getString("option2");
			
			String question = provinces.getJSONObject(i).getString("question");
			String option4 = provinces.getJSONObject(i).getString("option4");
			String option1 = provinces.getJSONObject(i).getString("option1");
			
			
			Examination ex=new Examination();
			ex.setAnimationflag(CommonUtils.parseInt(animationflag,0));
			ex.setAnimationimg(animationimg);
			ex.setAnswer(answer);
			ex.setCommentate(commentate);
			ex.setOption1(option1);
			ex.setOption2(option2);
			ex.setOption3(option3);
			ex.setOption4(option4);
			ex.setQuestion(question);
			ex.setQuestionid(CommonUtils.parseInt(questionid,0));
			ex.setQuestionno(CommonUtils.parseInt(questionno,0));
			ex.setQuestiontype(CommonUtils.parseInt(questiontype,0));
			ex.setSubject(subject);
			dataDao.addObject(ex);
			
		}

		//System.out.println(jsonFileInfo);
	}

}
