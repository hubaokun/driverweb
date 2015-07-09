<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.List" %>
<%@page import="com.daoshun.menu.SideMenu" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="css/page.css" type="text/css" media="screen" />
<link href="css/style.css" rel="stylesheet" type="text/css">
<link href="css/ordinary.css" rel="stylesheet" type="text/css">
<script src="js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script  type="text/javascript" src="js/complaint.js"></script>
<script type="text/javascript" src="other/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
$(function(){
	var index = $("#index").val();
	$("#left_list_"+index).show();
	var j = $("#change_id").val();
	$("#change_"+j+index).addClass('left_list_mask');
})
</script>

<style type="text/css">
.mask {
	position: fixed;
	top: 0px;
	bottom: 0px;
	left: 0px;
	right: 0px;
	background: #707070;
	opacity: 0.5;
	display: none;
}

.mask_last {
	position: fixed;
	background: #fff;
	
	width: 600px;
	height: 350px;
	margin: 0 auto;
	display: none;
}
</style>
<title>投诉详情</title>
</head>
<input type="hidden" id="index" value="6" />
<input type="hidden" id="change_id" value="0"/>
<body onload="getTop()">
<div id="content">
<jsp:include page="left.jsp"/>
<div id="content_form">

<div id="content_form_top">
	<div class="delbutton">&nbsp;详细资料</div>
</div>
<div id="content_form_table">
<table  border="0" cellspacing="0" cellpadding="0" style="width: 98%;">
<tr class="tr_th">
<th>ID</th>
<th>学员姓名</th>
<th>学员电话</th>
<th>教练姓名</th>
<th>教练电话</th>
<th>投诉类型</th>
<th>投诉原因</th>
<th>投诉类容</th>
<th>状态</th>
<th>投诉时间</th>
</tr>
<tr class="tr_td">
<td style="width: 52px;" class="border_right_bottom"><s:property value="complaint.complaintid"/></td>
<td style="width: 100px;" class="border_right_bottom"><s:property value="student.realname"/></td>
<td style="width: 100px;" class="border_right_bottom"><s:property value="student.phone"/></td>
<td style="width: 100px;" class="border_right_bottom"><s:property value="coach.realname"/></td>
<td style="width: 100px;" class="border_right_bottom"><s:property value="coach.phone"/></td>
<s:if test="complaint.type==1">
<td style="width: 100px;" class="border_right_bottom">学员投诉教练</td>
</s:if>
<s:else>
<td style="width: 100px;" class="border_right_bottom">教练投诉学员</td>
</s:else>
<td style="width: 100px;" class="border_right_bottom"><s:property value="complaint.reason"/></td>
<td style="width: 150px;overflow:hidden;" class="border_right_bottom"><s:property value="complaint.content"/></td>
<s:if test="complaint.state==0">
<td style="width: 100px;" class="border_right_bottom">未解决</td>
</s:if>
<s:elseif test="complaint.state==1">
<td style="width: 100px;" class="border_right_bottom">已解决</td>
</s:elseif>
<s:else >
<td style="width: 100px;" class="border_right_bottom">已取消</td>
</s:else>
<td style="width: 150px;" class="border_right_bottom"><s:date name="complaint.addtime" format="yyyy-MM-dd HH:mm:ss"/></td>
</tr>
</table>
</div>

<div id="content_form_top">
	<div class="delbutton">&nbsp;投诉关联订单</div>
</div>
<div id="content_form_table">
<table  border="0" cellspacing="0" cellpadding="0" style="width: 98%;">
<tr class="tr_th">
<th>订单ID</th>
<th>订单预订时间</th>
<th>开始时间</th>
<th>结束时间</th>
<th>订单状态</th>
</tr>
<tr class="tr_td">
<td  style="width:200px;" class="border_right_bottom"><s:property value="order.orderid"/></td>
<td  style="width:200px;" class="border_right_bottom"><s:date name="order.creat_time" format="yyyy-MM-dd HH:mm:ss"/></td>
<td  style="width:200px;" class="border_right_bottom"><s:date name="order.start_time" format="yyyy-MM-dd HH:mm:ss"/></td>
<td  style="width:200px;" class="border_right_bottom"><s:date name="order.end_time" format="yyyy-MM-dd HH:mm:ss"/></td>
<s:if test="order.studentstate==0||order.studentstate==2">
<td  style="width:200px;" class="border_right_bottom">未结算</td>
</s:if>
<s:elseif test="order.studentstate==3">
<td  style="width:200px;" class="border_right_bottom">已结算</td>
</s:elseif>
<s:else>
<td  style="width:200px;" class="border_right_bottom">已取消</td>
</s:else>
</tr>
</table>
</div>
<div id="content_form_top">
	<div class="delbutton">&nbsp;订单处理记录</div>
	<div class="table_edit_button" style="width: 90px;float:right;margin-top:35px;margin-right:18px;" onclick="alertBox()">
	<div class="table_button_edit_icon"></div>
	<div class="table_button_text">添加处理</div>
	</div>
	<s:if test="complaint.state==0">
	<div class="table_edit_button" style="width: 90px;float:right;margin-top:35px;margin-right: 18px;" onclick="checkComplaint(<s:property value="complaint.complaintid"/>,${index},${change_id})">
	<div class="table_button_edit_icon"></div>
	<div class="table_button_text">标记解决</div>
	</div>
	</s:if>
	<s:else>
	<div class="table_share_button" style="width: 90px;float:right;margin-top:35px;margin-right: 18px;">
	<div class="table_button_edit_icon"></div>
	<div class="table_button_text">标记解决</div>
	</div>
	</s:else>
</div>
<div id="content_form_table">
<table  border="0" cellspacing="0" cellpadding="0" style="width:98%;">
<tr class="tr_th">
<th>处理时间</th>
<th>处理内容</th>
<th>记录添加时间</th>
</tr>
<s:iterator value="complaintbooklist" var="cuser" id="list">
<tr class="tr_td">
<td  style="width:100px;" class="border_right_bottom"><s:date name="time" format="yyyy-MM-dd HH:mm:ss"/></td>
<td  style="width:200px;" class="border_right_bottom">${content}</td>
<td  style="width:100px;" class="border_right_bottom"><s:date name="addtime" format="yyyy-MM-dd HH:mm:ss"/></td>
</tr>
</s:iterator>
</table>
</div>
</div>
</div>
<!--添加投诉处理弹框 -->
	<div id="mask" class="mask"></div>
	<form action="" method="post" id="labelForm" enctype="multipart/form-data">
	<div id="mask_sec" style="position: fixed; width: 100%; height: 300px;z-index: 500">
		<div id="mask_last" class="mask_last">
		<div style="position: fixed; width: 400px; height: 250px;background: #4cc2ff;margin-left: 100px;margin-top: 50px;">
		<input type="hidden" id="index" name="index" value="${index}" />
		<input type="hidden" id="change_id" value="${change_id}"/>
		<input name="newaddtime" id="newaddtime" onclick="WdatePicker({startDate:'',dateFmt:'yyyy-MM-dd HH:mm:ss'})" type="text" placeholder="请输入处理时间" class="searchdiv" style="font-size: 15px;height: 30px;width: 185px;margin-top: 20px;margin-left: 102px;">
		<input type="hidden" id="complaintid" name="complaintid" value="<s:property value="complaint.complaintid"/>"/>
		<textarea id="book_content" name="content" style="width: 250px;height: 90px;margin: auto;margin-left: 70px;margin-top: 30px;font-size: 18px;resize: none;" placeholder="请输入处理内容"></textarea>
		<input type="button" style="width: 100px;height: 40px;margin: auto;margin-left: 70px;margin-top: 26px;margin-right:50px; font-size: 18px" value="确定" onclick="addComplaintBook()">
		<input type="button" style="width: 100px;height: 40px;margin: auto;margin-left: 180x;margin-top: -40px;font-size: 18px" value="取消" onclick="closeAlertBox()">
		</div>
		</div>
	</div>
	</form>
</body>
</html>
