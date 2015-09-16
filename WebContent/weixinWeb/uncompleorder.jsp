<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>未完成订单</title>
<link href="css/reset.css" rel="stylesheet" type="text/css" />
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="css/font-awesome.css" rel="stylesheet" />
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/scrollbar.css" rel="stylesheet" type="text/css" />
<link href="css/iscroll.css" rel="stylesheet" type="text/css" />
<link href="css/coachdetail.css" rel="stylesheet" type="text/css" />
<script src="js/iscroll.js"></script>
<script src="js/jquery-1.8.3.min.js"></script> 
<script src="js/jquery-ui-1.10.3.min.js"></script> 
<script src="js/jquery.raty.min.js"></script>
<script type="text/javascript">
var orderlist;
var pagenum=0;
var hasmore=0;
var studentid='${sessionScope.studentid}';
//studentid='18';
var action1="GetUnCompleteOrder";//未完成订单
var action2="GetWaitEvaluationOrder";//待评价订单
var action3="GetCompleteOrder";//已评价订单
var action4="GETCOMPLAINTORDER";//待处理订单
var token='${sessionScope.token}';
$(function(){
	var pagenum=0;
	getOrderlist(action1,pagenum);
});

function getOrderlist(at,pagenum){
	$.ajax({		   
		type : "POST",
		url : "../sorder",
		dataType: "json",
		data : {
			action : at,
			studentid : studentid,
			pagenum   : pagenum,
			token	  : token
		},
		success : function(data) {
			if(data.orderlist==undefined || data.orderlist.length==0){
				$('#noOrder').css('display','block');
				$('#wrapper').css('display','none');
				return;
			}
			orderlist=data.orderlist;
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
            	
            	content_list=content_list+'<li><div class="order-detail"><div class="order-detail-head"><div class="row"><div class="col-md-6 col-sm-6 col-xs-6">';
            	content_list=content_list+'<p class="text-left">'+starthour+"-"+endhour+'</p></div><div class="col-md-6 col-sm-6 col-xs-6">';
            	content_list=content_list+'<p class="text-right learning">'+hours+'</p></div><div class="col-md-12 col-sm-12 col-xs-12"><hr/></div></div></div>';
            	content_list=content_list+'<div class="order-detail-body"><div class="row"><div class="col-md-12 col-sm-12 col-xs-12">';
            	content_list=content_list+'<a href="uncompleorderdetail.jsp?orderid='+orderlist[i].orderid+'"><ul class="order-items"><li><span>科目：</span><span>';
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
            		content_list=content_list+'<a href="evaluatecoach.jsp?orderid='+orderlist[i].orderid+'"><span class="span-btn sure-btn">确认下车</span></a>';
            	}
            	if(at=='GetWaitEvaluationOrder'){
            		content_list=content_list+'<a href="evaluatecoach.jsp?orderid='+orderlist[i].orderid+'"><span class="span-btn sure-btn">立即评价</span></a>';
                }
            	if(at=='GetCompleteOrder'){
            		content_list=content_list+'<a href="coursearrange.jsp?coachid='+orderlist[i].coachid+'"><span class="span-btn sure-btn">继续预约</span></a>';
                }
            	content_list=content_list+'</p></div></div></div></div></li>';
            }
           
           $("#uncomplete").append(content_list);
		   hasmore=data.hasmore;
		   $("#pullUp").html("");
	   		if(data.orderlist.length<10){
	   			$("#pullUp").append("<span class='pullUpLabel'>没有更多了</span>");
	   		}else{
	   			$("#pullUp").append("<span class='pullUpIcon'></span><span class='pullUpLabel'>上拉加载更多...</span>");
	   		} 
	   		myScroll.refresh();	
		}
   });
}
</script>
<script type="text/javascript">
	var orderinfoId=0;
	//取消订单
	function cancelOrder(orderid){
		orderinfoId=orderid;//赋值订单号
		$('.overlay-cancle').css('display','block');
		var heightC = $('.overlay-cancle-content').height();
		var heightW = $(window).height();
		var w = (heightW-heightC)/2;
		$('.overlay-cancle-content').css('top',w);
	}
	function toCancelOrder(){
		$('.overlay-cancle').css('display','none');
		var studentid='${sessionScope.studentid}';//学员Id
		studentid='18';
		var token='${sessionScope.token}';
		var params = {
						action:"cancelOrder",
						studentid:studentid,
						orderid:orderinfoId,
						token:token
					 };
		jQuery.post("../sorder", params, showCancelOrder, 'json');
	}
	/* $('.overlay-cancle .overlay-cancle-content .col-md-12:last-child span').click(function ()
			{
	}); */
	function showCancelOrder(obj){
		alert(obj.message);
		if(obj.code==1){
			window.location.href="uncompleorder.jsp";
		}
	}
</script>
<script type="text/javascript">
var myScroll,
pullDownEl, pullDownOffset,
pullUpEl, pullUpOffset,
generatedCount = 0;
/**
 * 顶部向下拉动
 */
function pullDownAction () {
	setTimeout(function () {	// <-- Simulate network congestion, remove setTimeout from production!
		pagenum=0;
		$("#uncomplete").html("");
		getOrderlist(action1,pagenum);
		//myScroll.refresh();		//数据加载完成后，调用界面更新方法   Remember to refresh when contents are loaded (ie: on ajax completion)
	}, 1000);	// <-- Simulate network congestion, remove setTimeout from production!
	}
/**
* 顶部向上拉动，滚动翻页
* myScroll.refresh();		// 数据加载完成后，调用界面更新方法
*/
function pullUpAction () {
setTimeout(function () {	// <-- Simulate network congestion, remove setTimeout from production!
	if(hasmore==1)
    {
    	pagenum++;
    	getOrderlist(action1,pagenum);
    }else{
    	$("#pullUp").html("<span class='pullUpLabel'>真的没有更多了</span>");
    }
	//myScroll.refresh();		// 数据加载完成后，调用界面更新方法 Remember to refresh when contents are loaded (ie: on ajax completion)
}, 1000);	// <-- Simulate network congestion, remove setTimeout from production!
}

/**
* 初始化iScroll控件
*/
function loaded() {
pullDownEl = document.getElementById('pullDown');
pullDownOffset = pullDownEl.offsetHeight;
pullUpEl = document.getElementById('pullUp');	
pullUpOffset = pullUpEl.offsetHeight;

myScroll = new iScroll('wrapper', {
	scrollbarClass: 'myScrollbar', /* 重要样式 */
	useTransition: false, /* 此属性不知用意，本人从true改为false */
	topOffset: pullDownOffset,
	onRefresh: function () {
		if (pullDownEl.className.match('loading')) {
			pullDownEl.className = '';
			pullDownEl.querySelector('.pullDownLabel').innerHTML = '下拉刷新...';
		} else if (pullUpEl.className.match('loading')) {
			pullUpEl.className = '';
			pullUpEl.querySelector('.pullUpLabel').innerHTML = '上拉加载更多...';
		}
	},
	onScrollMove: function () {
		if (this.y > 5 && !pullDownEl.className.match('flip')) {
			pullDownEl.className = 'flip';
			pullDownEl.querySelector('.pullDownLabel').innerHTML = '松手开始更新...';
			this.minScrollY = 0;
		} else if (this.y < 5 && pullDownEl.className.match('flip')) {
			pullDownEl.className = '';
			pullDownEl.querySelector('.pullDownLabel').innerHTML = '下拉刷新...';
			this.minScrollY = -pullDownOffset;
		} else if (this.y < (this.maxScrollY - 5) && !pullUpEl.className.match('flip')) {
			pullUpEl.className = 'flip';
			pullUpEl.querySelector('.pullUpLabel').innerHTML = '松手开始更新...';
			this.maxScrollY = this.maxScrollY;
		} else if (this.y > (this.maxScrollY + 5) && pullUpEl.className.match('flip')) {
			pullUpEl.className = '';
			pullUpEl.querySelector('.pullUpLabel').innerHTML = '上拉加载更多...';
			this.maxScrollY = pullUpOffset;
		}
	},
	onScrollEnd: function () {
		if (pullDownEl.className.match('flip')) {
			pullDownEl.className = 'loading';
			pullDownEl.querySelector('.pullDownLabel').innerHTML = '加载中...';				
			pullDownAction();	// Execute custom function (ajax call?)
		} else if (pullUpEl.className.match('flip')) {
			pullUpEl.className = 'loading';
			pullUpEl.querySelector('.pullUpLabel').innerHTML = '加载中...';				
			pullUpAction();	// Execute custom function (ajax call?)
		}
	}
});
setTimeout(function () { document.getElementById('wrapper').style.left = '0'; }, 800);
}

//初始化绑定iScroll控件 
document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
document.addEventListener('DOMContentLoaded', loaded, false); 

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
            <li class="active">
            <a href="uncompleorder.jsp">未完成</a></li><li><a href="waitevaluationorder.jsp">待评价</a></li><li><a href="completeorder.jsp" >已评价</a></li><li><a href="complaintorder.jsp" >待处理</a></li></ul>
          <div id="order_tabs-1"> 
            <!--******************************************-->
            <div class="row order-content container">
             <div id="wrapper">
              <div id="scroller">
              <div id="pullDown"> <span class="pullDownIcon"></span><span class="pullDownLabel">下拉刷新...</span> </div>
              <ul class="order-timeline" id="uncomplete">
               <!-- <li>
               </li> -->
              </ul>
              <div id="pullUp" style="margin-top:15px;"> <span class="pullUpIcon"></span><span class="pullUpLabel">上拉加载更多...</span> </div>
            </div>
        </div>
      </div>
    </div>
  </div>
</div>
	  </div></div>


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
        <div class="col-md-12 col-sm-12 col-xs-12" onclick="toCancelOrder()"><span>请教练确认</span></div>
      </div>
    </div>
  </div>
</div>
<!--没有订单时显示的--> 
<div id="noOrder" class="container" style="margin-top:50px;display:none">
	<div class="row">
    	<div class="col-md-12 col-sm-12 col-xs-12">
        	<img src="images/emptyorder.png" class="img-responsive center-block" style="width:20%;" />
            <p class="text-center" style="color:rgb(217,217,217); font-size:13px; margin-top:8px;">您还没有相关订单</p>
        </div>
    </div>
</div>
<!--点击取消订单按钮的提示弹框 starts-->

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
	$("#cancelbtns").click(function ()
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
	/* $('.overlay-cancle .overlay-cancle-content .col-md-12:last-child span').click(function ()
	{
		$('.overlay-cancle').css('display','none');
		
	}); */
});

</script>
</body>
</html>
