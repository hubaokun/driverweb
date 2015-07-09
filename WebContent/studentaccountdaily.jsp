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
<link rel="stylesheet" href="css/page.css" type="text/css"media="screen" />
<script src="js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/page.js"></script>
<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
$(function(){
	var index = $("#index").val();
	$("#left_list_"+index).show();
	var j = $("#change_id").val();
	$("#change_"+j+index).addClass('left_list_mask');
})

function search(){
	var addtime = $("#addtime").val();
	window.location.href = "studentaccountdaily.do?addtime="+addtime;
}

function dataExport(){
	var addtime = $("#addtime").val();
	if (confirm("确认导出数据？")) {
		window.location.href="studentAccountNoticeExport.do?addtime="+addtime;
	}
}
</script>
<title>学员账户日报</title>
</head>
<input type="hidden" id="index" value="10" />
<input type="hidden" id="change_id" value="3"/>
<body onload="getTop()">
<div id="content">
<jsp:include page="left.jsp"/>
<div id="content_form">
<div id="content_form_top">
	<div class="searchbutton" onclick="search()">
		<img src="imgs/common/searchicon.png" width="22px" height="22px" style="margin-top: 9px;">
	</div>
	<div class="searchbutton" style="width:80px;">
		<div class="table_button_edit_icon"></div>
		<div class="table_button_text" style="font-size: 12px;line-height: 38px;" onclick="dataExport();">数据导出</div>
	</div>
	<div style="width: 300px; height: 40px; border: 1px solid #cfd9df; float: right; margin-right: 36px; margin-top: 22px;">
		<div style="width: 65px; text-align: center; line-height: 40px;border-right: 1px solid #cfd9df; float:left;">搜索时间</div>
		<input id="addtime" value="${addtime }" name="addtime" onclick="WdatePicker({startDate:'',dateFmt:'yyyy-MM-dd'})" style="width: 225px; height: 39px; border: 0px; padding-left: 8px; float: left;"/>
	</div>
</div>
<div id="content_form_table">
				<table border="0" cellspacing="0" cellpadding="0"
					style="width: 100%;">
					<tr class="tr_th">
						<th>期初余额</th>
						<th>当日充值金额</th>
						<th>当日取款申请金额</th>
						<th>期末订单冻结金额</th>
						<th>期末(可用)余额</th>
						<th>期初订单冻结金额</th>
						<th>当日订单冻结金额</th>
						<th>当日订单解冻金额</th>
						<th>当日订单完成支付金额</th>
						<th>期末订单冻结金额</th>
					</tr>
					<tr class="tr_td">
						<td style="width: 100px;" class="border_right_bottom"><s:property value="studentaccountdaily.beginbalance"/></td>
						<td style="width: 150px;" class="border_right_bottom"><s:property value="studentaccountdaily.rechargemoney"/></td>
						<td style="width: 150px;" class="border_right_bottom"><s:property value="studentaccountdaily.askformoney"/></td>
						<td style="width: 150px;" class="border_right_bottom"><s:property value="studentaccountdaily.freezemoney"/></td>
						<td style="width: 150px;" class="border_right_bottom"><s:property value="studentaccountdaily.endbalance"/></td>
						<td style="width: 150px;" class="border_right_bottom"><s:property value="studentaccountdaily.beginfreezemoney"/></td>
						<td style="width: 150px;" class="border_right_bottom"><s:property value="studentaccountdaily.dayfreezemoney"/></td>
						<td style="width: 150px;" class="border_right_bottom"><s:property value="studentaccountdaily.thawmoney"/></td>
						<td style="width: 150px;" class="border_right_bottom"><s:property value="studentaccountdaily.paymoney"/></td>
						<td style="width: 150px;" class="border_right_bottom"><s:property value="studentaccountdaily.freezemoney"/></td>
					</tr>
				</table>
</div>
</div>
</div>

</body>
</html>