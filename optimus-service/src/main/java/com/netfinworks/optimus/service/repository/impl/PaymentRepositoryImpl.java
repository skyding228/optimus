package com.netfinworks.optimus.service.repository.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;

import com.netfinworks.optimus.domain.enums.PaymentRecordEnum;
import com.netfinworks.optimus.entity.OrderEntity;
import com.netfinworks.optimus.entity.PaymentEntity;
import com.netfinworks.optimus.entity.PaymentEntityExample;
import com.netfinworks.optimus.mapper.ManualEntityMapper;
import com.netfinworks.optimus.mapper.PaymentEntityMapper;
import com.netfinworks.optimus.service.repository.OrderRepository;
import com.netfinworks.util.JSONUtil;

/**
 * 出入款管理
 * 
 * @author weichunhe create at 2016年4月1日
 */
@Repository
public class PaymentRepositoryImpl {

	private static Logger log = LoggerFactory
			.getLogger(PaymentRepositoryImpl.class);

	@Resource
	private PaymentEntityMapper paymentEntityMapper;

	@Resource
	private OrderRepository orderRepository;
	@Resource
	private ManualEntityMapper manualEntityMapper;

	// 事务管理
	@Resource(name = "transactionTemplate")
	private TransactionTemplate txTmpl;


	/**
	 * 判断产品是否已经存在出入款记录中了
	 * 
	 * @param subjectNo
	 * @return
	 */
	public boolean isInPaymentRecords(String subjectNo,
			PaymentRecordEnum paymentType, PaymentRecordEnum actionType,
			String plat) {
		Assert.isTrue(!StringUtils.isEmpty(subjectNo), "产品编号不能为空!");
		PaymentEntityExample example = new PaymentEntityExample();
		PaymentEntityExample.Criteria criteria = example.createCriteria()
				.andSubjectNoEqualTo(subjectNo);
		if (paymentType != null) {
			criteria.andPaymentTypeEqualTo(paymentType.getValue());
		}
		if (actionType != null) {
			criteria.andActionTypeEqualTo(actionType.getValue());
		}
		if (plat != null) {
			criteria.andPlatEqualTo(plat);
		}

		List<PaymentEntity> result = paymentEntityMapper
				.selectByExample(example);
		return result == null || result.size() == 0 ? false : true;
	}

	/**
	 * 插入记录
	 * 
	 * @param subjectNo
	 * @param subjectName
	 * @param amount
	 * @param actionType
	 * @param paymentType
	 * @param subjectType
	 * @return
	 */
	public PaymentEntity insert(String plat, String subjectNo,
			String subjectName, BigDecimal amount,
			PaymentRecordEnum actionType, PaymentRecordEnum paymentType,
			PaymentRecordEnum subjectType, String toPlat) {
		PaymentEntity entity = new PaymentEntity();
		entity.setId(UUID.randomUUID().toString());
		entity.setAmount(amount);
		entity.setActionType(actionType.getValue());
		entity.setCreateTime(new Date());
		entity.setPaymentType(paymentType.getValue());
		entity.setSubjectName(subjectName);
		entity.setSubjectNo(subjectNo);
		entity.setSubjectType(subjectType.getValue());
		entity.setPlat(plat);
		entity.setToPlat(toPlat);

		paymentEntityMapper.insert(entity);
		return entity;
	}

	/**
	 * 更新记录对应的订单
	 * 
	 * @param id
	 * @param orderId
	 * @return
	 */
	public int updateOrderId(String id, String orderId) {
		List<String> ids = new ArrayList<String>();
		ids.add(id);
		return updateOrderId(ids, orderId);
	}

	/**
	 * 更新记录对应的订单
	 * 
	 * @param ids
	 * @param orderId
	 * @return
	 */
	public int updateOrderId(List<String> ids, String orderId) {
		PaymentEntity entity = new PaymentEntity();
		entity.setOrderId(orderId);
		PaymentEntityExample example = new PaymentEntityExample();
		example.createCriteria().andIdIn(ids);
		return paymentEntityMapper.updateByExampleSelective(entity, example);
	}

	/**
	 * 生成待出入款订单并且更新待出入款相关记录的订单编号
	 * 
	 * @param order
	 * @param ids
	 */
	public OrderEntity makePaymentOrder(OrderEntity order, List<String> ids) {
		return txTmpl.execute(new TransactionCallback<OrderEntity>() {

			@Override
			public OrderEntity doInTransaction(TransactionStatus status) {
				try {
					orderRepository.saveOrder(order);
					updateOrderId(ids, order.getOrderId());
					return order;
				} catch (Exception e) {
					status.setRollbackOnly();
					log.error("生成待出入款记录订单出错", e);
				}
				return null;
			}
		});

	}

	/**
	 * 查询平台待出入款记录
	 * 
	 * @param plat
	 * @return
	 */
	public List<PaymentEntity> queryWaitPayments(String plat, String orderId) {
		PaymentEntityExample example = new PaymentEntityExample();
		example.setOrderByClause("create_time");
		if (StringUtils.isEmpty(orderId)) {
			example.createCriteria().andOrderIdIsNull().andPlatEqualTo(plat);
		} else {
			example.createCriteria().andOrderIdEqualTo(orderId);
		}
		return paymentEntityMapper.selectByExample(example);
	}

	/**
	 * 查询出这些订单的轧差额
	 * 
	 * @param ids
	 * @return
	 */
	public BigDecimal queryNettingAmount(List<String> ids) {

		BigDecimal amount = BigDecimal.ZERO;
		PaymentEntityExample example = new PaymentEntityExample();
		example.createCriteria().andIdIn(ids);
		List<PaymentEntity> records = paymentEntityMapper
				.selectByExample(example);
		if (records != null) {
			for (PaymentEntity r : records) {
				if (PaymentRecordEnum.PAYMENT_IN.getValue().equals(
						r.getPaymentType())) {
					amount = amount.add(r.getAmount());
				} else if (PaymentRecordEnum.PAYMENT_OUT.getValue().equals(
						r.getPaymentType())) {
					amount = amount.subtract(r.getAmount());
				}
			}
		}
		log.info("对待出入款记录进行轧差:ids={},amount={}", JSONUtil.toJson(ids), amount);
		return amount;
	}

	/**
	 * 满标放款前check 放款给借款人的钱 审核成功
	 */
	public int countPaymentSuccessBySubjectNo(String subjectNo,
			String actionType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("subjectNo", subjectNo);
		map.put("actionType", actionType);
		return manualEntityMapper.countPaymentSuccessBySubjectNo(map);
	}

	/**
	 * 根据id查询
	 * 
	 * @param ids
	 * @return
	 */
	public List<PaymentEntity> queryByIds(List<String> ids) {
		PaymentEntityExample example = new PaymentEntityExample();
		example.createCriteria().andIdIn(ids);
		return paymentEntityMapper.selectByExample(example);
	}

	/**
	 * @param actionType
	 * @return
	 */
	public List<PaymentEntity> queryByActionTypeWithOrder(PaymentRecordEnum actionType) {
		PaymentEntityExample example = new PaymentEntityExample();
		example.createCriteria().andActionTypeEqualTo(actionType.getValue()).andOrderIdIsNotNull();
		return paymentEntityMapper.selectByExample(example);
	}
}
