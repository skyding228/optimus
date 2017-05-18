package com.netfinworks.optimus.admin.web.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netfinworks.optimus.admin.util.ControllerUtil;
import com.netfinworks.optimus.admin.web.CommonConstants;
import com.netfinworks.optimus.admin.web.domain.OrderEntiryVM;
import com.netfinworks.optimus.admin.web.domain.PaymentResult;
import com.netfinworks.optimus.admin.web.domain.PaymentVM;
import com.netfinworks.optimus.domain.enums.OrderStatus;
import com.netfinworks.optimus.domain.enums.OrderType;
import com.netfinworks.optimus.domain.enums.PaymentRecordEnum;
import com.netfinworks.optimus.entity.MemberEntity;
import com.netfinworks.optimus.entity.OrderEntity;
import com.netfinworks.optimus.entity.PaymentEntity;
import com.netfinworks.optimus.service.AccountService;
import com.netfinworks.optimus.service.ConfigService;
import com.netfinworks.optimus.service.integration.invest.impl.InvestServiceImpl;
import com.netfinworks.optimus.service.repository.impl.PaymentRepositoryImpl;
import com.netfinworks.util.JSONUtil;

/**
 * 出入款管理
 * 
 * @author weichunhe create at 2016年3月25日
 */
@RestController
@RequestMapping("/payment")
public class PaymentController {

	private static Logger log = LoggerFactory
			.getLogger(PaymentController.class);

	static final String LogPrefix = "出入款管理";

	@Resource
	private ConfigService configService;

	@Resource(name = "investService")
	private InvestServiceImpl investService;

	@Resource
	private AccountService accountService;

	@Resource
	private PaymentRepositoryImpl paymentRepository;

	/**
	 * 查询待出入款项目
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/waitpayment")
	public Object queryWaitPayment(HttpServletRequest request, String orderId) {

		MemberEntity member = ControllerUtil.retriveFromSession(request,
				CommonConstants.SESSION_LOGIN_USER, MemberEntity.class);

		List<PaymentVM> outs = new ArrayList<PaymentVM>();
		List<PaymentVM> ins = new ArrayList<PaymentVM>();

		PaymentResult paymentResult = new PaymentResult();

		accountService.updatePaymentRecords(member.getChanId());
		List<PaymentEntity> waitPayments = paymentRepository.queryWaitPayments(
				member.getChanId(), orderId);
		log.info("{}-待出入款-定期响应信息:{}", LogPrefix, JSONUtil.toJson(waitPayments));

		// 对定期产品进行处理
		if (waitPayments != null) {
			waitPayments.forEach(x -> {
				PaymentEntity r = x;
				// 待出款产品
					if (PaymentRecordEnum.PAYMENT_OUT.getValue().equals(
							r.getPaymentType())) {
						paymentResult.addOutMoney(r.getAmount());
						outs.add(new PaymentVM(r));
					}
					// 待入款产品
					if (PaymentRecordEnum.PAYMENT_IN.getValue().equals(
							r.getPaymentType())) {
						paymentResult.addInMoney(r.getAmount());
						ins.add(new PaymentVM(r));
					}
				});
		}

		paymentResult.setIns(ins);
		paymentResult.setOuts(outs);

		return paymentResult;
	}

	/**
	 * 查询待执行的出入款订单
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/waitexecs")
	public List<OrderEntity> queryWaitPaymentOrders(HttpServletRequest request) {
		MemberEntity member = ControllerUtil.retriveFromSession(request,
				CommonConstants.SESSION_LOGIN_USER, MemberEntity.class);
		List<OrderEntity> orders = accountService.queryWaitExecList(member
				.getChanId());
		log.info("待执行出入款记录:{}", JSONUtil.toJson(orders));
		List<OrderEntity> vms = new ArrayList<OrderEntity>();
		if (orders != null) {
			orders.forEach(x -> {
				vms.add(new OrderEntiryVM(x));
			});
			log.info("待执行出入款-视图模型:{}", JSONUtil.toJson(vms));
			return vms;
		}
		return orders;
	}

	/**
	 * 查询待审核记录
	 * 
	 * @return
	 */
	@RequestMapping("/waitaudits")
	public List<OrderEntity> queryWaitAudits(HttpServletRequest request) {
		MemberEntity member = ControllerUtil.retriveFromSession(request,
				CommonConstants.SESSION_LOGIN_USER, MemberEntity.class);
		List<String> types = new ArrayList<String>();
		types.add(OrderType.MANUAL_IN.getValue());
		types.add(OrderType.MANUAL_OUT.getValue());
		List<OrderEntity> orders = accountService.queryWaitAuditList(
				member.getChanId(), types);

		log.info("待审核出入款-响应信息:{}", JSONUtil.toJson(orders));
		List<OrderEntity> vms = new ArrayList<OrderEntity>();
		if (orders != null) {
			orders.forEach(x -> {
				vms.add(new OrderEntiryVM(x));
			});
			log.info("待审核出入款-视图模型:{}", JSONUtil.toJson(vms));
			return vms;
		}

		return orders;
	}

	/**
	 * 查询待对方审核记录
	 * 
	 * @return
	 */
	@RequestMapping("/waitOppositeAudits")
	public List<OrderEntity> queryWaitOppositeAudits(HttpServletRequest request) {
		MemberEntity member = ControllerUtil.retriveFromSession(request,
				CommonConstants.SESSION_LOGIN_USER, MemberEntity.class);
		List<String> types = new ArrayList<String>();
		types.add(OrderType.CHAN_MANUAL_IN.getValue());
		types.add(OrderType.CHAN_MANUAL_OUT.getValue());
		List<OrderEntity> orders = accountService.queryWaitAuditList(
				member.getChanId(), types);

		log.info("待对方审核记录-响应信息:{}", JSONUtil.toJson(orders));
		List<OrderEntity> vms = new ArrayList<OrderEntity>();
		if (orders != null) {
			orders.forEach(x -> {
				vms.add(new OrderEntiryVM(x));
			});
			log.info("待对方审核记录-视图模型:{}", JSONUtil.toJson(vms));
			return vms;
		}

		return orders;
	}

	/**
	 * 生成待出入款记录
	 * 
	 * @param ids
	 * @param request
	 */
	@RequestMapping("/order")
	public OrderEntity makePaymentOrder(@RequestParam List<String> ids,
			HttpServletRequest request) {
		Assert.notNull(ids, "缺少出入款记录编号!");
		MemberEntity member = ControllerUtil.retriveFromSession(request,
				CommonConstants.SESSION_LOGIN_USER, MemberEntity.class);
		// 查询订单总金额
		BigDecimal amount = paymentRepository.queryNettingAmount(ids);

		OrderEntity order = new OrderEntity();
		if (amount.compareTo(BigDecimal.ZERO) < 0) {
			order.setOrderType(OrderType.CHAN_MANUAL_OUT.getValue());
			order.setMemo(OrderType.CHAN_MANUAL_OUT.getType());
		} else {
			order.setOrderType(OrderType.CHAN_MANUAL_IN.getValue());
			order.setMemo(OrderType.CHAN_MANUAL_IN.getType());
		}
		order.setOrderStatus(OrderStatus.INIT.getValue());
		order.setOrderTime(new Date());
		order.setChanId(member.getChanId());
		order.setChanUserId(member.getChanUserId());
		order.setProductId("");
		order.setMemberId(member.getMemberId());
		order.setAmount(amount.abs());
		// 获取所有payment
		List<PaymentEntity> paymentEntities = paymentRepository.queryByIds(ids);
		if (paymentEntities != null) {
			for (PaymentEntity paymentEntity : paymentEntities) {
				order.setMemo(order.getMemo() + " | "
						+ paymentEntity.getSubjectName());
			}
		}

		// 更新订单信息
		order = paymentRepository.makePaymentOrder(order, ids);
		Assert.notNull(order, "生成待出入款记录失败!");
		return order;
	}
}
