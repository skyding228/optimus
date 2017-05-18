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
import com.netfinworks.optimus.RedeemRequest;
import com.netfinworks.optimus.domain.Constants;
import com.netfinworks.optimus.domain.enums.ApplyOrderStateKind;
import com.netfinworks.optimus.domain.enums.RedeemOrderStateKind;
import com.netfinworks.optimus.entity.OrderEntity;
import com.netfinworks.optimus.service.entity.FundRedeemResult;
import com.netfinworks.optimus.service.fund.RedeemService;
import com.netfinworks.optimus.service.integration.ErrorKind;
import com.netfinworks.optimus.service.integration.ServiceException;
import com.netfinworks.optimus.service.integration.mef.MefClient;
import com.netfinworks.optimus.service.integration.mef.MefTradeTypeKind;
import com.netfinworks.optimus.service.repository.OrderRepository;
import com.vf.mef.vo.InvestRedeemRequest;
import com.vf.mef.vo.InvestRedeemResponse;
@Service
public class RedeemServiceImpl implements RedeemService{
	private final Logger           logger = LoggerFactory.getLogger(this.getClass());
	@Resource(name = "transactionTemplate")
    private TransactionTemplate      transactionTemplate;
	@Resource(name = "mefClient")
	private MefClient mefClient;
	
	@Resource
	private OrderRepository redeemOrderRepository;

	

	@Override
	public FundRedeemResult redeem(RedeemRequest request) {
		/************ 2.会员状态校验 *************/
		
		/************ 3.落地赎回交易表 ****************/
		OrderEntity redeemOrder = save(request);    	
		/************ 4.赎回 ****************/
		return doRedeem(redeemOrder, RedeemOrderStateKind.INIT, RedeemOrderStateKind.REDEEM_PROCESS);		
	}

	@Override
	public FundRedeemResult redeemRetry(String orderId) {
		/************ 1.根据订单号查询 ***********/
		OrderEntity order = redeemOrderRepository.getOrderByNo(orderId);
    	if(order == null){
    		logger.error("没有找到orderId为{}的赎回记录.", orderId);
    		throw new ServiceException(ErrorKind.RESPONSE_NOTFOUND);
    	}        
    	return doRedeem(order, RedeemOrderStateKind.REDEEM_EXCEPTION, RedeemOrderStateKind.REDEEM_PROCESS);    		        
	     
	}
	
	private FundRedeemResult doRedeem(OrderEntity order, RedeemOrderStateKind newState, RedeemOrderStateKind oldState) {
		redeemOrderRepository.updateRedeemOrderStatus(order.getOrderId(), oldState, newState, newState.getMsg());
		
    	InvestRedeemResponse response = null;
    	try {
			response = mefClient.investRedeemTrade(convertRedeemRequest(order));			
		} catch (Throwable t) {
			redeemOrderRepository.updateRedeemOrderStatus(order.getOrderId(), RedeemOrderStateKind.REDEEM_PROCESS, RedeemOrderStateKind.REDEEM_EXCEPTION, StringUtils.substring(t.getMessage(), Constants.MSG_BEGIN_SUBSTR, Constants.MSG_END_SUBSTR));
			throw new ServiceException(ErrorKind.INTEGRATION_ERROR, t);
		}
    	RedeemOrderStateKind result = null;
    	String memo = "";
    	if(response != null && response.isSuccess()) {  //通讯成功
    		if(StringUtils.startsWith(response.getReturnCode(), Constants.FOUD_OUT_PAY_STATUS_SUCCESS)) {      //成功
    			result = RedeemOrderStateKind.REDEEM_SUCCESS;
    		}else if(StringUtils.startsWith(response.getReturnCode(), Constants.FOUD_OUT_PAY_STATUS_FAILURE) || 
    				StringUtils.startsWith(response.getReturnCode(), Constants.FOUD_OUT_PAY_STATUS_EXCEPTION)) {    //失败
    			result = RedeemOrderStateKind.REDEEM_FAILURE;
    		}else if(StringUtils.startsWith(response.getReturnCode(), Constants.FOUD_OUT_PAY_STATUS_PROCESS)) {   //处理中
    			result = RedeemOrderStateKind.REDEEM_BACK_PROCESS;
    		}
    		memo = StringUtils.substring(response.getReturnMessage(), Constants.MSG_BEGIN_SUBSTR, Constants.MSG_END_SUBSTR);
    	}else{
    		result = RedeemOrderStateKind.REDEEM_EXCEPTION;
    		memo = "通讯失败";
    	}
    	if(StringUtils.isBlank(memo)) {
        	memo = result.getMsg();
        }    	
    	redeemOrderRepository.updateRedeemOrderStatus(order.getOrderId(), RedeemOrderStateKind.REDEEM_PROCESS, result, 
    			memo);
    	//组织结果返回
    	return FundRedeemResult.result(order.getOrderId(), result);
    }
    
    private InvestRedeemRequest convertRedeemRequest(OrderEntity order) {
    	InvestRedeemRequest request = new InvestRedeemRequest();
    	request.setAmount(order.getAmount().toString());
    	request.setMemberId(order.getMemberId());
    	request.setRequestNo(order.getOrderId());
    	request.setRequestTime(DateUtil.getNewFormatDateString(order.getOrderTime()));
    	request.setTradeType(MefTradeTypeKind.REDEEM.getCode());
    	request.setChannelNo(order.getChanId());
    	request.setProductNo(order.getProductId());
    	return request;
    }
    
    private OrderEntity save(final RedeemRequest request) {
		return transactionTemplate.execute(new TransactionCallback<OrderEntity>() {
            @Override
            public OrderEntity doInTransaction(TransactionStatus status) {
            	// 1、保存申购订单 初始状态为INIT            	
            	OrderEntity applyOrder = null ;
        		try{
        			applyOrder = redeemOrderRepository.createRedeemOrder(request);
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
