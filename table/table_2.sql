/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50719
Source Host           : localhost:3306
Source Database       : zhuhai_icbc

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2020-07-10 15:43:01
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `account_information`
-- ----------------------------
DROP TABLE IF EXISTS `account_information`;
CREATE TABLE `account_information` (
  `bank_id` bigint(20) NOT NULL,
  `account_id` bigint(20) NOT NULL,
  `account_status` tinyint(4) NOT NULL,
  `account_title` varchar(420) NOT NULL,
  `password` int(9) NOT NULL,
  `deposit_sign` int(1) NOT NULL,
  `exchange_sign` int(1) NOT NULL,
  `deposit_switch` int(1) NOT NULL,
  `withdrawal_switch` int(1) NOT NULL,
  `opening_institution` varchar(10) NOT NULL,
  `last_modified_date` date NOT NULL,
  `signing_date` date NOT NULL,
  `logout_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of account_information
-- ----------------------------
