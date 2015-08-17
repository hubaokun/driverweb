package com.daoshun.guangda.action;

import java.util.Date;
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
import com.daoshun.common.ApplePushUtil;
import com.daoshun.common.CommonUtils;
import com.daoshun.common.DeviceType;
import com.daoshun.common.PushtoSingle;
import com.daoshun.common.QueryResult;
import com.daoshun.guangda.pojo.CuserInfo;
import com.daoshun.guangda.pojo.NoticesInfo;
import com.daoshun.guangda.pojo.NoticesUserInfo;
import com.daoshun.guangda.pojo.OrderInfo;
import com.daoshun.guangda.pojo.SuserInfo;
import com.daoshun.guangda.pojo.UserPushInfo;
import com.daoshun.guangda.service.ICUserService;
import com.daoshun.guangda.service.ICtaskService;
import com.daoshun.guangda.service.ISUserService;

@ParentPackage("default")
@Controller
@Scope("prototype")
public class SystemNoticeAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8235293115972322759L;

	@Resource
	private ICtaskService ctaskService;

	@Resource
	private ICUserService cuserService;

	@Resource
	private ISUserService suserService;

	private List<NoticesInfo> noticeslist;

	private List<CuserInfo> cuserlist;

	private List<SuserInfo> suserlist;

	private List<OrderInfo> orderlist;

	private NoticesInfo noticeInfo;

	private UserPushInfo userPushInfo;

	private NoticesUserInfo noticesUserInfo;

	private CuserInfo cuser;

	private SuserInfo suser;

	private Integer pageIndex = 1;

	private Integer pageCount = 0;

	private long total;

	private int noticeid;

	private String noticestarttime;

	private String noticeendtime;

	private int index;

	private int change_id;

	private int userid;

	private String searchname;

	private Integer usertype;

	private String searchuserdiv;

	private Integer settype;

	private String category;

	private String contents;

	private Integer singleuserid;

	/**
	 * 得到通知信息列表
	 * 
	 * @return
	 */
	@Action(value = "getNoticeList", results = { @Result(name = SUCCESS, location = "/noticelist.jsp") })
	public String getNoticeList() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		int pagesize = CommonUtils.parseInt(String.valueOf(session.getAttribute("pagesize")), 10);
		QueryResult<NoticesInfo> result = ctaskService.getNoticesInfoList(noticestarttime, noticeendtime, pageIndex, pagesize);
		total = result.getTotal();
		noticeslist = result.getDataList();
		for (int i = 0; i < noticeslist.size(); i++) {
			if (noticeslist.get(i).getType() == 1) {
				noticesUserInfo = ctaskService.getNoticeUserInfoByid(noticeslist.get(i).getNoticeid());
				if (noticesUserInfo != null) {
					userid = noticesUserInfo.getUserid();
				}
				cuser = cuserService.getCuserByCoachid(String.valueOf(userid));
				if (cuser != null) {
					noticeslist.get(i).setRealname(cuser.getRealname());
				}
				cuserService.updateObject(noticeslist.get(i));
			} else {
				noticesUserInfo = ctaskService.getNoticeUserInfoByid(noticeslist.get(i).getNoticeid());
				if (noticesUserInfo != null) {
					userid = noticesUserInfo.getUserid();
				}
				suser = suserService.getUserById(String.valueOf(userid));
				if (suser != null) {
					noticeslist.get(i).setRealname(suser.getRealname());
				}
				cuserService.updateObject(noticeslist.get(i));
			}
		}
		pageCount = ((int) result.getTotal() + pagesize - 1) / pagesize;
		if (pageIndex > 1) {
			if (noticeslist == null || noticeslist.size() == 0) {
				pageIndex--;
				getNoticeList();
			}
		}
		return SUCCESS;
	}

	/**
	 * 关键字搜索通知列表
	 * 
	 * @return
	 */
	@Action(value = "getNoticeByKeyword", results = { @Result(name = SUCCESS, location = "/noticelist.jsp") })
	public String getNoticeByKeyword() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		int pagesize = CommonUtils.parseInt(String.valueOf(session.getAttribute("pagesize")), 10);
		QueryResult<NoticesInfo> result = ctaskService.getNoticeBykeyword(noticestarttime, noticeendtime, pageIndex, pagesize);
		total = result.getTotal();
		noticeslist = result.getDataList();
		for (int i = 0; i < noticeslist.size(); i++) {
			if (noticeslist.get(i).getType() == 1) {
				userid = ctaskService.getNoticeUserInfoByid(noticeslist.get(i).getNoticeid()).getUserid();
				cuser = cuserService.getCuserByCoachid(String.valueOf(userid));
				if (cuser != null) {
					noticeslist.get(i).setRealname(cuser.getRealname());
				}
				cuserService.updateObject(noticeslist.get(i));
			} else {
				noticesUserInfo = ctaskService.getNoticeUserInfoByid(noticeslist.get(i).getNoticeid());
				if (noticesUserInfo != null) {
					userid = noticesUserInfo.getUserid();
				}
				suser = suserService.getUserById(String.valueOf(userid));
				if (suser != null) {
					noticeslist.get(i).setRealname(suser.getRealname());
				}
				cuserService.updateObject(noticeslist.get(i));
			}
		}
		pageCount = ((int) result.getTotal() + pagesize - 1) / pagesize;
		if (pageIndex > 1) {
			if (noticeslist == null || noticeslist.size() == 0) {
				pageIndex--;
				getNoticeByKeyword();
			}
		}
		return SUCCESS;
	}

	/**
	 * 删除通知
	 */
	@Action(value = "delNotice")
	public void delNotice() {
		noticeInfo = ctaskService.getNoticeById(noticeid);
		if (noticeInfo == null) {
			setResponseStr("error");
			return;
		} else {
			cuserService.delObject(noticeInfo);
			setResponseStr("success");
		}
	}

	@Action(value = "jump", results = { @Result(name = SUCCESS, location = "/setNotice.jsp") })
	public String jump() {
		return SUCCESS;
	}

	/**
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Action(value = "noticeSearchUser")
	public void noticeSearchUser() {
		Map<String, Object> map = new HashMap<String, Object>();
		if (usertype == 1 || usertype == 3 || usertype == 4) {
			cuserlist = (List<CuserInfo>) ctaskService.getUserInfo(usertype, searchname);
			map.put("list", cuserlist);
			map.put("state", 200);
			map.put("type", 1);
			strToJson(map);
			setResponseStr("success");
		} else {
			suserlist = (List<SuserInfo>) ctaskService.getUserInfo(usertype, searchname);
			map.put("list", suserlist);
			map.put("state", 200);
			map.put("type", 2);
			strToJson(map);
			setResponseStr("success");
		}
	}

	@Action(value = "setMessage")
	public void setMessage() {
		// 教练群发
		if (usertype == 1 && settype == 0) {
			Demo1 d =new Demo1();
		    Thread t = new Thread(d);
			setResponseStr("success");
			t.start();
		} else
		// 学员群发
		if (usertype == 2 && settype == 0) {
			Demo2 d =new Demo2();
		    Thread t = new Thread(d);
			setResponseStr("success");
			t.start();
		} else
		// 教练单发
		if (usertype == 1 && settype == 1) {
			NoticesInfo noticeInfo = new NoticesInfo();
			noticeInfo.setAddtime(new Date());
			noticeInfo.setType(1);
			noticeInfo.setContent(contents);
			noticeInfo.setCategory(category);
			cuserService.addObject(noticeInfo);
			NoticesUserInfo noticesUserInfo = new NoticesUserInfo();
			noticesUserInfo.setNoticeid(noticeInfo.getNoticeid());
			noticesUserInfo.setUserid(singleuserid);
			noticesUserInfo.setReadstate(0);
			cuserService.addObject(noticesUserInfo);
			userPushInfo = cuserService.getUserPushInfo(singleuserid, 1);
			if (userPushInfo != null) {
				if (userPushInfo.getType() == DeviceType.ANDROID && !CommonUtils.isEmptyString(userPushInfo.getJpushid())) {
					// 安卓
					PushtoSingle pushsingle = new PushtoSingle();
					pushsingle.pushsingle(userPushInfo.getJpushid(), 1, "{\"message\":\"" + "您有新的通知" + "\",\"type\":\"3\"}");

				}
				if (userPushInfo.getType() == DeviceType.IOS && !CommonUtils.isEmptyString(userPushInfo.getDevicetoken())) {
					// ihpone
					ApplePushUtil.sendpush(userPushInfo.getDevicetoken(), "{\"aps\":{\"alert\":\"" + "您有新的通知" + "\",\"sound\":\"default\"},\"userid\":" + singleuserid + "}", 1, 1);
				}
				//
			}
			setResponseStr("success");
		} else
		// 学员单发
		if (usertype == 2 && settype == 1) {
			NoticesInfo noticeInfo = new NoticesInfo();
			noticeInfo.setAddtime(new Date());
			noticeInfo.setType(2);
			noticeInfo.setContent(contents);
			noticeInfo.setCategory(category);
			cuserService.addObject(noticeInfo);
			NoticesUserInfo noticesUserInfo = new NoticesUserInfo();
			noticesUserInfo.setNoticeid(noticeInfo.getNoticeid());
			noticesUserInfo.setUserid(singleuserid);
			noticesUserInfo.setReadstate(0);
			cuserService.addObject(noticesUserInfo);
			userPushInfo = cuserService.getUserPushInfo(singleuserid, 2);
			if (userPushInfo != null) {
				if (userPushInfo.getType() == DeviceType.ANDROID && !CommonUtils.isEmptyString(userPushInfo.getJpushid())) {
					// 安卓
					PushtoSingle pushsingle = new PushtoSingle();
					pushsingle.pushsingle(userPushInfo.getJpushid(), 2, "{\"message\":\"" + "您有新的通知" + "\",\"type\":\"3\"}");

				}
				if (userPushInfo.getType() == DeviceType.IOS && !CommonUtils.isEmptyString(userPushInfo.getDevicetoken())) {
					// ihpone
					ApplePushUtil.sendpush(userPushInfo.getDevicetoken(), "{\"aps\":{\"alert\":\"" + "您有新的通知" + "\",\"sound\":\"default\"},\"userid\":" + singleuserid + "}", 1, 2);
				}
			}
			setResponseStr("success");
		} else
		// 教练所有学员发
		if (usertype == 3 && !CommonUtils.isEmptyString(String.valueOf(singleuserid))) {
			Demo3 d =new Demo3();
		    Thread t = new Thread(d);
			setResponseStr("success");
			t.start();
		} else
		// 教练所有未完成订单发
		if (usertype == 4 && !CommonUtils.isEmptyString(String.valueOf(singleuserid))) {
			orderlist = ctaskService.getStudentofCoach(singleuserid, usertype);
			NoticesInfo noticeInfo = new NoticesInfo();
			noticeInfo.setAddtime(new Date());
			noticeInfo.setType(2);
			noticeInfo.setContent(contents);
			noticeInfo.setCategory(category);
			cuserService.addObject(noticeInfo);
			for (int i = 0; i < orderlist.size(); i++) {
				NoticesUserInfo noticesUserInfo = new NoticesUserInfo();
				noticesUserInfo.setNoticeid(noticeInfo.getNoticeid());
				noticesUserInfo.setUserid(orderlist.get(i).getStudentid());
				noticesUserInfo.setReadstate(0);
				cuserService.addObject(noticesUserInfo);
				userPushInfo = cuserService.getUserPushInfo(orderlist.get(i).getStudentid(), 2);
				if (userPushInfo != null) {
					if (userPushInfo.getType() == DeviceType.ANDROID && !CommonUtils.isEmptyString(userPushInfo.getJpushid())) {
						// 安卓
						PushtoSingle pushsingle = new PushtoSingle();
						pushsingle.pushsingle(userPushInfo.getJpushid(), 2, "{\"message\":\"" + "您有新的通知" + "\",\"type\":\"3\"}");

					}
					if (userPushInfo.getType() == DeviceType.IOS && !CommonUtils.isEmptyString(userPushInfo.getDevicetoken())) {
						// ihpone
						ApplePushUtil.sendpush(userPushInfo.getDevicetoken(), "{\"aps\":{\"alert\":\"" + "您有新的通知" + "\",\"sound\":\"default\"},\"userid\":" + orderlist.get(i).getStudentid() + "}", 1,
								2);
					}
				}
			}
			setResponseStr("success");
		} else {
			setResponseStr("error");
			return;
		}
	}
	
	class Demo1 implements Runnable{
	    public void run(){
			cuserlist = cuserService.getCoachlist();
			NoticesInfo noticeInfo = new NoticesInfo();
			noticeInfo.setAddtime(new Date());
			noticeInfo.setType(1);
			noticeInfo.setContent(contents);
			noticeInfo.setCategory(category);
			cuserService.addObject(noticeInfo);
			for (int i = 0; i < cuserlist.size(); i++) {
				NoticesUserInfo noticesUserInfo = new NoticesUserInfo();
				noticesUserInfo.setNoticeid(noticeInfo.getNoticeid());
				noticesUserInfo.setUserid(cuserlist.get(i).getCoachid());
				noticesUserInfo.setReadstate(0);
				cuserService.addObject(noticesUserInfo);
				userPushInfo = cuserService.getUserPushInfo(cuserlist.get(i).getCoachid(), 1);
				if (userPushInfo != null) {
					if (userPushInfo.getType() == DeviceType.ANDROID && !CommonUtils.isEmptyString(userPushInfo.getJpushid())) {
						// 安卓
						PushtoSingle pushsingle = new PushtoSingle();
						pushsingle.pushsingle(userPushInfo.getJpushid(), 1, "{\"message\":\"" + "您有新的通知" + "\",\"type\":\"3\"}");

					}
					if (userPushInfo.getType() == DeviceType.IOS && !CommonUtils.isEmptyString(userPushInfo.getDevicetoken())) {
						// ihpone
						ApplePushUtil
								.sendpush(userPushInfo.getDevicetoken(), "{\"aps\":{\"alert\":\"" + "您有新的通知" + "\",\"sound\":\"default\"},\"userid\":" + cuserlist.get(i).getCoachid() + "}", 1, 1);
					}
				}
			}
	    }
	}
	
	class Demo2 implements Runnable{
		public void run(){
			suserlist = cuserService.getStudentlist();
			NoticesInfo noticeInfo = new NoticesInfo();
			noticeInfo.setAddtime(new Date());
			noticeInfo.setType(2);
			noticeInfo.setContent(contents);
			noticeInfo.setCategory(category);
			cuserService.addObject(noticeInfo);
			for (int i = 0; i < suserlist.size(); i++) {
				NoticesUserInfo noticesUserInfo = new NoticesUserInfo();
				noticesUserInfo.setNoticeid(noticeInfo.getNoticeid());
				noticesUserInfo.setUserid(suserlist.get(i).getStudentid());
				noticesUserInfo.setReadstate(0);
				cuserService.addObject(noticesUserInfo);
				userPushInfo = cuserService.getUserPushInfo(suserlist.get(i).getStudentid(), 2);
				if (userPushInfo != null) {
					if (userPushInfo.getType() == DeviceType.ANDROID && !CommonUtils.isEmptyString(userPushInfo.getJpushid())) {
						// 安卓
						PushtoSingle pushsingle = new PushtoSingle();
						pushsingle.pushsingle(userPushInfo.getJpushid(), 2, "{\"message\":\"" + "您有新的通知" + "\",\"type\":\"3\"}");

					}
					if (userPushInfo.getType() == DeviceType.IOS && !CommonUtils.isEmptyString(userPushInfo.getDevicetoken())) {
						// ihpone
						ApplePushUtil.sendpush(userPushInfo.getDevicetoken(), "{\"aps\":{\"alert\":\"" + "您有新的通知" + "\",\"sound\":\"default\"},\"userid\":" + suserlist.get(i).getStudentid() + "}", 1,
								2);
					}
				}
			}
		}
	}
	
	class Demo3 implements Runnable{
		public void run(){
			orderlist = ctaskService.getStudentofCoach(singleuserid, usertype);
			NoticesInfo noticeInfo = new NoticesInfo();
			noticeInfo.setAddtime(new Date());
			noticeInfo.setType(2);
			noticeInfo.setContent(contents);
			noticeInfo.setCategory(category);
			cuserService.addObject(noticeInfo);
			for (int i = 0; i < orderlist.size(); i++) {
				NoticesUserInfo noticesUserInfo = new NoticesUserInfo();
				noticesUserInfo.setNoticeid(noticeInfo.getNoticeid());
				noticesUserInfo.setUserid(orderlist.get(i).getStudentid());
				noticesUserInfo.setReadstate(0);
				cuserService.addObject(noticesUserInfo);
				userPushInfo = cuserService.getUserPushInfo(orderlist.get(i).getStudentid(), 2);
				if (userPushInfo != null) {

					if (userPushInfo.getType() == DeviceType.ANDROID && !CommonUtils.isEmptyString(userPushInfo.getJpushid())) {
						// 安卓
						PushtoSingle pushsingle = new PushtoSingle();
						pushsingle.pushsingle(userPushInfo.getJpushid(), 2, "{\"message\":\"" + "您有新的通知" + "\",\"type\":\"3\"}");
					}
					if (userPushInfo.getType() == DeviceType.IOS && !CommonUtils.isEmptyString(userPushInfo.getDevicetoken())) {
						// ihpone
						ApplePushUtil.sendpush(userPushInfo.getDevicetoken(), "{\"aps\":{\"alert\":\"" + "您有新的通知" + "\",\"sound\":\"default\"},\"userid\":" + orderlist.get(i).getStudentid() + "}", 1,
								2);
					}

				}
			}
		}
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

	public long getTotal() {
		return total;
	}

	public List<NoticesInfo> getNoticeslist() {
		return noticeslist;
	}

	public void setNoticeslist(List<NoticesInfo> noticeslist) {
		this.noticeslist = noticeslist;
	}

	public int getNoticeid() {
		return noticeid;
	}

	public void setNoticeid(int noticeid) {
		this.noticeid = noticeid;
	}

	public NoticesInfo getNoticeInfo() {
		return noticeInfo;
	}

	public void setNoticeInfo(NoticesInfo noticeInfo) {
		this.noticeInfo = noticeInfo;
	}

	public String getNoticestarttime() {
		return noticestarttime;
	}

	public void setNoticestarttime(String noticestarttime) {
		this.noticestarttime = noticestarttime;
	}

	public String getNoticeendtime() {
		return noticeendtime;
	}

	public void setNoticeendtime(String noticeendtime) {
		this.noticeendtime = noticeendtime;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public CuserInfo getCuser() {
		return cuser;
	}

	public void setCuser(CuserInfo cuser) {
		this.cuser = cuser;
	}

	public SuserInfo getSuser() {
		return suser;
	}

	public void setSuser(SuserInfo suser) {
		this.suser = suser;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getSearchname() {
		return searchname;
	}

	public void setSearchname(String searchname) {
		this.searchname = searchname;
	}

	public Integer getUsertype() {
		return usertype;
	}

	public void setUsertype(Integer usertype) {
		this.usertype = usertype;
	}

	public List<CuserInfo> getCuserlist() {
		return cuserlist;
	}

	public void setCuserlist(List<CuserInfo> cuserlist) {
		this.cuserlist = cuserlist;
	}

	public List<SuserInfo> getSuserlist() {
		return suserlist;
	}

	public void setSuserlist(List<SuserInfo> suserlist) {
		this.suserlist = suserlist;
	}

	public String getSearchuserdiv() {
		return searchuserdiv;
	}

	public void setSearchuserdiv(String searchuserdiv) {
		this.searchuserdiv = searchuserdiv;
	}

	public Integer getSettype() {
		return settype;
	}

	public void setSettype(Integer settype) {
		this.settype = settype;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public Integer getSingleuserid() {
		return singleuserid;
	}

	public void setSingleuserid(Integer singleuserid) {
		this.singleuserid = singleuserid;
	}

	public NoticesUserInfo getNoticesUserInfo() {
		return noticesUserInfo;
	}

	public void setNoticesUserInfo(NoticesUserInfo noticesUserInfo) {
		this.noticesUserInfo = noticesUserInfo;
	}

	public List<OrderInfo> getOrderlist() {
		return orderlist;
	}

	public void setOrderlist(List<OrderInfo> orderlist) {
		this.orderlist = orderlist;
	}

	public UserPushInfo getUserPushInfo() {
		return userPushInfo;
	}

	public void setUserPushInfo(UserPushInfo userPushInfo) {
		this.userPushInfo = userPushInfo;
	}

	public int getChange_id() {
		return change_id;
	}

	public void setChange_id(int change_id) {
		this.change_id = change_id;
	}

}
