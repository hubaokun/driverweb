<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1" /><strong></strong>
<title>多选题练习</title>
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
        	<a href="index.jsp" class="back"></a>
        </div>
		<div class="col-md-6 col-sm-6 col-xs-6"><span class="current-title">科目四多选练习</span></div>
        <div class="col-md-3 col-sm-3 col-xs-3">
        	<a href="javascript:void(0)" class="board" onclick="showquestionboard ()"></a>
        </div>
    </div> 
	<div class="row question-col">

    </div>
    <div class="row best-explain">
    	<div class="col-md-12 col-sm-12 col-xs-12">            
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

<script src="js/jquery-1.8.3.min.js"></script>
<script>
var isopen = true;    		//控制“最佳解释”的开关——true:关闭；false：打开
var questionid = 1;	  		//记录加载到第几道题目,初始值为1
var prequestionrealid = 1;  //用于记录当前回答题目的上一题的questiono是多少
var initialquestionid;      //记录初始加载的题目ID是多少
var pagenum = 0;      		//记录请求到第几页了  （一页的数据是20道题目）
var questiondata;    		//记录20道题的全部信息
var countquestions = 0;     //用于记录题数

var answerquestions = [];           //用于保存题目信息

//TO DO: loading questions(20 unit) for the page for the first time
var active_url = "../examination";
var params = {action:"GETEXAMINATION",type:3,studentid:18,pagenum:pagenum};
jQuery.post(active_url, params, showItems, 'json');


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

	if (prequestionrealid == 0 || countquestions <= 0)
	{
		objcount.html("").html("已经是第一题了哦~");
	}
	else
	{
		objcount.html("").html("已经是最后一题了哦~");
		$('.next').attr('onclick','showpopwindow ()');
	}
}
function hidepopwindow ()
{
	var obj = $('.dialog-first');
	var obj_overlay = $('.overlay-first');
	
	hidewindow(obj,obj_overlay);
}

//TO DO: when load the page for the second time, show the dialog to ask whether the user want to restart?
function showrestartdialog (str)
{
	var obj = $('.dialog-restart');
	var obj_overlay = $('.overlay-restart');
	
	showwindow(obj,obj_overlay,true);
	
	var obj_tips = $('.dialog-restart .goon');
	obj_tips.html("").html("上次练习到第" + str + "题，是否继续？");
}
function hiderestartdialog ()
{
	var obj = $('.dialog-restart');
	var obj_overlay = $('.overlay-restart');
	
	hidewindow(obj,obj_overlay);
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
			break;
		}
	}
	return index;
}

//TO DO: clean all the structure for rewritting
function cleanStructure()
{
	var ancestorDiv1 = $('.question-col');
	ancestorDiv1.empty();
	
	var ancestorDiv2 = $('.best-explain');
	ancestorDiv2.children('div').empty();
	
	var ancestorDiv3 = $('.footbtns-inner');
	ancestorDiv3.empty();
}

//TO DO: change the statement and presentation of the "explain" button
function setbestexplain ()
{
	$('.explain').addClass('open-explain').html("关闭解释");
}
function resetbestexplain()
{
	$('.explain').removeClass('open-explain').html("本题解释");
}

//TO DO:judge whether the 'best-explain' is opening
function isshowExplain ()
{
	return $('.explain').hasClass('open-explain');
}

//TO DO: toggle the 'best-explain'
function togglebestexplain()
{
	var flag = isshowExplain ();
	if (flag)
	{
		changebuttonsstatement(true);
	}
	else
	{
		changebuttonsstatement(false);
	}
}

//TO DO: change the statements of the buttons
function changebuttonsstatement(isopen)
{
	var isblock = "block";
	if(!isopen)
	{
		setbestexplain ();
		isblock = "block";
	}
	else
	{
		resetbestexplain();
		isblock = "none";
	}
	$('.best-explain').css('display',isblock);
}

//TO DO: first load all the questions for the page
function showItems (data)
{
	questiondata = data;
	
	if (pagenum == 0)
	{
		initialquestionid = data.list[0].questionno;
	}

	loadQuestionTitle(data,questionid);
	
	loadQuestionItems (data,questionid);

	loadBestExplain(data,questionid);   
	
	loadFooterBtn ();
}

function showItems2(data)
{
	questiondata  = data;

	loadQuestionTitle (data,questionid);

	loadQuestionItems(data,questionid); 

	loadBestExplain(data,questionid);

	loadFooterBtn ();
}

//TO DO: load the question title
function loadQuestionTitle (data,questionid)
{
	cleanStructure();
	
	var ancestorDiv = $('.question-col');
	
	var parentDiv = $('<div></div>');
	parentDiv.addClass('col-md-12 col-sm-12 col-xs-12 question-title multi');

	var childP = $('<p></p>');	
	var tihao = countquestions;
	
	childP.html((tihao+1) + "、" +data.list[questionid-1].question);
	parentDiv.append(childP);
	
	//如果是有图片或者动画的，那么就要判断、加载
	if (data.list[questionid-1].animationflag)
	{
		var childImg = $('<img>');
		childImg.attr('src','../examination/images/' + data.list[questionid-1].animationimg);
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
	parentDiv.attr('questioncute',data.list[questionid-1].questionno);
	
	prequestionrealid = data.list[questionid-1].questionno;
	
	var childArrayA = new Array(size);
	
	for (var i=0;i<size;i++)
	{
		var strA = $('<a></a>');
		var strDivS = $('<div></div>');
		var strDivC = $('<div></div>');
		var num = 65 + i;
		
		childArrayA[i] = strA;
		childArrayA[i].attr('href','javascript:void(0)');
		childArrayA[i].attr('onclick','choosemuloption(this)');
		childArrayA[i].attr('answer',i+1);
		
		strDivS.addClass('item-state');
		strDivS.html(numToLetter(num));
		
		strDivC.addClass('item-con');
		strDivC.html(data.list[questionid-1].options[i]);
		
		childArrayA[i].append(strDivS);
		childArrayA[i].append(strDivC);
		
		parentDiv.append(childArrayA[i]);
	}

	var parentDivButton = $('<div></div>');
	parentDivButton.addClass('col-md-12 col-sm-12 col-xs-12 question-btn');
	
	var childButton = $('<button></button>');
	childButton.html("提交");
	childButton.attr('type','button');
	childButton.attr('id','btn_submitmul');
	childButton.attr('onclick','submitmuloption(this)');
	childButton.attr('disabled','disabled');
	parentDivButton.append(childButton);

	ancestorDiv.append(parentDiv);
	ancestorDiv.append(parentDivButton);	
	
	if (answerquestions.length > 0)
	{
		if (isInArray(answerquestions,questionon))
		{
			var index = returnindex(answerquestions,questionon);
			var arightindex = answerquestions[index][1];       //获得正确的选项 
			var awrongindex = answerquestions[index][2];       //获得用户选择的选项

			parentDiv.find("a").each(function ()
			{
				var _itemanswer = $(this).attr('answer');
				var _toaddclass = "";
				var _isitemright = arightindex.indexOf(_itemanswer) > -1;
				var _ismychoose = awrongindex.indexOf(_itemanswer) > -1;
				if (_isitemright) 
				{
					_toaddclass = _ismychoose ? "item-right": "item-no-wrong";
				}
				else 
				{
					_toaddclass = _ismychoose ? "item-wrong": "";
					changebuttonsstatement(false);
				}
				$(this).addClass(_toaddclass);
				$(this).attr("onclick", "");
			});
			
			parentDivButton.empty();
		}
	}
}

//TO DO:load best explain
function loadBestExplain(data,questionid)
{
	var questionno = $('.question-item').attr('questioncute');
	
	var ancestorDiv = $('.best-explain');
		
	var childP = $('<p></p>');
	var childH1 = $('<h1></h1>');
	
	childP.html(data.list[questionid-1].commentate); 
	childH1.html("最佳解释");
	
	ancestorDiv.children('div').append(childH1);
	ancestorDiv.children('div').append(childP);
	
	ancestorDiv.css('display','none');
	
	if (answerquestions.length > 0)
	{
		if (isInArray(answerquestions,questionno))
		{
			var index = returnindex(answerquestions,questionno);
			var arightindex = answerquestions[index][1];       //获得正确的选项 
			var awrongindex = answerquestions[index][2];       //获得用户选择的选项
			var isopen = answerquestions[index][3];

			if (arightindex == awrongindex)
			{
				changebuttonsstatement(isopen);
			}
			else
			{
				changebuttonsstatement(isopen);	
			}
		}
	}
}

//TO DO: load the footer's buttons
function loadFooterBtn ()
{
	var ancestorDiv = $('.footbtns-inner');
	
	//collect question
	var collectA = $('<a></a>');
	collectA.attr('href','javascript:void(0)');
	collectA.attr('onclick','collectcurrentquestion()');
	collectA.addClass('collect');
	collectA.html("收藏本题");
	
	//explain question
	var explainA = $('<a></a>');
	explainA.attr('href','javascript:void(0)');
	explainA.attr('onclick',"togglebestexplain()");
	explainA.addClass('explain');
	explainA.html("解释本题");
	
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
	
	ancestorDiv.append(collectA);
	ancestorDiv.append(explainA);
	ancestorDiv.append(preA);
	ancestorDiv.append(nextA);	
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
	var questioncute = _obj_pre.attr('questioncute');
	
	var question = new Array();
	
	question[0] = questioncute;
	question[1] = trueanswer;
	question[2] = myanswer;
	
	_obj_pre.find("a").each(function ()
	{
		var _itemanswer = $(this).attr('answer');
		var _toaddclass = "";
		var _isitemright = trueanswer.indexOf(_itemanswer) > -1;
		var _ismychoose = myanswer.indexOf(_itemanswer) > -1;
		if (_isitemright) 
		{
			 _toaddclass = _ismychoose ? "item-right": "item-no-wrong";
        }
		else 
		{
			_toaddclass = _ismychoose ? "item-wrong": "";
			changebuttonsstatement(false);
			question[3] = false;
        }
		$(this).addClass(_toaddclass);
		$(this).attr("onclick", "");
	});
	
	if (myanswer == trueanswer)
	{
		changebuttonsstatement(true);
		question[3] = true;
		
		setTimeout("loadNextQuestion (data)",1000); 
	}	
	
	answerquestions.push(question);
}

//TO DO: compare and show the result
function submitmuloption(obj)
{
	var _parent = $(obj).parent();

	var mychoose = getmulselects(_parent);
	
	showchooseresult(_parent,mychoose);
}

//TO DO: load the question board
function loadQuestionBoard ()
{
	var questionnum = 149;
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
	
	var _currentquestion = countquestions + 1;
	
	//本地存储：存储当前答题进行到哪一题了
	window.localStorage.setItem("currentquestion",_currentquestion);
	
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
	
	params = {action:"GETEXAMINATION",type:3,studentid:18,pagenum:pagenum};
	jQuery.post(active_url, params,showItems2, 'json'); 
	
	hidequestionboard();
}



//TO DO: load the ppre question
function loadPreQuestion (data)
{
	data = questiondata;
	
	--countquestions;
	
	var _currentquestion = countquestions + 1;

	window.localStorage.setItem("currentquestion",_currentquestion);
	
	
	var record = --questionid;
	
	if (record >= 1)
	{	
		loadQuestionTitle (data,questionid);
	
		loadQuestionItems(data,questionid); 	

		loadFooterBtn ();

		loadBestExplain(data,questionid);
	}
	else
	{
		questionid = 1;
	}
	
	if (pagenum == 0  && questionid == 1)
	{
		countquestions = 0;
		showpopwindow ();	
		return false;           //这里的意思是立即结束这个函数  
	}
	else if (pagenum >= 1)
	{
		if (((countquestions+1)%20 == 0)) 
		{
			pagenum--;
			
			questionid = 20;
			
			params = {action:"GETEXAMINATION",type:3,studentid:18,pagenum:pagenum};
			jQuery.post(active_url, params, showItems, 'json');
		}
	}
	

}

//TO DO: load the next question
function loadNextQuestion (data)
{
	data = questiondata;
	
	++countquestions;
	
	++questionid;
	
	var _currentquestion = countquestions + 1;
	
	//本地存储：存储当前答题进行到哪一题了
	window.localStorage.setItem("currentquestion",_currentquestion);
	
	if ((countquestions%20 == 0)  && (data.hasmore == 1))
	{
		pagenum++;

		questionid = 1;
		
		params = {action:"GETEXAMINATION",type:3,studentid:18,pagenum:pagenum};
		jQuery.post(active_url, params, showItems, 'json');
	}
	else if (countquestions == 149)
	{
		showpopwindow ();
		
		countquestions = 148;
		questionid--;
		
		return;
	}
	
	loadQuestionTitle (data,questionid);
	
	loadQuestionItems(data,questionid);	
	
	loadFooterBtn ();
	
	loadBestExplain(data,questionid);
}

//TO DO: change the statement and presentation of the "collect" button
function setcollect ()
{
	var collectobj = $('.collect');
	collectobj.addClass('collected').html("取消收藏");
	collectobj.removeAttr('onclick').attr('onclick','removecollectquestion()');
}
function resetcollect ()
{
	var collectobj = $('.collect');
	collectobj.removeClass('collected').html("收藏本题");
	collectobj.removeAttr('onclick').attr('onclick','collectcurrentquestion()');
}
	
//TO DO: collect questions
function collectcurrentquestion ()
{
	var studentid = 160;
	
	var questionid = $('.question-item').attr('questioncute');   //变量： 题目id号
	
	$.ajax({
		url: "../examination",
		data:
			{
			action:"ADDQUESTIONFAVORITES",
			studentid:studentid,
			questionno:questionid
			},
		type: 'post',
		dataType: 'json',
		success: function(data)
		{
			
			if(data.code==1)
			{
				alert("收藏成功!");
				setcollect ();
			}
			else
			{
				alert(data.message);
				alert ("收藏不成功");
			}				
		},
		error: function()
		{
			alert ("提交失败");
		}
	});
}

//TO DO:remove the collected question
function removecollectquestion ()
{
	var studentid = 160;
	var questionid = $('.question-item').attr('questioncute');   //变量： 题目id号
	
	$.ajax({
		url: "../examination",
		data:
			{
			action:"REMOVEQUESTIONFAVORITES",
			studentid:studentid,
			questionno:questionid
			},
		type: 'post',
		dataType: 'json',
		success: function(data)
		{	
			if(data.code==1)
			{
				alert("移除收藏成功!");
				resetcollect ();
			}
			else
			{
				alert(data.message);
				alert ("移除收藏不成功");
			}					
		},
		error: function()
		{
			alert ("提交失败");
		}
	});
}
</script>
<script>
$(document).ready(function ()
{
	loadQuestionBoard ();
	
	var _currentquestion_ = window.localStorage.getItem("currentquestion");
	
	if (_currentquestion_ != null)
	{
		showrestartdialog (_currentquestion_);
	}
	
	var obj_cancel = $('.only-certain');
	var objoverlay1 = $('.overlay-first');
	var objoverlay2 = $('.overlay-board');
	var btn_cancel = $('.start-cancel');
	var btn_certain = $('.start-certain');
	var overylay3 = $('.overlay-restart');
	
	obj_cancel.on('click',function ()
	{
		hidepopwindow ();
	});
	objoverlay1.on('click',function ()
	{
		hidepopwindow ();
	});	
	objoverlay1.on('click',function ()
	{
		hidepopwindow ();
	});	
	objoverlay2.on('click',function ()
	{
		hidequestionboard();
	});
	btn_certain.on('click',function ()
			{
				gotoquestion(_currentquestion_);
				hiderestartdialog ();
			});
	btn_cancel.on('click',function ()
			{
				hiderestartdialog ();
			});
	overylay3.on('click',function ()
			{
				hiderestartdialog ();
			});
});
</script>
</body>
</html>
