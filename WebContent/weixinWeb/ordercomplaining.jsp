<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="checksession.jsp" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>投诉中</title>
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="css/font-awesome.css" rel="stylesheet" />
<link href="css/orderdetail.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.carlearn-list
{
	margin-top:12px;
}
</style>
</head>

<body>
  <div class="container">
      <div class="row carlearn-head carlearn-head-grey">
          <span style="color:#f71c1c">投诉中</span>
      </div>
      <div class="row carlearn-detail">
		<div class="col-md-12 col-sm-12 col-xs-12">
          <div class="carlearn-detail-inner carlearn-detail-inner-grey">
            <p><span>科目：</span><span>科目二</span></p>
            <p><span>时间：</span><span>2015-12-12 9:00-10:00</span></p>
            <p><span>价格：</span><span>￥90/小时</span></p>
            <p><span>教练：</span><span>刘华</span></p>
            <p><span>电话：</span><span>13738921290</span></p>
            <p><span>地址：</span><span>文一西路998号19号楼405</span></p>
            <p><span>优惠：</span><span>小巴券（-￥90）</span></p>
          </div>
        </div>
        <div class="col-md-12 col-sm-12 col-xs-12">
        	<div class="total-wrap total-wrap-grey"></div>
        </div>
        <div class="col-md-12 col-sm-12 col-xs-12">
        	<p class="grey"><span>合计：</span><span>￥180</span></p>
        </div>
      </div>
<!--      <div class="row carlearn-sure-btn">
      	<div class="col-md-12 col-sm-12 col-xs-12">
        	<span class="evaluate-btn blue"  style="display:block;width:100%;"><i class="icon  icon-edit"></i>评价</span>
        </div>
      </div>  -->
      <div class="row carlearn-list">
      	<div class="col-md-12 col-sm-12 col-xs-12">
        	<div class="carlearn-list-inner">
              <p>订单标号：123456789</p>
              <p>付款时间：2015-09-09 15:30:03</p>
              <p>付款方式：支付宝</p>
            </div>
        </div>
      </div>    
      
      
  </div>
  
  
  
<!--评价弹出框 starts-->
<!--<div class="overlay-e">
	<div class="overlay-e-inner">
		<div class="container">
    	<div class="overlay-content-e">
		<div class="overlay-content-head">
        	<div class="row">
            	<div class="col-md-12">
                	<p>评价教练</p>
            		<hr/>
                </div>
			</div>
        </div>
        <form>
          <div class="overlay-content-body">
          	<div class="row">
            	<div class="col-md-12 col-sm-12 col-xs-12">
                	<p>
                    <span>教学态度</span>
                    <div id="score-attitude"></div>
                    <span class="pull-right e-mark-a">0.0</span>
                    </p>
                </div>
                <div class="col-md-12 col-sm-12 col-xs-12">
                	<p>
                    <span>教学质量</span>
                    <div id="score-quality"></div>
                    <span class="pull-right e-mark-q">0.0</span>
                    </p>
                </div>
                <div class="col-md-12 col-sm-12 col-xs-12">
                	<p>
                    <span>技能掌握</span>
                    <div id="score-control"></div>
                    <span class="pull-right e-mark-c">0.0</span>
                    </p>
                </div>
            	<div class="col-md-12 col-sm-12 col-xs-12">
                	<textarea placeholder="对他说点鼓励的话吧"></textarea>
                </div>
            </div>
          	
          </div>
          <div class="overlay-content-foot">
          	<div class="row">
            	<div class="col-md-6 col-sm-6 col-xs-6"><span class="grey skip">跳过</span></div>
                <div class="col-md-6 col-sm-6 col-xs-6"><span class="blue sub">提交</span></div>
            </div>
          </div>	
        </form>
    </div>
    <!--<div style="width:100px; height:50px; display:block;"></div>-->
<!--    </div>
    </div>
</div>-->

<!--评价弹出框 ends-->
<script src="js/jquery-1.8.3.min.js"></script> 
<script src="js/jquery.raty.min.js"></script>
<script>
$(document).ready(function()
{
	//$( "#tabs" ).tabs();
	//$('#order_tabs').tabs();
	
//	$('ul.foot-nav li').click(function ()
//	{
//		$(this).addClass('active').siblings().removeClass('active');		
//	});
//	$('ul.order-nav li').click(function ()
//	{
//		$(this).addClass('active').siblings().removeClass('active');		
//	});
    
	//控制评价弹框的内容层位置
	var width = $(document).width();
	var height = $(document).height();
	
	var height2 = $(window).height();
	//alert (height2);
	//alert (width);
	//alert (height);
	$('.overlay-e').height(height);
	
	var ww = (width-290)/2;
	var hh = (height2-343)/2;
	$('.overlay-content-e').css('bottom',hh);
	$('.overlay-content-e').css('left',ww);
	$('.evaluate-btn').on('click',function()
	{
		$('.overlay-e,.overlay-e-inner').css('display','block');
		//$(document).scrollTop(0);
	})
	$('.skip').on('click',function()
	{
		$('.overlay-e').css('display','none');
	});
	$('.sub').on('click',function()
	{
		$('.overlay-e').css('display','none');
	});
	
	$.fn.raty.defaults.path = 'js/img';
	$('#score-attitude').raty({
        score: function() {
          return $(this).attr('data-score');
        },
		click: function()
		{	
			var v = $('#score-attitude input[name="score"]').val();
			$('.e-mark-a').empty().html(v+".0");
		},
		size:24
      });
	  $('#score-quality').raty({
        score: function() {
          return $(this).attr('data-score');
        },
		click: function()
		{	
			var v = $('#score-quality input[name="score"]').val();
			$('.e-mark-q').empty().html(v+".0");
		},
		size:24
      });
	  $('#score-control').raty({
        score: function() {
          return $(this).attr('data-score');
        },
		click: function()
		{	
			var v = $('#score-control input[name="score"]').val();
			$('.e-mark-c').empty().html(v+".0");
		},
		size:24
      });
});
</script>
</body>
</html>
