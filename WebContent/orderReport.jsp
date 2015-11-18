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
<link href="css/daily.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="css/page.css" type="text/css" media="screen" />
<script src="js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="js/page.js"></script>
<script src="js/common.js"></script>
<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
$(function(){
	var index = $("#index").val();
	$("#left_list_"+index).show();
	var j = $("#change_id").val();
	$("#change_"+j+index).addClass('left_list_mask');
})

function search(){
	var starttime= $("#starttime").val();
	var endtime=$("#endtime").val();
	window.location.href = "GetDailyOrderReport.do?starttime="+starttime+"&endtime="+endtime;
}
function exportData()
{
	var timeforexport=$("#timeforexport").val();
	if(timeforexport==null || timeforexport=="")
	{
		alert("请填写导出时间");
	}else
	{
		window.open("exportOrderReport.do?date="+timeforexport);
	}
	
}


</script>
<title>订单日报</title>
</head>
<input type="hidden" id="index" value="10" />
<input type="hidden" id="change_id" value="0"/>
<body onload="getTop()">
<div id="content">
<jsp:include page="left.jsp"/>
<div id="content_form">
<div id="content_form_top">
<div style="width:600px;border: 1px dashed #CCC;float:left;">
<span style="float:left;display:inline-block;width:140px;height:75px;line-height:75px;text-align:right;">报表时间区间：</span>
<input value="${starttime}" id="starttime" name="starttime" onclick="WdatePicker({startDate:'',dateFmt:'yyyy-MM-dd'})" type="text" class="searchdiv" style="width: 200px; height: 33px; padding-left:5px; border: 1px solid #eaeff2; margin-top: 20px;float:left;">
<span style="float:left;display:inline-block;height:75px;line-height:75px;text-align:right;">到</span>
<input value="${endtime}" id="endtime" name="endtime" onclick="WdatePicker({startDate:'',dateFmt:'yyyy-MM-dd'})" type="text" class="searchdiv" style="width: 200px; height: 33px; padding-left:5px; border: 1px solid #eaeff2; margin-top: 20px;float:left;">
<input type="button" value="查询" onclick="search()" style="clear: both;height: 40px;width: 184px;background: #4cc2ff; color: #fff; font-size: 16px;text-align: center; line-height: 40px;margin-top: 20px;cursor: pointer;">
</div>
<div style="width:600px;border: 1px dashed #CCC;float:left;height:117px;margin-left:30px;">
	<span style="float:left;display:block;line-height: 60px;margin-top: 10px;">导出时间：</span>
	<input value="" id="timeforexport"  onclick="WdatePicker({startDate:'',dateFmt:'yyyy-MM-dd'})" type="text" class="searchdiv" style="width: 200px; height: 33px; padding-left:5px; border: 1px solid #eaeff2; margin-top: 24px;float:left;">
	<input type="button" value="数据导出" onclick="exportData()" style="clear: both;height: 40px;width: 184px;background: #4cc2ff; color: #fff; font-size: 16px;text-align: center; line-height: 40px;margin-top: 20px;cursor: pointer;margin-left:20px;">
</div>
</div>
<div id="content_form_table">
	<table  border="0" cellspacing="0" cellpadding="0"
					style="width: 100%;">
	<tr class="tr_th">
	      <th>序号</th>
	      <th>支付方式</th>
	      <th>学员姓名</th>
	      <th>学员手机号码</th>
	      <th>订单类型（课程类别）</th>
	      <th>订单开始时间</th>
	      <th>预约课时（小时）</th>
	      <th>课时单价（元）</th>
	      <th>服务费（10%）</th>
	      <th>订单总金额（元）</th>
	      <th>订单状态</th>
	      <th>实付金额（元）</th>
	      <th>教练姓名</th>
	      <th>教练所在驾校</th>
	      <th>教练手机号</th>
	   </tr>
	   <s:iterator value="orderList" var="order">
	   <tr class="tr_td">
		<td style="width: 40px;" class="border_right_bottom">${orderid}</td>
		<td style="width: 40px;" class="border_right_bottom">${paytypename}</td>
		<td style="width: 40px;" class="border_right_bottom">${studentinfo.realname}</td>
		<td style="width: 40px;" class="border_right_bottom">${studentinfo.phone}</td>
		<td style="width: 40px;" class="border_right_bottom">${subjectname}</td>
		<td style="width: 40px;" class="border_right_bottom"><s:date name="start_time" format="yyyy-MM-dd HH:mm:ss"/></td>
		<td style="width: 40px;" class="border_right_bottom">${time}</td>
		<td style="width: 40px;" class="border_right_bottom">${unitPrice}</td>
		<td style="width: 40px;" class="border_right_bottom">${order_pull1}</td>
		<td style="width: 40px;" class="border_right_bottom">${total}</td>
		<td style="width: 40px;" class="border_right_bottom">
		<!--订单状态-->
		<s:if test="coachstate!=2 || null==over_time || 1==studentstate ||2==studentstate">
		 	未结算
		</s:if>
		<s:if test="2==coachstate && over_time!=null && 3==studentstate">
			已结算
		</s:if>
		<s:if test="4==coachstate && 4==studentstate">
			已取消
		</s:if>
		
		</td>
		<td style="width: 40px;" class="border_right_bottom">${paidMoney}</td>
		<td style="width: 40px;" class="border_right_bottom">${coachInfo.realname}</td>
		<td style="width: 40px;" class="border_right_bottom">${schoolInfo.name}</td>
		<td style="width: 40px;" class="border_right_bottom">${coachInfo.phone}</td>
	   </tr>
		</s:iterator>
					<tr>
						<td colspan="8" style="height: 86px;">
							<div style="float: left; margin-top: 34px; margin-left: 20px;">
								总计：${total}条</div> <!-- 下部翻页 -->
							<div style="float: right; margin-top: 34px; margin-right: 20px;">
								<s:if test="%{pageCount>1}">

									<input type="hidden" value="${pageCount }" id="pageSize" />
									<input type="hidden" value="${pageIndex }" id="pageIndex" />
									<input type="hidden" value="${date}" id="date" />
									<div id="untreatedpage"></div>
									<script type="text/javascript">
										//container 容器，count 总页数 pageindex 当前页数
										function setPage(container, count,
												pageindex) {
											var container = container;
											var count = parseInt(count);
											var pageindex = parseInt(pageindex);
											var starttime = $("#starttime").val();
											var endtime=$("#endtime").val();
											var a = [];
											//总页数少于10 全部显示,大于10 显示前3 后3 中间3 其余....
											if (pageindex == 1) {
												//alert(pageindex);
												a[a.length] = "<a onclick=\"\" class=\"hide_page_prev unclickprev on\"></a>";
											} else {
												a[a.length] = "<a onclick=\"previousPage("
														+ pageindex
														+ ",'GetDailyOrderReport.do?starttime="+starttime+"&endtime="+endtime
														+ "&')\" class=\"page_prev\"></a>";
											}
											function setPageList() {
												if (pageindex == i) {
													a[a.length] = "<a onclick=\"goPage('GetDailyOrderReport.do?starttime="+starttime+"&endtime="+endtime
															+ "&',"
															+ i
															+ ")\" class=\"on\">"
															+ i + "</a>";
												} else {
													a[a.length] = "<a onclick=\"goPage('GetDailyOrderReport.do?starttime="+starttime+"&endtime="+endtime
															+ "&',"
															+ i
															+ ")\">"
															+ i
															+ "</a>";
												}
											}
											//总页数小于10
											if (count <= 10) {
												for (var i = 1; i <= count; i++) {
													setPageList();
												}
												;
											} else {
												//总页数大于10页
												if (pageindex <= 4) {
													for (var i = 1; i <= 5; i++) {
														setPageList();
													}
													a[a.length] = "...<a onclick=\"goPage('GetDailyOrderReport.do?starttime="+starttime+"&endtime="+endtime
															
															+ "&',"
															+ count
															+ ")\">"
															+ count + "</a>";
												} else if (pageindex >= count - 3) {
													a[a.length] = "<a onclick=\"goPage('GetDailyOrderReport.do?starttime="+starttime+"&endtime="+endtime
													
															+ "&',1)\">1</a>...";
													for (var i = count - 4; i <= count; i++) {
														setPageList();
													}
													;
												} else { //当前页在中间部分
													a[a.length] = "<a onclick=\"goPage('GetDailyOrderReport.do?starttime="+starttime+"&endtime="+endtime
														
															+ "&',1)\">1</a>...";
													for (var i = pageindex - 2; i <= pageindex + 2; i++) {
														setPageList();
													}
													a[a.length] = "...<a onclick=\"goPage('GetDailyOrderReport.do?starttime="+starttime+"&endtime="+endtime
															
															+ "&',"
															+ count
															+ ")\">"
															+ count + "</a>";
												}
											}
											if (pageindex == count) {
												a[a.length] = "<a onclick=\"\" class=\"hide_page_next unclicknext\"></a> 共"
														+ count
														+ "页  到第  "
														+ "<input type=\"text\" class=\"jump_num\" id=\"topage\"/> 页"
														+ "<a class=\"jump_btn\" onclick=\"gotoPage('GetDailyOrderReport.do?starttime="+starttime+"&endtime="+endtime
														
														+ "&',"
														+ $("#pageSize").val()
														+ ")\")\">"
														+ "<a id='page_msg'></a>";
											} else {
												a[a.length] = "<a onclick=\"nextPage("
														+ $("#pageIndex").val()
														+ ",'GetDailyOrderReport.do?starttime="+starttime+"&endtime="+endtime
														
												"&')\" "
														+ "class=\"page_next\"></a> 共"
														+ count
														+ "页 到第 "
														+ "<input type=\"text\" class=\"jump_num\" id=\"topage\"/> 页"
														+ "<a class=\"jump_btn\" onclick=\"gotoPage('GetDailyOrderReport.do?starttime="+starttime+"&endtime="+endtime
														
														+ "&',"
														+ $("#pageSize").val()
														+ ")\">"
														+ "<a id='page_msg'></a>";
											}
											container.innerHTML = a.join("");
										}
										setPage(
												document
														.getElementById("untreatedpage"),
												parseInt($("#pageSize").val()),
												parseInt($("#pageIndex").val()));
									</script>
								</s:if>
							</div>
						</td>
					</tr>
	</table>				
</div>
</div>
</div>
</body>
</html>
