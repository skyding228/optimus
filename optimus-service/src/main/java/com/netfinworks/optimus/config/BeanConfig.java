package com.netfinworks.optimus.config;

import com.netfinworks.common.monitor.web.servlet.HeathCheckDispatcherServlet;
import com.netfinworks.optimus.filter.LogChainFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.netfinworks.cloud.rpc.spring.AutoRpcClientProxyCreator;
import com.netfinworks.invest.facade.InvestFacade;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class BeanConfig {
    private static Logger log = LoggerFactory.getLogger(BeanConfig.class);

    //    @Bean
//    public static AutoJsonRpcServiceExporter rpcServiceExporter(@Value("${spring.application.name:rpctest}") String serviceId) {
//        AutoJsonRpcServiceExporter exporter = new AutoJsonRpcServiceExporter();
//        exporter.addImplScanPackage(RpcTestImpl.class.getPackage().getName());
//        return exporter;
//    }
//
//    /**
//     * 投资服务rpc配置
//     *
//     * @return
//     */
//    @Bean
//    public static AutoJsonRpcClientProxyCreator rpcClientProxyCreator(@Value("${spring.application.name:rpctest}") String serviceId) {
//        AutoJsonRpcClientProxyCreator creator = new AutoJsonRpcClientProxyCreator();
//        creator.setScanPackage(RpcTest.class.getPackage().getName());
//        creator.setServiceId("h5");
//        log.info("自动创建rpc客户端,{}->{}", creator.getServiceId(), RpcTest.class.getPackage().getName());
//        return creator;
//    }

    /**
     * 投资服务rpc配置
     *
     * @return
     */
    @Bean
    public static AutoRpcClientProxyCreator clientProxyCreator(Environment environment) {
        AutoRpcClientProxyCreator creator = new AutoRpcClientProxyCreator();
        creator.setScanPackage(InvestFacade.class.getPackage().getName());
        String investWebServiceId = environment.getProperty("investWeb.service.id", "invest");
        creator.setServiceId(investWebServiceId);
        return creator;
    }

    /**
     * 日志链 过滤器
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean loginFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setName("logchain");
        LogChainFilter filter = new LogChainFilter();
        registrationBean.setFilter(filter);
        registrationBean.setOrder(2);

        List<String> patterns = new ArrayList<>();
        patterns.add("/*");
        registrationBean.setUrlPatterns(patterns);

        return registrationBean;
    }

    /**
     * 健康检查
     *
     * @return
     */
    @Bean
    public ServletRegistrationBean healthServelt() {
        HeathCheckDispatcherServlet heathCheckDispatcherServlet = new HeathCheckDispatcherServlet();
        return new ServletRegistrationBean(heathCheckDispatcherServlet, "/_health_check");
    }
}
