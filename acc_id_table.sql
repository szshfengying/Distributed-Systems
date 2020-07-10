/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50727
Source Host           : localhost:3306
Source Database       : mydb

Target Server Type    : MYSQL
Target Server Version : 50727
File Encoding         : 65001

Date: 2020-07-08 22:02:18
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for acc_id_table
-- ----------------------------
DROP TABLE IF EXISTS `acc_id_table`;
CREATE TABLE `acc_id_table` (
  `sn_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自动递增',
  `type_id` int(3) unsigned NOT NULL COMMENT '1-账号序号',
  `acc_id` bigint(20) unsigned NOT NULL,
  `last_modify_date` date NOT NULL COMMENT 'YYYY-MM-DD',
  PRIMARY KEY (`sn_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
