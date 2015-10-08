ALTER TABLE app_coach_list
ADD accompanycoursestate INT(11) NOT NULL DEFAULT '0' ,add orderbyaccompany INT(11) DEFAULT '0';


INSERT INTO t_car_models VALUES(19,NOW(),"陪驾","陪驾");
INSERT INTO t_teach_subject VALUES(4,NOW(),"陪驾");