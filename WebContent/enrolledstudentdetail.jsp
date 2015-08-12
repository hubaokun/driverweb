<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="com.daoshun.menu.SideMenu"%>
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

.alertbox {
	position: fixed;
	top: 0px;
	bottom: 0px;
	left: 0px;
	right: 0px;
	background: #707070;
	opacity: 0.3;
	display: none;
}

.alertbox_last{
	position: fixed;
	background: #fff;
	width: 600px;
	height: 350px;
	margin: 0 auto;
	display: none;
}
</style>
<title>已报名学员列表</title>
</head>
<input type="hidden" id="index" value="1" />
<input type="hidden" id="change_id" value="3"/>
<body onload="getTop()" style="min-width: 1500px">
	<div id="content">
		<jsp:include page="left.jsp" />
		<div id="content_form">
			<div id="content_form_top">
			
					<!-- <div class="addbutton" onclick="showadduser()">+&nbsp;添加</div>-->
					<div class="searchbutton" onclick="enrolledstudentsearch()">
						<img src="imgs/common/searchicon.png" width=22px height=22px
							style="margin-top: 9px;" >
					</div>
					
					<div class="searchbutton" style="width:100px;">
						<div class="table_button_edit_icon"></div>
						<div class="table_button_text" style="font-size: 12px;line-height: 38px;" onclick="getdeleteStudent();">已删除学员</div>
					</div>
					<div class="searchbutton" style="width:80px;">
						<div class="table_button_edit_icon"></div>
						<div class="table_button_text" style="font-size: 12px;line-height: 38px;" onclick="returnEnrollStudent();">报名学员</div>
					</div>
					
					<div class="serchcontentdiv" style="float: left; margin-left: 50px; width: 156px" >
						<input  type="text" class="searchdiv" style="width: 50px;text-align: center;font-family: 微软雅黑;" value="姓名" readonly="readonly">
						<input id="realname" type="text" class="searchdiv" style="width:100px;font-family: 微软雅黑;" value="${searchname}"/>
					</div>
					<div class="serchcontentdiv" style="float: left; margin-left: 50px; width: 156px" >
						<input type="text" class="searchdiv" style="width: 50px;text-align: center;font-family: 微软雅黑;" value="电话" readonly="readonly">
						<input id="phone" type="text" class="searchdiv" style="width:100px;font-family: 微软雅黑;" value="${searchphone}" onkeyup="value=value.replace(/[^\d]/g,'')" onchange="phoneisNum()"/>
					</div>
					
				<div class="serchcontentdiv"style="float: left; margin-left: 50px; width: 309px"  >
					<input type="text" class="searchdiv" style="width: 71px;text-align: center;font-family: 微软雅黑;" value="注册时间" readonly="readonly">
					<input id="starttime" type="text" class="searchdiv" style="width: 99px;text-align: center;font-family: 微软雅黑;border-right: #fff;"  value="${minsdate}" onclick="WdatePicker({startDate:'',dateFmt:'yyyy-MM-dd'})" placeholder="&nbsp;请选择开始日期">
					<input type="text" class="searchdiv" style="width: 20px;text-align: center;font-family: 微软雅黑; border-right: #fff;" value="到" readonly="readonly">
					<input id="endtime" type="text" class="searchdiv" style="width: 105px;text-align: center;font-family: 微软雅黑;"value="${maxsdate}" onclick="WdatePicker({startDate:'',dateFmt:'yyyy-MM-dd'})" placeholder="&nbsp;请选择结束日期">
				</div>


				
			</div>
			
			<div id="content_form_table">
				<table border="0" cellspacing="0" cellpadding="0"
					style="width: 98%;">
					<tr class="tr_th">
						<th>学员ID</th>
						<th>真实姓名</th>
						<th>学员手机号码</th>
						<th>性别</th>
						<th>年龄</th>
						<th>城市</th>
						<th>报名时间</th>
						<th>注册时间</th>
						<th>操作</th>

					</tr>
					<s:iterator value="suserlist" var="suser">
						<tr class="tr_td">
							<td style="width: 80px;" class="border_right_bottom">${studentid}</td>
							<td style="width: 150px;" class="border_right_bottom"><a href="getStudentDetail.do?studentid=${studentid}&index=1&change_id=3"
										style="text-decoration: none; cursor: pointer;">${realname}</a></td>
							<td style="width: 150px;" class="border_right_bottom">${phone}</td>
							<s:if test="gender==1">
							<td style="width: 80px;" class="border_right_bottom">男</td>
							</s:if>
							<s:else>
							<td style="width: 80px;" class="border_right_bottom">女</td>
							</s:else>
							<td style="width: 80px;" class="border_right_bottom">${age}</td>
							<td style="width: 100px;" class="border_right_bottom">${city}</td>
							
							<td style="width: 100px;" class="border_right_bottom">
							<s:date name="enrolltime" format="yyyy-MM-dd HH:mm:ss" />
							</td>
							<td style="width: 100px;" class="border_right_bottom">
							<s:date name="addtime" format="yyyy-MM-dd HH:mm:ss" />
							</td>
							<td style="width: 100px;" class="border_noright_bottom">
<!-- 								<div class="table_edit_button" style="width: 85px; margin-left: 100px"> -->
<!-- 									<div class="table_button_edit_icon"></div> -->
<%-- 									<div class="table_button_text" onclick="showchangemoney(${studentid})">修改金额</div> --%>
<!-- 								</div> -->
								
								<div class="table_edit">
									<a href="getUserState.do?studentid=${studentid}&index=1&change_id=3&state=2"
										style="text-decoration: none; cursor: pointer;">跟进</a>
								</div>
							</td>

						</tr>
					</s:iterator>
					<tr>
						<td colspan="7" style="heighta: 86px;">
							<div style="float: left; margin-top: 34px; margin-left: 20px;">
								总计：${total}条</div> 
							<!-- 下部翻页 -->
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
					    a[a.length] = "<a onclick=\"previousPage("+pageindex+",'getEnrolledStudentByKeyword.do?searchname="+realname+"&searchphone="+phone+"&minsdate="+starttime+"&maxsdate="+endtime +"&index="+index+"&change_id"+j+"&')\" class=\"page_prev\"></a>";
					  }
					  function setPageList() {
					    if (pageindex == i) {
					      a[a.length] = "<a onclick=\"goPage('getEnrolledStudentByKeyword.do?searchname="+realname+"&searchphone="+phone+"&minsdate="+starttime+"&maxsdate="+endtime +"&index="+index+"&change_id"+j+"&',"+i+")\" class=\"on\">" + i + "</a>";
					    } else {
					      a[a.length] = "<a onclick=\"goPage('getEnrolledStudentByKeyword.do?searchname="+realname+"&searchphone="+phone+"&minsdate="+starttime+"&maxsdate="+endtime +"&index="+index+"&change_id"+j+"&',"+i+")\">" + i + "</a>";
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
					      a[a.length] = "...<a onclick=\"goPage('getEnrolledStudentByKeyword.do?searchname="+realname+"&searchphone="+phone+"&minsdate="+starttime+"&maxsdate="+endtime+"&index="+index+"&change_id"+j+"&',"+count+")\">" + count + "</a>";
					    } else if (pageindex >= count - 3) {
					      a[a.length] = "<a onclick=\"goPage('getEnrolledStudentByKeyword.do?searchname="+realname+"&searchphone="+phone+"&minsdate="+starttime+"&maxsdate="+endtime+"&index="+index+"&change_id"+j+"&',1)\">1</a>...";
					      for (var i = count - 4; i <= count; i++) {
					        setPageList();
					      };
					    } else { //当前页在中间部分
					      a[a.length] = "<a onclick=\"goPage('getEnrolledStudentByKeyword.do?searchname="+realname+"&searchphone="+phone+"&minsdate="+starttime+"&maxsdate="+endtime +"&index="+index+"&change_id"+j+"&',1)\">1</a>...";
					      for (var i = pageindex - 2; i <= pageindex+2; i++) {
					        setPageList();
					      }
					      a[a.length] = "...<a onclick=\"goPage('getEnrolledStudentByKeyword.do?searchname="+realname+"&searchphone="+phone+"&minsdate="+starttime+"&maxsdate="+endtime+"&index="+index+"&change_id"+j+"&',"+count+")\">" + count + "</a>";
					    }
					  }
					  if (pageindex == count) {
						    a[a.length] = "<a onclick=\"\" class=\"hide_page_next unclicknext\"></a> 共"+count+"页  到第  "+
						    "<input type=\"text\" class=\"jump_num\" id=\"topage\"/> 页"+
						    "<a class=\"jump_btn\" onclick=\"gotoPage('getEnrolledStudentByKeyword.do?searchname="+realname+"&searchphone="+phone+"&minsdate="+starttime+"&maxsdate="+endtime +"&index="+index+"&change_id"+j+"&',"+$("#pageSize").val()+")\")\">"+
						    "<a id='page_msg'></a>";
						  } else {
						    a[a.length] = 
						    	"<a onclick=\"nextPage("+$("#pageIndex").val()+",'getEnrolledStudentByKeyword.do?searchname="+realname+"&searchphone="+phone+"&minsdate="+starttime+"&maxsdate="+endtime +"&index="+index+"&change_id"+j+"&')\" "+
						    	"class=\"page_next\"></a> 共"+count+"页 到第 "+
						    "<input type=\"text\" class=\"jump_num\" id=\"topage\"/> 页"+
						    "<a class=\"jump_btn\" onclick=\"gotoPage('getEnrolledStudentByKeyword.do?searchname="+realname+"&searchphone="+phone+"&minsdate="+starttime+"&maxsdate="+endtime +"&',"+$("#pageSize").val()+")\">"+
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
		
		<!-- 添加学员弹框 -->
	<div id="add" class="add"></div>
	<div id="add_sec" style="position: fixed; width: 100%; height: 300px;z-index: 300; top:20%;left:25%;display:none">
		<div id="add_last" class="add_last">
		
		<div style="position: fixed; width: 600px; height: 300px;background: #4cc2ff;margin-left: 50px;margin-top: 50px;">
		<form action="addStudentByPhone.do" method="post" id="form_submit">
			<input id="newstudentphone" name="newstudentphone" type="text" style="width: 250px;height: 40px;margin: auto;margin-left: 40px;margin-top: 30px;font-size: 18px;text-align: center;" onblur="checkstudentExistance()"     placeholder="请输入手机号码"/>
			<input name="newstudentrealname" id="newstudentrealname" type="text" style="width: 250px;height: 40px;margin: auto;margin-left: 20px;margin-top: 15px;font-size: 18px;text-align: center;"     placeholder="请输入学员姓名"/>

			<input type="button" style="width: 100px;height: 40px;margin: auto;margin-left: 200px;margin-top: 50px;font-size: 18px" value="确定" onclick="addstudent()">
			<input type="button" style="width: 100px;height: 40px;margin: auto;margin-left: 180x;margin-top: -30px;font-size: 18px" value="取消" onclick="unshowadduser()">
		</form>
		</div>
		</div>
	</div>
	
	<!-- 数据导出弹框 -->
	<div id="alertbox" class="alertbox"></div>
	<div id="alertbox_sec" style="position: fixed; width: 100%; height: 500px;z-index: 300;">
		<div id="alertbox_last" class="alertbox_last">
		<div style="position: fixed; width: 500px; height: 250px;background: #4cc2ff;margin-left: 50px;margin-top: 50px;">
		<div style="font-size: 15px;width: 110px;margin: auto;">请选择导出信息</div>
		<div>全选<input type="checkbox" id="allstudetailcheck" value="0" onclick="selectallstudetail()"></div>
		<input type="checkbox" style="margin-top: 5px;" name="C1"  id="a1" value="0"/> 学生证或驾驶证号
		<input type="checkbox" style="margin-left: 5px;margin-top: 5px;" name="C1"  id="a1" value="1"> 学员证制证时间
		<input type="checkbox" style="margin-left: 5px;margin-top: 5px;" name="C1"  id="a1" value="2"> 城市
		<input type="checkbox" style="margin-left: 5px;margin-top: 5px;" name="C1"  id="a1" value="3"> 地址
		<input type="checkbox" style="margin-left: 5px;margin-top: 5px;" name="C1"  id="a1" value="4"> 性别
		<input type="checkbox" style="margin-left: 5px;margin-top: 5px;" name="C1"  id="a1" value="5"> 出生日期
		<input type="checkbox" style="margin-top: 5px;" name="C1"  id="a1" value="6"> 紧急联系人姓名
		<input type="checkbox" style="margin-left: 5px;margin-top: 5px;" name="C1"  id="a1" value="7"> 紧急联系人电话
		<input type="checkbox" style="margin-left: 5px;margin-top: 5px;" name="C1"  id="a1" value="8"> 冻结金额
		<input type="checkbox" style="margin-left: 5px;margin-top: 5px;" name="C1"  id="a1" value="9"> 账户余额
		<input type="checkbox" style="margin-left: 5px;margin-top: 5px;" name="C1"  id="a1" value="10"> 第三方QQ登录ID
	
		<input type="checkbox" style="margin-top: 5px;" name="C1"  id="a1" value="11"> 第三方微信登录ID
		<input type="checkbox" style="margin-left: 5px;margin-top: 5px;" name="C1"  id="a1" value="12"> 第三方微博登录ID
		<input type="checkbox" style="margin-left: 5px;margin-top: 5px;" name="C1"  id="a1" value="13"> 审核状态
		<input type="checkbox" style="margin-left: 5px;margin-top: 5px;" name="C1"  id="a1" value="14"> 教练确认状态
		<input type="checkbox" style="margin-top: 5px;" name="C1"  id="a1" value="15"> 综合评分
	
		</div>
		</div>
	</div>
	

</div>



<!-- 设置金额弹框 -->
	<div id="mask" class="mask"></div>
	<div id="mask_sec" style="position: fixed; width: 100%; height: 300px;z-index: 300">
		<div id="mask_last" class="mask_last">
		<div style="position: fixed; width: 300px; height: 200px;background: #4cc2ff;margin-left: 100px;margin-top: 50px;">
		<input type="hidden" id="money_studentid" value=""/>
		<input id="studentmoney" type="text" style="width: 170px;height: 40px;margin: auto;margin-left: 70px;margin-top: 45px;font-size: 18px;text-align: center;"  placeholder="请输入修改金额" onchange="isNum()"/>
		<input type="button" style="width: 100px;height: 40px;margin: auto;margin-left: 50px;margin-top: 40px;font-size: 18px" value="确定" onclick="changemoney()">
		<input type="button" style="width: 100px;height: 40px;margin: auto;margin-left: 180x;margin-top: -40px;font-size: 18px" value="取消" onclick="unshowchangemoney()">
		</div>
		</div>
	</div>
	
	
	
</body>
</html>