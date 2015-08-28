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
	window.location.href = "getaccountreportdaliy.do?addtime="+addtime;
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
	<div style="width: 100%; height: auto; margin: 0 auto;">
		<div class="daily_top">
			<div class="daily_top_center" style="width: 5%;">日期</div>
			<div class="daily_top_center" style="width: 5%;">注册教练</div>
			<div class="daily_top_center" style="width: 5%;">注册学员</div>
			<div class="daily_top_center" style="width: 5%;">一键报名</div>
			<div class="daily_top_center" style="width: 32%;">日课程</div>
			<div class="daily_top_center" style="width: 32%;">订单数</div>
			<div class="daily_top_center" style="width: 15%;">日交易额</div>
		</div>
		<div class="daily_second">  
			<div class="daily_second_center" style="width: 5%;">${addtime}</div>
			<div class="daily_second_center" style="width: 5%;">${accountreportdaliy.getRegistcoach()}</div>
			<div class="daily_second_center" style="width: 5%;">${accountreportdaliy.getRegiststudent() }</div>
			<div class="daily_second_center" style="width: 5%;">${accountreportdaliy.getApply() }</div>
			<div class="daily_second_center" style="width: 8%;">${accountreportdaliy.getCoachbeginclass() }</div>
			<div class="daily_second_center" style="width: 8%;">${accountreportdaliy.getCoursetimecount() }</div>
			<div class="daily_second_center" style="width: 8%;">${accountreportdaliy.getReservedstudent() }</div>
			<div class="daily_second_center" style="width: 8%;">${accountreportdaliy.getReservedcoursetime() }</div>
			<div class="daily_second_center" style="width: 8%;">${accountreportdaliy.getOrderbycash() }</div>
			<div class="daily_second_center" style="width: 8%;">${accountreportdaliy.getOrderbycoupon() }</div>
			<div class="daily_second_center" style="width: 8%;">${accountreportdaliy.getOrderbycoin() }</div>
			<div class="daily_second_center" style="width: 8%;">${accountreportdaliy.getOrdercancel() }</div>
			<div class="daily_second_center" style="width: 5%;">${accountreportdaliy.getCash() }</div>
			<div class="daily_second_center" style="width: 5%;">${accountreportdaliy.getCoupon() }</div>
			<div class="daily_second_center" style="width: 5%;">${accountreportdaliy.getCoin() }</div>
		</div>
		
		
	</div>
</div>
</div>
</div>

</body>
</html>