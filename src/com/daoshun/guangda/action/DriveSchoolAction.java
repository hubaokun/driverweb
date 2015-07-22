package com.daoshun.guangda.action;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.daoshun.common.CommonUtils;
import com.daoshun.common.QueryResult;
import com.daoshun.guangda.pojo.CuserInfo;
import com.daoshun.guangda.pojo.DriveSchoolInfo;
import com.daoshun.guangda.pojo.Provinces;
import com.daoshun.guangda.pojo.SchoolBalance;
import com.daoshun.guangda.service.ICUserService;
import com.daoshun.guangda.service.ICtaskService;
import com.daoshun.guangda.service.IDriveSchoolService;
import com.daoshun.guangda.serviceImpl.LocationServiceImpl;

@ParentPackage("default")
@Controller
@Scope("prototype")
public class DriveSchoolAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7110557588619079180L;

	@Resource
	private ICUserService cuserService;

	@Resource
	private ICtaskService ctaskService;
	
	@Resource
	private IDriveSchoolService driveSchoolService;

	private List<DriveSchoolInfo> driveSchoollist;
	
	private List<CuserInfo> cuserlist;

	private DriveSchoolInfo driveSchoolInfo;

	private DriveSchoolInfo secdriveSchoolInfo;
	
	private int schoolid;
	
	private BigDecimal transfarmoney;

	private String schoolname;

	private String schoolphone;

	private String schoolcontact;

	private String alipay_account;
	
	private Integer order_pull;

	private String oldschoolname;

	private String editschoolname;

	private String editschoolphone;

	private String editschoolcontact;

	private String editalipay_account;
	
	private Integer editorder_pull;

	private int index;
	
	private int change_id;

	private Integer pageIndex = 1;
	
	private int pageCount = 0;
	
	private long total;
	
	private String starttime;
	
	private String endtime;
	
	private Integer type;
	
	private List<SchoolBalance> schoolbalancelist;
	
	private String schoolkeyword;
	
	private String coachnamekeyword;
	
	private int changetype;
	
	private List<Provinces> provincelist;//添加省市区的省列表
	@Resource
	private  LocationServiceImpl locationService;//省市区的业务对象
	
	private String province;
	
	private String city;
	private String area;
	
	
	
	/**
	 * 得到驾校列表
	 * 
	 * @return
	 */
	@Action(value = "getdriveSchool", results = { @Result(name = SUCCESS, location = "/driveschoollist.jsp") })
	public String getdriveSchool() {
		QueryResult<DriveSchoolInfo> result = cuserService.getDriveSchoolInfoByPage(pageIndex, 10);
		driveSchoollist = result.getDataList();
		//provincelist=locationService.getProvinces();
		total = (int)result.getTotal();
		pageCount = (int) ((total + 9)/10);
		if(pageIndex>1){
			if(driveSchoollist==null||driveSchoollist.size()==0){
				pageIndex--;
				getdriveSchool();
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 得到指定驾校
	 * 
	 * @return
	 */
	@Action(value = "getdriveSchoolById", results = { @Result(name = SUCCESS, location = "/driveschoollist.jsp") })
	public String getdriveSchoolById() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		schoolid = CommonUtils.parseInt(String.valueOf(session.getAttribute("schoolid")), 0);
		driveSchoollist = cuserService.getDriveSchoolListById(schoolid);
		return SUCCESS;
	}

	@Action(value = "schoolmoneymanage", results = {@Result(name = SUCCESS, location = "/schoolmoneymanage.jsp")})
	public String schoolmoneymanage(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		int driveschoolid = CommonUtils.parseInt(String.valueOf(session.getAttribute("schoolid")),0);
		if(driveschoolid!=0){
			schoolid = driveschoolid;
			driveSchoollist = cuserService.getDriveSchoolListById(schoolid);
			total = 1;
			return SUCCESS;
		}else{
			QueryResult<DriveSchoolInfo> result = cuserService.getDriveSchoolInfoByPage(pageIndex,10);
			driveSchoollist = result.getDataList();
			pageCount = (int)(result.getTotal() + 9)/10;
			if(pageIndex>1){
				if(driveSchoollist==null||driveSchoollist.size()==0){
					pageIndex--;
					schoolmoneymanage();
				}
			}
			total = result.getTotal();
			return SUCCESS;
		}
	}
	
	@Action(value = "getschoolRechargeList", results = {@Result(name = SUCCESS, location = "/schooltransfarhistory.jsp")})
	public String getschoolRechargeList(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		int driveschoolid = CommonUtils.parseInt(String.valueOf(session.getAttribute("schoolid")),0);
		if(driveschoolid!=0){
			schoolid = driveschoolid;
			changetype = 1;
		}
		QueryResult<SchoolBalance> result = driveSchoolService.getSchoolBalanceByPage(pageIndex, 10, schoolid, type,starttime, endtime);
		schoolbalancelist = result.getDataList();
		total = result.getTotal();
		pageCount = (int) ((result.getTotal() + 9 )/10) ;
		driveSchoollist = cuserService.getDriveSchoolInfo();
		return SUCCESS;
	}
	
	@Action(value = "schooltransfarmoney")
	public void schooltransfarmoney(){
		driveSchoolInfo = ctaskService.getDriveSchoolByid(schoolid);
		if(driveSchoolInfo!=null){
			driveSchoolInfo.setMoney(driveSchoolInfo.getMoney().subtract(transfarmoney));
			cuserService.updateObject(driveSchoolInfo);
			SchoolBalance schoolbalance = new SchoolBalance();
			schoolbalance.setAmount(transfarmoney);
			schoolbalance.setSchoolid(schoolid);
			schoolbalance.setCoachid(0);
			schoolbalance.setType(2);
			schoolbalance.setAddtime(new Date());
			cuserService.addSchoolBalance(schoolbalance);
			setResponseStr("success");
		}else{
			setResponseStr("error");
		}
	}
	
	/**
	 * 添加驾校信息
	 */
	@Action(value = "addSchool")
	public void addSchool() {
		driveSchoolInfo = ctaskService.getDriveSchoolByname(schoolname);
		if (driveSchoolInfo != null) {
			setResponseStr("error");
			return;
		} else {
			DriveSchoolInfo driveSchoolInfo = new DriveSchoolInfo();
			if (CommonUtils.isEmptyString(schoolname)) {
				setResponseStr("error1");
				return;
			} else {
				driveSchoolInfo.setName(schoolname);
			}
			if (CommonUtils.isEmptyString(schoolphone)) {
				setResponseStr("error1");
				return;
			} else {
				driveSchoolInfo.setTelphone(schoolphone);
			}
			if(order_pull!=null){
				driveSchoolInfo.setOrder_pull(order_pull);
			}else{
				driveSchoolInfo.setOrder_pull(0);
			}
			if (!CommonUtils.isEmptyString(schoolcontact)) {
				driveSchoolInfo.setContact(schoolcontact);
			}
			if (!CommonUtils.isEmptyString(alipay_account)) {
				driveSchoolInfo.setAlipay_account(alipay_account);
			}
			driveSchoolInfo.setMoney(new BigDecimal(0));
			driveSchoolInfo.setAddtime(new Date());
			driveSchoolInfo.setProvince(province);
			driveSchoolInfo.setCity(city);
			driveSchoolInfo.setArea(area);
			cuserService.addObject(driveSchoolInfo);
			setResponseStr("success");
		}
	}

	/**
	 * 删除驾校信息
	 */
	@Action(value = "delSchool")
	public void delSchool() {
		driveSchoolInfo = ctaskService.getDriveSchoolByname(schoolname);
		if (driveSchoolInfo == null) {
			setResponseStr("error");
			return;
		} else {
			cuserService.delObject(driveSchoolInfo);
			setResponseStr("success");
		}
	}

	/**
	 * 修改驾校信息
	 */
	@Action(value = "editSchool")
	public void editSchool() {
		driveSchoolInfo = ctaskService.getDriveSchoolByname(oldschoolname);// 原驾校信息
		if (driveSchoolInfo == null) {
			setResponseStr("error");
			return;
		} else {
			if (CommonUtils.isEmptyString(editschoolname)) {
				// 编辑的名称为空
				if (!CommonUtils.isEmptyString(editschoolphone)) {
					driveSchoolInfo.setTelphone(editschoolphone);
				}
				if (!CommonUtils.isEmptyString(editschoolcontact)) {
					driveSchoolInfo.setContact(editschoolcontact);
				}
				if (!CommonUtils.isEmptyString(editalipay_account)) {
					driveSchoolInfo.setAlipay_account(editalipay_account);
				}
				
				if(editorder_pull!=null){
					driveSchoolInfo.setOrder_pull(editorder_pull);
				}
				if (CommonUtils.isEmptyString(editschoolphone) && CommonUtils.isEmptyString(editschoolcontact) 
						&& CommonUtils.isEmptyString(editalipay_account)&&editorder_pull==null
						&& driveSchoolInfo.getProvince().equals(province) && driveSchoolInfo.getCity().equals(city)
						&& driveSchoolInfo.getArea().equals(area)
						) {
					setResponseStr("error2");
					return;
				} else {
					if (!CommonUtils.isEmptyString(province)) {
						driveSchoolInfo.setProvince(province);
					}
					if (!CommonUtils.isEmptyString(city)) {
						driveSchoolInfo.setCity(city);
					}
					if (!CommonUtils.isEmptyString(area)) {
						driveSchoolInfo.setArea(area);
					}
					cuserService.updateObject(driveSchoolInfo);
					setResponseStr("success");
				}
			} else {
				secdriveSchoolInfo = ctaskService.getDriveSchoolByname(editschoolname);// 修改后的名称存不存在
				if (secdriveSchoolInfo == null) {
					driveSchoolInfo.setName(editschoolname);
					if (!CommonUtils.isEmptyString(editschoolphone)) {
						driveSchoolInfo.setTelphone(editschoolphone);
					}
					if (!CommonUtils.isEmptyString(editschoolcontact)) {
						driveSchoolInfo.setContact(editschoolcontact);
					}
					if (!CommonUtils.isEmptyString(editalipay_account)) {
						driveSchoolInfo.setAlipay_account(editalipay_account);
					}
					if(editorder_pull!=null){
						driveSchoolInfo.setOrder_pull(editorder_pull);
					}
					if (!CommonUtils.isEmptyString(province)) {
						driveSchoolInfo.setProvince(province);
					}
					if (!CommonUtils.isEmptyString(city)) {
						driveSchoolInfo.setCity(city);
					}
					if (!CommonUtils.isEmptyString(area)) {
						driveSchoolInfo.setArea(area);
					}
					cuserService.updateObject(driveSchoolInfo);
					setResponseStr("success");
				} else {
					setResponseStr("error1");
					return;
				}
			}
		}
	}

	@Action(value = "searchschoolname")
	public void searchschoolname(){
			driveSchoollist=driveSchoolService.getDriveschoollist(schoolkeyword);
			HashMap<String,Object> map = new HashMap<String,Object>();
			map.put("driveSchoollist", driveSchoollist);
			strToJson(map);
		
	}
	
	@Action(value = "searchcoachname")
	public void searchcoachname(){
			cuserlist=driveSchoolService.getCuserListByKeyname(coachnamekeyword);
			HashMap<String,Object> map = new HashMap<String,Object>();
			map.put("cuserlist", cuserlist);
			strToJson(map);
		
	}
	
	
	
	public List<CuserInfo> getCuserlist() {
		return cuserlist;
	}

	
	public void setCuserlist(List<CuserInfo> cuserlist) {
		this.cuserlist = cuserlist;
	}

	
	public String getCoachnamekeyword() {
		return coachnamekeyword;
	}

	
	public void setCoachnamekeyword(String coachnamekeyword) {
		this.coachnamekeyword = coachnamekeyword;
	}

	public String getSchoolkeyword() {
		return schoolkeyword;
	}

	
	public void setSchoolkeyword(String schoolkeyword) {
		this.schoolkeyword = schoolkeyword;
	}

	public List<DriveSchoolInfo> getDriveSchoollist() {
		return driveSchoollist;
	}

	public void setDriveSchoollist(List<DriveSchoolInfo> driveSchoollist) {
		this.driveSchoollist = driveSchoollist;
	}

	public String getSchoolname() {
		return schoolname;
	}

	public void setSchoolname(String schoolname) {
		this.schoolname = schoolname;
	}

	public String getSchoolphone() {
		return schoolphone;
	}

	public void setSchoolphone(String schoolphone) {
		this.schoolphone = schoolphone;
	}

	public DriveSchoolInfo getDriveSchoolInfo() {
		return driveSchoolInfo;
	}

	public void setDriveSchoolInfo(DriveSchoolInfo driveSchoolInfo) {
		this.driveSchoolInfo = driveSchoolInfo;
	}

	public DriveSchoolInfo getSecdriveSchoolInfo() {
		return secdriveSchoolInfo;
	}

	public void setSecdriveSchoolInfo(DriveSchoolInfo secdriveSchoolInfo) {
		this.secdriveSchoolInfo = secdriveSchoolInfo;
	}

	public String getOldschoolname() {
		return oldschoolname;
	}

	public void setOldschoolname(String oldschoolname) {
		this.oldschoolname = oldschoolname;
	}

	public String getEditschoolname() {
		return editschoolname;
	}

	public void setEditschoolname(String editschoolname) {
		this.editschoolname = editschoolname;
	}

	public String getEditschoolphone() {
		return editschoolphone;
	}

	public void setEditschoolphone(String editschoolphone) {
		this.editschoolphone = editschoolphone;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getSchoolcontact() {
		return schoolcontact;
	}

	public void setSchoolcontact(String schoolcontact) {
		this.schoolcontact = schoolcontact;
	}

	public String getEditschoolcontact() {
		return editschoolcontact;
	}

	public void setEditschoolcontact(String editschoolcontact) {
		this.editschoolcontact = editschoolcontact;
	}

	public String getAlipay_account() {
		return alipay_account;
	}

	public void setAlipay_account(String alipay_account) {
		this.alipay_account = alipay_account;
	}

	public String getEditalipay_account() {
		return editalipay_account;
	}

	public void setEditalipay_account(String editalipay_account) {
		this.editalipay_account = editalipay_account;
	}

	
	public Integer getOrder_pull() {
		return order_pull;
	}

	
	public void setOrder_pull(Integer order_pull) {
		this.order_pull = order_pull;
	}

	
	public Integer getEditorder_pull() {
		return editorder_pull;
	}

	
	public void setEditorder_pull(Integer editorder_pull) {
		this.editorder_pull = editorder_pull;
	}

	public int getChange_id() {
		return change_id;
	}

	public void setChange_id(int change_id) {
		this.change_id = change_id;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public int getSchoolid() {
		return schoolid;
	}

	public void setSchoolid(int schoolid) {
		this.schoolid = schoolid;
	}

	public BigDecimal getTransfarmoney() {
		return transfarmoney;
	}

	public void setTransfarmoney(BigDecimal transfarmoney) {
		this.transfarmoney = transfarmoney;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public List<SchoolBalance> getSchoolbalancelist() {
		return schoolbalancelist;
	}

	public void setSchoolbalancelist(List<SchoolBalance> schoolbalancelist) {
		this.schoolbalancelist = schoolbalancelist;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public int getChangetype() {
		return changetype;
	}

	public void setChangetype(int changetype) {
		this.changetype = changetype;
	}

	public List<Provinces> getProvincelist() {
		return provincelist;
	}

	public void setProvincelist(List<Provinces> provincelist) {
		this.provincelist = provincelist;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	
	
}
