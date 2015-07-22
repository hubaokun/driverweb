package com.daoshun.guangda.serviceImpl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alipay.config.AlipayConfig;
import com.daoshun.common.CommonUtils;
import com.daoshun.common.QueryResult;
import com.daoshun.guangda.pojo.BalanceStudentInfo;
import com.daoshun.guangda.pojo.CoachStudentInfo;
import com.daoshun.guangda.pojo.CouponInfo;
import com.daoshun.guangda.pojo.CouponRecord;
import com.daoshun.guangda.pojo.CuserInfo;
import com.daoshun.guangda.pojo.RechargeRecordInfo;
import com.daoshun.guangda.pojo.StudentApplyInfo;
import com.daoshun.guangda.pojo.StudentCheckInfo;
import com.daoshun.guangda.pojo.SuserInfo;
import com.daoshun.guangda.pojo.SystemSetInfo;
import com.daoshun.guangda.pojo.VerifyCodeInfo;
import com.daoshun.guangda.service.ISUserService;

@Service("suserService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class SUserServiceImpl extends BaseServiceImpl implements ISUserService {

	@Override
	public SuserInfo getUserByPhone(String phone) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append(" from SuserInfo where phone = :phone");
		String[] params = { "phone" };
		SuserInfo user = (SuserInfo) dataDao.getFirstObjectViaParam(cuserhql.toString(), params, phone);
		if (user != null) {
			user.setAvatarurl(getFilePathById(user.getAvatar()));
			user.setStudent_cardpicf_url(getFilePathById(user.getStudent_cardpicf()));
			user.setStudent_cardpicb_url(getFilePathById(user.getStudent_cardpicb()));
			user.setId_cardpicf_url(getFilePathById(user.getId_cardpicf()));
			user.setId_cardpicb_url(getFilePathById(user.getId_cardpicb()));
			user.setCcheck_idcardpicf_url(getFilePathById(user.getCcheck_idcardpicf()));
			user.setCcheck_idcardpicb_url(getFilePathById(user.getCcheck_idcardpicb()));
			user.setCcheck_pic_url(getFilePathById(user.getCcheck_pic()));
		}
		return user;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void addSuserInfo(SuserInfo user) {
		dataDao.addObject(user);
	}

	@Override
	public SuserInfo getUserByqq(String qq_openid) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append(" from SuserInfo where qq_openid = :qq_openid ");
		String[] params = { "qq_openid" };
		SuserInfo user = (SuserInfo) dataDao.getFirstObjectViaParam(cuserhql.toString(), params, qq_openid);
		if (user != null) {
			user.setAvatarurl(getFilePathById(user.getAvatar()));
			user.setStudent_cardpicf_url(getFilePathById(user.getStudent_cardpicf()));
			user.setStudent_cardpicb_url(getFilePathById(user.getStudent_cardpicb()));
			user.setId_cardpicf_url(getFilePathById(user.getId_cardpicf()));
			user.setId_cardpicb_url(getFilePathById(user.getId_cardpicb()));
			user.setCcheck_idcardpicf_url(getFilePathById(user.getCcheck_idcardpicf()));
			user.setCcheck_idcardpicb_url(getFilePathById(user.getCcheck_idcardpicb()));
			user.setCcheck_pic_url(getFilePathById(user.getCcheck_pic()));
		}
		return user;
	}

	@Override
	public SuserInfo getUserBywx(String wx_openid) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append(" from SuserInfo where wx_openid = :wx_openid ");
		String[] params = { "wx_openid" };
		SuserInfo user = (SuserInfo) dataDao.getFirstObjectViaParam(cuserhql.toString(), params, wx_openid);
		if (user != null) {
			user.setAvatarurl(getFilePathById(user.getAvatar()));
			user.setStudent_cardpicf_url(getFilePathById(user.getStudent_cardpicf()));
			user.setStudent_cardpicb_url(getFilePathById(user.getStudent_cardpicb()));
			user.setId_cardpicf_url(getFilePathById(user.getId_cardpicf()));
			user.setId_cardpicb_url(getFilePathById(user.getId_cardpicb()));
			user.setCcheck_idcardpicf_url(getFilePathById(user.getCcheck_idcardpicf()));
			user.setCcheck_idcardpicb_url(getFilePathById(user.getCcheck_idcardpicb()));
			user.setCcheck_pic_url(getFilePathById(user.getCcheck_pic()));
		}
		return user;
	}

	@Override
	public SuserInfo getUserBywb(String wb_openid) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append(" from SuserInfo where wb_openid = :wb_openid ");
		String[] params = { "wb_openid" };
		SuserInfo user = (SuserInfo) dataDao.getFirstObjectViaParam(cuserhql.toString(), params, wb_openid);
		if (user != null) {
			user.setAvatarurl(getFilePathById(user.getAvatar()));
			user.setStudent_cardpicf_url(getFilePathById(user.getStudent_cardpicf()));
			user.setStudent_cardpicb_url(getFilePathById(user.getStudent_cardpicb()));
			user.setId_cardpicf_url(getFilePathById(user.getId_cardpicf()));
			user.setId_cardpicb_url(getFilePathById(user.getId_cardpicb()));
			user.setCcheck_idcardpicf_url(getFilePathById(user.getCcheck_idcardpicf()));
			user.setCcheck_idcardpicb_url(getFilePathById(user.getCcheck_idcardpicb()));
			user.setCcheck_pic_url(getFilePathById(user.getCcheck_pic()));
		}
		return user;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void updateUserInfo(SuserInfo user) {
		dataDao.updateObject(user);
	}

	@Override
	public SuserInfo getUserById(String studentid) {
		SuserInfo suserInfo = dataDao.getObjectById(SuserInfo.class, CommonUtils.parseInt(studentid, 0));
		if (suserInfo != null) {
			suserInfo.setAvatarurl(getFilePathById(suserInfo.getAvatar()));
			suserInfo.setStudent_cardpicb_url(getFilePathById(suserInfo.getStudent_cardpicb()));
			suserInfo.setStudent_cardpicf_url(getFilePathById(suserInfo.getStudent_cardpicf()));
			suserInfo.setId_cardpicb_url(getFilePathById(suserInfo.getId_cardpicb()));
			suserInfo.setId_cardpicf_url(getFilePathById(suserInfo.getId_cardpicf()));
			suserInfo.setCcheck_idcardpicb_url(getFilePathById(suserInfo.getCcheck_idcardpicb()));
			suserInfo.setCcheck_idcardpicf_url(getFilePathById(suserInfo.getCcheck_idcardpicf()));
			suserInfo.setCcheck_pic_url(getFilePathById(suserInfo.getCcheck_pic()));
			dataDao.updateObject(suserInfo);
		}

		return suserInfo;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public String changeAvatar(String studentid, String fileid) {
		SuserInfo student = dataDao.getObjectById(SuserInfo.class, CommonUtils.parseInt(studentid, 0));
		student.setAvatar(CommonUtils.parseInt(fileid, 0));
		dataDao.updateObject(student);
		return getFilePathById(CommonUtils.parseInt(fileid, 0));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BalanceStudentInfo> getMyBalanceList(String studentid) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append(" from BalanceStudentInfo where userid = :studentid order by addtime desc");
		String[] params = { "studentid" };

		List<BalanceStudentInfo> recordlist = (List<BalanceStudentInfo>) dataDao.getObjectsViaParam(cuserhql.toString(), params, CommonUtils.parseInt(studentid, 0));
		return recordlist;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void applyCash(String studentid, String count) {
		// 修改学员的余额信息
		SuserInfo student = dataDao.getObjectById(SuserInfo.class, CommonUtils.parseInt(studentid, 0));
		if (student != null) {
			student.setMoney(student.getMoney().subtract(new BigDecimal(CommonUtils.parseInt(count, 0))));
			student.setFmoney(student.getFmoney().add(new BigDecimal(CommonUtils.parseInt(count, 0))));
			dataDao.updateObject(student);

			// 添加提现记录
			StudentApplyInfo apply = new StudentApplyInfo();
			apply.setUserid(CommonUtils.parseInt(studentid, 0));
			apply.setAmount(new BigDecimal(CommonUtils.parseFloat(count, 0f)));
			apply.setState(0);
			apply.setAddtime(new Date());
			dataDao.addObject(apply);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void addVerifyCode(VerifyCodeInfo verifycode) {
		dataDao.addObject(verifycode);
	}

	@Override
	public VerifyCodeInfo getVerifiCationByPhone(String phone, String type) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append(" from VerifyCodeInfo where phone =:phone and totype =:totype");
		String[] params = { "phone", "totype" };
		VerifyCodeInfo verify = (VerifyCodeInfo) dataDao.getFirstObjectViaParam(cuserhql.toString(), params, phone, CommonUtils.parseInt(type, 0));
		return verify;
	}

	/********************************************************* ACTION *******************************************************************/
	@SuppressWarnings("unchecked")
	@Override
	public QueryResult<SuserInfo> getAllSuserInfos(Integer pageIndex, int pagesize) {
		StringBuffer suserhql = new StringBuffer();
		suserhql.append("from SuserInfo");
		List<SuserInfo> suserInfolist = (List<SuserInfo>) dataDao.pageQueryViaParam(suserhql.toString() + " order by id desc", pagesize, pageIndex, null);
		String counthql = " select count(*) " + suserhql.toString();
		long total = (Long) dataDao.getFirstObjectViaParam(counthql, null);
		return new QueryResult<SuserInfo>(suserInfolist, total);
	}

	@SuppressWarnings("unchecked")
	@Override
	public QueryResult<StudentApplyInfo> getStudentApplyList(Integer pageIndex, int pagesize) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from StudentApplyInfo where state = 0");
		List<StudentApplyInfo> studentApplyList = (List<StudentApplyInfo>) dataDao.pageQueryViaParam(cuserhql.toString(), pagesize, pageIndex, null);
		if (studentApplyList != null && studentApplyList.size() > 0) {
			for (StudentApplyInfo studentapply : studentApplyList) {
				SuserInfo student = dataDao.getObjectById(SuserInfo.class, studentapply.getUserid());
				if (student != null) {
					studentapply.setRealname(student.getRealname());
					studentapply.setPhone(student.getPhone());
					studentapply.setAlipay_account(student.getAlipay_account());
				}
			}
		}
		String counthql = " select count(*) " + cuserhql.toString();
		long total = (Long) dataDao.getFirstObjectViaParam(counthql, null);
		return new QueryResult<StudentApplyInfo>(studentApplyList, total);
	}

	@SuppressWarnings("unchecked")
	@Override
	public QueryResult<SuserInfo> getStudentByKeyword(String searchname, String searchphone, String minsdate, String maxsdate, Integer pageIndex, int pagesize) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from SuserInfo where 1 = 1 ");
		if (!CommonUtils.isEmptyString(searchname)) {
			cuserhql.append(" and realname like '%" + searchname + "%'");
		}
		if (!CommonUtils.isEmptyString(searchphone)) {
			cuserhql.append(" and phone like '%" + searchphone + "%'");
		}
		if (!CommonUtils.isEmptyString(maxsdate)) {
			Date newmaxsdate = CommonUtils.getDateFormat(maxsdate, "yyyy-MM-dd");
			Calendar latertime = Calendar.getInstance();
			latertime.setTime(newmaxsdate);
			latertime.set(Calendar.HOUR_OF_DAY, 23);
			latertime.set(Calendar.MINUTE, 59);
			latertime.set(Calendar.SECOND, 59);
			Date timelater = latertime.getTime();
			String newtimelater = CommonUtils.getTimeFormat(timelater, "yyyy-MM-dd");
			cuserhql.append(" and addtime <= '" + newtimelater + "'");
		}
		if (!CommonUtils.isEmptyString(minsdate)) {
			Date newminsdate = CommonUtils.getDateFormat(minsdate, "yyyy-MM-dd");
			Calendar nowtime = Calendar.getInstance();
			nowtime.setTime(newminsdate);
			nowtime.set(Calendar.HOUR_OF_DAY, 0);
			nowtime.set(Calendar.MINUTE, 0);
			nowtime.set(Calendar.SECOND, 0);
			Date starttime = nowtime.getTime();
			String newtoday = CommonUtils.getTimeFormat(starttime, "yyyy-MM-dd");
			cuserhql.append(" and addtime >= '" + newtoday + "'");
		}
		List<SuserInfo> suserInfolist = (List<SuserInfo>) dataDao.pageQueryViaParam(cuserhql.toString() + " order by id desc", pagesize, pageIndex, null);
		String counthql = " select count(*) " + cuserhql.toString();
		long total = (Long) dataDao.getFirstObjectViaParam(counthql, null);
		return new QueryResult<SuserInfo>(suserInfolist, total);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void applyCheckPass(int coachid) {
		StudentApplyInfo studentApply = dataDao.getObjectById(StudentApplyInfo.class, coachid);
		if (studentApply != null) {
			studentApply.setState(1);
			studentApply.setUpdatetime(new Date());
			dataDao.updateObject(studentApply);
			SuserInfo student = dataDao.getObjectById(SuserInfo.class, studentApply.getUserid());
			if (student != null) {
				student.setFmoney(student.getFmoney().subtract(studentApply.getAmount()));
				dataDao.updateObject(student);
			}
			BalanceStudentInfo balancestudent = new BalanceStudentInfo();
			balancestudent.setType(2);
			balancestudent.setAddtime(new Date());
			balancestudent.setAmount(studentApply.getAmount());
			balancestudent.setUserid(studentApply.getUserid());
			dataDao.addObject(balancestudent);
		}
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public QueryResult<StudentApplyInfo> getCoachApplyBySearch(String searchname, String searchphone, String amount, String inputamount, Integer state, String minsdate, String maxsdate,
			Integer pageIndex, int pagesize) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from StudentApplyInfo where 1=1");
		if (!CommonUtils.isEmptyString(searchname)) {
			cuserhql.append(" and userid in (select studentid from SuserInfo where realname like '%" + searchname + "%')");
		}
		if (!CommonUtils.isEmptyString(searchphone)) {
			cuserhql.append(" and userid in (select studentid from SuserInfo where phone like '%" + searchphone + "%')");
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
		if (state != null) {
			if (state == 1) {
				cuserhql.append(" and state = 0");
			}
			if (state == 2) {
				cuserhql.append(" and state = 1");
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
		List<StudentApplyInfo> applycashlist = (List<StudentApplyInfo>) dataDao.pageQueryViaParam(cuserhql.toString(), pagesize, pageIndex, null);
		if (applycashlist != null && applycashlist.size() > 0) {
			for (StudentApplyInfo capplyCash : applycashlist) {
				SuserInfo student = dataDao.getObjectById(SuserInfo.class, capplyCash.getUserid());
				if (student != null) {
					capplyCash.setRealname(student.getRealname());
					capplyCash.setPhone(student.getPhone());
				}
			}
		}
		String counthql = " select count(*) " + cuserhql.toString();
		long total = (Long) dataDao.getFirstObjectViaParam(counthql, null);
		return new QueryResult<StudentApplyInfo>(applycashlist, total);
	}

	@SuppressWarnings("unchecked")
	@Override
	public QueryResult<BalanceStudentInfo> getApplyRecordList(Integer pageIndex, int pagesize) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from BalanceStudentInfo where type = 2");
		List<BalanceStudentInfo> balancecoachlist = (List<BalanceStudentInfo>) dataDao.pageQueryViaParam(cuserhql.toString(), pagesize, pageIndex, null);
		if (balancecoachlist != null && balancecoachlist.size() > 0) {
			for (BalanceStudentInfo balanceCoach : balancecoachlist) {
				SuserInfo coach = dataDao.getObjectById(SuserInfo.class, balanceCoach.getUserid());
				if (coach != null) {
					balanceCoach.setRealname(coach.getRealname());
					balanceCoach.setPhone(coach.getPhone());
				}
			}
		}
		String counthql = " select count(*) " + cuserhql.toString();
		long total = (Long) dataDao.getFirstObjectViaParam(counthql, null);
		return new QueryResult<BalanceStudentInfo>(balancecoachlist, total);
	}

	@Override
	public QueryResult<BalanceStudentInfo> getRechargeRecordList(Integer pageIndex, int pagesize) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from BalanceStudentInfo where type = 1");
		@SuppressWarnings("unchecked")
		List<BalanceStudentInfo> balancecoachlist = (List<BalanceStudentInfo>) dataDao.pageQueryViaParam(cuserhql.toString(), pagesize, pageIndex, null);
		if (balancecoachlist != null && balancecoachlist.size() > 0) {
			for (BalanceStudentInfo balanceCoach : balancecoachlist) {
				SuserInfo coach = dataDao.getObjectById(SuserInfo.class, balanceCoach.getUserid());
				if (coach != null) {
					balanceCoach.setRealname(coach.getRealname());
					balanceCoach.setPhone(coach.getPhone());
				}
			}
		}
		String counthql = " select count(*) " + cuserhql.toString();
		long total = (Long) dataDao.getFirstObjectViaParam(counthql, null);
		return new QueryResult<BalanceStudentInfo>(balancecoachlist, total);
	}

	@Override
	public CuserInfo getCoachByPhone(String phone) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append(" from CuserInfo where phone = :phone");
		String[] params = { "phone" };
		CuserInfo coach = (CuserInfo) dataDao.getFirstObjectViaParam(cuserhql.toString(), params, phone);
		return coach;
	}

	@Override
	public StudentApplyInfo getApplyById(int applyid) {
		return dataDao.getObjectById(StudentApplyInfo.class, applyid);
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public QueryResult<BalanceStudentInfo> searchStudentBalance(String searchname, String searchphone, String amount, String inputamount, String minsdate, String maxsdate, Integer pageIndex,
			int pagesize) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from BalanceStudentInfo where type = 2");
		if (!CommonUtils.isEmptyString(searchname)) {
			cuserhql.append(" and userid in (select studentid from SuserInfo where realname like '%" + searchname + "%')");
		}
		if (!CommonUtils.isEmptyString(searchphone)) {
			cuserhql.append(" and userid in (select studentid from SuserInfo where phone like '%" + searchphone + "%')");
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
		List<BalanceStudentInfo> applycashlist = (List<BalanceStudentInfo>) dataDao.pageQueryViaParam(cuserhql.toString(), pagesize, pageIndex, null);
		if (applycashlist != null && applycashlist.size() > 0) {
			for (BalanceStudentInfo capplyCash : applycashlist) {
				SuserInfo student = dataDao.getObjectById(SuserInfo.class, capplyCash.getUserid());
				if (student != null) {
					capplyCash.setRealname(student.getRealname());
					capplyCash.setPhone(student.getPhone());
				}
			}
		}
		String counthql = " select count(*) " + cuserhql.toString();
		long total = (Long) dataDao.getFirstObjectViaParam(counthql, null);
		return new QueryResult<BalanceStudentInfo>(applycashlist, total);
	}

	@Override
	public SuserInfo getUserByStudent_cardnum(String student_cardnum) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append(" from SuserInfo where student_cardnum = :student_cardnum");
		String[] params = { "student_cardnum" };
		SuserInfo student = (SuserInfo) dataDao.getFirstObjectViaParam(cuserhql.toString(), params, student_cardnum);
		return student;
	}

	@Override
	public SuserInfo getUserById_cardnum(String id_cardnum) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append(" from SuserInfo where id_cardnum = :id_cardnum");
		String[] params = { "id_cardnum" };
		SuserInfo student = (SuserInfo) dataDao.getFirstObjectViaParam(cuserhql.toString(), params, id_cardnum);
		return student;
	}

	@Override
	public List<SuserInfo> getStudentList() {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append(" from SuserInfo");
		List<SuserInfo> studentlist = (List<SuserInfo>) dataDao.getObjectsViaParam(cuserhql.toString(), null);
		return studentlist;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public HashMap<String, Object> recharge(String coachid, String amount) {
		HashMap<String, Object> result = new HashMap<String, Object>();

		// 插入数据
		RechargeRecordInfo info = new RechargeRecordInfo();
		info.setAddtime(new Date());
		info.setAmount(new BigDecimal(CommonUtils.parseFloat(amount, 0.0f)));
		info.setType(2);
		info.setUserid(CommonUtils.parseInt(coachid, 0));
		dataDao.addObject(info);

		if ("1".equals(CommonUtils.getAliSet())) {
			result.put("partner", AlipayConfig.partner);
			result.put("seller_id", AlipayConfig.seller_id);
			result.put("private_key", AlipayConfig.private_key);
		} else {
			result.put("partner", AlipayConfig.partner_formal);
			result.put("seller_id", AlipayConfig.seller_id_formal);
			result.put("private_key", AlipayConfig.private_key_formal);
		}

		result.put("notify_url", CommonUtils.getWebRootUrl() + "alipay_callback");
		result.put("out_trade_no", info.getRechargeid());
		result.put("subject", "小巴教练充值：" + amount + "元");
		result.put("total_fee", amount);
		result.put("body", "小巴教练充值：" + amount + "元");

		return result;
	}

	@Override
	public QueryResult<BalanceStudentInfo> searchStudentRecharge(String searchname, String searchphone, String amount, String inputamount, String minsdate, String maxsdate, Integer pageIndex,
			int pagesize) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from BalanceStudentInfo where type = 1");
		if (!CommonUtils.isEmptyString(searchname)) {
			cuserhql.append(" and userid in (select studentid from SuserInfo where realname like '%" + searchname + "%')");
		}
		if (!CommonUtils.isEmptyString(searchphone)) {
			cuserhql.append(" and userid in (select studentid from SuserInfo where phone like '%" + searchphone + "%')");
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
		List<BalanceStudentInfo> applycashlist = (List<BalanceStudentInfo>) dataDao.pageQueryViaParam(cuserhql.toString(), pagesize, pageIndex, null);
		if (applycashlist != null && applycashlist.size() > 0) {
			for (BalanceStudentInfo capplyCash : applycashlist) {
				SuserInfo student = dataDao.getObjectById(SuserInfo.class, capplyCash.getUserid());
				if (student != null) {
					capplyCash.setRealname(student.getRealname());
					capplyCash.setPhone(student.getPhone());
				}
			}
		}
		String counthql = " select count(*) " + cuserhql.toString();
		long total = (Long) dataDao.getFirstObjectViaParam(counthql, null);
		return new QueryResult<BalanceStudentInfo>(applycashlist, total);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public SuserInfo registerUser(String phone, String token) {
		// 首先查询出订单相关的几个时间配置
		String hqlset = "from SystemSetInfo where 1 = 1";
		SystemSetInfo setInfo = (SystemSetInfo) dataDao.getFirstObjectViaParam(hqlset, null);
		BigDecimal S_register_money = new BigDecimal(0);

		if (setInfo != null) {
			if (setInfo.getS_register_money() != null && setInfo.getS_register_money().floatValue() != 0)
				S_register_money = setInfo.getS_register_money();
		}

		SuserInfo user1 = new SuserInfo();
		user1.setPhone(phone);
		String code="S"+CommonUtils.getInviteCode(phone);
		user1.setInvitecode(code);
		user1.setSecondphone(phone);
		user1.setMoney(S_register_money);
		user1.setAddtime(new Date());
		user1.setFmoney(new BigDecimal(0));
		user1.setToken(token);
		user1.setToken_time(new Date());
		dataDao.addObject(user1);
		return user1;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public int sendMessageCode(String phone, String type) {

		String hql = "from VerifyCodeInfo where phone = :phone and totype = :totype";
		String[] param = { "phone", "totype" };
		VerifyCodeInfo vCode = (VerifyCodeInfo) dataDao.getFirstObjectViaParam(hql, param, phone, CommonUtils.parseInt(type, 0));
		Random random = new Random();
		StringBuilder vercode = new StringBuilder();
		for (int i = 0; i < 6; i++) {
			vercode.append(random.nextInt(10));
		}

		String hqlset = "from SystemSetInfo where 1=1";
		SystemSetInfo setInfo = (SystemSetInfo) dataDao.getFirstObjectViaParam(hqlset, null);
		int login_vcode_time = 15;
		if (setInfo != null && setInfo.getLogin_vcode_time() != null && setInfo.getLogin_vcode_time() > 0) {
			login_vcode_time = setInfo.getLogin_vcode_time();
		}

		String content = content ="【小巴科技】"+ vercode.toString() + "(小巴学车验证码," + login_vcode_time + "天登陆有效)";
		System.out.print(content);
		String result = CommonUtils.sendSms(phone, content);

		if (vCode != null) {
			vCode.setAddtime(new Date());
			vCode.setCode(vercode.toString());
			dataDao.updateObject(vCode);
		} else {
			vCode = new VerifyCodeInfo();
			vCode.setAddtime(new Date());
			vCode.setCode(vercode.toString());
			vCode.setPhone(phone);
			vCode.setTotype(CommonUtils.parseInt(type, 0));
			dataDao.addObject(vCode);
		}

		return 1;
	}

	/**
	 * 验证码有效性判断
	 * 
	 * @author guok
	 * @return 1:验证码OK 0:验证码错误或者不存在 2:验证码已经过期
	 */
	@Override
	public int checkVerCode(String phone, String vcode) {
		String hql = "from VerifyCodeInfo where phone = :phone and code = :code and totype = 2";
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

	@Override
	public List<SuserInfo> getUserCount() {
		List<SuserInfo> suer = dataDao.getAllObject(SuserInfo.class);
		return suer;
	}

	@Override
	public int getSuserAgeByid(int id) {
		StringBuffer suserhql = new StringBuffer();
		suserhql.append(" select year(now())-year(birthday) as age  from SuserInfo where studentid = :studentid");
		String[] params = { "studentid" };
		int count = (Integer) dataDao.getFirstObjectViaParam(suserhql.toString(), params, id);
		return count;
	}

	@Override
	public StudentCheckInfo getcoachbycheck(int studentid) {
		StringBuffer suserhql = new StringBuffer();
		suserhql.append("from  StudentCheckInfo where studentid = :studentid ");
		String[] params = { "studentid" };
		StudentCheckInfo studentCheckInfo = (StudentCheckInfo) dataDao.getFirstObjectViaParam(suserhql.toString(), params, studentid);
		return studentCheckInfo;
	}

	@Override
	public QueryResult<CoachStudentInfo> getCoachStudentByKeyword(String coachPhone, String studentPhone, Integer pageIndex, int pagesize) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from CoachStudentInfo where 1 = 1");
		if (!CommonUtils.isEmptyString(coachPhone)) {
			cuserhql.append(" and coachid in (select coachid from CuserInfo where phone like '%" + coachPhone + "%')");
		}
		if (!CommonUtils.isEmptyString(studentPhone)) {
			cuserhql.append(" and studentid in (select studentid from SuserInfo where phone like '%" + studentPhone + "%')");
		}
		List<CoachStudentInfo> coachstudentlist = (List<CoachStudentInfo>) dataDao.pageQueryViaParam(cuserhql.toString(), pagesize, pageIndex, null);
		if (coachstudentlist != null && coachstudentlist.size() > 0) {
			for (CoachStudentInfo coachstudent : coachstudentlist) {
				SuserInfo student = dataDao.getObjectById(SuserInfo.class, coachstudent.getStudentid());
				CuserInfo coach = dataDao.getObjectById(CuserInfo.class, coachstudent.getCoachid());
				if (student != null) {
					coachstudent.setStudentPhone(student.getPhone());
					coachstudent.setStudentname(student.getRealname());
				}
				if (coach != null) {
					coachstudent.setCoachPhone(coach.getPhone());
					coachstudent.setCoachname(coach.getRealname());
				}
			}
		}
		String counthql = " select count(*) " + cuserhql.toString();
		long total = (Long) dataDao.getFirstObjectViaParam(counthql, null);
		return new QueryResult<CoachStudentInfo>(coachstudentlist, total);
	}

	@Override
	public CoachStudentInfo getCoachStudentByPhone(int coachid, int studentid) {
		StringBuffer suserhql = new StringBuffer();
		suserhql.append("from  CoachStudentInfo where coachid =:coachid and studentid =:studentid ");
		String[] params = { "coachid", "studentid" };
		CoachStudentInfo coachstudent = (CoachStudentInfo) dataDao.getFirstObjectViaParam(suserhql.toString(), params, coachid, studentid);
		return coachstudent;
	}
	

	
	@Override
	public int getCouponSum(int studentid) {
		StringBuffer suserhql = new StringBuffer();
		suserhql.append(" from CouponRecord where state=0 and  userid ="+studentid);			
		String counthql = " select count(*) " + suserhql.toString();
		long total = (Long) dataDao.getFirstObjectViaParam(counthql, null);
		return (int)total;		
//		return couponList;
	}
	

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void addCoachStudent(CoachStudentInfo coachstudent) {
		dataDao.addObject(coachstudent);
	}
}
