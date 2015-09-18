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
<link href="css/loader.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/checksession.js"></script>
</head>
<body>
<div class="container">
  <div class="row course-head">
    <div class="col-md-12 col-sm-12 col-xs-12 course-head-top">
      <div class="row">
        <div class="col-md-10 col-sm-12 col-xs-10" id="coach_detail">
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
      <p> <span><i class="icon  icon-stop"></i>正在加载</span> <span><i class="icon  icon-stop"></i>可预约</span> <span><i class="icon  icon-stop"></i>已选择</span> </p>
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
                <a id="course_5" href="javascript:;" class="unavailable">
                    <p>5:00</p>
                    <p>正在加载</p>
                    <p>100</p>
                    <p><i class="icon icon-ok"></i></p>
                </a> 
              </div>
            <div class="col-md-3 col-sm-3 col-xs-3"> <a id="course_6" href="javascript:;" class="unavailable">
              	<p>6:00</p>
              <p>正在加载</p>
              <p>100</p>
              <p><i class="icon icon-ok"></i></p>
              </a> </div>
            <div class="col-md-3 col-sm-3 col-xs-3"> <a id="course_7" href="javascript:;" class="unavailable">
              	<p>7:00</p>
              <p>正在加载</p>
              <p>100</p>
              <p><i class="icon icon-ok"></i></p>
              </a> </div>
            <div class="col-md-3 col-sm-3 col-xs-3"> <a id="course_8" href="javascript:;" class="unavailable">
              	<p>8:00</p>
              <p>正在加载</p>
              <p>100</p>
              <p><i class="icon icon-ok"></i></p>
              </a> </div>
            <div class="clearfix"></div>
          </div>
          <!--**************************************************-->
          <div class="course-date-list-group-bottom">
            <div class="col-md-3 col-sm-3 col-xs-3"> <a id="course_9" href="javascript:;" class="unavailable">
              	<p>9:00</p>
              <p>正在加载</p>
              <p>100</p>
              <p><i class="icon icon-ok"></i></p>
              </a> </div>
            <div class="col-md-3 col-sm-3 col-xs-3"> <a id="course_10" href="javascript:;" class="unavailable">
              	<p>10:00</p>
              <p>正在加载</p>
              <p>100</p>
              <p><i class="icon icon-ok"></i></p>
              </a> </div>
            <div class="col-md-3 col-sm-3 col-xs-3"> <a id="course_11" href="javascript:;" class="unavailable">
              	<p>11:00</p>
              <p>正在加载</p>
              <p>100</p>
              <p><i class="icon icon-ok"></i></p>
              </a> </div>
            <div class="col-md-3 col-sm-3 col-xs-3"> <a id="course_12" href="javascript:;" class="unavailable">
              <p>12:00</p>
              <p>正在加载</p>
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
            <div class="col-md-3 col-sm-3 col-xs-3"> <a id="course_13" href="javascript:;" class="unavailable">
              	<p>13:00</p>
              <p>正在加载</p>
              <p>100</p>
              <p><i class="icon icon-ok"></i></p>
              </a> </div>
            <div class="col-md-3 col-sm-3 col-xs-3"> <a id="course_14" href="javascript:;" class="unavailable">
              	<p>14:00</p>
              <p>正在加载</p>
              <p>100</p>
              <p><i class="icon icon-ok"></i></p>
              </a> </div>
            <div class="col-md-3 col-sm-3 col-xs-3"> <a id="course_15" href="javascript:;" class="unavailable">
              	<p>15:00</p>
              <p>正在加载</p>
              <p>100</p>
              <p><i class="icon icon-ok"></i></p>
              </a> </div>
            <div class="col-md-3 col-sm-3 col-xs-3"> <a id="course_16" href="javascript:;" class="unavailable">
              <p>16:00</p>
              <p>正在加载</p>
              <p>100</p>
              <p><i class="icon icon-ok"></i></p>
              </a> </div>
            <div class="clearfix"></div>
          </div>
          <!--**************************************************-->
          <div class="course-date-list-group-bottom">
            <div class="col-md-3 col-sm-3 col-xs-3"> <a id="course_17" href="javascript:;" class="unavailable">
			  <p>17:00</p>
              <p>正在加载</p>
              <p>100</p>
              <p><i class="icon icon-ok"></i></p>
              </a> </div>
            <div class="col-md-3 col-sm-3 col-xs-3"> <a id="course_18" href="javascript:;" class="unavailable">
              <p>18:00</p>
              <p>正在加载</p>
              <p>100</p>
              <p><i class="icon icon-ok"></i></p>
              </a> </div>
            <div class="col-md-3 col-sm-3 col-xs-3"> <a id="course_19" href="javascript:;" class="unavailable">
                	<p>19:00</p>
              <p>正在加载</p>
              <p>100</p>
              <p><i class="icon icon-ok"></i></p>
              </a> </div>
            <div class="col-md-3 col-sm-3 col-xs-3"> <a id="course_20" href="javascript:;" class="unavailable">
              <p>20:00</p>
              <p>正在加载</p>
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
            <div class="col-md-3 col-sm-3 col-xs-3"> <a id="course_21" href="javascript:;" class="unavailable">
              <p>21:00</p>
              <p>正在加载</p>
                    <p>100</p>
                    <p><i class="icon icon-ok"></i></p>

              </a> </div>
            <div class="col-md-3 col-sm-3 col-xs-3"> <a id="course_22" href="javascript:;" class="unavailable">
              <p>22:00</p>
              <p>正在加载</p>
                    <p>100</p>
                    <p><i class="icon icon-ok"></i></p>
   
              </a> </div>
            <div class="col-md-3 col-sm-3 col-xs-3"> <a id="course_23" href="javascript:;" class="unavailable">
              <p>23:00</p>
              <p>正在加载</p>
                    <p>100</p>
                    <p><i class="icon icon-ok"></i></p>

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
    <div class="col-md-4 col-sm-4 col-xs-4"> <a id="btn_order_confirm" href="orderconfirm.jsp">已选好</a> </div>
  </div>
</div>

<!--提示框：未点击确认上车 starts-->
<div class="overlay-tips">
	<div class="overlay-tips-inner">
    	<div class="container">
        	<div class="row">
            	<div class="col-md-12 col-sm-12 col-xs-12 content-tips">
                	<span><i class="icon icon-exclamation-sign"></i>连续上课会很累的哦，慎重考虑哦亲</span>
                </div>
            </div>
        </div>
    </div>
</div>
<!--提示框：未点击确认上车 ends-->

<div class="overlay-wait">
  <div class="overlay-wait-content">
  	<div class="text-center">
      <p>正在加载</p>
      <div class="loader1"> <span></span> <span></span> <span></span> <span></span> <span></span> </div>
    </div>
  </div>
</div>

<script src="js/jquery-1.8.3.min.js"></script>

<!--课程表的日期选择JS函数 starts-->
<script>
var coachid= <%=request.getParameter("coachid")%>;
var studentid= <%=request.getParameter("studentid")%>;

var date = new Date();


year = date.getFullYear();

month = date.getMonth();

date = date.getDate();


//月份天数
var DaysInMonth = new Array(31,28,31,30,31,30,31,31,30,31,30,31); 

var dd = new Date();
dd  = dd.valueOf()

var date  = dd  + 10 * 24 * 60 * 60 * 1000;

//30天后的日子
date  = new Date(date);

function InitDate()
{
  for(var i = 1;i <= 10;i++)
  {
	date = new Date();
	document.getElementById("custom" + i).innerHTML = GetDate(date,i - 1);
  }
}

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

var coach_detail;

function getCoachDetail(){
	var active_url= "../sbook?action=GETCOACHDETAIL";
	var search_condition={"coachid":coachid};
	if(parseInt(coachid)>0){
		$.getJSON(active_url,search_condition,function(data){
			coach_detail= data.coachinfo;

			var score_int= parseInt(data.coachinfo.score);
			score_int= score_int>5?5:score_int;
			var score_html = "";

			for(var i =0;i<score_int;i++){
				score_html+="<i class='icon icon-star'></i>";
		    };
		    
			$("#coach_detail").html("");
			$("#coach_detail").append(
					"<p><span>"+data.coachinfo.realname+
					"</span><span>"+
					score_html+
					"</span></p>"+
					"<p>"+data.coachinfo.detail+"</p>"
			);
			
		});
	}
}

/*将日期格式化成"yyyy-MM-dd"*/
function getFormattedDate(date_str) {
	var date= new Date();
	
	  var year = date.getFullYear();
	  var month = (1 + date.getMonth()).toString();
	  month = month.length > 1 ? month : '0' + month;
	  var day = date.getDate().toString();
	  day = day.length > 1 ? day : '0' + day;
	  
	  if(date_str.length>0){
			month= date_str.substring(0,date_str.indexOf("-"));
			if(month.length==1){
				month= "0"+month;
			}
			
			day= date_str.substr(date_str.indexOf("-")+1);
			if(day.length==1){
				day= "0"+day;
			}
		}
	  
	  return year + '-' + month + '-' + day;
}
	
var data_list_all;

function getSchedulByDate(event){
	show_loading();
	var str_date ="";
	
	if(event){
		str_date = event.children("p").children('span:nth-child(2)').children('i:first-child').html();
	}

	
	var query_date = getFormattedDate(str_date);
	var active_url= "../sbook?action=REFRESHCOACHSCHEDULE";
	
	var search_condition={"coachid":coachid,"studentid":studentid,"date":query_date};
	if(parseInt(coachid)>0 && studentid>0){
		$.getJSON(active_url,search_condition,function(data)
		{
			data_list_all= data.datelist;
		for(var i = 0 ;i<data.datelist.length;i++){
			var item = data.datelist[i];
			var str_hour=item.hour?item.hour:i+5;
			
			var course_html= $("#course_"+str_hour);
			
			course_html.attr("scheduleid",item.scheduleid);
			
			var sub = $("#course_"+str_hour+" p:nth-child(2)");
			var money = $("#course_"+str_hour+" p:nth-child(3)");
			var status=0;
			//var class_name = course_html.className;
			course_html.removeClass("active");//.className = class_name.replace(" active","");
			
			if(item.subject==null)
			{
				sub.html("");
			}else{
				sub.html(item.subject);
				if ("科目三"==item.subject)
				{
					sub.addClass('subjec3');
				}else{
					sub.removeClass('subjec3');
				}
			}
			if (item.isrest == 1) {
				course_html.removeClass('available');
				course_html.addClass('unavailable');
				sub.html("不可约");//休息
				status=0;
			} else if (item.isrest == 0) {
				if (item.isbooked == 1) {
					course_html.removeClass('available');
					course_html.removeClass('appointment');
					course_html.addClass('unavailable');
					sub.html("不可约");//教练已被别人预约
					status=0;
				} else if (item.isbooked == 2) {
					course_html.removeClass('available');
					course_html.addClass('unavailable');
					course_html.addClass('appointment');
					sub.html("已预约");//您已预约这个教练
					status=0;
				} else if (item.isbooked == 3) {
					course_html.removeClass('available');
					course_html.removeClass('appointment');
					course_html.addClass('unavailable');
					sub.html("已预约其他教练");//您已预约其他教练
					status=0;
				} else {
					course_html.removeClass('unavailable');
					course_html.removeClass('appointment');
					course_html.addClass('available');
					money.html(""+item.price + "元");
					status=1;
				}

				if (status== 1) {					
					// 判断是否过时
					if (item.pasttime == 1) {
						course_html.removeClass('available');
						course_html.removeClass('appointment');
						course_html.addClass('unavailable');
						sub.removeClass('subjec3');
						status=0;
					}else{
						// 正常状态，蓝色
						course_html.removeClass('unavailable');
						course_html.removeClass('appointment');
						course_html.addClass('available');
						course_html.attr("onclick","choose_course(this)");
					}
				} else if (status == 2) {
					// 选中状态，绿色
					course_html.addClass('active');
				} else {
					course_html.removeClass('available');
					course_html.removeClass('appointment');
					course_html.addClass('unavailable');
					money.removeClass('subjec3');
					if (item.isbooked == 2) {
						money.addClass('subjec3');
					}
					status==0;
				}
			}	
		}

		hide_loading();	
		});
	}
}

var counttime = 0;
var countmoney = 0;
var data_list_choose;
function choose_course(event)
{
	
	
	var scheduleid_str = "";
	var class_name = event.className;
	var hour_str="";
	var hout_int= 0;
	
	if(class_name.indexOf(" active")>0){
		event.className = class_name.replace(" active","");
	}else{
		if(counttime>5){
			$('.overlay-tips,.overlay-tips-inner').css('display','block');
			var height = $(document).height();
			var hh = (height-44)/2;
			
			$('.overlay-tips-inner').css('top',hh);
			setTimeout("hideTips()",1000) ;
			return;
		}else if(counttime>1){
			
			$('.overlay-tips,.overlay-tips-inner').css('display','block');
			var height = $(document).height();
			var hh = (height-44)/2;
			$('.overlay-tips-inner').css('top',hh);
			setTimeout("hideTips()",1000) ;
			
		}
		
		event.className +=" active";
	}
	
	counttime = 0;
	countmoney=0;
	data_list_choose= [];
	
	$('.course-date-list').find('a.active').each(function(index, element) {
		counttime++;
		$(".course-foot .col-md-8 p span:first-child").empty().html(counttime);
		var money;
		money = parseInt($(this).find('p:nth-child(3)').html());//parseInt($('.course-date-list .course-date-list-group .col-md-3 a p:nth-child(3)').html());
		countmoney += money;
		hour_str = $(this).children().first().html();
		hout_int= parseInt(hour_str.substring(0,hour_str.indexOf(":")));
		$(".course-foot .col-md-8 p span:last-child").empty().html(countmoney);
		scheduleid_str +=event.getAttribute("scheduleid")+",";
		data_list_choose[counttime-1]=data_list_all[hout_int-5];
	});

	

	//选中之后，付款的账单弹出
	$('.course-foot').slideDown();
	if (counttime < 1)
	{
		$('.course-foot').slideUp();
	}
	var order_url;
	
	order_url="orderconfirm1.jsp?data_list_choose="+ JSON.stringify(data_list_choose)+"&coachid="+coachid+"&realname="+coach_detail.realname+"&phone="+coach_detail.phone+"&studentid="+studentid+"&counttime="+counttime+"&countmoney="+countmoney;
	$("#btn_order_confirm").attr("href",order_url);
	
}

function timeTips()
{
	setTimeout("hideTips()",4000) // 这个是主要参数，设置消失的时间
	
}
function hideTips()
{
	$('.overlay-tips,.overlay-tips-inner').css('display','none');	
}

function show_loading(){
	$('.overlay-wait').css('display','block');
	var heightC = $('.overlay-wait-content').height();
	var height = $(window).height();
	var h = (height-heightC)/2;
	$('.overlay-wait-content').css('margin-top',h);
}

function hide_loading(){
	$('.overlay-wait').css('display','none');
}

$(document).ready(function()
{
	
	getCoachDetail();
	 getSchedulByDate();
	 
			var chooseddate;
			$('.course-choose-date ul.course-choose-ul li').on('click',function()
			{
				$(this).addClass('active').siblings().removeClass('active');
				getSchedulByDate($(this));
				
			});	

			var width = $(document).width();

			var ww = width-12;
			$('.course-choose-date-inner').css('width',ww);	
			
			var wwwww = $(window).width();
			
			var height_w = $(window).height();

			var hh = height_w -121;
			
			InitDate();
			 
			 var width = $(document).width();
			 $('.course-choose-date-inner').width(width);

});
</script>
</body>
</html>
