package com.alibaba.csp.sentinel.dashboard.nacos.provider.degrade;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.DegradeRuleEntity;
import com.alibaba.csp.sentinel.dashboard.nacos.config.NacosConfigCenter;
import com.alibaba.csp.sentinel.dashboard.nacos.provider.AbstractDynamicRuleNacosProvider;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author immort-liuzyj(zyliu)
 * @since 2022/7/18  10:56
 */
@Component
public class DegradeRuleDynamicRuleNacosProvider extends AbstractDynamicRuleNacosProvider<List<DegradeRuleEntity>> {

    /**
     * @param app app name
     * @throws Exception if some error occurs
     */
    @Override
    public List<DegradeRuleEntity> getRules(String app, String ip, Integer port) throws Exception {

        String dataId = app + nacosConfigProperties.getDegradeRuleConfigSuffix();
        String content = NacosConfigCenter.getConfig(dataId);

        if (StringUtil.isEmpty(content)) {
            return new ArrayList<>();
        }

        List<DegradeRule> ruleList = JSON.parseArray(content, DegradeRule.class);

        if (CollectionUtils.isEmpty(ruleList)) {
            return new ArrayList<>(0);
        }

        return ruleList
                .stream()
                .filter(Objects::nonNull)
                .map(rule -> DegradeRuleEntity.fromDegradeRule(app, ip, port, rule))
                .collect(Collectors.toList());
    }
}
