/*
Navicat MySQL Data Transfer

Source Server         : xiaoba
Source Server Version : 50625
Source Host           : localhost:3306
Source Database       : driver

Target Server Type    : MYSQL
Target Server Version : 50625
File Encoding         : 65001

Date: 2015-08-28 20:11:54
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Procedure structure for accountreportforday
-- ----------------------------
DROP PROCEDURE IF EXISTS `accountreportforday`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `accountreportforday`(`querydate` date)
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



END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for coinreportmonthly
-- ----------------------------
DROP PROCEDURE IF EXISTS `coinreportmonthly`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `coinreportmonthly`(`datestart` TEXT,`dateend` TEXT)
BEGIN
	DECLARE v_payername VARCHAR(100) DEFAULT "";
  DECLARE v_payerschool VARCHAR(200) DEFAULT "";
  DECLARE v_payerphone VARCHAR(100) DEFAULT "";
  DECLARE v_coinnumber DECIMAL(20,2);
  DECLARE v_coinpay DECIMAL(20,2);
  DECLARE v_coinchage DECIMAL(20,2);
  DECLARE v_payerid INT;
  DECLARE v_type INT;
  
 -- 遍历数据结束标志
	DECLARE done INT DEFAULT FALSE;
  -- 游标
  DECLARE cur1 CURSOR FOR SELECT t.ownerid,SUM(t.coinnum),t.ownertype from t_coin_record t where DATE(t.addtime) BETWEEN datestart and  dateend and t.type=1 GROUP BY t.payerid ORDER BY t.ownerid;

	-- 将结束标志绑定到游标
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

  
  OPEN cur1;
   
  create TEMPORARY table if not exists tmpTable_coin1(  
           id int(10) primary key not null auto_increment,
           c_payerid INT (10),
           c_name VARCHAR(100),
           c_school VARCHAR(200),  
           c_phone VARCHAR(100),  
           c_coinnumber DECIMAL(20,2),
           c_coinpay DECIMAL(20,2),
					 c_coinchange DECIMAL(20,2)
 
  );
  TRUNCATE tmpTable_coin1;
  -- 开始循环
  read_loop: LOOP
    -- 提取游标里的数据
    FETCH cur1 INTO v_payerid,v_coinnumber,v_type;
    -- 声明结束的时候
    IF done THEN
      LEAVE read_loop;
    END IF;
    -- 这里做你想做的循环的事件\
    IF v_type = 1 THEN
  	        select name into v_payername from  t_drive_school_info where schoolid = v_payerid;
            set v_payerschool="";
            set v_payerphone="";
            set v_coinpay=0;
            set v_coinchage=0;
 	   ELSE   
        IF v_type = 2 THEN   
           ## select realname,phone into v_payername,v_payerphone from  t_user_coach t1  where coachid = v_payerid;
           select t1.realname,t2.name,t1.phone into v_payername,v_payerschool,v_payerphone from  t_user_coach t1, t_drive_school_info t2 where t1.coachid = v_payerid and t1.drive_schoolid=t2.schoolid;           
            select SUM(coinnum) into v_coinpay from t_coin_record where receiverid=v_payerid and type=2;
            IF v_coinpay is NULL THEN
                   set v_coinpay=0;
            END IF;
            select SUM(coinnum) into v_coinchage from t_coin_record where payerid=v_payerid and type=4;
            IF v_coinchage is NULL THEN
                   set v_coinchage=0;
            END IF;
       END IF;
    END IF; 
   INSERT into tmpTable_coin1(c_payerid,c_name,c_school,c_phone,c_coinnumber,c_coinpay,c_coinchange) SELECT v_payerid,v_payername,v_payerschool,v_payerphone,v_coinnumber,v_coinpay,v_coinchage;   
  END LOOP;
  CLOSE cur1;
  SELECT * from tmpTable_coin1;
  
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for couponreportmonthly
-- ----------------------------
DROP PROCEDURE IF EXISTS `couponreportmonthly`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `couponreportmonthly`(`datestart` TEXT,`dateend` TEXT)
BEGIN
	DECLARE v_ownername VARCHAR(100) DEFAULT "";
  DECLARE v_ownerschool VARCHAR(200) DEFAULT "";
  DECLARE v_ownerphone VARCHAR(100) DEFAULT "";
  DECLARE v_couponnumber INT(10);
  DECLARE v_couponpaycount INT(10);
  DECLARE v_ownerid INT;
 -- 遍历数据结束标志
	DECLARE done INT DEFAULT FALSE;
  -- 游标
  DECLARE cur1 CURSOR FOR SELECT t.ownerid,SUM(t.value) from t_couponget_record t where DATE(t.gettime) BETWEEN datestart and  dateend and ownerid<>0 GROUP BY t.ownerid ORDER BY t.ownerid;

	-- 将结束标志绑定到游标
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

  
  OPEN cur1;
   
  create TEMPORARY table if not exists tmpTable_coupon1(  
           id int(10) primary key not null auto_increment,
           c_opwnerid INT (10),
           c_name VARCHAR(100),
           c_school VARCHAR(200),  
           c_phone VARCHAR(100),  
           c_couponnumber INT(10),
           c_couponpay INT(10)
 
  );
  TRUNCATE tmpTable_coupon1;
  -- 开始循环
  read_loop: LOOP
    -- 提取游标里的数据
    FETCH cur1 INTO v_ownerid,v_couponnumber;
    -- 声明结束的时候
    IF done THEN
      LEAVE read_loop;
    END IF;
    -- 这里做你想做的循环的事件\
     SELECT t1.realname,t1.phone,t2.name into v_ownername,v_ownerphone,v_ownerschool  FROM t_user_coach t1,t_drive_school_info t2 where coachid=v_ownerid and t1.drive_schoolid=t2.schoolid;
		 SELECT SUM(value) into v_couponpaycount from t_couponget_record where state=1 and ownerid=v_ownerid and DATE(usetime) BETWEEN datestart and dateend;
			IF v_ownername is null  THEN
						set v_ownername="";
			END IF;      
			IF v_ownerschool is null  THEN
						set v_ownerschool="";
      END IF;
      IF v_ownerphone is null  THEN
            set v_ownerphone="";
      END IF;
		 
      IF v_couponpaycount is null  THEN
            set v_couponpaycount=0;
      END IF;
     INSERT into tmpTable_coupon1(c_opwnerid,c_name,c_school,c_phone,c_couponnumber,c_couponpay) SELECT v_ownerid,v_ownername,v_ownerschool,v_ownerphone,v_couponnumber,v_couponpaycount;   
  END LOOP;
  CLOSE cur1;
  SELECT * from tmpTable_coupon1;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for getcoachcoupondetailmontly
-- ----------------------------
DROP PROCEDURE IF EXISTS `getcoachcoupondetailmontly`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getcoachcoupondetailmontly`(IN `coachid` int,`datestart` TEXT,`dateend` TEXT)
BEGIN
	DECLARE s_phone varchar(100);
  DECLARE s_name varchar(100);
  DECLARE s_couponnum INT(10);
  DECLARE s_couponpaycount INT(10);
  DECLARE s_recid INT(11);
    
 -- 遍历数据结束标志
	DECLARE done INT DEFAULT FALSE;
  DECLARE cur1 CURSOR FOR  SELECT userid,SUM(value) FROM t_couponget_record where ownerid=coachid and DATE(gettime) BETWEEN datestart and dateend GROUP BY userid ORDER BY userid;

	-- 将结束标志绑定到游标
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
 
 OPEN cur1;
  create TEMPORARY table if not exists tmpTable_coupon2(  
           id int(10) primary key not null auto_increment,
           c_phone VARCHAR(100), 
           c_name VARCHAR(100),  
           c_couponnum111 INT(10),
           c_couponpaycount INT(10)
 
  );  
  TRUNCATE tmpTable_coupon2;
  -- 开始循环
  read_loop: LOOP
    -- 提取游标里的数据
    FETCH cur1 INTO s_recid,s_couponnum;
    IF s_couponnum is NULL THEN
      set s_couponnum=0;
    end IF;
    -- 声明结束的时候
    IF done THEN
      LEAVE read_loop;
    END IF;
    -- 这里做你想做的循环的事件\
    SELECT realname,phone into s_name,s_phone from t_user_student where studentid=s_recid;

    SELECT SUM(value) into s_couponpaycount from t_couponget_record where state=1 and userid=s_recid and DATE(usetime) BETWEEN datestart and dateend;
    IF s_couponpaycount is NULL THEN
        set s_couponpaycount=0;
    END IF;
  
    INSERT into tmpTable_coupon2(c_phone,c_name,c_couponnum111,c_couponpaycount) SELECT s_phone,s_name,s_couponnum,s_couponpaycount;
  END LOOP;
   CLOSE cur1;
  SELECT * from tmpTable_coupon2;
  

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for getstudentcoindetailmontly
-- ----------------------------
DROP PROCEDURE IF EXISTS `getstudentcoindetailmontly`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getstudentcoindetailmontly`(IN `coachid` int,`datestart` TEXT,`dateend` TEXT)
BEGIN
	DECLARE s_phone varchar(100);
  DECLARE s_name varchar(100);
  DECLARE s_coinnum DECIMAL(20,2);
  DECLARE s_coinpay DECIMAL(20,2);
  DECLARE v_recid INT(11);
    
 -- 遍历数据结束标志
	DECLARE done INT DEFAULT FALSE;
  DECLARE cur1 CURSOR FOR  SELECT receiverid,SUM(coinnum) FROM t_coin_record where type=1 and ownerid=coachid and DATE(addtime) BETWEEN  datestart and dateend GROUP BY receiverid ORDER BY receiverid;

	-- 将结束标志绑定到游标
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
 
 OPEN cur1;
  create TEMPORARY table if not exists tmpTable_coin2(  
           id int(10) primary key not null auto_increment,
           c_phone VARCHAR(100), 
           c_name VARCHAR(100),  
           c_coinnumber DECIMAL(20,2),
           c_coinpay DECIMAL(20,2)
 
  );  
  TRUNCATE tmpTable_coin2;
  -- 开始循环
  read_loop: LOOP
    -- 提取游标里的数据
    FETCH cur1 INTO v_recid,s_coinnum;
    -- 声明结束的时候
    IF done THEN
      LEAVE read_loop;
    END IF;
    -- 这里做你想做的循环的事件\
    SELECT realname,phone into s_name,s_phone from t_user_student where studentid=v_recid;
    SELECT SUM(coinnum) into s_coinpay  from t_coin_record where type=2 and payerid=v_recid and DATE(addtime) BETWEEN  datestart and dateend;
    IF s_coinpay is null THEN
        set s_coinpay=0;
    END IF;
  
    INSERT into tmpTable_coin2(c_phone,c_name,c_coinnumber,c_coinpay) SELECT s_phone,s_name,s_coinnum,s_coinpay;
  END LOOP;
  CLOSE cur1;
  SELECT * from tmpTable_coin2;
  
END
;;
DELIMITER ;
