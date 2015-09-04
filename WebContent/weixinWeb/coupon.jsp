<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>小巴券</title>
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="css/orderconfirm.css" rel="stylesheet" type="text/css" />
<style type="text/css">

</style>
</head>

<body>
<div class="container">
	<div class="coupon-content">
    	<div class="row coupon-detail border-grey">
        	<div class="col-md-4 col-sm-4 col-xs-4 coupon-detail-left coupon-detail-left-off">
            	<span>学时</span>
                <span>1</span>
            </div>
            <div class="col-md-6 col-sm-6 col-xs-6 coupon-detail-mid coupon-detail-mid-off">
            	<p>学时券</p>
                <p>指定教练：刘熊德</p>
                <p>有效期至2015-9-30</p>
            </div>
            <div class="col-md-2 col-sm-2 col-xs-2 coupon-detail-right-off"></div>
        </div>
        
        <div class="row coupon-ing coupon-detail border-blue">
        	<div class="col-md-4 col-sm-4 col-xs-4 coupon-detail-left coupon-detail-left-on">
            	<span>学时</span>
                <span>1</span>
            </div>
            <div class="col-md-6 col-sm-6 col-xs-6 coupon-detail-mid coupon-detail-mid-on">
            	<p>学时券</p>
                <p>指定教练：刘熊德</p>
                <p>有效期至2015-9-30</p>
            </div>
            <div class="col-md-2 col-sm-2 col-xs-2 coupon-detail-right-on"></div>
        </div>
        
        <div class="row coupon-detail border-grey">
        	<div class="col-md-4 col-sm-4 col-xs-4 coupon-detail-left coupon-detail-left-off">
            	<span>学时</span>
                <span>1</span>
            </div>
            <div class="col-md-6 col-sm-6 col-xs-6 coupon-detail-mid coupon-detail-mid-off">
            	<p>学时券</p>
                <p>指定教练：刘熊德</p>
                <p>有效期至2015-9-30</p>
            </div>
            <div class="col-md-2 col-sm-2 col-xs-2 un-coupon-detail-right"></div>
        </div>
        
        
    </div>
</div>

<script src="js/jquery-1.8.3.min.js"></script>
<script>
$(document).ready(function ()
{
	$(".coupon-ing").toggle( 
	  function () { 
	  $('.coupon-detail-right-on').addClass("active"); 
	  }, 
	  function () { 
	  $('.coupon-detail-right-on').removeClass("active"); 
	  });
});
</script>
</body>
</html>
