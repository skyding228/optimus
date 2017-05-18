package com.netfinworks.optimus.service.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import com.netfinworks.common.lang.Paginator;
import com.netfinworks.invest.entity.BidOrderInfoResult;
import com.netfinworks.invest.entity.InvestInstallment;
import com.netfinworks.invest.entity.InvestRepaymentInfoResult;
import com.netfinworks.invest.entity.InvestSubjectInfoResult;
import com.netfinworks.invest.facade.InvestQueryFacade;
import com.netfinworks.invest.request.InvestScheduleInfoQueryRequest;
import com.netfinworks.invest.request.InvestSubjectInfoQueryRequest;
import com.netfinworks.invest.response.BidOrderInfoQueryResponse;
import com.netfinworks.invest.response.InvestPreProfitQueryResponse;
import com.netfinworks.invest.response.InvestRepaymentInfoQueryResponse;
import com.netfinworks.invest.response.InvestScheduleInfoQueryResponse;
import com.netfinworks.invest.response.InvestSubjectInfoQueryResponse;
import com.netfinworks.invest.response.ReceivedProfitQueryResponse;
import com.netfinworks.optimus.PayRequest;
import com.netfinworks.optimus.domain.AccountInfo;
import com.netfinworks.optimus.domain.OrderRequest;
import com.netfinworks.optimus.domain.OrderRequestLambda;
import com.netfinworks.optimus.domain.OrderResponse;
import com.netfinworks.optimus.domain.RestResult;
import com.netfinworks.optimus.domain.TokenBean;
import com.netfinworks.optimus.domain.enums.InvestSubjectStatusKind;
import com.netfinworks.optimus.domain.enums.OrderStatus;
import com.netfinworks.optimus.domain.enums.OrderType;
import com.netfinworks.optimus.domain.enums.PaymentRecordEnum;
import com.netfinworks.optimus.domain.vm.BidOrderInfoResultVM;
import com.netfinworks.optimus.domain.vm.InvestRepaymentInfoResultVM;
import com.netfinworks.optimus.entity.AccountCheckEntity;
import com.netfinworks.optimus.entity.AccountEntity;
import com.netfinworks.optimus.entity.AccountEntityExample;
import com.netfinworks.optimus.entity.ChanStatsEntity;
import com.netfinworks.optimus.entity.EntryEntity;
import com.netfinworks.optimus.entity.EntryEntityExample;
import com.netfinworks.optimus.entity.MemberEntity;
import com.netfinworks.optimus.entity.MemberEntityExample;
import com.netfinworks.optimus.entity.OrderEntity;
import com.netfinworks.optimus.entity.OrderEntityExample;
import com.netfinworks.optimus.entity.PaymentEntity;
import com.netfinworks.optimus.factory.EntityFactory;
import com.netfinworks.optimus.mapper.AccountEntityMapper;
import com.netfinworks.optimus.mapper.EntryEntityMapper;
import com.netfinworks.optimus.mapper.ManualEntityMapper;
import com.netfinworks.optimus.mapper.MemberEntityMapper;
import com.netfinworks.optimus.mapper.OrderEntityMapper;
import com.netfinworks.optimus.service.AccountService;
import com.netfinworks.optimus.service.ConfigService;
import com.netfinworks.optimus.service.MemberService;
import com.netfinworks.optimus.service.auth.AuthService;
import com.netfinworks.optimus.service.entity.EntryQueryParams;
import com.netfinworks.optimus.service.entity.EntryQueryResult;
import com.netfinworks.optimus.service.fund.FundConstants;
import com.netfinworks.optimus.service.integration.invest.EnvUtil;
import com.netfinworks.optimus.service.integration.invest.impl.InvestServiceImpl;
import com.netfinworks.optimus.service.repository.AccountRepository;
import com.netfinworks.optimus.service.repository.OrderRepository;
import com.netfinworks.optimus.service.repository.impl.PaymentRepositoryImpl;
import com.netfinworks.optimus.utils.BeanUtil;
import com.netfinworks.optimus.utils.FormatUtil;
import com.netfinworks.optimus.utils.LambdaUtil;
import com.netfinworks.util.JSONUtil;

@Service
public class AccountServiceImpl implements AccountService {

	private static Logger log = LoggerFactory
			.getLogger(AccountServiceImpl.class);

	private static final String LogPrefix = "账户服务";

	private static SimpleDateFormat day = new SimpleDateFormat("-yyyyMMdd");

	private RestTemplate restTmpl = new RestTemplate();

	@Resource
	private AccountRepository accountRepository;

	@Resource
	private OrderRepository orderRepository;

	@Resource
	private PaymentRepositoryImpl paymentRepository;

	@Resource
	private OrderEntityMapper orderEntityMapper;

	@Resource
	private EntryEntityMapper entryEntityMapper;

	@Resource
	private MemberEntityMapper memberEntityMapper;

	@Resource(name = "investService")
	private InvestServiceImpl investService;

	@Autowired
	private InvestQueryFacade investQueryFacade;

	@Resource
	private ConfigService configService;

	@Resource
	private ManualEntityMapper manualEntityMapper;

	@Resource
	private AccountEntityMapper accountEntityMapper;

	@Resource
	private MemberService memberService;

	// 投资期限转换为天
	public static Map<String, Long> LoanTermDay = new HashMap<String, Long>();
	private static DecimalFormat decimalFormat = new DecimalFormat("#0.00000");
	static {
		for (int i = 1; i < 50; i++) {
			LoanTermDay.put(i + "月", i * 30L);
			// LoanTermDay.put(i + "年", i * 360L);
		}
	}

	@Override
	public AccountEntity getAccount(String accountId) {

		return accountRepository.getAccount(accountId);
	}

	@Override
	public AccountEntity updateAccountBalance(OrderEntity order) {

		return accountRepository.updateAccountBalance(order);
	}

	public AccountEntity deposit(BigDecimal amount, String pwd, String url,
			TokenBean bean) {

		MemberEntity member = bean.getMember();
		OrderEntity order = createOrder(member, amount,
				OrderType.DEPOSIT.getValue(), "充值");

		OrderRequestLambda request = new OrderRequestLambda();
		request.setUrl(configService.getDepositUrl(member.getChanId()));
		request.setPlat(member.getChanId());

		OrderRequest param = new OrderRequest();
		param.setOrderId(order.getOrderId());
		param.setAmount(order.getAmount());
		param.setNotifyUrl(url);// "http://func92.vfinance.cn/optimus-h5/api/callback/account/deposit"
		param.setPwd(pwd);// "37b8acd28f207ce2"
		param.setToken(bean.getToken());// "0I4wPSz3uA9HpVFDme8e"

		request.setRequest(param);
		// 调用接口充值
		OrderResponse response = LambdaUtil.function(this::deposit, request,
				"账户充值");

		return afterAccountChange(response);
	}

	/**
	 * 调用充值提现接口之后
	 * 
	 * @param response
	 */
	public AccountEntity afterAccountChange(OrderResponse response) {
		log.info("充值提现接口响应数据:{}", JSONUtil.toJson(response));
		OrderEntity order = orderEntityMapper.selectByPrimaryKey(response
				.getOriginOrderId());

		Assert.notNull(order, "不存在对应的订单信息!" + JSONUtil.toJson(response));

		order.setOuterOrderId(response.getOrderId());
		order.setOrderTime(new Date(response.getOrderTime()));
		order.setMemo(response.getReason());

		if ("SUCCESS".equals(response.getStatus())) {
			order.setOrderStatus(OrderStatus.SUCCESS.getValue());
		} else {
			order.setOrderStatus(OrderStatus.FAIL.getValue());
		}
		AccountEntity account = accountRepository.updateAccountBalance(order);
		// 调用不成功返回，accountid为空,accountName 是错误原因
		if (!OrderStatus.SUCCESS.getValue().equals(order.getOrderStatus())) {
			account = new AccountEntity();
			account.setAccountName(response.getReason());
		}
		return account;
	}

	private OrderResponse deposit(OrderRequestLambda request) {

		Map<String, String> param = BeanUtil.getPropertyMap(request
				.getRequest());
		String restText = restTmpl.postForObject(request.getUrl(),
				AuthService.restFormEntity(request.getPlat(), param),
				String.class);
		log.info("调用第三方API:{}-{}", JSONUtil.toJson(request), restText);
		return JSONUtil.fromJson(restText, OrderResponse.class);
	}

	@Override
	public AccountEntity withdraw(BigDecimal amount, String pwd, String url,
			TokenBean bean) {

		MemberEntity member = bean.getMember();
		// 进行校验
		AccountEntity account = accountRepository.getAccount(member
				.getAccountId());
		if (account == null) {
			account = new AccountEntity();
			account.setAccountName("未查询到对应账户信息!");
			log.error("未查询到对应账户信息!{}", JSONUtil.toJson(member));
			return account;
		} else if (account.getBalance().compareTo(amount) < 0) {
			account.setAccountId(null);
			account.setAccountName("余额不足!");
			log.info("提现余额不足:balance={},提现={}", account.getBalance(), amount);
			return account;
		}

		OrderEntity order = createOrder(member, amount,
				OrderType.WITHDRAW.getValue(), "提现");

		OrderRequestLambda request = new OrderRequestLambda();
		request.setUrl(configService.getWithDrawUrl(member.getChanId()));
		request.setPlat(member.getChanId());

		OrderRequest param = new OrderRequest();
		param.setOrderId(order.getOrderId());
		param.setAmount(order.getAmount());
		param.setNotifyUrl(url);// "http://func92.vfinance.cn/optimus-h5/api/callback/account/deposit"
		param.setPwd(pwd);// "37b8acd28f207ce2"
		param.setToken(bean.getToken());// "0I4wPSz3uA9HpVFDme8e"

		request.setRequest(param);
		// 调用接口充值
		OrderResponse response = LambdaUtil.function(this::deposit, request,
				"账户提现");

		return afterAccountChange(response);
	}

	/**
	 * 生成 初始化订单信息
	 * 
	 * @param member
	 * @param amount
	 * @param orderType
	 *            订单类型
	 * @param orderDesc
	 *            订单描述信息
	 * @return
	 */
	private OrderEntity createOrder(MemberEntity member, BigDecimal amount,
			String orderType, String orderDesc) {
		// 查询金额账户
		AccountEntity account = getAccount(member.getAccountId());

		Assert.notNull(account, "找不到用户对应的账户信息!memberId=" + member.getMemberId()
				+ ",accountId=" + member.getAccountId());

		// 构造order
		OrderEntity order = EntityFactory.orderEntity(member.getChanId(),
				member.getChanUserId(), member.getMemberId(), amount,
				OrderStatus.INIT.getValue(), orderDesc, "", orderType);

		log.info("生成" + orderDesc + "订单:{}", JSONUtil.toJson(order));
		// 保存初始订单
		orderRepository.saveOrder(order);

		return order;
	}

	/**
	 * 生成 初始化订单信息
	 * 
	 * @param member
	 * @param amount
	 * @param orderType
	 *            订单类型
	 * @param orderDesc
	 *            订单描述信息
	 * @return
	 */
	private OrderEntity createOrder(MemberEntity member, BigDecimal amount,
			String orderType, String orderDesc, PayRequest request) {
		// 查询金额账户
		AccountEntity account = getAccount(member.getAccountId());

		Assert.notNull(account, "找不到用户对应的账户信息!memberId=" + member.getMemberId()
				+ ",accountId=" + member.getAccountId());

		// 构造order
		OrderEntity order = EntityFactory.orderEntity(member.getChanId(),
				member.getChanUserId(), member.getMemberId(), amount,
				OrderStatus.INIT.getValue(), orderDesc, "", orderType);

		order.setProductId(request.getProductId());
		order.setOuterOrderId(request.getOuterOrderId());
		order.setMemo(request.getMemo());

		order.setExt1(request.getExt1());
		order.setExt2(request.getExt2());
		log.info("生成" + orderDesc + "订单:{}", JSONUtil.toJson(order));
		// 保存初始订单
		orderRepository.saveOrder(order);

		return order;
	}

	@Override
	public OrderEntity pay(PayRequest payRequest, MemberEntity member) {
		// 查询金额账户
		AccountEntity account = getAccount(member.getAccountId());
		Assert.notNull(account, "找不到用户对应的账户信息!memberId=" + member.getMemberId()
				+ ",accountId=" + member.getAccountId());

		Assert.notNull(payRequest, "支付请求对象不能为null");
		Assert.notNull(payRequest.getOrderType(), "orderType不能为null");

		OrderType type = payRequest.getOrderType();
		OrderEntity orderEntity = orderRepository.getOrderByOuterNo(payRequest
				.getOuterOrderId());
		if (orderEntity != null
				&& OrderStatus.SUCCESS.getValue().equals(
						orderEntity.getOrderStatus()))
			return orderEntity;
		if (OrderType.REFUND == type) {
			OrderEntity origOrderEntity = orderRepository
					.getOrderByOuterNo(payRequest.getOrigOuterOrderId());
			Assert.notNull(origOrderEntity, "退款原来订单不存在 origOuterOrderId="
					+ payRequest.getOrigOuterOrderId());
			Assert.isTrue(
					origOrderEntity.getAmount().equals(payRequest.getAmount()),
					"退款金额与原来订单金额不等 origAmount=" + origOrderEntity.getAmount()
							+ "  nowAmount=" + payRequest.getAmount());
		}
		OrderEntity order = null;
		// 存在 未成功的订单 I 重新支付 有外键唯一约束
		if (orderEntity != null) {
			order = orderEntity;
			order.setOrderStatus(OrderStatus.SUCCESS.getValue());
			order.setMemo(payRequest.getMemo());
		} else {
			order = createOrder(member, payRequest.getAmount(),
					type.getValue(), payRequest.getMemo(), payRequest);
			order.setOrderStatus(OrderStatus.SUCCESS.getValue());
		}
		long start = System.currentTimeMillis();
		accountRepository.updateAccountBalance(order);
		log.info("---pay cost :{}", System.currentTimeMillis() - start);
		// fail status 状态先还是 I
		return order;
	}

	@Override
	public EntryQueryResult queryEntry(EntryQueryParams entryQueryParams) {

		return accountRepository.queryEntry(entryQueryParams);
	}

	@Override
	public ChanStatsEntity queryTotalBalanceByChan(String chanId) {
		return accountRepository.queryTotalBalanceByChan(chanId);
	}

	@Override
	public void auditPublicAccount(String[] orderIdList, String operation,
			String status) {
		accountRepository.auditPublicAccount(orderIdList, operation, status);
	}

	@Override
	public void saveOrderEntity(OrderEntity orderentity) {
		orderRepository.saveOrder(orderentity);
	}

	/**
	 * 查询待审核 记录
	 * 
	 * @param chanId
	 *            渠道编号
	 * @param types
	 *            类型列表
	 * @return
	 */
	public List<OrderEntity> queryWaitAuditList(String chanId,
			List<String> types) {
		OrderEntityExample example = new OrderEntityExample();

		example.createCriteria()
				.andOrderStatusEqualTo(OrderStatus.INIT.getValue())
				.andOrderTypeIn(types).andChanIdEqualTo(chanId)
				.andOuterOrderIdIsNotNull().andExt1IsNotNull();// 屏蔽给个人用户手工操作状态为I
		example.setLimitStart(0);
		example.setLimitEnd(2000);
		example.setOrderByClause("order_time");
		List<OrderEntity> orders = orderEntityMapper.selectByExample(example);

		return orders;
	}

	/**
	 * 查询待执行 记录
	 * 
	 * @param chanId
	 *            渠道编号
	 * @return
	 */
	public List<OrderEntity> queryWaitExecList(String chanId) {
		OrderEntityExample example = new OrderEntityExample();
		List<String> types = new ArrayList<String>();
		types.add(OrderType.CHAN_MANUAL_IN.getValue());
		types.add(OrderType.CHAN_MANUAL_OUT.getValue());
		example.createCriteria()
				.andOrderStatusEqualTo(OrderStatus.INIT.getValue())
				.andOrderTypeIn(types).andChanIdEqualTo(chanId).andExt1IsNull();
		example.setLimitStart(0);
		example.setLimitEnd(1000);
		example.setOrderByClause("order_time");
		List<OrderEntity> orders = orderEntityMapper.selectByExample(example);

		return orders;
	}

	/**
	 * 更新待出入款记录，插入表中
	 * 
	 * @param plat
	 *            平台编码
	 */
	public void updatePaymentRecords(String plat) {
		InvestSubjectInfoQueryRequest req = new InvestSubjectInfoQueryRequest();

		List<String> status = new ArrayList<String>();
		status.add(InvestSubjectStatusKind.BIDFULL.getCode());
		status.add(InvestSubjectStatusKind.FINISH.getCode());
		req.setStatus(status);

		// req.setSubjectType(configService.getSubjectType(plat));
		req.setPageSize(Integer.MAX_VALUE);
		req.setPageNum(1);

		log.info("{}-待出款-定期请求参数:{}", LogPrefix, JSONUtil.toJson(req));
		InvestSubjectInfoQueryResponse resp = investService
				.queryInvestSubject(req);
		log.info("{}-待出款-定期响应信息:{}", LogPrefix, JSONUtil.toJson(resp));

		// 对定期产品进行处理
		List<InvestSubjectInfoResult> result = resp.getResult();
		if (result != null) {
			result.forEach(x -> {
				InvestSubjectInfoResult r = x;
				// 出款
				if (InvestSubjectStatusKind.BIDFULL.getCode().equals(
						r.getStatus())) {
					if (!paymentRepository.isInPaymentRecords(r.getSubjectNo(),
							PaymentRecordEnum.PAYMENT_OUT,
							PaymentRecordEnum.ACTION_LOAN, null)) {
						// paymentRepository.insert(configService
						// .getPlatBySubjectType(r.getSubjectType()), r
						// .getSubjectNo(), r.getSubjectName(), r
						// .getApplyAmount().getAmount(),
						// PaymentRecordEnum.ACTION_LOAN,
						// PaymentRecordEnum.PAYMENT_OUT,
						// PaymentRecordEnum.SUBJECT);
						paymentRepository.insert(PaymentRecordEnum.VFINANCE
								.getValue(), r.getSubjectNo(), r
								.getSubjectName(), r.getApplyAmount()
								.getAmount(), PaymentRecordEnum.ACTION_LOAN,
								PaymentRecordEnum.PAYMENT_OUT,
								PaymentRecordEnum.SUBJECT, configService
										.getPlatBySubjectType(r
												.getSubjectType()));
					}
				} else if (InvestSubjectStatusKind.FINISH.getCode().equals(
						r.getStatus())) {
					if (!paymentRepository.isInPaymentRecords(r.getSubjectNo(),
							PaymentRecordEnum.PAYMENT_IN,
							PaymentRecordEnum.ACTION_REPAYMENT, null)) {
						// paymentRepository.insert(
						// configService.getPlatBySubjectType(r
						// .getSubjectType()),
						// r.getSubjectNo(),
						// r.getSubjectName(),
						// queryTotalPrincipalAndInterest(r.getSubjectNo()),
						// PaymentRecordEnum.ACTION_REPAYMENT,
						// PaymentRecordEnum.PAYMENT_IN,
						// PaymentRecordEnum.SUBJECT);
						paymentRepository.insert(
								PaymentRecordEnum.VFINANCE.getValue(),
								r.getSubjectNo(),
								r.getSubjectName(),
								queryTotalPrincipalAndInterest(r.getSubjectNo()),
								PaymentRecordEnum.ACTION_REPAYMENT,
								PaymentRecordEnum.PAYMENT_IN,
								PaymentRecordEnum.SUBJECT, configService
										.getPlatBySubjectType(r
												.getSubjectType()));
					}
				}

			});
		}

		// 对基金进行处理,实现轧差接口，基金系统自动推送
		// queryNettingOrder(null, plat);

		netting();

		// 支付过期产品的备付金
		genPaymentOfOverDueSubject();

		// 备付金还款
		genPaymentOfProvisionRepayment();
	}

	/**
	 * 轧差所有平台的充值提现
	 */
	private void netting() {
		Map<String, Object> param = new HashMap<String, Object>();
		List<String> orderTypes = new ArrayList<String>();
		orderTypes.add(OrderType.WITHDRAW.getValue());
		orderTypes.add(OrderType.DEPOSIT.getValue());

		param.put("orderTypes", orderTypes);
		param.put("orderStatus", OrderStatus.SUCCESS.getValue());

		List<PaymentEntity> nettingList = manualEntityMapper
				.queryAccountNetting(param);
		log.info("充值提现轧差结果:{}", JSONUtil.toJson(nettingList));
		if (nettingList.size() == 0) {
			return;
		}

		nettingList
				.forEach(x -> {
					String date = x.getSubjectNo();
					if (OrderType.WITHDRAW.getValue()
							.equals(x.getPaymentType())) {
						String subjectNo = makeNettingSubjectNo(
								PaymentRecordEnum.VFINANCE.getValue(), date);
						// 如果存在可以查询出来进对比
						if (paymentRepository.isInPaymentRecords(subjectNo,
								PaymentRecordEnum.PAYMENT_OUT, null, null)) {
							return;
						}
						paymentRepository.insert(
								PaymentRecordEnum.VFINANCE.getValue(),
								subjectNo,
								PaymentRecordEnum.ACTION_WITHDRAW.getDesc()
										+ date, x.getAmount(),
								PaymentRecordEnum.ACTION_WITHDRAW,
								PaymentRecordEnum.PAYMENT_OUT,
								PaymentRecordEnum.ACCOUNT, x.getPlat());
					} else if (OrderType.DEPOSIT.getValue().equals(
							x.getPaymentType())) {
						String subjectNo = makeNettingSubjectNo(x.getPlat(),
								date);
						// 如果存在可以查询出来进对比
						if (paymentRepository.isInPaymentRecords(subjectNo,
								PaymentRecordEnum.PAYMENT_OUT, null, null)) {
							return;
						}
						paymentRepository.insert(x.getPlat(), subjectNo,
								PaymentRecordEnum.ACTION_DEPOSIT.getDesc()
										+ date, x.getAmount(),
								PaymentRecordEnum.ACTION_DEPOSIT,
								PaymentRecordEnum.PAYMENT_OUT,
								PaymentRecordEnum.ACCOUNT, x.getPlat());
					}
				});
	}

	// 轧差记录的唯一标识
	private String makeNettingSubjectNo(String plat, String date) {
		return plat + ":" + date;
	}

	/**
	 * 生成已过期产品的出入款记录，需要渠道出款支付备付金
	 */
	private void genPaymentOfOverDueSubject() {
		List<InvestSubjectInfoResult> subjects = investService
				.queryOverdueSubject();
		for (InvestSubjectInfoResult subject : subjects) {
			String plat = configService.getPlatBySubjectType(subject
					.getSubjectType());
			if (!paymentRepository.isInPaymentRecords(subject.getSubjectNo(),
					PaymentRecordEnum.PAYMENT_OUT,
					PaymentRecordEnum.ACTION_PROVISION_INVEST, null)) {

				// 生成备付金出入款记录
				paymentRepository.insert(plat, subject.getSubjectNo(),
						"备付金-投资-" + subject.getSubjectName(), subject
								.getBiddableAmount().getAmount(),
						PaymentRecordEnum.ACTION_PROVISION_INVEST,
						PaymentRecordEnum.PAYMENT_OUT,
						PaymentRecordEnum.SUBJECT, PaymentRecordEnum.VFINANCE
								.getValue());
			}

		}
	}

	/**
	 * 备付金还款记录
	 */
	private void genPaymentOfProvisionRepayment() {
		// 获取所有平台的备付金账户的所有还款记录
		List<String> plats = configService.getAllPlats();
		List<String> platMemberIds = new ArrayList<String>();
		for (String plat : plats) {
			platMemberIds.add(configService.getPlatValue(plat,
					ConfigService.PLAT_PROVISION_MEMBER));
		}
		List<String> orderTypes = new ArrayList<String>();
		orderTypes.add(OrderType.RECOVERY.getValue());

		List<OrderEntity> recoveryOrders = orderRepository
				.queryByMemberIdsAndOrderTypes(platMemberIds, orderTypes);
		log.info("查询备付金账户还款记录:{}->{}", JSONUtil.toJson(platMemberIds),
				JSONUtil.toJson(recoveryOrders));
		for (OrderEntity order : recoveryOrders) {

			if (!paymentRepository.isInPaymentRecords(order.getProductId(),
					PaymentRecordEnum.PAYMENT_OUT,
					PaymentRecordEnum.ACTION_PROVISION_REPAYMENT, null)) {
				// 获取产品信息
				InvestSubjectInfoResult subject = investService
						.querySubject(order.getProductId());
				// 判断是否已经存在
				String plat = configService.getPlatBySubjectType(subject
						.getSubjectType());
				// 生成备付金出入款记录
				paymentRepository.insert(PaymentRecordEnum.VFINANCE.getValue(),
						order.getProductId(),
						"备付金-还款-" + subject.getSubjectName(),
						order.getAmount(),
						PaymentRecordEnum.ACTION_PROVISION_REPAYMENT,
						PaymentRecordEnum.PAYMENT_OUT,
						PaymentRecordEnum.SUBJECT, plat);
			}
		}
	}

	/**
	 * 查询所有应还金额
	 * 
	 * @param subjectNo
	 * @return
	 */
	private BigDecimal queryTotalPrincipalAndInterest(String subjectNo) {
		InvestInstallment installment = queryInvestInstatllment(subjectNo);
		return installment == null ? BigDecimal.ZERO : installment
				.getPaidPrincipal().add(installment.getPaidInterest())
				.getAmount();
	}

	/**
	 * 查询分期还款信息
	 * 
	 * @param subjectNo
	 * @return
	 */
	public InvestInstallment queryInvestInstatllment(String subjectNo) {
		InvestScheduleInfoQueryRequest request = new InvestScheduleInfoQueryRequest();
		request.setSubjectNo(subjectNo);
		InvestScheduleInfoQueryResponse response = investQueryFacade
				.queryInvestScheduleInfo(request, EnvUtil.buildEnv());
		List<InvestInstallment> list = response.getInstallmentList();

		log.info("{}-查询还款信息-响应信息:{}", LogPrefix, JSONUtil.toJson(response));
		if (list != null && list.size() > 0) {
			InvestInstallment installment = list.get(0);
			return installment;
		}
		return null;
	}

	/**
	 * 查询轧差记录 直接放入对应的集合中
	 * 
	 * @param date
	 * @param ins
	 *            入款 集合
	 * @param outs
	 *            出款集合
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	private void queryNettingOrder(Date date, String plat) {
		if (plat == null) {
			return;
		}
		String subjectNo = configService.getValue(FundConstants.FUND_ID);
		String subjectName = configService.getValue(FundConstants.FUND_NAME);
		Map<String, Long> param = new HashMap<String, Long>();
		if (date != null) {
			param.put("dataTlong", date.getTime());
		}
		String url = configService.getPlatValue(plat,
				FundConstants.FUND_API_URL) + "/netting";
		RestResult result = restTmpl.getForObject(url, RestResult.class, param);
		log.info("{}-查询轧差记录-响应结果:{}", LogPrefix, result);
		List list = (List) result.getResult();
		Map<String, Object> json = null;
		if (list != null) {
			for (Object object : list) {
				if (object == null) {
					continue;
				}
				json = JSONUtil
						.fromJson(JSONUtil.toJson(object), HashMap.class);

				String suffix = day.format(new Date(MapUtils.getLong(json,
						"dateT")));
				if (paymentRepository.isInPaymentRecords(subjectNo + suffix,
						null, null, null)) {
					continue;
				}
				// 申购赎回
				if ("INVEST".equals(MapUtils.getString(json, "orderType"))) {
					paymentRepository.insert(
							configService.getPlatByFundId(subjectNo),
							subjectNo + suffix,
							subjectName + suffix,
							new BigDecimal(MapUtils.getDouble(json, "amount",
									new Double(0))),
							PaymentRecordEnum.ACTION_APPLY,
							PaymentRecordEnum.PAYMENT_OUT,
							PaymentRecordEnum.FUND, PaymentRecordEnum.VFINANCE
									.getValue());

					paymentRepository.insert(
							PaymentRecordEnum.VFINANCE.getValue(),
							subjectNo + suffix,
							subjectName + suffix,
							new BigDecimal(MapUtils.getDouble(json, "amount",
									new Double(0))),
							PaymentRecordEnum.ACTION_APPLY,
							PaymentRecordEnum.PAYMENT_OUT,
							PaymentRecordEnum.FUND, configService
									.getPlatByFundId(subjectNo));
				} else if ("REDEEM".equals(MapUtils
						.getString(json, "orderType"))) {
					paymentRepository.insert(
							configService.getPlatByFundId(subjectNo),
							subjectNo + suffix,
							subjectName + suffix,
							new BigDecimal(MapUtils.getDouble(json, "amount",
									new Double(0))),
							PaymentRecordEnum.ACTION_REDEEM,
							PaymentRecordEnum.PAYMENT_IN,
							PaymentRecordEnum.FUND, PaymentRecordEnum.VFINANCE
									.getValue());
					paymentRepository.insert(
							PaymentRecordEnum.VFINANCE.getValue(),
							subjectNo + suffix,
							subjectName + suffix,
							new BigDecimal(MapUtils.getDouble(json, "amount",
									new Double(0))),
							PaymentRecordEnum.ACTION_REDEEM,
							PaymentRecordEnum.PAYMENT_IN,
							PaymentRecordEnum.FUND, configService
									.getPlatByFundId(subjectNo));
				}
			}
		}
	}

	/**
	 * 生成对账文件
	 * 
	 * @param chanId
	 *            渠道编号
	 * @param date
	 *            对账日期
	 */
	public List<AccountCheckEntity> makeAccountCheckingFile(String chanId,
			String date, int pageNum, int pageSize) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("chanId", chanId);
		map.put("date", date);
		map.put("offset", (pageNum - 1) * pageSize);
		map.put("pageSize", pageSize);
		List<AccountCheckEntity> checkEntiries = manualEntityMapper
				.queryAccountChecking(map);
		log.info("{}-{}[{}-{}]-对账文件:{}", chanId, date, pageNum, pageSize,
				JSONUtil.toJson(checkEntiries));
		return checkEntiries;
	}

	public static void main(String[] args) {
		RestTemplate restTemplate = new RestTemplate();
		// restTemplate.
		String response = restTemplate.getForObject("https://www.finapm.com/",
				String.class);
		System.out.println(response);
	}

	@Override
	public RestResult queryAccountOverview(int pageNum, int pageSize,
			String userId) {
		Paginator paginator = new Paginator(pageSize);
		paginator.setPage(pageNum);

		MemberEntityExample example = new MemberEntityExample();
		example.setLimitStart(paginator.getOffset());
		example.setLimitEnd(paginator.getItemsPerPage());
		example.setOrderByClause("create_time");

		MemberEntityExample.Criteria criteria = example.createCriteria()
				.andTypeEqualTo("INVEST");

		if (!StringUtils.isEmpty(userId)) {
			criteria.andChanUserIdEqualTo(userId);
		}

		int total = memberEntityMapper.countByExample(example);
		List<MemberEntity> memberEntities = memberEntityMapper
				.selectByExample(example);

		// 查询账户余额
		List<String> ids = new ArrayList<String>();
		memberEntities.forEach(x -> {
			ids.add(x.getAccountId());
		});
		List<AccountEntity> accountEntities = queryAccounts(ids);
		Map<String, BigDecimal> balanceMap = new HashMap<String, BigDecimal>();
		accountEntities.forEach(x -> {
			balanceMap.put(x.getAccountId(), x.getBalance());
		});
		// 用 paypasswd 保存账户余额
		memberEntities.forEach(x -> {
			BigDecimal balance = balanceMap.get(x.getAccountId());
			x.setPayPasswd(FormatUtil
					.formatRate(balance == null ? BigDecimal.ZERO : balance));
		});

		paginator.setItems(total);
		RestResult result = new RestResult();
		result.setItemCount(total);
		result.setPageCount(paginator.getPages());
		result.setPageIndex(paginator.getPage());
		result.setPageSize(paginator.getItemsPerPage());
		result.setResult(memberEntities);
		return result;
	}

	private List<AccountEntity> queryAccounts(List<String> ids) {
		if (ids == null || ids.size() == 0) {
			return new ArrayList<AccountEntity>();
		}
		AccountEntityExample example = new AccountEntityExample();
		example.createCriteria().andAccountIdIn(ids);
		return accountEntityMapper.selectByExample(example);
	}

	/**
	 * 计算会员账户总览
	 * 
	 * @param member
	 * @return
	 */
	public AccountInfo calcOverview(MemberEntity member) {
		AccountInfo info = new AccountInfo();
		// 查询账户余额
		AccountEntity account = getAccount(member.getAccountId());
		Assert.notNull(account, "未查询到相关账户信息:accountId=" + member.getAccountId());
		info.setBalance(account.getBalance());

		BidOrderInfoQueryResponse notConfirmResp = investService
				.querySubjectNotConfirm(member.getMemberId());
		BigDecimal notConfirmMoney = BigDecimal.ZERO;
		if (notConfirmResp.getTotalFreezeAmt() != null) {
			notConfirmMoney = notConfirmResp.getTotalFreezeAmt().getAmount();
		}
		// 查询定期产品的未确认的投资
		info.setSubjectAsset(info.getSubjectAsset().add(notConfirmMoney));
		// 查询定期产品已确认未还款的投资
		info.setSubjectAsset(info.getSubjectAsset().add(
				querySubjectConfirm(member.getMemberId())));
		// 查询定期产品在途收益
		InvestPreProfitQueryResponse preResponse = investService
				.queryPreProfit(member.getMemberId());
		info.setSubjectPreProfit(preResponse.getPreProfit().getAmount());
		// 查询已获收益
		ReceivedProfitQueryResponse receivedProfitResp = investService
				.queryReceivedProfit(member.getMemberId());
		info.setTotalProfit(receivedProfitResp.getAllReceivedProfit()
				.getAmount());

		// 查询基金投资
		// XXX
		info.setTotalAsset(info.getTotalAsset().add(info.getBalance())
				.add(info.getSubjectAsset()));

		info.setUserName(member.getChanUserName());
		log.info("accountid ={}-账户总览:{}", member.getAccountId(),
				JSONUtil.toJson(info));
		info.format();
		return info;
	}

	// 查询定期产品已确认未还款的投资
	private BigDecimal querySubjectConfirm(String memberId) {
		List<InvestRepaymentInfoResult> list = investService
				.querySubjectConfirm(memberId, null);
		BigDecimal amount = BigDecimal.ZERO;
		if (list != null) {
			for (InvestRepaymentInfoResult info : list) {
				amount = amount.add(info.getExpectedRecovery().getAmount());
			}
		}
		return amount;
	}

	/**
	 * 查询个人投资概况
	 * 
	 * @param member
	 * @return {subjectNotConfirmV：未确认金额 subjectNotConfirmList: 未确认产品列表、
	 *         subjectConfirmV
	 *         /subjectConfirmList/subjectFinishV/subjectFinishList }
	 */
	public Map<String, Object> queryInvestOverview(MemberEntity member) {
		Map<String, Object> resp = new HashMap<String, Object>();
		BidOrderInfoQueryResponse notConfrimResp = investService
				.querySubjectNotConfirm(member.getMemberId());
		BigDecimal notConfirmMoney = BigDecimal.ZERO;
		if (notConfrimResp.getTotalFreezeAmt() != null) {
			notConfirmMoney = notConfrimResp.getTotalFreezeAmt().getAmount();
		}

		List<BidOrderInfoResult> notConfirmList = notConfrimResp.getResult();
		List<BidOrderInfoResult> notConfirmVms = new ArrayList<BidOrderInfoResult>();
		if (notConfirmList != null) {
			notConfirmList.forEach(x -> {
				BidOrderInfoResultVM vm = new BidOrderInfoResultVM(x,
						memberService, investService);
				vm.setPredictProfitV(calcPredictProfit(x.getSubjectNo(), x
						.getAmount().getAmount()));
				notConfirmVms.add(vm);
			});
		}
		resp.put("subjectNotConfirmV", FormatUtil.formatRate(notConfirmMoney));
		resp.put("subjectNotConfirmList", notConfirmVms);
		InvestRepaymentInfoQueryResponse finishResp = investService
				.querySubjectFinish(member.getMemberId());
		BigDecimal amount = BigDecimal.ZERO;
		List<InvestRepaymentInfoResult> finishList = finishResp.getResult();
		List<InvestRepaymentInfoResult> finishVms = new ArrayList<InvestRepaymentInfoResult>();
		if (finishList != null) {
			for (InvestRepaymentInfoResult info : finishList) {
				amount = amount.add(info.getTotalPrincipalAndInterest()
						.getAmount());
				// 查询还款时间
				InvestRepaymentInfoResultVM vm = new InvestRepaymentInfoResultVM(
						info);
				Date date = investService.querySubjectRecoveryDate(
						info.getSubjectNo(), member.getMemberId());
				vm.setPredictRepayDateV(date == null ? "" : FormatUtil
						.formatDate(date));
				finishVms.add(vm);
			}
		}
		resp.put("subjectFinishV", FormatUtil.formatRate(amount));
		resp.put("subjectFinishList", finishVms);
		BigDecimal confirm = BigDecimal.ZERO;
		List<InvestRepaymentInfoResult> list = investService
				.querySubjectConfirm(member.getMemberId(), null);
		List<InvestRepaymentInfoResultVM> vms = new ArrayList<InvestRepaymentInfoResultVM>();
		if (list != null) {
			for (InvestRepaymentInfoResult invest : list) {
				confirm = confirm.add(invest.getExpectedRecovery().getAmount());

				InvestRepaymentInfoResultVM vm = new InvestRepaymentInfoResultVM(
						invest);
				// 查询预计还款时间
				if (InvestSubjectStatusKind.REPAYING.getCode().equals(
						vm.getSubjectStatus())) {
					Date date = investService.querySubjectPredictRepayDate(vm
							.getSubjectNo());
					vm.setPredictRepayDateV(date == null ? "" : FormatUtil
							.formatDate(date));
					vm.setTotalInterestV(calcPredictProfit(vm.getSubjectNo(),
							vm.getBidAmount().getAmount()));
				}
				vms.add(vm);
			}
		}
		resp.put("subjectConfirmV", FormatUtil.formatRate(confirm));
		resp.put("subjectConfirmList", vms);
		return resp;
	}

	/**
	 * 计算预计收益
	 * 
	 * @param subjectNo
	 * @param amount
	 * @return
	 */
	private String calcPredictProfit(String subjectNo, BigDecimal amount) {
		InvestSubjectInfoResult subject = investService.querySubject(subjectNo);
		Long day = LoanTermDay.get(subject.getLoanTerm());
		if (day == null) {
			day = 0L;
		}
		MathContext mc = new MathContext(5, RoundingMode.FLOOR);
		String profit = decimalFormat.format(amount
				.multiply(subject.getRewardRate())
				.multiply(BigDecimal.valueOf(day))
				.divide(BigDecimal.valueOf(360 * 100L), mc));

		return profit.substring(0, profit.indexOf(".") + 3);
	}

	/**
	 * 获取平台账号
	 * 
	 * @param plat
	 * @return
	 */
	public String getPlatAccountId(String plat) {
		MemberEntity member = memberService.getPlatAdminMember(plat);
		if (member != null) {
			return member.getAccountId();
		}
		return null;
	}

	/**
	 * 获取账户某日余额
	 * 
	 * @param accountId
	 * @param date
	 * @return
	 */
	public BigDecimal getAccountBalanceOfDay(String accountId, Date date) {
		EntryEntityExample example = new EntryEntityExample();

		example.setLimitStart(0);
		example.setLimitEnd(1);
		example.setOrderByClause("entry_id desc");
		EntryEntityExample.Criteria criteria = example.createCriteria();
		criteria.andAccountIdEqualTo(accountId);
		if (date != null) {
			Date start = date;
			try {
				start = FormatUtil.dateFormat.parse(FormatUtil.dateFormat
						.format(date));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			GregorianCalendar gc = new GregorianCalendar();
			gc.setTime(start);
			gc.add(GregorianCalendar.DATE, 1);
			Date end = gc.getTime();
			criteria.andCreateTimeLessThan(end);
		}
		List<EntryEntity> entryEntities = entryEntityMapper
				.selectByExample(example);
		if (entryEntities.size() > 0) {
			return entryEntities.get(0).getAfterBalance();
		}
		return null;
	}
}
