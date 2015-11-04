<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>答题成绩</title>
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="css/reset.css" rel="stylesheet" type="text/css" />
<link href="css/style.css" rel="stylesheet" type="text/css" />
<style type="text/css">
</style>
</head>

<body>
<div class="container resultpage">
  <div class="row resultpage-img">
    <div class="col-md-12 col-sm-12 col-xs-12"> <img src="images/cheer.png" class="img-responsive center-block" /> </div>
  </div>
  <div class="row">
    <div class="col-md-12 col-sm-12 col-xs-12">
      <div class="col-md-6 col-sm-6 col-xs-6">分数：<i id="myscore"></i></div>
      <div class="col-md-6 col-sm-6 col-xs-6">用时：<i id="mytime"></i></div>
    </div>
  </div>
  <div class="row resultpage-btn">
    <div class="col-md-6 col-sm-6 col-xs-6"> <a href="index.jsp">首页</a> </div>
    <div class="col-md-6 col-sm-6 col-xs-6"> <a href="" id="restart">重考</a> </div>
  </div>
</div>
<script src="js/jquery-1.8.3.min.js"></script>
<script>
//TO DO:  从URL地址中获得参数
function getquerystring(name)
{
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);
     if(r!=null)return  unescape(r[2]); return null;
}

$(document).ready(function ()
{
	var myscore = 100 - getquerystring("myscore");
	var simulatetype = getquerystring("simulatetype");
	
	var obj_restart = $('#restart');
	var url_str = "simulateingstarts.jsp?simulatetype=" + simulatetype;
	obj_restart.attr('href',url_str);
	$('#myscore').html(myscore);
});

</script>
</body>
</html>
