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
@TableName("acc_id_table")
public class AccIdTableEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 记录序号;自动递增
	 */
	@TableId(value = "sn_id",type = IdType.ASSIGN_ID)
	private Long snId;
	/**
	 * 序号类型;1-账号序号
	 */
	private Integer typeId;
	/**
	 * 序号
	 */
	private String accId;
	/**
	 * 最后调整日;YYYY-MM-DD
	 */
	private Date lastModifyDate;
	/**
	 * 地区编号
	 */
	private Integer regionId;

}
