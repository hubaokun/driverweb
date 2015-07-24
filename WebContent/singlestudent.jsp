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

<title>学员个人详情</title>
</head>

<body onload="getTop()" style="min-width: 1580px">
<div id="content" >
<jsp:include page="left.jsp"/>
<div id="content_form">

<form action="" id="singlestudentForm" enctype="multipart/form-data" method="post">
<input type="hidden" id="index" value="1"  name="index"/>
<input type="hidden" id="change_id" value="2" name="change_id"/>
<input type="hidden" value="${pageCount }" id="pageSize" />
<input type="hidden" value="${pageIndex }" id="pageIndex" />
<div id="content_form_top">
	<div class="delbutton" style="cursor:default;">&nbsp;基本资料</div>
		<div class="addbutton" style="float: left;margin-left: 10px;" onclick="editsinglesuser()">保存修改</div>
		<div class="addbutton" style="float: right;margin-right: 10px;" onclick="gobacksuser()">返&nbsp;回</div>
</div>

<div id="content_form_table">
<table  border="0" cellspacing="0" cellpadding="0" style="margin-left: 27px;width: 98%;">

<tr class="tr_th">
<th>ID</th>
<th>真实姓名</th>
<th>电话号码</th>
<th>性别</th>
<th>出生日期</th>
<th>城市</th>
</tr>

<tr class="tr_td">

<td  style="width:150px;min-width:150px;background: #e5e5e5" class="border_right_bottom"><input name="studentid" id="studentid" value="${suser.studentid }" style="text-align: center;width:150px;background: #e5e5e5;height: 35px;font-size:18px"  readonly="readonly"/></td>
<td  style="width:150px;min-width:150px;" class="border_right_bottom">					 <input name="editrealname" id="realname" value="${suser.realname }" style="text-align: center;width:150px;height: 35px;font-size:18px"  /></td>
<td  style="width:150px;min-width:150px;" class="border_right_bottom"><input value="${suser.phone}" id="editphone" name="editphone"  style="text-align: center;width:150px;height: 35px;font-size:18px"  /></td>
<s:if test="suser.gender==1">
<td  style="width:150px;min-width:150px;" class="border_right_bottom">
<select  name="editgender" style="font-size:18px" >
<option selected="selected"  value="1" >男</option>
<option   value="2" >女</option>
</select>
</td>
</s:if>
<s:else>
<td  style="width:150px;min-width:150px;" class="border_right_bottom">
<select  name="editgender" style="font-size:18px">
<option   value="1" >男</option>
<option   selected="selected" value="2" >女</option>
</select>
</td>
</s:else>
<td  style="width:80px;min-width:150px;" class="border_right_bottom"><input name="editbirthday" value="${suser.birthday }" style="text-align: center;width:150px;height: 35px;font-size:18px" onclick="WdatePicker({startDate:'',dateFmt:'yyyy-MM-dd'})"/></td>
<td  style="width:150px;min-width: 150px" class="border_right_bottom"><input name="editcity" value="${suser.cityid }" style="text-align: center;width:90px;height: 35px;font-size:18px"  /></td>
</tr>





<tr class="tr_th">
<th>详细地址</th>
<th>紧急联系人姓名</th>
<th>紧急联系人号码</th>
<th>注册时间</th>
<th>综合评分</th>
</tr>


<tr class="tr_td">
<td  style="width:180px;min-width: 180px" class="border_right_bottom"><input name="editaddress" id="address" value="${suser.address}" style="text-align: center;width:180px;height: 35px;font-size:18px"  /></td>
<td  style="width:150px;min-width: 150px" class="border_right_bottom"><input name="editurgent_person" value="${suser.urgent_person }" style="text-align: center;width:150px;height: 35px;font-size:18px"  /></td>
<td  style="width:150px;min-width: 150px" class="border_right_bottom"><input name="editurgent_phone" onkeyup="value=value.replace(/[^\d]/g,'')" value="${suser.urgent_phone }"   style="text-align: center;width:150px;height: 35px;font-size:18px" /></td>
<td  style="width:150px;min-width: 150px" class="border_right_bottom"><input name="editaddtime" value="<s:date name="suser.addtime" format="yyyy-MM-dd HH:mm:ss" />" style="text-align: center;width:175px;height: 35px;font-size:18px" onclick="WdatePicker({startDate:'',dateFmt:'yyyy-MM-dd'})"/></td>
<td  style="width:150px;min-width: 150px" class="border_right_bottom"><input  name="editscore" onpaste="return false" ondragenter="return false" oncontextmenu="return false;" onkeyup="this.value=this.value.replace(/[\u4e00-\u9fa5]/g,'')"  onkeydown="myKeyDown(this.value, event)" value="${suser.score }" style="text-align: center;width:150px;height: 35px;font-size:18px"/></td>
</tr>

</table>
</div>

<div id="content_form_top">
	<div class="delbutton"style="cursor:default;">&nbsp;学员资格资料</div>
</div>
<div id="content_form_table">
<table  border="0" cellspacing="0" cellpadding="0"  style="margin-left: 27px;width: 98%;">
<tr class="tr_th">
<th>身份证号码</th>
<th>学员证或驾驶证号码</th>
<th>学员证制证时间</th>

</tr>
<tr class="tr_td">
<td  style="width:150px;min-width: 150px" class="border_right_bottom"><input name="editid_cardnum"  value="${suser.id_cardnum}" style="text-align: center;width:150px;height: 35px;font-size:18px"  /></td>
<td  style="width:150px;min-width: 150px" class="border_right_bottom"><input name="editstudent_cardnum"  value="${suser.student_cardnum}" style="text-align: center;width:150px;height: 35px;font-size:18px"  /></td>
<td  style="width:150px;min-width: 150px" class="border_right_bottom"><input name="editstudent_card_creat" value="${suser.student_card_creat }" style="text-align: center;width:150px;height: 35px;font-size:18px" onclick="WdatePicker({startDate:'',dateFmt:'yyyy-MM-dd'})"/></td>
</tr>

<tr class="tr_th">

<th>身份证正面照片 <input type="button" style="margin-left: 15px" value="修改">
<input name="editid_cardpicf_url" type="file" id="file01" style="width:38px;height: 20px;position: absolute;margin-left: -30px;opacity:0;margin-top: 14px;" onchange="change('image01', 'file01')"></th>
<th>身份证反面照片<input type="button" style="margin-left: 15px" value="修改" >
<input name="editid_cardpicb_url" type="file" id="file02" style="width:38px;height: 20px;position: absolute;margin-left: -30px;opacity:0;margin-top: 14px;" onchange="change('image02', 'file02')">
</th>
<th>学员证或驾驶证照片<input type="button" style="margin-left: 15px" value="修改">
<input name="editstudent_cardpicb_url" type="file" id="file03" style="width:38px;height: 20px;position: absolute;margin-left: -30px;opacity:0;margin-top: 14px;" onchange="change('image03', 'file03')">
</th>

</tr>
<tr class="tr_td">

<td  style="width:200px;height: 200px;min-width: 200px" class="border_right_bottom">
<img alt="" src="${suser.id_cardpicf_url}" style="width: 180px;height: 130px;" id="image01" onclick="changesize(1,'${suser.id_cardpicf_url}')">
</td>

<td  style="width:200px;height: 200px;min-width: 200px" class="border_right_bottom">
<img alt="" src="${suser.id_cardpicb_url}" style="width: 180px;height: 130px;" id="image02" onclick="changesize(2,'${suser.id_cardpicb_url}')">
</td>

<td style="width:200px;height: 200px;min-width: 200px" class="border_right_bottom">
<img alt="" src="${suser.student_cardpicb_url}" style="width: 180px;height: 130px;" id="image03"  onclick="changesize(3,'${suser.student_cardpicb_url}')">
</td>





</table>
</div>

<s:if test="suser.coachstate==1">
<div id="content_form_top">
	<div class="delbutton" style="width: 120px;cursor:default;">&nbsp;教练认证学员资料</div>
</div>
<div id="content_form_table">
<table  border="0" cellspacing="0" cellpadding="0" style="margin-left: 27px;width: 98%;">
<tr class="tr_th">
<th>验证教练ID</th>
<th>验证教练真实姓名</th>
<th>身份证正面照片</th>
<th>身份证反面照片</th>
<th>驾驶证或学员证照片</th>

</tr>
<tr class="tr_td">

<td  style="width:150px;min-width:150px;background: #e5e5e5" class="border_right_bottom"><input   value="${suser.cuser.coachid }" style="text-align: center;width:150px;background: #e5e5e5;height: 35px;font-size:18px"  readonly="readonly"/></td>
<td  style="width:150px;min-width:150px;background: #e5e5e5" class="border_right_bottom">					 <input   value="${suser.cuser.realname }" style="text-align: center;width:150px;height: 35px;font-size:18px;background: #e5e5e5" readonly="readonly" /></td>
<td style="width:200px;height: 200px;" class="border_right_bottom">
<img alt="" src="${suser.ccheck_idcardpicf_url}" style="width: 180px;height: 130px;">
</td>
<td style="width:200px;height: 200px;" class="border_right_bottom">
<img alt="" src="${suser.ccheck_idcardpicb_url}" style="width: 180px;height: 130px;">
</td>
<td style="width:200px;height: 200px;" class="border_right_bottom">
<img alt="" src="${suser.ccheck_pic_url}" style="width: 180px;height: 130px;">
</td>
</tr>
</table>
</div>

</s:if>


<div id="content_form_top">
	
	<div class="delbutton" style="cursor:default;">账号绑定情况</div>
</div>
<div id="content_form_table">
<table  border="0" cellspacing="0" cellpadding="0" style="margin-left: 27px;width: 98%;">

<tr class="tr_th">

<th>QQ账号</th>
<th>微信账号</th>
<th>微博账号</th>
</tr>
<tr class="tr_td">

<s:if test="suser.qq_openid == null">
<td  style="width:300px;background: #e5e5e5" class="border_right_bottom">
<input  value="未绑定" style="text-align: center;width:150px;height: 35px;font-size:18px;background: #e5e5e5" readonly="readonly" />
</td>
</s:if>
<s:else>
<td  style="width:300px;background: #e5e5e5" class="border_right_bottom">
<input  value="已绑定" style="text-align: center;width:150px;height: 35px;font-size:18px;background: #e5e5e5" readonly="readonly" />
</td>
</s:else>
<s:if test="suser.wx_openid == null">
<td  style="width:300px;background: #e5e5e5" class="border_right_bottom"><input  value="未绑定" style="text-align: center;width:150px;height: 35px;font-size:18px;background: #e5e5e5" readonly="readonly" /></td>
</s:if>
<s:else>
<td  style="width:300px;background: #e5e5e5" class="border_right_bottom"><input  value="已绑定" style="text-align: center;width:150px;height: 35px;font-size:18px;background: #e5e5e5" readonly="readonly" /></td>
</s:else>
<s:if test="suser.wb_openid == null">
<td  style="width:300px;background: #e5e5e5" class="border_right_bottom"><input  value="未绑定" style="text-align: center;width:150px;height: 35px;font-size:18px;background: #e5e5e5" readonly="readonly" /></td>
</s:if>
<s:else>
<td  style="width:300px;background: #e5e5e5" class="border_right_bottom"><input  value="已绑定" style="text-align: center;width:150px;height: 35px;font-size:18px;background: #e5e5e5" readonly="readonly" /></td>
</s:else>
</tr>
</table>
</div>

</form>
</div>
</div>

<!-- 放大图片弹框 -->
	<div id="change" class="change" onclick="unshowpic()"></div>
	<div id="change_sec" style="position: fixed; width: 100%; height: 300px;z-index: 500">
		<div id="change_last" class="change_last">
		<div style="position: fixed; width: 300px; height: 200px;margin-left: 100px;margin-top: 50px;">
		<img alt="" src="" id="showpic" style="height:400px;margin-top: -41px;margin-left: -34px;">
		</div>
		</div>
	</div>


</body>
</html>