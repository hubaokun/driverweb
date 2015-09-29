package com.daoshun.common;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.daoshun.guangda.dao.DataDao;
import com.daoshun.guangda.pojo.UserPushInfo;

/**
 * app消息推送发送线程池任务
 *  
 * @author 卢磊
 * @date 2015-09-28
 * @version 1.0
 */
@Component("sendPushThreadTask")
public class SendPushThreadTask {
	private static Logger logger = Logger.getLogger(SendPushThreadTask.class
			.getName());
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	
	public String  callSendPushMsg(final String pushMsg,final String studentid,final UserPushInfo userPushInfo){
		String add="";
		Future<String> task = taskExecutor.submit(new Callable<String>(){
			public String call() throws Exception {
				String re=sendPush(pushMsg,studentid,userPushInfo);
				return re;
			}
		});
		try {
			add=task.get();
		} catch (InterruptedException e) {
			//打印异常信息
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return add;
	}
	/**
	 * 服务器向APP推送消息
	 * @param pushMsg 
	 * @param studentid
	 */
	public String sendPush(String pushMsg,String studentid,UserPushInfo userPushInfo){
		String hql5 = "from UserPushInfo where userid =:userid and usertype = 1";
		String params5[] = { "userid" };
		//UserPushInfo userPushInfo = (UserPushInfo) dataDao.getFirstObjectViaParam(hql5, params5,CommonUtils.parseInt(studentid, 0));
		if (userPushInfo != null && userPushInfo.getDevicetoken() != null) {
			if (userPushInfo.getType() == DeviceType.ANDROID && !CommonUtils.isEmptyString(userPushInfo.getJpushid())) {
				PushtoSingle push = new PushtoSingle();
				push.pushsingle(userPushInfo.getJpushid(), 2, "{\"message\":\"" + pushMsg + "\",\"type\":\"5\"}");
			} else if (userPushInfo.getType() == DeviceType.IOS && !CommonUtils.isEmptyString(userPushInfo.getDevicetoken())) {
				ApplePushUtil.sendpush(userPushInfo.getDevicetoken(), "{\"aps\":{\"alert\":\"" + pushMsg + "\",\"sound\":\"default\"},\"userid\":" + studentid + "}", 1, 2);
			}
		}
		return "ok";
	}
}
