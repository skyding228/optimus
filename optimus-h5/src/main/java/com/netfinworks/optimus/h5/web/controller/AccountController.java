package com.netfinworks.optimus.h5.web.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netfinworks.common.lang.Paginator;
import com.netfinworks.invest.entity.InvestSubjectInfoResult;
import com.netfinworks.optimus.domain.AccountInfo;
import com.netfinworks.optimus.domain.TokenBean;
import com.netfinworks.optimus.domain.enums.OrderType;
import com.netfinworks.optimus.entity.AccountEntity;
import com.netfinworks.optimus.entity.EntryEntity;
import com.netfinworks.optimus.entity.MemberEntity;
import com.netfinworks.optimus.entity.OrderEntity;
import com.netfinworks.optimus.h5.web.CommonConstants;
import com.netfinworks.optimus.h5.web.domain.EntryEntityVM;
import com.netfinworks.optimus.h5.web.domain.RestResult;
import com.netfinworks.optimus.h5.web.util.ControllerUtil;
import com.netfinworks.optimus.service.AccountService;
import com.netfinworks.optimus.service.ConfigService;
import com.netfinworks.optimus.service.MemberService;
import com.netfinworks.optimus.service.entity.EntryQueryParams;
import com.netfinworks.optimus.service.entity.EntryQueryResult;
import com.netfinworks.optimus.service.entity.QueryEntryResponse;
import com.netfinworks.optimus.service.fund.ApplyService;
import com.netfinworks.optimus.service.fund.FundConstants;
import com.netfinworks.optimus.service.fund.RedeemService;
import com.netfinworks.optimus.service.integration.invest.impl.InvestServiceImpl;
import com.netfinworks.optimus.service.repository.OrderRepository;
import com.netfinworks.optimus.utils.BeanUtil;
import com.netfinworks.util.JSONUtil;

/**
 * 账户相关操作
 * 
 * @author weichunhe
 *
 */
@RestController
@RequestMapping("/account")
public class AccountController {
	// 暂存操作结果到session的可以
	static final String WITHDRAW_KEY = "withdrawsessionkey";
	static final String DEPOSIT_KEY = "depositsessionkey";

	@Resource
	private ApplyService applyService;
	@Resource
	private RedeemService redeemService;

	@Resource(name = "investService")
	private InvestServiceImpl investService;

	@Resource
	private AccountService accountService;

	@Resource
	private MemberService memberService;

	@Resource
	private ConfigService configService;

	@Resource
	private OrderRepository orderRepository;

	private static Logger log = LoggerFactory
			.getLogger(AccountController.class);

	/**
	 * 充值
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/deposit", method = RequestMethod.POST)
	public Object deposit(@RequestBody OrderEntity order,
			HttpServletRequest request) {
		BigDecimal amount = order.getAmount();
		Assert.isTrue(BigDecimal.ZERO.compareTo(amount) < 0, "充值金额必须大于0!");
		Assert.notNull(order.getMemo(), "缺少密码!"); // 暂用作密码字段
		TokenBean bean = ControllerUtil.retriveFromSession(request,
				CommonConstants.SESSION_LOGIN_USER, TokenBean.class);

		MemberEntity member = ControllerUtil.getMember(request, memberService);

		log.info("账户充值:{}-{}", member.getMemberId(), JSONUtil.toJson(order));
		// 对密码解密
		String pwd = order.getMemo(); // RSAUtil.decryptJSRsa(order.getMemo());

		AccountEntity account = accountService.deposit(amount, pwd,// "37b8acd28f207ce2",
				configService.getValue(ConfigService.API_URL_BASE)
						+ "/callback/account/deposit", bean);
		RestResult restResult = new RestResult();
		// 如果accountId 不为空就说明充值操作成功
		if (account.getAccountId() == null) {
			restResult.setCode(1);
			restResult.setMsg(account.getAccountName());
			return restResult;
		}

		Map<String, Object> result = BeanUtil.pick(account, "balance");

		ControllerUtil.storeToSession(request, DEPOSIT_KEY, ControllerUtil
				.resultModel(ControllerUtil.getContextPath(request)
						+ ControllerUtil.getStaticPath("/static/me.html"),
						"充值成功!", "本次充值" + amount + "元!", true));
		result.put("url", ControllerUtil.getContextPath(request)
				+ "/action/result?key=" + DEPOSIT_KEY);

		return result;
	}

	/**
	 * 提现
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/withdraw", method = RequestMethod.POST)
	public Object withdraw(@RequestBody OrderEntity order,
			HttpServletRequest request) {
		BigDecimal amount = order.getAmount();

		Assert.isTrue(BigDecimal.ZERO.compareTo(amount) < 0, "提现金额必须大于0!");
		Assert.notNull(order.getMemo(), "缺少密码!"); // 暂用作密码字段
		TokenBean bean = ControllerUtil.retriveFromSession(request,
				CommonConstants.SESSION_LOGIN_USER, TokenBean.class);
		MemberEntity member = ControllerUtil.getMember(request, memberService);

		log.info("账户提现:{}-{}", member.getMemberId(), JSONUtil.toJson(order));

		// 对密码解密
		String pwd = order.getMemo(); // RSAUtil.decryptJSRsa(order.getMemo());
		AccountEntity account = accountService.withdraw(amount, pwd,
				configService.getValue(ConfigService.API_URL_BASE)
						+ "/callback/account/redeem", bean);

		RestResult restResult = new RestResult();
		// 如果accountId 不为空就说明充值操作成功
		if (account.getAccountId() == null) {
			restResult.setCode(1);
			restResult.setMsg(account.getAccountName());
			return restResult;
		}

		Map<String, Object> result = BeanUtil.pick(account, "balance");

		ControllerUtil.storeToSession(request, WITHDRAW_KEY, ControllerUtil
				.resultModel(ControllerUtil.getContextPath(request)
						+ ControllerUtil.getStaticPath("/static/me.html"),
						"提现成功!", "本次提现" + amount + "元!", true));
		result.put("url", ControllerUtil.getContextPath(request)
				+ "/action/result?key=" + WITHDRAW_KEY);

		return result;
	}

	/**
	 * 查询账户 资产一览
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/overview")
	public Object balance(HttpServletRequest request) {

		MemberEntity member = ControllerUtil.getMember(request, memberService);
		AccountInfo info = accountService.calcOverview(member);

		return info;
	}

	/**
	 * 交易明细
	 * 
	 * @param request
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/tradeDetail")
	public Object tradeDetail(HttpServletRequest request,
			@RequestParam(defaultValue = "1") int pageNum,
			@RequestParam(defaultValue = "10") int pageSize) {
		MemberEntity member = ControllerUtil.getMember(request, memberService);
		// 查询账户余额
		AccountEntity account = accountService
				.getAccount(member.getAccountId());
		Assert.notNull(account, "未查询到相关账户信息:accountId=" + member.getAccountId());
		EntryQueryParams param = new EntryQueryParams();
		param.setMemberId(member.getMemberId());
		Paginator paginator = new Paginator(pageSize);
		paginator.setPage(pageNum);
		param.setPaginator(paginator);
		log.info("账户交易明细-请求参数:{}", JSONUtil.toJson(param));
		EntryQueryResult result = accountService.queryEntry(param);
		log.info("账户交易 明细-响应信息:{}", JSONUtil.toJson(result));

		QueryEntryResponse response = new QueryEntryResponse(result);
		if (response.getResult() != null) {
			List<EntryEntity> vms = new ArrayList<EntryEntity>();
			response.getResult().forEach(x -> {
				EntryEntityVM vm = new EntryEntityVM(x);
				vm.setDetail(getDetailByOrderId(vm.getOrderId()));
				vms.add(vm);
			});
			response.setResult(vms);
		}
		return response;
	}

	/**
	 * 获取订单的详细信息，产品名称，基金名称等
	 * 
	 * @param orderId
	 * @return
	 */
	private String getDetailByOrderId(String orderId) {
		String detail = "";
		if (StringUtils.isEmpty(orderId)) {
			return detail;
		}
		OrderEntity order = orderRepository.getOrderByNo(orderId);
		if (order == null || StringUtils.isEmpty(order.getProductId())) {
			return detail;
		}
		// 基金信息
		if (OrderType.APPLY.getValue().equals(order.getOrderStatus())
				|| OrderType.REDEEM.getValue().equals(order.getOrderStatus())) {
			detail = configService.getPlatValue(order.getChanId(),
					FundConstants.FUND_NAME);
			return StringUtils.isEmpty(detail) ? "" : detail;
		}
		InvestSubjectInfoResult subject = investService.querySubject(order
				.getProductId());
		if (subject != null) {
			return subject.getSubjectName();
		}

		return detail;
	}
}
