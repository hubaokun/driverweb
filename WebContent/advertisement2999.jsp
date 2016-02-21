<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="com.daoshun.common.Constant" %>
<%@ page import="com.daoshun.guangda.servlet.AdvertisementForm"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>在小巴学车只要2999</title>
<style>
*{
	margin:0px;
	padding:0px;

}
.content{
    width: 100%;
    top: 0px;
    left: 0px;
    background: url(./imgs/ads/2999.png) no-repeat;
    height: 179rem;
    background-size: 100% auto;
}
.adImage
{
   width:100%;
   height:auto;
}
.name
{
    position: absolute;
    display: block;
    top: 117rem;
    height: 5.9rem;
    margin-left: 3.3rem;
    width: 53.2rem;
    border:solid 0.01rem rgb(254,164,146);
    background:rgb(254,218,196);
    font-size:3em;
}
.phone
{
    position: absolute;
    display: block;
    top: 130rem;
    height: 5.9rem;
    margin-left: 3.3rem;
    width: 53.2rem;
    border: solid 0.01rem rgb(254,164,146);
    background: rgb(254,218,196);
    font-size:3em;
}
.submit
{
	position: absolute;
    display: block;
    top: 139.5rem;
    height: 5.9rem;
    border-radius: 0.9rem;
    margin-left: 18.5rem;
    width: 22.2rem;
    border: solid 0.01rem rgb(254,164,146);
    background: rgb(251,89,86);
    font-size: 3em;
    color:white;
    box-shadow: 0rem 0.5rem 0rem rgb(208,19,15);
}
</style>
</head>
<body>
<form class="content" action="AdvertisementForm?action=<%=Constant.ADVERTISEMENT_SIGNUP_ACTION%>" method="post">
<input type="hidden" name="source" value="baidu"/>
<input type="hidden" name="advertiseName" value="学车必须这个价 2999"/>
<input type="text" name="name" class="name" autocomplete="off" required="required" maxlength="50"/>
<input type="number" name="phoneNum" class="phone" autocomplete="off" required="required" maxlength="11"/>
<input type="submit" class="submit" value="提交报名"/>
</form>
</body>
<script>
window.onload=function()
{
	var SIGNUP_SUCCESS=1;
	var SIGNUP_FAILED=-1;
	var code='${code}';
	var info="";
		if(SIGNUP_SUCCESS==code)
		{
			info="报名成功！";
		}
		if(SIGNUP_FAILED==code)
		{
			info="报名失败！";
		}
	if(info!="")
	{
		alert(info);
	}
}
</script>
</html>