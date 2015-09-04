<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>小巴学车</title>
<link href="css/reset.css" rel="stylesheet" type="text/css" />
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="css/font-awesome.css" rel="stylesheet" />
<link href="css/style.css" rel="stylesheet" type="text/css" />
<style type="text/css">
</style>
</head>

<body>
<div class="container" >
  <div id="tabs" >
    <ul class="foot-nav" data-role="footer">
      <li class="active"><a href="javascript:void(0);"><span class="coach"></span><p>找教练</p></a></li><li><a href="orderlist.jsp"><span class="order"></span><p>订单</p></a></li><li><a href="my.jsp"><span class="my"></span><p>我的</p></a></li>
    </ul>
    <div id="tabs-1">
      <div class="row search-wrap">
        <form>
          <div class="col-md-3 col-sm-3 col-xs-3"> <span><a href="citychoose.jsp">杭州</a><i class="glyphicon icon-citydown"></i></span> </div>
          <div class="col-md-9 col-sm-9 col-xs-9">
            <div class="input-group">
              <input type="text" id="search_coach" class="form-control" placeholder="" aria-describedby="basic-addon2">
              <span class="input-group-addon" id="basic-addon2"><i class="glyphicon glyphicon-search"></i></span> </div>
          </div>
        </form>
      </div>
      <div class="row content-wrap"> 
        <!--一行的数据显示 starts-->
        <div class="col-md-12 col-sm-12 col-xs-12">
          <div class="row detail-wrap">
            <div class="col-md-3 col-sm-3 col-xs-3"> <img src="images/person-one.png" class="img-responsive img-circle " /> </div>
            <div class="col-md-9 col-sm-9 col-xs-9">
              <p>童卫军<span class="pull-right"><i class="glyphicon glyphicon-star"></i><i class="glyphicon glyphicon-star"></i><i class="glyphicon glyphicon-star"></i><i class="glyphicon glyphicon-star"></i><i class="glyphicon glyphicon-star"></i></span></p>
              <p>浙江省杭州市余杭区海曙路58号</p>
            </div>
          </div>
          <hr/>
        </div>
        <!--一行的数据显示 ends-->
        <div class="col-md-12 col-sm-12 col-xs-12">
          <div class="row detail-wrap">
            <div class="col-md-3 col-sm-3 col-xs-3"> <img src="images/person-two.png" class="img-responsive img-circle " /> </div>
            <div class="col-md-9 col-sm-9 col-xs-9">
              <p>同维军<span class="pull-right"><i class="glyphicon glyphicon-star"></i><i class="glyphicon glyphicon-star"></i><i class="glyphicon glyphicon-star"></i><i class="glyphicon glyphicon-star"></i><i class="glyphicon glyphicon-star"></i></span></p>
              <p>浙江省杭州市余杭区海曙路58号</p>
            </div>
          </div>
          <hr/>
        </div>
        <div class="col-md-12 col-sm-12 col-xs-12">
          <div class="row detail-wrap">
            <div class="col-md-3 col-sm-3 col-xs-3"> <img src="images/person-three.png" class="img-responsive img-circle " /> </div>
            <div class="col-md-9 col-sm-9 col-xs-9">
              <p>王文<span class="pull-right"><i class="glyphicon glyphicon-star"></i><i class="glyphicon glyphicon-star"></i><i class="glyphicon glyphicon-star"></i><i class="glyphicon glyphicon-star"></i><i class="glyphicon glyphicon-star"></i></span></p>
              <p>浙江省杭州市余杭区海曙路58号</p>
            </div>
          </div>
          <hr/>
        </div>
      </div>
    </div>
  </div>
</div>

<!--弹出框 starts-->
<div class="overlay">
  <div class="overlay-content ">
    <div class="container">
      <div class="row content-wrap"> 
        <!--一行的数据显示 starts-->
        <div class="col-md-12 col-sm-12 col-xs-12">
          <div class="row detail-wrap">
            <div class="col-md-3 col-sm-3 col-xs-3"> <img src="images/person-one.png" class="img-responsive img-circle " /> </div>
            <div class="col-md-9 col-sm-9 col-xs-9">
              <p>童卫军<span class="pull-right"><i class="glyphicon glyphicon-star"></i><i class="glyphicon glyphicon-star"></i><i class="glyphicon glyphicon-star"></i><i class="glyphicon glyphicon-star"></i><i class="glyphicon glyphicon-star"></i></span></p>
              <p>浙江省杭州市余杭区海曙路58号</p>
            </div>
          </div>
          <hr/>
        </div>
      </div>
      <section class="overlay-button-group"> <a href="coursearrange.jsp">预约课程</a><a href="coachdetail.jsp">教练详情</a> </section>
      <!--<div style="width:100px; height:50px; display:block;"></div>-->
    </div>
  </div>
</div>
<!--弹出框 ends--> 
 


<script src="js/jquery-1.8.3.min.js"></script> 
<script src="js/jquery-ui-1.10.3.min.js"></script> 
<script src="js/jquery.raty.min.js"></script>
<script>
$(document).ready(function()
{
	//$( "#tabs" ).tabs();
	$('#order_tabs').tabs();
	
//	$('ul.foot-nav li').click(function ()
//	{
//		$(this).addClass('active').siblings().removeClass('active');		
//	});
//	$('ul.order-nav li').click(function ()
//	{
//		$(this).addClass('active').siblings().removeClass('active');		
//	});
	
	//pop up
	$(".detail-wrap").on('click',function ()
	{
		$(".overlay").css('display','block');
	});
	$(".overlay").on('click',function ()
	{
		$(".overlay").css('display','none');
	});
	
	//search input focus&blur
	$("#search_coach").on('focus',function ()
	{
		//$("#tabs-1 div.content-wrap").hide('slow');
//		$('.foot-nav').hide('slow');
		$("#tabs-1 div.content-wrap").css("display","none");
		$('.foot-nav').css("display","none");
	});
	
	$("#search_coach").on('blur',function ()
	{
//		$("#tabs-1 div.content-wrap").show('slow');
//		$('.foot-nav').show('slow');
		$("#tabs-1 div.content-wrap").css("display","block");
		$('.foot-nav').css("display","block");
	});

});
</script>
</body>
</html>
