/*
 * netfinworks.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */
package com.netfinworks.optimus.domain.enums;

import java.util.Arrays;
import java.util.List;

public enum RedeemOrderStateKind {
	INIT("INIT", "初始"),
	REDEEM_PROCESS("REDEEM_PROCESS", "赎回中"),
	REDEEM_SUCCESS("REDEEM_SUCCESS", "赎回成功"),
	REDEEM_FAILURE("REDEEM_FAILURE", "赎回失败"),
	REDEEM_BACK_PROCESS("REDEEM_BACK_PROCESS", "处理中"),
	REDEEM_EXCEPTION("REDEEM_EXCEPTION", "赎回异常");
	
    private RedeemOrderStateKind(String code,String msg) {
        this.code = code;
        this.msg = msg;
    }
    public static RedeemOrderStateKind getByCode(String code) {
        for (RedeemOrderStateKind value : RedeemOrderStateKind.values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }
    public boolean equals(String code) {
        return getCode().equals(code);
    }
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    public static List<RedeemOrderStateKind> getAll(){
    	return Arrays.asList(values());
    }

}