<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="checksession.jsp" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>小巴币</title>
<script type="text/javascript" src="js/jquery-1.8.3.min.js"></script>
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="css/font-awesome.css" rel="stylesheet" />
<link href="css/account.css" rel="stylesheet" type="text/css" />
<style type="text/css">
body
{
	background-color:rgb(247,247,247);
}

</style>
<script type="text/javascript">
$(document).ready(function(){
	var studentid='${sessionScope.studentid}';
	var token='${sessionScope.token}';
	//studentid='18';
	var params = {	
					action:"GETCOINAFFILIATION",
					studentid:studentid,
					token:token			
				  };
	jQuery.post("../suser", params, showCoin, 'json');
	
	var params2 = {
					action:"GETSTUDENTCOINRECORDLIST",
					studentid:studentid,
					token:token
					};
	jQuery.post("../suser", params2, coinrecordlist, 'json');
	
});
function showCoin(obj){
	if(obj.code==1){
		var h="";
		var coins=obj.coinaffiliationlist;
		if(coins.length>0){
			//如果有记录时
			for(var i=0;i<coins.length;i++){
				h=h+"<p><span>"+coins[i].coin+"个</span><span class='pull-right'>"+coins[i].msg+"</span></p>";
			}
		}
		$("#coindetail").html(h);
	}else{
		alert(obj.message);
		window.location.href=redirect_login;
	}
	
}

function coinrecordlist(obj)
{
	if(obj.code==1){
		$("#coinnum").html(obj.coinnum+"个");
		$("#fcoinnum").html("（冻结金额￥"+obj.fcoinnum+"）");
		var rs=obj.recordlist;
		
		if(rs.length>0){
			var h="";
			for(var i=0;i<rs.length;i++){
				var type="";
				var op="";
				if(rs[i].type==1){
					type="收入";
					op="+";
				}else if(rs[i].type==2){
					type="支出";
					op="-";
				}
				h=h+'<div class="col-md-12 col-sm-12 col-xs-12"><p><span>'+type+'</span><span class="pull-right add">'+op+rs[i].coinnum;
				h=h+'</span></p><p><span>'+rs[i].addtime+'</span></p></div>';
			}
			$("#recorddetail").html(h);
			
		}	
	}else{
		/* alert(obj.message);
		window.location.href=redirect_login; */
	}
}
</script>
</head>

<body>
<div class="container">
  <!-- <div class="row coin-head">
    <div class="col-md-12 col-sm-12 col-xs-12">
      <p>小巴币<i class="icon icon-question-sign"></i></p>
      <p id="coinnum">2545个</p>
    </div>
  </div> -->
  <div class="row coin-head">
    <div class="col-md-12 col-sm-12 col-xs-12">
      <p>小巴币<i class="icon icon-question-sign"></i></p>
      <p  id="coinnum"><i>个</i></p>
      <p id="fcoinnum"></p>
    </div>
  </div>
  <div class="row coin-origin">
  	<div class="col-md-12 col-sm-12 col-xs-12"> 
    	<!-- <div class="row coin-origin-group">
        	<div id="coindetail" class="col-md-12 col-sm-12 col-xs-12 coin-origin-single">
            	<p><span>3625个</span><span class="pull-right">限广大驾校</span></p>
                <p><span>234个</span><span class="pull-right">限西湖驾校</span></p>
            </div>
        </div>
        <div class="row coin-origin-group">
        	<div class="col-md-12 col-sm-12 col-xs-12 coin-origin-single">
            	<p><span>344个</span><span class="pull-right">限刘华教练</span></p>
            </div>
        </div>
        <div class="row coin-origin-group">
        	<div class="col-md-12 col-sm-12 col-xs-12 coin-origin-single">
            	<p><span>123个</span><span class="pull-right">通用</span></p>
            </div>
        </div>    -->     
    </div>  
  </div>
  
  
  <div class="row coin-detail">
		<div class="col-md-12 col-sm-12 col-xs-12">
        
        	<div class="row coin-detail-head">
            	<div class="col-md-12 col-sm-12 col-xs-12"><span>明细</span></div>
            </div>
            <div class="row"><div class="col-md-12 col-sm-12 col-xs-12"><hr/></div></div>
            <div id="recorddetail" class="row coin-detail-body">
            	<!-- <div class="col-md-12 col-sm-12 col-xs-12">
                	<p><span>预约学车培训</span><span class="pull-right decrease">-200</span></p>
                    <p><span>2015-12-12 12:12:12</span></p>
                </div>
                <div class="col-md-12 col-sm-12 col-xs-12">
                	<p><span>充值</span><span class="pull-right add">+200</span></p>
                    <p><span>2015-12-12 12:12:12</span></p>
                </div> -->
            </div>  
        </div>
    </div>
  

  
  
  
  
</div>
</body>
</html>
