package com.netfinworks.optimus.service.integration.mef;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.vf.mef.MefRequest;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RedeemAllRequest extends MefRequest {
    private String requestNo;//渠道请求号
    private String memberId;//会员编号
    private String requestTime;//请求时间
    private String memo;//备注
    private String extension;//扩展信息

    public String getRequestNo() {
        return requestNo;
    }

    public void setRequestNo(String requestNo) {
        this.requestNo = requestNo;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
