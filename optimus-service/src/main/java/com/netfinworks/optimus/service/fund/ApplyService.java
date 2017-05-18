package com.netfinworks.optimus.service.fund;


import com.netfinworks.optimus.ApplyRequest;
import com.netfinworks.optimus.service.entity.FundApplyOrderResult;

/**
 * <p></p>
 *
 */
public interface ApplyService {
    
	/**
     * 申购
     * @param request
     * @return
     */
    public FundApplyOrderResult apply(ApplyRequest request);
    
    /**
     * 申购重试
     * @param request
     * @return	
     */
    public FundApplyOrderResult applyRetry(String orderId);
    
//    /**
//     * FSN申购
//     * @param applyOrder
//     * @param oldStatus
//     * @param newStatus
//     * @return FundApplyOrderResult
//     */
//    public FundApplyOrderResult doInvest(EfsApplyOrder applyOrder, ApplyOrderStateKind oldStatus, ApplyOrderStateKind newStatus);
    
    
}
