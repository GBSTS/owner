/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : kcbpfdb

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2020-02-11 11:24:17
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cexperimentnode
-- ----------------------------
INSERT INTO `cexperimentnode` VALUES ('1', '实验2 DIV+CSS+JS设计网页实验', '1', '2', null, null, null, null, '<p><strong><span style=\'font-family: 黑体;font-size: 16px\'><span style=\'font-family:黑体\'>实验目的：</span></span></strong></p><p style=\'margin-left: 56px;line-height: 22px\'> <span style=\'font-family: 宋体;font-size: 16px\'>1、&nbsp;</span><span style=\'font-family: 宋体;font-size: 16px\'><span style=\'font-family:宋体\'>学习掌握</span></span><span style=\'font-family: 宋体;font-size: 16px\'>Dreamweaver CS6网页设计工具</span><span style=\'font-family: 宋体;font-size: 16px\'><span style=\'font-family:宋体\'>；</span></span></p><p style=\'margin-left: 56px;line-height: 22px\'> <span style=\'font-family: 宋体;font-size: 16px\'>2、&nbsp;</span><span style=\'font-family: 宋体;font-size: 16px\'><span style=\'font-family:宋体\'>学习掌握</span></span><span style=\'font-family: 宋体;font-size: 16px\'>DIV+CSS+JS网页的代码级结构</span><span style=\'font-family: 宋体;font-size: 16px\'><span style=\'font-family:宋体\'>；</span></span></p><p style=\'margin-left: 56px;line-height: 22px\'>    <span style=\'font-family: 宋体;font-size: 16px\'>3、&nbsp;</span><span style=\'font-family: 宋体;font-size: 16px\'><span style=\'font-family:宋体\'>学习掌握</span></span><span style=\'font-family: 宋体;font-size: 16px\'><span style=\'font-family:宋体\'>并灵活使用</span>CSS设计网页；</span></p><p style=\'margin-left: 56px;line-height: 22px\'>    <span style=\'font-family: 宋体;font-size: 16px\'>4、&nbsp;</span><span style=\'font-family: 宋体;font-size: 16px\'><span style=\'font-family:宋体\'>学习掌握</span>JavaScript之事件调用</span><span style=\'font-family: 宋体;font-size: 16px\'><span style=\'font-family:宋体\'>。</span></span></p><p style=\'margin-left: 32px;line-height: 22px\'>    <span style=\'font-family: 宋体;font-size: 16px\'>&nbsp;</span></p><p>    <strong><span style=\'font-family: 黑体;font-size: 16px\'><span style=\'font-family:黑体\'>实验要求：</span></span></strong></p><p style=\'margin-left: 56px;line-height: 22px\'>    <span style=\'font-family: 宋体;font-size: 16px\'>1、&nbsp;</span><span style=\'font-family: 宋体;font-size: 16px\'><span style=\'font-family:宋体\'>能独立完成</span></span><span style=\'font-family: 宋体;font-size: 16px\'><span style=\'font-family:宋体\'>使用</span>Dreamweaver CS设计网页（DIV+CSS+JS）</span><span style=\'font-family: 宋体;font-size: 16px\'><span style=\'font-family:宋体\'>；</span></span></p><p style=\'margin-left: 56px;line-height: 22px\'>    <span style=\'font-family: 宋体;font-size: 16px\'>2、&nbsp;</span><span style=\'font-family: 宋体;font-size: 16px\'><span style=\'font-family:宋体\'>能独立完成</span></span><span style=\'font-family: 宋体;font-size: 16px\'><span style=\'font-family:宋体\'>网页文件的</span>CSS格式设计；</span></p><p style=\'margin-left: 56px;line-height: 22px\'>    <span style=\'font-family: 宋体;font-size: 16px\'>3、&nbsp;</span><span style=\'font-family: 宋体;font-size: 16px\'><span style=\'font-family:宋体\'>能独立完成对</span>JS脚本事件的处理</span><span style=\'font-family: 宋体;font-size: 16px\'><span style=\'font-family:宋体\'>。</span></span></p><p>    <br/></p>');

-- ----------------------------
-- Table structure for `cexperimentops`
-- ----------------------------
DROP TABLE IF EXISTS `cexperimentops`;
CREATE TABLE `cexperimentops` (
  `id` int(11) NOT NULL,
  `stuid` int(11) DEFAULT NULL,
  `cesid` int(11) DEFAULT NULL,
  `startdate` datetime DEFAULT NULL,
  `updatedate` datetime DEFAULT NULL,
  `pictures` varchar(200) DEFAULT NULL,
  `explain` text,
  `number` smallint(6) DEFAULT NULL,
  `score` int(11) DEFAULT NULL,
  `enddate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cexperimentops
-- ----------------------------

-- ----------------------------
-- Table structure for `cexperimentsteps`
-- ----------------------------
DROP TABLE IF EXISTS `cexperimentsteps`;
CREATE TABLE `cexperimentsteps` (
  `id` int(11) NOT NULL,
  `num` int(11) DEFAULT NULL COMMENT '序号',
  `intro` text COMMENT '步骤说明',
  `value` decimal(8,4) DEFAULT NULL COMMENT '权值',
  `ceid` int(11) DEFAULT NULL COMMENT '实验ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of cexperimentsteps
-- ----------------------------
INSERT INTO `cexperimentsteps` VALUES ('1', '1', '在实验室计算机或个人电脑中，打开Dreamweaver CS6软件，截图；', '0.1000', '1');
INSERT INTO `cexperimentsteps` VALUES ('2', '2', '在Dreamweaver CS6中打实验01完成的文件夹，使之成为本软件内的站点文件夹，截图；', '0.1000', '1');
INSERT INTO `cexperimentsteps` VALUES ('3', '3', '打开实验01的index.html文件，查看文件内容以及其与css和js文件的关系，找一个你觉得最能展现效果的界面，截图。', '0.2000', '1');

-- ----------------------------
-- Table structure for `kchapter`
-- ----------------------------
DROP TABLE IF EXISTS `kchapter`;
CREATE TABLE `kchapter` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cid` int(11) DEFAULT NULL COMMENT '课程ID',
  `chanum` int(11) DEFAULT NULL COMMENT '章节序号',
  `chatitle` varchar(50) DEFAULT NULL COMMENT '章节标题',
  `cretime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `stucount` int(11) DEFAULT NULL COMMENT '学生人数',
  `kstate` int(11) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='章节表';

-- ----------------------------
-- Records of kchapter
-- ----------------------------

-- ----------------------------
-- Table structure for `kclass`
-- ----------------------------
DROP TABLE IF EXISTS `kclass`;
CREATE TABLE `kclass` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `kclassid` varchar(20) DEFAULT NULL COMMENT ' 班级代号',
  `kclassname` varchar(50) DEFAULT NULL COMMENT ' 班级名称',
  `kclassnp` varchar(32) DEFAULT NULL COMMENT '班级人数',
  `startterm` varchar(50) DEFAULT NULL COMMENT '起始学期',
  `endterm` varchar(50) DEFAULT NULL COMMENT '结束学期',
  `studeputy` varchar(50) DEFAULT NULL COMMENT '学生代理',
  `kctype` varchar(20) DEFAULT NULL COMMENT '班级类型',
  `crepeaple` varchar(20) DEFAULT NULL COMMENT '创建人',
  `cretime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT ' 创建时间',
  `kstate` int(11) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT=' 班级表';

-- ----------------------------
-- Records of kclass
-- ----------------------------

-- ----------------------------
-- Table structure for `kclasspro`
-- ----------------------------
DROP TABLE IF EXISTS `kclasspro`;
CREATE TABLE `kclasspro` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stuid` int(11) DEFAULT NULL COMMENT '学生帐号ID',
  `stuno` varchar(20) DEFAULT NULL COMMENT ' 学号',
  `classid` int(11) DEFAULT NULL COMMENT '课程班ID',
  `chapterid` int(11) DEFAULT NULL COMMENT '课程序号',
  `cretime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT ' 首次签到时间',
  `continuetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT ' 继续签到时间',
  `integral` int(11) DEFAULT NULL COMMENT '积分',
  `kstate` int(11) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='上课表';

-- ----------------------------
-- Records of kclasspro
-- ----------------------------

-- ----------------------------
-- Table structure for `kclastu`
-- ----------------------------
DROP TABLE IF EXISTS `kclastu`;
CREATE TABLE `kclastu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stuno` varchar(20) DEFAULT NULL COMMENT ' 学号',
  `classid` int(11) DEFAULT NULL COMMENT '课程班ID',
  `kstate` int(11) DEFAULT NULL COMMENT '状态，0录入 1审核 2完成 7退出',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='班级学生对应表';

-- ----------------------------
-- Records of kclastu
-- ----------------------------

-- ----------------------------
-- Table structure for `kcourse`
-- ----------------------------
DROP TABLE IF EXISTS `kcourse`;
CREATE TABLE `kcourse` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `courseid` varchar(20) DEFAULT NULL COMMENT ' 课程代号',
  `coursename` varchar(50) DEFAULT NULL COMMENT ' 课程名称',
  `crepeaple` varchar(20) DEFAULT NULL COMMENT '创建人',
  `cretime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT ' 创建时间',
  `kstate` int(11) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='课程表';

-- ----------------------------
-- Records of kcourse
-- ----------------------------

-- ----------------------------
-- Table structure for `kstudent`
-- ----------------------------
DROP TABLE IF EXISTS `kstudent`;
CREATE TABLE `kstudent` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stuno` varchar(20) DEFAULT NULL COMMENT ' 学号',
  `stuname` varchar(50) DEFAULT NULL COMMENT ' 姓名',
  `kclass` varchar(50) DEFAULT NULL COMMENT '班级',
  `department` varchar(100) DEFAULT NULL COMMENT '院系',
  `school` varchar(100) DEFAULT NULL COMMENT '学校',
  `cremode` varchar(100) DEFAULT NULL COMMENT '创建方式',
  `cretime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT ' 时间',
  `kstate` int(11) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT=' 学生表';

-- ----------------------------
-- Records of kstudent
-- ----------------------------

-- ----------------------------
-- Table structure for `kstuser`
-- ----------------------------
DROP TABLE IF EXISTS `kstuser`;
CREATE TABLE `kstuser` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stuid` int(11) DEFAULT NULL COMMENT ' 学生编号',
  `kusername` varchar(50) DEFAULT NULL COMMENT ' 用户名',
  `kpassword` varchar(32) DEFAULT NULL COMMENT '密码',
  `telephone1` varchar(50) DEFAULT NULL COMMENT '手机1',
  `telephone2` varchar(50) DEFAULT NULL COMMENT '手机2',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `etverify` varchar(50) DEFAULT NULL COMMENT '验证码',
  `kcban` int(11) DEFAULT NULL COMMENT '当前课程班',
  `regmode` varchar(100) DEFAULT NULL COMMENT '注册方式',
  `regtime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT ' 注册时间',
  `kstate` int(11) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT=' 学生帐号表';

-- ----------------------------
-- Records of kstuser
-- ----------------------------

-- ----------------------------
-- Table structure for `kteacher`
-- ----------------------------
DROP TABLE IF EXISTS `kteacher`;
CREATE TABLE `kteacher` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `kteacherid` varchar(20) DEFAULT NULL COMMENT ' 教师代号',
  `kteachername` varchar(50) DEFAULT NULL COMMENT ' 教师名称',
  `ktdegree` varchar(50) DEFAULT NULL COMMENT '教师学位',
  `ktpost` varchar(50) DEFAULT NULL COMMENT '教师职务',
  `kttitle` varchar(50) DEFAULT NULL COMMENT '教师职称',
  `ktworkunit` varchar(50) DEFAULT NULL COMMENT '工作单位',
  `salary` varchar(20) DEFAULT NULL COMMENT '薪水',
  `birthday` datetime DEFAULT NULL COMMENT '出生年月日',
  `telephone1` varchar(50) DEFAULT NULL COMMENT '手机1',
  `telephone2` varchar(50) DEFAULT NULL COMMENT '手机2',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `etverify` varchar(50) DEFAULT NULL COMMENT '验证码',
  `regtime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT ' 创建时间',
  `kstate` int(11) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT=' 班级表';

-- ----------------------------
-- Records of kteacher
-- ----------------------------
