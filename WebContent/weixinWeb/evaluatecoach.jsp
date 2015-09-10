<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>待评价教练</title>
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="css/font-awesome.css" rel="stylesheet" />
<link href="css/orderdetail.css" rel="stylesheet" type="text/css" />
<style type="text/css"></style>
</head>

<body>
<div class="container evaluate-content">
  <div class="row evaluate-content-head">
    <div class="col-md-12 col-sm-12 col-xs-12"> <span class="title">教学态度</span>
      <div id="score-attitude" data-score="5"></div>
      <span class="pull-right e-mark-a">5.0</span> </div>
    <div class="col-md-12 col-sm-12 col-xs-12"> <span class="title">教学质量</span>
      <div id="score-quality" data-score="5"></div>
      <span class="pull-right e-mark-q">5.0</span> </div>
    <div class="col-md-12 col-sm-12 col-xs-12"> <span class="title">技能掌握</span>
      <div id="score-control" data-score="5"></div>
      <span class="pull-right e-mark-c">5.0</span> </div>
  </div>
  <div class="row evaluate-content-body">
    <div class="col-md-12 col-sm-12 col-xs-12">
      <form>
        <textarea placeholder="快来评价您的教练吧"  class=" col-md-12 col-sm-12 col-xs-12"></textarea>
      </form>
    </div>
  </div>
  <div class="row evaluate-content-foot" >
    <div class="col-md-5 col-sm-5 col-xs-5 col-md-offset-7 col-sm-offset-7 col-xs-offset-7"> <span class="expressView">发表评价</span> </div>
  </div>
</div>


<div class="overlay" >
      <div class="overlay-content" style="top: 195px; left: 12px;">
        <div class="container">
            <div class="row">
              <div class="col-md-12 col-sm-12 col-xs-12">
                  <span>评价还未完成，您确定要离开？</span>
              </div>
              <div class="no col-md-6 col-sm-6 col-xs-6" style="border-right:1px solid rgb(218,218,218);"><span >取消</span></div>
              <div class="yes col-md-6 col-sm-6 col-xs-6"><span>确定</span></div>
              
        </div>
      </div>
  </div>	
</div>
<script src="js/jquery-1.8.3.min.js"></script> 
<script src="js/jquery.raty.min.js"></script> 
<script>
$(document).ready(function()
{
	
	var height = $(window).height();
	var width = $(window).width();
	$('.evaluate-content-foot span').on('click',function()
	{
		$('.overlay').css('display','block');
		var width1 = $('.overlay-content').width();
		var height1 = $('.overlay-content').height();
			//alert (height);
		var h = (height-height1)/2;
		var w = (width-width1)/2;
		$('.overlay-content').css('top',h);
		$('.overlay-content').css('left',w);
	})
	$('.yes,.no').on('click',function()
	{
		$('.overlay').css('display','none');
	})
	

	
	
	//星星插件
	$.fn.raty.defaults.path = 'js/img';
	$('#score-attitude').raty({
        score: function() {
          return $(this).attr('data-score');
        },
		click: function()
		{	
			var v = $('#score-attitude input[name="score"]').val();
			$('.e-mark-a').empty().html(v+".0");
		},
		size:24
      });
	  $('#score-quality').raty({
        score: function() {
          return $(this).attr('data-score');
        },
		click: function()
		{	
			var v = $('#score-quality input[name="score"]').val();
			$('.e-mark-q').empty().html(v+".0");
		},
		size:24
      });
	  $('#score-control').raty({
        score: function() {
          return $(this).attr('data-score');
        },
		click: function()
		{	
			var v = $('#score-control input[name="score"]').val();
			$('.e-mark-c').empty().html(v+".0");
		},
		size:24
      });
});
</script>
</body>
</html>
