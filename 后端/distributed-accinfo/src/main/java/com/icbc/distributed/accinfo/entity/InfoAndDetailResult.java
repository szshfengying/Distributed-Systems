package com.icbc.distributed.accinfo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.text.DateFormat;
import java.util.Date;

@Data
public class InfoAndDetailResult {

    /**
     * 账号
     */
    private String accId;
    /**
     * 账户状态 0-正常 1-销户
     */
    private Integer accStatus;
    /**
     * 账户名称
     */
    private String accTitle;
    /**
     * 密码
     */
    private String password;
    /**
     * 通存标志
     */
    private Integer depositSign;
    /**
     * 通兑标志
     */
    private Integer exchangeSign;
    /**
     * 存现开关
     */
    private Integer depositSwitch;
    /**
     * 取现开关
     */
    private Integer withdrawalSwitch;
    /**
     * 开立机构
     */
    private String openingInstitution;
    /**
     * 最后修改日期
     */
    private Date lastModifiedDate;
    /**
     * 签订日期
     */
    private Date signingDate;
    /**
     * 注销日期
     */
    private Date logoutDate;
    /**
     * 地区编号
     */
    private Integer regionId;



    /**
     * 币种
     */
    private Integer currType;
    /**
     * 当前余额
     */
    private Long curBalance;
    /**
     * 昨日余额
     */
    private Long ydayBalance;
    /**
     * 最后交易日 日期按YYY-MM-DD格式存储
     */
    private Date lastTxnDate;

    @Override
    public String toString() {
        DateFormat df = DateFormat.getDateInstance();
        return "{" +
                "accId=" + accId +
                ", accStatus=" + accStatus +
                ", accTitle='" + accTitle + '\'' +
                ", password=" + password +
                ", depositSign=" + depositSign +
                ", exchangeSign=" + exchangeSign +
                ", depositSwitch=" + depositSwitch +
                ", withdrawalSwitch=" + withdrawalSwitch +
                ", openingInstitution='" + openingInstitution + '\'' +
                ", lastModifiedDate=" + df.format(lastModifiedDate) +
                ", signingDate=" + df.format(signingDate) +
                ", logoutDate=" + df.format(logoutDate) +
                ", regionId=" + regionId +
                ", currType=" + currType +
                ", curBalance=" + curBalance +
                ", ydayBalance=" + ydayBalance +
                ", lastTxnDate=" + df.format(lastTxnDate) +
                '}';
    }
}
