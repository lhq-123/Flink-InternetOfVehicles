/*
 Navicat Premium Data Transfer

 Source Server         : allin-mysql
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : allin:3306
 Source Schema         : vehicle_networking

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 21/03/2020 02:30:25
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for itcast_data_rate
-- ----------------------------
DROP TABLE IF EXISTS `itcast_data_rate`;
CREATE TABLE `itcast_data_rate`  (
  `series_no` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '记录序列号',
  `src_total_num` bigint(20) NOT NULL COMMENT '原数据正确数据总数',
  `error_src_total_num` bigint(20) NOT NULL COMMENT '原数据错误数据总数',
  `data_accuracy` float(7, 4) NOT NULL COMMENT '原始数据正确率',
  `data_error_rate` float(7, 4) NOT NULL COMMENT '原始数据错误率',
  `process_date` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '记录计算时间'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of itcast_data_rate
-- ----------------------------
INSERT INTO `itcast_data_rate` VALUES ('3aac0ac2-31f7-42c7-adb4-2c55bcb2b217', 1000000, 10000, 99.0000, 1.0000, '2020-03-08 15:31:34');
INSERT INTO `itcast_data_rate` VALUES ('31bc0ac2-31f7-42c7-adb4-2c55bcb2a188', 100000, 500, 99.5000, 0.5000, '2020-03-09 16:49:10');
INSERT INTO `itcast_data_rate` VALUES ('30da0ac2-31d7-42c7-abb4-2c55bcb2a166', 95982, 1500, 0.9800, 0.0200, '2020-03-14 01:39:59');
INSERT INTO `itcast_data_rate` VALUES ('5ac4ff34-523c-4cfb-b282-c129fa9745b0', 95982, 1500, 0.9846, 0.0154, '2020-03-14 01:49:07');
INSERT INTO `itcast_data_rate` VALUES ('07bbcbf7-e7fc-4221-9e9d-13ded4a9420d', 95982, 1500, 0.9846, 0.0154, '2020-03-14 01:59:13');
INSERT INTO `itcast_data_rate` VALUES ('99f96ffd-3817-409e-920b-70112f596cd4', 143746, 2256, 0.9845, 0.0155, '2020-03-14 12:14:46');

-- ----------------------------
-- Table structure for itcast_data_rate_day
-- ----------------------------
DROP TABLE IF EXISTS `itcast_data_rate_day`;
CREATE TABLE `itcast_data_rate_day`  (
  `series_no` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '记录序列号',
  `src_total_num` bigint(20) NOT NULL COMMENT '原数据正确数据总数',
  `error_src_total_num` bigint(20) NOT NULL COMMENT '原数据错误数据总数',
  `data_accuracy` float(7, 4) NOT NULL COMMENT '原始数据正确率',
  `data_error_rate` float(7, 4) NOT NULL COMMENT '原始数据错误率',
  `day` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '按天统计',
  `process_date` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '记录计算时间'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of itcast_data_rate_day
-- ----------------------------
INSERT INTO `itcast_data_rate_day` VALUES ('0d184307-2809-45ab-a8a6-4623d89889c6', 143746, 2256, 0.9845, 0.0155, '20200314', '2020-03-17 01:00:23');
INSERT INTO `itcast_data_rate_day` VALUES ('7f239259-20ad-48bc-9ac9-0b75f768cdcf', 143746, 2256, 0.9845, 0.0155, '20200314', '2020-03-17 01:03:30');
INSERT INTO `itcast_data_rate_day` VALUES ('56c6f71d-98de-4b11-9b51-98d7c2a27230', 143746, 2256, 0.9845, 0.0155, '20200300', '2020-03-17 01:09:55');
INSERT INTO `itcast_data_rate_day` VALUES ('ff9b895c-94e3-4d37-a07c-b003523041fc', 143746, 2256, 0.9845, 0.0155, '20200300', '2020-03-17 01:18:21');
INSERT INTO `itcast_data_rate_day` VALUES ('45d12650-73bc-4438-9f0d-a023ca14c79c', 143746, 2256, 0.9845, 0.0155, '20200300', '2020-03-17 01:21:49');
INSERT INTO `itcast_data_rate_day` VALUES ('abe05ece-c7ba-4f4b-a2c7-e5ff0f2a3e0b', 143746, 2256, 0.9845, 0.0155, '20200300', '2020-03-17 01:30:15');

-- ----------------------------
-- Table structure for itcast_data_rate_month
-- ----------------------------
DROP TABLE IF EXISTS `itcast_data_rate_month`;
CREATE TABLE `itcast_data_rate_month`  (
  `series_no` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '记录序列号',
  `src_total_num` bigint(20) NOT NULL COMMENT '原数据正确数据总数',
  `error_src_total_num` bigint(20) NOT NULL COMMENT '原数据错误数据总数',
  `data_accuracy` float(7, 4) NOT NULL COMMENT '原始数据正确率',
  `data_error_rate` float(7, 4) NOT NULL COMMENT '原始数据错误率',
  `month` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '按月统计',
  `process_date` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '记录计算时间'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of itcast_data_rate_month
-- ----------------------------
INSERT INTO `itcast_data_rate_month` VALUES ('45d12650-73bc-4438-9f0d-a023ca14c79c', 143746, 2256, 0.9845, 0.0155, '20200300', '2020-03-17 01:21:49');
INSERT INTO `itcast_data_rate_month` VALUES ('abe05ece-c7ba-4f4b-a2c7-e5ff0f2a3e0b', 143746, 2256, 0.9845, 0.0155, '20200300', '2020-03-17 01:30:15');

-- ----------------------------
-- Table structure for itcast_data_rate_week
-- ----------------------------
DROP TABLE IF EXISTS `itcast_data_rate_week`;
CREATE TABLE `itcast_data_rate_week`  (
  `series_no` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '记录序列号',
  `src_total_num` bigint(20) NOT NULL COMMENT '原数据正确数据总数',
  `error_src_total_num` bigint(20) NOT NULL COMMENT '原数据错误数据总数',
  `data_accuracy` float(7, 4) NOT NULL COMMENT '原始数据正确率',
  `data_error_rate` float(7, 4) NOT NULL COMMENT '原始数据错误率',
  `week` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '按周统计',
  `process_date` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '记录计算时间'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of itcast_data_rate_week
-- ----------------------------
INSERT INTO `itcast_data_rate_week` VALUES ('0d184307-2809-45ab-a8a6-4623d89889c6', 145646, 3264, 0.9781, 0.0219, '20200316', '2020-03-17 01:00:23');
INSERT INTO `itcast_data_rate_week` VALUES ('7f239259-20ad-48bc-9ac9-0b75f768cdcf', 143746, 2256, 0.9845, 0.0155, '20200316', '2020-03-17 01:03:30');

SET FOREIGN_KEY_CHECKS = 1;
