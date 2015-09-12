<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>地址选择</title>
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="css/font-awesome.css" rel="stylesheet" />
<link href="css/basicinfo.css" rel="stylesheet" type="text/css" />
<script src="js/jquery-1.8.3.min.js"></script> 
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
<style type="text/css">

</style>
</head>

<body>
<div class="container">
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
  <div class="row location-wrap">
  	<div class="col-md-12 col-sm-12 col-xs-12">
    	<a href="mybasicinfo.jsp" class="save-data">确定</a>
    </div>
  </div>  
  
</div>


</body>
</html>
