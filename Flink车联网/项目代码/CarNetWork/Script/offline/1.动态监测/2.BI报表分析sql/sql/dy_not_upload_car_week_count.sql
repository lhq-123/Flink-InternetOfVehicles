-- create table
CREATE TABLE bi.dy_not_upload_car_week_count(
  province varchar(255) DEFAULT NULL COMMENT '省份',
  city varchar(255) DEFAULT NULL COMMENT '市',
   county varchar(255) DEFAULT NULL COMMENT '区',
   week varchar(255) DEFAULT NULL COMMENT '周',
  num int(11) DEFAULT NULL COMMENT '次数'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='动态监测-周期均出行次数分布';

-- insert data
insert into  bi.dy_not_upload_car_week_count
SELECT province,city,county,week,count(vin) as num from (
SELECT vin,province,city,county,process_time,timeStampDiff(WEEK,DATE_FORMAT(process_time,'%y-%m-%d'),CURRENT_DATE) week
FROM vehicle_networking.online_data  where province is not null
) t
where DATE_FORMAT(t.process_time,'%y-%m-%d')>=DATE_FORMAT(DATE_SUB(CURRENT_DATE,INTERVAL 6 month),'%y-%m-%d')
GROUP BY province,city,week,county;