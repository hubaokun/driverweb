<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1" /><strong></strong>
<title>动画练习</title>
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="css/reset.css" rel="stylesheet" type="text/css" />
<link href="css/style.css" rel="stylesheet" type="text/css" />
<style type="text/css">

</style>
</head>

<body>
<div class="container">
	<div class="row question-col">
    	<div class="col-md-12 col-sm-12 colxs-12 question-title">
        	<p>1、在这种情况下怎样安全开车？</p>
            <img src="images/animation-1.png" class="img-responsive" />
        </div>
        <div class="col-md-12 col-sm-12 col-xs-12 question-item" trueanswer="2">
          	<a href="javascript:void(0);" onclick="chooseoption(this)" answer="1">
              <div class="item-state">A</div>
              <div class="item-con">违章行为</div>
            </a>
            <a href="javascript:void(0);" onclick="chooseoption(this)" answer="2">
              <div class="item-state">B</div>
              <div class="item-con">违法行为</div>
            </a>
            <a href="javascript:void(0);" onclick="chooseoption(this)" answer="3">
              <div class="item-state">C</div>
              <div class="item-con">过失行为</div>
            </a>
            <a href="javascript:void(0);" onclick="chooseoption(this)" answer="4">
              <div class="item-state">D</div>
              <div class="item-con">违规行为</div>
            </a>
        </div>
    </div>
    <div class="row best-explain">
    	<div class="col-md-12 col-sm-12 col-xs-12">
        	<h1>最佳解释</h1>
            <p>
            	《道路交通法》第一百零一条：违反道路交通安全法律、法规的规定，发生重大交通事故，构成犯罪的，依法追究心事责任，并由公安机关交通佛案例部门吊销驾驶证。驾驶证。
            </p>
        </div>
    </div>
    <div style="height:56px; width:100px;"></div>
    <div class="row footbtns">
    	<div class="footbtns-inner">
        	<a href="javascript:void(0);" class="collect">收藏本题</a>
        	<a href="javascript:void(0);" class="explain">本题解释</a>
        	<a href="javascript:void(0);" class="pre">上一题</a>
            <a href="javascript:void(0);" class="next">下一题</a>
        </div>
    </div>
</div>


<!-- pop up window starts-->
<div class="dialog">
	<div class="dialog-top">
    	<div class="begin">开始练习</div>
        <div class="goon">上次练习到第3题，是否继续？</div>
    </div>
    <div class="dialog-bottom">
    	<div class="cancel">取消</div>
        <div class="certain">确定</div>
    </div>
</div>
<div class="overlay">
</div>
<!-- pop up window ennds-->


<!--answer board starts-->

<!--answer board ends-->

<script src="js/jquery-1.8.3.min.js"></script>
<script>
function chooseoption(obj)
{
	$(obj).css('background-color','rgb(245,245,245)');
	var questions = document.querySelectorAll('.question-item a');
	var strT = $(obj).parent().attr('trueanswer');
	var str = $(obj).attr('answer');
	if (strT == str)
	{
		$(obj).addClass('item-right');
		for (var i=0;i<questions.length;i++)
		{
			questions.item(i).removeAttribute('onclick');
		}		
	}
	if (strT != str)
	{
		$(obj).addClass('item-wrong');
		$('.best-explain').css('display','block');
		for (var i=0;i<questions.length;i++)
		{
			if (questions.item(i).getAttribute('answer') == strT)
			{
				questions.item(i).className = 'item-right';
				break;
			}
		}
		//这边要怎么优化咧？？
		for (var i=0;i<questions.length;i++)
		{
			questions.item(i).removeAttribute('onclick');
		}
	}
}
</script>
<script>
$(document).ready(function ()
{
	$('.pre').on('click',function ()
	{
		$('.overlay').css('display','block');
		$('.dialog').css('display','block');
	});
});
</script>
</body>
</html>
