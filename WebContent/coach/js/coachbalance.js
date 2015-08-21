function searchCoachBalance(){
	var realname=$("#realname").val();
	var phone=$("#phone").val();
	var amount=$("#amount").val();
	var inputamount=$("#inputamount").val();
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
	window.location="getCoachBalanceBySearch.do?searchname="+realname+"&searchphone="+phone+"&amount="+amount+"&inputamount="+inputamount+"&minsdate="
	+minsdate+"&maxsdate="+maxsdate+"&index="+index+"&change_id="+change_id;
}
//根据关键词搜索历史提现信息，通过二次审核的，由财务发起
function searchCoachBalanceFinance(){
	var realname=$("#realname").val();
	var phone=$("#phone").val();
	var amount=$("#amount").val();
	var inputamount=$("#inputamount").val();
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
	window.location="getCoachBalanceListFinance.do?searchname="+realname+"&searchphone="+phone+"&amount="+amount+"&inputamount="+inputamount+"&minsdate="
	+minsdate+"&maxsdate="+maxsdate+"&index="+index+"&change_id="+change_id;
}