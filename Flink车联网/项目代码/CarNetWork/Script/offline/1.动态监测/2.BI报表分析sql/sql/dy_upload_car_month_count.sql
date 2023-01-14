-- create table
CREATE TABLE  if not exists bi.dy_upload_car_month_count(
  province varchar(255) DEFAULT NULL,
   city varchar(255) DEFAULT NULL,
   county varchar(255) DEFAULT NULL,
   dt date DEFAULT NULL COMMENT '日期',
   month varchar(7) NOT NULL COMMENT '月份',
   num int(6) NOT NULL COMMENT '数量'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='动态监测-每月新增上传数据车辆数';

-- insert data
insert into bi.dy_upload_car_month_count
SELECT aa.province,aa.city,aa.county, DATE_SUB(CURDATE(),INTERVAL 1 DAY) as daily,
DATE_FORMAT(DATE_SUB(CURDATE(),INTERVAL 1 DAY),'%y-%m') as month,count(vin) as num from (
SELECT a.vin,a.process_time,a.province,a.city,a.county from vehicle_networking.online_data a
where
DATE_FORMAT(a.process_time,'%y-%m-%d')>=date_format(now(),'%y-%m-01')
and a.vin NOT IN(SELECT b.vin from bi.dy_upload_car_month_count_tmp b)
and a.province is not null
)
aa GROUP BY province,city,county;