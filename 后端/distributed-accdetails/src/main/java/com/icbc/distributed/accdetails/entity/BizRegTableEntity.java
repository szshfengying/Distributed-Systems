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
 * @date 2020-07-20 20:19:52
 */
@Data
@TableName("biz_reg_table")
public class BizRegTableEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 记录序号
	 */
	@TableId
	private Long snId;

	/**
	 * 交易日期
	 */
	private Date txnDate;
	/**
	 * 业务唯一标号
	 */
	private String txnId;
	/**
	 * 渠道种类
	 */
	private Integer channelType;
	/**
	 * 操作机构
	 */
	private String execOrganno;
	/**
	 * 操作柜员
	 */
	private String execTellerno;
	/**
	 * 交易代码
	 */
	private Integer txnCode;
	/**
	 * 交易类型1-查询类2-更新类
	 */
	private Integer txnType;
	/**
	 * 现金转账标志1-现金2转账
	 */
	private Integer cashTransferFlag;
	/**
	 * 借方账号
	 */
	private String debitAccId;
	/**
	 * 借方账户名称
	 */
	private String debitAccName;
	/**
	 * 借方币种
	 */
	private Integer debitCurrType;
	/**
	 * 借方发生额
	 */
	private Long debitAmount;
	/**
	 * 贷方账号
	 */
	private String creditAccId;
	/**
	 * 贷方账户名称
	 */
	private String creditAccName;
	/**
	 * 贷方币种
	 */
	private Integer creditCurrType;
	/**
	 * 贷方发生额
	 */
	private Long creditAmount;
	/**
	 * 状态1-处理中2-交易
	 */
	private Integer status;
	/**
	 * 最后更新日期 日期按YYY-MM-DD
	 */
	private Date lastModifyDate;
	/**
	 * 地区编号
	 */
	private Integer regionId;

}
