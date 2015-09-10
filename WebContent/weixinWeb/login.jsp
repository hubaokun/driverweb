<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>登录</title>
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="css/login.css" rel="stylesheet" type="text/css" >
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="js/jquery-1.8.3.min.js"></script> 
<script src="js/jquery-ui-1.10.3.min.js"></script> 
<style type="text/css"></style>
<script src="http://api.map.baidu.com/api?v=2.0&ak=MGt8L6pnsrogzRS6u4TKMGcX" type="text/javascript"></script>
<!-- <style type="text/css">
		body, html{width: 100%;height: 100%;margin:0;font-family:"微软雅黑";font-size:14px;}
		#allmap {width:100%;height:500px;}
	</style> -->
<script>
var customer=${sessionScope.c_info};
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
wx.ready(function(){
    // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
    //  alert("验证成功");
   //   document.querySelector('#getpoision').onclick = function () {
         wx.getLocation({
        	type: 'wgs84', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
      	    success: function (res) {
      	        var latitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
      	       // $('#latitude').val(latitude);
      	        var longitude = res.longitude; // 经度，浮点数，范围为180 ~ -180。
      	       // $('#longitude').val(longitude);
      	        var speed = res.speed; // 速度，以米/每秒计
      	        var accuracy = res.accuracy; // 位置精度
	      	      var geoc = new BMap.Geocoder();
	              var pt=new BMap.Point(longitude,latitude);
	                geoc.getLocation(pt, function(rs){
	              		var addComp = rs.addressComponents;
	              		//alert(addComp.province + ", " + addComp.city + ", " + addComp.district + ", " + addComp.street + ", " + addComp.streetNumber);
	              		$('#city').val(addComp.city);
	              	});
      	    }
      	});
         

});
</script>
</head>

<body>
<div id="allmap"></div>
<input id="noncestr" type="hidden" value="${noncestr}"/>
<input id="timestamp" type="hidden" value="${timestamp}"/>
<input id="signature" type="hidden" value="${signature}"/>
<input id="s_type" type="hidden" value="2"/>
<input id="appid" type="hidden" value="${appid}"/>
<input id="devicetype" type="hidden" value="3"/>
<input id="version" type="hidden" value="1.0"/>
<input id="city" type="hidden" value=""/>
<div class="container">
  <div class="row logo-row">
    <div class="col-md-12 col-sm-12 col-xs-12"> <img src="images/new-logo.png" class="img-responsive center-block" /> </div>
  </div>
  <form>
  
    <div class="row form-row">
      <div class="col-md-12 col-sm-12 col-xs-12">
        <input type="text" placeholder="手机号" id="mobile" value="" class="center-block" onKeyUp="handle();" maxlength="11" />
        <button id="s_vcode" onclick="sendvcode()" type="button" class="un-active" disabled>发送验证码</button></div>
      <div class="col-md-12 col-sm-12 col-xs-12  pull-right div-line"></div>
      <div class="col-md-12 col-sm-12 col-xs-12">
        <input type="text" id="v_code" placeholder="验证码" class="center-block" />
      </div>
    </div>
    <div class="row login-row">
      <div class="col-md-12 col-sm-12 col-xs-12" id="s_login" onclick="slogin()"><a href="javascript:void(0)">登录</a> </div>
    </div>
  </form>
</div>
<script>
function slogin()
{
	var phone=$('#mobile').val();
	var password=$('#v_code').val();
	var devicetype=$('#devicetype').val();
	var version=$('#version').val();
	var action="LOGIN";
	var city=$('#city').val();
	if(phone=="")
	{
	    alert("手机号码不能为空");
	    return;
	}
	if(password=="")
	{
		 alert("验证码不能为空");
		 return;
	}
	$.ajax({
		type : "POST",
		url : "/xiaoba/suser",
		dataType: "json",
		data : {
			action : action,
			phone : phone,
			password : password,
			devicetype : devicetype,
			version : version
		},
		success : function(data) {
			var result=data;
			var code=result.code;
			var message=result.message;
			if(code==2 || code==3)
				alert(message);
			else
				{
					$(".login-row a").attr("href","weixinlogin?action=coachlist");
				    $(".login-row a").get(0).click();
				}
				
			
		}
	});
}
function sendvcode()
{ 
	var action="GETVERCODE";
	var phone=$('#mobile').val();
	var type=$('#s_type').val();
	$.ajax({
		type : "POST",
		url : "/xiaoba/suser",
		dataType: "json",
		data : {
			action : action,
			phone : phone,
			type : type
		},
		success : function(data) {
			var result=data;
			var code=result.code;
			var message=result.message;
			alert(message);
		}
	});
}
function handle()
{
	var v = $('#mobile').val();
	if (v.match("[\u4e00-\u9fa5]") || v.match("[a-zA-Z]"))
	{
		alert("手机号只能为数字");
		$('#mobile').val("");
		return;
	}
	if (v.match("^[0-9]*$"))
	{
	  if (v.length == 11)
	  {
		  $('.form-row button').addClass('active');
		  $('.form-row button').removeAttr("disabled");
	  }
	  if ((v.length < 11) || (v.length > 11))
	  {
		  if ($('.form-row button').hasClass('active'))
		  {
			  $('.form-row button').removeClass('active');
		  }
		  $(".form-row button").attr("disabled","true");
	  }
	}
	
}
</script>
</body>
</html>
