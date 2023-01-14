#!/bin/bash
#未上传数据月份车辆数分布
mysql -P$2 -h$1 -u$3 -p$4 big_screen -e "
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS bi.dy_not_upload_car_month_count_tmp;
CREATE TABLE bi.dy_not_upload_car_month_count_tmp(
  province varchar(255) DEFAULT NULL COMMENT '省份',
  city varchar(255) DEFAULT NULL COMMENT '市',
   county varchar(255) DEFAULT NULL COMMENT '区',
   month varchar(255) DEFAULT NULL COMMENT '月',
  num int(11) DEFAULT NULL COMMENT '次数'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='动态监测-月均未上传次数车辆数';"
mysql -P$2 -h$1 -u$3 -p$4 big_screen -e "
insert into  bi.dy_not_upload_car_month_count_tmp
SELECT t.province,t.city,t.county,t.month,count(vin) as num from (
select vin,province,city,county,case a.process_time_month
when DATE_FORMAT(CURRENT_DATE,'%y-%m') then '本月已上传'
when DATE_FORMAT(DATE_SUB(CURRENT_DATE,INTERVAL 1 month),'%y-%m') then DATE_FORMAT(CURRENT_DATE,'%y-%m')
when DATE_FORMAT(DATE_SUB(CURRENT_DATE,INTERVAL 2 month),'%y-%m') then DATE_FORMAT(DATE_SUB(CURRENT_DATE,INTERVAL 1 month),'%y-%m')
when DATE_FORMAT(DATE_SUB(CURRENT_DATE,INTERVAL 3 month),'%y-%m') then DATE_FORMAT(DATE_SUB(CURRENT_DATE,INTERVAL 2 month),'%y-%m')
when DATE_FORMAT(DATE_SUB(CURRENT_DATE,INTERVAL 4 month),'%y-%m') then DATE_FORMAT(DATE_SUB(CURRENT_DATE,INTERVAL 3 month),'%y-%m')
when DATE_FORMAT(DATE_SUB(CURRENT_DATE,INTERVAL 5 month),'%y-%m') then DATE_FORMAT(DATE_SUB(CURRENT_DATE,INTERVAL 4 month),'%y-%m')
when DATE_FORMAT(DATE_SUB(CURRENT_DATE,INTERVAL 6 month),'%y-%m') then DATE_FORMAT(DATE_SUB(CURRENT_DATE,INTERVAL 5 month),'%y-%m')
when DATE_FORMAT(DATE_SUB(CURRENT_DATE,INTERVAL 7 month),'%y-%m') then DATE_FORMAT(DATE_SUB(CURRENT_DATE,INTERVAL 6 month),'%y-%m')
when DATE_FORMAT(DATE_SUB(CURRENT_DATE,INTERVAL 8 month),'%y-%m') then DATE_FORMAT(DATE_SUB(CURRENT_DATE,INTERVAL 7 month),'%y-%m')
when DATE_FORMAT(DATE_SUB(CURRENT_DATE,INTERVAL 9 month),'%y-%m') then DATE_FORMAT(DATE_SUB(CURRENT_DATE,INTERVAL 8 month),'%y-%m')
when DATE_FORMAT(DATE_SUB(CURRENT_DATE,INTERVAL 10 month),'%y-%m') then DATE_FORMAT(DATE_SUB(CURRENT_DATE,INTERVAL 9 month),'%y-%m')
end as month from
(SELECT vin,province,city,county, DATE_FORMAT(process_time,'%y-%m') as process_time_month
FROM online.online_data where province is not null and province!=''
)
a
)
t
GROUP BY t.province,t.city,t.county,t.month;"
mysql -P$2 -h$1 -u$3 -p$4 big_screen -e "DROP TABLE IF EXISTS bi.dy_not_upload_car_month_count;"
mysql -P$2 -h$1 -u$3 -p$4 big_screen -e "ALTER TABLE bi.dy_not_upload_car_month_count_tmp RENAME TO bi.dy_not_upload_car_month_count;"
