package com.netfinworks.optimus.request;

public class MemberQueryRequest {
	private String chanId;
	private String chanName;
	private String memberId;
	private String mobile;

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getChanId() {
		return chanId;
	}

	public void setChanId(String chanId) {
		this.chanId = chanId;
	}

	public String getChanName() {
		return chanName;
	}

	public void setChanName(String chanName) {
		this.chanName = chanName;
	}

}
