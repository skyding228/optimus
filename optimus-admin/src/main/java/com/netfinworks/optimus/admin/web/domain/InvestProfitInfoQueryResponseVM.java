package com.netfinworks.optimus.admin.web.domain;

import com.netfinworks.invest.response.InvestProfitInfoQueryResponse;
import com.netfinworks.optimus.utils.BeanUtil;

public class InvestProfitInfoQueryResponseVM extends
		InvestProfitInfoQueryResponse {

	public InvestProfitInfoQueryResponseVM(
			InvestProfitInfoQueryResponse response) {
		BeanUtil.copyProperties(this, response);
		if (getTotalInterest() != null) {
			setTotalInterestV(getTotalInterest().format());
		}
		if (getTotalPrincipalAndInterest() != null) {
			setTotalPrincipalAndInterestV(getTotalPrincipalAndInterest()
					.format());
		}
	}

	private String totalPrincipalAndInterestV;
	private String totalInterestV;

	public String getTotalPrincipalAndInterestV() {
		return totalPrincipalAndInterestV;
	}

	public void setTotalPrincipalAndInterestV(String totalPrincipalAndInterestV) {
		this.totalPrincipalAndInterestV = totalPrincipalAndInterestV;
	}

	public String getTotalInterestV() {
		return totalInterestV;
	}

	public void setTotalInterestV(String totalInterestV) {
		this.totalInterestV = totalInterestV;
	}

}
