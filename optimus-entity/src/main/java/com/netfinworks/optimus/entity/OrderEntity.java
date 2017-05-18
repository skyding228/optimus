package com.netfinworks.optimus.entity;

import java.math.BigDecimal;
import java.util.Date;

public class OrderEntity extends BaseEntity {
    /**  */
    private String orderId;

    /** 渠道id */
    private String chanId;

    /** 渠道用户id */
    private String chanUserId;

    /** 会员id */
    private String memberId;

    /** 类型 deposit withdraw apply redeem */
    private String orderType;

    /** 产品id */
    private String productId;

    /** 充值金额 */
    private BigDecimal amount;

    /** 外部id */
    private String outerOrderId;

    /** 状态: I 初始   P 处理中 S 成功  F失败  */
    private String orderStatus;

    /** 备注 */
    private String memo;

    /**  */
    private Date orderTime;

    /**  */
    private Date updateTime;

    /** 扩展1 可用于操作员 */
    private String ext1;

    /** 扩展2 可用于审核员 */
    private String ext2;

    /**
     * 
     * @return orderId
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * 
     * @param orderId
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * 渠道id
     * @return chanId
     */
    public String getChanId() {
        return chanId;
    }

    /**
     * 渠道id
     * @param chanId
     */
    public void setChanId(String chanId) {
        this.chanId = chanId;
    }

    /**
     * 渠道用户id
     * @return chanUserId
     */
    public String getChanUserId() {
        return chanUserId;
    }

    /**
     * 渠道用户id
     * @param chanUserId
     */
    public void setChanUserId(String chanUserId) {
        this.chanUserId = chanUserId;
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
     * 类型 deposit withdraw apply redeem
     * @return orderType
     */
    public String getOrderType() {
        return orderType;
    }

    /**
     * 类型 deposit withdraw apply redeem
     * @param orderType
     */
    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    /**
     * 产品id
     * @return productId
     */
    public String getProductId() {
        return productId;
    }

    /**
     * 产品id
     * @param productId
     */
    public void setProductId(String productId) {
        this.productId = productId;
    }

    /**
     * 充值金额
     * @return amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 充值金额
     * @param amount
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 外部id
     * @return outerOrderId
     */
    public String getOuterOrderId() {
        return outerOrderId;
    }

    /**
     * 外部id
     * @param outerOrderId
     */
    public void setOuterOrderId(String outerOrderId) {
        this.outerOrderId = outerOrderId;
    }

    /**
     * 状态: I 初始   P 处理中 S 成功  F失败 
     * @return orderStatus
     */
    public String getOrderStatus() {
        return orderStatus;
    }

    /**
     * 状态: I 初始   P 处理中 S 成功  F失败 
     * @param orderStatus
     */
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * 备注
     * @return memo
     */
    public String getMemo() {
        return memo;
    }

    /**
     * 备注
     * @param memo
     */
    public void setMemo(String memo) {
        this.memo = memo;
    }

    /**
     * 
     * @return orderTime
     */
    public Date getOrderTime() {
        return orderTime;
    }

    /**
     * 
     * @param orderTime
     */
    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    /**
     * 
     * @return updateTime
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 扩展1 可用于操作员
     * @return ext1
     */
    public String getExt1() {
        return ext1;
    }

    /**
     * 扩展1 可用于操作员
     * @param ext1
     */
    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }

    /**
     * 扩展2 可用于审核员
     * @return ext2
     */
    public String getExt2() {
        return ext2;
    }

    /**
     * 扩展2 可用于审核员
     * @param ext2
     */
    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }
}