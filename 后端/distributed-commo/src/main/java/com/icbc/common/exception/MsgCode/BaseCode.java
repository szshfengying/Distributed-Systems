package com.icbc.common.exception.MsgCode;

import lombok.Data;


public enum  BaseCode {

    UNKNOW_EXCEPTION(10000,"系统未知异常"),
    VAILD_EXCEPTION(10001,"参数格式校验失败"),
    PARAM_NULL_EXCEPTION(10002,"传入参数为空"),
    TABLE_ID_OVERFlW(10003,"数据库自增账户ID异常"),
    BUSINESS_REPEAT_EXCEPTION(10004,"业务重复发起"),
    ACC_ID_EXCEPTION(10005,"产生账户ID异常"),
    CHECK_REPEAT_EXCEPTION(10006,"查重失败"),
    LOGIN_PASSWORD_INVAILD_EXCEPTION(10007,"登录密码错误"),
    LOGIN_ACCID_INVAILD_EXCEPTION(10008,"登录账户不存在"),






    TRANSFER_ERROR_BUSINESS_REPEAT(20000, "一对一转账业务重复");

    private int code;
    private String msg;

    BaseCode(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }


}
