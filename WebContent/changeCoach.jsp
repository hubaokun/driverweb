<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.List" %>
<%@page import="com.daoshun.menu.SideMenu" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <link href="css/style.css" rel="stylesheet" type="text/css">
  <script src="js/jquery-1.11.2.min.js"></script>
  <script type="text/javascript" src="js/common.js"></script>
  <script type="text/javascript" src="alljs/changeCoach.js"></script>
  <script type="text/javascript">
    $(function () {
      var index = $("#index").val();
      $("#left_list_" + index).show();
      var j = $("#change_id").val();
      $("#change_" + j + index).addClass('left_list_mask');
    })
  </script>
  <title>更换教练</title>
</head>
<input type="hidden" id="index" value="8"/>
<input type="hidden" id="change_id" value="0"/>

<body>
<div id="content">
  <jsp:include page="left.jsp"/>
  <div id="content_form">
    <div id="content_form_blank" style="min-height: 800px">
      <form id="addcoinrecordForm" action="" enctype="multipart/form-data" method="post">
        <div class="addscenic_center">


          <%--搜索学员--%>
          <div id="ssearch" style="width: 100%; height: 75px; border-bottom: 1px solid #eaeff2;">
            <div style="float:left; width: 143px; height: 100%; line-height: 75px; border-right: 1px solid #eaeff2;text-align: right;">
              学员手机<span style="color:#f83a22; margin-right:16px; margin-left: 8px;">*</span></div>
            <div style="height: 100%; line-height: 75px; float:left; margin-left: 20px;">
              <input value="" name="studentname" id="studentname" onclick="clickClear(this)" onkeyup="searchSuser();"
                     autocomplete="off" required="required"
                     style="width: 200px; height: 33px; padding-left:5px; border: 1px solid #eaeff2; margin-top: 20px;">
              <input value="" name="studentid" id="studentid" type="hidden">
            </div>
            <div class="binding_detail" style="  left: 164px;top:-20px;clear:both;" id="optionalStudent">;


            </div>
          </div>

            <%--搜索原教练--%>
            <div id="oldcoachsearch" style="width: 100%; height: 75px; border-bottom: 1px solid #eaeff2;">
              <div style="float:left; width: 143px; height: 100%; line-height: 75px; border-right: 1px solid #eaeff2;text-align: right;">
                原教练手机<span style="color:#f83a22; margin-right:16px; margin-left: 8px;">*</span></div>
              <div style="height: 100%; line-height: 75px; float:left; margin-left: 20px;">
                <input value="" name="oldcoachname" id="oldcoachname" onclick="clickClear(this)" onkeyup="searchOldCoach();"
                       autocomplete="off" required="required"
                       style="width: 200px; height: 33px; padding-left:5px; border: 1px solid #eaeff2; margin-top: 20px;">
                <input value="" name="oldcoachid" id="oldcoachid" type="hidden">
              </div>
              <div class="binding_detail" style="  left: 164px;top:-20px;clear:both;" id="optionaloldcoach">;

              </div>
            </div>


            <%--搜索现教练--%>
            <div id="newcoachsearch" style="width: 100%; height: 75px; border-bottom: 1px solid #eaeff2;">
            <div style="float:left; width: 143px; height: 100%; line-height: 75px; border-right: 1px solid #eaeff2;text-align: right;">
              新教练手机<span style="color:#f83a22; margin-right:16px; margin-left: 8px;">*</span></div>
            <div style="height: 100%; line-height: 75px; float:left; margin-left: 20px;">
              <input value="" name="newcoachname" id="newcoachname" onclick="clickClear(this)" onkeyup="searchNewCoach();"
                     autocomplete="off" required="required"
                     style="width: 200px; height: 33px; padding-left:5px; border: 1px solid #eaeff2; margin-top: 20px;">
              <input value="" name="newcoachid" id="newcoachid" type="hidden">
            </div>
            <div class="binding_detail" style="  left: 164px;top:-20px;clear:both;" id="optionalnewcoach">;


            </div>
          </div>
          <input type="button"  value="提交" onclick="changCoach()"
                 style="clear: both;height: 60px;width: 184px;background: #4cc2ff; color: #fff; font-size: 16px;text-align: center; line-height: 60px;margin-left: 248px;margin-top: 20px;cursor: pointer;">
      </form>
    </div>
  </div>
</div>

</body>
</html>