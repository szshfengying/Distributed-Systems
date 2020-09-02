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
 * @date 2020-08-19 21:24:54
 */
@Data
@TableName("nthpa_zon_table")
public class NthpaZonTableEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 记录序号;auto_increment
	 */
	private Long snId;
	/**
	 * 地区号
	 */
	@TableId
	private String zoneno;
	/**
	 * 地区名称
	 */
	private String notes;
	/**
	 * 地区状态;1-正常;3-正常撤销;4-撤销
	 */
	private Integer status;
	/**
	 * 最后更新日期
	 */
	private Date lstmodd;
	/**
	 * 备用数字
	 */
	private Long bakdecl;
	/**
	 * 备用字符
	 */
	private String bakchar;
	/**
	 * 省份
	 */
	private String province;

}
