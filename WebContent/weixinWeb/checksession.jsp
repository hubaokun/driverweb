<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<<<<<<< HEAD
<script type="text/javascript">
=======
<script type="text/javascript">
<<<<<<< HEAD
>>>>>>> 429fefb99380dfb9f1504f0e693cc46ce55bb29c
=======
>>>>>>> 664cd1b947f686cd9e0a6913b1d591dd7b116a19
>>>>>>> 2f2066cfe93a4cf833e95cb3caec1eddc8385b95
var g_studentId='${sessionScope.studentid}';
var redirect_login="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxabb11606edd48e8c&redirect_uri=http://wx.xiaobaxueche.com/dadmin/weixinWeb/weixin?action=login&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect";
if(g_studentId==''){
	window.location.href=redirect_login;
}
</script>
