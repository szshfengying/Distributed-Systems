-- 5表账户明细表
CREATE TABLE `account_details_table`(
`depart_id`  INT(3) UNSIGNED NOT NULL COMMENT '分区编号',
`acc_id`  bigint(20) UNSIGNED NOT NULL COMMENT   '账号',
`curr_type`  int(3) UNSIGNED NOT NULL COMMENT '币种',
`txn_id` VARCHAR(30) NOT NULL COMMENT   '业务唯一编码',
`exec_organno`  BIGINT(10) UNSIGNED NOT NULL COMMENT  '执行机构',
`exec_tellerno`  BIGINT(11)  UNSIGNED NOT NULL COMMENT '执行柜员',
`exec_date` DATE NOT NULL COMMENT   '交易日期',
 `sn_id`  BIGINT(20) UNSIGNED NOT NULL COMMENT  '明细序号',
`txn_code` int(5) UNSIGNED NOT NULL COMMENT  '交易代码',
`cash_flag` int(1) UNSIGNED NOT NULL COMMENT '现金标志',
`loan_flag`  int(1) UNSIGNED NOT NULL COMMENT '借贷标志',
`amount` BIGINT(17) NOT NULL COMMENT    '发生额',
`balance` BIGINT(17) NOT NULL COMMENT   '余额',
PRIMARY KEY (`depart_id`),
KEY `acc_id` (`acc_id`) USING BTREE,
KEY `curr_type` (`curr_type`) USING BTREE,
KEY `sn_id` (`sn_id`) USING BTREE,
KEY `exec_date` (`exec_date`) USING BTREE
)