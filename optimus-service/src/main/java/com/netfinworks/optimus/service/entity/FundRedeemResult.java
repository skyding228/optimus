/**
 * 
 */
package com.netfinworks.optimus.service.entity;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.netfinworks.optimus.domain.enums.RedeemOrderStateKind;

/**
 *
 */
public class FundRedeemResult {
	private RedeemOrderStateKind redeemOrderStateKind;
	
	private String redemptionOrderNo;

	public String getRedemptionOrderNo() {
		return redemptionOrderNo;
	}

	public void setRedemptionOrderNo(String redemptionOrderNo) {
		this.redemptionOrderNo = redemptionOrderNo;
	}
	
	public RedeemOrderStateKind getRedeemOrderStateKind() {
		return redeemOrderStateKind;
	}

	public void setRedeemOrderStateKind(RedeemOrderStateKind redeemOrderStateKind) {
		this.redeemOrderStateKind = redeemOrderStateKind;
	}
	
	public static FundRedeemResult result(String redemptionOrderNo, RedeemOrderStateKind state) {
		FundRedeemResult fundRedeemResult = new FundRedeemResult();
		fundRedeemResult.setRedemptionOrderNo(redemptionOrderNo);
		fundRedeemResult.setRedeemOrderStateKind(state);
		return fundRedeemResult;
	}

	@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
    }
	
}
