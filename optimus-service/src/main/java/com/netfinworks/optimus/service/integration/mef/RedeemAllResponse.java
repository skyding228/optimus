package com.netfinworks.optimus.service.integration.mef;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.vf.mef.MefResponse;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RedeemAllResponse extends MefResponse {
    private String requestNo;//渠道请求号
    private String tradeNo;//交易订单号
    private String amount;//交易金额
    private String extension;//扩展信息

    public String getRequestNo() {
        return requestNo;
    }

    public void setRequestNo(String requestNo) {
        this.requestNo = requestNo;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
