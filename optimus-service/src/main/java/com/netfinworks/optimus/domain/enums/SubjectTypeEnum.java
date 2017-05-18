/*
 * netfinworks.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */
package com.netfinworks.optimus.domain.enums;

public enum SubjectTypeEnum implements EnumBase {
	NORMAL("0", "公开标"), YOUNG("1", "新手标"), VIP("2", "VIP标"), INNER("3", "内部标"), SELF_FINANCE(
			"4", "自助金融"),
	/** 虚拟标的，用于拆标的主标的，客户端不显示。数据库字段最大是一个字符，不能使用多位数。 */
	VIRTUAL("V", "虚拟标"), ;

	private SubjectTypeEnum(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public static SubjectTypeEnum getByCode(String code) {
		for (SubjectTypeEnum value : SubjectTypeEnum.values()) {
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

	@Override
	public String getMessage() {
		return msg;
	}

	public void setCode(String code) {
		this.code = code;
	}

	private String msg;

}