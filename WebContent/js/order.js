
function searchOrder(){
	var coachphone=$("#coachphone").val();
	if(coachphone!=''){
		var regu =/^(?:13\d|15[0123456789]|12[0123456789]|11[0123456789]|14[0123456789]|16[0123456789]|17[0123456789]|19[0123456789]|18[0123456789]|17[0123456789])-?\d{5}(\d{3}|\*{3})$/;
		if(!regu.test(coachphone)){
			alert("电话格式不正确！");
			return;
		}
	}
	var studentphone=$("#studentphone").val();
	if(studentphone!=''){
		var regu =/^(?:13\d|15[0123456789]|12[0123456789]|11[0123456789]|14[0123456789]|16[0123456789]|17[0123456789]|19[0123456789]|18[0123456789]|17[0123456789])-?\d{5}(\d{3}|\*{3})$/;
		if(!regu.test(studentphone)){
			alert("电话格式不正确！");
			return;
		}
	}
	var startminsdate=$("#startminsdate").val();
	var startmaxsdate=$("#startmaxsdate").val();
	if(startminsdate!=''&&startmaxsdate!=''){
		if(startminsdate>startmaxsdate){
			alert("结束时间必须在开始时间之后！");
			return;
		}
	}
	var endminsdate=$("#endminsdate").val();
	var endmaxsdate=$("#endmaxsdate").val();
	if(endminsdate!=''&&endmaxsdate!=''){
		if(endminsdate>endmaxsdate){
			alert("结束时间必须在开始时间之后！");
			return;
		}
	}
	var state=$("#state").val();
	var ordertotal = $("#ordertotal").val();
	var inputordertotal = $("#inputordertotal").val();
	var ishavacomplaint = $("#ishavacomplaint").val();
	var index = $("#index").val();
	var change_id = $("#change_id").val();
	var t_paytype=$("#t_paytype").val();
	window.location.href="getOrderList.do?coachphone="+coachphone+"&studentphone="+studentphone+"&startminsdate="+startminsdate+"&startmaxsdate="+startmaxsdate+
	"&endminsdate="+endminsdate+"&endmaxsdate="+endmaxsdate+"&state="+state+"&ordertotal="+ordertotal+"&inputordertotal="+inputordertotal+"&ishavacomplaint="+ishavacomplaint+"&t_paytype="+t_paytype+"&index="+index+"&change_id="+change_id;
}