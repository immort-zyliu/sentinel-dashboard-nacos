package com.alibaba.csp.sentinel.dashboard.nacos.publisher.flow;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.nacos.publisher.AbstractDynamicRuleNacosPublisher;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author immort-liuzyj(zyliu)
 * @since 2022/7/17  18:35
 */
@Component
public class FlowDynamicRuleNacosPublisher extends AbstractDynamicRuleNacosPublisher<List<FlowRuleEntity>> {

    /**
     * 告诉父类 规则文件的文件名
     */
    @Override
    protected String loadDataId(String appName) {
        return appName + nacosConfigProperties.getFlowRuleConfigSuffix();
    }
}
