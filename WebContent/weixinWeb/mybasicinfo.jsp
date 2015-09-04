<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>基本信息</title>
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="css/font-awesome.css" rel="stylesheet" />
<link href="mobiscroll/css/mobiscroll.animation.css" rel="stylesheet" type="text/css" />
<link href="mobiscroll/css/mobiscroll.widget.css" rel="stylesheet" type="text/css" />
<link href="mobiscroll/css/mobiscroll.widget.ios.css" rel="stylesheet" type="text/css" />
<link href="mobiscroll/css/mobiscroll.scroller.css" rel="stylesheet" type="text/css" />
<link href="mobiscroll/css/mobiscroll.scroller.ios.css" rel="stylesheet" type="text/css" />
<link href="css/basicinfo.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.mbsc-ios .dw-sel {
  color: #1798f2;
}
.mbsc-ios .dwwc {
  padding: 30px 0px 10px 40px;
}
.mbsc-ios .dwb
{
	color: #1798f2;
}
</style>
</head>

<body>
	<div class="container">
    	<div class="row basic-head">
        	<div style="border-bottom:1px solid #eee">
            	<div class="col-md-3 col-sm-3 col-xs-3">
            	<img src="images/person-one.png" class="img-responsive img-circle center-block" />
            </div>	
            <div class="col-md-9 col-sm-9 col-xs-9">
            	<label for="avart_img"><i class="icon icon-chevron-right basic-icon"></i></label>
                <input type="file" id="avart_img" style="display:none;"  />
            </div>
            <div class="clearfix"></div>
            </div>
            <div class="col-md-12 col-sm-12 col-xs-12">
            	<p><span>真实姓名：</span><input type="text"  placeholder="真实姓名"/></p>
            </div>
            <div class="col-md-12 col-sm-12 col-xs-12">
            	<p><span>联系电话：</span><input type="text" /></p>
            </div>
        </div>
        <div class="row basic-data">
        	<div class="col-md-12 col-sm-12 col-xs-12">
            	<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;性别：<span><input type="text"  placeholder="性别"/></span></p>
            </div>
            <div class="col-md-12 col-sm-12 col-xs-12">
            	<p>出生年月：<span><input type="text" placeholder="出生年月" id="birthday" /></span></p>
            </div>
            <div class="col-md-12 col-sm-12 col-xs-12">
            	<p>所在城市：<span><input type="text" placeholder="入所在城市" /></i></span></p>
            </div>
        </div>   
        <div class="row mylearninfo-submit">
  	<div class="col-md-12 col-sm-12 col-xs-12">
    	<span class="save-data">保存信息</span>
    </div>
  </div>  
    </div>
    
    
<div class="overlay">
      <div class="overlay-content">
        <div class="container">
            <div class="row">
              <div class="col-md-12 col-sm-12 col-xs-12">
                  <span>是否保存资料的编写？</span>
              </div>
              <div class="no col-md-6 col-sm-6 col-xs-6" style="border-right:1px solid rgb(218,218,218);"><span class=" grey">否</span></div>
              <div class="yes col-md-6 col-sm-6 col-xs-6"><span class="blue">是</span></div>
              
        </div>
      </div>
  </div>	
</div>    
<script src="js/jquery-1.8.3.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="mobiscroll/js/mobiscroll.core.js"></script>
<script src="mobiscroll/js/mobiscroll.widget.js"></script>
<script src="mobiscroll/js/mobiscroll.scroller.js"></script>
<script src="mobiscroll/js/mobiscroll.util.datetime.js"></script>
<script src="mobiscroll/js/mobiscroll.datetimebase.js"></script>
<script src="mobiscroll/js/mobiscroll.i18n.zh.js"></script>
<script src="mobiscroll/js/mobiscroll.widget.ios.js"></script>
<!--<script src="js/bootstrap-datepicker.js"></script>-->
<script>
$(document).ready(function ()
{
	$("#avart_img").change(function ()
	{
		var img_v = $(this).val();
		//alert (img_v);
		$("#avartimg").src(img_v);
	});
	
	$('.save-data').on('click',function()
	{
		$('.overlay').css('display','block');
	})
	$('.yes,.no').on('click',function()
	{
		$('.overlay').css('display','none');
	})
	
	var height = $(window).height();
	var width = $(window).width();
	//alert (height);
	var h = (height-90)/2;
	var w = (width-296)/2;
	$('.overlay-content').css('top',h);
	$('.overlay-content').css('left',w);
	

	 
	//时间控件的调用
//	 $("#birthday").datepicker({
//	 });
	
});
</script>
<script type="text/javascript">
        $(function () {
			var nowData=new Date();
	        var opt= { 
	        	theme:'ios', //设置显示主题 
                mode:'scroller', //设置日期选择方式，这里用滚动
                display:'bottom', //设置控件出现方式及样式
                preset : 'date', //日期:年 月 日 时 分
                minDate: new Date(1916,1,1), 
				maxDate:new Date(nowData.getFullYear(),12,31),
//              dateFormat: 'yy-mm-dd', // 日期格式
//              dateOrder: 'yymmdd', //面板中日期排列格式
                stepMinute: 5, //设置分钟步长
                yearText:'年', 
                monthText:'月',
                dayText:'日',
                hourText:'时',
                minuteText:'分',
                lang:'zh' //设置控件语言};
            };
            $('#birthday').mobiscroll(opt);
        });
    </script>	
</body>
</html>
