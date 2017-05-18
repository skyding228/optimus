package com.netfinworks.optimus.admin.web.domain;

import com.netfinworks.optimus.domain.AccountInfo;
import com.netfinworks.optimus.entity.MemberEntity;
import com.netfinworks.optimus.utils.BeanUtil;

public class MemberEntityVM extends MemberEntity {

	public MemberEntityVM(MemberEntity member) {
		BeanUtil.copyProperties(this, member);
	}

	private AccountInfo accountInfo;

	public AccountInfo getAccountInfo() {
		return accountInfo;
	}

	public void setAccountInfo(AccountInfo accountInfo) {
		this.accountInfo = accountInfo;
	}

}
