#!/bin/bash
#未上传数据周期车辆数分布
mysql -P$2 -h$1 -u$3 -p$4 big_screen -e "
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS bi.dy_not_upload_car_week_count_tmp;
CREATE TABLE bi.dy_not_upload_car_week_count_tmp(
  province varchar(255) DEFAULT NULL COMMENT '省份',
  city varchar(255) DEFAULT NULL COMMENT '市',
   county varchar(255) DEFAULT NULL COMMENT '区',
   week varchar(255) DEFAULT NULL COMMENT '周',
  num int(11) DEFAULT NULL COMMENT '次数'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='动态监测-周期均出行次数分布';"
mysql -P$2 -h$1 -u$3 -p$4 big_screen -e "
insert into  bi.dy_not_upload_car_week_count_tmp
SELECT province,city,county,week,count(vin) as num from (
SELECT vin,province,city,county,process_time,timeStampDiff(WEEK,DATE_FORMAT(process_time,'%y-%m-%d'),CURRENT_DATE) week
FROM online.online_data  where province is not null
) t
where DATE_FORMAT(t.process_time,'%y-%m-%d')>=DATE_FORMAT(DATE_SUB(CURRENT_DATE,INTERVAL 6 month),'%y-%m-%d')
GROUP BY province,city,week,county;"
mysql -P$2 -h$1 -u$3 -p$4 big_screen -e "DROP TABLE IF EXISTS bi.dy_not_upload_car_week_count;"
mysql -P$2 -h$1 -u$3 -p$4 big_screen -e "ALTER TABLE bi.dy_not_upload_car_week_count_tmp RENAME TO bi.dy_not_upload_car_week_count;"

