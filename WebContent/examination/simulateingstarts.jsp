<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>模拟考试引导页</title>
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="css/reset.css" rel="stylesheet" type="text/css" />
<link href="css/style.css" rel="stylesheet" type="text/css" />
<style type="text/css"></style>
</head>

<body>
<div class="container simulatestarting">
	<div class="row">
    	<div class="col-md-12 col-sm-12 col-xs-12">
        	<img src="images/boy.png" class="img-responsive img-circle center-block" />
        </div>
    </div>
	<div class="row simulate-tips">
    	<div class="col-md-12 col-sm-12 col-xs-12">
        	<p><span class="pull-left">考试科目</span><span class="pull-right exam-name"></span></p>
        </div>
    </div>
    <div class="row simulate-tips">
    	<div class="col-md-12 col-sm-12 col-xs-12">
        	<p><span class="pull-left">考试车型</span><span class="pull-right">小车（C1,C2）</span></p>
        </div>
    </div>
    <div class="row simulate-tips">
    	<div class="col-md-12 col-sm-12 col-xs-12">
        	<p><span class="pull-left">考试标准</span><span class="pull-right exam-standard"></span></p>
        </div>
    </div>
    <div class="row simulate-tips">
    	<div class="col-md-12 col-sm-12 col-xs-12">
        	<p><span class="pull-left">合格标准</span><span class="pull-right">满分100分，90分及格</span></p>
        </div>
    </div>
    <div class="row simulate-start-btn">
    	<div class="col-md-12 col-sm-12 col-xs-12">
        	<a href="" id="answerstarting">开始答题</a>
        </div>
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
	var simulatetype = getquerystring("simulatetype");
	var duration = "";
	var obj_standard = $('span.exam-standard');
	var obj_name = $('span.exam-name');
	
	if (simulatetype == 1)
	{
		duration = 45;
		obj_standard.html("").html("100题，45分钟");
		obj_name.html("").html("科目一理论考试");
	}
	else if (simulatetype == 2)
	{
		duration = 30;
		obj_standard.html("").html("100题，30分钟");
		obj_name.html("").html("科目四理论考试");
	}
	else
	{
		alert ("数据出错啦！");
	}
	
	var url_str = "simulatelist.jsp?simulatetype=" + simulatetype + "&duration=" + duration;
	
	$('#answerstarting').attr('href',url_str);
});
</script>
</body>
</html>
