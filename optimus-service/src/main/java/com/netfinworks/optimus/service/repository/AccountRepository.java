package com.netfinworks.optimus.service.repository;

import com.netfinworks.optimus.entity.AccountEntity;
import com.netfinworks.optimus.entity.ChanStatsEntity;
import com.netfinworks.optimus.entity.OrderEntity;
import com.netfinworks.optimus.service.entity.EntryQueryParams;
import com.netfinworks.optimus.service.entity.EntryQueryResult;

public interface AccountRepository {
	/**
	 * 查询
	 */
	AccountEntity getAccount(String accountId);

	/**
	 * 更新余额
	 */
	AccountEntity updateAccountBalance(OrderEntity order);

	/**
	 * 查询for更新
	 */
	AccountEntity getAccountForUpdate(String accountId);

	/**
	 * 查询余额变动
	 */
	EntryQueryResult queryEntry(EntryQueryParams entryQueryParams);

	/**
	 * 查询总余额
	 */
	ChanStatsEntity queryTotalBalanceByChan(String chanId);

	/**
	 * 审核公管账户
	 * 
	 * @param orderIdList
	 * @param operation
	 *            操作员姓名
	 * @param status
	 *            状态,订单的成功(通过)或者失败(驳回)状态
	 */
	void auditPublicAccount(String[] orderIdList, String operation,
			String status);
}
