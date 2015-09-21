<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="checksession.jsp" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>我的账户</title>
<link href="css/reset.css" rel="stylesheet" type="text/css" />
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="css/font-awesome.css" rel="stylesheet" />
<link href="css/style.css" rel="stylesheet" type="text/css" />
<script src="js/jquery-1.8.3.min.js"></script>
<style type="text/css">
.container
{
	overflow:hidden;
}
</style>

<script type="text/javascript">

$(document).ready(function(){
	var sid='${sessionScope.studentid}';
	var token='${sessionScope.token}';
	/* var c_info='${sessionScope.c_info}';
	var wxinfo=$.parseJSON(c_info); */
	//sid="18";
	var params = {action:"GETSTUDENTINFO",studentid:sid,token:token};
	jQuery.post("../suser", params, showStudent, 'json');
});
function showStudent(obj){
	if(obj.code==1){
		var c_info='${sessionScope.c_info}';
		var wxinfo=$.parseJSON(c_info);
		$("#avatarurl").attr("src",wxinfo.headimgurl);
		$("#realname").html(wxinfo.nickname);
		/* if(c_info!=''){
			var wxinfo=$.parseJSON(c_info);
			$("#avatarurl").attr("src",wxinfo.headimgurl);
			$("#realname").html(wxinfo.nickname);
		} */
		 //alert(obj.data.realname);
		//alert(obj.data.realname+"#"+obj.data.avatarurl);
		/* if(obj.data.avatarurl==''){
			$("#avatarurl").attr("src",wxinfo.headimgurl);
		}else{
			$("#avatarurl").attr("src",obj.data.avatarurl);//设置头像图片
		}
		if(obj.data.realname==''){
			$("#realname").html(wxinfo.nickname);
		}else{
			$("#realname").html(obj.data.realname);
		} */
		$("#realname").html(obj.data.realname);
		//$("#avatarurl").attr("src",obj.data.avatarurl);//设置头像图片
		$("#phone").html(obj.data.phone);
		//$("#avatarurl").attr("src",'${sessionScope.avatarurl}');//设置头像图片
		//$("#avatarurl").attr("src","https://www.baidu.com/img/bdlogo.png");//设置头像图片
// 		$("#coin").html(obj.data.coinnum+"个");
// 		$("#money").html(obj.data.money+"元");
// 		$("#coupon").html(obj.coupon+"小时");
		$("#coin").html(obj.data.coinnum);
		$("#money").html(obj.data.money);
		$("#coupon").html(obj.coupon);
	}else{
		alert(obj.message);
		//window.location.href=redirect_login;
	}
}
</script>
</head>

<body>
<div class="container" >
  <div id="tabs" >
   <!--  <ul class="foot-nav" data-role="footer">
      <li><a href="coachlist.jsp"><span class="coach"></span><p>找教练</p></a></li><li><a href="uncompleorder.jsp"><span class="order"></span><p>订单</p></a></li><li class="active"><a href="javascript:void(0);"><span class="my"></span><p>我的</p></a></li>
    </ul> -->
    <ul class="foot-nav" data-role="footer">
      <li><a href="coachlist.jsp"><span class="coach"></span><p>找教练</p></a></li><li><a href="uncompleorder.jsp"><span class="order"></span><p>订单</p></a></li><li class="active"><a href="my.jsp"><span class="my"></span><p>我的</p></a></li>
    </ul>
    <div id="tabs-3">
    	<div class="row my-head">
        	<a href="mybasicinfo.jsp">
            	<div class="col-md-3 col-sm-3 col-xs-3">
            	<div class="head-avatar center-block">
                	<img id="avatarurl" src="images/person-one.png" class="img-responsive img-circle center-block" />
                </div>
            </div>
            <div class="col-md-7 col-sm-7 col-xs-7">
            	<p id="realname"></p>
                <p id="phone"></p>
            </div>
            <div class="col-md-2 col-sm-2 col-xs-2">
            	<i class="icon icon-chevron-right my-head-icon"></i>
            </div>
            </a>
        </div>
        <div class="row">
			 <div class="col-md-12 col-sm-12 col-xs-12 my-nav-item">
                <a href="accountdetail.jsp">
                    <i class="my-nav-icon icon-money-rest"></i>				
                    <span>账户余额</span>
                    <!-- <i class="glyphicon icon-right"><span id="money"></span></i> -->
<<<<<<< HEAD
                    <span class="pull-right"><span id="money"></span><i class="glyphicon icon-right"></i></span>	
=======
                    <span class="pull-right"><span id="money" style="margin-left: -20px;"></span><span class="unit-tips">元</span><i class="glyphicon icon-right"></i></span>	
>>>>>>> b3eed5202bf05f05533a59360b49785f1b4744d8
                </a>
    		</div>
            <div class="col-md-12 col-sm-12 col-xs-12 my-nav-item">
                <a href="coupon.jsp">
                    <i class="my-nav-icon icon-coupon"></i>				
                    <span>小巴券</span>		
                    <!-- <i class="glyphicon icon-right"><span id="coupon"></span></i> -->
<<<<<<< HEAD
                    <span class="pull-right"><span id="coupon"></span><i class="glyphicon icon-right"></i></span>
=======
                    <span class="pull-right"><span id="coupon" style="margin-left: -20px;"></span><span class="unit-tips">小时</span><i class="glyphicon icon-right"></i></span>
>>>>>>> b3eed5202bf05f05533a59360b49785f1b4744d8
                </a>
    		</div> 
            <div class="col-md-12 col-sm-12 col-xs-12 my-nav-item">
                <a href="coin.jsp">
                    <i class="my-nav-icon icon-coin"></i>				
                    <span>小巴币</span>		
                    <!-- <i class="glyphicon icon-right"><span id="coin"></span></i> -->
<<<<<<< HEAD
                    <span class="pull-right"><span id="coin">个</span><i class="glyphicon icon-right"></i></span>
=======
                    <span class="pull-right"><span id="coin" style="margin-left: -20px;"></span><span class="unit-tips">个</span><i class="glyphicon icon-right"></i></span>
>>>>>>> b3eed5202bf05f05533a59360b49785f1b4744d8
                </a>
    		</div>   
        </div>
<!--        <div class="row drive-row">
        	<div class="col-md-12 col-sm-12 col-xs-12 my-nav-item">
              <a href="">
                  <i class="my-nav-icon icon-infor"></i>				
                      <span>学驾信息</span>		
                  <i class="icon icon-chevron-right"></i>
              </a>
    		</div>
        </div>-->
       <!--  <div class="row logout-row">
        	<div class="col-md-12 col-sm-12 col-xs-12 my-nav-item">
              <span class="logout">退出登录</span>
    		</div>
        </div> -->
    </div>
  </div>
</div>
</body>
</html>
