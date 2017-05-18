package com.netfinworks.optimus.utils;

import java.util.function.Function;
import java.util.function.UnaryOperator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netfinworks.util.JSONUtil;

/**
 * 执行lambda表达式工具类
 * 
 * @author weichunhe
 *
 */
public class LambdaUtil {
	private static Logger log = LoggerFactory.getLogger(LambdaUtil.class);
	// 重试次数
	private static final int RetryTimes = 3;

	/**
	 * 执行返回string类型的lambda表达式
	 * 
	 * @return
	 */
	public static String string(UnaryOperator<String> lm) {
		return lm.apply("");
	}

	/**
	 * 执行 lambda
	 * 
	 * @param f
	 * @param t
	 * @param logProfix
	 *            日志前缀
	 * @return
	 */
	public static <T, R> R function(Function<T, R> f, T t, String logProfix) {
		R r = null;
		long start = 0, end = 0;
		boolean success = false;
		for (int i = 0; i < RetryTimes; i++) {
			log.info("{}-开始第{}次调用!", logProfix, i + 1);
			start = System.currentTimeMillis();
			try {
				r = f.apply(t);
				success = true;
			} catch (Exception e) {
				log.error("{}-第{}次调用失败", logProfix, i + 1, e);
				if (i == RetryTimes - 1) {
					throw new RuntimeException(logProfix + "调用失败,参数:"
							+ JSONUtil.toJson(t));
				}
			}
			end = System.currentTimeMillis();
			log.info("{}-第{}次调用耗时{}ms!调用成功:{}", logProfix, i + 1, end - start,
					success);
			if (success) {
				break;
			}
		}
		return r;
	}

	public static void main(String[] args) {
		String name = "wch";
		string((x) -> {
			System.out.println(name);
			return name;
		});
	}

}
