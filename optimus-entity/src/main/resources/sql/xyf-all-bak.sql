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

/*Table structure for table `T_SEQUENCE` */

DROP TABLE IF EXISTS `T_SEQUENCE`;

CREATE TABLE `T_SEQUENCE` (
  `NAME` varchar(50) NOT NULL COMMENT '名称',
  `CURRENT_VALUE` bigint(20) NOT NULL DEFAULT '1' COMMENT '当前值',
  `INCREMENT` smallint(6) NOT NULL DEFAULT '1' COMMENT '增长步长',
  `TOTAL` smallint(6) NOT NULL DEFAULT '10000' COMMENT '单次取值总量，更新总量需重启应用',
  `THRESHOLD` smallint(6) NOT NULL DEFAULT '1000' COMMENT '刷新阀值，更新阀值需重启应用',
  PRIMARY KEY (`NAME`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `t_account` */

DROP TABLE IF EXISTS `t_account`;

CREATE TABLE `t_account` (
  `account_id` varchar(50) CHARACTER SET latin1 NOT NULL DEFAULT '' COMMENT '账号id',
  `account_name` varchar(50) CHARACTER SET latin1 DEFAULT NULL COMMENT '名称',
  `account_title_no` varchar(50) CHARACTER SET latin1 DEFAULT NULL COMMENT '账户科目号',
  `balance` decimal(15,2) DEFAULT NULL COMMENT '余额',
  `debitBalance` decimal(15,2) DEFAULT NULL COMMENT '借方余额',
  `creditBalance` decimal(15,2) DEFAULT NULL COMMENT '贷方余额',
  `balance_direction` char(1) CHARACTER SET latin1 DEFAULT NULL COMMENT 'D：借方  C：贷方',
  `frozen_balance` decimal(15,2) DEFAULT NULL COMMENT '冻结余额',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `t_asset_detail` */

DROP TABLE IF EXISTS `t_asset_detail`;

CREATE TABLE `t_asset_detail` (
  `asset_id` varchar(50) DEFAULT NULL,
  `member_id` varchar(50) DEFAULT NULL,
  `asset_type` varchar(50) DEFAULT NULL,
  `product_id` varchar(50) DEFAULT NULL,
  `amount` varchar(50) DEFAULT NULL,
  `profit` varchar(50) DEFAULT NULL,
  `create_time` varchar(50) DEFAULT NULL,
  `update_time` varchar(50) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COMMENT='资产';

/*Table structure for table `t_bid_order` */

DROP TABLE IF EXISTS `t_bid_order`;

CREATE TABLE `t_bid_order` (
  `BID_ORDER_NO` varchar(32) NOT NULL COMMENT '投标编号',
  `SUBJECT_NO` varchar(32) DEFAULT NULL COMMENT '标的编号',
  `MEMBER_ID` varchar(32) NOT NULL COMMENT '投资人',
  `CHANNEL_CODE` varchar(32) DEFAULT NULL COMMENT '渠道代码',
  `AMOUNT` decimal(19,2) NOT NULL COMMENT '金额',
  `RECOVERED_PRINCIPAL` decimal(19,2) DEFAULT NULL COMMENT '已回收本金',
  `STATUS` varchar(20) NOT NULL COMMENT '状态：INIT-初始状态，PAY_SUCCESS-支付成功，REFUNDING-退款中，REFUND_SUCCESS-退款成功，BID_SUCCESS-投资成功',
  `SUBMIT_TYPE` char(1) NOT NULL COMMENT '投标方式：0-自动投标，1-手动投标',
  `CREATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `MODIFIED_TIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `EXTENSION` varchar(2048) DEFAULT NULL COMMENT '扩展信息',
  `REMARK` varchar(1000) DEFAULT NULL COMMENT '备注',
  `SUBJECT_TYPE` varchar(10) NOT NULL COMMENT '标的：SUBJECT     债权: CREDIT',
  `PAY_AMOUNT` decimal(19,2) DEFAULT NULL COMMENT '记录用户实际付款金额',
  `PAY_FEE` decimal(19,2) DEFAULT NULL COMMENT '卖家支付的费用',
  `REWARD_INTEREST` decimal(19,2) DEFAULT NULL COMMENT '记录购买时刻 预购多少利息',
  `BID_FAIL_MSG` varchar(100) DEFAULT NULL COMMENT '投资失败原因',
  PRIMARY KEY (`BID_ORDER_NO`),
  KEY `IDX_TBO_MEMBER_ID` (`MEMBER_ID`),
  KEY `IDX_TBO_SUBJECT_NO` (`SUBJECT_NO`),
  KEY `IDX_TBO_STATUS` (`STATUS`),
  KEY `IDX_TBO_SUBMIT_TYPE` (`SUBMIT_TYPE`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `t_credit` */

DROP TABLE IF EXISTS `t_credit`;

CREATE TABLE `t_credit` (
  `CREDIT_ID` varchar(32) NOT NULL COMMENT 'å€ºæƒID',
  `SUBJECT_NO` varchar(32) DEFAULT NULL COMMENT 'æ•£æ ‡æ ‡çš„ç¼–å·',
  `MEMBER_ID` varchar(32) NOT NULL COMMENT 'å€ºæƒæ‰€æœ‰äºº',
  `NUMBERS` decimal(19,2) NOT NULL COMMENT 'ä»½é¢',
  `CHANNEL_CODE` varchar(32) DEFAULT NULL COMMENT 'æ¸ é“ä»£ç ',
  `PRICE` decimal(19,2) NOT NULL COMMENT 'å•ä»·',
  `CREATE_TIME` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'åˆ›å»ºæ—¶é—´',
  `MODIFIED_TIME` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'æ›´æ–°æ—¶é—´',
  `REMARK` varchar(1000) DEFAULT NULL COMMENT 'å¤‡æ³¨',
  `IS_ASSIGNABLE` varchar(32) NOT NULL DEFAULT 'N',
  `BID_ORDER_NO` varchar(32) DEFAULT NULL COMMENT 'æŠ•æ ‡è®°å½•',
  PRIMARY KEY (`CREDIT_ID`),
  KEY `IDX_CREDIT_SUBJECT_MEMBER` (`SUBJECT_NO`,`MEMBER_ID`),
  KEY `IDX_CREDIT_MODIFIED` (`MODIFIED_TIME`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `t_deposit_order` */

DROP TABLE IF EXISTS `t_deposit_order`;

CREATE TABLE `t_deposit_order` (
  `order_id` varchar(100) NOT NULL DEFAULT '',
  `chan_id` varchar(100) NOT NULL COMMENT '渠道id',
  `chan_user_id` varchar(100) NOT NULL COMMENT '渠道用户id',
  `member_id` varchar(100) NOT NULL COMMENT '会员id',
  `amount` decimal(15,2) NOT NULL COMMENT '充值金额',
  `outer_order_id` varchar(100) DEFAULT NULL COMMENT '外部id',
  `order_status` varchar(100) NOT NULL COMMENT '状态: I 初始   P 处理中 S 成功  F失败 ',
  `memo` varchar(200) DEFAULT NULL COMMENT '备注',
  `order_time` datetime NOT NULL,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`order_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `t_entry` */

DROP TABLE IF EXISTS `t_entry`;

CREATE TABLE `t_entry` (
  `entry_id` bigint(50) NOT NULL AUTO_INCREMENT,
  `amount` decimal(18,2) DEFAULT NULL COMMENT '金额',
  `order_id` varchar(100) CHARACTER SET latin1 DEFAULT NULL COMMENT '交易id',
  `order_type` varchar(10) CHARACTER SET latin1 DEFAULT NULL COMMENT '交易类型',
  `dc` char(1) CHARACTER SET latin1 DEFAULT NULL COMMENT '+  -',
  `member_id` varchar(10) CHARACTER SET latin1 DEFAULT NULL COMMENT '会员id',
  `account_id` varchar(10) CHARACTER SET latin1 DEFAULT NULL COMMENT '账户id',
  `after_balance` decimal(18,2) DEFAULT NULL,
  `before_balance` decimal(18,2) DEFAULT NULL,
  `digest` varchar(10) CHARACTER SET latin1 DEFAULT NULL COMMENT '摘要',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`entry_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='分录（变化明细）';

/*Table structure for table `t_invest_subject` */

DROP TABLE IF EXISTS `t_invest_subject`;

CREATE TABLE `t_invest_subject` (
  `APPLY_NO` varchar(32) NOT NULL COMMENT '贷款申请编号',
  `SUBJECT_NO` varchar(32) NOT NULL COMMENT '标的编号',
  `SUBJECT_NAME` varchar(512) NOT NULL COMMENT '标的名称',
  `DESCRIPTION` varchar(4096) DEFAULT NULL,
  `APPLY_PURPOSE` varchar(4096) DEFAULT NULL,
  `APPLY_AMOUNT` decimal(19,2) NOT NULL COMMENT '申请金额',
  `BID_MIN_AMOUNT` decimal(19,2) DEFAULT NULL COMMENT '投资最小资金',
  `BID_UNIT` decimal(19,2) NOT NULL COMMENT '投标单位：单位人民币',
  `BIDDED_AMOUNT` decimal(19,2) NOT NULL COMMENT '已投标金额',
  `BIDDABLE_AMOUNT` decimal(19,2) NOT NULL COMMENT '可投资金额',
  `REWARD_RATE` decimal(4,2) NOT NULL COMMENT '借款利率：年化利率，小数表示，比如0.25（25%）',
  `LOAN_TERM` varchar(32) NOT NULL COMMENT '贷款期限：1个月，3个月，6个月，12个月',
  `STATUS` varchar(20) NOT NULL COMMENT '标的状态：INIT- 未投标，BIDDING-招标中05，BIDFULL-已满标10，OVERDUE-流标，REPAYING-还款中15，20FINISH-已完结',
  `BID_BEGIN_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '招标开始时间',
  `VALID_TIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '标的有效期',
  `REPAY_TYPE` char(1) NOT NULL COMMENT '还款方式：1-到期还本付息',
  `CREATE_TIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `MODIFIED_TIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `CONTRACT_NO` varchar(32) DEFAULT NULL COMMENT '合同编号',
  `PRODUCT_NO` varchar(32) DEFAULT NULL COMMENT '产品编码',
  `INTEREST_DATE` timestamp NULL DEFAULT NULL COMMENT '记息日期',
  `EXTENSION` varchar(2048) DEFAULT NULL COMMENT '扩展信息',
  `REMARK` varchar(1000) DEFAULT NULL COMMENT '备注',
  `SUBJECT_TAG` varchar(2048) DEFAULT NULL COMMENT '标的标签',
  `inst_no` varchar(32) DEFAULT NULL COMMENT '原理财产品发行机构',
  `product_name` varchar(64) DEFAULT NULL COMMENT '原理财产品名称',
  `product_interest_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '原理财产品计息日期。日期格式：YYYY-MM-DD hh:mm:ss',
  `due_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '原产品到期日。日期格式：YYYY-MM-DD hh:mm:ss',
  `channel_code` varchar(32) DEFAULT NULL COMMENT '渠道代码',
  `member_id` varchar(32) DEFAULT NULL COMMENT '原资产持有会员标识',
  `WARN_STATUS` char(1) NOT NULL DEFAULT '1' COMMENT '预警状态0:取消预警1:预警中',
  PRIMARY KEY (`SUBJECT_NO`),
  UNIQUE KEY `IDX_TIS_APPLY_NO` (`APPLY_NO`),
  KEY `IDX_TIS_STATUS` (`STATUS`),
  KEY `IDX_TIS_BIG_BEGIN_TIME` (`BID_BEGIN_TIME`),
  KEY `IDX_TIS_CREATE_TIME` (`CREATE_TIME`),
  KEY `IDX_TIS_STATUS_CREATETIME` (`STATUS`,`CREATE_TIME`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `t_member` */

DROP TABLE IF EXISTS `t_member`;

CREATE TABLE `t_member` (
  `member_id` varchar(50) CHARACTER SET latin1 NOT NULL DEFAULT '' COMMENT '用户id  ',
  `chan_id` varchar(50) CHARACTER SET latin1 NOT NULL COMMENT '渠道id',
  `chan_user_id` varchar(50) CHARACTER SET latin1 NOT NULL COMMENT '渠道用户id',
  `chan_user_name` varchar(50) CHARACTER SET latin1 NOT NULL COMMENT '渠道用户名',
  `mobile` varchar(50) CHARACTER SET latin1 DEFAULT NULL COMMENT '手机号',
  `account_id` varchar(50) CHARACTER SET latin1 NOT NULL COMMENT '账户id',
  `pay_passwd` varchar(50) CHARACTER SET latin1 DEFAULT NULL COMMENT '支付密码',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `status` varchar(50) CHARACTER SET latin1 DEFAULT NULL COMMENT '状态  Normal：正常     Lock：密码错误锁定     Freeze：风控冻结 ',
  `lock_time` datetime DEFAULT NULL COMMENT '锁定时间',
  PRIMARY KEY (`member_id`),
  UNIQUE KEY `chan_id_chan_user_id` (`chan_id`,`chan_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `t_order` */

DROP TABLE IF EXISTS `t_order`;

CREATE TABLE `t_order` (
  `order_id` varchar(100) CHARACTER SET latin1 NOT NULL DEFAULT '',
  `chan_id` varchar(100) CHARACTER SET latin1 NOT NULL COMMENT '渠道id',
  `chan_user_id` varchar(100) CHARACTER SET latin1 NOT NULL COMMENT '渠道用户id',
  `member_id` varchar(100) CHARACTER SET latin1 NOT NULL COMMENT '会员id',
  `order_type` varchar(100) CHARACTER SET latin1 NOT NULL COMMENT '类型 deposit withdraw apply redeem',
  `product_id` varchar(100) CHARACTER SET latin1 NOT NULL COMMENT '产品id',
  `amount` decimal(15,2) NOT NULL COMMENT '充值金额',
  `outer_order_id` varchar(100) CHARACTER SET latin1 DEFAULT NULL COMMENT '外部id',
  `order_status` varchar(100) CHARACTER SET latin1 NOT NULL COMMENT '状态: I 初始   P 处理中 S 成功  F失败 ',
  `memo` varchar(200) CHARACTER SET latin1 DEFAULT NULL COMMENT '备注',
  `order_time` datetime NOT NULL,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC;

/*Table structure for table `t_pay_password_validate_history` */

DROP TABLE IF EXISTS `t_pay_password_validate_history`;

CREATE TABLE `t_pay_password_validate_history` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `member_id` varchar(50) DEFAULT NULL COMMENT '会员id',
  `validate_time` timestamp NULL DEFAULT NULL COMMENT '验证时间',
  `validate_result` char(1) DEFAULT NULL COMMENT '验证结果',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `t_recovery_order` */

DROP TABLE IF EXISTS `t_recovery_order`;

CREATE TABLE `t_recovery_order` (
  `RECOVERY_ORDER_NO` varchar(32) NOT NULL COMMENT '本息回收订单号',
  `SUBJECT_NO` varchar(32) NOT NULL COMMENT '标的编号',
  `REPAY_ID` varchar(32) NOT NULL COMMENT '还款订单号',
  `REPAY_TERM` int(11) NOT NULL COMMENT '还款期数',
  `REPAY_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '还款时间',
  `TOTAL_TERM` int(11) NOT NULL COMMENT '总还款期数',
  `TOTAL_AMOUNT` decimal(19,2) NOT NULL COMMENT '总还款额',
  `PRINCIPAL` decimal(19,2) NOT NULL COMMENT '总还本金额',
  `INTEREST` decimal(19,2) NOT NULL COMMENT '总还利息额',
  `GMT_CREATE` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `GMT_MODIFIED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `EXTENSION` varchar(2048) DEFAULT NULL COMMENT '扩展信息',
  `MEMO` varchar(1000) DEFAULT NULL COMMENT '备注',
  `REPAY_TYPE` varchar(32) DEFAULT NULL COMMENT '逾期还款 展期还款 提前还款',
  PRIMARY KEY (`RECOVERY_ORDER_NO`),
  KEY `IDX_TRO_SUBJECTNO_REPAYTERM` (`SUBJECT_NO`,`REPAY_TERM`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `t_recovery_order_detail` */

DROP TABLE IF EXISTS `t_recovery_order_detail`;

CREATE TABLE `t_recovery_order_detail` (
  `RECOVERY_ORDER_DETAIL_NO` varchar(32) NOT NULL COMMENT '收益明细号',
  `CREDIT_ID` varchar(32) NOT NULL,
  `RECOVERY_ORDER_NO` varchar(32) NOT NULL COMMENT '本息回收订单号',
  `MEMBER_ID` varchar(32) NOT NULL COMMENT '投资人',
  `AMOUNT` decimal(19,2) NOT NULL COMMENT '还款额',
  `PRINCIPAL` decimal(19,2) NOT NULL COMMENT '本金',
  `INTEREST` decimal(19,2) NOT NULL COMMENT '收益',
  `STATUS` varchar(20) DEFAULT NULL COMMENT '状态： INIT-初始中，SUCCESS-成功',
  `CREATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `MODIFIED_TIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `EXTENSION` varchar(2048) DEFAULT NULL COMMENT '扩展信息',
  `REMARK` varchar(1000) DEFAULT NULL COMMENT '备注',
  `CREDIT_PRICE` decimal(19,2) DEFAULT NULL COMMENT '当期债权',
  PRIMARY KEY (`RECOVERY_ORDER_DETAIL_NO`),
  KEY `IDX_TROD_MEMBER_ID` (`MEMBER_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `t_subject_repay_info` */

DROP TABLE IF EXISTS `t_subject_repay_info`;

CREATE TABLE `t_subject_repay_info` (
  `SUBJECT_NO` varchar(32) NOT NULL COMMENT '散标标的编号',
  `PRE_REPAY_DATE` datetime NOT NULL COMMENT '上期期还款日',
  `CURRENT_REPAY_DATE` datetime NOT NULL COMMENT '当期期还款日',
  `END_REPAY_DATE` datetime NOT NULL COMMENT '最后还款日',
  `CURRENT_TERM_INTEREST` decimal(19,2) NOT NULL COMMENT '当期利息',
  `SURPLUS_TERM` int(11) NOT NULL COMMENT '剩余期数',
  `SURPLUS_INTEREST` decimal(19,2) NOT NULL COMMENT '剩余总利息',
  `REPAY_INFO_STATUS` varchar(32) NOT NULL COMMENT ' 正常:NORMAL   展期:EXTEND       提前还款: EARLY_REPAY:提前还款',
  `RECOVERY_DATE` datetime DEFAULT NULL,
  `MODIFIED_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`SUBJECT_NO`),
  KEY `IDX_SUBJECT_REPAY_MODIFIED` (`MODIFIED_TIME`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `t_withdraw_order` */

DROP TABLE IF EXISTS `t_withdraw_order`;

CREATE TABLE `t_withdraw_order` (
  `order_id` varchar(100) NOT NULL COMMENT '订单id',
  `chan_id` varchar(100) NOT NULL COMMENT '外部渠道id',
  `chan_user_id` varchar(100) NOT NULL COMMENT '外部用户id',
  `member_id` varchar(100) NOT NULL COMMENT '会员id',
  `amount` decimal(15,2) NOT NULL COMMENT '金额',
  `outer_order_id` varchar(100) DEFAULT NULL COMMENT '外部id',
  `order_status` varchar(100) NOT NULL COMMENT '状态: I 初始   P 处理中 S 成功  F失败 ',
  `memo` varchar(200) DEFAULT NULL,
  `order_time` datetime NOT NULL,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`order_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
