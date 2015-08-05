<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.daoshun.menu.SideMenu"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/style.css" rel="stylesheet" type="text/css">
<link href="css/ordinary.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="css/page.css" type="text/css" media="screen" />
<script src="js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/page.js"></script>
<script type="text/javascript" src="js/complaint.js"></script>
<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
function toresetstate()
{
	//发送异步请求
	jQuery.post("resetCoachCoursestate.do", "", showRet, 'json');
	alert("重置中。。。。");
}
function showRet(obj)
{
        if(obj.code=="0"){
        	alert("重置教练开课状态成功");
        }else{
        	alert("重置教练开课状态失败");
        }
    
}

</script>

<title>教练开课状态</title>
</head>
<audio src="imgs/common/beep.mp3"
	preload="auto" id="audio" loop="loop"></audio>
<input type="hidden" id="index" name="index" value="${index}" />
<input type="hidden" id="hidstate" value="${state}" />
<input type="hidden" id="change_id" value="${change_id}"/>
<body onload="getTop()">
	<div id="content">
		<jsp:include page="left.jsp" />
		<div id="content_form">
			<div id="content_form_top">
					
					<div class="addbutton" onclick="toresetstate()">重置教练开课状态</div>
					
			</div>
			<div id="citys" style="font-size:15px;padding-left:40px;padding-top:10px;">
			</div>
	</div>
</body>
</html>