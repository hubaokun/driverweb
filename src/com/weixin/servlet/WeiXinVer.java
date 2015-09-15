package com.weixin.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.poi.util.SystemOutLogger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.daoshun.common.CommonUtils;
import com.daoshun.common.QueryResult;
import com.daoshun.guangda.action.BaseAction;
import com.daoshun.guangda.pojo.RecommendInfo;
import com.daoshun.guangda.service.ICUserService;
import com.daoshun.guangda.service.IRecommendService;
import com.daoshun.guangda.service.ISUserService;
import com.daoshun.guangda.service.ISystemService;
import com.daoshun.guangda.servlet.BaseServlet;
import com.weixin.service.IGetYouWanna;

@WebServlet("/weixinver")
public class WeiXinVer extends BaseServlet{
	IGetYouWanna wxmessageService;
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		wxmessageService=(IGetYouWanna)applicationContext.getBean("WXmessageService");
	}
	
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		if (request.getMethod().equals("GET"))
		{
			Verification(request,response);
		}
		else
		{
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			InputStream is=request.getInputStream();
			SAXReader saxReader = new SAXReader();
			Document document;
			String ToUserName="";
			String FromUserName="";
			String CreateTime="";
			String MsgType="";
			try {
				document = saxReader.read(is);
				Element root = document.getRootElement();
				List<Element> elements=root.elements();
				for(Element e:elements)
				{
					System.out.println(e.getName()+" : "+e.getText());
					if(e.getName().equals("ToUserName"))
						ToUserName=e.getText();
					if(e.getName().equals("FromUserName"))
						FromUserName=e.getText();
					if(e.getName().equals("CreateTime"))
						CreateTime=e.getText();
					if(e.getName().equals("MsgType"))
						MsgType=e.getText();
				}
				//System.out.println(root.getName());
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
			
			
			if(MsgType.equals("text"))
			{
				String xml="<xml><ToUserName><![CDATA["+FromUserName+"]]></ToUserName><FromUserName><![CDATA["+ToUserName+"]]></FromUserName><CreateTime>"+CreateTime+"</CreateTime><MsgType><![CDATA[transfer_customer_service]]></MsgType></xml>";
				PrintWriter pw=response.getWriter();
				is.close();
				pw.write(xml);
				pw.close();
			}
			
		}
		
        
	}
    public void Verification(HttpServletRequest request, HttpServletResponse response) throws IOException{
    	String TOKEN = "mZHlAgNp3zqhNh";
		 // 微信加密签名
       String signature = request.getParameter("signature");
       // 随机字符串
       String echostr = request.getParameter("echostr");
       // 时间戳
       String timestamp = request.getParameter("timestamp");
       // 随机数
       String nonce = request.getParameter("nonce");
      
     
       String[] str = { TOKEN, timestamp, nonce };
       Arrays.sort(str); // 字典序排序
       String bigStr = str[0] + str[1] + str[2];
       // SHA1加密
       MessageDigest md;
       String digest="";
		try {
			md = MessageDigest.getInstance("SHA-1");
			digest = CommonUtils.byteToString(md.digest(bigStr.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       

       // 确认请求来至微信
       if (digest.equals(signature)) {
           response.getWriter().print(echostr);
       }
    }
	
}
