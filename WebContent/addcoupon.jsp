<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>  
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.List" %>
<%@page import="com.daoshun.menu.SideMenu" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/style.css" rel="stylesheet" type="text/css">
<script src="js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="coupon/js/coupon.js"></script>
<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
$(function(){
	var index = $("#index").val();
	$("#left_list_"+index).show();
	var j = $("#change_id").val();
	$("#change_"+j+index).addClass('left_list_mask');
})
var flag=false;
function addcoupon()
{
	var addvalue=$("#addvalue").val();
	var pub_count=$("#pub_count").val();
	var addendtime=$("#addendtime").val();	
	if(addvalue=="")
	{
	   alert("请输入面值");	
	   return;
	}
	if(pub_count=="")
	{
	   alert("请输入数量");	
	   return;
	}
	if(addendtime=="")
	{
	   alert("请输入过期时间");	
	   return;
	}
	if(flag==false)
	{   
		flag=true;
		$("#addmerchantForm").attr("action","addcoupon.do").submit();
		
	}
}
</script>
<title>小巴券编辑</title>
</head>
<input type="hidden" id="index" value="8"/>
<input type="hidden" id="change_id" value="0"/>
<body>
<div id="content">
<jsp:include page="left.jsp"/>
<div id="content_form">
<div id="content_form_blank">
<form id="addmerchantForm" action="" enctype="multipart/form-data" method="post">
	<div class="addscenic_center">
		<div class="addscenic_center_top">
			<div class="title_icon"></div>
			<div class="title_span">小巴券管理&nbsp;>&nbsp;<span style="color: #202020;">小巴券添加</span></div>
		</div>
		<div class="addscenic_center_title"><span style="margin-left: 20px;">小巴券添加</span></div>
		<div style="height: 23px;"></div>
		<div style="width: 100%; height: 75px; border-bottom: 1px solid #eaeff2;">
			<div style="float:left; width: 143px; height: 100%; line-height: 75px; border-right: 1px solid #eaeff2;text-align: right;">面值<span style="color:#f83a22; margin-right:16px; margin-left: 8px;">*</span></div>
			<div style="height: 100%; line-height: 75px; float:left; margin-left: 20px;">
				<input value="" id="addvalue" name="addvalue"  onkeyup="value=value.replace(/[^\d]/g,'')" required="required" style="width: 800px; height: 33px; padding-left:5px; border: 1px solid #eaeff2; margin-top: 20px;">
			</div>
		</div>
		
		<div style="width: 100%; height: 75px; border-bottom: 1px solid #eaeff2;">
			<div style="float:left; width: 143px; height: 100%; line-height: 75px; border-right: 1px solid #eaeff2;text-align: right;">优惠券类型<span style="color:#f83a22; margin-right:16px; margin-left: 8px;">*</span></div>
			<div style="height: 100%; line-height: 75px; float:left; margin-left: 20px;">
				<select name="addcoupontype" class="searchdiv" id="addcoupontype" style="  height: 50px;  border: 1px solid #cfd9df;margin-top:10px;">
				<option value="1">时间券</option>
				<option value="2">抵价券</option>
				</select>
			</div>
		</div>
		<div style="width: 100%; height: 75px; border-bottom: 1px solid #eaeff2;">
			<div style="float:left; width: 143px; height: 100%; line-height: 75px; border-right: 1px solid #eaeff2;text-align: right;">优惠券类型<span style="color:#f83a22; margin-right:16px; margin-left: 8px;">*</span></div>
			<div style="height: 100%; line-height: 75px; float:left; margin-left: 20px;">
				<select name="addownertype" id="addownertype" onchange="showsearchowner()" class="searchdiv" style="  height: 50px;  border: 1px solid #cfd9df;margin-top:10px;">
				<option value="0">平台发放</option>
				<option value="1">驾校发放</option>
				<option value="2">教练发放</option>
				</select>
			</div>
		</div>
		<div id="searchbefore" style="width: 100%; height: 75px; border-bottom: 1px solid #eaeff2;">
			<div style="float:left; width: 143px; height: 100%; line-height: 75px; border-right: 1px solid #eaeff2;text-align: right;">发放数量<span style="color:#f83a22; margin-right:16px; margin-left: 8px;">*</span></div>
			<div style="height: 100%; line-height: 75px; float:left; margin-left: 20px;">
				<input value="" required="required" onkeyup="value=value.replace(/[^\d]/g,'')" id="pub_count"  name="pub_count" style="width: 800px; height: 33px; padding-left:5px; border: 1px solid #eaeff2; margin-top: 20px;">
			</div>
		</div>
<!-- 		<div style="width: 100%; height: 75px; border-bottom: 1px solid #eaeff2;"> -->
<%-- 			<div style="float:left; width: 143px; height: 100%; line-height: 75px; border-right: 1px solid #eaeff2;text-align: right;">剩余数量<span style="color:#f83a22; margin-right:16px; margin-left: 8px;">*</span></div> --%>
<!-- 			<div style="height: 100%; line-height: 75px; float:left; margin-left: 20px;"> -->
<!-- 				<input value="" required="required" onkeypress="return IsNum(event)"  name="rest_count" style="width: 800px; height: 33px; padding-left:5px; border: 1px solid #eaeff2; margin-top: 20px;"> -->
<!-- 			</div> -->
<!-- 		</div> -->
		<div style="width: 100%; height: 75px; border-bottom: 1px solid #eaeff2;">
			<div style="float:left; width: 143px; height: 100%; line-height: 75px; border-right: 1px solid #eaeff2;text-align: right;">截止有效日期<span style="color:#f83a22; margin-right:16px; margin-left: 8px;">*</span></div>
			<div style="height: 100%; line-height: 75px; float:left; margin-left: 20px;">
				<input value="" required="required" id="addendtime"  name="addendtime" style="width: 800px; height: 33px; padding-left:5px; border: 1px solid #eaeff2; margin-top: 20px;"onclick="WdatePicker({startDate:'',dateFmt:'yyyy-MM-dd'})">
			</div>
		</div>
			<input type="button" onclick="addcoupon();" value="提交" style="clear: both;height: 60px;width: 184px;background: #4cc2ff; color: #fff; font-size: 16px;text-align: center; line-height: 60px;margin-left: 248px;margin-top: 20px;cursor: pointer;" >
			<div style="height:60px"></div>
	</div>
</form>
</div>
</div>
</div>



</body>
</html>