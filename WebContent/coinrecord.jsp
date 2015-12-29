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
    <script type="text/javascript" src="alljs/coinrecord.js"></script>
    <script type="text/javascript" src="js/page.js"></script>
    <script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript">
        $(function () {
            var index = $("#index").val();
            $("#left_list_" + index).show();
            var j = $("#change_id").val();
            $("#change_" + j + index).addClass('left_list_mask');
        })
    </script>
    <title>发放小巴币</title>
</head>
<input type="hidden" id="index" value="8"/>
<input type="hidden" id="change_id" value="2"/>

<body>
<div id="content">
	<jsp:include page="left.jsp" />
    <div id="content_form">
        <div id="content_form_blank" >
            <form id="addcoinrecordForm" action="" enctype="multipart/form-data" method="post">
                <div class="addscenic_center">

                    

                    <div id="ssearch" style="width: 100%; height: 150px; border-bottom: 1px solid #eaeff2;">
                        <div style="height: 100%; line-height: 75px; float:left; margin-left: 20px;">
                        <span style="color:red;float:left;"></span><span style="float:left;display:inline-block;width:120px;height:75px;line-height:75px;text-align:right;">学员姓名或手机号：</span>
                            <input value="${receivername}" name="receivername" id="receivername" onkeyup="searchSuser();"
                                   autocomplete="off" required="required"
                                   style="width: 200px; height: 33px; padding-left:5px; border: 1px solid #eaeff2; margin-top: 20px;float:left;">
                            <span style="float:left;display:inline-block;width:80px;height:75px;line-height:75px;text-align:right;">支付类型：</span>
                            <select name="type" style="width: 200px; height: 33px; padding-left:5px; border: 1px solid #eaeff2; margin-top: 20px;float:left;">
								<option value="">请选择</option>
                        		<option value="1" <s:if test="1==getType()">selected</s:if>>发放给学员</option>
                            	<option value="2" <s:if test="2==getType()">selected</s:if>>学员支付</option>
                            	<option value="3" <s:if test="3==getType()">selected</s:if>>退款</option>
                            	<option value="4" <s:if test="4==getType()">selected</s:if>>教练兑换</option>
                            </select>
                            <span style="float:left;display:inline-block;width:140px;height:75px;line-height:75px;text-align:right;">处理申请时间区间：</span>
                            <input value="${starttime }" id="starttime" name="starttime" onclick="WdatePicker({startDate:'',dateFmt:'yyyy-MM-dd'})" type="text" class="searchdiv" style="width: 200px; height: 33px; padding-left:5px; border: 1px solid #eaeff2; margin-top: 20px;float:left;">
                            <span style="float:left;display:inline-block;height:75px;line-height:75px;text-align:right;">到</span>
                            <input value="${endtime}" id="endtime" name="endtime" onclick="WdatePicker({startDate:'',dateFmt:'yyyy-MM-dd'})" type="text" class="searchdiv" style="width: 200px; height: 33px; padding-left:5px; border: 1px solid #eaeff2; margin-top: 20px;float:left;">
                            <input value="${receiverid}" name="receiverid" id="receiverid" type="hidden">
                           <s:if test="receiverid!=null">
                             <span style="float:left;">
                             &nbsp;&nbsp;&nbsp;&nbsp;${receivername}剩余小巴币数量：${coinnum }
                             <s:if test="coinnum!=0">
							<a href="javascript:reclaimCoin('${receivername}','${receiverid}')">全部回收${receivername }的小巴币</a>
							</s:if>
							</span>
                           </s:if>
                           
                             <input type="button"  value="查询" onclick="goCoinRecord()"
                           style="clear: both;height: 40px;width: 184px;background: #4cc2ff; color: #fff; font-size: 16px;text-align: center; line-height: 40px;margin-top: 20px;cursor: pointer;">
                        
                        </div>
                        <div class="binding_detail" style="  left: 145px;top:-93px;clear:both;" id="optionalStudent">;
                        </div>
                    </div>
				</div>
        </form>
        </div>
    </div>

	
	<div id="content_form_table">
		<table border="0" cellspacing="0" cellpadding="0" style="width: 98%;">
			<tr class="tr_th">
				<th>发行者类型</th>
				<th>支付者</th>
				<th>接受者</th>
				<th>小巴币数量</th>
				<th>支付类型</th>
				<th>发行者</th>
				<th>添加时间</th>
			</tr>
			<s:iterator value="coinrecordlist" var="coinrecordlist">
				<tr class="tr_td">
					<td style="width: 100px;" class="border_right_bottom"><s:if
							test="ownertype==0">平台发行</s:if> <s:elseif test="ownertype==1">驾校发行
					</s:elseif> <s:elseif test="ownertype==2">教练发行
					</s:elseif>
					</td>
					<td style="width: 100px;" class="border_right_bottom">${payername}</td>
					<td style="width: 100px;" class="border_right_bottom">${receivername}</td>
					<td style="width: 100px;" class="border_right_bottom">
						<s:if test="(1==type || 2==type || 4==type) && 0!=coinnum">
							-
						</s:if>
						<s:elseif test="3==type && 0!=coinnum">
							+
						</s:elseif>
							${coinnum}
					</td>
					<td style="width: 150px;" class="border_right_bottom"><s:if
							test="type==1">发放给学员</s:if> <s:elseif test="type==2">学员支付
					</s:elseif> <s:elseif test="type==3">退款
					</s:elseif><s:elseif test="type==4">教练兑换
					</s:elseif>
					</td>
					<td style="width: 100px;" class="border_right_bottom">${ownername}</td>
					<td style="width: 200px;" class="border_right_bottom"><s:date
							name="addtime" format="yyyy-MM-dd HH:mm:ss" /></td>

				</tr>
			</s:iterator>
			
			
			<!-- 下部分页 -->
			<tr>
						<td colspan="10" style="height: 86px;">
							<div style="float: left; margin-top: 34px; margin-left: 20px;">
								总计：${total}条</div>
							<!-- 下部翻页 -->
							<div style="float: right; margin-top: 34px; margin-right: 20px;">
								<s:if test="%{pageCount>1}">

									<input type="hidden" value="${pageCount }" id="pageSize" />
									<input type="hidden" value="${pageIndex }" id="pageIndex" />
									<input type="hidden" value="${type}" id="paytype"/>
									<input type="hidden" value="${ownertype}" id="searchownertype" />
									<input type="hidden" value="${ownerid}" id="searchownerid" />
									<input type="hidden" value="${starttime}" id="starttime" />
									<input type="hidden" value="${endtime}" id="endtime" />
									<input type="hidden" value="${receiverid}" id="receiverId"/>
									
									<div id="untreatedpage"></div>
									<script type="text/javascript">
										//container 容器，count 总页数 pageindex 当前页数
										function setPage(container, count,
												pageindex) {
											var container = container;
											var count = parseInt(count);
											var pageindex = parseInt(pageindex);
											
											var ownertype = $("#searchownertype").val();
											var ownerid = $("#searchownerid").val();
											var starttime = $("#starttime").val();
											var endtime = $("#endtime").val();
											var receiverid=$("#receiverId").val();
											var paytype=$("#paytype").val();
											
											var a = [];
											//总页数少于10 全部显示,大于10 显示前3 后3 中间3 其余....
											if (pageindex == 1) {
												//alert(pageindex);
												a[a.length] = "<a onclick=\"\" class=\"hide_page_prev unclickprev on\"></a>";
											} else {
												a[a.length] = "<a onclick=\"previousPage("
														+ pageindex
														+ ",'goCoinRecord.do?ownerid="
														+ ownerid
														+ "&ownertype="
														+ ownertype
														+ "&starttime="
														+ starttime
														+ "&endtime="
														+ endtime
														+"&receiverid="
														+receiverid
														+"&type="+paytype
														+ "&')\" class=\"page_prev\"></a>";
											}
											function setPageList() {
												if (pageindex == i) {
													a[a.length] = "<a onclick=\"goPage('goCoinRecord.do?ownerid="
															+ ownerid
															+ "&ownertype="
															+ ownertype
															+ "&starttime="
															+ starttime
															+ "&endtime="
															+ endtime
															+"&receiverid="
															+receiverid
															+"&type="+paytype
															+ "&',"
															+ i
															+ ")\" class=\"on\">"
															+ i + "</a>";
												} else {
													a[a.length] = "<a onclick=\"goPage('goCoinRecord.do?ownerid="
															+ ownerid
															+ "&ownertype="
															+ ownertype
															+ "&starttime="
															+ starttime
															+ "&endtime="
															+ endtime
															+"&receiverid="
															+receiverid
															+"&type="+paytype
															+ "&',"
															+ i
															+ ")\">"
															+ i
															+ "</a>";
												}
											}
											//总页数小于10
											if (count <= 10) {
												for ( var i = 1; i <= count; i++) {
													setPageList();
												}
												;
											} else {
												//总页数大于10页
												if (pageindex <= 4) {
													for ( var i = 1; i <= 5; i++) {
														setPageList();
													}
													a[a.length] = "...<a onclick=\"goPage('goCoinRecord.do?ownerid="
															+ ownerid
															+ "&ownertype="
															+ ownertype
															+ "&starttime="
															+ starttime
															+ "&endtime="
															+ endtime
															+"&receiverid="
															+receiverid
															+"&type="+paytype
															+ "&',"
															+ count
															+ ")\">"
															+ count + "</a>";
												} else if (pageindex >= count - 3) {
													a[a.length] = "<a onclick=\"goPage('goCoinRecord.do?ownerid="
															+ ownerid
															+ "&ownertype="
															+ ownertype
															+ "&starttime="
															+ starttime
															+ "&endtime="
															+ endtime
															+"&receiverid="
															+receiverid
															+"&type="+paytype
															+ "&',1)\">1</a>...";
													for ( var i = count - 4; i <= count; i++) {
														setPageList();
													}
													;
												} else { //当前页在中间部分
													a[a.length] = "<a onclick=\"goPage('goCoinRecord.do?ownerid="
															+ ownerid
															+ "&ownertype="
															+ ownertype
															+ "&starttime="
															+ starttime
															+ "&endtime="
															+ endtime
															+"&receiverid="
															+receiverid
															+"&type="+paytype
															+ "&',1)\">1</a>...";
													for ( var i = pageindex - 2; i <= pageindex + 2; i++) {
														setPageList();
													}
													a[a.length] = "...<a onclick=\"goPage('goCoinRecord.do?ownerid="
															+ ownerid
															+ "&ownertype="
															+ ownertype
															+ "&starttime="
															+ starttime
															+ "&endtime="
															+ endtime
															+"&receiverid="
															+receiverid
															+"&type="+paytype
															+ "&',"
															+ count
															+ ")\">"
															+ count + "</a>";
												}
											}
											if (pageindex == count) {
												a[a.length] = "<a onclick=\"\" class=\"hide_page_next unclicknext\"></a> 共"
														+ count
														+ "页  到第  "
														+ "<input type=\"text\" class=\"jump_num\" id=\"topage\"/> 页"
														+ "<a class=\"jump_btn\" onclick=\"gotoPage('goCoinRecord.do?ownerid="
														+ ownerid
														+ "&ownertype="
														+ ownertype
														+ "&starttime="
														+ starttime
														+ "&endtime="
														+ endtime
														+"&receiverid="
														+receiverid
														+"&type="+paytype
														+ "&',"
														+ $("#pageSize").val()
														+ ")\")\">"
														+ "<a id='page_msg'></a>";
											} else {
												a[a.length] = "<a onclick=\"nextPage("
														+ $("#pageIndex").val()
														+ ",'goCoinRecord.do?ownerid="
														+ ownerid
														+ "&ownertype="
														+ ownertype
														+ "&starttime="
														+ starttime
														+ "&endtime="
														+ endtime
														+"&receiverid="
														+receiverid
														+"&type="+paytype
														+ "&')\" "
														+ "class=\"page_next\"></a> 共"
														+ count
														+ "页 到第 "
														+ "<input type=\"text\" class=\"jump_num\" id=\"topage\"/> 页"
														+ "<a class=\"jump_btn\" onclick=\"gotoPage('goCoinRecord.do?ownerid="
														+ ownerid
														+ "&ownertype="
														+ ownertype
														+ "&starttime="
														+ starttime
														+ "&endtime="
														+ endtime
														+"&receiverid="
														+receiverid
														+"&type="+paytype
														+ "&',"
														+ $("#pageSize").val()
														+ ")\">"
														+ "<a id='page_msg'></a>";
											}
											// 					  a[a.length]="<a href='#' onclick='addunit()' style='float: right;position: relative;right: 50px;padding: 0px; margin: 0px; top: 3px;'><img src='imgs/add_.png'></a>";
											container.innerHTML = a.join("");
										}
										setPage(
												document.getElementById("untreatedpage"),
												parseInt($("#pageSize").val()),
												parseInt($("#pageIndex").val()));
									</script>
								</s:if>
							</div>
						</td>
					</tr>
			
			
		</table>

</body>
</html>