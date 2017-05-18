package com.netfinworks.optimus.facade.util;

import com.netfinworks.optimus.response.PageQueryResponse;
import com.netfinworks.optimus.domain.enums.ReturnCode;
import com.netfinworks.optimus.response.Response;
import com.netfinworks.optimus.service.entity.QueryPagingResult;

public class ResponseConverterUtil {
	/**
     * 设置CODE
     * @param response
     * @param code
     * @param msg
     */
    public static void initBaseResponse(Response response,ReturnCode code, String msg){
        response.setReturnCode(code.getCode());
        response.setReturnMessage(msg);
    }
    
    
    /**
     * 换换分页
     * @param page
     * @param response
     */
    public static void populatePage(QueryPagingResult page,PageQueryResponse response){
        
        if(page == null || response == null){
            return ;
        }
        response.setItemCount(page.getItemCount());
        response.setPageCount(page.getPageCount());
        response.setPageIndex(page.getPageIndex());
        response.setPageSize(page.getPageSize());
    }
}
