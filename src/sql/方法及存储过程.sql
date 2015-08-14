-- --------------------------------------------------------
-- 主机:                           120.25.236.228
-- 服务器版本:                        5.5.43-0ubuntu0.14.04.1 - (Ubuntu)
-- 服务器操作系统:                      debian-linux-gnu
-- HeidiSQL 版本:                  7.0.0.4393
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 导出 db_guangda 的数据库结构
CREATE DATABASE IF NOT EXISTS `db_guangda` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `db_guangda`;


-- 导出  过程 db_guangda.coachAccountDaily 结构
DELIMITER //
CREATE PROCEDURE `coachAccountDaily`(`querydate` date)
    COMMENT '教练端账户管理日报'
BEGIN
	DECLARE a2,a3,a4,a5,a6,a7,a8,a9,a11 INT(10);

	SELECT sum(money) into a2 from t_coach_balance_record where date(addtime)=(querydate-1);
	SELECT sum(amount) into a3 from t_recharge_record WHERE type=1 and state=1 and date(updatetime)=querydate;
	SELECT sum(amount) into a4 from t_coach_apply where date(addtime)=querydate;
	SELECT sum(total-delmoney) into a5 from t_order where studentstate=3 and coachstate=2 and date(over_time)=querydate;
	SELECT sum(gmoney) into a7 from t_user_coach;
	SELECT sum(total-delmoney) into a9 from t_order where studentstate<>3 and coachstate<>2 and date(over_time)<querydate;
	select 0 into a11;

	select a2,a3,a4,a5,a2+a3-a4+a5 a6,a7,a6-a7 a8,a9,a11,a9+a5-a11 a12,(SELECT sum(total-delmoney) from t_order where studentstate<>3 and coachstate=2 and date(date)=querydate and over_time is null) a13;
END//
DELIMITER ;


-- 导出  过程 db_guangda.coachApplyDaily 结构
DELIMITER //
CREATE PROCEDURE `coachApplyDaily`(`querydate` date)
    COMMENT '教练当日提现日报'
BEGIN
	select realname,realname1,a1,a2,a1+a2,a4,a1+a2-a4 from (select DISTINCT(u.coachid),realname,if(a.schoolid=0,`name`,realname) realname1 from t_user_coach u join t_coach_apply a on u.coachid=a.coachid left join t_drive_school_info s on a.schoolid=s.schoolid) t1 left JOIN 
	#期初累计金额
(select coachid,sum(amount) a1 from t_coach_apply where date(addtime)<querydate GROUP BY coachid) t2 on t1.coachid=t2.coachid LEFT JOIN
#当日申请金额	
(select coachid,sum(amount) a2 from t_coach_apply where date(addtime)=querydate GROUP BY coachid) t3 on t3.coachid=t2.coachid LEFT JOIN
	#当日已处理累计金额
	(select coachid,sum(amount) a4 from t_coach_apply where date(addtime)<=querydate and state<>0 GROUP BY coachid) t6 on t1.coachid=t6.coachid;
END//
DELIMITER ;


-- 导出  过程 db_guangda.coachDaily 结构
DELIMITER //
CREATE PROCEDURE `coachDaily`(`querydate` date)
    COMMENT '教练日报 '
BEGIN
	SELECT
	realname,drive_school,a1,a2,a3,a1+a2-a3 a4,a5,a6,a7,a5+a6-a7 a8,a9 from 
	(
		select coachid,if(drive_schoolid is null or drive_schoolid=0,drive_school,`name`) drive_school,realname from t_user_coach left join t_drive_school_info on drive_schoolid=schoolid
	) t1
#期初未完成订单数
left JOIN (
	SELECT
		coachid,
		count(1) a1
	FROM
		t_order
	WHERE
		date(creat_time)<querydate
	AND (
		studentstate <> 3
		OR coachstate <> 2)
GROUP BY coachid
	) t2
 on t1.coachid=t2.coachid
#当日生产订单数
LEFT JOIN (SELECT
			coachid,
			count(1) a2
		FROM
			t_order
		WHERE
			date(creat_time)=querydate
GROUP BY coachid) t3
on t1.coachid=t3.coachid
#当日完成订单数
left join (SELECT
			coachid,
			count(1) a3
		FROM
			t_order 
		WHERE
			studentstate=3 and coachstate=2 and date(over_time)=querydate
GROUP BY coachid) t4
on t1.coachid=t4.coachid
#期初未完成学时数
LEFT JOIN (SELECT
coachid,
			sum(time) a5
		FROM
			t_order 
		WHERE
			date(creat_time)< querydate
		AND (studentstate<>3 or coachstate<>2)
GROUP BY coachid) t6 
on t1.coachid=t6.coachid
#当日预约学时数
LEFT JOIN (SELECT
coachid,
			sum(time) a6
		FROM
			t_order 
		WHERE
			date(creat_time)= querydate
GROUP BY coachid) t7
on t1.coachid=t7.coachid
#当日完成学时数
LEFT JOIN(
SELECT
coachid,
			sum(time) a7
		FROM
			t_order 
		WHERE
			studentstate=3 and coachstate=2 and date(over_time)=querydate
GROUP BY coachid
) t8
on t1.coachid=t8.coachid

#当日教练账户余额
LEFT JOIN(SELECT coachid,money a9 from t_coach_balance_record where date(addtime)=querydate) t10
on t1.coachid=t10.coachid;
END//
DELIMITER ;


-- 导出  函数 db_guangda.getcoachstate 结构
DELIMITER //
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
			      IF starthouruse=12 or starthouruse=18 or starthouruse=5 or starthouruse=6 THEN
				      set starthouruse=starthouruse+1;
				   ELSE
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
END//
DELIMITER ;


-- 导出  函数 db_guangda.getdistance 结构
DELIMITER //
CREATE FUNCTION `getdistance`(`longtitude1` double, `latitude1` double, `longtitude2` double, `latitude2` double) RETURNS double
begin
declare EARTH_RADIUS double;
declare pi double;
declare radLat1 double; 
declare radLat2 double;
declare diffLat double;
declare diffLng double;
declare distance double;

set EARTH_RADIUS = 6378.137;
set pi = PI();
set radLat1 = latitude1*pi/180.0;
set radLat2 = latitude2*pi/180.0;
set diffLat = radLat1 - radLat2;
set diffLng = longtitude1*pi/180.0 - longtitude2*pi/180.0;
set distance = 2 * ASIN(SQRT(POW(SIN(diffLat/2),2) + COS(radLat1)*COS(radLat2)*POW(SIN(diffLng/2),2)));
set distance = distance * EARTH_RADIUS;
set distance = ROUND(distance * 10000) / 10000;
return distance;
end//
DELIMITER ;


-- 导出  过程 db_guangda.schoolBill 结构
DELIMITER //
CREATE PROCEDURE `schoolBill`(`startdate` date,`enddate` date,`schoolname` varchar(10))
    COMMENT '驾校对账单'
BEGIN
	SELECT drive_school,realname,a1,a2,a3,a1+a2-a3 a4,a5,a6,a7,a5+a6-a7 a8,a9,a10,0 a11,a9+a10,a13,a14,a15,a16,a13+a14-a15+a16 a17,a18,a13+a14-a15+a16-a18 a19 from
(select coachid,if(drive_schoolid is null or drive_schoolid=0,drive_school,`name`) drive_school,realname,gmoney a18 from t_user_coach left join t_drive_school_info on drive_schoolid=schoolid) t1
LEFT JOIN (select coachid,count(1) a1 from t_order where (studentstate<>3 or coachstate<>2) and date(creat_time)<startdate GROUP BY coachid) t2 on t1.coachid=t2.coachid
LEFT JOIN (select coachid,count(1) a2 from t_order where date(creat_time) BETWEEN startdate and enddate GROUP BY coachid) t3 on t1.coachid=t3.coachid
LEFT JOIN (select coachid,count(1) a3 from t_order where studentstate=3 AND coachstate=2 and date(creat_time) BETWEEN startdate and enddate GROUP BY coachid) t4 on t1.coachid=t4.coachid
LEFT JOIN (select coachid,sum(time) a5 from t_order where date(creat_time)<startdate GROUP BY coachid) t5 on t1.coachid=t5.coachid
LEFT JOIN (select coachid,sum(time) a6 from t_order where date(creat_time) BETWEEN startdate and enddate GROUP BY coachid) t6 on t1.coachid=t6.coachid
LEFT JOIN (select coachid,sum(time) a7 from t_order where studentstate=3 AND coachstate=2 and date(creat_time) BETWEEN startdate and enddate GROUP BY coachid) t7 on t1.coachid=t7.coachid
LEFT JOIN (select coachid,sum(total-delmoney) a9 from t_order where (studentstate<>3 or coachstate<>2) and date(creat_time)<startdate GROUP BY coachid) t8 on t1.coachid=t8.coachid
LEFT JOIN (select coachid,sum(total-delmoney) a10 from t_order where studentstate=3 and coachstate=2 and date(creat_time) BETWEEN startdate and enddate GROUP BY coachid) t9 on t1.coachid=t9.coachid
LEFT JOIN (select coachid,money a13 from t_coach_balance_record where date(addtime)=startdate-1) t10 on t1.coachid=t10.coachid
LEFT JOIN (select userid,sum(amount) a14 from t_recharge_record where type=1 and state=1 and date(updatetime) BETWEEN startdate and enddate GROUP BY userid) t11 on t1.coachid=t11.userid
LEFT JOIN (select coachid,sum(amount) a15 from t_coach_apply where date(updatetime) BETWEEN startdate and enddate GROUP BY coachid) t12 on t1.coachid=t12.coachid
LEFT JOIN (select coachid,sum(total-delmoney) a16 from t_order where studentstate=3 and coachstate=2 and date(over_time) BETWEEN startdate and enddate GROUP BY coachid) t13 on t1.coachid=t13.coachid
where drive_school=schoolname;
END//
DELIMITER ;


-- 导出  过程 db_guangda.schoolDaily 结构
DELIMITER //
CREATE PROCEDURE `schoolDaily`(`querydate` date)
    COMMENT '驾校日报'
BEGIN
		create TEMPORARY table if not exists tmpTable(  
           id int(10) primary key not null auto_increment,
           a1 varchar(255),  
           a2 varchar(255),  
           a3 int(10),  
           a4 int(10),
           a5 int(10),
           a6 int(10),
           a7 int(10),
           a8 int(10),
           a9 int(10),
           a10 int(11),
           a11 decimal(20,2)
  );  

truncate TABLE tmpTable;

insert into tmpTable(a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11) SELECT drive_school,realname,a1,a2,a3,a1+a2-a3,a5,a6,a7,a5+a6-a7,a9 from 
#姓名 驾校名
	(select coachid,if(drive_schoolid is null or drive_schoolid=0,drive_school,`name`) drive_school,realname from t_user_coach left join t_drive_school_info on drive_schoolid=schoolid) t1
#期初未完成订单数
left JOIN (
	SELECT
		coachid,
		count(1) a1
	FROM
		t_order
	WHERE
		date(creat_time)<querydate
	AND (
		studentstate <> 3
		OR coachstate <> 2)
GROUP BY coachid
	) t2
 on t1.coachid=t2.coachid
#当日生产订单数
LEFT JOIN (SELECT
			coachid,
			count(1) a2
		FROM
			t_order
		WHERE
			date(creat_time)=querydate
GROUP BY coachid) t3
on t1.coachid=t3.coachid
#当日完成订单数
left join (SELECT
			coachid,
			count(1) a3
		FROM
			t_order 
		WHERE
			studentstate=3 and coachstate=2 and date(over_time)=querydate
GROUP BY coachid) t4
on t1.coachid=t4.coachid

#期初预约学时数
LEFT JOIN (SELECT
		coachid,
			sum(time) a5
		FROM
			t_order 
		WHERE
			date(creat_time)<querydate
GROUP BY coachid) t6 
on t1.coachid=t6.coachid
#当日预约学时数
LEFT JOIN (SELECT
coachid,
			sum(time) a6
		FROM
			t_order 
		WHERE
			date(creat_time)= querydate
GROUP BY coachid) t7
on t1.coachid=t7.coachid
#当日完成学时数
LEFT JOIN(
SELECT
coachid,
			sum(time) a7
		FROM
			t_order 
		WHERE
			studentstate=3 and coachstate=2 and date(over_time)=querydate
GROUP BY coachid
) t8
on t1.coachid=t8.coachid

#当日教练账户余额
LEFT JOIN(SELECT coachid,money a9 from t_coach_balance_record where date(addtime)=querydate) t10
on t1.coachid=t10.coachid;


		create TEMPORARY table if not exists tmpTable1(  
           id int(10) primary key not null auto_increment,
           a1 varchar(255),  
           a2 varchar(255),  
           a3 int(10),  
           a4 int(10),
           a5 int(10),
           a6 int(10),
           a7 int(10),
           a8 int(10),
           a9 int(10),
           a10 int(11),
           a11 decimal(20,2)
  );  

truncate TABLE tmpTable1;

#各驾校小计
insert into tmpTable1(a1,a3,a4,a5,a6,a7,a8,a9,a10,a11) select concat(a1,'小计'),sum(a3),sum(a4),sum(a5),sum(a6),sum(a7),sum(a8),sum(a9),sum(a10),sum(a11) from tmpTable GROUP BY a1;

insert into tmpTable(a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11) select a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11 from tmpTable1;

#合计
truncate TABLE tmpTable1;

insert into tmpTable1(a3,a4,a5,a6,a7,a8,a9,a10,a11) select sum(a3),sum(a4),sum(a5),sum(a6),sum(a7),sum(a8),sum(a9),sum(a10),sum(a11) from tmpTable;

insert into tmpTable(a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11) select ' 合计',a2,a3,a4,a5,a6,a7,a8,a9,a10,a11 from tmpTable1;

select * from tmpTable ORDER BY a1 desc;

END//
DELIMITER ;


-- 导出  过程 db_guangda.studentAccountDaily 结构
DELIMITER //
CREATE PROCEDURE `studentAccountDaily`(`querydate` date)
    COMMENT '学员端账户管理日报'
BEGIN
	DECLARE a2,a3,a4,a5,a6,a7,a8,a9,a10 INT(10);

	select sum(money) into a2 from t_student_balance_record where date(addtime)=(querydate-1);
	SELECT sum(amount) into a3 from t_recharge_record where date(updatetime)=querydate;
	select sum(amount) into a4 from t_student_apply where date(addtime)=querydate;
	SELECT sum(total-delmoney) into a5 from t_order where studentstate<>3 and coachstate<>2 and date(creat_time)<=querydate;
	SELECT sum(total-delmoney) into a7 from t_order where studentstate<>3 and coachstate<>2 and date(creat_time)<querydate;
	SELECT sum(total-delmoney) into a8 from t_order where studentstate<>3 and coachstate<>2 and date(creat_time)=querydate;
	SELECT sum(total-delmoney) into a9 from t_order where studentstate=4 and date(creat_time)=querydate;
	SELECT sum(total-delmoney) into a10 from t_order where studentstate=3 and coachstate=2 and date(over_time)=querydate;

	select a2,a3,a4,a5,a2+a3-a4-a5 a6,a7,a8,a9,a10,a7+a8-a9-a10 a11;
END//
DELIMITER ;


-- 导出  过程 db_guangda.studentApplyDaily 结构
DELIMITER //
CREATE PROCEDURE `studentApplyDaily`(`querydate` date)
    COMMENT '学员提现日报'
BEGIN
SELECT
	realname,
	a1,
	a2,
	a1+a2 a3,
	a4,
	a1+a2-a4 a5
FROM
		(SELECT
			studentid,
			realname
		FROM
			t_user_student) t1
#期初累计取款申请金额
left join 
	(
		SELECT
			userid,
			sum(amount) a1
		FROM
			t_student_apply
		WHERE
			date(addtime)<querydate
		GROUP BY
			userid
	) t2
on t1.studentid=t2.userid

#当日取款申请金额
left JOIN 
	(
		SELECT
			userid,
			sum(amount) a2
		FROM
			t_student_apply
		WHERE
			date(addtime)=querydate
		GROUP BY
			userid
	) t3
on t1.studentid=t3.userid

#当日已处理取款申请金额
left JOIN 
	(
		SELECT
			userid,
			sum(amount) a4
		FROM
			t_student_apply
		WHERE
			date(addtime)<=querydate
and state=1
		GROUP BY
			userid
	) t5
on t1.studentid=t5.userid;
END//
DELIMITER ;


-- 导出  过程 db_guangda.systemDaliy 结构
DELIMITER //
CREATE PROCEDURE `systemDaliy`(`querydate` date)
    COMMENT '系统日报'
BEGIN

DECLARE a1 INT(10);
DECLARE a2 INT(10);
DECLARE a3 INT(10);
DECLARE b1 INT(10);
DECLARE b2 INT(10);
DECLARE b3 INT(10);

	create TEMPORARY table if not exists tmpTable(  
           id int(10) primary key not null auto_increment,
           a1 int(10),  
           a2 int(10),  
           a3 int(10)
  );  

truncate TABLE tmpTable;

select count(1) into a1 from t_user_coach where date(addtime)=querydate;
select count(1) into a2 from t_user_coach;
select count(1) into b1 from t_user_student where date(addtime)=querydate;
select count(1) into b2 from t_user_student;
select count(1) into a3 from (select distinct(userid) from t_active_record where usertype=1 and date(addtime)=querydate) t;
select count(1) into b3 from (select distinct(userid) from t_active_record where usertype=2 and date(addtime)=querydate) t;

#注册人数记录
insert into tmpTable(a1,a2,a3) select a1+b1,a2+b2,a3+b3;
insert into tmpTable(a1,a2,a3) select a1,a2,a3;
insert into tmpTable(a1,a2,a3) select b1,b2,b3;

#订单数记录
insert into tmpTable(a1,a2,a3) select (select count(1) from t_order where date(creat_time)=querydate) a1,
																			(select count(1) from t_order where studentstate=3 and coachstate=2 and date(over_time)=querydate) a2,
																			(select count(1) from t_order where (studentstate<>3 or coachstate<>2) and date(creat_time)<=querydate) a3;

#预约时间记录
insert into tmpTable(a1,a2,a3) select (select sum(time) from t_order where date(creat_time)=querydate) a1,
																			(select sum(time) from t_order where studentstate=3 and coachstate=2 and date(over_time)=querydate) a2,
																			(select sum(time) from t_order where (studentstate<>3 or coachstate<>2) and date(creat_time)<=querydate) a3;

select * from tmpTable;
END//
DELIMITER ;


-- 导出  过程 db_guangda.xiaobaDaily 结构
DELIMITER //
CREATE PROCEDURE `xiaobaDaily`(`startdate` date,`enddate` date)
    COMMENT '小巴券日报'
BEGIN
	#Routine body goes here...
			create TEMPORARY table if not exists tmpTable(  
           id int(10) primary key not null auto_increment,
           a1 varchar(255),  
           a2 varchar(255),  
           a3 int(10),  
           a4 int(10),
           a5 int(10),
           a6 int(10),
           a7 int(10),
           a8 int(10),
           a9 int(10),
           a10 int(10),
           a11 int(10),
           a12 int(10),
           a13 int(10),
           a14 int(10),
           a15 int(10),
           a16 int(10),
           a17 int(10),
           a18 int(10),
           a19 int(10),
           a20 int(10)
  );  

truncate TABLE tmpTable;
#平台抵用券
insert into tmpTable(a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15,a16,a17,a18,a19,a20)
select '抵价券',"平台",
#发型情况
(SELECT sum(pub_count) from t_coupon where ownertype=0 and date(addtime)<startdate and coupontype=2),
(SELECT sum(value*(pub_count)) from t_coupon where ownertype=0 and date(addtime)<startdate and coupontype=2),
(SELECT sum(pub_count) from t_coupon where ownertype=0 and date(addtime) BETWEEN startdate and enddate and coupontype=2),
(SELECT sum(value*(pub_count)) from t_coupon where ownertype=0 and date(addtime) BETWEEN startdate and enddate and coupontype=2),
(SELECT sum(pub_count) from t_coupon where ownertype=0 and date(addtime)<=enddate and coupontype=2),
(SELECT sum(value*(pub_count)) from t_coupon where ownertype=0 and date(addtime)<=enddate and coupontype=2),
#领用情况
(SELECT count(1) from t_couponget_record where ownertype=0 and date(gettime)<startdate and coupontype=2),
(SELECT sum(value) from t_couponget_record where ownertype=0 and date(gettime)<startdate and coupontype=2),
(SELECT count(1) from t_couponget_record where ownertype=0 and date(gettime) between startdate and enddate and coupontype=2),
(SELECT sum(value) from t_couponget_record where ownertype=0 and date(gettime) between startdate and enddate and coupontype=2),
(SELECT count(1) from t_couponget_record where ownertype=0 and date(gettime)<=enddate and coupontype=2),
(SELECT sum(value) from t_couponget_record where ownertype=0 and date(gettime)<=enddate and coupontype=2),
#使用情况
(SELECT count(1) from t_couponget_record where ownertype=0 and date(gettime) between startdate and enddate and coupontype=2 and state=1),
(SELECT sum(value) from t_couponget_record where ownertype=0 and date(gettime) between startdate and enddate and coupontype=2 and state=1),
(SELECT count(1) from t_couponget_record where ownertype=0 and date(gettime) between startdate and enddate and coupontype=2 and date(end_time)>enddate and state=0),
(SELECT sum(value) from t_couponget_record where ownertype=0 and date(gettime) between startdate and enddate and coupontype=2 and date(end_time)>enddate and state=0),
(SELECT count(1) from t_couponget_record where ownertype=0 and date(gettime)<=enddate and coupontype=2 and state=0),
(SELECT sum(value) from t_couponget_record where ownertype=0 and date(gettime)<=enddate and coupontype=2 and state=0);

#平台时间券
insert into tmpTable(a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15,a16,a17,a18,a19,a20)
select '时间券',"平台",
#发行情况
(SELECT sum(pub_count) from t_coupon where ownertype=0 and date(addtime)<startdate and coupontype=1),
(SELECT sum(value) from t_coupon where ownertype=0 and date(addtime)<startdate and coupontype=1),
(SELECT sum(pub_count) from t_coupon where ownertype=0 and date(addtime) BETWEEN startdate and enddate and coupontype=1),
(SELECT sum(value) from t_coupon where ownertype=0 and date(addtime) BETWEEN startdate and enddate and coupontype=1),
(SELECT sum(pub_count) from t_coupon where ownertype=0 and date(addtime)<=enddate and coupontype=1),
(SELECT sum(value) from t_coupon where ownertype=0 and date(addtime)<=enddate and coupontype=1),
#领用情况
(SELECT count(1) from t_couponget_record where ownertype=0 and date(gettime)<startdate and coupontype=1),
(SELECT sum(value) from t_couponget_record where ownertype=0 and date(gettime)<startdate and coupontype=1),
(SELECT count(1) from t_couponget_record where ownertype=0 and date(gettime) between startdate and enddate and coupontype=1),
(SELECT sum(value) from t_couponget_record where ownertype=0 and date(gettime) between startdate and enddate and coupontype=1),
(SELECT count(1) from t_couponget_record where ownertype=0 and date(gettime)<=enddate and coupontype=1),
(SELECT sum(value) from t_couponget_record where ownertype=0 and date(gettime)<=enddate and coupontype=1),
#使用情况
(SELECT count(1) from t_couponget_record where ownertype=0 and date(gettime) between startdate and enddate and coupontype=1 and state=1),
(SELECT sum(value) from t_couponget_record where ownertype=0 and date(gettime) between startdate and enddate and coupontype=1 and state=1),
(SELECT count(1) from t_couponget_record where ownertype=0 and date(gettime) between startdate and enddate and coupontype=1 and date(end_time)>enddate and state=0),
(SELECT sum(value) from t_couponget_record where ownertype=0 and date(gettime) between startdate and enddate and coupontype=1 and date(end_time)>enddate and state=0),
(SELECT count(1) from t_couponget_record where ownertype=0 and date(gettime)<=enddate and coupontype=1 and state=0),
(SELECT sum(value) from t_couponget_record where ownertype=0 and date(gettime)<=enddate and coupontype=1 and state=0);

#驾校时间券
insert into tmpTable(a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15,a16,a17,a18,a19,a20)
select '时间券',`name`,a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15,a16,a17,a18 from 
(select ownerid,`name`,sum(pub_count) a1,sum(value) a2 from t_drive_school_info join t_coupon on ownerid=schoolid where ownertype=1 and coupontype=1 and date(t_coupon.addtime)<startdate GROUP BY ownerid) t1
left join (select ownerid,sum(pub_count) a3,sum(value) a4 from t_coupon where ownertype=1 and coupontype=1 and date(addtime) BETWEEN startdate and enddate GROUP BY ownerid) t2
on t1.ownerid=t2.ownerid
LEFT JOIN (select ownerid,sum(pub_count) a5,sum(value) a6 from t_coupon where ownertype=1 and coupontype=1 and date(addtime)<=enddate GROUP BY ownerid) t3
on t1.ownerid=t3.ownerid

LEFT JOIN (SELECT ownerid,count(1) a7,sum(value) a8 from t_couponget_record where ownertype=1 and date(gettime)<startdate and coupontype=1 GROUP BY ownerid) t4
on t1.ownerid=t4.ownerid
LEFT JOIN (SELECT ownerid,count(1) a9,sum(value) a10 from t_couponget_record where ownertype=1 and date(gettime) BETWEEN startdate and enddate and coupontype=1 GROUP BY ownerid) t5
on t1.ownerid=t5.ownerid
left join (SELECT ownerid,count(1) a11,sum(value) a12 from t_couponget_record where ownertype=1 and date(gettime)>enddate and coupontype=1 GROUP BY ownerid) t6
on t1.ownerid=t6.ownerid

LEFT JOIN (SELECT ownerid,count(1) a13,sum(value) a14 from t_couponget_record where ownertype=1 and date(gettime)<startdate and coupontype=1 and state=1 GROUP BY ownerid) t7
on t1.ownerid=t7.ownerid
LEFT JOIN (SELECT ownerid,count(1) a15,sum(value) a16 from t_couponget_record where ownertype=1 and date(gettime) BETWEEN startdate and enddate and coupontype=1 and date(end_time)>enddate  and state=0 GROUP BY ownerid) t8
on t1.ownerid=t8.ownerid
left join (SELECT ownerid,count(1) a17,sum(value) a18 from t_couponget_record where ownertype=1 and date(gettime)<=enddate and coupontype=1 and state=0 GROUP BY ownerid) t9
on t1.ownerid=t9.ownerid;

#驾校抵用券
insert into tmpTable(a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15,a16,a17,a18,a19,a20)
select '抵价券',`name`,a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15,a16,a17,a18 from 
(select ownerid,`name`,sum(pub_count) a1,sum(value) a2 from t_drive_school_info join t_coupon on ownerid=schoolid where ownertype=1 and date(t_coupon.addtime)<startdate and coupontype=2 GROUP BY ownerid) t1
left join (select ownerid,sum(pub_count) a3,sum(value) a4 from t_coupon where ownertype=1 and date(addtime) BETWEEN startdate and enddate and coupontype=2 GROUP BY ownerid) t2
on t1.ownerid=t2.ownerid
LEFT JOIN (select ownerid,sum(pub_count) a5,sum(value) a6 from t_coupon where ownertype=1 and date(addtime)<=enddate and coupontype=2 GROUP BY ownerid) t3
on t1.ownerid=t3.ownerid

LEFT JOIN (SELECT ownerid,count(1) a7,sum(value) a8 from t_couponget_record where ownertype=1 and date(gettime)<startdate and coupontype=2 GROUP BY ownerid) t4
on t1.ownerid=t4.ownerid
LEFT JOIN (SELECT ownerid,count(1) a9,sum(value) a10 from t_couponget_record where ownertype=1 and date(gettime) BETWEEN startdate and enddate and coupontype=2 GROUP BY ownerid) t5
on t1.ownerid=t5.ownerid
left join (SELECT ownerid,count(1) a11,sum(value) a12 from t_couponget_record where ownertype=1 and date(gettime)>enddate and coupontype=2 GROUP BY ownerid) t6
on t1.ownerid=t6.ownerid

LEFT JOIN (SELECT ownerid,count(1) a13,sum(value) a14 from t_couponget_record where ownertype=1 and date(gettime)<startdate and coupontype=2 and state=1 GROUP BY ownerid) t7
on t1.ownerid=t7.ownerid
LEFT JOIN (SELECT ownerid,count(1) a15,sum(value) a16 from t_couponget_record where ownertype=1 and date(gettime) BETWEEN startdate and enddate and coupontype=2 and date(end_time)>enddate and state=0 GROUP BY ownerid) t8
on t1.ownerid=t8.ownerid
left join (SELECT ownerid,count(1) a17,sum(value) a18 from t_couponget_record where ownertype=1 and date(gettime)<=enddate and coupontype=2 and state=0 GROUP BY ownerid) t9
on t1.ownerid=t9.ownerid;

#教练抵价券
insert into tmpTable(a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15,a16,a17,a18,a19,a20) 
select '抵价券',realname,a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15,a16,a17,a18 from 
(select ownerid,realname,sum(pub_count) a1,sum(value) a2 from t_user_coach join t_coupon on ownerid=coachid where ownertype=2 and date(t_coupon.addtime)<startdate and coupontype=2 GROUP BY ownerid) t1
left join (select ownerid,sum(pub_count) a3,sum(value) a4 from t_coupon where ownertype=2 and date(addtime) BETWEEN startdate and enddate and coupontype=2 GROUP BY ownerid) t2
on t1.ownerid=t2.ownerid
LEFT JOIN (select ownerid,sum(pub_count) a5,sum(value) a6 from t_coupon where ownertype=2 and date(addtime)<=enddate and coupontype=2 GROUP BY ownerid) t3
on t1.ownerid=t3.ownerid

LEFT JOIN (SELECT ownerid,count(1) a7,sum(value) a8 from t_couponget_record where ownertype=2 and date(gettime)<startdate and coupontype=2 GROUP BY ownerid) t4
on t1.ownerid=t4.ownerid
LEFT JOIN (SELECT ownerid,count(1) a9,sum(value) a10 from t_couponget_record where ownertype=2 and date(gettime) BETWEEN startdate and enddate and coupontype=2 GROUP BY ownerid) t5
on t1.ownerid=t5.ownerid
left join (SELECT ownerid,count(1) a11,sum(value) a12 from t_couponget_record where ownertype=2 and date(gettime)<=enddate and coupontype=2 GROUP BY ownerid) t6
on t1.ownerid=t6.ownerid

LEFT JOIN (SELECT ownerid,count(1) a13,sum(value) a14 from t_couponget_record where ownertype=2 and date(gettime)<startdate and coupontype=2 and state=1 GROUP BY ownerid) t7
on t1.ownerid=t7.ownerid
LEFT JOIN (SELECT ownerid,count(1) a15,sum(value) a16 from t_couponget_record where ownertype=2 and date(gettime) BETWEEN startdate and enddate and coupontype=2 and date(end_time)>enddate and state=0 GROUP BY ownerid) t8
on t1.ownerid=t8.ownerid
left join (SELECT ownerid,count(1) a17,sum(value) a18 from t_couponget_record where ownertype=2 and date(gettime)<=enddate and coupontype=2 and state=0 GROUP BY ownerid) t9
on t1.ownerid=t9.ownerid;

#教练时间券
insert into tmpTable(a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15,a16,a17,a18,a19,a20)
select '时间券',realname,a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15,a16,a17,a18 from 
(select ownerid,realname,sum(pub_count) a1,sum(value) a2 from t_user_coach join t_coupon on ownerid=coachid where ownertype=2 and date(t_coupon.addtime)<startdate and coupontype=1 GROUP BY ownerid) t1
left join (select ownerid,sum(pub_count) a3,sum(value) a4 from t_coupon where ownertype=2 and date(addtime) BETWEEN startdate and enddate and coupontype=1 GROUP BY ownerid) t2
on t1.ownerid=t2.ownerid
LEFT JOIN (select ownerid,sum(pub_count) a5,sum(value) a6 from t_coupon where ownertype=2 and date(addtime)<=enddate and coupontype=2 GROUP BY ownerid) t3
on t1.ownerid=t3.ownerid

LEFT JOIN (SELECT ownerid,count(1) a7,sum(value) a8 from t_couponget_record where ownertype=2 and date(gettime)<startdate and coupontype=1 GROUP BY ownerid) t4
on t1.ownerid=t4.ownerid
LEFT JOIN (SELECT ownerid,count(1) a9,sum(value) a10 from t_couponget_record where ownertype=2 and date(gettime) BETWEEN startdate and enddate and coupontype=1 GROUP BY ownerid) t5
on t1.ownerid=t5.ownerid
left join (SELECT ownerid,count(1) a11,sum(value) a12 from t_couponget_record where ownertype=2 and date(gettime)<=enddate and coupontype=1 GROUP BY ownerid) t6
on t1.ownerid=t6.ownerid

LEFT JOIN (SELECT ownerid,count(1) a13,sum(value) a14 from t_couponget_record where ownertype=2 and date(gettime)<startdate and coupontype=1 and state=1 GROUP BY ownerid) t7
on t1.ownerid=t7.ownerid
LEFT JOIN (SELECT ownerid,count(1) a15,sum(value) a16 from t_couponget_record where ownertype=2 and date(gettime) BETWEEN startdate and enddate and coupontype=1 and date(end_time)>enddate and state=0 GROUP BY ownerid) t8
on t1.ownerid=t8.ownerid
left join (SELECT ownerid,count(1) a17,sum(value) a18 from t_couponget_record where ownertype=2 and date(gettime)<=enddate and coupontype=1 and state=0 GROUP BY ownerid) t9
on t1.ownerid=t9.ownerid;

select * from tmpTable ORDER BY a1;
END//
DELIMITER ;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
