package com.netfinworks.optimus.service.fund;

import com.netfinworks.optimus.RedeemRequest;
import com.netfinworks.optimus.service.entity.FundRedeemResult;

public interface RedeemService {
	/**
     * 资金赎回
     * @param fundRedeem
     * @return
     */
    public FundRedeemResult redeem(RedeemRequest fundRedeem);

    /**
     * 资金赎回
     * @param order
     * @return
     */
    public FundRedeemResult redeemRetry(String orderId);
    
}
