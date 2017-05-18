/*
 * netfinworks.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */
package com.netfinworks.optimus.domain.enums;

import java.util.Arrays;
import java.util.List;

public enum ApplyOrderStateKind {
	INIT("INIT", "初始"),
	APPLY_PROCESS("APPLY_PROCESS", "申购中"),
	APPLY_SUCCESS("APPLY_SUCCESS", "申购成功"),
	APPLY_FAILURE("APPLY_FAILURE", "申购失败"),
	APPLY_BACK_PROCESS("APPLY_BACK_PROCESS", "处理中"),
	APPLY_EXCEPTION("APPLY_EXCEPTION", "申购异常");
	
	private ApplyOrderStateKind(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public static ApplyOrderStateKind getByCode(String code) {
		for (ApplyOrderStateKind value : ApplyOrderStateKind.values()) {
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

	public static List<ApplyOrderStateKind> getAll() {
		return Arrays.asList(values());
	}

}