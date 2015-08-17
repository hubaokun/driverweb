package com.daoshun.guangda.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.daoshun.guangda.model.InviteReport;
import com.daoshun.guangda.pojo.CuserInfo;
import com.daoshun.guangda.pojo.RecommendInfo;
import com.daoshun.guangda.service.ICUserService;
import com.daoshun.guangda.service.IRecommendService;
import com.daoshun.guangda.service.ISBookService;

@ParentPackage("default")
@Controller
@Scope("prototype")
public class RecommendAction extends BaseAction {

	@Resource
	private IRecommendService recommendService;
	@Resource
	private ISBookService sbookService;
	



	private Integer pageIndex = 1;
	private Integer pageCount = 0;
	private Integer inviteCount;
	private Integer checkPastCount ;
	private BigDecimal earnCount;
	private Integer orderCount;
	private Integer coachid;
	private Integer invitedcoachid;
	private Integer invitedstudentid;
	private int typestyle;
	private int index;
	private int change_id;
	private long total;
	//private RecommendInfo rinfo;
	private int displayflag=0;
	private String realname;
	private String phone;
	private int resultstring=1;
	private int recommendtype;
	List<RecommendInfo> mp=new ArrayList<RecommendInfo>();
	List<InviteReport>  ip=new ArrayList<InviteReport>();
	//获取教练邀请教练列表
	@Action(value = "/getRecommendList", results = { @Result(name = SUCCESS, location = "/recommendlist.jsp") })
	public String getRecommendList()
	{
		HttpSession session = ServletActionContext.getRequest().getSession();
		int pagesize = CommonUtils.parseInt(String.valueOf(session.getAttribute("pagesize")), 10);
		recommendtype=1;
		QueryResult<RecommendInfo> qresult=recommendService.getRecommendListForServer(pageIndex,pagesize,recommendtype);
		total=qresult.getTotal();
	    mp=qresult.getDataList();
		pageCount = ((int) total + pagesize - 1) / pagesize;
		if (pageIndex > 1) {
			if (mp == null || mp.size() == 0) {
				pageIndex--;
				getRecommendList();
			}
		}
		return SUCCESS;
	}
	@Action(value = "/getRecommendListc2s", results = { @Result(name = SUCCESS, location = "/recommendlistc2s.jsp") })
	public String getRecommendListc2s()
	{
		HttpSession session = ServletActionContext.getRequest().getSession();
		int pagesize = CommonUtils.parseInt(String.valueOf(session.getAttribute("pagesize")), 10);
		recommendtype=2;
		QueryResult<RecommendInfo> qresult=recommendService.getRecommendListForServer(pageIndex,pagesize,recommendtype);
		total=qresult.getTotal();
	    mp=qresult.getDataList();
		pageCount = ((int) total + pagesize - 1) / pagesize;
		if (pageIndex > 1) {
			if (mp == null || mp.size() == 0) {
				pageIndex--;
				getRecommendListc2s();
			}
		}
		return SUCCESS;
	}
	
	//获取邀请详情
	@Action(value = "/getRecommendDetail", results = { @Result(name = SUCCESS, location = "/recommenddetail.jsp") })
	public String getRecommendDetail()
	{
		HttpSession session = ServletActionContext.getRequest().getSession();
		int pagesize = CommonUtils.parseInt(String.valueOf(session.getAttribute("pagesize")), 10);
		QueryResult<RecommendInfo> qresult=recommendService.getInvitedDetailsForServer(coachid.toString(),pageIndex,pagesize,recommendtype);
		total=qresult.getTotal();
	    mp=qresult.getDataList();
		pageCount = ((int) total + pagesize - 1) / pagesize;
		if (pageIndex > 1) {
			if (mp == null || mp.size() == 0) {
				pageIndex--;
				getRecommendDetail();
			}
		}
	    	return SUCCESS;

	}
	//获取邀请详情
	@Action(value = "/getRecommendDetailc2s", results = { @Result(name = SUCCESS, location = "/recommenddetailc2s.jsp")})
	public String getRecommendDetailc2s()
	{
		HttpSession session = ServletActionContext.getRequest().getSession();
		int pagesize = CommonUtils.parseInt(String.valueOf(session.getAttribute("pagesize")), 10);
		QueryResult<RecommendInfo> qresult=recommendService.getInvitedDetailsForServer(coachid.toString(),pageIndex,pagesize,recommendtype);
		total=qresult.getTotal();
	    mp=qresult.getDataList();
		pageCount = ((int) total + pagesize - 1) / pagesize;
		if (pageIndex > 1) {
			if (mp == null || mp.size() == 0) {
				pageIndex--;
				getRecommendDetailc2s();
			}
		}
	    return SUCCESS;

	}
	//发放奖励
	@Action(value = "/offerReward", results = { @Result(name = SUCCESS, location = "/getRecommendDetail.do?coachid=${coachid}&inviteCount=${invitecount}&checkPastCount=${checkmancount}&earnCount=${totalreward}&orderCount=${ordercount}&index=9&change_id=0&pageIndex=${pageIndex}&resultstring=${resultstring}&recommendtype=1",type = "redirect")})
	public String offerReward()
	{
		HttpSession session = ServletActionContext.getRequest().getSession();
		HashMap<String, Object> resultmap=recommendService.getCoachList("", "", "", "", "", "", "", "", "", "");
	    resultstring=recommendService.offeredReward(coachid.toString(), invitedcoachid.toString(),typestyle,resultmap);
		int pagesize = CommonUtils.parseInt(String.valueOf(session.getAttribute("pagesize")), 10);
		QueryResult<RecommendInfo> qresult=recommendService.getInvitedDetailsForServer(coachid.toString(),pageIndex,pagesize,recommendtype);
		mp=qresult.getDataList();
		total=qresult.getTotal();
		pageCount = ((int) total + pagesize - 1) / pagesize;
		if (pageIndex > 1) {
			if (mp == null || mp.size() == 0) {
				pageIndex--;
				getRecommendDetail();
			}
		}
		return SUCCESS;
	}
	//根据搜索条件搜索
	@Action(value = "/SearchRecommoned", results = { @Result(name = SUCCESS, location = "/recommendlist.jsp")})
	public String SearchRecommoned()
	{
		HttpSession session = ServletActionContext.getRequest().getSession();
		int pagesize = CommonUtils.parseInt(String.valueOf(session.getAttribute("pagesize")), 10);
		QueryResult<RecommendInfo> qresult=recommendService.getRecommonedInfoByKeyWord(realname, phone,recommendtype);
		total=qresult.getTotal();
	    mp=qresult.getDataList();
		pageCount = ((int) total + pagesize - 1) / pagesize;
		if (pageIndex > 1) {
			if (mp == null || mp.size() == 0) {
				pageIndex--;
				SearchRecommoned();
			}
		}
		return SUCCESS;
	}
	//根据搜索条件搜索邀请详情
	@Action(value = "/SearchRecommoneddetail", results = { @Result(name = SUCCESS, location = "/recommenddetail.jsp")})
	public String SearchRecommoneddetail()
	{
		HttpSession session = ServletActionContext.getRequest().getSession();
		int pagesize = CommonUtils.parseInt(String.valueOf(session.getAttribute("pagesize")), 10);
		QueryResult<RecommendInfo> qresult=recommendService.getRecommoneddetailInfoByKeyWord(realname, phone, coachid.toString(),recommendtype);
		total=qresult.getTotal();
	    mp=qresult.getDataList();
		pageCount = ((int) total + pagesize - 1) / pagesize;
		if (pageIndex > 1) {
			if (mp == null || mp.size() == 0) {
				pageIndex--;
				SearchRecommoned();
			}
		}
		return SUCCESS;
	}
    //删除邀请
	@Action(value = "/deleteRecommoned", results = { @Result(name = SUCCESS, location = "/getRecommendDetail.do?coachid=${coachid}&inviteCount=${invitecount}&checkPastCount=${checkmancount}&earnCount=${totalreward}&orderCount=${ordercount}&recommendtype=1&index=9&change_id=0&pageIndex=${pageIndex}",type = "redirect")})
	public String deleteRecommoned()
	{
		HttpSession session = ServletActionContext.getRequest().getSession();
		int pagesize = CommonUtils.parseInt(String.valueOf(session.getAttribute("pagesize")), 10);
		QueryResult<RecommendInfo> qresult=recommendService.getInvitedDetailsForServer(coachid.toString(),pageIndex,pagesize,recommendtype);
		recommendService.deleteRecommonedInfo(coachid.toString(), invitedcoachid.toString(),recommendtype);
		mp=qresult.getDataList();
		total=qresult.getTotal();
		pageCount = ((int) total + pagesize - 1) / pagesize;
		if (pageIndex > 1) {
			if (mp == null || mp.size() == 0) {
				pageIndex--;
				getRecommendDetail();
			}
		}
		return SUCCESS;
	}
	//根据搜索条件搜索
		@Action(value = "/SearchRecommonedc2s", results = { @Result(name = SUCCESS, location = "/recommendlistc2s.jsp")})
		public String SearchRecommonedc2s()
		{
			HttpSession session = ServletActionContext.getRequest().getSession();
			int pagesize = CommonUtils.parseInt(String.valueOf(session.getAttribute("pagesize")), 10);
			QueryResult<RecommendInfo> qresult=recommendService.getRecommonedInfoByKeyWord(realname, phone,recommendtype);
			total=qresult.getTotal();
		    mp=qresult.getDataList();
			pageCount = ((int) total + pagesize - 1) / pagesize;
			if (pageIndex > 1) {
				if (mp == null || mp.size() == 0) {
					pageIndex--;
					SearchRecommonedc2s();
				}
			}
			return SUCCESS;
		}
		//根据搜索条件搜索邀请详情
		@Action(value = "/SearchRecommoneddetailc2s", results = { @Result(name = SUCCESS, location = "/recommenddetailc2s.jsp")})
		public String SearchRecommoneddetailc2s()
		{
			HttpSession session = ServletActionContext.getRequest().getSession();
			int pagesize = CommonUtils.parseInt(String.valueOf(session.getAttribute("pagesize")), 10);
			QueryResult<RecommendInfo> qresult=recommendService.getRecommoneddetailInfoByKeyWord(realname, phone, coachid.toString(),recommendtype);
			total=qresult.getTotal();
		    mp=qresult.getDataList();
			pageCount = ((int) total + pagesize - 1) / pagesize;
			if (pageIndex > 1) {
				if (mp == null || mp.size() == 0) {
					pageIndex--;
					SearchRecommoneddetailc2s();
				}
			}
			return SUCCESS;
		}
	    //删除邀请
		@Action(value = "/deleteRecommonedc2s", results = { @Result(name = SUCCESS, location = "/getRecommendDetailc2s.do?coachid=${coachid}&inviteCount=${invitecount}&checkPastCount=${checkmancount}&earnCount=${totalreward}&orderCount=${ordercount}&recommendtype=2&index=9&change_id=0&pageIndex=${pageIndex}",type = "redirect")})
		public String deleteRecommonedc2s()
		{
			HttpSession session = ServletActionContext.getRequest().getSession();
			int pagesize = CommonUtils.parseInt(String.valueOf(session.getAttribute("pagesize")), 10);
			QueryResult<RecommendInfo> qresult=recommendService.getInvitedDetailsForServer(coachid.toString(),pageIndex,pagesize,recommendtype);
			recommendService.deleteRecommonedInfo(coachid.toString(), invitedstudentid.toString(),recommendtype);
			mp=qresult.getDataList();
			total=qresult.getTotal();
			pageCount = ((int) total + pagesize - 1) / pagesize;
			if (pageIndex > 1) {
				if (mp == null || mp.size() == 0) {
					pageIndex--;
					deleteRecommonedc2s();
				}
			}
			return SUCCESS;
		}
	//获取邀请日报
	@Action(value = "/getRecommendReport", results = { @Result(name = SUCCESS, location = "/recommendreport.jsp")})
    public String getRecommendReport()
    {
		HttpSession session = ServletActionContext.getRequest().getSession();
		int pagesize = CommonUtils.parseInt(String.valueOf(session.getAttribute("pagesize")), 10);
		QueryResult<InviteReport> qresult=recommendService.getRecommenReport(pagesize, pageIndex,recommendtype);
		ip=qresult.getDataList();
		total=qresult.getTotal();
		pageCount = ((int) total + pagesize - 1) / pagesize;
		if (pageIndex > 1) {
			if (ip == null || ip.size() == 0) {
				pageIndex--;
				getRecommendReport();
			}
		}
		return SUCCESS;
    }
	
	
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	public Integer getPageCount() {
		return pageCount;
	}
	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public List<RecommendInfo> getMp() {
		return mp;
	}
	public void setMp(List<RecommendInfo> mp) {
		this.mp = mp;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public Integer getInviteCount() {
		return inviteCount;
	}
	public void setInviteCount(Integer inviteCount) {
		this.inviteCount = inviteCount;
	}
	public Integer getCheckPastCount() {
		return checkPastCount;
	}
	public void setCheckPastCount(Integer checkPastCount) {
		this.checkPastCount = checkPastCount;
	}
	public BigDecimal getEarnCount() {
		return earnCount;
	}
	public void setEarnCount(BigDecimal earnCount) {
		this.earnCount = earnCount;
	}
	public Integer getOrderCount() {
		return orderCount;
	}
	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}
	public Integer getCoachid() {
		return coachid;
	}
	public void setCoachid(Integer coachid) {
		this.coachid = coachid;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public int getChange_id() {
		return change_id;
	}
	public void setChange_id(int change_id) {
		this.change_id = change_id;
	}
//	public RecommendInfo getRinfo() {
//		return rinfo;
//	}
//	public void setRinfo(RecommendInfo rinfo) {
//		this.rinfo = rinfo;
//	}
	public int getDisplayflag() {
		return displayflag;
	}
	public void setDisplayflag(int displayflag) {
		this.displayflag = displayflag;
	}
	public Integer getInvitedcoachid() {
		return invitedcoachid;
	}
	public void setInvitedcoachid(Integer invitedcoachid) {
		this.invitedcoachid = invitedcoachid;
	}
	public int getTypestyle() {
		return typestyle;
	}
	public void setTypestyle(int typestyle) {
		this.typestyle = typestyle;
	}
	
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getResultstring() {
		return resultstring;
	}
	public void setResultstring(int resultstring) {
		this.resultstring = resultstring;
	}
	public List<InviteReport> getIp() {
		return ip;
	}
	public void setIp(List<InviteReport> ip) {
		this.ip = ip;
	}
	public int getRecommendtype() {
		return recommendtype;
	}
	public void setRecommendtype(int recommendtype) {
		this.recommendtype = recommendtype;
	}
	public Integer getInvitedstudentid() {
		return invitedstudentid;
	}
	public void setInvitedstudentid(Integer invitedstudentid) {
		this.invitedstudentid = invitedstudentid;
	}


}