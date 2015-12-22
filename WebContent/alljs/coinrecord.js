/**
 * Created by tutu on 15/7/24.
 */
function getSelfTop(){
    var h= document.documentElement.clientHeight;
    var height2 = (h-700)/2;
    var w= document.documentElement.clientWidth;
    var width=(w-600)/2;
    var width2=(w-500)/2;
    $("#mask_last").css("margin-left",width);
    $("#mask_last").css("top","10%");
    $("#edit_last").css("margin-left",width2);
    $("#edit_last").css("top",height2);
};

//添加驾校
function givingcoupon(id) {
    $("#couponid").val(id);
    $("#mask").show();
    $("#mask_sec").show();
    $("#mask_last").show();
}

function unshowaddschool() {
    $("#mask").hide();
    $("#mask_sec").hide();
    $("#mask_last").hide();
}


function turnaddcoupon(){
    window.location.href="./turnaddcoupon.do";
}

function delcoupon(id){
    if(confirm("确认删除该优惠券?")){
        $.ajax({
            url:"./delcoupon.do",
            type:"post",
            data:{"couponid":id,},
            success:function(msg){
                alert("删除成功!");
                window.location.reload();
            }
        });
    }
}

function cancelcoupon(id){
    if(confirm("确认作废该优惠券?")){
        $.ajax({
            url:"./cancelcoupon.do",
            type:"post",
            data:{"recordid":id},
            success:function(msg){
                alert("作废成功!");
                window.location.reload();
            }
        });
    }
}

//限制用户输入数字
function IsNum(e) {
    var k = window.event ? e.keyCode : e.which;
    if (((k >= 48) && (k <= 57)) || k == 8 || k == 0) {
    } else {
        if (window.event) {
            window.event.returnValue = false;
        }
        else {
            e.preventDefault(); // for firefox
        }
    }
}

function changeserachdiv(){
    //alert("changeserachdiv");
    var givingtype = $("input[name='givingtype']:checked").val();
    if(givingtype==1){
        $("#bigsearch").remove();
        $("#bigsearchcoach").remove();
        $("#textareaid").css("margin-top","10px");
        $("#somestudent").hide();
        $("#exslfile").css("display","none");
    }else if(givingtype==3){
        $("#bigsearch").remove();
        $("#bigsearchcoach").remove();
        $("#textareaid").css("margin-top","30px");
        $("#somestudent").show();
        $("#exslfile").css("display","block");

    }
    else if(givingtype==4){
        var html = "";
        html += '<div id="bigsearchcoach" style="width: 400px;margin-top:20px; margin-left: 50px;height: 60px;  display: block;">';
        html +=	'<div class="serchcontentdiv" style="width: 304px;margin-top: 0px;float: left;">';
        html += '<input class="searchdiv" value="教练名称" type="text" style="width: 100px;text-align: center;font-size: 12px;" readonly="readonly">';
        html += '<input id="searchcoachname"  onkeyup="searchCoach();" autocomplete="off" class="searchdiv" type="text" style="width: 202px;"></div>';
        html += '<div class="binding_detail" id="gartenDetail" ></div>';
        html +=	'<input type="hidden" name="searchcoachid" id="searchcoachid"></div>';
        $("#givingafter").before(html);
        $("#somestudent").hide();
        $("#exslfile").css("display","none");
        $("#bigsearch").remove();

    }
    else{
        var html = "";
        html += '<div id="bigsearch" style="width: 400px;margin-top:20px; margin-left: 50px;height: 60px;  display: block;">';
        html +=	'<div class="serchcontentdiv" style="width: 304px;margin-top: 0px;float: left;">';
        html += '<input class="searchdiv" value="学员名称或者手机" type="text" style="width: 100px;text-align: center;font-size: 12px;" readonly="readonly">';
        html += '<input id="searchname"  onkeyup="searchSuser();" autocomplete="off" class="searchdiv" type="text" style="width: 202px;"></div>';
        html += '<div class="binding_detail" id="gartenDetail" ></div>';
        html +=	'<input type="hidden" name="searchsuserid" id="searchsuserid"></div>';
        $("#givingafter").before(html);
        $("#somestudent").hide();
        $("#exslfile").css("display","none");
        $("#bigsearchcoach").remove();
    }
}

function addcoupongiving(){
    var re = /^[0-9]+.?[0-9]*$/; // 判断字符串是否为数字 //判断正整数 /^[1-9]+[0-9]*]*$/
    var nubmer = $("#pushnum").val();
    if (nubmer==''||!re.test(nubmer)||nubmer==0) {
        alert("发送数量请输入不为0的数字");
        $("#pushnum").val("");
        return false;
    }else{
        $("#alertform").attr("action","addcoupongiving.do").submit();
    }
}



function searchSuser(){
    var name = $("#receivername").val();
    if(name == ''){
        return;
    }
    $.ajax({
        url: "searchSuser.do",
        data:{cuserkeyword: name},
        type: "POST",
        success: function(msg){
            var obj = eval("("+msg+")");
            var suserlist = obj.suserlist;
            $("#optionalStudent").empty();
            $("#optionalStudent").show();
            for(var i = 0; i < suserlist.length; i++){
                var id = suserlist[i].studentid;
                var name = suserlist[i].realname;
                var phone = suserlist[i].phone;
                if(name==null){
                    name="未设置";
                    $("#optionalStudent").append(
                        '<div class="binding_detail_item" onclick="ssearchName('+id+', \''+name+'\')">'
                        +name+":"+phone
                        +'</div>'
                    );
                }else{
                    $("#optionalStudent").append(
                        '<div class="binding_detail_item" onclick="ssearchName('+id+', \''+name+'\')">'
                        +name+":"+phone
                        +'</div>'
                    );
                }
            }
        }
    });
}


function searchCoach(){
    var name = $("#searchcoachname").val();
    if(name == ''){
        return;
    }
    $.ajax({
        url: "searchCuser.do",
        data:{cuserkeyword: name},
        type: "POST",
        success: function(msg){
            var obj = eval("("+msg+")");
            var cuserlist = obj.cuserlist;
            $("#gartenDetail").empty();
            $("#gartenDetail").show();
            for(var i = 0; i < cuserlist.length; i++){
                var id = cuserlist[i].coachid;
                var name = cuserlist[i].realname;
                var phone = cuserlist[i].phone;
                $("#gartenDetail").append(
                    '<div class="binding_detail_item" onclick="searchcoachName('+id+', \''+name+'\')">'
                    +name+":"+phone+'</div>'
                );
            }
        }
    });
}


function ssearchName(id,name){

    $("#receiverid").val(id);
    $("#receivername").val(name);
    $("#optionalStudent").empty();
    $("#optionalStudent").hide();
    //alert($("#searchsuserid").val());
}

function searchcoachName(id,name){
    $("#searchcoachid").val(id);
    $("#searchcoachname").val(name);
    $("#gartenDetail").empty();
    $("#gartenDetail").hide();
}


function showsearchowner(){
    var ownertype = $("#ownertype").val();
    $("#ownerid").remove();
    $("#bigsearch").remove();
    if(ownertype==2){
        var html = "";
        html +='<div id="bigsearch" style="width: 100%; height: 75px; border-bottom: 1px solid #eaeff2;">';
        html +='<div style="float:left; width: 143px; height: 100%; line-height: 75px; border-right: 1px solid #eaeff2;text-align: right;">教练名称或手机<span style="color:#f83a22; margin-right:16px; margin-left: 8px;">*</span></div>';
        html +='<div style="height: 100%; line-height: 75px; float:left; margin-left: 20px;">';
        html +='<input value="" name="owenersearch" id="coachsearchname" onkeyup="searchCuser();" autocomplete="off"  required="required" style="width: 800px; height: 33px; padding-left:5px; border: 1px solid #eaeff2; margin-top: 20px;">';
        html +='</div><div class="binding_detail"  style="  left: 164px;top:-20px;clear:both;" id="gartenDetail" >';
        html +='</div><input type="hidden" id="ownerid" name="ownerid" required="required">';
        $("#searchbefore").before(html);
    }else if(ownertype==1){
        var html = "";
        html +='<div  id="bigsearch" style="width: 100%; height: 75px; border-bottom: 1px solid #eaeff2;">';
        html +='<div style="float:left; width: 143px; height: 100%; line-height: 75px; border-right: 1px solid #eaeff2;text-align: right;">驾校名称<span style="color:#f83a22; margin-right:16px; margin-left: 8px;">*</span></div>';
        html +='<div style="height: 100%; line-height: 75px; float:left; margin-left: 20px;">';
        html +='<input value="" name="owenersearch" id="searchname" onkeyup="searchDriveSchool();" autocomplete="off"  required="required" style="width: 800px; height: 33px; padding-left:5px; border: 1px solid #eaeff2; margin-top: 20px;">';
        html +='</div><div class="binding_detail" style="  left: 164px;top:-20px;clear:both;" id="gartenDetail" >';
        html +='</div><input type="hidden" id="ownerid" name="ownerid" required="required">';
        $("#searchbefore").before(html);
    }
}

var temp="";
function showsearchschoolowner(){
    var ownertype = $("#ownertype").val();
    //$("#ownerid").remove();
    $("#bigsearch").remove();
    if(temp==""){
    	temp=$("#bigsearch2");
    }
    $("#bigsearch2").remove();
    if(ownertype==2){
    	//$("#bigsearch2").hide();
        var html = "";
        html +='<div id="bigsearch" style="width: 100%; height: 75px; border-bottom: 1px solid #eaeff2;">';
        html +='<div style="float:left; width: 143px; height: 100%; line-height: 75px; border-right: 1px solid #eaeff2;text-align: right;">教练名称或手机<span style="color:#f83a22; margin-right:16px; margin-left: 8px;">*</span></div>';
        html +='<div style="height: 100%; line-height: 75px; float:left; margin-left: 20px;">';
        html +='<input value="" name="coachowenersearch" id="coachsearchname" onkeyup="searchCuser();" autocomplete="off"  required="required" style="width: 800px; height: 33px; padding-left:5px; border: 1px solid #eaeff2; margin-top: 20px;">';
        html +='</div><div class="binding_detail"  style="  left: 164px;top:-20px;clear:both;" id="gartenDetail" >';
        html +='</div><input type="hidden" id="ownerid" name="ownerid" required="required">';
        html +='</div>';
        $("#searchbefore").before(html);
    }
    else if(ownertype==1){
    	//$("#bigsearch2").show();
    	$("#searchbefore").before(temp);
    	/*
        var html = "";
        html +='<div  id="bigsearch" style="width: 100%; height: 75px; border-bottom: 1px solid #eaeff2;">';
        html +='<div style="float:left; width: 143px; height: 100%; line-height: 75px; border-right: 1px solid #eaeff2;text-align: right;">驾校名称<span style="color:#f83a22; margin-right:16px; margin-left: 8px;">*</span></div>';
        html +='<div style="height: 100%; line-height: 75px; float:left; margin-left: 20px;">';
        html +='<input value="" name="owenersearch" id="searchname" onkeyup="searchDriveSchool();" autocomplete="off"  required="required" style="width: 800px; height: 33px; padding-left:5px; border: 1px solid #eaeff2; margin-top: 20px;">';
        html +='</div><div class="binding_detail" style="  left: 164px;top:-20px;clear:both;" id="gartenDetail" >';
        html +='</div><input type="hidden" id="ownerid" name="ownerid" required="required">';
        $("#searchbefore").before(html);*/
    }
}


function searchDriveSchool(){
    var name = $("#searchname").val();
    if(name == ''){
        return;
    }
    $.ajax({
        url: "searchDriveSchool.do",
        data:{schoolkeyword: name},
        type: "POST",
        success: function(msg){
            var obj = eval("("+msg+")");
            var schoollist = obj.schoollist;
            $("#gartenDetail").empty();
            $("#gartenDetail").show();
            for(var i = 0; i < schoollist.length; i++){
                var id = schoollist[i].schoolid;
                var name = schoollist[i].name;
                $("#gartenDetail").append(
                    '<div class="binding_detail_item" onclick="searchOwner2('+id+', \''+name+'\')">'
                    +name+'</div>'
                );
            }
        }
    });
}

function searchCuser(){
    var name = $("#coachsearchname").val();
    if(name == ''){
        return;
    }
    $.ajax({
        url: "searchCuser.do",
        data:{cuserkeyword: name},
        type: "POST",
        success: function(msg){
            var obj = eval("("+msg+")");
            var cuserlist = obj.cuserlist;
            $("#gartenDetail").empty();
            $("#gartenDetail").show();
            for(var i = 0; i < cuserlist.length; i++){
                var id = cuserlist[i].coachid;
                var name = cuserlist[i].realname;
                var phone = cuserlist[i].phone;
                $("#gartenDetail").append(
                    '<div class="binding_detail_item" onclick="searchOwner('+id+', \''+name+'\')">'
                    +name+":"+phone
                    +'</div>'
                );
            }
        }
    });
}

function searchOwner(id,name){
    $("#ownerid").val(id);
    $("#coachsearchname").val(name);
    $("#gartenDetail").empty();
    $("#gartenDetail").hide();
}
function searchOwner2(id,name){
    $("#ownerid").val(id);
    $("#searchname").val(name);
    $("#gartenDetail").empty();
    $("#gartenDetail").hide();
}


function selectall() {
    var aa = $("#allcheck").val();
    if (aa == 0) {
        var a = document.getElementsByName("checkbox");
        for (var i = 0; i < a.length; i++) {
            a[i].checked = true;
        }
        $("#allcheck").val("1");
    }
    if (aa == 1) {
        var a = document.getElementsByName("checkbox");
        for (var i = 0; i < a.length; i++) {
            a[i].checked = false;
        }
        $("#allcheck").val("0");
    }
}





function grantCoinRecord(){
    $("#grantCoinRecordButton").attr("disabled",true);
    $("#grantCoinRecordButton").css("background-color", "#d6d6d6");//改变背景色
    var ownertype = $("#ownertype").val();
    if(ownertype=="-1"){
    	alert("请选择发放类型");
    	return false;
    }
    var id = $("#receiverid").val();
    if(id == ''){
    	alert("学员信息不能为空");
        return false;
    }
    var coinnum = $("#coinnum").val();
    var receiverid = $("#receiverid").val();
    var ownerid = $("#ownerid").val();
    //var schoolownerid = $("#schoolownerid").val();
    var ownertype = $("#ownertype").val();
    //alert(receiverid+" "+ownerid+" "+coinnum+" "+ownertype+" "+schoolownerid);
    // if(ownertype==1){
    $.ajax({
        url: "grantCoinRecord.do",
        data:{ownerid: ownerid,receiverid:receiverid,coinnum:coinnum,ownertype:ownertype},
        type: "POST",
        success: function(msg){
            //alert(msg);
            var obj = eval("("+msg+")");
            var code = obj.code;
            if(code==1)
            {
                alert("添加成功!");
                $("#grantCoinRecordButton").css("background-color", "#4cc2ff");//改变背景色
            }
            else
            {
                alert("添加失败!");
                $("#grantCoinRecordButton").css("background-color", "#4cc2ff");//改变背景色
            }
            $("#receivername").val('');
        },
        error:function(msg)
        {
            alert("添加失败,请重试!");
            $("#grantCoinRecordButton").css("background-color", "#4cc2ff");//改变背景色
        }
    });
    //}
    /*if(ownertype==2 || ownertype==0){
    $.ajax({
        url: "grantCoinRecord.do",
        data:{ownerid: ownerid,receiverid:receiverid,coinnum:coinnum,ownertype:ownertype},
        type: "POST",
        success: function(msg){
            //alert(msg);
            var obj = eval("("+msg+")");
            var code = obj.code;
            if(code==1)
                alert("添加成功!");
            else
                alert("添加失败!");
            $("#receivername").val('');
        },
        error:function(msg)
        {
            alert("添加失败,请重试!");
        }
    });
    }*/
}

function goCoinRecord(){
	 $("#addcoinrecordForm").attr("action","goCoinRecord.do").submit();
}
function goSchoolCoinRecord(){
	 $("#addcoinrecordForm").attr("action","goSchoolCoinRecord.do").submit();
}
//回收小巴币
function reclaimCoin(v1,v2)
{
	if(confirm("确认要回收"+v1+"的所有小巴币吗?")){
		window.location.href="reclaimCoin.do?receiverid="+v2;
	}
}

//回收驾校发放小巴币
function reclaimSchoolCoin(v1,v2){
	if(confirm("确认要回收"+v1+"的所有小巴币吗?")){
		window.location.href="reclaimSchoolCoin.do?receiverid="+v2;
	}
}
