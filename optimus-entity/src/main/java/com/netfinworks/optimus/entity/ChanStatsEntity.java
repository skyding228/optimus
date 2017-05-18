package com.netfinworks.optimus.entity;

import java.math.BigDecimal;

public class ChanStatsEntity extends BaseEntity{
	/** 用户数*/
    private Integer cnt;
    /** 余额 */
    private BigDecimal balance;
    
	public Integer getCnt() {
		return cnt;
	}
	public void setCnt(Integer cnt) {
		this.cnt = cnt;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
    
    
}
