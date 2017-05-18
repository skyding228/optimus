package com.netfinworks.optimus.service.repository.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.netfinworks.common.lang.Paginator;
import com.netfinworks.optimus.ApplyRequest;
import com.netfinworks.optimus.RedeemRequest;
import com.netfinworks.optimus.domain.RestResult;
import com.netfinworks.optimus.domain.enums.ApplyOrderStateKind;
import com.netfinworks.optimus.domain.enums.OrderStatus;
import com.netfinworks.optimus.domain.enums.OrderType;
import com.netfinworks.optimus.domain.enums.RedeemOrderStateKind;
import com.netfinworks.optimus.entity.OrderEntity;
import com.netfinworks.optimus.entity.OrderEntityExample;
import com.netfinworks.optimus.mapper.OrderEntityMapper;
import com.netfinworks.optimus.service.repository.OrderRepository;
import com.netfinworks.optimus.service.repository.PrimaryNoRepository;
import com.netfinworks.util.JSONUtil;

@Repository
public class OrderRepositoryImpl implements OrderRepository {
	@Resource
	private PrimaryNoRepository primaryNoRepository;
	@Resource
	private OrderEntityMapper orderEntityMapper;

	private static Logger log = LoggerFactory
			.getLogger(OrderRepositoryImpl.class);

	private static SimpleDateFormat day = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public OrderEntity createApplyOrder(ApplyRequest request) {
		OrderEntity order = new OrderEntity();
		order.setProductId(request.getProductId());
		order.setAmount(request.getAmount());
		order.setChanId(request.getChanId());
		order.setChanUserId(request.getChanUserId());
		order.setMemberId(request.getMemberId());
		order.setMemo(ApplyOrderStateKind.INIT.getMsg());
		order.setOrderId(primaryNoRepository.getApplyOrderNo());
		order.setOrderStatus(OrderStatus.INIT.getValue());
		order.setOrderTime(new Date());
		order.setOrderType(OrderType.APPLY.getValue());
		validateOrder(order);
		orderEntityMapper.insert(order);
		return order;
	}

	@Override
	public boolean updateApplyOrderStatus(String orderId,
			ApplyOrderStateKind oldState, ApplyOrderStateKind newState,
			String memo) {
		OrderEntityExample example = new OrderEntityExample();
		example.createCriteria().andOrderIdEqualTo(orderId)
				.andOrderStatusEqualTo(oldState.getCode());
		OrderEntity update = new OrderEntity();
		update.setOrderStatus(newState.getCode());
		update.setMemo(newState.getMsg());
		validateOrder(update);
		return orderEntityMapper.updateByExampleSelective(update, example) == 1;
	}

	@Override
	public OrderEntity getOrderByNo(String orderId) {
		return orderEntityMapper.selectByPrimaryKey(orderId);
	}

	@Override
	public OrderEntity getOrderByOuterNo(String outerOrderId) {
		OrderEntityExample example = new OrderEntityExample();
		example.createCriteria().andOuterOrderIdEqualTo(outerOrderId);
		// .andOrderStatusEqualTo("S");
		List<OrderEntity> list = orderEntityMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public OrderEntity createRedeemOrder(RedeemRequest request) {
		OrderEntity order = new OrderEntity();
		order.setProductId(request.getProductId());
		order.setAmount(request.getAmount());
		order.setChanId(request.getChanId());
		order.setChanUserId(request.getChanUserId());
		order.setMemberId(request.getMemberId());

		order.setMemo(RedeemOrderStateKind.INIT.getMsg());
		order.setOrderId(primaryNoRepository.getRedeemOrderNo());
		order.setOrderStatus(OrderStatus.INIT.getValue());
		order.setOrderTime(new Date());
		order.setOrderType(OrderType.REDEEM.getValue());
		validateOrder(order);
		orderEntityMapper.insert(order);
		return order;
	}

	@Override
	public boolean updateRedeemOrderStatus(String orderId,
			RedeemOrderStateKind oldState, RedeemOrderStateKind newState,
			String memo) {
		OrderEntityExample example = new OrderEntityExample();
		example.createCriteria().andOrderIdEqualTo(orderId)
				.andOrderStatusEqualTo(oldState.getCode());
		OrderEntity update = new OrderEntity();
		update.setOrderStatus(newState.getCode());
		update.setMemo(newState.getMsg());
		validateOrder(update);
		return orderEntityMapper.updateByExampleSelective(update, example) == 1;
	}

	@Override
	public int saveOrder(OrderEntity order) {
		order.setOrderId(primaryNoRepository.getOrderNo());
		validateOrder(order);
		return orderEntityMapper.insert(order);
	}

	@Override
	public boolean updateStatus(OrderEntity order) {
		OrderEntity update = new OrderEntity();
		update.setOrderId(order.getOrderId());
		update.setOrderStatus(order.getOrderStatus());
		update.setUpdateTime(new Date());
		update.setOrderTime(order.getOrderTime());
		update.setOuterOrderId(order.getOuterOrderId());
		update.setMemo(order.getMemo());
		update.setExt1(order.getExt1());
		update.setExt2(order.getExt2());
		validateOrder(order);
		log.info("更新订单状态:" + JSONUtil.toJson(update));

		int count = orderEntityMapper.updateByPrimaryKeySelective(update);

		Assert.isTrue(count > 0, "更新订单状态失败!" + JSONUtil.toJson(update));

		return count > 0;
	}

	/**
	 * 校验订单信息 memo最长200
	 * 
	 * @param order
	 */
	private void validateOrder(OrderEntity order) {
		if (order == null) {
			return;
		}
		if (!StringUtils.isEmpty(order.getMemo())) {
			if (order.getMemo().length() > 200) {
				order.setMemo(order.getMemo().substring(0, 200));
			}
		}
	}

	@Override
	public RestResult queryAccountTrade(int pageNum, int pageSize, Date date,
			String plat, List<String> orderTypes, List<String> orderStatus,
			String userId) {

		Paginator paginator = new Paginator(pageSize);
		paginator.setPage(pageNum);
		OrderEntityExample example = new OrderEntityExample();
		example.setLimitStart(paginator.getOffset());
		example.setLimitEnd(paginator.getItemsPerPage());
		example.setOrderByClause("order_time");

		OrderEntityExample.Criteria criteria = example.createCriteria()
				.andChanIdEqualTo(plat);
		if (orderTypes != null && orderTypes.size() > 0) {
			criteria.andOrderTypeIn(orderTypes);
		}

		if (date != null) {
			Date start = date;
			try {
				start = day.parse(day.format(date));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			GregorianCalendar gc = new GregorianCalendar();
			gc.setTime(start);
			gc.add(GregorianCalendar.DATE, 1);
			Date end = gc.getTime();
			criteria.andOrderTimeBetween(start, end);
		}

		if (orderStatus != null && orderStatus.size() > 0) {
			criteria.andOrderStatusIn(orderStatus);
		}
		if (!StringUtils.isEmpty(userId)) {
			criteria.andChanUserIdEqualTo(userId);
		}

		int total = orderEntityMapper.countByExample(example);
		List<OrderEntity> orders = orderEntityMapper.selectByExample(example);

		paginator.setItems(total);
		RestResult result = new RestResult();
		result.setItemCount(total);
		result.setPageCount(paginator.getPages());
		result.setPageIndex(paginator.getPage());
		result.setPageSize(paginator.getItemsPerPage());
		result.setResult(orders);
		return result;
	}

	public static void main(String[] args) {
		Date date = new Date();
		Date start = null;
		try {
			start = day.parse(day.format(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(start);
		gc.add(GregorianCalendar.DATE, 1);
		Date end = gc.getTime();
		System.out.println(date);
		System.out.println(start);
		System.out.println(end);
	}

	@Override
	public List<OrderEntity> queryByMemberIdsAndOrderTypes(
			List<String> memberIds, List<String> orderTypes) {
		OrderEntityExample example = new OrderEntityExample();
		OrderEntityExample.Criteria criteria = example.createCriteria();
		criteria.andMemberIdIn(memberIds).andOrderTypeIn(orderTypes);
		return orderEntityMapper.selectByExample(example);
	}
}
