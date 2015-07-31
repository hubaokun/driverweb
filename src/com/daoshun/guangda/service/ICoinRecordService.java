package com.daoshun.guangda.service;

import com.daoshun.common.QueryResult;
import com.daoshun.guangda.pojo.*;

import java.util.List;

/**
 * Created by tutu on 15/7/24.
 */
public interface ICoinRecordService {

    public abstract QueryResult<CoinRecordInfo> getCoinRecordListByPage(int pageIndex, int pageSize, String starttime, String endtime,  Integer ownertype,  String ownerid);

    public abstract void addCoinRecord(CoinRecordInfo coinRecordInfo);

//    public abstract void delCouponById(int id);
//
//    public abstract List<SuserInfo> getSuserInfoByKeyword(String keyword);
//
//    public abstract List<DriveSchoolInfo> getDriveSchoolInfoByKeyword(String keyword);
//
//    public abstract List<CuserInfo> getCuserInfoByKeyword(String keyword);
//
//    public abstract CouponInfo getCounponInfoById(int id);
//
//    public abstract void updateCouponInfo(CouponInfo coupon);
//
//    public abstract void addCouponRecord(CouponRecord couponrecord);
//
//    public abstract QueryResult<CouponRecord> getCouponReecordListByPage(int pageIndex, int pageSize, String name, Integer coupontype, String starttime, String endtime, Integer value,
//                                                                         Integer valuetype, Integer ownertype, String ownerkey, Integer state);
//
//    public abstract QueryResult<CouponRecord> getCancelCouponReecordListByPage(int pageIndex, int pageSize, String name, Integer coupontype, String starttime, String endtime, Integer value,
//                                                                               Integer valuetype, Integer ownertype, String ownerkey);
//
//    public abstract void cancelcoupon(int recordid);
}
