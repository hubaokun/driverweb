package com.daoshun.guangda.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.bouncycastle.asn1.x509.UserNotice;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.daoshun.common.ApplePushUtil;
import com.daoshun.common.CommonUtils;
import com.daoshun.common.DeviceType;
import com.daoshun.common.ExslImport;
import com.daoshun.common.PushtoSingle;
import com.daoshun.common.QueryResult;
import com.daoshun.guangda.NetData.CouponRecordForExcel;
import com.daoshun.guangda.NetData.StudentInfoForExcel;
import com.daoshun.guangda.pojo.CoachStudentInfo;
import com.daoshun.guangda.pojo.CouponCoach;
import com.daoshun.guangda.pojo.CouponInfo;
import com.daoshun.guangda.pojo.CouponRecord;
import com.daoshun.guangda.pojo.CuserInfo;
import com.daoshun.guangda.pojo.DriveSchoolInfo;
import com.daoshun.guangda.pojo.NoticesInfo;
import com.daoshun.guangda.pojo.NoticesUserInfo;
import com.daoshun.guangda.pojo.OrderRecordInfo;
import com.daoshun.guangda.pojo.SuserInfo;
import com.daoshun.guangda.pojo.UserPushInfo;
import com.daoshun.guangda.service.ICUserService;
import com.daoshun.guangda.service.ICouponService;
import com.daoshun.guangda.service.IDriveSchoolService;
import com.daoshun.guangda.service.ISUserService;

@ParentPackage("default")
@Controller
@Scope("prototype")
public class CouponAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Resource
	private ICouponService couponService;

	@Resource
	private ISUserService suserService;

	@Resource
	private ICUserService cuserService;

	@Resource
	private IDriveSchoolService driveSchoolService;

	private SuserInfo suser;

	private List<CouponInfo> couponlist;

	private List<CouponRecord> couponrecordlist;
	
	private List<CouponCoach> couponcoachlist;

	private Integer pageIndex = 1;

	private int pageCount;

	private Integer state;// 状态 0

	private Integer coupontype;

	private String starttime;

	private String endtime;

	private Integer value;
	
	private int userid;

	private Integer valuetype;

	private int total;

	private int addvalue;

	private Date addendtime;

	private int addcoupontype;

	private int pub_count;

	private int rest_count;

	private int couponid;

	private int recordid;

	private String cuserkeyword;

	private String schoolkeyword;

	private int givingtype;

	private int pushtype;

	private int addownertype;

	private int addownerid;
	private int schoolownerid;

	private String searchsuserid;

	private String pushcontent;

	private String username;

	private Integer ownertype;

	private String ownerkey;

	private String exslfileFileName;

	private File exslfile;

	private int pushnum;

	private int searchschoolid;

	private int searchcoachid;

	private int errortype;

	private String errormessage;
	
	private String couponrecorddate;

	/**
	 * 得到优惠券列表
	 * 
	 * @return
	 */
	@Action(value = "getCouponList", results = { @Result(name = SUCCESS, location = "/couponlist.jsp") })
	public String getCouponList() {
		QueryResult<CouponInfo> result = couponService.getCouponListByPage(pageIndex, 15, coupontype, starttime, endtime, value, valuetype, ownertype, ownerkey);
		couponlist = result.getDataList();
		total = (int) result.getTotal();
		pageCount = (total + 14) / 15;
		if (pageIndex > 1) {
			if (couponlist == null || couponlist.size() == 0) {
				pageIndex--;
				getCouponList();
			}
		}
		return SUCCESS;
	}

	/**
	 * 得到优惠券列表
	 * 
	 * @return
	 */
	@Action(value = "getCouponRecordList", results = { @Result(name = SUCCESS, location = "/couponrecordlist.jsp") })
	public String getCouponRecordList() {
		QueryResult<CouponRecord> result = couponService.getCouponReecordListByPage(pageIndex, 15, username, coupontype, starttime, endtime, value, valuetype, ownertype, ownerkey, state,userid);
		couponrecordlist = result.getDataList();
		total = (int) result.getTotal();
		pageCount = (total + 14) / 15;
		if (pageIndex > 1) {
			if (couponrecordlist == null || couponrecordlist.size() == 0) {
				pageIndex--;
				getCouponRecordList();
			}
		}
		return SUCCESS;
	}
	
	
	/**
	 * 得到小巴券发放记录
	 * 
	 * @return
	 */
	@Action(value = "getCouponRecordInfo", results = { @Result(name = SUCCESS, location = "/couponrecordInfo.jsp") })
	public String getCouponRecordInfo() {
		QueryResult<CouponRecord> result = couponService.getCouponReecordInfoByPage(pageIndex, 15, username, coupontype, starttime, endtime, value, valuetype, ownertype, ownerkey, state);
		couponrecordlist = result.getDataList();
		total = (int) result.getTotal();
		pageCount = (total + 14) / 15;
		if (pageIndex > 1) {
			if (couponrecordlist == null || couponrecordlist.size() == 0) {
				pageIndex--;
				getCouponRecordList();
			}
		}
		return SUCCESS;
	}
	
	
	/**
	 * 得到教练小巴券兑换记录
	 * 
	 * @return
	 */
	@Action(value = "getCouponCoachInfo", results = { @Result(name = SUCCESS, location = "/couponcoachInfo.jsp") })
	public String getCouponCoachInfo() {
		QueryResult<CouponCoach> result = couponService.getCouponCoachInfoByPage(pageIndex, 15, username, coupontype, starttime, endtime, value, valuetype, ownertype, ownerkey, state);
		couponcoachlist = result.getDataList();
		total = (int) result.getTotal();
		pageCount = (total + 14) / 15;
		if (pageIndex > 1) {
			if (couponcoachlist == null || couponcoachlist.size() == 0) {
				pageIndex--;
				getCouponcoachlist();
			}
		}
		return SUCCESS;
	}


	// /**
	// * 得到作废的优惠券列表
	// *
	// * @return
	// */
	// @Action(value = "getCancelCouponList", results = { @Result(name = SUCCESS, location = "/cancelcouponlist.jsp") })
	// public String getCancelCouponList() {
	// QueryResult<CouponRecord> result = couponService.getCancelCouponReecordListByPage(pageIndex, 15, username, coupontype, starttime, endtime, value, valuetype, ownertype, ownerkey);
	// couponrecordlist = result.getDataList();
	// total = (int) result.getTotal();
	// pageCount = (total + 9) / 15;
	// if (pageIndex > 1) {
	// if (couponrecordlist == null || couponrecordlist.size() == 0) {
	// pageIndex--;
	// getCouponRecordList();
	// }
	// }
	// return SUCCESS;
	// }

	@Action(value = "turnaddcoupon", results = { @Result(name = SUCCESS, location = "/addcoupon.jsp") })
	public String turnaddcoupon() {
		return SUCCESS;
	}

	// 删除优惠券
	@Action(value = "delcoupon")
	public void delcoupon() {
		couponService.delCouponById(couponid);
	}

	// 作废优惠券
	@Action(value = "cancelcoupon")
	public void cancelcoupon() {
		couponService.cancelcoupon(recordid);
	}
	
	//作废所有未使用优惠券
	@Action(value = "cancelallcoupon", results = { @Result(name = SUCCESS, location = "/couponrecordlist.jsp") })
	public String cancelallcoupon() {
		couponService.cancelallcoupon(userid);
		return SUCCESS;
	}
	
	
	

	@Action(value = "searchSuser")
	public void searchSuser() {
		List<SuserInfo> suserlist = couponService.getSuserInfoByKeyword(cuserkeyword);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("suserlist", suserlist);
		strToJson(map);
	}

	@Action(value = "searchDriveSchool")
	public void searchDriveSchool() {
		List<DriveSchoolInfo> schoollist = couponService.getDriveSchoolInfoByKeyword(schoolkeyword);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("schoollist", schoollist);
		strToJson(map);
	}

	@Action(value = "searchCuser")
	public void searchCuser() {
		List<CuserInfo> cuserlist = couponService.getCuserInfoByKeyword(cuserkeyword);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("cuserlist", cuserlist);
		strToJson(map);
	}

	@Action(value = "addcoupon", results = { @Result(name = SUCCESS, location = "/getCouponList.do", type = "redirect") })
	public String addcoupon() {
		CouponInfo coupon = new CouponInfo();
		coupon.setCoupontype(addcoupontype);
		coupon.setOwnertype(addownertype);
		coupon.setPub_count(pub_count);
		coupon.setRest_count(pub_count);
		coupon.setValue(addvalue);
		coupon.setOwnerid(addownerid);
		String newtime = CommonUtils.getTimeFormat(addendtime, "yyyy-MM-dd");
		Date newend = CommonUtils.getDateFormat(newtime, "yyyy-MM-dd");
		Calendar sectime = Calendar.getInstance();
		sectime.setTime(newend);
		sectime.set(Calendar.HOUR_OF_DAY, 23);
		sectime.set(Calendar.MINUTE, 59);
		sectime.set(Calendar.SECOND, 59);
		Date newendtime = sectime.getTime();
		coupon.setEnd_time(newendtime);
		coupon.setAddtime(new Date());
		couponService.addCoupon(coupon);
		return SUCCESS;
	}

	@Action(value = "addcoupongiving", results = { @Result(name = SUCCESS, location = "/getCouponList.do", type = "redirect"),
			@Result(name = ERROR, location = "/getCouponList.do?errortype=1", type = "redirect"), @Result(name = "ERROR1", location = "/getCouponList.do?errortype=2", type = "redirect"), })
	public String addcoupongiving() {
		CouponInfo coupon = couponService.getCounponInfoById(couponid);
		if (givingtype == 1) {
			List<SuserInfo> userlist = suserService.getUserCount();
			if (coupon.getRest_count() < userlist.size() * pushnum) {
				return ERROR;
			} else {
				for (SuserInfo suer : userlist) {
					for (int i = 0; i < pushnum; i++) {
						CouponRecord couponrecord = new CouponRecord();
						couponrecord.setCouponid(couponid);
						couponrecord.setUserid(suer.getStudentid());
						couponrecord.setGettime(new Date());
						couponrecord.setValue(coupon.getValue());
						couponrecord.setOwnertype(coupon.getOwnertype());
						couponrecord.setState(0);
						couponrecord.setOwnerid(coupon.getOwnerid());
						couponrecord.setCoupontype(coupon.getCoupontype());
						couponrecord.setEnd_time(coupon.getEnd_time());
						couponrecord.setUsetime(null);
						couponService.addCouponRecord(couponrecord);
						coupon.setRest_count(coupon.getRest_count() - 1);
						couponService.updateCouponInfo(coupon);
					}
					String message = "您收到" + pushnum + "张小巴券哦,请注意查收";

					if (pushtype == 1) {// 短信
						CommonUtils.sendSms(suer.getPhone(), message);
					} else {// 推送通知
						UserPushInfo userPushInfo = cuserService.getUserPushInfo(suer.getStudentid(), 2);
						if (userPushInfo != null && userPushInfo.getDevicetoken() != null) {
							if (userPushInfo.getType() == DeviceType.ANDROID && !CommonUtils.isEmptyString(userPushInfo.getJpushid())) {
								PushtoSingle push = new PushtoSingle();
								push.pushsingle(userPushInfo.getJpushid(), 2, "{\"message\":\"" + message + "\",\"type\":\"4\"}");
							} else if (userPushInfo.getType() == DeviceType.IOS && !CommonUtils.isEmptyString(userPushInfo.getDevicetoken())) {
								ApplePushUtil.sendpush(userPushInfo.getDevicetoken(), "{\"aps\":{\"alert\":\"" + message + "\",\"sound\":\"default\"},\"userid\":" + suer.getStudentid() + "}", 1, 2);
							}
						}
					}

					if (!CommonUtils.isEmptyString(pushcontent)) {

						// 如果消息不为空的话,生成系统通知
						NoticesInfo noticeInfo = new NoticesInfo();
						noticeInfo.setAddtime(new Date());
						noticeInfo.setType(2);
						noticeInfo.setContent(pushcontent);
						noticeInfo.setCategory("小巴券领取");
						cuserService.addObject(noticeInfo);

						NoticesUserInfo nUser = new NoticesUserInfo();
						nUser.setNoticeid(noticeInfo.getNoticeid());
						nUser.setReadstate(0);
						nUser.setUserid(suer.getStudentid());
						cuserService.addObject(nUser);
					}

				}
				return SUCCESS;
			}
		} else if (givingtype == 3) {
			try {
				String url = uploadImg(exslfile, exslfileFileName);
				InputStream is2 = new FileInputStream(url);
				ExslImport excelReader = new ExslImport();
				Map<Integer, String> maps = excelReader.readExcelContent(is2);
				int count = 0;
				for (int i = 0; i < maps.size(); i++) {
					suser = suserService.getUserByPhone(maps.get(i));
					if (suser != null) {
						count++;
					}
				}
				if (count * pushnum > coupon.getRest_count()) {
					return ERROR;
				} else {
					for (int i = 1; i < maps.size(); i++) {
						suser = suserService.getUserByPhone(maps.get(i));
						if (suser != null) {
							for (int j = 0; j < pushnum; j++) {
								CouponRecord couponrecord = new CouponRecord();
								couponrecord.setCouponid(couponid);
								couponrecord.setUserid(suser.getStudentid());
								couponrecord.setGettime(new Date());
								couponrecord.setValue(coupon.getValue());
								couponrecord.setOwnertype(coupon.getOwnertype());
								couponrecord.setState(0);
								couponrecord.setOwnerid(coupon.getOwnerid());
								couponrecord.setCoupontype(coupon.getCoupontype());
								couponrecord.setEnd_time(coupon.getEnd_time());
								couponrecord.setUsetime(null);
								couponService.addCouponRecord(couponrecord);
								coupon.setRest_count(coupon.getRest_count() - 1);
								couponService.updateCouponInfo(coupon);
							}
							String message = "您收到" + pushnum + "张小巴券哦,请注意查收";

							if (pushtype == 1) {// 短信
								CommonUtils.sendSms(suser.getPhone(), message);
							} else {// 推送通知
								UserPushInfo userPushInfo = cuserService.getUserPushInfo(suser.getStudentid(), 2);
								if (userPushInfo != null && userPushInfo.getDevicetoken() != null) {
									if (userPushInfo.getType() == DeviceType.ANDROID && !CommonUtils.isEmptyString(userPushInfo.getJpushid())) {
										PushtoSingle push = new PushtoSingle();
										push.pushsingle(userPushInfo.getJpushid(), 2, "{\"message\":\"" + message + "\",\"type\":\"4\"}");
									} else if (userPushInfo.getType() == DeviceType.IOS && !CommonUtils.isEmptyString(userPushInfo.getDevicetoken())) {
										ApplePushUtil.sendpush(userPushInfo.getDevicetoken(),
												"{\"aps\":{\"alert\":\"" + message + "\",\"sound\":\"default\"},\"userid\":" + suser.getStudentid() + "}", 1, 2);
									}
								}
							}

							if (!CommonUtils.isEmptyString(pushcontent)) {
								// 如果消息不为空的话,生成系统通知
								NoticesInfo noticeInfo = new NoticesInfo();
								noticeInfo.setAddtime(new Date());
								noticeInfo.setType(2);
								noticeInfo.setContent(pushcontent);
								noticeInfo.setCategory("小巴券领取");
								cuserService.addObject(noticeInfo);

								NoticesUserInfo nUser = new NoticesUserInfo();
								nUser.setNoticeid(noticeInfo.getNoticeid());
								nUser.setReadstate(0);
								nUser.setUserid(suser.getStudentid());
								cuserService.addObject(nUser);
							}

						}
					}
					return SUCCESS;
				}
			} catch (Exception e) {
				return "ERROR1";
			}
		} else if (givingtype == 4) {
			List<CoachStudentInfo> coachstudentlist = driveSchoolService.getCoachStudentInfoByCoachid(searchcoachid);
			if (coachstudentlist.size() > 0) {
				if (coachstudentlist.size() * pushnum <= coupon.getRest_count()) {
					for (int i = 0; i < coachstudentlist.size(); i++) {
						SuserInfo uuser = suserService.getUserById(String.valueOf(coachstudentlist.get(i).getStudentid()));
						if (uuser != null) {
							for (int j = 0; j < pushnum; j++) {
								CouponRecord couponrecord = new CouponRecord();
								couponrecord.setCouponid(couponid);
								couponrecord.setUserid(uuser.getStudentid());
								couponrecord.setGettime(new Date());
								couponrecord.setValue(coupon.getValue());
								couponrecord.setOwnertype(coupon.getOwnertype());
								couponrecord.setState(0);
								couponrecord.setOwnerid(coupon.getOwnerid());
								couponrecord.setCoupontype(coupon.getCoupontype());
								couponrecord.setEnd_time(coupon.getEnd_time());
								couponrecord.setUsetime(null);
								couponService.addCouponRecord(couponrecord);
								coupon.setRest_count(coupon.getRest_count() - 1);
								couponService.updateCouponInfo(coupon);
							}
							String message = "您收到" + pushnum + "张小巴券哦,请注意查收";

							if (pushtype == 1) {// 短信
								CommonUtils.sendSms(uuser.getPhone(), message);
							} else {// 推送通知
								UserPushInfo userPushInfo = cuserService.getUserPushInfo(uuser.getStudentid(), 2);
								if (userPushInfo != null && userPushInfo.getDevicetoken() != null) {
									if (userPushInfo.getType() == DeviceType.ANDROID && !CommonUtils.isEmptyString(userPushInfo.getJpushid())) {
										PushtoSingle push = new PushtoSingle();
										push.pushsingle(userPushInfo.getJpushid(), 2, "{\"message\":\"" + message + "\",\"type\":\"4\"}");
									} else if (userPushInfo.getType() == DeviceType.IOS && !CommonUtils.isEmptyString(userPushInfo.getDevicetoken())) {
										ApplePushUtil.sendpush(userPushInfo.getDevicetoken(),
												"{\"aps\":{\"alert\":\"" + message + "\",\"sound\":\"default\"},\"userid\":" + uuser.getStudentid() + "}", 1, 2);
									}
								}
							}

							if (!CommonUtils.isEmptyString(pushcontent)) {
								// 如果消息不为空的话,生成系统通知
								NoticesInfo noticeInfo = new NoticesInfo();
								noticeInfo.setAddtime(new Date());
								noticeInfo.setType(2);
								noticeInfo.setContent(pushcontent);
								noticeInfo.setCategory("小巴券领取");
								cuserService.addObject(noticeInfo);

								NoticesUserInfo nUser = new NoticesUserInfo();
								nUser.setNoticeid(noticeInfo.getNoticeid());
								nUser.setReadstate(0);
								nUser.setUserid(uuser.getStudentid());
								cuserService.addObject(nUser);
							}

						}
					}
					return SUCCESS;
				} else {
					return ERROR;
				}
			} else {
				return "ERROR1";
			}
		} else {
			SuserInfo userinfo = suserService.getUserById(searchsuserid);
			if (userinfo == null) {
				return "ERROR1";
			}
			if (coupon.getRest_count() < pushnum) {
				return ERROR;
			} else {
				for (int i = 0; i < pushnum; i++) {
					CouponRecord couponrecord = new CouponRecord();
					couponrecord.setCouponid(couponid);
					couponrecord.setUserid(userinfo.getStudentid());
					couponrecord.setGettime(new Date());
					couponrecord.setValue(coupon.getValue());
					couponrecord.setOwnertype(coupon.getOwnertype());
					couponrecord.setState(0);
					couponrecord.setOwnerid(coupon.getOwnerid());
					couponrecord.setCoupontype(coupon.getCoupontype());
					couponrecord.setEnd_time(coupon.getEnd_time());
					couponService.addCouponRecord(couponrecord);
					couponrecord.setUsetime(null);
					coupon.setRest_count(coupon.getRest_count() - 1);
					couponService.updateCouponInfo(coupon);
				}
				String message = "您收到" + pushnum + "张小巴券哦,请注意查收";

				if (pushtype == 1) {// 短信
					CommonUtils.sendSms(userinfo.getPhone(), message);
				} else {// 推送通知
					UserPushInfo userPushInfo = cuserService.getUserPushInfo(userinfo.getStudentid(), 2);
					if (userPushInfo != null && userPushInfo.getDevicetoken() != null) {
						if (userPushInfo.getType() ==DeviceType.ANDROID && !CommonUtils.isEmptyString(userPushInfo.getJpushid())) {
							PushtoSingle push = new PushtoSingle();
							push.pushsingle(userPushInfo.getJpushid(), 2, "{\"message\":\"" + message + "\",\"type\":\"4\"}");
						} else if (userPushInfo.getType() == DeviceType.IOS && !CommonUtils.isEmptyString(userPushInfo.getDevicetoken())) {
							ApplePushUtil.sendpush(userPushInfo.getDevicetoken(), "{\"aps\":{\"alert\":\"" + message + "\",\"sound\":\"default\"},\"userid\":" + userinfo.getStudentid() + "}", 1, 2);
						}
					}
				}

				if (!CommonUtils.isEmptyString(pushcontent)) {
					// 如果消息不为空的话,生成系统通知
					NoticesInfo noticeInfo = new NoticesInfo();
					noticeInfo.setAddtime(new Date());
					noticeInfo.setType(2);
					noticeInfo.setContent(pushcontent);
					noticeInfo.setCategory("小巴券领取");
					cuserService.addObject(noticeInfo);

					NoticesUserInfo nUser = new NoticesUserInfo();
					nUser.setNoticeid(noticeInfo.getNoticeid());
					nUser.setReadstate(0);
					nUser.setUserid(userinfo.getStudentid());
					cuserService.addObject(nUser);
				}

				return SUCCESS;
			}
		}
	}
	
	//导出小巴券发放记录数据
	@Action(value = "/couponrecorddataExport")
	public void couponrecorddataExport() {
		couponrecordlist = couponService.getCouponRecordList(starttime,endtime);
		List<CouponRecordForExcel> excellist = new ArrayList<CouponRecordForExcel>();
		String [] data = couponrecorddate.split(",");
		for (CouponRecord couponrecord : couponrecordlist) {
			CouponRecordForExcel couponrecordexcel = new CouponRecordForExcel();
			for(int i =0;i<data.length;i++){
				couponrecordexcel.setId(couponrecord.getOrderid());
				if(data[i].equals("0")){
					SuserInfo suser = suserService.getUserById( (couponrecord.getUserid()).toString() );
					couponrecordexcel.setUsername(suser.getRealname());
				}else if(data[i].equals("1")){
					SuserInfo suser = suserService.getUserById( (couponrecord.getUserid()).toString() );
					couponrecord.setUserphone(suser.getPhone());
					couponrecordexcel.setPhone(couponrecord.getUserphone());
				}else if(data[i].equals("2")){
					couponrecordexcel.setValue(couponrecord.getValue());
				}else if(data[i].equals("3")){
					String owner = "";
					if(couponrecord.getOwnertype() ==1 ){
						DriveSchoolInfo driveSchool = cuserService.getDriveSchoolInfoByid(couponrecord.getOwnerid());
						owner = "驾校发行："+ driveSchool.getName();
					}
					if(couponrecord.getOwnertype() == 2){
						CuserInfo cuser = cuserService.getCoachByid(couponrecord.getOwnerid());
						owner = "教练发行：" + cuser.getRealname();
						
					}
					couponrecordexcel.setOwner(owner);
				}else if(data[i].equals("4")){
					couponrecordexcel.setGettime(couponrecord.getGettime());
				}else if(data[i].equals("5")){
					couponrecordexcel.setState(couponrecord.getState());
				}else if(data[i].equals("6")){
					couponrecordexcel.setUsetime(couponrecord.getUsetime());
				}else if(data[i].equals("7")){
					couponrecordexcel.setEnd_time(couponrecord.getEnd_time());
				}
			}
			excellist.add(couponrecordexcel);
		}
		String[] title = {"主键ID","用户名","手机号","面值","发券人","发放时间","使用状态(0:未使用1:已使用  2:已作废)","使用时间","使用截止日期" };
		String filename = CommonUtils.exportExcel("couponrecorddataExport", title, excellist,data);
		filename = CommonUtils.properties.getProperty("uploadFilePath") + filename;
		HttpServletResponse response = ServletActionContext.getResponse();
		CommonUtils.downloadExcel(filename, "小巴券发放记录表", response);
	}
	
	

	public SuserInfo getSuser() {
		return suser;
	}

	public void setSuser(SuserInfo suser) {
		this.suser = suser;
	}

	public List<CouponInfo> getCouponlist() {
		return couponlist;
	}

	public void setCouponlist(List<CouponInfo> couponlist) {
		this.couponlist = couponlist;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public Integer getCoupontype() {
		return coupontype;
	}

	public void setCoupontype(Integer coupontype) {
		this.coupontype = coupontype;
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

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public Integer getValuetype() {
		return valuetype;
	}

	public void setValuetype(Integer valuetype) {
		this.valuetype = valuetype;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getAddvalue() {
		return addvalue;
	}

	public void setAddvalue(int addvalue) {
		this.addvalue = addvalue;
	}

	public Date getAddendtime() {
		return addendtime;
	}

	public void setAddendtime(Date addendtime) {
		this.addendtime = addendtime;
	}

	public int getPub_count() {
		return pub_count;
	}

	public void setPub_count(int pub_count) {
		this.pub_count = pub_count;
	}

	public int getRest_count() {
		return rest_count;
	}

	public void setRest_count(int rest_count) {
		this.rest_count = rest_count;
	}

	public int getAddcoupontype() {
		return addcoupontype;
	}

	public void setAddcoupontype(int addcoupontype) {
		this.addcoupontype = addcoupontype;
	}

	public int getCouponid() {
		return couponid;
	}

	public void setCouponid(int couponid) {
		this.couponid = couponid;
	}

	public String getCuserkeyword() {
		return cuserkeyword;
	}

	public void setCuserkeyword(String cuserkeyword) {
		this.cuserkeyword = cuserkeyword;
	}

	public int getGivingtype() {
		return givingtype;
	}

	public void setGivingtype(int givingtype) {
		this.givingtype = givingtype;
	}

	public int getPushtype() {
		return pushtype;
	}

	public void setPushtype(int pushtype) {
		this.pushtype = pushtype;
	}

	public String getSearchsuserid() {
		return searchsuserid;
	}

	public void setSearchsuserid(String searchsuserid) {
		this.searchsuserid = searchsuserid;
	}

	public String getPushcontent() {
		return pushcontent;
	}

	public void setPushcontent(String pushcontent) {
		this.pushcontent = pushcontent;
	}

	public ISUserService getSuserService() {
		return suserService;
	}

	public void setSuserService(ISUserService suserService) {
		this.suserService = suserService;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<CouponRecord> getCouponrecordlist() {
		return couponrecordlist;
	}

	public void setCouponrecordlist(List<CouponRecord> couponrecordlist) {
		this.couponrecordlist = couponrecordlist;
	}
	

	public List<CouponCoach> getCouponcoachlist() {
		return couponcoachlist;
	}

	public void setCouponcoachlist(List<CouponCoach> couponcoachlist) {
		this.couponcoachlist = couponcoachlist;
	}

	public int getAddownertype() {
		return addownertype;
	}

	public void setAddownertype(int addownertype) {
		this.addownertype = addownertype;
	}

	public int getAddownerid() {
		return addownerid;
	}

	public void setAddownerid(int addownerid) {
		this.addownerid = addownerid;
	}
	

	public int getSchoolownerid() {
		return schoolownerid;
	}

	public void setSchoolownerid(int schoolownerid) {
		this.schoolownerid = schoolownerid;
	}

	public String getSchoolkeyword() {
		return schoolkeyword;
	}

	public void setSchoolkeyword(String schoolkeyword) {
		this.schoolkeyword = schoolkeyword;
	}

	public Integer getOwnertype() {
		return ownertype;
	}

	public void setOwnertype(Integer ownertype) {
		this.ownertype = ownertype;
	}

	public String getOwnerkey() {
		return ownerkey;
	}

	public void setOwnerkey(String ownerkey) {
		this.ownerkey = ownerkey;
	}

	public String getExslfileFileName() {
		return exslfileFileName;
	}

	public void setExslfileFileName(String exslfileFileName) {
		this.exslfileFileName = exslfileFileName;
	}

	public File getExslfile() {
		return exslfile;
	}

	public void setExslfile(File exslfile) {
		this.exslfile = exslfile;
	}

	public int getPushnum() {
		return pushnum;
	}

	public void setPushnum(int pushnum) {
		this.pushnum = pushnum;
	}

	public int getSearchschoolid() {
		return searchschoolid;
	}

	public void setSearchschoolid(int searchschoolid) {
		this.searchschoolid = searchschoolid;
	}

	public int getSearchcoachid() {
		return searchcoachid;
	}

	public void setSearchcoachid(int searchcoachid) {
		this.searchcoachid = searchcoachid;
	}

	public int getErrortype() {
		return errortype;
	}

	public void setErrortype(int errortype) {
		this.errortype = errortype;
	}

	public String getErrormessage() {
		return errormessage;
	}

	public void setErrormessage(String errormessage) {
		this.errormessage = errormessage;
	}

	public int getRecordid() {
		return recordid;
	}

	public void setRecordid(int recordid) {
		this.recordid = recordid;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getCouponrecorddate() {
		return couponrecorddate;
	}

	public void setCouponrecorddate(String couponrecorddate) {
		this.couponrecorddate = couponrecorddate;
	}
	
	
}
