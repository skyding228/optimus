package com.netfinworks.optimus.service.entity;

import java.util.Date;

import com.netfinworks.common.lang.Paginator;
import com.netfinworks.enums.OrderType;
import com.netfinworks.optimus.domain.enums.DcType;

public class EntryQueryParams {
	/** 会员编号 */
	private String memberId;
	private String dcType;
	private Date startDate;
	private Date endDate;

	private Paginator paginator = new Paginator();

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getDcType() {
		return dcType;
	}

	public void setDcType(String dcType) {
		this.dcType = dcType;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Paginator getPaginator() {
		return paginator;
	}

	public void setPaginator(Paginator paginator) {
		this.paginator = paginator;
	}

}
