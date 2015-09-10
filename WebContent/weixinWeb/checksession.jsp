<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
//检测session
String studentid=(String)session.getAttribute("studentid");
if(studentid==null){
	//response.sendRedirect("login.jsp");
}
//session.setAttribute("studentid", "18");
%>
