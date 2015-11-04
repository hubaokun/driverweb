
//上一页
function previousPage(page,act){
	 window.location.href = act+"pageIndex="+(page-1);
}

//下一页
function nextPage(page,act){ 
	window.location.href = act+"pageIndex="+(page+1); 
}
//跳转到
function gotoPage(act,cnt){
	//alert(act);
	//alert(cnt);
	var topage = $("#topage").val();
	var re = /^[0-9]*$/;
	if(topage == 0 || topage == ''){
		$("#page_msg").html("请输入大于0的页数！");  
		$("#topage").val('');
		$("#topage").focus();
	}else if(parseInt(topage)>cnt){ 
		$("#page_msg").html("跳转页大于总页数！");  
		$("#topage").val('');
		$("#topage").focus();
	}else if(!re.test(topage)){
		$("#page_msg").html("请输入数字！");  
		$("#topage").val('');
		$("#topage").focus();
	}else{
		window.location.href = act+"pageIndex="+topage;
	}
	
}

function goPage(act,cnt){
	window.location.href = act+"pageIndex="+cnt;
}
function IsNum(e) {
    var k = window.event ? e.keyCode : e.which;
    if (((k >= 48) && (k <= 57)) || k == 8 || k == 0) {
    } else {
        if (window.event) {
            window.event.returnValue = false;
        }
        else {
            e.preventDefault(); //for firefox 
        }
    }
} 