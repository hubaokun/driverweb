package com.daoshun.guangda.action;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.daoshun.common.CommonUtils;
import com.daoshun.common.QueryResult;
import com.daoshun.guangda.pojo.CoachStudentInfo;
import com.daoshun.guangda.pojo.CuserInfo;
import com.daoshun.guangda.pojo.SuserInfo;
import com.daoshun.guangda.service.ICUserService;
import com.daoshun.guangda.service.ISUserService;

public class UserRelationAction extends BaseAction {
	
	private static final long serialVersionUID = 2534791731119144729L;
	
	@Resource
	private ISUserService suserService;
	
	@Resource
	private ICUserService cuserService;
	
	private int index;

	private int change_id;

	private Integer pageIndex = 1;

	private Integer pageCount = 0;
	
	private long total;
	
	private String coachPhone;
	
	private String studentPhone;
	
	private List<CoachStudentInfo> coachstudentlist;
	
	private String addVersionFileName;
	
	private File addVersion; // 上传的版本文件
	
	/**
	 * 根据关键字筛选学员信息
	 * 
	 * @return
	 */
	@Action(value = "/getCoachStudent", results = { @Result(name = SUCCESS, location = "/userrelation.jsp") })
	public String getCoachStudent() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		int pagesize = CommonUtils.parseInt(String.valueOf(session.getAttribute("pagesize")), 10);
		QueryResult<CoachStudentInfo> result = suserService.getCoachStudentByKeyword(coachPhone, studentPhone, pageIndex, pagesize);
		total = result.getTotal();
		coachstudentlist = result.getDataList();
		pageCount = ((int) result.getTotal() + pagesize - 1) / pagesize;
		if (pageIndex > 1) {
			if (coachstudentlist == null || coachstudentlist.size() == 0) {
				pageIndex--;
				getCoachStudent();
			}
		}
		return SUCCESS;
	}
	
	@Action(value = "/jumpCoachStudentToLead", results = { @Result(name = SUCCESS, location = "/coachstudenttolead.jsp") })
	public String jumpCoachStudentToLead() {
		return SUCCESS;
	}
	
	@Action(value = "/coachStudentDataToLead", results = { @Result(name = SUCCESS, location = "/coachstudenttolead.jsp") })
	public String dataToLead() {
		String realpath = request.getSession().getServletContext().getRealPath("/");
		String extension = addVersionFileName.substring(addVersionFileName.lastIndexOf(".")).toLowerCase();
		String message = null;
		if (!extension.equals(".xls")) {
			message = "请上传excel文件!如果是2007以上版本excel请另存为(*.xls)文件再上传";
		} else {
			String severPicName = CommonUtils.uploadApp(addVersion, realpath, addVersionFileName);
			String file = realpath + severPicName;
			WorkbookSettings workbookSettings = new WorkbookSettings();
			workbookSettings.setEncoding("ISO-8859-1");
			Workbook book;
			try {
				book = Workbook.getWorkbook(new File(file), workbookSettings);// 鑾峰彇excel
				Sheet sheet = book.getSheet(0);
				int row = sheet.getRows();
				for (int i = 1; i <= row; i++) {
					String coachPhone = sheet.getCell(0, i).getContents();
					String studentPhone = sheet.getCell(1, i).getContents();
					double money = CommonUtils.parseDouble(sheet.getCell(2, i).getContents(), 0);
					int hour = CommonUtils.parseInt(sheet.getCell(3, i).getContents(), 0);
					CoachStudentInfo coachstudent = new CoachStudentInfo();
					int coachid = 0;
					int studentid = 0;
					//
					if (coachPhone != null && !"".equals(coachPhone)) {
						CuserInfo coach = cuserService.getCuserByPhone(coachPhone);
						if(coach!=null){
							coachid = coach.getCoachid();
							coachstudent.setCoachid(coachid);
						}else{
							continue;
						}
					} else {
						continue;
					}
					//
					if (studentPhone != null && !"".equals(studentPhone)) {
						SuserInfo student = suserService.getUserByPhone(studentPhone);
						if(student!=null){
							studentid = student.getStudentid();
							coachstudent.setStudentid(studentid);
						}else{
							continue;
						}
					} else {
						continue;
					}
					CoachStudentInfo coachstudent1 = suserService.getCoachStudentByPhone(coachid, studentid);
					if(coachstudent1!=null){
						continue;
					}
					coachstudent.setMoney(new BigDecimal(money));
					coachstudent.setHour(hour);
					suserService.addCoachStudent(coachstudent);
				}
			} catch (Exception e) {
			}
			message = "添加成功";
			CommonUtils.deleteFile(file);
		}
		request.setAttribute("message", message);
		return SUCCESS;
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

	public void setTotal(long total) {
		this.total = total;
	}

	public String getCoachPhone() {
		return coachPhone;
	}

	public void setCoachPhone(String coachPhone) {
		this.coachPhone = coachPhone;
	}

	public String getStudentPhone() {
		return studentPhone;
	}

	public void setStudentPhone(String studentPhone) {
		this.studentPhone = studentPhone;
	}

	public List<CoachStudentInfo> getCoachstudentlist() {
		return coachstudentlist;
	}

	public void setCoachstudentlist(List<CoachStudentInfo> coachstudentlist) {
		this.coachstudentlist = coachstudentlist;
	}

	public String getAddVersionFileName() {
		return addVersionFileName;
	}

	public void setAddVersionFileName(String addVersionFileName) {
		this.addVersionFileName = addVersionFileName;
	}

	public File getAddVersion() {
		return addVersion;
	}

	public void setAddVersion(File addVersion) {
		this.addVersion = addVersion;
	}
	
}
