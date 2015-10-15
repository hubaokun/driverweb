/*
Navicat MySQL Data Transfer

Source Server         : xiaoba
Source Server Version : 50625
Source Host           : localhost:3306
Source Database       : driver

Target Server Type    : MYSQL
Target Server Version : 50625
File Encoding         : 65001

Date: 2015-10-15 15:41:56
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Procedure structure for flush_app_coach_list
-- ----------------------------
DROP PROCEDURE IF EXISTS `flush_app_coach_list`;
DELIMITER ;;
CREATE  PROCEDURE `flush_app_coach_list`()
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
    freecoursestate
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
    u.freecoursestate
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
-- Procedure structure for updatefreecoursedata
-- ----------------------------
DROP PROCEDURE IF EXISTS `updatefreecoursedata`;
DELIMITER ;;
CREATE  PROCEDURE `updatefreecoursedata`()
BEGIN
update t_user_student
set freecoursestate=1
where studentid in (SELECT studentid from t_order where studentstate<>4 and coachstate<>4);
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for getcoachfreestate
-- ----------------------------
DROP FUNCTION IF EXISTS `getcoachfreestate`;
DELIMITER ;;
CREATE  FUNCTION `getcoachfreestate`(coachid int(11),datecount int(11),datestart text,starthour int(11),endhour int(11)) RETURNS tinyint(4)
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
