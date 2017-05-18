package com.netfinworks.optimus.entity;

import java.math.BigDecimal;
import java.util.Date;

public class PaymentEntity extends BaseEntity {
    /**  */
    private String id;

    /** 关联订单编号，确认是否已经进行了出入款 */
    private String orderId;

    /** 产品编号 */
    private String subjectNo;

    /** 产品名称 */
    private String subjectName;

    /** 金额 */
    private BigDecimal amount;

    /** 出入款类型,PAYMENT_OUT:出款,PAYMENT_IN:入款 */
    private String paymentType;

    /** 操作类型,ACTION_LOAN:放款,ACTION_REPAYMENT:还款,ACTION_APPLY:申购,ACTION_REDEEM:赎回 */
    private String actionType;

    /** 产品类型,SUBJECT:定期产品,FUND:基金 */
    private String subjectType;

    /** 创建时间 */
    private Date createTime;

    /** 所属平台编码 */
    private String plat;

    /** 渠道平台编码 */
    private String toPlat;

    /**
     * 
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 关联订单编号，确认是否已经进行了出入款
     * @return orderId
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * 关联订单编号，确认是否已经进行了出入款
     * @param orderId
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * 产品编号
     * @return subjectNo
     */
    public String getSubjectNo() {
        return subjectNo;
    }

    /**
     * 产品编号
     * @param subjectNo
     */
    public void setSubjectNo(String subjectNo) {
        this.subjectNo = subjectNo;
    }

    /**
     * 产品名称
     * @return subjectName
     */
    public String getSubjectName() {
        return subjectName;
    }

    /**
     * 产品名称
     * @param subjectName
     */
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
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
     * 出入款类型,PAYMENT_OUT:出款,PAYMENT_IN:入款
     * @return paymentType
     */
    public String getPaymentType() {
        return paymentType;
    }

    /**
     * 出入款类型,PAYMENT_OUT:出款,PAYMENT_IN:入款
     * @param paymentType
     */
    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    /**
     * 操作类型,ACTION_LOAN:放款,ACTION_REPAYMENT:还款,ACTION_APPLY:申购,ACTION_REDEEM:赎回
     * @return actionType
     */
    public String getActionType() {
        return actionType;
    }

    /**
     * 操作类型,ACTION_LOAN:放款,ACTION_REPAYMENT:还款,ACTION_APPLY:申购,ACTION_REDEEM:赎回
     * @param actionType
     */
    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    /**
     * 产品类型,SUBJECT:定期产品,FUND:基金
     * @return subjectType
     */
    public String getSubjectType() {
        return subjectType;
    }

    /**
     * 产品类型,SUBJECT:定期产品,FUND:基金
     * @param subjectType
     */
    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType;
    }

    /**
     * 创建时间
     * @return createTime
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 所属平台编码
     * @return plat
     */
    public String getPlat() {
        return plat;
    }

    /**
     * 所属平台编码
     * @param plat
     */
    public void setPlat(String plat) {
        this.plat = plat;
    }

    /**
     * 渠道平台编码
     * @return toPlat
     */
    public String getToPlat() {
        return toPlat;
    }

    /**
     * 渠道平台编码
     * @param toPlat
     */
    public void setToPlat(String toPlat) {
        this.toPlat = toPlat;
    }
}