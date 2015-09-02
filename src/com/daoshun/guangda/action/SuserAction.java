package com.daoshun.guangda.action;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.daoshun.common.CommonUtils;
import com.daoshun.common.QueryResult;
import com.daoshun.guangda.NetData.StudentInfoForExcel;
import com.daoshun.guangda.pojo.BalanceStudentInfo;
import com.daoshun.guangda.pojo.CuserInfo;
import com.daoshun.guangda.pojo.ModelPrice;
import com.daoshun.guangda.pojo.StudentApplyInfo;
import com.daoshun.guangda.pojo.StudentCheckInfo;
import com.daoshun.guangda.pojo.SuserInfo;
import com.daoshun.guangda.pojo.SuserState;
import com.daoshun.guangda.service.IBaseService;
import com.daoshun.guangda.service.ICUserService;
import com.daoshun.guangda.service.ISBookService;
import com.daoshun.guangda.service.ISUserService;

import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;

@ParentPackage("default")
@Controller
@Scope("prototype")
public class SuserAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2534791731119144729L;

	@Resource
	private ISUserService suserService;
	
	@Resource
	private IBaseService baseService;
	
	@Resource
	private ICUserService cuserService;

	private StudentCheckInfo studentcheck;
	private SuserInfo suser;
	private CuserInfo cuser;
	private SuserState suserSta = null;

	private List<SuserInfo> suserlist;
	
	private List<SuserState> suserstalist;

	// 学员提现申请列表
	private List<StudentApplyInfo> stuApplyList;

	private List<BalanceStudentInfo> balanceStudentList;

	// 提现金额
	private String amount;

	// 搜索比对金额
	private String inputamount;

	// 搜索的开始时间
	private String minsdate;

	// 搜索的结束时间
	private String maxsdate;
	
	// 搜索的开始时间
	private String minenrollsdate;

	// 搜索的结束时间
	private String maxenrollsdate;

	// 搜索名字
	private String searchname;

	// 搜索电话
	private String searchphone;

	private File addVersion; // 上传的版本文件

	private String addVersionFileName;

	private int index;

	private int change_id;

	private Integer pageIndex = 1;

	private Integer pageCount = 0;


	
	//添加新学员
	private String  newstudentphone;
	
	private String  newstudentname;
	
	//添加新跟进记录
	private String addcontent;
	
	// 学员提现申请id
	private int applyid;

	private int checknum;

	private long total;

	private Integer state;

	private Integer studentid;
	
	private Integer dealpeopleid;

	private double studentmoney;

	private Integer searchagestate;

	private Integer searchage;

	private Float searchminscore;

	private Float searchmaxscore;
	
	private String studentdate;
	
	//编辑学员个人资料
	private String editrealname;

	private String editphone;
	
	

	private Integer editgender;

	private String editbirthday;

	private String editcity;

	private String editaddress;

	private String editurgent_person;

	private String editurgent_phone;

	private Date editaddtime;

	private float editscore;
	
	private String editid_cardnum;
	
	private String editstudent_cardnum;
	
	private String editstudent_card_creat;

	private String editid_cardpicf_urlFileName;
	
	private File editid_cardpicf_url;
	
	private String editid_cardpicb_urlFileName;
	
	private File editid_cardpicb_url;
	
	private String editstudent_cardpicb_urlFileName;
	
	private File editstudent_cardpicb_url;
	
	private String[] checkbox;
	
	private BigDecimal money;
	
	private Integer editusertype;// 是否测试用户 ，0 普通用户  ，1 测试用户


	//编辑学员跟进状态
	private String editdealpeople;
	private int editdealpeopleid;
	private Date editdealtime;
	private String editcontent;


	private String oldcoachid;

	private String newcoachid;
	
	private String rphone;
	
	private int rtype;

	private int flagresult;
	/**
	 * 得到学员列表
	 * 
	 * @return
	 */
	@Action(value = "/getStudentlist", results = { @Result(name = SUCCESS, location = "/studentdetail.jsp") })
	public String suserlist() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		int pagesize = CommonUtils.parseInt(String.valueOf(session.getAttribute("pagesize")), 10);
		QueryResult<SuserInfo> result = suserService.getAllSuserInfos(pageIndex, pagesize);
		total = result.getTotal();
		suserlist = result.getDataList();
		for (int i = 0; i < suserlist.size(); i++) {
			if (!CommonUtils.isEmptyString(suserlist.get(i).getBirthday())) {
				int age = suserService.getSuserAgeByid(suserlist.get(i).getStudentid());
				suserlist.get(i).setAge(age);
			}
		}
		pageCount = ((int) result.getTotal() + pagesize - 1) / pagesize;
		if (pageIndex > 1) {
			if (suserlist == null || suserlist.size() == 0) {
				pageIndex--;
				suserlist();
			}
		}
		return SUCCESS;
	}
	@Resource
	ISBookService bookService;
	/**
	 * 得到报名学员列表
	 * 
	 * @return
	 */
	@Action(value = "/getEnrollStudentlist", results = { @Result(name = SUCCESS, location = "/enrollstudentdetail.jsp") })
	public String enrollsuserlist() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		int pagesize = CommonUtils.parseInt(String.valueOf(session.getAttribute("pagesize")), 10);
		QueryResult<SuserInfo> result = suserService.getEnrollSuserInfos(pageIndex, pagesize);
		total = result.getTotal();
		suserlist = result.getDataList();
		for (int i = 0; i < suserlist.size(); i++) {
			if (!CommonUtils.isEmptyString(suserlist.get(i).getBirthday())) {
				int age = suserService.getSuserAgeByid(suserlist.get(i).getStudentid());
				suserlist.get(i).setAge(age);
			}
		}
		//添加城市
		for (int i = 0; i < suserlist.size(); i++) {
			if (!CommonUtils.isEmptyString(String.valueOf(suserlist.get(i).getCityid()))) {
				//int age = suserService.getSuserAgeByid(suserlist.get(i).getStudentid());
				String city ="";
				if(!CommonUtils.isEmptyString(suserlist.get(i).getCityid())){
					city = suserService.getCityByCityid(Integer.parseInt(suserlist.get(i).getCityid()));
				}
				suserlist.get(i).setCity(city);
				if(suserlist.get(i).getModelcityid()!=null){
					List<ModelPrice> list=bookService.getModelPriceByCityId(suserlist.get(i).getModelcityid());
					if(list!=null && list.size()>0){
						ModelPrice mp=list.get(0);
						String model=suserlist.get(i).getModel();
						if("c1".equals(model)){
							suserlist.get(i).setPrice(mp.getC1xiaobaprice());
						}else if("c2".equals(model)){
							suserlist.get(i).setPrice(mp.getC2xiaobaprice());
						}
					}
				}
			}
		}
		
		pageCount = ((int) result.getTotal() + pagesize - 1) / pagesize;
		if (pageIndex > 1) {
			if (suserlist == null || suserlist.size() == 0) {
				pageIndex--;
				suserlist();
			}
		}
		return SUCCESS;
	}
	
	
	/**
	 * 得到已报名学员列表
	 * 
	 * @return
	 */
	@Action(value = "/getEnrolledStudentlist", results = { @Result(name = SUCCESS, location = "/enrolledstudentdetail.jsp") })
	public String enrolledsuserlist() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		int pagesize = CommonUtils.parseInt(String.valueOf(session.getAttribute("pagesize")), 10);
		QueryResult<SuserInfo> result = suserService.getEnrolledSuserInfos(pageIndex, pagesize);
		total = result.getTotal();
		suserlist = result.getDataList();
		for (int i = 0; i < suserlist.size(); i++) {
			if (!CommonUtils.isEmptyString(suserlist.get(i).getBirthday())) {
				int age = suserService.getSuserAgeByid(suserlist.get(i).getStudentid());
				suserlist.get(i).setAge(age);
			}
		}
		//添加城市
		for (int i = 0; i < suserlist.size(); i++) {
			if (!CommonUtils.isEmptyString(String.valueOf(suserlist.get(i).getCityid()))) {
				//int age = suserService.getSuserAgeByid(suserlist.get(i).getStudentid());
				String city ="";
				if(!CommonUtils.isEmptyString(suserlist.get(i).getCityid())){
					city = suserService.getCityByCityid(Integer.parseInt(suserlist.get(i).getCityid()));
				}
				suserlist.get(i).setCity(city);;
			}
		}
		pageCount = ((int) result.getTotal() + pagesize - 1) / pagesize;
		if (pageIndex > 1) {
			if (suserlist == null || suserlist.size() == 0) {
				pageIndex--;
				suserlist();
			}
		}
		return SUCCESS;
	}
	
	
	/**
	 * 得到已删除学员列表
	 * 
	 * @return
	 */
	@Action(value = "/getdeleteStudent", results = { @Result(name = SUCCESS, location = "/deletestudentdetail.jsp") })
	public String getdeleteStudent() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		int pagesize = CommonUtils.parseInt(String.valueOf(session.getAttribute("pagesize")), 10);
		QueryResult<SuserInfo> result = suserService.getDeleteSuserInfos(pageIndex, pagesize);
		total = result.getTotal();
		suserlist = result.getDataList();
		for (int i = 0; i < suserlist.size(); i++) {
			if (!CommonUtils.isEmptyString(suserlist.get(i).getBirthday())) {
				int age = suserService.getSuserAgeByid(suserlist.get(i).getStudentid());
				suserlist.get(i).setAge(age);
			}
		}
		
		//添加城市
		for (int i = 0; i < suserlist.size(); i++) {
			if (!CommonUtils.isEmptyString(String.valueOf(suserlist.get(i).getCityid()))) {
				String city ="";
				if(!CommonUtils.isEmptyString(suserlist.get(i).getCityid())){
					city = suserService.getCityByCityid(Integer.parseInt(suserlist.get(i).getCityid()));
				}
				suserlist.get(i).setCity(city);;
			}
		}
		
		pageCount = ((int) result.getTotal() + pagesize - 1) / pagesize;
		if (pageIndex > 1) {
			if (suserlist == null || suserlist.size() == 0) {
				pageIndex--;
				suserlist();
			}
		}
		return SUCCESS;
	}
	
	
	


	/**
	 * 添加学员
	 */
	@Action(value = "/addStudentByPhone", results = { @Result(name = SUCCESS, location = "/getStudentDetailByPhone.do?newstudentphone=${newstudentphone}", type = "redirect") })
	public String addCoachByPhone() {

		SuserInfo suser = new SuserInfo();
		suser.setRealname(newstudentname);
		suser.setPhone(newstudentphone);
//		suser.setTotaltime(0);
		suser.setPassword("");
//		suser.setState(Constant.suser_UNCOMPLETE); // 设置状态
		suser.setAddtime(new Date()); // 设置添加时间
//		suser.setNewtasknoti(0); // 设置消息默认提醒
//		suser.setCancancel(0); // 设置是否可以取消订单
		suser.setFmoney(new BigDecimal(0)); // 冻结金额
		suser.setMoney(new BigDecimal(0)); // 金额
		suserService.addSuserInfo(suser);

		return SUCCESS;
	}
	
	
	/**
	 * 添加报名学员
	 */
	@Action(value = "/addEnrollStudentByPhone", results = { @Result(name = SUCCESS, location = "/getStudentDetailByPhone.do?newstudentphone=${newstudentphone}", type = "redirect") })
	public String addEnrollStudentByPhone() {

		SuserInfo suser = new SuserInfo();
		suser.setRealname(newstudentname);
		suser.setPhone(newstudentphone);
//		suser.setTotaltime(0);
		suser.setPassword("");
//		suser.setState(Constant.suser_UNCOMPLETE); // 设置状态
		suser.setAddtime(new Date()); // 设置添加时间
//		suser.setNewtasknoti(0); // 设置消息默认提醒
//		suser.setCancancel(0); // 设置是否可以取消订单
		suser.setFmoney(new BigDecimal(0)); // 冻结金额
		suser.setMoney(new BigDecimal(0)); // 金额
		
		suser.setState(1);
		suserService.addSuserInfo(suser);

		return SUCCESS;
	}


	/**
	 * 添加跟进记录
 	 */
	@Action(value = "/addContent", results = { @Result(name = SUCCESS, location = "/getUserState.do?studentid=${studentid}", type = "redirect") })
	public String addContent() {
		//System.out.print("已跳转到添加");
		
		SuserState suserSta = new SuserState();
		suserSta.setContent(addcontent);
		suserSta.setDealtime(new Date()); // 设置添加时间
		suserSta.setStudentid(studentid);
		suserSta.setDealpeopleid(dealpeopleid);
		
		suserService.addSuserState(suserSta);

		return SUCCESS;
	}





	/**
	 * 前往学员更换教练页面
	 * @return
	 */
	@Action(value = "goChangeCoach", results = { @Result(name = SUCCESS, location = "/changeCoach.jsp") })
	public String goChangeCoach() {
		return SUCCESS;
	}


	/**
	 * 学员更换教练
	 * @return
	 */
	@Action(value = "changeCoach")
	public void changeCoach() {
		suserService.changeCoach(studentid.toString(),oldcoachid.toString(),newcoachid.toString());
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("code", 1);
		strToJson(map);

	}



	/**
 * 通过手机号码得到一个学员的详情
 * enroll
 * @return
 */
@Action(value = "/getStudentDetailByPhone", results = { @Result(name = SUCCESS, location = "/singlestudent.jsp") })
public String getStudentDetailByPhone() {
	suser = suserService.getUserByPhone(newstudentphone);
	if(!CommonUtils.isEmptyString(suser.getBirthday())){
		int age = suserService.getSuserAgeByid(suser.getStudentid());
		suser.setAge(age);
	}
	if(suser.getCoachstate()==1){
		studentcheck=suserService.getcoachbycheck(suser.getStudentid());
		if(studentcheck!=null){
			cuser=cuserService.getCoachByid(studentcheck.getCoachid());
			if(cuser!=null){
				suser.setCuser(cuser);
			}
		}
	}
	
	
	return SUCCESS;
}



	/**
	 * 得到一个学员的详情
	 * 
	 * @return
	 */
	@Action(value = "/getStudentDetail", results = { @Result(name = SUCCESS, location = "/singlestudent.jsp") })
	public String getStudentDetail() {
		suser = suserService.getUserById(String.valueOf(studentid));
		if(!CommonUtils.isEmptyString(suser.getBirthday())){
			int age = suserService.getSuserAgeByid(suser.getStudentid());
			suser.setAge(age);
		}
		if(!CommonUtils.isEmptyString(suser.getCityid())){
			String city ="";
			city = suserService.getCityByCityid(Integer.parseInt(suser.getCityid()));
			suser.setCity(city);
		}
		if(suser.getCoachstate()==1){
			studentcheck=suserService.getcoachbycheck(suser.getStudentid());
			if(studentcheck!=null){
				cuser=cuserService.getCoachByid(studentcheck.getCoachid());
				if(cuser!=null){
					suser.setCuser(cuser);
				}
			}
		}
		return SUCCESS;
	}

	
	/**
	 * 得到跟进详情
	 * 
	 * @return
	 */
	@Action(value = "/getUserState", results = { @Result(name = SUCCESS, location = "/userstate.jsp") })
	public String getUserState() {
		suser = suserService.getUserById(String.valueOf(studentid));
		
		HttpSession session = ServletActionContext.getRequest().getSession();
		int pagesize = CommonUtils.parseInt(String.valueOf(session.getAttribute("pagesize")), 10);
		QueryResult<SuserState> result = suserService.getStateByStuid(String.valueOf(studentid),pageIndex, pagesize);
		total = result.getTotal();
		suserstalist = result.getDataList();
		
		//获得ID对应的处理人姓名
		for (int i = 0; i < suserstalist.size(); i++) {
			if (!CommonUtils.isEmptyString(String.valueOf(suserstalist.get(i).getDealpeopleid()))) {
				//suserstalist.get(i).setDealpeople((suserService.getDealpeopleById(String.valueOf(suserstalist.get(i).getDealpeopleid()))).getDealpeoplename());
				suserstalist.get(i).setDealpeople((suserService.getDealpeopleById(String.valueOf(suserstalist.get(i).getDealpeopleid()))).getRealname());
				
			}
		}
		
		pageCount = ((int) result.getTotal() + pagesize - 1) / pagesize;
		if (pageIndex > 1) {
			if (suserstalist == null || suserstalist.size() == 0) {
				pageIndex--;
				//suserstalist();
			}
		}
		
		return SUCCESS;
	}

	
	/**
	 * 删除某个报名学员
	 * 
	 * @return
	 */
	@Action(value = "/deleteUser", results = { @Result(name = SUCCESS, location = "/getEnrollStudentlist.do", type = "redirect") })
	public String deleteUser() {
		suser = suserService.getUserById(String.valueOf(studentid));
		suser.setState(6);
		suserService.updateUserInfo(suser);
		
		return SUCCESS;
	}
	
	/**
	 * 恢复某个已删除学员
	 * 
	 * @return
	 */
	@Action(value = "/recoverUser", results = { @Result(name = SUCCESS, location = "/getdeleteStudent.do", type = "redirect") })
	public String recoverUser() {
		suser = suserService.getUserById(String.valueOf(studentid));
		suser.setState(1);
		suserService.updateUserInfo(suser);
		
		return SUCCESS;
	}


	

	/**
	 * 修改学员金额
	 */
	@Action(value = "/setStudentMoney")
	public void setStudentMoney() {
		suser = suserService.getUserById(String.valueOf(studentid));
		if (suser == null) {
			setResponseStr("error");
		} else {
			suser.setMoney(new BigDecimal(studentmoney));
			suserService.updateUserInfo(suser);

			if (suser.getMoney().doubleValue() == studentmoney) {
				setResponseStr("success");
			} else {
				setResponseStr("error");
			}

		}
	}

	/**
	 * 获取学员提现申请列表
	 * 
	 * @return
	 */
	@Action(value = "/getStudentApplyList", results = { @Result(name = SUCCESS, location = "/studentapply.jsp") })
	public String getStudentApplyList() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		int pagesize = CommonUtils.parseInt(String.valueOf(session.getAttribute("pagesize")), 10);
		QueryResult<StudentApplyInfo> result = suserService.getStudentApplyList(pageIndex, pagesize);
		total = result.getTotal();
		stuApplyList = result.getDataList();
		pageCount = ((int) result.getTotal() + pagesize - 1) / pagesize;
		if (pageIndex > 1) {
			if (stuApplyList == null || stuApplyList.size() == 0) {
				pageIndex--;
				getStudentApplyList();
			}
		}
		return SUCCESS;
	}

	/**
	 * 学员提现申请审核
	 * 
	 * @return
	 */
	@Action(value = "/studentApplyCheckPass", results = { @Result(name = SUCCESS, location = "/getStudentApplyList.do?index=${index}&pageIndex=${pageIndex}", type = "redirect") })
	public String studentApplyCheckPass() {
		suserService.applyCheckPass(applyid);
		return SUCCESS;
	}
	
	/**
	 * 学员提现申请审核不通过
	 * 
	 * @return
	 */
	@Action(value = "/studentApplyCheckNoPass", results = { @Result(name = SUCCESS, location = "/getStudentApplyList.do?index=${index}&pageIndex=${pageIndex}", type = "redirect") })
	public String studentApplyCheckNoPass() {
		suserService.applyCheckNoPass(applyid);
		return SUCCESS;
	}
	
	/**
	 * 学员提现作废
	 * 
	 * @return
	 */
	@Action(value = "/stuapplyCheckrevocation", results = { @Result(name = SUCCESS, location = "/getStudentApplyList.do?index=${index}&pageIndex=${pageIndex}", type = "redirect") })
	public String stuapplyCheckrevocation() {
		suserService.applyCheckrevocation(applyid);
		return SUCCESS;
	}

	/**
	 * 根据关键字筛选学员信息
	 * 
	 * @return
	 */
	@Action(value = "/getStudentByKeyword", results = { @Result(name = SUCCESS, location = "/studentdetail.jsp") })
	public String getStudentByKeyword() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		int pagesize = CommonUtils.parseInt(String.valueOf(session.getAttribute("pagesize")), 10);
		QueryResult<SuserInfo> result = suserService.getStudentByKeyword(searchname, searchphone, minsdate, maxsdate, pageIndex, pagesize);
		total = result.getTotal();
		suserlist = result.getDataList();
		for (int i = 0; i < suserlist.size(); i++) {
			if (!CommonUtils.isEmptyString(suserlist.get(i).getBirthday())) {
				int age = suserService.getSuserAgeByid(suserlist.get(i).getStudentid());
				suserlist.get(i).setAge(age);
			}
		}
		pageCount = ((int) result.getTotal() + pagesize - 1) / pagesize;
		if (pageIndex > 1) {
			if (suserlist == null || suserlist.size() == 0) {
				pageIndex--;
				getStudentByKeyword();
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 根据关键字筛选报名学员信息
	 * 
	 * @return
	 */
	@Action(value = "/getEnrollStudentByKeyword", results = { @Result(name = SUCCESS, location = "/enrollstudentdetail.jsp") })
	public String getEnrollStudentByKeyword() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		int pagesize = CommonUtils.parseInt(String.valueOf(session.getAttribute("pagesize")), 10);
		QueryResult<SuserInfo> result = suserService.getEnrollStudentByKeyword(searchname, searchphone, minsdate, maxsdate, minenrollsdate, maxenrollsdate, pageIndex, pagesize);
		total = result.getTotal(); 
		suserlist = result.getDataList();
		for (int i = 0; i < suserlist.size(); i++) {
			if (!CommonUtils.isEmptyString(suserlist.get(i).getBirthday())) {
				int age = suserService.getSuserAgeByid(suserlist.get(i).getStudentid());
				suserlist.get(i).setAge(age);
			}
		}
		
		//添加城市
				for (int i = 0; i < suserlist.size(); i++) {
					if (!CommonUtils.isEmptyString(String.valueOf(suserlist.get(i).getCityid()))) {
						String city ="";
						if(!CommonUtils.isEmptyString(suserlist.get(i).getCityid())){
							city = suserService.getCityByCityid(Integer.parseInt(suserlist.get(i).getCityid()));
						}
						suserlist.get(i).setCity(city);;
					}
				}
		pageCount = ((int) result.getTotal() + pagesize - 1) / pagesize;
		if (pageIndex > 1) {
			if (suserlist == null || suserlist.size() == 0) {
				pageIndex--;
				getStudentByKeyword();
			}
		}
		return SUCCESS;
	}
	
	
	/**
	 * 根据关键字筛选已报名学员信息
	 * 
	 * @return
	 */
	@Action(value = "/getEnrolledStudentByKeyword", results = { @Result(name = SUCCESS, location = "/enrolledstudentdetail.jsp") })
	public String getEnrolledStudentByKeyword() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		int pagesize = CommonUtils.parseInt(String.valueOf(session.getAttribute("pagesize")), 10);
		QueryResult<SuserInfo> result = suserService.getEnrolledStudentByKeyword(searchname, searchphone, minsdate, maxsdate, minenrollsdate, maxenrollsdate,pageIndex, pagesize);
		total = result.getTotal();
		suserlist = result.getDataList();
		for (int i = 0; i < suserlist.size(); i++) {
			if (!CommonUtils.isEmptyString(suserlist.get(i).getBirthday())) {
				int age = suserService.getSuserAgeByid(suserlist.get(i).getStudentid());
				suserlist.get(i).setAge(age);
			}
		}
		
		//添加城市
				for (int i = 0; i < suserlist.size(); i++) {
					if (!CommonUtils.isEmptyString(String.valueOf(suserlist.get(i).getCityid()))) {
						String city ="";
						if(!CommonUtils.isEmptyString(suserlist.get(i).getCityid())){
							city = suserService.getCityByCityid(Integer.parseInt(suserlist.get(i).getCityid()));
						}
						suserlist.get(i).setCity(city);;
					}
				}
		pageCount = ((int) result.getTotal() + pagesize - 1) / pagesize;
		if (pageIndex > 1) {
			if (suserlist == null || suserlist.size() == 0) {
				pageIndex--;
				getStudentByKeyword();
			}
		}
		return SUCCESS;
	}
	
	
	/**
	 * 根据关键字筛选已删除学员信息
	 * 
	 * @return
	 */
	@Action(value = "/getDeleteStudentByKeyword", results = { @Result(name = SUCCESS, location = "/deletestudentdetail.jsp") })
	public String getDeleteStudentByKeyword() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		int pagesize = CommonUtils.parseInt(String.valueOf(session.getAttribute("pagesize")), 10);
		QueryResult<SuserInfo> result = suserService.getDeleteStudentByKeyword(searchname, searchphone, minsdate, maxsdate,minenrollsdate, maxenrollsdate, pageIndex, pagesize);
		total = result.getTotal();
		suserlist = result.getDataList();
		for (int i = 0; i < suserlist.size(); i++) {
			if (!CommonUtils.isEmptyString(suserlist.get(i).getBirthday())) {
				int age = suserService.getSuserAgeByid(suserlist.get(i).getStudentid());
				suserlist.get(i).setAge(age);
			}
		}
		
		//添加城市
				for (int i = 0; i < suserlist.size(); i++) {
					if (!CommonUtils.isEmptyString(String.valueOf(suserlist.get(i).getCityid()))) {
						String city ="";
						if(!CommonUtils.isEmptyString(suserlist.get(i).getCityid())){
							city = suserService.getCityByCityid(Integer.parseInt(suserlist.get(i).getCityid()));
						}
						suserlist.get(i).setCity(city);;
					}
				}
		pageCount = ((int) result.getTotal() + pagesize - 1) / pagesize;
		if (pageIndex > 1) {
			if (suserlist == null || suserlist.size() == 0) {
				pageIndex--;
				getStudentByKeyword();
			}
		}
		return SUCCESS;
	}
	
	
	
	
	/**
	 * 根据关键字筛选学员跟进信息
	 * 
	 * @return
	 */
	@Action(value = "/getStudentstateByKeyword", results = { @Result(name = SUCCESS, location = "/userstate.jsp") })
	public String getStudentstateByKeyword() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		int pagesize = CommonUtils.parseInt(String.valueOf(session.getAttribute("pagesize")), 5);
		QueryResult<SuserState> result = suserService.getStudentstateByKeyword(String.valueOf(studentid), pageIndex, pagesize);
		total = result.getTotal();
		suserstalist = result.getDataList();
		
		pageCount = ((int) result.getTotal() + pagesize - 1) / pagesize;
		if (pageIndex > 1) {
			if (suserstalist == null || suserstalist.size() == 0) {
				pageIndex--;
				getStudentstateByKeyword();
			}
		}
		return SUCCESS;
	}
	
	
	
	
	/**
	 * 获取学员余额列表
	 * 
	 * @return
	 */
	@Action(value = "/getStudentBalance", results = { @Result(name = SUCCESS, location = "/studentbalancelist.jsp") })
	public String getStudentBalance() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		int pagesize = CommonUtils.parseInt(String.valueOf(session.getAttribute("pagesize")), 10);
		QueryResult<SuserInfo> result = suserService.getStudentByKeyword(searchname, searchphone, minsdate, maxsdate, pageIndex, pagesize);
		total = result.getTotal();
		suserlist = result.getDataList();
		for (int i = 0; i < suserlist.size(); i++) {
			if (!CommonUtils.isEmptyString(suserlist.get(i).getBirthday())) {
				int age = suserService.getSuserAgeByid(suserlist.get(i).getStudentid());
				suserlist.get(i).setAge(age);
			}
		}
		pageCount = ((int) result.getTotal() + pagesize - 1) / pagesize;
		if (pageIndex > 1) {
			if (suserlist == null || suserlist.size() == 0) {
				pageIndex--;
				getStudentByKeyword();
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 添加学员余额
	 * @return
	 */
	@Action(value = "/addstudentbalance", results = { @Result(name = SUCCESS, location = "/getStudentBalance.do?pageIndex=${pageIndex}",type="redirect") })
	public String addstudentbalance(){
		if(checkbox.length!=0){
			for (int i = 0; i < checkbox.length; i++) {
				suser = suserService.getUserById(checkbox[i]);
				suser.setMoney(suser.getMoney().add(money));
				suserService.updateUserInfo(suser);
			}
			return SUCCESS;
		}
		return null;
	}
	
	/**
	 * 添加学员余额
	 * 
	 * @return
	 */
	@Action(value = "/setStudentBalance")
	public void setStudentBalance() {
		SuserInfo student = suserService.getUserById(studentid.toString());
		student.setMoney(student.getMoney().add(money));
		suserService.updateUserInfo(student);
		setResponseStr("success");
	}
	
	
	
	/**
	+	 * 账号验证
	+	 */
		@Action(value = "/checkStudentExistance")
		public void checkStudentExistance() {
			SuserInfo cuser = suserService.getUserByPhone(newstudentphone);
			if (cuser != null) {
				setResponseStr("error");
			} else {
				setResponseStr("success");
			}
		}
		
	/**
	 * 减少学员余额
	 * @return
	 * @throws Exception 
	 */
	@Action(value = "/lessenstudentbalance", results = { @Result(name = SUCCESS, location = "/getStudentBalance.do?pageIndex=${pageIndex}",type="redirect") })
	public String lessenstudentbalance(){
		int less = 0;
		if(checkbox.length!=0){
			for (int i = 0; i < checkbox.length; i++) {
				suser=suserService.getUserById(checkbox[i]);
				BigDecimal bigdecimal = suser.getMoney().subtract(money);
				if(bigdecimal.compareTo(BigDecimal.ZERO)==-1){
					less = -1;
				}
			}
			if(less!=-1){
				for (int i = 0; i < checkbox.length; i++) {
					suser=suserService.getUserById(checkbox[i]);
					BigDecimal bigdecimal = suser.getMoney().subtract(money);
					suser.setMoney(bigdecimal);
					suserService.updateUserInfo(suser);
				}
			}
			return SUCCESS;
		}
		return null;
	}
	
	/**
	 * 减少教练余额
	 * 
	 * @return
	 */
	@Action(value = "/lessenStudentBalance")
	public void lessenStudentBalance() {
		SuserInfo student = suserService.getUserById(studentid.toString());
		BigDecimal bigdecimal = student.getMoney().subtract(money);
		if(bigdecimal.compareTo(BigDecimal.ZERO)==-1){
			setResponseStr("error");
		}else{
			student.setMoney(bigdecimal);
			suserService.updateUserInfo(student);
			setResponseStr("success");
		}
	}

	/**
	 * 关键字搜索提现申请列表
	 * 
	 * @return
	 */
	@Action(value = "/getStudentApplyBySearch", results = { @Result(name = SUCCESS, location = "/studentapply.jsp") })
	public String getStudentApplyBySearch() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		int pagesize = CommonUtils.parseInt(String.valueOf(session.getAttribute("pagesize")), 10);
		if(state == null){
			state=0;
		}
		QueryResult<StudentApplyInfo> result = suserService.getCoachApplyBySearch(searchname, searchphone, amount, inputamount, state, minsdate, maxsdate, pageIndex, pagesize);
		total = result.getTotal();
		stuApplyList = result.getDataList();
		pageCount = ((int) result.getTotal() + pagesize - 1) / pagesize;
		if (pageIndex > 1) {
			if (stuApplyList == null || stuApplyList.size() == 0) {
				pageIndex--;
				getStudentApplyBySearch();
			}
		}
		return SUCCESS;
	}

	/**
	 * 获取提现历史列表
	 * 
	 * @return
	 */
	@Action(value = "/getStudentApplyRecordList", results = { @Result(name = SUCCESS, location = "/studentbalance.jsp") })
	public String getApplyRecordList() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		int pagesize = CommonUtils.parseInt(String.valueOf(session.getAttribute("pagesize")), 10);
		QueryResult<BalanceStudentInfo> result = suserService.getApplyRecordList(pageIndex, pagesize);
		total = result.getTotal();
		balanceStudentList = result.getDataList();
		pageCount = ((int) result.getTotal() + pagesize - 1) / pagesize;
		if (pageIndex > 1) {
			if (balanceStudentList == null || balanceStudentList.size() == 0) {
				pageIndex--;
				getApplyRecordList();
			}
		}
		return SUCCESS;
	}

	/**
	 * 关键字搜索历史提现列表
	 * 
	 * @return
	 */
	@Action(value = "/searchStudentBalance", results = { @Result(name = SUCCESS, location = "/studentbalance.jsp") })
	public String searchStudentBalance() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		int pagesize = CommonUtils.parseInt(String.valueOf(session.getAttribute("pagesize")), 10);
		QueryResult<BalanceStudentInfo> result = suserService.searchStudentBalance(searchname, searchphone, amount, inputamount, minsdate, maxsdate, pageIndex, pagesize);
		total = result.getTotal();
		balanceStudentList = result.getDataList();
		pageCount = ((int) result.getTotal() + pagesize - 1) / pagesize;
		if (pageIndex > 1) {
			if (balanceStudentList == null || balanceStudentList.size() == 0) {
				pageIndex--;
				searchStudentBalance();
			}
		}
		return SUCCESS;
	}

	/**
	 * 获取充值历史列表
	 * 
	 * @return
	 */
	@Action(value = "/getStudentRechargeList", results = { @Result(name = SUCCESS, location = "/studentrecharge.jsp") })
	public String getStudentRechargeList() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		int pagesize = CommonUtils.parseInt(String.valueOf(session.getAttribute("pagesize")), 10);
		QueryResult<BalanceStudentInfo> result = suserService.getRechargeRecordList(pageIndex, pagesize);
		total = result.getTotal();
		balanceStudentList = result.getDataList();
		pageCount = ((int) result.getTotal() + pagesize - 1) / pagesize;
		if (pageIndex > 1) {
			if (balanceStudentList == null || balanceStudentList.size() == 0) {
				pageIndex--;
				getStudentRechargeList();
			}
		}
		return SUCCESS;
	}

	/**
	 * 关键字搜索历史充值列表
	 * 
	 * @return
	 */
	@Action(value = "/searchStudentRecharge", results = { @Result(name = SUCCESS, location = "/studentrecharge.jsp") })
	public String searchStudentRecharge() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		int pagesize = CommonUtils.parseInt(String.valueOf(session.getAttribute("pagesize")), 10);
		QueryResult<BalanceStudentInfo> result = suserService.searchStudentRecharge(searchname, searchphone, amount, inputamount, minsdate, maxsdate, pageIndex, pagesize);
		total = result.getTotal();
		balanceStudentList = result.getDataList();
		pageCount = ((int) result.getTotal() + pagesize - 1) / pagesize;
		if (pageIndex > 1) {
			if (balanceStudentList == null || balanceStudentList.size() == 0) {
				pageIndex--;
				searchStudentRecharge();
			}
		}
		return SUCCESS;
	}

	@Action(value = "/jumpStudentToLead", results = { @Result(name = SUCCESS, location = "/studentdatatolead.jsp") })
	public String jumpDataToLead() {
		return SUCCESS;
	}

	@Action(value = "/studentdataToLead", results = { @Result(name = SUCCESS, location = "/datatolead.jsp") })
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
				int row = sheet.getRows();
				int col = sheet.getColumns();//总列数
				for (int i = 1; i <row; i++) {
					int id = 0;
					String password = "";
					String phone = null;
					String student_cardnum = null;
					String student_card_creat = null;
					String id_cardnum = null;
					String cityid = null;
					String address = null;
					Integer gender = 0;
					String birthday = null;
					String realname = null;
					String urgent_person = null;
					String urgent_phone = null;
					BigDecimal fmoney = new BigDecimal(0);
					BigDecimal money = new BigDecimal(0);
					String qq_openid = null;
					String wx_openid = null;
					String wb_openid = null;
					Integer state = 0;
					Integer coachstate = 0;
					float score = 0;
					for (int j =0;j<col;j++){
						String title = sheet.getCell(j, 0).getContents();
						if(title.equals("主键ID")){
							id = CommonUtils.parseInt(sheet.getCell(j, i).getContents(),0);
						}else if(title.equals("手机号")){
							phone = sheet.getCell(j, i).getContents();
						}else if(title.equals("身份证号")){
							id_cardnum = sheet.getCell(j, i).getContents();
						}else if(title.equals("真实姓名")){
							realname = sheet.getCell(j, i).getContents();
						}else if(title.equals("学生证或驾驶证号")){
							student_cardnum = sheet.getCell(j, i).getContents();
						}else if(title.equals("学员证制证时间")){
							student_card_creat = sheet.getCell(j, i).getContents().replaceAll("/", "-");
						}else if(title.equals("城市")){
							cityid = sheet.getCell(j, i).getContents();
						}else if(title.equals("地址")){
							address = sheet.getCell(j, i).getContents();
						}else if(title.equals("性别(1:男  2:女)")){
							if(CommonUtils.parseInt(sheet.getCell(j, i).getContents(), 0)!=0){
								gender = CommonUtils.parseInt(sheet.getCell(j, i).getContents(), 0);
							}
						}else if(title.equals("出生日期")){
							birthday = sheet.getCell(j, i).getContents().replaceAll("/", "-");
						}else if(title.equals("紧急联系人姓名")){
							urgent_person = sheet.getCell(j, i).getContents();
						}else if(title.equals("紧急联系人电话")){
							urgent_phone = sheet.getCell(j, i).getContents();
						}else if(title.equals("冻结金额")){
							if(CommonUtils.parseDouble(sheet.getCell(j, i).getContents(), 0d)!=0d){
								fmoney = new BigDecimal(CommonUtils.parseDouble(sheet.getCell(j, i).getContents(), 0d));
							}
						}else if(title.equals("账户余额")){
							if(CommonUtils.parseDouble(sheet.getCell(j, i).getContents(), 0d)!=0d){
								money = new BigDecimal(CommonUtils.parseDouble(sheet.getCell(j, i).getContents(), 0d));
							}
						}else if(title.equals("第三方qq登录id")){
							qq_openid = sheet.getCell(j, i).getContents();
						}else if(title.equals("第三方微信登录id")){
							wx_openid = sheet.getCell(j, i).getContents();
						}else if(title.equals("第三方微博登录id")){
							wb_openid = sheet.getCell(j, i).getContents();
						}else if(title.equals("审核状态(0:未审核  1：审核通过  2：审核未通过)")){
							if(CommonUtils.parseInt(sheet.getCell(j, i).getContents(), 0)!=0){
								state = CommonUtils.parseInt(sheet.getCell(j, i).getContents(), 0);
							}
						}else if(title.equals("教练确认状态(0:未认证  1：认证  2：审核未通过)")){
							if(CommonUtils.parseInt(sheet.getCell(j, i).getContents(), 0)!=0){
								coachstate = CommonUtils.parseInt(sheet.getCell(j, i).getContents(), 0);
							}
						}else if(title.equals("综合评分")){
							if(CommonUtils.parseFloat(sheet.getCell(j, i).getContents(), 0)!=0){
								score = CommonUtils.parseFloat(sheet.getCell(j, i).getContents(), 0);
							}
						}
					}
					SuserInfo student = new SuserInfo();
					SuserInfo student1 = suserService.getUserByPhone(phone);
					if (student1 == null) {
						student.setPhone(phone);
					} else {
						if(student_card_creat==null){
							student1.setStudent_card_creat(student1.getStudent_card_creat());
						}else{
							student1.setStudent_card_creat(student_card_creat);
						}
						if(student_cardnum==null){
							student1.setStudent_cardnum(student1.getStudent_cardnum());
						}else{
							SuserInfo student2 = suserService.getUserByStudent_cardnum(student_cardnum);
							if (student2 == null) {
								student1.setStudent_cardnum(student_cardnum);
							} else {
								continue;
							}
						}
						if(id_cardnum==null){
							student1.setId_cardnum(student1.getId_cardnum());
						}else{
							SuserInfo student3 = suserService.getUserById_cardnum(id_cardnum);
							if (student3 == null) {
								student1.setId_cardnum(id_cardnum);
							} else {
								continue;
							}
						}
						if(cityid==null){
							student1.setCityid(student1.getCityid());
						}else{
							student1.setCityid(cityid);
						}
						if(address==null){
							student1.setAddress(student1.getAddress());
						}else{
							student1.setAddress(address);
						}
						if(gender==0){
							student1.setGender(student1.getGender());
						}else{
							student1.setGender(gender);
						}
						if(birthday==null){
							student1.setBirthday(student1.getBirthday());
						}else{
							student1.setBirthday(birthday);
						}
						if(realname==null){
							student1.setRealname(student1.getRealname());
						}else{
							student1.setRealname(realname);
						}
						if(urgent_person==null){
							student1.setUrgent_person(student1.getUrgent_person());
						}else{
							student1.setUrgent_person(urgent_person);
						}
						if(urgent_phone==null){
							student1.setUrgent_phone(student1.getUrgent_phone());
						}else{
							student1.setUrgent_phone(urgent_phone);
						}
						if(fmoney==new BigDecimal(0)){
							student1.setFmoney(student1.getFmoney());
						}else{
							student1.setFmoney(fmoney);
						}
						if(money==new BigDecimal(0)){
							student1.setMoney(student1.getMoney());
						}else{
							student1.setMoney(money);
						}
						if(qq_openid==null){
							student1.setQq_openid(student1.getQq_openid());
						}else{
							student1.setQq_openid(qq_openid);
						}
						if(wx_openid==null){
							student1.setWx_openid(student1.getWx_openid());
						}else{
							student1.setWx_openid(wx_openid);
						}
						if(wb_openid==null){
							student1.setWb_openid(student1.getWb_openid());
						}else{
							student1.setWb_openid(wb_openid);
						}
						if(state==0){
							student1.setState(student1.getState());
						}else{
							student1.setState(state);
						}
						if(coachstate==0){
							student1.setCoachstate(student1.getCoachstate());
						}else{
							student1.setCoachstate(coachstate);
						}
						if(score==0){
							student1.setScore(student1.getScore());
						}else{
							student1.setScore(score);
						}
						student1.setAvatar(0);
						student1.setStudent_cardpicf(0);
						student1.setStudent_cardpicb(0);
						student1.setId_cardpicf(0);
						student1.setId_cardpicb(0);
						student1.setAddtime(new Date());
						student1.setCcheck_idcardpicf(0);
						student1.setCcheck_idcardpicb(0);
						student1.setCcheck_pic(0);
						suserService.updateUserInfo(student1);
						index++;
						continue;
					}
					SuserInfo student2 = suserService.getUserByStudent_cardnum(student_cardnum);
					if (student2 == null) {
						student.setStudent_cardnum(student_cardnum);
					} else {
						continue;
					}
					SuserInfo student3 = suserService.getUserById_cardnum(id_cardnum);
					if (student3 == null) {
						student.setId_cardnum(id_cardnum);
					} else {
						continue;
					}
					student.setStudent_card_creat(student_card_creat);
					student.setCityid(cityid);
					student.setAddress(address);
					student.setGender(gender);
					student.setBirthday(birthday);
					student.setRealname(realname);
					student.setUrgent_person(urgent_person);
					student.setUrgent_phone(urgent_phone);
					student.setFmoney(fmoney);
					student.setMoney(money);
					student.setQq_openid(qq_openid);
					student.setWx_openid(wx_openid);
					student.setWb_openid(wb_openid);
					student.setState(state);
					student.setCoachstate(coachstate);
					student.setScore(score);
					student.setAvatar(0);
					student.setStudent_cardpicf(0);
					student.setStudent_cardpicb(0);
					student.setId_cardpicf(0);
					student.setId_cardpicb(0);
					student.setAddtime(new Date());
					student.setCcheck_idcardpicf(0);
					student.setCcheck_idcardpicb(0);
					student.setCcheck_pic(0);
					suserService.addSuserInfo(student);
					index++;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			message = "成功添加或修改了"+index+"条数据！";
			CommonUtils.deleteFile(file);
		}
		request.setAttribute("message", message);
		return SUCCESS;
	}

	@Action(value = "/studentdataExport")
	public void dataExport() {
		suserlist = suserService.getStudentList();
		List<StudentInfoForExcel> excellist = new ArrayList<StudentInfoForExcel>();
		String [] data = studentdate.split(",");
		for (SuserInfo student : suserlist) {
			StudentInfoForExcel studentexcel = new StudentInfoForExcel();
			for(int i =0;i<data.length;i++){
				studentexcel.setId(student.getStudentid());
				studentexcel.setPhone(student.getPhone());
				studentexcel.setId_cardnum(student.getId_cardnum());
				studentexcel.setRealname(student.getRealname());
				if(data[i].equals("0")){
					studentexcel.setStudent_cardnum(student.getStudent_cardnum());
				}else if(data[i].equals("1")){
					studentexcel.setStudent_card_creat(student.getStudent_card_creat());
				}else if(data[i].equals("2")){
					studentexcel.setCity(student.getCityid());
				}else if(data[i].equals("3")){
					studentexcel.setAddress(student.getAddress());
				}else if(data[i].equals("4")){
					studentexcel.setGender(student.getGender());
				}else if(data[i].equals("5")){
					studentexcel.setBirthday(student.getBirthday());
				}else if(data[i].equals("6")){
					studentexcel.setUrgent_person(student.getUrgent_person());
				}else if(data[i].equals("7")){
					studentexcel.setUrgent_phone(student.getUrgent_phone());
				}else if(data[i].equals("8")){
					studentexcel.setFmoney(student.getFmoney().doubleValue());
				}else if(data[i].equals("9")){
					studentexcel.setMoney(student.getMoney().doubleValue());
				}else if(data[i].equals("10")){
					studentexcel.setQq_openid(student.getQq_openid());
				}else if(data[i].equals("11")){
					studentexcel.setWx_openid(student.getWx_openid());
				}else if(data[i].equals("12")){
					studentexcel.setWb_openid(student.getWb_openid()); 
				}else if(data[i].equals("13")){
					studentexcel.setState(student.getState());
				}else if(data[i].equals("14")){
					studentexcel.setCoachstate(student.getCoachstate());
				}else if(data[i].equals("15")){
					studentexcel.setScore(student.getScore());
				}
			}
			excellist.add(studentexcel);
		}
		String[] title = {"主键ID","手机号","身份证号","真实姓名","学生证或驾驶证号","学员证制证时间","城市","地址","性别(1:男  2:女)","出生日期","紧急联系人姓名","紧急联系人电话","冻结金额","账户余额","第三方qq登录id","第三方微信登录id","第三方微博登录id",
				"审核状态(0:未审核  1：审核通过  2：审核未通过)","教练确认状态(0:未认证  1：认证  2：审核未通过)", "综合评分" };
		String filename = CommonUtils.exportExcel("dataexport", title, excellist,data);
		filename = CommonUtils.properties.getProperty("uploadFilePath") + filename;
		HttpServletResponse response = ServletActionContext.getResponse();
		CommonUtils.downloadExcel(filename, "小巴学员信息表", response);
	}

	
	@Action(value = "/editsinglestudent", results = { @Result(name = SUCCESS, location = "/getStudentDetail.do?studentid=${studentid}&index=${index}&change_id=${change_id}&editsucc=1",type="redirect") })

	public String editSingleStudent(){
		suser = suserService.getUserById(String.valueOf(studentid));
		//修改姓名
		if (!CommonUtils.isEmptyString(editrealname)) {
			suser.setRealname(editrealname);
		}

		//修改电话
		if (!CommonUtils.isEmptyString(editphone)) {
			suser.setPhone(editphone);
		}
		//修改性别
		if (editgender!=null) {
			suser.setGender(editgender);
		}
		//修改生日
		if (!CommonUtils.isEmptyString(editbirthday)) {
			suser.setBirthday(editbirthday);
		}
		//修改城市
		if(!CommonUtils.isEmptyString(editcity)){
			suser.setCityid(editcity);
		}
		//修改地址
		if(!CommonUtils.isEmptyString(editaddress)){
			suser.setAddress(editaddress);
		}
		//修改紧急联系人
		if(!CommonUtils.isEmptyString(editurgent_person)){
			suser.setUrgent_person(editurgent_person);
		}
		//修改紧急联系人电话
		if(!CommonUtils.isEmptyString(editurgent_phone)){
			suser.setUrgent_phone(editurgent_phone);
		}
		//修改注册时间
		if(editaddtime!=null){
			suser.setAddtime(editaddtime);
		}
		//修改评分
		if(editscore!=0){
			suser.setScore(editscore);
		}
		//修改身份证号
		if(!CommonUtils.isEmptyString(editid_cardnum)){
			suser.setId_cardnum(editid_cardnum);
		}
		//修改学员证号
		if(!CommonUtils.isEmptyString(editstudent_cardnum)){
			suser.setStudent_cardnum(editstudent_cardnum);
		}
		//修改学员证日期
		if(!CommonUtils.isEmptyString(editstudent_card_creat)){
			suser.setStudent_card_creat(editstudent_card_creat);
		}
		//修改是否测试用户 0 普通用户  1 测试用户
		if(editusertype!=null){
			suser.setUsertype(editusertype);
		}
		
		//身份证正面照
		if(editid_cardpicf_url!=null){
			try {
				String	filepath = CommonUtils.uploadImg(editid_cardpicf_url, editid_cardpicf_urlFileName);
				if (!CommonUtils.isEmptyString(filepath)) {
					String fileurl = CommonUtils.getTimeFormat(new Date(), "yyyyMMdd") + File.separator;
					long fileid = baseService.uploadComplete(fileurl, filepath.substring(filepath.lastIndexOf("/") + 1));
					suser.setId_cardpicf((int)fileid);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		//身份证反面照
		if(editid_cardpicb_url!=null){
			try {
				String	filepath = CommonUtils.uploadImg(editid_cardpicb_url, editid_cardpicb_urlFileName);
				if (!CommonUtils.isEmptyString(filepath)) {
					String fileurl = CommonUtils.getTimeFormat(new Date(), "yyyyMMdd") + File.separator;
					long fileid = baseService.uploadComplete(fileurl, filepath.substring(filepath.lastIndexOf("/") + 1));
					suser.setId_cardpicb((int)fileid);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		//修改学员证
		if(editstudent_cardpicb_url!=null){
			try {
				String	filepath = CommonUtils.uploadImg(editstudent_cardpicb_url, editstudent_cardpicb_urlFileName);
				if (!CommonUtils.isEmptyString(filepath)) {
					String fileurl = CommonUtils.getTimeFormat(new Date(), "yyyyMMdd") + File.separator;
					long fileid = baseService.uploadComplete(fileurl, filepath.substring(filepath.lastIndexOf("/") + 1));
					suser.setStudent_cardpicb((int)fileid);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		suserService.updateUserInfo(suser);
		return SUCCESS;
	}
	
	
	//修改跟进内容
	@Action(value = "/editsuserstate", results = { @Result(name = SUCCESS, location = "/getUserState.do?studentid=${studentid}&index=${index}&change_id=${change_id}",type="redirect") })
	public String editsuserstate(){
		//suser = suserService.getUserById(String.valueOf(studentid));
		//suserSta = suserService.getStateByStuid(String.valueOf(studentid));
		//修改处理人
		if (editdealpeople!=null) {
			//suserSta.setRealname(editrealname);
			//suserSta.setDealpeople(editdealpeople);
			//suserSta.setDealpeopleid(editdealpeopleid);
		}
		//修改处理时间
		if (editdealtime!=null) {
			suserSta.setDealtime(editdealtime);
		}
		//修改跟进内容
		if (!CommonUtils.isEmptyString(editcontent)) {
			suserSta.setContent(editcontent);
		}
		
		suserService.updateUserState(suserSta);
		return SUCCESS;
	}
	
	
	
//修改跟进状态
	@Action(value = "/changestate", results = { @Result(name = SUCCESS, location = "/getUserState.do?studentid=${studentid}&index=${index}&change_id=${change_id}",type="redirect") })
	public String changestate(){
		suser = suserService.getUserById(String.valueOf(studentid));
		//suserSta = suserService.getStateByStuid(String.valueOf(studentid));
		
		suser.setState(2);
		
		if(addcontent.equals("")){
			addcontent = "报名完成";
		}
		
		//添加跟进内容
		SuserState suserSta = new SuserState();
		suserSta.setContent(addcontent);
		suserSta.setDealtime(new Date()); // 设置添加时间
		suserSta.setStudentid(studentid);
		suserSta.setDealpeopleid(dealpeopleid);
		
		suserService.addSuserState(suserSta);
		suserService.updateUserInfo(suser);
		return SUCCESS;
	}
  //后台重置验证码
	@Action(value = "/gotoresetVerCode", results = { @Result(name = SUCCESS, location = "/resetVerCode.jsp") })
	public String gotoresetVerCode(){
		return SUCCESS;
	}
  //后台重置验证码
	@Action(value = "/resetVerCode", results = { @Result(name = SUCCESS, location = "/gotoresetVerCode.do?flagresult=${flagresult}",type="redirect")})
	public String resetVerCode(){
	    flagresult=suserService.resetVerCode(rphone, rtype);
		return SUCCESS;
	}
		
	/**
	 * 学员驾校列表
	 * 
	 * @return
	 */
	@Action(value = "/getStudentSchool", results = { @Result(name = SUCCESS, location = "/studentschool.jsp") })
	public String getStudentSchool() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		int pagesize = CommonUtils.parseInt(String.valueOf(session.getAttribute("pagesize")), 10);
		QueryResult<SuserInfo> result = suserService.getStudentSchool(pageIndex, pagesize);
		total = result.getTotal();
		suserlist = result.getDataList();
		for (int i = 0; i < suserlist.size(); i++) {
			//添加驾校名
			if ((suserlist.get(i).getDschollid()) != null) {
				String dschoolname = suserService.getSuserSchoolByid(suserlist.get(i).getDschollid());
				suserlist.get(i).setDschoolname(dschoolname);
			}
			//添加年龄
			if (!CommonUtils.isEmptyString(suserlist.get(i).getBirthday())) {
				int age = suserService.getSuserAgeByid(suserlist.get(i).getStudentid());
				suserlist.get(i).setAge(age);
			}
			//添加城市
			if (!CommonUtils.isEmptyString(String.valueOf(suserlist.get(i).getCityid()))) {
				String city ="";
				if(!CommonUtils.isEmptyString(suserlist.get(i).getCityid())){
					city = suserService.getCityByCityid(Integer.parseInt(suserlist.get(i).getCityid()));
				}
				suserlist.get(i).setCity(city);;
			}
		}
		
		pageCount = ((int) result.getTotal() + pagesize - 1) / pagesize;
		if (pageIndex > 1) {
			if (suserlist == null || suserlist.size() == 0) {
				pageIndex--;
				suserlist();
			}
		}
		return SUCCESS;
	}
	
	
	/**
	 * 学员随机添加驾校
	 * 
	 * @return
	 */
	@Action(value = "/setStudentSchool", results = { @Result(name = SUCCESS, location = "/studentschool.jsp") })
	public String setStudentSchool() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		int pagesize = CommonUtils.parseInt(String.valueOf(session.getAttribute("pagesize")), 10);
		QueryResult<SuserInfo> result = suserService.setStudentSchool(pageIndex, pagesize);
		total = result.getTotal();
		suserlist = result.getDataList();
		for (int i = 0; i < suserlist.size(); i++) {
			
			//随机分配驾校
			if ((suserlist.get(i).getDschollid()) == null) {
				suser = suserService.getUserById(String.valueOf(suserlist.get(i).getStudentid()));
				int[] driveschoollist = {1,2,3,4,5,6};
				int index = (int) (Math.random() * driveschoollist.length);
				suser.setDschollid(driveschoollist[index]);
				suserService.updateUserInfo(suser);
			}
			//添加驾校名
			if ((suserlist.get(i).getDschollid()) != null) {
				String dschoolname = suserService.getSuserSchoolByid(suserlist.get(i).getDschollid());
				suserlist.get(i).setDschoolname(dschoolname);
			}
			//添加年龄
			if (!CommonUtils.isEmptyString(suserlist.get(i).getBirthday())) {
				int age = suserService.getSuserAgeByid(suserlist.get(i).getStudentid());
				suserlist.get(i).setAge(age);
			}
			//添加城市
			if (!CommonUtils.isEmptyString(String.valueOf(suserlist.get(i).getCityid()))) {
				//int age = suserService.getSuserAgeByid(suserlist.get(i).getStudentid());
				String city ="";
				if(!CommonUtils.isEmptyString(suserlist.get(i).getCityid())){
					city = suserService.getCityByCityid(Integer.parseInt(suserlist.get(i).getCityid()));
				}
				suserlist.get(i).setCity(city);;
			}
			
		}
		
		
		pageCount = ((int) result.getTotal() + pagesize - 1) / pagesize;
		if (pageIndex > 1) {
			if (suserlist == null || suserlist.size() == 0) {
				pageIndex--;
				suserlist();
			}
		}
		return SUCCESS;
	}
	
	
	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public long getTotal() {
		return total;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public List<SuserInfo> getSuserlist() {
		return suserlist;
	}

	public void setSuserlist(List<SuserInfo> suserlist) {
		this.suserlist = suserlist;
	}
	

	public Integer getDealpeopleid() {
		return dealpeopleid;
	}


	public void setDealpeopleid(Integer dealpeopleid) {
		this.dealpeopleid = dealpeopleid;
	}


	public List<SuserState> getSuserstalist() {
		return suserstalist;
	}


	public void setSuserstalist(List<SuserState> suserstalist) {
		this.suserstalist = suserstalist;
	}


	public double getStudentmoney() {
		return studentmoney;
	}

	public void setStudentmoney(double studentmoney) {
		this.studentmoney = studentmoney;
	}

	public SuserInfo getSuser() {
		return suser;
	}

	public void setSuser(SuserInfo suser) {
		this.suser = suser;
	}
	
//Susersta的get和set方法
	public SuserState getSuserSta() {
		return suserSta;
	}


	public void setSuserSta(SuserState suserSta) {
		this.suserSta = suserSta;
	}


	public int getStudentid() {
		return studentid;
	}

	public void setStudentid(int studentid) {
		this.studentid = studentid;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public List<StudentApplyInfo> getStuApplyList() {
		return stuApplyList;
	}

	public void setStuApplyList(List<StudentApplyInfo> stuApplyList) {
		this.stuApplyList = stuApplyList;
	}

	public int getApplyid() {
		return applyid;
	}

	public void setApplyid(int applyid) {
		this.applyid = applyid;
	}

	public int getChecknum() {
		return checknum;
	}

	public void setChecknum(int checknum) {
		this.checknum = checknum;
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
	
	

	public String getMinenrollsdate() {
		return minenrollsdate;
	}


	public void setMinenrollsdate(String minenrollsdate) {
		this.minenrollsdate = minenrollsdate;
	}


	public String getMaxenrollsdate() {
		return maxenrollsdate;
	}


	public void setMaxenrollsdate(String maxenrollsdate) {
		this.maxenrollsdate = maxenrollsdate;
	}


	public List<BalanceStudentInfo> getBalanceStudentList() {
		return balanceStudentList;
	}

	public void setBalanceStudentList(List<BalanceStudentInfo> balanceStudentList) {
		this.balanceStudentList = balanceStudentList;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
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

	public Integer getSearchagestate() {
		return searchagestate;
	}

	public void setSearchagestate(Integer searchagestate) {
		this.searchagestate = searchagestate;
	}

	public Integer getSearchage() {
		return searchage;
	}

	public void setSearchage(Integer searchage) {
		this.searchage = searchage;
	}

	public Float getSearchminscore() {
		return searchminscore;
	}

	public void setSearchminscore(Float searchminscore) {
		this.searchminscore = searchminscore;
	}

	public Float getSearchmaxscore() {
		return searchmaxscore;
	}

	public void setSearchmaxscore(Float searchmaxscore) {
		this.searchmaxscore = searchmaxscore;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	
	
	public String getEditdealpeople() {
		return editdealpeople;
	}


	public void setEditdealpeople(String editdealpeople) {
		this.editdealpeople = editdealpeople;
	}


	public Date getEditdealtime() {
		return editdealtime;
	}


	public void setEditdealtime(Date editdealtime) {
		this.editdealtime = editdealtime;
	}


	public String getEditcontent() {
		return editcontent;
	}


	public void setEditcontent(String editcontent) {
		this.editcontent = editcontent;
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

	public int getChange_id() {
		return change_id;
	}

	public void setChange_id(int change_id) {
		this.change_id = change_id;
	}

	public String getStudentdate() {
		return studentdate;
	}

	public void setStudentdate(String studentdate) {
		this.studentdate = studentdate;
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

	
	public String getEditstudent_cardnum() {
		return editstudent_cardnum;
	}

	
	public void setEditstudent_cardnum(String editstudent_cardnum) {
		this.editstudent_cardnum = editstudent_cardnum;
	}

	
	public String getEditstudent_card_creat() {
		return editstudent_card_creat;
	}

	
	public void setEditstudent_card_creat(String editstudent_card_creat) {
		this.editstudent_card_creat = editstudent_card_creat;
	}

	
	public String getEditid_cardpicf_urlFileName() {
		return editid_cardpicf_urlFileName;
	}

	
	public void setEditid_cardpicf_urlFileName(String editid_cardpicf_urlFileName) {
		this.editid_cardpicf_urlFileName = editid_cardpicf_urlFileName;
	}

	
	public File getEditid_cardpicf_url() {
		return editid_cardpicf_url;
	}

	
	public void setEditid_cardpicf_url(File editid_cardpicf_url) {
		this.editid_cardpicf_url = editid_cardpicf_url;
	}

	
	public String getEditid_cardpicb_urlFileName() {
		return editid_cardpicb_urlFileName;
	}

	
	public void setEditid_cardpicb_urlFileName(String editid_cardpicb_urlFileName) {
		this.editid_cardpicb_urlFileName = editid_cardpicb_urlFileName;
	}

	
	public File getEditid_cardpicb_url() {
		return editid_cardpicb_url;
	}

	
	public void setEditid_cardpicb_url(File editid_cardpicb_url) {
		this.editid_cardpicb_url = editid_cardpicb_url;
	}

	
	public String getEditstudent_cardpicb_urlFileName() {
		return editstudent_cardpicb_urlFileName;
	}

	
	public void setEditstudent_cardpicb_urlFileName(String editstudent_cardpicb_urlFileName) {
		this.editstudent_cardpicb_urlFileName = editstudent_cardpicb_urlFileName;
	}

	
	public File getEditstudent_cardpicb_url() {
		return editstudent_cardpicb_url;
	}

	
	public void setEditstudent_cardpicb_url(File editstudent_cardpicb_url) {
		this.editstudent_cardpicb_url = editstudent_cardpicb_url;
	}

	
	public StudentCheckInfo getStudentcheck() {
		return studentcheck;
	}

	
	public void setStudentcheck(StudentCheckInfo studentcheck) {
		this.studentcheck = studentcheck;
	}

	
	public CuserInfo getCuser() {
		return cuser;
	}

	
	public void setCuser(CuserInfo cuser) {
		this.cuser = cuser;
	}

	public String[] getCheckbox() {
		return checkbox;
	}

	public void setCheckbox(String[] checkbox) {
		this.checkbox = checkbox;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public String getNewstudentphone() {
		return newstudentphone;
	}

	public void setNewstudentphone(String newstudentphone) {
		this.newstudentphone = newstudentphone;
	}

	
	
	public String getAddcontent() {
		return addcontent;
	}


	public void setAddcontent(String addcontent) {
		this.addcontent = addcontent;
	}


	public String getNewstudentname() {
		return newstudentname;
	}

	public void setNewstudentname(String newstudentname) {
		this.newstudentname = newstudentname;
	}
	

	public String getEditphone() {
		return editphone;
	}

	public void setEditphone(String editphone) {
		this.editphone = editphone;
	}

	public String getOldcoachid() {
		return oldcoachid;
	}

	public void setOldcoachid(String oldcoachid) {
		this.oldcoachid = oldcoachid;
	}

	public String getNewcoachid() {
		return newcoachid;
	}

	public void setNewcoachid(String newcoachid) {
		this.newcoachid = newcoachid;
	}


	public String getRphone() {
		return rphone;
	}


	public void setRphone(String rphone) {
		this.rphone = rphone;
	}


	public int getRtype() {
		return rtype;
	}


	public void setRtype(int rtype) {
		this.rtype = rtype;
	}


	public int getFlagresult() {
		return flagresult;
	}


	public void setFlagresult(int flagresult) {
		this.flagresult = flagresult;
	}


	public Integer getEditusertype() {
		return editusertype;
	}


	public void setEditusertype(Integer editusertype) {
		this.editusertype = editusertype;
	}

	
	
}
