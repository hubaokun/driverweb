<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.daoshun.menu.SideMenu"%>
<%@page import="com.daoshun.menu.MenuItem"%>
<%@page import="com.daoshun.menu.SubItem"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="css/left.css" rel="stylesheet" type="text/css">
<title></title>
<script type="text/javascript">
function updown(index) {
	$("#left_list_"+index).toggle("normal");
}

function goaction(actionUrl,index,j){
	window.location.href = actionUrl+"?index="+index+"&change_id="+j;
}


</script>
<%
	SideMenu instance = SideMenu.getInstance();
	HttpSession Session = request.getSession();
%>
</head>
<body>
	<jsp:include page="top.jsp" />
	<div id="nav" style="left: 0px;">
		<div id="nav_left">



			<s:if test="#session.usertype==0">
				<%
					List<MenuItem> menuItems = instance.getMenuItems();
						for (int i = 0; i < menuItems.size(); i++) {
				%>
				<div class="left_1" onclick="updown(<%=i%>)">
					<div class="left1_1">
						<img alt="" src="<%=menuItems.get(i).getIconURL()%>" width="17px"
							height="21px" style="margin-right: 14px;"><%=menuItems.get(i).getName()%></div>
				</div>
				<div class="left_list" id="left_list_<%=i%>">
					<%
						List<SubItem> subItems = menuItems.get(i).getSubItems();
								for (int j = 0; j < subItems.size(); j++) {
					%>
					<div id="change_<%=j%><%=i%>" class="left_list_mask_detail"
						onclick="goaction('<%=subItems.get(j).getAction()%>','<%=i%>','<%=j%>');">
						<div class="left_list_detail">
							<%=subItems.get(j).getName()%>
						</div>
					</div>
					<%
						}
					%>
				</div>

				<%
					}
				%>
			</s:if>
			<s:elseif test="#session.usertype==2">
				<%
					List<MenuItem> menuItems = instance.getMenuItems();
				%>
				<div class="left_1" onclick="updown(2)">
					<div class="left1_1">
						<img alt="" src="<%=menuItems.get(2).getIconURL()%>" width="17px"
							height="21px" style="margin-right: 14px;"><%=menuItems.get(2).getName()%></div>
				</div>
				<div class="left_list" id="left_list_2">
					<%
						List<SubItem> subItems = menuItems.get(2).getSubItems();
							for (int j = 0; j < subItems.size(); j++) {
					%>
					<div id="change_<%=j%>2" class="left_list_mask_detail"
						onclick="goaction('<%=subItems.get(j).getAction()%>','<%=2%>','<%=j%>');">
						<div class="left_list_detail">
							<%=subItems.get(j).getName()%>
						</div>
					</div>
					<%
						}
					%>
				</div>
				<div class="left_1" onclick="updown(9)">
					<div class="left1_1">
						<img alt="" src="<%=menuItems.get(9).getIconURL()%>" width="17px"
							height="21px" style="margin-right: 14px;"><%=menuItems.get(9).getName()%></div>
				</div>
				<div class="left_list" id="left_list_9">
					<div id="change_59" class="left_list_mask_detail"
						onclick="goaction('goSchoolGrantCoupon.do','9','5');">
						<div class="left_list_detail">
							驾校发放小巴券
						</div>
					</div>
					
					<div id="change_69" class="left_list_mask_detail"
						onclick="goaction('goSchoolGrantCoin.do','9','6');">
						<div class="left_list_detail">
							驾校发放小巴币
						</div>
					</div>
				</div>
			</s:elseif>
			<s:else>
				<%
					List<MenuItem> menuItems = instance.getMenuItems();
						for (int i = 0; i < menuItems.size(); i++) {
							List<Integer> aaa = (List<Integer>) session.getAttribute("permission");
							for (int k = 0; k < aaa.size(); k++) {
								if (i == (aaa.get(k)-1)) {
				%>

				<div class="left_1" onclick="updown(<%=i%>)">
					<div class="left1_1">
						<img alt="" src="<%=menuItems.get(i).getIconURL()%>" width="17px"
							height="21px" style="margin-right: 14px;"><%=menuItems.get(i).getName()%></div>
				</div>
				<div class="left_list" id="left_list_<%=i%>">
					<%
						List<SubItem> subItems = menuItems.get(i).getSubItems();
						for (int j = 0; j < subItems.size(); j++) {
							HashMap<String,List<Integer>> submap=(HashMap<String,List<Integer>>)session.getAttribute("subpermission");
							List<Integer> subpermissdions=submap.get(aaa.get(k).toString());
							  if (subpermissdions!=null && subpermissdions.size()>0)
							  {
									  for(Integer temp:subpermissdions)
								      if(j==(temp-1))
								      {
											    
	    		%>
				<div id="change_<%=j%><%=i%>" class="left_list_mask_detail"
					onclick="goaction('<%=subItems.get(j).getAction()%>','<%=i%>',<%=j%>);">
					<div class="left_list_detail">
						<%=subItems.get(j).getName()%>
					</div>
				</div>
				<%					    	  						    	  
											      }
												  
												  
											  }
											  else
											  {
					%>
					<div id="change_<%=j%><%=i%>" class="left_list_mask_detail"
						onclick="goaction('<%=subItems.get(j).getAction()%>','<%=i%>',<%=j%>);">
						<div class="left_list_detail">
							<%=subItems.get(j).getName()%>
						</div>
					</div>
					<%
											  }
						}
					%>
				</div>

				<%
					}
							}
						}
				%>

			</s:else>

		</div>
	</div>
</body>
</html>