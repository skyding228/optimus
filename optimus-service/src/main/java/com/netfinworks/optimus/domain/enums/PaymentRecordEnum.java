package com.netfinworks.optimus.domain.enums;

/**
 * 出入款记录
 * 
 * @author weichunhe create at 2016年4月1日
 */
public enum PaymentRecordEnum {
	PAYMENT_OUT("PAYMENT_OUT", "出款"), PAYMENT_IN("PAYMENT_IN", "入款"), ACTION_LOAN(
			"ACTION_LOAN", "放款"), ACTION_REPAYMENT("ACTION_REPAYMENT", "还款"), ACTION_APPLY(
			"ACTION_APPLY", "申购"), ACTION_REDEEM("ACTION_REDEEM", "赎回"), ACTION_DEPOSIT(
			"ACTION_DEPOSIT", "充值"), ACTION_WITHDRAW("ACTION_WITHDRAW", "提现"), ACTION_PROVISION_INVEST(
			"ACTION_PROVISION_INVEST", "备付金投资"), ACTION_PROVISION_REPAYMENT(
			"ACTION_PROVISION_REPAYMENT", "备付金还款"), SUBJECT("SUBJECT", "定期产品"), FUND(
			"FUND", "基金"), ACCOUNT("ACCOUNT", "充值提现"), VFINANCE("VFINANCE",
			"维金");

	private String desc;
	private String value;

	public String getDesc() {
		return desc;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	private PaymentRecordEnum(String value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	// public static final String PaymentTypeOut = "出款";
	// public static final String PaymentTypeIn = "入款";
	//
	// public static final String PaymentTypeOutValue = "out";
	// public static final String PaymentTypeInValue = "in";
	//
	// public static final String ActionTypeApply = "申购";
	// public static final String ActionTypeRedeem = "赎回";
	// public static final String ActionTypeOut = "放款";
	// public static final String ActionTypeIn = "还款";
	//
	// public static final String TypeFund = "fund";
	// public static final String TypeSubject = "subject";
}
