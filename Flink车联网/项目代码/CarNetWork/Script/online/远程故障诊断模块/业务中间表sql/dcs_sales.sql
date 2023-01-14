create table dcs_sales
(
    vehicle_id  varchar(25)   null comment '车辆ID',
    sales_money decimal(8, 2) null comment '售价',
    sales_date  varchar(25)   null comment '出售日期'
);

INSERT INTO vehicle_networking.dcs_sales (vehicle_id, sales_money, sales_date) VALUES ('ITCAST-01-1', 120000.00, '2019-10-01');
INSERT INTO vehicle_networking.dcs_sales (vehicle_id, sales_money, sales_date) VALUES ('ITCAST-01-2', 100000.00, '2019-01-31');
INSERT INTO vehicle_networking.dcs_sales (vehicle_id, sales_money, sales_date) VALUES ('ITCAST-04-1', 80000.00, '2019-03-01');
INSERT INTO vehicle_networking.dcs_sales (vehicle_id, sales_money, sales_date) VALUES ('ITCAST-04-2', 90000.00, '2019-03-02');
INSERT INTO vehicle_networking.dcs_sales (vehicle_id, sales_money, sales_date) VALUES ('ITCAST-04-3', 100000.00, '2019-03-02');
INSERT INTO vehicle_networking.dcs_sales (vehicle_id, sales_money, sales_date) VALUES ('ITCAST-04-4', 150000.00, '2019-03-05');
INSERT INTO vehicle_networking.dcs_sales (vehicle_id, sales_money, sales_date) VALUES ('ITCAST-04-5', 180000.00, '2019-03-10');
INSERT INTO vehicle_networking.dcs_sales (vehicle_id, sales_money, sales_date) VALUES ('ITCAST-04-6', 160000.00, '2019-03-12');
INSERT INTO vehicle_networking.dcs_sales (vehicle_id, sales_money, sales_date) VALUES ('ITCAST-02-1', 100000.00, '2020-01-12');
INSERT INTO vehicle_networking.dcs_sales (vehicle_id, sales_money, sales_date) VALUES ('ITCAST-02-2', 99999.00, '2020-01-20');
INSERT INTO vehicle_networking.dcs_sales (vehicle_id, sales_money, sales_date) VALUES ('ITCAST-02-3', 88888.00, '2020-03-28');
INSERT INTO vehicle_networking.dcs_sales (vehicle_id, sales_money, sales_date) VALUES ('ITCAST-04-7', 88800.00, '2020-04-10');
INSERT INTO vehicle_networking.dcs_sales (vehicle_id, sales_money, sales_date) VALUES ('ITCAST-04-8', 88000.00, '2020-04-20');
INSERT INTO vehicle_networking.dcs_sales (vehicle_id, sales_money, sales_date) VALUES ('ITCAST-03-1', 99999.00, '2020-04-20');
INSERT INTO vehicle_networking.dcs_sales (vehicle_id, sales_money, sales_date) VALUES ('ITCAST-03-2', 110000.00, '2020-04-25');
INSERT INTO vehicle_networking.dcs_sales (vehicle_id, sales_money, sales_date) VALUES ('ITCAST-03-3', 112100.00, '2020-04-26');
INSERT INTO vehicle_networking.dcs_sales (vehicle_id, sales_money, sales_date) VALUES ('ITCAST-03-4', 101080.00, '2020-04-28');
INSERT INTO vehicle_networking.dcs_sales (vehicle_id, sales_money, sales_date) VALUES ('ITCAST-03-5', 98000.00, '2020-04-30');