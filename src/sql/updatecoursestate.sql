


drop procedure if exists `updatecoursestate`;
delimiter $;
create procedure updatecoursestate() 
BEGIN   
DECLARE  v_coachid int;  
DECLARE v_coursestate int;

-- 遍历数据结束标志
DECLARE done INT DEFAULT FALSE;

 -- 游标
DECLARE rs CURSOR FOR SELECT coachid,coursestate FROM t_user_coach;   
-- 将结束标志绑定到游标
DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

OPEN rs; /*开启游标*/ 


-- 开始循环
  read_loop: LOOP
    -- 提取游标里的数据，这里只有一个，多个的话也一样；
    FETCH rs INTO v_coachid,v_coursestate;
    -- 声明结束的时候
    IF done THEN
      LEAVE read_loop;
    END IF;
    -- 这里做你想做的循环的事件
	select getcoachstate(v_coachid,10,curdate(),5,23,0) into v_coursestate;
-- 	select v_coachid,v_coursestate;
	update t_user_coach set coursestate=v_coursestate where coachid=v_coachid;

  END LOOP;


-- set @RunSQL = "select *  from tmp_paymentReport";      prepare smtm from @RunSQL;      execute smtm;  

END 