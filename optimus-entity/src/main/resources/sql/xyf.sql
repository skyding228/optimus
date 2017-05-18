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

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
