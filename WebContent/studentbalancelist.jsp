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
});
function dataToLead(){
	var index = $("#index").val();
	var change_id=$("#change_id").val();
	window.location.href="jumpStudentToLead.do?index="+index+"&change_id="+change_id;
}

function dataExport(){
	if (confirm("确认导出学员信息？")) {
		var studentdate = document.getElementsByName("C1");
		var data='';
		for(var i=0;i<studentdate.length;i++){
			if(studentdate[i].checked == true){
				data+=studentdate[i].value+",";
			}
		}
		if(data==''){
			alert("请选择导出信息！");
		}else{
			window.location.href="studentdataExport.do?studentdate="+data;
		}
	}
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
<title>学员列表</title>
</head>
<input type="hidden" id="index" value="1" />
<input type="hidden" id="change_id" value="5"/>
<body onload="getTop()" style="min-width: 1500px">
	<div id="content">
		<jsp:include page="left.jsp" />
		<div id="content_form">
			<div id="content_form_top">
		
				
					<div class="searchbutton" onclick="studentsearch()">
						<img src="imgs/common/searchicon.png" width=22px height=22px
							style="margin-top: 9px;" >
					</div>
					<div class="serchcontentdiv" style="float: left; margin-left: 50px; width: 156px" >
						<input  type="text" class="searchdiv" style="width: 50px;text-align: center;font-family: 微软雅黑;" value="姓名" readonly="readonly">
						<input id="realname" type="text" class="searchdiv" style="width:100px;font-family: 微软雅黑;" value="${searchname}"/>
					</div>

					<div class="serchcontentdiv" style="float: left; margin-left: 50px; width: 156px" >
						<input type="text" class="searchdiv" style="width: 50px;text-align: center;font-family: 微软雅黑;" value="电话" readonly="readonly">
						<input id="phone" type="text" class="searchdiv" style="width:100px;font-family: 微软雅黑;" value="${searchphone}" onchange="phoneisNum()" onkeyup="value=value.replace(/[^\d]/g,'')"/>
					</div>
					

					
<div class="serchcontentdiv"style="float: left; margin-left: 50px; width: 309px"  >
		<input type="text" class="searchdiv" style="width: 71px;text-align: center;font-family: 微软雅黑;" value="注册时间" readonly="readonly">
		<input id="starttime" type="text" class="searchdiv" style="width: 99px;text-align: center;font-family: 微软雅黑;border-right: #fff;"  value="${minsdate}" onclick="WdatePicker({startDate:'',dateFmt:'yyyy-MM-dd'})" placeholder="&nbsp;请选择开始日期">
		<input type="text" class="searchdiv" style="width: 20px;text-align: center;font-family: 微软雅黑; border-right: #fff;" value="到" readonly="readonly">
		<input id="endtime" type="text" class="searchdiv" style="width: 105px;text-align: center;font-family: 微软雅黑;"value="${maxsdate}" onclick="WdatePicker({startDate:'',dateFmt:'yyyy-MM-dd'})" placeholder="&nbsp;请选择结束日期">
	</div>





			</div>
			<div id="content_form_top" >
						<div class="addbutton" style="margin-left: 16px;width: 100px;background: #ff0000;cursor: default;"><b>&nbsp;批量操作按钮:</b></div>
						<div class="addbutton" style="margin-left: 35px;background: #4cc2ff" onclick="addstudentbalance(-1)">添加余额</div>
						<div class="addbutton" style="background: #4cc2ff" onclick="lessenstudentbalance(-1)">减少余额</div>
			</div>
			<div id="content_form_table">
			<form action="" id="doallForm" method="post">
				<table border="0" cellspacing="0" cellpadding="0"
					style="width: 98%;">
					<tr class="tr_th">
					<th>全选
					<input type="checkbox" id="allcheck" value="0" onclick="selectall()">
					</th>
						<th>学员ID</th>
						<th>真实姓名</th>
						<th>学员手机号码</th>
						<th>性别</th>
						<th>年龄</th>
						<th>余额</th>
						<th>冻结金额</th>
						<th>综合评分</th>
						<th>教练验证状态</th>
						<th>注册时间</th>
						<th>操作</th>

					</tr>
					<s:iterator value="suserlist" var="suser">
						<tr class="tr_td">
							<td style="width: 52px;" class="border_right_bottom"><input type="checkbox" name="checkbox" value="${studentid}" ></td>
							<td style="width: 50px;" class="border_right_bottom">${studentid}</td>
							<td style="width: 80px;" class="border_right_bottom">${realname}</td>
							<td style="width: 100px;" class="border_right_bottom">${phone}</td>
							<s:if test="gender==1">
							<td style="width: 50px;" class="border_right_bottom">男</td>
							</s:if>
							<s:else>
							<td style="width: 50px;" class="border_right_bottom">女</td>
							</s:else>
							<td style="width: 80px;" class="border_right_bottom">${age}</td>
							<td style="width: 80px;" class="border_right_bottom">${money}</td>
							<td style="width: 80px;" class="border_right_bottom">${fmoney}</td>
							<td style="width: 100px;" class="border_right_bottom">${score}&nbsp;分</td>
							
							<s:if test="coachstate==0">
							<td style="width: 100px;" class="border_right_bottom">未认证</td>
							</s:if>
						<s:elseif test="coachstate==1">
								<td style="width: 100px;" class="border_right_bottom">认证</td>
						</s:elseif>
						<s:else>
						<td style="width: 100px;" class="border_right_bottom">审核未通过</td>
						</s:else>
							<td style="width: 100px;" class="border_right_bottom">
							<s:date name="addtime" format="yyyy-MM-dd HH:mm:ss" />
							</td>
							<td style="width: 150px;" class="border_noright_bottom">
								<div class="table_edit_button" style="width: 80px;background:#1bbc9b">
									<div class="table_button_edit_icon"></div>
									<div class="table_button_text"
										onclick="addstudentbalance(${studentid})">添加余额</div>
								</div>
								<div class="table_edit_button" style="width: 90px;background:#f83a22">
									<div class="table_button_edit_icon"></div>
									<div class="table_button_text"
										onclick="lessenstudentbalance(${studentid})">减少余额</div>
								</div>
							</td>
						</tr>
					</s:iterator>
					<tr>
						<td colspan="11" style="height: 86px;">
							<div style="float: left; margin-top: 34px; margin-left: 20px;">
								总计：${total}条</div> <!-- 下部翻页 -->
							<div style="margin-top: 34px; margin-right: 20px;">
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
					var realname=$("#realname").val();
					var phone=$("#phone").val();
					var starttime=$("#starttime").val();
					var endtime=$("#endtime").val();
					var j = $("#change_id").val();
					var index=$("#index").val();
					var a = [];
					  //总页数少于10 全部显示,大于10 显示前3 后3 中间3 其余....
					  if (pageindex == 1) {
						  //alert(pageindex);
					    a[a.length] = "<a onclick=\"\" class=\"hide_page_prev unclickprev on\"></a>";
					  } else {
					    a[a.length] = "<a onclick=\"previousPage("+pageindex+",'getStudentBalance.do?searchname="+realname+"&searchphone="+phone+"&minsdate="+starttime+"&maxsdate="+endtime +"&index="+index+"&change_id"+j+"&')\" class=\"page_prev\"></a>";
					  }
					  function setPageList() {
					    if (pageindex == i) {
					      a[a.length] = "<a onclick=\"goPage('getStudentBalance.do?searchname="+realname+"&searchphone="+phone+"&minsdate="+starttime+"&maxsdate="+endtime +"&index="+index+"&change_id"+j+"&',"+i+")\" class=\"on\">" + i + "</a>";
					    } else {
					      a[a.length] = "<a onclick=\"goPage('getStudentBalance.do?searchname="+realname+"&searchphone="+phone+"&minsdate="+starttime+"&maxsdate="+endtime +"&index="+index+"&change_id"+j+"&',"+i+")\">" + i + "</a>";
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
					      a[a.length] = "...<a onclick=\"goPage('getStudentBalance.do?searchname="+realname+"&searchphone="+phone+"&minsdate="+starttime+"&maxsdate="+endtime+"&index="+index+"&change_id"+j+"&',"+count+")\">" + count + "</a>";
					    } else if (pageindex >= count - 3) {
					      a[a.length] = "<a onclick=\"goPage('getStudentBalance.do?searchname="+realname+"&searchphone="+phone+"&minsdate="+starttime+"&maxsdate="+endtime+"&index="+index+"&change_id"+j+"&',1)\">1</a>...";
					      for (var i = count - 4; i <= count; i++) {
					        setPageList();
					      };
					    } else { //当前页在中间部分
					      a[a.length] = "<a onclick=\"goPage('getStudentBalance.do?searchname="+realname+"&searchphone="+phone+"&minsdate="+starttime+"&maxsdate="+endtime +"&index="+index+"&change_id"+j+"&',1)\">1</a>...";
					      for (var i = pageindex - 2; i <= pageindex+2; i++) {
					        setPageList();
					      }
					      a[a.length] = "...<a onclick=\"goPage('getStudentBalance.do?searchname="+realname+"&searchphone="+phone+"&minsdate="+starttime+"&maxsdate="+endtime+"&index="+index+"&change_id"+j+"&',"+count+")\">" + count + "</a>";
					    }
					  }
					  if (pageindex == count) {
						    a[a.length] = "<a onclick=\"\" class=\"hide_page_next unclicknext\"></a> 共"+count+"页  到第  "+
						    "<input type=\"text\" class=\"jump_num\" id=\"topage\"/> 页"+
						    "<a class=\"jump_btn\" onclick=\"gotoPage('getStudentBalance.do?searchname="+realname+"&searchphone="+phone+"&minsdate="+starttime+"&maxsdate="+endtime +"&index="+index+"&change_id"+j+"&',"+$("#pageSize").val()+")\")\">"+
						    "<a id='page_msg'></a>";
						  } else {
						    a[a.length] = 
						    	"<a onclick=\"nextPage("+$("#pageIndex").val()+",'getStudentBalance.do?searchname="+realname+"&searchphone="+phone+"&minsdate="+starttime+"&maxsdate="+endtime +"&index="+index+"&change_id"+j+"&')\" "+
						    	"class=\"page_next\"></a> 共"+count+"页 到第 "+
						    "<input type=\"text\" class=\"jump_num\" id=\"topage\"/> 页"+
						    "<a class=\"jump_btn\" onclick=\"gotoPage('getStudentBalance.do?searchname="+realname+"&searchphone="+phone+"&minsdate="+starttime+"&maxsdate="+endtime +"&',"+$("#pageSize").val()+")\">"+
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
				<input type="hidden" id="changeallmoney" name="money">
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
		<input id="morebalance" type="text" style="width: 170px;height: 40px;margin: auto;margin-left: 70px;margin-top: 45px;font-size: 18px;text-align: center;"  placeholder="请输添加金额"/>
		<input id="morereason" type="text" style="width: 170px;height: 40px;margin: auto;margin-left: 70px;margin-top: 25px;font-size: 18px;text-align: center;"  placeholder="请输添加原因" />
		<input type="button" style="width: 100px;height: 40px;margin: auto;margin-left: 50px;margin-top: 40px;font-size: 18px" value="确定" onclick="addStudentBalanceSubmit()">
		<input type="button" style="width: 100px;height: 40px;margin: auto;margin-left: 180x;margin-top: -40px;font-size: 18px" value="取消" onclick="unshowchangegmoney()">
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
		<input id="lessbalance" type="text" style="width: 170px;height: 40px;margin: auto;margin-left: 70px;margin-top: 45px;font-size: 18px;text-align: center;"  placeholder="输入减少金额"/>
		<input id="lessreason" type="text" style="width: 170px;height: 40px;margin: auto;margin-left: 70px;margin-top: 25px;font-size: 18px;text-align: center;"  placeholder="请输添加原因" />
		<input type="button" style="width: 100px;height: 40px;margin: auto;margin-left: 50px;margin-top: 40px;font-size: 18px" value="确定" onclick="lessenStudentBalanceSubmit()">
		<input type="button" style="width: 100px;height: 40px;margin: auto;margin-left: 180x;margin-top: -40px;font-size: 18px" value="取消" onclick="unshowchangelevel()">
		<input type="hidden" value="" id="allchangelevel">
		</div>
		</div>
	</div>
	
</body>
</html>