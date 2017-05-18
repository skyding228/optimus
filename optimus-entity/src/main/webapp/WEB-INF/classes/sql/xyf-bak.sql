/*
SQLyog v10.2 
MySQL - 5.1.73 : Database - optimus
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`optimus` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;

USE `optimus`;

/*Table structure for table `t_account` */

DROP TABLE IF EXISTS `t_account`;

CREATE TABLE `t_account` (
  `account_id` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT '账号id',
  `account_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '名称',
  `account_title_no` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '账户科目号',
  `balance` decimal(15,2) DEFAULT NULL COMMENT '余额',
  `debitBalance` decimal(15,2) DEFAULT NULL COMMENT '借方余额',
  `creditBalance` decimal(15,2) DEFAULT NULL COMMENT '贷方余额',
  `balance_direction` char(1) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'D：借方  C：贷方',
  `frozen_balance` decimal(15,2) DEFAULT NULL COMMENT '冻结余额',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `t_account` */

insert  into `t_account`(`account_id`,`account_name`,`account_title_no`,`balance`,`debitBalance`,`creditBalance`,`balance_direction`,`frozen_balance`,`create_time`,`update_time`) values ('1','zhangsan','111','649.00',NULL,NULL,NULL,NULL,NULL,'2016-02-25 09:53:48');

/*Table structure for table `t_entry` */

DROP TABLE IF EXISTS `t_entry`;

CREATE TABLE `t_entry` (
  `entry_id` bigint(50) NOT NULL AUTO_INCREMENT,
  `amount` decimal(18,2) DEFAULT NULL COMMENT '金额',
  `order_id` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '交易id',
  `order_type` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '交易类型',
  `dc` char(1) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '+  -',
  `member_id` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '会员id',
  `account_id` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '账户id',
  `after_balance` decimal(18,2) DEFAULT NULL,
  `before_balance` decimal(18,2) DEFAULT NULL,
  `digest` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '摘要',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`entry_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='分录（变化明细）';

/*Data for the table `t_entry` */

insert  into `t_entry`(`entry_id`,`amount`,`order_id`,`order_type`,`dc`,`member_id`,`account_id`,`after_balance`,`before_balance`,`digest`,`create_time`) values (1,'10.00',NULL,'deposit','+','1','1','10.00','0.00','',NULL),(2,'10.00',NULL,'deposit','+','1','1','20.00','10.00','',NULL),(3,'10.00',NULL,'deposit','+','1','1','30.00','20.00','',NULL),(4,'22.00',NULL,'deposit','+','1','1','52.00','30.00','',NULL),(5,'33.00',NULL,'deposit','+','1','1','85.00','52.00','',NULL),(6,'44.00',NULL,'deposit','+','1','1','129.00','85.00','',NULL),(7,'55.00',NULL,'deposit','+','1','1','184.00','129.00','',NULL),(12,'111.00',NULL,'deposit','+','1','1','427.00','316.00','',NULL),(13,'222.00','4b509920-36fc-4f32-918e-01c2b7284636','deposit','+','1','1','649.00','427.00','','2016-02-25 15:00:09');

/*Table structure for table `t_member` */

DROP TABLE IF EXISTS `t_member`;

CREATE TABLE `t_member` (
  `member_id` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT '用户id  ',
  `chan_id` varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT '渠道id',
  `chan_user_id` varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT '渠道用户id',
  `chan_user_name` varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT '渠道用户名',
  `mobile` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '手机号',
  `account_id` varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT '账户id',
  `pay_passwd` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '支付密码',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `status` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '状态  Normal：正常     Lock：密码错误锁定     Freeze：风控冻结 ',
  `lock_time` datetime DEFAULT NULL COMMENT '锁定时间',
  PRIMARY KEY (`member_id`),
  UNIQUE KEY `chan_id_chan_user_id` (`chan_id`,`chan_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `t_member` */

insert  into `t_member`(`member_id`,`chan_id`,`chan_user_id`,`chan_user_name`,`mobile`,`account_id`,`pay_passwd`,`create_time`,`update_time`,`status`,`lock_time`) values ('1','xyf','wch','wch','111','1',NULL,NULL,'2016-02-24 18:11:04',NULL,NULL);

/*Table structure for table `t_order` */

DROP TABLE IF EXISTS `t_order`;

CREATE TABLE `t_order` (
  `order_id` varchar(100) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `chan_id` varchar(100) COLLATE utf8_unicode_ci NOT NULL COMMENT '渠道id',
  `chan_user_id` varchar(100) COLLATE utf8_unicode_ci NOT NULL COMMENT '渠道用户id',
  `member_id` varchar(100) COLLATE utf8_unicode_ci NOT NULL COMMENT '会员id',
  `order_type` varchar(100) COLLATE utf8_unicode_ci NOT NULL COMMENT '类型 deposit withdraw apply redeem',
  `product_id` varchar(100) COLLATE utf8_unicode_ci NOT NULL COMMENT '产品id',
  `amount` decimal(15,2) NOT NULL COMMENT '充值金额',
  `outer_order_id` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '外部id',
  `order_status` varchar(100) COLLATE utf8_unicode_ci NOT NULL COMMENT '状态: I 初始   P 处理中 S 成功  F失败 ',
  `memo` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '备注',
  `order_time` datetime NOT NULL,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC;

/*Data for the table `t_order` */

insert  into `t_order`(`order_id`,`chan_id`,`chan_user_id`,`member_id`,`order_type`,`product_id`,`amount`,`outer_order_id`,`order_status`,`memo`,`order_time`,`update_time`) values ('26f62077-cc2e-4467-bf0d-511f87cdcbc2','xyf','wch','1','deposit','','222.00',NULL,'I','','2016-02-25 14:54:57','2016-02-25 14:54:57'),('30f2f2e1-f862-43a4-8ab5-1b0f1b762d72','abcd','1111','111','','','50.00','1234','1',NULL,'2016-02-03 15:42:53','2016-02-03 15:42:53'),('4b509920-36fc-4f32-918e-01c2b7284636','xyf','wch','1','deposit','','222.00',NULL,'S','','2016-02-25 15:00:09','2016-02-25 15:00:09'),('4de00f2f-1f82-4580-b42d-5496bd74a827','xyf','wch','1','deposit','111','10.00',NULL,'S',NULL,'2016-02-24 18:46:10',NULL),('584c49c4-7411-4826-a08c-72c4c16c88af','abcd','1111','111','','','50.00','1234','1',NULL,'2016-02-03 15:04:57','2016-02-03 15:04:58'),('67baf312-d39a-4176-a4a5-7b840594427d','abcd','1111','111','','','50.00','1234','1',NULL,'2016-02-03 15:48:42','2016-02-03 15:48:42'),('8bbd2abd-1aec-40da-810e-71688c51d04a','xyf','wch','1','deposit','111','22.00',NULL,'S',NULL,'2016-02-25 09:26:14',NULL),('8d99f48b-3608-47a6-99e0-1006c3992a96','abcd','1111','1111','','','50.00','1234','1',NULL,'2016-02-03 15:03:34','2016-02-03 15:03:36'),('b6a5cbcf-15a2-4317-9935-c3f359d29645','abcd','1111','1111','','','50.00','1234','1',NULL,'2016-02-03 15:49:23','2016-02-03 15:49:24'),('b9454ef8-e16c-48a3-9014-be00d7d13dec','abcd','1111','111','','','50.00','1234','1',NULL,'2016-02-03 15:52:15','2016-02-03 15:52:16'),('bbc4a750-edd5-4297-89c2-b07e703a04c8','xyf','wch','1','deposit','prd','111.00',NULL,'S',NULL,'2016-02-25 10:24:48',NULL),('c9f132b2-c02a-449d-b156-14ed421fc09e','abcd','1111','111','','','50.00','1234','1',NULL,'2016-02-03 15:43:03','2016-02-03 15:43:04'),('cd0c5db3-3086-4989-8ff1-7e2074dfd3c1','abcd','1111','1111','','','50.00','1234','1','??','2016-02-03 15:50:46','2016-02-25 14:31:46'),('df9212dd-ba16-4d70-b534-19f4642b6e2c','abcd','1111','111','','','50.00','1234','1',NULL,'2016-02-03 15:51:50','2016-02-03 15:51:51');

/*Table structure for table `T_SEQUENCE` */

DROP TABLE IF EXISTS `T_SEQUENCE`;

CREATE TABLE `T_SEQUENCE` (
  `NAME` varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT '名称',
  `CURRENT_VALUE` bigint(20) NOT NULL DEFAULT '1' COMMENT '当前值',
  `INCREMENT` smallint(6) NOT NULL DEFAULT '1' COMMENT '增长步长',
  `TOTAL` smallint(6) NOT NULL DEFAULT '10000' COMMENT '单次取值总量，更新总量需重启应用',
  `THRESHOLD` smallint(6) NOT NULL DEFAULT '1000' COMMENT '刷新阀值，更新阀值需重启应用',
  PRIMARY KEY (`NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `T_SEQUENCE` */

insert  into `T_SEQUENCE`(`NAME`,`CURRENT_VALUE`,`INCREMENT`,`TOTAL`,`THRESHOLD`) values ('memberId',1430001,1,10000,1000),('orderId',1430001,1,10000,1000);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
