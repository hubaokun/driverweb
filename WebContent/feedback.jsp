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
<link rel="stylesheet" href="css/page.css" type="text/css" media="screen" />
<script src="js/jquery-1.11.2.min.js"></script>
<script src="other/js/other.js"></script>
<script src="js/common.js"></script>
<script type="text/javascript" src="js/page.js"></script>
<script type="text/javascript" src="other/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
$(function(){
	var index = $("#index").val()
	$("#left_list_"+index).show();
	var j = $("#change_id").val();
	$("#change_"+j+index).addClass('left_list_mask');
	
	var error=$("#errormessage").val();
	if(error==''){
	}else{
		alert(error);
		window.location="getFeedback.do";
	}
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
	height: 400px;
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
	height: 300px;
	margin: 0 auto;
	display: none;
}
</style>

<title>意见反馈列表</title>
</head>
<input type="hidden" id="index" value="9" />
<input type="hidden" id="change_id" value="0"/>
<input type="hidden" id="errormessage" value="${errormessage}"/>
<body onload="getTop()">
<div id="content">
<jsp:include page="left.jsp"/>
<div id="content_form">
<div id="content_form_top">
					<div class="searchbutton" onclick="searchfeedback()">

						<img src="imgs/common/searchicon.png" width=22px height=22px
							style="margin-top: 9px;" >
					</div> 
					
					
					<div class="serchcontentdiv" style="float: left; margin-left: 50px; width: 156px" >
						<input  type="text" class="searchdiv" style="width: 60px;text-align: center;font-family: 微软雅黑;" value="用户姓名" readonly="readonly">
						<input id="realname" type="text" class="searchdiv" style="width:90px;font-family: 微软雅黑;" value="${searchname}"/>
					</div>

					<div class="serchcontentdiv" style="float: left; margin-left: 50px; width: 156px" >
						<input type="text" class="searchdiv" style="width: 60px;text-align: center;font-family: 微软雅黑;" value="手机号码" readonly="readonly">
						<input id="phone" type="text" class="searchdiv" style="width:90px;font-family: 微软雅黑;" value="${searchphone}" onkeyup="value=value.replace(/[^\d]/g,'')" maxlength="11"/>
					</div>
					<div class="serchcontentdiv" style="float: left; margin-left: 50px; width:251px" >
						<input  type="text" class="searchdiv" style="width: 60px;text-align: center;font-family: 微软雅黑;" value="日期" readonly="readonly">
						<input  type="text" class="searchdiv" style="width:90px;font-family: 微软雅黑;" value="${starttime }" onclick="WdatePicker({startDate:'',dateFmt:'yyyy-MM-dd'})" name="starttime" id="starttime" placeholder="选择开始时间" />
						<input type="text" class="searchdiv" style="width:90px;font-family: 微软雅黑;" value="${endtime }" onclick="WdatePicker({startDate:'',dateFmt:'yyyy-MM-dd'})" name="endtime" id="endtime" placeholder="输入结束时间" />
					</div>
	
	
</div>
<div id="content_form_table">
<table  border="0" cellspacing="0" cellpadding="0" style="width:98%;">
<tr class="tr_th">
<th>意见ID</th>
<th>用户真实姓名</th>
<th>用户类型</th>
<th>用户手机号码</th>
<th>内容</th>
<th>时间</th>
<th>操作</th>
</tr>
<s:iterator value="feedbacklist" var="feedback">
<tr class="tr_td">
<td  style="width:50px;" class="border_right_bottom">${feedbackid}</td>
<td  style="width:100px;" class="border_right_bottom">${fromrealname }</td>
<s:if test="from_type==1">
<td  style="width:100px;" class="border_right_bottom">教练</td>
</s:if>
<s:else>
<td  style="width:100px;" class="border_right_bottom">学员</td>
</s:else>
<td  style="width:100px;" class="border_right_bottom">${fromphone }</td>
<td  style="width:300px;" class="border_right_bottom">${content }</td>
<td  style="width:100px;" class="border_right_bottom">
<s:date name="addtime"  format="yyyy-MM-dd HH:mm:ss" /></td>
<td  style="width:80px;" class="border_noright_bottom">

<div class="table_del_button">
<div class="table_button_del_icon"></div>
<div class="table_button_text" onclick="delfeedback('${feedbackid}')">删除</div>
</div>
<div class="table_edit_button">
<div class="table_button_edit_icon"></div>
<div class="table_button_text" onclick="showaddfeedback('${fromid}','${from_type}')">回复</div>
</div>
</td>
</tr>
</s:iterator>
<tr>
						<td colspan="7" style="height: 86px;">
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
					var searchname = $("#realname").val();
					var searchphone = $("#phone").val();
					var starttime = $("#starttime").val();
					var endtime = $("#endtime").val();
					var index=$("#index").val();
					var j=$("#change_id").val();
					var a = [];
					  //总页数少于10 全部显示,大于10 显示前3 后3 中间3 其余....
					  if (pageindex == 1) {
						  //alert(pageindex);
					    a[a.length] = "<a onclick=\"\" class=\"hide_page_prev unclickprev on\"></a>";
					  } else {
					    a[a.length] = "<a onclick=\"previousPage("+pageindex+",'getFeedback.do?searchname=" + searchname
						+ "&searchphone=" + searchphone + "&starttime=" + starttime
						+ "&endtime=" + endtime+"&index="+index+"&change_id="+j+"&')\" class=\"page_prev\"></a>";
					  }
					  function setPageList() {
					    if (pageindex == i) {
					      a[a.length] = "<a onclick=\"goPage('getFeedback.do?searchname=" + searchname
							+ "&searchphone=" + searchphone + "&starttime=" + starttime
							+ "&endtime=" + endtime+"&index="+index+"&change_id="+j+"&',"+i+")\" class=\"on\">" + i + "</a>";
					    } else {
					      a[a.length] = "<a onclick=\"goPage('getFeedback.do?searchname=" + searchname
							+ "&searchphone=" + searchphone + "&starttime=" + starttime
							+ "&endtime=" + endtime+"&index="+index+"&change_id="+j+"&',"+i+")\">" + i + "</a>";
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
					      a[a.length] = "...<a onclick=\"goPage('getFeedback.do?searchname=" + searchname
							+ "&searchphone=" + searchphone + "&starttime=" + starttime
							+ "&endtime=" + endtime+"&index="+index+"&change_id="+j+"&',"+count+")\">" + count + "</a>";
					    } else if (pageindex >= count - 3) {
					      a[a.length] = "<a onclick=\"goPage('getFeedback.do?searchname=" + searchname
							+ "&searchphone=" + searchphone + "&starttime=" + starttime
							+ "&endtime=" + endtime+"&index="+index+"&change_id="+j+"&',1)\">1</a>...";
					      for (var i = count - 4; i <= count; i++) {
					        setPageList();
					      };
					    } else { //当前页在中间部分
					      a[a.length] = "<a onclick=\"goPage('getFeedback.do?searchname=" + searchname
							+ "&searchphone=" + searchphone + "&starttime=" + starttime
							+ "&endtime=" + endtime+"&index="+index+"&change_id="+j+"&',1)\">1</a>...";
					      for (var i = pageindex - 2; i <= pageindex+2; i++) {
					        setPageList();
					      }
					      a[a.length] = "...<a onclick=\"goPage('getFeedback.do?searchname=" + searchname
							+ "&searchphone=" + searchphone + "&starttime=" + starttime
							+ "&endtime=" + endtime+"&index="+index+"&change_id="+j+"&',"+count+")\">" + count + "</a>";
					    }
					  }
					  if (pageindex == count) {
						    a[a.length] = "<a onclick=\"\" class=\"hide_page_next unclicknext\"></a> 共"+count+"页  到第  "+
						    "<input type=\"text\" class=\"jump_num\" id=\"topage\"/> 页"+
						    "<a class=\"jump_btn\" onclick=\"gotoPage('getFeedback.do?searchname=" + searchname
							+ "&searchphone=" + searchphone + "&starttime=" + starttime
							+ "&endtime=" + endtime+"&index="+index+"&change_id="+j+"&',"+$("#pageSize").val()+")\")\">"+
						    "<a id='page_msg'></a>";
						  } else {
						    a[a.length] = 
						    	"<a onclick=\"nextPage("+$("#pageIndex").val()+",'getFeedback.do?searchname=" + searchname
								+ "&searchphone=" + searchphone + "&starttime=" + starttime
								+ "&endtime=" + endtime+"&index="+index+"&change_id="+j+"&')\" "+
						    	"class=\"page_next\"></a> 共"+count+"页 到第 "+
						    "<input type=\"text\" class=\"jump_num\" id=\"topage\"/> 页"+
						    "<a class=\"jump_btn\" onclick=\"gotoPage('getFeedback.do?searchname=" + searchname
							+ "&searchphone=" + searchphone + "&starttime=" + starttime
							+ "&endtime=" + endtime+"&index="+index+"&change_id="+j+"&',"+$("#pageSize").val()+")\">"+
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

<!-- 回复反馈弹框 -->
<div id="mask" class="mask"></div>
	<div id="mask_sec" style="position: fixed; width: 100%; height: 300px;z-index: 300;">
		<div id="mask_last" class="mask_last">
		<input id="fromid" type="hidden"/>
		<input id="fromtype" type="hidden"/>
		<div style="position: fixed; width: 400px; height: 300px;background: #4cc2ff;margin-left: 50px;margin-top: 50px;">
		<textarea id="feedbackresult" style="width: 300px;height: 200px;resize:none;margin-left: 50px;margin-top: 20px;font-size: 18px;font-family:微软雅黑;color: #e5e5e5" onclick="checkfeedback()" onblur="checkfeedbacknull()">请输入回复的正文内容</textarea>
		<input type="button" style="width: 100px;height: 40px;margin: auto;margin-left: 100px;margin-top: 20px;font-size: 18px" value="回复" onclick="addfeedback()">
		<input type="button" style="width: 100px;height: 40px;margin: auto;margin-top: -40px;font-size: 18px" value="取消" onclick="unshowaddfeedback()">
		</div>
		</div>
	</div>
</body>
</html>