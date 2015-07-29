DELIMITER $$

DROP FUNCTION IF EXISTS `driver`.`getCoachOrderCount`$$

CREATE  FUNCTION `getCoachOrderCount`(coid INT) RETURNS int(11)
    COMMENT '查询教练的总订单数'
BEGIN
	DECLARE reScore INT; 
	select count(*)  INTO reScore from t_order where coachid=coid;
	RETURN reScore; 
END$$

DELIMITER ;