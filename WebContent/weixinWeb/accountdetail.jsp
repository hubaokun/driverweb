<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>账户余额详情</title>
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="css/font-awesome.css" rel="stylesheet" />
<link href="css/account.css" rel="stylesheet" type="text/css" />
<style type="text/css">

</style>
</head>

<body>
<div class="container">
	<div class="row account-head">
    	<div class="col-md-12 col-sm-12 col-xs-12">
        	<p>可用余额（元）<i class="icon icon-question-sign"></i></p>
            <p id="restMoney">￥2545</p>
        </div>
    </div>
    
    <div class="row account-money">
    	<div class="col-md-5 col-sm-5 col-xs-5">
			<a href="charge.jsp">
            	<i class="account-icon icon-charge"></i>
            <span>充值</span>
            </a>
        </div>
        <div class="col-md-2 col-sm-2 col-xs-2">
        	<div class="divider-line"></div>
        </div>
        <div class="col-md-5 col-sm-5 col-xs-5">
        	<a href="cash.jsp" style="  margin-left: -40px;">
            	<i class="account-icon icon-cash"></i>
            <span>提现</span>
            </a>
        </div>
    </div>
    <div class="row account-bill">
		<div class="col-md-12 col-sm-12 col-xs-12">
        
        	<div class="row account-bill-head">
            	<div class="col-md-12 col-sm-12 col-xs-12"><span>账单</span></div>
            </div>
            <div class="row"><div class="col-md-12 col-sm-12 col-xs-12"><hr/></div></div>
            <div class="row account-bill-body">
            	<div class="col-md-12 col-sm-12 col-xs-12">
                	<p><span>预约学车培训</span><span class="pull-right decrease">-200</span></p>
                    <p><span>2015-12-12 12:12:12</span><span class="pull-right">余额：￥123</span></p>
                </div>

                <div class="col-md-12 col-sm-12 col-xs-12">
                	<p><span>充值</span><span class="pull-right add">+200</span></p>
                    <p><span>2015-12-12 12:12:12</span><span class="pull-right">余额：￥123</span></p>
                </div>
            </div>  
        </div>
    </div>

</div>
</body>
</html>
