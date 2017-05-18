package com.netfinworks.optimus.h5.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.netfinworks.optimus.domain.TokenBean;
import com.netfinworks.optimus.domain.UserInfo;
import com.netfinworks.optimus.entity.MemberEntity;
import com.netfinworks.optimus.h5.web.CommonConstants;
import com.netfinworks.optimus.h5.web.util.ControllerUtil;
import com.netfinworks.optimus.service.ConfigService;
import com.netfinworks.optimus.service.MemberService;
import com.netfinworks.optimus.utils.FormatUtil;
import com.netfinworks.util.JSONUtil;

@Controller
@RequestMapping("/")
public class HomeController {
    private static Logger log = LoggerFactory.getLogger(HomeController.class);

    @Resource
    private ConfigService configService;

    @Resource
    private MemberService memberService;

    @RequestMapping(value = {"/index", "/"})
    public void index(HttpServletRequest request, HttpServletResponse response) {
        ControllerUtil.redirect(request, response,
                ControllerUtil.getStaticPath("/static/index.html"));
    }

    @RequestMapping(value = {"/deposit"})
    public void deposit(HttpServletRequest request, HttpServletResponse response) {
        ControllerUtil.redirect(request, response,
                ControllerUtil.getStaticPath("/static/medeposit.html"));
    }

    /**
     * 当前处于未登录状态
     *
     * @param request
     * @return
     */
    @RequestMapping("/nologin")
    public ModelAndView result(HttpServletRequest request,
                               HttpServletResponse response) {
        if (ControllerUtil.isAjax(request)) {
            request.setAttribute("status", "NEED_LOGIN");
            throw new RuntimeException(ControllerUtil.getContextPath(request)
                    + ControllerUtil.getStaticPath("/static/nologin.html"));
        }
        ControllerUtil.redirect(request, response,
                ControllerUtil.getStaticPath("/static/nologin.html"));
        return null;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public void dologin(HttpServletRequest request,
                        HttpServletResponse response, @RequestBody MemberEntity member) {
        Assert.isTrue(!StringUtils.isEmpty(member.getMemberId()), "缺少用户名!");

        Assert.isTrue(System.currentTimeMillis() < FormatUtil
                .parseDateLong(configService
                        .getValue(ConfigService.TEST_LOGIN_DEADLINE)), "登录不可用!");

        String contextPath = "http://localhost:" + request.getLocalPort()
                + ControllerUtil.getContextPath(request);
        // 初始化充值提现url //只有test002可以充值提现
        // if ("test002".equals(member.getMemberId())) {
        // configService.setPlatValue(plat, ConfigService.PLAT_DEPOSIT_URL,
        // contextPath + "/mock/b/account/deposit");
        // configService.setPlatValue(plat, ConfigService.PLAT_WITHDRAW_URL,
        // contextPath + "/mock/b/account/redeem");
        // }

        MemberEntity user = memberService.getMemberEntity(member.getMemberId());

        Assert.notNull(user, "不存在此用户:memberId=" + member.getMemberId());
        TokenBean token = new TokenBean("0I4wPSz3uA9HpVFDme8e",
                user.getMemberId());
        if (!StringUtils.isEmpty(user.getMobile())) {
            token.setToken(user.getMobile());
        }
        token.setUrl(contextPath + "/mock/b/member/token");
        UserInfo info = new UserInfo();
        token.setMember(user);
        token.setUser(info);
        ControllerUtil.storeToSession(request,
                CommonConstants.SESSION_LOGIN_USER, token);
        if (ControllerUtil.isAjax(request)) {
            request.setAttribute("status", "NEED_LOGIN");
            throw new RuntimeException(ControllerUtil.getContextPath(request)
                    + ControllerUtil.getStaticPath("/static/index.html"));
        }
        ControllerUtil.redirect(request, response,
                ControllerUtil.getStaticPath("/static/index.html"));
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public void login(HttpServletRequest request, HttpServletResponse response) {
        ControllerUtil.redirect(request, response,
                ControllerUtil.getStaticPath("/static/login.html"));
    }

    /**
     * 刷新配置缓存
     */
    @RequestMapping("/refreshConfig")
    @ResponseBody
    public Object refreshConfig() {
        configService.init();
        log.info("当前配置信息:{}", JSONUtil.toJson(configService.getConfig()));
        return configService.getConfig();
    }
}
