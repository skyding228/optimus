package com.netfinworks.optimus.request;

public class MemberPayRequest {
	private String outerOrderId;
	private String origOuterOrderId;
	private String memberId;
	private String amount;
	private String notifyUrl;
	private String memo;
	private String productId;
	private String orderType; //buy 投标 refund 退款
	
	private String ext1;
	private String ext2;
	
	public String getOuterOrderId() {
		return outerOrderId;
	}
	public void setOuterOrderId(String outerOrderId) {
		this.outerOrderId = outerOrderId;
	}
	public String getOrigOuterOrderId() {
		return origOuterOrderId;
	}
	public void setOrigOuterOrderId(String origOuterOrderId) {
		this.origOuterOrderId = origOuterOrderId;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getExt1() {
		return ext1;
	}
	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}
	public String getExt2() {
		return ext2;
	}
	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}
	

	
}
