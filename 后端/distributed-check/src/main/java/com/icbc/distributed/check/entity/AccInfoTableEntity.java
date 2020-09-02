package com.icbc.distributed.check.entity;

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
 * @date 2020-07-20 20:40:33
 */
@Data
@TableName("acc_info_table")
public class AccInfoTableEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 行号
	 */
	@TableId
	private Long snId;
	/**
	 * 账号
	 */
	private String accId;
	/**
	 * 账户状态 0-正常 1-销户
	 */
	private Integer accStatus;
	/**
	 * 账户名称
	 */
	private String accTitle;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 通存标志
	 */
	private Integer depositSign;
	/**
	 * 通兑标志
	 */
	private Integer exchangeSign;
	/**
	 * 存现开关
	 */
	private Integer depositSwitch;
	/**
	 * 取现开关
	 */
	private Integer withdrawalSwitch;
	/**
	 * 开立机构
	 */
	private String openingInstitution;
	/**
	 * 最后修改日期
	 */
	private Date lastModifiedDate;
	/**
	 * 签订日期
	 */
	private Date signingDate;
	/**
	 * 注销日期
	 */
	private Date logoutDate;
	/**
	 * 地区编号
	 */
	private Integer regionId;
	/**
	 * 支付密码
	 */
	private String payPassword;

}
