package com.netfinworks.optimus.h5.web;

import com.netfinworks.common.monitor.web.servlet.HeathCheckDispatcherServlet;
import com.netfinworks.optimus.h5.web.filter.SsoFilter;
import org.apache.commons.collections.MapUtils;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableEurekaClient
@ImportResource(locations = {"classpath:spring/applicationContext.xml", "classpath:spring/springmvc-servlet.xml", "classpath:spring/springmvc-interceptor.xml"})
public class H5App extends SpringBootServletInitializer {

    public static void main(String[] args) {
        new SpringApplicationBuilder(H5App.class).profiles(MapUtils.getString(System.getenv(), "profile", "dev")).run(args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(H5App.class);
    }

    /**
     * 登录 过滤器
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean ssoFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setName("sso");
        SsoFilter filter = new SsoFilter();
        registrationBean.setFilter(filter);
        registrationBean.setOrder(1);


        List<String> patterns = new ArrayList<>();
        patterns.add("/*");
        registrationBean.setUrlPatterns(patterns);

        return registrationBean;
    }


}
