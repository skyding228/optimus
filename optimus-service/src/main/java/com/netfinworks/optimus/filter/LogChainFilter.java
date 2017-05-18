package com.netfinworks.optimus.filter;


import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 日志链
 */
public class LogChainFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String id = req.getHeader("chain-id");
        if (!StringUtils.isEmpty(id)) {
            Thread.currentThread().setName(id);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}