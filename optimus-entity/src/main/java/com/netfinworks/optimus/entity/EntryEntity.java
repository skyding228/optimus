package com.netfinworks.optimus.entity;

import java.math.BigDecimal;
import java.util.Date;

public class EntryEntity extends BaseEntity {
    /**  */
    private Long entryId;

    /** 金额 */
    private BigDecimal amount;

    /** 交易id */
    private String orderId;

    /** 交易类型 */
    private String orderType;

    /** +  - */
    private String dc;

    /** 会员id */
    private String memberId;

    /** 账户id */
    private String accountId;

    /**  */
    private BigDecimal afterBalance;

    /**  */
    private BigDecimal beforeBalance;

    /** 摘要 */
    private String digest;

    /**  */
    private Date createTime;

    /**  */
    private String ext1;

    /**  */
    private String ext2;

    /**
     * 
     * @return entryId
     */
    public Long getEntryId() {
        return entryId;
    }

    /**
     * 
     * @param entryId
     */
    public void setEntryId(Long entryId) {
        this.entryId = entryId;
    }

    /**
     * 金额
     * @return amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 金额
     * @param amount
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 交易id
     * @return orderId
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * 交易id
     * @param orderId
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * 交易类型
     * @return orderType
     */
    public String getOrderType() {
        return orderType;
    }

    /**
     * 交易类型
     * @param orderType
     */
    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    /**
     * +  -
     * @return dc
     */
    public String getDc() {
        return dc;
    }

    /**
     * +  -
     * @param dc
     */
    public void setDc(String dc) {
        this.dc = dc;
    }

    /**
     * 会员id
     * @return memberId
     */
    public String getMemberId() {
        return memberId;
    }

    /**
     * 会员id
     * @param memberId
     */
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    /**
     * 账户id
     * @return accountId
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     * 账户id
     * @param accountId
     */
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    /**
     * 
     * @return afterBalance
     */
    public BigDecimal getAfterBalance() {
        return afterBalance;
    }

    /**
     * 
     * @param afterBalance
     */
    public void setAfterBalance(BigDecimal afterBalance) {
        this.afterBalance = afterBalance;
    }

    /**
     * 
     * @return beforeBalance
     */
    public BigDecimal getBeforeBalance() {
        return beforeBalance;
    }

    /**
     * 
     * @param beforeBalance
     */
    public void setBeforeBalance(BigDecimal beforeBalance) {
        this.beforeBalance = beforeBalance;
    }

    /**
     * 摘要
     * @return digest
     */
    public String getDigest() {
        return digest;
    }

    /**
     * 摘要
     * @param digest
     */
    public void setDigest(String digest) {
        this.digest = digest;
    }

    /**
     * 
     * @return createTime
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * @return ext1
     */
    public String getExt1() {
        return ext1;
    }

    /**
     * 
     * @param ext1
     */
    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }

    /**
     * 
     * @return ext2
     */
    public String getExt2() {
        return ext2;
    }

    /**
     * 
     * @param ext2
     */
    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }
}