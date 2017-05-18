/**
 * 
 */
package com.netfinworks.optimus.domain.enums;

/**
 *
 */
public enum RedeemType {
    AUTO("AUTO","自动"),
    MANUALLY("MANUALLY","手动");
    private RedeemType(String code,String msg) {
        this.code = code;
        this.msg = msg;
    }
    public static RedeemType getByCode(String code) {
        for (RedeemType value : RedeemType.values()) {
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
