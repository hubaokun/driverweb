package com.daoshun.guangda.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;

import com.daoshun.common.QueryResult;
import com.daoshun.guangda.pojo.AdminInfo;
import com.daoshun.guangda.pojo.BalanceStudentInfo;
import com.daoshun.guangda.pojo.CoachStudentInfo;
import com.daoshun.guangda.pojo.CuserInfo;
import com.daoshun.guangda.pojo.DriveSchoolInfo;
import com.daoshun.guangda.pojo.OrderInfo;
import com.daoshun.guangda.pojo.StudentApplyInfo;
import com.daoshun.guangda.pojo.StudentCheckInfo;
import com.daoshun.guangda.pojo.SuserInfo;
import com.daoshun.guangda.pojo.SuserState;
import com.daoshun.guangda.pojo.VerifyCodeInfo;

public interface ISUserService {

	// 验证登陆验证码的有效性 返回：1.验证码正常 0：验证码不存在或者错误 2:验证码已经过期
	public abstract int checkVerCode(String phone, String vcode);

	public abstract SuserInfo registerUser(String phone, String token,String openid);

	public abstract int sendMessageCode(String phone, String type);

	/**
	 * 根据手机号搜索用户
	 * 
	 * @param phone
	 * @return
	 */
	public abstract SuserInfo getUserByPhone(String phone);

	/**
	 * 根据邀请码搜索用户
	 *
	 * @param invitecode
	 * @return
	 */
	public abstract SuserInfo getUserByInviteCode(String invitecode);

	/**
	 * 根据手机号搜索教练
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
	 * 添加用户跟进
	 * 
	 * @param user
	 * @return
	 */
	public abstract void addSuserState(SuserState usersta);


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
	 * 修改用户跟进状态
	 * 
	 * @param user
	 */
	public abstract void updateUserState(SuserState userstate);

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
	 * 根据studentid搜索用户状态跟进
	 * 
	 * @param studentid
	 * @return
	 */
	public abstract QueryResult<SuserState> getStateByStuid(String studentid,Integer pageIndex, int pagesize);
	
	/**
	 * 根据dealpeopleid搜索处理人信息
	 * 
	 * @param dealpeopleid
	 * @return
	 */
	public abstract AdminInfo getDealpeopleById(String dealpeopleid);


	
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
	public abstract void applyCash(String studentid, String count,String resource);

	/******************************************************************************************/

	/** 得到所有学员信息 **/
	public abstract QueryResult<SuserInfo> getAllSuserInfos(Integer pageIndex, int pagesize);
	
	/** 得到所有报名学员信息 **/
	public abstract QueryResult<SuserInfo> getEnrollSuserInfos(Integer pageIndex, int pagesize);
	
	/** 得到所有已报名学员信息 **/
	public abstract QueryResult<SuserInfo> getEnrolledSuserInfos(Integer pageIndex, int pagesize);
	
	/** 得到所有已删除学员信息 **/
	public abstract QueryResult<SuserInfo> getDeleteSuserInfos(Integer pageIndex, int pagesize);

	public QueryResult<SuserInfo> getStudentByKeyword(String searchname, String searchphone, String minsdate, String maxsdate, Integer pageIndex, int pagesize);
	
	public QueryResult<SuserInfo> getEnrollStudentByKeyword(String searchname, String searchphone, String minsdate, String maxsdate,String minenrollsdate, String maxenrollsdate, Integer pageIndex, int pagesize);
	
	public QueryResult<SuserInfo> getEnrolledStudentByKeyword(String searchname, String searchphone, String minsdate, String maxsdate, String minenrollsdate, String maxenrollsdate,  Integer pageIndex, int pagesize);
	
	public QueryResult<SuserInfo> getDeleteStudentByKeyword(String searchname, String searchphone, String minsdate, String maxsdate, String minenrollsdate, String maxenrollsdate,Integer pageIndex, int pagesize);

	public QueryResult<SuserState> getStudentstateByKeyword(String studentid, Integer pageIndex, int pagesize);
	
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
	public abstract void applyCheckPass(int coachid,int resource);
	/**
	 * 提现申请审核不通过
	 * 
	 * @param coachid
	 * @param type
	 */
	public abstract void applyCheckNoPass(int coachid,int resource);
	/**
	 * 提现申请审核作废
	 * 
	 * @param coachid
	 * @param type
	 */
	public void applyCheckrevocation(int coachid);

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
	public abstract QueryResult<StudentApplyInfo> getApplyRecordList(Integer pageIndex, int pagesize);

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
	public abstract QueryResult<StudentApplyInfo> searchStudentBalance(String searchname, String searchphone, String amount, String inputamount, String minsdate, String maxsdate, Integer pageIndex,
			int pagesize);

	public abstract List<SuserInfo> getStudentList();

	public abstract HashMap<String, Object> recharge(String studentid, String amount,String resource,String ip,String trade_type,String appid) throws IOException;

	public abstract QueryResult<BalanceStudentInfo> searchStudentRecharge(String searchname, String searchphone, String amount, String inputamount, String minsdate, String maxsdate,
			Integer pageIndex, int pagesize);

	public abstract List<SuserInfo> getUserCount();

	public abstract int getSuserAgeByid(int id);
	
	public abstract String getCityByCityid(int id);
	
	public abstract String getSuserSchoolByid(int id);

	public abstract StudentCheckInfo getcoachbycheck(int studentid);

	public abstract QueryResult<CoachStudentInfo> getCoachStudentByKeyword(String coachPhone, String studentPhone, Integer pageIndex, int pagesize);

	public abstract CoachStudentInfo getCoachStudentByPhone(int coachid, int studentid);

	public abstract void addCoachStudent(CoachStudentInfo coachstudent);

	public abstract int getCouponSum(int studentid);

	public abstract int changeCoach(String studentId,String oldCoachId,String newCoachId);

	public int getCanUseCoinnum(String coachid, String studentid) ;
	public int getCanUseCoinnumForDriveSchool(String studentid) ;

	public int getSumCoinnum( String studentid);

	public HashMap<String, Object> getCoinRecordList(String studentid);
	/**
	 * 重置验证码为默认密码
	 */
    public abstract int resetVerCode(String phone,int type);
    
    public QueryResult<SuserInfo> getStudentSchool(Integer pageIndex, int pagesize);
    public QueryResult<SuserInfo> setStudentSchool(Integer pageIndex, int pagesize);
    public int getCanUseCoinnumForPlatform(String coachid, String studentid);
    public HashMap<String, Object> getConsumeAmount(String studentid);
    public HashMap<String, Object> promoRecharge(String studentid, String amount,String resource,String ip,String trade_type,String appid) throws IOException;
    public void addAlipayCallBack(String qs,String ru);
    public void promoEnrollCallback(String out_trade_no);
    public HashMap<String, Object> getCoinAffiliation(String studentid);
    public DriveSchoolInfo getCoinForDriveSchool(int studentid);
    public void addCoinForSettlement(OrderInfo order,CuserInfo cuser,SuserInfo student,int type);
    //检查是否存在此openid的记录
    public abstract String checkopenid(String openid);
    //学员是否拥有参与体验课资格的状态
    public boolean getFreecoursestate(int studentid);
    public int getCoinnumForDriveSchool( String studentid,String coachid);
    public void getFrozenCoinAffiliationException();
    public BigDecimal getCoachCoin(int coachid);
	public BigDecimal getCoachMoney(int coachid);
	public BigDecimal getCoachFrozenMoney(int coachid);
	public BigDecimal getStudentMoney(int studentid);
	public BigDecimal getStudentFrozenMoney(int studentid);
	public BigDecimal getStudentCoin(int studentid);
	public BigDecimal getStudentFrozenCoin(int studentid);
	public List<String> findStudentMoneyAbnormal();
	public List<String> findCoachMoneyAbnormal();
	public List<String> findCoachCoinAbnormal();
	public List<String> findStudentCoinAbnormal();
	public BigDecimal getSettleOrderTotal(int coachid,int studentid);
	public BigInteger getSettleOrderTime(int coachid,int studentid);
	public BigInteger getCanUseCouponNum(int studentid);
	public void addBackstageRecharge(String studentid, String amount,String operatetype,String reason) ;

}
