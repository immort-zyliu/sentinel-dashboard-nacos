package com.alibaba.csp.sentinel.dashboard.nacos.provider;

import com.alibaba.csp.sentinel.dashboard.nacos.config.NacosConfigManager;
import com.alibaba.csp.sentinel.dashboard.nacos.config.property.NacosConfigProperties;
import com.alibaba.csp.sentinel.dashboard.nacos.publisher.DynamicRuleNacosPublisher;
import com.alibaba.nacos.api.config.ConfigService;

import javax.annotation.Resource;

/**
 * @author immort-liuzyj(zyliu)
 * @since 2022/7/17  18:10
 */
public abstract class AbstractDynamicRuleNacosProvider<T> implements DynamicRuleNacosProvider<T> {

    @Resource
    protected NacosConfigProperties nacosConfigProperties;

}
