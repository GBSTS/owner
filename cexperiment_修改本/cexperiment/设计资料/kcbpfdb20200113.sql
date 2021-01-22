/*
SQLyog Trial v11.01 (32 bit)
MySQL - 5.7.20-log : Database - kcbpfdb
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Table structure for table `cexiperimentsteps` */

DROP TABLE IF EXISTS `cexiperimentsteps`;

CREATE TABLE `cexiperimentsteps` (
  `id` int(11) NOT NULL,
  `num` int(11) DEFAULT NULL COMMENT '序号',
  `intro` text COMMENT '步骤说明',
  `value` decimal(8,4) DEFAULT NULL COMMENT '权值',
  `ceid` int(11) DEFAULT NULL COMMENT '实验ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Table structure for table `cexperimentnode` */

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

/*Table structure for table `kchapter` */

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

/*Table structure for table `kclass` */

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

/*Table structure for table `kclasspro` */

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

/*Table structure for table `kclastu` */

DROP TABLE IF EXISTS `kclastu`;

CREATE TABLE `kclastu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stuno` varchar(20) DEFAULT NULL COMMENT ' 学号',
  `classid` int(11) DEFAULT NULL COMMENT '课程班ID',
  `kstate` int(11) DEFAULT NULL COMMENT '状态，0录入 1审核 2完成 7退出',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='班级学生对应表';

/*Table structure for table `kcourse` */

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

/*Table structure for table `kstudent` */

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

/*Table structure for table `kstuser` */

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

/*Table structure for table `kteacher` */

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

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
