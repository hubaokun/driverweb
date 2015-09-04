package com.weixin.action;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.util.SystemOutLogger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
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

@WebServlet("/weixinl")
public class WloginAction extends BaseServlet{
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String TOKEN = "mZHlAgNp3zqhNh";
		 // ΢�ż���ǩ��
        String signature = request.getParameter("signature");
        // ����ַ���
        String echostr = request.getParameter("echostr");
        // ʱ���
        String timestamp = request.getParameter("timestamp");
        // �����
        String nonce = request.getParameter("nonce");

        String[] str = { TOKEN, timestamp, nonce };
        Arrays.sort(str); // �ֵ�������
        String bigStr = str[0] + str[1] + str[2];
        // SHA1����
        MessageDigest md;
        String digest="";
		try {
			md = MessageDigest.getInstance("SHA-1");
			digest = CommonUtils.byteToString(md.digest(bigStr.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        

        // ȷ����������΢��
        if (digest.equals(signature)) {
            response.getWriter().print(echostr);
        }
     //   response.sendRedirect("weixinWeb/login.jsp");
	}
	
}
