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
	<title>邀请日报</title>
</head>
<input type="hidden" id="index" value="11" />
<input type="hidden" id="change_id" value="10"/>
<body onload="getTop()" style="min-width: 1500px">
<div id="content">
	<jsp:include page="left.jsp" />
	<div id="content_form">
		<div id="content_form_top">
		</div>
		<div id="content_form_table">
			<table border="0" cellspacing="0" cellpadding="0"
				   style="width: 98%;">
				<tr class="tr_th">
					<th>日期</th>
					<th>邀请人数</th>
					<th>通过教练认证数</th>
					<th>成功开单数</th>
					<th>奖励金额</th>
				</tr>
				<s:iterator value="ip">
					<tr class="tr_td">
						<td style="width: 80px;" class="border_right_bottom">${addtime}</td>
						<td style="width: 200px;" class="border_right_bottom">${InviteCount}</td>
					 	<td style="width: 200px;" class="border_right_bottom">${CheckPassCount}</td>
					    <td style="width: 200px;" class="border_right_bottom">${OrderPassCount}</td>
					    <td style="width: 200px;" class="border_right_bottom">${RewardCount}</td>				
					</tr>
				</s:iterator>
				<tr>
					<td colspan="7" style="height: 86px;">
						<div style="float: left; margin-top: 34px; margin-left: 20px;">
							总计：${total}条</div> <!-- 下部翻页 -->
						<div style="float: right; margin-top: 34px; margin-right: 20px;">
							<s:if test="%{pageCount>1}">

								<input type="hidden" value="${pageCount }" id="pageSize" />
								<input type="hidden" value="${pageIndex }" id="pageIndex" />
								<div id="untreatedpage"></div>
								<script type="text/javascript">
									//container 容器，count 总页数 pageindex 当前页数
									function setPage(container, count, pageindex) {
										var container = container;
										var count = parseInt(count);
										var pageindex = parseInt(pageindex);
										var realname=$("#realname").val();
										var phone=$("#phone").val();
										var starttime=$("#starttime").val();
										var endtime=$("#endtime").val();
										var j = $("#change_id").val();
										var index=$("#index").val();
										var a = [];
										//总页数少于10 全部显示,大于10 显示前3 后3 中间3 其余....
										if (pageindex == 1) {
											//alert(pageindex);
											a[a.length] = "<a onclick=\"\" class=\"hide_page_prev unclickprev on\"></a>";
										} else {
											a[a.length] = "<a onclick=\"previousPage("+pageindex+",'getRecommendReport.do?&recommendtype=1&index="+index+"&change_id"+j+"&')\" class=\"page_prev\"></a>";
										}
										function setPageList() {
											if (pageindex == i) {
												a[a.length] = "<a onclick=\"goPage('getRecommendReport.do?&recommendtype=1&index="+index+"&change_id"+j+"&',"+i+")\" class=\"on\">" + i + "</a>";
											} else {
												a[a.length] = "<a onclick=\"goPage('getRecommendReport.do?&recommendtype=1&index="+index+"&change_id"+j+"&',"+i+")\">" + i + "</a>";
											}
										}
										//总页数小于10
										if (count <= 10) {
											for (var i = 1; i <= count; i++) {
												setPageList();
											};
										} else {
											//总页数大于10页
											if (pageindex <= 4) {
												for (var i = 1; i <= 5; i++) {
													setPageList();
												}
												a[a.length] = "...<a onclick=\"goPage('getRecommendReport.do?&recommendtype=1&index="+index+"&change_id"+j+"&',"+count+")\">" + count + "</a>";
											} else if (pageindex >= count - 3) {
												a[a.length] = "<a onclick=\"goPage('getRecommendReport.do?&recommendtype=1&index="+index+"&change_id"+j+"&',1)\">1</a>...";
												for (var i = count - 4; i <= count; i++) {
													setPageList();
												};
											} else { //当前页在中间部分
												a[a.length] = "<a onclick=\"goPage('getRecommendReport.do?&recommendtype=1&index="+index+"&change_id"+j+"&',1)\">1</a>...";
												for (var i = pageindex - 2; i <= pageindex+2; i++) {
													setPageList();
												}
												a[a.length] = "...<a onclick=\"goPage('getRecommendReport.do?&recommendtype=1&index="+index+"&change_id"+j+"&',"+count+")\">" + count + "</a>";
											}
										}
										if (pageindex == count) {
											a[a.length] = "<a onclick=\"\" class=\"hide_page_next unclicknext\"></a> 共"+count+"页  到第  "+
											"<input type=\"text\" class=\"jump_num\" id=\"topage\"/> 页"+
											"<a class=\"jump_btn\" onclick=\"gotoPage('getRecommendReport.do?&recommendtype=1&index="+index+"&change_id"+j+"&',"+$("#pageSize").val()+")\")\">"+
											"<a id='page_msg'></a>";
										} else {
											a[a.length] =
													"<a onclick=\"nextPage("+$("#pageIndex").val()+",'getRecommendReport.do?&recommendtype=1&index="+index+"&change_id"+j+"&')\" "+
													"class=\"page_next\"></a> 共"+count+"页 到第 "+
													"<input type=\"text\" class=\"jump_num\" id=\"topage\"/> 页"+
													"<a class=\"jump_btn\" onclick=\"gotoPage('getRecommendReport.do?&recommendtype=1&',"+$("#pageSize").val()+")\">"+
													"<a id='page_msg'></a>";
										}
// 					  a[a.length]="<a href='#' onclick='addunit()' style='float: right;position: relative;right: 50px;padding: 0px; margin: 0px; top: 3px;'><img src='imgs/add_.png'></a>";
										container.innerHTML = a.join("");
									}
									setPage(document.getElementById("untreatedpage"),parseInt($("#pageSize").val()),parseInt($("#pageIndex").val()));
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