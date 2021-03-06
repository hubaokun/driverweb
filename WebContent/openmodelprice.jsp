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

//添加开通城市
function showaddcity() {
	$("#mask").show();
	$("#mask_sec").show();
	$("#mask_last").show();
	var params = {};
	jQuery.post("getProvinceToJson.do", params, showProvince, 'json');
}
//显示省信息
function showProvince(obj)
{
         document.getElementById("province").length=0;
         var o1=new Option("请选择",0);
      	  document.getElementById("province").add(o1);
         for(var i=0;i<obj.length;i++)
         {
         	var o=new Option(obj[i].province,obj[i].provinceid);
         	document.getElementById("province").add(o);
         }
}



function unshowaddcity() {
	$("#mask").hide();
	$("#mask_sec").hide();
	$("#mask_last").hide();
}
//-------------修改
//显示修改
function showeditcity() {
	$("#editForm").show();
}
function unshoweditcity() {
	$("#editForm").hide();
}
//点击事件
function editCitys()
{
	var cityid=$('input:radio[name=cityEdit]:checked').val();
	if(cityid!=null && cityid!="")
	{
		showeditcity();
		getCityInfoByCityId(cityid);
		
		
	}	
}


//设置城市和省的信息
function getCityInfoByCityId(cityid)
{
	var obj;
	 function parseData(data)
	 {
        	 obj=eval(data);
        	 $("#C1M").attr("value",obj.c1marketprice);
        	 $("#C1X").attr("value",obj.c1xiaobaprice);
        	 $("#C2M").attr("value",obj.c2marketprice);
        	 $("#C2X").attr("value",obj.c2xiaobaprice);
        	 $("#cid").attr("value",obj.id);
	 }
	 //获取价格信息
	 $.ajax({
             type: "GET",
             url: "getOpenModelPriceByCityId.do",
             data: {cid:cityid},
             dataType: "json",
             async : false,
             success:parseData
             });
	 //获取所有省市信息
	 $.ajax({  
	         type : "post",  
	          url : "getProvinceToJson.do",  
	          data :{}, 
	          dataType: "json",
	          async : false,  
	          success :showProvince2
	     }); 
	//显示默认省信息
		provinceId=setProvinceByCityId(cityid);
	//显示获取全部市信息，并设置默认市
		setCityByProvinceId(provinceId);
		$("#city2").val(cityid);
}
//
function showProvince2(obj)
{
		 obj=eval(obj);
         document.getElementById("province2").length=0;
         for(var i=0;i<obj.length;i++)
         {
         	var o=new Option(obj[i].province,obj[i].provinceid);
         	document.getElementById("province2").add(o);
         }
}
//通过城市id查找省的信息
function setProvinceByCityId(cityId)
{
	var provinceId;
	function setSelectInput(data)
	{
		  data=eval(data);
		  $("#province2").val(data.provinceid);
		  provinceId=data.provinceid;

	}
	$.ajax({  
        type : "get",  
         url : "getProvinceByCityId.do",  
         data :{cid:cityId},
         dataType: "json",
         async : false,  
         success :setSelectInput
    }); 
	return  provinceId;
}
//通过省id查找城市列表
function setCityByProvinceId(provinceId)
{
	function setCity(data)
	{
		obj=eval(data);
		document.getElementById("city2").length=0;
        for(var i=0;i<obj.length;i++)
        {
        	var o=new Option(obj[i].city,obj[i].cityid);
        	document.getElementById("city2").add(o);
        }
	}
	$.ajax({  
         type : "post",  
          url : "getCityByProvinceId.do",  
          data :{provinceid:provinceId},  
          dataType: "json",
          async : false,  
          success :setCity
     }); 
}







function citySelect()
{
	var cityIds="";
	$('input:checkbox[name=citySelected]:checked').each(function(i){
	       if(0==i){
	    	   cityIds = $(this).val();
	       }else{
	    	   cityIds += (","+$(this).val());
	       }
	      });
	return cityIds;
}

function deleteCitys()
{
	var citys=citySelect();
	if(citys!=0 && citys!="")
	{
		if(confirm("确定要删除吗?"))
		{
			$.get("deleteOpenModelPrice.do",{cities:citys},
					  function(data){
						data=eval(data);
					    if(data=="-1"){
					    	alert("操作失败");
					    }else if(data==1){
					    	removeListView(citys);
					    	alert("操作成功");
					    }
					  });
		}
	}
}


function removeListView(str)
{
	var arr=str.split(",");
	for(var i in arr)
	{
		$("#"+arr[i]).remove();
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
			<div class="addbutton" onclick="showaddcity()">+&nbsp;添加</div>
			<div class="addbutton" style="background:#f83a22;" onclick="deleteCitys()">-&nbsp;删除</div>
			<div class="addbutton" style="background:#4cc2ff;" onclick="editCitys()">&nbsp;&nbsp;修改</div>
			</div>
			
			<div id="content_form_table">
				<table border="0" cellspacing="0" cellpadding="0"
					style="width: 98%;">
					<tr class="tr_th">
					    <th>删</th>
					    <th>改</th>
						<th>城市名称</th>
						<th>城市ID</th>
						<th>C1小巴价</th>
						<th>C1市场价</th>
						<th>C2小巴价</th>
						<th>C2市场价</th>
					</tr>
					<s:iterator value="mplist" var="suser">
						<tr class="tr_td" id="${cityid}">
						    <td style="width:20px;" class="border_right_bottom"><input type="checkbox" value='${cityid}' name='citySelected'/></td>
							<td style="width:20px;" class="border_right_bottom"><input type="radio" value='${cityid}' name='cityEdit'/></td>
							<td style="width: 70px;" class="border_right_bottom">${cityname}</td>
							<td style="width:70px;" class="border_right_bottom">${cityid}</td>
							<td style="width:70px;" class="border_right_bottom">${c1xiaobaprice}</td>
							
							<td style="width: 70px;" class="border_right_bottom">${c1marketprice}</td>
							
							<td style="width: 70px;" class="border_right_bottom">${c2xiaobaprice}</td>
							<td style="width: 70px;" class="border_right_bottom">${c2marketprice}</td>
							
							<%-- <td style="width: 100px;" class="border_noright_bottom">
							
								
								<div class="table_edit">
									<a href="getUserState.do?studentid=${studentid}&index=1&change_id=3&state=2"
										style="text-decoration: none; cursor: pointer;">跟进</a>
								</div>
							</td> --%>

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
					var startenrolltime=$("#startenrolltime").val();
					var endenrolltime=$("#endenrolltime").val();
					var j = $("#change_id").val();
					var index=$("#index").val();
					var a = [];
					  //总页数少于10 全部显示,大于10 显示前3 后3 中间3 其余....
					  if (pageindex == 1) {
						  //alert(pageindex);
					    a[a.length] = "<a onclick=\"\" class=\"hide_page_prev unclickprev on\"></a>";
					  } else {
					    a[a.length] = "<a onclick=\"previousPage("+pageindex+",'getEnrolledStudentByKeyword.do?searchname="+realname+"&searchphone="+phone+"&minsdate="+starttime+"&maxsdate="+endtime +"&minenrollsdate=" + startenrolltime + "&maxenrollsdate="
						+ endenrolltime+"&index="+index+"&change_id"+j+"&')\" class=\"page_prev\"></a>";
					  }
					  function setPageList() {
					    if (pageindex == i) {
					      a[a.length] = "<a onclick=\"goPage('getEnrolledStudentByKeyword.do?searchname="+realname+"&searchphone="+phone+"&minsdate="+starttime+"&maxsdate="+endtime+"&minenrollsdate=" + startenrolltime + "&maxenrollsdate="
							+ endenrolltime +"&index="+index+"&change_id"+j+"&',"+i+")\" class=\"on\">" + i + "</a>";
					    } else {
					      a[a.length] = "<a onclick=\"goPage('getEnrolledStudentByKeyword.do?searchname="+realname+"&searchphone="+phone+"&minsdate="+starttime+"&maxsdate="+endtime +"&minenrollsdate=" + startenrolltime + "&maxenrollsdate="
							+ endenrolltime+"&index="+index+"&change_id"+j+"&',"+i+")\">" + i + "</a>";
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
					      a[a.length] = "...<a onclick=\"goPage('getEnrolledStudentByKeyword.do?searchname="+realname+"&searchphone="+phone+"&minsdate="+starttime+"&maxsdate="+endtime+"&minenrollsdate=" + startenrolltime + "&maxenrollsdate="
							+ endenrolltime+"&index="+index+"&change_id"+j+"&',"+count+")\">" + count + "</a>";
					    } else if (pageindex >= count - 3) {
					      a[a.length] = "<a onclick=\"goPage('getEnrolledStudentByKeyword.do?searchname="+realname+"&searchphone="+phone+"&minsdate="+starttime+"&maxsdate="+endtime+"&minenrollsdate=" + startenrolltime + "&maxenrollsdate="
							+ endenrolltime+"&index="+index+"&change_id"+j+"&',1)\">1</a>...";
					      for (var i = count - 4; i <= count; i++) {
					        setPageList();
					      };
					    } else { //当前页在中间部分
					      a[a.length] = "<a onclick=\"goPage('getEnrolledStudentByKeyword.do?searchname="+realname+"&searchphone="+phone+"&minsdate="+starttime+"&maxsdate="+endtime+"&minenrollsdate=" + startenrolltime + "&maxenrollsdate="
							+ endenrolltime +"&index="+index+"&change_id"+j+"&',1)\">1</a>...";
					      for (var i = pageindex - 2; i <= pageindex+2; i++) {
					        setPageList();
					      }
					      a[a.length] = "...<a onclick=\"goPage('getEnrolledStudentByKeyword.do?searchname="+realname+"&searchphone="+phone+"&minsdate="+starttime+"&maxsdate="+endtime+"&minenrollsdate=" + startenrolltime + "&maxenrollsdate="
							+ endenrolltime+"&index="+index+"&change_id"+j+"&',"+count+")\">" + count + "</a>";
					    }
					  }
					  if (pageindex == count) {
						    a[a.length] = "<a onclick=\"\" class=\"hide_page_next unclicknext\"></a> 共"+count+"页  到第  "+
						    "<input type=\"text\" class=\"jump_num\" id=\"topage\"/> 页"+
						    "<a class=\"jump_btn\" onclick=\"gotoPage('getEnrolledStudentByKeyword.do?searchname="+realname+"&searchphone="+phone+"&minsdate="+starttime+"&maxsdate="+endtime +"&minenrollsdate=" + startenrolltime + "&maxenrollsdate="
							+ endenrolltime+"&index="+index+"&change_id"+j+"&',"+$("#pageSize").val()+")\")\">"+
						    "<a id='page_msg'></a>";
						  } else {
						    a[a.length] = 
						    	"<a onclick=\"nextPage("+$("#pageIndex").val()+",'getEnrolledStudentByKeyword.do?searchname="+realname+"&searchphone="+phone+"&minsdate="+starttime+"&maxsdate="+endtime +"&minenrollsdate=" + startenrolltime + "&maxenrollsdate="
								+ endenrolltime+"&index="+index+"&change_id"+j+"&')\" "+
						    	"class=\"page_next\"></a> 共"+count+"页 到第 "+
						    "<input type=\"text\" class=\"jump_num\" id=\"topage\"/> 页"+
						    "<a class=\"jump_btn\" onclick=\"gotoPage('getEnrolledStudentByKeyword.do?searchname="+realname+"&searchphone="+phone+"&minsdate="+starttime+"&maxsdate="+endtime +"&minenrollsdate=" + startenrolltime + "&maxenrollsdate="
							+ endenrolltime+"&',"+$("#pageSize").val()+")\">"+
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
<form action="addOpenModelPrice.do" method="post">
<!-- 添加驾校弹框 -->
	<div id="mask" class="mask"></div>
	<div id="mask_sec" style="position: fixed; width: 100%; height: 300px;z-index: 300;">
		<div id="mask_last" class="mask_last" style="top:10;background:none;">
		<div style="position: fixed; width: 450px; height: 600px;background: #4cc2ff;margin-left: 100px;margin-top: 10px;border: 5px solid white;">
		<table>
		<tr>
		  <td align="center" style="width:100%">
		  <h2>没有的价格项可以不输入</h2>
		 </td>
		 </tr>
		<tr>
		  <td>
		 
		  <input name="c1marketprice" type="text" style="width: 240px;height: 40px;margin: auto;margin-left: 80px;margin-top: 20px;font-size: 18px;text-align: center;"  placeholder="请输入C1市场价"/></td>
		 </tr>
		 <tr>
		  <td><input name="c1xiaobaprice" type="text" style="width: 240px;height: 40px;margin: auto;margin-left: 80px;margin-top: 20px;font-size: 18px;text-align: center;"  placeholder="请输入C1小巴价" maxlength="11"/></td>
		</tr>
		<tr>
		  <td>
		  
		  <input  name="c2marketprice" type="text" style="width: 240px;height: 40px;margin: auto;margin-left: 80px;margin-top: 20px;font-size: 18px;text-align: center;"  placeholder="请输入C2市场价"/></td>
		  </tr>
		  <tr>
		  <td><input  name="c2xiaobaprice"  type="text" style="width: 240px;height: 40px;margin: auto;margin-left: 80px;margin-top: 20px;font-size: 18px;text-align: center;"  placeholder="请输入C2小巴价"/></td>
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
					
						 </table>
		<input type="submit" style="width: 100px;height: 40px;margin: auto;margin-left: 125px;margin-top: 15px;font-size: 18px" value="确定">
		<input type="button" style="width: 100px;height: 40px;margin: auto;margin-top: -40px;font-size: 15px" value="取消" onclick="unshowaddcity()">
	
</form>	
		</div>
		</div>
	</div>

<form id="editForm" action="editOpenModelPrice.do" method="post" style="width:100%; height:100%; background-color:rgba(122,122,122,0.5);display:block;z-index:500; position:fixed;top:0px;left:0px;display:block;display:none;">
    <div style="width:450px;height:600px;margin:auto;margin-top:30px;background:rgb(76,194,255);opacity:1;border:5px white solid;">
    <span style="width:100%;height:60px; font-size:20px;line-height:60px;text-align:center;clear:both;display:block;">修改</span>
    <input  id="cid"name="cid" type="hidden"/>
    <input  id="C1M" name="c1marketprice" type="text" style="width: 240px;height: 40px;margin: auto;margin-left: 80px;margin-top: 20px;font-size: 18px;text-align: center;"  placeholder="请输入C1市场价"/>C1市场价
    <input  id="C1X" name="c1xiaobaprice" type="text" style="width: 240px;height: 40px;margin: auto;margin-left: 80px;margin-top: 20px;font-size: 18px;text-align: center;"  placeholder="请输入C1小巴价" maxlength="11"/>C1小巴价
    <input  id="C2M" name="c2marketprice" type="text" style="width: 240px;height: 40px;margin: auto;margin-left: 80px;margin-top: 20px;font-size: 18px;text-align: center;"  placeholder="请输入C2市场价"/>C2市场价
    <input  id="C2X" name="c2xiaobaprice"  type="text" style="width: 240px;height: 40px;margin: auto;margin-left: 80px;margin-top: 20px;font-size: 18px;text-align: center;"  placeholder="请输入C2小巴价"/>C2小巴价
    <br/>
    
    <span style="width:80px; text-align:right;display:block;height:40px;float:left;margin-top: 20px;line-height:40px;font-size: 18px;">省：</span> <select  id="province2"  onchange="setCityByProvinceId(this.value)" style="width: 240px;height: 40px;margin: auto;margin-left:0px;margin-top: 20px;font-size: 18px;text-align: center;float:left;"></select>
	<span style="width:80px; text-align:right;display:block;hegiht:40px;float:left;margin-top: 20px;line-height:40px;clear:left;font-size: 18px;">市：</span> <select id="city2" name="city" onchange="tofindArea(this.value)" style="width: 240px;height: 40px;margin: auto;margin-left:0px;margin-top: 20px;font-size: 18px;text-align: center;float:left;"></select>
	<div style="clear:both;"></div>
	<span style="width:280px;height:40px;display:block; margin:auto;margin-top:20px;clear:both;">
	<input type="submit" style="width: 100px;height: 40px;font-size: 18px;float:left; margin-right:20px;" value="确定">
	<input type="button" style="width: 100px;height: 40px;font-size: 15px;float:left; margin-left:20px;" value="取消" onclick="unshoweditcity()">
 	
 	</span>
 </div>
</form>	
</div>	

</body>
</html>