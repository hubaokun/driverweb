package com.daoshun.guangda.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.daoshun.common.CommonUtils;
import com.daoshun.guangda.pojo.SignUpInfoFromAds;
import com.daoshun.guangda.service.ISignUpInfoFromAdsService;

@ParentPackage("default")
@Controller
@Scope("prototype")
public class SignUpInfoFromAdsAction extends BaseAction
{
	@Resource
	private ISignUpInfoFromAdsService signUpInfoFromAdsService;
	
	//**data for getSignUpInfoListFromAds**//
	
	private int state;                 //状态 0:未处理  1：已处理
	private int  isAbandoned;          //是否作废  0:未作废  1:已作废
	private Date addtimeRangeLeft;	   //添加时间区间（左）
	private Date addtimeRangeRight;    //添加时间区间（右）
	private Date processTimeRangeLeft; //处理时间区间（左）
	private Date processTimeRangeRight;//处理时间区间（右）
    private  String catg;
	private List<SignUpInfoFromAds> signUpList;
	List<String>catgs;
	//**data for getSignUpInfoListFromAds**//
	
	//**data for processup**//
	
	private int signUpId;   //  报名的ID
	private String processinfo;
	//**data for processup**//
	
	//**data for delete**//
	private int deleteInfoId;
	
	//** data for recover**//
	private int recoverSignId;
	
	// ** data for detail**//
	
	private int signDetailId;
	private SignUpInfoFromAds signUpDetail;
	
	@Action(value = "/getSignUpInfoListFromAds", results = { @Result(name = SUCCESS, location = "/signUpFromAds.jsp") })
	public String getSignUpInfoListFromAds()
	{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		//添加时间区间
		String addTimeLeft=null;
		String addTimeRight=null;
		
		//处理时间区间
		String processTimeLeft=null;
		String processTimeRight=null;
		
		if(addtimeRangeLeft!=null)
		{
			addTimeLeft=sdf.format(addtimeRangeLeft);
		}
		if(addtimeRangeRight!=null)
		{
			addTimeRight=sdf.format(addtimeRangeRight);
		}
		
		if (processTimeRangeLeft!=null)
		{
			processTimeLeft=sdf.format(processTimeRangeLeft);
		}
		if(processTimeRangeRight!=null)
		{
			processTimeRight=sdf.format(processTimeRangeRight);
		}
		
		signUpList=signUpInfoFromAdsService.getSignUpListByCondition(catg,state, isAbandoned, addTimeLeft, addTimeRight, processTimeLeft, processTimeRight);
		catgs=signUpInfoFromAdsService.getCatgs();
		return SUCCESS;
	}
	
	@Action(value = "/processSignUp", results = { @Result(name = SUCCESS,type="redirectAction", location = "signUpDetailFromAds.do?signDetailId=${signUpId}") })
	public String processSignUp()
	{
		HttpSession session = ServletActionContext.getRequest().getSession();
		int serverId=(int)session.getAttribute("userid");
		if(serverId!=0)
		{
			signUpInfoFromAdsService.processInfo(signUpId, processinfo, serverId);
		}

		return SUCCESS;
	}
	
	@Action(value = "/deleteSignUp", results = { @Result(name = SUCCESS,type="redirectAction",location = "getSignUpInfoListFromAds.do") })
	public String deleteSignUp()
	{
		signUpInfoFromAdsService.deleteInfo(deleteInfoId);
		return SUCCESS;
	}
	
	@Action(value = "/recoverSignUp", results = { @Result(name = SUCCESS,type="redirectAction",location = "getSignUpInfoListFromAds.do") })
	public String recoverSignUp()
	{
		signUpInfoFromAdsService.recoverInfo(recoverSignId);
		return SUCCESS;
	}
	
	@Action(value = "/signUpDetailFromAds", results = { @Result(name = SUCCESS, location = "/signUpFromAdsDetail.jsp") })
	public String signUpDetailFromAds()
	{
		signUpDetail=signUpInfoFromAdsService.getSignUpDetails(signDetailId);
		return SUCCESS;
	}
	
	@Action(value = "/getAdsCatgName", results = { @Result(name = SUCCESS, location = "advertisement2999.jsp") })
	public String getAdsCatgName()
	{
		List<String>catgs=signUpInfoFromAdsService.getCatgs();
		//TODO 获取所有广告名称
		return SUCCESS;
	}
	
	
	public int getState()
	{
		return state;
	}

	public void setState(int state)
	{
		this.state = state;
	}

	public int getIsAbandoned()
	{
		return isAbandoned;
	}

	public void setIsAbandoned(int isAbandoned)
	{
		this.isAbandoned = isAbandoned;
	}

	public Date getAddtimeRangeLeft()
	{
		return addtimeRangeLeft;
	}

	public void setAddtimeRangeLeft(Date addtimeRangeLeft)
	{
		this.addtimeRangeLeft = addtimeRangeLeft;
	}

	public Date getAddtimeRangeRight()
	{
		return addtimeRangeRight;
	}

	public void setAddtimeRangeRight(Date addtimeRangeRight)
	{
		this.addtimeRangeRight = addtimeRangeRight;
	}

	public Date getProcessTimeRangeLeft()
	{
		return processTimeRangeLeft;
	}

	public void setProcessTimeRangeLeft(Date processTimeRangeLeft)
	{
		this.processTimeRangeLeft = processTimeRangeLeft;
	}

	public Date getProcessTimeRangeRight()
	{
		return processTimeRangeRight;
	}

	public void setProcessTimeRangeRight(Date processTimeRangeRight)
	{
		this.processTimeRangeRight = processTimeRangeRight;
	}

	public List<SignUpInfoFromAds> getSignUpInfo()
	{
		return signUpList;
	}

	public void setSignUpInfo(List<SignUpInfoFromAds> signUpInfo)
	{
		this.signUpList = signUpInfo;
	}

	public List<SignUpInfoFromAds> getSignUpList()
	{
		return signUpList;
	}

	public void setSignUpList(List<SignUpInfoFromAds> signUpList)
	{
		this.signUpList = signUpList;
	}

	public int getSignUpId()
	{
		return signUpId;
	}

	public void setSignUpId(int signUpId)
	{
		this.signUpId = signUpId;
	}

	public String getProcessinfo()
	{
		return processinfo;
	}

	public void setProcessinfo(String processinfo)
	{
		this.processinfo = processinfo;
	}

	public int getDeleteInfoId()
	{
		return deleteInfoId;
	}

	public void setDeleteInfoId(int deleteInfoId)
	{
		this.deleteInfoId = deleteInfoId;
	}

	public int getRecoverSignId()
	{
		return recoverSignId;
	}

	public void setRecoverSignId(int recoverSignId)
	{
		this.recoverSignId = recoverSignId;
	}

	public int getSignDetailId()
	{
		return signDetailId;
	}

	public void setSignDetailId(int signDetailId)
	{
		this.signDetailId = signDetailId;
	}

	public SignUpInfoFromAds getSignUpDetail()
	{
		return signUpDetail;
	}

	public void setSignUpDetail(SignUpInfoFromAds signUpDetail)
	{
		this.signUpDetail = signUpDetail;
	}

	public List<String> getCatgs()
	{
		return catgs;
	}

	public void setCatgs(List<String> catgs)
	{
		this.catgs = catgs;
	}

	public String getCatg()
	{
		return catg;
	}

	public void setCatg(String catg)
	{
		this.catg = catg;
	}

}
