package com.daoshun.guangda.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.daoshun.common.CommonUtils;
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
			}else if(Constant.GETAUTOPOSITION.equals(action)){
				getAutoPosition(request,resultMap);
				//自动获取当前位置省市区信息
			}else if(Constant.GETADDRESSURL.equals(action)){
				getAddressUrl(request,resultMap);
			}else if(Constant.GETHOTCITY.equals(action)){
				//获取热门城市
				getHotCity(request, resultMap);
			}
			
			//
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
	 * 小巴服务接口
	 * @throws UnsupportedEncodingException
	 */
	public void getAutoPosition(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException, UnsupportedEncodingException {
		String pointcenter = getRequestParamter(request, "pointcenter");
		String cityid=getRequestParamter(request,"cityid");
		String provinceid=getRequestParamter(request,"provinceid");
		// 取得中心点经纬度
		String[] centers = pointcenter.split(",");
		String longitude =centers[0].trim();
		String latitude = centers[1].trim();
		
		AutoPositionInfo tempAutoPositionInfo=locationService.getAutoPositionInfoByCityId(cityid);
		if(tempAutoPositionInfo!=null){
			resultMap.put("simulateUrl", tempAutoPositionInfo.getSimulateexamurl());
			resultMap.put("bookreceptionUrl", tempAutoPositionInfo.getBookreceptionurl());
			if(tempAutoPositionInfo.getMaxprice()==null){
				resultMap.put("maxprice", 0);
			}else{
				resultMap.put("maxprice", tempAutoPositionInfo.getMaxprice().doubleValue());
			}
			if(tempAutoPositionInfo.getMinprice()==null){
				resultMap.put("minprice", 0);
			}else{
				resultMap.put("minprice", tempAutoPositionInfo.getMinprice().doubleValue());
			}
			if(tempAutoPositionInfo.getAttachcarmaxprice()==null)
			{
				resultMap.put("attachcarmaxprice", 0);
			}else{
				resultMap.put("attachcarmaxprice", tempAutoPositionInfo.getAttachcarmaxprice().doubleValue());
			}
			if(tempAutoPositionInfo.getAttachcarminprice()==null)
			{
				resultMap.put("attachcarminprice", 0);
			}else{
				resultMap.put("attachcarminprice", tempAutoPositionInfo.getAttachcarminprice().doubleValue());
			}
			/*if(tempAutoPositionInfo.getDefaultprice()==null){
				resultMap.put("defaultprice", 0);
			}else{
				resultMap.put("defaultprice", tempAutoPositionInfo.getDefaultprice().doubleValue());
			}*/
		}else{
			resultMap.put(Constant.CODE, 2);
			resultMap.put(Constant.MESSAGE, "cityid对应的数据不存在");
		}
		
	}
	/**
	 * 根据cityid或经纬度查询商城地址url
	 * @param request
	 * @param resultMap
	 * @throws ErrException
	 * @throws UnsupportedEncodingException
	 * @author 卢磊
	 */
	public void getAddressUrl(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException, UnsupportedEncodingException {
		String pointcenter = getRequestParamter(request, "pointcenter");
		String cityid=getRequestParamter(request,"cityid");
		String cityname="";
		if(cityid==null || "".equals(cityid)){
			if(pointcenter!=null){
				String[] centers = pointcenter.split(",");
				String longitude =centers[0].trim();
				String latitude = centers[1].trim();
				//如果没有传cityid时，根据经纬度查询cityid
				cityname=CommonUtils.getAddressByLngLat(longitude, latitude);
				cityname=cityname.replaceAll("市", "");
				List<CityInfo> citylist=locationService.getCityByCName(cityname);
				if(citylist!=null && citylist.size()>0){
					CityInfo city=citylist.get(0);
					cityid=city.getCityid().toString();
				}
			}
		}else{
			CityInfo cityinfo=locationService.getCityById(cityid);
			if(cityinfo!=null){
				cityname=cityinfo.getCity();
			}
		}
		AutoPositionInfo tempAutoPositionInfo=locationService.getAutoPositionInfoByCityId(cityid);
		if(tempAutoPositionInfo!=null){
			resultMap.put("cityname",cityname);
			resultMap.put("cityid",cityid);
			resultMap.put("simulateUrl", tempAutoPositionInfo.getSimulateexamurl());
			resultMap.put("bookreceptionUrl", tempAutoPositionInfo.getBookreceptionurl());
		}else{
			resultMap.put(Constant.CODE, 2);
			resultMap.put(Constant.MESSAGE, "cityid对应的数据不存在");
		}
	}
	/**
	 * 获取热门城市
	 * @param request
	 * @param resultMap
	 * @throws ErrException
	 * @throws UnsupportedEncodingException
	 */
	public void getHotCity(HttpServletRequest request, HashMap<String, Object> resultMap) throws ErrException, UnsupportedEncodingException {
		
		Cityinfo c1=new Cityinfo(330100,"杭州");
		Cityinfo c2=new Cityinfo(330700,"金华");
		Cityinfo c3=new Cityinfo(330200,"宁波");
		Cityinfo c4=new Cityinfo(310000,"上海");
		List<Cityinfo> list=new ArrayList<Cityinfo>();
		list.add(c1);
		list.add(c2);
		list.add(c3);
		list.add(c4);
		resultMap.put("city", list);
	}
	class Cityinfo{
		private int cityid;
		private String cityname;
		
		public Cityinfo() {
			super();
		}
		public Cityinfo(int cityid, String cityname) {
			super();
			this.cityid = cityid;
			this.cityname = cityname;
		}
		public int getCityid() {
			return cityid;
		}
		public void setCityid(int cityid) {
			this.cityid = cityid;
		}
		public String getCityname() {
			return cityname;
		}
		public void setCityname(String cityname) {
			this.cityname = cityname;
		}
	}
	
}
