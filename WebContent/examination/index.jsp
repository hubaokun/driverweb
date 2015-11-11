<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>科目考试</title>
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="css/reset.css" rel="stylesheet" type="text/css" />
<link href="css/style.css" rel="stylesheet" type="text/css" />
<style type="text/css">

</style>
</head>

<body>
<div class="container">
	<div class="row empty-row"></div>
	<div class="row title-row">
    	<div class="col-md-12 col-sm-12 col-xs-12">
        	<h1>科目一</h1>
        </div>
    </div>
    <div class="row subject-1-row">
    	<div class="col-md-12 col-sm-12 col-xs-12">
        	<ul class="subject-ul">
            	<li><a href="orderlist.jsp?passingtype=1" class="order-item">顺序练习</a><span class="subject-icon-right"></span></li>
                <li><a href="simulateingstarts.jsp?simulatetype=1" class="simulate-item">模拟考试</a><span class="subject-icon-right"></span></li>
            </ul>
        </div>
    </div>
    <div class="row title-row">
    	<div class="col-md-12 col-sm-12 col-xs-12">
        	<h1>科目四</h1>
        </div>
    </div>
     <div class="row subject-2-row">
    	<div class="col-md-12 col-sm-12 col-xs-12">
        	<ul class="subject-ul">
            	<li><a href="orderlist.jsp?passingtype=2" class="order-item">顺序练习</a><span class="subject-icon-right"></span></li>
                <li><a href="multilist.jsp" class="multi-item">多选题练习</a><span class="subject-icon-right"></span></li>
                <li><a href="orderlist.jsp?passingtype=4" class="animation-item">动画题加强练习</a><span class="subject-icon-right"></span></li>
                <li><a href="simulateingstarts.jsp?simulatetype=2" class="simulate-item">模拟考试</a><span class="subject-icon-right"></span></li>
            </ul>
        </div>
    </div>
</div>

<script src="js/jquery-1.8.3.min.js"></script>
</body>
</html>
