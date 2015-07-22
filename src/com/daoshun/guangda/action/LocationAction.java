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

import com.daoshun.guangda.pojo.Areas;
import com.daoshun.guangda.pojo.Cities;
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
public class LocationAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7110557588619079180L;
	@Resource
	private  LocationServiceImpl locationService;
	
	private List<Provinces> provincelist;
	
	private String provinceid;//省id
	private String cityid;//市id
	private String hotkey;//热键
	private String provinceName;//省名称
	
	@Action(value = "getProvince", results = {@Result(name = SUCCESS, location = "/location/location.jsp")})
	public String getPrivinces(){
		provincelist=locationService.getProvinces();
		return SUCCESS;
	}
	@Action(value = "getProvinceToJson")
	public void getPrivincesToJson(){
		List<Provinces> list=locationService.getProvinces();
		strToJson(list);
	}
	
	
	@Action(value = "getCityByProvinceId")
	public void getCityByProvinceId(){
		List<Cities> list=locationService.getCityByProvinceId(provinceid);
		strToJson(list);
	}
	@Action(value = "getCityByProvinceName")
	public void getCityByProvinceName(){
		List<Cities> list=locationService.getCityByProvinceName(provinceName);
		strToJson(list);
	}
	
	@Action(value = "getAreaByCityId")
	public void getAreaByCityId(){
		List<Areas> list=locationService.getAreaByCityId(cityid);
		strToJson(list);
	}
	@Action(value = "getCityByHotKey")
	public void getCityByHotKey(){
		List<Cities> list=locationService.getCityByHotKey(hotkey);
		strToJson(list);
	}
	public List<Provinces> getProvincelist() {
		return provincelist;
	}
	public void setProvincelist(List<Provinces> provincelist) {
		this.provincelist = provincelist;
	}
	public String getProvinceid() {
		return provinceid;
	}
	public void setProvinceid(String provinceid) {
		this.provinceid = provinceid;
	}
	public String getCityid() {
		return cityid;
	}
	public void setCityid(String cityid) {
		this.cityid = cityid;
	}
	public String getHotkey() {
		return hotkey;
	}
	public void setHotkey(String hotkey) {
		this.hotkey = hotkey;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	
}
