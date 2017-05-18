/*
 * netfinworks.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */
package com.netfinworks.optimus.domain.enums;

/**
 * 还款方式
 * 
 * @author weichunhe
 *
 */
public enum RepayTypeKind implements EnumBase {
	MONTHLY_PAYMENT("1", "按月付息到期还本"), MATCHING_REPAYMENT("2", "等额本息"), AVERAGE_CAPITAL(
			"3", "等额本金"), DISPOSABLE_PAYOFF("4", "利随本清"), BORROW_PAYBACK("5",
			"随借随还");

	private RepayTypeKind(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public static RepayTypeKind getByCode(String code) {
		for (RepayTypeKind value : RepayTypeKind.values()) {
			if (value.code.equals(code)) {
				return value;
			}
		}
		return null;
	}

	public boolean equals(String code) {
		return getCode().equals(code);
	}

	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	private String msg;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getMessage() {
		return msg;
	}
}