package com.weixin.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.daoshun.common.CommonUtils;
import com.daoshun.guangda.pojo.RechargeRecordInfo;
import com.daoshun.guangda.service.ICUserService;
import com.daoshun.guangda.service.ISUserService;
import com.daoshun.guangda.servlet.BaseServlet;
import com.weixin.service.IGetYouWanna;

@WebServlet("/weixinWeb/weixinpaycb")
public class weixinpayServlet extends BaseServlet{
	private ICUserService cuserService;
	private ISUserService suserService;
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		cuserService = (ICUserService) applicationContext.getBean("cuserService");
		suserService = (ISUserService) applicationContext.getBean("suserService");
	}
	
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{ 
		InputStream is=request.getInputStream();
		String out_trade_no="";
		String transaction_id="";
		String openid="";
		 SAXReader saxReader = new SAXReader();
		 Document document;
	     try {
			document = saxReader.read(is);
			Element root = document.getRootElement();
			List<Element> elements=root.elements();
			for(Element e:elements)
			{
				//System.out.println(e.getName()+"   "+e.getText());
				if(e.getName().equals("out_trade_no"))
				   out_trade_no=e.getText();
				if(e.getName().equals("transaction_id"))
					transaction_id=e.getText();
				if(e.getName().equals("openid"))
					openid=e.getText();
			}
			RechargeRecordInfo info = cuserService.getrechargerecord(out_trade_no);
			suserService.promoEnrollCallback(out_trade_no);
			if(info.getState()==0)
			{
				cuserService.buySuccessbyweixin(out_trade_no, openid, transaction_id);
			}
		    is.close();
		    String result="<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
		    PrintWriter pw=response.getWriter();
		    pw.write(result);
		    pw.flush();
		    pw.close();
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
}
