package com.daoshun.guangda.serviceImpl;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.daoshun.common.CommonUtils;
import com.daoshun.guangda.pojo.AreaInfo;
import com.daoshun.guangda.pojo.AutoPositionInfo;
import com.daoshun.guangda.pojo.CityInfo;
import com.daoshun.guangda.pojo.ProvinceInfo;
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
	public List<ProvinceInfo> getProvinces() {
		return dataDao.getAllObject(ProvinceInfo.class);
	}
	/**
	 * 根据省的ID查询此省的所有市信息
	 */
	@Override
	public List<CityInfo> getCityByProvinceId(String provinceid) {
		String[] params = { "provinceid" };
		return (List<CityInfo>)dataDao.getObjectsViaParam("from CityInfo where  provinceid=:provinceid", params,CommonUtils.parseInt(provinceid, 0));
	}
	/**
	 * 根据市的ID查询此市的所有区信息
	 */
	@Override
	public List<AreaInfo> getAreaByCityId(String cityid) {
		String[] params = { "cityid" };
		return (List<AreaInfo>)dataDao.getObjectsViaParam("from AreaInfo where cityid=:cityid", params,CommonUtils.parseInt(cityid, 0));
	}
	@Override
	public List<CityInfo> getCityByHotKey(String hotKey) {
		String[] params = { "hotkey" };
		return (List<CityInfo>)dataDao.getObjectsViaParam("from CityInfo where hotkey like :hotkey", params,"%"+hotKey+"%");
	}
	@Override
	public List<CityInfo> getCityByCName(String name) {
		String[] params = { "city" };
		return (List<CityInfo>)dataDao.getObjectsViaParam("from CityInfo where city like :city", params,"%"+name+"%");
	}
	/**
	 * 根据省名称查询这个省下边的所有市信息
	 */
	@Override
	public List<CityInfo> getCityByProvinceName(String name) {
		String[] params = { "province" };
		String hql="from CityInfo  where provinceid =(SELECT provinceid from ProvinceInfo where province=:province)";
		return (List<CityInfo>)dataDao.getObjectsViaParam(hql, params,name);
	}
	@Override
	public AreaInfo getAreaByAreaId(String areaid) {
		String[] params = { "areaid" };
		return (AreaInfo)dataDao.getFirstObjectViaParam("from AreaInfo where areaid=:areaid", params,CommonUtils.parseInt(areaid, 0));
	}
	@Override
	public ProvinceInfo getProvincesById(String provinceid) {
		String[] params = { "provinceid" };
		return (ProvinceInfo)dataDao.getFirstObjectViaParam("from ProvinceInfo where provinceid=:provinceid", params, CommonUtils.parseInt(provinceid, 0));
	}
	@Override
	public CityInfo getCityById(String cityid) {
		String[] params = { "cityid" };
		return (CityInfo)dataDao.getFirstObjectViaParam("from CityInfo where cityid=:cityid", params,CommonUtils.parseInt(cityid, 0) );
	}
	@Override
	public AreaInfo getAreaById(String areaid) {
		String[] params = { "areaid" };
		return (AreaInfo)dataDao.getFirstObjectViaParam("from AreaInfo where areaid=:areaid", params,CommonUtils.parseInt(areaid, 0) );
	}
	/**
	 *  根据省名称模糊查询省ID
	 */
	@Override
	public ProvinceInfo getProvinceInfoByProvinceName(String name) {
		String[] params = { "province" };
		return (ProvinceInfo)dataDao.getFirstObjectViaParam("from ProvinceInfo where province like:province", params,"%"+name+"%" );
	}
	/**
	 *  根据区名称模糊查询区ID
	 */
	@Override
	public AreaInfo getAreaInfoByAreaName(String name) {
		String[] params = { "area" };
		return (AreaInfo)dataDao.getFirstObjectViaParam("from AreaInfo where area like:area", params,"%"+name+"%");
	}
	/**
	 * 根据省市区名称获得自动匹配地区详情
	 */
	@Override
	public AutoPositionInfo getAutoPositionInfo(String cityid) {
		//CityInfo tempCityInfo=getCityByBaiduID(baiduid);
		String querystring="from AutoPositionInfo where cityid=:cityid";
		String[] params={"cityid"};
		List<AutoPositionInfo> tempAutoPositionInfo=(List<AutoPositionInfo>) dataDao.getObjectsViaParam(querystring, params,CommonUtils.parseInt(cityid, 0));
		if(tempAutoPositionInfo.size()>0)
		{
			return tempAutoPositionInfo.get(0);
		
		}
		else
		{
		      Logger logger=Logger.getRootLogger();
		      logger.warn("AutoPosition Exception cityid="+cityid);
		}
		       
		return null;
	}
	@Override
	public CityInfo getCityByBaiduID(String baiduid) {
		String querystring="from CityInfo where baiduid=:baiduid";
		String[] params={"baiduid"};
		CityInfo tempCityInfo=(CityInfo) dataDao.getFirstObjectViaParam(querystring, params,CommonUtils.parseInt(baiduid, 0));
		return tempCityInfo;
	}

   

}
