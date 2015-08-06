package com.daoshun.guangda.action;

import com.daoshun.common.CommonUtils;
import com.daoshun.common.QueryResult;
import com.daoshun.common.UserType;
import com.daoshun.guangda.pojo.CoinRecordInfo;
import com.daoshun.guangda.pojo.CouponInfo;
import com.daoshun.guangda.pojo.CouponRecord;
import com.daoshun.guangda.pojo.CuserInfo;
import com.daoshun.guangda.pojo.SuserInfo;
import com.daoshun.guangda.service.ICUserService;
import com.daoshun.guangda.service.ICoinRecordService;
import com.daoshun.guangda.service.ICouponService;
import com.daoshun.guangda.service.ISUserService;
import com.daoshun.guangda.serviceImpl.CoinRecordServiceImpl;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import javax.annotation.Resource;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by tutu on 15/7/24.
 */
public class CoinRecordAction extends BaseAction{


    @Resource
    private ICoinRecordService coinRecordService;

    @Resource
    private ISUserService suserService;
    
    @Resource
    private ICUserService cuserService;

    private Integer coinrecordid;

    // 小巴币的支付者id
    private Integer payerid;

    // 支付者类型 0:平台 1:驾校 2:教练
    private int payertype;

    // 小巴币的接受者id
    private Integer receiverid;
    
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
    
    
    
    
    
   
    

    

    


    

    @Action(value = "goGrantCoinRecord", results = { @Result(name = SUCCESS, location = "/coinGrant.jsp") })
    public String goGrantCoinRecord() {
    	
    	QueryResult<CoinRecordInfo> result = coinRecordService.getCoinRecordListByPage(pageIndex, 10, starttime, endtime, ownertype, String.valueOf(ownerid));
		
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




    @Action(value = "grantCoinRecord")
    public void grantCoinRecord() {

        CoinRecordInfo coinRecordInfo = new CoinRecordInfo ();
        coinRecordInfo.setReceiverid(receiverid);
        coinRecordInfo.setReceivername(receivername);
        coinRecordInfo.setReceivertype(UserType.STUDENT);//代表学员
        if(ownertype==UserType.PLATFORM)//平台发放
        {
            coinRecordInfo.setOwnerid(0);
            //coinRecordInfo.setPayerid(0);
        }
        else//教练发放
        {
            coinRecordInfo.setOwnerid(ownerid);
            //添加教练姓名
            CuserInfo cuser =cuserService.getCoachByid(ownerid);
            coinRecordInfo.setOwnername(cuser.getRealname());
            
           
        }
        coinRecordInfo.setPayerid(0);
        coinRecordInfo.setType(UserType.PLATFORM);
        coinRecordInfo.setOwnertype(ownertype);
        coinRecordInfo.setCoinnum(coinnum);
        coinRecordInfo.setType(1);
        coinRecordInfo.setAddtime(new Date());
        
        coinRecordService.addCoinRecord(coinRecordInfo);

        SuserInfo suser =suserService.getUserById(receiverid.toString());
        if(suser!=null){
        	 if(suser.getCoinnum()==null){
        		 suser.setCoinnum(coinnum);
        	 }else{
        		 suser.setCoinnum(suser.getCoinnum()+coinnum);
        	 }
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
    
    

    
    





}
