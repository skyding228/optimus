/**
 * 进行权限验证 拦截外部请求 进行权限验证
 */
package com.netfinworks.optimus.h5.web.interceptor;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.netfinworks.common.util.StreamUtil;
import com.netfinworks.optimus.service.auth.AuthService;
import com.netfinworks.util.JSONUtil;
@Component
public class ApiInterceptor implements HandlerInterceptor {
	@Resource
	private AuthService authService;

	private static Logger log = LoggerFactory.getLogger(ApiInterceptor.class);

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String auth_id = request.getHeader(AuthService.HEADER_AUTH_ID);

		String cryptograph = request.getHeader(AuthService.HEADER_CRYPTOGRAPH);

		log.info("权限校验-请求头:auth_id={},cryptograph={}", auth_id, cryptograph);
		String plat = null;
		Map paramMap = new HashMap();

		request.setAttribute("status", 403);
		// 如果 auth_id 或者 cryptograph 为空 就是验证失败 返回403
		Assert.isTrue(!StringUtils.isEmpty(auth_id), "need auth-id!");

		Assert.isTrue(!StringUtils.isEmpty(cryptograph), "need cryptograph!");

		// 根据 ahth_id 查找 平台编码 查找不到也是403
		plat = authService.getPlatNo(auth_id);

		Assert.isTrue(!StringUtils.isEmpty(plat), "ahth-id is invalid");
		// GET请求
		if (StringUtils.equalsIgnoreCase(request.getMethod(),
				RequestMethod.GET.name())) {
			Enumeration<String> names = request.getParameterNames();
			String name = null;
			while (names.hasMoreElements()) {
				name = names.nextElement();
				paramMap.put(name, request.getParameter(name));
			}
		} else if (StringUtils.equalsIgnoreCase(request.getMethod(),
				RequestMethod.POST.name())) {
			String paramText = StreamUtil.readText(request.getInputStream());

			log.info("receive:{}", paramText);
			try {
				paramMap = JSONUtil.fromJson(paramText, Map.class);
			} catch (Exception e) {
				log.error("请求参数格式不正确:" + paramText, e);
				Assert.isTrue(false, "请传递JSON字符串格式参数:" + paramText);
			}
		}

		String sha1 = AuthService.sha1(plat, paramMap);

		Assert.isTrue(StringUtils.equals(cryptograph, sha1),
				"cryptograph is error！");
		// 保存post的请求数据
		paramMap.put("auth_id", auth_id);
		request.setAttribute("postParam", paramMap);

		request.removeAttribute("status");
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		log.debug("api 拦截器-postHandle");
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		log.debug("api 拦截器-afterCompletion");
	}

	public static void main(String[] args) {
		String plat = "XINGYIFU2016";
		Map<String, String> paramMap = new HashMap<String, String>();

		paramMap.put("needInfo", "1");
		paramMap.put("token", "123456");

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("plat", plat);
		map.put("params", paramMap);

		// 根据加密算法进行加密 比对
		String text = JSONUtil.toJson(map);
		String sha1 = DigestUtils.sha1Hex(text);
		log.info("明文:{},密文:{}", text, sha1);
	}
}
