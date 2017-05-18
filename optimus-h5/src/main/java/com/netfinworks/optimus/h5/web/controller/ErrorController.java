package com.netfinworks.optimus.h5.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.netfinworks.optimus.utils.LogUtil;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@Controller
public class ErrorController implements org.springframework.boot.autoconfigure.web.ErrorController {
    private static Logger log = LoggerFactory.getLogger(ErrorController.class);

    private static final String PATH = "/error";


    @Autowired
    private ErrorAttributes errorAttributes;

    @Override
    public String getErrorPath() {
        return PATH;
    }

    private Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) {
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        return errorAttributes.getErrorAttributes(requestAttributes, includeStackTrace);
    }

    @RequestMapping(value = PATH, produces = "application/json;charset=UTF-8")
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> map = new HashMap<String, Object>();
        Object status = request.getAttribute("status");// 自定义 status
        if (status == null) {
            status = request.getAttribute("javax.servlet.error.status_code");
        }
        map.put("status", status);
        map.put("reason", getErrorAttributes(request, false).get("message"));
        map.put("path", request.getAttribute("javax.servlet.error.request_uri"));
        Object object = request.getAttribute("javax.servlet.error.exception");
        if (object != null) {
            map.put("exception_id", LogUtil.error((Throwable) object));
        }
        log.error("{},ErrorAttributes,{},status={}", map, getErrorAttributes(request, false), request.getAttribute("javax.servlet.error.status_code"));

        response.setStatus((Integer) request.getAttribute("javax.servlet.error.status_code"));
        return new ModelAndView(new MappingJackson2JsonView(),
                map);

    }

    /**
     * 去掉原因中的异常信息，只保留中文描述信息
     *
     * @param reason
     * @return
     */
    private String removeException(String reason) {
        int start = reason.indexOf("Exception:");
        if (start != -1) {
            return reason.substring(start + 10).trim();
        }
        return reason.trim();

    }

}
