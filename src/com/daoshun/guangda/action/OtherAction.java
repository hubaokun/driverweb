package com.daoshun.guangda.action;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.daoshun.common.ApplePushUtil;
import com.daoshun.common.CommonUtils;
import com.daoshun.common.PushtoSingle;
import com.daoshun.common.QueryResult;
import com.daoshun.guangda.pojo.CuserInfo;
import com.daoshun.guangda.pojo.DriveSchoolInfo;
import com.daoshun.guangda.pojo.FeedBackInfo;
import com.daoshun.guangda.pojo.NoticesInfo;
import com.daoshun.guangda.pojo.NoticesUserInfo;
import com.daoshun.guangda.pojo.SuserInfo;
import com.daoshun.guangda.pojo.UserPushInfo;
import com.daoshun.guangda.pojo.VersionInfo;
import com.daoshun.guangda.service.ICUserService;
import com.daoshun.guangda.service.ICtaskService;

@ParentPackage("default")
@Controller
@Scope("prototype")
public class OtherAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4179287330191793206L;

	@Resource
	private ICtaskService ctaskService;

	@Resource
	private ICUserService cuserService;

	private List<FeedBackInfo> feedbacklist;

	private List<VersionInfo> versionlist;

	private CuserInfo cuser;

	private SuserInfo suser;

	private UserPushInfo userPushInfo;

	private FeedBackInfo feedbackInfo;

	private VersionInfo versionInfo;

	private NoticesInfo noticInfo;

	private NoticesUserInfo noticesUserInfo;

	// 使用者id，反馈来源id
	private int fromid;

	// 反馈来源对象类型
	private int type;

	// 反馈信息id
	private int feedbackid;

	// 左侧栏的索引
	private int index;

	private int change_id;

	// 搜索筛选关键字
	private String searchname;

	private String searchphone;

	private String starttime;

	private String endtime;

	// 版本id
	private int versionid;

	// 版本号
	private String versioncode;

	// 客户端显示版本
	private String versionname;

	// 版本类型
	private int versiontype;

	// 版本分类
	private int category;

	// 版本状态
	private int state;

	// 新版本号
	private String editversioncode;

	// 新客户端显示版本
	private String editversionname;

	// 新版本类型
	private Integer editversiontype;

	// 新版本分类
	private Integer editcategory;

	// 新版本状态
	private Integer editstate;

	// 新版本下载地址
	private String versiondownload;

	// 新版本客户端类型
	private Integer apptype;

	// 反馈回复内容
	private String feedbackcontent;

	private Integer pageIndex = 1;

	private int total;

	private int pageCount;

	private String errormessage;

	/**
	 * 得到反馈信息列表
	 * 
	 * @return
	 */
	@Action(value = "/getFeedback", results = { @Result(name = SUCCESS, location = "/feedback.jsp") })
	public String getFeedback() {
		feedbacklist = ctaskService.getFeedbackInfo();
		QueryResult<FeedBackInfo> result = ctaskService.getFeedbackInfoOfPage(searchname, searchphone, starttime, endtime, pageIndex, 10);
		feedbacklist = result.getDataList();
		total = (int) result.getTotal();
		pageCount = (int) ((total + 9) / 10);
		if (pageIndex > 1) {
			if (feedbacklist == null || feedbacklist.size() == 0) {
				pageIndex--;
				getFeedback();
			}
		}
		for (int i = 0; i < feedbacklist.size(); i++) {
			fromid = feedbacklist.get(i).getFromid();
			type = feedbacklist.get(i).getFrom_type();
			if (type == 1) {
				cuser = (CuserInfo) ctaskService.getFromuserBy(fromid, type);
				if (cuser != null) {
					feedbacklist.get(i).setFromrealname(cuser.getRealname());
					feedbacklist.get(i).setFromphone(cuser.getPhone());
				}
				cuserService.updateObject(feedbacklist.get(i));
			} else {
				suser = (SuserInfo) ctaskService.getFromuserBy(fromid, type);
				if (suser != null) {
					feedbacklist.get(i).setFromrealname(suser.getRealname());
					feedbacklist.get(i).setFromphone(suser.getPhone());
				}
				cuserService.updateObject(feedbacklist.get(i));
			}
		}
		return SUCCESS;
	}

	/**
	 * 关键字搜索反馈信息
	 * 
	 * @return
	 */
	@Action(value = "/getFeedbackBykeyword", results = { @Result(name = SUCCESS, location = "/feedback.jsp") })
	public String getFeedbackBykeyword() {
		if (!CommonUtils.isEmptyString(starttime) && !CommonUtils.isEmptyString(endtime)) {
			if (CommonUtils.getDateFormat(starttime, "yyyy-MM-dd").after(CommonUtils.getDateFormat(endtime, "yyyy-MM-dd"))) {
				errormessage = "结束时间不能晚于开始时间";
				return SUCCESS;
			}
		}
		feedbacklist = ctaskService.getFeedbackBykeyword(searchname, searchphone, starttime, endtime);
		for (int i = 0; i < feedbacklist.size(); i++) {
			fromid = feedbacklist.get(i).getFromid();
			type = feedbacklist.get(i).getFrom_type();
			if (type == 1) {
				cuser = (CuserInfo) ctaskService.getFromuserBy(fromid, type);
				feedbacklist.get(i).setFromrealname(cuser.getRealname());
				feedbacklist.get(i).setFromphone(cuser.getPhone());
				cuserService.updateObject(feedbacklist.get(i));
			} else {
				suser = (SuserInfo) ctaskService.getFromuserBy(fromid, type);
				feedbacklist.get(i).setFromrealname(suser.getRealname());
				feedbacklist.get(i).setFromphone(suser.getPhone());
				cuserService.updateObject(feedbacklist.get(i));
			}
		}
		return SUCCESS;
	}

	/**
	 * 删除意见反馈信息
	 */
	@Action(value = "/delFeedbackInfo")
	public void delFeedbackInfo() {
		feedbackInfo = ctaskService.getfeedbackByid(feedbackid);
		if (feedbackInfo != null) {
			cuserService.delObject(feedbackInfo);
			setResponseStr("success");
		} else {
			setResponseStr("error");
		}
	}

	/**
	 * 得到版本信息列表
	 * 
	 * @return
	 */
	@Action(value = "/getVersion", results = { @Result(name = SUCCESS, location = "/versionlist.jsp") })
	public String getVersion() {
		versionlist = ctaskService.getVersion();
		return SUCCESS;
	}

	/**
	 * 删除版本信息
	 */
	@Action(value = "/delVersionInfo")
	public void delVersionInfo() {
		versionInfo = ctaskService.getVersionInfoById(versionid);
		if (versionInfo != null) {
			cuserService.delObject(versionInfo);
			setResponseStr("success");
		} else {
			setResponseStr("error");
		}
	}

	/**
	 * 添加新版本信息
	 */
	@Action(value = "/addVersion")
	public void addVersion() {
		if (CommonUtils.isEmptyString(versioncode) || CommonUtils.isEmptyString(versionname)) {
			setResponseStr("error");
		} else {
			versionInfo = new VersionInfo();
			versionInfo.setCategory(category);
			versionInfo.setState(state);
			versionInfo.setVersioncode(CommonUtils.parseInt(versioncode, 0));
			versionInfo.setVersionname(versionname);
			versionInfo.setType(versiontype);
			versionInfo.setApptype(apptype);
			versionInfo.setDownload(versiondownload);
			versionInfo.setAddtime(new Date());
			cuserService.addObject(versionInfo);
			setResponseStr("success");
		}
	}

	/**
	 * 修改版本信息
	 */
	@Action(value = "/editVersion")
	public void editVersion() {
		versionInfo = ctaskService.getVersionInfoById(versionid);
		if (versionInfo == null) {
			setResponseStr("error1");
		} else {
			if (!CommonUtils.isEmptyString(editversioncode)) {
				versionInfo.setVersioncode(CommonUtils.parseInt(editversioncode, 0));
			}
			if (!CommonUtils.isEmptyString(editversionname)) {
				versionInfo.setVersionname(editversionname);
			}
			if (!CommonUtils.isEmptyString(versiondownload)) {
				versionInfo.setDownload(versiondownload);
			}
			versionInfo.setApptype(apptype);
			versionInfo.setCategory(editcategory);
			versionInfo.setState(editstate);
			versionInfo.setType(editversiontype);
			cuserService.updateObject(versionInfo);
			setResponseStr("success");
		}
	}

	/**
	 * 反馈的回复
	 */
	@Action(value = "/replyFeedback")
	public void replyFeedback() {
		if (type == 1) {
			cuser = (CuserInfo) ctaskService.getFromuserBy(fromid, type);
			if (cuser != null) {
				NoticesInfo noticeInfo = new NoticesInfo();
				noticeInfo.setAddtime(new Date());
				noticeInfo.setType(1);
				noticeInfo.setContent(feedbackcontent);
				noticeInfo.setCategory("意见反馈回复");
				cuserService.addObject(noticeInfo);
				NoticesUserInfo noticesUserInfo = new NoticesUserInfo();
				noticesUserInfo.setNoticeid(noticeInfo.getNoticeid());
				noticesUserInfo.setUserid(cuser.getCoachid());
				noticesUserInfo.setReadstate(0);
				cuserService.addObject(noticesUserInfo);
				userPushInfo = cuserService.getUserPushInfo(cuser.getCoachid(), 1);
				if (userPushInfo != null) {
					if (userPushInfo.getType() == 0 && !CommonUtils.isEmptyString(userPushInfo.getJpushid())) {
						PushtoSingle push = new PushtoSingle();
						push.pushsingle(userPushInfo.getJpushid(), 1, "{\"message\":\"" + "您收到了意见反馈回复" + "\",\"type\":\"5\"}");
					} else if (userPushInfo.getType() == 1 && !CommonUtils.isEmptyString(userPushInfo.getDevicetoken())) {
						ApplePushUtil.sendpush(userPushInfo.getDevicetoken(), "{\"aps\":{\"alert\":\"" + "您收到了意见反馈回复" + "\",\"sound\":\"default\"},\"userid\":" + cuser.getCoachid() + "}", 1, 1);
					}
				}
				setResponseStr("success");
			} else {
				setResponseStr("error");
			}
		} else {
			suser = (SuserInfo) ctaskService.getFromuserBy(fromid, type);
			if (suser != null) {
				NoticesInfo noticeInfo = new NoticesInfo();
				noticeInfo.setAddtime(new Date());
				noticeInfo.setType(2);
				noticeInfo.setContent(feedbackcontent);
				noticeInfo.setCategory("意见反馈回复");
				cuserService.addObject(noticeInfo);
				NoticesUserInfo noticesUserInfo = new NoticesUserInfo();
				noticesUserInfo.setNoticeid(noticeInfo.getNoticeid());
				noticesUserInfo.setUserid(suser.getStudentid());
				noticesUserInfo.setReadstate(0);
				cuserService.addObject(noticesUserInfo);
				userPushInfo = cuserService.getUserPushInfo(suser.getStudentid(), 2);
				if (userPushInfo != null) {
					if (userPushInfo.getType() == 0 && !CommonUtils.isEmptyString(userPushInfo.getJpushid())) {
						PushtoSingle push = new PushtoSingle();
						push.pushsingle(userPushInfo.getJpushid(), 2, "{\"message\":\"" + "您收到了意见反馈回复" + "\",\"type\":\"5\"}");
					} else if (userPushInfo.getType() == 1 && !CommonUtils.isEmptyString(userPushInfo.getDevicetoken())) {
						ApplePushUtil.sendpush(userPushInfo.getDevicetoken(), "{\"aps\":{\"alert\":\"" + "您收到了意见反馈回复" + "\",\"sound\":\"default\"},\"userid\":" + cuser.getCoachid() + "}", 1, 1);
					}
				}
				setResponseStr("success");
			} else {
				setResponseStr("error");
			}
		}
	}

	public List<FeedBackInfo> getFeedbacklist() {
		return feedbacklist;
	}

	public void setFeedbacklist(List<FeedBackInfo> feedbacklist) {
		this.feedbacklist = feedbacklist;
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

	public FeedBackInfo getFeedbackInfo() {
		return feedbackInfo;
	}

	public void setFeedbackInfo(FeedBackInfo feedbackInfo) {
		this.feedbackInfo = feedbackInfo;
	}

	public int getFeedbackid() {
		return feedbackid;
	}

	public void setFeedbackid(int feedbackid) {
		this.feedbackid = feedbackid;
	}

	public int getFromid() {
		return fromid;
	}

	public void setFromid(int fromid) {
		this.fromid = fromid;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getSearchname() {
		return searchname;
	}

	public void setSearchname(String searchname) {
		this.searchname = searchname;
	}

	public String getSearchphone() {
		return searchphone;
	}

	public void setSearchphone(String searchphone) {
		this.searchphone = searchphone;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public List<VersionInfo> getVersionlist() {
		return versionlist;
	}

	public void setVersionlist(List<VersionInfo> versionlist) {
		this.versionlist = versionlist;
	}

	public int getVersionid() {
		return versionid;
	}

	public void setVersionid(int versionid) {
		this.versionid = versionid;
	}

	public VersionInfo getVersionInfo() {
		return versionInfo;
	}

	public void setVersionInfo(VersionInfo versionInfo) {
		this.versionInfo = versionInfo;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getVersioncode() {
		return versioncode;
	}

	public void setVersioncode(String versioncode) {
		this.versioncode = versioncode;
	}

	public String getVersionname() {
		return versionname;
	}

	public void setVersionname(String versionname) {
		this.versionname = versionname;
	}

	public int getVersiontype() {
		return versiontype;
	}

	public void setVersiontype(int versiontype) {
		this.versiontype = versiontype;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getEditversioncode() {
		return editversioncode;
	}

	public void setEditversioncode(String editversioncode) {
		this.editversioncode = editversioncode;
	}

	public String getEditversionname() {
		return editversionname;
	}

	public void setEditversionname(String editversionname) {
		this.editversionname = editversionname;
	}

	public Integer getEditversiontype() {
		return editversiontype;
	}

	public void setEditversiontype(Integer editversiontype) {
		this.editversiontype = editversiontype;
	}

	public Integer getEditcategory() {
		return editcategory;
	}

	public void setEditcategory(Integer editcategory) {
		this.editcategory = editcategory;
	}

	public Integer getEditstate() {
		return editstate;
	}

	public void setEditstate(Integer editstate) {
		this.editstate = editstate;
	}

	public String getFeedbackcontent() {
		return feedbackcontent;
	}

	public void setFeedbackcontent(String feedbackcontent) {
		this.feedbackcontent = feedbackcontent;
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

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public NoticesInfo getNoticInfo() {
		return noticInfo;
	}

	public void setNoticInfo(NoticesInfo noticInfo) {
		this.noticInfo = noticInfo;
	}

	public NoticesUserInfo getNoticesUserInfo() {
		return noticesUserInfo;
	}

	public void setNoticesUserInfo(NoticesUserInfo noticesUserInfo) {
		this.noticesUserInfo = noticesUserInfo;
	}

	public String getVersiondownload() {
		return versiondownload;
	}

	public void setVersiondownload(String versiondownload) {
		this.versiondownload = versiondownload;
	}

	public Integer getApptype() {
		return apptype;
	}

	public void setApptype(Integer apptype) {
		this.apptype = apptype;
	}

	public String getErrormessage() {
		return errormessage;
	}

	public void setErrormessage(String errormessage) {
		this.errormessage = errormessage;
	}

}
