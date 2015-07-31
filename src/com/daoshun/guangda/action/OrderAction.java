package com.daoshun.guangda.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.daoshun.common.CommonUtils;
import com.daoshun.common.ErrException;
import com.daoshun.common.QueryResult;
import com.daoshun.guangda.pojo.CuserInfo;
import com.daoshun.guangda.pojo.OrderInfo;
import com.daoshun.guangda.pojo.OrderPrice;
import com.daoshun.guangda.pojo.OrderRecordInfo;
import com.daoshun.guangda.pojo.SuserInfo;
import com.daoshun.guangda.service.ISOrderService;

@ParentPackage("default")
@Controller
@Scope("prototype")
public class OrderAction extends BaseAction {
	
	private static final long serialVersionUID = 2534791731119144729L;
	
	@Resource
	private ISOrderService orderService;
	
	private long total;
	
	private Integer pageIndex = 1;
	
	private Integer pageCount = 0;
	
	private List<OrderInfo> orderlist;
	
	private List<OrderRecordInfo> orderrecordlist;
	
	private List<OrderPrice> orderpricelist;
	
	private int index;
	
	private int change_id;
	
	private OrderInfo order;
	
	private CuserInfo coach;
	
	private SuserInfo student;
	
	private Integer orderid;
	
	private String coachphone;
	
	private String studentphone;
	
	private String startminsdate;
	
	private String startmaxsdate;
	
	private String endminsdate;
	
	private String endmaxsdate;
	
	private Integer state;
	
	private Integer ordertotal;
	
	private String inputordertotal;
	
	private Integer ishavacomplaint;
	
	/**
	 * 获取教练任务列表
	 * @return
	 */
	@Action(value = "/getOrderList", results = { @Result(name = SUCCESS, location = "/order.jsp") })
	public String getOrderList() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		int pagesize = CommonUtils.parseInt(String.valueOf(session.getAttribute("pagesize")), 10);
		QueryResult<OrderInfo> result = orderService.getOrderList(coachphone,studentphone,startminsdate,startmaxsdate,endminsdate,endmaxsdate,state,ordertotal,inputordertotal,ishavacomplaint, pageIndex, pagesize);
		total = result.getTotal();
		orderlist = result.getDataList();
		for(int i=0; i< orderlist.size();i++)
		{
			OrderInfo o = orderlist.get(i);
			Calendar now = Calendar.getInstance();
			
			Calendar starttime = Calendar.getInstance();
			starttime.setTime(o.getStart_time());
			
			if (now.before(starttime))
				o.setCan_cancel(1);
		}
		pageCount = ((int) result.getTotal() + pagesize - 1) / pagesize;
		if (pageIndex > 1) {
			if (orderlist == null || orderlist.size() == 0) {
				pageIndex--;
				getOrderList();
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 获取订单详情
	 * @return
	 */
	@Action(value = "/getOrderDetail", results = { @Result(name = SUCCESS, location = "/orderdetail.jsp") })
	public String getOrderDetail() {
		order = orderService.getOrderById(orderid);
		orderrecordlist = orderService.getOrderRecord(orderid);
		orderpricelist = orderService.getOrderPriceList(orderid);
		if(order!=null){
			coach = orderService.getCoachById(order.getCoachid());
			student = orderService.getStudentById(order.getStudentid());
		}
		return SUCCESS;
	}
	
	/**
	 * 根据关键字筛选订单信息
	 * 
	 * @return
	 */
	@Action(value = "/getOrderBySearch", results = { @Result(name = SUCCESS, location = "/order.jsp") })
	public String getOrderBySearch() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		int pagesize = CommonUtils.parseInt(String.valueOf(session.getAttribute("pagesize")), 10);
		QueryResult<OrderInfo> result = orderService.getOrderByKeyword(coachphone,studentphone,startminsdate,startmaxsdate,endminsdate,endmaxsdate,state,ordertotal,inputordertotal,ishavacomplaint, pageIndex, pagesize);
		total = result.getTotal();
		orderlist = result.getDataList();
		pageCount = ((int) result.getTotal() + pagesize - 1) / pagesize;
		if (pageIndex > 1) {
			if (orderlist == null || orderlist.size() == 0) {
				pageIndex--;
				getOrderBySearch();
			}
		}
		return SUCCESS;
	}
	

	@Action(value = "/cancelOrder", results = { @Result(name = SUCCESS, location = "/order.jsp") })
	public String getCoachDetail() throws ErrException {
		HttpSession session = ServletActionContext.getRequest().getSession();
		String orderid = request.getParameter("orderid");
//		  orderid = String.valueOf(session.getAttribute("orderid"));
		String studentid = request.getParameter("studentid");//String.valueOf(session.getAttribute("studentid"));
		CommonUtils.validateEmpty(studentid);
		CommonUtils.validateEmpty(orderid);
		int code = orderService.cancelOrder(studentid, orderid);
		if (code == 0) {
			return SUCCESS;
		} 
		return "";
	}
	@SuppressWarnings("deprecation")
	@Action(value = "/orderDataExport")
	public void orderDataExport() throws IOException {
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
	        HSSFSheet sheet = wb.createSheet("订单表");
	        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
	        int index = 0;
	        HSSFRow row = sheet.createRow((int) 0);
	        // 第四步，创建单元格，并设置值表头 设置表头居中  
	        HSSFCellStyle style = wb.createCellStyle();  
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式  
	        HSSFCell cell = row.createCell((short) 0);
	        cell.setCellValue("订单ID");
	        cell.setCellStyle(style);  
	        cell = row.createCell((short) 1);  
	        cell.setCellValue("学生ID");  
	        cell.setCellStyle(style);  
	        cell = row.createCell((short) 2);  
	        cell.setCellValue("教练ID");  
	        cell.setCellStyle(style);  
	        cell = row.createCell((short) 3);  
	        cell.setCellValue("生成时间");  
	        cell.setCellStyle(style);
	        cell = row.createCell((short) 4);  
	        cell.setCellValue("日期");  
	        cell.setCellStyle(style); 
	        cell = row.createCell((short) 5);  
	        cell.setCellValue("开始时间");  
	        cell.setCellStyle(style); 
	        cell = row.createCell((short) 6);  
	        cell.setCellValue("结束时间");  
	        cell.setCellStyle(style); 
	        cell = row.createCell((short) 7);  
	        cell.setCellValue("预定小时数");  
	        cell.setCellStyle(style); 
	        cell = row.createCell((short) 8);  
	        cell.setCellValue("订单总价");  
	        cell.setCellStyle(style);
	        cell = row.createCell((short) 9);  
	        cell.setCellValue("平台抽成");  
	        cell.setCellStyle(style); 
	        cell = row.createCell((short) 10);  
	        cell.setCellValue("驾校抽成");  
	        cell.setCellStyle(style);
	        cell = row.createCell((short) 11);  
	        cell.setCellValue("订单小巴券抵掉部分");  
	        cell.setCellStyle(style); 
	        cell = row.createCell((short) 12);  
	        cell.setCellValue("订单是否可取消");  
	        cell.setCellStyle(style); 
	        cell = row.createCell((short) 13);  
	        cell.setCellValue("经度");  
	        cell.setCellStyle(style); 
	        cell = row.createCell((short) 14);  
	        cell.setCellValue("纬度");  
	        cell.setCellStyle(style);
	        cell = row.createCell((short) 15);  
	        cell.setCellValue("详细地址");
	        cell.setCellStyle(style);
	        index++;
	        orderlist = orderService.getAllOrder();
	        if(orderlist!=null&&orderlist.size()>0){
	        	for(int i = 0;i<orderlist.size();i++){
	                row = sheet.createRow((int) index++);
	                OrderInfo order = orderlist.get(i);
	                row.createCell((short) 0).setCellValue((int)order.getOrderid());  
	                row.createCell((short) 1).setCellValue((int)order.getStudentid());  
	                row.createCell((short) 2).setCellValue((int)order.getCoachid());
	                String creattime = CommonUtils.getTimeFormat(order.getCreat_time(),"yyyy-MM-dd HH:mm:ss");
	                row.createCell((short) 3).setCellValue(creattime);
	                row.createCell((short) 4).setCellValue(order.getDate());
	                String starttime = CommonUtils.getTimeFormat(order.getStart_time(),"yyyy-MM-dd HH:mm:ss");
	                String endtime = CommonUtils.getTimeFormat(order.getEnd_time(),"yyyy-MM-dd HH:mm:ss");
	                row.createCell((short) 5).setCellValue(starttime);
	                row.createCell((short) 6).setCellValue(endtime);
	                row.createCell((short) 7).setCellValue(order.getTime());
	                row.createCell((short) 8).setCellValue(order.getTotal().toString());
	                row.createCell((short) 9).setCellValue(order.getPrice_out1().toString());
	                row.createCell((short) 10).setCellValue(order.getPrice_out2().toString());
	                row.createCell((short) 11).setCellValue(order.getDelmoney());
	                if(order.getCancancel()==0){
	                	row.createCell((short) 12).setCellValue("可以");
	                }else{
	                	row.createCell((short) 12).setCellValue("不可以");
	                }
	                row.createCell((short) 13).setCellValue(order.getLongitude());
	                row.createCell((short) 14).setCellValue(order.getLatitude());
	                row.createCell((short) 15).setCellValue(order.getDetail());
	                row = sheet.createRow((int) index++);
	                cell = row.createCell((short) 0);
	    	        cell.setCellValue("主键ID");
	    	        cell.setCellStyle(style);  
	    	        cell = row.createCell((short) 1);  
	    	        cell.setCellValue("订单ID");  
	    	        cell.setCellStyle(style);  
	    	        cell = row.createCell((short) 2);  
	    	        cell.setCellValue("操作类型");  
	    	        cell.setCellStyle(style);  
	    	        cell = row.createCell((short) 3);  
	    	        cell.setCellValue("经度");  
	    	        cell.setCellStyle(style);
	    	        cell = row.createCell((short) 4);  
	    	        cell.setCellValue("纬度");  
	    	        cell.setCellStyle(style); 
	    	        cell = row.createCell((short) 5);  
	    	        cell.setCellValue("地址详细");  
	    	        cell.setCellStyle(style);
	    	        cell = row.createCell((short) 6);  
	    	        cell.setCellValue("添加时间");  
	    	        cell.setCellStyle(style); 
	    	        cell = row.createCell((short) 7);  
	    	        cell.setCellValue("评价ID");  
	    	        cell.setCellStyle(style);
	    	        cell = row.createCell((short) 8);  
	    	        cell.setCellValue("投诉ID");  
	    	        cell.setCellStyle(style);
	    	        orderrecordlist = orderService.getOrderRecord(order.getOrderid());
	    	        if(orderrecordlist!=null&&orderrecordlist.size()>0){
	    	        	for(int j = 0;j<orderrecordlist.size();j++){
	    	        		row= sheet.createRow((int)  index++);
	    	        		OrderRecordInfo record = orderrecordlist.get(j);
	    	        		row.createCell((short) 0).setCellValue((int)record.getRecordid());
	    	        		row.createCell((short) 1).setCellValue((int)record.getOrderid());
	    	        		if(record.getOperation()==1){
	    	        			row.createCell((short) 2).setCellValue("学员确认上车");
	    	        		}else if(record.getOperation()==2){
	    	        			row.createCell((short) 2).setCellValue("学员确认下车");
	    	        		}else if(record.getOperation()==3){
	    	        			row.createCell((short) 2).setCellValue("教练确认上车");
	    	        		}else if(record.getOperation()==4){
	    	        			row.createCell((short) 2).setCellValue("教练确认下车");
	    	        		}else if(record.getOperation()==5){
	    	        			row.createCell((short) 2).setCellValue("学员取消订单");
	    	        		}else if(record.getOperation()==6){
	    	        			row.createCell((short) 2).setCellValue("学员评价");
	    	        		}else if(record.getOperation()==7){
	    	        			row.createCell((short) 2).setCellValue("学员投诉");
	    	        		}else if(record.getOperation()==8){
	    	        			row.createCell((short) 2).setCellValue("教练评价");
	    	        		}else if(record.getOperation()==9){
	    	        			row.createCell((short) 2).setCellValue("教练投诉");
	    	        		}
	    	        		row.createCell((short) 3).setCellValue(record.getLongitude());
	    	        		row.createCell((short) 4).setCellValue(record.getLatitude());
	    	        		row.createCell((short) 5).setCellValue(record.getDetail());
	    	        		String addtime = CommonUtils.getTimeFormat(record.getAddtime(),"yyyy-MM-dd HH:mm:ss");
	    	        		row.createCell((short) 6).setCellValue(addtime);
	    	        		row.createCell((short) 7).setCellValue((int)record.getEvaluationid());
	    	        		row.createCell((short) 8).setCellValue((int)record.getComplaintid());
	    	        	}
	    	        }
	                row = sheet.createRow((int) index++);
	                cell = row.createCell((short) 0);
	    	        cell.setCellValue("主键ID");
	    	        cell.setCellStyle(style);  
	    	        cell = row.createCell((short) 1);  
	    	        cell.setCellValue("订单ID");  
	    	        cell.setCellStyle(style);  
	    	        cell = row.createCell((short) 2);  
	    	        cell.setCellValue("小时");  
	    	        cell.setCellStyle(style);  
	    	        cell = row.createCell((short) 3);  
	    	        cell.setCellValue("经度");
	    	        cell.setCellStyle(style);
	    	        cell = row.createCell((short) 4);  
	    	        cell.setCellValue("纬度");  
	    	        cell.setCellStyle(style); 
	    	        cell = row.createCell((short) 5);  
	    	        cell.setCellValue("地址详细");  
	    	        cell.setCellStyle(style);
	    	        cell = row.createCell((short) 6);  
	    	        cell.setCellValue("科目名");  
	    	        cell.setCellStyle(style); 
	    	        cell = row.createCell((short) 7);  
	    	        cell.setCellValue("教练单价");  
	    	        cell.setCellStyle(style);
	    	        orderpricelist = orderService.getOrderPriceList(order.getOrderid());
	    	        if(orderpricelist!=null&&orderpricelist.size()>0){
	    	        	for(int j = 0;j<orderpricelist.size();j++){
	    	        		row= sheet.createRow((int)  index++);
	    	        		OrderPrice price = orderpricelist.get(j);
	    	        		row.createCell((short) 0).setCellValue((int)price.getRecordid());
	    	        		row.createCell((short) 1).setCellValue((int)price.getOrderid());
	    	        		row.createCell((short) 2).setCellValue(price.getHour());
	    	        		row.createCell((short) 3).setCellValue(price.getLongitude());
	    	        		row.createCell((short) 4).setCellValue(price.getLatitude());
	    	        		row.createCell((short) 5).setCellValue(price.getDetail());
	    	        		row.createCell((short) 6).setCellValue(price.getSubject());
	    	        		row.createCell((short) 7).setCellValue(price.getPrice().toString());
	    	        	}
	    	        }
	        	}
	        }
	        try  
	        {  
	            FileOutputStream fout = new FileOutputStream(file);  
	            wb.write(fout);  
	            fout.close();  
	        }  
	        catch (Exception e)  
	        {  
	            e.printStackTrace();  
	        } 
		filename = CommonUtils.properties.getProperty("uploadFilePath") + newfilename;
		HttpServletResponse response = ServletActionContext.getResponse();
		CommonUtils.downloadExcel(filename, "小巴订单信息表", response);
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

	public List<OrderInfo> getOrderlist() {
		return orderlist;
	}

	public void setOrderlist(List<OrderInfo> orderlist) {
		this.orderlist = orderlist;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public List<OrderRecordInfo> getOrderrecordlist() {
		return orderrecordlist;
	}

	public void setOrderrecordlist(List<OrderRecordInfo> orderrecordlist) {
		this.orderrecordlist = orderrecordlist;
	}

	public List<OrderPrice> getOrderpricelist() {
		return orderpricelist;
	}

	public void setOrderpricelist(List<OrderPrice> orderpricelist) {
		this.orderpricelist = orderpricelist;
	}

	public OrderInfo getOrder() {
		return order;
	}

	public void setOrder(OrderInfo order) {
		this.order = order;
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

	public Integer getOrderid() {
		return orderid;
	}

	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}

	public String getCoachphone() {
		return coachphone;
	}

	public void setCoachphone(String coachphone) {
		this.coachphone = coachphone;
	}

	public String getStudentphone() {
		return studentphone;
	}

	public void setStudentphone(String studentphone) {
		this.studentphone = studentphone;
	}

	public String getStartminsdate() {
		return startminsdate;
	}

	public void setStartminsdate(String startminsdate) {
		this.startminsdate = startminsdate;
	}

	public String getStartmaxsdate() {
		return startmaxsdate;
	}

	public void setStartmaxsdate(String startmaxsdate) {
		this.startmaxsdate = startmaxsdate;
	}

	public String getEndminsdate() {
		return endminsdate;
	}

	public void setEndminsdate(String endminsdate) {
		this.endminsdate = endminsdate;
	}

	public String getEndmaxsdate() {
		return endmaxsdate;
	}

	public void setEndmaxsdate(String endmaxsdate) {
		this.endmaxsdate = endmaxsdate;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getOrdertotal() {
		return ordertotal;
	}

	public void setOrdertotal(Integer ordertotal) {
		this.ordertotal = ordertotal;
	}

	public String getInputordertotal() {
		return inputordertotal;
	}

	public void setInputordertotal(String inputordertotal) {
		this.inputordertotal = inputordertotal;
	}

	public Integer getIshavacomplaint() {
		return ishavacomplaint;
	}

	public void setIshavacomplaint(Integer ishavacomplaint) {
		this.ishavacomplaint = ishavacomplaint;
	}

	public int getChange_id() {
		return change_id;
	}

	public void setChange_id(int change_id) {
		this.change_id = change_id;
	}
	
	
}
