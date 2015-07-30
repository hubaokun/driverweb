package com.daoshun.guangda.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.daoshun.common.CommonUtils;
import com.daoshun.common.Constant;
import com.daoshun.common.QueryResult;
import com.daoshun.guangda.model.InviteReport;
import com.daoshun.guangda.pojo.CuserInfo;
import com.daoshun.guangda.pojo.RecommendInfo;
/**
 *  @author wjr 
 */
import com.daoshun.guangda.pojo.SuserInfo;
public interface IRecommendService{
	/**
	 * 获取推荐人员详细内容
	 * @param coachid 教练ID
	 * @param page   当前页
	 */
	public abstract QueryResult<RecommendInfo> getRecommendList(String coachid,int page);	
	/**
	 * 获取推荐获得的总金额
	 *  @param coachid 教练ID
	 */
	public abstract BigDecimal getReward(String coachid);
	/**
	 *  获取所有被推荐人的教练信息
	 *  @param coachs 教练ID列表
	 */
	public abstract List<CuserInfo> getInvitedState(List<Integer> coachs);
	/**
	 *  获取所有被推荐人的开单状况
	 *  @param coachs 教练ID列表
	 */
	public abstract List  getFirstOrderState(List<Integer> coachs);
	/**
	 *  建立推荐人员信息
	 *  @param inviteid 邀请码
	 *  @param invitedcoachid 被邀请教练ID
	 */
	public abstract int addRecommendInfo(String inviteid,String invitedcoachid);
	/**
	 * 检测推荐人员信息是否存在
	 *  @param invitedcoachid 被邀请教练ID
	 */
	public abstract int checkRecommendinfo(String invitedcoachid);
	/**
	 * 检测是否还有推荐记录
	 *  @param coachid 教练ID
	 *  @param page    当前页
	 */
	public abstract int ifhasmoreRecommendinfo(String coachid,int page);
	/**
	 * 获取教练推荐教练列表（后台）
	 * @param page 当前页
	 * @param pagesize  一页数量
	 */
	public abstract QueryResult<RecommendInfo> getRecommendListForServer(int page,int pagesize);
	
	/**
	 * 获取某个教练的总推荐人数
	 * @param coachid 教练ID
	 */
	public abstract int getRecommendCount(String coachid);
	/**
	 * 获取某个教练所推荐的人的认证数以及其开单数
	 * @param coachs 要查询的教练List
	 */
	public abstract Map<String,List> getInviteCheckAndEarnMoneyCount(List<Integer> coachs);
   /**
    * 获取教练邀请详细信息
    * @param coachid 推荐教练ID
    * @param page 当前页
    * @param pagesize 页数
    */
	public abstract QueryResult<RecommendInfo> getInvitedDetailsForServer(String coachid,int page,int pagesize);
	/**
	 * 根据被推荐教练ID获取奖励金额
	 * @param invitedcoachid 被推荐教练ID
	 */
	public abstract BigDecimal getRewardByInvitedcoach(String invitedcoachid);
	/**
	 *  获取被推荐人的开单状况
	 *  @param coachid 教练ID
	 */
	public abstract int  checkOrderState(String coachid);
	/**
	 * 获取推荐人推荐总数
	 *  @param coachid 教练ID
	 */
	public abstract int getInviteCount(Integer coachid);
	/**
	 * 获取推荐人开单总数
	 * @param coachid 教练ID
	 */
	public abstract int getOrderCount(Integer coachid);
	/**
	 * 获取推荐人通过审核总人数
	 * @param coachid 教练ID
	 */
	public abstract int getCheckManCount(Integer coachid);
	/**
	 *  获取被推荐人的审核状况
	 *  @param coachid 教练ID
	 */
	public abstract int  checkRegisterState(String coachid);
	/**
	 * 发放推荐奖励
	 * @param coachid 需要发放奖励的教练ID
	 * @param invitedcoachid 被推荐教练的ID
	 * @param type 奖励类型 0=认证奖励 1=开单奖励
	 */
	public abstract int offeredReward(String coachid,String invitedcoachid,int type,HashMap<String, Object> resultmap);
	/**
	 * 更行推荐表信息
	 */
	public abstract void updateRecommendInfo(RecommendInfo temp);
	/** 根据教练id找到教练分享信息 **/
	public abstract List<RecommendInfo> getRecommondInviteInfoByCoachid(String coachid,String realname);
	/** 根据教练id找到教练被分享信息 **/
	public abstract RecommendInfo getRecommondInvitedInfoByCoachid(String coachid,String realname);
	/**
	 * 根据搜索条件搜索教练推荐信息
	 * @param searchname 查询姓名
	 * @param searchphone 查询电话号码
	 */
	public abstract QueryResult<RecommendInfo> getRecommonedInfoByKeyWord(String searchname,String searchphone);
    /**
     * 判断这个教练是否开课过
     */
	public abstract int judgeTeacheOrNot(String coachid,HashMap<String, Object> resultmap);
	/**
	 *  删除邀请
	 */
	public abstract void deleteRecommonedInfo(String coachid,String invitedcoachid);
	/**
	 * 获取推荐日报
	 */
	public abstract QueryResult<InviteReport> getRecommenReport(Integer pageSize,Integer pageIndex);
	/**
	 * 根据日期获取这一天的邀请总人数
	 */
	public abstract int getInviteCount(Date addtime);
	/**
	 * 根据日期获取这一天的认证总人数
	 */
	public abstract int getCheckPassCount(Date addtime);
	/**
	 * 根据日期获取这一天的开单总人数
	 */
	public abstract int getOrderPassCount(Date addtime);
	/**
	 * 根据日期获取这一天的奖励总金额
	 */
	public abstract BigDecimal getRewardCount(Date addtime);
	/**
	 * 根据搜索条件搜索被推荐教练信息
	 * @param searchname 查询姓名
	 * @param searchphone 查询电话号码
	 * @param coachid    推荐教练ID
	 */
	public abstract QueryResult<RecommendInfo> getRecommoneddetailInfoByKeyWord(String searchname,String searchphone,String coachid);
	/**
	 * 获取正在开课的教练列表
	 */
	public abstract HashMap<String, Object> getCoachList(String condition1, String condition2, String condition3, String condition4, String condition5, String condition6, String condition8, String condition9,
			String condition10, String condition11);
	
	
} 
