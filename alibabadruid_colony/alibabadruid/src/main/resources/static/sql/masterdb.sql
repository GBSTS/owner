/*
Navicat MySQL Data Transfer

Source Server         : 本地数据库
Source Server Version : 50732
Source Host           : localhost:3306
Source Database       : masterdb

Target Server Type    : MYSQL
Target Server Version : 50732
File Encoding         : 65001

Date: 2020-12-31 13:31:08
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for rights
-- ----------------------------
DROP TABLE IF EXISTS `rights`;
CREATE TABLE `rights` (
  `rightid` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(255) DEFAULT NULL,
  `rolenames` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`rightid`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of rights
-- ----------------------------
INSERT INTO `rights` VALUES ('1', '/admin/**', 'ROLE_ADMIN,ROLE_SUPERADMIN');
INSERT INTO `rights` VALUES ('2', '/student/**', 'ROLE_STUDENT,ROLE_SUPERADMIN');
INSERT INTO `rights` VALUES ('3', '/teacher/**', 'ROLE_TEACHER,ROLE_SUPERADMIN');
INSERT INTO `rights` VALUES ('12', '/**', 'ROLE_ANONYMOUS,ROLE_USER');
