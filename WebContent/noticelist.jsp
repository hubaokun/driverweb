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
<script type="text/javascript" src="systemnotice/js/systemnotice.js"></script>
<script type="text/javascript" src="systemconfig/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
$(function(){
	var index = $("#index").val();
	$("#left_list_"+index).show();
	var j = $("#change_id").val();
	$("#change_"+j+index).addClass('left_list_mask');
})
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
</style>


<title>系统通知</title>
</head>
<input type="hidden" id="index" value="${index}" />
<input type="hidden" id="change_id" value="${change_id}"/>
<body onload="getTop()">
	<div id="content">
		<jsp:include page="left.jsp" />
		<div id="content_form">
			<div id="content_form_top">
		<div class="addbutton" onclick="jumpSetNoticeJsp()">+&nbsp;新发布</div>
				
					<div class="searchbutton" onclick="searchnotice()">
						<img src="imgs/common/searchicon.png" width=22px height=22px
							style="margin-top: 9px;" >
					</div>
										
<div class="serchcontentdiv"style="float: right; margin-right: 50px; width: 309px"  >
		<input type="text" class="searchdiv" style="width: 71px;text-align: center;font-family: 微软雅黑;" value="发布时间" readonly="readonly">
		<input id="starttime" type="text" class="searchdiv" style="width: 100px;text-align: center;font-family: 微软雅黑;border-right: #fff;"  value="${noticestarttime}" onclick="WdatePicker({startDate:'',dateFmt:'yyyy-MM-dd'})" placeholder="&nbsp;请选择开始日期">
		<input type="text" class="searchdiv" style="width: 20px;text-align: center;font-family: 微软雅黑; border-right: #fff;" value="到" readonly="readonly">
		<input id="endtime" type="text" class="searchdiv" style="width: 105px;text-align: center;font-family: 微软雅黑;"value="${noticeendtime}" onclick="WdatePicker({startDate:'',dateFmt:'yyyy-MM-dd'})" placeholder="&nbsp;请选择结束日期">
	</div>

					

					







			</div>
			<div id="content_form_table">
				<table border="0" cellspacing="0" cellpadding="0"
					style="width: 98%;">
					<tr class="tr_th">
					
						
						<th>通知ID</th>
						<th>通知类型</th>
						<th>通知内容</th>
						<th>添加时间</th>
						<th>目标用户</th>
						<th>目标用户真实姓名</th>
						<th>操作</th>

					</tr>
					<s:iterator value="noticeslist" var="notice">
						<tr class="tr_td">
							
							<td style="width: 52px;" class="border_right_bottom">${noticeid}</td>
							<td style="width: 100px;" class="border_right_bottom">${category}</td>
							<td style="width: 300px;" class="border_right_bottom">${content}</td>
							<td style="width: 100px;" class="border_right_bottom">${addtime}</td>
							<s:if test="type==1">
							<td style="width: 100px;" class="border_right_bottom">教练</td>
							</s:if>
							<s:else>
							<td style="width: 100px;" class="border_right_bottom">学员</td>
							</s:else>
							<td style="width: 150px;" class="border_right_bottom">${realname}</td>
							<td style="width: 120px;" class="border_noright_bottom">
								
								<div class="table_edit_button" style="width: 70px;margin-left: 70px">
									<div class="table_button_edit_icon"></div>
									<div class="table_button_text" onclick="delnotice(${noticeid})">删除</div>
								</div>
								
								
							</td>

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
									<div id="untreatedpage"></div>
									<script type="text/javascript">
					//container 容器，count 总页数 pageindex 当前页数
					function setPage(container, count, pageindex) {
				var container = container;
					var count = parseInt(count);
					var pageindex = parseInt(pageindex);
					var starttime=$("#starttime").val();
					var endtime=$("#endtime").val();
					var index=$("#index").val();
					var j = $("#change_id").val();
					var a = [];
					  //总页数少于10 全部显示,大于10 显示前3 后3 中间3 其余....
					  if (pageindex == 1) {
						  //alert(pageindex);
					    a[a.length] = "<a onclick=\"\" class=\"hide_page_prev unclickprev on\"></a>";
					  } else {
					    a[a.length] = "<a onclick=\"previousPage("+pageindex+",'getNoticeList.do?&noticestarttime="+starttime+"&noticeendtime="+endtime+"&index="+index+"&change_id="+j+"&')\" class=\"page_prev\"></a>";
					  }
					  function setPageList() {
					    if (pageindex == i) {
					      a[a.length] = "<a onclick=\"goPage('getNoticeList.do?&noticestarttime="+starttime+"&noticeendtime="+endtime+"&index="+index+"&change_id="+j+"&',"+i+")\" class=\"on\">" + i + "</a>";
					    } else {
					      a[a.length] = "<a onclick=\"goPage('getNoticeList.do?&noticestarttime="+starttime+"&noticeendtime="+endtime+"&index="+index +"&change_id="+j+"&',"+i+")\">" + i + "</a>";
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
					      a[a.length] = "...<a onclick=\"goPage('getNoticeList.do?&noticestarttime="+starttime+"&noticeendtime="+endtime+"&index="+index +"&change_id="+j+"&',"+count+")\">" + count + "</a>";
					    } else if (pageindex >= count - 3) {
					      a[a.length] = "<a onclick=\"goPage('getNoticeList.do?&noticestarttime="+starttime+"&noticeendtime="+endtime+"&index="+index +"&change_id="+j+"&',1)\">1</a>...";
					      for (var i = count - 4; i <= count; i++) {
					        setPageList();
					      };
					    } else { //当前页在中间部分
					      a[a.length] = "<a onclick=\"goPage('getNoticeList.do?&noticestarttime="+starttime+"&noticeendtime="+endtime +"&index="+index+"&change_id="+j+"&',1)\">1</a>...";
					      for (var i = pageindex - 2; i <= pageindex+2; i++) {
					        setPageList();
					      }
					      a[a.length] = "...<a onclick=\"goPage('getNoticeList.do?&noticestarttime="+starttime+"&noticeendtime="+endtime+"&index="+index +"&change_id="+j+"&',"+count+")\">" + count + "</a>";
					    }
					  }
					  if (pageindex == count) {
						    a[a.length] = "<a onclick=\"\" class=\"hide_page_next unclicknext\"></a> 共"+count+"页  到第  "+
						    "<input type=\"text\" class=\"jump_num\" id=\"topage\"/> 页"+
						    "<a class=\"jump_btn\" onclick=\"gotoPage('getNoticeList.do?&noticestarttime="+starttime+"&noticeendtime="+endtime+"&index="+index +"&change_id="+j+"&',"+$("#pageSize").val()+")\")\">"+
						    "<a id='page_msg'></a>";
						  } else {
						    a[a.length] = 
						    	"<a onclick=\"nextPage("+$("#pageIndex").val()+",'getNoticeList.do?&noticestarttime="+starttime+"&noticeendtime="+endtime+"&index="+index +"&change_id="+j+"&')\" "+
						    	"class=\"page_next\"></a> 共"+count+"页 到第 "+
						    "<input type=\"text\" class=\"jump_num\" id=\"topage\"/> 页"+
						    "<a class=\"jump_btn\" onclick=\"gotoPage('getNoticeList.do?&noticestarttime="+starttime+"&noticeendtime="+endtime +"&',"+$("#pageSize").val()+")\">"+
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