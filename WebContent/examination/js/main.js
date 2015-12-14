/* Function:公共的要找的代码
 * Author:Ada
 * Create:2015-11-24
 */

/*
 * load函数
 * 
 */

var countquestions = 0;     //用于记录题数
var questionid = 0;         //用于记录题号（一页20道题的数据）
var answerquestions = [];   //用于保存题目信息
var totalrecord;            //用于保存题目总数
var isopen = true;    		//控制“最佳解释”的开关——true:关闭；false：打开
var pagenum = 0;      		//记录当前请求的页数

//TO DO: load the initial questions,so the questionid will always be 1
function showItems(data)
{
	_data = data;
	
	loadQuestionTitle ();

	loadQuestionItems();  	

	loadBestExplain();    

	//如果是模拟考试，则“底部导航栏”只加载一次；如果是练习考试，则“底部导航栏”会根据加载题目多次重写加载
	if (ifAnalog)
	{
		if (pagenum == 0  && firsttimepage)
		{
			loadFooterBtn ();
		}
		
		examinationid = data.answerid;
		firsttimepage = false;
	}
	else
	{
		loadFooterBtn ();
	}
	
}

//TO DO: load the question title
function loadQuestionTitle ()
{
	cleanStructure();
	
	var _addclass = "";
	var _type = _data.list[questionid].questiontype;
	var ancestorDiv = $('.question-col');
	var parentDiv = $('<div></div>');
	parentDiv.addClass('col-md-12 col-sm-12 col-xs-12 question-title');
	
	if (type == 4) //这个type是直接从url中拿到的那个type,它的数据类型是string，而不是number
	{
		_addclass = "animation";
	}
	else
	{
		_addclass = addRelatedClass(_type);
	}
	
	parentDiv.addClass(_addclass);
	
	parentDiv.attr('questioncute',_data.list[questionid].questionno);

	var childP = $('<p></p>');	
	var tihao = countquestions;
	
	childP.html((tihao+1) + "、" +_data.list[questionid].question);
	parentDiv.append(childP);
	
	//如果题目是包含图片的，那么就加载出来
	if (_data.list[questionid].animationimg != "")
	{
		var childImg = $('<img>');
		childImg.attr('src','../examination/img/' + _data.list[questionid].animationimg);
		childImg.addClass('img-responsive');
		parentDiv.append(childImg);
	}
	else
	{
		console.log ("本题没有图片");
	}	
	
	ancestorDiv.append(parentDiv);
}

//TO DO: load the question items
function loadQuestionItems () 
{
	var size = _data.list[questionid].options.length;
	var questionno = _data.list[questionid].questionno;
	var _type = _data.list[questionid].questiontype;
	
	var ancestorDiv = $('.question-col');
	
	var parentDiv = $('<div></div>');
	parentDiv.addClass('col-md-12 col-sm-12 col-xs-12 question-item');
	parentDiv.attr('trueanswer',_data.list[questionid].answer);
	
	var childArrayA = new Array(size);
	
	for (var i=0;i<size;i++)
	{
		var strA = $('<a></a>');
		var strDivS = $('<div></div>');
		var strDivC = $('<div></div>');
		var num = 65 + i;
		
		childArrayA[i] = strA;
		childArrayA[i].attr('href','javascript:void(0)');
		
		if (_type == 5)
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
		strDivC.html(_data.list[questionid].options[i]);
		
		childArrayA[i].append(strDivS);
		childArrayA[i].append(strDivC);
		
		parentDiv.append(childArrayA[i]);
	}
	
	ancestorDiv.append(parentDiv);

	if ( _type == 5)
	{
		loadQuestionsButton (ancestorDiv);
	}
		
	loadHistoryQuestion (answerquestions,questionno,parentDiv,_type);
}

//TO DO: load the submit button in multi questions
function loadQuestionsButton (parent_obj)
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
	
	parent_obj.append(parentDivButton);
}

//TO DO:load best explain
function loadBestExplain()
{
	var questionno = $('.question-item').attr('questioncute') || $('.question-title').attr('questioncute');
	
	var ancestorDiv = $('.best-explain');
		
	var childP = $('<p></p>');
	var childH1 = $('<h1></h1>');
	
	childP.html(_data.list[questionid].commentate); 
	childH1.html("最佳解释");
	
	ancestorDiv.children('div').append(childH1);
	ancestorDiv.children('div').append(childP);
	
	ancestorDiv.css('display','none');
		
	//这段代码最好需要优化！！！
	if (answerquestions.length > 0)
	{
		if (isInArray(answerquestions,questionno))
		{
			var index = returnindex(answerquestions,questionno);
			var arightindex = answerquestions[index][1];
			var awrongindex = answerquestions[index][2];
			var isopen = answerquestions[index][3];

			if (answerquestions[index][1] == answerquestions[index][2])
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
/* 	var collectA = $('<a></a>');
	collectA.attr('href','javascript:void(0)');
	collectA.attr('onclick','collectcurrentquestion()');
	//collectA.bind('click','collectcurremtquestion()');
	collectA.addClass('collect');
	collectA.html("收藏本题"); 
	
	//alert (questiondata.list[questionid-1].isfavorites);
 	if (questiondata.list[questionid-1].isfavorites == 1)
	{
		//alert ("此题已经收藏过了")
		collectA.addClass('collected').html("取消收藏");
		collectA.removeAttr('onclick').attr('onclick','removecollectquestion()');
	}
	else
	{
	} */
	
	//question board
	var boardA = $('<a></a>');
	boardA.attr('href','javascript:void(0)');
	boardA.attr('onclick','showquestionboard()');
	boardA.addClass('board');
	
	var currentquestion = countquestions + 1;
		
	boardA.html("" + currentquestion + "/" + totalrecord);
	
	//explain question
	var explainA = $('<a></a>');
	explainA.attr('href','javascript:void(0)');
	explainA.attr('onclick',"togglebestexplain()");
	explainA.addClass('explain');
	explainA.html("解释本题");
	
	//pre question
	var preA = $('<a></a>');
	preA.attr('href','javascript:void(0)');
	if (ifAnalog)
	{
		preA.attr('onclick','loadPreQuestionA()');
	}
	else
	{
		preA.attr('onclick','loadPreQuestion()');
	}
	
	preA.addClass('pre');
	preA.html("上一题");
	
	//next question
	var nextA = $("<a></a>");
	nextA.attr('href','javascript:void(0)');
	
	if (ifAnalog)
	{
		nextA.attr('onclick','loadNextQuestionA()');
	}
	else
	{
		nextA.attr('onclick','loadNextQuestion()');
	}
	nextA.addClass('next');
	nextA.html("下一题");
	
	//timing examnation
	var timerA = $('<a></a>');
	timerA.attr('href','javascript:void(0)');
	timerA.addClass('timer');
	
	if (judgesimulatetype (type))
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
	handinA.attr('onclick','showhandin ()');
	handinA.html("交卷");
	
	if (ifAnalog)
	{
		ancestorDiv.append(preA);
		ancestorDiv.append(nextA);	
		ancestorDiv.append(timerA);
		ancestorDiv.append(handinA);
	}
	else
	{
		ancestorDiv.append(boardA);
		ancestorDiv.append(explainA);
		ancestorDiv.append(preA);
		ancestorDiv.append(nextA);
	}		
}

//TO DO: load the question board 其实加载答题板的主要问题是要根据某个条件拿到进行的练习或是模拟考试的题目总数
function loadQuestionBoard (type)
{
	var questionnum;
	var _type = type;
	
	questionnum = sumquestions (_type); 
	
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

/*
 * 针对科目练习
 * 上一题(pre)，下一题(next)，跳转到某一题(goto)
 */

//TO DO: go to the previous question
function loadPreQuestion ()
{
	//TO DO: 将“最佳解释”恢复成默认设置	
	isopen = true; 
	--countquestions;
	--questionid;
	
	//TO DO: 调用本地存储，将当前题号实时存入本地
	var _currentquestion = countquestions + 1;
	setlocalstorage (type,_currentquestion); 
	
	//点击上一题，首先判断是否是第一题；如果是第一题的话，就结束函数
	if (pagenum == 0  && countquestions == -1)
	{
		showpopwindowf ();
		
		//将全局变量复位
		countquestions = 0;
		questionid = 0;
		
		return;
	}
	else
	{
		//再者判断是否需要请求新一页的数据
		if ((countquestions+1)%20 == 0)
		{
			if (off)
			{
				pagenum--;

				questionid = 19;
				
				params = {action:"GETEXAMINATION",type:type,studentid:18,pagenum:pagenum};
				jQuery.post(active_url, params, showItems, 'json');
			}
			else
			{
				shownetwork ();
				
				countquestions++;
				
				return ;
			}
		}
		else
		{
			loadQuestionTitle ();
			loadQuestionItems(); 	
			loadFooterBtn ();
			//loadBestExplain();
		}
	}
}

//TO DO: goto the next question
function loadNextQuestion ()
{
	//TO DO: 将“最佳解释”恢复成默认设置	
	isopen = true; 
	
	++countquestions;
	++questionid;
	
	//TO DO: 调用本地存储，将当前题号实时存入本地
	var _currentquestion = countquestions + 1;
	setlocalstorage (type,_currentquestion); 
	
	//点击下一题，首先判断是否是最后一题，如果是最后一题的话，结束函数
	if ((type == 1 && countquestions == 1229) || (type == 2 && countquestions == 1094) || (type == 3 && countquestions == 149) || (type == 4 && countquestions == 23))
	{
		showpopwindowl ();
		
		if (type == 1)
		{
			countquestions = 1228;
		}
		else if (type == 2)
		{
			countquestions = 1093;
		}
		else if (type == 3)
		{
			countquestions = 148;
		}
		else if (type == 4)
		{
			countquestions = 22;
		}
		else
		{
			console.log ("数据出错啦：请检查type的值！");
		}
		
		questionid--;
		return;
	}
	else
	{
		//再者判断是否需要通过请求下一页数据来加载试题；满足条件的话，就向后台请求数据
		if ((countquestions%20 == 0)  && (_data.hasmore == 1))
		{
			//alert ("ask for new page")
			if (off)
			{			
				pagenum++;
				
				questionid = 0;
				
				params = {action:"GETEXAMINATION",type:type,studentid:18,pagenum:pagenum};
				jQuery.post(active_url, params, showItems, 'json');
			}
			else   //这段未做过测试
			{
				shownetwork ();
				
				countquestions--;
				
				questionid--;
				
				return ;
			}
		}
		else
		{
			loadQuestionTitle ()		
			loadQuestionItems(); 		
			loadFooterBtn ();
			loadBestExplain();
		}
	}
}

/*
 * 针对模拟考试
 */
function loadPreQuestionA ()
{
	//TO DO: 将“最佳解释”恢复成默认设置	
	isopen = true; 
	--countquestions;
	--questionid;
	
	//点击上一题，首先判断是否是第一题；如果是第一题的话，就结束函数
	if (pagenum == 0  && countquestions == -1)
	{
		showpopwindowf ();
		
		//将全局变量复位
		countquestions = 0;
		questionid = 0;
		
		return;
	}
	else
	{
		//再者判断是否需要请求新一页的数据
		if ((countquestions+1)%20 == 0)
		{
			if (off)
			{
				pagenum--;

				questionid = 19;
				
				params = {action:"GETANALOGEXAMINATION",type:type,studentid:18,answerid:examinationid,pagenum:pagenum};
				jQuery.post(active_url, params, showItems, 'json');
			}
			else
			{
				shownetwork ();
				
				countquestions++;
				
				return ;
			}
		}
		else
		{
			loadQuestionTitle ();
			loadQuestionItems(); 	
			//loadBestExplain();
		}
	}
}

function loadNextQuestionA ()
{
	//TO DO: 将“最佳解释”恢复成默认设置	
	isopen = true; 
	
	++countquestions;
	++questionid;
	
	//点击下一题，首先判断是否是最后一题，如果是最后一题的话，结束函数
	if ((type == 1 && countquestions == 100) || (type == 2 && countquestions == 50))
	{
		showpopwindowl ();
		
		if (type == 1)
		{
			countquestions = 99;
		}
		else if (type == 2)
		{
			countquestions = 49;
		}
		else
		{
			console.log ("数据出错啦：请检查type的值！");
		}
		
		questionid--;
		return;
	}
	else
	{
		//再者判断是否需要通过请求下一页数据来加载试题；满足条件的话，就向后台请求数据
		if ((countquestions%20 == 0)  && (_data.hasmore == 1))
		{
			//alert ("ask for new page")
			if (off)
			{			
				pagenum++;
				
				questionid = 0;
				
				params = {action:"GETANALOGEXAMINATION",type:type,studentid:18,answerid:examinationid,pagenum:pagenum};
				jQuery.post(active_url, params, showItems, 'json');
			}
			else   //这段未做过测试
			{
				shownetwork ();
				
				countquestions--;
				
				questionid--;
				
				return ;
			}
		}
		else
		{
			loadQuestionTitle ()		
			loadQuestionItems(); 		
			//loadBestExplain();
		}
	}
}


//TO DO: go to the appointed question
function gotoquestion (questionnum)
{
	if (off)
	{
		//TO DO: 调用本地存储，将当前题号实时存入本地
		var _currentquestion = questionnum;
		setlocalstorage (type,_currentquestion);
		
		countquestions = questionnum - 1;  //因为questionnum是从1开始的		 
		
		//获得用户点击的题目“所处数据片”中的位置
		if (questionnum%20 == 0)
		{
			questionid = 19;
			pagenum = Math.floor(questionnum/20) - 1; 
		}
		else
		{
			questionid = questionnum%20 - 1;    //题号
			pagenum = Math.floor(questionnum/20);    //页数 
		}
			
		if (pagenum < 0 )
		{
			pagenum = 0;
		}
		
		if (ifAnalog)
		{
			params = {action:"GETANALOGEXAMINATION",type:type,studentid:18,answerid:examinationid,pagenum:pagenum};
			jQuery.post(active_url, params, showItems, 'json');
		}
		else
		{
			params = {action:"GETEXAMINATION",type:type,studentid:18,pagenum:pagenum};
			jQuery.post(active_url, params,showItems, 'json'); 
		}
	}
	else 
	{
		shownetwork ();
	}
	
	hidequestionboard();
}

/*
 * Hand in paper
 */

//TO DO: hand in the paper
function handinpaper ()
{
	var studentid = 18;
	var questionid = $('.question-title').attr('questioncute');   //变量： 题目id号
	var timeleft = $('.timer').html();
	
	var timecontinue = computetimediff (timegot + ":00",timeleft);
	
	answerquestions = (answerquestions.length > 0) ? answerquestions : false;
	
	if (off)
	{
		$.ajax({
			url: " ../examination",
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
	else
	{
		console.log ("由于网络未连接，所以没有办法提交答案");
	}
}

/*
 * collect (暂时不启用)
 */
//TO DO: collect questions
function collectcurrentquestion ()
{
	var studentid = 18;
	
	var questionid = $('.question-title').attr('questioncute');   //变量： 题目id号
	
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
	var studentid = 18;
	var questionid = $('.question-title').attr('questioncute');   //变量： 题目id号
	
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