package com.daoshun.guangda.action;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
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
import com.daoshun.common.QueryResult;
import com.daoshun.common.UserType;
import com.daoshun.guangda.pojo.CoinRecordInfo;
import com.daoshun.guangda.pojo.CuserInfo;
import com.daoshun.guangda.pojo.DriveSchoolInfo;
import com.daoshun.guangda.pojo.SuserInfo;
import com.daoshun.guangda.service.ICUserService;
import com.daoshun.guangda.service.ICoinRecordService;
import com.daoshun.guangda.service.IDriveSchoolService;
import com.daoshun.guangda.service.ISUserService;

/**
 * Created by tutu on 15/7/24.
 */
@ParentPackage("default")
@Controller
@Scope("prototype")
public class CoinRecordAction extends BaseAction{


    @Resource
    private ICoinRecordService coinRecordService;

    @Resource
    private ISUserService suserService;
    
    @Resource
    private ICUserService cuserService;
    @Resource
    private IDriveSchoolService driveSchoolService;

    private Integer coinrecordid;

    // 小巴币的支付者id
    private Integer payerid;

    // 支付者类型 0:平台 1:驾校 2:教练
    private int payertype;

    // 小巴币的接受者id
    private Integer receiverid;
    
    // 登陆驾校id
    private Integer schoolid;
    
    // 小巴币的接受者姓名
    private String receivername;

    // 接受者类型 0:平台 1:驾校 2:教练
    private int receivertype;

    // 小巴币的数量
    private Integer coinnum;

    // 支付类型,由2位数构成，便于将来状态扩展；10~19:驾校或教练发给学员；20~29学员订单支付;30~39教练兑换；40~49退款
    private int type;

    // 发行者类型 0:平台发行 1:驾校发行 2:教练发行
    private Integer ownertype;

    // 发行者ID
    private Integer ownerid;

    // 添加时间
    private Date addtime;

	private Integer pageIndex = 1;
	
    private String starttime;
    
    private String endtime;
    
    private List<CoinRecordInfo> coinrecordlist;
    
    private int total;
    
    private int pageCount;
    
    private List<DriveSchoolInfo> driveSchoollist;
    

    @Action(value = "goGrantCoinRecord", results = { @Result(name = SUCCESS, location = "/coinGrant.jsp") })
    public String goGrantCoinRecord() {
    	
    	QueryResult<CoinRecordInfo> result = coinRecordService.getCoinRecordListByPage(type,pageIndex, 10, starttime, 
    			endtime, ownertype, String.valueOf(ownerid),String.valueOf(receiverid));
		
    	coinrecordlist = result.getDataList();
		total = (int) result.getTotal();
		pageCount = (total + 9) / 10;
		if (pageIndex > 1) {
			if (coinrecordlist == null || coinrecordlist.size() == 0) {
				pageIndex--;
				getCoinrecordlist();
			}
		}
    	
        return SUCCESS;
    }
    
    //驾校发放小巴币
    @Action(value = "goSchoolGrantCoin", results = { @Result(name = SUCCESS, location = "/schoolCoinGrant.jsp") })
    public String goSchoolGrantCoin() {
    	HttpSession session = ServletActionContext.getRequest().getSession();
		int schoolid = CommonUtils.parseInt(String.valueOf(session.getAttribute("schoolid")), 0);
		driveSchoollist = cuserService.getDriveSchoolListById(schoolid);
		
        return SUCCESS;
    }
    
    //驾校发放小巴券
    @Action(value = "goSchoolGrantCoupon", results = { @Result(name = SUCCESS, location = "/schoolCouponGrant.jsp") })
    public String goSchoolGrantCoupon() {
    	HttpSession session = ServletActionContext.getRequest().getSession();
		int schoolid = CommonUtils.parseInt(String.valueOf(session.getAttribute("schoolid")), 0);
		if(schoolid!=0 ){
			driveSchoollist = cuserService.getDriveSchoolListById(schoolid);
		}
		
        return SUCCESS;
    }
     //小巴币发放记录
    @Action(value = "goCoinRecord", results = { @Result(name = SUCCESS, location = "/coinrecord.jsp") })
    public String goCoinRecord() {
    	QueryResult<CoinRecordInfo> result = coinRecordService.getCoinRecordListByPage(type,pageIndex, 10, starttime, endtime, ownertype, String.valueOf(ownerid),String.valueOf(receiverid));
    	if(receiverid!=null && !"".equals(receiverid) && !"null".equals(receiverid)){
        	SuserInfo suser=suserService.getUserById(String.valueOf(receiverid));
        	coinnum=suserService.getStudentCoin(suser.getStudentid()).intValue();
        	receivername=suser.getRealname();
        }
    	coinrecordlist = result.getDataList();
		total = (int) result.getTotal();
		pageCount = (total + 9) / 10;
		if (pageIndex > 1) {
			if (coinrecordlist == null || coinrecordlist.size() == 0) {
				pageIndex--;
				getCoinrecordlist();
			}
		}
        return SUCCESS;
    }
	//小巴币发放记录类型拆分
    
    //发送给学员的小巴币记录
    @Action(value="goCoinRecordRelease",results={ @Result(name=SUCCESS,  location="goCoinRecord.do?type=1", type="redirect")})
    public String goCoinRecordRelease()
    {
    	return SUCCESS;
    }
    
    //学员支付小巴币给教练记录
    @Action(value="goCoinRecordToCoach",results={ @Result(name=SUCCESS,  location="goCoinRecord.do?type=2", type="redirect")})
    public String goCoinRecordToCoach()
    {
    	return SUCCESS;
    }
    
    //小巴币退款记录
    @Action(value="goCoinRecordRefund",results={ @Result(name=SUCCESS,  location="goCoinRecord.do?type=3", type="redirect")})
    public String goCoinRecordRefund()
    {
    	return SUCCESS;
    }
    
    //教练兑换小巴币记录
    @Action(value="goCoinRecordExchangeByCoach",results={ @Result(name=SUCCESS,  location="goCoinRecord.do?type=4", type="redirect")})
    public String goCoinRecordExchangeByCoach()
    {
    	return SUCCESS;
    }
    
    
    //驾校小巴币发放记录
    @Action(value = "goSchoolCoinRecord", results = { @Result(name = SUCCESS, location = "/schoolcoinrecord.jsp") })
    public String goSchoolCoinRecord() {
    	HttpSession session = ServletActionContext.getRequest().getSession();
		int schoolid = CommonUtils.parseInt(String.valueOf(session.getAttribute("schoolid")), 0);
    	QueryResult<CoinRecordInfo> result = coinRecordService.getSchoolCoinRecordListByPage(pageIndex, 10, starttime, endtime, 1,String.valueOf(schoolid) ,String.valueOf(receiverid));
    	if(receiverid!=null && !"".equals(receiverid) && !"null".equals(receiverid)){
        	SuserInfo suser=suserService.getUserById(String.valueOf(receiverid));
        	coinnum=suserService.getStudentCoin(suser.getStudentid()).intValue();
        	receivername=suser.getRealname();
        }
    	coinrecordlist = result.getDataList();
		total = (int) result.getTotal();
		pageCount = (total + 9) / 10;
		if (pageIndex > 1) {
			if (coinrecordlist == null || coinrecordlist.size() == 0) {
				pageIndex--;
				getCoinrecordlist();
			}
		}
        return SUCCESS;
    }
    
    
    //回收小巴币
    @Action(value = "reclaimCoin", results = { @Result(name = SUCCESS, type = "redirect" ,location = "goCoinRecord.do?receiverid=${receiverid}") })
    public String reclaimCoin() {
    	coinRecordService.reclaimCoin(receiverid);
        return SUCCESS;
    }
    
  //回收驾校发放小巴币
    @Action(value = "reclaimSchoolCoin", results = { @Result(name = SUCCESS, type = "redirect" ,location = "goCoinRecord.do?receiverid=${receiverid}") })
    public String reclaimSchoolCoin() {
    	HttpSession session = ServletActionContext.getRequest().getSession();
		int schoolid = CommonUtils.parseInt(String.valueOf(session.getAttribute("schoolid")), 0);
		driveSchoollist = cuserService.getDriveSchoolListById(schoolid);
		String schoolname="";
		for(int i=0;i<driveSchoollist.size();i++){
			DriveSchoolInfo school = driveSchoollist.get(i);
			schoolname = school.getName();
		}
    	coinRecordService.reclaimSchoolCoin(receiverid,schoolid,schoolname);
        return SUCCESS;
    }
    
    //小巴币月报记录
    @Action(value = "CoinReport", results = { @Result(name = SUCCESS, type = "redirect" ,location = "goCoinRecord.do?receiverid=${receiverid}") })
    public String CoinReport() {
    	 
        return SUCCESS;
    }



    @Action(value = "grantCoinRecord")
    public void grantCoinRecord() {

        CoinRecordInfo coinRecordInfo = new CoinRecordInfo ();
        coinRecordInfo.setReceiverid(receiverid);
        coinRecordInfo.setReceivertype(UserType.STUDENT);//代表学员
        SuserInfo suser =suserService.getUserById(receiverid.toString());
        if(suser!=null){
        	 coinRecordInfo.setReceivername(suser.getRealname());
        }
        if(ownertype==UserType.PLATFORM)//平台发放
        {
             coinRecordInfo.setOwnerid(UserType.PLATFORM);
             coinRecordInfo.setOwnername("平台");
             coinRecordInfo.setPayerid(UserType.PLATFORM);
             coinRecordInfo.setPayertype(UserType.PLATFORM);//0 平台  1 驾校  2 教练  3 学员
             coinRecordInfo.setPayername("平台");
        }else if(ownertype==UserType.DRIVESCHOOL){//驾校发放
        	 coinRecordInfo.setOwnerid(ownerid);//驾校ID
        	 coinRecordInfo.setOwnertype(ownertype);
        	 DriveSchoolInfo ds=driveSchoolService.getDriveSchoolInfoByid(ownerid);
        	 if(ds!=null){
        		 coinRecordInfo.setOwnername(ds.getName());//驾校名称
        		 coinRecordInfo.setPayername(ds.getName());
        	 }
        	 /*coinRecordInfo.setPayerid(ownerid);
             coinRecordInfo.setPayertype(UserType.DRIVESCHOOL);*///0 平台  1 驾校  2 教练  3 学员
        	 coinRecordInfo.setPayerid(UserType.PLATFORM);
             coinRecordInfo.setPayertype(UserType.PLATFORM);//0 平台  1 驾校  2 教练  3 学员
             coinRecordInfo.setPayername("平台");
        }else if(ownertype==UserType.COAH){//教练发放
            /* coinRecordInfo.setOwnerid(ownerid);
             coinRecordInfo.setOwnertype(ownertype);
            //添加教练姓名
            CuserInfo cuser =cuserService.getCoachByid(ownerid);
            if(cuser!=null){
            	 coinRecordInfo.setOwnername(cuser.getRealname());
            	 coinRecordInfo.setPayername(cuser.getRealname());
            }
            coinRecordInfo.setPayerid(ownerid);
            coinRecordInfo.setPayertype(UserType.COAH);*///0 平台  1 驾校  2 教练  3 学员
        	
        	coinRecordInfo.setOwnerid(ownerid);
            coinRecordInfo.setOwnertype(ownertype);
           //添加教练姓名
           CuserInfo cuser =cuserService.getCoachByid(ownerid);
           if(cuser!=null){
           	 coinRecordInfo.setOwnername(cuser.getRealname());
           }
           coinRecordInfo.setPayerid(UserType.PLATFORM);
           coinRecordInfo.setPayertype(UserType.PLATFORM);//0 平台  1 驾校  2 教练  3 学员
           coinRecordInfo.setPayername("平台");
        }
        
        coinRecordInfo.setType(1);//type  1 发放给学员    2 学员支付    3 退款    4 教练兑换
        
        coinRecordInfo.setCoinnum(coinnum);
        coinRecordInfo.setAddtime(new Date());
        
        coinRecordService.addCoinRecord(coinRecordInfo);

       
        if(suser!=null){
        	BigDecimal scoinnum = suserService.getStudentCoin(suser.getStudentid());
        	suser.setCoinnum(scoinnum.intValue()+coinnum);
            suserService.updateUserInfo(suser);
        }
       

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("code", 1);
        strToJson(map);

    }


    public Integer getCoinrecordid() {
        return coinrecordid;
    }
	    
	public void setCoinrecordid(Integer coinrecordid) {
        this.coinrecordid = coinrecordid;
    }

    public Integer getPayerid() {
        return payerid;
    }

    public void setPayerid(Integer payerid) {
        this.payerid = payerid;
    }

    public Integer getReceiverid() {
        return receiverid;
    }

    public void setReceiverid(Integer receiverid) {
        this.receiverid = receiverid;
    }
    

    public String getReceivername() {
		return receivername;
	}


	public void setReceivername(String receivername) {
		this.receivername = receivername;
	}


	public Integer getCoinnum() {
        return coinnum;
    }

    public void setCoinnum(Integer coinnum) {
        this.coinnum = coinnum;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Integer getOwnertype() {
        return ownertype;
    }

    public void setOwnertype(Integer ownertype) {
        this.ownertype = ownertype;
    }

    public Integer getOwnerid() {
        return ownerid;
    }

    public void setOwnerid(Integer ownerid) {
        this.ownerid = ownerid;
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

	public ICoinRecordService getCoinRecordService() {
		return coinRecordService;
	}

	public void setCoinRecordService(ICoinRecordService coinRecordService) {
		this.coinRecordService = coinRecordService;
	}

	public ISUserService getSuserService() {
		return suserService;
	}

	public void setSuserService(ISUserService suserService) {
		this.suserService = suserService;
	}
	
	public ICUserService getCuserService() {
		return cuserService;
	}

	public void setCuserService(ICUserService cuserService) {
		this.cuserService = cuserService;
	}

	public int getPayertype() {
		return payertype;
	}

	public void setPayertype(int payertype) {
		this.payertype = payertype;
	}

	public int getReceivertype() {
		return receivertype;
	}

	public void setReceivertype(int receivertype) {
		this.receivertype = receivertype;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public List<CoinRecordInfo> getCoinrecordlist() {
		return coinrecordlist;
	}

	public void setCoinrecordlist(List<CoinRecordInfo> coinrecordlist) {
		this.coinrecordlist = coinrecordlist;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}




	public IDriveSchoolService getDriveSchoolService() {
		return driveSchoolService;
	}




	public void setDriveSchoolService(IDriveSchoolService driveSchoolService) {
		this.driveSchoolService = driveSchoolService;
	}

	public List<DriveSchoolInfo> getDriveSchoollist() {
		return driveSchoollist;
	}

	public void setDriveSchoollist(List<DriveSchoolInfo> driveSchoollist) {
		this.driveSchoollist = driveSchoollist;
	}

	public Integer getSchoolid() {
		return schoolid;
	}

	public void setSchoolid(Integer schoolid) {
		this.schoolid = schoolid;
	}
    
    

    
    





}
