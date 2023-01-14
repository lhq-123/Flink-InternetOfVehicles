create table online_data
(
    vin                   varchar(17)   not null comment '车架号',
    process_time          datetime      null comment '数据更新时间',
    lat                   double        null comment '纬度',
    lng                   double        null comment '经度',
    mileage               double        null comment '里程表读数',
    is_alarm              int(1)        null comment '故障标志(0正常，1故障)',
    alarm_name            varchar(1000) null comment '故障名称(多个故障用~分割)',
    terminal_time         datetime      null comment '终端时间',
    earliest_time         datetime      null comment '最早数据接收时间',
    max_voltage_battery   double        null comment '单体电池最高电压',
    min_voltage_battery   double        null comment '单体电池最低电压',
    max_temperature_value double        null comment '电池最高温度',
    min_temperature_value double        null comment '电池最低温度',
    speed                 double        null comment '车速',
    soc                   int(3)        null comment 'SOC',
    charge_flag           int(1)        null comment '充电标识 0：未充电 1：充电 2：异常',
    total_voltage         double        null comment '总电压,单位：V，实际取值0.1~100V',
    total_current         double        null comment '总电流,单位：A,实际取值为-1000~1000A',
    battery_voltage       varchar(1000) null comment '单体电池电压列表',
    probe_temperatures    varchar(1000) null comment '电池模块温度列表',
    series_name           varchar(255)  null comment '车系',
    model_name            varchar(255)  null comment '车型',
    live_time             int           null comment '年限(单位:月，未查到数据显示-1)',
    sales_date            varchar(20)   null comment '销售日期',
    car_type              varchar(20)   null comment '车辆类型',
    province              varchar(255)  null comment '省份',
    city                  varchar(255)  null comment '城市',
    county                varchar(20)   null comment '区（县）'
);

INSERT INTO vehicle_networking.online_data (vin, process_time, lat, lng, mileage, is_alarm, alarm_name, terminal_time, earliest_time, max_voltage_battery, min_voltage_battery, max_temperature_value, min_temperature_value, speed, soc, charge_flag, total_voltage, total_current, battery_voltage, probe_temperatures, series_name, model_name, live_time, sales_date, car_type, province, city, county) VALUES ('LS5A2AJX0JA000606', '2020-03-15 14:10:25', 116.397454, 39.909178, 1678, 0, 'SOC低报警,电池高温报警', '2019-08-15 14:09:58', '2019-01-01 14:30:37', 80, 21, 500, 260, 50, 16, 0, 123000, 88.1, '270', '21.1,22.3,23.4', '传智电动汽车', 'IT2W', 2004, '2019-01-01 14:30:37', '小轿车', '北京', '北京', '中国');
INSERT INTO vehicle_networking.online_data (vin, process_time, lat, lng, mileage, is_alarm, alarm_name, terminal_time, earliest_time, max_voltage_battery, min_voltage_battery, max_temperature_value, min_temperature_value, speed, soc, charge_flag, total_voltage, total_current, battery_voltage, probe_temperatures, series_name, model_name, live_time, sales_date, car_type, province, city, county) VALUES ('LS5A2AJX2HA001329', '2019-08-14 14:10:23', 116.397454, 39.909178, 1888, 0, 'SOC低报警,电池高温报警', '2019-08-15 14:09:56', '2019-01-01 14:30:37', 90, 18, 511, 233, 60, 15, 0, 134000, 86.6, '256', '22.1,24.3,25.4', '黑马电动汽车', 'HEIMA2T', 2010, '2018-12-01 15:30:37', '小轿车', '台湾', '台北', '中国');