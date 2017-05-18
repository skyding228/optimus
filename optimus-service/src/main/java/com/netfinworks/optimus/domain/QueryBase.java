package com.netfinworks.optimus.domain;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * <p>
 * 返回查询钱包对账单页面
 * </p>
 * 
 * @author zhangyun.m
 * @version $Id: WalletCheckResponse.java, v 0.1 2013-12-12 下午3:17:32 HP Exp $
 */
public class QueryBase implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer totalItem;
	private Integer pageSize = 20000;
	private Integer currentPage = 1;

	public Integer getTotalItem() {
		return totalItem;
	}

	public void setTotalItem(Integer totalItem) {
		this.totalItem = totalItem;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
