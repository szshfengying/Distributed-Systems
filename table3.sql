/*
Navicat MySQL Data Transfer

Source Server         : yes
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2020-07-08 23:28:12
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for table3
-- ----------------------------
DROP TABLE IF EXISTS `table3`;
CREATE TABLE `table3` (
  `sn_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `txn_date` date NOT NULL,
  `txn_id` varchar(30) NOT NULL,
  `channel_type` smallint(3) unsigned NOT NULL,
  `exec_organno` mediumint(5) unsigned NOT NULL,
  `exec_tellerno` bigint(11) unsigned NOT NULL,
  `txn_code` mediumint(5) unsigned NOT NULL,
  `txn_type` tinyint(1) unsigned NOT NULL,
  `cash_transfer_flag` tinyint(1) unsigned NOT NULL,
  `debit_acc_id` varchar(34) NOT NULL,
  `debit_acc_name` varchar(140) NOT NULL,
  `debit_curr_type` smallint(3) unsigned NOT NULL,
  `debit_amount` bigint(17) NOT NULL,
  `credit_acc_id` varchar(34) NOT NULL,
  `credit_acc_name` varchar(140) NOT NULL,
  `credit_curr_type` smallint(3) unsigned NOT NULL,
  `credit_amount` bigint(17) NOT NULL,
  `status` tinyint(1) unsigned NOT NULL,
  `last_modify_date` date NOT NULL,
  PRIMARY KEY (`sn_id`),
  KEY `txn_date` (`txn_date`),
  KEY `txn_id` (`txn_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of table3
-- ----------------------------
