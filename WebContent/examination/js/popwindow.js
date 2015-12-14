/* Function:所有调用弹窗的JS脚本
*           弹窗的弹出方式分为两种，一种是pop，另一种的slide
*           pop全部都有用到，slide只在模拟考试的时候有用到
*  Author:Ada
*  Create:2015-11-24
*/

/*TO DO: 弹窗的显示和隐藏  
 * flag in showwindow () :判断是弹出式窗口还是滑出式窗口，true：弹窗式；false：滑出式
 */

//TO DO: 弹出式弹窗居中显示方法
function midwindow (win_obj)
{
	var window_W = $(window).width();
	var window_H = $(window).height();
	
	var width = win_obj.width();
	var height = win_obj.height();
	var w = (window_W - width)/2;
	var h = (window_H - height)/2;
	
	win_obj.css('top',h);
	win_obj.css('left',w);
}

function showwindow (win_obj,overlay_obj,flag)
{
	win_obj.css("display","block");
	overlay_obj.css("display","block");
	
	if (flag)
	{
		midwindow (win_obj);
	}
	else
	{
		return;
	}
}
function hidewindow (win_obj,overlay_obj)
{
	win_obj.css("display","none");
	overlay_obj.css("display","none");
}

//TO DO: “开始练习弹框”
function showrestart (lastquestion)
{
	var win_obj = $('.dialog-restart');
	var overlay_obj = $('.overlay-restart');
	
	showwindow (win_obj,overlay_obj,true);
	
	var obj_tips = $('.dialog-restart .goon');
	obj_tips.html("").html("上次练习到第" + lastquestion + "题，是否继续？");
}
function hiderestart ()
{
	var win_obj = $('.dialog-restart');
	var overlay_obj = $('.overlay-restart');
	hidewindow (win_obj,overlay_obj);
}

//TO DO: “检查网络状况弹框”
function shownetwork ()
{
	var win_obj = $(".dialog-network");
	var overlay_obj = $(".overlay-network");
	showwindow (win_obj,overlay_obj,true);
}
function hidenetwork ()
{
	var win_obj = $(".dialog-network");
	var overlay_obj = $(".overlay-network");
	hidewindow (win_obj,overlay_obj);
}

//TO DO: “检查当前是否是第一题”
function showpopwindowf ()
{
	var win_obj = $('.dialog-first');
	var overlay_obj = $('.overlay-first');
	
	var objcount = $('.dialog-first .goon');

	showwindow (win_obj,overlay_obj,true);
	
	objcount.html("").html("已经是第一题了哦~");
	//$('.pre').attr('onclick','showpopwindowf ()');  考虑是否真的要这么做？？
}

//TO DO: “检查当前是否是最后一题”
function showpopwindowl ()
{
	var win_obj = $('.dialog-first');
	var overlay_obj = $('.overlay-first');
	
	var objcount = $('.dialog-first .goon');

	showwindow (win_obj,overlay_obj,true);
	
	objcount.html("").html("已经是最后一题了哦~");
	//$('.next').attr('onclick','showpopwindowl ()');  考虑是否真的要这么做？？
}

function hidepopwindow ()
{
	var win_obj = $('.dialog-first');
	var overlay_obj = $('.overlay-first');
	
	hidewindow(win_obj,overlay_obj);
}

//TO DO: show the question board
function showquestionboard()
{
	var win_obj = $('.dialog-board');
	var overlay_obj = $('.overlay-board');
	
	showwindow (win_obj,overlay_obj,false);
}
function hidequestionboard()
{
	var win_obj = $('.dialog-board');
	var overlay_obj = $('.overlay-board');
	
	hidewindow(win_obj,overlay_obj);
}

//TO DO: “确认退出模拟考试弹框”
function showquit ()
{
	var obj = $('.dialog-abandon');
	var obj_overlay = $('.overlay-abandon');
	
	showwindow(obj,obj_overlay,true);
}
function hidequit ()
{
	var obj = $('.dialog-abandon');
	var obj_overlay = $('.overlay-abandon');
	
	hidewindow(obj,obj_overlay);
}

//TO DO: “确认提交考试滑框”
function showhandin ()
{
	if (off)
	{
		var obj = $('.dialog');
		var objoverlay = $('.overlay');
		var objcount = $('.goon span');
		var leftquestions;
		
		if (type == 1)
		{
			leftquestions = 100 - answerquestions.length;
		}
		else if (type == 2)
		{
			leftquestions = 50 - answerquestions.length;
		}
		
		obj.css('display','block');
		objoverlay.css('display','block');
		objcount.html("").html(leftquestions);
	}
	else
	{
		shownetwork ();
	}
}
function hidehandin ()
{
	var obj = $('.dialog');
	var objoverlay = $('.overlay');
	obj.css('display','none');
	objoverlay.css('display','none');
}