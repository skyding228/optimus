/*
 * netfinworks.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */
package com.netfinworks.optimus.domain.enums;

import java.util.Arrays;
import java.util.List;

public enum MemberStateKind {	
	
	Normal("Normal", "正常"),
	Lock("Lock", "密码错误锁定"),
	Freeze("Freeze", "风控冻结 ")
	;
	
	private MemberStateKind(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public static MemberStateKind getByCode(String code) {
		for (MemberStateKind value : MemberStateKind.values()) {
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

	public static List<MemberStateKind> getAll() {
		return Arrays.asList(values());
	}

}