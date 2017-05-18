package com.netfinworks.optimus.utils;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志 工具类
 * 
 * @author weichunhe
 *
 */
public class LogUtil {
	private static Logger log = LoggerFactory.getLogger(LogUtil.class);

	/**
	 * 打印错误日志 ，返回日志id
	 * 
	 * @param e
	 * @return
	 */
	public static String error(Throwable e) {
		String id = UUID.randomUUID().toString();

		log.error(id, e);

		return id;
	}

}
