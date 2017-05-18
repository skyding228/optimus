package com.netfinworks.optimus.request;
/**
 *公管账户账户操作 
 */
public class ChanPublicAccountManualBookRequest {
	private String chanId;
	private String chanMemberId;
	private String chanPublicAccountId;
	
	private String orderType; // manual_in manual_out
	private String outerOrderId;//凭证号
	private String amount;
	private String memo;
	
	private String bookPaymentId;

	private String operation; // 操作员

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getChanId() {
		return chanId;
	}

	public void setChanId(String chanId) {
		this.chanId = chanId;
	}

	public String getOuterOrderId() {
		return outerOrderId;
	}

	public void setOuterOrderId(String outerOrderId) {
		this.outerOrderId = outerOrderId;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getChanMemberId() {
		return chanMemberId;
	}

	public void setChanMemberId(String chanMemberId) {
		this.chanMemberId = chanMemberId;
	}

	public String getChanPublicAccountId() {
		return chanPublicAccountId;
	}

	public void setChanPublicAccountId(String chanPublicAccountId) {
		this.chanPublicAccountId = chanPublicAccountId;
	}

	public String getBookPaymentId() {
		return bookPaymentId;
	}

	public void setBookPaymentId(String bookPaymentId) {
		this.bookPaymentId = bookPaymentId;
	}

}
