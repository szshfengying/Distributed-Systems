package com.icbc.distributed.accopen.entity;

import lombok.Data;

@Data
public class ResetEntity {
    /**
     * 账号
     */
    private String accId;
    /**
     * 原先的登录密码
     */
    private String password;
    /**
     * 修改的登录密码
     */
    private String newpassword;
}
