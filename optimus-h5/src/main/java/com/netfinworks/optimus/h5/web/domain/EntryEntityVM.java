package com.netfinworks.optimus.h5.web.domain;

import com.netfinworks.optimus.domain.enums.OrderType;
import com.netfinworks.optimus.entity.EntryEntity;
import com.netfinworks.optimus.utils.BeanUtil;
import com.netfinworks.optimus.utils.FormatUtil;

@SuppressWarnings("serial")
public class EntryEntityVM extends EntryEntity {

	private String orderTypeV;
	private String amountV;
	private String afterBalanceV;
	// 前端显示时的类名称
	private String className;
	// 交易时间
	private String dateV;
	// 查询明细信息
	private String detail;

	public EntryEntityVM(EntryEntity entity) {
		BeanUtil.copyProperties(this, entity);
		setAmountV(getDc() + FormatUtil.formatRate(getAmount()));
		setAfterBalanceV(FormatUtil.formatRate(getAfterBalance()));
		setOrderTypeV(OrderType.getByCode(getOrderType()).getType());
		setDateV(FormatUtil.formatDateTime(getCreateTime()));
		if ("+".equals(getDc())) {
			setClassName("i-color-success");
		} else if ("-".equals(getDc())) {
			setClassName("i-color-error");
		}

	}

	public String getDetail() {
		return detail;
	}


	public void setDetail(String detail) {
		this.detail = detail;
	}


	public String getDateV() {
		return dateV;
	}

	public void setDateV(String dateV) {
		this.dateV = dateV;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
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
