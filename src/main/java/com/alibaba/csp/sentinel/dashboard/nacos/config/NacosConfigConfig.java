package com.alibaba.csp.sentinel.dashboard.nacos.config;

import com.alibaba.csp.sentinel.dashboard.nacos.config.property.NacosConfigProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author immort-liuzyj(zyliu)
 * @since 2022/7/17  17:19
 * nacos 的 配置中心的 配置类
 */
@Configuration
@ConditionalOnProperty(name = "spring.cloud.nacos.config.enabled", matchIfMissing = true)
@EnableConfigurationProperties(NacosConfigProperties.class)
public class NacosConfigConfig {

    private static final Logger log = LoggerFactory.getLogger(NacosConfigConfig.class);

    @Bean
    public NacosConfigProperties nacosConfigProperties(ApplicationContext context) {
        if (context.getParent() != null
                && BeanFactoryUtils.beanNamesForTypeIncludingAncestors(
                context.getParent(), NacosConfigProperties.class).length > 0) {
            return BeanFactoryUtils.beanOfTypeIncludingAncestors(context.getParent(),
                    NacosConfigProperties.class);
        }
        return new NacosConfigProperties();
    }

    @Bean
    public NacosConfigManager nacosConfigManager(
            NacosConfigProperties nacosConfigProperties) {
        log.info("nacosConfigManager init");
        return new NacosConfigManager(nacosConfigProperties);
    }
}
