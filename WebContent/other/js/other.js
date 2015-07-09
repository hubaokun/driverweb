//删除反馈意见
function delfeedback(feedbackid) {
	if (confirm("是否删除该反馈意见？")) {
		$.ajax({
			type : "POST",
			url : "delFeedbackInfo.do",
			data : {
				feedbackid : feedbackid
			},
			success : function(data) {
				if (data == "error") {
					alert("删除失败");
					return;
				}
				if (data == "success") {
					alert("删除成功！");
					window.location.reload();
				}
			}
		});
	}
}

/**
 * 搜索信息反馈
 */
function searchfeedback() {
	var searchname = $("#realname").val();
	var searchphone = $("#phone").val();
	if(searchphone!=''){
		var regu =/^(?:13\d|15[0123456789]|12[0123456789]|11[0123456789]|14[0123456789]|16[0123456789]|17[0123456789]|19[0123456789]|18[0123456789]|17[0123456789])-?\d{5}(\d{3}|\*{3})$/;
		if(!regu.test(searchphone)){
			alert("电话格式不正确");
			return;
		}
	}
	var starttime = $("#starttime").val();
	var endtime = $("#endtime").val();
	if(starttime!=''&&endtime!=''){
		if(starttime>endtime){
			alert("结束时间必须在开始时间之后！");
			return;
		}
	}
	var index=$("#index").val();
	var j=$("#change_id").val();
	window.location = "getFeedback.do?searchname=" + searchname
			+ "&searchphone=" + searchphone + "&starttime=" + starttime
			+ "&endtime=" + endtime+"&index="+index+"&change_id="+j;

}

/**
 * 删除版本信息
 * 
 * @param versionid
 */
function delversion(versionid) {
	if (confirm("是否删除该版本信息？")) {
		$.ajax({
			type : "POST",
			url : "delVersionInfo.do",
			data : {
				versionid : versionid
			},
			success : function(data) {
				if (data == "error") {
					alert("删除失败");
					return;
				}
				if (data == "success") {
					alert("删除成功！");
					window.location.reload();
				}
			}
		});
	}
}

// 添加版本信息
function addversion() {
	var versioncode = $("#versioncode").val();
	var versionname = $("#versionname").val();
	var versiondownload=$("#versiondownload").val();
	if(versioncode==''||versionname==''||versiondownload==''){
		alert("内容不能为空");
		return false;
	}
	var type =document.getElementsByName("type");
	for (var int = 0; int < type.length; int++) {
		if(type[int].checked){
			type=type[int].value;
		}
	}
	var category =document.getElementsByName("versioncategory");
	for (var int = 0; int < category.length; int++) {
		if(category[int].checked){
			category=category[int].value;
		}
	}
	var state =document.getElementsByName("versionstate");
	for (var int = 0; int < state.length; int++) {
		if(state[int].checked){
			state=state[int].value;
		}
	}
	var apptype =document.getElementsByName("apptype");
	for (var int = 0; int < apptype.length; int++) {
		if(apptype[int].checked){
			apptype=apptype[int].value;
		}
	}
	
	if (confirm("是否添加新版本信息？")) {
		$.ajax({
			type : "POST",
			url : "addVersion.do",
			data : {
				versioncode : versioncode,
				versionname : versionname,
				versiontype : type,
				   category : category,
					  state : state,
				     apptype:apptype,
				     versiondownload:versiondownload
			},
			success : function(data) {
				if (data == "error") {
					alert("添加失败，信息不能为空");
					return;
				}
				if (data == "success") {
					alert("添加成功！");
					window.location.reload();
				}
			}
		});
	}
}

//修改版本信息
function editversion(){
	var versionid=$("#versionid").val();
	var editversioncode=$("#editversioncode").val();
	var editversionname=$("#editversionname").val();
	var editversiondownload=$("#editversiondownload").val();
	
	var edittype=document.getElementsByName("edittype");
	for (var int = 0; int < edittype.length; int++) {
		if(edittype[int].checked){
			edittype=edittype[int].value;
		}
	}
	var editcategory=document.getElementsByName("editcategory");
	for (var int = 0; int < editcategory.length; int++) {
		if(editcategory[int].checked){
			editcategory=editcategory[int].value;
		}
	}
	var editstate=document.getElementsByName("editstate");
	for (var int = 0; int < editstate.length; int++) {
		if(editstate[int].checked){
			editstate=editstate[int].value;
		}
	}
	
	var editapptype=document.getElementsByName("editapptype");
	for (var int = 0; int < editapptype.length; int++) {
		if(editapptype[int].checked){
			editapptype=editapptype[int].value;
		}
	}
	if (confirm("是否修改版本信息？")) {
		$.ajax({
			type : "POST",
			url : "editVersion.do",
			data : {
					  versionid : versionid,
				editversioncode : editversioncode,
				editversionname : editversionname,
				editversiontype : edittype,
				   editcategory : editcategory,
				      editstate : editstate,
				      apptype:editapptype,
				      versiondownload:editversiondownload
			},
			success : function(data) {
				if (data == "error1") {
					alert("修改失败，信息不能为空");
					return;
				}
				if (data == "error2") {
					alert("修改失败，版本信息不存在");
					return;
				}
				if (data == "success") {
					alert("修改成功！");
					window.location.reload();
				}
			}
		});
	}
	
}




// 版本号输入校验数字
function validate() {
	var reg = new RegExp("^[0-9]*$");
	var obj = document.getElementById("versioncode");
	if (!reg.test(obj.value)) {
		alert("版本号格式输入错误!");
	}
}


//校验2
function validate2() {
	var reg = new RegExp("^[0-9]*$");
	var obj = document.getElementById("editversioncode");
	if (!reg.test(obj.value)) {
		alert("版本号格式输入错误!");
	}
}

// 显示添加弹框
function showaddversion() {
	$("#mask").show();
	$("#mask_sec").show();
	$("#mask_last").show();
}

function unshowaddversion() {
	$("#mask").hide();
	$("#mask_sec").hide();
	$("#mask_last").hide();
}


//显示修改弹框
function showeditversion(versioncode,versionname,versionid,type,category,state,apptype,download) {
	$("#versionid").val(versionid);
	if(type == 0){
		$("#smalltype").prop("checked",true);
	}else{
		$("#bigtype").prop("checked",true);
	}
	if(category == 0){
		$("#android").prop("checked",true);
	}else{
		$("#ios").prop("checked",true);
	}
	if(state == 0){
		$("#noset").prop("checked",true);
	}else{
		$("#hadset").prop("checked",true);
	}
	if(apptype == 1){
		$("#coachpoint").prop("checked",true);
	}else{
		$("#studentpoint").prop("checked",true);
	}
	
	$("#oldversioncode").val("原版本号："+versioncode);
	$("#oldversionname").val("原客户端名称："+versionname);
	$("#oldversiondownload").val("原客户端下载地址："+download);
	$("#edit").show();
	$("#edit_sec").show();
	$("#edit_last").show();
}

function unshoweditversion() {
	$("#edit").hide();
	$("#edit_sec").hide();
	$("#edit_last").hide();
}



function getNewTop(){
	var h= document.documentElement.clientHeight;
	var height = (h-800)/2;
	var w= document.documentElement.clientWidth;
	var width=(w-700)/2;
	$("#mask_last").css("margin-left",width);
	$("#mask_last").css("top",height);
	$("#level_last").css("margin-left",width);
	$("#level_last").css("top",height);
	$("#edit_last").css("margin-left",width);
	$("#edit_last").css("top",height);
};



function phoneIsNum(){
	var re = /^[0-9]+.?[0-9]*$/;   //判断字符串是否为数字     //判断正整数 /^[1-9]+[0-9]*]*$/  
    var nubmer = $("#phone").val();
     if (!re.test(nubmer))
    {
        alert("请按规定输入电话号码");
        $("#phone").val("");
        return false;
     }
}
//显示回复弹框
function showaddfeedback(fromid,fromtype){
	$("#fromid").val(fromid);
	$("#fromtype").val(fromtype);
	$("#mask").show();
	$("#mask_sec").show();
	$("#mask_last").show();
}


function unshowaddfeedback(){
	$("#mask").hide();
	$("#mask_sec").hide();
	$("#mask_last").hide();
}

//点击编辑内容
function checkfeedback(){
	if($("#feedbackresult").val()=="请输入回复的正文内容"){
		$("#feedbackresult").val("");
		$("#feedbackresult").css("color","#000000");
	}
}

//编辑内容失焦
function checkfeedbacknull(){
	if($("#feedbackresult").val()==""){
		$("#feedbackresult").val("请输入回复的正文内容");
		$("#feedbackresult").css("color","#e5e5e5");
	}
}
function addfeedback(){
	if($("#feedbackresult").val()=="请输入回复的正文内容"){
		alert("请输入要回复的内容");
		return false;
	}
	var fromid= $("#fromid").val();
	var fromtype=$("#fromtype").val();
	var feedbackcontent=$("#feedbackresult").val();
	
	$("#mask").hide();
	$("#mask_sec").hide();
	$.ajax({
		type : "POST",
		url : "replyFeedback.do",
		data : {
			fromid : fromid,
			type:fromtype,
			feedbackcontent:feedbackcontent
		},
		success : function(data) {
			if (data == "error") {
				alert("回复失败");
				return;
			}
			if (data == "success") {
				alert("回复成功");
				window.location.reload();
			}
		}
	});
}



