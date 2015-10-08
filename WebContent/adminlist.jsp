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
<link href="admin/css/admin.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="css/page.css" type="text/css" media="screen" />
<script src="js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/page.js"></script>
<script type="text/javascript" src="coach/js/coachdetail.js"></script>
<script type="text/javascript" src="admin/js/admin.js"></script>
<script type="text/javascript">
$(function(){
	var index = $("#index").val();
	$("#left_list_"+index).show();
	var j = $("#change_id").val();
	$("#change_"+j+index).addClass('left_list_mask');
	$("#nav").css("min-height","908px");
	$("#admin_pageIndex").val($("#pageIndex").val());
})
</script>
<title>管理员设置</title>
</head>
<input type="hidden" id="index" value="${index}" />
<input type="hidden" id="change_id" value="${change_id}"/>
<body onload="getTopself()" style="min-width: 1650px;">
	<div id="content">
		<jsp:include page="left.jsp" />
		<div id="content_form">
			<div id="content_form_top">
			<div class="addbutton" onclick="showaddadmin()">+&nbsp;添加</div>
			<div class="addbutton" onclick="(newPermission('display'))()" style="background: #4cc2ff;">新建权限</div>
					<div class="searchbutton" onclick="searchadmins()">
						<img src="imgs/common/searchicon.png" width=22px height=22px
							style="margin-top: 9px;" >
					</div>
					<div class="serchcontentdiv" style="float: right; margin-right: 50px; width: 156px" >
						<input  type="text" class="searchdiv" style="width: 50px;text-align: center;font-family: 微软雅黑;" value="账号" readonly="readonly">
						<input id="login_account" type="text" class="searchdiv" style="width:100px;font-family: 微软雅黑;" value="${searchlogin}" />
					</div>

					<div class="serchcontentdiv" style="float: right; margin-right: 50px; width: 211px" >
						<input type="text" class="searchdiv" style="width: 85px;text-align: center;font-family: 微软雅黑;" value="联系电话" readonly="readonly">
						<input id="telphone" type="text" class="searchdiv" style="width:120px;font-family: 微软雅黑;" value="${searchtelphone}" onkeyup="value=value.replace(/[^\d]/g,'')"/>
					</div>
					
					

					


			</div>
			<div id="content_form_table">
				<table border="0" cellspacing="0" cellpadding="0"style="width: 98%;">
					<tr class="tr_th">
						
						<th>ID</th>
						<th>管理员账号</th>
						<th>管理员类型</th>
						<th>真实姓名</th>
						<th>联系电话</th>
						<th>所属驾校</th>
						<th>管理员权限</th>
						<th>添加时间</th>
						<th>操作</th>

					</tr>
					<s:iterator value="adminlist" var="admin">
						<tr class="tr_td">
							<td style="width: 52px;" class="border_right_bottom">${adminid}</td>
							<td style="width: 100px;" class="border_right_bottom">${login_account}</td>
							<s:if test="type==0">
							<td style="width: 100px;" class="border_right_bottom">超级管理员</td>
							</s:if>
							<s:elseif test="type==1">
							<td style="width: 100px;" class="border_right_bottom">平台管理员</td>
							</s:elseif>
							<s:else>
							<td style="width: 100px;" class="border_right_bottom">驾校管理员</td>
							</s:else>
							<td style="width: 100px;" class="border_right_bottom">${realname}</td>
							
							<td style="width: 100px;" class="border_right_bottom">${telphone}</td>
							
							<s:if test="schoolid==0">
							<td style="width: 200px;" class="border_right_bottom">无</td>
							</s:if>
							<s:else>
								<td style="width: 200px;" class="border_right_bottom">${schoolname}</td>
							</s:else>
							
							<s:if test="permission==null">
							<td style="width: 100px;" class="border_right_bottom"></td>
							</s:if>
							<s:else>
							<td style="width: 100px;" class="border_right_bottom">${permissions}</td>
							</s:else>
							
							
							<td style="width: 100px;" class="border_right_bottom">
							<s:date name="addtime" format="yyyy-MM-dd HH:mm:ss" />
							</td>

							<td style="width: 350px;" class="border_noright_bottom">
								<div class="table_edit_button" style="width: 60px;">
									<div class="table_button_edit_icon"></div>
									<div class="table_button_text" onclick="showeditadmin('${adminid}','${login_account}','${realname}','${telphone}')">编辑</div>
								</div>
								
								<div class="table_edit_button" style="width: 90px;">
									<div class="table_button_edit_icon"></div>
									<div class="table_button_text" onclick="showchangepsw('${adminid}','${password}')">修改密码</div>
								</div>
								
								
								<s:if test="type==1">
								<div class="table_edit_button" style="width: 90px;">
									<div class="table_button_edit_icon"></div>
									<div class="table_button_text" onclick="showchangepermession('${adminid}')">编辑权限</div>
								</div>
								</s:if>
								<s:else>
								<div class="table_edit_button" style="width: 90px;background: #e5e5e5;cursor: default;">
									<div class="table_button_edit_icon"></div>
									<div class="table_button_text" >编辑权限</div>
								</div>
								</s:else>
								
							
								<s:if test="type==2">
								<div class="table_edit_button" style="width: 90px;">
									<div class="table_button_edit_icon"></div>
									<div class="table_button_text" onclick="showchangeschool('${adminid}','${schoolname}')">调整驾校</div>
								</div>
								</s:if>
								<s:else>
								<div class="table_edit_button" style="width: 90px;background: #e5e5e5;cursor: default;">
									<div class="table_button_edit_icon"></div>
									<div class="table_button_text" >调整驾校</div>
								</div>
								</s:else>
							
							
							
								
								<div class="table_del_button" style="width: 60px;">
									<div class="table_button_del_icon"></div>
									<div class="table_button_text"
										onclick="deladmin('${adminid}')">删除</div>
								</div>
							</td>
						</tr>
					</s:iterator>
					<tr>
						<td colspan="9" style="height: 86px;">
							<div style="float: left; margin-top: 34px; margin-left: 20px;">
								总计：${total}条</div> <!-- 下部翻页 -->
							<div style="margin-top: 34px; margin-right: 20px;">
								<s:if test="%{pageCount>1}">

									<input type="hidden" value="${pageCount }" id="pageSize" />
									<input type="hidden" value="${pageIndex }" id="pageIndex" />
									<div id="untreatedpage"></div>
									<script type="text/javascript">
					//container 容器，count 总页数 pageindex 当前页数
					//这里要改 改改改改！！！！！！！！！！！！！！！！！！！！！！！
					function setPage(container, count, pageindex) {
					var container = container;
					var count = parseInt(count);
					var pageindex = parseInt(pageindex);
					var searchlogin=$("#login_account").val();
					var searchtelphone =$("#telphone").val();
					var index=$("#index").val();
					var j = $("#change_id").val();
					var a = [];
					  //总页数少于10 全部显示,大于10 显示前3 后3 中间3 其余....
					  if (pageindex == 1) {
						  //alert(pageindex);
					    a[a.length] = "<a onclick=\"\" class=\"hide_page_prev unclickprev on\"></a>";
					  } else {
					    a[a.length] = "<a onclick=\"previousPage("+pageindex+",'getAdminInfolistByKeyword.do?searchlogin="+searchlogin+"&searchtelphone="+searchtelphone+"&index="+index+"&change_id="+j +"&')\" class=\"page_prev\"></a>";
					  }
					  function setPageList() {
					    if (pageindex == i) {
					      a[a.length] = "<a onclick=\"goPage('getAdminInfolistByKeyword.do?searchlogin="+searchlogin+"&searchtelphone="+searchtelphone+"&index="+index+"&change_id="+j +"&',"+i+")\" class=\"on\">" + i + "</a>";
					    } else {
					      a[a.length] = "<a onclick=\"goPage('getAdminInfolistByKeyword.do?searchlogin="+searchlogin+"&searchtelphone="+searchtelphone+"&index="+index +"&change_id="+j +"&',"+i+")\">" + i + "</a>";
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
					      a[a.length] = "...<a onclick=\"goPage('getAdminInfolistByKeyword.do?searchlogin="+searchlogin+"&searchtelphone="+searchtelphone+"&index="+index +"&change_id="+j +"&',"+count+")\">" + count + "</a>";
					    } else if (pageindex >= count - 3) {
					      a[a.length] = "<a onclick=\"goPage('getAdminInfolistByKeyword.do?searchlogin="+searchlogin+"&searchtelphone="+searchtelphone+"&index="+index +"&change_id="+j +"&',1)\">1</a>...";
					      for (var i = count - 4; i <= count; i++) {
					        setPageList();
					      };
					    } else { //当前页在中间部分
					      a[a.length] = "<a onclick=\"goPage('getAdminInfolistByKeyword.do?searchlogin="+searchlogin+"&searchtelphone="+searchtelphone+"&index="+index +"&change_id="+j +"&',1)\">1</a>...";
					      for (var i = pageindex - 2; i <= pageindex+2; i++) {
					        setPageList();
					      }
					      a[a.length] = "...<a onclick=\"goPage('getAdminInfolistByKeyword.do?searchlogin="+searchlogin+"&searchtelphone="+searchtelphone+"&index="+index +"&change_id="+j +"&',"+count+")\">" + count + "</a>";
					    }
					  }
					  if (pageindex == count) {
						    a[a.length] = "<a onclick=\"\" class=\"hide_page_next unclicknext\"></a> 共"+count+"页  到第  "+
						    "<input type=\"text\" class=\"jump_num\" id=\"topage\"/> 页"+
						    "<a class=\"jump_btn\" onclick=\"gotoPage('getAdminInfolistByKeyword.do?searchlogin="+searchlogin+"&searchtelphone="+searchtelphone+"&index="+index +"&change_id="+j +"&',"+$("#pageSize").val()+")\")\">"+
						    "<a id='page_msg'></a>";
						  } else {
						    a[a.length] = 
						    	"<a onclick=\"nextPage("+$("#pageIndex").val()+",'getAdminInfolistByKeyword.do?searchlogin="+searchlogin+"&searchtelphone="+searchtelphone+"&index="+index +"&change_id="+j +"&')\" "+
						    	"class=\"page_next\"></a> 共"+count+"页 到第 "+
						    "<input type=\"text\" class=\"jump_num\" id=\"topage\"/> 页"+
						    "<a class=\"jump_btn\" onclick=\"gotoPage('getAdminInfolistByKeyword.do?searchlogin="+searchlogin+"&searchtelphone="+searchtelphone+"&index="+index +"&change_id="+j +"&',"+$("#pageSize").val()+")\">"+
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



<!-- 编辑弹框 -->
	<div id="edit" class="edit"></div>
	<div id="edit_sec" style="position: fixed; width: 100%; height: 300px;z-index: 300">
		<div id="edit_last" class="edit_last">
		<div style="position: fixed; width: 400px; height: 400px;background: #4cc2ff;margin-left: 100px;margin-top: 50px;">
		<input type="hidden" id="admin_idedit" value=""/>
		<input id="oldlogin" type="text" style="width: 250px;height: 40px;margin: auto;margin-left: 70px;margin-top: 15px;font-size: 18px;text-align: center;background: #e5e5e5" readonly="readonly" />
		<input id="newlogin" type="text" style="width: 250px;height: 40px;margin: auto;margin-left: 70px;margin-top: 15px;font-size: 18px;text-align: center;"  placeholder="请输入账号"/>
		<input id="oldrealname" type="text" style="width: 250px;height: 40px;margin: auto;margin-left: 70px;margin-top: 15px;font-size: 18px;text-align: center;background: #e5e5e5"readonly="readonly"  />
		<input id="newrealname" type="text" style="width: 250px;height: 40px;margin: auto;margin-left: 70px;margin-top: 15px;font-size: 18px;text-align: center;"  placeholder="请输入姓名"/>
		<input id="oldphone" type="text" style="width: 250px;height: 40px;margin: auto;margin-left: 70px;margin-top: 15px;font-size: 18px;text-align: center;background: #e5e5e5"readonly="readonly"  />
		<input id="newphone" type="text" style="width: 250px;height: 40px;margin: auto;margin-left: 70px;margin-top: 15px;font-size: 18px;text-align: center;" onkeyup="value=value.replace(/[^\d]/g,'')" maxlength="11"  placeholder="请输入联系电话"/>
		
		<input type="button" style="width: 100px;height: 40px;margin: auto;margin-left: 100px;margin-top: 18px;font-size: 18px" value="确定" onclick="editadmin(1)">
		<input type="button" style="width: 100px;height: 40px;margin: auto;margin-left: 180x;margin-top: -30px;font-size: 18px" value="取消" onclick="unshoweditadmin()">
		</div>
		</div>
	</div>
	
<!-- 修改密码弹框 -->
	<div id="psw" class="psw"></div>
	<div id="psw_sec" style="position: fixed; width: 100%; height: 300px;z-index: 400;">
		<div id="psw_last" class="psw_last">
		<div style="position: fixed; width: 400px; height: 300px;background: #4cc2ff;margin-left: 50px;margin-top: 50px;">
		<input type="hidden" id="admin_passwword" value=""/>
		<input type="hidden" id="admin_idpsw" value=""/>
		<input id="secpassword" type="password" style="width: 250px;height: 40px;margin: auto;margin-left: 75px;margin-top: 20px;font-size: 18px;text-align: center;"  placeholder="请输入新密码"/>
		<input id="tirpassword" type="password" style="width: 250px;height: 40px;margin: auto;margin-left: 75px;margin-top: 20px;font-size: 18px;text-align: center;"  placeholder="请再次输入"/>
		<input type="button" style="width: 100px;height: 40px;margin: auto;margin-left: 100px;margin-top: 40px;font-size: 18px" value="确定" onclick="editadmin(2)">
		<input type="button" style="width: 100px;height: 40px;margin: auto;margin-left: 180x;margin-top: -40px;font-size: 18px" value="取消" onclick="unshowchangepsw()">
		</div>
		</div>
	</div>
	
	
	
	<!-- 修改驾校弹框-->
	<div id="school" class="school"></div>
	<div id="school_sec" style="position: fixed; width: 100%; height: 300px;z-index: 300">
		<div id="school_last" class="school_last">
		<div style="position: fixed; width: 400px; height: 200px;background: #4cc2ff;margin-left: 50px;margin-top: 50px;">
		<input type="hidden" id="admin_idsch" value=""/>
		<input id="oldschoolname" type="text" style="width: 300px;height: 40px;margin: auto;margin-left: 50px;margin-top: 25px;font-size: 18px;f;background: #e5e5e5"  readonly="readonly"/>
		<select id="schoollist" style="width: 300px;height: 40px;margin: auto;margin-left: 50px;margin-top: 20px;font-size: 18px;">
		</select>
		<input type="button" style="width: 100px;height: 40px;margin: auto;margin-left: 90px;margin-top: 20px;font-size: 18px" value="确定" onclick="changeschool()">
		<input type="button" style="width: 100px;height: 40px;margin: auto;margin-left: 180x;margin-top: -40px;font-size: 18px" value="取消" onclick="unshowchangeschool()">
		</div>
		</div>
	</div>
	
	
	<!-- 添加管理员弹框 -->
	<div id="add" class="add"></div>
	<div id="add_sec" style="position: fixed; width: 100%; height: 300px;z-index: 300">
		<div id="add_last" class="add_last">
		
		<div style="position: fixed; width: 600px; height: 500px;background: #4cc2ff;margin-left: 50px;margin-top: 50px;">
		<form action="addAdmin.do" method="post" id="form_submit">
		<input type="hidden" id="schoolid" name="schoolid" value=""/>
		<input id="admin_login" name="admin_login" type="text" style="width: 250px;height: 40px;margin: auto;margin-left: 40px;margin-top: 30px;font-size: 18px;text-align: center;" onblur="checklogin()"     placeholder="请输入管理员账号"/>
		<input name="admin_name" id="admin_name" type="text" style="width: 250px;height: 40px;margin: auto;margin-left: 20px;margin-top: 15px;font-size: 18px;text-align: center;"     placeholder="请输入管理员姓名"/>
		<input name="admin_password" id="admin_password" type="text" style="width: 250px;height: 40px;margin: auto;margin-left: 40px;margin-top: 15px;font-size: 18px;text-align: center;"  placeholder="请输入密码"/>
		<input name="admin_phone" id="admin_phone" type="text" style="width: 250px;height: 40px;margin: auto;margin-left: 20px;margin-top: 15px;font-size: 18px;text-align: center;" onkeyup="value=value.replace(/[^\d]/g,'')" placeholder="请输入联系电话" maxlength="11"/>
		
		<div style="width: 524px;height: 40px;margin: auto;margin-left: 40px;margin-top: 30px;font-size: 18px;text-align: center;">
		<span style="width: 100px; position: relative; top: -7px;">管理员类型：</span>
		
		</div>
		<div style="width: 550px;height: 40px;margin: auto;margin-left: 10px;font-size: 18px;text-align: center;">
		<span style="width: 100px; position: relative; top: -7px;margin-left: 74px;">超级管理员：</span>
		<input id="noset" type="radio" style="width: 50px; height: 25px; margin-left: -15px; margin-top: 5px; font-size: 18px; text-align: center;cursor: pointer;" onclick="fun1()" value="0" name="admin_type"  checked="checked"/>
		<span style="width: 100px; position: relative; top: -7px; margin-left: 10px;">平台管理员：</span>
		<input id="hadset" type="radio" style="width: 50px; height: 25px; margin-left: -15px; margin-top: 5px; font-size: 18px; text-align: center;cursor: pointer;"onclick="fun2()" value="1" name="admin_type" />
		<span style="width: 100px; position: relative; top: -7px; margin-left: 10px;">驾校管理员：</span>
		<input id="hadset" type="radio" style="width: 50px; height: 25px; margin-left: -15px; margin-top: 5px; font-size: 18px; text-align: center;cursor: pointer;"onclick="fun3()" value="2" name="admin_type" />
		</div>
		
		<div id="normaladmin" style="width: 550px;height: 140px;margin: auto;margin-left: 25px;font-size: 18px;background: #fff;display: none;">
		
		<span style="width: 100px; position: relative; top: -7px;margin-left: 60px;">管理员设置</span>
		<input value="1" type="checkbox" style="width: 50px; height: 25px; margin-left: -15px; margin-top: 5px; font-size: 18px; text-align: center;cursor: pointer;" name="permession" />
		
		<span style="width: 100px; position: relative; top: -7px;margin-left: 30px;">用户管理</span>
		<input value="2" type="checkbox" style="width: 50px; height: 25px; margin-left: -15px; margin-top: 5px; font-size: 18px; text-align: center;cursor: pointer;" name="permession" />
		
		<span style="width: 100px; position: relative; top: -7px;margin-left: 30px;">充值提现管理</span>
		<input value="3" type="checkbox" style="width: 50px; height: 25px; margin-left: -15px; margin-top: 5px; font-size: 18px; text-align: center;cursor: pointer;" name="permession" />
		
		<span style="width: 100px; position: relative; top: -7px;margin-left: 78px;">系统配置</span>
		<input value="4" type="checkbox" style="width: 50px; height: 25px; margin-left: -15px; margin-top: 5px; font-size: 18px; text-align: center;cursor: pointer;" name="permession" />
		
		<span style="width: 100px; position: relative; top: -7px;margin-left: 30px;">系统通知</span>
		<input value="5" type="checkbox" style="width: 50px; height: 25px; margin-left: -15px; margin-top: 5px; font-size: 18px; text-align: center;cursor: pointer;" name="permession" />
		
		<span style="width: 100px; position: relative; top: -7px;margin-left: 66px;">订单管理</span>
		<input value="6" type="checkbox" style="width: 50px; height: 25px; margin-left: -15px; margin-top: 5px; font-size: 18px; text-align: center;cursor: pointer;" name="permession" />
		
		<span style="width: 100px; position: relative; top: -7px;margin-left: 78px;">投诉管理</span>
		<input value="7" type="checkbox" style="width: 50px; height: 25px; margin-left: -15px; margin-top: 5px; font-size: 18px; text-align: center;cursor: pointer;" name="permession" />
		
		<span style="width: 100px; position: relative; top: -7px;margin-left: 30px;">驾校管理</span>
		<input value="8" type="checkbox" style="width: 50px; height: 25px; margin-left: -15px; margin-top: 5px; font-size: 18px; text-align: center;cursor: pointer;" name="permession" />
		
		<span style="width: 100px; position: relative; top: -7px;margin-left: 48px;">小巴券管理</span>
		<input value="9" type="checkbox" style="width: 50px; height: 25px; margin-left: -15px; margin-top: 5px; font-size: 18px; text-align: center;cursor: pointer;" name="permession" />
		
			<span style="width: 100px; position: relative; top: -7px;margin-left: 78px;">日报管理</span>
		<input value="10" type="checkbox" style="width: 50px; height: 25px; margin-left: -15px; margin-top: 5px; font-size: 18px; text-align: center;cursor: pointer;" name="permession" />
		
		<span style="width: 100px; position: relative; top: -7px;margin-left: 66px;">其他</span>
		<input value="11" type="checkbox" style="width: 50px; height: 25px; margin-left: -15px; margin-top: 5px; font-size: 18px; text-align: center;cursor: pointer;" name="permession" />
		</div>
		
		<div id="schooladmin" style="width: 500px;height: 40px;margin-top:10px;margin-left: 50px;font-size: 18px;text-align: center;display: none;">
		<span style="width: 100px; position: relative; top: -7px;margin-left: -20px;">请选择所属驾校：</span>
		<select id="drive" style="font-size: 18px;text-align: center;width: 260px;height: 40px;">
		</select>
		</div>
		<input type="button" style="width: 100px;height: 40px;margin: auto;margin-left: 200px;margin-top: 50px;font-size: 18px" value="确定" onclick="addadmin()">
		<input type="button" style="width: 100px;height: 40px;margin: auto;margin-left: 180x;margin-top: -30px;font-size: 18px" value="取消" onclick="unshowaddadmin()">
		</form>
		</div>
		</div>
	</div>
	
	
	
	<!-- 权限编辑弹框-->
	<div id="power" class="power" style="background-color:rgba(112,112,122,0.5);opacity:1;">
		<div style="width: 600px; height:auto;background: #4cc2ff;margin:auto;margin-top: 50px;border:10px solid white">
		<form action="changePermession.do" id="editpower" method="post">
		<input type="hidden" id="admin_idpower" name="formadminid" value="2"/>
		<input type="hidden" id="admin_pageIndex" value="" name="pageIndex" />
		<input type="hidden" value="${searchlogin}" name="searchlogin"/>
		<input type="hidden" value="${searchtelphone}" name="searchtelphone"/>
		<select id="selectPid"  style="width: 300px;height: 40px;margin-top:20px;margin-left:150px;font-size: 18px;background: #e5e5e5;text-align: center;font-family: 微软雅黑"  readonly="readonly" onchange="selectPermission()"></select>
		
		<div id="changepermession" style="width: 540px;height:auto;margin: auto;margin-left: 25px;margin-top:20px;font-size: 18px;background: #fff;">
		</div>
		<input type="button" style="width: 100px;height: 40px;margin: auto;margin-left: 200px;margin-top: 40px;font-size: 18px" value="确定" onclick="changepermession()">
		<input type="button" style="width: 100px;height: 40px;margin: auto;margin-left: 180x;margin-top: -40px;font-size: 18px" value="取消" onclick="unshowchangepermession()">
		</form>
		</div>
		
	</div>
	
	<!-- 新建权限弹框-->
	<form id="newPermissionForm" action="addPermission.do" style="width: 100%;height: 100%;z-index: 500;position: fixed;top: 0px;left: 0px;background-color: rgba(122, 122, 122, 0.498039);display:none;">
		<div style="width:450px;height:300px;margin:auto;margin-top:30px;background:rgb(76,194,255);opacity:1;border:5px white solid;">
			<span style="width:100%;height:40px;display:block;font-size:20px;font-weight:bolder; line-height:40px; text-align:center;margin-top:20px;">新建权限</span>
			<span style="width:100%;display:block;height:40px;"><label style="width:100px;display:block;float:left;line-height:40px;text-align:right;font-size:16px;">父权限:</label><select id="parentPermission" style="width:180px;height:40px;float:left;" name="parentPermission"></select></span>
			<span style="width:100%;display:block;height:40px;margin-top:10px;"><label style="width:100px;display:block;float:left;line-height:40px;text-align:right;font-size:16px;">权限名称:</label><input type="text" style="width:180px;height:40px;float:left;font-size:16px;" name="permissionName"/><label style="line-height:40px; font-size:16px;color:red;display:none;display:${displayflag}">*失败，action已存在</label></span>
			<span style="width:100%;display:block;height:40px;margin-top:10px;"><label style="width:100px;display:block;float:left;line-height:40px;text-align:right;font-size:16px;">关联Action</label><input type="text" style="width:180px;height:40px;float:left;font-size:16px;text-align:right;" name="mappedAction"/><label style="line-height:40px; font-size:16px;">.do</label></span>
			<span style="width:280px;height:40px;display:block; margin:auto;margin-top:20px;clear:both;">
			<input type="submit" style="width: 100px;height: 40px;font-size: 18px;float:left; margin-right:20px;" value="新建">
			<input type="button" style="width: 100px;height: 40px;font-size: 15px;float:left; margin-left:20px;" value="取消" onclick="(newPermission('hide'))()">
 			</span>
		</div>
	</form>
	<script>
		//检查是否新建权限成功
		code="<%=request.getParameter("error")%>";
		(newPermission("error"))(code);
	</script>
</body>
</html>