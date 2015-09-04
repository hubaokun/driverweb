<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>学驾信息</title>
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="css/font-awesome.css" rel="stylesheet" />
<link href="css/basicinfo.css" rel="stylesheet" type="text/css" />
<style type="text/css">
</style>
</head>

<body>
<div class="container">
<form>
  <div class="row mylearninfo-head">
    <div class="col-md-12 col-sm-12 col-xs-12">
      <p><span>学员编号：</span><span><input type="text" placeholder="学员编号"/></span></p>
    </div>
    <div class="col-md-12 col-sm-12 col-xs-12">
      <p><span>身份证号：</span><span><input type="text" placeholder="身份证号"/></span></p>
    </div>
  </div>
  <div class="row mylearninfo-data">
    <div class="col-md-4 col-sm-4 col-xs-4">
     	准考证明：
    </div>
    <div class="col-md-6 col-sm-6 col-xs-6">
    	<img src="images/allowcard-icon.png" id="allowcard-img" class="img-responsive center-block" />
		<label for="allow_card_v" class="text-center" >上传</label>
        <input type="file" id="allow_card_v"/>
    </div>
  </div>
  <div class="row mylearninfo-data">
    <div class="col-md-4 col-sm-4 col-xs-4">
     	身份证：
    </div>
    <div class="col-md-6 col-sm-6 col-xs-6">
    	<img src="images/idcard-icon.png" class="img-responsive center-block" />
		<label for="id_card_v" class="text-center">上传</label>
        <input type="file" id="id_card_v" />
    </div>
  </div>
  <div class="row mylearninfo-submit">
  	<div class="col-md-12 col-sm-12 col-xs-12">
    	<span class="save-data">保存信息</span>
    </div>
  </div>  
</form> 
</div>


<div class="overlay">
      <div class="overlay-content">
        <div class="container">
            <div class="row">
              <div class="col-md-12 col-sm-12 col-xs-12">
                  <span>是否放弃资料的编写？</span>
              </div>
              <div class="no col-md-6 col-sm-6 col-xs-6" style="border-right:1px solid rgb(218,218,218);"><span class=" grey">否</span></div>
              <div class="yes col-md-6 col-sm-6 col-xs-6"><span class="blue">是</span></div>
              
        </div>
      </div>
  </div>	
</div>

<script src="js/jquery-1.8.3.min.js"></script>
<script>
$(document).ready(function ()
{
	$("#allow_card_v").change(function ()
	{
		var img_v = $(this).val();
		//alert (img_v);
		$("#allowcard-img").src(img_v);
	});
	
	$('.save-data').on('click',function()
	{
		$('.overlay').css('display','block');
	})
	$('.yes,.no').on('click',function()
	{
		$('.overlay').css('display','none');
	})
	
	var height = $(window).height();
	var width = $(window).width();
	//alert (height);
	var h = (height-90)/2;
	var w = (width-296)/2;
	$('.overlay-content').css('top',h);
	$('.overlay-content').css('left',w);
	
});
</script>
</body>
</html>
