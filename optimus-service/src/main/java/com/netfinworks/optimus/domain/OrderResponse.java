package com.netfinworks.optimus.domain;

public class OrderResponse {

	private String orderId;
	private String originOrderId;
	private long orderTime;
	private String status;
	private String reason;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOriginOrderId() {
		return originOrderId;
	}

	public void setOriginOrderId(String originOrderId) {
		this.originOrderId = originOrderId;
	}

	public long getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(long orderTime) {
		this.orderTime = orderTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
