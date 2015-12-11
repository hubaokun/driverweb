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

INSERT INTO t_car_models VALUES(19,NOW(),"���","���");
INSERT INTO t_teach_subject VALUES(4,NOW(),"���");

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
/*isnew=0��ʾΪ�ϴ����ɵľ����ݣ�isnew=1��ʾ�ǵ�ǰ��Ч���������ݣ�isnew=2��ʾ���ϴ�����ʱ����ʱ��ת����*/
/*��ʼ*/
/*1.��ʼǰ�������״̬=2����ʱ����*/
/*2.���������ɵ���ת����*/
/*3.ɾ��isnew=0�ϴ����ɵľ�����*/
/*4.��isnew=1�ĵ�ǰ��Ч��������״̬��Ϊisnew=0��ʾΪ�ϴ����ɵľ�����*/
/*5.�����β������������ת����״̬��Ϊ��ǰ������Чisnew=1*/
/*����*/

/*1.��ʼǰ�������״̬=2����ʱ����*/
DELETE from app_coach_list where isnew ="2";

/*2.���������ɵ���ת����*/
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

/*3.ɾ��isnew=0�ϴ����ɵľ�����*/
DELETE from app_coach_list where isnew ="0";

/*4.��isnew=1�ĵ�ǰ��Ч��������״̬��Ϊisnew=0��ʾΪ�ϴ����ɵľ�����*/
UPDATE app_coach_list SET isnew = "0" where isnew = "1";

/*5.�����β������������ת����״̬��Ϊ��ǰ������Чisnew=1*/
UPDATE app_coach_list SET isnew ="1" where isnew = "2";

/*6.ɾ��isnew=0�ϴ����ɵľ�����*/
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
  -- �������ݽ�����־
	DECLARE done INT DEFAULT FALSE;
  -- �α�
  DECLARE cur1 CURSOR FOR SELECT orderid from t_order where coachid=coid;

	-- ��������־�󶨵��α�
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

  OPEN  cur1; 
  read_loop: LOOP
    -- ��ȡ�α��������
    FETCH cur1 INTO v_orderid;
    -- ����������ʱ��
    IF done THEN
      LEAVE read_loop;
    END IF;
    SELECT t.subject into subjectname from t_order_price t where t.orderid=v_orderid LIMIT 1;
    IF subjectname="���" THEN
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

DECLARE starthouruse INT DEFAULT 5;/**ʵ�ʵĿ�ʼСʱ**/

DECLARE endhouruse INT DEFAULT 23;/**ʵ�ʵĽ���Сʱ**/



DECLARE i INT DEFAULT 0;/**ѭ�����±�**/

DECLARE result INT DEFAULT 0;/**���ؽ��**/

DECLARE count1 INT ;/**������Ϣ��������**/

DECLARE count2 INT ;/**���쿪����������**/





while i<datecount do/**����ѭ��**/

   IF i > 0 THEN/**������ǵ�һ��ѭ����������Ҫ��һ**/

      set datestart = date_sub(datestart,interval -1 day);	

   END IF;

  /**ȷ��ʵ�ʵĿ�ʼСʱ**/

   IF i=0 THEN

      set starthouruse=starthour;

   ELSE

      set starthouruse=5;

   END IF;

  /**ȷ��ʵ�ʵĽ���Сʱ**/

   IF i=datecount-1 THEN

      set endhouruse=endhour;

   ELSE

      set endhouruse=23;

   END IF;


    while starthouruse <= endhouruse do/**�����Сʱѭ��**/

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

    /**�±��һ**/

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

DECLARE starthouruse INT DEFAULT 5;/**ʵ�ʵĿ�ʼСʱ**/

DECLARE endhouruse INT DEFAULT 23;/**ʵ�ʵĽ���Сʱ**/



DECLARE i INT DEFAULT 0;/**ѭ�����±�**/

DECLARE result INT DEFAULT 0;/**���ؽ��**/

DECLARE count1 INT ;/**������Ϣ��������**/

DECLARE count2 INT ;/**���쿪����������**/





while i<datecount do/**����ѭ��**/

   IF i > 0 THEN/**������ǵ�һ��ѭ����������Ҫ��һ**/

      set datestart = date_sub(datestart,interval -1 day);	

   END IF;

  /**ȷ��ʵ�ʵĿ�ʼСʱ**/

   IF i=0 THEN

      set starthouruse=starthour;

   ELSE

      set starthouruse=5;

   END IF;

  /**ȷ��ʵ�ʵĽ���Сʱ**/

   IF i=datecount-1 THEN

      set endhouruse=endhour;

   ELSE

      set endhouruse=23;

   END IF;


    while starthouruse <= endhouruse do/**�����Сʱѭ��**/

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

    /**�±��һ**/

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

DECLARE starthouruse INT DEFAULT 5;/**ʵ�ʵĿ�ʼСʱ**/

DECLARE endhouruse INT DEFAULT 23;/**ʵ�ʵĽ���Сʱ**/



DECLARE i INT DEFAULT 0;/**ѭ�����±�**/

DECLARE result INT DEFAULT 0;/**���ؽ��**/

DECLARE count1 INT ;/**������Ϣ��������**/

DECLARE count2 INT ;/**���쿪����������**/





while i<datecount do/**����ѭ��**/

   IF i > 0 THEN/**������ǵ�һ��ѭ����������Ҫ��һ**/

      set datestart = date_sub(datestart,interval -1 day);	

   END IF;

  /**ȷ��ʵ�ʵĿ�ʼСʱ**/

   IF i=0 THEN

      set starthouruse=starthour;

   ELSE

      set starthouruse=5;

   END IF;

  /**ȷ��ʵ�ʵĽ���Сʱ**/

   IF i=datecount-1 THEN

      set endhouruse=endhour;

   ELSE

      set endhouruse=23;

   END IF;


    while starthouruse <= endhouruse do/**�����Сʱѭ��**/

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

    /**�±��һ**/

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
	DECLARE v_payername VARCHAR(100) DEFAULT "";## ���Ž���/��У
  DECLARE v_payerschool VARCHAR(200) DEFAULT "";## ���Ž���������У
  DECLARE v_payerphone VARCHAR(100) DEFAULT "";## ���Ž����ֻ�����
  DECLARE v_coinnumber DECIMAL(20,2);## ����С�ͱ�����
  DECLARE v_coinpay DECIMAL(20,2) DEFAULT "0.00";## �ѽ���С�ͱ�����
  DECLARE v_classhour INT(11) DEFAULT 0;## �ѽ���ѧʱ
  DECLARE v_coincharge DECIMAL(20,2);## �Ѷһ�С�ͱ�����
  DECLARE v_payerid INT(11);## ����ID���߼�УID
  DECLARE v_type INT(11);## ����������
  DECLARE v_count INT(11);## ��ʱ����1
  DECLARE v_count1 INT(11);## ��ʱ����2
  DECLARE v_count2 INT(11);## ��ʱ����3
  DECLARE v_count3 INT(11);## ��ʱ����4
  DECLARE v_oldcoinpay DECIMAL(20,2); ##�ϰ汾ʱ������С�ͱ�
  DECLARE v_newcoinpay DECIMAL(20,2); ##�°汾ʱ������С�ͱ�
	DECLARE v_receiverid INT(11);## ����С�ͱ�ѧԱID������Ϊ�ϰ汾��ʱ��ʹ��
 -- �������ݽ�����־
	DECLARE done INT DEFAULT FALSE;
  -- �α� DATE(t.addtime) BETWEEN datestart and  dateend and 
  DECLARE cur1 CURSOR FOR SELECT t.ownerid,SUM(t.coinnum),t.ownertype from t_coin_record t where t.type=1 GROUP BY t.ownerid ORDER BY t.ownerid;

	-- ��������־�󶨵��α�
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
  -- ��ʼѭ��
  read_loop: LOOP
    -- ��ȡ�α��������
    FETCH cur1 INTO v_payerid,v_coinnumber,v_type;
    -- ����������ʱ��
    IF done THEN
      LEAVE read_loop;
    END IF;
    -- ��������������ѭ�����¼�\

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
                 ## ȡ�ϰ汾��С�ͱ�ʹ�ü�¼
                BEGIN
                        -- �������ݽ�����־
												DECLARE done_2 INT DEFAULT FALSE;
												-- �α� DATE(t.addtime) BETWEEN datestart and  dateend and 
												DECLARE cur2 CURSOR FOR SELECT receiverid  from t_coin_record where type=1 and ownerid=v_payerid and ownertype=1 and payerid<>0;
												-- ��������־�󶨵��α�
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
	DECLARE v_payername VARCHAR(100) DEFAULT "";## ���Ž���/��У
  DECLARE v_payerschool VARCHAR(200) DEFAULT "";## ���Ž���������У
  DECLARE v_payerphone VARCHAR(100) DEFAULT "";## ���Ž����ֻ�����
  DECLARE v_coinnumber DECIMAL(20,2);## ����С�ͱ�����
  DECLARE v_coinpay DECIMAL(20,2) DEFAULT "0.00";## �ѽ���С�ͱ�����
  DECLARE v_classhour INT(11) DEFAULT 0;## �ѽ���ѧʱ
  DECLARE v_scoincharge DECIMAL(20,2);## �Ѷһ���УС�ͱ�����
  DECLARE v_coincharge DECIMAL(20,2);## �Ѷһ�����С�ͱ�����
  DECLARE v_unscoincharge DECIMAL(20,2);## δ�һ���УС�ͱ�����
  DECLARE v_uncoincharge DECIMAL(20,2);## δ�һ�����С�ͱ�����
  DECLARE v_payerid INT(11);## ����ID���߼�УID
  DECLARE v_type INT(11);## ����������
  DECLARE v_count INT(11);## ��ʱ����1
  DECLARE v_count1 INT(11);## ��ʱ����2
  DECLARE v_count2 INT(11);## ��ʱ����3
  DECLARE v_count3 INT(11);## ��ʱ����4
  DECLARE v_count4 INT(11);## ��ʱ����5
  DECLARE v_count5 INT(11);## ��ʱ����6
  DECLARE v_oldcoinpay DECIMAL(20,2); ##�ϰ汾ʱ������С�ͱ�
  DECLARE v_newcoinpay DECIMAL(20,2); ##�°汾ʱ������С�ͱ�
	DECLARE v_receiverid INT(11);## ����С�ͱ�ѧԱID������Ϊ�ϰ汾��ʱ��ʹ��
 -- �������ݽ�����־
	DECLARE done INT DEFAULT FALSE;
  -- �α� 
  DECLARE cur1 CURSOR FOR SELECT t.ownerid,SUM(t.coinnum),t.ownertype from t_coin_record t where t.type=1 and ownertype=2 and ownerid in(SELECT coachid from t_user_coach where drive_schoolid = p_schoolid) GROUP BY t.ownerid ORDER BY t.ownerid;

	-- ��������־�󶨵��α�
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
  -- ��ʼѭ��
  read_loop: LOOP
    -- ��ȡ�α��������
    FETCH cur1 INTO v_payerid,v_coinnumber,v_type;
    -- ����������ʱ��
    IF done THEN
      LEAVE read_loop;
    END IF;
    -- ��������������ѭ�����¼�\
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
            ## ��ȡ�Ѷһ���УС�ͱ�
            SELECT count(*) into v_count from t_coin_record where payerid=v_payerid and type=4 and ownertype=1 and orderid in (SELECT orderid from t_order where DATE(start_time) BETWEEN datestart and dateend and over_time is not null);
						IF v_count=0 THEN
                set v_scoincharge=0;
						ELSE
									select SUM(coinnum) into v_scoincharge from t_coin_record where payerid=v_payerid and type=4 and ownertype=1 and orderid in (SELECT orderid from t_order where DATE(start_time) BETWEEN datestart and dateend and over_time is not null);
									IF v_scoincharge is NULL THEN
												 set v_scoincharge=0;
									END IF;
						END IF;
           ## ��ȡ�Ѷһ�����С�ͱ�
						SELECT count(*) into v_count from t_coin_record where payerid=v_payerid and type=4 and ownertype=2 and orderid in (SELECT orderid from t_order where DATE(start_time) BETWEEN datestart and dateend and over_time is not null);
						IF v_count=0 THEN
                set v_coincharge=0;
						ELSE
									select SUM(coinnum) into v_coincharge from t_coin_record where payerid=v_payerid and type=4 and ownertype=2 and orderid in (SELECT orderid from t_order where DATE(start_time) BETWEEN datestart and dateend and over_time is not null);
									IF v_coincharge is NULL THEN
												 set v_coincharge=0;
									END IF;
						END IF;
            ## ��ȡδ�һ���УС�ͱ�
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
             ## ��ȡδ�һ�����С�ͱ�
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
  DECLARE v_ownerid INT(11);##�������������Ľ���ID�б�
  DECLARE v_schoolid INT(11);## ��УID
  DECLARE s_ownerid INT(11);## �����Уɸѡ�Ľ���ID�б�
 -- �������ݽ�����־
	DECLARE done INT DEFAULT FALSE;
  -- �α�
  DECLARE cur1 CURSOR FOR SELECT t.ownerid,SUM(t.value) from t_couponget_record t where ownerid<>0 and ownertype=2 GROUP BY t.ownerid ORDER BY t.ownerid;

	-- ��������־�󶨵��α�
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
  -- ��ʼѭ��
  read_loop: LOOP
    -- ��ȡ�α��������
    FETCH cur1 INTO v_ownerid,v_couponnumber;
    -- ����������ʱ��

    IF done THEN
      LEAVE read_loop;
    END IF;
    -- ��������������ѭ�����¼�\
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
  ##��ǰ��

  SELECT MONTH(CURDATE()) into now_month;

  ##ע�����

  SELECT count(*) into coachregister from t_user_coach where DATE(addtime)=querydate;

  ##ע��ѧԱ

  SELECT count(*) into studentregister from t_user_student where DATE(addtime)=querydate;

  ##��֤ͨ����
    
  SELECT count(*) into coachcertification from t_log where DATE(operatetime)=querydate;
  ##����ʵ�ʿ��ο�ʱ

  SELECT count(*) into coachcoursetotal from t_coach_schedule where isrest=1 and date=querydate;

  ##������ɿ�ʱ

  SELECT count(*) into coachcourseconfirm from t_order where DATE(start_time)=querydate and coachstate=2 and studentstate=3 and over_time is not null;

  ##ѧԱԤԼ��ʱ

  SELECT count(*) into studentbooked from t_coach_schedule where date=querydate and bookstate=1;


  ##ѧԱ��ɿ�ʱ

	SELECT count(*) into studentconfirm from t_order where DATE(start_time)=querydate and coachstate=2 and studentstate=3 and over_time is not null;

  ##������-С��ȯ
  
  SELECT count(*) into orderbycoupon from t_order where DATE(start_time)=querydate and coachstate=2 and studentstate=3 and over_time is not null and paytype=2;

  ##������-С�ͱ�

  SELECT count(*) into orderbycoin from t_order where DATE(start_time)=querydate and coachstate=2 and studentstate=3 and over_time is not null and paytype=3;
  ##������-�ֽ�

  SELECT count(*) into orderbyaccount from t_order where DATE(start_time)=querydate and coachstate=2 and studentstate=3 and over_time is not null and paytype=1;
  ##С�̈́�ѧԱʹ��ƽ̨
  SET s_couponplatform=0;
  ##С�̈́�ѧԱʹ�ü�У
  SET s_couponschool=0;
  ##С�̈́�ѧԱʹ�ý���
  SELECT count(*) INTO s_couponcoach from t_couponget_record where ownertype=2 and state=1 and DATE(usetime)=querydate;
  ##С�̈́������һ�ƽ̨
  SET c_couponplatform=0;
	##С�̈́������һ���У
  SET c_couponschool=0;
	##С�̈́������һ�����
  SELECT count(*) into c_couponcoach from t_coupon_coach where state=2 and DATE(gettime)=querydate;
  ##С�ͱ�ѧԱʹ��ƽ̨
  SET s_coinplatform=0;
	##С�ͱ�ѧԱʹ�ü�У
	SELECT sum(coinnum) into s_coinschool from t_coin_record where type=2 and DATE(addtime)=querydate and ownertype=1;
  IF s_coinschool is null THEN
       set s_coinschool=0;
  END IF;
	##С�ͱ�ѧԱʹ�ý���
  SELECT sum(coinnum) into s_coincoach from t_coin_record where type=2 and DATE(addtime)=querydate and ownertype=2;
  IF s_coincoach is null THEN
       set s_coincoach=0;
  END IF;
  ##С�ͱҽ����һ�ƽ̨
  SET c_coinplatform=0;
	##С�ͱҽ����һ���У
  SET c_coinschool=0;
	##С�ͱҽ����һ�����

  SELECT sum(coinnum) into c_coincoach from t_coin_record where type=4 and DATE(addtime)=querydate;
  IF c_coincoach is null THEN
       set c_coincoach=0;
  END IF;
  ##�����ֽ𶩵����

  SELECT sum(total)  into coachorderprice from t_order where DATE(start_time)=querydate and coachstate=2 and studentstate=3 and paytype=1 and over_time is not null;
  IF coachorderprice is null THEN
       set coachorderprice=0;
  END IF;
  ##ʵ�ʽ������ֽ��

  SELECT sum(amount) into coachrecharge from t_coach_apply where state=5 and DATE(updatetime)=querydate;
  IF coachrecharge is null THEN
       set coachrecharge=0;
  END IF;
  ##ѧԱ��ֵ���

  SELECT sum(amount)  into studentrecharge from t_recharge_record where state=1 and DATE(updatetime)=querydate;
  IF studentrecharge is null THEN
       set studentrecharge=0;
  END IF;  
  ##ѧԱ���ֽ��

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
 -- �������ݽ�����־
	DECLARE done INT DEFAULT FALSE;
  DECLARE cur1 CURSOR FOR  SELECT userid,SUM(value) FROM t_couponget_record where ownerid=coachid and state=1 and orderid in (SELECT orderid from t_order where DATE(start_time) BETWEEN datestart and dateend and over_time is not null) GROUP BY userid  ORDER BY userid;


	-- ��������־�󶨵��α�
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
  -- ��ʼѭ��
  read_loop: LOOP
    -- ��ȡ�α��������
    FETCH cur1 INTO s_recid,s_couponpaycount;
    
    -- ����������ʱ��
    IF done THEN
      LEAVE read_loop;
    END IF;
    -- ��������������ѭ�����¼�\
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
				DECLARE s_phone varchar(100);## ѧԱ�ֻ�����
				DECLARE s_name varchar(100); ## ѧԱ����
				DECLARE s_coinnum DECIMAL(20,2);## ѧԱ����С�ͱ�����
				DECLARE s_coinpay DECIMAL(20,2);## ѧԱ��ʹ��С�ͱ�����
				DECLARE v_recid INT(11);## ѧԱID
				DECLARE v_count INT(11);## ��ʱ����
				DECLARE s_classhour INT(11);## ѧԱ�ѽ���ѧʱ
				DECLARE v_ownertype INT(11);## ����������

				
				-- �������ݽ�����־
				 DECLARE done INT DEFAULT FALSE;
         DECLARE cur1 CURSOR FOR  SELECT receiverid,SUM(coinnum) FROM t_coin_record where type=1 and ownerid=coachid  GROUP BY receiverid HAVING SUM(coinnum)<>0 ORDER BY receiverid;
		     DECLARE cur2 CURSOR FOR  SELECT payerid FROM t_coin_record where type=2 and receiverid=coachid and orderid in (SELECT orderid from t_order where DATE(start_time) BETWEEN datestart and dateend and over_time is not null) GROUP BY payerid HAVING SUM(coinnum)<>0 ORDER BY payerid;
        -- ��������־�󶨵��α�
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
 ##����Ǽ�У�Ļ�
IF ftype=1 THEN	
			
							OPEN cur1;
								-- ��ʼѭ��
								read_loop: LOOP
									-- ��ȡ�α��������
									FETCH cur1 INTO v_recid,s_coinnum;
									-- ����������ʱ��
									IF done THEN
										LEAVE read_loop;
									END IF;
									-- ��������������ѭ�����¼�\	
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
 ##����ǽ����Ļ�
IF ftype=2 THEN	

					      SET done=0;
								OPEN cur2;
								-- ��ʼѭ��
								read_loop1: LOOP
									-- ��ȡ�α��������
           
								FETCH cur2 INTO v_recid;
									-- ����������ʱ��
									IF done THEN
										LEAVE read_loop1;
									END IF;
									
									-- ��������������ѭ�����¼�\
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
				DECLARE s_phone varchar(100);## ѧԱ�ֻ�����
				DECLARE s_name varchar(100); ## ѧԱ����
				DECLARE s_scoinnum DECIMAL(20,2);## ѧԱ���ռ�УС�ͱ�����
        DECLARE s_coinnum DECIMAL(20,2);## ѧԱ���ս���С�ͱ�����
        DECLARE s_usedscoinnum DECIMAL(20,2);## ѧԱ��ʹ�ü�УС�ͱ�����
        DECLARE s_usedcoinnum DECIMAL(20,2);## ѧԱ��ʹ�ý���С�ͱ�����
				DECLARE s_coinpay DECIMAL(20,2);## ѧԱ��ʹ��С�ͱ�����
				DECLARE v_recid INT(11);## ѧԱID
				DECLARE v_count INT(11);## ��ʱ����
				DECLARE s_classhour INT(11);## ѧԱ�ѽ���ѧʱ
				DECLARE v_ownertype INT(11);## ����������

				
				-- �������ݽ�����־
				 DECLARE done INT DEFAULT FALSE;
		     DECLARE cur2 CURSOR FOR  SELECT payerid FROM t_coin_record where type=2 and receiverid=coachid and orderid in (SELECT orderid from t_order where DATE(start_time) BETWEEN datestart and dateend and over_time is not null) GROUP BY payerid HAVING SUM(coinnum)<>0 ORDER BY payerid;
        -- ��������־�󶨵��α�
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
								-- ��ʼѭ��
								read_loop1: LOOP
									-- ��ȡ�α��������
           
								FETCH cur2 INTO v_recid;
									-- ����������ʱ��
									IF done THEN
										LEAVE read_loop1;
									END IF;
									
									-- ��������������ѭ�����¼�\
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
 -- �������ݽ�����־
	DECLARE done INT DEFAULT FALSE;
 -- �α�
  DECLARE cur1 CURSOR FOR SELECT modelid from t_user_coach t where modelid is not NULL and SUBSTRING(modelid,LENGTH(modelid),LENGTH(modelid))=',';
  DECLARE cur2 CURSOR FOR SELECT coachid,modelid from t_user_coach t where modelid is not NULL;

	-- ��������־�󶨵��α�
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
  OPEN cur1;
   
  -- ��ʼѭ��
  read_loop: LOOP
    -- ��ȡ�α��������
    FETCH cur1 INTO v_model;
    -- ����������ʱ��
    IF done THEN
      LEAVE read_loop;
    END IF;
    -- ��������������ѭ�����¼�\
      set v_model=SUBSTRING(v_model,1,2);
     ## SELECT model;
      update t_user_coach set modelid=v_model where modelid is not NULL and SUBSTRING(modelid,LENGTH(modelid),LENGTH(modelid))=',';
  END LOOP;
 CLOSE cur1;

  -- �α�


  set done=0;
  OPEN cur2;
   
  -- ��ʼѭ��
  read_loop: LOOP
    -- ��ȡ�α��������
    FETCH cur2 INTO v_coachid,v_model1;
    -- ����������ʱ��
    IF done THEN
      LEAVE read_loop;
    END IF;
    -- ��������������ѭ�����¼�\
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
/*1�����½���ǩԼ״̬*/
/*signstate=0��ʾδǩԼ��signstate=1��ʾ��ǩԼδ���ڣ�signstate=2��ʾ��ǩԼ����*/
update t_user_coach c set c.signstate = 0 where c.signexpired <=now() and c.signstate = 1;

/*2�����½�������ο���״̬*/
/*freecoursestate=0��ʾ�ӵ��쿪ʼ5����δ������Σ�freecoursestate=1��ʾ�ӵ��쿪ʼ55����δ�������*/
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
  
  -- �������ݽ�����־
	DECLARE done INT DEFAULT FALSE;
  -- �α�
  DECLARE cur1 CURSOR FOR SELECT DISTINCT t1.receiverid from t_coin_record t1 where t1.type=2 and t1.receiverid not in (SELECT t2.ownerid from t_coin_record t2 where t2.type=1);

	-- ��������־�󶨵��α�
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

  
  OPEN cur1;


 read_loop: LOOP
    -- ��ȡ�α��������
    FETCH cur1 INTO a1;
    -- ����������ʱ��

    IF done THEN
      LEAVE read_loop;
    END IF;
    -- ��������������ѭ�����¼�\
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