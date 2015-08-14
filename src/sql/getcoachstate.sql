DELIMITER $$

DROP FUNCTION IF EXISTS `driver`.`getcoachstate`$$

CREATE FUNCTION `getcoachstate`(`coachid` INT, `datecount` INT, `datestart` TEXT, `starthour` INT, `endhour` INT, `subjectid` INT) RETURNS tinyint(4)
    COMMENT '计算教练某个时间段内是否有可以预订的时间 返回值 0表示教练在查询的时间没没有时间  1 表示教练有时间'
BEGIN
DECLARE starthouruse INT DEFAULT 5;/**实际的开始小时**/
DECLARE endhouruse INT DEFAULT 23;/**实际的结束小时**/

DECLARE i INT DEFAULT 0;/**循环的下标**/
DECLARE result INT DEFAULT 0;/**返回结果**/
DECLARE count1 INT DEFAULT 0;/**当天休息数据数量**/
DECLARE count2 INT DEFAULT 0;/**当天开课数据数量**/
DECLARE count3 INT DEFAULT 0;/**该小时被预订的数据数量**/
DECLARE count4 INT DEFAULT 0;/**该小时被预订的数据数量**/
DECLARE count5 INT DEFAULT 0;/**该小时休息的数据数量**/
DECLARE count6 INT DEFAULT 0;/**该天是否全天被预定**/
DECLARE count7 INT DEFAULT 0;/**该天开课的小时数数据量**/
DECLARE defaultSubID INT DEFAULT 0;/**该小时休息的数据数量**/
DECLARE coachDefaultSubID INT DEFAULT 0;/**教练的默认日程设置**/
IF subjectid <> 0 THEN
   select coach_default_subject into defaultSubID from t_systemset where 1=1;/**系统的默认配置**/
END IF;

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
  /**查询全天状态**/
   select count(*) into count1 from t_coach_schedule where t_coach_schedule.coachid = coachid and t_coach_schedule.date = datestart and t_coach_schedule.hour = 0 and t_coach_schedule.state = 1;
   IF count1=0 THEN/**全天休息的话**/
      set i = i+1;
   ELSE/**全天开课的话**/
      while starthouruse <= endhouruse do/**当天的小时循环**/
       select count(*) into count6 from t_coach_booktime where t_coach_booktime.coachid = coachid and t_coach_booktime.date = datestart;
       select count(*) into count7 from t_coach_schedule where t_coach_schedule.coachid = coachid and t_coach_schedule.date = datestart and t_coach_schedule.hour <> 0 and t_coach_schedule.isrest = 0 and t_coach_schedule.expire = 0;
       IF count6=count7 THEN
            return 1;
       ELSE
         select count(*) into count3 from t_coach_booktime where t_coach_booktime.coachid = coachid and t_coach_booktime.date = datestart and t_coach_booktime.bookedtime = starthouruse;
         IF count3>0 THEN/**时间点已经被预订**/
            set starthouruse=starthouruse+1;
         ELSE
            select count(*),isrest,subjectid into count2,count4,count5 from t_coach_schedule where t_coach_schedule.coachid = coachid and t_coach_schedule.date = datestart and t_coach_schedule.hour = starthouruse;
            IF count2>0 THEN
			      IF count4=1 THEN/**休息**/
				      set starthouruse=starthouruse+1;
               ELSE
				      IF subjectid=0 THEN
					      return 1;
					   ELSE
					      IF count5 = subjectid THEN
						      return 1;
						   ELSE
						      set starthouruse=starthouruse+1;
						   END IF;
					   END IF;
               END IF;
            ELSE
			     /* IF starthouruse=12 or starthouruse=18 or starthouruse=5 or starthouruse=6 THEN
				      set starthouruse=starthouruse+1;
				   ELSE*/
				      IF subjectid=0 THEN
					      return 1;
					   ELSE
					      select subjectid into coachDefaultSubID from t_default_schedule where t_default_schedule.coachid = coachid and t_default_schedule.hour = starthouruse;
					      IF coachDefaultSubID = 0 THEN
					         IF defaultSubID = subjectid THEN
				               return 1;
				            ELSE
				               set starthouruse=starthouruse+1;
				            END IF;
					      ELSE
					         IF coachDefaultSubID = subjectid THEN
					            return 1;
					         ELSE
					            set starthouruse=starthouruse+1;
					         END IF;
					      END IF;
			       --  END IF;
		         END IF;
	         END IF;
			END IF;
    END IF;
		END while;
    /**下标加一**/
     set i=i+1;
   END IF;
end while;
return 0;
END $$
DELIMITER ;
