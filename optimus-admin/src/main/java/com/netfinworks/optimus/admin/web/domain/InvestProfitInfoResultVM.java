package com.netfinworks.optimus.admin.web.domain;

import com.netfinworks.invest.entity.InvestProfitInfoResult;
import com.netfinworks.optimus.entity.MemberEntity;
import com.netfinworks.optimus.utils.BeanUtil;

/**
 * 投资收益信息
 * 
 * @author weichunhe create at 2016年3月24日
 */
public class InvestProfitInfoResultVM extends InvestProfitInfoResult {

	private String userId;
	private String userName;
	// 本金
	private String principalV;
	// 利息
	private String interestV;

	public InvestProfitInfoResultVM(InvestProfitInfoResult info,
			MemberEntity member) {
		BeanUtil.copyProperties(this, info);
		if (member != null) {
			setUserId(member.getChanUserId());
			setUserName(member.getChanUserName());
		}
		setPrincipalV(getPrincipal().format());
		setInterestV(getInterest().format());

	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPrincipalV() {
		return principalV;
	}

	public void setPrincipalV(String principalV) {
		this.principalV = principalV;
	}

	public String getInterestV() {
		return interestV;
	}

	public void setInterestV(String interestV) {
		this.interestV = interestV;
	}

}
