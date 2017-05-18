package com.netfinworks.optimus.domain.enums;

/**
 * 
 * <p>
 * 注释
 * </p>
 * 
 * @author Yuyizhao
 * @version $Id: ReturnCode.java, v 0.1 2015年1月16日 下午2:59:30 yuyizhao Exp $
 */
public enum ReturnCode {

	SUCCESS("S0001", "处理成功"), EXCEPTION("E0001", "处理异常"), PARAMETER_INVALID(
			"F0101", "请求参数不正确"), BUSINESS_INVALID("F0102", "业务校验不正确"), ILLEGAL_STATE(
			"F1013", "状态检验不正确"), FAIL("F9999", "处理失败");
	private String message;
	private String code;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	ReturnCode(String code, String message) {
		this.message = message;
		this.code = code;
	}

}
