package com.daoshun.guangda.service;

import java.util.HashMap;
import java.util.List;

import com.daoshun.common.QueryResult;
import com.daoshun.guangda.pojo.BalanceStudentInfo;
import com.daoshun.guangda.pojo.CoachStudentInfo;
import com.daoshun.guangda.pojo.CouponInfo;
import com.daoshun.guangda.pojo.CouponRecord;
import com.daoshun.guangda.pojo.CuserInfo;
import com.daoshun.guangda.pojo.NoticesInfo;
import com.daoshun.guangda.pojo.StudentApplyInfo;
import com.daoshun.guangda.pojo.StudentCheckInfo;
import com.daoshun.guangda.pojo.SuserInfo;
import com.daoshun.guangda.pojo.VerifyCodeInfo;

public interface ISUserService {

	// 验证登陆验证码的有效性 返回：1.验证码正常 0：验证码不存在或者错误 2:验证码已经过期
	public abstract int checkVerCode(String phone, String vcode);

	public abstract SuserInfo registerUser(String phone, String token);

	public abstract int sendMessageCode(String phone, String type);

	/**
	 * 根据手机号搜索用户
	 * 
	 * @param phone
	 * @return
	 */
	public abstract SuserInfo getUserByPhone(String phone);

	/**
	 * 根据手机号搜索用户
	 * 
	 * @param phone
	 * @return
	 */
	public abstract CuserInfo getCoachByPhone(String phone);

	/**
	 * 添加验证码信息
	 * 
	 * @param verifycode
	 * @return
	 */
	public abstract void addVerifyCode(VerifyCodeInfo verifycode);

	/**
	 * 添加学生用户
	 * 
	 * @param user
	 * @return
	 */
	public abstract void addSuserInfo(SuserInfo user);

	/**
	 * 根据qq账号搜索用户
	 * 
	 * @param qq_openid
	 * @return
	 */
	public abstract SuserInfo getUserByqq(String qq_openid);

	/**
	 * 根据微信账号搜索用户
	 * 
	 * @param wx_openid
	 * @return
	 */
	public abstract SuserInfo getUserBywx(String wx_openid);

	/**
	 * 根据微博账号搜索用户
	 * 
	 * @param wb_openid
	 * @return
	 */
	public abstract SuserInfo getUserBywb(String wb_openid);

	/**
	 * 修改用户信息
	 * 
	 * @param user
	 */
	public abstract void updateUserInfo(SuserInfo user);

	/**
	 * 根据id搜索用户
	 * 
	 * @param studentid
	 * @return
	 */
	public abstract SuserInfo getUserById(String studentid);

	public abstract SuserInfo getUserByStudent_cardnum(String student_cardnum);

	public abstract SuserInfo getUserById_cardnum(String id_cardnum);

	
	/**
	 * 修改头像
	 * 
	 * @param user
	 */
	public abstract String changeAvatar(String studentid, String fileid);

	/**
	 * 获取账户余额信息
	 * 
	 * @param studentid
	 * @return
	 */
	public abstract List<BalanceStudentInfo> getMyBalanceList(String studentid);

	/**
	 * 账户充值
	 * 
	 * @param studentid
	 * @param count
	 */
	public abstract void applyCash(String studentid, String count);

	/******************************************************************************************/

	/** 得到所有学员信息 **/
	public abstract QueryResult<SuserInfo> getAllSuserInfos(Integer pageIndex, int pagesize);

	public QueryResult<SuserInfo> getStudentByKeyword(String searchname, String searchphone, String minsdate, String maxsdate, Integer pageIndex, int pagesize);

	/**
	 * 根据手机获取验证码
	 * 
	 * @param phone
	 * @param type
	 * @return
	 */
	public abstract VerifyCodeInfo getVerifiCationByPhone(String phone, String type);

	/**
	 * 获取学生提现申请列表
	 * 
	 * @param pageIndex
	 * @param pagesize
	 * @return
	 */
	public abstract QueryResult<StudentApplyInfo> getStudentApplyList(Integer pageIndex, int pagesize);

	/**
	 * 提现申请审核
	 * 
	 * @param coachid
	 * @param type
	 */
	public abstract void applyCheckPass(int coachid);

	/**
	 * 关键字搜索提现记录
	 * 
	 * @param searchname
	 * @param searchphone
	 * @param amount
	 * @param inputamount
	 * @param minsdate
	 * @param maxsdate
	 * @param pageIndex
	 * @param pagesize
	 * @return
	 */
	public abstract QueryResult<StudentApplyInfo> getCoachApplyBySearch(String searchname, String searchphone, String amount, String inputamount, Integer state, String minsdate, String maxsdate,
			Integer pageIndex, int pagesize);

	/**
	 * 获取历史提现记录
	 * 
	 * @param pageIndex
	 * @param pagesize
	 * @return
	 */
	public abstract QueryResult<BalanceStudentInfo> getApplyRecordList(Integer pageIndex, int pagesize);

	/**
	 * 获取历史充值记录
	 * 
	 * @param pageIndex
	 * @param pagesize
	 * @return
	 */
	public abstract QueryResult<BalanceStudentInfo> getRechargeRecordList(Integer pageIndex, int pagesize);

	/**
	 * 获取提现申请信息
	 * 
	 * @param applyid
	 * @return
	 */
	public abstract StudentApplyInfo getApplyById(int applyid);

	/**
	 * 关键字搜索提现记录
	 * 
	 * @param searchname
	 * @param searchphone
	 * @param amount
	 * @param inputamount
	 * @param minsdate
	 * @param maxsdate
	 * @param pageIndex
	 * @param pagesize
	 * @return
	 */
	public abstract QueryResult<BalanceStudentInfo> searchStudentBalance(String searchname, String searchphone, String amount, String inputamount, String minsdate, String maxsdate, Integer pageIndex,
			int pagesize);

	public abstract List<SuserInfo> getStudentList();

	public abstract HashMap<String, Object> recharge(String coachid, String amount);

	public abstract QueryResult<BalanceStudentInfo> searchStudentRecharge(String searchname, String searchphone, String amount, String inputamount, String minsdate, String maxsdate,
			Integer pageIndex, int pagesize);

	public abstract List<SuserInfo> getUserCount();

	public abstract int getSuserAgeByid(int id);

	public abstract StudentCheckInfo getcoachbycheck(int studentid);

	public abstract QueryResult<CoachStudentInfo> getCoachStudentByKeyword(String coachPhone, String studentPhone, Integer pageIndex, int pagesize);

	public abstract CoachStudentInfo getCoachStudentByPhone(int coachid, int studentid);

	public abstract void addCoachStudent(CoachStudentInfo coachstudent);

	public abstract int getCouponSum(int studentid);
}
