package com.netfinworks.optimus.service.entity;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.netfinworks.optimus.domain.enums.ApplyOrderStateKind;


/**
 * 申购响应 2.0
 *
 */
public class FundApplyOrderResult {
    
    /**
     * 申购状态
     */
    private ApplyOrderStateKind applyOrderStateKind;
    
    /*
     * 申购订单号
     */
    private String                         applyOrderNo;
    
    public ApplyOrderStateKind getApplyOrderStateKind() {
        return applyOrderStateKind;
    }

    public void setApplyOrderStateKind(ApplyOrderStateKind applyOrderStateKind) {
        this.applyOrderStateKind = applyOrderStateKind;
    }

    public String getApplyOrderNo() {
        return applyOrderNo;
    }

    public void setApplyOrderNo(String applyOrderNo) {
        this.applyOrderNo = applyOrderNo;
    }

    public static FundApplyOrderResult result(String applyOrderNo,ApplyOrderStateKind state){
        FundApplyOrderResult applyOrderResult = new FundApplyOrderResult();
        applyOrderResult.setApplyOrderNo(applyOrderNo);
        applyOrderResult.setApplyOrderStateKind(state);
        return applyOrderResult;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
    }
}
