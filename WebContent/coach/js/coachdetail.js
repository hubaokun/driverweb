//列表审核通过
function checkpass(index, coachid, wherecheck) {
	if (confirm("确认审核通过？")) {
		$.ajax({
			type : "POST",
			url : "listcheckPass.do",
			data : {
				checknum : index,
				coachid : coachid,
				wherecheck : wherecheck
			},
			success : function(data) {
				if (data == "error") {
					alert("修改失败");
					return;
				}
				if (data == "success") {
					alert("修改成功！");
					window.location.reload();
				}
				if (data == "error1") {
					alert("修改失败");
					return;
				}
				if (data == "success1") {
					alert("修改成功！");
					window.location = "getCoachDetail.do?coachid=" + coachid;
				}
			}
		});
	}
}
// 列表驳回审核
function checknopass(index, coachid, wherecheck) {
	if (confirm("确认审核不通过？")) {
		$.ajax({
			type : "POST",
			url : "listcheckPass.do",
			data : {
				checknum : index,
				coachid : coachid,
				wherecheck : wherecheck
			},
			success : function(data) {
				if (data == "error") {
					alert("修改失败");
					return;
				}
				if (data == "success") {
					alert("修改成功！");
					window.location.reload();
				}
				if (data == "error1") {
					alert("修改失败");
					return;
				}
				if (data == "success1") {
					alert("修改成功！");
					window.location = "getCoachDetail.do?coachid=" + coachid;
				}
			}
		});
	}
}

// 修改教练保证金
function changegmoney() {
	var a= $("#allchangemoney").val();
	if(a==-1){
		$("#allchangemoney").val("");
		var gmoney = document.getElementById("gmoney").value;
		$("#changeallmoney").val(gmoney);
		$("#doallForm").attr("action","dochangeallmoney.do").submit();
	}else{
	var gmoney = document.getElementById("gmoney").value;
	var gmoney_coachid = document.getElementById("gmoney_coachid").value;
	if (confirm("是否修改保证金金额？")) {
		$.ajax({
			type : "POST",
			url : "setGmoney.do",
			data : {
				gmoney : gmoney,
				coachid : gmoney_coachid
			},
			success : function(data) {
				if (data == "error") {
					alert("修改失败");
					return;
				}
				if (data == "success") {
					alert("修改成功！");
					$("#mask").css("display", "none");
					window.location.reload();
				}
			}
		});
	}
	}
}

//修改教练准驾车型
function changeModel() {
	$("#changeModelForm").attr("action", "changeCoachModel.do").submit();
}

//添加余额弹框
function addbalance(coachid) {
	if(coachid==-1){
		var a = document.getElementsByName("checkbox");
		var count=0;
		for (var i = 0; i < a.length; i++) {
			if(a[i].checked){
			count++;
			}
		}
		if(count==0){
			alert("请选择用户");
			return false;
		}else{
		$("#mask").show();
		$("#mask_sec").show();
		$("#mask_last").show();
		$("#changebalance").val(-1);
		}
	}else{
		$("#gmoney_coachid").val(coachid);
		$("#mask").show();
		$("#mask_sec").show();
		$("#mask_last").show();
	}
}

//减少余额弹框
function lessenbalance(coachid) {
	if(coachid==-1){
		var a = document.getElementsByName("checkbox");
		var count=0;
		for (var i = 0; i < a.length; i++) {
			if(a[i].checked){
			count++;
			}
		}
		if(count==0){
			alert("请选择用户");
			return false;
		}else{
			$("#level").show();
			$("#level_sec").show();
			$("#level_last").show();
			$("#allchangelevel").val(-1);
		}
	}else{
			$("#level_coachid").val(coachid);
			$("#level").show();
			$("#level_sec").show();
			$("#level_last").show();
	}
}

//添加教练余额
function addBalanceSubmit() {
	var a= $("#changebalance").val();
	if(a==-1){
		$("#changebalance").val("");
		var gmoney = document.getElementById("morebalance").value;
		$("#changeallmoney").val(gmoney);
		$("#doallForm").attr("action","addcoachbalance.do").submit();
	}else{
	var gmoney = document.getElementById("morebalance").value;
	var gmoney_coachid = document.getElementById("gmoney_coachid").value;
	if (confirm("是否修改教练余额？")) {
		$.ajax({
			type : "POST",
			url : "setBalance.do",
			data : {
				money : gmoney,
				coachid : gmoney_coachid
			},
			success : function(data) {
				if (data == "success") {
					alert("修改成功！");
					$("#mask").css("display", "none");
					window.location.reload();
				}
			}
		});
	}
	}
}

//减少余额
function lessenBalanceSubmit() {
	var a= $("#allchangelevel").val();
	if(a==-1){
		$("#allchangelevel").val("");
		var clevel = document.getElementById("lessbalance").value;
		$("#changeallmoney").val(clevel);
		$("#doallForm").attr("action","lessencoachbalance.do").submit();
	}else{
	var clevel = document.getElementById("lessbalance").value;
	var level_coachid = document.getElementById("level_coachid").value;
	if (confirm("是否修改教练余额？")) {
		$.ajax({
			type : "POST",
			url : "lessenBalance.do",
			data : {
				money : clevel,
				coachid : level_coachid
			},
			success : function(data) {
				if (data == "error") {
					alert("余额不足！");
					return;
				}
				if (data == "success") {
					alert("修改成功！");
					$("#level").css("display", "none");
					window.location.reload();
				}
			}
		});
	}
	}
}

//添加学员余额弹框
function addstudentbalance(coachid) {
	if(coachid==-1){
		var a = document.getElementsByName("checkbox");
		var count=0;
		for (var i = 0; i < a.length; i++) {
			if(a[i].checked){
			count++;
			}
		}
		if(count==0){
			alert("请选择用户");
			return false;
		}else{
		$("#mask").show();
		$("#mask_sec").show();
		$("#mask_last").show();
		$("#changebalance").val(-1);
		}
	}else{
		$("#gmoney_coachid").val(coachid);
		$("#mask").show();
		$("#mask_sec").show();
		$("#mask_last").show();
	}
}

//减少学员余额弹框
function lessenstudentbalance(coachid) {
	if(coachid==-1){
		var a = document.getElementsByName("checkbox");
		var count=0;
		for (var i = 0; i < a.length; i++) {
			if(a[i].checked){
			count++;
			}
		}
		if(count==0){
			alert("请选择用户");
			return false;
		}else{
			$("#level").show();
			$("#level_sec").show();
			$("#level_last").show();
			$("#allchangelevel").val(-1);
		}
	}else{
			$("#level_coachid").val(coachid);
			$("#level").show();
			$("#level_sec").show();
			$("#level_last").show();
	}
}

//添加学员余额
function addStudentBalanceSubmit() {
	var a= $("#changebalance").val();
	if(a==-1){
		$("#changebalance").val("");
		var gmoney = document.getElementById("morebalance").value;
		$("#changeallmoney").val(gmoney);
		$("#doallForm").attr("action","addstudentbalance.do").submit();
	}else{
	var gmoney = document.getElementById("morebalance").value;
	var gmoney_coachid = document.getElementById("gmoney_coachid").value;
	if (confirm("是否修改学员余额？")) {
		$.ajax({
			type : "POST",
			url : "setStudentBalance.do",
			data : {
				money : gmoney,
				studentid : gmoney_coachid
			},
			success : function(data) {
				if (data == "success") {
					alert("修改成功！");
					$("#mask").css("display", "none");
					window.location.reload();
				}
			}
		});
	}
	}
}

//减少学员余额
function lessenStudentBalanceSubmit() {
	var a= $("#allchangelevel").val();
	if(a==-1){
		$("#allchangelevel").val("");
		var clevel = document.getElementById("lessbalance").value;
		$("#changeallmoney").val(clevel);
		$("#doallForm").attr("action","lessenstudentbalance.do").submit();
	}else{
	var clevel = document.getElementById("lessbalance").value;
	var level_coachid = document.getElementById("level_coachid").value;
	if (confirm("是否修改学员余额？")) {
		$.ajax({
			type : "POST",
			url : "lessenStudentBalance.do",
			data : {
				money : clevel,
				coachid : level_coachid
			},
			success : function(data) {
				if (data == "error") {
					alert("余额不足！");
					return;
				}
				if (data == "success") {
					alert("修改成功！");
					$("#level").css("display", "none");
					window.location.reload();
				}
			}
		});
	}
	}
}

// 修改学员金额
function changemoney() {
	var studentmoney = document.getElementById("studentmoney").value;
	var money_studentid = document.getElementById("money_studentid").value;
	if (confirm("是否修改学员金额？")) {
		$.ajax({
			type : "POST",
			url : "setStudentMoney.do",
			data : {
				studentmoney : studentmoney,
				studentid : money_studentid
			},
			success : function(data) {
				if (data == "error") {
					alert("修改失败");
					return;
				}
				if (data == "success") {
					alert("修改成功！");
					$("#mask").css("display", "none");
					window.location.reload();
				}
			}
		});
	}
}

// 修改教练等级
function changelevel() {
	var a= $("#allchangelevel").val();
	if(a==-1){
		$("#allchangelevel").val("");
		var clevel = document.getElementById("clevel").value;
		$("#changealllevel").val(clevel);
		$("#doallForm").attr("action","dochangealllevel.do").submit();
	}else{
	var clevel = document.getElementById("clevel").value;
	var level_coachid = document.getElementById("level_coachid").value;
	if (confirm("是否修改教练等级？")) {
		$.ajax({
			type : "POST",
			url : "setLevel.do",
			data : {
				level : clevel,
				coachid : level_coachid
			},
			success : function(data) {
				if (data == "error") {
					alert("修改失败");
					return;
				}
				if (data == "success") {
					alert("修改成功！");
					$("#level").css("display", "none");
					window.location.reload();
				}
			}
		});
	}
	}
}

//修改教练所属教校
function changeSchool() {
	var coachid = document.getElementById("coachid").value;
	var driveSchoolname = $("#driveschool_id").val(); 
	if (confirm("是否修改教练所属教校？")) {
		$.ajax({
			type : "POST",
			url : "changeSchool.do",
			data : {
				driveSchoolname : driveSchoolname,
				coachid : coachid
			},
			success : function() {
				window.location.reload();
			}
		});
	}
}

//关键字搜索教校
function searchDriverSchool() {
	var schoolkeyword = $("#schoolkeyword").val();
	$.post("searchDriverSchool.do",{schoolkeyword:schoolkeyword},function(data){
		$("#driveschool_id").empty();
		var html = "";
		var list = data.driveSchoollist;
		for(var i = 0;i<list.length;i++){
			html += "<option value='"+list[i].schoolid+"'>"+list[i].name+"</option>";
		}
		$("#driveschool_id").append(html);
	},"json")
}

//修改教练教学用车型
function changeTeachCar() {
	var coachid = document.getElementById("coachid").value;
	var driveSchoolname = $("input[name='teachcarid']:checked").val(); 
	if (confirm("是否修改教练教校用车型？")) {
		$.ajax({
			type : "POST",
			url : "changeTeachCar.do",
			data : {
				driveSchoolname : driveSchoolname,
				coachid : coachid
			},
			success : function() {
				window.location.reload();
			}
		});
	}
}

// 修改教练可否取消订单状态
function changecancancel(index, coachid) {
	if (index == 1) {
		if (confirm("是否修改为订单不可取消？")) {
			$.ajax({
				type : "POST",
				url : "setcancel.do",
				data : {
					canceltype : index,
					coachid : coachid
				},
				success : function(data) {
					if (data == "error") {
						alert("修改失败");
						return;
					}
					if (data == "success") {
						alert("修改成功！");
						window.location.reload();
					}
				}
			});

		}
	} else if (index == 0) {
		if (confirm("是否修改为订单可以取消？")) {
			$.ajax({
				type : "POST",
				url : "setcancel.do",
				data : {
					canceltype : index,
					coachid : coachid
				},
				success : function(data) {
					if (data == "error") {
						alert("修改失败");
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
}

function dofrozen() {
	var a= $("#allchangefrozen").val();
	var frozecoach = $("input[name='aaaa']:checked").val();
	if(frozecoach==0){
		if(a==-1){
			var frozenstart = $("#frozenstart").val();
			var frozenend = $("#frozenend").val();
			if (frozenstart =='') {
				alert("请输入冻结开始时间");
				return false;
			}
			if(frozenend ==''){
				alert("请输入冻结结束时间");
				return false;
			}
			$("#changeallfrozenstart").val(frozenstart);
			$("#changeallfrozenend").val(frozenend);
			$("#doallForm").attr("action","dochangeallfrozen.do").submit();
			$("#allchangefrozen").val("");
		}else{
		var coachid_frozen = $("#coachid_frozen").val();
		var frozenstart = $("#frozenstart").val();
		var frozenend = $("#frozenend").val();
		if (frozenstart == 0) {
			alert("请输入冻结开始时间");
			return false;
		}
		if (frozenend == 0) {
			alert("请输入冻结结束时间");
			return false;
		}
			if (confirm("是否冻结该教练？")) {
				$.ajax({
					type : "POST",
					url : "setfrozentype.do",
					data : {
						frozecoachtype:frozecoach,
						coachid_frozen : coachid_frozen,
						frozenstart : frozenstart,
						frozenend : frozenend
					},
					success : function(data) {
						if (data == "error") {
							alert("冻结失败");
							return;
						}
						if (data == "success") {
							alert("冻结成功！");
							window.location.reload();
						}
					}
				});
			}
		}
	}else if(frozecoach==1){
		if(a==-1){
			$("#frozecoachtype").val(frozecoach);
			$("#doallForm").attr("action","dochangeallfrozen.do").submit();
			$("#allchangefrozen").val("");
		}else{
		var coachid_frozen = $("#coachid_frozen").val();
			if (confirm("是否永久冻结该教练？")) {
				$.ajax({
					type : "POST",
					url : "setfrozentype.do",
					data : {
						coachid_frozen : coachid_frozen
					},
					success : function(data) {
						if (data == "error") {
							alert("冻结失败");
							return;
						}
						if (data == "success") {
							alert("冻结成功！");
							window.location.reload();
						}
					}
				});
			}
		}
	}else if(frozecoach==2){
		if(a==-1){
			$("#frozecoachtype").val(frozecoach);
			$("#doallForm").attr("action","dochangeallfrozen.do").submit();
			$("#allchangefrozen").val("");
		}else{
		var coachid_frozen = $("#coachid_frozen").val();
			if (confirm("是否取消冻结该教练？")) {
				$.ajax({
					type : "POST",
					url : "setfrozentype.do",
					data : {
						frozecoachtype:frozecoach,
						coachid_frozen : coachid_frozen
					},
					success : function(data) {
						if (data == "error") {
							alert("取消冻结失败");
							return;
						}
						if (data == "success") {
							alert("取消冻结成功！");
							window.location.reload();
						}
					}
				});
			}
		}
	}
}

function unfrozen(index) {
	var coachid_unfrozen = index;
	if (confirm("是否解冻该教练？")) {
		$.ajax({
			type : "POST",
			url : "unfrozen.do",
			data : {
				coachid_unfrozen : coachid_unfrozen
			},
			success : function(data) {
				if (data == "error") {
					alert("解冻失败");
					return;
				}
				if (data == "success") {
					alert("解冻成功！");
					window.location.reload();
				}
			}
		});
	}
}

function coachquit(coachid) {
	if (confirm("确认离职？")) {
		$.ajax({
			type : "POST",
			url : "coachquit.do",
			data : {
				coachid : coachid
			},
			success : function(data) {
				if (data == "error") {
					alert("离职失败");
					return;
				}
				if (data == "success") {
					alert("离职成功！");
					window.location.reload();
				}
			}
		});
	}
}

// 显示冻结弹框
function showfrozen(index) {
	 $("#frozenstart").hide();
	 $("#frozenend").hide();
	if(index==-1){
		var a = document.getElementsByName("checkbox");
		var count=0;
		for (var i = 0; i < a.length; i++) {
			if(a[i].checked){
			count++;
			}
		}
		if(count==0){
			alert("请选择用户");
			return false;
		}else{
		$("#allchangefrozen").val(-1);
		$("#frozen").show();
		$("#frozen_sec").show();
		$("#frozen_last").show();
		
		}
	}else{
	$("#coachid_frozen").val(index);
	$("#frozen").show();
	$("#frozen_sec").show();
	$("#frozen_last").show();
	}
}

function showTeachCar() {
	$("#frozen").show();
	$("#frozen_sec").show();
	$("#frozen_last").show();
}

function unshowfrozen(index) {
	$("#frozen").hide();
	$("#frozen_sec").hide();
	$("#frozen_last").hide();
}

// 修改学员金额弹框
function showchangemoney(studentid) {
	$("#money_studentid").val(studentid);
	$("#mask").show();
	$("#mask_sec").show();
	$("#mask_last").show();
}

function unshowchangemoney() {
	$("#mask").hide();
	$("#mask_sec").hide();
	$("#mask_last").hide();
}

// 修改保证金弹框
function showchangegmoney(coachid) {
	if(coachid==-1){
		var a = document.getElementsByName("checkbox");
		var count=0;
		for (var i = 0; i < a.length; i++) {
			if(a[i].checked){
			count++;
			}
		}
		if(count==0){
			alert("请选择用户");
			return false;
		}else{
		$("#mask").show();
		$("#mask_sec").show();
		$("#mask_last").show();
		$("#allchangemoney").val(-1);
		}
	}else{
		$("#gmoney_coachid").val(coachid);
		$("#mask").show();
		$("#mask_sec").show();
		$("#mask_last").show();
	}
}



function unshowchangegmoney() {
	$("#mask").hide();
	$("#mask_sec").hide();
	$("#mask_last").hide();
}

// 修改等级弹框

function showchangelevel(coachid) {
	if(coachid==-1){
		var a = document.getElementsByName("checkbox");
		var count=0;
		for (var i = 0; i < a.length; i++) {
			if(a[i].checked){
			count++;
			}
		}
		if(count==0){
			alert("请选择用户");
			return false;
		}else{
			$("#level").show();
			$("#level_sec").show();
			$("#level_last").show();
			$("#allchangelevel").val(-1);
		}
	}else{
			$("#level_coachid").val(coachid);
			$("#level").show();
			$("#level_sec").show();
			$("#level_last").show();
	}
}

function showchangeschool() {
	$("#driveschool_id").empty();
	$("#schoolkeyword").val('')
	$("#alertbox").show();
	$("#alertbox_sec").show();
	$("#alertbox_last").show();
}

// 信息导出弹框
function alertDataExportBox() {
	$("#alertbox").show();
	$("#alertbox_sec").show();
	$("#alertbox_last").show();
}

function unAlertDataExportBox() {
	$("#alertbox").hide();
	$("#alertbox_sec").hide();
	$("#alertbox_last").hide();
}

function unshowchangelevel() {
	$("#level").hide();
	$("#level_sec").hide();
	$("#level_last").hide();
}

function unshowchangeschool() {
	$("#alertbox").hide();
	$("#alertbox_sec").hide();
	$("#alertbox_last").hide();
}

//学员条件搜索
function allstudentsearch() {
	var j = $("#change_id").val();
	var index = $("#index").val();
	var realname = $("#realname").val();
	var phone = $("#phone").val();
	var starttime = $("#starttime").val();
	var endtime = $("#endtime").val();
	if(starttime!=''&&endtime!=''){
		if(starttime>endtime){
			alert("结束时间必须大于开始时间！");
			return;
		}
	}
	window.location = "getStudentByKeyword.do?searchname=" + realname
			+ "&searchphone=" + phone + "&minsdate=" + starttime + "&maxsdate="
			+ endtime + "&index=" + index + "&change_id=" + j;
}

// 学员条件搜索
function studentsearch() {
	var j = $("#change_id").val();
	var index = $("#index").val();
	var realname = $("#realname").val();
	var phone = $("#phone").val();
	var starttime = $("#starttime").val();
	var endtime = $("#endtime").val();
	if(starttime!=''&&endtime!=''){
		if(starttime>endtime){
			alert("结束时间必须大于开始时间！");
			return;
		}
	}
	window.location = "getStudentBalance.do?searchname=" + realname
			+ "&searchphone=" + phone + "&minsdate=" + starttime + "&maxsdate="
			+ endtime + "&index=" + index + "&change_id=" + j;
}

// 教练搜索
function search() {
	var j = $("#change_id").val();
	var index = $("#index").val();
	var realname = $("#realname").val();
	var phone = $("#phone").val();
	var driveSchoolname = $("#driveschool").val();
	var carlicense = $("#carlicense").val();
	var checkstate = $("#checkstate").val();
	window.location = "getCoachByKeyword.do?searchname=" + realname
			+ "&searchphone=" + phone + "&driveSchoolname=" + driveSchoolname
			+ "&carlicense=" + carlicense + "&index=" + index + "&change_id="
			+ j + "&checkstate=" + checkstate;
}

function searchBalance(){
	var realname = $("#realname").val();
	var phone = $("#phone").val();
	var driveSchoolname = $("#driveschool").val();
	var carlicense = $("#carlicense").val();
	var checkstate = $("#checkstate").val();
	window.location = "getCoachBalance.do?searchname=" + realname
			+ "&searchphone=" + phone + "&driveSchoolname=" + driveSchoolname
			+ "&carlicense=" + carlicense + "&checkstate=" + checkstate;
}

function gobackcoach() {
	var pageSize = $("#pageSize").val();
	var pageIndex = $("#pageIndex").val();
	window.location.href = "getCoachlist.do?index=1&change_id=0&pageSize="
			+ pageSize + "&pageIndex=" + pageIndex;
}

function gobacksuser() {
	var pageSize = $("#pageSize").val();
	var pageIndex = $("#pageIndex").val();
	window.location.href = "getStudentlist.do?index=1&change_id=1&pageSize="
			+ pageSize + "&pageIndex=" + pageIndex;
}

function isNum() {
	var re = /^[0-9]+.?[0-9]*$/; // 判断字符串是否为数字 //判断正整数 /^[1-9]+[0-9]*]*$/
	var nubmer = $("#studentmoney").val();
	if (!re.test(nubmer)) {
		alert("金额请输入数字");
		$("#studentmoney").val("");
		return false;
	}
}
function phoneisNum() {
	var re = /^[0-9]+.?[0-9]*$/; // 判断字符串是否为数字 //判断正整数 /^[1-9]+[0-9]*]*$/
	var nubmer = $("#phone").val();
	if (!re.test(nubmer)) {
		alert("电话请输入数字");
		$("#phone").val("");
		return false;
	}
}
function coachPhoneidNum() {
	var re = /^[0-9]+.?[0-9]*$/; // 判断字符串是否为数字 //判断正整数 /^[1-9]+[0-9]*]*$/
	var nubmer = $("#phone").val();
	if (!re.test(nubmer)) {
		alert("电话请输入数字");
		$("#phone").val("");
		return false;
	}
}

// function editsinglecoach(){
// $("input[name='editcontent']").attr("readonly",false);
// $("input[name='editcontent']").css("background","#fff");
// $("#setedit").show();
// $("#unsetedit").show();
// }

function uneditsingle() {
	window.location.reload();
}

function doeditsingle() {
	var coachid = $("#coachid").val();
	var realname = $("#realname").val();
	var years = $("#years").val();
	var address = $("#address").val();
	var level = $("#level").val();
	var re = /^[0-9]+.?[0-9]*$/; // 判断字符串是否为数字 //判断正整数 /^[1-9]+[0-9]*]*$/
	if (!re.test(level)) {
		alert("教练等级请输入数字");
		return false;
	}
	if (!re.test(years)) {
		alert("教练教龄请输入数字");
		return false;
	}
	$.ajax({
		type : "POST",
		url : "editSingleCoach.do",
		data : {
			coachid : coachid,
			realname : realname,
			years : years,
			address : address,
			newlevel : level
		},
		success : function(data) {
			if (data == "error") {
				alert("编辑失败");
				return;
			}
			if (data == "success") {
				alert("修改成功！");
				window.location.reload();
			}
		}
	});

}

function editsinglecoach() {
	$("#singlecoachForm").attr("action", "editSingleCoach.do").submit();
}

function buttonclick(index) {
	$("#change").show();
	$("#change_sec").show();
	$("#change_last").show();
	if (index == 1) {
		// 图片放大
	}
	if (index == 2) {
		// 图片放大
	}
}

function changesize(index, url) {
	var picsrc = '';
	if (index == 1) {
		picsrc = document.getElementById("image01").src;
		if(picsrc!=''){
			var src = picsrc.replace(".jpg","origin.jpg");
			window.open(src);
		}
	}
	if (index == 2) {
		picsrc = document.getElementById("image02").src;
		if(picsrc!=''){
			var src = picsrc.replace(".jpg","origin.jpg");
			window.open(src);
		}
	}
	if (index == 3) {
		picsrc = document.getElementById("image03").src;
		if(picsrc!=''){
			var src = picsrc.replace(".jpg","origin.jpg");
			window.open(src);
		}
	}
	if (index == 4) {
		picsrc = document.getElementById("image04").src;
		if(picsrc!=''){
			var src = picsrc.replace(".jpg","origin.jpg");
			window.open(src);
		}
	}
	if (index == 5) {
		picsrc = document.getElementById("image05").src;
		if(picsrc!=''){
			var src = picsrc.replace(".jpg","origin.jpg");
			window.open(src);
		}
	}
	if (index == 6) {
		picsrc = document.getElementById("image06").src;
		if(picsrc!=''){
			var src = picsrc.replace(".jpg","origin.jpg");
			window.open(src);
		}
	}
//	if (url != '') {
//		$("#change").show();
//		$("#change_sec").show();
//		$("#change_last").show();
//		$("#showpic").attr("src", src);
//	}
}

function unshowpic() {
	$("#change").hide();
	$("#change_sec").hide();
	$("#change_last").hide();
}

function editsinglesuser() {
	
	var editphone=$("#editphone").val();
	var regu =/^(?:13\d|15[0123456789]|12[0123456789]|11[0123456789]|14[0123456789]|16[0123456789]|17[0123456789]|19[0123456789]|18[0123456789]|17[0123456789])-?\d{5}(\d{3}|\*{3})$/;
	if(!regu.test($("input[name=editphone]").val())){
		alert("用户联系电话格式不正确");
		return;
	}
	
	$("#singlestudentForm").attr("action", "editsinglestudent.do").submit();//studentdetail
}

function selectall() {
	var aa = $("#allcheck").val();
	if (aa == 0) {
		var a = document.getElementsByName("checkbox");
		for (var i = 0; i < a.length; i++) {
			a[i].checked = true;
		}
		$("#allcheck").val("1");
	}
	if (aa == 1) {
		var a = document.getElementsByName("checkbox");
		for (var i = 0; i < a.length; i++) {
			a[i].checked = false;
		}
		$("#allcheck").val("0");
	}
}

function selectallstudetail() {
	var aa = $("#allstudetailcheck").val();
	if (aa == 0) {
		var a = document.getElementsByName("C1");
		for (var i = 0; i < a.length; i++) {
			a[i].checked = true;
		}
		$("#allstudetailcheck").val("1");
	}
	if (aa == 1) {
		var a = document.getElementsByName("C1");
		for (var i = 0; i < a.length; i++) {
			a[i].checked = false;
		}
		$("#allstudetailcheck").val("0");
	}
}

function selectallcoachdetail() {
	var aa = $("#allcoachdetailcheck").val();
	if (aa == 0) {
		var a = document.getElementsByName("C1");
		for (var i = 0; i < a.length; i++) {
			a[i].checked = true;
		}
		$("#allcoachdetailcheck").val("1");
	}
	if (aa == 1) {
		var a = document.getElementsByName("C1");
		for (var i = 0; i < a.length; i++) {
			a[i].checked = false;
		}
		$("#allcoachdetailcheck").val("0");
	}
}

function allpass(){
	var a = document.getElementsByName("checkbox");
	var count=0;
	for (var i = 0; i < a.length; i++) {
		if(a[i].checked){
		count++;
		}
	}
	if(count==0){
		alert("请选择用户");
		return false;
	}else{
	$("#doallForm").attr("action","doallpass.do").submit();
	}
}


function allunpass(){
	var a = document.getElementsByName("checkbox");
	var count=0;
	for (var i = 0; i < a.length; i++) {
		if(a[i].checked){
		count++;
		}
	}
	if(count==0){
		alert("请选择用户");
		return false;
	}else{
	$("#doallForm").attr("action","doallunpass.do").submit();
	}
}


function allcancancel(){
	var a = document.getElementsByName("checkbox");
	var count=0;
	for (var i = 0; i < a.length; i++) {
		if(a[i].checked){
		count++;
		}
	}
	if(count==0){
		alert("请选择用户");
		return false;
	}else{
	$("#doallForm").attr("action","doallcancancel.do").submit();
	}
}

function allcannotcancel(){
	var a = document.getElementsByName("checkbox");
	var count=0;
	for (var i = 0; i < a.length; i++) {
		if(a[i].checked){
		count++;
		}
	}
	if(count==0){
		alert("请选择用户");
		return false;
	}else{
	$("#doallForm").attr("action","doallcannotcancel.do").submit();
	}
}

function allunfrozen(){
	var a = document.getElementsByName("checkbox");
	var count=0;
	for (var i = 0; i < a.length; i++) {
		if(a[i].checked){
		count++;
		}
	}
	if(count==0){
		alert("请选择用户");
		return false;
	}else{
	$("#doallForm").attr("action","doallunfrozen.do").submit();
	}
}

function allisquit(){
	var a = document.getElementsByName("checkbox");
	var count=0;
	for (var i = 0; i < a.length; i++) {
		if(a[i].checked){
		count++;
		}
	}
	if(count==0){
		alert("请选择用户");
		return false;
	}else{
	$("#doallForm").attr("action","doallisquit.do").submit();
	}
}

function showtimeinput(){
	 $("#frozenstart").show();
	 $("#frozenend").show();
}

function hiddentimeinput(){
	 $("#frozenstart").hide();
	 $("#frozenend").hide();
}


//显示添加用户(教练，学员)弹框
function showadduser() {
	$("#add").show();
	$("#add_sec").show();
	$("#add_last").show();
}

//点击隐藏添加用户(教练，学员)弹框
function unshowadduser() {
	$("#add").hide();
	$("#add_sec").hide();
	$("#add_last").hide();
}


//添加教练
function addcoach() {
//	var admin_login=$("#admin_login").val();
//	var admin_name=$("#admin_name").val();
//	var admin_password=$("#admin_password").val();
	var newcoachphone=$("#newcoachphone").val();
	var regu =/^(?:13\d|15[0123456789]|12[0123456789]|11[0123456789]|14[0123456789]|16[0123456789]|17[0123456789]|19[0123456789]|18[0123456789]|17[0123456789])-?\d{5}(\d{3}|\*{3})$/;
	if(!regu.test($("input[name=newcoachphone]").val())){
		alert("用户联系电话格式不正确");
		return;
	}
	if(newcoachphone==""){
		alert("手机号码不能为空");
		return;
	}else{
		$("#form_submit").submit();
	}
}


//添加学员
function addstudent() {
//	var admin_login=$("#admin_login").val();
//	var admin_name=$("#admin_name").val();
//	var admin_password=$("#admin_password").val();
	var newstudentphone=$("#newstudentphone").val();
	var regu =/^(?:13\d|15[0123456789]|12[0123456789]|11[0123456789]|14[0123456789]|16[0123456789]|17[0123456789]|19[0123456789]|18[0123456789]|17[0123456789])-?\d{5}(\d{3}|\*{3})$/;
	if(!regu.test($("input[name=newstudentphone]").val())){
		alert("用户联系电话格式不正确");
		return;
	}
	if(newstudentphone==""){
		alert("手机号码不能为空");
		return;
	}else{
		$("#form_submit").submit();
	}
}



/**
* 检验修改的账户名是否存在
*/
function checkCoachExistance() {
	var newcoachphone = $("#newcoachphone").val();
	if(newcoachphone==0){
		alert("手机号码不能为空");
	}else{
	$.ajax({
		type : "POST",
		url : "checkCoachExistance.do",
		data : {
			newcoachphone : newcoachphone
		},
		success : function(data) {
			if (data == "error") {
				alert("该账户已存在");
				return;
			}
		}
	});
	}
}


/**
* 检验修改的账户名是否存在
*/
function checkStudentExistance() {
	var newstudentphone = $("#newstudentphone").val();
	if(newstudentphone==0){
		alert("手机号码不能为空");
	}else{
	$.ajax({
		type : "POST",
		url : "checkStudentExistance.do",
		data : {
			newstudentphone : newstudentphone
		},
		success : function(data) {
			if (data == "error") {
				alert("该账户已存在");
				return;
			}
		}
	});
	}
}



