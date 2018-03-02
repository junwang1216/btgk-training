# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.7.13)
# Database: btgk-training
# Generation Time: 2018-01-31 14:32:11 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table org_attendance
# ------------------------------------------------------------

DROP TABLE IF EXISTS `org_attendance`;

CREATE TABLE `org_attendance` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `inDate` varchar(20) DEFAULT NULL COMMENT '签到时间',
  `inRole` int(11) DEFAULT NULL COMMENT '签到方 1：教练  3：学员',
  `inUserId` int(11) DEFAULT NULL COMMENT '签到人ID',
  `inClassID` int(11) DEFAULT NULL COMMENT '签到班级ID',
  `inScheduleId` int(11) DEFAULT NULL COMMENT '上课排期ID',
  `createTime` varchar(30) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table org_balance_settings
# ------------------------------------------------------------

DROP TABLE IF EXISTS `org_balance_settings`;

CREATE TABLE `org_balance_settings` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `balanceName` varchar(20) DEFAULT NULL COMMENT '收支项名称',
  `balanceType` int(11) DEFAULT NULL COMMENT '类型 1：收入 2：支出',
  `status` int(11) DEFAULT NULL COMMENT '状态 1：可用 2：不可用',
  `priority` int(11) DEFAULT NULL COMMENT '优先级1-n',
  `createTime` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `updateTime` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `org_balance_settings` WRITE;
/*!40000 ALTER TABLE `org_balance_settings` DISABLE KEYS */;

INSERT INTO `org_balance_settings` (`id`, `balanceName`, `balanceType`, `status`, `priority`, `createTime`, `updateTime`)
VALUES
	(2,'新生缴费',1,1,1,'2018-01-03 23:40:18','2018-01-03 23:40:18'),
	(3,'会员续费',1,1,2,'2018-01-03 23:40:18','2018-01-03 23:40:18'),
	(4,'学员退费',2,1,1,'2018-01-03 23:41:17','2018-01-03 23:41:17'),
	(5,'取消缴费',2,1,2,'2018-01-03 23:41:17','2018-01-03 23:41:17'),
	(6,'场地租赁',2,1,3,'2018-01-03 23:41:17','2018-01-03 23:41:17');

/*!40000 ALTER TABLE `org_balance_settings` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table org_class
# ------------------------------------------------------------

DROP TABLE IF EXISTS `org_class`;

CREATE TABLE `org_class` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `className` varchar(20) DEFAULT NULL COMMENT '班级名称',
  `classPrice` int(11) DEFAULT '0' COMMENT '班级价格',
  `classHours` int(11) DEFAULT '1' COMMENT '上课扣除课时',
  `venueId` int(11) DEFAULT NULL COMMENT '所属场馆',
  `courseId` int(11) DEFAULT NULL COMMENT '授课内容ID',
  `coachId` int(11) DEFAULT NULL COMMENT '执教教练ID',
  `status` int(11) DEFAULT NULL COMMENT '状态 1：报名中 2：上课中 3：已完结',
  `createTime` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `updateTime` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table org_class_schedule
# ------------------------------------------------------------

DROP TABLE IF EXISTS `org_class_schedule`;

CREATE TABLE `org_class_schedule` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `classId` int(11) DEFAULT NULL COMMENT '班级Id',
  `startTime` varchar(10) DEFAULT NULL COMMENT '开始时间',
  `endTime` varchar(10) DEFAULT NULL COMMENT '结束时间',
  `classDate` varchar(20) DEFAULT NULL COMMENT '上课日期',
  `startDate` varchar(20) DEFAULT NULL COMMENT '上课开始日期',
  `endDate` varchar(20) DEFAULT NULL COMMENT '上课结束日期',
  `classWeek` varchar(10) DEFAULT NULL COMMENT '上课星期',
  `coachId` int(11) DEFAULT NULL COMMENT '上课教练',
  `createTime` varchar(20) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table org_class_students
# ------------------------------------------------------------

DROP TABLE IF EXISTS `org_class_students`;

CREATE TABLE `org_class_students` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `classId` int(11) DEFAULT NULL COMMENT '班级ID',
  `studentId` int(11) DEFAULT NULL COMMENT '学生ID',
  `createTime` varchar(20) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table org_coaches
# ------------------------------------------------------------

DROP TABLE IF EXISTS `org_coaches`;

CREATE TABLE `org_coaches` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `realName` varchar(20) DEFAULT NULL COMMENT '真实姓名',
  `password` varchar(50) DEFAULT NULL COMMENT '密码',
  `mobile` varchar(11) DEFAULT NULL COMMENT '手机号码',
  `idCard` varchar(18) DEFAULT NULL COMMENT '身份证号',
  `email` varchar(30) DEFAULT NULL COMMENT '联系邮箱',
  `createTime` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `updateTime` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='机构教练';



# Dump of table org_coaches_roles
# ------------------------------------------------------------

DROP TABLE IF EXISTS `org_coaches_roles`;

CREATE TABLE `org_coaches_roles` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `coachId` int(11) DEFAULT NULL COMMENT '教练ID',
  `roleId` int(11) DEFAULT NULL COMMENT '角色ID',
  `createTime` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `updateTime` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table org_courses
# ------------------------------------------------------------

DROP TABLE IF EXISTS `org_courses`;

CREATE TABLE `org_courses` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `courseName` varchar(20) DEFAULT NULL COMMENT '授课名称',
  `sportId` int(11) DEFAULT NULL COMMENT '授课项目ID',
  `courseNote` varchar(500) DEFAULT NULL COMMENT '授课内容简介',
  `status` int(11) DEFAULT NULL COMMENT '可授课状态 1：可授课 2：不可授课',
  `createTime` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `updateTime` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='机构授课内容';



# Dump of table org_information
# ------------------------------------------------------------

DROP TABLE IF EXISTS `org_information`;

CREATE TABLE `org_information` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `orgName` varchar(30) DEFAULT NULL COMMENT '组织名称',
  `province` varchar(20) DEFAULT '请选择' COMMENT '所在省份',
  `city` varchar(20) DEFAULT '请选择' COMMENT '所在城市',
  `district` varchar(20) DEFAULT '请选择' COMMENT '所在区县',
  `address` varchar(50) DEFAULT NULL COMMENT '详细地址',
  `contactName` varchar(10) DEFAULT NULL COMMENT '联系人姓名',
  `contactPhone` varchar(22) DEFAULT NULL COMMENT '联系人座机号',
  `contactMobile` varchar(11) DEFAULT NULL COMMENT '联系人手机号',
  `contactEmail` varchar(20) DEFAULT NULL COMMENT '联系人邮箱',
  `orgNote` varchar(200) DEFAULT NULL COMMENT '组织简介',
  `createTime` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `updateTime` varchar(20) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='机构资料';

LOCK TABLES `org_information` WRITE;
/*!40000 ALTER TABLE `org_information` DISABLE KEYS */;

INSERT INTO `org_information` (`id`, `orgName`, `province`, `city`, `district`, `address`, `contactName`, `contactPhone`, `contactMobile`, `contactEmail`, `orgNote`, `createTime`, `updateTime`)
VALUES
	(1,'科技有限公司','北京','北京市','大兴区','','张老师','','15019087287','','','2017-12-28 23:39:56','2018-01-11 14:11:27');

/*!40000 ALTER TABLE `org_information` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table org_operator
# ------------------------------------------------------------

DROP TABLE IF EXISTS `org_operator`;

CREATE TABLE `org_operator` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `orgId` int(11) DEFAULT NULL COMMENT '机构ID',
  `roleId` int(11) DEFAULT NULL COMMENT '用户权限',
  `userNo` varchar(32) DEFAULT NULL COMMENT '用户编号',
  `userName` varchar(32) NOT NULL DEFAULT '' COMMENT '登录帐号',
  `realName` varchar(32) DEFAULT NULL COMMENT '真实姓名',
  `password` varchar(50) DEFAULT NULL COMMENT '登录密码',
  `mobile` varchar(16) DEFAULT NULL COMMENT '手机号',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `idCard` varchar(18) DEFAULT NULL COMMENT '身份证号',
  `birthday` varchar(10) DEFAULT NULL COMMENT '生日',
  `sex` int(11) DEFAULT NULL COMMENT '性别 1：男 2：女',
  `address` varchar(512) DEFAULT NULL COMMENT '用户联系地址',
  `lastLoginTime` varchar(20) DEFAULT NULL COMMENT '最后一次登录时间',
  `status` int(11) DEFAULT '1' COMMENT '状态 1：有效   2：锁定',
  `createTime` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `updateTime` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `operatorId_Unique` (`userName`),
  KEY `operatorId_Normal` (`userName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='登录用户表';

LOCK TABLES `org_operator` WRITE;
/*!40000 ALTER TABLE `org_operator` DISABLE KEYS */;

INSERT INTO `org_operator` (`id`, `orgId`, `roleId`, `userNo`, `userName`, `realName`, `password`, `mobile`, `email`, `idCard`, `birthday`, `sex`, `address`, `lastLoginTime`, `status`, `createTime`, `updateTime`)
VALUES
	(27,1,1,'admin20171228','superadmin','栾宝石','LSiFVHC0sHbUiwfeC5MNM1F3R064o3Wm+2wAN61iiAA=','15010068723','1532404212@qq.com','370782198804011324','1988-04-01',1,NULL,'2018-01-31 22:18:41',1,'2017-12-28 16:02:32','2017-12-28 16:02:32');

/*!40000 ALTER TABLE `org_operator` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table org_roles
# ------------------------------------------------------------

DROP TABLE IF EXISTS `org_roles`;

CREATE TABLE `org_roles` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `roleName` varchar(20) DEFAULT NULL COMMENT '角色名称',
  `roleNote` varchar(20) DEFAULT NULL COMMENT '角色描述',
  `status` int(11) DEFAULT NULL COMMENT '角色状态 1：可用 2：不可用',
  `createTime` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `updateTime` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='机构人员角色';



# Dump of table org_skills_paper
# ------------------------------------------------------------

DROP TABLE IF EXISTS `org_skills_paper`;

CREATE TABLE `org_skills_paper` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `paperTitle` varchar(20) DEFAULT NULL COMMENT '问卷标题',
  `paperDesc` varchar(100) DEFAULT NULL COMMENT '问卷描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='技能问卷';



# Dump of table org_skills_score
# ------------------------------------------------------------

DROP TABLE IF EXISTS `org_skills_score`;

CREATE TABLE `org_skills_score` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='技能问卷分数';



# Dump of table org_sports
# ------------------------------------------------------------

DROP TABLE IF EXISTS `org_sports`;

CREATE TABLE `org_sports` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `orgId` int(11) DEFAULT NULL COMMENT '机构编号',
  `sportName` varchar(10) DEFAULT NULL COMMENT '授课项目名称',
  `sportNameEn` varchar(20) DEFAULT NULL COMMENT '英文名称',
  `sportIcon` varchar(20) DEFAULT NULL COMMENT '授课项目图标',
  `status` int(11) DEFAULT NULL COMMENT '是否支持 1：支持 2：不支持',
  `createTime` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `updateTime` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='授课项目';

LOCK TABLES `org_sports` WRITE;
/*!40000 ALTER TABLE `org_sports` DISABLE KEYS */;

INSERT INTO `org_sports` (`id`, `orgId`, `sportName`, `sportNameEn`, `sportIcon`, `status`, `createTime`, `updateTime`)
VALUES
	(1,1,'篮球','basketball','icon-lanqiu',1,'2017-12-30 14:19:32','2017-12-30 14:19:32'),
	(2,1,'足球','football','icon-zuqiu',1,'2017-12-30 14:19:32','2017-12-30 14:19:32'),
	(3,1,'游泳','swimming','icon-youyong',1,'2017-12-30 14:19:32','2017-12-30 14:19:32'),
	(4,1,'乒乓球','ping pong','icon-pingpangqiu',1,'2017-12-30 14:19:32','2017-12-30 14:19:32'),
	(5,1,'网球','tennis','icon-wangqiu',1,'2017-12-30 14:19:32','2017-12-30 14:19:32'),
	(6,1,'羽毛球','badminton','icon-yumaoqiu',1,'2017-12-30 14:19:32','2017-12-30 14:19:32'),
	(7,1,'排球','volleyball','icon-paiqiu',1,'2017-12-30 14:19:32','2017-12-30 14:19:32'),
	(8,1,'高尔夫球','golf','icon-gaoerfu',1,'2017-12-30 14:19:32','2017-12-30 14:19:32'),
	(9,1,'滑雪','skiing','icon-huaxue',1,'2017-12-30 14:19:32','2017-12-30 14:19:32'),
	(10,1,'棒球','baseball','icon-bangqiu',1,'2017-12-30 14:19:32','2017-12-30 14:19:32');

/*!40000 ALTER TABLE `org_sports` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table org_sports_coaches
# ------------------------------------------------------------

DROP TABLE IF EXISTS `org_sports_coaches`;

CREATE TABLE `org_sports_coaches` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `sportId` int(11) DEFAULT NULL COMMENT '授课项目ID',
  `coachId` int(11) DEFAULT NULL COMMENT '教练ID',
  `createTime` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `updateTime` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='教练关联授课项目';



# Dump of table org_sports_skills
# ------------------------------------------------------------

DROP TABLE IF EXISTS `org_sports_skills`;

CREATE TABLE `org_sports_skills` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `skillName` varchar(20) DEFAULT NULL COMMENT '技能名称',
  `sportId` int(11) NOT NULL COMMENT '授课项目ID',
  `skillNote` varchar(200) DEFAULT NULL COMMENT '技能描述',
  `maxValue` int(11) DEFAULT NULL COMMENT '最高值 10或者100',
  `status` int(11) DEFAULT '1' COMMENT '是否显示 1：显示 2：显示',
  `createTime` varchar(30) DEFAULT NULL COMMENT '创建时间',
  `updateTime` varchar(30) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='授课项目关联测试技能';



# Dump of table org_students
# ------------------------------------------------------------

DROP TABLE IF EXISTS `org_students`;

CREATE TABLE `org_students` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `realName` varchar(20) DEFAULT NULL COMMENT '真实姓名',
  `mobile` varchar(11) DEFAULT NULL COMMENT '手机号码',
  `password` varchar(50) DEFAULT NULL COMMENT '用户密码',
  `idCard` varchar(18) DEFAULT NULL COMMENT '身份证号',
  `birthday` varchar(10) DEFAULT NULL COMMENT '出生日期',
  `sex` int(11) DEFAULT NULL COMMENT '性别 1：男 2：女',
  `height` int(11) DEFAULT NULL COMMENT '身高（cm）',
  `weight` int(11) DEFAULT NULL COMMENT '体重（kg）',
  `lastLoginTime` varchar(20) DEFAULT NULL COMMENT '最后一次登录',
  `createTime` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `updateTime` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table org_system_log
# ------------------------------------------------------------

DROP TABLE IF EXISTS `org_system_log`;

CREATE TABLE `org_system_log` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `logNo` varchar(50) DEFAULT NULL COMMENT '日志编号',
  `logType` int(11) DEFAULT NULL COMMENT '日志类型',
  `logContent` varchar(500) DEFAULT NULL COMMENT '日志类型',
  `ip` varchar(20) DEFAULT NULL COMMENT 'IP地址',
  `mac` varchar(20) DEFAULT NULL COMMENT 'MAC地址',
  `createTIme` varchar(20) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table org_venue_coaches
# ------------------------------------------------------------

DROP TABLE IF EXISTS `org_venue_coaches`;

CREATE TABLE `org_venue_coaches` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `venueId` int(11) DEFAULT NULL COMMENT '场馆ID',
  `coachId` int(11) DEFAULT NULL COMMENT '机构教练ID',
  `status` int(11) DEFAULT NULL COMMENT '是否离职 1：没离职 2：离职',
  `createTime` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `updateTime` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='机构教练关联场馆';

LOCK TABLES `org_venue_coaches` WRITE;
/*!40000 ALTER TABLE `org_venue_coaches` DISABLE KEYS */;

INSERT INTO `org_venue_coaches` (`id`, `venueId`, `coachId`, `status`, `createTime`, `updateTime`)
VALUES
	(15,2,2,NULL,'2018-01-13 12:31:05','2018-01-13 12:31:05'),
	(17,1,1,NULL,'2018-01-13 12:44:59','2018-01-13 12:44:59'),
	(18,1,3,NULL,'2018-01-13 12:45:13','2018-01-13 12:45:13'),
	(19,2,3,NULL,'2018-01-13 12:45:13','2018-01-13 12:45:13'),
	(20,1,4,NULL,'2018-01-13 12:46:21','2018-01-13 12:46:21');

/*!40000 ALTER TABLE `org_venue_coaches` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table org_venues
# ------------------------------------------------------------

DROP TABLE IF EXISTS `org_venues`;

CREATE TABLE `org_venues` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `orgId` int(11) DEFAULT NULL COMMENT '所属机构',
  `venueName` varchar(20) DEFAULT NULL COMMENT '场馆名称',
  `province` varchar(20) DEFAULT '请选择' COMMENT '所在省份',
  `city` varchar(20) DEFAULT '请选择' COMMENT '所在城市',
  `district` varchar(20) DEFAULT '请选择' COMMENT '所在区县',
  `address` varchar(20) DEFAULT NULL COMMENT '场馆地址',
  `contactName` varchar(20) DEFAULT NULL COMMENT '场馆联系人',
  `contactPhone` varchar(30) DEFAULT NULL COMMENT '座机号',
  `contactMobile` varchar(11) DEFAULT NULL COMMENT '手机号',
  `contactEmail` varchar(30) DEFAULT NULL COMMENT '联系邮箱',
  `createTime` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `updateTime` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='机构场馆信息';

LOCK TABLES `org_venues` WRITE;
/*!40000 ALTER TABLE `org_venues` DISABLE KEYS */;

INSERT INTO `org_venues` (`id`, `orgId`, `venueName`, `province`, `city`, `district`, `address`, `contactName`, `contactPhone`, `contactMobile`, `contactEmail`, `createTime`, `updateTime`)
VALUES
	(1,1,'观音桥健身馆','北京','北京市','东城区','观音桥健身馆','宋江','','15801303167','','2017-12-30 12:50:49','2017-12-30 12:50:49'),
	(2,1,'团结湖体育馆','北京','北京市','东城区','团结湖体育馆','吴用','','15801303166','','2018-01-11 14:52:20','2018-01-30 00:24:33');

/*!40000 ALTER TABLE `org_venues` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
