/**
 * 
 */
package com.netfinworks.optimus.domain;

import com.netfinworks.common.util.money.Money;

/**
 * <p>注释</p>
 */
public interface Constants {

    static final String        SOURCE_CODE                                 = "fj283";

    static final Money         ZERO_MONEY                                  = new Money("0.00");
    //会员转账
    public static final String PRODUCT_TRANSFER                            = "10310";

    static final String        ACCESS_CHANNEL_WEB                          = "WEB";
    static final String        ACCESS_CHANNEL_WAP                          = "WAP";

    static final String        PAY_CHANNEL_BALANCE                         = "01";
    static final String        PAY_CHANNEL_ONLINEBANK                      = "02";
    static final String        PAY_CHANNEL_BONUS                           = "08";

    public static final String TSS_INNER_MEMBER                            = "innerMember";
    public static final String TRADE_STATUS_FINISHED                       = "401";
    public static final String TRADE_STATUS_PAY_SUCC                       = "301";
    public static final String TRADE_STATUS_WAITING_PAY                    = "100";
    static final String        TRADE_PAYMENT_STATUS_FAIL                   = "F";
    //基本户
    //public static final long   ACCOUNT_TYPE_BASIC                          = 101;

    //退款原始凭证前缀
    public static final String RF_VOUCHER_PREFIX                           = "R";

    //通知模板
    //流标通知-投资人
    public static final String TPL_FAILBID_INVEST                          = "BIDFAIL_INVESTOR";
    //流标通知-借款人
    public static final String TPL_FAILBID_BORROWER                        = "BIDFAIL_BORROWER";
    //满标通知-借款人
    public static final String TPL_BIDFULL_BORROWER                        = "BIDFULL_BORROWER";
    //满标通知-投资人
    public static final String TPL_BIDFULL_INVESTOR                        = "BIDFULL_INVESTOR";
    //投标成功通知
    public static final String TPL_INVEST_SUCC                             = "BID_SUCCESS";
    //投标失败通知
    public static final String TPL_INVEST_FAIL                             = "BID_FAIL";
    //  本息回收
    static final String        TPL_NOTIFY_RECOVERY_SUCCESS                 = "INVEST_RECOVERY_SUCCESS";

    //PNS即时到账通知状态
    public static final String WAIT_BUYER_PAY                              = "WAIT_BUYER_PAY";
    public static final String TRADE_SUCCESS                               = "TRADE_SUCCESS";
    public static final String TRADE_FINISHED                              = "TRADE_FINISHED";
    public static final String TRADE_CLOSED                                = "TRADE_CLOSED";
    public static final String REFUND_SUCCESS                              = "REFUND_SUCCESS";
    public static final String TSS_REFUND_CODE_SUCCESS                     ="951";
    public static final String TSS_REFUND_CODE_FAILED                      ="952";
    //PNS支付通知状态（支付成功，支付失败）
    public static final String TSS_PAY_SUCCESS                             = "PAY_SUCCESS";
    public static final String TSS_PAY_FAIL                                = "PAY_FAIL";

    public static final String SUBJECT_STATUS_BIDFULL                      = "BID_FULL";
    public static final String SUBJECT_STATUS_BIDFAIL                      = "BID_FAIL";

    public static final String LOAN_FUND_APPLY_RESULT_BIDFULL              = "BIDFULL";
    public static final String LOAN_FUND_APPLY_RESULT_BIDFAIL              = "BIDFAIL";

    public static final String IS_CREDITOR_NO                              = "N";

    /**  符号.  */
    static final String        DOT                                         = ".";

    /** 
     * 出款交易通知状态
     */
    public static final String FOUD_OUT_PAY_STATUS_SUCCESS                 = "S";
    public static final String FOUD_OUT_PAY_STATUS_FAILURE                 = "F";
    public static final String FOUD_OUT_PAY_STATUS_PROCESS                 = "P";
    public static final String FOUD_OUT_PAY_STATUS_EXCEPTION               = "E";

    public static final String VERTICAL_BAR                                = "|";

    //查询MEMBER的身份证
    public static final int    MEMBER_VERIFY_ID_CARD                       = 1;
    //法人名称
    public static final int    MEMBER_VERIFY_LEAGAL_PERSON                 = 6;
    //法人手机
    public static final int    MEMBER_VERIFY_LEAGAL_PHONE                  = 7;

    static final String        FINANCE_PARTNER_NAME_KEY                    = "merchant_shop_name";
    
    //付款方名称
    public static final String FINANCE_BUYER_NAME_KEY                    = "buyer_name";

    public static final String FINANCE_USER_NO_KEY = "userNo";
    
    //DPM反应加减的flag
    public static final int DPM_DIRECTION_PLUS = 1;    
    public static final int DPM_DIRECTION_SUBTRACT = 2;
    
    //消息截取开始、结束值
    public static final int MSG_BEGIN_SUBSTR = 0;
    public static final int MSG_END_SUBSTR   = 128;
    
    //申购挂起值增加、减少
    public static final String APPLY_ORDER_HANG_ADD      = "add";
    public static final String APPLY_ORDER_HANG_SUBTRACT = "subtract";
    
 // 清算状态
 	public static final String RECO_STATE                                                         = "recoState";
 	// 结算状态
 	public static final String SETTLE_STATE                                                    = "settleState";
 	
 	//清结算对账文件通知：文件名
 	public static final String MQ_TRANSFER_FILE_FILENAME                            = "fileName";
 	//清结算对账文件通知：文件日期
 	public static final String MQ_TRANSFER_FILE_FILEDATE                              = "fileDate";
 	//清结算对账文件通知：文件地址
 	public static final String MQ_TRANSFER_FILE_UFS_ADDRESS                 = "ufsAddress";
}
