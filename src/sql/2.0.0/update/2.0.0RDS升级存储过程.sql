alter table app_coach_list add accompanynum int;
ALTER TABLE app_coach_list
ADD accompanycoursestate INT(11) NOT NULL DEFAULT '0' ,add orderbyaccompany INT(11) DEFAULT '0';
alter table app_coach_list
ADD COLUMN `signstate`  int(11) NULL DEFAULT 0 AFTER `orderbyaccompany`;

ALTER TABLE `app_coach_list`
ADD COLUMN `freecoursestate`  int(11) NOT NULL AFTER `signstate`,
ADD COLUMN `addtionalprice`  decimal(20,2) NOT NULL AFTER `freecoursestate`;

ALTER TABLE `t_question_favorites`
ADD COLUMN `studentid`  int(11) NOT NULL DEFAULT 0 AFTER `questionno`;

INSERT INTO t_car_models VALUES(19,NOW(),"陪驾","陪驾");
INSERT INTO t_teach_subject VALUES(4,NOW(),"陪驾");

update t_default_schedule set subjectid = 1 where subjectid = 0;
UPDATE t_user_coach c
SET c.subject2min = 50,
 c.subject2max = 120,
 c.subject3min = 50,
 c.subject3max = 150,
 c.trainingmin = 50,
 c.trainingmax = 150,
 c.accompanymin = 79,
 c.accompanymax = 79,
 c.hirecarmin = 0,
 c.hirecarmax = 50;


-- ----------------------------
-- Procedure structure for flush_app_coach_list
-- ----------------------------
DROP PROCEDURE IF EXISTS `flush_app_coach_list`;
DELIMITER ;;
CREATE DEFINER=`driver`@`%` PROCEDURE `flush_app_coach_list`()
BEGIN 
/*isnew=0表示为上次生成的旧数据，isnew=1表示是当前有效的最新数据，isnew=2表示是上次生成时的临时中转数据*/
/*开始*/
/*1.开始前先清除除状态=2的临时数据*/
/*2.插入新生成的中转数据*/
/*3.删除isnew=0上次生成的旧数据*/
/*4.把isnew=1的当前有效最新数据状态改为isnew=0表示为上次生成的旧数据*/
/*5.将本次插入的新生成中转数据状态改为当前最新有效isnew=1*/
/*结束*/

/*1.开始前先清除除状态=2的临时数据*/
DELETE from app_coach_list where isnew ="2";

/*2.插入新生成的中转数据*/
INSERT INTO app_coach_list (
isnew,
	address,
	sumnum,
  orderbyaccompany,
	coachid,
	addtime,
	avatar,
	birthday,
	cancancel,
	car_cardexptime,
	car_cardnum,
	car_cardpicb,
	car_cardpicf,
	carlicense,
	carmodel,
	carmodelid,
	city,
	coach_cardexptime,
	coach_cardnum,
	coach_cardpic,
	drive_cardexptime,
	drive_cardnum,
	drive_cardpic,
	drive_school,
	fmoney,
	frozenend,
	frozenstart,
	gender,
	gmoney,
	id_cardexptime,
	id_cardnum,
	id_cardpicb,
	id_cardpicf,
	isfrozen,
	LEVEL,
	modelid,
	money,
	newtasknoti,
	PASSWORD,
	phone,
	price,
	realname,
	realpic,
	score,
	selfeval,
	state,
	subjectdef,
	telphone,
	totaltime,
	urgent_person,
	urgent_phone,
	years,
	alipay_account,
	isquit,
	quittime,
	cashtype,
	token,
	token_time,
	invitecode,
	areaid,
	cityid,
	provinceid,
	coinnum,
	coursestate,
	fcoinnum,
	ad_flag,
	devicetype,
	usertype,
	version,
    drive_schoolid,
    accompanycoursestate,
    accompanynum,
    freecoursestate,
	signstate
) SELECT
2,
	getTeachAddress (u.coachid) AS address,
	getCoachOrderCount (u.coachid) AS sumnum,
    getCoachAccompanyOrder (u.coachid) AS orderbyaccompany,
	u.coachid,
	u.addtime,
	u.avatar,
	u.birthday,
	u.cancancel,
	u.car_cardexptime,
	u.car_cardnum,
	u.car_cardpicb,
	u.car_cardpicf,
	u.carlicense,
	u.carmodel,
	u.carmodelid,
	u.city,
	u.coach_cardexptime,
	u.coach_cardnum,
	u.coach_cardpic,
	u.drive_cardexptime,
	u.drive_cardnum,
	u.drive_cardpic,
	u.drive_school,
	u.fmoney,
	u.frozenend,
	u.frozenstart,
	u.gender,
	u.gmoney,
	u.id_cardexptime,
	u.id_cardnum,
	u.id_cardpicb,
	u.id_cardpicf,
	u.isfrozen,
	u. LEVEL,
	u.modelid,
	u.money,
	u.newtasknoti,
	u. PASSWORD,
	u.phone,
	u.price,
	u.realname,
	u.realpic,
	u.score,
	u.selfeval,
	u.state,
	u.subjectdef,
	u.telphone,
	u.totaltime,
	u.urgent_person,
	u.urgent_phone,
	u.years,
	u.alipay_account,
	u.isquit,
	u.quittime,
	u.cashtype,
	u.token,
	u.token_time,
	u.invitecode,
	u.areaid,
	u.cityid,
	u.provinceid,
	u.coinnum,
	u.coursestate,
	u.fcoinnum,
	u.ad_flag,
	u.devicetype,
	u.usertype,
	u.version,
	u.drive_schoolid,
    u.accompanycoursestate,
    u.accompanynum,
    u.freecoursestate,
	u.signstate
FROM
	t_user_coach u
WHERE
	state = 2
AND id_cardexptime > curdate()
AND coach_cardexptime > curdate()
AND drive_cardexptime > curdate()
AND car_cardexptime > curdate()
AND (
	SELECT
		count(*)
	FROM
		t_teach_address a
	WHERE
		u.coachid = a.coachid
	AND iscurrent = 1
) > 0
AND money >= gmoney
AND isquit = 0
ORDER BY
	coursestate DESC,
	drive_schoolid DESC,
	score DESC;

/*3.删除isnew=0上次生成的旧数据*/
DELETE from app_coach_list where isnew ="0";

/*4.把isnew=1的当前有效最新数据状态改为isnew=0表示为上次生成的旧数据*/
UPDATE app_coach_list SET isnew = "0" where isnew = "1";

/*5.将本次插入的新生成中转数据状态改为当前最新有效isnew=1*/
UPDATE app_coach_list SET isnew ="1" where isnew = "2";

/*6.删除isnew=0上次生成的旧数据*/
DELETE from app_coach_list where isnew in("0","2");

END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for getCoachAccompanyOrder
-- ----------------------------
DROP FUNCTION IF EXISTS `getCoachAccompanyOrder`;
DELIMITER ;;
CREATE DEFINER=`driver`@`%` FUNCTION `getCoachAccompanyOrder`(coid int(11)) RETURNS int(11)
BEGIN
  DECLARE reScore INT DEFAULT(0);
	DECLARE v_orderid INT(11); 
  DECLARE subjectname VARCHAR(100) DEFAULT "";
  -- 遍历数据结束标志
	DECLARE done INT DEFAULT FALSE;
  -- 游标
  DECLARE cur1 CURSOR FOR SELECT orderid from t_order where coachid=coid;

	-- 将结束标志绑定到游标
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

  OPEN  cur1; 
  read_loop: LOOP
    -- 提取游标里的数据
    FETCH cur1 INTO v_orderid;
    -- 声明结束的时候
    IF done THEN
      LEAVE read_loop;
    END IF;
    SELECT t.subject into subjectname from t_order_price t where t.orderid=v_orderid LIMIT 1;
    IF subjectname="陪驾" THEN
      set reScore=reScore+1;
    END IF;
  END LOOP;
  CLOSE cur1;
  RETURN reScore; 
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for getcoachaccompanystate
-- ----------------------------
DROP FUNCTION IF EXISTS `getcoachaccompanystate`;
DELIMITER ;;
CREATE DEFINER=`driver`@`%` FUNCTION `getcoachaccompanystate`(coachid int(11),datecount int(11),datestart text,starthour int(11),endhour int(11)) RETURNS tinyint(4)
BEGIN

DECLARE starthouruse INT DEFAULT 5;/**实际的开始小时**/

DECLARE endhouruse INT DEFAULT 23;/**实际的结束小时**/



DECLARE i INT DEFAULT 0;/**循环的下标**/

DECLARE result INT DEFAULT 0;/**返回结果**/

DECLARE count1 INT ;/**当天休息数据数量**/

DECLARE count2 INT ;/**当天开课数据数量**/





while i<datecount do/**日期循环**/

   IF i > 0 THEN/**如果不是第一次循环则日期需要加一**/

      set datestart = date_sub(datestart,interval -1 day);	

   END IF;

  /**确定实际的开始小时**/

   IF i=0 THEN

      set starthouruse=starthour;

   ELSE

      set starthouruse=5;

   END IF;

  /**确定实际的结束小时**/

   IF i=datecount-1 THEN

      set endhouruse=endhour;

   ELSE

      set endhouruse=23;

   END IF;


    while starthouruse <= endhouruse do/**当天的小时循环**/

          SELECT t_coach_schedule.isrest,t_coach_schedule.subjectid into count1,count2 from t_coach_schedule where t_coach_schedule.date=datestart and t_coach_schedule.coachid=coachid and t_coach_schedule.hour=starthouruse and t_coach_schedule.expire=0;
          IF count1=0 THEN
              IF count2=4 THEN
                 return 1;
              ELSE
								 set starthouruse=starthouruse+1;
                   
              END IF;
          ELSE
							set starthouruse=starthouruse+1;
         END IF;
		END while;

    /**下标加一**/

     set i=i+1;


end while;

return 0;

END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for getcoachstate
-- ----------------------------
DROP FUNCTION IF EXISTS `getcoachstate`;
DELIMITER ;;
CREATE DEFINER=`driver`@`%` FUNCTION `getcoachstate`(coachid int(11),datecount int(11),datestart text,starthour int(11),endhour int(11),subjectid int(11)) RETURNS varchar(10) CHARSET utf8mb4 COLLATE utf8mb4_unicode_ci
BEGIN

DECLARE starthouruse INT DEFAULT 5;/**实际的开始小时**/

DECLARE endhouruse INT DEFAULT 23;/**实际的结束小时**/



DECLARE i INT DEFAULT 0;/**循环的下标**/

DECLARE result INT DEFAULT 0;/**返回结果**/

DECLARE count1 INT ;/**当天休息数据数量**/

DECLARE count2 INT ;/**当天开课数据数量**/





while i<datecount do/**日期循环**/

   IF i > 0 THEN/**如果不是第一次循环则日期需要加一**/

      set datestart = date_sub(datestart,interval -1 day);	

   END IF;

  /**确定实际的开始小时**/

   IF i=0 THEN

      set starthouruse=starthour;

   ELSE

      set starthouruse=5;

   END IF;

  /**确定实际的结束小时**/

   IF i=datecount-1 THEN

      set endhouruse=endhour;

   ELSE

      set endhouruse=23;

   END IF;


    while starthouruse <= endhouruse do/**当天的小时循环**/

          SELECT t_coach_schedule.isrest,t_coach_schedule.subjectid into count1,count2 from t_coach_schedule where t_coach_schedule.date=datestart and t_coach_schedule.coachid=coachid and t_coach_schedule.hour=starthouruse and t_coach_schedule.expire=0;
					IF count1=0 THEN
              IF count2<>4 THEN
                 return 1;
              ELSE
										set starthouruse=starthouruse+1;
                    
              END IF;
          ELSE
							set starthouruse=starthouruse+1;
         END IF;
		END while;

    /**下标加一**/

     set i=i+1;


end while;

return 0;

END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for getcoachfreestate
-- ----------------------------
DROP FUNCTION IF EXISTS `getcoachfreestate`;
DELIMITER ;;
CREATE DEFINER=`driver`@`%` FUNCTION `getcoachfreestate`(coachid int(11),datecount int(11),datestart text,starthour int(11),endhour int(11)) RETURNS tinyint(4)
BEGIN

DECLARE starthouruse INT DEFAULT 5;/**实际的开始小时**/

DECLARE endhouruse INT DEFAULT 23;/**实际的结束小时**/



DECLARE i INT DEFAULT 0;/**循环的下标**/

DECLARE result INT DEFAULT 0;/**返回结果**/

DECLARE count1 INT ;/**当天休息数据数量**/

DECLARE count2 INT ;/**当天开课数据数量**/





while i<datecount do/**日期循环**/

   IF i > 0 THEN/**如果不是第一次循环则日期需要加一**/

      set datestart = date_sub(datestart,interval -1 day);	

   END IF;

  /**确定实际的开始小时**/

   IF i=0 THEN

      set starthouruse=starthour;

   ELSE

      set starthouruse=5;

   END IF;

  /**确定实际的结束小时**/

   IF i=datecount-1 THEN

      set endhouruse=endhour;

   ELSE

      set endhouruse=23;

   END IF;


    while starthouruse <= endhouruse do/**当天的小时循环**/

          SELECT t_coach_schedule.isrest,t_coach_schedule.subjectid into count1,count2 from t_coach_schedule where t_coach_schedule.date=datestart and t_coach_schedule.coachid=coachid and t_coach_schedule.hour=starthouruse and t_coach_schedule.expire=0 and t_coach_schedule.isfreecourse=1;
					IF count1=0 THEN
              IF count2<>4 THEN
                 return 1;
              ELSE
										set starthouruse=starthouruse+1;
                    
              END IF;
          ELSE
							set starthouruse=starthouruse+1;
         END IF;
		END while;

    /**下标加一**/

     set i=i+1;


end while;

return 0;

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for coinreportmonthly
-- ----------------------------
DROP PROCEDURE IF EXISTS `coinreportmonthly`;
DELIMITER ;;
CREATE DEFINER=`driver`@`%` PROCEDURE `coinreportmonthly`(`datestart` TEXT,`dateend` TEXT)
BEGIN
	DECLARE v_payername VARCHAR(100) DEFAULT "";## 发放教练/驾校
  DECLARE v_payerschool VARCHAR(200) DEFAULT "";## 发放教练所属驾校
  DECLARE v_payerphone VARCHAR(100) DEFAULT "";## 发放教练手机号码
  DECLARE v_coinnumber DECIMAL(20,2);## 发放小巴币数量
  DECLARE v_coinpay DECIMAL(20,2) DEFAULT "0.00";## 已结算小巴币数量
  DECLARE v_classhour INT(11) DEFAULT 0;## 已结算学时
  DECLARE v_coincharge DECIMAL(20,2);## 已兑换小巴币数量
  DECLARE v_payerid INT(11);## 教练ID或者驾校ID
  DECLARE v_type INT(11);## 发放人类型
  DECLARE v_count INT(11);## 临时变量1
  DECLARE v_count1 INT(11);## 临时变量2
  DECLARE v_count2 INT(11);## 临时变量3
  DECLARE v_count3 INT(11);## 临时变量4
  DECLARE v_oldcoinpay DECIMAL(20,2); ##老版本时候结算的小巴币
  DECLARE v_newcoinpay DECIMAL(20,2); ##新版本时候结算的小巴币
	DECLARE v_receiverid INT(11);## 接收小巴币学员ID，这里为老版本的时候使用
 -- 遍历数据结束标志
	DECLARE done INT DEFAULT FALSE;
  -- 游标 DATE(t.addtime) BETWEEN datestart and  dateend and 
  DECLARE cur1 CURSOR FOR SELECT t.ownerid,SUM(t.coinnum),t.ownertype from t_coin_record t where t.type=1 GROUP BY t.ownerid ORDER BY t.ownerid;

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
					 c_coinchange DECIMAL(20,2),
					 c_classhour INT (10),
					 c_type INT (10)
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

    set v_classhour=0;
    IF v_type= 1 THEN
            SELECT count(*) into v_count from  t_drive_school_info where schoolid = v_payerid;
            IF v_count=0 THEN
                set v_payerschool="";
								set v_payerphone="";
								set v_coinpay=0;
								set v_coincharge=0;
						ELSE
					      select name into v_payername from  t_drive_school_info where schoolid = v_payerid;
                    IF v_payername is NULL THEN
												  set v_payerschool="";
													set v_payerphone="";
													set v_coinpay=0;
													set v_coincharge=0;
										END IF;
						END IF;
            SELECT count(*) INTO v_count1 from t_coin_record where type=1 and ownerid=v_payerid and ownertype=1 and payerid<>0;
            IF v_count1=0 THEN
                select SUM(coinnum) into v_coinpay from t_coin_record where receiverid=v_payerid and type=2 and orderid in (SELECT orderid from t_order where DATE(start_time) BETWEEN datestart and dateend and over_time is not null);
									IF v_coinpay is NULL THEN
												 set v_coinpay=0;
									END IF;
            ELSE
                 ## 取老版本的小巴币使用记录
                BEGIN
                        -- 遍历数据结束标志
												DECLARE done_2 INT DEFAULT FALSE;
												-- 游标 DATE(t.addtime) BETWEEN datestart and  dateend and 
												DECLARE cur2 CURSOR FOR SELECT receiverid  from t_coin_record where type=1 and ownerid=v_payerid and ownertype=1 and payerid<>0;
												-- 将结束标志绑定到游标
												DECLARE CONTINUE HANDLER FOR NOT FOUND SET done_2 = TRUE;

												OPEN cur2;
                        
                        read_loop_2:LOOP

                         FETCH cur2 into v_receiverid;

												 IF done_2 THEN
															LEAVE read_loop_2;
												 END IF;

                             SELECT sum(coinnum) INTO v_oldcoinpay from t_coin_record where  type=2 and payerid=v_receiverid and ownertype=2 and orderid in (SELECT orderid from t_order where DATE(start_time) BETWEEN datestart and dateend and over_time is not null);

                             IF  v_oldcoinpay is not null THEN
                                   set v_coinpay=v_coinpay+v_oldcoinpay;
														 END IF;
											       SELECT count(*) INTO v_count3 from t_order where paytype=3 and studentid=v_receiverid and DATE(start_time) BETWEEN datestart and  dateend and over_time is not null; 
									           IF v_count3<>0 THEN
                                set v_classhour=v_classhour+v_count3;
														 END IF;
                             
												END LOOP;
                        close cur2;
                 END;
            END IF;
						SELECT count(*) into v_count2 from t_coin_record where ownerid=v_payerid and type=2 and orderid in (SELECT orderid from t_order where DATE(start_time) BETWEEN datestart and dateend and over_time is not null);
            
						IF v_count2<>0 THEN
									select SUM(coinnum) into v_newcoinpay from t_coin_record where ownerid=v_payerid and type=2 and orderid in (SELECT orderid from t_order where DATE(start_time) BETWEEN datestart and dateend and over_time is not null);
									IF v_newcoinpay is not NULL THEN
												 set v_coinpay=v_coinpay+v_newcoinpay;
                           
									END IF;
						END IF;

   
 	   ELSE   
        IF v_type = 2 THEN   
           ## select realname,phone into v_payername,v_payerphone from  t_user_coach t1  where coachid = v_payerid;
            SELECT count(*) into v_count from  t_user_coach t1, t_drive_school_info t2 where t1.coachid = v_payerid and t1.drive_schoolid=t2.schoolid;           
            IF v_count=0 THEN
               SET v_payername="";
               SET v_payerschool="";
							 SET v_payerphone="";
						ELSE
					      select t1.realname,t2.name,t1.phone into v_payername,v_payerschool,v_payerphone from  t_user_coach t1, t_drive_school_info t2 where t1.coachid = v_payerid and t1.drive_schoolid=t2.schoolid;
                    IF v_payername is NULL THEN
												  set v_payername="";
										END IF;
										IF v_payerschool is NULL THEN
												  set v_payerschool="";
										END IF;
										IF v_payerphone is NULL THEN
												  set v_payerphone="";
										END IF;
						END IF;
            SELECT count(*) into v_count from t_coin_record where receiverid=v_payerid and type=2 and DATE(addtime) BETWEEN datestart and  dateend;
            
						IF v_count=0 THEN
               set v_coinpay=0;
						ELSE
									select SUM(coinnum) into v_coinpay from t_coin_record where receiverid=v_payerid and type=2 and DATE(addtime) BETWEEN datestart and  dateend;
									IF v_coinpay is NULL THEN
												 set v_coinpay=0;
									END IF;
						END IF;

						SELECT count(*) into v_count from t_coin_record where payerid=v_payerid and type=4;
						IF v_count=0 THEN
                set v_coincharge=0;
						ELSE
									select SUM(coinnum) into v_coincharge from t_coin_record where payerid=v_payerid and type=4;
									IF v_coincharge is NULL THEN
												 set v_coincharge=0;
									END IF;
						END IF;
             SELECT count(*) INTO v_count3 from t_order where paytype=3 and coachid=v_payerid and DATE(creat_time) BETWEEN datestart and  dateend and over_time is not null; 
						 IF v_count3<>0 THEN
								set v_classhour=v_classhour+v_count3;
						 END IF;
       END IF;
    END IF; 
   INSERT into tmpTable_coin1(c_payerid,c_name,c_school,c_phone,c_coinnumber,c_coinpay,c_coinchange,c_classhour,c_type) SELECT v_payerid,v_payername,v_payerschool,v_payerphone,v_coinnumber,v_coinpay,v_coincharge,v_classhour,v_type;   
  END LOOP;
  CLOSE cur1;
  SELECT * from tmpTable_coin1;
  
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for coinreportmonthlybyschool
-- ----------------------------
DROP PROCEDURE IF EXISTS `coinreportmonthlybyschool`;
DELIMITER ;;
CREATE DEFINER=`driver`@`%` PROCEDURE `coinreportmonthlybyschool`(`datestart` TEXT,`dateend` TEXT,p_schoolid INT)
BEGIN
	DECLARE v_payername VARCHAR(100) DEFAULT "";## 发放教练/驾校
  DECLARE v_payerschool VARCHAR(200) DEFAULT "";## 发放教练所属驾校
  DECLARE v_payerphone VARCHAR(100) DEFAULT "";## 发放教练手机号码
  DECLARE v_coinnumber DECIMAL(20,2);## 发放小巴币数量
  DECLARE v_coinpay DECIMAL(20,2) DEFAULT "0.00";## 已结算小巴币数量
  DECLARE v_classhour INT(11) DEFAULT 0;## 已结算学时
  DECLARE v_scoincharge DECIMAL(20,2);## 已兑换驾校小巴币数量
  DECLARE v_coincharge DECIMAL(20,2);## 已兑换教练小巴币数量
  DECLARE v_unscoincharge DECIMAL(20,2);## 未兑换驾校小巴币数量
  DECLARE v_uncoincharge DECIMAL(20,2);## 未兑换教练小巴币数量
  DECLARE v_payerid INT(11);## 教练ID或者驾校ID
  DECLARE v_type INT(11);## 发放人类型
  DECLARE v_count INT(11);## 临时变量1
  DECLARE v_count1 INT(11);## 临时变量2
  DECLARE v_count2 INT(11);## 临时变量3
  DECLARE v_count3 INT(11);## 临时变量4
  DECLARE v_count4 INT(11);## 临时变量5
  DECLARE v_count5 INT(11);## 临时变量6
  DECLARE v_oldcoinpay DECIMAL(20,2); ##老版本时候结算的小巴币
  DECLARE v_newcoinpay DECIMAL(20,2); ##新版本时候结算的小巴币
	DECLARE v_receiverid INT(11);## 接收小巴币学员ID，这里为老版本的时候使用
 -- 遍历数据结束标志
	DECLARE done INT DEFAULT FALSE;
  -- 游标 
  DECLARE cur1 CURSOR FOR SELECT t.ownerid,SUM(t.coinnum),t.ownertype from t_coin_record t where t.type=1 and ownertype=2 and ownerid in(SELECT coachid from t_user_coach where drive_schoolid = p_schoolid) GROUP BY t.ownerid ORDER BY t.ownerid;

	-- 将结束标志绑定到游标
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

  
  OPEN cur1;
   
  create TEMPORARY table if not exists tmpTable_coin3(  
           id int(10) primary key not null auto_increment,
           c_payerid INT (10),
           c_name VARCHAR(100),
           c_school VARCHAR(200),  
           c_phone VARCHAR(100),  
           c_coinnumber DECIMAL(20,2),
           c_coinpay DECIMAL(20,2),
					 c_scoinchange DECIMAL(20,2),
           c_coinchange DECIMAL(20,2),
           c_unscoinchange DECIMAL(20,2),
           c_uncoinchange DECIMAL(20,2),
					 c_classhour INT (10),
					 c_type INT (10)
 
  );
  TRUNCATE tmpTable_coin3;
  -- 开始循环
  read_loop: LOOP
    -- 提取游标里的数据
    FETCH cur1 INTO v_payerid,v_coinnumber,v_type;
    -- 声明结束的时候
    IF done THEN
      LEAVE read_loop;
    END IF;
    -- 这里做你想做的循环的事件\
    set v_classhour=0;
            SELECT count(*) into v_count from  t_user_coach t1, t_drive_school_info t2 where t1.coachid = v_payerid and t1.drive_schoolid=t2.schoolid;           
            IF v_count=0 THEN
               SET v_payername="";
               SET v_payerschool="";
							 SET v_payerphone="";
						ELSE
					      select t1.realname,t2.name,t1.phone into v_payername,v_payerschool,v_payerphone from  t_user_coach t1, t_drive_school_info t2 where t1.coachid = v_payerid and t1.drive_schoolid=t2.schoolid;
                    IF v_payername is NULL THEN
												  set v_payername="";
										END IF;
										IF v_payerschool is NULL THEN
												  set v_payerschool="";
										END IF;
										IF v_payerphone is NULL THEN
												  set v_payerphone="";
										END IF;
						END IF;
            SELECT count(*) into v_count from t_coin_record where receiverid=v_payerid and type=2 and orderid in (SELECT orderid from t_order where DATE(start_time) BETWEEN datestart and dateend and over_time is not null);
            
						IF v_count=0 THEN
               set v_coinpay=0;
						ELSE
									select SUM(coinnum) into v_coinpay from t_coin_record where receiverid=v_payerid and type=2 and orderid in (SELECT orderid from t_order where DATE(start_time) BETWEEN datestart and dateend and over_time is not null);
									IF v_coinpay is NULL THEN
												 set v_coinpay=0;
									END IF;
						END IF;
            ## 获取已兑换驾校小巴币
            SELECT count(*) into v_count from t_coin_record where payerid=v_payerid and type=4 and ownertype=1 and orderid in (SELECT orderid from t_order where DATE(start_time) BETWEEN datestart and dateend and over_time is not null);
						IF v_count=0 THEN
                set v_scoincharge=0;
						ELSE
									select SUM(coinnum) into v_scoincharge from t_coin_record where payerid=v_payerid and type=4 and ownertype=1 and orderid in (SELECT orderid from t_order where DATE(start_time) BETWEEN datestart and dateend and over_time is not null);
									IF v_scoincharge is NULL THEN
												 set v_scoincharge=0;
									END IF;
						END IF;
           ## 获取已兑换教练小巴币
						SELECT count(*) into v_count from t_coin_record where payerid=v_payerid and type=4 and ownertype=2 and orderid in (SELECT orderid from t_order where DATE(start_time) BETWEEN datestart and dateend and over_time is not null);
						IF v_count=0 THEN
                set v_coincharge=0;
						ELSE
									select SUM(coinnum) into v_coincharge from t_coin_record where payerid=v_payerid and type=4 and ownertype=2 and orderid in (SELECT orderid from t_order where DATE(start_time) BETWEEN datestart and dateend and over_time is not null);
									IF v_coincharge is NULL THEN
												 set v_coincharge=0;
									END IF;
						END IF;
            ## 获取未兑换驾校小巴币
            SELECT count(*) into v_count from t_coin_record where receiverid=v_payerid and type=2 and ownertype=1;
            IF v_count=0 THEN
                set v_unscoincharge=0;
						ELSE
									select SUM(coinnum) into v_count4 from t_coin_record where receiverid=v_payerid and type=2 and ownertype=1;
									IF v_count4 is NULL THEN
												 set v_count4=0;

									END IF;
                  select SUM(coinnum) into v_count5 from t_coin_record where payerid=v_payerid and type=4 and ownertype=1;
                  IF v_count5 is NULL THEN
                         set v_count5=0;
                  END IF;
                  set v_unscoincharge=v_count4-v_count5;                 
						END IF;
             ## 获取未兑换教练小巴币
            SELECT count(*) into v_count from t_coin_record where receiverid=v_payerid and type=2 and ownertype=2;
            IF v_count=0 THEN
                set v_uncoincharge=0;
						ELSE
									select SUM(coinnum) into v_count4 from t_coin_record where receiverid=v_payerid and type=2 and ownertype=2;
									IF v_count4 is NULL THEN
												 set v_count4=0;
                  END IF;
                  select SUM(coinnum) into v_count5 from t_coin_record where payerid=v_payerid and type=4 and ownertype=2;
                  IF v_count5 is NULL THEN
                         set v_count5=0;
                  END IF;
                    set v_uncoincharge=v_count4-v_count5;
									
						END IF;


             SELECT count(*) INTO v_count3 from t_order where paytype=3 and coachid=v_payerid and DATE(start_time) BETWEEN datestart and  dateend and over_time is not null; 
						 IF v_count3<>0 THEN
								set v_classhour=v_classhour+v_count3;
						 END IF;
   INSERT into tmpTable_coin3(c_payerid,c_name,c_school,c_phone,c_coinnumber,c_coinpay,c_scoinchange,c_coinchange,c_unscoinchange,c_uncoinchange,c_classhour,c_type) SELECT v_payerid,v_payername,v_payerschool,v_payerphone,v_coinnumber,v_coinpay,v_scoincharge,v_coincharge,v_unscoincharge,v_uncoincharge,v_classhour,v_type;   
  END LOOP;
  CLOSE cur1;
  SELECT * from tmpTable_coin3;
  
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for couponreportmonthly
-- ----------------------------
DROP PROCEDURE IF EXISTS `couponreportmonthly`;
DELIMITER ;;
CREATE DEFINER=`driver`@`%` PROCEDURE `couponreportmonthly`(`datestart` TEXT,`dateend` TEXT,p_schoolid INT)
BEGIN
	DECLARE v_ownername VARCHAR(100) DEFAULT "";
  DECLARE v_ownerschool VARCHAR(200) DEFAULT "";
  DECLARE v_ownerphone VARCHAR(100) DEFAULT "";
  DECLARE v_couponnumber INT(10);
  DECLARE v_couponpaycount INT(10);
  DECLARE v_ownerid INT(11);##所有满足条件的教练ID列表
  DECLARE v_schoolid INT(11);## 驾校ID
  DECLARE s_ownerid INT(11);## 满足驾校筛选的教练ID列表
 -- 遍历数据结束标志
	DECLARE done INT DEFAULT FALSE;
  -- 游标
  DECLARE cur1 CURSOR FOR SELECT t.ownerid,SUM(t.value) from t_couponget_record t where ownerid<>0 and ownertype=2 GROUP BY t.ownerid ORDER BY t.ownerid;

	-- 将结束标志绑定到游标
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

  
  OPEN cur1;
   
  create TEMPORARY table if not exists tmpTable_coupon1(  
           id int(10) primary key not null auto_increment,
           c_ownerid INT (10),
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
	   SELECT t1.coachid,t1.realname,t1.phone,t2.name into s_ownerid, v_ownername,v_ownerphone,v_ownerschool  FROM t_user_coach t1,t_drive_school_info t2 where coachid=v_ownerid and t1.drive_schoolid=t2.schoolid and t2.schoolid=p_schoolid;
     ##SELECT t1.coachid,t1.realname,t1.phone,t2.name,t2.schoolid into s_ownerid, v_ownername,v_ownerphone,v_ownerschool,v_schoolid  from t_user_coach t1 where t1.coachid=v_ownerid and t1.drive_schoolid in (SELECT t2.schoolid from t_drive_school_info t2 where t2.schoolid=p_schoolid); 
     IF s_ownerid IS NULL THEN
          set done=0;
     ELSE
			SELECT SUM(value) into v_couponpaycount from t_couponget_record where state=1 and ownerid=s_ownerid and orderid in (SELECT orderid from t_order where DATE(creat_time) BETWEEN datestart and dateend and over_time is not null);
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
			 INSERT into tmpTable_coupon1(c_ownerid,c_name,c_school,c_phone,c_couponnumber,c_couponpay) SELECT s_ownerid,v_ownername,v_ownerschool,v_ownerphone,v_couponnumber,v_couponpaycount;   
       
     END IF;
    SET s_ownerid=null;
 END LOOP;
  CLOSE cur1;
  SELECT * from tmpTable_coupon1;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for daymonthlyreport
-- ----------------------------
DROP PROCEDURE IF EXISTS `daymonthlyreport`;
DELIMITER ;;
CREATE DEFINER=`driver`@`%` PROCEDURE `daymonthlyreport`(`querydate` date)
BEGIN

	DECLARE now_month,coachregister,studentregister,coachcertification,coachcoursetotal,coachcourseconfirm,studentbooked,studentconfirm,orderbycoupon,orderbycoin,orderbyaccount,s_couponplatform,s_couponschool,s_couponcoach,c_couponplatform,c_couponschool,c_couponcoach INT(11);
  DECLARE s_coinplatform,s_coinschool,s_coincoach,c_coinplatform,c_coinschool,c_coincoach,coachorderprice,coachrecharge,studentrecharge,studentapplycash decimal(19,2);
  ##当前月

  SELECT MONTH(CURDATE()) into now_month;

  ##注册教练

  SELECT count(*) into coachregister from t_user_coach where DATE(addtime)=querydate;

  ##注册学员

  SELECT count(*) into studentregister from t_user_student where DATE(addtime)=querydate;

  ##认证通过量
    
  SELECT count(*) into coachcertification from t_log where DATE(operatetime)=querydate;
  ##教练实际开课课时

  SELECT count(*) into coachcoursetotal from t_coach_schedule where isrest=1 and date=querydate;

  ##教练完成课时

  SELECT count(*) into coachcourseconfirm from t_order where DATE(start_time)=querydate and coachstate=2 and studentstate=3 and over_time is not null;

  ##学员预约课时

  SELECT count(*) into studentbooked from t_coach_schedule where date=querydate and bookstate=1;


  ##学员完成课时

	SELECT count(*) into studentconfirm from t_order where DATE(start_time)=querydate and coachstate=2 and studentstate=3 and over_time is not null;

  ##订单数-小巴券
  
  SELECT count(*) into orderbycoupon from t_order where DATE(start_time)=querydate and coachstate=2 and studentstate=3 and over_time is not null and paytype=2;

  ##订单数-小巴币

  SELECT count(*) into orderbycoin from t_order where DATE(start_time)=querydate and coachstate=2 and studentstate=3 and over_time is not null and paytype=3;
  ##订单数-现金订

  SELECT count(*) into orderbyaccount from t_order where DATE(start_time)=querydate and coachstate=2 and studentstate=3 and over_time is not null and paytype=1;
  ##小巴劵学员使用平台
  SET s_couponplatform=0;
  ##小巴劵学员使用驾校
  SET s_couponschool=0;
  ##小巴劵学员使用教练
  SELECT count(*) INTO s_couponcoach from t_couponget_record where ownertype=2 and state=1 and DATE(usetime)=querydate;
  ##小巴劵教练兑换平台
  SET c_couponplatform=0;
	##小巴劵教练兑换驾校
  SET c_couponschool=0;
	##小巴劵教练兑换教练
  SELECT count(*) into c_couponcoach from t_coupon_coach where state=2 and DATE(gettime)=querydate;
  ##小巴币学员使用平台
  SET s_coinplatform=0;
	##小巴币学员使用驾校
	SELECT sum(coinnum) into s_coinschool from t_coin_record where type=2 and DATE(addtime)=querydate and ownertype=1;
  IF s_coinschool is null THEN
       set s_coinschool=0;
  END IF;
	##小巴币学员使用教练
  SELECT sum(coinnum) into s_coincoach from t_coin_record where type=2 and DATE(addtime)=querydate and ownertype=2;
  IF s_coincoach is null THEN
       set s_coincoach=0;
  END IF;
  ##小巴币教练兑换平台
  SET c_coinplatform=0;
	##小巴币教练兑换驾校
  SET c_coinschool=0;
	##小巴币教练兑换教练

  SELECT sum(coinnum) into c_coincoach from t_coin_record where type=4 and DATE(addtime)=querydate;
  IF c_coincoach is null THEN
       set c_coincoach=0;
  END IF;
  ##教练现金订单金额

  SELECT sum(total)  into coachorderprice from t_order where DATE(start_time)=querydate and coachstate=2 and studentstate=3 and paytype=1 and over_time is not null;
  IF coachorderprice is null THEN
       set coachorderprice=0;
  END IF;
  ##实际教练提现金额

  SELECT sum(amount) into coachrecharge from t_coach_apply where state=5 and DATE(updatetime)=querydate;
  IF coachrecharge is null THEN
       set coachrecharge=0;
  END IF;
  ##学员充值金额

  SELECT sum(amount)  into studentrecharge from t_recharge_record where state=1 and DATE(updatetime)=querydate;
  IF studentrecharge is null THEN
       set studentrecharge=0;
  END IF;  
  ##学员提现金额

  SELECT sum(amount)  into studentapplycash from t_student_apply where state=1 and DATE(updatetime)=querydate;
  IF studentapplycash is null THEN
       set studentapplycash=0;
  END IF;
  
 ## SELECT now_month,querydate,coachregister,studentregister,coachcertification,coachcoursetotal,coachcourseconfirm,studentbooked,studentconfirm,orderbycoupon,orderbycoin,orderbyaccount,s_couponplatform,s_couponschool,s_couponcoach,c_couponplatform,c_couponschool,c_couponcoach,s_coinplatform,s_coinschool,s_coincoach,c_coinplatform,c_coinschool,c_coincoach,coachorderprice,coachrecharge,studentrecharge,studentapplycash; 
  INSERT INTO t_daymonthly(now_month,querydate,coachregister,studentregister,coachcertification,coachcoursetotal,coachcourseconfirm,studentbooked,studentconfirm,orderbycoupon,orderbycoin,orderbyaccount,s_couponplatform,s_couponschool,s_couponcoach,c_couponplatform,c_couponschool,c_couponcoach,s_coinplatform,s_coinschool,s_coincoach,c_coinplatform,c_coinschool,c_coincoach,coachorderprice,coachrecharge,studentrecharge,studentapplycash)  SELECT now_month,querydate,coachregister,studentregister,coachcertification,coachcoursetotal,coachcourseconfirm,studentbooked,studentconfirm,orderbycoupon,orderbycoin,orderbyaccount,s_couponplatform,s_couponschool,s_couponcoach,c_couponplatform,c_couponschool,c_couponcoach,s_coinplatform,s_coinschool,s_coincoach,c_coinplatform,c_coinschool,c_coincoach,coachorderprice,coachrecharge,studentrecharge,studentapplycash;



END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for getcoachcoupondetailmontly
-- ----------------------------
DROP PROCEDURE IF EXISTS `getcoachcoupondetailmontly`;
DELIMITER ;;
CREATE DEFINER=`driver`@`%` PROCEDURE `getcoachcoupondetailmontly`(IN `coachid` int,`datestart` TEXT,`dateend` TEXT)
BEGIN
	DECLARE s_phone varchar(100) DEFAULT "";
  DECLARE s_name varchar(100) DEFAULT "";
  DECLARE s_couponnum INT(10);
  DECLARE s_couponpaycount INT(10);
  DECLARE s_recid INT(11);
  DECLARE s_count INT(11);
 -- 遍历数据结束标志
	DECLARE done INT DEFAULT FALSE;
  DECLARE cur1 CURSOR FOR  SELECT userid,SUM(value) FROM t_couponget_record where ownerid=coachid and state=1 and orderid in (SELECT orderid from t_order where DATE(start_time) BETWEEN datestart and dateend and over_time is not null) GROUP BY userid  ORDER BY userid;


	-- 将结束标志绑定到游标
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
 
 OPEN cur1;
  create TEMPORARY table if not exists tmpTable_coupon2(  
           id int(10) primary key not null auto_increment,
           c_phone VARCHAR(100), 
           c_name VARCHAR(100),  
           c_couponnum INT(10),
           c_couponpaycount INT(10)
 
  );  
  TRUNCATE tmpTable_coupon2;
  -- 开始循环
  read_loop: LOOP
    -- 提取游标里的数据
    FETCH cur1 INTO s_recid,s_couponpaycount;
    
    -- 声明结束的时候
    IF done THEN
      LEAVE read_loop;
    END IF;
    -- 这里做你想做的循环的事件\
   /* IF s_recid =189 THEN
         set s_name="";
         set s_phone="";
     ELSE*/
        SELECT count(*) into s_count from t_user_student where studentid=s_recid;
				IF s_count=0 THEN
            set s_name="";
            set s_phone="";
				ELSE
            SELECT realname,phone into s_name,s_phone from t_user_student where studentid=s_recid;
						IF s_name is NULL THEN
												 set s_name="";
						END IF;
						IF s_phone is NULL THEN
											 set s_phone="";
						END IF;
				END IF;
        
				
   ## END IF;
     /* SELECT count(*) into s_count from t_couponget_record where ownerid=coachid and userid=s_recid and DATE(gettime) BETWEEN datestart and dateend;
      IF s_count=0 THEN
			     set s_couponnum=0;
			ELSE*/
       SELECT SUM(value) into s_couponnum from t_couponget_record where ownerid=coachid and userid=s_recid GROUP BY userid;
			##END IF;
    INSERT into tmpTable_coupon2(c_phone,c_name,c_couponnum,c_couponpaycount) SELECT s_phone,s_name,s_couponnum,s_couponpaycount;
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
CREATE DEFINER=`driver`@`%` PROCEDURE `getstudentcoindetailmontly`(`coachid` int,`ftype` int,`datestart` TEXT,`dateend` TEXT)
BEGIN
				DECLARE s_phone varchar(100);## 学员手机号码
				DECLARE s_name varchar(100); ## 学员姓名
				DECLARE s_coinnum DECIMAL(20,2);## 学员接收小巴币数量
				DECLARE s_coinpay DECIMAL(20,2);## 学员已使用小巴币数量
				DECLARE v_recid INT(11);## 学员ID
				DECLARE v_count INT(11);## 临时变量
				DECLARE s_classhour INT(11);## 学员已结算学时
				DECLARE v_ownertype INT(11);## 发放者类型

				
				-- 遍历数据结束标志
				 DECLARE done INT DEFAULT FALSE;
         DECLARE cur1 CURSOR FOR  SELECT receiverid,SUM(coinnum) FROM t_coin_record where type=1 and ownerid=coachid  GROUP BY receiverid HAVING SUM(coinnum)<>0 ORDER BY receiverid;
		     DECLARE cur2 CURSOR FOR  SELECT payerid FROM t_coin_record where type=2 and receiverid=coachid and orderid in (SELECT orderid from t_order where DATE(start_time) BETWEEN datestart and dateend and over_time is not null) GROUP BY payerid HAVING SUM(coinnum)<>0 ORDER BY payerid;
        -- 将结束标志绑定到游标
				 DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
          create TEMPORARY table if not exists tmpTable_coin2(  
										 id int(10) primary key not null auto_increment,
										 c_phone VARCHAR(100), 
										 c_name VARCHAR(100),  
										 c_coinnumber DECIMAL(20,2),
										 c_coinpay DECIMAL(20,2),
										 c_classhour int(10)
					 
						); 
						TRUNCATE tmpTable_coin2;
 ##如果是驾校的话
IF ftype=1 THEN	
			
							OPEN cur1;
								-- 开始循环
								read_loop: LOOP
									-- 提取游标里的数据
									FETCH cur1 INTO v_recid,s_coinnum;
									-- 声明结束的时候
									IF done THEN
										LEAVE read_loop;
									END IF;
									-- 这里做你想做的循环的事件\	
									SELECT count(*) into v_count from t_user_student where studentid=v_recid;
													
									IF v_count=0 THEN
										 set s_name="";
										 set s_phone="";		 
									ELSE
												SELECT realname,phone into s_name,s_phone from t_user_student where studentid=v_recid;
												IF s_name is NULL THEN
															 set s_name="";
												END IF;
												IF s_phone is NULL THEN
															 set s_phone="";
												END IF;
									END IF;
									SELECT count(*) into v_count from t_coin_record where type=2 and payerid=v_recid;
													
									IF v_count=0 THEN
										 set s_coinpay=0;
									ELSE
												SELECT SUM(coinnum) into s_coinpay  from t_coin_record where type=2 and payerid=v_recid and orderid in (SELECT orderid from t_order where DATE(start_time) BETWEEN datestart and dateend and over_time is not null);
												IF s_coinpay is null THEN
														set s_coinpay=0;
												END IF;
									END IF;
									SELECT count(*) into s_classhour from t_order where paytype=3 and studentid=v_recid and over_time is not null and DATE(start_time) BETWEEN datestart and dateend;
									IF s_classhour is  null THEN
											set s_classhour=0;
									END IF;
								
								INSERT into tmpTable_coin2(c_phone,c_name,c_coinnumber,c_coinpay,c_classhour) SELECT s_phone,s_name,s_coinnum,s_coinpay,s_classhour;
								END LOOP;
								CLOSE cur1;
								SELECT * from tmpTable_coin2;
END IF;
 ##如果是教练的话
IF ftype=2 THEN	

					      SET done=0;
								OPEN cur2;
								-- 开始循环
								read_loop1: LOOP
									-- 提取游标里的数据
           
								FETCH cur2 INTO v_recid;
									-- 声明结束的时候
									IF done THEN
										LEAVE read_loop1;
									END IF;
									
									-- 这里做你想做的循环的事件\
									SELECT count(*) into v_count from t_user_student where studentid=v_recid;           
									IF v_count=0 THEN
										 set s_name="";
										 set s_phone="";		 
									ELSE
												SELECT realname,phone into s_name,s_phone from t_user_student where studentid=v_recid;
												IF s_name is NULL THEN
															 set s_name="";
												END IF;
												IF s_phone is NULL THEN
															 set s_phone="";
												END IF;
									END IF;
									
									SELECT sum(coinnum) INTO s_coinnum from t_coin_record where type=1 and receiverid=v_recid;
							 
									SELECT count(*) into v_count from t_coin_record where type=2 and receiverid=coachid and payerid=v_recid and orderid in (SELECT orderid from t_order where DATE(start_time) BETWEEN datestart and dateend and over_time is not null);
													
									IF v_count=0 THEN
										 set s_coinpay=0;
									ELSE
												SELECT SUM(coinnum) into s_coinpay  from t_coin_record where type=2 and receiverid=coachid and payerid=v_recid and orderid in (SELECT orderid from t_order where DATE(start_time) BETWEEN datestart and dateend and over_time is not null);
												IF s_coinpay is null THEN
														set s_coinpay=0;
												END IF;
									END IF;

									SELECT count(*) into s_classhour from t_order t1 where t1.paytype=3 and t1.studentid=v_recid and t1.coachid=coachid and t1.over_time is not null and DATE(start_time) BETWEEN  datestart and dateend;
									IF s_classhour is  null THEN
											set s_classhour=0;
									END IF;
									INSERT into tmpTable_coin2(c_phone,c_name,c_coinnumber,c_coinpay,c_classhour) SELECT s_phone,s_name,s_coinnum,s_coinpay,s_classhour;
								END LOOP;
								CLOSE cur2;
								SELECT * from tmpTable_coin2;
 END IF;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for getstudentcoindetailmontlybyschool
-- ----------------------------
DROP PROCEDURE IF EXISTS `getstudentcoindetailmontlybyschool`;
DELIMITER ;;
CREATE DEFINER=`driver`@`%` PROCEDURE `getstudentcoindetailmontlybyschool`(`coachid` int,`datestart` TEXT,`dateend` TEXT)
BEGIN
				DECLARE s_phone varchar(100);## 学员手机号码
				DECLARE s_name varchar(100); ## 学员姓名
				DECLARE s_scoinnum DECIMAL(20,2);## 学员接收驾校小巴币数量
        DECLARE s_coinnum DECIMAL(20,2);## 学员接收教练小巴币数量
        DECLARE s_usedscoinnum DECIMAL(20,2);## 学员已使用驾校小巴币数量
        DECLARE s_usedcoinnum DECIMAL(20,2);## 学员已使用教练小巴币数量
				DECLARE s_coinpay DECIMAL(20,2);## 学员已使用小巴币数量
				DECLARE v_recid INT(11);## 学员ID
				DECLARE v_count INT(11);## 临时变量
				DECLARE s_classhour INT(11);## 学员已结算学时
				DECLARE v_ownertype INT(11);## 发放者类型

				
				-- 遍历数据结束标志
				 DECLARE done INT DEFAULT FALSE;
		     DECLARE cur2 CURSOR FOR  SELECT payerid FROM t_coin_record where type=2 and receiverid=coachid and orderid in (SELECT orderid from t_order where DATE(start_time) BETWEEN datestart and dateend and over_time is not null) GROUP BY payerid HAVING SUM(coinnum)<>0 ORDER BY payerid;
        -- 将结束标志绑定到游标
				 DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
          create TEMPORARY table if not exists tmpTable_coin4(  
										 id int(10) primary key not null auto_increment,
										 c_phone VARCHAR(100), 
										 c_name VARCHAR(100),
                     c_scoinnumber DECIMAL(20,2),
										 c_coinnumber DECIMAL(20,2),
                     c_usedscoinnumber DECIMAL(20,2),
                     c_usedcoinnumber DECIMAL(20,2),
										 c_coinpay DECIMAL(20,2),
										 c_classhour int(10)
					 
						); 
						TRUNCATE tmpTable_coin4;


								OPEN cur2;
								-- 开始循环
								read_loop1: LOOP
									-- 提取游标里的数据
           
								FETCH cur2 INTO v_recid;
									-- 声明结束的时候
									IF done THEN
										LEAVE read_loop1;
									END IF;
									
									-- 这里做你想做的循环的事件\
									SELECT count(*) into v_count from t_user_student where studentid=v_recid;           
									IF v_count=0 THEN
										 set s_name="";
										 set s_phone="";		 
									ELSE
												SELECT realname,phone into s_name,s_phone from t_user_student where studentid=v_recid;
												IF s_name is NULL THEN
															 set s_name="";
												END IF;
												IF s_phone is NULL THEN
															 set s_phone="";
												END IF;
									END IF;
									

                  SELECT sum(coinnum) INTO s_scoinnum from t_coin_record where type=1 and receiverid=v_recid and ownertype=1;
                  IF s_scoinnum IS NULL THEN
                       SET s_scoinnum=0;
                  END IF;

									SELECT sum(coinnum) INTO s_coinnum from t_coin_record where type=1 and receiverid=v_recid and ownertype=2;
                  IF s_coinnum IS NULL THEN
                       SET s_coinnum=0;
                  END IF;
                  SELECT sum(coinnum) INTO s_usedscoinnum from t_coin_record where type=2 and payerid=v_recid and ownertype=1 and receiverid=coachid and orderid in (SELECT orderid from t_order where DATE(start_time) BETWEEN datestart and dateend and over_time is not null) ;
                  IF s_usedscoinnum IS NULL THEN
                       SET s_usedscoinnum=0;
                  END IF;
                  SELECT sum(coinnum) INTO s_usedcoinnum from t_coin_record where type=2 and payerid=v_recid and ownertype=2 and receiverid=coachid and orderid in (SELECT orderid from t_order where DATE(start_time) BETWEEN datestart and dateend and over_time is not null) ;
                  IF s_usedcoinnum IS NULL THEN
                       SET s_usedcoinnum=0;
                  END IF;
									SELECT count(*) into v_count from t_coin_record where type=2 and receiverid=coachid and payerid=v_recid and orderid in (SELECT orderid from t_order where DATE(start_time) BETWEEN datestart and dateend and over_time is not null);
													
									IF v_count=0 THEN
										 set s_coinpay=0;
									ELSE
												SELECT SUM(coinnum) into s_coinpay  from t_coin_record where type=2 and receiverid=coachid and payerid=v_recid and orderid in (SELECT orderid from t_order where DATE(start_time) BETWEEN datestart and dateend and over_time is not null);
												IF s_coinpay is null THEN
														set s_coinpay=0;
												END IF;
									END IF;

									SELECT count(*) into s_classhour from t_order t1 where t1.paytype=3 and t1.studentid=v_recid and t1.coachid=coachid and t1.over_time is not null and DATE(start_time) BETWEEN  datestart and dateend;
									IF s_classhour is  null THEN
											set s_classhour=0;
									END IF;
									INSERT into tmpTable_coin4(c_phone,c_name,c_scoinnumber,c_coinnumber,c_usedscoinnumber,c_usedcoinnumber,c_coinpay,c_classhour) SELECT s_phone,s_name,s_scoinnum,s_coinnum,s_usedscoinnum,s_usedcoinnum,s_coinpay,s_classhour;
								END LOOP;
								CLOSE cur2;
								SELECT * from tmpTable_coin4;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for updateaccompanydriver
-- ----------------------------
DROP PROCEDURE IF EXISTS `updateaccompanydriver`;
DELIMITER ;;
CREATE DEFINER=`driver`@`%` PROCEDURE `updateaccompanydriver`()
BEGIN
	#Routine body goes here...
	DECLARE v_model VARCHAR(255);
  DECLARE v_model1 VARCHAR(255);
  DECLARE v_coachid INT(11);
 -- 遍历数据结束标志
	DECLARE done INT DEFAULT FALSE;
 -- 游标
  DECLARE cur1 CURSOR FOR SELECT modelid from t_user_coach t where modelid is not NULL and SUBSTRING(modelid,LENGTH(modelid),LENGTH(modelid))=',';
  DECLARE cur2 CURSOR FOR SELECT coachid,modelid from t_user_coach t where modelid is not NULL;

	-- 将结束标志绑定到游标
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
  OPEN cur1;
   
  -- 开始循环
  read_loop: LOOP
    -- 提取游标里的数据
    FETCH cur1 INTO v_model;
    -- 声明结束的时候
    IF done THEN
      LEAVE read_loop;
    END IF;
    -- 这里做你想做的循环的事件\
      set v_model=SUBSTRING(v_model,1,2);
     ## SELECT model;
      update t_user_coach set modelid=v_model where modelid is not NULL and SUBSTRING(modelid,LENGTH(modelid),LENGTH(modelid))=',';
  END LOOP;
 CLOSE cur1;

  -- 游标


  set done=0;
  OPEN cur2;
   
  -- 开始循环
  read_loop: LOOP
    -- 提取游标里的数据
    FETCH cur2 INTO v_coachid,v_model1;
    -- 声明结束的时候
    IF done THEN
      LEAVE read_loop;
    END IF;
    -- 这里做你想做的循环的事件\
      set v_model1=CONCAT(v_model1,",19");
     ## SELECT model1;
      update t_user_coach set modelid=v_model1 where modelid is not NULL and coachid=v_coachid;
  END LOOP;

  CLOSE cur2;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for updatefreecoursedata
-- ----------------------------
DROP PROCEDURE IF EXISTS `updatefreecoursedata`;
DELIMITER ;;
CREATE DEFINER=`driver`@`%` PROCEDURE `updatefreecoursedata`()
BEGIN
update t_user_student
set freecoursestate=1
where studentid in (SELECT studentid from t_order where studentstate<>4 and coachstate<>4);
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for update_coach_signstate
-- ----------------------------
DROP PROCEDURE IF EXISTS `update_coach_signstate`;
DELIMITER ;;
CREATE DEFINER=`driver`@`%` PROCEDURE `update_coach_signstate`()
BEGIN 
/*1、更新教练签约状态*/
/*signstate=0表示未签约，signstate=1表示已签约未过期，signstate=2表示已签约过期*/
update t_user_coach c set c.signstate = 0 where c.signexpired <=now() and c.signstate = 1;

/*2、更新教练体验课开课状态*/
/*freecoursestate=0表示从当天开始5天内未开体验课，freecoursestate=1表示从当天开始55天内未开体验课*/
update t_user_coach c set c.freecoursestate = 1 where  c.coachid in (select DISTINCT coachid from t_coach_schedule cs where cs.isfreecourse = 1 and cs.date < date_add(now(), interval 5 day) and cs.date >= now());

update t_user_coach c set c.freecoursestate = 0 where c.freecoursestate=1 and  c.coachid not in (select DISTINCT coachid from t_coach_schedule cs where cs.isfreecourse = 1 and cs.date < date_add(now(), interval 5 day) and cs.date >= now());


END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for getExCoinnum
-- ----------------------------
DROP FUNCTION IF EXISTS `getExCoinnum`;
DELIMITER ;;
CREATE DEFINER=`driver`@`%` FUNCTION `getExCoinnum`(
coid int(11)) RETURNS int(11)
BEGIN
	DECLARE reScore INT; 
	select sum(coinnum)  INTO reScore from t_coin_record where payerid=coid and type in (0,4);
	RETURN reScore; 
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for getExCoupon
-- ----------------------------
DROP FUNCTION IF EXISTS `getExCoupon`;
DELIMITER ;;
CREATE DEFINER=`driver`@`%` FUNCTION `getExCoupon`(
coid int(11)) RETURNS int(11)
BEGIN
	DECLARE reScore INT; 
	select count(*) into reScore from t_coupon_coach where coachid = coid and state = 2 ;
	RETURN reScore; 
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for test
-- ----------------------------
DROP PROCEDURE IF EXISTS `test`;
DELIMITER ;;
CREATE DEFINER=`driver`@`%` PROCEDURE `test`()
BEGIN
	DECLARE a1 INT;
  
  -- 遍历数据结束标志
	DECLARE done INT DEFAULT FALSE;
  -- 游标
  DECLARE cur1 CURSOR FOR SELECT DISTINCT t1.receiverid from t_coin_record t1 where t1.type=2 and t1.receiverid not in (SELECT t2.ownerid from t_coin_record t2 where t2.type=1);

	-- 将结束标志绑定到游标
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

  
  OPEN cur1;


 read_loop: LOOP
    -- 提取游标里的数据
    FETCH cur1 INTO a1;
    -- 声明结束的时候

    IF done THEN
      LEAVE read_loop;
    END IF;
    -- 这里做你想做的循环的事件\
	  INSERT into t_coin_record(coinnum,ownerid,ownertype,payerid,payertype,receiverid,receivertype,type) VALUES(0,a1,2,0,0,0,0,1);
 
 END LOOP;

END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for generate_daymonthlyreport_byhand
-- ----------------------------
DROP FUNCTION IF EXISTS `generate_daymonthlyreport_byhand`;
DELIMITER ;;
CREATE DEFINER=`driver`@`%` FUNCTION `generate_daymonthlyreport_byhand`() RETURNS int(11)
BEGIN
  DECLARE d1 INT DEFAULT 0;
	DECLARE d2 date;
  set d2="2015-11-01";
  WHILE d1<30 do
    CALL daymonthlyreport(d2+d1);
    set d1=d1+1;
  END WHILE;
 
	RETURN 0;
END
;;
DELIMITER ;

-- ----------------------------
-- Event structure for  reportmontly
-- ----------------------------
DROP EVENT IF EXISTS ` reportmontly`;
DELIMITER ;;
CREATE DEFINER=`driver`@`%` EVENT ` reportmontly` ON SCHEDULE EVERY 1 DAY STARTS '2015-10-17 02:00:00' ON COMPLETION PRESERVE ENABLE DO CALL daymonthlyreport(CURDATE()-1)
;;
DELIMITER ;

-- ----------------------------
-- Event structure for job_update_coach_signstate
-- ----------------------------
DROP EVENT IF EXISTS `job_update_coach_signstate`;
DELIMITER ;;
CREATE DEFINER=`driver`@`%` EVENT `job_update_coach_signstate` ON SCHEDULE EVERY 1 MINUTE STARTS '2015-11-20 02:30:00' ON COMPLETION PRESERVE ENABLE DO CALL update_coach_signstate()
;;
DELIMITER ;

update t_user_coach c set c.modelid = '17' where c.modelid is null;