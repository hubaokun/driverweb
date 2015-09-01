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
    <link rel="stylesheet" href="css/page.css" type="text/css"
	media="screen" />
    <script src="js/jquery-1.11.2.min.js"></script>
    <script type="text/javascript" src="js/common.js"></script>
    <script type="text/javascript" src="coupon/js/coupon.js"></script>
    <script type="text/javascript" src="js/page.js"></script>
    <script type="text/javascript">
        $(function () {
            var index = $("#index").val();
            $("#left_list_" + index).show();
            var j = $("#change_id").val();
            $("#change_" + j + index).addClass('left_list_mask');
        })
    </script>
    <title>驾校发放小巴券</title>
</head>
<input type="hidden" id="index" value="8"/>
<input type="hidden" id="change_id" value="2"/>

<body>
<div id="content">
	<jsp:include page="left.jsp" />
    <div id="content_form">
        <div id="content_form_blank" >
            <form id="addmerchantForm" action="addcoupon.do" enctype="multipart/form-data" method="post">
                <div class="addscenic_center">

                    <div style=" height: 75px; border-bottom: 1px solid #eaeff2;">
                        <div style="float:left; width: 143px; height: 100%; line-height: 75px; border-right: 1px solid #eaeff2;text-align: right;">
                                                                         发放人类型<span style="color:#f83a22; margin-right:16px; margin-left: 8px;">*</span></div>
                            <div style="height:50%; line-height: 75px; float:left; margin-left: 20px;">
                            <select name="addownertype" id="addownertype" onchange="showsearchschoolowner()" class="searchdiv"
                                    style="  height: 50px;  border: 1px solid #cfd9df;margin-top:10px;">
                                <option value="">请选择</option>
                                <option value="1">驾校发放</option>
                                <option value="2">教练发放</option>
                            </select>
                        </div>
                    </div>
                    
                    <div style=" height: 75px; border-bottom: 1px solid #eaeff2;">
                     <div style="float:left; width: 143px; height: 100%; line-height: 75px; border-right: 1px solid #eaeff2;text-align: right;">
                                                                         优惠券类型<span style="color:#f83a22; margin-right:16px; margin-left: 8px;">*</span></div>
                        <div style="height:50%; line-height: 75px; float:left; margin-left: 20px;">
                            <select name="addcoupontype" id="addcoupontype"  class="searchdiv"
                                    style="  height: 50px;  border: 1px solid #cfd9df;margin-top:10px;">
                                <option value="1">时间券</option>
                                <option value="2">抵价券</option>
                            </select>
                        </div>
                    </div>
                    
                    
                    <div style="width: 100%; height: 75px; border-bottom: 1px solid #eaeff2;">
					<div style="float:left; width: 143px; height: 100%; line-height: 75px; border-right: 1px solid #eaeff2;text-align: right;">面值<span style="color:#f83a22; margin-right:16px; margin-left: 8px;">*</span></div>
					<div style="height: 100%; line-height: 75px; float:left; margin-left: 20px;">
						<input value="" name="addvalue"  onkeyup="value=value.replace(/[^\d]/g,'')" required="required" style="width: 800px; height: 33px; padding-left:5px; border: 1px solid #eaeff2; margin-top: 20px;">
					</div>
					</div>
                    <!-- 
                    <div  id="bigsearch" style="width: 100%; height: 75px; border-bottom: 1px solid #eaeff2;">
                    <div style="float:left; width: 143px; height: 100%; line-height: 75px; border-right: 1px solid #eaeff2;text-align: right;">驾校名称<span style="color:#f83a22; margin-right:16px; margin-left: 8px;">*</span></div>
                    <div style="height: 100%; line-height: 75px; float:left; margin-left: 20px;">
                    <input value="" name="owenersearch" id="searchname" onkeyup="searchDriveSchool();" autocomplete="off"  required="required" style="width: 800px; height: 33px; padding-left:5px; border: 1px solid #eaeff2; margin-top: 20px;">
                    </div><div class="binding_detail" style="  left: 164px;top:-20px;clear:both;" id="gartenDetail" >
                    </div><input type="hidden" id="ownerid" name="ownerid" required="required">
                    </div>
                    -->
                    
                    <div id="searchbefore" style="width: 100%; height: 75px; border-bottom: 1px solid #eaeff2;">
                        <div style="float:left; width: 143px; height: 100%; line-height: 75px; border-right: 1px solid #eaeff2;text-align: right;">
                            	发放数量<span style="color:#f83a22; margin-right:16px; margin-left: 8px;">*</span></div>
                        <div style="height: 100%; line-height: 75px; float:left; margin-left: 20px;">
                            <input value="" required="required" onkeyup="value=value.replace(/[^\d]/g,'')" name="pub_count" 
                                   style="width: 200px; height: 33px; padding-left:5px; border: 1px solid #eaeff2; margin-top: 20px;">
                        </div>

                    </div>


					<div style="width: 100%; height: 75px; border-bottom: 1px solid #eaeff2;">
						<div style="float:left; width: 143px; height: 100%; line-height: 75px; border-right: 1px solid #eaeff2;text-align: right;">截止有效日期<span style="color:#f83a22; margin-right:16px; margin-left: 8px;">*</span></div>
						<div style="height: 100%; line-height: 75px; float:left; margin-left: 20px;">
							<input value="" required="required"  name="addendtime" style="width: 800px; height: 33px; padding-left:5px; border: 1px solid #eaeff2; margin-top: 20px;"onclick="WdatePicker({startDate:'',dateFmt:'yyyy-MM-dd'})">
						</div>
					</div>
                    
                    <input type="submit"  value="提交" style="clear: both;height: 60px;width: 184px;background: #4cc2ff; color: #fff; font-size: 16px;text-align: center; line-height: 60px;margin-left: 248px;margin-top: 20px;cursor: pointer;">
             
        </form>
        </div>
    </div>

	
	

</body>
</html>