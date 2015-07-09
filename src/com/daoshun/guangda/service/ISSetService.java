package com.daoshun.guangda.service;

import java.util.List;

import com.daoshun.guangda.pojo.NoticesInfo;

public interface ISSetService {
	
	/**
	 * 获取通知列表
	 * @param studentid
	 * @return
	 */
	public abstract List<NoticesInfo> getNoticesList(String studentid,String pagenum);
	
	/**
	 * 查询是否有更多通知 
	 * @param studentid
	 * @param pagenum
	 * @return
	 */
	public abstract Integer getNoticesMore(String studentid,String pagenum);
	
	/**
	 * 删除通知
	 * @param studentid
	 * @param noticeid
	 */
	public abstract void delNotice(String studentid,String noticeid);
	
	/**
	 * 设置已读通知
	 * @param studentid
	 * @param noticeid
	 */
	public abstract void readNotice(String studentid,String noticeid);
	
	/**
	 * 获取未读消息条数
	 * @param studentid
	 * @return
	 */
	public abstract Integer getMessageCount(String studentid);
	
	/**
	 * 意见反馈
	 * @param studentid
	 */
	public abstract void addFeedBack(String studentid,String content,String type);

}
