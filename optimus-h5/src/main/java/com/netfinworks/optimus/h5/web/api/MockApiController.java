package com.netfinworks.optimus.h5.web.api;

import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netfinworks.optimus.domain.OrderRequest;
import com.netfinworks.optimus.domain.OrderResponse;
import com.netfinworks.optimus.domain.UserInfo;
import com.netfinworks.util.JSONUtil;

/**
 * mock 第三方接口
 * 
 * @author weichunhe create at 2016年4月7日
 */
@RequestMapping("/mock/b")
@RestController
public class MockApiController {
	private static Logger log = LoggerFactory
			.getLogger(MockApiController.class);

	private static final String LogProfix = "MockAPi";

	/**
	 * 充值接口
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping("/account/deposit")
	public OrderResponse deposit(HttpServletRequest request,
			@RequestBody OrderRequest param) {

		OrderResponse response = new OrderResponse();
		response.setOrderId(UUID.randomUUID().toString());
		response.setOrderTime(System.currentTimeMillis());
		response.setOriginOrderId(param.getOrderId());
		int min = new Date().getMinutes();
		if (min % 2 == 1) {
			response.setStatus("SUCCESS");
			response.setReason("充值成功!");
		} else {
			response.setStatus("FAIL");
			response.setReason("只有奇数分钟可以充值!");
		}

		log.info("{}-充值:请求={},响应={}", LogProfix, JSONUtil.toJson(param),
				JSONUtil.toJson(response));
		return response;
	}

	/**
	 * 提现接口
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping("/account/redeem")
	public OrderResponse withdraw(HttpServletRequest request,
			@RequestBody OrderRequest param) {
		OrderResponse response = new OrderResponse();
		response.setOrderId(UUID.randomUUID().toString());
		response.setOrderTime(System.currentTimeMillis());
		response.setOriginOrderId(param.getOrderId());
		int min = new Date().getMinutes();
		if (min % 2 == 0) {
			response.setStatus("SUCCESS");
			response.setReason("提现成功!");
		} else {
			response.setStatus("FAIL");
			response.setReason("只有偶数分钟可以提现!");
		}

		log.info("{}-提现:请求={},响应={}", LogProfix, JSONUtil.toJson(param),
				JSONUtil.toJson(response));
		return response;
	}

	/**
	 * token 验证机制
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/member/token")
	public UserInfo token(HttpServletRequest request) {
		UserInfo user = new UserInfo();
		user.setEmail("1329555958");
		user.setIdNumber("xxxxxxxxxxx");
		user.setMemberId("10001001");
		user.setMemberName("axing");
		user.setRealName("zhouxignchi");
		user.setTelphone("111111111");
		log.info("{}-token:响应={}", LogProfix, JSONUtil.toJson(user));
		return user;
	}
}
