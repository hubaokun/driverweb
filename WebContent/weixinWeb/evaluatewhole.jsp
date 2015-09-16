<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>评价</title>
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="css/font-awesome.css" rel="stylesheet" />
<link href="css/scrollbar.css" rel="stylesheet" type="text/css" />
<link href="css/iscroll.css" rel="stylesheet" type="text/css" />
<link href="css/coachdetail.css" rel="stylesheet" type="text/css" />
<script src="js/iscroll.js"></script>
<style type="text/css">
#wrapper
{
top:0px;
}
ul#thelist .row
{
margin-left: 0px;
margin-right:0px;
}
</style>
<script src="js/jquery-1.8.3.min.js"></script> 
<script src="js/jquery-ui-1.10.3.min.js"></script> 
<%
    String coachid=request.getParameter("coachid");
%>
<script type="text/javascript">
var evaluatelist;
var pagenum=0;
var hasmore=1;
var type=2;
var coachid=<%=coachid%>;
var action="getCoachComments";
$(function(){
	$.ajax({		   
		type : "POST",
		url : "../sbook",
		dataType: "json",
		data : {
			action : action,
			coachid : coachid,
			type : type,
			pagenum   : pagenum
		},
		success : function(data) {
			evaluatelist=data.evalist;
			var content_list="";
            for(var i=0;i<evaluatelist.length;i++)
            {
            	var adddate=evaluatelist[i].addtime;
            	adddate=adddate.substring(0,10);
            	content_list=content_list+"<div class=\"row coach-judge-whole\"><div class=\"col-md-3 col-sm-3 col-xs-3\"><p>"+evaluatelist[i].nickname+"</p></div><div class=\"col-md-9 col-sm-9 col-xs-9\"><p><span class=\"pull-right\">"+adddate+"</span></p></div><div class=\"col-md-12 col-sm-12 col-xs-12\"><p>"+evaluatelist[i].content+"</p></div></div>";
            	
            }
            $("#thelist").html(content_list);
            hasmore=data.hasmore;
		}
   });
});

var myScroll,
pullDownEl, pullDownOffset,
pullUpEl, pullUpOffset,
generatedCount = 0;

/**
* 下拉刷新 （自定义实现此方法）
* myScroll.refresh();		// 数据加载完成后，调用界面更新方法
*/
function pullDownAction () {
setTimeout(function () {	// <-- Simulate network congestion, remove setTimeout from production!
	$.ajax({		   
		type : "POST",
		url : "../sbook",
		dataType: "json",
		data : {
			action : action,
			coachid : coachid,
			type : type,
			pagenum   : pagenum
		},
		success : function(data) {
			evaluatelist=data.evalist;
			var content_list="";
            for(var i=0;i<evaluatelist.length;i++)
            {
            	var adddate=evaluatelist[i].addtime;
            	adddate=adddate.substring(0,10);
            	content_list=content_list+"<li><div class=\"row coach-judge-whole\"><div class=\"col-md-3 col-sm-3 col-xs-3\"><p>"+evaluatelist[i].nickname+"</p></div><div class=\"col-md-9 col-sm-9 col-xs-9\"><p><span class=\"pull-right\">"+adddate+"</span></p></div><div class=\"col-md-12 col-sm-12 col-xs-12\"><p>"+evaluatelist[i].content+"</p></div></div></li>";
            	
            }
            $("#thelist").html(content_list);
            hasmore=data.hasmore;
		}
   });
	
	myScroll.refresh();		//数据加载完成后，调用界面更新方法   Remember to refresh when contents are loaded (ie: on ajax completion)
}, 1000);	// <-- Simulate network congestion, remove setTimeout from production!
}

/**
* 滚动翻页 （自定义实现此方法）
* myScroll.refresh();		// 数据加载完成后，调用界面更新方法
*/
function pullUpAction () {
setTimeout(function () {	// <-- Simulate network congestion, remove setTimeout from production!
	if(hasmore==1)
    {
    	pagenum++;
    	$.ajax({		   
    		type : "POST",
    		url : "../sbook",
    		dataType: "json",
    		data : {
    			action : action,
    			coachid : coachid,
    			type : type,
    			pagenum  : pagenum
    		},
    		success : function(data) {
    			evaluatelist=data.evalist;
    			var content_list="";
                for(var i=0;i<evaluatelist.length;i++)
                {
                	var adddate=evaluatelist[i].addtime;
                	adddate=adddate.substring(0,10);
                	content_list=content_list+"<li><div class=\"row coach-judge-whole\"><div class=\"col-md-3 col-sm-3 col-xs-3\"><p>"+evaluatelist[i].nickname+"</p></div><div class=\"col-md-9 col-sm-9 col-xs-9\"><p><span class=\"pull-right\">"+adddate+"</span></p></div><div class=\"col-md-12 col-sm-12 col-xs-12\"><p>"+evaluatelist[i].content+"</p></div></div></li>";
                }
                $("#thelist").append(content_list);
                hasmore=data.hasmore;
    		}
       });
    }
	
	myScroll.refresh();		// 数据加载完成后，调用界面更新方法 Remember to refresh when contents are loaded (ie: on ajax completion)
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
<div id="evaluatelist" class="container">
  <div id="wrapper">
    <div id="scroller">
      <div id="pullDown"> <span class="pullDownIcon"></span><span class="pullDownLabel">下拉刷新...</span> </div>
      <ul id="thelist">
        <li>
          <div class="row coach-judge-whole">
            <div class="col-md-3 col-sm-3 col-xs-3">
              <p>YOYO</p>
            </div>
            <div class="col-md-9 col-sm-9 col-xs-9">
              <p><span class="pull-right">2015-01-01</span></p>
            </div>
            <div class="col-md-12 col-sm-12 col-xs-12">
              <p>教练很有耐心，不随便发火，五星点赞</p>
            </div>
          </div>
        </li>
        <li>
          <div class="row coach-judge-whole">
            <div class="col-md-3 col-sm-3 col-xs-3">
              <p>YOYO</p>
            </div>
            <div class="col-md-9 col-sm-9 col-xs-9">
              <p><span class="pull-right">2015-01-01</span></p>
            </div>
            <div class="col-md-12 col-sm-12 col-xs-12">
              <p>教练很有耐心，不随便发火，五星点赞</p>
            </div>
          </div>
        </li>
        <li>
          <div class="row coach-judge-whole">
            <div class="col-md-3 col-sm-3 col-xs-3">
              <p>YOYO</p>
            </div>
            <div class="col-md-9 col-sm-9 col-xs-9">
              <p><span class="pull-right">2015-01-01</span></p>
            </div>
            <div class="col-md-12 col-sm-12 col-xs-12">
              <p>教练很有耐心，不随便发火，五星点赞</p>
            </div>
          </div>
        </li>
        <li>
          <div class="row coach-judge-whole">
            <div class="col-md-3 col-sm-3 col-xs-3">
              <p>YOYO</p>
            </div>
            <div class="col-md-9 col-sm-9 col-xs-9">
              <p><span class="pull-right">2015-01-01</span></p>
            </div>
            <div class="col-md-12 col-sm-12 col-xs-12">
              <p>教练很有耐心，不随便发火，五星点赞</p>
            </div>
          </div>
        </li>
      </ul>
      <div id="pullUp" style="margin-top:15px;"> <span class="pullUpIcon"></span><span class="pullUpLabel">上拉加载更多...</span> </div>
    </div>
  </div>
</div>
</body>
</html>
