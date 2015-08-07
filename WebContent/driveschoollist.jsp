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
<script src="driveschool/js/driveschool.js"></script>
<script src="js/common.js"></script>
<script type="text/javascript" src="js/page.js"></script>
<script type="text/javascript">
$(function(){
	var index = $("#index").val()
	$("#left_list_"+index).show();
	var j = $("#change_id").val();
	$("#change_"+j+index).addClass('left_list_mask');
})
</script>
<script type="text/javascript">
		
		function tofindCity(v)
		{
			//发送异步请求
			var params = {provinceid:v};
			jQuery.post("getCityByProvinceId.do", params, showCity, 'json');
		}
		
		function showCity(obj)
		{
			var citys=document.getElementsByName("city");
			for(var j=0;j<citys.length;j++){
				citys[j].length=0;
				for(var i=0;i<obj.length;i++)
		         {
		        	 var o=new Option(obj[i].city,obj[i].cityid);
		         	 //document.getElementById("city").add(o);
		         	citys[j].add(o);
		         }
		         var cityOneId=obj[0].cityid;//得到第一个城市的ID
		         tofindArea(cityOneId);
			}
		}
		function tofindArea(v)
		{
			//发送异步请求
			var params = {cityid:v};
			jQuery.post("getAreaByCityId.do", params, showArea, 'json');
		}
		
		function showArea(obj)
		{
				 var areas=document.getElementsByName("area");
				 for(var j=0;j<areas.length;j++){
					 areas[j].length=0;
					 for(var i=0;i<obj.length;i++)
			         {
			         	var o=new Option(obj[i].area,obj[i].areaid);
			         	areas[j].add(o);
			         }
				 }
		}
		function tofindCityByHotKey()
		{
			//发送异步请求
			var v=$("#hotkey").val();
			var params = {hotkey:v};
			jQuery.post("getCityByHotKey.do", params, showCityHotKey, 'json');
		}
		
		function showCityHotKey(obj)
		{
		         var html="";
		         for(var i=0;i<obj.length;i++)
		         {
		        	 html=html+obj[i].city+"<br>";
		         
		         }
		         $("#citys").html(html);
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
			height: 500px;
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
			overflow: scroll;
		}
	</style>

<title>驾校列表</title>
</head>
<input type="hidden" id="index" value="7" />
<input type="hidden" id="change_id" value="0"/>
<body onload="getSelfTop()">
<div id="content">
<jsp:include page="left.jsp"/>
<div id="content_form">
<div id="content_form_top">
	<div class="addbutton" onclick="showaddschool()">+&nbsp;添加</div>
	
</div>
<div id="content_form_table">
<table  border="0" cellspacing="0" cellpadding="0" style="width: 98%;">
<tr class="tr_th">
<th>驾校ID</th>
<th>驾校名称</th>
<th>驾校联系人</th>
<th>联系人支付宝</th>
<th>驾校联系电话</th>
<th>是否签约</th>
<th>驾校订单抽成</th>
<th>添加时间</th>
<th>操作</th>
</tr>
<s:iterator value="driveSchoollist" var="driveSchool">
<tr class="tr_td">
<td  style="width:50px;" class="border_right_bottom">${schoolid}</td>
<td  style="width:200px;" class="border_right_bottom">${name }</td>
<td  style="width:150px;" class="border_right_bottom">${contact }</td>
<td  style="width:200px;" class="border_right_bottom">${alipay_account}</td>
<td  style="width:200px;" class="border_right_bottom">${telphone }</td>
<td  style="width:200px;" class="border_right_bottom">${iscontract==1?"已签约":"未签约"}</td>
<td  style="width:100px;" class="border_right_bottom">${order_pull}&nbsp;%</td>
<td  style="width:200px;" class="border_right_bottom">
<s:date name="addtime"  format="yyyy-MM-dd HH:mm:ss" /></td>
<td  style="width:120px;" class="border_noright_bottom">


<div class="table_del_button">
<div class="table_button_del_icon"></div>
<div class="table_button_text" onclick="delschool('${name}')">删除</div>
</div>
<div class="table_edit_button">
<div class="table_button_edit_icon"></div>
<div class="table_button_text" 
onclick="showeditschool('${name}','${telphone }','${contact }','${alipay_account }','${order_pull}','${provinceid}','${cityid}','${areaid}','${iscontract}')">修改</div>
</div>


</td>
</tr>
</s:iterator>
<tr>
						<td colspan="10" style="height: 86px;">
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
					var a = [];
					  //总页数少于10 全部显示,大于10 显示前3 后3 中间3 其余....
					  if (pageindex == 1) {
						  //alert(pageindex);
					    a[a.length] = "<a onclick=\"\" class=\"hide_page_prev unclickprev on\"></a>";
					  } else {
					    a[a.length] = "<a onclick=\"previousPage("+pageindex+",'getdriveSchool.do?')\" class=\"page_prev\"></a>";
					  }
					  function setPageList() {
					    if (pageindex == i) {
					      a[a.length] = "<a onclick=\"goPage('getdriveSchool.do?',"+i+")\" class=\"on\">" + i + "</a>";
					    } else {
					      a[a.length] = "<a onclick=\"goPage('getdriveSchool.do?',"+i+")\">" + i + "</a>";
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
					      a[a.length] = "...<a onclick=\"goPage('getdriveSchool.do?',"+count+")\">" + count + "</a>";
					    } else if (pageindex >= count - 3) {
					      a[a.length] = "<a onclick=\"goPage('getdriveSchool.do?',1)\">1</a>...";
					      for (var i = count - 4; i <= count; i++) {
					        setPageList();
					      };
					    } else { //当前页在中间部分
					      a[a.length] = "<a onclick=\"goPage('getdriveSchool.do?',1)\">1</a>...";
					      for (var i = pageindex - 2; i <= pageindex+2; i++) {
					        setPageList();
					      }
					      a[a.length] = "...<a onclick=\"goPage('getdriveSchool.do?',"+count+")\">" + count + "</a>";
					    }
					  }
					  if (pageindex == count) {
						    a[a.length] = "<a onclick=\"\" class=\"hide_page_next unclicknext\"></a> 共"+count+"页  到第  "+
						    "<input type=\"text\" class=\"jump_num\" id=\"topage\"/> 页"+
						    "<a class=\"jump_btn\" onclick=\"gotoPage('getdriveSchool.do?',"+$("#pageSize").val()+")\")\">"+
						    "<a id='page_msg'></a>";
						  } else {
						    a[a.length] = 
						    	"<a onclick=\"nextPage("+$("#pageIndex").val()+",'getdriveSchool.do?')\" "+
						    	"class=\"page_next\"></a> 共"+count+"页 到第 "+
						    "<input type=\"text\" class=\"jump_num\" id=\"topage\"/> 页"+
						    "<a class=\"jump_btn\" onclick=\"gotoPage('getdriveSchool.do?',"+$("#pageSize").val()+")\">"+
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
<!-- 添加驾校弹框 -->
	<div id="mask" class="mask"></div>
	<div id="mask_sec" style="position: fixed; width: 100%; height: 300px;z-index: 300;">
		<div id="mask_last" class="mask_last">
		<div style="position: fixed; width: 450px; height: 600px;background: #4cc2ff;margin-left: 100px;margin-top: 10px;">
		<table>
		<tr>
		  <td><input id="schoolname" type="text" style="width: 240px;height: 40px;margin: auto;margin-left: 80px;margin-top: 20px;font-size: 18px;text-align: center;"  placeholder="请输入要添加的驾校名称"/></td>
		 </tr>
		 <tr>
		  <td><input id="schoolphone" type="text" style="width: 240px;height: 40px;margin: auto;margin-left: 80px;margin-top: 20px;font-size: 18px;text-align: center;"  placeholder="请输入要驾校联系电话" onkeyup="value=value.replace(/[^\d]/g,'')" maxlength="11"/></td>
		</tr>
		<tr>
		  <td><input id="schoolcontact" type="text" style="width: 240px;height: 40px;margin: auto;margin-left: 80px;margin-top: 20px;font-size: 18px;text-align: center;"  placeholder="请输入驾校联系人姓名"/></td>
		  </tr>
		  <tr>
		  <td><input id="alipay_account" type="text" style="width: 240px;height: 40px;margin: auto;margin-left: 80px;margin-top: 20px;font-size: 18px;text-align: center;"  placeholder="请输入联系人支付宝账号"/></td>
		  </tr>
		  <tr>
		  <td><input id="order_pull" type="text" style="width: 240px;height: 40px;margin: auto;margin-left: 80px;margin-top: 20px;font-size: 18px;text-align: center;"  placeholder="请设置驾校提成" onkeyup="value=value.replace(/[^\d]/g,'')"/></td>
		  </tr>
		  <tr align="center">
		  <td >是否签约:
		<select id="flag_contract">
		     <option value="1">已签约</option>
		     <option value="2" selected="selected">未签约</option>
		</select>
		</td>
	</tr>	
		<tr align="center">
		  <td>
		省
		<select  id="province" 
						 style="width: 80px;height: 40px;margin: auto;margin-left: 80px;margin-top: 20px;font-size: 18px;text-align: center;" onchange="tofindCity(this.value)"></select>
					</td></tr>
					<tr align="center"><td>
						 市<select id="city" name="city" style="width: 80px;height: 40px;margin: auto;margin-left: 80px;margin-top: 20px;font-size: 18px;text-align: center;" onchange="tofindArea(this.value)"></select>
						 </td></tr>
					<tr align="center"> <td>
						 区<select id="area" name="area" style="width: 80px;height: 40px;margin: auto;margin-left: 80px;margin-top: 20px;font-size: 18px;text-align: center;"  ></select>
						 </td></tr>
						 </table>
<!-- 		<input type="button" style="width: 100px;height: 40px;margin: auto;margin-left: 100px;margin-top: 25px;font-size: 18px" value="确定" onclick="addshcool()">
		<input type="button" style="width: 100px;height: 40px;margin: auto;margin-top: -40px;font-size: 18px" value="取消" onclick="unshowaddschool()"> -->
		<input type="button" style="width: 100px;height: 40px;margin: auto;margin-left: 125px;margin-top: 15px;font-size: 18px" value="确定" onclick="addshcool()">
		<input type="button" style="width: 100px;height: 40px;margin: auto;margin-top: -40px;font-size: 15px" value="取消" onclick="unshowaddschool()">
		
		</div>
		</div>
	</div>
	
<!-- 	修改驾校信息 -->
<div id="edit" class="edit"></div>
<div id="edit_sec" style="position: fixed; width: 100%; height: 300px;z-index: 300;">
	<div id="edit_last" class="edit_last">
		<div style=" width: 450px; height: 1200px;background: #4cc2ff;margin-left: 25px;margin-top: 25px;">
			<input type="hidden" value=""  id="name">
			<input id="oldschoolname" type="text" style="width: 300px;height: 40px;margin: auto;margin-left: 75px;margin-top: 15px;font-size: 18px;text-align: center;" disabled="disabled"/>
			<input id="editschoolname" type="text" style="width: 300px;height: 40px;margin: auto;margin-left: 75px;margin-top: 18px;font-size: 18px;text-align: center;" placeholder="请输入修改驾校名" />
			<input id="oldschoolphone" type="text" style="width: 300px;height: 40px;margin: auto;margin-left: 75px;margin-top: 18px;font-size: 18px;text-align: center;" disabled="disabled"/>
			<input id="editschoolphone" type="text" style="width: 300px;height: 40px;margin: auto;margin-left: 75px;margin-top: 18px;font-size: 18px;text-align: center;" placeholder="请输入修改联系电话" onchange="editschoolphoneIsNum()"/>
			<input id="oldschoolcontact" type="text" style="width: 300px;height: 40px;margin: auto;margin-left: 75px;margin-top: 18px;font-size: 18px;text-align: center;" disabled="disabled"/>
			<input id="editschoolcontact" type="text" style="width: 300px;height: 40px;margin: auto;margin-left: 75px;margin-top: 18px;font-size: 18px;text-align: center;" placeholder="请输入修改驾校联系人姓名" />
			<input id="oldalipay_account" type="text" style="width: 300px;height: 40px;margin: auto;margin-left: 75px;margin-top: 18px;font-size: 18px;text-align: center;" disabled="disabled"/>
			<input id="editalipay_account" type="text" style="width: 300px;height: 40px;margin: auto;margin-left: 75px;margin-top: 18px;font-size: 18px;text-align: center;" placeholder="请输入修改联系人支付宝账号" />
			<input id="oldorder_pull" type="text" style="width: 300px;height: 40px;margin: auto;margin-left: 75px;margin-top: 18px;font-size: 18px;text-align: center;" disabled="disabled"/>
			<input id="editorder_pull" type="text" style="width: 300px;height: 40px;margin: auto;margin-left: 75px;margin-top: 18px;font-size: 18px;text-align: center;" placeholder="请输入修改驾校订单提成" />
		   <input id=oldflage_contract type="text" style="width: 300px;height: 40px;margin: auto;margin-left: 75px;margin-top: 15px;font-size: 18px;text-align: center;" disabled="disabled"/>
		 <br>  <select id="editflag_contract" style="width:80px;text-align:center;">
		      <option value="1">已签约</option>
		      <option value="2" selected="selected">未签约</option>
		   </select>
		   <br>
			省
			<select  id="province1" style="width: 80px;height: 40px;margin: auto;margin-left: 80px;margin-top: 20px;font-size: 18px;text-align: center;" onchange="tofindCity(this.value)"></select>
			市<select id="city1" name="city" style="width: 80px;height: 40px;margin: auto;margin-left: 80px;margin-top: 20px;font-size: 18px;text-align: center;" onchange="tofindArea(this.value)"></select>区<select id="area1" name="area" style="width: 80px;height: 40px;margin: auto;margin-left: 80px;margin-top: 20px;font-size: 18px;text-align: center;"  ></select>
<br/>
			<input type="button" style="width: 100px;height: 40px;margin: auto;margin-left: 125px;margin-top: 15px;font-size: 18px" value="确定" onclick="editschool()">
			<input type="button" style="width: 100px;height: 40px;margin: auto;font-size: 15px" value="取消" onclick="unshoweditschool()">


		</div>
		</div>
	</div>
</body>
</html>