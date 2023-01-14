SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for electric_fence
-- ----------------------------
DROP TABLE IF EXISTS `electric_fence`;
CREATE TABLE `electric_fence` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `vin` varchar(255) NOT NULL COMMENT '车架号',
  `inTime` varchar(25) DEFAULT NULL COMMENT '进电子围栏时间',
  `outTime` varchar(25) DEFAULT NULL COMMENT '出电子围栏时间',
  `gpsTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '位置时间',
  `lat` double NOT NULL COMMENT '位置纬度',
  `lng` double NOT NULL COMMENT '位置经度',
  `eleId` int(11) DEFAULT NULL COMMENT '电子围栏ID',
  `eleName` varchar(255) NOT NULL COMMENT '电子围栏名称',
  `address` varchar(255) NOT NULL COMMENT '中心点地址',
  `latitude` double NOT NULL COMMENT '中心点纬度',
  `longitude` double NOT NULL COMMENT '中心点经度',
  `radius` float NOT NULL COMMENT '电子围栏半径',
  `terminalTime` varchar(255) DEFAULT NULL COMMENT '终端时间',
  `processTime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '插入数据的时间',
  PRIMARY KEY (`id`),
  KEY `vin` (`vin`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='电子围栏';
