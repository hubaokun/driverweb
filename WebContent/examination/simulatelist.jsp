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
<style type="text/css">

</style>
</head>

<body>
<div class="container">
	<div class="row empty-row"></div>
	<div class="row question-bar">
    	<div class="col-md-3 col-sm-3 col-xs-3">
        	<a href="javascript:void(0);" class="back" onclick="showslidewindow ()"></a>
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

<!-- pop up window starts-->
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
<!-- pop up window ends-->

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

<script src="js/jquery-1.8.3.min.js"></script>
<script>
var isopen = true;    		//控制“最佳解释”的开关——true:关闭；false：打开
var questionid = 1;	  		//记录加载到第几道题目,初始值为1
var prequestionrealid = 1;  //用于记录当前回答题目的上一题的questiono是多少
var pagenum = 0;      		//记录请求到第几页了  （一页的数据是20道题目）
var questiondata;           //记录20道题的全部信息
var scores = 0;           	//用户回答的分数
var examinationid;          //用于保存当前用户当前回答的模拟题的编号
var countquestions = 0;     //用于记录题数
var firsttimepage = true;   //用于标记是否是第一次请求pagenum为0的数据

var answerquestions = [];         //用于保存题目信息

//TO DO:  从URL地址中获得参数
function getquerystring(name)
{
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);
     if(r!=null)return  unescape(r[2]); return null;
}

var timegot = getquerystring ("duration");
var type = getquerystring ("simulatetype");
var simulatetype = getquerystring("simulatetype");           //标识此次考试是科目一还是科目四的模拟考试
var maxtime = timegot*60;                                    //一个小时，按秒计算，自己调整! 

//TO DO: loading questions(20 unit) for the page for the first time
var active_url = "../examination";
var params = {action:"GETANALOGEXAMINATION",type:type,studentid:18,answerid:0,pagenum:0};
jQuery.post(active_url, params, showItems, 'json');

//TO DO: judge whether the simulate type is one or two
function judgesimulatetype ()
{
	if (simulatetype == 1)
	{
		return true;
	}
	else if (simulatetype == 2)
	{
		return false;
	}
	else
	{
		alert ("数据出错啦！");
	}		
}

//TO DO: change the number to letter
function numToLetter(num)
{
	var str = 'A';
	switch(num)
	{
	case 65:str = 'A';
			break;
	case 66:str = 'B';
			break;
	case 67:str = 'C';
			break;
	case 68:str = 'D';
			break;
	default:alert ("wrong,check!");
	}
	return str;
}

//TO DO: clean all the structure for rewritting
function cleanStructure()
{
	var ancestorDiv1 = $('.question-col');
	ancestorDiv1.empty();
	
	var ancestorDiv2 = $('.best-explain');
	ancestorDiv2.children('div').empty();
}

//TO DO: judge if the number is in the array
function isInArray(arr,num)
{
	var flag = true;
	for (var i=0;i<arr.length;i++)
	{
		if (arr[i][0] == num)
		{
			flag = true;
			break;
		}
		else
		{
			flag = false;
		}
	}
	return flag;
}

//TO DO: return the number  
function returnindex (arr,num)
{
	var index;
	for (var i=0;i<arr.length;i++)
	{
		if (arr[i][0] == num)
		{
			index = i;
			//alert (index);
			break;
		}
	}
	return index;
}

//TO DO: load the question board
function loadQuestionBoard ()
{
	var questionnum ;
	if (type == 1)
	{
		questionnum = 100;
	}
	else if (type == 2)
	{
		questionnum = 50;
	}
	else
	{
		alert ("数据出错啦!");
	}
	
	var parentul = $('ul.ul-board');
	
	for (var i=0;i<questionnum;i++)
	{
		var childli = $('<li></li>');
		var num = i+1;
		childli.addClass('bg-deep-grey');
		childli.attr('onclick',"gotoquestion("+ num +")");
		childli.html(num);
		parentul.append(childli);
	}
}

//TO DO: go to the appointed question
function gotoquestion (questionnum)
{
	countquestions = questionnum - 1;
	
	if (questionnum%20 == 0)
	{
		questionid = 20;
		pagenum = Math.floor(questionnum/20) - 1; 
	}
	else
	{
		questionid = questionnum%20;   //相当于questionid
		pagenum = Math.floor(questionnum/20); 
	}
	
	if (pagenum < 0 )
	{
		pagenum = 0;
	}
	
	params = {action:"GETANALOGEXAMINATION",type:type,studentid:18,answerid:examinationid,pagenum:pagenum};
	jQuery.post(active_url, params, showItems2, 'json');
	
	hidequestionboard();
}

//TO DO: funciton for showing and hiding pop up window
function showwindow(obj,obj_overlay,flag)
{
	var objoverlay = $('.overlay');
	
	obj.css('display','block');
	obj_overlay.css('display','block');
	
	if (flag)
	{
		var window_W = $(window).width();
		var window_H = $(window).height();
		
		var width = obj.width();
		var height = obj.height();
		var w = (window_W - width)/2;
		var h = (window_H - height)/2;
		
		obj.css('top',h);
		obj.css('left',w);
	}
	else
	{
		return false;
	}
}

function hidewindow(obj,obj_overlay)
{
	obj.css('display','none');
	obj_overlay.css('display','none');
}

//TO DO: show and hide the pop up window to ensure handing in the current paper
function showpopwindow ()
{
	var obj = $('.dialog-first');
	var obj_overlay = $('.overlay-first');

	var objcount = $('.dialog-first .goon');
	
	showwindow(obj,obj_overlay,true);

	if (questionid == 1 & pagenum == 0)
	{
		objcount.html("").html("已经是第一题了哦~");
	}
	else
	{
		objcount.html("").html("已经是最后一题了哦~交卷啦~");
		$('.next').attr('onclick','showpopwindow ()');
	}
}

function hidepopwindow ()
{
	var obj = $('.dialog-first');
	var obj_overlay = $('.overlay-first');
	
	hidewindow(obj,obj_overlay);
}

//TO DO: show and hide the slide up window to ensure handing in the current paper
function showslidewindow ()
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

function hideslidewindow ()
{
	var obj = $('.dialog');
	var objoverlay = $('.overlay');
	obj.css('display','none');
	objoverlay.css('display','none');
}

//TO DO: show the question board
function showquestionboard()
{
	var obj = $('.dialog-board');
	var obj_overlay = $('.overlay-board');
	
	showwindow (obj,obj_overlay,false);
}
function hidequestionboard()
{
	var obj = $('.dialog-board');
	var obj_overlay = $('.overlay-board');
	
	hidewindow(obj,obj_overlay);
}


//TO DO: show the choose result
function showchoosetrueresult(obj)
{
	obj.removeAttr('onclick').siblings().removeAttr('onclick');	
}

function showchoosewrongresult(obj,trueanswer)
{
	$(obj).removeAttr('onclick').siblings().removeAttr('onclick');	
}

//单选题和判断题的选择方式
function chooseoption(obj)
{	
	data = questiondata;
	
	$(obj).addClass('bg-grey');
	var strT = $(obj).parent().attr('trueanswer');                  //正确答案
	var str = $(obj).attr('answer');                                //用户选择的答案
	var questioncute = $('.question-title').attr('questioncute');   //变量： 题目id号
		
	//TO DO: ues an array to record the information of the current question
	var question = new Array(4);
	question[0] = questioncute;
	question[1] = strT;
	question[2] = str;
	question[4] = true;
	
	if (strT == str)
	{
		isopen = true;
		question[3] = isopen;
		
		if (judgesimulatetype ())
		{
			scores = scores + 1;
		}
		else
		{
			scores = scores + 2;
		}
		
		showchoosetrueresult($(obj));	
	}
	if (strT != str)
	{
		isopen = false;
		question[3] = isopen;
		
		scores = scores + 0;
			
		showchoosewrongresult($(obj),strT);
	}
	answerquestions.push(question);

	//当回答正确的时候，自动加载下一题的数据
	setTimeout("loadNextQuestion (data)",1000); 
}

//TO DO:get the selected choices
function getmulselects(obj)
{
	var mulselects = "";
	$(obj).parent().find('a').each(function()
	{
		if ($(this).hasClass('on'))
		{
			mulselects += $(this).attr('answer');
		}
	});
	return mulselects;
}

//多选题的选择方式
//TO DO: to identify which choise is been chosen
function choosemuloption(obj)
{
	if ($(obj).hasClass('on'))
	{
		$(obj).removeClass('on');
	}
	else
	{
		$(obj).addClass('on');
	}
	var myanswer = getmulselects(obj);
	
	var obj_submit = $('#btn_submitmul');
	if(myanswer.length > 1)
	{
		obj_submit.removeAttr("disabled");
	}
	else
	{
		obj_submit.attr("disabled","disabled");
	}
}

//TO DO: show the result
function showchooseresult(obj,myanswer)
{	
	data = questiondata;
	
	var _obj_pre = obj.prev();
	
	var trueanswer = _obj_pre.attr('trueanswer');
	var questioncute = _obj_pre.prev().attr('questioncute');
	
	var question = new Array();
	
	question[0] = questioncute;
	question[1] = trueanswer;
	question[2] = myanswer;
	question[4] = false;
	
	_obj_pre.find("a").each(function ()
	{
		var _itemanswer = $(this).attr('answer');
		var _toaddclass = "";
		var _isitemright = trueanswer.indexOf(_itemanswer) > -1;
		var _ismychoose = myanswer.indexOf(_itemanswer) > -1;
		if (_isitemright) 
		{
			// _toaddclass = _ismychoose ? "item-right": "item-no-wrong";
        }
		else 
		{
			//_toaddclass = _ismychoose ? "item-wrong": "";
			//changebuttonsstatement(false);
			question[3] = false;
        }
		$(this).attr("onclick", "");
	});
	
	if (myanswer == trueanswer)
	{
		changebuttonsstatement(true);
		question[3] = true;
	}	
	
	answerquestions.push(question);
	
	setTimeout("loadNextQuestion (data)",1000); 	
}

//TO DO: compare and show the result
function submitmuloption(obj)
{
	var _parent = $(obj).parent();

	var mychoose = getmulselects(_parent);
	
	showchooseresult(_parent,mychoose);
}

//TO DO: load questions for the page
function showItems2 (data)
{
	questiondata = data;
	
	examinationid = data.answerid;

	loadQuestionTitle (data,questionid);
	
	loadQuestionItems (data,questionid);
}

//TO DO: load questions for the page
function showItems (data)
{
	questiondata = data;
	
	examinationid = data.answerid;

	loadQuestionTitle (data,questionid);
	
	loadQuestionItems (data,questionid);

	if (pagenum == 0  && firsttimepage)
	{
		initialquestionid = data.list[0].questionno;
		
		loadFooterBtn ();
	}
}

//TO DO: load the question title
function loadQuestionTitle (data,questionid)
{
	cleanStructure();
	
	var ancestorDiv = $('.question-col');
	
	var parentDiv = $('<div></div>');
	parentDiv.addClass('col-md-12 col-sm-12 col-xs-12 question-title');
	
	var _addclass = "";
	
	if (data.list[questionid-1].questiontype == 1 || data.list[questionid-1].questiontype == 3)
	{
		_addclass = "single";
	}
	else if (data.list[questionid-1].questiontype == 2 || data.list[questionid-1].questiontype == 4)
	{
		_addclass = "if";
	}
	else if (data.list[questionid-1].questiontype == 5)
	{
		_addclass = "multi";
	}
	else
	{
		alert ("数据出错");
	}
	parentDiv.addClass(_addclass);
	parentDiv.attr('questioncute',data.list[questionid-1].questionno);
	prequestionrealid = data.list[questionid-1].questionno;

	var childP = $('<p></p>');	
	
	var tihao = countquestions;
	
	childP.html((tihao+1) + "、" + data.list[questionid-1].question);
	parentDiv.append(childP);

	
	//如果是有图片或者动画的，那么就要判断、加载
	if (data.list[questionid-1].animationimg != "")
	{
		var childImg = $('<img>');
		childImg.attr('src','../examination/img/' + data.list[questionid-1].animationimg);
		childImg.addClass('img-responsive');
		parentDiv.append(childImg);
	}
	else
	{
		console.log ("本题是没有动画或则图片的");
	}
	
	ancestorDiv.append(parentDiv);
}

//TO DO: load the question items
function loadQuestionItems (data,questionid) 
{
	var size = data.list[questionid-1].options.length;
	var questionon = data.list[questionid-1].questionno;
	
	var ancestorDiv = $('.question-col');
	
	var parentDiv = $('<div></div>');
	parentDiv.addClass('col-md-12 col-sm-12 col-xs-12 question-item');
	parentDiv.attr('trueanswer',data.list[questionid-1].answer);
	
	var childArrayA = new Array(size);
	
	for (var i=0;i<size;i++)
	{
		var strA = $('<a></a>');
		var strDivS = $('<div></div>');
		var strDivC = $('<div></div>');
		var num = 65 + i;
		
		childArrayA[i] = strA;
		childArrayA[i].attr('href','javascript:void(0)');		
		
		if (data.list[questionid-1].questiontype == 5)
		{
			childArrayA[i].attr('onclick','choosemuloption(this)');
		}
		else
		{
			childArrayA[i].attr('onclick','chooseoption(this)');	
		}
		
		childArrayA[i].attr('answer',i+1);
		
		strDivS.addClass('item-state');
		strDivS.html(numToLetter(num));
		
		strDivC.addClass('item-con');
		strDivC.html(data.list[questionid-1].options[i]);
		
		childArrayA[i].append(strDivS);
		childArrayA[i].append(strDivC);
		
		parentDiv.append(childArrayA[i]);
	}
	
	ancestorDiv.append(parentDiv);
	
	if (data.list[questionid-1].questiontype == 5)
	{
		var parentDivButton = $('<div></div>');
		parentDivButton.addClass('col-md-12 col-sm-12 col-xs-12 question-btn');
		
		var childButton = $('<button></button>');
		childButton.html("提交");
		childButton.attr('type','button');
		childButton.attr('id','btn_submitmul');
		childButton.attr('onclick','submitmuloption(this)');
		childButton.attr('disabled','disabled');
		parentDivButton.append(childButton);
		
		ancestorDiv.append(parentDivButton);
	}
	
	//根据标识，显示是否已经回答过本题，对于已经回答过的题目，需要清楚选项的点击事件
	if (answerquestions.length > 0)
	{
		if (isInArray(answerquestions,questionon))
		{
			var index = returnindex(answerquestions,questionon);
			var myanswerindex = answerquestions[index][2];
			var ismulti = answerquestions[index][4];
			
			if (ismulti)
			{
				parentDiv.find('a').each(function ()
				{
					if ($(this).attr('answer') == myanswerindex)
					{
						$(this).addClass('bg-grey');
					}
					$(this).attr('onclick','');
				});
			}
			else
			{
				parentDiv.find("a").each(function ()
				{
					var _itemanswer = $(this).attr('answer');
					var _ismychoose = myanswerindex.indexOf(_itemanswer) > -1;

					if (_ismychoose)
					{
						$(this).addClass('bg-grey');
					}
					
					$(this).attr("onclick", "");
				});
								
				parentDivButton.empty();
			}
		}
	}
}

//TO DO: load the footer's buttons
function loadFooterBtn ()
{
	var ancestorDiv = $('.footbtns-inner');
	
	//pre question
	var preA = $('<a></a>');
	preA.attr('href','javascript:void(0)');
	preA.attr('onclick','loadPreQuestion()');
	preA.addClass('pre');
	preA.html("上一题");
	
	//next question
	var nextA = $("<a></a>");
	nextA.attr('href','javascript:void(0)');
	nextA.attr('onclick','loadNextQuestion()');
	nextA.addClass('next');
	nextA.html("下一题");
	
	//collect question
	var timerA = $('<a></a>');
	timerA.attr('href','javascript:void(0)');
	timerA.addClass('timer');
	
	if (judgesimulatetype ())
	{
		timerA.html("45:00");
	}
	else
	{
		timerA.html("30:00");
	}
		
	//hand in question
	var handinA = $('<a></a>');
	handinA.attr('href','javascript:void(0)');
	handinA.addClass('handin');
	handinA.attr('onclick','showslidewindow ()');
	handinA.html("交卷");
	
	ancestorDiv.append(preA);
	ancestorDiv.append(nextA);	
	ancestorDiv.append(timerA);
	ancestorDiv.append(handinA);
}

//TO DO: load the ppre question
function loadPreQuestion (data)
{
	data = questiondata;
	
	var record = --questionid;
	
	--countquestions;
	
	if ((type == 1 && countquestions < 100) || (type == 2 && countquestions < 50))
	{
		$('.next').attr('onclick','loadNextQuestion()');
	}
	
	if (record >= 1)
	{	
		//TO DO: load question title 
		loadQuestionTitle (data,questionid);
	
		loadQuestionItems(data,questionid); 	
	}
	else
	{
		questionid = 1;
	}
	
	//先判断当前是否是第一题
	if (pagenum == 0  && questionid == 1 )
	{
		countquestions = 0;
		showpopwindow();	
		return false;           //这里的意思是立即结束这个函数  
	}
	else if (pagenum >= 1)
	{
		if (((countquestions+1)%20 == 0)) 
		{
			pagenum--;
			firsttimepage = false;
			
			questionid = 20;
			
			params = {action:"GETANALOGEXAMINATION",type:type,studentid:18,answerid:examinationid,pagenum:pagenum};
			jQuery.post(active_url, params, showItems, 'json');
			
			//countquestions = countquestions + 1;		
		}
	}
}

//TO DO: load the next question
function loadNextQuestion (data)
{
	data = questiondata;
	
	++countquestions;
	
	++questionid;
	
	if ((type == 1 && countquestions < 100) || (type == 2 && countquestions < 50))
	{
		if ((countquestions%20 == 0) && data.hasmore == 1)    //并且之后应该再加一个条件：就是hasmore要为1
		{
			pagenum++;
			
			questionid = 1;
			
			params = {action:"GETANALOGEXAMINATION",type:type,studentid:18,answerid:examinationid,pagenum:pagenum};
			jQuery.post(active_url, params, showItems, 'json');
		}
		
		loadQuestionTitle (data,questionid);
		
		loadQuestionItems(data,questionid);
	}
	else if ((type == 1 && countquestions == 100) || (type == 2 && countquestions == 50))
	{
		if (judgesimulatetype ())
		{
			countquestions = 99;
		}
		else
		{
			countquestions = 49;
		}
		questionid--;
		
		loadQuestionTitle (data,questionid);

		loadQuestionItems(data,questionid);
		
		showpopwindow();
		return;
	}
	else
	{
		showpopwindow();
		return;
	}
}

//TO DO: hand in the paper
function handinpaper ()
{
	var studentid = 18;
	var questionid = $('.question-title').attr('questioncute');   //变量： 题目id号
	var timeleft = $('.timer').html();
	
	var timecontinue = computetimediff (timegot + ":00",timeleft);
	
	answerquestions = (answerquestions.length > 0) ? answerquestions : false;
	
	$.ajax({
		url: "../examination",
		data:
			{
			action: "ADDANSWERRECORDINFO",
			studentid: studentid,
			analogflag: 1,
			score: scores,
			answerinfo: "" + answerquestions + ""
			},
		type: 'post',
		dataType: 'json',
		success: function(data)
		{
			if(data.code==1)
			{
				window.location.href = "answerreuslt.jsp?myscore=" + scores + "&simulatetype=" + simulatetype + "&timecontinue=" + timecontinue;
			}
			else
			{
				alert(data.message);
			}
						
		},
		error: function()
		{
			alert ("提交失败");
		}
	});
}
	
//time1为总的时间，time2为剩余时间
function computetimediff (time1,time2)
{
    if (time1 != "" && time2 != "")
    {
        var a = time1.split(":");
        var a1 = parseInt(a[0]);
        var a2 = parseInt(a[1]);

        var b = time2.split(":");
        var b1 = parseInt(b[0]);
        var b2 = parseInt(b[1]);

        var c;
        var d;
        
        if ((a2 - b2) > 0)
        {
        	c = a2 - b2;
        	if (c < 10)
        	{
        		c = "0" + c;
        	}
        	
        	d = a1 - b1;
        	if (d < 10)
        	{
        		d = "0" + d;
        	}
        }
        else if ((a2 - b2) == 0)
        {
        	c = "00";
        	
        	d = a1 - b1;
        	if (d < 10)
        	{
        		d = "0" + d;
        	}
        }
        else
        {
        	c = 60 - b2;
        	if (c < 10)
        	{
        		c = "0" + c;
        	}
        	
        	d = (a1 - b1) - 1;
        	if (d < 10)
        	{
        		d = "0" + d;
        	}
        }
    }
    else
    {
        alert ("数据出错啦！");
    }
    
    return d + ":" + c;
}

//TO DO: count down time
function countdown()
{  
	if(maxtime>=0)
	{  
		minutes = Math.floor(maxtime/60);  
		seconds = Math.floor(maxtime%60);  
			
		if (minutes <= 9)
		{
			minutes = '0' + minutes;
		}
		if (seconds <= 9)
		{
			seconds = '0' + seconds;
		}	
		msg = minutes+":"+seconds;  
		$('.timer').html("").html(msg);
		//if(maxtime == 5*60) alert('注意，还有5分钟!');  
		--maxtime;  	
    }  
    else
	{  
  		clearInterval(timer);  
  		alert("时间到，结束!");  
  	}  
}  
timer = setInterval("countdown()",1000); 

</script>

<script>
$(document).ready(function ()
{
	loadQuestionBoard ();
	
	var obj_only_cancel = $('.only-certain');
	var obj_cancel = $('.cancel');
	var objoverlay = $('.overlay');
	var obj_first_overlay = $('.overlay-first');
	var objoverlay2 = $('.overlay-board');
	
	var _obj_title = $('.current-title');
	if (type == 1)
	{
		_obj_title.html("").html("科目一模拟考试");
	}
	else if (type == 2)
	{
		_obj_title.html("").html("科目四模拟考试");
	}
	
	obj_only_cancel.on('click',function()
	{
		hidepopwindow ();
	});
	obj_cancel.on('click',function ()
	{
		hideslidewindow ();
	});
	objoverlay.on('click',function ()
			{
				hideslidewindow ();
			});	
	obj_first_overlay.on('click',function ()
	{
		hidepopwindow ();
	});
	objoverlay2.on('click',function ()
			{
				hidequestionboard();
			})
});
</script>
</body>
</html>
