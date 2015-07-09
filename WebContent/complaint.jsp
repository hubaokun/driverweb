<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.daoshun.menu.SideMenu"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/style.css" rel="stylesheet" type="text/css">
<link href="css/ordinary.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="css/page.css" type="text/css" media="screen" />
<script src="js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/page.js"></script>
<script type="text/javascript" src="js/complaint.js"></script>
<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
polltime = "<s:property value='#session.polltime'/>";
setInterval('showNewOrder()',5000);
$(function(){
	var index = $("#index").val()
	$("#left_list_"+index).show();
	var j = $("#change_id").val();
	$("#change_"+j+index).addClass('left_list_mask');
	var hidstate = $("#hidstate").val();
	if(hidstate==''){
		$("#state").val(0);
	}else{
		$("#state").val(hidstate);
	}
})
</script>

<title>投诉列表</title>
</head>
<audio src="imgs/common/beep.mp3"
	preload="auto" id="audio" loop="loop"></audio>
<input type="hidden" id="index" name="index" value="${index}" />
<input type="hidden" id="hidstate" value="${state}" />
<input type="hidden" id="change_id" value="${change_id}"/>
<body onload="getTop()">
	<div id="content">
		<jsp:include page="left.jsp" />
		<div id="content_form">
			<div id="content_form_top">
					<div class="searchbutton">
						<img src="imgs/common/searchicon.png" width=22px height=22px
							style="margin-top: 9px;" onclick="searchComplaint()">
					</div>
					<div class="serchcontentdiv" style="float: left; margin-left: 50px; width: 154px" >
						<input  type="text" class="searchdiv" style="width: 50px;text-align: center;font-family: 微软雅黑;" value="投诉ID" readonly="readonly">
						<input id="complaintid" type="text" onkeyup="value=value.replace(/[^\d]/g,'')" class="searchdiv" style="width:100px;font-family: 微软雅黑;" value="${complaintid}" maxlength="10"/>
					</div>

					<div class="serchcontentdiv" style="float: left; margin-left: 50px; width: 180px" >
						<input type="text" class="searchdiv" style="width: 65px;text-align: center;font-family: 微软雅黑;" value="学员电话" readonly="readonly">
						<input id="studentphone" type="text" onkeyup="value=value.replace(/[^\d]/g,'')" class="searchdiv" style="width:111px;font-family: 微软雅黑;" value="${studentphone}" maxlength="11"/>
					</div>
					
					<div class="serchcontentdiv" style="float: left; margin-left: 50px; width: 180px" >
						<input type="text" class="searchdiv" style="width: 65px;text-align: center;font-family: 微软雅黑;" value="教练电话" readonly="readonly">
						<input id="coachphone" type="text" onkeyup="value=value.replace(/[^\d]/g,'')" class="searchdiv" style="width:111px;font-family: 微软雅黑;" value="${coachphone}" maxlength="11"/>
					</div>
					
	<div class="serchcontentdiv"style="float: left; margin-left: 50px; width: 164px"  >
	<input type="text" class="searchdiv" style="width: 50px;text-align: center;font-family: 微软雅黑;" value="类型" readonly="readonly">
		<select id="type" class="searchdiv" style="width: 110px;">
			<s:if test="type==1">
			<option value="0" >&nbsp;&nbsp;不限</option>
			<option value="1" selected="selected">学员投诉教练</option>
			<option value="2" >教练投诉学员</option>
			</s:if>
			<s:elseif test="type==2">
			<option value="0" >&nbsp;&nbsp;不限</option>
			<option value="1" >学员投诉教练</option>
			<option value="2" selected="selected">教练投诉学员</option>
			</s:elseif>
			<s:else>
			<option value="0" selected="selected">&nbsp;&nbsp;不限</option>
			<option value="1" >学员投诉教练</option>
			<option value="2" >教练投诉学员</option>
			</s:else>
		</select>
	</div>
									
<div class="serchcontentdiv"style="float: left; margin-left: 50px; width: 401px"  >
		<input type="text" class="searchdiv" style="width: 120px;text-align: center;font-family: 微软雅黑;" value="投诉时间区间" readonly="readonly">
		<input id="minsdate" onclick="WdatePicker({startDate:'',dateFmt:'yyyy-MM-dd'})" type="text" class="searchdiv" style="width: 120px;text-align: center;font-family: 微软雅黑;" value="${minsdate}" >
		<input type="text" class="searchdiv" style="width: 30px;text-align: center;font-family: 微软雅黑;" value="到" readonly="readonly">
		<input id="maxsdate" onclick="WdatePicker({startDate:'',dateFmt:'yyyy-MM-dd'})" type="text" class="searchdiv" style="width: 119px;text-align: center;font-family: 微软雅黑;" value="${maxsdate}">
</div>
	<div class="serchcontentdiv"style="float: left; margin-left: 50px; width: 130px"  >
	<input type="text" class="searchdiv" style="width: 40px;text-align: center;font-family: 微软雅黑;" value="状态" readonly="readonly">
		<select id="state" class="searchdiv" style="width: 85px;">
			<option value="0" >不限</option>
			<option value="1" >未解决</option>
			<option value="2" >已解决</option>
			<option value="3" >已取消</option>
		</select>
	</div>
			</div>
			<div id="content_form_table">
				<table border="0" cellspacing="0" cellpadding="0"
					style="width: 100%;">
					<tr class="tr_th">
						<th>ID</th>
						<th>学员姓名</th>
						<th>学员电话</th>
						<th>教练姓名</th>
						<th>教练电话</th>
						<th>投诉类型</th>
						<th>投诉原因</th>
						<th>状态</th>
						<th>投诉时间</th>
						<th>操作</th>
					</tr>
					<s:iterator value="complaintlist" var="cuser" id="list">
						<tr class="tr_td">
							<td style="width: 52px;" class="border_right_bottom">${complaintid}</td>
							<td style="width: 100px;" class="border_right_bottom">${student.realname}</td>
							<td style="width: 100px;" class="border_right_bottom">${student.phone}</td>
							<td style="width: 100px;" class="border_right_bottom">${coach.realname}</td>
							<td style="width: 100px;" class="border_right_bottom">${coach.phone}</td>
							<s:if test="type==1">
							<td style="width: 100px;" class="border_right_bottom">学员投诉教练</td>
							</s:if>
							<s:else>
							<td style="width: 100px;" class="border_right_bottom">教练投诉学员</td>
							</s:else>
							<td style="width: 100px;" class="border_right_bottom">${reason}</td>
							<s:if test="state==0">
							<td style="width: 100px;" class="border_right_bottom">未解决</td>
							</s:if>
							<s:elseif test="state==1">
								<td style="width: 100px;" class="border_right_bottom">已解决</td>
							</s:elseif>
							<s:else >
							<td style="width: 100px;" class="border_right_bottom">已取消</td>
							</s:else>
							<td style="width: 200px;" class="border_right_bottom"><s:date name="addtime" format="yyyy-MM-dd HH:mm:ss"/></td>
							<td style="width: 200px;" class="border_noright_bottom">
							<s:if test="state==0">
								<div onclick="checkComplaintPass(<s:property value="#list.complaintid"/>,${pageIndex },${index},${change_id})" class="table_edit_button" style="width: 80px;margin-left: 50px;">
									<div class="table_button_edit_icon"></div>
									<div class="table_button_text">&nbsp;解决</div>
								</div>
							</s:if>
							<s:else>
								<div class="table_share_button" style="width: 80px;margin-left: 50px;">
									<div class="table_button_edit_icon"></div>
									<div class="table_button_text">&nbsp;解决</div>
								</div>
							</s:else>
								<div class="table_lookdetail" >
									<a href="getComplaintDetail.do?complaintid=${complaintid}&index=6&change_id=${change_id}"
										style="text-decoration: none; cursor: pointer;font-family: 微软雅黑; "><b>查看详情</b></a>
								</div>
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
					function setPage(container, count, pageindex) {
				var container = container;
					var count = parseInt(count);
					var pageindex = parseInt(pageindex);
					var complaintid=$("#complaintid").val();
					var studentphone=$("#studentphone").val();
					var coachphone=$("#coachphone").val();
					var type=$("#type").val();
					var minsdate=$("#minsdate").val();
					var maxsdate=$("#maxsdate").val();
					var state = $("#hidstate").val();
					var index = $("#index").val()
					var change_id = $("#change_id").val();
					var a = [];
					  //总页数少于10 全部显示,大于10 显示前3 后3 中间3 其余....
					  if (pageindex == 1) {
						  //alert(pageindex);
					    a[a.length] = "<a onclick=\"\" class=\"hide_page_prev unclickprev on\"></a>";
					  } else {
					    a[a.length] = "<a onclick=\"previousPage("+pageindex+",'getComplaintBySearch.do?complaintid="+complaintid+"&studentphone="+studentphone+"&coachphone="+coachphone+"&type="+type+"&minsdate="+minsdate+"&maxsdate="+maxsdate+"&state="+state+"&index="+index+"&change_id="+change_id+"&')\" class=\"page_prev\"></a>";
					  }
					  function setPageList() {
					    if (pageindex == i) {
					      a[a.length] = "<a onclick=\"goPage('getComplaintBySearch.do?complaintid="+complaintid+"&studentphone="+studentphone+"&coachphone="+coachphone+"&type="+type+"&minsdate="+minsdate+"&maxsdate="+maxsdate+"&state="+state+"&index="+index+"&change_id="+change_id+"&',"+i+")\" class=\"on\">" + i + "</a>";
					    } else {
					      a[a.length] = "<a onclick=\"goPage('getComplaintBySearch.do?complaintid="+complaintid+"&studentphone="+studentphone+"&coachphone="+coachphone+"&type="+type+"&minsdate="+minsdate+"&maxsdate="+maxsdate+"&state="+state+"&index="+index+"&change_id="+change_id+"&',"+i+")\">" + i + "</a>";
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
					      a[a.length] = "...<a onclick=\"goPage('getComplaintBySearch.do?complaintid="+complaintid+"&studentphone="+studentphone+"&coachphone="+coachphone+"&type="+type+"&minsdate="+minsdate+"&maxsdate="+maxsdate+"&state="+state+"&index="+index+"&change_id="+change_id+"&',"+count+")\">" + count + "</a>";
					    } else if (pageindex >= count - 3) {
					      a[a.length] = "<a onclick=\"goPage('getComplaintBySearch.do?complaintid="+complaintid+"&studentphone="+studentphone+"&coachphone="+coachphone+"&type="+type+"&minsdate="+minsdate+"&maxsdate="+maxsdate+"&state="+state+"&index="+index+"&change_id="+change_id+"&',1)\">1</a>...";
					      for (var i = count - 4; i <= count; i++) {
					        setPageList();
					      };
					    } else { //当前页在中间部分
					      a[a.length] = "<a onclick=\"goPage('getComplaintBySearch.do?complaintid="+complaintid+"&studentphone="+studentphone+"&coachphone="+coachphone+"&type="+type+"&minsdate="+minsdate+"&maxsdate="+maxsdate+"&state="+state+"&index="+index+"&change_id="+change_id+"&',1)\">1</a>...";
					      for (var i = pageindex - 2; i <= pageindex+2; i++) {
					        setPageList();
					      }
					      a[a.length] = "...<a onclick=\"goPage('getComplaintBySearch.do?complaintid="+complaintid+"&studentphone="+studentphone+"&coachphone="+coachphone+"&type="+type+"&minsdate="+minsdate+"&maxsdate="+maxsdate+"&state="+state+"&index="+index+"&change_id="+change_id+"&',"+count+")\">" + count + "</a>";
					    }
					  }
					  if (pageindex == count) {
						    a[a.length] = "<a onclick=\"\" class=\"hide_page_next unclicknext\"></a> 共"+count+"页  到第  "+
						    "<input type=\"text\" class=\"jump_num\" id=\"topage\"/> 页"+
						    "<a class=\"jump_btn\" onclick=\"gotoPage('getComplaintBySearch.do?complaintid="+complaintid+"&studentphone="+studentphone+"&coachphone="+coachphone+"&type="+type+"&minsdate="+minsdate+"&maxsdate="+maxsdate+"&state="+state+"&index="+index+"&change_id="+change_id+"&',"+$("#pageSize").val()+")\")\">"+
						    "<a id='page_msg'></a>";
						  } else {
						    a[a.length] = 
						    	"<a onclick=\"nextPage("+$("#pageIndex").val()+",'getComplaintBySearch.do?complaintid="+complaintid+"&studentphone="+studentphone+"&coachphone="+coachphone+"&type="+type+"&minsdate="+minsdate+"&maxsdate="+maxsdate+"&state="+state+"&index="+index+"&change_id="+change_id+"&')\" "+
						    	"class=\"page_next\"></a> 共"+count+"页 到第 "+
						    "<input type=\"text\" class=\"jump_num\" id=\"topage\"/> 页"+
						    "<a class=\"jump_btn\" onclick=\"gotoPage('getComplaintBySearch.do?complaintid="+complaintid+"&studentphone="+studentphone+"&coachphone="+coachphone+"&type="+type+"&minsdate="+minsdate+"&maxsdate="+maxsdate+"&state="+state+"&index="+index+"&change_id="+change_id+"&',"+$("#pageSize").val()+")\">"+
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