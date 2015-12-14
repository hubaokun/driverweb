<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>顺序练习</title>
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
        	<a href="index.jsp" class="back"></a>
        </div>
		<div class="col-md-6 col-sm-6 col-xs-6"><span class="current-title">科目一顺序练习</span></div>
        <div class="col-md-3 col-sm-3 col-xs-3">
        	<!-- <a href="javascript:void(0)" class="board" onclick="showquestionboard ()"></a> -->
        </div>
    </div>   
	<div class="row question-col">
    	
    </div>
    <div class="row best-explain">
    	<div class="col-md-12 col-sm-12 col-xs-12">
        	<h1>最佳解释</h1>
        </div>
    </div>
    <div style="height:56px; width:100px;"></div>
    <div class="row footbtns">
    	<div class="footbtns-inner">
        	
        </div>
    </div>
</div>

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

<!-- pop up window starts-->
<div class="dialog-another dialog-restart">
	<div class="dialog-top">
    	<div class="begin">开始练习</div>
        <div class="goon"></div>
    </div>
    <div class="dialog-bottom">
    	<div class="cancel start-cancel">取消</div>
        <div class="certain start-certain">确定</div>
    </div>
</div>
<div class="overlay-restart">
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

var type = "";              //传递过来的题目类型
var _data;      		    //记录请全部要道题的全部信息
var ifAnalog = false;       //用于标识是否是模拟考试

type = getquerystring("passingtype");
totalrecord = sumquestions(type);

//TO DO: 第一次请求数据
var active_url = "../examination";
var params = {action:"GETEXAMINATION",type:type,studentid:18,pagenum:0};
jQuery.post(active_url, params,showItems, 'json'); 

</script>
<script>
$(document).ready(function ()
{
	var _obj_title = $('.current-title');
	var _currentquestion_ = getlocalstorage (type);
	intialpage (type,_obj_title);
	
	loadQuestionBoard (type);	
	
	//question board
	var overlay_board = $('.overlay-board');
	overlay_board.on('click',function ()
			{
				hidequestionboard ();
			});

	//restart
	var btn_cancel = $('.start-cancel');
	var btn_certain = $('.start-certain');
	var overlay_restart = $('.overlay-restart');
	btn_certain.on('click',function ()
			{
				gotoquestion(_currentquestion_);
				hiderestart ();
			});
	
	btn_cancel.on('click',function ()
			{
				hiderestart ();
			});
	overlay_restart.on('click',function ()
			{
				hiderestart ();
			});

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
});
</script>
</body>
</html>























