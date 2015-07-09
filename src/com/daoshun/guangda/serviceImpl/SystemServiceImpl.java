package com.daoshun.guangda.serviceImpl;

import java.util.Date;
import java.util.HashMap;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.daoshun.common.CommonUtils;
import com.daoshun.guangda.pojo.ActiveRecord;
import com.daoshun.guangda.pojo.SystemSetInfo;
import com.daoshun.guangda.pojo.UserLocationInfo;
import com.daoshun.guangda.pojo.UserPushInfo;
import com.daoshun.guangda.pojo.VersionInfo;
import com.daoshun.guangda.service.ISystemService;

@Service("systemService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class SystemServiceImpl extends BaseServiceImpl implements ISystemService {

	@Override
	public UserPushInfo getUserPushInfoBykeyword(int userid, int usertype) {
		StringBuffer syshql = new StringBuffer();
		syshql.append("from UserPushInfo where userid = :userid and usertype = :usertype");
		String[] params = { "userid", "usertype" };
		UserPushInfo userPushInfo = (UserPushInfo) dataDao.getFirstObjectViaParam(syshql.toString(), params, userid, usertype);
		return userPushInfo;
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
		ActiveRecord record = new ActiveRecord();
		record.setActionname(actionname);
		record.setAddtime(new Date());
		record.setServername(sername);
		record.setUserid(CommonUtils.parseInt(userid, 0));
		record.setUsertype(CommonUtils.parseInt(usertype, 0));
		dataDao.addObject(record);
	}

	@Override
	public SystemSetInfo getSystemSet() {
		String hql = "from SystemSetInfo where 1 = 1";
		SystemSetInfo systemSet = (SystemSetInfo) dataDao.getFirstObjectViaParam(hql, null, null);
		return systemSet;
	}

}
