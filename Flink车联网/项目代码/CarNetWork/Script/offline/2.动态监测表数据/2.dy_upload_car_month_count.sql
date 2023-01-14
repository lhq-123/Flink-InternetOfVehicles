-- ----------------------------
-- Table structure for dy_upload_car_month_count
-- ----------------------------
DROP TABLE IF EXISTS `dy_upload_car_month_count`;
CREATE TABLE `dy_upload_car_month_count`  (
  `province` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `city` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `county` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `dt` date NULL DEFAULT NULL COMMENT '日期',
  `month` varchar(7) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '月份',
  `num` int(6) NOT NULL COMMENT '数量'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '动态监测-每月新增上传数据车辆数' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dy_upload_car_month_count
-- ----------------------------
INSERT INTO `dy_upload_car_month_count` VALUES ('北京', '北京市', '昌平区', '2020-03-20', '20-03', 17602);
INSERT INTO `dy_upload_car_month_count` VALUES ('湖北省', '武汉市', '昌平区', '2020-03-20', '20-03', 13602);
INSERT INTO `dy_upload_car_month_count` VALUES ('重庆', '重庆市', '昌平区', '2020-03-20', '20-03', 11602);
INSERT INTO `dy_upload_car_month_count` VALUES ('四川省', '成都市', '昌平区', '2020-03-20', '20-03', 11156);
INSERT INTO `dy_upload_car_month_count` VALUES ('广东省', '广州市', '昌平区', '2020-03-20', '20-03', 12114);
INSERT INTO `dy_upload_car_month_count` VALUES ('广东省', '深圳市', '昌平区', '2020-03-20', '20-03', 16122);
INSERT INTO `dy_upload_car_month_count` VALUES ('上海', '上海市', '昌平区', '2020-03-20', '20-03', 18419);
INSERT INTO `dy_upload_car_month_count` VALUES ('山东省', '济南市', '昌平区', '2020-03-20', '20-03', 12332);
INSERT INTO `dy_upload_car_month_count` VALUES ('辽宁省', '大连市', '昌平区', '2020-03-20', '20-03', 13139);
INSERT INTO `dy_upload_car_month_count` VALUES ('江苏省', '南京市', '昌平区', '2020-03-20', '20-03', 13331);
INSERT INTO `dy_upload_car_month_count` VALUES ('浙江省', '杭州市', '昌平区', '2020-03-20', '20-03', 13882);
INSERT INTO `dy_upload_car_month_count` VALUES ('北京', '北京市', '昌平区', '2020-02-20', '20-02', 6573);
INSERT INTO `dy_upload_car_month_count` VALUES ('湖北省', '武汉市', '昌平区', '2020-02-20', '20-02', 3252);
INSERT INTO `dy_upload_car_month_count` VALUES ('重庆', '重庆市', '昌平区', '2020-02-20', '20-02', 2134);
INSERT INTO `dy_upload_car_month_count` VALUES ('四川省', '成都市', '昌平区', '2020-02-20', '20-02', 2144);
INSERT INTO `dy_upload_car_month_count` VALUES ('广东省', '广州市', '昌平区', '2020-02-20', '20-02', 2121);
INSERT INTO `dy_upload_car_month_count` VALUES ('广东省', '深圳市', '昌平区', '2020-02-20', '20-02', 3152);
INSERT INTO `dy_upload_car_month_count` VALUES ('上海', '上海市', '昌平区', '2020-02-20', '20-02', 8826);
INSERT INTO `dy_upload_car_month_count` VALUES ('山东省', '济南市', '昌平区', '2020-02-20', '20-02', 5142);
INSERT INTO `dy_upload_car_month_count` VALUES ('辽宁省', '大连市', '昌平区', '2020-02-20', '20-02', 2119);
INSERT INTO `dy_upload_car_month_count` VALUES ('江苏省', '南京市', '昌平区', '2020-02-20', '20-02', 1321);
INSERT INTO `dy_upload_car_month_count` VALUES ('浙江省', '杭州市', '昌平区', '2020-02-20', '20-02', 3320);
INSERT INTO `dy_upload_car_month_count` VALUES ('北京', '北京市', '昌平区', '2020-01-20', '20-01', 6128);
INSERT INTO `dy_upload_car_month_count` VALUES ('湖北省', '武汉市', '昌平区', '2020-01-20', '20-01', 4012);
INSERT INTO `dy_upload_car_month_count` VALUES ('重庆', '重庆市', '昌平区', '2020-01-20', '20-01', 2182);
INSERT INTO `dy_upload_car_month_count` VALUES ('四川省', '成都市', '昌平区', '2020-01-20', '20-01', 4191);
INSERT INTO `dy_upload_car_month_count` VALUES ('广东省', '广州市', '昌平区', '2020-01-20', '20-01', 3210);
INSERT INTO `dy_upload_car_month_count` VALUES ('广东省', '深圳市', '昌平区', '2020-01-20', '20-01', 4122);
INSERT INTO `dy_upload_car_month_count` VALUES ('上海', '上海市', '昌平区', '2020-01-20', '20-01', 7231);
INSERT INTO `dy_upload_car_month_count` VALUES ('山东省', '济南市', '昌平区', '2020-01-20', '20-01', 5142);
INSERT INTO `dy_upload_car_month_count` VALUES ('辽宁省', '大连市', '昌平区', '2020-01-20', '20-01', 2834);
INSERT INTO `dy_upload_car_month_count` VALUES ('江苏省', '南京市', '昌平区', '2020-01-20', '20-01', 1215);
INSERT INTO `dy_upload_car_month_count` VALUES ('浙江省', '杭州市', '昌平区', '2020-01-20', '20-01', 3136);
INSERT INTO `dy_upload_car_month_count` VALUES ('北京', '北京市', '昌平区', '2019-12-20', '19-12', 5116);
INSERT INTO `dy_upload_car_month_count` VALUES ('湖北省', '武汉市', '昌平区', '2019-12-20', '19-12', 3172);
INSERT INTO `dy_upload_car_month_count` VALUES ('重庆', '重庆市', '昌平区', '2019-12-20', '19-12', 3023);
INSERT INTO `dy_upload_car_month_count` VALUES ('四川省', '成都市', '昌平区', '2019-12-20', '19-12', 5213);
INSERT INTO `dy_upload_car_month_count` VALUES ('广东省', '广州市', '昌平区', '2019-12-20', '19-12', 4235);
INSERT INTO `dy_upload_car_month_count` VALUES ('广东省', '深圳市', '昌平区', '2019-12-20', '19-12', 5231);
INSERT INTO `dy_upload_car_month_count` VALUES ('上海', '上海市', '昌平区', '2019-12-20', '19-12', 6122);
INSERT INTO `dy_upload_car_month_count` VALUES ('山东省', '济南市', '昌平区', '2019-12-20', '19-12', 3212);
INSERT INTO `dy_upload_car_month_count` VALUES ('辽宁省', '大连市', '昌平区', '2019-12-20', '19-12', 2121);
INSERT INTO `dy_upload_car_month_count` VALUES ('江苏省', '南京市', '昌平区', '2019-12-20', '19-12', 1567);
INSERT INTO `dy_upload_car_month_count` VALUES ('浙江省', '杭州市', '昌平区', '2019-12-20', '19-12', 2318);
INSERT INTO `dy_upload_car_month_count` VALUES ('北京', '北京市', '昌平区', '2019-11-20', '19-11', 6009);
INSERT INTO `dy_upload_car_month_count` VALUES ('湖北省', '武汉市', '昌平区', '2019-11-20', '19-11', 3928);
INSERT INTO `dy_upload_car_month_count` VALUES ('重庆', '重庆市', '昌平区', '2019-11-20', '19-11', 2882);
INSERT INTO `dy_upload_car_month_count` VALUES ('四川省', '成都市', '昌平区', '2019-11-20', '19-11', 4893);
INSERT INTO `dy_upload_car_month_count` VALUES ('广东省', '广州市', '昌平区', '2019-11-20', '19-11', 3935);
INSERT INTO `dy_upload_car_month_count` VALUES ('广东省', '深圳市', '昌平区', '2019-11-20', '19-11', 4920);
INSERT INTO `dy_upload_car_month_count` VALUES ('上海', '上海市', '昌平区', '2019-11-20', '19-11', 5878);
INSERT INTO `dy_upload_car_month_count` VALUES ('山东省', '济南市', '昌平区', '2019-11-20', '19-11', 2913);
INSERT INTO `dy_upload_car_month_count` VALUES ('辽宁省', '大连市', '昌平区', '2019-11-20', '19-11', 2635);
INSERT INTO `dy_upload_car_month_count` VALUES ('江苏省', '南京市', '昌平区', '2019-11-20', '19-11', 1236);
INSERT INTO `dy_upload_car_month_count` VALUES ('浙江省', '杭州市', '昌平区', '2019-11-20', '19-11', 2121);
INSERT INTO `dy_upload_car_month_count` VALUES ('北京', '北京市', '昌平区', '2019-10-20', '19-10', 6136);
INSERT INTO `dy_upload_car_month_count` VALUES ('湖北省', '武汉市', '昌平区', '2019-10-20', '19-10', 3865);
INSERT INTO `dy_upload_car_month_count` VALUES ('重庆', '重庆市', '昌平区', '2019-10-20', '19-10', 2791);
INSERT INTO `dy_upload_car_month_count` VALUES ('四川省', '成都市', '昌平区', '2019-10-20', '19-10', 4082);
INSERT INTO `dy_upload_car_month_count` VALUES ('广东省', '广州市', '昌平区', '2019-10-20', '19-10', 4031);
INSERT INTO `dy_upload_car_month_count` VALUES ('广东省', '深圳市', '昌平区', '2019-10-20', '19-10', 4156);
INSERT INTO `dy_upload_car_month_count` VALUES ('上海', '上海市', '昌平区', '2019-10-20', '19-10', 4971);
INSERT INTO `dy_upload_car_month_count` VALUES ('山东省', '济南市', '昌平区', '2019-10-20', '19-10', 3352);
INSERT INTO `dy_upload_car_month_count` VALUES ('辽宁省', '大连市', '昌平区', '2019-10-20', '19-10', 2256);
INSERT INTO `dy_upload_car_month_count` VALUES ('江苏省', '南京市', '昌平区', '2019-10-20', '19-10', 2107);
INSERT INTO `dy_upload_car_month_count` VALUES ('浙江省', '杭州市', '昌平区', '2019-10-20', '19-10', 1945);
INSERT INTO `dy_upload_car_month_count` VALUES ('北京', '北京市', '昌平区', '2019-09-20', '19-09', 6021);
INSERT INTO `dy_upload_car_month_count` VALUES ('湖北省', '武汉市', '昌平区', '2019-09-20', '19-09', 2906);
INSERT INTO `dy_upload_car_month_count` VALUES ('重庆', '重庆市', '昌平区', '2019-09-20', '19-09', 2607);
INSERT INTO `dy_upload_car_month_count` VALUES ('四川省', '成都市', '昌平区', '2019-09-20', '19-09', 4101);
INSERT INTO `dy_upload_car_month_count` VALUES ('广东省', '广州市', '昌平区', '2019-09-20', '19-09', 4237);
INSERT INTO `dy_upload_car_month_count` VALUES ('广东省', '深圳市', '昌平区', '2019-09-20', '19-09', 4071);
INSERT INTO `dy_upload_car_month_count` VALUES ('上海', '上海市', '昌平区', '2019-09-20', '19-09', 3810);
INSERT INTO `dy_upload_car_month_count` VALUES ('山东省', '济南市', '昌平区', '2019-09-20', '19-09', 2823);
INSERT INTO `dy_upload_car_month_count` VALUES ('辽宁省', '大连市', '昌平区', '2019-09-20', '19-09', 2754);
INSERT INTO `dy_upload_car_month_count` VALUES ('江苏省', '南京市', '昌平区', '2019-09-20', '19-09', 1952);
INSERT INTO `dy_upload_car_month_count` VALUES ('浙江省', '杭州市', '昌平区', '2019-09-20', '19-09', 1823);
INSERT INTO `dy_upload_car_month_count` VALUES ('北京', '北京市', '昌平区', '2019-08-20', '19-08', 6021);
INSERT INTO `dy_upload_car_month_count` VALUES ('湖北省', '武汉市', '昌平区', '2019-08-20', '19-08', 2906);
INSERT INTO `dy_upload_car_month_count` VALUES ('重庆', '重庆市', '昌平区', '2019-08-20', '19-08', 2607);
INSERT INTO `dy_upload_car_month_count` VALUES ('四川省', '成都市', '昌平区', '2019-08-20', '19-08', 4101);
INSERT INTO `dy_upload_car_month_count` VALUES ('广东省', '广州市', '昌平区', '2019-08-20', '19-08', 4237);
INSERT INTO `dy_upload_car_month_count` VALUES ('广东省', '深圳市', '昌平区', '2019-08-20', '19-08', 4071);
INSERT INTO `dy_upload_car_month_count` VALUES ('上海', '上海市', '昌平区', '2019-08-20', '19-08', 3810);
INSERT INTO `dy_upload_car_month_count` VALUES ('山东省', '济南市', '昌平区', '2019-08-20', '19-08', 2823);
INSERT INTO `dy_upload_car_month_count` VALUES ('辽宁省', '大连市', '昌平区', '2019-08-20', '19-08', 2754);
INSERT INTO `dy_upload_car_month_count` VALUES ('江苏省', '南京市', '昌平区', '2019-08-20', '19-08', 1952);
INSERT INTO `dy_upload_car_month_count` VALUES ('浙江省', '杭州市', '昌平区', '2019-08-20', '19-08', 1223);
INSERT INTO `dy_upload_car_month_count` VALUES ('北京', '北京市', '昌平区', '2019-07-20', '19-07', 6321);
INSERT INTO `dy_upload_car_month_count` VALUES ('湖北省', '武汉市', '昌平区', '2019-07-20', '19-07', 3251);
INSERT INTO `dy_upload_car_month_count` VALUES ('重庆', '重庆市', '昌平区', '2019-07-20', '19-07', 2122);
INSERT INTO `dy_upload_car_month_count` VALUES ('四川省', '成都市', '昌平区', '2019-07-20', '19-07', 3221);
INSERT INTO `dy_upload_car_month_count` VALUES ('广东省', '广州市', '昌平区', '2019-07-20', '19-07', 2333);
INSERT INTO `dy_upload_car_month_count` VALUES ('广东省', '深圳市', '昌平区', '2019-07-20', '19-07', 3135);
INSERT INTO `dy_upload_car_month_count` VALUES ('上海', '上海市', '昌平区', '2019-07-20', '19-07', 2323);
INSERT INTO `dy_upload_car_month_count` VALUES ('山东省', '济南市', '昌平区', '2019-07-20', '19-07', 2213);
INSERT INTO `dy_upload_car_month_count` VALUES ('辽宁省', '大连市', '昌平区', '2019-07-20', '19-07', 1648);
INSERT INTO `dy_upload_car_month_count` VALUES ('江苏省', '南京市', '昌平区', '2019-07-20', '19-07', 1152);
INSERT INTO `dy_upload_car_month_count` VALUES ('浙江省', '杭州市', '昌平区', '2019-07-20', '19-07', 1323);
INSERT INTO `dy_upload_car_month_count` VALUES ('北京', '北京市', '昌平区', '2019-06-20', '19-06', 5821);
INSERT INTO `dy_upload_car_month_count` VALUES ('湖北省', '武汉市', '昌平区', '2019-06-20', '19-06', 3576);
INSERT INTO `dy_upload_car_month_count` VALUES ('重庆', '重庆市', '昌平区', '2019-06-20', '19-06', 1687);
INSERT INTO `dy_upload_car_month_count` VALUES ('四川省', '成都市', '昌平区', '2019-06-20', '19-06', 2156);
INSERT INTO `dy_upload_car_month_count` VALUES ('广东省', '广州市', '昌平区', '2019-06-20', '19-06', 3531);
INSERT INTO `dy_upload_car_month_count` VALUES ('广东省', '深圳市', '昌平区', '2019-06-20', '19-06', 3216);
INSERT INTO `dy_upload_car_month_count` VALUES ('上海', '上海市', '昌平区', '2019-06-20', '19-06', 5631);
INSERT INTO `dy_upload_car_month_count` VALUES ('山东省', '济南市', '昌平区', '2019-06-20', '19-06', 1568);
INSERT INTO `dy_upload_car_month_count` VALUES ('辽宁省', '大连市', '昌平区', '2019-06-20', '19-06', 1612);
INSERT INTO `dy_upload_car_month_count` VALUES ('江苏省', '南京市', '昌平区', '2019-06-20', '19-06', 1215);
INSERT INTO `dy_upload_car_month_count` VALUES ('浙江省', '杭州市', '昌平区', '2019-06-20', '19-06', 1213);