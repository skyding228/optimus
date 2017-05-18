package com.netfinworks.optimus.facade;

import com.netfinworks.cloud.rpc.spring.AutoRpcClientProxyCreator;
import com.netfinworks.cloud.rpc.spring.AutoRpcServiceExporter;
import com.netfinworks.common.monitor.web.servlet.HeathCheckDispatcherServlet;
import org.apache.commons.collections.MapUtils;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableEurekaClient
//@Import({BeanConfig.class})
@ImportResource(locations = {"classpath:spring/applicationContext-core.xml"})
public class CoreApp extends SpringBootServletInitializer {

    public static void main(String[] args) {
        new SpringApplicationBuilder(CoreApp.class).profiles(MapUtils.getString(System.getenv(), "profile", "dev")).run(args);
    }

    /**
     * 对外暴露rpc服务
     *
     * @return
     */
    @Bean
    public static AutoRpcServiceExporter autoJsonRpcServiceExporter() {
        //可以多次添加，包路径下面的子包也会扫描
        AutoRpcServiceExporter.addImplScanPackage("com.netfinworks.optimus.facade.impl");
        AutoRpcServiceExporter exporter = new AutoRpcServiceExporter();
//        exporter.setObjectMapper(new JacksonObjectMapper());
        return exporter;
    }


    /**
     * 创建client rpc服务访问接口
     *
     * @return
     */
    @Bean
    public static AutoRpcClientProxyCreator clientProxyCreator() {

        AutoRpcClientProxyCreator creator = new AutoRpcClientProxyCreator();
        creator.setScanPackage("com.netfinworks.invest.facade");
        creator.setServiceId("invest");
        return creator;
    }

    /**
     * 不加 JacksonAutoConfiguration JacksonProperties为null
     */
    @Bean
    public Jackson2ObjectMapperBuilder jacksonBuilder() {
        return new Jackson2ObjectMapperBuilder()
                .indentOutput(true);
    }

    /**
     * cxf 过滤器
     *
     * @return
     */
    @Bean
    public ServletRegistrationBean cxfServlet() {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean();
        registrationBean.setName("login");
        registrationBean.setServlet(new CXFServlet());
        registrationBean.setOrder(1);


        List<String> patterns = new ArrayList<>();
        patterns.add("/ws/*");
        registrationBean.setUrlMappings(patterns);

        return registrationBean;
    }


}
