package com.netfinworks.optimus.entity;

import java.math.BigDecimal;
import java.util.Date;

public class ChanViewEntity extends BaseEntity {
    /** 渠道编号.日期 */
    private String id;

    /** 渠道编号 */
    private String chanId;

    /** 数据日期 */
    private Date date;

    /** 前一日账户余额 */
    private BigDecimal previous;

    /** 充值 */
    private BigDecimal deposit;

    /** 提现 */
    private BigDecimal withdraw;

    /** 产品投资总金额 */
    private BigDecimal invest;

    /** 还款本金 */
    private BigDecimal principal;

    /** 还款利息 */
    private BigDecimal interest;

    /** 使用备付金投资 */
    private BigDecimal provisionInvest;

    /** 备付金还款本金 */
    private BigDecimal provisionPrincipal;

    /** 备付金还款利息 */
    private BigDecimal provisionInterest;

    /** 当日账户余额 */
    private BigDecimal balance;

    /** 创建时间 */
    private Date createTime;

    /**
     * 渠道编号.日期
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * 渠道编号.日期
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 渠道编号
     * @return chanId
     */
    public String getChanId() {
        return chanId;
    }

    /**
     * 渠道编号
     * @param chanId
     */
    public void setChanId(String chanId) {
        this.chanId = chanId;
    }

    /**
     * 数据日期
     * @return date
     */
    public Date getDate() {
        return date;
    }

    /**
     * 数据日期
     * @param date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * 前一日账户余额
     * @return previous
     */
    public BigDecimal getPrevious() {
        return previous;
    }

    /**
     * 前一日账户余额
     * @param previous
     */
    public void setPrevious(BigDecimal previous) {
        this.previous = previous;
    }

    /**
     * 充值
     * @return deposit
     */
    public BigDecimal getDeposit() {
        return deposit;
    }

    /**
     * 充值
     * @param deposit
     */
    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    /**
     * 提现
     * @return withdraw
     */
    public BigDecimal getWithdraw() {
        return withdraw;
    }

    /**
     * 提现
     * @param withdraw
     */
    public void setWithdraw(BigDecimal withdraw) {
        this.withdraw = withdraw;
    }

    /**
     * 产品投资总金额
     * @return invest
     */
    public BigDecimal getInvest() {
        return invest;
    }

    /**
     * 产品投资总金额
     * @param invest
     */
    public void setInvest(BigDecimal invest) {
        this.invest = invest;
    }

    /**
     * 还款本金
     * @return principal
     */
    public BigDecimal getPrincipal() {
        return principal;
    }

    /**
     * 还款本金
     * @param principal
     */
    public void setPrincipal(BigDecimal principal) {
        this.principal = principal;
    }

    /**
     * 还款利息
     * @return interest
     */
    public BigDecimal getInterest() {
        return interest;
    }

    /**
     * 还款利息
     * @param interest
     */
    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    /**
     * 使用备付金投资
     * @return provisionInvest
     */
    public BigDecimal getProvisionInvest() {
        return provisionInvest;
    }

    /**
     * 使用备付金投资
     * @param provisionInvest
     */
    public void setProvisionInvest(BigDecimal provisionInvest) {
        this.provisionInvest = provisionInvest;
    }

    /**
     * 备付金还款本金
     * @return provisionPrincipal
     */
    public BigDecimal getProvisionPrincipal() {
        return provisionPrincipal;
    }

    /**
     * 备付金还款本金
     * @param provisionPrincipal
     */
    public void setProvisionPrincipal(BigDecimal provisionPrincipal) {
        this.provisionPrincipal = provisionPrincipal;
    }

    /**
     * 备付金还款利息
     * @return provisionInterest
     */
    public BigDecimal getProvisionInterest() {
        return provisionInterest;
    }

    /**
     * 备付金还款利息
     * @param provisionInterest
     */
    public void setProvisionInterest(BigDecimal provisionInterest) {
        this.provisionInterest = provisionInterest;
    }

    /**
     * 当日账户余额
     * @return balance
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * 当日账户余额
     * @param balance
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
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
}