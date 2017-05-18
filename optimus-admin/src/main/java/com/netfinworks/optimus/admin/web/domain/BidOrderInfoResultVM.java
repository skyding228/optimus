package com.netfinworks.optimus.admin.web.domain;

import com.netfinworks.invest.entity.BidOrderInfoResult;
import com.netfinworks.optimus.domain.enums.InvestOrderStatusEnum;
import com.netfinworks.optimus.entity.MemberEntity;
import com.netfinworks.optimus.utils.BeanUtil;
import com.netfinworks.optimus.utils.FormatUtil;

/**
 * 投资记录 视图模型
 * 
 * @author weichunhe
 *
 */
public class BidOrderInfoResultVM extends BidOrderInfoResult {

	public BidOrderInfoResultVM(BidOrderInfoResult order, MemberEntity member) {
		BeanUtil.copyProperties(this, order);

		setAmountV(getAmount().format());
		setBidTimeV(FormatUtil.formatDate(getBidTime()));
		setMemberIdV(FormatUtil.shuckSensitive(getMemberId()));
		setStatusV(InvestOrderStatusEnum.valueOf(getStatus()).getMessage());
		if (member != null) {
			setUserId(member.getChanUserId());
			setUserName(member.getChanUserName());
		}
	}

	private String userId;
	private String userName;

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

	// 格式化后的购买金额
	private String amountV;

	// 购买时间
	private String bidTimeV;

	// 账户id
	private String memberIdV;

	// 状态
	private String statusV;

	public String getStatusV() {
		return statusV;
	}

	public void setStatusV(String statusV) {
		this.statusV = statusV;
	}

	public String getAmountV() {
		return amountV;
	}

	public void setAmountV(String amountV) {
		this.amountV = amountV;
	}

	public String getBidTimeV() {
		return bidTimeV;
	}

	public void setBidTimeV(String bidTimeV) {
		this.bidTimeV = bidTimeV;
	}

	public String getMemberIdV() {
		return memberIdV;
	}

	public void setMemberIdV(String memberIdV) {
		this.memberIdV = memberIdV;
	}

}
