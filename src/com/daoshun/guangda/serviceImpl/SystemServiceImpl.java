package com.daoshun.guangda.serviceImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.daoshun.common.ErrException;
import com.daoshun.guangda.pojo.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.daoshun.common.CommonUtils;
import com.daoshun.guangda.service.ISystemService;

import javax.servlet.http.HttpServletRequest;

@Service("systemService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class SystemServiceImpl extends BaseServiceImpl implements ISystemService {

	@Override
	public List<UserPushInfo> getUserPushInfosBykeyword(int userid, int usertype) {
		StringBuffer syshql = new StringBuffer();
		syshql.append("from UserPushInfo where userid = :userid and usertype = :usertype");
		String[] params = { "userid", "usertype" };

		List<UserPushInfo>  ups = (List<UserPushInfo>) dataDao.getObjectsViaParam(syshql.toString(), params, new Integer(userid),new Integer(usertype));
		return ups;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void updateUserLocation(String openid, String devicetype, String usertype, String appversion, String province, String city, String area) {
		String hql = "from UserLocationInfo where openid =:openid";
		String[] params = { "openid" };
		UserLocationInfo info = (UserLocationInfo) dataDao.getFirstObjectViaParam(hql, params, openid);
		if (info != null) {
			info.setAppversion(appversion);
			info.setArea(area);
			info.setCity(city);
			info.setDevicetype(CommonUtils.parseInt(devicetype, 0));
			info.setProvince(province);
			info.setUpdatetime(new Date());
			info.setUsertype(CommonUtils.parseInt(usertype, 0));

			dataDao.updateObject(info);
		} else {
			info = new UserLocationInfo();
			info.setAppversion(appversion);
			info.setArea(area);
			info.setCity(city);
			info.setDevicetype(CommonUtils.parseInt(devicetype, 0));
			info.setProvince(province);
			info.setUpdatetime(new Date());
			info.setUsertype(CommonUtils.parseInt(usertype, 0));
			info.setOpenid(openid);

			dataDao.addObject(info);
		}
	}

	@Override
	public HashMap<String, Object> checkVersion(String userid, String usertype, String devicetype, String version) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String hql = "from VersionInfo where apptype =:apptype and category =:category and state = 1 order by versioncode";
		String[] params = { "apptype", "category" };

		VersionInfo versionInfo = (VersionInfo) dataDao.getFirstObjectViaParam(hql, params, CommonUtils.parseInt(usertype, 0), CommonUtils.parseInt(devicetype, 0));
		if (versionInfo != null) {
			if (versionInfo.getVersioncode() > CommonUtils.parseInt(version, 0)) {// 有新版本
				result.put("code", 2);
				result.put("version", versionInfo);
				result.put("message", "操作成功");
			} else {
				result.put("code", 1);
				result.put("message", "操作成功");
			}
		} else {
			result.put("code", 1);
			result.put("message", "操作成功");
		}

		return result;
	}
	
	

	
	

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void recordUserAction(String userid, String usertype, String sername, String actionname) {
		//取消向表t_active_record的插入数据操作
		/*ActiveRecord record = new ActiveRecord();
		record.setActionname(actionname);
		record.setAddtime(new Date());
		record.setServername(sername);
		record.setUserid(CommonUtils.parseInt(userid, 0));
		record.setUsertype(CommonUtils.parseInt(usertype, 0));
		dataDao.addObject(record);*/
	}

	@Override
	public SystemSetInfo getSystemSet() {
		String hql = "from SystemSetInfo where 1 = 1";
		SystemSetInfo systemSet = (SystemSetInfo) dataDao.getFirstObjectViaParam(hql, null, null);
		return systemSet;
	}



	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public  boolean overLoginLimitCount(String loginid, int usertype, int flag, HttpServletRequest request, HashMap<String, Object> resultMap, int limitTimes, int timeout) throws ErrException
	{
		long limitTime=0;
		//默认10分钟
		if(timeout==0)
		{
			timeout=10;
		}
		limitTime=timeout*60;

		int loginCount=0;

		String hql="from UserLoginStatus where phone="+loginid+" and userType="+usertype;
		UserLoginStatus userloginstatus=(UserLoginStatus) dataDao.getFirstObjectViaParam(hql,null,null);
		//数据库中不存在，则添加一条添加时间，失败次数为空的记录
		if(userloginstatus==null)
		{
			userloginstatus=new UserLoginStatus();
			userloginstatus.setPhone(loginid);
			userloginstatus.setUserType(usertype);
			dataDao.addObject(userloginstatus);
			userloginstatus=(UserLoginStatus) dataDao.getFirstObjectViaParam(hql, null, null);
		}

		//清除上次登录尝试的数据（过期的）
		clearLoginStatus(limitTime,userloginstatus);

		//没有超过登录限制
		if(userloginstatus.getFailedCount()<limitTimes)
		{
			//登录成功
			if(UserLoginStatus.LOGIN_SUCCESS==flag)
			{
				return false;
			}

			//登录失败
			if(UserLoginStatus.LOGIN_FAIL==flag)
			{
				//第一次登录时，添加时间
				if(userloginstatus.getFailedCount()==0)
				{
					userloginstatus.setAddtime(new Date());
				}
				userloginstatus.setFailedCount(userloginstatus.getFailedCount()+1);
				dataDao.updateObject(userloginstatus);
				return false;
			}
		}else
		{
			resultMap.put("code", 2);
			resultMap.put("message", "请"+timeout+"分钟后再次尝试！");
			return true;
		}

		return false;
	}

	/**
	 * 已超出限制冻结时间，对登录失败次数置零
	 * @param time 限制时间  单位秒
	 * @param userloginstatus 登录信息
	 * @return
	 */
	private void clearLoginStatus(long time,UserLoginStatus userloginstatus)
	{
		time=time*1000;
		Date addtime=userloginstatus.getAddtime();
		if(addtime!=null)
		{
			long frozenTime=addtime.getTime()+time;
			long now=new Date().getTime();
			if(now>frozenTime)
			{
				userloginstatus.setAddtime(null);
				userloginstatus.setFailedCount(0);
				dataDao.updateObject(userloginstatus);
			}
		}
	}


}
