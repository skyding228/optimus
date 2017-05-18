package com.netfinworks.optimus.mapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.netfinworks.optimus.entity.AccountCheckEntity;
import com.netfinworks.optimus.entity.AccountEntity;
import com.netfinworks.optimus.entity.ChanStatsEntity;
import com.netfinworks.optimus.entity.ChanViewEntity;
import com.netfinworks.optimus.entity.EntryEntity;
import com.netfinworks.optimus.entity.PaymentEntity;

public interface ManualEntityMapper {
	// manual
	List<EntryEntity> queryEntryList(Map<String, Object> map);

	// manual
	int countEntryList(Map<String, Object> map);

	// manual
	ChanStatsEntity queryTotalBalanceByChan(String chanId);

	// 查询账户
	AccountEntity selectAccountEntityByPrimaryKeyForUpdate(String accountId);

	/**
	 * 查询对账文件
	 * 
	 * @param chanId
	 *            渠道编号
	 * @param date
	 *            对账日期 yyyy-MM-dd
	 * @param offset
	 * @param pageSize
	 * @return
	 */
	List<AccountCheckEntity> queryAccountChecking(Map<String, Object> map);

	/**
	 * 
	 * @param List
	 *            <String> orderTypes ('deposit','withdraw')
	 * @param String
	 *            orderStatus 'S'
	 * @param String
	 *            plat 平台编码，可选
	 * @param Date
	 *            date 日期 可选
	 * @return
	 */
	List<PaymentEntity> queryAccountNetting(Map<String, Object> map);

	/**
	 * 满标放款前check 放款给借款人的钱 审核成功
	 */
	int countPaymentSuccessBySubjectNo(Map<String, Object> map);

	/**
	 * 统计平台某日某类型操作总额
	 * 
	 * @param chanId
	 * @return
	 */
	BigDecimal countChanTotal(@Param("chanId") String chanId,
			@Param("date") Date date, @Param("orderType") String orderType,
			@Param("actionType") String actionType);

	/**
	 * 平台某日还款的产品编号列表
	 * 
	 * @param chanId
	 * @return
	 */
	List<String> queryChanRepayemntSubjectNos(@Param("chanId") String chanId,
			@Param("date") Date date);

	/**
	 * 查询备付金投资
	 * 
	 * @param chanId
	 * @param date
	 * @return
	 */
	BigDecimal countProvisionInvest(@Param("chanId") String chanId,
			@Param("date") Date date);

	/**
	 * 查询备付金还款本金和利息
	 * 
	 * @param chanId
	 * @param date
	 * @param provisionMemberId
	 *            平台的备付金账户
	 * @return
	 */
	List<ChanViewEntity> countProvisionRecovery(@Param("chanId") String chanId,
			@Param("date") Date date,
			@Param("memberId") String provisionMemberId);
}