package com.alibaba.csp.sentinel.dashboard.nacos.provider.flow;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.nacos.config.NacosConfigCenter;
import com.alibaba.csp.sentinel.dashboard.nacos.config.NacosConfigManager;
import com.alibaba.csp.sentinel.dashboard.nacos.provider.AbstractDynamicRuleNacosProvider;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author immort-liuzyj(zyliu)
 * @since 2022/7/17  18:08
 * 流控规则
 */
@Component
public class FlowRuleDynamicRuleNacosProvider extends AbstractDynamicRuleNacosProvider<List<FlowRuleEntity>> {


    @Override
    public List<FlowRuleEntity> getRules(String appName) throws Exception {
        return null;
    }

    /**
     * @param app app name
     * @throws Exception if some error occurs
     */
    @Override
    public List<FlowRuleEntity> getRules(String app, String ip, Integer port) throws Exception {
        String dataId = app + nacosConfigProperties.getFlowRuleConfigSuffix();
        String content = NacosConfigCenter.getConfig(dataId);

        List<FlowRule> flowRules = JSON.parseArray(content, FlowRule.class);
        if (flowRules == null) {
            flowRules = new ArrayList<>();
        }

        return flowRules.stream()
                .map(rule -> FlowRuleEntity.fromFlowRule(app, ip, port, rule))
                .peek(rule -> {
                    rule.setApp(app);
                    if (rule.getClusterConfig() != null && rule.getClusterConfig().getFlowId() != null) {
                        rule.setId(rule.getClusterConfig().getFlowId());
                    }
                })
                .collect(Collectors.toList());
    }
}
