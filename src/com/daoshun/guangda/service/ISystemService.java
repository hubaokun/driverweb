package com.daoshun.guangda.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.daoshun.common.Constant;
import com.daoshun.common.ErrException;
import com.daoshun.guangda.pojo.SystemSetInfo;
import com.daoshun.guangda.pojo.UserLoginStatus;
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
	public abstract List<UserPushInfo> getUserPushInfosBykeyword(int userid, int usertype);

	public abstract void updateUserLocation(String openid, String devicetype, String usertype, String appversion, String province, String city, String area);

	public abstract HashMap<String, Object> checkVersion(String userid, String usertype, String devicetype, String version);

	public abstract void recordUserAction(String userid, String usertype, String sername, String actionname);

	public abstract SystemSetInfo getSystemSet();



	/**
	 * 	判断登录尝试次数
	 * @author ChRx
	 * @param phone       登录帐号
	 * @param usertype    用户类型
	 * @param limitCount  登录限制次数
	 * @param timeIntervalMinute 过期时间，单位分钟
	 * @return true:超过登录限制   false:没超过登录限制
	 *
	 */
	public  boolean overLoginLimitCount(String phone,int usertype, int limitCount, int timeIntervalMinute);


	/**
	 * 登录成功 登陆限制记录清空
	 * @author ChRx
	 * @param phone              登录帐号
	 * @param usertype           用户类型
	 * @param timeIntervalMinute 时间限制：分钟
	 */
	public void successThenClear(String phone, int usertype, int timeIntervalMinute);


	/**
	 * 登录失败  登录限制记录+1
	 * @author ChRx
	 * @param phone               登录帐号
	 * @param usertype            用户类型
	 * @param timeIntervalMinute  时间限制：分钟
	 */
	public void failedThenCount(String loginid, int usertype, int timeIntervalMinute);
}
