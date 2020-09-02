package com.icbc.distributed.transfer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author ${author}
 * @email ${email}
 * @date 2020-07-20 20:46:50
 */
@Data
@TableName("acc_details_table")
public class AccDetailsTableEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 分区编号
	 */
	@TableId(value = "depart_id",type = IdType.ASSIGN_ID)
	private Integer departId;
	/**
	 * 账号
	 */
	private String accId;
	/**
	 * 币种
	 */
	private Integer currType;
	/**
	 * 业务唯一编码
	 */
	private String txnId;
	/**
	 * 执行机构
	 */
	private String execOrganno;
	/**
	 * 执行柜员
	 */
	private String execTellerno;
	/**
	 * 明细序号
	 */
	private Long snId;
	/**
	 * 交易日期
	 */
	private Date execDate;
	/**
	 * 交易代码
	 */
	private Integer txnCode;
	/**
	 * 现金标志;1-现金;2-转账
	 */
	private Integer cashFlag;
	/**
	 * 借贷标志
	 */
	private Integer loanFlag;
	/**
	 * 发生额
	 */
	private Double amount;
	/**
	 * 余额
	 */
	private Double balance;
	/**
	 * 地区编号
	 */
	private Integer regionId;

}
