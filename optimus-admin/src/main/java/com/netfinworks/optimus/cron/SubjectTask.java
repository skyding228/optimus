package com.netfinworks.optimus.cron;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.netfinworks.common.util.money.Money;
import com.netfinworks.invest.request.BidRequest;
import com.netfinworks.invest.response.BidResponse;
import com.netfinworks.optimus.domain.enums.OrderStatus;
import com.netfinworks.optimus.domain.enums.PaymentRecordEnum;
import com.netfinworks.optimus.domain.enums.ReturnCode;
import com.netfinworks.optimus.entity.MemberEntity;
import com.netfinworks.optimus.entity.OrderEntity;
import com.netfinworks.optimus.entity.PaymentEntity;
import com.netfinworks.optimus.service.AccountService;
import com.netfinworks.optimus.service.ConfigService;
import com.netfinworks.optimus.service.MemberService;
import com.netfinworks.optimus.service.integration.invest.impl.InvestServiceImpl;
import com.netfinworks.optimus.service.repository.OrderRepository;
import com.netfinworks.optimus.service.repository.impl.PaymentRepositoryImpl;
import com.netfinworks.util.JSONUtil;

/**
 * 标的相关的任务
 * 
 * @author weichunhe create at 2016年5月13日
 */
@Component
public class SubjectTask {
	@Autowired
	private PaymentRepositoryImpl paymentRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private AccountService accountService;

	@Resource(name = "investService")
	private InvestServiceImpl investService;

	@Resource
	private MemberService memberService;

	@Resource
	private ConfigService configService;

	private static Logger log = LoggerFactory.getLogger(SubjectTask.class);

	/**
	 * 申购已过期产品
	 */
	@Scheduled(cron = "0/30 * * * * ?")
	public void applyOverdueSubject() {

		// 1. 查询 payment 的action_type 是 备付金类型的
		List<PaymentEntity> payments = paymentRepository
				.queryByActionTypeWithOrder(PaymentRecordEnum.ACTION_PROVISION_INVEST);
		log.debug("执行备付金购买产品任务:{}", JSONUtil.toJson(payments));
		// 2. 判断审核是否通过
		for (PaymentEntity payment : payments) {
			OrderEntity order = orderRepository.getOrderByNo(payment
					.getOrderId());
			if (order != null
					&& OrderStatus.SUCCESS.getValue().equals(
							order.getOrderStatus())) {

				MemberEntity member = memberService
						.getMemberEntity(configService.getPlatValue(
								order.getChanId(),
								ConfigService.PLAT_PROVISION_MEMBER));
				// 3. 审核通过之后，判断是否有一条outOrderId为对应的payment记录的id的订单，没有就生成，有就执行
				OrderEntity provisionOrder = getProvisionOrder(payment, order,
						member);
				// 4. 执行订单
				if (!OrderStatus.SUCCESS.getValue().equals(
						provisionOrder.getOrderStatus())) {
					accountService.updateAccountBalance(provisionOrder);
				}
				// 5. 进行产品购买
				// 判断产品是否购买成功
				if (ReturnCode.SUCCESS.getCode().equals(
						provisionOrder.getExt1())) {
					continue;
				}
				BidRequest req = new BidRequest();
				req.setAmount(new Money(provisionOrder.getAmount()));
				req.setExtension("{\"platformBuy\":\"true\"}");
				req.setMemberId(member.getMemberId());
				req.setRemark("购买产品");
				req.setSubjectNo(provisionOrder.getProductId());
				req.setSubmitType("1"); // 手动购买
				BidResponse resp = investService.apply(req);
				log.info("备付金购买产品:{}-{}", JSONUtil.toJson(req),
						JSONUtil.toJson(resp));
				provisionOrder.setExt1(resp.getReturnCode());
				provisionOrder.setExt2(resp.getReturnMessage());
				orderRepository.updateStatus(provisionOrder);
			}
		}

	}

	/**
	 * 偿还备付金
	 */
	@Scheduled(cron = "15/30 * * * * ?")
	public void repaymentProvision() {
		// 1. 查询 payment 的action_type 是 备付金类型的
		List<PaymentEntity> payments = paymentRepository
				.queryByActionTypeWithOrder(PaymentRecordEnum.ACTION_PROVISION_REPAYMENT);
		log.debug("执行备付金还款任务:{}", JSONUtil.toJson(payments));
		for (PaymentEntity payment : payments) {
			OrderEntity order = orderRepository.getOrderByNo(payment
					.getOrderId());
			if (order != null
					&& OrderStatus.SUCCESS.getValue().equals(
							order.getOrderStatus())) {
				MemberEntity member = memberService
						.getMemberEntity(configService.getPlatValue(
								payment.getToPlat(),
								ConfigService.PLAT_PROVISION_MEMBER));

				OrderEntity provisionOrder = getProvisionOrder(payment, order,
						member);
				// 4. 执行订单
				if (!OrderStatus.SUCCESS.getValue().equals(
						provisionOrder.getOrderStatus())) {
					log.info("执行备付金还款:{}", JSONUtil.toJson(provisionOrder));
					accountService.updateAccountBalance(provisionOrder);
				}
			}
		}

	}

	private OrderEntity getProvisionOrder(PaymentEntity payment,
			OrderEntity order, MemberEntity member) {
		OrderEntity provisionOrder = orderRepository.getOrderByOuterNo(payment
				.getId());
		if (provisionOrder == null) {
			provisionOrder = new OrderEntity();
			provisionOrder.setAmount(payment.getAmount());
			provisionOrder.setChanId(order.getChanId());
			provisionOrder.setChanUserId(member.getChanUserId());
			provisionOrder.setMemberId(member.getMemberId());
			provisionOrder.setOuterOrderId(payment.getId());
			provisionOrder.setMemo(payment.getSubjectName());
			// provisionOrder.setOrderId();
			provisionOrder.setOrderStatus(OrderStatus.INIT.getValue());
			provisionOrder.setOrderTime(new Date());
			provisionOrder.setOrderType(order.getOrderType());
			provisionOrder.setProductId(payment.getSubjectNo());
			provisionOrder.setUpdateTime(new Date());
			orderRepository.saveOrder(provisionOrder);
		}
		return provisionOrder;
	}
}
