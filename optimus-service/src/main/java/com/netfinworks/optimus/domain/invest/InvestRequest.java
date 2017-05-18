package com.netfinworks.optimus.domain.invest;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.netfinworks.common.util.money.Money;
import com.netfinworks.optimus.domain.QueryBase;

/**
 * 请求参数
 * 
 * @version 1.0.0 2015-1-19 上午10:32:29
 *
 */
public class InvestRequest extends QueryBase implements Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = -6540793559332033855L;

	/**
	 * 投资人
	 */
	private String memberId;

	/**
	 * 标的编号
	 */
	private String subjectNo;
	/**
	 * 标的编号
	 */
	private String creditId;

	/**
	 * 投资金额
	 */
	private Money submitAmount;

	/**
	 * 区分投标记录
	 */
	private boolean needTotalFreezeAmt = false;

	/**
	 * 标的状态
	 */
	private List<String> status;

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getSubjectNo() {
		return subjectNo;
	}

	public void setSubjectNo(String subjectNo) {
		this.subjectNo = subjectNo;
	}

	public Money getSubmitAmount() {
		return submitAmount;
	}

	public void setSubmitAmount(Money submitAmount) {
		this.submitAmount = submitAmount;
	}

	public List<String> getStatus() {
		return status;
	}

	public void setStatus(List<String> status) {
		this.status = status;
	}

	public boolean isNeedTotalFreezeAmt() {
		return needTotalFreezeAmt;
	}

	public void setNeedTotalFreezeAmt(boolean needTotalFreezeAmt) {
		this.needTotalFreezeAmt = needTotalFreezeAmt;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public String getCreditId() {
		return creditId;
	}

	public void setCreditId(String creditId) {
		this.creditId = creditId;
	}
}
