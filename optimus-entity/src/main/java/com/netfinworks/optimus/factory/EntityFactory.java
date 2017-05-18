package com.netfinworks.optimus.factory;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import com.netfinworks.optimus.entity.OrderEntity;

/**
 * entity 构造工厂
 * 
 * @author weichunhe
 *
 */
public class EntityFactory {

	public static OrderEntity orderEntity(String chanId, String chanUserId,
			String memberId, BigDecimal amount, String orderStatus,
			String memo, String productId, String orderType) {
		OrderEntity order = new OrderEntity();
		order.setOrderId(UUID.randomUUID().toString());

		order.setChanId(chanId);
		order.setChanUserId(chanUserId);
		order.setMemberId(memberId);
		order.setAmount(amount);
		order.setOrderStatus(orderStatus);
		order.setMemo(memo);
		order.setProductId(productId);
		order.setOrderType(orderType);

		order.setOrderTime(new Date());
		order.setUpdateTime(new Date());

		return order;
	}
}
