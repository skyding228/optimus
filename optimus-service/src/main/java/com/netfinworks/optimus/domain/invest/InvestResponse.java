/**
 * 
 */
package com.netfinworks.optimus.domain.invest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.netfinworks.common.util.money.Money;

/**
 * <p>
 * 注释
 * </p>
 * 
 * @author wangtq
 * @version $Id: InvestResponse.java, v 0.1 2015年1月20日 上午11:28:02 wangtq Exp $
 */
public class InvestResponse {
	/**
	 * 奖金支付最高比例
	 */
	private String bonusPayRatio;
	/**
	 * 融资进度
	 */
	private BigDecimal progressRate;

	/**
	 * 标的编号
	 */
	private String subjectNo;

	/**
	 * 贷款编号
	 */
	private String applyNo;

	/**
	 * 标的名称
	 */
	private String subjectName;

	/**
	 * 标的描述
	 */
	private String description;

	/**
	 * 标的金额
	 */
	private Money applyAmount;

	/**
	 * 可投标金额
	 */
	private Money biddableAmount;

	/**
	 * 最低投标金额
	 */
	private Money bidMinAmount;

	/**
	 * 投标单位
	 */
	private Money bidUnit;

	/**
	 * 申请利率
	 */
	private BigDecimal rewardRate;

	/**
	 * 借款用途
	 */
	private String applyPurpose;

	/**
	 * 投标金额
	 */
	private BigDecimal bidAmount;

	/**
	 * 收益（汇总利息）
	 */
	private BigDecimal totalInterest;

	/**
	 * 已收金额（本金+利息）
	 */
	private BigDecimal receivedAmount;

	/**
	 * 待收金额（计算得来）
	 */
	private BigDecimal dueAmount;

	/**
	 * 冻结金额
	 */
	private BigDecimal totalFreezeAmt;

	/**
	 * 还款方式
	 */
	private String repayType;

	/**
	 * 借款期限
	 */
	private String loanTerm;

	/**
	 * 标的有效期限
	 */
	private Date validDate;

	/**
	 * 标的有效期限-页面展示
	 */
	private String strValidDate;

	/**
	 * 扩展信息
	 */
	private String extension;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 状态
	 */
	private String status;

	/**
	 * 投资收益（只有在计算收益的时候用的到）
	 */
	private List<InvestProfit> profitList;

	/**
	 * 投标记录（只有在查询投标记录的时候用到）
	 */
	private List<InvestRecord> recordList;

	/**
	 * 还款状态
	 */
	private String repayStatus;

	/**
	 * 是否可转让
	 */
	private String isAssignable;

	private String creditId;

	/**
	 * 标的类型:0-专家标;1-新手标
	 * */
	private String subjectType;

	private String memberId;

	public String getSubjectType() {
		return subjectType;
	}

	public void setSubjectType(String subjectType) {
		this.subjectType = subjectType;
	}

	public String getBonusPayRatio() {
		return bonusPayRatio;
	}

	public void setBonusPayRatio(String bonusPayRatio) {
		this.bonusPayRatio = bonusPayRatio;
	}

	public void setCreditId(String creditId) {
		this.creditId = creditId;
	}

	public String getCreditId() {
		return creditId;
	}

	public void setIsAssignable(String isAssignable) {
		this.isAssignable = isAssignable;
	}

	public String getIsAssignable() {
		return isAssignable;
	}

	public void setRepayStatus(String repayStatus) {
		this.repayStatus = repayStatus;
	}

	public String getRepayStatus() {
		return repayStatus;
	}

	public List<InvestRecord> getRecordList() {
		return recordList;
	}

	public void setRecordList(List<InvestRecord> recordList) {
		this.recordList = recordList;
	}

	public List<InvestProfit> getProfitList() {
		return profitList;
	}

	public void setProfitList(List<InvestProfit> profitList) {
		this.profitList = profitList;
	}

	public BigDecimal getReceivedAmount() {
		return receivedAmount;
	}

	public void setReceivedAmount(BigDecimal receivedAmount) {
		this.receivedAmount = receivedAmount;
	}

	public BigDecimal getDueAmount() {
		return dueAmount;
	}

	public void setDueAmount(BigDecimal dueAmount) {
		this.dueAmount = dueAmount;
	}

	public Money getBidMinAmount() {
		return bidMinAmount;
	}

	public void setBidMinAmount(Money bidMinAmount) {
		this.bidMinAmount = bidMinAmount;
	}

	public Money getBidUnit() {
		return bidUnit;
	}

	public void setBidUnit(Money bidUnit) {
		this.bidUnit = bidUnit;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the progressRate
	 */
	public BigDecimal getProgressRate() {
		return progressRate;
	}

	/**
	 * @param progressRate
	 *            the progressRate to set
	 */
	public void setProgressRate(BigDecimal progressRate) {
		this.progressRate = progressRate;
	}

	/**
	 * @return the subjectNo
	 */
	public String getSubjectNo() {
		return subjectNo;
	}

	/**
	 * @param subjectNo
	 *            the subjectNo to set
	 */
	public void setSubjectNo(String subjectNo) {
		this.subjectNo = subjectNo;
	}

	/**
	 * @return the subjectName
	 */
	public String getSubjectName() {
		return subjectName;
	}

	/**
	 * @param subjectName
	 *            the subjectName to set
	 */
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the applyAmount
	 */
	public Money getApplyAmount() {
		return applyAmount;
	}

	/**
	 * @param applyAmount
	 *            the applyAmount to set
	 */
	public void setApplyAmount(Money applyAmount) {
		this.applyAmount = applyAmount;
	}

	/**
	 * @return the biddableAmount
	 */
	public Money getBiddableAmount() {
		return biddableAmount;
	}

	/**
	 * @param biddableAmount
	 *            the biddableAmount to set
	 */
	public void setBiddableAmount(Money biddableAmount) {
		this.biddableAmount = biddableAmount;
	}

	/**
	 * @return the rewardRate
	 */
	public BigDecimal getRewardRate() {
		return rewardRate;
	}

	/**
	 * @param rewardRate
	 *            the rewardRate to set
	 */
	public void setRewardRate(BigDecimal rewardRate) {
		this.rewardRate = rewardRate;
	}

	/**
	 * @return the applyPurpose
	 */
	public String getApplyPurpose() {
		return applyPurpose;
	}

	/**
	 * @param applyPurpose
	 *            the applyPurpose to set
	 */
	public void setApplyPurpose(String applyPurpose) {
		this.applyPurpose = applyPurpose;
	}

	/**
	 * @return the repayType
	 */
	public String getRepayType() {
		return repayType;
	}

	/**
	 * @param repayType
	 *            the repayType to set
	 */
	public void setRepayType(String repayType) {
		this.repayType = repayType;
	}

	/**
	 * @return the loanTerm
	 */
	public String getLoanTerm() {
		return loanTerm;
	}

	/**
	 * @param loanTerm
	 *            the loanTerm to set
	 */
	public void setLoanTerm(String loanTerm) {
		this.loanTerm = loanTerm;
	}

	/**
	 * @return the validDate
	 */
	public Date getValidDate() {
		return validDate;
	}

	/**
	 * @param validDate
	 *            the validDate to set
	 */
	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}

	/**
	 * @return the extension
	 */
	public String getExtension() {
		return extension;
	}

	/**
	 * @param extension
	 *            the extension to set
	 */
	public void setExtension(String extension) {
		this.extension = extension;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark
	 *            the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStrValidDate() {
		return strValidDate;
	}

	public void setStrValidDate(String strValidDate) {
		this.strValidDate = strValidDate;
	}

	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	public BigDecimal getBidAmount() {
		return bidAmount;
	}

	public void setBidAmount(BigDecimal bidAmount) {
		this.bidAmount = bidAmount;
	}

	public BigDecimal getTotalInterest() {
		return totalInterest;
	}

	public void setTotalInterest(BigDecimal totalInterest) {
		this.totalInterest = totalInterest;
	}

	public BigDecimal getTotalFreezeAmt() {
		return totalFreezeAmt;
	}

	public void setTotalFreezeAmt(BigDecimal totalFreezeAmt) {
		this.totalFreezeAmt = totalFreezeAmt;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
