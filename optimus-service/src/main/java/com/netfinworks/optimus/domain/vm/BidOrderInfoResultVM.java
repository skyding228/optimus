package com.netfinworks.optimus.domain.vm;

import com.netfinworks.invest.entity.BidOrderInfoResult;
import com.netfinworks.invest.entity.InvestSubjectInfoResult;
import com.netfinworks.optimus.domain.enums.InvestOrderStatusEnum;
import com.netfinworks.optimus.entity.MemberEntity;
import com.netfinworks.optimus.service.MemberService;
import com.netfinworks.optimus.service.integration.invest.impl.InvestServiceImpl;
import com.netfinworks.optimus.utils.BeanUtil;
import com.netfinworks.optimus.utils.FormatUtil;

/**
 * 投资记录 视图模型
 * 
 * @author weichunhe
 *
 */
public class BidOrderInfoResultVM extends BidOrderInfoResult {

	public BidOrderInfoResultVM(BidOrderInfoResult order,
			MemberService memberService) {
		BeanUtil.copyProperties(this, order);
		setAmountV(getAmount().format());
		setBidTimeV(FormatUtil.formatDate(getBidTime()));
		setMemberIdV(FormatUtil.shuckSensitive(getMemberId()));
		setStatusV(InvestOrderStatusEnum.valueOf(getStatus()).getMessage());
		if (memberService == null) {
			return;
		}
		MemberEntity member = memberService.getMemberEntity(getMemberId());
		if (member != null) {
			setUserId(member.getChanUserId());
			setUserName(member.getChanUserName());
			setUserIdV(FormatUtil.shuckSensitive(getUserId()));
			setUserNameV(FormatUtil.shuckSensitive(getUserName()));
		}
	}

	public BidOrderInfoResultVM(BidOrderInfoResult order,
			MemberService memberService, InvestServiceImpl investService) {
		this(order, memberService);
		if (investService == null) {
			return;
		}
		// 查询 产品名称
		InvestSubjectInfoResult result = investService
				.querySubject(getSubjectNo());
		if (result != null) {
			setSubjectName(result.getSubjectName());
			setRewardRateV(FormatUtil.formatRate(result.getRewardRate()));
			setLoanTerm(result.getLoanTerm());
		}
	}

	// 预计收益
	private String predictProfitV;

	// 格式化后的购买金额
	private String amountV;

	// 购买时间
	private String bidTimeV;

	// 账户id
	private String memberIdV;

	private String statusV;

	private String userId, userName, userIdV, userNameV, subjectName,
			rewardRateV, loanTerm;

	public String getRewardRateV() {
		return rewardRateV;
	}

	public void setRewardRateV(String rewardRateV) {
		this.rewardRateV = rewardRateV;
	}

	public String getLoanTerm() {
		return loanTerm;
	}

	public void setLoanTerm(String loanTerm) {
		this.loanTerm = loanTerm;
	}

	public String getPredictProfitV() {
		return predictProfitV;
	}

	public void setPredictProfitV(String predictProfitV) {
		this.predictProfitV = predictProfitV;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getUserIdV() {
		return userIdV;
	}

	public void setUserIdV(String userIdV) {
		this.userIdV = userIdV;
	}

	public String getUserNameV() {
		return userNameV;
	}

	public void setUserNameV(String userNameV) {
		this.userNameV = userNameV;
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
