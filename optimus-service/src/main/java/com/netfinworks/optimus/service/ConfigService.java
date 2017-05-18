package com.netfinworks.optimus.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.netfinworks.optimus.domain.enums.PaymentRecordEnum;
import com.netfinworks.optimus.entity.DictEntity;
import com.netfinworks.optimus.entity.DictEntityExample;
import com.netfinworks.optimus.mapper.DictEntityMapper;
import com.netfinworks.optimus.service.fund.FundConstants;

@Service
public class ConfigService {

	@Resource
	public DictEntityMapper dictEntityMapper;
	// 所有的配置信息
	private Map<String, DictEntity> config = new HashMap<String, DictEntity>();

	// 产品类型为key,平台编码为value
	private Map<String, String> subjectTypeMap = new HashMap<String, String>();

	// 基金编码为key,平台编码为value
	private Map<String, String> fundIdMap = new HashMap<String, String>();
	// 所有的平台
	private List<String> allPlats = new ArrayList<String>();

	public static final String
	// API基础地址,以及平台基础地址
			API_URL_BASE = "API.URL.base",
			// 对账文件根路径
			CHECKING_FILE_ROOT = "CHECKING.FILE.root",

			// 验证token的url
			PLAT_TOKEN_URL = "/member/token",
			// 充值接口地址
			PLAT_DEPOSIT_URL = "/account/deposit",
			// 提现接口地址
			PLAT_WITHDRAW_URL = "/account/redeem",
			// 对账文件接口地址
			PLAT_CHECKING_URL = "/account/checking",
			// 平台对应的产品类型
			PLAT_SUBJECT_TYPE = "subjectType",

			// 渠道备付金会员编号
			PLAT_PROVISION_MEMBER = "PROVISION.memberId",

			// 平台接口授权码
			PLAT_AUTH_ID = "auth-id",
			// 测试相关的登录截止日期 yyyy-MM-dd
			TEST_LOGIN_DEADLINE = "TEST.LOGIN.deadline",
			// 字典表的类型列表
			TYPE_CONFIG = "CONFIG", // 配置信息类型
			TYPE_CHECKING = "CHECKING", // 对账文件数据类型
			TYPE_CHAN_VIEW = "CHAN_OVERVIEW"; // 渠道账户总览数据类型

	@PostConstruct
	public void init() {
		config.clear();
		subjectTypeMap.clear();
		fundIdMap.clear();
		allPlats.clear();
		DictEntityExample example = new DictEntityExample();
		example.createCriteria().andTypeEqualTo(TYPE_CONFIG);
		List<DictEntity> dicts = dictEntityMapper.selectByExample(example);
		if (dicts != null) {
			dicts.forEach(x -> {
				config.put(x.getId(), x);
			});
			makeSubjectTypeMap(dicts);
			makeFundIdMap(dicts);
		}

	}

	private static Pattern subjectTypeKeyPattern = Pattern.compile("^(\\w+)\\."
			+ PLAT_SUBJECT_TYPE + "$");

	private void makeSubjectTypeMap(List<DictEntity> dicts) {
		subjectTypeMap.clear();
		dicts.forEach(x -> {
			Matcher matcher = subjectTypeKeyPattern.matcher(x.getId());
			if (matcher.find()) {
				String val = matcher.group(1);
				subjectTypeMap.put(x.getValue(), val);
				allPlats.add(val);
			}
		});
	}

	private static Pattern fundIdKeyPattern = Pattern.compile("^(\\w+)\\."
			+ FundConstants.FUND_ID.replaceAll("\\.", "\\\\.") + "$");

	private void makeFundIdMap(List<DictEntity> dicts) {
		fundIdMap.clear();
		dicts.forEach(x -> {
			Matcher matcher = fundIdKeyPattern.matcher(x.getId());
			if (matcher.find()) {
				String val = matcher.group(1);
				fundIdMap.put(x.getValue(), val);
			}
		});
	}

	/**
	 * 获取所有平台
	 * 
	 * @return
	 */
	public List<String> getAllPlats() {
		List<String> list = new ArrayList<String>();
		list.addAll(allPlats);
		return list;
	}

	/**
	 * 根据key 获取value
	 * 
	 * @param key
	 * @return
	 */
	public String getValue(String key) {
		if (config.get(key) != null) {
			return config.get(key).getValue();
		}
		return null;
	}

	/**
	 * 获取平台下面的配置信息,会加上平台编号前缀
	 * 
	 * @param plat
	 * @param key
	 * @return
	 */
	public String getPlatValue(String plat, String key) {
		key = plat + "." + key;
		return getValue(key);
	}

	/**
	 * 设置
	 * 
	 * @param key
	 * @param value
	 */
	public void setValue(String key, String value) {
		DictEntity dict = config.get(key);
		if (dict == null) {
			dict = new DictEntity();
		}
		dict.setValue(value);
		config.put(key, dict);
	}

	/**
	 * 
	 * @param plat
	 * @param key
	 * @param value
	 */
	public void setPlatValue(String plat, String key, String value) {
		setValue(plat + "." + key, value);
	}

	/**
	 * 保存
	 * 
	 * @param dicts
	 */
	public void save(List<DictEntity> dicts) {
		if (dicts != null) {
			dicts.forEach(x -> {
				x.setType(TYPE_CONFIG);
				// 判断是否存在
				DictEntity d = dictEntityMapper.selectByPrimaryKey(x.getId());
				if (d != null) {
					dictEntityMapper.updateByPrimaryKey(x);
				} else {
					dictEntityMapper.insert(x);
				}
			});
			init();
		}
	}

	public Map<String, DictEntity> getConfig() {
		return config;
	}

	/**
	 * 获取平台编码对应的标的类型
	 * 
	 * @param plat
	 * @return
	 */
	public String getSubjectType(String plat) {
		return getPlatValue(plat, PLAT_SUBJECT_TYPE);
	}

	/**
	 * 根据产品类型获取对应的平台编码
	 * 
	 * @param subjectType
	 * @return
	 */
	public String getPlatBySubjectType(String subjectType) {
		return subjectTypeMap.get(subjectType);
	}

	/**
	 * 根据产品类型获取对应的平台编码
	 * 
	 * @param subjectType
	 * @return
	 */
	public String getPlatByFundId(String fundId) {
		return fundIdMap.get(fundId);
	}

	/**
	 * 获取平台充值地址
	 * 
	 * @param plat
	 * @return
	 */
	public String getDepositUrl(String plat) {
		return getPlatValue(plat, API_URL_BASE) + PLAT_DEPOSIT_URL;
	}

	/**
	 * 获取平台提现地址
	 * 
	 * @param plat
	 * @return
	 */
	public String getWithDrawUrl(String plat) {
		return getPlatValue(plat, API_URL_BASE) + PLAT_WITHDRAW_URL;
	}

	/**
	 * 获取平台名称
	 * 
	 * @param plat
	 * @return
	 */
	public String getPlatName(String plat) {
		if (StringUtils.equals(PaymentRecordEnum.VFINANCE.getValue(), plat)) {
			return PaymentRecordEnum.VFINANCE.getDesc();
		} else {
			if (StringUtils.isNotEmpty(getValue(plat))) {
				return getValue(plat);
			}
		}
		return plat;
	}

	public static void main(String[] args) {

	}
}
