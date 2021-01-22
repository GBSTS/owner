/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : kcbpfdb

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2020-01-15 15:16:44
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `cexiperimentsteps`
-- ----------------------------
DROP TABLE IF EXISTS `cexiperimentsteps`;
CREATE TABLE `cexiperimentsteps` (
  `id` int(11) NOT NULL,
  `num` int(11) DEFAULT NULL COMMENT '序号',
  `intro` text COMMENT '步骤说明',
  `value` decimal(8,4) DEFAULT NULL COMMENT '权值',
  `ceid` int(11) DEFAULT NULL COMMENT '实验ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of cexiperimentsteps
-- ----------------------------
INSERT INTO `cexiperimentsteps` VALUES ('1', '1', '在实验室计算机或个人电脑中，打开Dreamweaver CS6软件，截图；', '0.1000', '1');
INSERT INTO `cexiperimentsteps` VALUES ('2', '2', '在Dreamweaver CS6中打实验01完成的文件夹，使之成为本软件内的站点文件夹，截图；', '0.1000', '1');
INSERT INTO `cexiperimentsteps` VALUES ('3', '3', '3、打开实验01的index.html文件，查看文件内容以及其与css和js文件的关系，找一个你觉得最能展现效果的界面，截图。', '0.2000', '1');
