package com.netfinworks.optimus.admin.web.filter;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netfinworks.optimus.admin.util.ControllerUtil;
import com.netfinworks.optimus.admin.web.CommonConstants;
import com.netfinworks.optimus.entity.MemberEntity;

public class LoginFilter implements Filter {
    static Logger log = LoggerFactory.getLogger(LoginFilter.class);
    // 优先匹配
    private static final Pattern[] excludeUrls = {
            Pattern.compile("/jsonrpc.*"),
            Pattern.compile("/static/.*"), Pattern.compile("/refreshConfig"),
            Pattern.compile("/login"), Pattern.compile("/error"), Pattern.compile("/dologin"),
            Pattern.compile("/_health_check"), Pattern.compile("/task/execute")};

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // TODO Auto-generated method stub

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String uri = req.getRequestURI().replaceFirst(req.getContextPath(), "");

        for (Pattern pattern : excludeUrls) {
            if (pattern.matcher(uri).matches()) {
                chain.doFilter(request, response);
                return;
            }
        }
        MemberEntity member = ControllerUtil.retriveFromSession(req,
                CommonConstants.SESSION_LOGIN_USER, MemberEntity.class);

        if (member == null) {
            log.info("当前未登录,重定向{}->/login", uri);
            ControllerUtil.redirect(req, res, "/login");
            return;
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

//	public static void main(String[] args) {
//		Pattern p = Pattern.compile("/static/.*");
//		System.out.println(p.matcher("/static/11/22").matches());
//		System.out.println(p.matcher("qw/static/11/22").matches());
//		System.out.println(p.matcher("/static").matches());
//		System.out.println(p.matcher("/static/11").matches());
//	}
}
