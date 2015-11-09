<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="s" uri="/struts-tags" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.List" %>
<%@page import="com.daoshun.menu.SideMenu" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/style.css" rel="stylesheet" type="text/css">
<link href="css/ordinary.css" rel="stylesheet" type="text/css">
<link href="css/daily.css" rel="stylesheet" type="text/css">
<script src="js/jquery-1.11.2.min.js"></script>
<script src="js/common.js"></script>
<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
$(function(){
	var index = $("#index").val();
	$("#left_list_"+index).show();
	var j = $("#change_id").val();
	$("#change_"+j+index).addClass('left_list_mask');
})

function search(){
	var starttime=$("#starttime").val();
	var endtime=$("#endtime").val();
	var isStartTimeNull=true;
	var str="";
	if(starttime!="" || starttime!=null)
	{
		str="?starttime="+starttime;
		isStartTimeNull=false;
	}else
	{
		isStartTimeNull=true;
	}
	
	if(endtime!="" || endtime!=null)
	{
		if(!isStartTimeNull)
		{
			str="?starttime="+starttime+"&endtime="+endtime;
		}
	}
	window.location.href = "getaccountreportdaliy.do"+str;
}
function dataExport(){
	var starttime = $("#starttime").val();
	var endtime = $("#endtime").val();
	if(endtime<starttime)
		alert("结束时间必须大于开始时间，请重新选择！");
	else
	{
		if (confirm("确认导出小巴币数据？")) {
			window.location.href="AccountReportDayMontlyExport.do?starttime="+starttime+"&endtime="+endtime;
		}
	}
}

</script>
<title>系统日报</title>
<style type="text/css">
.tables
{
	white-space:nowrap;
}
.tables th,td
{
 	border:1px solid #D7E2EF !important;
 	border-bottom:none;
}
</style>
</head>
<input type="hidden" id="index" value="10" />
<input type="hidden" id="change_id" value="0"/>
<body onload="getTop()">
<div id="content">
<jsp:include page="left.jsp"/>
<div id="content_form" style="overflow:scroll;min-height:600px;">
<div id="content_form_top">
	<div class="searchbutton" onclick="search()">
		<img src="imgs/common/searchicon.png" width="22px" height="22px" style="margin-top: 9px;">
	</div>
	<div style="width: 300px; height: 40px; border: 1px solid #cfd9df; float:left; margin-right: 36px; margin-top: 22px;">
		<div style="width: 65px; text-align: center; line-height: 40px;border-right: 1px solid #cfd9df; float:left;">开始时间</div>
		<input id="starttime" value="${starttime}" name="starttime" onclick="WdatePicker({startDate:'',dateFmt:'yyyy-MM-dd'})" style="width: 225px; height: 39px; border: 0px; padding-left: 8px; float: left;"/>
	</div>
	<div style="width: 300px; height: 40px; border: 1px solid #cfd9df; float: left; margin-right: 36px; margin-top: 22px;">
		<div style="width: 65px; text-align: center; line-height: 40px;border-right: 1px solid #cfd9df; float:left;">结束时间</div>
		<input id="endtime" value="${endtime}" name="endtime" onclick="WdatePicker({startDate:'',dateFmt:'yyyy-MM-dd'})" style="width: 225px; height: 39px; border: 0px; padding-left: 8px; float: left;"/>
	</div>
	<div class="searchbutton" style="width:80px;">
		<div class="table_button_edit_icon"></div>
		<div class="table_button_text" style="font-size: 12px;line-height: 38px;" onclick="dataExport();">数据导出</div>
	</div>
</div>

<div id="content_form_table">
	<table  border="0" cellspacing="0" cellpadding="0"
					style="width: 100%;" class="tables">
	<tr class="tr_th">
	      <th rowspan="3">日期</th>                     
	      <th rowspan="2" colspan="2">当日新增用户量</th>  
	      <th rowspan="2" colspan="1">认证通过量</th>
	      <th rowspan="2" colspan="2">教练课时情况</th>
	      <th rowspan="2" colspan="2">学员课时情况</th>
	      <th rowspan="2" colspan="3">订单数</th>
	      <th rowspan="1" colspan="6">小巴券使用情况</th>
	      <th rowspan="1" colspan="6">小巴币使用情况</th>
	      <th rowspan="1" colspan="2">教练帐户资金</th>
	      <th rowspan="1" colspan="2">学员帐户资金</th>
	   </tr>
	   <tr>
	    <th rowspan="1" colspan="3">学员使用小巴券</th>
	    <th rowspan="1" colspan="3">教练兑换小巴券</th>
	    <th rowspan="1" colspan="3">学员使用小巴币</th>
	    <th rowspan="1" colspan="3">教练兑换小巴币</th>
	    <th rowspan="2" colspan="1">教练现金订单金额</th>
	    <th rowspan="2" colspan="1">实际教练体现金额</th>
	    <th rowspan="2" colspan="1">学员充值金额</th>
	    <th rowspan="2" colspan="1">学员提现金额</th>
	   </tr>
	   <tr>									           
	      <th rowspan="1" colspan="1">教练</th>    	
	      <th rowspan="1" colspan="1">学员</th>        
	      <th rowspan="1" colspan="1">教练</th>		   
	      <th rowspan="1" colspan="1">实际开课课时</th>
	      <th rowspan="1" colspan="1">完成课时</th>    
	      <th rowspan="1" colspan="1">预约课时</th>    
	      <th rowspan="1" colspan="1">完成课时</th>    
	      <th rowspan="1" colspan="1">小巴券</th>      
	      <th rowspan="1" colspan="1">小巴币</th>      
	      <th rowspan="1" colspan="1">现金订</th>      
	      <th rowspan="1" colspan="1">平台</th>        
	      <th rowspan="1" colspan="1">驾校</th>        
	      <th rowspan="1" colspan="1">教练</th>
	      <th rowspan="1" colspan="1">平台</th>
	      <th rowspan="1" colspan="1">驾校</th>
	      <th rowspan="1" colspan="1">教练</th>
	      <th rowspan="1" colspan="1">平台</th>
	      <th rowspan="1" colspan="1">驾校</th>
	      <th rowspan="1" colspan="1">教练</th>
	      <th rowspan="1" colspan="1">平台</th>
	      <th rowspan="1" colspan="1">驾校</th>
	      <th rowspan="1" colspan="1">教练</th>
	   </tr>
	   <s:iterator value="dailyReports" var="dailyReports">
	   <tr class="tr_td">
			<td style="width: 40px;" class="border_right_bottom"><s:date name="querydate" format="MM月dd日"/></td>        
			<td style="width: 40px;" class="border_right_bottom">${coachregister}</td>    
			<td style="width: 40px;" class="border_right_bottom">${studentregister}</td>   
			<td style="width: 40px;" class="border_right_bottom">${coachcertification}</td>
			<td style="width: 40px;" class="border_right_bottom">${coachcoursetotal}</td>    
			<td style="width: 40px;" class="border_right_bottom">${coachcourseconfirm}</td>  
			<td style="width: 40px;" class="border_right_bottom">${studentbooked}</td>       
			<td style="width: 40px;" class="border_right_bottom">${studentconfirm}</td>      
			<td style="width: 40px;" class="border_right_bottom">${orderbycoupon}</td>       
			<td style="width: 40px;" class="border_right_bottom">${orderbycoin}</td>         
			<td style="width: 40px;" class="border_right_bottom">${orderbyaccount}</td>      
			<td style="width: 40px;" class="border_right_bottom">${s_couponplatform}</td>    
			<td style="width: 40px;" class="border_right_bottom">${s_couponschool }</td>
			<td style="width: 40px;" class="border_right_bottom">${s_couponcoach}</td>      
			<td style="width: 40px;" class="border_right_bottom">${c_couponplatform}</td>   
			<td style="width: 40px;" class="border_right_bottom">${c_couponschool }</td>    
			<td style="width: 40px;" class="border_right_bottom">${c_couponcoach }</td>     
			<td style="width: 40px;" class="border_right_bottom">${s_coinplatform }</td>    
			<td style="width: 40px;" class="border_right_bottom">${s_coinschool }</td>      
			<td style="width: 40px;" class="border_right_bottom">${s_coincoach }</td>       
			<td style="width: 40px;" class="border_right_bottom">${c_coinplatform }</td>    
			<td style="width: 40px;" class="border_right_bottom">${c_coinschool }</td>      
			<td style="width: 40px;" class="border_right_bottom">${c_coincoach }</td>
			<td style="width: 40px;" class="border_right_bottom">${coachorderprice }</td>
			<td style="width: 40px;" class="border_right_bottom">${coachorderprice}</td>
			<td style="width: 40px;" class="border_right_bottom">${studentrecharge}</td>
			<td style="width: 40px;" class="border_right_bottom">${studentapplycash}</td>            
		</tr>
		</s:iterator>
	</table>
		
		
</div>
</div>
</div>

</body>
</html>