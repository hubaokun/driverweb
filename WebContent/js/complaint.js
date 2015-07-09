var polltime;
function showNewOrder(){
    $(function(){
    	$.ajax({
    		type:"POST",
    		url:"complaint",
    		//提交的数据
    		data:{"polltime":polltime},
    		datatype: "json",
    		success: function(msg) {
    			var data = eval("(" + msg + ")");
    			polltime = data.polltime;
    			if (data.pollflag == 1) {
    				document.getElementById("audio").play();
    			}
    				
    		}
    	});
    	
    });
}
function checkComplaintPass(complaintid,pageIndex,index,change_id) {
	if(confirm("确认投诉解决？")){
		window.location.href="updateComplaintState.do?complaintid="+complaintid+"&index="+index+"&pageIndex="+pageIndex+"&change_id="+change_id;
	}
}

function checkComplaint(complaintid,index,change_id) {
	if(confirm("确认投诉解决？")){
		window.location.href="updateComplaint.do?complaintid="+complaintid+"&index="+index+"&change_id="+change_id;
	}
}

function searchComplaint(){
	var complaintid=$("#complaintid").val();
	var studentphone=$("#studentphone").val();
	var coachphone=$("#coachphone").val();
	var type=$("#type").val();
	var minsdate=$("#minsdate").val();
	var maxsdate=$("#maxsdate").val();
	if(minsdate!=''&&maxsdate!=''){
		if(minsdate>maxsdate){
			alert("结束时间必须在开始时间之后！");
			return;
		}
	}
	var state = $("#state").val();
	var index=$("#index").val();
	var change_id = $("#change_id").val();
	window.location.href="getComplaintBySearch.do?complaintid="+complaintid+"&studentphone="+studentphone+"&coachphone="+coachphone+"&type="+type+"&minsdate="+minsdate+"&maxsdate="+maxsdate+"&state="+state+"&index="+index+"&change_id="+change_id;
}

//添加投诉处理弹框
function alertBox() {
	$("#mask").show();
	$("#mask_sec").show();
	$("#mask_last").show();
}

function closeAlertBox() {
	$("#mask").hide();
	$("#mask_sec").hide();
	$("#mask_last").hide();
}

function addComplaintBook(){
	var content = $("#book_content").val();
	var addtime = $("#newaddtime").val();
	if(addtime==''){
		alert("请设置投诉处理时间！");
		return;
	}
	if(content==''){
		alert("请输入投诉处理内容！");
		return;
	}
	$("#labelForm").attr("action", "addComplaintBookInfo.do").submit();
}
