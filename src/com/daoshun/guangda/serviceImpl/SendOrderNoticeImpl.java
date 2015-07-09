package com.daoshun.guangda.serviceImpl;

import java.util.Calendar;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.daoshun.common.ApplePushUtil;
import com.daoshun.common.CommonUtils;
import com.daoshun.common.PushtoSingle;
import com.daoshun.guangda.pojo.OrderInfo;
import com.daoshun.guangda.pojo.OrderNotiRecord;
import com.daoshun.guangda.pojo.UserPushInfo;
import com.daoshun.guangda.service.ISendOrderNotice;

@Component
public class SendOrderNoticeImpl extends BaseServiceImpl implements ISendOrderNotice {

	/**
	 * 每小时执行一次
	 */
	@Transactional(readOnly = false)
	@Scheduled(cron = "0 * * * * ?")
	@Override
	public void sendNoti() {

		Calendar c = Calendar.getInstance();
		// System.out.println("Send Order Niti....When:" + CommonUtils.getTimeFormat(c.getTime(), "yyyy-MM-dd HH:mm:ss"));
		String hql = "from OrderNotiRecord where sendtime = :sendtime";
		String[] params = { "sendtime" };
		List<OrderNotiRecord> list = (List<OrderNotiRecord>) dataDao.getObjectsViaParam(hql, params, c.getTime());
		if (list != null) {
			for (OrderNotiRecord orderNotiRecord : list) {
				int minute1 = orderNotiRecord.getBeforeminute();
				int type = orderNotiRecord.getType();
				int hour = minute1 / 60;
				int minute = minute1 % 60;
				String message = "";
				if (hour != 0 && minute != 0) {
					message = "您有订单还有" + hour + "小时" + minute + "分钟就要开始了哦";
				} else if (hour == 0 && minute != 0) {
					message = "您有订单还有" + minute + "分钟就要开始了哦";
				} else if (hour != 0 && minute == 0) {
					message = "您有订单还有" + hour + "小时就要开始了哦";
				}

				if (type == 1) {
					String hql5 = "from UserPushInfo where userid =:userid and usertype = 1";
					String params5[] = { "userid" };
					UserPushInfo pushInfo = (UserPushInfo) dataDao.getFirstObjectViaParam(hql5, params5, orderNotiRecord.getCoachid());
					if (pushInfo != null) {
						if (pushInfo.getType() == 0 && !CommonUtils.isEmptyString(pushInfo.getJpushid())) {// 安卓
							PushtoSingle pushsingle = new PushtoSingle();
							pushsingle.pushsingle(pushInfo.getJpushid(), 1, "{\"message\":\"" + message + "\",\"type\":\"2\"}");
						} else if (pushInfo.getType() == 1 && !CommonUtils.isEmptyString(pushInfo.getDevicetoken())) {
							ApplePushUtil.sendpush(pushInfo.getDevicetoken(), "{\"aps\":{\"alert\":\"" + message + "\",\"sound\":\"default\"},\"userid\":" + orderNotiRecord.getCoachid() + "}", 1, 1);
						}
					}
				} else if (type == 2) {
					String hql5 = "from UserPushInfo where userid =:userid and usertype = 2";
					String params5[] = { "userid" };
					UserPushInfo pushInfo = (UserPushInfo) dataDao.getFirstObjectViaParam(hql5, params5, orderNotiRecord.getStudentid());
					if (pushInfo != null) {
						if (pushInfo.getType() == 0 && !CommonUtils.isEmptyString(pushInfo.getJpushid())) {// 安卓
							PushtoSingle pushsingle = new PushtoSingle();
							pushsingle.pushsingle(pushInfo.getJpushid(), 2, "{\"message\":\"" + message + "\",\"type\":\"2\"}");
						} else if (pushInfo.getType() == 1 && !CommonUtils.isEmptyString(pushInfo.getDevicetoken())) {
							ApplePushUtil.sendpush(pushInfo.getDevicetoken(), "{\"aps\":{\"alert\":\"" + message + "\",\"sound\":\"default\"},\"userid\":" + orderNotiRecord.getCoachid() + "}", 1, 1);
						}
					}

				} else if (type == 3) {
					String hql5 = "from UserPushInfo where userid =:userid and usertype = 1";
					String params5[] = { "userid" };
					UserPushInfo pushInfo = (UserPushInfo) dataDao.getFirstObjectViaParam(hql5, params5, orderNotiRecord.getCoachid());
					if (pushInfo != null) {
						if (pushInfo.getType() == 0 && !CommonUtils.isEmptyString(pushInfo.getJpushid())) {// 安卓
							PushtoSingle pushsingle = new PushtoSingle();
							pushsingle.pushsingle(pushInfo.getJpushid(), 1, "{\"message\":\"" + message + "\",\"type\":\"2\"}");
						} else if (pushInfo.getType() == 1 && !CommonUtils.isEmptyString(pushInfo.getDevicetoken())) {
							ApplePushUtil.sendpush(pushInfo.getDevicetoken(), "{\"aps\":{\"alert\":\"" + message + "\",\"sound\":\"default\"},\"userid\":" + orderNotiRecord.getCoachid() + "}", 1, 1);
						}
					}

					String hql6 = "from UserPushInfo where userid =:userid and usertype = 2";
					String params6[] = { "userid" };
					UserPushInfo pushInfo1 = (UserPushInfo) dataDao.getFirstObjectViaParam(hql6, params6, orderNotiRecord.getStudentid());
					if (pushInfo1 != null) {
						if (pushInfo1.getType() == 0 && !CommonUtils.isEmptyString(pushInfo1.getJpushid())) {// 安卓
							PushtoSingle pushsingle = new PushtoSingle();
							pushsingle.pushsingle(pushInfo1.getJpushid(), 2, "{\"message\":\"" + message + "\",\"type\":\"2\"}");
						} else if (pushInfo1.getType() == 1 && !CommonUtils.isEmptyString(pushInfo1.getDevicetoken())) {
							ApplePushUtil.sendpush(pushInfo1.getDevicetoken(), "{\"aps\":{\"alert\":\"" + message + "\",\"sound\":\"default\"},\"userid\":" + orderNotiRecord.getCoachid() + "}", 1, 1);
						}
					}
				}
			}
		}
	}
}
