package com.daoshun.guangda.servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.daoshun.common.CommonUtils;
import com.daoshun.common.Constant;
import com.daoshun.common.ErrException;
import com.daoshun.guangda.pojo.AnswerRecordInfo;
import com.daoshun.guangda.pojo.Examination;
import com.daoshun.guangda.service.IExaminationService;
/**
 * 考驾题 servlet
 * @author 卢磊
 *
 */
@WebServlet("/examination")
public class ExaminationServlet extends BaseServlet{
	private static final long serialVersionUID = 1L;
	private IExaminationService examinationService;
	//private ISUserService suserService;
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		examinationService = (IExaminationService) applicationContext.getBean("examinationService");
		//suserService = (ISUserService) applicationContext.getBean("suserService");
	}
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String action = getAction(request);
			if (Constant.GETEXAMINATION.equals(action)) {
				// 获取普通题目
				getExamination(request,resultMap);
			}else if(Constant.GETANALOGEXAMINATION.equals(action)){
				getAnalogExamination(request, resultMap);
			}else if(Constant.ADDQUESTIONFAVORITES.equals(action)){
				addQuestionFavorites(request,resultMap);
			}else if(Constant.GETQUESTIONFAVORITES.equals(action)){
				getQuestionFavorites(request,resultMap);
			}else if(Constant.GETQUESTIONFAVORITESNUM.equals(action)){
				getQuestionFavoritesNum(request,resultMap);
			}else if(Constant.ADDANSWERRECORDINFO.equals(action)){
				addAnswerRecordInfo(request,resultMap);
			}else if(Constant.GETANSWERRECORDINFO.equals(action)){
				getAnswerRecordInfo(request,resultMap);
			}else if(Constant.REMOVEQUESTIONFAVORITES.equals(action)){
				removeQuestionFavorites(request,resultMap);
			}
			
			
		} catch (ErrException e) {
			e.printStackTrace();
			setResultWhenException(response, e.getMessage());
		}
		setResult(response, resultMap);
	
	}
	/**
	 *  获取非模拟类型的题目
	 */
	public void getExamination(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		//1 科目1的单选题	2 科目1的判断题      3 科目4的单选题     4 科目4的判断题      5 科目4的多选题 
		//type的值说明
		/*1 科目一 顺序练习  ： 1, 2  
		 *2 科目四顺序练习  ：3,4, 5，
		 *3 科目四多选练习 ： 5
		 *4 科目四动画题     ：
		 */
		String type = getRequestParamter(request, "type");
		String pagenum = getRequestParamter(request, "pagenum");
		
		CommonUtils.validateEmptytoMsg(type, "type为空");
		List<Examination> list=examinationService.getExamination(type,pagenum);
		for (Examination examination : list) {
			List<String> oplist=new ArrayList<String>();
			if(examination.getOption1()!=null && !"".equals(examination.getOption1())){
				oplist.add(examination.getOption1());
			}
			if(examination.getOption2()!=null && !"".equals(examination.getOption2())){
				oplist.add(examination.getOption2());
			}
			if(examination.getOption3()!=null && !"".equals(examination.getOption3())){
				oplist.add(examination.getOption3());
			}
			if(examination.getOption4()!=null && !"".equals(examination.getOption4())){
				oplist.add(examination.getOption4());
			}
			examination.setOptions(oplist);
		}
		
		int hasmore = examinationService.getExaminationMore(type,String.valueOf(CommonUtils.parseInt(pagenum, 0)+ 1));
		resultMap.put("hasmore", hasmore);
		resultMap.put("list", list);
	}
	/**
	 * 获取模拟考试题
	 * @param request
	 * @param resultMap
	 * @throws ErrException
	 */
	public void getAnalogExamination(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String type = getRequestParamter(request, "type");
		String pagenum = getRequestParamter(request, "pagenum");
		String studentid = getRequestParamter(request, "studentid");
		String answerid = getRequestParamter(request, "answerid");
		CommonUtils.validateEmptytoMsg(type, "type：不能为空，1 科目一模拟，2 科目四模拟");
		HashMap<String, Object> re=examinationService.getAnalogExamination(CommonUtils.parseInt(studentid,0),
												CommonUtils.parseInt(type,0), 
												CommonUtils.parseInt(answerid,-1),pagenum);
		
		resultMap.putAll(re);
	}
	/**
	 * 添加题目收藏
	 * @param request
	 * @param resultMap
	 * @throws ErrException
	 */
	public void addQuestionFavorites(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException{
		String studentid = getRequestParamter(request, "studentid");
		String questionno = getRequestParamter(request, "questionno");
		boolean issucc=examinationService.addQuestionFavorites(CommonUtils.parseInt(studentid,0), CommonUtils.parseInt(questionno,0));
		if(!issucc){
			resultMap.put("code", 2);
			resultMap.put("message","已经收藏过了" );
		}
	}
	/**
	 * 移除题目收藏
	 * @param request
	 * @param resultMap
	 * @throws ErrException
	 */
	public void removeQuestionFavorites(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException{
		String studentid = getRequestParamter(request, "studentid");
		String questionno = getRequestParamter(request, "questionno");
		boolean issucc=examinationService.removeQuestionFavorites(CommonUtils.parseInt(studentid,0), CommonUtils.parseInt(questionno,0));
		if(!issucc){
			resultMap.put("code", 2);
			resultMap.put("message","移除收藏失败！此题以前没有收藏过" );
		}
	}
	/**
	 *  获取学员收藏的题目
	 */
	public void getQuestionFavorites(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String studentid = getRequestParamter(request, "studentid");
		String pagenum = getRequestParamter(request, "pagenum");
		//CommonUtils.validateEmpty(coachid);
		List<Examination> list=examinationService.getQuestionFavorites(CommonUtils.parseInt(studentid, 0),pagenum);
		int hasmore = examinationService.getQuestionFavoritesMore(CommonUtils.parseInt(studentid, 0),String.valueOf(CommonUtils.parseInt(pagenum, 0)+ 1));
		resultMap.put("hasmore", hasmore);
		resultMap.put("list", list);
	}
	
	/*
	 * 模拟考试题生成规则：
	 * 科目一：
	 * 判断题 40个
	 * 单选题60个
	 * 
	 * 科目四：
	 * 判断题22个
	 * 单选题22个
	 * 多选题6个
	 */
	/**
	 *  获取学员收藏题目的数量
	 */
	public void getQuestionFavoritesNum(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String studentid = getRequestParamter(request, "studentid");
		int num=examinationService.getQuestionFavoritesNum(CommonUtils.parseInt(studentid, 0));
		resultMap.put("num", num);
	}
	
	/**
	 * 添加学员答题记录
	 * @param request
	 * @param resultMap
	 * @throws ErrException
	 */
	public void addAnswerRecordInfo(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String studentid = getRequestParamter(request, "studentid");
		String lastquestionno = getRequestParamter(request, "lastquestionno");
		String lastquestiontype = getRequestParamter(request, "lastquestiontype");
		String score = getRequestParamter(request, "score");
		String analogflag = getRequestParamter(request, "analogflag");
		String answerinfo = getRequestParamter(request, "answerinfo");
		CommonUtils.validateEmptytoMsg(studentid,"studentid为空");
		CommonUtils.validateEmptytoMsg(analogflag,"analogflag为空");
			//模拟题
		if("1".equals(analogflag)){
			CommonUtils.validateEmptytoMsg(score,"score为空");
			CommonUtils.validateEmptytoMsg(answerinfo,"answerinfo为空");
		}else if("2".equals(analogflag)){
			//非模拟题
			CommonUtils.validateEmptytoMsg(lastquestionno,"lastquestionno为空");
			CommonUtils.validateEmptytoMsg(lastquestiontype,"lastquestiontype为空");
		}else{
			CommonUtils.validateEmptytoMsg(analogflag,"analogflag值非法");
		}
		
		int n=examinationService.addAnswerRecordInfo(CommonUtils.parseInt(studentid, 0), 
														CommonUtils.parseInt(lastquestionno, 0), 
														CommonUtils.parseInt(lastquestiontype, 0),
														CommonUtils.parseInt(score, 0),
														CommonUtils.parseInt(analogflag, 0),answerinfo);
		if(n==-1){
			resultMap.put("code", -1);
			resultMap.put("message", "用户不存在");
		}
	}
	/**
	 * 获取学员上次的答题记录
	 * @param request
	 * @param resultMap
	 * @throws ErrException
	 */
	public void getAnswerRecordInfo(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String studentid = getRequestParamter(request, "studentid");
		CommonUtils.validateEmptytoMsg(studentid,"studentid为空");
		AnswerRecordInfo ar=examinationService.getAnswerRecordInfo(CommonUtils.parseInt(studentid,0));
		if(ar==null){
			resultMap.put("hasrecord", 0);
		}else{
			resultMap.put("hasrecord", 1);
			resultMap.put("data", ar);
		}
		/*try {
			examinationService.parserJson();
		} catch (JSONException e) {
			e.printStackTrace();
		}*/
	}
	
	
	
	
	
}
