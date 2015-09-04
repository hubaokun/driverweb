<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>登录</title>
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="css/login.css" rel="stylesheet" type="text/css" >
<style type="text/css"></style>
</head>

<body>
<div class="container">
  <div class="row logo-row">
    <div class="col-md-12 col-sm-12 col-xs-12"> <img src="images/new-logo.png" class="img-responsive center-block" /> </div>
  </div>
  <form>
    <div class="row form-row">
      <div class="col-md-12 col-sm-12 col-xs-12">
        <input type="text" placeholder="手机号" id="mobile" value="" class="center-block" onKeyUp="handle();" />
        <span class="un-active">发送验证码</span> </div>
      <div class="col-md-12 col-sm-12 col-xs-12  pull-right div-line"></div>
      <div class="col-md-12 col-sm-12 col-xs-12">
        <input type="text" placeholder="验证码" class="center-block" />
      </div>
    </div>
    <div class="row login-row">
      <div class="col-md-12 col-sm-12 col-xs-12"> <a href="coachlist.jsp">登录</a> </div>
    </div>
  </form>
</div>
<script src="js/jquery-1.8.3.min.js"></script> 
<script>

function handle()
{
	var v =  $('#mobile').val();
	if (v.match("^[0-9]*$"))
	{
	  if (v.length == 11)
	  {
		  $('.form-row span').addClass('active');
	  }
	  if ((v.length < 11) || (v.length > 11))
	  {
		  if ($('.form-row span').hasClass('active'))
		  {
			  $('.form-row span').removeClass('active').addClass('un-active');
		  }
	  }
	}
	
}
</script>
</body>
</html>
