package com.netfinworks.optimus.admin.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.netfinworks.optimus.admin.web.CommonConstants;
import com.netfinworks.optimus.entity.MemberEntity;
import com.netfinworks.optimus.service.MemberService;

/**
 * 与Controller相关的一些工具方法
 * 
 * @author weichunhe
 *
 */
public class ControllerUtil {

	private static Logger log = LoggerFactory.getLogger(ControllerUtil.class);

	/**
	 * 获取项目路径
	 * 
	 * @param request
	 * @return
	 */
	public static String getContextPath(HttpServletRequest request) {
		return request.getSession().getServletContext().getContextPath();
	}

	/**
	 * 生成 结果数据
	 * 
	 * @param url
	 *            完成 按钮要跳转的url
	 * @param message
	 * @param detail
	 *            详细信息
	 * @param success
	 *            操作是否成功
	 * @return
	 */
	public static Map<String, Object> resultModel(String url, String message,
			String detail, boolean success) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("url", url);
		map.put("message", message);
		map.put("detail", detail);
		map.put("success", success);
		return map;
	}

	/**
	 * 将数据保存到session 中
	 * 
	 * @param request
	 * @param key
	 * @param obj
	 */
	public static void storeToSession(HttpServletRequest request, String key,
			Object obj) {
		request.getSession().setAttribute(key, obj);
	}

	/**
	 * 从session中获取数据
	 * 
	 * @param request
	 * @param key
	 * @param klass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T retriveFromSession(HttpServletRequest request,
			String key, Class<T> klass) {
		Object obj = request.getSession().getAttribute(key);
		if (obj != null) {
			return (T) obj;
		}
		return null;
	}

	/**
	 * 删除属性
	 * 
	 * @param request
	 * @param key
	 */
	public static void removeFromSession(HttpServletRequest request, String key) {
		request.getSession().removeAttribute(key);
	}

	/**
	 * 重定向
	 * 
	 * @param request
	 * @param response
	 * @param url
	 */
	public static void redirect(HttpServletRequest request,
			HttpServletResponse response, String url) {
		String home = ControllerUtil.getContextPath(request) + url;
		log.info("重定向到页面:{}", home);
		try {
			response.sendRedirect(home);
		} catch (IOException e) {
			log.error("重定向时出错", e);
		}
	}

	/**
	 * 转发到指定地址
	 * 
	 * @param request
	 * @param response
	 * @param url
	 */
	public static void forward(HttpServletRequest request,
			HttpServletResponse response, String url) {
		log.info("转发到页面:{}", url);
		try {
			request.getRequestDispatcher(url).forward(request, response);
		} catch (Exception e) {
			log.error("请求转发时出错", e);
		}
	}

	/**
	 * 是否是ajax请求
	 * 
	 * @param request
	 * @return
	 */
	public static boolean isAjax(HttpServletRequest request) {
		boolean isAjax = false;
		String ajaxRequest = request.getHeader("x-Requested-With");
		if (ajaxRequest != null && ajaxRequest.equals("XMLHttpRequest")) {
			isAjax = true;
		}
		return isAjax;
	}
}
