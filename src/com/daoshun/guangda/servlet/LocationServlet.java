package com.daoshun.guangda.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.daoshun.common.Constant;
import com.daoshun.common.ErrException;
import com.daoshun.guangda.pojo.AreaInfo;
import com.daoshun.guangda.pojo.AutoPositionInfo;
import com.daoshun.guangda.pojo.CityInfo;
import com.daoshun.guangda.pojo.ProvinceInfo;
import com.daoshun.guangda.service.ILocationService;
/**
 * 省市区 服务接口  for app
 * @author 卢磊
 *
 */
@WebServlet("/location")
public class LocationServlet extends BaseServlet{
	private static final long serialVersionUID = 1L;
	private ILocationService locationService;
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		locationService = (ILocationService) applicationContext.getBean("locationService");
	}
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String action = getAction(request);
			if (Constant.GETPROVINCE.equals(action)) {
				// 获取所有的省名称
				getProvince(request,resultMap);
			}else if (Constant.GETCITYBYPROID.equals(action)) {
				// 按省编号查询市
				getCityByProvinceId(request,resultMap);
			}else if (Constant.GETCITYBYHOTKEY.equals(action)) {
				//按热键查询市
				getCityByHotKey(request,resultMap);
			}else if (Constant.GETCITYBYCNAME.equals(action)) {
				// 按市名称模糊查询市
				getCityByCName(request,resultMap);
			}else if (Constant.GETAREABYCITYID.equals(action)) {
				// 按市编号查询区
				getAreaByCityId(request,resultMap);
			}else if (Constant.GETAREABYAREAID.equals(action)) {
				// 按市编号查询区
				getAreaByAreaId(request,resultMap);
			}else if (Constant.GETPROCITYAREA.equals(action)) {
				//返回所有的省市区JSON
				getProCityArea(request,resultMap);
			}
			else if(Constant.GETAUTOPOSITION.equals(action))
			{
				getAutoPosition(request,resultMap);
				//自动获取当前位置省市区信息
			}
		} catch (ErrException e) {
			e.printStackTrace();
			setResultWhenException(response, e.getMessage());
		}
		setResult(response, resultMap);
	
	}
	/**
	 *  获取所有的省名称
	 */
	public void getProvince(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		List<ProvinceInfo> list=locationService.getProvinces();
		resultMap.put("provincelist", list);
	}
	/**
	 * 按省编号查询市
	 */
	public void getCityByProvinceId(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String provinceId= getRequestParamter(request, "provinceid");
		List<CityInfo> list=locationService.getCityByProvinceId(provinceId);
		resultMap.put("citylist", list);
	}
	/**
	 * 按热键查询市
	 */
	public void getCityByHotKey(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String hotkey= getRequestParamter(request, "hotkey");
		List<CityInfo> list=locationService.getCityByHotKey(hotkey);
		resultMap.put("citylist", list);
	}
	/**
	 * 按市名称模糊查询市
	 */
	public void getCityByCName(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String cname= getRequestParamter(request, "cname");
		List<CityInfo> list=locationService.getCityByCName(cname);
		resultMap.put("citylist", list);
	}
	/**
	 *  按市编号查询区
	 */
	public void getAreaByCityId(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String cityId= getRequestParamter(request, "cityid");
		List<AreaInfo> list=locationService.getAreaByCityId(cityId);
		resultMap.put("arealist", list);
	}
	/**
	 *  按区编号查询区
	 */ 
	public void getAreaByAreaId(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		String areaid= getRequestParamter(request, "areaid");
		AreaInfo area=locationService.getAreaByAreaId(areaid);
		resultMap.put("area", area);
	}
	/**
	 *  返回所有的省市区JSON
	 */ 
	public void getProCityArea(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException {
		List<ProvinceInfo> list=locationService.getProvinces();
		for (ProvinceInfo provinceInfo : list) {
			List<CityInfo> list2=locationService.getCityByProvinceId(provinceInfo.getProvinceid().toString());
			provinceInfo.setCities(list2);
			for (CityInfo cityInfo : list2) {
				List<AreaInfo> list3=locationService.getAreaByCityId(cityInfo.getCityid().toString());
				cityInfo.setAreas(list3);
			}
		}
		resultMap.put("china", list);
	}
	/**
	 * 自动获取省市区
	 * @throws UnsupportedEncodingException 
	 */
	public void getAutoPosition(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException, UnsupportedEncodingException {
		String pname=getRequestParamter(request,"pname");
		String cname=getRequestParamter(request,"cname");
		String aname=getRequestParamter(request,"aname");
		AutoPositionInfo tempAutoPositionInfo=locationService.getAutoPositionInfo(pname, cname, aname);
		resultMap.put("autoposition", tempAutoPositionInfo);
	}
}
