<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="checksession.jsp" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>城市选择</title>
<link href="css/reset.css" rel="stylesheet" type="text/css" />
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="css/font-awesome.css" rel="stylesheet" />
<link href="css/city.css" rel="stylesheet" type="text/css" />
<style type="text/css">
header {
	background-color: white;
	padding: 11px 0px;
	border-bottom: 1px solid #eee;
}
header .row {
	margin: 0px;
	padding: 0px;
}
header .row .col-md-2 {
	padding-right: 0px;
}
header .row .col-md-10 {
	padding-left: 0px;
}
header .row .col-md-2 span.search {
	color: rgb(187,187,187);
}
header .row .col-md-2 span.search i.icon-mysearch {
	background-image: url(images/search.png);
	background-repeat: no-repeat;
	background-size: contain;
	display: block;
	width: 19px;
	height: 19px;
}
header .row .col-md-10 input[type="text"] {
	border: none;
	width: 100%;
	height: 19px;
	text-indent: 1em;
}
.hotcity-title {
	margin-top: 20px;
}
.hotcity-title p {
	margin: 0px;
	padding: 0px;
}
.hotcity-title .col-md-12, .hotcity-city .col-md-4 {
	padding: 0px 12px;
}
.hotcity-title .col-md-12 p {
	color: #a5a5a5;
	font-size: 12px;
}
.hotcity-city-wrap .hotcity-city {
	margin-top: 12px;
}
.hotcity-city-wrap .hotcity-city:last-child {
	margin-bottom: 12px;
}
.hotcity-city-wrap .hotcity-city .col-md-4 {
	text-align: center;
}
.hotcity-city-wrap .hotcity-city .col-md-4 a {
	font-size: 13px;
	color: #333333;
	display: block;
	border-radius: 4px;
	background-color: white;
	border: 1px solid #eee;
	line-height: 35px;
}
.city-choose 
{
	background-color: white;
		border-top: 1px solid #bcbcbc;
}
.city-choose .col-md-12 a.choose
{
	text-decoration: none;
	font-size: 12px;
	color: #333333;
	display: block;
	line-height:35px;
	text-indent: 2em;
	border-bottom: 1px solid #bcbcbc;
	margin: 0px -15px;
	background-color:rgb(245,245,245);
	background-repeat:no-repeat;
	background-position:95% 50%;	
}

.current-right {
	background-image:url(images/right-icon.png);
	background-size: 6px 12px;
}
.current-down {
	background-image:url(images/down-icon.png);
	background-size: 12px 6px;
}

ul.citylist
{
	margin-bottom:0px;
	display:none;
}
ul.citylist li
{
	line-height:40px;
	border-bottom: 1px solid #bcbcbc;
}
ul.citylist li:last-child
{
	border-bottom:none;
}
ul.citylist li a
{
	text-decoration:none;
	color:#333333;
	font-size:12px;
	display:block;
}
</style>
</head>

<body>
<header>
  <form>
    <div class="row">
      <div class="col-md-2 col-sm-2 col-xs-2"> <span class="search pull-right"><i class="icon icon-mysearch"></i></span></div>
      <div class="col-md-10 col-sm-10 col-xs-10">
        <input type="text" placeholder="城市搜索" />
      </div>
    </div>
  </form>
</header>
<div class="container">
  <div class="row hotcity-title">
    <div class="col-md-12 col-sm-12 col-xs-12">
      <p>热门城市</p>
    </div>
  </div>
  <div class="hotcity-city-wrap">
    <div class="row hotcity-city">
      <div class="col-md-4 col-sm-4 col-xs-4"> <a href="coachlist.jsp"> 上海 </a> </div>
      <div class="col-md-4 col-sm-4 col-xs-4"> <a href="coachlist.jsp"> 北京 </a> </div>
      <div class="col-md-4 col-sm-4 col-xs-4"> <a href="coachlist.jsp"> 杭州 </a> </div>
    </div>
    <div class="row hotcity-city">
      <div class="col-md-4 col-sm-4 col-xs-4"> <a href="coachlist.jsp"> 金华 </a> </div>
    </div>
  </div>
  <!--********************************************************************************-->
  <div class="row city-choose">
    <div class="col-md-12 col-sm-12 col-xs-12"> 
        <a class="choose current-right">A</a>
      	<ul class="citylist">
        	<li><a href="coachlist.jsp">澳门</a></li>
            <li><a href="coachlist.jsp">鞍山</a></li>
            <li><a href="coachlist.jsp">安阳</a></li>
            <li><a href="coachlist.jsp">安庆</a></li>
            <li><a href="coachlist.jsp">阿坝</a></li>
            <li><a href="coachlist.jsp">安顺</a></li>
        </ul>
        <a class="choose current-right">B</a>
      	<ul class="citylist">
        	<li><a href="coachlist.jsp">北京</a></li>
            <li><a href="coachlist.jsp">保定</a></li>
            <li><a href="coachlist.jsp">宝鸡</a></li>
            <li><a href="coachlist.jsp">包头</a></li>
            <li><a href="coachlist.jsp">北海</a></li>
            <li><a href="coachlist.jsp">本溪</a></li>
        </ul>
        <a class="choose current-right">C</a>
      	<ul class="citylist">
        	<li><a href="coachlist.jsp">北京</a></li>
            <li><a href="coachlist.jsp">保定</a></li>
            <li><a href="coachlist.jsp">宝鸡</a></li>
            <li><a href="coachlist.jsp">包头</a></li>
            <li><a href="coachlist.jsp">北海</a></li>
            <li><a href="coachlist.jsp">本溪</a></li>
        </ul>
        <a class="choose current-right">D</a>
      	<ul class="citylist">
        	<li><a href="coachlist.jsp">北京</a></li>
            <li><a href="coachlist.jsp">保定</a></li>
            <li><a href="coachlist.jsp">宝鸡</a></li>
            <li><a href="coachlist.jsp">包头</a></li>
            <li><a href="coachlist.jsp">北海</a></li>
            <li><a href="coachlist.jsp">本溪</a></li>
        </ul>
        <a class="choose current-right">C</a>
      	<ul class="citylist">
        	<li><a href="coachlist.jsp">北京</a></li>
            <li><a href="coachlist.jsp">保定</a></li>
            <li><a href="coachlist.jsp">宝鸡</a></li>
            <li><a href="coachlist.jsp">包头</a></li>
            <li><a href="coachlist.jsp">北海</a></li>
            <li><a href="coachlist.jsp">本溪</a></li>
        </ul>
    </div>
  </div>
  <!--********************************************************************************--> 
</div>
<script src="js/jquery-1.8.3.min.js"></script> 
<script src="js/bootstrap.min.js"></script> 
<script>
//slidedown-icon
$(document).ready(function ()
{
	$(".choose").click(function(){
		$(this).toggleClass("current-right").siblings(".choose").addClass("current-right");
		$(this).toggleClass("current-down").siblings(".choose").removeClass("current-down");
		$(this).next(".citylist").slideToggle(300).siblings(".citylist").slideUp(500);
	})	
});

</script>
</body>
</html>
