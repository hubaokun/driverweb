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
<script src="js/jquery-1.11.2.min.js"></script>
<script src="systemconfig/js/systemconfig.js"></script>
<script src="js/common.js"></script>
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
	width: 500px;
	height: 300px;
	margin: 0 auto;
		display: none;
}
.edit {
	position: fixed;
	top: 0px;
	bottom: 0px;
	left: 0px;
	right: 0px;
	background: #707070;
	opacity: 0.3;
	display: none;
}

.edit_last {
	position: fixed;
	background: #fff;
	width: 500px;
	height: 300px;
	margin: 0 auto;
	display: none;
}
</style>

<title>投诉原因列表</title>
</head>
<input type="hidden" id="index" value="${index}" />
<input type="hidden" id="change_id" value="${change_id}"/>
<body onload="getTop()">
<div id="content">
<jsp:include page="left.jsp"/>
<div id="content_form">
<div id="content_form_top">
	<div class="addbutton" onclick="showaddmodel()">+&nbsp;添加</div>
	<div class="addbutton" onclick="search()" style="float: right;margin-right: 20px">&nbsp;显示所有</div>
	<div class="addbutton" onclick="search(1)" style="float: right;">&nbsp;教练</div>
	<div class="addbutton" onclick="search(2)" style="float: right;">&nbsp;学员</div>
	
</div>
<div id="content_form_table">
<table  border="0" cellspacing="0" cellpadding="0" style="width: 98%;">
<tr class="tr_th">
<th>原因ID</th>
<th>原因内容</th>
<th>投诉来源</th>
<th>操作</th>
</tr>
<s:iterator value="complaintSetInfolist" var="complaintset">
<tr class="tr_td">
<td  style="width:10%;" class="border_right_bottom">${setid}</td>
<td  style="width:45%;" class="border_right_bottom">${content}</td>
<s:if test="type==1">
<td  style="width:25%;" class="border_right_bottom">教练</td>
</s:if>
<s:else>
<td  style="width:25%;" class="border_right_bottom">学员</td>
</s:else>
<td  style="width:20%;" class="border_noright_bottom">

<div class="table_edit_button" style="cursor: auto; background: #fff;">
<div class="table_button_edit_icon"></div>
<div class="table_button_text" ></div>
</div>

<div class="table_del_button">
<div class="table_button_del_icon"></div>
<div class="table_button_text" onclick="delcomplaint('${setid}')">删除</div>
</div>
<div class="table_edit_button">
<div class="table_button_edit_icon"></div>
<div class="table_button_text" onclick="showeditset('${content}','${setid}')">修改</div>
</div>
<div class="table_edit_button" style="cursor: auto; background: #fff;">
<div class="table_button_edit_icon"></div>
<div class="table_button_text" ></div>
</div>

</td>
</tr>
</s:iterator>
</table>

</div>
</div>
</div>
<!-- 添加新准教车型弹框 -->
	<div id="mask" class="mask"></div>
	<div id="mask_sec" style="position: fixed; width: 100%; height: 300px;z-index: 300;">
		<div id="mask_last" class="mask_last">
		<div style="position: fixed; width: 300px; height: 200px;background: #4cc2ff;margin-left: 100px;margin-top: 50px;">
		<input id="contents" type="text" style="width: 218px;height: 40px;margin: auto;margin-left: 41px;margin-top: 20px;font-size: 18px;text-align: center;"  placeholder="请输入添加的投诉原因内容"/>
		<input id="type" type="text" style="width: 250px;height: 40px;margin: auto;margin-left: 23px;margin-top: 20px;font-size: 18px;text-align: center;"  placeholder="使用者：1代表教练 2代表学员" onchange="typeIsNum()"/>
		
		<input type="button" style="width: 100px;height: 40px;margin: auto;margin-left: 50px;margin-top: 20px;font-size: 18px" value="确定" onclick="addcomplaint()">
		<input type="button" style="width: 100px;height: 40px;margin: auto;margin-left: 180x;margin-top: -40px;font-size: 18px" value="取消" onclick="unshowaddmodel()">
		</div>
		</div>
	</div>
	
	
	<div id="edit" class="edit"></div>
	<div id="edit_sec" style="position: fixed; width: 100%; height: 300px;z-index: 300;">
		<div id="edit_last" class="edit_last">
		<div style="position: fixed; width: 300px; height: 200px;background: #4cc2ff;margin-left: 100px;margin-top: 50px;">
		<input id="setid" type="hidden">
		<input id="contentss" type="text" style="width: 218px;height: 40px;margin: auto;margin-left: 41px;margin-top: 13px;font-size: 18px;text-align: center;" disabled="disabled"/>
		<input id="newcontent" type="text" style="width: 218px;height: 40px;margin: auto;margin-left: 41px;margin-top: 20px;font-size: 18px;text-align: center;" placeholder="请输入修改内容" />
		<input type="button" style="width: 100px;height: 40px;margin: auto;margin-left: 50px;margin-top: 25px;font-size: 18px" value="确定" onclick="editcomplaint()">
		<input type="button" style="width: 100px;height: 40px;margin: auto;margin-left: 180x;margin-top: -40px;font-size: 18px" value="取消" onclick="unshoweditset()">
		</div>
		</div>
	</div>
</body>
</html>