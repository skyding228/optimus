package com.netfinworks.optimus.domain.enums;

/**
 * 订单状态
 * 
 * @author weichunhe
 *
 */
public enum OrderStatus {

	NONE("", ""), INIT("初始", "I"), PROCESS("处理中", "P"), SUCCESS("成功", "S"), FAIL(
			"失败", "F");

	private String type;
	private String value;

	private OrderStatus(String type, String value) {
		this.type = type;
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

	public String getType() {
		return this.type;
	}

	public static OrderStatus getByValue(String value) {
		for (OrderStatus status : OrderStatus.values()) {
			if (status.getValue().equals(value)) {
				return status;
			}
		}
		return null;
	}

}
