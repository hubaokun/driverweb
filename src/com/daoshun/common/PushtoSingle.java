package com.daoshun.common;

import java.io.IOException;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.TransmissionTemplate;

public class PushtoSingle {

	public void pushsingle(String cid, int type, String jsonMessage) {
		if (type == 1) {
			// 教练
			IGtPush push = new IGtPush(Constant.CHOST, Constant.CAPPKEY, Constant.CMASTER);
			try {
				push.connect();
				TransmissionTemplate template = transmissionTemplate(type);
				template.setTransmissionContent(jsonMessage);
				SingleMessage message = new SingleMessage();
				message.setOffline(true);
				// 离线有效时间，单位为毫秒，可选
				message.setOfflineExpireTime(24 * 3600 * 1000);
				message.setData(template);
				message.setPushNetWorkType(0); // 可选。判断是否客户端是否wifi环境下推送，1为在WIFI环境下，0为不限制网络环境。
				Target target = new Target();
				target.setAppId(Constant.CAPPID);
				target.setClientId(cid);
				// 用户别名推送，cid和用户别名只能2者选其一
				// String alias = "个";
				// target.setAlias(alias);
				System.out.println("****push message Android ClientId= "+cid+"******message= "+jsonMessage);
				IPushResult ret = push.pushMessageToSingle(message, target);
				System.out.println(ret.getResponse().toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (type == 2) {
			// 学员
			IGtPush push = new IGtPush(Constant.SHOST, Constant.SAPPKEY, Constant.SMASTER);
			try {
				push.connect();
				TransmissionTemplate template = transmissionTemplate(type);
				template.setTransmissionContent(jsonMessage);
				SingleMessage message = new SingleMessage();
				message.setOffline(true);
				// 离线有效时间，单位为毫秒，可选
				message.setOfflineExpireTime(24 * 3600 * 1000);
				message.setData(template);
				message.setPushNetWorkType(0); // 可选。判断是否客户端是否wifi环境下推送，1为在WIFI环境下，0为不限制网络环境。
				Target target = new Target();
				target.setAppId(Constant.SAPPID);
				target.setClientId(cid);
				// 用户别名推送，cid和用户别名只能2者选其一
				// String alias = "个";
				// target.setAlias(alias);
				IPushResult ret = push.pushMessageToSingle(message, target);
				System.out.println(ret.getResponse().toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static TransmissionTemplate transmissionTemplate(int type) {
		TransmissionTemplate template = new TransmissionTemplate();
		if (type == 1) {
			template.setAppId(Constant.CAPPID);
			template.setAppkey(Constant.CAPPKEY);
		}
		if (type == 2) {
			template.setAppId(Constant.SAPPID);
			template.setAppkey(Constant.SAPPKEY);
		}
		// 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
		template.setTransmissionType(2);
		return template;
	}
}