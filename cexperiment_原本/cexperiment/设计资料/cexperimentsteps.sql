/*
Navicat MySQL Data Transfer

Source Server         : ay_user
Source Server Version : 50719
Source Host           : localhost:3306
Source Database       : mysql

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2020-01-13 11:54:13
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for cexiperimentsteps
-- ----------------------------
DROP TABLE IF EXISTS `cexperimentsteps`;
CREATE TABLE `cexperimentsteps` (
  `id` int(11) NOT NULL,
  `num` int(11) DEFAULT NULL,
  `intro` text,
  `value` int(11) DEFAULT NULL,
  `ceid` char(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
