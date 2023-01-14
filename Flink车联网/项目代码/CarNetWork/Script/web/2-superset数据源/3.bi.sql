/*
 Navicat Premium Data Transfer

 Source Server         : node03-mysql
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : node03:3306
 Source Schema         : bi

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 28/05/2020 22:01:23
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for dy_not_upload_car_month_count
-- ----------------------------
DROP TABLE IF EXISTS `dy_not_upload_car_month_count`;
CREATE TABLE `dy_not_upload_car_month_count`  (
  `province` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '省份',
  `city` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '市',
  `county` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '区',
  `month` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '月',
  `num` int(11) NULL DEFAULT NULL COMMENT '次数'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '动态监测-月均未上传次数车辆数' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dy_not_upload_car_month_count
-- ----------------------------
INSERT INTO `dy_not_upload_car_month_count` VALUES ('北京', '北京市', '市辖区', '20-03', 15602);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('湖北省', '武汉市', '市辖区', '20-03', 13602);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('重庆', '重庆市', '市辖区', '20-03', 11602);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('四川省', '成都市', '市辖区', '20-03', 12156);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('广东省', '广州市', '市辖区', '20-03', 13114);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('广东省', '深圳市', '市辖区', '20-03', 14122);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('上海', '上海市', '市辖区', '20-03', 15819);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('山东省', '济南市', '市辖区', '20-03', 12132);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('辽宁省', '大连市', '市辖区', '20-03', 11139);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('江苏省', '南京市', '市辖区', '20-03', 11331);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('浙江省', '杭州市', '市辖区', '20-03', 12882);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('北京', '北京市', '市辖区', '20-02', 5618);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('湖北省', '武汉市', '市辖区', '20-02', 3252);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('重庆', '重庆市', '市辖区', '20-02', 3101);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('四川省', '成都市', '市辖区', '20-02', 3185);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('广东省', '广州市', '市辖区', '20-02', 2121);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('广东省', '深圳市', '市辖区', '20-02', 3152);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('上海', '上海市', '市辖区', '20-02', 8826);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('山东省', '济南市', '市辖区', '20-02', 5142);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('辽宁省', '大连市', '市辖区', '20-02', 2119);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('江苏省', '南京市', '市辖区', '20-02', 1321);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('浙江省', '杭州市', '市辖区', '20-02', 3320);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('北京', '北京市', '市辖区', '20-01', 6128);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('湖北省', '武汉市', '市辖区', '20-01', 4012);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('重庆', '重庆市', '市辖区', '20-01', 2182);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('四川省', '成都市', '市辖区', '20-01', 4191);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('广东省', '广州市', '市辖区', '20-01', 3210);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('广东省', '深圳市', '市辖区', '20-01', 4122);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('上海', '上海市', '市辖区', '20-01', 7231);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('山东省', '济南市', '市辖区', '20-01', 5142);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('辽宁省', '大连市', '市辖区', '20-01', 2834);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('江苏省', '南京市', '市辖区', '20-01', 1215);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('浙江省', '杭州市', '市辖区', '20-01', 3136);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('北京', '北京市', '市辖区', '19-12', 5116);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('湖北省', '武汉市', '市辖区', '19-12', 3172);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('重庆', '重庆市', '市辖区', '19-12', 3023);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('四川省', '成都市', '市辖区', '19-12', 5213);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('广东省', '广州市', '市辖区', '19-12', 4235);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('广东省', '深圳市', '市辖区', '19-12', 5231);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('上海', '上海市', '市辖区', '19-12', 6122);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('山东省', '济南市', '市辖区', '19-12', 3212);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('辽宁省', '大连市', '市辖区', '19-12', 2121);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('江苏省', '南京市', '市辖区', '19-12', 1567);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('浙江省', '杭州市', '市辖区', '19-12', 2318);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('北京', '北京市', '市辖区', '19-11', 6009);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('湖北省', '武汉市', '市辖区', '19-11', 3928);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('重庆', '重庆市', '市辖区', '19-11', 2882);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('四川省', '成都市', '市辖区', '19-11', 4893);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('广东省', '广州市', '市辖区', '19-11', 3935);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('广东省', '深圳市', '市辖区', '19-11', 4920);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('上海', '上海市', '市辖区', '19-11', 5878);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('山东省', '济南市', '市辖区', '19-11', 2913);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('辽宁省', '大连市', '市辖区', '19-11', 2635);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('江苏省', '南京市', '市辖区', '19-11', 1236);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('浙江省', '杭州市', '市辖区', '19-11', 2121);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('北京', '北京市', '市辖区', '19-10', 6136);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('湖北省', '武汉市', '市辖区', '19-10', 3865);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('重庆', '重庆市', '市辖区', '19-10', 2791);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('四川省', '成都市', '市辖区', '19-10', 4082);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('广东省', '广州市', '市辖区', '19-10', 4031);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('广东省', '深圳市', '市辖区', '19-10', 4156);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('上海', '上海市', '市辖区', '19-10', 4971);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('山东省', '济南市', '市辖区', '19-10', 3352);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('辽宁省', '大连市', '市辖区', '19-10', 2256);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('江苏省', '南京市', '市辖区', '19-10', 2107);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('浙江省', '杭州市', '市辖区', '19-10', 1945);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('北京', '北京市', '市辖区', '19-09', 6021);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('湖北省', '武汉市', '市辖区', '19-09', 2906);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('重庆', '重庆市', '市辖区', '19-09', 2607);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('四川省', '成都市', '市辖区', '19-09', 4101);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('广东省', '广州市', '市辖区', '19-09', 4237);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('广东省', '深圳市', '市辖区', '19-09', 4071);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('上海', '上海市', '市辖区', '19-09', 3810);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('山东省', '济南市', '市辖区', '19-09', 2823);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('辽宁省', '大连市', '市辖区', '19-09', 2754);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('江苏省', '南京市', '市辖区', '19-09', 1952);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('浙江省', '杭州市', '市辖区', '19-09', 1823);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('北京', '北京市', '市辖区', '19-08', 6021);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('湖北省', '武汉市', '市辖区', '19-08', 2906);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('重庆', '重庆市', '市辖区', '19-08', 2607);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('四川省', '成都市', '市辖区', '19-08', 4101);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('广东省', '广州市', '市辖区', '19-08', 4237);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('广东省', '深圳市', '市辖区', '19-08', 4071);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('上海', '上海市', '市辖区', '19-08', 3810);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('山东省', '济南市', '市辖区', '19-08', 2823);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('辽宁省', '大连市', '市辖区', '19-08', 2754);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('江苏省', '南京市', '市辖区', '19-08', 1952);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('浙江省', '杭州市', '市辖区', '19-08', 1823);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('北京', '北京市', '市辖区', '19-07', 6021);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('湖北省', '武汉市', '市辖区', '19-07', 2906);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('重庆', '重庆市', '市辖区', '19-07', 2607);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('四川省', '成都市', '市辖区', '19-07', 2671);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('广东省', '广州市', '市辖区', '19-07', 3137);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('广东省', '深圳市', '市辖区', '19-07', 3131);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('上海', '上海市', '市辖区', '19-07', 2923);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('山东省', '济南市', '市辖区', '19-07', 2313);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('辽宁省', '大连市', '市辖区', '19-07', 1668);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('江苏省', '南京市', '市辖区', '19-07', 1552);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('浙江省', '杭州市', '市辖区', '19-07', 1323);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('北京', '北京市', '市辖区', '19-06', 5891);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('湖北省', '武汉市', '市辖区', '19-06', 3016);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('重庆', '重庆市', '市辖区', '19-06', 3064);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('四川省', '成都市', '市辖区', '19-06', 4562);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('广东省', '广州市', '市辖区', '19-06', 3246);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('广东省', '深圳市', '市辖区', '19-06', 3435);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('上海', '上海市', '市辖区', '19-06', 3748);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('山东省', '济南市', '市辖区', '19-06', 2628);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('辽宁省', '大连市', '市辖区', '19-06', 2512);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('江苏省', '南京市', '市辖区', '19-06', 1925);
INSERT INTO `dy_not_upload_car_month_count` VALUES ('浙江省', '杭州市', '市辖区', '19-06', 1913);

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
INSERT INTO `dy_not_upload_car_week_count` VALUES ('北京', '北京市', '市辖区', '0', 1);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('北京', '北京市', '市辖区', '0', 1);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('湖北省', '武汉市', '市辖区', '2', 10);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('重庆', '重庆市', '市辖区', '1', 3);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('四川省', '成都市', '市辖区', '10', 102);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('广东省', '广州市', '市辖区', '7', 68);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('广东省', '深圳市', '市辖区', '0', 1);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('上海', '上海市', '市辖区', '0', 1);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('山东省', '济南市', '市辖区', '0', 1);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('辽宁省', '大连市', '市辖区', '1', 23);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('江苏省', '南京市', '市辖区', '1', 18);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('浙江省', '杭州市', '市辖区', '1', 25);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('北京', '北京市', '市辖区', '1', 5);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('湖北省', '武汉市', '市辖区', '50', 3893);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('重庆', '重庆市', '市辖区', '3', 100);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('四川省', '成都市', '市辖区', '1', 0);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('广东省', '广州市', '市辖区', '10', 398);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('广东省', '深圳市', '市辖区', '5', 152);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('上海', '上海市', '市辖区', '15', 826);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('山东省', '济南市', '市辖区', '3', 142);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('辽宁省', '大连市', '市辖区', '3', 119);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('江苏省', '南京市', '市辖区', '5', 321);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('浙江省', '杭州市', '市辖区', '6', 320);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('北京', '北京市', '市辖区', '7', 628);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('湖北省', '武汉市', '市辖区', '8', 1012);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('重庆', '重庆市', '市辖区', '9', 993);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('四川省', '成都市', '市辖区', '50', 3589);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('广东省', '广州市', '市辖区', '20', 1825);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('广东省', '深圳市', '市辖区', '25', 912);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('上海', '上海市', '市辖区', '12', 231);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('山东省', '济南市', '市辖区', '9', 142);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('辽宁省', '大连市', '市辖区', '1', 21);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('江苏省', '南京市', '市辖区', '10', 215);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('浙江省', '杭州市', '市辖区', '1', 36);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('北京', '北京市', '市辖区', '2', 86);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('湖北省', '武汉市', '市辖区', '12', 772);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('重庆', '重庆市', '市辖区', '19', 2133);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('四川省', '成都市', '市辖区', '2', 93);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('广东省', '广州市', '市辖区', '12', 285);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('广东省', '深圳市', '市辖区', '11', 831);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('上海', '上海市', '市辖区', '12', 622);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('山东省', '济南市', '市辖区', '13', 1012);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('辽宁省', '大连市', '市辖区', '2', 61);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('江苏省', '南京市', '市辖区', '3', 167);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('浙江省', '杭州市', '市辖区', '4', 318);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('北京', '北京市', '市辖区', '5', 609);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('湖北省', '武汉市', '市辖区', '6', 228);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('重庆', '重庆市', '市辖区', '7', 282);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('四川省', '成都市', '市辖区', '7', 393);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('广东省', '广州市', '市辖区', '6', 935);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('广东省', '深圳市', '市辖区', '9', 4920);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('上海', '上海市', '市辖区', '11', 878);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('山东省', '济南市', '市辖区', '10', 913);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('辽宁省', '大连市', '市辖区', '11', 1635);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('江苏省', '南京市', '市辖区', '11', 1236);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('浙江省', '杭州市', '市辖区', '13', 2121);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('北京', '北京市', '市辖区', '10', 1136);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('湖北省', '武汉市', '市辖区', '10', 2865);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('重庆', '重庆市', '市辖区', '13', 1791);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('四川省', '成都市', '市辖区', '14', 1082);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('广东省', '广州市', '市辖区', '19', 2031);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('广东省', '深圳市', '市辖区', '15', 2156);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('上海', '上海市', '市辖区', '16', 1971);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('山东省', '济南市', '市辖区', '19', 2352);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('辽宁省', '大连市', '市辖区', '19', 1256);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('江苏省', '南京市', '市辖区', '17', 1107);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('浙江省', '杭州市', '市辖区', '18', 1945);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('北京', '北京市', '市辖区', '35', 6021);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('湖北省', '武汉市', '市辖区', '21', 2906);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('重庆', '重庆市', '市辖区', '19', 2607);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('四川省', '成都市', '市辖区', '17', 2801);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('广东省', '广州市', '市辖区', '16', 2237);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('广东省', '深圳市', '市辖区', '17', 2171);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('上海', '上海市', '市辖区', '22', 3810);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('山东省', '济南市', '市辖区', '30', 5823);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('辽宁省', '大连市', '市辖区', '33', 6754);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('江苏省', '南京市', '市辖区', '35', 7852);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('浙江省', '杭州市', '市辖区', '12', 1823);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('北京', '北京市', '市辖区', '48', 3021);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('湖北省', '武汉市', '市辖区', '38', 8906);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('重庆', '重庆市', '市辖区', '19', 1607);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('四川省', '成都市', '市辖区', '18', 3101);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('广东省', '广州市', '市辖区', '8', 237);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('广东省', '深圳市', '市辖区', '33', 5071);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('上海', '上海市', '市辖区', '28', 3810);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('山东省', '济南市', '市辖区', '27', 3823);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('辽宁省', '大连市', '市辖区', '1', 27);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('江苏省', '南京市', '市辖区', '12', 1952);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('浙江省', '杭州市', '市辖区', '1', 18);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('北京', '北京市', '市辖区', '5', 121);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('湖北省', '武汉市', '市辖区', '19', 2906);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('重庆', '重庆市', '市辖区', '17', 2607);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('四川省', '成都市', '市辖区', '22', 2671);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('广东省', '广州市', '市辖区', '23', 3137);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('广东省', '深圳市', '市辖区', '25', 3131);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('上海', '上海市', '市辖区', '26', 2923);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('山东省', '济南市', '市辖区', '27', 2313);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('辽宁省', '大连市', '市辖区', '19', 1668);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('江苏省', '南京市', '市辖区', '18', 152);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('浙江省', '杭州市', '市辖区', '21', 3323);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('北京', '北京市', '市辖区', '35', 5891);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('湖北省', '武汉市', '市辖区', '30', 3016);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('重庆', '重庆市', '市辖区', '27', 3064);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('四川省', '成都市', '市辖区', '39', 4562);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('广东省', '广州市', '市辖区', '27', 3246);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('广东省', '深圳市', '市辖区', '26', 3435);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('上海', '上海市', '市辖区', '33', 3748);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('山东省', '济南市', '市辖区', '25', 2628);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('辽宁省', '大连市', '市辖区', '28', 2512);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('江苏省', '南京市', '市辖区', '20', 1925);
INSERT INTO `dy_not_upload_car_week_count` VALUES ('浙江省', '杭州市', '市辖区', '17', 1913);

-- ----------------------------
-- Table structure for dy_upload_car_daily_count
-- ----------------------------
DROP TABLE IF EXISTS `dy_upload_car_daily_count`;
CREATE TABLE `dy_upload_car_daily_count`  (
  `province` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '省份',
  `city` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '城市',
  `county` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '区/县',
  `dt` date NULL DEFAULT NULL COMMENT '日期',
  `num` int(6) NULL DEFAULT NULL COMMENT '数量'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '动态监测-每日新增上传车辆数' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dy_upload_car_daily_count
-- ----------------------------
INSERT INTO `dy_upload_car_daily_count` VALUES ('北京', '北京市', '市辖区', '2020-03-19', 5876);
INSERT INTO `dy_upload_car_daily_count` VALUES ('湖北省', '武汉市', '市辖区', '2020-03-19', 2698);
INSERT INTO `dy_upload_car_daily_count` VALUES ('重庆', '重庆市', '市辖区', '2020-03-19', 1889);
INSERT INTO `dy_upload_car_daily_count` VALUES ('四川省', '成都市', '市辖区', '2020-03-19', 1902);
INSERT INTO `dy_upload_car_daily_count` VALUES ('广东省', '广州市', '市辖区', '2020-03-19', 2218);
INSERT INTO `dy_upload_car_daily_count` VALUES ('广东省', '深圳市', '市辖区', '2020-03-19', 3848);
INSERT INTO `dy_upload_car_daily_count` VALUES ('上海', '上海市', '市辖区', '2020-03-19', 8179);
INSERT INTO `dy_upload_car_daily_count` VALUES ('山东省', '济南市', '市辖区', '2020-03-19', 3211);
INSERT INTO `dy_upload_car_daily_count` VALUES ('辽宁省', '大连市', '市辖区', '2020-03-19', 2123);
INSERT INTO `dy_upload_car_daily_count` VALUES ('江苏省', '南京市', '市辖区', '2020-03-19', 1218);
INSERT INTO `dy_upload_car_daily_count` VALUES ('浙江省', '杭州市', '市辖区', '2020-03-19', 1825);
INSERT INTO `dy_upload_car_daily_count` VALUES ('北京', '北京市', '市辖区', '2020-03-18', 5462);
INSERT INTO `dy_upload_car_daily_count` VALUES ('湖北省', '武汉市', '市辖区', '2020-03-18', 3893);
INSERT INTO `dy_upload_car_daily_count` VALUES ('重庆', '重庆市', '市辖区', '2020-03-18', 1234);
INSERT INTO `dy_upload_car_daily_count` VALUES ('四川省', '成都市', '市辖区', '2020-03-18', 3421);
INSERT INTO `dy_upload_car_daily_count` VALUES ('广东省', '广州市', '市辖区', '2020-03-18', 5398);
INSERT INTO `dy_upload_car_daily_count` VALUES ('广东省', '深圳市', '市辖区', '2020-03-18', 3152);
INSERT INTO `dy_upload_car_daily_count` VALUES ('上海', '上海市', '市辖区', '2020-03-18', 2826);
INSERT INTO `dy_upload_car_daily_count` VALUES ('山东省', '济南市', '市辖区', '2020-03-18', 4142);
INSERT INTO `dy_upload_car_daily_count` VALUES ('辽宁省', '大连市', '市辖区', '2020-03-18', 2119);
INSERT INTO `dy_upload_car_daily_count` VALUES ('江苏省', '南京市', '市辖区', '2020-03-18', 5321);
INSERT INTO `dy_upload_car_daily_count` VALUES ('浙江省', '杭州市', '市辖区', '2020-03-18', 4320);
INSERT INTO `dy_upload_car_daily_count` VALUES ('北京', '北京市', '市辖区', '2020-03-17', 4628);
INSERT INTO `dy_upload_car_daily_count` VALUES ('湖北省', '武汉市', '市辖区', '2020-03-17', 1612);
INSERT INTO `dy_upload_car_daily_count` VALUES ('重庆', '重庆市', '市辖区', '2020-03-17', 1993);
INSERT INTO `dy_upload_car_daily_count` VALUES ('四川省', '成都市', '市辖区', '2020-03-17', 3589);
INSERT INTO `dy_upload_car_daily_count` VALUES ('广东省', '广州市', '市辖区', '2020-03-17', 31825);
INSERT INTO `dy_upload_car_daily_count` VALUES ('广东省', '深圳市', '市辖区', '2020-03-17', 2912);
INSERT INTO `dy_upload_car_daily_count` VALUES ('上海', '上海市', '市辖区', '2020-03-17', 5231);
INSERT INTO `dy_upload_car_daily_count` VALUES ('山东省', '济南市', '市辖区', '2020-03-17', 1142);
INSERT INTO `dy_upload_car_daily_count` VALUES ('辽宁省', '大连市', '市辖区', '2020-03-17', 2321);
INSERT INTO `dy_upload_car_daily_count` VALUES ('江苏省', '南京市', '市辖区', '2020-03-17', 2315);
INSERT INTO `dy_upload_car_daily_count` VALUES ('浙江省', '杭州市', '市辖区', '2020-03-17', 3236);
INSERT INTO `dy_upload_car_daily_count` VALUES ('北京', '北京市', '市辖区', '2020-03-16', 6386);
INSERT INTO `dy_upload_car_daily_count` VALUES ('湖北省', '武汉市', '市辖区', '2020-03-16', 2772);
INSERT INTO `dy_upload_car_daily_count` VALUES ('重庆', '重庆市', '市辖区', '2020-03-16', 2133);
INSERT INTO `dy_upload_car_daily_count` VALUES ('四川省', '成都市', '市辖区', '2020-03-16', 2393);
INSERT INTO `dy_upload_car_daily_count` VALUES ('广东省', '广州市', '市辖区', '2020-03-16', 3285);
INSERT INTO `dy_upload_car_daily_count` VALUES ('广东省', '深圳市', '市辖区', '2020-03-16', 5831);
INSERT INTO `dy_upload_car_daily_count` VALUES ('上海', '上海市', '市辖区', '2020-03-16', 5622);
INSERT INTO `dy_upload_car_daily_count` VALUES ('山东省', '济南市', '市辖区', '2020-03-16', 1012);
INSERT INTO `dy_upload_car_daily_count` VALUES ('辽宁省', '大连市', '市辖区', '2020-03-16', 2361);
INSERT INTO `dy_upload_car_daily_count` VALUES ('江苏省', '南京市', '市辖区', '2020-03-16', 2167);
INSERT INTO `dy_upload_car_daily_count` VALUES ('浙江省', '杭州市', '市辖区', '2020-03-16', 3318);
INSERT INTO `dy_upload_car_daily_count` VALUES ('北京', '北京市', '市辖区', '2020-03-15', 8609);
INSERT INTO `dy_upload_car_daily_count` VALUES ('湖北省', '武汉市', '市辖区', '2020-03-15', 3228);
INSERT INTO `dy_upload_car_daily_count` VALUES ('重庆', '重庆市', '市辖区', '2020-03-15', 2282);
INSERT INTO `dy_upload_car_daily_count` VALUES ('四川省', '成都市', '市辖区', '2020-03-15', 1393);
INSERT INTO `dy_upload_car_daily_count` VALUES ('广东省', '广州市', '市辖区', '2020-03-15', 1935);
INSERT INTO `dy_upload_car_daily_count` VALUES ('广东省', '深圳市', '市辖区', '2020-03-15', 3920);
INSERT INTO `dy_upload_car_daily_count` VALUES ('上海', '上海市', '市辖区', '2020-03-15', 9878);
INSERT INTO `dy_upload_car_daily_count` VALUES ('山东省', '济南市', '市辖区', '2020-03-15', 2913);
INSERT INTO `dy_upload_car_daily_count` VALUES ('辽宁省', '大连市', '市辖区', '2020-03-15', 2635);
INSERT INTO `dy_upload_car_daily_count` VALUES ('江苏省', '南京市', '市辖区', '2020-03-15', 1236);
INSERT INTO `dy_upload_car_daily_count` VALUES ('浙江省', '杭州市', '市辖区', '2020-03-15', 2121);
INSERT INTO `dy_upload_car_daily_count` VALUES ('北京', '北京市', '市辖区', '2020-03-14', 1136);
INSERT INTO `dy_upload_car_daily_count` VALUES ('湖北省', '武汉市', '市辖区', '2020-03-14', 2865);
INSERT INTO `dy_upload_car_daily_count` VALUES ('重庆', '重庆市', '市辖区', '2020-03-14', 1791);
INSERT INTO `dy_upload_car_daily_count` VALUES ('四川省', '成都市', '市辖区', '2020-03-14', 1082);
INSERT INTO `dy_upload_car_daily_count` VALUES ('广东省', '广州市', '市辖区', '2020-03-14', 2031);
INSERT INTO `dy_upload_car_daily_count` VALUES ('广东省', '深圳市', '市辖区', '2020-03-14', 2156);
INSERT INTO `dy_upload_car_daily_count` VALUES ('上海', '上海市', '市辖区', '2020-03-14', 1971);
INSERT INTO `dy_upload_car_daily_count` VALUES ('山东省', '济南市', '市辖区', '2020-03-14', 2352);
INSERT INTO `dy_upload_car_daily_count` VALUES ('辽宁省', '大连市', '市辖区', '2020-03-14', 1256);
INSERT INTO `dy_upload_car_daily_count` VALUES ('江苏省', '南京市', '市辖区', '2020-03-14', 1107);
INSERT INTO `dy_upload_car_daily_count` VALUES ('浙江省', '杭州市', '市辖区', '2020-03-14', 1945);
INSERT INTO `dy_upload_car_daily_count` VALUES ('北京', '北京市', '市辖区', '2020-03-13', 6021);
INSERT INTO `dy_upload_car_daily_count` VALUES ('湖北省', '武汉市', '市辖区', '2020-03-13', 2906);
INSERT INTO `dy_upload_car_daily_count` VALUES ('重庆', '重庆市', '市辖区', '2020-03-13', 2607);
INSERT INTO `dy_upload_car_daily_count` VALUES ('四川省', '成都市', '市辖区', '2020-03-13', 2801);
INSERT INTO `dy_upload_car_daily_count` VALUES ('广东省', '广州市', '市辖区', '2020-03-13', 2237);
INSERT INTO `dy_upload_car_daily_count` VALUES ('广东省', '深圳市', '市辖区', '2020-03-13', 2171);
INSERT INTO `dy_upload_car_daily_count` VALUES ('上海', '上海市', '市辖区', '2020-03-13', 3810);
INSERT INTO `dy_upload_car_daily_count` VALUES ('山东省', '济南市', '市辖区', '2020-03-13', 5823);
INSERT INTO `dy_upload_car_daily_count` VALUES ('辽宁省', '大连市', '市辖区', '2020-03-13', 6754);
INSERT INTO `dy_upload_car_daily_count` VALUES ('江苏省', '南京市', '市辖区', '2020-03-13', 7852);
INSERT INTO `dy_upload_car_daily_count` VALUES ('浙江省', '杭州市', '市辖区', '2020-03-13', 1823);
INSERT INTO `dy_upload_car_daily_count` VALUES ('北京', '北京市', '市辖区', '2020-03-12', 3021);
INSERT INTO `dy_upload_car_daily_count` VALUES ('湖北省', '武汉市', '市辖区', '2020-03-12', 8906);
INSERT INTO `dy_upload_car_daily_count` VALUES ('重庆', '重庆市', '市辖区', '2020-03-12', 1607);
INSERT INTO `dy_upload_car_daily_count` VALUES ('四川省', '成都市', '市辖区', '2020-03-12', 3101);
INSERT INTO `dy_upload_car_daily_count` VALUES ('广东省', '广州市', '市辖区', '2020-03-12', 237);
INSERT INTO `dy_upload_car_daily_count` VALUES ('广东省', '深圳市', '市辖区', '2020-03-12', 5071);
INSERT INTO `dy_upload_car_daily_count` VALUES ('上海', '上海市', '市辖区', '2020-03-12', 3810);
INSERT INTO `dy_upload_car_daily_count` VALUES ('山东省', '济南市', '市辖区', '2020-03-12', 3823);
INSERT INTO `dy_upload_car_daily_count` VALUES ('辽宁省', '大连市', '市辖区', '2020-03-12', 227);
INSERT INTO `dy_upload_car_daily_count` VALUES ('江苏省', '南京市', '市辖区', '2020-03-12', 1952);
INSERT INTO `dy_upload_car_daily_count` VALUES ('浙江省', '杭州市', '市辖区', '2020-03-12', 318);
INSERT INTO `dy_upload_car_daily_count` VALUES ('北京', '北京市', '市辖区', '2020-03-11', 2121);
INSERT INTO `dy_upload_car_daily_count` VALUES ('湖北省', '武汉市', '市辖区', '2020-03-11', 2906);
INSERT INTO `dy_upload_car_daily_count` VALUES ('重庆', '重庆市', '市辖区', '2020-03-11', 2607);
INSERT INTO `dy_upload_car_daily_count` VALUES ('四川省', '成都市', '市辖区', '2020-03-11', 2671);
INSERT INTO `dy_upload_car_daily_count` VALUES ('广东省', '广州市', '市辖区', '2020-03-11', 3137);
INSERT INTO `dy_upload_car_daily_count` VALUES ('广东省', '深圳市', '市辖区', '2020-03-11', 3131);
INSERT INTO `dy_upload_car_daily_count` VALUES ('上海', '上海市', '市辖区', '2020-03-11', 2923);
INSERT INTO `dy_upload_car_daily_count` VALUES ('山东省', '济南市', '市辖区', '2020-03-11', 2313);
INSERT INTO `dy_upload_car_daily_count` VALUES ('辽宁省', '大连市', '市辖区', '2020-03-11', 1668);
INSERT INTO `dy_upload_car_daily_count` VALUES ('江苏省', '南京市', '市辖区', '2020-03-11', 152);
INSERT INTO `dy_upload_car_daily_count` VALUES ('浙江省', '杭州市', '市辖区', '2020-03-11', 3323);
INSERT INTO `dy_upload_car_daily_count` VALUES ('北京', '北京市', '市辖区', '2020-03-11', 5891);
INSERT INTO `dy_upload_car_daily_count` VALUES ('湖北省', '武汉市', '市辖区', '2020-03-11', 3016);
INSERT INTO `dy_upload_car_daily_count` VALUES ('重庆', '重庆市', '市辖区', '2020-03-11', 3064);
INSERT INTO `dy_upload_car_daily_count` VALUES ('四川省', '成都市', '市辖区', '2020-03-11', 4562);
INSERT INTO `dy_upload_car_daily_count` VALUES ('广东省', '广州市', '市辖区', '2020-03-11', 3246);
INSERT INTO `dy_upload_car_daily_count` VALUES ('广东省', '深圳市', '市辖区', '2020-03-11', 3435);
INSERT INTO `dy_upload_car_daily_count` VALUES ('上海', '上海市', '市辖区', '2020-03-11', 3748);
INSERT INTO `dy_upload_car_daily_count` VALUES ('山东省', '济南市', '市辖区', '2020-03-11', 2628);
INSERT INTO `dy_upload_car_daily_count` VALUES ('辽宁省', '大连市', '市辖区', '2020-03-11', 2512);
INSERT INTO `dy_upload_car_daily_count` VALUES ('江苏省', '南京市', '市辖区', '2020-03-11', 1925);
INSERT INTO `dy_upload_car_daily_count` VALUES ('浙江省', '杭州市', '市辖区', '2020-03-11', 1913);
INSERT INTO `dy_upload_car_daily_count` VALUES ('北京', '北京市', '市辖区', '2020-02-08', 6893);
INSERT INTO `dy_upload_car_daily_count` VALUES ('湖北省', '武汉市', '市辖区', '2020-02-08', 3016);
INSERT INTO `dy_upload_car_daily_count` VALUES ('重庆', '重庆市', '市辖区', '2020-02-08', 3064);
INSERT INTO `dy_upload_car_daily_count` VALUES ('四川省', '成都市', '市辖区', '2020-02-08', 2515);
INSERT INTO `dy_upload_car_daily_count` VALUES ('广东省', '广州市', '市辖区', '2020-02-08', 3212);
INSERT INTO `dy_upload_car_daily_count` VALUES ('广东省', '深圳市', '市辖区', '2020-02-08', 3536);
INSERT INTO `dy_upload_car_daily_count` VALUES ('上海', '上海市', '市辖区', '2020-02-08', 3335);
INSERT INTO `dy_upload_car_daily_count` VALUES ('山东省', '济南市', '市辖区', '2020-02-08', 2788);
INSERT INTO `dy_upload_car_daily_count` VALUES ('辽宁省', '大连市', '市辖区', '2020-02-08', 2612);
INSERT INTO `dy_upload_car_daily_count` VALUES ('江苏省', '南京市', '市辖区', '2020-02-08', 1525);
INSERT INTO `dy_upload_car_daily_count` VALUES ('浙江省', '杭州市', '市辖区', '2020-02-08', 1333);
INSERT INTO `dy_upload_car_daily_count` VALUES ('北京', '北京市', '市辖区', '2020-02-09', 6343);
INSERT INTO `dy_upload_car_daily_count` VALUES ('湖北省', '武汉市', '市辖区', '2020-02-09', 3713);
INSERT INTO `dy_upload_car_daily_count` VALUES ('重庆', '重庆市', '市辖区', '2020-02-09', 2064);
INSERT INTO `dy_upload_car_daily_count` VALUES ('四川省', '成都市', '市辖区', '2020-02-09', 3562);
INSERT INTO `dy_upload_car_daily_count` VALUES ('广东省', '广州市', '市辖区', '2020-02-09', 3246);
INSERT INTO `dy_upload_car_daily_count` VALUES ('广东省', '深圳市', '市辖区', '2020-02-09', 5435);
INSERT INTO `dy_upload_car_daily_count` VALUES ('上海', '上海市', '市辖区', '2020-02-09', 8128);
INSERT INTO `dy_upload_car_daily_count` VALUES ('山东省', '济南市', '市辖区', '2020-02-09', 2628);
INSERT INTO `dy_upload_car_daily_count` VALUES ('辽宁省', '大连市', '市辖区', '2020-02-09', 2512);
INSERT INTO `dy_upload_car_daily_count` VALUES ('江苏省', '南京市', '市辖区', '2020-02-09', 1925);
INSERT INTO `dy_upload_car_daily_count` VALUES ('浙江省', '杭州市', '市辖区', '2020-02-09', 2913);
INSERT INTO `dy_upload_car_daily_count` VALUES ('北京', '北京市', '市辖区', '2020-02-10', 5811);
INSERT INTO `dy_upload_car_daily_count` VALUES ('湖北省', '武汉市', '市辖区', '2020-02-10', 3214);
INSERT INTO `dy_upload_car_daily_count` VALUES ('重庆', '重庆市', '市辖区', '2020-02-10', 2043);
INSERT INTO `dy_upload_car_daily_count` VALUES ('四川省', '成都市', '市辖区', '2020-02-10', 9562);
INSERT INTO `dy_upload_car_daily_count` VALUES ('广东省', '广州市', '市辖区', '2020-02-10', 2236);
INSERT INTO `dy_upload_car_daily_count` VALUES ('广东省', '深圳市', '市辖区', '2020-02-10', 3325);
INSERT INTO `dy_upload_car_daily_count` VALUES ('上海', '上海市', '市辖区', '2020-02-10', 78);
INSERT INTO `dy_upload_car_daily_count` VALUES ('山东省', '济南市', '市辖区', '2020-02-10', 2628);
INSERT INTO `dy_upload_car_daily_count` VALUES ('辽宁省', '大连市', '市辖区', '2020-02-10', 2412);
INSERT INTO `dy_upload_car_daily_count` VALUES ('江苏省', '南京市', '市辖区', '2020-02-10', 1525);
INSERT INTO `dy_upload_car_daily_count` VALUES ('浙江省', '杭州市', '市辖区', '2020-02-10', 1813);

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
INSERT INTO `dy_upload_car_month_count` VALUES ('北京', '北京市', '市辖区', '2020-03-20', '20-03', 17602);
INSERT INTO `dy_upload_car_month_count` VALUES ('湖北省', '武汉市', '市辖区', '2020-03-20', '20-03', 13602);
INSERT INTO `dy_upload_car_month_count` VALUES ('重庆', '重庆市', '市辖区', '2020-03-20', '20-03', 11602);
INSERT INTO `dy_upload_car_month_count` VALUES ('四川省', '成都市', '市辖区', '2020-03-20', '20-03', 11156);
INSERT INTO `dy_upload_car_month_count` VALUES ('广东省', '广州市', '市辖区', '2020-03-20', '20-03', 12114);
INSERT INTO `dy_upload_car_month_count` VALUES ('广东省', '深圳市', '市辖区', '2020-03-20', '20-03', 16122);
INSERT INTO `dy_upload_car_month_count` VALUES ('上海', '上海市', '市辖区', '2020-03-20', '20-03', 18419);
INSERT INTO `dy_upload_car_month_count` VALUES ('山东省', '济南市', '市辖区', '2020-03-20', '20-03', 12332);
INSERT INTO `dy_upload_car_month_count` VALUES ('辽宁省', '大连市', '市辖区', '2020-03-20', '20-03', 13139);
INSERT INTO `dy_upload_car_month_count` VALUES ('江苏省', '南京市', '市辖区', '2020-03-20', '20-03', 13331);
INSERT INTO `dy_upload_car_month_count` VALUES ('浙江省', '杭州市', '市辖区', '2020-03-20', '20-03', 13882);
INSERT INTO `dy_upload_car_month_count` VALUES ('北京', '北京市', '市辖区', '2020-02-20', '20-02', 6573);
INSERT INTO `dy_upload_car_month_count` VALUES ('湖北省', '武汉市', '市辖区', '2020-02-20', '20-02', 3252);
INSERT INTO `dy_upload_car_month_count` VALUES ('重庆', '重庆市', '市辖区', '2020-02-20', '20-02', 2134);
INSERT INTO `dy_upload_car_month_count` VALUES ('四川省', '成都市', '市辖区', '2020-02-20', '20-02', 2144);
INSERT INTO `dy_upload_car_month_count` VALUES ('广东省', '广州市', '市辖区', '2020-02-20', '20-02', 2121);
INSERT INTO `dy_upload_car_month_count` VALUES ('广东省', '深圳市', '市辖区', '2020-02-20', '20-02', 3152);
INSERT INTO `dy_upload_car_month_count` VALUES ('上海', '上海市', '市辖区', '2020-02-20', '20-02', 8826);
INSERT INTO `dy_upload_car_month_count` VALUES ('山东省', '济南市', '市辖区', '2020-02-20', '20-02', 5142);
INSERT INTO `dy_upload_car_month_count` VALUES ('辽宁省', '大连市', '市辖区', '2020-02-20', '20-02', 2119);
INSERT INTO `dy_upload_car_month_count` VALUES ('江苏省', '南京市', '市辖区', '2020-02-20', '20-02', 1321);
INSERT INTO `dy_upload_car_month_count` VALUES ('浙江省', '杭州市', '市辖区', '2020-02-20', '20-02', 3320);
INSERT INTO `dy_upload_car_month_count` VALUES ('北京', '北京市', '市辖区', '2020-01-20', '20-01', 6128);
INSERT INTO `dy_upload_car_month_count` VALUES ('湖北省', '武汉市', '市辖区', '2020-01-20', '20-01', 4012);
INSERT INTO `dy_upload_car_month_count` VALUES ('重庆', '重庆市', '市辖区', '2020-01-20', '20-01', 2182);
INSERT INTO `dy_upload_car_month_count` VALUES ('四川省', '成都市', '市辖区', '2020-01-20', '20-01', 4191);
INSERT INTO `dy_upload_car_month_count` VALUES ('广东省', '广州市', '市辖区', '2020-01-20', '20-01', 3210);
INSERT INTO `dy_upload_car_month_count` VALUES ('广东省', '深圳市', '市辖区', '2020-01-20', '20-01', 4122);
INSERT INTO `dy_upload_car_month_count` VALUES ('上海', '上海市', '市辖区', '2020-01-20', '20-01', 7231);
INSERT INTO `dy_upload_car_month_count` VALUES ('山东省', '济南市', '市辖区', '2020-01-20', '20-01', 5142);
INSERT INTO `dy_upload_car_month_count` VALUES ('辽宁省', '大连市', '市辖区', '2020-01-20', '20-01', 2834);
INSERT INTO `dy_upload_car_month_count` VALUES ('江苏省', '南京市', '市辖区', '2020-01-20', '20-01', 1215);
INSERT INTO `dy_upload_car_month_count` VALUES ('浙江省', '杭州市', '市辖区', '2020-01-20', '20-01', 3136);
INSERT INTO `dy_upload_car_month_count` VALUES ('北京', '北京市', '市辖区', '2019-12-20', '19-12', 5116);
INSERT INTO `dy_upload_car_month_count` VALUES ('湖北省', '武汉市', '市辖区', '2019-12-20', '19-12', 3172);
INSERT INTO `dy_upload_car_month_count` VALUES ('重庆', '重庆市', '市辖区', '2019-12-20', '19-12', 3023);
INSERT INTO `dy_upload_car_month_count` VALUES ('四川省', '成都市', '市辖区', '2019-12-20', '19-12', 5213);
INSERT INTO `dy_upload_car_month_count` VALUES ('广东省', '广州市', '市辖区', '2019-12-20', '19-12', 4235);
INSERT INTO `dy_upload_car_month_count` VALUES ('广东省', '深圳市', '市辖区', '2019-12-20', '19-12', 5231);
INSERT INTO `dy_upload_car_month_count` VALUES ('上海', '上海市', '市辖区', '2019-12-20', '19-12', 6122);
INSERT INTO `dy_upload_car_month_count` VALUES ('山东省', '济南市', '市辖区', '2019-12-20', '19-12', 3212);
INSERT INTO `dy_upload_car_month_count` VALUES ('辽宁省', '大连市', '市辖区', '2019-12-20', '19-12', 2121);
INSERT INTO `dy_upload_car_month_count` VALUES ('江苏省', '南京市', '市辖区', '2019-12-20', '19-12', 1567);
INSERT INTO `dy_upload_car_month_count` VALUES ('浙江省', '杭州市', '市辖区', '2019-12-20', '19-12', 2318);
INSERT INTO `dy_upload_car_month_count` VALUES ('北京', '北京市', '市辖区', '2019-11-20', '19-11', 6009);
INSERT INTO `dy_upload_car_month_count` VALUES ('湖北省', '武汉市', '市辖区', '2019-11-20', '19-11', 3928);
INSERT INTO `dy_upload_car_month_count` VALUES ('重庆', '重庆市', '市辖区', '2019-11-20', '19-11', 2882);
INSERT INTO `dy_upload_car_month_count` VALUES ('四川省', '成都市', '市辖区', '2019-11-20', '19-11', 4893);
INSERT INTO `dy_upload_car_month_count` VALUES ('广东省', '广州市', '市辖区', '2019-11-20', '19-11', 3935);
INSERT INTO `dy_upload_car_month_count` VALUES ('广东省', '深圳市', '市辖区', '2019-11-20', '19-11', 4920);
INSERT INTO `dy_upload_car_month_count` VALUES ('上海', '上海市', '市辖区', '2019-11-20', '19-11', 5878);
INSERT INTO `dy_upload_car_month_count` VALUES ('山东省', '济南市', '市辖区', '2019-11-20', '19-11', 2913);
INSERT INTO `dy_upload_car_month_count` VALUES ('辽宁省', '大连市', '市辖区', '2019-11-20', '19-11', 2635);
INSERT INTO `dy_upload_car_month_count` VALUES ('江苏省', '南京市', '市辖区', '2019-11-20', '19-11', 1236);
INSERT INTO `dy_upload_car_month_count` VALUES ('浙江省', '杭州市', '市辖区', '2019-11-20', '19-11', 2121);
INSERT INTO `dy_upload_car_month_count` VALUES ('北京', '北京市', '市辖区', '2019-10-20', '19-10', 6136);
INSERT INTO `dy_upload_car_month_count` VALUES ('湖北省', '武汉市', '市辖区', '2019-10-20', '19-10', 3865);
INSERT INTO `dy_upload_car_month_count` VALUES ('重庆', '重庆市', '市辖区', '2019-10-20', '19-10', 2791);
INSERT INTO `dy_upload_car_month_count` VALUES ('四川省', '成都市', '市辖区', '2019-10-20', '19-10', 4082);
INSERT INTO `dy_upload_car_month_count` VALUES ('广东省', '广州市', '市辖区', '2019-10-20', '19-10', 4031);
INSERT INTO `dy_upload_car_month_count` VALUES ('广东省', '深圳市', '市辖区', '2019-10-20', '19-10', 4156);
INSERT INTO `dy_upload_car_month_count` VALUES ('上海', '上海市', '市辖区', '2019-10-20', '19-10', 4971);
INSERT INTO `dy_upload_car_month_count` VALUES ('山东省', '济南市', '市辖区', '2019-10-20', '19-10', 3352);
INSERT INTO `dy_upload_car_month_count` VALUES ('辽宁省', '大连市', '市辖区', '2019-10-20', '19-10', 2256);
INSERT INTO `dy_upload_car_month_count` VALUES ('江苏省', '南京市', '市辖区', '2019-10-20', '19-10', 2107);
INSERT INTO `dy_upload_car_month_count` VALUES ('浙江省', '杭州市', '市辖区', '2019-10-20', '19-10', 1945);
INSERT INTO `dy_upload_car_month_count` VALUES ('北京', '北京市', '市辖区', '2019-09-20', '19-09', 6021);
INSERT INTO `dy_upload_car_month_count` VALUES ('湖北省', '武汉市', '市辖区', '2019-09-20', '19-09', 2906);
INSERT INTO `dy_upload_car_month_count` VALUES ('重庆', '重庆市', '市辖区', '2019-09-20', '19-09', 2607);
INSERT INTO `dy_upload_car_month_count` VALUES ('四川省', '成都市', '市辖区', '2019-09-20', '19-09', 4101);
INSERT INTO `dy_upload_car_month_count` VALUES ('广东省', '广州市', '市辖区', '2019-09-20', '19-09', 4237);
INSERT INTO `dy_upload_car_month_count` VALUES ('广东省', '深圳市', '市辖区', '2019-09-20', '19-09', 4071);
INSERT INTO `dy_upload_car_month_count` VALUES ('上海', '上海市', '市辖区', '2019-09-20', '19-09', 3810);
INSERT INTO `dy_upload_car_month_count` VALUES ('山东省', '济南市', '市辖区', '2019-09-20', '19-09', 2823);
INSERT INTO `dy_upload_car_month_count` VALUES ('辽宁省', '大连市', '市辖区', '2019-09-20', '19-09', 2754);
INSERT INTO `dy_upload_car_month_count` VALUES ('江苏省', '南京市', '市辖区', '2019-09-20', '19-09', 1952);
INSERT INTO `dy_upload_car_month_count` VALUES ('浙江省', '杭州市', '市辖区', '2019-09-20', '19-09', 1823);
INSERT INTO `dy_upload_car_month_count` VALUES ('北京', '北京市', '市辖区', '2019-08-20', '19-08', 6021);
INSERT INTO `dy_upload_car_month_count` VALUES ('湖北省', '武汉市', '市辖区', '2019-08-20', '19-08', 2906);
INSERT INTO `dy_upload_car_month_count` VALUES ('重庆', '重庆市', '市辖区', '2019-08-20', '19-08', 2607);
INSERT INTO `dy_upload_car_month_count` VALUES ('四川省', '成都市', '市辖区', '2019-08-20', '19-08', 4101);
INSERT INTO `dy_upload_car_month_count` VALUES ('广东省', '广州市', '市辖区', '2019-08-20', '19-08', 4237);
INSERT INTO `dy_upload_car_month_count` VALUES ('广东省', '深圳市', '市辖区', '2019-08-20', '19-08', 4071);
INSERT INTO `dy_upload_car_month_count` VALUES ('上海', '上海市', '市辖区', '2019-08-20', '19-08', 3810);
INSERT INTO `dy_upload_car_month_count` VALUES ('山东省', '济南市', '市辖区', '2019-08-20', '19-08', 2823);
INSERT INTO `dy_upload_car_month_count` VALUES ('辽宁省', '大连市', '市辖区', '2019-08-20', '19-08', 2754);
INSERT INTO `dy_upload_car_month_count` VALUES ('江苏省', '南京市', '市辖区', '2019-08-20', '19-08', 1952);
INSERT INTO `dy_upload_car_month_count` VALUES ('浙江省', '杭州市', '市辖区', '2019-08-20', '19-08', 1223);
INSERT INTO `dy_upload_car_month_count` VALUES ('北京', '北京市', '市辖区', '2019-07-20', '19-07', 6321);
INSERT INTO `dy_upload_car_month_count` VALUES ('湖北省', '武汉市', '市辖区', '2019-07-20', '19-07', 3251);
INSERT INTO `dy_upload_car_month_count` VALUES ('重庆', '重庆市', '市辖区', '2019-07-20', '19-07', 2122);
INSERT INTO `dy_upload_car_month_count` VALUES ('四川省', '成都市', '市辖区', '2019-07-20', '19-07', 3221);
INSERT INTO `dy_upload_car_month_count` VALUES ('广东省', '广州市', '市辖区', '2019-07-20', '19-07', 2333);
INSERT INTO `dy_upload_car_month_count` VALUES ('广东省', '深圳市', '市辖区', '2019-07-20', '19-07', 3135);
INSERT INTO `dy_upload_car_month_count` VALUES ('上海', '上海市', '市辖区', '2019-07-20', '19-07', 2323);
INSERT INTO `dy_upload_car_month_count` VALUES ('山东省', '济南市', '市辖区', '2019-07-20', '19-07', 2213);
INSERT INTO `dy_upload_car_month_count` VALUES ('辽宁省', '大连市', '市辖区', '2019-07-20', '19-07', 1648);
INSERT INTO `dy_upload_car_month_count` VALUES ('江苏省', '南京市', '市辖区', '2019-07-20', '19-07', 1152);
INSERT INTO `dy_upload_car_month_count` VALUES ('浙江省', '杭州市', '市辖区', '2019-07-20', '19-07', 1323);
INSERT INTO `dy_upload_car_month_count` VALUES ('北京', '北京市', '市辖区', '2019-06-20', '19-06', 5821);
INSERT INTO `dy_upload_car_month_count` VALUES ('湖北省', '武汉市', '市辖区', '2019-06-20', '19-06', 3576);
INSERT INTO `dy_upload_car_month_count` VALUES ('重庆', '重庆市', '市辖区', '2019-06-20', '19-06', 1687);
INSERT INTO `dy_upload_car_month_count` VALUES ('四川省', '成都市', '市辖区', '2019-06-20', '19-06', 2156);
INSERT INTO `dy_upload_car_month_count` VALUES ('广东省', '广州市', '市辖区', '2019-06-20', '19-06', 3531);
INSERT INTO `dy_upload_car_month_count` VALUES ('广东省', '深圳市', '市辖区', '2019-06-20', '19-06', 3216);
INSERT INTO `dy_upload_car_month_count` VALUES ('上海', '上海市', '市辖区', '2019-06-20', '19-06', 5631);
INSERT INTO `dy_upload_car_month_count` VALUES ('山东省', '济南市', '市辖区', '2019-06-20', '19-06', 1568);
INSERT INTO `dy_upload_car_month_count` VALUES ('辽宁省', '大连市', '市辖区', '2019-06-20', '19-06', 1612);
INSERT INTO `dy_upload_car_month_count` VALUES ('江苏省', '南京市', '市辖区', '2019-06-20', '19-06', 1215);
INSERT INTO `dy_upload_car_month_count` VALUES ('浙江省', '杭州市', '市辖区', '2019-06-20', '19-06', 1213);

SET FOREIGN_KEY_CHECKS = 1;
