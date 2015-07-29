package com.daoshun.guangda.serviceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.daoshun.common.CommonUtils;
import com.daoshun.common.Constant;
import com.daoshun.common.QueryResult;
import com.daoshun.guangda.pojo.AdminInfo;
import com.daoshun.guangda.pojo.BalanceCoachInfo;
import com.daoshun.guangda.pojo.CscheduleInfo;
import com.daoshun.guangda.pojo.CuserInfo;
import com.daoshun.guangda.pojo.OrderInfo;
import com.daoshun.guangda.pojo.RecommendInfo;
import com.daoshun.guangda.pojo.SystemSetInfo;
import com.daoshun.guangda.service.IRecommendService;
/**
 * 
 * @author wjr  推荐分享服务实现类
 */
@Service("recommendService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class RecommendServiceImpl extends BaseServiceImpl implements IRecommendService {


	@Override
	//获取教练推荐人员列表
	public QueryResult<RecommendInfo> getRecommendList(String coachid,int page) {
	    StringBuilder querystring=new StringBuilder();
	    querystring.append("from RecommendInfo where coachid=:coachid order by coachid");
	    String[] params={"coachid"};
		List<RecommendInfo> listr=(List<RecommendInfo>)dataDao.pageQueryViaParam(querystring.toString(), Constant.USERLIST_SIZE, page, params,CommonUtils.parseInt(coachid, 0));
		/*for(RecommendInfo temp:listr)
		{
			System.out.println(temp.getInvitedcoachid());
		}*/
		String querys="select count(*)"+querystring.toString();
		long total=(Long) dataDao.getFirstObjectViaParam(querys, params, CommonUtils.parseInt(coachid, 0));
		if(listr!=null)
		    return new QueryResult<RecommendInfo>(listr,total);
		else
			return new QueryResult<RecommendInfo>(null,0);
	}
   //获取所得奖励总额
	@Override
	public BigDecimal getReward(String coachid) {
		String querystring="select sum(reward) from RecommendInfo where coachid=:coachid";
	    String[] params={"coachid"};
	    BigDecimal sumreward=(BigDecimal)dataDao.getFirstObjectViaParam(querystring, params, CommonUtils.parseInt(coachid, 0));
		return sumreward;
	}
   
	@Override
	public List<CuserInfo> getInvitedState(List<Integer> coachs) {
		String querystring="from CuserInfo where coachid in(:coachs) order by coachid";
		String[] params={"coachs"};
		List<CuserInfo> querylist=(List<CuserInfo>) dataDao.getObjectsViaParam(querystring, params, coachs);
		return querylist;
	}
	
	@Override
	public List getFirstOrderState(List<Integer> coachs) {
		String querystring="select coachid from OrderInfo where coachid in(:coachs) group by coachid";
		String[] params={"coachs"};
		List querylist=dataDao.getObjectsViaParam(querystring, params, coachs);
	    for(int i=0;i<coachs.size();i++)
	    {
	    	for(int j=0;j<querylist.size();j++)
	    	{
	    		if(coachs.get(i).equals(querylist.get(j)))
	    		{
	    			coachs.set(i, 1);
	    			break;
	    		}
	    	}
	    }
		return coachs;
	}
	
    //新增推荐人员信息
	@Override
	public int addRecommendInfo(String inviteid, String invitedcoachid) {	
	        String querystring="from CuserInfo where invitecode=:inviteid";
	        String querystring1="from CuserInfo where coachid=:coachid";
	        String[] params={"inviteid"};
	        String[] params1={"coachid"};
			CuserInfo cquery=(CuserInfo)dataDao.getFirstObjectViaParam(querystring, params,inviteid);
			CuserInfo invitequer=(CuserInfo)dataDao.getFirstObjectViaParam(querystring1, params1,CommonUtils.parseInt(invitedcoachid,0));
			if(cquery!=null)
			{
				BigDecimal c_reward = new BigDecimal(0);
				int coachid=cquery.getCoachid();
				int isExist=checkRecommendinfo(invitedcoachid);
				if(isExist==1)
				{
					RecommendInfo rectemp=new RecommendInfo();
					rectemp.setCoachid(coachid);
					rectemp.setInvitedcoachid(Integer.parseInt(invitedcoachid));
					rectemp.setType(0);
					rectemp.setInviteid(inviteid);
					rectemp.setAddtime(new Date());
					rectemp.setReward(c_reward);
					rectemp.setIschecked(invitequer.getState()==2?1:0);
					rectemp.setIsorder(checkOrderState(invitedcoachid));
					rectemp.setCflag(invitequer.getState()==2?1:0);
					rectemp.setOflag(checkOrderState(invitedcoachid));
				//	rectemp.setInvitecount(getInviteCount(coachid));
				//	rectemp.setOrdercount(getOrderCount(coachid));
					rectemp.setCoachname(cquery.getRealname());
					rectemp.setCoachtelphone(cquery.getTelphone());
					rectemp.setInvitedpeoplename(invitequer.getRealname());
					rectemp.setInvitedpeopletelphone(invitequer.getTelphone());
					dataDao.addObject(rectemp);
					return 1;
				}
				else
					return 0;
			}
			else
				return 0;
	}
	@Override
	public int checkRecommendinfo(String invitedcoachid) {
		String querystring="from RecommendInfo where invitedcoachid=:invitedcoachid";
		String[] params={"invitedcoachid"};
		List querylist=(List)dataDao.getObjectsViaParam(querystring, params,CommonUtils.parseInt(invitedcoachid,0));
		if(querylist.size()!=0)
		{
			//返回0代表已经存在记录了
			return 0;
		}
		else
			//返回1代表没有记录
			return 1;
	}
	@Override
	public int ifhasmoreRecommendinfo(String coachid, int page) {
		StringBuilder querystring=new StringBuilder();
	    querystring.append("from RecommendInfo where coachid=:coachid order by coachid");
	    String[] params={"coachid"};
		List<RecommendInfo> listr=(List<RecommendInfo>)dataDao.pageQueryViaParam(querystring.toString(), Constant.USERLIST_SIZE, page+1, params,CommonUtils.parseInt(coachid, 0));
		if(listr.size()==0)
		{
			return 0;
		}
		else
			return 1;
		
	}
	@SuppressWarnings("unchecked")
	@Override
	public QueryResult<RecommendInfo> getRecommendListForServer(int page, int pagesize) {
		String queryString="from RecommendInfo group by coachid order by coachid";
		String[] params={""};
		List<RecommendInfo> listr=(List<RecommendInfo>)dataDao.pageQueryViaParam(queryString.toString(), pagesize, page, params);
		for(RecommendInfo temp:listr)
		{
			temp.setInvitecount(getRecommendCount(String.valueOf(temp.getCoachid())));
			temp.setCheckmancount(getCheckManCount(temp.getCoachid()));
			temp.setOrdercount(getOrderCount(temp.getCoachid()));
			temp.setTotalreward(getReward(String.valueOf(temp.getCoachid())));
		}
		String querys="SELECT count(*)"+queryString;
 		List countlist=(List)dataDao.getObjectsViaParam(querys, params);
 		long total=	countlist.size();
        return new  QueryResult<RecommendInfo>(listr,total);
	}
	@Override
	public int getRecommendCount(String coachid) {
	    String queryString="select count(*) from RecommendInfo where coachid=:coachid";
	    String[] params={"coachid"};
	    long total=(Long) dataDao.getFirstObjectViaParam(queryString, params, CommonUtils.parseInt(coachid, 0));	    
		return (int)total;
	}
	@Override
	public Map<String,List> getInviteCheckAndEarnMoneyCount(List<Integer> coachs) {
		StringBuffer queryString=new StringBuffer();
		StringBuffer queryString1=new StringBuffer();
		StringBuffer queryString2=new StringBuffer();
		queryString.append("from RecommendInfo where coachid=:coachid");
		queryString1.append("from CuserInfo where coachid=:coachid");
		queryString2.append("from OrderInfo where coachid=:coachid");
		String[] params={"coachid"};
		Map<String,List> result=new HashMap<String,List>();
		CuserInfo tempCuserInfo=new CuserInfo();
		OrderInfo tempOrderInfo=new OrderInfo();
		List result1=new ArrayList();
		List result2=new ArrayList();
		List<RecommendInfo> tempRecommendInfo=new ArrayList<RecommendInfo>();
		for(int i=0;i<coachs.size();i++)
		{
			int rcount=0;
			int ocount=0;
			Integer tempid=coachs.get(i);
			tempRecommendInfo=(List<RecommendInfo>)dataDao.getObjectsViaParam(queryString.toString(), params, tempid);
			for(RecommendInfo r:tempRecommendInfo)
			{
				tempCuserInfo=(CuserInfo) dataDao.getFirstObjectViaParam(queryString1.toString(), params, r.getInvitedcoachid());
				tempOrderInfo=(OrderInfo) dataDao.getFirstObjectViaParam(queryString2.toString(),params,r.getInvitedcoachid());
				if(tempCuserInfo.getState()==2)
				  rcount++;
				if(tempOrderInfo!=null)
				  ocount++;
			}
			
			result1.add(rcount);
			result2.add(ocount);
		}
		result.put("checkCount", result1);
		result.put("earnCount", result2);
		return result;
	}
	@SuppressWarnings("unchecked")
	@Override
	public QueryResult<RecommendInfo> getInvitedDetailsForServer(String coachid,int page,int pagesize) {
          String querystring="from RecommendInfo where coachid=:coachid";
          String[] params={"coachid"};
          List<RecommendInfo> templist= (List<RecommendInfo>)dataDao.pageQueryViaParam(querystring,pagesize, page, params, CommonUtils.parseInt(coachid, 0));
          String querys="select count(*)"+querystring;
  		  long total=(Long) dataDao.getFirstObjectViaParam(querys, params, CommonUtils.parseInt(coachid, 0));
         return new  QueryResult<RecommendInfo>(templist,total);
	}
	@Override
	public BigDecimal getRewardByInvitedcoach(String invitedcoachid) {
		String queryString="select reward from RecommendInfo where invitedcoachid =:invitedcoachid";
		String[] params={"invitedcoachid"};
		BigDecimal result=(BigDecimal)dataDao.getFirstObjectViaParam(queryString, params,CommonUtils.parseInt(invitedcoachid, 0));
		return result;
	}
	@Override
	public int checkOrderState(String coachid) {
		String querystring="from OrderInfo where coachid =:coachid";
		String[] params={"coachid"};
		OrderInfo oi=(OrderInfo)dataDao.getFirstObjectViaParam(querystring, params, CommonUtils.parseInt(coachid, 0));
		if(oi==null)		
		   return 0;
		else if(oi.getOver_time()==null)
			 return 0;
		else
			 return 1;
	}
	@Override
	public int getInviteCount(Integer coachid) {
		String queryString="select count(*) from RecommendInfo where coachid =:coachid";
		String[] params={"coachid"};
		long total=(Long)dataDao.getFirstObjectViaParam(queryString, params, coachid);
		return (int)total;
	}
	@SuppressWarnings("unchecked")
	@Override
	public int getOrderCount(Integer coachid) {
		String queryString="from RecommendInfo where coachid =:coachid";
		String[] params={"coachid"};
		int result=0;
		List<RecommendInfo> tempRecommendInfo=(List<RecommendInfo>) dataDao.getObjectsViaParam(queryString, params, coachid);
		for(RecommendInfo temp:tempRecommendInfo)
		{
			if(checkOrderState(String.valueOf(temp.getInvitedcoachid()))==1)
				result++;
		}
		
		return result;
	}
	@Override
	public int getCheckManCount(Integer coachid) {
		String queryString="from RecommendInfo where coachid =:coachid";
		String[] params={"coachid"};
		int result=0;
		List<RecommendInfo> tempRecommendInfo=(List<RecommendInfo>) dataDao.getObjectsViaParam(queryString, params, coachid);
		for(RecommendInfo temp:tempRecommendInfo)
		{
			if(checkRegisterState(String.valueOf(temp.getInvitedcoachid()))==1)
				result++;
		}
		return result;
	}
	@Override
	public int checkRegisterState(String coachid) {
		String queryString="from CuserInfo where coachid =:coachid";
		String[] params={"coachid"};
		int result=0;
		CuserInfo tempCuserInfo=(CuserInfo) dataDao.getFirstObjectViaParam(queryString, params,CommonUtils.parseInt(coachid, 0));
	    if(tempCuserInfo.getState()==2)
			  return 1;
	    else
	    	  return 0;
		
	}
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public int offeredReward(String coachid,String invitedcoachid,int type) {
		String queryString="from CuserInfo where coachid =:coachid";
		String queryString1="from RecommendInfo where coachid =:coachid and invitedcoachid=:invitedcoachid";
		String[] params={"coachid"};
		String[] params1={"coachid","invitedcoachid"};
		CuserInfo tempCuserInfo=(CuserInfo) dataDao.getFirstObjectViaParam(queryString,params,CommonUtils.parseInt(coachid, 0));
		RecommendInfo tempRecommendInfo=(RecommendInfo) dataDao.getFirstObjectViaParam(queryString1, params1,CommonUtils.parseInt(coachid, 0),CommonUtils.parseInt(invitedcoachid, 0));
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from SystemSetInfo");
		SystemSetInfo systemSetInfo = (SystemSetInfo) dataDao.getFirstObjectViaParam(cuserhql.toString(), null);
		if(tempCuserInfo!=null && tempRecommendInfo!=null)
	    {    	
		    BigDecimal Balance=tempCuserInfo.getMoney();
		    BigDecimal Balanceplus=new BigDecimal(0);
		    BalanceCoachInfo tempBalanceCoachInfo=new BalanceCoachInfo();
			if(type==0)
			{
				if(judgeTeacheOrNot(invitedcoachid)==1)
				{
					Balanceplus=systemSetInfo.getCrewardamount();
			        tempRecommendInfo.setCflag(2);
			    	tempBalanceCoachInfo.setType(4);
			    	return 1;
				}
				else
					return 0;
			}
			else
			{
				Balanceplus=systemSetInfo.getOrewardamount();
				tempRecommendInfo.setOflag(2);
				tempBalanceCoachInfo.setType(5);
			}
			Balance=Balance.add(Balanceplus);
			tempCuserInfo.setMoney(Balance);
			tempRecommendInfo.setReward(tempRecommendInfo.getReward().add(Balanceplus));
			tempBalanceCoachInfo.setUserid(CommonUtils.parseInt(coachid, 0));
			tempBalanceCoachInfo.setAmount(Balanceplus);		
			tempBalanceCoachInfo.setAddtime(new Date());
			tempBalanceCoachInfo.setAmount_out1(new BigDecimal(0));
			tempBalanceCoachInfo.setAmount_out2(new BigDecimal(0));	
			dataDao.updateObject(tempRecommendInfo);
			dataDao.updateObject(tempCuserInfo);
			dataDao.addObject(tempBalanceCoachInfo);
			return 1;
	    }
		else
			return 0;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void updateRecommendInfo(RecommendInfo temp) {
		dataDao.updateObject(temp);
		
	}
	@Override
	public List<RecommendInfo> getRecommondInviteInfoByCoachid(String coachid,String realname) {
		String querystring="from RecommendInfo where coachid=:coachid";
		String[] params={"coachid"};
		List<RecommendInfo> tempRecommendInfoList=(List<RecommendInfo>) dataDao.getObjectsViaParam(querystring, params, CommonUtils.parseInt(coachid, 0));	
		return tempRecommendInfoList;
	}

	@Override
	public RecommendInfo getRecommondInvitedInfoByCoachid(String coachid,String realname) {
		String querystring1="from RecommendInfo where invitedcoachid=:invitedcoachid";
		String[] params1={"invitedcoachid"};
		RecommendInfo tempRecommendInfo=(RecommendInfo)dataDao.getFirstObjectViaParam(querystring1, params1, CommonUtils.parseInt(coachid, 0));
		return tempRecommendInfo;
	}
	@Override
	public QueryResult<RecommendInfo> getRecommonedInfoByKeyWord(String searchname, String searchphone, Integer pageIndex,Integer pagesize) 
	{
		String queryString="from RecommendInfo where coachname like '%coachname%' or coachtelphone like '%coachtelphone%' group by coachid order by coachid";
		String[] params={"coachname"};
		List<RecommendInfo> listr=(List<RecommendInfo>)dataDao.pageQueryViaParam(queryString.toString(), pagesize, pageIndex, params);
		for(RecommendInfo temp:listr)
		{
			temp.setInvitecount(getRecommendCount(String.valueOf(temp.getCoachid())));
			temp.setCheckmancount(getCheckManCount(temp.getCoachid()));
			temp.setOrdercount(getOrderCount(temp.getCoachid()));
			temp.setTotalreward(getReward(String.valueOf(temp.getCoachid())));
		}
		String querys="SELECT count(*)"+queryString;
 		List countlist=(List)dataDao.getObjectsViaParam(querys, params);
 		long total=	countlist.size();
        return new  QueryResult<RecommendInfo>(listr,total);
	}
	@Override
	public int judgeTeacheOrNot(String coachid) {
		String[] params={"coachid"};
		//查找 hour=0 并且state=1的开课记录
		List<CscheduleInfo> tempCscheduleInfo=(List<CscheduleInfo>)dataDao.getObjectsViaParam("from CscheduleInfo where coachid=:coachid and hour='0' and state=1 order by date desc", params,CommonUtils.parseInt(coachid, 0));
		for(CscheduleInfo c:tempCscheduleInfo)
		 {
			 //根据教练开课日期查找当天开课记录数
			 String[] params1={"coachid","date"};
			 List<CscheduleInfo> tempCscheduleInfo1=(List<CscheduleInfo>)dataDao.getObjectsViaParam("from CscheduleInfo where coachid=:coachid and date=:date", params1,CommonUtils.parseInt(coachid, 0),c.getDate());
				if(tempCscheduleInfo1!=null &&tempCscheduleInfo1.size()>1)
				  {
					    StringBuffer cuserhql1 = new StringBuffer();
						cuserhql1.append("from RecommendInfo where invitedcoachid = :invitedcoachid");
						String[] params2 = { "invitedcoachid" };
						RecommendInfo tempRecommendInfo = (RecommendInfo) dataDao.getFirstObjectViaParam(cuserhql1.toString(), params2, CommonUtils.parseInt(coachid, 0));
						if(tempRecommendInfo!=null)
						{
							if(tempRecommendInfo.getCflag()!=2)
							{
						      	tempRecommendInfo.setCflag(1);
						      	updateRecommendInfo(tempRecommendInfo);
						      	return 1;
							}
				        }
				  }
		 }
		 return 0;
	

		
		
	}
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void deleteRecommonedInfo(String coachid, String invitedcoachid) {
		String queryString="from RecommendInfo where coachid=:coachid and invitedcoachid=:invitedcoachid";
		String[] params={"coachid","invitedcoachid"};
		RecommendInfo tempRecommendInfo=(RecommendInfo)dataDao.getFirstObjectViaParam(queryString, params,CommonUtils.parseInt(coachid, 0),CommonUtils.parseInt(invitedcoachid, 0));
		dataDao.deleteObject(tempRecommendInfo);
	}
	
	
}
