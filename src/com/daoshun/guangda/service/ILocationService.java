package com.daoshun.guangda.service;

import java.util.List;

import com.daoshun.guangda.pojo.AreaInfo;
import com.daoshun.guangda.pojo.AutoPositionInfo;
import com.daoshun.guangda.pojo.CityInfo;
import com.daoshun.guangda.pojo.ProvinceInfo;
/**
 * 省市区服务
 * @author Administrator
 *
 */
public interface ILocationService {
	public List<ProvinceInfo> getProvinces();
	public ProvinceInfo getProvincesById(String provinceid);
	public CityInfo getCityById(String cityid);
	public AreaInfo getAreaById(String areaid);
	public List<CityInfo> getCityByProvinceId(String provinceid);
	public List<AreaInfo> getAreaByCityId(String cityid);
	public AreaInfo getAreaByAreaId(String areaid);
	public List<CityInfo> getCityByHotKey(String hotKey);
	public List<CityInfo>  getCityByCName(String name);
	public List<CityInfo>  getCityByProvinceName(String name);
	public ProvinceInfo  getProvinceInfoByProvinceName(String name);
	public AreaInfo  getAreaInfoByAreaName(String name);
	public AutoPositionInfo getAutoPositionInfo(String cityid,String provinceid);
	public AutoPositionInfo getAutoPositionInfoByCityId(String cityid);
	public CityInfo getCityByBaiduID(String baiduid);
}
