package com.netfinworks.optimus.admin.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.netfinworks.optimus.admin.util.ControllerUtil;
import com.netfinworks.optimus.admin.web.CommonConstants;
import com.netfinworks.optimus.domain.RestResult;
import com.netfinworks.optimus.entity.MemberEntity;
import com.netfinworks.optimus.service.ConfigService;
import com.netfinworks.optimus.service.MemberService;
import com.netfinworks.util.JSONUtil;

@Controller
public class HomeController {

    static Logger log = LoggerFactory.getLogger(HomeController.class);
    @Resource
    private ConfigService configService;
    @Resource
    private MemberService memberService;

    @RequestMapping("/")
    public void home(HttpServletRequest request, HttpServletResponse response) {
        ControllerUtil.redirect(request, response, "/login");
    }

    @RequestMapping("/index")
    public void demo(HttpServletRequest request, HttpServletResponse response) {
        ControllerUtil.forward(request, response,
                "/static/asset/views/home.html");
    }

    @RequestMapping(value = {"/login"})
    public void login(HttpServletRequest request, HttpServletResponse response) {
        if (ControllerUtil.isAjax(request)) {
            request.setAttribute("status", "NEED_LOGIN");
            throw new RuntimeException(ControllerUtil.getContextPath(request)
                    + "/login");
        }
        ControllerUtil.forward(request, response,
                "/static/asset/views/login.html");
    }

    @RequestMapping("/dologin")
    @ResponseBody
    public RestResult doLogin(HttpServletRequest request,
                              @RequestBody MemberEntity member) {
        Assert.notNull(member.getMemberId(), "缺少用户名!");
        Assert.notNull(member.getPayPasswd(), "缺少登录密码!");
        RestResult result = new RestResult();
        MemberEntity user = memberService.getMemberEntity(member.getMemberId());
        Assert.notNull(user, "不存在此用户!");
        Assert.isTrue("ADMIN".equals(user.getType()), "此用户不可登录管理系统!");
        Assert.isTrue(member.getPayPasswd().equals(user.getPayPasswd()),
                "登录密码不正确!");
        result.setResult(ControllerUtil.getContextPath(request) + "/index");
        ControllerUtil.storeToSession(request,
                CommonConstants.SESSION_LOGIN_USER, user);
        return result;

    }

    @RequestMapping("/changepwd")
    @ResponseBody
    public Object changePwd(HttpServletRequest request,
                            @RequestBody MemberEntity param) {
        Map<String, String> result = new HashMap<String, String>();
        String url = "/logout", msg = null;
        MemberEntity member = ControllerUtil.retriveFromSession(request,
                CommonConstants.SESSION_LOGIN_USER, MemberEntity.class);
        if (!StringUtils.equals(member.getPayPasswd(), param.getPayPasswd())) {
            msg = "原始密码不正确!";
        } else if (StringUtils.isEmpty(param.getType())) { // type 暂用作新秘密
            msg = "缺少新秘密!";
        }
        if (msg != null) {
            result.put("msg", msg);
        } else {
            member.setPayPasswd(param.getType());
            memberService.updateEntity(member);
            result.put("url", url);
        }
        return result;
    }

    @RequestMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        ControllerUtil.removeFromSession(request,
                CommonConstants.SESSION_LOGIN_USER);
        ControllerUtil.redirect(request, response, "/login");
    }

    @RequestMapping("/member")
    @ResponseBody
    public MemberEntity info(HttpServletRequest request,
                             HttpServletResponse response) {
        return ControllerUtil.retriveFromSession(request,
                CommonConstants.SESSION_LOGIN_USER, MemberEntity.class);
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

//	public static void main(String[] args) {
//		System.out.println(DigestUtils.sha1Hex("123456"));
//	}
}
