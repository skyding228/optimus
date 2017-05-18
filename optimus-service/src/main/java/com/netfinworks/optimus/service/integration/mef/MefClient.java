package com.netfinworks.optimus.service.integration.mef;

import com.vf.mef.vo.InvestRedeemRequest;
import com.vf.mef.vo.InvestRedeemResponse;

/**
 * <p>mef调用</p>
 *
 * @author 
 * @version 
 */
public interface MefClient {

    /**
     * 申购/赎回
     * @param request
     * @return
     */
	InvestRedeemResponse investRedeemTrade(InvestRedeemRequest request);
    
    /**
     * 全部赎回
     * @param request
     * @return
     */
//    RedeemAllResponse redeemAllTrade(RedeemAllRequest request);
}
