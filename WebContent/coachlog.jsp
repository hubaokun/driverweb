<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.daoshun.menu.SideMenu"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/style.css" rel="stylesheet" type="text/css">
<link href="css/ordinary.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="css/page.css" type="text/css"
	media="screen" />
<script src="js/jquery-1.11.2.min.js"></script>
<script src="js/common.js"></script>
<script type="text/javascript" src="js/page.js"></script>
<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
	$(function() {
		var index = $("#index").val();
		$("#left_list_" + index).show();
		var j = $("#change_id").val();
		$("#change_" + j + index).addClass('left_list_mask');

		var error = $("#errormessage").val();
		if (error == '') {
		} else {
			alert(error);
			window.location = "getCouponRecordList.do";
		}

	});

	function searchfromsubmit() {
		var starttime = $("#starttime").val();
		var endtime = $("#endtime").val();
		if (starttime != '' && endtime != '') {
			if (starttime > endtime) {
				alert("结束时间必须在开始时间之后！");
				return;
			}
		}
		$("#searchform").submit();
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
	width: 600px;
	height: 440px;
	margin: 0 auto;
	display: none;
}

.edit {
	position: fixed;
	top: 0px;
	bottom: 0px;
	left: 0px;
	right: 0px;
	background: #707070;
	opacity: 0.3;
	display: none;
}

.edit_last {
	position: fixed;
	background: #fff;
	width: 500px;
	height: 700px;
	margin: 0 auto;
	display: none;
}
</style>

<title>教练日志记录</title> 
</head>
<input type="hidden" id="index" value="11" />
<input type="hidden" id="change_id" value="0" />
<input type="hidden" id="errormessage" value="${errormessage}" />
<body onload="getSelfTop()">
	<div id="content">
		<jsp:include page="left.jsp" />
		<div id="content_form">
			<div id="content_form_top">
				<form id="searchform" action="CoachLog.do">
					<div id="content_form_top">

						<div class="searchbutton" onclick="searchfromsubmit();">
							<img src="imgs/common/searchicon.png" width=22px height=22px
								style="margin-top: 9px;">
						</div>

						<div class="serchcontentdiv"
							style="float: left; margin-left: 50px; width: 156px">
							<input type="text" class="searchdiv"
								style="width: 50px; text-align: center; font-family: 微软雅黑;"
								value="用户名" readonly="readonly"> <input id="username"
								type="text" name="username" class="searchdiv"
								style="width: 100px; font-family: 微软雅黑;" value="${username}"
								maxlength="11" onclick="username.focus();this.select();" />
						</div>
						
						<div class="serchcontentdiv"
							style="float: left; margin-left: 50px; width: 156px">
							<input type="text" class="searchdiv"
								style="width: 50px; text-align: center; font-family: 微软雅黑;"
								value="对应表" readonly="readonly"> <input id="oprateform"
								type="text" name="oprateform" class="searchdiv"
								style="width: 100px; font-family: 微软雅黑;" value="${oprateform}"
								maxlength="11" onclick="oprateform.focus();this.select();" />
						</div>
						
						
						<div class="serchcontentdiv"
							style="float: left; margin-left: 50px; width: 405px">
							<input type="text" class="searchdiv"
								style="width: 119px; text-align: center; font-family: 微软雅黑;"
								value="操作时间" readonly="readonly"> <input id="starttime"
								name="starttime"
								onclick="WdatePicker({startDate:'',dateFmt:'yyyy-MM-dd'})"
								type="text" class="searchdiv"
								style="width: 120px; text-align: center; font-family: 微软雅黑;"
								value="${starttime}"> <input type="text"
								class="searchdiv"
								style="width: 30px; text-align: center; font-family: 微软雅黑;"
								value="到" readonly="readonly"> <input id="endtime"
								name="endtime"
								onclick="WdatePicker({startDate:'',dateFmt:'yyyy-MM-dd'})"
								type="text" class="searchdiv"
								style="width: 120px; text-align: center; font-family: 微软雅黑;"
								value="${endtime}">
						</div>

						
					</div>
				</form>
			</div>
			<div id="content_form_table">
				<table border="0" cellspacing="0" cellpadding="0"
					style="width: 98%;">
					<tr class="tr_th">
						<th>操作人</th>
						<th>对应表</th>
						<th>教练名</th>
						<th>具体操作</th>
						<th>操作时间</th>
					</tr>
					<s:iterator value="logInfoList" var="logInfoList">
						<tr class="tr_td">
							<td style="width: 100px;" class="border_right_bottom">${operatorname}</td>
							<td style="width: 100px;" class="border_right_bottom">${operateform}</td>
							<td style="width: 150px;" class="border_right_bottom">${opusername}</td>
							<td style="width: 250px;" class="border_right_bottom">${operatecontent}</td>
							<td style="width: 150px;" class="border_right_bottom"><s:date
									name="operatetime" format="yyyy-MM-dd HH:mm:ss" /></td>
						</tr>
					</s:iterator>

					<tr>
						<td colspan="10" style="height: 86px;">
							<div style="float: left; margin-top: 34px; margin-left: 20px;">
								总计：${total}条</div> <!-- 下部翻页 -->
							<div style="float: right; margin-top: 34px; margin-right: 20px;">
								<s:if test="%{pageCount>1}">

									<input type="hidden" value="${pageCount }" id="pageSize" />
									<input type="hidden" value="${pageIndex }" id="pageIndex" />
									<div id="untreatedpage"></div>
									<script type="text/javascript">
										//container 容器，count 总页数 pageindex 当前页数
										function setPage(container, count,
												pageindex) {
											var container = container;
											var count = parseInt(count);
											var pageindex = parseInt(pageindex);
											var username = $("#username").val();
											var oprateform = $("#oprateform").val();
											var starttime = $("#starttime")
													.val();
											var endtime = $("#endtime").val();
											var a = [];
											//总页数少于10 全部显示,大于10 显示前3 后3 中间3 其余....
											if (pageindex == 1) {
												//alert(pageindex);
												a[a.length] = "<a onclick=\"\" class=\"hide_page_prev unclickprev on\"></a>";
											} else {
												a[a.length] = "<a onclick=\"previousPage("
														+ pageindex
														+ ",'CoachLog.do?username="
														+ username
														+"&oprateform="
														+ oprateform
														+ "&starttime="
														+ starttime
														+ "&endtime="
														+ endtime
														+ "&')\" class=\"page_prev\"></a>";
											}
											function setPageList() {
												if (pageindex == i) {
													a[a.length] = "<a onclick=\"goPage('CoachLog.do?username="
															+ username
															+"&oprateform="
															+ oprateform
															+ "&starttime="
															+ starttime
															+ "&endtime="
															+ endtime
															+ "&',"
															+ i
															+ ")\" class=\"on\">"
															+ i + "</a>";
												} else {
													a[a.length] = "<a onclick=\"goPage('CoachLog.do?username="
															+ username
															+"&oprateform="
															+ oprateform
															+ "&starttime="
															+ starttime
															+ "&endtime="
															+ endtime
															+ "&',"
															+ i
															+ ")\">"
															+ i
															+ "</a>";
												}
											}
											//总页数小于10
											if (count <= 10) {
												for ( var i = 1; i <= count; i++) {
													setPageList();
												}
												;
											} else {
												//总页数大于10页
												if (pageindex <= 4) {
													for ( var i = 1; i <= 5; i++) {
														setPageList();
													}
													a[a.length] = "...<a onclick=\"goPage('CoachLog.do?username="
															+ username
															+"&oprateform="
															+ oprateform
															+ "&starttime="
															+ starttime
															+ "&endtime="
															+ endtime
															+ "&',"
															+ count
															+ ")\">"
															+ count + "</a>";
												} else if (pageindex >= count - 3) {
													a[a.length] = "<a onclick=\"goPage('CoachLog.do?username="
															+ username
															+"&oprateform="
															+ oprateform
															+ "&starttime="
															+ starttime
															+ "&endtime="
															+ endtime
															+ "&',1)\">1</a>...";
													for ( var i = count - 4; i <= count; i++) {
														setPageList();
													}
													;
												} else { //当前页在中间部分
													a[a.length] = "<a onclick=\"goPage('CoachLog.do?username="
															+ username
															+"&oprateform="
															+ oprateform
															+ "&starttime="
															+ starttime
															+ "&endtime="
															+ endtime
															+ "&',1)\">1</a>...";
													for ( var i = pageindex - 2; i <= pageindex + 2; i++) {
														setPageList();
													}
													a[a.length] = "...<a onclick=\"goPage('CoachLog.do?username="
															+ username
															+"&oprateform="
															+ oprateform
															+ "&starttime="
															+ starttime
															+ "&endtime="
															+ endtime
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
														+ "<a class=\"jump_btn\" onclick=\"gotoPage('CoachLog.do?username="
														+ username
														+"&oprateform="
														+ oprateform
														+ "&starttime="
														+ starttime
														+ "&endtime="
														+ endtime
														+ "&',"
														+ $("#pageSize").val()
														+ ")\")\">"
														+ "<a id='page_msg'></a>";
											} else {
												a[a.length] = "<a onclick=\"nextPage("
														+ $("#pageIndex").val()
														+ ",'CoachLog.do?username="
														+ username
														+"&oprateform="
														+ oprateform
														+ "&starttime="
														+ starttime
														+ "&endtime="
														+ endtime
														+ "&')\" "
														+ "class=\"page_next\"></a> 共"
														+ count
														+ "页 到第 "
														+ "<input type=\"text\" class=\"jump_num\" id=\"topage\"/> 页"
														+ "<a class=\"jump_btn\" onclick=\"gotoPage('CoachLog.do?username="
														+ username
														+"&oprateform="
														+ oprateform
														+ "&starttime="
														+ starttime
														+ "&endtime="
														+ endtime
														+ "&',"
														+ $("#pageSize").val()
														+ ")\">"
														+ "<a id='page_msg'></a>";
											}
											// 					  a[a.length]="<a href='#' onclick='addunit()' style='float: right;position: relative;right: 50px;padding: 0px; margin: 0px; top: 3px;'><img src='imgs/add_.png'></a>";
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