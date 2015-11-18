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
<script type="text/javascript" src="js/order.js"></script>
<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
$(function(){
	var index = $("#index").val()
	$("#left_list_"+index).show();
	var j = $("#change_id").val();
	$("#change_"+j+index).addClass('left_list_mask');
	var hidstate = $("#hidstate").val();
	var hidordertotal = $("#hidordertotal").val();
	var hidishavacomplaint = $("#hidishavacomplaint").val()
	if(hidstate==''){
		$("#state").val(0)
	}else{
		$("#state").val(hidstate)
	}
	if(hidordertotal==''){
		$("#ordertotal").val(0)
	}else{
		$("#ordertotal").val(hidordertotal)
	}
	if(hidishavacomplaint==''){
		$("#ishavacomplaint").val(0);
	}else{
		$("#ishavacomplaint").val(hidishavacomplaint);
	}
})

function dataExport(){
		window.location.href="jumptodowanload.do";
}
</script>

<title>订单列表</title>
</head>
<input type="hidden" id="index" value="${index}" />
<input type="hidden" id="hidstate" value="${state}" />
<input type="hidden" id="hidordertotal" value="${ordertotal}" />
<input type="hidden" id="hidishavacomplaint" value="${ishavacomplaint}" />
<input type="hidden" id="change_id" value="${change_id}"/>
<input type="hidden" id="overtimeRS" value="${overtimeRangeS}"/>
<input type="hidden" id="overtimeRE" value="${overtimeRangeE}"/>
<body onload="getTop()">
	<div id="content">
		<jsp:include page="left.jsp" />
		<div id="content_form">
			<div id="content_form_top" style="height:auto;">
					<div class="searchbutton" onclick="searchOrder()">
						<img src="imgs/common/searchicon.png" width=22px height=22px
							style="margin-top: 9px;">
					</div>
					<div class="searchbutton" style="width:80px;">
						<div class="table_button_edit_icon"></div>
						<div class="table_button_text" style="font-size: 12px;line-height: 38px;" onclick="dataExport();">数据导出</div>
					</div>
					<div class="serchcontentdiv" style="float: left; margin-left: 50px; width: 155px" >
						<input  type="text" class="searchdiv" style="width: 59px;text-align: center;font-family: 微软雅黑;" value="教练电话" readonly="readonly">
						<input id="coachphone" type="text" class="searchdiv" style="width:90px;font-family: 微软雅黑;" value="${coachphone}" onkeyup="value=value.replace(/[^\d]/g,'')" maxlength="11"/>
					</div>

					<div class="serchcontentdiv" style="float: left; margin-left: 50px; width: 155px" >
						<input type="text" class="searchdiv" style="width: 59px;text-align: center;font-family: 微软雅黑;" value="学员电话" readonly="readonly">
						<input id="studentphone" type="text" class="searchdiv" style="width:90px;font-family: 微软雅黑;" value="${studentphone}" onkeyup="value=value.replace(/[^\d]/g,'')" maxlength="11"/>
					</div>
<div class="serchcontentdiv"style="float: left; margin-left: 50px; width: 400px"  >
		<input type="text" class="searchdiv" style="width: 89px;text-align: center;font-family: 微软雅黑;" value="开始时间区间" readonly="readonly">
		<input id="startminsdate" onclick="WdatePicker({startDate:'',dateFmt:'yyyy-MM-dd'})" type="text" class="searchdiv" style="width: 135px;text-align: center;font-family: 微软雅黑;" value="${startminsdate}" >
		<input type="text" class="searchdiv" style="width: 25px;text-align: center;font-family: 微软雅黑;" value="到" readonly="readonly">
		<input id="startmaxsdate" onclick="WdatePicker({startDate:'',dateFmt:'yyyy-MM-dd'})" type="text" class="searchdiv" style="width: 135px;text-align: center;font-family: 微软雅黑;" value="${startmaxsdate}">
</div>
<div class="serchcontentdiv"style="float: left; margin-left: 50px; width: 400px"  >
		<input type="text" class="searchdiv" style="width: 89px;text-align: center;font-family: 微软雅黑;" value="结束时间区间" readonly="readonly">
		<input id="endminsdate" onclick="WdatePicker({startDate:'',dateFmt:'yyyy-MM-dd'})" type="text" class="searchdiv" style="width: 135px;text-align: center;font-family: 微软雅黑;" value="${endminsdate}" >
		<input type="text" class="searchdiv" style="width: 25px;text-align: center;font-family: 微软雅黑;" value="到" readonly="readonly">
		<input id="endmaxsdate" onclick="WdatePicker({startDate:'',dateFmt:'yyyy-MM-dd'})" type="text" class="searchdiv" style="width: 135px;text-align: center;font-family: 微软雅黑;" value="${endmaxsdate}">
</div>

<div class="serchcontentdiv"style="float: left; margin-left: 50px; width: 400px"  >
		<input type="text" class="searchdiv" style="width: 89px;text-align: center;font-family: 微软雅黑;" value="预定时间区间" readonly="readonly">
		<input id="createminsdate" onclick="WdatePicker({startDate:'',dateFmt:'yyyy-MM-dd'})" type="text" class="searchdiv" style="width: 135px;text-align: center;font-family: 微软雅黑;" value="${createminsdate}" >
		<input type="text" class="searchdiv" style="width: 25px;text-align: center;font-family: 微软雅黑;" value="到" readonly="readonly">
		<input id="createmaxsdate" onclick="WdatePicker({startDate:'',dateFmt:'yyyy-MM-dd'})" type="text" class="searchdiv" style="width: 135px;text-align: center;font-family: 微软雅黑;" value="${createmaxsdate}">
</div>
					
	<div class="serchcontentdiv"style="float: left; margin-left: 50px; width: 115px"  >
	<input type="text" class="searchdiv" style="width: 40px;text-align: center;font-family: 微软雅黑;" value="状态" readonly="readonly">
		<select id="state" class="searchdiv" style="width: 70px;">
			<s:if test="state == 0">
				<option value="0" selected="selected">不限</option>
				<option value="1" >未结算</option>
				<option value="2" >已结算</option>
				<option value="3" >已取消</option>
			</s:if>
			<s:elseif test="state == 1">
				<option value="0" >不限</option>
				<option value="1" selected="selected">未结算</option>
				<option value="2" >已结算</option>
				<option value="3" >已取消</option>
			</s:elseif>
			<s:elseif test="state == 2">
				<option value="0" >不限</option>
				<option value="1" >未结算</option>
				<option value="2" selected="selected">已结算</option>
				<option value="3" >已取消</option>
			</s:elseif>
			<s:elseif test="state == 3">
				<option value="0" >不限</option>
				<option value="1" >未结算</option>
				<option value="2" >已结算</option>
				<option value="3" selected="selected">已取消</option>
			</s:elseif>
			<s:else>
				<option value="0" selected="selected">不限</option>
				<option value="1" >未结算</option>
				<option value="2" >已结算</option>
				<option value="3" >已取消</option>
			</s:else>
		</select>
	</div>
					
<div class="serchcontentdiv"style="float: left; margin-left: 50px; width: 156px"  >
		<select id="ordertotal" class="searchdiv" style="width: 80px;">
			<s:if test="ordertotal == 0">
				<option value="0" selected="selected">总价大于</option>
				<option value="1">总价等于</option>
				<option value="2">总价小于</option>
			</s:if>
			<s:elseif test="ordertotal == 1">
				<option value="0" >总价大于</option>
				<option value="1" selected="selected">总价等于</option>
				<option value="2">总价小于</option>
			</s:elseif>
			<s:elseif test="ordertotal == 2">
				<option value="0" >总价大于</option>
				<option value="1">总价等于</option>
				<option value="2" selected="selected">总价小于</option>
			</s:elseif>
			<s:else>
				<option value="0" selected="selected">总价大于</option>
				<option value="1">总价等于</option>
				<option value="2">总价小于</option>
			</s:else>
		</select>
		<input id="inputordertotal" type="text" class="searchdiv" style="width: 71px;text-align: center;font-family: 微软雅黑;" onkeyup="value=value.replace(/[^\d]/g,'')" value="${inputordertotal}">
	</div>
		<div class="serchcontentdiv"style="float: left; margin-left: 50px; width: 145px"  >
	<input type="text" class="searchdiv" style="width: 80px;text-align: center;font-family: 微软雅黑;" value="是否有投诉" readonly="readonly">
		<select id="ishavacomplaint" class="searchdiv" style="width: 60px;">
			<s:if test="ishavacomplaint == 0">
				<option value="0" selected="selected">不限</option>
				<option value="1" >是</option>
				<option value="2" >否</option>
			</s:if>
			<s:elseif test="ishavacomplaint == 1">
				<option value="0" >不限</option>
				<option value="1" selected="selected">是</option>
				<option value="2" >否</option>
			</s:elseif>
			<s:elseif test="ishavacomplaint == 2">
				<option value="0" >不限</option>
				<option value="1" >是</option>
				<option value="2" selected="selected">否</option>
			</s:elseif>
			<s:else>
				<option value="0" selected="selected">不限</option>
				<option value="1" >是</option>
				<option value="2" >否</option>
			</s:else>
		</select>
	</div>
	<div class="serchcontentdiv"style="float: left; margin-left: 50px; width: 158px"  >
		<input type="text" class="searchdiv" style="width: 80px;text-align: center;font-family: 微软雅黑;" value="支付方式" readonly="readonly">
		<select id="t_paytype"  class="searchdiv">
		   <s:if test="t_paytype == 1">
				<option value="0">不限</option>
			    <option value="1"  selected="selected">余额</option>
			    <option value="2">小巴券</option>
			    <option value="3">小巴币</option>
			</s:if>
			<s:elseif test="t_paytype == 2">
				<option value="0" >不限</option>
			    <option value="1">余额</option>
			    <option value="2" selected="selected">小巴券</option>
			    <option value="3">小巴币</option>
			</s:elseif>
			<s:elseif test="t_paytype == 3">
				<option value="0" >不限</option>
			    <option value="1">余额</option>
			    <option value="2">小巴券</option>
			    <option value="3"selected="selected">小巴币</option>
			</s:elseif>
			<s:else>
				<option value="0"  selected="selected">不限</option>
			    <option value="1">余额</option>
			    <option value="2">小巴券</option>
			    <option value="3">小巴币</option>
			</s:else>
		</select>
	</div>
	<div class="serchcontentdiv"style="float: left; margin-left: 50px; width: 158px"  >
		<input type="text" class="searchdiv" style="width: 80px;text-align: center;font-family: 微软雅黑;" value="订单类型" readonly="readonly">
		<select id="t_ordertype"  class="searchdiv">
		      <s:if test="t_ordertype == 1">
				 <option value="0">不限</option>
				    <option value="1" selected="selected">科目二</option>
				    <option value="2">科目三</option>
				    <option value="3">考场练习</option>
				    <option value="4">陪驾</option>
			  </s:if>
			   <s:elseif test="t_ordertype == 2">
				     <option value="0">不限</option>
				    <option value="1">科目二</option>
				    <option value="2" selected="selected">科目三</option>
				    <option value="3">考场练习</option>
				    <option value="4">陪驾</option>
			   </s:elseif>
			     <s:elseif test="t_ordertype == 3">
				    <option value="0">不限</option>
				    <option value="1">科目二</option>
				    <option value="2">科目三</option>
				    <option value="3" selected="selected">考场练习</option>
				    <option value="4">陪驾</option>
			   </s:elseif>
			     <s:elseif test="t_ordertype == 4">
				    <option value="0">不限</option>
				    <option value="1">科目二</option>
				    <option value="2">科目三</option>
				    <option value="3">考场练习</option>
				    <option value="4" selected="selected">陪驾</option>
			   </s:elseif>
			     <s:else>
				    <option value="0" selected="selected">不限</option>
				    <option value="1">科目二</option>
				    <option value="2">科目三</option>
				    <option value="3">考场练习</option>
				    <option value="4">陪驾</option>
			   </s:else>
		</select>
	</div>
<div class="serchcontentdiv"style="float: left; margin-left: 50px; width: 400px">
		<input type="text" class="searchdiv" style="width: 89px;text-align: center;font-family: 微软雅黑;" value="订单结算时间区间" readonly="readonly">
		<input id="overtimeRangeS" onclick="WdatePicker({startDate:'',dateFmt:'yyyy-MM-dd'})" type="text" class="searchdiv" style="width: 135px;text-align: center;font-family: 微软雅黑;" value="${overtimeRangeS}" >
		<input type="text" class="searchdiv" style="width: 25px;text-align: center;font-family: 微软雅黑;" value="到" readonly="readonly">
		<input id="overtimeRangeE" onclick="WdatePicker({startDate:'',dateFmt:'yyyy-MM-dd'})" type="text" class="searchdiv" style="width: 135px;text-align: center;font-family: 微软雅黑;" value="${overtimeRangeE}">
</div>
			</div>
			<div id="content_form_table">
				<table border="0" cellspacing="0" cellpadding="0" style="width: 100%;">
					<tr class="tr_th">
						<th>ID</th>
						<th>教练姓名</th>
						<th>教练电话</th>
						<th>学员姓名</th>
						<th>学员电话</th>
						<th>开始时间</th>
						<th>结束时间</th>
						<th>结算时间</th>
						<th>总价</th>
						<th>支付方式</th>
						<th>老订单支付方式</th>
						<th>订单类型</th>
						<th>订单状态</th>
						<th>投诉条数</th>
						<th>预定时间</th>
						<th>操作</th>
					</tr>
					<s:iterator value="orderlist" var="cuser">
						<tr class="tr_td">
							<td style="width: 52px;" class="border_right_bottom">${orderid}</td>
							<td style="width: 80px;" class="border_right_bottom">${cuserinfo.realname}</td>
							<td style="width: 150px;" class="border_right_bottom">${cuserinfo.phone}</td>
							<td style="width: 80px;" class="border_right_bottom">${studentinfo.realname}</td>
							<td style="width: 80px;" class="border_right_bottom">${studentinfo.phone}</td>
							<td style="width: 100px;" class="border_right_bottom"><s:date name="start_time" format="yyyy-MM-dd HH:mm:ss"/></td>
							<td style="width: 100px;" class="border_right_bottom"><s:date name="end_time" format="yyyy-MM-dd HH:mm:ss"/></td>
							<td style="width: 100px;" class="border_right_bottom"><s:date name="over_time" format="yyyy-MM-dd HH:mm:ss"/></td>
							<td style="width: 100px;" class="border_right_bottom">${total}</td>
							<s:if test="paytype==1">
								<td style="width: 60px;" class="border_right_bottom">余额</td>
							</s:if>
							<s:elseif test="paytype==2">
							<td style="width: 60px;" class="border_right_bottom">小巴券</td>
							</s:elseif>
							<s:elseif test="paytype==3">
								<td style="width: 60px;" class="border_right_bottom">小巴币</td>
							</s:elseif>
							<s:else>
							   <td style="width: 60px;" class="border_right_bottom">未知方式</td>
							</s:else>
							<s:if test="paytype==0">
								<s:if test="couponrecordid.length()>0 && delmoney>0 ">
									<td style="width: 60px;" class="border_right_bottom">小巴券</td>
								</s:if>
								<s:else>
									<td style="width: 60px;" class="border_right_bottom">余额</td>
								</s:else>
							</s:if>
							<s:else>
									<td style="width: 60px;" class="border_right_bottom">未知</td>
							</s:else>
							<td style="width: 80px;" class="border_right_bottom">${subjectname}</td>
							<s:if test="coachstate==2 && over_time!=null && studentstate==3">
								<td style="width: 40px;" class="border_right_bottom">已结算</td>
							</s:if>
							<s:elseif test="studentstate==4 && coachstate==4">
								<td style="width: 80px;" class="border_right_bottom">学员已取消</td>
							</s:elseif>
							<s:elseif test="can_cancel>0">
								<td style="width: 80px;" class="border_right_bottom">未开始</td>
							</s:elseif>
							<s:elseif test="(coachstate!=2 && coachstate!=4)||over_time==null||(studentstate!=3 && studentstate!=4)">
								<td style="width: 60px;" class="border_right_bottom">未结算</td>
							</s:elseif>
								
							
							
							<td style="width: 40px;" class="border_right_bottom">${complaintnum}</td>
							<td style="width: 80px;" class="border_right_bottom"><s:date name="creat_time" format="yyyy-MM-dd HH:mm:ss"/></td>
							<td style="width: 300px;" class="border_noright_bottom">
								<div class="table_lookdetail" >
									<a href="getOrderDetail.do?orderid=${orderid}&index=5&change_id=${orderid}"
										style="text-decoration: none; cursor: pointer;font-family: 微软雅黑; "><b>查看详情</b></a></div>
													
								<s:if test="couponrecordid.length()>2 && delmoney>0&&coachstate==0&&studentstate==0&&can_cancel>0">
								<div class="table_lookdetail" style="width:150px">
								
								<a href="cancelOrder.do?orderid=${orderid}&studentid=${studentinfo.studentid}&index=5&change_id=${orderid}"
										style="text-decoration: none; cursor: pointer;font-family: 微软雅黑; "><b>取消<%-- ${couponrecordid}--${delmoney}--${coachstate}--${studentstate} } --%></b></a>
							</div>
							</s:if>		
<%-- 							<s:else>
								<div class="table_lookdetail" style="width:150px">
								<a href="getOrderDetail.do?orderid=${orderid}&index=5&change_id=${orderid}"
										style="text-decoration: none; cursor: pointer;font-family: 微软雅黑; "><b>${couponrecordid}--${delmoney}--${coachstate}--${studentstate} }</b></a>
							</div>
							</s:else> --%>						
							</td>

						</tr>
					</s:iterator>
					<tr>
						<td colspan="11" style="height: 86px;">
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
					var coachphone=$("#coachphone").val();
					var studentphone=$("#studentphone").val();
					var startminsdate=$("#startminsdate").val();
					var startmaxsdate=$("#startmaxsdate").val();
					var endminsdate=$("#endminsdate").val();
					var endmaxsdate=$("#endmaxsdate").val();
					var createminsdate=$("#createminsdate").val();
					var createmaxsdate=$("#createmaxsdate").val();
					var state=$("#hidstate").val();
					var ordertotal = $("#ordertotal").val();
					var inputordertotal = $("#inputordertotal").val();
					var ishavacomplaint = $("#ishavacomplaint").val();
					var t_paytype=$("#t_paytype").val();
					var index = $("#index").val();
					var change_id = $("#change_id").val();
					var t_ordertype=$("#t_ordertype").val();
					var overtimeRangeS=$("#overtimeRS").val();
					var overtimeRangeE=$("#overtimeRE").val();
					var a = [];
					  //总页数少于10 全部显示,大于10 显示前3 后3 中间3 其余....
					  if (pageindex == 1) {
						  //alert(pageindex);
					    a[a.length] = "<a onclick=\"\" class=\"hide_page_prev unclickprev on\"></a>";
					  } else {
					    a[a.length] = "<a onclick=\"previousPage("+pageindex+",'getOrderList.do?coachphone="+coachphone+"&studentphone="+studentphone+"&startminsdate="+startminsdate+"&startmaxsdate="+startmaxsdate+
						"&endminsdate="+endminsdate+"&endmaxsdate="+endmaxsdate+"&createminsdate="+createminsdate+"&createmaxsdate="+createmaxsdate+"&state="+state+"&ordertotal="+ordertotal+"&inputordertotal="+inputordertotal+"&ishavacomplaint="+ishavacomplaint+"&t_paytype="+t_paytype+"&t_ordertype="+t_ordertype+"&index="+index+"&change_id="+change_id+"&overtimeRangeS="+overtimeRangeS+"&overtimeRangeE="+overtimeRangeE+"&')\" class=\"page_prev\"></a>";
					  }
					  function setPageList() {
					    if (pageindex == i) {
					      a[a.length] = "<a onclick=\"goPage('getOrderList.do?coachphone="+coachphone+"&studentphone="+studentphone+"&startminsdate="+startminsdate+"&startmaxsdate="+startmaxsdate+
					  	"&endminsdate="+endminsdate+"&endmaxsdate="+endmaxsdate+"&createminsdate="+createminsdate+"&createmaxsdate="+createmaxsdate+"&state="+state+"&ordertotal="+ordertotal+"&inputordertotal="+inputordertotal+"&ishavacomplaint="+ishavacomplaint+"&t_paytype="+t_paytype+"&t_ordertype="+t_ordertype+"&index="+index+"&change_id="+change_id+"&overtimeRangeS="+overtimeRangeS+"&overtimeRangeE="+overtimeRangeE+"&',"+i+")\" class=\"on\">" + i + "</a>";
					    } else {
					      a[a.length] = "<a onclick=\"goPage('getOrderList.do?coachphone="+coachphone+"&studentphone="+studentphone+"&startminsdate="+startminsdate+"&startmaxsdate="+startmaxsdate+
					  	"&endminsdate="+endminsdate+"&endmaxsdate="+endmaxsdate+"&createminsdate="+createminsdate+"&createmaxsdate="+createmaxsdate+"&state="+state+"&ordertotal="+ordertotal+"&inputordertotal="+inputordertotal+"&ishavacomplaint="+ishavacomplaint+"&t_paytype="+t_paytype+"&t_ordertype="+t_ordertype+"&index="+index+"&change_id="+change_id+"&overtimeRangeS="+overtimeRangeS+"&overtimeRangeE="+overtimeRangeE+"&',"+i+")\">" + i + "</a>";
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
					      a[a.length] = "...<a onclick=\"goPage('getOrderList.do?coachphone="+coachphone+"&studentphone="+studentphone+"&startminsdate="+startminsdate+"&startmaxsdate="+startmaxsdate+
					  	"&endminsdate="+endminsdate+"&endmaxsdate="+endmaxsdate+"&createminsdate="+createminsdate+"&createmaxsdate="+createmaxsdate+"&state="+state+"&ordertotal="+ordertotal+"&inputordertotal="+inputordertotal+"&ishavacomplaint="+ishavacomplaint+"&t_ordertype="+t_ordertype+"&index="+index+"&change_id="+change_id+"&overtimeRangeS="+overtimeRangeS+"&overtimeRangeE="+overtimeRangeE+"&',"+count+")\">" + count + "</a>";
					    } else if (pageindex >= count - 3) {
					      a[a.length] = "<a onclick=\"goPage('getOrderList.do?coachphone="+coachphone+"&studentphone="+studentphone+"&startminsdate="+startminsdate+"&startmaxsdate="+startmaxsdate+
					  	"&endminsdate="+endminsdate+"&endmaxsdate="+endmaxsdate+"&createminsdate="+createminsdate+"&createmaxsdate="+createmaxsdate+"&state="+state+"&ordertotal="+ordertotal+"&inputordertotal="+inputordertotal+"&ishavacomplaint="+ishavacomplaint+"&t_paytype="+t_paytype+"&t_ordertype="+t_ordertype+"&index="+index+"&change_id="+change_id+"&overtimeRangeS="+overtimeRangeS+"&overtimeRangeE="+overtimeRangeE+"&',1)\">1</a>...";
					      for (var i = count - 4; i <= count; i++) {
					        setPageList();
					      };
					    } else { //当前页在中间部分
					      a[a.length] = "<a onclick=\"goPage('getOrderList.do?coachphone="+coachphone+"&studentphone="+studentphone+"&startminsdate="+startminsdate+"&startmaxsdate="+startmaxsdate+
					  	"&endminsdate="+endminsdate+"&endmaxsdate="+endmaxsdate+"&createminsdate="+createminsdate+"&createmaxsdate="+createmaxsdate+"&state="+state+"&ordertotal="+ordertotal+"&inputordertotal="+inputordertotal+"&ishavacomplaint="+ishavacomplaint+"&t_paytype="+t_paytype+"&t_ordertype="+t_ordertype+"&index="+index+"&change_id="+change_id+"&overtimeRangeS="+overtimeRangeS+"&overtimeRangeE="+overtimeRangeE+"&',1)\">1</a>...";
					      for (var i = pageindex - 2; i <= pageindex+2; i++) {
					        setPageList();
					      }
					      a[a.length] = "...<a onclick=\"goPage('getOrderList.do?coachphone="+coachphone+"&studentphone="+studentphone+"&startminsdate="+startminsdate+"&startmaxsdate="+startmaxsdate+
					  	"&endminsdate="+endminsdate+"&endmaxsdate="+endmaxsdate+"&createminsdate="+createminsdate+"&createmaxsdate="+createmaxsdate+"&state="+state+"&ordertotal="+ordertotal+"&inputordertotal="+inputordertotal+"&ishavacomplaint="+ishavacomplaint+"&t_paytype="+t_paytype+"&t_ordertype="+t_ordertype+"&index="+index+"&change_id="+change_id+"&overtimeRangeS="+overtimeRangeS+"&overtimeRangeE="+overtimeRangeE+"&',"+count+")\">" + count + "</a>";
					    }
					  }
					  if (pageindex == count) {
						    a[a.length] = "<a onclick=\"\" class=\"hide_page_next unclicknext\"></a> 共"+count+"页  到第  "+
						    "<input type=\"text\" class=\"jump_num\" id=\"topage\"/> 页"+
						    "<a class=\"jump_btn\" onclick=\"gotoPage('getOrderList.do?coachphone="+coachphone+"&studentphone="+studentphone+"&startminsdate="+startminsdate+"&startmaxsdate="+startmaxsdate+
							"&endminsdate="+endminsdate+"&endmaxsdate="+endmaxsdate+"&createminsdate="+createminsdate+"&createmaxsdate="+createmaxsdate+"&state="+state+"&ordertotal="+ordertotal+"&inputordertotal="+inputordertotal+"&ishavacomplaint="+ishavacomplaint+"&t_paytype="+t_paytype+"&t_ordertype="+t_ordertype+"&index="+index+"&change_id="+change_id+"&overtimeRangeS="+overtimeRangeS+"&overtimeRangeE="+overtimeRangeE+"&',"+$("#pageSize").val()+")\")\">"+
						    "<a id='page_msg'></a>";
						  } else {
						    a[a.length] = 
						    	"<a onclick=\"nextPage("+$("#pageIndex").val()+",'getOrderList.do?coachphone="+coachphone+"&studentphone="+studentphone+"&startminsdate="+startminsdate+"&startmaxsdate="+startmaxsdate+
						    	"&endminsdate="+endminsdate+"&endmaxsdate="+endmaxsdate+"&createminsdate="+createminsdate+"&createmaxsdate="+createmaxsdate+"&state="+state+"&ordertotal="+ordertotal+"&inputordertotal="+inputordertotal+"&ishavacomplaint="+ishavacomplaint+"&t_paytype="+t_paytype+"&t_ordertype="+t_ordertype+"&index="+index+"&change_id="+change_id+"&overtimeRangeS="+overtimeRangeS+"&overtimeRangeE="+overtimeRangeE+"&')\" "+
						    	"class=\"page_next\"></a> 共"+count+"页 到第 "+
						    "<input type=\"text\" class=\"jump_num\" id=\"topage\"/> 页"+
						    "<a class=\"jump_btn\" onclick=\"gotoPage('getOrderList.do?coachphone="+coachphone+"&studentphone="+studentphone+"&startminsdate="+startminsdate+"&startmaxsdate="+startmaxsdate+
							"&endminsdate="+endminsdate+"&endmaxsdate="+endmaxsdate+"&createminsdate="+createminsdate+"&createmaxsdate="+createmaxsdate+"&state="+state+"&ordertotal="+ordertotal+"&inputordertotal="+inputordertotal+"&ishavacomplaint="+ishavacomplaint+"&t_paytype="+t_paytype+"&t_ordertype="+t_ordertype+"&index="+index+"&change_id="+change_id+"&overtimeRangeS="+overtimeRangeS+"&overtimeRangeE="+overtimeRangeE+"&',"+$("#pageSize").val()+")\">"+
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