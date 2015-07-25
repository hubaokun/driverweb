package com.daoshun.guangda.serviceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.daoshun.common.CommonUtils;
import com.daoshun.common.Constant;
import com.daoshun.common.QueryResult;
import com.daoshun.guangda.pojo.AdminInfo;
import com.daoshun.guangda.pojo.CuserInfo;
import com.daoshun.guangda.pojo.RecommendInfo;
import com.daoshun.guangda.service.IRecommendService;
/**
 * 
 * @author wjr  推荐分享服务实现类
 */
@Service("recommendService")
public class RecommendServiceImpl extends BaseServiceImpl implements IRecommendService {


	@Override
	//获取教练推荐人员列表
	public QueryResult<RecommendInfo> getRecommendList(String coachid,int page) {
	    StringBuilder querystring=new StringBuilder();
	    querystring.append("from RecommendInfo where coachid=:coachid order by coachid");
	    String[] params={"coachid"};
		List<RecommendInfo> listr=(List<RecommendInfo>)dataDao.pageQueryViaParam(querystring.toString(), Constant.USERLIST_SIZE, page, params,CommonUtils.parseInt(coachid, 0));
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
        String[] params={"inviteid"};
		CuserInfo cquery=(CuserInfo)dataDao.getFirstObjectViaParam(querystring, params,inviteid);
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
		if(!(querylist.size()==0))
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
	/*@Override
	public QueryResult<RecommendInfo> getRecommendListForServer(int page, int pagesize) {
		String queryString="from RecommendInfo group by coachid order by coachid";
		String[] params={""};
		List<RecommendInfo> listr=(List<RecommendInfo>)dataDao.pageQueryViaParam(queryString.toString(), pagesize, page, params);
		List<Integer> coachs=new ArrayList<Integer>(); 
		for(RecommendInfo temp:listr)
		{
			coachs.add(temp.getCoachid());
		}
		queryString="from CuserInfo where coachid in(:coachs) order by coachid";
		params[0]="coachs";
		List<CuserInfo> clistr=(List<CuserInfo>) dataDao.getObjectsViaParam(queryString, params,coachs);
		for(CuserInfo c:clistr)
		{
			System.out.println(c.getRealname());
		}
		return null;
	}*/
}
