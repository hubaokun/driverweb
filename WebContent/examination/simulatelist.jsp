<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>模拟练习</title>
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="css/reset.css" rel="stylesheet" type="text/css" />
<link href="css/style.css" rel="stylesheet" type="text/css" />
<script src="js/jquery-1.8.3.min.js"></script>
<script src="js/popwindow.js"></script>
<script src="js/tool.js"></script>
<script src="js/main.js"></script>
<style type="text/css">

</style>
</head>

<body>
<div class="container">
	<div class="row empty-row"></div>
	<div class="row question-bar">
    	<div class="col-md-3 col-sm-3 col-xs-3">
        	<a href="javascript:void(0);" class="back" onclick="showquit ()"></a>
        </div>
		<div class="col-md-6 col-sm-6 col-xs-6"><span class="current-title"></span></div>
        <div class="col-md-3 col-sm-3 col-xs-3">
        	<a href="javascript:void(0)" class="board" onclick="showquestionboard ()"></a>
        </div>
    </div> 
	<div class="row question-col">

    </div>
    <div style="height:56px; width:100px;"></div>
    <div class="row footbtns">
    	<div class="footbtns-inner">
			
        </div>
    </div>
</div>

<!-- slide window starts-->
<div class="dialog">
	<div class="dialog-top">
    	<div class="begin">确定交卷</div>
        <div class="goon">您还有<span></span>题未作答，确定交卷吗？</div>
    </div>
    <div class="dialog-bottom">
    	<div class="cancel">取消</div>
        <div class="certain" onclick="handinpaper()">确定</div>
    </div>
</div>
<div class="overlay">
</div>
<!-- slide window ends-->

<!-- pop up window starts-->
<div class="dialog-first dialog-another">
	<div class="dialog-top">
    	<div class="begin">友情提示</div>
        <div class="goon"></div>
    </div>
    <div class="dialog-bottom">
        <div class="certain only-certain">确定</div>
    </div>
</div>
<div class="overlay-first">
</div>
<!-- pop up window ends-->

<!-- pop up window starts-->
<div class="dialog-abandon dialog-another">
	<div class="dialog-top">
    	<div class="begin">友情提示</div>
        <div class="goon">确定放弃本次模拟考试？</div>
    </div>
     <div class="dialog-bottom">
    	<div class="cancel no-abandon">取消</div>
        <div class="certain abandon">确定</div>
    </div>
</div>
<div class="overlay-abandon">
</div>
<!-- pop up window ends-->

<!-- pop up window starts-->
<div class="dialog-network dialog-another">
	<div class="dialog-top">
    	<div class="begin">友情提示</div>
        <div class="goon">请检查您的网络连接！</div>
    </div>
    <div class="dialog-bottom">
        <div class="network-certain">确定</div>
    </div>
</div>
<div class="overlay-network">
</div>
<!-- pop up window ends-->

<!--题板 starts-->
<div class="dialog-board">
	<div class="board-content">
    	<div class="board-inner">
        	<ul class="ul-board">
            	<!-- append the li by the for loop -->
            </ul>
        </div>
    </div>
</div>
<div class="overlay-board">
</div>
<!--题板 ends-->  


<script>
var off = true;
//JS 监听断网或者接入网络
window.addEventListener('load', function() {
function updateOnlineStatus(event) {
    var condition = navigator.onLine ? "true" : "false";
    if (condition == "true")
    {
    	off = true;
    	console.log ("网络连接上了！");
    }
    else
    {
    	off = false;
    	shownetwork ();
    }
}
window.addEventListener('online',  updateOnlineStatus);
window.addEventListener('offline', updateOnlineStatus);
});

var isopen = true;    		//控制“最佳解释”的开关——true:关闭；false：打开
var questionid = 0;	  		//记录加载到第几道题目,初始值为1
var pagenum = 0;      		//记录请求到第几页了  （一页的数据是20道题目）
var _data;           //记录20道题的全部信息
var scores = 0;           	//用户回答的分数
var examinationid;          //用于保存当前用户当前回答的模拟题的编号
var countquestions = 0;     //用于记录题数
var firsttimepage = true;   //用于标记是否是第一次请求pagenum为0的数据

var answerquestions = [];         //用于保存题目信息

var ifAnalog = true;

var timegot = getquerystring ("duration");
var type = getquerystring ("simulatetype");
var simulatetype = getquerystring("simulatetype");           //标识此次考试是科目一还是科目四的模拟考试
var maxtime = timegot*60;                                    //一个小时，按秒计算，自己调整! 

//var active_url = "http://120.25.236.228/dadmin/examination";
var active_url = "../examination";
var params = {action:"GETANALOGEXAMINATION",type:type,studentid:18,answerid:0,pagenum:0};
jQuery.post(active_url, params, showItems, 'json');

</script>

<script>
$(document).ready(function ()
{	
	timer = setInterval("countdown()",1000);
	
	loadQuestionBoard (type);
	
	//first question && last question
	var overlay_fir_last = $('.overlay-first');
	var btn_fir_last = $('.only-certain');
	overlay_fir_last.on('click',function ()
			{
				hidepopwindow ();
			});
	btn_fir_last.on('click',function ()
			{
				hidepopwindow ();
			});
	
	//question board
	var overlay_board = $('.overlay-board');
	overlay_board.on('click',function ()
			{
				hidequestionboard ();
			});

	//abandon examination
	var obj_no_abandon = $('.no-abandon');
	var obj_abandon = $('.abandon');
	var overlay_abandon = $('.overlay-abandon');
	
	obj_no_abandon.on('click',function()
			{
				hidequit ();
			});
	obj_abandon.on('click',function ()
			{
				window.location.href = "simulateingstarts.jsp?simulatetype=" + simulatetype;
			});
	overlay_abandon.on('click',function ()
			{
				hidequit ();
			});	
	
	//netowrk
	var overlay_network = $('.overlay-network');
	var btn_network = $('.network-certain');
	overlay_network.on('click',function ()
			{
				hidenetwork ();
			});
	btn_network.on('click',function ()
			{
				hidenetwork ();
			});	
	
	//TO DO: 加载标题信息
	var _obj_title = $('.current-title');
	if (type == 1)
	{
		_obj_title.html("").html("科目一模拟考试");
	}
	else if (type == 2)
	{
		_obj_title.html("").html("科目四模拟考试");
	}
});
</script>
</body>
</html>
