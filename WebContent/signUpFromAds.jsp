<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="com.daoshun.menu.SideMenu"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/style.css" rel="stylesheet" type="text/css">
<link href="css/ordinary.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="css/page.css" type="text/css" media="screen" />
<script src="js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/page.js"></script>
<script type="text/javascript" src="coach/js/coachdetail.js"></script>
<script type="text/javascript" src="systemconfig/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
$(function(){
	var index = $("#index").val();
	$("#left_list_"+index).show();
	var j = $("#change_id").val();
	$("#change_"+j+index).addClass('left_list_mask');
});


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

</style>
<title>广告报名学员列表</title>
</head>
<input type="hidden" id="index" value="1" />
<input type="hidden" id="change_id" value="3"/>
<body onload="getTop()" style="min-width: 1500px">
	<div id="content">
		<jsp:include page="left.jsp" />
		<div id="content_form">
			<div id="content_form_top">
			
			<form action="getSignUpInfoListFromAds.do" method="post">
				 <div style="float: left;margin-left: 50px;width: 150px;height: 30px;margin-top: 21px;padding-top: 10px;" >
						<span style="font: 13.3333px Arial;">未处理</span><input type="radio" name="state" value="0" ${state==0?"checked='true'":""}/>  
						<span style="font: 13.3333px Arial;margin-left:20px;">已处理</span><input type="radio" name="state" value="1" ${state==1?"checked='true'":""}/> 
				</div>
				
				<div style="float: left;margin-left: 50px;width:150px;height: 30px;margin-top: 21px;padding-top: 10px;" >
						<span style="font: 13.3333px Arial;">未删除</span><input type="radio" name="isAbandoned" value="0" ${isAbandoned==0?"checked='true'":""}/>  
						<span style="margin-left:20px;font: 13.3333px Arial;">已删除</span><input type="radio" name="isAbandoned" value="1" ${isAbandoned==1?"checked='true'":""}/> 
				</div>			
					 <div class="serchcontentdiv" style="float: left; margin-left: 50px; width: 250px" >
						<input type="text" class="searchdiv" style="width: 71px;text-align: center;font-family: 微软雅黑;" value="广告名称" readonly="readonly">
						<select name="catg" id="catg" class="searchdiv" style="width: 174px;text-align: center;font-family: 微软雅黑;">
							<s:iterator value="catgs" id="id">
								<option value='<s:property value="id"/>'><s:property value="id"/></option>
							</s:iterator>
						</select>
					</div>
				<div class="serchcontentdiv"style="float: left; margin-left: 50px; width: 309px"  >
					<input type="text" class="searchdiv" style="width: 71px;text-align: center;font-family: 微软雅黑;" value="添加时间" readonly="readonly">
					<input name="addtimeRangeLeft"  id="addtimeRangeLeft" type="text" class="searchdiv" style="width: 99px;text-align: center;font-family: 微软雅黑;border-right: #fff;"  value="<s:date name="addtimeRangeLeft" format="yyyy-MM-dd"/>" onclick="WdatePicker({startDate:'',dateFmt:'yyyy-MM-dd'})" placeholder="&nbsp;请选择开始日期">
					<input type="text" class="searchdiv" style="width: 20px;text-align: center;font-family: 微软雅黑; border-right: #fff;" value="到" readonly="readonly">
					<input name="addtimeRangeRight" id="addtimeRangeRight" type="text" class="searchdiv" style="width: 105px;text-align: center;font-family: 微软雅黑;"value="<s:date name="addtimeRangeRight" format="yyyy-MM-dd"/>" onclick="WdatePicker({startDate:'',dateFmt:'yyyy-MM-dd'})" placeholder="&nbsp;请选择结束日期">
				</div>
				
				<input type="submit" class="searchbutton" style="width:94px;height:40px;margin-top:21px;background-image:url(imgs/common/searchicon.png);background-repeat:no-repeat;background-position:12px,12px;text-indent: 30px;background-color:#4cc2ff;float:left;margin-left:30px;">
			
			</form>
			
			
			</div>
			
			<div id="content_form_table">
				<table border="0" cellspacing="0" cellpadding="0"
					style="width: 98%;">
					<tr class="tr_th">
						<th>报名ID</th>
						<th>姓名</th>
						<th>手机号码</th>
						<th>广告名称</th>
						<th>报名来源</th>
						<th>报名时间</th>
						<th>当前状态</th>
						<th>操作</th>

					</tr>
					<s:iterator value="signUpList">
						<tr class="tr_td">
							<td style="width: 80px;" class="border_right_bottom">${signupId}</td>
							<td style="width: 150px;" class="border_right_bottom">${name}</td>
							<td style="width: 150px;" class="border_right_bottom">${phone}</td>
							<td style="width: 150px;" class="border_right_bottom">${adsTypeName}</td>
							<td style="width: 100px;" class="border_right_bottom">${source}</td>
							<td style="width: 80px;" class="border_right_bottom"><s:date name="addtime" format="yyyy-MM-dd HH:mm:ss"/></td>
							<td style="width: 100px;" class="border_right_bottom">${isAbandoned==0?"":"已删除 | "}${state==0?"未处理":"已处理"}</td>
							<td style="width: 100px;" class="border_right_bottom"><a href="signUpDetailFromAds.do?signDetailId=${signupId}">处理</a>&nbsp
							<s:if test="isAbandoned==0">
								<a href="deleteSignUp.do?deleteInfoId=${signupId}">删除</a>
							</s:if>
							<s:else>
								<a href="recoverSignUp.do?recoverSignId=${signupId}">恢复</a>
							</s:else>
							</td>
							</tr>
					</s:iterator>
				</table>

			</div>
		</div>

</body>
</html>