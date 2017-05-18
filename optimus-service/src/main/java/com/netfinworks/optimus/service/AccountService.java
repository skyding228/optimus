package com.netfinworks.optimus.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.netfinworks.invest.entity.InvestInstallment;
import com.netfinworks.optimus.PayRequest;
import com.netfinworks.optimus.domain.AccountInfo;
import com.netfinworks.optimus.domain.OrderResponse;
import com.netfinworks.optimus.domain.RestResult;
import com.netfinworks.optimus.domain.TokenBean;
import com.netfinworks.optimus.entity.AccountCheckEntity;
import com.netfinworks.optimus.entity.AccountEntity;
import com.netfinworks.optimus.entity.ChanStatsEntity;
import com.netfinworks.optimus.entity.MemberEntity;
import com.netfinworks.optimus.entity.OrderEntity;
import com.netfinworks.optimus.service.entity.EntryQueryParams;
import com.netfinworks.optimus.service.entity.EntryQueryResult;

@Service
public interface AccountService {
	/**
	 * 查询
	 */
	AccountEntity getAccount(String accountId);

	/**
	 * 更新余额 = 支付
	 */
	AccountEntity updateAccountBalance(OrderEntity order);

	/**
	 * 充值
	 * 
	 * @param amount
	 * @param pwd
	 * @param url
	 * @param bean
	 * @return
	 */
	AccountEntity deposit(BigDecimal amount, String pwd, String url,
			TokenBean bean);

	/**
	 * 提现
	 * 
	 * @param amount
	 * @param pwd
	 * @param url
	 * @param bean
	 * @return
	 */
	AccountEntity withdraw(BigDecimal amount, String pwd, String url,
			TokenBean bean);

	/**
	 * 支付 给 invest用
	 * 
	 * @param payRequest
	 *            支付信息
	 * @param member
	 *            会员账户
	 */
	OrderEntity pay(PayRequest payRequest, MemberEntity member);

	/**
	 * 查询
	 */
	EntryQueryResult queryEntry(EntryQueryParams entryQueryParams);

	/**
	 * 查询平台
	 *
	 */
	ChanStatsEntity queryTotalBalanceByChan(String chanId);

	/**
	 * 保存订单
	 *
	 */
	void saveOrderEntity(OrderEntity orderentity);

	/**
	 * 审核操作
	 * 
	 * @param orderIdList
	 * @param operation
	 *            操作员名称
	 * @param status
	 *            状态,订单的成功(通过)或者失败(驳回)状态
	 */
	void auditPublicAccount(String[] orderIdList, String operation,
			String status);

	/**
	 * 查询待审核 记录
	 * 
	 * @param chanId
	 *            渠道编号
	 * @param types
	 *            类型列表
	 * @return
	 */
	public List<OrderEntity> queryWaitAuditList(String chanId,
			List<String> types);

	/**
	 * 查询待执行出入款记录
	 * 
	 * @param chanId
	 * @return
	 */
	public List<OrderEntity> queryWaitExecList(String chanId);

	/**
	 * 更新待出入款记录，插入表中
	 * 
	 * @param plat
	 *            平台编码
	 */
	public void updatePaymentRecords(String plat);

	/**
	 * 查询对账记录
	 * 
	 * @param chanId
	 *            渠道编号
	 * @param date
	 *            日期 yyyy-MM-dd
	 * @param pageNum
	 *            从1开始 页码
	 * @param pageSize
	 *            页大小
	 * @return
	 */
	public List<AccountCheckEntity> makeAccountCheckingFile(String chanId,
			String date, int pageNum, int pageSize);

	/**
	 * 调用充值提现接口之后
	 * 
	 * @param response
	 */
	public AccountEntity afterAccountChange(OrderResponse response);

	/**
	 * 查询账户余额
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param userId
	 * @return
	 */
	public RestResult queryAccountOverview(int pageNum, int pageSize,
			String userId);

	/**
	 * 计算会员账户总览
	 * 
	 * @param member
	 * @return
	 */
	public AccountInfo calcOverview(MemberEntity member);

	/**
	 * 查询个人投资概况
	 * 
	 * @param member
	 * @return {subjectNotConfirmV：未确认金额 subjectNotConfirmList: 未确认产品列表、
	 *         subjectConfirmV
	 *         /subjectConfirmList/subjectFinishV/subjectFinishList }
	 */
	public Map<String, Object> queryInvestOverview(MemberEntity member);

	/**
	 * 获取平台账号
	 * 
	 * @param plat
	 * @return
	 */
	public String getPlatAccountId(String plat);

	/**
	 * 获取账户某日余额
	 * 
	 * @param accountId
	 * @param date
	 * @return
	 */
	public BigDecimal getAccountBalanceOfDay(String accountId, Date date);

	/**
	 * 查询分期还款信息
	 * 
	 * @param subjectNo
	 * @return
	 */
	public InvestInstallment queryInvestInstatllment(String subjectNo);
}
