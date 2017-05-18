package com.netfinworks.optimus.h5.web.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netfinworks.common.util.StreamUtil;
import com.netfinworks.optimus.domain.TokenBean;
import com.netfinworks.optimus.entity.MemberEntity;
import com.netfinworks.optimus.h5.web.CommonConstants;
import com.netfinworks.optimus.service.MemberService;
import com.netfinworks.util.JSONUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

/**
 * 与Controller相关的一些工具方法
 *
 * @author weichunhe
 */
@Component
public class ControllerUtil {

    private static Logger log = LoggerFactory.getLogger(ControllerUtil.class);

    /**
     * 获取项目路径
     *
     * @param request
     * @return
     */
    public static String getContextPath(HttpServletRequest request) {
        return request.getSession().getServletContext().getContextPath();
    }

    /**
     * 生成 结果数据
     *
     * @param url     完成 按钮要跳转的url
     * @param message
     * @param detail  详细信息
     * @param success 操作是否成功
     * @return
     */
    public static Map<String, Object> resultModel(String url, String message,
                                                  String detail, boolean success) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("url", url);
        map.put("message", message);
        map.put("detail", detail);
        map.put("success", success);
        return map;
    }

    /**
     * 将数据保存到session 中
     *
     * @param request
     * @param key
     * @param obj
     */
    public static void storeToSession(HttpServletRequest request, String key,
                                      Object obj) {
        request.getSession().setAttribute(key, obj);
    }

    /**
     * 从session中获取数据
     *
     * @param request
     * @param key
     * @param klass
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T retriveFromSession(HttpServletRequest request,
                                           String key, Class<T> klass) {
        Object obj = request.getSession().getAttribute(key);
        if (obj != null) {
            return (T) obj;
        }
        return null;
    }

    /**
     * 删除属性
     *
     * @param request
     * @param key
     */
    public static void removeFromSession(HttpServletRequest request, String key) {
        request.getSession().removeAttribute(key);
    }

    /**
     * 重定向
     *
     * @param request
     * @param response
     * @param url
     */
    public static void redirect(HttpServletRequest request,
                                HttpServletResponse response, String url) {
        String home = ControllerUtil.getContextPath(request) + url;
        log.info("重定向到页面:{}", home);
        try {
            response.sendRedirect(home);
        } catch (IOException e) {
            log.error("重定向时出错", e);
        }
    }

    /**
     * 转发到指定地址
     *
     * @param request
     * @param response
     * @param url
     */
    public static void forward(HttpServletRequest request,
                               HttpServletResponse response, String url) {
        log.info("转发到页面:{}", url);
        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception e) {
            log.error("请求转发时出错", e);
        }
    }

    /**
     * 是否是ajax请求
     *
     * @param request
     * @return
     */
    public static boolean isAjax(HttpServletRequest request) {
        boolean isAjax = false;
        String ajaxRequest = request.getHeader("x-Requested-With");
        if (ajaxRequest != null && ajaxRequest.equals("XMLHttpRequest")) {
            isAjax = true;
        }
        return isAjax;
    }

    /**
     * 获取当前登录用户
     *
     * @param request
     * @param memberService
     * @return
     */
    public static MemberEntity getMember(HttpServletRequest request,
                                         MemberService memberService) {
        TokenBean token = retriveFromSession(request,
                CommonConstants.SESSION_LOGIN_USER, TokenBean.class);

        // MemberEntity member = memberService.getOrCreateMember("xyf", "wch",
        // "",
        // "");
        // Assert.notNull(member, "找不到对应的账户信息!");
        return token.getMember();
    }

    /**
     * 获取服务器完整地址
     *
     * @param request
     * @return
     */
    public static String getAbsoluteContextPath(HttpServletRequest request) {
        return request.getScheme() + // 当前链接使用的协议
                "://" + request.getServerName() + // 服务器地址
                ":" + request.getServerPort() + // 端口号
                request.getContextPath();
    }

    // 保存HTML 名称为key,包含md5值的名称为value
    private static Map<String, String> htmlMap = new HashMap<String, String>();
    private static Pattern htmlNamePattern = Pattern
            .compile("((?:\\w+\\.)+(?:css|jpg|jpeg|gif|png|js|html))");


    private static Resource revSourceMap;

    @Value("classpath:revSourceMap.txt")
    public void setResource(Resource resource) {
        revSourceMap = resource;
    }

    /**
     * 获取静态资源
     *
     * @param path
     * @return
     */
    @SuppressWarnings("unchecked")
    public static String getStaticPath(String path) {
        if (htmlMap == null || htmlMap.size() == 0) {

            FileInputStream is = null;
            try {
                is = new FileInputStream(revSourceMap.getFile());
                String jsonStr = StreamUtil.readText(is);
                htmlMap = JSONUtil.fromJson(jsonStr, HashMap.class);
                log.info("revSourceMap={}", JSONUtil.toJson(htmlMap));
            } catch (Exception e) {
                log.error("读取revSourceMap出错了!", e);
            }
        }
        Matcher matcher = htmlNamePattern.matcher(path);
        int start = path.lastIndexOf("/");
        if (start == -1) {
            start = path.lastIndexOf("\\");
        }
        start += 1;
        if (matcher.find(start)) {
            String name = matcher.group();
            if (htmlMap.get(name) != null) {
                String md5name = path.replace(name, htmlMap.get(name));
                log.info("静态资源文件名替换为md5名称：{}->{}", path, md5name);
                return md5name;
            }
        }
        return path;
    }

}
