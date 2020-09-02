package com.icbc.distributed.transfer.entity;


import lombok.Data;
import java.util.Date;

@Data
public class TransferInfoEntity {

    /**
     * 业务唯一编码
     */
    private String txnId;

    /**
     * 执行机构
     */
    private String execOrganno;

    /**
     * 执行柜员
     */
    private String execTellerno;


    /**
     * 币种
     */
    private Integer currType;

    /**
     * 发起 账号
     */
    private String accIdFrom;

    /**
     * 发起 账户名称
     */
    private String accTitleFrom;


    /**
     * 接受  账号
     */
    private String accIdTo;

    /**
     * 接受  账户名称
     */
    private String accTitleTo;

    /**
     * 发生额
     */
    private Double amount;


    /**
     * 交易日期
     */
    private Date execDate;

    /**
     * 支付密码
     * */
    private String password;


}
