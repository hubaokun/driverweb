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
<script type="text/javascript" src="systemnotice/js/systemnotice.js"></script>
<script type="text/javascript">
$(function(){
	var index = $("#index").val();
	$("#left_list_"+index).show();
	var j = $("#change_id").val();
	$("#change_"+j+index).addClass('left_list_mask');
	
})
</script>
<title>发布新通知</title>
</head>
<input type="hidden" id="index" value="4" />
<input type="hidden" id="change_id" value="0"/>
<body>
<div id="content">
<jsp:include page="left.jsp"/>
<div id="content_form">
<div id="content_form_top">
	 <div class="addbutton" style="float: right;margin-right: 20px;" onclick="goback()">后退</div>
</div>




<form action="">





<div style="width: 500px;height: 60px;margin: auto;margin-left: 35%;margin-top: 40px">
					<span style="width: 100px; position: relative; top: -7px; margin-left: 0px; font-size: 15px">教练</span>
					<input  type="radio" style="width: 50px; height: 25px; margin-left: -10px; margin-top: 5px; font-size: 18px; text-align: center;"value="1" name="usertype" checked="checked" onclick="showsettype()"/>                                                                                                                                                                                                              
					<span style="width: 100px; position: relative; top: -7px; margin-left: 0px;font-size: 15px">学员</span>
					<input  type="radio" style="width: 50px; height: 25px; margin-left: -10px; margin-top: 5px; font-size: 18px; text-align: center;"value="2" name="usertype" onclick="showsettype()"/>
					<span style="width: 100px; position: relative; top: -7px; margin-left: 0px;font-size: 15px">教练所教所有学员</span>
					<input  type="radio" style="width: 50px; height: 25px; margin-left: -10px; margin-top: 5px; font-size: 18px; text-align: center;"value="3" name="usertype" onclick="unshowsettype()"/> 
					<span style="width: 100px; position: relative; top: -7px; margin-left: 0px;font-size: 15px">教练未完成的学员</span>
					<input  type="radio" style="width: 50px; height: 25px; margin-left: -10px; margin-top: 5px; font-size: 18px; text-align: center;"value="4" name="usertype" onclick="unshowsettype()" />


</div>


<div id="settypediv" style="width: 500px;height: 60px;margin: auto;margin-left: 35%">

					<span style="width: 100px; position: relative; top: -7px; margin-left: 0px;font-size: 15px">群发</span>
					<input  type="radio" style="width: 50px; height: 25px; margin-left: -10px; margin-top: 5px; font-size: 18px; text-align: center;"value="0" name="settype" checked="checked" onclick="unshowsearchs()" /> 
					<span style="width: 100px; position: relative; top: -7px; margin-left: 0px;font-size: 15px">单发</span>
					<input  type="radio" style="width: 50px; height: 25px; margin-left: -10px; margin-top: 5px; font-size: 18px; text-align: center;"value="1" name="settype" onclick="showsearchs()"/>


</div>

<div id="bigsearch" style="width: 400px;height: 60px;margin: auto;margin-top: 20px;margin-left: 35%;display: none;" >
<div class="searchbutton" style="float:right;margin-top:0px"><img src="imgs/common/searchicon.png" width=22px height=22px style="margin-top:9px;" onclick="searchuser()"></div>
<div class="serchcontentdiv" style="width: 308px;margin-top: 0px;float: left;">
	<input class="searchdiv" value="关键字" type="text" style="width: 100px;text-align: center;font-size: 12px;"readonly="readonly">
	<input id="searchname" class="searchdiv" type="text" style="width: 202px;">
</div>
</div>



<!-- 如果有搜索，那么则显示该div -->

<div id="searchuserdiv" style="width: 500px;margin: auto;margin-top: -5px;border: 1px solid #cfd9df;min-height: 100px;margin-left: 35%;display: none;">
<input type="hidden" type='radio' name='singleuser' value="0" checked="checked">


</div>


<!--通知类型 category -->
<div style="width: 500px;height: 40px;margin: auto;margin-top: 40px;margin-left: 35%">
<div class="serchcontentdiv" style="width: 500px;margin-top: 0px;float: left;">
	<input class="searchdiv" value="通知标题" type="text" style="width: 100px;text-align: center;font-size: 12px;"readonly="readonly">
	<input	id="category" class="searchdiv"  type="text" style="width: 394px;">
	</div>
</div>

<!-- 通知内容 content  -->
<div style="width: 500px;height: 200px;margin: auto;margin-top: 15px;margin-left: 35%">
<textarea id="contents" style="width: 494px;height: 200px;border: 1px solid #cfd9df; resize:none;font-family: 微软雅黑;font-size: 14px" onclick="">
</textarea>
</div>


<!-- 发送或重置  -->
<div style="width: 400px;height:50px;margin: auto;margin-top: 40px;margin-left: 39%">
<div class="addbutton" style="float: left;" onclick="setnotice()">发送</div>
<div class="addbutton" style="float:right;" onclick="reload()">取消</div>
</div>



</form>
</div>
</div>
</body>
</html>