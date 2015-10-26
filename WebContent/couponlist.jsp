<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.List" %>
<%@page import="com.daoshun.menu.SideMenu" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/style.css" rel="stylesheet" type="text/css">
<link href="css/ordinary.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="css/page.css" type="text/css" media="screen" />
<script src="js/jquery-1.11.2.min.js"></script>
<script src="coupon/js/coupon.js"></script>
<script src="js/common.js"></script>
<script type="text/javascript" src="js/page.js"></script>
<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
$(function(){
	var index = $("#index").val();
	$("#left_list_"+index).show();
	var j = $("#change_id").val();
	$("#change_"+j+index).addClass('left_list_mask');
	
	var coupontype = $("#hiddencoupontype").val();
	$("#searchtype").val(coupontype);
	
	var hiddenownertype = $("#hiddenownertype").val();
	$("#ownertype").val(hiddenownertype);
	
	var error=$("#errormessage").val();
	if(error==1){
		alert("小巴券剩余数量不足");
	}
	if(error==2){
		alert("请检查您当前网络是否畅通");
	}
});

function searchfromsubmit(){
	var starttime = $("#starttime").val();
	var endtime = $("#endtime").val();
	if(starttime!=''&&endtime!=''){
		if(starttime>endtime){
			alert("结束时间必须在开始时间之后！");
			return;
		}
	}
	$("#searchform").submit();
}
</script>
<style type="text/css">
.mask {
	position: fixed;
	top: 0px;
	bottom: 0px;
	left: 0px;
	right: 0px;
	background: #707070;
	opacity: 0.5;
	display: none;
}

.mask_last {
	position: fixed;
	background: #fff;
	width: 600px;
	height: 540px;
	margin: 0 auto;
		display: none;
}
.edit {
	position: fixed;
	top: 0px;
	bottom: 0px;
	left: 0px;
	right: 0px;
	background: #707070;
	opacity: 0.3;
	display: none;
}

.edit_last {
	position: fixed;
	background: #fff;
	width: 500px;
	height: 700px;
	margin: 0 auto;
	display: none;
}
</style>

<title>小巴券类型列表</title>
</head>
<input type="hidden" id="index" value="8" />
<input type="hidden" id="change_id" value="0"/>
<input type="hidden" id="errormessage" value="${errortype}"/>
<body onload="getSelfTop()" style="min-width:1770px">
<div id="content">
<jsp:include page="left.jsp"/>
<div id="content_form">
<div id="content_form_top">
	<div class="addbutton" onclick="turnaddcoupon()">+&nbsp;添加</div>
		<form id="searchform" action="getCouponList.do">
			<div id="content_form_top">
		
			<div class="searchbutton" onclick="searchfromsubmit();">
						<img src="imgs/common/searchicon.png" width=22px height=22px
							style="margin-top: 9px;">
			</div>					
			
		<div class="serchcontentdiv"style="float: left; margin-left: 50px; width: 175px"  >
		<input type="text" class="searchdiv" style="width:85px;text-align: center;font-family: 微软雅黑;float:left;" value="优惠券类型" readonly="readonly">
		<select id="searchtype" name="coupontype" class="searchdiv" style="width: 85px;float:left;">
		<option value="">全部</option> 
		<option value="1">时间券</option>
		<option value="2">抵价券</option>
		</select>
		<input type="hidden" id="hiddencoupontype" value="${coupontype }">
		</div>
		
		<div class="serchcontentdiv" style="float: left; margin-left: 50px; width: 196px">
		<select id="ownertype" name="ownertype" class="searchdiv" style="width: 100px;float:left;">
			<option value="" selected="selected">全部</option>
			<option value="0">平台发放</option>
			<option value="1">驾校发放</option>
			<option value="2">教练发放</option>
		</select>
		<input type="hidden" id="hiddenownertype" value="${ownertype }">
		<input id="inputordertotal" type="text" value="${ownerkey }" name="ownerkey" class="searchdiv" style="width: 91px;text-align: center;font-family: 微软雅黑;float:left;" value="">
		</div>
								
			<div class="serchcontentdiv"style="float: left; margin-left: 50px; width: 405px;float:left;"  >
					<input type="text" class="searchdiv" style="width: 119px;text-align: center;font-family: 微软雅黑;float:left;" value="到期时间" readonly="readonly">
					<input id="starttime" name="starttime" onclick="WdatePicker({startDate:'',dateFmt:'yyyy-MM-dd'})" type="text" class="searchdiv" style="width: 120px;text-align: center;font-family: 微软雅黑;float:left;" value="${starttime}">
					<input type="text" class="searchdiv" style="width: 30px;text-align: center;font-family: 微软雅黑;float:left;" value="到" readonly="readonly">
					<input id="endtime" name="endtime" onclick="WdatePicker({startDate:'',dateFmt:'yyyy-MM-dd'})" type="text" class="searchdiv" style="width: 120px;text-align: center;font-family: 微软雅黑;float:left;" value="${endtime}">
				</div>
			</div>
			</form>
</div>
<div id="content_form_table">
<table  border="0" cellspacing="0" cellpadding="0" style="width: 98%;">
<tr class="tr_th">
<!-- <th>全选 -->
<!-- <input type="checkbox" id="allcheck" value="0" onclick="selectall()"> -->
<!-- </th> -->
<th>优惠券ID</th>
<th>面值</th>
<th>类型</th>
<th>发券人类型</th>
<th>发放数量</th>
<th>发放时间</th>
<th>剩余数量</th>
<th>到期时间</th>
<th>操作</th>
</tr>
<s:iterator value="couponlist" var="couponlist" >
<tr class="tr_td">
<%-- <td style="width: 52px;" class="border_right_bottom"><input type="checkbox" name="checkbox" value="${couponid}" ></td> --%>
<td  style="width:50px;" class="border_right_bottom">${couponid}</td>
<td  style="width:200px;" class="border_right_bottom">${value }</td>
<td  style="width:150px;" class="border_right_bottom">
<s:if test="coupontype==1">
时间券
</s:if>
<s:elseif test="coupontype==2">
抵价券
</s:elseif>
</td>
<td  style="width:200px;" class="border_right_bottom">
<s:if test="ownertype==0">
平台发放
</s:if>
<s:elseif test="ownertype==1">
驾校发放:${schoolname }
</s:elseif>
<s:elseif test="ownertype==2">
教练发放:${cusername }
</s:elseif>
</td>
<td  style="width:200px;" class="border_right_bottom">${pub_count}</td>
<td  style="width:200px;" class="border_right_bottom">
<s:date name="addtime"  format="yyyy-MM-dd HH:mm:ss" /></td>
<td  style="width:100px;" class="border_right_bottom">${coach_rest}</td>
<td  style="width:200px;" class="border_right_bottom">
<s:date name="end_time"  format="yyyy-MM-dd HH:mm:ss" /></td>
<td  style="width:120px;" class="border_noright_bottom">


<!--<div class="table_del_button" onclick="delcoupon('${couponid}')">
<div class="table_button_del_icon"></div>
<div class="table_button_text" >删除</div>
</div>  -->
<div class="table_edit_button" onclick="givingcoupon('${couponid }')">
<div class="table_button_edit_icon"></div>
<div class="table_button_text">发放</div>
</div>


</td>
</tr>
</s:iterator>
<tr>
						<td colspan="8" style="height: 86px;">
							<div style="float: left; margin-top: 34px; margin-left: 20px;">
								总计：${total}条</div> <!-- 下部翻页 -->
					<div style="float: right; margin-top: 34px; margin-right: 20px;">
					<s:if test="%{pageCount>1}">

					<input type="hidden" value="${pageCount }" id="pageSize" />
					<input type="hidden" value="${pageIndex }" id="pageIndex" />
					<div id="untreatedpage"></div>
					<script type="text/javascript">
					//container 容器，count 总页数 pageindex 当前页数
					function setPage(container, count, pageindex) {
				    var container = container;
					var count = parseInt(count);
					var pageindex = parseInt(pageindex);
					var coupontype = $("#searchtype").val();
					var ownertype = $("#ownertype").val();
					var ownerkey = $("#inputordertotal").val();
					var starttime = $("#starttime").val();
					var endtime = $("#endtime").val();
					var a = [];
					  //总页数少于10 全部显示,大于10 显示前3 后3 中间3 其余....
					  if (pageindex == 1) {
						  //alert(pageindex);
					    a[a.length] = "<a onclick=\"\" class=\"hide_page_prev unclickprev on\"></a>";
					  } else {
					    a[a.length] = "<a onclick=\"previousPage("+pageindex+",'getCouponList.do?coupontype="+coupontype+"&ownertype="+ownertype+"&ownerkey="+ownerkey+"&starttime="+starttime+"&endtime="+endtime+"&')\" class=\"page_prev\"></a>";
					  }
					  function setPageList() {
					    if (pageindex == i) {
					      a[a.length] = "<a onclick=\"goPage('getCouponList.do?coupontype="+coupontype+"&ownertype="+ownertype+"&ownerkey="+ownerkey+"&starttime="+starttime+"&endtime="+endtime+"&',"+i+")\" class=\"on\">" + i + "</a>";
					    } else {
					      a[a.length] = "<a onclick=\"goPage('getCouponList.do?coupontype="+coupontype+"&ownertype="+ownertype+"&ownerkey="+ownerkey+"&starttime="+starttime+"&endtime="+endtime+"&',"+i+")\">" + i + "</a>";
					    }
					  }
					  //总页数小于10
					  if (count <= 10) {
					    for (var i = 1; i <= count; i++) {
					      setPageList();
					    };
					  } else {
						//总页数大于10页
					    if (pageindex <= 4) {
					      for (var i = 1; i <= 5; i++) {
					        setPageList();
					      }
					      a[a.length] = "...<a onclick=\"goPage('getCouponList.do?coupontype="+coupontype+"&ownertype="+ownertype+"&ownerkey="+ownerkey+"&starttime="+starttime+"&endtime="+endtime+"&',"+count+")\">" + count + "</a>";
					    } else if (pageindex >= count - 3) {
					      a[a.length] = "<a onclick=\"goPage('getCouponList.do?coupontype="+coupontype+"&ownertype="+ownertype+"&ownerkey="+ownerkey+"&starttime="+starttime+"&endtime="+endtime+"&',1)\">1</a>...";
					      for (var i = count - 4; i <= count; i++) {
					        setPageList();
					      };
					    } else { //当前页在中间部分
					      a[a.length] = "<a onclick=\"goPage('getCouponList.do?coupontype="+coupontype+"&ownertype="+ownertype+"&ownerkey="+ownerkey+"&starttime="+starttime+"&endtime="+endtime+"&',1)\">1</a>...";
					      for (var i = pageindex - 2; i <= pageindex+2; i++) {
					        setPageList();
					      }
					      a[a.length] = "...<a onclick=\"goPage('getCouponList.do?coupontype="+coupontype+"&ownertype="+ownertype+"&ownerkey="+ownerkey+"&starttime="+starttime+"&endtime="+endtime+"&',"+count+")\">" + count + "</a>";
					    }
					  }
					  if (pageindex == count) {
						    a[a.length] = "<a onclick=\"\" class=\"hide_page_next unclicknext\"></a> 共"+count+"页  到第  "+
						    "<input type=\"text\" class=\"jump_num\" id=\"topage\"/> 页"+
						    "<a class=\"jump_btn\" onclick=\"gotoPage('getCouponList.do?coupontype="+coupontype+"&ownertype="+ownertype+"&ownerkey="+ownerkey+"&starttime="+starttime+"&endtime="+endtime+"&',"+$("#pageSize").val()+")\")\">"+
						    "<a id='page_msg'></a>";
						  } else {
						    a[a.length] = 
						    	"<a onclick=\"nextPage("+$("#pageIndex").val()+",'getCouponList.do?coupontype="+coupontype+"&ownertype="+ownertype+"&ownerkey="+ownerkey+"&starttime="+starttime+"&endtime="+endtime+"&')\" "+
						    	"class=\"page_next\"></a> 共"+count+"页 到第 "+
						    "<input type=\"text\" class=\"jump_num\" id=\"topage\"/> 页"+
						    "<a class=\"jump_btn\" onclick=\"gotoPage('getCouponList.do?coupontype="+coupontype+"&ownertype="+ownertype+"&ownerkey="+ownerkey+"&starttime="+starttime+"&endtime="+endtime+"&',"+$("#pageSize").val()+")\">"+
						    "<a id='page_msg'></a>";
						  }
// 					  a[a.length]="<a href='#' onclick='addunit()' style='float: right;position: relative;right: 50px;padding: 0px; margin: 0px; top: 3px;'><img src='imgs/add_.png'></a>";
					  container.innerHTML = a.join("");
					} 
					setPage(document.getElementById("untreatedpage"),parseInt($("#pageSize").val()),parseInt($("#pageIndex").val()));
					</script>
								</s:if>
							</div>
						</td>
					</tr>
</table>

</div>
</div>
</div>
<!-- 发放小巴券弹框 -->
	<div id="mask" class="mask"></div>
	<div id="mask_sec" style="position: fixed; width: 100%; height: 500px;z-index: 300;">
		<div id="mask_last" class="mask_last">
		<form id="alertform" method="post"  enctype="multipart/form-data">
		<div style="position: fixed; width: 400px;   padding-bottom: 20px;background: #4cc2ff;margin-left: 100px;margin-top: 40px;">
		<div style="height:50px;margin-top:10px;text-align:center;line-height:50px;">
		<input  type="radio" name="givingtype" id="givingtype" value="1" onchange="changeserachdiv()" checked="checked" style="width:20px;height:20px ;position: relative;  top: 5px;">所有学员&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input  type="radio" name="givingtype" id="givingtype" value="2" onchange="changeserachdiv()" style="width:20px;height:20px;  position: relative;  top: 5px;margin-left: -40px">单个学员
		<input  type="radio" name="givingtype" id="givingtype" value="3" onchange="changeserachdiv()" style="width:20px;height:20px;  position: relative;  top: 5px;margin-left: 15px">部分学员
		<input  type="radio" name="givingtype" id="givingtype" value="4" onchange="changeserachdiv()" style="width:20px;height:20px;  position: relative;  top: 5px;margin-left: 15px">关系发放
		</div>
		<div id="givingafter" style="height:50px;margin-top:10px;text-align:center;line-height:50px;">
		<input  type="radio" name="pushtype" value="1" style="width:20px;height:20px ;position: relative;  top: 5px;">短信通知&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input  type="radio" name="pushtype" value="2" checked="checked" style="width:20px;height:20px;  position: relative;  top: 5px;">推送通知
		</div>
		
		<div>
		<input name="exslfile" id="exslfile" type="file" style="margin-left: 78px;display: none" >
		</div>
		<div id="textareaid" style="height:100px;margin-top:10px;text-align:center;line-height:50px;">
		<textarea id="pushcontent" name="pushcontent"  style="resize:none;width: 240px;height: 80px;margin: auto;font-size: 18px;"  placeholder="请输入通知内容"></textarea>
		</div>
		<div style="height:50px;margin-top:5px;text-align:center;line-height:50px;width: 300px">
		
		
		<div style="float: left;margin-left: 110px;margin-top: -5px" >单人发放数量：</div>
		<div style="float: right;">
		<input  type="text" id="pushnum" name="pushnum"  style="width:100px;height:30px;  position: relative;  top: 5px;">
		</div>
		</div>
		
		<input type="hidden" value="" id="couponid" name="couponid">
		<input type="button" style="width: 100px;height: 40px;margin: auto;margin-left: 100px;margin-top: 25px;font-size: 18px" value="确定" onclick="addcoupongiving()">
		<input type="button" style="width: 100px;height: 40px;margin: auto;margin-top: -40px;font-size: 18px" value="取消" onclick="unshowaddschool()">
		</div>
		</form>
		</div>
	</div>
</body>
</html>