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

<title>教练车型列表</title>
</head>
<input type="hidden" id="index" value="${index}" />
<input type="hidden" id="change_id" value="${change_id}"/>
<body onload="getTop()" style="min-width: 1500px">
<div id="content">
<jsp:include page="left.jsp"/>
<div id="content_form">
<div id="content_form_top">
	<div class="addbutton" onclick="showaddmodel()">+&nbsp;添加</div>
	
</div>
<div id="content_form_table" style="min-width: 1500px">
<table  border="0" cellspacing="0" cellpadding="0" style="width: 98%;">
<tr class="tr_th">
<th>车型ID</th>
<th>车型名称</th>
<th>操作</th>
</tr>
<s:iterator value="teachcarInfolist" var="teachcar">
<tr class="tr_td">
<td  style="width:50px;min-width: 200px" class="border_right_bottom">${modelid}</td>
<td  style="width:300px;min-width: 200px" class="border_right_bottom">${modelname }</td>
<td  style="width:120px;min-width: 120px" class="border_noright_bottom">

<div class="table_edit_button" style="cursor: auto; background: #fff;">
<div class="table_button_edit_icon"></div>
<div class="table_button_text" ></div>
</div>

<div class="table_del_button">
<div class="table_button_del_icon"></div>
<div class="table_button_text" onclick="delteachcarmodel('${modelname}')">删除</div>
</div>
<div class="table_edit_button">
<div class="table_button_edit_icon"></div>
<div class="table_button_text" onclick="showeditmodel('${modelname}')">修改</div>
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
		<input id="modelname" type="text" style="width: 218px;height: 40px;margin: auto;margin-left: 41px;margin-top: 45px;font-size: 18px;text-align: center;"  placeholder="请输入要添加的车型号"/>
		<input type="button" style="width: 100px;height: 40px;margin: auto;margin-left: 50px;margin-top: 40px;font-size: 18px" value="确定" onclick="addteachcarmodel()">
		<input type="button" style="width: 100px;height: 40px;margin: auto;margin-left: 180x;margin-top: -40px;font-size: 18px" value="取消" onclick="unshowaddmodel()">
		</div>
		</div>
	</div>
	
	
	<div id="edit" class="edit"></div>
	<div id="edit_sec" style="position: fixed; width: 100%; height: 300px;z-index: 300;">
		<div id="edit_last" class="edit_last">
		<div style="position: fixed; width: 300px; height: 200px;background: #4cc2ff;margin-left: 100px;margin-top: 50px;">
		<input id="oldmodelname" type="text" style="width: 218px;height: 40px;margin: auto;margin-left: 41px;margin-top: 13px;font-size: 18px;text-align: center;" disabled="disabled"/>
		<input id="editmodelname" type="text" style="width: 218px;height: 40px;margin: auto;margin-left: 41px;margin-top: 20px;font-size: 18px;text-align: center;" placeholder="请输入修改名称" />
		<input type="button" style="width: 100px;height: 40px;margin: auto;margin-left: 50px;margin-top: 25px;font-size: 18px" value="确定" onclick="editteachcarmodel()">
		<input type="button" style="width: 100px;height: 40px;margin: auto;margin-left: 180x;margin-top: -40px;font-size: 18px" value="取消" onclick="unshoweditmodel()">
		</div>
		</div>
	</div>
</body>
</html>