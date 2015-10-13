<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="checksession.jsp" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>确认订单</title>
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="css/font-awesome.css" rel="stylesheet" />
<link href="css/orderconfirm.css" rel="stylesheet" type="text/css" />
<link href="css/loader.css" rel="stylesheet" type="text/css" />
</head>

<body>
<div class="container">
<!--  <div class="row head-row-1">
    <div class="col-md-12 col-sm-12 col-xs-12"> <span>确认单订单</span> </div>
  </div>-->
  <div class="row head-row-2">
    <div class="col-md-12 col-sm-12 col-xs-12"> <span>请提前与教练联系，确认练车地点</span> </div>
  </div>
  <div class="row link-coach-row">
    <div class="col-md-12 col-sm-12 col-xs-12"> <a href="tel:${param.phone}"> <span>教练： ${param.realname}</span><span class="pull-right"><i class="icon icon-telphone"></i></span> </a> </div>
  </div>
  <div id = "order_list_view">
  
  </div>
 
  <div class="row confirm-pay-row">
    <div class="col-md-8 col-sm-8 col-xs-8">
      <p> 共<span>${param.counttime}</span>小时，合计：<span>￥${param.countmoney}</span> </p>
      <p id="coupon_sum"></p>
      <!--p id="coin_sum">使用小巴币：0个</p>
      <p id="money_sum">使用余额：￥0</p-->
    </div>
    <div class="col-md-4 col-sm-4 col-xs-4" id="confirm_pay" onclick="confirmPay()"> <span id="pay_or_charge">确定</span> </div>
  </div>
  <div style="width:100px; height:50px; display:block;"></div>
</div>

<!--弹出框 starts-->
<div class="overlay" id="pay_type_view">
	<div class="overlay-inner"  id="pay_type_content">
  		<div class="overlay-content ">
    <div class="container">
    	<div class="row overlay-content-title">
        	<div class="col-md-12 col-sm-12 col-xs-12">
            	<a href="javascript:;">
                	<span>支付方式</span>
                	<span id="set_pay_type_btn" class="pull-right"  >确定</span>
                	<span id="cancle_pay_type_btn" class="pull-right"  >取消</span>
                </a>
            </div>
        </div>
        <div class="row"><hr/></div>
        <div class="row overlay-content-body">
            <ul>
              <div class="col-md-12 col-sm-12 col-xs-12">
              	<li id="icon_coupon">
                	<div class="row">
                		<div class="col-md-6 col-sm-6 col-xs-6"><p>小巴券</p><p id="coupon_available">可用小巴券0张</p></div>
                		<div class="col-md-6 col-sm-6 col-xs-6"><i class="glyphicon glyphicon-ban-circle"></i></div>	
                    </div>
                </li>
                <li id="icon_coin">
                    <div class="row">
                        <div class="col-md-6 col-sm-6 col-xs-6"><p>小巴币</p><p id="coin_available">无可用小巴币</p></div>
                        <div class="col-md-6 col-sm-6 col-xs-6"><i class="icon icon-ok"></i></div>
                    </div>
                </li>
                <li id="icon_money" onclick="setPayTypeChooseWay(this)">
                    <div class="row">
                        <div class="col-md-6 col-sm-6 col-xs-6"><p>账户余额</p><p id="money_available">账户可用余额￥0</p></div>
                        <div class="col-md-6 col-sm-6 col-xs-6"><i class="icon icon-ok"></i></div>
                    </div>
                </li>
                </div>
            </ul>
        </div>
    </div>
  </div>
  	</div>
</div>

<!--确定付款弹窗 starts-->
<div id="pay_feedback_popup" class="overlay-cancle">
  <div class="overlay-cancle-content">
    <div class="container">
      <div class="row" style="margin-left:0px; margin-right:0px;">
      	<span class="pull-right"><i class="icon icon-remove" id="pay_feedback_close_btn"></i></span>
      </div>
      <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12"> <span id="pay_feedback_message">您已预约成功！</span> </div>
        <div class="col-md-12 col-sm-12 col-xs-12" id="orderDetailBtn"></div>
      </div>
    </div>
  </div>
</div>
<!--确定付款弹窗 ends-->

<div class="overlay-wait">
  <div class="overlay-wait-content">
  	<div class="text-center">
      <p>正在提交</p>
      <div class="loader1"> <span></span> <span></span> <span></span> <span></span> <span></span> </div>
    </div>
  </div>
</div>

<script src="js/jquery-1.8.3.min.js"></script> 
<script>

var data_list_choose=${param.data_list_choose};
var coachid = ${param.coachid};
var studentid = ${param.studentid};
var choosed_hour = 0;
var order_list;
var couponlist_available;
var coinnum_available= 0;
var money_available= 0;
var couponlist_available_local;
var coinnum_available_local= 0;
var money_available_local= 0;
var coupon_sum=0;
var coin_sum=0;
var money_sum=0;
var need_charge=false;//是否需要充值
var choosed_hour_price=0;
var choosed_scheduleid=0;
var paytype_name="";
var money_used=0;
var coin_used=0;
var token='${sessionScope.token}';

/*1.获取已选课程信息*/
function getOrderList(){
	$("#order_list_view").html("");
	for(var i=0;i<data_list_choose.length;i++){
		var item = data_list_choose[i];
		var course_time= item.date+" "+ item.hour+":00-"+(parseInt(item.hour)+1)+":00";
		var price = item.price;
		var subjet= item.subject;
		var detail = item.addressdetail;
		
		$("#order_list_view").append(
		  "<div class='row confirm-order-content' time='"+item.hour+"' id='"+item.scheduleid+"'>"+
		    "<div class='col-md-12 col-sm-12 col-xs-12 confirm-order-content-head'>"+
		      "<p>"+
		       " <time>"+course_time+"</time>"+
		        "<span class='pull-right'>￥"+item.price+"</span> </p>"+
		    "</div>"+
		    "<div class='col-md-12 col-sm-12 col-xs-12'>"+
		      "<hr/>"+
		    "</div>"+
		    "<div class='col-md-12 col-sm-12 col-xs-12 confirm-order-content-body'>"+
		      "<p>科目："+item.subject+"</p>"+
		      "<p>地址："+(item.addressdetail?item.addressdetail:"")+"</p>"+
		    "</div>"+
		    "<div class='col-md-12 col-sm-12 col-xs-12'>"+
		      "<hr/>"+
		    "</div>"+
		    "<div class='col-md-12 col-sm-12 col-xs-12 confirm-order-pay'> "+
		    "<a href='javascitp:;' onclick='showPayType("+item.hour+","+item.price+","+item.scheduleid+")'> <span>选择支付方式</span> <i class='icon icon-chevron-right'></i> <span id='"+item.scheduleid+"_paytype' class='pull-right pay-way'></span></a> </div>"+
		  "</div>"
		);
	}
	
}
/*2.获取可用余额信息*/
function getAllPayTypeBlance()
{
	var active_url= "../sbook?action=getCanUseCouponList";
	
	var search_condition={"coachid":coachid,"studentid":studentid};
	if(parseInt(coachid)>0 && studentid>0){
		$.getJSON(active_url,search_condition,function(data)
		{
			couponlist_available = data.couponlist;
			coinnum_available= data.coinnum;
			money_available= data.money;
			coinnum_available_local= data.coinnum;
			money_available_local= data.money;
			if(couponlist_available){
				couponlist_available_local=couponlist_available.concat();
			}
// 			/*临时调试赋值*/
// 			 			couponlist_available=null;
// 						couponlist_available_local=null;
// 						coinnum_available_local=800;
// 						coinnum_available=800;
// 						money_available= 29;
// 						money_available_local= 29; 
// 						/*临时调试赋值*/
			init_order_list();
		});
	}
		
}

/*
 * 1、小巴券、余额、单独小巴币是delmoney=订单额，total=0
 * 2、混合支付是delmoney=该订单中需要的小巴币个数 ，total=订单额
 */

/*3.初始化并自动分配支付方式和支付金额*/
function init_order_list(){
	var order_list_str="";
	var item;
	var paytype=1;
	var delmoney=0;//小巴币个数
	var recordid=0;
	var total=0;
	
	//初始“确定”按钮
	$("#pay_or_charge").html("确定");
	for(var i=0;i<data_list_choose.length;i++){
		item = data_list_choose[i];
		recordid=0;
		need_charge= false;
		//paytype:1 余额  2 小巴券 3 小巴币4小巴币+余额的混合支付
		//paytype=4,delmoney=小巴币个数
		if(couponlist_available && couponlist_available.length>i){
			//优先使用小巴券
			paytype= 2;
			paytype_name="学时券";
			recordid= couponlist_available[i].recordid;
			coupon_sum+=item.price;
			delmoney+=item.price;
			total=0;
			couponlist_available_local.shift();
			
		}else if(coinnum_available_local>0){
			//再使用小巴币
			if(coinnum_available_local>=item.price){
				paytype= 3;
				delmoney=item.price;
				total=0;
				paytype_name="小巴币";
				coinnum_available_local-=item.price;
				coin_sum+=delmoney;
			}else if(money_available_local>=(item.price-coinnum_available_local)){
				//如果可用小巴币不足，进行混合支付
				paytype= 4;
				paytype_name="小巴币 账户余额";
				delmoney=coinnum_available_local;
				total=item.price;
				coinnum_available_local=0;
				money_available_local -= (item.price-delmoney);
				coin_sum+=delmoney;
				money_sum+=item.price-delmoney;
				
			}else{
				//余额不足，提示去充值
				paytype=0;
				paytype_name="余额不足，请充值";
				need_charge= true;			
			}
		}else if(money_available_local>=item.price){
			//最后使用余额
			paytype=1;
			paytype_name="账户余额";
			delmoney=item.price;
			total=0;
			money_available_local-=delmoney;
			money_sum+=delmoney;
		}else{
			//余额不足，提示去充值
			paytype=0;
			paytype_name="余额不足，请充值";
			need_charge= true;
		}
		order_list_str+="{'time':["+item.hour+"],'date':'"+item.date+"','paytype':"+paytype+",'total':"+total+",'delmoney':"+delmoney+",'recordid':"+recordid+",'needcharge':"+need_charge+"},";
		//更改相应订单的支付方式显示
		$("#"+item.scheduleid+"_paytype").html(paytype_name);
		
		
		
	}
	order_list_str="["+order_list_str.substring(0,order_list_str.length-1)+"]";
	order_list= eval("("+order_list_str+")");
	
	var sum_str="";
	if(coupon_sum>0){
		sum_str+="优惠券抵扣￥"+coupon_sum;
	}
	if(coin_sum>0){
		sum_str+=" 小巴币"+coin_sum+"个 ";
	}
	if(money_sum>0){
		sum_str+=" 账户余额￥"+money_sum;
	}
	
	$("#coupon_sum").html(sum_str);
	
	setConfirmPayView();
	

/* 	$('#pay_feedback_message').html("您已预约成功");
	//$('#pay_orderid').css('display','block');
	//$('#pay_orderid').attr("onclick","location.href='uncompleorderdetail.jsp?orderid='");
	var str = "<span onclick='location.href='uncompleorderdetail.jsp?orderid="+successorderid+"'>"+"订单详情</span>";
	$('#orderDetailBtn').append(str); */
}

function showPayType(hour,price,scheduleid)
{
	choosed_hour=hour;
	choosed_hour_price= price;
	choosed_scheduleid= scheduleid;
	
	//need_charge= false;
	
	if(couponlist_available_local && couponlist_available_local.length>0){
		$("#coupon_available").html("可用小巴券"+couponlist_available_local.length+"张");
		$("#icon_coupon i").attr("class", "icon icon-ok");
	 	$('#icon_coupon').attr("onclick","setPayTypeChooseWay(this)");
	}else{
		$("#coupon_available").html("无可用小巴券");
		$("#icon_coupon i").attr("class", "glyphicon glyphicon-ban-circle");
		$('#icon_coupon').attr("onclick","");
	}
	
	if(coinnum_available_local && coinnum_available_local>0){
		$("#coin_available").html("可用小巴币"+coinnum_available_local+"个");
		$("#icon_coin i").className="icon icon-ok";
		$('#icon_coin').attr("onclick","setPayTypeChooseWay(this)");
	}else{
		$("#coin_available").html("无可用小巴币");
		$("#icon_coin i").attr("class", "glyphicon glyphicon-ban-circle");
		$('#icon_coin').attr("onclick","");
	}
	
	if(money_available_local && money_available_local>0){
		$("#money_available").html("账户可用余额"+money_available_local+"元");
		$("#icon_money i").className="icon icon-ok";
		$('#icon_money').attr("onclick","setPayTypeChooseWay(this)");
	}else{
		$("#money_available").html("余额不足，请充值");
		//need_charge= true;
	}
	
	//setConfirmPayView();
	
	if($('.overlay .overlay-content .overlay-content-body ul li').hasClass('active-check'))
	{
		$('.overlay .overlay-content .overlay-content-body ul li').removeClass('active-check');
	}
	
			$('#pay_type_view').css('display','block');
			$('#pay_type_content').css('display','block');
}

/*在支付方式选择的弹窗div中只变更该弹窗中的支付方式的选中状态，
 * 新的支付方式和原有支付方式的数值纪录在js全局变量中
 *供点击确定按钮后计算使用
 */
var paytype_changed_order_num;
var old_pay_type;
var new_pay_type;
var new_recordid;
var old_delmoney;
var is_paytype_changed;

function setPayTypeChooseWay(event){
	//首先找到对应hour的本地临时订单信息
	//var i=0;	
	
	for(var i=0 ;i<order_list.length;i++){
		if(order_list[i].time==choosed_hour){
			old_pay_type= order_list[i].paytype;
			old_delmoney= order_list[i].delmoney;
			paytype_changed_order_num=i;
			break;
		}
	}
	is_paytype_changed=true;
	if($(event).attr("id")=="icon_coupon"){
		new_pay_type= 2;
		if(new_pay_type==old_pay_type){
			is_paytype_changed=false;
			return;
		}
	}else if($(event).attr("id")=="icon_money"){
		new_pay_type= 1;
		if(new_pay_type==old_pay_type){
			is_paytype_changed=false;
			return;
		}
	}else if($(event).attr("id")=="icon_coin"){
		new_pay_type= 3;
		if(new_pay_type==old_pay_type){
			is_paytype_changed=false;
			return;
		}else if(old_pay_type==4){
			is_paytype_changed=false;
			return;
		}
	}

	
	if($(event).attr("id")=="icon_coupon"){
		$(event).addClass('active-check').siblings().removeClass('active-check');
		new_pay_type= 2;
		paytype_name="学时券";
	}else if($(event).attr("id")=="icon_money"){
		if(money_available_local>=choosed_hour_price){
			paytype_name="账户余额";
		}else{
			paytype_name="余额不足，请充值";
			//need_charge= true;
			//$("#pay_or_charge").html("充值");
			//$("#confirm_pay").attr("onclick","gotoCharge()"); 
		}
		$(event).addClass('active-check').siblings().removeClass('active-check');
		new_pay_type= 1;
	}else if(coinnum_available_local>=choosed_hour_price){
		$(event).addClass('active-check').siblings().removeClass('active-check');
		paytype_name="小巴币";
		new_pay_type= 3;
	}else if(money_available_local>=(choosed_hour_price-coinnum_available_local)){
		$(event).addClass('active-check');
		$('#icon_money').addClass('active-check');
		$('#icon_coupon').removeClass('active-check');
		paytype_name="小巴币 账户余额";
		new_pay_type= 4;
	}else{
		paytype_name="余额不足，请充值";
		new_pay_type= 4;
		//need_charge= true;
		//$("#pay_or_charge").html("充值");
		//$("#confirm_pay").attr("onclick","gotoCharge()"); 		
	}	
	
// 	$("#"+choosed_scheduleid+"_paytype").html(paytype_name);
	
// 	//如果本课时的支付方式改变了
// 	 recalculatePayType(i,new_pay_type,old_pay_type,old_delmoney);
	
}

function getcouponDetailByRecordId(recordId){
	var old_coupon_detail;

	for(var i=0 ;i<couponlist_available.length;i++){
		if(couponlist_available[i].recordid==recordId){
			old_coupon_detail= jQuery.extend(true, {}, couponlist_available[i]); ;
			return old_coupon_detail;
		}
	}
}

function recalculatePayType(i,new_pay_type,old_pay_type,old_delmoney){
	var new_recordid= 0;
	var old_recordid = 0;
	//如果本课时的支付方式改变了
	if(old_pay_type != new_pay_type){
		old_recordid=order_list[i].recordid;
		//首先计算分配新的支付方式
		if(new_pay_type==2){
			//更改本课时相应订单的支付方式
			order_list[i].paytype=new_pay_type;
			order_list[i].delmoney=choosed_hour_price;
			order_list[i].total=0;
			order_list[i].recordid=couponlist_available_local[0].recordid;
			new_recordid =couponlist_available_local[0].recordid;
			order_list[i].needcharge=false;
			//更新合计支付信息
			coupon_sum+=choosed_hour_price;
			//更新本地可用余额信息
			couponlist_available_local.shift();
		}else if(new_pay_type==1){
			
			order_list[i].recordid=0;
			order_list[i].paytype=new_pay_type;
			if( money_available_local>=choosed_hour_price){
				//更改本课时相应订单的支付方式
				order_list[i].delmoney=choosed_hour_price;
				order_list[i].total=0;
				order_list[i].needcharge=false;
				//更新合计支付信息
				money_sum+=choosed_hour_price;
				//更新本地可用余额信息
				money_available_local-=choosed_hour_price;
			}else{
				order_list[i].delmoney=0;
				order_list[i].needcharge=true;
			}
		}else if(new_pay_type==3 && coinnum_available_local>=choosed_hour_price){
			//更改本课时相应订单的支付方式
			order_list[i].paytype=new_pay_type;
			order_list[i].delmoney=choosed_hour_price;
			order_list[i].total=0;
			order_list[i].recordid=0;
			order_list[i].needcharge=false;
			//更新合计支付信息
			coin_sum+=choosed_hour_price;
			//更新本地可用余额信息
			coinnum_available_local-=choosed_hour_price;
		}else if(money_available_local>=(choosed_hour_price-coinnum_available_local)){
			new_pay_type=4
			//首先计算混合支付金额中小巴币金额
			order_list[i].paytype=new_pay_type;
			order_list[i].delmoney=coinnum_available_local;
			order_list[i].total=choosed_hour_price;
			order_list[i].recordid=0;
			order_list[i].needcharge=false;
			//更新合计支付信息
			coin_sum+=coinnum_available_local;
			coin_used=coinnum_available_local;
			money_used=choosed_hour_price-coinnum_available_local;
			money_sum+=money_used;
			money_available_local-=money_used;	
			//更新本地可用余额信息
			coinnum_available_local=0;
		}else{
			order_list[i].needcharge=true;
		}

		if(new_pay_type==4){
			if(old_pay_type==3){
				//原来使用小巴币支付，那么首先把合计中的小巴币数量减去，然后增加本地可用小巴币数量
				coin_sum-=coin_used;
				coinnum_available_local+=coin_used;
			}else if(old_pay_type==1){
				//原来使用账户余额支付，那么首先把合计中的账户余额数量减去，然后增加本地可用账户余额数量
				money_sum-=money_used;
				money_available_local+=money_used;	
			}else if(old_pay_type==2){
				//原来使用小巴币支付，那么首先把合计中的小巴币数量减去，然后增加本地可用小巴币数量
				coupon_sum-=choosed_hour_price;				
				couponlist_available_local.unshift(getcouponDetailByRecordId(old_recordid));
			}else if(old_pay_type==4){
				//原来使用混合支付，那么首先把合计中的小巴币数量减去，然后增加本地可用小巴币数量
				coin_sum-=coin_used;
				money_sum-=money_used;
				coinnum_available_local+=coin_used;
				money_available_local+=money_used;
			}
		}else{
			//更改老的支付方式数据
			if(old_pay_type==3){
				//原来使用小巴币支付，那么首先把合计中的小巴币数量减去，然后增加本地可用小巴币数量
				coin_sum-=choosed_hour_price;
				coinnum_available_local+=choosed_hour_price;
			}else if(old_pay_type==1){
				//原来使用账户余额支付，那么首先把合计中的账户余额数量减去，然后增加本地可用账户余额数量
				money_sum-=old_delmoney;
				money_available_local+=old_delmoney;	
			}else if(old_pay_type==2){
				//原来使用小巴币支付，那么首先把合计中的小巴币数量减去，然后增加本地可用小巴币数量
				coupon_sum-=choosed_hour_price;				
				couponlist_available_local.unshift(getcouponDetailByRecordId(old_recordid));
			}else if(old_pay_type==4){
				//原来使用混合支付，那么首先把合计中的小巴币数量减去，然后增加本地可用小巴币数量
				coin_sum-=old_delmoney;
				money_sum-=(choosed_hour_price-old_delmoney);
				coinnum_available_local+=old_delmoney;
				money_available_local+=(choosed_hour_price-old_delmoney);
			}
		}
		
		var sum_str="";
		if(coupon_sum>0){
			sum_str+="优惠券抵扣￥"+coupon_sum;
		}
		if(coin_sum>0){
			sum_str+=" 小巴币"+coin_sum+"个 ";
		}
		if(money_sum>0){
			sum_str+=" 账户余额￥"+money_sum;
		}
		
		$("#coupon_sum").html(sum_str);
	//$("#coin_sum").html("小巴币："+coin_sum);
	//$("#money_sum").html("账户余额："+money_sum);
	
		setConfirmPayView();
	}
}

var successorderid=0;
function confirmPay(){
	show_loading();
	var active_url= "../sbook?action=bookCoach";
	var search_condition={"coachid":coachid,"studentid":studentid,"date":JSON.stringify(order_list),"version":"1.9.4","token" : token};
	if(parseInt(coachid)>0 && studentid>0){
		$.getJSON(active_url,search_condition,function(data)
		{
			var server_code = data.code;
			successorderid = data.successorderid;
			//alert(data.message);
			//弹出提示、关联成功订单信息
			//成功的div上跳转到课程选择页面
			
			if(server_code==1)
			{
				$('#pay_feedback_message').html("您已预约成功");
				var str = "<span onclick=\"location.href=\'uncompleorderdetail.jsp?orderid=" + successorderid +"\'\">" + "订单详情</span>";
				$('#orderDetailBtn').append(str);
			}else{
				$('#pay_feedback_message').html(data.message);
				var str = "<span onclick='payFeedbackClose("+server_code+")'>确定</span>";
				$('#orderDetailBtn').empty().append(str);
				//$('#pay_orderid').css('display','none');
				//$('#pay_orderid').attr("onclick","");
			}
			hide_loading();	
			showPaySuccess(server_code);
		});
	}
}


function showPaySuccess(server_code)
{
	
	$('.overlay-cancle').css('display','block');
	var widthC = $('.overlay-cancle .overlay-cancle-content').width();
	var heightC = $('.overlay-cancle .overlay-cancle-content').height();
	var widthW = $(window).width();
	var heightW = $(window).height();
	var w = (widthW-widthC)/2;
	var h = (heightW-heightC)/2;
	$('.overlay-cancle .overlay-cancle-content').css('left',w);
	$('.overlay-cancle .overlay-cancle-content').css('top',h);

	$('#pay_feedback_close').attr("onclick","payFeedbackClose("+server_code+")");
}

function payFeedbackClose(server_code){
	if(server_code==1){
		location.href="coursearrange.jsp?coachid="+coachid+"&studentid="+studentid;
	}else if(server_code==101||server_code==102||server_code==103){
		var order_origin = ${param.data_list_choose};
		location.href="orderconfirm.jsp?data_list_choose="+JSON.stringify(order_origin)+"&coachid=${param.coachid}&realname=${param.realname}&phone=${param.phone}&studentid=${param.studentid}&counttime=${param.counttime}&countmoney=${param.countmoney}";
	}else{
		$("#pay_feedback_popup").css('display','none');
	}
}


function changePayType(){
	$(this).addClass('active-check').siblings().removeClass('active-check');
}

function gotoCharge(){
	location.href="weixin?action=charge";
}

function setConfirmPayView(){
	var charge=false;
	
	for(i=0 ;i<order_list.length;i++){
		if(order_list[i].needcharge==true){
			charge=true
			break;
		}
	}
	
	if(charge==true){
		$("#pay_or_charge").html("充值");
		$("#confirm_pay").attr("onclick","gotoCharge()"); 
	}else if(need_charge==false){
		$("#pay_or_charge").html("确定");
		$("#confirm_pay").attr("onclick","confirmPay()"); 
	}
}

function show_loading(){
	$('.overlay-wait').css('display','block');
	var heightC = $('.overlay-wait-content').height();
	var height = $(window).height();
	var h = (height-heightC)/2;
	$('.overlay-wait-content').css('margin-top',h);
}

function hide_loading(){
	$('.overlay-wait').css('display','none');
}

$(document).ready(function ()
{
	$('.confirm-order-pay').on('click',function ()
	{
		$('.overlay,.overlay-inner').css('display','block');
	})
	
	$('#set_pay_type_btn').on('click',function ()
	{
		//点击确定按钮后，只有支付方式变更了才进行支付方式的变更和计算
		if(is_paytype_changed==true){
			$("#"+choosed_scheduleid+"_paytype").html(paytype_name);
		 	recalculatePayType(paytype_changed_order_num,new_pay_type,old_pay_type,old_delmoney);
		}
		
		$('.overlay,.overlay-inner').css('display','none');
		
	});
	$('#cancle_pay_type_btn').on('click',function ()
	{
		$('.overlay,.overlay-inner').css('display','none');
	});
	
	//$('#pay_feedback_close_btn').parent().attr("onclick","location.href='coursearrange.jsp?coachid="+coachid+"'");
	
	$('#pay_feedback_close_btn').click(function ()
	{
		$('.overlay-cancle').css('display','none');
	});
	
	
	var height = $(document).height();
	var hh = $(window).height();
	//alert (hh);
	//alert (height);
	$('.overlay').height(height);
	//$('.overlay-inner').height(hh);
	
// 	$('.overlay .overlay-content .overlay-content-body ul li').on('click',function()
// 	{
// 		$(this).addClass('active-check').siblings().removeClass('active-check');
// 	})
	
	getOrderList();
	getAllPayTypeBlance();

});


</script>
</body>
</html>
