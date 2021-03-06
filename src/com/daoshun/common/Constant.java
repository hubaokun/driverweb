/**
 * 
 */
package com.daoshun.common;

/**
 * @author wangcl
 * 
 */
public class Constant {

	public static final String CODE = "code";
	public static final String MESSAGE = "message";
	public static final String EXCEPTION = "{\"message\":\"网络状况不佳，请稍后再试\",\"code\":100}";
	// 接口返回 未知错误
	public static final int kInfoCode_unknown_Error = 199;
	public static final String kInfoMsg_unknown_Error = "未知错误";

	public static final String LOGIN = "LOGIN";

	public static final String SAPPID = "DsO5qgFSbrATz16Tv8Bpz4";

	public static final String SAPPKEY = "FhgG3ERZzi6goLAGp911p4";

	public static final String SMASTER = "Hge5iI6Qrd8nJRWMjV3ym8";

	public static final String SHOST = "http://sdk.open.api.igexin.com/apiex.htm";

	public static final String CAPPID = "ACWCLzCDrb7Y5MkA4XWtm4";

	public static final String CAPPKEY = "davVrxDsKZ7xvCxbPedb41";

	public static final String CMASTER = "JJtxmlHJJX99zDe8QUfVv2";

	public static final String CHOST = "http://sdk.open.api.igexin.com/apiex.htm";

	/****************** 后台分页查询 *******************/
	public static final int USERLIST_SIZE = 10;
	public static final int ORDERLIST_SIZE = 10;// 订单每页条数
	public static final int EXAMINATION_SIZE = 20;// 订单每页条数

	/****************** 后台分页查询 *******************/

	/****************** 教练端字段定义 *******************/

	public static final int CUSER_UNCOMPLETE = 0;
	/** 非教练当前使用地址 **/
	public static final int COACHCURRENTADDRESS0 = 0;
	/** 教练当前使用地址 **/
	public static final int COACHCURRENTADDRESS1 = 1;
	/** 教练申请未完成状态 **/
	public static final int COCAHAPPLY_UNCOMPLETE = 0;

	/** 教练离职状态 **/
	public static final int COCAH_QUIT = 1;

	/** 分页是每页的显示量 **/
	public static final int COUNT_NUM = 10;
	/** 消息读取状态：已读 **/
	public static final int NOTICE_READ = 1;

	/****************** 教练端字段定义 *******************/

	/****************** 学生端接口定义 *******************/
	// 学员注册
	public static final String SREGISTER = "REGISTER";

	// 获取验证码
	public static final String GETVERCODE = "GETVERCODE";

	// 验证验证码有效性
	public static final String VERIFICATIONCODE = "VERIFICATIONCODE";

	// 学员登录
	public static final String SLOGIN = "LOGIN";
	
	// 学员登录
	public static final String SENROLL = "ENROLL";

	public static final String ThirdLogin = "THIRDLOGIN";

	// 第三方账号绑定已有账号
	public static final String BINDACCOUNT = "BINDACCOUNT";

	// 第三方账号绑定已有账号
	public static final String BINDNEWACCOUNT = "BINDNEWACCOUNT";

	// 完善账号信息
	public static final String PERFECTACCOUNTINFO = "PERFECTACCOUNTINFO";

	// 修改头像
	public static final String CHANGEAVATAR = "CHANGEAVATAR";

	// 完善学员驾驶信息
	public static final String PERFECTSTUDENTINFO = "PERFECTSTUDENTINFO";

	// 完善个人资料
	public static final String PERFECTPERSONINFO = "PERFECTPERSONINFO";

	// 修改密码验证原密码
	public static final String VERIFYPSW = "VERIFYPSW";

	// 修改密码
	public static final String CHANGEPSW = "FindPsw";

	// 获取账户余额信息
	public static final String GETMYBALANCEINFO = "GETMYBALANCEINFO";

	// 获取账户小巴币信息
	public static final String GETMYCOINRECORD = "GETMYCOINRECORD";
	
	public static final String GETCOACHCOINAFFILIATION = "GETCOACHCOINAFFILIATION";

	//获取学员钱包页面所需的附加信息：券的张数，小巴币的
	public static final String GETSTUDENTWALLETINFO="GETSTUDENTWALLETINFO";
	
	/*public static final String PROMOENROLLCALLBACK="PROMOENROLLCALLBACK";*/
	
	//获取学员小巴券列表
	public static final String GETSTUDENTCOUPONLIST="GETSTUDENTCOUPONLIST";

	//获取学员小巴币变动记录getCoinAffiliation
	public static final String GETSTUDENTCOINRECORDLIST="GETSTUDENTCOINRECORDLIST";
	
	public static final String GETCOINAFFILIATION="GETCOINAFFILIATION";
	//获取学员基本信息
	public static final String GETSTUDENTINFO="GETSTUDENTINFO";
	//获取学员基本信息
	public static final String GETFREECOURSESTATE="GETFREECOURSESTATE";
	
	//显示客户端筛选的按 科目筛选的文字
	public static final String GETQUERYSUBJECT="GETQUERYSUBJECT";
	
	//学员小巴币异常查询
	public static final String COINEXCEPTION="COINEXCEPTION";
	
	//学员余额查询测试
	public static final String TESTSTUDENTMONEY="TESTSTUDENTMONEY";
	
	//学员余额查询测试
	public static final String FINDSTUDENTMONEYEX="FINDSTUDENTMONEYEX";
	
	//教练余额查询测试
	public static final String TESTCOACHMONEY="TESTCOACHMONEY";
	
	
	//教练余额异常查询
	public static final String TESTCOACHMONEYEX="TESTCOACHMONEYEX";
	
	//学员小巴币查询测试
	public static final String TESTSTUDENTCOIN="TESTSTUDENTCOIN";
	
	//教练小巴币查询测试
	public static final String TESTCOACHCOIN="TESTCOACHCOIN";
	
	//教练小巴币异常查询
	public static final String TESTCOACHCOINEX="TESTCOACHCOINEX";
	
	//查询学员小巴币异常
	public static final String FINDSTUDENTCOINEX="FINDSTUDENTCOINEX";
	
	//教练学员总订单额
	public static final String GETSETTLEORDERTOTAL="GETSETTLEORDERTOTAL";
	
	//教练学员总订单小时数
	public static final String GETSETTLEORDERTIME="GETSETTLEORDERTIME";
	
	//教练学员总订单可用小巴券数
	public static final String GETCANUSECOUPONNUM="GETCANUSECOUPONNUM";
	
	// 申请提现
	public static final String APPLYCASH = "APPLYCASH";

	// 账户充值
	public static final String RECHARGE = "RECHARGE";
	public static final String GETENROLLINFO = "GETENROLLINFO";
	//促销报名
	public static final String PROMOENROLL = "PROMOENROLL";
	// 准教车型										  PERFECTCOACHMODELID
	public static final String PERFECTCOACHMODELID = "PERFECTCOACHMODELID";
	// 获取通知列表
	public static final String GETNOTICES = "GETNOTICES";

	// 删除通知
	public static final String DELNOTICE = "DELNOTICE";

	

	// 获取未读消息条数
	public static final String GETMESSAGECOUNT = "GETMESSAGECOUNT";

	// 意见反馈
	public static final String FEEDBACK = "FEEDBACK";

	// 获取被投诉列表
	public static final String GETCOMPLAINTTOMY = "GETCOMPLAINTTOMY";

	// 取得我的投诉列表
	public static final String GETMYCOMPLAINT = "GETMYCOMPLAINT";

	// 取得未完成订单
	public static final String GETUNCOMPLETEORDER = "GETUNCOMPLETEORDER";
	// 取得被投诉订单
	public static final String GETCOMPLAINTORDER  = "GETCOMPLAINTORDER";
	// 取得待处理订单
	public static final String GETWAITDEALWITHORDER  = "GETWAITDEALWITHORDER";

	// 取得待评价订单
	public static final String GETWAITEVALUATIONORDER = "GETWAITEVALUATIONORDER";

	// 取得已完成订单
	public static final String GETCOMPLETEORDER = "GETCOMPLETEORDER";

	// 取得订单详细
	public static final String GETORDERDETAIL = "GETORDERDETAIL";

	// 获取投诉的原因
	public static final String GETCOMPLAINTREASON = "GETCOMPLAINTREASON";

	// 投诉
	public static final String COMPLAINT = "COMPLAINT";

	// 取消订单投诉
	public static final String CANCELCOMPLAINT = "CANCELCOMPLAINT";

	// 确认上车
	public static final String CONFIRMON = "CONFIRMON";

	// 确认下车
	public static final String CONFIRMDOWN = "CONFIRMDOWN";

	// 取消订单
	public static final String CANCELORDER = "CANCELORDER";
	
	// 教练同意取消订单
	public static final String CANCELORDERAGREE = "CANCELORDERAGREE";

	// 获取教练详细
	public static final String GETCOACHDETAIL = "GETCOACHDETAIL";

	// 刷新教练日程安排
	public static final String REFRESHCOACHSCHEDULE = "REFRESHCOACHSCHEDULE";

	// 获取附近教练
	public static final String GETNEARBYCOACH = "GETNEARBYCOACH";

	// 获取教练列表
	public static final String GETCOACHLIST = "GETCOACHLIST";

	// 预定教练
	public static final String BOOKCOACH = "BOOKCOACH";

	/****************** 教练端接口定义 *******************/
	// 注册
	public static final String CREGISTER = "REGISTER";

	// 登录
	public static final String CLOGIN = "LOGIN";

	// 完善教练账号信息
	public static final String CPERFECTACCOUNTINFO = "PERFECTACCOUNTINFO";

	// 修改头像
	public static final String CCHANGEAVATAR = "CHANGEAVATAR";

	// 完善教练个人信息
	public static final String CPERFECTPERSONINFO = "PERFECTPERSONINFO";

	// 得到准教车型
	public static final String CGETCARMODEL = "GETCARMODEL";

	// 找回原密码
	public static final String CFINDPSW = "FINDPSW";

	// 修改密码验证原密码
	public static final String CVERIFYPSW = "VERIFYPSW";

	// 修改密码，后自动更新
	public static final String CCHANGEPSW = "CHANGEPSW";

	// 完善教练资格资料
	public static final String CPERFECTCOACHINFO = "PERFECTCOACHINFO";

	// 设置教练单价
	public static final String CSETPRICE = "SETPRICE";

	// 添加地址
	public static final String CADDADDRESS = "ADDADDRESS";

	// 删除地址
	public static final String CDELADDRESS = "DELADDRESS";

	// 设置当前使用地址
	public static final String CSETCURRENTADDRESS = "SETCURRENTADDRESS";

	// 根据coachid获取他的所有地址
	public static final String CGETALLADDRESS = "GETALLADDRESS";

	// 申请提现
	public static final String CAPPLYCASH = "APPLYCASH";

	// 获取通知
	public static final String CGETNOTICES = "GETNOTICES";

	// 删除通知
	public static final String CDELNOTICE = "DELNOTICE";
	
	// 修改通知状态为已读
	public static final String READNOTICE = "READNOTICE"; 

	// 取的投诉我的列表
	public static final String CGETCOMPLAINTTOMY = "GETCOMPLAINTTOMY";

	// 取得我投诉的列表
	public static final String CGETMYCOMPLAINT = "GETMYCOMPLAINT";

	// 获取评价我的列表
	public static final String CGETEVALUATIONTOMY = "GETEVALUATIONTOMY";

	// 获得我的评价列表
	public static final String CGETMYEVALUATION = "GETMYEVALUATION";

	// 改变消息提醒设置
	public static final String CCHANGESET = "CHANGESET";

	// 获取消息条数
	public static final String CGETMESSAGECOUNT = "GETMESSAGECOUNT";

	// 取得教练今天日程安排
	public static final String CGETSCHEDULE = "GETSCHEDULE";
	
	// 根据日期取得教练日程安排
	public static final String CGETSCHEDULEBYDATE = "GETSCHEDULEBYDATE";
	
	// 修改某天的休息时间
	public static final String CSETDATETIME = "SETDATETIME";

	// 修改订单式是否可以取消
	public static final String CCHANGEORDERCANCEL = "CHANGEORDERCANCEL";

	public static final String SETDEFAULT = "SETDEFAULT";

	// 改变某一天的全天安排
	public static final String CCHANGEALLDAYSCHEDULE = "CHANGEALLDAYSCHEDULE";

	// 获得还未进行的的任务列表
	public static final String CGETNOWTASK = "GETNOWTASK";

	// 取得我的历史列表
	public static final String CGETHISTASK = "GETHISTASK";

	// 教练确认上车
	public static final String CCONFIRMON = "CONFIRMON";

	// 教练确认下车
	public static final String CCONFIRMDOWN = "CONFIRMDOWN";

	// 教练对任务进行评价
	public static final String CEVALUATIONTASK = "EVALUATIONTASK";

	// 教练对学生确认
	public static final String CSTUDENTCHECK = "STUDENTCHECK";

	// 设置默认教学科目
	public static final String CSETSUBJECT = "SETSUBJECT";

	// 获得所有教学科目
	public static final String CGETALLSUBJECT = "GETALLSUBJECT";

	// 推送
	public static final String SUPDATEPUSHINFO = "UPDATEPUSHINFO";

	public static final String CREFRESHUSERMONEY = "REFRESHUSERMONEY";

	public static final String UPDATEUSERLOCATION = "UPDATEUSERLOCATION";

	public static final String CHECKVERSION = "CHECKVERSION";
	
	public static final String CHECKCONFIG = "CHECKCONFIG";

	// 获取教练教学用车车型
	public static final String CGETALLTEACHCARMODEL = "GETALLTEACHCARMODEL";

	// 获取教练评论
	public static final String GETCOACHCOMMENTS = "GETCOACHCOMMENTS";
	//学员对某个教练的评论历史记录
	public static final String GETCOMMENTSFORSTUDENT = "GETCOMMENTSFORSTUDENT";
	// 获取小巴券列表
	public static final String GETCOUPONLIST = "GETCOUPONLIST";
	// 获取历史小巴券列表
	public static final String GETHISCOUPONLIST = "GETHISCOUPONLIST";

	// 获取可以使用的小巴券列表
	public static final String GETCANUSECOUPONLIST = "GETCANUSECOUPONLIST";

	// 获取可以使用的小巴币个数
	public static final String GETCANUSECOINSUM = "GETCANUSECOINSUM";
	//提醒教练开课
	public static final String REMINDCOACH = "REMINDCOACH";
	//城市套餐价格
	public static final String GETMODELPRICE = "GETMODELPRICE";
	//城市套餐价格
	public static final String GETOPENMODELPRICE = "GETOPENMODELPRICE";
	public static final String GETDRIVERSCHOOLBYCITYNAME = "GETDRIVERSCHOOLBYCITYNAME";
	// 获取所有驾校信息
	public static final String CGETALLSCHOOL = "GETALLSCHOOL";
	// 设置支付宝账户
	public static final String CHANGEALIACCOUNT = "CHANGEALIACCOUNT";
	// 获取教练的所有小巴券列表
	public static final String GETALLCOUPON = "GETALLCOUPON";
	// 教练申请小巴券套现
	public static final String APPLYCOUPON = "APPLYCOUPON";

	// 教练申请小巴币套现
	public static final String APPLYCOIN = "APPLYCOIN";

	public static final String GETMYALLSTUDENT = "GETMYALLSTUDENT";
	
	public static final String DELALIACCOUNT = "DELALIACCOUNT";
	public static final String CHANGEAPPLYTYPE = "CHANGEAPPLYTYPE";
	
	//提现手续费说明
	public static final String CASHEXPLAIN = "CASHEXPLAIN";
    //获取推荐人员列表
	public static final String CGETRECOMMENDLIST = "CGETRECOMMENDLIST";
	//增加推荐人员
	public static final String CHEAKINVITECODE ="CHEAKINVITECODE";
    //自动获取当前省市区信息
	public static final String GETAUTOPOSITION="GETAUTOPOSITION";
	
	//
	public static final String GETADDRESSURL="GETADDRESSURL";
	
	//获取热门城市
	public static final String GETHOTCITY="GETHOTCITY";
	
	//小巴服务接口
	public static final String XIAOBASERVICE ="XIAOBASERVICE";

	//广告服务接口
	public static final String GETADVERTISEMENT ="GETADVERTISEMENT";
	
	// 获取默认日程设置
    public static final String GETDEFAULTSCHEDULE = "GETDEFAULTSCHEDULE";

	//获取某个教练有关联的学员
    public static final String GETCOACHSTUDENT="GETCOACHSTUDENT";
    
    //获取学员可用小巴券数，剩余数
    public static final String GETSTUDENTCOUPON="GETSTUDENTCOUPON";
    
    //教练发放小巴券
    public static final String GRANTCOUPON="GRANTCOUPON";
    
   //获取教练发放小巴券记录
    public static final String GETCOACHCOUPONLIST="GETCOACHCOUPONLIST";
    
    //获取教练是否有小巴券发放权限
    public static final String GETCOACHCOUPONLIMIT="GETCOACHCOUPONLIMIT";

	/**************************** 默认属性设置 ********************************/
	/************ 默认全天状态 ************/
	public static final int STATE_DEF = 0;
	/****************** 短信配置 ********************/
	public static final String SMS_USER = "6799";
	public static final String SMS_PWD = "yjia@6799yz";
	
	/******************省市区查询常量 ********************/
	/**获取省信息**/
	public static final String GETPROVINCE = "GETPROVINCE";
	/**通过省id获取市信息**/
	public static final String GETCITYBYPROID = "GETCITYBYPROID";
	/**通过热键获取市信息**/
	public static final String GETCITYBYHOTKEY = "GETCITYBYHOTKEY";
	/**通过城市名称获取市信息**/
	public static final String GETCITYBYCNAME = "GETCITYBYCNAME";
	
	
	/**按城市ID获取区信息**/
	public static final String GETAREABYCITYID = "GETAREABYCITYID";
	/**按区ID获取区信息**/
	public static final String GETAREABYAREAID = "GETAREABYAREAID";
	
	/**按区ID获取区信息**/
	public static final String GETPROCITYAREA = "GETPROCITYAREA"; 
	
	/**考驾非模拟题目**/
	public static final String GETEXAMINATION = "GETEXAMINATION";
	/**获取考驾模拟题目**/
	public static final String GETANALOGEXAMINATION = "GETANALOGEXAMINATION";
	
	public static final String GETEXAMINATIONALL = "GETEXAMINATIONALL";
	
	/**所有的模拟题**/
	public static final String GETANALOGEXAMINATIONALL = "GETANALOGEXAMINATIONALL";
	
	/**考驾题目收藏**/
	public static final String ADDQUESTIONFAVORITES = "ADDQUESTIONFAVORITES";
	  
	
	/**考驾题目收藏**/
	public static final String REMOVEQUESTIONFAVORITES = "REMOVEQUESTIONFAVORITES";
	
	/** 获取所有的考驾图片**/
	public static final String GETEXIMGALL = "GETEXIMGALL";
	
	/**获取学员收藏的题目**/
	public static final String GETQUESTIONFAVORITES = "GETQUESTIONFAVORITES";
	/**获取学员收藏题目的数量**/
	public static final String GETQUESTIONFAVORITESNUM = "GETQUESTIONFAVORITESNUM";
	
	/**添加非模拟题答题记录**/
	public static final String ADDANSWERRECORD = "ADDANSWERRECORD";
	
	public static final String ADDANALOGANSWERRECORD = "ADDANALOGANSWERRECORD";
	/**获取学员收藏题目的数量**/
	public static final String GETANSWERRECORDINFO = "GETANSWERRECORDINFO";
	
	/**教练价格范围**/
	public static final String GETCOACHPRICERANGE = "GETCOACHPRICERANGE";
	
	/**根据参数获取广告**/
	public static final String GETADVERTISEMENTBYPARAM = "GETADVERTISEMENTBYPARAM";

	/**尝试登录限制设置  间隔时间10分钟**/
	public static final int TRY_LOGIN_INTERVAL=2;

	/**尝试登录限制设置  可尝试次数6次**/
	public static final int TRY_TIMES=6;

	/*****广告报名*****/

	/**广告页action**/
	public static final String ADVERTISEMENT_ACTION="ADVERTISEMENT_ACTION";

	/**广告报名表单action**/
	public static final String ADVERTISEMENT_SIGNUP_ACTION="ADVERTISEMENT_SIGNUP_ACTION";

	/*****广告报名*****/
}
