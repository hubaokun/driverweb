//搜索管理员
function searchadmins() {
	var searchlogin = $("#login_account").val();
	var searchtelphone = $("#telphone").val();
	var index = $("#index").val();
	var j = $("#change_id").val();
	window.location = "getAdminInfolistByKeyword.do?searchlogin=" + searchlogin
			+ "&searchtelphone=" + searchtelphone + "&index=" + index+"&change_id="+j;
}

// 删除管理员
function deladmin(adminid) {
	if (confirm("是否删除该管理员？")) {
		$.ajax({
			type : "POST",
			url : "delAdmin.do",
			data : {
				adminid : adminid,
			},
			success : function(data) {
				if (data == "error") {
					alert("删除失败！");
					return;
				}
				if (data == "error1") {
					alert("您无法删除自己！");
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

// 显示编辑弹框
function showeditadmin(adminid, login, realname, phone) {
	$("#admin_idedit").val(adminid);
	$("#oldlogin").val("原账号：" + login);
	$("#oldrealname").val("原姓名：" + realname);
	$("#oldphone").val("原联系电话：" + phone);
	$("#edit").show();
	$("#edit_sec").show();
	$("#edit_last").show();
}

function unshoweditadmin() {
	$("#edit").hide();
	$("#edit_sec").hide();
	$("#edit_last").hide();
}

// 获得高度、宽度
function getTopself() {
	var h = document.documentElement.clientHeight;
	var height = (h - 500) / 2;
	var w = document.documentElement.clientWidth;
	var width = (w - 600) / 2;
	$("#edit_last").css("margin-left", width);
	$("#edit_last").css("top", height);
	$("#psw_last").css("margin-left", width);
	$("#psw_last").css("top", height);
	$("#school_last").css("margin-left", width);
	$("#school_last").css("top", height);
	$("#add_last").css("margin-left", width);
	$("#add_last").css("top", height);
	$("#power_last").css("margin-left", width);
	$("#power_last").css("top", height);
};



// 修改管理员信息
function editadmin(index) {
	if (index == 1) {
		var newlogin = $("#newlogin").val();
		var newrealname = $("#newrealname").val();
		var newphone = $("#newphone").val();
		var adminid = $("#admin_idedit").val();
		var regu =/^(?:13\d|15[0123456789]|12[0123456789]|11[0123456789]|14[0123456789]|16[0123456789]|17[0123456789]|19[0123456789]|18[0123456789]|17[0123456789])-?\d{5}(\d{3}|\*{3})$/;
		if(!regu.test(newphone)){
			alert("用户联系电话格式不正确");
			return;
		}
		if (confirm("是否编辑该管理员？")) {
			$.ajax({
				type : "POST",
				url : "editAdmin.do",
				data : {
					newlogin : newlogin,
					newrealname : newrealname,
					newphone : newphone,
					adminid : adminid
				},
				success : function(data) {
					if (data == "error") {
						alert("编辑失败");
						return;
					}
					if (data == "success") {
						alert("编辑成功！");
						window.location.reload();
					}
				}
			});
		}

	} else if (index == 2) {
		var adminid = $("#admin_idpsw").val();
		var secpassword = $("#secpassword").val();
		var tirpassword = $("#tirpassword").val();
		if (secpassword == 0 || tirpassword == 0) {
			alert("信息不能为空");
			return;
		}
		if (confirm("是否修改该管理员密码？")) {
			$.ajax({
				type : "POST",
				url : "changePsw.do",
				data : {
					adminid : adminid,
					secpassword : secpassword,
					tirpassword : tirpassword
				},
				success : function(data) {
					if (data == "error1") {
						alert("原密码输入正确");
						return;
					}
					if (data == "error2") {
						alert("两次密码输入不匹配");
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

// 显示修改密码弹框
function showchangepsw(index, password) {
	$("#admin_idpsw").val(index);
	$("#admin_passwword").val(password);
	$("#psw").show();
	$("#psw_sec").show();
	$("#psw_last").show();
}

function unshowchangepsw() {
	$("#psw").hide();
	$("#psw_sec").hide();
	$("#psw_last").hide();
}

// 修改驾校
function changeschool() {
	var newschoolid = document.getElementsByName("newschoolid");
	for (var int = 0; int < newschoolid.length; int++) {
		if (newschoolid[int].selected) {
			newschoolid = newschoolid[int].value;
		}
	}
	var adminid = $("#admin_idsch").val();
	if (confirm("是否修改所属驾校？")) {
		$.ajax({
			type : "POST",
			url : "changeschool.do",
			data : {
				adminid : adminid,
				newschoolid : newschoolid
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

// 显示修改驾校弹框
function showchangeschool(index, schoolname) {
	$("#oldschoolname").val("原驾校：" + schoolname);
	$("#admin_idsch").val(index);
	$("#school").show();
	$("#school_sec").show();
	$("#school_last").show();
	$.ajax({
		type : "POST",
		url : "getDriveSchool.do",
		success : function(data) {
			var jsondata = eval("(" + data + ")");
			var state = jsondata.state;
			if (state == 200) {
				$("#schoollist").empty();
				var html = "";
				var list = jsondata.list;
				for (var j = 0; j < list.length; j++) {
					html +="<option name='newschoolid' value='"
							+ list[j].schoolid
							+ "'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
							+ list[j].name + "</option>";
				}
				$("#schoollist").append(html);
			}
		}
	});
}

// 隐藏修改驾校弹框
function unshowchangeschool() {
	$("#school").hide();
	$("#school_sec").hide();
	$("#school_last").hide();
}

// 显示添加管理员弹框
function showaddadmin() {
	$("#add").show();
	$("#add_sec").show();
	$("#add_last").show();
}

// 点击隐藏管理员弹框
function unshowaddadmin() {
	$("#add").hide();
	$("#add_sec").hide();
	$("#add_last").hide();
}

function fun1() {
	$("#normaladmin").hide();
	$("#schooladmin").hide();
}

function fun2() {
	$("#normaladmin").show();
	$("#schooladmin").hide();
}

function fun3() {
	$("#normaladmin").hide();
	$("#schooladmin").show();
	$.ajax({
		type : "POST",
		url : "getDriveSchool.do",
		success : function(data) {
			var jsondata = eval("(" + data + ")");
			var state = jsondata.state;
			if (state == 200) {
				$("#drive").empty();
				var html = "";
				var list = jsondata.list;
				for (var j = 0; j < list.length; j++) {
					html += "<option name='admin_schoolid' value='"
							+ list[j].schoolid
							+ "'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
							+ list[j].name + "</option>";
				}
				$("#drive").append(html);
			}
		}
	});
}

// 添加管理员
function addadmin() {
	var schoolid = $("#drive").val();
	$("#schoolid").val(schoolid);
	var admin_login=$("#admin_login").val();
	var admin_name=$("#admin_name").val();
	var admin_password=$("#admin_password").val();
	var admin_phone=$("#admin_phone").val();
	var regu =/^(?:13\d|15[0123456789]|12[0123456789]|11[0123456789]|14[0123456789]|16[0123456789]|17[0123456789]|19[0123456789]|18[0123456789]|17[0123456789])-?\d{5}(\d{3}|\*{3})$/;
	if(!regu.test($("input[name=admin_phone]").val())){
		alert("用户联系电话格式不正确");
		return;
	}
	if(admin_login==""){
		alert("账号不能为空");
		return;
	}else if(admin_name==""){
		alert("用户姓名不能为空");
		return;
	}else if(admin_password==""){
		alert("用户密码不能为空");
		return;
	}else if(admin_phone==""){
		alert("用户联系电话不能为空");
		return;
	}else{
		$("#form_submit").submit();
	}
}


/**
 * 检验修改的账户名是否存在
 */
function checklogin() {
	var admin_login = $("#admin_login").val();
	if(admin_login==0){
		alert("账号名不能为空");
	}else{
	$.ajax({
		type : "POST",
		url : "checklogin.do",
		data : {
			admin_login : admin_login
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

//输入校验数字
function checkRate()
{
     var re = /^[0-9]+.?[0-9]*$/;   //判断字符串是否为数字     //判断正整数 /^[1-9]+[0-9]*]*$/  
    var nubmer = $("#telphone").val();
     if (!re.test(nubmer))
    {
        alert("电话请输入数字");
        $("#telphone").val("");
        return false;
     }
}

//修改权限
function changepermession(){
var admin_permession=document.getElementsByName("permession");
var count=0;
for (var int = 0; int < admin_permession.length; int++) {
	if(admin_permession[int].checked){
		count++;
	}
}

$("#editpower").submit();

}

//显示父权限
function setPid()
{
	$.ajax({
		type : "POST",
		url : "getParentPermission.do",
		 async: false,
		data :{},
		success : function(data)
		{
			
			var options=document.getElementById("selectPid");
			options.add(new Option("所有权限",0));
			parentPermission=eval(data);
			if(parentPermission!=-1)
			{
				for(var i in parentPermission)
				{
					var opt=new Option(parentPermission[i].name,parentPermission[i].permissionid);
					options.add(opt);
				}
			}
			
		}
	});
}

//筛选权限
function selectPermission()
{
	flag=$("#selectPid").val();
	var form=$("#editpower");
	var checkboxes=form.find("input[type='checkbox']");
	checkboxes.each(function(){
		if(flag==0)
		{
			$(this).parent().css("display","block");
		}else
		{
			var str=$(this).val();
			str=str.split(",");
			if(str[0]==flag)
			{
				$(this).parent().css("display","block");
			}else
			{
				$(this).parent().css("display","none");
			}
		}
	});
}
//显示编辑权限
function showchangepermession(index){
	str="";
	$("#admin_idpower").val(index);
	setPid();
	var pid=$("#selectPid").val();
	$.ajax({
		type : "POST",
		url : "getMyPermession.do",
		data : {
			adminid : index
		},
		success : function(data) {
			var jsondata = eval("("+data+")");
			var permession=jsondata.permissions;
			$("#changepermession").empty();
			for (var i = 0; i < permession.length; i++) {
				if(permession[i].checked){
					str+=permession[i].name;
					$("#changepermession").append("<div style='width:180px;height:28px;float:left;margin-top:8px;'>"+
						
						"<input value="+permession[i].parentPermissionId+","+permession[i].permissionid+" type='checkbox' style='width: 50px; height: 25px; margin-left: 5px; margin-top: 5px; font-size: 18px; text-align: center;cursor: pointer;float:left;' name='permession' checked='checked'/>"
						+"<span style='width: 100px;height: 25px; position: relative;margin-left: 10px;overflow: hidden;line-height:28px;float:left;' title='"+permession[i].name+"'>"+permession[i].name+"</span></div>");
				}else{
					$("#changepermession").append("<div style='width:180px;height:28px;float:left;margin-top:8px;'>"+
							"<input value="+permession[i].parentPermissionId+","+permession[i].permissionid+" type='checkbox' style='width: 50px; height: 25px; margin-left: 5px; margin-top: 5px; font-size: 18px; text-align: center;cursor: pointer;float:left;' name='permession' />"
						+"<span style='width: 100px;height: 28px; position: relative;margin-left: 10px;overflow: hidden;float:left;line-height:28px;'title='"+permession[i].name+"'>"+permession[i].name+"</span></div>");
				}
			}
		$("#changepermession").append("<div style='clear:both;'></div>");
		}
	});
	$("#power").show();
	$("#power_sec").show();
	$("#power_last").show();
}


function unshowchangepermession(){
	$("#power").hide();
	$("#power_sec").hide();
	$("#power_last").hide();
}

//新建权限
function newPermission(flag)
{
	if(flag=="display")
	{
		return function display(){
		obj=document.getElementById("newPermissionForm");
		obj.style.display="block";
		setParentPermissions();
		}
	}
	if(flag=="hide")
	{
		return function hide()
		{
			obj.style.display="none";
			var options=document.getElementById("parentPermission");
			options.length=0;
		}
	}
	
	if(flag=="error")
	{
		return function error(code){
			if(code==1)
			{
				alert("新建权限失败");
			}else if(code==2)
			{
				alert("新建权限成功！");
			}
			
		}
	}
	
	//获取父权限
	function setParentPermissions()
	{
		$.ajax({
			type : "POST",
			url : "getParentPermission.do",
			 async: false,
			data :{},
			success : function(data)
			{
				
				var options=document.getElementById("parentPermission");
				options.add(new Option("新建父权限",0));
				parentPermission=eval(data);
				if(parentPermission!=-1)
				{
					for(var i in parentPermission)
					{
						var opt=new Option(parentPermission[i].name,parentPermission[i].permissionid);
						options.add(opt);
					}
				}
				
			}
		});
	}
}

