package com.daoshun.guangda.action;

import com.daoshun.common.CommonUtils;
import com.daoshun.guangda.pojo.CoinRecordInfo;
import com.daoshun.guangda.pojo.CouponInfo;
import com.daoshun.guangda.pojo.CuserInfo;
import com.daoshun.guangda.pojo.SuserInfo;
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
public class CoinAction extends BaseAction{


    @Resource
    private ICoinRecordService coinRecordService;

    @Resource
    private ISUserService suserService;

    private Integer coinrecordid;

    // 小巴币的支付者id
    private Integer payerid;

    // 支付者类型 0:平台 1:驾校 2:教练
    private int payertype;

    // 小巴币的接受者id
    private Integer receiverid;

    // 接受者类型 0:平台 1:驾校 2:教练
    private int receivertype;

    // 小巴币的数量
    private Integer coinnum;

    // 支付类型,由2位数构成，便于将来状态扩展；10~19:驾校或教练发给学员；20~29学员订单支付;30~39教练兑换；40~49退款
    private int type;

    // 发行者类型 0:平台发行 1:驾校发行 2:教练发行
    private int ownertype;

    // 发行者ID
    private Integer ownerid;

    // 添加时间
    private Date addtime;



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

    public int getOwnertype() {
        return ownertype;
    }

    public void setOwnertype(int ownertype) {
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





    @Action(value = "getCoinRecordList", results = { @Result(name = SUCCESS, location = "/addCoin.jsp") })
    public String getCoinRecordList() {
        return SUCCESS;
    }




    @Action(value = "grantCoinRecord")
    public void grantCoinRecord() {

        CoinRecordInfo coinRecordInfo = new CoinRecordInfo ();
        coinRecordInfo.setReceiverid(receiverid);
        if(ownertype==0)//平台发放
        {
            coinRecordInfo.setOwnerid(0);
            coinRecordInfo.setPayerid(0);
        }
        else//教练发放
        {
            coinRecordInfo.setOwnerid(ownerid);
            coinRecordInfo.setPayerid(ownerid);
        }
        coinRecordInfo.setOwnertype(ownertype);
        coinRecordInfo.setCoinnum(coinnum);
        coinRecordInfo.setType(1);
        coinRecordInfo.setAddtime(new Date());

        coinRecordService.addCoinRecord(coinRecordInfo);

        SuserInfo suser =suserService.getUserById(receiverid.toString());
        int num = suser.getCoinnum()+coinnum;
        suser.setCoinnum(num);
        suserService.updateUserInfo(suser);

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("code", 1);
        strToJson(map);

    }




}
