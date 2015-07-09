	<meta http-equiv="content-type" content="text/html;charset=utf-8">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="s" uri="/struts-tags" %>
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
<script src="systemconfig/js/systemconfig.js"></script>
<script type="text/javascript" src="systemconfig/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"  src="js/common.js"></script>
<script src="KitJs/srco/js/kit.js"></script>
		<!--[if IE]>
		<script src="../../src/js/ieFix.js"></script>
		<![endif]-->
		<script src="KitJs/srco/js/array.js"></script>
		<script src="KitJs/srco/js/date.js"></script>
		<script src="KitJs/srco/js/dom.js"></script>
		<script src="KitJs/srco/js/selector.js"></script>
		<link rel="stylesheet" href="KitJs/css2.css" />
		<!--widget-->
		<script src="KitJs/srco/js/widget/DatePicker/datepicker.js"></script>
		<script src="KitJs/srco/js/widget/DatePicker/datepicker-n-months.js"></script>
		<link rel="stylesheet" href="KitJs/srco/css/widget/DatePicker/datepicker.css" />
<script type="text/javascript">
$(function(){
	var index = $("#index").val();
	$("#left_list_"+index).show();
	var j = $("#change_id").val();
	$("#change_"+j+index).addClass('left_list_mask');
})
</script>
<script>
			$kit.$(function() {
				//默认日历
				window.picker = new $kit.ui.DatePicker.NMonths();
				picker.init();
				$kit.el('#J_datePicker').appendChild(picker.picker);
				picker.show();
				picker.ev({
					ev : 'change',
					fn : function(e) {
						alert(picker.getValue());
					}
				})
				//输入框下拉
				$kit.ev({
					el : '#J_input',
					ev : 'focus',
					fn : function(e) {
						var d, ipt = e.target;
						d = e.target[$kit.ui.DatePicker.defaultConfig.kitWidgetName];
						if(d) {
							d.show();
						} else {
							d = new $kit.ui.DatePicker.NMonths();
							d.init();
							d.adhere($kit.el('#J_input'));
							d.show();
						}
					}
				});
				$kit.ev({
					el : document,
					ev : 'click',
					fn : function(e) {
						var input = $kit.el('#J_input');
						d = input[$kit.ui.DatePicker.defaultConfig.kitWidgetName];
						if(d && !$kit.contains(d.picker, e.target) && input != e.target) {
							d.hide();
						}
					}
				});
			})
		</script>
	<script type="text/javascript">
			var _gaq = _gaq || [];
			_gaq.push(['_setAccount', 'UA-30210234-1']);
			_gaq.push(['_trackPageview']); (function() {
				var ga = document.createElement('script');
				ga.type = 'text/javascript';
				ga.async = true;
				ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
				var s = document.getElementsByTagName('script')[0];
				s.parentNode.insertBefore(ga, s);
			})();

		</script>

<title>节假日设置</title>
</head>
<input type="hidden" id="index" value="${index}" />
<input type="hidden" id="change_id" value="${change_id}"/>
<body onload="getTop()">
<div id="content">
<jsp:include page="left.jsp"/>
<div id="content_form">

<div id="content_form_table"style="margin-top: 50px">
<table  border="0" cellspacing="0" cellpadding="0" style="width: 600px;">
<tr class="tr_th">
<th colspan="5">现使用节假日</th>

</tr>
<tr class="tr_td">
<td  style="width:400px;height: 200px" class="border_right_bottom">${systemSetInfo.holidays}</td>
</tr>
</table>

</div>

<div id="content_form_table"style="margin-top: 20px">

	<div class="serchcontentdiv" style="float: left; margin-left: 652px; width: 363px" >
						<input type="text" class="searchdiv" style="width: 160px;text-align: center;font-family: 微软雅黑;" value="选择日期(按住ctrl可多选)" readonly="readonly">
						<input id="J_input" type="text" class="searchdiv" style="width:100px;font-family: 微软雅黑;*zoom:1;"  />
					<input type="text" class="searchdiv" style="width: 95px;text-align: center;font-family: 微软雅黑;cursor: pointer;background: #4cc2ff" value="添加到操作表" readonly="readonly" onclick="clickadd()" >
					</div>
</div>

<div id="content_form_table"style="margin-top: 20px;width: 500px;height: 200px">

		
		
		<ul>
			<li>
				<h4></h4>
				<div id="J_datePicker" style="display: none;"></div>
				<br/>
				<br/>
			</li>
			<li>
			
				<br/>
				<br/>
				<br/>
				<br/>
				<br/>
			</li>
		</ul>
		
</div>






<div id="content_form_table"style="margin-top: 10px">
<table  border="0" cellspacing="0" cellpadding="0" style="width: 600px;">
<tr class="tr_th">
<th colspan="5">拟新设节假日</th>

</tr>
<tr class="tr_td">
<td id="newholidays" style="width:400px;height: 200px" class="border_right_bottom"></td>
</tr>
</table>
<div class="addbutton" onclick="updateholidays('${systemSetInfo.dataid}')" style="margin-left: 810px">&nbsp;保存</div>
</div>
</div>
</div>


</body>
</html>