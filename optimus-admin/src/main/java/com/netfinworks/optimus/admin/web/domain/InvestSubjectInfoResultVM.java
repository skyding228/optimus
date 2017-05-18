package com.netfinworks.optimus.admin.web.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.netfinworks.invest.entity.BidOrderInfoResult;
import com.netfinworks.invest.entity.InvestSubjectInfoResult;
import com.netfinworks.optimus.domain.enums.InvestSubjectStatusKind;
import com.netfinworks.optimus.domain.enums.RepayTypeKind;
import com.netfinworks.optimus.utils.BeanUtil;
import com.netfinworks.optimus.utils.FormatUtil;

/**
 * 投资标的 视图模型
 * 
 * @author weichunhe
 *
 */
public class InvestSubjectInfoResultVM extends InvestSubjectInfoResult {

	public InvestSubjectInfoResultVM(InvestSubjectInfoResult subject) {
		BeanUtil.copyProperties(this, subject);
		setProgressRateV(FormatUtil.formatRate(getProgressRate().multiply(
				new BigDecimal(100))));
		setBiddableAmountV(getBiddableAmount().format());
		setRewardRateV(FormatUtil.formatRate(getRewardRate()));
		setBidMinAmountV(getBidMinAmount().format());
		setApplyAmountV(getApplyAmount().format());
		setRepayTypeV(RepayTypeKind.getByCode(getRepayType()).getMessage());
		setStatusV(InvestSubjectStatusKind.getByCode(getStatus()).getMsg());
		setBidUnitV(getBidUnit().format());
	}

	public InvestSubjectInfoResultVM(InvestSubjectInfoResult subject,
			List<BidOrderInfoResult> records) {
		this(subject);
		setBidOrders(records);
		if (records != null) {
			List<BidOrderInfoResult> list = new ArrayList<BidOrderInfoResult>();
			for (BidOrderInfoResult order : records) {
				list.add(new BidOrderInfoResultVM(order,null));
			}
			setBidOrders(list);
		}
	}

	// 以下是专门用于视图显示时使用
	// 进度百分比
	private String progressRateV;
	// 剩余可购买金额 格式化后的
	private String biddableAmountV;
	// 格式化后的年化收益率
	private String rewardRateV;
	// 格式化后的起购金额
	private String bidMinAmountV;
	// 格式化后的融资总额
	private String applyAmountV;
	// 翻译后的还款方式
	private String repayTypeV;
	// 状态
	private String statusV;
	// 投资步长
	private String bidUnitV;

	public String getBidUnitV() {
		return bidUnitV;
	}

	public void setBidUnitV(String bidUnitV) {
		this.bidUnitV = bidUnitV;
	}

	public String getStatusV() {
		return statusV;
	}

	public void setStatusV(String statusV) {
		this.statusV = statusV;
	}

	// 投资记录
	private List<BidOrderInfoResult> bidOrders;

	public String getRepayTypeV() {
		return repayTypeV;
	}

	public void setRepayTypeV(String repayTypeV) {
		this.repayTypeV = repayTypeV;
	}

	public String getApplyAmountV() {
		return applyAmountV;
	}

	public void setApplyAmountV(String applyAmountV) {
		this.applyAmountV = applyAmountV;
	}

	public List<BidOrderInfoResult> getBidOrders() {
		return bidOrders;
	}

	public void setBidOrders(List<BidOrderInfoResult> bidOrders) {
		this.bidOrders = bidOrders;
	}

	public String getProgressRateV() {
		return progressRateV;
	}

	public void setProgressRateV(String progressRateV) {
		this.progressRateV = progressRateV;
	}

	public String getBiddableAmountV() {
		return biddableAmountV;
	}

	public void setBiddableAmountV(String biddableAmountV) {
		this.biddableAmountV = biddableAmountV;
	}

	public String getRewardRateV() {
		return rewardRateV;
	}

	public void setRewardRateV(String rewardRateV) {
		this.rewardRateV = rewardRateV;
	}

	public String getBidMinAmountV() {
		return bidMinAmountV;
	}

	public void setBidMinAmountV(String bidMinAmountV) {
		this.bidMinAmountV = bidMinAmountV;
	}

}
