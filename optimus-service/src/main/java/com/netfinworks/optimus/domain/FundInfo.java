package com.netfinworks.optimus.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

import com.netfinworks.optimus.entity.DictEntity;
import com.netfinworks.optimus.service.ConfigService;
import com.netfinworks.optimus.service.fund.FundConstants;
import com.netfinworks.optimus.utils.BeanUtil;

public class FundInfo {
	// 基金编号
	private String id;
	// 基金名称
	private String name;
	// 起购金额
	private String minAmount;
	// 是否开放购买 0 1
	private String canBuy;
	// 7日年化收益率
	private String returnRate7;
	// 每万份收益
	private String tenThousandProfit;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(String minAmount) {
		this.minAmount = minAmount;
	}

	public String getCanBuy() {
		return canBuy;
	}

	public void setCanBuy(String canBuy) {
		this.canBuy = canBuy;
	}

	public String getReturnRate7() {
		return returnRate7;
	}

	public void setReturnRate7(String returnRate7) {
		this.returnRate7 = returnRate7;
	}

	public String getTenThousandProfit() {
		return tenThousandProfit;
	}

	public void setTenThousandProfit(String tenThousandProfit) {
		this.tenThousandProfit = tenThousandProfit;
	}

	/**
	 * 生成键值对进行保存
	 * 
	 * @return
	 */
	public List<DictEntity> toDicts(String plat) {
		List<DictEntity> dicts = new ArrayList<DictEntity>();
		Map<String, String> map = BeanUtil.getPropertyMap(this);
		map.forEach((k, v) -> {
			if (!StringUtils.isEmpty(v)) {
				DictEntity d = new DictEntity();
				d.setId(plat + "." + FundConstants.PREFIX + "." + k);
				d.setValue(v);
				d.setType(plat + "." + FundConstants.PREFIX);
				dicts.add(d);
			}
		});

		return dicts;
	}

	/**
	 * 从配置中生成新的实例
	 * 
	 * @param map
	 * @return
	 */
	public static FundInfo newInstance(ConfigService config, String plat) {
		FundInfo info = new FundInfo();
		Map<String, String> map = BeanUtil.getPropertyMap(info);
//		config.init(key); //刷新缓存
		map.forEach((k, v) -> {
			try {
				BeanUtils.setProperty(
						info,
						k,
						config.getPlatValue(plat, FundConstants.PREFIX + "."
								+ k));
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		return info;
	}
}
