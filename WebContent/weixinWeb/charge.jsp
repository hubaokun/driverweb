<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>充值</title>
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="css/font-awesome.css" rel="stylesheet" />
<link href="css/cash.css" rel="stylesheet" type="text/css" />
<script src="js/jquery-1.8.3.min.js"></script> 
<script src="js/jquery-ui-1.10.3.min.js"></script> 
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript">
var customer=${sessionScope.c_info};
var studentid =${sessionScope.studentid};
var token='${sessionScope.token}';
wx.config({
    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
    appId: '${appid}', // 必填，公众号的唯一标识
    timestamp:'${timestamp}', // 必填，生成签名的时间戳
    nonceStr: '${noncestr}', // 必填，生成签名的随机串
    signature:'${signature}',// 必填，签名，见附录1
    jsApiList: [
				'checkJsApi',
				'onMenuShareTimeline',
				'onMenuShareAppMessage',
				'onMenuShareQQ',
				'onMenuShareWeibo',
				'onMenuShareQZone',
				'hideMenuItems',
				'showMenuItems',
				'hideAllNonBaseMenuItem',
				'showAllNonBaseMenuItem',
				'translateVoice',
				'startRecord',
				'stopRecord',
				'onVoiceRecordEnd',
				'playVoice',
				'onVoicePlayEnd',
				'pauseVoice',
				'stopVoice',
				'uploadVoice',
				'downloadVoice',
				'chooseImage',
				'previewImage',
				'uploadImage',
				'downloadImage',
				'getNetworkType',
				'openLocation',
				'getLocation',
				'hideOptionMenu',
				'showOptionMenu',
				'closeWindow',
				'scanQRCode',
				'chooseWXPay',
				'openProductSpecificView',
				'addCard',
				'chooseCard',
				'openCard'
				] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
    
});

</script>

</head>

<body>
<div class="container">
  <form>
    <div class="row money-row" style="margin-top:28px;">
      <div class="col-md-12 col-sm-12 col-xs-12">
        <input id="amount" type="text" placeholder="请输入金额"  maxlength="5"/>
      </div>
    </div>
    <div class="row sure-row">
      <div class="col-md-12 col-sm-12 col-xs-12"> <span class="cash-sure">微信支付</span> </div>
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
            <p><i class="icon icon-ok-sign"></i><span id="message">正在生成订单.....</span></p>
            <hr/>
            <span class="sure-btn">确定</span> 
           </div>
        </div>
      </div>
    </div>
  </div>
</div>
<script src="js/jquery-1.8.3.min.js"></script>
<script>
$(document).ready(function()
{
	//控制确定弹框位置
	var height = $(window).height();

	$('.cash-sure').click(function ()
	{
		var action ="RECHARGE";
		var amount=$("#amount").val();
		if(amount=="")
		{
			alert("请输入充值金额");
			return;
		}
		else if(amount>10000)
		{
			alert("充值金额不能大于10000");
			$("#amount").val("");
			return;
		}
		if(parseInt(amount)==amount)
		{   
			$.ajax({
				type : "POST",
				url : "/xiaoba/suser",
				dataType: "json",
				data : {
					action : action,
					studentid : studentid,
					amount : amount,
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
						var appid=data.appId;
						var timeStamp=data.timeStamp;
						var prepay_id=data.prepay_id;
						var nonceStr=data.nonceStr;
						var signType=data.signType;
						var paySign=data.paySign;
						var error=data.ERROR;
						if(error!=null && error=="FAIL")
						{
							alert("支付出错,请重新输入");
						}
						else
						{   
							wx.chooseWXPay({
							    timestamp: timeStamp, // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
							    nonceStr: nonceStr, // 支付签名随机串，不长于 32 位
							    package: "prepay_id="+prepay_id, // 统一支付接口返回的prepay_id参数值，提交格式如：prepay_id=***）
							    signType: signType, // 签名方式，默认为'SHA1'，使用新版支付需传入'MD5'
							    paySign: paySign, // 支付签名
							    success: function (res) {
							       // 支付成功后的回调函数 
							    }
							});
						}
					}
					
					
				}
			});
		}
		else
		{
			alert("充值金额必须是整数");
			$("#amount").val("");
		}
		
		
		
	})
	$('.sure-btn').click(function ()
	{
		$('.overlay').css('display','none');
	})
});
function displaywindow()
{
	$('.overlay').css('display','block');
	var h_content = $('.overlay-content').height();
	var hh = (height-h_content)/2;
	$('.overlay-content').css('margin-top',hh);
	$('.sure-btn').css('display','none');
}
function hidewindow()
{
	$('.overlay').css('display','none');
}
</script>
</body>
</html>
