/*
Navicat MySQL Data Transfer

Source Server         : 本地数据库
Source Server Version : 50732
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50732
File Encoding         : 65001

Date: 2020-11-28 13:25:52
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for base_admin_permission
-- ----------------------------
DROP TABLE IF EXISTS `base_admin_permission`;
CREATE TABLE `base_admin_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) DEFAULT NULL COMMENT '菜单名称',
  `pid` int(11) DEFAULT NULL COMMENT '父菜单id',
  `descpt` varchar(50) DEFAULT NULL COMMENT '描述',
  `url` varchar(100) DEFAULT NULL COMMENT '菜单url',
  `create_time` varchar(64) DEFAULT NULL COMMENT '添加时间',
  `update_time` varchar(64) DEFAULT NULL COMMENT '更新时间',
  `del_flag` int(1) DEFAULT NULL COMMENT '删除标志（0:删除 1：存在）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of base_admin_permission
-- ----------------------------
INSERT INTO `base_admin_permission` VALUES ('1', '系统管理', '0', '系统管理', '', '2018-11-30 10:27:34', '2018-11-30 10:27:34', '1');
INSERT INTO `base_admin_permission` VALUES ('2', '账号管理', '1', '账号管理', '/user/userManage', '2018-11-30 11:44:41', '2018-11-30 11:56:34', '1');
INSERT INTO `base_admin_permission` VALUES ('3', '角色管理', '1', '角色管理', '/role/roleManage', '2018-11-30 11:45:27', '2018-11-30 11:45:27', '1');
INSERT INTO `base_admin_permission` VALUES ('7', '权限管理', '1', '权限管理', '/permission/permissionManage', '2018-11-30 11:48:35', '2018-11-30 15:13:38', '1');
INSERT INTO `base_admin_permission` VALUES ('9', '基本设置', '0', '基本设置', '', '2018-11-30 12:10:32', '2018-11-30 12:10:32', '1');
INSERT INTO `base_admin_permission` VALUES ('10', '服务类目管理', '9', '服务类目管理', '/goodsCategory/goodsCategoryManage', '2018-12-04 11:47:07', '2018-12-04 11:47:07', '1');
INSERT INTO `base_admin_permission` VALUES ('11', '服务类型管理', '9', '服务类型管理', '/serviceType/serviceTypeManage', '2018-12-04 11:47:59', '2018-12-04 11:47:59', '1');
INSERT INTO `base_admin_permission` VALUES ('12', '支付方式', '9', '支付方式', '/payplatform/payplatManage', '2018-12-04 11:48:44', '2018-12-04 11:48:44', '1');
INSERT INTO `base_admin_permission` VALUES ('13', '银行管理', '9', '银行管理', '/bank/bankManage', '2018-12-04 11:49:13', '2018-12-04 11:49:13', '1');
INSERT INTO `base_admin_permission` VALUES ('14', '省市区管理', '9', '省市区管理', '/position/positionManage', '2018-12-04 11:49:36', '2018-12-04 11:50:02', '1');

-- ----------------------------
-- Table structure for base_admin_role
-- ----------------------------
DROP TABLE IF EXISTS `base_admin_role`;
CREATE TABLE `base_admin_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '权限角色ID',
  `role_name` varchar(30) NOT NULL COMMENT '角色名称',
  `role_desc` varchar(100) DEFAULT NULL COMMENT '角色描述',
  `permissions` varchar(20) DEFAULT NULL COMMENT '权限',
  `create_time` varchar(64) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(64) DEFAULT NULL COMMENT '更新时间',
  `role_status` int(1) NOT NULL DEFAULT '1' COMMENT '1：有效 \r\n            0：无效',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='系统用户角色表';

-- ----------------------------
-- Records of base_admin_role
-- ----------------------------
INSERT INTO `base_admin_role` VALUES ('1', '系统管理员', '系统管理员', '1,9', '2018-11-21 15:54:07', '2018-11-21 15:54:07', '1');
INSERT INTO `base_admin_role` VALUES ('2', '普通管理员', '普通管理员', '9', '2018-11-21 15:11:44', '2018-12-03 19:09:57', '1');

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
