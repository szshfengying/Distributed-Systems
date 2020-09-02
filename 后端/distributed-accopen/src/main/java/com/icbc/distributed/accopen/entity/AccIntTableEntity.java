package com.icbc.distributed.accopen.entity;

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
@TableName("acc_int_table")
public class AccIntTableEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long snId;
	/**
	 * 
	 */

	private String accId;
	/**
	 * 
	 */
	private Integer currType;
	/**
	 * 
	 */
	private String intEvino;
	/**
	 * 
	 */
	private Integer status;
	/**
	 * 
	 */
	private Date beginDate;
	/**
	 * 
	 */
	private Date endDate;
	/**
	 * 
	 */
	private Integer intDate;
	/**
	 * 
	 */
	private Long amount;
	/**
	 * 
	 */
	private Integer rateIncn;
	/**
	 * 
	 */
	private Integer rateAltf;
	/**
	 * 
	 */
	private Integer rateCode;
	/**
	 * 
	 */
	private Integer baseRate;
	/**
	 * 
	 */
	private Integer floaType;
	/**
	 * 
	 */
	private Integer floaRate;
	/**
	 * 
	 */
	private Integer execRate;
	/**
	 * 
	 */
	private String execOrganno;
	/**
	 * 
	 */
	private String execTellerno;
	/**
	 * 
	 */
	private Date execDate;
	/**
	 * 
	 */
	private Date execTimestamp;
	/**
	 * 地区编号
	 */
	private Integer regionId;

}
