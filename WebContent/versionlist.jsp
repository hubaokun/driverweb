<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.daoshun.menu.SideMenu"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/style.css" rel="stylesheet" type="text/css">
<link href="css/ordinary.css" rel="stylesheet" type="text/css">
<script src="js/jquery-1.11.2.min.js"></script>
<script src="other/js/other.js"></script>
<script src="js/common.js"></script>
<script type="text/javascript">
	$(function() {
		var index = $("#index").val();
		$("#left_list_" + index).show();
		var j = $("#change_id").val();
		$("#change_"+j+index).addClass('left_list_mask');
	})
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
	height: 600px;
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
	width: 700px;
	height: 800px;
	margin: 0 auto;
	display: none;
}
</style>

<title>版本列表</title>
</head>
<input type="hidden" id="index" value="${index}" />
<input type="hidden" id="change_id" value="${change_id}"/>
<body onload="getNewTop()" style="min-width: 1600px">
	<div id="content">
		<jsp:include page="left.jsp" />
		<div id="content_form">
			<div id="content_form_top">
				<div class="addbutton" onclick="showaddversion()">+&nbsp;添加</div>
			</div>
			<div id="content_form_table">
				<table border="0" cellspacing="0" cellpadding="0"
					style="width: 98%;">
					<tr class="tr_th">
						<th>数据ID</th>
						<th>版本号</th>
						<th>类别区分</th>
						<th>状态</th>
						<th>类型</th>
						<th>客户端</th>
						<th>添加时间</th>
						<th>操作</th>
					</tr>
					<s:iterator value="versionlist" var="version">
						<tr class="tr_td">
							<td style="width: 10%;" class="border_right_bottom">${versionid}</td>
							<td style="width: 15%;" class="border_right_bottom">${versioncode}</td>
							<s:if test="type==1">
								<td style="width: 10%;" class="border_right_bottom">大版本</td>
							</s:if>
							<s:else>
								<td style="width: 10%;" class="border_right_bottom">小版本</td>
							</s:else>
							<s:if test="state==0">
								<td style="width: 10%;" class="border_right_bottom">未发布</td>
							</s:if>
							<s:else>
								<td style="width: 10%;" class="border_right_bottom">已发布</td>
							</s:else>
							<s:if test="category==0">
								<td style="width: 10%;" class="border_right_bottom">安卓</td>
							</s:if>
							<s:else>
								<td style="width: 10%;" class="border_right_bottom">IOS</td>
							</s:else>

							<s:if test="apptype==1">
								<td style="width: 5%;" class="border_right_bottom">教练端</td>
							</s:if>
							<s:else>
								<td style="width: 5%;" class="border_right_bottom">学员端</td>
							</s:else>

							<td style="width: 20%;" class="border_right_bottom">
							<s:date name="addtime"  format="yyyy-MM-dd HH:mm:ss" /></td>

							<td style="width: 20%;" class="border_noright_bottom">

								<div class="table_edit_button"
									style="cursor: auto; background: #fff;">
									<div class="table_button_edit_icon"></div>
									<div class="table_button_text"></div>
								</div>
<s:if test="state==0">
<div class="table_del_button">
									<div class="table_button_del_icon"></div>
									<div class="table_button_text"
										onclick="delversion('${versionid}')">删除</div>
								</div>
								<div class="table_edit_button">
									<div class="table_button_edit_icon"></div>
									<div class="table_button_text"
										onclick="showeditversion('${versioncode}','${versionname}','${versionid}','${type}','${category}','${state}','${apptype}','${download}')">修改</div>
								</div>
</s:if>
<s:else>
								<div class="table_del_button" style="background:#e5e5e5;cursor: auto;">
									<div class="table_button_del_icon"></div>
									<div class="table_button_text" >删除</div>
								</div>
								<div class="table_edit_button" style="background:#e5e5e5;cursor: auto;">
									<div class="table_button_edit_icon"></div>
									<div class="table_button_text">修改</div>
								</div>


</s:else>
								<div class="table_edit_button"
									style="cursor: auto; background: #fff;">
									<div class="table_button_edit_icon"></div>
									<div class="table_button_text"></div>
								</div>

							</td>
						</tr>
					</s:iterator>
				</table>

			</div>
		</div>
	</div>
	<!-- 添加新版本弹框 -->
	<div id="mask" class="mask"></div>
	<div id="mask_sec"
		style="position: fixed; width: 100%; height: 300px; z-index: 300;">
		<div id="mask_last" class="mask_last">
			<div
				style="position: fixed; width: 500px; height: 500px; background: #4cc2ff; margin-left: 50px; margin-top: 50px;">
				<input id="versioncode" type="text"
					style="width: 280px; height: 40px; margin: auto; margin-left: 120px; margin-top: 20px; font-size: 18px; text-align: center;"
					placeholder="请输入添加的版本号" onblur="validate()" /> 
				<input id="versionname" type="text" style="width: 280px; height: 40px; margin: auto; margin-left: 120px; margin-top: 20px; font-size: 18px; text-align: center;"
					placeholder="请输入客户端显示版本" />
				<input id="versiondownload" type="text" style="width: 280px; height: 40px; margin: auto; margin-left: 120px; margin-top: 20px; font-size: 18px; text-align: center;"
					placeholder="请输入客户端下载地址"/>
					
				<div
					style="width: 300px; height: 40px; margin: auto; margin-left: 120px; margin-top: 20px; font-size: 18px; text-align: center; line-height: 40px;">
						 <span style="width: 100px; position: relative; top: -7px;">小版本：</span>
					<input  type="radio" style="width: 50px; height: 25px; margin-left: -15px; margin-top: 5px; font-size: 18px; text-align: center;" value="0" name="type" checked="checked" />
						 <span style="width: 100px; position: relative; top: -7px; margin-left: 20px;">大版本：</span>
					<input  type="radio" style="width: 50px; height: 25px; margin-left: -15px; margin-top: 5px; font-size: 18px; text-align: center;" value="1" name="type" />
				</div>
				<div
					style="width: 300px; height: 40px; margin: auto; margin-left: 123px; margin-top: 20px; font-size: 18px; text-align: center; line-height: 40px;">
					<span style="width: 100px; position: relative; top: -7px;">安&nbsp;&nbsp;卓：</span>
					<input  type="radio"
						style="width: 50px; height: 25px; margin-left: -13px; margin-top: 5px; font-size: 18px; text-align: center;"
						value="0" name="versioncategory" checked="checked" /> <span
						style="width: 100px; position: relative; top: -7px; margin-left: 42px;">IOS：</span>
					<input  type="radio"
						style="width: 50px; height: 25px; margin-left: -13px; margin-top: 5px; font-size: 18px; text-align: center;"
						value="1" name="versioncategory" />
				</div>
				
				<div
					style="width: 300px; height: 40px; margin: auto; margin-left: 120px; margin-top: 20px; font-size: 18px; text-align: center; line-height: 40px;">
						 <span style="width: 100px; position: relative; top: -7px;">教练端：</span>
					<input  type="radio" style="width: 50px; height: 25px; margin-left: -15px; margin-top: 5px; font-size: 18px; text-align: center;" value="1" name="apptype" checked="checked" />
						 <span style="width: 100px; position: relative; top: -7px; margin-left: 20px;">学员端：</span>
					<input  type="radio" style="width: 50px; height: 25px; margin-left: -15px; margin-top: 5px; font-size: 18px; text-align: center;" value="2" name="apptype" />
				</div>
				
				<div
					style="width: 300px; height: 40px; margin: auto; margin-left: 120px; margin-top: 20px; font-size: 18px; text-align: center; line-height: 40px;">
					<span style="width: 100px; position: relative; top: -7px;">未发布：</span>
					<input  type="radio"
						style="width: 50px; height: 25px; margin-left: -15px; margin-top: 5px; font-size: 18px; text-align: center;"
						value="0" name="versionstate" checked="checked" /> <span
						style="width: 100px; position: relative; top: -7px; margin-left: 20px;">已发布：</span>
					<input  type="radio"
						style="width: 50px; height: 25px; margin-left: -15px; margin-top: 5px; font-size: 18px; text-align: center;"
						value="1" name="versionstate" />
				</div>
				<input type="button"
					style="width: 100px; height: 40px; margin: auto; margin-left: 150px; margin-top: 20px; font-size: 18px"
					value="确定" onclick="addversion()"> <input type="button"
					style="width: 100px; height: 40px; margin: auto; margin-left: 180x; margin-top: -40px; font-size: 18px"
					value="取消" onclick="unshowaddversion()">
			</div>
		</div>
	</div>

<!-- 修改版本弹框 -->
	<div id="edit" class="edit"></div>
	<div id="edit_sec"
		style="position: fixed; width: 100%; height: 300px; z-index: 300;">
		<div id="edit_last" class="edit_last">
			<div
				style="position: fixed; width: 600px; height: 700px; background: #4cc2ff; margin-left: 50px; margin-top: 50px;">
				<input id="versionid" type="hidden"> 
				<input id="oldversioncode" type="text"style="width: 500px; height: 40px; margin: auto; margin-left: 50px; margin-top: 13px; font-size: 18px; text-align: center;"disabled="disabled" />
					 <input id="editversioncode" type="text"style="width: 500px; height: 40px; margin: auto; margin-left: 50px; margin-top: 20px; font-size: 18px; text-align: center;"placeholder="请输入新版本号" onblur="validate2()" /> 
					<input id="oldversionname" type="text"style="width: 500px; height: 40px; margin: auto; margin-left: 50px; margin-top: 13px; font-size: 18px; text-align: center;"disabled="disabled" />
					 <input id="editversionname" type="text"style="width: 500px; height: 40px; margin: auto; margin-left: 50px; margin-top: 20px; font-size: 18px; text-align: center;"placeholder="请输入新客户端名称" /> 
					<input id="oldversiondownload" type="text"style="width: 500px; height: 40px; margin: auto; margin-left: 50px; margin-top: 13px; font-size: 18px; text-align: center;"disabled="disabled" />
					 <input id="editversiondownload" type="text"style="width: 500px; height: 40px; margin: auto; margin-left: 50px; margin-top: 20px; font-size: 18px; text-align: center;"placeholder="请输入新下载地址" /> 
					
				<div style="width: 300px; height: 40px; margin: auto; margin-left: 150px; margin-top: 20px; font-size: 18px; text-align: center; line-height: 40px;">
					<span style="width: 100px; position: relative; top: -7px;">小版本：</span>
					<input id="smalltype" type="radio"style="width: 50px; height: 25px; margin-left: -15px; margin-top: 5px; font-size: 18px; text-align: center;cursor: pointer;"value="0" name="edittype" /> 
						<span style="width: 100px; position: relative; top: -7px; margin-left: 20px;">大版本：</span>
					<input id="bigtype" type="radio" style="width: 50px; height: 25px; margin-left: -15px; margin-top: 5px; font-size: 18px; text-align: center;cursor: pointer;" value="1" name="edittype" />
				</div>
				
				
				<div style="width: 300px; height: 40px; margin: auto; margin-left: 153px; margin-top: 20px; font-size: 18px; text-align: center; line-height: 40px;">
					<span style="width: 100px; position: relative; top: -7px;">安&nbsp;&nbsp;卓：</span>
					<input id="android" type="radio" style="width: 50px; height: 25px; margin-left: -13px; margin-top: 5px; font-size: 18px; text-align: center;cursor: pointer;" value="0" name="editcategory" />
						 <span style="width: 100px; position: relative; top: -7px; margin-left: 42px;">IOS：</span>
					<input id="ios" type="radio" style="width: 50px; height: 25px; margin-left: -13px; margin-top: 5px; font-size: 18px; text-align: center;cursor: pointer;" value="1" name="editcategory" />
				</div>
				
				<!--  -->
				<div
					style="width: 300px; height: 40px; margin: auto; margin-left: 150px; margin-top: 20px; font-size: 18px; text-align: center; line-height: 40px;">
						 <span style="width: 100px; position: relative; top: -7px;">教练端：</span>
					<input id="coachpoint"  type="radio" style="width: 50px; height: 25px; margin-left: -15px; margin-top: 5px; font-size: 18px; text-align: center;" value="1" name="editapptype" checked="checked" />
						 <span style="width: 100px; position: relative; top: -7px; margin-left: 20px;">学员端：</span>
					<input id="studentpoint"  type="radio" style="width: 50px; height: 25px; margin-left: -15px; margin-top: 5px; font-size: 18px; text-align: center;" value="2" name="editapptype" />
				</div>
				
				
				<div style="width: 300px; height: 40px; margin: auto; margin-left: 150px; margin-top: 20px; font-size: 18px; text-align: center; line-height: 40px;">
					<span style="width: 100px; position: relative; top: -7px;">未发布：</span>
					<input id="noset" type="radio" style="width: 50px; height: 25px; margin-left: -15px; margin-top: 5px; font-size: 18px; text-align: center;cursor: pointer;" value="0" name="editstate"  />
						 <span style="width: 100px; position: relative; top: -7px; margin-left: 20px;">已发布：</span>
					<input id="hadset" type="radio" style="width: 50px; height: 25px; margin-left: -15px; margin-top: 5px; font-size: 18px; text-align: center;cursor: pointer;" value="1" name="editstate" />
				</div>
					 
					 
					 
					 
					 <input type="button"style="width: 100px; height: 40px;  margin-left: 150px; margin-top: 25px; font-size: 18px;cursor: pointer;"value="确定" onclick="editversion()">
					  <input type="text"style="width: 100px; height: 40px; margin: auto; margin-left: 180x; margin-top: -40px; font-size: 18px;background: #4cc2ff;" readonly="readonly">
					 <input type="button"style="width: 100px; height: 40px; margin: auto; margin-left: 180x; margin-top: -40px; font-size: 18px; cursor: pointer;"value="取消" onclick="unshoweditversion()">
			</div>
		</div>
	</div>
</body>
</html>