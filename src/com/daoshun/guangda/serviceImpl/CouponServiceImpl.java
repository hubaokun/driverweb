package com.daoshun.guangda.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.daoshun.common.CommonUtils;
import com.daoshun.common.QueryResult;
import com.daoshun.guangda.pojo.CouponCoach;
import com.daoshun.guangda.pojo.CouponInfo;
import com.daoshun.guangda.pojo.CouponRecord;
import com.daoshun.guangda.pojo.CuserInfo;
import com.daoshun.guangda.pojo.DriveSchoolInfo;
import com.daoshun.guangda.pojo.SuserInfo;
import com.daoshun.guangda.service.ICouponService;

/**
 * @author wangcl
 * 
 */
@Service("couponService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class CouponServiceImpl extends BaseServiceImpl implements ICouponService {

	@Override
	public QueryResult<CouponInfo> getCouponListByPage(int pageIndex, int pageSize, Integer coupontype, String starttime, String endtime, Integer value, Integer valuetype, Integer ownertype,
			String ownerkey) {
		StringBuffer couponhql = new StringBuffer();
		couponhql.append(" from CouponInfo where 1=1 ");
		if (coupontype != null) {
			couponhql.append(" and coupontype = " + coupontype);
		}
		if (!CommonUtils.isEmptyString(starttime)) {
			couponhql.append(" and end_time > '" + starttime + "'");
		}

		if (!CommonUtils.isEmptyString(endtime)) {
			couponhql.append(" and end_time <= '" + endtime + " 23:59:59'");
		}
		if (ownertype != null) {
			couponhql.append(" and ownertype = " + ownertype);
			if (ownertype == 1) {
				if (!CommonUtils.isEmptyString(ownerkey)) {
					couponhql.append(" and ownerid in ( select schoolid from DriveSchoolInfo where name like '%" + ownerkey + "%')");
				}
			} else if (ownertype == 2) {
				if (!CommonUtils.isEmptyString(ownerkey)) {
					couponhql.append(" and ownerid in ( select coachid from CuserInfo where realname like '%" + ownerkey + "%')");
				}
			}
		}
		if (value != null) {
			if (valuetype == 1) {
				couponhql.append(" and value >" + value);
			} else if (valuetype == 2) {
				couponhql.append(" and value =" + value);
			} else if (valuetype == 3) {
				couponhql.append(" and value <" + value);
			}
		}
		List<CouponInfo> couponlist = (List<CouponInfo>) dataDao.pageQueryViaParam(couponhql.toString() + " order by addtime desc", pageSize, pageIndex, null);
		for (CouponInfo coupon : couponlist) {
			if (coupon.getOwnertype() == 1) {
				DriveSchoolInfo driveschool = dataDao.getObjectById(DriveSchoolInfo.class, coupon.getOwnerid());
				if (driveschool != null) {
					coupon.setSchoolname(driveschool.getName());
				}
			}
			if (coupon.getOwnertype() == 2) {
				CuserInfo cuserinfo = dataDao.getObjectById(CuserInfo.class, coupon.getOwnerid());
				if (cuserinfo != null) {
					coupon.setCusername(cuserinfo.getRealname());
				}
			}
		}
		String counthql = couponhql.insert(0, " select count(*) ").toString();
		long count = (Long) dataDao.getFirstObjectViaParam(counthql, null);
		QueryResult<CouponInfo> result = new QueryResult<CouponInfo>(couponlist, count);
		return result;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void addCoupon(CouponInfo coupon) {
		dataDao.addObject(coupon);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void delCouponById(int id) {
		CouponInfo coupon = dataDao.getObjectById(CouponInfo.class, id);
		if (coupon != null) {
			dataDao.deleteObject(coupon);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SuserInfo> getSuserInfoByKeyword(String keyword) {
		String hql = " from SuserInfo where phone like '%" + keyword + "%' or realname like '%" + keyword + "%' ";
		List<SuserInfo> suserlist = (List<SuserInfo>) dataDao.getObjectsViaParam(hql, null);
		return suserlist;
	}

	@Override
	public CouponInfo getCounponInfoById(int id) {
		CouponInfo coupon = dataDao.getObjectById(CouponInfo.class, id);
		return coupon;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void addCouponRecord(CouponRecord couponrecord) {
		dataDao.addObject(couponrecord);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void updateCouponInfo(CouponInfo coupon) {
		dataDao.updateObject(coupon);
	}
//获得小巴券发放记录明细
	@SuppressWarnings("unchecked")
	@Override
	public QueryResult<CouponRecord> getCouponReecordListByPage(int pageIndex, int pageSize, String name, Integer coupontype, String starttime, String endtime, Integer value, Integer valuetype,
			Integer ownertype, String ownerkey, Integer state,int userid) {
		StringBuffer couponhql = new StringBuffer();
		if(state != null){
			couponhql.append(" from CouponRecord where state = " + state);
		}else{
			couponhql.append(" from CouponRecord where 1 = 1");
		}
		
		if (!CommonUtils.isEmptyString(String.valueOf(userid))){
			couponhql.append(" and userid="+userid);
		}
		
		if (!CommonUtils.isEmptyString(name)) {
			couponhql.append(" and userid in ( select studentid from SuserInfo where realname like '%" + name + "%' or phone like '%" + name + "%')");
		}
		if (coupontype != null) {
			couponhql.append(" and coupontype = " + coupontype);
		}
		if (!CommonUtils.isEmptyString(starttime)) {
			starttime = starttime + " 00:00:00";
			couponhql.append(" and usetime > '" + starttime + "'");
		}

		if (!CommonUtils.isEmptyString(endtime)) {
			endtime = endtime + " 23:59:59";
			couponhql.append(" and usetime <= '" + endtime + "'");
		}
		if (ownertype != null) {
			couponhql.append(" and ownertype = " + ownertype);
			if (ownertype == 1) {
				if (!CommonUtils.isEmptyString(ownerkey)) {
					couponhql.append(" and ownerid in ( select schoolid from DriveSchoolInfo where name like '%" + ownerkey + "%')");
				}
			} else if (ownertype == 2) {
				if (!CommonUtils.isEmptyString(ownerkey)) {
					couponhql.append(" and ownerid in ( select coachid from CuserInfo where realname like '%" + ownerkey + "%')");
				}
			}
		}
		if (value != null) {
			if (valuetype == 1) {
				couponhql.append(" and value >" + value);
			} else if (valuetype == 2) {
				couponhql.append(" and value =" + value);
			} else if (valuetype == 3) {
				couponhql.append(" and value <" + value);
			}
		}
		couponhql.append(" order by recordid desc");
		List<CouponRecord> CouponRecordlist = (List<CouponRecord>) dataDao.pageQueryViaParam(couponhql.toString(), pageSize, pageIndex, null);
		for (CouponRecord couponrecord : CouponRecordlist) {
			SuserInfo suserinfo = dataDao.getObjectById(SuserInfo.class, couponrecord.getUserid());
			if (suserinfo != null) {
				if (CommonUtils.isEmptyString(suserinfo.getRealname())) {
					couponrecord.setUsernick("未设置:" + suserinfo.getPhone());
				} else {
					couponrecord.setUsernick(suserinfo.getRealname() + ":" + suserinfo.getPhone());
				}
			}
			if (couponrecord.getOwnertype() == 1) {
				DriveSchoolInfo driveschool = dataDao.getObjectById(DriveSchoolInfo.class, couponrecord.getOwnerid());
				if (driveschool != null) {
					couponrecord.setSchoolname(driveschool.getName());
				}
			}
			if (couponrecord.getOwnertype() == 2) {
				CuserInfo cuserinfo = dataDao.getObjectById(CuserInfo.class, couponrecord.getOwnerid());
				if (cuserinfo != null) {
					couponrecord.setCusername(cuserinfo.getRealname());
				}
			}
		}
		String counthql = couponhql.insert(0, " select count(*)").toString();
		long count = (Long) dataDao.getFirstObjectViaParam(counthql, null);
		//System.out.print(count);
		QueryResult<CouponRecord> result = new QueryResult<CouponRecord>(CouponRecordlist, count);
		return result;
	}
	
//获得小巴券发放记录
	@SuppressWarnings("unchecked")
	@Override
	public QueryResult<CouponRecord> getCouponReecordInfoByPage(int pageIndex, int pageSize, String name, Integer coupontype, String starttime, String endtime, Integer value, Integer valuetype,
			Integer ownertype, String ownerkey, Integer state) {
		StringBuffer couponhql = new StringBuffer();
		
		
		//couponhql.append("from CouponRecord cr group by cr.userid");
		
		if(state != null){
			couponhql.append(" from CouponRecord cr  where state = " + state);
		}else{
			couponhql.append(" from CouponRecord cr  where 1 = 1");
		}
		
		if (!CommonUtils.isEmptyString(name)) {
			couponhql.append(" and userid in ( select studentid from SuserInfo where realname like '%" + name + "%' or phone like '%" + name + "%')");
		}
		if (coupontype != null) {
			couponhql.append(" and coupontype = " + coupontype);
		}
		
		if (CommonUtils.isEmptyString(starttime) && CommonUtils.isEmptyString(endtime)) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			starttime = formatter.format(new Date());
			endtime = formatter.format(new Date());
		}
		if (!CommonUtils.isEmptyString(starttime)) {
			starttime = starttime + " 00:00:00";
			couponhql.append(" and gettime > '" + starttime + "'");
		}

		if (!CommonUtils.isEmptyString(endtime)) {
			endtime = endtime + " 23:59:59";
			couponhql.append(" and gettime <= '" + endtime + "'");
		}
		if (ownertype != null) {
			couponhql.append(" and ownertype = " + ownertype);
			if (ownertype == 1) {
				if (!CommonUtils.isEmptyString(ownerkey)) {
					couponhql.append(" and ownerid in ( select schoolid from DriveSchoolInfo where name like '%" + ownerkey + "%')");
				}
			} else if (ownertype == 2) {
				if (!CommonUtils.isEmptyString(ownerkey)) {
					couponhql.append(" and ownerid in ( select coachid from CuserInfo where realname like '%" + ownerkey + "%')");
				}
			}
		}
		if (value != null) {
			if (valuetype == 1) {
				couponhql.append(" and value >" + value);
			} else if (valuetype == 2) {
				couponhql.append(" and value =" + value);
			} else if (valuetype == 3) {
				couponhql.append(" and value <" + value);
			}
		}
		
		couponhql.append(" group by cr.userid,cr.ownerid order by userid desc");
	
		List<CouponRecord> CouponRecordlist = (List<CouponRecord>) dataDao.pageQueryViaParam(couponhql.toString(), pageSize, pageIndex, null);
		
		for (CouponRecord couponrecord : CouponRecordlist) {
			SuserInfo suserinfo = dataDao.getObjectById(SuserInfo.class, couponrecord.getUserid());
			
			couponrecord.setUserphone(suserinfo.getPhone());
			
			if (suserinfo != null) {
				if (CommonUtils.isEmptyString(suserinfo.getRealname())) {
					couponrecord.setUsernick("未设置:" + suserinfo.getPhone());
				} else {
					couponrecord.setUsernick(suserinfo.getRealname() + ":" + suserinfo.getPhone());
				}
			}
			if (couponrecord.getOwnertype() == 1) {
				DriveSchoolInfo driveschool = dataDao.getObjectById(DriveSchoolInfo.class, couponrecord.getOwnerid());
				if (driveschool != null) {
					couponrecord.setSchoolname(driveschool.getName());
				}
			}
			if (couponrecord.getOwnertype() == 2) {
				CuserInfo cuserinfo = dataDao.getObjectById(CuserInfo.class, couponrecord.getOwnerid());
				if (cuserinfo != null) {
					couponrecord.setCusername(cuserinfo.getRealname());
				}
			}
			
			//获取每个用户小巴券总张数
			String counthql2 = "select count(*) from CouponRecord where userid="+couponrecord.getUserid()+"and ownerid="+couponrecord.getOwnerid();
			long totalcount = (Long) dataDao.getFirstObjectViaParam(counthql2, null);
			int tcount = (int)totalcount;
			couponrecord.setTotalcount(tcount);
			//获取每个用户小巴券使用张数
			String counthql3 = "select count(*) from CouponRecord where state=1 and userid="+couponrecord.getUserid()+"and ownerid="+couponrecord.getOwnerid();
			long usecount = (Long) dataDao.getFirstObjectViaParam(counthql3, null);
			int ucount = (int)usecount;
			couponrecord.setUsecount(ucount);
			
			
		}
		
		
		//查询分组后的所有结果，以便获得分组后的总条数count
		//String cthql = "from CouponRecord cr group by cr.userid,cr.ownerid";
		List<CouponRecord> Crlist = (List<CouponRecord>) dataDao.getObjectsViaParam(couponhql.toString(),null);
		long count = Crlist.size();
		//System.out.print(count);
		QueryResult<CouponRecord> result = new QueryResult<CouponRecord>(CouponRecordlist, count);
		return result;
	}
	
//获得所有小巴券发放记录明细
	@Override
	public List<CouponRecord> getCouponRecordList(String starttime, String endtime) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append(" from CouponRecord where 1=1");
		if (!CommonUtils.isEmptyString(starttime)) {
			starttime = starttime + " 00:00:00";
			cuserhql.append(" and usetime > '" + starttime + "'");
		}

		if (!CommonUtils.isEmptyString(endtime)) {
			endtime = endtime + " 23:59:59";
			cuserhql.append(" and usetime <= '" + endtime + "'");
		}
		List<CouponRecord> CouponRecordlist = (List<CouponRecord>) dataDao.getObjectsViaParam(cuserhql.toString(), null);
		return CouponRecordlist;
	}
	
//获得教练小巴券兑换记录
	@SuppressWarnings("unchecked")
	@Override
	public QueryResult<CouponCoach> getCouponCoachInfoByPage(int pageIndex, int pageSize, String name, Integer coupontype, String starttime, String endtime, Integer value, Integer valuetype,
			Integer ownertype, String ownerkey, Integer state)  {
		
		StringBuffer couponhql = new StringBuffer();
		
		if(state != null){
			couponhql.append(" from CouponCoach where state = " + state);
		}else{
			couponhql.append(" from CouponCoach where 1 = 1");
		}
		
		if (!CommonUtils.isEmptyString(name)) {
			couponhql.append(" and coachid in ( select coachid from CuserInfo where realname like '%" + name + "%' or phone like '%" + name + "%')");
		}
		if (coupontype != null) {
			couponhql.append(" and coupontype = " + coupontype);
		}
		if (!CommonUtils.isEmptyString(starttime)) {
			starttime = starttime + " 00:00:00";
			couponhql.append(" and gettime > '" + starttime + "'");
		}

		if (!CommonUtils.isEmptyString(endtime)) {
			endtime = endtime + " 23:59:59";
			couponhql.append(" and gettime <= '" + endtime + "'");
		}
		if (ownertype != null) {
			couponhql.append(" and ownertype = " + ownertype);
			if (ownertype == 1) {
				if (!CommonUtils.isEmptyString(ownerkey)) {
					couponhql.append(" and ownerid in ( select schoolid from DriveSchoolInfo where name like '%" + ownerkey + "%')");
				}
			} else if (ownertype == 2) {
				if (!CommonUtils.isEmptyString(ownerkey)) {
					couponhql.append(" and ownerid in ( select coachid from CuserInfo where realname like '%" + ownerkey + "%')");
				}
			}
		}
		if (value != null) {
			if (valuetype == 1) {
				couponhql.append(" and value >" + value);
			} else if (valuetype == 2) {
				couponhql.append(" and value =" + value);
			} else if (valuetype == 3) {
				couponhql.append(" and value <" + value);
			}
		}
		
		couponhql.append("order by recordid desc");
		//System.out.print(couponhql);
	
		List<CouponCoach> CouponCoachlist = (List<CouponCoach>) dataDao.pageQueryViaParam(couponhql.toString(), pageSize, pageIndex, null);
		
		for (CouponCoach couponcoach : CouponCoachlist) {
			CuserInfo Cuserinfo = dataDao.getObjectById(CuserInfo.class, couponcoach.getCoachid());
			if (Cuserinfo != null) {
				if (CommonUtils.isEmptyString(Cuserinfo.getRealname())) {
					couponcoach.setUsernick("未设置:" + Cuserinfo.getPhone());
				} else {
					couponcoach.setUsernick(Cuserinfo.getRealname() + ":" + Cuserinfo.getPhone());
				}
			}
			if (couponcoach.getOwnertype() == 1) {
				DriveSchoolInfo driveschool = dataDao.getObjectById(DriveSchoolInfo.class, couponcoach.getOwnerid());
				if (driveschool != null) {
					couponcoach.setSchoolname(driveschool.getName());
				}
			}
			if (couponcoach.getOwnertype() == 2) {
				CuserInfo cuserinfo = dataDao.getObjectById(CuserInfo.class, couponcoach.getOwnerid());
				if (cuserinfo != null) {
					couponcoach.setCusername(cuserinfo.getRealname());
				}
			}
			
		}
		
		String counthql = couponhql.insert(0, " select count(*)").toString();
		long count = (Long) dataDao.getFirstObjectViaParam(counthql, null);
		QueryResult<CouponCoach> result = new QueryResult<CouponCoach>(CouponCoachlist, count);
		return result;
	}

	@Override
	public List<DriveSchoolInfo> getDriveSchoolInfoByKeyword(String keyword) {
		String hql = " from DriveSchoolInfo where name like '%" + keyword + "%' ";
		List<DriveSchoolInfo> dirveschoollist = (List<DriveSchoolInfo>) dataDao.getObjectsViaParam(hql, null);
		return dirveschoollist;
	}

	@Override
	public List<CuserInfo> getCuserInfoByKeyword(String keyword) {
		String hql = " from CuserInfo where phone like '%" + keyword + "%' or realname like '%" + keyword + "%' ";
		List<CuserInfo> cuserlist = (List<CuserInfo>) dataDao.getObjectsViaParam(hql, null);
		return cuserlist;
	}

	// 作废优惠券
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void cancelcoupon(int recordid) {
		CouponRecord couponRecord = dataDao.getObjectById(CouponRecord.class, recordid);
		if (couponRecord != null) {
			couponRecord.setState(2);
			couponRecord.setGettime(new Date());// 更新作废时间
			dataDao.updateObject(couponRecord);
		}
	}
	
	
	// 作废所有未使用优惠券
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void cancelallcoupon(int userid) {
		
		String couponhql = "from CouponRecord  where state = 0 and userid="+userid;
		List<CouponRecord> Crlist = (List<CouponRecord>) dataDao.getObjectsViaParam(couponhql.toString(),null);

		for (CouponRecord couponrecord : Crlist) {
			if (couponrecord != null) {
				couponrecord.setState(2);
				couponrecord.setGettime(new Date());// 更新作废时间
				dataDao.updateObject(couponrecord);
			}
			
		}
		
	}
	

	@Override
	public QueryResult<CouponRecord> getCancelCouponReecordListByPage(int pageIndex, int pageSize, String name, Integer coupontype, String starttime, String endtime, Integer value, Integer valuetype,
			Integer ownertype, String ownerkey) {
		StringBuffer couponhql = new StringBuffer();
		couponhql.append(" from CouponRecord where state = 2 ");// 不包括已经作废的
		if (!CommonUtils.isEmptyString(name)) {
			couponhql.append(" and userid in ( select studentid from SuserInfo where realname like '%" + name + "%' or phone like '%" + name + "%')");
		}
		if (coupontype != null) {
			couponhql.append(" and coupontype = " + coupontype);
		}
		if (!CommonUtils.isEmptyString(starttime)) {
			starttime = starttime + " 00:00:00";
			couponhql.append(" and end_time > '" + starttime + "'");
		}

		if (!CommonUtils.isEmptyString(endtime)) {
			endtime = endtime + " 23:59:59";
			couponhql.append(" and end_time <= '" + endtime + "'");
		}
		if (ownertype != null) {
			couponhql.append(" and ownertype = " + ownertype);
			if (ownertype == 1) {
				if (!CommonUtils.isEmptyString(ownerkey)) {
					couponhql.append(" and ownerid in ( select schoolid from DriveSchoolInfo where name like '%" + ownerkey + "%')");
				}
			} else if (ownertype == 2) {
				if (!CommonUtils.isEmptyString(ownerkey)) {
					couponhql.append(" and ownerid in ( select coachid from CuserInfo where realname like '%" + ownerkey + "%')");
				}
			}
		}
		if (value != null) {
			if (valuetype == 1) {
				couponhql.append(" and value >" + value);
			} else if (valuetype == 2) {
				couponhql.append(" and value =" + value);
			} else if (valuetype == 3) {
				couponhql.append(" and value <" + value);
			}
		}
		couponhql.append(" order by recordid desc");
		List<CouponRecord> CouponRecordlist = (List<CouponRecord>) dataDao.pageQueryViaParam(couponhql.toString(), pageSize, pageIndex, null);
		for (CouponRecord couponrecord : CouponRecordlist) {
			SuserInfo suserinfo = dataDao.getObjectById(SuserInfo.class, couponrecord.getUserid());
			if (suserinfo != null) {
				if (CommonUtils.isEmptyString(suserinfo.getRealname())) {
					couponrecord.setUsernick("未设置:" + suserinfo.getPhone());
				} else {
					couponrecord.setUsernick(suserinfo.getRealname() + ":" + suserinfo.getPhone());
				}
			}
			if (couponrecord.getOwnertype() == 1) {
				DriveSchoolInfo driveschool = dataDao.getObjectById(DriveSchoolInfo.class, couponrecord.getOwnerid());
				if (driveschool != null) {
					couponrecord.setSchoolname(driveschool.getName());
				}
			}
			if (couponrecord.getOwnertype() == 2) {
				CuserInfo cuserinfo = dataDao.getObjectById(CuserInfo.class, couponrecord.getOwnerid());
				if (cuserinfo != null) {
					couponrecord.setCusername(cuserinfo.getRealname());
				}
			}
		}
		String counthql = couponhql.insert(0, " select count(*)").toString();
		long count = (Long) dataDao.getFirstObjectViaParam(counthql, null);
		QueryResult<CouponRecord> result = new QueryResult<CouponRecord>(CouponRecordlist, count);
		return result;
	}

}
