package com.daoshun.guangda.serviceImpl;


import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.daoshun.common.CoinType;
import com.daoshun.common.CommonUtils;
import com.daoshun.common.QueryResult;
import com.daoshun.common.UserType;
import com.daoshun.guangda.pojo.CoinRecordInfo;
import com.daoshun.guangda.pojo.SuserInfo;
import com.daoshun.guangda.service.ICoinRecordService;
import com.daoshun.guangda.service.ISUserService;

/**
 * Created by tutu on 15/7/24.
 */

@Service("CoinService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class CoinRecordServiceImpl extends BaseServiceImpl implements ICoinRecordService {
	@Resource
    private ISUserService suserService;

    @Override
    public void addCoinRecord(CoinRecordInfo coinRecordInfo) {
            dataDao.addObject(coinRecordInfo);
    }


    public  QueryResult<CoinRecordInfo> getCoinRecordListByPage(int type,int pageIndex, int pageSize, String starttime,
    		String endtime,  Integer ownertype,  String ownerid,String receiverid)
    {
        StringBuffer coinsql = new StringBuffer();
        coinsql.append(" from CoinRecordInfo where 1=1 ");
        if (!CommonUtils.isEmptyString(starttime)) {
            coinsql.append(" and addtime > '" + starttime + "'");
        }
 
        if (!CommonUtils.isEmptyString(endtime)) {
            coinsql.append(" and addtime <= '" + endtime + " 23:59:59'");
        }
        if (ownertype != null) {
            coinsql.append(" and ownertype = " + ownertype);
            if (ownertype == 1) {
                if (!CommonUtils.isEmptyString(ownerid)) {
                    coinsql.append(" and ownerid in ( select schoolid from DriveSchoolInfo where name like '%" + ownerid + "%')");
                }
            } else if (ownertype == 2) {
                if (!CommonUtils.isEmptyString(ownerid)) {
                    coinsql.append(" and ownerid in ( select coachid from CuserInfo where realname like '%" + ownerid + "%')");
                }
            }
        }
        
        if(receiverid!=null && !"".equals(receiverid) && !"null".equals(receiverid)){
        	coinsql.append(" and (receiverid="+receiverid);
        	coinsql.append(" or payerid="+receiverid+")");
        	
        }

		 if(type!=0)
        {
        	coinsql.append(" and type="+type);
    	}

        List<CoinRecordInfo> coinRecordList = (List<CoinRecordInfo>) dataDao.pageQueryViaParam(coinsql.toString() + " order by addtime desc", pageSize, pageIndex, null);

        if (coinRecordList != null && coinRecordList.size() > 0) {
            for (CoinRecordInfo coinrecordinfo : coinRecordList) {
                if(coinrecordinfo.getOwnertype() == 0){
                	coinrecordinfo.setOwnername("小巴");
                }
            }
        }
        
        String counthql = coinsql.insert(0, " select count(*) ").toString();
        long count = (Long) dataDao.getFirstObjectViaParam(counthql, null);
        QueryResult<CoinRecordInfo> result = new QueryResult<CoinRecordInfo>(coinRecordList, count);
        return result;
    }
    
    //驾校小巴币发放记录
    public  QueryResult<CoinRecordInfo> getSchoolCoinRecordListByPage(int pageIndex, int pageSize, String starttime,
    		String endtime,  Integer ownertype,  String ownerid,String receiverid)
    {
        StringBuffer coinsql = new StringBuffer();
        coinsql.append(" from CoinRecordInfo where 1=1 ");
        if (!CommonUtils.isEmptyString(starttime)) {
            coinsql.append(" and addtime > '" + starttime + "'");
        }
 
        if (!CommonUtils.isEmptyString(endtime)) {
            coinsql.append(" and addtime <= '" + endtime + " 23:59:59'");
        }
        if (ownertype != null) {
            coinsql.append(" and ownertype = " + ownertype);
            if (ownertype == 1) {
                if (!CommonUtils.isEmptyString(ownerid)) {
                    coinsql.append(" and ownerid = " + ownerid);
                }
            } else if (ownertype == 2) {
                if (!CommonUtils.isEmptyString(ownerid)) {
                    coinsql.append(" and ownerid = " + ownerid);
                }
            }
        }
        
        if(receiverid!=null && !"".equals(receiverid) && !"null".equals(receiverid)){
        	coinsql.append(" and receiverid="+receiverid);
        	coinsql.append(" or payerid="+receiverid);
        	
        }
        System.out.println(coinsql);
        List<CoinRecordInfo> coinRecordList = (List<CoinRecordInfo>) dataDao.pageQueryViaParam(coinsql.toString() + " order by addtime desc", pageSize, pageIndex, null);

        if (coinRecordList != null && coinRecordList.size() > 0) {
            for (CoinRecordInfo coinrecordinfo : coinRecordList) {
                if(coinrecordinfo.getOwnertype() == 0){
                	coinrecordinfo.setOwnername("小巴");
                }
            }
        }
        
        String counthql = coinsql.insert(0, " select count(*) ").toString();
        long count = (Long) dataDao.getFirstObjectViaParam(counthql, null);
        QueryResult<CoinRecordInfo> result = new QueryResult<CoinRecordInfo>(coinRecordList, count);
        return result;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void reclaimCoin(int receiverid) {
    	SuserInfo suser=dataDao.getObjectById(SuserInfo.class, receiverid);
    	if(suser!=null){
    		int coinnum=suserService.getStudentCoin(suser.getStudentid()).intValue();
    		if(coinnum>0){
				//回收即学员向平台支付剩余所有的小巴币
				CoinRecordInfo coinRecordInfo = new CoinRecordInfo ();
		        coinRecordInfo.setReceiverid(0);
		        coinRecordInfo.setReceivertype(UserType.PLATFORM);
		        coinRecordInfo.setReceivername("平台");
		       
		        
		        coinRecordInfo.setPayerid(receiverid);
		        coinRecordInfo.setPayertype(UserType.STUDENT);
		        coinRecordInfo.setPayername(suser.getRealname());
		        coinRecordInfo.setType(CoinType.REFUND);
		        
		        
		        coinRecordInfo.setOwnerid(0);
		        coinRecordInfo.setOwnertype(UserType.PLATFORM);
		        coinRecordInfo.setOwnername("平台");
		        
		        coinRecordInfo.setCoinnum(coinnum);
		        coinRecordInfo.setAddtime(new Date());
		        
		        dataDao.addObject(coinRecordInfo);
		        if(suser.getCoinnum()!=null){
		        	suser.setCoinnum(0);
		        }
		        dataDao.updateObject(suser);
    		}
	        
    	}
	}
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
   	@Override
   	public void reclaimSchoolCoin(int receiverid,int schoolid,String schoolname) {
       	SuserInfo suser=dataDao.getObjectById(SuserInfo.class, receiverid);
       	if(suser!=null){
       		int coinnum=suserService.getStudentCoin(suser.getStudentid()).intValue();
       		if(coinnum>0){
   				//回收即学员向驾校支付剩余所有的小巴币
   				CoinRecordInfo coinRecordInfo = new CoinRecordInfo ();
   		        coinRecordInfo.setReceiverid(schoolid);
   		        coinRecordInfo.setReceivertype(UserType.DRIVESCHOOL);
   		        coinRecordInfo.setReceivername(schoolname);
   		       
   		        
   		        coinRecordInfo.setPayerid(receiverid);
   		        coinRecordInfo.setPayertype(UserType.STUDENT);
   		        coinRecordInfo.setPayername(suser.getRealname());
   		        coinRecordInfo.setType(CoinType.REFUND);
   		        
   		        
   		        coinRecordInfo.setOwnerid(0);
   		        coinRecordInfo.setOwnertype(UserType.PLATFORM);
   		        coinRecordInfo.setOwnername("平台");
   		        
   		        coinRecordInfo.setCoinnum(coinnum);
   		        coinRecordInfo.setAddtime(new Date());
   		        
   		        dataDao.addObject(coinRecordInfo);
   		        if(suser.getCoinnum()!=null){
   		        	suser.setCoinnum(0);
   		        }
   		        dataDao.updateObject(suser);
       		}
   	        
       	}
   	}
    
    
}
