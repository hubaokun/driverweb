<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<script type="text/javascript">
// 	$(function(){
// 		var width = document.documentElement.clientWidth;
// 		width = width-211;
// 		$("#content_form").css("width",width);
// 	});
</script>
<title>普通列表</title>
</head>
<body>
<div id="content">
<jsp:include page="left.jsp"/>
<div id="content_form">
<div id="content_form_top">
	<div class="delbutton">-&nbsp;删除</div>
	<div class="addbutton">+&nbsp;添加</div>
	<div class="sendmessage"><img src="imgs/common/messageicon.png" width=16px height=16px style="margin-top:14px;">&nbsp;群发消息</div>
	<form action="" method="post">
	<div class="searchbutton"><img src="imgs/common/searchicon.png" width=22px height=22px style="margin-top:9px;"></div>
	<div class="serchcontentdiv">
		<select name="" class="searchdiv">
			<option value="0">全部</option>
			<option value="1">全部</option>
			<option value="2">全部</option>
			<option value="3">全部</option>
		</select>
	</div>
	</form>
</div>
<div id="content_form_table">
<table  border="0" cellspacing="0" cellpadding="0" style="width: 98%;">
<tr class="tr_th">
<th>ID</th>
<th>账户</th>
<th>Email</th>
<th>操作</th>
</tr>
<tr class="tr_td">
<td  style="width:52px;" class="border_right_bottom">1</td>
<td  style="width:100px;" class="border_right_bottom">123456</td>
<td  style="width:100px;" class="border_right_bottom">123456@163.com</td>
<td  style="width:200px;" class="border_noright_bottom">
<div class="table_del_button"></div>
<div class="table_edit_button"></div>
<div class="table_share_button"></div>
<div class="table_message_button"></div>
<div class="table_lookdetail">查看详情</div>
</td>
</tr>
</table>
</div>
</div>
</div>
</body>
</html>