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
	var params = {};
	jQuery.post("getProvinceToJson.do", params, showProvince, 'json');
}
//显示省信息
function showProvince(obj)
{
         document.getElementById("province").length=0;
         for(var i=0;i<obj.length;i++)
         {
         	var o=new Option(obj[i].province,obj[i].provinceid);
         	document.getElementById("province").add(o);
         }
         document.getElementById("province1").length=0;
         for(var i=0;i<obj.length;i++)
         {
         	var o=new Option(obj[i].province,obj[i].provinceid);
         	document.getElementById("province1").add(o);
         }
}
function unshowaddschool() {
	$("#mask").hide();
	$("#mask_sec").hide();
	$("#mask_last").hide();
}
var citysid;
var areasid;
//修改驾校信息
function showeditschool(schoolname,schoolphone,schoolcontact,alipay,order_pull,provinceid,cityid,areaid,iscontract) {
	$("#oldschoolname").val("原驾校名："+schoolname);
	$("#oldschoolphone").val("原联系电话："+schoolphone);
	$("#oldschoolcontact").val("原联系人姓名："+schoolcontact);
	$("#oldalipay_account").val("原支付宝账号："+alipay);
	$("#oldorder_pull").val("原订单抽成： "+order_pull+"%");
	$("#oldflage_contract").val("原签约信息："+(iscontract==1?"已签约":"未签约"));
//    if(iscontract==1)
//		$("#editflag_contract").find("option:[text='已签约']").Attr("select",true);
//	else
//		$("#editflag_contract").find("option:[text='未签约']").Attr("select",true);
	$("#name").val(schoolname);
	citysid=cityid;
	areasid=areaid;
	//填充省选项,并且选中原来的省选项
	var params = {};
	jQuery.post("getProvinceToJson.do", params, function(obj){
			 //填充省选项
		    document.getElementById("province1").length=0;
	        for(var i=0;i<obj.length;i++)
	        {
	        	var o=new Option(obj[i].province,obj[i].provinceid);
	        	document.getElementById("province1").add(o);
	        }
			//选中省
			var objSelect= $("#province1").get(0);
			for(var i=0;i<objSelect.options.length;i++) {  
		        if(objSelect.options[i].value == provinceid) {  
		            objSelect.options[i].selected = true;  
		            break;  
		        }  
		    }  
	    }, 'json');
	//填充市，设置选中市选项
	var params = {provinceid:provinceid};
	jQuery.post("getCityByProvinceId.do", params, showEditCity,'json');
	
	$("#edit").show();
	$("#edit_sec").show();
	$("#edit_last").show();

}
//设置修改的区内容
function setAreainfo(citysid,area)
{
	//填充区，设置选中区选项
	var params = {cityid:citysid};
	jQuery.post("getAreaByCityId.do", params, function(obj){
		//填充区
		document.getElementById("area1").length=0;
        for(var i=0;i<obj.length;i++)
        {
        	var o=new Option(obj[i].area,obj[i].areaid);
        	document.getElementById("area1").add(o);
        }
        //选中区
		var objSelect= $("#area1").get(0);
		for(var i=0;i<objSelect.options.length;i++) {  
	        if(objSelect.options[i].value == areasid) { 
	        	//cityid=objSelect.options[i].value;//
	            objSelect.options[i].selected = true;  
	            break;  
	        }  
	    }  
	}, 'json');

}
//设置修改页面的城市内容
function showEditCity(obj)
{
	//填充市
	document.getElementById("city1").length=0;
    for(var i=0;i<obj.length;i++)
    {
    	var o=new Option(obj[i].city,obj[i].cityid);
    	document.getElementById("city1").add(o);
    }
    //选中市
	var objSelect= $("#city1").get(0);
	for(var i=0;i<objSelect.options.length;i++) {  
        if(objSelect.options[i].value == citysid) { 
            objSelect.options[i].selected = true; 
           // var citysid=objSelect.options[i].value;//
            setAreainfo(citysid,areasid)
            break;
        }  
    }  
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
	var provinceid=$("#province").val();
	var cityid=$("#city").val();
	var areaid=$("#area").val();
	var iscontract=$("#flag_contract").val();
	if(city==""){
		alert("请选择 市");
		return;
	}
	if(area=="" || area=="市辖区"){
		alert("请选择 区");
		return;
	}
	if (confirm("是否添加驾校信息？")) {
		$.ajax({
			type : "POST",
			url : "addSchool.do",
			data : {
				schoolname:schoolname,
				schoolphone:schoolphone,
				schoolcontact:schoolcontact,
				alipay_account:alipay_account,
				order_pull:order_pull,
				provinceid:provinceid,
				cityid:cityid,
				areaid:areaid,
				iscontract:iscontract
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
	
	var provinceid=$("#province1").val();
	var cityid=$("#city1").val();
	var areaid=$("#area1").val();
	var iscontract=$("#editflag_contract").val();
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
				editorder_pull:editorder_pull,
				provinceid:provinceid,
				cityid:cityid,
				areaid:areaid,
				iscontract:iscontract
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

