package com.alibaba.csp.sentinel.dashboard.nacos.publisher.paramflow;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.ParamFlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.nacos.publisher.AbstractDynamicRuleNacosPublisher;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author immort-liuzyj(zyliu)
 * @since 2022/7/18  17:14
 */
@Component
public class ParamFlowDynamicRuleNacosPublisher extends AbstractDynamicRuleNacosPublisher<List<ParamFlowRuleEntity>> {

    /**
     * 告诉父类 规则文件的文件名
     *
     * @param appName 应用的名称
     */
    @Override
    protected String loadDataId(String appName) {
        return appName + this.nacosConfigProperties.getParamFlowRuleConfigSuffix();
    }
}
