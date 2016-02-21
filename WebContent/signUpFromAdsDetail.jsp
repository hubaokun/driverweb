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
<script  type="text/javascript" src="coach/js/coachdetail.js"></script>
<script type="text/javascript" src="systemconfig/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
$(function(){
	var index = $("#index").val();
	$("#left_list_"+index).show();
	var j = $("#change_id").val();
	$("#change_"+j+index).addClass('left_list_mask');
	
	$("#nav").css("min-height","1260px");
})
</script>

<style type="text/css">
.change {
	position: fixed;
	top: 0px;
	bottom: 0px;
	left: 0px;
	right: 0px;
	background: #707070;
	opacity: 0.5;
	display: none;
}

.change_last {
	position: fixed;

	width: 500px;
	height: 300px;
	margin: 0 auto;
	display: none;
}
</style>

<title>报名详情</title>
</head>

<body onload="getTop()" style="min-width: 1580px">
<div id="content" >
<jsp:include page="left.jsp" />
<div id="content_form">

	<input type="hidden" id="index" value="1"  name="index"/>
	<input type="hidden" id="change_id" value="3" name="change_id"/>
	<input type="hidden" value="${pageCount }" id="pageSize" />
	<input type="hidden" value="${pageIndex }" id="pageIndex" />
	<input type="hidden" id="state" value="${state}"  name="state"/>
	<div id="content_form_top">
		<div class="delbutton">&nbsp;基本资料</div>
		<a class="addbutton" style="float: left;margin-right: 10px;" href="getSignUpInfoListFromAds.do">返&nbsp;回</a>
	</div>
	
	<div id="content_form_table">
	<table  border="0" cellspacing="0" cellpadding="0" style="margin-left: 27px;width:60%;">
	
	<tr class="tr_th">
		<th>ID</th>
		<th>学员姓名</th>
		<th style="width:150px;">电话号码</th>
		<th>广告名称</th>
		<th>报名来源</th>
		<th>当前状态</th>
	</tr>
	
	<tr class="tr_td">
	
		<td  style="width:150px;min-width:150px;background: #e5e5e5" class="border_right_bottom"><input name="studentid" id="studentid" value="${signUpDetail.signupId}" style="text-align: center;width:150px;background: #e5e5e5;height: 35px;font-size:18px"  readonly="readonly"/></td>
		<td  style="width:150px;min-width:150px;background: #e5e5e5" class="border_right_bottom"><input name="editrealname" id="realname" value="${signUpDetail.name }" style="text-align: center;width:150px;height: 35px;font-size:18px;background: #e5e5e5"  readonly/></td>
		<td  style="width:150px;min-width:150px;background: #e5e5e5" class="border_right_bottom"><input value="${signUpDetail.phone }" style="text-align: center;width:150px;background: #e5e5e5;height: 35px;font-size:18px"  readonly="readonly"/></td>
		<td  style="width:150px;min-width:150px;background: #e5e5e5" class="border_right_bottom"><input value="${signUpDetail.adsTypeName }" style="text-align: center;background: #e5e5e5;height: 35px;font-size:18px"  readonly="readonly"/></td>
		<td  style="width:150px;min-width:150px;background: #e5e5e5" class="border_right_bottom"><input value="${signUpDetail.source }" style="text-align: center;width:150px;background: #e5e5e5;height: 35px;font-size:18px"  readonly="readonly"/></td>
		<td  style="width:80px;min-width:150px;background: #e5e5e5" class="border_right_bottom"><input name="editbirthday" value="${signUpDetail.state==0?"未处理":"已处理"}" style="text-align: center;width:150px;height: 35px;font-size:18px;background: #e5e5e5" readonly /></td>
	</tr>
	
	</table>
	
	<!-- 添加跟进记录 -->
	<div id="addcontentform">
	<div id="content_form_top">
	</div>
	
	<form action="processSignUp.do" method="post" id="contentform_submit">
		<input type="hidden" id="signUpId" name="signUpId" value="${signUpDetail.signupId}" />
		<textarea  id="processinfo" name="processinfo"  rows="8" cols="100" style="margin-left: 27px;margin-top:10px;width:60%;float:left;" placeholder="添加处理备注">${signUpDetail.processInfo}</textarea>
		<input type="submit" style="float: left;margin-left: 30px; margin-bottom:30px;clear:both;" class="addbutton" value="处理"/>
	</form>
	
	</div>
	
	<!-- 历史跟进记录 -->
	<div id="content_form_top">
	</div>
	<table  border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;margin-left: 27px;width:60%;">
	<tr class="tr_th">
		<th>处理人</th>
		<th>跟进时间</th>
	</tr>
	<tr class="tr_td">
			<td style="width:95px;min-width: 95px;background: #e5e5e5" class="border_right_bottom"><input name="editdealpeople" id="dealpeople" value="${signUpDetail.processor.realname}" style="text-align: center;width:95px;height: 35px;background: #e5e5e5;font-size:18px" readonly /></td>
			<td style="width:150px;min-width: 150px;background: #e5e5e5" class="border_right_bottom"><input name="editdealtime" value="<s:date name="signUpDetail.processTime" format="yyyy-MM-dd HH:mm:ss" />" style="text-align: center;width:175px;height: 35px;background: #e5e5e5;font-size:18px" readonly/></td>
	</tr>
	</table>
	
	
	</div>
	


</div>
</div>

</body>
</html>