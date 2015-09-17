var g_studentId='${sessionScope.studentid}';
var redirect_login="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxabb11606edd48e8c&redirect_uri=http://wx.xiaobaxueche.com/dadmin/weixinWeb/weixin?action=login&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect";
if(g_studentId==''){
	window.location.href=redirect_login;
}