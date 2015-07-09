// 限制用户输入数字
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

//
//$(function(){
//	var s= document.documentElement.clientHeight;
//	var height = s-61-92;
//	$("#nav").css("min-height",height);
//	$("#nav_left").css("min-height",height);
//});


function getTop(){
	var h= document.documentElement.clientHeight;
	var height = (h-300)/2;
	var w= document.documentElement.clientWidth;
	var width=(w-500)/2;
	$("#mask_last").css("margin-left",width);
	$("#mask_last").css("top",height);
	$("#level_last").css("margin-left",width);
	$("#level_last").css("top",height);
	$("#edit_last").css("margin-left",width);
	$("#edit_last").css("top",height);
	$("#frozen_last").css("margin-left",width);
	$("#frozen_last").css("top",height);
	$("#alertbox_last").css("margin-left",width);
	$("#alertbox_last").css("top",height);
	$("#change_last").css("margin-left",width);
	$("#change_last").css("top",height);
};

//输入金额 可为负
function myKeyDown(value, e){
    var    k=window.event.keyCode;   
//		alert(value.indexOf("."));
	if((k==190&&value.length==0)){
		if(document.all)
    	{
    	window.event.returnValue = false;
    	}
    	else
    	{
    	event.preventDefault();
    	}
		return ;
	}
	if((k==110&&value.length==0)){
		if(document.all)
    	{
    	window.event.returnValue = false;
    	}
    	else
    	{
    	event.preventDefault();
    	}
		return ;
	}
	if((k==189&&value.length>0)||(k==109&&value.length>0)){
		if(document.all)
    	{
    	window.event.returnValue = false;
    	}
    	else
    	{
    	event.preventDefault();
    	}
		return ;
	}
    if((k==46)||(k==8)||(k==189&&value.indexOf("-")<0)||(k==109&&value.indexOf("-")<0)||(k==190&&value.indexOf(".")<0)||(k==110&&value.indexOf(".")<0)|| (k>=48 && k<=57)||(k>=96 && k<=105)||(k>=37 && k<=40)){
     }
    else if(k==13){
         window.event.keyCode = 9;}
    else{
    	if(document.all)
    	{
    	window.event.returnValue = false;
    	}
    	else
    	{
    	event.preventDefault();
    	}}
}

function change(a, b) {
	var pic = document.getElementById(a);
	var file = document.getElementById(b);

	var fileval = $("#" + b).val();
	if (fileval == "") {
		return;
	}
	// gif在IE浏览器暂时无法显示
	if (!/\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test(file.value)) {
		alert("图片类型必须是.gif,jpeg,jpg,png中的一种,请重新上传!");
		file.outerHTML = file.outerHTML.replace(/(value=\").+\"/i, "$1\"");
		return;
	}

	var isIE = /msie/i.test(navigator.userAgent) && !window.opera;
	var fileSize = 0;
	if (isIE && !file.files) {
		var filePath = file.value;
		var fileSystem = new ActiveXObject("Scripting.FileSystemObject");
		var fileImg = fileSystem.GetFile(filePath);
		fileSize = fileImg.Size;
	} else {
		fileSize = file.files[0].size;
	}
	var size = fileSize / 1024 * 1024; //单位B
	if (size > (1024 * 1024)) {
		alert("文件大小不能超过1M,请重新编辑后上传");
		file.outerHTML = file.outerHTML.replace(/(value=\").+\"/i, "$1\"");
		return;
	}

	// IE浏览器
	if (document.all) {
		file.select();
		var reallocalpath = document.selection.createRange().text;
		var ie6 = /msie 6/i.test(navigator.userAgent);
		// IE6浏览器设置img的src为本地路径可以直接显示图片
		if (ie6)
			pic.src = reallocalpath;
		else {
			// 非IE6版本的IE由于安全问题直接设置img的src无法显示本地图片，但是可以通过滤镜来实现
			pic.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='image',src=\""
					+ reallocalpath + "\")";
			// 设置img的src为base64编码的透明图片 取消显示浏览器默认图片
			pic.src = 'data:image/gif;base64,R0lGODlhAQABAIAAAP///wAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==';
		}
	} else {
		html5Reader(file, pic);
	}
	$(".selectedfile").val(file.value);
}

function html5Reader(file, pic) {
	var file = file.files[0];
	var reader = new FileReader();
	reader.readAsDataURL(file);
	reader.onload = function(e) {

		pic.src = this.result;
	};
}
