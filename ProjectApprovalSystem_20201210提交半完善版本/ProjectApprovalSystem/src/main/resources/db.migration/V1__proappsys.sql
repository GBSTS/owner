/*
Navicat MySQL Data Transfer

Source Server         : 本地数据库
Source Server Version : 50732
Source Host           : localhost:3306
Source Database       : proappsys

Target Server Type    : MYSQL
Target Server Version : 50732
File Encoding         : 65001

Date: 2020-12-10 09:28:36
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for apply
-- ----------------------------
DROP TABLE IF EXISTS `apply`;
CREATE TABLE `apply` (
  `applyId` int(50) NOT NULL AUTO_INCREMENT,
  `itemId` int(50) DEFAULT NULL,
  `itemName` varchar(255) DEFAULT NULL,
  `itemMoney` int(50) DEFAULT NULL,
  `itemDetailed` varchar(5000) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `reason` varchar(255) DEFAULT NULL,
  `userId` int(50) DEFAULT NULL,
  `account` varchar(255) DEFAULT NULL,
  `finish` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`applyId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of apply
-- ----------------------------
INSERT INTO `apply` VALUES ('1', '1', '项目1', '100000', '完成本公司的任务', '审批通过', '', '1', 'abc', 'False');
INSERT INTO `apply` VALUES ('2', '2', '项目2', '50000', '完成本公司的需求。完成本公司的需求。完成本公司的需求。完成本公司的需求。完成本公司的需求。完成本公司的需求。完成本公司的需求。完成本公司的需求。完成本公司的需求。完成本公司的需求。完成本公司的需求。完成本公司的需求。完成本公司的需求。完成本公司的需求。完成本公司的需求。完成本公司的需求。完成本公司的需求。完成本公司的需求。完成本公司的需求。完成本公司的需求。完成本公司的需求。完成本公司的需求。完成本公司的需求。完成本公司的需求。完成本公司的需求。完成本公司的需求。完成本公司的需求。完成本公司的需求。完成本公司的需求。完成本公司的需求。完成本公司的需求。完成本公司的需求。完成本公司的需求。完成本公司的需求。完成本公司的需求。完成本公司的需求。完成本公司的需求。', '等待审批', '', '1', 'abc', null);
INSERT INTO `apply` VALUES ('3', '3', '项目3', '150000', ',true,true,true,true,true,true,true,true4184156174856181\r\n@东方闪电', '拒绝申请', '', '1', 'abc', null);
INSERT INTO `apply` VALUES ('4', '3', '项目3', '150000', ',true,true,true,true,true,true,true,true4184156174856181\r\n@东方闪电', '等待审批', '', '1', 'abc', null);

-- ----------------------------
-- Table structure for company
-- ----------------------------
DROP TABLE IF EXISTS `company`;
CREATE TABLE `company` (
  `companyId` int(50) NOT NULL AUTO_INCREMENT,
  `companyName` varchar(255) DEFAULT NULL,
  `userId` int(50) DEFAULT NULL,
  `companyBriefIntroduction` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`companyId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of company
-- ----------------------------
INSERT INTO `company` VALUES ('1', 'company', '2', 'comapnyIntroduction');

-- ----------------------------
-- Table structure for fileupload
-- ----------------------------
DROP TABLE IF EXISTS `fileupload`;
CREATE TABLE `fileupload` (
  `fileId` int(50) NOT NULL AUTO_INCREMENT,
  `filePath` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`fileId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fileupload
-- ----------------------------

-- ----------------------------
-- Table structure for item
-- ----------------------------
DROP TABLE IF EXISTS `item`;
CREATE TABLE `item` (
  `itemId` int(50) NOT NULL AUTO_INCREMENT,
  `itemName` varchar(255) DEFAULT NULL,
  `companyId` int(50) DEFAULT NULL,
  `companyName` varchar(255) DEFAULT NULL,
  `currentHumanNumber` int(50) DEFAULT NULL,
  `itemDeadline` date DEFAULT NULL,
  `itemDetailed` varchar(5000) DEFAULT NULL,
  `itemMoney` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`itemId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of item
-- ----------------------------
INSERT INTO `item` VALUES ('1', '项目1', '1', 'company', '0', '2021-02-28', '完成本公司的任务', '100000');
INSERT INTO `item` VALUES ('2', '项目2', '1', 'company', '0', '2021-02-28', '完成本公司的需求。完成本公司的需求。完成本公司的需求。完成本公司的需求。完成本公司的需求。完成本公司的需求。完成本公司的需求。完成本公司的需求。完成本公司的需求。完成本公司的需求。完成本公司的需求。完成本公司的需求。完成本公司的需求。完成本公司的需求。完成本公司的需求。完成本公司的需求。完成本公司的需求。完成本公司的需求。完成本公司的需求。完成本公司的需求。完成本公司的需求。完成本公司的需求。完成本公司的需求。完成本公司的需求。完成本公司的需求。完成本公司的需求。完成本公司的需求。完成本公司的需求。完成本公司的需求。完成本公司的需求。完成本公司的需求。完成本公司的需求。完成本公司的需求。完成本公司的需求。完成本公司的需求。完成本公司的需求。完成本公司的需求。', '50000');
INSERT INTO `item` VALUES ('3', '项目3', '1', 'company', '0', '2021-03-14', ',true,true,true,true,true,true,true,true4184156174856181\r\n@东方闪电', '150000');

-- ----------------------------
-- Table structure for submit
-- ----------------------------
DROP TABLE IF EXISTS `submit`;
CREATE TABLE `submit` (
  `submitId` int(50) NOT NULL AUTO_INCREMENT,
  `itemId` int(50) DEFAULT NULL,
  `itemName` varchar(255) DEFAULT NULL,
  `submitDate` varchar(255) DEFAULT NULL,
  `fileId` int(50) DEFAULT NULL,
  `submitIntroduction` varchar(255) DEFAULT NULL,
  `userId` int(50) DEFAULT NULL,
  PRIMARY KEY (`submitId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of submit
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `userId` int(50) NOT NULL AUTO_INCREMENT,
  `account` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `identity` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'abc', '123asd', '3350861305@qq.com', '17366759223', 'True', '001', '');
INSERT INTO `user` VALUES ('2', '提供者', 'tgztgz', '3350861305@qq.com', '17366759223', 'True', '002', '');
