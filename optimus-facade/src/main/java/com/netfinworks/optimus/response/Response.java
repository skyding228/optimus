package com.netfinworks.optimus.response;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * <p>注释</p>
 */
public abstract class Response {
    /** 返回编码  */
    private String returnCode;
    /** 返回信息  */
    private String returnMessage;

    public Response() {
        super();
    }

    public Response(String returnCode, String returnMessage) {
        super();
        this.returnCode = returnCode;
        this.returnMessage = returnMessage;
    }
    
    public Response(boolean isSuccess, String returnCode, String returnMessage) {
        super();
        this.returnCode = returnCode;
        this.returnMessage = returnMessage;
    }

	public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
