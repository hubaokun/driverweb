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
<link rel="stylesheet" href="css/page.css" type="text/css"media="screen" />
<script src="js/jquery-1.11.2.min.js"></script>
<script src="js/common.js"></script>
<script type="text/javascript" src="js/page.js"></script>
<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="location/js/location.js"></script>
<script type="text/javascript">
$(function(){
	var index = $("#index").val();
	$("#left_list_"+index).show();
	var j = $("#change_id").val();
	$("#change_"+j+index).addClass('left_list_mask');
})

function search(){
	var addtime = $("#addtime").val();
	window.location.href = "coachdaily.do?addtime="+addtime;
}

function dataExport(){
	var addtime = $("#addtime").val();
	if (confirm("确认导出数据？")) {
		window.location.href="coachNoticeExport.do?addtime="+addtime;
	}
}
//教练搜索
function search() {
	var province = $("#province").val();
	var city = $("#city").val();
	var area = $("#area").val();
	var phone = $("#phone").val();
	var driver_school = $("#driver_school").val();
	var startdate = $("#startdate").val();
	var enddate = $("#enddate").val();
	window.location.href = "coachreport.do?provinceid=" + province
			+ "&cityid=" + city + "&areaid=" + area
			+ "&driver_school=" + driver_school + "&startdate=" + startdate + "&enddate="
			+ enddate ;
}
</script>
<title>教练报表</title>
</head>
<input type="hidden" id="index" value="10" />
<input type="hidden" id="change_id" value="2"/>
<body onload="getTop()">
<div id="content">
<jsp:include page="left.jsp"/>
<div id="content_form">
<div id="content_form_top">
	<div class="searchbutton" onclick="search()">
		<img src="imgs/common/searchicon.png" width="22px" height="22px" style="margin-top: 9px;">
	</div>
	
	<div class="serchcontentdiv" style="float: left; margin-left: 50px; width: 180px" >
						<input type="text" class="searchdiv" style="width: 65px;text-align: center;font-family: 微软雅黑;" value="省份" readonly="readonly">
						<s:if test="provincelist!=null">
						<s:select id="province" name="provinceid" list="provincelist" listKey="provinceid" 
						listValue="province" cssClass="searchdiv" onchange="tofindCity(this.value)" headerValue="不限" headerKey="null"></s:select></s:if>
					</div>
					
					<div class="serchcontentdiv" style="float: left; margin-left: 50px; width: 180px" >
						<input type="text" class="searchdiv" style="width: 65px;text-align: center;font-family: 微软雅黑;" value="市" readonly="readonly">
						 <select id="city" name="pro" onchange="tofindArea(this.value)"></select>
					</div>
					<div class="serchcontentdiv" style="float: left; margin-left: 50px; width: 180px" >
						<input type="text" class="searchdiv11" style="width: 65px;text-align: center;font-family: 微软雅黑;" value="区" readonly="readonly">
						<select id="area" ></select>
					</div>
					<div class="serchcontentdiv"style="float: left; margin-left: 50px; width: 160px"  >
						<input type="text" class="searchdiv" style="width:70px;font-family: 微软雅黑;text-align: center;" value="所属驾校" readonly="readonly"/>
								
								<s:select list="driveSchoollist" cssClass="searchdiv" 
								name="driver_school" listKey="schoolid" listValue="name" headerKey="0" headerValue="不限"></s:select>
					</div>
					<div class="serchcontentdiv"style="float: left; margin-left: 50px; width: 309px"  >
						<input type="text" class="searchdiv" style="width: 71px;text-align: center;font-family: 微软雅黑;" value="注册时间" readonly="readonly">
						<input id="startdate" type="text" class="searchdiv" style="width: 99px;text-align: center;font-family: 微软雅黑;border-right: #fff;"  value="${minsdate}" onclick="WdatePicker({startDate:'',dateFmt:'yyyy-MM-dd'})" placeholder="&nbsp;请选择开始日期">
						<input type="text" class="searchdiv" style="width: 20px;text-align: center;font-family: 微软雅黑; border-right: #fff;" value="到" readonly="readonly">
						<input id="enddate" type="text" class="searchdiv" style="width: 105px;text-align: center;font-family: 微软雅黑;"value="${maxsdate}" onclick="WdatePicker({startDate:'',dateFmt:'yyyy-MM-dd'})" placeholder="&nbsp;请选择结束日期">
					</div>
</div>
<div id="content_form_table">
				<table border="0" cellspacing="0" cellpadding="0"
					style="width: 100%;">
					<tr class="tr_th">
						<th>教练编号</th>
						<th>教练姓名</th>
						<th>电话</th>
						<th>订单总数</th>
						<th>已完成订单数</th>
						<th>已取消订单数</th>
						<th>待完成订单数</th>
						<th>驾校名称</th>
						<th>驾校编号</th>
					</tr>
					<s:iterator value="cuserlist" status="st">
						<tr class="tr_td">
							<td style="width: 52px;" class="border_right_bottom">${coachid}</td>
							<td style="width: 150px;" class="border_right_bottom">${realname}</td>
							<td style="width: 150px;" class="border_right_bottom">${phone}</td>
							<td style="width: 100px;" class="border_right_bottom">${sumnum}</td>
							<td style="width: 100px;" class="border_right_bottom">${overnum }</td>
							<td style="width: 100px;" class="border_right_bottom">${cancelnum }</td>
							<td style="width: 100px;" class="border_right_bottom">${waitnum }</td>
							<td style="width: 100px;" class="border_right_bottom">${drive_school}</td>
							<td style="width: 100px;" class="border_right_bottom">${drive_schoolid}</td>
							
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
					function setPage(container, count, pageindex) {
				var container = container;
					var count = parseInt(count);
					var pageindex = parseInt(pageindex);
					var addtime = $("#addtime").val();
					var a = [];
					  //总页数少于10 全部显示,大于10 显示前3 后3 中间3 其余....
					  if (pageindex == 1) {
						  //alert(pageindex);
					    a[a.length] = "<a onclick=\"\" class=\"hide_page_prev unclickprev on\"></a>";
					  } else {
					    a[a.length] = "<a onclick=\"previousPage("+pageindex+",'coachdaily.do?addtime="+addtime+"&')\" class=\"page_prev\"></a>";
					  }
					  function setPageList() {
					    if (pageindex == i) {
					      a[a.length] = "<a onclick=\"goPage('coachdaily.do?addtime="+addtime+"&',"+i+")\" class=\"on\">" + i + "</a>";
					    } else {
					      a[a.length] = "<a onclick=\"goPage('coachdaily.do?addtime="+addtime+"&',"+i+")\">" + i + "</a>";
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
					      a[a.length] = "...<a onclick=\"goPage('coachreport.do?addtime="+addtime+"&',"+count+")\">" + count + "</a>";
					    } else if (pageindex >= count - 3) {
					      a[a.length] = "<a onclick=\"goPage('coachreport.do?addtime="+addtime+"&',1)\">1</a>...";
					      for (var i = count - 4; i <= count; i++) {
					        setPageList();
					      };
					    } else { //当前页在中间部分
					      a[a.length] = "<a onclick=\"goPage('coachreport.do?addtime="+addtime+"&',1)\">1</a>...";
					      for (var i = pageindex - 2; i <= pageindex+2; i++) {
					        setPageList();
					      }
					      a[a.length] = "...<a onclick=\"goPage('coachreport.do?addtime="+addtime+"&',"+count+")\">" + count + "</a>";
					    }
					  }
					  if (pageindex == count) {
						    a[a.length] = "<a onclick=\"\" class=\"hide_page_next unclicknext\"></a> 共"+count+"页  到第  "+
						    "<input type=\"text\" class=\"jump_num\" id=\"topage\"/> 页"+
						    "<a class=\"jump_btn\" onclick=\"gotoPage('coachreport.do?addtime="+addtime+"&',"+$("#pageSize").val()+")\")\">"+
						    "<a id='page_msg'></a>";
						  } else {
						    a[a.length] = 
						    	"<a onclick=\"nextPage("+$("#pageIndex").val()+",'coachreport.do?addtime="+addtime+"&')\" "+
						    	"class=\"page_next\"></a> 共"+count+"页 到第 "+
						    "<input type=\"text\" class=\"jump_num\" id=\"topage\"/> 页"+
						    "<a class=\"jump_btn\" onclick=\"gotoPage('coachreport.do?addtime="+addtime+"&',"+$("#pageSize").val()+")\">"+
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