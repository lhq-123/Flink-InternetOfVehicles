#!/bin/bash
#每日新增上传车辆数
mysql -P$2 -h$1 -u$3 -p$4 big_screen -e "
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
CREATE TABLE  if not exists bi.dy_upload_car_daily_count(
province varchar(255) DEFAULT NULL COMMENT '省份',
  city varchar(255) DEFAULT NULL COMMENT '城市',
  county varchar(255) DEFAULT NULL COMMENT '区/县',
  dt date DEFAULT NULL COMMENT '日期',
  num int(6) DEFAULT NULL COMMENT '数量'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='动态监测-每日新增上传车辆数';

delete from bi.dy_upload_car_daily_count where dt=DATE_SUB(CURDATE(),INTERVAL 1 DAY);

insert into bi.dy_upload_car_daily_count
SELECT aa.province,aa.city,aa.county, DATE_SUB(CURDATE(),INTERVAL 1 DAY) as daily,
count(vin) as num from (
SELECT a.vin,a.process_time,a.province,a.city,a.county from online.online_data a
where a.process_time>DATE_SUB(CURRENT_DATE,INTERVAL 1 day)and a.province is not null
and a.vin NOT IN(
SELECT b.vin from bi.dy_upload_car_daily_count_tmp b
)
)aa
GROUP BY province,city,county;"
