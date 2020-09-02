package com.icbc.distributed.accopen.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("c_log_record")
public class DetectLoginTableEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 账号
     */
    private String accId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 锁定标志
     */
    private int lockFlag;

    /**
     * 失败次数
     */
    private int failureNum;

    /**
     * 行号
     */
    private long snId;


    /**
     * 登录时间
     */
    private Date loginDate;

}
