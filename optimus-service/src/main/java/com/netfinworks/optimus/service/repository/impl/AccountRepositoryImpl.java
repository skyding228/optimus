package com.netfinworks.optimus.service.repository.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;

import com.netfinworks.common.lang.Paginator;
import com.netfinworks.optimus.domain.enums.OrderStatus;
import com.netfinworks.optimus.domain.enums.OrderType;
import com.netfinworks.optimus.entity.AccountEntity;
import com.netfinworks.optimus.entity.ChanStatsEntity;
import com.netfinworks.optimus.entity.EntryEntity;
import com.netfinworks.optimus.entity.MemberEntity;
import com.netfinworks.optimus.entity.OrderEntity;
import com.netfinworks.optimus.mapper.AccountEntityMapper;
import com.netfinworks.optimus.mapper.EntryEntityMapper;
import com.netfinworks.optimus.mapper.ManualEntityMapper;
import com.netfinworks.optimus.mapper.MemberEntityMapper;
import com.netfinworks.optimus.service.entity.EntryQueryParams;
import com.netfinworks.optimus.service.entity.EntryQueryResult;
import com.netfinworks.optimus.service.entity.QueryPagingResult;
import com.netfinworks.optimus.service.repository.AccountRepository;
import com.netfinworks.optimus.service.repository.OrderRepository;
import com.netfinworks.util.JSONUtil;

@Repository
public class AccountRepositoryImpl implements AccountRepository {

	private static Logger log = LoggerFactory
			.getLogger(AccountRepositoryImpl.class);

	@Resource
	private AccountEntityMapper accountEntityMapper;
	@Resource
	private EntryEntityMapper entryEntityMapper;

	@Resource
	private OrderRepository orderRepository;
	@Resource
	private ManualEntityMapper manualEntityMapper;

	@Resource
	private MemberEntityMapper memberEntityMapper;

	// 事务管理
	@Resource(name = "transactionTemplate")
	private TransactionTemplate txTmpl;

	@Override
	public AccountEntity getAccount(String accountId) {
		return accountEntityMapper.selectByPrimaryKey(accountId);
	}

	@Override
	public AccountEntity updateAccountBalance(OrderEntity order) {

		return txTmpl.execute(new TransactionCallback<AccountEntity>() {
			@Override
			public AccountEntity doInTransaction(TransactionStatus status) {
				MemberEntity member = memberEntityMapper
						.selectByPrimaryKey(order.getMemberId());
				// 查询账户 lock
				AccountEntity account = manualEntityMapper
						.selectAccountEntityByPrimaryKeyForUpdate(member
								.getAccountId());

				// 要注意同一个order重复更新
				OrderEntity entity = orderRepository.getOrderByNo(order
						.getOrderId());
				// 订单状态已经是成功或者失败等最终状态了，就不进行处理了
				if (OrderStatus.SUCCESS.getValue().equals(
						entity.getOrderStatus())
						|| OrderStatus.FAIL.getValue().equals(
								entity.getOrderStatus())) {
					return account;
				}

				// 如果是将订单处理为失败,就只更新订单状态，不用更新余额
				if (OrderStatus.FAIL.getValue().equals(order.getOrderStatus())) {
					// 更新订单状态
					orderRepository.updateStatus(order);
					return account;
				}

				// 余额变更记录
				EntryEntity entry = geneEntryEntity(order, account);

				Assert.isTrue(entry.getAfterBalance()
						.compareTo(BigDecimal.ZERO) >= 0, "余额不足");

				// 更新余额
				account.setBalance(entry.getAfterBalance());
				account.setUpdateTime(new Date());
				log.info(
						"account变更:accountId={},entry.createTime={},orderId={}",
						account.getAccountId(), entry.getCreateTime(),
						order.getOrderId());

				try {
					// 记录余额变更历史
					entryEntityMapper.insert(entry);
					// 更新余额
					accountEntityMapper.updateByPrimaryKeySelective(account);

					// 更新订单状态
					order.setOrderStatus(OrderStatus.SUCCESS.getValue());
					orderRepository.updateStatus(order);
				} catch (Exception e) {
					order.setOrderStatus(OrderStatus.FAIL.getValue());// 失败了
																		// 外面要知道失败
					status.setRollbackOnly();
					log.error("订单处理失败:" + JSONUtil.toJson(order), e);
				}
				return account;
			}
		});

	}

	/**
	 * 生成余额变更记录
	 * 
	 * @param order
	 * @param account
	 * @return
	 */
	private EntryEntity geneEntryEntity(OrderEntity order, AccountEntity account) {
		String dc = OrderType.getBalanceDC(order.getOrderType());
		BigDecimal balance = account.getBalance();
		EntryEntity entry = new EntryEntity();
		entry.setAccountId(account.getAccountId());
		entry.setAmount(order.getAmount());
		entry.setDc(dc);
		entry.setBeforeBalance(balance);
		BigDecimal afterBalance = null;
		if ("+".equals(dc)) {
			afterBalance = balance.add(order.getAmount());
		} else {
			afterBalance = balance.subtract(order.getAmount());
		}
		Assert.isTrue(afterBalance.compareTo(BigDecimal.ZERO) >= 0, "余额不足");
		entry.setAfterBalance(afterBalance);
		entry.setDigest(order.getMemo());
		entry.setMemberId(order.getMemberId());
		entry.setOrderId(order.getOrderId());
		entry.setOrderType(order.getOrderType());
		entry.setCreateTime(new Date());

		entry.setExt1(order.getExt1());
		entry.setExt2(order.getExt2());
		return entry;
	}

	@Override
	public AccountEntity getAccountForUpdate(String accountId) {
		return manualEntityMapper
				.selectAccountEntityByPrimaryKeyForUpdate(accountId);
	}

	@Override
	public EntryQueryResult queryEntry(EntryQueryParams entryQueryParams) {
		EntryQueryResult result = new EntryQueryResult();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dcType", entryQueryParams.getDcType());
		map.put("memberId", entryQueryParams.getMemberId());
		map.put("startDate", entryQueryParams.getStartDate());
		map.put("endDate", entryQueryParams.getEndDate());
		// set 总条数
		Paginator paginator = entryQueryParams.getPaginator();
		paginator.setItems(manualEntityMapper.countEntryList(map));
		// mysql 分页
		map.put("offset", new Integer(entryQueryParams.getPaginator()
				.getOffset()));
		map.put("pageSize", new Integer(entryQueryParams.getPaginator()
				.getLength()));

		List<EntryEntity> list = manualEntityMapper.queryEntryList(map);

		QueryPagingResult pageResult = result.getPagingResult();

		pageResult.setItemCount(paginator.getItems());
		pageResult.setPageCount(paginator.getPages());
		pageResult.setPageIndex(paginator.getPage());
		pageResult.setPageSize(paginator.getItemsPerPage());

		result.setPagingResult(pageResult);
		result.setEntryList(list);
		return result;
	}

	@Override
	public ChanStatsEntity queryTotalBalanceByChan(String chanId) {
		return manualEntityMapper.queryTotalBalanceByChan(chanId);
	}

	@Override
	public void auditPublicAccount(String[] orderIdList, String operation,
			String orderStatus) {
		// 只能 通过或者驳回
		Assert.isTrue(OrderStatus.SUCCESS.getValue().equals(orderStatus)
				|| OrderStatus.FAIL.getValue().equals(orderStatus),
				"操作不正确,审核时只能通过或者驳回!");

		txTmpl.execute(new TransactionCallback<Boolean>() {
			@Override
			public Boolean doInTransaction(TransactionStatus status) {
				try {
					// 遍历
					for (String orderId : orderIdList) {
						// 订单
						OrderEntity order = orderRepository
								.getOrderByNo(orderId);
						if (OrderStatus.INIT.getValue().equals(
								order.getOrderStatus())) {
							// 只有通过的时候才去更新余额,否则直接修改订单状态为失败
							if (OrderStatus.SUCCESS.getValue().equals(
									orderStatus)) {
								MemberEntity member = memberEntityMapper
										.selectByPrimaryKey(order.getMemberId());
								// 查询账户
								AccountEntity account = manualEntityMapper
										.selectAccountEntityByPrimaryKeyForUpdate(member
												.getAccountId());
								// 余额变更记录
								EntryEntity entry = geneEntryEntity(order,
										account);
								entry.setExt2(operation);
								// 记录余额变更历史
								entryEntityMapper.insert(entry);
								// 更新余额
								account.setBalance(entry.getAfterBalance());
								accountEntityMapper
										.updateByPrimaryKeySelective(account);
							}

							// 更新订单状态
							order.setOrderStatus(orderStatus);
							order.setExt2(operation);
							orderRepository.updateStatus(order);
						}
					}

				} catch (Exception e) {
					status.setRollbackOnly();
					log.error("订单处理失败:" + JSONUtil.toJson(orderIdList), e);
					throw e;
				}
				return true;
			}
		});

	}
}
