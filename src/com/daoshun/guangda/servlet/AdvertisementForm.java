package com.daoshun.guangda.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.daoshun.common.CommonUtils;
import com.daoshun.common.Constant;
import com.daoshun.guangda.pojo.SignUpInfoFromAds;
import com.daoshun.guangda.service.ISignUpInfoFromAdsService;

@WebServlet("/AdvertisementForm")
public class AdvertisementForm extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private ISignUpInfoFromAdsService signUpInfoFromAdsService;
	
	public static final int SIGNUP_SUCCESS=1;
	public static final int SIGNUP_FAILED=-1;
	
	//操作状态返回值
	private int code=0;
	
    public AdvertisementForm() {
        super();
    }
    @Override
    public void init(ServletConfig config)throws ServletException
    {
    	super.init(config);
    	signUpInfoFromAdsService=(ISignUpInfoFromAdsService)applicationContext.getBean("signUpInfoFromAdsService");
    }

    /**
     * 广告报名  /AdvertisementForm?action=ADVERTISEMENT_ACTION
     * 报名action /AdvertisementForm?action=ADVERTISEMENT_SIGNUP_ACTION
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String action=request.getParameter("action");
	    if(!CommonUtils.isEmptyString(action))
	    {
	    	if(Constant.ADVERTISEMENT_ACTION.equals(action))
	    	{
	    		request.getRequestDispatcher("advertisement2999.jsp").forward(request, response);
	    		
	    	}else if(Constant.ADVERTISEMENT_SIGNUP_ACTION.equals(action))
	    	{
	    		doForm(request);
	    		request.setAttribute("code",code);
	    		request.getRequestDispatcher("/AdvertisementForm?action=ADVERTISEMENT_ACTION").forward(request, response);
	    	}
	    }
	
	}

	private void doForm(HttpServletRequest request)
	{
		String name=request.getParameter("name");
		String phone=request.getParameter("phoneNum");
		Date addTime=new Date();
		String advertiseName="学车必须这个价 2999";
		
		if(!CommonUtils.isEmptyString(name) && !CommonUtils.isEmptyString(phone))
		{
			SignUpInfoFromAds signUpInfo=new SignUpInfoFromAds();
			signUpInfo.setName(name);
			signUpInfo.setPhone(phone);
			signUpInfo.setAddtime(addTime);
			signUpInfo.setState(ISignUpInfoFromAdsService.STATE_NOT_PROCESSED);
			signUpInfo.setAdsTypeName(advertiseName);
			signUpInfoFromAdsService.addSignUpInfo(signUpInfo);
			code=SIGNUP_SUCCESS;
			
		}else
		{
			code=SIGNUP_FAILED;
			//TODO 返回值: 失败
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	public int getCode()
	{
		return code;
	}
	public void setCode(int code)
	{
		this.code = code;
	}
	public static int getSignupSuccess()
	{
		return SIGNUP_SUCCESS;
	}
	public static int getSignupFailed()
	{
		return SIGNUP_FAILED;
	}

}
