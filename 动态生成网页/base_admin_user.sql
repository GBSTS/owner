/*
Navicat MySQL Data Transfer

Source Server         : 本地数据库
Source Server Version : 50732
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50732
File Encoding         : 65001

Date: 2020-11-27 01:36:05
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for base_admin_user
-- ----------------------------
DROP TABLE IF EXISTS `base_admin_user`;
CREATE TABLE `base_admin_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `sys_user_name` varchar(50) NOT NULL COMMENT '系统用户名称',
  `sys_user_pwd` varchar(250) DEFAULT NULL COMMENT '系统用户密码',
  `role_id` int(255) DEFAULT NULL COMMENT '角色',
  `user_phone` varchar(11) NOT NULL COMMENT '手机号',
  `reg_time` varchar(32) NOT NULL COMMENT '登记时间',
  `user_status` int(1) NOT NULL DEFAULT '0' COMMENT '状态（0：无效；1：有效）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='系统管理员帐号';

-- ----------------------------
-- Records of base_admin_user
-- ----------------------------
INSERT INTO `base_admin_user` VALUES ('1', 'admin', '3ef7164d1f6167cb9f2658c07d3c2f0a', '1', '13411182215', '2018-11-22 10:57:33', '1');
INSERT INTO `base_admin_user` VALUES ('2', 'jackson', 'e11d3c3371bb30ea6115564711c67918', '2', '19563648695', '2018-11-22 10:57:33', '1');
INSERT INTO `base_admin_user` VALUES ('4', 'alice', '636c1f81d87d7e221bc5674b4a9373b9', '2', '11111111111', '2018-11-22 11:01:58', '0');
INSERT INTO `base_admin_user` VALUES ('6', 'tom', 'e0fafeacb989b545d1fbe25f90348db0', '2', '17366759223', '2020-11-26 11:06:33', '1');
