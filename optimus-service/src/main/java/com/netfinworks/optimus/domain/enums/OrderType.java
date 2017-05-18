package com.netfinworks.optimus.domain.enums;

/**
 * 订单类型
 * 
 * @author weichunhe
 *
 */
public enum OrderType {

	DEPOSIT("充值", "deposit"), WITHDRAW("提现", "withdraw"), APPLY("申购", "apply"), REDEEM(
			"赎回", "redeem"), BUY("投资", "buy"), REFUND("退款", "refund"), RECOVERY(
			"还款", "recovery"), MANUAL_IN("入款", "manual_in"), MANUAL_OUT(
			"出款", "manual_out"), CHAN_MANUAL_IN("渠道入款", "chan_manual_in") // 渠道手工入款
	, CHAN_MANUAL_OUT("渠道出款", "chan_manual_out");// 渠道手工出款

	private String type;
	private String value;

	OrderType(String type, String value) {
		this.type = type;
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

	public String getType() {
		return this.type;
	}

	public static OrderType getByCode(String code) {
		for (OrderType value : OrderType.values()) {
			if (value.value.equals(code)) {
				return value;
			}
		}
		return null;
	}

	/**
	 * 生成 操作符号
	 * 
	 * @param orderType
	 * @return
	 */
	public static String getBalanceDC(String orderType) {

		if (DEPOSIT.getValue().equals(orderType)) {
			return "+";
		} else if (WITHDRAW.getValue().equals(orderType)) {
			return "-";
		} else if (APPLY.getValue().equals(orderType)) {
			return "-";
		} else if (REDEEM.getValue().equals(orderType)) {
			return "+";
		} else if (BUY.getValue().equals(orderType)) {
			return "-";
		} else if (REFUND.getValue().equals(orderType)) {
			return "+";
		} else if (RECOVERY.getValue().equals(orderType)) {
			return "+";
		} else if (MANUAL_IN.getValue().equals(orderType)) {
			return "+";
		} else if (MANUAL_OUT.getValue().equals(orderType)) {
			return "-";
		} else if (CHAN_MANUAL_IN.getValue().equals(orderType)) {
			return "-";
		} else if (CHAN_MANUAL_OUT.getValue().equals(orderType)) {
			return "+";
		} else {
			return null;
		}

	}
}
