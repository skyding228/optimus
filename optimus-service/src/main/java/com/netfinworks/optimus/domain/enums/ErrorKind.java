/*
 * Copyright 2013 netfinworks.com, Inc. All rights reserved.
 */
package com.netfinworks.optimus.domain.enums;

/**
 * <p>
 * </p>
 *
 * @author zhangjiewen
 * @version $Id: ExceptionKind.java, v 0.1 13-11-28 下午2:08 zhangjiewen Exp $
 */
public enum ErrorKind {
	INVOKE_SUCCESS("S0001", "调用成功"), VERIFY_SIGN_FAIL("F0099", "验签失败"), VALIDATION_ERROR(
			"F1001", "数据校验错误"), PERSIST_ERROR("F1002", "数据存储错误"), ILLEGAL_MEMBER_ACCOUNT(
			"F1003", "非法会员账号"), INTEGRATION_ERROR("F1004", "调用外部系统错误"), RESPONSE_NOTFOUND(
			"F1006", "没有找到相应对象"), ASYNC_ERROR("F1007", "异步执行错误"), BALANCE_NOT_ENOUGH(
			"F1008", "账户余额不足"), ILLEGAL_ACCOUNT_STATUS("F1011", "账户状态不正确"), PHONE_NOT_FOUND(
			"F1012", "账户手机号不存在"), SMS_NOTIFY_ERROR("F1009", "短信通知错误"), ILLEGAL_INVEST_SUBJECT_STATUS(
			"F1013", "标的状态不正确"),

	QUERYING_ACCOUNT_INFO_UNKNOWN_ERROR("F1014", "获取账户余额时发生异常"), QUERYING_ACCOUNT_BY_MEMBER_ID_UNKNOWN_ERROR(
			"F1015", "根据会员编码获取账户号发生错误"), QUERYING_ACCOUNT_INFO_RESULT_NOT_CORRECT(
			"F1016", "未获取到正确的账户信息"), TRADESERVICE_CREATEANDPAY_UNKNOWN_ERROR(
			"F1017", "调用下单支付发生未知异常"), TRADESERVICE_CREATEANDPAY_RESULT_NOT_CORRECT(
			"F1018", "调用交易服务结果应答失败"), SUBJECT_OVER_DATE("F1019", "标的已过期"), BID_AMOUNT_NOT_VALID(
			"F1020", "投标金额不合法"), BID_BEGIN_DATE_NOT_REACHED("F1021", "未到投标日期"), BIDDER_BALANCE_NOT_ENOUGTH(
			"F1022", "投资人余额不足"), SUBJECT_STATUS_NOT_VALID("F1023", "标的状态不合法"), SUBJECT_UPDATE_FAILURE(
			"F1024", "标的更新失败"), BID_UPDATE_FAILURE("F1025", "投标订单更新失败"), SUBJECT_CANNOT_BE_BIDDED(
			"F1029", "该标的不能投标"), // 原来为F1026
	SUBJECT_IS_BIDFULL("F1027", "该标的已投满，不能投标"), NO_BID_SUCCESS_RECORD("F1028",
			"未投资"), DB_DML_ERROR("F1030", "数据操作失败"),

	LOAN_FUND_APPLY_NOTIFY_RESPONSE_ERROR("F1026", "贷款/放款服务应答失败"),

	CREDIT_STATUS_NOT_VALID("F1040", "债权状态不合法"),

	UNKNOWN_ERROR("F9999", "未知异常");

	private String code;

	private String msg;

	private ErrorKind(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public static ErrorKind getByCode(String code) {
		for (ErrorKind value : ErrorKind.values()) {
			if (value.code.equals(code)) {
				return value;
			}
		}
		return null;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public boolean equals(String code) {
		return getCode().equals(code);
	}
}