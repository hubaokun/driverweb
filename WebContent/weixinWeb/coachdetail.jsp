<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>教练详情</title>
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="css/font-awesome.css" rel="stylesheet" />
<link href="css/coachdetail.css" rel="stylesheet" type="text/css" />
<script src="js/jquery-1.8.3.min.js"></script> 
<script src="js/jquery-ui-1.10.3.min.js"></script> 
<style type="text/css">
</style>
<%
    String coachid=request.getParameter("coachid");
	HttpSession s=request.getSession();
	String studentid=s.getAttribute("studentid").toString();
%>
<script type="text/javascript">
var coachinfo;
var evaluatelist;
var pagenum=0;
var hasmore=1;
var type=1;
var c_id=<%=coachid%>;
$(function(){
	var action1="GETCOACHDETAIL";
	$.ajax({		   
			type : "POST",
			url : "/xiaoba/sbook",
			dataType: "json",
			data : {
				action : action1,
				coachid : <%=coachid%>
			},
			success : function(data) {
				coachinfo=data.coachinfo;
				var model_list="";
				
				$("#avatar").html("<div class='head-avatar center-block'> <img src="+coachinfo.avatarurl+" class='img-responsive img-circle center-block' /> </div>")
	            $("#c_name").html("<p>"+coachinfo.realname+"<span class='coach-mark'><i class='icon icon-man'></i>"+coachinfo.age+"</span></p><p><i class='glyphicon glyphicon-star'></i><i class='glyphicon glyphicon-star'></i><i class='glyphicon glyphicon-star'></i><i class='glyphicon glyphicon-star'></i><i class='glyphicon glyphicon-star'></i></p>");
	            $("#c_card").html("<p>教练证号：<span>"+coachinfo.coach_cardnum+"</span></p>");
	            for(var i=0;i<coachinfo.modellist.length;i++)
	            {
	            	model_list=model_list+coachinfo.modellist[i].modelname;
	            }
	            $("#c_car").html("<p>准教车型：<span>"+model_list+"</span></p>");
	            $("#c_self_evaluation").html("<p>自我评价：<span>"+coachinfo.selfeval+"</span></p>");
	            $("#callhim").html("<div class='col-md-6 col-sm-6 col-xs-6'><a href='sms:"+coachinfo.phone+"'> <span><i class='icon icon-envelope'></i>短信</span></a> </div><div class='col-md-6 col-sm-6 col-xs-6'><a href='tel:"+coachinfo.phone+"'> <span><i class='icon icon-phone'></i>电话</span></a> </div>");
	           // $("#evaluatestar").html("<p><span><i class='icon icon-star'></i><i class='icon icon-star'></i><i class='icon icon-star'></i><i class='icon icon-star'></i><i class='icon  icon-star-empty'></i></span><span>"+coachinfo.score+"</span><span class='pull-right'>568人评价<i class='glyphicon icon-right'></i></span></p>");
			}
		
		
	});	
	var action2="getCoachComments";
	$.ajax({		   
		type : "POST",
		url : "/xiaoba/sbook",
		dataType: "json",
		data : {
			action : action2,
			coachid : <%=coachid%>,
			type : type,
			pagenum   : pagenum
		},
		success : function(data) {
			evaluatelist=data.evalist;
			
			var content_list="<div class=\"row coach-judge-head\"><a href=\"evaluatewhole.jsp?coachid="+c_id+"\"><div id=\"evaluatestar\" class=\"col-md-12 col-sm-12 col-xs-12\"><p><span><i class=\"icon icon-star\"></i><i class=\"icon icon-star\"></i><i class=\"icon icon-star\"></i><i class=\"icon icon-star\"></i><i class=\"icon  icon-star-empty\"></i></span><span>"+coachinfo.score+"</span><span class=\"pull-right\">"+data.count+"人评价<i class=\"glyphicon icon-right\"></i></span></p></div></a></div><div class=\"row\"><hr/></div>";
            for(var i=0;i<evaluatelist.length;i++)
            {
            	var adddate=evaluatelist[i].addtime;
            	var fromstudentid=evaluatelist[i].from_user;
            	adddate=adddate.substring(0,10);
            	content_list=content_list+"<div class='row coach-judge-body'><a href='evaluatesingle.jsp?coachid="+c_id+"&studentid="+fromstudentid+"'><div class='col-md-3 col-sm-3 col-xs-3'><p>"+evaluatelist[i].nickname+"</p></div><div class='col-md-9 col-sm-9 col-xs-9'><p><span class='pull-right'>"+adddate+"<i class='glyphicon icon-right'></i></span></p></div><div class='col-md-12 col-sm-12 col-xs-12'><p>"+evaluatelist[i].content+"</p></div></a></div>";
            	
            }
            $("#evaluatelist").html(content_list);
            hasmore=data.hasmore;
		}
   });
	var winH = $(window).height(); //页面可视区域高度 
    //var i = 1; //设置当前页数 
    $(window).scroll(function () { 
        var pageH = $(document.body).height(); 
        var scrollT = $(window).scrollTop(); //滚动条top 
        var aa = (pageH-winH-scrollT)/winH; 
        if(aa<0.02){ 
           /* $.getJSON("pro.json",{},function(data){ 
                if(data)
                {  
                   str = "<div style=width:100%; height:100px; background-color:grey;><h1>7</h1></div><div style='width:100%; height:100px; background-color:yellow;'></div>";
                   $("body").append(str); 
                    
                   i++; 
                }else{ 
                    //没有数据了做点什么事情 
                    return false; 
                } 
            }); */
            if(hasmore==1)
            {
            	var action2="getCoachComments";
            	pagenum++;
            	$.ajax({		   
            		type : "POST",
            		url : "/xiaoba/sbook",
            		dataType: "json",
            		data : {
            			action : action2,
            			coachid : <%=coachid%>,
            			type : type,
            			pagenum   : pagenum
            		},
            		success : function(data) {
            			evaluatelist=data.evalist;
            			var content_list="";
                        for(var i=0;i<evaluatelist.length;i++)
                        {
                        	var adddate=evaluatelist[i].addtime;
                        	var fromstudentid=evaluatelist[i].from_user;
                        	adddate=adddate.substring(0,10);
                        	content_list=content_list+"<div class='row coach-judge-body'><a href='evaluatesingle.jsp?coachid="+c_id+"&studentid="+fromstudentid+"'><div class='col-md-3 col-sm-3 col-xs-3'><p>"+evaluatelist[i].nickname+"</p></div><div class='col-md-9 col-sm-9 col-xs-9'><p><span class='pull-right'>"+adddate+"<i class='glyphicon icon-right'></i></span></p></div><div class='col-md-12 col-sm-12 col-xs-12'><p>"+evaluatelist[i].content+"</p></div></a></div>";
                        	
                        }
                        $("#evaluatelist").append(content_list);
                        hasmore=data.hasmore;
            		}
               });
            }
        	
        } 
    }); 
});

</script>
</head>

<body>
<div class="container">
  <div class="row coach-head">
    <div id="avatar" class="col-md-3 col-sm-3 col-xs-3">
      
    </div>
    <div id="c_name" class="col-md-7 col-sm-7 col-xs-7">
      
    </div>
  </div>
  <div class="row coach-data">
    <div id="c_card" class="col-md-12 col-sm-12 col-xs-12">
      
    </div>
  </div>
  <div class="row coach-data">
    <div id="c_car" class="col-md-12 col-sm-12 col-xs-12">
      
    </div>
  </div>
  <div class="row coach-data">
    <div id="c_self_evaluation" class="col-md-12 col-sm-12 col-xs-12">
      
    </div>
  </div>
  <div id="callhim" class="row coach-contact">
    
  </div>
  <div class="row coach-judge">
    <div id="evaluatelist" class="col-md-12 col-sm-12 col-xs-12">
      <div class="row coach-judge-head">
        <a href="evaluatewhole.jsp">
      	  <div id="evaluatestar" class="col-md-12 col-sm-12 col-xs-12">
        
        </div>
        </a>
      </div>
      <div class="row"><hr/></div>
      <!--  <div class="row coach-judge-body">
        <a href="evaluatesingle.jsp">
          <div class="col-md-3 col-sm-3 col-xs-3">
            <img src="images/coach-avart.png" class="img-responsive" />
            <p>YOYO</p>
          </div>
          <div class="col-md-9 col-sm-9 col-xs-9">
            <p><span class="pull-right">2015-01-01<i class="glyphicon icon-right"></i></span></p>
          </div>
          <div class="col-md-12 col-sm-12 col-xs-12">
            <p>教练很有耐心，不随便发火，五星点赞</p>
          </div>
        </a>
      </div>
      <div class="row coach-judge-body">
        <a href="evaluatesingle.jsp">
          <div class="col-md-3 col-sm-3 col-xs-3">
            <img src="images/coach-avart.png" class="img-responsive" />
            <p>YOYO</p>
          </div>
          <div class="col-md-9 col-sm-9 col-xs-9">
            <p><span class="pull-right">2015-01-01<i class="glyphicon icon-right"></i></span></p>
          </div>
          <div class="col-md-12 col-sm-12 col-xs-12">
            <p>教练很有耐心，不随便发火，五星点赞</p>
          </div>
        </a>
      </div>-->
    </div>
  </div>
</div>
</body>
</html>
