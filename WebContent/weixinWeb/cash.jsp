<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>提现固定账号修改</title>
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="css/font-awesome.css" rel="stylesheet" />
<link href="css/cash.css" rel="stylesheet" type="text/css" />
<script src="js/jquery-1.8.3.min.js"></script> 
<script src="js/jquery-ui-1.10.3.min.js"></script> 
<script type="text/javascript">
var customer=${sessionScope.c_info};
var studentid =${sessionScope.studentid};
var token='${sessionScope.token}';
$(function(){
		$("#cname").html(customer.nickname);
}); 
</script>
</head>

<body>
<div class="container">
  <form>
  	<div class="row tips-row">
      <div class="col-md-12 col-sm-12 col-xs-12">
        <p>提现到<b id="cname" style="font-size:13px;">张三</b>微信钱包</p>
      </div>
    </div>
    <div class="row money-row">
      <div class="col-md-12 col-sm-12 col-xs-12">
        <input id="amount" type="text" placeholder="请输入金额" maxlength="5"/>
      </div>
    </div>
    <div class="row tips-row">
      <div class="col-md-12 col-sm-12 col-xs-12">
        <p>可提现金额<span style="font-size:14px; color:#eb5811">￥${param.money}</span></p>
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
</script>
</body>
</html>
