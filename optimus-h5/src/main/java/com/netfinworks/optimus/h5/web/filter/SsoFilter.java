package com.netfinworks.optimus.h5.web.filter;

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

import com.netfinworks.cloud.rpc.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.netfinworks.optimus.domain.TokenBean;
import com.netfinworks.optimus.domain.UserInfo;
import com.netfinworks.optimus.entity.MemberEntity;
import com.netfinworks.optimus.h5.web.CommonConstants;
import com.netfinworks.optimus.h5.web.util.ControllerUtil;
import com.netfinworks.optimus.service.auth.AuthService;
import com.netfinworks.optimus.utils.FormatUtil;

/**
 * xinyifu sso
 */
public class SsoFilter implements Filter {
    private Logger log = LoggerFactory.getLogger(getClass());

    // 优先匹配,无需登录验证的路径,其余路径都需要
    private static final Pattern[] excludeUrls = {
            Pattern.compile("/jsonrpc.*"),
            Pattern.compile("/static/.*"), Pattern.compile("/to/.*"),
            Pattern.compile("/api/.*"), Pattern.compile("/action/.*"),
            Pattern.compile("/docs/.*"), Pattern.compile("/login"), Pattern.compile("/error"),
            Pattern.compile("/nologin"), Pattern.compile("/refreshConfig"),
            Pattern.compile("/mock/.*"), Pattern.compile("/test/.*"),
            Pattern.compile("/_health_check"), Pattern.compile("/task/execute"), Pattern.compile("/health"), Pattern.compile("/info"), Pattern.compile(Util.RPC_PATH_PREFIX + ".*")};
    // 需要sso验证的路径
    private static final Pattern[] ssoUrls = {Pattern.compile("/fund/.*"),
            Pattern.compile("/invest/.*"), Pattern.compile("/account/.*")};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        // 模拟登录
        toLogin(req);

        String uri = req.getRequestURI().replaceFirst(req.getContextPath(), "");

        for (Pattern pattern : excludeUrls) {
            if (pattern.matcher(uri).matches()) {
                chain.doFilter(request, response);
                return;
            }
        }
        TokenBean tokenBean = ControllerUtil.retriveFromSession(req,
                CommonConstants.SESSION_LOGIN_USER, TokenBean.class);

        if (tokenBean == null) {
            log.info("当前未登录,重定向{}->/nologin", uri);
            ControllerUtil.redirect(req, res, "/nologin");
            return;
        } else {
            // 判断如果uri需要sso验证就去验证
            for (Pattern pattern : ssoUrls) {
                if (pattern.matcher(uri).matches()) {
                    UserInfo user = AuthService.validateToken(tokenBean);
                    // 验证失败
                    if (user == null) {
                        log.info("当前未登录,重定向{}->/nologin", uri);
                        ControllerUtil.redirect(req, res, "/nologin");
                    } else {
                        chain.doFilter(request, response);
                    }
                    return;
                }
            }
        }
        chain.doFilter(request, response);
    }

    // API直接调用时进行登录
    private void toLogin(HttpServletRequest request) {
        String test = request.getParameter("_test");
        if (test == null) {
            return;
        }
        log.info("----------------测试-------------------");
        Assert.isTrue(System.currentTimeMillis() < FormatUtil
                .parseDateLong("2016-05-01"), "测试不可用!");
        String contextPath = "http://localhost:" + request.getLocalPort()
                + ControllerUtil.getContextPath(request);
        MemberEntity user = new MemberEntity();
        user.setMemberId("200112650002");
        user.setChanId("XINGYIFU2016");
        user.setChanUserId("test002");
        user.setChanUserName(user.getChanUserId());
        user.setAccountId(user.getMemberId());
        TokenBean token = new TokenBean("0I4wPSz3uA9HpVFDme8e", "XINGYIFU2016");
        token.setUrl(contextPath + "/mock/b/member/token");
        UserInfo info = new UserInfo();
        token.setMember(user);
        token.setUser(info);
        ControllerUtil.storeToSession(request,
                CommonConstants.SESSION_LOGIN_USER, token);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
