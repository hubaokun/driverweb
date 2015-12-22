package com.daoshun.guangda.serviceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.util.SystemOutLogger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.daoshun.common.CommonUtils;
import com.daoshun.common.Constant;
import com.daoshun.common.QueryResult;
import com.daoshun.guangda.model.InviteReport;
import com.daoshun.guangda.pojo.BalanceCoachInfo;
import com.daoshun.guangda.pojo.CuserInfo;
import com.daoshun.guangda.pojo.OrderInfo;
import com.daoshun.guangda.pojo.RecommendInfo;
import com.daoshun.guangda.pojo.SuserInfo;
import com.daoshun.guangda.pojo.SystemSetInfo;
import com.daoshun.guangda.service.IRecommendService;
import com.daoshun.guangda.service.ISUserService;
/**
 * 
 * @author wjr  推荐分享服务实现类
 */
@Service("recommendService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class RecommendServiceImpl extends BaseServiceImpl implements IRecommendService {
	private SessionFactory sessionFactory;
	@Resource
	private ISUserService suserService;
	@Override
	//获取教练推荐人员列表
	public QueryResult<RecommendInfo> getRecommendList(String coachid,int page,int type) {
	    StringBuilder querystring=new StringBuilder();
	    querystring.append("from RecommendInfo where coachid=:coachid and type=:type order by coachid");
	    String[] params={"coachid","type"};
		List<RecommendInfo> listr=(List<RecommendInfo>)dataDao.pageQueryViaParam(querystring.toString(), Constant.USERLIST_SIZE, page, params,CommonUtils.parseInt(coachid, 0),type);
		String querys="select count(*)"+querystring.toString();
		long total=(Long) dataDao.getFirstObjectViaParam(querys, params, CommonUtils.parseInt(coachid, 0),type);
		if(listr!=null)
		    return new QueryResult<RecommendInfo>(listr,total);
		else
			return new QueryResult<RecommendInfo>(null,0);
	}
   //获取所得奖励总额
	@Override
	public BigDecimal getReward(String coachid,int type) {
		String querystring="select sum(reward) from RecommendInfo where coachid=:coachid and type=:type";
	    String[] params={"coachid","type"};
	    BigDecimal sumreward=(BigDecimal)dataDao.getFirstObjectViaParam(querystring, params, CommonUtils.parseInt(coachid, 0),type);
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
	public int addRecommendInfo(String inviteid, String invitedpeopleid,int type) {	
	    if(type==1)
	    {
	        String querystring="from CuserInfo where invitecode=:inviteid";
	        String querystring1="from CuserInfo where coachid=:coachid";
	        String[] params={"inviteid"};
	        String[] params1={"coachid"};
			CuserInfo cquery=(CuserInfo)dataDao.getFirstObjectViaParam(querystring, params,inviteid);
			CuserInfo invitequer=(CuserInfo)dataDao.getFirstObjectViaParam(querystring1, params1,CommonUtils.parseInt(invitedpeopleid,0));
			if(cquery!=null)
			{
				int coachid=cquery.getCoachid();
				int isExist=checkRecommendinfo(invitedpeopleid,type);
				if(isExist==1)
				{
					RecommendInfo rectemp=new RecommendInfo();
					rectemp.setCoachid(coachid);
					rectemp.setInvitedcoachid(Integer.parseInt(invitedpeopleid));
					rectemp.setType(0);
					rectemp.setInviteid(inviteid);
					rectemp.setAddtime(new Date());
					rectemp.setReward(new BigDecimal(0));
					rectemp.setIschecked(invitequer.getState()==2?1:0);
					rectemp.setIsorder(checkOrderState(invitedpeopleid));
					rectemp.setCflag(invitequer.getState()==2?1:0);
					rectemp.setOflag(checkOrderState(invitedpeopleid));
					rectemp.setCoachname(cquery.getRealname());
					rectemp.setType(type);
					rectemp.setCoachtelphone(cquery.getTelphone());
					rectemp.setInvitedpeoplename(invitequer.getRealname());
					rectemp.setInvitedpeopletelphone(invitequer.getTelphone());
					dataDao.addObject(rectemp);
					return 1;
				}
				else
					return 0;
			}
			return 0;
	    }
	    else if(type==2)
	    {
	    	String querystring="from CuserInfo where invitecode=:inviteid";
	        String querystring1="from SuserInfo where studentid=:studentid";
	        String[] params={"inviteid"};
	        String[] params1={"studentid"};
	        CuserInfo cquery=(CuserInfo)dataDao.getFirstObjectViaParam(querystring, params,inviteid);
	        SuserInfo invitequer=(SuserInfo)dataDao.getFirstObjectViaParam(querystring1, params1,CommonUtils.parseInt(invitedpeopleid,0));
	        if(cquery!=null)
			{
				int coachid=cquery.getCoachid();
				int isExist=checkRecommendinfo(invitedpeopleid,type);
				if(isExist==1)
				{
					RecommendInfo rectemp=new RecommendInfo();
					rectemp.setCoachid(coachid);
					rectemp.setInvitedstudentid(Integer.parseInt(invitedpeopleid));
					rectemp.setType(0);
					rectemp.setInviteid(inviteid);
					rectemp.setAddtime(new Date());
					rectemp.setReward(new BigDecimal(0));
					rectemp.setCoachname(cquery.getRealname());
					rectemp.setType(type);
					rectemp.setCoachtelphone(cquery.getTelphone());
					rectemp.setInvitedpeoplename(invitequer.getRealname());
					rectemp.setInvitedpeopletelphone(invitequer.getPhone());
					dataDao.addObject(rectemp);
					return 1;
				}
				else
					return 0;
			}
	        return 0;
	    }
	    return 0;
	}
	@Override
	public int checkRecommendinfo(String invitedcoachid,int type) {
		StringBuffer querystring=new StringBuffer();
		String[] params={""};
		if(type==1 || type==3)
		{
			querystring.append("from RecommendInfo where invitedcoachid=:invitedcoachid");
			params[0]="invitedcoachid";
		}
		else
		{
			querystring.append("from RecommendInfo where invitedstudentid=:invitedstudentid");
			params[0]="invitedstudentid";
		}
		List querylist=(List)dataDao.getObjectsViaParam(querystring.toString(), params,CommonUtils.parseInt(invitedcoachid,0));
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
	public int ifhasmoreRecommendinfo(String coachid, int page,int type) {
		StringBuilder querystring=new StringBuilder();
	    querystring.append("from RecommendInfo where coachid=:coachid and type=:type order by coachid");
	    String[] params={"coachid","type"};
		List<RecommendInfo> listr=(List<RecommendInfo>)dataDao.pageQueryViaParam(querystring.toString(), Constant.USERLIST_SIZE, page+1, params,CommonUtils.parseInt(coachid, 0),type);
		if(listr.size()==0)
		{
			return 0;
		}
		else
			return 1;
	}
	@SuppressWarnings("unchecked")
	@Override
	public QueryResult<RecommendInfo> getRecommendListForServer(int page, int pagesize,int type) {
		StringBuffer queryString=new StringBuffer();
		queryString.append("from RecommendInfo where type=:type group by coachid order by coachid");
		String[] params={"type"};	
		
		List<RecommendInfo> listr=(List<RecommendInfo>)dataDao.pageQueryViaParam(queryString.toString(), pagesize, page, params,type);
		for(RecommendInfo temp:listr)
		{
			temp.setInvitecount(getRecommendCount(String.valueOf(temp.getCoachid()),type));
			if(type==1)
			{
				temp.setCheckmancount(getCheckManCount(temp.getCoachid(),type));
				temp.setOrdercount(getOrderCount(temp.getCoachid()));
			}
			temp.setTotalreward(getReward(String.valueOf(temp.getCoachid()),type));
		}
		String querys="SELECT count(*)"+queryString;
 		List countlist=(List)dataDao.getObjectsViaParam(querys, params,type);
 		long total=	countlist.size();
        return new  QueryResult<RecommendInfo>(listr,total);
	}
	@Override
	public int getRecommendCount(String coachid,int type) {
	    StringBuffer queryString=new StringBuffer();
	    queryString.append("select count(*) from RecommendInfo where coachid=:coachid and type=:type");
	    String[] params={"coachid","type"};
	    long total=(Long) dataDao.getFirstObjectViaParam(queryString.toString(), params, CommonUtils.parseInt(coachid, 0),type);	    
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
	public QueryResult<RecommendInfo> getInvitedDetailsForServer(String coachid,int page,int pagesize,int type) {
          String querystring="from RecommendInfo where coachid=:coachid and type=:type";
          String[] params={"coachid","type"};
          List<RecommendInfo> templist= (List<RecommendInfo>)dataDao.pageQueryViaParam(querystring,pagesize, page, params, CommonUtils.parseInt(coachid, 0),type);
          String querys="select count(*)"+querystring;
  		  long total=(Long) dataDao.getFirstObjectViaParam(querys, params, CommonUtils.parseInt(coachid, 0),type);
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
	public int getCheckManCount(Integer coachid,int type) {
		String queryString="from RecommendInfo where coachid =:coachid and type=:type";
		String[] params={"coachid","type"};
		int result=0;
		List<RecommendInfo> tempRecommendInfo=(List<RecommendInfo>) dataDao.getObjectsViaParam(queryString, params, coachid,type);
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
	public int offeredReward(String coachid,String invitedcoachid,int type,HashMap<String, Object> resultmap) {
		String queryString="from CuserInfo where coachid =:coachid";
		String queryString1="from RecommendInfo where coachid =:coachid and invitedcoachid=:invitedcoachid";
		String[] params={"coachid"};
		String[] params1={"coachid","invitedcoachid"};
		CuserInfo tempCuserInfo=(CuserInfo) dataDao.getFirstObjectViaParam(queryString,params,CommonUtils.parseInt(coachid, 0));
		RecommendInfo tempRecommendInfo=(RecommendInfo) dataDao.getFirstObjectViaParam(queryString1, params1,CommonUtils.parseInt(coachid, 0),CommonUtils.parseInt(invitedcoachid, 0));
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("from SystemSetInfo");
		SystemSetInfo systemSetInfo = (SystemSetInfo) dataDao.getFirstObjectViaParam(cuserhql.toString(), null);
		int result=1;
		if(tempCuserInfo!=null && tempRecommendInfo!=null)
	    {    	
			int cmoney[]=suserService.getCoachMoney(tempCuserInfo.getCoachid());
			BigDecimal cuserOrderMoney=new BigDecimal(cmoney[0]);
			BigDecimal Balance=cuserOrderMoney;
		    BigDecimal Balanceplus=new BigDecimal(0);
		    BalanceCoachInfo tempBalanceCoachInfo=new BalanceCoachInfo();
			if(type==0)
			{
				if(judgeTeacheOrNot(invitedcoachid,resultmap)==1)
				{
					Balanceplus=systemSetInfo.getCrewardamount();
			        tempRecommendInfo.setCflag(2);
			    	tempBalanceCoachInfo.setType(4);
				}
				else
					result=0;
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
	    }
		else
			result=0;
		return result;
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
	public List<RecommendInfo> getRecommondInviteInfoByStudentid(String studentid,String realname) {
		String querystring="from RecommendInfo where studentid=:studentid";
		String[] params={"studentid"};
		List<RecommendInfo> tempRecommendInfoList=(List<RecommendInfo>) dataDao.getObjectsViaParam(querystring, params, CommonUtils.parseInt(studentid, 0));	
		return tempRecommendInfoList;
	}

	@Override
	public RecommendInfo getRecommondInvitedInfoByStudentid(String studentid,String realname) {
		String querystring1="from RecommendInfo where invitedstudentid=:invitedstudentid";
		String[] params1={"invitedstudentid"};
		RecommendInfo tempRecommendInfo=(RecommendInfo)dataDao.getFirstObjectViaParam(querystring1, params1, CommonUtils.parseInt(studentid, 0));
		return tempRecommendInfo;
	}
	@SuppressWarnings("unchecked")
	@Override
	public QueryResult<RecommendInfo> getRecommonedInfoByKeyWord(String searchname, String searchphone,int type) 
	{
		StringBuffer queryString=new StringBuffer("from RecommendInfo where type=:type "); 
		String[] params=new String[3];
		int count=0;
		params[count++]="type";
		if(!searchname.equals(""))
		{
			queryString.append(" and coachname like :coachname ");
			params[count++]="coachname";
			
		}
		if(!searchphone.equals(""))
		{
			queryString.append(" and coachtelphone like :coachtelphone ");
			params[count++]="coachtelphone";
		}
		queryString.append(" group by coachid order by coachid");
		
		
	
		String querys="SELECT count(*)"+queryString.toString();
		List<RecommendInfo> listr=new ArrayList<RecommendInfo>();
		List countlist=new ArrayList();
	    if(!searchname.equals("") && searchphone.equals(""))
	    {
			listr=(List<RecommendInfo>)dataDao.getObjectsViaParam(queryString.toString(), params,type,"%"+searchname+"%");
	 		countlist=(List)dataDao.getObjectsViaParam(querys, params,type,"%"+searchname+"%");
	    }
	    else if(searchname.equals("") && !searchphone.equals(""))
	    {
	    	listr=(List<RecommendInfo>)dataDao.getObjectsViaParam(queryString.toString(), params,type,"%"+searchphone+"%");
	 		countlist=(List)dataDao.getObjectsViaParam(querys, params,type,"%"+searchphone+"%");
	    }
	    else if(!searchname.equals("") && !searchphone.equals(""))
	    {
	    	listr=(List<RecommendInfo>)dataDao.getObjectsViaParam(queryString.toString(), params,type,"%"+searchname+"%","%"+searchphone+"%");
	 		countlist=(List)dataDao.getObjectsViaParam(querys, params,type,"%"+searchname+"%","%"+searchphone+"%");
	    }
	    else
	    {
	    	listr=(List<RecommendInfo>)dataDao.getObjectsViaParam(queryString.toString(), params,type);
	    	countlist=(List)dataDao.getObjectsViaParam(querys, params,type);
	    }
 		for(RecommendInfo temp:listr)
		{
			temp.setInvitecount(getRecommendCount(String.valueOf(temp.getCoachid()),type));
			if(type==1)
			{
			temp.setCheckmancount(getCheckManCount(temp.getCoachid(),type));
			temp.setOrdercount(getOrderCount(temp.getCoachid()));
			}
			temp.setTotalreward(getReward(String.valueOf(temp.getCoachid()),type));
		}
 		long total=	countlist.size();
        return new  QueryResult<RecommendInfo>(listr,total);
	}
	@Override
	public int judgeTeacheOrNot(String coachid,HashMap<String, Object> resultmap) {
		List<CuserInfo> coachlist=(List<CuserInfo>)resultmap.get("coachlist");
		for(CuserInfo c:coachlist)
		{  
			if(coachid.equals(String.valueOf(c.getCoachid())))
				return 1;
				
		}
		 return 0;
	}
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void deleteRecommonedInfo(String coachid, String invitedid,int type) {
		StringBuffer queryString=new StringBuffer();
		queryString.append("from RecommendInfo where coachid=:coachid and type=:type");
		String[] params=new String[4];
		int count =0;
		params[count++]="coachid";
		params[count++]="type";
		if(type==1)
		{
			queryString.append(" and invitedcoachid=:invitedcoachid");
			params[count++]="invitedcoachid";
		}
		else if(type==2)
		{
			queryString.append(" and invitedstudentid=:invitedstudentid");
			params[count++]="invitedstudentid";
		}
		RecommendInfo tempRecommendInfo=(RecommendInfo)dataDao.getFirstObjectViaParam(queryString.toString(), params,CommonUtils.parseInt(coachid, 0),type,CommonUtils.parseInt(invitedid, 0));
		dataDao.deleteObject(tempRecommendInfo);
	}
	@Override
	public QueryResult<InviteReport> getRecommenReport(Integer pageSize,Integer pageIndex,int type) {
	    StringBuffer querysb=new StringBuffer();
	    querysb.append("select DATE(addtime) from RecommendInfo where type=:type group by DATE(addtime) order by addtime desc");
	    String querystring="select count(*) from RecommendInfo where type=:type group by DATE(addtime) order by addtime desc";
	    String[] params={"type"};
	    List dateRecommendInfo=(List)dataDao.pageQueryViaParam(querysb.toString(), pageSize, pageIndex, params,type);
	    List<RecommendInfo> tempRecommendInfo=(List<RecommendInfo>) dataDao.getObjectsViaParam(querystring, params,type);
	    long total=tempRecommendInfo.size();
	    List<InviteReport> resultInviteReport=new ArrayList<InviteReport>();
	    for(int i=0;i<dateRecommendInfo.size();i++)
        {
	    	InviteReport temInviteReport=new InviteReport();
	    	temInviteReport.setAddtime((Date) dateRecommendInfo.get(i));
	    	Date tempdate=(Date) dateRecommendInfo.get(i);
	    	temInviteReport.setInviteCount(getInviteCount(tempdate,type));
	    	temInviteReport.setCheckPassCount(getCheckPassCount(tempdate));
	    	temInviteReport.setOrderPassCount(getOrderPassCount(tempdate));
	    	temInviteReport.setRewardCount(getRewardCount(tempdate,type));
	    	resultInviteReport.add(temInviteReport);
        }
		return new QueryResult<InviteReport>(resultInviteReport,total);
	}
	@Override
	public int getInviteCount(Date addtime,int type) {
		String querystring="select count(*) from RecommendInfo where Date(addtime)=:addtime and type=:type";
		String[] params={"addtime","type"};
		long result=(Long)dataDao.getFirstObjectViaParam(querystring, params,addtime,type);
		return (int) result;
	}
	@Override
	public int getCheckPassCount(Date addtime) {
		String querystring="select count(*) from RecommendInfo where Date(addtime)=:addtime and ischecked=1";
		String[] params={"addtime"};
		long result=(Long)dataDao.getFirstObjectViaParam(querystring, params,addtime);
		return (int) result;
	}
	@Override
	public int getOrderPassCount(Date addtime) {
		String querystring="select count(*) from RecommendInfo where Date(addtime)=:addtime and isorder=1";
		String[] params={"addtime"};
		long result=(Long)dataDao.getFirstObjectViaParam(querystring, params,addtime);
		return (int) result;
	}
	@Override
	public BigDecimal getRewardCount(Date addtime,int type) {
		String querystring="select sum(reward) from RecommendInfo where Date(addtime)=:addtime and type=:type";
		String[] params={"addtime","type"};
		BigDecimal result=(BigDecimal)dataDao.getFirstObjectViaParam(querystring, params,addtime,type);
		return result;

	}
	public HashMap<String, Object> getCoachList(String condition1, String condition2, String condition3, String condition4, String condition5, String condition6, String condition8, String condition9,
			String condition10, String condition11) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		StringBuffer cuserhql = new StringBuffer();
		cuserhql.append("select getTeachAddress(u.coachid) as address,getCoachOrderCount(u.coachid) as drive_schoolid, u.*  from t_user_coach u where state = 2 and id_cardexptime > curdate() and coach_cardexptime > curdate() and drive_cardexptime > curdate() and car_cardexptime > curdate() and (select count(*) from t_teach_address a where u.coachid = a.coachid and iscurrent = 1) > 0");
		// 真实姓名和教练所属驾校
		if (!CommonUtils.isEmptyString(condition1)) {
			cuserhql.append(" and (realname like '%" + condition1 + "%' or drive_school like '%" + condition1 + "%' or drive_schoolid in (select schoolid from t_drive_school_info where name like  '%"
					+ condition1 + "%')) ");
		}
		// 星级
		if (!CommonUtils.isEmptyString(condition2)) {
			cuserhql.append(" and score >= " + condition2);
		}

		if (!CommonUtils.isEmptyString(condition3)) {

			int subjectid = CommonUtils.parseInt(condition6, 0);

			Date start = null;
			if(condition3.length() == 10){
				start = CommonUtils.getDateFormat(condition3, "yyyy-MM-dd");
			}else if(condition3.length() == 19){
				start = CommonUtils.getDateFormat(condition3, "yyyy-MM-dd HH:mm:ss");
			}

			if (start != null) {
				Calendar startCal = Calendar.getInstance();
				startCal.setTime(start);

				int starthour = startCal.get(Calendar.HOUR_OF_DAY);
				int datecount = 1;
				cuserhql.append(" and getcoachstate(u.coachid," + datecount + ",'" + CommonUtils.getTimeFormat(start, "yyyy-MM-dd") + "'," + starthour + "," + 23 + "," + subjectid + ") = 1");

			}
		} else {
			int subjectid = CommonUtils.parseInt(condition6, 0);
			Calendar c = Calendar.getInstance();

			cuserhql.append(" and getcoachstate(u.coachid," + 10 + ",'" + CommonUtils.getTimeFormat(c.getTime(), "yyyy-MM-dd") + "'," + 5 + "," + 23 + "," + subjectid + ") = 1");
		}

		if (!CommonUtils.isEmptyString(condition11)) {
			cuserhql.append(" and modelid like '%" + condition11 + "%'");
		}


		
		cuserhql.append(" and money >= gmoney and isquit = 0 and state=2 order by score desc,drive_schoolid desc ");
		//System.out.println(cuserhql.toString());
		List<CuserInfo> coachlist = (List<CuserInfo>) dataDao.SqlQuery(cuserhql.toString(),CuserInfo.class);
		
		if (coachlist != null && coachlist.size() > 0) {
			for (CuserInfo coach : coachlist) {
			
				if(coach.getDrive_schoolid()!=null){
					coach.setSumnum(new Long(coach.getDrive_schoolid()));
				}
				if(coach.getAddress()!=null){
					String str[]=coach.getAddress().split("#");
					coach.setAddress("");
					if(str!=null && str.length==3){
						coach.setLongitude(str[0]);
						coach.setLatitude(str[1]);
						coach.setDetail(str[2]);
					}
					coach.setAvatarurl(getFilePathById(coach.getAvatar()));
				}
			}
		}
		result.put("coachlist", coachlist);
	
		return result;
	}
	@Override
	public QueryResult<RecommendInfo> getRecommoneddetailInfoByKeyWord(String searchname, String searchphone,String coachid,int type) {
		 StringBuffer querystring=new StringBuffer();
		 querystring.append("from RecommendInfo where coachid=:coachid and type=:type");
		 String[] params=new String[4];
		 int count=0;
		 params[count++]="coachid";
		 params[count++]="type";
			if(!searchname.equals(""))
			{
				querystring.append(" and invitedpeoplename like :invitedpeoplename ");
				params[count++]="invitedpeoplename";
				
			}
			if(!searchphone.equals(""))
			{
				querystring.append(" and invitedpeopletelphone like :invitedpeopletelphone ");
				params[count++]="invitedpeopletelphone";
			}
			String querys="SELECT count(*)"+querystring.toString();
		 List countlist=new ArrayList();
		 List<RecommendInfo> templist=new ArrayList<RecommendInfo>();
		    if(!searchname.equals("") && searchphone.equals(""))
		    {
		    	templist=(List<RecommendInfo>)dataDao.getObjectsViaParam(querystring.toString(), params,CommonUtils.parseInt(coachid, 0),type,"%"+searchname+"%");
		 		countlist=(List)dataDao.getObjectsViaParam(querys, params,CommonUtils.parseInt(coachid, 0),type,"%"+searchname+"%");
		    }
		    else if(searchname.equals("") && !searchphone.equals(""))
		    {
		    	templist=(List<RecommendInfo>)dataDao.getObjectsViaParam(querystring.toString(), params,CommonUtils.parseInt(coachid, 0),type,"%"+searchphone+"%");
		 		countlist=(List)dataDao.getObjectsViaParam(querys, params,CommonUtils.parseInt(coachid, 0),type,"%"+searchphone+"%");
		    }
		    else if(!searchname.equals("") && !searchphone.equals(""))
		    {
		    	templist=(List<RecommendInfo>)dataDao.getObjectsViaParam(querystring.toString(), params,CommonUtils.parseInt(coachid, 0),type,"%"+searchname+"%","%"+searchphone+"%");
		 		countlist=(List)dataDao.getObjectsViaParam(querys, params,CommonUtils.parseInt(coachid, 0),type,"%"+searchname+"%","%"+searchphone+"%");
		    }
		    else
		    {
		    	templist=(List<RecommendInfo>)dataDao.getObjectsViaParam(querystring.toString(), params,CommonUtils.parseInt(coachid, 0),type);
		    	countlist=(List)dataDao.getObjectsViaParam(querys, params,CommonUtils.parseInt(coachid, 0),type);
		    }
 		  long total=countlist.size();
        return new  QueryResult<RecommendInfo>(templist,total);
	}
	@Override
	public int isoversixhour(String id, int type) {
		Calendar c=Calendar.getInstance();
		Calendar now=Calendar.getInstance();
		    if(type==1)
		    {
		    	String querystring="from CuserInfo where coachid=:coachid ";
		    	String[] params={"coachid"};
		    	CuserInfo temp=(CuserInfo) dataDao.getFirstObjectViaParam(querystring, params,CommonUtils.parseInt(id, 0));
		    	c.setTime(temp.getAddtime());
		    	c.add(c.HOUR_OF_DAY,6);
		    	if(now.getTime().after(c.getTime()))
		    		return 0;//已经超过6个小时
		    	else
		    		return 1;
		    }
		    else
		    {
		    	String querystring="from SuserInfo where studentid=:studentid ";
		    	String[] params={"studentid"};
		    	SuserInfo temp=(SuserInfo) dataDao.getFirstObjectViaParam(querystring, params,CommonUtils.parseInt(id, 0));
		    	c.setTime(temp.getAddtime());
		    	c.add(c.HOUR_OF_DAY,6);
		    	if(now.getTime().after(c.getTime()))
		    		return 0;//已经超过6个小时
		    	else
		    		return 1;
		    }

	}
}
