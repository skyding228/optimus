package com.netfinworks.optimus.service.fund.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.netfinworks.common.util.DateUtil;
import com.netfinworks.optimus.ApplyRequest;
import com.netfinworks.optimus.domain.Constants;
import com.netfinworks.optimus.domain.enums.ApplyOrderStateKind;
import com.netfinworks.optimus.entity.AccountEntity;
import com.netfinworks.optimus.entity.OrderEntity;
import com.netfinworks.optimus.service.entity.FundApplyOrderResult;
import com.netfinworks.optimus.service.fund.ApplyService;
import com.netfinworks.optimus.service.integration.ErrorKind;
import com.netfinworks.optimus.service.integration.ServiceException;
import com.netfinworks.optimus.service.integration.mef.MefClient;
import com.netfinworks.optimus.service.integration.mef.MefTradeTypeKind;
import com.netfinworks.optimus.service.repository.AccountRepository;
import com.netfinworks.optimus.service.repository.OrderRepository;
import com.vf.mef.vo.InvestRedeemRequest;
import com.vf.mef.vo.InvestRedeemResponse;
@Service
public class ApplyServiceImpl implements ApplyService {
	private final Logger           logger = LoggerFactory.getLogger(this.getClass());
	@Resource(name = "transactionTemplate")
    private TransactionTemplate      transactionTemplate;
	@Resource(name = "mefClient")
	private MefClient mefClient;
	
	@Resource
	private OrderRepository applyOrderRepository;
	@Resource
	private AccountRepository accountRepository ;

	@Override
	public FundApplyOrderResult apply(ApplyRequest request) {
    	/************ 2.会员状态校验 *************/
//    	validate(request);    	
		//锁定
		AccountEntity account  = accountRepository.getAccountForUpdate(request.getMemberId()) ;
		if(account.getBalance().compareTo(request.getAmount())<0){
			throw new ServiceException(ErrorKind.INTEGRATION_ERROR, "账户余额不足");
		} ;
    	/************ 3.保存申购订单 ****************/
		OrderEntity applyOrder = save(request);
        /************ 4.FSN 申购 *******************/
		FundApplyOrderResult result =  doInvest(applyOrder, ApplyOrderStateKind.INIT, ApplyOrderStateKind.APPLY_PROCESS);
		/************ 5.更新余额 *******************/
		if(ApplyOrderStateKind.APPLY_SUCCESS == result.getApplyOrderStateKind()) {
			accountRepository.updateAccountBalance(applyOrder);
		}
		return result ;
	}

	@Override
	public FundApplyOrderResult applyRetry(String orderId) {
		/************ 1.根据订单号查询 ***********/
		OrderEntity applyOrder = applyOrderRepository.getOrderByNo(orderId);
    	if(applyOrder == null){
    		logger.error("没有找到orderId为{}的申购记录.", orderId);
    		throw new ServiceException(ErrorKind.RESPONSE_NOTFOUND);
    	}        
    	return doInvest(applyOrder, ApplyOrderStateKind.APPLY_EXCEPTION, ApplyOrderStateKind.APPLY_PROCESS);
	}
	
    public FundApplyOrderResult doInvest(OrderEntity applyOrder, ApplyOrderStateKind oldStatus, ApplyOrderStateKind newStatus) {
    	applyOrderRepository.updateApplyOrderStatus(applyOrder.getOrderId(), oldStatus, newStatus, newStatus.getMsg());
    	InvestRedeemResponse response = null;
		try {
			response = mefClient.investRedeemTrade(convertInvestRequest(applyOrder));
		} catch (Throwable t) {
			applyOrderRepository.updateApplyOrderStatus(applyOrder.getOrderId(), 
					ApplyOrderStateKind.APPLY_PROCESS, ApplyOrderStateKind.APPLY_EXCEPTION, 
					StringUtils.substring(t.getMessage(), Constants.MSG_BEGIN_SUBSTR, Constants.MSG_END_SUBSTR));
            throw new ServiceException(ErrorKind.INTEGRATION_ERROR, t);
		}
		ApplyOrderStateKind result = null;
		String memo = "";
        if(response != null && response.isSuccess()) { //通讯成功
        	if(StringUtils.startsWith(response.getReturnCode(), Constants.FOUD_OUT_PAY_STATUS_SUCCESS)) {     //成功
        		result = ApplyOrderStateKind.APPLY_SUCCESS;
        	}else if(StringUtils.startsWith(response.getReturnCode(), Constants.FOUD_OUT_PAY_STATUS_FAILURE) || 
        			StringUtils.startsWith(response.getReturnCode(), Constants.FOUD_OUT_PAY_STATUS_EXCEPTION)) {   //失败
        		result = ApplyOrderStateKind.APPLY_FAILURE;
        	}else if(StringUtils.startsWith(response.getReturnCode(), Constants.FOUD_OUT_PAY_STATUS_PROCESS)) {  //处理中
        		result = ApplyOrderStateKind.APPLY_BACK_PROCESS;
        	}
        	memo = StringUtils.substring(response.getReturnMessage(), Constants.MSG_BEGIN_SUBSTR, Constants.MSG_END_SUBSTR);
        }else{
        	result = ApplyOrderStateKind.APPLY_EXCEPTION;
        	memo = "通讯失败";
        }
        if(StringUtils.isBlank(memo)) {
        	memo = result.getMsg();
        }
        applyOrderRepository.updateApplyOrderStatus(applyOrder.getOrderId(), ApplyOrderStateKind.APPLY_PROCESS,
    			result, memo);
        return FundApplyOrderResult.result(applyOrder.getOrderId(), result);
    }
    
    //转换mef请求
    private InvestRedeemRequest convertInvestRequest(OrderEntity applyOrder) {
    	InvestRedeemRequest request = new InvestRedeemRequest();
    	request.setAmount(applyOrder.getAmount().toString());
    	request.setChannelNo(applyOrder.getChanId());
    	request.setProductNo(applyOrder.getProductId());
    	request.setMemberId(applyOrder.getMemberId());
    	request.setRequestNo(applyOrder.getOrderId());
    	request.setTradeType(MefTradeTypeKind.INVEST.getCode());
    	request.setRequestTime(DateUtil.getNewFormatDateString(applyOrder.getOrderTime()));
    	return request;
    }
	
	
	
    private OrderEntity save(final ApplyRequest request) {
		return transactionTemplate.execute(new TransactionCallback<OrderEntity>() {
            @Override
            public OrderEntity doInTransaction(TransactionStatus status) {
            	// 1、保存申购订单 初始状态为INIT            	
            	OrderEntity applyOrder = null ;
        		try{
        			applyOrder = applyOrderRepository.createApplyOrder(request);
        		}catch(DuplicateKeyException e){
        		    logger.error("创建申购记录时发生异常.RequestNo:{}",request,e);
        		    return null;
        		}
        		// 2、更新申购挂起值
        		//BigDecimal totalHang = signService.handleTotalHang(TotalHangHandleType.APPLY_APPLY, applyOrder.getApplyAmount().getAmount(), applyOrder.getMemberId());
        		//logger.info("[EFS]申购更新挂起值，memberId:{}, applyOrderHang:{}", applyOrder.getMemberId(), totalHang);
        		return applyOrder;
            }            
        });
	}
	

}
