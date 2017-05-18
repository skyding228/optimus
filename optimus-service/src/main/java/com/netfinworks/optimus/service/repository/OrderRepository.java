package com.netfinworks.optimus.service.repository;

import java.util.Date;
import java.util.List;

import com.netfinworks.optimus.ApplyRequest;
import com.netfinworks.optimus.RedeemRequest;
import com.netfinworks.optimus.domain.RestResult;
import com.netfinworks.optimus.domain.enums.ApplyOrderStateKind;
import com.netfinworks.optimus.domain.enums.RedeemOrderStateKind;
import com.netfinworks.optimus.entity.OrderEntity;

/**
 * <p>
 * </p>
 *
 */
public interface OrderRepository {
	/**
	 * 创建申购记录
	 */
	public OrderEntity createApplyOrder(ApplyRequest request);

	/**
	 * 更新申购记录状态
	 */
	boolean updateApplyOrderStatus(String subscribeOrderNo,
			ApplyOrderStateKind oldState, ApplyOrderStateKind newState,
			String memo);

	/**
	 * 根据编号获取申购记录
	 */
	OrderEntity getOrderByNo(String orderId);

	/**
	 * 根据外部编号获取记录
	 */
	OrderEntity getOrderByOuterNo(String outerOrderId);

	/**
	 * 创建赎回记录
	 */
	public OrderEntity createRedeemOrder(RedeemRequest request);

	/**
	 * 更新赎回记录状态
	 */
	public boolean updateRedeemOrderStatus(String orderId,
			RedeemOrderStateKind oldState, RedeemOrderStateKind newState,
			String memo);

	/**
	 * 保存订单数据
	 * 
	 * @param order
	 * @return
	 */
	public int saveOrder(OrderEntity order);

	/**
	 * 更新订单状态
	 * 
	 * @param order
	 * @return
	 */
	public boolean updateStatus(OrderEntity order);

	/**
	 * 查询某一天的充值提现交易记录
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param date
	 * @return
	 */
	public RestResult queryAccountTrade(int pageNum, int pageSize, Date date,
			String plat, List<String> orderTypes, List<String> orderStatus,
			String userId);

	public List<OrderEntity> queryByMemberIdsAndOrderTypes(
			List<String> memberIds, List<String> orderTypes);
}
