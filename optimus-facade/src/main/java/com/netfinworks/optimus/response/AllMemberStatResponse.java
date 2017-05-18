package com.netfinworks.optimus.response;

import java.math.BigDecimal;

public class AllMemberStatResponse extends Response {

	private String chan;
	private String chanName;
	private Integer chanUserCnt;
	private BigDecimal chanTotalBalance;

	public String getChan() {
		return chan;
	}

	public void setChan(String chan) {
		this.chan = chan;
	}

	public String getChanName() {
		return chanName;
	}

	public void setChanName(String chanName) {
		this.chanName = chanName;
	}

	public Integer getChanUserCnt() {
		return chanUserCnt;
	}

	public void setChanUserCnt(Integer chanUserCnt) {
		this.chanUserCnt = chanUserCnt;
	}

	public BigDecimal getChanTotalBalance() {
		return chanTotalBalance;
	}

	public void setChanTotalBalance(BigDecimal chanTotalBalance) {
		this.chanTotalBalance = chanTotalBalance;
	}

}
