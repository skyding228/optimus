package com.netfinworks.optimus.h5.web.controller;

import java.math.BigDecimal;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netfinworks.optimus.PayRequest;
import com.netfinworks.optimus.domain.enums.OrderType;
import com.netfinworks.optimus.entity.MemberEntity;
import com.netfinworks.optimus.entity.OrderEntity;
import com.netfinworks.optimus.service.AccountService;
import com.netfinworks.optimus.service.MemberService;

/**
 * 账户相关操作
 * 
 * @author weichunhe
 * 暂时无用
 */
@RestController
@RequestMapping("/account")
public class PayController {

	
	@Resource
	private AccountService accountService;

	@Resource
	private MemberService memberService;

	private static Logger log = LoggerFactory
			.getLogger(PayController.class);

	/**
	 * 充值
	 * 
	 * @param request
	 * @return
	 */
//	@RequestMapping(value = "/pay")
//	public Object deposit(String  orderType ,String outerOrderId ,String origOuterOrderId, String memberId , BigDecimal amount,String notifyUrl, String memo ,String productId ,  HttpServletRequest request) {
//
//		Assert.isTrue(BigDecimal.ZERO.compareTo(amount) < 0, "支付金额必须大于0!");
//
//		MemberEntity member = getMember(request);
//
//		log.info("账户支付-金额:{}-{}", member.getMemberId(), amount);
//
//		PayRequest payRequest = new PayRequest();
//		payRequest.setAmount(amount);
//		payRequest.setMemo(memo); 
//		payRequest.setNotifyUrl(notifyUrl);
//		payRequest.setOuterOrderId(outerOrderId);
//		payRequest.setOrigOuterOrderId(origOuterOrderId);
//		payRequest.setProductId(productId);
//		payRequest.setOrderType(OrderType.getByCode(orderType));
//		OrderEntity order = accountService.pay(payRequest, member);
//
//		return "{'result':'success','data':{'outerOrderId':'"+order.getOuterOrderId()+"'}}";
//	}

	

	private MemberEntity getMember(HttpServletRequest request) {
		// MemberEntity member = (MemberEntity)
		// request.getSession().getAttribute(
		// CommonConstants.SESSION_LOGIN_USER);

		MemberEntity member = memberService.getOrCreateMember("xyf", "wch", "",
				"");
		Assert.notNull(member, "找不到对应的账户信息!");
		return member;
	}
}
