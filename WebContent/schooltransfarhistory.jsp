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
<link rel="stylesheet" href="css/page.css" type="text/css"media="screen" />
<script src="js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/page.js"></script>
<script type="text/javascript" src="coach/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
$(function(){
	var index = $("#index").val();
	$("#left_list_"+index).show();
	var j = $("#change_id").val();
	$("#change_"+j+index).addClass('left_list_mask');
	
// 	var hidamount = $("#hidamount").val();
// 	if(hidamount==''){
// 		$("#amount").val(0);
// 	}else{
// 		$("#amount").val(hidamount);
// 	}
// 	var hidstate = $("#hidstate").val();
// 	if(hidstate==''){
// 		$("#state").val(0);
// 	}else{
// 		$("#state").val(hidstate);
// // 	}
	var hiddenschoolid = $("#hiddenschoolid").val();
	$("#driveschoolid").val(hiddenschoolid);
	
	var type = $("#hiddentype").val();
	$("#searchtype").val(type);
})

function searchfromsubmit(){
	var starttime = $("#starttime").val();
	var endtime = $("#endtime").val();
	if(starttime!=''&&endtime!=''){
		if(starttime>endtime){
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
	width: 500px;
	height: 300px;
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
<title>驾校余额管理</title>
</head>
<input type="hidden" id="hidamount" value="${amount}" />
<input type="hidden" id="hidstate" value="${state}" />
<input type="hidden" id="index" value="2"/>
<input type="hidden" id="change_id" value="${change_id}"/>
<body onload="getTop()">
	<div id="content">
		<jsp:include page="left.jsp" />
		<div id="content_form">
			<form id="searchform" action="getschoolRechargeList.do">
			<div id="content_form_top">
		
			<div class="searchbutton" onclick="searchfromsubmit();">
						<img src="imgs/common/searchicon.png" width=22px height=22px
							style="margin-top: 9px;">
			</div>					
	<s:if test="changetype==1">
		<input type="hidden" id="driveschoolid" value="${schoolid}" />
	</s:if>
	<s:else>
	<div class="serchcontentdiv"style="float: left; margin-left: 50px; width: 130px"  >
		<input type="text" class="searchdiv" style="width: 40px;text-align: center;font-family: 微软雅黑;" value="驾校" readonly="readonly">
		<select id="driveschoolid" name="schoolid" class="searchdiv" style="width: 85px;">
		<option value="0">全部</option> 
		<s:iterator value="driveSchoollist" id="driveSchoollist">
		<option value="${schoolid }">${name }</option>
		</s:iterator>
		</select>
		<input type="hidden" id="hiddenschoolid" value="${schoolid }">
	</div>
	</s:else>
	
		<div class="serchcontentdiv"style="float: left; margin-left: 50px; width: 155px"  >
		<input type="text" class="searchdiv" style="width: 65px;text-align: center;font-family: 微软雅黑;" value="转账类型" readonly="readonly">
		<select id="searchtype" name="type" class="searchdiv" style="width: 85px;">
		<option value="">全部</option> 
		<option value="1">转入</option>
		<option value="2">转出</option>
		</select>
		<input type="hidden" id="hiddentype" value="${type }">
	</div>
								
			<div class="serchcontentdiv"style="float: left; margin-left: 50px; width: 405px"  >
					<input type="text" class="searchdiv" style="width: 119px;text-align: center;font-family: 微软雅黑;" value="转账时间" readonly="readonly">
					<input id="starttime" name="starttime" onclick="WdatePicker({startDate:'',dateFmt:'yyyy-MM-dd'})" type="text" class="searchdiv" style="width: 120px;text-align: center;font-family: 微软雅黑;" value="${starttime}">
					<input type="text" class="searchdiv" style="width: 30px;text-align: center;font-family: 微软雅黑;" value="到" readonly="readonly">
					<input id="endtime" name="endtime" onclick="WdatePicker({startDate:'',dateFmt:'yyyy-MM-dd'})" type="text" class="searchdiv" style="width: 120px;text-align: center;font-family: 微软雅黑;" value="${endtime}">
				</div>
			</div>
			</form>
			<div id="content_form_table">
				<table border="0" cellspacing="0" cellpadding="0"
					style="width: 100%;">
					<tr class="tr_th">
						<th>序号</th>
<!-- 						<th>状态</th> -->
						<th>驾校名称</th>
						<th>类型</th>
						<th>金额</th>
<!-- 						<th>当前星级</th> -->
						<th>时间</th>
					</tr>
					<s:iterator value="schoolbalancelist" var="schoolbalancelist" status="st">
						<tr class="tr_td">
							<td style="width: 52px;" class="border_right_bottom"><s:property value="#st.index+1"/></td>
<%-- 							<s:if test="state==0"> --%>
<!-- 							<td style="width: 100px;" class="border_right_bottom">申请中</td> -->
<%-- 							</s:if> --%>
<%-- 							<s:else> --%>
<!-- 							<td style="width: 100px;" class="border_right_bottom">申请通过</td> -->
<%-- 							</s:else> --%>
							<td style="width: 150px;" class="border_right_bottom">${driveschool.name}</td>
							<td style="width: 150px;" class="border_right_bottom">
							<s:if test="type==1">
							转入
							</s:if>
							<s:elseif test="type==2">
							转出
							</s:elseif>
							</td>
							<td style="width: 150px;" class="border_right_bottom">${amount}</td>
							<td style="width: 150px;" class="border_noright_bottom">
								<s:date name="addtime" format="yyyy-MM-dd HH:mm:ss"/></td>  
<%-- 								<s:else> --%>
<!-- 								<div class="table_edit_button" style="width: 80px;margin-left: 100px;"> -->
<!-- 									<div class="table_button_edit_icon"></div> -->
<!-- 									<div class="table_button_text">审核通过</div> -->
<!-- 								</div> -->
<%-- 								</s:else> --%>
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
					var schoolid = $("#hiddenschoolid").val();
					var type = $("#hiddentype").val();
					var starttime = $("#starttime").val();
					var endtime = $("#endtime").val();
					var a = [];
					  //总页数少于10 全部显示,大于10 显示前3 后3 中间3 其余....
					  if (pageindex == 1) {
						  //alert(pageindex);
					    a[a.length] = "<a onclick=\"\" class=\"hide_page_prev unclickprev on\"></a>";
					  } else {
					    a[a.length] = "<a onclick=\"previousPage("+pageindex+",'getschoolRechargeList.do?schoolid"+schoolid+"&type="+type+"&starttime="+starttime+"&endtime="+endtime+"&')\" class=\"page_prev\"></a>";
					  }
					  function setPageList() {
					    if (pageindex == i) {
					      a[a.length] = "<a onclick=\"goPage('getschoolRechargeList.do?schoolid"+schoolid+"&type="+type+"&starttime="+starttime+"&endtime="+endtime+"&',"+i+")\" class=\"on\">" + i + "</a>";
					    } else {
					      a[a.length] = "<a onclick=\"goPage('getschoolRechargeList.do?schoolid"+schoolid+"&type="+type+"&starttime="+starttime+"&endtime="+endtime+"&',"+i+")\">" + i + "</a>";
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
					      a[a.length] = "...<a onclick=\"goPage('getschoolRechargeList.do?schoolid"+schoolid+"&type="+type+"&starttime="+starttime+"&endtime="+endtime+"&',"+count+")\">" + count + "</a>";
					    } else if (pageindex >= count - 3) {
					      a[a.length] = "<a onclick=\"goPage('getschoolRechargeList.do?schoolid"+schoolid+"&type="+type+"&starttime="+starttime+"&endtime="+endtime+"&',1)\">1</a>...";
					      for (var i = count - 4; i <= count; i++) {
					        setPageList();
					      };
					    } else { //当前页在中间部分
					      a[a.length] = "<a onclick=\"goPage('getschoolRechargeList.do?schoolid"+schoolid+"&type="+type+"&starttime="+starttime+"&endtime="+endtime+"&',1)\">1</a>...";
					      for (var i = pageindex - 2; i <= pageindex+2; i++) {
					        setPageList();
					      }
					      a[a.length] = "...<a onclick=\"goPage('getschoolRechargeList.do?schoolid"+schoolid+"&type="+type+"&starttime="+starttime+"&endtime="+endtime+"&',"+count+")\">" + count + "</a>";
					    }
					  }
					  if (pageindex == count) {
						    a[a.length] = "<a onclick=\"\" class=\"hide_page_next unclicknext\"></a> 共"+count+"页  到第  "+
						    "<input type=\"text\" class=\"jump_num\" id=\"topage\"/> 页"+
						    "<a class=\"jump_btn\" onclick=\"gotoPage('getschoolRechargeList.do?schoolid"+schoolid+"&type="+type+"&starttime="+starttime+"&endtime="+endtime+"&',"+$("#pageSize").val()+")\")\">"+
						    "<a id='page_msg'></a>";
						  } else {
						    a[a.length] = 
						    	"<a onclick=\"nextPage("+$("#pageIndex").val()+",'getschoolRechargeList.do?schoolid"+schoolid+"&type="+type+"&starttime="+starttime+"&endtime="+endtime+"&')\" "+
						    	"class=\"page_next\"></a> 共"+count+"页 到第 "+
						    "<input type=\"text\" class=\"jump_num\" id=\"topage\"/> 页"+
						    "<a class=\"jump_btn\" onclick=\"gotoPage('getschoolRechargeList.do?schoolid"+schoolid+"&type="+type+"&starttime="+starttime+"&endtime="+endtime+"&',"+$("#pageSize").val()+")\">"+
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

	<div id="mask" class="mask"></div>
	<div id="mask_sec" style="position: fixed; width: 100%; height: 300px;z-index: 300;">
		<div id="mask_last" class="mask_last">
		<div style="position: fixed; width: 300px; height: 200px;background: #4cc2ff;margin-left: 100px;margin-top: 50px;">
		<input id="transferamount" type="text"  onkeydown="myKeyDown(this.value, event)" style="width: 218px;height: 40px;margin: auto;margin-left: 41px;margin-top: 45px;font-size: 18px;text-align: center;"  placeholder="请输入转账的金额"/>
		<input type="button" style="width: 100px;height: 40px;margin: auto;margin-left: 50px;margin-top: 40px;font-size: 18px" value="确定" onclick="schooltransfar()">
		<input type="button" style="width: 100px;height: 40px;margin: auto;margin-left: 180x;margin-top: -40px;font-size: 18px" value="取消" onclick="unshowaddsubject()">
		<input type="hidden" value="" id="schoolmoney">
		<input type="hidden" value="" id="schoolid">
		</div>
		</div>
	</div>
</html>