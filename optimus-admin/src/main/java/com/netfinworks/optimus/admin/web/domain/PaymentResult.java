package com.netfinworks.optimus.admin.web.domain;

import java.math.BigDecimal;
import java.util.List;

import com.netfinworks.optimus.domain.enums.PaymentRecordEnum;
import com.netfinworks.optimus.utils.FormatUtil;

public class PaymentResult {
	// 轧差 结果 出款或入款
	private String nettingV;
	// 轧差额
	private String nettingMoneyV;
	// 出款小计
	private String outV;
	// 入款小计
	private String inV;
	// 所有的出款项
	private List<PaymentVM> outs;
	// 所有的入款项
	private List<PaymentVM> ins;

	private BigDecimal inMoney = new BigDecimal(0);
	private BigDecimal outMoney = new BigDecimal(0);
	private BigDecimal nettingMoney = new BigDecimal(0);

	public PaymentResult() {
		netting();
	}

	public void addOutMoney(BigDecimal money) {
		outMoney = outMoney.add(money);
		netting();
	}

	public void addInMoney(BigDecimal money) {
		inMoney = inMoney.add(money);
		netting();
	}

	// 轧差
	private void netting() {
		nettingMoney = outMoney.subtract(inMoney).abs();
		if (outMoney.compareTo(inMoney) > 0) {
			setNettingV(PaymentRecordEnum.PAYMENT_OUT.getDesc());
			setNettingMoneyV(FormatUtil.formatRate(outMoney.subtract(inMoney)));
		} else {
			setNettingV(PaymentRecordEnum.PAYMENT_IN.getDesc());
			setNettingMoneyV(FormatUtil.formatRate(inMoney.subtract(outMoney)));
		}
		setInV(FormatUtil.formatRate(inMoney));
		setOutV(FormatUtil.formatRate(outMoney));
	}

	public BigDecimal getInMoney() {
		return inMoney;
	}

	public void setInMoney(BigDecimal inMoney) {
		this.inMoney = inMoney;
	}

	public BigDecimal getOutMoney() {
		return outMoney;
	}

	public void setOutMoney(BigDecimal outMoney) {
		this.outMoney = outMoney;
	}

	public BigDecimal getNettingMoney() {
		return nettingMoney;
	}

	public void setNettingMoney(BigDecimal nettingMoney) {
		this.nettingMoney = nettingMoney;
	}

	public String getNettingMoneyV() {
		return nettingMoneyV;
	}

	public void setNettingMoneyV(String nettingMoneyV) {
		this.nettingMoneyV = nettingMoneyV;
	}

	public String getNettingV() {
		return nettingV;
	}

	public void setNettingV(String nettingV) {
		this.nettingV = nettingV;
	}

	public String getOutV() {
		return outV;
	}

	public void setOutV(String outV) {
		this.outV = outV;
	}

	public String getInV() {
		return inV;
	}

	public void setInV(String inV) {
		this.inV = inV;
	}

	public List<PaymentVM> getOuts() {
		return outs;
	}

	public void setOuts(List<PaymentVM> outs) {
		this.outs = outs;
	}

	public List<PaymentVM> getIns() {
		return ins;
	}

	public void setIns(List<PaymentVM> ins) {
		this.ins = ins;
	}

}
