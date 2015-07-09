package com.daoshun.guangda.action;

import java.util.Date;
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
import com.daoshun.guangda.pojo.ComplaintBookInfo;
import com.daoshun.guangda.pojo.ComplaintInfo;
import com.daoshun.guangda.pojo.CuserInfo;
import com.daoshun.guangda.pojo.OrderInfo;
import com.daoshun.guangda.pojo.SuserInfo;
import com.daoshun.guangda.service.ISOrderService;

@ParentPackage("default")
@Controller
@Scope("prototype")
public class ComplaintAction extends BaseAction {
	
	private static final long serialVersionUID = 2534791731119144729L;
	
	@Resource
	private ISOrderService orderService;
	
	private long total;
	
	private Integer pageIndex = 1;
	
	private Integer pageCount = 0;
	
	private int index;
	
	private List<ComplaintInfo> complaintlist;
	
	private ComplaintInfo complaint = null;
	
	private OrderInfo order;
	
	private CuserInfo coach;
	
	private SuserInfo student;
	
	private List<ComplaintBookInfo> complaintbooklist;
	
	private Integer complaintid;
	
	private String studentphone;
	
	private String coachphone;
	
	private Integer type;
	
	private Integer state;
	
	private String minsdate;
	
	private String maxsdate;
	
	private String content;
	
	private int change_id;
	
	private String newaddtime;
	
	/**
	 * 获取投诉列表
	 * @return
	 */
	@Action(value = "/getComplaintList", results = { @Result(name = SUCCESS, location = "/complaint.jsp")})
	public String getComplaintList() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		int pagesize = CommonUtils.parseInt(String.valueOf(session.getAttribute("pagesize")), 10);
		QueryResult<ComplaintInfo> result = orderService.getComplaintList( pageIndex, pagesize);
		total = result.getTotal();
		complaintlist = result.getDataList();
		pageCount = ((int) result.getTotal() + pagesize - 1) / pagesize;
		if (pageIndex > 1) {
			if (complaintlist == null || complaintlist.size() == 0) {
				pageIndex--;
				getComplaintList();
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 获取投诉详情
	 * @return
	 */
	@Action(value = "/getComplaintDetail", results = { @Result(name = SUCCESS, location = "/complaintdetail.jsp") })
	public String getComplaintDetail() {
		complaint = orderService.getComplaintById(complaintid);
		if(complaint!=null){
			order = orderService.getOrderById(complaint.getOrder_id());
			complaintbooklist = orderService.getComplaintBookList(complaintid);
			if(complaint.getType()==1){
				student = orderService.getStudentById(complaint.getFrom_user());
				coach = orderService.getCoachById(complaint.getTo_user());
			}else{
				student = orderService.getStudentById(complaint.getTo_user());
				coach = orderService.getCoachById(complaint.getFrom_user());
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 根据关键字筛选投诉信息
	 * 
	 * @return
	 */
	@Action(value = "/getComplaintBySearch", results = { @Result(name = SUCCESS, location = "/complaint.jsp") })
	public String getComplaintBySearch() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		int pagesize = CommonUtils.parseInt(String.valueOf(session.getAttribute("pagesize")), 10);
		QueryResult<ComplaintInfo> result = orderService.getComplaintByKeyword(complaintid, studentphone, coachphone, type, minsdate, maxsdate, state, pageIndex, pagesize);
		total = result.getTotal();
		complaintlist = result.getDataList();
		pageCount = ((int) result.getTotal() + pagesize - 1) / pagesize;
		if (pageIndex > 1) {
			if (complaintlist == null || complaintlist.size() == 0) {
				pageIndex--;
				getComplaintBySearch();
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 判断投诉是否解决
	 */
	@Action(value="/complaintCheck")
	public void complaintCheck(){
		complaint = orderService.getComplaintById(complaintid);
		if(complaint!=null){
			if(complaint.getState()==1||complaint.getState()==2){
				setResponseStr("error");
			}else{
				setResponseStr("success");
			}
		}
	}
	
	@Action(value = "/updateComplaintState" , results = { @Result(name = SUCCESS, location = "/getComplaintList.do?index=${index}&pageIndex=${pageIndex}",type = "redirect") })
	public String updateComplaintState(){
		complaint = orderService.getComplaintById(complaintid);
		if(complaint!=null){
			complaint.setState(1);
			orderService.updateComplaintState(complaint);
		}
		return SUCCESS;
	}
	
	@Action(value = "/updateComplaint" , results = { @Result(name = SUCCESS, location = "/getComplaintDetail.do?index=${index}&complaintid=${complaintid}",type = "redirect") })
	public String updateComplaint(){
		complaint = orderService.getComplaintById(complaintid);
		if(complaint!=null){
			complaint.setState(1);
			orderService.updateComplaintState(complaint);
		}
		return SUCCESS;
	}
	
	@Action(value = "/addComplaintBookInfo" , results = { @Result(name = SUCCESS, location = "/getComplaintDetail.do?complaintid=${complaintid}",type = "redirect") })
	public String addComplaintBook(){
		ComplaintBookInfo complaintbook = new ComplaintBookInfo();
		complaintbook.setComplaintid(complaintid);
		complaintbook.setTime(CommonUtils.getDateFormat(newaddtime,"yyyy-MM-dd HH:mm:ss"));
		complaintbook.setContent(content);
		complaintbook.setAddtime(new Date());
		orderService.addComplaintBook(complaintbook);
		return SUCCESS;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
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

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public List<ComplaintInfo> getComplaintlist() {
		return complaintlist;
	}

	public void setComplaintlist(List<ComplaintInfo> complaintlist) {
		this.complaintlist = complaintlist;
	}

	public ComplaintInfo getComplaint() {
		return complaint;
	}

	public void setComplaint(ComplaintInfo complaint) {
		this.complaint = complaint;
	}

	public Integer getComplaintid() {
		return complaintid;
	}

	public void setComplaintid(Integer complaintid) {
		this.complaintid = complaintid;
	}

	public OrderInfo getOrder() {
		return order;
	}

	public void setOrder(OrderInfo order) {
		this.order = order;
	}

	public String getStudentphone() {
		return studentphone;
	}

	public void setStudentphone(String studentphone) {
		this.studentphone = studentphone;
	}

	public String getCoachphone() {
		return coachphone;
	}

	public void setCoachphone(String coachphone) {
		this.coachphone = coachphone;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getMinsdate() {
		return minsdate;
	}

	public void setMinsdate(String minsdate) {
		this.minsdate = minsdate;
	}

	public String getMaxsdate() {
		return maxsdate;
	}

	public void setMaxsdate(String maxsdate) {
		this.maxsdate = maxsdate;
	}

	public CuserInfo getCoach() {
		return coach;
	}

	public void setCoach(CuserInfo coach) {
		this.coach = coach;
	}

	public SuserInfo getStudent() {
		return student;
	}

	public void setStudent(SuserInfo student) {
		this.student = student;
	}

	public List<ComplaintBookInfo> getComplaintbooklist() {
		return complaintbooklist;
	}

	public void setComplaintbooklist(List<ComplaintBookInfo> complaintbooklist) {
		this.complaintbooklist = complaintbooklist;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public int getChange_id() {
		return change_id;
	}

	public void setChange_id(int change_id) {
		this.change_id = change_id;
	}

	public String getNewaddtime() {
		return newaddtime;
	}

	public void setNewaddtime(String newaddtime) {
		this.newaddtime = newaddtime;
	}
	
	
}
