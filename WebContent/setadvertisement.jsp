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
<script language="javascript">
function goset()
{
	$("#ad_form").attr("action", "setAdvertisement.do").submit();
}
</script>
<title>广告设置</title>
</head>
<input type="hidden" id="index" value="1" />
<input type="hidden" id="change_id" value="0"/>

<body onload="getTop()">
		<jsp:include page="left.jsp" />
<form id="ad_form" action="" method="post" enctype="multipart/form-data">
    <table>
    <tr> 广告内容设置 </tr>
    
    <td colspan="2">广告图片上传</td>
    <td>
      <label for="file01">浏览</label>
	  	<input name="editid_cardpicfurl" type="file" id="file01" style="display:none;"  onchange="change('image01', 'file01')">
		<img alt="" src="" style="width: 180px;height: 130px;" id="image01" onclick=""></td>
	    <td>设置URL路径<input type="text"  id="ad_url" name="ad_url" maxlength="20"/>
	<div class="addbutton" style="float: left;margin-left: 10px;" onclick="goset()">确认提交</div>
	</td>					
    </table>
  </form>
					
					
					
		
	
</body>
</html>