package com.weixin.common;

import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.daoshun.common.CommonUtils;
import com.daoshun.guangda.serviceImpl.BaseServiceImpl;
import com.daoshun.guangda.servlet.BaseServlet;
import com.weixin.service.IGetYouWanna;
import com.weixin.serviceImpl.GetYouWannaImpl;

@Component
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class GetAccessToken {
	@Scheduled(cron = "0 40 * * * ?")
	public void checktoken() {
		IGetYouWanna WXmessageService=new GetYouWannaImpl();
		WXmessageService.getAccessToken();
	//	WXmessageService.getWebAccessToken(code);
		WXmessageService.getjsapi_ticket(WeiXinMessage.getValue("service_access_token"));
	}
}
