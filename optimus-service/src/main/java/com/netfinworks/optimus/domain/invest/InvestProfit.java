/**
 * 
 */
package com.netfinworks.optimus.domain.invest;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * <p>投资收益</p>
 * @author wangtq
 * @version $Id: InvestProfit.java, v 0.1 2015年1月20日 上午11:28:02 wangtq Exp $
 */
public class InvestProfit {
    
    /**
     * 还款日期
     */
    private Date repayDate;
    
    /**
     * 本金
     */
    private BigDecimal      principal;
    
    /**
     * 利息
     */
    private BigDecimal      interest;
    
    
    
    public BigDecimal getPrincipal() {
        return principal;
    }

    public void setPrincipal(BigDecimal principal) {
        this.principal = principal;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public Date getRepayDate() {
        return repayDate;
    }

    public void setRepayDate(Date repayDate) {
        this.repayDate = repayDate;
    }
    
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
    
}
