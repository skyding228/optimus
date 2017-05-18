package com.netfinworks.optimus.admin.web.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netfinworks.common.lang.ArrayUtil;
import com.netfinworks.common.lang.Paginator;
import com.netfinworks.optimus.admin.util.ControllerUtil;
import com.netfinworks.optimus.admin.web.CommonConstants;
import com.netfinworks.optimus.admin.web.domain.EntryEntityVM;
import com.netfinworks.optimus.admin.web.domain.MemberEntityVM;
import com.netfinworks.optimus.admin.web.domain.OrderEntiryVM;
import com.netfinworks.optimus.domain.RestResult;
import com.netfinworks.optimus.domain.enums.OrderStatus;
import com.netfinworks.optimus.domain.enums.OrderType;
import com.netfinworks.optimus.domain.enums.PaymentRecordEnum;
import com.netfinworks.optimus.entity.EntryEntity;
import com.netfinworks.optimus.entity.MemberEntity;
import com.netfinworks.optimus.entity.OrderEntity;
import com.netfinworks.optimus.entity.PaymentEntity;
import com.netfinworks.optimus.mapper.ManualEntityMapper;
import com.netfinworks.optimus.service.AccountService;
import com.netfinworks.optimus.service.MemberService;
import com.netfinworks.optimus.service.entity.EntryQueryParams;
import com.netfinworks.optimus.service.entity.EntryQueryResult;
import com.netfinworks.optimus.service.entity.QueryEntryResponse;
import com.netfinworks.optimus.service.repository.OrderRepository;
import com.netfinworks.optimus.utils.FormatUtil;
import com.netfinworks.util.JSONUtil;

@RestController
@RequestMapping("/account")
public class AccountController {

	@Resource
	private AccountService accountService;

	@Resource
	private OrderRepository orderRepository;

	@Resource
	private MemberService memberService;

	@Resource
	private ManualEntityMapper manualEntityMapper;

	static Logger log = LoggerFactory.getLogger(AccountController.class);
	final String LogProfix = "账户操作";
	private static SimpleDateFormat day = new SimpleDateFormat("yyyyMMdd");

	/**
	 * 进行出入款操作
	 * 
	 * @param request
	 * @param order
	 */
	@RequestMapping("/payment")
	public Object doPayment(HttpServletRequest request,
			@RequestBody OrderEntity order) {
		log.info("{}-出入款-请求参数:{}", LogProfix, JSONUtil.toJson(order));

		Assert.notNull(order.getOuterOrderId(), "缺少凭证号!");
		Assert.isNull(
				orderRepository.getOrderByOuterNo(order.getOuterOrderId()),
				"此凭证号已被使用过!");
		Assert.notNull(order.getAmount(), "缺少金额!");
		Assert.isTrue(
				OrderType.CHAN_MANUAL_IN.getValue()
						.equals(order.getOrderType())
						|| OrderType.CHAN_MANUAL_OUT.getValue().equals(
								order.getOrderType()), "订单类型不正确!");
		MemberEntity member = ControllerUtil.retriveFromSession(request,
				CommonConstants.SESSION_LOGIN_USER, MemberEntity.class);
		if (!StringUtils.isEmpty(order.getOrderId())) {
			order.setExt1(member.getChanUserName());
			order.setOrderTime(new Date());
			orderRepository.updateStatus(order);
		} else {
			order.setOrderStatus(OrderStatus.INIT.getValue());
			order.setExt1(member.getChanUserName());
			order.setOrderTime(new Date());
			order.setChanId(member.getChanId());
			order.setChanUserId(member.getChanUserId());
			order.setProductId("");
			order.setMemberId(member.getMemberId());
			// order.setMemo("");
			accountService.saveOrderEntity(order);
		}
		log.info("{}-出入款-操作订单:{}", LogProfix, JSONUtil.toJson(order));

		return null;
	}

	/**
	 * 账户列表
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/list")
	public RestResult accounts(HttpServletRequest request,
			@RequestParam(defaultValue = "1") int pageNum,
			@RequestParam(defaultValue = "10") int pageSize, String userId) {

		RestResult result = accountService.queryAccountOverview(pageNum,
				pageSize, userId);
		List<MemberEntity> memberEntities = (List<MemberEntity>) result
				.getResult();
		List<MemberEntityVM> vms = new ArrayList<MemberEntityVM>();
		memberEntities.forEach(x -> {
			MemberEntityVM vm = new MemberEntityVM(x);
			vm.setAccountInfo(accountService.calcOverview(x));
			vms.add(vm);
		});
		result.setResult(vms);
		log.info("会员账户信息:{}", JSONUtil.toJson(result));
		return result;
	}

	/**
	 * 查询账户 资产一览
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/overview/subject")
	public Object subjectOverview(HttpServletRequest request,
			@RequestParam String userId) {

		MemberEntity member = ControllerUtil.retriveFromSession(request,
				CommonConstants.SESSION_LOGIN_USER, MemberEntity.class);
		member = memberService.getMemberEntityByChanUserId(member.getChanId(),
				userId);
		Assert.notNull(member, "不存在对应的账户信息!userId=" + userId);
		Map<String, Object> overview = accountService
				.queryInvestOverview(member);
		overview.put("member", member);
		log.info("定期资产一览:{}", JSONUtil.toJson(overview));
		return overview;
	}

	/**
	 * 进行审核
	 * 
	 * @param request
	 * @param orders
	 *            订单编号列表
	 * @param status
	 *            订单的成功(通过)或者失败(驳回)状态
	 */
	@RequestMapping(value = "/doaudit")
	public void doAudit(HttpServletRequest request,
			@RequestParam String[] orders, @RequestParam String status) {
		MemberEntity member = ControllerUtil.retriveFromSession(request,
				CommonConstants.SESSION_LOGIN_USER, MemberEntity.class);
		Assert.isTrue(OrderStatus.SUCCESS.getValue().equals(status)
				|| OrderStatus.FAIL.getValue().equals(status),
				"审核失败!只能同意或者驳回!status=" + status);
		log.info("{}-审核出入款-订单列表:{}", LogProfix, JSONUtil.toJson(orders));
		accountService.auditPublicAccount(orders, member.getChanUserName(),
				status);
		log.info("{}-审核出入款-操作成功!", LogProfix);
	}

	/**
	 * 查询 账户变更历史
	 * 
	 * @param request
	 * @param pageSize
	 * @param pageNum
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@RequestMapping("/changehistory")
	public QueryEntryResponse queryChangeHistory(HttpServletRequest request,
			@RequestParam(defaultValue = "10") int pageSize,
			@RequestParam(defaultValue = "1") int pageNum,
			@RequestParam(defaultValue = "0") long startTime,
			@RequestParam(defaultValue = "0") long endTime) {
		MemberEntity member = ControllerUtil.retriveFromSession(request,
				CommonConstants.SESSION_LOGIN_USER, MemberEntity.class);

		EntryQueryParams param = new EntryQueryParams();

		param.setMemberId(member.getMemberId());
		if (startTime > 0 && endTime > 0) {
			Assert.isTrue(endTime >= startTime, "结束时间应该大于起始时间!");
		}
		if (startTime > 0) {
			param.setStartDate(new Date(startTime));
		}
		if (endTime > 0) {
			param.setEndDate(new Date(endTime));
		}
		Paginator paginator = new Paginator(pageSize);
		paginator.setPage(pageNum);
		param.setPaginator(paginator);

		log.info("{}-查询账户变更历史-请求参数:{}", LogProfix, JSONUtil.toJson(param));
		EntryQueryResult result = accountService.queryEntry(param);
		log.info("{}-查询账户变更历史-响应信息:{}", LogProfix, JSONUtil.toJson(result));

		QueryEntryResponse response = new QueryEntryResponse(result);
		if (response.getResult() != null) {
			List<EntryEntity> vms = new ArrayList<EntryEntity>();
			response.getResult().forEach(x -> {
				vms.add(new EntryEntityVM(x));
			});
			response.setResult(vms);
		}
		return response;
	}

	/**
	 * 查询充值提现交易记录
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param request
	 * @param date
	 *            yyyyMMdd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/trade", method = RequestMethod.GET)
	public RestResult trade(@RequestParam(defaultValue = "1") int pageNum,
			@RequestParam(defaultValue = "10") int pageSize,
			HttpServletRequest request, @RequestParam String date,
			@RequestParam(defaultValue = "") String type, String orderStatus,
			String userId) throws Exception {
		MemberEntity member = ControllerUtil.retriveFromSession(request,
				CommonConstants.SESSION_LOGIN_USER, MemberEntity.class);
		Date date2 = StringUtils.isEmpty(date) ? null : day.parse(date);
		List<String> types = new ArrayList<String>();
		ArrayUtil.toList(type.split(",")).forEach(
				x -> {
					if (StringUtils.isEmpty((String) x)) {
						return;
					}
					if (PaymentRecordEnum.ACTION_DEPOSIT.getValue().equals(x)
							|| OrderType.DEPOSIT.getValue().equals(x)) {
						types.add(OrderType.DEPOSIT.getValue());
					} else if (PaymentRecordEnum.ACTION_WITHDRAW.getValue()
							.equals(x)
							|| OrderType.WITHDRAW.getValue().equals(x)) {
						types.add(OrderType.WITHDRAW.getValue());
					} else {
						types.add((String) x);
					}
				});

		List<String> statuses = null;
		if (orderStatus != null) {
			statuses = ArrayUtil.toList(orderStatus.split(","));
		}
		RestResult result = orderRepository.queryAccountTrade(pageNum,
				pageSize, date2, member.getChanId(), types, statuses, userId);
		log.info("{}-充值提现交易记录：{}", date, JSONUtil.toJson(result));
		List<OrderEntiryVM> vms = new ArrayList<OrderEntiryVM>();
		List<OrderEntity> orders = (List<OrderEntity>) result.getResult();
		orders.forEach(x -> {
			MemberEntity user = memberService.getMemberEntity(x.getMemberId());
			vms.add(new OrderEntiryVM(x, user));
		});
		result.setResult(vms);
		log.info("{}-充值提现交易记录-视图模型：{}", date, JSONUtil.toJson(result));
		return result;
	}

	@RequestMapping(value = "/tradeoverview", method = RequestMethod.GET)
	public Object tradeOverview(HttpServletRequest request,
			@RequestParam String date) throws Exception {
		MemberEntity member = ControllerUtil.retriveFromSession(request,
				CommonConstants.SESSION_LOGIN_USER, MemberEntity.class);
		Date date2 = day.parse(date);

		Map<String, Object> param = new HashMap<String, Object>();
		List<String> orderTypes = new ArrayList<String>();
		orderTypes.add(OrderType.WITHDRAW.getValue());
		orderTypes.add(OrderType.DEPOSIT.getValue());

		param.put("orderTypes", orderTypes);
		param.put("orderStatus", OrderStatus.SUCCESS.getValue());

		param.put("plat", member.getChanId());
		param.put("date", date2);

		List<PaymentEntity> nettingList = manualEntityMapper
				.queryAccountNetting(param);
		Map<String, String> result = new HashMap<String, String>();
		BigDecimal net = BigDecimal.ZERO, deposit = BigDecimal.ZERO, withdraw = BigDecimal.ZERO;
		if (nettingList != null) {
			for (PaymentEntity e : nettingList) {
				if (OrderType.DEPOSIT.getValue().equals(e.getPaymentType())) {
					deposit = e.getAmount();
				} else if (OrderType.WITHDRAW.getValue().equals(
						e.getPaymentType())) {
					withdraw = e.getAmount();
				}
			}
			net = deposit.subtract(withdraw);
		}
		// if (net.compareTo(BigDecimal.ZERO) > 0) {
		// result.put("netV", "");
		// } else {
		// result.put("netV", "入款");
		// }
		result.put("netV", date + "-轧差");
		result.put("net", FormatUtil.formatRate(net.abs()));
		result.put("withdraw", FormatUtil.formatRate(withdraw));
		result.put("deposit", FormatUtil.formatRate(deposit));

		log.info("{}-{}充值提现总览:{}", member.getChanId(), date,
				JSONUtil.toJson(result));
		return result;
	}

}
