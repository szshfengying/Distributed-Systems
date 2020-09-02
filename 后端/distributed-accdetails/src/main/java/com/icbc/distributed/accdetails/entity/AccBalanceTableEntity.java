package com.icbc.distributed.accdetails.entity;

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
 * @date 2020-07-20 20:19:53
 */
@Data
@TableName("acc_balance_table")
public class AccBalanceTableEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 记录序号AUTO_INCREMENT
	 */
	@TableId
	private Long snId;
	/**
	 * 账号
	 */
	private Long accId;
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
