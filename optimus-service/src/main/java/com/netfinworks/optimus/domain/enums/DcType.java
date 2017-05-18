/*
 * netfinworks.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */
package com.netfinworks.optimus.domain.enums;

import java.util.Arrays;
import java.util.List;

public enum DcType {
	Add("+", "增加"),
	Sub("-", "减少")
	;
	
	private DcType(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public static DcType getByCode(String code) {
		for (DcType value : DcType.values()) {
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

	public static List<DcType> getAll() {
		return Arrays.asList(values());
	}

}