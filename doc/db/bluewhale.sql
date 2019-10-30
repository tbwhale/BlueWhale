/*
SQLyog Ultimate v11.13 (64 bit)
MySQL - 5.0.96-community-nt : Database - bluewhale
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`bluewhale` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `bluewhale`;

/*Table structure for table `daily` */

DROP TABLE IF EXISTS `daily`;

CREATE TABLE `daily` (
  `ID` char(10) NOT NULL COMMENT '主键',
  `PERSON_CONTRACT` char(10) default NULL COMMENT '员工ID',
  `TASK_SOURCE` char(10) default NULL COMMENT '任务来源',
  `TASK_NAME` char(10) default NULL COMMENT '任务名称',
  `DESCRIPTION` char(255) default NULL COMMENT '描述',
  `TASK_STATUS` char(10) default NULL COMMENT '任务状态',
  `WORK_HOUR` char(10) default NULL COMMENT '工时',
  `DEMAND_BUG` char(10) default NULL COMMENT '需求号或BUG号',
  `BUG_CLASSIFICATION` char(10) default NULL COMMENT 'BUG分类',
  `START_WORK_DATE` char(10) default NULL COMMENT '上班时间',
  `AFTER_WORK_DATE` char(10) default NULL COMMENT '下班时间',
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `daily` */

/*Table structure for table `person` */

DROP TABLE IF EXISTS `person`;

CREATE TABLE `person` (
  `ID` char(10) NOT NULL COMMENT '主键',
  `CONTRACT` char(10) default NULL COMMENT '合同号',
  `NAME` char(50) default NULL COMMENT '姓名',
  `PHONE` char(50) default NULL COMMENT '电话',
  `DEPARTMENT` char(50) default NULL COMMENT '部门',
  `PROTEAM_CODE` char(10) default NULL COMMENT '项目组代码',
  `EDUCATION` char(10) default NULL COMMENT '学历',
  `SCHOOL` char(50) default NULL COMMENT '学校名称',
  `URGENT_CONTACT_PHONE` char(10) default NULL COMMENT '紧急联系电话',
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `person` */

/*Table structure for table `plan` */

DROP TABLE IF EXISTS `plan`;

CREATE TABLE `plan` (
  `ID` char(10) NOT NULL COMMENT '主键',
  `PERSON_CONTRACT` char(10) default NULL COMMENT '负责人ID(员工ID)',
  `DEMAND_TASK` char(10) default NULL COMMENT '需求/任务编号',
  `TASK_TYPE_ID` char(10) default NULL COMMENT '任务类型ID',
  `TASK_NAME` char(10) default NULL COMMENT '任务名称',
  `START_DATE` char(10) default NULL COMMENT '计划开始日期',
  `END_DATE` char(10) default NULL COMMENT '计划结束日期',
  `WORKLOAD` char(10) default NULL COMMENT '工作量估计(人天)',
  `REMARK` char(225) default NULL COMMENT '备注',
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `plan` */

/*Table structure for table `project_team` */

DROP TABLE IF EXISTS `project_team`;

CREATE TABLE `project_team` (
  `ID` char(10) NOT NULL COMMENT '主键',
  `CODE` char(10) default NULL COMMENT '项目组编号',
  `NAME` char(50) default NULL COMMENT '项目组名称',
  `DAILY_TEM_ID` char(10) default NULL COMMENT '项目组日报模板ID',
  `WEEKLY_TEM_ID` char(10) default NULL COMMENT '项目组周报模板ID',
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `project_team` */

/*Table structure for table `task` */

DROP TABLE IF EXISTS `task`;

CREATE TABLE `task` (
  `ID` char(10) NOT NULL COMMENT '主键',
  `BUSINESS` char(10) default NULL COMMENT '业务模块',
  `PERSON_CONTRACT` char(10) default NULL COMMENT '负责人ID',
  `DEMAND_BUG` char(10) default NULL COMMENT '需求/BUG号',
  `DESCRIPTION` char(255) default NULL COMMENT '描述',
  `STATUS` char(10) default NULL COMMENT '状态',
  `FILE_PATH` char(10) default NULL COMMENT '文件路径',
  `STAR_DATE` char(10) default NULL COMMENT '开始时间',
  `END_DATE` char(10) default NULL COMMENT '结束时间',
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `task` */

/*Table structure for table `task_type` */

DROP TABLE IF EXISTS `task_type`;

CREATE TABLE `task_type` (
  `ID` char(10) NOT NULL COMMENT '主键',
  `CODE` char(10) default NULL COMMENT '任务代码',
  `DESCRIPTION` char(255) default NULL COMMENT '描述',
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `task_type` */

/*Table structure for table `template` */

DROP TABLE IF EXISTS `template`;

CREATE TABLE `template` (
  `ID` char(10) NOT NULL COMMENT '主键',
  `PROJECT_TEAM_CODE` char(10) default NULL COMMENT '项目组ID',
  `TITLE` char(50) default NULL COMMENT '标题',
  `HEAD` char(255) default NULL COMMENT '表头',
  `FLAG` char(255) default NULL COMMENT '标识(周报和日报标识)',
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `template` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
