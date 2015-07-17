package com.daoshun.guangda.service;

import java.util.HashMap;

import com.daoshun.guangda.pojo.SystemSetInfo;
import com.daoshun.guangda.pojo.UserPushInfo;

public interface ISystemService {

	/**
	 * 根据信息找到推送数据
	 * 
	 * @param userid
	 *            用户id
	 * @param usertype
	 *            用户类型 教练、学员
	 * @param type
	 *            设备类型 安卓、IOS
	 * @return
	 */
	public abstract UserPushInfo getUserPushInfoBykeyword(int userid, int usertype);

	public abstract void updateUserLocation(String openid, String devicetype, String usertype, String appversion, String province, String city, String area);

	public abstract HashMap<String, Object> checkVersion(String userid, String usertype, String devicetype, String version);

	public abstract void recordUserAction(String userid, String usertype, String sername, String actionname);

	public abstract SystemSetInfo getSystemSet();

}
