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
            	<label for="avart_img"><i class="icon icon-chevron-right basic-icon"></i></label>
               <!--  <input type="file" id="avart_img" style="display:none;"  /> -->
            </div>
            <div class="clearfix"></div>
            </div>
            <div class="col-md-12 col-sm-12 col-xs-12">
            	<p><span>真实姓名：</span><input type="text" id="realname" placeholder="真实姓名"/></p>
            </div>
            <div class="col-md-12 col-sm-12 col-xs-12">
            	<p><span>联系电话：</span><input type="text" id="phone"/></p>
            </div>
        </div>
        <div class="row basic-data">
        	<!-- <div class="col-md-12 col-sm-12 col-xs-12">
            	<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;性别：<span><input type="text"  placeholder="性别" id="gender"/></span></p>
            </div> -->
            <div class="col-md-12 col-sm-12 col-xs-12">
		      <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;性别：
		        <input type="text"  placeholder="性别" id="gender" />
		      </p>
		    </div>
            <div class="col-md-12 col-sm-12 col-xs-12">
            	<p>出生年月：<span><input type="text" placeholder="出生年月" id="birthday" /></span></p>
            </div>
            <div class="col-md-12 col-sm-12 col-xs-12"><p>所在城市：
		        <input type="text" placeholder="省-市-区/县" id="location"/>
		        <input type="hidden" id="selectedcity" />
		    </p></div>
        </div>   
        <div class="row mylearninfo-submit">
  	<div class="col-md-12 col-sm-12 col-xs-12">
    	<span class="save-data">保存信息</span>
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
              <div class="yes col-md-6 col-sm-6 col-xs-6" onclick="perfectPersoninfo()"><span class="blue" >是</span></div>
              
        </div>
      </div>
  </div>	
</div> 

<div class="overlay-gender" style="display:none;">
  <div class="overlay-gender-content">
    <div class="container">
      <div class="row button-wrap">
        <div class="col-md-6 col-sm-6 col-xs-6 text-left"> <span class="cancel">取消</span> </div>
        <div class="col-md-6 col-sm-6 col-xs-6 text-right"> <span class="sure">确定</span> </div>
      </div>
      <div class="row radio-wrap">
        <div class="col-md-6 col-sm-6 col-xs-6">
          <input type="radio" id="male" name="gender" value="男" checked>
          <label for="male">男</label>
        </div>
        <div class="col-md-6 col-sm-6 col-xs-6">
          <input type="radio" id="female" value="女" name="gender">
          <label for="female">女</label>
        </div>
      </div>
    </div>
  </div>
</div>
<div class="overlay-location">
  <div class="overlay-location-content">
    <div class="container">
    <div class="row button-wrap">
              <div class="col-md-6 col-sm-6 col-xs-6 text-left">
                  <span class="cancel-location">取消</span>
              </div>
              <div class="col-md-6 col-sm-6 col-xs-6 text-right">
                  <span class="sure-location">确定</span>
              </div>
            </div>
      <div class="row province-wrap">
        <div class="col-md-3 col-sm-3 col-xs-3"><span class="pull-right">省份</span></div>
        <div class="col-md-9 col-sm-9 col-xs-9">
          <input type="text" id="province" list="prvlist" placeholder="省/自治区/直辖市" onblur="changeProvince();" />
          <datalist id="prvlist" style="height:50px;"></datalist>
        </div>
      </div>
      <div class="row city-wrap">
        <div class="col-md-3 col-sm-3 col-xs-3"><span class="pull-right">市</span></div>
        <div class="col-md-9 col-sm-9 col-xs-9">
          <input type="text" id="city" list="citylist" placeholder="市" onblur="changeCity();" >
          <datalist id="citylist"> </datalist>
        </div>
      </div>
      <div class="row area-wrap">
        <div class="col-md-3 col-sm-3 col-xs-3"><span class="pull-right">区/县</span></div>
        <div class="col-md-9 col-sm-9 col-xs-9">
          <input type="text" id="area" list="arealist" placeholder="区">
          <datalist id="arealist"> </datalist>
        </div>
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
	
	
	//性别选择的弹框
	$('#gender').click(function ()
	{
		$('.overlay-gender').css('display','block');
	});

	$('.cancel').click(function ()
	{
		$('.overlay-gender').css('display','none');
	});
	
	$('.sure').click(function ()
	{
		$('.overlay-gender').css('display','none');
		var str = $("input[name='gender']:checked").val();
		//alert (str);
		$("#gender").val(str);
	});
	
	//城市选择的弹框
	$('#location').click(function ()
	{
		$('.overlay-location').css('display','block');
	});

	$('.cancel-location').click(function ()
	{
		$('.overlay-location').css('display','none');
		
	});
	
	$('.sure-location').click(function ()
	{
		$('.overlay-location').css('display','none');
		
		var pro = $('#province').val();
		var city = $('#city').val();
		var area = $("#area").val();
		
		//获得选中城市的ID
		var obj = $('#citylist option').attr("id");
		
		var str = pro + city + area;
		$('#location').val(str);
		$("#selectedcity").val(obj);
		//$('#location').
		
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
//              dateFormat: 'yy-mm-dd', // 日期格式
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
			Aprovince += '<option id=" ' + data.RECORDS[i].provinceid + '" value="' + data.RECORDS[i].province + '">'; 	
		}
		//alert (typeof Afather);
		
		$("#prvlist").append(Aprovince);
		//alert (data);
	}); 

}

function changeProvince()
{
	var dataroot_pro="json/t_provinces.json"; 

	//先去清空city中的数据，再进行新城市数据的加载    
    $('#city').val("");
    $('#citylist').empty();
	
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
    $('#arealist').empty();
	
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

function getJson(id)
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
} 


function setCity(val)
{ 
    var Acity; 
    
    var $listcity = $("#citylist"); 
    
    var dataroot_pro="json/t_cities.json";
    
    $.getJSON(dataroot_pro,function (data) 
    { 
      for (var n = 0; n < data.RECORDS.length; n++) 
      { 
        if (data.RECORDS[n].provinceid == val) 
        { 
          //alert(data.RECORDS[n].provinceid); 
          Acity += '<option id=" ' + data.RECORDS[n].cityid + '" value="' + data.RECORDS[n].city + '">'; 
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
    var $listarea = $("#arealist"); 
    
    //alert ("拿到的城市的id为：" + Aval);
    
    $.get('json/t_areas.json',function (data) 
    { 
      for (var m = 0; m < data.RECORDS.length; m++) 
      { 
        if (data.RECORDS[m].cityid == Aval) 
        { 
          Aarea += '<option id=" ' + data.RECORDS[m].areaid + '" value="' + data.RECORDS[m].area + '">'; 
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
	var params = {action:"GETSTUDENTINFO",studentid:sid};
	jQuery.post("../suser", params, showStudent, 'json');
	
	var c_info='${sessionScope.c_info}';
	var wxinfo=$.parseJSON(c_info);
	$("#avatarurl").attr("src",wxinfo.headimgurl);
	
});
function showStudent(obj){
	if(obj.code==1){
		$("#realname").val(obj.data.realname);
		$("#phone").val(obj.data.phone);
		$("#cityname").val(obj.data.city);
		$("#birthday").val(obj.data.birthday);
		if(obj.data.gender==1){
			$("#gender").val("男");
		}else if(obj.data.gender==2){
			$("#gender").val("女");
		}
		$("#cityname").val(obj.data.city);
	}else{
		alert(obj.message);
	}
}

function perfectPersoninfo(){
	var sid='${sessionScope.studentid}';
	//sid="18";
	var gender=$("#gender").val();
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
	var token='${sessionScope.token}';
	var params = {
				  action:"PERFECTPERSONINFO",
				  studentid:sid,
				  realname:$("#realname").val(),
				  phone:$("#phone").val(),
				  gender:g,
				  birthday:$("#birthday").val(),
				  token:token,
				  cityid:$("#selectedcity").val()
				  };
	jQuery.post("../suser", params, showPerfect, 'json');
}
function showPerfect(obj){
	alert(obj.message);
}
</script>
</body>
</html>
