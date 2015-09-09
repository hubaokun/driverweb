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
<link href="css/scrollbar.css" rel="stylesheet" type="text/css" />
<link href="css/iscroll.css" rel="stylesheet" type="text/css" />
<link href="css/style.css" rel="stylesheet" type="text/css" />
<style type="text/css">
</style>
<%
   HttpSession s=request.getSession();
   String studentid=s.getAttribute("studentid").toString();
%>
<script src="js/jquery-1.8.3.min.js"></script> 
<script src="js/jquery-ui-1.10.3.min.js"></script> 
<script src="js/jquery.raty.min.js"></script>
<script src="js/iscroll.js"></script>
<style type="text/css">
</style>
<script type="text/javascript">

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
		var el, li, i;
		el = document.getElementById('thelist');

		for (i=0; i<3; i++) {
			li = document.createElement('li');
			li.innerText = 'Generated row ' + (++generatedCount);
			el.insertBefore(li, el.childNodes[0]);
		}
		
		myScroll.refresh();		//数据加载完成后，调用界面更新方法   Remember to refresh when contents are loaded (ie: on ajax completion)
	}, 1000);	// <-- Simulate network congestion, remove setTimeout from production!
}

/**
 * 滚动翻页 （自定义实现此方法）
 * myScroll.refresh();		// 数据加载完成后，调用界面更新方法
 */
function pullUpAction () {
	setTimeout(function () {	// <-- Simulate network congestion, remove setTimeout from production!
		var el, li, i;
		el = document.getElementById('thelist');

		for (i=0; i<3; i++) {
			li = document.createElement('li');
			li.innerText = 'Generated row ' + (++generatedCount);
			el.appendChild(li, el.childNodes[0]);
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
<div class="container" >
  <div id="tabs" >
    <ul class="foot-nav" data-role="footer">
      <li class="active"><a href="javascript:void(0);"><span class="coach"></span><p>找教练</p></a></li><li><a href="getmyorder(<%=studentid%>)"><span class="order"></span><p>订单</p></a></li><li><a href="my.html"><span class="my"></span><p>我的</p></a></li>
    </ul>
    <div id="tabs-1">
      <div class="row search-wrap">
        <form>
          <div class="col-md-3 col-sm-3 col-xs-3"> <span><a href="citychoose.html">杭州</a><i class="glyphicon icon-citydown"></i></span> </div>
          <div class="col-md-7 col-sm-7 col-xs-7">
            <div class="input-group">
              <input type="text" id="search_coach" class="form-control" placeholder="" aria-describedby="basic-addon2">
              <span class="input-group-addon" id="basic_addon2" ><i class="glyphicon glyphicon-search"></i></span> </div>
          </div>
          <div class="col-md-2 col-sm-2 col-xs-2">
          	<span id="select-coach">筛选</span>
          </div>
        </form>
      </div>
      <div id="wrapper">
        <div id="scroller">
          <div id="pullDown"> <span class="pullDownIcon"></span><span class="pullDownLabel">下拉刷新...</span> </div>
          <div class="row content-wrap">
            <ul id="thelist">
              <li>
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
              </li>
              <li>
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
              </li>
              <li>
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
              </li>
              <li>
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
              </li>
              <li>
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
              </li>
              <li>
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
              </li>
              <li>
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
              </li>
            </ul>
          </div>
          <div id="pullUp"> <span class="pullUpIcon"></span><span class="pullUpLabel">上拉加载更多...</span> </div>
        </div>
      </div>
    </div>
  </div>
</div>

<!--单个教练选择弹出框 starts-->
<div class="overlay">
  <div class="overlay-content ">
    <div class="container"  id="coach_detail">
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
      <section class="overlay-button-group"> <a href="coursearrange.html">预约课程</a><a href="./coachdetail.jsp?coachid=">教练详情</a> </section>
      <!--<div style="width:100px; height:50px; display:block;"></div>-->
    </div>
  </div>
</div>
<!--弹出框 ends--> 

<!--筛选弹出框 starts-->
<div class="overlay-select">
	<div class="overlay-content ">
    	<div class="container">
        	<div class="row content-wrap">
            	<form>
                	<div class="col-md-12 col-sm-12 col-xs-12 subject-wrap">
                    	<div class="row">
                        	<div class="col-md-4 col-sm-4 col-xs-4" style="width:30%;"><input type="radio" id="subject2" name="subject" value=1 checked /><label for="subject2">科目二</label></div>
                            <div class="col-md-4 col-sm-4 col-xs-4" style="width:30%;"><input type="radio" id="subject3" name="subject" value=2 /><label for="subject3">科目三</label></div>
                            <div class="col-md-4 col-sm-4 col-xs-4" style="width:40%;"><input type="radio" id="subject_no" name="subject" value=3 /><label for="subject_no" style="padding-left:10px;">考试训练</label></div>
                        </div>
                    </div>
                    <div class="col-md-12 col-sm-12 col-xs-12 car-school">
                    	<p>驾校</p>
                        <div class="row">
                        	<div class="col-md-12 col-sm-12 col-xs-12">
                            	<input type="text" placeholder="请输入驾校" />
                            </div>
                        </div>
                    </div>
                    <div class="col-md-12 col-sm-12 col-xs-12 gender-wrap">
                        <p>性别</p>
                        <div class="row">
                            <div class="col-md-4 col-sm-4 col-xs-4"><input type="radio" id="gender_no" name="gender" checked /><label for="gender_no">不限</label></div>
                            <div class="col-md-4 col-sm-4 col-xs-4"><input type="radio" id="gender_male" name="gender" /><label for="gender_male">男</label></div>
                            <div class="col-md-4 col-sm-4 col-xs-4"><input type="radio" id="gender_female" name="gender" /><label for="gender_female">女</label></div>
                        </div>
                    </div>
                    <div class="col-md-12 col-sm-12 col-xs-12 car-year-wrap">
                        <p>星级</p>
                        <div class="row">
                            <div class="col-md-3 col-sm-3 col-xs-3"><input type="radio" id="year_no" name="car-year" checked /><label for="year_no">不限</label></div>
                            <div class="col-md-3 col-sm-3 col-xs-3"><input type="radio" id="year_one" name="car-year" /><label for="year_one">1星以上</label></div>
                            <div class="col-md-3 col-sm-3 col-xs-3"><input type="radio" id="year_two" name="car-year" /><label for="year_two">2星以上</label></div>
                            <div class="col-md-3 col-sm-3 col-xs-3"><input type="radio" id="year_three" name="car-year" /><label for="year_three">3星以上</label></div>
                            <div class="col-md-3 col-sm-3 col-xs-3"><input type="radio" id="year_four" name="car-year" /><label for="year_four">4星以上</label></div>
                             <div class="col-md-3 col-sm-3 col-xs-3"><input type="radio" id="year_five" name="car-year" /><label for="year_five">5星以上</label></div>
                        </div>
                    </div>
                    <div class="col-md-12 col-sm-12 col-xs-12 button-wrap">
                        <button type="reset" class="pull-left">重置</button>
                        <span class="pull-right" id="select-finish" >完成</span>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<!--筛选弹出框 ends-->

<script>
$(document).ready(function()
{
	load_coachlist();

	$('#order_tabs').tabs();

// 	//pop up
// 	$(".detail-wrap").on('click',function ()
// 	{
// 		$(".overlay").css('display','block');
// 	});
	
	$(".overlay").on('click',function ()
	{
		$(".overlay").css('display','none');
	});
	
	//search input focus&blur
	$("#search_coach").on('focus',function ()
	{
		$("#tabs-1 div.content-wrap").css("display","none");
		$('.foot-nav').css("display","none");
	});
	
	$("#search_coach").on('blur',function ()
	{
		$("#tabs-1 div.content-wrap").css("display","block");
		$('.foot-nav').css("display","block");
	});
	
	//筛选弹出框调用
	$("#select-coach").on('click',function ()
	{
		//调用城市列表
		getSchoolByCity();
		$('.overlay-select').slideDown('slow');
	});
	
	$('#select-finish').on('click',function()
	{
		//查询
		search_com();
		$('.overlay-select').slideUp('slow');
	});
	
	$('#basic_addon2').on('click',function()
	{
		search_coache();
	});
	
	
	
});

var active_url = "../sbook?action=GETCOACHLIST";
var studentid =0;


function load_coachlist(search_condition){
	$.getJSON(active_url,search_condition,function(data){
		$("#thelist").html("");		  
		$.each(data.coachlist, function(i, item) {
			var score_int = 0;
			var score_html= "";
			var avatar_url = "";
			avatar_url = item.avatarurl?item.avatarurl:"http://xiaobaxueche.com/dadmin/weixinWeb/images/caoch_avart_defalt.jpg"
			scope_int= parseInt(item.score);
			scope_int= scope_int>5?5:scope_int;
			var score_html = generateScoreView(scope_int);
            
            $("#thelist").append(
            		 "<li>"+"<div class='col-md-12 col-sm-12 col-xs-12' coachid="+item.coachid+">"+
                     "<div class='row detail-wrap' onclick='dispalyDetail(this)'>" +
                       "<div class='col-md-3 col-sm-3 col-xs-3'> <img src='"+avatar_url+"' class='img-responsive img-circle ' /> </div>"+
                       "<div class='col-md-9 col-sm-9 col-xs-9'>" +
                         "<p>" +item.realname+"<span class='pull-right'>"+
                         score_html+
                         "</span></p>"+
                         "<p>"+item.detail+"</p>"+
                         "</div>"+
                     "</div>"+
                     "<hr/>"+
                   "</div>"+"</li>"
            );

        });
		
	});
}

function generateScoreView(scope){
	var score_str="";
	for(var i =0;i<scope;i++){
		score_str+="<i class='glyphicon glyphicon-star'></i>";
    };
    return score_str;
}

function search_coache(){
	var search_str = $("#search_coach").val();
	var search_condition = {"condition1":search_str};
	load_coachlist(search_condition);
};

function search_com(){
	var condition1 = $("#search_coach").val();//condition1
	var condition6 = $("input[name='subject']:checked").val();;//condition6
	var driverschoolid = $("#driverschoolid").val();//driverschoolid
	var search_condition = {"condition1":condition1,"condition6":condition6,"driverschoolid":driverschoolid};
	load_coachlist(search_condition);
};

function getSchoolByCity(){
	var city_url = "../sbook?action=GETDRIVERSCHOOLBYCITYNAME";
	var search_condition = {"cityname":"杭州"};
	$.getJSON(city_url,search_condition,function(data){
		//alert(data.dslist);
	});
	

};

function dispalyDetail(event){
	var coachid=0;
	coachid = event.parentElement.getAttribute("coachid");
	
	$("#coach_detail").html("");
	$("#coach_detail").append(
			event.outerHTML+
          "</div>"+
		      "</div>"+
		      "<section class='overlay-button-group'> <a href='coursearrange.jsp?coachid="+coachid+"&studentid="+studentid+"'>预约课程</a><a href='coachdetail.jsp?coachid="+coachid+"'>教练详情</a> </section>"
			);
	$(".overlay").css('display','block');
}
function getmyorder(studentid)
{
	var action="GetUnCompleteOrder";
	$.ajax({
		  type:"post",
		  dataType:"json",
		  url:"/sorder",
		  data:{
			    action:action,
			    studentid :studentid
		  },
		success:function(data){
			var result=data;
			
		}	
		
	});
	
}
function getcoachdetail(studentid)
{
	var action="GetUnCompleteOrder";
	$.ajax({
		  type:"post",
		  dataType:"json",
		  url:"/sorder",
		  data:{
			    action:action,
			    studentid :studentid
		  },
		success:function(data){
			var result=data;
			
		}	
		
	});
	
}
</script>
</body>
</html>
