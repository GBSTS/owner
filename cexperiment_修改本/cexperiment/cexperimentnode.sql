/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : kcbpfdb

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2020-01-13 10:24:31
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `cexperimentnode`
-- ----------------------------
DROP TABLE IF EXISTS `cexperimentnode`;
CREATE TABLE `cexperimentnode` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cename` varchar(150) NOT NULL COMMENT '实验标题',
  `classid` int(11) DEFAULT NULL COMMENT '课程班编号',
  `num` smallint(6) DEFAULT NULL COMMENT '序号顺序',
  `code` text COMMENT '参考源代码',
  `pictures` varchar(200) DEFAULT NULL COMMENT '相关图片',
  `video` varchar(100) DEFAULT NULL COMMENT '相关视频',
  `annex` varchar(100) DEFAULT NULL COMMENT '相关附件',
  `intro` text COMMENT '实验介绍',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cexperimentnode
-- ----------------------------
