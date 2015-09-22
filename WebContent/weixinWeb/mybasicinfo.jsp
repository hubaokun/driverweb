<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="checksession.jsp" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>基本信息</title>
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="css/font-awesome.css" rel="stylesheet" />
<link href="mobiscroll/css/mobiscroll.animation.css" rel="stylesheet" type="text/css" />
<link href="mobiscroll/css/mobiscroll.widget.css" rel="stylesheet" type="text/css" />
<link href="mobiscroll/css/mobiscroll.widget.ios.css" rel="stylesheet" type="text/css" />
<link href="mobiscroll/css/mobiscroll.scroller.css" rel="stylesheet" type="text/css" />
<link href="mobiscroll/css/mobiscroll.scroller.ios.css" rel="stylesheet" type="text/css" />
<link href="css/basicinfo.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.mbsc-ios .dw-sel {
	color: #1798f2;
}
.mbsc-ios .dwwc {
	padding: 30px 0px 10px 40px;
}
.mbsc-ios .dwb {
	color: #1798f2;
}
</style>
</head>

<body>
	<div class="container">
    	<div class="row basic-head">
        	<div style="border-bottom:1px solid #eee">
            	<div class="col-md-3 col-sm-3 col-xs-3">
            	<img id="avatarurl" src="images/person-one.png" class="img-responsive img-circle center-block" />
            </div>	
            <div class="col-md-9 col-sm-9 col-xs-9">
            	<label for="avart_img"></label>
               <!--  <input type="file" id="avart_img" style="display:none;"  /> -->
            </div>
            <div class="clearfix"></div>
            </div>
            <div class="col-md-12 col-sm-12 col-xs-12">
            	<div class="row basic-data-content">
            		<div class="col-md-4 col-sm-4 col-xs-4">
            			<p>联系电话</p>
            		</div>
            		<div class="col-md-8 col-sm-8 col-xs-8">
            			<input type="text" id="phone" disabled value="13757187548"/>
            		</div>
            	</div>
            </div>
        </div>
        <div class="row basic-data">
        	<div class="col-md-12 col-sm-12 col-xs-12">	
            	<div class="row basic-data-content">
            		<div class="col-md-4 col-sm-4 col-xs-4">
            			<p>真实姓名</p>
            		</div>
            		<div class="col-md-8 col-sm-8 col-xs-8">
            			<input type="text" id="nameSave" cate="1" disabled value="莉莉"/>
            			<input type="text"  placeholder="输入姓名" id="name" cate=2 />
            		</div>
            	</div>
            	<div class="row basic-data-content">
            		<div class="col-md-4 col-sm-4 col-xs-4">
            			<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;性别</p>
            		</div>
            		<div class="col-md-8 col-sm-8 col-xs-8">
            			<input type="text" id="genderSave" cate="1" disabled value="女" /> <select  placeholder="性别" id="genderEdit" cate="2"  style="border:none;"/><option value="男">男</option><option value="女">女</option></select>
            		</div>
            	</div>
            	<div class="row basic-data-content">
            		<div class="col-md-4 col-sm-4 col-xs-4">
            			<p>出生年月</p>
            		</div>
            		<div class="col-md-8 col-sm-8 col-xs-8">
            			<input type="text" cate="1" placeholder="出生年月" id="birthdaySave" disabled value="" /><input type="text" placeholder="出生年月" id="birthday" cate="2" value="" />
            		</div>
            	</div>
            	<div class="row basic-data-content">
            		<div class="col-md-4 col-sm-4 col-xs-4">
            			<p>所在城市</p>
            		</div>
            		<div class="col-md-8 col-sm-8 col-xs-8">
            			<input type="text" placeholder="省-市-区/县" id="location" disabled cate="1" value="浙江省杭州市余杭区"/>
		        		<select id="province"  placeholder="省/自治区/直辖市" onblur="changeProvince();" cate="2" />
          					<option>--选择省份--</option>
          				</select>
          				<select id="city"  placeholder="市" onblur="changeCity();"  cate="2">
          					<option>--选择市--</option>
         				</select>
         				<select id="area"  placeholder="区" cate="2">
         					<option>--选择区--</option>
         				</select>		
         				<input type="hidden" id="selectedprovince" />
		       			<input type="hidden" id="selectedcity" />
		       			<input type="hidden" id="selectedarea" />
            		</div>
            	</div>
            	
            	
            </div>

        </div> 
          
        <div class="row mylearninfo-submit">
  	<div class="col-md-12 col-sm-12 col-xs-12">
    	<span class="save-data">保存信息</span>
    	<span class="edit-data">编辑信息</span>
    </div>
  </div>  
    </div>
    
    
<div class="overlay">
      <div class="overlay-content">
        <div class="container">
            <div class="row">
              <div class="col-md-12 col-sm-12 col-xs-12">
                  <span>是否保存资料的编写？</span>
              </div>
              <div class="no col-md-6 col-sm-6 col-xs-6" style="border-right:1px solid rgb(218,218,218);"><span class="grey">否</span></div>
              <div class="yes col-md-6 col-sm-6 col-xs-6" onclick="perfectPersoninfo()" id="span-save"><span class="blue" >是</span></div>
              
        </div>
      </div>
  </div>	
</div> 

<script src="js/jquery-1.8.3.min.js"></script> 
<script src="js/bootstrap.min.js"></script> 
<script src="mobiscroll/js/mobiscroll.core.js"></script> 
<script src="mobiscroll/js/mobiscroll.widget.js"></script> 
<script src="mobiscroll/js/mobiscroll.scroller.js"></script> 
<script src="mobiscroll/js/mobiscroll.util.datetime.js"></script> 
<script src="mobiscroll/js/mobiscroll.datetimebase.js"></script> 
<script src="mobiscroll/js/mobiscroll.i18n.zh.js"></script> 
<script src="mobiscroll/js/mobiscroll.widget.ios.js"></script> 
<!--<script src="js/bootstrap-datepicker.js"></script>--> 
<script>
$(document).ready(function ()
{
		
	$("#avart_img").change(function ()
	{
		var img_v = $(this).val();
		//alert (img_v);
		$("#avartimg").src(img_v);
	});
	
	var height = $(window).height();
	var width = $(window).width();
	$('.save-data').on('click',function()
	{
		$('.overlay').css('display','block');
		var w_content = $('.overlay-content').width();
		var h_content = $('.overlay-content').height();
		var h = (height-h_content)/2;
		var w = (width-w_content)/2;
		$('.overlay-content').css('top',h);
		$('.overlay-content').css('left',w);
	});
	$('.yes,.no').on('click',function()
	{
		$('.overlay').css('display','none');
	})
	
	//城市选择的弹框
	$('#location').click(function ()
	{
		$('.overlay-location').css('display','block');
	});

	$('.cancel-location').click(function ()
	{
		$('.overlay-location').css('display','none');
		
	});
	
/* 	$('.sure-location').click(function ()
	{
		$('.overlay-location').css('display','none');
		
		var pro = $('#province').val();
		var city = $('#city').val();
		var area = $("#area").val();
		
		//获得选中城市的ID
		var obj = $('#city option').attr("id");
		alert (obj);
		
		var str = pro + city + area;
		$('#location').val(str);
		$("#selectedcity").val(obj);
		//$('#location').
		
	}); */
	 
	//控制编辑和保存页面的切换
	$('span.edit-data').click(function ()
	{
		$('input[cate="1"]').css('display','none');
		$('input[cate="2"]').css('display','block');
		$('select[cate="2"]').css('display','block');
		$('span.save-data').css('display','block');
		$('span.edit-data').css('display','none');
	});
	$('#span-save').click(function ()
	{
		$('input[cate="1"]').css('display','block');
		$('input[cate="2"]').css('display','none');
		$('select[cate="2"]').css('display','none');
		$('span.save-data').css('display','none');
		$('span.edit-data').css('display','block');
	});

	
});
</script> 
<script type="text/javascript">
        $(function () {
			var nowData=new Date();
	        var opt= { 
	        	theme:'ios', //设置显示主题 
                mode:'scroller', //设置日期选择方式，这里用滚动
                display:'bottom', //设置控件出现方式及样式
                preset : 'date', //日期:年 月 日 时 分
                minDate: new Date(1916,1,1), 
				maxDate:new Date(nowData.getFullYear(),12,31),
                dateFormat: 'yy-mm-dd', // 日期格式
//              dateOrder: 'yymmdd', //面板中日期排列格式
                stepMinute: 5, //设置分钟步长
                yearText:'年', 
                monthText:'月',
                dayText:'日',
                hourText:'时',
                minuteText:'分',
                lang:'zh' //设置控件语言};
            };
            $('#birthday').mobiscroll(opt);
        });
    </script>
    <script> 

var Aprovince;

//获取省份 
function initProvinceData()
{ 
	var dataroot_pro="json/t_provinces.json"; 
	
	$.getJSON(dataroot_pro, function(data)
	{ 
		//alert (data.RECORDS[0].code);
		//alert (data.RECORDS.length);
		
		for(var i=0;i<data.RECORDS.length;i++)
		{
			Aprovince += '<option id=" ' + data.RECORDS[i].provinceid + '" value="' + data.RECORDS[i].province + '">' + data.RECORDS[i].province + '</option>'; 	
		}
		//alert (typeof Afather);
		
		$("#province").append(Aprovince);
		//alert (data);
	}); 

}

function changeProvince()
{
	var dataroot_pro="json/t_provinces.json"; 

	//先去清空city中的数据，再进行新城市数据的加载    
    $('#city').val("");
    $('#city').empty();
	
    $.getJSON(dataroot_pro, function(data)
    { 
		//alert (data.RECORDS.length);
		var len = data.RECORDS.length;
		var pro_id;
		var prv_val = $("#province").val();
		for (var i=0;i<len;i++)
		{
			//alert (data.RECORDS[i]);
			if (data.RECORDS[i].province == prv_val)
			{
				//alert (data.RECORDS[i].province);
				pro_id = data.RECORDS[i].provinceid;
				setCity(pro_id);
				break;
			}
		}
		//alert (pro_id);
		getJson(pro_id);
    }); 
}

function changeCity()
{ 
	var dataroot_pro="json/t_cities.json"; 

	//先去清空city中的数据，再进行新城市数据的加载    
    $('#area').val("");
    $('#area').empty();
	
    $.getJSON(dataroot_pro, function(data)
    { 
		//alert (data.RECORDS.length);
		var len = data.RECORDS.length;
		var city_id;
		var city_val = $("#city").val();
		for (var i=0;i<len;i++)
		{
			//alert (data.RECORDS[i]);
			if (data.RECORDS[i].city == city_val)
			{
				//alert (data.RECORDS[i].city);
				city_id = data.RECORDS[i].cityid;
				break;
			}
		}
		//alert ( "改变之后的城市id:" + city_id);
		setArea(city_id);
    }); 
} 

/* function getJson(id)
{ 
	var dataroot_pro="json/t_cities.json"; 
    var provinceID; 
    $.getJSON(dataroot_pro,function (data) 
    { 
      for (var i = 0; i < data.RECORDS.length; i++) 
      { 
        if (data.RECORDS[i].provinceid == id) 
        { 
        	provinceID = data.RECORDS[i].provinceid;
        	break;
        } 
      } 
      setCity(provinceID); 
    }); 
}  */

function setCity(val)
{ 
    var Acity; 
    
    var $listcity = $("#city"); 
    
    var dataroot_pro="json/t_cities.json";
    
    $.getJSON(dataroot_pro,function (data) 
    { 
      for (var n = 0; n < data.RECORDS.length; n++) 
      { 
        if (data.RECORDS[n].provinceid == val) 
        { 
          //alert(data.RECORDS[n].provinceid); 
          Acity += '<option id=" ' + data.RECORDS[n].cityid + '" value="' + data.RECORDS[n].city + '">' + data.RECORDS[n].city + '</option>'; 
        } 
      } 
      $listcity.append(Acity); 
    }); 
 } 

/* function getJsonArea(id)
{ 
    var areaID; 
    alert ("从上个函数中拿到的城市id为：" + id);
    $.getJSON('t_areas.json',function (data) 
    { 
      for (var i = 0; i < data.RECORDS.length; i++) 
      { 
        if (data.RECORDS[i].cityid == id) 
        { 
          areaID=data.RECORDS[i].id; 
          break;
        } 
      } 
      alert ("getJsonArea中的城市id为：" + areaID);
      setArea(areaID); 
    }); 
  }  */
  
  function setArea(Aval)
  { 
    var Aarea; 
    var $listarea = $("#area"); 
    
    //alert ("拿到的城市的id为：" + Aval);
    
    $.get('json/t_areas.json',function (data) 
    { 
      for (var m = 0; m < data.RECORDS.length; m++) 
      { 
        if (data.RECORDS[m].cityid == Aval) 
        { 
          Aarea += '<option id=" ' + data.RECORDS[m].areaid + '" value="' + data.RECORDS[m].area + '">' + data.RECORDS[m].area + '</option>'; 
        } 
      } 
      $listarea.append(Aarea); 
    }); 
  } 


$(document).ready(function ()
{
	initProvinceData();
});
 
</script>

<script type="text/javascript">
$(document).ready(function(){
	var sid='${sessionScope.studentid}';
	//sid="18";
	var token='${sessionScope.token}';
	var params = {action:"GETSTUDENTINFO",studentid:sid,token:token};
	jQuery.post("../suser", params, showStudent, 'json');
	
	/* var c_info='${sessionScope.c_info}';
	var wxinfo=$.parseJSON(c_info);
	$("#avatarurl").attr("src",wxinfo.headimgurl); */
	
});
function showStudent(obj){
	if(obj.code==1){
		var c_info='${sessionScope.c_info}';
		var wxinfo=$.parseJSON(c_info);
		/* if(obj.data.avatarurl==''){
			$("#avatarurl").attr("src",wxinfo.headimgurl);
		}else{
			$("#avatarurl").attr("src",obj.data.avatarurl);//设置头像图片
		} */
		$("#avatarurl").attr("src",wxinfo.headimgurl);
		
		$("#nameSave").val(obj.data.realname);
		$("#name").val(obj.data.realname);
		$("#phone").val(obj.data.phone);
		$("#cityname").val(obj.data.city);
		$("#birthday").val(obj.data.birthday);
		$("#birthdaySave").val(obj.data.birthday);
		if(obj.data.gender==1){
			$("#genderSave").val("男");
		}else if(obj.data.gender==2){
			$("#genderSave").val("女");
		}
		$("#location").val(obj.data.city);
	}else{
		alert(obj.message);
		window.location.href=redirect_login;
	}
}

function perfectPersoninfo(){
	var sid='${sessionScope.studentid}';
	//sid="18";
	var gender = $("#genderEdit").find("option:selected").val();
	//alert (gender);
	
	
	
	
	//获得选中城市的ID
	var objProvinceId = $('#province').find("option:selected").attr("id");
	//alert (objProvinceId);
	$("#selectedprovince").val(objProvinceId);
	var objCityId = $('#city option').attr("id");
	$("#selectedcity").val(objCityId);
	//alert (objCityId);
	var objAreaId = $('#area option').attr("id");
	//alert (objAreaId);
	$("#selectedarea").val(objAreaId);

	var g="1";
	if(gender=='男'){
		g="1";
	}else if(gender=='女'){
		g="2";
	}
	var tel = $("#phone").val(); //获取手机号
	var telReg = !!tel.match(/^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/);
	if(!telReg){
		alert("手机号码格式有误!");
		return false;
	}
	//alert(" selectedcity="+$("#selectedcity").val()+" gender="+g);
	var token='${sessionScope.token}';
	var cityid=$.trim($("#selectedcity").val());
	var params = {
				  action:"PERFECTPERSONINFO",
				  studentid:sid,
				  realname:$("#name").val(),
				  gender:g,
				  birthday:$("#birthday").val(),
				  token:token,
				  cityid:cityid,
				  provinceid:$("#provinceid").val(),
				  areaid:$("#areaid").val()
				  };
	jQuery.post("../suser", params, showPerfect, 'json'); 



}
function showPerfect(obj){
	if(obj.code==1){
		alert(obj.message);
		window.location.href="mybasicinfo.jsp";
	}else{
		alert(obj.message);
		window.location.href=redirect_login;
	}
}
</script>
</body>
</html>