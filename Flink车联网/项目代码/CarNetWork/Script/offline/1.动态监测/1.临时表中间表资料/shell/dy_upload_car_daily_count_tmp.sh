#!/bin/bash
#每日上传车辆数统计临时表
mysql -P$2 -h$1 -u$3 -p$4 big_screen -e"
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
CREATE TABLE  if not exists bi.dy_upload_car_daily_count_tmp(
  vin varchar(17) NOT NULL COMMENT '车架号',
   process_time datetime DEFAULT NULL COMMENT '数据更新时间',
   province varchar(255) DEFAULT NULL COMMENT '省份',
   city varchar(255) DEFAULT NULL COMMENT '市',
   county varchar(255) DEFAULT NULL COMMENT '区/县',
   dt date NOT NULL COMMENT '日期',
   PRIMARY KEY (vin) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='动态监测-每日未上传车辆历史表';
delete from bi.dy_upload_car_daily_count_tmp where DATE_FORMAT(process_time,'%y-%m-%d')=DATE_SUB(CURDATE(),INTERVAL 2 DAY);
replace into bi.dy_upload_car_daily_count_tmp
SELECT vin, process_time,province,city,county,DATE_SUB(CURDATE(),INTERVAL 2 DAY)as daily
from vehicle_networking.online_data
where process_time<	DATE_SUB(CURDATE(),INTERVAL 1 DAY) AND process_time>DATE_SUB(CURRENT_DATE,INTERVAL 2 day)
 and province is not null
;"
