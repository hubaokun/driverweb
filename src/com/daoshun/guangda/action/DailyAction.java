package com.daoshun.guangda.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.daoshun.common.CommonUtils;
import com.daoshun.guangda.NetData.CoachAccountDaily;
import com.daoshun.guangda.NetData.CoachApplyDailyData;
import com.daoshun.guangda.NetData.CoachDailyData;
import com.daoshun.guangda.NetData.CoinReportMontly;
import com.daoshun.guangda.NetData.CouponReportMontly;
import com.daoshun.guangda.NetData.SchoolBillDaily;
import com.daoshun.guangda.NetData.SchoolDailyData;
import com.daoshun.guangda.NetData.AccountReportDaily;
import com.daoshun.guangda.NetData.StudentAccountDaily;
import com.daoshun.guangda.NetData.StudentApplyDailyData;
import com.daoshun.guangda.NetData.StudentCoinDetail;
import com.daoshun.guangda.NetData.StudentCouponDetail;
import com.daoshun.guangda.NetData.XiaoBaDaily;
import com.daoshun.guangda.pojo.DriveSchoolInfo;
import com.daoshun.guangda.service.ICUserService;
import com.daoshun.guangda.service.IDailyService;

@ParentPackage("default")
@Controller
@Scope("prototype")
public class DailyAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4179287330191793206L;

	@Resource
	private IDailyService dailyService;

	@Resource
	private ICUserService cuserService;

	private Integer pageIndex = 1;

	private int total;

	private int indexnum = 0;

	private int pageCount;

	// 学员-当日注册人数
	private String susercount_total;

	// 学员-当日累计注册人
	private String susercount_regirect;

	// 学员-当日活跃人数
	private String susercount_happy;

	// 当日累计教练注册人数
	private String cusercount;

	// 用戶-当日注册人数
	private String usertotal_today_regirect;

	// 用户-当日累计注册人
	private String usertotal_regirect;

	// 用户-当日活跃人数
	private String usertotal_happy;

	// 教练-当日注册人数
	private String usercocal_today_regirect;

	// 教练-当日累计注册人
	private String usercocal_regirect;

	// 教练-当日活跃人数
	private String usercocal_happy;

	// 教练-当日生成订单数
	private String creart_order_today;

	// 教练-当日完成订单数
	private String finish_order_today;

	// 教练-累计未完成订单数
	private String total_order;

	// 学员-当日生成订单数
	private String str_creart_order_today;

	// 学员-当日完成订单数
	private String str_finish_order_today;

	// 学员-累计未完成订单数
	private String str_total_order;

	private String addtime;

	private String starttime;
	
	private String endtime;

	private String schoolname;
	
	private String coachid;
	
	private List<SchoolDailyData> schooldailylist;

	private List<CoachDailyData> coachdailylist;

	private List<StudentApplyDailyData> studentapplylist;

	private List<CoachApplyDailyData> coachapplylist;

	private StudentAccountDaily studentaccountdaily;

	private CoachAccountDaily coachaccountdaily;
	
	private AccountReportDaily accountreportdaliy;
	
	private List<CouponReportMontly> couponreportmontly;
	
	private List<StudentCouponDetail> studentlist;
	
	private List<CoinReportMontly> coinreportmontly;
	
	private List<StudentCoinDetail> studentcoinlist;
	
	private List<StudentCouponDetail> studentcoupondetail;
	
	private List<StudentCoinDetail> studentcoindetail;

	private List<SchoolBillDaily> schoolbilldaily;

	private List<XiaoBaDaily> xiaobadaily;

	private List<DriveSchoolInfo> driveSchoollist;
	
	private List<Object> cuserinfolist;//驾校报表list

	/**
	 * 系统日报
	 * 
	 * @return
	 */
	@Action(value = "/systemdaily", results = { @Result(name = SUCCESS, location = "/systemdaily.jsp") })
	public String systemdaily() {
		if (CommonUtils.isEmptyString(addtime) || addtime == null) {
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			addtime = formatter.format(new Date());
		}
		List<Object> object = dailyService.getSystemdatal(addtime);
		for (Object object2 : object) {
			Object[] obj = (Object[]) object2;
			if ((Integer) obj[0] == 1) {
				if (obj[1] != null) {
					usertotal_today_regirect = obj[1].toString();
				}
				if (obj[2] == null) {
					usertotal_regirect = "0";
				} else {
					usertotal_regirect = obj[2].toString();
				}
				if (obj[3] == null) {
					usertotal_happy = "0";
				} else {
					usertotal_happy = obj[3].toString();
				}
			} else if ((Integer) obj[0] == 2) {
				if (obj[1] == null) {
					susercount_total = "0";
				} else {
					susercount_total = obj[1].toString();
				}
				if (obj[2] == null) {
					susercount_regirect = "0";
				} else {
					susercount_regirect = obj[2].toString();
				}
				if (obj[3] == null) {
					susercount_happy = "0";
				} else {
					susercount_happy = obj[3].toString();
				}
			} else if ((Integer) obj[0] == 3) {
				if (obj[1] == null) {
					usercocal_today_regirect = "0";
				} else {
					usercocal_today_regirect = obj[1].toString();
				}
				if (obj[2] == null) {
					usercocal_regirect = "0";
				} else {
					usercocal_regirect = obj[2].toString();
				}
				if (obj[3] == null) {
					usercocal_happy = "0";
				} else {
					usercocal_happy = obj[3].toString();
				}
			} else if ((Integer) obj[0] == 4) {
				if (obj[1] == null) {
					creart_order_today = "0";
				} else {
					creart_order_today = obj[1].toString();
				}
				if (obj[2] == null) {
					finish_order_today = "0";
				} else {
					finish_order_today = obj[2].toString();
				}
				if (obj[3] == null) {
					total_order = "0";
				} else {
					total_order = obj[3].toString();
				}
			} else if ((Integer) obj[0] == 5) {
				if (obj[1] == null) {
					str_creart_order_today = "0";
				} else {
					str_creart_order_today = obj[1].toString();
				}
				if (obj[2] == null) {
					str_finish_order_today = "0";
				} else {
					str_finish_order_today = obj[2].toString();
				}
				if (obj[3] == null) {
					str_total_order = "0";
				} else {
					str_total_order = obj[3].toString();
				}
			}
		}
		return SUCCESS;
	}

	@SuppressWarnings("deprecation")
	@Action(value = "/systemNoticeExport")
	public void systemNoticeExport() throws IOException {
		// 以下开始输出到EXCEL
		String newfilename = "";
		// 定义输出流，以便打开保存对话框begin
		newfilename += CommonUtils.getTimeFormat(new Date(), "yyyyMMddhhmmssSSS") + "_" + (int) (Math.random() * 100) + ".xls";
		String filename = CommonUtils.properties.get("uploadFilePath") + newfilename;
		File file = new File(filename);
		if (!file.exists()) {
			file.createNewFile();
		}
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("系统日报表");
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow((Integer) 0);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue(addtime + "系统日报");
		cell.setCellStyle(style);
		row = sheet.createRow((Integer) 1);
		cell = row.createCell((short) 0);
		cell.setCellValue("序号");
		cell.setCellStyle(style);
		cell = row.createCell((short) 1);
		cell.setCellValue("项目");
		cell.setCellStyle(style);
		cell = row.createCell((short) 2);
		cell.setCellValue("当日注册人数");
		cell.setCellStyle(style);
		cell = row.createCell((short) 3);
		cell.setCellValue("当日累计注册人数");
		cell.setCellStyle(style);
		cell = row.createCell((short) 4);
		cell.setCellValue("当日活跃人数");
		cell.setCellStyle(style);
		List<Object> object = dailyService.getSystemdatal(addtime);
		for (Object object2 : object) {
			Object[] obj = (Object[]) object2;
			if ((Integer) obj[0] == 1) {
				if (obj[1] != null) {
					usertotal_today_regirect = obj[1].toString();
				}
				if (obj[2] == null) {
					usertotal_regirect = "0";
				} else {
					usertotal_regirect = obj[2].toString();
				}
				if (obj[3] == null) {
					usertotal_happy = "0";
				} else {
					usertotal_happy = obj[3].toString();
				}
			} else if ((Integer) obj[0] == 2) {
				if (obj[1] == null) {
					susercount_total = "0";
				} else {
					susercount_total = obj[1].toString();
				}
				if (obj[2] == null) {
					susercount_regirect = "0";
				} else {
					susercount_regirect = obj[2].toString();
				}
				if (obj[3] == null) {
					susercount_happy = "0";
				} else {
					susercount_happy = obj[3].toString();
				}
			} else if ((Integer) obj[0] == 3) {
				if (obj[1] == null) {
					usercocal_today_regirect = "0";
				} else {
					usercocal_today_regirect = obj[1].toString();
				}
				if (obj[2] == null) {
					usercocal_regirect = "0";
				} else {
					usercocal_regirect = obj[2].toString();
				}
				if (obj[3] == null) {
					usercocal_happy = "0";
				} else {
					usercocal_happy = obj[3].toString();
				}
			} else if ((Integer) obj[0] == 4) {
				if (obj[1] == null) {
					creart_order_today = "0";
				} else {
					creart_order_today = obj[1].toString();
				}
				if (obj[2] == null) {
					finish_order_today = "0";
				} else {
					finish_order_today = obj[2].toString();
				}
				if (obj[3] == null) {
					total_order = "0";
				} else {
					total_order = obj[3].toString();
				}
			} else if ((Integer) obj[0] == 5) {
				if (obj[1] == null) {
					str_creart_order_today = "0";
				} else {
					str_creart_order_today = obj[1].toString();
				}
				if (obj[2] == null) {
					str_finish_order_today = "0";
				} else {
					str_finish_order_today = obj[2].toString();
				}
				if (obj[3] == null) {
					str_total_order = "0";
				} else {
					str_total_order = obj[3].toString();
				}
			}
		}
		row = sheet.createRow((Integer) 2);
		row.createCell((short) 0).setCellValue(1);
		row.createCell((short) 1).setCellValue("用户总数");
		row.createCell((short) 2).setCellValue(usertotal_today_regirect);
		row.createCell((short) 3).setCellValue(usertotal_regirect);
		row.createCell((short) 4).setCellValue(usertotal_happy);
		row = sheet.createRow((Integer) 3);
		row.createCell((short) 0).setCellValue(2);
		row.createCell((short) 1).setCellValue("教练端");
		row.createCell((short) 2).setCellValue(usercocal_today_regirect);
		row.createCell((short) 3).setCellValue(usercocal_regirect);
		row.createCell((short) 4).setCellValue(usercocal_happy);
		row = sheet.createRow((Integer) 4);
		row.createCell((short) 0).setCellValue(3);
		row.createCell((short) 1).setCellValue("学员端");
		row.createCell((short) 2).setCellValue(susercount_total);
		row.createCell((short) 3).setCellValue(susercount_regirect);
		row.createCell((short) 4).setCellValue(susercount_happy);
		row = sheet.createRow((Integer) 5);
		cell = row.createCell((short) 0);
		cell.setCellValue("序号");
		cell.setCellStyle(style);
		cell = row.createCell((short) 1);
		cell.setCellValue("项目");
		cell.setCellStyle(style);
		cell = row.createCell((short) 2);
		cell.setCellValue("当日生成订单数");
		cell.setCellStyle(style);
		cell = row.createCell((short) 3);
		cell.setCellValue("当日完成订单数");
		cell.setCellStyle(style);
		cell = row.createCell((short) 4);
		cell.setCellValue("累计未完成订单数");
		cell.setCellStyle(style);
		row = sheet.createRow((Integer) 6);
		row.createCell((short) 0).setCellValue(1);
		row.createCell((short) 1).setCellValue("教练端");
		row.createCell((short) 2).setCellValue(creart_order_today);
		row.createCell((short) 3).setCellValue(finish_order_today);
		row.createCell((short) 4).setCellValue(total_order);
		row = sheet.createRow((Integer) 7);
		row.createCell((short) 0).setCellValue(2);
		row.createCell((short) 1).setCellValue("学员端");
		row.createCell((short) 2).setCellValue(str_creart_order_today);
		row.createCell((short) 3).setCellValue(finish_order_today);
		row.createCell((short) 4).setCellValue(total_order);
		row = sheet.createRow((Integer) 8);
		cell = row.createCell((short) 0);
		cell.setCellValue("序号");
		cell.setCellStyle(style);
		cell = row.createCell((short) 1);
		cell.setCellValue("项目");
		cell.setCellStyle(style);
		cell = row.createCell((short) 2);
		cell.setCellValue("当日预约学时数");
		cell.setCellStyle(style);
		cell = row.createCell((short) 3);
		cell.setCellValue("当日完成学时数");
		cell.setCellStyle(style);
		cell = row.createCell((short) 4);
		cell.setCellValue("累计未完成学时数");
		cell.setCellStyle(style);
		row = sheet.createRow((Integer) 9);
		row.createCell((short) 0).setCellValue(1);
		row.createCell((short) 1).setCellValue("教练端");
		row.createCell((short) 2).setCellValue(str_creart_order_today);
		row.createCell((short) 3).setCellValue(str_finish_order_today);
		row.createCell((short) 4).setCellValue(str_total_order);
		row = sheet.createRow((Integer) 10);
		row.createCell((short) 0).setCellValue(2);
		row.createCell((short) 1).setCellValue("学员端");
		row.createCell((short) 2).setCellValue(str_creart_order_today);
		row.createCell((short) 3).setCellValue(str_finish_order_today);
		row.createCell((short) 4).setCellValue(str_total_order);
		try {
			FileOutputStream fout = new FileOutputStream(file);
			wb.write(fout);
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		filename = CommonUtils.properties.getProperty("uploadFilePath") + newfilename;
		HttpServletResponse response = ServletActionContext.getResponse();
		CommonUtils.downloadExcel(filename, "小巴订单信息表", response);
	}

	/**
	 * 教校日报
	 * 
	 * @return
	 */
	@Action(value = "/schooldaily", results = { @Result(name = SUCCESS, location = "/schooldaily.jsp") })
	public String schooldaily() {
		if (CommonUtils.isEmptyString(addtime) || addtime == null) {
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			addtime = formatter.format(new Date());
		}
		List<Object> obj = dailyService.getSchoolDaily(addtime);
		schooldailylist = new ArrayList<SchoolDailyData>();
		total = obj.size();
		int pagesize = 10;
		pageCount = (total + pagesize - 1) / pagesize;
		if (obj != null && obj.size() > 0) {
			for (Object object : obj) {
				indexnum++;
				SchoolDailyData schooldaily = new SchoolDailyData();
				Object[] array = (Object[]) object;
				if (array[1] != null) {
					schooldaily.setSchoolname(array[1].toString());
				}
				if (array[2] != null) {
					schooldaily.setCoachname(array[2].toString());
				}
				if (array[3] != null) {
					schooldaily.setUnfinishorder((Integer) array[3]);
				} else {
					schooldaily.setUnfinishorder(0);
				}
				if (array[4] != null) {
					schooldaily.setCreateorder((Integer) array[4]);
				} else {
					schooldaily.setCreateorder(0);
				}
				if (array[5] != null) {
					schooldaily.setFinishorder((Integer) array[5]);
				} else {
					schooldaily.setFinishorder(0);
				}
				if (array[6] != null) {
					schooldaily.setAllunfinishorder((Integer) array[6]);
				} else {
					schooldaily.setAllunfinishorder(0);
				}
				if (array[7] != null) {
					schooldaily.setOrderhour((Integer) array[7]);
				} else {
					schooldaily.setOrderhour(0);
				}
				if (array[8] != null) {
					schooldaily.setDayorderhour((Integer) array[8]);
				} else {
					schooldaily.setDayorderhour(0);
				}
				if (array[9] != null) {
					schooldaily.setFinishhour((Integer) array[9]);
				} else {
					schooldaily.setFinishhour(0);
				}
				if (array[10] != null) {
					schooldaily.setAllunfinishhour((Integer) array[10]);
				} else {
					schooldaily.setAllunfinishhour(0);
				}
				if (array[11] != null) {
					schooldaily.setMoney((BigDecimal) array[11]);
				} else {
					schooldaily.setMoney(new BigDecimal(0));
				}
				if ((pageIndex * pagesize - 9) <= indexnum && indexnum <= (pageIndex * pagesize)) {
					schooldailylist.add(schooldaily);
				}
			}
		}
		return SUCCESS;
	}

	/**
	 * 教校日报导出
	 * 
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	@Action(value = "/schoolNoticeExport")
	public void schoolNoticeExport() throws IOException {
		// 以下开始输出到EXCEL
		String newfilename = "";
		// 定义输出流，以便打开保存对话框begin
		newfilename += CommonUtils.getTimeFormat(new Date(), "yyyyMMddhhmmssSSS") + "_" + (Math.random() * 100) + ".xls";
		String filename = CommonUtils.properties.get("uploadFilePath") + newfilename;
		File file = new File(filename);
		if (!file.exists()) {
			file.createNewFile();
		}
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("教校日报表");
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		int index = 0;
		HSSFRow row = sheet.createRow((Integer) index++);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue(addtime + "教校日报");
		cell.setCellStyle(style);
		row = sheet.createRow((Integer) index++);
		cell = row.createCell((short) 0);
		cell.setCellValue("序号");
		cell.setCellStyle(style);
		cell = row.createCell((short) 1);
		cell.setCellValue("教校");
		cell.setCellStyle(style);
		cell = row.createCell((short) 2);
		cell.setCellValue("教练");
		cell.setCellStyle(style);
		cell = row.createCell((short) 3);
		cell.setCellValue("期初未完成订单数");
		cell.setCellStyle(style);
		cell = row.createCell((short) 4);
		cell.setCellValue("当日生成订单数");
		cell.setCellStyle(style);
		cell = row.createCell((short) 5);
		cell.setCellValue("当日完成订单数");
		cell.setCellStyle(style);
		cell = row.createCell((short) 6);
		cell.setCellValue("累计未完成订单数");
		cell.setCellStyle(style);
		cell = row.createCell((short) 7);
		cell.setCellValue("期初预约学时数");
		cell.setCellStyle(style);
		cell = row.createCell((short) 8);
		cell.setCellValue("当日预约学时数");
		cell.setCellStyle(style);
		cell = row.createCell((short) 9);
		cell.setCellValue("当日完成学时数");
		cell.setCellStyle(style);
		cell = row.createCell((short) 10);
		cell.setCellValue("累计未完成学时数");
		cell.setCellStyle(style);
		cell = row.createCell((short) 11);
		cell.setCellValue("当日教练账户余额");
		cell.setCellStyle(style);
		List<Object> obj = dailyService.getSchoolDaily(addtime);
		schooldailylist = new ArrayList<SchoolDailyData>();
		if (obj != null && obj.size() > 0) {
			for (Object object : obj) {
				SchoolDailyData schooldaily = new SchoolDailyData();
				Object[] array = (Object[]) object;
				if (array[1] != null) {
					schooldaily.setSchoolname(array[1].toString());
				}
				if (array[2] != null) {
					schooldaily.setCoachname(array[2].toString());
				}
				if (array[3] != null) {
					schooldaily.setUnfinishorder((Integer) array[3]);
				} else {
					schooldaily.setUnfinishorder(0);
				}
				if (array[4] != null) {
					schooldaily.setCreateorder((Integer) array[4]);
				} else {
					schooldaily.setCreateorder(0);
				}
				if (array[5] != null) {
					schooldaily.setFinishorder((Integer) array[5]);
				} else {
					schooldaily.setFinishorder(0);
				}
				if (array[6] != null) {
					schooldaily.setAllunfinishorder((Integer) array[6]);
				} else {
					schooldaily.setAllunfinishorder(0);
				}
				if (array[7] != null) {
					schooldaily.setOrderhour((Integer) array[7]);
				} else {
					schooldaily.setOrderhour(0);
				}
				if (array[8] != null) {
					schooldaily.setDayorderhour((Integer) array[8]);
				} else {
					schooldaily.setDayorderhour(0);
				}
				if (array[9] != null) {
					schooldaily.setFinishhour((Integer) array[9]);
				} else {
					schooldaily.setFinishhour(0);
				}
				if (array[10] != null) {
					schooldaily.setAllunfinishhour((Integer) array[10]);
				} else {
					schooldaily.setAllunfinishhour(0);
				}
				if (array[11] != null) {
					schooldaily.setMoney(new BigDecimal((Double) array[11]));
				} else {
					schooldaily.setMoney(new BigDecimal(0));
				}
				schooldailylist.add(schooldaily);
			}
		}
		if (schooldailylist != null && schooldailylist.size() > 0) {
			for (int i = 0; i < schooldailylist.size(); i++) {
				row = sheet.createRow((Integer) index++);
				SchoolDailyData schooldaily = schooldailylist.get(i);
				row.createCell((short) 0).setCellValue(i + 1);
				if (schooldaily.getSchoolname() != null) {
					row.createCell((short) 1).setCellValue(schooldaily.getSchoolname());
				}
				if (schooldaily.getCoachname() != null) {
					row.createCell((short) 2).setCellValue(schooldaily.getCoachname());
				}
				if (schooldaily.getUnfinishorder() != null) {
					row.createCell((short) 3).setCellValue(schooldaily.getUnfinishorder());
				}
				if (schooldaily.getCreateorder() != null) {
					row.createCell((short) 4).setCellValue(schooldaily.getCreateorder());
				}
				if (schooldaily.getFinishorder() != null) {
					row.createCell((short) 5).setCellValue(schooldaily.getFinishorder());
				}
				if (schooldaily.getAllunfinishorder() != null) {
					row.createCell((short) 6).setCellValue(schooldaily.getAllunfinishorder());
				}
				if (schooldaily.getOrderhour() != null) {
					row.createCell((short) 7).setCellValue(schooldaily.getOrderhour());
				}
				if (schooldaily.getDayorderhour() != null) {
					row.createCell((short) 8).setCellValue(schooldaily.getDayorderhour());
				}
				if (schooldaily.getFinishhour() != null) {
					row.createCell((short) 9).setCellValue(schooldaily.getFinishhour());
				}
				if (schooldaily.getAllunfinishhour() != null) {
					row.createCell((short) 10).setCellValue(schooldaily.getAllunfinishhour());
				}
				if (schooldaily.getMoney() != null) {
					row.createCell((short) 11).setCellValue(schooldaily.getMoney().toString());
				}
			}
		}
		try {
			FileOutputStream fout = new FileOutputStream(file);
			wb.write(fout);
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		filename = CommonUtils.properties.getProperty("uploadFilePath") + newfilename;
		HttpServletResponse response = ServletActionContext.getResponse();
		CommonUtils.downloadExcel(filename, "教校日报表", response);
	}

	/**
	 * 小巴券月报数据导出
	 * 
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	@Action(value = "/CouponReportMontlyExport")
	public void CouponReportMontlyExport() throws IOException {
		// 以下开始输出到EXCEL
		String newfilename = "";
		// 定义输出流，以便打开保存对话框begin
		newfilename += CommonUtils.getTimeFormat(new Date(), "yyyyMMddhhmmssSSS") + "_" + (Math.random() * 100) + ".xls";
		String filename = CommonUtils.properties.get("uploadFilePath") + newfilename;
		File file = new File(filename);
		if (!file.exists()) {
			file.createNewFile();
		}
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("小巴券月报表");
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		int index = 0;
		HSSFRow row = sheet.createRow((Integer) index++);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue("小巴学时券统计报表（"+starttime +"——"+endtime+ "）");
		cell.setCellStyle(style);
		sheet.addMergedRegion(new Region(0, (short) 0, 1, (short) 10));
		index++;
		row = sheet.createRow((Integer) index++);
		cell = row.createCell((short) 0);
		cell.setCellValue("教练 /驾校");
		cell.setCellStyle(style);
		sheet.addMergedRegion(new Region(2, (short) 0, 2, (short) 6));
		cell = row.createCell((short) 7);
		cell.setCellValue("学员");
		cell.setCellStyle(style);
		sheet.addMergedRegion(new Region(2, (short) 7, 2, (short) 10));
		
		
		row = sheet.createRow((Integer) index++);
		cell = row.createCell((short) 0);
		cell.setCellValue("序号");
		cell.setCellStyle(style);
		cell = row.createCell((short) 1);
		cell.setCellValue("券类");
		cell.setCellStyle(style);
		cell = row.createCell((short) 2);
		cell.setCellValue("发放教练");
		cell.setCellStyle(style);
		cell = row.createCell((short) 3);
		cell.setCellValue("发放教练手机号");
		cell.setCellStyle(style);
		cell = row.createCell((short) 4);
		cell.setCellValue("教练所在驾校");
		cell.setCellStyle(style);
		cell = row.createCell((short) 5);
		cell.setCellValue("发放教练券(1小时/张)");
		cell.setCellStyle(style);
		cell = row.createCell((short) 6);
		cell.setCellValue("已结算教练券(1小时/张)");
		cell.setCellStyle(style);
		cell = row.createCell((short) 7);
		cell.setCellValue("学员手机号码");
		cell.setCellStyle(style);
		cell = row.createCell((short) 8);
		cell.setCellValue("学员 姓名");
		cell.setCellStyle(style);
		cell = row.createCell((short) 9);
		cell.setCellValue("获得教练券");
		cell.setCellStyle(style);
		cell = row.createCell((short) 10);
		cell.setCellValue("已使用教练券");
		cell.setCellStyle(style);
		List<Object> objm = dailyService.getCouponReportMontly(CommonUtils.getDateFormat(starttime, "yyyy-MM-dd"), CommonUtils.getDateFormat(endtime, "yyyy-MM-dd"));
		couponreportmontly = new ArrayList<CouponReportMontly>();
		if (objm != null && objm.size() > 0) {
			for (Object object : objm) {
				indexnum++;
				CouponReportMontly coupontemp = new CouponReportMontly();
				Object[] array = (Object[]) object;
				if (array[1] != null) {
					coupontemp.setCoachid(array[1].toString());
				}
				if (array[2] != null) {
					coupontemp.setCoachname(array[2].toString());
				}
				if (array[3] != null) {
					coupontemp.setCoachphone(array[3].toString());
				}
				if (array[4] != null) {
					coupontemp.setSchoolname(array[4].toString());
				}
				if (array[5] != null) {
					coupontemp.setCouponnumber((Integer) array[5]);
				} else {
					coupontemp.setCouponnumber(0);
				}
				if (array[6] != null) {
					coupontemp.setCouponpaycount((Integer) array[6]);
				} else {
					coupontemp.setCouponpaycount(0);
				}
				List<Object> objd=dailyService.getCouponReportDetail(coupontemp.getCoachid(), CommonUtils.getDateFormat(starttime, "yyyy-MM-dd"), CommonUtils.getDateFormat(endtime, "yyyy-MM-dd"));
				studentlist=new ArrayList<StudentCouponDetail>();
						if (objd != null && objd.size() > 0) {
							for (Object o1 : objd) {
								Object[] olist=(Object[])o1;
								StudentCouponDetail tempstudent=new StudentCouponDetail();
								if (olist[1] != null) {
									tempstudent.setPhone(olist[1].toString());
								}
								if (olist[2] != null) {
									tempstudent.setName(olist[2].toString());
								}
								if (olist[3] != null) {
									tempstudent.setCouponusenumber((Integer)olist[3]);
								}
								else {
									tempstudent.setCouponusenumber(0);
								}
								if (olist[4] != null) {
									tempstudent.setCouponpaynumber((Integer)olist[4]);
								}
								else {
									tempstudent.setCouponpaynumber(0);
								}
									studentlist.add(tempstudent);
							}
							coupontemp.setStudentdetaillist(studentlist);
						}
				couponreportmontly.add(coupontemp);
			}
			
		}
		if (couponreportmontly != null && couponreportmontly.size() > 0) {
			for (int i = 0; i < couponreportmontly.size(); i++) {
				row = sheet.createRow((Integer) index);
				CouponReportMontly couponmontly = couponreportmontly.get(i);
				List<StudentCouponDetail> tempstudentlist=couponmontly.getStudentdetaillist();
				int rowcount=0;
				int rowendindex=index;
				if(tempstudentlist!=null)
				{
					rowcount=couponmontly.getStudentdetaillist().size();
					rowendindex=index+rowcount-1;
				}
				cell=row.createCell((short) 0);
				cell.setCellValue(i + 1);
				cell.setCellStyle(style);
				sheet.addMergedRegion(new Region(index,(short)0,rowendindex,(short) 0));
				cell=row.createCell((short) 1);
				cell.setCellValue("学时券");
				cell.setCellStyle(style);
				sheet.addMergedRegion(new Region(index,(short)1,rowendindex,(short) 1));
				if (couponmontly.getCoachname() != null) {
					cell=row.createCell((short) 2);
					cell.setCellValue(couponmontly.getCoachname());
					cell.setCellStyle(style);
					sheet.addMergedRegion(new Region(index,(short)2,rowendindex,(short) 2));
				}
				if (couponmontly.getCoachphone() != null) {
					cell=row.createCell((short) 3);
					cell.setCellValue(couponmontly.getCoachphone());
					cell.setCellStyle(style);
					sheet.addMergedRegion(new Region(index,(short)3,rowendindex,(short) 3));
				}
				if (couponmontly.getSchoolname() != null) {
					cell=row.createCell((short) 4);
					cell.setCellValue(couponmontly.getSchoolname());
					cell.setCellStyle(style);
					sheet.addMergedRegion(new Region(index,(short)4,rowendindex,(short) 4));
				}
				if (couponmontly.getCouponnumber() != null) {
					cell=row.createCell((short) 5);
					cell.setCellValue(couponmontly.getCouponnumber());
					cell.setCellStyle(style);
					sheet.addMergedRegion(new Region(index,(short)5,rowendindex,(short) 5));
				}
				if (couponmontly.getCouponpaycount() != null) {
					cell=row.createCell((short) 6);
					cell.setCellValue(couponmontly.getCouponpaycount());
					cell.setCellStyle(style);
					sheet.addMergedRegion(new Region(index,(short)6,rowendindex,(short) 6));
				}
				if(tempstudentlist!=null && tempstudentlist.size()>0)
				{
				    for(int j=0;j<tempstudentlist.size();j++)
				    {
				    	StudentCouponDetail tempt=tempstudentlist.get(j);
				    	if(j==0)
				    	{
					    	cell=row.createCell((short) 7);
					    	cell.setCellValue(tempt.getPhone());
							cell.setCellStyle(style);
							cell=row.createCell((short) 8);
					    	cell.setCellValue(tempt.getName());
							cell.setCellStyle(style);
							cell=row.createCell((short) 9);
					    	cell.setCellValue(tempt.getCouponusenumber());
							cell.setCellStyle(style);
							cell=row.createCell((short) 10);
					    	cell.setCellValue(tempt.getCouponpaynumber());
							cell.setCellStyle(style);
							if(j==tempstudentlist.size()-1)
					    	{
					    		index++;
					    	}
				    	}
	                   
				    	else
				    	{
				    		index++;
				    		row=sheet.createRow((Integer) index);
				    		cell=row.createCell((short) 7);
					    	cell.setCellValue(tempt.getPhone());
							cell.setCellStyle(style);
							cell=row.createCell((short) 8);
					    	cell.setCellValue(tempt.getName());
							cell.setCellStyle(style);
							cell=row.createCell((short) 9);
					    	cell.setCellValue(tempt.getCouponusenumber());
							cell.setCellStyle(style);
							cell=row.createCell((short) 10);
					    	cell.setCellValue(tempt.getCouponpaynumber());
							cell.setCellStyle(style);
							if(j==tempstudentlist.size()-1)
					    	{
					    		index++;
					    	}
				    	}
				    	
				    }
				}
				else
					index++;
			}
		}
		try {
			FileOutputStream fout = new FileOutputStream(file);
			wb.write(fout);
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		filename = CommonUtils.properties.getProperty("uploadFilePath") + newfilename;
		HttpServletResponse response = ServletActionContext.getResponse();
		CommonUtils.downloadExcel(filename, "小巴券月报表", response);
	}
	/**
	 * 小巴币月报数据导出
	 * 
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	@Action(value = "/CoinReportMontlyExport")
	public void CoinReportMontlyExport() throws IOException {
		// 以下开始输出到EXCEL
		String newfilename = "";
		// 定义输出流，以便打开保存对话框begin
		newfilename += CommonUtils.getTimeFormat(new Date(), "yyyyMMddhhmmssSSS") + "_" + (Math.random() * 100) + ".xls";
		String filename = CommonUtils.properties.get("uploadFilePath") + newfilename;
		File file = new File(filename);
		if (!file.exists()) {
			file.createNewFile();
		}
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("小巴币月报表");
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		int index = 0;
		HSSFRow row = sheet.createRow((Integer) index++);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue("小巴币统计报表（"+starttime +"——"+endtime+ "）");
		cell.setCellStyle(style);
		sheet.addMergedRegion(new Region(0, (short) 0, 1, (short) 11));
		index++;
		row = sheet.createRow((Integer) index++);
		cell = row.createCell((short) 0);
		cell.setCellValue("教练 /驾校");
		cell.setCellStyle(style);
		sheet.addMergedRegion(new Region(2, (short) 0, 2, (short) 7));
		cell = row.createCell((short) 8);
		cell.setCellValue("学员");
		cell.setCellStyle(style);
		sheet.addMergedRegion(new Region(2, (short) 8, 2, (short) 11));
		
		
		row = sheet.createRow((Integer) index++);
		cell = row.createCell((short) 0);
		cell.setCellValue("序号");
		cell.setCellStyle(style);
		cell = row.createCell((short) 1);
		cell.setCellValue("券类");
		cell.setCellStyle(style);
		cell = row.createCell((short) 2);
		cell.setCellValue("发放教练/驾校");
		cell.setCellStyle(style);
		cell = row.createCell((short) 3);
		cell.setCellValue("教练所在驾校");
		cell.setCellStyle(style);
		cell = row.createCell((short) 4);
		cell.setCellValue("发放教练手机号");
		cell.setCellStyle(style);
		cell = row.createCell((short) 5);
		cell.setCellValue("小巴币面值(1元/个)");
		cell.setCellStyle(style);
		cell = row.createCell((short) 6);
		cell.setCellValue("发放小巴币(个)");
		cell.setCellStyle(style);
		cell = row.createCell((short) 7);
		cell.setCellValue("已结算小巴币(个)");
		cell.setCellStyle(style);
		cell = row.createCell((short) 8);
		cell.setCellValue("学员手机号");
		cell.setCellStyle(style);
		cell = row.createCell((short) 9);
		cell.setCellValue("学员姓名");
		cell.setCellStyle(style);
		cell = row.createCell((short) 10);
		cell.setCellValue("获取小巴币");
		cell.setCellStyle(style);
		cell = row.createCell((short) 11);
		cell.setCellValue("已使用小巴币");
		cell.setCellStyle(style);
		
		
		List<Object> objm = dailyService.getCoinReportMontly(CommonUtils.getDateFormat(starttime, "yyyy-MM-dd"), CommonUtils.getDateFormat(endtime, "yyyy-MM-dd"));
		coinreportmontly = new ArrayList<CoinReportMontly>();
		if (objm != null && objm.size() > 0) {
			for (Object object : objm) {
				indexnum++;
				CoinReportMontly cointemp = new CoinReportMontly();
				Object[] array = (Object[]) object;
				if (array[1] != null) {
					cointemp.setCoachid(array[1].toString());
				}
				if (array[2] != null) {
					cointemp.setCoachname(array[2].toString());
				}
				if (array[3] != null) {
					cointemp.setSchoolname(array[3].toString());
				}
				if (array[4] != null) {
					cointemp.setCoachphone(array[4].toString());
				}
				if (array[5] != null) {
					cointemp.setCoinnumber(new BigDecimal(array[5].toString()));
				} else {
					cointemp.setCoinnumber(new BigDecimal(0));
				}
				if (array[6] != null) {
					cointemp.setCoinnpaycount(new BigDecimal(array[6].toString()));
				} else {
					cointemp.setCoinnpaycount(new BigDecimal(0));
				}
				List<Object> objd=dailyService.getCoinReportDetail(cointemp.getCoachid(), CommonUtils.getDateFormat(starttime, "yyyy-MM-dd"), CommonUtils.getDateFormat(endtime, "yyyy-MM-dd"));
				studentcoinlist=new ArrayList<StudentCoinDetail>();
						if (objd != null && objd.size() > 0) {
							for (Object o1 : objd) {
								Object[] olist=(Object[])o1;
								StudentCoinDetail tempstudent=new StudentCoinDetail();
								if (olist[1] != null) {
									tempstudent.setPhone(olist[1].toString());
								}
								if (olist[2] != null) {
									tempstudent.setName(olist[2].toString());
								}
								if (olist[3] != null) {
									tempstudent.setCoinusenumber(new BigDecimal(olist[3].toString()));
								}
								else {
									tempstudent.setCoinusenumber(new BigDecimal(0));
								}
								if (olist[4] != null) {
									tempstudent.setCoinpaynumber(new BigDecimal(olist[4].toString()));
								}
								else {
									tempstudent.setCoinpaynumber(new BigDecimal(0));
								}
								studentcoinlist.add(tempstudent);
							}
							cointemp.setStudentdetaillist(studentcoinlist);
						}
						coinreportmontly.add(cointemp);
			}
			
		}
		if (coinreportmontly != null && coinreportmontly.size() > 0) {
			for (int i = 0; i < coinreportmontly.size(); i++) {
				row = sheet.createRow((Integer) index);
				CoinReportMontly coinmontly = coinreportmontly.get(i);
				List<StudentCoinDetail> tempstudentlist=coinmontly.getStudentdetaillist();
				int rowcount=0;
				int rowendindex=index;
				if(tempstudentlist!=null)
				{
				  rowcount=coinmontly.getStudentdetaillist().size();
				  rowendindex=index+rowcount-1;
				}
				cell=row.createCell((short) 0);
				cell.setCellValue(i + 1);
				cell.setCellStyle(style);
				sheet.addMergedRegion(new Region(index,(short)0,rowendindex,(short) 0));
				cell=row.createCell((short) 1);
				cell.setCellValue("小巴币");
				cell.setCellStyle(style);
				sheet.addMergedRegion(new Region(index,(short)1,rowendindex,(short) 1));
				if (coinmontly.getCoachname() != null) {
					cell=row.createCell((short) 2);
					cell.setCellValue(coinmontly.getCoachname());
					cell.setCellStyle(style);
					sheet.addMergedRegion(new Region(index,(short)2,rowendindex,(short) 2));
				}
				if (coinmontly.getSchoolname() != null) {
					cell=row.createCell((short) 3);
					cell.setCellValue(coinmontly.getSchoolname());
					cell.setCellStyle(style);
					sheet.addMergedRegion(new Region(index,(short)3,rowendindex,(short) 3));
				}
				if (coinmontly.getCoachphone() != null) {
					cell=row.createCell((short) 4);
					cell.setCellValue(coinmontly.getCoachphone());
					cell.setCellStyle(style);
					sheet.addMergedRegion(new Region(index,(short)4,rowendindex,(short) 4));
				}
				cell=row.createCell((short) 5);
				cell.setCellValue("1");
				cell.setCellStyle(style);
				sheet.addMergedRegion(new Region(index,(short)5,rowendindex,(short) 5));
				
				if (coinmontly.getCoinnumber() != null) {
					cell=row.createCell((short) 6);
					cell.setCellValue(coinmontly.getCoinnumber().doubleValue());
					cell.setCellStyle(style);
					sheet.addMergedRegion(new Region(index,(short)6,rowendindex,(short) 6));
				}
				if (coinmontly.getCoinnpaycount() != null) {
					cell=row.createCell((short) 7);
					cell.setCellValue(coinmontly.getCoinnpaycount().doubleValue());
					cell.setCellStyle(style);
					sheet.addMergedRegion(new Region(index,(short)7,rowendindex,(short) 7));
				}
				if(tempstudentlist!=null && tempstudentlist.size()>0)
				{
				    for(int j=0;j<tempstudentlist.size();j++)
				    {
				    	StudentCoinDetail tempt=tempstudentlist.get(j);
				    	if(j==0)
				    	{
					    	cell=row.createCell((short) 8);
					    	cell.setCellValue(tempt.getPhone());
							cell.setCellStyle(style);
							cell=row.createCell((short) 9);
					    	cell.setCellValue(tempt.getName());
							cell.setCellStyle(style);
							cell=row.createCell((short) 10);
					    	cell.setCellValue(tempt.getCoinusenumber().doubleValue());
							cell.setCellStyle(style);
							cell=row.createCell((short) 11);
					    	cell.setCellValue(tempt.getCoinpaynumber().doubleValue());
							cell.setCellStyle(style);
							if(j==tempstudentlist.size()-1)
					    	{
					    		index++;
					    	}
				    	}
	                   
				    	else
				    	{
				    		index++;
				    		row=sheet.createRow((Integer) index);
				    		cell=row.createCell((short) 8);
					    	cell.setCellValue(tempt.getPhone());
							cell.setCellStyle(style);
							cell=row.createCell((short) 9);
					    	cell.setCellValue(tempt.getName());
							cell.setCellStyle(style);
							cell=row.createCell((short) 10);
					    	cell.setCellValue(tempt.getCoinusenumber().doubleValue());
							cell.setCellStyle(style);
							cell=row.createCell((short) 11);
					    	cell.setCellValue(tempt.getCoinpaynumber().doubleValue());
							cell.setCellStyle(style);
							if(j==tempstudentlist.size()-1)
					    	{
					    		index++;
					    	}
				    	}
				    	
				    }
				}
				else
					index++;
			}
		}
		try {
			FileOutputStream fout = new FileOutputStream(file);
			wb.write(fout);
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		filename = CommonUtils.properties.getProperty("uploadFilePath") + newfilename;
		HttpServletResponse response = ServletActionContext.getResponse();
		CommonUtils.downloadExcel(filename, "小巴币月报表", response);
	}
	
	/**
	 * 教练日报
	 * 
	 * @return
	 */
	@Action(value = "/coachdaily", results = { @Result(name = SUCCESS, location = "/coachdaily.jsp") })
	public String coachdaily() {
		if (CommonUtils.isEmptyString(addtime) || addtime == null) {
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			addtime = formatter.format(new Date());
		}
		List<Object> obj = dailyService.getCoachDaily(addtime);
		coachdailylist = new ArrayList<CoachDailyData>();
		total = obj.size();
		int pagesize = 10;
		pageCount = (total + pagesize - 1) / pagesize;
		if (obj != null && obj.size() > 0) {
			for (Object object : obj) {
				indexnum++;
				CoachDailyData coachdaily = new CoachDailyData();
				Object[] array = (Object[]) object;
				if (array[0] != null) {
					coachdaily.setCoachname(array[0].toString());
				}
				if (array[1] != null) {
					coachdaily.setSchoolname(array[1].toString());
				}
				if (array[2] != null) {
					coachdaily.setUnfinishorder((BigInteger) array[2]);
				} else {
					coachdaily.setUnfinishorder(new BigInteger("0"));
				}
				if (array[3] != null) {
					coachdaily.setCreateorder((BigInteger) array[3]);
				} else {
					coachdaily.setCreateorder(new BigInteger("0"));
				}
				if (array[4] != null) {
					coachdaily.setFinishorder((BigInteger) array[4]);
				} else {
					coachdaily.setFinishorder(new BigInteger("0"));
				}
				if (array[5] != null) {
					coachdaily.setAllunfinishorder((BigInteger) array[5]);
				} else {
					coachdaily.setAllunfinishorder(new BigInteger("0"));
				}
				if (array[6] != null) {
					coachdaily.setUnfinishhour((BigDecimal) array[6]);
				} else {
					coachdaily.setUnfinishhour(new BigDecimal(0));
				}
				if (array[7] != null) {
					coachdaily.setOrderhour((BigDecimal) array[7]);
				} else {
					coachdaily.setOrderhour(new BigDecimal(0));
				}
				if (array[8] != null) {
					coachdaily.setFinishhour((BigDecimal) array[8]);
				} else {
					coachdaily.setFinishhour(new BigDecimal(0));
				}
				if (array[9] != null) {
					coachdaily.setAllunfinishhour((BigDecimal) array[9]);
				} else {
					coachdaily.setAllunfinishhour(new BigDecimal(0));
				}
				if (array[10] != null) {
					coachdaily.setMoney((BigDecimal) array[10]);
				} else {
					coachdaily.setMoney(new BigDecimal(0));
				}
				if ((pageIndex * pagesize - 9) <= indexnum && indexnum <= (pageIndex * pagesize)) {
					coachdailylist.add(coachdaily);
				}
			}
		}
		return SUCCESS;
	}

	/**
	 * 教练日报导出
	 * 
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	@Action(value = "/coachNoticeExport")
	public void coachNoticeExport() throws IOException {
		// 以下开始输出到EXCEL
		String newfilename = "";
		// 定义输出流，以便打开保存对话框begin
		newfilename += CommonUtils.getTimeFormat(new Date(), "yyyyMMddhhmmssSSS") + "_" + (int) (Math.random() * 100) + ".xls";
		String filename = CommonUtils.properties.get("uploadFilePath") + newfilename;
		File file = new File(filename);
		if (!file.exists()) {
			file.createNewFile();
		}
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("教练日报表");
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		int index = 0;
		HSSFRow row = sheet.createRow((Integer) index++);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue(addtime + "教练日报");
		cell.setCellStyle(style);
		row = sheet.createRow((Integer) index++);
		cell = row.createCell((short) 0);
		cell.setCellValue("序号");
		cell.setCellStyle(style);
		cell = row.createCell((short) 1);
		cell.setCellValue("教练");
		cell.setCellStyle(style);
		cell = row.createCell((short) 2);
		cell.setCellValue("教校");
		cell.setCellStyle(style);
		cell = row.createCell((short) 3);
		cell.setCellValue("期初未完成订单数");
		cell.setCellStyle(style);
		cell = row.createCell((short) 4);
		cell.setCellValue("当日生成订单数");
		cell.setCellStyle(style);
		cell = row.createCell((short) 5);
		cell.setCellValue("当日完成订单数");
		cell.setCellStyle(style);
		cell = row.createCell((short) 6);
		cell.setCellValue("累计未完成订单数");
		cell.setCellStyle(style);
		cell = row.createCell((short) 7);
		cell.setCellValue("期初未完成学时数");
		cell.setCellStyle(style);
		cell = row.createCell((short) 8);
		cell.setCellValue("当日预约学时数");
		cell.setCellStyle(style);
		cell = row.createCell((short) 9);
		cell.setCellValue("当日完成学时数");
		cell.setCellStyle(style);
		cell = row.createCell((short) 10);
		cell.setCellValue("累计未完成学时数");
		cell.setCellStyle(style);
		cell = row.createCell((short) 11);
		cell.setCellValue("当日教练账户余额");
		cell.setCellStyle(style);
		List<Object> obj = dailyService.getCoachDaily(addtime);
		coachdailylist = new ArrayList<CoachDailyData>();
		if (obj != null && obj.size() > 0) {
			for (Object object : obj) {
				CoachDailyData coachdaily = new CoachDailyData();
				Object[] array = (Object[]) object;
				if (array[0] != null) {
					coachdaily.setCoachname(array[0].toString());
				}
				if (array[1] != null) {
					coachdaily.setSchoolname(array[1].toString());
				}
				if (array[2] != null) {
					coachdaily.setUnfinishorder((BigInteger) array[2]);
				} else {
					coachdaily.setUnfinishorder(new BigInteger("0"));
				}
				if (array[3] != null) {
					coachdaily.setCreateorder((BigInteger) array[3]);
				} else {
					coachdaily.setCreateorder(new BigInteger("0"));
				}
				if (array[4] != null) {
					coachdaily.setFinishorder((BigInteger) array[4]);
				} else {
					coachdaily.setFinishorder(new BigInteger("0"));
				}
				if (array[5] != null) {
					coachdaily.setAllunfinishorder((BigInteger) array[5]);
				} else {
					coachdaily.setAllunfinishorder(new BigInteger("0"));
				}
				if (array[6] != null) {
					coachdaily.setUnfinishhour((BigDecimal) array[6]);
				} else {
					coachdaily.setUnfinishhour(new BigDecimal(0));
				}
				if (array[7] != null) {
					coachdaily.setOrderhour((BigDecimal) array[7]);
				} else {
					coachdaily.setOrderhour(new BigDecimal(0));
				}
				if (array[8] != null) {
					coachdaily.setFinishhour((BigDecimal) array[8]);
				} else {
					coachdaily.setFinishhour(new BigDecimal(0));
				}
				if (array[9] != null) {
					coachdaily.setAllunfinishhour((BigDecimal) array[9]);
				} else {
					coachdaily.setAllunfinishhour(new BigDecimal(0));
				}
				if (array[10] != null) {
					coachdaily.setMoney((BigDecimal) array[10]);
				} else {
					coachdaily.setMoney(new BigDecimal(0));
				}
				coachdailylist.add(coachdaily);
			}
		}
		if (coachdailylist != null && coachdailylist.size() > 0) {
			for (int i = 0; i < coachdailylist.size(); i++) {
				row = sheet.createRow((Integer) index++);
				CoachDailyData coachdaily = coachdailylist.get(i);
				row.createCell((short) 0).setCellValue(i + 1);
				if (coachdaily.getCoachname() != null) {
					row.createCell((short) 1).setCellValue(coachdaily.getCoachname());
				}
				if (coachdaily.getSchoolname() != null) {
					row.createCell((short) 2).setCellValue(coachdaily.getSchoolname());
				}
				if (coachdaily.getUnfinishorder() != null) {
					row.createCell((short) 3).setCellValue(coachdaily.getUnfinishorder().toString());
				}
				if (coachdaily.getCreateorder() != null) {
					row.createCell((short) 4).setCellValue(coachdaily.getCreateorder().toString());
				}
				if (coachdaily.getFinishorder() != null) {
					row.createCell((short) 5).setCellValue(coachdaily.getFinishorder().toString());
				}
				if (coachdaily.getAllunfinishorder() != null) {
					row.createCell((short) 6).setCellValue(coachdaily.getAllunfinishorder().toString());
				}
				if (coachdaily.getUnfinishhour() != null) {
					row.createCell((short) 7).setCellValue(coachdaily.getUnfinishhour().toString());
				}
				if (coachdaily.getOrderhour() != null) {
					row.createCell((short) 8).setCellValue(coachdaily.getOrderhour().toString());
				}
				if (coachdaily.getFinishhour() != null) {
					row.createCell((short) 9).setCellValue(coachdaily.getFinishhour().toString());
				}
				if (coachdaily.getAllunfinishhour() != null) {
					row.createCell((short) 10).setCellValue(coachdaily.getAllunfinishhour().toString());
				}
				if (coachdaily.getMoney() != null) {
					row.createCell((short) 11).setCellValue(coachdaily.getMoney().toString());
				}
			}
		}
		try {
			FileOutputStream fout = new FileOutputStream(file);
			wb.write(fout);
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		filename = CommonUtils.properties.getProperty("uploadFilePath") + newfilename;
		HttpServletResponse response = ServletActionContext.getResponse();
		CommonUtils.downloadExcel(filename, "教练日报表", response);
	}

	/**
	 * 学员当日提现日报
	 * 
	 * @return
	 */
	@Action(value = "/studentapply", results = { @Result(name = SUCCESS, location = "/studentapplydaily.jsp") })
	public String studentapply() {
		if (CommonUtils.isEmptyString(addtime) || addtime == null) {
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			addtime = formatter.format(new Date());
		}
		List<Object> student = dailyService.getStudentApplyDaily(addtime);
		studentapplylist = new ArrayList<StudentApplyDailyData>();
		total = student.size();
		int pagesize = 10;
		pageCount = (total + pagesize - 1) / pagesize;
		if (student != null && student.size() > 0) {
			for (Object object : student) {
				indexnum++;
				StudentApplyDailyData studentapply = new StudentApplyDailyData();
				Object[] array = (Object[]) object;
				if (array[0] != null) {
					studentapply.setName(array[0].toString());
				}
				if (array[1] != null) {
					studentapply.setBeginaskmoney((BigDecimal) array[1]);
				} else {
					studentapply.setBeginaskmoney(new BigDecimal(0));
				}
				if (array[2] != null) {
					studentapply.setNowaskmoney((BigDecimal) array[2]);
				} else {
					studentapply.setNowaskmoney(new BigDecimal(0));
				}
				if (array[3] != null) {
					studentapply.setNowallaskmoney((BigDecimal) array[3]);
				} else {
					studentapply.setNowallaskmoney(new BigDecimal(0));
				}
				if (array[4] != null) {
					studentapply.setDmoney((BigDecimal) array[4]);
				} else {
					studentapply.setDmoney(new BigDecimal(0));
				}
				if (array[5] != null) {
					studentapply.setWdmoney((BigDecimal) array[5]);
				} else {
					studentapply.setWdmoney(new BigDecimal(0));
				}
				if ((pageIndex * pagesize - 9) <= indexnum && indexnum <= (pageIndex * pagesize)) {
					studentapplylist.add(studentapply);
				}
			}
		}
		return SUCCESS;
	}

	/**
	 * 学生当日提现日报导出
	 * 
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	@Action(value = "/studentApplyNoticeExport")
	public void studentApplyNoticeExport() throws IOException {
		// 以下开始输出到EXCEL
		String newfilename = "";
		// 定义输出流，以便打开保存对话框begin
		newfilename += CommonUtils.getTimeFormat(new Date(), "yyyyMMddhhmmssSSS") + "_" + (int) (Math.random() * 100) + ".xls";
		String filename = CommonUtils.properties.get("uploadFilePath") + newfilename;
		File file = new File(filename);
		if (!file.exists()) {
			file.createNewFile();
		}
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("学生当日提现日报表");
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		int index = 0;
		HSSFRow row = sheet.createRow((Integer) index++);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue(addtime + "学生当日提现日报");
		cell.setCellStyle(style);
		row = sheet.createRow((Integer) index++);
		cell = row.createCell((short) 0);
		cell.setCellValue("序号");
		cell.setCellStyle(style);
		cell = row.createCell((short) 1);
		cell.setCellValue("取款人");
		cell.setCellStyle(style);
		cell = row.createCell((short) 2);
		cell.setCellValue("期初累计取款申请金额");
		cell.setCellStyle(style);
		cell = row.createCell((short) 3);
		cell.setCellValue("当日取款申请金额");
		cell.setCellStyle(style);
		cell = row.createCell((short) 4);
		cell.setCellValue("当日累计取款申请金额");
		cell.setCellStyle(style);
		cell = row.createCell((short) 5);
		cell.setCellValue("当日已处理取款申请金额");
		cell.setCellStyle(style);
		cell = row.createCell((short) 6);
		cell.setCellValue("当日待处理取款申请金额");
		cell.setCellStyle(style);
		List<Object> student = dailyService.getStudentApplyDaily(addtime);
		studentapplylist = new ArrayList<StudentApplyDailyData>();
		if (student != null && student.size() > 0) {
			for (Object object : student) {
				StudentApplyDailyData studentapply = new StudentApplyDailyData();
				Object[] array = (Object[]) object;
				if (array[0] != null) {
					studentapply.setName(array[0].toString());
				}
				if (array[1] != null) {
					studentapply.setBeginaskmoney((BigDecimal) array[1]);
				} else {
					studentapply.setBeginaskmoney(new BigDecimal(0));
				}
				if (array[2] != null) {
					studentapply.setNowaskmoney((BigDecimal) array[2]);
				} else {
					studentapply.setNowaskmoney(new BigDecimal(0));
				}
				if (array[3] != null) {
					studentapply.setNowallaskmoney((BigDecimal) array[3]);
				} else {
					studentapply.setNowallaskmoney(new BigDecimal(0));
				}
				if (array[4] != null) {
					studentapply.setDmoney((BigDecimal) array[4]);
				} else {
					studentapply.setDmoney(new BigDecimal(0));
				}
				if (array[5] != null) {
					studentapply.setWdmoney((BigDecimal) array[5]);
				} else {
					studentapply.setWdmoney(new BigDecimal(0));
				}
				studentapplylist.add(studentapply);
			}
		}
		if (studentapplylist != null && studentapplylist.size() > 0) {
			for (int i = 0; i < studentapplylist.size(); i++) {
				row = sheet.createRow((Integer) index++);
				StudentApplyDailyData studentapply = studentapplylist.get(i);
				row.createCell((short) 0).setCellValue(i + 1);
				if (studentapply.getName() != null) {
					row.createCell((short) 1).setCellValue(studentapply.getName());
				}
				if (studentapply.getBeginaskmoney() != null) {
					row.createCell((short) 2).setCellValue(studentapply.getBeginaskmoney().toString());
				}
				if (studentapply.getNowaskmoney() != null) {
					row.createCell((short) 3).setCellValue(studentapply.getNowaskmoney().toString());
				}
				if (studentapply.getNowallaskmoney() != null) {
					row.createCell((short) 4).setCellValue(studentapply.getNowallaskmoney().toString());
				}
				if (studentapply.getDmoney() != null) {
					row.createCell((short) 5).setCellValue(studentapply.getDmoney().toString());
				}
				if (studentapply.getWdmoney() != null) {
					row.createCell((short) 6).setCellValue(studentapply.getWdmoney().toString());
				}
			}
		}
		try {
			FileOutputStream fout = new FileOutputStream(file);
			wb.write(fout);
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		filename = CommonUtils.properties.getProperty("uploadFilePath") + newfilename;
		HttpServletResponse response = ServletActionContext.getResponse();
		CommonUtils.downloadExcel(filename, "学生当日提现日报", response);
	}

	/**
	 * 教练当日提现日报
	 * 
	 * @return
	 */
	@Action(value = "/coachapply", results = { @Result(name = SUCCESS, location = "/coachapplydaily.jsp") })
	public String coachapply() {
		if (CommonUtils.isEmptyString(addtime) || addtime == null) {
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			addtime = formatter.format(new Date());
		}
		List<Object> coach = dailyService.getCoachApplyDaily(addtime);
		coachapplylist = new ArrayList<CoachApplyDailyData>();
		total = coach.size();
		int pagesize = 10;
		pageCount = (total + pagesize - 1) / pagesize;
		if (coach != null && coach.size() > 0) {
			for (Object object : coach) {
				indexnum++;
				CoachApplyDailyData coachapply = new CoachApplyDailyData();
				Object[] array = (Object[]) object;
				if (array[0] != null) {
					coachapply.setName(array[0].toString());
				}
				if (array[1] != null) {
					coachapply.setRealname(array[1].toString());
				}
				if (array[2] != null) {
					coachapply.setBeginaskmoney((BigDecimal) array[2]);
				} else {
					coachapply.setBeginaskmoney(new BigDecimal(0));
				}
				if (array[3] != null) {
					coachapply.setNowaskmoney((BigDecimal) array[3]);
				} else {
					coachapply.setNowaskmoney(new BigDecimal(0));
				}
				if (array[4] != null) {
					coachapply.setNowallaskmoney((BigDecimal) array[4]);
				} else {
					coachapply.setNowallaskmoney(new BigDecimal(0));
				}
				if (array[5] != null) {
					coachapply.setDmoney((BigDecimal) array[5]);
				} else {
					coachapply.setDmoney(new BigDecimal(0));
				}
				if (array[6] != null) {
					coachapply.setWdmoney((BigDecimal) array[6]);
				} else {
					coachapply.setWdmoney(new BigDecimal(0));
				}
				if ((pageIndex * pagesize - 9) <= indexnum && indexnum <= (pageIndex * pagesize)) {
					coachapplylist.add(coachapply);
				}
			}
		}
		return SUCCESS;
	}

	/**
	 * 教练当日提现日报导出
	 * 
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	@Action(value = "/coachApplyNoticeExport")
	public void coachApplyNoticeExport() throws IOException {
		// 以下开始输出到EXCEL
		String newfilename = "";
		// 定义输出流，以便打开保存对话框begin
		newfilename += CommonUtils.getTimeFormat(new Date(), "yyyyMMddhhmmssSSS") + "_" + (int) (Math.random() * 100) + ".xls";
		String filename = CommonUtils.properties.get("uploadFilePath") + newfilename;
		File file = new File(filename);
		if (!file.exists()) {
			file.createNewFile();
		}
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("教练当日提现日报表");
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		int index = 0;
		HSSFRow row = sheet.createRow((Integer) index++);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue(addtime + "教练当日提现日报");
		cell.setCellStyle(style);
		row = sheet.createRow((Integer) index++);
		cell = row.createCell((short) 0);
		cell.setCellValue("序号");
		cell.setCellStyle(style);
		cell = row.createCell((short) 1);
		cell.setCellValue("取款人");
		cell.setCellStyle(style);
		cell = row.createCell((short) 2);
		cell.setCellValue("收款人");
		cell.setCellStyle(style);
		cell = row.createCell((short) 3);
		cell.setCellValue("期初累计取款申请金额");
		cell.setCellStyle(style);
		cell = row.createCell((short) 4);
		cell.setCellValue("当日取款申请金额");
		cell.setCellStyle(style);
		cell = row.createCell((short) 5);
		cell.setCellValue("当日累计取款申请金额");
		cell.setCellStyle(style);
		cell = row.createCell((short) 6);
		cell.setCellValue("当日已处理取款申请金额");
		cell.setCellStyle(style);
		cell = row.createCell((short) 7);
		cell.setCellValue("当日待处理取款申请金额");
		cell.setCellStyle(style);
		List<Object> coach = dailyService.getCoachApplyDaily(addtime);
		coachapplylist = new ArrayList<CoachApplyDailyData>();
		if (coach != null && coach.size() > 0) {
			for (Object object : coach) {
				CoachApplyDailyData coachapply = new CoachApplyDailyData();
				Object[] array = (Object[]) object;
				if (array[0] != null) {
					coachapply.setName(array[0].toString());
				}
				if (array[1] != null) {
					coachapply.setRealname(array[1].toString());
				}
				if (array[2] != null) {
					coachapply.setBeginaskmoney((BigDecimal) array[2]);
				} else {
					coachapply.setBeginaskmoney(new BigDecimal(0));
				}
				if (array[3] != null) {
					coachapply.setNowaskmoney((BigDecimal) array[3]);
				} else {
					coachapply.setNowaskmoney(new BigDecimal(0));
				}
				if (array[4] != null) {
					coachapply.setNowallaskmoney((BigDecimal) array[4]);
				} else {
					coachapply.setNowallaskmoney(new BigDecimal(0));
				}
				if (array[5] != null) {
					coachapply.setDmoney((BigDecimal) array[5]);
				} else {
					coachapply.setDmoney(new BigDecimal(0));
				}
				if (array[6] != null) {
					coachapply.setWdmoney((BigDecimal) array[6]);
				} else {
					coachapply.setWdmoney(new BigDecimal(0));
				}
				coachapplylist.add(coachapply);
			}
		}
		if (coachapplylist != null && coachapplylist.size() > 0) {
			for (int i = 0; i < coachapplylist.size(); i++) {
				row = sheet.createRow((Integer) index++);
				CoachApplyDailyData studentapply = coachapplylist.get(i);
				row.createCell((short) 0).setCellValue(i + 1);
				if (studentapply.getName() != null) {
					row.createCell((short) 1).setCellValue(studentapply.getName());
				}
				if (studentapply.getRealname() != null) {
					row.createCell((short) 2).setCellValue(studentapply.getRealname());
				}
				if (studentapply.getBeginaskmoney() != null) {
					row.createCell((short) 3).setCellValue(studentapply.getBeginaskmoney().toString());
				}
				if (studentapply.getNowaskmoney() != null) {
					row.createCell((short) 4).setCellValue(studentapply.getNowaskmoney().toString());
				}
				if (studentapply.getNowallaskmoney() != null) {
					row.createCell((short) 5).setCellValue(studentapply.getNowallaskmoney().toString());
				}
				if (studentapply.getDmoney() != null) {
					row.createCell((short) 6).setCellValue(studentapply.getDmoney().toString());
				}
				if (studentapply.getWdmoney() != null) {
					row.createCell((short) 7).setCellValue(studentapply.getWdmoney().toString());
				}
			}
		}
		try {
			FileOutputStream fout = new FileOutputStream(file);
			wb.write(fout);
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		filename = CommonUtils.properties.getProperty("uploadFilePath") + newfilename;
		HttpServletResponse response = ServletActionContext.getResponse();
		CommonUtils.downloadExcel(filename, "教练当日提现日报", response);
	}

	/**
	 * 学生账户日报
	 * 
	 * @return
	 */
	@Action(value = "/studentaccountdaily", results = { @Result(name = SUCCESS, location = "/studentaccountdaily.jsp") })
	public String studentaccountdaily() {
		if (CommonUtils.isEmptyString(addtime) || addtime == null) {
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			addtime = formatter.format(new Date());
		}
		Object object = dailyService.getStudentAccountDaily(addtime);
		Object[] obj = (Object[]) object;
		studentaccountdaily = new StudentAccountDaily();
		if (obj[0] != null) {
			studentaccountdaily.setBeginbalance((Integer) obj[0]);
		} else {
			studentaccountdaily.setBeginbalance(0);
		}
		if (obj[1] != null) {
			studentaccountdaily.setRechargemoney((Integer) obj[1]);
		} else {
			studentaccountdaily.setRechargemoney(0);
		}
		if (obj[2] != null) {
			studentaccountdaily.setAskformoney((Integer) obj[2]);
		} else {
			studentaccountdaily.setAskformoney(0);
		}
		if (obj[3] != null) {
			studentaccountdaily.setFreezemoney((Integer) obj[3]);
		} else {
			studentaccountdaily.setFreezemoney(0);
		}
		if (obj[4] != null) {
			studentaccountdaily.setEndbalance((Integer) obj[4]);
		} else {
			studentaccountdaily.setEndbalance(0);
		}
		if (obj[5] != null) {
			studentaccountdaily.setBeginfreezemoney((Integer) obj[5]);
		} else {
			studentaccountdaily.setBeginfreezemoney(0);
		}
		if (obj[6] != null) {
			studentaccountdaily.setDayfreezemoney((Integer) obj[6]);
		} else {
			studentaccountdaily.setDayfreezemoney(0);
		}
		if (obj[7] != null) {
			studentaccountdaily.setThawmoney((Integer) obj[7]);
		} else {
			studentaccountdaily.setThawmoney(0);
		}
		if (obj[8] != null) {
			studentaccountdaily.setPaymoney((Integer) obj[8]);
		} else {
			studentaccountdaily.setPaymoney(0);
		}
		return SUCCESS;
	}

	/**
	 * 学生账户日报导出
	 * 
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	@Action(value = "/studentAccountNoticeExport")
	public void studentAccountNoticeExport() throws IOException {
		// 以下开始输出到EXCEL
		String newfilename = "";
		// 定义输出流，以便打开保存对话框begin
		newfilename += CommonUtils.getTimeFormat(new Date(), "yyyyMMddhhmmssSSS") + "_" + (int) (Math.random() * 100) + ".xls";
		String filename = CommonUtils.properties.get("uploadFilePath") + newfilename;
		File file = new File(filename);
		if (!file.exists()) {
			file.createNewFile();
		}
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("学生账户日报表");
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow((Integer) 0);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue(addtime + "学生账户日报");
		cell.setCellStyle(style);
		row = sheet.createRow((Integer) 1);
		cell = row.createCell((short) 0);
		cell.setCellValue("期初余额");
		cell.setCellStyle(style);
		cell = row.createCell((short) 1);
		cell.setCellValue("当日充值金额");
		cell.setCellStyle(style);
		cell = row.createCell((short) 2);
		cell.setCellValue("当日取款申请金额");
		cell.setCellStyle(style);
		cell = row.createCell((short) 3);
		cell.setCellValue("期末订单冻结金额");
		cell.setCellStyle(style);
		cell = row.createCell((short) 4);
		cell.setCellValue("期末（可取）余额");
		cell.setCellStyle(style);
		cell = row.createCell((short) 5);
		cell.setCellValue("期初订单冻结金额");
		cell.setCellStyle(style);
		cell = row.createCell((short) 6);
		cell.setCellValue("当日订单冻结金额");
		cell.setCellStyle(style);
		cell = row.createCell((short) 7);
		cell.setCellValue("当日订单解冻金额");
		cell.setCellStyle(style);
		cell = row.createCell((short) 8);
		cell.setCellValue("当日订单完成支付金额");
		cell.setCellStyle(style);
		cell = row.createCell((short) 9);
		cell.setCellValue("期末订单冻结金额");
		cell.setCellStyle(style);
		Object object = dailyService.getStudentAccountDaily(addtime);
		Object[] obj = (Object[]) object;
		studentaccountdaily = new StudentAccountDaily();
		if (obj[0] != null) {
			studentaccountdaily.setBeginbalance((Integer) obj[0]);
		} else {
			studentaccountdaily.setBeginbalance(0);
		}
		if (obj[1] != null) {
			studentaccountdaily.setRechargemoney((Integer) obj[1]);
		} else {
			studentaccountdaily.setRechargemoney(0);
		}
		if (obj[2] != null) {
			studentaccountdaily.setAskformoney((Integer) obj[2]);
		} else {
			studentaccountdaily.setAskformoney(0);
		}
		if (obj[3] != null) {
			studentaccountdaily.setFreezemoney((Integer) obj[3]);
		} else {
			studentaccountdaily.setFreezemoney(0);
		}
		if (obj[4] != null) {
			studentaccountdaily.setEndbalance((Integer) obj[4]);
		} else {
			studentaccountdaily.setEndbalance(0);
		}
		if (obj[5] != null) {
			studentaccountdaily.setBeginfreezemoney((Integer) obj[5]);
		} else {
			studentaccountdaily.setBeginfreezemoney(0);
		}
		if (obj[6] != null) {
			studentaccountdaily.setDayfreezemoney((Integer) obj[6]);
		} else {
			studentaccountdaily.setDayfreezemoney(0);
		}
		if (obj[7] != null) {
			studentaccountdaily.setThawmoney((Integer) obj[7]);
		} else {
			studentaccountdaily.setThawmoney(0);
		}
		if (obj[8] != null) {
			studentaccountdaily.setPaymoney((Integer) obj[8]);
		} else {
			studentaccountdaily.setPaymoney(0);
		}
		if (studentaccountdaily != null) {
			row = sheet.createRow((Integer) 2);
			row.createCell((short) 0).setCellValue(studentaccountdaily.getBeginbalance());
			row.createCell((short) 1).setCellValue(studentaccountdaily.getRechargemoney());
			row.createCell((short) 2).setCellValue(studentaccountdaily.getAskformoney());
			row.createCell((short) 3).setCellValue(studentaccountdaily.getFreezemoney());
			row.createCell((short) 4).setCellValue(studentaccountdaily.getEndbalance());
			row.createCell((short) 5).setCellValue(studentaccountdaily.getBeginfreezemoney());
			row.createCell((short) 6).setCellValue(studentaccountdaily.getDayfreezemoney());
			row.createCell((short) 7).setCellValue(studentaccountdaily.getThawmoney());
			row.createCell((short) 8).setCellValue(studentaccountdaily.getPaymoney());
			row.createCell((short) 9).setCellValue(studentaccountdaily.getFreezemoney());
		}
		try {
			FileOutputStream fout = new FileOutputStream(file);
			wb.write(fout);
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		filename = CommonUtils.properties.getProperty("uploadFilePath") + newfilename;
		HttpServletResponse response = ServletActionContext.getResponse();
		CommonUtils.downloadExcel(filename, "学员账户日报", response);
	}

	/**
	 * 教练账户日报
	 * 
	 * @return
	 */
	@Action(value = "/coachaccountdaily", results = { @Result(name = SUCCESS, location = "/coachaccountdaily.jsp") })
	public String coachaccountdaily() {
		if (CommonUtils.isEmptyString(addtime) || addtime == null) {
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			addtime = formatter.format(new Date());
		}
		Object object = dailyService.getCoachAccountDaily(addtime);
		Object[] obj = (Object[]) object;
		coachaccountdaily = new CoachAccountDaily();
		if (obj[0] != null) {
			coachaccountdaily.setBeginbalance((Integer) obj[0]);
		} else {
			coachaccountdaily.setBeginbalance(0);
		}
		if (obj[1] != null) {
			coachaccountdaily.setRechargemoney((Integer) obj[1]);
		} else {
			coachaccountdaily.setRechargemoney(0);
		}
		if (obj[2] != null) {
			coachaccountdaily.setAskformoney((Integer) obj[2]);
		} else {
			coachaccountdaily.setAskformoney(0);
		}
		if (obj[3] != null) {
			coachaccountdaily.setSystembalance((Integer) obj[3]);
		} else {
			coachaccountdaily.setSystembalance(0);
		}
		if (obj[4] != null) {
			coachaccountdaily.setGmoney((Integer) obj[4]);
		} else {
			coachaccountdaily.setGmoney(0);
		}
		if (obj[5] != null) {
			coachaccountdaily.setCanusemoney((Integer) (obj[5]));
		} else {
			coachaccountdaily.setCanusemoney(0);
		}
		if (obj[6] != null) {
			coachaccountdaily.setUnfinishordermoney((Integer) obj[6]);
		} else {
			coachaccountdaily.setUnfinishordermoney(0);
		}
		if (obj[7] != null) {
			coachaccountdaily.setJiesuanmoney((Integer) obj[7]);
		} else {
			coachaccountdaily.setJiesuanmoney(0);
		}
		if (obj[8] != null) {
			coachaccountdaily.setDrawbackmoney((Integer) obj[8]);
		} else {
			coachaccountdaily.setDrawbackmoney(0);
		}
		if (obj[9] != null) {
			coachaccountdaily.setEndunfinishmoney((BigDecimal) obj[9]);
		} else {
			coachaccountdaily.setEndunfinishmoney(new BigDecimal(0));
		}
		if (obj[10] != null) {
			coachaccountdaily.setFinishmoney((BigDecimal) obj[10]);
		} else {
			coachaccountdaily.setFinishmoney(new BigDecimal(0));
		}
		return SUCCESS;
	}

	/**
	 * 教练账户日报导出
	 * 
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	@Action(value = "/coachAccountNoticeExport")
	public void coachAccountNoticeExport() throws IOException {
		// 以下开始输出到EXCEL
		String newfilename = "";
		// 定义输出流，以便打开保存对话框begin
		newfilename += CommonUtils.getTimeFormat(new Date(), "yyyyMMddhhmmssSSS") + "_" + (int) (Math.random() * 100) + ".xls";
		String filename = CommonUtils.properties.get("uploadFilePath") + newfilename;
		File file = new File(filename);
		if (!file.exists()) {
			file.createNewFile();
		}
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("教练账户日报表");
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow((Integer) 0);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue(addtime + "教练账户日报");
		cell.setCellStyle(style);
		row = sheet.createRow((Integer) 1);
		cell = row.createCell((short) 0);
		cell.setCellValue("期初余额");
		cell.setCellStyle(style);
		cell = row.createCell((short) 1);
		cell.setCellValue("当日充值金额");
		cell.setCellStyle(style);
		cell = row.createCell((short) 2);
		cell.setCellValue("当日取款申请金额");
		cell.setCellStyle(style);
		cell = row.createCell((short) 3);
		cell.setCellValue("当日结算订单金额");
		cell.setCellStyle(style);
		cell = row.createCell((short) 4);
		cell.setCellValue("期末系统余额");
		cell.setCellStyle(style);
		cell = row.createCell((short) 5);
		cell.setCellValue("保证金");
		cell.setCellStyle(style);
		cell = row.createCell((short) 6);
		cell.setCellValue("期末教练可取金额");
		cell.setCellStyle(style);
		cell = row.createCell((short) 7);
		cell.setCellValue("期初未完成订单金额");
		cell.setCellStyle(style);
		cell = row.createCell((short) 8);
		cell.setCellValue("当日结算订单金额");
		cell.setCellStyle(style);
		cell = row.createCell((short) 9);
		cell.setCellValue("投诉订单退款金额");
		cell.setCellStyle(style);
		cell = row.createCell((short) 10);
		cell.setCellValue("期末未完成订单金额");
		cell.setCellStyle(style);
		cell = row.createCell((short) 11);
		cell.setCellValue("当日完成未结算订单金额");
		cell.setCellStyle(style);
		Object object = dailyService.getCoachAccountDaily(addtime);
		Object[] obj = (Object[]) object;
		coachaccountdaily = new CoachAccountDaily();
		if (obj[0] != null) {
			coachaccountdaily.setBeginbalance((Integer) obj[0]);
		} else {
			coachaccountdaily.setBeginbalance(0);
		}
		if (obj[1] != null) {
			coachaccountdaily.setRechargemoney((Integer) obj[1]);
		} else {
			coachaccountdaily.setRechargemoney(0);
		}
		if (obj[2] != null) {
			coachaccountdaily.setAskformoney((Integer) obj[2]);
		} else {
			coachaccountdaily.setAskformoney(0);
		}
		if (obj[3] != null) {
			coachaccountdaily.setSystembalance((Integer) obj[3]);
		} else {
			coachaccountdaily.setSystembalance(0);
		}
		if (obj[4] != null) {
			coachaccountdaily.setGmoney((Integer) obj[4]);
		} else {
			coachaccountdaily.setGmoney(0);
		}
		if (obj[5] != null) {
			coachaccountdaily.setCanusemoney((Integer) (obj[5]));
		} else {
			coachaccountdaily.setCanusemoney(0);
		}
		if (obj[6] != null) {
			coachaccountdaily.setUnfinishordermoney((Integer) obj[6]);
		} else {
			coachaccountdaily.setUnfinishordermoney(0);
		}
		if (obj[7] != null) {
			coachaccountdaily.setJiesuanmoney((Integer) obj[7]);
		} else {
			coachaccountdaily.setJiesuanmoney(0);
		}
		if (obj[8] != null) {
			coachaccountdaily.setDrawbackmoney((Integer) obj[8]);
		} else {
			coachaccountdaily.setDrawbackmoney(0);
		}
		if (obj[9] != null) {
			coachaccountdaily.setEndunfinishmoney((BigDecimal) obj[9]);
		} else {
			coachaccountdaily.setEndunfinishmoney(new BigDecimal(0));
		}
		if (obj[10] != null) {
			coachaccountdaily.setFinishmoney((BigDecimal) obj[10]);
		} else {
			coachaccountdaily.setFinishmoney(new BigDecimal(0));
		}
		if (coachaccountdaily != null) {
			row = sheet.createRow((Integer) 2);
			row.createCell((short) 0).setCellValue(coachaccountdaily.getBeginbalance());
			row.createCell((short) 1).setCellValue(coachaccountdaily.getRechargemoney());
			row.createCell((short) 2).setCellValue(coachaccountdaily.getAskformoney());
			row.createCell((short) 3).setCellValue(coachaccountdaily.getJiesuanmoney());
			row.createCell((short) 4).setCellValue(coachaccountdaily.getSystembalance());
			row.createCell((short) 5).setCellValue(coachaccountdaily.getGmoney());
			row.createCell((short) 6).setCellValue(coachaccountdaily.getCanusemoney());
			row.createCell((short) 7).setCellValue(coachaccountdaily.getUnfinishordermoney());
			row.createCell((short) 8).setCellValue(coachaccountdaily.getJiesuanmoney());
			row.createCell((short) 9).setCellValue(coachaccountdaily.getDrawbackmoney());
			if (coachaccountdaily.getEndunfinishmoney() != null) {
				row.createCell((short) 10).setCellValue(coachaccountdaily.getEndunfinishmoney().toString());
			}
			if (coachaccountdaily.getFinishmoney() != null) {
				row.createCell((short) 11).setCellValue(coachaccountdaily.getFinishmoney().toString());
			}
		}
		try {
			FileOutputStream fout = new FileOutputStream(file);
			wb.write(fout);
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		filename = CommonUtils.properties.getProperty("uploadFilePath") + newfilename;
		HttpServletResponse response = ServletActionContext.getResponse();
		CommonUtils.downloadExcel(filename, "教练账户日报", response);
	}

	/**
	 * 教校对账单
	 * 
	 * @return
	 */
	@Action(value = "/schoolbilldaily", results = { @Result(name = SUCCESS, location = "/schoolbilldaily.jsp") })
	public String schoolbilldaily() {
		if (CommonUtils.isEmptyString(addtime) || addtime == null) {
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			addtime = formatter.format(new Date());
		}
		if (CommonUtils.isEmptyString(starttime) || starttime == null) {
			Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH) + 1;
			starttime = year + "-" + month + "-01";
		}
		if (CommonUtils.isEmptyString(schoolname) || schoolname == null) {
			schoolname = "";
		}
		driveSchoollist = cuserService.getDriveSchoolInfo();
		List<Object> obj = dailyService.getSchoolBillDaily(starttime, addtime, schoolname);
		schoolbilldaily = new ArrayList<SchoolBillDaily>();
		total = obj.size();
		int pagesize = 10;
		pageCount = (total + pagesize - 1) / pagesize;
		if (obj != null && obj.size() > 0) {
			for (Object object : obj) {
				indexnum++;
				SchoolBillDaily schoolbill = new SchoolBillDaily();
				Object[] array = (Object[]) object;
				if (array[0] != null) {
					schoolbill.setSchoolname(array[0].toString());
					;
				}
				if (array[1] != null) {
					schoolbill.setCoachname(array[1].toString());
				}
				if (array[2] != null) {
					schoolbill.setUnfinishorder((BigInteger) array[2]);
				} else {
					schoolbill.setUnfinishorder(new BigInteger("0"));
				}
				if (array[3] != null) {
					schoolbill.setCreateorder((BigInteger) array[3]);
				} else {
					schoolbill.setCreateorder(new BigInteger("0"));
				}
				if (array[4] != null) {
					schoolbill.setFinishorder((BigInteger) array[4]);
				} else {
					schoolbill.setFinishorder(new BigInteger("0"));
				}
				if (array[5] != null) {
					schoolbill.setAllunfinishorder((BigInteger) array[5]);
				} else {
					schoolbill.setAllunfinishorder(new BigInteger("0"));
				}
				if (array[6] != null) {
					schoolbill.setBeginorderhour((BigDecimal) array[6]);
				} else {
					schoolbill.setBeginorderhour(new BigDecimal(0));
				}
				if (array[7] != null) {
					schoolbill.setOrderhour((BigDecimal) array[7]);
				} else {
					schoolbill.setOrderhour(new BigDecimal(0));
				}
				if (array[8] != null) {
					schoolbill.setFinishhour((BigDecimal) array[8]);
				} else {
					schoolbill.setFinishhour(new BigDecimal(0));
				}
				if (array[9] != null) {
					schoolbill.setAllunfinishhour((BigDecimal) array[9]);
				} else {
					schoolbill.setAllunfinishhour(new BigDecimal(0));
				}
				if (array[10] != null) {
					schoolbill.setUnfinishmoney((BigDecimal) array[10]);
				} else {
					schoolbill.setUnfinishmoney(new BigDecimal(0));
				}
				if (array[11] != null) {
					schoolbill.setJiesuanmoney((BigDecimal) array[11]);
				} else {
					schoolbill.setJiesuanmoney(new BigDecimal(0));
				}
				if (array[12] != null) {
					schoolbill.setDrawbackmoney((BigInteger) array[12]);
				} else {
					schoolbill.setDrawbackmoney(new BigInteger("0"));
				}
				if (array[13] != null) {
					schoolbill.setEndunfinishmoney((BigDecimal) array[13]);
				} else {
					schoolbill.setEndunfinishmoney(new BigDecimal(0));
				}
				if (array[14] != null) {
					schoolbill.setMoney((BigDecimal) array[14]);
				} else {
					schoolbill.setMoney(new BigDecimal(0));
				}
				if (array[15] != null) {
					schoolbill.setRechargemoney((BigDecimal) array[15]);
				} else {
					schoolbill.setRechargemoney(new BigDecimal(0));
				}
				if (array[16] != null) {
					schoolbill.setDrawmoney((BigDecimal) array[16]);
				} else {
					schoolbill.setDrawmoney(new BigDecimal(0));
				}
				if (array[17] != null) {
					schoolbill.setDayjiesuanmoney((BigDecimal) array[17]);
				} else {
					schoolbill.setDayjiesuanmoney(new BigDecimal(0));
				}
				if (array[18] != null) {
					schoolbill.setSystemmoney((BigDecimal) array[18]);
				} else {
					schoolbill.setSystemmoney(new BigDecimal(0));
				}
				if (array[19] != null) {
					schoolbill.setGmoney((BigDecimal) array[19]);
				} else {
					schoolbill.setGmoney(new BigDecimal(0));
				}
				if (array[20] != null) {
					schoolbill.setEndusermoney((BigDecimal) array[20]);
				} else {
					schoolbill.setEndusermoney(new BigDecimal(0));
				}
				if ((pageIndex * pagesize - 9) <= indexnum && indexnum <= (pageIndex * pagesize)) {
					schoolbilldaily.add(schoolbill);
				}
			}
		}
		return SUCCESS;
	}

	/**
	 * 教校对账单导出
	 * 
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	@Action(value = "/schoolBillNoticeExport")
	public void schoolBillNoticeExport() throws IOException {
		// 以下开始输出到EXCEL
		String newfilename = "";
		// 定义输出流，以便打开保存对话框begin
		newfilename += CommonUtils.getTimeFormat(new Date(), "yyyyMMddhhmmssSSS") + "_" + (int) (Math.random() * 100) + ".xls";
		String filename = CommonUtils.properties.get("uploadFilePath") + newfilename;
		File file = new File(filename);
		if (!file.exists()) {
			file.createNewFile();
		}
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("教校对账单表");
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		int index = 0;
		HSSFRow row = sheet.createRow((Integer) index++);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue(schoolname + "对账单");
		cell.setCellStyle(style);
		row = sheet.createRow((Integer) index++);
		cell = row.createCell((short) 0);
		cell.setCellValue("对账单时间:");
		cell.setCellStyle(style);
		cell = row.createCell((short) 1);
		cell.setCellValue(starttime + "至" + addtime);
		cell.setCellStyle(style);
		row = sheet.createRow((Integer) index++);
		cell = row.createCell((short) 0);
		cell.setCellValue("序号");
		cell.setCellStyle(style);
		cell = row.createCell((short) 1);
		cell.setCellValue("教校");
		cell.setCellStyle(style);
		cell = row.createCell((short) 2);
		cell.setCellValue("教练");
		cell.setCellStyle(style);
		cell = row.createCell((short) 3);
		cell.setCellValue("期初未完成订单数");
		cell.setCellStyle(style);
		cell = row.createCell((short) 4);
		cell.setCellValue("当期生成订单数");
		cell.setCellStyle(style);
		cell = row.createCell((short) 5);
		cell.setCellValue("当期完成订单数");
		cell.setCellStyle(style);
		cell = row.createCell((short) 6);
		cell.setCellValue("累计未完成订单数");
		cell.setCellStyle(style);
		cell = row.createCell((short) 7);
		cell.setCellValue("期初预约学时数");
		cell.setCellStyle(style);
		cell = row.createCell((short) 8);
		cell.setCellValue("当期预约学时数");
		cell.setCellStyle(style);
		cell = row.createCell((short) 9);
		cell.setCellValue("当期完成学时数");
		cell.setCellStyle(style);
		cell = row.createCell((short) 10);
		cell.setCellValue("累计未完成学时数");
		cell.setCellStyle(style);
		cell = row.createCell((short) 11);
		cell.setCellValue("期初未完成订单金额");
		cell.setCellStyle(style);
		cell = row.createCell((short) 12);
		cell.setCellValue("当期结算订单金额");
		cell.setCellStyle(style);
		cell = row.createCell((short) 13);
		cell.setCellValue("投诉订单退款金额");
		cell.setCellStyle(style);
		cell = row.createCell((short) 14);
		cell.setCellValue("期末未完成订单金额");
		cell.setCellStyle(style);
		cell = row.createCell((short) 15);
		cell.setCellValue("期初余额");
		cell.setCellStyle(style);
		cell = row.createCell((short) 16);
		cell.setCellValue("当期充值金额");
		cell.setCellStyle(style);
		cell = row.createCell((short) 17);
		cell.setCellValue("当期取款申请金额");
		cell.setCellStyle(style);
		cell = row.createCell((short) 18);
		cell.setCellValue("当期结算订单金额");
		cell.setCellStyle(style);
		cell = row.createCell((short) 19);
		cell.setCellValue("期末系统余额");
		cell.setCellStyle(style);
		cell = row.createCell((short) 20);
		cell.setCellValue("保证金");
		cell.setCellStyle(style);
		cell = row.createCell((short) 21);
		cell.setCellValue("期末教练可取金额");
		cell.setCellStyle(style);
		driveSchoollist = cuserService.getDriveSchoolInfo();
		List<Object> obj = dailyService.getSchoolBillDaily(starttime, addtime, schoolname);
		schoolbilldaily = new ArrayList<SchoolBillDaily>();
		if (obj != null && obj.size() > 0) {
			for (Object object : obj) {
				SchoolBillDaily schoolbill = new SchoolBillDaily();
				Object[] array = (Object[]) object;
				if (array[0] != null) {
					schoolbill.setSchoolname(array[0].toString());
					;
				}
				if (array[1] != null) {
					schoolbill.setCoachname(array[1].toString());
				}
				if (array[2] != null) {
					schoolbill.setUnfinishorder((BigInteger) array[2]);
				} else {
					schoolbill.setUnfinishorder(new BigInteger("0"));
				}
				if (array[3] != null) {
					schoolbill.setCreateorder((BigInteger) array[3]);
				} else {
					schoolbill.setCreateorder(new BigInteger("0"));
				}
				if (array[4] != null) {
					schoolbill.setFinishorder((BigInteger) array[4]);
				} else {
					schoolbill.setFinishorder(new BigInteger("0"));
				}
				if (array[5] != null) {
					schoolbill.setAllunfinishorder((BigInteger) array[5]);
				} else {
					schoolbill.setAllunfinishorder(new BigInteger("0"));
				}
				if (array[6] != null) {
					schoolbill.setBeginorderhour((BigDecimal) array[6]);
				} else {
					schoolbill.setBeginorderhour(new BigDecimal(0));
				}
				if (array[7] != null) {
					schoolbill.setOrderhour((BigDecimal) array[7]);
				} else {
					schoolbill.setOrderhour(new BigDecimal(0));
				}
				if (array[8] != null) {
					schoolbill.setFinishhour((BigDecimal) array[8]);
				} else {
					schoolbill.setFinishhour(new BigDecimal(0));
				}
				if (array[9] != null) {
					schoolbill.setAllunfinishhour((BigDecimal) array[9]);
				} else {
					schoolbill.setAllunfinishhour(new BigDecimal(0));
				}
				if (array[10] != null) {
					schoolbill.setUnfinishmoney((BigDecimal) array[10]);
				} else {
					schoolbill.setUnfinishmoney(new BigDecimal(0));
				}
				if (array[11] != null) {
					schoolbill.setJiesuanmoney((BigDecimal) array[11]);
				} else {
					schoolbill.setJiesuanmoney(new BigDecimal(0));
				}
				if (array[12] != null) {
					schoolbill.setDrawbackmoney((BigInteger) array[12]);
				} else {
					schoolbill.setDrawbackmoney(new BigInteger("0"));
				}
				if (array[13] != null) {
					schoolbill.setEndunfinishmoney((BigDecimal) array[13]);
				} else {
					schoolbill.setEndunfinishmoney(new BigDecimal(0));
				}
				if (array[14] != null) {
					schoolbill.setMoney((BigDecimal) array[14]);
				} else {
					schoolbill.setMoney(new BigDecimal(0));
				}
				if (array[15] != null) {
					schoolbill.setRechargemoney((BigDecimal) array[15]);
				} else {
					schoolbill.setRechargemoney(new BigDecimal(0));
				}
				if (array[16] != null) {
					schoolbill.setDrawmoney((BigDecimal) array[16]);
				} else {
					schoolbill.setDrawmoney(new BigDecimal(0));
				}
				if (array[17] != null) {
					schoolbill.setDayjiesuanmoney((BigDecimal) array[17]);
				} else {
					schoolbill.setDayjiesuanmoney(new BigDecimal(0));
				}
				if (array[18] != null) {
					schoolbill.setSystemmoney((BigDecimal) array[18]);
				} else {
					schoolbill.setSystemmoney(new BigDecimal(0));
				}
				if (array[19] != null) {
					schoolbill.setGmoney((BigDecimal) array[19]);
				} else {
					schoolbill.setGmoney(new BigDecimal(0));
				}
				if (array[20] != null) {
					schoolbill.setEndusermoney((BigDecimal) array[20]);
				} else {
					schoolbill.setEndusermoney(new BigDecimal(0));
				}
				schoolbilldaily.add(schoolbill);
			}
		}
		if (schoolbilldaily != null && schoolbilldaily.size() > 0) {
			for (int i = 0; i < schoolbilldaily.size(); i++) {
				row = sheet.createRow((Integer) index++);
				SchoolBillDaily schoolbill = schoolbilldaily.get(i);
				row.createCell((short) 0).setCellValue(i + 1);
				if (schoolbill.getSchoolname() != null) {
					row.createCell((short) 1).setCellValue(schoolbill.getSchoolname());
				}
				if (schoolbill.getCoachname() != null) {
					row.createCell((short) 2).setCellValue(schoolbill.getCoachname());
				}
				if (schoolbill.getUnfinishorder() != null) {
					row.createCell((short) 3).setCellValue(schoolbill.getUnfinishorder().toString());
				}
				if (schoolbill.getCreateorder() != null) {
					row.createCell((short) 4).setCellValue(schoolbill.getCreateorder().toString());
				}
				if (schoolbill.getFinishorder() != null) {
					row.createCell((short) 5).setCellValue(schoolbill.getFinishorder().toString());
				}
				if (schoolbill.getAllunfinishorder() != null) {
					row.createCell((short) 6).setCellValue(schoolbill.getAllunfinishorder().toString());
				}
				if (schoolbill.getBeginorderhour() != null) {
					row.createCell((short) 7).setCellValue(schoolbill.getBeginorderhour().toString());
				}
				if (schoolbill.getOrderhour() != null) {
					row.createCell((short) 8).setCellValue(schoolbill.getOrderhour().toString());
				}
				if (schoolbill.getFinishhour() != null) {
					row.createCell((short) 9).setCellValue(schoolbill.getFinishhour().toString());
				}
				if (schoolbill.getAllunfinishhour() != null) {
					row.createCell((short) 10).setCellValue(schoolbill.getAllunfinishhour().toString());
				}
				if (schoolbill.getUnfinishmoney() != null) {
					row.createCell((short) 11).setCellValue(schoolbill.getUnfinishmoney().toString());
				}
				if (schoolbill.getJiesuanmoney() != null) {
					row.createCell((short) 12).setCellValue(schoolbill.getJiesuanmoney().toString());
				}
				if (schoolbill.getDrawbackmoney() != null) {
					row.createCell((short) 13).setCellValue(schoolbill.getDrawbackmoney().toString());
				}
				if (schoolbill.getEndunfinishmoney() != null) {
					row.createCell((short) 14).setCellValue(schoolbill.getEndunfinishmoney().toString());
				}
				if (schoolbill.getMoney() != null) {
					row.createCell((short) 15).setCellValue(schoolbill.getMoney().toString());
				}
				if (schoolbill.getRechargemoney() != null) {
					row.createCell((short) 16).setCellValue(schoolbill.getRechargemoney().toString());
				}
				if (schoolbill.getDrawmoney() != null) {
					row.createCell((short) 17).setCellValue(schoolbill.getDrawmoney().toString());
				}
				if (schoolbill.getDayjiesuanmoney() != null) {
					row.createCell((short) 18).setCellValue(schoolbill.getDayjiesuanmoney().toString());
				}
				if (schoolbill.getSystemmoney() != null) {
					row.createCell((short) 19).setCellValue(schoolbill.getSystemmoney().toString());
				}
				if (schoolbill.getGmoney() != null) {
					row.createCell((short) 20).setCellValue(schoolbill.getGmoney().toString());
				}
				if (schoolbill.getEndusermoney() != null) {
					row.createCell((short) 21).setCellValue(schoolbill.getEndusermoney().toString());
				}
			}
		}
		try {
			FileOutputStream fout = new FileOutputStream(file);
			wb.write(fout);
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		filename = CommonUtils.properties.getProperty("uploadFilePath") + newfilename;
		HttpServletResponse response = ServletActionContext.getResponse();
		CommonUtils.downloadExcel(filename, "教校对账单日报", response);
	}

	/**
	 * 小巴券日报
	 * 
	 * @return
	 */
	@Action(value = "/xiaobadaily", results = { @Result(name = SUCCESS, location = "/xiaobadaily.jsp") })
	public String xiaobadaily() {
		if (CommonUtils.isEmptyString(addtime) || addtime == null) {
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			addtime = formatter.format(new Date());
		}
		if (CommonUtils.isEmptyString(starttime) || starttime == null) {
			Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH) + 1;
			starttime = year + "-" + month + "-01";
		}
		List<Object> obj = dailyService.getXiaoBaDaily(starttime, addtime);
		xiaobadaily = new ArrayList<XiaoBaDaily>();
		total = obj.size();
		int pagesize = 10;
		pageCount = (total + pagesize - 1) / pagesize;
		if (obj != null && obj.size() > 0) {
			for (Object object : obj) {
				indexnum++;
				XiaoBaDaily xiaoba = new XiaoBaDaily();
				Object[] array = (Object[]) object;
				if (array[1] != null) {
					xiaoba.setType(array[1].toString());
				}
				if (array[2] != null) {
					xiaoba.setName(array[2].toString());
				}
				if (array[3] != null) {
					xiaoba.setBeginpublishnum((Integer) array[3]);
				} else {
					xiaoba.setBeginpublishnum(0);
				}
				if (array[4] != null) {
					xiaoba.setBeginpublishmoney((Integer) array[4]);
				} else {
					xiaoba.setBeginpublishmoney(0);
				}
				if (array[5] != null) {
					xiaoba.setPublishnum((Integer) array[5]);
				} else {
					xiaoba.setPublishnum(0);
				}
				if (array[6] != null) {
					xiaoba.setPublishmoney((Integer) array[6]);
				} else {
					xiaoba.setPublishmoney(0);
				}
				if (array[7] != null) {
					xiaoba.setEndpublishnum((Integer) array[7]);
				} else {
					xiaoba.setEndpublishnum(0);
				}
				if (array[8] != null) {
					xiaoba.setEndpublishmoney((Integer) array[8]);
				} else {
					xiaoba.setEndpublishmoney(0);
				}
				if (array[9] != null) {
					xiaoba.setBeginwaitnum((Integer) array[9]);
				} else {
					xiaoba.setBeginwaitnum(0);
				}
				if (array[10] != null) {
					xiaoba.setBeginwaitmoney((Integer) array[10]);
				} else {
					xiaoba.setBeginwaitmoney(0);
				}
				if (array[11] != null) {
					xiaoba.setGetnum((Integer) array[11]);
				} else {
					xiaoba.setGetnum(0);
				}
				if (array[12] != null) {
					xiaoba.setGetmoney((Integer) array[12]);
				} else {
					xiaoba.setGetmoney(0);
				}
				if (array[13] != null) {
					xiaoba.setEndwaitnum((Integer) array[13]);
				} else {
					xiaoba.setEndwaitnum(0);
				}
				if (array[14] != null) {
					xiaoba.setEndwaitmoney((Integer) array[14]);
				} else {
					xiaoba.setEndwaitmoney(0);
				}
				if (array[15] != null) {
					xiaoba.setUsenum((Integer) array[15]);
				} else {
					xiaoba.setUsenum(0);
				}
				if (array[16] != null) {
					xiaoba.setUsemoney((Integer) array[16]);
				} else {
					xiaoba.setUsemoney(0);
				}
				if (array[17] != null) {
					xiaoba.setUnusenum((Integer) array[17]);
				} else {
					xiaoba.setUnusenum(0);
				}
				if (array[18] != null) {
					xiaoba.setUnusemoney((Integer) array[18]);
				} else {
					xiaoba.setUnusemoney(0);
				}
				if (array[19] != null) {
					xiaoba.setEndunusenum((Integer) array[19]);
				} else {
					xiaoba.setEndunusenum(0);
				}
				if (array[20] != null) {
					xiaoba.setEndunusemoney((Integer) array[20]);
				} else {
					xiaoba.setEndunusemoney(0);
				}
				if ((pageIndex * pagesize - 9) <= indexnum && indexnum <= (pageIndex * pagesize)) {
					xiaobadaily.add(xiaoba);
				}
			}
		}
		return SUCCESS;
	}

	/**
	 * 小巴券日报导出
	 * 
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	@Action(value = "/xiaoBaNoticeExport")
	public void xiaoBaNoticeExport() throws IOException {
		// 以下开始输出到EXCEL
		String newfilename = "";
		// 定义输出流，以便打开保存对话框begin
		newfilename += CommonUtils.getTimeFormat(new Date(), "yyyyMMddhhmmssSSS") + "_" + (int) (Math.random() * 100) + ".xls";
		String filename = CommonUtils.properties.get("uploadFilePath") + newfilename;
		File file = new File(filename);
		if (!file.exists()) {
			file.createNewFile();
		}
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("小巴券日报表");
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		int index = 0;
		HSSFRow row = sheet.createRow((Integer) index++);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue("小巴券日报");
		cell.setCellStyle(style);
		row = sheet.createRow((Integer) index++);
		cell = row.createCell((short) 0);
		cell.setCellValue("日期区间:");
		cell.setCellStyle(style);
		cell = row.createCell((short) 1);
		cell.setCellValue(starttime + "至" + addtime);
		cell.setCellStyle(style);
		row = sheet.createRow((Integer) index++);
		cell = row.createCell((short) 0);
		cell.setCellValue("序号");
		cell.setCellStyle(style);
		cell = row.createCell((short) 1);
		cell.setCellValue("券种");
		cell.setCellStyle(style);
		cell = row.createCell((short) 2);
		cell.setCellValue("发券人");
		cell.setCellStyle(style);
		cell = row.createCell((short) 3);
		cell.setCellValue("期初发行数量");
		cell.setCellStyle(style);
		cell = row.createCell((short) 4);
		cell.setCellValue("期初发行金额");
		cell.setCellStyle(style);
		cell = row.createCell((short) 5);
		cell.setCellValue("本期新发数量");
		cell.setCellStyle(style);
		cell = row.createCell((short) 6);
		cell.setCellValue("本期新发金额");
		cell.setCellStyle(style);
		cell = row.createCell((short) 7);
		cell.setCellValue("期末发行数量");
		cell.setCellStyle(style);
		cell = row.createCell((short) 8);
		cell.setCellValue("期末发行金额");
		cell.setCellStyle(style);
		cell = row.createCell((short) 9);
		cell.setCellValue("期初待领数量");
		cell.setCellStyle(style);
		cell = row.createCell((short) 10);
		cell.setCellValue("期初待领金额");
		cell.setCellStyle(style);
		cell = row.createCell((short) 11);
		cell.setCellValue("本期领用数量");
		cell.setCellStyle(style);
		cell = row.createCell((short) 12);
		cell.setCellValue("本期领用金额");
		cell.setCellStyle(style);
		cell = row.createCell((short) 13);
		cell.setCellValue("期末待领用数量");
		cell.setCellStyle(style);
		cell = row.createCell((short) 14);
		cell.setCellValue("期末待领用金额");
		cell.setCellStyle(style);
		cell = row.createCell((short) 15);
		cell.setCellValue("本期使用数量");
		cell.setCellStyle(style);
		cell = row.createCell((short) 16);
		cell.setCellValue("本期使用金额");
		cell.setCellStyle(style);
		cell = row.createCell((short) 17);
		cell.setCellValue("本期到期失效数量");
		cell.setCellStyle(style);
		cell = row.createCell((short) 18);
		cell.setCellValue("本期到期失效金额");
		cell.setCellStyle(style);
		cell = row.createCell((short) 19);
		cell.setCellValue("期末已领用未使用数量");
		cell.setCellStyle(style);
		cell = row.createCell((short) 20);
		cell.setCellValue("期末已领用未使用金额");
		cell.setCellStyle(style);
		List<Object> obj = dailyService.getXiaoBaDaily(starttime, addtime);
		xiaobadaily = new ArrayList<XiaoBaDaily>();
		if (obj != null && obj.size() > 0) {
			for (Object object : obj) {
				XiaoBaDaily xiaoba = new XiaoBaDaily();
				Object[] array = (Object[]) object;
				if (array[1] != null) {
					xiaoba.setType(array[1].toString());
				}
				if (array[2] != null) {
					xiaoba.setName(array[2].toString());
				}
				if (array[3] != null) {
					xiaoba.setBeginpublishnum((Integer) array[3]);
				} else {
					xiaoba.setBeginpublishnum(0);
				}
				if (array[4] != null) {
					xiaoba.setBeginpublishmoney((Integer) array[4]);
				} else {
					xiaoba.setBeginpublishmoney(0);
				}
				if (array[5] != null) {
					xiaoba.setPublishnum((Integer) array[5]);
				} else {
					xiaoba.setPublishnum(0);
				}
				if (array[6] != null) {
					xiaoba.setPublishmoney((Integer) array[6]);
				} else {
					xiaoba.setPublishmoney(0);
				}
				if (array[7] != null) {
					xiaoba.setEndpublishnum((Integer) array[7]);
				} else {
					xiaoba.setEndpublishnum(0);
				}
				if (array[8] != null) {
					xiaoba.setEndpublishmoney((Integer) array[8]);
				} else {
					xiaoba.setEndpublishmoney(0);
				}
				if (array[9] != null) {
					xiaoba.setBeginwaitnum((Integer) array[9]);
				} else {
					xiaoba.setBeginwaitnum(0);
				}
				if (array[10] != null) {
					xiaoba.setBeginwaitmoney((Integer) array[10]);
				} else {
					xiaoba.setBeginwaitmoney(0);
				}
				if (array[11] != null) {
					xiaoba.setGetnum((Integer) array[11]);
				} else {
					xiaoba.setGetnum(0);
				}
				if (array[12] != null) {
					xiaoba.setGetmoney((Integer) array[12]);
				} else {
					xiaoba.setGetmoney(0);
				}
				if (array[13] != null) {
					xiaoba.setEndwaitnum((Integer) array[13]);
				} else {
					xiaoba.setEndwaitnum(0);
				}
				if (array[14] != null) {
					xiaoba.setEndwaitmoney((Integer) array[14]);
				} else {
					xiaoba.setEndwaitmoney(0);
				}
				if (array[15] != null) {
					xiaoba.setUsenum((Integer) array[15]);
				} else {
					xiaoba.setUsenum(0);
				}
				if (array[16] != null) {
					xiaoba.setUsemoney((Integer) array[16]);
				} else {
					xiaoba.setUsemoney(0);
				}
				if (array[17] != null) {
					xiaoba.setUnusenum((Integer) array[17]);
				} else {
					xiaoba.setUnusenum(0);
				}
				if (array[18] != null) {
					xiaoba.setUnusemoney((Integer) array[18]);
				} else {
					xiaoba.setUnusemoney(0);
				}
				if (array[19] != null) {
					xiaoba.setEndunusenum((Integer) array[19]);
				} else {
					xiaoba.setEndunusenum(0);
				}
				if (array[20] != null) {
					xiaoba.setEndunusemoney((Integer) array[20]);
				} else {
					xiaoba.setEndunusemoney(0);
				}
				xiaobadaily.add(xiaoba);
			}
		}
		if (xiaobadaily != null && xiaobadaily.size() > 0) {
			for (int i = 0; i < xiaobadaily.size(); i++) {
				row = sheet.createRow((Integer) index++);
				XiaoBaDaily xiaoba = xiaobadaily.get(i);
				row.createCell((short) 0).setCellValue(i + 1);
				if (xiaoba.getType() != null) {
					row.createCell((short) 1).setCellValue(xiaoba.getType());
				}
				if (xiaoba.getName() != null) {
					row.createCell((short) 2).setCellValue(xiaoba.getName());
				}
				if (xiaoba.getBeginpublishnum() != null) {
					row.createCell((short) 3).setCellValue(xiaoba.getBeginpublishnum().toString());
				}
				if (xiaoba.getBeginpublishmoney() != null) {
					row.createCell((short) 4).setCellValue(xiaoba.getBeginpublishmoney().toString());
				}
				if (xiaoba.getPublishnum() != null) {
					row.createCell((short) 5).setCellValue(xiaoba.getPublishnum().toString());
				}
				if (xiaoba.getPublishmoney() != null) {
					row.createCell((short) 6).setCellValue(xiaoba.getPublishmoney().toString());
				}
				if (xiaoba.getEndpublishnum() != null) {
					row.createCell((short) 7).setCellValue(xiaoba.getEndpublishnum().toString());
				}
				if (xiaoba.getEndpublishmoney() != null) {
					row.createCell((short) 8).setCellValue(xiaoba.getEndpublishmoney().toString());
				}
				if (xiaoba.getBeginwaitnum() != null) {
					row.createCell((short) 9).setCellValue(xiaoba.getBeginwaitnum().toString());
				}
				if (xiaoba.getBeginwaitmoney() != null) {
					row.createCell((short) 10).setCellValue(xiaoba.getBeginwaitmoney().toString());
				}
				if (xiaoba.getGetnum() != null) {
					row.createCell((short) 11).setCellValue(xiaoba.getGetnum().toString());
				}
				if (xiaoba.getGetmoney() != null) {
					row.createCell((short) 12).setCellValue(xiaoba.getGetmoney().toString());
				}
				if (xiaoba.getEndwaitnum() != null) {
					row.createCell((short) 13).setCellValue(xiaoba.getEndwaitnum().toString());
				}
				if (xiaoba.getEndwaitmoney() != null) {
					row.createCell((short) 14).setCellValue(xiaoba.getEndwaitmoney().toString());
				}
				if (xiaoba.getUsenum() != null) {
					row.createCell((short) 15).setCellValue(xiaoba.getUsenum().toString());
				}
				if (xiaoba.getUsemoney() != null) {
					row.createCell((short) 16).setCellValue(xiaoba.getUsemoney().toString());
				}
				if (xiaoba.getUnusenum() != null) {
					row.createCell((short) 17).setCellValue(xiaoba.getUnusenum().toString());
				}
				if (xiaoba.getUnusemoney() != null) {
					row.createCell((short) 18).setCellValue(xiaoba.getUnusemoney().toString());
				}
				if (xiaoba.getEndunusenum() != null) {
					row.createCell((short) 19).setCellValue(xiaoba.getEndunusenum().toString());
				}
				if (xiaoba.getEndunusemoney() != null) {
					row.createCell((short) 20).setCellValue(xiaoba.getEndunusemoney().toString());
				}
			}
		}
		try {
			FileOutputStream fout = new FileOutputStream(file);
			wb.write(fout);
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		filename = CommonUtils.properties.getProperty("uploadFilePath") + newfilename;
		HttpServletResponse response = ServletActionContext.getResponse();
		CommonUtils.downloadExcel(filename, "小巴券日报", response);
	}

	/**
	   * 跳转日业绩报表
	   * @throws IOException
	   */
		@SuppressWarnings("deprecation")
		@Action(value = "/GotoAccountReport",results = { @Result(name = SUCCESS, location = "/accountreport.jsp") })
		public String GotoAccountReport(){
		   return SUCCESS;
		}
		 /**
		   * 日业绩报表
		   * @throws IOException
		   */
			@SuppressWarnings("deprecation")
			@Action(value = "/getaccountreportdaliy",results = { @Result(name = SUCCESS, location = "/accountreport.jsp") })
			public String getaccountreportdaliy(){
				if (CommonUtils.isEmptyString(addtime) || addtime == null) {
					DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					addtime = formatter.format(new Date());
				}
				Object object = dailyService.getAccountReport(addtime);
				Object[] obj=(Object[]) object;
				accountreportdaliy=new AccountReportDaily();
				if(obj[0]!=null)
			        accountreportdaliy.setRegistcoach((Integer)obj[0]);
				else
					accountreportdaliy.setRegistcoach(0);
				if(obj[1]!=null)
			        accountreportdaliy.setRegiststudent((Integer) obj[1]);
				else
					 accountreportdaliy.setRegiststudent(0);
				if(obj[2]!=null)
					 accountreportdaliy.setApply((Integer) obj[2]);
				else
					 accountreportdaliy.setApply(0);
				if(obj[3]!=null)
			         accountreportdaliy.setCoachbeginclass((Integer) obj[3]);
				else
					 accountreportdaliy.setCoachbeginclass(0);
				if(obj[4]!=null)
			         accountreportdaliy.setCoursetimecount((Integer) obj[4]);
				else
					accountreportdaliy.setCoursetimecount(0);
				if(obj[5]!=null)
			        accountreportdaliy.setReservedstudent((Integer) obj[5]);
				else
					accountreportdaliy.setReservedstudent(0);
				if(obj[6]!=null)
			        accountreportdaliy.setReservedcoursetime((Integer) obj[6]);
				else
					accountreportdaliy.setReservedcoursetime(0);
				if(obj[7]!=null)
			        accountreportdaliy.setOrderbycash((Integer) obj[7]);
				else
					accountreportdaliy.setOrderbycash(0);
				if(obj[8]!=null)
			        accountreportdaliy.setOrderbycoupon((Integer) obj[8]);
				else
					accountreportdaliy.setOrderbycoupon(0);
				if(obj[9]!=null)
			        accountreportdaliy.setOrderbycoin((Integer) obj[9]);
				else
					accountreportdaliy.setOrderbycoin(0);
				if(obj[10]!=null)
			        accountreportdaliy.setOrdercancel((Integer) obj[10]);
				else
					accountreportdaliy.setOrdercancel(0);
				if(obj[11]!=null)
			        accountreportdaliy.setCash((Integer) obj[11]);
				else
					accountreportdaliy.setCash(0);
				if(obj[12]!=null)
			        accountreportdaliy.setCoupon((Integer) obj[12]);
				else
					accountreportdaliy.setCoupon(0);
				if(obj[13]!=null)
			        accountreportdaliy.setCoin((Integer) obj[13]);
				else
					accountreportdaliy.setCoin(0);
			    
			   return SUCCESS;
			}
	  /**
	   * 跳转到小巴券月报表
	 * @return 
	   * @throws IOException
	   */
		@SuppressWarnings("deprecation")
		@Action(value = "/GotoCouponReportMontly",results = { @Result(name = SUCCESS, location = "/couponreportmontly.jsp") })
		public String GotoCouponReportMontly()  {
			
			return SUCCESS;
		}
		
		
		 /**
		   * 小巴券月报表
		 * @return 
		   * @throws IOException
		   */
			@SuppressWarnings("deprecation")
			@Action(value = "/GetCouponReportMontly",results = { @Result(name = SUCCESS, location = "/couponreportmontly.jsp") })
			public String GetCouponReportMontly()  {
				
				List<Object> objm = dailyService.getCouponReportMontly(CommonUtils.getDateFormat(starttime, "yyyy-MM-dd"), CommonUtils.getDateFormat(endtime, "yyyy-MM-dd"));
				couponreportmontly = new ArrayList<CouponReportMontly>();
				total = objm.size();
				int pagesize = 10;
				pageCount = (total + pagesize - 1) / pagesize;
				if (objm != null && objm.size() > 0) {
					for (Object object : objm) {
						indexnum++;
						CouponReportMontly coupontemp = new CouponReportMontly();
						Object[] array = (Object[]) object;
						if (array[1] != null) {
							coupontemp.setCoachid(array[1].toString());
						}
						if (array[2] != null) {
							coupontemp.setCoachname(array[2].toString());
						}
						if (array[3] != null) {
							coupontemp.setCoachphone(array[3].toString());
						}
						if (array[4] != null) {
							coupontemp.setSchoolname(array[4].toString());
						}
						if (array[5] != null) {
							coupontemp.setCouponnumber((Integer) array[5]);
						} else {
							coupontemp.setCouponnumber(0);
						}
						if (array[6] != null) {
							coupontemp.setCouponpaycount((Integer) array[6]);
						} else {
							coupontemp.setCouponpaycount(0);
						}				
						
						if ((pageIndex * pagesize - 9) <= indexnum && indexnum <= (pageIndex * pagesize)) {
							couponreportmontly.add(coupontemp);
						}
					}
				}
				
				return SUCCESS;
			}
			 /**
			   * 小巴券月报表详情
			 * @return 
			   * @throws IOException
			   */
				@SuppressWarnings("deprecation")
				@Action(value = "/GetCouponReportDetail",results = { @Result(name = SUCCESS, location = "/couponreportdetail.jsp") })
				public String GetCouponReportDetail()  {
					
					List<Object> objd=dailyService.getCouponReportDetail(coachid, CommonUtils.getDateFormat(starttime, "yyyy-MM-dd"), CommonUtils.getDateFormat(endtime, "yyyy-MM-dd"));
					studentlist=new ArrayList<StudentCouponDetail>();
					total = objd.size();
					int pagesize = 10;
					pageCount = (total + pagesize - 1) / pagesize;
							indexnum++;
							if (objd != null && objd.size() > 0) {
								for (Object o1 : objd) {
									Object[] olist=(Object[])o1;
									StudentCouponDetail tempstudent=new StudentCouponDetail();
									if (olist[1] != null) {
										tempstudent.setPhone(olist[1].toString());
									}
									if (olist[2] != null) {
										tempstudent.setName(olist[2].toString());
									}
									if (olist[3] != null) {
										tempstudent.setCouponusenumber((Integer)olist[3]);
									}
									else {
										tempstudent.setCouponusenumber(0);
									}
									if (olist[4] != null) {
										tempstudent.setCouponpaynumber((Integer)olist[4]);
									}
									else {
										tempstudent.setCouponpaynumber(0);
									}
									if ((pageIndex * pagesize - 9) <= indexnum && indexnum <= (pageIndex * pagesize)) {
										studentlist.add(tempstudent);
									}
								}
							}
					
					return SUCCESS;
				}
	  /**
	   * 跳转到小巴币月报表
	   * @throws IOException
	   */
		@SuppressWarnings("deprecation")
		@Action(value = "/GotoCoinReportMontly",results = { @Result(name = SUCCESS, location = "/coinreportmontly.jsp") })
		public String GotoCoinReportMontly()  {
			return SUCCESS;
		}
		 /**
		   * 小巴币月报表
		   * @throws IOException
		   */
			@SuppressWarnings("deprecation")
			@Action(value = "/GetCoinReportMontly",results = { @Result(name = SUCCESS, location = "/coinreportmontly.jsp") })
			public String GetCoinReportMontly()  {
				List<Object> objm = dailyService.getCoinReportMontly(CommonUtils.getDateFormat(starttime, "yyyy-MM-dd"), CommonUtils.getDateFormat(endtime, "yyyy-MM-dd"));
				coinreportmontly = new ArrayList<CoinReportMontly>();
				total = objm.size();
				int pagesize = 10;
				pageCount = (total + pagesize - 1) / pagesize;
				if (objm != null && objm.size() > 0) {
					for (Object object : objm) {
						indexnum++;
						CoinReportMontly cointemp = new CoinReportMontly();
						Object[] array = (Object[]) object;
						if (array[1] != null) {
							cointemp.setCoachid(array[1].toString());
						}
						if (array[2] != null) {
							cointemp.setCoachname(array[2].toString());
						}
						if (array[3] != null) {
							cointemp.setSchoolname(array[3].toString());
						}
						if (array[4] != null) {
							cointemp.setCoachphone(array[4].toString());
						}
						if (array[5] != null) {
							cointemp.setCoinnumber(new BigDecimal(array[5].toString()));
						} else {
							cointemp.setCoinnumber(new BigDecimal(0));
						}
						if (array[6] != null) {
							cointemp.setCoinnpaycount(new BigDecimal(array[6].toString()));
						} else {
							cointemp.setCoinnpaycount(new BigDecimal(0));
						}				
						
						if ((pageIndex * pagesize - 9) <= indexnum && indexnum <= (pageIndex * pagesize)) {
							coinreportmontly.add(cointemp);
						}
					}
				}
				return SUCCESS;
			}
     /**
	   * 小巴币月报表详细
	   * @throws IOException
	 */
		@SuppressWarnings("deprecation")
		@Action(value = "/GetCoinReportMontlyDetail",results = { @Result(name = SUCCESS, location = "/coinreportdetail.jsp") })
		public String GetCoinReportMontlyDetail()  {
			List<Object> objd=dailyService.getCoinReportDetail(coachid, CommonUtils.getDateFormat(starttime, "yyyy-MM-dd"), CommonUtils.getDateFormat(endtime, "yyyy-MM-dd"));
			studentcoinlist=new ArrayList<StudentCoinDetail>();
			total = objd.size();
			int pagesize = 10;
			pageCount = (total + pagesize - 1) / pagesize;
					indexnum++;
					if (objd != null && objd.size() > 0) {
						for (Object o1 : objd) {
							Object[] olist=(Object[])o1;
							StudentCoinDetail tempstudent=new StudentCoinDetail();
							if (olist[1] != null) {
								tempstudent.setPhone(olist[1].toString());
							}
							if (olist[2] != null) {
								tempstudent.setName(olist[2].toString());
							}
							if (olist[3] != null) {
								tempstudent.setCoinusenumber(new BigDecimal(olist[3].toString()));
							}
							else {
								tempstudent.setCoinusenumber(new BigDecimal(0));
							}
							if (olist[4] != null) {
								tempstudent.setCoinpaynumber(new BigDecimal(olist[4].toString()));
							}
							else {
								tempstudent.setCoinpaynumber(new BigDecimal(0));
							}
							if ((pageIndex * pagesize - 9) <= indexnum && indexnum <= (pageIndex * pagesize)) {
								studentcoinlist.add(tempstudent);
							}
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

	public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getUsertotal_regirect() {
		return usertotal_regirect;
	}

	public void setUsertotal_regirect(String usertotal_regirect) {
		this.usertotal_regirect = usertotal_regirect;
	}

	public String getUsertotal_today_regirect() {
		return usertotal_today_regirect;
	}

	public void setUsertotal_today_regirect(String usertotal_today_regirect) {
		this.usertotal_today_regirect = usertotal_today_regirect;
	}

	public String getSusercount_total() {
		return susercount_total;
	}

	public void setSusercount_total(String susercount_total) {
		this.susercount_total = susercount_total;
	}

	public String getSusercount_regirect() {
		return susercount_regirect;
	}

	public void setSusercount_regirect(String susercount_regirect) {
		this.susercount_regirect = susercount_regirect;
	}

	public String getSusercount_happy() {
		return susercount_happy;
	}

	public void setSusercount_happy(String susercount_happy) {
		this.susercount_happy = susercount_happy;
	}

	public String getCusercount() {
		return cusercount;
	}

	public void setCusercount(String cusercount) {
		this.cusercount = cusercount;
	}

	public String getUsertotal_happy() {
		return usertotal_happy;
	}

	public void setUsertotal_happy(String usertotal_happy) {
		this.usertotal_happy = usertotal_happy;
	}

	public String getUsercocal_today_regirect() {
		return usercocal_today_regirect;
	}

	public void setUsercocal_today_regirect(String usercocal_today_regirect) {
		this.usercocal_today_regirect = usercocal_today_regirect;
	}

	public String getUsercocal_regirect() {
		return usercocal_regirect;
	}

	public void setUsercocal_regirect(String usercocal_regirect) {
		this.usercocal_regirect = usercocal_regirect;
	}

	public String getUsercocal_happy() {
		return usercocal_happy;
	}

	public void setUsercocal_happy(String usercocal_happy) {
		this.usercocal_happy = usercocal_happy;
	}

	public String getCreart_order_today() {
		return creart_order_today;
	}

	public void setCreart_order_today(String creart_order_today) {
		this.creart_order_today = creart_order_today;
	}

	public String getFinish_order_today() {
		return finish_order_today;
	}

	public void setFinish_order_today(String finish_order_today) {
		this.finish_order_today = finish_order_today;
	}

	public String getTotal_order() {
		return total_order;
	}

	public void setTotal_order(String total_order) {
		this.total_order = total_order;
	}

	public String getStr_creart_order_today() {
		return str_creart_order_today;
	}

	public void setStr_creart_order_today(String str_creart_order_today) {
		this.str_creart_order_today = str_creart_order_today;
	}

	public String getStr_finish_order_today() {
		return str_finish_order_today;
	}

	public void setStr_finish_order_today(String str_finish_order_today) {
		this.str_finish_order_today = str_finish_order_today;
	}

	public String getStr_total_order() {
		return str_total_order;
	}

	public void setStr_total_order(String str_total_order) {
		this.str_total_order = str_total_order;
	}

	public List<SchoolDailyData> getSchooldailylist() {
		return schooldailylist;
	}

	public void setSchooldailylist(List<SchoolDailyData> schooldailylist) {
		this.schooldailylist = schooldailylist;
	}

	public List<CoachDailyData> getCoachdailylist() {
		return coachdailylist;
	}

	public void setCoachdailylist(List<CoachDailyData> coachdailylist) {
		this.coachdailylist = coachdailylist;
	}

	public List<StudentApplyDailyData> getStudentapplylist() {
		return studentapplylist;
	}

	public void setStudentapplylist(List<StudentApplyDailyData> studentapplylist) {
		this.studentapplylist = studentapplylist;
	}

	public List<CoachApplyDailyData> getCoachapplylist() {
		return coachapplylist;
	}

	public void setCoachapplylist(List<CoachApplyDailyData> coachapplylist) {
		this.coachapplylist = coachapplylist;
	}

	public StudentAccountDaily getStudentaccountdaily() {
		return studentaccountdaily;
	}

	public void setStudentaccountdaily(StudentAccountDaily studentaccountdaily) {
		this.studentaccountdaily = studentaccountdaily;
	}

	public CoachAccountDaily getCoachaccountdaily() {
		return coachaccountdaily;
	}

	public void setCoachaccountdaily(CoachAccountDaily coachaccountdaily) {
		this.coachaccountdaily = coachaccountdaily;
	}

	public List<SchoolBillDaily> getSchoolbilldaily() {
		return schoolbilldaily;
	}

	public void setSchoolbilldaily(List<SchoolBillDaily> schoolbilldaily) {
		this.schoolbilldaily = schoolbilldaily;
	}

	public List<XiaoBaDaily> getXiaobadaily() {
		return xiaobadaily;
	}

	public void setXiaobadaily(List<XiaoBaDaily> xiaobadaily) {
		this.xiaobadaily = xiaobadaily;
	}

	public int getIndexnum() {
		return indexnum;
	}

	public void setIndexnum(int indexnum) {
		this.indexnum = indexnum;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getSchoolname() {
		return schoolname;
	}

	public void setSchoolname(String schoolname) {
		this.schoolname = schoolname;
	}

	public List<DriveSchoolInfo> getDriveSchoollist() {
		return driveSchoollist;
	}

	public void setDriveSchoollist(List<DriveSchoolInfo> driveSchoollist) {
		this.driveSchoollist = driveSchoollist;
	}

	public List<Object> getCuserinfolist() {
		return cuserinfolist;
	}

	public void setCuserinfolist(List<Object> cuserinfolist) {
		this.cuserinfolist = cuserinfolist;
	}

	public AccountReportDaily getAccountreportdaliy() {
		return accountreportdaliy;
	}

	public void setAccountreportdaliy(AccountReportDaily accountreportdaliy) {
		this.accountreportdaliy = accountreportdaliy;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public List<CouponReportMontly> getCouponreportmontly() {
		return couponreportmontly;
	}

	public void setCouponreportmontly(List<CouponReportMontly> couponreportmontly) {
		this.couponreportmontly = couponreportmontly;
	}

	public List<CoinReportMontly> getCoinreportmontly() {
		return coinreportmontly;
	}

	public void setCoinreportmontly(List<CoinReportMontly> coinreportmontly) {
		this.coinreportmontly = coinreportmontly;
	}

	public List<StudentCouponDetail> getStudentcoupondetail() {
		return studentcoupondetail;
	}

	public void setStudentcoupondetail(List<StudentCouponDetail> studentcoupondetail) {
		this.studentcoupondetail = studentcoupondetail;
	}

	public List<StudentCoinDetail> getStudentcoindetail() {
		return studentcoindetail;
	}

	public void setStudentcoindetail(List<StudentCoinDetail> studentcoindetail) {
		this.studentcoindetail = studentcoindetail;
	}

	public String getCoachid() {
		return coachid;
	}

	public void setCoachid(String coachid) {
		this.coachid = coachid;
	}

	public List<StudentCouponDetail> getStudentlist() {
		return studentlist;
	}

	public void setStudentlist(List<StudentCouponDetail> studentlist) {
		this.studentlist = studentlist;
	}

	public List<StudentCoinDetail> getStudentcoinlist() {
		return studentcoinlist;
	}

	public void setStudentcoinlist(List<StudentCoinDetail> studentcoinlist) {
		this.studentcoinlist = studentcoinlist;
	}

	

	
	
	


}
