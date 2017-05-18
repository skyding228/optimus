package com.netfinworks.optimus.service.entity;

import java.util.List;

import com.netfinworks.optimus.entity.EntryEntity;
import com.netfinworks.optimus.utils.BeanUtil;

public class QueryEntryResponse extends QueryPagingResult {

	/**
	 * 记录
	 */
	private List<EntryEntity> result;

	public QueryEntryResponse(EntryQueryResult result) {
		BeanUtil.copyProperties(this, result.getPagingResult());
		setResult(result.getEntryList());
	}

	public List<EntryEntity> getResult() {
		return result;
	}

	public void setResult(List<EntryEntity> result) {
		this.result = result;
	}

}
