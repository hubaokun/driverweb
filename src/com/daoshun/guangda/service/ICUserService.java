package com.daoshun.guangda.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.daoshun.common.QueryResult;
import com.daoshun.guangda.pojo.AdminInfo;
import com.daoshun.guangda.pojo.BalanceCoachInfo;
import com.daoshun.guangda.pojo.CApplyCashInfo;
import com.daoshun.guangda.pojo.CaddAddressInfo;
import com.daoshun.guangda.pojo.CoachLevelInfo;
import com.daoshun.guangda.pojo.CoachStudentInfo;
import com.daoshun.guangda.pojo.ComplaintSetInfo;
import com.daoshun.guangda.pojo.CouponRecord;
import com.daoshun.guangda.pojo.CsubjectInfo;
import com.daoshun.guangda.pojo.CuserInfo;
import com.daoshun.guangda.pojo.DriveSchoolInfo;
import com.daoshun.guangda.pojo.LogInfo;
import com.daoshun.guangda.pojo.ModelsInfo;
import com.daoshun.guangda.pojo.PermissionSetInfo;
import com.daoshun.guangda.pojo.RechargeRecordInfo;
import com.daoshun.guangda.pojo.SchoolBalance;
import com.daoshun.guangda.pojo.SuserInfo;
import com.daoshun.guangda.pojo.SystemSetInfo;
import com.daoshun.guangda.pojo.TeachcarInfo;
import com.daoshun.guangda.pojo.UserPushInfo;
import com.daoshun.guangda.pojo.VerifyCodeInfo;

public interface ICUserService {

	public abstract TeachcarInfo getTeachcarInfoByID(int teachCarId);

	public abstract int getCoachAllCouponTime(int coachid);

	public abstract CuserInfo registerUser(String phone, String token);

	public abstract CsubjectInfo getFirstSubject();

	// 验证登陆验证码的有效性 返回：1.验证码正常 0：验证码不存在或者错误 2:验证码已经过期
	public abstract int checkVerCode(String phone, String vcode);

	/** 根据登录名找到admin对象信息 **/
	public abstract AdminInfo getAdminInfo(String loginname);

	/** 根据电话号得到教练用户信息(根据登录名，登录名即是电话号) **/
	public abstract CuserInfo getCuserByPhone(String loginname);

	/** 新增教练用户 **/
	public abstract void addCuser(CuserInfo cuser);

	/** 根据教练id找到教练用户信息 **/
	public abstract CuserInfo getCuserByCoachid(String coachid);

	/** 更新教练信息数据 **/
	public abstract void updateCuser(CuserInfo cuser);

	/** 得到所有准教车型 **/
	public abstract List<ModelsInfo> getAllModelInfo();

	/** 修改头像 返回头像图片地址 **/
	public abstract String changeAvatar(CuserInfo cuser);

	/** 根据图片id返回图片url **/
	public abstract String backUrl(int picid);

	/** 根据车型id找到车型对象 **/
	public abstract ModelsInfo getmodellistbycoachid(int modelid);

	/** 根据发送给教练的验证码找到对应的信息 **/
	public abstract VerifyCodeInfo getVerifyCodeInfo(String code, String phone);

	/** 得到教练当前地址信息 **/
	public abstract CaddAddressInfo getcoachaddress(String coachid);

	/******* 根据科目id获得信息 *********/
	public abstract CsubjectInfo getsubjectBysubjectid(int subjectid);

	/******* 根据地址id找到地址信息 ******/
	public abstract CaddAddressInfo getaddress(int adressid);

	/******* 算出教练年龄 ******/
	public abstract int getCoachAgeByid(int id);

	public abstract HashMap<String, Object> recharge(String coachid, String amount);

	/******* 购买成功的回调  支付宝******/
	public abstract void buySuccess(String out_trade_no, String buyer_id, String buyer_email);
	/******* 购买成功的回调  微信 ******/
	public abstract void buySuccessbyweixin(String out_trade_no, String openid,String weixinorderid);

	/******* 获取教练的充值历史和余额 ******/
	public abstract HashMap<String, Object> getBalanceList(String coachid);

	/******* 获取教练的小巴币记录 ******/
	public abstract HashMap<String, Object> getCoinRecordList(String coachid);

	/*********************************************** ACTION ************************************************/
	/** id找教练 **/
	public abstract CuserInfo getCoachByid(int id);

	/** 很据身份证找教练 **/
	public abstract CuserInfo getCoachByIdnum(String idnum);

	/** 得到所有的管理员 **/
	public abstract QueryResult<AdminInfo> getAdmins(Integer pageIndex, int pagesize);

	/** 关键字搜索管理员 **/
	public abstract QueryResult<AdminInfo> getAdminsBykeyword(String searchlogin, String searchtelphone, Integer pageIndex, int pagesize);

	/** 根据id找到管理员 **/
	public abstract AdminInfo getAdminInfoByid(int adminid);

	/** 根据管理员账号找到管理员 **/
	public abstract AdminInfo getAdminInfoBylogin(String loginname);

	/** 根据id找到驾校 **/
	public abstract DriveSchoolInfo getDriveSchoolInfoByid(int id);

	/** 得到所有权限 **/
	public abstract List<PermissionSetInfo> getPermission();
	
	/**根据权限id字符串获取权限名称**/
	 public String getPermissionNamesByPString(String pString);
	/**根据已获得权限，查询所有权限并标记check**/
	public List<PermissionSetInfo> getPermissionByString(String pString);
	
	/** 得到所有教练列表 **/
	public abstract List<CuserInfo> getCoachlist();

	/** 得到所有学员 **/
	public abstract List<SuserInfo> getStudentlist();

	/** 得到所有教练信息 **/
	public abstract QueryResult<CuserInfo> getAllCuserInfos(Integer pageIndex, int pagesize);

	/****** 筛选教练 *******/
	public abstract QueryResult<CuserInfo> getCoachByKeyword(String searchname, String searchphone, Integer driveSchoolname, String carlicense, Integer checkstate, Integer pageIndex, int pagesize);

	/****** 筛选教练日志 *******/
	public abstract QueryResult<LogInfo> getCoachLogByKeyword(Integer pageIndex, int pagesize,String username, String formname, String starttime, String endtime);
	
	/** 得到驾校信息 **/
	public abstract List<DriveSchoolInfo> getDriveSchoolInfo();

	public abstract List<DriveSchoolInfo> getDriveSchoolListById(int schoolid);

	public abstract QueryResult<DriveSchoolInfo> getDriveSchoolInfoByPage(int pageIndex, int pageSize);

	/****** 审核通过改变id状态 ******/
	public abstract void checkPass(String coachid, int type,int userid);

	/****** 设置教练保证金金额 ****/
	public abstract void setGmoneys(String coachid, BigDecimal gmoney);

	/****** 设置教练等级 ****/
	public abstract void setLevels(String coachid, int level);

	@SuppressWarnings({"unchecked", "deprecation"})
	QueryResult<CApplyCashInfo> getCoachHistoryApplyBySearch(int schoolid, String searchname, String searchphone, String amount, String inputamount, String minsdate, String maxsdate, Integer pageIndex, int pagesize);

	@SuppressWarnings({"unchecked", "deprecation"})
	QueryResult<CApplyCashInfo> getCoachHistoryBalanceBySearchFinance(int schoolid, String searchname, String searchphone, String amount, String inputamount, String minsdate, String maxsdate, Integer pageIndex, int pagesize);

	
	
	/** 分页得到准教车型列表信息 **/
	public abstract QueryResult<ModelsInfo> getModellist(Integer pageIndex, int pagesize);

	/** 得到所有准教车型信息 **/
	public abstract List<ModelsInfo> getModelList();

	/** 根据modelname 找到车型信息 **/
	public abstract ModelsInfo getModelsInfoBymodelname(String modelname);

	/** 得到教练等级列表 **/
	public abstract List<CoachLevelInfo> getLevellist();

	/** 根据levelname 找到等级信息 **/
	public abstract CoachLevelInfo getLevelInfoBylevelname(String levelname);

	/** 获得投诉原因列表 **/
	public abstract List<ComplaintSetInfo> getComplaintSetInfolist();

	/** 添加某个类型对象 **/
	public abstract void addObject(Object object);

	/** 删除某个类型对象 **/
	public abstract void delObject(Object object);

	/** 修改某个类型对象 **/
	public abstract void updateObject(Object object);

	/** 根据类型查找到投诉原因 **/
	public abstract List<ComplaintSetInfo> getComplaintSetByType(int searchtype);

	/** 根据id得设置原因类型 **/
	public abstract ComplaintSetInfo getComplaintSetInfoByid(int setid);

	/** 得到科目信息 **/
	public abstract List<CsubjectInfo> getSubjectInfo();

	/** 根据id找到科目信息 **/
	public abstract CsubjectInfo getSubjectById(int subjectid);

	/** 根据科目名称找到科目信息 **/
	public abstract CsubjectInfo getSubjectByName(String subjectname);

	/** 得到系统配置的信息 **/
	public abstract SystemSetInfo getSystemSetInfo();
	/** 重置教练开课状态**/
	public abstract void resetCoachCoursestate();

	/** 根据id找到系统配置信息 **/
	public abstract SystemSetInfo getSystemSetInfoByid(int dateid);

	/** 获取教练提现申请列表 */
	public abstract QueryResult<CApplyCashInfo> getCoachApplyList(Integer pageIndex, int pagesize);
	
	/** 财务获取教练提现申请列表 */
	public abstract QueryResult<CApplyCashInfo> getCoachApplyListFinance(Integer pageIndex, int pagesize);

	/** 获取历史提现记录列表 */
	public abstract QueryResult<BalanceCoachInfo> getApplyRecordList(Integer pageIndex, int pagesize);

	/** 提现申请审核 */
	public abstract void applyCheckPass(int coachid);
	/** 提现申请审核 */
	public abstract void applyCheckNoPass(int coachid);
	/** 提现申请审核 */
	public abstract void applyCheckrevocation(int coachid);
	
	/** 教练提现申请返审 */
	public abstract void applyCheckback(int applyid);
	
	/** 教练提现申请返审 */
	public abstract void applyCheckPassTwice(int applyid);

	/** 根据条件搜索提现记录 */
	public abstract QueryResult<CApplyCashInfo> getCoachApplyBySearch(String searchname, String searchphone, String amount, String inputamount, Integer schoolid, String minsdate, String maxsdate,Integer state,
			Integer pageIndex, int pagesize);

	/** 根据条件搜索历史提现 */
	public abstract QueryResult<BalanceCoachInfo> getCoachBalanceBySearch(int schoolid, String searchname, String searchphone, String amount, String inputamount, String minsdate, String maxsdate,
			Integer pageIndex, int pagesize);

	/** 获取提现申请信息 */
	public abstract CApplyCashInfo getApplyById(int applyid);

	/** 根据userid 和usertype 找到推送数据 **/
	public abstract UserPushInfo getUserPushInfo(int userid, int usertype);

	public abstract CaddAddressInfo getCurrentAddressBycoachid(String coachid);

	public abstract CuserInfo getCuserById_cardnum(String id_cardnum);

	public abstract CuserInfo getCuserByCoach_cardnum(String coach_cardnum);

	public abstract CuserInfo getCuserByCar_cardnum(String car_cardnum);

	public abstract CuserInfo getCuserByBrive_cardnum(String drive_cardnum);

	public abstract List<DriveSchoolInfo> getDriveSchoolList(String keyword);
	
	public abstract List<DriveSchoolInfo> getDriveSchoolListBycity(String keyword,String provinceid,String cityid);

	public abstract List<TeachcarInfo> getTeachcarInfolist();

	public abstract TeachcarInfo getTeachcarInfoBymodelname(String modelname);

	public abstract void coachCancelOrder(CuserInfo cuser);

	public abstract void addSchoolBalance(SchoolBalance entity);

	public abstract QueryResult<BalanceCoachInfo> getRechargeRecordList(int pageIndex, int pagesize);

	public abstract QueryResult<BalanceCoachInfo> searchCoachRecharge(String searchname, String searchphone, String amount, String inputamount, String minsdate, String maxsdate, int pageIndex,
			int pagesize);
	public  QueryResult<CuserInfo> getCuserReport(String provinceid,String cityid,String areaid,String drive_school,String startdate,String enddate,Integer pageIndex, int pagesize);
	/**
	 * 按教练ID查询总订单数
	 * @param coachid
	 * @return
	 */
	public Long getOrderSum(int coachid);
	public Long getOrderOver(int coachid);
	public Long getOrderCancel(int coachid);
	
	/**
	 *  获取教练提现详情
	 */
	public abstract Map getWithdrawcashdetail(int applyid,String coachid);
	/**
	 *  设置教练先显示用驾校名称
	 */
	public abstract void setCoachDriverSchool(List<CuserInfo> cusrlist);
	/**
	 * 根据驾校名称找到驾校ID
	 */
	public abstract Integer getDriverSchoolIDbyName(String schoolname);
	 public List<DriveSchoolInfo> getDriveSchoolInfoByCityId(String cityid);
	 public int getCanUseCoinnumForPlatform(String coachid);
	 public int getCanUseCoinnumForDriveSchool( String coachid);
	 public int getCanUseCoinnumForCoach(String coachid) ;
	 public HashMap<String, Object> getCoinAffiliation(String coachid);
	 public RechargeRecordInfo getrechargerecord(String recordid);
	/**
	 * 增加教练可发放小巴券总数
	 * @param coachid  教练ID
	 * @param pub_count 新增可发放数
	 */
	 public abstract void updateCoachCoupon(String coachid,Integer pub_count);
	 /**
	  * 获取教练所有学员信息
	  */
	 public abstract List<SuserInfo> getCoachStudent(String coachid);
	 /**
	  * 获取学员小巴券可用张数
	  */
	 public abstract Integer getstudentCoupontotal(String studentid,String coachid);
	 /**
	  * 获取学员小巴券剩余张数
	  */
	 public abstract Integer getstudentCouponrest(String studentid,String coachid);
	 /**
	  * 教练发放小巴券
	  */
	 public abstract String coachgrantcoupon(String coachid,String phone,Integer pubnum);
	 /**
	  * 获取教练发放小巴券记录
	  */
	 public abstract HashMap<String, Object> getcoachcouponlist(String coachid,String pagenum);
	 /**
	  * 获取教练是否拥有发放小巴券权限
	  */
	 public abstract Integer getcoachcouponlimit(String coachid);
	 	 //获取父权限
	 public List<PermissionSetInfo> getParentPermissions();
	 
	 //添加权限
	 public boolean addPermission(PermissionSetInfo info);
	 
	 //获得权限数
	 public long getSubPermissionCount(int pid);
	 public HashMap<String, Object> getCoinAffiliationException();
	 //获取所有明星签约教练列表
	 public abstract QueryResult<CuserInfo> getCoachListBySignstate(String searchname, String searchphone, Integer driveSchoolname, String carlicense, Integer checkstate,Integer signstate, String signexpiredmin,String signexpiredmax,  Integer pageIndex, int pagesize);
	 public void addBackstageRecharge(String coachid, String amount,String operatetype,String reason);
	 public Map getCoachCashOrder(int applyid, String coachid);
}
