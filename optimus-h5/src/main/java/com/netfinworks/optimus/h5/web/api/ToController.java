package com.netfinworks.optimus.h5.web.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.netfinworks.optimus.domain.TokenBean;
import com.netfinworks.optimus.h5.web.CommonConstants;
import com.netfinworks.optimus.h5.web.util.ControllerUtil;

/**
 * 第三方跳转
 * 
 * @author weichunhe create at 2016年3月28日
 */

@Controller
@RequestMapping("/to")
public class ToController {
	private static Logger log = LoggerFactory.getLogger(ToController.class);

	/**
	 * 跳转到index
	 * 
	 * @param request
	 * @param response
	 * @param key
	 *            根据用户携带的token生成的唯一身份标识
	 * @return
	 */
	@RequestMapping("/index/{key}")
	public void toIndex(HttpServletRequest request,
			HttpServletResponse response, @PathVariable String key) {
		validKey(request, response, key);
		ControllerUtil.redirect(request, response,ControllerUtil.getStaticPath("/static/index.html"));
	}

	/**
	 * 跳转到个人中心
	 * 
	 * @param request
	 * @param response
	 * @param key
	 *            根据用户携带的token生成的唯一身份标识
	 * @return
	 */
	@RequestMapping("/account/me/{key}")
	public void toMe(HttpServletRequest request, HttpServletResponse response,
			@PathVariable String key) {
		validKey(request, response, key);
		ControllerUtil.redirect(request, response, ControllerUtil.getStaticPath("/static/me.html"));
	}

	/**
	 * 跳转到充值页面
	 * 
	 * @param request
	 * @param response
	 * @param key
	 *            根据用户携带的token生成的唯一身份标识
	 * @return
	 */
	@RequestMapping("/account/deposit/{key}")
	public void toDeposit(HttpServletRequest request,
			HttpServletResponse response, @PathVariable String key) {
		validKey(request, response, key);
		ControllerUtil.redirect(request, response, ControllerUtil.getStaticPath("/static/medeposit.html"));
	}

	private void validKey(HttpServletRequest request,
			HttpServletResponse response, String key) {
		TokenBean bean = APITokenMgt.validKey(key);
		log.info("跳转,key={}", key);
		if (bean == null) {
			ControllerUtil.redirect(request, response, "/nologin");
		}
		Assert.notNull(bean, "无效身份信息,请重新登录!");
		ControllerUtil.storeToSession(request,
				CommonConstants.SESSION_LOGIN_USER, bean);
	}

}
