
//审核
function stuCheckPass(applyid,index,pageIndex,change_id) {
	if(confirm("确认通过审核？")){
		window.location.href="studentApplyCheckPass.do?applyid="+applyid+"&index="+index+"&pageIndex="+pageIndex+"&change_id="+change_id;
	}
}
//审核不通过
function stuCheckNoPass(applyid,index,pageIndex,change_id) {
	if(confirm("确认审核不通过？")){
		window.location.href="studentApplyCheckNoPass.do?applyid="+applyid+"&index="+index+"&pageIndex="+pageIndex+"&change_id="+change_id;
	}
}

function searchStudentApply(){
	var realname=$("#realname").val();
	var phone=$("#phone").val();
	var amount=$("#amount").val();
	var inputamount=$("#inputamount").val();
	var state = $("#state").val();
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
	window.location="getStudentApplyBySearch.do?searchname="+realname+"&searchphone="+phone+"&amount="+amount+"&inputamount="+inputamount+"&state="+state+"&minsdate="
	+minsdate+"&maxsdate="+maxsdate+"&index="+index+"&change_id="+change_id;
}



