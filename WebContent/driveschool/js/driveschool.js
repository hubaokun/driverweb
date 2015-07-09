function getSelfTop(){
	var h= document.documentElement.clientHeight;
	var height = (h-500)/2;
	var height2 = (h-700)/2;
	var w= document.documentElement.clientWidth;
	var width=(w-600)/2;
	var width2=(w-500)/2;
	$("#mask_last").css("margin-left",width);
	$("#mask_last").css("top",height);
	$("#edit_last").css("margin-left",width2);
	$("#edit_last").css("top",height2);
};

//添加驾校
function showaddschool() {
	$("#mask").show();
	$("#mask_sec").show();
	$("#mask_last").show();

}

function unshowaddschool() {
	$("#mask").hide();
	$("#mask_sec").hide();
	$("#mask_last").hide();
}

//修改驾校信息
function showeditschool(schoolname,schoolphone,schoolcontact,alipay,order_pull) {
	$("#oldschoolname").val("原驾校名："+schoolname);
	$("#oldschoolphone").val("原联系电话："+schoolphone);
	$("#oldschoolcontact").val("原联系人姓名："+schoolcontact);
	$("#oldalipay_account").val("原支付宝账号："+alipay);
	$("#oldorder_pull").val("原订单抽成： "+order_pull+"%");
	$("#name").val(schoolname);
	$("#edit").show();
	$("#edit_sec").show();
	$("#edit_last").show();

}

function unshoweditschool() {
	$("#edit").hide();
	$("#edit_sec").hide();
	$("#edit_last").hide();
}

//添加驾校
function addshcool(){
	var re = /^(?:0|[1-9][0-9]?|100)$/;   //判断字符串是否为数字     //判断正整数 /^[1-9]+[0-9]*]*$/  
	  var nubmer = $("#order_pull").val();
	   if (!re.test(nubmer))
	  {
	      alert("抽成只能设置成0~100的整数");
	      $("#order_pull").val("");
	      return false;
	   }
	var schoolname=$("#schoolname").val();
	var schoolphone=$("#schoolphone").val();
	var regu =/^(?:13\d|15[0123456789]|12[0123456789]|11[0123456789]|14[0123456789]|16[0123456789]|17[0123456789]|19[0123456789]|18[0123456789]|17[0123456789])-?\d{5}(\d{3}|\*{3})$/;
	if(!regu.test(schoolphone)){
		alert("用户联系电话格式不正确");
		return;
	}
	var schoolcontact=$("#schoolcontact").val();
	var alipay_account=$("#alipay_account").val();
	var order_pull=$("#order_pull").val();
	if (confirm("是否添加驾校信息？")) {
		$.ajax({
			type : "POST",
			url : "addSchool.do",
			data : {
				schoolname:schoolname,
				schoolphone:schoolphone,
				schoolcontact:schoolcontact,
				alipay_account:alipay_account,
				order_pull:order_pull
			},
			success : function(data) {
				if (data == "error") {
					alert("已有驾校信息，请勿重复添加");
					return; 
				}
				if (data == "error1") {
					alert("添加信息不能为空");
					return; 
				}
				if (data == "success") {
					alert("添加成功！");
					$("#mask").css("display", "none");
					window.location.reload();
				}
			}
		});
	}

}


//删除驾校信息
function delschool(schoolname){
	if (confirm("是否删除驾校信息？")) {
		$.ajax({
			type : "POST",
			url : "delSchool.do",
			data : {
				schoolname : schoolname
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


//修改驾校信息
function editschool(){
	var oldschoolname=$("#name").val();
	var editschoolname=$("#editschoolname").val();
	var editschoolphone=$("#editschoolphone").val();
	var editschoolcontact=$("#editschoolcontact").val();
	var editalipay_account=$("#editalipay_account").val();
	var editorder_pull=$("#editorder_pull").val();
	if(editorder_pull!=0){
		var re =/^(?:0|[1-9][0-9]?|100)$/;   //判断字符串是否为数字     //判断正整数 /^[1-9]+[0-9]*]*$/  
		   if (!re.test(editorder_pull))
		  {
		      alert("抽成只能设置成0~100的整数");
		      $("#editorder_pull").val("");
		      return false;
		   }
	}
	if (confirm("是否修改驾校信息？")) {
		$.ajax({
			type : "POST",
			url : "editSchool.do",
			data : {
				oldschoolname : oldschoolname,
				editschoolname:editschoolname,
				editschoolphone:editschoolphone,
				editschoolcontact:editschoolcontact,
				editalipay_account:editalipay_account,
				editorder_pull:editorder_pull
			},
			success : function(data) {
				if (data == "error") {
					alert("修改失败");
					return;
				}
				if (data == "success") {
					window.location.reload();
				}
				if (data == "error1") {
					alert("修改后的名称已存在");
					return;
				}
				if (data == "error2") {
					alert("请输入修改内容，如不需修改则点击取消");
					return;
				}
			}
		});
	}
}







function schoolphoneIsNum(){
	var re = /^[0-9]+.?[0-9]*$/;   //判断字符串是否为数字     //判断正整数 /^[1-9]+[0-9]*]*$/  
    var nubmer = $("#schoolphone").val();
     if (!re.test(nubmer))
    {
        alert("请按规定输入电话号码");
        $("#schoolphone").val("");
        return false;
     }
}

function editschoolphoneIsNum(){
	var re = /^[0-9]+.?[0-9]*$/;   //判断字符串是否为数字     //判断正整数 /^[1-9]+[0-9]*]*$/  
    var nubmer = $("#editschoolphone").val();
     if (!re.test(nubmer))
    {
        alert("请按规定输入电话号码");
        $("#editschoolphone").val("");
        return false;
     }
}

