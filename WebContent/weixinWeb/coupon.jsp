<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="checksession.jsp" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>小巴券</title>
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="css/orderconfirm.css" rel="stylesheet" type="text/css" />
<script src="js/jquery-1.8.3.min.js"></script>
<script src="js/jquery-ui-1.10.3.min.js"></script>
<style type="text/css">
.container
{
	padding:0px 12px;
}
</style>

<script type="text/javascript">
$(document).ready(function(){
	var studentid='${sessionScope.studentid}';
	var token='${sessionScope.token}';
	studentid='18';
	var params = {
					action:"GETCOUPONLIST",
					studentid:studentid,
					token:token				
				 };
	jQuery.post("../sbook", params, showCoupon, 'json');
	
    var params2 = {
    				action:"GETHISCOUPONLIST",
    				studentid:studentid,
    				token:token
    			  };
	jQuery.post("../sbook", params2, showHisCoupon, 'json');
});

function showCoupon(obj){
		if(obj.code==1){
			var os=obj.couponlist;
			if(os.length>0){
				var h="";
				for(var i=0;i<os.length;i++){
					var endtime=os[i].end_time.substring(0,10);
					h=h+'<div class="row coupon-ing coupon-detail border-blue">';
					h=h+'<div class="col-md-4 col-sm-4 col-xs-4 coupon-detail-left coupon-detail-left-on"><span>学时</span><span>1</span></div>';
					h=h+'<div class="col-md-8 col-sm-8 col-xs-8 coupon-detail-mid coupon-detail-mid-on"><p>学时券</p><p>指定教练：';
					h=h+os[i].title+'</p><p>有效期至'+endtime+'</p></div></div>';
				}
				$("#couponlist").html(h);
			}
		}else{
			alert(obj.message);
			window.location.href=redirect_login;
		}
		
}

//历史券的状态
//1：已经使用
//0：过期
function showHisCoupon(obj){
	if(obj.code==1){
		var os=obj.couponlist;
		if(os.length>0){
			var h="";
			for(var i=0;i<os.length;i++)
			{
				if (os[i].state == 1)
				{
					var endtime=os[i].end_time.substring(0,10);
					h=h+'<div class="row coupon-detail coupon-detail-right-off border-grey">';
					h=h+'<div class="col-md-4 col-sm-4 col-xs-4 coupon-detail-left coupon-detail-left-off"><span>学时</span><span>1</span></div>';
					h=h+'<div class="col-md-8 col-sm-8 col-xs-8 coupon-detail-mid coupon-detail-mid-off"><p>学时券</p><p>指定教练：';
					h=h+os[i].title+'</p><p>有效期至'+endtime+'</p></div></div>';
				}
				if (os[i].state == 0)
				{
					var endtime=os[i].end_time.substring(0,10);
					h=h+'<div class="row coupon-detail un-coupon-detail-right border-grey">';
					h=h+'<div class="col-md-4 col-sm-4 col-xs-4 coupon-detail-left coupon-detail-left-off"><span>学时</span><span>1</span></div>';
					h=h+'<div class="col-md-8 col-sm-8 col-xs-8 coupon-detail-mid coupon-detail-mid-off"><p>学时券</p><p>指定教练：';
					h=h+os[i].title+'</p><p>有效期至'+endtime+'</p></div></div>';
				}
			}
			$("#hisCouponlist").html(h);
		}
	}	
}

</script>
</head>

<body>
<div class="container">
	<div id="tabs">
  <ul class="coupon-nav">
    <li class="on"><a href="#tabs-1">可用</a></li><li><a href="#tabs-2">历史</a></li>
  </ul>
  <div id="tabs-1">
    <div id="couponlist" class="coupon-content">

    </div>
    
  </div>
  <div id="tabs-2">
    <div id="hisCouponlist" class="coupon-content">
  
    </div>
  </div>
</div>
</div>

<script>
$(document).ready(function ()
{
	$( "#tabs" ).tabs();
	
	$('ul.coupon-nav li').click(function ()
	{
		$(this).addClass('on').siblings().removeClass('on');		
	});
	$(".coupon-ing").toggle( 
	  function () { 
	  $(this).addClass("active"); 
	  }, 
	  function () {
	   $(this).removeClass("active"); 
	  });
});
</script>
</body>
</html>
