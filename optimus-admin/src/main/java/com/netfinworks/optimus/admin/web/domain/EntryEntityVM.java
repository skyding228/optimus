package com.netfinworks.optimus.admin.web.domain;

import com.netfinworks.optimus.domain.enums.OrderType;
import com.netfinworks.optimus.entity.EntryEntity;
import com.netfinworks.optimus.utils.BeanUtil;
import com.netfinworks.optimus.utils.FormatUtil;

@SuppressWarnings("serial")
public class EntryEntityVM extends EntryEntity {

	private String orderTypeV;
	private String amountV;
	private String afterBalanceV;

	public EntryEntityVM(EntryEntity entity) {
		BeanUtil.copyProperties(this, entity);
		setAmountV(FormatUtil.formatRate(getAmount()));
		setAfterBalanceV(FormatUtil.formatRate(getAfterBalance()));
		setOrderTypeV(OrderType.getByCode(getOrderType()).getType());
	}

	public String getAmountV() {
		return amountV;
	}

	public void setAmountV(String amountV) {
		this.amountV = amountV;
	}

	public String getAfterBalanceV() {
		return afterBalanceV;
	}

	public void setAfterBalanceV(String afterBalanceV) {
		this.afterBalanceV = afterBalanceV;
	}

	public String getOrderTypeV() {
		return orderTypeV;
	}

	public void setOrderTypeV(String orderTypeV) {
		this.orderTypeV = orderTypeV;
	}

}
