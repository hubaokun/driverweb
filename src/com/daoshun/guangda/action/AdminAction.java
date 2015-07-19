package com.daoshun.guangda.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.daoshun.guangda.pojo.AdminInfo;
import com.daoshun.guangda.pojo.DriveSchoolInfo;
import com.daoshun.guangda.pojo.PermissionSetInfo;
import com.daoshun.guangda.service.ICUserService;

@ParentPackage("default")
@Controller
@Scope("prototype")
public class AdminAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8624782821062846241L;

	@Resource
	private ICUserService cuserService;

	private List<AdminInfo> adminlist;

	private AdminInfo admin;

	private AdminInfo newadmin;

	private DriveSchoolInfo driveschool;

	private List<DriveSchoolInfo> driveSchoollist;

	private List<PermissionSetInfo> permissionSetInfos;

	// 翻页数据
	private long total;

	private Integer pageIndex = 1;

	private Integer pageCount = 0;

	// 关键字搜索的数据
	private String searchlogin;

	private String searchtelphone;

	// 管理员id
	private int adminid;

	// 编辑admin的数据
	private String newlogin;

	private String newrealname;

	private String newphone;

	private String secpassword;

	private String tirpassword;

	private Integer newschoolid;

	private String admin_login;

	private String admin_name;

	private String admin_password;

	private String admin_phone;

	private String admin_type;

	private String[] permession;

	private String permessions;

	private int index;

	private int formadminid;
	
	private int change_id;
	
	private int schoolid;

	/**
	 * 得到管理员列表
	 * 
	 * @return
	 */
	@Action(value = "/getAdminInfolist", results = { @Result(name = SUCCESS, location = "/adminlist.jsp") })
	public String getAdminInfolist() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		int pagesize = CommonUtils.parseInt(String.valueOf(session.getAttribute("pagesize")), 10);
		QueryResult<AdminInfo> result = cuserService.getAdmins(pageIndex, pagesize);
		total = result.getTotal();
		adminlist = result.getDataList();
		for (int i = 0; i < adminlist.size(); i++) {
			if (adminlist.get(i).getSchoolid() != 0) {
				driveschool = cuserService.getDriveSchoolInfoByid(adminlist.get(i).getSchoolid());
				adminlist.get(i).setSchoolname(driveschool.getName());
			}
			if (!CommonUtils.isEmptyString(adminlist.get(i).getPermission())) {
				String[] per = adminlist.get(i).getPermission().split(",");
				String perme = "";
				for (int j = 0; j < per.length; j++) {
					if (CommonUtils.parseInt(per[j], 0) == 1) {
						perme = perme + "管理员设置" + " ";
					}
					if (CommonUtils.parseInt(per[j], 0) == 2) {
						perme = perme + "用户管理" + " ";
					}
					if (CommonUtils.parseInt(per[j], 0) == 3) {
						perme = perme + "充值提现管理" + " ";
					}
					if (CommonUtils.parseInt(per[j], 0) == 4) {
						perme = perme + "系统配置" + " ";
					}
					if (CommonUtils.parseInt(per[j], 0) == 5) {
						perme = perme + "系统通知" + " ";
					}
					if (CommonUtils.parseInt(per[j], 0) == 6) {
						perme = perme + "订单管理" + " ";
					}
					if (CommonUtils.parseInt(per[j], 0) == 7) {
						perme = perme + "投诉管理" + " ";
					}
					if (CommonUtils.parseInt(per[j], 0) == 8) {
						perme = perme + "驾校管理" + " ";
					}
					if (CommonUtils.parseInt(per[j], 0) == 9) {
						perme = perme + "小巴券管理" + " ";
					}
					if (CommonUtils.parseInt(per[j], 0) == 10) {
						perme = perme + "日报管理" + " ";
					}
					if (CommonUtils.parseInt(per[j], 0) == 11) {
						perme = perme + "其他" + " ";
					}
				}
				adminlist.get(i).setPermissions(perme);
			}
		}
		pageCount = ((int) result.getTotal() + pagesize - 1) / pagesize;
		if (pageIndex > 1) {
			if (adminlist == null || adminlist.size() == 0) {
				pageIndex--;
				getAdminInfolist();
			}
		}
		return SUCCESS;
	}

	/**
	 * 关键字搜索管理员
	 * 
	 * @return
	 */
	@Action(value = "/getAdminInfolistByKeyword", results = { @Result(name = SUCCESS, location = "/adminlist.jsp") })
	public String getAdminInfolistByKeyword() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		int pagesize = CommonUtils.parseInt(String.valueOf(session.getAttribute("pagesize")), 10);
		QueryResult<AdminInfo> result = cuserService.getAdminsBykeyword(searchlogin, searchtelphone, pageIndex, pagesize);
		total = result.getTotal();
		adminlist = result.getDataList();
		for (int i = 0; i < adminlist.size(); i++) {
			if (adminlist.get(i).getSchoolid() != 0) {
				driveschool = cuserService.getDriveSchoolInfoByid(adminlist.get(i).getSchoolid());
				adminlist.get(i).setSchoolname(driveschool.getName());
			}
			if (!CommonUtils.isEmptyString(adminlist.get(i).getPermission())) {
				String[] per = adminlist.get(i).getPermission().split(",");
				String perme = "";
				for (int j = 0; j < per.length; j++) {
					if (CommonUtils.parseInt(per[j], 0) == 1) {
						perme = perme + "管理员设置" + " ";
					}
					if (CommonUtils.parseInt(per[j], 0) == 2) {
						perme = perme + "用户管理" + " ";
					}
					if (CommonUtils.parseInt(per[j], 0) == 3) {
						perme = perme + "充值提现管理" + " ";
					}
					if (CommonUtils.parseInt(per[j], 0) == 4) {
						perme = perme + "系统配置" + " ";
					}
					if (CommonUtils.parseInt(per[j], 0) == 5) {
						perme = perme + "系统通知" + " ";
					}
					if (CommonUtils.parseInt(per[j], 0) == 6) {
						perme = perme + "订单管理" + " ";
					}
					if (CommonUtils.parseInt(per[j], 0) == 7) {
						perme = perme + "投诉管理" + " ";
					}
					if (CommonUtils.parseInt(per[j], 0) == 8) {
						perme = perme + "驾校管理" + " ";
					}
					if (CommonUtils.parseInt(per[j], 0) == 9) {
						perme = perme + "其他" + " ";
					}
				}
				adminlist.get(i).setPermissions(perme);
			}
		}
		pageCount = ((int) result.getTotal() + pagesize - 1) / pagesize;
		if (pageIndex > 1) {
			if (adminlist == null || adminlist.size() == 0) {
				pageIndex--;
				getAdminInfolistByKeyword();
			}
		}
		return SUCCESS;
	}

	/**
	 * 删除管理员
	 */
	@Action(value = "/delAdmin")
	public void delAdmin() {
		admin = cuserService.getAdminInfoByid(adminid);
		HttpSession session = ServletActionContext.getRequest().getSession();
		int admin_id = (Integer) session.getAttribute("userid");
		if(adminid==admin_id){
			setResponseStr("error1");
		}else if(admin == null) {
			setResponseStr("error");
		} else {
			cuserService.delObject(admin);
			setResponseStr("success");
		}
	}

	/**
	 * 编辑管理员
	 */
	@Action(value = "/editAdmin")
	public void editAdmin() {
		admin = cuserService.getAdminInfoByid(adminid);
		if (admin == null) {
			setResponseStr("error");
			return;
		} else {
			if (!CommonUtils.isEmptyString(newlogin)) {
				admin.setLogin_account(newlogin);
			}
			if (!CommonUtils.isEmptyString(newrealname)) {
				admin.setRealname(newrealname);
			}
			if (!CommonUtils.isEmptyString(newphone)) {
				admin.setTelphone(newphone);
			}
			cuserService.updateObject(admin);
			setResponseStr("success");
		}
	}

	/**
	 * 修改密码
	 */
	@Action(value = "/changePsw")
	public void changePsw() {
		admin = cuserService.getAdminInfoByid(adminid);
		if (!tirpassword.equals(secpassword)) {
			setResponseStr("error2");
			return;
		} else {
			admin.setPassword(secpassword);
			cuserService.updateObject(admin);
			setResponseStr("success");
		}
	}

	/**
	 * 得到驾校列表
	 */
	@Action(value = "/getDriveSchool")
	public void getDriveSchool() {
		Map<String, Object> map = new HashMap<String, Object>();
		driveSchoollist = cuserService.getDriveSchoolInfo();
		map.put("list", driveSchoollist);
		map.put("state", 200);
		strToJson(map);
		setResponseStr("success");
	}

	/**
	 * 改变驾校
	 */
	@Action(value = "/changeschool")
	public void changeschool() {
		admin = cuserService.getAdminInfoByid(adminid);
		if (admin == null) {
			setResponseStr("error");
		} else {
			if (newschoolid != 0) {
				admin.setSchoolid(newschoolid);
				cuserService.updateObject(admin);
				setResponseStr("success");
			}
		}
	}

	/**
	 * 得到权限设置
	 */
	@Action(value = "/getPermission")
	public void getPermission() {
		Map<String, Object> map = new HashMap<String, Object>();
		permissionSetInfos = cuserService.getPermission();
		map.put("list", permissionSetInfos);
		map.put("state", 200);
		strToJson(map);
		setResponseStr("success");
	}

	/**
	 * 添加管理员
	 */
	@Action(value = "/addAdmin", results = { @Result(name = SUCCESS, location = "/getAdminInfolist.do", type = "redirect"), @Result(name = ERROR, location = "/getAdminInfolist.do", type = "redirect") })
	public String addAdmin() {
		AdminInfo admin = new AdminInfo();
		admin.setAddtime(new Date());
		if (!CommonUtils.isEmptyString(admin_login)) {
			newadmin = cuserService.getAdminInfoBylogin(admin_login);
			if (newadmin != null) {
				return ERROR;
			}
			admin.setLogin_account(admin_login);
		} else {
			return ERROR;
		}
		if (!CommonUtils.isEmptyString(admin_name)) {
			admin.setRealname(admin_name);
		} else {
			return ERROR;
		}
		if (!CommonUtils.isEmptyString(admin_password)) {
			admin.setPassword(admin_password);
		} else {
			return ERROR;
		}
		if (!CommonUtils.isEmptyString(admin_phone)) {
			admin.setTelphone(admin_phone);
		} else {
			return ERROR;
		}
		if (!CommonUtils.isEmptyString(admin_type)) {
			admin.setType(CommonUtils.parseInt(admin_type, 0));
		} else {
			admin.setType(0);
		}
		if(schoolid>0){
			admin.setSchoolid(schoolid);
		}else{
			admin.setSchoolid(0);
		}
		permessions = "";
		if (permession != null) {
			for (int i = 0; i < permession.length; i++) {
				if (i != (permession.length - 1)) {
					permessions = permessions + permession[i] + ",";
				} else {
					permessions = permessions + permession[i];
				}
			}
			admin.setPermission(permessions);
		}
		cuserService.addObject(admin);
		return SUCCESS;
	}

	/**
	 * 账号验证
	 */
	@Action(value = "/checklogin")
	public void checklogin() {
		newadmin = cuserService.getAdminInfoBylogin(admin_login);
		if (newadmin != null) {
			setResponseStr("error");
		} else {
			setResponseStr("success");
		}
	}

	@Action(value = "/changePermession", results = { @Result(name = SUCCESS, location = "/getAdminInfolist.do?pageIndex=${pageIndex}", type = "redirect"),
			@Result(name = ERROR, location = "/getAdminInfolist.do", type = "redirect") })
	public String changePermession() {
		admin = cuserService.getAdminInfoByid(formadminid);
		if (admin == null) {
			return ERROR;
		} else if (permession.length == 0) {
			return ERROR;
		} else {
			permessions = "";
			for (int i = 0; i < permession.length; i++) {
				if (i != (permession.length - 1)) {
					permessions = permessions + permession[i] + ",";
				} else {
					permessions = permessions + permession[i];
				}
			}
			admin.setPermission(permessions);
			cuserService.updateObject(admin);
			return SUCCESS;
		}
	}
	@Action(value = "/getMyPermession")
	public void getMyPermession(){
		Map<String, Object> map = new HashMap<String, Object>();
		permissionSetInfos=cuserService.getPermission();
		admin=cuserService.getAdminInfoByid(adminid);
		if(admin!=null){
			String[] per=admin.getPermission().split(",");
			map.put("per", per);
			map.put("permissions", permissionSetInfos);
			map.put("code", 1);
			strToJson(map);
		}else{
			map.put("code", 2);
			strToJson(map);
		}
	}

	public List<AdminInfo> getAdminlist() {
		return adminlist;
	}

	public void setAdminlist(List<AdminInfo> adminlist) {
		this.adminlist = adminlist;
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

	public long getTotal() {
		return total;
	}

	public String getSearchlogin() {
		return searchlogin;
	}

	public void setSearchlogin(String searchlogin) {
		this.searchlogin = searchlogin;
	}

	public String getSearchtelphone() {
		return searchtelphone;
	}

	public void setSearchtelphone(String searchtelphone) {
		this.searchtelphone = searchtelphone;
	}

	public int getAdminid() {
		return adminid;
	}

	public void setAdminid(int adminid) {
		this.adminid = adminid;
	}

	public AdminInfo getAdmin() {
		return admin;
	}

	public void setAdmin(AdminInfo admin) {
		this.admin = admin;
	}

	public String getNewlogin() {
		return newlogin;
	}

	public void setNewlogin(String newlogin) {
		this.newlogin = newlogin;
	}

	public String getNewrealname() {
		return newrealname;
	}

	public void setNewrealname(String newrealname) {
		this.newrealname = newrealname;
	}

	public String getNewphone() {
		return newphone;
	}

	public void setNewphone(String newphone) {
		this.newphone = newphone;
	}

	public String getSecpassword() {
		return secpassword;
	}

	public void setSecpassword(String secpassword) {
		this.secpassword = secpassword;
	}

	public String getTirpassword() {
		return tirpassword;
	}

	public void setTirpassword(String tirpassword) {
		this.tirpassword = tirpassword;
	}

	public DriveSchoolInfo getDriveschool() {
		return driveschool;
	}

	public void setDriveschool(DriveSchoolInfo driveschool) {
		this.driveschool = driveschool;
	}

	public List<DriveSchoolInfo> getDriveSchoollist() {
		return driveSchoollist;
	}

	public void setDriveSchoollist(List<DriveSchoolInfo> driveSchoollist) {
		this.driveSchoollist = driveSchoollist;
	}

	public Integer getNewschoolid() {
		return newschoolid;
	}

	public void setNewschoolid(Integer newschoolid) {
		this.newschoolid = newschoolid;
	}

	public List<PermissionSetInfo> getPermissionSetInfos() {
		return permissionSetInfos;
	}

	public void setPermissionSetInfos(List<PermissionSetInfo> permissionSetInfos) {
		this.permissionSetInfos = permissionSetInfos;
	}

	public String getAdmin_login() {
		return admin_login;
	}

	public void setAdmin_login(String admin_login) {
		this.admin_login = admin_login;
	}

	public String[] getPermession() {
		return permession;
	}

	public void setPermession(String[] permession) {
		this.permession = permession;
	}

	public String getAdmin_type() {
		return admin_type;
	}

	public void setAdmin_type(String admin_type) {
		this.admin_type = admin_type;
	}

	public String getAdmin_name() {
		return admin_name;
	}

	public void setAdmin_name(String admin_name) {
		this.admin_name = admin_name;
	}

	public String getAdmin_password() {
		return admin_password;
	}

	public void setAdmin_password(String admin_password) {
		this.admin_password = admin_password;
	}

	public String getAdmin_phone() {
		return admin_phone;
	}

	public void setAdmin_phone(String admin_phone) {
		this.admin_phone = admin_phone;
	}

	public AdminInfo getNewadmin() {
		return newadmin;
	}

	public void setNewadmin(AdminInfo newadmin) {
		this.newadmin = newadmin;
	}

	public String getPermessions() {
		return permessions;
	}

	public void setPermessions(String permessions) {
		this.permessions = permessions;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getFormadminid() {
		return formadminid;
	}

	public void setFormadminid(int formadminid) {
		this.formadminid = formadminid;
	}

	public int getChange_id() {
		return change_id;
	}

	public void setChange_id(int change_id) {
		this.change_id = change_id;
	}

	public int getSchoolid() {
		return schoolid;
	}

	public void setSchoolid(int schoolid) {
		this.schoolid = schoolid;
	}
	
	
}
