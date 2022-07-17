package com.alibaba.csp.sentinel.dashboard.nacos.config;

import com.alibaba.csp.sentinel.dashboard.nacos.config.property.NacosConfigProperties;
import com.alibaba.csp.sentinel.dashboard.nacos.exception.NacosRuntimeException;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author immort-liuzyj(zyliu)
 * @since 2022/7/17  18:17
 */
@Component
public class NacosConfigCenter {

    private static final Logger log = LoggerFactory.getLogger(NacosConfigCenter.class);

    private static NacosConfigProperties NACOS_CONFIG_PROPERTIES;

    private static ConfigService CONFIG_SERVICE;

    public NacosConfigCenter(NacosConfigManager nacosConfigManager, NacosConfigProperties nacosConfigProperties) {
        NACOS_CONFIG_PROPERTIES = nacosConfigProperties;
        CONFIG_SERVICE = nacosConfigManager.getConfigService();
    }

    public static String getConfig(String dataId, String groupId, Integer readTimeOut) throws NacosException {
        return CONFIG_SERVICE.getConfig(dataId, groupId, readTimeOut);
    }

    public static String getConfig(String dataId, String groupId) throws NacosException {
        int timeout = NACOS_CONFIG_PROPERTIES.getTimeout();
        return getConfig(dataId, groupId, timeout);
    }

    public static String getConfig(String dataId) throws NacosException {
        return getConfig(dataId, NACOS_CONFIG_PROPERTIES.getGroup());
    }


    public static void publishConfig(String dataId, String content) throws NacosException {
        CONFIG_SERVICE.publishConfig(dataId, NACOS_CONFIG_PROPERTIES.getGroup(), content);
    }

}
