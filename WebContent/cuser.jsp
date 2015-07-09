<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>教练详情</title>
</head>
<link rel="stylesheet" href="css/page.css" type="text/css" media="screen" />
<script src="js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/page.js"></script>

<script type="text/javascript">
		//关键词
	function search(){
		var keyword = $("#keyword").val();
		window.location.href="getCoachlist.do?keyword="+keyword;
		//关键字 调用方法 重新查询
	}
	
	function jumptoeditorunitcus(unitid){
		window.location.href = "jumptoeditorunitcus.do?unitid="+unitid;
	}
</script>

<!-- 复选全选 -->
<script type="text/javascript">
function checkall(){
	var ischecked=document.getElementById("checkall").checked;
	if(ischecked){
		checkallbox();
	}else{
		discheckallbox();
	}
}

function checkallbox(){
	var boxarry=document.getElementsByName("box");
	for (var i = 0; i < boxarry.length; i++) {
		boxarry[i].checked=true;
	}
}

function discheckallbox(){
	var boxarry=document.getElementsByName("box");
	for (var i = 0; i < boxarry.length; i++) {
		boxarry[i].checked=false;
	}
}
</script>

<script type="text/javascript">
function select(){
	var a=document.getElementById("checkall").val;
	alert(a);
}


</script>
<body>
<div style="width:100%; height: 60px;background-color: yellow; text-align: center;line-height: 60px;">详情

<div style="float:right;width:190px; height:40px;font-family: '微软雅黑'; font-size:14px;">
		姓名:<input value="${keyword }" name="keyword" id="keyword" style="width:121px;height:27px;margin-top:10px;border:1px solid #777777;" placeholder="请输入姓名"/>
	</div>
	<div style="float:right;width:190px; height:40px;font-family: '微软雅黑'; font-size:14px;">
		年龄:<input value="${keyword }" name="keyword" id="keyword" style="width:121px;height:27px;margin-top:10px;border:1px solid #777777;" placeholder="年龄"/>
	</div>
	<div style="float:right;width:190px; height:40px;font-family: '微软雅黑'; font-size:14px;">
		状态:<input value="${keyword }" name="keyword" id="keyword" style="width:121px;height:27px;margin-top:10px;border:1px solid #777777;" placeholder="状态"/>
	</div>

</div>

<div style="width: 100%;">
<form action="">

<table width="100%"  border="1" cellspacing="0" style="width: 100%;">
 					
							  <tr>
							  <td><input id="checkall" type="checkbox" value="1" onselect=""/></td>
								 <th>ID</th>
								 <th>真实姓名</th>
								 <th>电话号码</th>
								 <th>性别</th>
								 <th>年龄</th>
								 <th>教龄</th>
								 <th>综合评分</th>
								 <th>状态</th>
								 <th>地址</th>
								 <th>修改手机号码请求</th>
								 <th>操作</th>
								
							  </tr>
						
					<s:iterator value="cuserlist" var="cuser">
							<tr>
							<td><input id="box" name="box" type="checkbox"></td>
								<td>${cuser.coachid}</td>
								<td>${cuser.realname}</td>
								<td>${cuser.phone}</td>
								<td>${cuser.gender}</td>
								<td>${cuser.birthday}</td>
								<td>${cuser.years}</td>
								<td>${cuser.score}</td>
								<td>${cuser.state}</td>
								<td>${cuser.address}</td>
								<td>${cuser.secondphone}</td>
								<td>
								<input  type="button" value="审核"      onclick="clickalert(1)"/>
								<input  type="button" value="设置保证金"  onclick="clickalert(2)"/>
								<input  type="button" value="设置等级"   onclick="clickalert(3)"/>
								<input  type="button" value="设置订单取消" onclick="clickalert(3)"/>
								<input type="button" value="设置教练星级" onclick="clickalert(3)"/>
								|<a href="getCoachDetail.do?coachid=${cuser.coachid}" style="text-decoration: none;color: black; "><b>&nbsp;查看详情</b></a>
								</td>
							</tr>
					</s:iterator>
							<tr>
<td colspan="12" style="height:86px;">
<div style="float:left;margin-top: 34px;margin-left:20px;">
总计：${total}条
</div>


<!-- 下部翻页 -->
<div  style="float:right;margin-top: 34px;margin-right:20px;">
<s:if test="%{pageCount>1}">
				
				<input type="hidden" value="${pageCount }" id="pageSize" />
				<input type="hidden" value="${pageIndex }" id="pageIndex" />
				<div id="untreatedpage" ></div>
				<script type="text/javascript">
					//container 容器，count 总页数 pageindex 当前页数
					function setPage(container, count, pageindex) {
				var container = container;
					var count = parseInt(count);
					var pageindex = parseInt(pageindex);
					var keyword = $("#keyword").val();
					var a = [];
					  //总页数少于10 全部显示,大于10 显示前3 后3 中间3 其余....
					  if (pageindex == 1) {
						  //alert(pageindex);
					    a[a.length] = "<a onclick=\"\" class=\"hide_page_prev unclickprev on\"></a>";
					  } else {
					    a[a.length] = "<a onclick=\"previousPage("+pageindex+",'unitlistofcustomer.do?keyword="+keyword +"&')\" class=\"page_prev\"></a>";
					  }
					  function setPageList() {
					    if (pageindex == i) {
					      a[a.length] = "<a onclick=\"goPage('unitlistofcustomer.do?keyword="+keyword +"&',"+i+")\" class=\"on\">" + i + "</a>";
					    } else {
					      a[a.length] = "<a onclick=\"goPage('unitlistofcustomer.do?keyword="+keyword +"&',"+i+")\">" + i + "</a>";
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
					      a[a.length] = "...<a onclick=\"goPage('unitlistofcustomer.do?keyword="+keyword +"&',"+count+")\">" + count + "</a>";
					    } else if (pageindex >= count - 3) {
					      a[a.length] = "<a onclick=\"goPage('unitlistofcustomer.do?keyword="+keyword +"&',1)\">1</a>...";
					      for (var i = count - 4; i <= count; i++) {
					        setPageList();
					      };
					    } else { //当前页在中间部分
					      a[a.length] = "<a onclick=\"goPage('unitlistofcustomer.do?keyword="+keyword +"&',1)\">1</a>...";
					      for (var i = pageindex - 2; i <= pageindex+2; i++) {
					        setPageList();
					      }
					      a[a.length] = "...<a onclick=\"goPage('unitlistofcustomer.do?keyword="+keyword +"&',"+count+")\">" + count + "</a>";
					    }
					  }
					  if (pageindex == count) {
						    a[a.length] = "<a onclick=\"\" class=\"hide_page_next unclicknext\"></a> 共"+count+"页  到第  "+
						    "<input type=\"text\" class=\"jump_num\" id=\"topage\"/> 页"+
						    "<a class=\"jump_btn\" onclick=\"gotoPage('unitlistofcustomer.do?keyword="+keyword +"&',"+$("#pageSize").val()+")\")\">"+
						    "<a id='page_msg'></a>";
						  } else {
						    a[a.length] = 
						    	"<a onclick=\"nextPage("+$("#pageIndex").val()+",'unitlistofcustomer.do?keyword="+keyword +"&')\" "+
						    	"class=\"page_next\"></a> 共"+count+"页 到第 "+
						    "<input type=\"text\" class=\"jump_num\" id=\"topage\"/> 页"+
						    "<a class=\"jump_btn\" onclick=\"gotoPage('unitlistofcustomer.do?keyword="+keyword +"&',"+$("#pageSize").val()+")\">"+
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
</form>
</div>
</body>
</html>