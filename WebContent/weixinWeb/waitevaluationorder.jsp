<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>待评价</title>
<link href="css/reset.css" rel="stylesheet" type="text/css" />
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="css/font-awesome.css" rel="stylesheet" />
<link href="css/style.css" rel="stylesheet" type="text/css" />
<script src="js/jquery-1.8.3.min.js"></script> 
<script src="js/jquery-ui-1.10.3.min.js"></script> 
<script type="text/javascript">
var orderlist;
var pagenum=0;
var hasmore=0;
var studentid='${sessionScope.studentid}';
studentid='18';
var action1="GetUnCompleteOrder";//未完成订单
var action2="GetWaitEvaluationOrder";//待评价订单
var action3="GetCompleteOrder";//已评价订单
var action4="GETCOMPLAINTORDER";//待处理订单

$(function(){
	var pagenum=0;
	getOrderlist(action2,pagenum);
	var winH = $(window).height(); //页面可视区域高度 
    //var i = 1; //设置当前页数 
    $(window).scroll(function () { 
        var pageH = $(document.body).height(); 
        var scrollT = $(window).scrollTop(); //滚动条top 
        var aa = (pageH-winH-scrollT)/winH; 
        if(aa<0.02){  
        	pagenum++;
            if(hasmore==1)
            {
            	getOrderlist(action2,pagenum);
            } 
        } 
    });  
});

function getOrderlist(at,pagenum){
	$.ajax({		   
		type : "POST",
		url : "../sorder",
		dataType: "json",
		data : {
			action : at,
			studentid : studentid,
			pagenum   : pagenum
		},
		success : function(data) {
			orderlist=data.orderlist;
			hasmore=data.hasmore;
			var content_list="";
            for(var i=0;i<orderlist.length;i++)
            {
            	var start_time=orderlist[i].start_time.substring(0,10);
            	var starthour=orderlist[i].start_time.substring(11,16);
            	var end_time=orderlist[i].end_time.substring(0,10);
            	var endhour=orderlist[i].end_time.substring(11,16);
        		var hours=orderlist[i].hours;
            	if(hours==0){
            		hours="此车单即将开始";
            	}else if(hours==-1){
            		hours="正在学车";
				}else if(hours==-2){
            		hours="学车完成";
				}else if(hours==-3){
            		hours="正在学车";
				}else if(hours==-4){
            		hours="等待确认下车";
				}else if(hours==-5){
            		hours="投诉处理中";
				}else if(hours==-6){
            		hours="客服协商中";
				}else if(hours>=1440){
        			hours="距学车还有"+( Math.floor(hours/1440))+"天";
        		}else if(hours<1440 && hours>=60){
        			hours="距学车还有"+( Math.floor(hours/60))+"小时";
        		}else if(hours<60 && hours>0){
        			hours="距学车还有"+hours+"分钟";
        		}
            	/* content_list=content_list+"<div class=\"order-detai\"><div class=\"order-detail-head\"><div class=\"row\"><div class=\"col-md-6 col-sm-6 col-xs-6\"><p class=\"text-left\">"
            	+starthour+"-"+endhour+"</p></div><div class=\"col-md-6 col-sm-6 col-xs-6\"><p class=\"text-right learning\">正在学车</p></div><div class=\"col-md-12 col-sm-12 col-xs-12\"><hr/></div></div></div><div class=\"order-detail-body\"> <div class=\"row\"><div class=\"col-md-12 col-sm-12 col-xs-12\"><a href=\"ordercarlearning.jsp\"><ul class=\"order-items\"><li><span>科目：</span><span>"+orderlist[i].subjectname+"</span></li><li><span>教练：</span><span>"+orderlist[i].cuserinfo.realname+"</span></li><li><span>地址：</span><span>"+orderlist[i].detail+"</span></li></ul></a><hr/><p class=\"text-right compute\">合计：<span>￥"+orderlist[i].total+"</span></p><p><a href=\"complaincoach.jsp\"><span class=\"span-btn complain-btn\">投诉</span></a><span class=\"span-btn sure-btn\">确认上车</span></p></div></div></div></div>"; */
            	content_list=content_list+'<div class="order-detail"><div class="order-detail-head"><div class="row"><div class="col-md-6 col-sm-6 col-xs-6">';
            	content_list=content_list+'<p class="text-left">'+starthour+"-"+endhour+'</p></div><div class="col-md-6 col-sm-6 col-xs-6">';
            	content_list=content_list+'<p class="text-right learning">'+hours+'</p></div><div class="col-md-12 col-sm-12 col-xs-12"><hr/></div></div></div>';
            	content_list=content_list+'<div class="order-detail-body"><div class="row"><div class="col-md-12 col-sm-12 col-xs-12">';
            	content_list=content_list+'<a href="ordercarlearning.jsp"><ul class="order-items"><li><span>科目：</span><span>';
            	content_list=content_list+orderlist[i].subjectname;
            	content_list=content_list+'</span></li><li><span>教练：</span><span>'+orderlist[i].cuserinfo.realname;
            	content_list=content_list+'</span></li><li><span>地址：</span><span>'+orderlist[i].detail;
            	content_list=content_list+'</span></li></ul></a><hr/><p class="text-right compute">合计：<span>';
            	content_list=content_list+orderlist[i].total+'</span></p><p>';
            	
            	
            	
            	if(orderlist[i].can_complaint==1){
            		content_list=content_list+'<a href="complaincoach.jsp?orderid='+orderlist[i].orderid+'"><span class="span-btn complain-btn">投诉</span></a>';
            	}
            	if(at=='GetUnCompleteOrder'){
	            	if(orderlist[i].studentstate==4){
	            		content_list=content_list+'<span class="span-btn sure-btn">已提交取消订单申请,等待教练确认中!</span>';
	            	}else if(orderlist[i].can_cancel==1){
	            		content_list=content_list+'<span class="span-btn complain-btn" onclick="cancelOrder('+orderlist[i].orderid+')">取消订单</span>';
	            	}
            	}
            	if(orderlist[i].can_down==1){
            		content_list=content_list+'<a href="complaincoach.jsp?orderid='+orderlist[i].orderid+'"><span class="span-btn complain-btn">投诉</span></a>';
            		content_list=content_list+'<span class="span-btn sure-btn">确认下车</span>';
            	}
            	if(at=='GetWaitEvaluationOrder'){
            		content_list=content_list+'<a href="evaluatecoach.jsp?orderid='+orderlist[i].orderid+'"><span class="span-btn sure-btn">立即评价</span></a>';
                }
            	if(at=='GetCompleteOrder'){
            		content_list=content_list+'<a href="coursearrange.jsp?coachid='+orderlist[i].coachid+'"><span class="span-btn sure-btn">继续预约</span></a>';
                }
            	content_list=content_list+'</p></div></div></div></div>';
            }
            if(at=='GetUnCompleteOrder'){
            	 $("ul#uncomplete li").html(content_list);
            }else if(at=='GetWaitEvaluationOrder'){
            	$("ul#waitevaluation li").html(content_list);
            }else if(at=='GetCompleteOrder'){
            	$("ul#completeOrder li").html(content_list);
            }else if(at=='GETCOMPLAINTORDER'){
            	$("ul#complaint li").html(content_list);
            }
		}
   });
}
</script>
<script type="text/javascript">
	//取消订单
	function cancelOrder(orderid){
		/* $('.overlay-cancle').css('display','block');
		var heightC = $('.overlay-cancle-content').height();
		var heightW = $(window).height();
		var w = (heightW-heightC)/2;
		$('.overlay-cancle-content').css('top',w); */
		if(confirm("确认取消此订单")){
			var studentid='${sessionScope.studentid}';//学员Id
			studentid='18';
			var token='${sessionScope.token}';
			var params = {action:"cancelOrder",studentid:studentid,orderid:orderid,token:token};
			jQuery.post("../sorder", params, showCancelOrder, 'json');
		}
	}
	function showCancelOrder(obj){
		if(obj.code==1){//取消成功
			getOrderlist(action1);
		}
	}
</script>
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
            <li >
            <a href="uncompleorder.jsp">未完成</a></li><li  class="active"><a href="waitevaluationorder.jsp">待评价</a></li><li><a href="completeorder.jsp" >已评价</a></li><li><a href="complaintorder.jsp" >待处理</a></li></ul>
          
          <div id="order_tabs-2"> 
          	<!--******************************************-->
            <div class="row order-content container">
              <ul class="order-timeline"  id="waitevaluation">
                <li>
                  <!-- <div class="point"></div>
                  <div class="time-wrap"> 明天 </div> -->
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
<!--点击取消订单按钮的提示弹框 starts-->
<div class="overlay-cancle">
  <div class="overlay-cancle-content">
    <div class="container">
      <div class="row">
      	<span class="pull-right"><i class="icon icon-remove"></i></span>
      </div>
      <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12"> <span>教练确认以后才能取消成功</span> </div>
        <div class="col-md-12 col-sm-12 col-xs-12"><span>请教练确认</span></div>
      </div>
    </div>
  </div>
</div>
<!--点击取消订单按钮的提示弹框 starts-->
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
	//$('#order_tabs').tabs();
//	$('ul.foot-nav li').click(function ()
//	{
//		$(this).addClass('active').siblings().removeClass('active');		
//	});
	$('ul.order-nav li').click(function ()
	{
		$(this).addClass('active').siblings().removeClass('active');		
	});
	//取消订单的提示弹框
	$(".un-sure-btn").click(function ()
	{
		$('.overlay-cancle').css('display','block');
		var heightC = $('.overlay-cancle-content').height();
		var heightW = $(window).height();
		var w = (heightW-heightC)/2;
		$('.overlay-cancle-content').css('top',w);
	});
	$('.icon-remove').click(function ()
	{
		$('.overlay-cancle').css('display','none');
	});
	$('.overlay-cancle .overlay-cancle-content .col-md-12:last-child span').click(function ()
	{
		$('.overlay-cancle').css('display','none');
	});

});

</script>
</body>
</html>
