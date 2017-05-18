package com.netfinworks.optimus;

import java.math.BigDecimal;

import com.netfinworks.optimus.domain.enums.OrderType;

public class PayRequest {
	 /** product id */
    private String productId;
    
//	 /** 渠道id */
//    private String chanId;
//    /** 渠道用户id */
//    private String chanUserId;
//    /** 会员id */
//    private String memberId;
    
    /** 金额 */
    private BigDecimal amount;
    /** 外部订单 */
    private String outerOrderId ;
    /** 外部订单 */
    private String origOuterOrderId ;
    /** 返回url */
    private String notifyUrl ;
    /** 备注 */
    private String memo ;
    /** 类型 */
    private OrderType orderType ;
     
    private String ext1 ;
    private String ext2 ;

//	public String getChanId() {
//		return chanId;
//	}
//
//	public void setChanId(String chanId) {
//		this.chanId = chanId;
//	}
//
//	public String getChanUserId() {
//		return chanUserId;
//	}
//
//	public void setChanUserId(String chanUserId) {
//		this.chanUserId = chanUserId;
//	}
//
//	public String getMemberId() {
//		return memberId;
//	}
//
//	public void setMemberId(String memberId) {
//		this.memberId = memberId;
//	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getOuterOrderId() {
		return outerOrderId;
	}

	public void setOuterOrderId(String outerOrderId) {
		this.outerOrderId = outerOrderId;
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

	public String getOrigOuterOrderId() {
		return origOuterOrderId;
	}

	public void setOrigOuterOrderId(String origOuterOrderId) {
		this.origOuterOrderId = origOuterOrderId;
	}

	public OrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(OrderType orderType) {
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
