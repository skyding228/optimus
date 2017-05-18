/*
 * netfinworks.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */
package com.netfinworks.optimus.domain.enums;

/**
 * 申购类型
 *
 */
public enum ApplyTypeKind {
    MANUAL("MANUAL","手动"),
    AUTO("AUTO","自动");
    private ApplyTypeKind(String code,String msg) {
        this.code = code;
        this.msg = msg;
    }
    public static ApplyTypeKind getByCode(String code) {
        for (ApplyTypeKind value : ApplyTypeKind.values()) {
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

}