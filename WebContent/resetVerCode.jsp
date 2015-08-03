<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.daoshun.menu.SideMenu"%>
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
	var flagresult=$("#flagresult").val();
	if(flagresult==1)
		{alert("重置成功！");$("#flagresult").val(0);}
		
});
function resetphone()
{
  var rphone=document.getElementById("phone").value;
  var rtype=document.getElementById("rtype").value;
  if(rphone=="")
	   alert("手机号码不能为空");
  else if(rphone.length<11)
	  alert("手机号码格式不正确");
  else
  {
	  if(confirm("确认重置改手机验证码吗？"))
	  {
		    window.location.href="resetVerCode.do?rphone="+rphone+"&rtype="+rtype;
	  }
  }

}
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
<title>重置验证码</title>
</head>
<input type="hidden" id="index" value="10" />
<input type="hidden" id="change_id" value="2"/>
<input type="hidden" id="flagresult" value="${flagresult}"/>
<body onload="getTop()" style="min-width: 1500px">
	<div id="content">
		<jsp:include page="left.jsp" />
		<div id="content_form">
			<div id="content_form_top">
		
					<div class="serchcontentdiv" style="float: left; margin-left: 50px; width: 156px" >
						<input type="text" class="searchdiv" style="width: 50px;text-align: center;font-family: 微软雅黑;" value="电话" readonly="readonly">
						<input id="phone" name="phone" type="text" class="searchdiv" style="width:100px;font-family: 微软雅黑;" value="" onkeyup="value=value.replace(/[^\d]/g,'')" onchange="phoneisNum()"/>
					</div>
				  <div  style="float:left; margin-left: 50px;margin-top:25px; width: 100px;border:thin solid;">
					    <select id="rtype" name="rtype">
					       <option value="1">教练登陆</option>
					       <option value="2">学员登陆</option>
					    </select>
					</div>	
					 <div class="addbutton" onclick="resetphone()">
						<img src="imgs/common/searchicon.png" width=22px height=22px
							style="margin-top: 9px;" >				
					</div>
			</div>
		</div>
	</div>


</body>
</html>