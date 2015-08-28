package com.daoshun.guangda.action;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.daoshun.common.CommonUtils;
import com.daoshun.guangda.pojo.CoachLevelInfo;
import com.daoshun.guangda.pojo.ComplaintSetInfo;
import com.daoshun.guangda.pojo.CsubjectInfo;
import com.daoshun.guangda.pojo.ModelsInfo;
import com.daoshun.guangda.pojo.SystemSetInfo;
import com.daoshun.guangda.pojo.TeachcarInfo;
import com.daoshun.guangda.service.IAdvertisementService;
import com.daoshun.guangda.service.ICUserService;

@ParentPackage("default")
@Controller
@Scope("prototype")
public class SystemConfigAction extends BaseAction {

	/**
	 *
	 */
	private static final long serialVersionUID = -2449919380551492547L;

	@Resource
	private ICUserService cuserService;

	@Resource
	private IAdvertisementService advertisementService;
	
	private List<ModelsInfo> modelslist;

	private List<CoachLevelInfo> levellist;

	private List<ComplaintSetInfo> complaintSetInfolist;

	private List<CsubjectInfo> subjectlist;

	private List<TeachcarInfo> teachcarInfolist;

	private ModelsInfo model;

	private CoachLevelInfo levelInfo;

	private ModelsInfo oldmodel;

	private ComplaintSetInfo complaintSetInfo;

	private CsubjectInfo subjectInfo;

	private CsubjectInfo newsubjectInfo;

	private SystemSetInfo systemSetInfo;

	private TeachcarInfo teachcar;

	private TeachcarInfo newteachcar;

	private Integer pageIndex = 1;

	private Integer pageCount = 0;

	private long total;

	private String modelname;

	// 原来的车型名称
	private String oldmodelname;

	// 修改后的车型名称
	private String editmodelname;

	private String searchname;

	// 等级名称
	private String levelname;

	// 原来的教练等级名称
	private String oldlevelname;

	// 修改后的教练等级名称
	private String newlevelname;

	// 查询类型
	private int searchtype;

	// 投诉原因设置id
	private int setid;

	// 投诉内容
	private String content;

	// 投诉对象类型
	private int type;

	// 修改投诉原因
	private String newcontent;

	// 科目id
	private int subjectid;

	// 添加的科目名称
	private String addsubjectname;

	// 修改的科目名称
	private String editsubjectname;

	// 原来的科目名称
	private String oldsubjectname;

	private int index;

	private int change_id;

	//新编辑的holidays
	private String newholidays;

	//点击日期
	private String clickholiday;

	//数据id
	private int dataid;

	//编辑值
	private String editvalue;

	//要修改的列名
	private String colname;

	//图片文件
	private File ad_img;
	
	
	//图片路径
	private String ad_url;
	/**
	 * 得到车型列表
	 *
	 * @return
	 */
	@Action(value = "/getTeachcarModellist", results = { @Result(name = SUCCESS, location = "/coachcar.jsp") })
	public String getTeachcarModellist() {
		teachcarInfolist = cuserService.getTeachcarInfolist();
		return SUCCESS;
	}

	/**
	 * 添加车型
	 */
	@Action(value = "/addTeachcarModel")
	public void addTeachcarModel() {
//		model = cuserService.getModelsInfoBymodelname(modelname);
		teachcar=cuserService.getTeachcarInfoBymodelname(modelname);
		if (teachcar != null) {
			setResponseStr("error");
			return;
		} else {
			TeachcarInfo teachcar = new TeachcarInfo();
			teachcar.setModelname(modelname);
			teachcar.setAddtime(new Date());
			cuserService.addObject(teachcar);
			setResponseStr("success");
		}
	}


	/**
	 * 删除车型
	 */
	@Action(value = "/delTeachcarModel")
	public void delTeachcarModel() {
		teachcar = cuserService.getTeachcarInfoBymodelname(modelname);
		if (teachcar == null) {
			setResponseStr("error");
			return;
		} else {
			cuserService.delObject(teachcar);
			setResponseStr("success");
		}
	}

	/**
	 * 修改车型
	 */
	@Action(value = "/editTeachcarModel")
	public void editTeachcarModel() {
		teachcar = cuserService.getTeachcarInfoBymodelname(oldmodelname);
		newteachcar=cuserService.getTeachcarInfoBymodelname(editmodelname);
		if(newteachcar!=null){
			setResponseStr("error1");
		}else{
			if (teachcar == null) {
				setResponseStr("error");
			} else {
				teachcar.setModelname(editmodelname);
				cuserService.updateObject(teachcar);
				setResponseStr("success");
			}
		}
	}

	/**
	 * 得到准教车型列表
	 *
	 * @return
	 */
	@Action(value = "/getModellist", results = { @Result(name = SUCCESS, location = "/modellist.jsp") })
	public String getModellist() {
		modelslist = cuserService.getModelList();
		return SUCCESS;
	}

	/**
	 * 添加准教车型
	 */
	@Action(value = "/addModel")
	public void addModel() {
		model = cuserService.getModelsInfoBymodelname(modelname);
		if (model != null) {
			setResponseStr("error");
			return;
		} else {
			ModelsInfo model = new ModelsInfo();
			model.setModelname(modelname);
			model.setSearchname(searchname);
			model.setAddtime(new Date());
			cuserService.addObject(model);
			setResponseStr("success");
		}
	}

	/**
	 * 删除准教车型
	 */
	@Action(value = "/delModel")
	public void delModel() {
		model = cuserService.getModelsInfoBymodelname(modelname);
		if (model == null) {
			setResponseStr("error");
			return;
		} else {
			cuserService.delObject(model);
			setResponseStr("success");
		}
	}

	/**
	 * 修改准教车型
	 */
	@Action(value = "/editModel")
	public void editModel() {
		oldmodel = cuserService.getModelsInfoBymodelname(oldmodelname);
		model=cuserService.getModelsInfoBymodelname(editmodelname);
		if(model!=null){
			setResponseStr("error1");
		}else{
			if (oldmodel == null) {
				setResponseStr("error");
			} else {
				if(searchname==null){
					oldmodel.setSearchname(oldmodel.getSearchname());
				}else{
					oldmodel.setSearchname(searchname);
				}
				if(editmodelname==null){
					oldmodel.setModelname(oldmodel.getModelname());
				}else{
					oldmodel.setModelname(editmodelname);
				}
				cuserService.updateObject(oldmodel);
				setResponseStr("success");
			}
		}
	}

	/**
	 * 得到教练等级列表
	 *
	 * @return
	 */
	@Action(value = "/getallLevellist", results = { @Result(name = SUCCESS, location = "/levellist.jsp") })
	public String getallLevellist() {
		levellist = cuserService.getLevellist();
		return SUCCESS;
	}

	/**
	 * 添加教练等级
	 */
	@Action(value = "/addLevel")
	public void addLevel() {
		levelInfo = cuserService.getLevelInfoBylevelname(levelname);
		if (levelInfo != null) {
			setResponseStr("error");
			return;
		} else {
			CoachLevelInfo levelInfo = new CoachLevelInfo();
			levelInfo.setLevelname(levelname);
			levelInfo.setAddtime(new Date());
			cuserService.addObject(levelInfo);
			setResponseStr("success");
		}
	}

	/**
	 * 删除教练等级
	 */
	@Action(value = "/delLevel")
	public void delLevel() {
		levelInfo = cuserService.getLevelInfoBylevelname(levelname);
		if (levelInfo == null) {
			setResponseStr("error");
		} else {
			cuserService.delObject(levelInfo);
			setResponseStr("success");
		}
	}

	/**
	 * 修改教练等级
	 */
	@Action(value = "/editLevel")
	public void editLevel() {
		levelInfo = cuserService.getLevelInfoBylevelname(oldlevelname);
		if (levelInfo == null) {
			setResponseStr("error");
		} else {
			levelInfo.setLevelname(newlevelname);
			cuserService.updateObject(levelInfo);
			setResponseStr("success");
		}
	}

	/**
	 * 得到投诉原因
	 *
	 * @return
	 */
	@Action(value = "/getComplaintlist", results = { @Result(name = SUCCESS, location = "/complaintlist.jsp") })
	public String getComplaintlist() {
		if (searchtype == 1) {
			complaintSetInfolist = cuserService.getComplaintSetByType(searchtype);
			return SUCCESS;
		} else if (searchtype == 2) {
			complaintSetInfolist = cuserService.getComplaintSetByType(searchtype);
			return SUCCESS;
		} else {
			complaintSetInfolist = cuserService.getComplaintSetInfolist();
			return SUCCESS;
		}
	}

	/**
	 * 添加投诉
	 */
	@Action(value = "/addComplaintSetInfo")
	public void addComplaintSetInfo() {
		if (content != null && type != 0) {
			complaintSetInfo = new ComplaintSetInfo();
			complaintSetInfo.setContent(content);
			complaintSetInfo.setAddtime(new Date());
			complaintSetInfo.setType(type);
			cuserService.addObject(complaintSetInfo);
			setResponseStr("success");
		} else {
			setResponseStr("error");
		}
	}

	/**
	 * 删除投诉原因
	 */
	@Action(value = "/delComplaintSetInfo")
	public void delComplaintSetInfo() {
		complaintSetInfo = cuserService.getComplaintSetInfoByid(setid);
		if (complaintSetInfo == null) {
			setResponseStr("error");
		} else {
			cuserService.delObject(complaintSetInfo);
			setResponseStr("success");
		}
	}

	/**
	 * 修改投诉原因
	 */
	@Action(value = "/editComplaintSet")
	public void editComplaintSet() {
		complaintSetInfo = cuserService.getComplaintSetInfoByid(setid);
		if (complaintSetInfo == null) {
			setResponseStr("error");
		} else {
			complaintSetInfo.setContent(newcontent);
			cuserService.updateObject(complaintSetInfo);
			setResponseStr("success");
		}
	}

	/**
	 * 得到科目信息列表
	 * @return
	 */
	@Action(value = "/getSubjectlists", results = { @Result(name = SUCCESS, location = "/subjectlist.jsp") })
	public String getSubjectlists() {
		subjectlist = cuserService.getSubjectInfo();
		return SUCCESS;
	}

	/**
	 * 删除科目信息
	 */
	@Action(value = "/delSubject")
	public void delSubject() {
		subjectInfo = cuserService.getSubjectById(subjectid);
		if (subjectInfo == null) {
			setResponseStr("error");
		} else {
			cuserService.delObject(subjectInfo);
			setResponseStr("success");
		}
	}

	/**
	 * 添加科目信息
	 */
	@Action(value = "/addSubject")
	public void addSubject() {
		subjectInfo = cuserService.getSubjectByName(addsubjectname);
		if (subjectInfo != null) {
			setResponseStr("error");
		} else {
			subjectInfo = new CsubjectInfo();
			subjectInfo.setSubjectname(addsubjectname);
			subjectInfo.setAddtime(new Date());
			cuserService.addObject(subjectInfo);
			setResponseStr("success");
		}
	}

	/**
	 *修改科目信息
	 */
	@Action(value = "/editSubject")
	public void editSubject() {
		subjectInfo = cuserService.getSubjectByName(oldsubjectname);
		newsubjectInfo = cuserService.getSubjectByName(editsubjectname);
		if (subjectInfo == null) {
			setResponseStr("error");
		} else if (newsubjectInfo == null) {
			subjectInfo.setSubjectname(editsubjectname);
			cuserService.updateObject(subjectInfo);
			setResponseStr("success");
		} else {
			setResponseStr("error1");
		}
	}

	/**
	 * 获取节假日
	 * @return
	 */
	@Action(value = "/getHolidays", results = { @Result(name = SUCCESS, location = "/holidays.jsp") })
	public String getHolidays(){
		systemSetInfo=cuserService.getSystemSetInfo();
		return SUCCESS;
	}

	@Action(value = "/getHolidaysday")
	public void getHolidaysday(){
		String[] nowholiday=newholidays.split(",");
		int count=0;
		String[] nowclickholiday=clickholiday.split(",");
		for (int i = 0; i < nowholiday.length; i++) {
			for (int j = 0; j < nowclickholiday.length; j++) {
				if(nowclickholiday[j].equals(nowholiday[i])){
					count++;
				}
			}
		}
		if(count==0){
			setResponseStr("success");
		}else{
			setResponseStr("error");
		}
	}

	/**
	 * 更新节假日
	 */
	@Action(value = "/updateHolidays")
	public void updateHolidays(){
		if(!CommonUtils.isEmptyString(newholidays)){
			systemSetInfo=cuserService.getSystemSetInfoByid(dataid);
			if(systemSetInfo==null){
				SystemSetInfo systemSetInfos=new SystemSetInfo();
				systemSetInfos.setTime_cancel(0);
				if(!CommonUtils.isEmptyString(newholidays)){
					systemSetInfos.setHolidays(newholidays);
				}
				cuserService.addObject(systemSetInfos);
				setResponseStr("success");
			}else{
				systemSetInfo.setHolidays(newholidays);
				cuserService.updateObject(systemSetInfo);
				setResponseStr("success");
			}}else{
			setResponseStr("error");
		}
	}

	/**
	 * @return
	 */
	@Action(value = "/getTimeCancel", results = { @Result(name = SUCCESS, location = "/timecancel.jsp") })
	public String getTimeCancel(){
		systemSetInfo=cuserService.getSystemSetInfo();
		return SUCCESS;
	}
	
	/**显示重置教练开课状态页面
	 * @return
	 */
	@Action(value = "/showResetCoachCoursestate", results = { @Result(name = SUCCESS, location = "/restcoachstate.jsp") })
	public String showResetCoachCoursestate(){
		return SUCCESS;
	}
	/**重置教练开课状态
	 * @return
	 */
	@Action(value = "/resetCoachCoursestate")
	public void resetCoachCoursestate(){
		cuserService.resetCoachCoursestate();
		Map map=new HashMap();
		map.put("code", "0");
		map.put("message", "重置成功!");
		strToJson(map);
	}
	/**打开广告设置
	 * @return
	 */
	@Action(value = "/gotosetAdvertisement", results = { @Result(name = SUCCESS, location = "/setadvertisement.jsp") })
	public String gotosetAdvertisement(){
		return SUCCESS;
	}
	
	/**进行广告设置
	 * @return
	 */
	@Action(value = "/setAdvertisement", results = { @Result(name = SUCCESS, location = "/setadvertisement.jsp") })
	public String setAdvertisement(){
		try {
			File f=new File("ad_img.jpg");		
			FileUtils.copyFile(ad_img, f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	/**
	 *
	 */
	@Action(value = "/editValue")
	public void editTime(){
		systemSetInfo=cuserService.getSystemSetInfoByid(dataid);
		if(systemSetInfo==null){
			setResponseStr("error");
		}else{
			if(colname.equals("time_cancel"))
				systemSetInfo.setTime_cancel(Integer.parseInt(editvalue));
			else if(colname.equals("s_can_up"))
				systemSetInfo.setS_can_up(Integer.parseInt(editvalue));
			else if(colname.equals("s_can_down"))
				systemSetInfo.setS_can_down(Integer.parseInt(editvalue));
			else if(colname.equals("s_order_end"))
				systemSetInfo.setS_order_end(Integer.parseInt(editvalue));
			else if(colname.equals("order_pull"))
				systemSetInfo.setOrder_pull(Integer.parseInt(editvalue));
			else if(colname.equals("s_register_money"))
				systemSetInfo.setS_register_money(new BigDecimal(editvalue));
			else if(colname.equals("c_register_money"))
				systemSetInfo.setC_register_money(new BigDecimal(editvalue));
			else if(colname.equals("c_register_gmoney"))
				systemSetInfo.setC_register_gmoney(new BigDecimal(editvalue));
			else if(colname.equals("s_default_coment"))
				systemSetInfo.setS_default_coment(editvalue);
			else if(colname.equals("c_default_coment"))
				systemSetInfo.setC_default_coment(editvalue);
			else if(colname.equals("login_vcode_time"))
				systemSetInfo.setLogin_vcode_time(Integer.parseInt(editvalue));
			else if(colname.equals("book_day_max"))
				systemSetInfo.setBook_day_max(Integer.parseInt(editvalue));
			else if(colname.equals("coach_default_price"))
				systemSetInfo.setCoach_default_price(Integer.parseInt(editvalue));
			else if(colname.equals("coach_default_subject"))
				systemSetInfo.setCoach_default_subject(Integer.parseInt(editvalue));
			else if(colname.equals("can_use_coupon_count"))
				systemSetInfo.setCan_use_coupon_count(Integer.parseInt(editvalue));
			else if(colname.equals("can_use_diff_coupon"))
				systemSetInfo.setCan_use_diff_coupon(Integer.parseInt(editvalue));
//			else if(colname.equals("advertisement_flag"))
//				systemSetInfo.setAdvertisement_flag(Integer.parseInt(editvalue));
			else if(colname.equals("coursedate_flag"))
				systemSetInfo.setCoursedate(Integer.parseInt(editvalue));
			else if(colname.equals("coach_advertisement_flag"))
				systemSetInfo.setCoach_advertisement_flag(Integer.parseInt(editvalue));
			else if(colname.equals("coach_advertisement_urlflag"))
				systemSetInfo.setCoach_advertisement_url(editvalue);
			else if(colname.equals("coach_advertisement_imgflag"))
				systemSetInfo.setCoach_advertisement_img(editvalue);
			else if(colname.equals("student_advertisement_flag"))
				systemSetInfo.setStudent_advertisement_flag(Integer.parseInt(editvalue));
			else if(colname.equals("student_advertisement_urlflag"))
				systemSetInfo.setStudent_advertisement_url(editvalue);
			else if(colname.equals("student_advertisement_imgflag"))
				systemSetInfo.setStudent_advertisement_img(editvalue);
			else if(colname.equals("crewardamount_flag"))
				systemSetInfo.setCrewardamount(new BigDecimal(editvalue));
			else if(colname.equals("orewardamount_flag"))
				systemSetInfo.setOrewardamount(new BigDecimal(editvalue));
			cuserService.updateObject(systemSetInfo);
			setResponseStr("success");
		}
	}

	@Action(value = "/jumpp", results = { @Result(name = SUCCESS, location = "/newday.jsp") })
	public String jumpp(){
		return SUCCESS;
	}


	public ComplaintSetInfo getComplaintSetInfo() {
		return complaintSetInfo;
	}

	public void setComplaintSetInfo(ComplaintSetInfo complaintSetInfo) {
		this.complaintSetInfo = complaintSetInfo;
	}

	public List<ModelsInfo> getModelslist() {
		return modelslist;
	}

	public void setModelslist(List<ModelsInfo> modelslist) {
		this.modelslist = modelslist;
	}

	public ModelsInfo getModel() {
		return model;
	}

	public void setModel(ModelsInfo model) {
		this.model = model;
	}

	public ModelsInfo getOldmodel() {
		return oldmodel;
	}

	public void setOldmodel(ModelsInfo oldmodel) {
		this.oldmodel = oldmodel;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public String getModelname() {
		return modelname;
	}

	public void setModelname(String modelname) {
		this.modelname = modelname;
	}

	public String getOldmodelname() {
		return oldmodelname;
	}

	public void setOldmodelname(String oldmodelname) {
		this.oldmodelname = oldmodelname;
	}

	public String getEditmodelname() {
		return editmodelname;
	}

	public void setEditmodelname(String editmodelname) {
		this.editmodelname = editmodelname;
	}

	public long getTotal() {
		return total;
	}

	public void setLevellist(List<CoachLevelInfo> levellist) {
		this.levellist = levellist;
	}

	public List<CoachLevelInfo> getLevellist() {
		return levellist;
	}

	public CoachLevelInfo getLevelInfo() {
		return levelInfo;
	}

	public void setLevelInfo(CoachLevelInfo levelInfo) {
		this.levelInfo = levelInfo;
	}

	public String getLevelname() {
		return levelname;
	}

	public void setLevelname(String levelname) {
		this.levelname = levelname;
	}

	public String getOldlevelname() {
		return oldlevelname;
	}

	public void setOldlevelname(String oldlevelname) {
		this.oldlevelname = oldlevelname;
	}

	public String getNewlevelname() {
		return newlevelname;
	}

	public void setNewlevelname(String newlevelname) {
		this.newlevelname = newlevelname;
	}

	public List<ComplaintSetInfo> getComplaintSetInfolist() {
		return complaintSetInfolist;
	}

	public void setComplaintSetInfolist(List<ComplaintSetInfo> complaintSetInfolist) {
		this.complaintSetInfolist = complaintSetInfolist;
	}

	public int getSearchtype() {
		return searchtype;
	}

	public void setSearchtype(int searchtype) {
		this.searchtype = searchtype;
	}

	public int getSetid() {
		return setid;
	}

	public void setSetid(int setid) {
		this.setid = setid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getNewcontent() {
		return newcontent;
	}

	public void setNewcontent(String newcontent) {
		this.newcontent = newcontent;
	}

	public List<CsubjectInfo> getSubjectlist() {
		return subjectlist;
	}

	public void setSubjectlist(List<CsubjectInfo> subjectlist) {
		this.subjectlist = subjectlist;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getSubjectid() {
		return subjectid;
	}

	public void setSubjectid(int subjectid) {
		this.subjectid = subjectid;
	}

	public CsubjectInfo getSubjectInfo() {
		return subjectInfo;
	}

	public void setSubjectInfo(CsubjectInfo subjectInfo) {
		this.subjectInfo = subjectInfo;
	}

	public String getAddsubjectname() {
		return addsubjectname;
	}

	public void setAddsubjectname(String addsubjectname) {
		this.addsubjectname = addsubjectname;
	}

	public String getEditsubjectname() {
		return editsubjectname;
	}

	public void setEditsubjectname(String editsubjectname) {
		this.editsubjectname = editsubjectname;
	}

	public String getOldsubjectname() {
		return oldsubjectname;
	}

	public void setOldsubjectname(String oldsubjectname) {
		this.oldsubjectname = oldsubjectname;
	}

	public CsubjectInfo getNewsubjectInfo() {
		return newsubjectInfo;
	}

	public void setNewsubjectInfo(CsubjectInfo newsubjectInfo) {
		this.newsubjectInfo = newsubjectInfo;
	}


	public SystemSetInfo getSystemSetInfo() {
		return systemSetInfo;
	}


	public void setSystemSetInfo(SystemSetInfo systemSetInfo) {
		this.systemSetInfo = systemSetInfo;
	}


	public String getNewholidays() {
		return newholidays;
	}


	public void setNewholidays(String newholidays) {
		this.newholidays = newholidays;
	}


	public int getDataid() {
		return dataid;
	}


	public void setDataid(int dataid) {
		this.dataid = dataid;
	}


	public String getEditvalue() {
		return editvalue;
	}

	public void setEditvalue(String editvalue) {
		this.editvalue = editvalue.trim();
	}


	public String getClickholiday() {
		return clickholiday;
	}


	public void setClickholiday(String clickholiday) {
		this.clickholiday = clickholiday;
	}



	public List<TeachcarInfo> getTeachcarInfolist() {
		return teachcarInfolist;
	}



	public void setTeachcarInfolist(List<TeachcarInfo> teachcarInfolist) {
		this.teachcarInfolist = teachcarInfolist;
	}


	public TeachcarInfo getTeachcar() {
		return teachcar;
	}


	public void setTeachcar(TeachcarInfo teachcar) {
		this.teachcar = teachcar;
	}


	public TeachcarInfo getNewteachcar() {
		return newteachcar;
	}


	public void setNewteachcar(TeachcarInfo newteachcar) {
		this.newteachcar = newteachcar;
	}

	public int getChange_id() {
		return change_id;
	}

	public void setChange_id(int change_id) {
		this.change_id = change_id;
	}

	public String getColname() {
		return colname;
	}

	public void setColname(String colname) {
		this.colname = colname;
	}

	public String getSearchname() {
		return searchname;
	}

	public void setSearchname(String searchname) {
		this.searchname = searchname;
	}

	

	public File getAd_img() {
		return ad_img;
	}

	public void setAd_img(File ad_img) {
		this.ad_img = ad_img;
	}

	public String getAd_url() {
		return ad_url;
	}

	public void setAd_url(String ad_url) {
		this.ad_url = ad_url;
	}


}