package com.netfinworks.optimus.h5.web.domain;

import java.util.HashMap;
import java.util.Map;

import com.netfinworks.invest.entity.InvestSubjectInfoResult;
import com.netfinworks.optimus.entity.MemberEntity;

/**
 * 投资合同
 * 
 * @author weichunhe create at 2016年4月26日
 */
public class InvestContract {
	// 借出人
	private MemberEntity member;

	// 产品相关信息
	private InvestSubjectInfoResult subject;
	/**
	 * 借入人： 营业执照号： 法人姓名： 法人身份证： 手续费：借款金额的 ‰，若服务费金额不足一分钱的将按一分钱收取。 名称、协议编号
	 * 
	 * 
	 * borrower businessLicense legalPerson legalPersonCert fee name protocolNo
	 */
	private Map<String, String> exts = new HashMap<String, String>();

	private String amountV, startDateV, endDateV, loanDays, rewardRateV;

	public Map<String, String> getExts() {
		return exts;
	}

	public void setExts(Map<String, String> exts) {
		this.exts = exts;
	}

	public String getRewardRateV() {
		return rewardRateV;
	}

	public void setRewardRateV(String rewardRateV) {
		this.rewardRateV = rewardRateV;
	}

	public MemberEntity getMember() {
		return member;
	}

	public void setMember(MemberEntity member) {
		this.member = member;
	}

	public InvestSubjectInfoResult getSubject() {
		return subject;
	}

	public void setSubject(InvestSubjectInfoResult subject) {
		this.subject = subject;
	}

	public String getAmountV() {
		return amountV;
	}

	public void setAmountV(String amountV) {
		this.amountV = amountV;
	}

	public String getStartDateV() {
		return startDateV;
	}

	public void setStartDateV(String startDateV) {
		this.startDateV = startDateV;
	}

	public String getEndDateV() {
		return endDateV;
	}

	public void setEndDateV(String endDateV) {
		this.endDateV = endDateV;
	}

	public String getLoanDays() {
		return loanDays;
	}

	public void setLoanDays(String loanDays) {
		this.loanDays = loanDays;
	}

}
