package com.daoshun.guangda.service;

import java.util.Date;
import java.util.List;

import com.daoshun.guangda.pojo.SignUpInfoFromAds;

public interface ISignUpInfoFromAdsService
{
	/**
	 *  0:未处理   1:已处理   2:已作废
	 */
	public final static int STATE_NOT_PROCESSED=0;
	public final static int STATE_PROCESSED=1;
	
	public final static int ABANDONED=1;
	public final static int NOT_ABANDONED=0;
	/**
	 * @author ChRx
	 * @param signUpinfo
	 * 添加报名信息
	 */
	public void addSignUpInfo(SignUpInfoFromAds signUpinfo);
	
	/**
	 * @param signId       报名Id
	 * @param processinfo  处理的备注
	 * @param serverId     客服Id
	 * 客服处理
	 */
	public void processInfo(int signId, String processinfo, int serverId);
	/**
	 * 
	 * @param signId
	 * 删除报名信息
	 */
	public void deleteInfo(int signId);
	
	/**
	 * @param signId
	 * 恢复报名信息
	 */
	public void recoverInfo(int signId);
	
	/**
	 * @param signId
	 * 获取报名详情
	 */
	public SignUpInfoFromAds getSignUpDetails(int signId);
	
	/**
	 * @param state 0:未处理   1:已处理   2:已作废
	 * @param addtime 报名时间
	 * @param processTime  处理时间
	 * @return
	 */
	public List<SignUpInfoFromAds> getSignUpListByCondition(String catg, int state, int isAbandoned, String addtimeRangeLeft, String addtimeRangeRight, String processTimeRangeLeft, String processTimeRangeRight);
	
	public List<String>getCatgs();
}
