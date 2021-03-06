<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="checksession.jsp" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>账户余额详情</title>
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="css/font-awesome.css" rel="stylesheet" />
<link href="css/account.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="js/checksession.js"></script>
<script type="text/javascript">
var studentid='${sessionScope.studentid}';
var token='${sessionScope.token}';
$(document).ready(function(){
	var params = {
					action:"GETMYBALANCEINFO",
					studentid:studentid,
					token:token
				  };
	jQuery.post("../suser", params, showBalance, 'json');
});

function showBalance(obj){
	if(obj.code==1){
		//设置当前余额
		$("#money").html('<i>￥</i>'+obj.balance);
		$("#fmoney").html('（冻结金额￥'+obj.fmoney+'）');
		$("#cash").attr("href","cash.jsp?money="+obj.balance);
		var rs=obj.recordlist;
		if(rs.length>0){
			var h="";
			for (var i = 0; i < rs.length; i++) {
				var type='';
				var op='';
				//1:支付宝充值  2：提现  3：订单支付 4:提现不通过 5 
				if(rs[i].type==1){
					type="支付宝充值";
					op="+";
				}else if(rs[i].type==2){
					type="提现";
					op="-";
				}else if(rs[i].type==3){
					type="订单支付";
					op="-";
				}else if(rs[i].type==4){
					type="提现不通过";
					op="+";
				}else if(rs[i].type==5){
					type="微信充值";
					op="+";
				}
				h=h+'<div class="col-md-12 col-sm-12 col-xs-12"><p><span>'+type+'</span><span class="pull-right ';
				if(op=='+'){
					h=h+'add">';
				}else{
					h=h+'decrease">';
				}
				
				h=h+op+rs[i].amount+'</span></p><p><span>'+rs[i].addtime+'</span><span class="pull-right"></span></p></div>';
			}
			//alert(h);
			$("#balanceDetail").html(h);
			
		}
	}else{
		alert(obj.message);
		window.location.href=redirect_login;
	}
}

</script>

</head>

<body>
<div class="container">
	<!-- <div class="row account-head">
    	<div class="col-md-12 col-sm-12 col-xs-12">
        	<p>可用余额（元）<i class="icon icon-question-sign"></i></p>
            <p id="money">1111</p>
            <p id="restMoney">￥2545</p>
        </div>
    </div> -->
    <div class="row account-head">
    	<div class="col-md-12 col-sm-12 col-xs-12">
        	<p>可用余额（元）<i class="icon icon-question-sign"></i></p>
            <p id="money"><i>￥</i></p>
            <p id="fmoney"></p>
        </div>
    </div>
    <div class="row account-money">
    	<div class="col-md-5 col-sm-5 col-xs-5">
			<a href="weixin?action=charge">
            	<i class="account-icon icon-charge"></i>
            <span>充值</span>
            </a>
        </div>
        <div class="col-md-2 col-sm-2 col-xs-2">
        	<div class="divider-line"></div>
        </div>
        <div class="col-md-5 col-sm-5 col-xs-5">
        	<a id="cash" href="cash.jsp?money=" style="  margin-left: -40px;">
            	<i class="account-icon icon-cash"></i>
            <span>提现</span>
            </a>
        </div>
    </div>
    <div class="row account-bill">
		<div class="col-md-12 col-sm-12 col-xs-12">
        
        	<div class="row account-bill-head">
            	<div class="col-md-12 col-sm-12 col-xs-12"><span>账单</span></div>
            </div>
            <div class="row"><div class="col-md-12 col-sm-12 col-xs-12"><hr/></div></div>
            <div id="balanceDetail" class="row account-bill-body">
            	<!-- <div class="col-md-12 col-sm-12 col-xs-12">
                	<p><span>预约学车培训</span><span class="pull-right decrease">-200</span></p>
                    <p><span>2015-12-12 12:12:12</span><span class="pull-right">余额：￥123</span></p>
                </div>

                <div class="col-md-12 col-sm-12 col-xs-12">
                	<p><span>充值</span><span class="pull-right add">+200</span></p>
                    <p><span>2015-12-12 12:12:12</span><span class="pull-right">余额：￥123</span></p>
                </div> -->
            </div>  
        </div>
    </div>

</div>
</body>
</html>
