package com.netfinworks.optimus.domain;

public class UserInfo {
	/**
	 * UserInfo { memberId (string): 会员编号 , memberName (string): 用于显示的会员昵称 ,
	 * email (string, optional): 邮箱地址 , telphone (string, optional): 手机号 ,
	 * realName (string, optional): 真实姓名 , idNumber (string, optional): 身份证编号 }
	 */
	private String memberId, memberName, email, telphone, realName, idNumber;

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

}
