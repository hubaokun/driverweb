package com.daoshun.guangda.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.daoshun.common.QueryResult;
import com.daoshun.guangda.pojo.CuserInfo;
import com.daoshun.guangda.pojo.RecommendInfo;
/**
 *  @author wjr 
 */
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
	public abstract List<RecommendInfo> getRecommendListForServer(int page,int pagesize);
	
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
	public abstract List<RecommendInfo> getInvitedDetails(String coachid,int page,int pagesize);
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
	public abstract void offeredReward(String coachid,String invitedcoachid,int type);
} 
