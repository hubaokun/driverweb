//删除通知信息
function delnotice(noticeid) {
	if (confirm("是否删除通知信息？")) {
		$.ajax({
			type : "POST",
			url : "delNotice.do",
			data : {
				noticeid : noticeid
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

// 跳转新发布系统通知消息
function jumpSetNoticeJsp() {
	window.location = "jump.do";
}

// 根据发布时间搜索系统通知
function searchnotice() {
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
//	window.location = "getNoticeByKeyword.do?&noticestarttime=" + starttime
//			+ "&noticeendtime=" + endtime+"&index="+index+"&change_id="+j;
	window.location = "getNoticeList.do?&noticestarttime=" + starttime
	+ "&noticeendtime=" + endtime+"&index="+index+"&change_id="+j;
}

// 返回上一页
function goback() {
	history.go(-1);
}

// 取消发布，刷新页面
function reload() {
	window.location.reload();
}

// 显示搜索栏
function showsearchs() {
	$("#bigsearch").show();
	$("#searchuserdiv").show();
}

// 不显示搜索栏
function unshowsearchs() {
	$("#bigsearch").hide();
	$("#searchuserdiv").hide();
}

// 不显示群发单发
function unshowsettype() {
	$("#settypediv").hide();
	$("#bigsearch").show();
	$("#searchuserdiv").show();
}

// 显示群发单发
function showsettype() {
	var settype = document.getElementsByName("settype");
	for (var int = 0; int < settype.length; int++) {
		if (settype[int].checked) {
			settype = settype[int].value;
		}
	}
	if (settype == 1) {
		$("#bigsearch").show();
		$("#searchuserdiv").show();
		$("#settypediv").show();
	} else if (settype == 0) {
		$("#settypediv").show();
		$("#searchuserdiv").hide();
		$("#bigsearch").hide();
	}
}

// 根据关键字、类型搜索
function searchuser() {
	// var searchuserdiv="block";
	var usertype = document.getElementsByName("usertype");
	for (var int = 0; int < usertype.length; int++) {
		if (usertype[int].checked) {
			usertype = usertype[int].value;
		}
	}
	var searchname = $("#searchname").val();
	$.ajax({
		type : "POST",
		url : "noticeSearchUser.do",
		data : {
			usertype : usertype,
			searchname : searchname
		},
			success : function(data) {
				var jsondata = eval("("+data+")");
				var state=jsondata.state;
				if(state==200){
					if(jsondata.type==1){
						$("#searchuserdiv").empty();
						var html = "";
						var list=jsondata.list;
						for (var j = 0; j < list.length; j++) {
							html += "<input style='margin-top:5px;margin-left:10px;font-size:5px;'  type='radio' name='singleuser' value='"+list[j].coachid+"'>"+list[j].realname;
						}
						$("#searchuserdiv").append(html);
				}else{
					$("#searchuserdiv").empty();
					var html = "";
					var list=jsondata.list;
					for (var j = 0; j < list.length; j++) {
						html += "<input style='margin-top:5px;margin-left:10px;font-size:5px;'  type='radio' name='singleuser' value='"+list[j].studentid+"'>"+list[j].realname;
					}
					$("#searchuserdiv").append(html);
				}
					
				}
		}
	});

}

// 发布通知
function setnotice() {
	//群发还是单发
	var settype = $("input[name='settype']:checked").val();
	//发送的四种方向
	var usertype = $("input[name='usertype']:checked").val();
	//单选框的选择的id
	var singleuser = $("input[name='singleuser']:checked").val();
	//通知类型
	var category = $("#category").val();
	//通知内容
	var contents = $("#contents").val();
	if (confirm("是否发送通知")) {
		$.ajax({
			type : "POST",
			url : "setMessage.do",
			data : {
				settype : settype,
				usertype:usertype,
				singleuserid:singleuser,
				category:category,
				contents:contents
			},
			success : function(data) {
				if (data == "error") {
					alert("请您不要漏填发布信息");
					window.location.reload();
				}
				if (data == "success") {
					alert("发送成功！");
					window.location.reload();
				}
			}
		});
	}
	

}
