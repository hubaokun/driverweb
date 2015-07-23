package com.daoshun.guangda.service;

import java.math.BigDecimal;
import java.util.List;

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
	 * 检测是否还有记录
	 *  @param coachid 教练ID
	 *  @param page    当前页
	 */
	public abstract int ifhasmoreRecommendinfo(String coachid,int page);
} 
