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
<script type="text/javascript" src="coach/js/coachapply.js"></script>
<script type="text/javascript" src="coach/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
$(function(){
	var index = $("#index").val();
	$("#left_list_"+index).show();
	var j = $("#change_id").val();
	$("#change_"+j+index).addClass('left_list_mask');
	
	var hidamount = $("#hidamount").val();
	if(hidamount==''){
		$("#amount").val(0);
	}else{
		$("#amount").val(hidamount);
	}
	var hiddenschoolid = $("#hiddenschoolid").val();
	$("#driveschoolid").val(hiddenschoolid);
})
</script>
<title>教练提现详情</title>
</head>
<input type="hidden" id="hidamount" value="${amount}" />
<!-- <input type="hidden" id="hidstate" value="${state}" />-->
<input type="hidden" id="index" value="${index}"/>
<input type="hidden" id="change_id" value="${change_id}"/>
<body onload="getTop()">
	<div id="content">
		<jsp:include page="left.jsp" />
		<div id="content_form">
			<div id="content_form_table">
					<font size="4" color="red">当前提现申请详情:</font>
					<br>
					<table border="0" cellspacing="0" cellpadding="0" style="width: 100%;">
					  <tr class="tr_th">
					  <th>教练姓名</th>
					  <th>电话号码</th>
					  <th>提现金额</th>
					  <th>账户余额</th>
					  <th>保证金额</th>
					  <th>冻结金额</th>
					  <th>申请时间</th>
					  <th>支付宝账号</th>
					  </tr>
					<!--<s:iterator value="cacash">--> 
					   <tr class="tr_td">
					    <td class="border_right_bottom">${coach.realname}</td>
					    <td class="border_right_bottom">${coach.phone}</td>
					    <td class="border_right_bottom">${amount}</td>
					    <td class="border_right_bottom">${coach.money}</td>
					    <td class="border_right_bottom">${coach.gmoney}</td>
					    <td class="border_right_bottom">${coach.fmoney}</td>
					    <td style="width: 200px;" class="border_right_bottom"><s:date name="addtime" format="yyyy-MM-dd HH:mm:ss"/></td>
					    <td class="border_right_bottom">${coach.alipay_account}</td>
					   </tr>
					 <!-- </s:iterator>--> 
					</table>
				</div>
	
		</div>
		
		<div id="content_form">
			<div id="content_form_table">
					<font size="4" color="red">历史提现申请详情:</font><br>
					<table border="0" cellspacing="0" cellpadding="0" style="width: 100%;">
					  <tr class="tr_th">
					  <th>教练姓名</th>
					  <th>电话号码</th>
					  <th>提现金额</th>
					  <th>申请时间</th>
					  <th>支付宝账号</th>
					  </tr>
					  <s:iterator value="balancecoachlist">
					   <tr class="tr_td">
					    <td class="border_right_bottom">${realname}</td>
					    <td class="border_right_bottom">${phone}</td>
					    <td class="border_right_bottom">${amount}</td>
					    <td style="width: 200px;" class="border_right_bottom"><s:date name="addtime" format="yyyy-MM-dd HH:mm:ss"/></td>
					    <td class="border_right_bottom">${alipay_account}</td>
					   </tr>
					  </s:iterator>
					</table>
				</div>
		</div>
		<div id="content_form">
			<div id="content_form_table">
					<font size="4" color="red">历史交易订单详情:</font><br>
					<table border="0" cellspacing="0" cellpadding="0" style="width: 100%;">
					  <tr class="tr_th">
					  <th>教练姓名</th>
					  <th>教练电话</th>
					  <th>学员姓名</th>
					  <th>学员电话</th>
					  <th>开始时间</th>
					  <th>结束时间</th>
					  <th>支付方式</th>
					  <th>创建时间</th>
					  <th>结算时间</th>
					  <th>订单状态</th>
					  <th>抵扣金额</th>
					  <th>总价</th>
					  </tr>
					  <s:iterator value="orderlist">
					   <tr class="tr_td">
					    <td class="border_right_bottom">${cuserinfo.realname}</td>
					    <td class="border_right_bottom">${cuserinfo.phone}</td>
					    <td class="border_right_bottom">${studentinfo.realname}</td>
					    <td class="border_right_bottom">${studentinfo.phone}</td>
					    <td class="border_right_bottom">${start_time}</td>
					    <td class="border_right_bottom">${end_time}</td>
					    <td class="border_right_bottom">${paytype}</td>
					    <td class="border_right_bottom">${creat_time}</td>
					    <td class="border_right_bottom">${over_time}</td>
					    <td class="border_right_bottom">已结算</td>
					    <td class="border_right_bottom">${delmoney}</td>
					    <td class="border_right_bottom">${total}</td>
					   </tr>
					  </s:iterator>
					</table>
				</div>
		</div>
		<div id="content_form">
			<div id="content_form_table">
					<font size="4" color="red">学员历史交易金额充值记录:</font><br>
					<table border="0" cellspacing="0" cellpadding="0" style="width: 100%;">
					  <tr class="tr_th">
					  <th>真实姓名</th>
					  <th>电话号码</th>
					  <th>充值金额</th>
					  <th>充值账号</th>
					  <th>申请处理时间</th>
					  </tr>
					  <s:iterator value="recharglist">
					   <tr class="tr_td">
					    <td class="border_right_bottom">${suser.realname}</td>
					    <td class="border_right_bottom">${suser.phone}</td>
					    <td class="border_right_bottom">${amount}</td>
					    <td class="border_right_bottom">${suser.alipay_account}</td>
					    <td style="width: 200px;" class="border_right_bottom"><s:date name="addtime" format="yyyy-MM-dd HH:mm:ss"/></td>
					    <td></td>
					   </tr>
					  </s:iterator>
					</table>
				</div>
		</div>
	</div>
</body>
</html>