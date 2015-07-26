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
	<script src="js/jquery-1.11.2.min.js"></script>
	<script src="systemconfig/js/systemconfig.js"></script>
	<script src="js/common.js"></script>
	<script type="text/javascript">
		$(function(){
			var index = $("#index").val()
			$("#left_list_"+index).show();
			var j = $("#change_id").val();
			$("#change_"+j+index).addClass('left_list_mask');
		})
	</script>
	<style type="text/css">

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
	</style>

	<title>系统参数设置</title>
</head>
<input type="hidden" id="index" value="${index}" />
<input type="hidden" id="change_id" value="${change_id}"/>
<body onload="getTop()">
<div id="content">
	<jsp:include page="left.jsp"/>
	<div id="content_form">
		<div id="content_form_top">


		</div>
		<div id="content_form_table">
			<table  border="0" cellspacing="0" cellpadding="0" style="width: 70%;">
				<!-- 表头 -->
				<tr class="tr_th">
					<th>设置项</th>
					<th>设置值</th>
					<th>操作</th>
				</tr>

				<!-- 1订单取消时间 -->
				<tr class="tr_td">
					<td style="width:500px;" class="border_right_bottom">订单取消时间</td>
					<td  style="width:500px;" class="border_right_bottom">${systemSetInfo.time_cancel}分钟</td>
					<td  style="width:120px;" class="border_noright_bottom">

						<div class="table_edit_button">
							<div class="table_button_edit_icon"></div>
							<div class="table_button_text" onclick="showedittimecancel('${systemSetInfo.dataid}','${systemSetInfo.time_cancel}','time_cancel')">设置</div>
						</div>
					</td>
				</tr>


				<!-- 2学员确认上车时间 -->
				<tr class="tr_td">
					<td style="width:500px;" class="border_right_bottom">学员确认上车时间</td>
					<td  style="width:500px;" class="border_right_bottom">${systemSetInfo.s_can_up}分钟</td>
					<td  style="width:120px;" class="border_noright_bottom">

						<div class="table_edit_button">
							<div class="table_button_edit_icon"></div>
							<div class="table_button_text" onclick="showedittimecancel('${systemSetInfo.dataid}','${systemSetInfo.s_can_up}','s_can_up')">设置</div>
						</div>
					</td>
				</tr>

				<!-- 3学员确认下车时间 -->
				<tr class="tr_td">
					<td style="width:500px;" class="border_right_bottom">学员确认下车时间</td>
					<td  style="width:500px;" class="border_right_bottom">${systemSetInfo.s_can_down}分钟</td>
					<td  style="width:120px;" class="border_noright_bottom">

						<div class="table_edit_button">
							<div class="table_button_edit_icon"></div>
							<div class="table_button_text" onclick="showedittimecancel('${systemSetInfo.dataid}','${systemSetInfo.s_can_down}','s_can_down')">设置</div>
						</div>
					</td>
				</tr>

				<!-- 4订单结束到结算时间 -->
				<tr class="tr_td">
					<td style="width:500px;" class="border_right_bottom">订单结束到结算时间</td>
					<td  style="width:500px;" class="border_right_bottom">${systemSetInfo.s_order_end}分钟</td>
					<td  style="width:120px;" class="border_noright_bottom">

						<div class="table_edit_button">
							<div class="table_button_edit_icon"></div>
							<div class="table_button_text" onclick="showedittimecancel('${systemSetInfo.dataid}','${systemSetInfo.s_order_end}','s_order_end')">设置</div>
						</div>
					</td>
				</tr>

				<!-- 5平台对订单的抽成 -->
				<tr class="tr_td">
					<td style="width:500px;" class="border_right_bottom">平台对订单的抽成</td>
					<td  style="width:500px;" class="border_right_bottom">${systemSetInfo.order_pull}%</td>
					<td  style="width:120px;" class="border_noright_bottom">

						<div class="table_edit_button">
							<div class="table_button_edit_icon"></div>
							<div class="table_button_text" onclick="showedittimecancel('${systemSetInfo.dataid}','${systemSetInfo.order_pull}','order_pull')">设置</div>
						</div>
					</td>
				</tr>

				<!-- 6学员注册之后赠送的金额 -->
				<tr class="tr_td">
					<td style="width:500px;" class="border_right_bottom">学员注册之后赠送的金额</td>
					<td  style="width:500px;" class="border_right_bottom">${systemSetInfo.s_register_money}元</td>
					<td  style="width:120px;" class="border_noright_bottom">

						<div class="table_edit_button">
							<div class="table_button_edit_icon"></div>
							<div class="table_button_text" onclick="showedittimecancel('${systemSetInfo.dataid}','${systemSetInfo.s_register_money}','s_register_money')">设置</div>
						</div>
					</td>
				</tr>

				<!-- 7教练注册之后赠送的金额 -->
				<tr class="tr_td">
					<td style="width:500px;" class="border_right_bottom">教练注册之后赠送的金额</td>
					<td  style="width:500px;" class="border_right_bottom">${systemSetInfo.c_register_money}元</td>
					<td  style="width:120px;" class="border_noright_bottom">

						<div class="table_edit_button">
							<div class="table_button_edit_icon"></div>
							<div class="table_button_text" onclick="showedittimecancel('${systemSetInfo.dataid}','${systemSetInfo.c_register_money}','c_register_money')">设置</div>
						</div>
					</td>
				</tr>

				<!-- 8教练注册之后默认的保证金额 -->
				<tr class="tr_td">
					<td style="width:500px;" class="border_right_bottom">教练注册之后默认的保证金额</td>
					<td  style="width:500px;" class="border_right_bottom">${systemSetInfo.c_register_gmoney}元</td>
					<td  style="width:120px;" class="border_noright_bottom">

						<div class="table_edit_button">
							<div class="table_button_edit_icon"></div>
							<div class="table_button_text" onclick="showedittimecancel('${systemSetInfo.dataid}','${systemSetInfo.c_register_gmoney}','c_register_gmoney')">设置</div>
						</div>
					</td>
				</tr>

				<!-- 9学员端默认评价语 -->
				<tr class="tr_td">
					<td style="width:500px;" class="border_right_bottom">学员端默认评价语</td>
					<td  style="width:500px;" class="border_right_bottom">${systemSetInfo.s_default_coment}</td>
					<td  style="width:120px;" class="border_noright_bottom">

						<div class="table_edit_button">
							<div class="table_button_edit_icon"></div>
							<div class="table_button_text" onclick="showedittimecancel('${systemSetInfo.dataid}','${systemSetInfo.s_default_coment}','s_default_coment')">设置</div>
						</div>
					</td>
				</tr>

				<!-- 10教练端默认的评价语 -->
				<tr class="tr_td">
					<td style="width:500px;" class="border_right_bottom">教练端默认的评价语</td>
					<td  style="width:500px;" class="border_right_bottom">${systemSetInfo.c_default_coment}</td>
					<td  style="width:120px;" class="border_noright_bottom">

						<div class="table_edit_button">
							<div class="table_button_edit_icon"></div>
							<div class="table_button_text" onclick="showedittimecancel('${systemSetInfo.dataid}','${systemSetInfo.c_default_coment}','c_default_coment')">设置</div>
						</div>
					</td>
				</tr>

				<!-- 11用户登录验证码过期时间设置 -->
				<tr class="tr_td">
					<td style="width:500px;" class="border_right_bottom">用户登录验证码过期时间设置</td>
					<td  style="width:500px;" class="border_right_bottom">${systemSetInfo.login_vcode_time}天</td>
					<td  style="width:120px;" class="border_noright_bottom">

						<div class="table_edit_button">
							<div class="table_button_edit_icon"></div>
							<div class="table_button_text" onclick="showedittimecancel('${systemSetInfo.dataid}','${systemSetInfo.login_vcode_time}','login_vcode_time')">设置</div>
						</div>
					</td>
				</tr>

				<!-- 12用户可以预订的教练最大时间 -->
				<tr class="tr_td">
					<td style="width:500px;" class="border_right_bottom">用户可以预订的教练最大时间</td>
					<td  style="width:500px;" class="border_right_bottom">${systemSetInfo.book_day_max}天</td>
					<td  style="width:120px;" class="border_noright_bottom">

						<div class="table_edit_button">
							<div class="table_button_edit_icon"></div>
							<div class="table_button_text" onclick="showedittimecancel('${systemSetInfo.dataid}','${systemSetInfo.book_day_max}','book_day_max')">设置</div>
						</div>
					</td>
				</tr>

				<!-- 13教练默认的价格 -->
				<tr class="tr_td">
					<td style="width:500px;" class="border_right_bottom">教练默认的价格</td>
					<td  style="width:500px;" class="border_right_bottom">${systemSetInfo.coach_default_price}元</td>
					<td  style="width:120px;" class="border_noright_bottom">

						<div class="table_edit_button">
							<div class="table_button_edit_icon"></div>
							<div class="table_button_text" onclick="showedittimecancel('${systemSetInfo.dataid}','${systemSetInfo.coach_default_price}','coach_default_price')">设置</div>
						</div>
					</td>
				</tr>

				<!-- 14 教练默认的教学科目 -->
				<tr class="tr_td">
					<td style="width:500px;" class="border_right_bottom">教练默认的教学科目</td>
					<td  style="width:500px;" class="border_right_bottom">${systemSetInfo.coach_default_subject}</td>
					<td  style="width:120px;" class="border_noright_bottom">

						<div class="table_edit_button">
							<div class="table_button_edit_icon"></div>
							<div class="table_button_text" onclick="showedittimecancel('${systemSetInfo.dataid}','${systemSetInfo.coach_default_subject}','coach_default_subject')">设置</div>
						</div>
					</td>
				</tr>

				<!-- 15 订单最多使用小巴券数量 -->
				<tr class="tr_td">
					<td style="width:500px;" class="border_right_bottom">订单最多使用小巴券数量</td>
					<td  style="width:500px;" class="border_right_bottom">${systemSetInfo.can_use_coupon_count}</td>
					<td  style="width:120px;" class="border_noright_bottom">

						<div class="table_edit_button">
							<div class="table_button_edit_icon"></div>
							<div class="table_button_text" onclick="showedittimecancel('${systemSetInfo.dataid}','${systemSetInfo.can_use_coupon_count}','can_use_coupon_count')">设置</div>
						</div>
					</td>
				</tr>

				<!-- 16 订单是否可以使用不同的小巴券 -->
				<tr class="tr_td">
					<td style="width:500px;" class="border_right_bottom">订单是否可以使用不同的小巴券</td>
					<s:if test="systemSetInfo.can_use_diff_coupon==0">
						<td  style="width:500px;" class="border_right_bottom">不可以</td>
					</s:if>
					<s:else>
						<td  style="width:500px;" class="border_right_bottom">可以</td>
					</s:else>
					<td  style="width:120px;" class="border_noright_bottom">

						<div class="table_edit_button">
							<div class="table_button_edit_icon"></div>
							<div class="table_button_text" onclick="showdiffxiaoba('${systemSetInfo.dataid}','${systemSetInfo.can_use_diff_coupon}','can_use_diff_coupon')">设置</div>
						</div>
					</td>
				</tr>


				<!-- 17 订单是否启用广告 -->
				<tr class="tr_td">
					<td style="width:500px;" class="border_right_bottom">是否启用广告</td>
					<s:if test="systemSetInfo.advertisement_flag==0">
						<td  style="width:500px;" class="border_right_bottom">不启用</td>
					</s:if>
					<s:else>
						<td  style="width:500px;" class="border_right_bottom">启用</td>
					</s:else>
					<td  style="width:120px;" class="border_noright_bottom">

						<div class="table_edit_button">
							<div class="table_button_edit_icon"></div>
							<div class="table_button_text" onclick="showdiffadv('${systemSetInfo.dataid}','${systemSetInfo.advertisement_flag}','advertisement_flag')">设置</div>
						</div>
					</td>
				</tr>
				<!--18推荐认证奖励金额 -->
				<tr class="tr_td">
					<td style="width:500px;" class="border_right_bottom">认证奖励金额</td>
					<td  style="width:500px;" class="border_right_bottom">${systemSetInfo.crewardamount}元</td>
					<td  style="width:120px;" class="border_noright_bottom">

						<div class="table_edit_button">
							<div class="table_button_edit_icon"></div>
							<div class="table_button_text" onclick="showedittimecancel('${systemSetInfo.dataid}','${systemSetInfo.crewardamount}','crewardamount_flag')">设置</div>
						</div>
					</td>
				</tr>
				<!--1推荐开单奖励金额 -->
				<tr class="tr_td">
					<td style="width:500px;" class="border_right_bottom">开单奖励金额</td>
					<td  style="width:500px;" class="border_right_bottom">${systemSetInfo.orewardamount}元</td>
					<td  style="width:120px;" class="border_noright_bottom">

						<div class="table_edit_button">
							<div class="table_button_edit_icon"></div>
							<div class="table_button_text" onclick="showedittimecancel('${systemSetInfo.dataid}','${systemSetInfo.orewardamount}','orewardamount_flag')">设置</div>
						</div>
					</td>
				</tr>
				<%--
                <!-- 18 订单是否可以使用不同的小巴券 -->
                <tr class="tr_td">
                <td style="width:500px;" class="border_right_bottom">广告URL</td>

                <div class="table_edit_button">
                    <div class="table_button_edit_icon"></div>
                    <div class="table_button_text" onclick="showdiffxiaoba('${systemSetInfo.dataid}','${systemSetInfo.can_use_diff_coupon}','can_use_diff_coupon')">设置</div>
                </div>
                </td>
                </tr> --%>
			</table>

		</div>
	</div>
</div>


<!-- 设置新值弹出框-->
<div id="edit" class="edit"></div>
<div id="edit_sec" style="position: fixed; width: 100%; height: 300px;z-index: 300;">
	<div id="edit_last" class="edit_last">
		<div style="position: fixed; width: 300px; height: 200px;background: #4cc2ff;margin-left: 100px;margin-top: 50px;">
			<input id="systemid" type="hidden" >
			<input id="colname" type="hidden" >
			<input id="oldvalue" type="text" style="width: 218px;height: 40px;margin: auto;margin-left: 41px;margin-top: 13px;font-size: 18px;text-align: center;" disabled="disabled"/>
			<input id="editvalue" type="text" style="width: 218px;height: 40px;margin: auto;margin-left: 41px;margin-top: 20px;font-size: 18px;text-align: center;" placeholder="请输入新值" />
			<input type="button" style="width: 100px;height: 40px;margin: auto;margin-left: 50px;margin-top: 25px;font-size: 18px" value="确定" onclick="edittime_cancel()">
			<input type="button" style="width: 100px;height: 40px;margin: auto;margin-left: 180x;margin-top: -40px;font-size: 18px" value="取消" onclick="unshowedittimecancel()">
		</div>
	</div>
</div>

<!-- 设置新值弹出框-->
<div id="level" class="level"></div>
<div id="level_sec" style="position: fixed; width: 100%; height: 300px;z-index: 300;">
	<div id="level_last" class="level_last">
		<div style="position: fixed; width: 300px; height: 200px;background: #4cc2ff;margin-left: 100px;margin-top: 50px;">
			<input id="numsystemid" type="hidden" >
			<input id="numcolname" type="hidden" >
			<input id="numoldvalue" type="text" style="width: 218px;height: 40px;margin: auto;margin-left: 41px;margin-top: 13px;font-size: 18px;text-align: center;" disabled="disabled"/>
			<input id="numeditvalue" type="text" style="width: 218px;height: 40px;margin: auto;margin-left: 41px;margin-top: 20px;font-size: 18px;text-align: center;" placeholder="请输入新值" onkeyup="value=value.replace(/[^\d]/g,'')" />
			<input type="button" style="width: 100px;height: 40px;margin: auto;margin-left: 50px;margin-top: 25px;font-size: 18px" value="确定" onclick="edittime_cancel_num()">
			<input type="button" style="width: 100px;height: 40px;margin: auto;margin-left: 180x;margin-top: -40px;font-size: 18px" value="取消" onclick="unshowisnum()">
		</div>
	</div>
</div>

<!-- 是否可以使用不同小巴券弹出框-->
<div id="mask" class="mask"></div>
<div id="mask_sec" style="position: fixed; width: 100%; height: 300px;z-index: 300;">
	<div id="mask_last" class="mask_last">
		<div style="position: fixed; width: 300px; height: 200px;background: #4cc2ff;margin-left: 100px;margin-top: 50px;">
			<input id="systemxiaobaid" type="hidden" >
			<input id="xiaobaname" type="hidden" >
			<input id="xiaobaoldvalue" type="text" style="width: 218px;height: 40px;margin: auto;margin-left: 41px;margin-top: 13px;font-size: 18px;text-align: center;" disabled="disabled"/>
			<select id="diffxiaoba" style="width: 75px; height:25px; margin: auto;margin-left: 110px;margin-top: 20px;">
				<option value="0">不可以</option>
				<option value="1">可以</option>
			</select>
			<input type="button" style="width: 100px;height: 40px;margin: auto;margin-left: 50px;margin-top: 25px;font-size: 18px" value="确定" onclick="updatexiaoba()">
			<input type="button" style="width: 100px;height: 40px;margin: auto;margin-left: 180x;margin-top: -40px;font-size: 18px" value="取消" onclick="unupdatexiaoba()">
		</div>
	</div>
</div>


<!-- 是否可以启用广告弹出框-->
<div id="maskadv" class="mask"></div>
<div id="mask_adv" style="position: fixed; width: 100%; height: 300px;z-index: 300;">
	<div id="adv_last" class="mask_last">
		<div style="position: fixed; width: 300px; height: 200px;background: #4cc2ff;margin-left: 400px;top: 250px;">
			<input id="advid" type="hidden" >
			<input id="advflag" type="hidden" >
			<input id="advoldvalue" type="text" style="width: 218px;height: 40px;margin: auto;margin-left: 41px;margin-top: 13px;font-size: 18px;text-align: center;" disabled="disabled"/>
			<select id="diffadv" style="width: 75px; height:25px; margin: auto;margin-left: 110px;margin-top: 20px;">
				<option value="0">不启用</option>
				<option value="1">启用</option>
			</select>
			<input type="button" style="width: 100px;height: 40px;margin: auto;margin-left: 50px;margin-top: 25px;font-size: 18px" value="确定" onclick="updateadv()">
			<input type="button" style="width: 100px;height: 40px;margin: auto;margin-left: 180x;margin-top: -40px;font-size: 18px" value="取消" onclick="unupdateadv()">
		</div>
	</div>
</div>

</body>
</html>