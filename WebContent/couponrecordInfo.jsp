<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.daoshun.menu.SideMenu"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/style.css" rel="stylesheet" type="text/css">
<link href="css/ordinary.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="css/page.css" type="text/css"
	media="screen" />
<script src="js/jquery-1.11.2.min.js"></script>
<script src="coupon/js/coupon.js"></script>
<script src="js/common.js"></script>
<script type="text/javascript" src="js/page.js"></script>
<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
	$(function() {
		var index = $("#index").val();
		$("#left_list_" + index).show();
		var j = $("#change_id").val();
		$("#change_" + j + index).addClass('left_list_mask');

		var coupontype = $("#hiddencoupontype").val();
		$("#searchtype").val(coupontype);

		var state = $("#hiddenstate").val();
		$("#state").val(state);
		
		var starttime = $("#hiddenstarttime").val();
		var endtime = $("#hiddenendtime").val();
		if(starttime=='' && endtime==''){
			var d = new Date();
			$("#starttime").val( d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate() );
			$("#endtime").val( d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate() );
		}else{
			$("#starttime").val(starttime);
			$("#endtime").val(endtime);
		}
		
		var hiddenownertype = $("#hiddenownertype").val();
		$("#ownertype").val(hiddenownertype);

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
		if (endtime == ''){
			var d = new Date();
			document.getElementById("endtime").value=d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();
		}
		$("#searchform").submit();
	}
	
	function dataExport(){
		if (confirm("确认导出小巴券发放信息？")) {
			var couponrecorddate = document.getElementsByName("C1");
			var data='';
			var startdate=$("#datastartdate").val();
			var enddate=$("#dataenddate").val();
			
			for(var i=0;i<couponrecorddate.length;i++){
				if(couponrecorddate[i].checked == true){
					data+=couponrecorddate[i].value+",";
				}
			}
			if(startdate>enddate){
				alert("开始时间不能大于结束时间");
			}else if(data==''){
				alert("请选择导出信息！");
			}else{
				window.location.href="couponrecorddataExport.do?couponrecorddate="+data+"&starttime="+startdate+"&endtime="+enddate;
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

<title>小巴券发放记录</title>
</head>
<input type="hidden" id="index" value="8" />
<input type="hidden" id="change_id" value="1" />
<input type="hidden" id="errormessage" value="${errormessage}" />
<input type="hidden" id="searchownertype" value="${ownertype}" />
<input type="hidden" id="searchtypetype" value="${coupontype}" />
<body onload="getSelfTop()">
	<div id="content">
		<jsp:include page="left.jsp" />
		<div id="content_form">
			<div id="content_form_top">
				<form id="searchform" action="getCouponRecordInfo.do">
					<div id="content_form_top">

						<div class="searchbutton" onclick="searchfromsubmit();">
							<img src="imgs/common/searchicon.png" width=22px height=22px
								style="margin-top: 9px;">
						</div>
						
						<div class="searchbutton" style="width:80px;">
							<div class="table_button_edit_icon"></div>
							<div class="table_button_text" style="font-size: 12px;line-height: 38px;" onclick="alertDataExportBox();">数据导出</div>
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
							style="float: left; margin-left: 50px; width: 175px">
							<input type="text" class="searchdiv"
								style="width: 85px; text-align: center; font-family: 微软雅黑;"
								value="优惠券类型" readonly="readonly"> <select
								id="searchtype" name="coupontype" class="searchdiv"
								style="width: 85px;">
								<option id="searchtype0" value="">全部</option>
								<option id="searchtype1" value="1">时间券</option>
								<option id="searchtype2" value="2">抵价券</option>
							</select> <input type="hidden" id="hiddencoupontype" value="${coupontype}">
						</div>

						<div class="serchcontentdiv"
							style="float: left; margin-left: 50px; width: 196px">
							<select id="ownertype" name="ownertype" class="searchdiv"
								style="width: 100px;">
								<option value="" selected="selected">全部</option>
								<option value="0">平台发放</option>
								<option value="1">驾校发放</option>
								<option value="2">教练发放</option>
							</select> <input type="hidden" id="hiddenownertype" value="${ownertype }">
							<input id="inputordertotal" type="text" value="${ownerkey }"
								name="ownerkey" class="searchdiv"
								style="width: 91px; text-align: center; font-family: 微软雅黑;"
								value="" onclick="inputordertotal.focus();this.select();">
						</div>

						<div class="serchcontentdiv"
							style="float: left; margin-left: 50px; width: 405px">
							<input type="text" class="searchdiv"
								style="width: 119px; text-align: center; font-family: 微软雅黑;"
								value="发放时间" readonly="readonly"> <%SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
								String today=formatter.format(new Date());%><input id="starttime"
								name="starttime"
								onclick="WdatePicker({startDate:'',dateFmt:'yyyy-MM-dd'})"
								type="text" class="searchdiv"
								style="width: 120px; text-align: center; font-family: 微软雅黑;"
								value="${starttime}">  <input type="hidden" id="hiddenstarttime" value="${starttime}">
							<input type="text" class="searchdiv"  
								style="width: 30px; text-align: center; font-family: 微软雅黑;"
								value="到" readonly="readonly"> <input id="endtime"
								name="endtime"
								onclick="WdatePicker({startDate:'',dateFmt:'yyyy-MM-dd'})"
								type="text" class="searchdiv"
								style="width: 120px; text-align: center; font-family: 微软雅黑;"
								value="${endtime}"> <input type="hidden" id="hiddenendtime" value="${endtime}">
						</div>

						<div class="serchcontentdiv"
							style="float: left; margin-left: 50px; width: 175px">
							<input type="text" class="searchdiv"
								style="width: 85px; text-align: center; font-family: 微软雅黑;"
								value="优惠券状态" readonly="readonly"> <select id="state"
								name="state" class="searchdiv" style="width: 85px;">
								<option id="state0" value="">全部</option>
								<option id="state0" value="0">未使用</option>
								<option id="state1" value="1">已使用</option>
								<option id="state2" value="2">已作废</option>
							</select> <input type="hidden" id="hiddenstate" value="${state}">
						</div>
					</div>
				</form>
			</div>
			<div id="content_form_table">
				<table border="0" cellspacing="0" cellpadding="0"
					style="width: 98%;">
					<tr class="tr_th">
						<th>用户名</th>
						<th>发券人</th>
						<th>总张数</th>
						<th>已使用张数</th>
						<th>发放时间</th>
						<th>操作</th>
					</tr>
					<s:iterator value="couponrecordlist" var="couponrecordlist">
						<tr class="tr_td">
							<td style="width: 100px;" class="border_right_bottom"><a href="getCouponCoachInfo.do?username=${userphone}" 
										style="text-decoration: none; cursor: pointer;">${usernick}</a></td>
							<td style="width: 150px;" class="border_right_bottom"><s:if
									test="ownertype==0">平台发行</s:if> 
									<s:elseif test="ownertype==1">驾校发行:${schoolname }
									</s:elseif> <s:elseif test="ownertype==2">教练发行:${cusername }
									</s:elseif>
							</td>
							<td style="width: 200px;" class="border_right_bottom">${totalcount }</td>
							
							<td style="width: 120px;" class="border_right_bottom">${usecount }</td>
							
							<td style="width: 200px;" class="border_right_bottom"><s:date
									name="gettime" format="yyyy-MM-dd HH:mm:ss" /></td>
							<td style="width: 200px;" class="border_noright_bottom">
								<a href="getCouponRecordList.do?userid=${userid}"
										style="text-decoration: none; cursor: pointer;">明细</a>
							</td>
							
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
											var coupontype = $(
													"#searchtypetype").val();
											var ownertype = $(
													"#searchownertype").val();
											var ownerkey = $("#inputordertotal")
													.val();
											var starttime = $("#starttime")
													.val();
											var endtime = $("#endtime").val();
											var state = $("#hiddenstate").val();
											var a = [];
											//若结束日期为空，则设置截止日期为当前日期(防止两个日期都为空的情况下显示的数据为当日的数据)
											if (endtime == ''){
												var d = new Date();
												document.getElementById("endtime").value = d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();
											}
											//总页数少于10 全部显示,大于10 显示前3 后3 中间3 其余....
											if (pageindex == 1) {
												//alert(pageindex);
												a[a.length] = "<a onclick=\"\" class=\"hide_page_prev unclickprev on\"></a>";
											} else {
												a[a.length] = "<a onclick=\"previousPage("
														+ pageindex
														+ ",'getCouponRecordInfo.do?username="
														+ username
														+ "&coupontype="
														+ coupontype
														+ "&ownertype="
														+ ownertype
														+ "&ownerkey="
														+ ownerkey
														+ "&starttime="
														+ starttime
														+ "&endtime="
														+ endtime
														+ "&state="
														+ state
														+ "&')\" class=\"page_prev\"></a>";
											}
											function setPageList() {
												if (pageindex == i) {
													a[a.length] = "<a onclick=\"goPage('getCouponRecordInfo.do?username="
															+ username
															+ "&coupontype="
															+ coupontype
															+ "&ownertype="
															+ ownertype
															+ "&ownerkey="
															+ ownerkey
															+ "&starttime="
															+ starttime
															+ "&endtime="
															+ endtime
															+ "&state="
															+ state
															+ "&',"
															+ i
															+ ")\" class=\"on\">"
															+ i + "</a>";
												} else {
													a[a.length] = "<a onclick=\"goPage('getCouponRecordInfo.do?username="
															+ username
															+ "&coupontype="
															+ coupontype
															+ "&ownertype="
															+ ownertype
															+ "&ownerkey="
															+ ownerkey
															+ "&starttime="
															+ starttime
															+ "&endtime="
															+ endtime
															+ "&state="
															+ state
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
													a[a.length] = "...<a onclick=\"goPage('getCouponRecordInfo.do?username="
															+ username
															+ "&coupontype="
															+ coupontype
															+ "&ownertype="
															+ ownertype
															+ "&ownerkey="
															+ ownerkey
															+ "&starttime="
															+ starttime
															+ "&endtime="
															+ endtime
															+ "&state="
															+ state
															+ "&',"
															+ count
															+ ")\">"
															+ count + "</a>";
												} else if (pageindex >= count - 3) {
													a[a.length] = "<a onclick=\"goPage('getCouponRecordInfo.do?username="
															+ username
															+ "&coupontype="
															+ coupontype
															+ "&ownertype="
															+ ownertype
															+ "&ownerkey="
															+ ownerkey
															+ "&starttime="
															+ starttime
															+ "&endtime="
															+ endtime
															+ "&state="
															+ state
															+ "&',1)\">1</a>...";
													for ( var i = count - 4; i <= count; i++) {
														setPageList();
													}
													;
												} else { //当前页在中间部分
													a[a.length] = "<a onclick=\"goPage('getCouponRecordInfo.do?username="
															+ username
															+ "&coupontype="
															+ coupontype
															+ "&ownertype="
															+ ownertype
															+ "&ownerkey="
															+ ownerkey
															+ "&starttime="
															+ starttime
															+ "&endtime="
															+ endtime
															+ "&state="
															+ state
															+ "&',1)\">1</a>...";
													for ( var i = pageindex - 2; i <= pageindex + 2; i++) {
														setPageList();
													}
													a[a.length] = "...<a onclick=\"goPage('getCouponRecordInfo.do?username="
															+ username
															+ "&coupontype="
															+ coupontype
															+ "&ownertype="
															+ ownertype
															+ "&ownerkey="
															+ ownerkey
															+ "&starttime="
															+ starttime
															+ "&endtime="
															+ endtime
															+ "&state="
															+ state
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
														+ "<a class=\"jump_btn\" onclick=\"gotoPage('getCouponRecordInfo.do?username="
														+ username
														+ "&coupontype="
														+ coupontype
														+ "&ownertype="
														+ ownertype
														+ "&ownerkey="
														+ ownerkey
														+ "&starttime="
														+ starttime
														+ "&endtime="
														+ endtime
														+ "&state="
														+ state
														+ "&',"
														+ $("#pageSize").val()
														+ ")\")\">"
														+ "<a id='page_msg'></a>";
											} else {
												a[a.length] = "<a onclick=\"nextPage("
														+ $("#pageIndex").val()
														+ ",'getCouponRecordInfo.do?username="
														+ username
														+ "&coupontype="
														+ coupontype
														+ "&ownertype="
														+ ownertype
														+ "&ownerkey="
														+ ownerkey
														+ "&starttime="
														+ starttime
														+ "&endtime="
														+ endtime
														+ "&state="
														+ state
														+ "&')\" "
														+ "class=\"page_next\"></a> 共"
														+ count
														+ "页 到第 "
														+ "<input type=\"text\" class=\"jump_num\" id=\"topage\"/> 页"
														+ "<a class=\"jump_btn\" onclick=\"gotoPage('getCouponRecordInfo.do?username="
														+ username
														+ "&coupontype="
														+ coupontype
														+ "&ownertype="
														+ ownertype
														+ "&ownerkey="
														+ ownerkey
														+ "&starttime="
														+ starttime
														+ "&endtime="
														+ endtime
														+ "&state="
														+ state
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
		
		<div id="alertbox" class="alertbox"></div>
	<div id="alertbox_sec" style="position: fixed; width: 100%; height: 300px;z-index: 300;">
		<div id="alertbox_last" class="alertbox_last">
		<div style="position: fixed; width: 500px; height: 250px;background: #4cc2ff;margin-left: 50px;margin-top: 50px;">
		<div style="font-size: 15px;width: 110px;margin: auto;">请选择导出信息</div>
		<div>全选<input type="checkbox" id="allrecordcheck" value="0" onclick="selectallrecord()"></div>
		<input type="checkbox" style="margin-top: 5px;" name="C1"  id="a1" value="0"/> 用户名
		<input type="checkbox" style="margin-left: 5px;margin-top: 5px;" name="C1"  id="a1" value="1"> 手机号
		<input type="checkbox" style="margin-left: 5px;margin-top: 5px;" name="C1"  id="a1" value="2"> 面值
		<input type="checkbox" style="margin-left: 5px;margin-top: 5px;" name="C1"  id="a1" value="3"> 发券人
		<input type="checkbox" style="margin-left: 5px;margin-top: 5px;" name="C1"  id="a1" value="4"> 发放时间
		<input type="checkbox" style="margin-left: 5px;margin-top: 5px;" name="C1"  id="a1" value="5"> 使用状态
		<input type="checkbox" style="margin-left: 5px;margin-top: 5px;" name="C1"  id="a1" value="6"> 使用时间
		<input type="checkbox" style="margin-left: 5px;margin-top: 5px;" name="C1"  id="a1" value="7"> 使用截止日期


		导出时间选择:开始时间
		<input id="datastartdate" onclick="WdatePicker({startDate:'',dateFmt:'yyyy-MM-dd'})" type="text" class="searchdiv" style="width: 135px;height:20px;text-align: center;font-family: 微软雅黑;" value="" >
		结束时间
		<input id="dataenddate" onclick="WdatePicker({startDate:'',dateFmt:'yyyy-MM-dd'})" type="text" class="searchdiv" style="width: 135px;height:20px;text-align: center;font-family: 微软雅黑;" value="" >
		
		<input type="button" style="width: 100px;height: 40px;margin: auto;margin-left: 130px;margin-top: 100px;font-size: 18px" value="确定" onclick="dataExport()">
		<input type="button" style="width: 100px;height: 40px;margin: auto;margin-left: 180x;margin-top: -40px;font-size: 18px" value="取消" onclick="unAlertDataExportBox()">
		</div>
		</div>
	</div>
		
	</div>



	<!-- 数据导出弹框 -->
	
	
</body>
</html>