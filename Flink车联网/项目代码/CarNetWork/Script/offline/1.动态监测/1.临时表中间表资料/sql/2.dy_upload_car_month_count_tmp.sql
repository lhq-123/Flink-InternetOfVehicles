-- create table
CREATE TABLE if not exists bi.dy_upload_car_month_count_tmp(
 vin varchar(17) NOT NULL COMMENT '车架号',
  process_time datetime DEFAULT NULL COMMENT '数据更新时间',
  province varchar(255) DEFAULT NULL,
  city varchar(255) DEFAULT NULL,
  county varchar(255) DEFAULT NULL,
  dt date DEFAULT NULL COMMENT '日期',
  month varchar(7) NOT NULL COMMENT '月份',
  PRIMARY KEY (vin) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='动态监测-每月上传数据车辆数历史表';

-- insert into table values("data")
delete from bi.dy_upload_car_month_count_tmp where dt=DATE_SUB(CURDATE(),INTERVAL 2 DAY);
replace into bi.dy_upload_car_month_count_tmp
SELECT vin, process_time,province,city,county,DATE_FORMAT(process_time,'%y-%m-%d')as daily,
DATE_FORMAT(process_time,'%y-%m')as month
from vehicle_networking.online_data
 where DATE_FORMAT(process_time,'%y-%m')<=DATE_FORMAT(DATE_SUB(CURDATE(),INTERVAL 1 month),'%y-%m')
 and DATE_FORMAT(process_time,'%y-%m')>DATE_FORMAT(DATE_SUB(CURDATE(),INTERVAL 2 month),'%y-%m')
 and province is not null
 ;