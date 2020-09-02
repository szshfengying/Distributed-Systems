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
@TableName("acc_id_table")
public class AccIdTableEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 记录序号;自动递增
	 */
	@TableId
	private Long snId;
	/**
	 * 序号类型;1-账号序号
	 */
	private Integer typeId;
	/**
	 * 序号
	 */
	private Long accId;
	/**
	 * 最后调整日;YYYY-MM-DD
	 */
	private Date lastModifyDate;
	/**
	 * 地区编号
	 */
	private Integer regionId;

}
