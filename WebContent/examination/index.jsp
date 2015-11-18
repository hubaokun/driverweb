<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>科目考试</title>
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="css/reset.css" rel="stylesheet" type="text/css" />
<link href="css/style.css" rel="stylesheet" type="text/css" />
<script src="js/jquery-1.8.3.min.js"></script>
<style type="text/css">

</style>
</head>

<body>
<div class="container">
	<div class="row empty-row"></div>
	<div class="row title-row">
    	<div class="col-md-12 col-sm-12 col-xs-12">
        	<h1>科目一</h1>
        </div>
    </div>
    <div class="row subject-1-row">
    	<div class="col-md-12 col-sm-12 col-xs-12">
        	<ul class="subject-ul">
            	<li><a href="orderlist.jsp?passingtype=1"  class="order-item" id="orderone">顺序练习</a><span class="subject-icon-right"></span></li>
                <li><a href="simulateingstarts.jsp?simulatetype=1"  class="simulate-item" id="ordertwo">模拟考试</a><span class="subject-icon-right"></span></li>
            </ul>
        </div>
    </div>
    <div class="row title-row">
    	<div class="col-md-12 col-sm-12 col-xs-12">
        	<h1>科目四</h1>
        </div>
    </div>
     <div class="row subject-2-row">
    	<div class="col-md-12 col-sm-12 col-xs-12">
        	<ul class="subject-ul">
            	<li><a href="orderlist.jsp?passingtype=2"  class="order-item" id="orderthree">顺序练习</a><span class="subject-icon-right"></span></li>
                <li><a href="multilist.jsp" class="multi-item" id="orderfour">多选题练习</a><span class="subject-icon-right"></span></li>
                <li><a href="orderlist.jsp?passingtype=4"  class="animation-item" id="orderfive">动画题加强练习</a><span class="subject-icon-right"></span></li>
                <li><a href="simulateingstarts.jsp?simulatetype=2"  class="simulate-item" id="ordersix">模拟考试</a><span class="subject-icon-right"></span></li>
            </ul>
        </div>
    </div>
</div>

<!-- pop up window starts-->
<div class="dialog-first dialog-another">
	<div class="dialog-top">
    	<div class="begin">友情提示</div>
        <div class="goon">请检查您的网络连接！</div>
    </div>
    <div class="dialog-bottom">
        <div class="certain only-certain">确定</div>
    </div>
</div>
<div class="overlay-first">
</div>
<!-- pop up window ends-->

<script>
//JS 监听断网或者接入网络
window.addEventListener('load', function() {
    function updateOnlineStatus(event) {
        var condition = navigator.onLine ? "true" : "false";
        if (condition == "true")
        {
        	console.log ("网络连接上了！");
        }
        else
        {
        	showpopwindow ();
        }
    }
    window.addEventListener('online',  updateOnlineStatus);
    window.addEventListener('offline', updateOnlineStatus);
});

//TO DO: funciton for showing and hiding pop up window
function showwindow(obj,obj_overlay,flag)
{
	var objoverlay = $('.overlay');
	
	obj.css('display','block');
	obj_overlay.css('display','block');
	
	if (flag)
	{
		var window_W = $(window).width();
		var window_H = $(window).height();
		
		var width = obj.width();
		var height = obj.height();
		var w = (window_W - width)/2;
		var h = (window_H - height)/2;
		
		obj.css('top',h);
		obj.css('left',w);
	}
	else
	{
		return false;
	}
}

function hidewindow(obj,obj_overlay)
{
	obj.css('display','none');
	obj_overlay.css('display','none');
}

//TO DO: show and hide the pop up window to ensure handing in the current paper
function showpopwindow ()
{
	var obj = $('.dialog-first');
	var obj_overlay = $('.overlay-first');

	var objcount = $('.dialog-first .goon');
	
	showwindow(obj,obj_overlay,true);
}
function hidepopwindow ()
{
	var obj = $('.dialog-first');
	var obj_overlay = $('.overlay-first');
	
	hidewindow(obj,obj_overlay);
}
</script>
<script>
$(document).ready(function ()
{
	var obj_btn = $(".only-certain");
	var overlay = $(".overlay-first");
	obj_btn.on('click',function ()
	{
		hidepopwindow ();
	});
	overlay.on('click',function ()
	{
		hidepopwindow ();
	});
});
</script>
</body>
</html>
