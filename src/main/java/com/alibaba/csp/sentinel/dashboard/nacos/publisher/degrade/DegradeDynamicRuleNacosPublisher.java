package com.alibaba.csp.sentinel.dashboard.nacos.publisher.degrade;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.DegradeRuleEntity;
import com.alibaba.csp.sentinel.dashboard.nacos.publisher.AbstractDynamicRuleNacosPublisher;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author immort-liuzyj(zyliu)
 * @since 2022/7/18  10:48
 */
@Component
public class DegradeDynamicRuleNacosPublisher extends AbstractDynamicRuleNacosPublisher<List<DegradeRuleEntity>> {

    /**
     * 告诉父类 规则文件的文件名
     *
     * @param appName 应用的名称
     */
    @Override
    protected String loadDataId(String appName) {
        return appName + this.nacosConfigProperties.getDegradeRuleConfigSuffix();
    }
}
