package com.daoshun.guangda.serviceImpl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alipay.config.AlipayConfig;
import com.daoshun.common.ApplePushUtil;
import com.daoshun.common.CommonUtils;
import com.daoshun.common.Constant;
import com.daoshun.common.QueryResult;
import com.daoshun.guangda.pojo.AdminInfo;
import com.daoshun.guangda.pojo.BalanceCoachInfo;
import com.daoshun.guangda.pojo.BalanceStudentInfo;
import com.daoshun.guangda.pojo.CApplyCashInfo;
import com.daoshun.guangda.pojo.CaddAddressInfo;
import com.daoshun.guangda.pojo.CoachLevelInfo;
import com.daoshun.guangda.pojo.ComplaintSetInfo;
import com.daoshun.guangda.pojo.CouponCoach;
import com.daoshun.guangda.pojo.CsubjectInfo;
import com.daoshun.guangda.pojo.CuserInfo;
import com.daoshun.guangda.pojo.DriveSchoolInfo;
import com.daoshun.guangda.pojo.ModelsInfo;
import com.daoshun.guangda.pojo.OrderInfo;
import com.daoshun.guangda.pojo.PermissionSetInfo;
import com.daoshun.guangda.pojo.RechargeRecordInfo;
import com.daoshun.guangda.pojo.SchoolBalance;
import com.daoshun.guangda.pojo.SuserInfo;
import com.daoshun.guangda.pojo.SystemSetInfo;
import com.daoshun.guangda.pojo.TeachcarInfo;
import com.daoshun.guangda.pojo.UserPushInfo;
import com.daoshun.guangda.pojo.VerifyCodeInfo;
import com.daoshun.guangda.service.ICUserService;

/**
 * @author liukn
 * 
 */
@Service("cuserService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class CUserServiceImpl extends BaseServiceImpl implements ICUserService {

	@Override
	public CuserInfo getCuserByPhone(String loginname) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append(" from CuserInfo where phone = :phone ");
		String[] params = { "phone" };
		CuserInfo cuser = (CuserInfo) dataDao.getFirstObjectViaParam(cuserhql.toString(), params, loginname);
		return cuser;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void addCuser(CuserInfo cuser) {
		dataDao.addObject(cuser);
	}

	@Override
	public CuserInfo getCuserByCoachid(String coachid) {
		CuserInfo cuser = dataDao.getObjectById(CuserInfo.class, CommonUtils.parseInt(coachid, 0));
		if (cuser != null) {
			if(cuser.getLevel()!=0){
				CoachLevelInfo level = dataDao.getObjectById(CoachLevelInfo.class, cuser.getLevel());
				if(level!=null){
					cuser.setLevelname(level.getLevelname());
				}
			}
			cuser.setAvatarurl(getFilePathById(cuser.getAvatar()));
			cuser.setId_cardpicburl(getFilePathById(cuser.getId_cardpicb()));
			cuser.setId_cardpicfurl(getFilePathById(cuser.getId_cardpicf()));
			cuser.setDrive_cardpicurl(getFilePathById(cuser.getDrive_cardpic()));
			cuser.setCar_cardpicfurl(getFilePathById(cuser.getCar_cardpicf()));
			cuser.setCar_cardpicburl(getFilePathById(cuser.getCar_cardpicb()));
			cuser.setCoach_cardpicurl(getFilePathById(cuser.getCoach_cardpic()));
			dataDao.updateObject(cuser);
		}

		return cuser;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ModelsInfo> getAllModelInfo() {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from ModelsInfo where 1=1 order by searchname");
		List<ModelsInfo> modellist = (List<ModelsInfo>) dataDao.getObjectsViaParam(cuserhql.toString(), null);
		return modellist;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public String changeAvatar(CuserInfo cuser) {
		dataDao.updateObject(cuser);
		return getFilePathById(cuser.getAvatar());
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void updateCuser(CuserInfo cuser) {
		dataDao.updateObject(cuser);
	}

	@Override
	public String backUrl(int picid) {
		return getFilePathById(picid);
	}

	@Override
	public ModelsInfo getmodellistbycoachid(int modelid) {
		ModelsInfo model = dataDao.getObjectById(ModelsInfo.class, modelid);
		return model;
	}

	@Override
	public VerifyCodeInfo getVerifyCodeInfo(String code, String phone) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from VerifyCodeInfo where code = :code and totype = 1 and phone = :phone");
		String[] params = { "code", "phone" };
		VerifyCodeInfo verifyCodeInfo = (VerifyCodeInfo) dataDao.getFirstObjectViaParam(cuserhql.toString(), params, code, phone);
		return verifyCodeInfo;
	}

	/**
	 * 获取教练的默认上车地址
	 */
	@Override
	public CaddAddressInfo getcoachaddress(String coachid) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from CaddAddressInfo where coachid = :coachid and iscurrent = 1");
		String[] params = { "coachid" };
		CaddAddressInfo addressInfo = (CaddAddressInfo) dataDao.getFirstObjectViaParam(cuserhql.toString(), params, CommonUtils.parseInt(coachid, 0));
		return addressInfo;
	}

	@Override
	public CsubjectInfo getsubjectBysubjectid(int subjectid) {
		CsubjectInfo subjectInfo = dataDao.getObjectById(CsubjectInfo.class, subjectid);
		return subjectInfo;
	}

	@Override
	public CaddAddressInfo getaddress(int adressid) {
		CaddAddressInfo addressInfo = dataDao.getObjectById(CaddAddressInfo.class, adressid);
		return addressInfo;
	}

	/****************************************** ACTION *********************************************************/
	@SuppressWarnings("unchecked")
	@Override
	public QueryResult<AdminInfo> getAdmins(Integer pageIndex, int pagesize) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from AdminInfo");
		List<AdminInfo> adminlist = (List<AdminInfo>) dataDao.pageQueryViaParam(cuserhql.toString(), pagesize, pageIndex, null);
		String counthql = " select count(*) " + cuserhql.toString();
		long total = (Long) dataDao.getFirstObjectViaParam(counthql, null);
		return new QueryResult<AdminInfo>(adminlist, total);
	}

	@SuppressWarnings("unchecked")
	@Override
	public QueryResult<AdminInfo> getAdminsBykeyword(String searchlogin, String searchtelphone, Integer pageIndex, int pagesize) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from AdminInfo where 1=1");
		if (!CommonUtils.isEmptyString(searchlogin)) {
			cuserhql.append(" and login_account like '%" + searchlogin + "%'");
		}
		if (!CommonUtils.isEmptyString(searchtelphone)) {
			cuserhql.append(" and telphone like '%" + searchtelphone + "%'");
		}
		List<AdminInfo> adminlist = (List<AdminInfo>) dataDao.pageQueryViaParam(cuserhql.toString(), pagesize, pageIndex, null);
		String counthql = " select count(*) " + cuserhql.toString();
		long total = (Long) dataDao.getFirstObjectViaParam(counthql, null);
		return new QueryResult<AdminInfo>(adminlist, total);
	}

	@Override
	public AdminInfo getAdminInfoByid(int adminid) {
		AdminInfo adminInfo = dataDao.getObjectById(AdminInfo.class, adminid);
		return adminInfo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PermissionSetInfo> getPermission() {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from PermissionSetInfo");
		List<PermissionSetInfo> permissionSetInfo = (List<PermissionSetInfo>) dataDao.getObjectsViaParam(cuserhql.toString(), null);
		return permissionSetInfo;
	}

	@Override
	public AdminInfo getAdminInfoBylogin(String loginname) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from AdminInfo where login_account = :login_account");
		String[] params = { "login_account" };
		AdminInfo adminInfo = (AdminInfo) dataDao.getFirstObjectViaParam(cuserhql.toString(), params, loginname);
		return adminInfo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public QueryResult<CuserInfo> getAllCuserInfos(Integer pageIndex, int pagesize) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from CuserInfo where isquit = 0 ");
		List<CuserInfo> cuserInfolist = (List<CuserInfo>) dataDao.pageQueryViaParam(cuserhql.toString() + " order by id desc ", pagesize, pageIndex, null);
		String counthql = " select count(*) " + cuserhql.toString();
		long total = (Long) dataDao.getFirstObjectViaParam(counthql, null);
		return new QueryResult<CuserInfo>(cuserInfolist, total);
	}

	@SuppressWarnings("unchecked")
	@Override
	public QueryResult<CuserInfo> getCoachByKeyword(String searchname, String searchphone, Integer driveSchoolname, String carlicense, Integer checkstate, Integer pageIndex, int pagesize) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from CuserInfo where 1=1 ");

		if (!CommonUtils.isEmptyString(searchname)) {
			cuserhql.append(" and realname like '%" + searchname + "%'");
		}
		if (!CommonUtils.isEmptyString(searchphone)) {
			cuserhql.append(" and phone like '%" + searchphone + "%'");
		}

		if (driveSchoolname != 0) {
			if (driveSchoolname == -1) {
				cuserhql.append(" and drive_school =null and drive_schoolid =null ");
			} else {
				cuserhql.append(" and drive_schoolid =" + driveSchoolname);
			}

		}
		if (checkstate != null) {
			if (checkstate == 0) {
				cuserhql.append(" and state = 0 ");
			}
			if (checkstate == 1) {
				cuserhql.append(" and state = 1 ");
			}
			
			if (checkstate == 2) {
				cuserhql.append(" and state = 2 ");
			}
			
			if (checkstate == 3) {
				cuserhql.append(" and state = 3 ");
			}
		}

		if (!CommonUtils.isEmptyString(carlicense)) {
			cuserhql.append(" and carlicense like '%" + carlicense + "%'");
		}

		List<CuserInfo> cuserInfolist = (List<CuserInfo>) dataDao.pageQueryViaParam(cuserhql.toString() + " order by id desc ", pagesize, pageIndex, null);
		String counthql = " select count(*) " + cuserhql.toString();
		long total = (Long) dataDao.getFirstObjectViaParam(counthql, null);
		return new QueryResult<CuserInfo>(cuserInfolist, total);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void checkPass(String coachid, int type) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from CuserInfo where coachid = :coachid");
		String[] params = { "coachid" };
		CuserInfo cuserInfo = (CuserInfo) dataDao.getFirstObjectViaParam(cuserhql.toString(), params, CommonUtils.parseInt(coachid, 0));
		cuserInfo.setState(type);
		dataDao.updateObject(cuserInfo);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void setGmoneys(String coachid, BigDecimal gmoney) {
		CuserInfo cuserInfo = dataDao.getObjectById(CuserInfo.class, CommonUtils.parseInt(coachid, 0));
		cuserInfo.setGmoney(gmoney);
		dataDao.updateObject(cuserInfo);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void setLevels(String coachid, int level) {
		CuserInfo cuserInfo = dataDao.getObjectById(CuserInfo.class, CommonUtils.parseInt(coachid, 0));
		cuserInfo.setLevel(level);
		dataDao.updateObject(cuserInfo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public QueryResult<CApplyCashInfo> getCoachApplyList(Integer pageIndex, int pagesize) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from CApplyCashInfo where state = 0 order by addtime desc");
		List<CApplyCashInfo> applycashlist = (List<CApplyCashInfo>) dataDao.pageQueryViaParam(cuserhql.toString(), pagesize, pageIndex, null);
		if (applycashlist != null && applycashlist.size() > 0) {
			for (CApplyCashInfo capplyCash : applycashlist) {
				CuserInfo coach = dataDao.getObjectById(CuserInfo.class, capplyCash.getCoachid());
				if (coach != null) {
					capplyCash.setCoach(coach);
				}
			}
		}
		String counthql = " select count(*) " + cuserhql.toString();
		long total = (Long) dataDao.getFirstObjectViaParam(counthql, null);
		return new QueryResult<CApplyCashInfo>(applycashlist, total);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void applyCheckPass(int coachid) {
		CApplyCashInfo capplyCash = dataDao.getObjectById(CApplyCashInfo.class, coachid);
		if (capplyCash != null) {
			capplyCash.setState(1);
			capplyCash.setUpdatetime(new Date());
			dataDao.updateObject(capplyCash);
			CuserInfo coach = dataDao.getObjectById(CuserInfo.class, capplyCash.getCoachid());
			if (coach != null) {
				coach.setFmoney(coach.getFmoney().subtract(capplyCash.getAmount()));
				dataDao.updateObject(coach);
			}
			BalanceCoachInfo balancoach = new BalanceCoachInfo();
			balancoach.setType(2);
			balancoach.setAddtime(new Date());
			balancoach.setAmount(capplyCash.getAmount());
			balancoach.setUserid(capplyCash.getCoachid());
			balancoach.setAmount_out1(new BigDecimal(0));
			balancoach.setAmount_out2(new BigDecimal(0));
			dataDao.addObject(balancoach);
			if (capplyCash.getSchoolid() != null && capplyCash.getSchoolid() > 0) {
				DriveSchoolInfo schoolinfo = dataDao.getObjectById(DriveSchoolInfo.class, capplyCash.getSchoolid());
				if (schoolinfo != null) {
					schoolinfo.setMoney(schoolinfo.getMoney().add(capplyCash.getAmount()));
					dataDao.updateObject(schoolinfo);
					SchoolBalance schoolbalance = new SchoolBalance();
					schoolbalance.setAddtime(new Date());
					schoolbalance.setCoachid(coachid);
					schoolbalance.setAmount(capplyCash.getAmount());
					schoolbalance.setSchoolid(capplyCash.getSchoolid());
					schoolbalance.setType(1);
					dataDao.addObject(schoolbalance);
				}
			}
		}
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public QueryResult<CApplyCashInfo> getCoachApplyBySearch(String searchname, String searchphone, String amount, String inputamount, Integer schoolid, String minsdate, String maxsdate,
			Integer pageIndex, int pagesize) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from CApplyCashInfo where state = 0");
		if (!CommonUtils.isEmptyString(searchname)) {
			cuserhql.append(" and coachid in (select coachid from CuserInfo where realname like '%" + searchname + "%')");
		}
		if (!CommonUtils.isEmptyString(searchphone)) {
			cuserhql.append(" and coachid in (select coachid from CuserInfo where phone like '%" + searchphone + "%')");
		}
		if (CommonUtils.parseFloat(inputamount, 0) != 0) {
			if (CommonUtils.parseInt(amount, -1) == 0) {
				cuserhql.append("and amount >" + inputamount);
			} else if (CommonUtils.parseInt(amount, -1) == 1) {
				cuserhql.append("and amount =" + inputamount);
			} else {
				cuserhql.append("and amount <" + inputamount);
			}
		}
		if (schoolid != null) {
			cuserhql.append(" and schoolid =" + schoolid);
		}
		if (!CommonUtils.isEmptyString(minsdate)) {
			cuserhql.append("and addtime >'" + minsdate + "'");
		}
		if (!CommonUtils.isEmptyString(maxsdate)) {
			Date enddate = CommonUtils.getDateFormat(maxsdate, "yyyy-MM-dd");
			enddate.setHours(23);
			enddate.setMinutes(59);
			enddate.setSeconds(59);
			String endmaxstime = CommonUtils.getTimeFormat(enddate, "yyyy-MM-dd HH:mm:ss");
			cuserhql.append("and addtime <'" + endmaxstime + "'");
		}
		cuserhql.append("order by addtime desc");
		List<CApplyCashInfo> applycashlist = (List<CApplyCashInfo>) dataDao.pageQueryViaParam(cuserhql.toString(), pagesize, pageIndex, null);
		if (applycashlist != null && applycashlist.size() > 0) {
			for (CApplyCashInfo capplyCash : applycashlist) {
				CuserInfo coach = dataDao.getObjectById(CuserInfo.class, capplyCash.getCoachid());
				if (coach != null) {
					capplyCash.setCoach(coach);
				}
			}
		}
		String counthql = " select count(*) " + cuserhql.toString();
		long total = (Long) dataDao.getFirstObjectViaParam(counthql, null);
		return new QueryResult<CApplyCashInfo>(applycashlist, total);
	}

	@SuppressWarnings("unchecked")
	@Override
	public QueryResult<BalanceCoachInfo> getApplyRecordList(Integer pageIndex, int pagesize) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from BalanceCoachInfo order by addtime desc");
		List<BalanceCoachInfo> balancecoachlist = (List<BalanceCoachInfo>) dataDao.pageQueryViaParam(cuserhql.toString(), pagesize, pageIndex, null);
		if (balancecoachlist != null && balancecoachlist.size() > 0) {
			for (BalanceCoachInfo balanceCoach : balancecoachlist) {
				CuserInfo coach = dataDao.getObjectById(CuserInfo.class, balanceCoach.getUserid());
				if (coach != null) {
					balanceCoach.setRealname(coach.getRealname());
					balanceCoach.setPhone(coach.getPhone());
				}
			}
		}
		String counthql = " select count(*) " + cuserhql.toString();
		long total = (Long) dataDao.getFirstObjectViaParam(counthql, null);
		return new QueryResult<BalanceCoachInfo>(balancecoachlist, total);
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public QueryResult<BalanceCoachInfo> getCoachBalanceBySearch(int schoolid, String searchname, String searchphone, String amount, String inputamount, String minsdate, String maxsdate,
			Integer pageIndex, int pagesize) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from BalanceCoachInfo where 1=1");
		if (schoolid > 0) {
			cuserhql.append(" and userid in (select coachid from CuserInfo where drive_schoolid = " + schoolid + ")");
		}
		if (!CommonUtils.isEmptyString(searchname)) {
			cuserhql.append(" and userid in (select coachid from CuserInfo where realname like '%" + searchname + "%')");
		}
		if (!CommonUtils.isEmptyString(searchphone)) {
			cuserhql.append(" and userid in (select coachid from CuserInfo where phone like '%" + searchphone + "%')");
		}
		if (CommonUtils.parseFloat(inputamount, 0) != 0) {
			if (CommonUtils.parseInt(amount, -1) == 0) {
				cuserhql.append("and amount >" + inputamount);
			} else if (CommonUtils.parseInt(amount, -1) == 1) {
				cuserhql.append("and amount =" + inputamount);
			} else {
				cuserhql.append("and amount <" + inputamount);
			}
		}
		if (!CommonUtils.isEmptyString(minsdate)) {
			cuserhql.append("and addtime >'" + minsdate + "'");
		}
		if (!CommonUtils.isEmptyString(maxsdate)) {
			Date enddate = CommonUtils.getDateFormat(maxsdate, "yyyy-MM-dd");
			enddate.setHours(23);
			enddate.setMinutes(59);
			enddate.setSeconds(59);
			String endmaxstime = CommonUtils.getTimeFormat(enddate, "yyyy-MM-dd HH:mm:ss");
			cuserhql.append("and addtime <'" + endmaxstime + "'");
		}
		cuserhql.append("order by addtime desc");
		List<BalanceCoachInfo> applycashlist = (List<BalanceCoachInfo>) dataDao.pageQueryViaParam(cuserhql.toString(), pagesize, pageIndex, null);
		if (applycashlist != null && applycashlist.size() > 0) {
			for (BalanceCoachInfo capplyCash : applycashlist) {
				CuserInfo coach = dataDao.getObjectById(CuserInfo.class, capplyCash.getUserid());
				if (coach != null) {
					capplyCash.setRealname(coach.getRealname());
					capplyCash.setPhone(coach.getPhone());
				}
			}
		}
		String counthql = " select count(*) " + cuserhql.toString();
		long total = (Long) dataDao.getFirstObjectViaParam(counthql, null);
		return new QueryResult<BalanceCoachInfo>(applycashlist, total);
	}

	@SuppressWarnings("unchecked")
	@Override
	public QueryResult<ModelsInfo> getModellist(Integer pageIndex, int pagesize) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from ModelsInfo ");
		List<ModelsInfo> modelsInfo = (List<ModelsInfo>) dataDao.pageQueryViaParam(cuserhql.toString(), pagesize, pageIndex, null);
		String counthql = " select count(*) " + cuserhql.toString();
		long total = (Long) dataDao.getFirstObjectViaParam(counthql, null);
		return new QueryResult<ModelsInfo>(modelsInfo, total);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ModelsInfo> getModelList() {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from ModelsInfo ");
		List<ModelsInfo> modelsInfo = (List<ModelsInfo>) dataDao.getObjectsViaParam(cuserhql.toString(), null);
		return modelsInfo;
	}

	@Override
	public ModelsInfo getModelsInfoBymodelname(String modelname) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from ModelsInfo where modelname = :modelname ");
		String[] params = { "modelname" };
		ModelsInfo modelinfo = (ModelsInfo) dataDao.getFirstObjectViaParam(cuserhql.toString(), params, modelname);
		return modelinfo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CoachLevelInfo> getLevellist() {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from CoachLevelInfo ");
		List<CoachLevelInfo> coachLevelInfo = (List<CoachLevelInfo>) dataDao.getObjectsViaParam(cuserhql.toString(), null);
		return coachLevelInfo;
	}

	@Override
	public CoachLevelInfo getLevelInfoBylevelname(String levelname) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from CoachLevelInfo where levelname = :levelname ");
		String[] params = { "levelname" };
		CoachLevelInfo coachLevelInfo = (CoachLevelInfo) dataDao.getFirstObjectViaParam(cuserhql.toString(), params, levelname);
		return coachLevelInfo;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void delObject(Object object) {
		dataDao.deleteObject(object);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void addObject(Object object) {
		dataDao.addObject(object);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void updateObject(Object object) {
		dataDao.updateObject(object);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ComplaintSetInfo> getComplaintSetInfolist() {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from ComplaintSetInfo ");
		List<ComplaintSetInfo> complaintset = (List<ComplaintSetInfo>) dataDao.getObjectsViaParam(cuserhql.toString(), null);
		return complaintset;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ComplaintSetInfo> getComplaintSetByType(int searchtype) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from ComplaintSetInfo ");
		if (searchtype == 1) {
			cuserhql.append(" where type = 1 ");
			List<ComplaintSetInfo> complaintset = (List<ComplaintSetInfo>) dataDao.getObjectsViaParam(cuserhql.toString(), null);
			return complaintset;
		} else if (searchtype == 2) {
			cuserhql.append(" where type = 2 ");
			List<ComplaintSetInfo> complaintset = (List<ComplaintSetInfo>) dataDao.getObjectsViaParam(cuserhql.toString(), null);
			return complaintset;
		}
		return null;
	}

	@Override
	public ComplaintSetInfo getComplaintSetInfoByid(int setid) {
		ComplaintSetInfo complaintset = dataDao.getObjectById(ComplaintSetInfo.class, setid);
		return complaintset;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CsubjectInfo> getSubjectInfo() {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from CsubjectInfo ");
		List<CsubjectInfo> subjectlist = (List<CsubjectInfo>) dataDao.getObjectsViaParam(cuserhql.toString(), null);
		return subjectlist;
	}

	@Override
	public CsubjectInfo getSubjectById(int subjectid) {
		CsubjectInfo subjectInfo = dataDao.getObjectById(CsubjectInfo.class, subjectid);
		return subjectInfo;
	}

	@Override
	public CsubjectInfo getSubjectByName(String subjectname) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from CsubjectInfo where subjectname = :subjectname ");
		String[] params = { "subjectname" };
		CsubjectInfo subjectInfo = (CsubjectInfo) dataDao.getFirstObjectViaParam(cuserhql.toString(), params, subjectname);
		return subjectInfo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DriveSchoolInfo> getDriveSchoolInfo() {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from DriveSchoolInfo ");
		List<DriveSchoolInfo> driveSchoolInfo = (List<DriveSchoolInfo>) dataDao.getObjectsViaParam(cuserhql.toString(), null);
		return driveSchoolInfo;
	}

	@Override
	public SystemSetInfo getSystemSetInfo() {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from SystemSetInfo ");
		SystemSetInfo systemSetInfo = (SystemSetInfo) dataDao.getFirstObjectViaParam(cuserhql.toString(), null);
		return systemSetInfo;
	}

	@Override
	public SystemSetInfo getSystemSetInfoByid(int dateid) {
		SystemSetInfo systemSetInfo = dataDao.getObjectById(SystemSetInfo.class, dateid);
		return systemSetInfo;
	}

	@Override
	@Transactional
	public List<CuserInfo> getCoachlist() {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from CuserInfo ");
		@SuppressWarnings("unchecked")
		List<CuserInfo> cuserlist = (List<CuserInfo>) dataDao.getObjectsViaParam(cuserhql.toString(), null);
		return cuserlist;
	}

	@Override
	@Transactional
	public List<SuserInfo> getStudentlist() {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from SuserInfo ");
		@SuppressWarnings("unchecked")
		List<SuserInfo> suserlist = (List<SuserInfo>) dataDao.getObjectsViaParam(cuserhql.toString(), null);
		return suserlist;
	}

	@Override
	public AdminInfo getAdminInfo(String loginname) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from AdminInfo where login_account = :login_account");
		String[] params = { "login_account" };
		AdminInfo adminInfo = (AdminInfo) dataDao.getFirstObjectViaParam(cuserhql.toString(), params, loginname);
		return adminInfo;
	}

	@Override
	public CApplyCashInfo getApplyById(int applyid) {
		return dataDao.getObjectById(CApplyCashInfo.class, applyid);
	}

	@Override
	@Transactional
	public UserPushInfo getUserPushInfo(int userid, int usertype) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from UserPushInfo where userid = :userid and usertype = :usertype");
		String[] params = { "userid", "usertype" };
		UserPushInfo userPushInfo = (UserPushInfo) dataDao.getFirstObjectViaParam(cuserhql.toString(), params, userid, usertype);
		return userPushInfo;
	}

	@Override
	public DriveSchoolInfo getDriveSchoolInfoByid(int id) {
		DriveSchoolInfo driveSchoolInfo = dataDao.getObjectById(DriveSchoolInfo.class, id);
		return driveSchoolInfo;
	}

	@Override
	public CaddAddressInfo getCurrentAddressBycoachid(String coachid) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from CaddAddressInfo where coachid =:coachid and iscurrent = 1");
		String[] params = { "coachid" };
		CaddAddressInfo caddAddressInfo = (CaddAddressInfo) dataDao.getFirstObjectViaParam(cuserhql.toString(), params, CommonUtils.parseInt(coachid, 0));
		return caddAddressInfo;
	}

	@Override
	public CuserInfo getCuserById_cardnum(String id_cardnum) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from CuserInfo where id_cardnum =:id_cardnum ");
		String[] params = { "id_cardnum" };
		CuserInfo coach = (CuserInfo) dataDao.getFirstObjectViaParam(cuserhql.toString(), params, id_cardnum);
		return coach;
	}

	@Override
	public CuserInfo getCuserByCoach_cardnum(String coach_cardnum) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from CuserInfo where coach_cardnum =:coach_cardnum ");
		String[] params = { "coach_cardnum" };
		CuserInfo coach = (CuserInfo) dataDao.getFirstObjectViaParam(cuserhql.toString(), params, coach_cardnum);
		return coach;
	}

	@Override
	public CuserInfo getCuserByCar_cardnum(String car_cardnum) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from CuserInfo where car_cardnum =:car_cardnum ");
		String[] params = { "car_cardnum" };
		CuserInfo aaa = (CuserInfo) dataDao.getFirstObjectViaParam(cuserhql.toString(), params, car_cardnum);
		return aaa;
	}

	@Override
	public CuserInfo getCuserByBrive_cardnum(String drive_cardnum) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from CuserInfo where drive_cardnum =:drive_cardnum ");
		String[] params = { "drive_cardnum" };
		CuserInfo coach = (CuserInfo) dataDao.getFirstObjectViaParam(cuserhql.toString(), params, drive_cardnum);
		return coach;
	}

	@Override
	public CuserInfo getCoachByid(int id) {
		CuserInfo coach = dataDao.getObjectById(CuserInfo.class, id);
		return coach;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TeachcarInfo> getTeachcarInfolist() {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from TeachcarInfo ");
		List<TeachcarInfo> teachcarInfolist = (List<TeachcarInfo>) dataDao.getObjectsViaParam(cuserhql.toString(), null);
		return teachcarInfolist;
	}

	@Override
	public TeachcarInfo getTeachcarInfoBymodelname(String modelname) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from TeachcarInfo where modelname = :modelname");
		String[] params = { "modelname" };
		TeachcarInfo teachcarInfo = (TeachcarInfo) dataDao.getFirstObjectViaParam(cuserhql.toString(), params, modelname);
		return teachcarInfo;
	}

	@Override
	public int getCoachAgeByid(int id) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append(" select year(now())-year(birthday) as age  from CuserInfo where coachid = :coachid");
		String[] params = { "coachid" };
		int count = (Integer) dataDao.getFirstObjectViaParam(cuserhql.toString(), params, id);
		return count;
	}

	@Override
	public CuserInfo getCoachByIdnum(String idnum) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from CuserInfo where id_cardnum = :id_cardnum ");
		String[] params = { "id_cardnum" };
		CuserInfo cuser = (CuserInfo) dataDao.getFirstObjectViaParam(cuserhql.toString(), params, idnum);
		return cuser;
	}

	// 充值
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public HashMap<String, Object> recharge(String coachid, String amount) {

		HashMap<String, Object> result = new HashMap<String, Object>();

		// 插入数据
		RechargeRecordInfo info = new RechargeRecordInfo();
		info.setAddtime(new Date());
		info.setAmount(BigDecimal.valueOf(CommonUtils.parseFloat(amount, 0f)));
		info.setType(1);
		info.setUserid(CommonUtils.parseInt(coachid, 0));
		dataDao.addObject(info);

		if ("1".equals(CommonUtils.getAliSet())) {
			result.put("partner", AlipayConfig.partner);
		} else {
			result.put("partner", AlipayConfig.partner_formal);
		}

		result.put("notify_url", CommonUtils.getWebRootUrl() + "alipay_callback");
		result.put("out_trade_no", info.getRechargeid());
		result.put("subject", "小巴教练充值：" + amount + "元");
		if ("1".equals(CommonUtils.getAliSet())) {
			result.put("seller_id", AlipayConfig.seller_id);
		} else {
			result.put("seller_id", AlipayConfig.seller_id_formal);
		}

		result.put("total_fee", amount);
		result.put("body", "小巴教练充值：" + amount + "元");
		if ("1".equals(CommonUtils.getAliSet())) {
			result.put("private_key", AlipayConfig.private_key);
		} else {
			result.put("private_key", AlipayConfig.private_key_formal);
		}

		return result;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void buySuccess(String out_trade_no, String buyer_id, String buyer_email) {
		RechargeRecordInfo info = dataDao.getObjectById(RechargeRecordInfo.class, CommonUtils.parseInt(out_trade_no, 0));
		if (info != null) {

			int type = info.getType();
			if (type == 1) {
				CuserInfo user = dataDao.getObjectById(CuserInfo.class, info.getUserid());
				if (user != null) {
					user.setMoney(user.getMoney().add(info.getAmount()));
					dataDao.updateObject(user);
					// 插入充值记录
					BalanceCoachInfo balance = new BalanceCoachInfo();
					balance.setAddtime(new Date());
					balance.setAmount(info.getAmount());
					balance.setUserid(user.getCoachid());
					balance.setType(3);
					balance.setAmount_out1(new BigDecimal(0));
					balance.setAmount_out2(new BigDecimal(0));
					dataDao.addObject(balance);
				}
			} else if (type == 2) {
				SuserInfo user = dataDao.getObjectById(SuserInfo.class, info.getUserid());
				if (user != null) {
					user.setMoney(user.getMoney().add(info.getAmount()));
					dataDao.updateObject(user);
					// 插入充值记录
					BalanceStudentInfo balance = new BalanceStudentInfo();
					balance.setAddtime(new Date());
					balance.setAmount(info.getAmount());
					balance.setUserid(user.getStudentid());
					balance.setType(1);
					dataDao.addObject(balance);
				}
			}

			info.setUpdatetime(new Date());
			info.setBuyer_email(buyer_email);
			info.setBuyer_id(buyer_id);
			info.setState(1);
			dataDao.updateObject(info);

		}
	}

	@Override
	public HashMap<String, Object> getBalanceList(String coachid) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		CuserInfo user = dataDao.getObjectById(CuserInfo.class, CommonUtils.parseInt(coachid, 0));
		if (user != null) {
			result.put("balance", user.getMoney());
			result.put("fmoney", user.getFmoney());
			result.put("gmoney", user.getGmoney());
			String hql = "from BalanceCoachInfo where userid =:userid order by addtime desc";
			String[] params = { "userid" };
			List<BalanceCoachInfo> list = (List<BalanceCoachInfo>) dataDao.getObjectsViaParam(hql, params, CommonUtils.parseInt(coachid, 0));
			if (list != null) {
				result.put("recordlist", list);
			}

		} else {
			result.put("code", 2);
			result.put("message", "用户不存在");
		}
		return result;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public CuserInfo registerUser(String phone, String token) {
		// 首先查询出订单相关的几个时间配置
		String hqlset = "from SystemSetInfo where 1 = 1";
		SystemSetInfo setInfo = (SystemSetInfo) dataDao.getFirstObjectViaParam(hqlset, null);
		BigDecimal C_register_money = new BigDecimal(0);
		BigDecimal C_register_gmoney = new BigDecimal(0);

		if (setInfo != null) {
			if (setInfo.getC_register_money() != null && setInfo.getC_register_money().doubleValue() != 0)
				C_register_money = setInfo.getC_register_money();
			if (setInfo.getC_register_gmoney() != null && setInfo.getC_register_gmoney().doubleValue() != 0)
				C_register_gmoney = setInfo.getC_register_gmoney();
		}

		CuserInfo cuser = new CuserInfo();
		cuser.setPhone(phone); // 设置电话
		cuser.setPhone(phone);
		String code="C"+CommonUtils.getInviteCode(phone);
		cuser.setInvitecode(code);
		cuser.setTelphone(phone);
		cuser.setPassword("");
		cuser.setState(Constant.CUSER_UNCOMPLETE); // 设置状态
		cuser.setAddtime(new Date()); // 设置添加时间
		cuser.setNewtasknoti(0); // 设置消息默认提醒
		cuser.setCancancel(0); // 设置是否可以取消订单
		cuser.setFmoney(new BigDecimal(0)); // 冻结金额
		cuser.setMoney(C_register_money); // 金额
		cuser.setGmoney(C_register_gmoney); // 保证金
		cuser.setPrice(new BigDecimal(0)); // 教练单价
		cuser.setToken(token);
		cuser.setToken_time(new Date());
		dataDao.addObject(cuser);
		return cuser;
	}

	// 返回：1.验证码正常 0：验证码不存在或者错误 2:验证码已经过期
	@Override
	public int checkVerCode(String phone, String vcode) {
		String hql = "from VerifyCodeInfo where phone = :phone and code = :code and totype = 1";
		String[] params = { "phone", "code" };
		VerifyCodeInfo verCode = (VerifyCodeInfo) dataDao.getFirstObjectViaParam(hql, params, phone, vcode);

		if (verCode == null) {
			return 0;
		} else {
			String hqlset = "from SystemSetInfo where 1=1";
			SystemSetInfo setInfo = (SystemSetInfo) dataDao.getFirstObjectViaParam(hqlset, null);
			int login_vcode_time = 15;
			if (setInfo != null && setInfo.getLogin_vcode_time() != null && setInfo.getLogin_vcode_time() > 0) {
				login_vcode_time = setInfo.getLogin_vcode_time();
			}

			Calendar now = Calendar.getInstance();

			Calendar time = Calendar.getInstance();
			time.setTime(verCode.getAddtime());
			time.add(Calendar.DAY_OF_YEAR, login_vcode_time);

			if (time.before(now)) {
				return 2;
			} else {
				return 1;
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void coachCancelOrder(CuserInfo cuser) {
		if (cuser != null) {
			StringBuffer cancelorderhql = new StringBuffer();
			cancelorderhql.append(" from OrderInfo o where o.coachid = :coachid and o.start_time < :systemdate ");
			cancelorderhql.append(" and (select count(*) from OrderRecordInfo where orderid = o.orderid and ( operation = 1 or operation = 2)  ) = 0 and studentstate = 0 ");
			String[] params = { "coachid", "systemdate" };
			List<OrderInfo> cancelorderlist = (List<OrderInfo>) dataDao.getObjectsViaParam(cancelorderhql.toString(), params, cuser.getCoachid(), new Date());
			for (OrderInfo cancelorder : cancelorderlist) {
				cancelorder.setStudentstate(4);
				cancelorder.setCoachstate(4);
				dataDao.updateObject(cancelorder);
				UserPushInfo userPushInfo = (UserPushInfo) dataDao.getObjectsViaParam(" from UserPushInfo where userid = :studentid and usertype = 2", new String[] { "studentid" },
						cancelorder.getStudentid());
				if (userPushInfo != null && userPushInfo.getType() == 1) {
					ApplePushUtil.sendpush(userPushInfo.getDevicetoken(),
							"{\"aps\":{\"alert\":\"" + "由于" + cuser.getRealname() + "教练离职，您" + cancelorder.getStart_time() + "~" + cancelorder.getEnd_time() + "的订单已经被取消"
									+ "\",\"sound\":\"default\"} }", 1, 2);
				}
			}
		}
	}

	@Override
	public QueryResult<DriveSchoolInfo> getDriveSchoolInfoByPage(int pageIndex, int pageSize) {
		String hql = " from DriveSchoolInfo";
		List<DriveSchoolInfo> schoollist = (List<DriveSchoolInfo>) dataDao.pageQueryViaParam(hql + " order by addtime ", pageSize, pageIndex, null);
		String counthql = " select count(*) " + hql;
		long count = (Long) dataDao.getFirstObjectViaParam(counthql, null);
		QueryResult<DriveSchoolInfo> result = new QueryResult<DriveSchoolInfo>(schoollist, count);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void addSchoolBalance(SchoolBalance entity) {
		dataDao.addObject(entity);
	}

	@Override
	public CsubjectInfo getFirstSubject() {
		String hql = "from CsubjectInfo where 1=1";
		return (CsubjectInfo) dataDao.getFirstObjectViaParam(hql, null);
	}

	@Override
	public int getCoachAllCouponTime(int coachid) {
		int hour = 0;
		String hql = "from CouponCoach where coachid = :coachid and state = 1";
		String[] params = { "coachid" };
		List<CouponCoach> list = (List<CouponCoach>) dataDao.getObjectsViaParam(hql, params, coachid);
		if (list != null) {
			for (CouponCoach object : list) {
				hour += object.getValue();
			}
		}
		return hour;
	}

	@Override
	public TeachcarInfo getTeachcarInfoByID(int teachCarId) {
		return dataDao.getObjectById(TeachcarInfo.class, teachCarId);
	}

	@Override
	public List<DriveSchoolInfo> getDriveSchoolListById(int schoolid) {
		String hql = " from DriveSchoolInfo where schoolid =:schoolid ";
		String[] params = { "schoolid" };
		List<DriveSchoolInfo> schoollist = (List<DriveSchoolInfo>) dataDao.getObjectsViaParam(hql, params, schoolid);
		return schoollist;
	}

	@Override
	public List<DriveSchoolInfo> getDriveSchoolList(String keyword) {
		String hql = "from DriveSchoolInfo";
		if (keyword != null) {
			hql += " where name like '%" + keyword + "%'";
		}
		List<DriveSchoolInfo> schoollist = (List<DriveSchoolInfo>) dataDao.getObjectsViaParam(hql, null);
		return schoollist;
	}

	@Override
	public QueryResult<BalanceCoachInfo> getRechargeRecordList(int pageIndex, int pagesize) {
		
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from BalanceCoachInfo where type = 3");
		@SuppressWarnings("unchecked")
		List<BalanceCoachInfo> balancecoachlist = (List<BalanceCoachInfo>) dataDao.pageQueryViaParam(cuserhql.toString(), pagesize, pageIndex, null);
		if (balancecoachlist != null && balancecoachlist.size() > 0) {
			for (BalanceCoachInfo balanceCoach : balancecoachlist) {
				CuserInfo coach = dataDao.getObjectById(CuserInfo.class, balanceCoach.getUserid());
				if (coach != null) {
					balanceCoach.setRealname(coach.getRealname());
					balanceCoach.setPhone(coach.getPhone());
				}
			}
		}
		String counthql = " select count(*) " + cuserhql.toString();
		long total = (Long) dataDao.getFirstObjectViaParam(counthql, null);
		return new QueryResult<BalanceCoachInfo>(balancecoachlist, total);

	}

	@Override
	public QueryResult<BalanceCoachInfo> searchCoachRecharge(String searchname, String searchphone, String amount, String inputamount, String minsdate, String maxsdate, int pageIndex, int pagesize) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from BalanceCoachInfo where type = 3");
		if (!CommonUtils.isEmptyString(searchname)) {
			cuserhql.append(" and userid in (select coachid from CuserInfo where realname like '%" + searchname + "%')");
		}
		if (!CommonUtils.isEmptyString(searchphone)) {
			cuserhql.append(" and userid in (select coachid from CuserInfo where phone like '%" + searchphone + "%')");
		}
		if (CommonUtils.parseFloat(inputamount, 0) != 0) {
			if (CommonUtils.parseInt(amount, -1) == 0) {
				cuserhql.append("and amount >" + inputamount);
			} else if (CommonUtils.parseInt(amount, -1) == 1) {
				cuserhql.append("and amount =" + inputamount);
			} else {
				cuserhql.append("and amount <" + inputamount);
			}
		}
		if (!CommonUtils.isEmptyString(minsdate)) {
			cuserhql.append("and addtime >'" + minsdate + "'");
		}
		if (!CommonUtils.isEmptyString(maxsdate)) {
			Date enddate = CommonUtils.getDateFormat(maxsdate, "yyyy-MM-dd");
			enddate.setHours(23);
			enddate.setMinutes(59);
			enddate.setSeconds(59);
			String endmaxstime = CommonUtils.getTimeFormat(enddate, "yyyy-MM-dd HH:mm:ss");
			cuserhql.append("and addtime <'" + endmaxstime + "'");
		}
		
		List<BalanceCoachInfo> applycashlist = (List<BalanceCoachInfo>) dataDao.pageQueryViaParam(cuserhql.toString(), pagesize, pageIndex, null);
		if (applycashlist != null && applycashlist.size() > 0) {
			for (BalanceCoachInfo capplyCash : applycashlist) {
				CuserInfo student = dataDao.getObjectById(CuserInfo.class, capplyCash.getUserid());
				if (student != null) {
					capplyCash.setRealname(student.getRealname());
					capplyCash.setPhone(student.getPhone());
				}
			}
		}
		String counthql = " select count(*) " + cuserhql.toString();
		long total = (Long) dataDao.getFirstObjectViaParam(counthql, null);
		return new QueryResult<BalanceCoachInfo>(applycashlist, total);
	}

	

	@Override
	public Long getOrderSum(int coachid) {
		String hql="select count(*) from OrderInfo where coachid=:coachid";
		String p[]={"coachid"};
		Long o=(Long) dataDao.getFirstObjectViaParam(hql, p, coachid);
		return o;
	}

	@Override
	public Long getOrderOver(int coachid) {
		String hql="select count(*) from OrderInfo where coachid=:coachid and over_time is not null";
		String p[]={"coachid"};
		Long o=(Long) dataDao.getFirstObjectViaParam(hql, p, coachid);
		return o;
	}

	@Override
	public Long getOrderCancel(int coachid) {
		String hql="select count(*) from OrderInfo where coachid=:coachid and coachstate=4";
		String p[]={"coachid"};
		Long o=(Long) dataDao.getFirstObjectViaParam(hql, p, coachid);
		return o;
	}
	
	@Override
	public QueryResult<CuserInfo> getCuserReport(String provinceid,String cityid,String areaid,String drive_school,String startdate,String enddate,Integer pageIndex, int pagesize) {
		//dataDao.callCoachReport();
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from CuserInfo where 1=1 ");
		if(!CommonUtils.isEmptyString(startdate) && ! CommonUtils.isEmptyString(enddate)){
			cuserhql.append(" and coachid in (select coachid from OrderInfo where date >"+startdate+" and date<"+enddate+")  ");
		}
		if(!CommonUtils.isEmptyString(provinceid)){
			cuserhql.append(" and provinceid="+provinceid);
		}
		if(!CommonUtils.isEmptyString(cityid)){
			cuserhql.append(" and cityid="+cityid);
		}
		if(!CommonUtils.isEmptyString(areaid)){
			cuserhql.append(" and areaid="+areaid);
		}
		if(!CommonUtils.isEmptyString(drive_school)){
			cuserhql.append(" and drive_school="+drive_school);
		}
		List<CuserInfo> cuserInfolist = (List<CuserInfo>) dataDao.pageQueryViaParam(cuserhql.toString() + " order by coachid desc ", pagesize, pageIndex, null);
		String counthql = " select count(*) " + cuserhql.toString();
		long total = (Long) dataDao.getFirstObjectViaParam(counthql, null);
		return new QueryResult<CuserInfo>(cuserInfolist, total);
	}

}
