package com.daoshun.guangda.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.daoshun.guangda.pojo.AreaInfo;
import com.daoshun.guangda.pojo.CityInfo;
import com.daoshun.guangda.pojo.ProvinceInfo;
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
	
	private List<ProvinceInfo> provincelist;
	
	private String provinceid;//省id
	private String schoolprovinceid;//所属驾校的省id
	private String cityid;//市id
	private String hotkey;//热键
	private String provinceName;//省名称
	//查询所有的省信息
	@Action(value = "getProvince", results = {@Result(name = SUCCESS, location = "/location.jsp")})
	public String getPrivinces(){
		provincelist=locationService.getProvinces();
		return SUCCESS;
	}
	//查询所有的省信息的JSON格式
	@Action(value = "getProvinceToJson")
	public void getPrivincesToJson(){
		List<ProvinceInfo> list=locationService.getProvinces();
		strToJson(list);
	}
	
	//按省ID查询市
	@Action(value = "getCityByProvinceId")
	public void getCityByProvinceId(){
		List<CityInfo> list=locationService.getCityByProvinceId(provinceid);
		strToJson(list);
	}
//驾校按省ID查询市
	/*@Action(value = "getSchoolCityByProvinceId")
	public void getSchoolCityByProvinceId(){
		List<CityInfo> list=locationService.getCityByProvinceId(provinceid);
		strToJson(list);
	}*/
	
	//按省名称查询市
	@Action(value = "getCityByProvinceName")
	public void getCityByProvinceName(){
		List<CityInfo> list=locationService.getCityByProvinceName(provinceName);
		strToJson(list);
	}
	//按市ID查询区
	@Action(value = "getAreaByCityId")
	public void getAreaByCityId(){
		List<AreaInfo> list=locationService.getAreaByCityId(cityid);
		strToJson(list);
	}
	//按热键查询市
	@Action(value = "getCityByHotKey")
	public void getCityByHotKey(){
		List<CityInfo> list=locationService.getCityByHotKey(hotkey);
		strToJson(list);
	}
	//按城市id查省
		@Action(value="getProvinceByCityId")
		public void getProvinceByCityId()
		{
			String cid=request.getParameter("cid");
			if(cid!=null && !"".equals(cid))
			{
				ProvinceInfo info=locationService.getProvinceByCityId(cid);
				strToJson(info);
			}
		}
	public List<ProvinceInfo> getProvincelist() {
		return provincelist;
	}
	public void setProvincelist(List<ProvinceInfo> provincelist) {
		this.provincelist = provincelist;
	}
	public String getProvinceid() {
		return provinceid;
	}
	public void setProvinceid(String provinceid) {
		this.provinceid = provinceid;
	}
	
	public String getSchoolprovinceid() {
		return schoolprovinceid;
	}
	public void setSchoolprovinceid(String schoolprovinceid) {
		this.schoolprovinceid = schoolprovinceid;
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
