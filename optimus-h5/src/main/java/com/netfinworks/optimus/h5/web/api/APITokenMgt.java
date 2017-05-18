package com.netfinworks.optimus.h5.web.api;

import java.util.HashMap;
import java.util.Map;

import com.netfinworks.optimus.domain.TokenBean;

/**
 * 第三方token管理 生成一个30s有效期的key用来标识用户身份进行首页跳转
 * 
 * @author weichunhe create at 2016年3月28日
 */
public class APITokenMgt {

	private static Map<String, TokenBean> tokens = new HashMap<String, TokenBean>();

	/**
	 * 保存token
	 * 
	 * @param token
	 * @param platNo
	 * @return
	 */
	public static TokenBean saveToken(String token, String platNo) {
		TokenBean bean = new TokenBean(token, platNo);
		tokens.put(bean.getKey(), bean);
		return bean;
	}

	/**
	 * 去校验key
	 * 
	 * @param key
	 * @return
	 */
	public static TokenBean validKey(String key) {
		TokenBean bean = tokens.get(key);
		if (bean != null) {
			if (!bean.keyIsValid()) {
				return null;
			}
			tokens.remove(key);
		}
		return bean;
	}
}
