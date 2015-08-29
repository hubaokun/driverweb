package com.daoshun.guangda.service;

import java.util.List;

import com.daoshun.common.QueryResult;
import com.daoshun.guangda.pojo.CouponCoach;
import com.daoshun.guangda.pojo.CouponInfo;
import com.daoshun.guangda.pojo.CouponRecord;
import com.daoshun.guangda.pojo.CuserInfo;
import com.daoshun.guangda.pojo.DriveSchoolInfo;
import com.daoshun.guangda.pojo.SuserInfo;

public interface ICouponService {

	public abstract QueryResult<CouponInfo> getCouponListByPage(int pageIndex, int pageSize, Integer coupontype, String starttime, String endtime, Integer value, Integer valuetype, Integer ownertype,
			String ownerkey);

	public abstract void addCoupon(CouponInfo coupon);

	public abstract void delCouponById(int id);

	public abstract List<SuserInfo> getSuserInfoByKeyword(String keyword);

	public abstract List<DriveSchoolInfo> getDriveSchoolInfoByKeyword(String keyword);

	public abstract List<CuserInfo> getCuserInfoByKeyword(String keyword);

	public abstract CouponInfo getCounponInfoById(int id);

	public abstract void updateCouponInfo(CouponInfo coupon);

	public abstract void addCouponRecord(CouponRecord couponrecord);

	public abstract QueryResult<CouponRecord> getCouponReecordListByPage(int pageIndex, int pageSize, String name, Integer coupontype, String starttime, String endtime, Integer value,
			Integer valuetype, Integer ownertype, String ownerkey, Integer state,int userid);
	
	public abstract QueryResult<CouponRecord> getCouponReecordInfoByPage(int pageIndex, int pageSize, String name, Integer coupontype, String starttime, String endtime, Integer value,
			Integer valuetype, Integer ownertype, String ownerkey, Integer state);
	
	public List<CouponRecord> getCouponRecordList();
	
	public abstract QueryResult<CouponCoach> getCouponCoachInfoByPage(int pageIndex, int pageSize, String name, Integer coupontype, String starttime, String endtime, Integer value,
			Integer valuetype, Integer ownertype, String ownerkey, Integer state);

	public abstract QueryResult<CouponRecord> getCancelCouponReecordListByPage(int pageIndex, int pageSize, String name, Integer coupontype, String starttime, String endtime, Integer value,
			Integer valuetype, Integer ownertype, String ownerkey);

	public abstract void cancelcoupon(int recordid);
	public abstract void cancelallcoupon(int userid);
}
