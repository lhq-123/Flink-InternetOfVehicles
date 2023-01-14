create table electronic_fence_setting
(
    id          int auto_increment comment '自增id'
        primary key,
    name        varchar(255)  not null comment '电子围栏名称',
    address     varchar(255)  not null comment '中心点地址',
    radius      float         not null comment '电子围栏半径  单位km',
    longitude   double        not null comment '中心点经度',
    latitude    double        not null comment '中心点纬度',
    start_time  date          not null comment '有效期开始时间',
    end_time    date          not null comment '有效期结束时间',
    status      int default 0 not null comment '激活状态  1 激活 0 未激活 2删除',
    default_num int           null comment '1 默认并置顶数据  0非置顶'
)
    comment '实时监控-电子围栏设置' collate = utf8mb4_bin;

INSERT INTO vehicle_networking.electronic_fence_setting (id, name, address, radius, longitude, latitude, start_time, end_time, status, default_num) VALUES (27, '新增电子围栏', '重庆钢铁(集团)有限责任公司', 20, 106.486144, 29.490344, '2019-07-16', '2020-08-12', 2, 0);
INSERT INTO vehicle_networking.electronic_fence_setting (id, name, address, radius, longitude, latitude, start_time, end_time, status, default_num) VALUES (28, '新增测试', '重庆钢铁(集团)有限责任公司', 20, 106.486144, 29.490344, '2019-07-03', '2020-08-05', 2, 0);
INSERT INTO vehicle_networking.electronic_fence_setting (id, name, address, radius, longitude, latitude, start_time, end_time, status, default_num) VALUES (30, '大石坝', '大石坝', 2, 106.634497, 29.538954, '2019-07-04', '2020-03-12', 2, 0);
INSERT INTO vehicle_networking.electronic_fence_setting (id, name, address, radius, longitude, latitude, start_time, end_time, status, default_num) VALUES (31, '测试', '鱼嘴(公交站)', 1, 106.7603, 29.625, '2019-07-04', '2020-01-10', 2, 0);
INSERT INTO vehicle_networking.electronic_fence_setting (id, name, address, radius, longitude, latitude, start_time, end_time, status, default_num) VALUES (32, '重庆大同路测试', '重庆医药(大同路)', 20, 106.573763, 29.55749, '2019-07-06', '2020-08-08', 2, 0);
INSERT INTO vehicle_networking.electronic_fence_setting (id, name, address, radius, longitude, latitude, start_time, end_time, status, default_num) VALUES (33, '渝中', '解放碑(商圈)', 2, 106.57665, 29.557212, '2019-07-01', '2020-12-05', 2, 0);
INSERT INTO vehicle_networking.electronic_fence_setting (id, name, address, radius, longitude, latitude, start_time, end_time, status, default_num) VALUES (34, '江北', '解放碑(商圈)', 5.2, 106.57665, 29.557212, '2019-07-01', '2021-04-01', 2, 0);
INSERT INTO vehicle_networking.electronic_fence_setting (id, name, address, radius, longitude, latitude, start_time, end_time, status, default_num) VALUES (35, '重庆车辆测试', '重庆市人民政府', 10, 106.550483, 29.563707, '2019-05-01', '2020-09-30', 2, 1);
INSERT INTO vehicle_networking.electronic_fence_setting (id, name, address, radius, longitude, latitude, start_time, end_time, status, default_num) VALUES (36, '测试', '惠州南旋集团', 5, 114.550967, 23.148145, '2019-04-01', '2020-02-14', 2, 0);
INSERT INTO vehicle_networking.electronic_fence_setting (id, name, address, radius, longitude, latitude, start_time, end_time, status, default_num) VALUES (37, '北京天安门', '天安门', 15, 116.397454, 39.909178, '2019-07-01', '2020-09-24', 1, 0);
INSERT INTO vehicle_networking.electronic_fence_setting (id, name, address, radius, longitude, latitude, start_time, end_time, status, default_num) VALUES (38, '福建', '福建海晟连锁(禾祥店)', 2, 118.081105, 24.463515, '2019-05-30', '2020-01-09', 1, 0);
INSERT INTO vehicle_networking.electronic_fence_setting (id, name, address, radius, longitude, latitude, start_time, end_time, status, default_num) VALUES (39, '自动推送测试1', '全兴别墅', 10, 106.364325, 29.519534, '2019-09-02', '2020-09-28', 1, 0);
INSERT INTO vehicle_networking.electronic_fence_setting (id, name, address, radius, longitude, latitude, start_time, end_time, status, default_num) VALUES (40, '蓝迪驾校', '蓝迪驾校', 1, 106.446951, 29.551387, '2019-09-02', '2020-09-28', 1, 0);
INSERT INTO vehicle_networking.electronic_fence_setting (id, name, address, radius, longitude, latitude, start_time, end_time, status, default_num) VALUES (41, '解放碑', '人民解放纪念碑', 5, 106.577034, 29.557204, '2019-09-11', '2020-09-28', 2, 0);
INSERT INTO vehicle_networking.electronic_fence_setting (id, name, address, radius, longitude, latitude, start_time, end_time, status, default_num) VALUES (42, '解放碑2', '人民解放纪念碑', 5, 106.577034, 29.557204, '2019-09-01', '2020-09-28', 2, 0);
INSERT INTO vehicle_networking.electronic_fence_setting (id, name, address, radius, longitude, latitude, start_time, end_time, status, default_num) VALUES (43, '双福育才中学', '重庆市双福育才中学', 5, 106.298316, 29.409031, '2019-09-02', '2020-09-28', 1, 0);
INSERT INTO vehicle_networking.electronic_fence_setting (id, name, address, radius, longitude, latitude, start_time, end_time, status, default_num) VALUES (44, '西湖', '西湖文化广场', 10, 120.163752, 30.276651, '2019-05-01', '2020-06-03', 1, 0);
INSERT INTO vehicle_networking.electronic_fence_setting (id, name, address, radius, longitude, latitude, start_time, end_time, status, default_num) VALUES (54, 'test_01', '冉家坝', 5, 106.49617, 29.590742, '2019-09-23', '2020-09-28', 2, 0);
INSERT INTO vehicle_networking.electronic_fence_setting (id, name, address, radius, longitude, latitude, start_time, end_time, status, default_num) VALUES (55, 'test_02', '中共南岸区委党校', 50, 106.638859, 29.48121, '2019-08-01', '2020-05-15', 0, 0);
INSERT INTO vehicle_networking.electronic_fence_setting (id, name, address, radius, longitude, latitude, start_time, end_time, status, default_num) VALUES (56, '2222', '万州老年大学', 10, 108.366088, 30.814602, '2019-09-04', '2023-10-05', 2, 0);
INSERT INTO vehicle_networking.electronic_fence_setting (id, name, address, radius, longitude, latitude, start_time, end_time, status, default_num) VALUES (57, '重庆市区测试', '重庆市政协', 20, 106.52444, 29.594429, '2019-09-17', '2025-02-06', 2, 0);
INSERT INTO vehicle_networking.electronic_fence_setting (id, name, address, radius, longitude, latitude, start_time, end_time, status, default_num) VALUES (58, 'zz', '重庆大泰电子科技有限公司', 3.5, 106.213311, 29.544469, '2019-09-03', '2020-10-25', 2, 0);
INSERT INTO vehicle_networking.electronic_fence_setting (id, name, address, radius, longitude, latitude, start_time, end_time, status, default_num) VALUES (59, '测试模板333', '重庆大泰电子科技有限公司', 20, 106.213311, 29.544469, '2019-09-17', '2020-09-27', 1, 0);
INSERT INTO vehicle_networking.electronic_fence_setting (id, name, address, radius, longitude, latitude, start_time, end_time, status, default_num) VALUES (68, 'test33', '重庆大泰电子科技有限公司', 3, 106.213311, 29.544469, '2019-08-27', '2020-09-10', 1, 0);
INSERT INTO vehicle_networking.electronic_fence_setting (id, name, address, radius, longitude, latitude, start_time, end_time, status, default_num) VALUES (69, '测试1012', '百度重庆营销中心', 10, 106.55214, 29.553241, '2019-10-15', '2020-11-14', 2, 0);
INSERT INTO vehicle_networking.electronic_fence_setting (id, name, address, radius, longitude, latitude, start_time, end_time, status, default_num) VALUES (70, '吸尘器', '百度重庆营销中心', 10, 106.55214, 29.553241, '2019-10-08', '2020-11-12', 2, 0);
INSERT INTO vehicle_networking.electronic_fence_setting (id, name, address, radius, longitude, latitude, start_time, end_time, status, default_num) VALUES (71, '泉州测试', '泉州森林公园', 10, 118.660265, 24.886667, '2019-10-24', '2020-05-06', 1, 0);
INSERT INTO vehicle_networking.electronic_fence_setting (id, name, address, radius, longitude, latitude, start_time, end_time, status, default_num) VALUES (72, '测试1107', '大坪(地铁站)', 0, 106.519184, 29.541886, '2019-11-07', '2020-12-05', 1, 0);
INSERT INTO vehicle_networking.electronic_fence_setting (id, name, address, radius, longitude, latitude, start_time, end_time, status, default_num) VALUES (73, '东风汽车', '武汉经济开发区东风汽车股份有限公司', 5, 114.165344, 30.503033, '2019-11-01', '2020-12-31', 1, 0);
INSERT INTO vehicle_networking.electronic_fence_setting (id, name, address, radius, longitude, latitude, start_time, end_time, status, default_num) VALUES (74, '南京西城路', '江苏省南京市建邺区西城路', 30, 118.7658, 32.02084, '2020-04-01', '2020-12-01', 1, 0);
INSERT INTO vehicle_networking.electronic_fence_setting (id, name, address, radius, longitude, latitude, start_time, end_time, status, default_num) VALUES (75, '新乡市新乡县', '河南省新乡市新乡县', 35, 113.86316, 35.21567, '2020-04-02', '2020-12-02', 1, 0);
INSERT INTO vehicle_networking.electronic_fence_setting (id, name, address, radius, longitude, latitude, start_time, end_time, status, default_num) VALUES (76, '河北邢台市', '邢台市清河县', 25, 115.5423333, 37.0348333, '2020-04-02', '2020-04-02', 1, 0);