package com.daoshun.common;

import java.util.ArrayList;
import java.util.List;

import javapns.devices.Device;
import javapns.devices.implementations.basic.BasicDevice;
import javapns.notification.AppleNotificationServerBasicImpl;
import javapns.notification.PushNotificationManager;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotification;

import org.json.JSONException;

public class ApplePushUtil {

	/**
	 * apple的推送方法
	 * 
	 * @param ServletPath
	 *            发布路径request.getRealPath()
	 * @param dbManager
	 *            DB连接
	 * @param userId
	 *            推送的用户ID
	 * @param message
	 *            推送消息的内容
	 * @param count
	 *            应用图标上小红圈上的数值
	 * @param preDeviceToken
	 *            上次登录的device token
	 * 
	 * @return int 1:推送成功 2:推送失败 -1:未知错误 99:对方没用iphone登录，不能推送
	 */
	public static int sendpush(String deviceToken, String message, int count, int type) {

		try {
			PushNotificationPayload payLoad = PushNotificationPayload.fromJSON(message);
			payLoad.addBadge(count); // 图标小红圈的数值
			payLoad.addSound("default");// 铃音 默认
			PushNotificationManager pushManager = new PushNotificationManager();
			// True 产品推送服务器 false 测试的服务器
			if (type == 1) {
				// String path = ApplePushUtil.class.getClassLoader().getResource("guangda_coach_push_dev.p12").getPath();
				// pushManager.initializeConnection(new AppleNotificationServerBasicImpl(path, "123456", false));
				String path = ApplePushUtil.class.getClassLoader().getResource("guangda_coach_push_dis.p12").getPath();
				pushManager.initializeConnection(new AppleNotificationServerBasicImpl(path, "123456", true));
			}
			if (type == 2) {
				// String path = ApplePushUtil.class.getClassLoader().getResource("guangda_student_push_dev.p12").getPath();
				// pushManager.initializeConnection(new AppleNotificationServerBasicImpl(path, "123456", false));
				String path = ApplePushUtil.class.getClassLoader().getResource("guangda_student_push_dis.p12").getPath();
				pushManager.initializeConnection(new AppleNotificationServerBasicImpl(path, "123456", true));
			}

			List<PushedNotification> notifications = new ArrayList<PushedNotification>();
			Device device = new BasicDevice();
			device.setToken(deviceToken);
			PushedNotification notification = pushManager.sendNotification(device, payLoad, true);
			notifications.add(notification);
			List<PushedNotification> failedNotifications = PushedNotification.findFailedNotifications(notifications);
			List<PushedNotification> successNotifications = PushedNotification.findSuccessfulNotifications(notifications);
			int failed = failedNotifications.size();
			int successful = successNotifications.size();
			if (successful > 0 && failed == 0) {
				System.out.println("success:" + successNotifications.toString());
				return 1;
			} else {
				System.out.println("failed:" + failedNotifications.toString());
				return 2;
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return 98;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
}
