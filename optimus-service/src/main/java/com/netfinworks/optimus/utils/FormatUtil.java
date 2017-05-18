package com.netfinworks.optimus.utils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 格式化工具类
 * 
 * @author weichunhe
 *
 */
public class FormatUtil {
	private static Logger log = LoggerFactory.getLogger(FormatUtil.class);
	private static DecimalFormat rateFm = new DecimalFormat(
			"##,###,###,###,##0.00");

	public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private static DateFormat dateTimeFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	/**
	 * 比率；等级；（利息等的）费率 格式化
	 * 
	 * @param rate
	 * @return
	 */
	public static String formatRate(Object rate) {
		return rateFm.format(rate);
	}

	/**
	 * 比率；等级；（利息等的）费率 格式化
	 * 
	 * @param rate
	 * @return
	 */
	public static String formatRate(Object rate, Object ifNull) {
		return formatRate(rate == null ? ifNull : rate);
	}

	/**
	 * 格式化 日期 yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date) {
		return dateFormat.format(date);
	}

	/**
	 * 
	 * @param format
	 * @return
	 */
	public static long parseDateLong(String format) {
		long date = 0;
		try {
			date = dateFormat.parse(format).getTime();
		} catch (ParseException e) {
			log.info("解析日期错误:format={},param={}", dateFormat.toString(), format);
		}
		return date;
	}

	/**
	 * 格式化时间 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDateTime(Date date) {
		return dateTimeFormat.format(date);
	}

	/**
	 * 对数据进行脱敏处理 中间使用*代替
	 * <p>
	 * 长度为1,返回*;长度为2，第二位为*;长度超过2,中间为*
	 * </p>
	 * 
	 * @param msg
	 * @return
	 */
	public static String shuckSensitive(String msg) {
		if (msg == null) {
			return msg;
		}
		if (msg.length() == 1) {
			return "*";
		}
		if (msg.length() == 2) {
			return msg.substring(0, 1) + "*";
		}
		StringBuilder sb = new StringBuilder(msg);
		for (int i = 1; i < msg.length() - 1; i++) {
			sb.replace(i, i + 1, "*");
		}
		return sb.toString();
	}

	/**
	 * 截取字符串
	 * 
	 * @param str
	 * @param end
	 * @return
	 */
	public static String limitStr(String str, int end) {
		if (StringUtils.isEmpty(str)) {
			return str;
		}
		return str.substring(0, Math.min(str.length(), end));
	}

	public static void main(String[] args) {
		System.out.println(formatRate(new BigDecimal("100000.0000")));
		System.out.println(formatRate(new BigDecimal("0.7")));
		System.out.println(formatRate(0.7));
		System.out.println(shuckSensitive("1"));
		System.out.println(shuckSensitive("12"));
		System.out.println(shuckSensitive("123"));
		System.out.println(shuckSensitive("1234"));
		System.out.println(shuckSensitive("12345"));
	}
}
