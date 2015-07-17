//添加车型
function addteachcarmodel(){
		var modelname =$("#modelname").val();
		if (confirm("是否添加车型？")) {
			$.ajax({
				type : "POST",
				url : "addTeachcarModel.do",
				data : {
					modelname : modelname
				},
				success : function(data) {
					if (data == "error") {
						alert("已有车型，请勿重复添加");
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
//删除车型
function delteachcarmodel(modelname){
	if (confirm("是否删除车型？")) {
		$.ajax({
			type : "POST",
			url : "delTeachcarModel.do",
			data : {
				modelname : modelname
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
//修改车型
function editteachcarmodel(){
	var oldmodelname=$("#oldmodelname").val();
	var editmodelname=$("#editmodelname").val();
	if (confirm("是否修改车型？")) {
		$.ajax({
			type : "POST",
			url : "editTeachcarModel.do",
			data : {
				editmodelname : editmodelname,
				oldmodelname:oldmodelname
			},
			success : function(data) {
				if (data == "error") {
					alert("修改失败");
					return;
				}
				if (data == "error1") {
					alert("该车型已存在");
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

//添加准教车型
function addmodel(){
		var modelname =$("#modelname").val();
		var searchname = $("#searchname").val();
		if (confirm("是否添加准教车型？")) {
			$.ajax({
				type : "POST",
				url : "addModel.do",
				data : {
					modelname : modelname,
					searchname:searchname
				},
				success : function(data) {
					if (data == "error") {
						alert("已有车型，请勿重复添加");
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

//删除准教车型
function delmodel(modelname){
	if (confirm("是否删除准教车型？")) {
		$.ajax({
			type : "POST",
			url : "delModel.do",
			data : {
				modelname : modelname
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

//修改准教车型
function editmodel(){
	var oldmodelname=$("#oldmodelname").val();
	var editmodelname=$("#editmodelname").val();
	var searchname = $("#editsearchname").val();
	if(editmodelname==''&&searchname==''){
		alert("请输入车型！");
		return;
	}
	if (confirm("是否修改准教车型？")) {
		$.ajax({
			type : "POST",
			url : "editModel.do",
			data : {
				editmodelname : editmodelname,
				oldmodelname:oldmodelname,
				searchname:searchname
			},
			success : function(data) {
				if (data == "error") {
					alert("修改失败");
					return;
				}
				if (data == "error1") {
					alert("该车型已存在");
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


//添加教练等级
function addlevel(){
		var levelname =$("#levelname").val();
		if (confirm("是否添加教练等级？")) {
			$.ajax({
				type : "POST",
				url : "addLevel.do",
				data : {
					levelname : levelname
				},
				success : function(data) {
					if (data == "error") {
						alert("已有教练等级，请勿重复添加");
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

//修改教练等级
function editlevel(){
	var oldlevelname=$("#oldlevelname").val();
	var newlevelname=$("#newlevelname").val();
	if (confirm("是否修改教练等级？")) {
		$.ajax({
			type : "POST",
			url : "editLevel.do",
			data : {
				oldlevelname : oldlevelname,
				newlevelname:newlevelname
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

//删除教练等级
function dellevel(levelname){
	if (confirm("是否删除教练等级？")) {
		$.ajax({
			type : "POST",
			url : "delLevel.do",
			data : {
				levelname : levelname
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


//添加投诉原因
function addcomplaint(){
	var contents =$("#contents").val();
	var type =$("#type").val();
	if(type==1){
	if (confirm("是否添加投诉原因？")) {
		$.ajax({
			type : "POST",
			url : "addComplaintSetInfo.do",
			data : {
				content : contents,
				type : type
			},
			success : function(data) {
				if (data == "error") {
					alert("请按要求输入");
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
	}else if(type==2){
		if (confirm("是否添加投诉原因？")) {
			$.ajax({
				type : "POST",
				url : "addComplaintSetInfo.do",
				data : {
					content : contents,
					type : type
				},
				success : function(data) {
					if (data == "error") {
						alert("请按要求输入");
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
	}else{
		alert("使用者输入不规范");
	}
}


//分类搜索投诉原因
function search(index){
	var i=$("#index").val();
	var j=$("#change_id").val();
	if(index==null){
		window.location="getComplaintlist.do?index="+i+"&change_id="+j;
	}else{
		window.location="getComplaintlist.do?searchtype="+index+"&index="+i+"&change_id="+j;
	}
}

//删除投诉原因
function delcomplaint(setid){
	if (confirm("是否删除投诉原因？")) {
		$.ajax({
			type : "POST",
			url : "delComplaintSetInfo.do",
			data : {
				setid : setid
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





//修改投诉原因内容
function editcomplaint(){
	var setid=$("#setid").val();
	var newcontent=$("#newcontent").val();
	if (confirm("是否修改投诉内容？")) {
		$.ajax({
			type : "POST",
			url : "editComplaintSet.do",
			data : {
				setid : setid,
				newcontent:newcontent
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

//添加教学科目
function addsubject(){
	var addsubjectname=$("#addsubjectname").val();
		$.ajax({
			type : "POST",
			url : "addSubject.do",
			data : {
				addsubjectname:addsubjectname
			},
			success : function(data) {
				if (data == "error") {
					alert("已有教学科目，请勿重复添加");
					return; 
				}
				if (data == "success") {
					$("#mask").css("display", "none");
					window.location.reload();
				}
			}
		});
}


//删除教学科目
function delsubject(subjectid){
	if (confirm("是否删除教学科目？")) {
		$.ajax({
			type : "POST",
			url : "delSubject.do",
			data : {
				subjectid : subjectid
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



//修改教学科目内容
function editsubject(){
	var oldsubjectname=$("#oldsubjectname").val();
	var editsubjectname=$("#editsubjectname").val();
	if (confirm("是否修改教学科目？")) {
		$.ajax({
			type : "POST",
			url : "editSubject.do",
			data : {
				oldsubjectname : oldsubjectname,
				editsubjectname:editsubjectname
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
					alert("修改后的名称已存在");
					return;
				}
			}
		});
	}
}
//点击添加假日日期
function clickadd(){
	var clickholiday=$("#J_input").val();
	var newdate=$("#J_input").val();
	var newholidays=$("#newholidays").text();
	if(newdate==0){
		alert("请选择日期");
		return false;
	}
	if(newholidays==0){
		$("#newholidays").text(newdate);
	}else{
		$.ajax({
			type : "POST",
			url : "getHolidaysday.do",
			data : {
				newholidays : newholidays,
				clickholiday:clickholiday
			},
			success : function(data) {
				if (data == "error") {
					alert("添加日期已存在，请检查");
					return;
				}
				if (data == "success") {
					$("#newholidays").text(newholidays+","+newdate);

				}
			}
		});
	}
}

//保存假日日期，覆盖原数据
function updateholidays(dataid){
	var newholidays=$("#newholidays").text();
	if (confirm("是否保存节假日设定？")) {
		$.ajax({
			type : "POST",
			url : "updateHolidays.do",
			data : {
				newholidays : newholidays,
				dataid:dataid
			},
			success : function(data) {
				if (data == "error") {
					alert("修改失败,内容不能为空");
					return;
				}
				if (data == "success") {
					window.location.reload();
				}
			}
		});
	}
}


//修改系统设置值
function edittime_cancel(){
	var dataid=$("#systemid").val();
	var editvalue=$("#editvalue").val();
	var colname=$("#colname").val();
	if (confirm("是否修改？")) {
		$.ajax({
			type : "POST",
			url : "editValue.do",
			data : {
				dataid : dataid,
				editvalue : editvalue,
				colname: colname
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
					alert("修改后的名称已存在");
					return;
				}
			}
		});
	}
}

//修改系统设置值
function edittime_cancel_num(){
	var dataid=$("#numsystemid").val();
	var editvalue=$("#numeditvalue").val();
	var colname=$("#numcolname").val();
	if (confirm("是否修改？")) {
		$.ajax({
			type : "POST",
			url : "editValue.do",
			data : {
				dataid : dataid,
				editvalue : editvalue,
				colname: colname
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
					alert("修改后的名称已存在");
					return;
				}
			}
		});
	}
}

//修改是否可以使用不同小巴券
function updatexiaoba(){
	var dataid=$("#systemxiaobaid").val();
	var editvalue=$("#diffxiaoba").val();
	alert(editvalue);
	var colname=$("#xiaobaname").val();
	if (confirm("是否修改？")) {
		$.ajax({
			type : "POST",
			url : "editValue.do",
			data : {
				dataid : dataid,
				editvalue : editvalue,
				colname: colname
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
					alert("修改后的名称已存在");
					return;
				}
			}
		});
	}
}


//修改是否可启用广告图片
function updateadv(){
	var dataid=$("#advid").val();
	var editvalue=$("#diffadv").val();
//	alert(editvalue);
	var colname=$("#advflag").val();
	if (confirm("是否修改？")) {
		$.ajax({
			type : "POST",
			url : "editValue.do",
			data : {
				dataid : dataid,
				editvalue : editvalue,
				colname: colname
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
					alert("修改后的名称已存在");
					return;
				}
			}
		});
	}
}





//添加教学科目
function showaddsubject(subjectname) {
	$("#mask").show();
	$("#mask_sec").show();
	$("#mask_last").show();

}

function unshowaddsubject() {
	$("#mask").hide();
	$("#mask_sec").hide();
	$("#mask_last").hide();
}


//修改教学科目
function showeditsubject(subjectname) {
	$("#oldsubjectname").val(subjectname);
	$("#edit").show();
	$("#edit_sec").show();
	$("#edit_last").show();

}

function unshoweditsubject() {
	$("#edit").hide();
	$("#edit_sec").hide();
	$("#edit_last").hide();
}


//添加教练等级
function showaddlevel() {
	$("#mask").show();
	$("#mask_sec").show();
	$("#mask_last").show();

}

function unshowaddlevel() {
	$("#mask").hide();
	$("#mask_sec").hide();
	$("#mask_last").hide();
}


//添加准教车型
function showaddmodel() {
	$("#mask").show();
	$("#mask_sec").show();
	$("#mask_last").show();

}

function unshowaddmodel() {
	$("#mask").hide();
	$("#mask_sec").hide();
	$("#mask_last").hide();
}



//修改准教车型
function showeditmodel(modelname,searchname) {
	$("#oldmodelname").val(modelname);
	$("#editmodelname").val(modelname);
	$("#editsearchname").val(searchname);
	$("#edit").show();
	$("#edit_sec").show();
	$("#edit_last").show();

}

function unshoweditmodel() {
	$("#edit").hide();
	$("#edit_sec").hide();
	$("#edit_last").hide();
}


//修改等级车型
function showeditlevel(levelname) {
	$("#oldlevelname").val(levelname);
	$("#edit").show();
	$("#edit_sec").show();
	$("#edit_last").show();

}

function unshoweditlevel() {
	$("#edit").hide();
	$("#edit_sec").hide();
	$("#edit_last").hide();
}

//修改投诉原因内容
function showeditset(contents,setid) {
	$("#setid").val(setid);
	$("#contentss").val(contents);
	$("#edit").show();
	$("#edit_sec").show();
	$("#edit_last").show();

}

function unshoweditset() {
	$("#edit").hide();
	$("#edit_sec").hide();
	$("#edit_last").hide();
}

//设置订单取消时间
function showedittimecancel(dataid,oldvalue,colname){
	if(!isNaN(oldvalue)){
		$("#numeditvalue").val('');
		$("#numsystemid").val(dataid);
		$("#numoldvalue").val('当前值:  '+oldvalue);
		$("#numcolname").val(colname);
		$("#level").show();
		$("#level_sec").show();
		$("#level_last").show();
	}else{
		$("#editvalue").val('');
		$("#systemid").val(dataid);
		$("#oldvalue").val('当前值:  '+oldvalue);
		$("#colname").val(colname);
		$("#edit").show();
		$("#edit_sec").show();
		$("#edit_last").show();
	}
}

// 设置订单是否可以使用不同小巴券
function showdiffxiaoba(dataid,oldvalue,colname){
	$("#systemxiaobaid").val(dataid);
	if(oldvalue==0){
		$("#xiaobaoldvalue").val('当前值: 不可以 ');
	}else{
		$("#xiaobaoldvalue").val('当前值: 可以 ');
	}
	$("#xiaobaname").val(colname);
	$("#mask").show();
	$("#mask_sec").show();
	$("#mask_last").show();
}


//设置是否启用广告
function showdiffadv(dataid,oldvalue,colname){
	$("#advid").val(dataid);
	if(oldvalue==0){
		$("#advoldvalue").val('当前值: 不启用 ');
	}else{
		$("#advoldvalue").val('当前值: 启用 ');
	}
	$("#advflag").val(colname);
	$("#maskadv").show();
	$("#mask_adv").show();
	$("#adv_last").show();
}



function unshowisnum() {
	$("#level").hide();
	$("#level_sec").hide();
	$("#level_last").hide();
}

function unshowedittimecancel() {
	$("#edit").hide();
	$("#edit_sec").hide();
	$("#edit_last").hide();
}

function unupdatexiaoba() {
	$("#mask").hide();
	$("#mask_sec").hide();
	$("#mask_last").hide();
}


function unupdateadv() {
	$("#maskadv").hide();
	$("#mask_adv").hide();
	$("#adv_last").hide();
}



function timeIsNum(){
	var re = /^[0-9]+.?[0-9]*$/;   //判断字符串是否为数字     //判断正整数 /^[1-9]+[0-9]*]*$/  
    var nubmer = $("#edittime").val();
     if (!re.test(nubmer))
    {
        alert("请输入数字");
        $("#edittime").val("");
        return false;
     }
}

function typeIsNum(){
	var re = /^[0-9]+.?[0-9]*$/;   //判断字符串是否为数字     //判断正整数 /^[1-9]+[0-9]*]*$/  
    var nubmer = $("#type").val();
     if (!re.test(nubmer))
    {
        alert("请按规定输入1或2");
        $("#type").val("");
        return false;
     }
}

