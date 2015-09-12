<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta ne="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>Untitled Document</title>
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<style type="text/css">
*
{
padding:0px;
margin:0px;
}
</style>
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
</head>

<body>

<input id="province" list="prvlist" placeholder="省/自治区/直辖市" onblur="changeProvince();">
<datalist id="prvlist" style="height:50px;"></datalist>
<br/>

<input style="" id="city" list="citylist" placeholder="市" onblur="changeCity();" >
<datalist id="citylist"> </datalist>

<br/>
<input style="" id="area" list="arealist" placeholder="区">
<datalist id="arealist"> </datalist>

</body>
</html>
