package com.netfinworks.optimus.service.integration.mef;

/**   
 * <p> 
 * EFS-->FSN 交易类型
 * </p>
 * @ClassName:    MefTradeTypeKind
 * @Description:  EFS-->FSN 交易类型 
 *    
 */
public enum MefTradeTypeKind {
	INVEST("invest", "申购"),
    REDEEM("redeem", "赎回");
    private MefTradeTypeKind(String code,String msg) {
        this.code = code;
        this.msg = msg;
    }
    public static MefTradeTypeKind getByCode(String code) {
        for (MefTradeTypeKind value : MefTradeTypeKind.values()) {
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
