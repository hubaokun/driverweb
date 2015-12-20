/* Function:通用的函数工具脚本代码
 * Author:Ada
 * Create:2015-11-24
 */

//TO DO: 判断一个数不为null也不为0,用来决定是否要在再次进入页面的时候，加载提示弹框
function ifShowRestart (num)
{
	if ((num != null) && (num != 0) && (num != undefined))
	{
		showrestart (num);
	}
	else
	{
		return false;
	}
}

//TO DO:  从URL地址中获得参数
function getquerystring(name)
{
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);
     if(r!=null)return  unescape(r[2]); return null;
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
	default:console.log ("不好意思，您的数据出错了！");
	        break;
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
			//alert (index);
			break;
		}
	}
	return index;
}

//TO DO: return the sum of all questions by type
function sumquestions (type)
{
	var totalrecord;
	
	if (ifAnalog)
	{
		switch (type)
		{
			case "1":totalrecord = 100;
				   break;
			case "2":totalrecord = 50;
				   break;
			default:totalrecord = 0;
					console.log ("不好意思，您的数据出错了！");
				    break;
		}
	}
	else
	{
		switch (type)
		{
			case "1":totalrecord = 1229;
				   break;
			case "2":totalrecord = 1094;
				   break;
			case "3":totalrecord = 149;
			       break;
			case "4":totalrecord = 23;
			       break;
			default:totalrecord = 0;
					console.log ("不好意思，您的数据出错了！");
				    break;
		}
	}
	
	return totalrecord;
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

//TO DO: clean all the structure for reloading the question
function cleanStructure()
{
	var ancestorDiv1 = $('.question-col');
	ancestorDiv1.empty();
	
	var ancestorDiv2 = $('.best-explain');
	ancestorDiv2.children('div').empty();
	
	if (!ifAnalog)
	{
		var ancestorDiv3 = $('.footbtns-inner');
		ancestorDiv3.empty();
	}	
}

//TO DO: judge the question type and add the related class
function addRelatedClass (type)
{
	var _addclass = "";
	switch (type)
	{
	case 1:_addclass = "single";
	         break;
	case 3:_addclass = "single";
	         break;
	case 2:_addclass = "if";
	         break;
	case 4:_addclass = "if";
	         break;
	case 5:_addclass = "multi";
	         break;
	default:_addclass = "single";
	        console.log ("不好意思，您的数据出错了！");
	        break;
	}
	return _addclass;
}

/*
 * TO DO: show the answer result
 * Parameter:
 * 			obj:当前点击对象----single:选项;multi:提交按钮
 * 			myanswer:用户选择的答案
 * 			flag:区别是单选还是多选-----true:single;false:multi
 */
function showchooseresult(obj,myanswer,flag)
{
	var _obj;
	var trueanswer;
	var questioncute;
	
	if (flag)
	{
		_obj = obj.parent ();
	}
	else
	{
		_obj = obj.prev ();
	}
	trueanswer = _obj.attr('trueanswer');
	questioncute = _obj.prev().attr('questioncute');
	
	var question = new Array();
	
	if (trueanswer == myanswer)
	{		
		switch (type)
		{
			case "1":scores  = scores + 1;
				break;
			case "2":scores  = scores + 2;
				break;
			default:console.log ("该题不是属于模拟题");
				break;
		} 
	}
	
	question[0] = questioncute;
	question[1] = trueanswer;
	question[2] = myanswer;
	question[4] = flag;     // 是用来标识存储进入数组的元素为多选题还是单选题等
	
	_obj.find("a").each(function ()
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
		
		if (ifAnalog)
		{
			setTimeout("loadNextQuestionA ()",1000);
		}
		else
		{
			setTimeout("loadNextQuestion ()",1000);
		}
	}	
	
	answerquestions.push(question);	
}

//单选题、判断题的选择判断方式
function chooseoption(obj)
{	
	$(obj).css('background-color','rgb(245,245,245)');
	var myanswer = $(obj).attr('answer');                           //用户选择的答案
	
	showchooseresult ($(obj),myanswer,true);
}

//多选题的选择方式
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

//TO DO: to identify which choice is been chosen
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

//TO DO: compare and show the result
function submitmuloption(obj)
{
	var _parent = $(obj).parent();

	var mychoose = getmulselects(_parent);
	
	showchooseresult(_parent,mychoose,false);
}


//TO DO: local storage,store the current questioncute
function setlocalstorage (type,currentquestion)
{
	switch (type)
	{
		case "1":window.localStorage.setItem("currentquestion1",currentquestion);
			   break;
		case "2":window.localStorage.setItem("currentquestion2",currentquestion);
			   break;
		case "3":window.localStorage.setItem("currentquestion3",currentquestion);
		       break;
		case "4":window.localStorage.setItem("currentquestion4",currentquestion);
		       break;
		default:totalrecord = 0;
				console.log ("不好意思，您的数据出错了！");
			    break;
	}
}

//TO DO: return local storage
function getlocalstorage (type)
{
	var currentquestion;
	switch (type)
	{
		case "1":currentquestion = window.localStorage.getItem("currentquestion1");
				 return currentquestion;
		case "2":currentquestion = window.localStorage.getItem("currentquestion2");
		         return currentquestion;
		case "3":currentquestion = window.localStorage.getItem("currentquestion3");
		 	     return currentquestion;
		case "4":currentquestion = window.localStorage.getItem("currentquestion4");
		 		 return currentquestion;
		default:currentquestion = 1;
				console.log ("不好意思，您的数据出错了！");
			    break;
	}
}

//TO DO: 在加载页面是需要首先调用的函数，用来初始化页面
function intialpage (type,obj_title)
{
	var _currentquestion;
	switch (type)
	{
		case "1":obj_title.html("").html("科目一顺序练习");
			     _currentquestion = getlocalstorage (type);
			     ifShowRestart (_currentquestion);
		         break;
		case "2":obj_title.html("").html("科目四顺序练习");
			     _currentquestion = getlocalstorage (type);
		         ifShowRestart (_currentquestion);
	             break;
		case "3":obj_title.html("").html("科目四多选练习");
		         _currentquestion = getlocalstorage (type);
		         ifShowRestart (_currentquestion);
	             break;
	    case "4":obj_title.html("").html("科目四动画练习");
		         _currentquestion = getlocalstorage (type);
	             ifShowRestart (_currentquestion);
                 break;
        default:console.log ("数据出错了:请检查本地存储和type值");
	}
}

//TO DO: 判断当前题目是否是最后一题
function ifLastQuestion (type,sum)
{
	if ((type == 1 && sum == 1229) || (type == 2 && sum == 1094) || (type == 3 && sum == 149) || (type == 4 && sum == 23))
	{
		return true;
	}
	else
	{
		return false;
	}
}

//TO DO: 判断是模拟几
function judgesimulatetype (type)
{
	if (type == 1)
	{
		return true;
	}
	else
	{
		return false;
	}
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

//TO DO:  reappear the former questions
function loadHistoryQuestion (_answerquestions,questionno,parentobj,type)
{
	if (_answerquestions.length > 0)
	{
		if (isInArray (_answerquestions,questionno))
		{
			var index = returnindex (_answerquestions,questionno)
			var arightindex = answerquestions[index][1];
			var awrongindex = answerquestions[index][2];
			var ismulti = answerquestions[index][4];

			parentobj.find("a").each(function ()
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
			if (type == 5)
			{
				var button_obj = parentobj.next();
				button_obj.empty ();
			}
		}
	}	
}

//TO DO: 计算考试用时
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













