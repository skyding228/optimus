/**
 *
 */
package com.netfinworks.optimus.service.integration.common;


/**
 * <p>接入点代理</p>
 * @author 
 * @version 
 */
public interface IntegrationProxy<T> {
    /**
     * 构建接入点实例
     * @return 实例
     */
    T buildInst();

    public void setService(T service);
}
