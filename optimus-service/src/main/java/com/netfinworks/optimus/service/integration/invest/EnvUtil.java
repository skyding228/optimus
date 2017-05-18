package com.netfinworks.optimus.service.integration.invest;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.netfinworks.common.domain.OperationEnvironment;
import com.netfinworks.common.lang.SystemUtil;

public class EnvUtil {

    public static OperationEnvironment buildEnv() {
        OperationEnvironment env = new OperationEnvironment();
        env.setClientIp(SystemUtil.getHostInfo().getAddress());
        env.setClientMac(SystemUtil.getHostInfo().getName());
        env.setClientId("fax_admin");
        return env;
    }

    public static void main(String[] args) {
        System.out.println(new ReflectionToStringBuilder(EnvUtil.buildEnv(), ToStringStyle.SHORT_PREFIX_STYLE));
    }
}
