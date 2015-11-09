package com.daoshun.guangda.service;

import java.util.HashMap;
import java.util.List;

import org.json.JSONException;

import com.daoshun.guangda.pojo.AnswerRecordInfo;
import com.daoshun.guangda.pojo.AreaInfo;
import com.daoshun.guangda.pojo.AutoPositionInfo;
import com.daoshun.guangda.pojo.CityInfo;
import com.daoshun.guangda.pojo.Examination;
import com.daoshun.guangda.pojo.ProvinceInfo;
/**
 * 考驾service接口
 * @author 卢磊
 *
 */
public interface IExaminationService {
	public List<Examination> getExamination(String type,String pagenum,int studentid);
	public int getExaminationMore(String type,int pagenum);
	public HashMap<String, Object> getAnalogExamination(int studentid,int type,int answerid,String pagenum);
	public boolean addQuestionFavorites(int studentid,int questionid);
	public List<Examination> getQuestionFavorites(int studentid,String pagenum);
	public int getQuestionFavoritesMore(int studentid,String pagenum);
	public int getQuestionFavoritesNum(int studentid);
	public int addAnalogAnswerRecord(int studentid,int score,int answerid,String answerinfo) ;
	public AnswerRecordInfo getAnswerRecordInfo(int studentid);
	public  void parserJson() throws JSONException;
	public boolean removeQuestionFavorites(int studentid,int questionno);
	public int addAnswerRecord(int studentid,int lastquestionno);
	
	public List<Examination> getExaminationAll(String type,int studentid);
	public int getExaminationTotal(String type) ;
}
