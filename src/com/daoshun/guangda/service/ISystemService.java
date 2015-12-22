package com.daoshun.guangda.service;

import java.util.HashMap;
import java.util.List;

import com.daoshun.common.ErrException;
import com.daoshun.guangda.pojo.SystemSetInfo;
import com.daoshun.guangda.pojo.UserPushInfo;

import javax.servlet.http.HttpServletRequest;

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
	public abstract List<UserPushInfo> getUserPushInfosBykeyword(int userid, int usertype);

	public abstract void updateUserLocation(String openid, String devicetype, String usertype, String appversion, String province, String city, String area);

	public abstract HashMap<String, Object> checkVersion(String userid, String usertype, String devicetype, String version);

	public abstract void recordUserAction(String userid, String usertype, String sername, String actionname);

	public abstract SystemSetInfo getSystemSet();


	/**判断验证码尝试次数
	 * @flag 是否登录成功 1时成功
	 * @param request
	 * @param resultMap
	 * @param limitTimes  登录限制次数
	 * @param timeout session过期日期，单位秒 默认30*60
	 * @author ChRx
	 * @return
	 * @throws ErrException
	 *
	 */

	public  boolean overLoginLimitCount(String phone, int usertype, int flag, HttpServletRequest request, HashMap<String, Object> resultMap, int limitTimes, int timeout) throws ErrException;


}
