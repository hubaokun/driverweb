<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
//暂未使用到
var studentid='${sessionScope.studentid}';
if(sid=='' || studentid==0){
	window.location.href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxabb11606edd48e8c&redirect_uri=http://wx.xiaobaxueche.com/dadmin/weixinWeb/weixin?action=login&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect";
}
</script>
