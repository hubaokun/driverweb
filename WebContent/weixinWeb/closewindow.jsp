<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
//检测session
/* String studentid=(String)session.getAttribute("studentid");
if(studentid==null){
	//response.sendRedirect("login.jsp");
} */
//session.setAttribute("studentid", "18");
//*************暂未使用到******************
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>close</title>
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="css/login.css" rel="stylesheet" type="text/css" >
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="js/jquery-1.8.3.min.js"></script> 
<style type="text/css"></style>
<script>
var customer=${sessionScope.c_info};
var openid=customer.openid;
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
<script type="text/javascript">
	function clwin()
	{
		window.location.href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxabb11606edd48e8c&redirect_uri=http://wx.xiaobaxueche.com/dadmin/weixinWeb/weixin?action=login&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect";
	}
</script>
<body>
<%-- <input id="noncestr" type="hidden" value="${noncestr}"/>
<input id="timestamp" type="hidden" value="${timestamp}"/>
<input id="signature" type="hidden" value="${signature}"/>
<input id="s_type" type="hidden" value="2"/>
<input id="appid" type="hidden" value="${appid}"/>
<input id="devicetype" type="hidden" value="3"/>
<input id="version" type="hidden" value="1.0"/> --%>
<input type="button" value="登录信息已过期，请重新登录!" onclick="clwin()">
</body>
</html>


