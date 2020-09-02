package com.icbc.distributed.accinfo.entity;

import lombok.Data;

import java.util.Date;

@Data
public class AccDetailQueryEntity {


    /**
     * 业务唯一标号
     */
    private String txnId;

    /**
     * 交易代码
     */
    private Integer txnCode;

    /**
     * 交易类型1-查询类2-更新类
     */
    private Integer txnType;

    /**
     * 交易日期
     */
    private Date txnDate;

    /**
     * 操作机构
     */
    private  String execOrganno;
    /**
     * 操作柜员
     */
    private  String execTellerno;

    /**
     * 业务发生账号
     * */
    private String accId;



}
