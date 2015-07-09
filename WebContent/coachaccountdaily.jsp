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
	window.location.href = "coachaccountdaily.do?addtime="+addtime;
}

function dataExport(){
	var addtime = $("#addtime").val();
	if (confirm("确认导出数据？")) {
		window.location.href="coachAccountNoticeExport.do?addtime="+addtime;
	}
}
</script>
<title>教练账户日报</title>
</head>
<input type="hidden" id="index" value="10" />
<input type="hidden" id="change_id" value="4"/>
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
						<th>当日结算订单金额</th>
						<th>期末系统余额</th>
						<th>保证金</th>
						<th>期末教练可取金额</th>
						<th>期初未完成订单金额</th>
						<th>当日结算订单金额</th>
						<th>投诉订单退款金额</th>
						<th>期末未完成订单金额</th>
						<th>当日完成未结算订单金额</th>
					</tr>
					<tr class="tr_td">
						<td style="width: 100px;" class="border_right_bottom"><s:property value="coachaccountdaily.beginbalance"/></td>
						<td style="width: 150px;" class="border_right_bottom"><s:property value="coachaccountdaily.rechargemoney"/></td>
						<td style="width: 150px;" class="border_right_bottom"><s:property value="coachaccountdaily.askformoney"/></td>
						<td style="width: 150px;" class="border_right_bottom"><s:property value="coachaccountdaily.jiesuanmoney"/></td>
						<td style="width: 150px;" class="border_right_bottom"><s:property value="coachaccountdaily.systembalance"/></td>
						<td style="width: 100px;" class="border_right_bottom"><s:property value="coachaccountdaily.gmoney"/></td>
						<td style="width: 150px;" class="border_right_bottom"><s:property value="coachaccountdaily.canusemoney"/></td>
						<td style="width: 150px;" class="border_right_bottom"><s:property value="coachaccountdaily.unfinishordermoney"/></td>
						<td style="width: 150px;" class="border_right_bottom"><s:property value="coachaccountdaily.jiesuanmoney"/></td>
						<td style="width: 150px;" class="border_right_bottom"><s:property value="coachaccountdaily.drawbackmoney"/></td>
						<td style="width: 150px;" class="border_right_bottom"><s:property value="coachaccountdaily.endunfinishmoney"/></td>
						<td style="width: 150px;" class="border_right_bottom"><s:property value="coachaccountdaily.finishmoney"/></td>
					</tr>
				</table>
</div>
</div>
</div>

</body>
</html>