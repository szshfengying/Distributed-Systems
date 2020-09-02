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
@TableName("nthpa_brp_table")
public class NthpaBrpTableEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 地区序号;auto_increment
	 */
	@TableId
	private Long snId;
	/**
	 * 地区号;唯一索引-字段1
	 */
	private Integer zoneno;
	/**
	 * 网点号;唯一索引-字段1
	 */
	private String brno;
	/**
	 * 管理网点号;唯一索引-字段1
	 */
	private Integer mbrno;
	/**
	 * 网点类型;1-分行;2-一级网点;3-二级网点
	 */
	private Integer brnoType;
	/**
	 * 网点名称
	 */
	private String brnoName;
	/**
	 * 服务器签到状态;1-未签到;2-已签到
	 */
	private Integer status;
	/**
	 * 网点全称
	 */
	private String brnoFullName;
	/**
	 * 分签号管理网点号
	 */
	private Integer rmbrno;
	/**
	 * 网点地址
	 */
	private String address;
	/**
	 * 网点邮编
	 */
	private String zip;
	/**
	 * 电话
	 */
	private String phone;
	/**
	 * 简称
	 */
	private String shortnm;
	/**
	 * 核算网点号
	 */
	private Integer actBrno;
	/**
	 * 网点状态;1-正常;2-计划撤销;3-正常撤销;4...
	 */
	private Integer brnoStatus;
	/**
	 * 最后更新日期;日期按YYYY-MM-DD格式存储
	 */
	private Date lastModDate;
	/**
	 * 备用数字
	 */
	private Long bakDecl;
	/**
	 * 备用字符
	 */
	private String bakVarchar;

}
