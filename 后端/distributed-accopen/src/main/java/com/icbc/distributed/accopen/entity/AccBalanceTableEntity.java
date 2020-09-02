package com.icbc.distributed.accopen.entity;

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
 * @date 2020-07-20 20:37:46
 */
@Data
@TableName("acc_balance_table")
public class AccBalanceTableEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 记录序号AUTO_INCREMENT
	 */
	@TableId(value = "sn_id",type = IdType.ASSIGN_ID)
	private Long snId;
	/**
	 * 账号
	 */
	private String accId;
	/**
	 * 币种
	 */
	private Integer currType;
	/**
	 * 当前余额
	 */
	private Long curBalance;
	/**
	 * 昨日余额
	 */
	private Long ydayBalance;
	/**
	 * 最后交易日 日期按YYY-MM-DD格式存储
	 */
	private Date lastTxnDate;
	/**
	 * 地区编号
	 */
	private Integer regionId;

}
