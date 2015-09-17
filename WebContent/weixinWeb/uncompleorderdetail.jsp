<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>未完成订单</title>
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="css/font-awesome.css" rel="stylesheet" />
<link href="css/orderdetail.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery-1.8.3.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	var studentid='${sessionScope.studentid}';
	//studentid="18";
	var orderid='${param.orderid}';
	//orderid='25249';
	var token='${sessionScope.token}';
	$("#orderid").html(orderid);
	var params = {
					action:"getOrderDetail",
					studentid:studentid,
					orderid:orderid,
					token:token
				};
	jQuery.post("../sorder", params, showOrderDetail, 'json');
});

function showOrderDetail(obj){
	if(obj.code==1){
		var order=obj.orderinfo;
		$("#subjectname").html(order.subjectname);	
		$("#start_time").html(order.start_time);	
		$("#total").html("￥"+order.total);	
		$("#realname").html(order.cuserinfo.realname);
		$("#phone").html(order.cuserinfo.phone);
		$("#detail").html(order.detail);
		var content_list="";
		if(order.studentstate==4){
    		content_list=content_list+'<span class="complain-btn">已提交取消订单申请,等待教练确认中!</span>';
    	}else if(order.can_cancel==1){
    		content_list=content_list+'<span class="sure-btn" onclick="cancelOrder('+orderlist[i].orderid+')">取消订单</span>';
    	}
		$("#unorder").html(content_list);
	}else{
		alert(obj.message);
		
	}
}
</script>
</head>

<body>
<div class="container">
     <!--  <div class="row carlearn-head carlearn-head-blue">
          <span>学车中</span>
      </div> -->
      <div class="row carlearn-detail">
		<div class="col-md-12 col-sm-12 col-xs-12">
          <div class="carlearn-detail-inner carlearn-detail-inner-grey">
          	<p><span>编号：</span><span id="orderid"></span></p>
            <p><span>科目：</span><span id="subjectname"></span></p>
            <p><span>时间：</span><span id="start_time"></span></p>
            <!-- <p><span>价格：</span><span id="total">￥90/小时</span></p> -->
            <p><span>教练：</span><span id="realname"></span></p>
            <p><span>电话：</span><span id="phone"></span></p>
            <p><span>地址：</span><span id="detail"></span></p>
            <!-- <p><span>优惠：</span><span>小巴券（-￥90）</span></p> -->
          </div>
        </div>
        <div class="col-md-12 col-sm-12 col-xs-12">
        	<div class="total-wrap total-wrap-grey"></div>
        </div>
        <div class="col-md-12 col-sm-12 col-xs-12">
        	<p class="grey"><span>合计：</span><span id="total"></span></p>
        </div>
      </div>
      <div class="row carlearn-sure-btn">
      	<div class="col-md-12 col-sm-12 col-xs-12">
        	<p id="unorder">
              <!--  <span class="complain-btn">投诉</span>
               <span class="sure-btn">确认上车</span> -->
             </p> 
        </div>
      </div>  
      <div class="row carlearn-list">
      	<!-- <div class="col-md-12 col-sm-12 col-xs-12">
        	<div class="carlearn-list-inner">
              <p>订单标号：123456789</p>
              <p>付款时间：2015-09-09 15:30:03</p>
              <p>付款方式：支付宝</p>
            </div>
        </div> -->
      </div>    
      
      
  </div>
</body>
</html>
