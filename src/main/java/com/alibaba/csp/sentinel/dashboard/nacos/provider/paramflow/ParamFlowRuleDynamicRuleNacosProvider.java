package com.alibaba.csp.sentinel.dashboard.nacos.provider.paramflow;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.ParamFlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.nacos.config.NacosConfigCenter;
import com.alibaba.csp.sentinel.dashboard.nacos.provider.AbstractDynamicRuleNacosProvider;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author immort-liuzyj(zyliu)
 * @since 2022/7/18  17:16
 */
@Component
public class ParamFlowRuleDynamicRuleNacosProvider extends AbstractDynamicRuleNacosProvider<List<ParamFlowRuleEntity>> {


    /**
     * @param app app name
     * @throws Exception if some error occurs
     */
    @Override
    public List<ParamFlowRuleEntity> getRules(String app, String ip, Integer port) throws Exception {

        String dataId = app + nacosConfigProperties.getParamFlowRuleConfigSuffix();
        String content = NacosConfigCenter.getConfig(dataId);

        if (StringUtil.isEmpty(content)) {
            return new ArrayList<>(0);
        }

        List<ParamFlowRule> ruleList = JSON.parseArray(content, ParamFlowRule.class);
        if (ruleList == null) {
            return new ArrayList<>(0);
        }

        return ruleList.stream()
                .map(rule -> ParamFlowRuleEntity.fromParamFlowRule(app, ip, port, rule))
                .collect(Collectors.toList());

    }
}
