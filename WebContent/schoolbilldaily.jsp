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
<script type="text/javascript">
$(function(){
	var index = $("#index").val();
	$("#left_list_"+index).show();
	var j = $("#change_id").val();
	$("#change_"+j+index).addClass('left_list_mask');
	$("#driveschool").val($("#schoolname").val());
})

function search(){
	var starttime = $("#starttime").val();
	var addtime = $("#addtime").val();
	if(starttime!=''&&addtime!=''){
		if(starttime>addtime){
			alert("结束时间必须在开始时间之后！");
			return;
		}
	}
	var schoolname = $("#driveschool").val();
	window.location.href = "schoolbilldaily.do?addtime="+addtime+"&starttime="+starttime+"&schoolname="+schoolname;
}

function dataExport(){
	var starttime = $("#starttime").val();
	var addtime = $("#addtime").val();
	var schoolname = $("#driveschool").val();
	if (confirm("确认导出数据？")) {
		window.location.href="schoolBillNoticeExport.do?addtime="+addtime+"&starttime="+starttime+"&schoolname="+schoolname;
	}
}
</script>
<title>教校对账单</title>
</head>
<input type="hidden" id="index" value="10" />
<input type="hidden" id="change_id" value="7"/>
<input type="hidden" id="schoolname" value="${schoolname}"/>
<body onload="getTop()">
<div id="content">
<jsp:include page="left.jsp"/>
<div id="content_form">
<div id="content_form_top">
	<div class="searchbutton" onclick="search()">
		<img src="imgs/common/searchicon.png" width="22px" height="22px" style="margin-top: 9px;">
	</div>
	<div class="searchbutton" style="width:80px;">
		<div class="table_button_edit_icon"></div>
		<div class="table_button_text" style="font-size: 12px;line-height: 38px;" onclick="dataExport();">数据导出</div>
	</div>
	<div class="serchcontentdiv"style="float: right; margin-right: 50px; width: 180px"  >
	<input type="text" class="searchdiv" style="width:70px;font-family: 微软雅黑;text-align: center;" value="驾校" readonly="readonly"/>
		<select id="driveschool" class="searchdiv" style="width: 105px;">
				<option value="">无教校</option>
			<s:iterator value="driveSchoollist" var="driveSchool">
				<option value="${name}">${name}</option>
			</s:iterator>
		</select>
	</div>
	<div style="width: 347px; height: 40px; border: 1px solid #cfd9df; float: right; margin-right: 36px; margin-top: 22px;">
		<div style="width: 65px; text-align: center; line-height: 40px;border-right: 1px solid #cfd9df; float:left;">搜索时间</div>
		<input id="starttime" onclick="WdatePicker({startDate:'',dateFmt:'yyyy-MM-dd'})" type="text" class="searchdiv" style="width: 120px;text-align: center;font-family: 微软雅黑;" value="${starttime}">
		<input type="text" class="searchdiv" style="width: 30px;text-align: center;font-family: 微软雅黑;" value="到" readonly="readonly">
		<input id="addtime" onclick="WdatePicker({startDate:'',dateFmt:'yyyy-MM-dd'})" type="text" class="searchdiv" style="width: 120px;text-align: center;font-family: 微软雅黑;" value="${addtime}" >
	</div>
</div>
<div id="content_form_table" style="overflow: scroll;">
				<table border="0" cellspacing="0" cellpadding="0"
					style="width: 200%;">
					<tr class="tr_th">
						<th>序号</th>
						<th>驾校</th>
						<th>教练</th>
						<th>期初未完成订单数</th>
						<th>当期生成订单数</th>
						<th>当期完成订单数</th>
						<th>累计未完成订单数</th>
						<th>期初预约学时数</th>
						<th>当期预约学时数</th>
						<th>当期完成学时数</th>
						<th>累计未完成学时数</th>
						<th>初期未完成订单金额</th>
						<th>当期结算订单金额</th>
						<th>投诉订单退款金额</th>
						<th>期末未完成订单金额</th>
						<th>期初余额</th>
						<th>当期充值金额</th>
						<th>当期取款申请金额</th>
						<th>当期结算订单金额</th>
						<th>期末系统余额</th>
						<th>保证金额</th>
						<th>期末教练可取金额</th>
					</tr>
					<s:iterator value="schoolbilldaily" status="st">
						<tr class="tr_td">
							<td style="width: 52px;" class="border_right_bottom"><s:property value="#st.index+1"/></td>
							<td style="width: 150px;" class="border_right_bottom">${schoolname}</td>
							<td style="width: 150px;" class="border_right_bottom">${coachname}</td>
							<td style="width: 100px;" class="border_right_bottom">${unfinishorder}</td>
							<td style="width: 100px;" class="border_right_bottom">${createorder}</td>
							<td style="width: 100px;" class="border_right_bottom">${finishorder}</td>
							<td style="width: 100px;" class="border_right_bottom">${allunfinishorder}</td>
							<td style="width: 100px;" class="border_right_bottom">${beginorderhour}</td>
							<td style="width: 100px;" class="border_right_bottom">${orderhour}</td>
							<td style="width: 100px;" class="border_right_bottom">${finishhour}</td>
							<td style="width: 100px;" class="border_right_bottom">${allunfinishhour}</td>
							<td style="width: 100px;" class="border_right_bottom">${unfinishmoney}</td>
							<td style="width: 100px;" class="border_right_bottom">${jiesuanmoney}</td>
							<td style="width: 100px;" class="border_right_bottom">${drawbackmoney}</td>
							<td style="width: 100px;" class="border_right_bottom">${endunfinishmoney}</td>
							<td style="width: 100px;" class="border_right_bottom">${money}</td>
							<td style="width: 100px;" class="border_right_bottom">${rechargemoney}</td>
							<td style="width: 100px;" class="border_right_bottom">${drawmoney}</td>
							<td style="width: 100px;" class="border_right_bottom">${dayjiesuanmoney}</td>
							<td style="width: 100px;" class="border_right_bottom">${systemmoney}</td>
							<td style="width: 100px;" class="border_right_bottom">${gmoney}</td>
							<td style="width: 100px;" class="border_right_bottom">${endusermoney}</td>
						</tr>
					</s:iterator>
										<tr>
						<td colspan="10" style="height: 86px;">
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
					var starttime = $("#starttime").val();
					var addtime = $("#addtime").val();
					var schoolname = $("#schoolname").val();
					var a = [];
					  //总页数少于10 全部显示,大于10 显示前3 后3 中间3 其余....
					  if (pageindex == 1) {
						  //alert(pageindex);
					    a[a.length] = "<a onclick=\"\" class=\"hide_page_prev unclickprev on\"></a>";
					  } else {
					    a[a.length] = "<a onclick=\"previousPage("+pageindex+",'schoolbilldaily.do?addtime="+addtime+"&starttime="+starttime+"&schoolname="+schoolname+"&')\" class=\"page_prev\"></a>";
					  }
					  function setPageList() {
					    if (pageindex == i) {
					      a[a.length] = "<a onclick=\"goPage('schoolbilldaily.do?addtime="+addtime+"&starttime="+starttime+"&schoolname="+schoolname+"&',"+i+")\" class=\"on\">" + i + "</a>";
					    } else {
					      a[a.length] = "<a onclick=\"goPage('schoolbilldaily.do?addtime="+addtime+"&starttime="+starttime+"&schoolname="+schoolname+"&',"+i+")\">" + i + "</a>";
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
					      a[a.length] = "...<a onclick=\"goPage('schoolbilldaily.do?addtime="+addtime+"&starttime="+starttime+"&schoolname="+schoolname+"&',"+count+")\">" + count + "</a>";
					    } else if (pageindex >= count - 3) {
					      a[a.length] = "<a onclick=\"goPage('schoolbilldaily.do?addtime="+addtime+"&starttime="+starttime+"&schoolname="+schoolname+"&',1)\">1</a>...";
					      for (var i = count - 4; i <= count; i++) {
					        setPageList();
					      };
					    } else { //当前页在中间部分
					      a[a.length] = "<a onclick=\"goPage('schoolbilldaily.do?addtime="+addtime+"&starttime="+starttime+"&schoolname="+schoolname+"&',1)\">1</a>...";
					      for (var i = pageindex - 2; i <= pageindex+2; i++) {
					        setPageList();
					      }
					      a[a.length] = "...<a onclick=\"goPage('schoolbilldaily.do?addtime="+addtime+"&starttime="+starttime+"&schoolname="+schoolname+"&',"+count+")\">" + count + "</a>";
					    }
					  }
					  if (pageindex == count) {
						    a[a.length] = "<a onclick=\"\" class=\"hide_page_next unclicknext\"></a> 共"+count+"页  到第  "+
						    "<input type=\"text\" class=\"jump_num\" id=\"topage\"/> 页"+
						    "<a class=\"jump_btn\" onclick=\"gotoPage('schoolbilldaily.do?addtime="+addtime+"&starttime="+starttime+"&schoolname="+schoolname+"&',"+$("#pageSize").val()+")\")\">"+
						    "<a id='page_msg'></a>";
						  } else {
						    a[a.length] = 
						    	"<a onclick=\"nextPage("+$("#pageIndex").val()+",'schoolbilldaily.do?addtime="+addtime+"&starttime="+starttime+"&schoolname="+schoolname+"&')\" "+
						    	"class=\"page_next\"></a> 共"+count+"页 到第 "+
						    "<input type=\"text\" class=\"jump_num\" id=\"topage\"/> 页"+
						    "<a class=\"jump_btn\" onclick=\"gotoPage('schoolbilldaily.do?addtime="+addtime+"&starttime="+starttime+"&schoolname="+schoolname+"&',"+$("#pageSize").val()+")\">"+
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