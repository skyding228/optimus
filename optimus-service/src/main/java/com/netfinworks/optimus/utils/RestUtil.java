package com.netfinworks.optimus.utils;

import java.util.HashMap;
import java.util.Map;

public class RestUtil {
	/**
	 * 构造 Get 方法的参数字符串
	 * 
	 * @param param
	 * @param isStart
	 *            是否包含起始?
	 * @return
	 */
	public static String buildGetParamUrl(Map<String, Object> param,
			boolean isStart) {
		StringBuilder sb = new StringBuilder();
		if (isStart) {
			sb.append("?");
		}
		param.forEach((k, v) -> {
			sb.append(k);
			sb.append("={");
			sb.append(k);
			sb.append("}");
			sb.append("&");
		});
		if (sb.length() > 0 && "&".equals(sb.substring(sb.length() - 1))) {
			sb.delete(sb.length() - 1, sb.length());
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("a", "11");
		param.put("b", 22);
		System.out.println(buildGetParamUrl(param, true));
		System.out.println(buildGetParamUrl(param, false));
	}
}
