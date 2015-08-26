



DROP PROCEDURE IF EXISTS  `coinreportmonthly`;

DELIMITER $$

 CREATE PROCEDURE `coinreportmonthly`(`datestart` TEXT,`dateend` TEXT)
BEGIN
  DECLARE v_payername VARCHAR(100) DEFAULT "";
  DECLARE v_payerschool VARCHAR(100) DEFAULT "";
  DECLARE v_payerphone VARCHAR(100) DEFAULT "";
  DECLARE v_coinnumber DOUBLE;
  DECLARE v_coinpay DOUBLE;
  DECLARE  v_payerid INT;
  DECLARE  v_type INT;
  
 -- 遍历数据结束标志
	DECLARE done INT DEFAULT FALSE;
  -- 游标
  DECLARE cur1 CURSOR FOR SELECT t.ownerid,SUM(t.coinnum),t.ownertype from t_coin_record t where DATE(t.addtime) BETWEEN datestart and  dateend and t.type=1 GROUP BY t.payerid ORDER BY t.ownerid DESC;

	-- 将结束标志绑定到游标
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

  
  OPEN cur1;
   
  create TEMPORARY table if not exists tmpTable(  
           id int(10) primary key not null auto_increment,
           c_name VARCHAR(100),
           c_school VARCHAR(100),  
           c_phone VARCHAR(100),  
           c_coinnumber DOUBLE
 
  );  
  TRUNCATE tmpTable;
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
 	  ELSE   
        IF v_type = 2 THEN   
           ## select realname,phone into v_payername,v_payerphone from  t_user_coach t1  where coachid = v_payerid;
           ## SELECT name into v_payerschool from t_drive_school_info where 
            select realname,t2.name,phone into v_payername,v_payerschool,v_payerphone from  t_user_coach t1, t_drive_school_info t2 where coachid = v_payerid and t1.drive_schoolid=t2.schoolid;           
        END IF;
    END IF; 
   INSERT into tmpTable(c_name,c_school,c_phone,c_coinnumber) SELECT v_payername,v_payerschool,v_payerphone,v_coinnumber;   
  END LOOP;
  SELECT * from tmpTable;
  CLOSE cur1;
END $$
DELIMITER ;

