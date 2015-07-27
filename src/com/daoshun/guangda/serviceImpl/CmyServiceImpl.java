package com.daoshun.guangda.serviceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.daoshun.common.CommonUtils;
import com.daoshun.common.Constant;
import com.daoshun.guangda.NetData.ComplaintNetData;
import com.daoshun.guangda.NetData.EvaluationNetData;
import com.daoshun.guangda.model.StudentInfo;
import com.daoshun.guangda.pojo.CApplyCashInfo;
import com.daoshun.guangda.pojo.CaddAddressInfo;
import com.daoshun.guangda.pojo.CoachStudentInfo;
import com.daoshun.guangda.pojo.ComplaintInfo;
import com.daoshun.guangda.pojo.CouponCoach;
import com.daoshun.guangda.pojo.CsubjectInfo;
import com.daoshun.guangda.pojo.CuserInfo;
import com.daoshun.guangda.pojo.EvaluationInfo;
import com.daoshun.guangda.pojo.NoticesInfo;
import com.daoshun.guangda.pojo.NoticesUserInfo;
import com.daoshun.guangda.pojo.OrderInfo;
import com.daoshun.guangda.pojo.SuserInfo;
import com.daoshun.guangda.service.ICmyService;

/**
 * @author liukn
 * 
 */
@Service("cmyService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class CmyServiceImpl extends BaseServiceImpl implements ICmyService {

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void setPrice(CuserInfo cuser) {
		dataDao.updateObject(cuser);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void addAddress(CaddAddressInfo cadd) {
		dataDao.addObject(cadd);
	}

	@Override
	public CaddAddressInfo getCoachidByaddAddressid(String addressid) {
		CaddAddressInfo cadd = dataDao.getObjectById(CaddAddressInfo.class, CommonUtils.parseInt(addressid, 0));
		return cadd;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void delAddress(CaddAddressInfo cadd) {
		dataDao.deleteObject(cadd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CaddAddressInfo> getAddressInfosByCoachid(String coachid) {
		StringBuffer cmyhql = new StringBuffer();
		cmyhql.append("from CaddAddressInfo where coachid = :coachid ");
		String[] params = { "coachid" };
		List<CaddAddressInfo> cadds = (List<CaddAddressInfo>) dataDao.getObjectsViaParam(cmyhql.toString(), params, CommonUtils.parseInt(coachid, 0));
		return cadds;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void setCurrentAddress(CaddAddressInfo cadd) {
		dataDao.updateObject(cadd);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void addApplyCash(CApplyCashInfo appl) {
		dataDao.addObject(appl);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NoticesInfo> getNoticesInfoByuserid(String userid, int page, int count) {
		StringBuffer cmyhql = new StringBuffer();
		cmyhql.append(" from NoticesInfo where type = 1 and noticeid in (select noticeid from NoticesUserInfo where userid = :userid) order by addtime desc");
		String[] params = { "userid" };
		List<NoticesInfo> noticesInfo = (List<NoticesInfo>) dataDao.pageQueryViaParam(cmyhql.toString(), count, page, params, CommonUtils.parseInt(userid, 0));
		return noticesInfo;
	}

	@Override
	public NoticesUserInfo getNoticeUserInfoByNoticeid(int noticeid) {
		StringBuffer cmyhql = new StringBuffer();
		cmyhql.append("from NoticesUserInfo where noticeid = :noticeid ");
		String[] params = { "noticeid" };
		NoticesUserInfo noticeUserInfo = (NoticesUserInfo) dataDao.getFirstObjectViaParam(cmyhql.toString(), params, noticeid);
		return noticeUserInfo;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void updateNoticesUserInfo(NoticesUserInfo noticesUserInfo) {
		dataDao.updateObject(noticesUserInfo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NoticesInfo> getNoticesInfoByuserid(String userid) {
		StringBuffer cmyhql = new StringBuffer();
		cmyhql.append(" from NoticesInfo where type=1 and noticeid in (select noticeid from NoticesUserInfo where userid = :userid) order by addtime desc");
		String[] params = { "userid" };
		List<NoticesInfo> noticesInfo = (List<NoticesInfo>) dataDao.getObjectsViaParam(cmyhql.toString(), params, CommonUtils.parseInt(userid, 1));
		return noticesInfo;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void delNoticesInfo(NoticesInfo noticeInfo) {
		dataDao.deleteObject(noticeInfo);
	}

	@Override
	public NoticesInfo getNoticesInfoByNoiticeid(int noticeid) {
		NoticesInfo noticeInfo = dataDao.getObjectById(NoticesInfo.class, noticeid);
		return noticeInfo;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void delNoticesUserInfo(NoticesUserInfo noticesUserInfo) {
		dataDao.deleteObject(noticesUserInfo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ComplaintNetData> getComplaintInfoByTo_user(int to_user, int pageNum) {
		StringBuffer cmyhql = new StringBuffer();
		cmyhql.append("select distinct from_user, order_id from ComplaintInfo where to_user = :to_user and type =1");// type=1
		cmyhql.append(" order by addtime desc ");
		String[] params = { "to_user" };
		List<Object[]> ComplaintInfolist = (List<Object[]>) dataDao.pageQueryViaParam(cmyhql.toString(), Constant.COUNT_NUM, pageNum + 1, params, to_user);
		List<ComplaintNetData> ComplaintNetDatalist = new ArrayList<ComplaintNetData>();
		for (Object[] ComplaintInfo : ComplaintInfolist) {
			int userid = Integer.valueOf(ComplaintInfo[0].toString());
			int orderid = Integer.valueOf(ComplaintInfo[1].toString());
			SuserInfo suserInfo = dataDao.getObjectById(SuserInfo.class, userid);
			OrderInfo order = dataDao.getObjectById(OrderInfo.class, orderid);
			// EvaluationInfo evaluationInfo=dataDao.getObjectById(EvaluationInfo.class, orderid);
			if (suserInfo != null && order != null) {
				// 根据信息找到对教练的评价表
				StringBuffer cmyhql2 = new StringBuffer();
				cmyhql2.append("from EvaluationInfo where from_user = :from_user and order_id = :order_id and to_user = :to_user and type =1");// type=1
				cmyhql2.append(" order by addtime desc ");
				String[] params2 = { "from_user", "order_id", "to_user" };
				EvaluationInfo evaluationInfo = (EvaluationInfo) dataDao.getFirstObjectViaParam(cmyhql2.toString(), params2, userid, orderid, to_user);
				ComplaintNetData complaint = new ComplaintNetData();
				complaint.setCoachid(to_user); // 教练id
				complaint.setStudentid(userid); // 学员id
				complaint.setStudentavatar(getFilePathById(suserInfo.getAvatar())); // 学员头像id
				complaint.setName(suserInfo.getRealname()); // 学员真是姓名
				complaint.setPhone(suserInfo.getPhone()); // 学员手机号码
				complaint.setStudentcardnum(suserInfo.getStudent_cardnum()); // 学员证号
				if (evaluationInfo == null) {
					complaint.setScore(0); // 评分
				} else {
					complaint.setScore(evaluationInfo.getScore1()); // 平均分
				}
				complaint.setStarttime(order.getStart_time()); // 开始时间
				complaint.setEndtime(order.getEnd_time()); // 结束时间
				StringBuffer cmyhql1 = new StringBuffer();
				cmyhql1.append("from ComplaintInfo where order_id = :order_id and from_user = :from_user and to_user = :to_user and type = 1 ");
				String[] params1 = { "order_id", "from_user", "to_user" };
				List<ComplaintInfo> ComplaintInfolist1 = (List<ComplaintInfo>) dataDao.getObjectsViaParam(cmyhql1.toString(), params1, orderid, userid, to_user);
				for (int i = 0; i < ComplaintInfolist1.size(); i++) {
					ComplaintInfolist1.get(i).setSet(ComplaintInfolist1.get(i).getReason());// 投诉原因
				}
				complaint.setContentlist(ComplaintInfolist1);
				ComplaintNetDatalist.add(complaint);
			}
		}
		return ComplaintNetDatalist;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ComplaintNetData> getComplaintInfoByFrom_user(int from_user, int pageNum) {
		StringBuffer cmyhql = new StringBuffer();
		cmyhql.append("select distinct to_user, order_id from ComplaintInfo where from_user = :coachid and type = 2 order by addtime desc");// 投诉用户type=2
		String[] params = { "coachid" };
		List<Object[]> ComplaintInfolist = (List<Object[]>) dataDao.pageQueryViaParam(cmyhql.toString(), Constant.COUNT_NUM, pageNum + 1, params, from_user);
		List<ComplaintNetData> ComplaintNetDatalist = new ArrayList<ComplaintNetData>();
		for (Object[] ComplaintInfo : ComplaintInfolist) {
			int userid = Integer.valueOf(ComplaintInfo[0].toString());
			int orderid = Integer.valueOf(ComplaintInfo[1].toString());
			SuserInfo suserInfo = dataDao.getObjectById(SuserInfo.class, userid);
			OrderInfo order = dataDao.getObjectById(OrderInfo.class, orderid);
			if (suserInfo != null && order != null) {
				// 根据信息找到教练对学员的评价表
				StringBuffer cmyhql2 = new StringBuffer();
				cmyhql2.append("from EvaluationInfo where from_user = :from_user and order_id = :order_id and to_user = :to_user and type =2");// type=2
				cmyhql2.append(" order by addtime desc ");
				String[] params2 = { "from_user", "order_id", "to_user" };
				EvaluationInfo evaluationInfo = (EvaluationInfo) dataDao.getFirstObjectViaParam(cmyhql2.toString(), params2, from_user, orderid, userid);
				ComplaintNetData complaint = new ComplaintNetData();
				complaint.setStudentid(userid); // 学员id
				complaint.setStudentavatar(getFilePathById(suserInfo.getAvatar())); // 学员头像id
				complaint.setName(suserInfo.getRealname()); // 学员真是姓名
				complaint.setPhone(suserInfo.getPhone()); // 学员手机号码
				complaint.setStudentcardnum(suserInfo.getStudent_cardnum()); // 学员证号
				if (evaluationInfo == null) {
					complaint.setScore(0); // 评分
				} else {
					complaint.setScore(evaluationInfo.getScore1()); // 评分
				}
				complaint.setStarttime(order.getStart_time()); // 开始时间
				complaint.setEndtime(order.getEnd_time()); // 结束时间
				StringBuffer cmyhql1 = new StringBuffer();
				cmyhql1.append("from ComplaintInfo where order_id = :order_id and from_user = :from_user and to_user = :to_user and type = 2 ");
				String[] params1 = { "order_id", "from_user", "to_user" };
				List<ComplaintInfo> ComplaintInfolist1 = (List<ComplaintInfo>) dataDao.getObjectsViaParam(cmyhql1.toString(), params1, orderid, from_user, userid);
				for (int i = 0; i < ComplaintInfolist1.size(); i++) {
					ComplaintInfolist1.get(i).setSet(ComplaintInfolist1.get(i).getReason());// 投诉原因
				}
				complaint.setContentlist(ComplaintInfolist1);
				ComplaintNetDatalist.add(complaint);
			}
		}
		return ComplaintNetDatalist;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EvaluationNetData> getEvaluationInfoByTo_user(int to_user, int pageNum) {
		StringBuffer cmyhql = new StringBuffer();
		cmyhql.append("select distinct from_user, order_id from EvaluationInfo where to_user = :coachid and type = 1  order by addtime desc");// 评价我 type=1
		String[] params = { "coachid" };
		List<Object[]> EvaluationInfolist = (List<Object[]>) dataDao.pageQueryViaParam(cmyhql.toString(), Constant.COUNT_NUM, pageNum + 1, params, to_user);
		List<EvaluationNetData> evaluationNetDatalist = new ArrayList<EvaluationNetData>();
		for (Object[] EvaluationInfo : EvaluationInfolist) {
			int userid = Integer.valueOf(EvaluationInfo[0].toString());
			int orderid = Integer.valueOf(EvaluationInfo[1].toString());
			SuserInfo suserInfo = dataDao.getObjectById(SuserInfo.class, userid);
			OrderInfo order = dataDao.getObjectById(OrderInfo.class, orderid);
			if (suserInfo != null && order != null) {
				// 根据信息找到对教练的评价表
				StringBuffer cmyhql2 = new StringBuffer();
				cmyhql2.append("from EvaluationInfo where from_user = :from_user and order_id = :order_id and to_user = :to_user and type = 1");// type=1
				cmyhql2.append(" order by addtime desc ");
				String[] params2 = { "from_user", "order_id", "to_user" };
				EvaluationInfo evaluationInfo = (EvaluationInfo) dataDao.getFirstObjectViaParam(cmyhql2.toString(), params2, userid, orderid, to_user);
				EvaluationNetData evaluation = new EvaluationNetData();
				evaluation.setStudentid(userid); // 学员id
				evaluation.setStudentavatar(getFilePathById(suserInfo.getAvatar())); // 学员头像id
				evaluation.setName(suserInfo.getRealname()); // 学员真是姓名
				evaluation.setPhone(suserInfo.getPhone()); // 学员手机号码
				evaluation.setStudentcardnum(suserInfo.getStudent_cardnum()); // 学员证号
				evaluation.setStarttime(order.getStart_time()); // 开始时间
				evaluation.setEndtime(order.getEnd_time()); // 结束时间
				evaluation.setScore(evaluationInfo.getScore1()); // 评分
				evaluation.setContent(evaluationInfo.getContent()); // 评价内容
				evaluationNetDatalist.add(evaluation);
			}
		}
		return evaluationNetDatalist;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EvaluationNetData> getEvaluationInfoByFrom_user(int from_user, int pageNum) {
		StringBuffer cmyhql = new StringBuffer();
		cmyhql.append("select distinct to_user, order_id from EvaluationInfo where from_user = :coachid and type = 2 order by addtime desc");// 评价用户 type=2
		String[] params = { "coachid" };
		List<Object[]> EvaluationInfolist = (List<Object[]>) dataDao.pageQueryViaParam(cmyhql.toString(), Constant.COUNT_NUM, pageNum + 1, params, from_user);
		List<EvaluationNetData> evaluationNetDatalist = new ArrayList<EvaluationNetData>();
		for (Object[] EvaluationInfo : EvaluationInfolist) {
			int userid = Integer.valueOf(EvaluationInfo[0].toString());
			int orderid = Integer.valueOf(EvaluationInfo[1].toString());
			SuserInfo suserInfo = dataDao.getObjectById(SuserInfo.class, userid);
			OrderInfo order = dataDao.getObjectById(OrderInfo.class, orderid);
			if (suserInfo != null && order != null) {
				// 根据信息找到教练对学员的评价表
				StringBuffer cmyhql2 = new StringBuffer();
				cmyhql2.append("from EvaluationInfo where from_user = :from_user and order_id = :order_id and to_user = :to_user and type =2 ");// type=2
				cmyhql2.append(" order by addtime desc ");
				String[] params2 = { "from_user", "order_id", "to_user" };
				EvaluationInfo evaluationInfo = (EvaluationInfo) dataDao.getFirstObjectViaParam(cmyhql2.toString(), params2, from_user, orderid, userid);
				EvaluationNetData evaluation = new EvaluationNetData();
				evaluation.setStudentid(userid); // 学员id
				evaluation.setStudentavatar(getFilePathById(suserInfo.getAvatar())); // 学员头像id
				evaluation.setName(suserInfo.getRealname()); // 学员真是姓名
				evaluation.setPhone(suserInfo.getPhone()); // 学员手机号码
				evaluation.setStudentcardnum(suserInfo.getStudent_cardnum()); // 学员证号
				evaluation.setStarttime(order.getStart_time()); // 开始时间
				evaluation.setEndtime(order.getEnd_time()); // 结束时间
				evaluation.setScore(evaluationInfo.getScore1()); // 评分
				evaluation.setContent(evaluationInfo.getContent()); // 评价内容
				evaluationNetDatalist.add(evaluation);
			}
		}
		return evaluationNetDatalist;
	}

	@Override
	public int allnoticecount(String coachid) {
		StringBuffer cmyhql = new StringBuffer();
		cmyhql.append("select count(*) from NoticesUserInfo where userid = :coachid and noticeid in (select noticeid from NoticesInfo where type = 1) ");
		String[] params = { "coachid" };
		int allnoticecount = Integer.valueOf(dataDao.getFirstObjectViaParam(cmyhql.toString(), params, CommonUtils.parseInt(coachid, 0)).toString());// 查询数量
		return allnoticecount;
	}

	@Override
	public int unnoticecount(String coachid) {
		StringBuffer cmyhql = new StringBuffer();
		cmyhql.append("select count(*) from NoticesUserInfo where userid = :coachid and noticeid in (select noticeid from NoticesInfo where type = 1) and readstate = 0 ");
		String[] params = { "coachid" };
		int noticecount = Integer.valueOf(dataDao.getFirstObjectViaParam(cmyhql.toString(), params, CommonUtils.parseInt(coachid, 0)).toString());// 查询数量
		return noticecount;
	}

	@Override
	public int complaintmy(String coachid) {
		StringBuffer cmyhql = new StringBuffer();
		cmyhql.append("select count(complaintid) from ComplaintInfo where to_user = :coachid and type = 1");
		String[] params = { "coachid" };
		int complaint1 = Integer.valueOf(dataDao.getFirstObjectViaParam(cmyhql.toString(), params, CommonUtils.parseInt(coachid, 0)).toString());// 查询数量
		return complaint1;
	}

	@Override
	public int mycomplaint(String coachid) {
		StringBuffer cmyhql = new StringBuffer();
		cmyhql.append("select count(complaintid) from ComplaintInfo where from_user = :coachid and type = 2");
		String[] params = { "coachid" };
		int complaint2 = Integer.valueOf(dataDao.getFirstObjectViaParam(cmyhql.toString(), params, CommonUtils.parseInt(coachid, 0)).toString());// 查询数量
		return complaint2;
	}

	@Override
	public int evaluationmy(String coachid) {
		StringBuffer cmyhql = new StringBuffer();
		cmyhql.append("select count(evaluationid) from EvaluationInfo where to_user = :coachid and type = 1");
		String[] params = { "coachid" };
		int evaluation1 = Integer.valueOf(dataDao.getFirstObjectViaParam(cmyhql.toString(), params, CommonUtils.parseInt(coachid, 0)).toString());// 查询数量
		return evaluation1;
	}

	@Override
	public int myevaluation(String coachid) {
		StringBuffer cmyhql = new StringBuffer();
		cmyhql.append("select count(evaluationid) from EvaluationInfo where from_user = :coachid and type = 2");
		String[] params = { "coachid" };
		int evaluation2 = Integer.valueOf(dataDao.getFirstObjectViaParam(cmyhql.toString(), params, CommonUtils.parseInt(coachid, 0)).toString());// 查询数量
		return evaluation2;
	}

	@Override
	public CsubjectInfo getSubject(int subjectid) {
		StringBuffer cmyhql = new StringBuffer();
		cmyhql.append("from CsubjectInfo where subjectid = :subjectid ");
		String[] params = { "subjectid" };
		CsubjectInfo subjectInfo = (CsubjectInfo) dataDao.getFirstObjectViaParam(cmyhql.toString(), params, subjectid);
		return subjectInfo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CsubjectInfo> getAllSubject() {
		StringBuffer cmyhql = new StringBuffer();
		cmyhql.append("from CsubjectInfo ");
		List<CsubjectInfo> subjectInfoList = (List<CsubjectInfo>) dataDao.getObjectsViaParam(cmyhql.toString(), null);
		return subjectInfoList;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void updateSubject(CsubjectInfo subject) {
		dataDao.updateObject(subject);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public HashMap<String, Object> updateAliAccount(String userid, String type, String alicount, String cashtype) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		if (CommonUtils.parseInt(type, 0) == 1) {
			CuserInfo cuser = dataDao.getObjectById(CuserInfo.class, CommonUtils.parseInt(userid, 0));
			if (cuser != null) {
				cuser.setAlipay_account(alicount);
				if (!CommonUtils.isEmptyString(cashtype)) {
					cuser.setCashtype(CommonUtils.parseInt(cashtype, 0));
				}
				dataDao.updateObject(cuser);
				result.put("code", 1);
				result.put("message", "提交成功");
				result.put("aliacount", cuser.getAlipay_account());
				result.put("cashtype", cuser.getCashtype());
			} else {
				result.put("code", 2);
				result.put("message", "用户不存在");
			}
		} else {
			SuserInfo suser = dataDao.getObjectById(SuserInfo.class, CommonUtils.parseInt(userid, 0));
			if (suser != null) {
				suser.setAlipay_account(alicount);
				dataDao.updateObject(suser);
				result.put("code", 1);
				result.put("message", "提交成功");
				result.put("aliacount", suser.getAlipay_account());
			} else {
				result.put("code", 2);
				result.put("message", "用户不存在");
			}
		}
		return result;
	}

	@Override
	public List<CouponCoach> getCouponCoachList(String coachid) {
		List<CouponCoach> list = new ArrayList<CouponCoach>();
		String hql = "from CouponCoach where coachid = :coachid and (state = 1 or state=2) order by state asc,gettime desc";
		String[] params = { "coachid" };
		list.addAll((List<CouponCoach>) dataDao.getObjectsViaParam(hql, params, CommonUtils.parseInt(coachid, 0)));
		return list;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public HashMap<String, Object> applyCoupon(String coachid, String recordids) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String ids[] = recordids.split(",");

		if (ids.length == 0) {
			result.put("code", 2);
			result.put("message", "数据错误");
		} else {
			CuserInfo cuser = dataDao.getObjectById(CuserInfo.class, CommonUtils.parseInt(coachid, 0));
			if (cuser != null) {
				for (int i = 0; i < ids.length; i++) {
					int recordid = CommonUtils.parseInt(ids[i], 0);
					CouponCoach cc = dataDao.getObjectById(CouponCoach.class, recordid);
					if (cc != null) {
						cc.setState(2);// 设置状态
						cuser.setFmoney(cuser.getFmoney().add(new BigDecimal(cc.getMoney_value())));// 增加用户冻结金额
						cuser.setMoney(cuser.getMoney().add(new BigDecimal(cc.getMoney_value())));
					}
					dataDao.updateObject(cc);

				}
				dataDao.updateObject(cuser);
				result.put("code", 1);
				result.put("message", "申请提交成功");
				result.put("fmoney", cuser.getFmoney().doubleValue());
				result.put("money", cuser.getMoney().doubleValue());
			} else {
				result.put("code", 3);
				result.put("message", "用户不存在");
			}
		}

		return result;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void setAllMessageReaded(String coachid) {
		// 获取所有未读的消息
		String hql = "from NoticesUserInfo where userid = :userid and noticeid in (select noticeid from NoticesInfo where type = 1)";
		String[] params = { "userid" };
		List<NoticesUserInfo> unreadedlist = (List<NoticesUserInfo>) dataDao.getObjectsViaParam(hql, params, CommonUtils.parseInt(coachid, 0));
		for (NoticesUserInfo info : unreadedlist) {
			info.setReadstate(1);
			dataDao.updateObject(info);
		}
	}

	@Override
	public HashMap<String, Object> getMyAllStudent(String coachid, String pageNum) {
		HashMap<String, Object> returnResult = new HashMap<String, Object>();

		List<StudentInfo> result = new ArrayList<StudentInfo>();
		String hql = "from SuserInfo where studentid in (select studentid from CoachStudentInfo where coachid = :coachid order by money desc)";
		String[] params = { "coachid" };
		List<SuserInfo> suserList = (List<SuserInfo>) dataDao.pageQueryViaParam(hql, 10, CommonUtils.parseInt(pageNum, 1), params, CommonUtils.parseInt(coachid, 0));
		for (SuserInfo suserInfo : suserList) {
			StudentInfo studentInfo = new StudentInfo();
			studentInfo.setAvatar(getFilePathById(suserInfo.getAvatar()));
			studentInfo.setCoachstate(suserInfo.getCoachstate());
			studentInfo.setPhone(suserInfo.getPhone());
			studentInfo.setRealname(suserInfo.getRealname());
			studentInfo.setStudent_cardnum(suserInfo.getStudent_cardnum());
			studentInfo.setStudentid(suserInfo.getStudentid());
			studentInfo.setLearntime(suserInfo.getLearn_time());

			String hql1 = "from CoachStudentInfo where coachid = :coachid and studentid = :studentid";
			String[] params1 = { "coachid", "studentid" };
			CoachStudentInfo info = (CoachStudentInfo) dataDao.getFirstObjectViaParam(hql1, params1, CommonUtils.parseInt(coachid, 0), suserInfo.getStudentid());

			if (info != null) {
				studentInfo.setLearnmytime(info.getHour());
				studentInfo.setMoney(info.getMoney().doubleValue());
			} else {
				studentInfo.setLearnmytime(0);
				studentInfo.setMoney(0);
			}
			result.add(studentInfo);
		}
		returnResult.put("studentList", result);

		List<SuserInfo> nextPage = (List<SuserInfo>) dataDao.pageQueryViaParam(hql, 10, CommonUtils.parseInt(pageNum, 1) + 1, params, CommonUtils.parseInt(coachid, 0));
		if (nextPage != null && nextPage.size() > 0) {
			returnResult.put("hasmore", 1);
		} else {
			returnResult.put("hasmore", 0);
		}

		return returnResult;
	}

	@Override
	public int getMyStudentCount(String coachid) {
		String hql = "from SuserInfo where studentid in (select studentid from CoachStudentInfo where coachid = :coachid)";
		String[] params = { "coachid" };
		List<SuserInfo> suserList = (List<SuserInfo>) dataDao.getObjectsViaParam(hql, params, CommonUtils.parseInt(coachid, 0));

		if (suserList != null) {
			return suserList.size();
		}
		return 0;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public HashMap<String, Object> delAliAccount(String userid, String type) {
		HashMap<String, Object> result = new HashMap<String, Object>();

		if (CommonUtils.parseInt(type, 0) == 1) {// 教练
			CuserInfo cuser = dataDao.getObjectById(CuserInfo.class, CommonUtils.parseInt(userid, 0));
			if (cuser != null) {
				cuser.setAlipay_account("");
				dataDao.updateObject(cuser);
				result.put("code", 1);
				result.put("message", "修改成功");
			} else {
				result.put("code", 2);
				result.put("message", "用户不存在");
			}
		} else {
			SuserInfo suser = dataDao.getObjectById(SuserInfo.class, CommonUtils.parseInt(userid, 0));
			if (suser != null) {
				suser.setAlipay_account("");
				dataDao.updateObject(suser);
				result.put("code", 1);
				result.put("message", "修改成功");
			} else {
				result.put("code", 2);
				result.put("message", "用户不存在");
			}
		}

		return result;
	}

	/**
	 * 修改教练的提现方式
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public HashMap<String, Object> changeApplyType(String coachid, String setvalue) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		int cid = CommonUtils.parseInt(coachid, 0);
		int svalue = CommonUtils.parseInt(setvalue, 0);

		CuserInfo cuser = dataDao.getObjectById(CuserInfo.class, cid);
		if (cuser != null) {
			if (svalue == 0) {
				cuser.setCashtype(svalue);
				result.put("code", 1);
				result.put("message", "修改成功");
				dataDao.updateObject(cuser);
			} else if (svalue == 1) {
				if (cuser.getDrive_schoolid() == null || cuser.getDrive_schoolid() == 0) {
					result.put("code", 3);
					result.put("message", "您必须先选择一个平台下的驾校才能设置驾校取款.");
				} else {
					cuser.setCashtype(svalue);
					result.put("code", 1);
					result.put("message", "修改成功");
					dataDao.updateObject(cuser);
				}
			} else {
				result.put("code", 4);
				result.put("message", "错误的请求参数");
			}
		} else {
			result.put("code", 2);
			result.put("message", "用户参数错误");
		}
		result.put("cashtype", cuser.getCashtype());
		return result;
	}
}
