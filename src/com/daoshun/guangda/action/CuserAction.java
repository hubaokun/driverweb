package com.daoshun.guangda.action;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.daoshun.common.CommonUtils;
import com.daoshun.common.Constant;
import com.daoshun.common.QueryResult;
import com.daoshun.guangda.NetData.CoachInfoForExcel;
import com.daoshun.guangda.pojo.BalanceCoachInfo;
import com.daoshun.guangda.pojo.CApplyCashInfo;
import com.daoshun.guangda.pojo.CoachLevelInfo;
import com.daoshun.guangda.pojo.CuserInfo;
import com.daoshun.guangda.pojo.DriveSchoolInfo;
import com.daoshun.guangda.pojo.ModelsInfo;
import com.daoshun.guangda.pojo.TeachcarInfo;
import com.daoshun.guangda.service.IBaseService;
import com.daoshun.guangda.service.ICUserService;
import com.daoshun.guangda.service.IDriveSchoolService;

@ParentPackage("default")
@Controller
@Scope("prototype")
public class CuserAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Resource
	private ICUserService cuserService;
	
	@Resource
	private IBaseService baseService;
	
	@Resource
	private IDriveSchoolService driveSchoolService;

	private List<CuserInfo> cuserlist;

	private List<CApplyCashInfo> applycashlist;

	private List<BalanceCoachInfo> balancecoachlist;

	private List<DriveSchoolInfo> driveSchoollist;

	private CuserInfo cuser;

	private String coachid;

	// 教练提现申请id
	private int applyid;

	// 提现金额
	private String amount;

	private int index;

	// 搜索比对金额
	private String inputamount;

	// 搜索的开始时间
	private String minsdate;

	// 搜索的结束时间
	private String maxsdate;

	private File addVersion; // 上传的版本文件

	private String addVersionFileName;

	//
	private long total;

	private Integer pageIndex = 1;

	private Integer pageCount = 0;

	private String keyword;

	// 审核类型
	private int checknum;

	// 冻结金额
	private double gmoney;

	// 教练等级
	private int level;

	// 审核来源类型
	private int wherecheck;

	// 搜索关键字
	private String searchname;

	private String searchphone;

	private Integer driveSchoolname = 0;

	private String carlicense;

	private Integer state;
	
	private Integer checkstate;

	// 是否可以取消订单
	private int canceltype;

	private int coachid_frozen;

	private Date frozenstart;

	private Date frozenend;

	private int change_id;

	private int coachid_unfrozen;

	// 修改个人详细
	private String realname;

	private String address;

	private String newlevel;

	private String years;

	private List<DriveSchoolInfo> driveschoollist;

	private int schoolid;
	
	private String school_id;
	
	
	private String coachdate;

	// 修改教练个人详细
	private String editrealname;

	private Integer editgender;

	private Integer edityears;

	private String editbirthday;

	private String editcity;

	private String editaddress;

	private String editurgent_person;

	private String editurgent_phone;

	private String editselfval;

	private Integer editlevel;

	private Date editaddtime;

	private float editscore;

	private String editid_cardnum;

	private String editid_cardexptime;

	private String editcoach_cardnum;

	private String editcoach_cardexptime;

	private String editdrive_cardnum;

	private String editdrive_cardexptime;

	private String editcar_cardnum;

	private String editcar_cardexptime;

	private String editcarmodel;

	private String editcarlicense;
	
	private String editid_cardpicfurlFileName;
	
	private File editid_cardpicfurl;
	
	private String editid_cardpicburlFileName;
	
	private File editid_cardpicburl;
	
	private String editcoach_cardpicurlFileName;
	
	private File editcoach_cardpicurl;
	
	private String editdrive_cardpicurlFileName;
	
	private File editdrive_cardpicurl;
	
	private String editcar_cardpicfurlFileName;
	
	private File editcar_cardpicfurl;
	
	private String editcar_cardpicburlFileName;
	
	private File editcar_cardpicburl;
	
	private String[] checkbox;
	
	private BigDecimal allmoney;
	
	private Integer alllevel;
	
	private Date allfrozenstart;
	
	private Date allfrozenend;
	
	private BigDecimal money;
	
	private List<ModelsInfo> modellist;
	
	private List<ModelsInfo> coachmodellist;
	
	private String [] coachmodelid;
	
	private List<TeachcarInfo> teachcarlist;
	
	private int changetype;
	
	private String schoolkeyword;
	
	private List<CoachLevelInfo> levelist;
	
	private int frozecoachtype;

	/**
	 * 得到教练列表
	 * 
	 * @return
	 */
	@Action(value = "/getCoachlist", results = { @Result(name = SUCCESS, location = "/coachdetail.jsp") })
	public String cuserlist() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		int pagesize = CommonUtils.parseInt(String.valueOf(session.getAttribute("pagesize")), 20);
//		pagesize = CommonUtils.parseInt(String.valueOf(request.getAttribute("pagesize")), pagesize);
		driveSchoollist = cuserService.getDriveSchoolInfo();
		levelist = cuserService.getLevellist();
		QueryResult<CuserInfo> result = cuserService.getAllCuserInfos(pageIndex, pagesize);
		total = result.getTotal();
		cuserlist = result.getDataList();
		for (int i = 0; i < cuserlist.size(); i++) {
			if (cuserlist.get(i).getIsfrozen() == 1 && cuserlist.get(i).getFrozenend() != null) {
				Date today = new Date();
				Calendar nowtime = Calendar.getInstance();
				nowtime.setTime(today);
				nowtime.set(Calendar.HOUR_OF_DAY, 0);
				nowtime.set(Calendar.MINUTE, 0);
				nowtime.set(Calendar.SECOND, 0);
				nowtime.set(Calendar.MILLISECOND, 0);
				today = nowtime.getTime();
				if (today.after(cuserlist.get(i).getFrozenend())) {
					cuserlist.get(i).setIsfrozen(0);
					cuserService.updateObject(cuserlist.get(i));
				}
			}
			if (!CommonUtils.isEmptyString(cuserlist.get(i).getBirthday())) {
				int age = cuserService.getCoachAgeByid(cuserlist.get(i).getCoachid());
				cuserlist.get(i).setAge(age);
			}
		}
		pageCount = ((int) result.getTotal() + pagesize - 1) / pagesize;
		if (pageIndex > 1) {
			if (cuserlist == null || cuserlist.size() == 0) {
				pageIndex--;
				cuserlist();
			}
		}
		return SUCCESS;
	}

	/**
	 * 审核通过、或者不通过 根据传过来的checknum
	 * 
	 * @return
	 */
	@Action(value = "/listcheckPass")
	public void checkPass() {
		cuserService.checkPass(coachid, checknum);
		CuserInfo cuserInfo = cuserService.getCuserByCoachid(coachid);
		if (wherecheck == 1) {
			if (cuserInfo.getState() == checknum) {
				setResponseStr("success");
			} else {
				setResponseStr("error");
			}
		} else if (wherecheck == 2) {
			if (cuserInfo.getState() == checknum) {
				setResponseStr("success1");
			} else {
				setResponseStr("error1");
			}
		}
	}

	@Action(value = "/jumpDataToLead", results = { @Result(name = SUCCESS, location = "/datatolead.jsp") })
	public String jumpDataToLead() {
		return SUCCESS;
	}

	@Action(value = "/dataToLead", results = { @Result(name = SUCCESS, location = "/datatolead.jsp") })
	public String dataToLead() {
		int index = 0;
		String realpath = request.getSession().getServletContext().getRealPath("/");
		String extension = addVersionFileName.substring(addVersionFileName.lastIndexOf(".")).toLowerCase();
		String message = null;
		if (!extension.equals(".xls")) {
			message = "请上传excel文件!如果是2007以上版本excel请另存为(*.xls)文件再上传";
		} else {
			String severPicName = CommonUtils.uploadApp(addVersion, realpath, addVersionFileName);
			String file = realpath + severPicName;
			WorkbookSettings workbookSettings = new WorkbookSettings();
			workbookSettings.setEncoding("ISO-8859-1");
			Workbook book;
			try {
				book = Workbook.getWorkbook(new File(file), workbookSettings);// 获取excel
				Sheet sheet = book.getSheet(0);
				int col = sheet.getColumns();//总列数
				int row = sheet.getRows();//总行数
				for(int i = 1;i<row;i++){
					int id = 0;
					String password = "";
					String phone = null;
					String telphone = null;
					String id_cardnum = null;
					String id_cardexptime = null;
					String coach_cardnum = null;
					String coach_cardexptime = null;
					String drive_cardnum = null;
					String drive_cardexptime = null;
					String car_cardnum = null;
					String car_cardexptime = null;
					String modelid = null;
					String carmodel = null;
					int carmodelid = 0;
					String carlicense = null;
					String drive_school = null;
					int gender = 0;
					String birthday = null;
					String city = null;
					String address = null;
					String realname = null;
					String urgent_person = null;
					String urgent_phone = null;
					int years = 0;
					BigDecimal money = new BigDecimal(0);
					BigDecimal gmoney = new BigDecimal(0);
					BigDecimal fmoney = new BigDecimal(0);
					BigDecimal price = new BigDecimal(0);
					int subjectdef = 0;
					int state = 3;
					String selfeval = null;
					int level = 0;
					int newtasknoti = 0;
					int cancancel = 0;
					int totaltime = 0;
					float score = 0;
					int isfrozen = 0;
					for (int j =0;j<col;j++){
						String title = sheet.getCell(j, 0).getContents();
						if(title.equals("教练手机号码")){
							phone = sheet.getCell(j, i).getContents();
						}else if(title.equals("身份证号码")){
							id_cardnum = sheet.getCell(j, i).getContents();
						}else if(title.equals("真实姓名")){
							realname = sheet.getCell(j, i).getContents();
						}else if(title.equals("教练的联系电话")){
							 telphone = sheet.getCell(j, i).getContents();
						}else if(title.equals("身份证到期日期")){
							 id_cardexptime = sheet.getCell(j, i).getContents().replaceAll("/", "-");
						}else if(title.equals("教练证号")){
							 coach_cardnum = sheet.getCell(j, i).getContents();
						}else if(title.equals("教练证到期日期")){
							 coach_cardexptime = sheet.getCell(j, i).getContents().replaceAll("/", "-");
						}else if(title.equals("驾驶证号")){
							 drive_cardnum = sheet.getCell(j, i).getContents();
						}else if(title.equals("驾驶证到期日期")){
							 drive_cardexptime = sheet.getCell(j, i).getContents().replaceAll("/", "-");
						}else if(title.equals("车辆年检证号")){
							 car_cardnum = sheet.getCell(j, i).getContents();
						}else if(title.equals("车辆行驶证到期日期")){
							 car_cardexptime = sheet.getCell(j, i).getContents().replaceAll("/", "-");
						}else if(title.equals("教练准教车型ID")){
							 modelid = sheet.getCell(j, i).getContents();
						}else if(title.equals("教学用车型号")){
							 carmodel = sheet.getCell(j, i).getContents();
						}else if(title.equals("教学用车型号ID")){
							if(CommonUtils.parseInt(sheet.getCell(j, i).getContents(), 0)!=0){
								carmodelid = CommonUtils.parseInt(sheet.getCell(j, i).getContents(), 0);
							}
						}else if(title.equals("教学用车牌照")){
							 carlicense = sheet.getCell(j, i).getContents();
						}else if(title.equals("驾校名称")){
							 drive_school = sheet.getCell(j, i).getContents();
						}else if(title.equals("性别")){
							if(CommonUtils.parseInt(sheet.getCell(j, i).getContents(), 0)!=0){
								gender = CommonUtils.parseInt(sheet.getCell(j, i).getContents(), 0);
							}
						}else if(title.equals("出生日期")){
							 birthday = sheet.getCell(j, i).getContents().replaceAll("/", "-");
						}else if(title.equals("教练所属城市")){
							 city = sheet.getCell(j, i).getContents();
						}else if(title.equals("教练联系地址")){
							 address = sheet.getCell(j, i).getContents();
						}else if(title.equals("紧急联系人姓名")){
							 urgent_person = sheet.getCell(j, i).getContents();
						}else if(title.equals("紧急联系人电话")){
							 urgent_phone = sheet.getCell(j, i).getContents();
						}else if(title.equals("教龄")){
							if(CommonUtils.parseInt(sheet.getCell(j, i).getContents(), 0)!=0){
								years = CommonUtils.parseInt(sheet.getCell(j, i).getContents(), 0);
							}
						}else if(title.equals("可提现余额")){
							if(CommonUtils.parseDouble(sheet.getCell(j, i).getContents(), 0d)!=0d){
								money = new BigDecimal(CommonUtils.parseDouble(sheet.getCell(j, i).getContents(), 0d));
							}
						}else if(title.equals("保证金")){
							if(CommonUtils.parseDouble(sheet.getCell(j, i).getContents(), 0d)!=0d){
								gmoney = new BigDecimal(CommonUtils.parseDouble(sheet.getCell(j, i).getContents(), 0d));
							}
						}else if(title.equals("冻结金额")){
							if(CommonUtils.parseDouble(sheet.getCell(j, i).getContents(), 0d)!=0d){
								fmoney = new BigDecimal(CommonUtils.parseDouble(sheet.getCell(j, i).getContents(), 0d));
							}
						}else if(title.equals("教练教车单价")){
							if(CommonUtils.parseDouble(sheet.getCell(j, i).getContents(), 0d)!=0d){
								price = new BigDecimal(CommonUtils.parseDouble(sheet.getCell(j, i).getContents(), 0d));
							}
						}else if(title.equals("教练默认的教学科目")){
							if(CommonUtils.parseInt(sheet.getCell(j, i).getContents(), 0)!=0){
								subjectdef = CommonUtils.parseInt(sheet.getCell(j, i).getContents(), 0);
							}
						}else if(title.equals("教练状态")){
							if(CommonUtils.parseInt(sheet.getCell(j, i).getContents(), 0)!=0){
								state = CommonUtils.parseInt(sheet.getCell(j, i).getContents(), 0);
							}
						}else if(title.equals("教练自我评价")){
							 selfeval = sheet.getCell(j, i).getContents();
						}else if(title.equals("教练等级")){
							if(CommonUtils.parseInt(sheet.getCell(j, i).getContents(), 0)!=0){
								level = CommonUtils.parseInt(sheet.getCell(j, i).getContents(), 0);
							}
						}else if(title.equals("教练注册时间")){
							
						}else if(title.equals("接收到新任务是否提醒")){
							if(CommonUtils.parseInt(sheet.getCell(j, i).getContents(), 0)!=0){
								newtasknoti = CommonUtils.parseInt(sheet.getCell(j, i).getContents(), 0);
							}
						}else if(title.equals("教练是否可以设置订单可否取消")){
							if(CommonUtils.parseInt(sheet.getCell(j, i).getContents(), 0)!=0){
								cancancel = CommonUtils.parseInt(sheet.getCell(j, i).getContents(), 0);
							}
						}else if(title.equals("教练教学时长")){
							if(CommonUtils.parseInt(sheet.getCell(j, i).getContents(), 0)!=0){
								totaltime = CommonUtils.parseInt(sheet.getCell(j, i).getContents(), 0);
							}
						}else if(title.equals("综合评分")){
							if(CommonUtils.parseFloat(sheet.getCell(j, i).getContents(), 0)!=0){
								score = CommonUtils.parseFloat(sheet.getCell(j, i).getContents(), 0);
							}
						}else if(title.equals("教练是否被冻结")){
							if(CommonUtils.parseInt(sheet.getCell(j, i).getContents(), 0)!=0){
								isfrozen = CommonUtils.parseInt(sheet.getCell(j, i).getContents(), 0);
							}
						}
					}
					CuserInfo coach = new CuserInfo();
					CuserInfo coach1 = cuserService.getCuserByPhone(phone);
					if (coach1 == null) {
						coach.setPhone(phone);
					} else {
						if(password==""){
							coach1.setPassword(coach1.getPassword());
						}else{
							coach1.setPassword(CommonUtils.MD5(password));
						}
						if(id_cardnum==null){
							coach1.setId_cardnum(coach1.getId_cardnum());
						}else{
							CuserInfo coach2 = cuserService.getCuserById_cardnum(id_cardnum);
							if (coach2 == null) {
								coach1.setId_cardnum(id_cardnum);
							} else {
								continue;
							}
						}
						if(coach_cardnum==null){
							coach1.setCoach_cardnum(coach1.getCoach_cardnum());
						}else{
							CuserInfo coach3 = cuserService.getCuserByCoach_cardnum(coach_cardnum);
							if (coach3 == null) {
								coach1.setCoach_cardnum(coach_cardnum);
							} else {
								continue;
							}
						}
						if(car_cardnum==null){
							coach1.setCar_cardnum(coach1.getCar_cardnum());
						}else{
							CuserInfo coach4 = cuserService.getCuserByCar_cardnum(car_cardnum);
							if (coach4 == null) {
								coach1.setCar_cardnum(car_cardnum);
							} else {
								continue;
							}
						}
						if(drive_cardnum==null){
							coach1.setDrive_cardnum(coach1.getDrive_cardnum());
						}else{
							CuserInfo coach5 = cuserService.getCuserByBrive_cardnum(drive_cardnum);
							if (coach5 == null) {
								coach1.setDrive_cardnum(drive_cardnum);
							} else {
								continue;
							}
						}
						if(telphone==null){
							coach1.setTelphone(coach1.getTelphone());
						}else{
							coach1.setTelphone(telphone);
						}
						if(id_cardexptime==null){
							coach1.setId_cardexptime(coach1.getId_cardexptime());
						}else{
							coach1.setId_cardexptime(id_cardexptime);
						}
						if(coach_cardexptime==null){
							coach1.setCoach_cardexptime(coach1.getCoach_cardexptime());
						}else{
							coach1.setCoach_cardexptime(coach_cardexptime);
						}
						if(drive_cardexptime==null){
							coach1.setDrive_cardexptime(coach1.getDrive_cardexptime());
						}else{
							coach1.setDrive_cardexptime(drive_cardexptime);
						}
						if(car_cardexptime==null){
							coach1.setCar_cardexptime(coach1.getCar_cardexptime());
						}else{
							coach1.setCar_cardexptime(car_cardexptime);
						}
						if(modelid==null){
							coach1.setModelid(coach1.getModelid());
						}else{
							coach1.setModelid(modelid);
						}
						if(carmodel==null){
							coach1.setCarmodel(coach1.getCarmodel());
						}else{
							coach1.setCarmodel(carmodel);
						}
						if(carmodelid==0){
							coach1.setCarmodelid(coach1.getCarmodelid());
						}else{
							coach1.setCarmodelid(carmodelid);
						}
						if(carlicense==null){
							coach1.setCarlicense(coach1.getCarlicense());
						}else{
							coach1.setCarlicense(carlicense);
						}
						if(drive_school==null){
							coach1.setDrive_school(coach1.getDrive_school());
						}else{
							coach1.setDrive_school(drive_school);
						}
						if(gender==0){
							coach1.setGender(coach1.getGender());
						}else{
							coach1.setGender(gender);
						}
						if(birthday==null){
							coach1.setBirthday(coach1.getBirthday());
						}else{
							coach1.setBirthday(birthday);
						}
						if(city==null){
							coach1.setCity(coach1.getCity());
						}else{
							coach1.setCity(city);
						}
						if(address==null){
							coach1.setAddress(coach1.getAddress());
						}else{
							coach1.setAddress(address);
						}
						if(realname==null){
							coach1.setRealname(coach1.getRealname());
						}else{
							coach1.setRealname(realname);
						}
						if(urgent_person==null){
							coach1.setUrgent_person(coach1.getUrgent_person());
						}else{
							coach1.setUrgent_person(urgent_person);
						}
						if(urgent_phone==null){
							coach1.setUrgent_phone(coach1.getUrgent_phone());
						}else{
							coach1.setUrgent_phone(urgent_phone);
						}
						if(years==0){
							coach1.setYears(coach1.getYears());
						}else{
							coach1.setYears(years);
						}
						if(money==new BigDecimal(0)){
							coach1.setMoney(coach1.getMoney());
						}else{
							coach1.setMoney(money);
						}
						if(gmoney==new BigDecimal(0)){
							coach1.setGmoney(coach1.getGmoney());
						}else{
							coach1.setGmoney(gmoney);
						}
						if(fmoney==new BigDecimal(0)){
							coach1.setFmoney(coach1.getFmoney());
						}else{
							coach1.setFmoney(fmoney);
						}
						if(price==new BigDecimal(0)){
							coach1.setPrice(coach1.getPrice());
						}else{
							coach1.setPrice(price);
						}
						if(subjectdef==0){
							coach1.setSubjectdef(coach1.getSubjectdef());
						}else{
							coach1.setSubjectdef(subjectdef);
						}
						if(state==0){
							coach1.setState(coach1.getState());
						}else{
							coach1.setState(state);
						}
						if(selfeval==null){
							coach1.setSelfeval(coach1.getSelfeval());
						}else{
							coach1.setSelfeval(selfeval);
						}
						if(level==0){
							coach1.setLevel(coach1.getLevel());
						}else{
							coach1.setLevel(level);
						}
						coach1.setAddtime(new Date());
						if(newtasknoti==0){
							coach1.setNewtasknoti(coach1.getNewtasknoti());
						}else{
							coach1.setNewtasknoti(newtasknoti);
						}
						if(cancancel==0){
							coach1.setCancancel(coach1.getCancancel());
						}else{
							coach1.setCancancel(cancancel);
						}
						if(totaltime==0){
							coach1.setTotaltime(coach1.getTotaltime());
						}else{
							coach1.setTotaltime(totaltime);
						}
						if(score==0){
							coach1.setScore(coach1.getScore());
						}else{
							coach1.setScore(score);
						}
						if(isfrozen==0){
							coach1.setIsfrozen(coach1.getIsfrozen());
						}else{
							coach1.setIsfrozen(isfrozen);
						}
						coach1.setId_cardpicb(0);
						coach1.setId_cardpicf(0);
						coach1.setCoach_cardpic(0);
						coach1.setDrive_cardpic(0);
						coach1.setCar_cardpicb(0);
						coach1.setCar_cardpicf(0);
						coach1.setRealpic(0);
						coach1.setAvatar(0);
						coach1.setIsquit(0);
						cuserService.updateCuser(coach1);
						index++;
						continue;
					}
					CuserInfo coach2 = cuserService.getCuserById_cardnum(id_cardnum);
					if (coach2 == null) {
						coach.setId_cardnum(id_cardnum);
					} else {
						continue;
					}
					CuserInfo coach3 = cuserService.getCuserByCoach_cardnum(coach_cardnum);
					if (coach3 == null) {
						coach.setCoach_cardnum(coach_cardnum);
					} else {
						continue;
					}
					CuserInfo coach4 = cuserService.getCuserByCar_cardnum(car_cardnum);
					if (coach4 == null) {
						coach.setCar_cardnum(car_cardnum);
					} else {
						continue;
					}
					CuserInfo coach5 = cuserService.getCuserByBrive_cardnum(drive_cardnum);
					if (coach5 == null) {
						coach.setDrive_cardnum(drive_cardnum);
					} else {
						continue;
					}
					coach.setPassword(CommonUtils.MD5(password));
					coach.setTelphone(telphone);
					coach.setId_cardexptime(id_cardexptime);
					coach.setCoach_cardexptime(coach_cardexptime);
					coach.setDrive_cardexptime(drive_cardexptime);
					coach.setCar_cardexptime(car_cardexptime);
					coach.setModelid(modelid);
					coach.setCarmodel(carmodel);
					coach.setCarmodelid(carmodelid);
					coach.setCarlicense(carlicense);
					coach.setDrive_school(drive_school);
					coach.setGender(gender);
					coach.setBirthday(birthday);
					coach.setCity(city);
					coach.setAddress(address);
					coach.setRealname(realname);
					coach.setUrgent_person(urgent_person);
					coach.setUrgent_phone(urgent_phone);
					coach.setYears(years);
					coach.setMoney(money);
					coach.setGmoney(gmoney);
					coach.setFmoney(fmoney);
					coach.setPrice(price);
					coach.setSubjectdef(subjectdef);
					coach.setState(state);
					coach.setSelfeval(selfeval);
					coach.setLevel(level);
					coach.setAddtime(new Date());
					coach.setNewtasknoti(newtasknoti);
					coach.setCancancel(cancancel);
					coach.setTotaltime(totaltime);
					coach.setScore(score);
					coach.setIsfrozen(isfrozen);
					coach.setId_cardpicb(0);
					coach.setId_cardpicf(0);
					coach.setCoach_cardpic(0);
					coach.setDrive_cardpic(0);
					coach.setCar_cardpicb(0);
					coach.setCar_cardpicf(0);
					coach.setRealpic(0);
					coach.setAvatar(0);
					coach.setIsquit(0);
					cuserService.addCuser(coach);
					index++;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			message = "添加成功或修改了"+index+"条数据！";
			CommonUtils.deleteFile(file);
		}
		request.setAttribute("message", message);
		return SUCCESS;
	}

	/**
	 * 教练信息导出到Exsel
	 */
	@Action(value = "/dataExport")
	public void dataExport() {
		cuserlist = cuserService.getCoachlist();
		List<CoachInfoForExcel> excellist = new ArrayList<CoachInfoForExcel>();
		String[] data = coachdate.split(",");
		for (CuserInfo coach : cuserlist) {
			CoachInfoForExcel coachexcel = new CoachInfoForExcel();
			for(int i=0;i<data.length;i++){
				coachexcel.setId(coach.getCoachid());
				coachexcel.setPhone(coach.getPhone());
				coachexcel.setId_cardnum(coach.getId_cardnum());
				coachexcel.setRealname(coach.getRealname());
				if(data[i].equals("0")){
					coachexcel.setTelphone(coach.getTelphone());
				}else if(data[i].equals("1")){
					coachexcel.setId_cardexptime(coach.getId_cardexptime());
				}else if(data[i].equals("2")){
					coachexcel.setCoach_cardnum(coach.getCoach_cardnum());
				}else if(data[i].equals("3")){
					coachexcel.setCoach_cardexptime(coach.getCoach_cardexptime());
				}else if(data[i].equals("4")){
					coachexcel.setDrive_cardnum(coach.getDrive_cardnum());
				}else if(data[i].equals("5")){
					coachexcel.setDrive_cardexptime(coach.getDrive_cardexptime());
				}else if(data[i].equals("6")){
					coachexcel.setCar_cardnum(coach.getCar_cardnum());
				}else if(data[i].equals("7")){
					coachexcel.setCar_cardexptime(coach.getCar_cardexptime());
				}else if(data[i].equals("8")){
					coachexcel.setModelid(coach.getModelid());
				}else
				if(data[i].equals("9")){
					coachexcel.setCarmodel(coach.getCarmodel());
				}else
				if(data[i].equals("10")){
					coachexcel.setCarmodelid(coach.getCarmodelid());
				}else
				if(data[i].equals("11")){
					coachexcel.setCarlicense(coach.getCarlicense());
				}else
				if(data[i].equals("12")){
					coachexcel.setDrive_school(coach.getDrive_school());
				}else
				if(data[i].equals("13")){
					coachexcel.setGender(coach.getGender());
				}else
				if(data[i].equals("14")){
					coachexcel.setBirthday(coach.getBirthday());
				}else
				if(data[i].equals("15")){
					coachexcel.setCity(coach.getCity());
				}else
				if(data[i].equals("16")){
					coachexcel.setAddress(coach.getAddress());
				}else
				if(data[i].equals("17")){
					coachexcel.setUrgent_person(coach.getUrgent_person());
				}else
				if(data[i].equals("18")){
					coachexcel.setUrgent_phone(coach.getUrgent_phone());
				}else
				if(data[i].equals("19")){
					coachexcel.setYears(coach.getYears());
				}else
				if(data[i].equals("20")){
					coachexcel.setMoney(coach.getMoney().doubleValue());
				}else
				if(data[i].equals("21")){
					coachexcel.setGmoney(coach.getGmoney().doubleValue());
				}else
				if(data[i].equals("22")){
					coachexcel.setFmoney(coach.getFmoney().doubleValue());
				}else
				if(data[i].equals("23")){
					coachexcel.setPrice(coach.getPrice().doubleValue());
				}else
				if(data[i].equals("24")){
					coachexcel.setSubjectdef(coach.getSubjectdef());
				}else
				if(data[i].equals("25")){
					coachexcel.setState(coach.getState());
				}else
				if(data[i].equals("26")){
					coachexcel.setSelfeval(coach.getSelfeval());
				}else
				if(data[i].equals("27")){
					coachexcel.setLevel(coach.getLevel());
				}else
				if(data[i].equals("28")){
					coachexcel.setAddtime(coach.getAddtime());
				}else
				if(data[i].equals("29")){
					coachexcel.setNewtasknoti(coach.getNewtasknoti());
				}else
				if(data[i].equals("30")){
					coachexcel.setCancancel(coach.getCancancel());
				}else
				if(data[i].equals("31")){
					coachexcel.setTotaltime(coach.getTotaltime());
				}else
				if(data[i].equals("32")){
					coachexcel.setScore(coach.getScore());
				}else
				if(data[i].equals("33")){
					coachexcel.setIsfrozen(coach.getIsfrozen());
				}else
				if(data[i].equals("34")){
					coachexcel.setFrozenstart(coach.getFrozenstart());
				}else
				if(data[i].equals("35")){
					coachexcel.setFrozenend(coach.getFrozenend());
				}
			}
			excellist.add(coachexcel);
		}
		String[] title = { "主键ID", "教练手机号码","身份证号码","真实姓名","教练的联系电话","身份证到期日期","教练证号","教练证到期日期","驾驶证号","驾驶证到期日期","车辆年检证号","车辆行驶证到期日期","教练准教车型ID","教学用车型号","教学用车型号ID","教学用车牌照","驾校名称","性别",
				"出生日期","教练所属城市","教练联系地址","紧急联系人姓名","紧急联系人电话","教龄","可提现余额","保证金","冻结金额","教练教车单价","教练默认的教学科目","教练状态","教练自我评价","教练等级","教练注册时间","接收到新任务是否提醒","教练是否可以设置订单可否取消",
				"教练教学时长","综合评分","教练是否被冻结","教练冻结开始时间","教练冻结结束时间" };
		String filename = CommonUtils.exportExcel("dataexport", title, excellist,data);
		filename = CommonUtils.properties.getProperty("uploadFilePath") + filename;
		HttpServletResponse response = ServletActionContext.getResponse();
		CommonUtils.downloadExcel(filename, "小巴教练信息表", response);
	}

	/**
	 * 教练提现审核
	 * 
	 * @return
	 */
	@Action(value = "/applyCheckPass", results = { @Result(name = SUCCESS, location = "/getCoachApplyList.do?index=${index}&pageIndex=${pageIndex}", type = "redirect") })
	public String applyCheckPass() {
		cuserService.applyCheckPass(applyid);
		return SUCCESS;
	}

	/**
	 * 设置保证金
	 * 
	 * @return
	 */
	@Action(value = "/setGmoney")
	public void setGmoney() {
		cuserService.setGmoneys(coachid, new BigDecimal(gmoney));
		CuserInfo cuserInfo = cuserService.getCuserByCoachid(coachid);
		if (cuserInfo.getGmoney().doubleValue() == gmoney) {
			setResponseStr("success");
		} else {
			setResponseStr("error");
		}
	}
	
	/**
	 * 添加教练余额
	 * 
	 * @return
	 */
	@Action(value = "/setBalance")
	public void setBalance() {
		CuserInfo cuserInfo = cuserService.getCuserByCoachid(coachid);
		cuserInfo.setMoney(cuserInfo.getMoney().add(money));
		cuserService.updateCuser(cuserInfo);
		setResponseStr("success");
	}
	
	/**
	 * 减少教练余额
	 * 
	 * @return
	 */
	@Action(value = "/lessenBalance")
	public void lessenBalance() {
		CuserInfo cuserInfo = cuserService.getCuserByCoachid(coachid);
		BigDecimal bigdecimal = cuserInfo.getMoney().subtract(money);
		if(bigdecimal.compareTo(BigDecimal.ZERO)==-1){
			setResponseStr("error");
		}else{
			cuserInfo.setMoney(bigdecimal);
			cuserService.updateCuser(cuserInfo);
			setResponseStr("success");
		}
	}

	/**
	 * 设置教练等级
	 * 
	 * @return
	 */
	@Action(value = "/setLevel")
	public void setLevel() {
		cuserService.setLevels(coachid, level);
		CuserInfo cuserInfo = cuserService.getCuserByCoachid(coachid);
		if (cuserInfo.getLevel() == level) {
			setResponseStr("success");
		} else {
			setResponseStr("error");
		}
	}
	
	/**
	 * 关键字搜索教校
	 */
	@Action(value="/searchDriverSchool")
	public void searchDriverSchool(){
		Map<String, Object> map = new HashMap<String, Object>();
		driveSchoollist = cuserService.getDriveSchoolList(schoolkeyword);
		map.put("driveSchoollist", driveSchoollist);
		strToJson(map);
	}

	/**
	 * 得到一个教练的详情
	 * 
	 * @return
	 */
	@Action(value = "/getCoachDetail", results = { @Result(name = SUCCESS, location = "/singlecoach.jsp") })
	public String getCoachDetail() {
		cuser = cuserService.getCuserByCoachid(coachid);
		levelist = cuserService.getLevellist();
		modellist = cuserService.getModelList();
		teachcarlist = cuserService.getTeachcarInfolist();
		if(cuser!=null){
			if(cuser.getModelid()!=null){
				String[] modelid = cuser.getModelid().split(",");
				coachmodellist = new ArrayList<ModelsInfo>();
				for(String id :modelid){
					ModelsInfo model = cuserService.getmodellistbycoachid(CommonUtils.parseInt(id, 0));
					if(model!=null){
						coachmodellist.add(model);
					}
				}
			}
			if(cuser.getCarmodelid()!=0){
				TeachcarInfo teachcar = cuserService.getTeachcarInfoByID(cuser.getCarmodelid());
				if(teachcar!=null){
					cuser.setCarmodel(teachcar.getModelname());
				}
			}
			if(cuser.getDrive_schoolid()!=null&&cuser.getDrive_schoolid()!=0){
				DriveSchoolInfo school = driveSchoolService.getDriveSchoolInfoByid(cuser.getDrive_schoolid());
				if(school!=null){
					cuser.setDrive_school(school.getName());
					driveSchoolname = cuser.getDrive_schoolid();
				}
			}
		}
		if (!CommonUtils.isEmptyString(cuser.getBirthday())) {
			cuser.setAge(cuserService.getCoachAgeByid(CommonUtils.parseInt(coachid, 0)));
		}
		
		return SUCCESS;
	}

	/**
	 * 根据关键字搜索
	 * 
	 * @return
	 */
	@Action(value = "/getCoachByKeyword", results = { @Result(name = SUCCESS, location = "/coachdetail.jsp") })
	public String getCoachByKeyword() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		int pagesize = CommonUtils.parseInt(String.valueOf(session.getAttribute("pagesize")), 10);
		driveSchoollist = cuserService.getDriveSchoolInfo();
		QueryResult<CuserInfo> result = cuserService.getCoachByKeyword(searchname, searchphone, driveSchoolname, carlicense,checkstate, pageIndex, pagesize);
		total = result.getTotal();
		cuserlist = result.getDataList();
		for (int i = 0; i < cuserlist.size(); i++) {
			if (cuserlist.get(i).getIsfrozen() == 1 && cuserlist.get(i).getFrozenend() != null) {
				Date today = new Date();
				Calendar nowtime = Calendar.getInstance();
				nowtime.setTime(today);
				nowtime.set(Calendar.HOUR_OF_DAY, 0);
				nowtime.set(Calendar.MINUTE, 0);
				nowtime.set(Calendar.SECOND, 0);
				nowtime.set(Calendar.MILLISECOND, 0);
				today = nowtime.getTime();
				if (today.after(cuserlist.get(i).getFrozenend())) {
					cuserlist.get(i).setIsfrozen(0);
					cuserService.updateObject(cuserlist.get(i));
				}
			}
			if (!CommonUtils.isEmptyString(cuserlist.get(i).getBirthday())) {
				int age = cuserService.getCoachAgeByid(cuserlist.get(i).getCoachid());
				cuserlist.get(i).setAge(age);
			}
		}
		pageCount = ((int) result.getTotal() + pagesize - 1) / pagesize;
		if (pageIndex > 1) {
			if (cuserlist == null || cuserlist.size() == 0) {
				pageIndex--;
				getCoachByKeyword();
			}
		}
		return SUCCESS;
	}
	
	
	/**
	 * 根据关键字搜索
	 * 
	 * @return
	 */
	@Action(value = "/getCoachTobeChecked", results = { @Result(name = SUCCESS, location = "/coachTobeChecked.jsp") })
	public String getCoachTobeChecked() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		int pagesize = CommonUtils.parseInt(String.valueOf(session.getAttribute("pagesize")), 20);
		driveSchoollist = cuserService.getDriveSchoolInfo();
		QueryResult<CuserInfo> result = cuserService.getCoachByKeyword(searchname, searchphone, driveSchoolname, carlicense,1, pageIndex, pagesize);
		total = result.getTotal();
		cuserlist = result.getDataList();
		for (int i = 0; i < cuserlist.size(); i++) {
			if (cuserlist.get(i).getIsfrozen() == 1 && cuserlist.get(i).getFrozenend() != null) {
				Date today = new Date();
				Calendar nowtime = Calendar.getInstance();
				nowtime.setTime(today);
				nowtime.set(Calendar.HOUR_OF_DAY, 0);
				nowtime.set(Calendar.MINUTE, 0);
				nowtime.set(Calendar.SECOND, 0);
				nowtime.set(Calendar.MILLISECOND, 0);
				today = nowtime.getTime();
				if (today.after(cuserlist.get(i).getFrozenend())) {
					cuserlist.get(i).setIsfrozen(0);
					cuserService.updateObject(cuserlist.get(i));
				}
			}
			if (!CommonUtils.isEmptyString(cuserlist.get(i).getBirthday())) {
				int age = cuserService.getCoachAgeByid(cuserlist.get(i).getCoachid());
				cuserlist.get(i).setAge(age);
			}
		}
		pageCount = ((int) result.getTotal() + pagesize - 1) / pagesize;
		if (pageIndex > 1) {
			if (cuserlist == null || cuserlist.size() == 0) {
				pageIndex--;
				getCoachByKeyword();
			}
		}
		return SUCCESS;
	}
	
	
	
	/**
	 * 获取教练余额列表
	 * 
	 * @return
	 */
	@Action(value = "/getCoachBalance", results = { @Result(name = SUCCESS, location = "/coachbalancelist.jsp") })
	public String getCoachBalance() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		int pagesize = CommonUtils.parseInt(String.valueOf(session.getAttribute("pagesize")), 10);
		driveSchoollist = cuserService.getDriveSchoolInfo();
		QueryResult<CuserInfo> result = cuserService.getCoachByKeyword(searchname, searchphone, driveSchoolname, carlicense,checkstate, pageIndex, pagesize);
		total = result.getTotal();
		cuserlist = result.getDataList();
		for (int i = 0; i < cuserlist.size(); i++) {
			if (cuserlist.get(i).getIsfrozen() == 1 && cuserlist.get(i).getFrozenend() != null) {
				Date today = new Date();
				Calendar nowtime = Calendar.getInstance();
				nowtime.setTime(today);
				nowtime.set(Calendar.HOUR_OF_DAY, 0);
				nowtime.set(Calendar.MINUTE, 0);
				nowtime.set(Calendar.SECOND, 0);
				nowtime.set(Calendar.MILLISECOND, 0);
				today = nowtime.getTime();
				if (today.after(cuserlist.get(i).getFrozenend())) {
					cuserlist.get(i).setIsfrozen(0);
					cuserService.updateObject(cuserlist.get(i));
				}
			}
			if (!CommonUtils.isEmptyString(cuserlist.get(i).getBirthday())) {
				int age = cuserService.getCoachAgeByid(cuserlist.get(i).getCoachid());
				cuserlist.get(i).setAge(age);
			}
		}
		pageCount = ((int) result.getTotal() + pagesize - 1) / pagesize;
		if (pageIndex > 1) {
			if (cuserlist == null || cuserlist.size() == 0) {
				pageIndex--;
				getCoachByKeyword();
			}
		}
		return SUCCESS;
	}

	/**
	 * 关键字搜索提现申请列表
	 * 
	 * @return
	 */
	@Action(value = "/getCoachApplyBySearch", results = { @Result(name = SUCCESS, location = "/coachapply.jsp") })
	public String getCoachApplyBySearch() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		int pagesize = CommonUtils.parseInt(String.valueOf(session.getAttribute("pagesize")), 10);
		int driveschoolid = CommonUtils.parseInt(String.valueOf(session.getAttribute("schoolid")),0);
		if(driveschoolid!=0){
			schoolid = driveschoolid;
			changetype = 1;
		}
		QueryResult<CApplyCashInfo> result = cuserService.getCoachApplyBySearch(searchname, searchphone, amount, inputamount, schoolid, minsdate, maxsdate, pageIndex, pagesize);
		total = result.getTotal();
		applycashlist = result.getDataList();
		pageCount = ((int) result.getTotal() + pagesize - 1) / pagesize;
		if (pageIndex > 1) {
			if (applycashlist == null || applycashlist.size() == 0) {
				pageIndex--;
				getCoachApplyBySearch();
			}
		}
		driveschoollist = cuserService.getDriveSchoolInfo();
		return SUCCESS;
	}

	/**
	 * 关键字搜索提现历史列表
	 * 
	 * @return
	 */
	@Action(value = "/getCoachBalanceBySearch", results = { @Result(name = SUCCESS, location = "/coachbalance.jsp") })
	public String getCoachBalanceBySearch() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		int pagesize = CommonUtils.parseInt(String.valueOf(session.getAttribute("pagesize")), 10);
		int driveschoolid = CommonUtils.parseInt(String.valueOf(session.getAttribute("schoolid")),0);
		if(driveschoolid!=0){
			schoolid = driveschoolid;
		}
		QueryResult<BalanceCoachInfo> result = cuserService.getCoachBalanceBySearch(schoolid,searchname, searchphone, amount, inputamount, minsdate, maxsdate, pageIndex, pagesize);
		total = result.getTotal();
		balancecoachlist = result.getDataList();
		pageCount = ((int) result.getTotal() + pagesize - 1) / pagesize;
		if (pageIndex > 1) {
			if (balancecoachlist == null || balancecoachlist.size() == 0) {
				pageIndex--;
				getCoachBalanceBySearch();
			}
		}
		return SUCCESS;
	}

	/**
	 * 获取教练提现申请列表
	 * 
	 * @return
	 */
	@Action(value = "/getCoachApplyList", results = { @Result(name = SUCCESS, location = "/coachapply.jsp") })
	public String getCoachApplyList() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		int pagesize = CommonUtils.parseInt(String.valueOf(session.getAttribute("pagesize")), 10);
		QueryResult<CApplyCashInfo> result = cuserService.getCoachApplyList(pageIndex, pagesize);
		total = result.getTotal();
		applycashlist = result.getDataList();
		pageCount = ((int) result.getTotal() + pagesize - 1) / pagesize;
		if (pageIndex > 1) {
			if (applycashlist == null || applycashlist.size() == 0) {
				pageIndex--;
				getCoachApplyList();
			}
		}
		driveschoollist = cuserService.getDriveSchoolInfo();
		return SUCCESS;
	}

	/**
	 * 获取提现历史列表
	 * 
	 * @return
	 */
	@Action(value = "/getCoachApplyRecordList", results = { @Result(name = SUCCESS, location = "/coachbalance.jsp") })
	public String getApplyRecordList() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		int pagesize = CommonUtils.parseInt(String.valueOf(session.getAttribute("pagesize")), 10);
		QueryResult<BalanceCoachInfo> result = cuserService.getApplyRecordList(pageIndex, pagesize);
		total = result.getTotal();
		balancecoachlist = result.getDataList();
		pageCount = ((int) result.getTotal() + pagesize - 1) / pagesize;
		if (pageIndex > 1) {
			if (applycashlist == null || applycashlist.size() == 0) {
				pageIndex--;
				getApplyRecordList();
			}
		}
		return SUCCESS;
	}

	/**
	 * 设置是否可以取消
	 */
	@Action(value = "/setcancel")
	public void setcancel() {
		cuser = cuserService.getCuserByCoachid(coachid);
		if (canceltype == 1) {
			cuser.setCancancel(canceltype);
			cuserService.updateCuser(cuser);
			if (cuser.getCancancel() == canceltype) {
				setResponseStr("success");
			} else {
				setResponseStr("error");
			}
		} else if (canceltype == 0) {
			cuser.setCancancel(canceltype);
			cuserService.updateCuser(cuser);
			if (cuser.getCancancel() == canceltype) {
				setResponseStr("success");
			} else {
				setResponseStr("error");
			}
		}
	}

	/**
	 * 冻结
	 */
	@Action(value = "/setfrozentype")
	public void setfrozen() {
		cuser = cuserService.getCoachByid(coachid_frozen);
		if (cuser == null) {
			setResponseStr("error");
		} else {
			if(frozecoachtype==1){
				cuser.setIsfrozen(1);
				cuserService.updateCuser(cuser);
			}else if(frozecoachtype==0){
				cuser.setIsfrozen(0);
				cuser.setFrozenstart(frozenstart);
				cuser.setFrozenend(frozenend);
				cuserService.updateCuser(cuser);
			}else if(frozecoachtype==2){
				cuser.setIsfrozen(0);
				cuser.setFrozenstart(null);
				cuser.setFrozenend(null);
				cuserService.updateCuser(cuser);
			}
			setResponseStr("success");
		}
	}

	/**
	 * 解冻
	 */
	@Action(value = "/unfrozen")
	public void unfrozen() {
		cuser = cuserService.getCoachByid(coachid_unfrozen);
		if (cuser == null) {
			setResponseStr("error");
		} else {
			cuser.setIsfrozen(0);
			cuser.setFrozenend(null);
			cuserService.updateObject(cuser);
			setResponseStr("success");
		}
	}

	@Action(value = "/coachquit")
	public void coachquit() {
		cuser = cuserService.getCoachByid(CommonUtils.parseInt(coachid, 0));
		if (cuser == null) {
			setResponseStr("error");
		} else {
			cuser.setIsquit(Constant.COCAH_QUIT);
			cuser.setQuittime(new Date());
			cuserService.coachCancelOrder(cuser);
			setResponseStr("success");
		}
	}

	/**
	 * 编辑个人详情
	 */
	@Action(value = "/editSingleCoach",results={@Result(name=SUCCESS,location="/getCoachDetail.do?coachid=${coachid}&index=1&change_id=0",type="redirect")})
	public String editSingleCoach() {
		cuser = cuserService.getCoachByid(CommonUtils.parseInt(coachid, 0));

			//修改姓名
			if (!CommonUtils.isEmptyString(editrealname)) {
				cuser.setRealname(editrealname);
			}
			//修改性别
			if (editgender!=null) {
				cuser.setGender(editgender);
			}
			//修改教龄
			if (edityears!=null) {
				cuser.setYears(edityears);
			}
			//修改生日
			if (!CommonUtils.isEmptyString(editbirthday)) {
				cuser.setBirthday(editbirthday);
			}
			//修改城市
			if(!CommonUtils.isEmptyString(editcity)){
				cuser.setCity(editcity);
			}
			//修改地址
			if(!CommonUtils.isEmptyString(editaddress)){
				cuser.setAddress(editaddress);
			}
			//修改紧急联系人
			if(!CommonUtils.isEmptyString(editurgent_person)){
				cuser.setUrgent_person(editurgent_person);
			}
			//修改紧急联系人电话
			if(!CommonUtils.isEmptyString(editurgent_phone)){
				cuser.setUrgent_phone(editurgent_phone);
			}
			//修改自我评价
			if(!CommonUtils.isEmptyString(editselfval)){
				cuser.setSelfeval(editselfval);
			}
			//修改教练等级
			if(editlevel!=null){
				cuser.setLevel(editlevel);
			}
			//修改注册时间
			if(editaddtime!=null){
				cuser.setAddtime(editaddtime);
			}
			//修改评分
			if(editscore!=0){
				cuser.setScore(editscore);
			}
			//修改身份证号
			if(!CommonUtils.isEmptyString(editid_cardnum)){
				cuser.setId_cardnum(editid_cardnum);
			}
			//修改身份证到期时间
			if(!CommonUtils.isEmptyString(editid_cardexptime)){
				cuser.setId_cardexptime(editid_cardexptime);
			}
			//修改教练证号
			if(!CommonUtils.isEmptyString(editcoach_cardnum)){
				cuser.setCoach_cardnum(editcoach_cardnum);
			}
			//修改教练证到期时间
			if(!CommonUtils.isEmptyString(editcoach_cardexptime)){
				cuser.setCoach_cardexptime(editcoach_cardexptime);
			}
			//修改驾驶证号
			if(!CommonUtils.isEmptyString(editdrive_cardnum)){
				cuser.setDrive_cardnum(editdrive_cardnum);
			}
			//修改驾驶证到期时间
			if(!CommonUtils.isEmptyString(editdrive_cardexptime)){
				cuser.setDrive_cardexptime(editdrive_cardexptime);
			}
			//修改车证号
			if(!CommonUtils.isEmptyString(editcar_cardnum)){
				cuser.setCar_cardnum(editcar_cardnum);
			}
			//修改车证号到期时间
			if(!CommonUtils.isEmptyString(editcar_cardexptime)){
				cuser.setCar_cardexptime(editcar_cardexptime);
			}
			//修改教学车型
			if(!CommonUtils.isEmptyString(editcarmodel)){
				cuser.setCarmodel(editcarmodel);
			}
			//修改教学车车牌
			if(!CommonUtils.isEmptyString(editcarlicense)){
				cuser.setCarlicense(editcarlicense);
			}
			//身份证正面照
			if(editid_cardpicfurl!=null){
				try {
					String	filepath = CommonUtils.uploadImg(editid_cardpicfurl, editid_cardpicfurlFileName);
					if (!CommonUtils.isEmptyString(filepath)) {
						String fileurl = CommonUtils.getTimeFormat(new Date(), "yyyyMMdd") + File.separator;
						long fileid = baseService.uploadComplete(fileurl, filepath.substring(filepath.lastIndexOf("/") + 1));
						cuser.setId_cardpicf((int)fileid);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			//身份证反面照
			if(editid_cardpicburl!=null){
				try {
					String	filepath = CommonUtils.uploadImg(editid_cardpicburl, editid_cardpicburlFileName);
					if (!CommonUtils.isEmptyString(filepath)) {
						String fileurl = CommonUtils.getTimeFormat(new Date(), "yyyyMMdd") + File.separator;
						long fileid = baseService.uploadComplete(fileurl, filepath.substring(filepath.lastIndexOf("/") + 1));
						cuser.setId_cardpicb((int)fileid);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			//修改教练证照片
			if(editcoach_cardpicurl!=null){
				try {
					String	filepath = CommonUtils.uploadImg(editcoach_cardpicurl, editcoach_cardpicurlFileName);
					if (!CommonUtils.isEmptyString(filepath)) {
						String fileurl = CommonUtils.getTimeFormat(new Date(), "yyyyMMdd") + File.separator;
						long fileid = baseService.uploadComplete(fileurl, filepath.substring(filepath.lastIndexOf("/") + 1));
						cuser.setCoach_cardpic((int)fileid);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			//修改驾驶证照片
			if(editdrive_cardpicurl!=null){
				try {
					String	filepath = CommonUtils.uploadImg(editdrive_cardpicurl, editdrive_cardpicurlFileName);
					if (!CommonUtils.isEmptyString(filepath)) {
						String fileurl = CommonUtils.getTimeFormat(new Date(), "yyyyMMdd") + File.separator;
						long fileid = baseService.uploadComplete(fileurl, filepath.substring(filepath.lastIndexOf("/") + 1));
						cuser.setDrive_cardpic((int)fileid);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
			//修改车辆行驶证正面照
			if(editcar_cardpicfurl!=null){
				try {
					String	filepath = CommonUtils.uploadImg(editcar_cardpicfurl, editcar_cardpicfurlFileName);
					if (!CommonUtils.isEmptyString(filepath)) {
						String fileurl = CommonUtils.getTimeFormat(new Date(), "yyyyMMdd") + File.separator;
						long fileid = baseService.uploadComplete(fileurl, filepath.substring(filepath.lastIndexOf("/") + 1));
						cuser.setCar_cardpicf((int)fileid);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			//修改车辆行驶证反面照
			if(editcar_cardpicburl!=null){
				try {
					String	filepath = CommonUtils.uploadImg(editcar_cardpicburl, editcar_cardpicburlFileName);
					if (!CommonUtils.isEmptyString(filepath)) {
						String fileurl = CommonUtils.getTimeFormat(new Date(), "yyyyMMdd") + File.separator;
						long fileid = baseService.uploadComplete(fileurl, filepath.substring(filepath.lastIndexOf("/") + 1));
						cuser.setCar_cardpicb((int)fileid);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			cuserService.updateObject(cuser);
			return SUCCESS;
		}
	
	
	@Action(value = "/doallpass", results = { @Result(name = SUCCESS, location = "/getCoachlist.do?pageIndex=${pageIndex}",type="redirect") })
	public String doallpass(){
		if(checkbox.length!=0){
			for (int i = 0; i < checkbox.length; i++) {
				cuser=cuserService.getCoachByid(CommonUtils.parseInt(checkbox[i], 0));
				cuser.setState(2);
				cuserService.updateCuser(cuser);
			}
			return SUCCESS;
		}
		return null;
	}
	
	
	@Action(value = "/doallunpass", results = { @Result(name = SUCCESS, location = "/getCoachlist.do?pageIndex=${pageIndex}",type="redirect") })
	public String doallunpass(){
		if(checkbox.length!=0){
			for (int i = 0; i < checkbox.length; i++) {
				cuser=cuserService.getCoachByid(CommonUtils.parseInt(checkbox[i], 0));
				cuser.setState(4);
				cuserService.updateCuser(cuser);
			}
			return SUCCESS;
		}
		return null;
	}
	
	
	@Action(value = "/dochangeallmoney", results = { @Result(name = SUCCESS, location = "/getCoachlist.do?pageIndex=${pageIndex}",type="redirect") })
	public String dochangeallmoney(){
		if(checkbox.length!=0){
			for (int i = 0; i < checkbox.length; i++) {
				cuser=cuserService.getCoachByid(CommonUtils.parseInt(checkbox[i], 0));
				cuser.setGmoney(allmoney);
				cuserService.updateCuser(cuser);
			}
			return SUCCESS;
		}
		return null;
	}
	
	/**
	 * 添加教练余额
	 * @return
	 */
	@Action(value = "/addcoachbalance", results = { @Result(name = SUCCESS, location = "/getCoachBalance.do?pageIndex=${pageIndex}",type="redirect") })
	public String addcoachbalance(){
		if(checkbox.length!=0){
			for (int i = 0; i < checkbox.length; i++) {
				cuser=cuserService.getCoachByid(CommonUtils.parseInt(checkbox[i], 0));
				cuser.setMoney(cuser.getMoney().add(money));
				cuserService.updateCuser(cuser);
			}
			return SUCCESS;
		}
		return null;
	}
	
	@Action(value = "/dochangealllevel", results = { @Result(name = SUCCESS, location = "/getCoachlist.do?pageIndex=${pageIndex}",type="redirect") })
	public String dochangealllevel(){
		if(checkbox.length!=0){
			for (int i = 0; i < checkbox.length; i++) {
				cuser=cuserService.getCoachByid(CommonUtils.parseInt(checkbox[i], 0));
				cuser.setLevel(alllevel);
				cuserService.updateCuser(cuser);
			}
			return SUCCESS;
		}
		return null;
	}
	
	/**
	 * 减少教练余额
	 * @return
	 * @throws Exception 
	 */
	@Action(value = "/lessencoachbalance", results = { @Result(name = SUCCESS, location = "/getCoachBalance.do?pageIndex=${pageIndex}",type="redirect") })
	public String lessencoachbalance(){
		int less = 0;
		if(checkbox.length!=0){
			for (int i = 0; i < checkbox.length; i++) {
				cuser=cuserService.getCoachByid(CommonUtils.parseInt(checkbox[i], 0));
				BigDecimal bigdecimal = cuser.getMoney().subtract(money);
				if(bigdecimal.compareTo(BigDecimal.ZERO)==-1){
					less = -1;
				}
			}
			if(less!=-1){
				for (int i = 0; i < checkbox.length; i++) {
					cuser=cuserService.getCoachByid(CommonUtils.parseInt(checkbox[i], 0));
					BigDecimal bigdecimal = cuser.getMoney().subtract(money);
					cuser.setMoney(bigdecimal);
					cuserService.updateCuser(cuser);
				}
			}
			return SUCCESS;
		}
		return null;
	}
	
	
	@Action(value = "/doallcancancel", results = { @Result(name = SUCCESS, location = "/getCoachlist.do?pageIndex=${pageIndex}",type="redirect") })
	public String doallcancancel(){
		if(checkbox.length!=0){
			for (int i = 0; i < checkbox.length; i++) {
				cuser=cuserService.getCoachByid(CommonUtils.parseInt(checkbox[i], 0));
				cuser.setCancancel(0);
				cuserService.updateCuser(cuser);
			}
			return SUCCESS;
		}
		return null;
	}
	
	@Action(value = "/doallcannotcancel", results = { @Result(name = SUCCESS, location = "/getCoachlist.do?pageIndex=${pageIndex}",type="redirect") })
	public String doallcannotcancel(){
		if(checkbox.length!=0){
			for (int i = 0; i < checkbox.length; i++) {
				cuser=cuserService.getCoachByid(CommonUtils.parseInt(checkbox[i], 0));
				cuser.setCancancel(1);
				cuserService.updateCuser(cuser);
			}
			return SUCCESS;
		}
		return null;
	}
	
	
	@Action(value = "/doallunfrozen", results = { @Result(name = SUCCESS, location = "/getCoachlist.do?pageIndex=${pageIndex}",type="redirect") })
	public String doallunfrozen(){
		if(checkbox.length!=0){
			for (int i = 0; i < checkbox.length; i++) {
				cuser=cuserService.getCoachByid(CommonUtils.parseInt(checkbox[i], 0));
				cuser.setIsfrozen(0);
				cuser.setFrozenend(null);
				cuserService.updateCuser(cuser);
			}
			return SUCCESS;
		}
		return null;
	}
	
	@Action(value = "/dochangeallfrozen", results = { @Result(name = SUCCESS, location = "/getCoachlist.do?pageIndex=${pageIndex}",type="redirect") })
	public String dochangeallfrozen(){
		if(checkbox.length!=0){
			for (int i = 0; i < checkbox.length; i++) {
				cuser=cuserService.getCoachByid(CommonUtils.parseInt(checkbox[i], 0));
				if(frozecoachtype==1){
					cuser.setIsfrozen(1);
					cuserService.updateCuser(cuser);
				}else if(frozecoachtype==0){
					cuser.setIsfrozen(0);
					cuser.setFrozenstart(allfrozenstart);
					cuser.setFrozenend(allfrozenend);
					cuserService.updateCuser(cuser);
				}else if(frozecoachtype==2){
					cuser.setIsfrozen(0);
					cuser.setFrozenstart(null);
					cuser.setFrozenend(null);
					cuserService.updateCuser(cuser);
				}
			}
			return SUCCESS;
		}
		return null;
	}
	
	@Action(value = "/doallisquit", results = { @Result(name = SUCCESS, location = "/getCoachlist.do?pageIndex=${pageIndex}",type="redirect") })
	public String doallisquit(){
		if(checkbox.length!=0){
			for (int i = 0; i < checkbox.length; i++) {
				cuser=cuserService.getCoachByid(CommonUtils.parseInt(checkbox[i], 0));
				cuser.setIsquit(Constant.COCAH_QUIT);
				cuser.setQuittime(new Date());
				cuserService.updateCuser(cuser);
			}
			return SUCCESS;
		}
		return null;
	}
	
	@Action(value = "/changeSchool")
	public void changeSchool() {
		cuser = cuserService.getCoachByid(CommonUtils.parseInt(coachid, 0));
		if (cuser!=null) {
			cuser.setDrive_schoolid(driveSchoolname);
			cuser.setDriveschool("");
			cuserService.updateCuser(cuser);
		} 
	}
	
	@Action(value = "/changeCoachModel", results = { @Result(name = SUCCESS, location = "/getCoachDetail.do?coachid=${coachid}",type="redirect") })
	public String changeCoachModel() {
		cuser = cuserService.getCoachByid(CommonUtils.parseInt(coachid, 0));
		modellist = new ArrayList<ModelsInfo>();
		String modelid = "";
		if(cuser!=null){
			if(coachmodelid.length>0){
				for(String id :coachmodelid){
					modelid += id+",";
					ModelsInfo model = cuserService.getmodellistbycoachid(CommonUtils.parseInt(id, 0));
					if(model!=null){
						modellist.add(model);
					}
				}
			}
			cuser.setModelid(modelid);
			cuserService.updateCuser(cuser);
		}
		return SUCCESS;
	}
	
	@Action(value = "/changeTeachCar")
	public void changeTeachCar() {
		cuser = cuserService.getCoachByid(CommonUtils.parseInt(coachid, 0));
		if (cuser!=null) {
			cuser.setCarmodelid(driveSchoolname);
			cuser.setCarmodel("");
			cuserService.updateCuser(cuser);
		} 
	}
	
	public int getChecknum() {
		return checknum;
	}

	public void setChecknum(int checknum) {
		this.checknum = checknum;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<CuserInfo> getCuserlist() {
		return cuserlist;
	}

	public void setCuserlist(List<CuserInfo> cuserlist) {
		this.cuserlist = cuserlist;
	}

	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public String getCoachid() {
		return coachid;
	}

	public void setCoachid(String coachid) {
		this.coachid = coachid;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public CuserInfo getCuser() {
		return cuser;
	}

	public void setCuser(CuserInfo cuser) {
		this.cuser = cuser;
	}

	public double getGmoney() {
		return gmoney;
	}

	public void setGmoney(double gmoney) {
		this.gmoney = gmoney;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getSearchname() {
		return searchname;
	}

	public void setSearchname(String searchname) {
		this.searchname = searchname;
	}

	public String getSearchphone() {
		return searchphone;
	}

	public void setSearchphone(String searchphone) {
		this.searchphone = searchphone;
	}

	public List<CApplyCashInfo> getApplycashlist() {
		return applycashlist;
	}

	public int getWherecheck() {
		return wherecheck;
	}

	public void setApplycashlist(List<CApplyCashInfo> applycashlist) {
		this.applycashlist = applycashlist;
	}

	public int getApplyid() {
		return applyid;
	}

	public void setApplyid(int applyid) {
		this.applyid = applyid;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getInputamount() {
		return inputamount;
	}

	public void setInputamount(String inputamount) {
		this.inputamount = inputamount;
	}

	public String getMinsdate() {
		return minsdate;
	}

	public void setMinsdate(String minsdate) {
		this.minsdate = minsdate;
	}

	public String getMaxsdate() {
		return maxsdate;
	}

	public void setMaxsdate(String maxsdate) {
		this.maxsdate = maxsdate;
	}

	public List<BalanceCoachInfo> getBalancecoachlist() {
		return balancecoachlist;
	}

	public void setBalancecoachlist(List<BalanceCoachInfo> balancecoachlist) {
		this.balancecoachlist = balancecoachlist;
	}

	public void setWherecheck(int wherecheck) {
		this.wherecheck = wherecheck;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getCanceltype() {
		return canceltype;
	}

	public void setCanceltype(int canceltype) {
		this.canceltype = canceltype;
	}

	public List<DriveSchoolInfo> getDriveSchoollist() {
		return driveSchoollist;
	}

	public void setDriveSchoollist(List<DriveSchoolInfo> driveSchoollist) {
		this.driveSchoollist = driveSchoollist;
	}


	
	public Integer getDriveSchoolname() {
		return driveSchoolname;
	}

	
	public void setDriveSchoolname(Integer driveSchoolname) {
		this.driveSchoolname = driveSchoolname;
	}

	public String getCarlicense() {
		return carlicense;
	}

	public void setCarlicense(String carlicense) {
		this.carlicense = carlicense;
	}

	public File getAddVersion() {
		return addVersion;
	}

	public void setAddVersion(File addVersion) {
		this.addVersion = addVersion;
	}

	public String getAddVersionFileName() {
		return addVersionFileName;
	}

	public void setAddVersionFileName(String addVersionFileName) {
		this.addVersionFileName = addVersionFileName;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public int getCoachid_frozen() {
		return coachid_frozen;
	}

	public void setCoachid_frozen(int coachid_frozen) {
		this.coachid_frozen = coachid_frozen;
	}

	public Date getFrozenstart() {
		return frozenstart;
	}

	public void setFrozenstart(Date frozenstart) {
		this.frozenstart = frozenstart;
	}

	public Date getFrozenend() {
		return frozenend;
	}

	public void setFrozenend(Date frozenend) {
		this.frozenend = frozenend;
	}

	public int getCoachid_unfrozen() {
		return coachid_unfrozen;
	}

	public void setCoachid_unfrozen(int coachid_unfrozen) {
		this.coachid_unfrozen = coachid_unfrozen;
	}

	public int getChange_id() {
		return change_id;
	}

	public void setChange_id(int change_id) {
		this.change_id = change_id;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNewlevel() {
		return newlevel;
	}

	public void setNewlevel(String newlevel) {
		this.newlevel = newlevel;
	}

	public String getYears() {
		return years;
	}

	public void setYears(String years) {
		this.years = years;
	}

	public List<DriveSchoolInfo> getDriveschoollist() {
		return driveschoollist;
	}

	public void setDriveschoollist(List<DriveSchoolInfo> driveschoollist) {
		this.driveschoollist = driveschoollist;
	}

	public int getSchoolid() {
		return schoolid;
	}

	public void setSchoolid(int schoolid) {
		this.schoolid = schoolid;
	}

	public String getCoachdate() {
		return coachdate;
	}

	public void setCoachdate(String coachdate) {
		this.coachdate = coachdate;
	}
	
	public String getEditrealname() {
		return editrealname;
	}

	
	public void setEditrealname(String editrealname) {
		this.editrealname = editrealname;
	}

	
	public Integer getEditgender() {
		return editgender;
	}

	
	public void setEditgender(Integer editgender) {
		this.editgender = editgender;
	}

	
	public Integer getEdityears() {
		return edityears;
	}

	
	public void setEdityears(Integer edityears) {
		this.edityears = edityears;
	}

	
	public String getEditbirthday() {
		return editbirthday;
	}

	
	public void setEditbirthday(String editbirthday) {
		this.editbirthday = editbirthday;
	}

	
	public String getEditcity() {
		return editcity;
	}

	
	public void setEditcity(String editcity) {
		this.editcity = editcity;
	}

	
	public String getEditaddress() {
		return editaddress;
	}

	
	public void setEditaddress(String editaddress) {
		this.editaddress = editaddress;
	}

	
	public String getEditurgent_person() {
		return editurgent_person;
	}

	
	public void setEditurgent_person(String editurgent_person) {
		this.editurgent_person = editurgent_person;
	}

	
	public String getEditurgent_phone() {
		return editurgent_phone;
	}

	
	public void setEditurgent_phone(String editurgent_phone) {
		this.editurgent_phone = editurgent_phone;
	}

	
	public String getEditselfval() {
		return editselfval;
	}

	
	public void setEditselfval(String editselfval) {
		this.editselfval = editselfval;
	}

	
	public Integer getEditlevel() {
		return editlevel;
	}

	
	public void setEditlevel(Integer editlevel) {
		this.editlevel = editlevel;
	}

	
	public Date getEditaddtime() {
		return editaddtime;
	}

	
	public void setEditaddtime(Date editaddtime) {
		this.editaddtime = editaddtime;
	}



	
	
	public float getEditscore() {
		return editscore;
	}

	
	public void setEditscore(float editscore) {
		this.editscore = editscore;
	}

	public String getEditid_cardnum() {
		return editid_cardnum;
	}

	
	public void setEditid_cardnum(String editid_cardnum) {
		this.editid_cardnum = editid_cardnum;
	}

	
	public String getEditid_cardexptime() {
		return editid_cardexptime;
	}

	
	public void setEditid_cardexptime(String editid_cardexptime) {
		this.editid_cardexptime = editid_cardexptime;
	}

	
	public String getEditcoach_cardnum() {
		return editcoach_cardnum;
	}

	
	public void setEditcoach_cardnum(String editcoach_cardnum) {
		this.editcoach_cardnum = editcoach_cardnum;
	}

	
	public String getEditcoach_cardexptime() {
		return editcoach_cardexptime;
	}

	
	public void setEditcoach_cardexptime(String editcoach_cardexptime) {
		this.editcoach_cardexptime = editcoach_cardexptime;
	}

	
	public String getEditdrive_cardnum() {
		return editdrive_cardnum;
	}

	
	public void setEditdrive_cardnum(String editdrive_cardnum) {
		this.editdrive_cardnum = editdrive_cardnum;
	}

	
	public String getEditdrive_cardexptime() {
		return editdrive_cardexptime;
	}

	
	public void setEditdrive_cardexptime(String editdrive_cardexptime) {
		this.editdrive_cardexptime = editdrive_cardexptime;
	}

	
	public String getEditcar_cardnum() {
		return editcar_cardnum;
	}

	
	public void setEditcar_cardnum(String editcar_cardnum) {
		this.editcar_cardnum = editcar_cardnum;
	}

	
	public String getEditcar_cardexptime() {
		return editcar_cardexptime;
	}

	
	public void setEditcar_cardexptime(String editcar_cardexptime) {
		this.editcar_cardexptime = editcar_cardexptime;
	}

	
	public String getEditcarmodel() {
		return editcarmodel;
	}

	
	public void setEditcarmodel(String editcarmodel) {
		this.editcarmodel = editcarmodel;
	}

	
	public String getEditcarlicense() {
		return editcarlicense;
	}

	
	public void setEditcarlicense(String editcarlicense) {
		this.editcarlicense = editcarlicense;
	}

	
	public String getEditid_cardpicfurlFileName() {
		return editid_cardpicfurlFileName;
	}

	
	public void setEditid_cardpicfurlFileName(String editid_cardpicfurlFileName) {
		this.editid_cardpicfurlFileName = editid_cardpicfurlFileName;
	}

	
	public File getEditid_cardpicfurl() {
		return editid_cardpicfurl;
	}

	
	public void setEditid_cardpicfurl(File editid_cardpicfurl) {
		this.editid_cardpicfurl = editid_cardpicfurl;
	}

	
	public String getEditid_cardpicburlFileName() {
		return editid_cardpicburlFileName;
	}

	
	public void setEditid_cardpicburlFileName(String editid_cardpicburlFileName) {
		this.editid_cardpicburlFileName = editid_cardpicburlFileName;
	}

	
	public File getEditid_cardpicburl() {
		return editid_cardpicburl;
	}

	
	public void setEditid_cardpicburl(File editid_cardpicburl) {
		this.editid_cardpicburl = editid_cardpicburl;
	}

	
	public String getEditcoach_cardpicurlFileName() {
		return editcoach_cardpicurlFileName;
	}

	
	public void setEditcoach_cardpicurlFileName(String editcoach_cardpicurlFileName) {
		this.editcoach_cardpicurlFileName = editcoach_cardpicurlFileName;
	}

	
	public File getEditcoach_cardpicurl() {
		return editcoach_cardpicurl;
	}

	
	public void setEditcoach_cardpicurl(File editcoach_cardpicurl) {
		this.editcoach_cardpicurl = editcoach_cardpicurl;
	}

	
	public String getEditdrive_cardpicurlFileName() {
		return editdrive_cardpicurlFileName;
	}

	
	public void setEditdrive_cardpicurlFileName(String editdrive_cardpicurlFileName) {
		this.editdrive_cardpicurlFileName = editdrive_cardpicurlFileName;
	}

	
	public File getEditdrive_cardpicurl() {
		return editdrive_cardpicurl;
	}

	
	public void setEditdrive_cardpicurl(File editdrive_cardpicurl) {
		this.editdrive_cardpicurl = editdrive_cardpicurl;
	}

	
	public String getEditcar_cardpicfurlFileName() {
		return editcar_cardpicfurlFileName;
	}

	
	public void setEditcar_cardpicfurlFileName(String editcar_cardpicfurlFileName) {
		this.editcar_cardpicfurlFileName = editcar_cardpicfurlFileName;
	}

	
	public File getEditcar_cardpicfurl() {
		return editcar_cardpicfurl;
	}

	
	public void setEditcar_cardpicfurl(File editcar_cardpicfurl) {
		this.editcar_cardpicfurl = editcar_cardpicfurl;
	}

	
	public String getEditcar_cardpicburlFileName() {
		return editcar_cardpicburlFileName;
	}

	
	public void setEditcar_cardpicburlFileName(String editcar_cardpicburlFileName) {
		this.editcar_cardpicburlFileName = editcar_cardpicburlFileName;
	}

	
	public File getEditcar_cardpicburl() {
		return editcar_cardpicburl;
	}

	
	public void setEditcar_cardpicburl(File editcar_cardpicburl) {
		this.editcar_cardpicburl = editcar_cardpicburl;
	}

	
	
	

	public String getSchool_id() {
		return school_id;
	}

	public void setSchool_id(String school_id) {
		this.school_id = school_id;
	}


	
	public Integer getCheckstate() {
		return checkstate;
	}


	
	public void setCheckstate(Integer checkstate) {
		this.checkstate = checkstate;
	}

	
	public String[] getCheckbox() {
		return checkbox;
	}

	
	public void setCheckbox(String[] checkbox) {
		this.checkbox = checkbox;
	}

	
	public BigDecimal getAllmoney() {
		return allmoney;
	}

	
	public void setAllmoney(BigDecimal allmoney) {
		this.allmoney = allmoney;
	}

	
	public Integer getAlllevel() {
		return alllevel;
	}

	
	public void setAlllevel(Integer alllevel) {
		this.alllevel = alllevel;
	}

	
	public Date getAllfrozenstart() {
		return allfrozenstart;
	}

	
	public void setAllfrozenstart(Date allfrozenstart) {
		this.allfrozenstart = allfrozenstart;
	}

	
	public Date getAllfrozenend() {
		return allfrozenend;
	}

	
	public void setAllfrozenend(Date allfrozenend) {
		this.allfrozenend = allfrozenend;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public List<ModelsInfo> getModellist() {
		return modellist;
	}

	public void setModellist(List<ModelsInfo> modellist) {
		this.modellist = modellist;
	}

	public String[] getCoachmodelid() {
		return coachmodelid;
	}

	public void setCoachmodelid(String[] coachmodelid) {
		this.coachmodelid = coachmodelid;
	}

	public List<ModelsInfo> getCoachmodellist() {
		return coachmodellist;
	}

	public void setCoachmodellist(List<ModelsInfo> coachmodellist) {
		this.coachmodellist = coachmodellist;
	}

	public List<TeachcarInfo> getTeachcarlist() {
		return teachcarlist;
	}

	public void setTeachcarlist(List<TeachcarInfo> teachcarlist) {
		this.teachcarlist = teachcarlist;
	}

	public int getChangetype() {
		return changetype;
	}

	public void setChangetype(int changetype) {
		this.changetype = changetype;
	}

	public String getSchoolkeyword() {
		return schoolkeyword;
	}

	public void setSchoolkeyword(String schoolkeyword) {
		this.schoolkeyword = schoolkeyword;
	}

	public List<CoachLevelInfo> getLevelist() {
		return levelist;
	}

	public void setLevelist(List<CoachLevelInfo> levelist) {
		this.levelist = levelist;
	}

	public int getFrozecoachtype() {
		return frozecoachtype;
	}

	public void setFrozecoachtype(int frozecoachtype) {
		this.frozecoachtype = frozecoachtype;
	}
	

}
