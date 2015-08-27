DROP PROCEDURE IF EXISTS  `accountreportforday`;

DELIMITER $$

CREATE PROCEDURE `accountreportforday`(`querydate` date)
BEGIN
	DECLARE a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14 INT(11);
  
  ##注册教练
  SELECT count(*) into a1 from t_user_coach where DATE(addtime)=querydate;
  ##注册学员
  SELECT count(*) into a2 from t_user_student where DATE(addtime)=querydate;
  ##一键报名
  SELECT count(*) into a3 from t_user_student where Date(enrolltime)=querydate and state=1;
  ##在线教练数
  SELECT  count(DISTINCT coachid) into a4 from t_coach_schedule  where date=querydate;
  ##总发布课时数
  SELECT count(*) into a5 from t_coach_schedule where date=querydate and isrest=0;

  ##预约学员
  SELECT count(DISTINCT studentid) into a6 from t_order where date=querydate;
  ##预约课时
  SELECT count(*) into a7 from t_coach_booktime where date=querydate;
  ##现金订单有老数据情况下
  SELECT count(*) into a8 from t_order where delmoney=0 and date=querydate and couponrecordid="";
  ##现金订单无老数据情况下
  ## SELECT count(*) into a8 from t_order where paytype=1 and date=querydate;
  ##学时券订单有老数据情况下
  SELECT count(*) into a9 from t_order where delmoney<>0 and date=querydate and couponrecordid<>"";
  ##学时券订单无老数据情况下
  ## SELECT count(*) into a9 from t_order where paytype=2 and date=querydate;

  ##小巴币订单
  SELECT count(*) into a10 from t_order where paytype=3 and date=querydate;

  ##学员取消订单
  SELECT count(*) into a11 from t_order where date=querydate and studentstate=4;
  ##现金
  SELECT SUM(total) into a12 from t_order where delmoney=0 and date=querydate and couponrecordid="";
  ##学时券
  SELECT SUM(total) into a13 from t_order where delmoney<>0 and date=querydate and couponrecordid<>"";
  ##小巴币 
  SELECT SUM(total) into a14 from t_order where paytype=3 and date=querydate;
  SELECT a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14;

END $$
DELIMITER;
