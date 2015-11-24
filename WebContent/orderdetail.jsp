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
<script type="text/javascript">
$(function(){
	var index = $("#index").val();
	$("#left_list_"+index).show();
	var j = $("#change_id").val();
	$("#change_"+j+index).addClass('left_list_mask');
})
</script>
<title>投诉详情</title>
</head>
<input type="hidden" id="index" value="${index}" />
<input type="hidden" id="change_id" value="${change_id}"/>
<body onload="getTop()">
<div id="content">
<jsp:include page="left.jsp"/>
<div id="content_form">

<div id="content_form_top">
	<div class="delbutton">&nbsp;详细资料</div>
</div>
<div id="content_form_table">
<table  border="0" cellspacing="0" cellpadding="0" style="width: 98%;">
<tr class="tr_th">
<th>ID</th>
<th>教练真实姓名</th>
<th>学员真实姓名</th>
<th>开始时间</th>
<th>结束时间</th>
<th>总价</th>
<th>支付方式</th>
<th>混合支付中使用小巴币个数</th>
<th>混合支付中使用余额数量</th>
<th>订单状态</th>
</tr>
<tr class="tr_td">
<td style="width: 52px;" class="border_right_bottom"><s:property value="order.orderid"/></td>
<td style="width: 100px;" class="border_right_bottom"><s:property value="coach.realname"/></td>
<td style="width: 100px;" class="border_right_bottom"><s:property value="student.realname"/></td>
<td style="width: 200px;" class="border_right_bottom"><s:date name="order.start_time" format="yyyy-MM-dd HH:mm:ss"/></td>
<td style="width: 200px;" class="border_right_bottom"><s:date name="order.end_time" format="yyyy-MM-dd HH:mm:ss"/></td>
<td style="width: 100px;" class="border_right_bottom"><s:property value="order.total"/></td>
<td style="width: 100px;" class="border_right_bottom" paytype='<s:property value="order.paytype"/>'>
<s:if test="order.paytype==1">
	余额
</s:if>
<s:elseif test="order.paytype==2">
	小巴券
</s:elseif>
<s:elseif test="order.paytype==3">
	小巴币
</s:elseif>
<s:elseif test="order.paytype==4">
	小巴币+余额混合支付
</s:elseif>
<s:elseif test="order.paytype==0">
	<s:if test="order.delmoney>0 ">
		小巴券
	</s:if>
	<s:else>
		余额
	</s:else>
</s:elseif>
<s:else>
	未知
</s:else>
</td>
<td style="width: 100px;" class="border_right_bottom"><s:property value="order.mixCoin"/></td>
<td style="width: 100px;" class="border_right_bottom"><s:property value="order.mixMoney"/></td>
<s:if test="order.coachstate==2&&order.over_time!=null&&order.studentstate==3">
	<td style="width: 100px;" class="border_right_bottom">已结算</td>
</s:if>
<s:elseif test="order.coachstate==2&&order.over_time!=null&&order.studentstate==4">
	<td style="width: 100px;" class="border_right_bottom">已取消</td>
</s:elseif>
<s:elseif test="order.coachstate!=2||order.over_time==null||order.studentstate!=3&&order.studentstate!=4">
	<td style="width: 100px;" class="border_right_bottom">未结算</td>
</s:elseif>
</tr>
</table>
</div>

<div id="content_form_top">
	<div class="delbutton">&nbsp;订单操作记录</div>
</div>
<div id="content_form_table">
<table  border="0" cellspacing="0" cellpadding="0" style="width: 98%;">
<tr class="tr_th">
<th>操作</th>
<th>内容</th>
<th>时间</th>
</tr>
<s:iterator value="orderrecordlist" var="cuser" id="list">
<tr class="tr_td">
<s:if test="operation==1">
<td  style="width:200px;" class="border_right_bottom">学员确认上车</td>
<td  style="width:200px;" class="border_right_bottom">${detail}</td>
<td  style="width:200px;" class="border_right_bottom">${addtime}</td>
</s:if>
<s:elseif test="operation==2">
<td  style="width:200px;" class="border_right_bottom">学员确认下车</td>
<td  style="width:200px;" class="border_right_bottom">${detail}</td>
<td  style="width:200px;" class="border_right_bottom">${addtime}</td>
</s:elseif>
<s:elseif test="operation==3">
<td  style="width:200px;" class="border_right_bottom">教练确认上车</td>
<td  style="width:200px;" class="border_right_bottom">${detail}</td>
<td  style="width:200px;" class="border_right_bottom">${addtime}</td>
</s:elseif>
<s:elseif test="operation==4">
<td  style="width:200px;" class="border_right_bottom">教练确认下车</td>
<td  style="width:200px;" class="border_right_bottom">${detail}</td>
<td  style="width:200px;" class="border_right_bottom">${addtime}</td>
</s:elseif>
<s:elseif test="operation==5">
<td  style="width:200px;" class="border_right_bottom">学员取消订单</td>
<td  style="width:200px;" class="border_right_bottom"></td>
<td  style="width:200px;" class="border_right_bottom">${addtime}</td>
</s:elseif>
<s:elseif test="operation==6">
<td  style="width:200px;" class="border_right_bottom">学员评价订单</td>
<td  style="width:200px;" class="border_right_bottom">${content}</td>
<td  style="width:200px;" class="border_right_bottom">${addtime}</td>
</s:elseif>
<s:elseif test="operation==7">
<td  style="width:200px;" class="border_right_bottom">学员投诉订单</td>
<td  style="width:200px;" class="border_right_bottom">${content}</td>
<td  style="width:200px;" class="border_right_bottom">${addtime}</td>
</s:elseif>
<s:elseif test="operation==8">
<td  style="width:200px;" class="border_right_bottom">教练评价订单</td>
<td  style="width:200px;" class="border_right_bottom">${content}</td>
<td  style="width:200px;" class="border_right_bottom">${addtime}</td>
</s:elseif>
<s:elseif test="operation==9">
<td  style="width:200px;" class="border_right_bottom">教练投诉订单</td>
<td  style="width:200px;" class="border_right_bottom">${content}</td>
<td  style="width:200px;" class="border_right_bottom">${addtime}</td>
</s:elseif>
</tr>
</s:iterator>
</table>
</div>
<div id="content_form_top">
	<div class="delbutton" style="width:100px">&nbsp;订单时间点详细</div>
</div>
<div id="content_form_table">
<table  border="0" cellspacing="0" cellpadding="0" style="width:98%;">
<tr class="tr_th">
<th>价格</th>
<th>小时数</th>
<th>科目</th>
<th>上车地点</th>
</tr>
<s:iterator value="orderpricelist" var="cuser" id="list">
<tr class="tr_td">
<td  style="width:100px;" class="border_right_bottom">${price }</td>
<td  style="width:100px;" class="border_right_bottom">${hour }</td>
<td  style="width:200px;" class="border_right_bottom">${subject}</td>
<td  style="width:300px;" class="border_right_bottom">${detail}</td>
</tr>
</s:iterator>
</table>
</div>
</div>
</div>
</body>
</html>