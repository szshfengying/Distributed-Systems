package com.icbc.distributed.accinfo.entity;

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
 * @date 2020-07-20 20:31:15
 */
@Data
@TableName("acc_agrt_table")
public class AccAgrtTableEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 行号
	 */
	@TableId
	private Long snId;
	/**
	 * 账号
	 */

	private String  accId;
	/**
	 * 币种
	 */
	private Integer currType;
	/**
	 * 利率变动方式;1-固定利率;2-变动利率
	 */
	private Integer rateIncm;
	/**
	 * 利率变动周期;1-按日变动;2-按月变动;3-按季变动;4-按半年变动;5-按年变动;
	 */
	private Integer rateAltf;
	/**
	 * 基准利率;有符号数
	 */
	private Integer baseRate;
	/**
	 * 浮动方式;0-不浮动;2-浮动点数;3-浮动比例;
	 */
	private Integer floaType;
	/**
	 * 浮动率有符号数
	 */
	private Integer floaRate;
	/**
	 * 最后计息日期
	 */
	private Date lastRateDate;
	/**
	 * 最后修改机构
	 */
	private String lastOrganno;
	/**
	 * 最后修改柜员
	 */
	private String lastTellerno;
	/**
	 * 最后修改日期
	 */
	private Date lastModifyDate;
	/**
	 * 地区编号
	 */
	private Integer regionId;


}
