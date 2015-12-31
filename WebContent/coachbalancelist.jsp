<%@page import="org.springframework.web.context.request.SessionScope"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.daoshun.menu.SideMenu"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/style.css" rel="stylesheet" type="text/css">
<link href="css/ordinary.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="css/page.css" type="text/css" media="screen" />
<script src="js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/page.js"></script>
<script type="text/javascript" src="coach/js/coachdetail.js"></script>
<script type="text/javascript" src="systemconfig/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
$(function(){
	var index = $("#index").val();
	$("#left_list_"+index).show();
	var j = $("#change_id").val();
	$("#change_"+j+index).addClass('left_list_mask');
	
	var checkstates=$("#checkstatus").val();
	if(checkstates==2){
		$("#check2").attr("selected",true);
	}
	if(checkstates==1){
		$("#check1").attr("selected",true);
	}
	
	var schoolstate=$("#schoolstate").val();
	if(schoolstate!=0){
		if(schoolstate==-1){
			$("#school-1").attr("selected",true);
	}else{
		$("#school"+schoolstate).attr("selected",true);
		}
	}
});

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
	
	width: 500px;
	height: 300px;
	margin: 0 auto;
	display: none;
}

.level {
	position: fixed;
	top: 0px;
	bottom: 0px;
	left: 0px;
	right: 0px;
	background: #707070;
	opacity: 0.3;
	display: none;
}

.level_last {
	position: fixed;
	background: #fff;
	width: 500px;
	height: 300px;
	margin: 0 auto;
	display: none;
}
</style>


<title>教练余额管理</title>
</head>
<input type="hidden" id="change_id" value="8"/>
<input type="hidden" id="index" value="1" />
<input type="hidden" id=checkstatus value="${checkstate}">
<input type="hidden" id=schoolstate value="${driveSchoolname}">
<body onload="getTop()" style="min-width: 1850px">
	<div id="content">
		<jsp:include page="left.jsp" />
		<div id="content_form">
			<div id="content_form_top" >
		
					<div class="searchbutton" onclick="searchBalance()">
						<img src="imgs/common/searchicon.png" width=22px height=22px
							style="margin-top: 9px;" >
					</div>
					<div class="serchcontentdiv" style="float: left; margin-left: 50px; width: 156px" >
						<input  type="text" class="searchdiv" style="width: 50px;text-align: center;font-family: 微软雅黑;" value="姓名" readonly="readonly">
						<input id="realname" type="text" class="searchdiv" style="width:100px;font-family: 微软雅黑;" value="${searchname}"/>
					</div>

					<div class="serchcontentdiv" style="float: left; margin-left: 50px; width: 156px" >
						<input type="text" class="searchdiv" style="width: 50px;text-align: center;font-family: 微软雅黑;" value="电话" readonly="readonly">
						<input id="phone" type="text" class="searchdiv" style="width:100px;font-family: 微软雅黑;" value="${searchphone}" onkeyup="value=value.replace(/[^\d]/g,'')" onchange="coachPhoneidNum()"/>
					</div>
					
					
<div class="serchcontentdiv"style="float: left; margin-left: 50px; width: 160px"  >
<input type="text" class="searchdiv" style="width:70px;font-family: 微软雅黑;text-align: center;" value="所属驾校" readonly="readonly"/>
		<select id="driveschool" class="searchdiv" style="width: 85px;">
				<option value="0">不限</option>
				<option id="school-1" value="-1">无驾校</option>
			<s:iterator value="driveSchoollist" var="driveSchool">
				<option id="school${schoolid}" value="${schoolid}">${name}</option>
			</s:iterator>
		</select>
	</div>
	
	
	
	
	<div class="serchcontentdiv"style="float: left; margin-left: 50px; width: 160px"  >
<input  type="text" class="searchdiv" style="width:70px;font-family: 微软雅黑;text-align: center;" value="审核状态" readonly="readonly"/>
		<select id="checkstate" class="searchdiv" style="width: 85px;">
				<option value="4" selected="selected">不限</option>
				<option id="check1" value="0">资料不完善</option>
				<option id="check2" value="1">等待审核</option>
				<option id="check3" value="2">审核通过</option>
				<option id="check4" value="3">审核未通过</option>
				
		</select>
	</div>
	
					
<div class="serchcontentdiv" style="float: left; margin-left: 50px; width: 156px" >
						<input type="text" class="searchdiv" style="width: 50px;text-align: center;font-family: 微软雅黑;" value="车牌号" readonly="readonly">
						<input id="carlicense" type="text" class="searchdiv" style="width:100px;font-family: 微软雅黑;"/>
					</div>
			</div>
			
			
			<div id="content_form_top2" >
			
						<div class="addbutton" style="margin-left: 16px;width: 100px;background: #ff0000;cursor: default;"><b>&nbsp;批量操作按钮:</b></div>
										<div class="addbutton" style="margin-left: 35px;background: #4cc2ff" onclick="addbalance(-1)">添加余额</div>
									  <div class="addbutton" style="background: #4cc2ff" onclick="lessenbalance(-1)">减少余额</div>
			</div>
			<div id="content_form_table">
			<form action="" id="doallForm" method="post">
				<table border="0" cellspacing="0" cellpadding="0"
					style="width: 98%;">
					<tr class="tr_th">
					<th>全选
					<input type="checkbox" id="allcheck" value="0" onclick="selectall()">
					</th>
						<th>ID</th>
						<th>真实姓名</th>
						<th>电话号码</th>
						<th>性别</th>
						<th>年龄</th>
						<th>教龄</th>
						<th>余额</th>
						<th>综合评分</th>
						<th>状态</th>
						<th>冻结金额</th>
						<th>保证金</th>
						<th>地址</th>
						<th>操作</th>

					</tr>
					<s:iterator value="cuserlist" var="cuser">
						<tr class="tr_td">
						<td style="width: 52px;" class="border_right_bottom"><input type="checkbox" name="checkbox" value="${coachid}" ></td>
						
							<td style="width: 52px;" class="border_right_bottom">${coachid}</td>
							<td style="width: 100px;" class="border_right_bottom">${realname}</td>
							<td style="width: 150px;" class="border_right_bottom">${phone}</td>
							<s:if test="gender==1">
							<td style="width: 50px;" class="border_right_bottom">男</td>
							</s:if>
							<s:else>
							<td style="width: 50px;" class="border_right_bottom">女</td>
							</s:else>
							<td style="width: 80px;" class="border_right_bottom">${age}</td>
							<td style="width: 50px;" class="border_right_bottom">${years}&nbsp;年</td>
							<td style="width: 100px;" class="border_right_bottom">${money}</td>
							<td style="width: 80px;" class="border_right_bottom">${score}&nbsp;分</td>
							<s:if test="state==0">
							<td style="width: 100px;" class="border_right_bottom">资料不完善</td>
							</s:if>
							<s:elseif test="state==1">
								<td style="width: 100px;" class="border_right_bottom">等待审核</td>
							</s:elseif>
							<s:elseif test="state==2">
							<td style="width: 100px;" class="border_right_bottom">审核通过</td>
							</s:elseif>
							<s:else >
							<td style="width: 100px;" class="border_right_bottom">审核未通过</td>
							</s:else>
							<td style="width: 100px;" class="border_right_bottom">${fmoney}</td>
							<td style="width: 100px;" class="border_right_bottom">${gmoney}</td>
							<td style="width: 250px;" class="border_right_bottom">${address}</td>

							<td style="width: 700px;" class="border_noright_bottom">

								<div class="table_edit_button" style="width: 80px;background:#1bbc9b">
									<div class="table_button_edit_icon"></div>
									<div class="table_button_text"
										onclick="addbalance(${coachid})">添加余额</div>
								</div>
								<div class="table_edit_button" style="width: 90px;background:#f83a22">
									<div class="table_button_edit_icon"></div>
									<div class="table_button_text"
										onclick="lessenbalance(${coachid})">减少余额</div>
								</div>
							</td>
						</tr>
					</s:iterator>
					<tr>
						<td colspan="13" style="height: 86px;">
							<div style="float: left; margin-top: 34px; margin-left: 20px;">
								总计：${total}条</div> <!-- 下部翻页 -->
							<div style="margin-top: 34px; margin-right: 20px;">
								<s:if test="%{pageCount>1}">

									<input type="hidden" value="${pageCount }" id="pageSize" />
									<input type="hidden" value="${pageIndex }" name="pageIndex" id="pageIndex" />
									<div id="untreatedpage"></div>
									<script type="text/javascript">
					//container 容器，count 总页数 pageindex 当前页数
					//这里要改 改改改改！！！！！！！！！！！！！！！！！！！！！！！
					function setPage(container, count, pageindex) {
					var container = container;
					var count = parseInt(count);
					var pageindex = parseInt(pageindex);
					var state=$("#state").val();
					var realname=$("#realname").val();
					var phone=$("#phone").val();
					var driveSchoolname=$("#schoolstate").val();
					var carlicense=$("#carlicense").val();
					var index=$("#index").val();
					var j = $("#change_id").val();
					var checkstate = $("#checkstatus").val();
					var a = [];
					  //总页数少于10 全部显示,大于10 显示前3 后3 中间3 其余....
					  if (pageindex == 1) {
						  //alert(pageindex);
					    a[a.length] = "<a onclick=\"\" class=\"hide_page_prev unclickprev on\"></a>";
					  } else {
					    a[a.length] = "<a onclick=\"previousPage("+pageindex+",'getCoachBalance.do?searchname=" + realname+ "&searchphone=" + phone + "&driveSchoolname=" + driveSchoolname+ "&carlicense=" + carlicense + "&index=" + index+"&change_id="+j+"&checkstate="+checkstate+"&')\" class=\"page_prev\"></a>";
					  }
					  function setPageList() {
					    if (pageindex == i) {
					      a[a.length] = "<a onclick=\"goPage('getCoachBalance.do?searchname="+realname+"&searchphone="+phone+"&driveSchoolname="+driveSchoolname+"&carlicense="+carlicense+"&index="+index+"&change_id"+j+"&',"+i+")\" class=\"on\">" + i + "</a>";
					    } else {
					      a[a.length] = "<a onclick=\"goPage('getCoachBalance.do?searchname=" + realname+ "&searchphone=" + phone + "&driveSchoolname=" + driveSchoolname+ "&carlicense=" + carlicense + "&index=" + index+"&change_id="+j+"&checkstate="+checkstate+"&',"+i+")\">" + i + "</a>";
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
					      a[a.length] = "...<a onclick=\"goPage('getCoachBalance.do?searchname=" + realname+ "&searchphone=" + phone + "&driveSchoolname=" + driveSchoolname+ "&carlicense=" + carlicense + "&index=" + index+"&change_id="+j+"&checkstate="+checkstate+"&',"+count+")\">" + count + "</a>";
					    } else if (pageindex >= count - 3) {
					      a[a.length] = "<a onclick=\"goPage('getCoachBalance.do?searchname=" + realname+ "&searchphone=" + phone + "&driveSchoolname=" + driveSchoolname+ "&carlicense=" + carlicense + "&index=" + index+"&change_id="+j+"&checkstate="+checkstate+"&',1)\">1</a>...";
					      for (var i = count - 4; i <= count; i++) {
					        setPageList();
					      };
					    } else { //当前页在中间部分
					      a[a.length] = "<a onclick=\"goPage('getCoachBalance.do?searchname=" + realname+ "&searchphone=" + phone + "&driveSchoolname=" + driveSchoolname+ "&carlicense=" + carlicense + "&index=" + index+"&change_id="+j+"&checkstate="+checkstate+"&',1)\">1</a>...";
					      for (var i = pageindex - 2; i <= pageindex+2; i++) {
					        setPageList();
					      }
					      a[a.length] = "...<a onclick=\"goPage('getCoachBalance.do?searchname=" + realname+ "&searchphone=" + phone + "&driveSchoolname=" + driveSchoolname+ "&carlicense=" + carlicense + "&index=" + index+"&change_id="+j+"&checkstate="+checkstate+"&',"+count+")\">" + count + "</a>";
					    }
					  }
					  if (pageindex == count) {
						    a[a.length] = "<a onclick=\"\" class=\"hide_page_next unclicknext\"></a> 共"+count+"页  到第  "+
						    "<input type=\"text\" class=\"jump_num\" id=\"topage\"/> 页"+
						    "<a class=\"jump_btn\" onclick=\"gotoPage('getCoachBalance.do?searchname=" + realname+ "&searchphone=" + phone + "&driveSchoolname=" + driveSchoolname+ "&carlicense=" + carlicense + "&index=" + index+"&change_id="+j+"&checkstate="+checkstate+"&',"+$("#pageSize").val()+")\")\">"+
						    "<a id='page_msg'></a>";
						  } else {
						    a[a.length] = 
						    	"<a onclick=\"nextPage("+$("#pageIndex").val()+",'getCoachBalance.do?searchname=" + realname+ "&searchphone=" + phone + "&driveSchoolname=" + driveSchoolname+ "&carlicense=" + carlicense + "&index=" + index+"&change_id="+j+"&checkstate="+checkstate+"&')\" "+
						    	"class=\"page_next\"></a> 共"+count+"页 到第 "+
						    "<input type=\"text\" class=\"jump_num\" id=\"topage\"/> 页"+
						    "<a class=\"jump_btn\" onclick=\"gotoPage('getCoachBalance.do?searchname="+realname+"&searchphone="+phone+"&driveSchoolname="+driveSchoolname+"&carlicense="+carlicense+"&index="+index +"&',"+$("#pageSize").val()+")\">"+
						    "<a id='page_msg'></a>";
						  }
					  container.innerHTML = a.join("");
					} 
					setPage(document.getElementById("untreatedpage"),parseInt($("#pageSize").val()),parseInt($("#pageIndex").val()));
					</script>
								</s:if>
							</div>
						</td>
					</tr>
				</table>
				<input type="hidden" id="changeallmoney" name="money">
				<input type="hidden" id="changealllevel" name="alllevel">
				<input type="hidden" id="changeallfrozenstart" name="allfrozenstart">
				<input type="hidden" id="changeallfrozenend" name="allfrozenend">
</form>
			</div>
		</div>
	</div>



<!-- 添加余额弹框 -->
	<div id="mask" class="mask"></div>
	<div id="mask_sec" style="position: fixed; width: 100%; height: 300px;z-index: 300">
		<div id="mask_last" class="mask_last">
		<div style="position: fixed; width: 300px; height: 200px;background: #4cc2ff;margin-left: 100px;margin-top: 50px;">
		<input type="hidden" id="gmoney_coachid" value=""/>
		<input id="morebalance" type="text" style="width: 170px;height: 40px;margin: auto;margin-left: 70px;margin-top: 45px;font-size: 18px;text-align: center;"  placeholder="请输添加金额" onkeyup="value=value.replace(/[^\d]/g,'')"/>
		<input id="morereason" type="text" style="width: 170px;height: 40px;margin: auto;margin-left: 70px;margin-top: 25px;font-size: 18px;text-align: center;"  placeholder="请输添加原因" />
		<input type="button" style="width: 100px;height: 40px;margin: auto;margin-left: 50px;margin-top: 40px;font-size: 18px" value="确定" onclick="addBalanceSubmit()">
		<input type="button" style="width: 100px;height: 40px;margin: auto;margin-left: 180px;margin-top: -40px;font-size: 18px" value="取消" onclick="unshowchangegmoney()">
		<input type="hidden" value="" id="changebalance">
		</div>
		</div>
	</div>
	
	<!-- 减少余额弹框 -->
	<div id="level" class="level"></div>
	<div id="level_sec" style="position: fixed; width: 100%; height: 300px;z-index: 300;">
		<div id="level_last" class="level_last">
		<div style="position: fixed; width: 300px; height: 200px;background: #4cc2ff;margin-left: 100px;margin-top: 50px;">
		<input type="hidden" id="level_coachid" value=""/>
		<input id="lessbalance" type="text" style="width: 170px;height: 40px;margin: auto;margin-left: 70px;margin-top: 45px;font-size: 18px;text-align: center;"  placeholder="输入减少金额" onkeyup="value=value.replace(/[^\d]/g,'')"/>
		<input id="lessreason" type="text" style="width: 170px;height: 40px;margin: auto;margin-left: 70px;margin-top: 25px;font-size: 18px;text-align: center;"  placeholder="请输添加原因" />
		<input type="button" style="width: 100px;height: 40px;margin: auto;margin-left: 50px;margin-top: 40px;font-size: 18px" value="确定" onclick="lessenBalanceSubmit()">
		<input type="button" style="width: 100px;height: 40px;margin: auto;margin-left: 180px; margin-top: -40px;font-size: 18px" value="取消" onclick="unshowchangelevel()">
		<input type="hidden" value="" id="allchangelevel">
		</div>
		</div>
	</div>
</body>
</html>