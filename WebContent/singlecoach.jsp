<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.List" %>
<%@page import="com.daoshun.menu.SideMenu" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="css/page.css" type="text/css" media="screen" />
	<link href="css/style.css" rel="stylesheet" type="text/css">
	<link href="css/ordinary.css" rel="stylesheet" type="text/css">
	<!-- <link href="css/lrtk.css" type="text/css" rel="stylesheet"> -->
	<!-- <link href="css/magiczoomplus.css" type="text/css" rel="stylesheet"> -->
	<script src="js/jquery-1.11.2.min.js"></script>
	<script type="text/javascript" src="js/common.js"></script>
	<script  type="text/javascript" src="coach/js/coachdetail.js"></script>
	<script type="text/javascript" src="systemconfig/js/My97DatePicker/WdatePicker.js"></script>
	<%-- <script type="text/javascript" src="js/mzp-packed.js"></script> --%>
	<%-- <script type="text/javascript" src="js/lrtk.js"></script> --%>
	<script type="text/javascript">
		$(function(){
			var index = $("#index").val();
			$("#left_list_"+index).show();
			var j = $("#change_id").val();
			$("#change_"+j+index).addClass('left_list_mask');
			$("#nav").css("min-height","1468px");
			var level = $("#coachlevel_id").val();
			$("#clevel").val(level);

		})
		$(document).ready(function(){
			var pid='${cuser.provinceid }';//省ID
			var cid='${cuser.cityid }';//市ID
			var aid='${cuser.areaid }';//区ID
			if(pid=='' || pid==0){
				pid="0";
			}
			//pid="330000";
			//cid="330400";
			//aid="330481";
			initProvinceCityArea(pid,cid,aid);
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

		.change {
			position: fixed;
			top: 0px;
			bottom: 0px;
			left: 0px;
			right: 0px;
			background: #707070;
			opacity: 0.5;
			display: none;
		}

		.change_last {
			position: fixed;


			width: 500px;
			height: 300px;
			margin: 0 auto;
			display: none;
		}

		.frozen {
			position: fixed;
			top: 0px;
			bottom: 0px;
			left: 0px;
			right: 0px;
			background: #707070;
			opacity: 0.3;
			display: none;
		}

		.frozen_last {
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
	<%--Ada add the new style  starts--%>
	<style type="text/css">
		.clear-fix {
			clear: both;
		}
		#content hr
		{
			width: 90%;
			margin: 0 auto;
			margin-top: 25px;
			border: none;
			border-top: 1px solid #eee;
		}
		#content_form_table1 table, #content_form_table2 table {
			margin: 0 auto;
		}
		#content_form_table2 table
		{
			width:100%;
			text-align:left;
			margin-bottom:50px;
			margin-left:130px;
		}
		#content_form_table1 table tr input,#content_form_table1 table tr select, #content_form_table2 table tr input {
			height: 30px;
			font-size: 12px;
			color: #555;
			background-color: #fff;
			background-image: none;
			border: 1px solid #ccc;
			border-radius: 4px;
			-webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,.075);
			box-shadow: inset 0 1px 1px rgba(0,0,0,.075);
			-webkit-transition: border-color ease-in-out .15s,-webkit-box-shadow ease-in-out .15s;
			-o-transition: border-color ease-in-out .15s,box-shadow ease-in-out .15s;
			transition: border-color ease-in-out .15s,box-shadow ease-in-out .15s;
		}
		#content_form_table2 table tr  p
		{
			line-height:35px;
		}
		#content_form_table2 table tr label
		{
			background:rgb(229,229,229);
			padding:5px;
		}
		#content_form_table1 table tr span,#content_form_table2 table tr span
		{
			margin-left: 15px;
			padding:5px;
			background:rgb(229,229,229);
			cursor:pointer;
		}

		#content_form_table1 table tr td:nth-child(odd) {
			text-align: right;
			padding-left: 20px;
		}
		#content_form_table1 table tr td:nth-child(even) {
			border-right: 1px solid #eee;
			padding-right: 20px;
		}
		#content_form_table1 table tr td:last-child {
			border-right: none;
		}
		#content_form_table2 table tr
		{
			border-bottom:1px solid #eee;
		}
	</style>
	<%--Ada add the new style  ends--%>
	<title>教练个人详情</title>
</head>
<input type="hidden" id="index" value="1" />
<input type="hidden" id="change_id" value="0"/>
<input type="hidden" id="coachlevel_id" value="${cuser.level}"/>
<input type="hidden" value="${pageCount }" id="pageSize" />
<input type="hidden" value="${pageIndex }" id="pageIndex" />
<body onload="getTop()">
<div id="content">
	<jsp:include page="left.jsp"/>
	<div id="content_form">

		<div id="content_form_top1">
			<div class="delbutton">&nbsp;基本资料</div>
			<div class="addbutton" style="float: left;margin-left: 10px;" onclick="editsinglecoach()">保存修改</div>
			<div class="addbutton" style="float: right;margin-right: 10px;" onclick="gobackcoach()">返&nbsp;回</div>
		</div>

		<div class="clear-fix"></div>
		<form action="" id="singlecoachForm" enctype="multipart/form-data" method="post">
			<div id="content_form_table1" >
				<table >
					<tr>
						<td>ID：</td>
						<td><input name="coachid" id="coachid" value="${cuser.coachid }"  readonly="readonly"></td>
						<td>教龄：</td>
						<td><input name="edityears" id="years" value="${cuser.years }" ></td>
						<td>省市区：</td>
						<td>
						省
						<select  id="province" 
						  onchange="tofindCity(this.value)" name="provinceid"></select><br>
						 市<select id="city" name="cityid"  onchange="tofindArea(this.value)"></select>
						 区<select id="area" name="areaid"  ></select>
						</td>
					</tr>
					<tr>
						<td>真实姓名：</td>
						<td><input name="editrealname" id="realname" value="${cuser.realname }"></td>
						<td>所属驾校：</td>
						<td><input value="${cuser.drive_school }"  readonly="readonly">
							<span onclick="showchangeschool()" value="修改">修改</span>
						</td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>电话号码：</td>
						<td><input value="${cuser.phone }"  readonly="readonly"></td>
						<td>教练等级：</td>
						<td><select id="clevel" name="editlevel">
							<s:iterator value="levelist" var="list">
								<option value="${levelid}">${levelname}</option>
							</s:iterator>
						</select></td>
						<td>详细地址：</td>
						<td><input name="editaddress" id="address" value="${cuser.address}" /></td>
					</tr>
					<tr>
						<td>性别：</td>
						<s:if test="cuser.gender==1">
							<td >
								<select  name="editgender" >
									<option selected="selected"  value="1" >男</option>
									<option   value="2" >女</option>
								</select>
							</td>
						</s:if>
						<s:else>
							<td>
								<select  name="editgender">
									<option   value="1" >男</option>
									<option   selected="selected" value="2" >女</option>
								</select>
							</td>
						</s:else>
						<td>综合评分：</td>
						<td><input name="editscore" value="${cuser.score }" onpaste="return false" ondragenter="return false" oncontextmenu="return false;" onkeyup="this.value=this.value.replace(/[\u4e00-\u9fa5]/g,'')" onkeydown="myKeyDown(this.value, event)"></td>
						<td>注册时间3232：</td>
						<td><input name="editaddtime" value="<s:date name="cuser.addtime" format="yyyy-MM-dd HH:mm:ss" />" onclick="WdatePicker({startDate:'',dateFmt:'yyyy-MM-dd'})"/></td>
					</tr>
					<tr>
						<td>紧急联系人：</td>
						<td><input name="editurgent_person" value="${cuser.urgent_person }" ></td>
						<td>紧急联系人号码：</td>
						<td><input name="editurgent_phone" onkeyup="value=value.replace(/[^\d]/g,'')" value="${cuser.urgent_phone }" ></td>
						<td>自我评价：</td>
						<td><input name="editselfval" type="text" value="${cuser.selfeval }" /></td>
					</tr>
					<tr>
						<td>出生日期：</td>
						<td><input name="editbirthday" value="${cuser.birthday }"  onclick="WdatePicker({startDate:'',dateFmt:'yyyy-MM-dd'})" realvalue=""></td>
						<td>
							<%--注册时间：--%>
						</td>
						<td>
							<%--<input name="editaddtime" value="<s:date name="cuser.addtime" format="yyyy-MM-dd HH:mm:ss" />" onclick="WdatePicker({startDate:'',dateFmt:'yyyy-MM-dd'})"/>--%>
						</td>
						<td></td>
						<td></td>
					</tr>
				</table>

			</div>
			<hr/>
			<div id="content_form_top2">
				<div class="delbutton">&nbsp;教练资格资料</div>
			</div>
			<div class="clear-fix"></div>
			<div id="content_form_table2">
				<table>
					<tr>
						<td><p>身份证号码</p>
							<input name="editid_cardnum" value="${cuser.id_cardnum }" class="inputwithborder" ></td>
						<td><p>身份证到期时间</p>
							<input name="editid_cardexptime" value="${cuser.id_cardexptime }" class="inputwithborder"  onclick="WdatePicker({startDate:'',dateFmt:'yyyy-MM-dd'})"></td>
						<td><p>身份证正面照片
							<label for="file01">修改</label>
							<input name="editid_cardpicfurl" type="file" id="file01" style="display:none;"  onchange="change('image01', 'file01')">
						</p>
							<img alt="" src="${cuser.id_cardpicfurl}" style="width: 180px;height: 130px;" id="image01" onclick="changesize(1,'${cuser.id_cardpicfurl}')"></td>
						<td><p>身份证背面照片
							<label for="file02">修改</label>
							<input name="editid_cardpicburl" type="file" id="file02" style="display:none;" onchange="change('image02', 'file02')">
						</p>
							<img alt="" src="${cuser.id_cardpicburl}" style="width: 180px;height: 130px;" id="image02" onclick="changesize(2,'${cuser.id_cardpicburl}')"></td>
					</tr>
					<tr>
						<td><p>教练证号</p>
							<input name="editcoach_cardnum" value="${cuser.coach_cardnum }" class="inputwithborder" ></td>
						<td><p>教练证到期时间</p>
							<input name="editcoach_cardexptime" value="${cuser.coach_cardexptime }" class="inputwithborder" onclick="WdatePicker({startDate:'',dateFmt:'yyyy-MM-dd'})" realvalue=""></td>
						<td><p>教练证照片
							<label for="file03">修改</label>
							<input name="editcoach_cardpicurl" type="file" id="file03" style="display:none;" onchange="change('image03', 'file03')">
						</p>
							<img alt="" src="${cuser.coach_cardpicurl}" style="width: 180px;height: 130px;" id="image03" onclick="changesize(3,'${cuser.coach_cardpicurl}')"></td>
						<td><p>准教车型
							<span  onclick="showchangemoney()">修改</span>
						</p>
							<p><s:iterator value="coachmodellist" status="list">
								<input name="aaaa" value="${modelname}" style="text-align: center;width:80px;height: 35px;font-size:12px"/>
							</s:iterator></p>
						</td>
					</tr>
					<tr>
						<td><p>驾驶证号</p>
							<input name="editdrive_cardnum" value="${cuser.drive_cardnum}" class="inputwithborder" /></td>
						<td><p>驾驶证到期时间</p>
							<input name="editdrive_cardexptime" value="${cuser.drive_cardexptime }" class="inputwithborder" onclick="WdatePicker({startDate:'',dateFmt:'yyyy-MM-dd'})"></td>
						<td><p>驾驶证照片
							<label for="file04">修改</label>
							<input name="editdrive_cardpicurl" type="file" id="file04" style="width:38px;height: 20px;position: absolute;margin-left: -30px;opacity:0;margin-top: 14px;" onchange="change('image04', 'file04')">
						</p>
							<img alt="" src="${cuser.drive_cardpicurl}" style="width: 180px;height: 130px;" id="image04" onclick="changesize(4,'${cuser.drive_cardpicurl}')"></td>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td><p>车辆年检证号</p>
							<input name="editcar_cardnum" value="${cuser.car_cardnum }" class="inputwithborder" /></td>
						<td><p>车辆年检证到期时间</p>
							<input name="editcar_cardexptime" value="${cuser.car_cardexptime }" class="inputwithborder"  onclick="WdatePicker({startDate:'',dateFmt:'yyyy-MM-dd'})"></td>
						<td><p>车辆行驶证正面照片
							<label for="file05">修改</label>
							<input name="editcar_cardpicfurl" type="file" id="file05" style="display:none;" onchange="change('image05', 'file05')">
						</p>
							<img alt="" src="${cuser.car_cardpicfurl}" style="width: 180px;height: 130px;" id="image05" onclick="changesize(5,'${cuser.car_cardpicfurl}')">
						</td>
						<td><p>车辆行驶证反面照片
							<label for="file06">修改</label>
							<input name="editcar_cardpicburl" type="file" id="file06" style="display:none;" onchange="change('image06', 'file06')">
						</p>
							<img alt="" src="${cuser.car_cardpicburl}" style="width: 180px;height: 130px;" id="image06" onclick="changesize(6,'${cuser.car_cardpicburl}')">
						</td>
					</tr>
					<tr>
						<td><p>教学用车型
							<span onclick="showTeachCar()">修改</span>
						</p>
							<input name="editcarmodel" type="text" value="${cuser.carmodel }"  readonly="readonly"/>
						</td>
						<td><p>教学用车牌照</p>
							<input name="editcarlicense" value="${cuser.carlicense}" /></td>
						<td><p>是否已设置地址</p>
							<s:if test="cuser.address==null">
								<p>未设置</p>
							</s:if>
							<s:else>
								<p>已设置</p>
							</s:else>
						</td>
						<td><p>操作</p>
							<div class="table_edit_button" style="width: 80px;background:#1bbc9b">
								<div class="table_button_edit_icon"></div>
								<div class="table_button_text" onclick="checkpass(2,771,1)">审核通过</div>
							</div>
							<div class="table_edit_button" style="width: 90px;background:#f83a22">
								<div class="table_button_edit_icon"></div>
								<div class="table_button_text" onclick="checknopass(3,771,1)">审核不通过</div>
							</div>
							<div class="clear-fix"></div>
							<div class="table_edit_button" style="width: 90px; margin-top:10px;">
								<div class="table_button_edit_icon"></div>
								<div class="table_button_text" onclick="showchangegmoney(771)">设置保证金</div>
							</div>
							<div class="table_edit_button" style="width: 110px;background:#1bbc9b; margin-top:10px;">
								<div class="table_button_edit_icon"></div>
								<div class="table_button_text" onclick="changecancancel(1,771)">订单可以取消</div>
							</div></td>
					</tr>
				</table>


			</div>
		</form>

	</div>
</div>
<!-- 修改准驾车型弹框 -->
<div id="mask" class="mask"></div>
<div id="mask_sec" style="position: fixed; width: 100%; height: 300px;z-index: 500">
	<div id="mask_last" class="mask_last">
		<div style="position: fixed; width: 300px; height: 200px;background: #4cc2ff;margin-left: 100px;margin-top: 50px;">
			<form  action="" id="changeModelForm" enctype="multipart/form-data" method="post">
				<input type="hidden" name="coachid" value="${cuser.coachid }"/>
				<s:iterator value="modellist" status="list">
					<s:if test="list.index3%==2">
						<div style="clean:both; float:left;"><input name="coachmodelid" id="coachmodelid" type="radio" value="${modelid}" />${modelname}</div>
					</s:if>
					<s:else>
						<div style="float:left;"><input name="coachmodelid" id="coachmodelid" type="radio" value="${modelid}" />${modelname}</div>
					</s:else>
				</s:iterator>
			</form>
			<input type="button" style="width: 100px;height: 40px;margin: auto;margin-left: 50px;margin-right:22px; margin-top: 100px;font-size: 18px" value="确定" onclick="changeModel()">
			<input type="button" style="width: 100px;height: 40px;margin: auto;margin-left: 180px;margin-top: -40px;font-size: 18px" value="取消" onclick="unshowchangegmoney()">
		</div>
	</div>
</div>

<!-- 修改所属教校弹框 -->
<div id="alertbox" class="alertbox"></div>
<div id="alertbox_sec" style="position: fixed; width: 100%; height: 300px;z-index: 500;">
	<div id="alertbox_last" class="alertbox_last" style="">
		<div style="position: fixed; width: 400px; height: 200px;background: #4cc2ff;margin-left: 50px;margin-top: 50px;">
			<div style="font-size: 16px;margin-top: 5px;width: 320px;float: left;margin-left: 20px;">
				关键字：<input type="text" style="height: 25px;width: 230px;" id="schoolkeyword" />
			</div>
			<div onclick="searchDriverSchool();" style="float:left;cursor:pointer;">
				<img src="imgs/common/searchicon.png" width=22px height=22px style="margin-top: 9px;">
			</div>
			<div style="width: 230px;margin: auto;margin-top: 50px;">
				<select id="driveschool_id" style="width: 230px;height: 30px;">
				</select>
			</div>
			<input type="button" style="width: 100px;height: 40px;margin: auto;margin-left: 90px;margin-right:10px; margin-top: 50px;font-size: 18px" value="确定" onclick="changeSchool()">
			<input type="button" style="width: 100px;height: 40px;margin: auto;margin-left: 180px;margin-top: -40px;font-size: 18px" value="取消" onclick="unshowchangeschool()">
		</div>
	</div>
</div>

<!-- 设置保教练等级弹框 -->
<div id="level" class="level"></div>
<div id="level_sec" style="position: fixed; width: 100%; height: 300px;z-index: 300;">
	<div id="level_last" class="level_last">
		<div style="position: fixed; width: 300px; height: 200px;background: #4cc2ff;margin-left: 100px;margin-top: 50px;">
			<input type="hidden" id="level_coachid" value=""/>
			<select id="clevel" class="searchdiv" style="width: 100px;height: 40px;margin-top: 27px;margin-left: 100px;">
				<s:iterator value="levelist" var="list">
					<option value="${levelid}">${levelname}</option>
				</s:iterator>
			</select>
			<input type="button" style="width: 100px;height: 40px;margin: auto;margin-left: 50px;margin-top: 40px;font-size: 18px" value="确定" onclick="changelevel()">
			<input type="button" style="width: 100px;height: 40px;margin: auto;margin-left: 180px;margin-top: -40px;font-size: 18px" value="取消" onclick="unshowchangelevel()">
			<input type="hidden" value="" id="allchangelevel">
		</div>
	</div>
</div>

<!-- 放大图片弹框 -->
<div id="change" class="change" onclick="unshowpic()"></div>
<div id="change_sec" style="position: fixed; width: 100%; height: 300px;z-index: 500">
	<div id="change_last" class="change_last">
		<div style="position: fixed; width: 300px; height: 200px;margin-left: 100px;margin-top: 50px;">
			<img alt="" src="" id="showpic" style="height: 400PX;margin-top: -41px;margin-left: -34px;">
		</div>
	</div>
</div>

<!-- 教学用车型弹框 -->
<div id="frozen" class="frozen"></div>
<div id="frozen_sec" style="position: fixed; width: 100%; height: 300px;z-index: 300">
	<div id="frozen_last" class="frozen_last">
		<div style="position: fixed; width: 300px; height: 200px;background: #4cc2ff;margin-left: 100px;margin-top: 50px;">
			<s:iterator value="teachcarlist" var="driveSchool" status="list">
				<s:if test="list.index3%==2">
					<div style="clean:both; float:left;"><input name="teachcarid" id="teachcarid" type="radio" value="${modelid}" />${modelname}</div>
				</s:if>
				<s:else>
					<div style="float:left;"><input name="teachcarid" id="teachcarid" type="radio" value="${modelid}" />${modelname}</div>
				</s:else>
			</s:iterator>
			<input type="button" style="width: 100px;height: 40px;margin: auto;margin-left: 50px;margin-right:20px; margin-top: 100px;font-size: 18px" value="确定" onclick="changeTeachCar()">
			<input type="button" style="width: 100px;height: 40px;margin: auto;margin-left: 180px;margin-top: -40px;font-size: 18px" value="取消" onclick="unshowfrozen()">
		</div>
	</div>
</div>
</body>
</html>