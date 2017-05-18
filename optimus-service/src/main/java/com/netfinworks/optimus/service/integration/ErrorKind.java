/*
 * Copyright 2013 netfinworks.com, Inc. All rights reserved.
 */
package com.netfinworks.optimus.service.integration;

/**
 * <p></p>
 *
 * @author 
 * @version 
 */
public enum ErrorKind {
    INVOKE_SUCCESS("S0001","调用成功"),
    VERIFY_SIGN_FAIL("F0099","验签失败"),
    VALIDATION_ERROR("F1001", "数据校验错误"),
    PERSIST_ERROR("F1002", "数据存储错误"),
    ILLEGAL_MEMBER_ACCOUNT("F1003","非法会员账号"),
    INTEGRATION_ERROR("F1004","调用外部系统错误"),
    RESPONSE_NOTFOUND("F1006","没有找到相应对象"),
    ASYNC_ERROR("F1007","异步执行错误"),
    BALANCE_NOT_ENOUGH("F1008","账户余额不足"),
    ILLEGAL_ACCOUNT_STATUS("F1011","账户状态不正确"),
    PHONE_NOT_FOUND("F1012","账户手机号不存在"),
    SMS_NOTIFY_ERROR("F1009","短信通知错误"),
    
    REDEEM_RECORD_NOT_FOUND("F1010","找不到对应的赎回记录"),
    SUBSCRIPTION_RECORD_NOT_FOUND("F1013","找不到对应的申购记录"),
    PROFIT_DETAIL_RECORD_NOT_FOUND("F1014","找不到对应的收益记录"),
    FSN_SIGN_ERROR("F1015","FSN签名错误"),
    
    FSN_EXE_ERROR("F1020","FSN调用错误"),
    
    CREDIT_STATUS_NOT_VALID("F1040", "债权状态不合法"),
    PAY_FAIL("F1050","支付失败"),
    MEMBER_ACCOUNT_NOT_EXIST("F1051","会员账户不存在"),
    ILLEGAL_STATE_ERROR("F1060","更新数据状态异常"),
    UNKNOWN_ERROR("F9999","未知异常");

    private String code;

    private String msg;

    private ErrorKind(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ErrorKind getByCode(String code) {
        for (ErrorKind value : ErrorKind.values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean equals(String code) {
        return getCode().equals(code);
    }
}