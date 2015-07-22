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
		
		function tofindCity(v)
		{
			//发送异步请求
			var params = {provinceid:v};
			jQuery.post("getCityByProvinceId.do", params, showCity, 'json');
		}
		
		function showCity(obj)
		{
		         document.getElementById("city").length=0;
		         for(var i=0;i<obj.length;i++)
		         {
		         	var o=new Option(obj[i].city,obj[i].cityid);
		         	document.getElementById("city").add(o);
		         }
		    
		}
		function tofindArea(v)
		{
			//发送异步请求
			var params = {cityid:v};
			jQuery.post("getAreaByCityId.do", params, showArea, 'json');
		}
		
		function showArea(obj)
		{
		         document.getElementById("area").length=0;
		         for(var i=0;i<obj.length;i++)
		         {
		         	var o=new Option(obj[i].area,obj[i].areaid);
		         	document.getElementById("area").add(o);
		         }
		}
		
		
		function tofindCityByHotKey()
		{
			//发送异步请求
			var v=$("#hotkey").val();
			var params = {hotkey:v};
			jQuery.post("getCityByHotKey.do", params, showCityHotKey, 'json');
		}
		
		function showCityHotKey(obj)
		{
		         var html="";
		         for(var i=0;i<obj.length;i++)
		         {
		        	 html=html+obj[i].city+"<br>";
		         
		         }
		         $("#citys").html(html);
		}
	
	</script>

<title>省市区</title>
</head>
<audio src="imgs/common/beep.mp3"
	preload="auto" id="audio" loop="loop"></audio>
<input type="hidden" id="index" name="index" value="${index}" />
<input type="hidden" id="hidstate" value="${state}" />
<input type="hidden" id="change_id" value="${change_id}"/>
<body onload="getTop()">
	<div id="content">
		<jsp:include page="../left.jsp" />
		<div id="content_form">
			<div id="content_form_top">
					<div class="searchbutton">
						<img src="imgs/common/searchicon.png" width=22px height=22px
							style="margin-top: 9px;" onclick="tofindCityByHotKey()">
					</div>
					<div class="serchcontentdiv" style="float: left; margin-left: 50px; width: 180px" >
						<input type="text" class="searchdiv" style="width: 65px;text-align: center;font-family: 微软雅黑;" value="省份" readonly="readonly">
						<s:select list="provincelist" listKey="provinceid" 
						listValue="province" cssClass="searchdiv" onchange="tofindCity(this.value)"></s:select>
					</div>
					
					<div class="serchcontentdiv" style="float: left; margin-left: 50px; width: 180px" >
						<input type="text" class="searchdiv" style="width: 65px;text-align: center;font-family: 微软雅黑;" value="市" readonly="readonly">
						 <select id="city" onchange="tofindArea(this.value)"></select>
					</div>
					<div class="serchcontentdiv" style="float: left; margin-left: 50px; width: 180px" >
						<input type="text" class="searchdiv11" style="width: 65px;text-align: center;font-family: 微软雅黑;" value="区" readonly="readonly">
						<select id="area" ></select>
					</div>
					<div class="serchcontentdiv" style="float: right; margin-left: 50px; width: 154px" >
						<input  type="text" class="searchdiv" style="width: 50px;text-align: center;font-family: 微软雅黑;" value="热键查询" readonly="readonly">
						<input id="hotkey" type="text"  class="searchdiv" style="width:100px;font-family: 微软雅黑;"  maxlength="10"/>
					</div>
			</div>
			<div id="citys" style="font-size:15px;padding-left:40px;padding-top:10px;">
			</div>
	</div>
</body>
</html>