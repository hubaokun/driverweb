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
<script src="js/jquery-1.8.3.min.js"></script> 
<script src="js/jquery-ui-1.10.3.min.js"></script> 
<script src="js/jquery.raty.min.js"></script>
<script src="js/iscroll.js"></script>
<script type="text/javascript" src="js/checksession.js"></script>
<script type="text/javascript">
var active_url = "../sbook?action=GETCOACHLIST";
var studentid=18;//${sessionScope.studentid};
var pagenum=0;
var city="杭州";//"${sessionScope.city}";
var myScroll,
	pullDownEl, pullDownOffset,
	pullUpEl, pullUpOffset,
	generatedCount = 0;

	city=city.length>2?city.substr(0,2):city;

/**
 * 下拉刷新 （自定义实现此方法）
 * myScroll.refresh();		// 数据加载完成后，调用界面更新方法
 */
function pullDownAction () {
	setTimeout(function () {	// <-- Simulate network congestion, remove setTimeout from production!
// 		var el, li, i;
// 		el = document.getElementById('thelist');

// 		for (i=0; i<3; i++) {
// 			li = document.createElement('li');
// 			li.innerText = 'Generated row ' + (++generatedCount);
// 			el.insertBefore(li, el.childNodes[0]);
// 		}
pagenum=0;
//load_coachlist();
search_com();
		//myScroll.refresh();		//数据加载完成后，调用界面更新方法   Remember to refresh when contents are loaded (ie: on ajax completion)
	}, 1000);	// <-- Simulate network congestion, remove setTimeout from production!
}

/**
 * 滚动翻页 （自定义实现此方法）
 * myScroll.refresh();		// 数据加载完成后，调用界面更新方法
 */
function pullUpAction () {
	setTimeout(function () {	// <-- Simulate network congestion, remove setTimeout from production!
// 		var el, li, i;
// 		el = document.getElementById('thelist');

// 		for (i=0; i<3; i++) {
// 			li = document.createElement('li');
// 			li.innerText = 'Generated row ' + (++generatedCount);
// 			el.appendChild(li, el.childNodes[0]);
// 		}

pagenum++;
//search_com();
load_more();
		
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
      <li class="active"><a href="javascript:void(0);"><span class="coach"></span><p>找教练</p></a></li><li><a href="uncompleorder.jsp"><span class="order"></span><p>订单</p></a></li><li><a href="my.jsp"><span class="my"></span><p>我的</p></a></li>
    </ul>
    <div id="tabs-1">
      <div class="row search-wrap">
        <form>
          <div class="col-md-3 col-sm-3 col-xs-3"> <span><a href="javascript:void(0);">${sessionScope.city}</a><i class="glyphicon icon-citydown"></i></span> </div>
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
    <div class="container"  >
       <div class="row content-wrap" id="coach_detail"></div>
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
                    	<div class="col-md-12 col-sm-12 col-xs-12 subject-wrap">
          <p>科目</p>
            <div class="row">
              <div class="col-md-4 col-sm-4 col-xs-4" style="width:30%;">
                <input type="radio" id="subject2" name="subject" subjectid="1" checked />
                <label for="subject2">科目二</label>
              </div>
              <div class="col-md-4 col-sm-4 col-xs-4" style="width:30%;">
                <input type="radio" id="subject3" name="subject" subjectid="2"/>
                <label for="subject3">科目三</label>
              </div>
              <div class="col-md-4 col-sm-4 col-xs-4" style="width:40%;">
                <input type="radio" id="subject_no" name="subject" subjectid="3"/>
                <label for="subject_no" style="padding-left:10px;">考试训练</label>
              </div>
            </div>
          </div>
                    <div class="col-md-12 col-sm-12 col-xs-12 car-school">
                    	<p>驾校</p>
                        <div class="row">
                        	<div class="col-md-12 col-sm-12 col-xs-12">
                            	<select type="text" id="selectSchool" placeholder="请选择驾校"  onchange="getSelectedSchoolId(this)" >
                            		
                            	</select>
                            </div>
                            
                        </div>
                    </div>
                    <div class="col-md-12 col-sm-12 col-xs-12 cartype-wrap">
                        <p>准驾车型</p>
                        <div class="row">
                            <div class="col-md-4 col-sm-4 col-xs-4"><input type="radio" id="cartype_no" name="cartype" modelid="0" checked /><label for="cartype_no">不限</label></div>
                            <div class="col-md-4 col-sm-4 col-xs-4"><input type="radio" id="cartype_c1" name="cartype" modelid="17" /><label for="cartype_c1">C1</label></div>
                            <div class="col-md-4 col-sm-4 col-xs-4"><input type="radio" id="cartype_c2" name="cartype" modelid="18" /><label for="cartype_c2">C2</label></div>
                        </div>
                    </div>
                    <div class="col-md-12 col-sm-12 col-xs-12 button-wrap">
                        <button type="reset" class="pull-left">重置</button>
                        <span class="pull-right" id="cancel-select" style="margin-left:10px;">取消</span>
                        <span class="pull-right" id="select-finish" >完成</span>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<!--筛选弹出框 ends-->

<script>
var cityNameArr = new Array();
var cityIdArr = new Array();
$(document).ready(function()
{
	load_coachlist("'fixedposition':"+city);

	$('#order_tabs').tabs();
	
	getSchoolByCity();

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
		$("#pullUp").css("display","none");
	});
	
	$("#search_coach").on('blur',function ()
	{
		$("#tabs-1 div.content-wrap").css("display","block");
		$('.foot-nav').css("display","block");
	});
	
	//筛选弹出框调用
/* 	$("#select-coach").on('click',function ()
	{
		//调用城市列表
		getSchoolByCity();
		$('.overlay-select').slideDown('slow');
	}); */
	
/* 	$('#select-finish').on('click',function()
	{
		//查询
		search_com();
		$('.overlay-select').slideUp('slow');
	}); */
	
	//筛选弹出框调用
	$("#select-coach").on('click',function ()
	{
		//调用城市列表
		//getSchoolByCity();
		var school;
		$('.overlay-select').css("display","block");

		for (var i=0;i<cityNameArr.length;i++)
		{
			school += '<option id="'+ cityIdArr[i] +'"  value="' + cityNameArr[i] + '">' +  cityNameArr[i] + '</option>'; 
		}
		
		$('#selectSchool').append(school);
		
		//$("select.flexselect").flexselect();
		
	});
	$('#select-finish').on('click',function()
	{
		/*查询*/
		search_com();
		$('.overlay-select').css("display","none");
	});
	$('#cancel-select').click(function ()
	{
		$('.overlay-select').css('display','none');
	});
	
	
	$('#basic_addon2').on('click',function()
	{
		search_coache();
	});
	
	
	
});



function load_coachlist(search_condition){
	$.getJSON(active_url,search_condition,function(data){
		$("#thelist").html("");		  
		binddate2coachlist(data);
	});
}

function binddate2coachlist(data){	  
		$.each(data.coachlist, function(i, item) {
			var score_int = 0;
			var score_html= "";
			var avatar_url = "";
			avatar_url = item.avatarurl?item.avatarurl:"http://xiaobaxueche.com/dadmin/weixinWeb/images/caoch_avart_defalt.jpg"
			score_int= parseInt(item.score);
			score_int= score_int>5?5:score_int;
			var score_html = "";

			for(var i =0;i<score_int;i++){
				score_html+="<i class='glyphicon glyphicon-star'></i>";
		    };

            $("#thelist").append(
            		 "<li>"+"<div class='col-md-12 col-sm-12 col-xs-12' coachid="+item.coachid+">"+
                     "<div class='row detail-wrap' onclick='dispalyDetail(this)'>" +
                       "<div class='col-md-3 col-sm-3 col-xs-3'> <img src='"+avatar_url+"' class='img-responsive img-circle ' /></div>"+
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
		
		$("#pullUp").html("");
		if(data.coachlist.length<10){
			$("#pullUp").append("<span class='pullUpLabel'></span>");
		}else{
			$("#pullUp").append("<span class='pullUpIcon'></span><span class='pullUpLabel'>上拉加载更多...</span>");
		}
		
		myScroll.refresh();	
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
var driverschoolid;
function search_com(){
	var condition1 = $("#search_coach").val();//condition1
	var condition6 = $("input[name='subject']:checked").attr("subjectid");//condition6
	var condition10 = $("input[name='cartype']:checked").attr("modelid");//condition10
	//driverschoolid = $("#driverschoolid").val();//driverschoolid
	var search_condition = {"condition1":condition1,"condition6":condition6,"condition10":condition10,"driverschoolid":driverschoolid,"pagenum":pagenum,"fixedposition":city};
	load_coachlist(search_condition);
};

function load_more(){
	var condition1 = $("#search_coach").val();//condition1
	var condition6 = $("input[name='subject']:checked").val();;//condition6
	//var driverschoolid = $("#driverschoolid").val();//driverschoolid
	var search_condition = {"condition1":condition1,"condition6":condition6,"driverschoolid":driverschoolid,"pagenum":pagenum,"fixedposition":city};
	$.getJSON(active_url,search_condition,function(data){	  
		binddate2coachlist(data);
	});
};

function getSchoolByCity(){
	var city_url = "../sbook?action=GETDRIVERSCHOOLBYCITYNAME";
	var search_condition = {"cityname":city};
	$.getJSON(city_url,search_condition,function(data){
		//alert(data.dslist); 
		for (var i=0;i<data.dslist.length;i++)
		{
			cityNameArr.push(data.dslist[i].name);
			cityIdArr.push(data.dslist[i].schoolid);
		}
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

function getSelectedSchoolId(event){
	driverschoolid = $(event).find("option:selected").attr("id");
}


</script>
</body>
</html>
