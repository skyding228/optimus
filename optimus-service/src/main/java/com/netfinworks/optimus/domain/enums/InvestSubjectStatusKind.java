package com.netfinworks.optimus.domain.enums;

/**
 * <p>
 * </p>
 *
 * @author zhangjiewen
 * @version $Id: InvestSubjectStatusKind.java, v 1.0 17:20 zhangjiewen Exp $
 */
public enum InvestSubjectStatusKind implements EnumBase {
	INIT("01", "未投标"), APPROVE_PASS("03", "审批通过"), APPROVE_REJECT("04", "审批失败"), BIDDING(
			"05", "招标中"), HANDLEBIDFULL("08", "满标处理中"), BIDFULL("10", "已满标"), BID_FULL_REJECT(
			"13", "满标后流标"), REPAYING("15", "还款中"), FINISH("20", "已完结"), OVERDUE(
			"99", "流标"), CANCELED("50", "已取消"), ;

	private InvestSubjectStatusKind(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public static InvestSubjectStatusKind getByCode(String code) {
		for (InvestSubjectStatusKind value : InvestSubjectStatusKind.values()) {
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

	@Override
	public String getMessage() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
