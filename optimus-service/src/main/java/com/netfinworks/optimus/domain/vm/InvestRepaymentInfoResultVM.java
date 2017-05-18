package com.netfinworks.optimus.domain.vm;

import com.netfinworks.invest.entity.InvestRepaymentInfoResult;
import com.netfinworks.optimus.domain.enums.InvestSubjectStatusKind;
import com.netfinworks.optimus.utils.BeanUtil;
import com.netfinworks.optimus.utils.FormatUtil;

public class InvestRepaymentInfoResultVM extends InvestRepaymentInfoResult {

	public InvestRepaymentInfoResultVM(InvestRepaymentInfoResult result) {
		BeanUtil.copyProperties(this, result);
		setAmountV(getExpectedRecovery().format());
		setStatusV(InvestSubjectStatusKind.getByCode(getSubjectStatus())
				.getMessage());
		setTotalAmountV(getTotalPrincipalAndInterest().subtract(
				getTotalInterest()).format());
		setTotalInterestV(getTotalInterest().format());
		setRewardRateV(FormatUtil.formatRate(getRewardRate()));
	}

	private String predictRepayDateV;// 预计还款时间

	private String amountV;

	private String statusV, rewardRateV;

	public String getRewardRateV() {
		return rewardRateV;
	}

	public void setRewardRateV(String rewardRateV) {
		this.rewardRateV = rewardRateV;
	}

	// 已完结的
	private String totalInterestV; // 收益
	private String totalAmountV;

	public String getTotalInterestV() {
		return totalInterestV;
	}

	public void setTotalInterestV(String totalInterestV) {
		this.totalInterestV = totalInterestV;
	}

	public String getTotalAmountV() {
		return totalAmountV;
	}

	public void setTotalAmountV(String totalAmountV) {
		this.totalAmountV = totalAmountV;
	}

	public String getAmountV() {
		return amountV;
	}

	public String getPredictRepayDateV() {
		return predictRepayDateV;
	}

	public void setPredictRepayDateV(String predictRepayDateV) {
		this.predictRepayDateV = predictRepayDateV;
	}

	public void setAmountV(String amountV) {
		this.amountV = amountV;
	}

	public String getStatusV() {
		return statusV;
	}

	public void setStatusV(String statusV) {
		this.statusV = statusV;
	}

}
