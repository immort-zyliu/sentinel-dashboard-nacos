package com.alibaba.csp.sentinel.dashboard.nacos.provider.system;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.SystemRuleEntity;
import com.alibaba.csp.sentinel.dashboard.nacos.config.NacosConfigCenter;
import com.alibaba.csp.sentinel.dashboard.nacos.provider.AbstractDynamicRuleNacosProvider;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author immort-liuzyj(zyliu)
 * @since 2022/7/21  14:26
 * <p>
 * 系统限流规则
 */
@Component
public class SystemRuleDynamicRuleNacosProvider extends AbstractDynamicRuleNacosProvider<List<SystemRuleEntity>> {

    /**
     * @param app  app name
     * @param ip   machine client IP
     * @param port machine client port
     * @throws Exception if some error occurs
     */
    @Override
    public List<SystemRuleEntity> getRules(String app, String ip, Integer port) throws Exception {

        // 获取文件名称
        String dataId = app + nacosConfigProperties.getSystemRuleConfigSuffix();

        // 从nacos中获取对应配置文件的内容
        String content = NacosConfigCenter.getConfig(dataId);


        if (StringUtil.isEmpty(content)) {
            return new ArrayList<>(0);
        }

        // json 反序列化
        List<SystemRule> ruleList = JSON.parseArray(content, SystemRule.class);

        // 转换成 dashboard 所使用的entity
        return ruleList.stream()
                .map(rule -> SystemRuleEntity.fromSystemRule(app, ip, port, rule))
                .collect(Collectors.toList());
    }
}
