<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/style.css" rel="stylesheet" type="text/css">
<link href="css/ordinary.css" rel="stylesheet" type="text/css">
<script src="js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="js/dataexport.js"></script>
<script src="js/dataexport.js"></script>
<script src="js/common.js"></script>
<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
var msg="${requestScope.message}";
if(msg!=""){
   alert(msg);
}
$(function(){
	var index = $("#index").val()
	$("#left_list_"+index).show();
	
})
function dataExport(){
	var startdate=$("#datastartdate").val();
	var enddate=$("#dataenddate").val();
	if(startdate>enddate)
		alert("开始时间不能大于结束时间");
	else
	{
		if (confirm("确认导出订单信息？")) {
			window.location.href="orderDataExport.do?datastartdate="+startdate+"&dataenddate="+enddate;
		}
	}
	   
}
function back(){
	window.history.back();
	   
}
</script>
<title>教练信息导出</title>
</head>
<input type="hidden" id="index" value="1" />
<body onload="getSelfTop()">
<div id="content">
<jsp:include page="left.jsp"/>
<div id="content_form">
<div id="content_form_table">
<form action="" method="post" id="labelForm" enctype="multipart/form-data">
<table  border="1" cellspacing="0" cellpadding="0" style="width: 40%;">
<tr class="tr_th">
<th colspan="5">
	订单信息导出
</th>
</tr>
<tr class="tr_td">
<td style="width: 200px;">导出时间选择</td>
<td>开始时间</td>
<td><input id="datastartdate" onclick="WdatePicker({startDate:'',dateFmt:'yyyy-MM-dd'})" type="text" class="searchdiv" style="width: 135px;text-align: center;font-family: 微软雅黑;" value="" ></td>
<td>结束时间</td>
<td><input id="dataenddate" onclick="WdatePicker({startDate:'',dateFmt:'yyyy-MM-dd'})" type="text" class="searchdiv" style="width: 135px;text-align: center;font-family: 微软雅黑;" value="" ></td>
</tr>
<tr class="tr_td">
<td colspan="5">
<div style="width:200px; margin:auto;">
<div style="width: 80px;float:left;height: 30px;line-height: 28px;background-color: #57BCF3;cursor: pointer;" onclick="dataExport()">确认导出</div>
<div style="width: 80px;float:left;height: 30px;line-height: 28px;background-color: #57BCF3;margin-left: 20px;cursor: pointer;" onclick="back()">取消</div>
</div>
</td>
</tr>
</table>
</form>
</div>
</div>
</div>
</body>
</html>