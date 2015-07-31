<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.List" %>
<%@page import="com.daoshun.menu.SideMenu" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="css/page.css" type="text/css" media="screen" />
<link href="css/style.css" rel="stylesheet" type="text/css">
<link href="css/ordinary.css" rel="stylesheet" type="text/css">
<script src="js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script  type="text/javascript" src="coach/js/coachdetail.js"></script>
<script type="text/javascript" src="systemconfig/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
$(function(){
	var index = $("#index").val();
	$("#left_list_"+index).show();
	var j = $("#change_id").val();
	$("#change_"+j+index).addClass('left_list_mask');
	
	var state = $("#state").val();
	
	if(state == 2){
		//alert(state);
		$("#compeletebutton").hide();
	}
	if(state == 6){
		//alert(state);
		$("#addcontentform").hide();
	}
	
	$("#nav").css("min-height","1260px");
})
</script>

<style type="text/css">
.change {
	position: fixed;
	top: 0px;
	bottom: 0px;
	left: 0px;
	right: 0px;
	background: #707070;
	opacity: 0.5;
	display: none;
}

.change_last {
	position: fixed;
	
	width: 500px;
	height: 300px;
	margin: 0 auto;
	display: none;
}
</style>

<title>用户跟进状态信息</title>
</head>

<body onload="getTop()" style="min-width: 1580px">
<div id="content" >
<jsp:include page="left.jsp" />
<div id="content_form">

<!-- <form action="" id="userstateForm" enctype="multipart/form-data" method="post">-->
	<input type="hidden" id="index" value="1"  name="index"/>
	<input type="hidden" id="change_id" value="3" name="change_id"/>
	<input type="hidden" value="${pageCount }" id="pageSize" />
	<input type="hidden" value="${pageIndex }" id="pageIndex" />
	<input type="hidden" id="state" value="${state}"  name="state"/>
	<div id="content_form_top">
		<div class="delbutton">&nbsp;基本资料</div>
		<div class="addbutton" style="float: right;margin-right: 10px;" onclick="gobackuserstate()">返&nbsp;回</div>
	</div>
	
	<div id="content_form_table">
	<table  border="0" cellspacing="0" cellpadding="0" style="margin-left: 27px;width: 98%;">
	
	<tr class="tr_th">
		<th>ID</th>
		<th>学员姓名</th>
		<th style="width:150px;">电话号码</th>
		<th>性别</th>
		<th>出生日期</th>
		<th>地址</th>
	</tr>
	
	<tr class="tr_td">
	
		<td  style="width:150px;min-width:150px;background: #e5e5e5" class="border_right_bottom"><input name="studentid" id="studentid" value="${suser.studentid }" style="text-align: center;width:150px;background: #e5e5e5;height: 35px;font-size:18px"  readonly="readonly"/></td>
		<td  style="width:150px;min-width:150px;background: #e5e5e5" class="border_right_bottom"><input name="editrealname" id="realname" value="${suser.realname }" style="text-align: center;width:150px;height: 35px;font-size:18px;background: #e5e5e5"  readonly/></td>
		<td  style="width:150px;min-width:150px;background: #e5e5e5" class="border_right_bottom"><input value="${suser.phone }" style="text-align: center;width:150px;background: #e5e5e5;height: 35px;font-size:18px"  readonly="readonly"/></td>
		<s:if test="suser.gender==1">
			<td  style="width:150px;min-width:150px;background: #e5e5e5" class="border_right_bottom">
			<!-- <input selected="selected"  value="男" readonly/>
			<option   value="2" >女</option> -->
			<input name="gender" id="gender" value="男" style="text-align: center;width:150px;background: #e5e5e5;height: 35px;font-size:18px"  readonly="readonly"/>
			</td>
		</s:if>
		<s:else>
			<td  style="width:150px;min-width:150px;background: #e5e5e5" class="border_right_bottom">
			<!--<select  name="editgender" style="font-size:18px;background: #e5e5e5">-->
			<input name="gender" id="gender" value="女" style="text-align: center;width:150px;background: #e5e5e5;height: 35px;font-size:18px"  readonly="readonly"/>
			<!-- </select>-->
			</td>
		</s:else>
		<td  style="width:80px;min-width:150px;background: #e5e5e5" class="border_right_bottom"><input name="editbirthday" value="${suser.birthday }" style="text-align: center;width:150px;height: 35px;font-size:18px;background: #e5e5e5" readonly /></td>
		<td  style="width:150px;min-width: 150px;background: #e5e5e5" class="border_right_bottom"><input name="editcity" value="${suser.address }" style="text-align: center;width:150px;height: 35px;font-size:18px;background: #e5e5e5"  readonly /></td>
	</tr>
	
	</table>
	
	<!-- 添加跟进记录 -->
	<div id="addcontentform">
	<div id="content_form_top">
	</div>
	
	<form action="addContent.do" method="post" id="contentform_submit">
		<input type="hidden" id="studentid" name="studentid" value="${suser.studentid }" />
		<input type="hidden" id="dealpeopleid" name="dealpeopleid" value="${session.userid}"/>
		<textarea id="addcontent" name="addcontent" rows="8" cols="100" style="margin-left: 27px;margin-top:10px;width: 95%;" placeholder="请输入跟进信息"></textarea>
	</form>
	<div class="addbutton" style="float: left;margin-left: 30px;" onclick="addcontent()">添加跟进记录</div>
	<div id="compeletebutton" class="addbutton" style="float: left;margin-left: 30px;" onclick="changestate()">报名完成</div>
	
	</div>
	
	<!-- 历史跟进记录 -->
	<div id="content_form_top">
	</div>
	<table  border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;margin-left: 27px;width: 98%;">
	<tr class="tr_th">
		<th>处理人</th>
		<th>跟进时间</th>
		<th style="width:300px;">处理内容</th>
	</tr>
	<s:iterator value="suserstalist" var="suserstalist">
		<tr class="tr_td">
			<td style="width:95px;min-width: 95px;background: #e5e5e5" class="border_right_bottom"><input name="editdealpeople" id="dealpeople" value="${dealpeople}" style="text-align: center;width:95px;height: 35px;background: #e5e5e5;font-size:18px" readonly /></td>
			<td style="width:150px;min-width: 150px;background: #e5e5e5" class="border_right_bottom"><input name="editdealtime" value="<s:date name="dealtime" format="yyyy-MM-dd HH:mm:ss" />" style="text-align: center;width:175px;height: 35px;background: #e5e5e5;font-size:18px" readonly/></td>
			<td style="width:300px;min-width: 300px;background: #e5e5e5" class="border_right_bottom"><input name="editcontent"  value="${content }"   style="text-align: center;width:100%;height: 35px;background: #e5e5e5;font-size:18px" readonly/></td>
		</tr>
	</s:iterator>
	
	<!-- 分页 -->
	<tr>
						<td colspan="7" style="height: 86px;">
							<div style="float: left; margin-top: 34px; margin-left: 20px;">
								总计：${total}条</div> 
							<!-- 下部翻页 -->
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
					  if (pageindex == 1){
						  //alert(pageindex);
					    a[a.length] = "<a onclick=\"\" class=\"hide_page_prev unclickprev on\"></a>";
					  } else {
					    a[a.length] = "<a onclick=\"previousPage("+pageindex+",'getStudentstateByKeyword.do?studentid="+studentid+"&index="+index+"&change_id"+j+"&')\" class=\"page_prev\"></a>";
					  }
					  function setPageList() {
					    if (pageindex == i) {
					      a[a.length] = "<a onclick=\"goPage('getStudentstateByKeyword.do?studentid="+studentid+"&index="+index+"&change_id"+j+"&',"+i+")\" class=\"on\">" + i + "</a>";
					    } else {
					      a[a.length] = "<a onclick=\"goPage('getStudentstateByKeyword.do?studentid="+studentid+"&index="+index+"&change_id"+j+"&',"+i+")\">" + i + "</a>";
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
					      a[a.length] = "...<a onclick=\"goPage('getStudentstateByKeyword.do?studentid="+studentid+"&index="+index+"&change_id"+j+"&',"+count+")\">" + count + "</a>";
					    } else if (pageindex >= count - 3) {
					      a[a.length] = "<a onclick=\"goPage('getStudentstateByKeyword.do?studentid="+studentid+"&index="+index+"&change_id"+j+"&',1)\">1</a>...";
					      for (var i = count - 4; i <= count; i++) {
					        setPageList();
					      };
					    } else { //当前页在中间部分
					      a[a.length] = "<a onclick=\"goPage('getStudentstateByKeyword.do?studentid="+studentid+"&index="+index+"&change_id"+j+"&',1)\">1</a>...";
					      for (var i = pageindex - 2; i <= pageindex+2; i++) {
					        setPageList();
					      }
					      a[a.length] = "...<a onclick=\"goPage('getStudentstateByKeyword.do?studentid="+studentid+"&index="+index+"&change_id"+j+"&',"+count+")\">" + count + "</a>";
					    }
					  }
					  if (pageindex == count) {
						    a[a.length] = "<a onclick=\"\" class=\"hide_page_next unclicknext\"></a> 共"+count+"页  到第  "+
						    "<input type=\"text\" class=\"jump_num\" id=\"topage\"/> 页"+
						    "<a class=\"jump_btn\" onclick=\"gotoPage('getStudentstateByKeyword.do?studentid="+studentid+index+"&change_id"+j+"&',"+$("#pageSize").val()+")\")\">"+
						    "<a id='page_msg'></a>";
						  } else {
						    a[a.length] = 
						    	"<a onclick=\"nextPage("+$("#pageIndex").val()+",'getStudentstateByKeyword.do?studentid="+studentid+"&index="+index+"&change_id"+j+"&')\" "+
						    	"class=\"page_next\"></a> 共"+count+"页 到第 "+
						    "<input type=\"text\" class=\"jump_num\" id=\"topage\"/> 页"+
						    "<a class=\"jump_btn\" onclick=\"gotoPage('getStudentstateByKeyword.do?studentid="+studentid+"&searchphone="+endtime +"&',"+$("#pageSize").val()+")\">"+
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