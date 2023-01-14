create table t_car_type_code
(
    model_code varchar(25) null comment '车型编码',
    show_name  varchar(50) null comment '车辆类型全名',
    nick_name  varchar(25) null comment '车辆类型简称'
);

INSERT INTO vehicle_networking.t_car_type_code (model_code, show_name, nick_name) VALUES ('ITCAST_01', '电动-试驾车', '试驾车');
INSERT INTO vehicle_networking.t_car_type_code (model_code, show_name, nick_name) VALUES ('ITCAST_02', '电动-网约车', '网约车');
INSERT INTO vehicle_networking.t_car_type_code (model_code, show_name, nick_name) VALUES ('ITCAST_03', '电动-出租车', '出租车');
INSERT INTO vehicle_networking.t_car_type_code (model_code, show_name, nick_name) VALUES ('ITCAST_04', '电动-私家车', '私家车');
INSERT INTO vehicle_networking.t_car_type_code (model_code, show_name, nick_name) VALUES ('ITCAST_05', '电动-公交车', '公交车');
INSERT INTO vehicle_networking.t_car_type_code (model_code, show_name, nick_name) VALUES ('ITCAST_06', '电动-城市洒水车', '洒水车');