<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="css/login.css" type="text/css" media="screen" />
<script src="js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="js/jquery.cookie.js"></script>
<script>
$(function(){
	var s=document.documentElement.clientHeight;
	$(".bg").height(s);
});

function login(){
	$("#loginform").attr("action","login.do").submit();
}
$(function(){
	var remembername = $.cookie('remembername')?$.cookie('remembername'):"";
// 	alert(remembername);
	if(remembername==1){
	var cookieloginName = $.cookie('loginname')?$.cookie('loginname'):"";
	$("#loginname").val(cookieloginName);
	$("#remembername").each(function(){
		   $(this).attr("checked",true);
		  });  
	}else{
		$("#loginname").val("");
	}
	var rememberpw = $.cookie('rememberpw')?$.cookie('rememberpw'):"";
	if(rememberpw==1){
	var cookiebgPwd = $.cookie('password')?$.cookie('password'):"";
	$("#password").val(cookiebgPwd);
	$("#rememberpw").each(function(){
		   $(this).attr("checked",true);
		  });  
	}else{
		$("#password").val("");
	}
	
});

$(document).keydown(function(event){ 
	if(event.keyCode == 13){
	        $("#login").click();
	}
	}); 
</script>
<title>登录</title>
</head>
<body >
    <div style="background-image:url(imgs/logon.png);background-repeat: no-repeat; background-size: 100% 100%; min-height: 280px; min-width: 700px;"> 
    <div class="bg" style="min-height: 680px;width: 100%;">
        <div style="height:70px;"></div>
      <form style="height:550px;" id="loginform" method="post" action="">
            <div class="loginbg">
            <div style="width: 692px;margin: 0 auto;">
            	<img src="imgs/circle.png" style="margin-top: -10px" >
            </div>
            <div style="width: 692px;margin: 0 auto;margin-top: -690px;position: relative;">
                <div style="height:130px;width: 400px;margin:0 auto;margin-top: 160px;">
		        	<img src="imgs/welcome.png" >
		        </div>
		        <div style="width: 160px;margin:0 auto;font-family:微软雅黑;color: white;margin-top: -30px;">小巴管理平台登录</div>
                        <div style="color:#FF0000;margin-top: 20px" align="center"  >${errmessage}</div>
						<div style="width: 540px;margin-top: 100px;">
						<div style="float: left;margin-left: 175px;" >
                        <div style="width:275px;background-color: white;height: 37px;">
                       		<input class="input" type="text" name="loginname" id="loginname" value="${loginname}"  style="float:right;" placeholder="输入用户名"/>
                        	<img src="imgs/login_user.png" style="float:right;margin-top: 4px;margin-right: 5px;">
                        </div>
                         <div style="width:275px;background-color: white;height: 37px;margin-top: 5px;">
                        	<input style="float:right; " class="input"  type="password" name="password" id="password" placeholder="输入密码"/>
                        	<img src="imgs/login_pad.png" style="float:right;margin-top: 6px;margin-right: 10px;">
                        </div>
 </div>          
                        <div style="float: right; margin-top: -14px;"><div id="login" style="height:80px;width: 80px;background-color:#0B96FB;position: relative;top:13px;left: 10px;font-size: 30px;text-align: center;line-height: 77px;color: white;font-family:微软雅黑;cursor:pointer;" onclick="login();">登录</div></div>
</div>
              
           		</div>
            </div>
        </form>        
    </div>

    </div>
</body>
<script>
// function frememberpw(){
// 	if($("#rememberpw").prop("checked")==true){
// // 		if($("input[name=password]").val() != ''){
// 		$.cookie('rememberpw',1, {
// 				expires : 30
// 			});
// 		$.cookie('password', $("input[name=password]").val(), {
// 			expires : 30
// 		});
// // 		}
// // 		alert(111);
// 	}else{
// 		$.cookie('rememberpw',0, {
// 			expires : 30
// 		});
// 	}
// }

// function fremembername(){
// 	if($("#remembername").prop("checked")==true){
// // 		if($("input[name=loginname]").val() != ''){
// 		$.cookie('remembername',1, {
// 				expires : 30
// 			});
// 		$.cookie('loginname', $("input[name=loginname]").val(), {
// 			expires : 30
// 		});
// // 		}
// // 		alert(111);
// 	}else{
// 		$.cookie('remembername',0, {
// 			expires : 30
// 		});
// 	}
// }
</script>
</html>