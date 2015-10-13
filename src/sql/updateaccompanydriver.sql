/*
Navicat MySQL Data Transfer

Source Server         : xiaoba
Source Server Version : 50625
Source Host           : localhost:3306
Source Database       : driver

Target Server Type    : MYSQL
Target Server Version : 50625
File Encoding         : 65001

Date: 2015-10-12 16:53:07
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Procedure structure for updateaccompanydriver
-- ----------------------------
DROP PROCEDURE IF EXISTS `updateaccompanydriver`;
DELIMITER ;;
CREATE  PROCEDURE `updateaccompanydriver`()
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
