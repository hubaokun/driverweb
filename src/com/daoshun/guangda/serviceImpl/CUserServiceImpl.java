package com.daoshun.guangda.serviceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.net.SyslogAppender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alipay.config.AlipayConfig;
import com.daoshun.common.ApplePushUtil;
import com.daoshun.common.CommonUtils;
import com.daoshun.common.Constant;
import com.daoshun.common.DeviceType;
import com.daoshun.common.PushtoSingle;
import com.daoshun.common.QueryResult;
import com.daoshun.common.SendPushThreadTask;
import com.daoshun.common.UserType;
import com.daoshun.guangda.pojo.AdminInfo;
import com.daoshun.guangda.pojo.BalanceCoachInfo;
import com.daoshun.guangda.pojo.BalanceStudentInfo;
import com.daoshun.guangda.pojo.CApplyCashInfo;
import com.daoshun.guangda.pojo.CaddAddressInfo;
import com.daoshun.guangda.pojo.CoachLevelInfo;
import com.daoshun.guangda.pojo.CoachStudentInfo;
import com.daoshun.guangda.pojo.CoinAffiliation;
import com.daoshun.guangda.pojo.CoinRecordInfo;
import com.daoshun.guangda.pojo.ComplaintSetInfo;
import com.daoshun.guangda.pojo.CouponCoach;
import com.daoshun.guangda.pojo.CouponRecord;
import com.daoshun.guangda.pojo.CsubjectInfo;
import com.daoshun.guangda.pojo.CuserInfo;
import com.daoshun.guangda.pojo.DriveSchoolInfo;
import com.daoshun.guangda.pojo.LogInfo;
import com.daoshun.guangda.pojo.ModelsInfo;
import com.daoshun.guangda.pojo.NoticesInfo;
import com.daoshun.guangda.pojo.NoticesUserInfo;
import com.daoshun.guangda.pojo.OrderInfo;
import com.daoshun.guangda.pojo.PermissionSetInfo;
import com.daoshun.guangda.pojo.RechargeRecordInfo;
import com.daoshun.guangda.pojo.RecommendInfo;
import com.daoshun.guangda.pojo.SchoolBalance;
import com.daoshun.guangda.pojo.SuserInfo;
import com.daoshun.guangda.pojo.SystemSetInfo;
import com.daoshun.guangda.pojo.TeachcarInfo;
import com.daoshun.guangda.pojo.UserPushInfo;
import com.daoshun.guangda.pojo.VerifyCodeInfo;
import com.daoshun.guangda.service.ICUserService;
import com.daoshun.guangda.service.ICouponService;
import com.daoshun.guangda.service.ISUserService;

/**
 * @author liukn
 */
@Service("cuserService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class CUserServiceImpl extends BaseServiceImpl implements ICUserService {
	@Resource
	private ICouponService couponService;
	@Resource
	private ISUserService suserService;
	/*@Resource
	private SendPushThreadTask sendPushThreadTask;*/
    @Override
    public CuserInfo getCuserByPhone(String loginname) {
        StringBuffer cuserhql = new StringBuffer();
        cuserhql.append(" from CuserInfo where phone = :phone ");
        String[] params = {"phone"};
        CuserInfo cuser = (CuserInfo) dataDao.getFirstObjectViaParam(cuserhql.toString(), params, loginname);
        return cuser;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void addCuser(CuserInfo cuser) {
        dataDao.addObject(cuser);
    }

    @Override
    public CuserInfo getCuserByCoachid(String coachid) {
        CuserInfo cuser = dataDao.getObjectById(CuserInfo.class, CommonUtils.parseInt(coachid, 0));
        if (cuser != null) {
            if (cuser.getLevel() != 0) {
                CoachLevelInfo level = dataDao.getObjectById(CoachLevelInfo.class, cuser.getLevel());
                if (level != null) {
                    cuser.setLevelname(level.getLevelname());
                }
            }
            cuser.setAvatarurl(getFilePathById(cuser.getAvatar()));
            cuser.setId_cardpicburl(getFilePathById(cuser.getId_cardpicb()));
            cuser.setId_cardpicfurl(getFilePathById(cuser.getId_cardpicf()));
            cuser.setDrive_cardpicurl(getFilePathById(cuser.getDrive_cardpic()));
            cuser.setCar_cardpicfurl(getFilePathById(cuser.getCar_cardpicf()));
            cuser.setCar_cardpicburl(getFilePathById(cuser.getCar_cardpicb()));
            cuser.setCoach_cardpicurl(getFilePathById(cuser.getCoach_cardpic()));
            dataDao.updateObject(cuser);
        }

        return cuser;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ModelsInfo> getAllModelInfo() {
        StringBuffer cuserhql = new StringBuffer();
        cuserhql.append("from ModelsInfo where 1=1 order by searchname");
        List<ModelsInfo> modellist = (List<ModelsInfo>) dataDao.getObjectsViaParam(cuserhql.toString(), null);
        return modellist;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public String changeAvatar(CuserInfo cuser) {
        dataDao.updateObject(cuser);
        return getFilePathById(cuser.getAvatar());
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void updateCuser(CuserInfo cuser) {
        dataDao.updateObject(cuser);
    }

    @Override
    public String backUrl(int picid) {
        return getFilePathById(picid);
    }

    @Override
    public ModelsInfo getmodellistbycoachid(int modelid) {
        ModelsInfo model = dataDao.getObjectById(ModelsInfo.class, modelid);
        return model;
    }

    @Override
    public VerifyCodeInfo getVerifyCodeInfo(String code, String phone) {
        StringBuffer cuserhql = new StringBuffer();
        cuserhql.append("from VerifyCodeInfo where code = :code and totype = 1 and phone = :phone");
        String[] params = {"code", "phone"};
        VerifyCodeInfo verifyCodeInfo = (VerifyCodeInfo) dataDao.getFirstObjectViaParam(cuserhql.toString(), params, code, phone);
        return verifyCodeInfo;
    }

    /**
     * 获取教练的默认上车地址
     */
    @Override
    public CaddAddressInfo getcoachaddress(String coachid) {
        StringBuffer cuserhql = new StringBuffer();
        cuserhql.append("from CaddAddressInfo where coachid = :coachid and iscurrent = 1 and isused = 0 ");
        String[] params = {"coachid"};
        CaddAddressInfo addressInfo = (CaddAddressInfo) dataDao.getFirstObjectViaParam(cuserhql.toString(), params, CommonUtils.parseInt(coachid, 0));
        return addressInfo;
    }

    @Override
    public CsubjectInfo getsubjectBysubjectid(int subjectid) {
        CsubjectInfo subjectInfo = dataDao.getObjectById(CsubjectInfo.class, subjectid);
        return subjectInfo;
    }

    @Override
    public CaddAddressInfo getaddress(int adressid) {
        CaddAddressInfo addressInfo = dataDao.getObjectById(CaddAddressInfo.class, adressid);
        return addressInfo;
    }

    /**
     * *************************************** ACTION ********************************************************
     */
    @SuppressWarnings("unchecked")
    @Override
    public QueryResult<AdminInfo> getAdmins(Integer pageIndex, int pagesize) {
        StringBuffer cuserhql = new StringBuffer();
        cuserhql.append("from AdminInfo");
        List<AdminInfo> adminlist = (List<AdminInfo>) dataDao.pageQueryViaParam(cuserhql.toString(), pagesize, pageIndex, null);
        String counthql = " select count(*) " + cuserhql.toString();
        long total = (Long) dataDao.getFirstObjectViaParam(counthql, null);
        return new QueryResult<AdminInfo>(adminlist, total);
    }

    @SuppressWarnings("unchecked")
    @Override
    public QueryResult<AdminInfo> getAdminsBykeyword(String searchlogin, String searchtelphone, Integer pageIndex, int pagesize) {
        StringBuffer cuserhql = new StringBuffer();
        cuserhql.append("from AdminInfo where 1=1");
        if (!CommonUtils.isEmptyString(searchlogin)) {
            cuserhql.append(" and login_account like '%" + searchlogin + "%'");
        }
        if (!CommonUtils.isEmptyString(searchtelphone)) {
            cuserhql.append(" and telphone like '%" + searchtelphone + "%'");
        }
        List<AdminInfo> adminlist = (List<AdminInfo>) dataDao.pageQueryViaParam(cuserhql.toString(), pagesize, pageIndex, null);
        String counthql = " select count(*) " + cuserhql.toString();
        long total = (Long) dataDao.getFirstObjectViaParam(counthql, null);
        return new QueryResult<AdminInfo>(adminlist, total);
    }

    @Override
    public AdminInfo getAdminInfoByid(int adminid) {
        AdminInfo adminInfo = dataDao.getObjectById(AdminInfo.class, adminid);
        return adminInfo;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PermissionSetInfo> getPermission() {
        StringBuffer cuserhql = new StringBuffer();
        cuserhql.append("from PermissionSetInfo");
        List<PermissionSetInfo> permissionSetInfo = (List<PermissionSetInfo>) dataDao.getObjectsViaParam(cuserhql.toString(), null);
        return permissionSetInfo;
    }
    
    @Override 
    /**
     * 获取已获得权限名称
     * @param pString
     * @return
     */
    public String getPermissionNamesByPString(String pString)
    {
    	String result="";
    	List<PermissionSetInfo> permissionList=getPermissionByString(pString);
    	Iterator<PermissionSetInfo> iter=permissionList.iterator();
    	while(iter.hasNext())
    	{
    		PermissionSetInfo info=iter.next();
    		if(info.isChecked())
    		{
    			if(result=="")
    			{
    				result+=info.getName();
    			}else
    			{
    				result+=","+info.getName();
    			}
    			
    		}
    	}
    	return result;
    }
    
    private void clearCheckState(List<PermissionSetInfo> list)
    {
    	Iterator <PermissionSetInfo>iter=list.iterator();
    	while(iter.hasNext())
    	{
    		iter.next().setChecked(false);
    	}
    }
//-----权限部分开始---------------------
    //获最后的权限id
    public long getSubPermissionCount(int pid)
    {
    	long i=-1;
    		String hql="from PermissionSetInfo where 1=1 and parentPermissionId=:pid order by permissionid desc ";
    		String[] params={"pid"};
    		List list=dataDao.getObjectsViaParam(hql, params,pid);
    		if(list!=null && list.size()!=0)
    		{
    			i=((PermissionSetInfo)list.get(0)).getPermissionid();
    		}else
    		{
    			i=0;
    		}
    	return i;
    }
    /**
     * 根据命令获取权限
     * @param pString 命令串，例如1-2;3;4,2-1
     * @return
     */
    
    public List<PermissionSetInfo> getPermissionByString(String pString)
    {
    	StringBuffer hql=new StringBuffer();
    	hql.append("from PermissionSetInfo where 1=1 and parentPermissionId!=0");
    	List<PermissionSetInfo> infoList =(List<PermissionSetInfo>)dataDao.getObjectsViaParam(hql.toString(),null);
    	clearCheckState(infoList);
    	List<PermissionSetInfo> obtainPermission=parsePermissionString(pString);
    	this.checkPermission(infoList,obtainPermission);
    	return infoList;
    }

	//获取所有权限
	private List<PermissionSetInfo> parsePermissionString(String pString)
	{
		List<PermissionSetInfo> list=new ArrayList<PermissionSetInfo>();
		if(!CommonUtils.isEmptyString(pString))
		{
			String[]permissions=pString.split(",");
			if(permissions!=null)
			{
				for(int i=0;i<permissions.length;++i)
				{
					List<PermissionSetInfo>listtmp=parsePermission(permissions[i]);
					if(listtmp!=null && listtmp.size()>0)
					{
						list.addAll(listtmp);
					}
				}
			}
			
		}
		return list;
	}
	
	
/*	//获取单条权限
	private List<PermissionSetInfo> parsePermission(String permission)
	{
		List<PermissionSetInfo> permissionList=new ArrayList<PermissionSetInfo>();
		if(!CommonUtils.isEmptyString(permission))
		{
			String pPermission="";
			String[] firstBlade=permission.split("-");
			String[]secondBlade=null;
			if(firstBlade!=null && firstBlade.length!=0)
			{
				pPermission=firstBlade[0];
				if(firstBlade.length>1)
				{
					secondBlade=firstBlade[1].split(";");
				}
			}
			if(!CommonUtils.isEmptyString(pPermission))
			{
				if(secondBlade!=null && secondBlade.length!=0)
				{
					for(int i=0;i<secondBlade.length;++i)
					{
							PermissionSetInfo info=new PermissionSetInfo();
							info.setParentPermissionId(Integer.parseInt(pPermission));
							info.setPermissionid(Integer.parseInt(secondBlade[i]));
							permissionList.add(info);
					}	
				}
			}
			
		}
		
		return permissionList;
	}*/
	
	//通过字符串解析单个权限 ，例：1-1;3;  如果单个数字 代表父权限下所有的子权限都包含
	private List<PermissionSetInfo> parsePermission(String singleP)
	{
		String pPermission="";//父权限
		
		List<PermissionSetInfo> pList=new ArrayList<PermissionSetInfo>();
		if(!CommonUtils.isEmptyString(singleP))
		{
			
			String[] parent=singleP.split("-");
			String[] child=null;
			if(parent.length>0)
			{
				pPermission=parent[0];
			}
			if(1==parent.length)//当只有父权限时,表示所有子权限获得
			{
				String hql="from PermissionSetInfo where parentPermissionId=:pid";
				String[] params={"pid"};
				pList=(List<PermissionSetInfo>)dataDao.getObjectsViaParam(hql, params,Integer.parseInt(pPermission));
			}else
			{
				child=parent[1].split(";");
				String hql="from PermissionSetInfo where parentPermissionId=:pid and permissionid=:id";
				String[] params={"pid","id"};
				for(int i=0;i<child.length;++i)
				{
					List<PermissionSetInfo> list=(List<PermissionSetInfo>)dataDao.getObjectsViaParam(hql, params,Integer.parseInt(pPermission),Integer.parseInt(child[i]));
					if(list!=null && list.size()!=0)
					{
						pList.add(list.get(0));
					}
					
				}
			}
			
		}
		
		return pList;
	}
	
	/**
	 * 标记已获得的权限
	 * @param allPermission
	 * @param permissions
	 * @return  
	 */
	private void checkPermission(List<PermissionSetInfo>allPermission,List<PermissionSetInfo>permissions)
	{
		if(allPermission!=null && permissions!=null && permissions.size()>0)
		{
			Iterator<PermissionSetInfo>iterP;
			Iterator<PermissionSetInfo>iter=permissions.iterator();
			PermissionSetInfo ptmp=null;
			PermissionSetInfo ctmp=null;
			
			while(iter.hasNext())
			{
				ctmp=iter.next();
				iterP=allPermission.iterator();
				while(iterP.hasNext())
				{
					ptmp=iterP.next();
					if(ptmp.getParentPermissionId()==ctmp.getParentPermissionId())
					{
						if(ptmp.getPermissionid()==ctmp.getPermissionid())
						{
							ptmp.setChecked(true);
							break;
							
						}
					}
				}
			}
		}
	}
	
 //-----权限部分结束---------------------   
    @Override
    public AdminInfo getAdminInfoBylogin(String loginname) {
        StringBuffer cuserhql = new StringBuffer();
        cuserhql.append("from AdminInfo where login_account = :login_account");
        String[] params = {"login_account"};
        AdminInfo adminInfo = (AdminInfo) dataDao.getFirstObjectViaParam(cuserhql.toString(), params, loginname);
        return adminInfo;
    }

    @SuppressWarnings("unchecked")
    @Override
    public QueryResult<CuserInfo> getAllCuserInfos(Integer pageIndex, int pagesize) {
        StringBuffer cuserhql = new StringBuffer();
        cuserhql.append("from CuserInfo where isquit = 0 ");
        List<CuserInfo> cuserInfolist = (List<CuserInfo>) dataDao.pageQueryViaParam(cuserhql.toString() + " order by id desc ", pagesize, pageIndex, null);
        String counthql = " select count(*) " + cuserhql.toString();
        long total = (Long) dataDao.getFirstObjectViaParam(counthql, null);
        return new QueryResult<CuserInfo>(cuserInfolist, total);
    }

    @SuppressWarnings("unchecked")
    @Override
    public QueryResult<CuserInfo> getCoachByKeyword(String searchname, String searchphone, Integer driveSchoolname, String carlicense, Integer checkstate, Integer pageIndex, int pagesize) {
        StringBuffer cuserhql = new StringBuffer();
        cuserhql.append("from CuserInfo where 1=1 ");

        if (!CommonUtils.isEmptyString(searchname)) {
            cuserhql.append(" and realname like '%" + searchname + "%'");
        }
        if (!CommonUtils.isEmptyString(searchphone)) {
            cuserhql.append(" and phone like '%" + searchphone + "%'");
        }

        if (driveSchoolname != 0) {
            if (driveSchoolname == -1) {
                cuserhql.append(" and drive_school =null and drive_schoolid =null ");
            }
            else {
                cuserhql.append(" and drive_schoolid =" + driveSchoolname);
            }

        }
        if (checkstate != null) {
        	if (checkstate == 4) {
        		cuserhql.append(" and state >= 0 ");
        	}
            if (checkstate == 0) {
                cuserhql.append(" and state = 0 ");
            }
            if (checkstate == 1) {
                cuserhql.append(" and state = 1 ");
            }

            if (checkstate == 2) {
                cuserhql.append(" and state = 2 ");
            }

            if (checkstate == 3) {
                cuserhql.append(" and state = 3 ");
            }
        }

        if (!CommonUtils.isEmptyString(carlicense)) {
            cuserhql.append(" and carlicense like '%" + carlicense + "%'");
        }

        List<CuserInfo> cuserInfolist = (List<CuserInfo>) dataDao.pageQueryViaParam(cuserhql.toString() + " order by id desc ", pagesize, pageIndex, null);
        String counthql = " select count(*) " + cuserhql.toString();
        long total = (Long) dataDao.getFirstObjectViaParam(counthql, null);
        return new QueryResult<CuserInfo>(cuserInfolist, total);
    }
    
  //获取查询教练日志  
    @SuppressWarnings("unchecked")
    @Override
    public QueryResult<LogInfo> getCoachLogByKeyword( Integer pageIndex, int pagesize,String username, String formname, String starttime, String endtime) {
        StringBuffer cuserhql = new StringBuffer();
        cuserhql.append("from LogInfo where 1=1 ");

        if (!CommonUtils.isEmptyString(username)) {
            cuserhql.append(" and opuserid in ( select coachid from CuserInfo where realname like '%" + username + "%' or phone like '%" + username + "%')");
        }
        if (!CommonUtils.isEmptyString(formname)) {
            cuserhql.append(" and operateform like '%" + formname + "%'");
        }

        if (!CommonUtils.isEmptyString(starttime)) {
			starttime = starttime + " 00:00:00";
			cuserhql.append(" and operatetime > '" + starttime + "'");
		}

		if (!CommonUtils.isEmptyString(endtime)) {
			endtime = endtime + " 23:59:59";
			cuserhql.append(" and operatetime <= '" + endtime + "'");
		}
		
        List<LogInfo> cuserLogInfolist = (List<LogInfo>) dataDao.pageQueryViaParam(cuserhql.toString() + " order by logid desc ", pagesize, pageIndex, null);
        for (LogInfo couponlog : cuserLogInfolist) {
			CuserInfo Cuserinfo = dataDao.getObjectById(CuserInfo.class, couponlog.getOpuserid());
			if (Cuserinfo != null) {
				if (CommonUtils.isEmptyString(Cuserinfo.getRealname())) {
					couponlog.setOpusername("未设置:" + Cuserinfo.getPhone());
				} else {
					couponlog.setOpusername(Cuserinfo.getRealname() + ":" + Cuserinfo.getPhone());
				}
			}
			AdminInfo adminInfo = dataDao.getObjectById(AdminInfo.class, couponlog.getOperatorid());
			if (adminInfo != null) {
				couponlog.setOperatorname(adminInfo.getRealname());
			}
        }
        
        String counthql = " select count(*) " + cuserhql.toString();
        long total = (Long) dataDao.getFirstObjectViaParam(counthql, null);
        return new QueryResult<LogInfo>(cuserLogInfolist, total);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void checkPass(String coachid, int type,int userid) {
        StringBuffer cuserhql = new StringBuffer();
        cuserhql.append("from CuserInfo where coachid = :coachid");
        String[] params = {"coachid"};
        CuserInfo cuserInfo = (CuserInfo) dataDao.getFirstObjectViaParam(cuserhql.toString(), params, CommonUtils.parseInt(coachid, 0));
        cuserInfo.setState(type);
        dataDao.updateObject(cuserInfo);
        
        if(type==2){
	        LogInfo logInfo = new LogInfo();
	        logInfo.setOperatorid(userid);
	        logInfo.setOpuserid(Integer.parseInt(coachid));
	        logInfo.setOperatecontent("审核通过");
	        logInfo.setOperatetime(new Date());
	        logInfo.setOperateform("教练表");
	        dataDao.addObject(logInfo);
	        CoinRecordInfo tempCoinRecordInfo =new CoinRecordInfo();
			tempCoinRecordInfo.setOwnerid(CommonUtils.parseInt(coachid, 0));
			tempCoinRecordInfo.setOwnertype(2);
			tempCoinRecordInfo.setType(1);
			tempCoinRecordInfo.setCoinnum(0);
			tempCoinRecordInfo.setReceiverid(0);
			tempCoinRecordInfo.setReceivertype(0);
			tempCoinRecordInfo.setPayerid(0);
			tempCoinRecordInfo.setPayertype(0);
			dataDao.addObject(tempCoinRecordInfo);
        }
        
        StringBuffer cuserhql1 = new StringBuffer();
        cuserhql1.append("from RecommendInfo where invitedcoachid = :invitedcoachid");
        String[] params1 = {"invitedcoachid"};
        RecommendInfo tempRecommendInfo = (RecommendInfo) dataDao.getFirstObjectViaParam(cuserhql1.toString(), params1, CommonUtils.parseInt(coachid, 0));
        if (tempRecommendInfo != null) {
            if (type == 2) {
                tempRecommendInfo.setIschecked(1);
            }
            else {
                tempRecommendInfo.setIschecked(0);
            }
            dataDao.updateObject(tempRecommendInfo);
        }
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void setGmoneys(String coachid, BigDecimal gmoney) {
        CuserInfo cuserInfo = dataDao.getObjectById(CuserInfo.class, CommonUtils.parseInt(coachid, 0));
        cuserInfo.setGmoney(gmoney);
        dataDao.updateObject(cuserInfo);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void setLevels(String coachid, int level) {
        CuserInfo cuserInfo = dataDao.getObjectById(CuserInfo.class, CommonUtils.parseInt(coachid, 0));
        cuserInfo.setLevel(level);
        dataDao.updateObject(cuserInfo);
    }

    @SuppressWarnings("unchecked")
    @Override
    public QueryResult<CApplyCashInfo> getCoachApplyList(Integer pageIndex, int pagesize) {
        StringBuffer cuserhql = new StringBuffer();
        cuserhql.append("from CApplyCashInfo where state = 0 order by addtime desc");
        List<CApplyCashInfo> applycashlist = (List<CApplyCashInfo>) dataDao.pageQueryViaParam(cuserhql.toString(), pagesize, pageIndex, null);
        if (applycashlist != null && applycashlist.size() > 0) {
            for (CApplyCashInfo capplyCash : applycashlist) {
                CuserInfo coach = dataDao.getObjectById(CuserInfo.class, capplyCash.getCoachid());
                if (coach != null) {
                    capplyCash.setCoach(coach);
                }
            }
        }
        String counthql = " select count(*) " + cuserhql.toString();
        long total = (Long) dataDao.getFirstObjectViaParam(counthql, null);
        return new QueryResult<CApplyCashInfo>(applycashlist, total);
    }
    @SuppressWarnings("unchecked")
    @Override
    public QueryResult<CApplyCashInfo> getCoachApplyListFinance(Integer pageIndex, int pagesize) {
        StringBuffer cuserhql = new StringBuffer();
        cuserhql.append("from CApplyCashInfo where state = 1 and Date(addtime)>'2015-09-01' order by addtime desc");
        List<CApplyCashInfo> applycashlist = (List<CApplyCashInfo>) dataDao.pageQueryViaParam(cuserhql.toString(), pagesize, pageIndex, null);
        if (applycashlist != null && applycashlist.size() > 0) {
            for (CApplyCashInfo capplyCash : applycashlist) {
                CuserInfo coach = dataDao.getObjectById(CuserInfo.class, capplyCash.getCoachid());
                if (coach != null) {
                    capplyCash.setCoach(coach);
                }
            }
        }
        String counthql = " select count(*) " + cuserhql.toString();
        long total = (Long) dataDao.getFirstObjectViaParam(counthql, null);
        return new QueryResult<CApplyCashInfo>(applycashlist, total);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void applyCheckPass(int coachid) {
        CApplyCashInfo capplyCash = dataDao.getObjectById(CApplyCashInfo.class, coachid);
        Date todate=new Date();
        if (capplyCash != null) {
            capplyCash.setState(1);
            capplyCash.setUpdatetime(todate);
            dataDao.updateObject(capplyCash);
//            CuserInfo coach = dataDao.getObjectById(CuserInfo.class, capplyCash.getCoachid());
//            if (coach != null) {
//                coach.setFmoney(coach.getFmoney().subtract(capplyCash.getAmount()));
//                dataDao.updateObject(coach);
//            }
//            BalanceCoachInfo balancoach = new BalanceCoachInfo();
//            balancoach.setType(2);
//            balancoach.setAddtime(todate);
//            balancoach.setAmount(capplyCash.getAmount());
//            balancoach.setUserid(capplyCash.getCoachid());
//            balancoach.setAmount_out1(new BigDecimal(0));
//            balancoach.setAmount_out2(new BigDecimal(0));
//            dataDao.addObject(balancoach);
            if (capplyCash.getSchoolid() != null && capplyCash.getSchoolid() > 0) {
                DriveSchoolInfo schoolinfo = dataDao.getObjectById(DriveSchoolInfo.class, capplyCash.getSchoolid());
                if (schoolinfo != null) {
                    schoolinfo.setMoney(schoolinfo.getMoney().add(capplyCash.getAmount()));
                    dataDao.updateObject(schoolinfo);
                    SchoolBalance schoolbalance = new SchoolBalance();
                    schoolbalance.setAddtime(todate);
                    schoolbalance.setCoachid(coachid);
                    schoolbalance.setAmount(capplyCash.getAmount());
                    schoolbalance.setSchoolid(capplyCash.getSchoolid());
                    schoolbalance.setType(1);
                    dataDao.addObject(schoolbalance);
                }
            }
        }
    }
    
    //教练提现审核不通过
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void applyCheckNoPass(int coachid) {
        CApplyCashInfo capplyCash = dataDao.getObjectById(CApplyCashInfo.class, coachid);
        Date todate=new Date();
        if (capplyCash != null) {
            capplyCash.setState(3);
            capplyCash.setUpdatetime(todate);
            dataDao.updateObject(capplyCash);
            
            CuserInfo coach = dataDao.getObjectById(CuserInfo.class, capplyCash.getCoachid());
            if (coach != null) {
                coach.setFmoney(coach.getFmoney().subtract(capplyCash.getAmount()));
                //增加教练可提现余额
                coach.setMoney(coach.getMoney().add(capplyCash.getAmount()));
                dataDao.updateObject(coach);
            }
            
            BalanceCoachInfo balancoach = new BalanceCoachInfo();
            balancoach.setType(6);
            balancoach.setAddtime(todate);
            balancoach.setAmount(capplyCash.getAmount());  
            balancoach.setUserid(capplyCash.getCoachid());
            balancoach.setAmount_out1(new BigDecimal(0));
            balancoach.setAmount_out2(new BigDecimal(0));
            dataDao.addObject(balancoach);
            /*
            if (capplyCash.getSchoolid() != null && capplyCash.getSchoolid() > 0) {
                DriveSchoolInfo schoolinfo = dataDao.getObjectById(DriveSchoolInfo.class, capplyCash.getSchoolid());
                if (schoolinfo != null) {
                    schoolinfo.setMoney(schoolinfo.getMoney().add(capplyCash.getAmount()));
                    dataDao.updateObject(schoolinfo);
                    SchoolBalance schoolbalance = new SchoolBalance();
                    schoolbalance.setAddtime(new Date());
                    schoolbalance.setCoachid(coachid);
                    schoolbalance.setAmount(capplyCash.getAmount());
                    schoolbalance.setSchoolid(capplyCash.getSchoolid());
                    schoolbalance.setType(1);
                    dataDao.addObject(schoolbalance);
                }
            }
            */
        }
    }
    
    
    //教练提现作废
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void applyCheckrevocation(int coachid) {
        CApplyCashInfo capplyCash = dataDao.getObjectById(CApplyCashInfo.class, coachid);
        Date todate=new Date();
        if (capplyCash != null) {
            capplyCash.setState(4);
            capplyCash.setUpdatetime(todate);
            dataDao.updateObject(capplyCash);
            
            CuserInfo coach = dataDao.getObjectById(CuserInfo.class, capplyCash.getCoachid());
            if (coach != null) {
                coach.setFmoney(coach.getFmoney().subtract(capplyCash.getAmount()));
                dataDao.updateObject(coach);
            }
            /*
            BalanceCoachInfo balancoach = new BalanceCoachInfo();
            balancoach.setType(2);
            balancoach.setAddtime(new Date());
            balancoach.setAmount(capplyCash.getAmount());
            balancoach.setUserid(capplyCash.getCoachid());
            balancoach.setAmount_out1(new BigDecimal(0));
            balancoach.setAmount_out2(new BigDecimal(0));
            dataDao.addObject(balancoach);
            */
            /*
            if (capplyCash.getSchoolid() != null && capplyCash.getSchoolid() > 0) {
                DriveSchoolInfo schoolinfo = dataDao.getObjectById(DriveSchoolInfo.class, capplyCash.getSchoolid());
                if (schoolinfo != null) {
                    schoolinfo.setMoney(schoolinfo.getMoney().add(capplyCash.getAmount()));
                    dataDao.updateObject(schoolinfo);
                    SchoolBalance schoolbalance = new SchoolBalance();
                    schoolbalance.setAddtime(new Date());
                    schoolbalance.setCoachid(coachid);
                    schoolbalance.setAmount(capplyCash.getAmount());
                    schoolbalance.setSchoolid(capplyCash.getSchoolid());
                    schoolbalance.setType(1);
                    dataDao.addObject(schoolbalance);
                }
            }
            */
        }
    }
    //教练提现返审
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void applyCheckback(int applyid) {
		CApplyCashInfo capplyCash = dataDao.getObjectById(CApplyCashInfo.class, applyid);
		String querystring="from BalanceCoachInfo where addtime=:addtime";
		String querystring1="from SchoolBalance where addtime=:addtime";
		String[] params={"addtime"};
		Date searchdate=capplyCash.getUpdatetime();
        if (capplyCash != null) {
            capplyCash.setState(0);
            dataDao.updateObject(capplyCash);
//            CuserInfo coach = dataDao.getObjectById(CuserInfo.class, capplyCash.getCoachid());
//            if (coach != null) {
//                coach.setFmoney(coach.getFmoney().add(capplyCash.getAmount()));
//                dataDao.updateObject(coach);
//            }
            if (capplyCash.getSchoolid() != null && capplyCash.getSchoolid() > 0) {
                DriveSchoolInfo schoolinfo = dataDao.getObjectById(DriveSchoolInfo.class, capplyCash.getSchoolid());
                if (schoolinfo != null) {
                    schoolinfo.setMoney(schoolinfo.getMoney().subtract(capplyCash.getAmount()));
                    dataDao.updateObject(schoolinfo);
                }
            }
        }
	}
    //教练提现二次审批通过，由财务发起
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void applyCheckPassTwice(int applyid) {
        CApplyCashInfo capplyCash = dataDao.getObjectById(CApplyCashInfo.class, applyid);
        Date todate=new Date();
        if (capplyCash != null) {
            capplyCash.setState(5);
            capplyCash.setUpdatetime(todate);
            CuserInfo coach = dataDao.getObjectById(CuserInfo.class, capplyCash.getCoachid());
	          if (coach != null) {
	              coach.setFmoney(coach.getFmoney().subtract(capplyCash.getAmount()));
	              dataDao.updateObject(coach);
	          }
            dataDao.updateObject(capplyCash); 
 
            BalanceCoachInfo balancoach = new BalanceCoachInfo();
            balancoach.setType(7);
            balancoach.setAddtime(todate);
            balancoach.setAmount(capplyCash.getAmount());
            balancoach.setUserid(capplyCash.getCoachid());
            balancoach.setAmount_out1(new BigDecimal(0));
            balancoach.setAmount_out2(new BigDecimal(0));
            dataDao.addObject(balancoach);
//            if (capplyCash.getSchoolid() != null && capplyCash.getSchoolid() > 0) {
//                DriveSchoolInfo schoolinfo = dataDao.getObjectById(DriveSchoolInfo.class, capplyCash.getSchoolid());
//                if (schoolinfo != null) {
//                    SchoolBalance schoolbalance = new SchoolBalance();
//                    schoolbalance.setAddtime(todate);
//                    schoolbalance.setCoachid(applyid);
//                    schoolbalance.setAmount(capplyCash.getAmount());
//                    schoolbalance.setSchoolid(capplyCash.getSchoolid());
//                    schoolbalance.setType(1);
//                    dataDao.addObject(schoolbalance);
//                }
//            }
        }
    }
    
    @SuppressWarnings({"unchecked", "deprecation"})
    @Override
    public QueryResult<CApplyCashInfo> getCoachApplyBySearch(String searchname, String searchphone, String amount, String inputamount, Integer schoolid, String minsdate, String maxsdate, Integer state,Integer pageIndex, int pagesize) {
        StringBuffer cuserhql = new StringBuffer();
        cuserhql.append("from CApplyCashInfo where amount > 0 and Date(addtime)>'2015-09-01'");
        if (!CommonUtils.isEmptyString(searchname)) {
            cuserhql.append(" and coachid in (select coachid from CuserInfo where realname like '%" + searchname + "%')");
        }
        if (!CommonUtils.isEmptyString(searchphone)) {
            cuserhql.append(" and coachid in (select coachid from CuserInfo where phone like '%" + searchphone + "%')");
        }
        if (CommonUtils.parseFloat(inputamount, 0) != 0) {
            if (CommonUtils.parseInt(amount, -1) == 0) {
                cuserhql.append("and amount >" + inputamount);
            }
            else if (CommonUtils.parseInt(amount, -1) == 1) {
                cuserhql.append("and amount =" + inputamount);
            }
            else {
                cuserhql.append("and amount <" + inputamount);
            }
        }
        if (schoolid != null) {
            cuserhql.append(" and schoolid =" + schoolid);
        }
        if (!CommonUtils.isEmptyString(minsdate)) {
            cuserhql.append("and addtime >'" + minsdate + "'");
        }
        if (!CommonUtils.isEmptyString(maxsdate)) {
            Date enddate = CommonUtils.getDateFormat(maxsdate, "yyyy-MM-dd");
            enddate.setHours(23);
            enddate.setMinutes(59);
            enddate.setSeconds(59);
            String endmaxstime = CommonUtils.getTimeFormat(enddate, "yyyy-MM-dd HH:mm:ss");
            cuserhql.append("and addtime <'" + endmaxstime + "'");
        }
        if (state != null) {
            cuserhql.append(" and state =" + state);
        }
        cuserhql.append("order by addtime desc");
        List<CApplyCashInfo> applycashlist = (List<CApplyCashInfo>) dataDao.pageQueryViaParam(cuserhql.toString(), pagesize, pageIndex, null);
        if (applycashlist != null && applycashlist.size() > 0) {
            for (CApplyCashInfo capplyCash : applycashlist) {
                CuserInfo coach = dataDao.getObjectById(CuserInfo.class, capplyCash.getCoachid());
                if (coach != null) {
                    capplyCash.setCoach(coach);
                }
            }
        }
        String counthql = " select count(*) " + cuserhql.toString();
        long total = (Long) dataDao.getFirstObjectViaParam(counthql, null);
        return new QueryResult<CApplyCashInfo>(applycashlist, total);
    }

    @SuppressWarnings("unchecked")
    @Override
    public QueryResult<BalanceCoachInfo> getApplyRecordList(Integer pageIndex, int pagesize) {
        StringBuffer cuserhql = new StringBuffer();
        cuserhql.append("from BalanceCoachInfo order by addtime desc");
        List<BalanceCoachInfo> balancecoachlist = (List<BalanceCoachInfo>) dataDao.pageQueryViaParam(cuserhql.toString(), pagesize, pageIndex, null);
        if (balancecoachlist != null && balancecoachlist.size() > 0) {
            for (BalanceCoachInfo balanceCoach : balancecoachlist) {
                CuserInfo coach = dataDao.getObjectById(CuserInfo.class, balanceCoach.getUserid());
                if (coach != null) {
                    balanceCoach.setRealname(coach.getRealname());
                    balanceCoach.setPhone(coach.getPhone());
                }
            }
        }
        String counthql = " select count(*) " + cuserhql.toString();
        long total = (Long) dataDao.getFirstObjectViaParam(counthql, null);
        return new QueryResult<BalanceCoachInfo>(balancecoachlist, total);
    }

    @SuppressWarnings({"unchecked", "deprecation"})
    @Override
    public QueryResult<BalanceCoachInfo> getCoachBalanceBySearch(int schoolid, String searchname, String searchphone, String amount, String inputamount, String minsdate, String maxsdate, Integer pageIndex, int pagesize) {
        StringBuffer cuserhql = new StringBuffer();
        cuserhql.append("from BalanceCoachInfo where 1=1");
        if (schoolid > 0) {
            cuserhql.append(" and userid in (select coachid from CuserInfo where drive_schoolid = " + schoolid + ")");
        }
        if (!CommonUtils.isEmptyString(searchname)) {
            cuserhql.append(" and userid in (select coachid from CuserInfo where realname like '%" + searchname + "%')");
        }
        if (!CommonUtils.isEmptyString(searchphone)) {
            cuserhql.append(" and userid in (select coachid from CuserInfo where phone like '%" + searchphone + "%')");
        }
        if (CommonUtils.parseFloat(inputamount, 0) != 0) {
            if (CommonUtils.parseInt(amount, -1) == 0) {
                cuserhql.append("and amount >" + inputamount);
            }
            else if (CommonUtils.parseInt(amount, -1) == 1) {
                cuserhql.append("and amount =" + inputamount);
            }
            else {
                cuserhql.append("and amount <" + inputamount);
            }
        }
        if (!CommonUtils.isEmptyString(minsdate)) {
            cuserhql.append("and addtime >'" + minsdate + "'");
        }
        if (!CommonUtils.isEmptyString(maxsdate)) {
            Date enddate = CommonUtils.getDateFormat(maxsdate, "yyyy-MM-dd");
            enddate.setHours(23);
            enddate.setMinutes(59);
            enddate.setSeconds(59);
            String endmaxstime = CommonUtils.getTimeFormat(enddate, "yyyy-MM-dd HH:mm:ss");
            cuserhql.append("and addtime <'" + endmaxstime + "'");
        }
        cuserhql.append("order by addtime desc");
        List<BalanceCoachInfo> applycashlist = (List<BalanceCoachInfo>) dataDao.pageQueryViaParam(cuserhql.toString(), pagesize, pageIndex, null);
        if (applycashlist != null && applycashlist.size() > 0) {
            for (BalanceCoachInfo capplyCash : applycashlist) {
                CuserInfo coach = dataDao.getObjectById(CuserInfo.class, capplyCash.getUserid());
                if (coach != null) {
                    capplyCash.setRealname(coach.getRealname());
                    capplyCash.setPhone(coach.getPhone());
                }
            }
        }
        String counthql = " select count(*) " + cuserhql.toString();
        long total = (Long) dataDao.getFirstObjectViaParam(counthql, null);
        return new QueryResult<BalanceCoachInfo>(applycashlist, total);
    }



    @SuppressWarnings({"unchecked", "deprecation"})
    @Override
    public QueryResult<CApplyCashInfo> getCoachHistoryApplyBySearch(int schoolid, String searchname, String searchphone, String amount, String inputamount, String minsdate, String maxsdate, Integer pageIndex, int pagesize) {
        StringBuffer cuserhql = new StringBuffer();
        cuserhql.append("from CApplyCashInfo where state=5 ");
        if (schoolid > 0) {
            cuserhql.append(" and coachid in (select coachid from CuserInfo where drive_schoolid = " + schoolid + ")");
        }
        if (!CommonUtils.isEmptyString(searchname)) {
            cuserhql.append(" and coachid in (select coachid from CuserInfo where realname like '%" + searchname + "%')");
        }
        if (!CommonUtils.isEmptyString(searchphone)) {
            cuserhql.append(" and coachid in (select coachid from CuserInfo where phone like '%" + searchphone + "%')");
        }
        if (CommonUtils.parseFloat(inputamount, 0) != 0) {
            if (CommonUtils.parseInt(amount, -1) == 0) {
                cuserhql.append("and amount >" + inputamount);
            }
            else if (CommonUtils.parseInt(amount, -1) == 1) {
                cuserhql.append("and amount =" + inputamount);
            }
            else {
                cuserhql.append("and amount <" + inputamount);
            }
        }
        if (!CommonUtils.isEmptyString(minsdate)) {
            cuserhql.append("and updatetime >'" + minsdate + "'");
        }
        if (!CommonUtils.isEmptyString(maxsdate)) {
            Date enddate = CommonUtils.getDateFormat(maxsdate, "yyyy-MM-dd");
            enddate.setHours(23);
            enddate.setMinutes(59);
            enddate.setSeconds(59);
            String endmaxstime = CommonUtils.getTimeFormat(enddate, "yyyy-MM-dd HH:mm:ss");
            cuserhql.append("and updatetime <'" + endmaxstime + "'");
        }
        cuserhql.append("order by updatetime desc");
        List<CApplyCashInfo> applycashlist = (List<CApplyCashInfo>) dataDao.pageQueryViaParam(cuserhql.toString(), pagesize, pageIndex, null);
        if (applycashlist != null && applycashlist.size() > 0) {
            for (CApplyCashInfo capplyCash : applycashlist) {
                CuserInfo coach = dataDao.getObjectById(CuserInfo.class, capplyCash.getCoachid());
                if (coach != null) {
                    capplyCash.setRealname(coach.getRealname());
                    capplyCash.setPhone(coach.getPhone());
                    capplyCash.setAlipay_account(coach.getAlipay_account());
                }
            }
        }
        String counthql = " select count(*) " + cuserhql.toString();
        long total = (Long) dataDao.getFirstObjectViaParam(counthql, null);
        return new QueryResult<CApplyCashInfo>(applycashlist, total);
    }
    
    //获取教练提现二次审核通过的历史记录，由财务发起
    @SuppressWarnings({"unchecked", "deprecation"})
    @Override
    public QueryResult<CApplyCashInfo> getCoachHistoryBalanceBySearchFinance(int schoolid, String searchname, String searchphone, String amount, String inputamount, String minsdate, String maxsdate, Integer pageIndex, int pagesize)
    {
    	StringBuffer cuserhql = new StringBuffer();
        cuserhql.append("from CApplyCashInfo where state=5 ");
        if (schoolid > 0) {
            cuserhql.append(" and coachid in (select coachid from CuserInfo where drive_schoolid = " + schoolid + ")");
        }
        if (!CommonUtils.isEmptyString(searchname)) {
            cuserhql.append(" and coachid in (select coachid from CuserInfo where realname like '%" + searchname + "%')");
        }
        if (!CommonUtils.isEmptyString(searchphone)) {
            cuserhql.append(" and coachid in (select coachid from CuserInfo where phone like '%" + searchphone + "%')");
        }
        if (CommonUtils.parseFloat(inputamount, 0) != 0) {
            if (CommonUtils.parseInt(amount, -1) == 0) {
                cuserhql.append("and amount >" + inputamount);
            }
            else if (CommonUtils.parseInt(amount, -1) == 1) {
                cuserhql.append("and amount =" + inputamount);
            }
            else {
                cuserhql.append("and amount <" + inputamount);
            }
        }
        if (!CommonUtils.isEmptyString(minsdate)) {
            cuserhql.append("and updatetime >'" + minsdate + "'");
        }
        if (!CommonUtils.isEmptyString(maxsdate)) {
            Date enddate = CommonUtils.getDateFormat(maxsdate, "yyyy-MM-dd");
            enddate.setHours(23);
            enddate.setMinutes(59);
            enddate.setSeconds(59);
            String endmaxstime = CommonUtils.getTimeFormat(enddate, "yyyy-MM-dd HH:mm:ss");
            cuserhql.append("and updatetime <'" + endmaxstime + "'");
        }
        cuserhql.append("order by updatetime desc");
        List<CApplyCashInfo> applycashlist = (List<CApplyCashInfo>) dataDao.pageQueryViaParam(cuserhql.toString(), pagesize, pageIndex, null);
        if (applycashlist != null && applycashlist.size() > 0) {
            for (CApplyCashInfo capplyCash : applycashlist) {
                CuserInfo coach = dataDao.getObjectById(CuserInfo.class, capplyCash.getCoachid());
                if (coach != null) {
                    capplyCash.setRealname(coach.getRealname());
                    capplyCash.setPhone(coach.getPhone());
                    capplyCash.setAlipay_account(coach.getAlipay_account());
                }
            }
        }
        String counthql = " select count(*) " + cuserhql.toString();
        long total = (Long) dataDao.getFirstObjectViaParam(counthql, null);
        return new QueryResult<CApplyCashInfo>(applycashlist, total);
    }


    @SuppressWarnings("unchecked")
    @Override
    public QueryResult<ModelsInfo> getModellist(Integer pageIndex, int pagesize) {
        StringBuffer cuserhql = new StringBuffer();
        cuserhql.append("from ModelsInfo ");
        List<ModelsInfo> modelsInfo = (List<ModelsInfo>) dataDao.pageQueryViaParam(cuserhql.toString(), pagesize, pageIndex, null);
        String counthql = " select count(*) " + cuserhql.toString();
        long total = (Long) dataDao.getFirstObjectViaParam(counthql, null);
        return new QueryResult<ModelsInfo>(modelsInfo, total);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ModelsInfo> getModelList() {
        StringBuffer cuserhql = new StringBuffer();
        cuserhql.append("from ModelsInfo ");
        List<ModelsInfo> modelsInfo = (List<ModelsInfo>) dataDao.getObjectsViaParam(cuserhql.toString(), null);
        return modelsInfo;
    }

    @Override
    public ModelsInfo getModelsInfoBymodelname(String modelname) {
        StringBuffer cuserhql = new StringBuffer();
        cuserhql.append("from ModelsInfo where modelname = :modelname ");
        String[] params = {"modelname"};
        ModelsInfo modelinfo = (ModelsInfo) dataDao.getFirstObjectViaParam(cuserhql.toString(), params, modelname);
        return modelinfo;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<CoachLevelInfo> getLevellist() {
        StringBuffer cuserhql = new StringBuffer();
        cuserhql.append("from CoachLevelInfo ");
        List<CoachLevelInfo> coachLevelInfo = (List<CoachLevelInfo>) dataDao.getObjectsViaParam(cuserhql.toString(), null);
        return coachLevelInfo;
    }

    @Override
    public CoachLevelInfo getLevelInfoBylevelname(String levelname) {
        StringBuffer cuserhql = new StringBuffer();
        cuserhql.append("from CoachLevelInfo where levelname = :levelname ");
        String[] params = {"levelname"};
        CoachLevelInfo coachLevelInfo = (CoachLevelInfo) dataDao.getFirstObjectViaParam(cuserhql.toString(), params, levelname);
        return coachLevelInfo;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void delObject(Object object) {
        dataDao.deleteObject(object);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void addObject(Object object) {
        dataDao.addObject(object);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void updateObject(Object object) {
        dataDao.updateObject(object);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ComplaintSetInfo> getComplaintSetInfolist() {
        StringBuffer cuserhql = new StringBuffer();
        cuserhql.append("from ComplaintSetInfo ");
        List<ComplaintSetInfo> complaintset = (List<ComplaintSetInfo>) dataDao.getObjectsViaParam(cuserhql.toString(), null);
        return complaintset;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ComplaintSetInfo> getComplaintSetByType(int searchtype) {
        StringBuffer cuserhql = new StringBuffer();
        cuserhql.append("from ComplaintSetInfo ");
        if (searchtype == 1) {
            cuserhql.append(" where type = 1 ");
            List<ComplaintSetInfo> complaintset = (List<ComplaintSetInfo>) dataDao.getObjectsViaParam(cuserhql.toString(), null);
            return complaintset;
        }
        else if (searchtype == 2) {
            cuserhql.append(" where type = 2 ");
            List<ComplaintSetInfo> complaintset = (List<ComplaintSetInfo>) dataDao.getObjectsViaParam(cuserhql.toString(), null);
            return complaintset;
        }
        return null;
    }

    @Override
    public ComplaintSetInfo getComplaintSetInfoByid(int setid) {
        ComplaintSetInfo complaintset = dataDao.getObjectById(ComplaintSetInfo.class, setid);
        return complaintset;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<CsubjectInfo> getSubjectInfo() {
        StringBuffer cuserhql = new StringBuffer();
        cuserhql.append("from CsubjectInfo ");
        List<CsubjectInfo> subjectlist = (List<CsubjectInfo>) dataDao.getObjectsViaParam(cuserhql.toString(), null);
        return subjectlist;
    }

    @Override
    public CsubjectInfo getSubjectById(int subjectid) {
        CsubjectInfo subjectInfo = dataDao.getObjectById(CsubjectInfo.class, subjectid);
        return subjectInfo;
    }

    @Override
    public CsubjectInfo getSubjectByName(String subjectname) {
        StringBuffer cuserhql = new StringBuffer();
        cuserhql.append("from CsubjectInfo where subjectname = :subjectname ");
        String[] params = {"subjectname"};
        CsubjectInfo subjectInfo = (CsubjectInfo) dataDao.getFirstObjectViaParam(cuserhql.toString(), params, subjectname);
        return subjectInfo;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DriveSchoolInfo> getDriveSchoolInfo() {
        StringBuffer cuserhql = new StringBuffer();
        cuserhql.append("from DriveSchoolInfo ");
        List<DriveSchoolInfo> driveSchoolInfo = (List<DriveSchoolInfo>) dataDao.getObjectsViaParam(cuserhql.toString(), null);
        return driveSchoolInfo;
    }
    @SuppressWarnings("unchecked")
    @Override
    public List<DriveSchoolInfo> getDriveSchoolInfoByCityId(String cityid) {
        StringBuffer cuserhql = new StringBuffer();
        cuserhql.append("from DriveSchoolInfo where cityid=:cityid or cityid='0'");
        String p[]={"cityid"};
        List<DriveSchoolInfo> driveSchoolInfo = (List<DriveSchoolInfo>) dataDao.getObjectsViaParam(cuserhql.toString(), p,cityid);
        return driveSchoolInfo;
    }

    @Override
    public SystemSetInfo getSystemSetInfo() {
        StringBuffer cuserhql = new StringBuffer();
        cuserhql.append("from SystemSetInfo ");
        SystemSetInfo systemSetInfo = (SystemSetInfo) dataDao.getFirstObjectViaParam(cuserhql.toString(), null);
        return systemSetInfo;
    }

    @Override
    public SystemSetInfo getSystemSetInfoByid(int dateid) {
        SystemSetInfo systemSetInfo = dataDao.getObjectById(SystemSetInfo.class, dateid);
        return systemSetInfo;
    }

    @Override
    @Transactional
    public List<CuserInfo> getCoachlist() {
        StringBuffer cuserhql = new StringBuffer();
        cuserhql.append("from CuserInfo ");
        @SuppressWarnings("unchecked") List<CuserInfo> cuserlist = (List<CuserInfo>) dataDao.getObjectsViaParam(cuserhql.toString(), null);
        return cuserlist;
    }

    @Override
    @Transactional
    public List<SuserInfo> getStudentlist() {
        StringBuffer cuserhql = new StringBuffer();
        cuserhql.append("from SuserInfo ");
        @SuppressWarnings("unchecked") List<SuserInfo> suserlist = (List<SuserInfo>) dataDao.getObjectsViaParam(cuserhql.toString(), null);
        return suserlist;
    }

    @Override
    public AdminInfo getAdminInfo(String loginname) {
        StringBuffer cuserhql = new StringBuffer();
        cuserhql.append("from AdminInfo where login_account = :login_account");
        String[] params = {"login_account"};
        AdminInfo adminInfo = (AdminInfo) dataDao.getFirstObjectViaParam(cuserhql.toString(), params, loginname);
        return adminInfo;
    }

    @Override
    public CApplyCashInfo getApplyById(int applyid) {
        return dataDao.getObjectById(CApplyCashInfo.class, applyid);
    }

    @Override
    @Transactional
    public UserPushInfo getUserPushInfo(int userid, int usertype) {
        StringBuffer cuserhql = new StringBuffer();
        cuserhql.append("from UserPushInfo where userid = :userid and usertype = :usertype");
        String[] params = {"userid", "usertype"};
        UserPushInfo userPushInfo = (UserPushInfo) dataDao.getFirstObjectViaParam(cuserhql.toString(), params, userid, usertype);
        return userPushInfo;
    }

    @Override
    public DriveSchoolInfo getDriveSchoolInfoByid(int id) {
        DriveSchoolInfo driveSchoolInfo = dataDao.getObjectById(DriveSchoolInfo.class, id);
        return driveSchoolInfo;
    }

    @Override
    public CaddAddressInfo getCurrentAddressBycoachid(String coachid) {
        StringBuffer cuserhql = new StringBuffer();
        cuserhql.append("from CaddAddressInfo where coachid =:coachid and iscurrent = 1");
        String[] params = {"coachid"};
        CaddAddressInfo caddAddressInfo = (CaddAddressInfo) dataDao.getFirstObjectViaParam(cuserhql.toString(), params, CommonUtils.parseInt(coachid, 0));
        return caddAddressInfo;
    }

    @Override
    public CuserInfo getCuserById_cardnum(String id_cardnum) {
        StringBuffer cuserhql = new StringBuffer();
        cuserhql.append("from CuserInfo where id_cardnum =:id_cardnum ");
        String[] params = {"id_cardnum"};
        CuserInfo coach = (CuserInfo) dataDao.getFirstObjectViaParam(cuserhql.toString(), params, id_cardnum);
        return coach;
    }

    @Override
    public CuserInfo getCuserByCoach_cardnum(String coach_cardnum) {
        StringBuffer cuserhql = new StringBuffer();
        cuserhql.append("from CuserInfo where coach_cardnum =:coach_cardnum ");
        String[] params = {"coach_cardnum"};
        CuserInfo coach = (CuserInfo) dataDao.getFirstObjectViaParam(cuserhql.toString(), params, coach_cardnum);
        return coach;
    }

    @Override
    public CuserInfo getCuserByCar_cardnum(String car_cardnum) {
        StringBuffer cuserhql = new StringBuffer();
        cuserhql.append("from CuserInfo where car_cardnum =:car_cardnum ");
        String[] params = {"car_cardnum"};
        CuserInfo aaa = (CuserInfo) dataDao.getFirstObjectViaParam(cuserhql.toString(), params, car_cardnum);
        return aaa;
    }

    @Override
    public CuserInfo getCuserByBrive_cardnum(String drive_cardnum) {
        StringBuffer cuserhql = new StringBuffer();
        cuserhql.append("from CuserInfo where drive_cardnum =:drive_cardnum ");
        String[] params = {"drive_cardnum"};
        CuserInfo coach = (CuserInfo) dataDao.getFirstObjectViaParam(cuserhql.toString(), params, drive_cardnum);
        return coach;
    }

    @Override
    public CuserInfo getCoachByid(int id) {
        CuserInfo coach = dataDao.getObjectById(CuserInfo.class, id);
        return coach;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<TeachcarInfo> getTeachcarInfolist() {
        StringBuffer cuserhql = new StringBuffer();
        cuserhql.append("from TeachcarInfo ");
        List<TeachcarInfo> teachcarInfolist = (List<TeachcarInfo>) dataDao.getObjectsViaParam(cuserhql.toString(), null);
        return teachcarInfolist;
    }

    @Override
    public TeachcarInfo getTeachcarInfoBymodelname(String modelname) {
        StringBuffer cuserhql = new StringBuffer();
        cuserhql.append("from TeachcarInfo where modelname = :modelname");
        String[] params = {"modelname"};
        TeachcarInfo teachcarInfo = (TeachcarInfo) dataDao.getFirstObjectViaParam(cuserhql.toString(), params, modelname);
        return teachcarInfo;
    }

    @Override
    public int getCoachAgeByid(int id) {
        StringBuffer cuserhql = new StringBuffer();
        cuserhql.append(" select year(now())-year(birthday) as age  from CuserInfo where coachid = :coachid");
        String[] params = {"coachid"};
        int count = (Integer) dataDao.getFirstObjectViaParam(cuserhql.toString(), params, id);
        return count;
    }

    @Override
    public CuserInfo getCoachByIdnum(String idnum) {
        StringBuffer cuserhql = new StringBuffer();
        cuserhql.append("from CuserInfo where id_cardnum = :id_cardnum ");
        String[] params = {"id_cardnum"};
        CuserInfo cuser = (CuserInfo) dataDao.getFirstObjectViaParam(cuserhql.toString(), params, idnum);
        return cuser;
    }

    // 充值
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public HashMap<String, Object> recharge(String coachid, String amount) {

        HashMap<String, Object> result = new HashMap<String, Object>();

        // 插入数据
        RechargeRecordInfo info = new RechargeRecordInfo();
        info.setAddtime(new Date());
        info.setAmount(BigDecimal.valueOf(CommonUtils.parseFloat(amount, 0f)));
        info.setType(1);
        info.setUserid(CommonUtils.parseInt(coachid, 0));
        dataDao.addObject(info);

        if ("1".equals(CommonUtils.getAliSet())) {
            result.put("partner", AlipayConfig.partner);
        }
        else {
            result.put("partner", AlipayConfig.partner_formal);
        }

        result.put("notify_url", CommonUtils.getWebRootUrl() + "alipay_callback");
        result.put("out_trade_no", info.getRechargeid());
        result.put("subject", "小巴教练充值：" + amount + "元");
        if ("1".equals(CommonUtils.getAliSet())) {
            result.put("seller_id", AlipayConfig.seller_id);
        }
        else {
            result.put("seller_id", AlipayConfig.seller_id_formal);
        }

        result.put("total_fee", amount);
        result.put("body", "小巴教练充值：" + amount + "元");
        if ("1".equals(CommonUtils.getAliSet())) {
            result.put("private_key", AlipayConfig.private_key);
        }
        else {
            result.put("private_key", AlipayConfig.private_key_formal);
        }

        return result;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void buySuccess(String out_trade_no, String buyer_id, String buyer_email) {
        RechargeRecordInfo info = dataDao.getObjectById(RechargeRecordInfo.class, CommonUtils.parseInt(out_trade_no, 0));
        if (info != null) {

            int type = info.getType();
            if (type == 1) {
                CuserInfo user = dataDao.getObjectById(CuserInfo.class, info.getUserid());
                if (user != null) {
                    user.setMoney(user.getMoney().add(info.getAmount()));
                    dataDao.updateObject(user);
                    // 插入充值记录
                    BalanceCoachInfo balance = new BalanceCoachInfo();
                    balance.setAddtime(new Date());
                    balance.setAmount(info.getAmount());
                    balance.setUserid(user.getCoachid());
                    balance.setType(3);
                    balance.setAmount_out1(new BigDecimal(0));
                    balance.setAmount_out2(new BigDecimal(0));
                    dataDao.addObject(balance);
                }
            }
            else if (type == 2) {
                SuserInfo user = dataDao.getObjectById(SuserInfo.class, info.getUserid());
                if (user != null) {
                    user.setMoney(user.getMoney().add(info.getAmount()));
                    dataDao.updateObject(user);
                    // 插入充值记录
                    BalanceStudentInfo balance = new BalanceStudentInfo();
                    balance.setAddtime(new Date());
                    balance.setAmount(info.getAmount());
                    balance.setUserid(user.getStudentid());
                    balance.setType(1);
                    dataDao.addObject(balance);
                }
            }

            info.setUpdatetime(new Date());
            info.setBuyer_email(buyer_email);
            info.setBuyer_id(buyer_id);
            info.setState(1);
            dataDao.updateObject(info);

        }
    }

    @Override
    public HashMap<String, Object> getBalanceList(String coachid) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        CuserInfo user = dataDao.getObjectById(CuserInfo.class, CommonUtils.parseInt(coachid, 0));
        if (user != null) {
            result.put("balance", user.getMoney());
            result.put("fmoney", user.getFmoney());
            result.put("gmoney", user.getGmoney());
            String hql = "from BalanceCoachInfo where userid =:userid and amount>0 order by addtime desc";
            String[] params = {"userid"};
            List<BalanceCoachInfo> list = (List<BalanceCoachInfo>) dataDao.getObjectsViaParam(hql, params, CommonUtils.parseInt(coachid, 0));
            if (list != null) {
                result.put("recordlist", list);
            }

        }
        else {
            result.put("code", 2);
            result.put("message", "用户不存在");
        }
        return result;
    }


    
    public HashMap<String, Object> getCoinRecordList2(String coachid) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        CuserInfo user = dataDao.getObjectById(CuserInfo.class, CommonUtils.parseInt(coachid, 0));
        if (user != null) {
            result.put("coinnum", user.getCoinnum());
            String hql = "from CoinRecordInfo  where (receiverid =:receiverid and receivertype="+ UserType.COAH+" ) or (payerid =:payerid and payertype="+ UserType.COAH+")  order by addtime desc";
            //String hql = "from CoinRecordInfo a left join SuserInfo b on a.payerid=b.studentid   where (receiverid =:receiverid and receivertype="+ UserType.COAH+" ) or (payerid =:payerid and payertype="+ UserType.COAH+")  order by addtime desc";
            String[] params = {"receiverid", "payerid"};

            Integer cid = CommonUtils.parseInt(coachid, 0);
            List<CoinRecordInfo> list = (List<CoinRecordInfo>) dataDao.getObjectsViaParam(hql, params, cid, cid);
            if (list != null) {
                result.put("recordlist", list);
            }

        }
        else {
            result.put("code", 2);
            result.put("message", "用户不存在");
        }
        return result;
    }
    
    
    public HashMap<String, Object> getCoinRecordList(String coachid) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        CuserInfo user = dataDao.getObjectById(CuserInfo.class, CommonUtils.parseInt(coachid, 0));
        if (user != null) {
            result.put("coinnum", user.getCoinnum());
            Integer cid = CommonUtils.parseInt(coachid, 0);
            String hql = "from CoinRecordInfo  where (receiverid =:receiverid and receivertype="+ UserType.COAH+" ) or (payerid =:payerid and payertype="+ UserType.COAH+")  order by addtime desc";
            //System.out.println(hql);
            //String hql = "select a.*,b.realname as studentname from t_coin_record  a left join t_user_student b on a.payerid=b.studentid  where (receiverid ="+cid+" and receivertype="+ UserType.COAH+" ) or (payerid ="+cid+" and payertype="+ UserType.COAH+")  order by addtime desc";
           // System.out.println(hql);
            String[] params = {"receiverid", "payerid"};
            
            List<CoinRecordInfo> list = (List<CoinRecordInfo>) dataDao.getObjectsViaParam(hql, params, cid, cid);
           /* String sids="(0";
            for (CoinRecordInfo co : list) {
				sids+=","+co.getPayerid();
			}
            sids+=")";
            
            String hql2="from SuserInfo where studentid  in "+sids;
            List<SuserInfo> list2 = (List<SuserInfo>) dataDao.getObjectsViaParam(hql2, null);
            Map<Integer,String> map=new HashMap<Integer,String>();
            for (SuserInfo suserInfo : list2) {
				map.put(suserInfo.getStudentid(), "ffff");
			}
            
            for (CoinRecordInfo co : list) {
            	co.setStudentname(map.get(co.getPayerid()));
			}*/
            
            if (list != null) {
                result.put("recordlist", list);
            }
        }
        else {
            result.put("code", 2);
            result.put("message", "用户不存在");
        }
        return result;
    }


    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public CuserInfo registerUser(String phone, String token) {
        // 首先查询出订单相关的几个时间配置
        String hqlset = "from SystemSetInfo where 1 = 1";
        SystemSetInfo setInfo = (SystemSetInfo) dataDao.getFirstObjectViaParam(hqlset, null);
        BigDecimal C_register_money = new BigDecimal(0);
        BigDecimal C_register_gmoney = new BigDecimal(0);

        if (setInfo != null) {
            if (setInfo.getC_register_money() != null && setInfo.getC_register_money().doubleValue() != 0) {
                C_register_money = setInfo.getC_register_money();
            }
            if (setInfo.getC_register_gmoney() != null && setInfo.getC_register_gmoney().doubleValue() != 0) {
                C_register_gmoney = setInfo.getC_register_gmoney();
            }
        }

        CuserInfo cuser = new CuserInfo();
        cuser.setPhone(phone); // 设置电话
        cuser.setPhone(phone);
        String code = "C" + CommonUtils.getInviteCode(phone);
        cuser.setInvitecode(code);
        cuser.setTelphone(phone);
        cuser.setPassword("");
        cuser.setState(Constant.CUSER_UNCOMPLETE); // 设置状态
        cuser.setAddtime(new Date()); // 设置添加时间
        cuser.setNewtasknoti(0); // 设置消息默认提醒
        cuser.setCancancel(0); // 设置是否可以取消订单
        cuser.setFmoney(new BigDecimal(0)); // 冻结金额
        cuser.setMoney(C_register_money); // 金额
        cuser.setGmoney(C_register_gmoney); // 保证金
        cuser.setPrice(new BigDecimal(0)); // 教练单价
        cuser.setToken(token);
        cuser.setToken_time(new Date());
        cuser.setCoinnum(0);
        cuser.setFcoinnum(0);
        cuser.setGender(1);
        dataDao.addObject(cuser);
        return cuser;
    }

    // 返回：1.验证码正常 0：验证码不存在或者错误 2:验证码已经过期
    @Override
    public int checkVerCode(String phone, String vcode) {
        String hql = "from VerifyCodeInfo where phone = :phone and code = :code and totype = 1";
        String[] params = {"phone", "code"};
        VerifyCodeInfo verCode = (VerifyCodeInfo) dataDao.getFirstObjectViaParam(hql, params, phone, vcode);

        if (verCode == null) {
            return 0;
        }
        else {
            String hqlset = "from SystemSetInfo where 1=1";
            SystemSetInfo setInfo = (SystemSetInfo) dataDao.getFirstObjectViaParam(hqlset, null);
            int login_vcode_time = 15;
            if (setInfo != null && setInfo.getLogin_vcode_time() != null && setInfo.getLogin_vcode_time() > 0) {
                login_vcode_time = setInfo.getLogin_vcode_time();
            }

            Calendar now = Calendar.getInstance();

            Calendar time = Calendar.getInstance();
            time.setTime(verCode.getAddtime());
            time.add(Calendar.DAY_OF_YEAR, login_vcode_time);

            if (time.before(now)) {
                return 2;
            }
            else {
                return 1;
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void coachCancelOrder(CuserInfo cuser) {
        if (cuser != null) {
            StringBuffer cancelorderhql = new StringBuffer();
            cancelorderhql.append(" from OrderInfo o where o.coachid = :coachid and o.start_time < :systemdate ");
            cancelorderhql.append(" and (select count(*) from OrderRecordInfo where orderid = o.orderid and ( operation = 1 or operation = 2)  ) = 0 and studentstate = 0 ");
            String[] params = {"coachid", "systemdate"};
            List<OrderInfo> cancelorderlist = (List<OrderInfo>) dataDao.getObjectsViaParam(cancelorderhql.toString(), params, cuser.getCoachid(), new Date());
            for (OrderInfo cancelorder : cancelorderlist) {
                cancelorder.setStudentstate(4);
                cancelorder.setCoachstate(4);
                dataDao.updateObject(cancelorder);
                UserPushInfo userPushInfo = (UserPushInfo) dataDao.getObjectsViaParam(" from UserPushInfo where userid = :studentid and usertype = 2", new String[]{"studentid"}, cancelorder.getStudentid());
                if (userPushInfo != null && userPushInfo.getType() == DeviceType.IOS) {
                    ApplePushUtil.sendpush(userPushInfo.getDevicetoken(), "{\"aps\":{\"alert\":\"" + "由于" + cuser.getRealname() + "教练离职，您" + cancelorder.getStart_time() + "~" + cancelorder.getEnd_time() + "的订单已经被取消" + "\",\"sound\":\"default\"} }", 1, 2);
                }
            }
        }
    }

    @Override
    public QueryResult<DriveSchoolInfo> getDriveSchoolInfoByPage(int pageIndex, int pageSize) {
        String hql = " from DriveSchoolInfo";
        List<DriveSchoolInfo> schoollist = (List<DriveSchoolInfo>) dataDao.pageQueryViaParam(hql + " order by addtime ", pageSize, pageIndex, null);
        String counthql = " select count(*) " + hql;
        long count = (Long) dataDao.getFirstObjectViaParam(counthql, null);
        QueryResult<DriveSchoolInfo> result = new QueryResult<DriveSchoolInfo>(schoollist, count);
        return result;
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void addSchoolBalance(SchoolBalance entity) {
        dataDao.addObject(entity);
    }

    @Override
    public CsubjectInfo getFirstSubject() {
        String hql = "from CsubjectInfo where 1=1";
        return (CsubjectInfo) dataDao.getFirstObjectViaParam(hql, null);
    }

    @Override
    public int getCoachAllCouponTime(int coachid) {
        int hour = 0;
        String hql = "from CouponCoach where coachid = :coachid and state = 1";
        String[] params = {"coachid"};
        List<CouponCoach> list = (List<CouponCoach>) dataDao.getObjectsViaParam(hql, params, coachid);
        if (list != null) {
            for (CouponCoach object : list) {
                hour += object.getValue();
            }
        }
        return hour;
    }

    @Override
    public TeachcarInfo getTeachcarInfoByID(int teachCarId) {
        return dataDao.getObjectById(TeachcarInfo.class, teachCarId);
    }

    @Override
    public List<DriveSchoolInfo> getDriveSchoolListById(int schoolid) {
        String hql = " from DriveSchoolInfo where schoolid =:schoolid ";
        String[] params = {"schoolid"};
        List<DriveSchoolInfo> schoollist = (List<DriveSchoolInfo>) dataDao.getObjectsViaParam(hql, params, schoolid);
        return schoollist;
    }

    @Override
    public List<DriveSchoolInfo> getDriveSchoolList(String keyword) {
        String hql = "from DriveSchoolInfo";
        if (keyword != null) {
            hql += " where name like '%" + keyword + "%'";
        }
        List<DriveSchoolInfo> schoollist = (List<DriveSchoolInfo>) dataDao.getObjectsViaParam(hql, null);
        return schoollist;
    }

    @Override
    public List<DriveSchoolInfo> getDriveSchoolListBycity(String keyword,String provinceid,String cityid) {
        String hql = "from DriveSchoolInfo";
        if (keyword != null) {
            hql += " where name like '%" + keyword + "%'";
        }
        if (provinceid != null) {
            hql += " and provinceid ="+provinceid;
        }
        if (cityid != null) {
            hql += " and cityid="+cityid;
        }
        List<DriveSchoolInfo> schoollist = (List<DriveSchoolInfo>) dataDao.getObjectsViaParam(hql, null);
        return schoollist;
    }

    @Override
    public QueryResult<BalanceCoachInfo> getRechargeRecordList(int pageIndex, int pagesize) {

        StringBuffer cuserhql = new StringBuffer();
        cuserhql.append("from BalanceCoachInfo where type = 3");
        @SuppressWarnings("unchecked") List<BalanceCoachInfo> balancecoachlist = (List<BalanceCoachInfo>) dataDao.pageQueryViaParam(cuserhql.toString(), pagesize, pageIndex, null);
        if (balancecoachlist != null && balancecoachlist.size() > 0) {
            for (BalanceCoachInfo balanceCoach : balancecoachlist) {
                CuserInfo coach = dataDao.getObjectById(CuserInfo.class, balanceCoach.getUserid());
                if (coach != null) {
                    balanceCoach.setRealname(coach.getRealname());
                    balanceCoach.setPhone(coach.getPhone());
                }
            }
        }
        String counthql = " select count(*) " + cuserhql.toString();
        long total = (Long) dataDao.getFirstObjectViaParam(counthql, null);
        return new QueryResult<BalanceCoachInfo>(balancecoachlist, total);

    }

    @Override
    public QueryResult<BalanceCoachInfo> searchCoachRecharge(String searchname, String searchphone, String amount, String inputamount, String minsdate, String maxsdate, int pageIndex, int pagesize) {
        StringBuffer cuserhql = new StringBuffer();
        cuserhql.append("from BalanceCoachInfo where type = 3");
        if (!CommonUtils.isEmptyString(searchname)) {
            cuserhql.append(" and userid in (select coachid from CuserInfo where realname like '%" + searchname + "%')");
        }
        if (!CommonUtils.isEmptyString(searchphone)) {
            cuserhql.append(" and userid in (select coachid from CuserInfo where phone like '%" + searchphone + "%')");
        }
        if (CommonUtils.parseFloat(inputamount, 0) != 0) {
            if (CommonUtils.parseInt(amount, -1) == 0) {
                cuserhql.append("and amount >" + inputamount);
            }
            else if (CommonUtils.parseInt(amount, -1) == 1) {
                cuserhql.append("and amount =" + inputamount);
            }
            else {
                cuserhql.append("and amount <" + inputamount);
            }
        }
        if (!CommonUtils.isEmptyString(minsdate)) {
            cuserhql.append("and addtime >'" + minsdate + "'");
        }
        if (!CommonUtils.isEmptyString(maxsdate)) {
            Date enddate = CommonUtils.getDateFormat(maxsdate, "yyyy-MM-dd");
            enddate.setHours(23);
            enddate.setMinutes(59);
            enddate.setSeconds(59);
            String endmaxstime = CommonUtils.getTimeFormat(enddate, "yyyy-MM-dd HH:mm:ss");
            cuserhql.append("and addtime <'" + endmaxstime + "'");
        }

        List<BalanceCoachInfo> applycashlist = (List<BalanceCoachInfo>) dataDao.pageQueryViaParam(cuserhql.toString(), pagesize, pageIndex, null);
        if (applycashlist != null && applycashlist.size() > 0) {
            for (BalanceCoachInfo capplyCash : applycashlist) {
                CuserInfo student = dataDao.getObjectById(CuserInfo.class, capplyCash.getUserid());
                if (student != null) {
                    capplyCash.setRealname(student.getRealname());
                    capplyCash.setPhone(student.getPhone());
                }
            }
        }
        String counthql = " select count(*) " + cuserhql.toString();
        long total = (Long) dataDao.getFirstObjectViaParam(counthql, null);
        return new QueryResult<BalanceCoachInfo>(applycashlist, total);
    }


    @Override
    public Long getOrderSum(int coachid) {
        String hql = "select count(*) from OrderInfo where coachid=:coachid";
        String p[] = {"coachid"};
        Long o = (Long) dataDao.getFirstObjectViaParam(hql, p, coachid);
        return o;
    }

    @Override
    public Long getOrderOver(int coachid) {
        String hql = "select count(*) from OrderInfo where coachid=:coachid and over_time is not null";
        String p[] = {"coachid"};
        Long o = (Long) dataDao.getFirstObjectViaParam(hql, p, coachid);
        return o;
    }

    @Override
    public Long getOrderCancel(int coachid) {
        String hql = "select count(*) from OrderInfo where coachid=:coachid and coachstate=4";
        String p[] = {"coachid"};
        Long o = (Long) dataDao.getFirstObjectViaParam(hql, p, coachid);
        return o;
    }

    @Override
    public QueryResult<CuserInfo> getCuserReport(String provinceid, String cityid, String areaid, String drive_school, String startdate, String enddate, Integer pageIndex, int pagesize) {
        //dataDao.callCoachReport();
        StringBuffer cuserhql = new StringBuffer();
        cuserhql.append("from CuserInfo where 1=1 ");
        if (!CommonUtils.isEmptyString(startdate) && !CommonUtils.isEmptyString(enddate)) {
            cuserhql.append(" and coachid in (select coachid from OrderInfo where date >'" + startdate + "' and date<'" + enddate + "')  ");
        }
        if ("null".equals(provinceid)) {
            provinceid = "";
        }
        if ("null".equals(cityid)) {
            cityid = "";
        }
        if ("null".equals(areaid)) {
            areaid = "";
        }
        if ("0".equals(drive_school)) {
            drive_school = "";
        }
        if (!CommonUtils.isEmptyString(provinceid)) {
            cuserhql.append(" and provinceid=" + provinceid);
        }
        if (!CommonUtils.isEmptyString(cityid)) {
            cuserhql.append(" and cityid=" + cityid);
        }
        if (!CommonUtils.isEmptyString(areaid)) {
            cuserhql.append(" and areaid=" + areaid);
        }
        if (!CommonUtils.isEmptyString(drive_school)) {
            cuserhql.append(" and drive_schoolid=" + drive_school);
        }
        //System.out.println(cuserhql.toString());
        List<CuserInfo> cuserInfolist = (List<CuserInfo>) dataDao.pageQueryViaParam(cuserhql.toString() + " order by coachid  ", pagesize, pageIndex, null);
        String counthql = " select count(*) " + cuserhql.toString();
        long total = (Long) dataDao.getFirstObjectViaParam(counthql, null);
        return new QueryResult<CuserInfo>(cuserInfolist, total);
    }
    /**
     * 重置教练开课状态
     */
	@Override
	public void resetCoachCoursestate() {
		dataDao.callUpdatecoursestate();
	}

	@Override
	public Map getWithdrawcashdetail(int applyid, String coachid) {
		 Map result=new HashMap();
		 StringBuffer querystring1=new StringBuffer();
		 StringBuffer querystring2=new StringBuffer();
		 StringBuffer querystring3=new StringBuffer();
		 String[] params={"coachid"};
		 String[] params1={"userid"};
		 //历史提现申请详情
		 querystring1.append("from BalanceCoachInfo where userid=:userid and type=2");
		 //现阶段由于需要统计paytype=0的老数据，所以先用方案1
		 querystring2.append("from OrderInfo where coachid=:coachid and over_time is not null and delmoney=0");
		//方案2，不包含paytype=0的老数据
		// querystring2.append("from OrderInfo where coachid=:coachid and over_time is not null and paytype=1");
		 querystring3.append("from RechargeRecordInfo where userid=:userid and state=1 and type=2");
		 CuserInfo tempCuserInfo=(CuserInfo) dataDao.getObjectById(CuserInfo.class, CommonUtils.parseInt(coachid, 0));
		 CApplyCashInfo  tempCApplyCashInfo=dataDao.getObjectById(CApplyCashInfo.class, applyid);
		 tempCApplyCashInfo.setCoach(tempCuserInfo);
		 List<BalanceCoachInfo> tempBalanceCoachInfolist=(List<BalanceCoachInfo>) dataDao.getObjectsViaParam(querystring1.toString(), params1,CommonUtils.parseInt(coachid, 0));
		 for(BalanceCoachInfo b:tempBalanceCoachInfolist)
		 {
			 b.setRealname(tempCuserInfo.getRealname());
			 b.setPhone(tempCuserInfo.getPhone());
			 b.setAlipay_account(tempCuserInfo.getAlipay_account());
		 }
		 List<OrderInfo> tempOrderInfo=(List<OrderInfo>) dataDao.getObjectsViaParam(querystring2.toString(), params,CommonUtils.parseInt(coachid, 0));
		 List<Integer> studentname=new ArrayList<Integer>();
		 List<RechargeRecordInfo> tempRechargeRecordInfo=new ArrayList<RechargeRecordInfo>();
		 if(tempOrderInfo!=null)
		 {
			 for(OrderInfo c:tempOrderInfo)
			 {
				 //如果未添加该学院ID
				 if(!studentname.contains(c.getStudentid()))
				 {
					 studentname.add(c.getStudentid());
				 }
				 c.setCuserinfo(tempCuserInfo);
				 SuserInfo temps=dataDao.getObjectById(SuserInfo.class,c.getStudentid());
				 c.setStudentinfo(temps);
			 }
		 }
		 for(int i=0;i<studentname.size();i++)
		 {
			 List<RechargeRecordInfo> temp=(List<RechargeRecordInfo>) dataDao.getObjectsViaParam(querystring3.toString(), params1,studentname.get(i));
			 tempRechargeRecordInfo.addAll(temp);
		 }
		 for(RechargeRecordInfo r:tempRechargeRecordInfo)
		 {
			 Integer studentid=r.getUserid();
			 SuserInfo tempsuserinfo=dataDao.getObjectById(SuserInfo.class, studentid);
			 r.setSuser(tempsuserinfo);
		 }
		 result.put("CuserInfo", tempCuserInfo);
		 result.put("CApplyCashInfo", tempCApplyCashInfo);
		 result.put("BalanceCoachInfo", tempBalanceCoachInfolist);
		 result.put("OrderInfo", tempOrderInfo);
		 result.put("RechargeRecordInfo", tempRechargeRecordInfo);
		 return result;
	}

	@Override
	public void setCoachDriverSchool(List<CuserInfo> cusrlist) {
		StringBuffer querystring=new StringBuffer();
		querystring.append("from DriveSchoolInfo where schoolid=:schoolid");
		String[] params={"schoolid"};
		for(CuserInfo c:cusrlist)
		{
			DriveSchoolInfo temp=(DriveSchoolInfo) dataDao.getFirstObjectViaParam(querystring.toString(), params, c.getDrive_schoolid());
			if(temp!=null)
			{
				c.setDisplaydriverschool(temp.getName());
			}
		}	
	}

	@Override
	public Integer getDriverSchoolIDbyName(String schoolname) {
		StringBuffer querystring=new StringBuffer();
		querystring.append("from DriveSchoolInfo where name=:name");
		String[] params={"name"};
		DriveSchoolInfo temp=(DriveSchoolInfo) dataDao.getFirstObjectViaParam(querystring.toString(), params,schoolname);
		if(temp!=null)
                return temp.getSchoolid();
		else	
		        return 0;
	}
	/**
	 * 教练的可用小巴币
	 * @param coachid
	 * @return
	 */
	public int getCanUseCoinnumForCoach(String coachid) {
			String countinhql = "select sum(coinnum) from CoinRecordInfo where (receiverid ="+coachid+" and receivertype="+ UserType.COAH+" and ownertype="+UserType.COAH+" and ownerid="+coachid+")";
			Object in= dataDao.getFirstObjectViaParam(countinhql, null);
			int totalin= in==null?0:CommonUtils.parseInt(in.toString(), 0);
			/*if(totalin==0){
				return 0;
			}*/
			String countouthql = "select sum(coinnum) from CoinRecordInfo where (payerid ="+coachid+" and payertype="+ UserType.COAH+" and ownertype="+UserType.COAH+"  and ownerid="+coachid+")";
			Object out= dataDao.getFirstObjectViaParam(countouthql, null);
			int totalout = (out==null) ? 0: CommonUtils.parseInt(out.toString(),0);
			return (int) (totalin-totalout);
	}
	/**
	 * 驾校发放的小巴币
	 * @param coachid
	 * @return
	 */
	public int getCanUseCoinnumForDriveSchool( String coachid) {
		String countinhql2 = "select sum(coinnum) from CoinRecordInfo where (receiverid ="+coachid+" and receivertype="+ UserType.COAH+" and ownertype="+UserType.DRIVESCHOOL+")";
		Object in2= dataDao.getFirstObjectViaParam(countinhql2, null);
		int totalin2= in2==null?0:CommonUtils.parseInt(in2.toString(), 0);
		/*if(totalin2==0){
			return 0;
		}*/
		String countouthql3 = "select sum(coinnum) from CoinRecordInfo where (payerid ="+coachid+" and payertype="+ UserType.COAH+" and ownertype="+UserType.DRIVESCHOOL+")";
		Object out2= dataDao.getFirstObjectViaParam(countouthql3, null);
		int totalout2 = (out2==null) ? 0: CommonUtils.parseInt(out2.toString(),0);
		
		return (int) (totalin2-totalout2);
	}
	/**
	 * 获取教练平台可用小巴币数量
	 * @param coachid 教练id
	 * @return
	 */
	public int getCanUseCoinnumForPlatform(String coachid) {
			String countinhql = "select sum(coinnum) from CoinRecordInfo where (receiverid ="+coachid+" and receivertype="+ UserType.COAH+" and ownertype="+UserType.PLATFORM+")";
			Object in= dataDao.getFirstObjectViaParam(countinhql, null);
			int totalin= in==null?0:CommonUtils.parseInt(in.toString(), 0);
			/*if(totalin==0){
				return 0;
			}*/
			String countouthql = "select sum(coinnum) from CoinRecordInfo where (payerid = "+coachid+" and payertype="+ UserType.COAH+" and ownertype="+UserType.PLATFORM+")";
			Object out= dataDao.getFirstObjectViaParam(countouthql, null);
			int totalout = (out==null) ? 0: CommonUtils.parseInt(out.toString(),0);
		return (int) (totalin-totalout);
	}
	
	/**
	 * 获取教练小巴币归属那些教练或驾校使用
	 */
	public HashMap<String, Object> getCoinAffiliation(String coachid){
		HashMap<String, Object> result = new HashMap<String, Object>();
		//平台小巴币
		int numForPlatform=getCanUseCoinnumForPlatform(coachid);
		CoinAffiliation cf=new CoinAffiliation(numForPlatform,"",UserType.PLATFORM);
		List<CoinAffiliation> mList=new ArrayList<CoinAffiliation>();
		mList.add(cf);
		//教练
		int coachCoin=getCanUseCoinnumForCoach(coachid);
		CoinAffiliation ca=new CoinAffiliation(coachCoin,"",UserType.COAH);
		mList.add(ca);
		//驾校
		int schoolCoin=getCanUseCoinnumForDriveSchool(coachid);
		CoinAffiliation ca2=new CoinAffiliation(schoolCoin,"",UserType.DRIVESCHOOL);
		mList.add(ca2);
		/*String hql="from CoinRecordInfo where receiverid =:receiverid and receivertype=2 and ownertype=:ownertype GROUP BY ownerid";
		String params[]={"receiverid","ownertype"};
		List<CoinRecordInfo> crlist= (List<CoinRecordInfo>) dataDao.getObjectsViaParam(hql, params, CommonUtils.parseInt(coachid, 0),UserType.COAH);
		if(crlist!=null && crlist.size()>0){
			for (CoinRecordInfo coinRecordInfo : crlist) {
				int coachCoin=getCanUseCoinnumForCoach(String.valueOf(coinRecordInfo.getOwnerid()));
				CoinAffiliation ca=new CoinAffiliation(coachCoin,"",UserType.COAH);
				mList.add(ca);
			}
		}
		String dschoolhql="from CoinRecordInfo where receiverid =:receiverid and receivertype=2 and ownertype=:ownertype GROUP BY ownerid";
		String dparams[]={"receiverid","ownertype"};
		List<CoinRecordInfo> slist= (List<CoinRecordInfo>)dataDao.getObjectsViaParam(dschoolhql, dparams, CommonUtils.parseInt(coachid, 0),UserType.DRIVESCHOOL);
		if(slist!=null && slist.size()>0){
			for (CoinRecordInfo coinRecordInfo : slist) {
				int schoolCoin=getCanUseCoinnumForDriveSchool(coachid);
				CoinAffiliation ca=new CoinAffiliation(schoolCoin,"",UserType.DRIVESCHOOL);
				mList.add(ca);
			}
		}*/
		result.put("coinaffiliationlist",mList);//教练
		return result;
	}
	/**
	 * 获取教练小巴币归属那些教练或驾校使用 异常数据查询
	 */
	public HashMap<String, Object> getCoinAffiliationException(){
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		String hql="from CuserInfo";
		List<CuserInfo> list=(List<CuserInfo>) dataDao.getObjectsViaParam(hql, null);
		for (CuserInfo cuserInfo : list) {
			String coachid=String.valueOf(cuserInfo.getCoachid());
			//平台小巴币
			int numForPlatform=getCanUseCoinnumForPlatform(coachid);
			CoinAffiliation cf=new CoinAffiliation(numForPlatform,"",UserType.PLATFORM);
			List<CoinAffiliation> mList=new ArrayList<CoinAffiliation>();
			mList.add(cf);
			if(numForPlatform<0){
				System.out.println("coachid="+coachid+"  ,平台小巴币="+numForPlatform);
			}
			//教练
			int coachCoin=getCanUseCoinnumForCoach(coachid);
			CoinAffiliation ca=new CoinAffiliation(coachCoin,"",UserType.COAH);
			mList.add(ca);
			if(coachCoin<0){
				System.out.println("coachid="+coachid+"  ,教练小巴币="+coachCoin);
			}
			//驾校
			int schoolCoin=getCanUseCoinnumForDriveSchool(coachid);
			CoinAffiliation ca2=new CoinAffiliation(schoolCoin,"",UserType.DRIVESCHOOL);
			mList.add(ca2);
			if(schoolCoin<0){
				System.out.println("coachid="+coachid+"  ,驾校小巴币="+schoolCoin);
			}
			
			
		}
		
		
		return result;
	}

	@Override
	public RechargeRecordInfo getrechargerecord(String recordid) {
		 RechargeRecordInfo info = dataDao.getObjectById(RechargeRecordInfo.class, CommonUtils.parseInt(recordid, 0));
		return info;
	}
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void buySuccessbyweixin(String out_trade_no, String openid,String weixinorderid) {
		RechargeRecordInfo info = dataDao.getObjectById(RechargeRecordInfo.class, CommonUtils.parseInt(out_trade_no, 0));
        if (info != null) {

            int type = info.getType();
            if (type == 1) {
                CuserInfo user = dataDao.getObjectById(CuserInfo.class, info.getUserid());
                if (user != null) {
                    user.setMoney(user.getMoney().add(info.getAmount()));
                    dataDao.updateObject(user);
                    // 插入充值记录
                    BalanceCoachInfo balance = new BalanceCoachInfo();
                    balance.setAddtime(new Date());
                    balance.setAmount(info.getAmount());
                    balance.setUserid(user.getCoachid());
                    balance.setType(3);
                    balance.setAmount_out1(new BigDecimal(0));
                    balance.setAmount_out2(new BigDecimal(0));
                    dataDao.addObject(balance);
                }
            }
            else if (type == 2) {
                SuserInfo user = dataDao.getObjectById(SuserInfo.class, info.getUserid());
                if (user != null) {
                    user.setMoney(user.getMoney().add(info.getAmount()));
                    dataDao.updateObject(user);
                    // 插入充值记录
                    BalanceStudentInfo balance = new BalanceStudentInfo();
                    balance.setAddtime(new Date());
                    balance.setAmount(info.getAmount());
                    balance.setUserid(user.getStudentid());
                    balance.setType(5);
                    dataDao.addObject(balance);
                }
            }

            info.setUpdatetime(new Date());
            if(!openid.equals(""))
            {
                info.setOpenid(openid);
            }
            info.setWxorderid(weixinorderid);
            info.setState(1);
            dataDao.updateObject(info);

        }
	}
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void updateCoachCoupon(String coachid,Integer pub_count) {
		CuserInfo cuser=getCoachByid(CommonUtils.parseInt(coachid, 0));
		Integer total=cuser.getCoupontotal();
		Integer rest=cuser.getCouponrest();
		total+=pub_count;
		rest+=pub_count;
		cuser.setCoupontotal(total);
		cuser.setCouponrest(rest);
		updateCuser(cuser);
	}

	@Override
	public List<SuserInfo> getCoachStudent(String coachid) {
		StringBuffer querybuffer=new StringBuffer();
		//querybuffer.append("from SuserInfo t1 left join CoachStudentInfo t2 on t1.studentid=t2.studentid where t2.coachid=:coachid");
		querybuffer.append("from CoachStudentInfo where coachid=:coachid");
		String[] params={"coachid"};
		List<CoachStudentInfo> relationlist=(List<CoachStudentInfo>)dataDao.getObjectsViaParam(querybuffer.toString(), params, CommonUtils.parseInt(coachid, 0));
        List<Integer> studentidlist=new ArrayList<Integer>();	
		if(relationlist!=null &relationlist.size()>0)
		{
			  for (CoachStudentInfo s:relationlist)
				{
					studentidlist.add(s.getStudentid());
				}
				String querystring="from SuserInfo where studentid in(:studentid)";
				String[] params1={"studentid"};
				List<SuserInfo> studentlist=(List<SuserInfo>) dataDao.getObjectsViaParam(querystring, params1, studentidlist);
				return studentlist;
		}
		return null;
		
	}

	@Override
	public Integer getstudentCoupontotal(String studentid,String coachid) {
		String querystring="from CouponRecord where userid=:studentid and ownerid=:coachid and NOW()<end_time and state=1";
		String[] params={"studentid","coachid"};
		List<CouponRecord> temp=(List<CouponRecord>) dataDao.getObjectsViaParam(querystring, params,CommonUtils.parseInt(studentid, 0),CommonUtils.parseInt(coachid,0));
		if(temp!=null && temp.size()>0)
		   return temp.size();
		else
		   return 0;
	}

	@Override
	public Integer getstudentCouponrest(String studentid,String coachid) {
		String querystring="from CouponRecord where userid=:studentid and ownerid=:coachid and NOW()<end_time and state=0";
		String[] params={"studentid","coachid"};
		List<CouponRecord> temp=(List<CouponRecord>) dataDao.getObjectsViaParam(querystring, params,CommonUtils.parseInt(studentid, 0),CommonUtils.parseInt(coachid,0));
		if(temp!=null && temp.size()>0)
			   return temp.size();
			else
			   return 0;
	}
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public String coachgrantcoupon(String coachid,String phone,Integer pubnum) {
		SuserInfo userinfo =suserService.getUserByPhone(phone);
		CuserInfo cuserinfo=dataDao.getObjectById(CuserInfo.class, CommonUtils.parseInt(coachid, 0));
		if(cuserinfo.getCouponlimit()==0)
		{
			return "ERROR0";
		}
		if (userinfo == null) {
			return "ERROR1";
		}
		if (cuserinfo.getCouponrest() < pubnum) {
			return "ERROR2";
		} else {
			Calendar c=Calendar.getInstance();
			Date now=c.getTime();
			c.add(Calendar.DAY_OF_MONTH, 90);	
			for (int i = 0; i < pubnum; i++) {
				CouponRecord couponrecord = new CouponRecord();
				couponrecord.setCouponid(0);
				couponrecord.setUserid(userinfo.getStudentid());
				couponrecord.setGettime(now);
				couponrecord.setValue(1);
				couponrecord.setOwnertype(2);
				couponrecord.setState(0);
				couponrecord.setOwnerid(CommonUtils.parseInt(coachid,0));
				couponrecord.setCoupontype(1);
				couponrecord.setEnd_time(c.getTime());
				couponrecord.setUsetime(null);
				couponService.addCouponRecord(couponrecord);
				cuserinfo.setCouponrest(cuserinfo.getCouponrest()-1);	
				
			}
			dataDao.updateObject(cuserinfo);
			String message = "您收到" + pubnum + "张小巴券哦,请注意查收";

//			if (pushtype == 1) {// 短信
//				CommonUtils.sendSms(userinfo.getPhone(), message);
//			} else {
				// 推送通知
				UserPushInfo userPushInfo = getUserPushInfo(userinfo.getStudentid(), 2);
				if (userPushInfo != null && userPushInfo.getDevicetoken() != null) {
					if (userPushInfo.getType() ==DeviceType.ANDROID && !CommonUtils.isEmptyString(userPushInfo.getJpushid())) {
						PushtoSingle push = new PushtoSingle();
						push.pushsingle(userPushInfo.getJpushid(), 2, "{\"message\":\"" + message + "\",\"type\":\"4\"}");
					} else if (userPushInfo.getType() == DeviceType.IOS && !CommonUtils.isEmptyString(userPushInfo.getDevicetoken())) {
						ApplePushUtil.sendpush(userPushInfo.getDevicetoken(), "{\"aps\":{\"alert\":\"" + message + "\",\"sound\":\"default\"},\"userid\":" + userinfo.getStudentid() + "}", 1, 2);
					}
				}
		//	}

			String	pushcontent="小巴券已发放";
				// 如果消息不为空的话,生成系统通知
				NoticesInfo noticeInfo = new NoticesInfo();
				noticeInfo.setAddtime(new Date());
				noticeInfo.setType(2);
				noticeInfo.setContent(pushcontent);
				noticeInfo.setCategory("小巴券领取");
				addObject(noticeInfo);

				NoticesUserInfo nUser = new NoticesUserInfo();
				nUser.setNoticeid(noticeInfo.getNoticeid());
				nUser.setReadstate(0);
				nUser.setUserid(userinfo.getStudentid());
				addObject(nUser);
			
		}
		return "SUCCESS";
	}

	@Override
	public HashMap<String, Object> getcoachcouponlist(String coachid,String pagenum) {
		HashMap<String, Object> returnResult = new HashMap<String, Object>();
		String querystring="from CouponRecord where ownerid=:coachid GROUP BY gettime order by gettime desc";
		String[] params={"coachid"};
		List<CouponRecord> list=(List<CouponRecord>) dataDao.pageQueryViaParam(querystring,Constant.COUNT_NUM,CommonUtils.parseInt(pagenum, 0), params, CommonUtils.parseInt(coachid, 0));
		String querystring1="select count(*) "+querystring;
		List countlist=dataDao.pageQueryViaParam(querystring1, Constant.COUNT_NUM, CommonUtils.parseInt(pagenum, 0), params,CommonUtils.parseInt(coachid, 0));
		if(list!=null && list.size()>0)
		{
			for(int i=0;i<list.size();i++)
			{
				CouponRecord temp=list.get(i);
				temp.setUsecount((long)countlist.get(i));
				SuserInfo suser=dataDao.getObjectById(SuserInfo.class, temp.getUserid());
				if(suser!=null)
				{
					 temp.setUsename(suser.getRealname());
					 temp.setUserphone(suser.getPhone());
				}
				else
				{
					temp.setUsename("");
					temp.setUserphone("");
				}
				 
				 
			}
			
			returnResult.put("CouponRecordList", list) ;
		}
		List<CouponRecord> nextPage = (List<CouponRecord>) dataDao.pageQueryViaParam(querystring,Constant.COUNT_NUM, (CommonUtils.parseInt(pagenum, 1) + 1), params, CommonUtils.parseInt(coachid, 0));
		if (nextPage != null && nextPage.size() > 0) {
			returnResult.put("hasmore", 1);
		} else {
			returnResult.put("hasmore", 0);
		}
		
		return returnResult;
	}

	@Override
	public Integer getcoachcouponlimit(String coachid) {
		 CuserInfo cuser=dataDao.getObjectById(CuserInfo.class, CommonUtils.parseInt(coachid, 0));
		 if(cuser.getCouponlimit()==0)
			 return 0;
		 else
			 return 1;
	}
		//获取父权限
	public List<PermissionSetInfo> getParentPermissions()
	{
		String hql="from PermissionSetInfo where parentPermissionId=:pPermission";
		String[] params={"pPermission"};
		List<PermissionSetInfo> list=(List<PermissionSetInfo>) dataDao.getObjectsViaParam(hql, params,0);
		return list;
	}
	
	//添加权限
	public boolean addPermission(PermissionSetInfo info)
	{
		if(!isExist(info))
		{
			dataDao.addObject(info);
			return true;
		}else
		{
			return false;
		}
	}
	private boolean isExist(PermissionSetInfo info)
	{
		List<PermissionSetInfo>list=null;
		if(CommonUtils.isEmptyString(info.getMappedAction()))
		{
			String hql="from PermissionSetInfo where name=:name";
			String[] params={"name"};
			list=(List<PermissionSetInfo>)dataDao.getObjectsViaParam(hql, params,info.getName());
			
		}
		
		if(list==null || list.size()==0)
		{
			return false;
		}else
		{
			return true;
		}
	}
	
	@SuppressWarnings("unchecked")
    @Override
    public QueryResult<CuserInfo> getCoachListBySignstate(String searchname, String searchphone, Integer driveSchoolname, String carlicense, Integer checkstate, Integer signstate, String signexpiredmin,String signexpiredmax,  Integer pageIndex, int pagesize) {
        StringBuffer cuserhql = new StringBuffer();
        cuserhql.append("from CuserInfo where 1=1 ");

        if (!CommonUtils.isEmptyString(searchname)) {
            cuserhql.append(" and realname like '%" + searchname + "%'");
        }
        if (!CommonUtils.isEmptyString(searchphone)) {
            cuserhql.append(" and phone like '%" + searchphone + "%'");
        }

        if (driveSchoolname != 0) {
            if (driveSchoolname == -1) {
                cuserhql.append(" and drive_school =null and drive_schoolid =null ");
            }
            else {
                cuserhql.append(" and drive_schoolid =" + driveSchoolname);
            }

        }
        if (checkstate != null) {
        	if (checkstate == 4) {
        		cuserhql.append(" and state >= 0 ");
        	}
            if (checkstate == 0) {
                cuserhql.append(" and state = 0 ");
            }
            if (checkstate == 1) {
                cuserhql.append(" and state = 1 ");
            }

            if (checkstate == 2) {
                cuserhql.append(" and state = 2 ");
            }

            if (checkstate == 3) {
                cuserhql.append(" and state = 3 ");
            }
        }

        if (!CommonUtils.isEmptyString(carlicense)) {
            cuserhql.append(" and carlicense like '%" + carlicense + "%'");
        }
        
        if (signstate >= 0) {
                cuserhql.append(" and signstate =" + signstate);
        }
        
        if (!CommonUtils.isEmptyString(signexpiredmin)) {
			cuserhql.append(" and signexpired >='" + signexpiredmin + "'");
		}
		if (!CommonUtils.isEmptyString(signexpiredmax)) {
			Date signexpireddate = CommonUtils.getDateFormat(signexpiredmax, "yyyy-MM-dd");
			signexpireddate.setHours(23);
			signexpireddate.setMinutes(59);
			signexpireddate.setSeconds(59);
			String signexpiredmaxstime = CommonUtils.getTimeFormat(signexpireddate, "yyyy-MM-dd HH:mm:ss");
			cuserhql.append(" and signexpired <='" + signexpiredmaxstime + "'");
		}

        List<CuserInfo> cuserInfolist = (List<CuserInfo>) dataDao.pageQueryViaParam(cuserhql.toString() + " order by id desc ", pagesize, pageIndex, null);
        String counthql = " select count(*) " + cuserhql.toString();
        long total = (Long) dataDao.getFirstObjectViaParam(counthql, null);
        return new QueryResult<CuserInfo>(cuserInfolist, total);
    }
}
