package com.icbc.distributed.accopen.vo.request;

import lombok.Data;

@Data
public class AccOpenEntity {
    /**
     * 执行机构
     */
    private String execOrganno;
    /**
     * 执行柜员
     */
    private String execTellerno;

    private String region;//地区

    private String branchId;//网点全称

    private String  accTitle;//账户名

    private String password;//密码
    /**
     * 支付密码
     */
    private String payPassword;
    /**
     * 身份证
     */
    private String number;
    /**
     * 电话号码
     */
    private String phone;

}
