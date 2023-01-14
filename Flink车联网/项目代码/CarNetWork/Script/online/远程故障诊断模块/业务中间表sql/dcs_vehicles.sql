create table dcs_vehicles
(
    vin         varchar(255) not null comment '车架号',
    model_code  varchar(25)  not null comment '车型编码',
    series_name varchar(50)  null comment '车系名称',
    series_code varchar(25)  null comment '车系编码',
    vehicle_id  varchar(25)  not null comment '车辆ID'
);

INSERT INTO vehicle_networking.dcs_vehicles (vin, model_code, series_name, series_code, vehicle_id) VALUES ('LS6A2E0E1KA004667', 'ITCAST_01', '传智电动车', 'HEIMA_CAR', 'ITCAST-01-1');
INSERT INTO vehicle_networking.dcs_vehicles (vin, model_code, series_name, series_code, vehicle_id) VALUES ('LS5A3CJC7JF890353', 'ITCAST_01', '传智电动车', 'HEIMA_CAR', 'ITCAST-01-2');
INSERT INTO vehicle_networking.dcs_vehicles (vin, model_code, series_name, series_code, vehicle_id) VALUES ('LS5A2DKDXJA000165', 'ITCAST_02', '传智网约电动车', 'HEIMA_ONLINE_CAR', 'ITCAST-02-1');
INSERT INTO vehicle_networking.dcs_vehicles (vin, model_code, series_name, series_code, vehicle_id) VALUES ('LS5A3AJC3JD002004', 'ITCAST_02', '传智网约电动车', 'HEIMA_ONLINE_CAR', 'ITCAST-02-2');
INSERT INTO vehicle_networking.dcs_vehicles (vin, model_code, series_name, series_code, vehicle_id) VALUES ('LS5A2AJX0GA004101', 'ITCAST_02', '传智网约电动车', 'HEIMA_ONLINE_CAR', 'ITCAST-02-3');
INSERT INTO vehicle_networking.dcs_vehicles (vin, model_code, series_name, series_code, vehicle_id) VALUES ('LS5A2DJX0KA000012', 'ITCAST_04', '传智私家车', 'HEIMA_PRIVATE_CAR', 'ITCAST-04-1');
INSERT INTO vehicle_networking.dcs_vehicles (vin, model_code, series_name, series_code, vehicle_id) VALUES ('LS6A2E0E5KA000668', 'ITCAST_04', '传智私家车', 'HEIMA_PRIVATE_CAR', 'ITCAST-04-2');
INSERT INTO vehicle_networking.dcs_vehicles (vin, model_code, series_name, series_code, vehicle_id) VALUES ('LS5A3AJC3JB004654', 'ITCAST_04', '传智私家车', 'HEIMA_PRIVATE_CAR', 'ITCAST-04-3');
INSERT INTO vehicle_networking.dcs_vehicles (vin, model_code, series_name, series_code, vehicle_id) VALUES ('LS5A2AJX8FA000084', 'ITCAST_04', '传智私家车', 'HEIMA_PRIVATE_CAR', 'ITCAST-04-4');
INSERT INTO vehicle_networking.dcs_vehicles (vin, model_code, series_name, series_code, vehicle_id) VALUES ('LS5A2AJX3FJ100674', 'ITCAST_04', '传智私家车', 'HEIMA_PRIVATE_CAR', 'ITCAST-04-5');
INSERT INTO vehicle_networking.dcs_vehicles (vin, model_code, series_name, series_code, vehicle_id) VALUES ('LS5A2DJX0KA000012', 'ITCAST_04', '传智私家车', 'HEIMA_PRIVATE_CAR', 'ITCAST-04-6');
INSERT INTO vehicle_networking.dcs_vehicles (vin, model_code, series_name, series_code, vehicle_id) VALUES ('LS5A2AJX8FA000084', 'ITCAST_04', '传智私家车', 'HEIMA_PRIVATE_CAR', 'ITCAST-04-7');
INSERT INTO vehicle_networking.dcs_vehicles (vin, model_code, series_name, series_code, vehicle_id) VALUES ('LS5A2AJX3FJ100674', 'ITCAST_04', '传智私家车', 'HEIMA_PRIVATE_CAR', 'ITCAST-04-8');
INSERT INTO vehicle_networking.dcs_vehicles (vin, model_code, series_name, series_code, vehicle_id) VALUES ('LS5A2AJX9KA002064', 'ITCAST_03', '传智出租车', 'HEIMA_TAXI_CAR', 'ITCAST-03-1');
INSERT INTO vehicle_networking.dcs_vehicles (vin, model_code, series_name, series_code, vehicle_id) VALUES ('LS5A2AJX0KA002065', 'ITCAST_03', '传智出租车', 'HEIMA_TAXI_CAR', 'ITCAST-03-2');
INSERT INTO vehicle_networking.dcs_vehicles (vin, model_code, series_name, series_code, vehicle_id) VALUES ('LS5A3CJC8JF890183', 'ITCAST_03', '传智出租车', 'HEIMA_TAXI_CAR', 'ITCAST-03-3');
INSERT INTO vehicle_networking.dcs_vehicles (vin, model_code, series_name, series_code, vehicle_id) VALUES ('LS5A3CJC9JF890242', 'ITCAST_03', '传智出租车', 'HEIMA_TAXI_CAR', 'ITCAST-03-4');
INSERT INTO vehicle_networking.dcs_vehicles (vin, model_code, series_name, series_code, vehicle_id) VALUES ('LS5A3CJC2JF810117', 'ITCAST_03', '传智出租车', 'HEIMA_TAXI_CAR', 'ITCAST-03-5');