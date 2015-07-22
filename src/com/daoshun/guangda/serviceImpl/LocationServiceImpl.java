package com.daoshun.guangda.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.daoshun.common.CommonUtils;
import com.daoshun.guangda.pojo.Areas;
import com.daoshun.guangda.pojo.Cities;
import com.daoshun.guangda.pojo.Provinces;
import com.daoshun.guangda.service.ILocationService;
/**
 * 省市区查询
 * @author 卢磊
 *
 */
@Service("locationService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class LocationServiceImpl extends BaseServiceImpl implements ILocationService{
	/**
	 * 查询所有的省名称
	 */
	@Override
	public List<Provinces> getProvinces() {
		return dataDao.getAllObject(Provinces.class);
	}
	/**
	 * 根据省的ID查询此省的所有市信息
	 */
	@Override
	public List<Cities> getCityByProvinceId(String provinceid) {
		String[] params = { "provinceid" };
		return (List<Cities>)dataDao.getObjectsViaParam("from Cities where  provinceid=:provinceid", params,CommonUtils.parseInt(provinceid, 0));
	}
	/**
	 * 根据市的ID查询此市的所有区信息
	 */
	@Override
	public List<Areas> getAreaByCityId(String cityid) {
		String[] params = { "cityid" };
		return (List<Areas>)dataDao.getObjectsViaParam("from Areas where cityid=:cityid", params,CommonUtils.parseInt(cityid, 0));
	}
	@Override
	public List<Cities> getCityByHotKey(String hotKey) {
		String[] params = { "hotkey" };
		return (List<Cities>)dataDao.getObjectsViaParam("from Cities where hotkey like :hotkey", params,"%"+hotKey+"%");
	}
	@Override
	public List<Cities> getCityByCName(String name) {
		String[] params = { "city" };
		return (List<Cities>)dataDao.getObjectsViaParam("from Cities where city like :city", params,"%"+name+"%");
	}
	/**
	 * 根据省名称查询这个省下边的所有市信息
	 */
	@Override
	public List<Cities> getCityByProvinceName(String name) {
		String[] params = { "province" };
		String hql="from Cities  where provinceid =(SELECT provinceid from Provinces where province=:province)";
		return (List<Cities>)dataDao.getObjectsViaParam(hql, params,name);
	}
	@Override
	public Areas getAreaByAreaId(String areaid) {
		String[] params = { "areaid" };
		return (Areas)dataDao.getFirstObjectViaParam("from Areas where areaid=:areaid", params,CommonUtils.parseInt(areaid, 0));
	}
	
	

}
