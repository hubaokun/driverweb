<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.daoshun.menu.SideMenu"%>
<%@page import="org.apache.struts2.util.Counter"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/style.css" rel="stylesheet" type="text/css">
<link href="css/ordinary.css" rel="stylesheet" type="text/css">
<link href="css/daily.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="css/page.css" type="text/css"
	media="screen" />
<script src="js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/page.js"></script>
<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
	$(function() {
		var index = $("#index").val();
		$("#left_list_" + index).show();
		var j = $("#change_id").val();
		$("#change_" + j + index).addClass('left_list_mask');
	})

	function search() {
		var starttime = $("#starttime").val();
		var endtime = $("#endtime").val();
		var schoolid=$("#schoolid").val();
		if (endtime < starttime)
			alert("结束时间必须大于开始时间，请重新选择！");
		else
			window.location.href = "getCoinReportForSchool.do?starttime="
					+ starttime + "&endtime=" + endtime+"&schoolid="+schoolid;
	}

	function dataExport(){
		var starttime = $("#starttime").val();
		var endtime = $("#endtime").val();
		var driverschoolid= $("#schoolid").val();
		if(driverschoolid==0)
		{
			alert("请先选择驾校");
			return;
		}
		if(endtime<starttime)
			alert("结束时间必须大于开始时间，请重新选择！");
		else
		{
			if (confirm("确认导出小巴币数据？")) {
				window.location.href="CoinReportMontlyExportBySchool.do?starttime="+starttime+"&endtime="+endtime+"&schoolid="+driverschoolid;
			}
		}
	}
	function checkdetail(coachid, type) {
		var starttime = $("#starttime").val();
		var endtime = $("#endtime").val();
		window.location.href = "GetCoinReportMontlyDetail.do?starttime="
				+ starttime + "&endtime=" + endtime + "&coachid=" + coachid
				+ "&ownertype=" + type;

	}
</script>
<title>小巴币月报（驾校）</title>
<style type="text/css">
.tables {
	white-space: nowrap;
}

.tables th, td {
	border: 1px solid #D7E2EF !important;
	border-bottom: none;
}
.nousetr
{
	width:0px;
	border:none;
}
</style>
</head>
<input type="hidden" id="index" value="10" />
<input type="hidden" id="change_id" value="1" />
<body onload="getTop()">
	<div id="content">
		<jsp:include page="left.jsp" />
		<div id="content_form">
			<div id="content_form_top">

				<div
					style="width: 300px; height: 40px; border: 1px solid #cfd9df; float: left; margin-right: 36px; margin-top: 22px;">
					<div
						style="width: 65px; text-align: center; line-height: 40px; border-right: 1px solid #cfd9df; float: left;">选择驾校</div>
					<select id="schoolid" value="${schoolname}" name="schoolid"
						onclick="WdatePicker({endDate:'',dateFmt:'yyyy-MM-dd'})"
						style="width: 225px; height: 39px; border: 0px; padding-left: 8px; float: left;">
						<s:iterator value="driveSchoollist" status="st">
						 	<option value="${schoolid}">${name}</option>
						</s:iterator>
					</select>
				</div>
				
				<div
					style="width: 300px; height: 40px; border: 1px solid #cfd9df; float:left; margin-right: 36px; margin-top: 22px;">
					<div
						style="width: 65px; text-align: center; line-height: 40px; border-right: 1px solid #cfd9df; float: left;">开始时间</div>
					<input id="starttime" value="${starttime }" name="starttime"
						onclick="WdatePicker({startDate:'',dateFmt:'yyyy-MM-dd'})"
						style="width: 225px; height: 39px; border: 0px; padding-left: 8px; float: left;" />
				</div>
				
				<div
					style="width: 300px; height: 40px; border: 1px solid #cfd9df; float:left; margin-right: 36px; margin-top: 22px;">
					<div
						style="width: 65px; text-align: center; line-height: 40px; border-right: 1px solid #cfd9df; float: left;">结束时间</div>
					<input id="endtime" value="${endtime }" name="endtime"
						onclick="WdatePicker({endDate:'',dateFmt:'yyyy-MM-dd'})"
						style="width: 225px; height: 39px; border: 0px; padding-left: 8px; float: left;" />
				</div>
				
				<div class="searchbutton" onclick="search()">
					<img src="imgs/common/searchicon.png" width="22px" height="22px"
						style="margin-top: 9px;float:left;">
				</div>
				<div class="searchbutton" style="width: 80px;float:left !important;">
					<div class="table_button_edit_icon"></div>
					<div class="table_button_text"
						style="font-size: 12px; line-height: 38px;float:left;"
						onclick="dataExport();">数据导出</div>
				</div>
			</div>
			<div style="overflow: scroll; width: 100%; height: 100%;">
				<div id="content_form_table"
					style=" width: 1788px;">
					<table border="0" cellspacing="0" cellpadding="0"
						style="width: 1258px; float: left;" class="tables">
						<tr class="tr_th">
							<th rowspan="1" colspan="13">教练 /驾校</th>

						</tr>
						<tr>
							<th rowspan="2" colspan="1">序号</th>
							<th rowspan="2" colspan="1">券类</th>
							<th rowspan="2" colspan="1">教练所在驾校</th>
							<th rowspan="2" colspan="1">发放教练手机号</th>
							<th rowspan="2" colspan="1">发放教练</th>
							<th rowspan="2" colspan="1">小巴币面值(1元/个)</th>
							<th rowspan="2" colspan="1">发放小巴币(个)</th>
							<th rowspan="1" colspan="2">教练账户已兑换小巴币(个)</th>
							<th rowspan="1" colspan="2">教练账户未兑换小巴币(个)</th>
							<th rowspan="2" colspan="1">已结算学时（小时）</th>
							<th rowspan="2" colspan="1">已结算小巴币(个)</th>
						</tr>
						<tr>
							<th>驾校</th>
							<th>教练</th>
							<th>驾校</th>
							<th>教练</th>
						</tr>
						<s:iterator value="coinReportForSchoolData" status="st">
							<tr style="height:${count*46}px !important;">
								<td style="width: 5%;" class="border_right_bottom"
									rowspan="1" colspan="1"><s:property
										value="#st.index+1" /></td>
								<td style="width: 5%;" class="border_right_bottom"
									rowspan="1" colspan="1">小巴币</td>
								<td style="width: 5%;" class="border_right_bottom"
									rowspan="1" colspan="1">${c_school}</td>
								<td style="width: 5%;" class="border_right_bottom"
									rowspan="1" colspan="1">${c_phone}</td>
								<td style="width: 5%;" class="border_right_bottom"
									rowspan="1" colspan="1">${c_name}</td>
								<td style="width: 5%;" class="border_right_bottom"
									rowspan="1" colspan="1">${varValue}</td>
								<td style="width: 5%;" class="border_right_bottom"
									rowspan="1" colspan="1">${c_coinnumber}</td>
								<td style="width: 5%;" class="border_right_bottom"
									rowspan="1" colspan="1">${c_scoinchange}</td>
								<td style="width: 5%;" class="border_right_bottom"
									rowspan="1" colspan="1">${c_coinchange}</td>
								<td style="width: 5%;" class="border_right_bottom"
									rowspan="1" colspan="1">${c_unscoinchange}</td>
								<td style="width: 5%;" class="border_right_bottom"
									rowspan="1" colspan="1">${c_uncoinchange}</td>
								<td style="width: 5%;" class="border_right_bottom"
									rowspan="1" colspan="1">${c_classhour}</td>
								<td style="width: 5%;" class="border_right_bottom"
									rowspan="1" colspan="1">${c_coinpay}</td>
							</tr>
<%-- 							<s:if test="count!=1">
								<s:bean name="org.apache.struts2.util.Counter" id="counter">
									<s:param name="first" value="1" />
									<s:param name="last" value="count" />
									<s:iterator>
										<tr class="nousetr"></tr>
									</s:iterator>
								</s:bean>
							</s:if> --%>
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

									</s:if>
								</div>
							</td>
						</tr>
					</table>
					<table border="0" cellspacing="0" cellpadding="0"
						style="width: 500px; float: left;" class="tables">
						<tr class="tr_th">
							<th rowspan="1" colspan="7">学员</th>
						</tr>
						<tr>
							<th rowspan="2" colspan="1">学员手机号</th>
							<th rowspan="2" colspan="1">学员姓名</th>
							<th rowspan="1" colspan="2">获取小巴币</th>
							<th rowspan="1" colspan="2">已使用小巴币</th>
							<th rowspan="2" colspan="1">已使用学时</th>
						</tr>
						<tr>
							<th>驾校</th>
							<th>教练</th>
							<th>驾校</th>
							<th>教练</th>
						</tr>
						<s:iterator value="coinReportForSchoolData" status="st">
							<s:iterator value="detailsOfStudent">
								<tr>
									<s:if test="c_name!=null && c_name!=''">
									<td style="width: 5%;" class="border_right_bottom" rowspan="1"
										colspan="1">${c_phone}</td>
									<td style="width: 5%;" class="border_right_bottom" rowspan="1"
										colspan="1">${c_name}</td>
									<td style="width: 5%;" class="border_right_bottom" rowspan="1"
										colspan="1">${c_scoinnumber}</td>
									<td style="width: 5%;" class="border_right_bottom" rowspan="1"
										colspan="1">${c_coinnumber}</td>
									<td style="width: 5%;" class="border_right_bottom" rowspan="1"
										colspan="1">${c_usedscoinnumber}</td>
									<td style="width: 5%;" class="border_right_bottom" rowspan="1"
										colspan="1">${c_usedcoinnumber}</td>
									<td style="width: 5%;" class="border_right_bottom" rowspan="1"
										colspan="1">${c_classhour}</td>
									</s:if>
									<s:else>
									<td style="width: 5%;" class="border_right_bottom" rowspan="1"
										colspan="1"></td>
										<td style="width: 5%;" class="border_right_bottom" rowspan="1"
										colspan="1"></td>
										<td style="width: 5%;" class="border_right_bottom" rowspan="1"
										colspan="1"></td>
										<td style="width: 5%;" class="border_right_bottom" rowspan="1"
										colspan="1"></td>
										<td style="width: 5%;" class="border_right_bottom" rowspan="1"
										colspan="1"></td>
										<td style="width: 5%;" class="border_right_bottom" rowspan="1"
										colspan="1"></td>
										<td style="width: 5%;" class="border_right_bottom" rowspan="1"
										colspan="1"></td>
									</s:else>
								</tr>
							</s:iterator>
						</s:iterator>
					</table>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		//container 容器，count 总页数 pageindex 当前页数
		function setPage(container, count, pageindex) {
			var container = container;
			var count = parseInt(count);
			var pageindex = parseInt(pageindex);
			var starttime = $("#starttime").val();
			var endtime = $("#endtime").val();
			var a = [];
			//总页数少于10 全部显示,大于10 显示前3 后3 中间3 其余....
			if (pageindex == 1) {
				//alert(pageindex);
				a[a.length] = "<a onclick=\"\" class=\"hide_page_prev unclickprev on\"></a>";
			} else {
				a[a.length] = "<a onclick=\"previousPage(" + pageindex
						+ ",'GetCoinReportMontly.do?starttime=" + starttime
						+ "&endtime=" + endtime
						+ "&')\" class=\"page_prev\"></a>";
			}
			function setPageList() {
				if (pageindex == i) {
					a[a.length] = "<a onclick=\"goPage('GetCoinReportMontly.do?starttime="
							+ starttime
							+ "&endtime="
							+ endtime
							+ "&',"
							+ i
							+ ")\" class=\"on\">" + i + "</a>";
				} else {
					a[a.length] = "<a onclick=\"goPage('GetCoinReportMontly.do?starttime="
							+ starttime
							+ "&endtime="
							+ endtime
							+ "&',"
							+ i
							+ ")\">" + i + "</a>";
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
					a[a.length] = "...<a onclick=\"goPage('GetCoinReportMontly.do?starttime="
							+ starttime
							+ "&endtime="
							+ endtime
							+ "&',"
							+ count
							+ ")\">" + count + "</a>";
				} else if (pageindex >= count - 3) {
					a[a.length] = "<a onclick=\"goPage('GetCoinReportMontly.do?starttime="
							+ starttime
							+ "&endtime="
							+ endtime
							+ "&',1)\">1</a>...";
					for (var i = count - 4; i <= count; i++) {
						setPageList();
					}
					;
				} else { //当前页在中间部分
					a[a.length] = "<a onclick=\"goPage('GetCoinReportMontly.do?starttime="
							+ starttime
							+ "&endtime="
							+ endtime
							+ "&',1)\">1</a>...";
					for (var i = pageindex - 2; i <= pageindex + 2; i++) {
						setPageList();
					}
					a[a.length] = "...<a onclick=\"goPage('GetCoinReportMontly.do?starttime="
							+ starttime
							+ "&endtime="
							+ endtime
							+ "&',"
							+ count
							+ ")\">" + count + "</a>";
				}
			}
			if (pageindex == count) {
				a[a.length] = "<a onclick=\"\" class=\"hide_page_next unclicknext\"></a> 共"
						+ count
						+ "页  到第  "
						+ "<input type=\"text\" class=\"jump_num\" id=\"topage\"/> 页"
						+ "<a class=\"jump_btn\" onclick=\"gotoPage('GetCoinReportMontly.do?starttime="
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
						+ ",'GetCoinReportMontly.do?starttime="
						+ starttime
						+ "&endtime="
						+ endtime
						+ "&')\" "
						+ "class=\"page_next\"></a> 共"
						+ count
						+ "页 到第 "
						+ "<input type=\"text\" class=\"jump_num\" id=\"topage\"/> 页"
						+ "<a class=\"jump_btn\" onclick=\"gotoPage('GetCoinReportMontly.do?starttime="
						+ starttime + "&endtime=" + endtime + "&',"
						+ $("#pageSize").val() + ")\">"
						+ "<a id='page_msg'></a>";
			}
			//					  a[a.length]="<a href='#' onclick='addunit()' style='float: right;position: relative;right: 50px;padding: 0px; margin: 0px; top: 3px;'><img src='imgs/add_.png'></a>";
			container.innerHTML = a.join("");
		}
		setPage(document.getElementById("untreatedpage"), parseInt($(
				"#pageSize").val()), parseInt($("#pageIndex").val()));
	</script>
</body>
</html>