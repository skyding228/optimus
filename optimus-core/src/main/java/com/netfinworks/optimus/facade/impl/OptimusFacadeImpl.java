package com.netfinworks.optimus.facade.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;

import com.netfinworks.common.lang.Paginator;
import com.netfinworks.common.lang.StringUtil;
import com.netfinworks.optimus.PayRequest;
import com.netfinworks.optimus.domain.RestResult;
import com.netfinworks.optimus.domain.enums.DcType;
import com.netfinworks.optimus.domain.enums.OrderStatus;
import com.netfinworks.optimus.domain.enums.OrderType;
import com.netfinworks.optimus.domain.enums.ReturnCode;
import com.netfinworks.optimus.entity.AccountEntity;
import com.netfinworks.optimus.entity.ChanStatsEntity;
import com.netfinworks.optimus.entity.MemberEntity;
import com.netfinworks.optimus.entity.OrderEntity;
import com.netfinworks.optimus.entity.PaymentEntity;
import com.netfinworks.optimus.facade.OptimusFacade;
import com.netfinworks.optimus.facade.util.ResponseConverterUtil;
import com.netfinworks.optimus.request.ChanPublicAccountAuditRequest;
import com.netfinworks.optimus.request.ChanPublicAccountManualBookRequest;
import com.netfinworks.optimus.request.EntryQueryRequest;
import com.netfinworks.optimus.request.ManualBookRequest;
import com.netfinworks.optimus.request.MemberPayRequest;
import com.netfinworks.optimus.request.MemberQueryRequest;
import com.netfinworks.optimus.response.AllMemberStatResponse;
import com.netfinworks.optimus.response.EntryQueryResponse;
import com.netfinworks.optimus.response.OrderQueryResponse;
import com.netfinworks.optimus.service.AccountService;
import com.netfinworks.optimus.service.ConfigService;
import com.netfinworks.optimus.service.MemberService;
import com.netfinworks.optimus.service.entity.EntryQueryParams;
import com.netfinworks.optimus.service.entity.EntryQueryResult;
import com.netfinworks.optimus.service.repository.OrderRepository;
import com.netfinworks.optimus.service.repository.impl.PaymentRepositoryImpl;
import com.netfinworks.util.JSONUtil;

@WebService(endpointInterface = "com.netfinworks.optimus.facade.OptimusFacade", targetNamespace = "http://optimus.api.ws.netfinworks.com")
public class OptimusFacadeImpl implements OptimusFacade {
	private static final Logger logger = LoggerFactory
			.getLogger(OptimusFacadeImpl.class);
	static Logger log = LoggerFactory.getLogger(OptimusFacadeImpl.class);

	@Resource
	private AccountService accountService;

	@Resource
	private MemberService memberService;
	@Resource
	private PaymentRepositoryImpl paymentRepository;
	@Resource
	private OrderRepository orderRepository;
	@Resource
	private ConfigService configService;
	// 事务管理
	@Resource(name = "transactionTemplate")
	private TransactionTemplate txTmpl;

	@Override
	public EntryQueryResponse queryEntryEntity(EntryQueryRequest request) {
		EntryQueryResponse response = new EntryQueryResponse();
		ResponseConverterUtil.initBaseResponse(response, ReturnCode.SUCCESS,
				ReturnCode.SUCCESS.getMessage());
		try {
			Assert.notNull(request, "请求不能为null");
			validateDcType(request.getDcType());

			java.text.SimpleDateFormat dateFomrmat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			EntryQueryParams entryQueryParams = new EntryQueryParams();
			if (request.getStartDate() != null
					&& !"".equals(request.getStartDate().trim())) {
				entryQueryParams.setStartDate(dateFomrmat.parse(request
						.getStartDate()));
			}
			if (request.getEndDate() != null
					&& !"".equals(request.getEndDate().trim())) {
				entryQueryParams.setEndDate(dateFomrmat.parse(request
						.getEndDate()));
			}
			entryQueryParams.setMemberId(request.getMemberId());
			entryQueryParams.setDcType(request.getDcType());

			Paginator paginator = new Paginator();
			paginator.setItemsPerPage(request.getPageSize());
			paginator.setPage(request.getPageNum());
			entryQueryParams.setPaginator(paginator);

			EntryQueryResult result = accountService
					.queryEntry(entryQueryParams);
			response.setResult(result.getEntryList());
			;
			ResponseConverterUtil.populatePage(result.getPagingResult(),
					response);

		} catch (IllegalArgumentException e) {
			logger.error("请求参数异常", e);
			ResponseConverterUtil.initBaseResponse(response,
					ReturnCode.PARAMETER_INVALID, e.getMessage());
		} catch (Exception e) {
			logger.error("未知异常", e);
			ResponseConverterUtil.initBaseResponse(response,
					ReturnCode.EXCEPTION, e.getMessage());
		}
		return response;
	}

	private static void validateDcType(String dcType) {
		if (dcType != null && !"".equals(dcType)) {
			DcType dcType2 = DcType.getByCode(dcType);
			Assert.notNull(dcType2, dcType + "交易类型不正确");
		}
	}

	@Override
	public AllMemberStatResponse queryAllMemberEntity(String chanId) {
		ChanStatsEntity chanStatsEntity = accountService
				.queryTotalBalanceByChan(chanId);
		AllMemberStatResponse response = new AllMemberStatResponse();
		ResponseConverterUtil.initBaseResponse(response, ReturnCode.SUCCESS,
				ReturnCode.SUCCESS.getMessage());
		response.setChanTotalBalance(chanStatsEntity.getBalance());
		response.setChanUserCnt(chanStatsEntity.getCnt());
		return response;
	}

	@Override
	public OrderEntity pay(MemberPayRequest request) {
		long start = System.currentTimeMillis();
		logger.info("账户支付-request:{}", request);

		Assert.notNull(request, "支付请求不能为null!");
		Assert.isTrue(StringUtil.isNotBlank(request.getMemberId()),
				"memberId不能为空!");
		Assert.isTrue(StringUtil.isNotBlank(request.getAmount()), "amount不能为空");
		Assert.isTrue(StringUtil.isNotBlank(request.getOrderType()),
				"orderType不能为空!");
		Assert.notNull(OrderType.getByCode(request.getOrderType()),
				"orderType不能为空!");
		Assert.isTrue(StringUtil.isNotBlank(request.getOuterOrderId()),
				"outerOrderId不能为空!");

		BigDecimal amount = new BigDecimal(request.getAmount());
		Assert.isTrue(BigDecimal.ZERO.compareTo(amount) < 0, "支付金额必须大于0!");
		MemberEntity member = memberService.getMemberEntity(request
				.getMemberId());

		PayRequest payRequest = new PayRequest();
		payRequest.setAmount(amount);
		payRequest.setMemo(request.getMemo());
		payRequest.setNotifyUrl(request.getNotifyUrl());
		payRequest.setOuterOrderId(request.getOuterOrderId());
		payRequest.setOrigOuterOrderId(request.getOrigOuterOrderId());
		payRequest.setProductId(request.getProductId());
		payRequest.setOrderType(OrderType.getByCode(request.getOrderType()));
		payRequest.setExt1(request.getExt1());
		payRequest.setExt2(request.getExt2());
		
		OrderEntity order = accountService.pay(payRequest, member);
		logger.info("账户支付-order:{}", order);
		logger.info("账户支付-costpay time:{}", System.currentTimeMillis() - start);
		return order;
	}

	@Override
	public OrderEntity manaulBook(ManualBookRequest request) {
		MemberEntity member = memberService.getMemberEntity(request
				.getMemberId());
		Assert.notNull(member, "会员不存在");
		PayRequest payRequest = new PayRequest();
		payRequest.setAmount(new BigDecimal(request.getAmount()));
		payRequest.setMemo(request.getMemo());
		payRequest.setNotifyUrl("");
		OrderType orderType = OrderType.getByCode(request.getOrderType());

		Assert.notNull(orderType, orderType + "交易类型不正确");
		Assert.isTrue(OrderType.MANUAL_IN == orderType
				|| OrderType.MANUAL_OUT == orderType, orderType + "交易类型不正确");
		payRequest.setOrderType(orderType);
		// outerOrderId
		payRequest.setOuterOrderId(UUID.randomUUID().toString());
		payRequest.setProductId("");
		OrderEntity orderEntity = accountService.pay(payRequest, member);
		return orderEntity;
	}

	@Override
	public MemberEntity queryMemberEntity(MemberQueryRequest request) {
		MemberEntity memberEntity = memberService.getMemberEntity(request
				.getMemberId());
		if (memberEntity == null) {
			memberEntity = memberService.getMemberEntityByChanUserId(
					request.getChanId(), request.getMemberId());
		}
		return memberEntity;
	}

	@Override
	public AccountEntity queryAccountEntity(String accountId) {
		return accountService.getAccount(accountId);
	}

	@Override
	public OrderEntity manaulBookPublicAccount(
			ChanPublicAccountManualBookRequest request) {
		log.info("公共账号手工记账-请求:{}", JSONUtil.toJson(request));
		Assert.notNull(request, "手工入账请求不能为null!");
		Assert.isTrue(StringUtil.isNotBlank(request.getAmount()), "审核员不能为空!");
		Assert.isTrue(StringUtil.isNotBlank(request.getChanId()), "渠道不能为空!");
		Assert.isTrue(StringUtil.isNotBlank(request.getMemo()), "备注不能为空!");
		Assert.isTrue(StringUtil.isNotBlank(request.getOperation()), "操作员不能为空!");
		Assert.isTrue(StringUtil.isNotBlank(request.getOrderType()),
				"入账类型不能为空!");
		Assert.isTrue(StringUtil.isNotBlank(request.getOuterOrderId()),
				"凭证号不能为空!");
		Assert.isNull(
				orderRepository.getOrderByOuterNo(request.getOuterOrderId()),
				"此凭证号已被使用过!");

		MemberEntity member = memberService.getMemberEntity(request
				.getChanMemberId());

		OrderEntity order = new OrderEntity();
		//
		order.setOuterOrderId(request.getOuterOrderId());
		order.setOrderId(UUID.randomUUID().toString());
		order.setChanId(request.getChanId());
		order.setChanUserId(member.getChanUserId());
		order.setMemberId(member.getMemberId());
		order.setAmount(new BigDecimal(request.getAmount()));
		order.setOrderStatus(OrderStatus.INIT.getValue());
		order.setMemo(request.getMemo());
		order.setProductId("");
		order.setOrderType(request.getOrderType());

		order.setOrderTime(new Date());
		order.setUpdateTime(new Date());

		order.setExt1(request.getOperation());

		txTmpl.execute(new TransactionCallback<OrderEntity>() {
			@Override
			public OrderEntity doInTransaction(TransactionStatus status) {
				try {
					accountService.saveOrderEntity(order);
					// 更新待出入
					if (StringUtil.isNotBlank(request.getBookPaymentId())) {
						paymentRepository.updateOrderId(
								request.getBookPaymentId(), order.getOrderId());
					}
				} catch (Exception e) {
					status.setRollbackOnly();
					log.error("订单处理失败:" + JSONUtil.toJson(order), e);
					throw e;
				}
				return order;
			}
		});
		return order;
	}

	@Override
	public void manaulBookAudit(ChanPublicAccountAuditRequest request) {
		log.info("公共账号手工记账审核-请求:{}", JSONUtil.toJson(request));
		Assert.notNull(request, "审核请求不能为null!");
		Assert.isTrue(StringUtil.isNotBlank(request.getOperation()), "审核员不能为空!");
		Assert.notNull(request.getOrderIdList(), "审核订单不能为null!");
		Assert.isTrue(request.getOrderIdList().length > 0, "审核订单不能为null!");
		accountService.auditPublicAccount(request.getOrderIdList(),
				request.getOperation(), "S");
	}

	@Override
	public List<OrderEntity> queryOrderListToAudit(String chanId) {
		log.info("待审核出入款-请求渠道:{}", chanId);
		List<String> types = new ArrayList<String>();
		types.add(OrderType.CHAN_MANUAL_IN.getValue());
		types.add(OrderType.CHAN_MANUAL_OUT.getValue());
		List<OrderEntity> orders = accountService.queryWaitAuditList(chanId,
				types);
		log.info("待审核出入款-响应信息:{}", JSONUtil.toJson(orders));
		return orders;
	}

	@Override
	public List<OrderEntity> queryOrderListWaitAudit(String chanId) {
		log.info("待渠道审核出入款-请求渠道:{}", chanId);
		List<String> types = new ArrayList<String>();
		types.add(OrderType.MANUAL_IN.getValue());
		types.add(OrderType.MANUAL_OUT.getValue());
		List<OrderEntity> orders = accountService.queryWaitAuditList(chanId,
				types);
		log.info("待渠道审核出入款-响应信息:{}", JSONUtil.toJson(orders));
		return orders;
	}

	String palt = "VFINANCE";

	@Override
	public List<PaymentEntity> queryWaitPayments(String orderId) {
		accountService.updatePaymentRecords(null);
		List<PaymentEntity> waitPayments = paymentRepository.queryWaitPayments(
				palt, orderId);
		return waitPayments;
	}

	// ACTION_LOAN
	@Override
	public int countPaymentSuccessBySubjectNo(String subjectNo,
			String actionType) {
		return paymentRepository.countPaymentSuccessBySubjectNo(subjectNo,
				actionType);
	}

	public OrderQueryResponse queryAccountTrade(int pageNum, int pageSize,
			Date date, String chanId, List<String> orderTypes) {
		OrderQueryResponse response = new OrderQueryResponse();
		RestResult restResult = orderRepository.queryAccountTrade(pageNum,
				pageSize, date, chanId, orderTypes, null, null);
		response.setResult((List<OrderEntity>) restResult.getResult());
		response.setItemCount(restResult.getItemCount());
		response.setPageCount(restResult.getPageCount());
		response.setPageIndex(restResult.getPageIndex());
		response.setPageSize(restResult.getPageSize());
		return response;
	}
	
	public List<OrderEntity> queryProvisionRecoveryOrder(String subjectNo,String plat){
		String platMemberId = configService.getPlatValue(plat,ConfigService.PLAT_PROVISION_MEMBER) ;
		List<String> platMemberIds = new ArrayList<String>();
		platMemberIds.add(platMemberId);
				
		List<String> orderTypes = new ArrayList<String>();
		orderTypes.add(OrderType.RECOVERY.getValue());

		List<OrderEntity> recoveryOrders = orderRepository
				.queryByMemberIdsAndOrderTypes(platMemberIds, orderTypes);
		//过滤
		List<OrderEntity> result = new ArrayList<OrderEntity>();
		if(recoveryOrders!=null) {
			for(OrderEntity entity :recoveryOrders ){
				if(subjectNo.equals(entity.getProductId())) {
					result.add(entity) ;
				}
			}
		}
		log.info("查询备付金账户还款记录:{}->{}", JSONUtil.toJson(platMemberIds),
				JSONUtil.toJson(result));
		return result ;
	}
}
