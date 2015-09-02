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

<link rel="stylesheet" href="css/page.css" type="text/css"
	media="screen" />
<script src="js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/page.js"></script>
<script type="text/javascript" src="student/js/studentapply.js"></script>
<script type="text/javascript" src="student/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
$(function(){
	var index = $("#index").val()
	$("#left_list_"+index).show();
	var j = $("#change_id").val();
	$("#change_"+j+index).addClass('left_list_mask');
	var hidamount = $("#hidamount").val();
	if(hidamount==''){
		$("#amount").val(0);
	}else{
		$("#amount").val(hidamount);
	}
	var hidstate = $("#hidstate").val();
	if(hidstate==''){
		$("#state").val(0);
	}else{
		$("#state").val(hidstate);
	}
})
</script>
<title>学员提现申请</title>
</head>
<input type="hidden" id="hidamount" value="${amount}" />
<!-- <input type="hidden" id="hidstate" value="${state}" />-->
<input type="hidden" id="index" value="${index}" />
<input type="hidden" id="change_id" value="${change_id}"/>
<body onload="getTop()">
	<div id="content">
		<jsp:include page="left.jsp" />
		<div id="content_form">
			<div id="content_form_top">
					<div class="searchbutton" onclick="searchStudentApply();">
						<img src="imgs/common/searchicon.png" width=22px height=22px
							style="margin-top: 9px;">
					</div>
					<div class="serchcontentdiv" style="float: left; margin-left: 50px; width: 156px" >
						<input  type="text" class="searchdiv" style="width: 50px;text-align: center;font-family: 微软雅黑;" value="姓名" readonly="readonly">
						<input id="realname" type="text" class="searchdiv" style="width:100px;font-family: 微软雅黑;" value="${searchname }" maxlength="255"/>
					</div>

					<div class="serchcontentdiv" style="float: left; margin-left: 50px; width: 156px" >
						<input type="text" class="searchdiv" style="width: 50px;text-align: center;font-family: 微软雅黑;" value="电话" readonly="readonly">
						<input id="phone" type="text" onkeyup="value=value.replace(/[^\d]/g,'')" class="searchdiv" style="width:100px;font-family: 微软雅黑;" value="${searchphone}" maxlength="11"/>
					</div>
								
<div class="serchcontentdiv"style="float: left; margin-left: 50px; width: 191px"  >
		<select id="amount" class="searchdiv" style="width: 115px;">
			<option value="0">提现金额大于</option>
			<option value="1">提现金额等于</option>
			<option value="2">提现金额小于</option>
		</select>
		<input id="inputamount" type="text" onkeyup="value=value.replace(/[^\d]/g,'')" class="searchdiv" style="width: 71px;text-align: center;font-family: 微软雅黑;" value="${inputamount }" maxlength="10">
	</div>
					
<div class="serchcontentdiv"style="float: left; margin-left: 50px; width: 385px"  >
		<input type="text" class="searchdiv" style="width: 99px;text-align: center;font-family: 微软雅黑;" value="申请时间区间" readonly="readonly">
		<input id="minsdate" onclick="WdatePicker({startDate:'',dateFmt:'yyyy-MM-dd'})" type="text" class="searchdiv" style="width: 120px;text-align: center;font-family: 微软雅黑;" value="${minsdate}" >
		<input type="text" class="searchdiv" style="width: 30px;text-align: center;font-family: 微软雅黑;" value="到" readonly="readonly">
		<input id="maxsdate" onclick="WdatePicker({startDate:'',dateFmt:'yyyy-MM-dd'})" type="text" class="searchdiv" style="width: 120px;text-align: center;font-family: 微软雅黑;" value="${maxsdate}" >
	</div>
	
 	<div class="serchcontentdiv"style="float: left; margin-left: 50px; width: 130px"  > 
 	<input type="text" class="searchdiv" style="width: 40px;text-align: center;font-family: 微软雅黑;" value="状态" readonly="readonly"> 
 		<select id="state" class="searchdiv" style="width: 85px;"> 
			<option value="0" >申请中</option> 
 			<option value="2" >申请不通过</option> 
 		</select> <input type="hidden" id="hidstate" value="${state}" />
 	</div> 
 	
 	<!-- <input type="hidden" id="state" value="${state}" />-->
			</div>
			<div id="content_form_table">
				<table border="0" cellspacing="0" cellpadding="0"
					style="width: 100%;">
					<tr class="tr_th">
						<th>序号</th>
<!-- 						<th>状态</th> -->
						<th>真实姓名</th>
						<th>电话号码</th>
						<th>提现金额</th>
						<th>申请时间</th>
						<th>支付宝</th>
						<th>操作</th>

					</tr>
					<s:iterator value="stuApplyList" var="cuser" status="st">
						<tr class="tr_td">
							<td style="width: 52px;" class="border_right_bottom"><s:property value="#st.index+1"/></td>
<%-- 							<s:if test="state==0"> --%>
<!-- 							<td style="width: 100px;" class="border_right_bottom">申请中</td> -->
<%-- 							</s:if> --%>
<%-- 							<s:else> --%>
<!-- 							<td style="width: 100px;" class="border_right_bottom">申请通过</td> -->
<%-- 							</s:else> --%>
							<td style="width: 100px;" class="border_right_bottom">${realname}</td>
							<td style="width: 150px;" class="border_right_bottom">${phone}</td>
							<td style="width: 100px;" class="border_right_bottom">${amount}</td>
							<td style="width: 200px;" class="border_right_bottom"><s:date name="addtime" format="yyyy-MM-dd HH:mm:ss"/></td>
							<td style="width: 150px;" class="border_right_bottom">${alipay_account}</td>
							<td style="width: 200px;" class="border_noright_bottom">
							<s:if test="state==0">
								<div class="table_edit_button" style="width: 80px;">
									<div class="table_button_edit_icon"></div>
									<div class="table_button_text"
										onclick="stuCheckPass(${applyid},${index},${pageIndex},${change_id})">审核通过</div>
								</div>
								<div class="table_edit_button" style="width: 90px;background:#f83a22">
									<div class="table_button_edit_icon"></div>
									<div class="table_button_text"
										onclick="stuCheckNoPass(${applyid},${index},${pageIndex},${change_id})">审核不通过</div>
								</div>
								<div class="table_edit_button" style="width: 80px;background:#999999">
									<div class="table_button_edit_icon"></div>
									<div class="table_button_text"
										onclick="checkrevocation(${applyid},${index},${pageIndex},${change_id})">作废</div>
								</div>
								</s:if>
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
					var realname=$("#realname").val();
					var phone=$("#phone").val();
					var amount=$("#hidamount").val();
					var inputamount=$("#inputamount").val();
 					var state = $("#state").val();
					var minsdate=$("#minsdate").val();
					var maxsdate=$("#maxsdate").val();
					var index = $("#index").val();
					var change_id = $("#change_id").val();
					var a = [];
					  //总页数少于10 全部显示,大于10 显示前3 后3 中间3 其余....
					  if (pageindex == 1) {
						  //alert(pageindex);
					    a[a.length] = "<a onclick=\"\" class=\"hide_page_prev unclickprev on\"></a>";
					  } else {
					    a[a.length] = "<a onclick=\"previousPage("+pageindex+",'getStudentApplyBySearch.do?searchname="+realname+"&searchphone="+phone+"&amount="+amount+"&inputamount="+inputamount+"&minsdate="+minsdate+"&maxsdate="+maxsdate+"&state="+state+"&index="+index+"&change_id="+change_id+"&')\" class=\"page_prev\"></a>";
					  }
					  function setPageList() {
					    if (pageindex == i) {
					      a[a.length] = "<a onclick=\"goPage('getStudentApplyBySearch.do?searchname="+realname+"&searchphone="+phone+"&amount="+amount+"&inputamount="+inputamount+"&minsdate="+minsdate+"&maxsdate="+maxsdate+"&state="+state+"&index="+index+"&change_id="+change_id+"&',"+i+")\" class=\"on\">" + i + "</a>";
					    } else {
					      a[a.length] = "<a onclick=\"goPage('getStudentApplyBySearch.do?searchname="+realname+"&searchphone="+phone+"&amount="+amount+"&inputamount="+inputamount+"&minsdate="+minsdate+"&maxsdate="+maxsdate+"&state="+state+"&index="+index+"&change_id="+change_id+"&',"+i+")\">" + i + "</a>";
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
					      a[a.length] = "...<a onclick=\"goPage('getStudentApplyBySearch.do?searchname="+realname+"&searchphone="+phone+"&amount="+amount+"&inputamount="+inputamount+"&minsdate="+minsdate+"&maxsdate="+maxsdate+"&state="+state+"&index="+index+"&change_id="+change_id+"&',"+count+")\">" + count + "</a>";
					    } else if (pageindex >= count - 3) {
					      a[a.length] = "<a onclick=\"goPage('getStudentApplyBySearch.do?searchname="+realname+"&searchphone="+phone+"&amount="+amount+"&inputamount="+inputamount+"&minsdate="+minsdate+"&maxsdate="+maxsdate+"&state="+state+"&index="+index+"&change_id="+change_id+"&',1)\">1</a>...";
					      for (var i = count - 4; i <= count; i++) {
					        setPageList();
					      };
					    } else { //当前页在中间部分
					      a[a.length] = "<a onclick=\"goPage('getStudentApplyBySearch.do?searchname="+realname+"&searchphone="+phone+"&amount="+amount+"&inputamount="+inputamount+"&minsdate="+minsdate+"&maxsdate="+maxsdate+"&state="+state+"&index="+index+"&change_id="+change_id+"&',1)\">1</a>...";
					      for (var i = pageindex - 2; i <= pageindex+2; i++) {
					        setPageList();
					      }
					      a[a.length] = "...<a onclick=\"goPage('getStudentApplyBySearch.do?searchname="+realname+"&searchphone="+phone+"&amount="+amount+"&inputamount="+inputamount+"&minsdate="+minsdate+"&maxsdate="+maxsdate+"&state="+state+"&index="+index+"&change_id="+change_id+"&',"+count+")\">" + count + "</a>";
					    }
					  }
					  if (pageindex == count) {
						    a[a.length] = "<a onclick=\"\" class=\"hide_page_next unclicknext\"></a> 共"+count+"页  到第  "+
						    "<input type=\"text\" class=\"jump_num\" id=\"topage\"/> 页"+
						    "<a class=\"jump_btn\" onclick=\"gotoPage('getStudentApplyBySearch.do?searchname="+realname+"&searchphone="+phone+"&amount="+amount+"&inputamount="+inputamount+"&minsdate="+minsdate+"&maxsdate="+maxsdate+"&state="+state+"&index="+index+"&change_id="+change_id+"&',"+$("#pageSize").val()+")\")\">"+
						    "<a id='page_msg'></a>";
						  } else {
						    a[a.length] = 
						    	"<a onclick=\"nextPage("+$("#pageIndex").val()+",'getStudentApplyBySearch.do?searchname="+realname+"&searchphone="+phone+"&amount="+amount+"&inputamount="+inputamount+"&minsdate="+minsdate+"&maxsdate="+maxsdate+"&state="+state+"&index="+index+"&change_id="+change_id+"&')\" "+
						    	"class=\"page_next\"></a> 共"+count+"页 到第 "+
						    "<input type=\"text\" class=\"jump_num\" id=\"topage\"/> 页"+
						    "<a class=\"jump_btn\" onclick=\"gotoPage('getStudentApplyBySearch.do?searchname="+realname+"&searchphone="+phone+"&amount="+amount+"&inputamount="+inputamount+"&minsdate="+minsdate+"&maxsdate="+maxsdate+"&state="+state+"&index="+index+"&change_id="+change_id+"&',"+$("#pageSize").val()+")\">"+
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