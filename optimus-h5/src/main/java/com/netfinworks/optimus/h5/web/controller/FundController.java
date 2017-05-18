package com.netfinworks.optimus.h5.web.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.netfinworks.enums.OrderType;
import com.netfinworks.optimus.domain.FundInfo;
import com.netfinworks.optimus.domain.TokenBean;
import com.netfinworks.optimus.entity.MemberEntity;
import com.netfinworks.optimus.entity.OrderEntity;
import com.netfinworks.optimus.h5.web.CommonConstants;
import com.netfinworks.optimus.h5.web.util.ControllerUtil;
import com.netfinworks.optimus.service.ConfigService;
import com.netfinworks.optimus.service.MemberService;
import com.netfinworks.optimus.service.fund.ApplyService;
import com.netfinworks.optimus.service.fund.FundConstants;
import com.netfinworks.optimus.service.fund.RedeemService;
import com.netfinworks.optimus.service.integration.mef.MefClientImpl;
import com.netfinworks.optimus.utils.FormatUtil;
import com.netfinworks.util.JSONUtil;
import com.vf.mef.vo.InvestRedeemRequest;
import com.vf.mef.vo.InvestRedeemResponse;

@RestController
@RequestMapping("/fund")
public class FundController {

	static final String APPLY_KEY = "applysessionkey";
	static final String REDEEM_KEY = "redeemsessionkey";
	@Autowired
	private ApplyService applyService;
	@Autowired
	private RedeemService redeemService;

	@Resource(name = "mefClient")
	private MefClientImpl mefclient;

	@Autowired
	private MemberService memberService;

	@Resource
	private ConfigService configService;

	private static Logger log = LoggerFactory.getLogger(FundController.class);

	/**
	 * 申购
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/apply", method = RequestMethod.POST)
	public Object apply(HttpServletRequest request,
			@RequestBody OrderEntity order) {
		BigDecimal amount = order.getAmount();
		Assert.notNull(amount, "缺少申购金额参数!");
		Assert.isTrue(BigDecimal.ZERO.compareTo(amount) < 0, "申购金额必须大于0!");

		MemberEntity member = ControllerUtil.getMember(request, memberService);

		InvestRedeemRequest req = new InvestRedeemRequest();
		req.setChannelNo(member.getChanId());
		req.setAmount(amount.toString());
		req.setExtension("");
		req.setMemberId(member.getMemberId());
		req.setMemo("申购基金");
		req.setProductNo(configService.getPlatValue(member.getChanId(),
				FundConstants.FUND_ID));
		req.setRequestNo(UUID.randomUUID().toString().replaceAll("-", ""));
		req.setRequestTime(FormatUtil.formatDateTime(new Date()));
		req.setTradeType(OrderType.INVEST.name());

		InvestRedeemResponse response = mefclient.investRedeemTrade(req);
		log.info("申购基金响应数据" + JSONUtil.toJson(response));

		Map<String, Object> result = new HashMap<String, Object>();

		ControllerUtil.storeToSession(request, APPLY_KEY, ControllerUtil
				.resultModel(
						ControllerUtil.getContextPath(request)
								+ ControllerUtil
										.getStaticPath("/static/me.html"),
						"申购成功!",
						"本次申购"
								+ configService.getPlatValue(
										member.getChanId(),
										FundConstants.FUND_NAME)
								+ FormatUtil.formatRate(amount) + "元!", true));
		result.put("url", ControllerUtil.getContextPath(request)
				+ "/action/result?key=" + APPLY_KEY);

		log.info("基金申购-响应信息:{}", JSONUtil.toJson(result));
		return result;
	}

	/**
	 * 赎回
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/redeem", method = RequestMethod.POST)
	public Object redeem(HttpServletRequest request,
			@RequestBody OrderEntity order) {
		Assert.notNull(order.getAmount(), "缺少赎回金额!");
		MemberEntity member = ControllerUtil.getMember(request, memberService);

		Map<String, Object> result = new HashMap<String, Object>();

		InvestRedeemRequest req = new InvestRedeemRequest();
		req.setChannelNo(member.getChanId());
		req.setAmount(order.getAmount().toString());
		req.setExtension("");
		req.setMemberId(member.getMemberId());
		req.setMemo("赎回基金");
		req.setProductNo(configService.getPlatValue(member.getChanId(),
				FundConstants.FUND_ID));
		req.setRequestNo(UUID.randomUUID().toString().replaceAll("-", ""));
		req.setRequestTime(FormatUtil.formatDateTime(new Date()));
		req.setTradeType(OrderType.REDEEM.name());
		InvestRedeemResponse response = mefclient.investRedeemTrade(req);
		log.info("赎回基金响应数据" + JSONUtil.toJson(response));
		ControllerUtil.storeToSession(request, REDEEM_KEY, ControllerUtil
				.resultModel(
						ControllerUtil.getContextPath(request)
								+ ControllerUtil
										.getStaticPath("/static/me.html"),
						"赎回成功!",
						"本次赎回"
								+ configService.getPlatValue(
										member.getChanId(),
										FundConstants.FUND_NAME)
								+ FormatUtil.formatRate(order.getAmount())
								+ "元!", true));
		result.put("url", ControllerUtil.getContextPath(request)
				+ "/action/result?key=" + REDEEM_KEY);
		log.info("基金赎回-响应信息:{}", JSONUtil.toJson(result));
		return result;
	}

	// @RequestMapping("/redirect")
	// public void redirectpost(HttpServletRequest request,
	// HttpServletResponse response) {
	// response.setHeader("auth_id", "1");
	// try {
	// response.sendRedirect("/fund/redeem");
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }

	@RequestMapping("/info")
	public Object fundInfo(HttpServletRequest request) {
		TokenBean bean = ControllerUtil.retriveFromSession(request,
				CommonConstants.SESSION_LOGIN_USER, TokenBean.class);
		FundInfo info = FundInfo.newInstance(configService, bean.getPlatNo());
		log.info("查询基金数据:{}", JSONUtil.toJson(info));
		return info;
	}

	public static void main(String[] args) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("orderId", "DEPOSIT00001");
		params.put("memberId", "1000001");
		params.put("amount", "100.00");
		Map<String, Object> json = new HashMap<String, Object>();

		json.put("params", params);
		json.put("plat", "1234");
		System.out.println(org.apache.commons.codec.digest.DigestUtils
				.sha1Hex("wch"));
		System.out.println(JSONUtil.toJson(json));
		System.out.println(org.apache.commons.codec.digest.DigestUtils
				.sha1Hex(JSONUtil.toJson(json)));
	}
}
