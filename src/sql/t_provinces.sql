/*
Navicat MySQL Data Transfer

Source Server         : xiaoba
Source Server Version : 50625
Source Host           : localhost:3306
Source Database       : driver

Target Server Type    : MYSQL
Target Server Version : 50625
File Encoding         : 65001

Date: 2015-08-04 18:05:09
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_provinces
-- ----------------------------
DROP TABLE IF EXISTS `t_provinces`;
CREATE TABLE `t_provinces` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `provinceid` int(11) NOT NULL,
  `province` varchar(50) NOT NULL,
  `hotKey` varchar(255) DEFAULT NULL,
  `baiduid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=37 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='省份';

-- ----------------------------
-- Records of t_provinces
-- ----------------------------
INSERT INTO `t_provinces` VALUES ('1', '110000', '北京市', 'BJ', '131');
INSERT INTO `t_provinces` VALUES ('2', '120000', '天津市', 'TJ', '332');
INSERT INTO `t_provinces` VALUES ('3', '130000', '河北省', 'HB', '25');
INSERT INTO `t_provinces` VALUES ('4', '140000', '山西省', 'SX', '10');
INSERT INTO `t_provinces` VALUES ('5', '150000', '内蒙古自治区', 'NMG', '22');
INSERT INTO `t_provinces` VALUES ('6', '210000', '辽宁省', 'LN', '19');
INSERT INTO `t_provinces` VALUES ('7', '220000', '吉林省', 'JL', '9');
INSERT INTO `t_provinces` VALUES ('8', '230000', '黑龙江省', 'HLJ', '2');
INSERT INTO `t_provinces` VALUES ('10', '320000', '江苏省', 'JS', '18');
INSERT INTO `t_provinces` VALUES ('11', '330000', '浙江省', 'ZJ', '29');
INSERT INTO `t_provinces` VALUES ('12', '340000', '安徽省', 'AH', '23');
INSERT INTO `t_provinces` VALUES ('13', '350000', '福建省', 'FJ', '16');
INSERT INTO `t_provinces` VALUES ('14', '360000', '江西省', 'SX', '31');
INSERT INTO `t_provinces` VALUES ('15', '370000', '山东省', 'SD', '8');
INSERT INTO `t_provinces` VALUES ('16', '410000', '河南省', 'HN', '30');
INSERT INTO `t_provinces` VALUES ('17', '420000', '湖北省', 'HB', '15');
INSERT INTO `t_provinces` VALUES ('18', '430000', '湖南省', 'HN', '26');
INSERT INTO `t_provinces` VALUES ('19', '440000', '广东省', 'GD', '7');
INSERT INTO `t_provinces` VALUES ('20', '450000', '广西壮族自治区', 'GX', '17');
INSERT INTO `t_provinces` VALUES ('21', '460000', '海南省', 'HN', '21');
INSERT INTO `t_provinces` VALUES ('23', '510000', '四川省', 'SC', '32');
INSERT INTO `t_provinces` VALUES ('24', '520000', '贵州省', 'GZ', '24');
INSERT INTO `t_provinces` VALUES ('25', '530000', '云南省', 'YN', '28');
INSERT INTO `t_provinces` VALUES ('26', '540000', '西藏自治区', 'XZ', '13');
INSERT INTO `t_provinces` VALUES ('27', '610000', '陕西省', 'SX', '27');
INSERT INTO `t_provinces` VALUES ('28', '620000', '甘肃省', 'GS', '6');
INSERT INTO `t_provinces` VALUES ('29', '630000', '青海省', 'QH', '11');
INSERT INTO `t_provinces` VALUES ('30', '640000', '宁夏回族自治区', 'NX', '20');
INSERT INTO `t_provinces` VALUES ('31', '650000', '新疆维吾尔自治区', 'XJ', '12');
INSERT INTO `t_provinces` VALUES ('33', '810000', '香港特别行政区', 'XG', '2912');
INSERT INTO `t_provinces` VALUES ('34', '820000', '澳门特别行政区', 'AM', '2911');
INSERT INTO `t_provinces` VALUES ('35', '310000', '上海市', 'SH', '289');
INSERT INTO `t_provinces` VALUES ('36', '500000', '重庆市', 'CC', '132');
