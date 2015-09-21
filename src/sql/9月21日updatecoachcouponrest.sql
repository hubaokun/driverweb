/*
Navicat MySQL Data Transfer

Source Server         : xiaoba
Source Server Version : 50625
Source Host           : localhost:3306
Source Database       : driver

Target Server Type    : MYSQL
Target Server Version : 50625
File Encoding         : 65001

Date: 2015-09-21 15:20:57
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Function structure for updatecoachcouponrest
-- ----------------------------
DROP FUNCTION IF EXISTS `updatecoachcouponrest`;
DELIMITER ;;
CREATE  FUNCTION `updatecoachcouponrest`() RETURNS int(11)
BEGIN
	update t_user_coach t1
  set t1.couponrest=(SELECT sum(rest_count) from t_coupon t2 where t2.ownerid=t1.coachid and t2.end_time>NOW()),t1.coupontotal=(SELECT sum(pub_count) from t_coupon t2 where t2.ownerid=t1.coachid and t2.end_time>NOW());
 
	RETURN 0;
END
;;
DELIMITER ;
