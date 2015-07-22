package com.daoshun.guangda.service;

import java.util.List;

import com.daoshun.guangda.pojo.Areas;
import com.daoshun.guangda.pojo.Cities;
import com.daoshun.guangda.pojo.Provinces;
/**
 * 省市区服务
 * @author Administrator
 *
 */
public interface ILocationService {
	public List<Provinces> getProvinces();
	public List<Cities> getCityByProvinceId(String provinceid);
	public List<Areas> getAreaByCityId(String cityid);
	public List<Cities> getCityByHotKey(String hotKey);
	public List<Cities>  getCityByCName(String name);
	public List<Cities>  getCityByProvinceName(String name);
}
