package com.daoshun.guangda.service;

import com.daoshun.guangda.pojo.AdvertisementInfo;
import com.daoshun.guangda.pojo.SystemSetInfo;

public interface IAdvertisementService {
	
	//设置广告内容
	public abstract void setAdvertisementcontent(String path,String url);
   //更新广告内容
	public abstract void updateAdvertisementcontent(AdvertisementInfo temp);
	//获取广告内容
	public abstract SystemSetInfo getAdvertisementcontent();
	//判断广告是否展示过
	public abstract int jugeFlag(String type,String id);
}
