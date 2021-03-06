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
<script type="text/javascript" src="systemconfig/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
$(function(){
	var index = $("#index").val();
	$("#left_list_"+index).show();
	var j = $("#change_id").val();
	$("#change_"+j+index).addClass('left_list_mask');
});
function dataToLead(){
	var index = $("#index").val();
	var change_id=$("#change_id").val();
	window.location.href="jumpCoachStudentToLead.do?index="+index+"&change_id="+change_id;
}

function coachStudentSearch() {
	var j = $("#change_id").val();
	var index = $("#index").val();
	var coachPhone = $("#coachPhone").val();
	var studentPhone = $("#studentPhone").val();
	window.location = "getCoachStudent.do?coachPhone="+coachPhone+"&studentPhone="+studentPhone+"&index="+index+"&change_id="+j;
}
</script>
<title>教练学员关系</title>
</head>
<input type="hidden" id="index" value="${index}" />
<input type="hidden" id="change_id" value="${change_id}"/>
<body onload="getTop()" style="min-width: 1500px">
	<div id="content">
		<jsp:include page="left.jsp" />
		<div id="content_form">
			<div id="content_form_top">
					<div class="searchbutton" onclick="coachStudentSearch()">
						<img src="imgs/common/searchicon.png" width=22px height=22px
							style="margin-top: 9px;" >
					</div>
					<div class="searchbutton" style="width:80px;">
						<div class="table_button_edit_icon"></div>
						<div class="table_button_text" style="font-size: 12px;line-height: 38px;" onclick="dataToLead();">数据导入</div>
					</div>
					<div class="serchcontentdiv" style="float: left; margin-left: 50px; width: 183px" >
						<input  type="text" class="searchdiv" style="width: 77px;text-align: center;font-family: 微软雅黑;" value="教练手机号" readonly="readonly">
						<input id="coachPhone" type="text" class="searchdiv" style="width:100px;font-family: 微软雅黑;" value="${coachPhone}" onkeyup="value=value.replace(/[^\d]/g,'')"/>
					</div>

					<div class="serchcontentdiv" style="float: left; margin-left: 50px; width: 183px" >
						<input type="text" class="searchdiv" style="width: 77px;text-align: center;font-family: 微软雅黑;" value="学员手机号" readonly="readonly">
						<input id="studentPhone" type="text" class="searchdiv" style="width:100px;font-family: 微软雅黑;" value="${studentPhone}" onchange="phoneisNum()" onkeyup="value=value.replace(/[^\d]/g,'')"/>
					</div>
			</div>
			<div id="content_form_table">
				<table border="0" cellspacing="0" cellpadding="0"
					style="width: 98%;">
					<tr class="tr_th">
						<th>教练ID</th>
						<th>教练姓名</th>
						<th>教练手机号</th>
						<th>学员ID</th>
						<th>学员姓名</th>
						<th>学员手机号</th>
						<th>学习费用总数</th>
						<th>学习小时总数</th>
					</tr>
					<s:iterator value="coachstudentlist" var="suser">
						<tr class="tr_td">
							<td style="width: 80px;" class="border_right_bottom">${coachid}</td>
							<td style="width: 80px;" class="border_right_bottom">${coachname}</td>
							<td style="width: 200px;" class="border_right_bottom">${coachPhone}</td>
							<td style="width: 80px;" class="border_right_bottom">${studentid}</td>
							<td style="width: 80px;" class="border_right_bottom">${studentname}</td>
							<td style="width: 200px;" class="border_right_bottom">${studentPhone}</td>
							<td style="width: 200px;" class="border_right_bottom">${money}</td>
							<td style="width: 200px;" class="border_noright_bottom">${hour }</td>
						</tr>
					</s:iterator>
					<tr>
						<td colspan="8" style="height: 86px;">
							<div style="float: left; margin-top: 34px; margin-left: 20px;">
								总计：${total}条</div> <!-- 下部翻页 -->
							<div style="margin-top: 34px; margin-right: 20px;">
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
					var coachPhone=$("#coachPhone").val();
					var studentPhone=$("#studentPhone").val();
					var j = $("#change_id").val();
					var index=$("#index").val();
					var a = [];
					  //总页数少于10 全部显示,大于10 显示前3 后3 中间3 其余....
					  if (pageindex == 1) {
						  //alert(pageindex);
					    a[a.length] = "<a onclick=\"\" class=\"hide_page_prev unclickprev on\"></a>";
					  } else {
					    a[a.length] = "<a onclick=\"previousPage("+pageindex+",'getCoachStudent.do?coachPhone="+coachPhone+"&studentPhone="+studentPhone+"&index="+index+"&change_id="+j+"&')\" class=\"page_prev\"></a>";
					  }
					  function setPageList() {
					    if (pageindex == i) {
					      a[a.length] = "<a onclick=\"goPage('getCoachStudent.do?coachPhone="+coachPhone+"&studentPhone="+studentPhone+"&index="+index+"&change_id="+j+"&',"+i+")\" class=\"on\">" + i + "</a>";
					    } else {
					      a[a.length] = "<a onclick=\"goPage('getCoachStudent.do?coachPhone="+coachPhone+"&studentPhone="+studentPhone+"&index="+index+"&change_id="+j+"&',"+i+")\">" + i + "</a>";
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
					      a[a.length] = "...<a onclick=\"goPage('getCoachStudent.do?coachPhone="+coachPhone+"&studentPhone="+studentPhone+"&index="+index+"&change_id="+j+"&',"+count+")\">" + count + "</a>";
					    } else if (pageindex >= count - 3) {
					      a[a.length] = "<a onclick=\"goPage('getCoachStudent.do?coachPhone="+coachPhone+"&studentPhone="+studentPhone+"&index="+index+"&change_id="+j+"&',1)\">1</a>...";
					      for (var i = count - 4; i <= count; i++) {
					        setPageList();
					      };
					    } else { //当前页在中间部分
					      a[a.length] = "<a onclick=\"goPage('getCoachStudent.do?coachPhone="+coachPhone+"&studentPhone="+studentPhone+"&index="+index+"&change_id="+j+"&',1)\">1</a>...";
					      for (var i = pageindex - 2; i <= pageindex+2; i++) {
					        setPageList();
					      }
					      a[a.length] = "...<a onclick=\"goPage('getCoachStudent.do?coachPhone="+coachPhone+"&studentPhone="+studentPhone+"&index="+index+"&change_id="+j+"&',"+count+")\">" + count + "</a>";
					    }
					  }
					  if (pageindex == count) {
						    a[a.length] = "<a onclick=\"\" class=\"hide_page_next unclicknext\"></a> 共"+count+"页  到第  "+
						    "<input type=\"text\" class=\"jump_num\" id=\"topage\"/> 页"+
						    "<a class=\"jump_btn\" onclick=\"gotoPage('getCoachStudent.do?coachPhone="+coachPhone+"&studentPhone="+studentPhone+"&index="+index+"&change_id="+j+"&',"+$("#pageSize").val()+")\")\">"+
						    "<a id='page_msg'></a>";
						  } else {
						    a[a.length] = 
						    	"<a onclick=\"nextPage("+$("#pageIndex").val()+",'getCoachStudent.do?coachPhone="+coachPhone+"&studentPhone="+studentPhone+"&index="+index+"&change_id="+j+"&')\" "+
						    	"class=\"page_next\"></a> 共"+count+"页 到第 "+
						    "<input type=\"text\" class=\"jump_num\" id=\"topage\"/> 页"+
						    "<a class=\"jump_btn\" onclick=\"gotoPage('getCoachStudent.do?coachPhone="+coachPhone+"&studentPhone="+studentPhone+"&index="+index+"&change_id="+j+"&',"+$("#pageSize").val()+")\">"+
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