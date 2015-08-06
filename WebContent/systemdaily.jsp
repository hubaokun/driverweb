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
	window.location.href = "systemdaily.do?addtime="+addtime;
}

function dataExport(){
	var addtime = $("#addtime").val();
	if (confirm("确认导出数据？")) {
		window.location.href="systemNoticeExport.do?addtime="+addtime;
	}
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
	<div style="width: 80%; height: auto; margin: 0 auto;">
		<div class="daily_top">
			<div class="daily_top_center" style="width: 10%;">序号</div>
			<div class="daily_top_center" style="width: 19%;">项目</div>
			<div class="daily_top_center" style="width: 20%;">当日注册人数</div>
			<div class="daily_top_center" style="width: 25%;">当日累计注册人数</div>
			<div class="daily_top_right" style="width: 25%;">当日活跃人数</div>
		</div>
		<div class="daily_second">
			<div class="daily_second_center" style="width: 10%;">1</div>
			<div class="daily_second_center" style="width: 19%;">用户总数</div>
			<div class="daily_second_center" style="width: 20%;">${usertotal_today_regirect }</div>
			<div class="daily_second_center" style="width: 25%;">${usertotal_regirect }</div>
			<div class="daily_second_right" style="width: 25%;">${usertotal_happy }</div>
		</div>
		<div class="daily_second">
			<div class="daily_second_center" style="width: 10%;">2</div>
			<div class="daily_second_center" style="width: 19%;">教练端</div>
			<div class="daily_second_center" style="width: 20%;">${susercount_total }</div>
			<div class="daily_second_center" style="width: 25%;">${susercount_regirect }</div>
			<div class="daily_second_right" style="width: 25%;">${susercount_happy }</div>
		</div>
		<div class="daily_second">
			<div class="daily_second_center" style="width: 10%;">3</div>
			<div class="daily_second_center" style="width: 19%;">学员端</div>
			<div class="daily_second_center" style="width: 20%;">${usercocal_today_regirect }</div>
			<div class="daily_second_center" style="width: 25%;">${usercocal_regirect }</div>
			<div class="daily_second_right" style="width: 25%;">${usercocal_happy }</div>
		</div>
		<div class="daily_top">
			<div class="daily_top_center" style="width: 10%;">序号</div>
			<div class="daily_top_center" style="width: 19%;">项目</div>
			<div class="daily_top_center" style="width: 20%;">当日生成订单数</div>
			<div class="daily_top_center" style="width: 25%;">当日完成订单数</div>
			<div class="daily_top_right" style="width: 25%;">累计未完成订单数</div>
		</div>
		<div class="daily_second">
			<div class="daily_second_center" style="width: 10%;">1</div>
			<div class="daily_second_center" style="width: 19%;">教练端</div>
			<div class="daily_second_center" style="width: 20%;">${creart_order_today }</div>
			<div class="daily_second_center" style="width: 25%;">${finish_order_today }</div>
			<div class="daily_second_right" style="width: 25%;">${total_order }</div>
		</div>
		<div class="daily_second">
			<div class="daily_second_center" style="width: 10%;">2</div>
			<div class="daily_second_center" style="width: 19%;">学员端</div>
			<div class="daily_second_center" style="width: 20%;">${str_creart_order_today }</div>
			<div class="daily_second_center" style="width: 25%;">${finish_order_today }</div>
			<div class="daily_second_right" style="width: 25%;">${total_order }</div>
		</div>
		<div class="daily_top">
			<div class="daily_top_center" style="width: 10%;">序号</div>
			<div class="daily_top_center" style="width: 19%;">项目</div>
			<div class="daily_top_center" style="width: 20%;">当日预约学时数</div>
			<div class="daily_top_center" style="width: 25%;">当日完成学时数</div>
			<div class="daily_top_right" style="width: 25%;">累计未完成学时数</div>
		</div>
		<div class="daily_second">
			<div class="daily_second_center" style="width: 10%;">1</div>
			<div class="daily_second_center" style="width: 19%;">教练端</div>
			<div class="daily_second_center" style="width: 20%;">${str_creart_order_today }</div>
			<div class="daily_second_center" style="width: 25%;">${str_finish_order_today }</div>
			<div class="daily_second_right" style="width: 25%;">${str_total_order }</div>
		</div>
		<div class="daily_second">
			<div class="daily_second_center" style="width: 10%;">2</div>
			<div class="daily_second_center" style="width: 19%;">学员端</div>
			<div class="daily_second_center" style="width: 20%;">${str_creart_order_today }</div>
			<div class="daily_second_center" style="width: 25%;">${str_finish_order_today }</div>
			<div class="daily_second_right" style="width: 25%;">${str_total_order }</div>
		</div>
	</div>
</div>
</div>
</div>

</body>
</html>