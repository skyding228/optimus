package com.netfinworks.optimus;

import java.math.BigDecimal;

public class RedeemRequest {
	 /** product id */
    private String productId;
	 /** 渠道id */
    private String chanId;

    /** 渠道用户id */
    private String chanUserId;

    /** 会员id */
    private String memberId;

    /** 金额 */
    private BigDecimal amount;

	public String getChanId() {
		return chanId;
	}

	public void setChanId(String chanId) {
		this.chanId = chanId;
	}

	public String getChanUserId() {
		return chanUserId;
	}

	public void setChanUserId(String chanUserId) {
		this.chanUserId = chanUserId;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
    
}
