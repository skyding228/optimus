package com.netfinworks.optimus.domain;

public class RestResult {
	private int pageCount, pageIndex, pageSize, itemCount;

	private Object result;

	public RestResult() {

	}

	public RestResult(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getItemCount() {
		return itemCount;
	}

	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageCount() {

		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
}
