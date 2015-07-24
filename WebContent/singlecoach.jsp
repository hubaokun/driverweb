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
<!-- <link href="css/lrtk.css" type="text/css" rel="stylesheet"> -->
<!-- <link href="css/magiczoomplus.css" type="text/css" rel="stylesheet"> -->
<script src="js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script  type="text/javascript" src="coach/js/coachdetail.js"></script>
<script type="text/javascript" src="systemconfig/js/My97DatePicker/WdatePicker.js"></script>
<%-- <script type="text/javascript" src="js/mzp-packed.js"></script> --%>
<%-- <script type="text/javascript" src="js/lrtk.js"></script> --%>
<script type="text/javascript">
$(function(){
	var index = $("#index").val();
	$("#left_list_"+index).show();
	var j = $("#change_id").val();
	$("#change_"+j+index).addClass('left_list_mask');
	$("#nav").css("min-height","1468px");
	var level = $("#coachlevel_id").val();
	$("#clevel").val(level);
	
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
.level {
	position: fixed;
	top: 0px;
	bottom: 0px;
	left: 0px;
	right: 0px;
	background: #707070;
	opacity: 0.3;
	display: none;
}

.level_last {
	position: fixed;
	background: #fff;
	width: 500px;
	height: 300px;
	margin: 0 auto;
	display: none;
}

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

.frozen {
	position: fixed;
	top: 0px;
	bottom: 0px;
	left: 0px;
	right: 0px;
	background: #707070;
	opacity: 0.3;
	display: none;
}

.frozen_last {
	position: fixed;
	background: #fff;
	width: 500px;
	height: 300px;
	margin: 0 auto;
	display: none;
}

.alertbox {
	position: fixed;
	top: 0px;
	bottom: 0px;
	left: 0px;
	right: 0px;
	background: #707070;
	opacity: 0.3;
	display: none;
}

.alertbox_last{
	position: fixed;
	background: #fff;
	width: 600px;
	height: 350px;
	margin: 0 auto;
	display: none;
}

</style>
<title>教练个人详情</title>
</head>
<input type="hidden" id="index" value="1" />
<input type="hidden" id="change_id" value="0"/>
<input type="hidden" id="coachlevel_id" value="${cuser.level}"/>
<input type="hidden" value="${pageCount }" id="pageSize" />
<input type="hidden" value="${pageIndex }" id="pageIndex" />
<body onload="getTop()" style="min-width: 1680px">
<div id="content">
<jsp:include page="left.jsp"/>
<div id="content_form">

<div id="content_form_top1">
	<div class="delbutton">&nbsp;基本资料</div>
	<div class="addbutton" style="float: left;margin-left: 10px;" onclick="editsinglecoach()">保存修改</div>
	<div class="addbutton" style="float: right;margin-right: 10px;" onclick="gobackcoach()">返&nbsp;回</div>
</div>


<form action="" id="singlecoachForm" enctype="multipart/form-data" method="post">
<div id="content_form_table1" style="min-width: 1200px">
<table  border="0" cellspacing="0" cellpadding="0" style="width: 98%;">
<tr class="tr_th"  >
<th >ID</th>
<th >真实姓名</th>
<th>电话号码</th>
<th>性别</th>
<th>教龄</th>
<th>所属驾校<input type="button" style="margin-left: 15px" onclick="showchangeschool()" value="修改"></th>
<th>出生日期</th>
<th>城市</th>
</tr>

<tr class="tr_td">
<td  style="width:150px;min-width:150px;background: #e5e5e5" class="border_right_bottom"><input name="coachid" id="coachid" value="${cuser.coachid }" style="text-align: center;width:150px;background: #e5e5e5;height: 35px;font-size:12px"  readonly="readonly"/></td>
<td  style="width:150px;min-width:150px;" class="border_right_bottom"><input   name="editrealname" id="realname" value="${cuser.realname }" style="text-align: center;width:150px;height: 35px;font-size:12px"  /></td>
<td  style="width:150px;min-width:150px;background: #e5e5e5" class="border_right_bottom"><input value="${cuser.phone }" style="text-align: center;width:150px;background: #e5e5e5;height: 35px;font-size:12px"  readonly="readonly"/></td>

<s:if test="cuser.gender==1">
<td  style="width:150px;min-width:150px;" class="border_right_bottom">
<select  name="editgender" style="font-size:12px" >
<option selected="selected"  value="1" >男</option>
<option   value="2" >女</option>
</select>
</td>
</s:if>
<s:else>
<td  style="width:150px;min-width:150px;" class="border_right_bottom">
<select  name="editgender" style="font-size:12px">
<option   value="1" >男</option>
<option   selected="selected" value="2" >女</option>
</select>
</td>
</s:else>


<td  style="width:150px;min-width:150px;" class="border_right_bottom"><input name="edityears" id="years" value="${cuser.years }" style="text-align: center;width:150px;height: 35px;font-size:12px"/></td>
<td  style="width:250px;min-width:150px;" class="border_right_bottom"><input value="${cuser.drive_school }" style="text-align: center;width:240px;height: 35px;font-size:12px"  readonly="readonly"/>



</td>

<td  style="width:100px;min-width:150px;" class="border_right_bottom"><input name="editbirthday" value="${cuser.birthday }" style="text-align: center;width:150px;height: 35px;font-size:12px" onclick="WdatePicker({startDate:'',dateFmt:'yyyy-MM-dd'})"/></td>
<td  style="width:150px;min-width: 150px" class="border_right_bottom"><input name="editcity" value="${cuser.cityid}" style="text-align: center;width:90px;height: 35px;font-size:12px"  /></td>
</tr>



<tr class="tr_th">
<th>详细地址</th>
<th>紧急联系人姓名</th>
<th>紧急联系人号码</th>
<th>自我评价</th>
<th>教练等级</th>
<th>注册时间</th>
<th>综合评分</th>
</tr>



<tr class="tr_td">
<td  style="width:150px;min-width: 150px" class="border_right_bottom"><input name="editaddress" id="address" value="${cuser.address}" style="text-align: center;width:150px;height: 35px;font-size:12px"  /></td>
<td  style="width:150px;min-width: 150px" class="border_right_bottom"><input name="editurgent_person" value="${cuser.urgent_person }" style="text-align: center;width:150px;height: 35px;font-size:12px"  /></td>
<td  style="width:150px;min-width: 150px" class="border_right_bottom"><input name="editurgent_phone" onkeyup="value=value.replace(/[^\d]/g,'')" value="${cuser.urgent_phone }"   style="text-align: center;width:150px;height: 35px;font-size:12px" /></td>
<td  style="width:150px;min-width: 150px" class="border_right_bottom"><input  name="editselfval" value="${cuser.selfeval }" style="text-align: center;width:150px;height: 35px;font-size:12px"/></td>
<td  style="width:150px;min-width: 150px" class="border_right_bottom">
	<select id="clevel" name="editlevel" class="searchdiv" style="width: 100px;height: 35px;">
		<s:iterator value="levelist" var="list">
			<option value="${levelid}">${levelname}</option>
		</s:iterator>
	</select>
</td>
<td  style="width:150px;min-width: 150px" class="border_right_bottom"><input name="editaddtime" value="<s:date name="cuser.addtime" format="yyyy-MM-dd HH:mm:ss" />" style="text-align: center;width:200px;height: 35px;font-size:12px" onclick="WdatePicker({startDate:'',dateFmt:'yyyy-MM-dd'})"/></td>
<td  style="width:150px;min-width: 150px" class="border_right_bottom"><input name="editscore" value="${cuser.score }" style="text-align: center;width:150px;height: 35px;font-size:12px;" onpaste="return false" ondragenter="return false" oncontextmenu="return false;" onkeyup="this.value=this.value.replace(/[\u4e00-\u9fa5]/g,'')"  onkeydown="myKeyDown(this.value, event)"/></td>
</tr>



</table>

</div>

<div id="content_form_top2">
	<div class="delbutton">&nbsp;教练资格资料</div>
</div>
<div id="content_form_table2">
<table  border="0" cellspacing="0" cellpadding="0" style="width: 98%;">

<tr class="tr_th">
<th>身份证号码</th>
<th>身份证到期时间</th>
<th>身份证正面照片 <input type="button" style="margin-left: 15px" value="修改">
<input name="editid_cardpicfurl" type="file" id="file01" style="width:38px;height: 20px;position: absolute;margin-left: -30px;opacity:0;margin-top: 14px;" onchange="change('image01', 'file01')"></th>
<th>身份证反面照片<input type="button" style="margin-left: 15px" value="修改" >
<input name="editid_cardpicburl" type="file" id="file02" style="width:38px;height: 20px;position: absolute;margin-left: -30px;opacity:0;margin-top: 14px;" onchange="change('image02', 'file02')">
</th>
</tr>
<tr class="tr_td">
<td  style="width:200px;min-width: 200px" class="border_right_bottom"><input name="editid_cardnum" value="${cuser.id_cardnum }" class="inputwithborder" style="text-align: center;width:200px;height: 35px;font-size:12px"/></td>

<td  style="width:150px;min-width: 200px" class="border_right_bottom"><input name="editid_cardexptime" value="${cuser.id_cardexptime }" class="inputwithborder" style="text-align: center;width:200px;height: 35px;font-size:12px" onclick="WdatePicker({startDate:'',dateFmt:'yyyy-MM-dd'})"/></td>

<td  style="width:200px;height: 200px;min-width: 200px" class="border_right_bottom">
<img alt="" src="${cuser.id_cardpicfurl}" style="width: 180px;height: 130px;" id="image01" onclick="changesize(1,'${cuser.id_cardpicfurl}')">
</td>

<td style="width:200px;height: 200px;min-width: 200px" class="border_right_bottom">
<img alt="" src="${cuser.id_cardpicburl}" style="width: 180px;height: 130px;" id="image02" onclick="changesize(2,'${cuser.id_cardpicburl}')">
</td>
</tr>

<tr class="tr_th">
<th>教练证号</th>
<th>教练证到期时间</th>
<th>教练证照片<input type="button" style="margin-left: 15px" value="修改">
<input name="editcoach_cardpicurl" type="file" id="file03" style="width:38px;height: 20px;position: absolute;margin-left: -30px;opacity:0;margin-top: 14px;" onchange="change('image03', 'file03')">
</th>
<th>准教车型<input type="button" style="margin-left: 15px" onclick="showchangemoney()" value="修改"></th>
</tr>
<tr class="tr_td">
<td  style="width:150px;min-width: 200px" class="border_right_bottom"><input name="editcoach_cardnum" value="${cuser.coach_cardnum }" class="inputwithborder" style="text-align: center;width:150px;height: 35px;font-size:12px"/></td>
<td  style="width:150px;min-width: 200px" class="border_right_bottom"><input name="editcoach_cardexptime" value="${cuser.coach_cardexptime }" class="inputwithborder" style="text-align: center;width:200px;height: 35px;font-size:12px" onclick="WdatePicker({startDate:'',dateFmt:'yyyy-MM-dd'})"/></td>
<td style="width:200px;height: 200px;min-width: 200px" class="border_right_bottom">
<img alt="" src="${cuser.coach_cardpicurl}" style="width: 180px;height: 130px;" id="image03"  onclick="changesize(3,'${cuser.coach_cardpicurl}')">
<!-- <input name="editcoach_cardpicurl" type="file" id="file03" style="width:38px;height: 20px;position: absolute;margin-left: -40px;opacity:0;margin-top: -67px;" onchange="change('image03', 'file03')"> -->
</td>
<td  style="width:200px;" class="border_right_bottom">
		<s:iterator value="coachmodellist" status="list">
			<input name="aaaa" value="${modelname}" style="text-align: center;width:80px;height: 35px;font-size:12px"/>
		</s:iterator>
</td>
</tr>



<tr class="tr_th">
<th>驾驶证号</th>
<th>驾驶证到期时间</th>
<th>驾驶证照片<input type="button" style="margin-left: 15px" value="修改">
<input name="editdrive_cardpicurl" type="file" id="file04" style="width:38px;height: 20px;position: absolute;margin-left: -30px;opacity:0;margin-top: 14px;" onchange="change('image04', 'file04')">
</tr>
<tr class="tr_td">
<td  style="width:150px;min-width: 200px" class="border_right_bottom"><input name="editdrive_cardnum" value="${cuser.drive_cardnum}" class="inputwithborder" style="text-align: center;width:150px;height: 35px;font-size:12px"/></td>
<td  style="width:150px;min-width: 200px" class="border_right_bottom"><input name="editdrive_cardexptime" value="${cuser.drive_cardexptime }" class="inputwithborder"  style="text-align: center;width:200px;height: 35px;font-size:12px" onclick="WdatePicker({startDate:'',dateFmt:'yyyy-MM-dd'})"/></td>
<td style="width:200px;height: 200px;min-width: 200px" class="border_right_bottom">
<img alt="" src="${cuser.drive_cardpicurl}" style="width: 180px;height: 130px;" id="image04"  onclick="changesize(4,'${cuser.drive_cardpicurl}')">
<!-- <input name="editdrive_cardpicurl" type="file" id="file04" style="width:38px;height: 20px;position: absolute;margin-left: -40px;opacity:0;margin-top: -67px;" onchange="change('image04', 'file04')"> -->
</td>
</tr>


<tr class="tr_th">
<th>车辆年检证号</th>
<th>车辆年检证到期时间</th>
<th>车辆行驶证正面照片<input type="button" style="margin-left: 15px;" value="修改">
<input name="editcar_cardpicfurl" type="file" id="file05" style="width:38px;height: 20px;position: absolute;margin-left: -30px;opacity:0;margin-top: 14px;" onchange="change('image05', 'file05')">
</th>
<th>车辆行驶证反面照片<input type="button" style="margin-left: 15px" value="修改">
<input name="editcar_cardpicburl" type="file" id="file06" style="width:38px;height: 20px;position: absolute;margin-left: -30px;opacity:0;margin-top: 14px;" onchange="change('image06', 'file06')">
</th>
</tr>
<tr class="tr_td">
<td  style="width:200px;min-width: 200px" class="border_right_bottom"><input name="editcar_cardnum" value="${cuser.car_cardnum }" class="inputwithborder"  style="text-align: center;width:150px;height: 35px;font-size:12px"/></td>
<td  style="width:200px;min-width: 200px" class="border_right_bottom"><input name="editcar_cardexptime" value="${cuser.car_cardexptime }" class="inputwithborder"  style="text-align: center;width:200px;height: 35px;font-size:12px" onclick="WdatePicker({startDate:'',dateFmt:'yyyy-MM-dd'})"/></td>
<td style="width:200px;height: 200px;min-width: 200px" class="border_right_bottom">
<img alt="" src="${cuser.car_cardpicfurl}" style="width: 180px;height: 130px;" id="image05"  onclick="changesize(5,'${cuser.car_cardpicfurl}')">
<!-- <input name="editcar_cardpicfurl" type="file" id="file05" style="width:38px;height: 20px;position: absolute;margin-left: -40px;opacity:0;margin-top: -67px;" onchange="change('image05', 'file05')"> -->
</td>
<td style="width:200px;height: 200px;" class="border_right_bottom">
<img alt="" src="${cuser.car_cardpicburl}" style="width: 180px;height: 130px;"id="image06"  onclick="changesize(6,'${cuser.car_cardpicburl}')">
<!-- <input name="editcar_cardpicburl" type="file" id="file06" style="width:38px;height: 20px;position: absolute;margin-left: -40px;opacity:0;margin-top: -67px;" onchange="change('image06', 'file06')"> -->
</td>
</tr>



<tr class="tr_th">

<th>教学用车型<input type="button" style="margin-left: 15px" onclick="showTeachCar()" value="修改"> </th>
<th>教学用车牌照</th>
<th>是否已设置地址</th>
<th>操作</th>
</tr>


<tr class="tr_td">
<td  style="width:200px;min-width: 200px" class="border_right_bottom"><input name="editcarmodel" value="${cuser.carmodel }" style="text-align: center;width:150px;height: 35px;font-size:12px" readonly="readonly"/></td>
<td  style="width:200px;min-width: 200px" class="border_right_bottom"><input name="editcarlicense" value="${cuser.carlicense}" style="text-align: center;width:150px;height: 35px;font-size:12px"/></td>
<s:if test="cuser.address==null">
<td  style="width:200px;font-size:12px;min-width: 200px" class="border_right_bottom">未设置</td>
</s:if>
<s:else>
<td  style="width:200px;font-size:12px;min-width: 200px" class="border_right_bottom">已设置</td>
</s:else>
<td style="width: 340px;min-width: 340px" class="border_noright_bottom">

								<div class="table_edit_button" style="width: 80px;background:#1bbc9b">
									<div class="table_button_edit_icon"></div>
									<div class="table_button_text"
										onclick="checkpass(2,${coachid},1)">审核通过</div>
								</div>
								<div class="table_edit_button" style="width: 90px;background:#f83a22">
									<div class="table_button_edit_icon"></div>
									<div class="table_button_text"
										onclick="checknopass(3,${coachid},1)">审核不通过</div>
								</div>
								<div class="table_edit_button" style="width: 90px;">
									<div class="table_button_edit_icon"></div>
									<div class="table_button_text" onclick="showchangegmoney(${coachid})">设置保证金</div>
								</div>
								<s:if test="cuser.cancancel == 0">
								<div class="table_edit_button" style="width: 110px;background:#1bbc9b">
									<div class="table_button_edit_icon"></div>
									<div class="table_button_text" onclick="changecancancel(1,${coachid})">订单可以取消</div>
								</div>
								</s:if>
								<s:else>
								<div class="table_edit_button" style="width: 110px;background:#f83a22">
									<div class="table_button_edit_icon"></div>
									<div class="table_button_text" onclick="changecancancel(0,${coachid})">订单不可取消</div>
									</div>
								</s:else>
							</td>
</tr>


</table>


</div>
</form>

</div>
</div>
<!-- 修改准驾车型弹框 -->
	<div id="mask" class="mask"></div>
	<div id="mask_sec" style="position: fixed; width: 100%; height: 300px;z-index: 500">
		<div id="mask_last" class="mask_last">
		<div style="position: fixed; width: 300px; height: 200px;background: #4cc2ff;margin-left: 100px;margin-top: 50px;">
		<form  action="" id="changeModelForm" enctype="multipart/form-data" method="post">
			<input type="hidden" name="coachid" value="${cuser.coachid }"/>
		<s:iterator value="modellist" status="list">
			<s:if test="list.index3%==2">
			<div style="clean:both; float:left;"><input name="coachmodelid" id="coachmodelid" type="radio" value="${modelid}" />${modelname}</div>
			</s:if>
			<s:else>
			<div style="float:left;"><input name="coachmodelid" id="coachmodelid" type="radio" value="${modelid}" />${modelname}</div> 
			</s:else>
		</s:iterator>
		</form>
		<input type="button" style="width: 100px;height: 40px;margin: auto;margin-left: 50px;margin-right:22px; margin-top: 100px;font-size: 18px" value="确定" onclick="changeModel()">
		<input type="button" style="width: 100px;height: 40px;margin: auto;margin-left: 180x;margin-top: -40px;font-size: 18px" value="取消" onclick="unshowchangegmoney()">
		</div>
		</div>
	</div>
	
<!-- 修改所属教校弹框 -->
	<div id="alertbox" class="alertbox"></div>
	<div id="alertbox_sec" style="position: fixed; width: 100%; height: 300px;z-index: 500;">
		<div id="alertbox_last" class="alertbox_last" style="">
		<div style="position: fixed; width: 400px; height: 200px;background: #4cc2ff;margin-left: 50px;margin-top: 50px;">
		<div style="font-size: 16px;margin-top: 5px;width: 320px;float: left;margin-left: 20px;">
			关键字：<input type="text" style="height: 25px;width: 230px;" id="schoolkeyword" />
		</div>
		<div onclick="searchDriverSchool();" style="float:left;cursor:pointer;">
				<img src="imgs/common/searchicon.png" width=22px height=22px style="margin-top: 9px;">
		</div>
		<div style="width: 230px;margin: auto;margin-top: 50px;">
			<select id="driveschool_id" style="width: 230px;height: 30px;">
			</select> 
		</div>
		<input type="button" style="width: 100px;height: 40px;margin: auto;margin-left: 90px;margin-right:10px; margin-top: 50px;font-size: 18px" value="确定" onclick="changeSchool()">
		<input type="button" style="width: 100px;height: 40px;margin: auto;margin-left: 180x;margin-top: -40px;font-size: 18px" value="取消" onclick="unshowchangeschool()">
		</div>
		</div>
	</div>
	
<!-- 设置保教练等级弹框 -->
	<div id="level" class="level"></div>
	<div id="level_sec" style="position: fixed; width: 100%; height: 300px;z-index: 300;">
		<div id="level_last" class="level_last">
		<div style="position: fixed; width: 300px; height: 200px;background: #4cc2ff;margin-left: 100px;margin-top: 50px;">
		<input type="hidden" id="level_coachid" value=""/>
		<select id="clevel" class="searchdiv" style="width: 100px;height: 40px;margin-top: 27px;margin-left: 100px;">
			<s:iterator value="levelist" var="list">
				<option value="${levelid}">${levelname}</option>
			</s:iterator>
		</select>
		<input type="button" style="width: 100px;height: 40px;margin: auto;margin-left: 50px;margin-top: 40px;font-size: 18px" value="确定" onclick="changelevel()">
		<input type="button" style="width: 100px;height: 40px;margin: auto;margin-left: 180x;margin-top: -40px;font-size: 18px" value="取消" onclick="unshowchangelevel()">
		<input type="hidden" value="" id="allchangelevel">
		</div>
		</div>
	</div>
	
	<!-- 放大图片弹框 -->
	<div id="change" class="change" onclick="unshowpic()"></div>
	<div id="change_sec" style="position: fixed; width: 100%; height: 300px;z-index: 500">
		<div id="change_last" class="change_last">
		<div style="position: fixed; width: 300px; height: 200px;margin-left: 100px;margin-top: 50px;">
		<img alt="" src="" id="showpic" style="height: 400PX;margin-top: -41px;margin-left: -34px;">
		</div>
		</div>
	</div>
	
	<!-- 教学用车型弹框 -->
	<div id="frozen" class="frozen"></div>
	<div id="frozen_sec" style="position: fixed; width: 100%; height: 300px;z-index: 300">
		<div id="frozen_last" class="frozen_last">
		<div style="position: fixed; width: 300px; height: 200px;background: #4cc2ff;margin-left: 100px;margin-top: 50px;">
		<s:iterator value="teachcarlist" var="driveSchool" status="list">
			<s:if test="list.index3%==2">
			<div style="clean:both; float:left;"><input name="teachcarid" id="teachcarid" type="radio" value="${modelid}" />${modelname}</div>
			</s:if>
			<s:else>
			<div style="float:left;"><input name="teachcarid" id="teachcarid" type="radio" value="${modelid}" />${modelname}</div> 
			</s:else>
		</s:iterator>
		<input type="button" style="width: 100px;height: 40px;margin: auto;margin-left: 50px;margin-right:20px; margin-top: 100px;font-size: 18px" value="确定" onclick="changeTeachCar()">
		<input type="button" style="width: 100px;height: 40px;margin: auto;margin-left: 180x;margin-top: -40px;font-size: 18px" value="取消" onclick="unshowfrozen()">
		</div>
		</div>
	</div>
</body>
</html>