function getSelfTop(){
	var h= document.documentElement.clientHeight;
	var height2 = (h-700)/2;
	var w= document.documentElement.clientWidth;
	var width=(w-600)/2;
	var width2=(w-500)/2;
	$("#mask_last").css("margin-left",width);
	$("#mask_last").css("top","10%");
	$("#edit_last").css("margin-left",width2);
	$("#edit_last").css("top",height2);
};

//添加驾校
function givingcoupon(id) {
	$("#couponid").val(id);
	$("#mask").show();
	$("#mask_sec").show();
	$("#mask_last").show();
}

function unshowaddschool() {
	$("#mask").hide();
	$("#mask_sec").hide();
	$("#mask_last").hide();
}


function turnaddcoupon(){
	window.location.href="./turnaddcoupon.do";
}

function delcoupon(id){
	if(confirm("确认删除该优惠券?")){
	$.ajax({
		url:"./delcoupon.do",
		type:"post",
		data:{"couponid":id,},
		success:function(msg){
			alert("删除成功!");
			window.location.reload();
		}
	});
	}
}

function cancelcoupon(id){
	if(confirm("确认作废该优惠券?")){
	$.ajax({
		url:"./cancelcoupon.do",
		type:"post",
		data:{"recordid":id,},
		success:function(msg){
			alert("作废成功!");
			window.location.reload();
		}
	});
	}
}

//限制用户输入数字
function IsNum(e) {
    var k = window.event ? e.keyCode : e.which;
    if (((k >= 48) && (k <= 57)) || k == 8 || k == 0) {
    } else {
        if (window.event) {
            window.event.returnValue = false;
        }
        else {
            e.preventDefault(); // for firefox
        }
    }
} 

function changeserachdiv(){
	var givingtype = $("input[name='givingtype']:checked").val();
	if(givingtype==1){
		$("#bigsearch").remove();
		$("#bigsearchcoach").remove();
		$("#textareaid").css("margin-top","10px");
		$("#somestudent").hide();
		$("#exslfile").css("display","none");
	}else if(givingtype==3){
		$("#bigsearch").remove();
		$("#bigsearchcoach").remove();
		$("#textareaid").css("margin-top","30px");
		$("#somestudent").show();
		$("#exslfile").css("display","block");

	}
	else if(givingtype==4){
		var html = "";
		html += '<div id="bigsearchcoach" style="width: 400px;margin-top:20px; margin-left: 50px;height: 60px;  display: block;">';
		html +=	'<div class="serchcontentdiv" style="width: 304px;margin-top: 0px;float: left;">';
		html += '<input class="searchdiv" value="教练名称" type="text" style="width: 100px;text-align: center;font-size: 12px;" readonly="readonly">';
		html += '<input id="searchcoachname"  onkeyup="searchCoach();" autocomplete="off" class="searchdiv" type="text" style="width: 202px;"></div>';
		html += '<div class="binding_detail" id="gartenDetail" ></div>';
		html +=	'<input type="hidden" name="searchcoachid" id="searchcoachid"></div>';
		$("#givingafter").before(html);
		$("#somestudent").hide();
		$("#exslfile").css("display","none");
		$("#bigsearch").remove();

	}
	else{
		var html = "";
		html += '<div id="bigsearch" style="width: 400px;margin-top:20px; margin-left: 50px;height: 60px;  display: block;">';
		html +=	'<div class="serchcontentdiv" style="width: 304px;margin-top: 0px;float: left;">';
		html += '<input class="searchdiv" value="学员名称或者手机" type="text" style="width: 100px;text-align: center;font-size: 12px;" readonly="readonly">';
		html += '<input id="searchname"  onkeyup="searchSuser();" autocomplete="off" class="searchdiv" type="text" style="width: 202px;"></div>';
		html += '<div class="binding_detail" id="gartenDetail" ></div>';
		html +=	'<input type="hidden" name="searchsuserid" id="searchsuserid"></div>';
		$("#givingafter").before(html);
		$("#somestudent").hide();
		$("#exslfile").css("display","none");
		$("#bigsearchcoach").remove();
	}
}

function addcoupongiving(){
		var re = /^[0-9]+.?[0-9]*$/; // 判断字符串是否为数字 //判断正整数 /^[1-9]+[0-9]*]*$/
		var nubmer = $("#pushnum").val();
		if (nubmer==''||!re.test(nubmer)||nubmer==0) {
			alert("发送数量请输入不为0的数字");
			$("#pushnum").val("");
			return false;
		}else{
		$("#alertform").attr("action","addcoupongiving.do").submit();
	}
}



function searchSuser(){
	var name = $("#searchname").val();
	if(name == ''){
		return;
	}
	$.ajax({
			url: "searchSuser.do",
			data:{cuserkeyword: name},
			type: "POST",
			success: function(msg){
				var obj = eval("("+msg+")");
				var suserlist = obj.suserlist;
				$("#gartenDetail").empty();
				$("#gartenDetail").show();
				for(var i = 0; i < suserlist.length; i++){
					var id = suserlist[i].studentid;
					var name = suserlist[i].realname;
					var phone = suserlist[i].phone;
					if(name==null){
						name="未设置";
						$("#gartenDetail").append(
								'<div class="binding_detail_item" onclick="searchName('+id+', \''+phone+'\')">'
									+name+":"+phone
								 +'</div>'	
							);
					}else{
						$("#gartenDetail").append(
								'<div class="binding_detail_item" onclick="searchName('+id+', \''+phone+'\')">'
									+name+":"+phone
								 +'</div>'	
							);
					}
				}
			}
		}); 
}


function searchCoach(){
	var name = $("#searchcoachname").val();
	if(name == ''){
		return;
	}
	$.ajax({
			url: "searchCuser.do",
			data:{cuserkeyword: name},
			type: "POST",
			success: function(msg){
				var obj = eval("("+msg+")");
				var cuserlist = obj.cuserlist;
				$("#gartenDetail").empty();
				$("#gartenDetail").show();
				for(var i = 0; i < cuserlist.length; i++){
					var id = cuserlist[i].coachid;
					var name = cuserlist[i].realname;
					var phone = cuserlist[i].phone;
					$("#gartenDetail").append(
						'<div class="binding_detail_item" onclick="searchcoachName('+id+', \''+name+'\')">'
							+name+":"+phone+'</div>'	
					);
				}
			}
		}); 
}


function searchName(id,name){
	$("#searchsuserid").val(id);
	$("#searchname").val(name);
	$("#gartenDetail").empty();
	$("#gartenDetail").hide();
}

function searchcoachName(id,name){
	$("#searchcoachid").val(id);
	$("#searchcoachname").val(name);
	$("#gartenDetail").empty();
	$("#gartenDetail").hide();
}


function showsearchowner(){
	var addownertype = $("#addownertype").val();
	$("#addownerid").remove();
	$("#bigsearch").remove();
	if(addownertype==2){
		var html = "";
		html +='<div id="bigsearch" style="width: 100%; height: 75px; border-bottom: 1px solid #eaeff2;">';
		html +='<div style="float:left; width: 143px; height: 100%; line-height: 75px; border-right: 1px solid #eaeff2;text-align: right;">教练名称或手机<span style="color:#f83a22; margin-right:16px; margin-left: 8px;">*</span></div>';
		html +='<div style="height: 100%; line-height: 75px; float:left; margin-left: 20px;">';
		html +='<input value="" name="owenersearch" id="searchname" onkeyup="searchCuser();" autocomplete="off"  required="required" style="width: 800px; height: 33px; padding-left:5px; border: 1px solid #eaeff2; margin-top: 20px;">';
		html +='</div><div class="binding_detail"  style="  left: 164px;top:-20px;clear:both;" id="gartenDetail" >';
		html +='</div><input type="hidden" id="addownerid" name="addownerid" required="required">';
		$("#searchbefore").before(html);
	}else if(addownertype==1){
		var html = "";
		html +='<div  id="bigsearch" style="width: 100%; height: 75px; border-bottom: 1px solid #eaeff2;">';
		html +='<div style="float:left; width: 143px; height: 100%; line-height: 75px; border-right: 1px solid #eaeff2;text-align: right;">驾校名称<span style="color:#f83a22; margin-right:16px; margin-left: 8px;">*</span></div>';
		html +='<div style="height: 100%; line-height: 75px; float:left; margin-left: 20px;">';
		html +='<input value="" name="owenersearch" id="searchname" onkeyup="searchDriveSchool();" autocomplete="off"  required="required" style="width: 800px; height: 33px; padding-left:5px; border: 1px solid #eaeff2; margin-top: 20px;">';
		html +='</div><div class="binding_detail" style="  left: 164px;top:-20px;clear:both;" id="gartenDetail" >';
		html +='</div><input type="hidden" id="addownerid" name="addownerid" required="required">';
		$("#searchbefore").before(html);
	}
}

function searchDriveSchool(){
	var name = $("#searchname").val();
	if(name == ''){
		return;
	}
	$.ajax({
			url: "searchDriveSchool.do",
			data:{schoolkeyword: name},
			type: "POST",
			success: function(msg){
				var obj = eval("("+msg+")");
				var schoollist = obj.schoollist;
				$("#gartenDetail").empty();
				$("#gartenDetail").show();
				for(var i = 0; i < schoollist.length; i++){
					var id = schoollist[i].schoolid;
					var name = schoollist[i].name;
					$("#gartenDetail").append(
						'<div class="binding_detail_item" onclick="searchOwner('+id+', \''+name+'\')">'
							+name+'</div>'	
					);
				}
			}
		}); 
}

function searchCuser(){
	var name = $("#searchname").val();
	if(name == ''){
		return;
	}
	$.ajax({
			url: "searchCuser.do",
			data:{cuserkeyword: name},
			type: "POST",
			success: function(msg){
				var obj = eval("("+msg+")");
				var cuserlist = obj.cuserlist;
				$("#gartenDetail").empty();
				$("#gartenDetail").show();
				for(var i = 0; i < cuserlist.length; i++){
					var id = cuserlist[i].coachid;
					var name = cuserlist[i].realname;
					var phone = cuserlist[i].phone;
					$("#gartenDetail").append(
						'<div class="binding_detail_item" onclick="searchOwner('+id+', \''+name+'\')">'
							+name+":"+phone
						 +'</div>'	
					);
				}
			}
		}); 
}

function searchOwner(id,name){
	$("#addownerid").val(id);
	$("#searchname").val(name);
	$("#gartenDetail").empty();
	$("#gartenDetail").hide();
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


