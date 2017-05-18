/*
 * netfinworks.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */
package com.netfinworks.optimus.service.entity;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.netfinworks.optimus.entity.EntryEntity;

public class EntryQueryResult {

	private QueryPagingResult pagingResult = new QueryPagingResult();
	/**
	 * 记录
	 */
	private List<EntryEntity> entryList;

	public QueryPagingResult getPagingResult() {
		return pagingResult;
	}

	public void setPagingResult(QueryPagingResult pagingResult) {
		this.pagingResult = pagingResult;
	}

	public List<EntryEntity> getEntryList() {
		return entryList;
	}

	public void setEntryList(List<EntryEntity> entryList) {
		this.entryList = entryList;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
	}
}
