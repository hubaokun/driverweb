package com.daoshun.guangda.servlet;

import java.io.IOException;
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
}
