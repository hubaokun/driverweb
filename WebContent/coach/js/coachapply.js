
//审核通过
function checkpass(applyid,index,pageIndex,change_id) {
	if(confirm("确认通过审核？")){
		window.location.href="applyCheckPass.do?applyid="+applyid+"&index="+index+"&pageIndex="+pageIndex+"&change_id="+change_id;
	}
}
//审核不通过
function checknopass(applyid,index,pageIndex,change_id) {
	if(confirm("确认不通过审核？")){
		window.location.href="applyCheckNoPass.do?applyid="+applyid+"&index="+index+"&pageIndex="+pageIndex+"&change_id="+change_id;
	}
}

//作废
function checkrevocation(applyid,index,pageIndex,change_id) {
	if(confirm("确认撤销？")){
		window.location.href="applyCheckrevocation.do?applyid="+applyid+"&index="+index+"&pageIndex="+pageIndex+"&change_id="+change_id;
	}
}

function checkall(){
	if($("input[name='checkall']").attr("checked")==true){
		$("input[name='checkbox']").attr("checked",true);
	}else{
		$("input[name='checkbox']").removeAttr("checked");
	}
	
}

function searchCoachApply(){
	var realname=$("#realname").val();
	var phone=$("#phone").val();
	var amount=$("#amount").val();
	var inputamount=$("#inputamount").val();
	var state = $("#state").val();
	var driveschoolid = $("#driveschoolid").val();
	var minsdate=$("#minsdate").val();
	var maxsdate=$("#maxsdate").val();
	if(minsdate!=''&&maxsdate!=''){
		if(minsdate>maxsdate){
			alert("结束时间必须在开始时间之后！");
			return;
		}
	}
	var index = $("#index").val();
	var change_id = $("#change_id").val();
	window.location="getCoachApplyBySearch.do?searchname="+realname+"&searchphone="+phone+"&amount="+amount+"&inputamount="+inputamount+"&minsdate="
	+minsdate+"&maxsdate="+maxsdate+"&index="+index+"&change_id="+change_id+"&schoolid="+driveschoolid+"&state="+state;
}



