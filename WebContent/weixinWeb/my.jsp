<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>小巴学车</title>
<link href="css/reset.css" rel="stylesheet" type="text/css" />
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="css/font-awesome.css" rel="stylesheet" />
<link href="css/style.css" rel="stylesheet" type="text/css" />
<style type="text/css">
</style>
</head>

<body>
<div class="container" >
  <div id="tabs" >
    <ul class="foot-nav" data-role="footer">
      <li><a href="coachlist.jsp"><span class="coach"></span><p>找教练</p></a></li><li><a href="orderlist.jsp"><span class="order"></span><p>订单</p></a></li><li class="active"><a href="javascript:void(0);"><span class="my"></span><p>我的</p></a></li>
    </ul>

    <div id="tabs-3">
    	<div class="row my-head">
        	<a href="mybasicinfo.jsp">
            	<div class="col-md-3 col-sm-3 col-xs-3">
            	<div class="head-avatar center-block">
                	<img src="images/person-one.png" class="img-responsive img-circle center-block" />
                </div>
            </div>
            <div class="col-md-7 col-sm-7 col-xs-7">
            	<p>童卫军</p>
                <p>159888865324</p>
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
                    <i class="icon icon-chevron-right"><span>200￥</span></i>	
                    
                </a>
    		</div>
            <div class="col-md-12 col-sm-12 col-xs-12 my-nav-item">
                <a href="coupon.jsp">
                    <i class="my-nav-icon icon-coupon"></i>				
                    <span>小巴券</span>		
                    <i class="icon icon-chevron-right"><span>500小时</span></i>
                </a>
    		</div>
            <div class="col-md-12 col-sm-12 col-xs-12 my-nav-item">
                <a href="coin.jsp">
                    <i class="my-nav-icon icon-coin"></i>				
                    <span>小巴币</span>		
                    <i class="icon icon-chevron-right"><span>20个</span></i>
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
        <div class="row logout-row">
        	<div class="col-md-12 col-sm-12 col-xs-12 my-nav-item">
              <span>退出登录</span>
    		</div>
        </div>
    </div>
  </div>
</div>



<script src="js/jquery-1.8.3.min.js"></script>

</body>
</html>
