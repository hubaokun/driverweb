<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%-- <%@ include file="checksession.jsp" %>   --%>  
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>提现</title>
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="css/font-awesome.css" rel="stylesheet" />
<link href="css/cash.css" rel="stylesheet" type="text/css" />
<script src="js/jquery-1.8.3.min.js"></script> 
<script src="js/jquery-ui-1.10.3.min.js"></script> 
<script type="text/javascript">
var customer=${sessionScope.c_info};
var studentid =${sessionScope.studentid};
//var token='${sessionScope.token}';
$(function(){
		$("#cname").html(customer.nickname);
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
		$("#cash").html("￥"+obj.balance);
	}
}
</script>
</head>

<body>
<div class="container">
  <form>
  	<div class="row tips-row1">
      <div class="col-md-12 col-sm-12 col-xs-12">
        <p>提现到<b id="cname" style="font-size:13px;">张三</b>微信钱包</p>
      </div>
    </div>
    <div class="row money-row">
      <div class="col-md-12 col-sm-12 col-xs-12">
        <input id="amount" type="number" placeholder="请输入金额" maxlength="3"/>
      </div>
    </div>
    <div class="row tips-row2">
      <div class="col-md-12 col-sm-12 col-xs-12">
        <p>可提现金额<span id="cash">￥${param.money}</span><span>（1次可提现金额最高为999元）</span></p>
      </div>
    </div>
    <div class="row sure-row">
      <div class="col-md-12 col-sm-12 col-xs-12"> <span id="confirm" onclick="applycash()" class="cash-sure">确定</span></div>
    </div>
  </form>
</div>

<!--确定弹出框 starts-->
<div class="overlay">
  <div class="overlay-inner">
    <div class="container">
      <div class="overlay-content">
        <div class="row">
          <div class="col-md-12">
            <p><i class="icon icon-ok-sign"></i>提现申请提交成功</p>
            <hr/>
            <span class="sure-btn">确定</span> 
           </div>
        </div>
      </div>
    </div>
  </div>
</div>
<script>
$(document).ready(function()
{
	$('.account-row .col-md-12:last-child span').on('click',function()
	{
		//alert("3333");
		$('.account-row .col-md-12:last-child span').css('display','none');
	})
	$('#alipay').keydown(function ()
	{
		var v = $('#alipay').val();
		if (v != "")
		{
			$('.account-row .col-md-12:last-child span').css('display','block');
		}
		if (v == "")
		{
			$('.account-row .col-md-12:last-child span').css('display','none');
		}
	});

//	$('#alipay').blur(function()
//	{
//		$('.account-row .col-md-12:last-child span').css('display','none');
//	});

	//控制确定弹框位置
	var height = $(window).height();
	$('.sure-btn').click(function ()
	{
		$('.overlay').css('display','none');
		window.location.href="my.jsp";
	})
});
function applycash()
{
	var amount=$("#amount").val();
	if(amount==0)
	{
	   alert("提现金额不能为0");
	   $("#amount").val("");
	   return;
	}
	var action="APPLYCASH";
	if(parseInt(amount)==amount)
	{   
		$.ajax({
			type : "POST",
			url : "../suser",
			dataType: "json",
			data : {
				action : action,
				studentid : studentid,
				count : amount,
				resource : 1,
				token : token
			},
			success : function(data) {
				var code=data.code;
				if(code!=1)
				{
					var message=data.message;
				    alert(message);	
				}
				else
				{
					$('.overlay').css('display','block');
					var h_content = $('.overlay-content').height();
					var hh = (height-h_content)/2;
					$('.overlay-content').css('margin-top',hh);
					 
				}
			}
		})
	}
	else
	{
		alert("提现金额必须是整数");
		$("#amount").val("");
	}
}


//控制输入框中输入的位数
var amount = $('#amount');
amount.keydown(function(){
	var curLength=amount.val().length;	
	if(curLength>=3){
		var num=amount.val().substr(0,2);
		amount.val(num);
	}
});
</script>
</body>
</html>
