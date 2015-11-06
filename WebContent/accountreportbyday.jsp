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
	var addtime = $("#addtime").val();
	window.location.href = "getaccountreportdaliybyday.do?addtime="+addtime;
}


</script>
<title>系统日报</title>
</head>
<input type="hidden" id="index" value="10" />
<input type="hidden" id="change_id" value="0"/>
<body onload="getTop()">
<div id="content">
<jsp:include page="left.jsp"/>
<div id="content_form">
<div id="content_form_top">
	<div class="searchbutton" onclick="search()">
		<img src="imgs/common/searchicon.png" width="22px" height="22px" style="margin-top: 9px;">
	</div>
	<div style="width: 300px; height: 40px; border: 1px solid #cfd9df; float: right; margin-right: 36px; margin-top: 22px;">
		<div style="width: 65px; text-align: center; line-height: 40px;border-right: 1px solid #cfd9df; float:left;">报表时间</div>
		<input id="addtime" value="${addtime }" name="addtime" onclick="WdatePicker({startDate:'',dateFmt:'yyyy-MM-dd'})" style="width: 225px; height: 39px; border: 0px; padding-left: 8px; float: left;"/>
	</div>
</div>
<div id="content_form_table">
	<table  border="0" cellspacing="0" cellpadding="0"
					style="width: 100%;">
	<tr class="tr_th">
	      <th rowspan="2">日期</th>
	      <th rowspan="2">注册教练</th>
	      <th rowspan="2">注册学员</th>
	      <th rowspan="2">一键报名</th>
	      <th colspan="4">日课程</th>
	      <th colspan="4">订单数</th>
	      <th colspan="3">日交易额</th>
	   </tr>
	   <tr>
	      <th>在线教练数</th>
	      <th>总发布课时</th>
	      <th>预约学员</th>
	      <th>预约课时</th>
	      <th>现金订单</th>
	      <th>学时券订单数</th>
	      <th>小巴币订单数</th>
	      <th>取消订单数</th>
	      <th>现金</th>
	      <th>小巴券</th>
	      <th>小巴币</th>
	   </tr>
	   <tr class="tr_td">
		<td style="width: 40px;" class="border_right_bottom">${addtime}</td>
		<td style="width: 40px;" class="border_right_bottom">${accountreportdaliy.getRegistcoach()}</td>
		<td style="width: 40px;" class="border_right_bottom">${accountreportdaliy.getRegiststudent() }</td>
		<td style="width: 40px;" class="border_right_bottom">${accountreportdaliy.getApply() }</td>
		<td style="width: 40px;" class="border_right_bottom">${accountreportdaliy.getCoachbeginclass() }</td>
		<td style="width: 40px;" class="border_right_bottom">${accountreportdaliy.getCoursetimecount() }</td>
		<td style="width: 40px;" class="border_right_bottom">${accountreportdaliy.getReservedstudent() }</td>
		<td style="width: 40px;" class="border_right_bottom">${accountreportdaliy.getReservedcoursetime() }</td>
		<td style="width: 40px;" class="border_right_bottom">${accountreportdaliy.getOrderbycash() }</td>
		<td style="width: 40px;" class="border_right_bottom">${accountreportdaliy.getOrderbycoupon() }</td>
		<td style="width: 40px;" class="border_right_bottom">${accountreportdaliy.getOrderbycoin() }</td>
		<td style="width: 40px;" class="border_right_bottom">${accountreportdaliy.getOrdercancel() }</td>
		<td style="width: 40px;" class="border_right_bottom">${accountreportdaliy.getCash() }</td>
		<td style="width: 40px;" class="border_right_bottom">${accountreportdaliy.getCoupon() }</td>
		<td style="width: 40px;" class="border_right_bottom">${accountreportdaliy.getCoin() }</td>
		</tr>   
	</table>
		
		
</div>
</div>
</div>

</body>
</html>