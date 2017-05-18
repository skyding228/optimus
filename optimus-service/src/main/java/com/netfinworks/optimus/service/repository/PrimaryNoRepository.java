/**
 * 
 */
package com.netfinworks.optimus.service.repository;

/**
 * <p>注释</p>
 */
public interface PrimaryNoRepository {

    public static final String PREFIX_APPLY_DEFAULT              = "A";
    public static final String PREFIX_REDEEM_DEFAULT             = "R";
    
    public static final String PREFIX_APPLY_REQUEST_SIGN         = "SG";
    public static final String PREFIX_REDEEM_REQUEST_REDO        = "RE";
    public static final String PREFIX_REDEEM_REQUEST_CANCEL_SIGN = "CS";

    String getOrderNo();
    
    String getMemberNo();
    
    String getApplyOrderNo();

    String getRedeemOrderNo();

    Long getProfitId();

    /**
     * 根据制定前缀生成赎回请求号
     * @param prefix
     * @return
     */
    String getRedeemRequestNoWithPrefix(String prefix);

    /**
     * 根据制定前缀生成申购请求号
     * @param prefix
     * @return
     */
    String getApplyRequestNoWithPrefix(String prefix);
}
