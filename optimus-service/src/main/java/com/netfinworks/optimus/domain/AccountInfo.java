package com.netfinworks.optimus.domain;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.netfinworks.optimus.utils.BeanUtil;
import com.netfinworks.optimus.utils.FormatUtil;

/**
 * 账户 金额一览表 ,由于 format 方法会进行金额格式化,所以添加属性后要修改此方法
 * 
 * @author weichunhe
 *
 */
public class AccountInfo {

	private String userName;

	/**
	 * 账户余额
	 */
	private BigDecimal balance = BigDecimal.ZERO;
	/**
	 * 当前资产总值
	 */
	private BigDecimal totalAsset = BigDecimal.ZERO;

	/**
	 * 总收益
	 */
	private BigDecimal totalProfit = BigDecimal.ZERO;

	/**
	 * 昨日收益
	 */
	private BigDecimal yesterdayProfit = BigDecimal.ZERO;

	/**
	 * 定期产品总资产，包含确认和未确认的
	 */
	private BigDecimal subjectAsset = BigDecimal.ZERO;

	/**
	 * 定期产品在途收益
	 */
	private BigDecimal subjectPreProfit = BigDecimal.ZERO;

	/**
	 * 基金总资产，包含确认和未确认的
	 */
	private BigDecimal fundAsset = BigDecimal.ZERO;
	/**
	 * 格式化后的数据 key是原来的属性名V value 是格式化的数据
	 */
	private Map<String, String> vm = new HashMap<String, String>();

	public BigDecimal getSubjectPreProfit() {
		return subjectPreProfit;
	}

	public void setSubjectPreProfit(BigDecimal subjectPreProfit) {
		this.subjectPreProfit = subjectPreProfit;
	}

	public BigDecimal getSubjectAsset() {
		return subjectAsset;
	}

	public void setSubjectAsset(BigDecimal subjectAsset) {
		this.subjectAsset = subjectAsset;
	}

	public BigDecimal getFundAsset() {
		return fundAsset;
	}

	public void setFundAsset(BigDecimal fundAsset) {
		this.fundAsset = fundAsset;
	}

	public Map<String, String> getVm() {
		return vm;
	}

	public void setVm(Map<String, String> vm) {
		this.vm = vm;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getTotalAsset() {
		return totalAsset;
	}

	public void setTotalAsset(BigDecimal totalAsset) {
		this.totalAsset = totalAsset;
	}

	public BigDecimal getTotalProfit() {
		return totalProfit;
	}

	public void setTotalProfit(BigDecimal totalProfit) {
		this.totalProfit = totalProfit;
	}

	public BigDecimal getYesterdayProfit() {
		return yesterdayProfit;
	}

	public void setYesterdayProfit(BigDecimal yesterdayProfit) {
		this.yesterdayProfit = yesterdayProfit;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void format() {
		Map<String, String> map = BeanUtil.getPropertyMap(this, "vm","userName");

		map.forEach((x, y) -> {
			vm.put(x + "V", FormatUtil.formatRate(new BigDecimal(y)));
		});
	}
}
