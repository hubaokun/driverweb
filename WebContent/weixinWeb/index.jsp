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
      <li class="active"><a href="#tabs-1"><span class="coach"></span><p>找教练</p></a></li><li><a href="#tabs-2"><span class="order"></span><p>订单</p></a></li><li><a href="#tabs-3"><span class="my"></span><p>我的</p></a></li>
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
    <div id="tabs-2">
      <div class="row order-nav-wrap">
        <div id="order_tabs">
          <ul class="order-nav">
            <li class="active"><a href="#order_tabs-1">未完成</a></li><li><a href="#order_tabs-2">待评价</a></li><li><a href="#order_tabs-3">已完成</a></li>
          </ul>
          <div id="order_tabs-1"> 
            <!--******************************************-->
            <div class="row order-content container">
              <ul class="order-timeline">
                <li>
                  <div class="point"></div>
                  <div class="time-wrap">今天 </div>
                  <!--single order detail starts-->                 
                  <div class="order-detail">
                    <div class="order-detail-head">
                      <div class="row">
                        <div class="col-md-6 col-sm-6 col-xs-6">
                          <p class="text-left">16:00-17:00</p>
                        </div>
                        <div class="col-md-6 col-sm-6 col-xs-6">
                          <p class="text-right">学车未开始</p>
                        </div>
                      </div>
                      <hr/>
                    </div>
                    <div class="order-detail-body"> 
						<div class="row">
                        	<div class="col-md-12 col-sm-12 col-xs-12">
                            	<a href="ordercarlearnbefore.jsp">
                            	<ul class="order-items">
                                  <li><span>科目：</span><span>科目二</span></li>
                                  <li><span>教练：</span><span>张伟华</span></li>
                                  <li><span>地址：</span><span>杭州文艺西路222号</span></li>
                                </ul>
                                </a>
                                <hr/>
                                <p class="text-right compute">合计：<span>￥180.00</span></p>
                                <span class="span-btn sure-btn">确认上车</span> 
                            </div>
                        </div>
                    </div>
                  </div>                  
                  <!--single order ends-->    
                  <!--single order detail starts-->                  
                  <div class="order-detail">
                    <div class="order-detail-head">
                      <div class="row">
                        <div class="col-md-6 col-sm-6 col-xs-6">
                          <p class="text-left">16:00-17:00</p>
                        </div>
                        <div class="col-md-6 col-sm-6 col-xs-6">
                          <p class="text-right">正在学车</p>
                        </div>
                      </div>
                      <hr/>
                    </div>
                    <div class="order-detail-body"> 
						<div class="row">
                        	<div class="col-md-12 col-sm-12 col-xs-12">
                            	<a href="ordercarlearning.jsp">
                            	<ul class="order-items">
                                  <li><span>科目：</span><span>科目二</span></li>
                                  <li><span>教练：</span><span>张伟华</span></li>
                                  <li><span>地址：</span><span>杭州文艺西路222号</span></li>
                                </ul>
                                </a>
                                <hr/>
                                <p class="text-right compute">合计：<span>￥180.00</span></p>
                                <span class="evaluate-btn span-btn sure-btn">确认下车</span> 
                            </div>
                        </div>
                    </div>
                  </div>
                  
                  <!--single order ends-->
                  
                  <div class="order-detail">
                    <div class="order-detail-head">
                      <div class="row">
                        <div class="col-md-6 col-sm-6 col-xs-6">
                          <p class="text-left">16:00-17:00</p>
                        </div>
                        <div class="col-md-6 col-sm-6 col-xs-6">
                          <p class="text-right">学车未开始</p>
                        </div>
                      </div>
                      <hr/>
                    </div>
                    <div class="order-detail-body"> 
						<div class="row">
                        	<div class="col-md-12 col-sm-12 col-xs-12">
                            <a href="ordercarlearnbefore.jsp">
                            	<ul class="order-items">
                                  <li><span>科目：</span><span>科目二</span></li>
                                  <li><span>教练：</span><span>张伟华</span></li>
                                  <li><span>地址：</span><span>杭州文艺西路222号</span></li>
                                </ul>
                                </a>
                                <hr/>
                                <p class="text-right compute">合计：<span>￥180.00</span></p>
                                <span class="span-btn un-sure-btn">离学车还有<span class="left-time">01:00:00</span></span> 
                            </div>
                        </div>
                    </div>
                  </div>
                  <div style="width:100px; height:50px; display:block;"></div>
                </li>
              </ul>
            </div>
            <!--******************************************--> 
          </div>
          <div id="order_tabs-2"> 
          	<!--******************************************-->
            <div class="row order-content container">
              <ul class="order-timeline">
                <li>
                  <div class="point"></div>
                  <div class="time-wrap"> 明天 </div>
                  <!--single order detail starts-->
                  <div class="order-detail">
                    <div class="order-detail-head">
                      <div class="row">
                        <div class="col-md-6 col-sm-6 col-xs-6">
                          <p class="text-left">16:00-17:00</p>
                        </div>
                        <div class="col-md-6 col-sm-6 col-xs-6">
                          <p class="text-right">学车结束</p>
                        </div>
                      </div>
                      <hr/>
                    </div>
                    <div class="order-detail-body"> 
						<div class="row">
                        	<div class="col-md-12 col-sm-12 col-xs-12">
                            	<a href="ordercarlearned.jsp">
                            	<ul class="order-items">
                                  <li><span>科目：</span><span>科目二</span></li>
                                  <li><span>教练：</span><span>张伟华</span></li>
                                  <li><span>地址：</span><span>杭州文艺西路222号</span></li>
                                </ul>
                                </a>
                                <hr/>
                                <p class="text-right compute">合计：<span>￥180.00</span></p>
                                <a href="javascript:;" class="evaluate-btn span-btn judge-btn pull-right"><i class="icon  icon-edit"></i>评价</a> 
                            </div>
                        </div>
                    </div>
                  </div>
                  <!--single order ends-->
                  <div class="order-detail">
                    <div class="order-detail-head">
                      <div class="row">
                        <div class="col-md-6 col-sm-6 col-xs-6">
                          <p class="text-left">16:00-17:00</p>
                        </div>
                        <div class="col-md-6 col-sm-6 col-xs-6">
                          <p class="text-right">学车结束</p>
                        </div>
                      </div>
                      <hr/>
                    </div>
                    <div class="order-detail-body"> 
						<div class="row">
                        	<div class="col-md-12 col-sm-12 col-xs-12">
                            <a href="ordercarlearned.jsp">
                            	<ul class="order-items">
                                  <li><span>科目：</span><span>科目二</span></li>
                                  <li><span>教练：</span><span>张伟华</span></li>
                                  <li><span>地址：</span><span>杭州文艺西路222号</span></li>
                                </ul>
                                </a>
                                <hr/>
                                <p class="text-right compute">合计：<span>￥180.00</span></p>
                                <a href="javascript:;" class="evaluate-btn span-btn judge-btn pull-right"><i class="icon  icon-edit"></i>评价</a> 
                            </div>
                        </div>
                    </div>
                  </div>
                  <div style="width:100px; height:50px; display:block;"></div>
                </li>
              </ul>
            </div>
            <!--******************************************--> 
          
          </div>
          <div id="order_tabs-3">
          	<!--******************************************-->
            <div class="row order-content container">
              <ul class="order-timeline">
                <li>
                  <div class="point"></div>
                  <div class="time-wrap"> 昨天 </div>
                  <!--single order detail starts-->
                  <div class="order-detail">
                    <div class="order-detail-head">
                      <div class="row">
                        <div class="col-md-6 col-sm-6 col-xs-6">
                          <p class="text-left">16:00-17:00</p>
                        </div>
                        <div class="col-md-6 col-sm-6 col-xs-6">
                          <p class="text-right">完成学车</p>
                        </div>
                      </div>
                      <hr/>
                    </div>
                    <div class="order-detail-body"> 
						<div class="row">
                        	<div class="col-md-12 col-sm-12 col-xs-12">
                            	<ul class="order-items">
                                  <li><span>科目：</span><span>科目二</span></li>
                                  <li><span>教练：</span><span>张伟华</span></li>
                                  <li><span>地址：</span><span>杭州文艺西路222号</span></li>
                                </ul>
                                <hr/>
                                <p class="text-right compute">合计：<span>￥180.00</span></p>
                            </div>
                        </div>
                    </div>
                  </div>
                  <!--single order ends-->
                  <div class="order-detail">
                    <div class="order-detail-head">
                      <div class="row">
                        <div class="col-md-6 col-sm-6 col-xs-6">
                          <p class="text-left">16:00-17:00</p>
                        </div>
                        <div class="col-md-6 col-sm-6 col-xs-6">
                          <p class="text-right">完成学车</p>
                        </div>
                      </div>
                      <hr/>
                    </div>
                    <div class="order-detail-body"> 
						<div class="row">
                        	<div class="col-md-12 col-sm-12 col-xs-12">
                            	<ul class="order-items">
                                  <li><span>科目：</span><span>科目二</span></li>
                                  <li><span>教练：</span><span>张伟华</span></li>
                                  <li><span>地址：</span><span>杭州文艺西路222号</span></li>
                                </ul>
                                <hr/>
                                <p class="text-right compute">合计：<span>￥180.00</span></p>
                            </div>
                        </div>
                    </div>
                  </div>
                  <div style="width:100px; height:50px; display:block;"></div>
                </li>
              </ul>
            </div>
            <!--******************************************--> 
           </div>
        </div>
      </div>
    </div>
    <div id="tabs-3">
    	<div class="row my-head">
        	<div class="col-md-3 col-sm-3 col-xs-3">
            	<div class="head-avatar center-block">
                		<img src="images/person-one.png" class="img-responsive img-circle center-block" />
                </div>
            </div>
            <div class="col-md-7 col-sm-7 col-xs-7">
            	<p>童卫军</p>
                <p>159888865324</p>
            </div>
            <div class="col-md-2 col-sm-2 col-xs-2">
            	<i class="icon icon-chevron-right my-head-icon"></i>
            </div>
        </div>
        <div class="row">
			 <div class="col-md-12 col-sm-12 col-xs-12 my-nav-item">
                <a href="accountdetail.jsp">
                    <i class="my-nav-icon icon-money-rest"></i>				
                    <span>账户余额</span>		
                    <i class="icon icon-chevron-right"></i>
                </a>
    		</div>
            <div class="col-md-12 col-sm-12 col-xs-12 my-nav-item">
                <a href="coupon.jsp">
                    <i class="my-nav-icon icon-coupon"></i>				
                    <span>小巴券</span>		
                    <i class="icon icon-chevron-right"></i>
                </a>
    		</div>
            <div class="col-md-12 col-sm-12 col-xs-12 my-nav-item">
                <a href="coin.jsp">
                    <i class="my-nav-icon icon-coin"></i>				
                    <span>小巴币</span>		
                    <i class="icon icon-chevron-right"></i>
                </a>
    		</div>     
        </div>
        <div class="row drive-row">
        	<div class="col-md-12 col-sm-12 col-xs-12 my-nav-item">
              <a href="mylearninfo.jsp">
                  <i class="my-nav-icon icon-infor"></i>				
                      <span>学驾信息</span>		
                  <i class="icon icon-chevron-right"></i>
              </a>
    		</div>
        </div>
        <div class="row logout-row">
        	<div class="col-md-12 col-sm-12 col-xs-12 my-nav-item">
              <span>退出登录</span>
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
      <div style="width:100px; height:50px; display:block;"></div>
    </div>
  </div>
</div>
<!--弹出框 ends--> 
 
<!--评价弹出框 starts-->
<div class="overlay-e">
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
    <div style="width:100px; height:50px; display:block;"></div>
 
    </div>
</div>

<!--评价弹出框 ends-->

<script src="js/jquery-1.8.3.min.js"></script> 
<script src="js/jquery-ui-1.10.3.min.js"></script> 
<script src="js/jquery.raty.min.js"></script>
<script>
$(document).ready(function()
{
	$( "#tabs" ).tabs();
	$('#order_tabs').tabs();
	
	$('ul.foot-nav li').click(function ()
	{
		$(this).addClass('active').siblings().removeClass('active');		
	});
	$('ul.order-nav li').click(function ()
	{
		$(this).addClass('active').siblings().removeClass('active');		
	});
	
	//pop up
	$(".detail-wrap").on('click',function ()
	{
		$(".overlay").slideToggle('slow');
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
	
	
	//控制评价弹框的内容层位置
	var width = $(document).width();
	var height = $(document).height();
	//alert (width);
	//alert (height);
	
	var ww = (width-290)/2;
	var hh = (height-343)/2;
	$('.overlay-content-e').css('top',hh);
	$('.overlay-content-e').css('left',ww);
	$('.evaluate-btn').on('click',function(){
		$('.overlay-e').css('display','block');
		$(document).scrollTop(0);
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
