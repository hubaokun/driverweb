package com.daoshun.guangda.serviceImpl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.daoshun.common.CommonUtils;
import com.daoshun.guangda.model.AdvertisementConfig;
import com.daoshun.guangda.pojo.AdvertisementInfo;
import com.daoshun.guangda.pojo.CuserInfo;
import com.daoshun.guangda.pojo.SystemSetInfo;
import com.daoshun.guangda.service.IAdvertisementService;

/**
 * 
 * @author wjr 设置广告内容
 */
@Service("advertisementService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class AdvertisementServiceImpl extends BaseServiceImpl implements IAdvertisementService {

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void setAdvertisementcontent(String path, String url) {

		AdvertisementInfo temp = dataDao.getObjectById(AdvertisementInfo.class, 1);
		String[] paths = path.split("upload/");
		path = paths[1];
		path = CommonUtils.getFileRootUrl() + path;
		// temp.setAdimg(path);
		// temp.setAdurl(url);
		updateAdvertisementcontent(temp);
		StringBuffer querystring = new StringBuffer();
		StringBuffer querystring1 = new StringBuffer();
		querystring.append("update t_user_coach set ad_flag=0");
		querystring1.append("update t_user_student set ad_flag=0");
		dataDao.updateSqlQuery(querystring.toString());
		dataDao.updateSqlQuery(querystring1.toString());

	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void updateAdvertisementcontent(AdvertisementInfo temp) {
		dataDao.updateObject(temp);

	}

	@Override
	public SystemSetInfo getAdvertisementcontent() {
		// AdvertisementInfo temp=dataDao.getObjectById(AdvertisementInfo.class,
		// 1);
		SystemSetInfo temp = dataDao.getObjectById(SystemSetInfo.class, 1);
		return temp;
	}

	@Override
	public int jugeFlag(String type, String id) {
		StringBuffer querystring = new StringBuffer();
		String[] params = { "" };
		if (type.equals("1")) {
			querystring.append("from CuserInfo where coachid=:coachid");
			params[0] = "coachid";
			CuserInfo temp = (CuserInfo) dataDao.getFirstObjectViaParam(querystring.toString(), params,
					CommonUtils.parseInt(id, 0));
			if (temp.getAd_flag() == 0)
				return 0;
			else
				return 1;
		} else {
			querystring.append("from SuserInfo where studentid=:studentid");
			params[0] = "studentid";
			CuserInfo temp = (CuserInfo) dataDao.getFirstObjectViaParam(querystring.toString(), params,
					CommonUtils.parseInt(id, 0));
			if (temp.getAd_flag() == 0)
				return 0;
			else
				return 1;
		}

	}

	@Override
	public List<AdvertisementConfig> getAdvertiesementByParam(String cityid, String position, String height, String width,
			String driverschoolid, String coachid, String devicetype, String adtype) {
		StringBuffer adsql = new StringBuffer();
		adsql.append(
				"SELECT img.adimgid,img.adid,ad.position,ad.title,ad.adtype,ad.content,ad.opentype,ad.openaction,ad.openurl,ad.begintime,ad.endtime,img.imgurl,img.height,img.width,img.devicetype FROM t_advertisement ad,t_advertisement_img img,t_advertisement_scope sco WHERE img.adid = ad.adid AND sco.adid = ad.adid AND ad.endtime > NOW() AND ad.iscanceled = 0 ");
		if (!CommonUtils.isEmptyString(adtype)) {
			adsql.append(" AND ad.adtype = " + adtype);
		}
		if (!CommonUtils.isEmptyString(position)) {
			adsql.append(" AND ad.position = " + position);
		}
		if (!CommonUtils.isEmptyString(devicetype)) {
			adsql.append(" AND img.devicetype = " + devicetype);
		}
		if (!CommonUtils.isEmptyString(height)) {
			adsql.append(" AND img.height = " + height);
		}
		if (!CommonUtils.isEmptyString(width)) {
			adsql.append(" AND img.width = " + width);
		}
		if (!CommonUtils.isEmptyString(cityid)) {
			adsql.append(" AND ( sco.cityid = '" + cityid + "' ");
		} else {
			adsql.append(" AND ( sco.cityid = '330100' ");
		}
		if (!CommonUtils.isEmptyString(driverschoolid)) {
			adsql.append(" OR (sco.driverschoolid = " + driverschoolid);
			if(CommonUtils.isEmptyString(coachid)){
				adsql.append(" and sco.coachid = " + coachid +") ");
			}else{
				adsql.append(" and sco.coachid is null) ");
			}
			
		}
		adsql.append(" ) order by ad.ordervalue ASC, ad.begintime ASC");
		List<Object[]>  list = dataDao.createSQLQuery(adsql.toString());

		List<AdvertisementConfig> advcfglist = new ArrayList<AdvertisementConfig>();

		for (Object[] object : list) {
			AdvertisementConfig advc = new AdvertisementConfig();
			if(object[0]!=null){
				advc.setAdimgid((int) object[0]);	
			}
			if(object[1]!=null){
				advc.setAdid((int) object[1]);	
			}
			if(object[2]!=null){
				advc.setPosition((int) object[2]);	
			}			
			if(object[3]!=null){
				advc.setTitle((String) object[3]);	
			}
			if(object[4]!=null){
				advc.setAdtype((int) object[4]);	
			}			
			if(object[5]!=null){
				advc.setContent((String) object[5]);
			}
			if(object[6]!=null){
				advc.setOpentype((int) object[6]);	
			}
			if(object[7]!=null){
				advc.setOpenaction((int) object[7]);	
			}			
			if(object[8]!=null){
				advc.setOpenurl((String) object[8]);
			}
			if(object[9]!=null){
				advc.setBegintime( (Timestamp) object[9]);
			}
			if(object[10]!=null){
				advc.setEndtime((Timestamp) object[10]);
			}			
			if(object[11]!=null){
				advc.setImgurl((String) object[11]);
			}
			if(object[12]!=null){
				advc.setHeight((int) object[12]);
			}
			if(object[13]!=null){
				advc.setWidth((int) object[13]);
			}
			if(object[14]!=null){
				advc.setDevicetype((int) object[14]);
			}
			
			advcfglist.add(advc);
		}

		return advcfglist;
	}

}
