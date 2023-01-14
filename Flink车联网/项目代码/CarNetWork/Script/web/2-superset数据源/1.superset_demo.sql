/*
 Navicat Premium Data Transfer

 Source Server         : node03-mysql
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : node03:3306
 Source Schema         : superset_demo

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 28/05/2020 22:05:24
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for dm_sales
-- ----------------------------
DROP TABLE IF EXISTS `dm_sales`;
CREATE TABLE `dm_sales`  (
  `id` mediumint(8) NOT NULL AUTO_INCREMENT,
  `date1` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `channelid` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `productid` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `regionid` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `amount` int(11) NULL DEFAULT NULL,
  `price` double NULL DEFAULT NULL,
  `channelname` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `productname` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `regionname` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 160 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dm_sales
-- ----------------------------
INSERT INTO `dm_sales` VALUES (1, '2019-02-01', '01', '01', '010', 1, 3400, '商场', 'meta20', '北京');
INSERT INTO `dm_sales` VALUES (2, '2019-02-01', '02', '02', '021', 2, 6800, '京东', 'p30', '上海');
INSERT INTO `dm_sales` VALUES (3, '2019-02-01', '01', '01', '010', 1, 3400, '商场', 'meta20', '北京');
INSERT INTO `dm_sales` VALUES (4, '2019-02-01', '01', '02', '021', 1, 3400, '商场', 'p30', '上海');
INSERT INTO `dm_sales` VALUES (5, '2019-02-01', '02', '01', '010', 1, 3400, '京东', 'meta20', '北京');
INSERT INTO `dm_sales` VALUES (6, '2019-02-01', '01', '01', '021', 2, 6800, '商场', 'meta20', '上海');
INSERT INTO `dm_sales` VALUES (7, '2019-02-01', '03', '02', '010', 1, 3400, '天猫', 'p30', '北京');
INSERT INTO `dm_sales` VALUES (8, '2019-02-01', '01', '01', '021', 1, 3400, '商场', 'meta20', '上海');
INSERT INTO `dm_sales` VALUES (9, '2019-02-01', '01', '03', '010', 1, 3400, '商场', 'ihpone Xs', '北京');
INSERT INTO `dm_sales` VALUES (10, '2019-02-01', '02', '01', '021', 3, 10200, '京东', 'meta20', '上海');
INSERT INTO `dm_sales` VALUES (11, '2019-02-01', '01', '04', '010', 1, 3400, '商场', '小米 9', '北京');
INSERT INTO `dm_sales` VALUES (12, '2019-02-01', '03', '01', '021', 1, 3400, '天猫', 'meta20', '上海');
INSERT INTO `dm_sales` VALUES (13, '2019-02-01', '01', '04', '010', 1, 3400, '商场', '小米 9', '北京');
INSERT INTO `dm_sales` VALUES (14, '2019-02-02', '01', '01', '010', 1, 3400, '商场', 'meta20', '北京');
INSERT INTO `dm_sales` VALUES (15, '2019-02-02', '02', '02', '021', 2, 6800, '京东', 'p30', '上海');
INSERT INTO `dm_sales` VALUES (16, '2019-02-02', '01', '01', '010', 1, 3400, '商场', 'meta20', '北京');
INSERT INTO `dm_sales` VALUES (17, '2019-02-02', '01', '02', '021', 1, 3400, '商场', 'p30', '上海');
INSERT INTO `dm_sales` VALUES (18, '2019-02-02', '02', '01', '010', 1, 3400, '京东', 'meta20', '北京');
INSERT INTO `dm_sales` VALUES (19, '2019-02-02', '01', '01', '021', 2, 6800, '商场', 'meta20', '上海');
INSERT INTO `dm_sales` VALUES (20, '2019-02-02', '03', '02', '010', 1, 3400, '天猫', 'p30', '北京');
INSERT INTO `dm_sales` VALUES (21, '2019-02-02', '01', '01', '021', 1, 3400, '商场', 'meta20', '上海');
INSERT INTO `dm_sales` VALUES (22, '2019-02-02', '01', '03', '010', 1, 3400, '商场', 'ihpone Xs', '北京');
INSERT INTO `dm_sales` VALUES (23, '2019-02-02', '02', '01', '021', 3, 10200, '京东', 'meta20', '上海');
INSERT INTO `dm_sales` VALUES (24, '2019-02-02', '01', '04', '010', 1, 3400, '商场', '小米 9', '北京');
INSERT INTO `dm_sales` VALUES (25, '2019-02-02', '03', '01', '021', 1, 3400, '天猫', 'meta20', '上海');
INSERT INTO `dm_sales` VALUES (26, '2019-02-02', '01', '04', '010', 1, 3400, '商场', '小米 9', '北京');
INSERT INTO `dm_sales` VALUES (27, '2019-02-01', '01', '01', '010', 1, 3400, '商场', 'meta20', '北京');
INSERT INTO `dm_sales` VALUES (28, '2019-02-01', '02', '02', '021', 2, 6800, '京东', 'p30', '上海');
INSERT INTO `dm_sales` VALUES (29, '2019-02-01', '01', '01', '010', 1, 3400, '商场', 'meta20', '北京');
INSERT INTO `dm_sales` VALUES (30, '2019-02-01', '01', '02', '021', 1, 3400, '商场', 'p30', '上海');
INSERT INTO `dm_sales` VALUES (31, '2019-02-01', '02', '01', '010', 1, 3400, '京东', 'meta20', '北京');
INSERT INTO `dm_sales` VALUES (32, '2019-02-01', '01', '01', '021', 2, 6800, '商场', 'meta20', '上海');
INSERT INTO `dm_sales` VALUES (33, '2019-02-01', '03', '02', '010', 1, 3400, '天猫', 'p30', '北京');
INSERT INTO `dm_sales` VALUES (34, '2019-02-01', '01', '01', '021', 1, 3400, '商场', 'meta20', '上海');
INSERT INTO `dm_sales` VALUES (35, '2019-02-01', '01', '03', '010', 1, 3400, '商场', 'ihpone Xs', '北京');
INSERT INTO `dm_sales` VALUES (36, '2019-02-01', '02', '01', '021', 3, 10200, '京东', 'meta20', '上海');
INSERT INTO `dm_sales` VALUES (37, '2019-02-01', '01', '04', '010', 1, 3400, '商场', '小米 9', '北京');
INSERT INTO `dm_sales` VALUES (38, '2019-02-01', '03', '01', '021', 1, 3400, '天猫', 'meta20', '上海');
INSERT INTO `dm_sales` VALUES (39, '2019-02-01', '01', '04', '010', 1, 3400, '商场', '小米 9', '北京');
INSERT INTO `dm_sales` VALUES (40, '2019-02-02', '01', '01', '010', 1, 3400, '商场', 'meta20', '北京');
INSERT INTO `dm_sales` VALUES (41, '2019-02-02', '02', '02', '021', 2, 6800, '京东', 'p30', '上海');
INSERT INTO `dm_sales` VALUES (42, '2019-02-02', '01', '01', '010', 1, 3400, '商场', 'meta20', '北京');
INSERT INTO `dm_sales` VALUES (43, '2019-02-02', '01', '02', '021', 1, 3400, '商场', 'p30', '上海');
INSERT INTO `dm_sales` VALUES (44, '2019-02-02', '02', '01', '010', 1, 3400, '京东', 'meta20', '北京');
INSERT INTO `dm_sales` VALUES (45, '2019-02-02', '01', '01', '021', 2, 6800, '商场', 'meta20', '上海');
INSERT INTO `dm_sales` VALUES (46, '2019-02-02', '03', '02', '010', 1, 3400, '天猫', 'p30', '北京');
INSERT INTO `dm_sales` VALUES (47, '2019-02-02', '01', '01', '021', 1, 3400, '商场', 'meta20', '上海');
INSERT INTO `dm_sales` VALUES (48, '2019-02-02', '01', '03', '010', 1, 3400, '商场', 'ihpone Xs', '北京');
INSERT INTO `dm_sales` VALUES (49, '2019-02-02', '02', '01', '021', 3, 10200, '京东', 'meta20', '上海');
INSERT INTO `dm_sales` VALUES (50, '2019-02-02', '01', '04', '010', 1, 3400, '商场', '小米 9', '北京');
INSERT INTO `dm_sales` VALUES (51, '2019-02-02', '03', '01', '021', 1, 3400, '天猫', 'meta20', '上海');
INSERT INTO `dm_sales` VALUES (52, '2019-02-02', '01', '04', '010', 1, 3400, '商场', '小米 9', '北京');
INSERT INTO `dm_sales` VALUES (53, '2019-02-01', '01', '01', '010', 1, 3400, '商场', 'meta20', '北京');
INSERT INTO `dm_sales` VALUES (54, '2019-02-01', '02', '02', '021', 2, 6800, '京东', 'p30', '上海');
INSERT INTO `dm_sales` VALUES (55, '2019-02-01', '01', '01', '010', 1, 3400, '商场', 'meta20', '北京');
INSERT INTO `dm_sales` VALUES (56, '2019-02-01', '01', '02', '021', 1, 3400, '商场', 'p30', '上海');
INSERT INTO `dm_sales` VALUES (57, '2019-02-01', '02', '01', '010', 1, 3400, '京东', 'meta20', '北京');
INSERT INTO `dm_sales` VALUES (58, '2019-02-01', '01', '01', '021', 2, 6800, '商场', 'meta20', '上海');
INSERT INTO `dm_sales` VALUES (59, '2019-02-01', '03', '02', '010', 1, 3400, '天猫', 'p30', '北京');
INSERT INTO `dm_sales` VALUES (60, '2019-02-01', '01', '01', '021', 1, 3400, '商场', 'meta20', '上海');
INSERT INTO `dm_sales` VALUES (61, '2019-02-01', '01', '03', '010', 1, 3400, '商场', 'ihpone Xs', '北京');
INSERT INTO `dm_sales` VALUES (62, '2019-02-01', '02', '01', '021', 3, 10200, '京东', 'meta20', '上海');
INSERT INTO `dm_sales` VALUES (63, '2019-02-01', '01', '04', '010', 1, 3400, '商场', '小米 9', '北京');
INSERT INTO `dm_sales` VALUES (64, '2019-02-01', '03', '01', '021', 1, 3400, '天猫', 'meta20', '上海');
INSERT INTO `dm_sales` VALUES (65, '2019-02-01', '01', '04', '010', 1, 3400, '商场', '小米 9', '北京');
INSERT INTO `dm_sales` VALUES (66, '2019-02-02', '01', '01', '010', 1, 3400, '商场', 'meta20', '郑州');
INSERT INTO `dm_sales` VALUES (67, '2019-02-02', '02', '02', '021', 2, 6800, '京东', 'p30', '上海');
INSERT INTO `dm_sales` VALUES (68, '2019-02-02', '01', '01', '010', 1, 3400, '商场', 'meta20', '北京');
INSERT INTO `dm_sales` VALUES (69, '2019-02-02', '01', '02', '021', 1, 3400, '商场', 'p30', '上海');
INSERT INTO `dm_sales` VALUES (70, '2019-02-02', '02', '01', '010', 1, 3400, '京东', 'meta20', '北京');
INSERT INTO `dm_sales` VALUES (71, '2019-02-02', '01', '01', '021', 2, 6800, '商场', 'meta20', '上海');
INSERT INTO `dm_sales` VALUES (72, '2019-02-02', '03', '02', '010', 1, 3400, '天猫', 'p30', '北京');
INSERT INTO `dm_sales` VALUES (73, '2019-02-02', '01', '01', '021', 1, 3400, '商场', 'meta20', '上海');
INSERT INTO `dm_sales` VALUES (74, '2019-02-02', '01', '03', '010', 1, 3400, '商场', 'ihpone Xs', '北京');
INSERT INTO `dm_sales` VALUES (75, '2019-02-02', '02', '01', '021', 3, 10200, '京东', 'meta20', '上海');
INSERT INTO `dm_sales` VALUES (76, '2019-02-02', '01', '04', '010', 1, 3400, '商场', '小米 9', '北京');
INSERT INTO `dm_sales` VALUES (77, '2019-02-02', '03', '01', '021', 1, 3400, '天猫', 'meta20', '上海');
INSERT INTO `dm_sales` VALUES (78, '2019-02-02', '01', '04', '010', 1, 3400, '商场', '小米 9', '北京');
INSERT INTO `dm_sales` VALUES (79, '2019-02-03', '01', '04', '010', 1, 61400, '商场', '小米 9', '北京');
INSERT INTO `dm_sales` VALUES (80, '2019-02-04', '02', '04', '010', 1, 68400, '商场', '小米 9', '北京');
INSERT INTO `dm_sales` VALUES (81, '2019-02-05', '03', '04', '010', 1, 71400, '商场', '小米 9', '杭州');
INSERT INTO `dm_sales` VALUES (82, '2019-02-06', '01', '04', '010', 1, 81400, '商场', '小米 9', '上海');
INSERT INTO `dm_sales` VALUES (83, '2019-02-07', '02', '04', '010', 1, 93410, '商场', '小米 9', '深圳');
INSERT INTO `dm_sales` VALUES (84, '2019-02-08', '02', '04', '010', 1, 113410, '商场', '小米 9', '深圳');
INSERT INTO `dm_sales` VALUES (85, '2019-02-09', '01', '04', '010', 1, 53410, '商场', '小米 9', '沭阳');
INSERT INTO `dm_sales` VALUES (87, '2019-02-01', '01', '04', '010', 1, 3400, '商场', 'meta20', '南通');
INSERT INTO `dm_sales` VALUES (88, '2019-02-01', '03', '04', '010', 1, 7300, '商场', '小米 9', '杭州');
INSERT INTO `dm_sales` VALUES (90, '2019-02-01', '01', '04', '010', 1, 53410, '商场', '小米 9', '沭阳');
INSERT INTO `dm_sales` VALUES (91, '2019-02-01', '02', '04', '010', 1, 133410, '商场', '小米 9', '深圳');
INSERT INTO `dm_sales` VALUES (92, '2019-02-01', '01', '01', '010', 1, 8800, '商场', 'meta20', '郑州');
INSERT INTO `dm_sales` VALUES (93, '2019-02-01', '01', '04', '010', 1, 3400, '商场', 'meta20', '南通');
INSERT INTO `dm_sales` VALUES (94, '2019-02-01', '03', '04', '010', 1, 7300, '商场', '小米 9', '杭州');
INSERT INTO `dm_sales` VALUES (95, '2019-02-01', '01', '04', '010', 1, 53410, '商场', '小米 9', '沭阳');
INSERT INTO `dm_sales` VALUES (96, '2019-02-01', '02', '04', '010', 1, 133410, '商场', '小米 9', '深圳');
INSERT INTO `dm_sales` VALUES (97, '2019-02-01', '01', '01', '010', 1, 8800, '商场', 'meta20', '郑州');
INSERT INTO `dm_sales` VALUES (98, '2019-02-02', '01', '04', '010', 1, 5200, '商场', 'meta20', '南通');
INSERT INTO `dm_sales` VALUES (99, '2019-02-02', '03', '04', '010', 1, 6300, '商场', '小米 9', '杭州');
INSERT INTO `dm_sales` VALUES (100, '2019-02-02', '01', '04', '010', 1, 23410, '商场', '小米 9', '沭阳');
INSERT INTO `dm_sales` VALUES (101, '2019-02-02', '02', '04', '010', 1, 153410, '商场', '小米 9', '深圳');
INSERT INTO `dm_sales` VALUES (102, '2019-02-02', '01', '01', '010', 1, 18800, '商场', 'meta20', '郑州');
INSERT INTO `dm_sales` VALUES (103, '2019-02-03', '01', '04', '010', 1, 5118, '商场', 'meta20', '南通');
INSERT INTO `dm_sales` VALUES (104, '2019-02-03', '03', '04', '010', 1, 6520, '商场', '小米 9', '杭州');
INSERT INTO `dm_sales` VALUES (105, '2019-02-03', '01', '04', '010', 1, 23610, '商场', '小米 9', '沭阳');
INSERT INTO `dm_sales` VALUES (106, '2019-02-03', '02', '04', '010', 1, 155410, '商场', '小米 9', '深圳');
INSERT INTO `dm_sales` VALUES (107, '2019-02-03', '01', '01', '010', 1, 19800, '商场', 'meta20', '郑州');
INSERT INTO `dm_sales` VALUES (108, '2019-02-04', '01', '04', '010', 1, 5518, '商场', 'meta20', '南通');
INSERT INTO `dm_sales` VALUES (109, '2019-02-04', '03', '04', '010', 1, 7120, '商场', '小米 9', '杭州');
INSERT INTO `dm_sales` VALUES (110, '2019-02-04', '01', '04', '010', 1, 65110, '商场', '小米 9', '沭阳');
INSERT INTO `dm_sales` VALUES (111, '2019-02-04', '02', '04', '010', 1, 132110, '商场', '小米 9', '深圳');
INSERT INTO `dm_sales` VALUES (112, '2019-02-04', '01', '01', '010', 1, 39800, '商场', 'meta20', '郑州');
INSERT INTO `dm_sales` VALUES (113, '2019-02-05', '01', '04', '010', 1, 6518, '商场', 'meta20', '南通');
INSERT INTO `dm_sales` VALUES (114, '2019-02-05', '03', '04', '010', 1, 7620, '商场', '小米 9', '杭州');
INSERT INTO `dm_sales` VALUES (115, '2019-02-05', '01', '04', '010', 1, 66110, '商场', '小米 9', '沭阳');
INSERT INTO `dm_sales` VALUES (116, '2019-02-05', '02', '04', '010', 1, 162110, '商场', '小米 9', '深圳');
INSERT INTO `dm_sales` VALUES (117, '2019-02-05', '01', '01', '010', 1, 36800, '商场', 'meta20', '郑州');
INSERT INTO `dm_sales` VALUES (118, '2019-02-06', '01', '04', '010', 1, 6818, '商场', 'meta20', '南通');
INSERT INTO `dm_sales` VALUES (119, '2019-02-06', '03', '04', '010', 1, 7880, '商场', '小米 9', '杭州');
INSERT INTO `dm_sales` VALUES (120, '2019-02-06', '01', '04', '010', 1, 68180, '商场', '小米 9', '沭阳');
INSERT INTO `dm_sales` VALUES (121, '2019-02-06', '02', '04', '010', 1, 18810, '商场', '小米 9', '深圳');
INSERT INTO `dm_sales` VALUES (122, '2019-02-06', '01', '01', '010', 1, 88800, '商场', 'meta20', '郑州');
INSERT INTO `dm_sales` VALUES (123, '2019-02-07', '01', '04', '010', 1, 6718, '商场', 'meta20', '南通');
INSERT INTO `dm_sales` VALUES (124, '2019-02-07', '03', '04', '010', 1, 7770, '商场', '小米 9', '杭州');
INSERT INTO `dm_sales` VALUES (125, '2019-02-07', '01', '04', '010', 1, 6780, '商场', '小米 9', '沭阳');
INSERT INTO `dm_sales` VALUES (126, '2019-02-07', '02', '04', '010', 1, 17810, '商场', '小米 9', '深圳');
INSERT INTO `dm_sales` VALUES (127, '2019-02-07', '01', '01', '010', 1, 7700, '商场', 'meta20', '郑州');
INSERT INTO `dm_sales` VALUES (128, '2019-02-08', '01', '04', '010', 1, 8718, '商场', 'meta20', '南通');
INSERT INTO `dm_sales` VALUES (129, '2019-02-08', '03', '04', '010', 1, 8770, '商场', '小米 9', '杭州');
INSERT INTO `dm_sales` VALUES (130, '2019-02-08', '01', '04', '010', 1, 8780, '商场', '小米 9', '沭阳');
INSERT INTO `dm_sales` VALUES (131, '2019-02-08', '02', '04', '010', 1, 87810, '商场', '小米 9', '深圳');
INSERT INTO `dm_sales` VALUES (132, '2019-02-08', '01', '01', '010', 1, 8700, '商场', 'meta20', '郑州');
INSERT INTO `dm_sales` VALUES (133, '2019-02-09', '01', '04', '010', 1, 8918, '商场', 'meta20', '南通');
INSERT INTO `dm_sales` VALUES (134, '2019-02-09', '03', '04', '010', 1, 8990, '商场', '小米 9', '杭州');
INSERT INTO `dm_sales` VALUES (135, '2019-02-09', '01', '04', '010', 1, 9990, '商场', '小米 9', '沭阳');
INSERT INTO `dm_sales` VALUES (136, '2019-02-09', '02', '04', '010', 1, 19810, '商场', '小米 9', '深圳');
INSERT INTO `dm_sales` VALUES (137, '2019-02-09', '01', '01', '010', 1, 9900, '商场', 'meta20', '郑州');
INSERT INTO `dm_sales` VALUES (138, '2019-02-10', '01', '04', '010', 1, 10918, '商场', 'meta20', '南通');
INSERT INTO `dm_sales` VALUES (139, '2019-02-10', '03', '04', '010', 1, 10990, '商场', '小米 9', '杭州');
INSERT INTO `dm_sales` VALUES (140, '2019-02-10', '01', '04', '010', 1, 10190, '商场', '小米 9', '沭阳');
INSERT INTO `dm_sales` VALUES (141, '2019-02-10', '02', '04', '010', 1, 10810, '商场', '小米 9', '深圳');
INSERT INTO `dm_sales` VALUES (142, '2019-02-10', '01', '01', '010', 1, 9100, '商场', 'meta20', '郑州');
INSERT INTO `dm_sales` VALUES (143, '2019-02-04', '01', '04', '010', 1, 5200, '商场', 'meta20', '北京');
INSERT INTO `dm_sales` VALUES (144, '2019-02-04', '03', '04', '021', 1, 6300, '商场', '小米 9', '上海');
INSERT INTO `dm_sales` VALUES (145, '2019-02-03', '02', '04', '010', 1, 133410, '商场', '小米 9', '北京');
INSERT INTO `dm_sales` VALUES (146, '2019-02-03', '01', '01', '021', 1, 8800, '商场', 'meta20', '上海');
INSERT INTO `dm_sales` VALUES (147, '2019-02-05', '02', '04', '010', 1, 133410, '商场', '小米 9', '北京');
INSERT INTO `dm_sales` VALUES (148, '2019-02-05', '01', '01', '021', 1, 8800, '商场', 'meta20', '上海');
INSERT INTO `dm_sales` VALUES (149, '2019-02-06', '02', '04', '010', 1, 66410, '商场', '小米 9', '北京');
INSERT INTO `dm_sales` VALUES (150, '2019-02-06', '01', '01', '021', 1, 68890, '商场', 'meta20', '上海');
INSERT INTO `dm_sales` VALUES (151, '2019-02-07', '02', '04', '010', 1, 67570, '商场', '小米 9', '北京');
INSERT INTO `dm_sales` VALUES (152, '2019-02-07', '01', '01', '021', 1, 67790, '商场', 'meta20', '上海');
INSERT INTO `dm_sales` VALUES (153, '2019-02-07', '02', '01', '0175', 1, 5570, '商场', '小米 9', '沭阳');
INSERT INTO `dm_sales` VALUES (154, '2019-02-07', '02', '01', '017', 1, 26570, '商场', '小米 9', '杭州');
INSERT INTO `dm_sales` VALUES (155, '2019-02-07', '01', '01', '021', 1, 15790, '商场', 'meta20', '郑州');
INSERT INTO `dm_sales` VALUES (156, '2019-02-07', '02', '01', '010', 1, 76570, '商场', '小米 9', '北京');
INSERT INTO `dm_sales` VALUES (157, '2019-02-07', '01', '01', '0176', 1, 9790, '商场', 'meta20', '南通');
INSERT INTO `dm_sales` VALUES (158, '2019-02-06', '02', '01', '010', 1, 8570, '天猫', '小米9', '郑州');
INSERT INTO `dm_sales` VALUES (159, '2019-02-07', '01', '01', '0176', 1, 79790, '天猫', 'meta20', '上海');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` tinytext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `name` tinytext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `age` int(11) NULL DEFAULT NULL,
  `gender` int(11) NULL DEFAULT NULL,
  `province` tinytext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `city` tinytext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `region` tinytext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `phone` tinytext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `birthday` tinytext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `hobby` tinytext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `register_date` tinytext CHARACTER SET utf8 COLLATE utf8_general_ci NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('392456197008193000', '张三', 20, 0, '北京市', '昌平区', '回龙观', '18589407692', '1995-08-19', '美食;篮球;足球', '2018-08-06 09:44:43');
INSERT INTO `t_user` VALUES ('267456198006210000', '李四', 25, 1, '河南省', '郑州市', '郑东新区', '18681109672', '1980-06-21', '音乐;阅读;旅游', '2017-04-07 09:14:13');
INSERT INTO `t_user` VALUES ('892456199007203000', '王五', 24, 1, '湖北省', '武汉市', '汉阳区', '18798009102', '1990-07-20', '写代码;读代码;算法', '2016-06-08 07:34:23');
INSERT INTO `t_user` VALUES ('492456198712198000', '赵六', 26, 2, '陕西省', '西安市', '莲湖区', '18189189195', '1987-12-19', '购物;旅游', '2016-01-09 19:15:53');
INSERT INTO `t_user` VALUES ('267456198006210123', '李四2', 26, 1, '河南省', '郑州市', '郑东新区', '18681109672', '1980-06-21', '音乐;阅读;旅游', '2018-04-07 09:14:13');
INSERT INTO `t_user` VALUES ('392456197008193000', '张三2', 21, 0, '北京市', '昌平区', '回龙观', '18589407692', '1995-08-19', '美食;篮球;足球', '2019-08-06 09:44:43');
INSERT INTO `t_user` VALUES ('267456198006210112', '李四3', 27, 1, '河南省', '郑州市', '郑东新区', '18681109672', '1980-06-21', '音乐;阅读;旅游', '2018-04-07 09:14:13');
INSERT INTO `t_user` VALUES ('420945619871219135', '田七2', 20, 2, '湖北省', '武汉市', '武昌区', '13689149112', '1995-12-19', '美食;旅游;代码', '2019-01-05 13:15:53');
INSERT INTO `t_user` VALUES ('420945619871219215', '田七3', 20, 2, '湖北省', '武汉市', '武昌区', '13569789135', '1995-12-19', '美食;旅游;代码', '2019-01-05 13:15:53');

SET FOREIGN_KEY_CHECKS = 1;
