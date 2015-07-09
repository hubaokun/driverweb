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
<script type="text/javascript">
var msg="${requestScope.message}";
if(msg!=""){
   alert(msg);
}
$(function(){
	var index = $("#index").val()
	$("#left_list_"+index).show();
	
})
function dataToLead(){
	if (confirm("确认导入教练信息？")) {
		$("#labelForm").attr("action", "dataToLead.do").submit();
	}
}
function back(){
	window.location.href="getCoachlist.do";
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
<th colspan="2">
	教练信息导入
</th>
</tr>
<tr class="tr_td">
<td style="width: 200px;">导入文件</td>
<td><input style="float:left;height:43px;" name="addVersion" type=file id="addVersion"/></td>
</tr>
<tr class="tr_td">
<td colspan="2">
<div style="width:200px; margin:auto;">
<div style="width: 80px;float:left;height: 30px;line-height: 28px;background-color: #57BCF3;cursor: pointer;" onclick="dataToLead()">确认导入</div>
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