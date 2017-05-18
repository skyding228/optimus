package com.netfinworks.optimus.admin.web.domain;

import com.netfinworks.optimus.domain.enums.OrderStatus;
import com.netfinworks.optimus.domain.enums.OrderType;
import com.netfinworks.optimus.entity.MemberEntity;
import com.netfinworks.optimus.entity.OrderEntity;
import com.netfinworks.optimus.utils.BeanUtil;
import com.netfinworks.optimus.utils.FormatUtil;

@SuppressWarnings("serial")
public class OrderEntiryVM extends OrderEntity {

	private String amountV;
	private String orderStatusV;
	private String orderTypeV;
	private String userName, userId;

	public OrderEntiryVM(OrderEntity entity) {
		BeanUtil.copyProperties(this, entity);
		setAmountV(FormatUtil.formatRate(getAmount()));
		setOrderStatusV(OrderStatus.getByValue(getOrderStatus()).getType());
		setOrderTypeV(OrderType.getByCode(getOrderType()).getType());
	}

	public OrderEntiryVM(OrderEntity entity, MemberEntity member) {
		this(entity);
		if (member != null) {
			setUserName(member.getChanUserName());
			setUserId(member.getChanUserId());
		}
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOrderTypeV() {
		return orderTypeV;
	}

	public void setOrderTypeV(String orderTypeV) {
		this.orderTypeV = orderTypeV;
	}

	public String getAmountV() {
		return amountV;
	}

	public void setAmountV(String amountV) {
		this.amountV = amountV;
	}

	public String getOrderStatusV() {
		return orderStatusV;
	}

	public void setOrderStatusV(String orderStatusV) {
		this.orderStatusV = orderStatusV;
	}

}
