package com.netfinworks.optimus.entity;

import java.math.BigDecimal;
import java.util.Date;

public class AccountEntity extends BaseEntity {
    /** 账号id */
    private String accountId;

    /** 名称 */
    private String accountName;

    /** 账户科目号 */
    private String accountTitleNo;

    /** 余额 */
    private BigDecimal balance;

    /** 借方余额 */
    private BigDecimal debitbalance;

    /** 贷方余额 */
    private BigDecimal creditbalance;

    /** D：借方  C：贷方 */
    private String balanceDirection;

    /** 冻结余额 */
    private BigDecimal frozenBalance;

    /**  */
    private Date createTime;

    /**  */
    private Date updateTime;

    /**
     * 账号id
     * @return accountId
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     * 账号id
     * @param accountId
     */
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    /**
     * 名称
     * @return accountName
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * 名称
     * @param accountName
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    /**
     * 账户科目号
     * @return accountTitleNo
     */
    public String getAccountTitleNo() {
        return accountTitleNo;
    }

    /**
     * 账户科目号
     * @param accountTitleNo
     */
    public void setAccountTitleNo(String accountTitleNo) {
        this.accountTitleNo = accountTitleNo;
    }

    /**
     * 余额
     * @return balance
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * 余额
     * @param balance
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    /**
     * 借方余额
     * @return debitbalance
     */
    public BigDecimal getDebitbalance() {
        return debitbalance;
    }

    /**
     * 借方余额
     * @param debitbalance
     */
    public void setDebitbalance(BigDecimal debitbalance) {
        this.debitbalance = debitbalance;
    }

    /**
     * 贷方余额
     * @return creditbalance
     */
    public BigDecimal getCreditbalance() {
        return creditbalance;
    }

    /**
     * 贷方余额
     * @param creditbalance
     */
    public void setCreditbalance(BigDecimal creditbalance) {
        this.creditbalance = creditbalance;
    }

    /**
     * D：借方  C：贷方
     * @return balanceDirection
     */
    public String getBalanceDirection() {
        return balanceDirection;
    }

    /**
     * D：借方  C：贷方
     * @param balanceDirection
     */
    public void setBalanceDirection(String balanceDirection) {
        this.balanceDirection = balanceDirection;
    }

    /**
     * 冻结余额
     * @return frozenBalance
     */
    public BigDecimal getFrozenBalance() {
        return frozenBalance;
    }

    /**
     * 冻结余额
     * @param frozenBalance
     */
    public void setFrozenBalance(BigDecimal frozenBalance) {
        this.frozenBalance = frozenBalance;
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
}