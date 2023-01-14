-- ----------------------------
-- Table structure for dy_not_upload_car_week_count
-- ----------------------------
DROP TABLE IF EXISTS `dy_not_upload_car_week_count`;
CREATE TABLE `dy_not_upload_car_week_count`  (
  `province` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '省份',
  `city` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '市',
  `county` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '区',
  `week` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '周',
  `num` int(11) NULL DEFAULT NULL COMMENT '次数'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '动态监测-周期均出行次数分布' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dy_not_upload_car_week_count
-- ----------------------------
INSERT INTO `dy_not_upload_car_week_count` VALUES ('北京', '北京市', '昌平区', '0', 1);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('北京', '北京市', '昌平区', '0', 1);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('湖北省', '武汉市', '昌平区', '2', 10);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('重庆', '重庆市', '昌平区', '1', 3);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('四川省', '成都市', '昌平区', '10', 102);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('广东省', '广州市', '昌平区', '7', 68);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('广东省', '深圳市', '昌平区', '0', 1);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('上海', '上海市', '昌平区', '0', 1);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('山东省', '济南市', '昌平区', '0', 1);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('辽宁省', '大连市', '昌平区', '1', 23);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('江苏省', '南京市', '昌平区', '1', 18);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('浙江省', '杭州市', '昌平区', '1', 25);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('北京', '北京市', '昌平区', '1', 5);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('湖北省', '武汉市', '昌平区', '50', 3893);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('重庆', '重庆市', '昌平区', '3', 100);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('四川省', '成都市', '昌平区', '1', 0);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('广东省', '广州市', '昌平区', '10', 398);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('广东省', '深圳市', '昌平区', '5', 152);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('上海', '上海市', '昌平区', '15', 826);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('山东省', '济南市', '昌平区', '3', 142);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('辽宁省', '大连市', '昌平区', '3', 119);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('江苏省', '南京市', '昌平区', '5', 321);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('浙江省', '杭州市', '昌平区', '6', 320);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('北京', '北京市', '昌平区', '7', 628);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('湖北省', '武汉市', '昌平区', '8', 1012);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('重庆', '重庆市', '昌平区', '9', 993);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('四川省', '成都市', '昌平区', '50', 3589);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('广东省', '广州市', '昌平区', '20', 1825);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('广东省', '深圳市', '昌平区', '25', 912);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('上海', '上海市', '昌平区', '12', 231);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('山东省', '济南市', '昌平区', '9', 142);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('辽宁省', '大连市', '昌平区', '1', 21);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('江苏省', '南京市', '昌平区', '10', 215);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('浙江省', '杭州市', '昌平区', '1', 36);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('北京', '北京市', '昌平区', '2', 86);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('湖北省', '武汉市', '昌平区', '12', 772);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('重庆', '重庆市', '昌平区', '19', 2133);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('四川省', '成都市', '昌平区', '2', 93);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('广东省', '广州市', '昌平区', '12', 285);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('广东省', '深圳市', '昌平区', '11', 831);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('上海', '上海市', '昌平区', '12', 622);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('山东省', '济南市', '昌平区', '13', 1012);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('辽宁省', '大连市', '昌平区', '2', 61);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('江苏省', '南京市', '昌平区', '3', 167);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('浙江省', '杭州市', '昌平区', '4', 318);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('北京', '北京市', '昌平区', '5', 609);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('湖北省', '武汉市', '昌平区', '6', 228);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('重庆', '重庆市', '昌平区', '7', 282);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('四川省', '成都市', '昌平区', '7', 393);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('广东省', '广州市', '昌平区', '6', 935);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('广东省', '深圳市', '昌平区', '9', 4920);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('上海', '上海市', '昌平区', '11', 878);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('山东省', '济南市', '昌平区', '10', 913);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('辽宁省', '大连市', '昌平区', '11', 1635);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('江苏省', '南京市', '昌平区', '11', 1236);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('浙江省', '杭州市', '昌平区', '13', 2121);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('北京', '北京市', '昌平区', '10', 1136);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('湖北省', '武汉市', '昌平区', '10', 2865);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('重庆', '重庆市', '昌平区', '13', 1791);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('四川省', '成都市', '昌平区', '14', 1082);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('广东省', '广州市', '昌平区', '19', 2031);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('广东省', '深圳市', '昌平区', '15', 2156);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('上海', '上海市', '昌平区', '16', 1971);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('山东省', '济南市', '昌平区', '19', 2352);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('辽宁省', '大连市', '昌平区', '19', 1256);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('江苏省', '南京市', '昌平区', '17', 1107);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('浙江省', '杭州市', '昌平区', '18', 1945);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('北京', '北京市', '昌平区', '35', 6021);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('湖北省', '武汉市', '昌平区', '21', 2906);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('重庆', '重庆市', '昌平区', '19', 2607);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('四川省', '成都市', '昌平区', '17', 2801);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('广东省', '广州市', '昌平区', '16', 2237);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('广东省', '深圳市', '昌平区', '17', 2171);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('上海', '上海市', '昌平区', '22', 3810);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('山东省', '济南市', '昌平区', '30', 5823);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('辽宁省', '大连市', '昌平区', '33', 6754);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('江苏省', '南京市', '昌平区', '35', 7852);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('浙江省', '杭州市', '昌平区', '12', 1823);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('北京', '北京市', '昌平区', '48', 3021);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('湖北省', '武汉市', '昌平区', '38', 8906);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('重庆', '重庆市', '昌平区', '19', 1607);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('四川省', '成都市', '昌平区', '18', 3101);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('广东省', '广州市', '昌平区', '8', 237);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('广东省', '深圳市', '昌平区', '33', 5071);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('上海', '上海市', '昌平区', '28', 3810);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('山东省', '济南市', '昌平区', '27', 3823);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('辽宁省', '大连市', '昌平区', '1', 27);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('江苏省', '南京市', '昌平区', '12', 1952);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('浙江省', '杭州市', '昌平区', '1', 18);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('北京', '北京市', '昌平区', '5', 121);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('湖北省', '武汉市', '昌平区', '19', 2906);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('重庆', '重庆市', '昌平区', '17', 2607);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('四川省', '成都市', '昌平区', '22', 2671);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('广东省', '广州市', '昌平区', '23', 3137);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('广东省', '深圳市', '昌平区', '25', 3131);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('上海', '上海市', '昌平区', '26', 2923);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('山东省', '济南市', '昌平区', '27', 2313);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('辽宁省', '大连市', '昌平区', '19', 1668);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('江苏省', '南京市', '昌平区', '18', 152);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('浙江省', '杭州市', '昌平区', '21', 3323);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('北京', '北京市', '昌平区', '35', 5891);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('湖北省', '武汉市', '昌平区', '30', 3016);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('重庆', '重庆市', '昌平区', '27', 3064);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('四川省', '成都市', '昌平区', '39', 4562);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('广东省', '广州市', '昌平区', '27', 3246);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('广东省', '深圳市', '昌平区', '26', 3435);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('上海', '上海市', '昌平区', '33', 3748);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('山东省', '济南市', '昌平区', '25', 2628);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('辽宁省', '大连市', '昌平区', '28', 2512);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('江苏省', '南京市', '昌平区', '20', 1925);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('浙江省', '杭州市', '昌平区', '17', 1913);