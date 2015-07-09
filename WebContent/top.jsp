<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.List" %>
<%@page import="com.daoshun.menu.SideMenu" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="css/top.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
// 	$(function(){
// 		var width = document.documentElement.clientWidth;
// 		$("#header").css("width",width);
// 	});
</script>
<% SideMenu instance = SideMenu.getInstance();%>
<title></title>
</head>
<body>
<div id="header">
<div class="logo" style="margin-left: 15px;"><a href="#"><img src="imgs/logo.png" style="margin-top: 5px;margin-left: -20px;"/></a><span style="position: relative;top: -15px;left: 10px;"><%=instance.getAppname() %>后台管理</span></div>
<div class="nav_top">
		<div class="logout" onclick="logout()">退出</div>
		<div style="background:#89d7ff;width:2px;height:15px;margin-top: 28px;float: right;"></div>
		<div class="oparetor"><b>${session.loginname}</b></div>
<%--    <p><a href="#">当前用户：<s:property value="#session.loginname"/></a><span>|</span><a href="#" onclick="logout()">退出</a></p> --%>
<%-- <div class="date"><img class="pic_san" src="imgs/date.png"/><a href="#"><s:date name="#session.date" format="yyyy-MM-dd" /></a></div>    --%>
</div>
</div>
</body>
<script type="text/javascript">
function logout(){
	$.post("logout.do");
	window.location.href="login.jsp";
}
</script>
</html>