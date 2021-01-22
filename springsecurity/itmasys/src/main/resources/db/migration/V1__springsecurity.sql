/*
Navicat MySQL Data Transfer

Source Server         : 本地数据库
Source Server Version : 50732
Source Host           : localhost:3306
Source Database       : itmasys

Target Server Type    : MYSQL
Target Server Version : 50732
File Encoding         : 65001

Date: 2020-12-28 10:26:02
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
INSERT INTO `rights` VALUES ('1', '/company/**', 'ROLE_COMPANY,ROLE_ADMIN');
INSERT INTO `rights` VALUES ('2', '/implemeter/**', 'ROLE_IMPLEMETER,ROLE_ADMIN');
INSERT INTO `rights` VALUES ('3', '/approved/**', 'ROLE_APPROVED,ROLE_ADMIN');
INSERT INTO `rights` VALUES ('12', '/**', 'ROLE_ANONYMOUS,ROLE_USER');

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
  `roleid` int(2) NOT NULL AUTO_INCREMENT,
  `rolename` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`roleid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of roles
-- ----------------------------
INSERT INTO `roles` VALUES ('1', 'ROLE_USER');
INSERT INTO `roles` VALUES ('2', 'ROLE_ADMIN');
INSERT INTO `roles` VALUES ('3', 'ROLE_APPROVED');
INSERT INTO `roles` VALUES ('4', 'ROLE_COMPANY');
INSERT INTO `roles` VALUES ('5', 'ROLE_IMPLEMETER');

-- ----------------------------
-- Table structure for sysuser
-- ----------------------------
DROP TABLE IF EXISTS `sysuser`;
CREATE TABLE `sysuser` (
  `userid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `roleid` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`userid`,`username`),
  KEY `username` (`username`),
  KEY `role1` (`roleid`),
  KEY `userid` (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sysuser
-- ----------------------------
INSERT INTO `sysuser` VALUES ('1', 'user', '$2a$10$9SIFu8l8asZUKxtwqrJM5ujhWarz/PMnTX44wXNsBHfpJMakWw3M6', '1');
INSERT INTO `sysuser` VALUES ('2', 'admin', '$2a$10$9SIFu8l8asZUKxtwqrJM5ujhWarz/PMnTX44wXNsBHfpJMakWw3M6', '1,2');
INSERT INTO `sysuser` VALUES ('3', 'approved', '$2a$10$9SIFu8l8asZUKxtwqrJM5ujhWarz/PMnTX44wXNsBHfpJMakWw3M6', '1,3');
INSERT INTO `sysuser` VALUES ('4', 'company', '$2a$10$9SIFu8l8asZUKxtwqrJM5ujhWarz/PMnTX44wXNsBHfpJMakWw3M6', '1,4');
INSERT INTO `sysuser` VALUES ('5', 'implemeter', '$2a$10$9SIFu8l8asZUKxtwqrJM5ujhWarz/PMnTX44wXNsBHfpJMakWw3M6', '1,5');
