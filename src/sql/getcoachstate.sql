DELIMITER $$

DROP FUNCTION IF EXISTS `driver`.`getcoachstate`$$

CREATE FUNCTION `getcoachstate`(`coachid` INT, `datecount` INT, `datestart` TEXT, `starthour` INT, `endhour` INT, `subjectid` INT) RETURNS tinyint(4)
    COMMENT '计算教练某个时间段内是否有可以预订的时间 返回值 0表示教练在查询的时间没没有时间  1 表示教练有时间'
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

          SELECT isrest,subjectid into count1,count2 from t_coach_schedule where t_coach_schedule.date=datestart and t_coach_schedule.coachid=coachid and t_coach_schedule.hour=starthouruse;
          IF count1=0 THEN
              IF count2=0 THEN
                 return 1;
              ELSE
                 IF count2 = subjectid THEN

						      return 1;

								 ELSE

										set starthouruse=starthouruse+1;
                    
								 END IF;

              END IF;
          ELSE
							set starthouruse=starthouruse+1;
         END IF;
		END while;

    /**下标加一**/

     set i=i+1;


end while;

return 0;
END $$
DELIMITER ;
