//查询市
function tofindCity(v)
		{
			//发送异步请求
			var params = {provinceid:v};
			jQuery.post("getCityByProvinceId.do", params, showCity, 'json');
		}
		
		function showCity(obj)
		{
		         document.getElementById("city").length=0;
		         for(var i=0;i<obj.length;i++)
		         {
		         	var o=new Option(obj[i].city,obj[i].cityid);
		         	document.getElementById("city").add(o);
		         }
		    
		}
		//查询区
		function tofindArea(v)
		{
			//发送异步请求
			var params = {cityid:v};
			jQuery.post("getAreaByCityId.do", params, showArea, 'json');
		}
		
		function showArea(obj)
		{
		         document.getElementById("area").length=0;
		         for(var i=0;i<obj.length;i++)
		         {
		         	var o=new Option(obj[i].area,obj[i].areaid);
		         	document.getElementById("area").add(o);
		         }
		}
		
		//按热键查询
		function tofindCityByHotKey()
		{
			//发送异步请求
			var v=$("#hotkey").val();
			var params = {hotkey:v};
			jQuery.post("getCityByHotKey.do", params, showCityHotKey, 'json');
		}
		
		function showCityHotKey(obj)
		{
		         var html="";
		         for(var i=0;i<obj.length;i++)
		         {
		        	 html=html+obj[i].city+"<br>";
		         
		         }
		         $("#citys").html(html);
		}
	