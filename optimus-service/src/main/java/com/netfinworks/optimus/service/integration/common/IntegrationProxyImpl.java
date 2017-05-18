/**
 *
 */
package com.netfinworks.optimus.service.integration.common;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.springframework.beans.factory.InitializingBean;

/**
 * <p>接入点代理实现</p>
 * @author 
 * @version 
 */
public class IntegrationProxyImpl<T> implements IntegrationProxy<T>, InitializingBean {

    private String   address;
    private Long     timeout;
    private Class<T> clazz;

    private T        cachedService;

    /**
     * 构建接入点实例
     *
     * @return 实例
     */
    @Override
    public T buildInst() {
        return cachedService;
    }

    /**
     * 设置服务的客户端策略
     * 连接时间: 默认=30s 设置为15s,
     * 接收超时时间: 默认=60s 设置为15s
     * 自动分组：默认=true 设置为false
     * 是否保持连接:使用默认设置=true
     *
     *
     * @param service  服务实例
     */
    private void configPolicy(T service) {
        Client clientP = ClientProxy.getClient(service);
        HTTPConduit http = (HTTPConduit) clientP.getConduit();
        HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
        httpClientPolicy.setConnectionTimeout(timeout);
        httpClientPolicy.setReceiveTimeout(timeout);
        httpClientPolicy.setAllowChunking(false);
        http.setClient(httpClientPolicy);
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }

    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void afterPropertiesSet() throws Exception {
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setServiceClass(clazz);
        factory.setAddress(address);
        T service = (T) factory.create();
        configPolicy(service);
        cachedService = service;
    }
    @Override
    public void setService(T service){
        this.cachedService = service;
    }
}
