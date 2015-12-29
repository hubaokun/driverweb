package com.daoshun.guangda.serviceImpl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.daoshun.common.CommonUtils;
import com.daoshun.guangda.pojo.AdminInfo;
import com.daoshun.guangda.pojo.SignUpInfoFromAds;
import com.daoshun.guangda.service.ISignUpInfoFromAdsService;

@Service("signUpInfoFromAdsService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class SignUpInfoFromAdsServiceImpl extends BaseServiceImpl implements ISignUpInfoFromAdsService
{

	@Override
	public void addSignUpInfo(SignUpInfoFromAds signUpinfo)
	{
		if(signUpinfo!=null)
		{
			signUpinfo.setState(STATE_NOT_PROCESSED);
		}
		dataDao.addObject(signUpinfo);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void processInfo(int signId, String processinfo, int serverId)
	{
		SignUpInfoFromAds info=dataDao.getObjectById(SignUpInfoFromAds.class,signId);
		Date now=new Date();//现在日期
		if(info!=null)
		{
			
			if(!CommonUtils.isEmptyString(processinfo) && signId!=0)
			{
				info.setProcessTime(now);
				info.setProcessorId(serverId);
				info.setProcessInfo(processinfo);
				info.setState(STATE_PROCESSED);
				dataDao.updateObject(info);
			}
		}

	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void deleteInfo(int signId)
	{
		SignUpInfoFromAds info=dataDao.getObjectById(SignUpInfoFromAds.class,signId);
		if(info!=null)
		{
			info.setIsAbandoned(ABANDONED);
			dataDao.updateObject(info);
		}

	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void recoverInfo(int signId)
	{
		SignUpInfoFromAds info=dataDao.getObjectById(SignUpInfoFromAds.class,signId);
		if(info!=null)
		{
			info.setIsAbandoned(NOT_ABANDONED);
			dataDao.updateObject(info);
		}
	}

	@Override
	public SignUpInfoFromAds getSignUpDetails(int signId)
	{
		SignUpInfoFromAds info=dataDao.getObjectById(SignUpInfoFromAds.class,signId);
		if(info!=null)
		{
			if(info.getProcessorId()!=0)
			{
				AdminInfo processor=dataDao.getObjectById(AdminInfo.class,info.getProcessorId());
				if(processor!=null)
				{
					info.setProcessor(processor);
				}
			}
		}
		return info;
	}

	@Override
	public List<SignUpInfoFromAds> getSignUpListByCondition(String catg,int state,int isAbandoned, String addtimeRangeLeft,String addtimeRangeRight, String processTimeRangeLeft,String processTimeRangeRight)
	{
		StringBuffer hql=new StringBuffer("from SignUpInfoFromAds where 1=1 ");
		hql.append("and state="+state+" ");
		hql.append("and isAbandoned="+isAbandoned+" ");
		if(!CommonUtils.isEmptyString(catg))
		{
			hql.append("and adsTypeName like '%"+catg+"%' ");
		}
		if(!CommonUtils.isEmptyString(addtimeRangeLeft))
		{
			addtimeRangeLeft+=" 00:00:00";
			hql.append("and addtime>='"+addtimeRangeLeft+"' ");
		}
		if(!CommonUtils.isEmptyString(addtimeRangeRight))
		{
			addtimeRangeRight+=" 23:59:59";
			hql.append("and addtime<='"+addtimeRangeRight+"' ");
		}
		
		
		if(!CommonUtils.isEmptyString(processTimeRangeLeft))
		{
			processTimeRangeLeft+="00:00:00";
			hql.append("and processTime>="+processTimeRangeLeft+" ");
		}
		
		if(!CommonUtils.isEmptyString(processTimeRangeRight))
		{
			processTimeRangeRight+="23:59:59";
			hql.append("and processTime<="+processTimeRangeRight+" ");
		}
		
		hql.append("order by addtime DESC");
		List<SignUpInfoFromAds> signUpInfoList=(List<SignUpInfoFromAds>) dataDao.getObjectsViaParam(hql.toString(),null,null);
		return signUpInfoList;
	}

	@Override
	public List<String> getCatgs()
	{
		StringBuffer hql=new StringBuffer("select distinct adsTypeName from SignUpInfoFromAds group by adsTypeName");
		List<String>catgs=(List<String>)dataDao.getObjectsViaParam(hql.toString(), null, null);
		return catgs;
	}

}
