package com.netfinworks.optimus.request;

/**
 * 公管账户审核
 */
public class ChanPublicAccountAuditRequest {

	private String[] orderIdList;
	private String operation; // 审核员
	public String[] getOrderIdList() {
		return orderIdList;
	}
	public void setOrderIdList(String[] orderIdList) {
		this.orderIdList = orderIdList;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}

	
}
