DELIMITER $$

DROP FUNCTION IF EXISTS `driver`.`getTeachAddress`$$

CREATE  FUNCTION `getTeachAddress`(`v_coachid` INT) RETURNS varchar(500) CHARSET utf8
    COMMENT '根据教练ID查询教练经纬度及详细地址'
BEGIN
DECLARE v_longitude varchar(500);/**经纬度**/
DECLARE v_latitude varchar(500);
DECLARE v_detail  varchar(500);/**详细地址**/
  select longitude,latitude,detail into v_longitude,v_latitude,v_detail  from t_teach_address where coachid =v_coachid and iscurrent = 1;
  return concat(v_longitude,'#',v_latitude,'#',v_detail);
END$$

DELIMITER ;