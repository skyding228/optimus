package com.netfinworks.optimus.facade;

import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import com.netfinworks.cloud.rpc.RpcService;
import com.netfinworks.optimus.entity.AccountEntity;
import com.netfinworks.optimus.entity.MemberEntity;
import com.netfinworks.optimus.entity.OrderEntity;
import com.netfinworks.optimus.entity.PaymentEntity;
import com.netfinworks.optimus.request.ChanPublicAccountAuditRequest;
import com.netfinworks.optimus.request.ChanPublicAccountManualBookRequest;
import com.netfinworks.optimus.request.EntryQueryRequest;
import com.netfinworks.optimus.request.ManualBookRequest;
import com.netfinworks.optimus.request.MemberPayRequest;
import com.netfinworks.optimus.request.MemberQueryRequest;
import com.netfinworks.optimus.response.AllMemberStatResponse;
import com.netfinworks.optimus.response.EntryQueryResponse;
import com.netfinworks.optimus.response.OrderQueryResponse;
@WebService(targetNamespace = "http://optimus.api.ws.netfinworks.com")
@RpcService
public interface OptimusFacade {
	 /**
     * 变动明细查询
     * @param request
     * @return
     */
	EntryQueryResponse queryEntryEntity(EntryQueryRequest request);	
	/**
     * 查询渠道余额用户统计
     * @param chan
     * @return
     */
	AllMemberStatResponse queryAllMemberEntity(String chan);
	/**
     * 会员查询
     * @param request
     * @return
     */
	MemberEntity queryMemberEntity(MemberQueryRequest request);
	/**
     * 会员账户查询
     * @param accountId
     * @return
     */
	AccountEntity queryAccountEntity(String accountId);
	
	/**
     * invest外部调用支付
     * @param request
     * @return
     */
	OrderEntity pay(MemberPayRequest request);
	
	/**
     * 会员手工记账
     * @param request
     * @return
     */
	OrderEntity manaulBook(ManualBookRequest request);
	/**
     * 公管账户手工记账
     * @param request
     * @return
     */
	OrderEntity manaulBookPublicAccount(ChanPublicAccountManualBookRequest request);
	/**
     * 公管账户手工记账审核
     * @param request
     * @return
     */
	void manaulBookAudit(ChanPublicAccountAuditRequest request);
	/**
     * 维金待审核记录
     */
	List<OrderEntity> queryOrderListToAudit(String chanId);
	/**
     * 维金待渠道审核记录
     */
	List<OrderEntity> queryOrderListWaitAudit(String chanId);
	/**
     * 待出入款记录
     */
	List<PaymentEntity> queryWaitPayments(String orderId) ;
	/**
     * 渠道待审核出入款记录
     */
//	List<PaymentEntity> queryWaitAudits() ;
	/**
	 * 满标放款前check 放款给借款人的钱 审核成功
	 */
	int countPaymentSuccessBySubjectNo(String subjectNo,String actionType);
	
	OrderQueryResponse queryAccountTrade(int pageNum, int pageSize, Date date,String chanId,List<String> orderTypes);
	
	List<OrderEntity> queryProvisionRecoveryOrder(String subjectNo,String plat);
}
