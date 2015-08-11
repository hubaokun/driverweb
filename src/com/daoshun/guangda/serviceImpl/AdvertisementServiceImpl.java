package com.daoshun.guangda.serviceImpl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.daoshun.common.CommonUtils;
import com.daoshun.guangda.pojo.AdvertisementInfo;
import com.daoshun.guangda.pojo.CuserInfo;
import com.daoshun.guangda.service.IAdvertisementService;
/**
 * 
 * @author wjr  设置广告内容
 */
@Service("advertisementService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class AdvertisementServiceImpl extends BaseServiceImpl  implements IAdvertisementService {

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void setAdvertisementcontent(String path, String url) {
		
		AdvertisementInfo temp=dataDao.getObjectById(AdvertisementInfo.class, 1);
		String[] paths=path.split("upload/");
		path=paths[1];
		path=CommonUtils.getFileRootUrl()+path;
		temp.setAdimg(path);
		temp.setAdurl(url);
		updateAdvertisementcontent(temp);
		StringBuffer querystring=new StringBuffer();
		StringBuffer querystring1=new StringBuffer();
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
	public AdvertisementInfo getAdvertisementcontent() {
		AdvertisementInfo temp=dataDao.getObjectById(AdvertisementInfo.class, 1);
		return temp;
	}


	@Override
	public int jugeFlag(String type, String id) {
		StringBuffer querystring=new StringBuffer();
		String[] params={""};
		if(type.equals("1"))
		{
		  querystring.append("from CuserInfo where coachid=:coachid");
		  params[0]="coachid";
		  CuserInfo temp=(CuserInfo) dataDao.getFirstObjectViaParam(querystring.toString(), params, CommonUtils.parseInt(id, 0));
		  if(temp.getAd_flag()==0)
			  return 0;
		  else
			  return 1;
		}
		else
		{
			 querystring.append("from SuserInfo where studentid=:studentid");
			  params[0]="studentid";
			  CuserInfo temp=(CuserInfo) dataDao.getFirstObjectViaParam(querystring.toString(), params, CommonUtils.parseInt(id, 0));
			  if(temp.getAd_flag()==0)
				  return 0;
			  else
				  return 1;
		}

	}
    
}
