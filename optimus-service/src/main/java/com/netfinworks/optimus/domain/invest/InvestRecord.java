
package com.netfinworks.optimus.domain.invest;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * <p>投资记录</p>
 * @author wangtq
 * @version $Id: InvestRecord.java, v 0.1 2015年1月20日 上午11:28:02 wangtq Exp $
 */
public class InvestRecord {
    
    /**
     * 投标时间
     */
    private Date investDate;
    
    /**
     * 投标人memberId
     */
    private String   memberId;
    
    /**
     * 投标金额
     */
    private BigDecimal   bidAmount;
    
    /**
     * 投标冻结金额
     */
   private BigDecimal totalFreezeAmt ; 
    
    
    
    public BigDecimal getTotalFreezeAmt() {
        return totalFreezeAmt;
    }
    public void setTotalFreezeAmt(BigDecimal totalFreezeAmt) {
        this.totalFreezeAmt = totalFreezeAmt;
    }
    public Date getInvestDate() {
        return investDate;
    }
    public void setInvestDate(Date investDate) {
        this.investDate = investDate;
    }
    public String getMemberId() {
        return memberId;
    }
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
    public BigDecimal getBidAmount() {
        return bidAmount;
    }
    public void setBidAmount(BigDecimal bidAmount) {
        this.bidAmount = bidAmount;
    }





    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
    
}
