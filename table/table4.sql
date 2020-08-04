/*
 Navicat Premium Data Transfer

 Source Server         : mycon
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : 192.168.153.134:3306
 Source Schema         : bal

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : 65001

 Date: 10/07/2020 21:15:55
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for table4
-- ----------------------------
DROP TABLE IF EXISTS `table4`;
CREATE TABLE `table4`  (
  `sn_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'AUTO_INCREMENT',
  `acc_id` bigint(20) UNSIGNED NOT NULL,
  `curr_type` int(3) UNSIGNED NULL DEFAULT NULL,
  `cur_balance` bigint(17) NOT NULL,
  `yday_balance` bigint(17) NOT NULL,
  `last_txn_date` date NOT NULL COMMENT '日期按YYY-MM-DD格式存储',
  PRIMARY KEY (`sn_id`, `acc_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of table4
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
