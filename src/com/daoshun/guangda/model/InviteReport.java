package com.daoshun.guangda.model;

import java.math.BigDecimal;
import java.util.Date;

public class InviteReport {
     private Date addtime;
     private int InviteCount;
     private int CheckPassCount;
     private int OrderPassCount;
     private BigDecimal RewardCount;

	public Date getAddtime() {
		return addtime;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	public int getInviteCount() {
		return InviteCount;
	}
	public void setInviteCount(int inviteCount) {
		InviteCount = inviteCount;
	}
	public int getCheckPassCount() {
		return CheckPassCount;
	}
	public void setCheckPassCount(int checkPassCount) {
		CheckPassCount = checkPassCount;
	}
	public int getOrderPassCount() {
		return OrderPassCount;
	}
	public void setOrderPassCount(int orderPassCount) {
		OrderPassCount = orderPassCount;
	}
	public BigDecimal getRewardCount() {
		return RewardCount;
	}
	public void setRewardCount(BigDecimal rewardCount) {
		RewardCount = rewardCount;
	}
     
     
}
