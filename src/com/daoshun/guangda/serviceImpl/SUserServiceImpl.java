package com.daoshun.guangda.serviceImpl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alipay.config.AlipayConfig;
import com.daoshun.common.CoinType;
import com.daoshun.common.CommonUtils;
import com.daoshun.common.QueryResult;
import com.daoshun.common.UserType;
import com.daoshun.guangda.pojo.AdminInfo;
import com.daoshun.guangda.pojo.AlipayCallBack;
import com.daoshun.guangda.pojo.BalanceStudentInfo;
import com.daoshun.guangda.pojo.CoachStudentInfo;
import com.daoshun.guangda.pojo.CoinAffiliation;
import com.daoshun.guangda.pojo.CoinRecordInfo;
import com.daoshun.guangda.pojo.CuserInfo;
import com.daoshun.guangda.pojo.DriveSchoolInfo;
import com.daoshun.guangda.pojo.OrderInfo;
import com.daoshun.guangda.pojo.RechargeRecordInfo;
import com.daoshun.guangda.pojo.StudentApplyInfo;
import com.daoshun.guangda.pojo.StudentCheckInfo;
import com.daoshun.guangda.pojo.SuserInfo;
import com.daoshun.guangda.pojo.SuserState;
import com.daoshun.guangda.pojo.SystemSetInfo;
import com.daoshun.guangda.pojo.VerifyCodeInfo;
import com.daoshun.guangda.service.ISUserService;
import com.weixin.service.IGetYouWanna;
import com.weixin.serviceImpl.GetYouWannaImpl;

@Service("suserService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class SUserServiceImpl extends BaseServiceImpl implements ISUserService {
	static Logger logger = Logger.getLogger(SUserServiceImpl.class.getName ()) ;
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

	@Override
	public SuserInfo getUserByInviteCode(String invitecode) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append(" from SuserInfo where invitecode = :invitecode");
		String[] params = { "invitecode" };
		SuserInfo user = (SuserInfo) dataDao.getFirstObjectViaParam(cuserhql.toString(), params, invitecode);
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
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void addSuserState(SuserState usersta){
		dataDao.addObject(usersta);
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

		String inviteCode="S"+CommonUtils.getInviteCode(user.getPhone());


		StringBuffer suserhql = new StringBuffer();
		suserhql.append(" update SuserInfo set inviteCode=null where inviteCode=:inviteCode");
		String[] params = { "inviteCode" };
		dataDao.updateObjectsViaParam(suserhql.toString(), params, inviteCode);


		dataDao.updateObject(user);
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void updateUserState(SuserState userstate) {
		dataDao.updateObject(userstate);
	}
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public SuserInfo getUserById(String studentid) {
		SuserInfo suserInfo = dataDao.getObjectById(SuserInfo.class, CommonUtils.parseInt(studentid, 0));
		if (suserInfo != null) {
			if(suserInfo.getAvatar()!=null){
				String avurl=getFilePathById(suserInfo.getAvatar());
				if(avurl!=null){
					suserInfo.setAvatarurl(avurl);
				}
			}else{
				suserInfo.setAvatar(0);
				//logger.error("studentid="+studentid+",getUserById()的suserInfo.getAvatar()为null");
				suserInfo.setStudent_cardpicb(0);
				suserInfo.setStudent_cardpicf(0);
				suserInfo.setId_cardpicb(0);
				suserInfo.setId_cardpicf(0);
				suserInfo.setCcheck_idcardpicb(0);
				suserInfo.setCcheck_idcardpicf(0);
				suserInfo.setCcheck_pic(0);
				suserInfo.setCoachstate(0);
				dataDao.updateObject(suserInfo);
			}
			suserInfo.setStudent_cardpicb_url(getFilePathById(suserInfo.getStudent_cardpicb()));
			suserInfo.setStudent_cardpicf_url(getFilePathById(suserInfo.getStudent_cardpicf()));
			suserInfo.setId_cardpicb_url(getFilePathById(suserInfo.getId_cardpicb()));
			suserInfo.setId_cardpicf_url(getFilePathById(suserInfo.getId_cardpicf()));
			suserInfo.setCcheck_idcardpicb_url(getFilePathById(suserInfo.getCcheck_idcardpicb()));
			suserInfo.setCcheck_idcardpicf_url(getFilePathById(suserInfo.getCcheck_idcardpicf()));
			suserInfo.setCcheck_pic_url(getFilePathById(suserInfo.getCcheck_pic()));
			
		}

		return suserInfo;
	}
	
	//获得学员的跟进状态
	@SuppressWarnings("unchecked")
	@Override
	public QueryResult<SuserState> getStateByStuid(String studentid,Integer pageIndex, int pagesize) {
		
		StringBuffer suserhql = new StringBuffer();
		suserhql.append("from SuserState where studentid=:studentid order by stateid desc");
		String[] parms={"studentid"};
		List<SuserState> susersta = (List<SuserState>)dataDao.pageQueryViaParam(suserhql.toString(),10, 1, parms,CommonUtils.parseInt(studentid, 0));
		String counthql = " select count(*) " + suserhql.toString();
		long total = (Long) dataDao.getFirstObjectViaParam(counthql, parms,CommonUtils.parseInt(studentid, 0));
	
		return new QueryResult<SuserState>(susersta, total);
	}
	
	//根据处理人的id获取处理人信息
	@Override
	public AdminInfo getDealpeopleById(String dealpeopleid){
	AdminInfo dealpeople;
	if(Integer.parseInt(dealpeopleid) != -1){
		dealpeople = dataDao.getObjectById(AdminInfo.class, CommonUtils.parseInt(dealpeopleid, 0));
		
		if (dealpeople != null) {
			
			dataDao.updateObject(dealpeople);
		}else{
			//System.out.print("dealpeople对象为空");
		}
	}
	else{
		dealpeople = new AdminInfo();
		dealpeople.setRealname("");;
		dataDao.updateObject(dealpeople);
		System.out.print("未获取到dealpeople对象");
	}
		
		return dealpeople;
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
		cuserhql.append(" from BalanceStudentInfo where userid = :studentid and amount>0 order by addtime desc");
		String[] params = { "studentid" };

		List<BalanceStudentInfo> recordlist = (List<BalanceStudentInfo>) dataDao.getObjectsViaParam(cuserhql.toString(), params, CommonUtils.parseInt(studentid, 0));
		for (BalanceStudentInfo bi : recordlist) {
			//类型(1:支付宝充值  2：支付宝提现  3：订单支付 4:支付宝提现不通过 5:微信充值 6:微信提现 7：微信提现不通过)
			if(bi.getType()==1){
				bi.setType_explain("充值");
			}else if(bi.getType()==2){
				bi.setType_explain("提现");
				bi.setAmount(new BigDecimal("-"+bi.getAmount().intValue()));
			}else if(bi.getType()==3){
				bi.setType_explain("订单支付");
				bi.setAmount(new BigDecimal("-"+bi.getAmount().intValue()));
			}else if(bi.getType()==4){
				bi.setType_explain("提现不通过");
			}else if(bi.getType()==5){
				bi.setType_explain("充值");
			}else if(bi.getType()==6){
				bi.setType_explain("提现");
				bi.setAmount(new BigDecimal("-"+bi.getAmount().intValue()));
			}else if(bi.getType()==7){
				bi.setType_explain("提现不通过");
			}
		}
		return recordlist;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void applyCash(String studentid, String count,String resource) {
		// 修改学员的余额信息
		SuserInfo student = dataDao.getObjectById(SuserInfo.class, CommonUtils.parseInt(studentid, 0));
		if (student != null) {
			int cmoney[]=getStudentMoney(student.getStudentid());
			BigDecimal suserOrderMoney=new BigDecimal(cmoney[0]);
			BigDecimal suserOrderFMoney=new BigDecimal(cmoney[1]);
			student.setMoney(suserOrderMoney.subtract(new BigDecimal(CommonUtils.parseInt(count, 0))));
			student.setFmoney(suserOrderFMoney.add(new BigDecimal(CommonUtils.parseInt(count, 0))));
			dataDao.updateObject(student);

			// 添加提现记录
			StudentApplyInfo apply = new StudentApplyInfo();
			apply.setUserid(CommonUtils.parseInt(studentid, 0));
			apply.setAmount(new BigDecimal(CommonUtils.parseFloat(count, 0f)));
			apply.setState(0);
			apply.setAddtime(new Date());
			apply.setResource(CommonUtils.parseInt(resource, 0));
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
	
	//实现显示报名学员列表的函数接口
	@SuppressWarnings("unchecked")
	@Override
	public QueryResult<SuserInfo> getEnrollSuserInfos(Integer pageIndex, int pagesize) {
		StringBuffer suserhql = new StringBuffer();
		suserhql.append("from SuserInfo where state=1");
		List<SuserInfo> suserInfolist = (List<SuserInfo>) dataDao.pageQueryViaParam(suserhql.toString() + " order by studentid asc", pagesize, pageIndex, null);
		String counthql = " select count(*) " + suserhql.toString();
		long total = (Long) dataDao.getFirstObjectViaParam(counthql, null);
		return new QueryResult<SuserInfo>(suserInfolist, total);
	}
	
	//实现显示已报名学员列表的函数接口
	@SuppressWarnings("unchecked")
	@Override
	public QueryResult<SuserInfo> getEnrolledSuserInfos(Integer pageIndex, int pagesize) {
		StringBuffer suserhql = new StringBuffer();
		suserhql.append("from SuserInfo where state=2");
		List<SuserInfo> suserInfolist = (List<SuserInfo>) dataDao.pageQueryViaParam(suserhql.toString() + " order by studentid asc", pagesize, pageIndex, null);
		String counthql = " select count(*) " + suserhql.toString();
		long total = (Long) dataDao.getFirstObjectViaParam(counthql, null);
		return new QueryResult<SuserInfo>(suserInfolist, total);
	}
	
	//获取已删除学员
		@SuppressWarnings("unchecked")
		@Override
		public QueryResult<SuserInfo> getDeleteSuserInfos(Integer pageIndex, int pagesize) {
			StringBuffer suserhql = new StringBuffer();
			suserhql.append("from SuserInfo where state=6");
			List<SuserInfo> suserInfolist = (List<SuserInfo>) dataDao.pageQueryViaParam(suserhql.toString() + " order by studentid asc", pagesize, pageIndex, null);
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
					studentapply.setWeixin_account(student.getOpenid());
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
			cuserhql.append(" and DATE(addtime) <= '" + newtimelater + "'");
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
			cuserhql.append(" and DATE(addtime) >= '" + newtoday + "'");
		}
		List<SuserInfo> suserInfolist = (List<SuserInfo>) dataDao.pageQueryViaParam(cuserhql.toString() + " order by id desc", pagesize, pageIndex, null);
		String counthql = " select count(*) " + cuserhql.toString();
		long total = (Long) dataDao.getFirstObjectViaParam(counthql, null);
		return new QueryResult<SuserInfo>(suserInfolist, total);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public QueryResult<SuserInfo> getEnrollStudentByKeyword(String searchname, String searchphone, String minsdate, String maxsdate, String minenrollsdate, String maxenrollsdate, Integer pageIndex, int pagesize) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from SuserInfo where state=1");
		if (!CommonUtils.isEmptyString(searchname)) {
			cuserhql.append(" and realname like '%" + searchname + "%'");
		}
		if (!CommonUtils.isEmptyString(searchphone)) {
			cuserhql.append(" and phone like '%" + searchphone + "%'");
		}
		//注册时间
		if (!CommonUtils.isEmptyString(maxsdate)) {
			Date newmaxsdate = CommonUtils.getDateFormat(maxsdate, "yyyy-MM-dd");
			Calendar latertime = Calendar.getInstance();
			latertime.setTime(newmaxsdate);
			latertime.set(Calendar.HOUR_OF_DAY, 23);
			latertime.set(Calendar.MINUTE, 59);
			latertime.set(Calendar.SECOND, 59);
			Date timelater = latertime.getTime();
			String newtimelater = CommonUtils.getTimeFormat(timelater, "yyyy-MM-dd");
			cuserhql.append(" and DATE(addtime) <= '" + newtimelater + "'");
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
			cuserhql.append(" and DATE(addtime) >= '" + newtoday + "'");
		}
		//报名时间
		if (!CommonUtils.isEmptyString(maxenrollsdate)) {
			Date newmaxsdate = CommonUtils.getDateFormat(maxenrollsdate, "yyyy-MM-dd");
			Calendar latertime = Calendar.getInstance();
			latertime.setTime(newmaxsdate);
			latertime.set(Calendar.HOUR_OF_DAY, 23);
			latertime.set(Calendar.MINUTE, 59);
			latertime.set(Calendar.SECOND, 59);
			Date timelater = latertime.getTime();
			String newtimelater = CommonUtils.getTimeFormat(timelater, "yyyy-MM-dd");
			cuserhql.append(" and  DATE(enrolltime) <= '" + newtimelater + "'");
		}
		if (!CommonUtils.isEmptyString(minenrollsdate)) {
			Date newminsdate = CommonUtils.getDateFormat(minenrollsdate, "yyyy-MM-dd");
			Calendar nowtime = Calendar.getInstance();
			nowtime.setTime(newminsdate);
			nowtime.set(Calendar.HOUR_OF_DAY, 0);
			nowtime.set(Calendar.MINUTE, 0);
			nowtime.set(Calendar.SECOND, 0);
			Date starttime = nowtime.getTime();
			String newtoday = CommonUtils.getTimeFormat(starttime, "yyyy-MM-dd");
			cuserhql.append(" and  DATE(enrolltime) >= '" + newtoday + "'");
		}
		List<SuserInfo> suserInfolist = (List<SuserInfo>) dataDao.pageQueryViaParam(cuserhql.toString() + " order by studentid asc", pagesize, pageIndex, null);
		String counthql = " select count(*) " + cuserhql.toString();
		long total = (Long) dataDao.getFirstObjectViaParam(counthql, null);
		return new QueryResult<SuserInfo>(suserInfolist, total);
	}
	
	
	//已删除学员条件搜索
	@SuppressWarnings("unchecked")
	@Override
	public QueryResult<SuserInfo> getDeleteStudentByKeyword(String searchname, String searchphone, String minsdate, String maxsdate, String minenrollsdate, String maxenrollsdate,Integer pageIndex, int pagesize) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from SuserInfo where state=6");
		if (!CommonUtils.isEmptyString(searchname)) {
			cuserhql.append(" and realname like '%" + searchname + "%'");
		}
		if (!CommonUtils.isEmptyString(searchphone)) {
			cuserhql.append(" and phone like '%" + searchphone + "%'");
		}
		//注册时间
		if (!CommonUtils.isEmptyString(maxsdate)) {
			Date newmaxsdate = CommonUtils.getDateFormat(maxsdate, "yyyy-MM-dd");
			Calendar latertime = Calendar.getInstance();
			latertime.setTime(newmaxsdate);
			latertime.set(Calendar.HOUR_OF_DAY, 23);
			latertime.set(Calendar.MINUTE, 59);
			latertime.set(Calendar.SECOND, 59);
			Date timelater = latertime.getTime();
			String newtimelater = CommonUtils.getTimeFormat(timelater, "yyyy-MM-dd");
			cuserhql.append(" and DATE(addtime) <= '" + newtimelater + "'");
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
			cuserhql.append(" and DATE(addtime) >= '" + newtoday + "'");
		}
		//报名时间
		if (!CommonUtils.isEmptyString(maxenrollsdate)) {
			Date newmaxsdate = CommonUtils.getDateFormat(maxenrollsdate, "yyyy-MM-dd");
			Calendar latertime = Calendar.getInstance();
			latertime.setTime(newmaxsdate);
			latertime.set(Calendar.HOUR_OF_DAY, 23);
			latertime.set(Calendar.MINUTE, 59);
			latertime.set(Calendar.SECOND, 59);
			Date timelater = latertime.getTime();
			String newtimelater = CommonUtils.getTimeFormat(timelater, "yyyy-MM-dd");
			cuserhql.append(" and DATE(enrolltime) <= '" + newtimelater + "'");
		}
		if (!CommonUtils.isEmptyString(minenrollsdate)) {
			Date newminsdate = CommonUtils.getDateFormat(minenrollsdate, "yyyy-MM-dd");
			Calendar nowtime = Calendar.getInstance();
			nowtime.setTime(newminsdate);
			nowtime.set(Calendar.HOUR_OF_DAY, 0);
			nowtime.set(Calendar.MINUTE, 0);
			nowtime.set(Calendar.SECOND, 0);
			Date starttime = nowtime.getTime();
			String newtoday = CommonUtils.getTimeFormat(starttime, "yyyy-MM-dd");
			cuserhql.append(" and DATE(enrolltime) >= '" + newtoday + "'");
		}
		List<SuserInfo> suserInfolist = (List<SuserInfo>) dataDao.pageQueryViaParam(cuserhql.toString() + " order by studentid asc", pagesize, pageIndex, null);
		String counthql = " select count(*) " + cuserhql.toString();
		long total = (Long) dataDao.getFirstObjectViaParam(counthql, null);
		return new QueryResult<SuserInfo>(suserInfolist, total);
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public QueryResult<SuserState> getStudentstateByKeyword(String studentid, Integer pageIndex, int pagesize) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from SuserState where studentid=:studentid order by stateid desc");
		
		String[] parms={"studentid"};
		List<SuserState> susersta = (List<SuserState>)dataDao.pageQueryViaParam(cuserhql.toString(), 10, 1, parms,CommonUtils.parseInt(studentid, 0));
		String counthql = " select count(*) " + cuserhql.toString();
		long total = (Long) dataDao.getFirstObjectViaParam(counthql, parms,CommonUtils.parseInt(studentid, 0));
	
		return new QueryResult<SuserState>(susersta, total);

	}
	
	
	
	
	//已报名学员
	@SuppressWarnings("unchecked")
	@Override
	public QueryResult<SuserInfo> getEnrolledStudentByKeyword(String searchname, String searchphone, String minsdate, String maxsdate,String minenrollsdate, String maxenrollsdate, Integer pageIndex, int pagesize) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from SuserInfo where state=2 ");
		if (!CommonUtils.isEmptyString(searchname)) {
			cuserhql.append(" and realname like '%" + searchname + "%'");
		}
		if (!CommonUtils.isEmptyString(searchphone)) {
			cuserhql.append(" and phone like '%" + searchphone + "%'");
		}
		//注册时间
		if (!CommonUtils.isEmptyString(maxsdate)) {
			Date newmaxsdate = CommonUtils.getDateFormat(maxsdate, "yyyy-MM-dd");
			Calendar latertime = Calendar.getInstance();
			latertime.setTime(newmaxsdate);
			latertime.set(Calendar.HOUR_OF_DAY, 23);
			latertime.set(Calendar.MINUTE, 59);
			latertime.set(Calendar.SECOND, 59);
			Date timelater = latertime.getTime();
			String newtimelater = CommonUtils.getTimeFormat(timelater, "yyyy-MM-dd");
			cuserhql.append(" and DATE(addtime) <= '" + newtimelater + "'");
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
			cuserhql.append(" and DATE(addtime) >= '" + newtoday + "'");
		}
		//报名时间
		if (!CommonUtils.isEmptyString(maxenrollsdate)) {
			Date newmaxsdate = CommonUtils.getDateFormat(maxenrollsdate, "yyyy-MM-dd");
			Calendar latertime = Calendar.getInstance();
			latertime.setTime(newmaxsdate);
			latertime.set(Calendar.HOUR_OF_DAY, 23);
			latertime.set(Calendar.MINUTE, 59);
			latertime.set(Calendar.SECOND, 59);
			Date timelater = latertime.getTime();
			String newtimelater = CommonUtils.getTimeFormat(timelater, "yyyy-MM-dd");
			cuserhql.append(" and DATE(enrolltime) <= '" + newtimelater + "'");
		}
		if (!CommonUtils.isEmptyString(minenrollsdate)) {
			Date newminsdate = CommonUtils.getDateFormat(minenrollsdate, "yyyy-MM-dd");
			Calendar nowtime = Calendar.getInstance();
			nowtime.setTime(newminsdate);
			nowtime.set(Calendar.HOUR_OF_DAY, 0);
			nowtime.set(Calendar.MINUTE, 0);
			nowtime.set(Calendar.SECOND, 0);
			Date starttime = nowtime.getTime();
			String newtoday = CommonUtils.getTimeFormat(starttime, "yyyy-MM-dd");
			cuserhql.append(" and DATE(enrolltime) >= '" + newtoday + "'");
		}
		List<SuserInfo> suserInfolist = (List<SuserInfo>) dataDao.pageQueryViaParam(cuserhql.toString() + " order by studentid asc", pagesize, pageIndex, null);
		String counthql = " select count(*) " + cuserhql.toString();
		long total = (Long) dataDao.getFirstObjectViaParam(counthql, null);
		return new QueryResult<SuserInfo>(suserInfolist, total);
	}
	

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void applyCheckPass(int coachid,int resource) {
		StudentApplyInfo studentApply = dataDao.getObjectById(StudentApplyInfo.class, coachid);
		if (studentApply != null) {
			studentApply.setState(1);
			studentApply.setUpdatetime(new Date());
			dataDao.updateObject(studentApply);
			SuserInfo student = dataDao.getObjectById(SuserInfo.class, studentApply.getUserid());
			if (student != null) {
				int cmoney[]=getStudentMoney(student.getStudentid());
				BigDecimal suserOrderFMoney=new BigDecimal(cmoney[1]);
				student.setFmoney(suserOrderFMoney.subtract(studentApply.getAmount()));
				dataDao.updateObject(student);
			}
			BalanceStudentInfo balancestudent = new BalanceStudentInfo();
			if(resource==0){
				balancestudent.setType(2);
			}else if(resource==1){
				balancestudent.setType(6);
			}
			
			balancestudent.setAddtime(new Date());
			balancestudent.setAmount(studentApply.getAmount());
			balancestudent.setUserid(studentApply.getUserid());
			dataDao.addObject(balancestudent);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void applyCheckNoPass(int coachid,int resource) {
		StudentApplyInfo studentApply = dataDao.getObjectById(StudentApplyInfo.class, coachid);
		if (studentApply != null) {
			studentApply.setState(2);
			studentApply.setUpdatetime(new Date());
			dataDao.updateObject(studentApply);
	            
	         SuserInfo suserInfo = dataDao.getObjectById(SuserInfo.class, coachid);
	         if(suserInfo != null){
	        	 int cmoney[]=getStudentMoney(suserInfo.getStudentid());
	        	 BigDecimal suserOrderMoney=new BigDecimal(cmoney[0]);
	        	 BigDecimal suserOrderFMoney=new BigDecimal(cmoney[1]);
	        	 suserInfo.setFmoney(suserOrderFMoney.subtract(studentApply.getAmount()));
	        	 //增加学员可提现余额
	        	 suserInfo.setMoney(suserOrderMoney.add(studentApply.getAmount()));
	        	 dataDao.updateObject(suserInfo);
	         }
	         
	         BalanceStudentInfo balanStudentInfo = new BalanceStudentInfo();
	         if(resource==0){
	        	 balanStudentInfo.setType(4);
			 }else if(resource==1){
				 balanStudentInfo.setType(7);
			 }
	         balanStudentInfo.setAddtime(new Date());
	         balanStudentInfo.setAmount(studentApply.getAmount());
	         balanStudentInfo.setUserid(studentApply.getUserid());
	         dataDao.addObject(balanStudentInfo);
	         
		}
	}
	
	//学员提现作废
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void applyCheckrevocation(int coachid) {
        StudentApplyInfo studentApply = dataDao.getObjectById(StudentApplyInfo.class, coachid);
        Date todate=new Date();
        if (studentApply != null) {
        	studentApply.setState(3);
        	studentApply.setUpdatetime(todate);
            dataDao.updateObject(studentApply);
           
            
            SuserInfo suserInfo = dataDao.getObjectById(SuserInfo.class, coachid);
            if(suserInfo != null){
            	int cmoney[]=getStudentMoney(suserInfo.getStudentid());
            	BigDecimal suserOrderFMoney=new BigDecimal(cmoney[1]);
            	suserInfo.setFmoney(suserOrderFMoney.subtract(studentApply.getAmount()));
            	dataDao.updateObject(suserInfo);
            }
           
        }
    }
    

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public QueryResult<StudentApplyInfo> getCoachApplyBySearch(String searchname, String searchphone, String amount, String inputamount, Integer state, String minsdate, String maxsdate,
			Integer pageIndex, int pagesize) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from StudentApplyInfo where 1 = 1");
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
			if (state == 0) {
				cuserhql.append(" and state = 0");
			}
			if (state == 2) {
				cuserhql.append(" and state = 2");
			}
			if (state == 3) {
				cuserhql.append(" and state = 3");
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
					capplyCash.setAlipay_account(student.getAlipay_account());
				}
			}
		}
		String counthql = " select count(*) " + cuserhql.toString();
		long total = (Long) dataDao.getFirstObjectViaParam(counthql, null);
		return new QueryResult<StudentApplyInfo>(applycashlist, total);
	}

	@SuppressWarnings("unchecked")
	@Override
	public QueryResult<StudentApplyInfo> getApplyRecordList(Integer pageIndex, int pagesize) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from StudentApplyInfo where state = 1");
		List<StudentApplyInfo> balancecoachlist = (List<StudentApplyInfo>) dataDao.pageQueryViaParam(cuserhql.toString(), pagesize, pageIndex, null);
		if (balancecoachlist != null && balancecoachlist.size() > 0) {
			for (StudentApplyInfo balanceCoach : balancecoachlist) {
				SuserInfo coach = dataDao.getObjectById(SuserInfo.class, balanceCoach.getUserid());
				if (coach != null) {
					balanceCoach.setRealname(coach.getRealname());
					balanceCoach.setPhone(coach.getPhone());
					balanceCoach.setAlipay_account(coach.getAlipay_account());
					//System.out.print(balanceCoach.getResource());
					if(coach.getOpenid() != null){
						balanceCoach.setWeixin_account(coach.getOpenid());
					}
				}
			}
		}
		String counthql = " select count(*) " + cuserhql.toString();
		long total = (Long) dataDao.getFirstObjectViaParam(counthql, null);
		return new QueryResult<StudentApplyInfo>(balancecoachlist, total);
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
			//获取学员充值账号(type=2并且state=1)
			for (BalanceStudentInfo balanceCoach : balancecoachlist) {
				//RechargeRecordInfo user = dataDao.getObjectById(RechargeRecordInfo.class, balanceCoach.getUserid());
				
				
				Date addtime = balanceCoach.getAddtime();
				StringBuffer suserhql = new StringBuffer();
				suserhql.append("from RechargeRecordInfo where updatetime=:addtime order by userid asc");
				String[] parms={"addtime"};
				List<RechargeRecordInfo> users = (List<RechargeRecordInfo>)dataDao.pageQueryViaParam(suserhql.toString(),10, 1, parms,addtime);
				String counthql = " select count(*) " + suserhql.toString();
				long total = (Long) dataDao.getFirstObjectViaParam(counthql, parms,addtime);
	
				QueryResult<RechargeRecordInfo> result = new QueryResult<RechargeRecordInfo>(users, total);
				List<RechargeRecordInfo> userinfo= result.getDataList();
				RechargeRecordInfo user = userinfo.get(0);
				
				if (user.getType()==2 && user.getState()==1) {
					balanceCoach.setBuyer_email(user.getBuyer_email());
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
	
	public CuserInfo getCoachById(String coachid) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append(" from CuserInfo where coachid = :coachid");
		String[] params = { "coachid" };
		CuserInfo coach = (CuserInfo) dataDao.getFirstObjectViaParam(cuserhql.toString(), params, CommonUtils.parseInt(coachid, 0));
		return coach;
	}

	@Override
	public StudentApplyInfo getApplyById(int applyid) {
		return dataDao.getObjectById(StudentApplyInfo.class, applyid);
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public QueryResult<StudentApplyInfo> searchStudentBalance(String searchname, String searchphone, String amount, String inputamount, String minsdate, String maxsdate, Integer pageIndex,
			int pagesize) {
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from StudentApplyInfo where state = 1");
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
			cuserhql.append("and updatetime >'" + minsdate + "'");
		}
		if (!CommonUtils.isEmptyString(maxsdate)) {
			Date enddate = CommonUtils.getDateFormat(maxsdate, "yyyy-MM-dd");
			enddate.setHours(23);
			enddate.setMinutes(59);
			enddate.setSeconds(59);
			String endmaxstime = CommonUtils.getTimeFormat(enddate, "yyyy-MM-dd HH:mm:ss");
			cuserhql.append("and updatetime <'" + endmaxstime + "'");
		}
		List<StudentApplyInfo> applycashlist = (List<StudentApplyInfo>) dataDao.pageQueryViaParam(cuserhql.toString(), pagesize, pageIndex, null);
		if (applycashlist != null && applycashlist.size() > 0) {
			for (StudentApplyInfo capplyCash : applycashlist) {
				SuserInfo student = dataDao.getObjectById(SuserInfo.class, capplyCash.getUserid());
				if (student != null) {
					capplyCash.setRealname(student.getRealname());
					capplyCash.setPhone(student.getPhone());
					capplyCash.setAlipay_account(student.getAlipay_account());
				}
			}
		}
		String counthql = " select count(*) " + cuserhql.toString();
		long total = (Long) dataDao.getFirstObjectViaParam(counthql, null);
		return new QueryResult<StudentApplyInfo>(applycashlist, total);
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
	public HashMap<String, Object> recharge(String studentid, String amount,String resource,String cip,String trade_type,String appid) throws IOException {
		HashMap<String, Object> result = new HashMap<String, Object>();

		// 插入数据
		RechargeRecordInfo info = new RechargeRecordInfo();
		info.setAddtime(new Date());
		info.setAmount(new BigDecimal(CommonUtils.parseFloat(amount, 0.0f)));
		info.setType(2);
		info.setUserid(CommonUtils.parseInt(studentid, 0));
		info.setPaytype(CommonUtils.parseInt(resource, 0));
		dataDao.addObject(info);
        if(resource.equals("0"))
        {
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
    		result.put("subject", "小巴学车充值：" + amount + "元");
    		result.put("total_fee", amount);
    		result.put("body", "小巴学车充值：" + amount + "元");
        }
        else if(resource.equals("1"))
        {
        	SuserInfo suser=dataDao.getObjectById(SuserInfo.class, CommonUtils.parseInt(studentid, 0));
        	IGetYouWanna wxmessageService=new GetYouWannaImpl();
        	String prepay_id=wxmessageService.getSignForPrePay(suser.getOpenid(), amount, info.getRechargeid().toString(), cip,trade_type,appid);
        	long timeStamp=wxmessageService.CreatenTimestamp();
        	String nonceStr=wxmessageService.CreatenNonce_str(25);
        	String mch_key=CommonUtils.getWXKey();
        	String appidtemp="";
        	SystemSetInfo systemset=dataDao.getObjectById(SystemSetInfo.class, 1);
        	if(appid==null)
        	   appidtemp=CommonUtils.getAppid();
        	else
        		appidtemp=appid;
        	String signType="MD5";
        	String paySign=wxmessageService.getSignForPay(appidtemp, timeStamp, nonceStr, signType, prepay_id);
        	if(paySign.equals("FAIL") || prepay_id.equals("FAIL"))
        	{
        		result.put("ERROR", "FAIL");
        	}
    	    if(trade_type!=null && trade_type.equals("APP"))
    	    {
    	    	result.put("mch_id", CommonUtils.getSmchid());
    	    }
    	    result.put("weixinpay", systemset.getWeixinpay_flag());
        	result.put("appId", appid);
        	result.put("prepay_id", prepay_id);
        	result.put("timeStamp", timeStamp);
        	result.put("nonceStr", nonceStr);       	
        	result.put("signType", signType);
        	result.put("paySign", paySign);
        	result.put("mch_key", mch_key);
        }

		return result;
	}
	/**
	 * 促销报名支付
	 * @param coachid
	 * @param amount
	 * @return
	 * @throws IOException 
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public HashMap<String, Object> promoRecharge(String studentid, String amount,String resource,String ip,String trade_type,String appid) throws IOException {
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		// 插入数据
		RechargeRecordInfo info=null;
			info = new RechargeRecordInfo();
			info.setAddtime(new Date());
			info.setAmount(new BigDecimal(CommonUtils.parseFloat(amount, 0.0f)));
			info.setType(3);
			info.setPaytype(CommonUtils.parseInt(resource, 0));
			info.setUserid(CommonUtils.parseInt(studentid, 0));
			dataDao.addObject(info);
		if(resource.equals("0"))
		{
			if ("1".equals(CommonUtils.getAliSet())) {
				result.put("partner", AlipayConfig.partner);
				result.put("seller_id", AlipayConfig.seller_id);
				result.put("private_key", AlipayConfig.private_key);
			} else {
				result.put("partner", AlipayConfig.partner_formal);
				result.put("seller_id", AlipayConfig.seller_id_formal);
				result.put("private_key", AlipayConfig.private_key_formal);
			}
			result.put("notify_url", CommonUtils.getWebRootUrl() + "enroll_callback");
			//result.put("notify_url", "http://120.25.236.228:8080/dadmin/suser?action=PROMOENROLLCALLBACK");
			result.put("out_trade_no", info.getRechargeid());
			result.put("subject", "报名费：" + amount + "元");
			result.put("total_fee", amount);
			result.put("body", "报名费：" + amount + "元");
		}
	    else if(resource.equals("1"))
	        {
	        	SuserInfo suser=dataDao.getObjectById(SuserInfo.class, CommonUtils.parseInt(studentid, 0));
	        	IGetYouWanna wxmessageService=new GetYouWannaImpl();
	        	String prepay_id=wxmessageService.getSignForPrePay(suser.getOpenid(), amount, info.getRechargeid().toString(), ip,trade_type,appid);
	        	long timeStamp=wxmessageService.CreatenTimestamp();
	        	String nonceStr=wxmessageService.CreatenNonce_str(25);
	        	String mch_key=CommonUtils.getWXKey();
	        	String appidtemp="";
	        	SystemSetInfo systemset=dataDao.getObjectById(SystemSetInfo.class, 1);
	        	if(appid==null)
	        	   appidtemp=CommonUtils.getAppid();
	        	else
	        		appidtemp=appid;
	        	String signType="MD5";
	        	String paySign=wxmessageService.getSignForPay(appidtemp, timeStamp, nonceStr, signType, prepay_id);
	        	if(paySign.equals("FAIL") || prepay_id.equals("FAIL"))
	        	{
	        		result.put("ERROR", "FAIL");
	        	}
	    	    if(trade_type!=null && trade_type.equals("APP"))
	    	    {
	    	    	result.put("mch_id", CommonUtils.getSmchid());
	    	    }
	    	    result.put("weixinpay", systemset.getWeixinpay_flag());
	        	result.put("appId", appid);
	        	result.put("prepay_id", prepay_id);
	        	result.put("timeStamp", timeStamp);
	        	result.put("nonceStr", nonceStr);       	
	        	result.put("signType", signType);
	        	result.put("paySign", paySign);
	        	result.put("mch_key", mch_key);
	        }

		return result;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void promoEnrollCallback(String out_trade_no){
		RechargeRecordInfo rc= dataDao.getObjectById(RechargeRecordInfo.class, CommonUtils.parseInt(out_trade_no,0));
		if(rc!=null){
			SuserInfo user=dataDao.getObjectById(SuserInfo.class,rc.getUserid());
			user.setEnrollpay(1);//设置已支付
			dataDao.updateObject(user);
		}
		 
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
		
		//获取学员充值账号(type=2并且state=1)
		for (BalanceStudentInfo balanceCoach : applycashlist) {
			//RechargeRecordInfo user = dataDao.getObjectById(RechargeRecordInfo.class, balanceCoach.getUserid());
			
			
			Date addtime = balanceCoach.getAddtime();
			StringBuffer suserhql = new StringBuffer();
			suserhql.append("from RechargeRecordInfo where updatetime=:addtime order by userid asc");
			String[] parms={"addtime"};
			List<RechargeRecordInfo> users = (List<RechargeRecordInfo>)dataDao.pageQueryViaParam(suserhql.toString(),10, 1, parms,addtime);
			String counthql = " select count(*) " + suserhql.toString();
			long total = (Long) dataDao.getFirstObjectViaParam(counthql, parms,addtime);

			QueryResult<RechargeRecordInfo> result = new QueryResult<RechargeRecordInfo>(users, total);
			List<RechargeRecordInfo> userinfo= result.getDataList();
			RechargeRecordInfo user = userinfo.get(0);
			
			if (user.getType()==2 && user.getState()==1) {
				balanceCoach.setBuyer_email(user.getBuyer_email());
			}
		}
		
		String counthql = " select count(*) " + cuserhql.toString();
		long total = (Long) dataDao.getFirstObjectViaParam(counthql, null);
		return new QueryResult<BalanceStudentInfo>(applycashlist, total);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public SuserInfo registerUser(String phone, String token,String openid) {
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
		user1.setFcoinnum(new BigDecimal(0));
		if(openid!=null && !"".equals(openid)){
			user1.setOpenid(openid);//设置版本号
		}
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
		String m="";
		if("1".equals(type)){
			m="教练";
		}else if("2".equals(type)){
			m="学员";
		}
		String content = content ="【小巴科技】"+ vercode.toString() + "(小巴学车"+m+"验证码," + login_vcode_time + "天登陆有效)";
		System.out.println(content);
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
		Integer count = (Integer) dataDao.getFirstObjectViaParam(suserhql.toString(), params, id);
		if(count==null){
			count=0;
		}
		return count;
	}
	
	@Override
	public String getCityByCityid(int id) {
		StringBuffer suserhql = new StringBuffer();
		suserhql.append(" select city  from CityInfo where cityid = :cityid");
		String[] params = { "cityid" };
		String ct = (String)dataDao.getFirstObjectViaParam(suserhql.toString(), params, id);
		return ct;
	}
	
	@Override
	public String getSuserSchoolByid(int id) {
		StringBuffer suserhql = new StringBuffer();
		suserhql.append(" select name from DriveSchoolInfo where schoolid = :schoolid");
		String[] params = { "schoolid" };
		String uschool = (String)dataDao.getFirstObjectViaParam(suserhql.toString(), params, id);
		return uschool;
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
		suserhql.append(" from CouponRecord where state=0 and end_time>=now() and  userid ="+studentid);			
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


	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public  int changeCoach(String studentId,String oldCoachId,String newCoachId)
	{
		StringBuffer suserhql = new StringBuffer();
		suserhql.append(" update CouponRecord set ownerid=:newCoachId where state=0 and ownertype=2 and ownerid=:oldCoachId  and userid =:studentId");
		String[] params = { "newCoachId", "oldCoachId","studentId" };
 		dataDao.updateObjectsViaParam(suserhql.toString(), params, Integer.parseInt(newCoachId), Integer.parseInt(oldCoachId), Integer.parseInt(studentId));
		return 1;
	}
	/**
	 * 结算时添加小巴币
	 * @param order 订单
	 * @param cuser 教练对象
	 * @param student 学员对象
	 * @param type  1 纯小巴币结算  ，2 小巴币和余额混合支付
	 * @author 卢磊
	 */
	/*public void addCoinForSettlement(OrderInfo order,CuserInfo cuser,SuserInfo student,int type){
		//可用教练小巴币
		int coinnumForCoach=getCanUseCoinnum(String.valueOf(order.getCoachid()),String.valueOf(order.getStudentid()));
		int coinnumForSchool=getCanUseCoinnumForDriveSchool(String.valueOf(order.getStudentid()));//获取驾校可用小巴币
		//获取平台发送的小巴币
		int coinnumForPlatform=getCanUseCoinnumForPlatform("0",String.valueOf(order.getStudentid()));//获取平台可用小巴币
		//订单额
		int total=0;
		if(type==1){
			total=order.getTotal().intValue();
		}else if(type==2){
			total=order.getMixCoin();
		}
		CoinRecordInfo coinRecordInfo = new CoinRecordInfo ();
        coinRecordInfo.setReceiverid(cuser.getCoachid());
        coinRecordInfo.setReceivertype(UserType.COAH);
        coinRecordInfo.setReceivername(cuser.getRealname());
       
        coinRecordInfo.setPayerid(student.getStudentid());
        coinRecordInfo.setPayertype(UserType.STUDENT);
        coinRecordInfo.setPayername(student.getRealname());
        coinRecordInfo.setType(CoinType.STUDENT_PAY);//学员支付
        coinRecordInfo.setAddtime(new Date());
        coinRecordInfo.setOrderid(order.getOrderid());//设置小巴币所属的订单的ID
		//小巴币结算优先顺序 ：平台--驾校--教练
		
		if(coinnumForPlatform>=total){//平台小巴币大于等于订单额时，使用平台小巴币结算
	        coinRecordInfo.setOwnerid(0);
	        coinRecordInfo.setOwnertype(UserType.PLATFORM);
	        coinRecordInfo.setOwnername("平台");
	        coinRecordInfo.setCoinnum(order.getTotal().intValue());
	        dataDao.addObject(coinRecordInfo);
		}else if((coinnumForPlatform+coinnumForSchool)>=total){//平台小巴币与驾校小巴币大于等于订单额时，使用平台小巴币和驾校小巴币结算
			//需要插入2条记录
			//平台的小巴币全部结算掉
			if(coinnumForPlatform>0){
				coinRecordInfo.setOwnerid(0);
		        coinRecordInfo.setOwnertype(UserType.PLATFORM);
		        coinRecordInfo.setOwnername("平台");
		        coinRecordInfo.setCoinnum(coinnumForPlatform);
		        dataDao.addObject(coinRecordInfo);
			}
			if(coinnumForSchool>0){
				//剩余部分由驾校小巴币结算
		        DriveSchoolInfo school=getCoinForDriveSchool(student.getStudentid());
		        if(school!=null){
		        	coinRecordInfo.setOwnerid(school.getSchoolid());
		        	coinRecordInfo.setOwnername(school.getName());
		        }else{
		        	coinRecordInfo.setOwnerid(-1);
		        	coinRecordInfo.setOwnername("驾校");
		        }
		        coinRecordInfo.setOwnertype(UserType.DRIVESCHOOL);
		        coinRecordInfo.setCoinnum(total-coinnumForPlatform);//剩余部分由驾校小巴币结算
		        dataDao.addObject(coinRecordInfo);
			}
		}else if((coinnumForPlatform+coinnumForSchool+coinnumForCoach)>=total){
			//最多需要插入3条记录
			//平台的小巴币全部结算掉
			if(coinnumForPlatform>0 && coinnumForSchool>0 && coinnumForCoach>0 && (coinnumForPlatform+coinnumForSchool)<total){
				if(coinnumForPlatform>0){
					coinRecordInfo.setOwnerid(0);
			        coinRecordInfo.setOwnertype(UserType.PLATFORM);
			        coinRecordInfo.setOwnername("平台");
			        coinRecordInfo.setCoinnum(coinnumForPlatform);//平台小巴币结算一部分
			        dataDao.addObject(coinRecordInfo);
				}
		        //剩余其中一部分由驾校小巴币结算
				if(coinnumForSchool>0){
				    DriveSchoolInfo school=getCoinForDriveSchool(student.getStudentid());
				    if(school!=null){
				        	coinRecordInfo.setOwnerid(school.getSchoolid());
				        	coinRecordInfo.setOwnername(school.getName());
				    }else{
				        	coinRecordInfo.setOwnerid(-1);
				        	coinRecordInfo.setOwnername("驾校");
				    }
				    coinRecordInfo.setOwnertype(UserType.DRIVESCHOOL);
				    coinRecordInfo.setCoinnum(coinnumForSchool);//剩余其中一部分由驾校小巴币结算
				    dataDao.addObject(coinRecordInfo);
				}
				coinRecordInfo.setOwnerid(order.getCoachid());
			    coinRecordInfo.setOwnertype(UserType.COAH);
			    coinRecordInfo.setOwnername(cuser.getRealname());
			    coinRecordInfo.setCoinnum(total-coinnumForPlatform-coinnumForSchool);
			    dataDao.addObject(coinRecordInfo);
			}
		}
	}*/
	public void addCoinForSettlement(OrderInfo order,CuserInfo cuser,SuserInfo student,int type){
		//可用教练小巴币
		//int coinnumForCoach=getCanUseCoinnum(String.valueOf(order.getCoachid()),);
		int coinnumForCoach=getCanUseCoinnumForCoach(String.valueOf(order.getStudentid()));
		//获取驾校可用小巴币
		int coinnumForSchool=getCanUseCoinnumForDriveSchool(String.valueOf(order.getStudentid()));
		//获取平台发送的小巴币
		int coinnumForPlatform=getCanUseCoinnumForPlatform("0",String.valueOf(order.getStudentid()));//获取平台可用小巴币
		//订单额
		int total=0;
		if(type==1){
			total=order.getTotal().intValue();
		}else if(type==2){
			total=order.getMixCoin();
		}
		//小巴币结算优先顺序 ：教练--驾校--平台
		if(coinnumForCoach>=total){//驾校小巴币大于等于订单额时，使用平台小巴币结算
			CoinRecordInfo cr1=createRecordInfo(order,cuser,student);
			cr1.setOwnerid(order.getCoachid());
			cr1.setOwnertype(UserType.COAH);
			cr1.setOwnername(cuser.getRealname());
			cr1.setCoinnum(order.getTotal().intValue());
	        dataDao.addObject(cr1);
		}else if((coinnumForCoach+coinnumForSchool)>=total){//教练小巴币与驾校小巴币大于等于订单额时，使用教练小巴币和驾校小巴币结算
			//需要插入2条记录
			//教练的小巴币全部结算掉
			if(coinnumForCoach>0){
				CoinRecordInfo cr2=createRecordInfo(order,cuser,student);
				cr2.setOwnerid(order.getCoachid());
				cr2.setOwnertype(UserType.COAH);
				cr2.setOwnername(cuser.getRealname());
				cr2.setCoinnum(coinnumForCoach);
		        dataDao.addObject(cr2);
			}
			if(coinnumForSchool>0){
				CoinRecordInfo cr3=createRecordInfo(order,cuser,student);
				//剩余部分由驾校小巴币结算
		        DriveSchoolInfo school=getCoinForDriveSchool(student.getStudentid());
		        if(school!=null){
		        	cr3.setOwnerid(school.getSchoolid());
		        	cr3.setOwnername(school.getName());
		        }else{
		        	cr3.setOwnerid(-1);
		        	cr3.setOwnername("驾校");
		        }
		        cr3.setOwnertype(UserType.DRIVESCHOOL);
		        cr3.setCoinnum(total-coinnumForCoach);//剩余部分由驾校小巴币结算
		        dataDao.addObject(cr3);
			}
		}else if((coinnumForCoach+coinnumForSchool+coinnumForPlatform)>=total){
			//最多需要插入3条记录
			//if(coinnumForPlatform>0 && coinnumForSchool>0 && coinnumForCoach>0 && (coinnumForPlatform+coinnumForSchool)<total){
				if(coinnumForCoach>0){
					CoinRecordInfo cr4=createRecordInfo(order,cuser,student);
					cr4.setOwnerid(order.getCoachid());
					cr4.setOwnertype(UserType.COAH);
					cr4.setOwnername(cuser.getRealname());
					cr4.setCoinnum(coinnumForCoach);
			        dataDao.addObject(cr4);
				}
		        //剩余其中一部分由驾校小巴币结算
				if(coinnumForSchool>0){
					CoinRecordInfo cr5=createRecordInfo(order,cuser,student);
				    DriveSchoolInfo school=getCoinForDriveSchool(student.getStudentid());
				    if(school!=null){
				    	cr5.setOwnerid(school.getSchoolid());
				    	cr5.setOwnername(school.getName());
				    }else{
				    	cr5.setOwnerid(-1);
				    	cr5.setOwnername("驾校");
				    }
				    cr5.setOwnertype(UserType.DRIVESCHOOL);
				    cr5.setCoinnum(coinnumForSchool);//剩余其中一部分由驾校小巴币结算
				    dataDao.addObject(cr5);
				}
				if(coinnumForPlatform>0){
					CoinRecordInfo cr6=createRecordInfo(order,cuser,student);
					cr6.setOwnerid(0);
					cr6.setOwnertype(UserType.PLATFORM);
					cr6.setOwnername("平台");
					cr6.setCoinnum(total-coinnumForCoach-coinnumForSchool);//平台小巴币结算一部分
			        dataDao.addObject(cr6);
				}
			//}
		}
	}
	public void getFrozenCoinAffiliationException(){
		String hql="from SuserInfo";
		
		List<SuserInfo> list=(List<SuserInfo>) dataDao.getObjectsViaParam(hql, null);
		for (SuserInfo suserInfo : list) {
			int studentid=suserInfo.getStudentid();
			//可用教练小巴币
			//int coinnumForCoach=getCanUseCoinnum(String.valueOf(order.getCoachid()),String.valueOf(order.getStudentid()));
			int coinnumForCoach=getCanUseCoinnumForCoach(String.valueOf(studentid));
			if(coinnumForCoach<0){
				System.out.println("studentid="+studentid+", 可用教练小巴币="+coinnumForCoach);
			}
			
			//获取驾校可用小巴币
			int coinnumForSchool=getCanUseCoinnumForDriveSchool(String.valueOf(studentid));
			if(coinnumForSchool<0){
				System.out.println("studentid="+studentid+", 驾校可用小巴币="+coinnumForSchool);
			}
			//获取平台发送的小巴币
			int coinnumForPlatform=getCanUseCoinnumForPlatform("0",String.valueOf(studentid));//获取平台可用小巴币
			if(coinnumForPlatform<0){
				System.out.println("studentid="+studentid+", 平台小巴币="+coinnumForPlatform);
			}
		}
	}
	/**
	 * 小巴币归属
	 * @param student
	 * @param total
	 * @return 
	 */
	public int[] getFrozenCoinAffiliation(int studentid,int total){
		int coinAn[]=new int[3];//小巴币归属数组，0 教练小巴币  1 驾校小巴币  2 平台小巴币
		int frozenCoach=0;
		int frozenSchool=0;
		int frozenPlatform=0;
		//可用教练小巴币
		//int coinnumForCoach=getCanUseCoinnum(String.valueOf(order.getCoachid()),String.valueOf(order.getStudentid()));
		int coinnumForCoach=getCanUseCoinnumForCoach(String.valueOf(studentid));
		if(coinnumForCoach<0){
			System.out.println("studentid="+studentid+", 可用教练小巴币="+coinnumForCoach);
		}
		
		//获取驾校可用小巴币
		int coinnumForSchool=getCanUseCoinnumForDriveSchool(String.valueOf(studentid));
		if(coinnumForSchool<0){
			System.out.println("studentid="+studentid+", 驾校可用小巴币="+coinnumForSchool);
		}
		//获取平台发送的小巴币
		int coinnumForPlatform=getCanUseCoinnumForPlatform("0",String.valueOf(studentid));//获取平台可用小巴币
		if(coinnumForPlatform<0){
			System.out.println("studentid="+studentid+", 平台小巴币="+coinnumForPlatform);
		}
		//订单额
		/*int total=0;
		if(type==1){
			total=order.getTotal().intValue();
		}else if(type==2){
			total=order.getMixCoin();
		}*/
		
		//小巴币结算优先顺序 ：教练--驾校--平台
		if(coinnumForCoach>=total){//驾校小巴币大于等于订单额时，使用平台小巴币结算
	        frozenCoach+=total;
		}else if((coinnumForCoach+coinnumForSchool)>=total){//教练小巴币与驾校小巴币大于等于订单额时，使用教练小巴币和驾校小巴币结算
			//需要插入2条记录
			//教练的小巴币全部结算掉
			if(coinnumForCoach>0){
		        frozenCoach+=coinnumForCoach;
			}
			if(coinnumForSchool>0){
		        frozenSchool+=total-coinnumForCoach;
			}
		}else if((coinnumForCoach+coinnumForSchool+coinnumForPlatform)>=total){
			//最多需要插入3条记录
			//if(coinnumForPlatform>0 && coinnumForSchool>0 && coinnumForCoach>0 && (coinnumForPlatform+coinnumForSchool)<total){
				if(coinnumForCoach>0){
			        frozenCoach+=coinnumForCoach;
				}
		        //剩余其中一部分由驾校小巴币结算
				if(coinnumForSchool>0){
				    frozenSchool+=coinnumForSchool;
				}
				if(coinnumForPlatform>0){
			        frozenPlatform+=total-coinnumForCoach-coinnumForSchool;
				}
			//}
		}
		coinAn[0]=frozenCoach;
		coinAn[1]=frozenSchool;
		coinAn[2]=frozenPlatform;
		return coinAn;
	}
	//创建RecordInfo对象
	private CoinRecordInfo createRecordInfo(OrderInfo order,CuserInfo cuser,SuserInfo student){
		CoinRecordInfo coinRecordInfo = new CoinRecordInfo ();
        coinRecordInfo.setReceiverid(cuser.getCoachid());
        coinRecordInfo.setReceivertype(UserType.COAH);
        coinRecordInfo.setReceivername(cuser.getRealname());
       
        coinRecordInfo.setPayerid(student.getStudentid());
        coinRecordInfo.setPayertype(UserType.STUDENT);
        coinRecordInfo.setPayername(student.getRealname());
        coinRecordInfo.setType(CoinType.STUDENT_PAY);//学员支付
        coinRecordInfo.setAddtime(new Date());
        coinRecordInfo.setOrderid(order.getOrderid());//设置小巴币所属的订单的ID
        return coinRecordInfo;
	}
	/**
	 * 获取学员针对某个教练的可用小巴币
	 * @param coachid 教练ID
	 * @param studentid 学员ID
	 * @return 小巴币个数
	 */
	@Override
	public int getCanUseCoinnum(String coachid, String studentid) {
			String countinhql = "select sum(coinnum) from CoinRecordInfo where (receiverid ="+studentid+" and receivertype="+ UserType.STUDENT+" and ownertype="+UserType.COAH+" and ownerid="+coachid+")";
			Object in= dataDao.getFirstObjectViaParam(countinhql, null);
			int totalin= in==null?0:CommonUtils.parseInt(in.toString(), 0);
		String countouthql = "select sum(coinnum) from CoinRecordInfo where (payerid ="+studentid+" and payertype="+ UserType.STUDENT+" and ownertype="+UserType.COAH+"  and ownerid="+coachid+")";
		Object out= dataDao.getFirstObjectViaParam(countouthql, null);
		int totalout = (out==null) ? 0: CommonUtils.parseInt(out.toString(),0);
		if((totalin-totalout)>=0){
			return (totalin-totalout);
		}else{
			return 0;
		}
	}
	/**
	 * 获取学员针对所有教练的可用小巴币
	 * @param coachid 教练ID
	 * @param studentid 学员ID
	 * @return 小巴币个数
	 * @author 卢磊
	 */
	public int getCanUseCoinnumForCoach(String studentid) {
		String countinhql = "select sum(coinnum) from CoinRecordInfo where (receiverid ="+studentid+" and receivertype="+ UserType.STUDENT+" and ownertype="+UserType.COAH+")";
		Object in= dataDao.getFirstObjectViaParam(countinhql, null);
		int totalin= in==null?0:CommonUtils.parseInt(in.toString(), 0);
		/*if(totalin==0){
			return 0;
		}*/
		String countouthql = "select sum(coinnum) from CoinRecordInfo where (payerid ="+studentid+" and payertype="+ UserType.STUDENT+" and ownertype="+UserType.COAH+")";
		Object out= dataDao.getFirstObjectViaParam(countouthql, null);
		int totalout = (out==null) ? 0: CommonUtils.parseInt(out.toString(),0);
		return (int) (totalin-totalout);
	}
	
	
	public int getCanUseCoinnumForDriveSchool( String studentid) {
		/*String countinhql = "select sum(coinnum) from CoinRecordInfo where (receiverid ="+studentid+" and receivertype="+ UserType.STUDENT+"and ownertype="+UserType.COAH+" and ownerid="+coachid+")";
		Object in= dataDao.getFirstObjectViaParam(countinhql, null);
		int totalin= in==null?0:CommonUtils.parseInt(in.toString(), 0);
		
		String countouthql = "select sum(coinnum) from CoinRecordInfo where (payerid ="+studentid+" and payertype="+ UserType.STUDENT+" and ownertype="+UserType.COAH+"  and ownerid="+coachid+")";
		Object out= dataDao.getFirstObjectViaParam(countouthql, null);
		int totalout = (out==null) ? 0: CommonUtils.parseInt(out.toString(),0);*/
		
		/*CuserInfo cuser=getCoachById(coachid);
		Integer schoolid=cuser.getDrive_schoolid();
		
		String countinhql2 = "select sum(coinnum) from CoinRecordInfo where (receiverid ="+studentid+" and receivertype="+ UserType.STUDENT+"and ownertype="+UserType.DRIVESCHOOL+" and ownerid="+schoolid+")";
		Object in2= dataDao.getFirstObjectViaParam(countinhql2, null);
		int totalin2= in2==null?0:CommonUtils.parseInt(in2.toString(), 0);

		String countouthql3 = "select sum(coinnum) from CoinRecordInfo where (payerid ="+studentid+" and payertype="+ UserType.STUDENT+" and ownertype="+UserType.DRIVESCHOOL+"  and ownerid="+schoolid+")";
		Object out2= dataDao.getFirstObjectViaParam(countouthql3, null);
		int totalout2 = (out2==null) ? 0: CommonUtils.parseInt(out2.toString(),0);*/
		
		String countinhql2 = "select sum(coinnum) from CoinRecordInfo where (receiverid ="+studentid+" and receivertype="+ UserType.STUDENT+" and ownertype="+UserType.DRIVESCHOOL+")";
		Object in2= dataDao.getFirstObjectViaParam(countinhql2, null);
		int totalin2= in2==null?0:CommonUtils.parseInt(in2.toString(), 0);
		/*if(totalin2==0){
			return 0;
		}*/
		String countouthql3 = "select sum(coinnum) from CoinRecordInfo where (payerid ="+studentid+" and payertype="+ UserType.STUDENT+" and ownertype="+UserType.DRIVESCHOOL+")";
		Object out2= dataDao.getFirstObjectViaParam(countouthql3, null);
		int totalout2 = (out2==null) ? 0: CommonUtils.parseInt(out2.toString(),0);
		
		return (int) (totalin2-totalout2);
	}
	/**
	 * 学员预约时查询可用驾校小巴币
	 * @param studentid
	 * @param coachid
	 * @return
	 */
	public int getCoinnumForDriveSchool( String studentid,String coachid) {
		String hql2="from DriveSchoolInfo where schoolid = (select drive_schoolid from CuserInfo where coachid=:coachid)";
		DriveSchoolInfo  ds= (DriveSchoolInfo) dataDao.getFirstObjectViaParam(hql2, new String[]{"coachid"},CommonUtils.parseInt(coachid, 0));
		int ownerid=0;
		if(ds!=null){
			ownerid=ds.getSchoolid();
		}
		StringBuffer hql=new StringBuffer();
		hql.append("select sum(coinnum) from CoinRecordInfo where (receiverid =:receiverid and receivertype=:receivertype");
		hql.append(" and ownertype=:ownertype and ownerid=:ownerid )");
		String param[]={"receiverid","receivertype","ownertype","ownerid"};
		
		Object in2= dataDao.getFirstObjectViaParam(hql.toString(), param,CommonUtils.parseInt(studentid, 0),UserType.STUDENT,UserType.DRIVESCHOOL,ownerid);
		int totalin2= in2==null?0:CommonUtils.parseInt(in2.toString(), 0);
		if(totalin2==0){
			return 0;
		}
		String countouthql3 = "select sum(coinnum) from CoinRecordInfo where (payerid ="+studentid+" and payertype="+ UserType.STUDENT+" and ownertype="+UserType.DRIVESCHOOL+")";
		Object out2= dataDao.getFirstObjectViaParam(countouthql3, null);
		int totalout2 = (out2==null) ? 0: CommonUtils.parseInt(out2.toString(),0);
		
		return (int) (totalin2-totalout2);
	}
	/**
	 * 获取驾校小巴币的驾校信息
	 * @param studentid
	 * @return
	 */
	public DriveSchoolInfo getCoinForDriveSchool(int studentid) {
		String dschoolhql="from CoinRecordInfo where receiverid =:receiverid and receivertype=3 and ownertype=:ownertype GROUP BY ownerid";
		String dparams[]={"receiverid","ownertype"};
		List<CoinRecordInfo> slist= (List<CoinRecordInfo>)dataDao.getObjectsViaParam(dschoolhql, dparams, studentid,UserType.DRIVESCHOOL);
		DriveSchoolInfo school=null;
		if(slist!=null && slist.size()>0){
			CoinRecordInfo cr=slist.get(0);
			school=dataDao.getObjectById(DriveSchoolInfo.class, cr.getOwnerid());
		}
		return school;
	}
	/*public DriveSchoolInfo getCoinForCoach(int studentid) {
		String dschoolhql="from CoinRecordInfo where receiverid =:receiverid and receivertype=3 and ownertype=:ownertype GROUP BY ownerid";
		String dparams[]={"receiverid","ownertype"};
		List<CoinRecordInfo> slist= (List<CoinRecordInfo>)dataDao.getObjectsViaParam(dschoolhql, dparams, studentid,UserType.DRIVESCHOOL);
		DriveSchoolInfo school=null;
		if(slist!=null && slist.size()>0){
			CoinRecordInfo cr=slist.get(0);
			school=dataDao.getObjectById(DriveSchoolInfo.class, cr.getOwnerid());
		}
		return school;
	}*/
	/**
	 * 获取平台发给学员的可用小巴币数量
	 * @param pid 平台ID
	 * @param studentid 学员ID
	 * 
	 * @return
	 */
	public int getCanUseCoinnumForPlatform(String pid, String studentid) {
			String countinhql = "select sum(coinnum) from CoinRecordInfo where (receiverid ="+studentid+" and receivertype="+ UserType.STUDENT+" and ownertype="+UserType.PLATFORM+" and ownerid="+pid+")";
			Object in= dataDao.getFirstObjectViaParam(countinhql, null);
			int totalin= in==null?0:CommonUtils.parseInt(in.toString(), 0);
			/*if(totalin==0){
				return 0;
			}*/
			String countouthql = "select sum(coinnum) from CoinRecordInfo where (payerid ="+studentid+" and payertype="+ UserType.STUDENT+" and ownertype="+UserType.PLATFORM+"  and ownerid="+pid+")";
			Object out= dataDao.getFirstObjectViaParam(countouthql, null);
			int totalout = (out==null) ? 0: CommonUtils.parseInt(out.toString(),0);
		return (int) (totalin-totalout);
	}
	/**
	 * 获取学员小巴币归属那些教练或驾校使用
	 */
	public HashMap<String, Object> getCoinAffiliation(String studentid){
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		SuserInfo suser=dataDao.getObjectById(SuserInfo.class, CommonUtils.parseInt(studentid, 0));
		
		//获取冻结小巴币数量
		int frozenCoin=0;
		if(suser.getFcoinnum()!=null){
			frozenCoin=suser.getFcoinnum().intValue();
		}
		//获取冻结扣除数量
		int[] coinAn=getFrozenCoinAffiliation(CommonUtils.parseInt(studentid, 0),frozenCoin);
		List<CoinAffiliation> mList=new ArrayList<CoinAffiliation>();
		//getFrozenCoinAffiliation(studentid,100);
		String hql="from CoinRecordInfo where receiverid =:receiverid and receivertype=3 and ownertype=:ownertype GROUP BY ownerid";
		String params[]={"receiverid","ownertype"};
		List<CoinRecordInfo> crlist= (List<CoinRecordInfo>) dataDao.getObjectsViaParam(hql, params, CommonUtils.parseInt(studentid, 0),2);
		//int sumCoachCoin=0;//教练小巴币总数
		
		if(crlist!=null && crlist.size()>0){
			for (CoinRecordInfo coinRecordInfo : crlist) {
				int coachid=coinRecordInfo.getOwnerid();
				int coachCoin=getCanUseCoinnum(String.valueOf(coachid),studentid);
				//sumCoachCoin+=coachCoin;
				String coachName="";
				if(coinRecordInfo.getOwnername()!=null){
					coachName=coinRecordInfo.getOwnername();
				}else{
					CuserInfo cuser=dataDao.getObjectById(CuserInfo.class, coachid);
					if(cuser!=null){
						coachName=cuser.getRealname();
					}
				}
				//针对教练的可用小巴币减去冻结
				///////////////////////
				coachCoin-=coinAn[0];
				///////////////////////
				if(coachCoin>0){
					CoinAffiliation ca=new CoinAffiliation(coachCoin,"仅限:"+coachName+"教练");
					mList.add(ca);
				}
			}
		}
		String dschoolhql="from CoinRecordInfo where receiverid =:receiverid and receivertype=3 and ownertype=:ownertype GROUP BY ownerid";
		String dparams[]={"receiverid","ownertype"};
		List<CoinRecordInfo> slist= (List<CoinRecordInfo>)dataDao.getObjectsViaParam(dschoolhql, dparams, CommonUtils.parseInt(studentid, 0),1);
		//int sumSchoolCoin=0;//驾校小巴币总数
		if(slist!=null && slist.size()>0){
			for (CoinRecordInfo coinRecordInfo : slist) {
				int schoolCoin=getCanUseCoinnumForDriveSchool(studentid);
				//sumSchoolCoin+=schoolCoin;
				//针对驾校的可用小巴币减去冻结
				///////////////////////
				schoolCoin-=coinAn[1];
				///////////////////////
				if(schoolCoin>0){
					CoinAffiliation ca=new CoinAffiliation(schoolCoin,"仅限:"+coinRecordInfo.getOwnername());
					mList.add(ca);
				}
			}
		}
		//平台小巴币
		int numForPlatform=getCanUseCoinnumForPlatform("0",studentid);
		numForPlatform-=coinAn[2];
		if(numForPlatform>0){
			CoinAffiliation cf=new CoinAffiliation(numForPlatform,"适用:所有教练");
			mList.add(cf);//通用
		}
		result.put("coinaffiliationlist",mList);//教练
		return result;
	}
	public int getSumCoinnum( String studentid ) {
			String countinhql = "select sum(coinnum) from CoinRecordInfo where  receivertype=3 and receiverid ="+studentid;
			Object in= dataDao.getFirstObjectViaParam(countinhql, null);
			int totalin= in==null?0:CommonUtils.parseInt(in.toString(), 0);

		return (int) totalin;
	}



	@Override
	public HashMap<String, Object> getCoinRecordList(String studentid) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		SuserInfo user = dataDao.getObjectById(SuserInfo.class, CommonUtils.parseInt(studentid, 0));
		if (user != null) {
			int coinnum=0;
			int fcoinnum=0;
			if(user.getCoinnum()!=null){
				coinnum=user.getCoinnum();
			}
			if(user.getFcoinnum()!=null){
				fcoinnum=user.getFcoinnum().intValue();
			}
			result.put("coinnum", coinnum);
			result.put("fcoinnum", fcoinnum);
			String hql = "from CoinRecordInfo where (receiverid =:receiverid and receivertype="+ UserType.STUDENT+" ) or (payerid =:payerid and payertype="+ UserType.STUDENT+")  order by addtime desc";
			String[] params = {"receiverid", "payerid"};
            
			Integer cid = CommonUtils.parseInt(studentid, 0);
			
			List<CoinRecordInfo> list = (List<CoinRecordInfo>)dataDao.getObjectsViaParam(hql, params, cid, cid);
			
			String chql="from CuserInfo where coachid=:coachid";
			String[] params1={"coachid"};
			if (list != null && list.size()>0) {
				CuserInfo tempCuserInfo=(CuserInfo)dataDao.getFirstObjectViaParam(chql, params1,list.get(0).getOwnerid());
				if(tempCuserInfo!=null)
				   result.put("coachname", tempCuserInfo.getRealname());
				result.put("recordlist", list);
				result.put("hasmore", 0);
			}
		}
		else {
			result.put("code", 2);
			result.put("message", "用户不存在");
		}
		return result;
	}
	//查询小巴币，小巴券,余额的累积消费额
	public HashMap<String, Object> getConsumeAmount(String studentid) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		SuserInfo user = dataDao.getObjectById(SuserInfo.class, CommonUtils.parseInt(studentid, 0));
		if (user != null) {
			/*String hql = "select sum(coinnum) from CoinRecordInfo where payerid =:payerid and payertype="+ UserType.STUDENT+"  order by addtime desc";
			String[] params = { "payerid"};
			Integer cid = CommonUtils.parseInt(studentid, 0);
			BigDecimal consumeCoin = (BigDecimal)dataDao.getFirstObjectViaParam(hql, params, cid);
			if(consumeCoin!=null){
				result.put("consumeCoin", consumeCoin);//小巴币的累积消费额
			}else{
				result.put("consumeCoin", 0);//小巴币的累积消费额
			}
			
			
			String couponhql="select count(*) from CouponRecord where state=1 and userid=:userid";
			String[] couponParams = { "userid"};
			Long consumeCoupon = (Long)dataDao.getFirstObjectViaParam(couponhql, couponParams, cid);
			if(consumeCoupon!=null){
				result.put("consumeCoupon", consumeCoupon);//小巴券的累积消费额
			}else{
				result.put("consumeCoupon", 0);//小巴券的累积消费额
			}
			*/
			
			Integer cid = CommonUtils.parseInt(studentid, 0);
			String hql3 ="select sum(total) from OrderInfo where studentid=:studentid and paytype=:paytype and studentstate=3";
			String[] params3 = { "studentid","paytype"};
			// 1 余额  3小巴币  2 小巴券
			BigDecimal consumeMoney = (BigDecimal)dataDao.getFirstObjectViaParam(hql3, params3, cid,1);
			String hql4 ="select count(*) from OrderInfo where studentid=:studentid and paytype=:paytype and studentstate=3";
			Long  consumeCoupon= (Long)dataDao.getFirstObjectViaParam(hql4, params3, cid,2);//小巴券
			
			BigDecimal  consumeCoin= (BigDecimal)dataDao.getFirstObjectViaParam(hql3, params3, cid,3);//小巴币
			//混合支付的情况：
			String mixhql ="select sum(mixCoin),sum(mixMoney) from OrderInfo where studentid=:studentid and paytype=:paytype and studentstate=3";
			String[] mixparams = { "studentid","paytype"};
			Object[]  mixamout= (Object[])dataDao.getFirstObjectViaParam(mixhql, mixparams, cid,4);
			Long  mixcoin=0L;
			Long  mixmoney=0L;
			if(mixamout!=null){
				if(mixamout[0]!=null){
					mixcoin=(Long)mixamout[0];
				}
				if(mixamout[1]!=null){
					mixmoney=(Long)mixamout[1];
				}
			}
			
			if(consumeMoney!=null){
				result.put("consumeMoney", consumeMoney.doubleValue()+mixmoney.doubleValue());
			}else{
				result.put("consumeMoney", 0);
			}
			if(consumeCoin!=null){
				result.put("consumeCoin", consumeCoin.intValue()+mixcoin.doubleValue());
			}else{
				result.put("consumeCoin", 0);
			}
			if(consumeCoupon!=null){
				result.put("consumeCoupon", consumeCoupon.intValue());
			}else{
				result.put("consumeCoupon",0);
			}
			
			
		}else {
			result.put("code", 2);
			result.put("message", "用户不存在");
		}
		return result;
	}
    @Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Override
	public int resetVerCode(String phone,int type) {
		StringBuffer querystring=new StringBuffer();
		querystring.append("from VerifyCodeInfo where phone = :phone and totype=:totype");
		String[] params={"phone","totype"};
		VerifyCodeInfo tempVerifyCodeInfo = (VerifyCodeInfo) dataDao.getFirstObjectViaParam(querystring.toString(), params, phone,type);
		String newvcode=phone.substring(5, 11);
		if(tempVerifyCodeInfo!=null)
		{
			tempVerifyCodeInfo.setCode(newvcode);
			tempVerifyCodeInfo.setAddtime(new Date());
            dataDao.updateObject(tempVerifyCodeInfo);
		}
		else
		{
			VerifyCodeInfo vc=new VerifyCodeInfo();
			vc.setAddtime(new Date());
			vc.setCode(newvcode);
			vc.setTotype(type);
			vc.setPhone(phone);
			dataDao.addObject(vc);
		}
		return 1;
	}
    
    
    
   //学员驾校列表
  	@SuppressWarnings("unchecked")
  	@Override
  	public QueryResult<SuserInfo> getStudentSchool(Integer pageIndex, int pagesize) {
  		StringBuffer suserhql = new StringBuffer();
  		suserhql.append("from SuserInfo where state=1");
  		List<SuserInfo> suserInfolist = (List<SuserInfo>) dataDao.pageQueryViaParam(suserhql.toString() + " order by studentid asc", pagesize, pageIndex, null);
  		String counthql = " select count(*) " + suserhql.toString();
  		long total = (Long) dataDao.getFirstObjectViaParam(counthql, null);
  		return new QueryResult<SuserInfo>(suserInfolist, total);
  	}
  	
    //学员添加驾校列表
  	@SuppressWarnings("unchecked")
  	@Override
  	public QueryResult<SuserInfo> setStudentSchool(Integer pageIndex, int pagesize) {
  		StringBuffer suserhql = new StringBuffer();
  		suserhql.append("from SuserInfo where state=1");
  		//List<SuserInfo> suserInfolist = (List<SuserInfo>) dataDao.pageQueryViaParam(suserhql.toString() + " order by studentid asc", pagesize, pageIndex, null);
  		List<SuserInfo> suserInfolist = (List<SuserInfo>) dataDao.getObjectsViaParam(suserhql.toString() + " order by studentid asc", null);
  		
  		String counthql = " select count(*) " + suserhql.toString();
  		long total = (Long) dataDao.getFirstObjectViaParam(counthql, null);
  		return new QueryResult<SuserInfo>(suserInfolist, total);
  	}

	@Override
	public void addAlipayCallBack(String qs, String ru) {
		AlipayCallBack ac=new AlipayCallBack();
		ac.setQs(qs);
		ac.setRu(ru);
		dataDao.addObject(ac);
		
	}

	@Override
	public String checkopenid(String openid) {
		  String querystring="from SuserInfo where openid=:openid";
		  String[] params={"openid"};
		  SuserInfo suser=(SuserInfo) dataDao.getFirstObjectViaParam(querystring, params,openid);
		  if(suser!=null)
			  return suser.getPhone();
		  return "";
	}
	/**
	 * 获取此学员是否拥有参与体验课资格
	 * @param studentid 学员id
	 * @return  true 有资格 ，false 无资格
	 */
	@Override
	public boolean getFreecoursestate(int studentid) {
		String hql10="select count(*) from OrderInfo where studentid=:studentid and orderid not in "+
					 " (select orderid from OrderInfo where coachstate=4 and studentstate=4 and studentid=:studentid) ";
		Long n=(Long) dataDao.getFirstObjectViaParam(hql10, new String[]{"studentid","studentid"}, studentid,studentid);
		if(n==null){
			n=0L;
		}
		if(studentid==0){
			return false;
		}
		if(n==0){
			return true;
		}
		return false;
	}
  	
	/**
	 * 从订单表中查询学员的真实小巴币个数
	 * 【小巴币个数=充值的小巴币数-回收的小巴币-订单中已结算的小巴币-冻结 小巴币】
	 * 其中  订单中已结算的小巴币=纯小巴币支付的订单+混合支付中小巴币支付的订单
	 * @param studentid 学员id
	 * @return
	 */
	public int[] getStudentCoin(int studentid){
		int coin_fcoin[]=new int[2];
		SuserInfo suser=dataDao.getObjectById(SuserInfo.class, studentid);
		if(suser==null){
			return coin_fcoin;
		}
		//【充值的小巴币】
		int receiverCoinNum=0;
		String hql1="select sum(coinnum) from CoinRecordInfo where  type=1 and receiverid="+studentid;
		Long sumcoinnum=(Long) dataDao.getFirstObjectViaParam(hql1, null);
		if(sumcoinnum==null){
			sumcoinnum=0L;
		}
		String hql1_1="select sum(coinnum) from CoinRecordInfo where  type=3 and payerid="+studentid;
		Long retrieveCoin=(Long) dataDao.getFirstObjectViaParam(hql1_1, null);
		if(retrieveCoin==null){
			retrieveCoin=0L;
		}
		//【回收的小巴币】
		receiverCoinNum=sumcoinnum.intValue()-retrieveCoin.intValue();
		//【订单中已结算的小巴币】
		int settleCoinNum=0;
		String hql2="select sum(total) from t_order where studentid=:studentid and paytype=3 and studentstate=3  and coachstate=2 and over_time is not null";
		String hql3="select sum(mixCoin) from t_order where studentid=:studentid and paytype=4  and studentstate=3  and coachstate=2 and over_time is not null";
		BigDecimal n1=(BigDecimal) dataDao.getFirstObjectViaParamSql(hql2, new String[]{"studentid"}, studentid);
		if(n1==null){
			n1=new BigDecimal(0);
		}
		BigDecimal n2=(BigDecimal) dataDao.getFirstObjectViaParamSql(hql3, new String[]{"studentid"}, studentid);
		if(n2==null){
			n2=new BigDecimal(0);
		}
		settleCoinNum=(int)(n1.intValue()+n2.intValue());
		//【冻结 小巴币】 =纯余额支付订单中的冻结金额+混合支付订单中的冻结小巴币
		//纯小巴币支付订单中的冻结小巴币
		StringBuffer sql6=new StringBuffer();
		sql6.append("select sum(total) from t_order where studentid=:studentid and paytype=3 and orderid not in (")
		.append("select orderid from t_order where studentid=:studentid and paytype=3 and (")
		.append("( studentstate=3 and coachstate=2 and over_time is not  null)")
		.append("or ( studentstate=4 and coachstate=4 and over_time is  null)))");
		
		BigDecimal n3=(BigDecimal) dataDao.getFirstObjectViaParamSql(sql6.toString(), new String[]{"studentid","studentid"}, studentid,studentid);
		if(n3==null){
			n3=new BigDecimal(0);
		}
		
		//混合支付订单中的冻结小巴币
		StringBuffer sql7=new StringBuffer();
		sql7.append("select sum(mixCoin) from t_order where studentid=:studentid and paytype=4 and orderid not in (")
		.append("select orderid from t_order where studentid=:studentid and paytype=4 and (")
		.append("( studentstate=3 and coachstate=2 and over_time is not  null)")
		.append("or ( studentstate=4 and coachstate=4 and over_time is  null)))");
				
		BigDecimal n4=(BigDecimal) dataDao.getFirstObjectViaParamSql(sql7.toString(), new String[]{"studentid","studentid"}, studentid,studentid);
		if(n4==null){
			n4=new BigDecimal(0);
		}
		//【冻结 小巴币】 =纯小巴币支付订单中的冻结小巴币+混合支付订单中的冻结小巴币
		int fcoinNum=n3.intValue()+n4.intValue();
		
		//【小巴币个数=充值的小巴币数-订单中已结算的小巴币-冻结 小巴币】
		int rn=receiverCoinNum-settleCoinNum-fcoinNum;
		coin_fcoin[0]=rn;//小巴币
		coin_fcoin[1]=fcoinNum;
		return coin_fcoin;
	}
  	
	/**
	 * 从订单表中查询学员的真实余额值
	 * 【余额值=学员充值金额-提现金额-已结算订单消费的金额-冻结金额】
	 * 冻结金额=订单中已经结算的和确认取消的订单之外剩余的订单都是冻结金额订单
	 * @param studentid
	 * @return
	 */
	public int[] getStudentMoney(int studentid){
		int money_fmoney[]=new int[2];
		SuserInfo suser=dataDao.getObjectById(SuserInfo.class, studentid);
		if(suser==null){
			return money_fmoney;
		}
		//学员充值金额
		String hql1="select sum(amount) from RechargeRecordInfo where userid=:userid  and type=2 and state=1";
		BigDecimal rechargeMoney=(BigDecimal) dataDao.getFirstObjectViaParam(hql1, new String[]{"userid"}, studentid);
		if(rechargeMoney==null){
			rechargeMoney=new BigDecimal(0);
		}
		
		//已提现金额
		String hql2="select sum(amount) from StudentApplyInfo where userid=:userid and state=1";
		BigDecimal cashMoney=(BigDecimal) dataDao.getFirstObjectViaParam(hql2, new String[]{"userid"}, studentid);
		if(cashMoney==null){
			cashMoney=new BigDecimal(0);
		}
		
		//已结算订单消费的金额
		int settleMoney=0;
		String hql3="select sum(total) from OrderInfo where studentid=:studentid and paytype=1 and studentstate=3  and coachstate=2 and over_time is not null";
		String hql4="select sum(mixmoney) from t_order where studentid=:studentid and paytype=4  and studentstate=3  and coachstate=2 and over_time is not null";
		BigDecimal n1=(BigDecimal) dataDao.getFirstObjectViaParam(hql3, new String[]{"studentid"}, studentid);
		if(n1==null){
			n1=new BigDecimal(0);
		}
		BigDecimal n2=new BigDecimal(0);
		n2=(BigDecimal) dataDao.getFirstObjectViaParamSql(hql4, new String[]{"studentid"}, studentid);
		/*if(nmlist!=null && nmlist.size()>0 &&  nmlist.get(0)!=null){
			n2=(BigDecimal) nmlist.get(0);
		}*/
		//BigDecimal n2=(BigDecimal) dataDao.getFirstObjectViaParam(hql4, new String[]{"studentid"}, studentid);
		if(n2==null){
			n2=new BigDecimal(0);
		}
		settleMoney=(int)(n1.intValue()+n2.intValue());
		
		//【冻结 金额】 =提现时的冻结金额+(纯余额支付订单中的冻结金额+混合支付订单中的冻结金额)
		
		//提现中金额，会冻结
		String hql5="select sum(amount) from StudentApplyInfo where userid=:userid and state=0";
		BigDecimal freezeCashMoney=(BigDecimal) dataDao.getFirstObjectViaParam(hql5, new String[]{"userid"}, studentid);
		if(freezeCashMoney==null){
			freezeCashMoney=new BigDecimal(0);
		}
		
		//纯余额支付订单中的冻结金额
		StringBuffer sql6=new StringBuffer();
		sql6.append("select sum(total) from t_order where studentid=:studentid and paytype=1 and orderid not in (")
		.append("select orderid from t_order where studentid=:studentid and paytype=1 and (")
		.append("( studentstate=3 and coachstate=2 and over_time is not  null)")
		.append("or ( studentstate=4 and coachstate=4 and over_time is  null)))");
		
		BigDecimal n3=(BigDecimal) dataDao.getFirstObjectViaParamSql(sql6.toString(), new String[]{"studentid","studentid"}, studentid,studentid);
		if(n3==null){
			n3=new BigDecimal(0);
		}
		
		//混合支付订单中的冻结金额
		StringBuffer sql7=new StringBuffer();
		sql7.append("select sum(mixMoney) from t_order where studentid=:studentid and paytype=4 and orderid not in (")
		.append("select orderid from t_order where studentid=:studentid and paytype=4 and (")
		.append("( studentstate=3 and coachstate=2 and over_time is not  null)")
		.append("or ( studentstate=4 and coachstate=4 and over_time is  null)))");
				
		BigDecimal n4=(BigDecimal) dataDao.getFirstObjectViaParamSql(sql7.toString(), new String[]{"studentid","studentid"}, studentid,studentid);
		if(n4==null){
			n4=new BigDecimal(0);
		}
		//【冻结 金额】 =提现时的冻结金额+(纯余额支付订单中的冻结金额+混合支付订单中的冻结金额)
		int fmoney=freezeCashMoney.intValue()+n3.intValue()+n4.intValue();
		
		//【余额值=学员充值金额-提现金额-已结算订单消费的金额-冻结金额】
		int rn=(int) (rechargeMoney.intValue()-cashMoney.intValue()-settleMoney-fmoney);
		
		money_fmoney[0]=rn;//余额
		money_fmoney[1]=fmoney;//冻结余额
		return money_fmoney;
	}
	/**
	 * 查询有异常的学员余额
	 */
	public List findStudentMoneyException(){
		List relist=new ArrayList();
		String hql="from SuserInfo";
		List<SuserInfo> list=(List<SuserInfo>) dataDao.getObjectsViaParam(hql, null);
		for (SuserInfo user : list) {
			if(user!=null){
				if(user.getMoney()==null){
					user.setMoney(new BigDecimal(0));
				}
				if(user.getFmoney()==null){
					user.setFmoney(new BigDecimal(0));
				}
				int m1[]=getStudentMoney(user.getStudentid());
				int m2=user.getMoney().intValue()-user.getFmoney().intValue();
				if(m1[0]!=m2 || m2<0 || m1[0]<0 ||m1[1]<0 ||m1[1]!=user.getFmoney().intValue()){
					/*String str="studentid="+user.getStudentid()+",money="+m2+",订单结算值="+m1+"<br>";
					relist.add(str);*/
					//System.out.println("studentid="+user.getStudentid()+",money="+m2+",订单结算值="+m1+"<br>");
					dataDao.updateBySql("update t_user_student_copy2 set money2="+m1[0]+", fmoney2="+m1[1]+" where studentid="+user.getStudentid());
				}
			}
			
		}
		return relist;
	}
	/**
  	 * 【教练金额=订单表教练获取的金额-提现金额-提现冻结金额】
  	 * @param coachid
  	 * @return
  	 */
	public int[] getCoachMoney(int coachid){
		int money_fmoney[]=new int[2];
		CuserInfo cuser=dataDao.getObjectById(CuserInfo.class, coachid);
		if(cuser==null){
			return money_fmoney;
		}
		//订单表教练获取的金额
		int receiverMoney=0;
		String hql1="select sum(total) from t_order where coachid=:coachid and paytype=1 and studentstate=3 and coachstate=2 and over_time is not null";
		BigDecimal n1=(BigDecimal) dataDao.getFirstObjectViaParamSql(hql1, new String[]{"coachid"}, coachid);
		if(n1==null){
			n1=new BigDecimal(0);
		}
		String hql2="select sum(mixmoney) from t_order where coachid=:coachid and paytype=4 and studentstate=3 and coachstate=2 and over_time is not null";
		BigDecimal n2=(BigDecimal) dataDao.getFirstObjectViaParamSql(hql2, new String[]{"coachid"}, coachid);
		if(n2==null){
			n2=new BigDecimal(0);
			
		}
		receiverMoney=n1.intValue()+n2.intValue();
		//提现金额
		String hql3="select sum(amount) from CApplyCashInfo where coachid=:coachid and state=1";
		BigDecimal amount=(BigDecimal) dataDao.getFirstObjectViaParam(hql3, new String[]{"coachid"}, coachid);
		if(amount==null){
			amount=new BigDecimal(0);
		}
		//提现冻结金额
		String hql4="select sum(amount) from CApplyCashInfo where coachid=:coachid and state=0";
		BigDecimal freezeAmount=(BigDecimal) dataDao.getFirstObjectViaParam(hql4, new String[]{"coachid"}, coachid);
		if(freezeAmount==null){
			freezeAmount=new BigDecimal(0);
		}
		// 【教练金额=订单表教练获取的金额-提现金额-提现冻结金额】
		money_fmoney[0]=receiverMoney-amount.intValue()-freezeAmount.intValue();
		money_fmoney[1]=freezeAmount.intValue();
		return money_fmoney;
	}
	/**
	 * 查询有异常的教练余额
	 */
	public List findCoachMoneyException(){
		List relist=new ArrayList();
		String hql="from CuserInfo";
		List<CuserInfo> list=(List<CuserInfo>) dataDao.getObjectsViaParam(hql, null);
		for (CuserInfo user : list) {
			if(user!=null){
				if(user.getMoney()==null){
					user.setMoney(new BigDecimal(0));
				}
				if(user.getFmoney()==null){
					user.setFmoney(new BigDecimal(0));
				}
				int m1[]=getCoachMoney(user.getCoachid());
				int m2=user.getMoney().intValue()-user.getFmoney().intValue();
				if(m1[0]!=m2 || m2<0 || m1[0]<0 ||m1[1]<0 ||m1[1]!=user.getFmoney().intValue()){
					/*String str="studentid="+user.getStudentid()+",money="+m2+",订单结算值="+m1+"<br>";
					relist.add(str);*/
					//System.out.println("studentid="+user.getStudentid()+",money="+m2+",订单结算值="+m1+"<br>");
					dataDao.updateBySql("update t_user_coach_copy2 set money2="+m1[0]+", fmoney2="+m1[1]+" where coachid="+user.getCoachid());
				}
			}
			
		}
		return relist;
	}
	/**
  	 * 【教练小巴币=订单表教练获取的小巴币-兑换的小巴币】
  	 *  教练无冻结小巴币
  	 * @param coachid
  	 * @return
  	 */
	public int getCoachCoin(int coachid){
		//int coin_fcoin[]=new int[2];
		CuserInfo cuser=dataDao.getObjectById(CuserInfo.class, coachid);
		if(cuser==null){
			return 0;
		}
		//订单表教练获取的金额
		int receiverCoin=0;
		String hql1="select sum(total) from t_order where coachid=:coachid and paytype=3 and studentstate=3 and coachstate=2  and over_time is not null";
		BigDecimal n1=(BigDecimal) dataDao.getFirstObjectViaParamSql(hql1, new String[]{"coachid"}, coachid);
		if(n1==null){
			n1=new BigDecimal(0);
		}
		String hql2="select sum(mixcoin) from t_order where coachid=:coachid and paytype=4 and studentstate=3 and coachstate=2 and over_time is not null";
		BigDecimal n2=(BigDecimal) dataDao.getFirstObjectViaParamSql(hql2, new String[]{"coachid"}, coachid);
		if(n2==null){
			n2=new BigDecimal(0);
		}
		receiverCoin=n1.intValue()+n2.intValue();
		//兑换的小巴币
		String hql3="select sum(coinnum) from t_coin_record where payertype=2 and payerid=:payerid";
		BigDecimal amount=(BigDecimal) dataDao.getFirstObjectViaParamSql(hql3, new String[]{"payerid"}, coachid);
		if(amount==null){
			amount=new BigDecimal(0);
		}
		return receiverCoin-amount.intValue();//订单获取的小巴币
	}
	/**
	 * 查询有异常的教练小巴币
	 */
	public List findCoachCoinException(){
		List relist=new ArrayList();
		String hql="from CuserInfo";
		List<CuserInfo> list=(List<CuserInfo>) dataDao.getObjectsViaParam(hql, null);
		for (CuserInfo user : list) {
			if(user!=null){
				if(user.getCoinnum()==null){
					user.setCoinnum(new BigDecimal(0).intValue());
				}
				int m1=getCoachCoin(user.getCoachid());
				int m2=user.getCoinnum().intValue();
				if(m1!=m2 || m2<0 || m1<0 ){
					/*String str="studentid="+user.getStudentid()+",money="+m2+",订单结算值="+m1+"<br>";
					relist.add(str);*/
					//System.out.println("coachid="+user.getCoachid()+",coinnum="+m2+",订单结算值="+m1+"<br>");
					dataDao.updateBySql("update t_user_coach_copy2 set coinnum2="+m1+" where coachid="+user.getCoachid());
				}
			}
			
		}
		return relist;
	}
	/**
	 * 查询有异常的学员小巴币
	 */
	public List findStudentCoinException(){
		List relist=new ArrayList();
		String hql="from SuserInfo";
		List<SuserInfo> list=(List<SuserInfo>) dataDao.getObjectsViaParam(hql, null);
		for (SuserInfo user : list) {
			if(user!=null){
				if(user.getCoinnum()==null){
					user.setCoinnum(new BigDecimal(0).intValue());
				}
				if(user.getFcoinnum()==null){
					user.setFcoinnum(new BigDecimal(0));
				}
				int m1[]=getStudentCoin(user.getStudentid());
				int m2=user.getCoinnum().intValue();
				int m3=user.getFcoinnum().intValue();
				if(m1[0]!=m2 || m2<0 || m1[0]<0 ||m1[1]<0 ||m1[1]!=user.getFcoinnum().intValue() ||m3<0){
					/*String str="studentid="+user.getStudentid()+",money="+m2+",订单结算值="+m1+"<br>";
					relist.add(str);*/
					//System.out.println("studentid="+user.getStudentid()+",可用小巴币="+m2+",fcoinnum="+m3+",订单结算值 小巴币="+m1[0]+"，订单冻结小巴币="+m1[1]);
					dataDao.updateBySql("update t_user_student_copy2 set coinnum2="+m1[0]+", fcoinnum="+m1[1]+" where studentid="+user.getStudentid());
				}
			}
			
		}
		return relist;
	}
}
