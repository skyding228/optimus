package com.netfinworks.optimus;

import com.netfinworks.common.monitor.web.servlet.HeathCheckDispatcherServlet;
import com.netfinworks.optimus.admin.web.filter.LoginFilter;
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
@ImportResource(locations = {"classpath:spring/applicationContext.xml", "classpath:spring/springmvc-servlet.xml"})
public class AdminApp extends SpringBootServletInitializer {

    public static void main(String[] args) {
        new SpringApplicationBuilder(AdminApp.class).profiles(MapUtils.getString(System.getenv(), "profile", "dev")).run(args);
    }

    /**
     * 登录 过滤器
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean loginFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setName("login");
        LoginFilter filter = new LoginFilter();
        registrationBean.setFilter(filter);
        registrationBean.setOrder(1);


        List<String> patterns = new ArrayList<>();
        patterns.add("/*");
        registrationBean.setUrlPatterns(patterns);

        return registrationBean;
    }


}
