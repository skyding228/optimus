package com.netfinworks.optimus.domain.enums;

public enum InvestOrderStatusEnum implements EnumBase {
	// BID_SUCCESS("BID_SUCCESS", "投资成功"), PAY_SUCCESS("PAY_SUCCESS", "交易成功");
	INIT("INIT", "初始状态"), PAY_SUCCESS("PAY_SUCCESS", "支付成功"), REFUNDING(
			"REFUNDING", "退款中"), REFUND_SUCCESS("REFUND_SUCCESS", "退款成功"), BID_SUCCESS(
			"BID_SUCCESS", "投资成功"), BID_FAIL("BID_FAIL", "投资失败"), ;
	private InvestOrderStatusEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}

	private String code;
	private String message;

	public String getCode() {
		return code;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
