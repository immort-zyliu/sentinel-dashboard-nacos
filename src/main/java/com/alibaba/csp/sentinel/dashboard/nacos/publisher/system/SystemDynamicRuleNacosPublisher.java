package com.alibaba.csp.sentinel.dashboard.nacos.publisher.system;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.SystemRuleEntity;
import com.alibaba.csp.sentinel.dashboard.nacos.publisher.AbstractDynamicRuleNacosPublisher;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author immort-liuzyj(zyliu)
 * @since 2022/7/21  14:30
 * 系统限流规则
 */
@Component
public class SystemDynamicRuleNacosPublisher extends AbstractDynamicRuleNacosPublisher<List<SystemRuleEntity>> {

    /**
     * 告诉父类 规则文件的文件名
     *
     * @param appName 应用的名称
     */
    @Override
    protected String loadDataId(String appName) {
        return appName + this.nacosConfigProperties.getSystemRuleConfigSuffix();
    }
}
