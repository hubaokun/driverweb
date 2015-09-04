<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>预约课程</title>
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="css/font-awesome.css" rel="stylesheet" />
<link href="css/course.css" rel="stylesheet" type="text/css" />
<link href="css/course.css" rel="stylesheet" type="text/css" />
<style type="text/css">

</style>
</head>
<body>
<div class="container">
  <div class="row course-head">
    <div class="col-md-12 col-sm-12 col-xs-12 course-head-top">
      <div class="row">
        <div class="col-md-10 col-sm-12 col-xs-10">
          <p><span>程英豪</span><span><i class="icon icon-star"></i><i class="icon icon-star"></i><i class="icon icon-star"></i><i class="icon icon-star"></i><i class="icon icon-star"></i><i class="icon icon-star"></i></span></p>
          <p>浙江省杭州市余杭区西环路188号</p>
        </div>
        <div class="col-md-2 col-sm-2 col-xs-2"> <i class="icon icon-chevron-right pull-right"></i> </div>
      </div>
    </div>
    <!--课程表的日期选择 starts-->
    <div class="col-md-12 col-sm-12 col-xs-12 course-head-bottom">
      <div class="row course-choose-date">
        <div class="course-choose-date-inner">
          <ul class="course-choose-ul">
            <li class="active">
              <p id="custom1"> <span>四</span> <span> <i>8-13</i> <i></i> </span> </p>
              <p><i class="icon icon-caret-up"></i></p>
            </li>
            <li>
              <p id="custom2"></p>
              <p><i class="icon icon-caret-up"></i></p>
            </li>
            <li>
              <p id="custom3"></p>
              <p><i class="icon icon-caret-up"></i></p>
            </li>
            <li>
              <p id="custom4"></p>
              <p><i class="icon icon-caret-up"></i></p>
            </li>
            <li>
              <p id="custom5"></p>
              <p><i class="icon icon-caret-up"></i></p>
            </li>
            <li>
              <p id="custom6"></p>
              <p><i class="icon icon-caret-up"></i></p>
            </li>
            <li>
              <p id="custom7"></p>
              <p><i class="icon icon-caret-up"></i></p>
            </li>
            <li>
              <p id="custom8"></p>
              <p><i class="icon icon-caret-up"></i></p>
            </li>
            <li>
              <p id="custom9"></p>
              <p><i class="icon icon-caret-up"></i></p>
            </li>
            <li>
              <p id="custom10"></p>
              <p><i class="icon icon-caret-up"></i></p>
            </li>
            <li>
              <p id="custom11"></p>
              <p><i class="icon icon-caret-up"></i></p>
            </li>
            <li>
              <p id="custom12"></p>
              <p><i class="icon icon-caret-up"></i></p>
            </li>
            <li>
              <p id="custom13"></p>
              <p><i class="icon icon-caret-up"></i></p>
            </li>
            <li>
              <p id="custom14"></p>
              <p><i class="icon icon-caret-up"></i></p>
            </li>
            <li>
              <p id="custom15"></p>
              <p><i class="icon icon-caret-up"></i></p>
            </li>
            <li>
              <p id="custom16"></p>
              <p><i class="icon icon-caret-up"></i></p>
            </li>
            <li>
              <p id="custom17"></p>
              <p><i class="icon icon-caret-up"></i></p>
            </li>
            <li>
              <p id="custom18"></p>
              <p><i class="icon icon-caret-up"></i></p>
            </li>
            <li>
              <p id="custom19"></p>
              <p><i class="icon icon-caret-up"></i></p>
            </li>
            <li>
              <p id="custom20"></p>
              <p><i class="icon icon-caret-up"></i></p>
            </li>
            <li>
              <p id="custom21"></p>
              <p><i class="icon icon-caret-up"></i></p>
            </li>
            <li>
              <p id="custom22"></p>
              <p><i class="icon icon-caret-up"></i></p>
            </li>
            <li>
              <p id="custom23"></p>
              <p><i class="icon icon-caret-up"></i></p>
            </li>
            <li>
              <p id="custom24"></p>
              <p><i class="icon icon-caret-up"></i></p>
            </li>
            <li>
              <p id="custom25"></p>
              <p><i class="icon icon-caret-up"></i></p>
            </li>
            <li>
              <p id="custom26"></p>
              <p><i class="icon icon-caret-up"></i></p>
            </li>
            <li>
              <p id="custom27"></p>
              <p><i class="icon icon-caret-up"></i></p>
            </li>
            <li>
              <p id="custom28"></p>
              <p><i class="icon icon-caret-up"></i></p>
            </li>
            <li>
              <p id="custom29"></p>
              <p><i class="icon icon-caret-up"></i></p>
            </li>
            <li>
              <p id="custom30"></p>
              <p><i class="icon icon-caret-up"></i></p>
            </li>
          </ul>
        </div>
      </div>
    </div>
    <!--课程表的日期选择 ends--> 
  </div>
  <!--课程选择 starts-->
  <div class="course-scroll">
  <div class="row course-tips">
    <div class="col-md-12 col-sm-12 col-xs-12">
      <p> <span><i class="icon  icon-stop"></i>不可预约</span> <span><i class="icon  icon-stop"></i>可预约</span> <span><i class="icon  icon-stop"></i>已选择</span> </p>
    </div>
  </div>
  <div class="row course-date-list">
    <form>
      <div class="col-md-12 col-sm-12 col-xs-12">
        <div class="row course-date-list-group"> 
        <div style="display:none" id="HiddenDate"></div>
          <!--**************************************************-->
          <div class="course-date-list-group-top">
            <div class="col-md-3 col-sm-3 col-xs-3"> 
                <a href="javascript:;" class="unavailable">
                    <p>5:00</p>
                    <p>不可预约</p>
<!--                    <p>100</p>
                    <p><i class="icon icon-ok"></i></p>-->
                </a> 
              </div>
            <div class="col-md-3 col-sm-3 col-xs-3"> <a href="javascript:;" class="unavailable appointment">
              	<p>6:00</p>
              <p>我已预约</p>
<!--              <p>100</p>
              <p><i class="icon icon-ok"></i></p>-->
              </a> </div>
            <div class="col-md-3 col-sm-3 col-xs-3"> <a href="javascript:;" class="unavailable">
              	<p>7:00</p>
              <p>不可预约</p>
<!--              <p>100</p>
              <p><i class="icon icon-ok"></i></p>-->
              </a> </div>
            <div class="col-md-3 col-sm-3 col-xs-3"> <a href="javascript:;" class="unavailable">
              	<p>8:00</p>
              <p>不可预约</p>
<!--              <p>100</p>
              <p><i class="icon icon-ok"></i></p>-->
              </a> </div>
            <div class="clearfix"></div>
          </div>
          <!--**************************************************-->
          <div class="course-date-list-group-bottom">
            <div class="col-md-3 col-sm-3 col-xs-3"> <a href="javascript:;" class="unavailable appointment">
              	<p>9:00</p>
              <p>我已预约</p>
<!--              <p>100</p>
              <p><i class="icon icon-ok"></i></p>-->
              </a> </div>
            <div class="col-md-3 col-sm-3 col-xs-3"> <a href="javascript:;" class="available">
              	<p>10:00</p>
              <p>科目二</p>
              <p>100</p>
              <p><i class="icon icon-ok"></i></p>
              </a> </div>
            <div class="col-md-3 col-sm-3 col-xs-3"> <a href="javascript:;" class="available">
              	<p>11:00</p>
              <p>科目二</p>
              <p>100</p>
              <p><i class="icon icon-ok"></i></p>
              </a> </div>
            <div class="col-md-3 col-sm-3 col-xs-3"> <a href="javascript:;" class="available">
              <p>12:00</p>
              <p>科目二</p>
              <p>100</p>
              <p><i class="icon icon-ok"></i></p>
            </a> </div>
            <div class="clearfix"></div>
          </div>
          <!--**************************************************--> 
        </div>
        <div class="row course-date-list-group"> 
          <!--**************************************************-->
          <div class="course-date-list-group-top">
            <div class="col-md-3 col-sm-3 col-xs-3"> <a href="javascript:;" class="available">
              	<p>13:00</p>
              <p>科目二</p>
              <p>100</p>
              <p><i class="icon icon-ok"></i></p>
              </a> </div>
            <div class="col-md-3 col-sm-3 col-xs-3"> <a href="javascript:;" class="available">
              	<p>14:00</p>
              <p>科目二</p>
              <p>100</p>
              <p><i class="icon icon-ok"></i></p>
              </a> </div>
            <div class="col-md-3 col-sm-3 col-xs-3"> <a href="javascript:;" class="available">
              	<p>15:00</p>
              <p>科目二</p>
              <p>100</p>
              <p><i class="icon icon-ok"></i></p>
              </a> </div>
            <div class="col-md-3 col-sm-3 col-xs-3"> <a href="javascript:;" class="available">
              <p>16:00</p>
              <p>科目二</p>
              <p>100</p>
              <p><i class="icon icon-ok"></i></p>
              </a> </div>
            <div class="clearfix"></div>
          </div>
          <!--**************************************************-->
          <div class="course-date-list-group-bottom">
            <div class="col-md-3 col-sm-3 col-xs-3"> <a href="javascript:;" class="available">

              <p>17:00</p>
              <p>科目二</p>
              <p>100</p>
              <p><i class="icon icon-ok"></i></p>

              </a> </div>
            <div class="col-md-3 col-sm-3 col-xs-3"> <a href="javascript:;" class="available">

              <p>18:00</p>
              <p>科目二</p>
              <p>100</p>
              <p><i class="icon icon-ok"></i></p>

              </a> </div>
            <div class="col-md-3 col-sm-3 col-xs-3"> <a href="javascript:;" class="unavailable">

                	<p>19:00</p>
              <p>不可预约</p>
<!--              <p>100</p>
              <p><i class="icon icon-ok"></i></p>-->

              </a> </div>
            <div class="col-md-3 col-sm-3 col-xs-3"> <a href="javascript:;" class="unavailable">
              <p>20:00</p>
              <p>不可预约</p>
<!--              <p>100</p>
              <p><i class="icon icon-ok"></i></p>-->

            </a> </div>
            <div class="clearfix"></div>
          </div>
          <!--**************************************************--> 
        </div>
        <div class="row course-date-list-group"> 
          <!--**************************************************-->
          <div class="course-date-list-group-top">
            <div class="col-md-3 col-sm-3 col-xs-3"> <a href="javascript:;" class="unavailable">
              <p>21:00</p>
              <p>不可预约</p>
<!--              <p>100</p>
              <p><i class="icon icon-ok"></i></p>-->

              </a> </div>
            <div class="col-md-3 col-sm-3 col-xs-3"> <a href="javascript:;" class="unavailable">
              <p>22:00</p>
              <p>不可预约</p>
<!--              <p>100</p>
              <p><i class="icon icon-ok"></i></p>-->
   
              </a> </div>
            <div class="col-md-3 col-sm-3 col-xs-3"> <a href="javascript:;" class="unavailable">
              <p>23:00</p>
              <p>不可预约</p>
<!--              <p>100</p>
              <p><i class="icon icon-ok"></i></p>-->

            </a> </div>
            <div class="clearfix"></div>
          </div>
          <!--**************************************************--> 
        </div>
        <div style="width:100px ;height:20px;"></div>
      </div>
      
    </form>  
  </div>
  </div>
  <!--课程选择  ends-->
  <div class="row course-foot" >
    <div class="col-md-8 col-sm-8 col-xs-8">
      <p class="pull-right"> 共<span>0</span>小时，合计：￥<span>0</span> </p>
    </div>
    <div class="col-md-4 col-sm-4 col-xs-4"> <a href="orderconfirm.jsp">已选好</a> </div>
  </div>
</div>
<script src="js/jquery-1.8.3.min.js"></script>
<script>
$(document).ready(function()
{
	$('.course-choose-date ul.course-choose-ul li').on('click',function(){
		$(this).addClass('active').siblings().removeClass('active');
	
	});	
	
	$('.course-date-list a.available').toggle(
	function()
	{
		$(this).addClass('active');
	},
	function()
	{
		$(this).removeClass('active');
	});
	
	
$('.course-date-list a.available').on('click',function()
	{
		var counttime = 0;
		var countmoney = 0;
		$('.course-date-list').find('a.active').each(function(index, element) {
			counttime++;
			$(".course-foot .col-md-8 p span:first-child").empty().html(counttime);
			var money;
			money = parseInt($('.course-date-list .course-date-list-group .col-md-3 a p:nth-child(3)').html());
			countmoney += money;
			$(".course-foot .col-md-8 p span:last-child").empty().html(countmoney);
		});

		//选中之后，付款的账单弹出
		$('.course-foot').slideDown();
		if (counttime < 1)
		{
			$('.course-foot').slideUp();
		}
	});
	
	var width = $(document).width();
	//alert (width);
	var ww = width-12;
	$('.course-choose-date-inner').css('width',ww);	
	
	var wwwww = $(window).width();
	alert (wwwww);
	var height_w = $(window).height();
	//alert (height_w);
	var hh = height_w -121;
	//$('.course-date-list').css("height",hh);
	//$('.container').height(height_w);
	
});
</script>
<!--课程表的日期选择JS函数 starts-->
<script>
var date = new Date();
//alert (date);
year = date.getFullYear();
//alert (year);
month = date.getMonth();
//alert (month);
date = date.getDate();
//alert (date);

//月份天数
var DaysInMonth = new Array(31,28,31,30,31,30,31,31,30,31,30,31); 

var dd = new Date();
dd  = dd.valueOf()
//alert (dd);

var date  = dd  + 30 * 24 * 60 * 60 * 1000;
//alert (date);

//30天后的日子
date  = new Date(date);
//alert (date);

function InitDate()
{
  for(var i = 1;i <= 30;i++)
  {
	date = new Date();
	document.getElementById("custom" + i).innerHTML = GetDate(date,i - 1);
  }
}


//<span>四</span>
//              <span>
//              	<i>8-13</i>
//                <i></i>
//              </span>
//this.month + "-" + this.date + this.day;
function GetDate(currentDate,num)
{
  var date = new Date(currentDate);
  date  = date.valueOf()
  date  = date + num * 24 * 60 * 60 * 1000;
  date  = new Date(date);
  this.year = date.getFullYear();
  this.month = date.getMonth() + 1;
  this.date = date.getDate();
  this.day = new Array("日", "一", "二", "三", "四", "五", "六")[date.getDay()];
  this.hour = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
  this.minute = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
  this.second = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
  var currentTime = "<span>" + this.day + "</span>" + "<span><i>" + this.month + "-" + this.date + "</i><i></i></span>"; 
  return currentTime;
}

</script>
<script>
window.onload= function ()
{
	 InitDate();
	 
	 var width = $(document).width();
	 $('.course-choose-date-inner').width(width);
};
</script>
</body>
</html>
