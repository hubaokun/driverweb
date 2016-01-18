
var provinces=[];
var schools=[];
var citys=[];
var provinceId;
var cityId;
var schoolId;


function locationPicker()
{
	provinces=getAllProvinces();
	$("#editprovince").suggest(provinces,
			{	dataFormater:provinceFormater,
				hot_list:provinces,
				dataContainer:'#editprovinceid',
				onSelect:triggerCity,
				attachObject:'#suggest'});
}


function triggerCity()
{
	provinceId=$("#editprovinceid").val();
	if(provinceId!=0)
	{
		citys=getCitiesByProvinceId(provinceId);
		$("#editcity").suggest(citys,
				{	dataFormater:cityDataFormater,
					hot_list:citys,
					dataContainer:'#editcityid',
					onSelect:triggerSchool,
					attachObject:'#suggest2'});
		$("#editcity").click();
	}
	
}

function triggerSchool()
{
	cityId=$("#editcityid").val();
	if(cityId!=0)
	{
		schools=getSchoolsByCityId(cityId);
		$("#editdrive_school").suggest(schools,{dataFormater:schoolDataFormater,hot_list:schools,dataContainer:'#editdrive_schoolid',onSelect:function(){},attachObject:'#suggest3'});
		$("#editdrive_school").click();
	}
	
}
//数据格式化
function provinceFormater(html,data,items)
{
	
	for (var i = 0; i < data.length; i++) 
	{
		var reg = new RegExp('^' + items + '.*$', 'im');
		if (reg.test(data[i].provinceid) || reg.test(data[i].province) || reg.test(data[i].hotkey)) 
		{
			html += '<li rel="' + data[i].provinceid+ '"><a href="#' + i + '">'+ data[i].province+ '<span>'+data[i].hotkey+'</span></a></li>';
		}
	}
	return html;
}
function cityDataFormater(html,data,items)
{
	for (var i = 0; i < data.length; i++) 
	{
		var reg = new RegExp('^' + items + '.*$', 'im');
		if (reg.test(data[i].cityid) || reg.test(data[i].city) || reg.test(data[i].hotkey)) 
		{
			html += '<li rel="' + data[i].cityid+ '"><a href="#' + i + '">'+ data[i].city+ '<span>'+data[i].hotkey+'</span></a></li>';
		}
	}
	return html;
}
function schoolDataFormater(html,data,items)
{
	for (var i = 0; i < data.length; i++) 
	{
		var reg = new RegExp('^' + items + '.*$', 'im');
		if (reg.test(data[i].schoolid) || reg.test(data[i].name)) 
		{
			html += '<li rel="' + data[i].schoolid+ '"><a href="#' + i + '">'+ data[i].name+ '<span>'+data[i].schoolid+'</span></a></li>';
		}
	}
	return html;
}




//获取省
function getAllProvinces()
{
	var action="getProvinceToJson.do";
	var data;
	var params=new Object();
	data=getData(action,params,false);
	return data;
}

//获取市
function getCitiesByProvinceId(id)
{
	var action="getCityByProvinceId.do";
	var data;
	var params=new Object();
	params.provinceid=id;
	data=getData(action,params,false);
	return data;
}

//获取驾校
function getSchoolsByCityId(id)
{
	var action="getschoollistByCityId.do";
	var data;
	var params=new Object();
	params.cityid=id;
	data=getData(action,params,false);
	if(data)
	{
		data=data.driveSchoollist;
	}
	return data;
}

//驾校模糊查询
function getSchoolsByKeyword(keyword,cityId)
{
	var action="";
	var data;
	var params=new Object();
	
	data=getData(action,params,true);
	return data;
}



//获取数据
/**
 * url:请求地址
 * params:参数  Object类型
 * async:是否异步 
 */
function getData(urlStr,params,async)
{
	var parameters="";
	//生成参数串
	if(params)
	{
		parameters="?";
		for(attrName in params)
		{
			if(parameters!="?")
			{
				parameters+="&";
			}
			var p=attrName+"="+params[attrName];
			parameters+=p;
		}
		urlStr+=parameters;
	}else
	{
		
	}
	
	var result;
	//请求数据
	 $.ajax({
	        async: async,
	        type : "GET",
	        url : urlStr,
	        success : function(data) {
	            result=data;
	        }
	    });
	return eval("("+result+")");
}