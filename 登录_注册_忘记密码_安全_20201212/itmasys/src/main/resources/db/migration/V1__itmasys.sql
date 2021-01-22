/*
Navicat MySQL Data Transfer

Source Server         : 本地数据库
Source Server Version : 50732
Source Host           : localhost:3306
Source Database       : itmasys

Target Server Type    : MYSQL
Target Server Version : 50732
File Encoding         : 65001

Date: 2020-12-11 22:01:11
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for personinfo
-- ----------------------------
DROP TABLE IF EXISTS `personinfo`;
CREATE TABLE `personinfo` (
  `userid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `education` varchar(255) DEFAULT NULL,
  `cardid` int(20) DEFAULT NULL,
  PRIMARY KEY (`userid`),
  CONSTRAINT `userid` FOREIGN KEY (`userid`) REFERENCES `sysuser` (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of personinfo
-- ----------------------------
INSERT INTO `personinfo` VALUES ('5', 'company', '17366759223', '3350861305@qq.com', null, null, '1');
INSERT INTO `personinfo` VALUES ('6', 'approved', '17366759223', '3350861305@qq.com', null, null, '2');
INSERT INTO `personinfo` VALUES ('7', 'implementer', '17366759223', '3350861305@qq.com', null, null, '3');

-- ----------------------------
-- Table structure for register
-- ----------------------------
DROP TABLE IF EXISTS `register`;
CREATE TABLE `register` (
  `registerid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `roleid` int(2) DEFAULT NULL,
  `randomnumber` int(6) DEFAULT NULL,
  PRIMARY KEY (`registerid`),
  KEY `role2` (`roleid`),
  CONSTRAINT `role2` FOREIGN KEY (`roleid`) REFERENCES `role` (`roleid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of register
-- ----------------------------

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `roleid` int(2) NOT NULL AUTO_INCREMENT,
  `rolename` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`roleid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', 'company');
INSERT INTO `role` VALUES ('2', 'approved');
INSERT INTO `role` VALUES ('3', 'implementer');

-- ----------------------------
-- Table structure for sysuser
-- ----------------------------
DROP TABLE IF EXISTS `sysuser`;
CREATE TABLE `sysuser` (
  `userid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `roleid` int(2) DEFAULT NULL,
  PRIMARY KEY (`userid`,`username`),
  KEY `username` (`username`),
  KEY `role1` (`roleid`),
  KEY `userid` (`userid`),
  CONSTRAINT `role1` FOREIGN KEY (`roleid`) REFERENCES `role` (`roleid`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sysuser
-- ----------------------------
INSERT INTO `sysuser` VALUES ('5', 'company', '$2a$10$EyLck4wy/Eb6fT7XW4o8Aeg245n4fsmdHTUUD0xU9vcze9P9C3JKK', '1');
INSERT INTO `sysuser` VALUES ('6', 'approved', '$2a$10$s7RW.Zl3tcPCqpLKnCq4HelMkKRYvxzuzhET9XtrypXoq4MUAi.lq', '2');
INSERT INTO `sysuser` VALUES ('7', 'implementer', '$2a$10$AxomZjJh5TFqVRs6lir2vekY6G.tcn.Oz3wJ8yBY.rHKr3RbSr7Wa', '3');
