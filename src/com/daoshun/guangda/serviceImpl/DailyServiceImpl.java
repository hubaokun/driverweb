package com.daoshun.guangda.serviceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.daoshun.common.CommonUtils;
import com.daoshun.common.QueryResult;
import com.daoshun.guangda.NetData.CoinReportBySchool;
import com.daoshun.guangda.pojo.AdminInfo;
import com.daoshun.guangda.pojo.CuserInfo;
import com.daoshun.guangda.pojo.DriveSchoolInfo;
import com.daoshun.guangda.pojo.OrderInfo;
import com.daoshun.guangda.pojo.OrderPrice;
import com.daoshun.guangda.pojo.SuserInfo;
import com.daoshun.guangda.service.IDailyService;
import com.daoshun.guangda.pojo.daymontlyreportInfo;

@Service("dailyService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class DailyServiceImpl extends BaseServiceImpl implements IDailyService {

	@Override
	public int getSuserCount(String addtime) {
		long count = 0;
		StringBuffer systemhql = new StringBuffer();
		systemhql.append("from SuserInfo where 1=1");
		if(CommonUtils.isEmptyString(addtime)){
			systemhql.append(" and addtime <= '" + addtime + "'");
		}
		systemhql.insert(0, "select count(*)");
		count = (Long) dataDao.getFirstObjectViaParam(systemhql.toString(), null);
		return (int) count;
	}

	@Override
	public int getCuserCount(String addtime) {
		long count = 0;
		StringBuffer systemhql = new StringBuffer();
		systemhql.append("from CuserInfo where 1=1");
		if(CommonUtils.isEmptyString(addtime)){
			systemhql.append(" and addtime <= '" + addtime + "'");
		}
		systemhql.insert(0, "select count(*)");
		count = (Long) dataDao.getFirstObjectViaParam(systemhql.toString(), null);
		return (int) count;
	}

	@Override
	public List<Object> getSystemdatal(String addtime) {
		List<Object> Object = dataDao.callSyetem(addtime);
		return Object;
	}

	@Override
	public List<Object> getSchoolDaily(String addtime) {
		List<Object> Object = dataDao.callSchoolDaily(addtime);
		return Object;
	}

	@Override
	public List<Object> getCoachDaily(String addtime) {
		List<Object> Object = dataDao.callCoachDaily(addtime);
		return Object;
	}

	@Override
	public List<Object> getStudentApplyDaily(String addtime) {
		List<Object> Object = dataDao.callStudentApplyDaily(addtime);
		return Object;
	}

	@Override
	public List<Object> getCoachApplyDaily(String addtime) {
		List<Object> Object = dataDao.callCoachApplyDaily(addtime);
		return Object;
	}

	@Override
	public Object getStudentAccountDaily(String addtime) {
		Object object = dataDao.callStudentAccountDaily(addtime);
		return object;
	}

	@Override
	public Object getCoachAccountDaily(String addtime) {
		Object object = dataDao.callCoachAccountDaily(addtime);
		return object;
	}

	@Override
	public List<Object> getSchoolBillDaily(String starttime, String addtime,String schoolname) {
		List<Object> Object = dataDao.callSchoolBillDaily(starttime,addtime,schoolname);
		return Object;
	}

	@Override
	public List<Object> getXiaoBaDaily(String starttime, String addtime) {
		List<Object> Object = dataDao.callXiaoBaDaily(starttime,addtime);
		return Object;
	}

	@Override
	public Object getAccountReport(String addtime) {
		Object obj = dataDao.getAccountReport(addtime);
		return obj;
	}
	
	//ChRx
	@SuppressWarnings("unchecked")
	@Override
	public List<daymontlyreportInfo> getAccountReportX(String starttime,String endtime)
	{
		StringBuffer hql=new StringBuffer("from daymontlyreportInfo where ");
		String[] params=null;
		Date start=null;
		Date end=null;
		SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd");
		try {
			if(!CommonUtils.isEmptyString(starttime))
			{
				start=sdf.parse(starttime);
			}
			if(!CommonUtils.isEmptyString(endtime))
			{
				end=sdf.parse(endtime);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Boolean isStartTimeEmpty=CommonUtils.isEmptyString(starttime);
		if(!isStartTimeEmpty)
		{
			hql.append("querydate>=:starttime");
			params=new String[1];
			params[0]="starttime";
		}
		if(!CommonUtils.isEmptyString(endtime))
		{   if(!isStartTimeEmpty)
			{
				hql=new StringBuffer("from daymontlyreportInfo where querydate between :starttime and :endtime");
				params=new String[2];
				params[0]="starttime";
				params[1]="endtime";
			}else
			{
				hql.append("querydate<=:endtime");
				params=new String[1];
				params[0]="endtime";
			}
		}
		Object p=new Object();
		List<daymontlyreportInfo> result=null;
		if(params.length==1)
		{
			if(isStartTimeEmpty)
			{
				result=(List<daymontlyreportInfo>)dataDao.getObjectsViaParam(hql.toString(), params,end);
			}else
			{
				result=(List<daymontlyreportInfo>)dataDao.getObjectsViaParam(hql.toString(), params,start);
			}
		}else
		{
			  result=(List<daymontlyreportInfo>)dataDao.getObjectsViaParam(hql.toString(), params,start,end);
		}
		return result;
	}

	@Override
	public List<Object> getCouponReportMontly(Date startdate,Date enddate,String schoolId){
		List<Object> Object = dataDao.getCouponReportMontly(startdate, enddate,schoolId);
		return Object;
	}

	@Override
	public List<Object> getCoinReportMontly(Date startdate,Date enddate) {
		List<Object> Object = dataDao.getCoinReportMontly(startdate, enddate);
		return Object;
	}
	@Override
	public List<Object> getCoinReportMontlyBySchool(Date startdate,Date enddate,Integer schoolid) {
		List<Object> Object = dataDao.getCoinReportMontlyBySchool(startdate, enddate,schoolid);
		return Object;
	}
	@Override
	public List<Object> getCouponReportDetail(String coachid, Date startdate, Date enddate) {
		List<Object> Object = dataDao.getCouponReportDetail(coachid, startdate, enddate);
		return Object;
	}

	@Override
	public List<Object> getCoinReportDetail(String coachid,String ownertype, Date startdate, Date enddate) {
		List<Object> Object = dataDao.getCoinReportDetail(coachid,ownertype, startdate, enddate);
		return Object;
	}

	@Override
	public List<Object> getCoinReportDetailBySchool(String coachid, Date startdate, Date enddate) {
		List<Object> Object = dataDao.getCoinReportDetailBySchool(coachid, startdate, enddate);
		return Object;
	}
	
	@Override
	public QueryResult<OrderInfo> getOrderInfos(String starttime,String endtime,int pageIndex,int pagesize) {
		StringBuffer hql=new StringBuffer("from OrderInfo where 1=1");
		List<OrderInfo> orderList=null;
		if(!CommonUtils.isEmptyString(starttime))
		{
			hql.append(" and date>='"+starttime+"'");
		}
		if(!CommonUtils.isEmptyString(endtime))
		{
			hql.append(" and date<='"+endtime+"'");
		}
		orderList=(List<OrderInfo>)dataDao.pageQueryViaParam(hql.toString(),pagesize,pageIndex,null);
		
		Iterator<OrderInfo> iterator=orderList.iterator();
		while (iterator.hasNext()) {
			OrderInfo order =iterator.next();
			SuserInfo studentinfo=dataDao.getObjectById(SuserInfo.class,order.getStudentid());
			order.setStudentinfo(studentinfo);
			CuserInfo coachInfo=dataDao.getObjectById(CuserInfo.class,order.getCoachid());
			DriveSchoolInfo school=null;
			if (coachInfo!=null) {
				if(coachInfo.getDrive_schoolid()!=null)
				{
					school=dataDao.getObjectById(DriveSchoolInfo.class,coachInfo.getDrive_schoolid());
				}
			}
			order.setCoachInfo(coachInfo);
			order.setSchoolInfo(school);
			//1 余额  2 小巴券  3 小巴币
			if (order.getPaytype()==1) {
				order.setPaytypename("余额");
				order.setPaidMoney(String.valueOf(order.getTotal()));
			}
			if (order.getPaytype()==1) {
				order.setPaytypename("小巴券");
				order.setPaidMoney("0");
			}
			if (order.getPaytype()==1) {
				order.setPaytypename("小巴币");
				order.setPaidMoney("0");
			}
			if(order.getMixCoin()!=0 && order.getMixMoney()!=0)
			{
				order.setPaytypename("混合支付");
				order.setPaidMoney("币:"+order.getMixCoin()+",余:"+order.getMixMoney());
			}
			OrderPrice orderPrice=dataDao.getObjectById(OrderPrice.class,order.getOrderid());
			order.setSubjectname(orderPrice.getSubject());
			order.setUnitPrice(orderPrice.getPrice());
		}
		String counthql = " select count(*) " + hql.toString();
        long total = (Long) dataDao.getFirstObjectViaParam(counthql,null);
		return new QueryResult<OrderInfo>(orderList, total);
	}

	@Override
	public List<OrderInfo> getOrdersForExported(String date) {
		
		StringBuffer hql=new StringBuffer("from OrderInfo where 1=1");
		List<OrderInfo> orderList=null;
		if(!CommonUtils.isEmptyString(date))
		{
			hql.append(" and date=:date");
			String[] params={"date"};
			orderList=(List<OrderInfo>)dataDao.getObjectsViaParam(hql.toString(), params,date);
			
		Iterator<OrderInfo> iterator=orderList.iterator();
		while (iterator.hasNext()) {
			OrderInfo order =iterator.next();
			SuserInfo studentinfo=dataDao.getObjectById(SuserInfo.class,order.getStudentid());
			order.setStudentinfo(studentinfo);
			CuserInfo coachInfo=dataDao.getObjectById(CuserInfo.class,order.getCoachid());
			DriveSchoolInfo school=null;
			if (coachInfo!=null) {
				if(coachInfo.getDrive_schoolid()!=null)
				{
					school=dataDao.getObjectById(DriveSchoolInfo.class,coachInfo.getDrive_schoolid());
				}
			}
			order.setCoachInfo(coachInfo);
			order.setSchoolInfo(school);
			//1 余额  2 小巴券  3 小巴币
			if (order.getPaytype()==1) {
				order.setPaytypename("余额");
			}
			if (order.getPaytype()==1) {
				order.setPaytypename("小巴券");
			}
			if (order.getPaytype()==1) {
				order.setPaytypename("小巴币");
			}
			if(order.getMixCoin()!=0 && order.getMixMoney()!=0)
			{
				order.setPaytypename("混合支付");
				order.setPaidMoney("币:"+order.getMixCoin()+",余:"+order.getMixMoney());
			}
			OrderPrice orderPrice=dataDao.getObjectById(OrderPrice.class,order.getOrderid());
			order.setSubjectname(orderPrice.getSubject());
			order.setUnitPrice(orderPrice.getPrice());
		}
		return orderList;
	}else {
		return null;
	}
}
//ChRx
@Override
public List<Object> getCoinReportMontlyBySchool(String startdate,String enddate,int schoolid)
{
	 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	 Date startDate=null;
	 Date endDate=null;
	try {
		startDate = sdf.parse(startdate);
		
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 
	try {
		endDate=sdf.parse(enddate);
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 List<Object> list=dataDao.getCoinReportMontlyBySchool(startDate, endDate, schoolid);
	return list;
}
@Override
public List<Object> getCoinReportDetailMontlyBySchool(int coachId,String dateStart,String dateEnd)
{
	 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	 Date startdate=null;
	 Date enddate=null;
	 try {
		startdate=sdf.parse(dateStart);
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 try {
		enddate=sdf.parse(dateEnd);
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 String idOfCoach=String.valueOf(coachId);

	List<Object>list=dataDao.getCoinReportDetailBySchool(idOfCoach, startdate, enddate);
	return list;
}
}
