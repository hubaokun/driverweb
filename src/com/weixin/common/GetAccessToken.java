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
	private int tokencount=0;
	private int ticketcount=0;
	//ÿ5���Ӽ��һ��token�Ƿ����
	@Scheduled(cron = "0 0/5 * * * ?")
	public void checktoken() {
		System.out.println("##################΢��token��ʱ����ִ�п�ʼ#################");
		IGetYouWanna WXmessageService=new GetYouWannaImpl();
		if(WXmessageService.getAccessToken())
		{
			if(WXmessageService.getjsapi_ticket(WeiXinMessage.getValue("service_access_token")))
			{
				System.out.println("##################΢��token��ʱ����ִ�н���#################");
			}
			else
			{
				if(ticketcount==3)
				{
					System.out.println("ticketcount="+ticketcount+"��������ӿ�ʧ��,���Ժ�����!");
					return;
				}
				ticketcount++;
				checktoken();
			}
		}
		else
		{
			if(tokencount==3)
			{
				System.out.println("tokencount="+tokencount+"��������ӿ�ʧ��,���Ժ�����!");
				return;
			}
			tokencount++;
			checktoken();
		}

		
		
	}
}
