/**
 * Created by tutu on 15/7/26.
 */
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




function clickClear(target)
{
    //if(target.value!='')
    //{
    //    target.style.color="#B0B0B0";
    //    target.value="请输入手机号码";
    //}
    //else
    //if(target.value=="请输入手机号码")
    if(target.value!="")
    {
        //target.style.color="#000000";
        target.value="";
    }
}

function searchSuser(){
    var name = $("#studentname").val();
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
                        '<div class="binding_detail_item" onclick="searchStudentName('+id+', \''+name+'\')">'
                        +name+":"+phone
                        +'</div>'
                    );
                }else{
                    $("#optionalStudent").append(
                        '<div class="binding_detail_item" onclick="searchStudentName('+id+', \''+name+'\')">'
                        +name+":"+phone
                        +'</div>'
                    );
                }
            }
        }
    });
}


function searchOldCoach(){
    var name = $("#oldcoachname").val();
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
            $("#optionaloldcoach").empty();
            $("#optionaloldcoach").show();
            for(var i = 0; i < cuserlist.length; i++){
                var id = cuserlist[i].coachid;
                var name = cuserlist[i].realname;
                var phone = cuserlist[i].phone;
                $("#optionaloldcoach").append(
                    '<div class="binding_detail_item" onclick="searchOldCoachName('+id+', \''+name+'\')">'
                    +name+":"+phone+'</div>'
                );
            }
        }
    });
}




function searchNewCoach(){
    var name = $("#newcoachname").val();
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
            $("#optionalnewcoach").empty();
            $("#optionalnewcoach").show();
            for(var i = 0; i < cuserlist.length; i++){
                var id = cuserlist[i].coachid;
                var name = cuserlist[i].realname;
                var phone = cuserlist[i].phone;
                $("#optionalnewcoach").append(
                    '<div class="binding_detail_item" onclick="searchNewCoachName('+id+', \''+name+'\')">'
                    +name+":"+phone+'</div>'
                );
            }
        }
    });
}






function searchStudentName(id,name){

    $("#studentid").val(id);
    $("#studentname").val(name);
    $("#optionalStudent").empty();
    $("#optionalStudent").hide();
    //alert($("#searchsuserid").val());
}

function searchOldCoachName(id,name){
    $("#oldcoachid").val(id);
    $("#oldcoachname").val(name);
    $("#optionaloldcoach").empty();
    $("#optionaloldcoach").hide();
}




function searchNewCoachName(id,name){
    $("#newcoachid").val(id);
    $("#newcoachname").val(name);
    $("#optionalnewcoach").empty();
    $("#optionalnewcoach").hide();
}




function changCoach(){
    var studentid = $("#studentid").val();
    var oldcoachid = $("#oldcoachid").val();
    var newcoachid = $("#newcoachid").val();
    if(studentid == '' || oldcoachid=='' || newcoachid==''){
        return;
    }

    $.ajax({
        url: "changeCoach.do",
        data:{studentid: studentid,oldcoachid:oldcoachid,newcoachid:newcoachid},
        type: "POST",
        success: function(msg){
            //alert(msg);
            var obj = eval("("+msg+")");
            var code = obj.code;
            if(code==1)
                alert("修改成功!");
            else
                alert("修改失败!");
        },
        error:function(msg)
        {
            alert("修改失败,请重试!");
        }
    });
}


