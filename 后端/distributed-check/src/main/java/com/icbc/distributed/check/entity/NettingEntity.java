package com.icbc.distributed.check.entity;

import lombok.Data;

@Data
public class NettingEntity {
    /**
     * 账号
     */
    private String accId;
    /**
     * 对账结果
     */
    private int result;
    /**
     * 借贷标志 0是借 1是贷
     */
    private String loanFlag;
    /**
     * 发生额
     */
    private String amount;
    /**
     * 交易日期
     */
    private String execDate;
    /**
     * 币种
     */
    private String currType;
}
