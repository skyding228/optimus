package com.netfinworks.optimus.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.xml.resolver.apps.xparse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netfinworks.optimus.service.entity.EntryQueryResult;
import com.netfinworks.optimus.service.fund.FundConstants;

/**
 * bean 操作
 *
 * @author weichunhe
 */
public class BeanUtil {

    private static Logger log = LoggerFactory.getLogger(BeanUtil.class);

    /**
     * 摘取 bean 中指定的names 属性
     *
     * @param bean
     * @param names 属性名称列表
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> pick(Object bean, String... names) {
        Map<String, Object> map = new HashMap<String, Object>();

        Map<String, String> properties = new HashMap<String, String>();
        try {
            properties = BeanUtils.describe(bean);
        } catch (Exception e) {
            log.error("描述bean时出现异常", e);
        }

        for (String name : names) {
            map.put(name, properties.get(name));
        }

        return map;
    }

    /**
     * 获取对象的字符串map
     *
     * @param bean
     * @param excludes
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> getPropertyMap(Object bean,
                                                     String... excludes) {
        List<String> excludeList = new ArrayList<String>();
        excludeList.addAll(Arrays.asList(excludes));
        excludeList.add("class");
        Map<String, String> map = null;
        try {
            map = BeanUtils.describe(bean);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, String> properties = new HashMap<String, String>();
        if (map != null) {
            map.forEach((x, y) -> {
                if (!excludeList.contains(x)) {
                    if (y != null) {
                        properties.put(x, y.toString());
                    } else {
                        properties.put(x, null);
                    }
                }
            });
        }

        return properties;
    }

    /**
     * 复制属性
     *
     * @param dest
     * @param src
     */
    public static void copyProperties(Object dest, Object src) {
        Map<String, String> propertiesMap = getPropertyMap(src);
        propertiesMap.forEach((k, v) -> {
            try {
                BeanUtils.setProperty(dest, k,
                        jodd.bean.BeanUtil.getPropertySilently(src, k));// copyProperties(dest,
                // src);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

}
