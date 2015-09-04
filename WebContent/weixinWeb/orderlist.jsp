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
      <li><a href="coachlist.jsp"><span class="coach"></span><p>找教练</p></a></li><li class="active"><a href="javascript:void(0);"><span class="order"></span><p>订单</p></a></li><li><a href="my.jsp"><span class="my"></span><p>我的</p></a></li>
    </ul>
    <div id="tabs-2">
      <div class="row order-nav-wrap">
        <div id="order_tabs">
          <ul class="order-nav">
            <li class="active"><a href="#order_tabs-1">未完成</a></li><li><a href="#order_tabs-2">待评价</a></li><li><a href="#order_tabs-3">已评价</a></li><li><a href="#order_tabs-4">待处理</a></li>
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
                          <p class="text-right learning">正在学车</p>
                        </div>
                        <div class="col-md-12 col-sm-12 col-xs-12">
                        	<hr/>
                        </div>
                      </div>
                      
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
                                <p>
                               	  <a href="complaincoach.jsp"><span class="span-btn complain-btn">投诉</span></a>
                                  <span class="span-btn sure-btn">确认上车</span>
                                </p> 
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
                          <p class="text-right learning">正在学车</p>
                        </div>
                        <div class="col-md-12 col-sm-12 col-xs-12">
                        	<hr/>
                        </div>
                      </div>
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
                                <p>
                               	  <a href="complaincoach.jsp"><span class="span-btn complain-btn">投诉</span></a>
                                  <span class="span-btn sure-btn">确认上车</span>
                                </p> 
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
                          <p class="text-right beforelearn">学车未开始</p>
                        </div>
                        <div class="col-md-12 col-sm-12 col-xs-12">
                        	<hr/>
                        </div>
                      </div>
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
                                <span class="span-btn un-sure-btn">取消订单</span></span> 
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
                        <div class="col-md-12 col-sm-12 col-xs-12">
                        	<hr/>
                        </div>
                      </div>
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
                                <a href="evaluatecoach.jsp" class="evaluate-btn span-btn judge-btn pull-right"><i class="icon  icon-edit"></i>评价</a>
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
                        <div class="col-md-12 col-sm-12 col-xs-12">
                        	<hr/>
                        </div>
                      </div>
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
                                <a href="evaluatecoach.jsp" class="evaluate-btn span-btn judge-btn pull-right"><i class="icon  icon-edit"></i>评价</a>
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
                        <div class="col-md-12 col-sm-12 col-xs-12">
                        	<hr/>
                        </div>
                      </div>
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
                        <div class="col-md-12 col-sm-12 col-xs-12">
                        	<hr/>
                        </div>
                      </div>
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
           <div id="order_tabs-4">
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
                                           <p class="text-right learning">投诉中</p>
                                       </div>
                                       <div class="col-md-12 col-sm-12 col-xs-12">
                                           <hr/>
                                       </div>
                                   </div>

                               </div>
                               <div class="order-detail-body">
                                   <div class="row">
                                       <div class="col-md-12 col-sm-12 col-xs-12">
                                           <a href="ordercomplaining.jsp">
                                               <ul class="order-items">
                                                   <li><span>科目：</span><span>科目二</span></li>
                                                   <li><span>教练：</span><span>张伟华</span></li>
                                                   <li><span>地址：</span><span>杭州文艺西路222号</span></li>
                                               </ul>
                                           </a>
                                           <hr/>
                                           <p class="text-right compute">合计：<span>￥180.00</span></p>
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
                                           <p class="text-right learning">客服协调</p>
                                       </div>
                                       <div class="col-md-12 col-sm-12 col-xs-12">
                                           <hr/>
                                       </div>
                                   </div>
                               </div>
                               <div class="order-detail-body">
                                   <div class="row">
                                       <div class="col-md-12 col-sm-12 col-xs-12">
                                           <a href="orderassorting.jsp">
                                               <ul class="order-items">
                                                   <li><span>科目：</span><span>科目二</span></li>
                                                   <li><span>教练：</span><span>张伟华</span></li>
                                                   <li><span>地址：</span><span>杭州文艺西路222号</span></li>
                                               </ul>
                                           </a>
                                           <hr/>
                                           <p class="text-right compute">合计：<span>￥180.00</span></p>
                                       </div>
                                   </div>
                               </div>
                           </div>

                           <!--single order ends-->
                           <div style="width:100px; height:50px; display:block;"></div>
                       </li>
                   </ul>
               </div>
           </div>
        </div>
      </div>
    </div>
  </div>
</div>


<!--提示框：未点击确认上车 starts-->
<div class="overlay-tips">
	<div class="overlay-tips-inner">
    	<div class="container">
        	<div class="row">
            	<div class="col-md-12 col-sm-12 col-xs-12 content-tips">
                	<span><i class="icon icon-exclamation-sign"></i>16:00-17:00课程未确认下车</span>
                </div>
            </div>
        </div>
    </div>
</div>
<!--提示框：未点击确认上车 ends-->
<script src="js/jquery-1.8.3.min.js"></script> 
<script src="js/jquery-ui-1.10.3.min.js"></script> 
<script src="js/jquery.raty.min.js"></script>
<script>
	function timeTips()
	{
		setTimeout("hideTips()",4000) // 这个是主要参数，设置消失的时间
		
	}
	function hideTips()
	{
		$('.overlay-tips,.overlay-tips-inner').css('display','none');
		
	}
</script>

<script>
$(document).ready(function()
{
	//$( "#tabs" ).tabs();
	$('#order_tabs').tabs();
	
//	$('ul.foot-nav li').click(function ()
//	{
//		$(this).addClass('active').siblings().removeClass('active');		
//	});
	$('ul.order-nav li').click(function ()
	{
		$(this).addClass('active').siblings().removeClass('active');		
	});

});
</script>
</body>
</html>
